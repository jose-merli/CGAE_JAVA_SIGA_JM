package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosSqlProvider;

public class GenRecursosCatalogosSqlExtendsProvider extends GenRecursosCatalogosSqlProvider{

	public String getCatalogEntity(String idInstitucion){
		SQL sql = new SQL();
		
		sql.SELECT("distinct NOMBRETABLA");
		sql.FROM("GEN_RECURSOS_CATALOGOS");
		sql.WHERE("IDINSTITUCION  = '" + idInstitucion + "'");
		
		return sql.toString();
	}
	
	
	public String getCatalogSearch(int numPagina, MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT REC.IDRECURSO");
		sql.SELECT("NOMBRETABLA");
		sql.SELECT("CONCAT(IDRECURSO, CONCAT(' ',REC.IDRECURSOALIAS)) AS IDRECURSOALIAS ");
		sql.SELECT(" (Select DESCRIPCION From GEN_RECURSOS_CATALOGOS REC2 WHERE REC2.IDLENGUAJE = '" + multiidiomaCatalogoSearchDTO.getIdiomaBusqueda()  + "' AND REC2.IDRECURSO = REC.IDRECURSO) AS DESCRIPCIONBUSCAR");
		sql.SELECT(" (Select DESCRIPCION From GEN_RECURSOS_CATALOGOS REC2 WHERE REC2.IDLENGUAJE = '" + multiidiomaCatalogoSearchDTO.getIdiomaTraduccion() + "' AND REC2.IDRECURSO = REC.IDRECURSO) AS DESCRIPCIONTRADUCIR ");
		sql.SELECT(" '" +  multiidiomaCatalogoSearchDTO.getIdiomaBusqueda()  + "' as IDLENGUAJEBUSCAR ");
		sql.SELECT(" '" + multiidiomaCatalogoSearchDTO.getIdiomaTraduccion() + "' as IDLENGUAJETRADUCIR ");
		sql.FROM(" GEN_RECURSOS_CATALOGOS REC ");
		sql.WHERE(" IDLENGUAJE = '"+ multiidiomaCatalogoSearchDTO.getIdiomaBusqueda()  + "'");
		sql.WHERE(" NOMBRETABLA = '" + multiidiomaCatalogoSearchDTO.getNombreTabla() + "'");
		//Se comenta la institución ya que en esta tabla no aplica este filtro: sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY(" DESCRIPCIONBUSCAR ");
		
		return sql.toString();
	}
}
