package com.jher.nid_aux_histoires.service.impl;

import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.repository.SceneRepository;
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.service.mapper.SceneMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jher.nid_aux_histoires.domain.Scene}.
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
        scene = sceneRepository.save(scene);
        return sceneMapper.toDto(scene);
    }

    @Override
    public SceneDTO update(SceneDTO sceneDTO) {
        log.debug("Request to update Scene : {}", sceneDTO);
        Scene scene = sceneMapper.toEntity(sceneDTO);
        scene = sceneRepository.save(scene);
        return sceneMapper.toDto(scene);
    }

    @Override
    public Optional<SceneDTO> partialUpdate(SceneDTO sceneDTO) {
        log.debug("Request to partially update Scene : {}", sceneDTO);

        return sceneRepository
            .findById(sceneDTO.getId())
            .map(existingScene -> {
                sceneMapper.partialUpdate(existingScene, sceneDTO);

                return existingScene;
            })
            .map(sceneRepository::save)
            .map(sceneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SceneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scenes");
        return sceneRepository.findAll(pageable).map(sceneMapper::toDto);
    }

    public Page<SceneDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sceneRepository.findAllWithEagerRelationships(pageable).map(sceneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SceneDTO> findOne(Long id) {
        log.debug("Request to get Scene : {}", id);
        return sceneRepository.findOneWithEagerRelationships(id).map(sceneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scene : {}", id);
        sceneRepository.deleteById(id);
    }
}
