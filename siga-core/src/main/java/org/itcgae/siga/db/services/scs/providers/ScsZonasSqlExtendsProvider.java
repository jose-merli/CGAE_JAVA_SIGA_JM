package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ZonasItem;
import org.itcgae.siga.db.mappers.ScsZonaSqlProvider;

public class ScsZonasSqlExtendsProvider extends ScsZonaSqlProvider{

	public String searchZonas(ZonasItem zonasItem, Short idInstitucion) {
		
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		
		sql1.SELECT("*");
		sql.SELECT("scs_zona.idzona");
		sql.SELECT("scs_zona.nombre");
		sql.SELECT("scs_zona.idinstitucion");
		sql.SELECT("scs_zona.fechabaja");
		sql.SELECT("LISTAGG(subzona.nombre, ';') WITHIN GROUP (ORDER BY scs_zona.idinstitucion, scs_zona.idzona) AS nombresubzonas");
		sql.SELECT("LISTAGG(subzona.idsubzona,';') WITHIN GROUP(ORDER BY scs_zona.idinstitucion, scs_zona.idzona ) AS idsConjuntoSubzonas");
		sql.SELECT("NVL(scs_zona.partidosjudiciales,' ') as partidosjudiciales");
		sql.SELECT("nvl(scs_zona.idpartidosjudiciales,' ') AS idpartidosjudiciales");
		
		SQL sql2 = new SQL();
				
		sql2.SELECT_DISTINCT("scs_zona.idzona");
		sql2.SELECT("scs_zona.nombre");
		sql2.SELECT("scs_zona.idinstitucion");
		sql2.SELECT("LISTAGG(partidojudicial.nombre, ';') WITHIN GROUP(ORDER BY scs_zona.idinstitucion, scs_zona.idzona) AS partidosjudiciales");
		sql2.SELECT("LISTAGG(partidojudicial.idpartido, ';') WITHIN GROUP(ORDER BY scs_zona.idinstitucion, scs_zona.idzona) AS idpartidosjudiciales");
		sql2.SELECT("scs_zona.fechabaja");

		sql2.FROM("SCS_ZONA");
		
		SQL sql3 = new SQL();
		
		sql3.SELECT_DISTINCT("subzonapartido.idinstitucion");
		sql3.SELECT("subzonapartido.idzona");
		sql3.SELECT("partido.nombre");
		sql3.SELECT("partido.idpartido");

		sql3.FROM("scs_subzonapartido subzonapartido");
		sql3.LEFT_OUTER_JOIN("cen_partidojudicial partido ON partido.idpartido = subzonapartido.idpartido) partidojudicial "
				+ "ON ( partidojudicial.idinstitucion = scs_zona.idinstitucion AND partidojudicial.idzona = scs_zona.idzona )");

		sql2.LEFT_OUTER_JOIN("(" + sql3 + "" );

		sql2.GROUP_BY("scs_zona.idzona, scs_zona.nombre, scs_zona.idinstitucion, scs_zona.fechabaja");
		
		sql.FROM("(" + sql2 + ") scs_zona");
		sql.LEFT_OUTER_JOIN("scs_subzona subzona ON scs_zona.idzona = subzona.idzona AND scs_zona.idinstitucion = subzona.idinstitucion");
		
		sql.WHERE("scs_zona.idinstitucion = '" + idInstitucion + "'");
		
		if(zonasItem.getDescripcionzona() != null && zonasItem.getDescripcionzona() != "") {
			sql.WHERE("UPPER(scs_zona.nombre) like UPPER('%"+ zonasItem.getDescripcionzona() + "%')");
		}
		
		if(!zonasItem.getHistorico()) {
			sql.WHERE("scs_zona.fechabaja is null");
		}
		
		sql.GROUP_BY("scs_zona.idzona, scs_zona.nombre, scs_zona.idinstitucion, scs_zona.partidosjudiciales, scs_zona.fechabaja, scs_zona.idpartidosjudiciales");
		sql.ORDER_BY("scs_zona.nombre");
		
		sql1.FROM("(" + sql.toString() + ") consulta");
		
		if(zonasItem.getDescripcionsubzona() != null && zonasItem.getDescripcionsubzona() != "") {
			sql1.WHERE("UPPER(consulta.nombresubzonas) like UPPER('%"+ zonasItem.getDescripcionsubzona() + "%')");
		}
		
		if(zonasItem.getDescripcionpartido() != null && zonasItem.getDescripcionpartido() != "") {
			sql1.WHERE("UPPER(consulta.partidosjudiciales) like UPPER('%"+ zonasItem.getDescripcionpartido() + "%')");
		}
	
		return sql1.toString();
	}
	
	public String getIdZona(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDZONA) AS IDZONA");
		sql.FROM("SCS_ZONA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
	
	public String searchGroupZoneByName(String idZona, String nombre, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("idzona");
		sql.SELECT("nombre");
		sql.FROM("SCS_ZONA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("NOMBRE like '"+ nombre +"'");
		sql.WHERE("IDZONA not in '" + idZona + "'");
		
		return sql.toString();
	}
	
	public String searchGroupZoneOnlyByName(String nombre, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("idzona");
		sql.SELECT("nombre");
		sql.FROM("SCS_ZONA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("UPPER(NOMBRE) like UPPER('"+ nombre +"')");
		
		return sql.toString();
	}
	
}
