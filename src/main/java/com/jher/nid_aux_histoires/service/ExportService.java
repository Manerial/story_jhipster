package com.jher.nid_aux_histoires.service;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public interface ExportService {
	Path getPathOfExportedBook(long id) throws Docx4JException, IOException, JAXBException, InvalidFormatException;
}
