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
	public Path getPathOfExportedBook(long id, ExportDocx.FILE_FORMAT format) {
		Optional<BookDTO> optBook = bookService.findOne(id);
		if (optBook.isPresent()) {
			BookDTO book = optBook.get();
			String bookPathString = ExportDocx.getObjectFilePath(book.getName(), format);
			return Paths.get(bookPathString);
		}
		return Path.of("do_not_exist");
	}

	@Override
	public boolean exportBook(long id) {
		if (isLockedBook(id)) {
			log.warn("Le livre est déjà en cours d'export");
			return false;
		} else {
			lockBook(id);
		}

		Thread exportThread = new Thread(() -> {
			try {
				export(id);
				unlockBook(id);
			} catch (Exception e) {
				String error = "Error during the generation of the document : " + e.getMessage();
				log.warn(error, e);
			}
		});
		exportThread.start();
		return true;
	}

	public boolean isLockedBook(long id) {
		return lockedBooks.contains("ID_" + id);
	}

	private boolean lockBook(long id) {
		return lockedBooks.add("ID_" + id);
	}

	private boolean unlockBook(long id) {
		return lockedBooks.remove("ID_" + id);
	}

	private boolean export(long id) {
		Optional<BookDTO> optBook = bookService.findOne(id);
		if (optBook.isPresent()) {
			BookDTO book = optBook.get();

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
			return true;
		}
		return false;
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
