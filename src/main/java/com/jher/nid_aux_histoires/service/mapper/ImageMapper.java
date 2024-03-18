package com.jher.nid_aux_histoires.service.mapper;

import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {}
