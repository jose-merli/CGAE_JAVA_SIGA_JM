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

		sql.SELECT("IDTIPOSERVICIO");
		sql.FROM("FOR_CURSO");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idCurso =" + idCurso);
		
		return sql.toString();
	}

	public String getServicesCourse(String idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("ts.idservicio as IDTIPOSERVICIO");
		sql.SELECT("ts.DESCRIPCION");
		sql.FROM("PYS_SERVICIOS ts");
		sql.WHERE("ts.IDTIPOSERVICIOS = 5");
		sql.WHERE("ts.idinstitucion ='" + idInstitucion + "'");
		sql.ORDER_BY("TS.DESCRIPCION");
		return sql.toString();
	}
}
