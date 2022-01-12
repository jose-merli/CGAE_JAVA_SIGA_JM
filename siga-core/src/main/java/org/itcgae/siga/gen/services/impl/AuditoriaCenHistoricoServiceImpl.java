package org.itcgae.siga.gen.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.CEN_TIPOCAMBIO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenComponentes;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaCenHistoricoServiceImpl implements IAuditoriaCenHistoricoService {

	Logger LOGGER = Logger.getLogger(AuditoriaCenHistoricoServiceImpl.class);
	private static final String salto = "\n";

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;

	@Autowired
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;

	@Override
	public void insertaCenHistorico(Long idPersona, CEN_TIPOCAMBIO tipoCambio, String descripcion,
			HttpServletRequest request, String motivo) {

		NewIdDTO idHistorico = new NewIdDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);

			Short newIdHistorico = new Short("1");
			idHistorico = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(String.valueOf(idPersona),
					String.valueOf(idInstitucion));

			if (null != idHistorico && !idHistorico.getNewId().isEmpty()) {
				newIdHistorico = Short.valueOf(idHistorico.getNewId());
			}

			CenHistorico cenHistorico = new CenHistorico();
			cenHistorico.setIdpersona(idPersona);
			cenHistorico.setIdinstitucion(idInstitucion);
			cenHistorico.setFechaentrada(new Date());
			cenHistorico.setFechaefectiva(new Date());
			if (!UtilidadesString.esCadenaVacia(motivo)) {
				cenHistorico.setMotivo(motivo);
			}

			cenHistorico.setIdtipocambio(tipoCambio.getIdTipoCambio());
			cenHistorico.setFechamodificacion(new Date());
			cenHistorico.setUsumodificacion(usuario.getIdusuario());
			cenHistorico.setIdhistorico(newIdHistorico);
			cenHistorico.setDescripcion(descripcion);
			cenHistorico.setObservaciones(null);
			cenHistoricoExtendsMapper.insertSelective(cenHistorico);
		}
	}

	/***
	 * Método para gestionar las auditorias cada vez que se realizan cambios en
	 * pantalla
	 * 
	 * @param gruposPersonaJuridica
	 * @param gruposPerJuridicaAntiguos
	 * @param cenPersonaAnterior
	 * @param cenPersonaPosterior
	 * @param cenNocolegiadoAnterior
	 * @param cenNocolegiadoPosterior
	 * @param cenClienteAnterior
	 * @param cenClientePosterior
	 */
	@Override
	public void manageAuditoriaDatosGenerales(List<String[]> gruposPerJuridicaNuevos,
			List<String[]> gruposPerJuridicaAntiguos, CenPersona cenPersonaAnterior, CenPersona cenPersonaPosterior,
			CenNocolegiado cenNocolegiadoAnterior, CenNocolegiado cenNocolegiadoPosterior,
			CenCliente cenClienteAnterior, CenCliente cenClientePosterior, String accion, HttpServletRequest request,
			String motivo, boolean cambioEtiquetas) {
		String cenPersonaDescripcion = null;
		String cenNoColegiadoDescripcion = null;
		String cenClienteDescripcion = null;

		switch (accion) {
		case "UPDATE":
			// auditoria para cen_persona
			cenPersonaDescripcion = getDescripcionCenPersona(cenPersonaAnterior, cenPersonaPosterior, accion);

			if (cenPersonaDescripcion != null) {

				insertaCenHistorico(cenPersonaPosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, cenPersonaDescripcion, request,
						motivo);
			}

			cenNoColegiadoDescripcion = getDescripcionCenNoColegiado(cenNocolegiadoAnterior, cenNocolegiadoPosterior,
					accion);

			// auditoria para cen_nocolegiado
			if (cenNoColegiadoDescripcion != null) {

				insertaCenHistorico(cenNocolegiadoPosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_COLEGIALES, cenNoColegiadoDescripcion, request,
						motivo);
			}

			// auditoria para cen_cliente
			cenClienteDescripcion = getDescripcionCliente(cenClienteAnterior, cenClientePosterior, accion);

			if (cenClienteDescripcion != null) {

				insertaCenHistorico(cenClientePosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, cenClienteDescripcion, request,
						motivo);
			}

			// si se añadieron nuevos grupos o se borraron
			// boolean mismosGrupos = false;
			// int contador = 0;
			// // comprueba que la actualización no contiene los mismos grupos
			// if (!gruposPerJuridicaNuevos.isEmpty() &&
			// !gruposPerJuridicaAntiguos.isEmpty()) {
			// if (gruposPerJuridicaNuevos.size() == gruposPerJuridicaAntiguos.size()) {
			// for (String nuevoGrupo : gruposPerJuridicaNuevos) {
			// if (gruposPerJuridicaAntiguos.contains(nuevoGrupo)) {
			// contador++;
			// }
			// }
			//
			// if (contador == gruposPerJuridicaNuevos.size())
			// mismosGrupos = true;
			// }
			// }
			// // auditoria para etiquetas
			// if (!mismosGrupos) {
			// insertaCenHistorico(cenClientePosterior.getIdpersona(),
			// SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES,
			// getDescripcionGrupos(gruposPerJuridicaAntiguos, gruposPerJuridicaNuevos,
			// accion, request),
			// request, motivo);
			// }

			// auditoria para etiquetas
			if (cambioEtiquetas) {
				insertaCenHistorico(cenClientePosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, getDescripcionGruposColegiado(
								gruposPerJuridicaAntiguos, gruposPerJuridicaNuevos, accion, request),
						request, motivo);
			}

			break;
		case "INSERT":
			// auditoria para cen_persona
			insertaCenHistorico(cenPersonaPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES,
					getDescripcionCenPersona(cenPersonaAnterior, cenPersonaPosterior, accion), request, motivo);
			// auditoria para cen_nocolegiado
			insertaCenHistorico(cenNocolegiadoPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_COLEGIALES,
					getDescripcionCenNoColegiado(cenNocolegiadoAnterior, cenNocolegiadoPosterior, accion), request,
					motivo);
			// auditoria para cen_cliente
			insertaCenHistorico(cenClientePosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES,
					getDescripcionCliente(cenClienteAnterior, cenClientePosterior, accion), request, motivo);
			// auditoria para etiquetas
			if (null != gruposPerJuridicaNuevos && !gruposPerJuridicaNuevos.isEmpty()) {
				insertaCenHistorico(cenClientePosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, getDescripcionGruposColegiado(
								gruposPerJuridicaAntiguos, gruposPerJuridicaNuevos, accion, request),
						request, motivo);
			}

			break;
		case "DELETE":
			// para pasar de nocolegiado -> colegiado || borrar sociedad (NO SE HACE)
			break;
		default:
			break;
		}

	}

	@Override
	public void manageAuditoriaDatosDirecciones(CenDirecciones cenDireccionesAnterior,
			CenDirecciones cenDireccionesPosterior, String accion, HttpServletRequest request, String motivo) {
		switch (accion) {
		case "UPDATE":
			insertaCenHistorico(cenDireccionesPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DIRECCIONES,
					getDescripcionCenDirecciones(cenDireccionesAnterior, cenDireccionesPosterior, accion), request,
					motivo);
			break;
		case "INSERT":
			insertaCenHistorico(cenDireccionesPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DIRECCIONES,
					getDescripcionCenDirecciones(cenDireccionesAnterior, cenDireccionesPosterior, accion), request,
					motivo);
		case "DELETE":
			// (NO SE HACE PORQUE EXISTE HISTÓRICO)
			break;
		default:
			break;
		}
	}

	@Override
	public void manageAuditoriaDatosCuentasBancarias(CenCuentasbancarias cenCuentasbancariasAnterior,
			CenCuentasbancarias cenCuentasbancariasPosterior, String accion, HttpServletRequest request,
			String motivo) {
		switch (accion) {
		case "UPDATE":
			insertaCenHistorico(cenCuentasbancariasPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_CUENTAS_BANCARIAS, getDescripcionCenCuentasBancarias(
							cenCuentasbancariasAnterior, cenCuentasbancariasPosterior, accion),
					request, motivo);
			break;
		case "INSERT":
			insertaCenHistorico(cenCuentasbancariasPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_CUENTAS_BANCARIAS, getDescripcionCenCuentasBancarias(
							cenCuentasbancariasAnterior, cenCuentasbancariasPosterior, accion),
					request, motivo);
		case "DELETE":
			// (NO SE HACE PORQUE EXISTE HISTÓRICO)
			break;
		default:
			break;
		}
	}
	
	@Override
	public void manageAuditoriaDatosCv(CenDatoscv cenDatoscvAnterior,
			CenDatoscv cenDatoscvPosterior, String accion, HttpServletRequest request,
			String motivo) {
		switch (accion) {
		case "UPDATE":
			insertaCenHistorico(cenDatoscvPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_CV, getDescripcionCenDatoscv(
							cenDatoscvAnterior, cenDatoscvPosterior, accion),
					request, motivo);
			break;
		case "INSERT":
			insertaCenHistorico(cenDatoscvPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_CV, getDescripcionCenDatoscv(
							cenDatoscvAnterior, cenDatoscvPosterior, accion),
					request, motivo);
		case "DELETE":
			// (NO SE HACE PORQUE EXISTE HISTÓRICO)
			break;
		default:
			break;
		}
	}
	
	@Override
	public void manageAuditoriaComponentes(CenComponentes cenComponentesAnterior,
			CenComponentes cenComponentesPosterior, String accion, HttpServletRequest request,
			String motivo) {
		switch (accion) {
		case "UPDATE":
			insertaCenHistorico(cenComponentesPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_COMPONENTES, getDescripcionCenComponentes(
							cenComponentesAnterior, cenComponentesPosterior, accion),
					request, motivo);
			break;
		case "INSERT":
			insertaCenHistorico(cenComponentesPosterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_COMPONENTES, getDescripcionCenComponentes(
							cenComponentesAnterior, cenComponentesPosterior, accion),
					request, motivo);
		case "DELETE":
			// (NO SE HACE PORQUE EXISTE HISTÓRICO)
			break;
		default:
			break;
		}
	}
	
	@Override
	public void manageAuditoriaEstados(CenDatoscolegialesestado estadoAnterior, CenDatoscolegialesestado estadoPosterior,
			String accion, HttpServletRequest request,
			String motivo, CEN_TIPOCAMBIO tipoCambio, Long idPersona) {
		switch (accion) {
		case "UPDATE":
			insertaCenHistorico(idPersona,
					tipoCambio, getDescripcionEstados(estadoAnterior, estadoPosterior, accion),
					request, motivo);
			break;
		case "INSERT":
			insertaCenHistorico(idPersona,
					tipoCambio, getDescripcionEstados(estadoAnterior, estadoPosterior, accion),
					request, motivo);
		case "DELETE":
			// (NO SE HACE PORQUE EXISTE HISTÓRICO)
			break;
		default:
			break;
		}
	}

	private void addDato(StringBuffer sb, String descripcion, String valor) {
		sb.append(" - ").append(descripcion).append(": ").append(valor != null ? valor : "").append(salto);
	}

	public void addDato(StringBuffer sb, String descripcion, Integer valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	/**
	 * 
	 * @param sb
	 * @param descripcion
	 * @param date
	 */

	public void addDato(StringBuffer sb, String descripcion, Date date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		addDato(sb, descripcion, (date != null ? df.format(date) : ""));
	}

	/**
	 * 
	 * @param sb
	 * @param descripcion
	 * @param valor
	 */

	public void addDato(StringBuffer sb, String descripcion, Long valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	/**
	 * 
	 * @param sb
	 * @param descripcion
	 * @param valor
	 */

	public void addDato(StringBuffer sb, String descripcion, Short valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	// DATOS CEN_PERSONA

	private String getDescripcionCenPersona(CenPersona cenPersonaAnterior, CenPersona cenPersonaPosterior,
			String accion) {
		StringBuffer sb = new StringBuffer();
		String descripcion = null;
		boolean sameDay = false;

		switch (accion) {
		case "UPDATE":

			if (cenPersonaAnterior.getFechanacimiento() != null && cenPersonaPosterior.getFechanacimiento() != null) {
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(cenPersonaAnterior.getFechanacimiento());
				cal2.setTime(cenPersonaPosterior.getFechanacimiento());
				sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
						&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
			}

			if ((cenPersonaAnterior.getApellidos1() != null && cenPersonaPosterior.getApellidos1() != null
					&& !cenPersonaAnterior.getApellidos1().equals(cenPersonaPosterior.getApellidos1()))
					|| (cenPersonaAnterior.getApellidos1() != null && cenPersonaPosterior.getApellidos1() == null)
					|| (cenPersonaPosterior.getApellidos1() != null && cenPersonaAnterior.getApellidos1() == null)
					|| (cenPersonaAnterior.getApellidos2() != null && cenPersonaPosterior.getApellidos2() != null
							&& !cenPersonaAnterior.getApellidos2().equals(cenPersonaPosterior.getApellidos2()))
					|| (cenPersonaAnterior.getApellidos2() != null && cenPersonaPosterior.getApellidos2() == null)
					|| (cenPersonaPosterior.getApellidos2() != null && cenPersonaAnterior.getApellidos2() == null)
					|| (cenPersonaAnterior.getNifcif() != null && cenPersonaPosterior.getNifcif() != null
							&& !cenPersonaAnterior.getNifcif().equals(cenPersonaPosterior.getNifcif()))
					|| (cenPersonaAnterior.getNifcif() != null && cenPersonaPosterior.getNifcif() == null)
					|| (cenPersonaPosterior.getNifcif() != null && cenPersonaAnterior.getNifcif() == null)
					|| (cenPersonaAnterior.getNaturalde() != null && cenPersonaPosterior.getNaturalde() != null
							&& !cenPersonaAnterior.getNaturalde().equals(cenPersonaPosterior.getNaturalde()))
					|| (cenPersonaAnterior.getNaturalde() != null && cenPersonaPosterior.getNaturalde() == null)
					|| (cenPersonaPosterior.getNaturalde() != null && cenPersonaAnterior.getNaturalde() == null)
					|| (cenPersonaAnterior.getNombre() != null && cenPersonaPosterior.getNombre() != null
							&& !cenPersonaAnterior.getNombre().equals(cenPersonaPosterior.getNombre()))
					|| (cenPersonaAnterior.getNombre() != null && cenPersonaPosterior.getNombre() == null)
					|| (cenPersonaPosterior.getNombre() != null && cenPersonaAnterior.getNombre() == null)
					|| (cenPersonaAnterior.getSexo() != null && cenPersonaPosterior.getSexo() != null
							&& !cenPersonaAnterior.getSexo().equals(cenPersonaPosterior.getSexo()))
					|| !sameDay || cenPersonaAnterior.getIdestadocivil() != cenPersonaPosterior.getIdestadocivil()
					|| (cenPersonaAnterior.getFechanacimiento() != null
							&& cenPersonaPosterior.getFechanacimiento() == null)
					|| (cenPersonaPosterior.getFechanacimiento() != null
							&& cenPersonaAnterior.getFechanacimiento() == null)) {

				getDescripcionCenPersona(sb, cenPersonaAnterior, "REGISTRO ANTERIOR");
				getDescripcionCenPersona(sb, cenPersonaPosterior, "REGISTRO ACTUAL");

				descripcion = sb.toString();

			} else {
				descripcion = null;
			}

			break;
		case "INSERT":
			getDescripcionCenPersona(sb, cenPersonaPosterior, "REGISTRO NUEVO");
			descripcion = sb.toString();

			break;
		case "DELETE":
			getDescripcionCenPersona(sb, cenPersonaPosterior, "REGISTRO ELIMINADO");
			descripcion = sb.toString();

			break;
		default:
			break;
		}

		return descripcion;

	}

	private String getDescripcionCenPersona(StringBuffer sb, CenPersona cenPersona, String cabecera) {

		if (cenPersona != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Idpersona", cenPersona.getIdpersona());
			addDato(sb, "Nombre", cenPersona.getNombre());
			addDato(sb, "Apellidos1", cenPersona.getApellidos1());
			addDato(sb, "Apellidos2", cenPersona.getApellidos2());
			addDato(sb, "Nifcif", cenPersona.getNifcif());
			addDato(sb, "Fechamodificacion", cenPersona.getFechamodificacion());
			addDato(sb, "Usumodificacion", cenPersona.getUsumodificacion());
			addDato(sb, "Idtipoidentificacion", cenPersona.getIdtipoidentificacion());
			addDato(sb, "Fechanacimiento", cenPersona.getFechanacimiento());
			addDato(sb, "Idestadocivil", cenPersona.getIdestadocivil());
			addDato(sb, "Naturalde", cenPersona.getNaturalde());
			addDato(sb, "Fallecido", cenPersona.getFallecido());
			addDato(sb, "Sexo", cenPersona.getSexo());
		}

		return sb.toString();

	}

	// DATOS CEN_NOCOLEGIADO

	private String getDescripcionCenNoColegiado(CenNocolegiado cenNoColegiadoAnterior,
			CenNocolegiado cenNoColegiadoPosterior, String accion) {
		StringBuffer sb = new StringBuffer();
		String descripcion = null;
		
		switch (accion) {
		case "UPDATE":

			if ((cenNoColegiadoAnterior.getAnotaciones() != null && cenNoColegiadoPosterior.getAnotaciones() != null
					&& !cenNoColegiadoAnterior.getAnotaciones().equals(cenNoColegiadoPosterior.getAnotaciones()))
					|| (cenNoColegiadoAnterior.getAnotaciones() != null && cenNoColegiadoPosterior.getAnotaciones() == null)
					|| (cenNoColegiadoPosterior.getAnotaciones() != null && cenNoColegiadoAnterior.getAnotaciones() == null)) {

				getDescripcionNocolegiado(sb, cenNoColegiadoAnterior, "REGISTRO ANTERIOR");
				getDescripcionNocolegiado(sb, cenNoColegiadoPosterior, "REGISTRO ACTUAL");
				
				descripcion = sb.toString();
			}else {
				descripcion = null;
			}
			
			break;
		case "INSERT":
			getDescripcionNocolegiado(sb, cenNoColegiadoPosterior, "REGISTRO NUEVO");
			descripcion = sb.toString();
			break;
		case "DELETE":
			getDescripcionNocolegiado(sb, cenNoColegiadoPosterior, "REGISTRO ELIMINADO");
			descripcion = sb.toString();
			break;
		default:
			break;
		}

		return descripcion;
	}

	private void getDescripcionNocolegiado(StringBuffer sb, CenNocolegiado cenNocolegiado, String cabecera) {
		if (cenNocolegiado != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Idpersona", cenNocolegiado.getIdpersona());
			addDato(sb, "Idinstitucion", cenNocolegiado.getIdinstitucion());
			addDato(sb, "Fechamodificacion", cenNocolegiado.getFechamodificacion());
			addDato(sb, "Usumodificacion", cenNocolegiado.getUsumodificacion());
			addDato(sb, "Serie", cenNocolegiado.getSerie());
			addDato(sb, "Numeroref", cenNocolegiado.getNumeroref());
			addDato(sb, "Sociedadsj", cenNocolegiado.getSociedadsj());
			addDato(sb, "Tipo", cenNocolegiado.getTipo());
			addDato(sb, "Anotaciones", cenNocolegiado.getAnotaciones());
			addDato(sb, "Prefijo_numreg", cenNocolegiado.getPrefijoNumreg());
			addDato(sb, "Contador_numreg", cenNocolegiado.getContadorNumreg());
			addDato(sb, "Sufijo_numreg", cenNocolegiado.getSufijoNumreg());
			addDato(sb, "Fechafin", cenNocolegiado.getFechafin());
			addDato(sb, "Idpersonanotario", cenNocolegiado.getIdpersonanotario());
			addDato(sb, "Resena", cenNocolegiado.getResena());
			addDato(sb, "Objetosocial", cenNocolegiado.getObjetosocial());
			addDato(sb, "Sociedadprofesional", cenNocolegiado.getSociedadprofesional());
			addDato(sb, "Prefijo_numsspp", cenNocolegiado.getPrefijoNumsspp());
			addDato(sb, "Contador_numsspp", cenNocolegiado.getContadorNumsspp());
			addDato(sb, "Sufijo_numsspp", cenNocolegiado.getSufijoNumsspp());
			addDato(sb, "Nopoliza", cenNocolegiado.getNopoliza());
			addDato(sb, "Companiaseg", cenNocolegiado.getCompaniaseg());
			addDato(sb, "Identificadords", cenNocolegiado.getIdentificadords());
			addDato(sb, "Fecha_baja", cenNocolegiado.getFechaBaja());
			LOGGER.debug("Descripción de no colegiado " + sb.toString());
		}
	}

	// DATOS CEN_CLIENTE

	private String getDescripcionCliente(CenCliente cenClienteAnterior, CenCliente cenClientePosterior, String accion) {
		StringBuffer sb = new StringBuffer();
		String descripcion = null;

		switch (accion) {
		case "UPDATE":

			if ((cenClienteAnterior.getComisiones() != null && cenClientePosterior.getComisiones() != null
					&& !cenClienteAnterior.getComisiones().equals(cenClientePosterior.getComisiones()))
					|| (cenClienteAnterior.getComisiones() != null && cenClientePosterior.getComisiones() == null)
					|| (cenClienteAnterior.getComisiones() != null && cenClientePosterior.getComisiones() == null)
					|| (cenClienteAnterior.getIdtratamiento() != null && cenClientePosterior.getIdtratamiento() != null
							&& !cenClienteAnterior.getIdtratamiento().equals(cenClientePosterior.getIdtratamiento()))
					|| (cenClienteAnterior.getIdtratamiento() != null && cenClientePosterior.getIdtratamiento() == null)
					|| (cenClienteAnterior.getIdtratamiento() != null && cenClientePosterior.getIdtratamiento() == null)
					|| (cenClienteAnterior.getAsientocontable() != null
							&& cenClientePosterior.getAsientocontable() != null
							&& !cenClienteAnterior.getAsientocontable()
									.equals(cenClientePosterior.getAsientocontable()))
					|| (cenClienteAnterior.getAsientocontable() != null
							&& cenClientePosterior.getAsientocontable() == null)
					|| (cenClienteAnterior.getAsientocontable() != null
							&& cenClientePosterior.getAsientocontable() == null)) {

				getDescripcionCliente(sb, cenClienteAnterior, "REGISTRO ANTERIOR");
				getDescripcionCliente(sb, cenClientePosterior, "REGISTRO ACTUAL");

				descripcion = sb.toString();

			} else {
				descripcion = null;
			}

			break;
		case "INSERT":
			getDescripcionCliente(sb, cenClientePosterior, "REGISTRO NUEVO");
			descripcion = sb.toString();
			break;
		case "DELETE":
			getDescripcionCliente(sb, cenClientePosterior, "REGISTRO ELIMINADO");
			descripcion = sb.toString();
			break;
		default:
			break;
		}

		return descripcion;
	}

	private void getDescripcionCliente(StringBuffer sb, CenCliente cenCliente, String cabecera) {
		if (cenCliente != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Idpersona", cenCliente.getIdpersona());
			addDato(sb, "Idinstitucion", cenCliente.getIdinstitucion());
			addDato(sb, "Fechaalta", cenCliente.getFechaalta());
			addDato(sb, "Caracter", cenCliente.getCaracter());
			addDato(sb, "Publicidad", cenCliente.getPublicidad());
			addDato(sb, "Guiajudicial", cenCliente.getGuiajudicial());
			addDato(sb, "Abonosbanco", cenCliente.getAbonosbanco());
			addDato(sb, "Cargosbanco", cenCliente.getCargosbanco());
			addDato(sb, "Comisiones", cenCliente.getComisiones());
			addDato(sb, "Idtratamiento", cenCliente.getIdtratamiento());
			addDato(sb, "Fechamodificacion", cenCliente.getFechamodificacion());
			addDato(sb, "Usumodificacion", cenCliente.getUsumodificacion());
			addDato(sb, "Idlenguaje", cenCliente.getIdlenguaje());
			addDato(sb, "Fotografia", cenCliente.getFotografia());
			addDato(sb, "Asientocontable", cenCliente.getAsientocontable());
			addDato(sb, "Letrado", cenCliente.getLetrado());
			addDato(sb, "Fechacarga", cenCliente.getFechacarga());
			addDato(sb, "Fechaactualizacion", cenCliente.getFechaactualizacion());
			addDato(sb, "Fechaexportcenso", cenCliente.getFechaexportcenso());
			addDato(sb, "Noenviarrevista", cenCliente.getNoenviarrevista());
			addDato(sb, "Noaparecerredabogacia", cenCliente.getNoaparecerredabogacia());
			addDato(sb, "Exportarfoto", cenCliente.getExportarfoto());
		}
	}

	// DATOS CEN_COLEGIADO

	private String getDescripcionCenColegiado(CenColegiado cenColegiadoAnterior, CenColegiado cenColegiadoPosterior,
			String accion) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionCenColegiado(sb, cenColegiadoAnterior, "REGISTRO ANTERIOR");
			getDescripcionCenColegiado(sb, cenColegiadoPosterior, "REGISTRO ACTUAL");
			break;
		case "INSERT":
			getDescripcionCenColegiado(sb, cenColegiadoPosterior, "REGISTRO NUEVO");
			break;
		case "DELETE":
			getDescripcionCenColegiado(sb, cenColegiadoPosterior, "REGISTRO ELIMINADO");
			break;
		default:
			break;
		}

		return sb.toString();
	}

	private void getDescripcionCenColegiado(StringBuffer sb, CenColegiado cenColegiado, String cabecera) {
		if (cenColegiado != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Idpersona", cenColegiado.getIdpersona());
			addDato(sb, "Idinstitucion", cenColegiado.getIdinstitucion());
			addDato(sb, "Fechapresentacion", cenColegiado.getFechapresentacion());
			addDato(sb, "Fechaincorporacion", cenColegiado.getFechaincorporacion());
			addDato(sb, "Indtitulacion", cenColegiado.getIndtitulacion());
			addDato(sb, "Jubilacioncuota", cenColegiado.getJubilacioncuota());
			addDato(sb, "Situacionejercicio", cenColegiado.getSituacionejercicio());
			addDato(sb, "Situacionresidente", cenColegiado.getSituacionresidente());
			addDato(sb, "Situacionempresa", cenColegiado.getSituacionempresa());
			addDato(sb, "Fechamodificacion", cenColegiado.getFechamodificacion());
			addDato(sb, "Usumodificacion", cenColegiado.getUsumodificacion());
			addDato(sb, "Comunitario", cenColegiado.getComunitario());
			addDato(sb, "Ncolegiado", cenColegiado.getNcolegiado());
			addDato(sb, "Fechajura", cenColegiado.getFechajura());
			addDato(sb, "Ncomunitario", cenColegiado.getNcomunitario());
			addDato(sb, "Fechatitulacion", cenColegiado.getFechatitulacion());
			addDato(sb, "Otroscolegios", cenColegiado.getOtroscolegios());
			addDato(sb, "Fechadeontologia", cenColegiado.getFechadeontologia());
			addDato(sb, "Fechamovimiento", cenColegiado.getFechamovimiento());
			addDato(sb, "Idtiposseguro", cenColegiado.getIdtiposseguro());
			addDato(sb, "Cuentacontablesjcs", cenColegiado.getCuentacontablesjcs());
			addDato(sb, "Identificadords", cenColegiado.getIdentificadords());
			addDato(sb, "Nmutualista", cenColegiado.getNmutualista());
			addDato(sb, "Numsolicitudcolegiacion", cenColegiado.getNumsolicitudcolegiacion());

		}

	}

	private String getDescripcionGruposColegiado(List<String[]> gruposPerJuridicaAntiguos,
			List<String[]> gruposPersonaJuridica, String accion, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionGruposColegiados(sb, gruposPerJuridicaAntiguos, "REGISTRO ANTERIOR", request);
			getDescripcionGruposColegiados(sb, gruposPersonaJuridica, "REGISTRO ACTUAL", request);
			break;
		case "INSERT":
			getDescripcionGruposColegiados(sb, gruposPersonaJuridica, "REGISTRO NUEVO", request);
			break;
		case "DELETE":
			getDescripcionGruposColegiados(sb, gruposPersonaJuridica, "REGISTRO ELIMINADO", request);
			break;
		default:
			break;
		}

		return sb.toString();
	}

	private String getDescripcionGrupos(List<String> gruposPerJuridicaAntiguos, List<String> gruposPersonaJuridica,
			String accion, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionGrupos(sb, gruposPerJuridicaAntiguos, "REGISTRO ANTERIOR", request);
			getDescripcionGrupos(sb, gruposPersonaJuridica, "REGISTRO ACTUAL", request);
			break;
		case "INSERT":
			getDescripcionGrupos(sb, gruposPersonaJuridica, "REGISTRO NUEVO", request);
			break;
		case "DELETE":
			getDescripcionGrupos(sb, gruposPersonaJuridica, "REGISTRO ELIMINADO", request);
			break;
		default:
			break;
		}

		return sb.toString();
	}

	private void getDescripcionGrupos(StringBuffer sb, List<String> gruposPerJuridica, String cabecera,
			HttpServletRequest request) {
		List<StringDTO> stringDTO = new ArrayList<StringDTO>();

		if (gruposPerJuridica != null && gruposPerJuridica.size() > 0) {
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			// Conseguimos mas información del usuario logeado
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				stringDTO = cenGruposclienteExtendsMapper.selectDescripcionGrupos(gruposPerJuridica, usuario,
						String.valueOf(idInstitucion));
				// label
				sb.append(cabecera).append(salto);
				addDato(sb, "Grupos fijos", "");
				for (int i = 0; i < stringDTO.size(); i++) {
					if (i != stringDTO.size() - 1)
						sb.append(stringDTO.get(i).getValor()).append(";").append(salto);
					else
						sb.append(stringDTO.get(i).getValor()).append(salto).append(" ");
				}
			}

		} else {
			sb.append(cabecera).append(salto);
			addDato(sb, "Grupos fijos", "");
		}
	}

	private void getDescripcionGruposColegiados(StringBuffer sb, List<String[]> gruposPerJuridica, String cabecera,
			HttpServletRequest request) {
		List<StringDTO> stringDTO = new ArrayList<StringDTO>();

		if (gruposPerJuridica != null && gruposPerJuridica.size() > 0) {
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			// Conseguimos mas información del usuario logeado
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// cOMPROBAR PO RIDINSTITUCION E IDGRUPO
				stringDTO = cenGruposclienteExtendsMapper.selectDescripcionGruposColegiados(gruposPerJuridica, usuario,
						String.valueOf(idInstitucion));
				// label
				sb.append(cabecera).append(salto);
				addDato(sb, "Grupos fijos", "");
				for (int i = 0; i < stringDTO.size(); i++) {
					if (i != stringDTO.size() - 1)
						sb.append(stringDTO.get(i).getValor()).append(";").append(salto);
					else
						sb.append(stringDTO.get(i).getValor()).append(salto).append(" ");
				}
			}

		} else {
			sb.append(cabecera).append(salto);
			addDato(sb, "Grupos fijos", "");
		}
	}

	// DATOS CEN_DIRECCIONES

	private String getDescripcionCenDirecciones(CenDirecciones cenDireccionesAnterior,
			CenDirecciones cenDireccionesPosterior, String accion) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionCenDirecciones(sb, cenDireccionesAnterior, "REGISTRO ANTERIOR");
			getDescripcionCenDirecciones(sb, cenDireccionesPosterior, "REGISTRO ACTUAL");
			break;
		case "INSERT":
			getDescripcionCenDirecciones(sb, cenDireccionesPosterior, "REGISTRO NUEVO");
			break;
		case "DELETE":
			getDescripcionCenDirecciones(sb, cenDireccionesPosterior, "REGISTRO ELIMINADO"); // no necesario
			break;
		default:
			break;
		}

		return sb.toString();
	}

	private void getDescripcionCenDirecciones(StringBuffer sb, CenDirecciones cenDirecciones, String cabecera) {
		if (cenDirecciones != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Idinstitucion", cenDirecciones.getIdinstitucion());
			addDato(sb, "Idpersona", cenDirecciones.getIdpersona());
			addDato(sb, "Iddireccion", cenDirecciones.getIddireccion());
			addDato(sb, "Fechamodificacion", cenDirecciones.getFechamodificacion());
			addDato(sb, "Usumodificacion", cenDirecciones.getUsumodificacion());
			addDato(sb, "Preferente", cenDirecciones.getPreferente());
			addDato(sb, "Domicilio", cenDirecciones.getDomicilio());
			addDato(sb, "Codigopostal", cenDirecciones.getCodigopostal());
			addDato(sb, "Telefono1", cenDirecciones.getTelefono1());
			addDato(sb, "Telefono2", cenDirecciones.getTelefono2());
			addDato(sb, "Movil", cenDirecciones.getMovil());
			addDato(sb, "Fax1", cenDirecciones.getFax1());
			addDato(sb, "Fax2", cenDirecciones.getFax2());
			addDato(sb, "Correoelectronico", cenDirecciones.getCorreoelectronico());
			addDato(sb, "Paginaweb", cenDirecciones.getPaginaweb());
			addDato(sb, "Fechabaja", cenDirecciones.getFechabaja());
			addDato(sb, "IdPais", cenDirecciones.getIdpais());
			addDato(sb, "Provincia", cenDirecciones.getIdprovincia());
			addDato(sb, "Poblacion", cenDirecciones.getIdpoblacion());
			addDato(sb, "Fechacarga", cenDirecciones.getFechacarga());
			addDato(sb, "Idinstitucionalta", cenDirecciones.getIddireccionalta());
			addDato(sb, "Poblacionextranjera", cenDirecciones.getPoblacionextranjera());
			addDato(sb, "Otraprovincia", cenDirecciones.getOtraprovincia());
		}

	}

	// DATOS CEN_CUENTASBANCARIAS

	private String getDescripcionCenCuentasBancarias(CenCuentasbancarias cenCuentasbancariasAnterior,
			CenCuentasbancarias cenCuentasbancariasPosterior, String accion) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionCenCuentasBancarias(sb, cenCuentasbancariasAnterior, "REGISTRO ANTERIOR");
			getDescripcionCenCuentasBancarias(sb, cenCuentasbancariasPosterior, "REGISTRO ACTUAL");
			break;
		case "INSERT":
			getDescripcionCenCuentasBancarias(sb, cenCuentasbancariasPosterior, "REGISTRO NUEVO");
			break;
		case "DELETE":
			getDescripcionCenCuentasBancarias(sb, cenCuentasbancariasPosterior, "REGISTRO ELIMINADO"); // no necesario
			break;
		default:
			break;
		}

		return sb.toString();
	}

	private void getDescripcionCenCuentasBancarias(StringBuffer sb, CenCuentasbancarias cenCuentasbancarias,
			String cabecera) {
		if (cenCuentasbancarias != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Idinstitucion", cenCuentasbancarias.getIdinstitucion());
			addDato(sb, "Idpersona", cenCuentasbancarias.getIdpersona());
			addDato(sb, "Idcuenta", cenCuentasbancarias.getIdcuenta());
			addDato(sb, "Abonocargo", cenCuentasbancarias.getAbonocargo());
			addDato(sb, "cbo_codigo", cenCuentasbancarias.getCboCodigo());
			addDato(sb, "Codigosucursal", cenCuentasbancarias.getCodigosucursal());
			addDato(sb, "Digitocontrol", cenCuentasbancarias.getDigitocontrol());
			addDato(sb, "Numerocuenta", cenCuentasbancarias.getNumerocuenta());
			addDato(sb, "Titular", cenCuentasbancarias.getTitular());
			addDato(sb, "Fechamodificacion", cenCuentasbancarias.getFechamodificacion());
			addDato(sb, "Usumodificacion", cenCuentasbancarias.getUsumodificacion());
			addDato(sb, "Abonosjcs", cenCuentasbancarias.getAbonosjcs());
			addDato(sb, "Fechabaja", cenCuentasbancarias.getFechabaja());
			addDato(sb, "Cuentacontable", cenCuentasbancarias.getCuentacontable());
			addDato(sb, "Iban", cenCuentasbancarias.getIban());
		}

	}
	
	// DATOS CEN_DATOS_CV

	private String getDescripcionCenDatoscv(CenDatoscv cenDatoscvAnterior,
			CenDatoscv cenDatoscvPosterior, String accion) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionCenDatoscv(sb, cenDatoscvAnterior, "REGISTRO ANTERIOR");
			getDescripcionCenDatoscv(sb, cenDatoscvPosterior, "REGISTRO ACTUAL");
			break;
		case "INSERT":
			getDescripcionCenDatoscv(sb, cenDatoscvPosterior, "REGISTRO NUEVO");
			break;
		case "DELETE":
			getDescripcionCenDatoscv(sb, cenDatoscvPosterior, "REGISTRO ELIMINADO"); // no necesario
			break;
		default:
			break;
		}

		return sb.toString();
	}
	
	private void getDescripcionCenDatoscv(StringBuffer sb, CenDatoscv cenDatoscv,
			String cabecera) {
		if (cenDatoscv != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Certificado", cenDatoscv.getCertificado());
			addDato(sb, "Creditos", cenDatoscv.getCreditos());
			addDato(sb, "Descripcion", cenDatoscv.getDescripcion());
			addDato(sb, "Fechainicio", cenDatoscv.getFechainicio());
			addDato(sb, "Fechafin", cenDatoscv.getFechafin());
			addDato(sb, "Idtipocv", cenDatoscv.getIdtipocv());
			addDato(sb, "Idtipocvsubtipo1", cenDatoscv.getIdtipocvsubtipo1());
			addDato(sb, "IdinstitucionSubt1", cenDatoscv.getIdinstitucionSubt1());
			addDato(sb, "Idtipocvsubtipo2", cenDatoscv.getIdtipocvsubtipo2());
			addDato(sb, "IdinstitucionSubt2", cenDatoscv.getIdinstitucionSubt2());
		}

	}
	
	// DATOS CEN_COMPONENTES

	private String getDescripcionCenComponentes(CenComponentes cenComponentesAnterior,
			CenComponentes cenComponentesPosterior, String accion) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionCenComponentes(sb, cenComponentesAnterior, "REGISTRO ANTERIOR");
			getDescripcionCenComponentes(sb, cenComponentesPosterior, "REGISTRO ACTUAL");
			break;
		case "INSERT":
			getDescripcionCenComponentes(sb, cenComponentesPosterior, "REGISTRO NUEVO");
			break;
		case "DELETE":
			getDescripcionCenComponentes(sb, cenComponentesPosterior, "REGISTRO ELIMINADO"); // no necesario
			break;
		default:
			break;
		}

		return sb.toString();
	}
	
	private void getDescripcionCenComponentes(StringBuffer sb, CenComponentes cenComponentes,
			String cabecera) {
		if (cenComponentes != null) {
			sb.append(cabecera).append(salto);
			
			if (cenComponentes.getCapitalsocial() != null) {
				addDato(sb, "Capitalsocial", cenComponentes.getCapitalsocial().toString());
			} else { 
				addDato(sb, "Capitalsocial", "");
			}
			
			addDato(sb, "Idcargo", cenComponentes.getIdcargo());
			addDato(sb, "Descripcion cargo", cenComponentes.getCargo());
			addDato(sb, "Fechacargo", cenComponentes.getFechacargo());
			addDato(sb, "Idcomponente", cenComponentes.getIdcomponente());
			addDato(sb, "Numcolegiado", cenComponentes.getNumcolegiado());
			addDato(sb, "Persona", cenComponentes.getCenClienteIdpersona());
			addDato(sb, "Provincia", cenComponentes.getIdprovincia());
			addDato(sb, "Sociedad", cenComponentes.getSociedad());
			addDato(sb, "Tipo colegio", cenComponentes.getIdtipocolegio());
		}

	}

	private String getDescripcionEstados(CenDatoscolegialesestado estadoAnterior, CenDatoscolegialesestado estadoPosterior, String accion) {
		StringBuffer sb = new StringBuffer();

		switch (accion) {
		case "UPDATE":
			getDescripcionEstados(sb, estadoAnterior, "REGISTRO ANTERIOR");
			getDescripcionEstados(sb, estadoPosterior, "REGISTRO ACTUAL");
			break;
		case "INSERT":
			getDescripcionEstados(sb, estadoPosterior, "REGISTRO NUEVO");
			break;
		case "DELETE":
			getDescripcionEstados(sb, estadoPosterior, "REGISTRO ELIMINADO"); // no necesario
			break;
		default:
			break;
		}

		return sb.toString();
	}
	
	private void getDescripcionEstados(StringBuffer sb, CenDatoscolegialesestado estado,
			String cabecera) {
		if (estado != null) {
			sb.append(cabecera).append(salto);
			addDato(sb, "Fechaestado", estado.getFechaestado());
			addDato(sb, "Idestado", estado.getIdestado());
			addDato(sb, "Observaciones", estado.getObservaciones());
		}

	}

	@Override
	public void manageAuditoriaDatosColegiales(CenColegiado cenColegiadoAnterior, CenColegiado cenColegiadoPosterior,
			String accion, HttpServletRequest request, String motivo) {
		// TODO Auto-generated method stub
		switch (accion) {
		case "UPDATE":
			// auditoria para cen_colegiado
			insertaCenHistorico(cenColegiadoAnterior.getIdpersona(),
					SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_COLEGIALES,
					getDescripcionCenColegiado(cenColegiadoAnterior, cenColegiadoPosterior, accion), request, motivo);
			break;
		case "INSERT":
			break;
		case "DELETE":
			// para pasar de nocolegiado -> colegiado || borrar sociedad (NO SE HACE)
			break;
		default:
			break;
		}
	}

	@Override
	public void manageAuditoriaDatosGeneralesColegiado(List<String[]> gruposPerJuridicaNuevos,
			List<String[]> gruposPerJuridicaAntiguos, CenPersona cenPersona, CenPersona cenPersonaPosterior,
			CenColegiado cenColegiadoAnterior, CenColegiado cenColegiadoPosterior, CenCliente cenCliente,
			CenCliente cenClientePosterior, String accion, HttpServletRequest request, String motivo,
			boolean cambioEtiquetas) {
		String cenPersonaDescripcion = null;
		String cenClienteDescripcion = null;

		switch (accion) {
		case "UPDATE":
			// auditoria para cen_persona
			cenPersonaDescripcion = getDescripcionCenPersona(cenPersona, cenPersonaPosterior, accion);

			if (cenPersonaDescripcion != null) {
				insertaCenHistorico(cenPersonaPosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, cenPersonaDescripcion, request,
						motivo);
			}

			// auditoria para cen_colegiado
			// insertaCenHistorico(cenColegiadoPosterior.getIdpersona(),
			// SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_COLEGIALES,
			// getDescripcionCenColegiado(cenColegiadoAnterior, cenColegiadoPosterior,
			// accion), request, motivo);
			// auditoria para cen_cliente
			cenClienteDescripcion = getDescripcionCliente(cenCliente, cenClientePosterior, accion);

			if (cenClienteDescripcion != null) {

				insertaCenHistorico(cenClientePosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, cenClienteDescripcion, request,
						motivo);

			}

			// auditoria para etiquetas
			if (cambioEtiquetas) {
				insertaCenHistorico(cenClientePosterior.getIdpersona(),
						SigaConstants.CEN_TIPOCAMBIO.MODIFICACION_DATOS_GENERALES, getDescripcionGruposColegiado(
								gruposPerJuridicaAntiguos, gruposPerJuridicaNuevos, accion, request),
						request, motivo);
			}

			break;
		case "INSERT":

			break;
		case "DELETE":
			// para pasar de nocolegiado -> colegiado || borrar sociedad (NO SE HACE)
			break;
		default:
			break;
		}

	}

}
