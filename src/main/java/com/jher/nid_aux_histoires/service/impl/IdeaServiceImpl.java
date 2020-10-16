package com.jher.nid_aux_histoires.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.IdeaService;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.WritingOptionDTO;
import com.jher.nid_aux_histoires.service.mapper.IdeaMapper;
import com.jher.nid_aux_histoires.service.tool.IdeaGenerator;

/**
 * Service Implementation for managing {@link Idea}.
 */
@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

	private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);

	private final IdeaRepository ideaRepository;

	private final WordAnalysisService wordAnalysisService;

	private final IdeaMapper ideaMapper;

	public IdeaServiceImpl(IdeaRepository ideaRepository, IdeaMapper ideaMapper,
			WordAnalysisService wordAnalysisService) {
		this.ideaRepository = ideaRepository;
		this.ideaMapper = ideaMapper;
		this.wordAnalysisService = wordAnalysisService;
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
		return ideaRepository.findAll(pageable).map(ideaMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<IdeaDTO> findOne(Long id) {
		log.debug("Request to get Idea : {}", id);
		return ideaRepository.findById(id).map(ideaMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Idea : {}", id);
		ideaRepository.deleteById(id);
	}

	@Override
	public List<PersonaDTO> generatePersonas(int numberOfPersona, PersonaDTO constraint) {
		List<PersonaDTO> personas = new ArrayList<>();
		IdeaGenerator ideaGenerator = new IdeaGenerator(ideaRepository, wordAnalysisService);
		for (int i = 0; i < numberOfPersona; i++) {
			personas.add(ideaGenerator.generatePersona(constraint));
		}
		return personas;
	}

	@Override
	public List<WritingOptionDTO> generateWritingOptions(int numberOfWritingOption, WritingOptionDTO constraint) {
		List<WritingOptionDTO> writingOptions = new ArrayList<>();
		IdeaGenerator ideaGenerator = new IdeaGenerator(ideaRepository, wordAnalysisService);
		for (int i = 0; i < numberOfWritingOption; i++) {
			writingOptions.add(ideaGenerator.generateWritingOption(constraint));
		}
		return writingOptions;
	}
}