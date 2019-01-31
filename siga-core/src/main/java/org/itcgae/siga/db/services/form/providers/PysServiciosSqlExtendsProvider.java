package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysServiciosSqlProvider;

public class PysServiciosSqlExtendsProvider extends PysServiciosSqlProvider {

	public String selectMaxIdServicio(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDSERVICIO) +1, 0) AS IDSERVICIO");
		sql.FROM("PYS_SERVICIOS");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		
		return sql.toString();
	}
	
	public String selectIdServicioByIdCurso(Short idInstitucion, Long idCurso) {

		SQL sql = new SQL();

		sql.SELECT("IDSERVICIO");
		sql.FROM("FOR_CURSO");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idCurso =" + idCurso);
		
		return sql.toString();
	}

}