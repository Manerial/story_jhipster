package com.jher.nid_aux_histoires.service.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.WordAnalysisService;
import com.jher.nid_aux_histoires.service.dto.idea_generator.LocationDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.PhysicalDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.TraitsDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.WritingOptionDTO;
import com.jher.nid_aux_histoires.service.impl.WordAnalysisServiceImpl;

public class IdeaGenerator {
	private final Logger log = LoggerFactory.getLogger(WordAnalysisServiceImpl.class);
	public IdeaRepository ideaRepository;
	public WordAnalysisService wordAnalysisService;

	public IdeaGenerator(IdeaRepository ideaRepository, WordAnalysisService wordAnalysisService) {
		this.ideaRepository = ideaRepository;
		this.wordAnalysisService = wordAnalysisService;
	}

	private String getDefaultOrRandom(String defaultValue, String type) {
		return (!stringEmpty(defaultValue)) ? defaultValue : getRandomIdeaByType(type).getValue();
	}

	private List<String> getDefaultOrRandom(List<String> defaultValues, String type, int defaultMin, int defaultMax) {
		if (defaultValues != null && defaultValues.size() > 0) {
			return defaultValues;
		}
		List<String> ideas = new ArrayList<String>();
		List<Idea> ideaResources = ideaRepository.findByType(type);
		int number = (int) RandomClass.getRandomIntoInterval(defaultMin, defaultMax);
		if (number == 0) {
			ideas.add("Aucun");
		}
		while (ideas.size() < number) {
			int index = RandomClass.getRandBelow(ideaResources.size());
			String hasSash = (ideas.size() < number - 1) ? "," : "";
			if (!ideas.contains(ideaResources.get(index).getValue())) {
				ideas.add(ideaResources.get(index).getValue() + hasSash);
			}
		}
		return ideas;
	}

	private int getDefaultOrRandom(int defaultValue, String type, int defaultMax) {
		return (defaultValue > 0) ? defaultValue : RandomClass.getRandBelow(defaultMax);
	}

	private Idea getRandomIdeaByType(String type) {
		List<Idea> resources = ideaRepository.findByType(type);
		int index = RandomClass.getRandBelow(resources.size());
		return resources.get(index);
	}

	public WritingOptionDTO generateWritingOption(WritingOptionDTO constraint) {
		WritingOptionDTO writingOption = new WritingOptionDTO(constraint);
		String style = getDefaultOrRandom(writingOption.getStyle(), IdeaTypes.style.toString());
		String theme = getDefaultOrRandom(writingOption.getTheme(), IdeaTypes.theme.toString());
		writingOption.setStyle(style);
		writingOption.setTheme(theme);
		return writingOption;
	}

	public LocationDTO generateLocation(LocationDTO constraint) {
		LocationDTO location = new LocationDTO(constraint);
		String place = getDefaultOrRandom(location.getPlace(), IdeaTypes.place.toString());
		String landscape = getDefaultOrRandom(location.getLandscape(), IdeaTypes.landscape.toString());
		String material = getDefaultOrRandom(location.getMaterial(), IdeaTypes.material.toString());
		location.setPlace(place);
		location.setLandscape(landscape);
		location.setMaterial(material);
		return location;
	}

	public PersonaDTO generatePersona(PersonaDTO constraint) {
		PersonaDTO persona = new PersonaDTO(constraint);
		int age = getDefaultOrRandom(persona.getAge(), IdeaTypes.age.toString(), 100);
		String job = getRandomJob(persona.getJob(), age);
		String name = getRandomName(persona.getName());
		PhysicalDTO physical = generatePhysical(persona.getPhysical(), age);
		String role = getDefaultOrRandom(persona.getRole(), IdeaTypes.role.toString());
		String title = getRandomTitle(persona.getTitle());
		TraitsDTO traits = generateTraits(persona.getTraits());
		persona.setAge(age);
		persona.setJob(job);
		persona.setName(name);
		persona.setPhysical(physical);
		persona.setRole(role);
		persona.setTitle(title);
		persona.setTraits(traits);
		return persona;
	}

	private String getRandomName(String defaultName) {
		if (!stringEmpty(defaultName)) {
			return defaultName;
		}
		return wordAnalysisService.generateWord(IdeaTypes.name.toString());
	}

	private String getRandomJob(String defaultJob, int age) {
		if (!stringEmpty(defaultJob)) {
			return defaultJob;
		}
		return (age > 16) ? getRandomIdeaByType(IdeaTypes.job.toString()).getValue() : "Aucun";
	}

	private String getRandomTitle(String defaultTitle) {
		if (!stringEmpty(defaultTitle)) {
			return defaultTitle;
		}
		String prefix = getRandomIdeaByType(IdeaTypes.prefix.toString()).getValue();
		String adjective = getRandomIdeaByType(IdeaTypes.adjective.toString()).getValue();
		String suffix = getRandomIdeaByType(IdeaTypes.suffix.toString()).getValue();
		return prefix + " " + adjective + " " + suffix;
	}

	private PhysicalDTO generatePhysical(PhysicalDTO physical, int age) {
		String eyesColor = getDefaultOrRandom(physical.getEyesColor(), IdeaTypes.eyes_color.toString());
		String faceShape = getDefaultOrRandom(physical.getFaceShape(), IdeaTypes.face_shape.toString());
		String gender = getDefaultOrRandom(physical.getGender(), IdeaTypes.gender.toString());
		String hairColor = getDefaultOrRandom(physical.getHairColor(), IdeaTypes.hair_color.toString());
		String hairStyle = getDefaultOrRandom(physical.getHairStyle(), IdeaTypes.hair_style.toString());
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
			height = RandomClass.getRandomIntoInterval(40 + age * 5, 100 + age * 5);
		} else if (gender.equals("Homme")) {
			height = RandomClass.getRandomIntoInterval(150, 230);
		} else {
			height = RandomClass.getRandomIntoInterval(130, 200);
		}
		return height;
	}

	private double getWeight(double defaultWeight, double height) {
		if (defaultWeight > 0) {
			return defaultWeight;
		}
		double minWeight = height * height * 1.5;
		double maxWeight = height * height * 3.1;
		return RandomClass.getRandomIntoInterval(minWeight, maxWeight);
	}

	private String getMorphology(String morphology, double height, double weight) {
		if (!stringEmpty(morphology)) {
			return morphology;
		}
		int BMI = getBMI(weight, height);
		List<Idea> morphologies = ideaRepository.findByType(IdeaTypes.morphology.toString());
		int index = 0;
		do {
			index = RandomClass.getRandBelow(morphologies.size());
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

	private boolean stringEmpty(String value) {
		return value == null || value.equals("");
	}

	private TraitsDTO generateTraits(TraitsDTO traits) {
		List<String> badTraits = getDefaultOrRandom(traits.getBadTraits(), IdeaTypes.bad_trait.toString(), 1, 3);
		List<String> carac = getDefaultOrRandom(traits.getCaracteristics(), IdeaTypes.caracteristic.toString(), 2, 5);
		List<String> goodTraits = getDefaultOrRandom(traits.getGoodTraits(), IdeaTypes.good_trait.toString(), 2, 5);
		List<String> handicaps = getDefaultOrRandom(traits.getHandicaps(), IdeaTypes.handicap.toString(), 0, 3);
		traits.setBadTraits(badTraits);
		traits.setCaracteristics(carac);
		traits.setGoodTraits(goodTraits);
		traits.setHandicaps(handicaps);
		return traits;
	}
}

class RandomClass {
	private static final Random random = new Random();

	/**
	 * Get a random number between 0 and max
	 *
	 * @param max : the maximum value the random number can take
	 * @return a random number
	 */
	public static int getRandBelow(int max) {
		return random.nextInt(max);
	}

	/**
	 * Get a random number between min and max (both included)
	 *
	 * @param min : the minimum value the random number can take
	 * @param max : the maximum value the random number can take
	 * @return a random number
	 */
	public static double getRandomIntoInterval(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}
}
