package com.jher.nid_aux_histoires.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.jher.nid_aux_histoires.config.Constants;
import com.jher.nid_aux_histoires.domain.Authority;
import com.jher.nid_aux_histoires.domain.User;
import com.jher.nid_aux_histoires.repository.AuthorityRepository;
import com.jher.nid_aux_histoires.repository.UserRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.security.SecurityUtils;
import com.jher.nid_aux_histoires.service.dto.UserDTO;
import com.jher.nid_aux_histoires.service.mapper.UserMapperLight;

import io.github.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepository authorityRepository;

	private final CacheManager cacheManager;

	private final UserMapperLight userMapperLight;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthorityRepository authorityRepository, CacheManager cacheManager, UserMapperLight userMapperLight) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorityRepository = authorityRepository;
		this.cacheManager = cacheManager;
		this.userMapperLight = userMapperLight;
	}

	public Optional<User> activateRegistration(String key) {
		log.debug("Activating user for activation key");
		return userRepository.findOneByActivationKey(key).map(user -> {
			// activate given user for the registration key.
			user.setActivated(true);
			user.setActivationKey(null);
			this.clearUserCaches(user);
			log.debug("Activated user: {}", user);
			return user;
		});
	}

	public Optional<User> completePasswordReset(String newPassword, String key) {
		log.debug("Reset user password for reset key");
		return userRepository.findOneByResetKey(key)
				.filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400))).map(user -> {
					user.setPassword(passwordEncoder.encode(newPassword));
					user.setResetKey(null);
					user.setResetDate(null);
					this.clearUserCaches(user);
					return user;
				});
	}

	public Optional<User> requestPasswordReset(String mail) {
		return userRepository.findOneByEmailIgnoreCase(mail).filter(User::getActivated).map(user -> {
			user.setResetKey(RandomUtil.generateResetKey());
			user.setResetDate(Instant.now());
			this.clearUserCaches(user);
			return user;
		});
	}

	public User registerUser(UserDTO userDTO, String password) {
		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new UsernameAlreadyUsedException();
			}
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});
		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		if (userDTO.getEmail() != null) {
			newUser.setEmail(userDTO.getEmail().toLowerCase());
		}
		newUser.setImageUrl(userDTO.getImageUrl());
		newUser.setLangKey(userDTO.getLangKey());
		// new user is not active
		newUser.setActivated(false);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Set<Authority> authorities = new HashSet<>();
		authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		this.clearUserCaches(newUser);
		log.debug("Created Information for User");
		return newUser;
	}

	private boolean removeNonActivatedUser(User existingUser) {
		if (existingUser.getActivated()) {
			return false;
		}
		userRepository.delete(existingUser);
		userRepository.flush();
		this.clearUserCaches(existingUser);
		return true;
	}

	public User createUser(UserDTO userDTO) {
		User user = new User();
		user.setLogin(userDTO.getLogin().toLowerCase());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		if (userDTO.getEmail() != null) {
			user.setEmail(userDTO.getEmail().toLowerCase());
		}
		user.setImageUrl(userDTO.getImageUrl());
		if (userDTO.getLangKey() == null) {
			user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
		} else {
			user.setLangKey(userDTO.getLangKey());
		}
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey());
		user.setResetDate(Instant.now());
		user.setActivated(true);
		if (userDTO.getAuthorities() != null) {
			Set<Authority> authorities = userDTO.getAuthorities().stream().map(authorityRepository::findById)
					.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}
		userRepository.save(user);
		this.clearUserCaches(user);
		log.debug("Created Information for User");
		return user;
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update.
	 * @return updated user.
	 */
	public Optional<UserDTO> updateUser(UserDTO userDTO) {
		return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
				.map(user -> {
					this.clearUserCaches(user);
					user.setLogin(userDTO.getLogin().toLowerCase());
					user.setFirstName(userDTO.getFirstName());
					user.setLastName(userDTO.getLastName());
					if (userDTO.getEmail() != null) {
						user.setEmail(userDTO.getEmail().toLowerCase());
					}
					user.setImageUrl(userDTO.getImageUrl());
					user.setActivated(userDTO.isActivated());
					user.setLangKey(userDTO.getLangKey());
					Set<Authority> managedAuthorities = user.getAuthorities();
					managedAuthorities.clear();
					userDTO.getAuthorities().stream().map(authorityRepository::findById).filter(Optional::isPresent)
							.map(Optional::get).forEach(managedAuthorities::add);
					this.clearUserCaches(user);
					log.debug("Changed Information for User: {}", user);
					return user;
				}).map(UserDTO::new);
	}

	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			userRepository.delete(user);
			this.clearUserCaches(user);
			log.debug("Deleted User: {}", user);
		});
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user.
	 * @param lastName  last name of user.
	 * @param email     email id of user.
	 * @param langKey   language key.
	 * @param imageUrl  image URL of user.
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			if (email != null) {
				user.setEmail(email.toLowerCase());
			}
			user.setLangKey(langKey);
			user.setImageUrl(imageUrl);
			this.clearUserCaches(user);
			log.debug("Changed Information for User: {}", user);
		});
	}

	@Transactional
	public void changePassword(String currentClearTextPassword, String newPassword) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			String currentEncryptedPassword = user.getPassword();
			if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
				throw new InvalidPasswordException();
			}
			String encryptedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encryptedPassword);
			this.clearUserCaches(user);
			log.debug("Changed password for User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
		return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	@Transactional(readOnly = true)
	public List<UserDTO> getAuthorsLight() {
		userRepository.flush();
		Predicate<Authority> byName = authority -> authority.getName().equals(AuthoritiesConstants.AUTHOR);
		Predicate<User> byAuthority = user -> user.getAuthorities().stream().filter(byName).count() > 0;
		List<User> users = userRepository.findAllWithEager().stream().filter(byAuthority).collect(Collectors.toList());
		return userMapperLight.usersToUserDTOs(users);
	}

	@Transactional(readOnly = true)
	public UserDTO getUserLightByLogin(String login) {
		Optional<User> user = userRepository.findOneByLogin(login);
		if (user.isPresent()) {
			return userMapperLight.userToUserDTO(user.get());
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities() {
		Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
		log.info("Get user authority with login : {}", currentUserLogin.get());
		return currentUserLogin.flatMap(userRepository::findOneWithAuthoritiesByLogin);
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
				Instant.now().minus(3, ChronoUnit.DAYS)).forEach(user -> {
					log.debug("Deleting not activated user");
					userRepository.delete(user);
					this.clearUserCaches(user);
				});
	}

	/**
	 * Gets a list of all the authorities.
	 * 
	 * @return a list of all the authorities.
	 */
	@Transactional(readOnly = true)
	public List<String> getAuthorities() {
		return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

	private void clearUserCaches(User user) {
		Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
		if (user.getEmail() != null) {
			Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
		}
	}
}
