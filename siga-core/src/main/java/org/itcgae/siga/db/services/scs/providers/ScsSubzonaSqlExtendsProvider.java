package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
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
	
	public String searchSubzonas(String idZona, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("sub.idzona");
		sql.SELECT("sub.idsubzona");
		sql.SELECT("sub.nombre as descripcionsubzona");
		sql.SELECT("LISTAGG(parjud.idpartido, ';') WITHIN GROUP (ORDER BY parjud.idpartido) AS jurisdiccion");
		sql.FROM("SCS_SUBZONA sub");
		sql.INNER_JOIN("scs_subzonapartido subpar on subpar.idsubzona = sub.idsubzona and subpar.idinstitucion = sub.idinstitucion and sub.idzona = subpar.idzona");
		sql.INNER_JOIN("cen_partidojudicial parjud on parjud.idpartido = subpar.idpartido");
		
		sql.WHERE("sub.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("sub.idzona = '" + idZona + "'");
		
		sql.GROUP_BY("sub.idzona, sub.nombre, sub.idsubzona");
		sql.ORDER_BY("descripcionsubzona");
	
		return sql.toString();
	}
	
}
