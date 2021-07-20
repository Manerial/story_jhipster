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

import com.jher.nid_aux_histoires.config.SecurityConfiguration;
import com.jher.nid_aux_histoires.service.BookStatusService;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.jher.nid_aux_histoires.domain.BookStatus}.
 */
@RestController
@RequestMapping("/api")
public class BookStatusResource {

	private final Logger log = LoggerFactory.getLogger(BookStatusResource.class);

	private static final String ENTITY_NAME = "bookStatus";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BookStatusService bookStatusService;

	public BookStatusResource(BookStatusService bookStatusService) {
		this.bookStatusService = bookStatusService;
	}

	/**
	 * {@code POST  /bookStatuses} : Create a new bookStatus.
	 *
	 * @param bookStatusDTO the bookStatusDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new bookStatusDTO, or with status {@code 400 (Bad Request)}
	 *         if the bookStatus has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/bookStatuses")
	public ResponseEntity<BookStatusDTO> createBookStatus(@RequestBody BookStatusDTO bookStatusDTO)
			throws URISyntaxException {
		log.debug("REST request to save BookStatus : {}", bookStatusDTO);
		if (bookStatusDTO.getId() != null) {
			throw new BadRequestAlertException("A new book status cannot already have an ID", ENTITY_NAME, "idexists");
		}
		BookStatusDTO result = bookStatusService.save(bookStatusDTO);
		return ResponseEntity
				.created(new URI("/api/bookStatuses/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /bookStatuses} : Updates an existing bookStatus.
	 *
	 * @param bookStatusDTO the bookStatusDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated bookStatusDTO, or with status {@code 400 (Bad Request)}
	 *         if the bookStatusDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the bookStatusDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/bookStatuses")
	public ResponseEntity<BookStatusDTO> updateBookStatus(@RequestBody BookStatusDTO bookStatusDTO)
			throws URISyntaxException {
		log.debug("REST request to update BookStatus : {}", bookStatusDTO);
		if (bookStatusDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		BookStatusDTO result = bookStatusService.save(bookStatusDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				bookStatusDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /bookStatuses} : get all the bookStatuses.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of bookStatuses in body.
	 */
	@GetMapping("/bookStatuses")
	public ResponseEntity<List<BookStatusDTO>> getAllBookStatus(Pageable pageable) {
		log.debug("REST request to get a page of BookStatus");
		Page<BookStatusDTO> page = bookStatusService.findAllByUser(pageable, SecurityConfiguration.getUserLogin());
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /bookStatuses} : get all the bookStatuses.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of bookStatuses in body.
	 */
	@GetMapping("/bookStatuses/user")
	public ResponseEntity<List<BookStatusDTO>> getAllBookStatusByUser(Pageable pageable) {
		log.debug("REST request to get a page of BookStatus");
		Page<BookStatusDTO> page = bookStatusService.findAllByUser(pageable, SecurityConfiguration.getUserLogin());
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /bookStatuses/:id} : get the "id" bookStatus.
	 *
	 * @param id the id of the bookStatusDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the bookStatusDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/bookStatuses/{id}")
	public ResponseEntity<BookStatusDTO> getBookStatus(@PathVariable Long id) {
		log.debug("REST request to get BookStatus : {}", id);
		Optional<BookStatusDTO> bookStatusDTO = bookStatusService.findOne(id);
		return ResponseUtil.wrapOrNotFound(bookStatusDTO);
	}

	/**
	 * {@code DELETE  /bookStatuses/:id} : delete the "id" bookStatus.
	 *
	 * @param id the id of the bookStatusDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/bookStatuses/{id}")
	public ResponseEntity<Void> deleteBookStatus(@PathVariable Long id) {
		log.debug("REST request to delete BookStatus : {}", id);
		bookStatusService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
