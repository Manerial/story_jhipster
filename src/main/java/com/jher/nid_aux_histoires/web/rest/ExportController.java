package com.jher.nid_aux_histoires.web.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jher.nid_aux_histoires.service.ExportService;

@RestController
@RequestMapping("/api")
public class ExportController {

	private final Logger log = LoggerFactory.getLogger(ExportController.class);

	private final ExportService exportService;

	public ExportController(ExportService exportService) {
		this.exportService = exportService;
	}

	/**
	 * Exporte un livre en Word
	 * 
	 * @return une réponse contenant la liste des entités
	 * @throws JAXBException
	 * @throws IOException
	 * @throws Docx4JException
	 */
	@GetMapping("/export/book/{id}")
	public ResponseEntity<ByteArrayResource> exportBookById(@Valid @PathVariable int id) {
		Path path = null;
		try {
			path = exportService.getPathOfExportedBook(id);
		} catch (Exception e) {
			String error = "Error during the generation of the document : " + e.getMessage();
			log.warn(error, e);
			return ResponseEntity.badRequest().body(new ByteArrayResource(error.getBytes()));
		}
		if (path == null) {
			String error = "Error during the generation of the document.";
			log.warn(error);
			return ResponseEntity.badRequest().body(new ByteArrayResource(error.getBytes()));
		}
		byte[] data;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			String error = "Error during the reading of the document :" + e.getMessage();
			log.warn(error, e);
			return ResponseEntity.badRequest().body(new ByteArrayResource(error.getBytes()));
		}
		ByteArrayResource resource = new ByteArrayResource(data);
		try {
			Files.delete(path);
		} catch (IOException e) {
			log.warn("", e);
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=\"" + path.getFileName().toString() + "\"")
				.contentType(MediaType
						.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
				.contentLength(data.length).body(resource);
	}
}