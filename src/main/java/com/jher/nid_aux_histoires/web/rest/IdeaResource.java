package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.service.IdeaService;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Idea}.
 */
@RestController
@RequestMapping("/api")
public class IdeaResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    private static final String ENTITY_NAME = "idea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    public IdeaResource(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    /**
     * {@code POST  /ideas} : Create a new idea.
     *
     * @param ideaDTO the ideaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ideaDTO, or with status {@code 400 (Bad Request)} if the idea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ideas")
    public ResponseEntity<IdeaDTO> createIdea(@RequestBody IdeaDTO ideaDTO) throws URISyntaxException {
        log.debug("REST request to save Idea : {}", ideaDTO);
        if (ideaDTO.getId() != null) {
            throw new BadRequestAlertException("A new idea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdeaDTO result = ideaService.save(ideaDTO);
        return ResponseEntity.created(new URI("/api/ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ideas} : Updates an existing idea.
     *
     * @param ideaDTO the ideaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ideaDTO,
     * or with status {@code 400 (Bad Request)} if the ideaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ideaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ideas")
    public ResponseEntity<IdeaDTO> updateIdea(@RequestBody IdeaDTO ideaDTO) throws URISyntaxException {
        log.debug("REST request to update Idea : {}", ideaDTO);
        if (ideaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdeaDTO result = ideaService.save(ideaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ideaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ideas} : get all the ideas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ideas in body.
     */
    @GetMapping("/ideas")
    public ResponseEntity<List<IdeaDTO>> getAllIdeas(Pageable pageable) {
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
    @GetMapping("/ideas/{id}")
    public ResponseEntity<IdeaDTO> getIdea(@PathVariable Long id) {
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
    @DeleteMapping("/ideas/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long id) {
        log.debug("REST request to delete Idea : {}", id);
        ideaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
