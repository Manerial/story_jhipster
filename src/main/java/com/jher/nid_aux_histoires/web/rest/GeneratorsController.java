package com.jher.nid_aux_histoires.web.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jher.nid_aux_histoires.service.IdeaService;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_LocationDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_ObjectDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_WritingOptionDTO;

/**
 * REST controller for managing
 * {@link com.jher.nid_aux_histoires.domain.WordAnalysis}.
 */
@RestController
@RequestMapping("/api")
public class GeneratorsController {

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final WordAnalysisService wordAnalysisService;

	private final IdeaService ideaService;

	public GeneratorsController(WordAnalysisService wordAnalysisService, IdeaService ideaService) {
		this.wordAnalysisService = wordAnalysisService;
		this.ideaService = ideaService;
	}

	/**
	 * Génère une liste de personnages
	 * 
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/personas")
	public ResponseEntity<List<R_PersonaDTO>> generatePersonas(@RequestParam int number,
			@Valid @RequestBody R_PersonaDTO constraint) {
		List<R_PersonaDTO> generatedWords = ideaService.generateR_Personas(number, constraint);
		return ResponseEntity.ok().body(generatedWords);
	}

	/**
	 * Génère une liste de lieux
	 * 
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/locations")
	public ResponseEntity<List<R_LocationDTO>> generateLocations(@RequestParam int number,
			@Valid @RequestBody R_LocationDTO constraint) {
		List<R_LocationDTO> generatedLocations = ideaService.generateR_Locations(number, constraint);
		return ResponseEntity.ok().body(generatedLocations);
	}

	/**
	 * Génère une liste d'objets
	 * 
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/objects")
	public ResponseEntity<List<R_ObjectDTO>> generateObject(@RequestParam int number,
			@Valid @RequestBody R_ObjectDTO constraint) {
		List<R_ObjectDTO> generatedObject = ideaService.generateR_Object(number, constraint);
		return ResponseEntity.ok().body(generatedObject);
	}

	/**
	 * Génère une liste d'options d'écriture
	 * 
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/writing_options")
	public ResponseEntity<List<R_WritingOptionDTO>> generateWritingOptions(@RequestParam int number,
			@Valid @RequestBody R_WritingOptionDTO constraint) {
		List<R_WritingOptionDTO> generatedWritingOptions = ideaService.generateR_WritingOptions(number, constraint);
		return ResponseEntity.ok().body(generatedWritingOptions);
	}

	/**
	 * Génère une liste de mots en fonction de contraintes
	 * 
	 * @return une réponse contenant la liste des entités
	 */
	@GetMapping("/generate/words")
	public ResponseEntity<List<String>> generateWords(@RequestParam int number, @RequestParam int fixLength,
			@RequestParam String type) {
		List<String> generatedWords = wordAnalysisService.generateWords(number, fixLength, type);
		return ResponseEntity.ok().body(generatedWords);
	}

	/**
	 * Fournit la liste des objets dans la BDD
	 * 
	 * @return une réponse contenant la liste des entités
	 */
	@GetMapping("/generate/get_types")
	public ResponseEntity<List<Map<String, String>>> getTypes() {
		return ResponseEntity.ok().body(wordAnalysisService.getListOfTypes());
	}
}
