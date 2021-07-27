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
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_CreatureDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_LocationDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_ObjectDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_WritingOptionDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;
import com.jher.nid_aux_histoires.service.tool.REG_Entity;

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
	 * @param number     : nombre d'entités à créer
	 * @param constraint : entité contenant des contraintes à respecter
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/personas")
	public ResponseEntity<List<Random_Interface>> generatePersonas(@RequestParam int number,
			@Valid @RequestBody R_PersonaDTO constraint) {
		List<Random_Interface> generatedPersonas = ideaService.generate(number, REG_Entity.persona, constraint);
		return ResponseEntity.ok().body(generatedPersonas);
	}

	/**
	 * Génère une liste de créatures
	 * 
	 * @param number     : nombre d'entités à créer
	 * @param constraint : entité contenant des contraintes à respecter
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/creatures")
	public ResponseEntity<List<Random_Interface>> generateCreatures(@RequestParam int number,
			@Valid @RequestBody R_CreatureDTO constraint) {
		List<Random_Interface> generatedCreatures = ideaService.generate(number, REG_Entity.creature, constraint);
		return ResponseEntity.ok().body(generatedCreatures);
	}

	/**
	 * Génère une liste de titres honorifiques
	 * 
	 * @param number : nombre d'entités à créer
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/honorary_titles")
	public ResponseEntity<List<Random_Interface>> generateHonoraryTitles(@RequestParam int number) {
		List<Random_Interface> generatedHonoraryTitles = ideaService.generate(number, REG_Entity.honorary_title, null);
		return ResponseEntity.ok().body(generatedHonoraryTitles);
	}

	/**
	 * Génère une liste de lieux
	 * 
	 * @param number     : nombre d'entités à créer
	 * @param constraint : entité contenant des contraintes à respecter
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/locations")
	public ResponseEntity<List<Random_Interface>> generateLocations(@RequestParam int number,
			@Valid @RequestBody R_LocationDTO constraint) {
		List<Random_Interface> generatedLocations = ideaService.generate(number, REG_Entity.location, constraint);
		return ResponseEntity.ok().body(generatedLocations);
	}

	/**
	 * Génère une liste d'objets
	 * 
	 * @param number     : nombre d'entités à créer
	 * @param constraint : entité contenant des contraintes à respecter
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/objects")
	public ResponseEntity<List<Random_Interface>> generateObject(@RequestParam int number,
			@Valid @RequestBody R_ObjectDTO constraint) {
		List<Random_Interface> generatedObjects = ideaService.generate(number, REG_Entity.object, constraint);
		return ResponseEntity.ok().body(generatedObjects);
	}

	/**
	 * Génère une liste d'options d'écriture
	 * 
	 * @param number     : nombre d'entités à créer
	 * @param constraint : entité contenant des contraintes à respecter
	 * @return une réponse contenant la liste des entités
	 */
	@PostMapping("/generate/writing_options")
	public ResponseEntity<List<Random_Interface>> generateWritingOptions(@RequestParam int number,
			@Valid @RequestBody R_WritingOptionDTO constraint) {
		List<Random_Interface> generatedWritingOptions = ideaService.generate(number, REG_Entity.writing_option,
				constraint);
		return ResponseEntity.ok().body(generatedWritingOptions);
	}

	/**
	 * Génère une liste de mots en fonction de contraintes
	 * 
	 * @param number    : nombre de mots à créer
	 * @param fixLength : taille de mots à respecter (random si inférieur à 2)
	 * @param type      : type de mots à créer
	 * @return une réponse contenant la liste des entités
	 */
	@GetMapping("/generate/words")
	public ResponseEntity<List<String>> generateWords(@RequestParam int number, @RequestParam int fixLength,
			@RequestParam String type) {
		List<String> generatedWords = wordAnalysisService.generate(number, fixLength, type);
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
