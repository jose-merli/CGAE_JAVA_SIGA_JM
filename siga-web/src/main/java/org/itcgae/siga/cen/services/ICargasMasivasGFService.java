package org.itcgae.siga.cen.services;

import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.itcgae.siga.commons.constants.SigaConstants.CargaMasivaDatosGF;


public interface ICargasMasivasGFService {

	public static final List<CargaMasivaDatosGF> CAMPOSEJEMPLO = Arrays.asList(CargaMasivaDatosGF.COLEGIADONUMERO, CargaMasivaDatosGF.PERSONANIF, CargaMasivaDatosGF.C_IDGRUPO, CargaMasivaDatosGF.GENERAL, CargaMasivaDatosGF.ACCION);
	public static final List<CargaMasivaDatosGF> CAMPOSLOG = Arrays.asList(CargaMasivaDatosGF.COLEGIADONUMERO, CargaMasivaDatosGF.PERSONANIF, CargaMasivaDatosGF.PERSONANOMBRE, CargaMasivaDatosGF.C_IDPERSONA, CargaMasivaDatosGF.C_IDGRUPO, CargaMasivaDatosGF.GENERAL, CargaMasivaDatosGF.NOMBREGRUPO, CargaMasivaDatosGF.ACCION, CargaMasivaDatosGF.ERRORES);
	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx = "xlsx";
	public static final String nombreFicheroEjemplo ="PlantillaMasivaDatosGF";
	public static final String nombreFicheroError ="LogErrorCargaMasivaGF";	
	
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector);
//	public List<CargaMasivaDatosGF> parseExcelFile(CargaMasivaDatosGF cargaMasivaObject);
//	public void processExcelFile(CargaMasivaDatosGF cargaMasivaObject);
//	public Long uploadFile(byte[] excelBytes, CargaMasivaDatosGF cargaMasivaObject, boolean isLog);
//	public String getDirectorioFichero(Short idInstitucion);
//	public File getErrorExcelFile(CargaMasivaDatosGF cargaMasivaObject);
}
