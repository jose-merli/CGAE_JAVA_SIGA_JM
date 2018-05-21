package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenGruposclienteSqlProvider;

public class CenGruposclienteSqlExtendsProvider extends CenGruposclienteSqlProvider{

	public String getLabel(AdmUsuarios usuario) {
		SQL sql = new SQL();
		
		sql.SELECT("distinct GRUCLI.IDGRUPO");
		sql.SELECT("GENR.DESCRIPCION");
		sql.FROM(" cen_gruposcliente GRUCLI");
		sql.INNER_JOIN("  GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO");
		sql.WHERE(" GRUCLI.IDINSTITUCION = '"+ usuario.getIdinstitucion()  + "'");
		sql.WHERE(" GENR.IDLENGUAJE = '"+ usuario.getIdlenguaje()+ "'");
		
		return sql.toString();
	}
}
