package com.jher.nid_aux_histoires.web.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jher.nid_aux_histoires.export.ExportDocx;
import com.jher.nid_aux_histoires.export.ExportDocx.AVAILABLE_FILE_FORMAT;
import com.jher.nid_aux_histoires.service.BonusService;
import com.jher.nid_aux_histoires.service.ExportService;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class ExportController {

	private final Logger log = LoggerFactory.getLogger(ExportController.class);

	private final ExportService exportService;

	private final BonusService bonusService;

	public ExportController(ExportService exportService, BonusService bonusService) {
		this.exportService = exportService;
		this.bonusService = bonusService;
	}

	/**
	 * Exporte un livre en Word
	 * 
	 * @param id : l'Id du livre à exporter
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/export/book/{id}")
	public ResponseEntity<String> exportBookById(@Valid @PathVariable int id) {
		if (exportService.exportBook(id)) {
			return ResponseEntity.ok().body("Export ongoing");
		} else {
			return ResponseEntity.badRequest().body("Export already ongoing");
		}
	}

	/**
	 * Récupère les formats disponibles
	 * 
	 * @return une réponse contenant la liste des formats disponibles
	 */
	@GetMapping("/download/formats")
	public ResponseEntity<AVAILABLE_FILE_FORMAT[]> getExportFormat() {
		return ResponseEntity.ok().body(ExportDocx.AVAILABLE_FILE_FORMAT.values());
	}

	/**
	 * Exporte un livre au format spécifié
	 * 
	 * @param id     : l'Id du livre à récupérer
	 * @param format : le format du livre à récupérer
	 * @return une réponse contenant le fichier à télécharger
	 */
	@GetMapping("/download/book/{id}")
	public ResponseEntity<ByteArrayResource> getBook(@Valid @PathVariable int id,
			@Valid @RequestParam ExportDocx.FILE_FORMAT format) {
		Path path = exportService.getPathOfExportedBook(id, format);
		log.info(path.toString());

		if (!path.toFile().exists()) {
			throw new BadRequestAlertException("The entity book does not exist", "book", "donotexist");
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
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=\"" + path.getFileName().toString() + "\"")
				.contentType(exportService.getMediaType(format)).contentLength(data.length).body(resource);
	}

	/**
	 * Exporte un bonus au format spécifié
	 * 
	 * @param id : l'Id du livre à récupérer
	 * @return une réponse contenant le fichier à télécharger
	 */
	@GetMapping("/download/bonus/{id}")
	public ResponseEntity<byte[]> getBonus(@PathVariable Long id) {
		log.debug("REST request to get Bonus : {}", id);
		Optional<BonusDTO> optBonus = bonusService.findOne(id);
		if (!optBonus.isPresent()) {
			String error = "Error during the reading of the document. Document not found";
			return ResponseEntity.badRequest().body(error.getBytes());
		}
		BonusDTO bonusDTO = optBonus.get();
		MediaType MT = MediaType.parseMediaType(bonusDTO.getDataContentType());
		String fileName = bonusDTO.getName() + "." + bonusDTO.getDataContentType();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"")
				.contentType(MT).contentLength(bonusDTO.getData().length).body(bonusDTO.getData());
	}
}