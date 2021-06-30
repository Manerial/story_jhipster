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

import com.jher.nid_aux_histoires.service.LibraryService;
import com.jher.nid_aux_histoires.service.dto.LibraryDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.jher.nid_aux_histoires.domain.Library}.
 */
@RestController
@RequestMapping("/api")
public class LibraryResource {

	private final Logger log = LoggerFactory.getLogger(LibraryResource.class);

	private static final String ENTITY_NAME = "library";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final LibraryService libraryService;

	public LibraryResource(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	/**
	 * {@code POST  /libraries} : Create a new library.
	 *
	 * @param libraryDTO the libraryDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new libraryDTO, or with status {@code 400 (Bad Request)} if
	 *         the library has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/libraries")
	public ResponseEntity<LibraryDTO> createLibrary(@RequestBody LibraryDTO libraryDTO) throws URISyntaxException {
		log.debug("REST request to save Library : {}", libraryDTO);
		if (libraryDTO.getId() != null) {
			throw new BadRequestAlertException("A new library cannot already have an ID", ENTITY_NAME, "idexists");
		}
		LibraryDTO result = libraryService.save(libraryDTO);
		return ResponseEntity
				.created(new URI("/api/libraries/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /libraries} : Updates an existing library.
	 *
	 * @param libraryDTO the libraryDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated libraryDTO, or with status {@code 400 (Bad Request)} if
	 *         the libraryDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the libraryDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/libraries")
	public ResponseEntity<LibraryDTO> updateLibrary(@RequestBody LibraryDTO libraryDTO) throws URISyntaxException {
		log.debug("REST request to update Library : {}", libraryDTO);
		if (libraryDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		LibraryDTO result = libraryService.save(libraryDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, libraryDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /libraries} : get all the libraries.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of libraries in body.
	 */
	@GetMapping("/libraries")
	public ResponseEntity<List<LibraryDTO>> getAllLibraries(Pageable pageable) {
		log.debug("REST request to get a page of Libraries");
		Page<LibraryDTO> page = libraryService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /libraries/:id} : get the "id" library.
	 *
	 * @param id the id of the libraryDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the libraryDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/libraries/{id}")
	public ResponseEntity<LibraryDTO> getLibrary(@PathVariable Long id) {
		log.debug("REST request to get Library : {}", id);
		Optional<LibraryDTO> libraryDTO = libraryService.findOne(id);
		return ResponseUtil.wrapOrNotFound(libraryDTO);
	}

	/**
	 * {@code DELETE  /libraries/:id} : delete the "id" library.
	 *
	 * @param id the id of the libraryDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/libraries/{id}")
	public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
		log.debug("REST request to delete Library : {}", id);
		libraryService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
