package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.repository.BookStatusRepository;
import com.jher.nid_aux_histoires.service.BookStatusService;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;
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
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.BookStatus}.
 */
@RestController
@RequestMapping("/api/book-statuses")
public class BookStatusResource {

    private final Logger log = LoggerFactory.getLogger(BookStatusResource.class);

    private static final String ENTITY_NAME = "bookStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookStatusService bookStatusService;

    private final BookStatusRepository bookStatusRepository;

    public BookStatusResource(BookStatusService bookStatusService, BookStatusRepository bookStatusRepository) {
        this.bookStatusService = bookStatusService;
        this.bookStatusRepository = bookStatusRepository;
    }

    /**
     * {@code POST  /book-statuses} : Create a new bookStatus.
     *
     * @param bookStatusDTO the bookStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookStatusDTO, or with status {@code 400 (Bad Request)} if the bookStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BookStatusDTO> createBookStatus(@RequestBody BookStatusDTO bookStatusDTO) throws URISyntaxException {
        log.debug("REST request to save BookStatus : {}", bookStatusDTO);
        if (bookStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookStatusDTO result = bookStatusService.save(bookStatusDTO);
        return ResponseEntity
            .created(new URI("/api/book-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-statuses/:id} : Updates an existing bookStatus.
     *
     * @param id the id of the bookStatusDTO to save.
     * @param bookStatusDTO the bookStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookStatusDTO,
     * or with status {@code 400 (Bad Request)} if the bookStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookStatusDTO> updateBookStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookStatusDTO bookStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BookStatus : {}, {}", id, bookStatusDTO);
        if (bookStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookStatusDTO result = bookStatusService.update(bookStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /book-statuses/:id} : Partial updates given fields of an existing bookStatus, field will ignore if it is null
     *
     * @param id the id of the bookStatusDTO to save.
     * @param bookStatusDTO the bookStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookStatusDTO,
     * or with status {@code 400 (Bad Request)} if the bookStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BookStatusDTO> partialUpdateBookStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookStatusDTO bookStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BookStatus partially : {}, {}", id, bookStatusDTO);
        if (bookStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookStatusDTO> result = bookStatusService.partialUpdate(bookStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /book-statuses} : get all the bookStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookStatuses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BookStatusDTO>> getAllBookStatuses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BookStatuses");
        Page<BookStatusDTO> page = bookStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-statuses/:id} : get the "id" bookStatus.
     *
     * @param id the id of the bookStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookStatusDTO> getBookStatus(@PathVariable("id") Long id) {
        log.debug("REST request to get BookStatus : {}", id);
        Optional<BookStatusDTO> bookStatusDTO = bookStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookStatusDTO);
    }

    /**
     * {@code DELETE  /book-statuses/:id} : delete the "id" bookStatus.
     *
     * @param id the id of the bookStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookStatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete BookStatus : {}", id);
        bookStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
