package com.jher.nid_aux_histoires.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.repository.ImageRepository;
import com.jher.nid_aux_histoires.service.ImageService;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import com.jher.nid_aux_histoires.service.mapper.ImageMapper;
import com.jher.nid_aux_histoires.service.mapper.ImageMapperLight;

/**
 * Service Implementation for managing {@link Image}.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

	private final ImageRepository imageRepository;

	private final ImageMapper imageMapper;

	private final ImageMapperLight imageMapperLight;

	public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper,
			ImageMapperLight imageMapperLight) {
		this.imageRepository = imageRepository;
		this.imageMapper = imageMapper;
		this.imageMapperLight = imageMapperLight;
	}

	@Override
	public ImageDTO save(ImageDTO imageDTO) {
		log.debug("Request to save Image : {}", imageDTO);
		Image image = imageMapper.toEntity(imageDTO);
		image.generatePreview();
		image = imageRepository.save(image);
		return imageMapper.toDto(image);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ImageDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Images");
		return imageRepository.findAll(pageable).map(imageMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImageDTO> findAllByBookId(Long id) {
		log.debug("Request to get all Images");
		return imageMapperLight.toDto(imageRepository.findAllByBookId(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImageDTO> findAllByPartId(Long id) {
		log.debug("Request to get all Images");
		return imageMapperLight.toDto(imageRepository.findAllByPartId(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImageDTO> findAllByChapterId(Long id) {
		log.debug("Request to get all Images");
		return imageMapperLight.toDto(imageRepository.findAllByChapterId(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImageDTO> findAllBySceneId(Long id) {
		log.debug("Request to get all Images");
		return imageMapperLight.toDto(imageRepository.findAllBySceneId(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ImageDTO> findOne(Long id) {
		log.debug("Request to get Image : {}", id);
		return imageRepository.findById(id).map(imageMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Image : {}", id);
		imageRepository.deleteById(id);
	}
}
