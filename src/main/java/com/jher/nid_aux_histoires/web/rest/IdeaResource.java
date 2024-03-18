package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.IdeaService;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
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
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Idea}.
 */
@RestController
@RequestMapping("/api/ideas")
public class IdeaResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    private static final String ENTITY_NAME = "idea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    private final IdeaRepository ideaRepository;

    public IdeaResource(IdeaService ideaService, IdeaRepository ideaRepository) {
        this.ideaService = ideaService;
        this.ideaRepository = ideaRepository;
    }

    /**
     * {@code POST  /ideas} : Create a new idea.
     *
     * @param ideaDTO the ideaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ideaDTO, or with status {@code 400 (Bad Request)} if the idea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IdeaDTO> createIdea(@RequestBody IdeaDTO ideaDTO) throws URISyntaxException {
        log.debug("REST request to save Idea : {}", ideaDTO);
        if (ideaDTO.getId() != null) {
            throw new BadRequestAlertException("A new idea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdeaDTO result = ideaService.save(ideaDTO);
        return ResponseEntity
            .created(new URI("/api/ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ideas/:id} : Updates an existing idea.
     *
     * @param id the id of the ideaDTO to save.
     * @param ideaDTO the ideaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ideaDTO,
     * or with status {@code 400 (Bad Request)} if the ideaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ideaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IdeaDTO> updateIdea(@PathVariable(value = "id", required = false) final Long id, @RequestBody IdeaDTO ideaDTO)
        throws URISyntaxException {
        log.debug("REST request to update Idea : {}, {}", id, ideaDTO);
        if (ideaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ideaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ideaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IdeaDTO result = ideaService.update(ideaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ideaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ideas/:id} : Partial updates given fields of an existing idea, field will ignore if it is null
     *
     * @param id the id of the ideaDTO to save.
     * @param ideaDTO the ideaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ideaDTO,
     * or with status {@code 400 (Bad Request)} if the ideaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ideaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ideaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IdeaDTO> partialUpdateIdea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IdeaDTO ideaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Idea partially : {}, {}", id, ideaDTO);
        if (ideaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ideaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ideaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IdeaDTO> result = ideaService.partialUpdate(ideaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ideaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ideas} : get all the ideas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ideas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IdeaDTO>> getAllIdeas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ideas");
        Page<IdeaDTO> page = ideaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ideas/:id} : get the "id" idea.
     *
     * @param id the id of the ideaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ideaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IdeaDTO> getIdea(@PathVariable("id") Long id) {
        log.debug("REST request to get Idea : {}", id);
        Optional<IdeaDTO> ideaDTO = ideaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ideaDTO);
    }

    /**
     * {@code DELETE  /ideas/:id} : delete the "id" idea.
     *
     * @param id the id of the ideaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable("id") Long id) {
        log.debug("REST request to delete Idea : {}", id);
        ideaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
