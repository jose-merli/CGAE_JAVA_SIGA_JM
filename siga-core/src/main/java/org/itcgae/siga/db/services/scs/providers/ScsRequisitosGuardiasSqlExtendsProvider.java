package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsRequisitosguardiasSqlProvider;
import org.itcgae.siga.db.mappers.ScsTiposguardiasSqlProvider;

public class ScsRequisitosGuardiasSqlExtendsProvider extends ScsRequisitosguardiasSqlProvider {

	public String comboRequisitosGuardia() {

		SQL sql = new SQL();

		sql.SELECT("IDREQUISITOSGUARDIAS,DESCRIPCION");
		sql.FROM("SCS_REQUISITOSGUARDIAS");
		sql.ORDER_BY( "IDREQUISITOSGUARDIAS");
		return sql.toString();
	}


}
