package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scene} and its DTO {@link SceneDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImageMapper.class, ChapterMapper.class})
public interface SceneMapper extends EntityMapper<SceneDTO, Scene> {

    @Mapping(source = "chapter.id", target = "chapterId")
    SceneDTO toDto(Scene scene);

    @Mapping(target = "removeImage", ignore = true)
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
