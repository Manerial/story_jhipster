package com.jher.nid_aux_histoires.web.rest;

import java.net.URI;
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

import com.jher.nid_aux_histoires.config.SecurityConfiguration;
import com.jher.nid_aux_histoires.service.ChapterService;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.jher.nid_aux_histoires.domain.Chapter}.
 */
@RestController
@RequestMapping("/api")
public class ChapterResource {

	private final Logger log = LoggerFactory.getLogger(ChapterResource.class);

	private static final String ENTITY_NAME = "chapter";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ChapterService chapterService;

	public ChapterResource(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	/**
	 * {@code POST  /chapters} : Create a new chapter.
	 *
	 * @param chapterDTO the chapterDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new chapterDTO, or with status {@code 400 (Bad Request)} if
	 *         the chapter has already an ID.
	 * @throws Exception
	 */
	@PostMapping("/chapters")
	public ResponseEntity<ChapterDTO> createChapter(@RequestBody ChapterDTO chapterDTO) throws Exception {
		log.debug("REST request to save Chapter : {}", chapterDTO);
		if (chapterDTO.getId() != null) {
			throw new BadRequestAlertException("A new chapter cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ChapterDTO result = chapterService.save(chapterDTO);
		return ResponseEntity
				.created(new URI("/api/chapters/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /chapters} : Updates an existing chapter.
	 *
	 * @param chapterDTO the chapterDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated chapterDTO, or with status {@code 400 (Bad Request)} if
	 *         the chapterDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the chapterDTO couldn't be
	 *         updated.
	 * @throws Exception
	 */
	@PutMapping("/chapters")
	public ResponseEntity<ChapterDTO> updateChapter(@RequestBody ChapterDTO chapterDTO) throws Exception {
		log.debug("REST request to update Chapter : {}", chapterDTO);
		SecurityConfiguration.CheckLoggedUser(chapterService.findAuthorLoginByChapterId(chapterDTO.getId()));
		if (chapterDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ChapterDTO result = chapterService.save(chapterDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chapterDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /chapters} : get all the chapters.
	 *
	 * @param pageable  the pagination information.
	 * @param eagerload flag to eager load entities from relationships (This is
	 *                  applicable for many-to-many).
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of chapters in body.
	 */
	@GetMapping("/chapters")
	public ResponseEntity<List<ChapterDTO>> getAllChapters(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Chapters");
		Page<ChapterDTO> page;
		if (SecurityConfiguration.IsAdmin()) {
			page = chapterService.findAll(pageable);
		} else {
			page = chapterService.findAllByAuthorLogin(pageable, SecurityConfiguration.getLoggedUser().getName());
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /chapters/:id} : get the "id" chapter.
	 *
	 * @param id the id of the chapterDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the chapterDTO, or with status {@code 404 (Not Found)}.
	 * @throws Exception
	 */
	@GetMapping("/chapters/{id}")
	public ResponseEntity<ChapterDTO> getChapterById(@PathVariable Long id) throws Exception {
		log.debug("REST request to get Chapter : {}", id);
		SecurityConfiguration.CheckLoggedUser(chapterService.findAuthorLoginByChapterId(id));
		Optional<ChapterDTO> chapterDTO = chapterService.findOne(id);
		return ResponseUtil.wrapOrNotFound(chapterDTO);
	}

	/**
	 * {@code DELETE  /chapters/:id} : delete the "id" chapter.
	 *
	 * @param id the id of the chapterDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/chapters/{id}")
	public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
		log.debug("REST request to delete Chapter : {}", id);
		chapterService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
