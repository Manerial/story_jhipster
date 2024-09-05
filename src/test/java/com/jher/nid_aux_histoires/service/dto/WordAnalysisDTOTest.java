package com.jher.nid_aux_histoires.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class WordAnalysisDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordAnalysisDTO.class);
        WordAnalysisDTO wordAnalysisDTO1 = new WordAnalysisDTO();
        wordAnalysisDTO1.setId(1L);
        WordAnalysisDTO wordAnalysisDTO2 = new WordAnalysisDTO();
        assertThat(wordAnalysisDTO1).isNotEqualTo(wordAnalysisDTO2);
        wordAnalysisDTO2.setId(wordAnalysisDTO1.getId());
        assertThat(wordAnalysisDTO1).isEqualTo(wordAnalysisDTO2);
        wordAnalysisDTO2.setId(2L);
        assertThat(wordAnalysisDTO1).isNotEqualTo(wordAnalysisDTO2);
        wordAnalysisDTO1.setId(null);
        assertThat(wordAnalysisDTO1).isNotEqualTo(wordAnalysisDTO2);
    }
}
