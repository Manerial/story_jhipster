package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WordAnalysis} and its DTO {@link WordAnalysisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WordAnalysisMapper extends EntityMapper<WordAnalysisDTO, WordAnalysis> {



    default WordAnalysis fromId(Long id) {
        if (id == null) {
            return null;
        }
        WordAnalysis wordAnalysis = new WordAnalysis();
        wordAnalysis.setId(id);
        return wordAnalysis;
    }
}
