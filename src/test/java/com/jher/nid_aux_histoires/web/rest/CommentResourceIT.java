package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Comment;
import com.jher.nid_aux_histoires.repository.CommentRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.dto.CommentDTO;
import com.jher.nid_aux_histoires.service.mapper.CommentMapper;

/**
 * Integration tests for the {@link CommentResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentResourceIT {

	private static final String DEFAULT_TEXT = "AAAAAAAAAA";
	private static final String UPDATED_TEXT = "BBBBBBBBBB";

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restCommentMockMvc;

	private Comment comment;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Comment createEntity(EntityManager em) {
		Comment comment = new Comment().text(DEFAULT_TEXT);
		return comment;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Comment createUpdatedEntity(EntityManager em) {
		Comment comment = new Comment().text(UPDATED_TEXT);
		return comment;
	}

	@BeforeEach
	public void initTest() {
		comment = createEntity(em);
	}

	@Test
	@Transactional
	public void createComment() throws Exception {
		int databaseSizeBeforeCreate = commentRepository.findAll().size();
		// Create the Comment
		CommentDTO commentDTO = commentMapper.toDto(comment);
		restCommentMockMvc.perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(commentDTO))).andExpect(status().isCreated());

		// Validate the Comment in the database
		List<Comment> commentList = commentRepository.findAll();
		assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
		Comment testComment = commentList.get(commentList.size() - 1);
		assertThat(testComment.getText()).isEqualTo(DEFAULT_TEXT);
	}

	@Test
	@Transactional
	public void createCommentWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = commentRepository.findAll().size();

		// Create the Comment with an existing ID
		comment.setId(1L);
		CommentDTO commentDTO = commentMapper.toDto(comment);

		// An entity with an existing ID cannot be created, so this API call must fail
		restCommentMockMvc.perform(post("/api/comments").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(commentDTO))).andExpect(status().isBadRequest());

		// Validate the Comment in the database
		List<Comment> commentList = commentRepository.findAll();
		assertThat(commentList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllComments() throws Exception {
		// Initialize the database
		commentRepository.saveAndFlush(comment);

		// Get all the commentList
		restCommentMockMvc.perform(get("/api/comments?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
				.andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
	}

	@Test
	@Transactional
	public void getComment() throws Exception {
		// Initialize the database
		commentRepository.saveAndFlush(comment);

		// Get the comment
		restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(comment.getId().intValue()))
				.andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
	}

	@Test
	@Transactional
	public void getNonExistingComment() throws Exception {
		// Get the comment
		restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	// @Test
	// @Transactional
	public void updateComment() throws Exception {
		// Initialize the database
		commentRepository.saveAndFlush(comment);

		int databaseSizeBeforeUpdate = commentRepository.findAll().size();

		// Update the comment
		Comment updatedComment = commentRepository.findById(comment.getId()).get();
		// Disconnect from session so that the updates on updatedComment are not
		// directly saved in db
		em.detach(updatedComment);
		updatedComment.text(UPDATED_TEXT);

		CommentDTO commentDTO = commentMapper.toDto(updatedComment);
		restCommentMockMvc.perform(put("/api/comments").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(commentDTO))).andExpect(status().isOk());

		// Validate the Comment in the database
		List<Comment> commentList = commentRepository.findAll();
		assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
		Comment testComment = commentList.get(commentList.size() - 1);
		assertThat(testComment.getText()).isEqualTo(UPDATED_TEXT);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateNonExistingComment() throws Exception {
		int databaseSizeBeforeUpdate = commentRepository.findAll().size();

		// Create the Comment
		CommentDTO commentDTO = commentMapper.toDto(comment);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restCommentMockMvc.perform(put("/api/comments").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(commentDTO))).andExpect(status().isBadRequest());

		// Validate the Comment in the database
		List<Comment> commentList = commentRepository.findAll();
		assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void deleteComment() throws Exception {
		// Initialize the database
		commentRepository.saveAndFlush(comment);

		int databaseSizeBeforeDelete = commentRepository.findAll().size();

		// Delete the comment
		restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Comment> commentList = commentRepository.findAll();
		assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
