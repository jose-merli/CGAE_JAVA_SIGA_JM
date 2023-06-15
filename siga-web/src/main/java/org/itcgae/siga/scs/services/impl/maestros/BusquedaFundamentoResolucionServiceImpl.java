package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsTipofundamentos;
import org.itcgae.siga.db.entities.ScsTipofundamentosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipofundamentosExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IBusquedaFundamentoResolucionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaFundamentoResolucionServiceImpl implements IBusquedaFundamentoResolucionService {

	private Logger LOGGER = Logger.getLogger(BusquedaFundamentoResolucionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsTipofundamentosExtendsMapper scsTipofundamentosExtendsMapper;

	@Override
	public FundamentoResolucionDTO searchFundamentosResolucion(FundamentoResolucionItem fundamentoResolucionItem,
			HttpServletRequest request) {
		LOGGER.info("searchFundamentosResolucion() -> Entrada al servicio para obtener los funcionamiento de resolucion");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<FundamentoResolucionItem> fundamentos = null;
		FundamentoResolucionDTO fundamentoResolucionDTO = new FundamentoResolucionDTO();

		if (idInstitucion != null) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchFundamentosResolucion() / scsTipofundamentosExtendsMapper.searchFundamentos() -> Entrada a scsTipofundamentosExtendsMapper para obtener fundamentos de resolucion");

				fundamentos = scsTipofundamentosExtendsMapper.searchFundamentosResolucion(fundamentoResolucionItem, usuario.getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"searchFundamentosResolucion() / scsTipofundamentosExtendsMapper.searchFundamentos() -> Salida a scsTipofundamentosExtendsMapper para obtener fundamentos de resolucion");

				if (fundamentos != null) {
					fundamentoResolucionDTO.setFundamentoResolucionItems(fundamentos);
				}
			}

		}
		LOGGER.info(
				"searchFundamentosResolucion() -> Salida del servicio para obtener los fundamentos de resolucion");
		return fundamentoResolucionDTO;
	}

	@Override
	public UpdateResponseDTO deleteFundamentosResolucion(FundamentoResolucionDTO fundamentoResolucionDTO,
			HttpServletRequest request) {
		LOGGER.info("deleteFundamentosResolucion() ->  Entrada al servicio para eliminar fundamentos de resolucion");

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
					"deleteFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (FundamentoResolucionItem fundamentoItem : fundamentoResolucionDTO.getFundamentoResolucionItems()) {

						ScsTipofundamentosExample scsTipofundamentosExample = new ScsTipofundamentosExample();
						scsTipofundamentosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdfundamentoEqualTo(Short.valueOf(fundamentoItem.getIdFundamento()));

						LOGGER.info(
								"deleteFundamentosResolucion() / scsTipofundamentosExtendsMapper.selectByExample() -> Entrada a scsTipofundamentosExtendsMapper para buscar el fundamento de resolucion");

						List<ScsTipofundamentos> fundamentosList = scsTipofundamentosExtendsMapper.selectByExample(scsTipofundamentosExample);

						LOGGER.info(
								"deleteFundamentosResolucion() / scsTipofundamentosExtendsMapper.selectByExample() -> Salida de scsTipofundamentosExtendsMapper para buscar el fundamento de resolucion");

						if (null != fundamentosList && fundamentosList.size() > 0) {

							ScsTipofundamentos fundamento = fundamentosList.get(0);

							fundamento.setFechabaja(new Date());
							fundamento.setFechamodificacion(new Date());
							fundamento.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByPrimaryKey() -> Entrada a scsJuzgadoExtendsMapper para dar de baja a un fundamento");

							response = scsTipofundamentosExtendsMapper.updateByPrimaryKey(fundamento);

							LOGGER.info(
									"deleteFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByPrimaryKey() -> Salida de scsJuzgadoExtendsMapper para dar de baja a un fundamento");
						}
					}

				} catch (Exception e) {
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

		LOGGER.info("deleteFundamentosResolucion() -> Salida del servicio para eliminar fundamentos de resolucion");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO activateFundamentosResolucion(FundamentoResolucionDTO fundamentoResolucionDTO,
			HttpServletRequest request) {
		LOGGER.info("activateFundamentosResolucion() ->  Entrada al servicio para activar fundamentos de resolucion");

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
					"activateFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (FundamentoResolucionItem fundamentoItem : fundamentoResolucionDTO.getFundamentoResolucionItems()) {

						ScsTipofundamentosExample scsTipofundamentosExample = new ScsTipofundamentosExample();
						scsTipofundamentosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdfundamentoEqualTo(Short.valueOf(fundamentoItem.getIdFundamento()));


						LOGGER.info(
								"activateFundamentosResolucion() / scsTipofundamentosExtendsMapper.selectByExample() -> Entrada a scsTipofundamentosExtendsMapper para buscar el fundamento de resolucion");

						List<ScsTipofundamentos> fundamentosList = scsTipofundamentosExtendsMapper.selectByExample(scsTipofundamentosExample);

						LOGGER.info(
								"activateFundamentosResolucion() / scsTipofundamentosExtendsMapper.selectByExample() -> Salida de scsTipofundamentosExtendsMapper para buscar el fundamento de resolucion");

						if (null != fundamentosList && fundamentosList.size() > 0) {

							ScsTipofundamentos fundamento = fundamentosList.get(0);

							fundamento.setFechabaja(null);
							fundamento.setFechamodificacion(new Date());
							fundamento.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByPrimaryKey() -> Entrada a scsTipofundamentosExtendsMapper para dar de alta a un fundamento");

							response = scsTipofundamentosExtendsMapper.updateByPrimaryKey(fundamento);

							LOGGER.info(
									"activateFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByPrimaryKey() -> Salida de scsTipofundamentosExtendsMapper para dar de alta a un fundamento");
						}
					}

				} catch (Exception e) {
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

		LOGGER.info("activateFundamentosResolucion() -> Salida del servicio para activar fundamentos de resolucion");

		return updateResponseDTO;
	}


	
}