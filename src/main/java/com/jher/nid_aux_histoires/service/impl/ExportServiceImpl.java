package com.jher.nid_aux_histoires.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jher.nid_aux_histoires.export.ExportDocx;
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.ExportService;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.mapper.BookMapper;

@Service
public class ExportServiceImpl implements ExportService {

	@Autowired
	BookMapper bookMapper;

	@Autowired
	BookService bookService;

	@Autowired
	ExportDocx exportDocx;

	@Override
	public Path getPathOfExportedBook(long id) throws Exception {
		BookDTO book = bookService.findOne(id).get();
		String bookPathString = ExportDocx.getObjectFilePath(book.getName());
		return Paths.get(bookPathString);
	}

	@Override
	public void exportBook(long id) {
		BookDTO book = bookService.findOne(id).get();
		try {
			exportDocx.launchGeneration(book);
		} catch (Exception e) {
		}

		try {
			exportDocx.convertWordToFormat(book, ExportDocx.FILE_FORMAT.pdf);
		} catch (Exception e) {
		}

		try {
			exportDocx.convertWordToFormat(book, ExportDocx.FILE_FORMAT.epub);
		} catch (Exception e) {
		}
	}
}
