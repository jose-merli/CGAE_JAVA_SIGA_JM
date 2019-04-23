package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysSuscripcionSqlProvider;

public class PysSuscripcionSqlExtendsProvider extends PysSuscripcionSqlProvider {

	public String selectMaxIdSuscripcion(Short idInstitucion, Long idServicio, Long idServicioInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDSUSCRIPCION) +1, 1) AS IDSUSCRIPCION");
		sql.FROM("PYS_SUSCRIPCION");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		sql.WHERE("idServicio = " + idServicio);
		sql.WHERE("idserviciosinstitucion = " + idServicioInstitucion);
		
		return sql.toString();
	}
	
	

}
