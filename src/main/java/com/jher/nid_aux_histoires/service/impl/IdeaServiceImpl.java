package com.jher.nid_aux_histoires.service.impl;

import com.jher.nid_aux_histoires.service.IdeaService;
import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import com.jher.nid_aux_histoires.service.mapper.IdeaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Idea}.
 */
@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);

    private final IdeaRepository ideaRepository;

    private final IdeaMapper ideaMapper;

    public IdeaServiceImpl(IdeaRepository ideaRepository, IdeaMapper ideaMapper) {
        this.ideaRepository = ideaRepository;
        this.ideaMapper = ideaMapper;
    }

    @Override
    public IdeaDTO save(IdeaDTO ideaDTO) {
        log.debug("Request to save Idea : {}", ideaDTO);
        Idea idea = ideaMapper.toEntity(ideaDTO);
        idea = ideaRepository.save(idea);
        return ideaMapper.toDto(idea);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IdeaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ideas");
        return ideaRepository.findAll(pageable)
            .map(ideaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IdeaDTO> findOne(Long id) {
        log.debug("Request to get Idea : {}", id);
        return ideaRepository.findById(id)
            .map(ideaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Idea : {}", id);
        ideaRepository.deleteById(id);
    }
}
