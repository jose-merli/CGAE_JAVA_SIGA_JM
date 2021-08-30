package org.itcgae.siga.com.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itcgae.siga.DTOs.com.DatosDocumentoItem;

import com.aspose.words.Document;

public interface IGeneracionDocumentosService {

	public Document sustituyeDocumento(Document doc, HashMap<String, Object> dato) throws Exception;
	
	public DatosDocumentoItem grabaDocumento(Document doc, String pathfinal, String nombrefichero, boolean firmado) throws Exception;

	public DatosDocumentoItem generarExcel(String string, String pathFicheroSalida, String nombreFicheroSalida,	List<List<Map<String, Object>>> listaDatosExcel, ArrayList<String> nombresConsultasDatos) throws Exception;

	public DatosDocumentoItem generarExcelGeneracionCalendario(String pathFicheroSalida, String nombreFicheroSalida,
			List<List<Map<String, Object>>> listaDatosExcel) throws Exception;
	
	
}
