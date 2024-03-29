package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jher.nid_aux_histoires.config.Constants;

import lombok.Data;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
public class User extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String login;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", length = 60, nullable = false)
	private String password;

	@Size(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	@Size(max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 254)
	@Column(length = 254, unique = true)
	private String email;

	@NotNull
	@Column(nullable = false)
	private boolean activated = false;

	@Size(min = 2, max = 10)
	@Column(name = "lang_key", length = 10)
	private String langKey;

	@Size(max = 256)
	@Column(name = "image_url", length = 256)
	private String imageUrl;

	@Size(max = 20)
	@Column(name = "activation_key", length = 20)
	@JsonIgnore
	private String activationKey;

	@Size(max = 20)
	@Column(name = "reset_key", length = 20)
	@JsonIgnore
	private String resetKey;

	@Column(name = "reset_date")
	private Instant resetDate = null;

	@Size(max = 5000)
	@Column(name = "introduction")
	private String introduction = null;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<BookStatus> bookStatuses = new HashSet<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "jhi_user_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@BatchSize(size = 20)
	private Set<Authority> authorities = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Comment> comments = new HashSet<>();

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Book> books = new HashSet<>();

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Cover> covers = new HashSet<>();

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Bonus> bonuses = new HashSet<>();

	// Lowercase the login before saving it in database
	public void setLogin(String login) {
		this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
	}

	public boolean getActivated() {
		return this.activated;
	}

	public User comments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public User addComment(Comment comment) {
		this.comments.add(comment);
		comment.setUser(this);
		return this;
	}

	public User removeComment(Comment comment) {
		this.comments.remove(comment);
		comment.setBook(null);
		return this;
	}

	public User books(Set<Book> books) {
		this.books = books;
		return this;
	}

	public User addBook(Book book) {
		this.books.add(book);
		book.setAuthor(this);
		return this;
	}

	public User removeBook(Book book) {
		this.books.remove(book);
		book.setAuthor(null);
		return this;
	}

	public User covers(Set<Cover> covers) {
		this.covers = covers;
		return this;
	}

	public User addCover(Cover cover) {
		this.covers.add(cover);
		cover.setOwner(this);
		return this;
	}

	public User removeCover(Cover cover) {
		this.covers.remove(cover);
		cover.setOwner(null);
		return this;
	}

	public User bonuss(Set<Bonus> bonuss) {
		this.bonuses = bonuss;
		return this;
	}

	public User addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
		bonus.setOwner(this);
		return this;
	}

	public User removeBonus(Bonus bonus) {
		this.bonuses.remove(bonus);
		bonus.setOwner(null);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		return id != null && id.equals(((User) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "User{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", email='" + email + '\'' + ", imageUrl='" + imageUrl + '\'' + ", activated='" + activated
				+ '\'' + ", langKey='" + langKey + '\'' + ", activationKey='" + activationKey + '\'' + "}";
	}
}
