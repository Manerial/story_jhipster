package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.repository.PartRepository;
import com.jher.nid_aux_histoires.service.PartService;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
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
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Part}.
 */
@RestController
@RequestMapping("/api/parts")
public class PartResource {

    private final Logger log = LoggerFactory.getLogger(PartResource.class);

    private static final String ENTITY_NAME = "part";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartService partService;

    private final PartRepository partRepository;

    public PartResource(PartService partService, PartRepository partRepository) {
        this.partService = partService;
        this.partRepository = partRepository;
    }

    /**
     * {@code POST  /parts} : Create a new part.
     *
     * @param partDTO the partDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partDTO, or with status {@code 400 (Bad Request)} if the part has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartDTO> createPart(@RequestBody PartDTO partDTO) throws URISyntaxException {
        log.debug("REST request to save Part : {}", partDTO);
        if (partDTO.getId() != null) {
            throw new BadRequestAlertException("A new part cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartDTO result = partService.save(partDTO);
        return ResponseEntity
            .created(new URI("/api/parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parts/:id} : Updates an existing part.
     *
     * @param id the id of the partDTO to save.
     * @param partDTO the partDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partDTO,
     * or with status {@code 400 (Bad Request)} if the partDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartDTO> updatePart(@PathVariable(value = "id", required = false) final Long id, @RequestBody PartDTO partDTO)
        throws URISyntaxException {
        log.debug("REST request to update Part : {}, {}", id, partDTO);
        if (partDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartDTO result = partService.update(partDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parts/:id} : Partial updates given fields of an existing part, field will ignore if it is null
     *
     * @param id the id of the partDTO to save.
     * @param partDTO the partDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partDTO,
     * or with status {@code 400 (Bad Request)} if the partDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartDTO> partialUpdatePart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartDTO partDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Part partially : {}, {}", id, partDTO);
        if (partDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartDTO> result = partService.partialUpdate(partDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parts} : get all the parts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartDTO>> getAllParts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Parts");
        Page<PartDTO> page;
        if (eagerload) {
            page = partService.findAllWithEagerRelationships(pageable);
        } else {
            page = partService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parts/:id} : get the "id" part.
     *
     * @param id the id of the partDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartDTO> getPart(@PathVariable("id") Long id) {
        log.debug("REST request to get Part : {}", id);
        Optional<PartDTO> partDTO = partService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partDTO);
    }

    /**
     * {@code DELETE  /parts/:id} : delete the "id" part.
     *
     * @param id the id of the partDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable("id") Long id) {
        log.debug("REST request to delete Part : {}", id);
        partService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
