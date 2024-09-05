package com.jher.nid_aux_histoires.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.repository.ChapterRepository;
import com.jher.nid_aux_histoires.service.ChapterService;
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.service.mapper.ChapterMapper;

/**
 * Service Implementation for managing {@link Chapter}.
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

	private final Logger log = LoggerFactory.getLogger(ChapterServiceImpl.class);

	private final SceneService sceneService;

	private final ChapterRepository chapterRepository;

	private final ChapterMapper chapterMapper;

	public ChapterServiceImpl(ChapterRepository chapterRepository, ChapterMapper chapterMapper,
			SceneService sceneService) {
		this.sceneService = sceneService;
		this.chapterRepository = chapterRepository;
		this.chapterMapper = chapterMapper;
	}

	@Override
	public ChapterDTO save(ChapterDTO chapterDTO) {
		log.debug("Request to save Chapter : {}", chapterDTO);
		Chapter chapter = chapterMapper.toEntity(chapterDTO);
		if (chapter.getNumber() <= 0) {
			chapter.setNumber(findNextNumberForBookId(chapter.getPart().getId()));
		}
		chapter = chapterRepository.save(chapter);
		return chapterMapper.toDto(chapter);
	}

	@Override
	public ChapterDTO saveBash(ChapterDTO chapterDTO) {
		log.debug("Request to save Chapter : {}", chapterDTO);
		Chapter chapter = chapterMapper.toEntity(chapterDTO);
		chapter = chapterRepository.save(chapter);
		for (SceneDTO sceneDTO : chapterDTO.getScenes()) {
			sceneDTO.setChapterId(chapter.getId());
			sceneService.save(sceneDTO);
		}
		return chapterMapper.toDto(chapter);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<ChapterDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Chapters");
		return chapterRepository.findAll(pageable).map(chapterMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ChapterDTO> findAllByAuthorLogin(Pageable pageable, String authorLogin) {
		return chapterRepository.findAllByAuthorLogin(pageable, authorLogin).map(chapterMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public String findAuthorLoginByChapterId(Long id) {
		return chapterRepository.findAuthorLoginByChapterId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ChapterDTO> findOne(Long id) {
		log.debug("Request to get Chapter : {}", id);
		return chapterRepository.findOne(id).map(chapterMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public void delete(Long id) {
		log.debug("Request to delete Chapter : {}", id);
		Chapter chapter = chapterRepository.findById(id).get();
		for (Scene scene : chapter.getScenes()) {
			sceneService.delete(scene.getId());
		}
		chapterRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	private int findNextNumberForBookId(Long bookId) {
		log.debug("Request to get all Parts");
		int bigest = 1;
		for (Chapter chapter : chapterRepository.findAllByPartId(bookId)) {
			bigest = (bigest > chapter.getNumber()) ? bigest : chapter.getNumber() + 1;
		}
		return bigest;
	}
}
