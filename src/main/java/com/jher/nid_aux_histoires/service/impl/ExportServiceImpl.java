package com.jher.nid_aux_histoires.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.jher.nid_aux_histoires.export.ExportDocx;
import com.jher.nid_aux_histoires.export.ExportDocx.FILE_FORMAT;
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.ExportService;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.mapper.BookMapper;
import com.jher.nid_aux_histoires.web.rest.ExportController;

@Service
public class ExportServiceImpl implements ExportService {

	@Autowired
	BookMapper bookMapper;

	@Autowired
	BookService bookService;

	@Autowired
	ExportDocx exportDocx;

	private static List<String> lockedBooks = new ArrayList<>();
	private final Logger log = LoggerFactory.getLogger(ExportController.class);

	@Override
	public Path getPathOfExportedBook(long id, ExportDocx.FILE_FORMAT format) throws Exception {
		Optional<BookDTO> O_book = bookService.findOne(id);
		if (O_book.isPresent()) {
			BookDTO book = O_book.get();
			String bookPathString = ExportDocx.getObjectFilePath(book.getName(), format);
			return Paths.get(bookPathString);
		}
		return null;
	}

	@Override
	public boolean exportBook(long id) {
		String idBook = "ID_" + id;
		if (lockedBooks.contains(idBook)) {
			log.warn("Le livre est déjà en cours d'export");
			return false;
		} else {
			lockedBooks.add(idBook);
		}

		Thread exportThread = new Thread(() -> {
			try {
				export(id);
				lockedBooks.remove(idBook);
			} catch (Exception e) {
				String error = "Error during the generation of the document : " + e.getMessage();
				log.warn(error, e);
			}
		});
		exportThread.start();
		return true;
	}

	private void export(long id) {
		Optional<BookDTO> O_book = bookService.findOne(id);
		if (O_book.isPresent()) {
			BookDTO book = bookService.findOne(id).get();

			try {
				exportDocx.launchGeneration(book);
			} catch (Exception e) {
			}

			try {
				exportDocx.convertWordToFormat(book, ExportDocx.FILE_FORMAT.PDF);
			} catch (Exception e) {
			}

			try {
				exportDocx.convertWordToFormat(book, ExportDocx.FILE_FORMAT.EPUB);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public MediaType getMediaType(FILE_FORMAT format) {
		switch (format) {
		case DOCX:
			return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		case EPUB:
			return new MediaType("application", "epub+zip");
		case PDF:
			return MediaType.APPLICATION_PDF;
		case JPG:
			return new MediaType("image", "jpg");
		case JPEG:
			return MediaType.IMAGE_JPEG;
		case PNG:
			return MediaType.IMAGE_PNG;
		default:
			return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		}
	}
}
