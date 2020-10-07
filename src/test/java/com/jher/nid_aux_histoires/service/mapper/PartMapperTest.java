package com.jher.nid_aux_histoires.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PartMapperTest {

    private PartMapper partMapper;

    @BeforeEach
    public void setUp() {
        partMapper = new PartMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(partMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partMapper.fromId(null)).isNull();
    }
}
