package org.itcgae.siga.cen.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDatosGFItem;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ICargasMasivasGFService {
	
	public static final List<String> CAMPOSEJEMPLO = Arrays.asList(CargaMasivaDatosGFItem.COLEGIADONUMERO, CargaMasivaDatosGFItem.PERSONANIF, 
			CargaMasivaDatosGFItem.C_IDGRUPO, CargaMasivaDatosGFItem.GENERAL, CargaMasivaDatosGFItem.ACCION, CargaMasivaDatosGFItem.C_FECHAINICIO,
			CargaMasivaDatosGFItem.C_FECHAFIN);

	
	public static final List<String> CAMPOSLOG = Arrays.asList(CargaMasivaDatosGFItem.COLEGIADONUMERO, CargaMasivaDatosGFItem.PERSONANIF, 
			CargaMasivaDatosGFItem.PERSONANOMBRE, CargaMasivaDatosGFItem.C_IDPERSONA, CargaMasivaDatosGFItem.C_IDGRUPO, 
			CargaMasivaDatosGFItem.GENERAL, CargaMasivaDatosGFItem.NOMBREGRUPO, CargaMasivaDatosGFItem.ACCION, CargaMasivaDatosGFItem.C_FECHAINICIO,
			CargaMasivaDatosGFItem.C_FECHAFIN, CargaMasivaDatosGFItem.ERRORES);

	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx = "xlsx";
	public static final String nombreFicheroEjemplo = "PlantillaMasivaDatosGF";
	public static final String nombreFicheroError = "LogErrorCargaMasivaGF";


	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	public ResponseEntity<InputStreamResource> generateExcelEtiquetas();
	public CargaMasivaDTO searchEtiquetas(CargaMasivaItem cargaMasivaItem, HttpServletRequest request);
	public UpdateResponseDTO uploadFileExcel(MultipartHttpServletRequest request) throws IllegalStateException, IOException;
	public ResponseEntity<InputStreamResource> downloadOriginalFile(CargaMasivaItem cargaMasivaItem, HttpServletRequest request);
	public ResponseEntity<InputStreamResource> downloadLogFile(CargaMasivaItem cargaMasivaItem, HttpServletRequest request);

	
}
