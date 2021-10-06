package com.jher.nid_aux_histoires.service.dto.idea_generator;

import lombok.Data;

@Data
public class R_QuestDTO implements Random_Interface {
	private String giver;
	private String receiver;
	private String objective;
	private String hazard;
	private String consequence;

	public R_QuestDTO() {
	}

	public R_QuestDTO(R_QuestDTO quest) {
		setGiver(quest.getGiver());
		setReceiver(quest.getReceiver());
		setObjective(quest.getObjective());
		setHazard(quest.getHazard());
		setConsequence(quest.getConsequence());
	}
}
