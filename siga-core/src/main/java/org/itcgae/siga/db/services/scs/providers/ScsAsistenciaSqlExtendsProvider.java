package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;

public class ScsAsistenciaSqlExtendsProvider extends ScsAsistenciaSqlProvider {

	public String searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax, String idLenguaje) {
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
				+ "a.juzgado,"
				+"a.numerodiligencia");
		
		sql.FROM("scs_asistencia a");
	    sql.JOIN("scs_tipoasistencia ta ON (ta.idtipoasistencia = a.idtipoasistencia)");
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
		
		sql.WHERE("ROWNUM <= " + tamMax);

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
				"SCS_GUARDIASTURNO GUARDIA ON ASISTENCIA.IDGUARDIA = GUARDIA.IDGUARDIA AND ASISTENCIA.IDTURNO = GUARDIA.IDTURNO");
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

}
