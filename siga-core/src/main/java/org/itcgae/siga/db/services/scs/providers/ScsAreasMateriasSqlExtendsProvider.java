package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AreasItem;
import org.itcgae.siga.db.mappers.ScsAreaSqlProvider;

public class ScsAreasMateriasSqlExtendsProvider extends ScsAreaSqlProvider {

	public String getIdArea(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDAREA) AS IDAREA");
		sql.FROM("SCS_AREA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");

		return sql.toString();
	}
	
	public String getJurisdicciones(String idInstitucion, String nif) {

		SQL sql = new SQL();

		sql.SELECT("jurisdiccion.IDJURISDICCION");
		sql.SELECT("rec.descripcion");

		sql.FROM("SCS_JURISDICCION jurisdiccion");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec on rec.idrecurso= jurisdiccion.DESCRIPCION and rec.idlenguaje = (select idLenguaje from Adm_Usuarios where idInstitucion = "+ idInstitucion +" and nif = '"+ nif +"')");
		
		sql.ORDER_BY("rec.DESCRIPCION");

		return sql.toString();
	}
	
	public String comboAreas(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDAREA, NOMBRE");
		sql.FROM("SCS_AREA");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion +"'");
		sql.ORDER_BY("nombre");

		return sql.toString();
	}

	public String searchAreas(AreasItem areasItem) {
		
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		
		sql1.SELECT("*");
		sql.SELECT("scs_area.idarea, scs_area.nombre, scs_area.contenido, scs_area.fechabaja, scs_area.idinstitucion,LISTAGG(materia.nombre, ';') WITHIN GROUP (ORDER BY scs_area.idinstitucion,scs_area.idarea) AS nombremateriagrup "
				+ ",scs_area.jurisdicciones, scs_area.idjurisdicciones").ORDER_BY("scs_area.nombre");

		sql2.SELECT("scs_area.idarea, scs_area.nombre, scs_area.contenido, scs_area.fechabaja, scs_area.idinstitucion,"
				+ "LISTAGG(materiajurisdiccion.descripcion, ';') WITHIN GROUP (ORDER BY scs_area.idinstitucion,scs_area.idarea) AS jurisdicciones,"
				+ "LISTAGG(materiajurisdiccion.idjurisdiccion, ';') WITHIN GROUP (ORDER BY scs_area.idinstitucion,scs_area.idarea) AS idjurisdicciones");
		sql2.FROM("scs_area");
		
		sql3.SELECT("distinct materiajurisdiccion.idinstitucion, materiajurisdiccion.idarea,rec.descripcion, jurisdiccion.idjurisdiccion from SCS_MATERIAJURISDICCION materiajurisdiccion "
				+ "left join SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION = materiajurisdiccion.IDJURISDICCION left join GEN_RECURSOS_CATALOGOS rec on rec.idrecurso = jurisdiccion.DESCRIPCION and rec.idlenguaje = 1");
		sql2.LEFT_OUTER_JOIN("("+sql3.toString() + ") materiajurisdiccion on ( materiajurisdiccion.idinstitucion = scs_area.idinstitucion and materiajurisdiccion.idarea = scs_area.idarea)"); 
		sql2.WHERE("scs_area.idinstitucion = "+ areasItem.getidInstitucion());
		if(areasItem.getNombreArea() != null && !areasItem.getNombreArea().isEmpty()) {
			sql2.WHERE("LOWER(scs_area.nombre)  like '%" + areasItem.getNombreArea().toLowerCase()+"%'");
		}
		if(!areasItem.isHistorico()) {
			sql2.WHERE("scs_area.fechabaja is null");
		}
		sql2.GROUP_BY("scs_area.idarea, scs_area.nombre, scs_area.contenido,scs_area.idinstitucion, scs_area.fechabaja");
		
		sql.FROM("("+sql2.toString() + ") scs_area"); 
		sql.LEFT_OUTER_JOIN("scs_materia materia on scs_area.idarea = materia.idarea and\r\n" + 
				"scs_area.idinstitucion = materia.idinstitucion");
		
		sql.GROUP_BY("scs_area.idarea, scs_area.nombre, scs_area.contenido,\r\n" + 
				"scs_area.idinstitucion,scs_area.jurisdicciones, scs_area.fechabaja, scs_area.idjurisdicciones");
		sql1.FROM("("+sql.toString() + ") consulta"); 
		if(areasItem.getNombreMateria() != null && !areasItem.getNombreMateria().isEmpty()) {
			sql1.WHERE(" LOWER(consulta.nombremateriagrup) like '%" + areasItem.getNombreMateria().toLowerCase()+ "%'");
		}
		if(areasItem.getJurisdiccion() != null && !areasItem.getJurisdiccion().isEmpty()) {
			sql1.WHERE(" LOWER(consulta.idjurisdicciones) like '%" + areasItem.getJurisdiccion().toLowerCase()+ "%'");
		}
		if(areasItem.getIdArea() != null && !areasItem.getIdArea().isEmpty()) {
			sql1.WHERE(" LOWER(consulta.idArea) = '" + areasItem.getIdArea().toLowerCase()+ "'");
		}
		return sql1.toString();
	}
	
}
