package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;

/**
 * Mapper for the entity {@link Scene} and its DTO {@link SceneDTO}.
 */
@Mapper(componentModel = "spring", uses = { ChapterMapper.class })
public interface SceneMapper extends EntityMapper<SceneDTO, Scene> {

	@Mapping(source = "chapter.id", target = "chapterId")
	SceneDTO toDto(Scene scene);

	@Mapping(source = "chapterId", target = "chapter")
	Scene toEntity(SceneDTO sceneDTO);

	default Scene fromId(Long id) {
		if (id == null) {
			return null;
		}
		Scene scene = new Scene();
		scene.setId(id);
		return scene;
	}
}
