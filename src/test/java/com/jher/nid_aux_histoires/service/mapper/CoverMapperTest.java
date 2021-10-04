package com.jher.nid_aux_histoires.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoverMapperTest {

	private CoverMapper coverMapper;

	@BeforeEach
	public void setUp() {
		coverMapper = new CoverMapperImpl();
	}

	@Test
	public void testEntityFromId() {
		Long id = 1L;
		assertThat(coverMapper.fromId(id).getId()).isEqualTo(id);
		assertThat(coverMapper.fromId(null)).isNull();
	}
}
