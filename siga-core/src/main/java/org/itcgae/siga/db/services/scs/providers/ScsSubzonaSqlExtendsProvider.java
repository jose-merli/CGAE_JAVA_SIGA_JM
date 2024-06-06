package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsSubzonaExample;
import org.itcgae.siga.db.mappers.ScsSubzonaSqlProvider;

public class ScsSubzonaSqlExtendsProvider extends ScsSubzonaSqlProvider{
	
	public String getIdSubzona(Short idInstitucion, short idZona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDSUBZONA) AS IDSUBZONA");
		sql.FROM("SCS_SUBZONA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("IDZONA = '"+ idZona +"'");

		return sql.toString();
	}
	
	public String getIdSubzona2(Short idInstitucion, String idZona) {
		        SQL sql = new SQL();
		        sql.SELECT("IDINSTITUCION");
		        sql.SELECT("IDZONA");
		        sql.SELECT("IDSUBZONA");
		        sql.SELECT("NOMBRE");
		        sql.SELECT("FECHAMODIFICACION");
		        sql.SELECT("USUMODIFICACION");
		        sql.SELECT("MUNICIPIOS");
		        sql.SELECT("IDPARTIDO");
		        sql.SELECT("FECHABAJA");
		        sql.FROM("SCS_SUBZONA");
		        sql.WHERE("((IDZONA IN ("+ idZona +" ) AND IDINSTITUCION = " + idInstitucion + " AND FECHABAJA IS NULL))");
				sql.ORDER_BY("NOMBRE");
		        
		        return sql.toString();
		    }
	
	public String searchSubzonas(String idZona, String idSubZona, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("sub.idzona");
		sql.SELECT("sub.idsubzona");
		sql.SELECT("sub.nombre as descripcionsubzona");
		sql.SELECT("LISTAGG(parjud.idpartido, '; ') WITHIN GROUP (ORDER BY parjud.nombre) AS jurisdiccion");
		sql.SELECT("LISTAGG(parjud.nombre, '; ') WITHIN GROUP (ORDER BY parjud.nombre) AS nombrePartidosJudiciales");
		sql.SELECT("sub.fechabaja");
		
		
		sql.FROM("SCS_SUBZONA sub");
		sql.INNER_JOIN("scs_subzonapartido subpar on subpar.idsubzona = sub.idsubzona and subpar.idinstitucion = sub.idinstitucion and sub.idzona = subpar.idzona");
		sql.INNER_JOIN("cen_partidojudicial parjud on parjud.idpartido = subpar.idpartido");
		
		sql.WHERE("sub.idinstitucion = '" + idInstitucion + "'");
		if(idZona.contains(",")) {
			sql.WHERE("sub.idzona IN (" + idZona + ")");
		}else {
			sql.WHERE("sub.idzona = '" + idZona + "'");
		}
		
		if(idSubZona != null) {
			sql.WHERE("sub.idsubzona = '" + idSubZona + "'");
		}
		
		sql.GROUP_BY("sub.idzona, sub.nombre, sub.idsubzona, sub.fechabaja");
		sql.ORDER_BY("descripcionsubzona");
	
		return sql.toString();
	}
	
}
