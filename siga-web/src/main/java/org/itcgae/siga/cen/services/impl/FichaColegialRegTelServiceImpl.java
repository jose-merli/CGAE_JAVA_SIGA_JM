package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.cen.services.IFichaColegialRegTelService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialRegTelServiceImpl implements IFichaColegialRegTelService {

	private Logger LOGGER = Logger.getLogger(FichaColegialRegTelServiceImpl.class);

	@Autowired
	private DocushareHelper docushareHelper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;

	@Autowired
	private CenNocolegiadoMapper cenNocolegiadoMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public DocushareDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// numero de colegiado o numero de comunitario.
		CenColegiadoExample example = new CenColegiadoExample();
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona)).andIdinstitucionEqualTo(idInstitucion);
		List<CenColegiado> config = cenColegiadoMapper.selectByExample(example);
		
		if (config.get(0).getIdentificadords() == null) {
			LOGGER.debug("IdentificadorDS null, buscamos por colegiado " );
			if (config.get(0).getComunitario() == "0") {
				valorColegiadoDocu = config.get(0).getNcolegiado();
			} else {
				valorColegiadoDocu = config.get(0).getNcomunitario();
			}
			LOGGER.debug("ValorColegiadoDocu : " + valorColegiadoDocu);
			identificadorDS = docushareHelper.buscaCollectionCenso(valorColegiadoDocu, idInstitucion);
		} else {
			LOGGER.debug("IdentificadorDS : " + config.get(0).getIdentificadords());
			identificadorDS = config.get(0).getIdentificadords();
		}
		// NO COLEGIADO
		// identificadorDS = "Collection-179";
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, identificadorDS);
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	@Override
	public DocushareDTO searchListDir(int numPagina, DocuShareObjectVO docu, HttpServletRequest request)
			throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		identificadorDS = docu.getId();
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, docu.getParent());
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> downloadDoc(DocuShareObjectVO docushareObjectVO,
			HttpServletRequest request) throws Exception {
		File file = null;
		String identificadorDS = null;
		identificadorDS = docushareObjectVO.getId();
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		file = docushareHelper.getDocument(idInstitucion, identificadorDS);
		// Se convierte el fichero en array de bytes para enviarlo al front

		InputStream fileStream = null;
		ResponseEntity<InputStreamResource> res = null;
		try {
			fileStream = new FileInputStream(file);
			String extension = file.getName().substring(file.getName().length() - 3);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
			// if (null != extension) {
			// if (extension.equals("pdf")) {
			// headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
			// }else{
			// headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_XML_VALUE));
			// }
			// }else{
			// headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
			// }
			headers.setContentLength(file.length());
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		}
		return res;
	}

	@Override
	public DocushareDTO searchListDocNoCol(int numPagina, String idPersona, HttpServletRequest request)
			throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorNoColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// numero de colegiado o numero de comunitario.
		CenPersonaExample persona = new CenPersonaExample();
		persona.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona));
		List<CenPersona> configPersona = cenPersonaMapper.selectByExample(persona);
		CenNocolegiadoExample example = new CenNocolegiadoExample();
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona));
		List<CenNocolegiado> config = cenNocolegiadoMapper.selectByExample(example);
		
		if (config.get(0).getIdentificadords() == null) {
			if (configPersona.get(0).getIdtipoidentificacion() == 10) {
				valorNoColegiadoDocu = "NIF " + configPersona.get(0).getNifcif();
			} else if (configPersona.get(0).getIdtipoidentificacion() == 20) {
				valorNoColegiadoDocu = "CIF " + configPersona.get(0).getNifcif();
			} else {
				valorNoColegiadoDocu = "NIE " + configPersona.get(0).getNifcif();
			}
			identificadorDS = docushareHelper.buscaCollectionNoColegiado(valorNoColegiadoDocu, idInstitucion);
		} else {
			identificadorDS = config.get(0).getIdentificadords();
		}
		// NO COLEGIADO
		// identificadorDS = "Collection-179";
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, "");
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	@Override
	public DocushareDTO searchListDirNoCol(int numPagina, DocuShareObjectVO docu, HttpServletRequest request)
			throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorNoColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		// numero de colegiado o numero de comunitario.
		CenPersonaExample persona = new CenPersonaExample();
		persona.createCriteria().andIdpersonaEqualTo(Long.parseLong(docu.getIdPersona()));
		List<CenPersona> configPersona = cenPersonaMapper.selectByExample(persona);
		CenNocolegiadoExample example = new CenNocolegiadoExample();
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(docu.getIdPersona()));
		List<CenNocolegiado> config = cenNocolegiadoMapper.selectByExample(example);
		
		if (config.get(0).getIdentificadords() == null) {
			if (configPersona.get(0).getIdtipoidentificacion() == 10) {
				valorNoColegiadoDocu = "NIF " + configPersona.get(0).getNifcif();
			} else if (configPersona.get(0).getIdtipoidentificacion() == 20) {
				valorNoColegiadoDocu = "CIF " + configPersona.get(0).getNifcif();
			} else {
				valorNoColegiadoDocu = "NIE " + configPersona.get(0).getNifcif();
			}
			identificadorDS = docushareHelper.buscaCollectionNoColegiado(valorNoColegiadoDocu, idInstitucion);
		} else {
			identificadorDS = config.get(0).getIdentificadords();
		}
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, docu.getParent());
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	@Override
	public String getPermisoRegTel(HttpServletRequest request) throws Exception {
		String result = null;
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andParametroEqualTo("REGTEL");
		List<GenParametros> config = new ArrayList<GenParametros>();
		GenParametros param = new GenParametros();
		ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
		parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
		parametroRequestDTO.setModulo("GEN");
		parametroRequestDTO.setParametrosGenerales("REGTEL");
		StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
		param.setParametro("REGTEL");
		param.setValor(paramRequest.getValor());
		config.add(param);
		
		
		if (config.size() > 0) {
			result = config.get(0).getValor();
		} else {
			result = "0";
		}
		return result;
	}

	@Override
	public String insertCollection(String idPersona, HttpServletRequest request) throws Exception {

		LOGGER.info("insertCollection() -> Entrada al servicio para la insertar una nueva colección");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idDS = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertCollection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertCollection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// Buscamos a la persona
				CenPersonaExample cenPersonaExample = new CenPersonaExample();
				cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona));

				LOGGER.info(
						"insertCollection() / cenPersonaMapper.selectByExample() -> Entrada a cenColegiadoMapper para obtener información del colegiado");

				List<CenPersona> personas = cenPersonaMapper.selectByExample(cenPersonaExample);

				LOGGER.info(
						"insertCollection() / cenPersonaMapper.selectByExample() -> Salida de cenColegiadoMapper para obtener información del colegiado");

				if (null != personas && personas.size() > 0) {

					CenPersona cenPersona = personas.get(0);
					String description = cenPersona.getNombre() + " " + cenPersona.getApellidos1();

					if (cenPersona.getApellidos2() != null) {
						description += " " + cenPersona.getApellidos2();
					}

					CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
					cenColegiadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona))
							.andIdinstitucionEqualTo(idInstitucion);

					LOGGER.info(
							"insertCollection() / cenColegiadoMapper.selectByExample() -> Entrada a cenColegiadoMapper para obtener información del colegiado");

					List<CenColegiado> colegiados = cenColegiadoMapper.selectByExample(cenColegiadoExample);

					LOGGER.info(
							"insertCollection() / cenColegiadoMapper.selectByExample() -> Salida de cenColegiadoMapper para obtener información del colegiado");

					if (null != colegiados && colegiados.size() > 0) {

						CenColegiado cenColegiado = colegiados.get(0);
						String title = null;

						if (cenColegiado.getComunitario() != null && cenColegiado.getComunitario().equals("1")) {
							title = cenColegiado.getNcomunitario();
						} else {
							title = cenColegiado.getNcolegiado();
						}
						
						idDS = docushareHelper.createCollectionCenso(idInstitucion, title, description);

						cenColegiado.setIdentificadords(idDS);
						cenColegiado.setFechamodificacion(new Date());
						cenColegiado.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"insertCollection() / cenColegiadoMapper.updateByPrimaryKeySelective() -> Entrada a cenColegiadoMapper para modificar el identificador del colegiado");

						int response = cenColegiadoMapper.updateByPrimaryKeySelective(cenColegiado);

						LOGGER.info(
								"insertCollection() / cenColegiadoMapper.updateByPrimaryKeySelective() -> Salida de cenColegiadoMapper para modificar el identificador del colegiado");

						if (response == 0) {
							LOGGER.warn(
									"insertCollection() / cenColegiadoMapper.selectByExample() -> Error al modificar en el colegiado el identificadords");
						}

					} else {
						LOGGER.warn(
								"insertCollection() / cenColegiadoMapper.selectByExample() -> No existe el colegiado en la institución "
										+ idInstitucion);
					}

				} else {
					LOGGER.warn(
							"insertCollection() / cenPersonaMapper.selectByExample() -> No existe la persona en tabla cenPersona");
				}

			}

		}

		LOGGER.info("insertCollection() -> Salida al servicio para la insertar una nueva colección");

		return idDS;

	}

	@Override
	public String insertCollectionNoCol(String idPersona, HttpServletRequest request) throws Exception {

		LOGGER.info("insertCollection() -> Entrada al servicio para la insertar una nueva colección");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idDS = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertCollection() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertCollection() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				CenPersonaExample cenPersonaExample = new CenPersonaExample();
				cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona));

				LOGGER.info(
						"insertCollection() / cenPersonaMapper.selectByExample() -> Entrada a cenColegiadoMapper para obtener información del colegiado");

				List<CenPersona> personas = cenPersonaMapper.selectByExample(cenPersonaExample);

				LOGGER.info(
						"insertCollection() / cenPersonaMapper.selectByExample() -> Salida de cenColegiadoMapper para obtener información del colegiado");

				if (null != personas && personas.size() > 0) {

					CenPersona cenPersona = personas.get(0);
					String description = cenPersona.getNombre() + " " + cenPersona.getApellidos1();

					if (cenPersona.getApellidos2() != null) {
						description += " " + cenPersona.getApellidos2();
					}

					// Busqueda por nif
					String tipo = UtilidadesString.isNifNie(cenPersona.getNifcif());
					String title = null;

					if (tipo.equals(SigaConstants.NIF)) {
						title = SigaConstants.NIF + " " + cenPersona.getNifcif();
					} else if (tipo.equals(SigaConstants.NIE)) {
						title = SigaConstants.NIE + " " + cenPersona.getNifcif();
					} else {
						title = SigaConstants.CIF + " " + cenPersona.getNifcif();
					}

					CenNocolegiadoExample cenNoColegiadoExample = new CenNocolegiadoExample();
					cenNoColegiadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona))
							.andIdinstitucionEqualTo(idInstitucion);

					LOGGER.info(
							"insertCollection() / cenNocolegiadoMapper.selectByExample() -> Entrada a cenColegiadoMapper para obtener información del colegiado");

					List<CenNocolegiado> noColegiados = cenNocolegiadoMapper.selectByExample(cenNoColegiadoExample);

					LOGGER.info(
							"insertCollection() / cenNocolegiadoMapper.selectByExample() -> Salida de cenColegiadoMapper para obtener información del colegiado");

					if (null != noColegiados && noColegiados.size() > 0) {

						CenNocolegiado cenNoColegiado = noColegiados.get(0);
						
						idDS = docushareHelper.createCollectionNoColegiado(idInstitucion, title, description);

						cenNoColegiado.setIdentificadords(idDS);
						cenNoColegiado.setFechamodificacion(new Date());
						cenNoColegiado.setUsumodificacion(usuario.getIdusuario());

						LOGGER.info(
								"insertCollection() / cenNocolegiadoMapper.updateByPrimaryKeySelective() -> Entrada a cenNocolegiadoMapper para modificar el identificador del no colegiado");

						int response = cenNocolegiadoMapper.updateByPrimaryKeySelective(cenNoColegiado);

						LOGGER.info(
								"insertCollection() / cenNocolegiadoMapper.updateByPrimaryKeySelective() -> Salida de cenNocolegiadoMapper para modificar el identificador del no colegiado");

						if (response == 0) {
							LOGGER.warn(
									"insertCollection() / cenNocolegiadoMapper.selectByExample() -> Error al modificar en el no colegiado el identificadords");
						}

					} else {
						LOGGER.warn(
								"insertCollection() / cenNocolegiadoMapper.selectByExample() -> No se encuentra el no colegiado en la institución "
										+ idInstitucion);
					}

				} else {
					LOGGER.warn(
							"insertCollection() / cenPersonaMapper.selectByExample() -> No se encuentra la persona en tabla cenPersona");
				}

			}

		}

		LOGGER.info("insertCollection() -> Salida al servicio para la insertar una nueva colección");

		return idDS;
	
	}
}
