package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Idea} and its DTO {@link IdeaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IdeaMapper extends EntityMapper<IdeaDTO, Idea> {



    default Idea fromId(Long id) {
        if (id == null) {
            return null;
        }
        Idea idea = new Idea();
        idea.setId(id);
        return idea;
    }
}
