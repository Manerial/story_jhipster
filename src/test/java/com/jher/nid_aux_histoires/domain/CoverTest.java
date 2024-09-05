package com.jher.nid_aux_histoires.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class CoverTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cover.class);
        Cover cover1 = new Cover();
        cover1.setId(1L);
        Cover cover2 = new Cover();
        cover2.setId(cover1.getId());
        assertThat(cover1).isEqualTo(cover2);
        cover2.setId(2L);
        assertThat(cover1).isNotEqualTo(cover2);
        cover1.setId(null);
        assertThat(cover1).isNotEqualTo(cover2);
    }
}
