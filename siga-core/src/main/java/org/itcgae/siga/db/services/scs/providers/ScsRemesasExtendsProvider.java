package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.commons.utils.UtilidadesString;

public class ScsRemesasExtendsProvider {

	private Logger LOGGER = Logger.getLogger(ScsRemesasExtendsProvider.class);

	public String comboEstado(String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("IDESTADO as ID");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION, " + idLenguaje + ") as DESCRIPCION");
		sql.FROM("CAJG_TIPOESTADOREMESA");
		sql.ORDER_BY("IDESTADO");
		
		LOGGER.info(sql.toString());

		return sql.toString();
	}

	public String buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		SQL subquery0 = new SQL();
		SQL subquery = new SQL();
		SQL subquery1 = new SQL();
		SQL subquery2 = new SQL();
		SQL subquery3 = new SQL();
		SQL subquery4 = new SQL();
		SQL subquery5 = new SQL();
		SQL subquery6 = new SQL();
		SQL subquery7 = new SQL();
		SQL subquery8 = new SQL();
		SQL subquery9 = new SQL();
		SQL subquery10 = new SQL();
		SQL subquery11 = new SQL();
		SQL fechaGeneracion = new SQL();
		SQL fechaEnvio = new SQL();
		SQL fechaRecepcion = new SQL();
		SQL idestado = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		
		subquery0.SELECT("fecharemesa");
		subquery0.FROM("cajg_remesaestados est");
		subquery0.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery0.WHERE("est.idremesa = rem.idremesa");
		subquery0.WHERE("est.idestado = 0");
		subquery0.WHERE("ROWNUM = 1");
		
		subquery.SELECT("fecharemesa");
		subquery.FROM("cajg_remesaestados est");
		subquery.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery.WHERE("est.idremesa = rem.idremesa");
		subquery.WHERE("est.idestado = 1");
		subquery.WHERE("ROWNUM = 1");

		subquery1.SELECT("fecharemesa");
		subquery1.FROM("cajg_remesaestados est");
		subquery1.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery1.WHERE("est.idremesa = rem.idremesa");
		subquery1.WHERE("est.idestado = 2");
		subquery1.WHERE("ROWNUM = 1");

		subquery2.SELECT("fecharemesa");
		subquery2.FROM("cajg_remesaestados est");
		subquery2.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery2.WHERE("est.idremesa = rem.idremesa");
		subquery2.WHERE("est.idestado = 3");
		subquery2.WHERE("ROWNUM = 1");

		subquery4.SELECT("idestado");
		subquery4.FROM("cajg_remesaestados");
		subquery4.WHERE("idinstitucion = " + idInstitucion.toString());
		subquery4.ORDER_BY("fecharemesa desc");
		subquery4.ORDER_BY("idestado desc");

		subquery3.SELECT("descripcion");
		subquery3.FROM("cajg_tipoestadoremesa est");
		subquery3.FROM("(" + subquery4.toString() + ") a");
		subquery3.WHERE("est.idestado = a.idestado");
		subquery3.WHERE("rownum=1");

		subquery9.SELECT("count(distinct ejgr.idejgremesa)");
		subquery9.FROM("cajg_ejgremesa ejgr");
		subquery9.FROM("cajg_respuesta_ejgremesa resp");
		subquery9.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery9.WHERE("ejgr.idremesa = rem.idremesa");
		subquery9.WHERE("ejgr.idejgremesa = resp.idejgremesa");
		
		subquery11.SELECT("COUNT(1)");
		subquery11.FROM("CAJG_EJGREMESA ER2");
		subquery11.FROM("CAJG_REMESA REM2");
		subquery11.WHERE("ER2.IDINSTITUCION = REM2.IDINSTITUCION");
		subquery11.WHERE("ER2.IDREMESA = REM2.IDREMESA");
		subquery11.WHERE("REM2.IDTIPOREMESA = remesa1.IDTIPOREMESA");
		subquery11.WHERE("ER2.IDINSTITUCION = ejgremesa1.IDINSTITUCION");
		subquery11.WHERE("ER2.ANIO = ejgremesa1.ANIO");
		subquery11.WHERE("ER2.NUMERO = ejgremesa1.NUMERO");
		subquery11.WHERE("ER2.IDTIPOEJG = ejgremesa1.IDTIPOEJG");
		subquery11.WHERE("ER2.IDREMESA > ejgremesa1.IDREMESA ");
		
		subquery10.SELECT("COUNT(("+ subquery11.toString() + "))");
		subquery10.FROM("SCS_EJG ejg1");
		subquery10.FROM("SCS_GUARDIASTURNO guardia1");
		subquery10.FROM("SCS_TIPOEJG tipoejg1");
		subquery10.FROM("CEN_COLEGIADO colegiado1");
		subquery10.FROM("CAJG_EJGREMESA ejgremesa1");
		subquery10.FROM("CAJG_REMESA remesa1");
		subquery10.FROM("SCS_PERSONAJG persona1");
		subquery10.FROM("SCS_ESTADOEJG estado1");
		subquery10.FROM("SCS_MAESTROESTADOSEJG maestroestado1");
		subquery10.WHERE("ejg1.IDTIPOEJG = tipoejg1.IDTIPOEJG");
		subquery10.WHERE("ejg1.IDINSTITUCION = guardia1.IDINSTITUCION(+)");
		subquery10.WHERE("ejg1.GUARDIATURNO_IDTURNO = guardia1.IDTURNO(+)");
		subquery10.WHERE("ejg1.GUARDIATURNO_IDGUARDIA = guardia1.IDGUARDIA(+)");
		subquery10.WHERE("ejg1.IDINSTITUCION = colegiado1.IDINSTITUCION(+)");
		subquery10.WHERE("ejg1.IDPERSONA = colegiado1.IDPERSONA(+)");
		subquery10.WHERE("ejg1.idinstitucion = persona1.IDINSTITUCION (+)");
		subquery10.WHERE("ejg1.IDPERSONAJG = persona1.IDPERSONA(+)");
		subquery10.WHERE("estado1.idestadoejg = maestroestado1.idestadoejg (+)");
		subquery10.WHERE("estado1.idinstitucion = ejg1.idinstitucion");
		subquery10.WHERE("estado1.idtipoejg = ejg1.idtipoejg");
		subquery10.WHERE("estado1.anio = ejg1.anio");
		subquery10.WHERE("estado1.numero = ejg1.numero");
		subquery10.WHERE("estado1.fechabaja IS NULL");
		subquery10.WHERE("estado1.idestadoporejg = (\r\n"
				+ "		SELECT\r\n"
				+ "			MAX(estado2.idestadoporejg)\r\n"
				+ "		FROM\r\n"
				+ "			scs_estadoejg estado2\r\n"
				+ "		WHERE\r\n"
				+ "			estado2.idinstitucion = estado1.idinstitucion\r\n"
				+ "			AND estado2.idtipoejg = estado1.idtipoejg\r\n"
				+ "			AND estado2.anio = estado1.anio\r\n"
				+ "			AND estado2.numero = estado1.numero\r\n"
				+ "                    )");
		subquery10.WHERE("ejg1.idinstitucion = ejgremesa1.idinstitucion");
		subquery10.WHERE("ejg1.anio = ejgremesa1.anio");
		subquery10.WHERE("ejg1.numero = ejgremesa1.numero");
		subquery10.WHERE("ejg1.idtipoejg = ejgremesa1.idtipoejg");
		subquery10.WHERE("ejgremesa1.idremesa = remesa1.idremesa");
		subquery10.WHERE("ejgremesa1.idinstitucion = remesa1.idinstitucion");
		subquery10.WHERE("ejgremesa1.idremesa = rem.IDREMESA ");
		subquery10.WHERE("ejgremesa1.idinstitucion = rem.IDINSTITUCION");
		subquery10.WHERE("(SELECT\r\n"
				+ "					COUNT(1)\r\n"
				+ "				FROM\r\n"
				+ "					CAJG_EJGREMESA ER2,\r\n"
				+ "					CAJG_REMESA REM2\r\n"
				+ "				WHERE\r\n"
				+ "					(ER2.IDINSTITUCION = REM2.IDINSTITUCION\r\n"
				+ "						AND ER2.IDREMESA = REM2.IDREMESA\r\n"
				+ "						AND REM2.IDTIPOREMESA = remesa1.IDTIPOREMESA\r\n"
				+ "						AND ER2.IDINSTITUCION = ejgremesa1.IDINSTITUCION\r\n"
				+ "						AND ER2.ANIO = ejgremesa1.ANIO\r\n"
				+ "						AND ER2.NUMERO = ejgremesa1.NUMERO\r\n"
				+ "						AND ER2.IDTIPOEJG = ejgremesa1.IDTIPOEJG\r\n"
				+ "						AND ER2.IDREMESA > ejgremesa1.IDREMESA )\r\n"
				+ "			) >0");
		subquery10.WHERE("ejgremesa1.IDEJGREMESA IN(\r\n"
				+ "			SELECT cre.IDEJGREMESA  FROM CAJG_RESPUESTA_EJGREMESA cre \r\n"
				+ "			JOIN CAJG_EJGREMESA ce ON ce.IDEJGREMESA = cre.IDEJGREMESA\r\n"
				+ "		)");
		
		
		subquery6.SELECT("count(1)");
		subquery6.FROM("cajg_ejgremesa ejgr");
		subquery6.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery6.WHERE("ejgr.idremesa = rem.idremesa");

		subquery7.SELECT("1");
		subquery7.FROM("cajg_ejgremesa ejgr");
		subquery7.WHERE("ejgr.idremesa= rem.idremesa");
		subquery7.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery7.WHERE("ejgr.numero = " + remesasBusquedaItem.getNumeroEJG());

		subquery8.SELECT("1");
		subquery8.FROM("cajg_ejgremesa ejgr");
		subquery8.WHERE("ejgr.idremesa= rem.idremesa");
		subquery8.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery8.WHERE("ejgr.anio = " + remesasBusquedaItem.getAnnioEJG());

		fechaGeneracion.SELECT("fecharemesa");
		fechaGeneracion.FROM("cajg_remesaestados est");
		fechaGeneracion.WHERE("est.idinstitucion = rem.idinstitucion");
		fechaGeneracion.WHERE("est.idremesa = rem.idremesa");
		fechaGeneracion.WHERE("est.idestado = 1");
		if (remesasBusquedaItem.getFechaGeneracionDesde() != null) {
			String fechaGeneracionDesde = "";
			fechaGeneracionDesde = dateFormat.format(remesasBusquedaItem.getFechaGeneracionDesde());
			fechaGeneracion.WHERE("TRUNC(est.fecharemesa) >= TO_DATE('" + fechaGeneracionDesde + "', 'DD/MM/RRRR')");
		}

		if (remesasBusquedaItem.getFechaGeneracionHasta() != null) {
			String fechaGeneracionHasta = "";
			fechaGeneracionHasta = dateFormat.format(remesasBusquedaItem.getFechaGeneracionHasta());
			fechaGeneracion.WHERE("TRUNC(est.fecharemesa) <= TO_DATE('" + fechaGeneracionHasta + "', 'DD/MM/RRRR')");
		}

		fechaEnvio.SELECT("fecharemesa");
		fechaEnvio.FROM("cajg_remesaestados est");
		fechaEnvio.WHERE("est.idinstitucion = rem.idinstitucion");
		fechaEnvio.WHERE("est.idremesa = rem.idremesa");
		fechaEnvio.WHERE("est.idestado = 2");
		if (remesasBusquedaItem.getFechaEnvioDesde() != null) {
			String fechaEnvioDesde = "";
			fechaEnvioDesde = dateFormat.format(remesasBusquedaItem.getFechaEnvioDesde());
			fechaEnvio.WHERE("TRUNC(est.fecharemesa) >= TO_DATE('" + fechaEnvioDesde + "', 'DD/MM/RRRR')");
		}

		if (remesasBusquedaItem.getFechaEnvioHasta() != null) {
			String fechaEnvioHasta = "";
			fechaEnvioHasta = dateFormat.format(remesasBusquedaItem.getFechaEnvioHasta());
			fechaEnvio.WHERE("TRUNC(est.fecharemesa) <= TO_DATE('" + fechaEnvioHasta + "', 'DD/MM/RRRR')");
		}

		fechaRecepcion.SELECT("fecharemesa");
		fechaRecepcion.FROM("cajg_remesaestados est");
		fechaRecepcion.WHERE("est.idinstitucion = rem.idinstitucion");
		fechaRecepcion.WHERE("est.idremesa = rem.idremesa");
		fechaRecepcion.WHERE("est.idestado = 3");
		if (remesasBusquedaItem.getFechaRecepcionDesde() != null) {
			String fechaRecepcionDesde = "";
			fechaRecepcionDesde = dateFormat.format(remesasBusquedaItem.getFechaRecepcionDesde());
			fechaRecepcion.WHERE("TRUNC(est.fecharemesa) >= TO_DATE('" + fechaRecepcionDesde + "', 'DD/MM/RRRR')");
		}

		if (remesasBusquedaItem.getFechaRecepcionHasta() != null) {
			String fechaRecepcionHasta = "";
			fechaRecepcionHasta = dateFormat.format(remesasBusquedaItem.getFechaRecepcionHasta());
			fechaRecepcion.WHERE("TRUNC(est.fecharemesa) <= TO_DATE('" + fechaRecepcionHasta + "', 'DD/MM/RRRR')");
		}

		idestado.SELECT("MAX(idestado)");
		idestado.FROM("cajg_remesaestados est2");
		idestado.WHERE("est2.idinstitucion = rem.idinstitucion");
		idestado.WHERE("est2.idremesa = rem.idremesa");
		
		sql.SELECT("REM.IDREMESA IDREMESA");
		sql.SELECT("REM.IDINSTITUCION IDINSTITUCION");
		sql.SELECT("REM.PREFIJO PREFIJO");
		sql.SELECT("rem.numero NUMERO");
		sql.SELECT("REM.SUFIJO SUFIJO");
		sql.SELECT("rem.descripcion");
		sql.SELECT("(" + subquery0.toString() + ") fecha_creacion");
		sql.SELECT("(" + subquery.toString() + ") fecha_generacion");
		sql.SELECT("(" + subquery1.toString() + ") fecha_envio");
		sql.SELECT("(" + subquery2.toString() + ") fecha_recepcion");
		sql.SELECT("F_SIGA_GETRECURSO((tipoest.descripcion), " + idLenguaje + ") estado");
		sql.SELECT("(" + subquery9.toString() + ") - " + "("+subquery10.toString() +") incidencias_ejg");
		sql.SELECT("(" + subquery6.toString() + ") total_ejg");
		sql.SELECT("(" + subquery9.toString() + ") - " + "("+subquery10.toString() +") || ' / ' || (" + subquery6.toString() + ") as INCIDENCIAS");
		sql.FROM("cajg_remesa rem");
		sql.FROM("cajg_remesaestados est");
		sql.FROM("cajg_tipoestadoremesa tipoest");

		sql.WHERE("rem.idinstitucion = " + idInstitucion.toString()); // colegio logado
		
		sql.WHERE("est.idremesa = rem.idremesa");
		
		sql.WHERE("est.idinstitucion = rem.idinstitucion");
		
		if(remesasBusquedaItem.isInformacionEconomica()) {
			sql.WHERE("rem.IDTIPOREMESA = 1");
		}else {
			sql.WHERE("(rem.IDTIPOREMESA = 0 or rem.IDTIPOREMESA is null)");
		}

		sql.WHERE("est.idestado = (" + idestado.toString() + ")");

		sql.WHERE("tipoest.idestado = est.idestado");

		if (remesasBusquedaItem.getNumero() != null) {
			sql.WHERE("rem.numero like '%" + remesasBusquedaItem.getNumero() + "%'"); // numero
		}

		if (remesasBusquedaItem.getPrefijo() != null) {
			sql.WHERE("rem.prefijo like '%" + remesasBusquedaItem.getPrefijo()  + "%'"); // prefijo
		}

		if (remesasBusquedaItem.getSufijo() != null) {
			sql.WHERE("rem.sufijo like '%" + remesasBusquedaItem.getSufijo()  + "%'"); // sufijo
		}

		if (remesasBusquedaItem.getDescripcion() != null) {
			sql.WHERE("UPPER(rem.descripcion) like '%" + remesasBusquedaItem.getDescripcion().toUpperCase() + "%'"); // descripcion
		}

		if (remesasBusquedaItem.getEstado() != null) {
			sql.WHERE("est.idestado = " + remesasBusquedaItem.getEstado()); // estado
		}
		
		if(remesasBusquedaItem.getIdRemesa() != 0) {
			sql.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa()); // idremesa
		}

		if (remesasBusquedaItem.getFechaGeneracionDesde() != null
				|| remesasBusquedaItem.getFechaGeneracionHasta() != null) {
			sql.WHERE("exists(" + fechaGeneracion + ")");
		}

		if (remesasBusquedaItem.getFechaEnvioDesde() != null || remesasBusquedaItem.getFechaEnvioHasta() != null) {
			sql.WHERE("exists(" + fechaEnvio + ")");
		}

		if (remesasBusquedaItem.getFechaRecepcionDesde() != null
				|| remesasBusquedaItem.getFechaRecepcionHasta() != null) {
			sql.WHERE("exists(" + fechaRecepcion + ")");
		}

		if (remesasBusquedaItem.getNumeroEJG() != 0) {
			sql.WHERE("exists (" + subquery7.toString() + ")"); // numero deEJG
		}

		if (remesasBusquedaItem.getAnnioEJG() != 0) {
			sql.WHERE("exists (" + subquery8.toString() + ")"); // año deEJG
		}

		sql.ORDER_BY("FECHA_CREACION DESC");
		
		if(tamMaximo != null)
		sql.FETCH_FIRST_ROWS_ONLY(tamMaximo);
		
		LOGGER.info(sql.toString());

		return sql.toString();
	}

	public String isEstadoRemesaIniciada(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion) {
		SQL sql = new SQL();
		SQL subquery = new SQL();

		subquery.SELECT("MAX(rem.fechamodificacion)");
		subquery.FROM("cajg_remesaestados rem");
		subquery.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa());
		subquery.WHERE("rem.idinstitucion = " + idInstitucion.toString());

		sql.SELECT("rem.idremesa");
		sql.SELECT("REM.IDINSTITUCION");
		sql.SELECT("REM.IDESTADO");
		sql.SELECT("REM.FECHAREMESA");
		sql.FROM("cajg_remesaestados rem");

		if (remesasBusquedaItem.getIdRemesa() != 0) {
			subquery.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa());
		}

		sql.WHERE("rem.idinstitucion = " + idInstitucion.toString());

		if (remesasBusquedaItem.isFicha()) {
			sql.WHERE("(rem.idestado = 0 OR rem.idestado = 6)");
		} else {
			sql.WHERE("rem.idestado = 0");
		}

		if (remesasBusquedaItem.getIdRemesa() != 0) {
			sql.WHERE("rem.fechamodificacion = (" + subquery.toString() + ")");
		}

		LOGGER.info(sql.toString());

		return sql.toString();
	}

	public String listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("rem.idremesa");
		sql.SELECT("REM.IDINSTITUCION");
		sql.SELECT("TO_CHAR(NVL(rem.FECHAMODIFICACION, rem.FECHAREMESA), 'dd/MM/yyyy HH24:MI:SS') FECHAMODIFICACION");
		sql.SELECT("F_SIGA_GETRECURSO(tip.descripcion, " + idLenguaje + ") estado");
		sql.SELECT("tip.idestado");
		sql.FROM("cajg_remesaestados rem");
		sql.FROM("cajg_tipoestadoremesa tip");
		sql.WHERE("tip.idestado = rem.idestado");

		if (remesasBusquedaItem.getIdRemesa() != 0) {
			sql.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa());
		}

		sql.WHERE("rem.idinstitucion = " + idInstitucion.toString());
		sql.ORDER_BY("tip.idestado asc");

		LOGGER.info(sql.toString());

		return sql.toString();
	}

	public String getMaxIdRemesa(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(idremesa)+1 as IDREMESA");
		sql.FROM("cajg_remesa");
		sql.WHERE("idinstitucion = " + idInstitucion.toString());

		LOGGER.info(sql.toString());

		return sql.toString();
	}

	public String getEJGRemesa(RemesasItem remesasItem, Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		SQL nuevaRemesa = new SQL();
		SQL estadoRemesa = new SQL();
		SQL numIncidencias = new SQL();
		SQL incidenciaAntesEnvio = new SQL();
		SQL incidenciaDespuesEnvio = new SQL();

		numIncidencias.SELECT("COUNT(1)");
		numIncidencias.FROM("CAJG_RESPUESTA_EJGREMESA ER");
		numIncidencias.WHERE("ER.IDEJGREMESA = ejgremesa.IDEJGREMESA");

		incidenciaAntesEnvio.SELECT("COUNT(1)");
		incidenciaAntesEnvio.FROM("CAJG_RESPUESTA_EJGREMESA ER");
		incidenciaAntesEnvio.WHERE("ER.IDEJGREMESA = ejgremesa.IDEJGREMESA");
		incidenciaAntesEnvio.WHERE("ER.IDTIPORESPUESTA = 1");

		incidenciaDespuesEnvio.SELECT("COUNT(1)");
		incidenciaDespuesEnvio.FROM("CAJG_RESPUESTA_EJGREMESA ER");
		incidenciaDespuesEnvio.WHERE("ER.IDEJGREMESA = ejgremesa.IDEJGREMESA");
		incidenciaDespuesEnvio.WHERE("ER.IDTIPORESPUESTA = 2");

		nuevaRemesa.SELECT("COUNT(1)");
		nuevaRemesa.FROM("CAJG_EJGREMESA ER2");
		nuevaRemesa.FROM("CAJG_REMESA REM2");
		nuevaRemesa.WHERE("ER2.IDINSTITUCION = REM2.IDINSTITUCION");
		nuevaRemesa.WHERE("ER2.IDREMESA = REM2.IDREMESA");
		nuevaRemesa.WHERE("REM2.IDTIPOREMESA = remesa.IDTIPOREMESA");
		nuevaRemesa.WHERE("ER2.IDINSTITUCION = EJGREMESA.IDINSTITUCION");
		nuevaRemesa.WHERE("ER2.ANIO = EJGREMESA.ANIO");
		nuevaRemesa.WHERE("ER2.NUMERO = EJGREMESA.NUMERO");
		nuevaRemesa.WHERE("ER2.IDTIPOEJG = EJGREMESA.IDTIPOEJG");
		nuevaRemesa.WHERE("ER2.IDREMESA > EJGREMESA.IDREMESA");

		estadoRemesa.SELECT("MAX(IDTIPORESPUESTA)");
		estadoRemesa.FROM("CAJG_RESPUESTA_EJGREMESA ER");
		estadoRemesa.WHERE("ER.IDEJGREMESA = ejgremesa.IDEJGREMESA");

		sql.SELECT("ejgremesa.idejgremesa");
		sql.SELECT("ejg.idinstitucion || '-' || ejg.idtipoejg || '-' || ejg.anio || '-' || ejg.numero IDENTIFICADOR");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.anio ANIOEJG");
		sql.SELECT("ejg.numejg NUMEROEJG");
		sql.SELECT("guardia.descripcion TURNO_GUARDIA_EJG");
		sql.SELECT("F_SIGA_GETRECURSO(maestroestado.descripcion, " + idLenguaje + ") ESTADOEJG");
		sql.SELECT("persona.nombre || ' ' || persona.apellido1 || ' ' || persona.apellido2 SOLICITANTE");
		sql.SELECT("(" + nuevaRemesa.toString() + ") NUEVAREMESA");
		sql.SELECT("DECODE( (" + estadoRemesa.toString() + "), 1, 'Incidencias validacion', "
				+ "2, 'Incidencias envio', 'Correcto') ESTADOREMESA");
		sql.SELECT("(" + numIncidencias.toString() + ") NUMERO_INCIDENCIAS");
		sql.SELECT("(" + incidenciaAntesEnvio.toString() + ") INCIDENCIAS_ANTES_ENVIO");
		sql.SELECT("(" + incidenciaDespuesEnvio.toString() + ") INCIDENCIAS_DESPUES_ENVIO");
		sql.FROM("SCS_EJG ejg");
		sql.FROM("SCS_GUARDIASTURNO guardia");
		sql.FROM("SCS_TIPOEJG tipoejg");
		sql.FROM("CEN_COLEGIADO colegiado");
		sql.FROM("CAJG_EJGREMESA ejgremesa");
		sql.FROM("CAJG_REMESA remesa");
		sql.FROM("SCS_PERSONAJG persona");
		sql.FROM("SCS_ESTADOEJG estado");
		sql.FROM("SCS_MAESTROESTADOSEJG maestroestado");
		sql.WHERE("ejg.IDTIPOEJG = tipoejg.IDTIPOEJG");
		sql.WHERE("ejg.IDINSTITUCION = guardia.IDINSTITUCION(+)");
		sql.WHERE("ejg.GUARDIATURNO_IDTURNO = guardia.IDTURNO(+)");
		sql.WHERE("ejg.GUARDIATURNO_IDGUARDIA = guardia.IDGUARDIA(+)");
		sql.WHERE("ejg.IDINSTITUCION = colegiado.IDINSTITUCION(+)");
		sql.WHERE("ejg.IDPERSONA = colegiado.IDPERSONA(+)");
		sql.WHERE("ejg.idinstitucion = persona.IDINSTITUCION (+)");
		sql.WHERE("ejg.IDPERSONAJG = persona.IDPERSONA(+)");
		sql.WHERE("estado.idestadoejg = maestroestado.idestadoejg (+)");
		sql.WHERE("estado.idinstitucion = ejg.idinstitucion");
		sql.WHERE("estado.idtipoejg = ejg.idtipoejg");
		sql.WHERE("estado.anio = ejg.anio");
		sql.WHERE("estado.numero = ejg.numero");
		sql.WHERE("estado.fechabaja IS NULL");
		sql.WHERE("estado.idestadoporejg = (\r\n"
				+ "                SELECT\r\n"
				+ "            MAX(estado2.idestadoporejg)\r\n"
				+ "        FROM\r\n"
				+ "            scs_estadoejg estado2\r\n"
				+ "        WHERE\r\n"
				+ "                estado2.idinstitucion = estado.idinstitucion\r\n"
				+ "            AND estado2.idtipoejg = estado.idtipoejg\r\n"
				+ "            AND estado2.anio = estado.anio\r\n"
				+ "            AND estado2.numero = estado.numero\r\n"
				+ "                    )");
		sql.WHERE("ejg.idinstitucion=ejgremesa.idinstitucion");
		sql.WHERE("ejg.anio=ejgremesa.anio");
		sql.WHERE("ejg.numero=ejgremesa.numero");
		sql.WHERE("ejg.idtipoejg=ejgremesa.idtipoejg");
		sql.WHERE("ejgremesa.idremesa = remesa.idremesa");
		sql.WHERE("ejgremesa.idinstitucion = remesa.idinstitucion");

		if (remesasItem.getIdRemesa() != 0) {
			sql.WHERE("ejgremesa.idremesa= " + remesasItem.getIdRemesa());
		}

		sql.WHERE("ejgremesa.idinstitucion = " + idInstitucion.toString());

		LOGGER.info(sql.toString());

		return sql.toString();
	}

	public String getAcciones(RemesasItem remesasItem, Short idInstitucion, String idlenguaje, String tipoPCAJG) {
		SQL sql = new SQL();
		int estado;

		sql.SELECT("tipaccest.idtipoaccionremesa as IDTIPOACCIONREMESA");
		sql.SELECT("F_SIGA_GETRECURSO(tipacc.DESCRIPCION, " + idlenguaje + ") as DESCRIPCION");
		sql.FROM("CAJG_TIPOACCION_ESTADO_PCAJG tipaccest");
		sql.FROM("CAJG_TIPOACCIONREMESA tipacc");
		sql.WHERE("tipacc.idtipoaccionremesa = tipaccest.idtipoaccionremesa");

		if (!UtilidadesString.esCadenaVacia(remesasItem.getEstado())) {

			switch (remesasItem.getEstado()) {
			case "Iniciada":
				estado = 0;
				break;
			case "Generada":
				estado = 1;
				break;
			case "Enviada":
				estado = 2;
				break;
			case "Recibida":
				estado = 3;
				break;
			case "Validando":
				estado = 4;
				break;
			case "Validada":
				estado = 5;
				break;
			case "Procesando remesa":
				estado = 6;
				break;
			default:
				estado = 7;
			}

			sql.WHERE("tipaccest.idestado = " + estado);
		}

		if (!UtilidadesString.esCadenaVacia(tipoPCAJG)) {
			sql.WHERE("tipaccest.tipo_pcajg = " + tipoPCAJG);
		}
		
		sql.ORDER_BY("tipaccest.idtipoaccionremesa");
		
		LOGGER.info(sql.toString());

		return sql.toString();
	}

}