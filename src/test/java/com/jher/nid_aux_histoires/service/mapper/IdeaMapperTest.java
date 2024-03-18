package com.jher.nid_aux_histoires.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IdeaMapperTest {

    private IdeaMapper ideaMapper;

    @BeforeEach
    public void setUp() {
        ideaMapper = new IdeaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ideaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ideaMapper.fromId(null)).isNull();
    }
}
