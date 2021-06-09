package com.jher.nid_aux_histoires.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Bonus;
import com.jher.nid_aux_histoires.repository.BonusRepository;
import com.jher.nid_aux_histoires.service.BonusService;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;
import com.jher.nid_aux_histoires.service.mapper.BonusMapper;
import com.jher.nid_aux_histoires.service.mapper.BonusMapperLight;

/**
 * Service Implementation for managing {@link Bonus}.
 */
@Service
@Transactional
public class BonusServiceImpl implements BonusService {

	private final Logger log = LoggerFactory.getLogger(BonusServiceImpl.class);

	private final BonusRepository bonusRepository;

	private final BonusMapper bonusMapper;

	private final BonusMapperLight bonusMapperLight;

	public BonusServiceImpl(BonusRepository bonusRepository, BonusMapper bonusMapper,
			BonusMapperLight bonusMapperLight) {
		this.bonusRepository = bonusRepository;
		this.bonusMapper = bonusMapper;
		this.bonusMapperLight = bonusMapperLight;
	}

	@Override
	public BonusDTO save(BonusDTO bonusDTO) {
		log.debug("Request to save Bonus : {}", bonusDTO);
		Bonus bonus = bonusMapper.toEntity(bonusDTO);
		bonus = bonusRepository.save(bonus);
		return bonusMapper.toDto(bonus);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BonusDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Bonuses");
		return bonusRepository.findAll(pageable).map(bonusMapper::toDto);
	}

	@Override
	public List<BonusDTO> findAllByBookId(Long id) {
		log.debug("Request to get all Bonuses by Book");
		return bonusMapperLight.toDto(bonusRepository.findAllByBookId(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BonusDTO> findOne(Long id) {
		log.debug("Request to get Bonus : {}", id);
		return bonusRepository.findById(id).map(bonusMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Bonus : {}", id);
		bonusRepository.deleteById(id);
	}
}
