package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.db.mappers.ScsEstadoejgSqlProvider;

public class ScsActaSqlExtendsProvider extends ScsEstadoejgSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsActaSqlExtendsProvider.class);

	public String busquedaActas(ActasItem actasItem, Short idInstitucion) {

		String fechaReunion = "0";
		String fechaResolucion = "0";

		if (actasItem.getFechaReunion() != null) {
			fechaReunion = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFechaReunion());

		}

		if (actasItem.getFechaResolucion() != null) {
			fechaResolucion = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFechaResolucion());
		}

		SQL sql = new SQL();
		sql.SELECT(
				"ACT.IDACTA as IDACTA,ACT.IDINSTITUCION as IDINSTITUCION,ACT.ANIOACTA || '/' || ACT.NUMEROACTA as NUMEROACTA,ACT.FECHARESOLUCION as FECHARESOLUCION,"
						+ "ACT.FECHAREUNION as FECHAREUNION, ACT.IDPRESIDENTE as IDPRESIDENTE, ACT.IDSECRETARIO as IDSECRETARIO,"
						+ " f_siga_getrecurso(PRE.NOMBRE,1) AS NOMBREPRESIDENTE , f_siga_getrecurso(SEC.NOMBRE,1) AS NOMBRESECRETARIO   ");
		sql.FROM("SCS_ACTACOMISION ACT, SCS_PONENTE PRE, SCS_PONENTE SEC");
		// sql.WHERE("ACT.IDINSTITUCION like '" + idInstitucion + "'");
		sql.WHERE("PRE.IDPONENTE (+) = ACT.IDPRESIDENTE");
		sql.WHERE("PRE.IDINSTITUCION (+) = ACT.IDINSTITUCION");
		sql.WHERE("SEC.IDPONENTE (+) = ACT.IDSECRETARIO");
		sql.WHERE("SEC.IDINSTITUCION (+) = ACT.IDINSTITUCION");

		if (actasItem.getAnio() != null) {
			sql.WHERE("ACT.ANIOACTA='" + actasItem.getAnio() + "'");
		}

		if (actasItem.getNumeroActa() != null) {
			sql.WHERE("ACT.NUMEROACTA LIKE'" + actasItem.getNumeroActa() + "'");
		}

		if (fechaResolucion != "0") {
			sql.WHERE("FECHARESOLUCION = '" + fechaResolucion + "'");
		}
		if (fechaReunion != "0") {
			sql.WHERE("FECHAREUNION = '" + fechaReunion + "'");
		}

		if (actasItem.getIdPresidente() != null) {
			sql.WHERE("ACT.IDPRESIDENTE ='" + actasItem.getIdPresidente() + "'");

		}

		if (actasItem.getIdSecretario() != null) {
			sql.WHERE("ACT.IDSECRETARIO ='" + actasItem.getIdSecretario() + "'");
		}

		// ORDER BY ACT.ANIOACTA DESC, TO_NUMBER(regexp_replace(NUMEROACTA, '\\D', ''))
		// desc,NUMEROACTA desc;

		LOGGER.info("*******************busquedaActas********************" + sql.toString());
		return sql.toString();
	}

	public String comprobarBorrarActas(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("IDACTA");
		sql.FROM("SCS_EJG_ACTA");
		sql.WHERE("IDACTA =" + actasItem.getIdActa());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnio());

		LOGGER.info("*******************comprobarBorrarActas********************" + sql.toString());
		return sql.toString();
	}

	public String borrarActas(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_ACTASCOMISION");
		sql.WHERE("IDACTA =" + actasItem.getIdActa());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnio());

		LOGGER.info("*******************borrarActas********************" + sql.toString());
		return sql.toString();
	}

	public String comboSufijoActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("valor");
		sql.FROM("GEN_PARAMETROS");
		sql.WHERE("PARAMETRO = 'CAJG_SUFIJO_ACTAS'");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		LOGGER.info("*******************comboSufijoActa********************" + sql.toString());
		return sql.toString();
	}

	public String comprobarGuardarActaPonente(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("1 as contar");
		sql.FROM("SCS_PONENTES");
		sql.WHERE("IDPONENTE =" + actasItem.getIdPresidente());
		sql.WHERE("IDPONENTE =" + actasItem.getIdSecretario());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		LOGGER.info("*******************comprobarGuardarActaPonente********************" + sql.toString());
		return sql.toString();
	}

	public String comprobarGuardarActaSufijo(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("1 as contar");
		sql.FROM("GEN_PARAMETROS");
		sql.WHERE("PARAMETRO = 'CAJG_SUFIJO_ACTAS'");
		sql.WHERE("valor = " + actasItem.getNumeroActa() + actasItem.getSufijo());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		LOGGER.info("*******************comprobarGuardarActaSufijo********************" + sql.toString());
		return sql.toString();
	}

	public String anadirEJGPendientesCAJG(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("1 as contar");
		sql.FROM("GEN_PARAMETROS");
		sql.WHERE("PARAMETRO = 'CAJG_SUFIJO_ACTAS'");
		sql.WHERE("valor = " + actasItem.getNumeroActa() + actasItem.getSufijo());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		LOGGER.info("*******************anadirEJGPendientesCAJG********************" + sql.toString());
		return sql.toString();
	}

	public String guardarActa(ActasItem actasItem) {

		SQL sql = new SQL();
		sql.UPDATE("SCS_ACTACOMISION");
		sql.SET("ANIOACTA = " + actasItem.getAnio());
		sql.SET("NUMEROACTA = " + actasItem.getNumeroActa() + actasItem.getSufijo());
		sql.SET("FECHAREUNION = " + actasItem.getFechaReunion());
		sql.SET("HORAINICIOREUNION = " + actasItem.getHoraInicio());
		sql.SET("HORAFINREUNION = " + actasItem.getHoraFin());
		sql.SET("FECHARESOLUCION = " + actasItem.getFechaResolucion());
		sql.SET("IDPRESIDENTE = " + actasItem.getIdPresidente());
		sql.SET("IDSECRETARIO = " + actasItem.getIdSecretario());
		sql.SET("MIEMBROSCOMISION = " + actasItem.getMiembros());
		sql.SET("OBSERVACIONES = " + actasItem.getObservaciones());
		sql.SET("PENDIENTES = " + actasItem.getPendientes());

		LOGGER.info("*******************guardarActa********************" + sql.toString());
		return sql.toString();
	}

	public String abrirActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.UPDATE("SCS_ACTACOMISION");
		sql.SET("FECHARESOLUCION = NULL");

		LOGGER.info("*******************abrirActa********************" + sql.toString());
		return sql.toString();
	}

	public String detectarEjgAsociadoActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("ANIO,NUMERO,IDINSTITUCION,IDTIPOEJG");
		sql.FROM("SCS_EJG");
		sql.WHERE("IDACTA =" + actasItem.getIdActa());
		sql.WHERE("IDINSTITUCIONACTA = " + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnio());

		LOGGER.info("*******************detectarEjgAsociadoActa********************" + sql.toString());
		return sql.toString();
	}

	public String actualizarEjg(EjgItem ejgItem) {

		SQL sql = new SQL();
		sql.UPDATE("SCS_EJG");
		sql.SET("FECHARESOLUCIONCAJG = NULL");
		LOGGER.info("*******************abrirActa********************" + sql.toString());
		return sql.toString();
	}

	public String actualizarEstadoEjg(EjgItem ejgItem, EstadoEjgItem estadoEjgItem) {

		SQL sql = new SQL();
		sql.UPDATE("SCS_ESTADOEJG");
		sql.SET("FECHABAJA =" + estadoEjgItem.getFechabaja());
		sql.WHERE("ANIO = " + ejgItem.getAnnio());
		sql.WHERE("NUMERO = " + ejgItem.getNumero());
		sql.WHERE("IDINSTITUCION = " + ejgItem.getidInstitucion());
		sql.WHERE("IDTIPOEJG = " + ejgItem.getTipoEJG());
		sql.WHERE("IDESTADOEJG=10");

		LOGGER.info("*******************actualizarEstadoEjg********************" + sql.toString());
		return sql.toString();
	}

	public String getEstadosEjg(Short idinstitucion, Short idtipoejg, Short anio, Long numero) {

		SQL sql = new SQL();
		sql.SELECT("IDESTADOPOREJG");
		sql.FROM("SCS_ESTADOEJG");
		sql.WHERE("ANIO = " + anio);
		sql.WHERE("NUMERO = " + numero);
		sql.WHERE("IDINSTITUCION = " + idinstitucion);
		sql.WHERE("IDTIPOEJG = " + idtipoejg);
		sql.WHERE("ROWNUM <=  1");
		sql.ORDER_BY("FECHAMODIFICACION DESC");
	    

		LOGGER.info("*******************getEstadosEjg********************" + sql.toString());
		return sql.toString();
	}

	public String expedientesRetirados(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("ANIO,NUMERO,IDINSTITUCION,IDTIPOEJG");
		sql.FROM("SCS_EJG");
		sql.WHERE("IDACTA =" + actasItem.getIdActa());
		sql.WHERE("IDINSTITUCIONACTA = " + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnio());
		sql.WHERE("IDTIPORATIFICACIONEJG = 4");
		sql.WHERE("IDTIPORATIFICACIONEJG = 6");

		LOGGER.info("*******************expedientesRetirados********************" + sql.toString());
		return sql.toString();
	}

	public String updatePendientes(ActasItem actasItem, String pendientes) {

		SQL sql = new SQL();
		sql.UPDATE("SCS_ACTACOMISION");
		sql.SET("PENDIENTES =" + pendientes);

		LOGGER.info("*******************abrirActa********************" + sql.toString());
		return sql.toString();
	}

	public String desvincularActa(EjgItem ejgItem, ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_EJGACTA");
		sql.WHERE("IDACTA =" + actasItem.getIdActa());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnio());
		sql.WHERE("ANIO = " + ejgItem.getAnnio());
		sql.WHERE("NUMERO = " + ejgItem.getNumero());
		sql.WHERE("IDINSTITUCION = " + ejgItem.getidInstitucion());
		sql.WHERE("IDTIPOEJG = " + ejgItem.getTipoEJG());

		LOGGER.info("*******************borrarActas********************" + sql.toString());
		return sql.toString();
	}

	public String cambiarEstadoEjg(EstadoEjgItem estadoEjgItem, String resolucion, String estado) {

		SQL sql = new SQL();
		sql.UPDATE("SCS_ESTADOEJG");
		if (resolucion == "4") {
			sql.SET("OBSERVACIONES = Expediente pendiente de la CAJG. Se retira del acta " + estado);

		} else {
			sql.SET("OBSERVACIONES = Expediente retirado del acta " + estado);
		}
		sql.WHERE("ANIO = " + estadoEjgItem.getAnio());
		sql.WHERE("NUMERO = " + estadoEjgItem.getNumero());
		sql.WHERE("IDINSTITUCION = " + estadoEjgItem.getIdinstitucion());
		sql.WHERE("IDTIPOEJG = " + estadoEjgItem.getIdtipoejg());
		LOGGER.info("*******************abrirActa********************" + sql.toString());
		return sql.toString();
	}

	public String getUltimoEstadoEjg(EjgItem ejgItem, Short idInstitucion, String resolucion) {

		SQL sql = new SQL();
		sql.SELECT("ANIO,NUMERO,IDINSTITUCION,IDTIPOEJG");
		sql.FROM("SCS_ESTADOEJG");
		sql.WHERE("ANIO = " + ejgItem.getAnnio());
		sql.WHERE("NUMERO = " + ejgItem.getNumero());
		sql.WHERE("IDINSTITUCION = " + ejgItem.getidInstitucion());
		sql.WHERE("IDTIPOEJG = " + ejgItem.getTipoEJG());
		if (resolucion == "4") {
			sql.WHERE("IDESTADOPOREJG = 9 ");

		} else {
			sql.WHERE("IDESTADOPOREJG = 21");
		}
		sql.ORDER_BY("FECHAMODIFICACION");
		LOGGER.info("*******************getUltimoEstadoEjg********************" + sql.toString());
		return sql.toString();
	}

	public String getActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"IDACTA, IDINSTITUCION,ANIOACTA,FECHAREUNION,FECHARESOLUCION,HORAINICIOREUNION,HORAFINREUNION,IDPRESIDENTE,IDSECRETARIO,MIEMBROSCOMISION,FECHAMODIFICACION,USUMODIFICACION,PENDIENTES,IDINTERCAMBIO,FECHAINTERCAMBIO,NUMEROACTA,OBSERVACIONES");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDACTA =" + actasItem.getIdActa());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnio());

		LOGGER.info("*******************comprobarBorrarActas********************" + sql.toString());
		return sql.toString();
	}

}
