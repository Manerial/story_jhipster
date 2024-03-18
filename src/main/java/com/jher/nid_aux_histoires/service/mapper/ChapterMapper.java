package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chapter} and its DTO {@link ChapterDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChapterMapper extends EntityMapper<ChapterDTO, Chapter> {
    @Mapping(target = "images", source = "images", qualifiedByName = "imageIdSet")
    @Mapping(target = "part", source = "part", qualifiedByName = "partId")
    ChapterDTO toDto(Chapter s);

    @Mapping(target = "removeImage", ignore = true)
    Chapter toEntity(ChapterDTO chapterDTO);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);

    @Named("imageIdSet")
    default Set<ImageDTO> toDtoImageIdSet(Set<Image> image) {
        return image.stream().map(this::toDtoImageId).collect(Collectors.toSet());
    }

    @Named("partId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PartDTO toDtoPartId(Part part);
}
