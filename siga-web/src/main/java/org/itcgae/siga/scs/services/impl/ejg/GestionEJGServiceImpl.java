package org.itcgae.siga.scs.services.impl.ejg;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.DelitosEjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ListaContrarioEJGJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsContrariosejgKey;
import org.itcgae.siga.db.entities.ScsDelitosejg;
import org.itcgae.siga.db.entities.ScsDelitosejgExample;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazadaExample;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsEjgdesignaExample;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgKey;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.mappers.ScsDelitosejgMapper;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgPrestacionRechazadaMapper;
import org.itcgae.siga.db.mappers.ScsEjgdesignaMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpTipoexpedienteExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActacomisionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsContrariosejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDelitoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDocumentacionEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsExpedienteEconomicoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrigencajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrestacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSituacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoencalidadExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IEEJGServices;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionEJGServiceImpl implements IGestionEJG {
	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsPrestacionExtendsMapper scsPrestacionesExtendsMapper;
	
	@Autowired
	private ScsSituacionExtendsMapper scsSituacionesExtendsMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private ScsTipoencalidadExtendsMapper scsTipoencalidadExtendsMapper;

	@Autowired
	private ScsExpedienteEconomicoExtendsMapper scsExpedienteEconomicoExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Autowired
	private ScsDocumentacionEjgExtendsMapper scsDocumentacionejgExtendsMapper;

	@Autowired
	private ScsOrigencajgExtendsMapper scsOrigencajgExtendsMapper;

	@Autowired
	private ScsActacomisionExtendsMapper scsActacomisionExtendsMapper;

	@Autowired
	private ExpTipoexpedienteExtendsMapper expExpedienteExtendsMapper;

	@Autowired
	private ScsDelitoExtendsMapper scsDelitoExtendsMapper;
	
	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;
	
	@Autowired
	private ScsDelitosejgMapper scsDelitosejgMapper;

	@Autowired
	private ScsEejgPeticionesMapper scsEejgPeticionesMapper;

	@Autowired
	private ExpExpedienteMapper expExpedienteMapper;

	@Autowired
	private ScsEjgPrestacionRechazadaMapper scsEjgPrestacionRechazadaMapper;

	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;

	@Autowired
	private ScsEjgdesignaMapper scsEjgdesignaMapper;

	@Autowired
	private IEEJGServices eejgService;
	
	@Autowired
	private ScsComisariaExtendsMapper scsComisariaExtendsMapper;
	
	@Autowired
	private ScsContrariosejgExtendsMapper scsContrariosejgExtendsMapper;
	
	@Autowired
	private ScsContrariosejgMapper scsContrariosejgMapper;
	
	@Override
	public EjgDTO datosEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("datosEJG() -> Entrada al servicio para obtener el colegiado");
		EjgDTO ejgDTO = new EjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"datosEJG() / scsEjgExtendsMapper.datosEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgExtendsMapper.datosEJG(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"datosEJG() / scsEjgExtendsMapper.datosEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			} else {
				LOGGER.warn(
						"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("datosEJG() -> Salida del servicio para obtener los datos del ejg seleccionado");
		return ejgDTO;
	}

	@Override
	public ComboDTO comboPrestaciones(HttpServletRequest request) {
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
					"comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPrestaciones() / scsPrestacionesExtendsMapper.comboPrestaciones() -> Entrada a scsPrestacionesExtendsMapper para obtener los combo");

				comboItems = scsPrestacionesExtendsMapper.comboPrestaciones(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());

				LOGGER.info(
						"comboPrestaciones() / scsPrestacionesExtendsMapper.comboPrestaciones() -> Salida a scsPrestacionesExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboPrestaciones() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboTipoencalidad(HttpServletRequest request) {
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
					"comboTipoencalidad() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTipoencalidad() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoencalidad() / scsTiporesolucionExtendsMapper.comboTipoEnCalidadDe() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				comboItems = scsTipoencalidadExtendsMapper.comboTipoencalidad(usuarios.get(0).getIdlenguaje().toString(),idInstitucion);

				LOGGER.info(
						"comboTipoencalidad() / scsTiporesolucionExtendsMapper.comboTipoEnCalidadDe() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoencalidad() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboSituaciones(HttpServletRequest request) {
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
					"comboSituaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboSituaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboSituaciones() / scsSituacionesExtendsMapper.comboSituaciones() -> Entrada a scsSituacionesExtendsMapper para obtener el combo de situaciones");

				comboItems = scsSituacionesExtendsMapper.comboSituaciones(usuarios.get(0).getIdlenguaje().toString());

				LOGGER.info(
						"comboSituaciones() / scsSituacionesExtendsMapper.comboSituaciones() -> Salida a scsSituacionesExtendsMapper para obtener el combo de situaciones");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboSituaciones() -> Salida del servicio para obtener las situaciones");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboCDetenciones(HttpServletRequest request) {
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
					"comboCDetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboCDetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboCDetenciones() / scsComisariaExtendsMapper.comboCDetenciones() -> Entrada a scsComisariaExtendsMapper para obtener el combo de centros de detencion");

				comboItems = scsComisariaExtendsMapper.comboCDetenciones(idInstitucion);

				LOGGER.info(
						"comboCDetenciones() / scsComisariaExtendsMapper.comboCDetenciones() -> Salida a scsComisariaExtendsMapper para obtener el combo de centros de detencion");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboCDetenciones() -> Salida del servicio para obtener los centros de detencion");
		return comboDTO;
	}
	
	@Override
	public ComboDTO comboDelitos(HttpServletRequest request) {
		LOGGER.info("comboDelitos() -> Entrada al servicio para obtener combo delitos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDelitoExtendsMapper.comboDelitos(usuarios.get(0).getIdlenguaje(), idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("comboDelitos() -> Salida del servicio para obtener combo delitos");

		return comboDTO;
	}

	@Override
	public UpdateResponseDTO guardarServiciosTramitacion(EjgItem datos, HttpServletRequest request) {

		UpdateResponseDTO responsedto = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {

					// Seleccionamos el EJG que vamos a actualizar
					ScsEjgKey key = new ScsEjgKey();

					key.setIdinstitucion(idInstitucion);
					key.setAnio(Short.parseShort(datos.getAnnio()));
					key.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					key.setNumero(Long.parseLong(datos.getNumero()));

					ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(key);

					// Una vez tenemos el EJG, introducimos la informacion seleccionada en la
					// tarjeta

					ejg.setGuardiaturnoIdguardia(Integer.parseInt(datos.getIdGuardia()));
					ejg.setGuardiaturnoIdturno(Integer.parseInt(datos.getIdTurno()));
					ejg.setIdpersona(Long.parseLong(datos.getIdPersona()));

					LOGGER.info(
							"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Entrada a scsEjgMapper para actualizar el ejg");

					response = scsEjgMapper.updateByPrimaryKeySelective(ejg);

					LOGGER.info(
							"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Salida a scsEjgMapper para actualizar el ejg");

				} catch (Exception e) {

				}
			}

		}
		LOGGER.info(
				"guardarServiciosTramitacion() -> Salida del servicio para actualizar turno, guardia y letrado asociados a un EJG.");
		return responsedto;
	}

	@Override
	public List<ScsEjgPrestacionRechazada> searchPrestacionesRechazadas(EjgItem ejgItem, HttpServletRequest request) {

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ScsEjgPrestacionRechazada> idsPrestRech = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				idsPrestRech = scsPrestacionesExtendsMapper.prestacionesRechazadas(ejgItem, idInstitucion);

				LOGGER.info(
						"searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

			}

		}
		LOGGER.info(
				"searchPrestacionesRechazadas() -> Salida del servicio para obtener las prestaciones rechazadas del ejg");
		return idsPrestRech;
	}

	@Override
	public UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("unidadFamiliarEJG() -> Entrada al servicio para obtener unidad Familiar");
		UnidadFamiliarEJGDTO unidadFamiliarEJGDTO = new UnidadFamiliarEJGDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Entrada a scsEjgExtendsMapper para obtener Unidad Familiar");
				unidadFamiliarEJGDTO.setUnidadFamiliarEJGItems(scsPersonajgExtendsMapper.unidadFamiliarEJG(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Salida de scsEjgExtendsMapper para obtener Unidad Familiar");
			} else {
				LOGGER.warn(
						"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("unidadFamiliarEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return unidadFamiliarEJGDTO;
	}

	@Override
	public InsertResponseDTO insertFamiliarEJG(List<String> item, HttpServletRequest request) {
		LOGGER.info("insertFamiliarEJG() -> Entrada al servicio para obtener unidad Familiar");
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.insertFamiliarEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.insertFamiliarEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.nuevoEstado() -> Entrada para insertar en la unidad familiar del ejg");

				try {
					// [ejg.idInstitucion, justiciable.idpersona, ejg.annio, ejg.tipoEJG,
					// ejg.numero]
					ScsUnidadfamiliarejg familiar = new ScsUnidadfamiliarejg();

					familiar.setIdinstitucion(idInstitucion);
					familiar.setIdpersona(Long.parseLong(item.get(1)));
					familiar.setAnio(Short.parseShort(item.get(2)));
					familiar.setIdtipoejg(Short.parseShort(item.get(3)));
					familiar.setNumero(Long.parseLong(item.get(4)));

					familiar.setUsumodificacion(usuarios.get(0).getIdusuario());
					familiar.setFechamodificacion(new Date());

					familiar.setSolicitante((short) 0);

					response = scsUnidadfamiliarejgMapper.insert(familiar);
					
					if (response != 1) {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.borrarEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
						throw new Exception("ERROR: no se ha podido ");
					}else {
						responsedto.setStatus(SigaConstants.OK);
					}

					LOGGER.debug(
							"GestionEJGServiceImpl.insertFamiliarEJG() -> Salida del servicio para insertar en la unidad familiar para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.insertFamiliarEJG() -> Se ha producido un error al insertar en la unidad familiar de los ejgs. ",
							e);
				}
			}
		}
		return responsedto;
	}

	@Override
	public ExpedienteEconomicoDTO getExpedientesEconomicos(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getExpedientesEconomicos() -> Entrada al servicio para obtener expedientes económicos");
		ExpedienteEconomicoDTO expedienteEconomicoDTO = new ExpedienteEconomicoDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				Error error = new Error();

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"getExpedientesEconomicos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener Expedienets Económicos");
				expedienteEconomicoDTO.setExpEconItems(scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(
						ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener Expedienets Económicos");
				if (expedienteEconomicoDTO.getExpEconItems() != null
						&& tamMaximo != null
						&& expedienteEconomicoDTO.getExpEconItems().size() > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					expedienteEconomicoDTO.setError(error);
					// justiciablesItems.remove(justiciablesItems.size()-1);
				}

			} else {
				LOGGER.warn(
						"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getExpedientesEconomicos() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return expedienteEconomicoDTO;
	}

	@Override
	public EstadoEjgDTO getEstados(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getEstados() -> Entrada al servicio para obtener el colegiado");
		EstadoEjgDTO estadoEjgDTO = new EstadoEjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getEstados() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEstadoejgExtendsMapper para obtener los estados del EJG");
				estadoEjgDTO.setEstadoEjgItems(scsEstadoejgExtendsMapper.getEstados(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getEstados() / scsEjgExtendsMapper.getEstados() -> Salida de scsEstadoejgExtendsMapper para obtener los estados del EJG");
			} else {
				LOGGER.warn(
						"getEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEstados() -> idInstitucion del token nula");
		}
		return estadoEjgDTO;
	}

	@Override
	public EjgDocumentacionDTO getDocumentos(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getExpedientesEconomicos() -> Entrada al servicio para obtener expedientes económicos");
		EjgDocumentacionDTO ejgDocumentacionDTO = new EjgDocumentacionDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"getDocumentos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"getDocumentos() / setEjgDocItemsItems.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener la documentación de EJG");
				ejgDocumentacionDTO.setEjgDocItems(scsDocumentacionejgExtendsMapper.getDocumentacion(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getDocumentos() / setEjgDocItemsItems.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener la documentación de EJG");
			} else {
				LOGGER.warn(
						"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDocumentos() -> idInstitucion del token nula");
		}

		LOGGER.info("getDocumentos() -> Salida del servicio para obtener la documentación de EJG");
		return ejgDocumentacionDTO;
	}

	@Override
	public EjgItem getDictamen(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getDictamen() -> Entrada al servicio para obtener el colegiado");
		EjgItem dictamen = new EjgItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getDictamen() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEjgExtendsMapper para obtener información del Informe de Calificación");
				dictamen = scsEjgExtendsMapper.getDictamen(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());
				LOGGER.info(
						"getDictamen() / scsEjgExtendsMapper.getEstados() -> Salida de scsEjgExtendsMapper para obtener información del Informe de Calificación");
			} else {
				LOGGER.warn(
						"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDictamen() -> idInstitucion del token nula");
		}
		return dictamen;
	}

	@Override
	public ComboDTO comboOrigen(HttpServletRequest request) {
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
					"comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Entrada a scsOrigencajgExtendsMapper para rellenar el combo");
				comboItems = scsOrigencajgExtendsMapper.comboOrigen(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());
				LOGGER.info(
						"comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Salida a scsOrigencajgExtendsMapper para rellenar el combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboActaAnnio(HttpServletRequest request) {
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
					"comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Entrada a scsActacomisionExtendsMapper para obtener los combo");
				comboItems = scsActacomisionExtendsMapper.getActaAnnio(idInstitucion.toString());
				LOGGER.info(
						"comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Salida a scsActacomisionExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboActaAnnio() -> Salida del servicio");
		return comboDTO;
	}

	@Override
	public ResolucionEJGItem getResolucion(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getResolucion() -> Entrada al servicio para obtener el colegiado");
		ResolucionEJGItem resolucion = new ResolucionEJGItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getResolucion() / scsEjgExtendsMapper.getResolucion() -> Entrada a scsEjgExtendsMapper para obtener información de la Resolución");
				resolucion = scsEjgExtendsMapper.getResolucion(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());
				LOGGER.info(
						"getResolucion() / scsEjgExtendsMapper.getResolucion() -> Salida de scsEjgExtendsMapper para obtener información de la Resolución");
			} else {
				LOGGER.warn(
						"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getResolucion() -> idInstitucion del token nula");
		}
		return resolucion;
	}

	@Override
	public ComboDTO comboTipoExpediente(HttpServletRequest request) {
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
					"comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");
				comboItems = expExpedienteExtendsMapper.comboTipoExpediente(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());
				LOGGER.info(
						"comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO cambioEstadoMasivo(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para cambiar los estados y la fecha de estado para los ejgs");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");

						// obtenemos el maximo de idestadoporejg
						ScsEstadoejgExample example = new ScsEstadoejgExample();
						example.setOrderByClause("IDESTADOPOREJG DESC");
						example.createCriteria().andAnioEqualTo(Short.parseShort(datos.get(i).getAnnio()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoejgEqualTo(Short.parseShort(datos.get(i).getTipoEJG()))
								.andNumeroEqualTo(Long.parseLong(datos.get(i).getNumero()));

						List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

						// damos el varlo al idestadoporejg + 1
						if (listEjg.size() > 0) {
							record.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
						} else {
							record.setIdestadoporejg(Long.parseLong("0"));
						}

						// scsEstadoejgMapper.selectByExample(example)

						response = scsEstadoejgMapper.insert(record);
					}

					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.cambiarEstadoEJGs() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.cambiarEstadoEJGs() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public ResponseEntity<InputStreamResource> descargarExpedientesJG(List<EjgItem> datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ResponseEntity<InputStreamResource> response = null;
		List<DatosDocumentoItem> ficheros = new ArrayList<DatosDocumentoItem>();
		HttpHeaders headers = new HttpHeaders();
		File fichero = null;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarExpedientesJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarExpedientesJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarExpedientesJG() -> Generando fichero y creando la descarga...");

				try {
					// recorremos la lista para generar el documento de cada uno de los ejgs
					for(EjgItem ejg : datos) {
						// obtenemos la peticion y el idXML
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos de la petición...");
	
						ScsEejgPeticionesExample scsEejgPeticionesExample = new ScsEejgPeticionesExample();
	
						scsEejgPeticionesExample.createCriteria()
								.andIdinstitucionEqualTo(Short.parseShort(ejg.getidInstitucion()))
								.andIdpersonaEqualTo(Long.parseLong(ejg.getIdPersonajg()))
								.andAnioEqualTo(Short.parseShort(ejg.getAnnio()))
								.andIdtipoejgEqualTo(Short.parseShort(ejg.getTipoEJG()))
								.andNumeroEqualTo(Long.parseLong(ejg.getNumEjg()));
	
						List<ScsEejgPeticiones> peticiones = scsEejgPeticionesMapper.selectByExample(scsEejgPeticionesExample);
	
						if (peticiones != null && peticiones.size() > 0) {
	
							// obtenemos los datos del fichero
							LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos para el informe...");
							Map<Integer, Map<String, String>> mapInformeEejg = eejgService.getDatosInformeEejg(ejg,peticiones.get(0));
		
							LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo el informe...");
							DatosDocumentoItem documento = eejgService.getInformeEejg(mapInformeEejg, ejg.getidInstitucion());
							
							ficheros.add(documento);
						}
					}
	
					fichero = WSCommons.zipBytes(ficheros, new File("downloads.zip"));
					
					String tipoMime = "application/zip";
					headers.setContentType(MediaType.parseMediaType(tipoMime));
					headers.set("Content-Disposition", "attachment; filename=\"" + fichero.getName() + "\"");
					headers.setContentLength(fichero.length());

                    InputStream fileStream = new FileInputStream(fichero);	
					response = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);

						
					LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Acción realizada correctamente");
				} catch (Exception e) {
					if ("noExiste".equals(e.getMessage())) {
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() ->", e);
					} else {
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Se ha producido un error: ", e);
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarExpedientesJG() -> Salida del servicio.");
		return response;
	}

	@Override
	@Transactional
	public EjgDTO insertaDatosGenerales(EjgItem datos, HttpServletRequest request) {
		EjgDTO ejgdto = new EjgDTO();
		int response = 0;
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.insertaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para insertar los datos generales del ejg");

				try {
					record = setDatosGeneralesEJG(datos);

					// AGREGAMOS DATOS QUE FALTAN EN EL RECORD

					// longitud maxima para num ejg
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("LONGITUD_CODEJG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					List<GenParametros> listParam = genParametrosExtendsMapper.selectByExample(genParametrosExample);

					String longitudEJG = listParam.get(0).getValor();

					// numejg

					String numEJG = scsEjgExtendsMapper.getNumeroEJG(record.getIdtipoejg(), record.getAnio(),
							record.getIdinstitucion());

					int numCeros = Integer.parseInt(longitudEJG) - numEJG.length();

					String ceros = "";
					for (int i = 0; i < numCeros; i++) {
						ceros += "0";
					}

					ceros += numEJG;

					record.setNumejg(ceros);

					// Repetimos el proceso con numero

					String numero = scsEjgExtendsMapper.getNumero(record.getIdtipoejg(), record.getAnio(),
							record.getIdinstitucion());

					numCeros = Integer.parseInt(longitudEJG) - numero.length();

					ceros = "";
					for (int i = 0; i < numCeros; i++) {
						ceros += "0";
					}

					ceros += numero;

					record.setNumero(Long.parseLong(ceros));

					// Determinamos el origen de apertura ya que, aunque no sea una clave primaria,
					// no se permita que tenga valor null.
					record.setOrigenapertura("M");

					// Sucede lo mismo
					record.setTipoletrado("M");
					record.setFechacreacion(new Date());
					record.setFechamodificacion(new Date());
					record.setUsucreacion(usuarios.get(0).getIdusuario());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());

					// Campos opcionales
					record.setFechapresentacion(datos.getFechapresentacion());
					record.setFechalimitepresentacion(datos.getFechalimitepresentacion());
					if (datos.getTipoEJGColegio() != null)
						record.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));

					// Campo de prestaciones
					if (datos.getPrestacionesRechazadas() != null) {
						ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();

						preRe.setIdinstitucion(idInstitucion);
						preRe.setAnio(Short.parseShort(datos.getAnnio()));
						preRe.setNumero(record.getNumero());
						preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
						preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
						preRe.setFechamodificacion(new Date());
						preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));

						for (String idprestacion : datos.getPrestacionesRechazadas()) {
							preRe.setIdprestacion(Short.parseShort(idprestacion));

							response = scsEjgPrestacionRechazadaMapper.insert(preRe);
						}
					} else
						response = 1;

//					IDINSTITUCION
//					ANIO
//					NUMEJG
//					SUFIJO
//					Restriccion problematica.
					LOGGER.info(record.getIdinstitucion());
					LOGGER.info(record.getAnio());
					LOGGER.info(record.getNumejg());
					LOGGER.info(record.getSufijo());

					if (response == 1)
						response = scsEjgMapper.insert(record);

				} catch (Exception e) {
					LOGGER.error(
							"GestionEJGServiceImpl.insertaDatosGenerales(). ERROR: al hacer el insert de datos generales. ",
							e);
					response = 0;
				}
				// respuesta si se actualiza correctamente para que se rellene el campo de
				// numero
				if (response >= 1) {
					List<EjgItem> list = new ArrayList<>();

					EjgItem item = new EjgItem();

					item.seteidInstitucion(idInstitucion.toString());
					item.setAnnio(datos.getAnnio());
					item.setNumEjg(record.getNumejg());
					item.setNumero(record.getNumero().toString());
					item.setTipoEJG(datos.getTipoEJG());

					// Campos opcionales
					item.setFechapresentacion(datos.getFechapresentacion());
					item.setFechalimitepresentacion(datos.getFechalimitepresentacion());
					item.setTipoEJGColegio(datos.getTipoEJGColegio());
					item.setIdTipoExpInsos(datos.getIdTipoExpInsos());

					list.add(item);

					ejgdto.setEjgItems(list);
//						responsedto.setStatus(SigaConstants.OK);
					error.setCode(200);
					LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> OK. Datos generales insertados");
				} else {
//						responsedto.setStatus(SigaConstants.KO);
					error.setCode(400);
					LOGGER.error(
							"GestionEJGServiceImpl.insertaDatosGenerales() -> KO. No se ha insertado los datos generales");
				}
				ejgdto.setError(error);

			}
		}

		LOGGER.info("GestionEJGServiceImpl.insertaDatosGenerales() -> Salida del servicio.");

		return ejgdto;
	}

	@Override
	public EjgDesignaDTO getEjgDesigna(EjgItem datos, HttpServletRequest request) {

		LOGGER.info("getEjgDesigna() -> Entrada al servicio para obtener el colegiado");
		EjgDesignaDTO ejgDesignaDTO = new EjgDesignaDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {

				LOGGER.info(
						"getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Entrada a scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");

				try {
					ScsEjgdesignaExample example = new ScsEjgdesignaExample();

					example.createCriteria().andAniodesignaEqualTo(Short.parseShort(datos.getAnnio()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdtipoejgEqualTo(Short.parseShort(datos.getTipoEJG()))
							.andNumeroejgEqualTo(Long.parseLong(datos.getNumero()));

					List<ScsEjgdesigna> ejgDesignas = scsEjgdesignaMapper.selectByExample(example);

					ejgDesignaDTO.setScsEjgdesignas(ejgDesignas);

					LOGGER.info(
							"getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Salida de scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");
				} catch (Exception e) {
					LOGGER.debug(
							"getEjgDesigna() -> Se ha producido un error al obtener asociaciones con designaciones del EJG. ",
							e);
				}
			} else {
				LOGGER.warn(
						"getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEjgDesigna() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener las asociaciones con designaciones del EJG.");
		return ejgDesignaDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO actualizaDatosGenerales(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.info(
					"GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para actualizar los datos generales del ejg");

				try {
//					for (int i = 0; datos.get) > i; i++) {
					response = 0;

//						Clave primaria scsEstadoejg
//						IDINSTITUCION
//						IDTIPOEJG
//						ANIO
//						NUMERO
//						IDESTADOPOREJG

					// seleccionamos el objeto para el update

					ScsEjgKey ejgKey = new ScsEjgKey();

					ejgKey.setIdinstitucion(idInstitucion);
					ejgKey.setAnio(Short.parseShort(datos.getAnnio()));
					ejgKey.setNumero(Long.parseLong(datos.getNumero()));
					ejgKey.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
//						key.setIdestadoporejg(Long.parseLong(datos.getEstadoEJG()));

					ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

					// Modificamos el objeto obtenido

					ejg.setFechaapertura(datos.getFechaApertura());
					ejg.setFechapresentacion(datos.getFechapresentacion());
					ejg.setFechalimitepresentacion(datos.getFechalimitepresentacion());
					if (datos.getTipoEJGColegio() != null)
						ejg.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));
					ejg.setUsumodificacion(usuarios.get(0).getIdusuario());
					ejg.setFechamodificacion(new Date());

					// Actualizamos la entrada en la BBDD
					response = scsEjgMapper.updateByPrimaryKeySelective(ejg);

					// Actualizar el expediente del que se extrae el tipo de expediente
					// Clave primaria
//						IDINSTITUCION
//						IDINSTITUCION_TIPOEXPEDIENTE
//						IDTIPOEXPEDIENTE
//						NUMEROEXPEDIENTE
//						ANIOEXPEDIENTE

					ExpExpedienteKey expKey = new ExpExpedienteKey();

					expKey.setIdinstitucion(idInstitucion);
					if (datos.getAnioexpInsos() != null)
						expKey.setAnioexpediente(Short.parseShort(datos.getAnioexpInsos()));
					if (datos.getNumeroexpInsos() != null)
						expKey.setNumeroexpediente(Integer.parseInt(datos.getNumeroexpInsos()));
					if (datos.getIdTipoExpInsos() != null)
						expKey.setIdtipoexpediente(Short.parseShort(datos.getIdTipoExpInsos()));
					if (datos.getIdInstTipoExp() != null)
						expKey.setIdinstitucionTipoexpediente(Short.parseShort(datos.getIdInstTipoExp()));

					ExpExpediente newExp = expExpedienteMapper.selectByPrimaryKey(expKey);

					if (newExp != null) {
						if (datos.getTipoEJG() != null)
							newExp.setIdtipoexpediente(Short.parseShort(datos.getTipoEJG()));
						newExp.setUsumodificacion(usuarios.get(0).getIdusuario());
						newExp.setFechamodificacion(new Date());

						response = expExpedienteMapper.updateByPrimaryKeySelective(newExp);

					}

					// Manejo de prestaciones
					// Se comprueban las prestaciones rechazadas en la ficha.
					// El proceso que se va a seguir sera comprobar si se recibe una lista
					// prestaciones rechazadas
					// en tal caso, eliminar las existentes y despues recorrer las recibidas para i

//						Clave primaria
//						IDINSTITUCION
//						ANIO
//						NUMERO
//						IDTIPOEJG
//						IDPRESTACION
					// Comprobar en el caso de que no se haga ningún cambio y con algún cambio.
					if (datos.getPrestacionesRechazadas() != null) {
						ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();

						preRe.setIdinstitucion(idInstitucion);
						preRe.setAnio(Short.parseShort(datos.getAnnio()));
						preRe.setNumero(Long.parseLong(datos.getNumero()));
						preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
						preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
						preRe.setFechamodificacion(new Date());
						preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));

						ScsEjgPrestacionRechazadaExample examplePresRe = new ScsEjgPrestacionRechazadaExample();

						examplePresRe.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andAnioEqualTo(preRe.getAnio()).andNumeroEqualTo(preRe.getNumero())
								.andIdtipoejgEqualTo(preRe.getIdtipoejg()).andIdtipoejgEqualTo(preRe.getIdtipoejg());

						List<ScsEjgPrestacionRechazada> rechazadas = scsEjgPrestacionRechazadaMapper
								.selectByExample(examplePresRe);

						// Eliminamos las entradas existentes relacionadas si las hubiera.

						response = scsEjgPrestacionRechazadaMapper.deleteByExample(examplePresRe);

						if (rechazadas.isEmpty())
							response = 1;

						for (String idprestacion : datos.getPrestacionesRechazadas()) {
							preRe.setIdprestacion(Short.parseShort(idprestacion));

							response = scsEjgPrestacionRechazadaMapper.insert(preRe);
						}

					}

					LOGGER.info(
							"GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio para actualizar los datos generales del ejg");

				} catch (Exception e) {
					LOGGER.info(
							"GestionEJGServiceImpl.actualizaDatosGenerales() -> Se ha producido un error al actualizar los datos generales del ejg",
							e);
					response = 0;
				}
			}

			// respuesta si se actualiza correctamente
			if (response >= 1) {
				responsedto.setStatus(SigaConstants.OK);
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> OK. Datos Generales actualizados para el ejg");
			} else {
				responsedto.setStatus(SigaConstants.KO);
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> KO. No se ha actualizado ningún dato general del ejg");
			}

		}

		LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio.");

		return responsedto;
	}

	/**
	 * metodo para settear los datos del objeto para el insert o update
	 * 
	 * @param item
	 * @return
	 */
	private ScsEjgWithBLOBs setDatosGeneralesEJG(EjgItem item) {
		ScsEjgWithBLOBs result = new ScsEjgWithBLOBs();

		if (item.getidInstitucion() != null) {
			result.setIdinstitucion(Short.parseShort(item.getidInstitucion()));
		}

		if (item.getFechaApertura() != null) {
			result.setFechaapertura(item.getFechaApertura());
		}

		if (item.getAnnio() != null) {
			result.setAnio(Short.parseShort(item.getAnnio()));
		}

		if (item.getNumero() != null) {
			result.setNumero(Long.parseLong(item.getNumEjg()));
		}

		if (item.getFechapresentacion() != null) {
			result.setFechapresentacion(item.getFechapresentacion());
		}

		if (item.getFechalimitepresentacion() != null) {
			result.setFechalimitepresentacion(item.getFechalimitepresentacion());
		}

		// ver si id o nombre de estado ¿situacion?
		if (item.getEstadoEJG() != null) {
			result.setIdsituacion(Short.parseShort(item.getEstadoEJG()));
		}

		// tenemos q ver si devuleve id o nombre de tipo ejg
		if (item.getTipoEJG() != null) {

			result.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
		}

		if (item.getTipoEJGColegio() != null) {
			result.setIdtipoejgcolegio(Short.parseShort(item.getTipoEJGColegio()));
		}

		// PRETENSIONES FALTA POR HACER
//		if(item.getPr!=null) {
//			result.set.setPr();
//		}

//		if(item.getAnioexpediente()!=null) {
//			result.setAn(Short.parseShort(item.getAnioexpediente()));
//		}
//		
//		if(item.getNumeroexpediente()!=null) {
//			result.setNum(Short.parseShort(item.getNumeroexpediente()));
//		}
//		
//
//		if(item.getTurno()!=null) {
//			result.setTu(Short.parseShort(item.getTurno()));
//		}
//		
//		if(item.getGuardia()!=null) {
//			result.setGuardiaturnoIdguardia(item.getGuardia());
//		}
//		
//		if(item.getNumColegiado()!=null) {
//			result.setNum(Short.parseShort(item.getNumColegiado()));
//		}
//		
//		if(item.getNumeroexpediente()!=null) {
//			result.setNum(Short.parseShort(item.getNumeroexpediente()));
//		}

		return result;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarEstado(List<EstadoEjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarEstado() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (EstadoEjgItem item : datos) {
						response = 0;
						
						ScsEstadoejg record = new ScsEstadoejg();
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(item.getIdtipoejg()));
						record.setAnio(Short.parseShort(item.getAnio()));
						record.setNumero(Long.parseLong(item.getNumero()));
						record.setIdestadoporejg(Long.parseLong(item.getIdestadoporejg()));
						record.setFechabaja(new Date());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);
						
						if (response != 1) {
							responsedto.setStatus(SigaConstants.KO);
							LOGGER.error("GestionEJGServiceImpl.borrarEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
							throw new Exception("ERROR: no se ha podido ");
						}else {
							responsedto.setStatus(SigaConstants.OK);
						}
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					
				} 
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarFamiliar(List<UnidadFamiliarEJGItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarFamiliar() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarFamiliar() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (int i = 0; datos.size() > i; i++) {

						response = 0;

						// seleccionamos el objeto por key
						ScsUnidadfamiliarejgKey key = new ScsUnidadfamiliarejgKey();

						key.setIdinstitucion(idInstitucion);
						key.setIdtipoejg(Short.parseShort(datos.get(i).getUf_idTipoejg()));
						key.setAnio(Short.parseShort(datos.get(i).getUf_anio()));
						key.setNumero(Long.parseLong(datos.get(i).getUf_numero()));
						key.setIdpersona(Long.parseLong(datos.get(i).getUf_idPersona()));

						ScsUnidadfamiliarejg record = scsUnidadfamiliarejgMapper.selectByPrimaryKey(key);

						// Modificamos el objeto

						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());

						// Según se elimine o se active.
						if (datos.get(i).getFechaBaja() == null) {
							record.setFechabaja(new Date());
							
							//Se elimina su propiedad como solicitante principal
							if(record.getSolicitante().equals("1")) {
								record.setSolicitante((short)0);
								
								//Actualizamos el ejg correspondientemente para que no lo considere su solicitante principal
								ScsEjgKey ejgKey = new ScsEjgKey();
								
								ejgKey.setAnio(Short.parseShort(datos.get(i).getUf_anio()));
								ejgKey.setIdinstitucion(idInstitucion);
								ejgKey.setIdtipoejg(Short.parseShort(datos.get(i).getUf_idTipoejg()));
								ejgKey.setNumero(Long.parseLong(datos.get(i).getUf_numero()));
								
								ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
								
								ejg.setIdpersonajg(null);
							}
							response = scsUnidadfamiliarejgMapper.updateByPrimaryKeySelective(record);
						} else {
							record.setFechabaja(null);
							response = scsUnidadfamiliarejgMapper.updateByPrimaryKey(record);
						}

					}
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarFamiliar() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarFamiliar() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarFamiliar() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public InsertResponseDTO nuevoEstado(EstadoEjgItem datos, HttpServletRequest request) {
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para cambiar los datos generales del ejg");

				try {
					
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(Short.parseShort(datos.getIdinstitucion()));
						record.setAnio(Short.parseShort(datos.getAnio()));
						record.setNumero(Long.parseLong(datos.getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.getIdEstadoejg()));
						record.setFechainicio(datos.getFechaInicio());
						record.setObservaciones(datos.getObservaciones());
						
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());

						response = scsEstadoejgMapper.insertSelective(record);
						
						
					
					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.nuevoEstado() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.nuevoEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarImpugnacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarImpugnacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.guardarImpugnacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.guardarImpugnacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarImpugnacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarResolucion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarResolucion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarResolucion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarResolucion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.guardarResolucion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.guardarResolucion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarResolucion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.guardarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.guardarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.borrarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO descargarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.descargarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.descargarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO descargarDocumentacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarDocumentacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarDocumentacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarDocumentacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.descargarDocumentacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.descargarDocumentacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarDocumentacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarRelacion(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");
						record.setFechabaja(new Date());

						response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO updateDatosJuridicos(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {
					ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();
						response = 0;

						// Preparamos las variables para buscar la tabla a actualizar
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
						record.setAnio(Short.parseShort(datos.getAnnio()));
						record.setNumero(Long.parseLong(datos.getNumero()));
						
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						// Preparamos las variables de la tabla a actualizar
						if(datos.getRenuncia()!=null)record.setIdrenuncia(Short.parseShort(datos.getRenuncia()));
						else record.setIdrenuncia(null);
						if(datos.getPerceptivo()!=null)record.setIdpreceptivo(Short.parseShort(datos.getPerceptivo()));
						else record.setIdpreceptivo(null);
						record.setIdsituacion(datos.getIdsituacion());
						record.setNumerodiligencia(datos.getNumerodiligencia());
						record.setComisaria(datos.getComisaria());
						record.setCalidad(datos.getCalidad());
						
						//
						record.setNumeroprocedimiento(dni);
						record.setAnioprocedimiento(idInstitucion);
						record.setNig(datos.getNig());
						if(datos.getJuzgado()!=null)record.setJuzgado(Long.parseLong(datos.getJuzgado()));
						else record.setJuzgado(null);
						record.setIdpretension(datos.getIdPretension());
						record.setObservaciones(datos.getObservaciones());
						record.setDelitos(datos.getDelitos());
						
						response = scsEjgMapper.updateByPrimaryKeySelective(record);
					
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}

	/**
	 * getComunicaciones
	 */
	public EnviosMasivosDTO getComunicaciones(EjgItem item, HttpServletRequest request) {

		LOGGER.info("GestionEJGServiceImpl.getComunicaciones() -> Entrada al servicio para obtener comunicaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EnviosMasivosDTO enviosMasivosDTO = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosMasivosItem = new ArrayList<EnviosMasivosItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.debug(
						"busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las comunicaciones");

				//obtenemos los datos de la comunicacion
				enviosMasivosItem = scsEjgExtendsMapper.getComunicaciones(item.getNumEjg(), item.getAnnio(), item.getTipoEJG(), idInstitucion, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las comunicaciones");

				if (enviosMasivosItem != null) {
					enviosMasivosDTO.setEnviosMasivosItem(enviosMasivosItem);
				}
			}

		}
		LOGGER.info("busquedaComunicaciones() -> Salida del servicio para obtener comunicaciones");
		
		return enviosMasivosDTO;
	}

	public RelacionesDTO getRelacionesEJG(EjgItem item, HttpServletRequest request) {
		LOGGER.info("getRelacionesEJG() -> Entrada al servicio para obtener relaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RelacionesDTO relacionesDTO = new RelacionesDTO();
		List<RelacionesItem> relacionesItem = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getRelacionesEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getRelacionesEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getRelacionesEJG() / scsProcuradorExtendsMapper.busquedaRelaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las relaciones");

				relacionesItem = scsEjgExtendsMapper.getRelacionesEJG(item);

				
				
				LOGGER.info(
						"busquedaRelaciones() / scsDesignacionesExtendsMapper.busquedaRelaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las relaciones");

				if (relacionesItem != null) {
					
					relacionesDTO.setRelacionesItem(relacionesItem);
				}
			}

		}
		LOGGER.info("getRelacionesEJG()) -> Salida del servicio para obtener relaciones");
		return relacionesDTO;
	}
	
	@Override
	public List<ListaContrarioEJGJusticiableItem> busquedaListaContrariosEJG(EjgItem item, HttpServletRequest request,
			Boolean historico) {
		LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Entrada al servicio servicio");
		List<ListaContrarioEJGJusticiableItem> contrarios = null;
//		List<GenParametros> tamMax = null;
//		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

//			GenParametrosExample genParametrosExample = new GenParametrosExample();
//			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
//					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
//					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
//			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
//			LOGGER.info(
//					"GestionEJGServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
//			LOGGER.info(
//					"GestionEJGServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			if (tamMax != null) {
//				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
//			} else {
//				tamMaximo = null;
//			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"GestionEJGServiceImpl.busquedaListaContrariosEJG -> Entrada a servicio para la busqueda de contrarios en EJG");

				try {
					contrarios = scsContrariosejgExtendsMapper.busquedaListaContrariosEJG(item, idInstitucion, historico);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG -> Salida del servicio");
				}
				LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG -> Salida del servicio");
			}
		}

		return contrarios;
	}

	@Override
	public UpdateResponseDTO deleteContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("deleteContrarios() ->  Entrada al servicio para eliminar contrarios");

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
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());
					key.setIdtipoejg(item.getIdtipoejg());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					if (contrario.getFechabaja() == null) {
						contrario.setFechabaja(new Date());
					} else {
						contrario.setFechabaja(null);
					}

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"deleteContrario() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"deleteContrario() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("insertContrario() ->  Entrada al servicio para insertar contrarios");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsContrariosejg contrario = new ScsContrariosejg();
					contrario.setAnio(item.getAnio());
					contrario.setNumero(item.getNumero());
					contrario.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					contrario.setIdpersona(item.getIdpersona());
					contrario.setIdtipoejg(item.getIdtipoejg());
					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"insertContrarioEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info(
							"insertContrarioEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciable");

					// Se comprueba si tiene representante y se busca.
					if (personajg.getIdrepresentantejg() != null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						contrario.setNombrerepresentanteejg(representante.getApellido1() + " " + representante.getApellido2()
								+ ", " + representante.getNombre());
					}

					LOGGER.info(
							"insertContrarioEJG() / scsContrariosejgMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para insertar contrario ejg");

					response = scsContrariosejgMapper.insert(contrario);

					LOGGER.info(
							"insertContrarioEJG() / scsContrariosejgMapper.insert() -> Salida de ScsDefendidosdesignaMapper para insertar contrario ejg");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertContrario() -> Salida del servicio para insertar contrarios");

		return insertResponseDTO;
	}
	

	@Override
	public UpdateResponseDTO updateRepresentanteContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("updateRepresentanteContrarioEJG() ->  Entrada al servicio para eliminar contrarios");

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
					"updateRepresentanteContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRepresentanteContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdtipoejg(item.getIdtipoejg());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					contrario.setNombrerepresentanteejg(item.getNombrerepresentanteejg());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateRepresentanteContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosejgMapper para actualizar el representante de un contrario ejg.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateRepresentanteContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosejgMapper para actualizar el representante de un contrario ejg.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateRepresentanteContrarioEJG() -> Salida del servicio para actualizar el representante de un contrario ejg");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateAbogadoContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("updateAbogadoContrarioEJG() ->  Entrada al servicio para eliminar contrarios");

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
					"updateAbogadoContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAbogadoContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdtipoejg(item.getIdtipoejg());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					contrario.setIdabogadocontrarioejg(item.getIdabogadocontrarioejg());
					contrario.setNombreabogadocontrarioejg(item.getNombreabogadocontrarioejg());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

//						List<ColegiadoItem> colegiadosItems = cenColegiadoExtendsMapper.selectColegiadosByIdPersona(idInstitucion, contrario.getIdabogadocontrario().toString());
//						
//						FichaDatosColegialesItem abogado = colegiadosItems.get(0);

//						contrario.set

					LOGGER.info(
							"updateAbogadoContrarioEJG() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateAbogadoContrarioEJG() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateAbogadoContrarioEJG() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO updateProcuradorContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("updateProcuradorContrarioEJG() ->  Entrada al servicio para eliminar contrarios");

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
					"updateProcuradorContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateProcuradorContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdtipoejg(item.getIdtipoejg());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					contrario.setIdprocurador(item.getIdprocurador());

//					List<FichaDatosColegialesItem> colegiadosSJCSItems = cenColegiadoExtendsMapper
//							.selectDatosColegiales(item.getIdprocurador().toString(), idInstitucion.toString());

//					FichaDatosColegialesItem procurador = colegiadosSJCSItems.get(0);
//						contrario.set

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateProcuradorContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosejgMapper para actualizar el representante de un interesado.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateProcuradorContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosejgMapper para actualizar el representante de un interesado.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateProcuradorContrarioEJG() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}
	
	@Override
	public ProcuradorDTO busquedaProcuradorEJG(EjgItem ejg, HttpServletRequest request) {
		LOGGER.info("busquedaProcuradorEJG() -> Entrada al servicio para obtener procuradores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaProcuradorEJG() / scsEjgExtendsMapper.busquedaProcuradorEJG() -> Entrada a scsEjgExtendsMapper para obtener los procuradores");

				
				procuradorItemList = scsEjgExtendsMapper.busquedaProcuradorEJG(idInstitucion.toString());

				LOGGER.info(
						"busquedaProcuradorEJG() / scsEjgExtendsMapper.busquedaProcuradorEJG -> Salida a scsEjgExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					if(!procuradorItemList.isEmpty()) {
						procuradorItemList.get(0).setNumeroTotalProcuradores(String.valueOf(procuradorItemList.size()));
					}
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("busquedaProcuradorEJG() -> Salida del servicio para obtener procuradores");
		return procuradorDTO;
	}
	
	
	@Override
	public UpdateResponseDTO guardarProcuradorEJG(EjgItem item, HttpServletRequest request) {
		LOGGER.info("guardarProcuradorEJG() ->  Entrada al servicio para guardar procurador EJG");

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
					"guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {


					//Introducimos los atributos claves para seleccionar el ejg que queremos actualizar
					ScsEjgWithBLOBs ejg = new ScsEjgWithBLOBs();
					ejg.setAnio(Short.parseShort(item.getAnnio()));
					ejg.setNumero(Long.parseLong(item.getNumero()));
					ejg.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
					ejg.setIdinstitucion(Short.parseShort(idInstitucion.toString()));

					ejg.setIdprocurador(Long.parseLong(item.getIdProcurador()));
					ejg.setIdinstitucionProc(item.getIdInstitucionProc());

					ejg.setFechamodificacion(new Date());
					ejg.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKeySelective() -> Entrada a scsContrariosejgMapper para guardar procurador EJG.");

					response = scsEjgMapper.updateByPrimaryKeySelective(ejg);

					LOGGER.info(
							"guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKeySelective() -> Salida de scsContrariosejgMapper para guardar procurador EJG.");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("guardarProcuradorEJG() -> Salida del servicio para guardar procurador EJG");

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO nuevoProcuradorEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("nuevoProcuradorEJG() ->  Entrada al servicio para insertar un procurador ejg");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

//		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"nuevoProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"nuevoProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				
				//Creamos el objeto a actualizar
				
				ScsEjgWithBLOBs ejg = new ScsEjgWithBLOBs();
				
				ejg.setAnio(Short.parseShort(ejgItem.getAnnio()));
				ejg.setNumero(Long.parseLong(ejgItem.getNumero()));
				ejg.setIdtipoejg(Short.parseShort(ejgItem.getTipoEJG()));
				ejg.setIdinstitucion(idInstitucion);
				
				ejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				ejg.setFechamodificacion(new Date());
				
				ejg.setIdprocurador(Long.parseLong(ejgItem.getIdProcurador()));	
				ejg.setIdinstitucionProc(ejgItem.getIdInstitucionProc());
				
				response = scsEjgMapper.updateByPrimaryKeySelective(ejg);

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha insertado el procurador ejg");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha insertado el procurador ejg correctamente");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				updateResponseDTO.setError(error);

				LOGGER.info("nuevoProcuradorEJG() -> Salida del servicio para insertar un procurador ejg");

			}

		}

		return updateResponseDTO;
	}
	
	public DelitosEjgDTO getDelitosEjg(EjgItem item, HttpServletRequest request) {

		LOGGER.info("GestionEJGServiceImpl.getDelitosEjg() -> Entrada al servicio para obtener delitos ejg");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DelitosEjgDTO delitosEjgDTO = new DelitosEjgDTO();
		List<ScsDelitosejg> delitosEjgItem = new ArrayList<ScsDelitosejg>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getDelitosEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getDelitosEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.debug(
						"getDelitosEjg() / scsDelitosejgMapper.selectByExample() -> Entrada a scsDelitosejgMapper para obtener los delitos ejg");

				//Obtenemos los delitos ejg que esten asignados a nuestro EJG
				
				ScsDelitosejgExample example = new ScsDelitosejgExample();
				
				example.createCriteria().andAnioEqualTo(Short.parseShort(item.getAnnio()))
				.andIdinstitucionEqualTo(idInstitucion).andNumeroEqualTo(Long.parseLong(item.getNumero()))
				.andIdtipoejgEqualTo(Short.parseShort(item.getTipoEJG()));
				
				delitosEjgItem = scsDelitosejgMapper.selectByExample(example);

				LOGGER.info(
						"getDelitosEjg() / scsDelitosejgMapper.selectByExample() -> Salida a scsDelitosejgMapper para obtener los delitos ejg");

				if (delitosEjgItem != null) {
					delitosEjgDTO.setDelitosEjgItem(delitosEjgItem);
				}
			}

		}
		LOGGER.info("getDelitosEjg() -> Salida del servicio para obtener delitos ejg");
		
		return delitosEjgDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO actualizarDelitosEJG(EjgItem item, HttpServletRequest request) {
		LOGGER.info("actualizarDelitosEJG() -> Entrada al servicio para insertar delitos ejg");
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.actualizarDelitosEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.actualizarDelitosEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.actualizarDelitosEJG() -> Entrada para insertar delitos ejg");

				try {
					ScsDelitosejg delitos = new ScsDelitosejg();
					
					delitos.setIdinstitucion(idInstitucion);
					delitos.setAnio(Short.parseShort(item.getAnnio()));
					delitos.setNumero(Long.parseLong(item.getNumero()));
					delitos.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
					delitos.setUsumodificacion(usuarios.get(0).getIdusuario());
					delitos.setFechamodificacion(new Date());

					ScsDelitosejgExample oldDelitos = new ScsDelitosejgExample();

					oldDelitos.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andAnioEqualTo(delitos.getAnio()).andNumeroEqualTo(delitos.getNumero())
							.andIdtipoejgEqualTo(delitos.getIdtipoejg());

					// Eliminamos las entradas existentes relacionadas si las hubiera.

					response = scsDelitosejgMapper.deleteByExample(oldDelitos);

					if (item.getDelitos().isEmpty())
						response = 1;

					String[] idDelitos = item.getDelitos().split(" ");
					for (String idDelito : idDelitos) {
						delitos.setIddelito(Short.parseShort(idDelito));

						response = scsDelitosejgMapper.insert(delitos);
					}

					LOGGER.debug(
							"GestionEJGServiceImpl.actualizarDelitosEJG() -> Salida del servicio para insertar delitos ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.actualizarDelitosEJG() -> Se ha producido un error al insertar delitos ejgs. ",
							e);
				}
				// respuesta si se actualiza correctamente
				if (response >= 1) {
					responsedto.setStatus(SigaConstants.OK);
					LOGGER.debug("GestionEJGServiceImpl.actualizarDelitosEJG() -> OK.");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error("GestionEJGServiceImpl.actualizarDelitosEJG() -> KO.");
				}
			}
		}
		return responsedto;
	}
	
	
}