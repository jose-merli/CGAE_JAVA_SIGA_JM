package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.mappers.ScsTiposguardiasSqlProvider;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsTiposGuardiasSqlExtendsProvider extends ScsTiposguardiasSqlProvider {

	public String comboTiposGuardia(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("SCS_TIPOSGUARDIAS.IDTIPOGUARDIA, GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		sql.FROM("SCS_TIPOSGUARDIAS");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		sql.WHERE("SCS_TIPOSGUARDIAS.FECHA_BAJA IS NULL AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE ='"+idLenguaje+"'");
		return sql.toString();
	}


}
