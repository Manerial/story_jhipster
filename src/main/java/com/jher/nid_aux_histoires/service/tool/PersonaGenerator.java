package com.jher.nid_aux_histoires.service.tool;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_PhysicalDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_TraitsDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;

public class PersonaGenerator extends RandomEntityGenerator {
	private WordAnalysisService wordAnalysisService;

	public PersonaGenerator(IdeaRepository ideaRepository, WordAnalysisService wordAnalysisService) {
		super(ideaRepository);
		this.wordAnalysisService = wordAnalysisService;
	}

	@Override
	public R_PersonaDTO generate(Random_Interface constraint) {
		R_PersonaDTO randomPersona = new R_PersonaDTO((R_PersonaDTO) constraint);
		int age = getDefaultOrRandom(randomPersona.getAge(), 100);
		String job = getRandomJob(randomPersona.getJob(), age);
		String name = getRandomName(randomPersona.getName());
		String race = getDefaultOrRandom(randomPersona.getRace(), REG_Element.race);
		R_PhysicalDTO physical = generatePhysical(randomPersona.getPhysical(), age);
		String role = getDefaultOrRandom(randomPersona.getRole(), REG_Element.role);
		R_TraitsDTO traits = generateTraits(randomPersona.getTraits());

		randomPersona.setAge(age);
		randomPersona.setJob(job);
		randomPersona.setName(name);
		randomPersona.setRace(race);
		randomPersona.setPhysical(physical);
		randomPersona.setRole(role);
		randomPersona.setTraits(traits);
		return randomPersona;
	}

	private String getRandomName(String defaultName) {
		if (!stringEmpty(defaultName)) {
			return defaultName;
		}
		return wordAnalysisService.generate("name");
	}

	private String getRandomJob(String defaultJob, int age) {
		if (!stringEmpty(defaultJob)) {
			return defaultJob;
		}
		return (age > 16) ? getRandomIdeaByType(REG_Element.job).getValue() : "Aucun";
	}

	private R_PhysicalDTO generatePhysical(R_PhysicalDTO physical, int age) {
		String eyesColor = getDefaultOrRandom(physical.getEyesColor(), REG_Element.eyes_color);
		String faceShape = getDefaultOrRandom(physical.getFaceShape(), REG_Element.face_shape);
		String gender = getDefaultOrRandom(physical.getGender(), REG_Element.gender);
		String hairColor = getDefaultOrRandom(physical.getHairColor(), REG_Element.hair_color);
		String hairStyle = getDefaultOrRandom(physical.getHairStyle(), REG_Element.hair_style);
		double height = getHeight(physical.getHeight(), age, gender);
		double weight = getWeight(physical.getWeight(), height);
		String morphology = getMorphology(physical.getMorphology(), height, weight);
		physical.setEyesColor(eyesColor);
		physical.setFaceShape(faceShape);
		physical.setGender(gender);
		physical.setHairColor(hairColor);
		physical.setHairStyle(hairStyle);
		physical.setHeight(height);
		physical.setWeight(weight);
		physical.setMorphology(morphology);
		return physical;
	}

	private double getHeight(double defaultHeight, int age, String gender) {
		if (defaultHeight > 0) {
			return defaultHeight;
		}
		double height;
		if (age < 18) {
			int min = 40 + age * 5;
			int max = 100 + age * 5;
			height = RNG.getRandomIntoInterval(min, max);
		} else if (gender.equals("Homme")) {
			height = RNG.getRandomIntoInterval(150, 230);
		} else {
			height = RNG.getRandomIntoInterval(130, 200);
		}
		return height;
	}

	private double getWeight(double defaultWeight, double height) {
		if (defaultWeight > 0) {
			return defaultWeight;
		}
		double minWeight = height * height * 1.5;
		double maxWeight = height * height * 3.1;
		return RNG.getRandomIntoInterval(minWeight, maxWeight);
	}

	private String getMorphology(String morphology, double height, double weight) {
		if (!stringEmpty(morphology)) {
			return morphology;
		}
		int BMI = getBMI(weight, height);
		List<Idea> morphologies = ideaRepository.findByType(REG_Element.morphology.toString());
		int index = 0;
		do {
			index = RNG.getRandBelow(morphologies.size());
		} while (!checkBMI(morphologies.get(index).getComplement(), BMI));
		return morphologies.get(index).getValue();
	}

	private int getBMI(double weight, double height) {
		float weightKG = (float) (weight) / 1000;
		float heightM = (float) (height) / 100;
		int BMI = (int) (weightKG / (heightM * heightM));
		return BMI;
	}

	private static boolean checkBMI(String complement, int BMI) {
		String BMIString;
		int BMIMin;
		int BMIMax;
		if (complement.contains("+")) {
			BMIString = complement.split("[+]")[0];
			BMIMin = Integer.parseInt(BMIString);
			return BMIMin <= BMI;
		} else if (complement.contains("-")) {
			BMIString = complement.split("[-]")[0];
			BMIMax = Integer.parseInt(BMIString);
			return BMI <= BMIMax;
		} else if (complement.contains("/")) {
			BMIString = complement.split("[/]")[0];
			BMIMin = Integer.parseInt(BMIString);
			BMIString = complement.split("[/]")[1];
			BMIMax = Integer.parseInt(BMIString);
			return BMIMin <= BMI && BMI <= BMIMax;
		} else if (complement.equals("ALL")) {
			return true;
		}
		return false;
	}

	private R_TraitsDTO generateTraits(R_TraitsDTO traits) {
		Set<String> badTraits = getDefaultOrRandom(traits.getBadTraits(), REG_Element.bad_trait, 1, 3);
		Set<String> carac = getDefaultOrRandom(traits.getCaracteristics(), REG_Element.hobby, 2, 5);
		Set<String> goodTraits = getDefaultOrRandom(traits.getGoodTraits(), REG_Element.good_trait, 2, 5);
		Set<String> handicaps;
		if (RNG.getRandBelow(100) < 40) {
			handicaps = getDefaultOrRandom(traits.getHandicaps(), REG_Element.handicap, 1, 3);
		} else {
			handicaps = new TreeSet<String>();
			handicaps.add("Aucun");
		}
		traits.setBadTraits(badTraits);
		traits.setCaracteristics(carac);
		traits.setGoodTraits(goodTraits);
		traits.setHandicaps(handicaps);
		return traits;
	}
}
