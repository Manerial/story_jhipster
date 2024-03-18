package com.jher.nid_aux_histoires.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SceneMapperTest {

    private SceneMapper sceneMapper;

    @BeforeEach
    public void setUp() {
        sceneMapper = new SceneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sceneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sceneMapper.fromId(null)).isNull();
    }
}
