package com.jher.nid_aux_histoires.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Cover.
 */
@Entity
@Table(name = "cover")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cover implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Lob
	@Column(name = "picture")
	private byte[] picture;

	@Column(name = "picture_content_type")
	private String pictureContentType;

	@Lob
	@Column(name = "preview")
	private byte[] preview;

	@Column(name = "preview_content_type")
	private String previewContentType;

	@OneToMany(mappedBy = "cover")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Book> bookToCovers = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Cover name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicture() {
		return picture;
	}

	public Cover picture(byte[] picture) {
		this.picture = picture;
		return this;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getPictureContentType() {
		return pictureContentType;
	}

	public Cover pictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
		return this;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public byte[] getPreview() {
		return preview;
	}

	public Cover preview(byte[] preview) {
		this.preview = preview;
		return this;
	}

	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

	public void generatePreview() {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(this.getPicture());
			BufferedImage bImage = ImageIO.read(bais);
			double ratio = bImage.getHeight() / 170;
			int newWidth = (int) (bImage.getWidth() / ratio);
			int newHeight = (int) (bImage.getHeight() / ratio);

			BufferedImage newResizedImage = resizeImage(bImage, newWidth, newHeight);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(newResizedImage, getExtension(), baos);
			this.preview = baos.toByteArray();
			this.previewContentType = this.pictureContentType;
		} catch (Exception e) {

		}
	}

	public String getExtension() {
		return this.pictureContentType.split("/")[1];
	}

	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
		java.awt.Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight,
				java.awt.Image.SCALE_DEFAULT);
		BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
		return outputImage;
	}

	public String getPreviewContentType() {
		return previewContentType;
	}

	public Cover previewContentType(String previewContentType) {
		this.previewContentType = previewContentType;
		return this;
	}

	public void setPreviewContentType(String previewContentType) {
		this.previewContentType = previewContentType;
	}

	public Set<Book> getBookToCovers() {
		return bookToCovers;
	}

	public Cover bookToCovers(Set<Book> books) {
		this.bookToCovers = books;
		return this;
	}

	public Cover addBookToCover(Book book) {
		this.bookToCovers.add(book);
		book.setCover(this);
		return this;
	}

	public Cover removeBookToCover(Book book) {
		this.bookToCovers.remove(book);
		book.setCover(null);
		return this;
	}

	public void setBookToCovers(Set<Book> books) {
		this.bookToCovers = books;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Cover)) {
			return false;
		}
		return id != null && id.equals(((Cover) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "cover{" + "id=" + getId() + ", name='" + getName() + "'" + ", picture='" + getPicture() + "'"
				+ ", pictureContentType='" + getPictureContentType() + "'" + ", preview='" + getPreview() + "'"
				+ ", previewContentType='" + getPreviewContentType() + "'" + "}";
	}
}
