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

import com.jher.nid_aux_histoires.service.CoverService;
import com.jher.nid_aux_histoires.service.dto.CoverDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Cover}.
 */
@RestController
@RequestMapping("/api")
public class CoverResource {

	private final Logger log = LoggerFactory.getLogger(CoverResource.class);

	private static final String ENTITY_NAME = "cover";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CoverService coverService;

	public CoverResource(CoverService coverService) {
		this.coverService = coverService;
	}

	/**
	 * {@code POST  /covers} : Create a new cover.
	 *
	 * @param coverDTO the coverDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new coverDTO, or with status {@code 400 (Bad Request)} if
	 *         the cover has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/covers")
	public ResponseEntity<CoverDTO> createCover(@RequestBody CoverDTO coverDTO) throws URISyntaxException {
		log.debug("REST request to save Cover : {}", coverDTO);
		if (coverDTO.getId() != null) {
			throw new BadRequestAlertException("A new cover cannot already have an ID", ENTITY_NAME, "idexists");
		}
		CoverDTO result = coverService.save(coverDTO);
		return ResponseEntity
				.created(new URI("/api/cover/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /covers} : Updates an existing cover.
	 *
	 * @param coverDTO the coverDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated coverDTO, or with status {@code 400 (Bad Request)} if the
	 *         coverDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the coverDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/covers")
	public ResponseEntity<CoverDTO> updateCover(@RequestBody CoverDTO coverDTO) throws URISyntaxException {
		log.debug("REST request to update Cover : {}", coverDTO);
		if (coverDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		CoverDTO result = coverService.save(coverDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /covers} : get all the covers.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of covers in body.
	 */
	@GetMapping("/covers")
	public ResponseEntity<List<CoverDTO>> getAllCovers(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Covers");
		Page<CoverDTO> page = coverService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /covers} : get all the covers related to an Author.
	 *
	 * @param id the id of the Author to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of covers in body.
	 */
	@GetMapping("/covers/author/{id}")
	public ResponseEntity<List<CoverDTO>> getAllCoversByAuthor(@PathVariable Long id) {
		log.debug("REST request to get a list of Covers related to an Author");
		List<CoverDTO> covers = coverService.findAllByAuthorId(id);
		return ResponseEntity.ok().body(covers);
	}

	/**
	 * {@code GET  /covers/:id} : get the "id" cover.
	 *
	 * @param id the id of the coverDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the coverDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/covers/{id}")
	public ResponseEntity<CoverDTO> getCover(@PathVariable Long id) {
		log.debug("REST request to get Cover : {}", id);
		Optional<CoverDTO> coverDTO = coverService.findOne(id);
		return ResponseUtil.wrapOrNotFound(coverDTO);
	}

	/**
	 * {@code DELETE  /covers/:id} : delete the "id" cover.
	 *
	 * @param id the id of the coverDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/covers/{id}")
	public ResponseEntity<Void> deleteCover(@PathVariable Long id) {
		log.debug("REST request to delete Cover : {}", id);
		coverService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
