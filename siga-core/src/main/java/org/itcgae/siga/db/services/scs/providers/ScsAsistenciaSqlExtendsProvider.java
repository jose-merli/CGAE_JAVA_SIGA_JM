package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;

public class ScsAsistenciaSqlExtendsProvider extends ScsAsistenciaSqlProvider {

	public String searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo, String idLenguaje) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha;

		SQL sql = new SQL();

		sql.SELECT("a.idinstitucion, a.anio, a.numero, ('A' ||a.anio || '/' || a.numero) asunto,"
				+ "(nvl(t.abreviatura,'') || '/' || nvl(g.nombre,'')) turnoguardia,"
				+ "f_siga_getrecurso(nvl(ta.descripcion,''),"+idLenguaje+") tipoasistencia,"
				+ "(nvl(pjg.nombre,'') || ' ' || nvl(pjg.apellido1,'') || ' ' || nvl(pjg.apellido2,'')) interesado,"
				+ "(nvl(per.nombre,'') || ' ' || nvl(per.apellidos1,'') || ' ' || nvl(per.apellidos2,'')) letrado,"
				+ "a.fechahora, NVL(a.numeroprocedimiento,'') numeroprocedimiento,"
				+ "a.comisaria centrodetencion,"
				+ "scs_juzgado.nombre juzgado,"
				+"a.numerodiligencia,"
				+ "nvl( a.numerodiligencia, 'Sin número' ) || ' / ' || nvl( a.numeroprocedimiento,'Sin número' ) dilnigproc");

		sql.FROM("scs_asistencia a");
	    sql.JOIN("scs_tipoasistencia ta ON (ta.idtipoasistencia = a.idtipoasistencia)");
	    sql.LEFT_OUTER_JOIN("scs_juzgado ON scs_juzgado.idjuzgado = a.juzgado AND scs_juzgado.idinstitucion = a.idinstitucion");
	    sql.LEFT_OUTER_JOIN("scs_personajg pjg ON (pjg.idpersona = a.idpersonajg AND pjg.idinstitucion = a.idinstitucion)");
	    sql.LEFT_OUTER_JOIN("scs_turno t ON (t.idturno = a.idturno AND t.idinstitucion = a.idinstitucion)");
	    sql.LEFT_OUTER_JOIN("scs_guardiasturno g ON (a.idturno = g.idturno AND a.idinstitucion = g.idinstitucion AND a.idguardia = g.idguardia)");
	    sql.LEFT_OUTER_JOIN("cen_persona per ON (a.idpersonacolegiado = per.idpersona)");

	    sql.WHERE("a.idinstitucion = "+asuntosJusticiableItem.getIdInstitucion());

	    if(asuntosJusticiableItem.getAnio() != null && !asuntosJusticiableItem.getAnio().trim().isEmpty()) {
	    	sql.WHERE("a.anio = "+asuntosJusticiableItem.getAnio());
	    }

	    if(asuntosJusticiableItem.getNumero() != null && !asuntosJusticiableItem.getNumero().trim().isEmpty()) {
	    	sql.WHERE("a.numero = "+asuntosJusticiableItem.getNumero());
	    }
	    if(asuntosJusticiableItem.getIdTipoAsistencia() != null && !asuntosJusticiableItem.getIdTipoAsistencia().trim().isEmpty()) {
	    	sql.WHERE("a.idtipoasistencia = "+asuntosJusticiableItem.getIdTipoAsistencia());
	    }

	    if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
			sql.WHERE("TO_CHAR(a.fechahora,'DD/MM/RRRR') >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
			sql.WHERE("TO_CHAR(a.fechahora,'DD/MM/RRRR') <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getNif() != null && !asuntosJusticiableItem.getNif().trim().isEmpty()) {
			sql.WHERE("pjg.nif = '%" + asuntosJusticiableItem.getNif().trim() + "%'");
		}

		if (asuntosJusticiableItem.getNombre() != null && !asuntosJusticiableItem.getNombre().trim().isEmpty()) {
			sql.WHERE("pjg.nombre LIKE upper('%" + asuntosJusticiableItem.getNombre().trim() + "%')");
		}

		if (asuntosJusticiableItem.getApellidos() != null && !asuntosJusticiableItem.getApellidos().trim().isEmpty()) {
			sql.WHERE("(pjg.apellido1 || ' ' || pjg.apellido2) LIKE upper('%"
					+ asuntosJusticiableItem.getApellidos().trim() + "%')");
		}
		if (asuntosJusticiableItem.getNumeroDiligencia() != null && !asuntosJusticiableItem.getNumeroDiligencia().trim().isEmpty()) {
			sql.WHERE("a.numerodiligencia = '"+ asuntosJusticiableItem.getNumeroDiligencia().trim()+ "'");
		}
		if (asuntosJusticiableItem.getComisaria() != null) {
			sql.WHERE("a.comisaria   = " + asuntosJusticiableItem.getComisaria());
		}
		if (asuntosJusticiableItem.getIdJuzgado() != null) {
			sql.WHERE("a.juzgado  = " + asuntosJusticiableItem.getIdJuzgado());
		}
		if (asuntosJusticiableItem.getNumeroProcedimiento() != null && !asuntosJusticiableItem.getNumeroProcedimiento().trim().isEmpty()) {
			sql.WHERE("a.numeroprocedimiento   = '" + asuntosJusticiableItem.getNumeroProcedimiento().trim() + "'");
		}

		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("a.idguardia = " + asuntosJusticiableItem.getIdGuardia());
		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("a.idpersonacolegiado = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}

		sql.WHERE("ROWNUM <= " + tamMaximo);

		sql.ORDER_BY("a.anio desc, a.numero DESC");

//		SQL sqlOrder = new SQL();
//
//		sqlOrder.SELECT("*");
//		sql.SELECT("ASISTENCIA.idinstitucion, ASISTENCIA.anio,ASISTENCIA.numero,'' as clave, '' as rol, 'A' as tipo");
//		sql.FROM("SCS_ASISTENCIA ASISTENCIA");
//		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
//		// alguno de los datos del solicitante(Nif, nombre o apellidos).
//		if (asuntosJusticiableItem.getNif() != null || asuntosJusticiableItem.getNombre() != null
//				|| asuntosJusticiableItem.getApellidos() != null) {
//			sql.INNER_JOIN(
//					"SCS_PERSONAJG PERSONA ON (ASISTENCIA.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = ASISTENCIA.IDINSTITUCION)");
//		}
//		sql.WHERE("ASISTENCIA.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());
//
//		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
//			sql.WHERE("ASISTENCIA.ANIO = " + asuntosJusticiableItem.getAnio());
//		}
//		if (asuntosJusticiableItem.getNumero() != null) {
//			sql.WHERE("ASISTENCIA.NUMERO = " + asuntosJusticiableItem.getNumero());
//		}
//		if (asuntosJusticiableItem.getIdTurno() != null) {
//			sql.WHERE("ASISTENCIA.IDTURNO = " + asuntosJusticiableItem.getIdTurno());
//		}
//		if (asuntosJusticiableItem.getIdGuardia() != null) {
//			sql.WHERE("ASISTENCIA.IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
//		}
//		if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			String fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
//			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
//		}
//		if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			String fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
//			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
//		}
//
//		if (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif() != "") {
//			if (asuntosJusticiableItem.getApellidos() != null)
//				sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
//						asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
//			if (asuntosJusticiableItem.getNombre() != null)
//				sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));
//
//		}
//		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
//			sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + asuntosJusticiableItem.getIdPersonaColegiado());
//		}
//		if (asuntosJusticiableItem.getNif() != null  && asuntosJusticiableItem.getNif() != "") {
//            sql.WHERE("upper(PERSONA.NIF) like upper('%"+asuntosJusticiableItem.getNif()+"%')");
//		}
//		if (asuntosJusticiableItem.getIdTipoAsistencia() != null) {
//			sql.WHERE("ASISTENCIA.IDTIPOASISTENCIA  = " + asuntosJusticiableItem.getIdTipoAsistencia());
//		}
//		if (asuntosJusticiableItem.getNumProcedimiento() != null && asuntosJusticiableItem.getNumProcedimiento() != "") {
//			sql.WHERE("ASISTENCIA.NUMEROPROCEDIMIENTO   = '" + asuntosJusticiableItem.getNumProcedimiento()+"'");
//		}
//		if (asuntosJusticiableItem.getNumeroDiligencia() != null && asuntosJusticiableItem.getNumeroDiligencia() != "") {
//			sql.WHERE("ASISTENCIA.NUMERODILIGENCIA   = '" + asuntosJusticiableItem.getNumeroDiligencia()+"'");
//		}
//		if (asuntosJusticiableItem.getComisaria() != null) {
//			sql.WHERE("ASISTENCIA.COMISARIA   = " + asuntosJusticiableItem.getComisaria());
//		}
//		if (asuntosJusticiableItem.getIdJuzgado() != null) {
//			sql.WHERE("ASISTENCIA.JUZGADO  = " + asuntosJusticiableItem.getIdJuzgado());
//		}
//		sqlOrder.FROM("(" + sql + " )");
//		if (tamMax != null) {
//			Integer tamMaxNumber = tamMax + 1;
//			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
//		}
//
//		return sqlOrder.toString();
		return sql.toString();
	}

	public String getAsuntoTipoAsistencia(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("ASISTENCIA.IDINSTITUCION");
		sql.SELECT("concat('A' || ASISTENCIA.anio || '/',lpad(ASISTENCIA.NUMERO,5,'0') ) as asunto");
		sql.SELECT("ASISTENCIA.FECHAHORA as fecha");
		sql.SELECT("ASISTENCIA.ANIO");
		sql.SELECT("ASISTENCIA.NUMERO");
		sql.SELECT("ASISTENCIA.codigo");
		sql.SELECT("GUARDIA.nombre as turnoguardia");
		sql.SELECT(
				"('<b>Centro de Detención</b>: ' || COMISARIA.NOMBRE || '<br/> <b>Comisaría</b>: ' || COMISARIA.NOMBRE) as datosinteres");
		sql.SELECT("ASISTENCIA.IDTURNO");
		sql.SELECT("ASISTENCIA.idpersonajg idpersonaasistido");
		sql.SELECT("ASISTENCIA.IDPERSONACOLEGIADO");

		sql.FROM("SCS_ASISTENCIA ASISTENCIA");

		sql.LEFT_OUTER_JOIN(
				"SCS_GUARDIASTURNO GUARDIA ON ASISTENCIA.IDGUARDIA = GUARDIA.IDGUARDIA AND ASISTENCIA.IDTURNO = GUARDIA.IDTURNO"
				+ " AND ASISTENCIA.IDINSTITUCION = GUARDIA.IDINSTITUCION ");
		sql.LEFT_OUTER_JOIN(
				"SCS_COMISARIA COMISARIA ON COMISARIA.IDCOMISARIA = ASISTENCIA.COMISARIA AND COMISARIA.IDINSTITUCION = ASISTENCIA.IDINSTITUCION");

		sql.WHERE("ASISTENCIA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("ASISTENCIA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("ASISTENCIA.NUMERO = '" + asuntoClave.getNumero() + "'");

		if (asuntoClave.getClave() != null) {
			sql.WHERE("ASISTENCIA.idturno = '" + asuntoClave.getClave() + "'");
		}

		return sql.toString();
	}

	public String searchAsistenciasExpress(FiltroAsistenciaItem filtroAsistenciaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("scs_asistencia.anio");
		sql.SELECT("scs_asistencia.numero");
		sql.SELECT("scs_asistencia.fechahora");
		sql.SELECT("scs_asistencia.observaciones");
		sql.SELECT("scs_asistencia.incidencias");
		sql.SELECT("scs_asistencia.ejganio");
		sql.SELECT("scs_asistencia.ejgnumero");
		sql.SELECT("scs_asistencia.fechaanulacion");
		sql.SELECT("scs_asistencia.motivosanulacion");
		sql.SELECT("scs_asistencia.contrarios");
		sql.SELECT("scs_asistencia.datosdefensajuridica");
		sql.SELECT("scs_asistencia.fechacierre");
		sql.SELECT("scs_asistencia.idtipoasistencia");
		sql.SELECT("scs_asistencia.idtipoasistenciacolegio");
		sql.SELECT("scs_asistencia.idturno");
		sql.SELECT("scs_asistencia.idguardia");
		sql.SELECT("scs_asistencia.idpersonacolegiado");
		sql.SELECT("scs_asistencia.idpersonajg");
		sql.SELECT("scs_asistencia.fechamodificacion");
		sql.SELECT("scs_asistencia.usumodificacion");
		sql.SELECT("scs_asistencia.designa_anio");
		sql.SELECT("scs_asistencia.designa_numero");
		sql.SELECT("scs_asistencia.designa_turno");
		sql.SELECT("scs_asistencia.facturado");
		sql.SELECT("scs_asistencia.pagado");
		sql.SELECT("scs_asistencia.idfacturacion");
		sql.SELECT("scs_asistencia.numerodiligencia");
		sql.SELECT("scs_asistencia.comisaria");
		sql.SELECT("scs_asistencia.comisariaidinstitucion");
		sql.SELECT("scs_asistencia.numeroprocedimiento");
		sql.SELECT("scs_asistencia.juzgado");
		sql.SELECT("scs_asistencia.juzgadoidinstitucion");
		sql.SELECT("scs_asistencia.idestadoasistencia");
		sql.SELECT("scs_asistencia.ejgidtipoejg");
		sql.SELECT("scs_asistencia.nig");
		sql.SELECT("scs_asistencia.idpretension");
		sql.SELECT("scs_asistencia.fechaestadoasistencia");
		sql.SELECT("scs_asistencia.fechasolicitud");
		sql.SELECT("scs_asistencia.idorigenasistencia");
		sql.SELECT("scs_asistencia.idmovimiento");
		sql.SELECT("to_char(fechahora, 'hh24')      hora");
		sql.SELECT("to_char(fechahora, 'mi')        minuto");
		sql.SELECT("nombre");
		sql.SELECT("apellido1");
		sql.SELECT("apellido2");
		sql.SELECT("nif");
		sql.SELECT("sexo");
		sql.SELECT("aa.fechajustificacion");
		sql.SELECT("aa.fecha fechaActuacion");
		sql.SELECT("aa.numeroasunto");
		sql.SELECT("case"
				+ "    when aa.idcomisaria is not null"
				+ "    then aa.idcomisaria"
				+ "    when aa.idjuzgado is not null"
				+ "    then aa.idjuzgado"
				+ "    else null end lugar");
		sql.SELECT("case"
				+ "    when aa.idcomisaria is not null"
				+ "    then 'C'"
				+ "    when aa.idjuzgado is not null"
				+ "    then 'J'"
				+ "    else '' end comisariaJuzgado");
//		sql.SELECT("da.iddelito");
		sql.FROM("scs_asistencia");
		sql.LEFT_OUTER_JOIN("scs_actuacionasistencia aa on aa.idinstitucion = scs_asistencia.idinstitucion AND aa.anio = scs_asistencia.anio AND aa.numero = scs_asistencia.numero");
		sql.LEFT_OUTER_JOIN("scs_personajg p on p.idpersona = scs_asistencia.idpersonajg AND p.idinstitucion = scs_asistencia.idinstitucion");
//		sql.LEFT_OUTER_JOIN("scs_delitosasistencia da on scs_asistencia.anio = da.anio AND scs_asistencia.numero = da.numero AND scs_asistencia.idinstitucion = da.idinstitucion");
		sql.WHERE("scs_asistencia.idinstitucion = " + idInstitucion);
		sql.AND();
		sql.WHERE("scs_asistencia.idturno = '" + filtroAsistenciaItem.getIdTurno() + "'");
		sql.AND();
		sql.WHERE("scs_asistencia.idguardia = '" + filtroAsistenciaItem.getIdGuardia() + "'");
		sql.AND();
		
		if (filtroAsistenciaItem.getIdLetradoManual() != null && !"".equals(filtroAsistenciaItem.getIdLetradoManual())) {
			sql.WHERE("scs_asistencia.idpersonacolegiado = '" + filtroAsistenciaItem.getIdLetradoManual() + "'");
		} else {
			sql.WHERE("scs_asistencia.idpersonacolegiado = '" + filtroAsistenciaItem.getIdLetradoGuardia() + "'");
		}
		
		sql.AND();
		sql.WHERE("trunc(scs_asistencia.fechahora) = to_date('"+filtroAsistenciaItem.getDiaGuardia() + "','dd/MM/yyyy')");
		/*sql.AND();
		sql.WHERE("EXISTS ("
				+ "        SELECT"
				+ "            1"
				+ "        FROM"
				+ "            scs_actuacionasistencia aa"
				+ "        WHERE"
				+ "                aa.idinstitucion = scs_asistencia.idinstitucion"
				+ "            AND aa.anio = scs_asistencia.anio"
				+ "            AND aa.numero = scs_asistencia.numero"
				+ "    )");*/
		
		if(filtroAsistenciaItem.getIdTipoAsistencia()!= null) {
			sql.AND();
			sql.WHERE("scs_asistencia.idtipoasistencia = '" + filtroAsistenciaItem.getIdTipoAsistencia() + "'");
		}
		if(!"".equals(filtroAsistenciaItem.getIdTipoAsistenciaColegiado())
				&& filtroAsistenciaItem.getIdTipoAsistenciaColegiado() != null) {
			sql.AND();
			sql.WHERE("scs_asistencia.idtipoasistenciacolegio = '" + filtroAsistenciaItem.getIdTipoAsistenciaColegiado() + "'");
		}
		sql.ORDER_BY("scs_asistencia.anio","scs_asistencia.numero","aa.idactuacion");

		return sql.toString();
	}
	
	public String getDelitosFromAsistencia(String anio, String numero, String institucion) {

		SQL sql = new SQL();
		sql.SELECT("da.iddelito");
		sql.FROM("scs_delitosasistencia da");
		sql.WHERE("da.anio = " + anio);
		sql.WHERE("da.numero = " + numero);
        sql.WHERE("da.idinstitucion = " + institucion);     
        return sql.toString();
	}
	
	
	public String getNextNumeroAsistencia(String anio, Short idInstitucion) {
		 SQL sql = new SQL();
		 //SELECT nvl(MAX(NUMERO)+1,1) NUMERO FROM SCS_ASISTENCIA WHERE IDINSTITUCION = 2005 AND ANIO = 2018

		 sql.SELECT("nvl(MAX(NUMERO)+1,1) NUMERO");
		 sql.FROM("SCS_ASISTENCIA");
		 sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'",
				 "ANIO = '" + anio + "'");

		 return sql.toString();
	}

	public String eliminarRelacionAsistencia(String idinstitucion, String anio, String numero) {
    	SQL sql = new SQL();

    	sql.UPDATE("SCS_ASISTENCIA");
    	sql.SET("EJGIDTIPOEJG = null");
    	sql.SET("EJGANIO = null");
    	sql.SET("EJGNUMERO = null");

    	sql.WHERE("IDINSTITUCION = " + idinstitucion);
		sql.WHERE("ANIO = " + anio);
		sql.WHERE("NUMERO = " + numero);

    	return sql.toString();
    }

	public String eliminarRelacionAsistenciaDes(String idinstitucion, String anio, String numero) {
    	SQL sql = new SQL();

    	sql.UPDATE("SCS_ASISTENCIA");
    	sql.SET("DESIGNA_TURNO = null");
    	sql.SET("DESIGNA_ANIO = null");
    	sql.SET("DESIGNA_NUMERO = null");

    	sql.WHERE("IDINSTITUCION = " + idinstitucion);
		sql.WHERE("ANIO = " + anio);
		sql.WHERE("NUMERO = " + numero);

    	return sql.toString();
    }

	public String updateAsistenciaExpress(ScsActuacionasistencia record) {
		SQL sql = new SQL();
        sql.UPDATE("SCS_ACTUACIONASISTENCIA");

        if (record.getFecha() != null) {
            sql.SET("FECHA = #{fecha,jdbcType=TIMESTAMP}");
        }

        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }

        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }

        if (record.getFechajustificacion() != null) {
            sql.SET("FECHAJUSTIFICACION = #{fechajustificacion,jdbcType=TIMESTAMP}");
        }

        if (record.getNumeroasunto() != null) {
            sql.SET("NUMEROASUNTO = #{numeroasunto,jdbcType=VARCHAR}");
        }

        if (record.getIdcomisaria() != null) {
            sql.SET("IDCOMISARIA = #{idcomisaria,jdbcType=DECIMAL}");
            sql.SET("IDINSTITUCION_COMIS = #{idinstitucionComis,jdbcType=DECIMAL}");
            sql.SET("IDINSTITUCION_JUZG = NULL");
            sql.SET("IDJUZGADO = NULL");
        }else if (record.getIdjuzgado() != null) {
            sql.SET("IDINSTITUCION_JUZG = #{idinstitucionJuzg,jdbcType=DECIMAL}");
            sql.SET("IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL}");
            sql.SET("IDCOMISARIA = NULL");
            sql.SET("IDINSTITUCION_COMIS = NULL");
        }else {
        	sql.SET("IDINSTITUCION_JUZG = NULL");
            sql.SET("IDJUZGADO = NULL");
            sql.SET("IDCOMISARIA = NULL");
            sql.SET("IDINSTITUCION_COMIS = NULL");
        }

        if (record.getIdtipoasistencia() != null) {
            sql.SET("IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}");
        }


        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("ANIO = #{anio,jdbcType=DECIMAL}");
        sql.WHERE("NUMERO = #{numero,jdbcType=DECIMAL}");
        sql.WHERE("IDACTUACION = #{idactuacion,jdbcType=DECIMAL}");

        return sql.toString();
	}

	public String searchListaContrarios(String anioNumero, Short idInstitucion, boolean mostrarHistorico){
		SQL SQL = new SQL();
		SQL.SELECT("CA.IDPERSONA",
				"P.NIF",
				"P.apellido1" +
				"            || decode(P.apellido2, NULL, '', ' ' || P.apellido2)" +
				"            || ', '" +
				"            || P.nombre AS apellidosnombre",
				"CA.NUMERO",
				"CA.ANIO",
				"CA.IDINSTITUCION",
				"CA.FECHABAJA"
		);
		SQL.FROM("SCS_CONTRARIOSASISTENCIA CA");
		SQL.INNER_JOIN("SCS_PERSONAJG P ON CA.IDPERSONA = P.IDPERSONA AND CA.IDINSTITUCION = P.IDINSTITUCION");
		SQL.WHERE("CA.ANIO='"+anioNumero.split("/")[0]+"'",
				"CA.IDINSTITUCION = '"+idInstitucion+"'",
				"CA.NUMERO = '"+anioNumero.split("/")[1]+"'");
		if(!mostrarHistorico){
			SQL.WHERE("CA.FECHABAJA IS NULL");
		}
		return SQL.toString();
	}

	public String getNumContrarios(String anioNumero, Short idInstitucion){
		SQL SQL = new SQL();

		SQL.SELECT("COUNT (*) contrarios");
		SQL.FROM("scs_contrariosasistencia ca");
		SQL.WHERE("ca.anio='"+anioNumero.split("/")[0]+"'",
				"ca.numero='"+anioNumero.split("/")[1]+"'",
				"ca.idinstitucion='"+idInstitucion+"'");
		return SQL.toString();
	}

	public String searchRelaciones(String anio, String num, Short idInstitucion, int idLenguaje, Integer tamMax){
		SQL SQL_PADRE = new SQL(); //Query que une la query de designas y la de ejgs por UNION
		SQL SQL_DESIGNAS = new SQL(); //Query que busca las designas asociadas a la asistencia
		SQL SQL_NOMBRETURNO = new SQL();
		SQL SQL_NOMBRETIPODESIGNA = new SQL();
		SQL SQL_INTERESADODESIGNA = new SQL();
		SQL SQL_EJG = new SQL(); //Query que busca los ejgs asociados a la asistencia
		SQL SQL_NOMBRETURNOEJG = new SQL();
		SQL SQL_NOMBRETIPOEJG = new SQL();
		SQL SQL_INTERESADOEJG = new SQL();

		//---
		SQL_NOMBRETURNO.SELECT("nombre");
		SQL_NOMBRETURNO.FROM("SCS_TURNO t");
		SQL_NOMBRETURNO.WHERE("t.idturno = d.idturno  AND t.idinstitucion = d.idinstitucion");
		//---
		SQL_NOMBRETIPODESIGNA.SELECT("f_siga_getrecurso(descripcion, "+idLenguaje+")");
		SQL_NOMBRETIPODESIGNA.FROM("scs_tipodesignacolegio tdc");
		SQL_NOMBRETIPODESIGNA.WHERE("tdc.idtipodesignacolegio = d.idtipodesignacolegio and tdc.idinstitucion = d.idinstitucion");
		//---
		SQL_INTERESADODESIGNA.SELECT("p.apellido1 || ' ' || p.apellido2|| ', '|| p.nombre");
		SQL_INTERESADODESIGNA.FROM("scs_personajg p",
				"scs_defendidosdesigna dd");
		SQL_INTERESADODESIGNA.WHERE("p.idpersona = dd.idpersona and dd.anio = d.anio and dd.numero = d.numero and dd.idturno = d.idturno and dd.idinstitucion = d.idinstitucion and p.idinstitucion = d.idinstitucion AND asi.idpersonajg = p.idpersona");
		//---
		SQL_DESIGNAS.SELECT(
				"TRIM('D')|| d.anio|| '/'|| TO_CHAR(d.codigo) sjcs",
				"d.idinstitucion",
				"d.anio",
				"d.numero",
				"null numejg",
				"dl.idpersona idletrado",
				"nvl(decode(nvl(c.comunitario,0),0, c.ncolegiado, c.ncomunitario), c.ncolegiado)|| ' - ' || cen_persona.apellidos1 || ' ' || cen_persona.apellidos2|| ','|| cen_persona.nombre letrado",
				"TO_CHAR(asi.idturno) idturno",
				"null idguardia",
				"TO_CHAR(d.idturno) idturnodesigna",
				"TO_CHAR(d.idtipodesignacolegio) idtipo",
				"TO_CHAR(d.codigo) codigo",
				"("+SQL_NOMBRETURNO.toString()+") desc_turno",
				"("+SQL_NOMBRETIPODESIGNA.toString()+") des_tipo",
				"("+SQL_INTERESADODESIGNA.toString()+") interesado",
				"null impugnacion",
				"null fechaimpugnacion",
				"null dictamen",
				"null fechadictamen",
				"null idTipoDictamenEjg",
				"null IDFUNDAMENTOCALIF",
				"null dictamenObs",
				"'Sin resolución' resolucion",
				"null fecharesolucion",
				"null centrodetencion",
				"d.fechaentrada fechaasunto",
				"'Sin número' || ' / ' || NVL(d.nig,'Sin número') || ' / ' || NVL(d.numprocedimiento,'Sin número') dilnigproc");
		SQL_DESIGNAS.FROM("scs_asistencia asi");
		SQL_DESIGNAS.INNER_JOIN(
				"scs_designa d on d.anio = asi.designa_anio and d.numero = asi.designa_numero and d.idinstitucion = asi.idinstitucion and d.idturno = asi.designa_turno",
				"SCS_DESIGNASLETRADO dl on d.anio = dl.anio and d.numero = dl.numero and d.idturno = dl.idturno and d.idinstitucion = dl.idinstitucion",
				"cen_persona ON cen_persona.idpersona = dl.idpersona",
				"cen_colegiado c on cen_persona.idpersona = c.idpersona and c.idinstitucion = asi.idinstitucion");
		SQL_DESIGNAS.WHERE(
				"asi.anio='"+anio+"'",
				"asi.numero = '"+num+"'",
				"asi.idinstitucion="+idInstitucion);


		//---

		SQL_NOMBRETURNOEJG.SELECT("nombre");
		SQL_NOMBRETURNOEJG.FROM("scs_turno");
		SQL_NOMBRETURNOEJG.WHERE("idturno = e.guardiaturno_idturno AND  idinstitucion = e.idinstitucion");
		//---
		SQL_NOMBRETIPOEJG.SELECT("f_siga_getrecurso( descripcion,"+idLenguaje+")");
		SQL_NOMBRETIPOEJG.FROM("scs_tipoejg");
		SQL_NOMBRETIPOEJG.WHERE("scs_tipoejg.idtipoejg = e.idtipoejg");
		//---


		SQL_EJG.SELECT(
				"TRIM('E') || e.anio || '/' || e.numejg sjcs",
				"e.idinstitucion",
				"e.anio",
				"e.numero numero",
				"e.numejg numejg",
				"e.idpersona idletrado",
				"case when e.idpersona is not null then nvl(decode(nvl(c.comunitario,0),0, c.ncolegiado, c.ncomunitario), c.ncolegiado)|| ' - ' || cen_persona.apellidos1|| ' '|| cen_persona.apellidos2|| ','|| cen_persona.nombre else '' end letrado",
				"TO_CHAR(e.guardiaturno_idturno) idturno",
				"TO_CHAR(e.guardiaturno_idguardia) idguardia",
				"TO_CHAR(a.idturno) idturnodesigna",
				"TO_CHAR(e.idtipoejg) idtipo",
				"e.numejg codigo",
				"("+SQL_NOMBRETURNOEJG.toString()+") des_turno",
				"("+SQL_NOMBRETIPOEJG.toString()+") des_tipo",
				"perjg.apellido1 ||  " +
						" CASE WHEN perjg.apellido2 is not null then " +
						" ' ' || perjg.apellido2 || ', '" +
						" ELSE" +
						" ', '" +
						" END || perjg.nombre interesado",
				"imp.descripcion impugnacion",
				"fechaauto fechaimpugnacion",
				"f_siga_getrecurso(dic.descripcion,"+idLenguaje+") dictamen",
				"fechadictamen fechadictamen",
				"e.idtipodictamenejg idTipoDictamenEjg",
				"e.IDFUNDAMENTOCALIF",
				"DBMS_LOB.substr(e.dictamen,3000) dictamenObs",
				"NVL(f_siga_getrecurso(res.descripcion,"+idLenguaje+"),'Sin resolución') resolucion",
				"e.FECHARESOLUCIONCAJG fecharesolucion",
				"null centrodetencion",
				"fechaapertura fechaasunto",
				"NVL(e.numerodiligencia,'Sin número') || ' / ' || NVL(e.nig,'Sin número') || ' / ' || NVL(e.numeroprocedimiento,'Sin número') dilnigproc");

		SQL_EJG.FROM("scs_ejg e");
		SQL_EJG.INNER_JOIN("scs_asistencia a on a.idinstitucion = e.idinstitucion AND a.ejganio = e.anio AND a.ejgnumero = e.numero AND a.ejgidtipoejg = e.idtipoejg");
		SQL_EJG.LEFT_OUTER_JOIN("cen_persona on cen_persona.idpersona = e.idpersona",
				"cen_colegiado c on cen_persona.idpersona=c.idpersona and c.idinstitucion=e.idinstitucion",
				"scs_personajg perjg on perjg.idpersona = e.idpersonajg and e.idinstitucion = perjg.idinstitucion",
				"scs_tipodictamenejg dic on e.idtipodictamenejg = dic.idtipodictamenejg and e.idinstitucion = dic.idinstitucion",
				"scs_tiporesolucion res on e.IDTIPORATIFICACIONEJG = res.idtiporesolucion",
				"scs_tiporesolauto imp ON e.idtiporesolauto = imp.idtiporesolauto");
		SQL_EJG.WHERE("a.anio = '"+anio+"' AND  a.numero = '"+num+"' AND  a.idinstitucion ="+idInstitucion);


		SQL_PADRE.SELECT(" *");
		SQL_PADRE.FROM("( " + SQL_EJG.toString() + " UNION " + SQL_DESIGNAS.toString() +" )");
		if(tamMax != null && tamMax > 0) {
			SQL_PADRE.WHERE(" ROWNUM <= " + tamMax);
		}
		SQL_PADRE.ORDER_BY( "sjcs DESC",
				"idinstitucion",
				"anio DESC",
				"codigo DESC");
		return SQL_PADRE.toString();
	}

	public String comboTipoDocumentosAsistencia (){
		SQL SQL = new SQL();

		SQL.SELECT("TDA.IDTIPODOCUMENTOASI",
				"TDA.NOMBRE");
		SQL.FROM("SCS_TIPODOCUMENTOASI TDA");
		SQL.WHERE("FECHABAJA IS NULL");

		return SQL.toString();
	}

	public String comboAsociadoAsistencia (String anio, String numero, Short idInstitucion, Integer idLenguaje){
		SQL SQL = new SQL();
		SQL.SELECT("AA.IDACTUACION AS ID",
				"AA.IDACTUACION || ' - ' || substr(F_SIGA_GETRECURSO(TA.DESCRIPCION, "+idLenguaje+"), 0, 60) AS DESCRIPCION ");
		SQL.FROM("SCS_ACTUACIONASISTENCIA AA");
		SQL.INNER_JOIN("SCS_TIPOACTUACION TA ON TA.IDINSTITUCION = AA.IDINSTITUCION \n" +
				"AND TA.IDTIPOASISTENCIA = AA.IDTIPOASISTENCIA \n" +
				"AND TA.IDTIPOACTUACION = AA.IDTIPOACTUACION ");
		SQL.WHERE("AA.IDINSTITUCION = "+ idInstitucion,
				"AA.ANIO = '"+anio+"'",
				"AA.NUMERO = '"+numero+"'");
		SQL.ORDER_BY("ID");
		return SQL.toString();
	}

	public String searchDocumentacion(String anio , String numero, Short idInstitucion, String idActuacion){
		SQL SQL = new SQL();
		SQL.SELECT("da.iddocumentacionasi",
				"da.idtipodocumento",
				"da.idfichero",
				"CASE " +
						"WHEN DA.IDACTUACION IS NULL " +
						"THEN 0 " +
						"WHEN DA.IDACTUACION IS NOT NULL " +
						"THEN DA.IDACTUACION END AS IDACTUACION",
				"da.observaciones",
				"da.nombrefichero",
				"TO_CHAR(da.fechaentrada,'DD/MM/YYYY') fechaentrada",
				"p.idpersona");
		SQL.FROM("scs_documentacionasi DA");
		SQL.LEFT_OUTER_JOIN("ADM_USUARIOS ADM ON ADM.IDUSUARIO = DA.USUMODIFICACION AND ADM.IDINSTITUCION = DA.IDINSTITUCION");
		SQL.LEFT_OUTER_JOIN("CEN_PERSONA P ON ADM.NIF = P.NIFCIF");

		SQL.WHERE("DA.ANIO = '"+anio+"' AND DA.NUMERO = '"+numero+"' AND da.idinstitucion = "+idInstitucion);
		if(!UtilidadesString.esCadenaVacia(idActuacion)){
			SQL.WHERE("DA.IDACTUACION = '"+idActuacion+"'");
		}

		return SQL.toString();
	}

	public String comboOrigenContacto (Short idInstitucion){
		SQL SQL = new SQL();

		SQL.SELECT("IDORIGENCONTACTO AS ID",
				"F_SIGA_GETRECURSO(DESCRIPCION, 1) AS DESCRIPCION");
		SQL.FROM("SCS_ORIGENCONTACTO");
		SQL.WHERE("IDINSTITUCION="+idInstitucion,
				"FECHA_BAJA IS NULL");
		SQL.ORDER_BY("IDORIGENCONTACTO");

		return SQL.toString();
	}

	public String searchActuaciones(String anio, String num, Short idInstitucion, int idLenguaje, String mostrarHistorico){
		SQL SQL = new SQL();
		SQL.SELECT("aa.idactuacion",
				"TO_CHAR(aa.fecha, 'DD/MM/YYYY') fecha",
				"aa.numeroasunto",
				"TO_CHAR(aa.fechajustificacion,'DD/MM/YYYY') fechajustificacion",
				"CASE " +
						" when aa.validada = '1'" +
						"     then 'SÍ'\n" +
						"     else 'NO'\n" +
						"    end validada",
				"aa.anulacion",
				"aa.idfacturacion",
				"aa.facturado",
				"aa.IDTIPOACTUACION ",
				"f_siga_getrecurso(ta.descripcion, "+idLenguaje+") descripcionactuacion",
				"CASE" +
						"    WHEN aa.facturado = '1'" +
						"    then decode(aa.idfacturacion, NULL, NULL, fjg.nombre" +
						"                                         || ' ('" +
						"                                         || to_char(fjg.fechadesde, 'DD/MM/YYYY')" +
						"                                         || '-'" +
						"                                         || to_char(fjg.fechahasta, 'DD/MM/YYYY')" +
						"                                         || ')')" +
						"    else null end nombrefacturacion");
		SQL.FROM("scs_actuacionasistencia  aa");
		SQL.INNER_JOIN(" scs_asistencia a on  aa.idinstitucion = a.idinstitucion AND aa.anio = a.anio AND aa.numero = a.numero",
				"  scs_tipoactuacion  ta on ta.idinstitucion = aa.idinstitucion AND ta.idtipoasistencia = aa.idtipoasistencia  AND ta.idtipoactuacion= aa.idtipoactuacion");
		SQL.LEFT_OUTER_JOIN(" fcs_facturacionjg fjg on fjg.idfacturacion = aa.idfacturacion AND fjg.idinstitucion = aa.idinstitucion");
		SQL.WHERE("aa.idinstitucion = "+idInstitucion,
				"aa.anio = '"+anio+"'",
				"aa.numero = '"+num+"'");
		SQL.AND();
		if("S".equals(mostrarHistorico)){
			SQL.WHERE("aa.anulacion = '0' or aa.anulacion is null or aa.anulacion = '1'");
		}else{
			SQL.WHERE("aa.anulacion = '0' or aa.anulacion is null");
		}
		SQL.ORDER_BY("aa.fecha desc","aa.idactuacion desc");
		return SQL.toString();
	}

	public String searchAsistencias(FiltroAsistenciaItem filtroAsistenciaItem, Short idInstitucion, Integer idLenguaje, Integer tamMax) {
		SQL SQL = new SQL();
		SQL SQL_PADRE = new SQL();
		SQL.SELECT("a.anio, a.idInstitucion");
		SQL.SELECT("a.numero");
		SQL.SELECT("TO_CHAR(a.fechahora,'DD/MM/YYYY HH24:MI') fechahora");
		SQL.SELECT("a.idguardia");
		SQL.SELECT("(SELECT g.nombre FROM scs_guardiasturno g WHERE a.idguardia = g.idguardia AND a.idinstitucion = g.idinstitucion AND a.idturno = g.idturno) nombreguardia");
		SQL.SELECT("a.idturno");
		SQL.SELECT("(SELECT t.nombre FROM scs_turno t WHERE a.idturno = t.idturno AND a.idinstitucion = t.idinstitucion ) nombreturno");
		SQL.SELECT("a.idpersonajg");
		SQL.SELECT("(SELECT pjg.apellido1 || ' ' || pjg.apellido2 || ', ' || pjg.nombre FROM scs_personajg pjg WHERE pjg.idinstitucion = a.idinstitucion AND pjg.idpersona = a.idpersonajg) asistido");
		SQL.SELECT("a.idestadoasistencia");
		SQL.SELECT("(SELECT f_siga_getrecurso(ea.descripcion, "+idLenguaje+") FROM scs_estadoasistencia ea WHERE a.idestadoasistencia = ea.idestadoasistencia) estadoasistencia");
		SQL.SELECT("a.idpersonacolegiado");
		SQL.SELECT("p.apellidos1 || ' ' || p.apellidos2 || ', ' || p.nombre letrado");
		SQL.SELECT("CASE" +
			" WHEN (" +
            	" SELECT COUNT(*) cantidad FROM scs_actuacionasistencia act" +
            	" WHERE act.idinstitucion = a.idinstitucion AND act.anio = a.anio AND act.numero = a.numero" +
            " ) = 0 THEN" +
            	" 'VACÍO'" +
            " WHEN (" +
            	" SELECT COUNT(*) cantidad FROM scs_actuacionasistencia act" +
            	" WHERE act.idinstitucion = a.idinstitucion AND act.anio = a.anio AND act.numero = a.numero AND act.validada = 0" +
            " ) > 0 THEN" +
            	" 'NO'" +
            " ELSE" +
            	" 'SI'" +
			" END actuacionesvalidadas");
		SQL.SELECT("a.IDTIPOASISTENCIACOLEGIO");
		SQL.SELECT("a.fechacierre");
		SQL.SELECT("a.fechasolicitud");
		SQL.SELECT("(SELECT g.requeridavalidacion FROM scs_guardiasturno g WHERE a.idguardia = g.idguardia AND a.idinstitucion = g.idinstitucion AND a.idturno = g.idturno) requeridavalidacion");
		SQL.FROM("scs_asistencia a");

		SQL.INNER_JOIN(
				//"scs_guardiasturno g on a.idguardia = g.idguardia and a.idinstitucion = g.idinstitucion and a.idturno = g.idturno",
				//"scs_turno t on a.idturno = t.idturno and a.idinstitucion = t.idinstitucion",
				//"scs_estadoasistencia ea on a.idestadoasistencia = ea.idestadoasistencia",
				"cen_persona p on p.idpersona = a.idpersonacolegiado");
		SQL.WHERE("a.idinstitucion = "+idInstitucion);
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getAnio())){
			SQL.WHERE("a.anio='"+filtroAsistenciaItem.getAnio()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNumero())){
			SQL.WHERE("a.numero='"+filtroAsistenciaItem.getNumero()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getFechaAsistenciaDesde())){
			SQL.WHERE("a.fechahora >= TO_DATE('"+filtroAsistenciaItem.getFechaAsistenciaDesde()+"','DD/MM/YYYY HH24:MI')");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getFechaAsistenciaHasta())){
			SQL.WHERE("a.fechahora <= TO_DATE('"+filtroAsistenciaItem.getFechaAsistenciaHasta()+"','DD/MM/YYYY HH24:MI')");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdTurno())){
			SQL.WHERE("a.idturno IN ("+filtroAsistenciaItem.getIdTurno()+")");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdGuardia())){
			SQL.WHERE("a.idguardia IN ("+filtroAsistenciaItem.getIdGuardia()+")");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdLetradoGuardia())){
			SQL.WHERE("a.idpersonacolegiado = '"+filtroAsistenciaItem.getIdLetradoGuardia()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdTipoAsistenciaColegiado())){
			SQL.WHERE("a.IDTIPOASISTENCIACOLEGIO IN ("+filtroAsistenciaItem.getIdTipoAsistenciaColegiado()+")");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdOrigenAsistencia())){
			SQL.WHERE("a.idorigenasistencia IN ("+filtroAsistenciaItem.getIdOrigenAsistencia()+")");
		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdEstadoAsistencia())){
			SQL.WHERE("a.idestadoasistencia IN ("+filtroAsistenciaItem.getIdEstadoAsistencia()+")");
		}
//		if("N".equals(filtroAsistenciaItem.getIdActuacionValidada())){
//			SQL.WHERE("UPPER(F_SIGA_ACTUACIONESASIST(a.idinstitucion,a.anio, a.numero)) = 'NO'");
//		}else if("S".equals(filtroAsistenciaItem.getIdActuacionValidada())){
//			SQL.WHERE("UPPER(F_SIGA_ACTUACIONESASIST(a.idinstitucion,a.anio, a.numero)) = 'SI'");
//		}else if("SA".equals(filtroAsistenciaItem.getIdActuacionValidada())){
//			SQL.WHERE("UPPER(F_SIGA_ACTUACIONESASIST(a.idinstitucion,a.anio, a.numero)) is null");
//		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdActuacionValidada()) && filtroAsistenciaItem.getIdActuacionValidada() != null && !filtroAsistenciaItem.getIdActuacionValidada().isEmpty()){ //Es un campo de scs_actuacionasistencia
			if (filtroAsistenciaItem.getIdActuacionValidada().contains("SA")) {
				SQL.WHERE("(UPPER(F_SIGA_ACTUACIONESASIST(a.idinstitucion,a.anio, a.numero)) IN ("+ filtroAsistenciaItem.getIdActuacionValidada() +") OR F_SIGA_ACTUACIONESASIST(a.idinstitucion,a.anio, a.numero) IS NULL)");
			}else {
				SQL.WHERE("UPPER(F_SIGA_ACTUACIONESASIST(a.idinstitucion,a.anio, a.numero)) IN ("+ filtroAsistenciaItem.getIdActuacionValidada() +")");
			}
		}
		if(UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdEstadoAsistido())){
			SQL.LEFT_OUTER_JOIN("scs_personajg pjg on pjg.idinstitucion = a.idinstitucion and pjg.idpersona = a.idpersonajg");
			if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNif())) {
				SQL.WHERE("UPPER(pjg.nif)=UPPER('" + filtroAsistenciaItem.getNif() + "')");
			}
			if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNombre())) {
				SQL.WHERE("UPPER(pjg.nombre)=UPPER('" + filtroAsistenciaItem.getNombre() + "')");
			}
			if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getApellidos())) {
				if (filtroAsistenciaItem.getApellidos().contains(" ")) {
					String[] apellidos = filtroAsistenciaItem.getApellidos().split(" ");
					if (apellidos.length == 2) {
						SQL.WHERE("UPPER(pjg.apellido1)=UPPER('" + apellidos[0] + "')");
						SQL.WHERE("UPPER(pjg.apellido2)=UPPER('" + apellidos[1] + "')");
					} else if (apellidos.length == 1) {
						SQL.WHERE("UPPER(pjg.apellido1)=UPPER('" + apellidos[0] + "')");
					}
				} else {
					SQL.WHERE("UPPER(pjg.apellido1)=UPPER('" + filtroAsistenciaItem.getApellidos() + "')");
				}
			}
		}else{

			switch (filtroAsistenciaItem.getIdEstadoAsistido()){
				case SigaConstants.JUSTICIABLE_ROL_SOLICITANTE:
					SQL.LEFT_OUTER_JOIN("scs_personajg pjg on pjg.idinstitucion = a.idinstitucion and pjg.idpersona = a.idpersonajg");
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNif())) {
						SQL.WHERE("UPPER(pjg.nif)=UPPER('" + filtroAsistenciaItem.getNif() + "')");
					}
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNombre())) {
						SQL.WHERE("UPPER(pjg.nombre)=UPPER('" + filtroAsistenciaItem.getNombre() + "')");
					}
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getApellidos())) {
						if (filtroAsistenciaItem.getApellidos().contains(" ")) {
							String[] apellidos = filtroAsistenciaItem.getApellidos().split(" ");
							if (apellidos.length == 2) {
								SQL.WHERE("UPPER(pjg.apellido1)=UPPER('" + apellidos[0] + "')");
								SQL.WHERE("UPPER(pjg.apellido2)=UPPER('" + apellidos[1] + "')");
							} else if (apellidos.length == 1) {
								SQL.WHERE("UPPER(pjg.apellido1)=UPPER('" + apellidos[0] + "')");
							}
						} else {
							SQL.WHERE("UPPER(pjg.apellido1)=UPPER('" + filtroAsistenciaItem.getApellidos() + "')");
						}
					}
					break;
				case SigaConstants.JUSTICIABLE_ROL_CONTRARIO:
					SQL.LEFT_OUTER_JOIN("scs_personajg pjg on pjg.idinstitucion = a.idinstitucion and pjg.idpersona = a.idpersonajg");
					//Buscamos un contrario de la asistencia, por lo que nos vamos a la tabla de contrarios, sacamos el id persona y volvemos a innerjoin con la tabla de justiciables filtrando por el idpersona de la tabla de contrarios
					SQL.INNER_JOIN(
							"scs_contrariosasistencia contrario on contrario.idinstitucion = a.idinstitucion and contrario.anio = a.anio and contrario.numero = a.numero");
					SQL.INNER_JOIN(
							"scs_personajg perjgcontrario on perjgcontrario.idpersona = contrario.idpersona AND perjgcontrario.IDINSTITUCION = contrario.IDINSTITUCION");
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNif())) {
						SQL.WHERE("UPPER(perjgcontrario.nif)=UPPER('" + filtroAsistenciaItem.getNif() + "')");
					}
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNombre())) {
						SQL.WHERE("UPPER(perjgcontrario.nombre)='" + filtroAsistenciaItem.getNombre() + "'");
					}
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getApellidos())) {
						if (filtroAsistenciaItem.getApellidos().contains(" ")) {
							String[] apellidos = filtroAsistenciaItem.getApellidos().split(" ");
							if (apellidos.length == 2) {
								SQL.WHERE("UPPER(perjgcontrario.apellido1)=UPPER('" + apellidos[0] + "')");
								SQL.WHERE("UPPER(perjgcontrario.apellido2)=UPPER('" + apellidos[1] + "')");
							} else if (apellidos.length == 1) {
								SQL.WHERE("UPPER(perjgcontrario.apellido1)=UPPER('" + apellidos[0] + "')");
							}
						} else {
							SQL.WHERE("UPPER(perjgcontrario.apellido1)=UPPER('" + filtroAsistenciaItem.getApellidos() + "')");
						}
					}
					break;
				case SigaConstants.JUSTICIABLE_ROL_REPRESENTANTE:
					SQL.INNER_JOIN("scs_personajg pjg on pjg.idinstitucion = a.idinstitucion and pjg.idpersona = a.idpersonajg");
					//Buscamos el representante del asistido, para ello volvemos a hacer un innerjoin con scs_personajg relacionando el idrepresentante del asistido con el idpersona del representante y filtramos
					SQL.INNER_JOIN("scs_personajg representante on pjg.idrepresentantejg = representante.idpersona and pjg.idinstitucion = representante.idinstitucion");
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNif())) {
						SQL.WHERE("UPPER(representante.nif)=UPPER('" + filtroAsistenciaItem.getNif() + "')");
					}
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNombre())) {
						SQL.WHERE("UPPER(representante.nombre)=UPPER('" + filtroAsistenciaItem.getNombre() + "')");
					}
					if (!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getApellidos())) {
						if (filtroAsistenciaItem.getApellidos().contains(" ")) {
							String[] apellidos = filtroAsistenciaItem.getApellidos().split(" ");
							if (apellidos.length == 2) {
								SQL.WHERE("UPPER(representante.apellido1)=UPPER('" + apellidos[0] + "')");
								SQL.WHERE("UPPER(representante.apellido2)=UPPER('" + apellidos[1] + "')");
							} else if (apellidos.length == 1) {
								SQL.WHERE("UPPER(representante.apellido1)=UPPER('" + apellidos[0] + "')");
							}
						} else {
							SQL.WHERE("UPPER(representante.apellido1)=UPPER('" + filtroAsistenciaItem.getApellidos() + "')");
						}
					}
					break;
				/*case SigaConstants.JUSTICIABLE_ROL_UNIDADFAMILIAR: //No hay unidad familiar en Asistencias
					break;*/
			}

		}
		if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNig())
			|| !UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNumDiligencia())
			|| !UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNumProcedimiento())
			|| !UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdProcedimiento())
			|| !UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdComisaria())
			|| !UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdJuzgado())
			|| !UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdTipoActuacion())){ //Si hay algun dato de actuaciones relleno, hacemos left outer join con scs_actuacionasistencia

			SQL.LEFT_OUTER_JOIN("scs_actuacionasistencia aa on aa.anio = a.anio and aa.numero = a.numero and a.idinstitucion = aa.idinstitucion");

			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNig())){  //Es un campo de scs_actuacionasistencia
				SQL.WHERE("aa.nig='"+filtroAsistenciaItem.getNig()+"'");
			}
			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNumDiligencia())){ //Es un campo de scs_asistencia
				SQL.WHERE("a.numerodiligencia ='"+filtroAsistenciaItem.getNumDiligencia()+"'");
			}
			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getNumProcedimiento())){ //Es un campo de scs_asistencia
				SQL.WHERE("a.NUMEROPROCEDIMIENTO ='"+filtroAsistenciaItem.getNumProcedimiento()+"'");
			}
			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdProcedimiento())){ //Es un campo de scs_asistencia
				SQL.WHERE("a.idpretension IN ("+filtroAsistenciaItem.getIdProcedimiento()+")");
			}
			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdComisaria())){ //Es un campo de scs_actuacionasistencia
				SQL.WHERE("aa.idcomisaria IN ("+filtroAsistenciaItem.getIdComisaria()+")");
			}
			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdJuzgado())){ //Es un campo de scs_actuacionasistencia
				SQL.WHERE("aa.idjuzgado IN ("+filtroAsistenciaItem.getIdJuzgado()+")");
			}
			if(!UtilidadesString.esCadenaVacia(filtroAsistenciaItem.getIdTipoActuacion()) && filtroAsistenciaItem.getIdTipoActuacion() != null && !filtroAsistenciaItem.getIdTipoActuacion().isEmpty()){ //Es un campo de scs_actuacionasistencia
				SQL.WHERE("aa.IDTIPOACTUACION IN ("+filtroAsistenciaItem.getIdTipoActuacion()+")");
			}

		}
		SQL.ORDER_BY("a.anio DESC","a.numero DESC");
		SQL_PADRE.SELECT(" *");
		SQL_PADRE.FROM("( " + SQL.toString() + " )");
		if(tamMax != null && tamMax > 0) {
			tamMax += 1;
			SQL_PADRE.WHERE(" ROWNUM <= " + tamMax);
		}


		return SQL_PADRE.toString();
	}
}
