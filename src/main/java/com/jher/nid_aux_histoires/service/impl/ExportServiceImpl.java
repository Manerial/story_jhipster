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
	public Path getPathOfExportedBook(long id) {
		BookDTO book = bookService.findOne(id).get();
		String bookPathString = exportDocx.getObjectFilePath(book.getName());
		return Paths.get(bookPathString);
	}

	@Override
	public Path exportBook(long id) {
		BookDTO book = bookService.findOne(id).get();
		try {
			return exportDocx.launchGeneration(book);
		} catch (Exception e) {
		}
		return null;
	}
}
