package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysServiciosinstitucionSqlProvider;

public class PysServiciosinstitucionSqlExtendsProvider extends PysServiciosinstitucionSqlProvider {

	public String selectMaxIdServicioinstitucion(Short idInstitucion, Long idServicio) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDSERVICIOSINSTITUCION) +1, 0) AS IDSERVICIOINSTITUCION");
		sql.FROM("PYS_SERVICIOSINSTITUCION");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		sql.WHERE("idservicio =" + idServicio);
		
		return sql.toString();
	}
	
	public String selectIdServicioinstitucionByIdServicio(Short idInstitucion, Long idServicio) {

		SQL sql = new SQL();

		sql.SELECT("IDSERVICIOSINSTITUCION");
		sql.FROM("PYS_SERVICIOSINSTITUCION");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		sql.WHERE("idservicio =" + idServicio);
		
		return sql.toString();
	}

}
