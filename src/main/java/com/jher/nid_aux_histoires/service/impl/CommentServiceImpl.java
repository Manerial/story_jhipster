package com.jher.nid_aux_histoires.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Comment;
import com.jher.nid_aux_histoires.repository.CommentRepository;
import com.jher.nid_aux_histoires.service.CommentService;
import com.jher.nid_aux_histoires.service.dto.CommentDTO;
import com.jher.nid_aux_histoires.service.mapper.CommentMapper;

/**
 * Service Implementation for managing {@link Comment}.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;

	public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
		this.commentRepository = commentRepository;
		this.commentMapper = commentMapper;
	}

	@Override
	public CommentDTO save(CommentDTO commentDTO) {
		log.debug("Request to save Comment : {}", commentDTO);
		Comment comment = commentMapper.toEntity(commentDTO);
		comment = commentRepository.save(comment);
		return commentMapper.toDto(comment);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CommentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Comments");
		return commentRepository.findAll(pageable).map(commentMapper::toDto);
	}

	@Override
	public List<CommentDTO> findAllByBookId(Long id) {
		return commentMapper.toDto(commentRepository.findAllByBookId(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CommentDTO> findOne(Long id) {
		log.debug("Request to get Comment : {}", id);
		return commentRepository.findById(id).map(commentMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Comment : {}", id);
		commentRepository.deleteById(id);
		commentRepository.flush();
	}
}
