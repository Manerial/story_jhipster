package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Comment;
import com.jher.nid_aux_histoires.service.dto.CommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {}
