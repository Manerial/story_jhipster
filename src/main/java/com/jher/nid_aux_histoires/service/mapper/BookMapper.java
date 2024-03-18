package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "images", source = "images", qualifiedByName = "imageIdSet")
    @Mapping(target = "cover", source = "cover", qualifiedByName = "imageId")
    BookDTO toDto(Book s);

    @Mapping(target = "removeImage", ignore = true)
    Book toEntity(BookDTO bookDTO);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);

    @Named("imageIdSet")
    default Set<ImageDTO> toDtoImageIdSet(Set<Image> image) {
        return image.stream().map(this::toDtoImageId).collect(Collectors.toSet());
    }
}
