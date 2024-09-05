package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Library;
import com.jher.nid_aux_histoires.service.dto.LibraryDTO;

/**
 * Mapper for the entity {@link Library} and its DTO {@link LibraryDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookMapper.class, ChapterMapper.class, UserMapper.class })
public interface LibraryMapper extends EntityMapper<LibraryDTO, Library> {

	@Mapping(source = "book.id", target = "bookId")
	@Mapping(source = "curentChapter.id", target = "curentChapterId")
	@Mapping(source = "user.id", target = "userId")
	LibraryDTO toDto(Library library);

	@Mapping(source = "bookId", target = "book")
	@Mapping(source = "curentChapterId", target = "curentChapter")
	@Mapping(source = "userId", target = "user.id")
	Library toEntity(LibraryDTO libraryDTO);

	default Library fromId(Long id) {
		if (id == null) {
			return null;
		}
		Library library = new Library();
		library.setId(id);
		return library;
	}
}
