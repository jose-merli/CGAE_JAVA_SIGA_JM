package org.itcgae.siga.scs.services.impl.ejg.acta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActasDTO;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;

import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsActacomision;
import org.itcgae.siga.db.entities.ScsActacomisionKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgActa;
import org.itcgae.siga.db.entities.ScsEjgActaExample;
import org.itcgae.siga.db.entities.ScsEjgActaKey;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.mappers.ScsActacomisionMapper;
import org.itcgae.siga.db.mappers.ScsEjgActaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActaExtendsMapper;
import org.itcgae.siga.scs.services.acta.IBusquedaActa;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
public class BusquedaActaServiceImpl implements IBusquedaActa {

	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private ScsActaExtendsMapper scsActaExtendsMapper;

	@Autowired

	private ScsActacomisionMapper scsActacomisionMapper;
	@Autowired
	private ScsEjgActaMapper scsEjgActaMapper;

	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Override
	public ActasDTO busquedaActas(ActasItem actasItem, HttpServletRequest request) {
		LOGGER.info("busquedaActas() -> Entrada al servicio para obtener el colegiado");
		ActasDTO actasDTO = new ActasDTO();
		Error error = new Error();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			try {
				LOGGER.info(
						"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsActaExtendsMapper para obtener las actas");
				actasDTO.actasItem(scsActaExtendsMapper.busquedaActas(actasItem, Short.valueOf(idInstitucion)));
				LOGGER.info(
						"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsActaExtendsMapper para obtener lista de Actas");
			} catch (Exception e) {
				error.setCode(500);
				error.setDescription("No se han podido relaizar la busqueda de actas");
				LOGGER.info(
						"Error" + e);
				LOGGER.info(
						"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsActaExtendsMapper para obtener lista de Actas por un error");
			}

		} else {
			LOGGER.warn("busquedaActas() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");
		actasDTO.setError(error);
		return actasDTO;
	}

	@Override
	public DeleteResponseDTO borrarActas(ActasItem actasItem, HttpServletRequest request) {
		LOGGER.info("borrarActas() -> Entrada al servicio para obtener el colegiado");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		ActasDTO comprobarBorraractasDTO = new ActasDTO();
		Error error = new Error();
		List<GenParametros> tamMax = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"borrarActas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"borrarActas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"borrarActas() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			LOGGER.info(
					"borrarActas() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			ScsActacomisionKey key = new ScsActacomisionKey();
			key.setAnioacta(Short.valueOf(actasItem.getAnio()));
			key.setIdacta(Long.valueOf(actasItem.getIdActa()));
			key.setIdinstitucion(idInstitucion);

			ScsActacomision acta = scsActacomisionMapper.selectByPrimaryKey(key);

			ScsEjgActaExample example = new ScsEjgActaExample();

			example.createCriteria().andIdactaEqualTo(acta.getIdacta());
			example.createCriteria().andIdinstitucionactaEqualTo(acta.getIdinstitucion());
			example.createCriteria().andAnioactaEqualTo(acta.getAnioacta());

			List<ScsEjgActa> listaEjgAsociadosActa = scsEjgActaMapper.selectByExample(example);

			if (listaEjgAsociadosActa.isEmpty() || listaEjgAsociadosActa == null) {
				LOGGER.info(
						"borrarActas() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsActaExtendsMapper para obtener las actas");

				int response = scsActacomisionMapper.deleteByPrimaryKey(acta);
				if (response == 1) {
					deleteResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.info("borrarActas() -> OK. Acta eliminada correctamente");
				} else {
					deleteResponseDTO.setStatus(SigaConstants.KO);
					error.setCode(500);
					error.setDescription("Acta NO eliminada correctamente");
					deleteResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("borrarActas() -> KO. Acta NO eliminada correctamente");
				}

				LOGGER.info(
						"borrarActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsActaExtendsMapper para obtener lista de Actas");
			} else {
				deleteResponseDTO.setStatus(SigaConstants.KO);
				error.setCode(500);
				error.setDescription("Acta NO eliminada porque tiene elementos asociados");
				deleteResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.info("El acta tiene elementos asociados, no se ha podido borrar");
				LOGGER.warn("borrarActas() -> KO. Acta NO eliminada correctamente");
			}

		} else {
			deleteResponseDTO.setStatus(SigaConstants.KO);
			error.setCode(500);
			error.setDescription("Acta NO eliminada porque idInstitucion del token nulo");
			deleteResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("borrarActas() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de borrarActas");
		deleteResponseDTO.setError(error);
		return deleteResponseDTO;
	}

	@Override
	public ComboDTO comboSufijoActa(HttpServletRequest request) {
		LOGGER.info("comboSufijoActa() -> Entrada al servicio para obtener el colegiado");
		ComboDTO comboDTO = new ComboDTO();
		List<GenParametros> tamMax = null;
		Error error = new Error();
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ComboItem> comboItems = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboSufijoActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboSufijoActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"comboSufijoActa() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			LOGGER.info(
					"comboSufijoActa() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			try {
				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener el combo de sufijos para actas");

				comboItems = scsActaExtendsMapper.comboSufijoActa(Short.valueOf(idInstitucion));
				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener el combo de sufijos para actas");
			} catch (Exception e) {
				error.setCode(500);
				error.setDescription("Fallo a la hora de devolver los sufijos");
				LOGGER.info(
						"comboSufijoActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener el combo de sufijos para actas");
			}

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			} else {
				error.setCode(500);
				error.setDescription("El listado de combos es nulo");
			}

		} else {
			error.setCode(500);
			error.setDescription("Fallo a la hora de devolver los sufijos, idInstitucion del token nula");
			LOGGER.warn("comboSufijoActa() -> idInstitucion del token nula");

		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los sufijos de las actas");
		comboDTO.setError(error);
		return comboDTO;
	}

	@Override
	public InsertResponseDTO guardarActa(ActasItem actasItem, HttpServletRequest request) {

		// el campo fechabaja es nulo y idestadoporejg mas nueva
		// maestroestado ejg para ver el “Remitido a Comisión”

		LOGGER.info("guardarActa() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idUltimoEstado = "";
		List<ComboItem> comboItems = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"guardarActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"guardarActa() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			LOGGER.info(
					"guardarActa() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			int year = Calendar.getInstance().get(Calendar.YEAR);

			ScsActacomisionKey key = new ScsActacomisionKey();
			key.setAnioacta(Short.valueOf(actasItem.getAnio()));
			key.setIdacta(Long.valueOf(actasItem.getIdActa()));
			key.setIdinstitucion(idInstitucion);

			ScsActacomision acta = scsActacomisionMapper.selectByPrimaryKey(key);

			if (actasItem.getAnio().equalsIgnoreCase(String.valueOf(year)) && actasItem.getFechaReunion() != null
					&& actasItem.getMiembros().length() <= 4000 && actasItem.getPendientes().length() <= 4000
					&& scsActaExtendsMapper.comprobarGuardarActaPonente(actasItem, Short.valueOf(idInstitucion)) == null
					&& scsActaExtendsMapper.comprobarGuardarActaSufijo(actasItem,
							Short.valueOf(idInstitucion)) == null) {

				LOGGER.info(
						"guardarActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para guardar el acta");

				acta.setAnioacta(Short.valueOf(actasItem.getAnio()));
				acta.setNumeroacta(actasItem.getNumeroActa() + actasItem.getSufijo());
				acta.setFechareunion(actasItem.getFechaReunion());
				acta.setHorainicioreunion(actasItem.getHoraInicio());
				acta.setHorafinreunion(actasItem.getHoraFin());
				acta.setFecharesolucion(actasItem.getFechaResolucion());
				acta.setIdpresidente(Integer.parseInt(actasItem.getIdPresidente()));
				acta.setIdsecretario(Integer.parseInt(actasItem.getIdSecretario()));
				acta.setMiembroscomision(actasItem.getMiembros());
				acta.setObservaciones(actasItem.getObservaciones());
				acta.setPendientes(actasItem.getPendientes());

				scsActacomisionMapper.updateByPrimaryKey(acta);

				LOGGER.info(
						"guardarActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para guardar el acta");

			} else {
				error.setCode(500);
				error.setDescription("No se han cumplido los requisitos para guardar el acta");
				LOGGER.info("guardarActa() No se han cumplido los requisitos para guardar el acta");
			}

		} else {
			LOGGER.warn("guardarActa() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para guardar el Acta");
		insertResponseDTO.setError(error);
		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO anadirEJGPendientesCAJG(ActasItem actasItem, HttpServletRequest request) {
		LOGGER.info("busquedaEJGComision() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idUltimoEstado = "";
		String pendientes = "";

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"busquedaActas() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			LOGGER.info(
					"busquedaActas() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			LOGGER.info(
					"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener las actas");

			ScsActacomisionKey key = new ScsActacomisionKey();
			key.setAnioacta(Short.valueOf(actasItem.getAnio()));
			key.setIdacta(Long.valueOf(actasItem.getIdActa()));
			key.setIdinstitucion(idInstitucion);

			ScsActacomision acta = scsActacomisionMapper.selectByPrimaryKey(key);

			ScsEjgExample example = new ScsEjgExample();

			example.createCriteria().andIdactaEqualTo(acta.getIdacta());
			example.createCriteria().andIdinstitucionEqualTo(acta.getIdinstitucion());
			example.createCriteria().andAnioactaEqualTo(acta.getAnioacta());
			example.createCriteria().andIdtiporatificacionejgEqualTo(Short.valueOf("6"));
			example.createCriteria().andIdtiporatificacionejgEqualTo(Short.valueOf("4"));

			List<ScsEjg> listaEjgAsociadosActa = scsEjgMapper.selectByExample(example);

			if (listaEjgAsociadosActa != null) {
				for (ScsEjg ejgItem : listaEjgAsociadosActa) {
					String crearPendientes = ejgItem.getAnio() + "/" + ejgItem.getNumejg();
					pendientes += crearPendientes;

					ScsEjgActaKey ejgActakey = new ScsEjgActaKey();
					ejgActakey.setAnioacta(Short.valueOf(actasItem.getAnio()));
					ejgActakey.setAnioejg(ejgItem.getAnio());
					ejgActakey.setIdacta(ejgItem.getIdacta());
					ejgActakey.setIdinstitucionacta(acta.getIdinstitucion());
					ejgActakey.setIdinstitucionejg(ejgItem.getIdinstitucion());
					ejgActakey.setIdtipoejg(ejgItem.getIdtipoejg());
					ejgActakey.setNumeroejg(ejgItem.getNumero());

					scsEjgActaMapper.deleteByPrimaryKey(ejgActakey);

					if (ejgItem.getNumeroresolucion() == "4") {

						ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();

						exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion());
						exampleEstado.createCriteria().andIdtipoejgEqualTo(ejgItem.getIdtipoejg());
						exampleEstado.createCriteria().andAnioEqualTo(ejgItem.getAnio());
						exampleEstado.createCriteria().andNumeroEqualTo(ejgItem.getNumero());
						exampleEstado.createCriteria().andIdestadoejgEqualTo(Short.valueOf("9"));
						exampleEstado.setOrderByClause("FECHAMODIFICACION DESC");

						List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper.selectByExample(exampleEstado);

						ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);

						scsEstadoejg.setObservaciones("Expediente pendiente de la CAJG. Se retira del acta "
								+ acta.getAnioacta() + "/" + acta.getNumeroacta());

						scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

					} else if (ejgItem.getNumeroresolucion() == "6") {

						ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();

						exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion());
						exampleEstado.createCriteria().andIdtipoejgEqualTo(ejgItem.getIdtipoejg());
						exampleEstado.createCriteria().andAnioEqualTo(ejgItem.getAnio());
						exampleEstado.createCriteria().andNumeroEqualTo(ejgItem.getNumero());
						exampleEstado.createCriteria().andIdestadoejgEqualTo(Short.valueOf("9"));
						exampleEstado.setOrderByClause("FECHAMODIFICACION DESC");

						List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper.selectByExample(exampleEstado);

						ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);

						scsEstadoejg.setObservaciones("Expediente pendiente de la CAJG. Se retira del acta "
								+ acta.getAnioacta() + "/" + acta.getNumeroacta());

						scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

					}

				}
				acta.setObservaciones(pendientes);
				scsActacomisionMapper.updateByPrimaryKey(acta);

			}

			LOGGER.info(
					"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");
		} else {
			LOGGER.warn("busquedaEJGComision() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO abrirActa(ActasItem actasItem, HttpServletRequest request) {
		
		LOGGER.info("busquedaEJGComision() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"busquedaActas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			LOGGER.info("busquedaActas() / buscando el acta ");

			ScsActacomisionKey key = new ScsActacomisionKey();
			key.setAnioacta(Short.valueOf(actasItem.getAnio()));
			key.setIdacta(Long.valueOf(actasItem.getIdActa()));
			key.setIdinstitucion(Short.valueOf("2011"));

			LOGGER.info("busquedaActas() / valores para buscar el acta = " + actasItem.getAnio() + " id acta = "
					+ actasItem.getIdActa());

			ScsActacomision acta = scsActacomisionMapper.selectByPrimaryKey(key);
			
			if(acta != null) {

			LOGGER.info("busquedaActas() / acta encontrada");

			LOGGER.info("busquedaActas() / buscando los ejg asociados al acta");

			ScsEjgExample example = new ScsEjgExample();

			LOGGER.info("busquedaActas() / valores acta institucion = " + acta.getIdinstitucion() + " id acta = "
					+ acta.getIdacta() + " anioacta =  " + acta.getAnioacta());

			example.createCriteria().andIdactaEqualTo(acta.getIdacta())
					.andIdinstitucionEqualTo(acta.getIdinstitucion()).andAnioactaEqualTo(acta.getAnioacta());

			List<ScsEjg> listaEjgAsociadosActa = scsEjgMapper.selectByExample(example);
		

			LOGGER.info("busquedaActas() / encontrado los ejg asociados al acta");

			LOGGER.info("busquedaActas() / entro en el if");

			if (!listaEjgAsociadosActa.isEmpty()) {
				LOGGER.info("tamaño de la lista de ejg asociados" + listaEjgAsociadosActa.size());
				for (ScsEjg ejg : listaEjgAsociadosActa) {
					boolean masActasAbiertas = false;
					String informacionEjgActaAbierta = "";
					ejg.setFecharesolucioncajg(null);

					LOGGER.info("busquedaActas() / valores ejg institucion = " + ejg.getIdinstitucion()
							+ " idtipoejg =  " + ejg.getIdtipoejg() + " anio =  " + ejg.getAnio() + " numero =  "
							+ ejg.getNumero());

					LOGGER.info("busquedaActas() / buscando los estadosejg del ejg en cuestion");

					ScsEjgActaExample scsEjgActaExample = new ScsEjgActaExample();

					scsEjgActaExample.createCriteria().andIdinstitucionejgEqualTo(ejg.getIdinstitucion())
							.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andAnioejgEqualTo(ejg.getAnio())
							.andNumeroejgEqualTo(ejg.getNumero());

					List<ScsEjgActa> listaEjgActa = scsEjgActaMapper.selectByExample(scsEjgActaExample);

					if (!listaEjgActa.isEmpty()) {

						for (ScsEjgActa scsEjgActa : listaEjgActa) {

							ScsActacomisionKey actakey = new ScsActacomisionKey();
							actakey.setAnioacta(Short.valueOf(scsEjgActa.getAnioacta()));
							actakey.setIdacta(Long.valueOf(scsEjgActa.getIdacta()));
							actakey.setIdinstitucion(scsEjgActa.getIdinstitucionacta());

							ScsActacomision actaComision = scsActacomisionMapper.selectByPrimaryKey(key);

							if (actaComision.getFecharesolucion() == null) {
								masActasAbiertas = true;
								informacionEjgActaAbierta += "  Acta abierta -> idActa = "
										+ actaComision.getIdacta() + " año = " + actaComision.getAnioacta()
										+ " idInstitucion = " + actaComision.getIdinstitucion()
										+ " para el ejg -> idInstitucion = " + ejg.getIdinstitucion()
										+ " idTipoEjg = " + ejg.getIdtipoejg() + " año = " + ejg.getAnio()
										+ " numeroEjg = " + ejg.getNumejg();
							}

						}
					}else {
						
						error.setCode(500);
						error.setDescription("La lista de objetos scs_ejg_acta esta vacia");
					}

					if (masActasAbiertas == false) {

						acta.setFecharesolucion(null);

						String idEstadoPorEjg = scsActaExtendsMapper.getEstadosEjg(ejg.getIdinstitucion(),
								ejg.getIdtipoejg(), ejg.getAnio(), ejg.getNumero());

						ScsEstadoejgKey estadoejgKey = new ScsEstadoejgKey();

						estadoejgKey.setAnio(ejg.getAnio());
						estadoejgKey.setIdestadoporejg(Long.valueOf(idEstadoPorEjg));
						estadoejgKey.setIdinstitucion(ejg.getIdinstitucion());
						estadoejgKey.setIdtipoejg(ejg.getIdtipoejg());
						estadoejgKey.setNumero(ejg.getNumero());

						LOGGER.info("ejg.getIdinstitucion()"+ ejg.getIdinstitucion());
						LOGGER.info("Long.valueOf(idEstadoPorEjg)"+ Long.valueOf(idEstadoPorEjg));
						LOGGER.info("ejg.getIdinstitucion()"+ ejg.getIdinstitucion());
						LOGGER.info("ejg.getIdtipoejg()"+ ejg.getIdtipoejg());
						LOGGER.info("ejg.getIdtipoejg()"+ ejg.getIdtipoejg());

						
						ScsEstadoejg estadoejgObject = scsEstadoejgMapper.selectByPrimaryKey(estadoejgKey);

						LOGGER.info("busquedaActas() / encontrado los estadosejg del ejg en cuestion");

						if (estadoejgObject != null) {
							LOGGER.info("scsestadoejg no es nulo ");
							if (estadoejgObject.getIdestadoporejg().equals(Long.valueOf("10"))) {
								LOGGER.info("scsestadoejg tiene el idestadopor ejg a 10  ");
								estadoejgObject.setFechabaja(new Date());
								int resultado = scsEstadoejgMapper.updateByPrimaryKey(estadoejgObject);
								LOGGER.info("ha actualizado el registro????? " + resultado);
							}
						}else {
							error.setCode(500);
							error.setDescription("El objeto de estado para el ejg es nulo");
						}

					}else {
						error.setCode(500);
						error.setDescription(informacionEjgActaAbierta);
					}

				}

			}else {
				error.setCode(500);
				error.setDescription("La lista de ejg esta vacia");
			}

			LOGGER.info(
					"busquedaActas() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");
			
			}else {
				error.setCode(500);
				error.setDescription("El acta es nula");
			}

		} else {
			error.setCode(500);
			error.setDescription("el id de la instuticon es nulo");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO cerrarActa(ActasItem actasItem, HttpServletRequest request) {
		LOGGER.info("cerrarActa() -> Entrada al servicio para obtener el colegiado");
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idUltimoEstado = "";

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"cerrarActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"cerrarActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			AdmUsuarios usuario = usuarios.get(0);
			usuario.setIdinstitucion(idInstitucion);
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"cerrarActa() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			LOGGER.info(
					"cerrarActa() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			LOGGER.info(
					"cerrarActa() / scsEjgExtendsMapper.busquedaEJG() -> Entrada a scsEjgExtendsMapper para obtener las actas");

			ActasItem actaItem = scsActaExtendsMapper.getActa(actasItem, Short.valueOf(idInstitucion));

			if (actaItem.getFechaResolucion() == null) {

				actasItem.setFechaResolucion(new Date());
			}

			ScsActacomisionKey key = new ScsActacomisionKey();
			key.setAnioacta(Short.valueOf(actaItem.getAnio()));
			key.setIdacta(Long.valueOf(actaItem.getIdActa()));
			key.setIdinstitucion(idInstitucion);

			ScsActacomision acta = scsActacomisionMapper.selectByPrimaryKey(key);

			ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();

			ScsEjgActaExample exampleEjgActa = new ScsEjgActaExample();
			ScsEjgExample example = new ScsEjgExample();

			example.createCriteria().andIdactaEqualTo(acta.getIdacta());
			example.createCriteria().andIdinstitucionEqualTo(acta.getIdinstitucion());
			example.createCriteria().andAnioactaEqualTo(acta.getAnioacta());

			List<ScsEjg> listaEjgAsociados = scsEjgMapper.selectByExample(example);

			if (!listaEjgAsociados.isEmpty()) {
				for (ScsEjg ejgItem : listaEjgAsociados) {
					if (ejgItem.getIdfundamentojuridico() == null) {
						ejgItem.setObservaciones(
								ejgItem.getObservaciones() + "Expediente sin Resolución. Se retira del acta "
										+ ejgItem.getAnio() + "/" + ejgItem.getNumejg());

						scsEjgMapper.updateByPrimaryKey(ejgItem);

						exampleEjgActa.createCriteria().andIdactaEqualTo(acta.getIdacta());
						exampleEjgActa.createCriteria().andIdinstitucionactaEqualTo(acta.getIdinstitucion());
						exampleEjgActa.createCriteria().andAnioactaEqualTo(acta.getAnioacta());
						exampleEjgActa.createCriteria().andIdinstitucionejgEqualTo(ejgItem.getIdinstitucion());
						exampleEjgActa.createCriteria().andIdtipoejgEqualTo(ejgItem.getIdtipoejg());
						exampleEjgActa.createCriteria().andAnioejgEqualTo(ejgItem.getAnio());
						exampleEjgActa.createCriteria().andNumeroejgEqualTo(Long.valueOf(ejgItem.getNumejg()));

						scsEjgActaMapper.deleteByExample(exampleEjgActa);

					} else {

						ejgItem.setObservaciones(
								ejgItem.getObservaciones() + "Expediente sin Resolución. Se retira del acta "
										+ ejgItem.getAnio() + "/" + ejgItem.getNumejg());

						ejgItem.setFecharesolucioncajg(acta.getFecharesolucion());

						scsEjgMapper.updateByPrimaryKey(ejgItem);

						exampleEstado.createCriteria().andIdinstitucionEqualTo(ejgItem.getIdinstitucion());
						exampleEstado.createCriteria().andIdtipoejgEqualTo(ejgItem.getIdtipoejg());
						exampleEstado.createCriteria().andAnioEqualTo(ejgItem.getAnio());
						exampleEstado.createCriteria().andNumeroEqualTo(ejgItem.getNumero());
						exampleEstado.setOrderByClause("FECHADESIGNA DESC");

						List<ScsEstadoejg> listaEstadoEjg = scsEstadoejgMapper.selectByExample(exampleEstado);

						if (!listaEstadoEjg.isEmpty()) {

							ScsEstadoejg scsEstadoejg = listaEstadoEjg.get(0);
							scsEstadoejg.setIdestadoporejg(Long.valueOf(10));
							scsEstadoejg.setAnio(Short.valueOf(ejgItem.getAnio()));
							scsEstadoejg.setNumero(Long.valueOf(ejgItem.getNumejg()));
							scsEstadoejg.setIdtipoejg(Short.valueOf(ejgItem.getIdtipoejg()));
							scsEstadoejg.setIdestadoejg((short) (scsEstadoejg.getIdestadoejg() + 1));
							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

							Date fechaResolucion = actasItem.getFechaResolucion();

							Date fechaSinHora;
							try {
								fechaSinHora = formatter.parse(formatter.format(fechaResolucion));
								scsEstadoejg.setFechainicio(fechaSinHora);

							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							scsEstadoejgMapper.updateByPrimaryKey(scsEstadoejg);

						}

						exampleEjgActa.createCriteria().andIdactaEqualTo(acta.getIdacta());
						exampleEjgActa.createCriteria().andIdinstitucionactaEqualTo(acta.getIdinstitucion());
						exampleEjgActa.createCriteria().andAnioactaEqualTo(acta.getAnioacta());
						exampleEjgActa.createCriteria().andIdinstitucionejgEqualTo(ejgItem.getIdinstitucion());
						exampleEjgActa.createCriteria().andIdtipoejgEqualTo(ejgItem.getIdtipoejg());
						exampleEjgActa.createCriteria().andAnioejgEqualTo(ejgItem.getAnio());
						exampleEjgActa.createCriteria().andNumeroejgEqualTo(Long.valueOf(ejgItem.getNumejg()));

						scsEjgActaMapper.deleteByExample(exampleEjgActa);

					}

				}

			}

			LOGGER.info(
					"cerrarActa() / scsEjgExtendsMapper.busquedaEJG() -> Salida de scsEjgExtendsMapper para obtener lista de Actas");
		} else {
			LOGGER.warn("cerrarActa() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener la lista de Actas");
		updateResponseDTO.setError(error);
		return updateResponseDTO;
	}

}