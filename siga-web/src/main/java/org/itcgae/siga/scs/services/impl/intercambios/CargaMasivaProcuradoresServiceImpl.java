package org.itcgae.siga.scs.services.impl.intercambios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.scs.services.intercambios.ICargaMasivaProcuradores;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class CargaMasivaProcuradoresServiceImpl implements ICargaMasivaProcuradores {

	@Override
	public InputStreamResource descargarModelo(HttpServletRequest request)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		ByteArrayInputStream byteInput = crearExcel();

		return new InputStreamResource(byteInput);

	}

	private ByteArrayInputStream crearExcel() {
		List<String> cabeceraExcel = Arrays.asList("CODIGODESIGNAABOGADO", "NUMEJG", "NUMCOLPROCURADOR",
				"NUMDESIGNAPROCURADOR", "FECHADESIGPROCURADOR", "OBSERVACIONES");
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Modelo Carga Masiva Procuradores");

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < cabeceraExcel.size(); col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cabeceraExcel.get(col));
			}

			Row row = sheet.createRow(1);

			row.createCell(0).setCellValue("nnnn/nnnnn");
			row.createCell(1).setCellValue("nnnn/nnnnn");
			row.createCell(2).setCellValue("nnnnn");
			row.createCell(3).setCellValue("nnnnn");
			row.createCell(4).setCellValue("dd/mm/yyyy");
			row.createCell(5).setCellValue("aaaaaaaaaaaaa");
			
			Row row2 = sheet.createRow(2);

			row2.createCell(0).setCellValue("Requerido");
			row2.createCell(1).setCellValue("Opcional");
			row2.createCell(2).setCellValue("Requerido");
			row2.createCell(3).setCellValue("Opcional");
			row2.createCell(4).setCellValue("Requerido");
			row2.createCell(5).setCellValue("Opcional");

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Error al crear el archivo Excel: " + e.getMessage());
		}
	}

}
