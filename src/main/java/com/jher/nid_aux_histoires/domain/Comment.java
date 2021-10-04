package com.jher.nid_aux_histoires.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "text")
	private String text;

	@Column(name = "created_date")
	private Date createdDate;

	@ManyToOne
	@JsonIgnoreProperties(value = "comments", allowSetters = true)
	private Book book;

	@ManyToOne
	@JsonIgnoreProperties(value = "comments", allowSetters = true)
	private User user;

	public Comment() {
		this.createdDate = new Date();
	}

	public Comment text(String text) {
		this.text = text;
		return this;
	}

	public Comment book(Book book) {
		this.book = book;
		return this;
	}

	public Comment user(User user) {
		this.user = user;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Comment)) {
			return false;
		}
		return id != null && id.equals(((Comment) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Comment{" + "id=" + getId() + ", text='" + getText() + "'" + "}";
	}
}
