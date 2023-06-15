package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ComisariaDTO;
import org.itcgae.siga.DTOs.scs.ComisariaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsComisaria;
import org.itcgae.siga.db.entities.ScsComisariaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IComisariasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComisariasServiceImpl implements IComisariasService {

	private Logger LOGGER = Logger.getLogger(ComisariasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsComisariaExtendsMapper scsComisariaExtendsMapper;


	@Override
	public ComisariaDTO searchComisarias(ComisariaItem comisariaItem, HttpServletRequest request) {
		LOGGER.info("searchPrisiones() -> Entrada al servicio para obtener prisiones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComisariaDTO comisariaDTO = new ComisariaDTO();
		List<ComisariaItem> comisariaItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchComisarias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchComisarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchComisarias() / scsPrisionExtendsMapper.searchPrisiones() -> Entrada a scsPrisionExtendsMapper para obtener las prisiones");

				comisariaItemList = scsComisariaExtendsMapper.searchComisarias(comisariaItem, idInstitucion);

				LOGGER.info(
						"searchComisarias() / scsPrisionExtendsMapper.searchPrisiones() -> Salida a scsPrisionExtendsMapper para obtener las prisiones");

				if (comisariaItemList != null) {
					comisariaDTO.setComisariaItems(comisariaItemList);
				}
			}

		}
		LOGGER.info("searchComisarias() -> Salida del servicio para obtener prisiones");
		return comisariaDTO;
	}


	@Override
	@Transactional
	public UpdateResponseDTO deleteComisarias(ComisariaDTO comisariaDTO, HttpServletRequest request) {

		LOGGER.info("deleteComisarias() ->  Entrada al servicio para eliminar prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteComisarias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteComisarias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ComisariaItem comisariaItem : comisariaDTO.getComisariaItems()) {

						ScsComisariaExample scsComisariaExample = new ScsComisariaExample();
						scsComisariaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcomisariaEqualTo(Long.valueOf(comisariaItem.getIdComisaria()));

						LOGGER.info(
								"deleteComisarias() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsComisaria> comisariasList = scsComisariaExtendsMapper.selectByExample(scsComisariaExample);

						LOGGER.info(
								"deleteComisarias() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != comisariasList && comisariasList.size() > 0) {

							ScsComisaria comisaria = comisariasList.get(0);

							comisaria.setFechabaja(new Date());
							comisaria.setFechamodificacion(new Date());
							comisaria.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteComisarias() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

							response = scsComisariaExtendsMapper.updateByPrimaryKey(comisaria);

							LOGGER.info(
									"deleteComisarias() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
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
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteComisarias() -> Salida del servicio para eliminar prisiones");

		return updateResponseDTO;
	
	}


	@Override
	@Transactional
	public UpdateResponseDTO activateComisarias(ComisariaDTO comisariaDTO, HttpServletRequest request) {


		LOGGER.info("activatePrisiones() ->  Entrada al servicio para dar de alta a prisiones");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activatePrisiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activatePrisiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ComisariaItem comisariaItem : comisariaDTO.getComisariaItems()) {

						ScsComisariaExample scsComisariaExample = new ScsComisariaExample();
						scsComisariaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcomisariaEqualTo(Long.valueOf(comisariaItem.getIdComisaria()));

						LOGGER.info(
								"activatePrisiones() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsComisaria> comisariasList = scsComisariaExtendsMapper.selectByExample(scsComisariaExample);

						LOGGER.info(
								"activatePrisiones() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != comisariasList && comisariasList.size() > 0) {

							ScsComisaria comisaria= comisariasList.get(0);

							comisaria.setFechabaja(null);
							comisaria.setFechamodificacion(new Date());
							comisaria.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activatePrisiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de alta a una prision");

							response = scsComisariaExtendsMapper.updateByPrimaryKey(comisaria);

							LOGGER.info(
									"activatePrisiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de alta a una prision");

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
		} else {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateCourt() -> Salida del servicio para dar de alta a las prisiones");

		return updateResponseDTO;

	
	}


	@Override
	public UpdateResponseDTO updateComisaria(ComisariaItem comisariaItem, HttpServletRequest request) {

		LOGGER.info("updateComisaria() ->  Entrada al servicio para editar prision");

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
					"updateComisaria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateComisaria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					if(comisariaItem.getCodigoExt() != null) {

					ScsComisariaExample scsComisariaExample2 = new ScsComisariaExample();
					scsComisariaExample2.createCriteria().andCodigoextEqualTo(comisariaItem.getCodigoExt())
							.andIdinstitucionEqualTo(idInstitucion).andIdcomisariaNotEqualTo(Long.decode(comisariaItem.getIdComisaria())); ;

					LOGGER.info(
							"createComisaria() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión(CodigoExt)");

					List<ScsComisaria> comisariaList2 = scsComisariaExtendsMapper.selectByExample(scsComisariaExample2);

					LOGGER.info(
							"createComisaria() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión(Códigoext)");

					if (comisariaList2 != null && comisariaList2.size() > 0) {
						error.setCode(400);
						error.setDescription("prisiones.error.literal.existeComisariaCode");
						codeext = false;
					} 
					}
					if(codeext) {

					ScsComisariaExample example = new ScsComisariaExample();
					example.createCriteria().andNombreLike(comisariaItem.getNombre())
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdcomisariaNotEqualTo(Long.decode(comisariaItem.getIdComisaria()));

					LOGGER.info(
							"updateComisaria() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar juzgado");

					List<ScsComisaria> comisariaList = scsComisariaExtendsMapper.selectByExample(example);

					LOGGER.info(
							"updateComisaria() / scsJuzgadoExtendsMapper.selectByExample() -> Salida a scsJuzgadoExtendsMapper para buscar juzgado");

					if (comisariaList != null && comisariaList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.gestionJuzgado.existeJuzgadoMismoNombre");

					} else {

						ScsComisariaExample scsComisariaExample  = new ScsComisariaExample();
						scsComisariaExample.createCriteria().andIdcomisariaEqualTo(Long.valueOf(comisariaItem.getIdComisaria()))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updateComisaria() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsComisaria> scsComisariaList = scsComisariaExtendsMapper.selectByExample(scsComisariaExample);

						LOGGER.info(
								"updateComisaria() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prision");

						ScsComisaria comisaria = scsComisariaList.get(0);

						comisaria.setNombre(comisariaItem.getNombre());
						comisaria.setFechamodificacion(new Date());
						comisaria.setUsumodificacion(usuario.getIdusuario().intValue());
						comisaria.setDomicilio(comisariaItem.getDomicilio());
						comisaria.setCodigopostal(comisariaItem.getCodigoPostal());
						comisaria.setIdpoblacion(comisariaItem.getIdPoblacion());
						comisaria.setIdprovincia(comisariaItem.getIdProvincia());
						comisaria.setTelefono1(comisariaItem.getTelefono1());
						comisaria.setTelefono2(comisariaItem.getTelefono2());
						comisaria.setFax1(comisariaItem.getFax1());
						comisaria.setCodigoext(comisariaItem.getCodigoExt());
						comisaria.setVisiblemovil(comisariaItem.getVisibleMovil());
						comisaria.setEmail(comisariaItem.getEmail());


						response = scsComisariaExtendsMapper.updateByPrimaryKey(comisaria);
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

		LOGGER.info("updateComisaria() -> Salida del servicio para editar prision");

		return updateResponseDTO;
	
	}

	@Override
	public InsertResponseDTO createComisaria(ComisariaItem comisariaItem, HttpServletRequest request) {

		LOGGER.info("createPrision() ->  Entrada al servicio para crear una nueva prisión");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		long idComisaria = 0;
		Boolean codeext = true;


		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createComisaria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createComisaria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					if(comisariaItem.getCodigoExt() != null) {

					ScsComisariaExample scsComisariaExample2 = new ScsComisariaExample();
					scsComisariaExample2.createCriteria().andCodigoextEqualTo(comisariaItem.getCodigoExt())
							.andIdinstitucionEqualTo(idInstitucion);

					LOGGER.info(
							"createComisaria() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión(CodigoExt)");

					List<ScsComisaria> comisariaList2 = scsComisariaExtendsMapper.selectByExample(scsComisariaExample2);

					LOGGER.info(
							"createComisaria() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión(Códigoext)");

					if (comisariaList2 != null && comisariaList2.size() > 0) {
						error.setCode(400);
						error.setDescription("prisiones.error.literal.existeComisariaCode");
						insertResponseDTO.setStatus(SigaConstants.KO);
						codeext = false;
					}
					}
						if(codeext) {

					ScsComisariaExample scsComisariaExample = new ScsComisariaExample();
					scsComisariaExample.createCriteria().andNombreEqualTo(comisariaItem.getNombre())
							.andIdinstitucionEqualTo(idInstitucion);

					LOGGER.info(
							"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión");

					List<ScsComisaria> comisariaList = scsComisariaExtendsMapper.selectByExample(scsComisariaExample);

					LOGGER.info(
							"createPrision() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión");

					if (comisariaList != null && comisariaList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.comisarias.existeComisariaMismoNombre");
						insertResponseDTO.setStatus(SigaConstants.KO);
					} else {

						ScsComisaria comisaria= new ScsComisaria();

						comisaria.setFechabaja(null);
						comisaria.setFechamodificacion(new Date());
						comisaria.setUsumodificacion(usuario.getIdusuario().intValue());
						comisaria.setIdinstitucion(idInstitucion);
						comisaria.setNombre(comisariaItem.getNombre());
						comisaria.setDomicilio(comisariaItem.getDomicilio());
						comisaria.setCodigopostal(comisariaItem.getCodigoPostal());
						comisaria.setIdpoblacion(comisariaItem.getIdPoblacion());
						comisaria.setIdprovincia(comisariaItem.getIdProvincia());
						comisaria.setTelefono1(comisariaItem.getTelefono1());
						comisaria.setTelefono2(comisariaItem.getTelefono2());
						comisaria.setFax1(comisariaItem.getFax1());
						comisaria.setCodigoext(comisariaItem.getCodigoExt());
						comisaria.setEmail(comisariaItem.getEmail());
						comisaria.setVisiblemovil(comisariaItem.getVisibleMovil());
						NewIdDTO idP = scsComisariaExtendsMapper.getIdComisaria(usuario.getIdinstitucion());

						if (idP == null) {
							comisaria.setIdcomisaria((long) 1);
							idComisaria= 1;
						} else {
							idComisaria = (Integer.parseInt(idP.getNewId()) + 1);
							comisaria.setIdcomisaria(idComisaria);
						}

						LOGGER.info(
								"createPrision() / scsPrisionExtendsMapper.insert() -> Entrada a scsPrisionExtendsMapper para insertar la nueva prision");

						response = scsComisariaExtendsMapper.insert(comisaria);

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
			insertResponseDTO.setId(String.valueOf(idComisaria));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createPrision() -> Salida del servicio para crear una nueva prisión");

		return insertResponseDTO;
	
	}

}
