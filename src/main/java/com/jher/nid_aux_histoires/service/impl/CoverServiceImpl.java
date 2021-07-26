package com.jher.nid_aux_histoires.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.domain.Cover;
import com.jher.nid_aux_histoires.repository.BookRepository;
import com.jher.nid_aux_histoires.repository.CoverRepository;
import com.jher.nid_aux_histoires.service.CoverService;
import com.jher.nid_aux_histoires.service.dto.CoverDTO;
import com.jher.nid_aux_histoires.service.mapper.CoverMapper;
import com.jher.nid_aux_histoires.service.mapper.CoverMapperLight;

/**
 * Service Implementation for managing {@link Cover}.
 */
@Service
@Transactional
public class CoverServiceImpl implements CoverService {

	private final Logger log = LoggerFactory.getLogger(CoverServiceImpl.class);

	private final CoverRepository coverRepository;

	private final CoverMapper coverMapper;

	private final CoverMapperLight coverMapperLight;

	private final BookRepository bookRepository;

	public CoverServiceImpl(CoverRepository coverRepository, CoverMapper coverMapper, CoverMapperLight coverMapperLight,
			BookRepository bookRepository) {
		this.coverRepository = coverRepository;
		this.coverMapper = coverMapper;
		this.coverMapperLight = coverMapperLight;
		this.bookRepository = bookRepository;
	}

	@Override
	public CoverDTO save(CoverDTO coverDTO) {
		log.debug("Request to save Cover : {}", coverDTO);
		Cover cover = coverMapper.toEntity(coverDTO);
		cover.generatePreview();
		cover = coverRepository.save(cover);
		return coverMapper.toDto(cover);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CoverDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Covers");
		return coverRepository.findAll(pageable).map(coverMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CoverDTO> findAllByOwnerId(Pageable pageable, Long id) {
		log.debug("Request to get all Covers");
		return coverRepository.findAllByOwnerId(pageable, id).map(coverMapperLight::toDto);
	}

	@Override
	public Page<CoverDTO> findAllByOwnerLogin(Pageable pageable, String login) {
		log.debug("Request to get all Covers");
		return coverRepository.findAllByOwnerLogin(pageable, login).map(coverMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CoverDTO> findOne(Long id) {
		log.debug("Request to get Cover : {}", id);
		return coverRepository.findById(id).map(coverMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Cover : {}", id);
		Cover cover = coverRepository.findById(id).get();
		for (Book book : cover.getBookToCovers()) {
			book.setCover(null);
		}
		bookRepository.saveAll(cover.getBookToCovers());
		coverRepository.deleteById(id);

	}
}
