package com.jher.nid_aux_histoires.service.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jher.nid_aux_histoires.config.Constants;
import com.jher.nid_aux_histoires.domain.Authority;
import com.jher.nid_aux_histoires.domain.User;

import lombok.Data;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
public class UserDTO {

	private Long id;

	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 254)
	private String email;

	@Size(max = 256)
	private String imageUrl;

	private boolean activated = false;

	@Size(min = 2, max = 10)
	private String langKey;

	private String createdBy;

	private Instant createdDate;

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	private String introduction;

	private int books;

	private Set<String> authorities;

	private Set<CommentDTO> comments = new HashSet<>();

	public UserDTO() {
		// Empty constructor needed for Jackson.
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.activated = user.getActivated();
		this.imageUrl = user.getImageUrl();
		this.langKey = user.getLangKey();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.lastModifiedBy = user.getLastModifiedBy();
		this.lastModifiedDate = user.getLastModifiedDate();
		this.introduction = user.getIntroduction();
		this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "UserDTO{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", email='" + email + '\'' + ", imageUrl='" + imageUrl + '\'' + ", activated=" + activated
				+ ", langKey='" + langKey + '\'' + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastModifiedBy='" + lastModifiedBy + '\'' + ", lastModifiedDate=" + lastModifiedDate
				+ ", authorities=" + authorities + "}";
	}
}
