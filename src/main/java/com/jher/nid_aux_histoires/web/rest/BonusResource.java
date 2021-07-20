package com.jher.nid_aux_histoires.web.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.jher.nid_aux_histoires.service.BonusService;
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Bonus}.
 */
@RestController
@RequestMapping("/api")
public class BonusResource {

	private final Logger log = LoggerFactory.getLogger(BonusResource.class);

	private static final String ENTITY_NAME = "bonus";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BonusService bonusService;

	private final BookService bookService;

	public BonusResource(BonusService bonusService, BookService bookService) {
		this.bonusService = bonusService;
		this.bookService = bookService;
	}

	/**
	 * {@code POST  /bonuses} : Create a new bonus.
	 *
	 * @param bonusDTO the bonusDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new bonusDTO, or with status {@code 400 (Bad Request)} if
	 *         the bonus has already an ID.
	 * @throws Exception
	 */
	@PostMapping(path = "/bonuses")
	public ResponseEntity<BonusDTO> createBonus(@RequestBody BonusDTO bonusDTO) throws Exception {
		log.debug("REST request to save Bonus : {}", bonusDTO);
		checkBonus(bonusDTO);
		if (bonusDTO.getId() != null) {
			throw new BadRequestAlertException("A new bonus cannot already have an ID", ENTITY_NAME, "idexists");
		}
		BonusDTO result = bonusService.save(bonusDTO);
		return ResponseEntity
				.created(new URI("/api/bonuses/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /bonuses} : Updates an existing bonus.
	 *
	 * @param bonusDTO the bonusDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated bonusDTO, or with status {@code 400 (Bad Request)} if the
	 *         bonusDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the bonusDTO couldn't be
	 *         updated.
	 * @throws Exception
	 */
	@PutMapping(path = "/bonuses")
	public ResponseEntity<BonusDTO> updateBonus(@RequestBody BonusDTO bonusDTO) throws Exception {
		log.debug("REST request to update Bonus : {}", bonusDTO);
		checkBonus(bonusDTO);
		if (bonusDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		BonusDTO result = bonusService.save(bonusDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonusDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /bonuses} : get all the bonuses.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of bonuses in body.
	 */
	@GetMapping("/bonuses")
	public ResponseEntity<List<BonusDTO>> getAllBonuses(Pageable pageable) {
		log.debug("REST request to get a page of Bonuses");
		Page<BonusDTO> page;
		if (SecurityConfiguration.IsAdmin()) {
			page = bonusService.findAll(pageable);
		} else {
			page = bonusService.findAllByOwnerLogin(pageable, SecurityConfiguration.getUserLogin());
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /bonuses/:id} : get the "id" bonus.
	 *
	 * @param id the id of the bonusDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the bonusDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/bonuses/{id}")
	public ResponseEntity<BonusDTO> getBonus(@PathVariable Long id) {
		log.debug("REST request to get Bonus : {}", id);
		Optional<BonusDTO> bonusDTO = bonusService.findOne(id);
		return ResponseUtil.wrapOrNotFound(bonusDTO);
	}

	/**
	 * {@code GET  /bonuses} : get all the bonuses related to a Book.
	 *
	 * @param id the id of the Book to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of bonuses in body.
	 */
	@GetMapping("/bonuses/book/{id}")
	public ResponseEntity<List<BonusDTO>> getAllBonusesByBook(@PathVariable Long id) {
		log.debug("REST request to get a list of Bonuses related to a Book");
		List<BonusDTO> bonuses = bonusService.findAllByBookId(id);
		return ResponseEntity.ok().body(bonuses);
	}

	/**
	 * {@code DELETE  /bonuses/:id} : delete the "id" bonus.
	 *
	 * @param id the id of the bonusDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws Exception
	 */
	@DeleteMapping("/bonuses/{id}")
	public ResponseEntity<Void> deleteBonus(@PathVariable Long id) throws Exception {
		log.debug("REST request to delete Bonus : {}", id);
		BonusDTO bonusDTO = bonusService.findOne(id).get();
		SecurityConfiguration.CheckLoggedUser(bonusDTO.getOwnerLogin());
		bonusService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	private void checkBonus(BonusDTO bonusDTO) throws Exception {
		SecurityConfiguration.CheckLoggedUser(bonusDTO.getOwnerLogin());

		Long bookId = bonusDTO.getBookId();
		String login = bookService.findOneLight(bookId).get().getAuthorLogin();
		if (!SecurityConfiguration.IsAdmin() && !login.equals(SecurityConfiguration.getUserLogin())) {
			throw new Exception("You have no access to this resource (Book : " + bookId + ")");
		}
	}
}
