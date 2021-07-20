package com.jher.nid_aux_histoires.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.BookStatus;
import com.jher.nid_aux_histoires.repository.BookStatusRepository;
import com.jher.nid_aux_histoires.service.BookStatusService;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;
import com.jher.nid_aux_histoires.service.mapper.BookStatusMapper;

/**
 * Service Implementation for managing {@link BookStatus}.
 */
@Service
@Transactional
public class BookStatusServiceImpl implements BookStatusService {

	private final Logger log = LoggerFactory.getLogger(BookStatusServiceImpl.class);

	private final BookStatusRepository bookStatusRepository;

	private final BookStatusMapper bookStatusMapper;

	public BookStatusServiceImpl(BookStatusRepository bookStatusRepository, BookStatusMapper bookStatusMapper) {
		this.bookStatusRepository = bookStatusRepository;
		this.bookStatusMapper = bookStatusMapper;
	}

	@Override
	public BookStatusDTO save(BookStatusDTO bookStatusDTO) {
		log.debug("Request to save BookStatus : {}", bookStatusDTO);
		BookStatus bookStatus = bookStatusMapper.toEntity(bookStatusDTO);
		bookStatus = bookStatusRepository.save(bookStatus);
		return bookStatusMapper.toDto(bookStatus);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookStatusDTO> findAll(Pageable pageable) {
		log.debug("Request to get all BookStatus");
		return bookStatusRepository.findAll(pageable).map(bookStatusMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookStatusDTO> findAllByUser(Pageable pageable, String login) {
		log.debug("Request to get all BookStatus by user login");
		return bookStatusRepository.findAllByUserLogin(pageable, login).map(bookStatusMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BookStatusDTO> findOne(Long id) {
		log.debug("Request to get BookStatus : {}", id);
		return bookStatusRepository.findById(id).map(bookStatusMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete BookStatus : {}", id);
		bookStatusRepository.deleteById(id);
	}
}
