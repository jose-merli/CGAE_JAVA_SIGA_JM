package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.db.mappers.ScsEstadoejgSqlProvider;

public class ScsActaSqlExtendsProvider extends ScsEstadoejgSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsActaSqlExtendsProvider.class);

	public String busquedaActas(ActasItem actasItem, Short idInstitucion) {

		String fechaReuniondesde = "0";
		String fechaReunionhasta = "0";
		String fechaResoluciondesde = "0";
		String fechaResolucionhasta = "0";

		if (actasItem.getFechareuniondesde() != null) {
			fechaReuniondesde = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFechareuniondesde());
		}
		if (actasItem.getFechareunionhasta() != null) {
			fechaReunionhasta = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFechareunionhasta());
		}
		
		LOGGER.info("*******************fechaResolucion********************" + actasItem.getFecharesolucion() + " "
				+ actasItem.getAnioacta() + " " + actasItem.getIdpresidente());
		if (actasItem.getFecharesoluciondesde() != null) {
			fechaResoluciondesde = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFecharesoluciondesde());
		}
		if (actasItem.getFecharesolucionhasta() != null) {
			fechaResolucionhasta = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFecharesolucionhasta());
		}

		SQL sql = new SQL();
		sql.SELECT(
				"ACT.IDACTA as IDACTA,ACT.IDINSTITUCION as IDINSTITUCION,ACT.ANIOACTA as ANIOACTA, ACT.ANIOACTA || '/' || ACT.NUMEROACTA as NUMEROACTA,ACT.FECHARESOLUCION as FECHARESOLUCION,"
						+ "ACT.FECHAREUNION as FECHAREUNION, ACT.IDPRESIDENTE as IDPRESIDENTE, ACT.IDSECRETARIO as IDSECRETARIO,"
						+ " f_siga_getrecurso(PRE.NOMBRE,1) AS NOMBREPRESIDENTE , f_siga_getrecurso(SEC.NOMBRE,1) AS NOMBRESECRETARIO   ");
		sql.FROM("SCS_ACTACOMISION ACT, SCS_PONENTE PRE, SCS_PONENTE SEC");
		sql.WHERE("ACT.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("PRE.IDPONENTE (+) = ACT.IDPRESIDENTE");
		sql.WHERE("PRE.IDINSTITUCION (+) = ACT.IDINSTITUCION");
		sql.WHERE("SEC.IDPONENTE (+) = ACT.IDSECRETARIO");
		sql.WHERE("SEC.IDINSTITUCION (+) = ACT.IDINSTITUCION");

		if (actasItem.getAnioacta() != null) {
			sql.WHERE("ACT.ANIOACTA='" + actasItem.getAnioacta() + "'");
		}

		if (actasItem.getNumeroacta() != null) {
			sql.WHERE("ACT.NUMEROACTA LIKE'%" + actasItem.getNumeroacta() + "%'");
		}

		if (fechaResoluciondesde != "0") {
			LOGGER.info("*******************fechaResolucion********************" + fechaResoluciondesde);
			sql.WHERE("TRUNC(FECHARESOLUCION) >= TO_DATE('" + fechaResoluciondesde + "', 'DD/MM/RRRR')");
		}
		if (fechaResolucionhasta != "0") {
			LOGGER.info("*******************fechaResolucion********************" + fechaResolucionhasta);
			sql.WHERE("TRUNC(FECHARESOLUCION) <= TO_DATE('" + fechaResolucionhasta + "', 'DD/MM/RRRR')");
		}
		if (fechaReuniondesde != "0") {
			sql.WHERE("TRUNC(FECHAREUNION) >= TO_DATE('" + fechaReuniondesde + "', 'DD/MM/RRRR')");
		}
		if (fechaReunionhasta != "0") {
			sql.WHERE("TRUNC(FECHAREUNION) <= TO_DATE('" + fechaReunionhasta + "', 'DD/MM/RRRR')");
		}

		if (actasItem.getIdpresidente() != null) {
			sql.WHERE("ACT.IDPRESIDENTE ='" + actasItem.getIdpresidente() + "'");

		}

		if (actasItem.getIdsecretario() != null) {
			sql.WHERE("ACT.IDSECRETARIO ='" + actasItem.getIdsecretario() + "'");
		}

		// ORDER BY ACT.ANIOACTA DESC, TO_NUMBER(regexp_replace(NUMEROACTA, '\\D', ''))
		// desc,NUMEROACTA desc;

		LOGGER.info("*******************busquedaActas********************" + sql.toString());
		return sql.toString();
	}

	public String comprobarNumeroActa(String anioHoy, String numeroActa, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("NUMEROACTA");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("ANIOACTA = '" + anioHoy +"'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion+"'");
		sql.WHERE("NUMEROACTA = '"+numeroActa+"'");
		LOGGER.info("*******************obtenerNumActa********************" + sql.toString());
		return sql.toString();
	}

	public String obtenerIdActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("MAX(IDACTA) as IDACTA");
		sql.FROM("(SELECT IDACTA FROM SCS_ACTACOMISION WHERE ANIOACTA = " + actasItem.getAnioacta()
				+ " AND IDINSTITUCION = " + idInstitucion + ")");

		LOGGER.info("*******************obtenerIdActa********************" + sql.toString());
		return sql.toString();
	}

	public String comprobarBorrarActas(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("IDACTA");
		sql.FROM("SCS_EJG_ACTA");
		sql.WHERE("IDACTA =" + actasItem.getIdacta());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnioacta());

		LOGGER.info("*******************comprobarBorrarActas********************" + sql.toString());
		return sql.toString();
	}

	public String borrarActas(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_ACTASCOMISION");
		sql.WHERE("IDACTA =" + actasItem.getIdacta());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnioacta());

		LOGGER.info("*******************borrarActas********************" + sql.toString());
		return sql.toString();
	}

	public String comboSufijoActa(Short idInstitucion) {

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
		sql.SELECT("COUNT (1) as contar");
		sql.FROM("SCS_PONENTE");
		sql.WHERE("IDPONENTE =" + actasItem.getIdpresidente() + " OR IDPONENTE = " + actasItem.getIdpresidente());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		LOGGER.info("*******************comprobarGuardarActaPonente********************" + sql.toString());
		return sql.toString();
	}

	public String comprobarGuardarActaSufijo(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("1 as contar");
		sql.FROM("GEN_PARAMETROS");
		sql.WHERE("PARAMETRO = 'CAJG_SUFIJO_ACTAS'");
		if (actasItem.getSufijo() != null) {
			sql.WHERE("valor = '" + actasItem.getNumeroacta() + actasItem.getSufijo() + "'");
		} else {
			sql.WHERE("valor = '" + actasItem.getNumeroacta() + "'");
		}
		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		LOGGER.info("*******************comprobarGuardarActaSufijo********************" + sql.toString());
		return sql.toString();
	}

	public String detectarEjgAsociadoActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("ANIO,NUMERO,IDINSTITUCION,IDTIPOEJG");
		sql.FROM("SCS_EJG");
		sql.WHERE("IDACTA =" + actasItem.getIdacta());
		sql.WHERE("IDINSTITUCIONACTA = " + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnioacta());

		LOGGER.info("*******************detectarEjgAsociadoActa********************" + sql.toString());
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
		sql.ORDER_BY("IDESTADOPOREJG DESC");

		LOGGER.info("*******************getEstadosEjg********************" + sql.toString());
		return sql.toString();
	}

	public String getActa(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"IDACTA, IDINSTITUCION,ANIOACTA,FECHAREUNION,FECHARESOLUCION,HORAINICIOREUNION,HORAFINREUNION,IDPRESIDENTE,IDSECRETARIO,MIEMBROSCOMISION,FECHAMODIFICACION,USUMODIFICACION,PENDIENTES,IDINTERCAMBIO,FECHAINTERCAMBIO,NUMEROACTA,OBSERVACIONES");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDACTA =" + actasItem.getIdacta());
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + actasItem.getAnioacta());

		LOGGER.info("*******************comprobarBorrarActas********************" + sql.toString());
		return sql.toString();
	}
	
	public String getNuevoNumActaComp(String idInstitucion, String anio, String sufijo) {
		SQL sql = new SQL();
		sql.SELECT(
				"NVL(MAX(to_number(regexp_replace(NUMEROACTA, '\\\\D', ''))),0)  AS NUMEROACTA");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + anio);
		sql.WHERE("NUMEROACTA LIKE '%"+sufijo+"%'");

		LOGGER.info("*******************getNuevoNumActa con Sufijo********************" + sql.toString());
		return sql.toString();
	}
	public String getNuevoNumActaAuxComp(String idInstitucion, String anio, String sufijo) {
		SQL sql = new SQL();
		sql.SELECT(
				"NVL(MAX(NUMEROACTA), 0) AS NUMEROACTA ");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + anio);
		sql.WHERE("NUMEROACTA LIKE '%"+sufijo+"%'");

		LOGGER.info("*******************getNuevoNumActaAux con Sufijo********************" + sql.toString());
		return sql.toString();
	}
	public String getNuevoNumActaSimple(String idInstitucion, String anio) {
		SQL sql = new SQL();
		sql.SELECT(
				"NVL(MAX(TO_NUMBER(NUMEROACTA )), 0) AS NUMEROACTA");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + anio);

		LOGGER.info("*******************getNuevoNumActa sin Sufijo********************" + sql.toString());
		return sql.toString();
	}
	public String getNuevoNumActaAuxSimple(String idInstitucion, String anio) {
		SQL sql = new SQL();
		sql.SELECT(
				"NVL(MAX(NUMEROACTA ), 0) AS NUMEROACTA");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("ANIOACTA =" + anio);

		LOGGER.info("*******************getNuevoNumActaAux sin Sufijo********************" + sql.toString());
		return sql.toString();
	}

}
