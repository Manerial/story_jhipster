package com.jher.nid_aux_histoires.service.impl;

import com.jher.nid_aux_histoires.domain.WordAnalysis;
import com.jher.nid_aux_histoires.repository.WordAnalysisRepository;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;
import com.jher.nid_aux_histoires.service.mapper.WordAnalysisMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jher.nid_aux_histoires.domain.WordAnalysis}.
 */
@Service
@Transactional
public class WordAnalysisServiceImpl implements WordAnalysisService {

    private final Logger log = LoggerFactory.getLogger(WordAnalysisServiceImpl.class);

    private final WordAnalysisRepository wordAnalysisRepository;

    private final WordAnalysisMapper wordAnalysisMapper;

    public WordAnalysisServiceImpl(WordAnalysisRepository wordAnalysisRepository, WordAnalysisMapper wordAnalysisMapper) {
        this.wordAnalysisRepository = wordAnalysisRepository;
        this.wordAnalysisMapper = wordAnalysisMapper;
    }

    @Override
    public WordAnalysisDTO save(WordAnalysisDTO wordAnalysisDTO) {
        log.debug("Request to save WordAnalysis : {}", wordAnalysisDTO);
        WordAnalysis wordAnalysis = wordAnalysisMapper.toEntity(wordAnalysisDTO);
        wordAnalysis = wordAnalysisRepository.save(wordAnalysis);
        return wordAnalysisMapper.toDto(wordAnalysis);
    }

    @Override
    public WordAnalysisDTO update(WordAnalysisDTO wordAnalysisDTO) {
        log.debug("Request to update WordAnalysis : {}", wordAnalysisDTO);
        WordAnalysis wordAnalysis = wordAnalysisMapper.toEntity(wordAnalysisDTO);
        wordAnalysis = wordAnalysisRepository.save(wordAnalysis);
        return wordAnalysisMapper.toDto(wordAnalysis);
    }

    @Override
    public Optional<WordAnalysisDTO> partialUpdate(WordAnalysisDTO wordAnalysisDTO) {
        log.debug("Request to partially update WordAnalysis : {}", wordAnalysisDTO);

        return wordAnalysisRepository
            .findById(wordAnalysisDTO.getId())
            .map(existingWordAnalysis -> {
                wordAnalysisMapper.partialUpdate(existingWordAnalysis, wordAnalysisDTO);

                return existingWordAnalysis;
            })
            .map(wordAnalysisRepository::save)
            .map(wordAnalysisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WordAnalysisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WordAnalyses");
        return wordAnalysisRepository.findAll(pageable).map(wordAnalysisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WordAnalysisDTO> findOne(Long id) {
        log.debug("Request to get WordAnalysis : {}", id);
        return wordAnalysisRepository.findById(id).map(wordAnalysisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WordAnalysis : {}", id);
        wordAnalysisRepository.deleteById(id);
    }
}
