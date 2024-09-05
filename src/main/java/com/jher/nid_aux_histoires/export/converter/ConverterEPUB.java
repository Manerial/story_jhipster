package com.jher.nid_aux_histoires.export.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jher.nid_aux_histoires.export.ExportDocx;
import com.jher.nid_aux_histoires.export.ExportDocx.FILE_FORMAT;

public class ConverterEPUB implements ConverterInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterEPUB.class);

	@Override
	public void startConvertion(String bookName) throws IOException {
		String[] cmd = this.getCommand(bookName);
		Runtime run = Runtime.getRuntime();
		Process pr = run.exec(cmd);
		// pr.waitFor();
		BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		while ((line = buf.readLine()) != null) {
			LOGGER.info(line);
		}
	}

	private String[] getCommand(String bookName) {
		String inputfilepath = ExportDocx.getObjectFilePath(bookName);
		String outputfilepath = ExportDocx.getObjectFilePath(bookName, FILE_FORMAT.EPUB);
		String[] cmd = { "ebook-convert", inputfilepath, outputfilepath, "--docx-inline-subsup", "--epub-inline-toc" };
		LOGGER.info(String.join(" ", cmd));
		return cmd;
	}

}
