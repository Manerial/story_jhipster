package com.jher.nid_aux_histoires.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.domain.Bonus;
import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.domain.Comment;
import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.repository.BookRepository;
import com.jher.nid_aux_histoires.service.BonusService;
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.CommentService;
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

	private final BookRepository bookRepository;

	private final BookMapper bookMapper;

	private final BookMapperLight bookMapperLight;

	private final PartService partService;

	private final CommentService commentService;

	private final BonusService bonusService;

	public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, BookMapperLight bookMapperLight,
			PartService partService, CommentService commentService, BonusService bonusService) {
		this.partService = partService;
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
		this.bookMapperLight = bookMapperLight;
		this.commentService = commentService;
		this.bonusService = bonusService;
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
	public BookDTO changeVisibility(Long bookId) {
		Optional<Book> O_book = bookRepository.findById(bookId);
		if (O_book.isPresent()) {
			Book book = O_book.get();
			book.setVisibility(!book.getVisibility());
			bookRepository.save(book);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Books");
		return bookRepository.findAll(pageable).map(bookMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookDTO> findAllVisible(Pageable pageable) {
		log.debug("Request to get all Books");
		return bookRepository.findAllVisible(pageable).map(bookMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookDTO> findAllByAuthorId(Pageable pageable, String login) {
		return bookRepository.findAllByAuthorLogin(pageable, login).map(bookMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookDTO> findAllVisibleByAuthorId(Pageable pageable, String login) {
		return bookRepository.findAllVisibleByAuthorLogin(pageable, login).map(bookMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BookDTO> findAllFavoritsVisible(Pageable pageable, String login) {
		return bookRepository.findAllFavoritsVisible(pageable, login).map(bookMapperLight::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BookDTO> findOne(Long id) {
		log.debug("Request to get Book : {}", id);
		return bookRepository.findOne(id).map(bookMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BookDTO> findOneLight(Long id) {
		log.debug("Request to get Book : {}", id);
		return bookRepository.findOne(id).map(bookMapperLight::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Book : {}", id);
		Optional<Book> O_book = bookRepository.findById(id);
		if (O_book.isPresent()) {
			Book book = bookRepository.findById(id).get();

			for (Comment comment : book.getComments()) {
				commentService.delete(comment.getId());
			}
			for (Part part : book.getParts()) {
				partService.delete(part.getId());
			}
			for (Bonus bonus : book.getBonuses()) {
				bonusService.delete(bonus.getId());
			}
			bookRepository.deleteById(id);
		}
	}
}
