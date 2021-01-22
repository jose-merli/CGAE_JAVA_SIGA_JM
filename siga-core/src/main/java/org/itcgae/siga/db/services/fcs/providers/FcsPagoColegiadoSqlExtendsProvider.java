package org.itcgae.siga.db.services.fcs.providers;

import java.util.ArrayList;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsPagoColegiadoSqlProvider;

public class FcsPagoColegiadoSqlExtendsProvider extends FcsPagoColegiadoSqlProvider{

	public String selectPagosColegiadoDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas) {
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
		sql.SELECT("IDPAGOSJG");
		sql.FROM("FCS_PAGO_COLEGIADO");
		sql.WHERE("IDINSTITUCION = " + colegio +  "");
		sql.WHERE("IDPERORIGEN IN (" + gruposIN +  ")");
		sql.GROUP_BY("IDINSTITUCION, IDPAGOSJG");
		if (null != listaIdPersonas && listaIdPersonas.size()>0) {
			sql.HAVING("COUNT(1) = " + listaIdPersonas.size() +  "");
		}else {
			sql.HAVING("COUNT(1) = 0");
		}
		
		return sql.toString();
	}
}

