package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysPreciosserviciosSqlProvider;

public class PysPreciosserviciosSqlExtendsProvider extends PysPreciosserviciosSqlProvider {

	public String selectMaxIdPrecioServicio(Short idInstitucion, Long idServicio, Long idServicioInstitucion, Short idPeriocidad) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDPRECIOSSERVICIOS) +1, 0) AS IDPRECIOSERVICIO");
		sql.FROM("PYS_PRECIOSSERVICIOS");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		sql.WHERE("idServicio = " + idServicio);
		sql.WHERE("idserviciosinstitucion = " + idServicioInstitucion);
		sql.WHERE("IDPERIODICIDAD = " + idPeriocidad );
		
		return sql.toString();
	}

}
