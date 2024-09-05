package com.jher.nid_aux_histoires.service;

import java.nio.file.Path;

import org.springframework.http.MediaType;

import com.jher.nid_aux_histoires.export.ExportDocx;

public interface ExportService {
	Path getPathOfExportedBook(long id, ExportDocx.FILE_FORMAT format) throws Exception;

	boolean exportBook(long id);

	MediaType getMediaType(ExportDocx.FILE_FORMAT format);
}
