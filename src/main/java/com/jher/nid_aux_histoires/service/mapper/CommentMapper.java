package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Comment;
import com.jher.nid_aux_histoires.service.dto.CommentDTO;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookMapper.class, UserMapper.class })
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

	@Mapping(source = "book.id", target = "bookId")
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.login", target = "userLogin")
	CommentDTO toDto(Comment comment);

	@Mapping(source = "bookId", target = "book")
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "createdDate", ignore = true)
	Comment toEntity(CommentDTO commentDTO);

	default Comment fromId(Long id) {
		if (id == null) {
			return null;
		}
		Comment comment = new Comment();
		comment.setId(id);
		return comment;
	}
}
