package com.jher.nid_aux_histoires.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class WordAnalysisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordAnalysis.class);
        WordAnalysis wordAnalysis1 = new WordAnalysis();
        wordAnalysis1.setId(1L);
        WordAnalysis wordAnalysis2 = new WordAnalysis();
        wordAnalysis2.setId(wordAnalysis1.getId());
        assertThat(wordAnalysis1).isEqualTo(wordAnalysis2);
        wordAnalysis2.setId(2L);
        assertThat(wordAnalysis1).isNotEqualTo(wordAnalysis2);
        wordAnalysis1.setId(null);
        assertThat(wordAnalysis1).isNotEqualTo(wordAnalysis2);
    }
}
