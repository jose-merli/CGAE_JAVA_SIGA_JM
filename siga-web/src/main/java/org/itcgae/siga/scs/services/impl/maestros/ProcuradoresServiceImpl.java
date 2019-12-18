package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsProcurador;
import org.itcgae.siga.db.entities.ScsProcuradorExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcuradorExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IProcuradoresService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcuradoresServiceImpl implements IProcuradoresService {

	private Logger LOGGER = Logger.getLogger(ProcuradoresServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsProcuradorExtendsMapper scsProcuradorExtendsMapper;


	@Override
	public ProcuradorDTO searchProcuradores(ProcuradorItem procuradorItem, HttpServletRequest request) {
		LOGGER.info("searchPrisiones() -> Entrada al servicio para obtener prisiones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				procuradorItemList = scsProcuradorExtendsMapper.searchProcuradores(procuradorItem, idInstitucion);
				
				LOGGER.info(
						"searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("searchComisarias() -> Salida del servicio para obtener prisiones");
		return procuradorDTO;
	}


	@Override
	@Transactional
	public UpdateResponseDTO deleteProcuradores(ProcuradorDTO procuradorDTO, HttpServletRequest request) {

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
					"deleteProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ProcuradorItem procuradorItem : procuradorDTO.getProcuradorItems()) {

						ScsProcuradorExample scsProcuradorExample = new ScsProcuradorExample();
						scsProcuradorExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdprocuradorEqualTo(Long.valueOf(procuradorItem.getIdProcurador()));

						LOGGER.info(
								"deleteProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsProcurador> procuradoresList = scsProcuradorExtendsMapper.selectByExample(scsProcuradorExample);

						LOGGER.info(
								"deleteProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != procuradoresList && procuradoresList.size() > 0) {

							ScsProcurador procurador = procuradoresList.get(0);

							procurador.setFechabaja(new Date());
							procurador.setFechamodificacion(new Date());
							procurador.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteProcuradores() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

							response = scsProcuradorExtendsMapper.updateByPrimaryKey(procurador);

							LOGGER.info(
									"deleteProcuradores() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
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
	public UpdateResponseDTO activateProcuradores(ProcuradorDTO procuradorDTO, HttpServletRequest request) {


		LOGGER.info("activateProcuradores() ->  Entrada al servicio para dar de alta a prisiones");

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
					"activateProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (ProcuradorItem procuradorItem : procuradorDTO.getProcuradorItems()) {

						ScsProcuradorExample scsProcuradorExample = new ScsProcuradorExample();
						scsProcuradorExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdprocuradorEqualTo(Long.valueOf(procuradorItem.getIdProcurador()));

						LOGGER.info(
								"activateProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsProcurador> procuradoresList = scsProcuradorExtendsMapper.selectByExample(scsProcuradorExample);

						LOGGER.info(
								"activateProcuradores() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

						if (null != procuradoresList && procuradoresList.size() > 0) {

							ScsProcurador procurador= procuradoresList.get(0);

							procurador.setFechabaja(null);
							procurador.setFechamodificacion(new Date());
							procurador.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateProcuradores() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de alta a una prision");

							response = scsProcuradorExtendsMapper.updateByPrimaryKey(procurador);

							LOGGER.info(
									"activateProcuradores() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de alta a una prision");

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
	public UpdateResponseDTO updateProcurador(ProcuradorItem procuradorItem, HttpServletRequest request) {

		LOGGER.info("updateComisaria() ->  Entrada al servicio para editar prision");

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
					"updateComisaria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateComisaria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsProcuradorExample example = new ScsProcuradorExample();

					if(procuradorItem.getApellido2()!= null && procuradorItem.getApellido2() != "")
						example.createCriteria().andNombreLike(procuradorItem.getNombre().trim())
						.andApellidos1EqualTo(procuradorItem.getApellido1().trim())
						.andApellidos2EqualTo(procuradorItem.getApellido2().trim())
						.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull()
						.andIdprocuradorNotEqualTo(Long.decode(procuradorItem.getIdProcurador()));
					else
						example.createCriteria().andNombreLike(procuradorItem.getNombre().trim())
						.andApellidos1EqualTo(procuradorItem.getApellido1().trim())
						.andApellidos2IsNull()
						.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull()
						.andIdprocuradorNotEqualTo(Long.decode(procuradorItem.getIdProcurador()));

			

					LOGGER.info(
							"updateComisaria() / scsJuzgadoExtendsMapper.selectByExample() -> Entrada a scsJuzgadoExtendsMapper para buscar juzgado");

					List<ScsProcurador> procuradorList = scsProcuradorExtendsMapper.selectByExample(example);

					LOGGER.info(
							"updateComisaria() / scsJuzgadoExtendsMapper.selectByExample() -> Salida a scsJuzgadoExtendsMapper para buscar juzgado");

					if (procuradorList != null && procuradorList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.procuradores.existeProcuradorMismoNombre");

					} else {

						ScsProcuradorExample scsProcuradorExample  = new ScsProcuradorExample();
						scsProcuradorExample.createCriteria().andIdprocuradorEqualTo(Long.valueOf(procuradorItem.getIdProcurador()))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updateComisaria() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

						List<ScsProcurador> scsProcuradorList = scsProcuradorExtendsMapper.selectByExample(scsProcuradorExample);

						LOGGER.info(
								"updateComisaria() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prision");

						ScsProcurador procurador = scsProcuradorList.get(0);

						procurador.setFechabaja(null);
						procurador.setFechamodificacion(new Date());
						procurador.setUsumodificacion(usuario.getIdusuario().intValue());
						procurador.setIdinstitucion(idInstitucion);
						procurador.setNombre(procuradorItem.getNombre());
						procurador.setDomicilio(procuradorItem.getDomicilio());
						procurador.setCodigopostal(procuradorItem.getCodigoPostal());
						procurador.setIdpoblacion(procuradorItem.getIdPoblacion());
						procurador.setIdcolprocurador(procuradorItem.getIdColProcurador());
						procurador.setApellidos1(procuradorItem.getApellido1());
						procurador.setApellidos2(procuradorItem.getApellido2());
						procurador.setFax1(procuradorItem.getFax1());
						procurador.setNcolegiado(procuradorItem.getnColegiado());
						procurador.setIdprovincia(procuradorItem.getIdProvincia());
						procurador.setTelefono1(procuradorItem.getTelefono1());
						procurador.setTelefono2(procuradorItem.getTelefono2());
						procurador.setCodigo(procuradorItem.getCodigoExt());
						procurador.setEmail(procuradorItem.getEmail());
						
						response = scsProcuradorExtendsMapper.updateByPrimaryKey(procurador);
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
	public InsertResponseDTO createProcurador(ProcuradorItem procuradorItem, HttpServletRequest request) {

		LOGGER.info("createProcurador() ->  Entrada al servicio para crear una nueva prisión");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		long idProcurador = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsProcuradorExample scsProcuradorExample = new ScsProcuradorExample();
					if(procuradorItem.getApellido2()!= null)
						scsProcuradorExample.createCriteria().andNombreEqualTo(procuradorItem.getNombre().trim())
						.andApellidos1EqualTo(procuradorItem.getApellido1().trim())
						.andApellidos2EqualTo(procuradorItem.getApellido2().trim())
						.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
					else
						scsProcuradorExample.createCriteria().andNombreEqualTo(procuradorItem.getNombre().trim())
						.andApellidos1EqualTo(procuradorItem.getApellido1().trim())
						.andApellidos2IsNull()
						.andIdinstitucionEqualTo(idInstitucion).andFechabajaIsNull();
			

					LOGGER.info(
							"createProcurador() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión");

					List<ScsProcurador> procuradorList = scsProcuradorExtendsMapper.selectByExample(scsProcuradorExample);

					LOGGER.info(
							"createProcurador() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión");

					if (procuradorList != null && procuradorList.size() > 0) {
						error.setCode(400);
						error.setDescription("messages.jgr.maestros.procuradores.existeProcuradorMismoNombre");
						insertResponseDTO.setStatus(SigaConstants.KO);
					} else {

						ScsProcurador procurador= new ScsProcurador();

						procurador.setFechabaja(null);
						procurador.setFechamodificacion(new Date());
						procurador.setUsumodificacion(usuario.getIdusuario().intValue());
						procurador.setIdinstitucion(idInstitucion);
						procurador.setNombre(procuradorItem.getNombre());
						procurador.setDomicilio(procuradorItem.getDomicilio());
						procurador.setCodigopostal(procuradorItem.getCodigoPostal());
						procurador.setIdpoblacion(procuradorItem.getIdPoblacion());
						procurador.setIdcolprocurador(procuradorItem.getIdColProcurador());
						procurador.setApellidos1(procuradorItem.getApellido1());
						procurador.setApellidos2(procuradorItem.getApellido2());
						procurador.setFax1(procuradorItem.getFax1());
						procurador.setNcolegiado(procuradorItem.getnColegiado());
						procurador.setIdprovincia(procuradorItem.getIdProvincia());
						procurador.setTelefono1(procuradorItem.getTelefono1());
						procurador.setTelefono2(procuradorItem.getTelefono2());
						procurador.setCodigo(procuradorItem.getCodigoExt());
						procurador.setEmail(procuradorItem.getEmail());
						NewIdDTO idP = scsProcuradorExtendsMapper.getIdProcurador(usuario.getIdinstitucion());

						if (idP == null) {
							procurador.setIdprocurador((long) 1);
							idProcurador= 1;
						} else {
							idProcurador= (Integer.parseInt(idP.getNewId()) + 1);
							procurador.setIdprocurador(idProcurador);
						}

						LOGGER.info(
								"createProcurador() / scsPrisionExtendsMapper.insert() -> Entrada a scsPrisionExtendsMapper para insertar la nueva prision");

						response = scsProcuradorExtendsMapper.insert(procurador);

						LOGGER.info(
								"createProcurador() / scsPrisionExtendsMapper.insert() -> Salida de scsPrisionExtendsMapper para insertar la nueva prision");

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
			insertResponseDTO.setId(String.valueOf(idProcurador));
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createProcurador() -> Salida del servicio para crear una nueva prisión");

		return insertResponseDTO;
	
	}

}
