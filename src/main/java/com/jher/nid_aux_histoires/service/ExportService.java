package com.jher.nid_aux_histoires.service;

import java.nio.file.Path;

public interface ExportService {
	Path getPathOfExportedBook(long id);

	Path exportBook(long id);
}
