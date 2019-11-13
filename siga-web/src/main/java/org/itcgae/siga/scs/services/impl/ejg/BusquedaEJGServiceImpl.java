package org.itcgae.siga.scs.services.impl.ejg;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.DocumentacionEjgDTO;
import org.itcgae.siga.DTO.scs.DocumentacionEjgItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsTiposentidoauto;
import org.itcgae.siga.db.entities.ScsTiposentidoautoExample;
import org.itcgae.siga.db.mappers.ScsTiposentidoautoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsFundamentoscalificacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPreceptivoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRenunciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoejgcolegioExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipofundamentosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTiporesolucionExtendsMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipofundamentosSqlExtendsProvider;
import org.itcgae.siga.scs.services.ejg.IBusquedaEJG;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BusquedaEJGServiceImpl implements IBusquedaEJG{
	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private ScsTipoejgExtendsMapper scsTipoEjgextendsMapper;
	@Autowired
	ScsTipoejgcolegioExtendsMapper scsTipoejgcolegioExtendsMapper;
	@Autowired
	private ScsFundamentoscalificacionExtendsMapper scsFundamentoscalificacionExtendsMapper;
	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;
	@Autowired
	private ScsTiporesolucionExtendsMapper scsTiporesolucionExtendsMapper;
	@Autowired
	private ScsTiposentidoautoMapper scsTiposentidoautoMapper;
	@Autowired
	private ScsPreceptivoExtendsMapper scsPreceptivoExtendsMapper;
	@Autowired
	private ScsRenunciaExtendsMapper scsRenunciaExtendsMapper;
	@Autowired
	private ScsTipofundamentosExtendsMapper scsTipofundamentosExtendsMapper;
	

	@Override
	public ComboDTO comboTipoEJG(HttpServletRequest request) {
		// TODO Auto-generated method stub
				// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = null;

				if (idInstitucion != null) {
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
					LOGGER.info(
							"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

					LOGGER.info(
							"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

					if (usuarios != null && usuarios.size() > 0) {

						LOGGER.info(
								"comboTipoEJG() / scsTipoEjgextendsMapper.comboTipoejg() -> Entrada a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

						comboItems = scsTipoEjgextendsMapper.comboTipoejg(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

						LOGGER.info(
								"comboTipoEJG() / scsTipoEjgextendsMapper.comboTipoejg() -> Salida a sqScsTipodictamenejgExtendsMapper para obtener los tipos ejg");

						if (comboItems != null) {
							comboDTO.setCombooItems(comboItems);
						}
					}

				}
				LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
				return comboDTO;
	}


	@Override
	public ComboDTO comboTipoColegioEjg(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTipoColegioEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTipoColegioEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoColegioEjg() / scsTipoejgcolegioExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener combo");

				comboItems = scsTipoejgcolegioExtendsMapper.comboTipoColegioEjg(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

				LOGGER.info(
						"comboTipoEJG() / scsTipoejgcolegioExtendsMapper.comboFundamentoCalificacion() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener combo");
		return comboDTO;
	}


	@Override
	public ComboDTO comboFundamentoCalificacion(HttpServletRequest request) {
		// TODO Auto-generated method stub
				// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = null;

				if (idInstitucion != null) {
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
					LOGGER.info(
							"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

					LOGGER.info(
							"comboTipoEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

					if (usuarios != null && usuarios.size() > 0) {

						LOGGER.info(
								"comboTipoEJG() / scsFundamentoscalificacionExtendsMapper.comboDic() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener los combo");

						comboItems = scsFundamentoscalificacionExtendsMapper.comboFundamentoCalificacion(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

						LOGGER.info(
								"comboTipoEJG() / scsFundamentoscalificacionExtendsMapper.selectTipoSolicitud() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener los combo");

						if (comboItems != null) {
							comboDTO.setCombooItems(comboItems);
						}
					}

				}
				LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
				return comboDTO;
	}


	@Override
	public ComboDTO comboResolucion(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboResolucion() / scsTiporesolucionExtendsMapper.getResoluciones() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				comboItems = scsTiporesolucionExtendsMapper.getResoluciones(usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"comboResolucion() / scsTiporesolucionExtendsMapper.getResoluciones() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}


	@Override
	public ComboDTO comboFundamentoImpug(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ScsTiposentidoauto> comboItems = null;
		List<ComboItem> comboItemsOK = new ArrayList<ComboItem>();;

		

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			ScsTiposentidoautoExample scsTiposentidoautoExample = new ScsTiposentidoautoExample();

			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboFundamentoImpug() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboFundamentoImpug() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboFundamentoImpug() / scsTiposentidoautoMapper.getResoluciones() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				scsTiposentidoautoExample.createCriteria().andFechaBajaIsNull();
				scsTiposentidoautoExample.setOrderByClause("descripcion");
				comboItems = scsTiposentidoautoMapper.selectByExample(scsTiposentidoautoExample);

				LOGGER.info(
						"comboFundamentoImpug() / scsTiposentidoautoMapper.getResoluciones() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

				LOGGER.info("comboFundamentoImpug() -> Salida ya con los datos recogidos");
				
				
				if (comboItems != null) {
					for(ScsTiposentidoauto fundamento: comboItems ) {
						ComboItem item = new ComboItem();
						item.setLabel(fundamento.getDescripcion());
						item.setValue(fundamento.getIdtiposentidoauto().toString());
						comboItemsOK.add(item);

					}
					comboDTO.setCombooItems(comboItemsOK);
					LOGGER.info("comboFundamentoImpug() -> Salida ya con los datos recogidos");
				}
			}

		}
		LOGGER.info("comboFundamentoImpug() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}


	@Override
	public ComboDTO comboPreceptivo(HttpServletRequest request) {
		// TODO Auto-generated method stub
				// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = null;

				if (idInstitucion != null) {
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
					LOGGER.info(
							"comboPerceptivo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

					LOGGER.info(
							"comboPerceptivo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

					if (usuarios != null && usuarios.size() > 0) {

						LOGGER.info(
								"comboPerceptivo() / scsTiporesolucionExtendsMapper.getResoluciones() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

						comboItems = scsPreceptivoExtendsMapper.comboPreceptivo(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

						LOGGER.info(
								"comboPerceptivo() / scsTiporesolucionExtendsMapper.getResoluciones() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

						if (comboItems != null) {
							comboDTO.setCombooItems(comboItems);
						}
					}

				}
				LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
				return comboDTO;
	}


	@Override
	public ComboDTO comboRenuncia(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboRenuncia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboRenuncia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboRenuncia() / scsTiporesolucionExtendsMapper.comboRenuncia() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				comboItems = scsRenunciaExtendsMapper.comboRenuncia(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

				LOGGER.info(
						"comboRenuncia() / scsTiporesolucionExtendsMapper.comboRenuncia() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}


	@Override
	public ComboDTO comboCreadoDesde(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboCreadoDesde() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboCreadoDesde() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboCreadoDesde() / scsEjgExtendsMapper.comboCreadoDesde() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener combo");

				comboItems = scsEjgExtendsMapper.comboCreadoDesde(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

				LOGGER.info(
						"comboCreadoDesde() / scsEjgExtendsMapper.comboCreadoDesde() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener combo");
		return comboDTO;
	}


	@Override
	public ComboDTO ComboFundamentoJurid(HttpServletRequest request, String resolucion) {
		// TODO Auto-generated method stub
				// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				ComboDTO comboDTO = new ComboDTO();
				List<ComboItem> comboItems = null;

				if (idInstitucion != null) {
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
					LOGGER.info(
							"ComboFundamentoJurid() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

					LOGGER.info(
							"ComboFundamentoJurid() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

					if (usuarios != null && usuarios.size() > 0) {

						LOGGER.info(
								"ComboFundamentoJurid() / scsTipofundamentosSqlExtendsMapper.ComboFundamentoJurid() -> Entrada a scsFundamentoscalificacionExtendsMapper para obtener combo");

						comboItems = scsTipofundamentosExtendsMapper.comboFundamentoJurid(usuarios.get(0).getIdlenguaje(), idInstitucion.toString(), resolucion);

						LOGGER.info(
								"ComboFundamentoJurid() / scsTipofundamentosSqlExtendsMapper.ComboFundamentoJurid() -> Salida a scsFundamentoscalificacionExtendsMapper para obtener combo");

						if (comboItems != null) {
							comboDTO.setCombooItems(comboItems);
						}
					}

				}
				LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener combo");
				return comboDTO;
	}

}
