package com.jher.nid_aux_histoires.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class BonusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonus.class);
        Bonus bonus1 = new Bonus();
        bonus1.setId(1L);
        Bonus bonus2 = new Bonus();
        bonus2.setId(bonus1.getId());
        assertThat(bonus1).isEqualTo(bonus2);
        bonus2.setId(2L);
        assertThat(bonus1).isNotEqualTo(bonus2);
        bonus1.setId(null);
        assertThat(bonus1).isNotEqualTo(bonus2);
    }
}
