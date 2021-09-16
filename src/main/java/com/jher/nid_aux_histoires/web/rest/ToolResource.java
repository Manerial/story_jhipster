package com.jher.nid_aux_histoires.web.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jher.nid_aux_histoires.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Cover}.
 */
@RestController
@RequestMapping("/api")
public class ToolResource {

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	enum EncoderType {
		QRCODE, DATAMATRIX,
	}

	/**
	 * {@code GET  /encode_text} : Get the QR_Code of a text.
	 *
	 * @param text          the text to transform.
	 * @param barcodeFormat the barcode format (QRCode, DataMatrix, etc)
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)}
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException        if the image cannot be read
	 * @throws WriterException    if the image cannot be write
	 */
	@GetMapping("/encode_text")
	public ResponseEntity<byte[]> generateEncodedImage(@RequestParam String text,
			@RequestParam BarcodeFormat barcodeFormat) throws URISyntaxException, WriterException, IOException {
		int size = 500;
		byte[] generatedImage = null;

		switch (barcodeFormat) {
		case DATA_MATRIX:
			generatedImage = createImage(text, size, new DataMatrixWriter(), barcodeFormat);
			break;
		case QR_CODE:
			generatedImage = createImage(text, size, new QRCodeWriter(), barcodeFormat);
			break;
		case AZTEC:
			generatedImage = createImage(text, size, new AztecWriter(), barcodeFormat);
			break;
		case CODABAR:
			generatedImage = createImage(text, size, new CodaBarWriter(), barcodeFormat);
			break;
		case CODE_128:
			generatedImage = createImage(text, size, new Code128Writer(), barcodeFormat);
			break;
		case CODE_39:
			generatedImage = createImage(text, size, new Code39Writer(), barcodeFormat);
			break;
		case CODE_93:
			generatedImage = createImage(text, size, new Code93Writer(), barcodeFormat);
			break;
		case EAN_13:
			generatedImage = createImage(text, size, new EAN13Writer(), barcodeFormat);
			break;
		case EAN_8:
			generatedImage = createImage(text, size, new EAN8Writer(), barcodeFormat);
			break;
		case ITF:
			generatedImage = createImage(text, size, new ITFWriter(), barcodeFormat);
			break;
		case PDF_417:
			generatedImage = createImage(text, size, new PDF417Writer(), barcodeFormat);
			break;
		case UPC_A:
			generatedImage = createImage(text, size, new UPCAWriter(), barcodeFormat);
			break;
		case UPC_E:
			generatedImage = createImage(text, size, new UPCEWriter(), barcodeFormat);
			break;
		default:
			throw new BadRequestAlertException("The BarcodeFormat does not exist", "BarcodeFormat", "donotexist");
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"CodeImage.png\"")
				.contentType(MediaType.IMAGE_PNG).contentLength(generatedImage.length).body(generatedImage);
	}

	/**
	 * {@code GET  /decode_image} : Get the text of an encoded image.
	 *
	 * @param image the image to decode.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)}
	 * @throws IOException if the image cannot be read
	 */
	@PostMapping("/decode_image")
	public ResponseEntity<String> decodeImage(@RequestParam MultipartFile image) throws IOException {
		return ResponseEntity.ok().body(decodeQRCode(image));
	}

	private static String decodeQRCode(MultipartFile image) throws IOException {
		InputStream is = new ByteArrayInputStream(image.getBytes());
		BufferedImage bufferedImage = ImageIO.read(is);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no code in the image");
			return null;
		}
	}

	private static byte[] createImage(String codeText, int size, Writer writer, BarcodeFormat bcf)
			throws WriterException, IOException {
		BitMatrix byteMatrix = writer.encode(codeText, bcf, size, size);

		BufferedImage image = MatrixToImageWriter.toBufferedImage(byteMatrix);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		byte[] bytes = baos.toByteArray();

		return bytes;

	}
}
