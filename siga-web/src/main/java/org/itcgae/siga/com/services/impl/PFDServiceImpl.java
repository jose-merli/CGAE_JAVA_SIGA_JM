package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

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

import com.pfd.ws.firma.FirmaCorporativaPDFTO;
import com.pfd.ws.firma.SelloFirmaPDFTO;
import com.pfd.ws.service.FirmaCorporativaPDFDocument;
import com.pfd.ws.service.FirmaCorporativaPDFDocument.FirmaCorporativaPDF;
import com.pfd.ws.service.FirmaCorporativaPDFResponseDocument;
import com.pfd.ws.service.FirmaCorporativaPDFResponseDocument.FirmaCorporativaPDFResponse;
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
	public String firmarPDF(File fichero) throws Exception {
		FirmaCorporativaPDFTO request = FirmaCorporativaPDFTO.Factory.newInstance();

		GenParametrosKey keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_FIRMA_LOCATION);

		GenParametros param = _genParametrosMapper.selectByPrimaryKey(keyParam);

		String firmaLocation = param.getValor();

		keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_FIRMA_VISIBLE);

		param = _genParametrosMapper.selectByPrimaryKey(keyParam);

		String firmaVisible = param.getValor();

		keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_IDCLIENTE);

		param = _genParametrosMapper.selectByPrimaryKey(keyParam);
		String idClientePFD = param.getValor();

		keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_FIRMA_RAZON);

		param = _genParametrosMapper.selectByPrimaryKey(keyParam);
		String razon = param.getValor();

		request.setIdCliente(idClientePFD);
		String base64File = "";
		String csv = "";

		if (fichero != null) {
			try (FileInputStream inFile = new FileInputStream(fichero)) {
				byte bytesData[] = new byte[(int) fichero.length()];
				inFile.read(bytesData);
				base64File = Base64.getEncoder().encodeToString(bytesData);
			} catch (FileNotFoundException e) {
				LOGGER.error("Fichero a firmar no encontrado", e);
				throw new Exception("Fichero a firmar no encontrado");
			} catch (IOException e) {
				LOGGER.error("Error al leer el fichero a firmar ", e);
				throw new Exception("Error al leer el fichero a firmar");
			}
		}

		if (base64File != null && !"".equals(base64File)) {
			request.setPdfB64(base64File);

			SelloFirmaPDFTO sello = SelloFirmaPDFTO.Factory.newInstance();

			sello.setLocalidad(firmaLocation);
			sello.setRazon(razon);
			sello.setVisible(Integer.parseInt(firmaVisible));

			request.setSelloFirma(sello);
			// request.setIdColegio(AppConstants.IDCOLEGIOCGAE);

			FirmaCorporativaPDFDocument requestDoc = FirmaCorporativaPDFDocument.Factory.newInstance();

			FirmaCorporativaPDF firma = FirmaCorporativaPDF.Factory.newInstance();
			firma.setFirmaCorporativaPDFRequest(request);

			requestDoc.setFirmaCorporativaPDF(firma);

			FirmaCorporativaPDFResponseDocument response = null;
			try {

				keyParam = new GenParametrosKey();

				keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
				keyParam.setModulo(SigaConstants.MODULO_GEN);
				keyParam.setParametro(SigaConstants.PFD_URLWS);

				param = _genParametrosMapper.selectByPrimaryKey(keyParam);
				String uriService = param.getValor();

				response = clientPFD.firmarPDF(uriService, requestDoc);
				if (response != null) {
					FirmaCorporativaPDFResponse responseDoc = response.getFirmaCorporativaPDFResponse();
					String resultado = responseDoc.getFirmaCorporativaPDFResponse().getResultado();

					if (SigaConstants.FIRMA_OK.equalsIgnoreCase(resultado)) {
						LOGGER.debug("PFDServiceImpl.firmarPDF :: Documento firmado correctamente");
						base64File = responseDoc.getFirmaCorporativaPDFResponse().getFirmaB64();
					} else {
						LOGGER.error("PFDServiceImpl.firmarPDF :: Error al firmar el documento :: " + resultado);
						throw new Exception("PFDServiceImpl.firmarPDF :: Error al firmar el documento :: " + resultado);
					}
				} else {
					LOGGER.error("PFDServiceImpl.firmarPDF :: Error al firmar el documento :: Respuesta nula");
					throw new Exception("PFDServiceImpl.firmarPDF :: Error al enviar a firmar un documento");
				}
			} catch (Exception e) {
				LOGGER.error("PFDServiceImpl.firmarPDF :: Error al enviar a firmar un documento ", e);
				throw new Exception("PFDServiceImpl.firmarPDF :: Error al enviar a firmar un documento");
			}
		} else {
			throw new Exception("PFDServiceImpl.firmarPDF :: Error al obtener el fichero a firmar");
		}

		return base64File;
	}

	@Override
	public String obtenerDocumentoFirmado(String csv) throws Exception {
		return obtenerDocumentoFirmadoModulo(csv, SigaConstants.ID_INSTITUCION_0, SigaConstants.MODULO_GEN, SigaConstants.PFD_IDCLIENTE, SigaConstants.PFD_URLWS);
	}

	@Override
	public String obtenerDocumentoEEJGFirmado(String csv) throws Exception {
		return obtenerDocumentoFirmadoModulo(csv, SigaConstants.IDINSTITUCION_2000, SigaConstants.MODULO_SCS, SigaConstants.EEJG_IDSISTEMA, SigaConstants.PFD_URLWS);
	}

	private String obtenerDocumentoFirmadoModulo(String csv, Short idInstitucion, String modulo, String parametroIdCliente, String paramentroUrl) throws Exception {
		SolicitudDocumentoTO solicitud = SolicitudDocumentoTO.Factory.newInstance();
		String documentoBase64 = "";

		GenParametrosKey keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(idInstitucion);
		keyParam.setModulo(modulo);
		keyParam.setParametro(parametroIdCliente);
		GenParametros param = _genParametrosMapper.selectByPrimaryKey(keyParam);

		String idClientePFD = param.getValor();

		solicitud.setIdAppCliente(idClientePFD);
		solicitud.setIdValidacion(csv);
		
		// cambiar esto por el csv del fichero, para pruebas en local
		//solicitud.setIdValidacion("DEM-JKTYO-NERK0-LNGBQ-CLB1Z");

		ObtenerDocumento doc = ObtenerDocumento.Factory.newInstance();
		doc.setObtenerDocumentoRequest(solicitud);

		ObtenerDocumentoDocument requestDoc = ObtenerDocumentoDocument.Factory.newInstance();

		requestDoc.setObtenerDocumento(doc);

		ObtenerDocumentoResponseDocument response = null;

		try {
			keyParam = new GenParametrosKey();

			keyParam.setIdinstitucion(idInstitucion);
			keyParam.setModulo(modulo);
			keyParam.setParametro(paramentroUrl);

			param = _genParametrosMapper.selectByPrimaryKey(keyParam);
			String uriService = param.getValor();

			response = clientPFD.obtenerDocumento(uriService, requestDoc);
			if (response != null) {
				ObtenerDocumentoResponse responseDoc = response.getObtenerDocumentoResponse();
				String resultado = responseDoc.getObtenerDocumentoResponse().getResultado();
				LOGGER.debug(responseDoc.xmlText());
				if (SigaConstants.SOLICITUD_DOCUMENTO_OK.equalsIgnoreCase(resultado)) {
					LOGGER.debug("Documento encontrado en la PFD");
					documentoBase64 = responseDoc.getObtenerDocumentoResponse().getDocumento().getFirmab64();
				} else {
					LOGGER.error("PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: "
							+ resultado);
					throw new Exception(
							"PFDServiceImpl.firmarPDF :: Error al obtener el documento firmado :: " + resultado);
				}
			} else {
				LOGGER.error(
						"PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: Respuesta nula");
				throw new Exception(
						"PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: Respuesta nula");
			}

		} catch (Exception e) {
			LOGGER.error("Error al enviar a firmar un documento ", e);
			throw new Exception("Error al enviar a firmar un documento");
		}

		return documentoBase64;
	}

	@Override
	public byte[] getBytes(String file) throws Exception {
		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		inputStream.close();

		return bytes;
	}

	@Override
	public File createTempFile(byte[] bytes, String fileName) throws Exception {
		File returnFile = null;

		try {
			returnFile = new File(fileName);

			FileOutputStream fileOutputStream = new FileOutputStream(returnFile);

			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();

			returnFile.deleteOnExit();

		} catch (Exception e) {
			LOGGER.debug("Se ha producido un error al createFile" + e.toString());
			throw new Exception("Se ha producido un error al createFile");
		}

		return returnFile;
	}

}
