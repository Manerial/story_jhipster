package com.jher.nid_aux_histoires.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.WordAnalysis;
import com.jher.nid_aux_histoires.repository.WordAnalysisRepository;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;
import com.jher.nid_aux_histoires.service.mapper.WordAnalysisMapper;
import com.jher.nid_aux_histoires.service.tool.REG_WordAnalysis;
import com.jher.nid_aux_histoires.service.tool.WordGenerator;

/**
 * Service Implementation for managing {@link WordAnalysis}.
 */
@Service
@Transactional
public class WordAnalysisServiceImpl implements WordAnalysisService {

	private final Logger log = LoggerFactory.getLogger(WordAnalysisServiceImpl.class);

	private final WordAnalysisRepository wordAnalysisRepository;

	private final WordAnalysisMapper wordAnalysisMapper;

	public WordAnalysisServiceImpl(WordAnalysisRepository wordAnalysisRepository,
			WordAnalysisMapper wordAnalysisMapper) {
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
		wordAnalysisRepository.flush();
	}

	@Transactional
	public List<Map<String, String>> getListOfTypes() {
		List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
		List<Map<String, String>> types = new ArrayList<>();
		for (WordAnalysis wordAnalysis : wordAnalysisList) {
			Map<String, String> type = new HashMap<>();
			type.put("name", wordAnalysis.getName());
			type.put("type", wordAnalysis.getType());
			types.add(type);
		}
		return types;
	}

	@Transactional
	public List<String> generate(int numberOfWords, int fixLength, REG_WordAnalysis type) {
		WordAnalysis wordAnalysis = wordAnalysisRepository.findByType(type.toString());
		WordGenerator wordGenerator;
		try {
			wordGenerator = new WordGenerator(wordAnalysis);
			return wordGenerator.generateWords(numberOfWords, fixLength);
		} catch (JSONException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Transactional
	public String generate(REG_WordAnalysis type) {
		WordAnalysis wordAnalysis = wordAnalysisRepository.findByType(type.toString());
		WordGenerator wordGenerator;
		try {
			wordGenerator = new WordGenerator(wordAnalysis);
			return wordGenerator.generateWord();
		} catch (JSONException e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
