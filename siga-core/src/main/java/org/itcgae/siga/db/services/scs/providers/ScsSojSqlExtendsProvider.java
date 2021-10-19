package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsSojSqlProvider;

public class ScsSojSqlExtendsProvider extends ScsSojSqlProvider {

	public String searchClaveSoj(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax, String idLenguaje) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha;
		SQL sql = new SQL();

		sql.SELECT("soj.idinstitucion," 
				+ "    soj.anio," 
				+ "    soj.numsoj numero," 
				+ "    soj.numsoj codigo,"
				+ "    soj.estado," 
				+ "		soj.fechaapertura,"
				+ "(nvl(t.abreviatura,'') || '/' || nvl(g.nombre,'')) turnoguardia,"
				+ "('S' || soj.anio || '/' || soj.numero) asunto," + "    f_siga_getrecurso(nvl(ts.descripcion,'')," + idLenguaje + ") tiposoj," 
				+ "		soj.idtiposoj,"
				+ "    (nvl(pjg.nombre,'') || ' ' || nvl(pjg.apellido1,'') || ' ' || nvl(pjg.apellido2,'')) interesado,"
				+ "(nvl(per.nombre,'') || ' ' || nvl(per.apellidos1,'') || ' ' || nvl(per.apellidos2,'')) letrado");
		sql.FROM("    scs_soj soj\r\n");
		sql.JOIN("scs_personajg pjg ON ( pjg.idpersona = soj.idpersonajg AND pjg.idinstitucion = soj.idinstitucion)");
		sql.JOIN("cen_persona per ON (soj.idpersona = per.idpersona)");
		sql.JOIN("scs_turno t ON ( t.idturno = soj.idturno AND t.idinstitucion = soj.idinstitucion)");
		sql.JOIN(
				"scs_guardiasturno g ON ( soj.idturno = g.idturno AND soj.idinstitucion = g.idinstitucion AND soj.idguardia = g.idguardia)");
		sql.JOIN("scs_tiposoj ts ON ( ts.idtiposoj = soj.idtiposoj )");
		sql.JOIN("scs_tiposojcolegio tsc on (tsc.idtiposojcolegio = soj.idtiposojcolegio and tsc.idinstitucion = soj.idinstitucion)");
		sql.WHERE("soj.idinstitucion = "+asuntosJusticiableItem.getIdInstitucion());

		if (asuntosJusticiableItem.getAnio() != null && !asuntosJusticiableItem.getAnio().trim().isEmpty()) {
			sql.WHERE("soj.anio = " + asuntosJusticiableItem.getAnio().trim());
		}

		if (asuntosJusticiableItem.getNumero() != null && !asuntosJusticiableItem.getNumero().trim().isEmpty()) {
			sql.WHERE("soj.numsoj = " + asuntosJusticiableItem.getNumero().trim());
		}

		if (asuntosJusticiableItem.getIdTipoSoj() != null && !asuntosJusticiableItem.getIdTipoSoj().trim().isEmpty()) {
			sql.WHERE("soj.idtiposoj = " + asuntosJusticiableItem.getIdTipoSoj().trim());
		}

		if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
			sql.WHERE("soj.fechaapertura >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
			sql.WHERE("soj.fechaapertura <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
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

		if (asuntosJusticiableItem.getIdTurno() != null) {
			sql.WHERE("soj.guardiaturno_idturno = " + asuntosJusticiableItem.getIdTurno());
		}
		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("soj.guardiaturno_idguardia = " + asuntosJusticiableItem.getIdGuardia());
		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("soj.idpersona = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}
		

		sql.ORDER_BY("soj.anio desc, soj.numero DESC");
//				+ "    AND\r\n"
//				+ "        soj.numero = $$NUM SOJ$$\r\n"
//				+ "    AND\r\n"
//				+ "        TO_CHAR(soj.fechaapertura ,'DD/MM/RRRR') >= TO_DATE($$FECHAAPERTURADESDE$$,'DD/MM/RRRR')\r\n"
//				+ "    AND\r\n"
//				+ "        TO_CHAR(soj.fechaapertura ,'DD/MM/RRRR') >= TO_DATE($$FECHAAPERTURAHASTA$$,'DD/MM/RRRR')\r\n"
//				+ "    AND\r\n"
//				+ "        soj.idtiposoj = $$ID TIPO SOJ$$\r\n"
//				+ "    AND\r\n"
//				+ "        soj.idturno = $$IDTURNO$$\r\n"
//				+ "    AND\r\n"
//				+ "        soj.idguardia = $$IDGUARDIA$$\r\n"
//				+ "    AND\r\n"
//				+ "        soj.idpersona = $$ID PERSONA COLEGIADO$$ --(HAY QUE UNA BUSQUEDA PRIMERO DEL NCOLEGIADO PARA SABER LA IDPERSONA)\r\n"
//				+ "    AND\r\n"
//				+ "        
//				+ "    AND\r\n"
//				+ "        upper(pjg.nombre) LIKE upper(\"'%\"+$$NOMBRE INTERESADO$$+\"%'\")\r\n"
//				+ "    AND\r\n"
//				+ "        upper( (pjg.apellido1 || ' ' || pjg.apellido2) ) LIKE upper(\"'%\"+$$APELLIDOS INTERESADO$$+\"%'\")\r\n"
//				+ "    AND\r\n"
//				+ "        upper(pjg.nif) LIKE upper(\"'%\"+$$NIF INTERESADO$$+\"%'\")"
//		SQL sqlOrder = new SQL();
//
//		sqlOrder.SELECT("*");
//		sql.SELECT("SOJ.idinstitucion, SOJ.anio,SOJ.numero,to_char(SOJ.IDTIPOSOJ) AS clave, '' as rol, 'S' as tipo");
//		sql.FROM("SCS_SOJ SOJ");
//		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
//		// alguno de los datos del solicitante(Nif, nombre o apellidos).
//		if (asuntosJusticiableItem.getNombre() != null || asuntosJusticiableItem.getApellidos() != null
//				|| asuntosJusticiableItem.getNif() != null) {
//			sql.INNER_JOIN(
//					"SCS_PERSONAJG PERSONA ON (SOJ.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = SOJ.IDINSTITUCION)");
//		}
//		sql.WHERE("SOJ.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());
//
//		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
//			sql.WHERE("SOJ.ANIO = " + asuntosJusticiableItem.getAnio());
//		}
//		if (asuntosJusticiableItem.getNumero() != null) {
//			sql.WHERE("SOJ.NUMERO = " + asuntosJusticiableItem.getNumero());
//		}
//		if (asuntosJusticiableItem.getIdTurno() != null) {
//			sql.WHERE("SOJ.IDTURNO = " + asuntosJusticiableItem.getIdTurno());
//		}
//		if (asuntosJusticiableItem.getIdGuardia() != null) {
//			sql.WHERE("SOJ.IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
//		}
//
//		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
//			sql.WHERE("SOJ.IDPERSONA = " + asuntosJusticiableItem.getIdPersonaColegiado());
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
//		if (asuntosJusticiableItem.getNif() != null  && asuntosJusticiableItem.getNif() != "") {
//            sql.WHERE("upper(PERSONA.NIF) like upper('%"+asuntosJusticiableItem.getNif()+"%')");
//		}
//
//		if ((asuntosJusticiableItem.getNombre() != null  && asuntosJusticiableItem.getNombre() != "")|| (asuntosJusticiableItem.getApellidos() != null && asuntosJusticiableItem.getApellidos() != "")
//				|| (asuntosJusticiableItem.getNif() != null  && asuntosJusticiableItem.getNif() != "")) {
//			if (asuntosJusticiableItem.getApellidos() != null)
//				sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
//						asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
//			if (asuntosJusticiableItem.getNombre() != null)
//				sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));
//		}
//
//		if (asuntosJusticiableItem.getIdTipoSoj() != null) {
//			sql.WHERE("SOJ.IDTIPOSOJ = " + asuntosJusticiableItem.getIdTipoSoj());
//		}
//
//		sqlOrder.FROM("(" + sql + " )");
//		if (tamMax != null) {
//			Integer tamMaxNumber = tamMax + 1;
//			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
//		}
		
//		return sqlOrder.toString();
		
		SQL sqlLimit = new SQL();
		sqlLimit.SELECT("*");
		sqlLimit.FROM("(" + sql.toString() + ")");
		sqlLimit.WHERE("ROWNUM <= " + tamMax);
		return sqlLimit.toString();
	}

	public String getAsuntoTipoSoj(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("SOJ.IDINSTITUCION");
		sql.SELECT("concat('S' || SOJ.anio || '/',lpad(SOJ.NUMSOJ,5,'0') ) as asunto");
		sql.SELECT("SOJ.FECHAAPERTURA as fecha");
		sql.SELECT("SOJ.ANIO");
		sql.SELECT("SOJ.NUMERO");
		sql.SELECT("' ' AS turnoguardia");
		sql.SELECT(
				"('<b>Tipo Consulta</b>: ' || RECTIPOCONSULTA.DESCRIPCION || '<br/> <b>Tipo Respuesta</b>: ' || RECTIPORESPUESTA.DESCRIPCION) as datosInteres");
		sql.SELECT("SOJ.idpersonajg IDPERSONASOJ");
		sql.SELECT("SOJ.IDPERSONA IDPERSONACOLEGIADO");

		sql.FROM("SCS_SOJ SOJ");
		sql.LEFT_OUTER_JOIN(
				"scs_tiporespuesta TIPORESPUESTA ON SOJ.IDTIPORESPUESTA = TIPORESPUESTA.IDTIPORESPUESTA AND SOJ.IDINSTITUCION = TIPORESPUESTA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPORESPUESTA ON RECTIPORESPUESTA.IDRECURSO = TIPORESPUESTA.DESCRIPCION AND RECTIPORESPUESTA.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tipoconsulta TIPOCONSULTA ON SOJ.IDTIPOCONSULTA = TIPOCONSULTA.IDTIPOCONSULTA AND SOJ.IDINSTITUCION = TIPOCONSULTA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPOCONSULTA ON RECTIPOCONSULTA.IDRECURSO = TIPOCONSULTA.DESCRIPCION AND RECTIPOCONSULTA.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.WHERE("SOJ.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("SOJ.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("SOJ.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("SOJ.IDTIPOSOJ = '" + asuntoClave.getClave() + "'");

		return sql.toString();
	}
	
	public String eliminarRelacionSoj(String idinstitucion, String anio, String numero, String tipoSoj){
    	SQL sql = new SQL();
    	
    	sql.UPDATE("SCS_SOJ");
    	sql.SET("EJGIDTIPOEJG = null");
    	sql.SET("EJGANIO = null");
    	sql.SET("EJGNUMERO = null");
    	
    	sql.WHERE("IDINSTITUCION = " + idinstitucion);
		sql.WHERE("ANIO = " + anio);
		sql.WHERE("NUMERO = " + numero);
		sql.WHERE("IDTIPOSOJ = " + tipoSoj);
    	
    	return sql.toString();
    }

}
