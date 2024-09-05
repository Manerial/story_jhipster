package com.jher.nid_aux_histoires.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jher.nid_aux_histoires.domain.User;
import com.jher.nid_aux_histoires.service.dto.UserDTO;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as
 * MapStruct support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapperLight {
	private final Logger log = LoggerFactory.getLogger(UserMapperLight.class);

	public List<UserDTO> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
	}

	public UserDTO userToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setLogin(user.getLogin());
		userDTO.setImageUrl(user.getImageUrl());
		userDTO.setIntroduction(user.getIntroduction());
		try {
			userDTO.setBooks((user.getBooks() != null) ? user.getBooks().size() : 0);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return userDTO;
	}

	public User userFromId(Long id) {
		if (id == null) {
			return null;
		}
		User user = new User();
		user.setId(id);
		return user;
	}
}
