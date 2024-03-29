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
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Scene}.
 */
@RestController
@RequestMapping("/api")
public class SceneResource {

	private final Logger log = LoggerFactory.getLogger(SceneResource.class);

	private static final String ENTITY_NAME = "scene";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final SceneService sceneService;

	private final ChapterService chapterService;

	public SceneResource(SceneService sceneService, ChapterService chapterService) {
		this.sceneService = sceneService;
		this.chapterService = chapterService;
	}

	/**
	 * {@code POST  /scenes} : Create a new scene.
	 *
	 * @param sceneDTO the sceneDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new sceneDTO, or with status {@code 400 (Bad Request)} if
	 *         the scene has already an ID.
	 * @throws Exception bad request
	 */
	@PostMapping("/scenes")
	public ResponseEntity<SceneDTO> createScene(@RequestBody SceneDTO sceneDTO) throws Exception {
		log.debug("REST request to save Scene : {}", sceneDTO);
		checkScene(sceneDTO);
		if (sceneDTO.getId() != null) {
			throw new BadRequestAlertException("A new scene cannot already have an ID", ENTITY_NAME, "idexists");
		}
		SceneDTO result = sceneService.save(sceneDTO);
		return ResponseEntity
				.created(new URI("/api/scenes/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /scenes} : Updates an existing scene.
	 *
	 * @param sceneDTO the sceneDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated sceneDTO, or with status {@code 400 (Bad Request)} if the
	 *         sceneDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the sceneDTO couldn't be
	 *         updated.
	 * @throws Exception bad request
	 */
	@PutMapping("/scenes")
	public ResponseEntity<SceneDTO> updateScene(@RequestBody SceneDTO sceneDTO) throws Exception {
		log.debug("REST request to update Scene : {}", sceneDTO);
		checkScene(sceneDTO);
		if (sceneDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		SceneDTO result = sceneService.save(sceneDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sceneDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /scenes} : get all the scenes.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of scenes in body.
	 */
	@GetMapping("/scenes")
	public ResponseEntity<List<SceneDTO>> getAllScenes(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Scenes");
		Page<SceneDTO> page;
		if (SecurityConfiguration.IsAdmin()) {
			page = sceneService.findAll(pageable);
		} else {
			page = sceneService.findAllByAuthorLogin(pageable, SecurityConfiguration.getUserLogin());
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /scenes/:id} : get the "id" scene.
	 *
	 * @param id the id of the sceneDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the sceneDTO, or with status {@code 404 (Not Found)}.
	 * @throws Exception bad request
	 */
	@GetMapping("/scenes/{id}")
	public ResponseEntity<SceneDTO> getSceneById(@PathVariable Long id) throws Exception {
		log.debug("REST request to get Scene : {}", id);
		SecurityConfiguration.CheckLoggedUser(sceneService.findAuthorLoginBySceneId(id));
		Optional<SceneDTO> sceneDTO = sceneService.findOne(id);
		return ResponseUtil.wrapOrNotFound(sceneDTO);
	}

	/**
	 * {@code DELETE  /scenes/:id} : delete the "id" scene.
	 *
	 * @param id the id of the sceneDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/scenes/{id}")
	public ResponseEntity<Void> deleteScene(@PathVariable Long id) {
		log.debug("REST request to delete Scene : {}", id);
		sceneService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	private void checkScene(SceneDTO sceneDTO) throws Exception {
		if (sceneDTO.getId() != null) {
			SecurityConfiguration.CheckLoggedUser(sceneService.findAuthorLoginBySceneId(sceneDTO.getId()));
		}

		Long chapterId = sceneDTO.getChapterId();
		String login = chapterService.findAuthorLoginByChapterId(chapterId);
		if (!SecurityConfiguration.IsAdmin() && !login.equals(SecurityConfiguration.getUserLogin())) {
			throw new Exception("You have no access to this resource (Chapter : " + chapterId + ")");
		}
	}
}
