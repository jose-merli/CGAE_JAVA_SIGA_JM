package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaSqlProvider;

public class ScsTipoasistenciaSqlExtendsProvider extends ScsTipoasistenciaSqlProvider {

	public String getComboAsistencia(String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipoasistencia.idtipoasistencia");
		sql.SELECT("asisdes.descripcion");
		sql.FROM("SCS_TIPOASISTENCIA tipoasistencia");
		sql.INNER_JOIN("gen_recursos_catalogos asisdes on asisdes.IDRECURSO = tipoasistencia.descripcion and asisdes.idlenguaje = '" + idLenguaje + "'" );
		sql.ORDER_BY("asisdes.descripcion");

		return sql.toString();
	}
	
	public String getTiposGuardia(String idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDTIPOGUARDIA as IDTIPOASISTENCIA");
		sql.SELECT("CATGUARDIA.DESCRIPCION");
		sql.FROM("SCS_TIPOSGUARDIAS  GUARDIA");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catguardia on (catguardia.idrecurso = guardia.descripcion and catguardia.idlenguaje = '"+idLenguaje+"')");
		sql.WHERE("FECHA_BAJA IS NULL");

		return sql.toString();
	}
	
	public String updateTiposAsistencia(TiposAsistenciaItem tiposAsistenciasItem,Short idInstitucion) {

		SQL sql = new SQL();
//		sql.UPDATE(table)
//		sql.SET(sets)

		return sql.toString();
	}
	
	
	
	
	public String searchTiposAsistencia(boolean historico, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("ASIS.IDINSTITUCION");
		sql.SELECT("ASIS.IDTIPOASISTENCIACOLEGIO");
		sql.SELECT("ASIS.IMPORTE");
		sql.SELECT("ASIS.IMPORTEMAXIMO");
		sql.SELECT("ASIS.VISIBLEMOVIL");
		sql.SELECT("ASIS.FECHA_BAJA");
		sql.SELECT("CAT.DESCRIPCION AS TIPOASISTENCIA");
		sql.SELECT("ASIS.PORDEFECTO");
		sql.SELECT("DECODE(ASIS.PORDEFECTO,1,'SI','NO') AS PORDEFECTOSTRING");
		sql.SELECT("LISTAGG(CATGUARDIA.DESCRIPCION, ', ') WITHIN GROUP (ORDER BY ASIS.IDTIPOASISTENCIACOLEGIO)  AS TIPOSGUARDIA");
		sql.SELECT("LISTAGG(GUARDIA.IDTIPOGUARDIA, ', ') WITHIN GROUP (ORDER BY ASIS.IDTIPOASISTENCIACOLEGIO)  AS IDTIPOSGUARDIA");
	
		sql.FROM("SCS_TIPOASISTENCIACOLEGIO ASIS");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ASIS.DESCRIPCION AND CAT.IDLENGUAJE = 1)");
		sql.LEFT_OUTER_JOIN(
				"SCS_TIPOASISTENCIAGUARDIA ASISGUARDIA ON ASIS.IDTIPOASISTENCIACOLEGIO = ASISGUARDIA.IDTIPOASISTENCIACOLEGIO AND ASIS.IDINSTITUCION = ASISGUARDIA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"SCS_TIPOSGUARDIAS GUARDIA ON GUARDIA.IDTIPOGUARDIA = ASISGUARDIA.IDTIPOGUARDIA");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS CATGUARDIA ON (CATGUARDIA.IDRECURSO = GUARDIA.DESCRIPCION AND CATGUARDIA.IDLENGUAJE = 1)");
		sql.WHERE("ASIS.IDINSTITUCION = '"+idInstitucion+"'");
		if(!historico) {
			sql.WHERE("ASIS.FECHA_BAJA IS NULL");
		}
		sql.GROUP_BY("ASIS.IDINSTITUCION, ASIS.IDTIPOASISTENCIACOLEGIO, ASIS.IMPORTE, ASIS.IMPORTEMAXIMO, ASIS.VISIBLEMOVIL,ASIS.FECHA_BAJA, CAT.DESCRIPCION, ASIS.PORDEFECTO, DECODE(ASIS.PORDEFECTO,1,'SI','NO')");
		sql.ORDER_BY("TIPOASISTENCIA");
		return sql.toString();
	}
	
	public String searchTiposAsistenciaPorDefecto(boolean historico, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("ASIS.IDINSTITUCION");
		sql.SELECT("ASIS.IDTIPOASISTENCIACOLEGIO");
		sql.SELECT("ASIS.IMPORTE");
		sql.SELECT("ASIS.IMPORTEMAXIMO");
		sql.SELECT("ASIS.VISIBLEMOVIL");
		sql.SELECT("ASIS.FECHA_BAJA");
		sql.SELECT("CAT.DESCRIPCION AS TIPOASISTENCIA");
		sql.SELECT("ASIS.PORDEFECTO");
		sql.SELECT("DECODE(ASIS.PORDEFECTO,1,'SI','NO') AS PORDEFECTOSTRING");
		sql.SELECT("LISTAGG(CATGUARDIA.DESCRIPCION, ', ') WITHIN GROUP (ORDER BY ASIS.IDTIPOASISTENCIACOLEGIO)  AS TIPOSGUARDIA");
		sql.SELECT("LISTAGG(GUARDIA.IDTIPOGUARDIA, ', ') WITHIN GROUP (ORDER BY ASIS.IDTIPOASISTENCIACOLEGIO)  AS IDTIPOSGUARDIA");
	
		sql.FROM("SCS_TIPOASISTENCIACOLEGIO ASIS");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = ASIS.DESCRIPCION AND CAT.IDLENGUAJE = 1)");
		sql.LEFT_OUTER_JOIN(
				"SCS_TIPOASISTENCIAGUARDIA ASISGUARDIA ON ASIS.IDTIPOASISTENCIACOLEGIO = ASISGUARDIA.IDTIPOASISTENCIACOLEGIO AND ASIS.IDINSTITUCION = ASISGUARDIA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"SCS_TIPOSGUARDIAS GUARDIA ON GUARDIA.IDTIPOGUARDIA = ASISGUARDIA.IDTIPOGUARDIA");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS CATGUARDIA ON (CATGUARDIA.IDRECURSO = GUARDIA.DESCRIPCION AND CATGUARDIA.IDLENGUAJE = 1)");
		sql.WHERE("ASIS.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("ASIS.PORDEFECTO ='1'");
		
		if(!historico) {
			sql.WHERE("ASIS.FECHA_BAJA IS NULL");
		}
		sql.GROUP_BY("ASIS.IDINSTITUCION, ASIS.IDTIPOASISTENCIACOLEGIO, ASIS.IMPORTE, ASIS.IMPORTEMAXIMO, ASIS.VISIBLEMOVIL,ASIS.FECHA_BAJA, CAT.DESCRIPCION, ASIS.PORDEFECTO, DECODE(ASIS.PORDEFECTO,1,'SI','NO')");

		return sql.toString();
	}
	
	
}
