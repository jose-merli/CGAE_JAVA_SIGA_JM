package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTipocambiocolaSqlProvider;

public class CenTipocambioSqlExtendsProvider extends CenTipocambiocolaSqlProvider{

	public String getActionType(String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("DISTINCT TIPO.IDTIPOCAMBIO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_TIPOCAMBIO TIPO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT on CAT.IDRECURSO = TIPO.DESCRIPCION and IDLENGUAJE = '" + idLenguaje +"'");
		
		return sql.toString();
	}
}
