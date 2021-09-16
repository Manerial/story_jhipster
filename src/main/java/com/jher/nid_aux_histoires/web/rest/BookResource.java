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
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.CoverService;
import com.jher.nid_aux_histoires.service.ExportService;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.dto.CoverDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Book}.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

	private final Logger log = LoggerFactory.getLogger(BookResource.class);

	private static final String ENTITY_NAME = "book";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BookService bookService;

	private final CoverService coverService;

	private final ExportService exportService;

	public BookResource(BookService bookService, CoverService coverService, ExportService exportService) {
		this.bookService = bookService;
		this.coverService = coverService;
		this.exportService = exportService;
	}

	/**
	 * {@code POST  /books} : Create a new book.
	 *
	 * @param bookDTO the bookDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new bookDTO, or with status {@code 400 (Bad Request)} if the
	 *         book has already an ID.
	 * @throws Exception bad request
	 */
	@PostMapping("/books")
	public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) throws Exception {
		log.debug("REST request to save Book : {}", bookDTO);
		checkBook(bookDTO);
		if (bookDTO.getId() != null) {
			throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
		}
		BookDTO result = bookService.save(bookDTO);
		return ResponseEntity
				.created(new URI("/api/books/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code POST  /books} : Create a new book.
	 *
	 * @param bookDTO the bookDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new bookDTO, or with status {@code 400 (Bad Request)} if the
	 *         book has already an ID.
	 * @throws Exception bad request
	 */
	@PostMapping("/books/import")
	public ResponseEntity<BookDTO> importBook(@RequestBody BookDTO bookDTO) throws Exception {
		log.debug("REST request to save Book : {}", bookDTO);
		checkBook(bookDTO);
		if (bookDTO.getId() != null) {
			throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
		}
		BookDTO result = bookService.saveBash(bookDTO);
		exportService.exportBook(result.getId());
		return ResponseEntity
				.created(new URI("/api/books/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /books} : Updates an existing book.
	 *
	 * @param bookDTO the bookDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated bookDTO, or with status {@code 400 (Bad Request)} if the
	 *         bookDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the bookDTO couldn't be
	 *         updated.
	 * @throws Exception bad request
	 */
	@PutMapping("/books")
	public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) throws Exception {
		log.debug("REST request to update Book : {}", bookDTO);
		checkBook(bookDTO);
		if (bookDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		BookDTO result = bookService.save(bookDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT /books/visibility/{id}} : Updates an existing book visibility.
	 *
	 * @param id the book id to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated bookDTO, or with status {@code 400 (Bad Request)} if the
	 *         bookDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the bookDTO couldn't be
	 *         updated.
	 * @throws Exception bad request
	 */
	@PutMapping("/books/visibility/{id}")
	public ResponseEntity<BookDTO> updateBookVisibility(@PathVariable Long id) throws Exception {
		Optional<BookDTO> O_book = bookService.findOne(id);
		log.debug("REST request to update Book : {}", O_book);
		if (O_book.isEmpty() || O_book.get().getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		SecurityConfiguration.CheckLoggedUser(O_book.get().getAuthorLogin());
		BookDTO result = bookService.changeVisibility(id);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, O_book.get().getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /books} : get all the books.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of books in body.
	 */
	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>> getAllBooks(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Books");
		Page<BookDTO> page = null;
		if (SecurityConfiguration.IsAdmin()) {
			page = bookService.findAll(pageable);
		} else {
			page = bookService.findAllVisible(pageable);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /books} : get all the books.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of books in body.
	 */
	@GetMapping("/books/favorits")
	public ResponseEntity<List<BookDTO>> getAllBooksFavorits(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {
		log.debug("REST request to get a page of Books");
		Page<BookDTO> page = bookService.findAllFavoritsVisible(pageable, SecurityConfiguration.getUserLogin());

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /books} : get all the books.
	 *
	 * @param pageable the pagination information.
	 * @param login    the author login.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of books in body.
	 */
	@GetMapping("/books/author/{login}")
	public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			@PathVariable String login) {
		log.debug("REST request to get a page of Books");
		Page<BookDTO> page = null;
		try {
			SecurityConfiguration.CheckLoggedUser(login);
			page = bookService.findAllByAuthorId(pageable, login);
		} catch (Exception e) {
			page = bookService.findAllVisibleByAuthorId(pageable, login);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /books/:id} : get the "id" book.
	 *
	 * @param id the id of the bookDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the bookDTO, or with status {@code 404 (Not Found)}.
	 * @throws Exception bad request
	 */
	@GetMapping("/books/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws Exception {
		log.debug("REST request to get Book : {}", id);
		Optional<BookDTO> bookDTO = bookService.findOne(id);
		if (!bookDTO.get().getVisibility()) {
			SecurityConfiguration.CheckLoggedUser(bookDTO.get().getAuthorLogin());
		}
		return ResponseUtil.wrapOrNotFound(bookDTO);
	}

	/**
	 * {@code GET  /books/:id} : get the "id" book.
	 *
	 * @param id the id of the bookDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the bookDTO, or with status {@code 404 (Not Found)}.
	 * @throws Exception bad request
	 */
	@GetMapping("/books/light/{id}")
	public ResponseEntity<BookDTO> getBookLight(@PathVariable Long id) throws Exception {
		log.debug("REST request to get Book : {}", id);
		Optional<BookDTO> O_book = bookService.findOneLight(id);
		if (O_book.isPresent() && !O_book.get().getVisibility()) {
			SecurityConfiguration.CheckLoggedUser(O_book.get().getAuthorLogin());
		}
		return ResponseUtil.wrapOrNotFound(O_book);
	}

	/**
	 * {@code DELETE  /books/:id} : delete the "id" book.
	 *
	 * @param id the id of the bookDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws Exception bad request
	 */
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws Exception {
		log.debug("REST request to delete Book : {}", id);
		Optional<BookDTO> O_book = bookService.findOne(id);
		if (O_book.isPresent()) {
			SecurityConfiguration.CheckLoggedUser(O_book.get().getAuthorLogin());
			bookService.delete(id);
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();
		}
		throw new BadRequestAlertException("The entity book does not exist", ENTITY_NAME, "donotexist");
	}

	private void checkBook(BookDTO bookDTO) throws Exception {
		SecurityConfiguration.CheckLoggedUser(bookDTO.getAuthorLogin());

		// Avoid link with other resources
		if (bookDTO.getCoverId() != null) {
			Optional<CoverDTO> O_cover = coverService.findOne(bookDTO.getCoverId());
			if (O_cover.isPresent()) {
				String login = O_cover.get().getOwnerLogin();
				if (!SecurityConfiguration.IsAdmin() && !login.equals(SecurityConfiguration.getUserLogin())) {
					throw new Exception("You have no access to this resource (Cover : " + bookDTO.getCoverId() + ")");
				}
			}
		}
	}
}
