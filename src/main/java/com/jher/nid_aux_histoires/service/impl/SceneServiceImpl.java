package com.jher.nid_aux_histoires.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.repository.SceneRepository;
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.service.mapper.SceneMapper;

/**
 * Service Implementation for managing {@link Scene}.
 */
@Service
@Transactional
public class SceneServiceImpl implements SceneService {

	private final Logger log = LoggerFactory.getLogger(SceneServiceImpl.class);

	private final SceneRepository sceneRepository;

	private final SceneMapper sceneMapper;

	public SceneServiceImpl(SceneRepository sceneRepository, SceneMapper sceneMapper) {
		this.sceneRepository = sceneRepository;
		this.sceneMapper = sceneMapper;
	}

	@Override
	public SceneDTO save(SceneDTO sceneDTO) {
		log.debug("Request to save Scene : {}", sceneDTO);
		Scene scene = sceneMapper.toEntity(sceneDTO);
		if (scene.getNumber() <= 0) {
			scene.setNumber(findNextNumberForBookId(scene.getChapter().getId()));
		}
		scene = sceneRepository.save(scene);
		return sceneMapper.toDto(scene);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SceneDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Scenes");
		return sceneRepository.findAll(pageable).map(sceneMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SceneDTO> findAllByAuthorLogin(Pageable pageable, String login) {
		return sceneRepository.findAllByAuthorLogin(pageable, login).map(sceneMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public String findAuthorLoginBySceneId(Long id) {
		return sceneRepository.findAuthorLoginBySceneId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SceneDTO> findOne(Long id) {
		log.debug("Request to get Scene : {}", id);
		return sceneRepository.findOne(id).map(sceneMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Scene : {}", id);
		sceneRepository.deleteById(id);
	}

	private int findNextNumberForBookId(Long chapterId) {
		log.debug("Request to get all Parts");
		int bigest = 1;
		for (Scene scene : sceneRepository.findAllByChapterId(chapterId)) {
			bigest = (bigest > scene.getNumber()) ? bigest : scene.getNumber() + 1;
		}
		return bigest;
	}
}
