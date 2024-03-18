package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Bonus;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bonus} and its DTO {@link BonusDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonusMapper extends EntityMapper<BonusDTO, Bonus> {}
