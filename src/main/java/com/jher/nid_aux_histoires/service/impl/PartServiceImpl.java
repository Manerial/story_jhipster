package com.jher.nid_aux_histoires.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.repository.PartRepository;
import com.jher.nid_aux_histoires.service.ChapterService;
import com.jher.nid_aux_histoires.service.PartService;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
import com.jher.nid_aux_histoires.service.mapper.PartMapper;

/**
 * Service Implementation for managing {@link Part}.
 */
@Service
@Transactional
public class PartServiceImpl implements PartService {

	private final Logger log = LoggerFactory.getLogger(PartServiceImpl.class);

	private final ChapterService chapterService;

	private final PartRepository partRepository;

	private final PartMapper partMapper;

	public PartServiceImpl(PartRepository partRepository, PartMapper partMapper, ChapterService chapterService) {
		this.chapterService = chapterService;
		this.partRepository = partRepository;
		this.partMapper = partMapper;
	}

	@Override
	public PartDTO save(PartDTO partDTO) {
		log.debug("Request to save Part : {}", partDTO);
		Part part = partMapper.toEntity(partDTO);
		if (part.getNumber() <= 0) {
			part.setNumber(findNextNumberForBookId(part.getBook().getId()));
		}
		part = partRepository.save(part);
		return partMapper.toDto(part);
	}

	@Override
	public PartDTO saveBash(PartDTO partDTO) {
		log.debug("Request to save Part : {}", partDTO);
		Part part = partMapper.toEntity(partDTO);
		part = partRepository.save(part);
		for (ChapterDTO chapterDTO : partDTO.getChapters()) {
			chapterDTO.setPartId(part.getId());
			chapterService.saveBash(chapterDTO);
		}
		return partMapper.toDto(part);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PartDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Parts");
		return partRepository.findAll(pageable).map(partMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PartDTO> findAllByAuthorLogin(Pageable pageable, String login) {
		return partRepository.findAllByAuthorLogin(pageable, login).map(partMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PartDTO> findOne(Long id) {
		log.debug("Request to get Part : {}", id);
		return partRepository.findOne(id).map(partMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public String findAuthorLoginByPartId(Long id) {
		return partRepository.findAuthorLoginByPartId(id);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Part : {}", id);
		Optional<Part> optPart = partRepository.findById(id);
		if (optPart.isPresent()) {
			Part part = optPart.get();
			for (Chapter chapter : part.getChapters()) {
				chapterService.delete(chapter.getId());
			}
			partRepository.deleteById(id);
			partRepository.flush();
		}
	}

	private int findNextNumberForBookId(Long bookId) {
		log.debug("Request to get all Parts");
		int bigest = 1;
		for (Part part : partRepository.findAllByBookId(bookId)) {
			bigest = (bigest > part.getNumber()) ? bigest : part.getNumber() + 1;
		}
		return bigest;
	}
}
