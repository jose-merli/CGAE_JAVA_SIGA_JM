package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.MateriasItem;
import org.itcgae.siga.db.mappers.ScsMateriaSqlProvider;

public class ScsMateriaSqlExtendsProvider extends ScsMateriaSqlProvider {

	public String searchMateria(MateriasItem materia) {
		SQL sql = new SQL();

		sql.SELECT("mat.IDAREA");
		sql.SELECT("mat.IDMATERIA");
		sql.SELECT("mat.NOMBRE as NOMBREMATERIA");
		sql.SELECT("mat.CONTENIDO");
		sql.SELECT("mat.IDINSTITUCION");
		sql.FROM("SCS_MATERIA mat");
		sql.SELECT("LISTAGG(jur.idjurisdiccion, ';') WITHIN GROUP (ORDER BY jur.idjurisdiccion) AS jurisdiccion");
		sql.LEFT_OUTER_JOIN("scs_MATERIAJURISDICCION jur on jur.idmateria = mat.idmateria and jur.idarea = mat.idarea");
		sql.WHERE("mat.IDAREA = '"+ materia.getIdArea() +"'");
		sql.WHERE("mat.IDINSTITUCION = '"+ materia.getidInstitucion() + "'");
		sql.GROUP_BY("mat.IDAREA, mat.IDMATERIA, mat.NOMBRE, mat.CONTENIDO, mat.IDINSTITUCION");
		sql.ORDER_BY("NOMBREMATERIA");
		return sql.toString();
	}
	

	public String getIdMateria(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDMATERIA) AS IDMATERIA");
		sql.FROM("SCS_MATERIA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");

		return sql.toString();
	}
	
	public String comboMaterias(Short idInstitucion, String idArea) {

		SQL sql = new SQL();

		sql.SELECT("IDMATERIA, NOMBRE");
		sql.FROM("SCS_MATERIA");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion +"' AND IDAREA IN ("+idArea+")");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}
	
//	SQL sql = new SQL();
//	
//	sql.SELECT("sub.idzona");
//	sql.SELECT("sub.idsubzona");
//	sql.SELECT("sub.nombre as descripcionsubzona");
//	sql.SELECT("LISTAGG(parjud.idpartido, ';') WITHIN GROUP (ORDER BY parjud.idpartido) AS jurisdiccion");
//	sql.SELECT("sub.fechabaja");
//	
//	sql.FROM("SCS_SUBZONA sub");
//	sql.INNER_JOIN("scs_subzonapartido subpar on subpar.idsubzona = sub.idsubzona and subpar.idinstitucion = sub.idinstitucion and sub.idzona = subpar.idzona");
//	sql.INNER_JOIN("cen_partidojudicial parjud on parjud.idpartido = subpar.idpartido");
//	
//	sql.WHERE("sub.idinstitucion = '" + idInstitucion + "'");
//	sql.WHERE("sub.idzona = '" + idZona + "'");
//	
//	sql.GROUP_BY("sub.idzona, sub.nombre, sub.idsubzona, sub.fechabaja");
//	sql.ORDER_BY("descripcionsubzona");
//
//	return sql.toString();
}
