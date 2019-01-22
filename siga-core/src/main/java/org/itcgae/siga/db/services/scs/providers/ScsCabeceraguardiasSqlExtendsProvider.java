package org.itcgae.siga.db.services.scs.providers;

import java.util.ArrayList;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;

public class ScsCabeceraguardiasSqlExtendsProvider extends ScsCabeceraguardiasSqlProvider{

	public String getCabeceraGuardiasDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas) {
		SQL sql = new SQL();
		// preparar grupos en sentencia IN
		String gruposIN = "";
		for(int i=0;i< listaIdPersonas.size(); i++) {
			
			gruposIN = gruposIN.concat("'");
			if(i != listaIdPersonas.size() -1) {
				gruposIN = gruposIN.concat(listaIdPersonas.get(i));
				gruposIN = gruposIN.concat("'");
				gruposIN = gruposIN.concat(",");
			}else {
				gruposIN = gruposIN.concat(listaIdPersonas.get(i));
				gruposIN = gruposIN.concat("'");
			}	
		}
		sql.SELECT_DISTINCT("IDINSTITUCION");
		sql.SELECT("IDTURNO");
		sql.SELECT("IDGUARDIA");
		sql.SELECT("FECHAINICIO");
		sql.FROM("SCS_CABECERAGUARDIAS");
		sql.WHERE("IDINSTITUCION = " + colegio +  "");
		sql.WHERE("IDPERSONA IN (" + gruposIN +  ")");
		sql.GROUP_BY("IDINSTITUCION,IDTURNO,IDGUARDIA, FECHAINICIO ");
		if (null != listaIdPersonas && listaIdPersonas.size()>0) {
			sql.HAVING("COUNT(1) = " + listaIdPersonas.size() +  "");
		}else {
			sql.HAVING("COUNT(1) = 0");
		}
		
		return sql.toString();
	}
}

