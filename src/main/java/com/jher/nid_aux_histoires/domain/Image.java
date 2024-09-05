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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image implements Serializable {

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

	@ManyToMany(mappedBy = "images")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Book> books = new HashSet<>();

	@ManyToMany(mappedBy = "images")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Part> parts = new HashSet<>();

	@ManyToMany(mappedBy = "images")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Chapter> chapters = new HashSet<>();

	@ManyToMany(mappedBy = "images")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Scene> scenes = new HashSet<>();

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

	public Image name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicture() {
		return picture;
	}

	public Image picture(byte[] picture) {
		this.picture = picture;
		return this;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getPictureContentType() {
		return pictureContentType;
	}

	public Image pictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
		return this;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public byte[] getPreview() {
		return preview;
	}

	public Image preview(byte[] preview) {
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

	public Image previewContentType(String previewContentType) {
		this.previewContentType = previewContentType;
		return this;
	}

	public void setPreviewContentType(String previewContentType) {
		this.previewContentType = previewContentType;
	}

	public Set<Book> getBookToCovers() {
		return bookToCovers;
	}

	public Image bookToCovers(Set<Book> books) {
		this.bookToCovers = books;
		return this;
	}

	public Image addBookToCover(Book book) {
		this.bookToCovers.add(book);
		book.setCover(this);
		return this;
	}

	public Image removeBookToCover(Book book) {
		this.bookToCovers.remove(book);
		book.setCover(null);
		return this;
	}

	public void setBookToCovers(Set<Book> books) {
		this.bookToCovers = books;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public Image books(Set<Book> books) {
		this.books = books;
		return this;
	}

	public Image addBook(Book book) {
		this.books.add(book);
		book.getImages().add(this);
		return this;
	}

	public Image removeBook(Book book) {
		this.books.remove(book);
		book.getImages().remove(this);
		return this;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Set<Part> getParts() {
		return parts;
	}

	public Image parts(Set<Part> parts) {
		this.parts = parts;
		return this;
	}

	public Image addPart(Part part) {
		this.parts.add(part);
		part.getImages().add(this);
		return this;
	}

	public Image removePart(Part part) {
		this.parts.remove(part);
		part.getImages().remove(this);
		return this;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public Image chapters(Set<Chapter> chapters) {
		this.chapters = chapters;
		return this;
	}

	public Image addChapter(Chapter chapter) {
		this.chapters.add(chapter);
		chapter.getImages().add(this);
		return this;
	}

	public Image removeChapter(Chapter chapter) {
		this.chapters.remove(chapter);
		chapter.getImages().remove(this);
		return this;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Set<Scene> getScenes() {
		return scenes;
	}

	public Image scenes(Set<Scene> scenes) {
		this.scenes = scenes;
		return this;
	}

	public Image addScene(Scene scene) {
		this.scenes.add(scene);
		scene.getImages().add(this);
		return this;
	}

	public Image removeScene(Scene scene) {
		this.scenes.remove(scene);
		scene.getImages().remove(this);
		return this;
	}

	public void setScenes(Set<Scene> scenes) {
		this.scenes = scenes;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Image)) {
			return false;
		}
		return id != null && id.equals(((Image) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Image{" + "id=" + getId() + ", name='" + getName() + "'" + ", picture='" + getPicture() + "'"
				+ ", pictureContentType='" + getPictureContentType() + "'" + ", preview='" + getPreview() + "'"
				+ ", previewContentType='" + getPreviewContentType() + "'" + "}";
	}
}
