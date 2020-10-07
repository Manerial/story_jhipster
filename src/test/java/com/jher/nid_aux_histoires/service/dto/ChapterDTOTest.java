package com.jher.nid_aux_histoires.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class ChapterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChapterDTO.class);
        ChapterDTO chapterDTO1 = new ChapterDTO();
        chapterDTO1.setId(1L);
        ChapterDTO chapterDTO2 = new ChapterDTO();
        assertThat(chapterDTO1).isNotEqualTo(chapterDTO2);
        chapterDTO2.setId(chapterDTO1.getId());
        assertThat(chapterDTO1).isEqualTo(chapterDTO2);
        chapterDTO2.setId(2L);
        assertThat(chapterDTO1).isNotEqualTo(chapterDTO2);
        chapterDTO1.setId(null);
        assertThat(chapterDTO1).isNotEqualTo(chapterDTO2);
    }
}
