package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.db.mappers.PysServiciosinstitucionSqlProvider;

public class PysServiciosinstitucionSqlExtendsProvider extends PysServiciosinstitucionSqlProvider {

	public String selectMaxIdServicioinstitucion(Short idInstitucion, Long idServicio) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDSERVICIOSINSTITUCION) +1, 1) AS IDSERVICIOINSTITUCION");
		sql.FROM("PYS_SERVICIOSINSTITUCION");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		sql.WHERE("idservicio =" + idServicio);
		
		return sql.toString();
	}
//	
//	public String selectIdServicioinstitucionByIdServicio(Short idInstitucion, Long idServicio) {
//
//		SQL sql = new SQL();
//
//		sql.SELECT("min(IDSERVICIOSINSTITUCION) as IDSERVICIOSINSTITUCION");
//		sql.FROM("PYS_SERVICIOSINSTITUCION");
//		sql.WHERE("idInstitucion =" + idInstitucion);
//		sql.WHERE("idTipoServicios = 5");
//		sql.WHERE("idservicio =" + idServicio);
//		
//		return sql.toString();
//	}
	
	public String selectIdServicioinstitucionByIdServicio(Short idInstitucion, Long idCurso) {

		SQL sql = new SQL();

		sql.SELECT("IDSERVICIO as IDSERVICIOSINSTITUCION");
		sql.FROM("FOR_CURSO");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idCurso =" + idCurso);
		
		return sql.toString();
	}
	
	public String getIndiceMaxServicio(ServicioDetalleDTO servicio, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" NVL((MAX(IDSERVICIOSINSTITUCION) + 1),1) AS IDSERVICIOSINSTITUCION");
		
		sql.FROM(" PYS_SERVICIOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" IDTIPOSERVICIOS ='" + servicio.getIdtiposervicios() + "'");
		sql.WHERE(" IDSERVICIO ='" + servicio.getIdservicio() + "'");

		return sql.toString();
	}


}
