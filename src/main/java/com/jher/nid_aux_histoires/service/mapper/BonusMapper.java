package com.jher.nid_aux_histoires.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jher.nid_aux_histoires.domain.Bonus;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;

/**
 * Mapper for the entity {@link Bonus} and its DTO {@link BonusDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookMapper.class })
public interface BonusMapper extends EntityMapper<BonusDTO, Bonus> {

	@Mapping(source = "book.id", target = "bookId")
	BonusDTO toDto(Bonus comment);

	@Mapping(source = "bookId", target = "book")
	Bonus toEntity(BonusDTO commentDTO);

	default Bonus fromId(Long id) {
		if (id == null) {
			return null;
		}
		Bonus bonus = new Bonus();
		bonus.setId(id);
		return bonus;
	}
}
