package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.CommentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = getCommentSample1();
        Comment comment2 = new Comment();
        assertThat(comment1).isNotEqualTo(comment2);

        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);

        comment2 = getCommentSample2();
        assertThat(comment1).isNotEqualTo(comment2);
    }
}
