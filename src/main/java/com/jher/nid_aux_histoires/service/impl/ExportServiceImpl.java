package com.jher.nid_aux_histoires.service.impl;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
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
	public Path getPathOfExportedBook(long id)
			throws Docx4JException, IOException, JAXBException, InvalidFormatException {
		BookDTO book = bookService.findOne(id).get();
		return exportDocx.launchGeneration(book);
	}
}
