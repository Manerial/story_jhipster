package com.jher.nid_aux_histoires.export.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jher.nid_aux_histoires.export.ExportDocx;
import com.jher.nid_aux_histoires.export.ExportDocx.FILE_FORMAT;

public class ConverterPDF implements ConverterInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterPDF.class);

	@Override
	public String[] getCommand(String bookName) {
		String inputfilepath = ExportDocx.getObjectFilePath(bookName);
		String outputfilepath = ExportDocx.getObjectFilePath(bookName, FILE_FORMAT.PDF);
		String[] cmd = { "ebook-convert", inputfilepath, outputfilepath, "--docx-inline-subsup", "--pdf-add-toc" };
		LOGGER.info(String.join(" ", cmd));
		return cmd;
	}

}
