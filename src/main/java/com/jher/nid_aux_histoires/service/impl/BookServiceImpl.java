package com.jher.nid_aux_histoires.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.repository.BookRepository;
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.PartService;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
import com.jher.nid_aux_histoires.service.mapper.BookMapper;
import com.jher.nid_aux_histoires.service.mapper.BookMapperLight;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

	private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

	private final PartService partService;

	private final BookRepository bookRepository;

	private final BookMapper bookMapper;

	private final BookMapperLight bookMapperLight;

	public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, PartService partService,
			BookMapperLight bookMapperLight) {
		this.partService = partService;
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
		this.bookMapperLight = bookMapperLight;
	}

	@Override
	public BookDTO save(BookDTO bookDTO) {
		log.debug("Request to save Book : {}", bookDTO);
		Book book = bookMapper.toEntity(bookDTO);
		book = bookRepository.save(book);
		return bookMapper.toDto(book);
	}

	@Override
	public BookDTO saveBash(BookDTO bookDTO) {
		log.debug("Request to save Book : {}", bookDTO);
		Book book = bookMapper.toEntity(bookDTO);
		book = bookRepository.save(book);
		for (PartDTO partDTO : bookDTO.getParts()) {
			partDTO.setBookId(book.getId());
			partService.saveBash(partDTO);
		}
		return bookMapper.toDto(book);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Books");
		return bookRepository.findAll(pageable).map(bookMapperLight::toDto);
	}

	@Override
	public List<BookDTO> findAllByAuthorId(String login) {
		return bookMapperLight.toDto(bookRepository.findAllByAuthorLogin(login));
	}

	public Page<BookDTO> findAllWithEagerRelationships(Pageable pageable) {
		return bookRepository.findAllWithEagerRelationships(pageable).map(bookMapper::toDto);
	}

	public List<BookDTO> findAllWithEagerRelationships() {
		return bookMapper.toDto(bookRepository.findAllWithEagerRelationships());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BookDTO> findOne(Long id) {
		log.debug("Request to get Book : {}", id);
		return bookRepository.findOneWithEagerRelationships(id).map(bookMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Book : {}", id);
		Book book = bookRepository.findById(id).get();
		for (Part part : book.getParts()) {
			partService.delete(part.getId());
		}
		bookRepository.deleteById(id);
	}
}
