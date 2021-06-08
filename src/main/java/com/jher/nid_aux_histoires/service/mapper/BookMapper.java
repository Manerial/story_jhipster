package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.service.dto.BookDTO;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = { ImageMapper.class, UserMapper.class })
public interface BookMapper extends EntityMapper<BookDTO, Book> {
	@Mapping(source = "author.id", target = "authorId")
	@Mapping(source = "author.login", target = "authorLogin")
	@Mapping(source = "cover.id", target = "coverId")
	@Mapping(source = "cover.preview", target = "coverPreview")
	@Mapping(target = "bonuses", expression = "java(book.getBonuses().size() > 0)")
	BookDTO toDto(Book book);

	@Mapping(target = "parts", ignore = true)
	@Mapping(target = "comments", ignore = true)
	@Mapping(target = "bonuses", ignore = true)
	@Mapping(target = "removePart", ignore = true)
	@Mapping(target = "removeImage", ignore = true)
	@Mapping(source = "coverId", target = "cover")
	@Mapping(source = "authorId", target = "author")
	Book toEntity(BookDTO bookDTO);

	default Book fromId(Long id) {
		if (id == null) {
			return null;
		}
		Book book = new Book();
		book.setId(id);
		return book;
	}
}
