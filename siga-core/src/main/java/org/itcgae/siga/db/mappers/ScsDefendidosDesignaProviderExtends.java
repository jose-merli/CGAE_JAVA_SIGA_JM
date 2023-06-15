package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;

public class ScsDefendidosDesignaProviderExtends extends ScsDefendidosdesignaSqlProvider{
	
	public String seleccionarDesigna(ScsDefendidosdesigna designa) {
		SQL sql = new SQL();

		sql.SELECT("NUMERO");
		sql.SELECT("IDTURNO");
		sql.FROM("scs_designa");
		sql.WHERE("IDINSTITUCION  = " + designa.getIdinstitucion() + " ");
		sql.WHERE("ANIO   = " + designa.getAnio() + " ");
		sql.WHERE("CODIGO   = " + designa.getNumero() + " ");
		
		return sql.toString();
	}

}
