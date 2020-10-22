package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageMapperLight extends EntityMapper<ImageDTO, Image> {

	@Mapping(target = "bookToCovers", ignore = true)
	@Mapping(target = "removeBookToCover", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "removeBook", ignore = true)
	@Mapping(target = "parts", ignore = true)
	@Mapping(target = "removePart", ignore = true)
	@Mapping(target = "chapters", ignore = true)
	@Mapping(target = "removeChapter", ignore = true)
	@Mapping(target = "scenes", ignore = true)
	@Mapping(target = "removeScene", ignore = true)
	Image toEntity(ImageDTO imageDTO);

	@Mapping(target = "picture", ignore = true)
	ImageDTO toDto(Image imageDTO);

	default Image fromId(Long id) {
		if (id == null) {
			return null;
		}
		Image image = new Image();
		image.setId(id);
		return image;
	}
}
