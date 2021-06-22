package com.jher.nid_aux_histoires.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.jher.nid_aux_histoires.domain.Comment} entity.
 */
public class BonusDTO implements Serializable, Comparable<BonusDTO> {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long ownerId;

	private String ownerLogin;

	private byte[] data;

	private String dataContentType;

	private Long bookId;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerLogin() {
		return ownerLogin;
	}

	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = ownerLogin;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public void setDataContentType(String dataContentType) {
		this.dataContentType = dataContentType;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BonusDTO)) {
			return false;
		}

		return id != null && id.equals(((BonusDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "BonusDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", dataContentType='"
				+ getDataContentType() + "'" + "}";
	}

	@Override
	public int compareTo(BonusDTO o) {
		return this.id.compareTo(o.id);
	}
}
