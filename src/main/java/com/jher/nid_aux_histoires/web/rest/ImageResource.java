package com.jher.nid_aux_histoires.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jher.nid_aux_histoires.service.ImageService;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Image}.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

	private final Logger log = LoggerFactory.getLogger(ImageResource.class);

	private static final String ENTITY_NAME = "image";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ImageService imageService;

	public ImageResource(ImageService imageService) {
		this.imageService = imageService;
	}

	/**
	 * {@code POST  /images} : Create a new image.
	 *
	 * @param imageDTO the imageDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new imageDTO, or with status {@code 400 (Bad Request)} if
	 *         the image has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/images")
	public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
		log.debug("REST request to save Image : {}", imageDTO);
		if (imageDTO.getId() != null) {
			throw new BadRequestAlertException("A new image cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ImageDTO result = imageService.save(imageDTO);
		return ResponseEntity
				.created(new URI("/api/images/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /images} : Updates an existing image.
	 *
	 * @param imageDTO the imageDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated imageDTO, or with status {@code 400 (Bad Request)} if the
	 *         imageDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the imageDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/images")
	public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
		log.debug("REST request to update Image : {}", imageDTO);
		if (imageDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ImageDTO result = imageService.save(imageDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /images} : get all the images.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of images in body.
	 */
	@GetMapping("/images")
	public ResponseEntity<List<ImageDTO>> getAllImages(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Images");
		Page<ImageDTO> page = imageService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /images} : get all the images related to a Book.
	 *
	 * @param id the id of the Book to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of images in body.
	 */
	@GetMapping("/images/book/{id}")
	public ResponseEntity<List<ImageDTO>> getAllImagesByBook(@PathVariable Long id) {
		log.debug("REST request to get a list of Images related to a Book");
		List<ImageDTO> images = imageService.findAllByBookId(id);
		return ResponseEntity.ok().body(images);
	}

	/**
	 * {@code GET  /images} : get all the images related to a Part.
	 *
	 * @param id the id of the Part to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of images in body.
	 */
	@GetMapping("/images/part/{id}")
	public ResponseEntity<List<ImageDTO>> getAllImagesByPart(@PathVariable Long id) {
		log.debug("REST request to get a list of Images related to a Part");
		List<ImageDTO> images = imageService.findAllByPartId(id);
		return ResponseEntity.ok().body(images);
	}

	/**
	 * {@code GET  /images} : get all the images related to a Chapter.
	 *
	 * @param id the id of the Chapter to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of images in body.
	 */
	@GetMapping("/images/chapter/{id}")
	public ResponseEntity<List<ImageDTO>> getAllImagesByChapter(@PathVariable Long id) {
		log.debug("REST request to get a list of Images related to a Chapter");
		List<ImageDTO> images = imageService.findAllByChapterId(id);
		return ResponseEntity.ok().body(images);
	}

	/**
	 * {@code GET  /images} : get all the images related to a Scene.
	 *
	 * @param id the id of the Scene to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of images in body.
	 */
	@GetMapping("/images/scene/{id}")
	public ResponseEntity<List<ImageDTO>> getAllImagesByScene(@PathVariable Long id) {
		log.debug("REST request to get a list of Images related to a Scene");
		List<ImageDTO> images = imageService.findAllBySceneId(id);
		return ResponseEntity.ok().body(images);
	}

	/**
	 * {@code GET  /images/:id} : get the "id" image.
	 *
	 * @param id the id of the imageDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the imageDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/images/{id}")
	public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
		log.debug("REST request to get Image : {}", id);
		Optional<ImageDTO> imageDTO = imageService.findOne(id);
		return ResponseUtil.wrapOrNotFound(imageDTO);
	}

	/**
	 * {@code DELETE  /images/:id} : delete the "id" image.
	 *
	 * @param id the id of the imageDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/images/{id}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
		log.debug("REST request to delete Image : {}", id);
		imageService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
