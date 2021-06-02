package com.jher.nid_aux_histoires.web.rest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * REST controller for managing {@link com.jher.nid_aux_histoires.domain.Image}.
 */
@RestController
@RequestMapping("/api")
public class ToolResource {

	private final Logger log = LoggerFactory.getLogger(ToolResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	public ToolResource() {
	}

	/**
	 * {@code GET  /qr_code} : Get the QR_Code of a text.
	 *
	 * @param qrCodeText the text to transform.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)}
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException
	 * @throws WriterException
	 */
	@GetMapping("/qr_code")
	public ResponseEntity<byte[]> getQR_CODE(@RequestBody String qrCodeText)
			throws URISyntaxException, WriterException, IOException {
		int size = 500;
		byte[] qr_code = createQRImage(qrCodeText, size);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"QR_CODE.png\"")
				.contentType(MediaType.IMAGE_PNG).contentLength(qr_code.length).body(qr_code);
	}

	private static byte[] createQRImage(String qrCodeText, int size) throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);

		// Make the BufferedImage that are to hold the QRCode
		int[] coordinates = byteMatrix.getEnclosingRectangle();
		int left = coordinates[0];
		int top = coordinates[1];
		int width = coordinates[2];
		int height = coordinates[3];

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		// graphics.setColor(new Color(0x0000000, true));
		graphics.fillRect(0, 0, width, height);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = top; i <= height + top; i++) {
			for (int j = left; j <= width + left; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i - top, j - left, 1, 1);
				}
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
}
