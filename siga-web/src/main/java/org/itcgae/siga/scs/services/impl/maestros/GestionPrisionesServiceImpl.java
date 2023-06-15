package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.PrisionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsPrision;
import org.itcgae.siga.db.entities.ScsPrisionExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrisionExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IGestionPrisionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionPrisionesServiceImpl implements IGestionPrisionesService {

	private Logger LOGGER = Logger.getLogger(GestionPrisionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsPrisionExtendsMapper scsPrisionExtendsMapper;

	@Override
	public UpdateResponseDTO updatePrision(PrisionItem prisionItem, HttpServletRequest request) {

		LOGGER.info("updatePrision() ->  Entrada al servicio para editar prision");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;
		Boolean codeext = true;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updatePrision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updatePrision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					if(prisionItem.getCodigoExt() != null) {
						ScsPrisionExample scsPrisionExample2 = new ScsPrisionExample();
						scsPrisionExample2.createCriteria().andCodigoextEqualTo(prisionItem.getCodigoExt())
						.andIdprisionNotEqualTo(Long.valueOf(prisionItem.getIdPrision())).andIdinstitucionEqualTo(idInstitucion);
						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision(codigoExt)");

						List<ScsPrision> prisionList2 = scsPrisionExtendsMapper.selectByExample(scsPrisionExample2);

						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión (codigoExt)");

						if (prisionList2 != null && prisionList2.size() > 0) {
							error.setCode(400);
							error.setDescription("prisiones.error.literal.existePrisionCode");
							codeext = false;
						}
					}

					
					if(codeext) {
						ScsPrisionExample scsExample  = new ScsPrisionExample();
						scsExample.createCriteria().andNombreEqualTo(prisionItem.getNombre())
						.andIdprisionNotEqualTo(Long.valueOf(prisionItem.getIdPrision())).andIdinstitucionEqualTo(idInstitucion);
						List<ScsPrision> scsPrisionListExample = scsPrisionExtendsMapper.selectByExample(scsExample);
						if (scsPrisionListExample != null && scsPrisionListExample.size() > 0) {
							error.setCode(400);
							error.setDescription("prisiones.error.literal.existePrision");
							codeext = false;
						}else {
						
						
						ScsPrisionExample scsPrisionExample  = new ScsPrisionExample();
						scsPrisionExample.createCriteria().andIdprisionEqualTo(Long.valueOf(prisionItem.getIdPrision()))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updatePrision() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsPrision> scsPrisionList = scsPrisionExtendsMapper.selectByExample(scsPrisionExample);

						LOGGER.info(
								"updatePrision() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prision");

						ScsPrision prision = scsPrisionList.get(0);

						prision.setNombre(prisionItem.getNombre());
						prision.setFechamodificacion(new Date());
						prision.setUsumodificacion(usuario.getIdusuario().intValue());
						prision.setDomicilio(prisionItem.getDomicilio());
						prision.setCodigopostal(prisionItem.getCodigoPostal());
						prision.setIdpoblacion(prisionItem.getIdPoblacion());
						prision.setIdprovincia(prisionItem.getIdProvincia());
						prision.setTelefono1(prisionItem.getTelefono1());
						prision.setTelefono2(prisionItem.getTelefono2());
						prision.setFax1(prisionItem.getFax());
						prision.setCodigoext(prisionItem.getCodigoExt());
						prision.setEmail(prisionItem.getEmail());
						prision.setVisiblemovil(prisionItem.getVisibleMovil());

						response = scsPrisionExtendsMapper.updateByPrimaryKey(prision);
					}
					}
					
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if(response == 1){
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updatePrision() -> Salida del servicio para editar prision");

		return updateResponseDTO;
	
	}

	@Override
	public InsertResponseDTO createPrision(PrisionItem prisionItem, HttpServletRequest request) {

		LOGGER.info("createPrision() ->  Entrada al servicio para crear una nueva prisión");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Boolean codeext = true;
		long idPrision = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createPrision() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createPrision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					
					if(prisionItem.getCodigoExt() != null) {
						ScsPrisionExample scsPrisionExample2 = new ScsPrisionExample();
						scsPrisionExample2.createCriteria().andCodigoextLike(prisionItem.getCodigoExt())
								.andIdinstitucionEqualTo(idInstitucion);
						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision(codigoExt)");

						List<ScsPrision> prisionList2 = scsPrisionExtendsMapper.selectByExample(scsPrisionExample2);

						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión (codigoExt)");

						if (prisionList2 != null && prisionList2.size() > 0) {
							error.setCode(400);
							error.setDescription("prisiones.error.literal.existePrisionCode");
							insertResponseDTO.setStatus(SigaConstants.KO);
							codeext = false;
						}
					}

					
					if(codeext) {

					ScsPrisionExample scsPrisionExample = new ScsPrisionExample();
					scsPrisionExample.createCriteria().andNombreLike(prisionItem.getNombre())
							.andIdinstitucionEqualTo(idInstitucion);

					LOGGER.info(
							"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión");

					List<ScsPrision> prisionList = scsPrisionExtendsMapper.selectByExample(scsPrisionExample);

					LOGGER.info(
							"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión");

					if (prisionList != null && prisionList.size() > 0) {
						error.setCode(400);
						error.setDescription("prisiones.error.literal.existePrision");
						insertResponseDTO.setStatus(SigaConstants.KO);
					} else {

						ScsPrision prision = new ScsPrision();

						prision.setFechabaja(null);
						prision.setFechamodificacion(new Date());
						prision.setUsumodificacion(usuario.getIdusuario().intValue());
						prision.setIdinstitucion(idInstitucion);
						prision.setNombre(prisionItem.getNombre());
						prision.setDomicilio(prisionItem.getDomicilio());
						prision.setCodigopostal(prisionItem.getCodigoPostal());
						prision.setIdpoblacion(prisionItem.getIdPoblacion());
						prision.setIdprovincia(prisionItem.getIdProvincia());
						prision.setTelefono1(prisionItem.getTelefono1());
						prision.setTelefono2(prisionItem.getTelefono2());
						prision.setFax1(prisionItem.getFax());
						prision.setCodigoext(prisionItem.getCodigoExt());
						prision.setEmail(prisionItem.getEmail());
						prision.setVisiblemovil(prisionItem.getVisibleMovil());

						NewIdDTO idP = scsPrisionExtendsMapper.getIdPrision(usuario.getIdinstitucion());

						if (idP == null) {
							prision.setIdprision((long) 1);
							idPrision = 1;
						} else {
							idPrision = (Integer.parseInt(idP.getNewId()) + 1);
							prision.setIdprision(idPrision);
						}

						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.insert() -> Entrada a scsPrisionExtendsMapper para insertar la nueva prision");

						response = scsPrisionExtendsMapper.insert(prision);

						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.insert() -> Salida de scsPrisionExtendsMapper para insertar la nueva prision");

					}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idPrision));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createPrision() -> Salida del servicio para crear una nueva prisión");

		return insertResponseDTO;
	
	}




}