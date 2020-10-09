package com.jher.nid_aux_histoires.web.rest.library;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	public SceneResource(SceneService sceneService) {
		this.sceneService = sceneService;
	}

	/**
	 * {@code POST  /scenes} : Create a new scene.
	 *
	 * @param sceneDTO the sceneDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new sceneDTO, or with status {@code 400 (Bad Request)} if
	 *         the scene has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/scenes")
	public ResponseEntity<SceneDTO> createScene(@RequestBody SceneDTO sceneDTO) throws URISyntaxException {
		log.debug("REST request to save Scene : {}", sceneDTO);
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
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/scenes")
	public ResponseEntity<SceneDTO> updateScene(@RequestBody SceneDTO sceneDTO) throws URISyntaxException {
		log.debug("REST request to update Scene : {}", sceneDTO);
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
	 * @param pageable  the pagination information.
	 * @param eagerload flag to eager load entities from relationships (This is
	 *                  applicable for many-to-many).
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of scenes in body.
	 */
	@GetMapping("/scenes")
	public ResponseEntity<List<SceneDTO>> getAllScenes(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
		log.debug("REST request to get a page of Scenes");
		Page<SceneDTO> page;
		if (eagerload) {
			page = sceneService.findAllWithEagerRelationships(pageable);
		} else {
			page = sceneService.findAll(pageable);
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
	 */
	@GetMapping("/scenes/{id}")
	public ResponseEntity<SceneDTO> getScene(@PathVariable Long id) {
		log.debug("REST request to get Scene : {}", id);
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
}
