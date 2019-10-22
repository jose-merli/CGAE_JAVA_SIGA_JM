package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;

public class ScsTipoactuacionSqlExtendsProvider extends ScsTipoactuacionSqlProvider {

	public String getComboActuacion(String idTipoAsistencia, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipoactuacion.idtipoactuacion");
		sql.SELECT("actuades.descripcion");
		sql.FROM("SCS_TIPOACTUACION tipoactuacion");
		sql.INNER_JOIN("gen_recursos_catalogos actuades on actuades.IDRECURSO = tipoactuacion.descripcion and actuades.idlenguaje = '" + idLenguaje + "'" );
		sql.WHERE("tipoactuacion.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("tipoactuacion.idtipoasistencia = '" + idTipoAsistencia + "'");
		sql.ORDER_BY("actuades.descripcion");

		return sql.toString();
	}
	
	
	public String searchTiposActuacion(boolean historico, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("ACTUACION.idinstitucion");
		sql.SELECT("ACTUACION.IDTIPOACTUACION");
		sql.SELECT("ACTUACION.importe");
		sql.SELECT("ACTUACION.importemaximo");
		sql.SELECT("ACTUACION.fechabaja");
		sql.SELECT("cat.descripcion as DESCRIPCIONTIPOACTUACION");
		sql.SELECT("LISTAGG(catasis.descripcion, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS DESCRIPCIONTIPOASISTENCIA");
		sql.SELECT("LISTAGG(ACTUACION.idtipoasistencia, ', ') WITHIN GROUP (ORDER BY ACTUACION.IDTIPOACTUACION)  AS IDTIPOASISTENCIA");
	
		sql.FROM("SCS_TIPOACTUACION ACTUACION");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS cat on (cat.idrecurso = ACTUACION.descripcion and cat.idlenguaje = '"+idLenguaje+"' AND cat.NOMBRETABLA = 'SCS_TIPOACTUACION' AND CAT.IDINSTITUCION = ACTUACION.IDINSTITUCION)");
		sql.INNER_JOIN(
				"SCS_TIPOASISTENCIACOLEGIO asis on asis.idtipoasistenciacolegio = ACTUACION.idtipoasistenciA and asis.idinstitucion = ACTUACION.idinstitucion");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS catasis on (catasis.idrecurso = asis.descripcion and catasis.idlenguaje = '"+idLenguaje+"')");
		sql.WHERE("ACTUACION.IDINSTITUCION = '"+idInstitucion+"'");
		if(!historico) {
			sql.WHERE("ACTUACION.FECHABAJA IS NULL");
		}
		sql.GROUP_BY("ACTUACION.idinstitucion, ACTUACION.IDTIPOACTUACION, ACTUACION.importe, ACTUACION.importemaximo, ACTUACION.fechabaja, cat.descripcion");
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
	public String getTiposAsistencia(String idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDTIPOASISTENCIACOLEGIO, CAT.DESCRIPCION");
		sql.FROM("SCS_TIPOASISTENCIACOLEGIO asis");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat on (cat.idrecurso = asis.descripcion and cat.idlenguaje = '"+idLenguaje+"')");
		sql.WHERE("ASIS.IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE("FECHA_BAJA IS NULL");

		return sql.toString();
	}
	
	public String getIdTipoactuacion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTIPOACTUACION) AS IDTIPOACTUACION");
		sql.FROM("SCS_TIPOACTUACION");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}

}
