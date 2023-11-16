package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;

public class ScsTipoactuacionSqlExtendsProvider extends ScsTipoactuacionSqlProvider {

	public String getComboActuacion(String idTipoAsistencia, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipoactuacion.idtipoactuacion");
		sql.SELECT("f_siga_getrecurso(tipoactuacion.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOACTUACION tipoactuacion");
		sql.WHERE("tipoactuacion.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("tipoactuacion.idtipoasistencia = '" + idTipoAsistencia + "'");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	
	public String searchTiposActuacion(boolean historico, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("ACTUACION.idinstitucion");
		sql.SELECT("ACTUACION.IDTIPOACTUACION");
		sql.SELECT("ACTUACION.importe");
		sql.SELECT("ACTUACION.importemaximo");
		sql.SELECT("ACTUACION.comisariajuzgadopordefecto");
		sql.SELECT("to_date(ACTUACION.fechabaja,'DD/MM/RRRR') as fechabaja ");
		sql.SELECT("f_siga_getrecurso(ACTUACION.descripcion,"+idLenguaje+") DESCRIPCIONTIPOACTUACION");
		sql.SELECT("LISTAGG(f_siga_getrecurso(asis.descripcion,"+idLenguaje+"), ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS DESCRIPCIONTIPOASISTENCIA");
		sql.SELECT("LISTAGG(ACTUACION.idtipoasistencia, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS IDTIPOASISTENCIA");
		sql.FROM("SCS_TIPOACTUACION ACTUACION");
		sql.INNER_JOIN(
				"SCS_TIPOASISTENCIACOLEGIO asis on asis.idtipoasistenciacolegio = ACTUACION.idtipoasistenciA and asis.idinstitucion = ACTUACION.idinstitucion");
		sql.WHERE("ACTUACION.IDINSTITUCION = '"+idInstitucion+"'");
		if(!historico) {
			sql.WHERE("ACTUACION.FECHABAJA IS NULL");
		}
		sql.GROUP_BY("ACTUACION.idinstitucion, ACTUACION.IDTIPOACTUACION, ACTUACION.importe, ACTUACION.importemaximo,actuacion.comisariajuzgadopordefecto, to_date(ACTUACION.fechabaja,'DD/MM/RRRR'), cat.descripcion");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	public String searchTipoActuacionPorDefecto(String idLenguaje, Short idInstitucion, String descripcionTipoAsistencia, String juzgadoComisaria) {

		SQL sql = new SQL();
		String sqlPrincipal = "";

		sql.SELECT("ACTUACION.idinstitucion");
		sql.SELECT("ACTUACION.IDTIPOACTUACION");
		sql.SELECT("ACTUACION.importe");
		sql.SELECT("ACTUACION.importemaximo");
		sql.SELECT("ACTUACION.comisariajuzgadopordefecto");
		sql.SELECT("to_date(ACTUACION.fechabaja,'DD/MM/RRRR') as fechabaja ");
		sql.SELECT("cat.descripcion as DESCRIPCIONTIPOACTUACION");
		sql.SELECT("LISTAGG(catasis.descripcion, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS DESCRIPCIONTIPOASISTENCIA");
		sql.SELECT("LISTAGG(ACTUACION.idtipoasistencia, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS IDTIPOASISTENCIA");
	
		sql.FROM("SCS_TIPOACTUACION ACTUACION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS cat on (cat.idrecurso = ACTUACION.descripcion and cat.idlenguaje = '"+idLenguaje+"' AND cat.NOMBRETABLA = 'SCS_TIPOACTUACION' AND CAT.IDINSTITUCION = ACTUACION.IDINSTITUCION)");
		sql.INNER_JOIN(
				"SCS_TIPOASISTENCIACOLEGIO asis on asis.idtipoasistenciacolegio = ACTUACION.idtipoasistenciA and asis.idinstitucion = ACTUACION.idinstitucion");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS catasis on (catasis.idrecurso = asis.descripcion and catasis.idlenguaje = '"+idLenguaje+"')");
		sql.WHERE("ACTUACION.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("ACTUACION.FECHABAJA IS NULL");
		sql.GROUP_BY("ACTUACION.idinstitucion, ACTUACION.IDTIPOACTUACION, ACTUACION.importe, ACTUACION.importemaximo,actuacion.comisariajuzgadopordefecto, to_date(ACTUACION.fechabaja,'DD/MM/RRRR'), cat.descripcion");
		sql.ORDER_BY("cat.descripcion");
		
		sqlPrincipal = "SELECT PRINCIPAL.idtipoactuacion FROM (" + sql.toString() + ") PRINCIPAL WHERE PRINCIPAL.descripciontipoasistencia LIKE '%" + descripcionTipoAsistencia + "%' AND COMISARIAJUZGADOPORDEFECTO IS NOT NULL AND COMISARIAJUZGADOPORDEFECTO = '" + juzgadoComisaria + "'";

		return sqlPrincipal;
	}
	
	public String searchTipoActuacionPorDefectoIdTipoAsistencia(String idLenguaje, Short idInstitucion, String idTipoAsistencia, String juzgadoComisaria) {

		SQL sql = new SQL();
		String sqlPrincipal = "";

		sql.SELECT("ACTUACION.idinstitucion");
		sql.SELECT("ACTUACION.IDTIPOACTUACION");
		sql.SELECT("ACTUACION.importe");
		sql.SELECT("ACTUACION.importemaximo");
		sql.SELECT("ASIS.idtipoasistenciacolegio");
		sql.SELECT("ACTUACION.comisariajuzgadopordefecto");
		sql.SELECT("to_date(ACTUACION.fechabaja,'DD/MM/RRRR') as fechabaja ");
		sql.SELECT("cat.descripcion as DESCRIPCIONTIPOACTUACION");
		sql.SELECT("LISTAGG(catasis.descripcion, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS DESCRIPCIONTIPOASISTENCIA");
		sql.SELECT("LISTAGG(ACTUACION.idtipoasistencia, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS IDTIPOASISTENCIA");
	
		sql.FROM("SCS_TIPOACTUACION ACTUACION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS cat on (cat.idrecurso = ACTUACION.descripcion and cat.idlenguaje = '"+idLenguaje+"' AND cat.NOMBRETABLA = 'SCS_TIPOACTUACION' AND CAT.IDINSTITUCION = ACTUACION.IDINSTITUCION)");
		sql.INNER_JOIN(
				"SCS_TIPOASISTENCIACOLEGIO asis on asis.idtipoasistenciacolegio = ACTUACION.idtipoasistenciA and asis.idinstitucion = ACTUACION.idinstitucion");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS catasis on (catasis.idrecurso = asis.descripcion and catasis.idlenguaje = '"+idLenguaje+"')");
		sql.WHERE("ACTUACION.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("ACTUACION.FECHABAJA IS NULL");
		sql.GROUP_BY("ACTUACION.idinstitucion, ACTUACION.IDTIPOACTUACION, ASIS.idtipoasistenciacolegio, ACTUACION.importe, ACTUACION.importemaximo,actuacion.comisariajuzgadopordefecto, to_date(ACTUACION.fechabaja,'DD/MM/RRRR'), cat.descripcion");
		sql.ORDER_BY("cat.descripcion");
		
		sqlPrincipal = "SELECT PRINCIPAL.idtipoactuacion FROM (" + sql.toString() + ") PRINCIPAL WHERE PRINCIPAL.idtipoasistenciacolegio = '" + idTipoAsistencia + "' AND COMISARIAJUZGADOPORDEFECTO IS NOT NULL AND COMISARIAJUZGADOPORDEFECTO = '" + juzgadoComisaria + "'";

		return sqlPrincipal;
	}
	
	public String getTiposAsistencia(String idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("IDTIPOASISTENCIACOLEGIO");
		sql.SELECT("f_siga_getrecurso(asis.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOASISTENCIACOLEGIO asis");
		sql.WHERE("ASIS.IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE("FECHA_BAJA IS NULL");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	public String getIdTipoactuacion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTIPOACTUACION) AS IDTIPOACTUACION");
		sql.FROM("SCS_TIPOACTUACION");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}

}
