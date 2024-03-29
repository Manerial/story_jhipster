package com.jher.nid_aux_histoires.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
import com.jher.nid_aux_histoires.service.CommentService;
import com.jher.nid_aux_histoires.service.UserService;
import com.jher.nid_aux_histoires.service.dto.CommentDTO;
import com.jher.nid_aux_histoires.service.dto.UserDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.jher.nid_aux_histoires.domain.Comment}.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

	private final Logger log = LoggerFactory.getLogger(CommentResource.class);

	private static final String ENTITY_NAME = "comment";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CommentService commentService;
	private final UserService userService;

	public CommentResource(CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.userService = userService;
	}

	/**
	 * {@code POST  /comments} : Create a new comment.
	 *
	 * @param commentDTO the commentDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new commentDTO, or with status {@code 400 (Bad Request)} if
	 *         the comment has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) throws URISyntaxException {
		log.debug("REST request to save Comment : {}", commentDTO);
		if (commentDTO.getId() != null) {
			throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
		}
		setUserLogin(commentDTO);
		CommentDTO result = commentService.save(commentDTO);
		return ResponseEntity
				.created(new URI("/api/comments/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /comments} : Updates an existing comment.
	 *
	 * @param commentDTO the commentDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated commentDTO, or with status {@code 400 (Bad Request)} if
	 *         the commentDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the commentDTO couldn't be
	 *         updated.
	 * @throws Exception bad request
	 */
	@PutMapping("/comments")
	public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO) throws Exception {
		SecurityConfiguration.CheckLoggedUser(commentDTO.getUserLogin());
		log.debug("REST request to update Comment : {}", commentDTO);
		if (commentDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		setUserLogin(commentDTO);
		CommentDTO result = commentService.save(commentDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /comments} : get all the comments.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of comments in body.
	 */
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDTO>> getAllComments(Pageable pageable) {
		log.debug("REST request to get a page of Comments");
		Page<CommentDTO> page = commentService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /comments/:id} : get the "id" comment.
	 *
	 * @param id the id of the commentDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the commentDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/comments/book/{id}")
	public ResponseEntity<List<CommentDTO>> getAllCommentsByBookId(@PathVariable Long id) {
		log.debug("REST request to get Comment : {}", id);
		List<CommentDTO> commentDTO = commentService.findAllByBookId(id);
		return ResponseEntity.ok().body(commentDTO);
	}

	/**
	 * {@code GET  /comments/:id} : get the "id" comment.
	 *
	 * @param id the id of the commentDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the commentDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/comments/{id}")
	public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
		log.debug("REST request to get Comment : {}", id);
		Optional<CommentDTO> commentDTO = commentService.findOne(id);
		return ResponseUtil.wrapOrNotFound(commentDTO);
	}

	/**
	 * {@code DELETE  /comments/:id} : delete the "id" comment.
	 *
	 * @param id the id of the commentDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws Exception bad request
	 */
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long id) throws Exception {
		log.debug("REST request to delete Comment : {}", id);
		Optional<CommentDTO> optComment = commentService.findOne(id);
		if (optComment.isPresent()) {
			CommentDTO commentDTO = optComment.get();
			SecurityConfiguration.CheckLoggedUser(commentDTO.getUserLogin());
			commentService.delete(id);
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();
		}
		throw new BadRequestAlertException("The entity comment does not exist", ENTITY_NAME, "donotexist");
	}

	private void setUserLogin(CommentDTO commentDTO) {
		String login = SecurityConfiguration.getUserLogin();
		UserDTO user = userService.getUserLightByLogin(login);
		commentDTO.setUserId(user.getId());
	}
}
