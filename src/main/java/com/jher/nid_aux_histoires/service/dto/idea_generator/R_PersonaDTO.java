package com.jher.nid_aux_histoires.service.dto.idea_generator;

public class R_PersonaDTO implements Random_Interface {
	private int age;
	private String job;
	private String name;
	private String race;
	private R_PhysicalDTO physical;
	private String role;
	private R_TraitsDTO traits;

	public R_PersonaDTO() {
		this.physical = new R_PhysicalDTO();
		this.traits = new R_TraitsDTO();
	}

	public R_PersonaDTO(R_PersonaDTO persona) {
		setAge(persona.getAge());
		setJob(persona.getJob());
		setName(persona.getName());
		setPhysical(new R_PhysicalDTO(persona.getPhysical()));
		setRole(persona.getRole());
		setTraits(new R_TraitsDTO(persona.getTraits()));
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

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public R_PhysicalDTO getPhysical() {
		return physical;
	}

	public void setPhysical(R_PhysicalDTO physical) {
		this.physical = physical;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public R_TraitsDTO getTraits() {
		return traits;
	}

	public void setTraits(R_TraitsDTO traits) {
		this.traits = traits;
	}
}
