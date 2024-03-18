package com.jher.nid_aux_histoires.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookStatusDTO.class);
        BookStatusDTO bookStatusDTO1 = new BookStatusDTO();
        bookStatusDTO1.setId(1L);
        BookStatusDTO bookStatusDTO2 = new BookStatusDTO();
        assertThat(bookStatusDTO1).isNotEqualTo(bookStatusDTO2);
        bookStatusDTO2.setId(bookStatusDTO1.getId());
        assertThat(bookStatusDTO1).isEqualTo(bookStatusDTO2);
        bookStatusDTO2.setId(2L);
        assertThat(bookStatusDTO1).isNotEqualTo(bookStatusDTO2);
        bookStatusDTO1.setId(null);
        assertThat(bookStatusDTO1).isNotEqualTo(bookStatusDTO2);
    }
}
