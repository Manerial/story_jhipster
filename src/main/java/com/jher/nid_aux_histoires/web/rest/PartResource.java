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
import com.jher.nid_aux_histoires.service.PartService;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Part}.
 */
@RestController
@RequestMapping("/api")
public class PartResource {

	private final Logger log = LoggerFactory.getLogger(PartResource.class);

	private static final String ENTITY_NAME = "part";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PartService partService;

	public PartResource(PartService partService) {
		this.partService = partService;
	}

	/**
	 * {@code POST  /parts} : Create a new part.
	 *
	 * @param partDTO the partDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new partDTO, or with status {@code 400 (Bad Request)} if the
	 *         part has already an ID.
	 * @throws Exception
	 */
	@PostMapping("/parts")
	public ResponseEntity<PartDTO> createPart(@RequestBody PartDTO partDTO) throws Exception {
		log.debug("REST request to save Part : {}", partDTO);
		if (partDTO.getId() != null) {
			throw new BadRequestAlertException("A new part cannot already have an ID", ENTITY_NAME, "idexists");
		}
		PartDTO result = partService.save(partDTO);
		return ResponseEntity
				.created(new URI("/api/parts/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /parts} : Updates an existing part.
	 *
	 * @param partDTO the partDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated partDTO, or with status {@code 400 (Bad Request)} if the
	 *         partDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the partDTO couldn't be
	 *         updated.
	 * @throws Exception
	 */
	@PutMapping("/parts")
	public ResponseEntity<PartDTO> updatePart(@RequestBody PartDTO partDTO) throws Exception {
		log.debug("REST request to update Part : {}", partDTO);
		SecurityConfiguration.CheckLoggedUser(partService.findAuthorLoginByPartId(partDTO.getId()));
		if (partDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PartDTO result = partService.save(partDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /parts} : get all the parts.
	 *
	 * @param pageable  the pagination information.
	 * @param eagerload flag to eager load entities from relationships (This is
	 *                  applicable for many-to-many).
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of parts in body.
	 */
	@GetMapping("/parts")
	public ResponseEntity<List<PartDTO>> getAllParts(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Parts");
		Page<PartDTO> page;
		if (SecurityConfiguration.IsAdmin()) {
			page = partService.findAll(pageable);
		} else {
			page = partService.findAllByAuthorLogin(pageable, SecurityConfiguration.getLoggedUser().getName());
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /parts/:id} : get the "id" part.
	 *
	 * @param id the id of the partDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the partDTO, or with status {@code 404 (Not Found)}.
	 * @throws Exception
	 */
	@GetMapping("/parts/{id}")
	public ResponseEntity<PartDTO> getPartById(@PathVariable Long id) throws Exception {
		log.debug("REST request to get Part : {}", id);
		SecurityConfiguration.CheckLoggedUser(partService.findAuthorLoginByPartId(id));
		Optional<PartDTO> partDTO = partService.findOne(id);
		return ResponseUtil.wrapOrNotFound(partDTO);
	}

	/**
	 * {@code DELETE  /parts/:id} : delete the "id" part.
	 *
	 * @param id the id of the partDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/parts/{id}")
	public ResponseEntity<Void> deletePart(@PathVariable Long id) {
		log.debug("REST request to delete Part : {}", id);
		partService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
