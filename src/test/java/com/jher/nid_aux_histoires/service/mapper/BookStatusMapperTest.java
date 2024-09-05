package com.jher.nid_aux_histoires.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BookStatusMapperTest {

    private BookStatusMapper bookStatusMapper;

    @BeforeEach
    public void setUp() {
        bookStatusMapper = new BookStatusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bookStatusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bookStatusMapper.fromId(null)).isNull();
    }
}
