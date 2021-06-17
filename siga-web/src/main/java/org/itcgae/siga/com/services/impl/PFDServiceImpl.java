package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.ws.client.ClientPFD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfd.ws.service.ObtenerDocumentoDocument;
import com.pfd.ws.service.ObtenerDocumentoDocument.ObtenerDocumento;
import com.pfd.ws.service.ObtenerDocumentoResponseDocument;
import com.pfd.ws.service.ObtenerDocumentoResponseDocument.ObtenerDocumentoResponse;
import com.pfd.ws.validacionfirma.SolicitudDocumentoTO;

@Service
public class PFDServiceImpl implements IPFDService {

	
	Logger LOGGER = Logger.getLogger(PFDServiceImpl.class);	
	
	@Autowired
	ClientPFD clientPFD;
	
	@Autowired	
	GenPropertiesMapper _genPropertiesMapper;
	
	@Autowired	
	GenParametrosMapper _genParametrosMapper;
	
	@Override
	public String obtenerDocumentoFirmado(String csv) throws Exception {
		SolicitudDocumentoTO solicitud = SolicitudDocumentoTO.Factory.newInstance();
		String documentoBase64 = "";
		
		GenParametrosKey keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_IDCLIENTE);
		GenParametros param = _genParametrosMapper.selectByPrimaryKey(keyParam);
		
		String idClientePFD = param.getValor();
		
		solicitud.setIdAppCliente(idClientePFD);
//		solicitud.setIdValidacion(csv);
		solicitud.setIdValidacion("DEM-JKTYO-NERK0-LNGBQ-CLB1Z");//cambiar esto por el csv del fichero, solo para que funcione en local
		
		ObtenerDocumento doc = ObtenerDocumento.Factory.newInstance();
		doc.setObtenerDocumentoRequest(solicitud);
		
		ObtenerDocumentoDocument requestDoc = ObtenerDocumentoDocument.Factory.newInstance();
		
		requestDoc.setObtenerDocumento(doc);
			
		ObtenerDocumentoResponseDocument response = null;
		
		try {
			keyParam = new GenParametrosKey();

			keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
			keyParam.setModulo(SigaConstants.MODULO_GEN);
			keyParam.setParametro(SigaConstants.PFD_URLWS);
			
			param = _genParametrosMapper.selectByPrimaryKey(keyParam);
			String uriService = param.getValor();
			
			response = clientPFD.obtenerDocumento(uriService, requestDoc);
			if(response != null){
				ObtenerDocumentoResponse responseDoc = response.getObtenerDocumentoResponse();
				String resultado = responseDoc.getObtenerDocumentoResponse().getResultado();
				LOGGER.debug(responseDoc.xmlText());
				if(SigaConstants.SOLICITUD_DOCUMENTO_OK.equalsIgnoreCase(resultado)){
					LOGGER.debug("Documento encontrado en la PFD");
					documentoBase64 = responseDoc.getObtenerDocumentoResponse().getDocumento().getFirmab64();
				}else{
					LOGGER.error("PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: " + resultado);
					throw new Exception("PFDServiceImpl.firmarPDF :: Error al obtener el documento firmado :: " + resultado);
				}
			}else{
				LOGGER.error("PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: Respuesta nula");
				throw new Exception("PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: Respuesta nula");
			}
			
		} catch (Exception e) {
			LOGGER.error("Error al enviar a firmar un documento ", e);
			throw new Exception("Error al enviar a firmar un documento");
		}
		
		return documentoBase64;
	}
	
	@Override
	public byte[] getBytes(String file) throws Exception{
		FileInputStream inputStream =  new FileInputStream(file);
		byte [] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		inputStream.close();
		
		return bytes;
	}
	
	@Override
	public File createTempFile(byte[] bytes,String fileName) throws Exception{
		File returnFile = null;
		
		try{	
			returnFile = new File(fileName);
    		
			FileOutputStream fileOutputStream  = new FileOutputStream(returnFile);	
			
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			returnFile.deleteOnExit();

		} catch (Exception e) {
			LOGGER.debug("Se ha producido un error al createFile"+e.toString());
			throw new Exception("Se ha producido un error al createFile");
		}
		
		return returnFile;
	}

}
