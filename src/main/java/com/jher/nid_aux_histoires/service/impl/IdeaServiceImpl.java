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
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;
import com.jher.nid_aux_histoires.service.mapper.IdeaMapper;
import com.jher.nid_aux_histoires.service.tool.CreatureGenerator;
import com.jher.nid_aux_histoires.service.tool.HonoraryTitleGenerator;
import com.jher.nid_aux_histoires.service.tool.LocationGenerator;
import com.jher.nid_aux_histoires.service.tool.ObjectGenerator;
import com.jher.nid_aux_histoires.service.tool.PersonaGenerator;
import com.jher.nid_aux_histoires.service.tool.REG_Entity;
import com.jher.nid_aux_histoires.service.tool.RandomEntityGenerator;
import com.jher.nid_aux_histoires.service.tool.WritingOptionGenerator;

/**
 * Service Implementation for managing {@link Idea}.
 */
@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

	private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);

	private final IdeaRepository ideaRepository;
	private final IdeaMapper ideaMapper;
	private final WordAnalysisService wordAnalysisService;

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
	public List<Random_Interface> generate(int number, REG_Entity type, Random_Interface constraint) {
		List<Random_Interface> objects = new ArrayList<>();
		RandomEntityGenerator ideaGenerator;
		switch (type) {
		case LOCATION:
			ideaGenerator = new LocationGenerator(ideaRepository);
			break;
		case OBJECT:
			ideaGenerator = new ObjectGenerator(ideaRepository);
			break;
		case PERSONA:
			ideaGenerator = new PersonaGenerator(ideaRepository, wordAnalysisService);
			break;
		case WRITING_OPTION:
			ideaGenerator = new WritingOptionGenerator(ideaRepository);
			break;
		case HONORARY_TITLE:
			ideaGenerator = new HonoraryTitleGenerator(ideaRepository);
			break;
		case CREATURE:
			ideaGenerator = new CreatureGenerator(ideaRepository, wordAnalysisService);
			break;
		default:
			return objects;
		}
		for (int i = 0; i < number; i++) {
			objects.add(ideaGenerator.generate(constraint));
		}
		return objects;
	}
}
