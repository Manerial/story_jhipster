package com.jher.nid_aux_histoires.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WordAnalysisMapperTest {

    private WordAnalysisMapper wordAnalysisMapper;

    @BeforeEach
    public void setUp() {
        wordAnalysisMapper = new WordAnalysisMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wordAnalysisMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wordAnalysisMapper.fromId(null)).isNull();
    }
}
