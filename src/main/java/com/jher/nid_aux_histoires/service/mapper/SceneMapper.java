package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scene} and its DTO {@link SceneDTO}.
 */
@Mapper(componentModel = "spring")
public interface SceneMapper extends EntityMapper<SceneDTO, Scene> {
    @Mapping(target = "images", source = "images", qualifiedByName = "imageIdSet")
    @Mapping(target = "chapter", source = "chapter", qualifiedByName = "chapterId")
    SceneDTO toDto(Scene s);

    @Mapping(target = "removeImage", ignore = true)
    Scene toEntity(SceneDTO sceneDTO);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);

    @Named("imageIdSet")
    default Set<ImageDTO> toDtoImageIdSet(Set<Image> image) {
        return image.stream().map(this::toDtoImageId).collect(Collectors.toSet());
    }

    @Named("chapterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChapterDTO toDtoChapterId(Chapter chapter);
}
