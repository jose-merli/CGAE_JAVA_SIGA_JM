package org.itcgae.siga.db.services.form.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.TiposServiciosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.PysServiciosSqlProvider;

public class PysServiciosSqlExtendsProvider extends PysServiciosSqlProvider {

	public String selectMaxIdServicio(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDSERVICIO) +1, 1) AS IDSERVICIO");
		sql.FROM("PYS_SERVICIOS");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		
		return sql.toString();
	}
	
	public String selectIdServicioByIdCurso(Short idInstitucion, Long idCurso) {

		SQL sql = new SQL();

		sql.SELECT("IDTIPOSERVICIO as IDSERVICIO");
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
	
	public String searchTiposServicios(String idioma, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("ss.IDTIPOSERVICIOS");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("ss.IDSERVICIO");
		sql.SELECT("ss.DESCRIPCION");
		sql.SELECT("ss.FECHABAJA");
		
		sql.FROM("PYS_SERVICIOS ss");
		
		sql.JOIN("PYS_TIPOSERVICIOS tp ON ss.IDTIPOSERVICIOS = tp.IDTIPOSERVICIOS");
		
		sql.WHERE("ss.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("ss.FECHABAJA IS NULL");
		
		sql.ORDER_BY("ss.IDTIPOSERVICIOS");
		sql.ORDER_BY("ss.IDSERVICIO");
		sql.ORDER_BY("ss.IDINSTITUCION");

		return sql.toString();
	}

	public String searchTiposServiciosHistorico(String idioma, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("ss.IDTIPOSERVICIOS");
		sql.SELECT("substr(f_siga_getrecurso (tp.DESCRIPCION ,'" + idioma +"'), 0, 30) AS DESCRIPCION_TIPO");
		sql.SELECT("ss.IDSERVICIO");
		sql.SELECT("ss.DESCRIPCION");
		sql.SELECT("ss.FECHABAJA");
		
		sql.FROM("PYS_SERVICIOS ss");
		
		sql.JOIN("PYS_TIPOSERVICIOS tp ON ss.IDTIPOSERVICIOS = tp.IDTIPOSERVICIOS");
		
		sql.WHERE("ss.IDINSTITUCION = '" + idInstitucion + "'");
		
		sql.ORDER_BY("ss.IDTIPOSERVICIOS");
		sql.ORDER_BY("ss.IDSERVICIO");
		sql.ORDER_BY("ss.IDINSTITUCION");

		return sql.toString();
	}

	public String comboTiposServicios(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOSERVICIOS AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_TIPOSERVICIOS");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	public String searchTiposServiciosByIdCategoriaMultiple(String idioma, Short idInstitucion, String idCategoria) { //En realidad busca los servicios con ese idcategoria
		SQL sql = new SQL();
		
		sql.SELECT("IDTIPOSERVICIOS || '-' || IDSERVICIO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_SERVICIOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOSERVICIOS IN ( " + idCategoria + ")");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}
	
	public String searchTiposServiciosByIdCategoria(String idioma, Short idInstitucion, String idCategoria) { //En realidad busca los servicios con ese idcategoria
		SQL sql = new SQL();
		
		sql.SELECT("IDSERVICIO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_SERVICIOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOSERVICIOS IN ( " + idCategoria + ")");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}

	public String getIndiceMaxServicio(List<TiposServiciosItem> listadoServicios, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL((MAX(IDSERVICIO) + 1),1) AS IDSERVICIO");
		
		sql.FROM("PYS_SERVICIOS");
		
		sql.WHERE("IDTIPOSERVICIOS ='" + listadoServicios.get(0).getIdtiposervicios() + "'");;
		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		
		return sql.toString();
	}
	
	public String activarDesactivarServicio(AdmUsuarios usuario, Short idInstitucion, TiposServiciosItem servicio) {
		SQL sql = new SQL();
		
		sql.UPDATE("PYS_SERVICIOS");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '"+ usuario.getIdusuario() + "'");
		
		if(servicio.getFechabaja() != null) {
			sql.SET("FECHABAJA = NULL");
		}
		else{
			sql.SET("FECHABAJA = SYSDATE");
		}
		
		sql.WHERE("IDSERVICIO = '" + servicio.getIdservicio() + "'");
		sql.WHERE("IDTIPOSERVICIOS = '" + servicio.getIdtiposervicios() + "'");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		return sql.toString();
	}
	
	public String obtenerCodigosPorColegioServicios(Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" CODIGOEXT");
		
		sql.FROM(" PYS_SERVICIOSINSTITUCION");
		
		sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		
		return sql.toString();
	}
}
