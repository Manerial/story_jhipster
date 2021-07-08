package com.jher.nid_aux_histoires.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class BookStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookStatus.class);
        BookStatus bookStatus1 = new BookStatus();
        bookStatus1.setId(1L);
        BookStatus bookStatus2 = new BookStatus();
        bookStatus2.setId(bookStatus1.getId());
        assertThat(bookStatus1).isEqualTo(bookStatus2);
        bookStatus2.setId(2L);
        assertThat(bookStatus1).isNotEqualTo(bookStatus2);
        bookStatus1.setId(null);
        assertThat(bookStatus1).isNotEqualTo(bookStatus2);
    }
}
