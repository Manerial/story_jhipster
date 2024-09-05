package com.jher.nid_aux_histoires.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageProperties;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

import com.jher.nid_aux_histoires.export.converter.ConverterEPUB;
import com.jher.nid_aux_histoires.export.converter.ConverterInterface;
import com.jher.nid_aux_histoires.export.converter.ConverterPDF;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.dto.PartDTO;

@Service
public class ExportDocx {

	@Value("${export.model.path}")
	private String modelFilePath;

	@Value("${export.model.book.name}")
	private String modelBookFileName;

	@Value("${export.model.part.name}")
	private String modelPartFileName;

	private WordprocessingMLPackage mainDoc;

	public static enum FILE_FORMAT {
		docx, pdf, epub
	};

	public static final String TEMP_DIR = System.getProperty("user.home") + "\\BooksExports\\";
	public static final String LINE_BREAK_PLACEHOLDER = "§";
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportDocx.class);

	public ExportDocx() {
		File file = new File(TEMP_DIR);
		file.mkdir();
	}

	public Path launchGeneration(BookDTO book)
			throws Docx4JException, IOException, JAXBException, InvalidFormatException {
		LOGGER.info("Début de la génération du livre WORD {}", book.getName());
		File outputFile = buildWordFile(modelBookFileName, book.getName(), book);
		mainDoc = Docx4J.load(outputFile);

		List<File> cvJobs = this.generateContent(book);
		appendFileList(cvJobs);
		mainDoc.save(outputFile);

		setProperties(outputFile.getPath(), book.getAuthor(), book.getName());

		LOGGER.info("Fin de la génération du livre WORD");
		return new File(outputFile.getPath()).toPath();
	}

	public void convertWordToFormat(BookDTO book, FILE_FORMAT format)
			throws FileNotFoundException, Docx4JException, Exception {

		String bookName = book.getName();
		LOGGER.info("Début de la génération du livre {} {}", format, bookName);
		ConverterInterface CI = null;
		switch (format) {
		case epub:
			CI = new ConverterEPUB();
			break;
		case pdf:
			CI = new ConverterPDF();
			break;
		default:
			throw new Exception("Converter not found");
		}
		String[] cmd = CI.getCommand(bookName);
		Runtime run = Runtime.getRuntime();
		Process pr = run.exec(cmd);
		// pr.waitFor();
		BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		while ((line = buf.readLine()) != null) {
			LOGGER.info(line);
		}
		LOGGER.info("Fin de la génération du livre {}", format);
	}

	public static String getObjectFilePath(String objectName) {
		return getObjectFilePath(objectName, FILE_FORMAT.docx);
	}

	public static String getObjectFilePath(String objectName, FILE_FORMAT format) {
		return TEMP_DIR + objectName + "." + format;
	}

	private List<File> generateContent(BookDTO book) throws IOException, Docx4JException {
		List<File> fileList = new ArrayList<>();
		for (PartDTO part : book.getParts()) {
			fileList.add(buildWordFile(modelPartFileName, "Part_" + part.getId(), part));
		}
		return fileList;
	}

	private File buildWordFile(String modelFileName, String objectName, Object object)
			throws IOException, FileNotFoundException, Docx4JException {
		LOGGER.info("Chargement du fichier model : " + modelFileName);
		WordprocessingMLPackage wordMLPackage = Docx4J.load(new java.io.File(modelFilePath + modelFileName));
		String outputFilePath = getObjectFilePath(objectName);
		LOGGER.info("Chargement du fichier de sortie : " + outputFilePath);
		File outputFile = new File(outputFilePath);
		try (OutputStream out = new FileOutputStream(outputFile)) {
			DocxStamper stamper = buildDocxStamper();
			stamper.stamp(wordMLPackage, object, out);
			return outputFile;
		} catch (IOException e) {
			LOGGER.warn("Problème avec le modele suivant : " + outputFile.getAbsolutePath(), e);
			throw new IOException("Erreur lors de la génération du template : " + objectName);
		}
	}

	private void setProperties(String file, String author, String title) throws IOException, InvalidFormatException {
		OPCPackage opc = OPCPackage.open(file);
		PackageProperties pp = opc.getPackageProperties();

		pp.setCreatorProperty(author);
		pp.setTitleProperty(title);

		opc.close();
	}

	private void appendFileList(List<File> files) throws IOException, Docx4JException {
		for (File file : files) {
			appendFile(file);
		}
	}

	private void appendFile(File file) throws IOException, Docx4JException {
		List<Object> objects = Docx4J.load(file).getMainDocumentPart().getContent();
		for (Object o : objects) {
			mainDoc.getMainDocumentPart().getContent().add(o);
		}
		Files.delete(file.toPath());
	}

	private DocxStamper buildDocxStamper() {
		return new DocxStamperConfiguration().setLineBreakPlaceholder(LINE_BREAK_PLACEHOLDER)
				.leaveEmptyOnExpressionError(true).build();
	}
}
