package com.jher.nid_aux_histoires.service.mapper;


import com.jher.nid_aux_histoires.domain.*;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bonus} and its DTO {@link BonusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BonusMapper extends EntityMapper<BonusDTO, Bonus> {



    default Bonus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bonus bonus = new Bonus();
        bonus.setId(id);
        return bonus;
    }
}
