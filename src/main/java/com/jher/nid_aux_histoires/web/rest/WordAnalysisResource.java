package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;
import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;

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
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.WordAnalysis}.
 */
@RestController
@RequestMapping("/api")
public class WordAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(WordAnalysisResource.class);

    private static final String ENTITY_NAME = "wordAnalysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordAnalysisService wordAnalysisService;

    public WordAnalysisResource(WordAnalysisService wordAnalysisService) {
        this.wordAnalysisService = wordAnalysisService;
    }

    /**
     * {@code POST  /word-analyses} : Create a new wordAnalysis.
     *
     * @param wordAnalysisDTO the wordAnalysisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wordAnalysisDTO, or with status {@code 400 (Bad Request)} if the wordAnalysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/word-analyses")
    public ResponseEntity<WordAnalysisDTO> createWordAnalysis(@RequestBody WordAnalysisDTO wordAnalysisDTO) throws URISyntaxException {
        log.debug("REST request to save WordAnalysis : {}", wordAnalysisDTO);
        if (wordAnalysisDTO.getId() != null) {
            throw new BadRequestAlertException("A new wordAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordAnalysisDTO result = wordAnalysisService.save(wordAnalysisDTO);
        return ResponseEntity.created(new URI("/api/word-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /word-analyses} : Updates an existing wordAnalysis.
     *
     * @param wordAnalysisDTO the wordAnalysisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wordAnalysisDTO,
     * or with status {@code 400 (Bad Request)} if the wordAnalysisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wordAnalysisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/word-analyses")
    public ResponseEntity<WordAnalysisDTO> updateWordAnalysis(@RequestBody WordAnalysisDTO wordAnalysisDTO) throws URISyntaxException {
        log.debug("REST request to update WordAnalysis : {}", wordAnalysisDTO);
        if (wordAnalysisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordAnalysisDTO result = wordAnalysisService.save(wordAnalysisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wordAnalysisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /word-analyses} : get all the wordAnalyses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wordAnalyses in body.
     */
    @GetMapping("/word-analyses")
    public ResponseEntity<List<WordAnalysisDTO>> getAllWordAnalyses(Pageable pageable) {
        log.debug("REST request to get a page of WordAnalyses");
        Page<WordAnalysisDTO> page = wordAnalysisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /word-analyses/:id} : get the "id" wordAnalysis.
     *
     * @param id the id of the wordAnalysisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wordAnalysisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/word-analyses/{id}")
    public ResponseEntity<WordAnalysisDTO> getWordAnalysis(@PathVariable Long id) {
        log.debug("REST request to get WordAnalysis : {}", id);
        Optional<WordAnalysisDTO> wordAnalysisDTO = wordAnalysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wordAnalysisDTO);
    }

    /**
     * {@code DELETE  /word-analyses/:id} : delete the "id" wordAnalysis.
     *
     * @param id the id of the wordAnalysisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/word-analyses/{id}")
    public ResponseEntity<Void> deleteWordAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete WordAnalysis : {}", id);
        wordAnalysisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
