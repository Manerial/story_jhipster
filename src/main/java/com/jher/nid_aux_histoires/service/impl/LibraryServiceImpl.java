package com.jher.nid_aux_histoires.service.impl;

import com.jher.nid_aux_histoires.service.LibraryService;
import com.jher.nid_aux_histoires.domain.Library;
import com.jher.nid_aux_histoires.repository.LibraryRepository;
import com.jher.nid_aux_histoires.service.dto.LibraryDTO;
import com.jher.nid_aux_histoires.service.mapper.LibraryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Library}.
 */
@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final Logger log = LoggerFactory.getLogger(LibraryServiceImpl.class);

    private final LibraryRepository libraryRepository;

    private final LibraryMapper libraryMapper;

    public LibraryServiceImpl(LibraryRepository libraryRepository, LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    public LibraryDTO save(LibraryDTO libraryDTO) {
        log.debug("Request to save Library : {}", libraryDTO);
        Library library = libraryMapper.toEntity(libraryDTO);
        library = libraryRepository.save(library);
        return libraryMapper.toDto(library);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LibraryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Libraries");
        return libraryRepository.findAll(pageable)
            .map(libraryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LibraryDTO> findOne(Long id) {
        log.debug("Request to get Library : {}", id);
        return libraryRepository.findById(id)
            .map(libraryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Library : {}", id);
        libraryRepository.deleteById(id);
    }
}
