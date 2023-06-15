package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.db.mappers.CenGruposcriteriosSqlProvider;
import org.itcgae.siga.db.mappers.FacSeriefacturacionSqlProvider;

import java.util.stream.Collectors;

public class CenGruposcriteriosExtendsSqlProvider extends CenGruposcriteriosSqlProvider {

	public String getNewIdGruposCriterios(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("(NVL(MAX(gc.idgruposcriterios),0) + 1) as id");
		sql.FROM("cen_gruposcriterios gc");
		sql.WHERE("gc.idinstitucion = " + idInstitucion);

		return sql.toString();
	}

}
