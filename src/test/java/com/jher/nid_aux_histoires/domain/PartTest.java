package com.jher.nid_aux_histoires.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class PartTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Part.class);
        Part part1 = new Part();
        part1.setId(1L);
        Part part2 = new Part();
        part2.setId(part1.getId());
        assertThat(part1).isEqualTo(part2);
        part2.setId(2L);
        assertThat(part1).isNotEqualTo(part2);
        part1.setId(null);
        assertThat(part1).isNotEqualTo(part2);
    }
}
