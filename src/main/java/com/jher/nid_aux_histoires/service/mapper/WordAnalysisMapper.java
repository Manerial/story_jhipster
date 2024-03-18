package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.WordAnalysis;
import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WordAnalysis} and its DTO {@link WordAnalysisDTO}.
 */
@Mapper(componentModel = "spring")
public interface WordAnalysisMapper extends EntityMapper<WordAnalysisDTO, WordAnalysis> {}
