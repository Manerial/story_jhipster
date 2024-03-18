package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.repository.SceneRepository;
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Scene}.
 */
@RestController
@RequestMapping("/api/scenes")
public class SceneResource {

    private final Logger log = LoggerFactory.getLogger(SceneResource.class);

    private static final String ENTITY_NAME = "scene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SceneService sceneService;

    private final SceneRepository sceneRepository;

    public SceneResource(SceneService sceneService, SceneRepository sceneRepository) {
        this.sceneService = sceneService;
        this.sceneRepository = sceneRepository;
    }

    /**
     * {@code POST  /scenes} : Create a new scene.
     *
     * @param sceneDTO the sceneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sceneDTO, or with status {@code 400 (Bad Request)} if the scene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SceneDTO> createScene(@RequestBody SceneDTO sceneDTO) throws URISyntaxException {
        log.debug("REST request to save Scene : {}", sceneDTO);
        if (sceneDTO.getId() != null) {
            throw new BadRequestAlertException("A new scene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SceneDTO result = sceneService.save(sceneDTO);
        return ResponseEntity
            .created(new URI("/api/scenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scenes/:id} : Updates an existing scene.
     *
     * @param id the id of the sceneDTO to save.
     * @param sceneDTO the sceneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sceneDTO,
     * or with status {@code 400 (Bad Request)} if the sceneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sceneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SceneDTO> updateScene(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SceneDTO sceneDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Scene : {}, {}", id, sceneDTO);
        if (sceneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sceneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sceneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SceneDTO result = sceneService.update(sceneDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sceneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scenes/:id} : Partial updates given fields of an existing scene, field will ignore if it is null
     *
     * @param id the id of the sceneDTO to save.
     * @param sceneDTO the sceneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sceneDTO,
     * or with status {@code 400 (Bad Request)} if the sceneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sceneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sceneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SceneDTO> partialUpdateScene(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SceneDTO sceneDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scene partially : {}, {}", id, sceneDTO);
        if (sceneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sceneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sceneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SceneDTO> result = sceneService.partialUpdate(sceneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sceneDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /scenes} : get all the scenes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scenes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SceneDTO>> getAllScenes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Scenes");
        Page<SceneDTO> page;
        if (eagerload) {
            page = sceneService.findAllWithEagerRelationships(pageable);
        } else {
            page = sceneService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scenes/:id} : get the "id" scene.
     *
     * @param id the id of the sceneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sceneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SceneDTO> getScene(@PathVariable("id") Long id) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScene(@PathVariable("id") Long id) {
        log.debug("REST request to delete Scene : {}", id);
        sceneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
