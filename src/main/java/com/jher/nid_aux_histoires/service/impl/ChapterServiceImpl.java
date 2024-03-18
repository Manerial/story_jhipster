package com.jher.nid_aux_histoires.service.impl;

import com.jher.nid_aux_histoires.service.ChapterService;
import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.repository.ChapterRepository;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.service.mapper.ChapterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Chapter}.
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    private final Logger log = LoggerFactory.getLogger(ChapterServiceImpl.class);

    private final ChapterRepository chapterRepository;

    private final ChapterMapper chapterMapper;

    public ChapterServiceImpl(ChapterRepository chapterRepository, ChapterMapper chapterMapper) {
        this.chapterRepository = chapterRepository;
        this.chapterMapper = chapterMapper;
    }

    @Override
    public ChapterDTO save(ChapterDTO chapterDTO) {
        log.debug("Request to save Chapter : {}", chapterDTO);
        Chapter chapter = chapterMapper.toEntity(chapterDTO);
        chapter = chapterRepository.save(chapter);
        return chapterMapper.toDto(chapter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChapterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chapters");
        return chapterRepository.findAll(pageable)
            .map(chapterMapper::toDto);
    }


    public Page<ChapterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return chapterRepository.findAllWithEagerRelationships(pageable).map(chapterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChapterDTO> findOne(Long id) {
        log.debug("Request to get Chapter : {}", id);
        return chapterRepository.findOneWithEagerRelationships(id)
            .map(chapterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chapter : {}", id);
        chapterRepository.deleteById(id);
    }
}
