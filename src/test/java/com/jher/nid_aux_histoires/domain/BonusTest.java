package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.BonusTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonus.class);
        Bonus bonus1 = getBonusSample1();
        Bonus bonus2 = new Bonus();
        assertThat(bonus1).isNotEqualTo(bonus2);

        bonus2.setId(bonus1.getId());
        assertThat(bonus1).isEqualTo(bonus2);

        bonus2 = getBonusSample2();
        assertThat(bonus1).isNotEqualTo(bonus2);
    }
}
