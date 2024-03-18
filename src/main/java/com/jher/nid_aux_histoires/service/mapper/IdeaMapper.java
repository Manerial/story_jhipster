package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Idea} and its DTO {@link IdeaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IdeaMapper extends EntityMapper<IdeaDTO, Idea> {}
