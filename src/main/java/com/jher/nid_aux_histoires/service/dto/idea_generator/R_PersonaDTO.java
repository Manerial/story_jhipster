package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
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
}
