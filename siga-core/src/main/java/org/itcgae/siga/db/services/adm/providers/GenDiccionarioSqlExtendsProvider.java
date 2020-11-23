package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.EtiquetaSearchDTO;
import org.itcgae.siga.db.mappers.GenDiccionarioSqlProvider;

public class GenDiccionarioSqlExtendsProvider extends GenDiccionarioSqlProvider{

	
	public String getLabelLenguage() {
		SQL sql = new SQL();
		
		sql.SELECT("L.IDLENGUAJE");
		sql.SELECT("C.DESCRIPCION");
		sql.FROM("ADM_LENGUAJES L");
		
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS C on L.DESCRIPCION = C.idRecurso");
		sql.WHERE("C.IDLENGUAJE = '1'");
		sql.WHERE("CODIGOEJIS is not null");
		sql.WHERE("L.FECHA_BAJA is null");
		
		return sql.toString();
	}
	
	
	

	public String searchLabels(int numPagina, EtiquetaSearchDTO etiquetaSearchDTO) {
		SQL sql = new SQL();
		
		sql.SELECT("REC.IDRECURSO");
		sql.SELECT("(Select DESCRIPCION From GEN_DICCIONARIO REC2 WHERE REC2.IDLENGUAJE = '" + etiquetaSearchDTO.getIdiomaBusqueda()  + "' AND REC2.IDRECURSO = REC.IDRECURSO) AS DESCRIPCIONBUSCAR");
		sql.SELECT("(Select DESCRIPCION From GEN_DICCIONARIO REC2 WHERE REC2.IDLENGUAJE = '" + etiquetaSearchDTO.getIdiomaTraduccion()  + "' AND REC2.IDRECURSO = REC.IDRECURSO) AS DESCRIPCIONTRADUCIR");
		sql.SELECT("'" + etiquetaSearchDTO.getIdiomaBusqueda() + "' as IDLENGUAJEBUSCAR");
		sql.SELECT("'" + etiquetaSearchDTO.getIdiomaTraduccion() + "' as IDLENGUAJETRADUCIR ");
		sql.FROM("GEN_DICCIONARIO REC ");
		sql.WHERE("IDLENGUAJE = '" + etiquetaSearchDTO.getIdiomaBusqueda() + "'");
		if(!etiquetaSearchDTO.getDescripcion().equalsIgnoreCase("") && !etiquetaSearchDTO.getDescripcion().equals(null)) {
			sql.WHERE("UPPER(descripcion)  like UPPER('%" + etiquetaSearchDTO.getDescripcion() + "%')");
		}
		
		return sql.toString();
	}
}


