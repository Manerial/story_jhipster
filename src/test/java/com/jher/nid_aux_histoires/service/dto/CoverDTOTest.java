package com.jher.nid_aux_histoires.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class CoverDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoverDTO.class);
        CoverDTO coverDTO1 = new CoverDTO();
        coverDTO1.setId(1L);
        CoverDTO coverDTO2 = new CoverDTO();
        assertThat(coverDTO1).isNotEqualTo(coverDTO2);
        coverDTO2.setId(coverDTO1.getId());
        assertThat(coverDTO1).isEqualTo(coverDTO2);
        coverDTO2.setId(2L);
        assertThat(coverDTO1).isNotEqualTo(coverDTO2);
        coverDTO1.setId(null);
        assertThat(coverDTO1).isNotEqualTo(coverDTO2);
    }
}
