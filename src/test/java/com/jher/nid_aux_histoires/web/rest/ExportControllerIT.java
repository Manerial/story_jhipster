package com.jher.nid_aux_histoires.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.export.ExportDocx.FILE_FORMAT;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.ExportService;

/**
 * Integration tests for the {@link ChapterResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExportControllerIT {

	@Autowired
	private MockMvc restExportMockMvc;

	@Autowired
	private ExportService exportService;

	@Test
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void export() throws Exception {
		restExportMockMvc.perform(post("/api/export/book/{id}", 1l)).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getAllFormats() throws Exception {
		restExportMockMvc.perform(get("/api/download/formats")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getBook() throws Exception {
		FILE_FORMAT format = FILE_FORMAT.PDF;
		restExportMockMvc.perform(get("/api/download/book/{id}?format=" + format, 1l)).andExpect(status().isOk())
				.andExpect(content().contentType(exportService.getMediaType(format)));

		format = FILE_FORMAT.DOCX;
		restExportMockMvc.perform(get("/api/download/book/{id}?format=" + format, 1l)).andExpect(status().isOk())
				.andExpect(content().contentType(exportService.getMediaType(format)));
	}
}
