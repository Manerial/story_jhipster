package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.BookStatus;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;

/**
 * Mapper for the entity {@link BookStatus} and its DTO {@link BookStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookMapper.class, ChapterMapper.class, UserMapper.class })
public interface BookStatusMapper extends EntityMapper<BookStatusDTO, BookStatus> {

	@Mapping(source = "book.id", target = "bookId")
	@Mapping(source = "curentChapter.id", target = "curentChapterId")
	@Mapping(source = "user.id", target = "userId")
	BookStatusDTO toDto(BookStatus bookStatus);

	@Mapping(source = "bookId", target = "book")
	@Mapping(source = "curentChapterId", target = "curentChapter")
	@Mapping(source = "userId", target = "user.id")
	BookStatus toEntity(BookStatusDTO bookStatusDTO);

	default BookStatus fromId(Long id) {
		if (id == null) {
			return null;
		}
		BookStatus bookStatus = new BookStatus();
		bookStatus.setId(id);
		return bookStatus;
	}
}
