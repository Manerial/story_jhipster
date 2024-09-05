package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class PersonaDTO {
	private int age;
	private String job;
	private String name;
	private PhysicalDTO physical;
	private String role;
	private String title;
	private TraitsDTO traits;

	public PersonaDTO() {
		this.physical = new PhysicalDTO();
		this.traits = new TraitsDTO();
	}

	public PersonaDTO(PersonaDTO persona) {
		setAge(persona.getAge());
		setJob(persona.getJob());
		setName(persona.getName());
		setPhysical(new PhysicalDTO(persona.getPhysical()));
		setRole(persona.getRole());
		setTitle(persona.getTitle());
		setTraits(new TraitsDTO(persona.getTraits()));
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PhysicalDTO getPhysical() {
		return physical;
	}

	public void setPhysical(PhysicalDTO physical) {
		this.physical = physical;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TraitsDTO getTraits() {
		return traits;
	}

	public void setTraits(TraitsDTO traits) {
		this.traits = traits;
	}
}
