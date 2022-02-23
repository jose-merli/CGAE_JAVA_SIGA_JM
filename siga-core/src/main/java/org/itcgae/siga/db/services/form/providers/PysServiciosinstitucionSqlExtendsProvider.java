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
	
	public String selectIdServicioinstitucionByIdServicio(Short idInstitucion, Long idCurso) {

		SQL sql = new SQL();

		sql.SELECT("IDSERVICIO as IDSERVICIOSINSTITUCION");
		sql.FROM("FOR_CURSO");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idCurso =" + idCurso);
		
		return sql.toString();
	}

	public String existsDescription(Long idServicio, Long idServiciosInstitucion, Short idTipoServicios, Short idInstitucion, String descripcion) {
		SQL sql = new SQL();

		sql.SELECT("COUNT(*)");

		sql.FROM("PYS_SERVICIOSINSTITUCION servicio");

		if (idServicio != null && idServiciosInstitucion != null && idTipoServicios != null)
			sql.WHERE("NOT (servicio.IDSERVICIO = '" + idServicio +"' AND servicio.IDSERVICIOSINSTITUCION = '" + idServiciosInstitucion + "' AND servicio.IDTIPOSERVICIOS = '" + idTipoServicios + "')");

		sql.WHERE("servicio.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("servicio.DESCRIPCION = '" + descripcion.trim() + "'");

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

	public String detalleServicio(int idTipoServicio, int idServicio, int idServiciosInstitucion, Short idInstitucion) {

		SQL sql = new SQL();
		
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.IDSERVICIO");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.IDSERVICIOSINSTITUCION");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.DESCRIPCION");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.SOLICITARBAJA");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.SOLICITARALTA");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.CUENTACONTABLE");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.FECHABAJA");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.NOFACTURABLE");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.IDTIPOIVA");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.CODIGOEXT");
		
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.AUTOMATICO");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.IDCONSULTA");
		
		sql.SELECT(" PYS_SERVICIOS.DESCRIPCION AS TIPO");
		sql.SELECT(" PYS_TIPOIVA.VALOR AS VALORIVA");
		sql.SELECT(" F_SIGA_GETRECURSO (PYS_TIPOSERVICIOS.DESCRIPCION, 1) AS CATEGORIA");
		
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.INICIOFINALPONDERADO");
		sql.SELECT(" PYS_SERVICIOSINSTITUCION.FACTURACIONPONDERADA");
		
		sql.FROM( "PYS_SERVICIOSINSTITUCION");
		
		sql.JOIN(" PYS_SERVICIOS ON\r\n"
				+ " PYS_SERVICIOSINSTITUCION.IDSERVICIO = PYS_SERVICIOS.IDSERVICIO\r\n"
				+ " AND PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = PYS_SERVICIOS.IDTIPOSERVICIOS\r\n"
				+ " AND PYS_SERVICIOS.IDINSTITUCION = PYS_SERVICIOSINSTITUCION.IDINSTITUCION");
		
		sql.JOIN(" PYS_TIPOSERVICIOS ON\r\n"
				+ " PYS_TIPOSERVICIOS.IDTIPOSERVICIOS = PYS_SERVICIOS.IDTIPOSERVICIOS");
		
		sql.JOIN(" PYS_TIPOIVA ON\r\n"
				+ " PYS_SERVICIOSINSTITUCION.IDTIPOIVA = PYS_TIPOIVA.IDTIPOIVA");
		
		sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDTIPOSERVICIOS = '" + idTipoServicio + "'");
		sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDSERVICIO = '" + idServicio + "'");
		sql.WHERE(" PYS_SERVICIOSINSTITUCION.IDSERVICIOSINSTITUCION = '" + idServiciosInstitucion + "'");
		
		sql.ORDER_BY("PYS_SERVICIOSINSTITUCION.DESCRIPCION");
		
		return sql.toString();
		
	}

	public String obtenerFormasDePagoInternetByServicio(int idTipoServicio, int idServicio, int idServiciosInstitucion, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDFORMAPAGO");
				
		sql.FROM(" PYS_FORMAPAGOSERVICIOS");
				
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDTIPOSERVICIOS = '" + idTipoServicio + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDSERVICIO = '" + idServicio + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDSERVICIOSINSTITUCION = '" + idServiciosInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.INTERNET = 'A'" );
				
		return sql.toString();
	}
	
	public String obtenerFormasDePagoSecretariaByServicio(int idTipoServicio, int idServicio, int idServiciosInstitucion, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDFORMAPAGO");
				
		sql.FROM(" PYS_FORMAPAGOSERVICIOS");
				
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDTIPOSERVICIOS = '" + idTipoServicio + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDSERVICIO = '" + idServicio + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.IDSERVICIOSINSTITUCION = '" + idServiciosInstitucion + "'");
		sql.WHERE(" PYS_FORMAPAGOSERVICIOS.INTERNET = 'S'" );
				
		return sql.toString();
	}
	
	public String comboCondicionSuscripcion(String idioma, short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDCONSULTA");
		sql.SELECT(" f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM(" con_consulta");
		sql.WHERE(" idobjetivo = 3");
		sql.WHERE(" idclasecomunicacion = 1");
		//sql.WHERE(" idmodulo = 9");
		sql.WHERE(" idinstitucion = '" + idInstitucion + "'");
		sql.WHERE(" fechabaja is null");
		
		
		
		sql.ORDER_BY(" DESCRIPCION");
		
	
		return sql.toString();

	}
	
	public String getCriterioByIdConsulta(short idInstitucion, int idConsulta) {
		SQL sql = new SQL();
		
		sql.SELECT(" SENTENCIA");
		
		sql.FROM(" con_consulta");
		sql.WHERE(" idobjetivo = 3");
		sql.WHERE(" idclasecomunicacion = 1");
		sql.WHERE(" idinstitucion = '" + idInstitucion + "'");
		sql.WHERE(" idconsulta = " + idConsulta);

		return sql.toString();

	}
	
	public String getIdServicioInstitucion(ServicioDetalleDTO servicio, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" IDSERVICIOSINSTITUCION");
		
		sql.FROM(" PYS_SERVICIOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" IDTIPOSERVICIOS ='" + servicio.getIdtiposervicios() + "'");
		sql.WHERE(" IDSERVICIO ='" + servicio.getIdservicio() + "'");
		sql.WHERE(" DESCRIPCION = '" + servicio.getDescripcion() + "'");
		
		return sql.toString();
	}

}
