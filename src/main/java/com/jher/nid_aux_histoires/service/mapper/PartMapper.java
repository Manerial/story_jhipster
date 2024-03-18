package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Part} and its DTO {@link PartDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartMapper extends EntityMapper<PartDTO, Part> {
    @Mapping(target = "images", source = "images", qualifiedByName = "imageIdSet")
    @Mapping(target = "book", source = "book", qualifiedByName = "bookId")
    PartDTO toDto(Part s);

    @Mapping(target = "removeImage", ignore = true)
    Part toEntity(PartDTO partDTO);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);

    @Named("imageIdSet")
    default Set<ImageDTO> toDtoImageIdSet(Set<Image> image) {
        return image.stream().map(this::toDtoImageId).collect(Collectors.toSet());
    }

    @Named("bookId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookDTO toDtoBookId(Book book);
}
