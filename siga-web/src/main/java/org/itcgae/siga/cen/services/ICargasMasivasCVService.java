package org.itcgae.siga.cen.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.commons.constants.SigaConstants.CargaMasivaDatosCVVo;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ICargasMasivasCVService {

	public static final List<String> CAMPOSEJEMPLO = Arrays.asList(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo()	,CargaMasivaDatosCVVo.PERSONANIF.getCampo(),
			CargaMasivaDatosCVVo.C_FECHAINICIO.getCampo(),CargaMasivaDatosCVVo.C_FECHAFIN.getCampo(),CargaMasivaDatosCVVo.C_CREDITOS.getCampo(),CargaMasivaDatosCVVo.FECHAVERIFICACION.getCampo(),CargaMasivaDatosCVVo.C_DESCRIPCION.getCampo()
			,CargaMasivaDatosCVVo.TIPOCVCOD.getCampo(),CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo(),CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo());
	public static final List<String> CAMPOSLOG = Arrays.asList(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo() ,CargaMasivaDatosCVVo.PERSONANIF.getCampo() ,CargaMasivaDatosCVVo.PERSONANOMBRE.getCampo() ,CargaMasivaDatosCVVo.C_IDPERSONA.getCampo(),
			CargaMasivaDatosCVVo.C_FECHAINICIO.getCampo() ,CargaMasivaDatosCVVo.C_FECHAFIN.getCampo() ,CargaMasivaDatosCVVo.C_CREDITOS.getCampo() ,CargaMasivaDatosCVVo.FECHAVERIFICACION.getCampo() ,CargaMasivaDatosCVVo.C_DESCRIPCION.getCampo()
			,CargaMasivaDatosCVVo.TIPOCVCOD.getCampo(),CargaMasivaDatosCVVo.TIPOCVNOMBRE.getCampo(),CargaMasivaDatosCVVo.C_IDTIPOCV.getCampo(),CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo() ,CargaMasivaDatosCVVo.SUBTIPOCV1NOMBRE.getCampo(),CargaMasivaDatosCVVo.C_IDTIPOCVSUBTIPO1.getCampo(),CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo()	,CargaMasivaDatosCVVo.SUBTIPOCV2NOMBRE.getCampo(),CargaMasivaDatosCVVo.C_IDTIPOCVSUBTIPO2.getCampo(),CargaMasivaDatosCVVo.ERRORES.getCampo());
	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx ="xlsx";
	public static final String nombreFicheroEjemplo ="PlantillaMasivaDatosCV";
	public static final String nombreFicheroError ="LogErrorCargaMasivaCV";
	

	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException;
	
	public UpdateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException;
}
