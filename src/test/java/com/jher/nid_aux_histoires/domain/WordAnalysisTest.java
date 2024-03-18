package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.WordAnalysisTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WordAnalysisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordAnalysis.class);
        WordAnalysis wordAnalysis1 = getWordAnalysisSample1();
        WordAnalysis wordAnalysis2 = new WordAnalysis();
        assertThat(wordAnalysis1).isNotEqualTo(wordAnalysis2);

        wordAnalysis2.setId(wordAnalysis1.getId());
        assertThat(wordAnalysis1).isEqualTo(wordAnalysis2);

        wordAnalysis2 = getWordAnalysisSample2();
        assertThat(wordAnalysis1).isNotEqualTo(wordAnalysis2);
    }
}
