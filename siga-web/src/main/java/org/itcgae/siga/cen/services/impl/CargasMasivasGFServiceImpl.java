package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.CargaMasivaDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.CargaMasivaDatosGFVo;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CargasMasivasGFServiceImpl implements ICargasMasivasGFService{
	
	@Autowired
	ICargasMasivasGFService cargasMasivasGFService;
	
	@Autowired
	SigaConstants sigaConstants;
	
	@Autowired
	CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;
	
	private Logger LOGGER = Logger.getLogger(CargasMasivasGFServiceImpl.class);
	
	@Override
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) throws BusinessException {
		
		LOGGER.info("createExcelFile() -> Entrada al servicio que crea la plantilla Excel");
		
		if(orderList ==null && datosVector==null)
			throw new BusinessException("No hay datos para crear el fichero");
		if(orderList ==null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = ExcelHelper.createExcelFile(orderList , datosVector, sigaConstants.nombreFicheroEjemplo);
		
		LOGGER.info("createExcelFile() -> Salida al servicio que crea la plantilla Excel");
		
		return XLSFile;
		
	}
	
	@Override
	public ResponseEntity<InputStreamResource> generateExcelEtiquetas() {
		
		LOGGER.info("generateExcelEtiquetas() -> Entrada al servicio para generar la plantilla Excel Etiquetas");

		Vector<Hashtable<String, Object>> datosVector = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();

		
		//1. Se defonen las columnas que conforman la plantilla
		
		//1.1 Se rellena la primera fila 
		datosHashtable.put(CargaMasivaDatosGFVo.COLEGIADONUMERO.getCampo(), "nnnnnn");
		datosHashtable.put(CargaMasivaDatosGFVo.PERSONANIF.getCampo(), "nnnnnnnna");
		datosHashtable.put(CargaMasivaDatosGFVo.C_IDGRUPO.getCampo(), "nnnn");
		datosHashtable.put(CargaMasivaDatosGFVo.GENERAL.getCampo(), "1/0");
		datosHashtable.put(CargaMasivaDatosGFVo.ACCION.getCampo(), "A/B");
		datosVector.add(datosHashtable);

		//1.1 Se rellena la segunda fila 
		datosHashtable = new Hashtable<String, Object>();
		datosHashtable.put(CargaMasivaDatosGFVo.COLEGIADONUMERO.getCampo(), "Opcional. Si nulo nif/cif requerido");
		datosHashtable.put(CargaMasivaDatosGFVo.PERSONANIF.getCampo(), "Opcional. Si nulo colegiadonumero requerido");
		datosHashtable.put(CargaMasivaDatosGFVo.C_IDGRUPO.getCampo(), "Requerido");
		datosHashtable.put(CargaMasivaDatosGFVo.GENERAL.getCampo(),
				"Requerido. 1 si es general, 0 si es propio del ICA");
		datosHashtable.put(CargaMasivaDatosGFVo.ACCION.getCampo(), "Requerido");
		datosVector.add(datosHashtable);

		
		//2. Crea el fichero excel
		File file = createExcelFile(sigaConstants.CAMPOSEJEMPLO, datosVector);
		
		//3. Se convierte el fichero en array de bytes para enviarlo al front 
		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(
					new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOGGER.info("generateExcelEtiquetas() -> Salida del servicio para generar la plantilla Excel Etiquetas");
		
		return res;

	}

	@Override
	public CargaMasivaDTO searchEtiquetas(CargaMasivaItem cargaMasivaItem, HttpServletRequest request) {
		
		LOGGER.info("searchEtiquetas() -> Entrada al servicio para obtener etiquetas");

		CargaMasivaDTO cargaMasivaDTO = new CargaMasivaDTO();
		List<CargaMasivaItem> cargaMasivaItemList = new ArrayList<CargaMasivaItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {

			cargaMasivaItemList = cenCargaMasivaExtendsMapper.selectEtiquetas(idInstitucion, cargaMasivaItem);
			cargaMasivaDTO.setCargaMasivaItem(cargaMasivaItemList);

			if (cargaMasivaItemList == null || cargaMasivaItemList.size() == 0) {

				LOGGER.warn(
						"searchEtiquetas() / cenCargaMasivaExtendsMapper.searchEtiquetas() -> No existen etiquetas con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchEtiquetas() -> idInstitucion del token nula");
		}

		return cargaMasivaDTO;
	}
	
}
