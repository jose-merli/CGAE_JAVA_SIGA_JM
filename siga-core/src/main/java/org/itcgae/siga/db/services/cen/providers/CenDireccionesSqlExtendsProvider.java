package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.db.mappers.CenComponentesSqlProvider;

public class CenDireccionesSqlExtendsProvider extends CenComponentesSqlProvider{
	
	

	public String selectDireccionesWs(String idPersona) {		
		SQL sql = new SQL();
		

		sql.SELECT_DISTINCT("CAT.DESCRIPCION AS TIPODIRECCION");
		sql.SELECT("DIRECCION.IDDIRECCION");
		sql.SELECT("DIRECCION.CODIGOPOSTAL");
		sql.SELECT("TO_CHAR(DIRECCION.FECHABAJA,'DD/MM/YYYY') AS FECHABAJA");
		sql.SELECT("DIRECCION.IDINSTITUCION");
		sql.SELECT("DIRECCION.DOMICILIO ");
		sql.SELECT("DIRECCION.IDPOBLACION");
		sql.SELECT("DIRECCION.IDPROVINCIA");
		sql.SELECT("DIRECCION.IDPAIS");
		sql.SELECT("DIRECCION.TELEFONO1");
		sql.SELECT("DIRECCION.FAX1");
		sql.SELECT("DIRECCION.MOVIL");
		sql.SELECT("DIRECCION.PAGINAWEB");
		sql.SELECT("DIRECCION.CORREOELECTRONICO");
		sql.SELECT("PAIS.CODIGOEXT AS IDEXTERNOPAIS");
		sql.SELECT("CATPAIS.DESCRIPCION AS NOMBREPAIS");
		sql.SELECT("POBLACION.CODIGOEXT AS IDEXTERNOPOBLACION");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.CODIGOEXT AS IDEXTERNOPROVINCIA");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");
		sql.FROM("CEN_DIRECCIONES DIRECCION");
		sql.INNER_JOIN(" CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON (TIPODIRECCION.IDDIRECCION = DIRECCION.IDDIRECCION AND TIPODIRECCION.IDPERSONA = DIRECCION.IDPERSONA AND  TIPODIRECCION.IDINSTITUCION = DIRECCION.IDINSTITUCION) ");
		sql.INNER_JOIN(" CEN_TIPODIRECCION TIPO ON TIPO.IDTIPODIRECCION = TIPODIRECCION.IDTIPODIRECCION");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS  CAT ON (CAT.IDRECURSO = TIPO.DESCRIPCION AND CAT.IDLENGUAJE = '1')");
		sql.LEFT_OUTER_JOIN("CEN_PAIS PAIS ON PAIS.IDPAIS = DIRECCION.IDPAIS");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS  CATPAIS ON (CATPAIS.IDRECURSO = PAIS.NOMBRE AND CATPAIS.IDLENGUAJE = '1')");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = DIRECCION.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = DIRECCION.IDPOBLACION");
		sql.WHERE("DIRECCION.IDPERSONA = '"+idPersona+"'");
		sql.WHERE("DIRECCION.FECHABAJA is null");
		sql.WHERE("TIPO.IDTIPODIRECCION = '3'");
		sql.ORDER_BY("CAT.DESCRIPCION, DIRECCION.IDDIRECCION");
		return sql.toString();
	}

	
	public String selectDirecciones(DatosDireccionesSearchDTO datosDireccionesSearchDTO,String idInstitucion) {
		
		SQL sqlPrincipal = new SQL();
		SQL sql = new SQL();
		
		
		sql.SELECT_DISTINCT(" CAT.DESCRIPCION");
		sql.SELECT("DIRECCION.IDDIRECCION");
		sql.SELECT("TIPO.IDTIPODIRECCION");
		sql.SELECT("DIRECCION.CODIGOPOSTAL");
		sql.SELECT("TO_CHAR(DIRECCION.FECHABAJA,'DD/MM/YYYY') AS FECHABAJA");
		sql.SELECT("DIRECCION.IDINSTITUCION");
		sql.SELECT("DIRECCION.DOMICILIO ");
		sql.SELECT("(DIRECCION.DOMICILIO || ' ' || DIRECCION.CODIGOPOSTAL || ' ' ||POBLACION.NOMBRE || ' ' ||PROVINCIAS.NOMBRE || ' ' || DECODE(DIRECCION.IDPAIS,191,'',CATPAIS.DESCRIPCION) ) AS DOMICILIOLISTA");
		sql.SELECT("DIRECCION.IDPOBLACION");
		sql.SELECT("DIRECCION.IDPROVINCIA");
		sql.SELECT("DIRECCION.IDPAIS");
		sql.SELECT("DIRECCION.TELEFONO1");
		sql.SELECT("DIRECCION.FAX1");
		sql.SELECT("DIRECCION.MOVIL");
		sql.SELECT("DIRECCION.FECHAMODIFICACION");
		sql.SELECT("DIRECCION.PAGINAWEB");
		sql.SELECT("DIRECCION.CORREOELECTRONICO");
		sql.SELECT("PAIS.CODIGOEXT AS IDEXTERNOPAIS");
		sql.SELECT("CATPAIS.DESCRIPCION AS NOMBREPAIS");
		sql.SELECT("POBLACION.CODIGOEXT AS IDEXTERNOPOBLACION");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.CODIGOEXT AS IDEXTERNOPROVINCIA");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");
		sql.FROM("CEN_DIRECCIONES DIRECCION");
		sql.INNER_JOIN(" CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON (TIPODIRECCION.IDDIRECCION = DIRECCION.IDDIRECCION AND TIPODIRECCION.IDPERSONA = DIRECCION.IDPERSONA AND  TIPODIRECCION.IDINSTITUCION = DIRECCION.IDINSTITUCION) ");
		sql.INNER_JOIN(" CEN_TIPODIRECCION TIPO ON TIPO.IDTIPODIRECCION = TIPODIRECCION.IDTIPODIRECCION");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS  CAT ON (CAT.IDRECURSO = TIPO.DESCRIPCION AND CAT.IDLENGUAJE = '1')");
		sql.LEFT_OUTER_JOIN("CEN_PAIS PAIS ON PAIS.IDPAIS = DIRECCION.IDPAIS");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS  CATPAIS ON (CATPAIS.IDRECURSO = PAIS.NOMBRE AND CATPAIS.IDLENGUAJE = '1')");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = DIRECCION.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = DIRECCION.IDPOBLACION");
		sql.WHERE("DIRECCION.IDPERSONA = '"+datosDireccionesSearchDTO.getIdPersona()+"'");
		if (!datosDireccionesSearchDTO.getHistorico()) {
			sql.WHERE("DIRECCION.FECHABAJA is null");
		}
		sql.WHERE("DIRECCION.IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("CAT.DESCRIPCION, DIRECCION.IDDIRECCION  ");

		
		
		
		
		
		
		sqlPrincipal.SELECT_DISTINCT("LISTAGG(DIRECCIONES.DESCRIPCION, ';') WITHIN GROUP (ORDER BY DIRECCIONES.DESCRIPCION)  OVER (PARTITION BY DIRECCIONES.IDDIRECCION) AS TIPODIRECCION");
		sqlPrincipal.SELECT_DISTINCT("LISTAGG(DIRECCIONES.IDTIPODIRECCION, ';') WITHIN GROUP (ORDER BY DIRECCIONES.IDTIPODIRECCION)  OVER (PARTITION BY DIRECCIONES.IDDIRECCION) AS IDTIPODIRECCIONLIST");
		sqlPrincipal.SELECT("IDDIRECCION");
		sqlPrincipal.SELECT("CODIGOPOSTAL");
		sqlPrincipal.SELECT("FECHABAJA");
		sqlPrincipal.SELECT("IDINSTITUCION");
		sqlPrincipal.SELECT("DOMICILIOLISTA");
		sqlPrincipal.SELECT("DOMICILIO ");
		sqlPrincipal.SELECT("IDPOBLACION");
		sqlPrincipal.SELECT("IDPROVINCIA");
		sqlPrincipal.SELECT("IDPAIS");
		sqlPrincipal.SELECT("TELEFONO1");
		sqlPrincipal.SELECT("FAX1");
		sqlPrincipal.SELECT("MOVIL");
		sqlPrincipal.SELECT("FECHAMODIFICACION");
		sqlPrincipal.SELECT("PAGINAWEB");
		sqlPrincipal.SELECT("CORREOELECTRONICO");
		sqlPrincipal.SELECT("IDEXTERNOPAIS");
		sqlPrincipal.SELECT("NOMBREPAIS");
		sqlPrincipal.SELECT("IDEXTERNOPOBLACION");
		sqlPrincipal.SELECT("NOMBREPOBLACION");
		sqlPrincipal.SELECT("IDEXTERNOPROVINCIA");
		sqlPrincipal.SELECT("NOMBREPROVINCIA");
		//sqlPrincipal.FROM(sql.toString());
		
		sqlPrincipal.FROM( "(" + sql.toString() + ") DIRECCIONES");
		
		return sqlPrincipal.toString();
	}
	
	
	public String selectNewIdDireccion(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("MAX(DIRECCION.IDDIRECCION) + 1 AS IDDIRECCION");
		sql.FROM("CEN_DIRECCIONES DIRECCION");
		sql.WHERE("DIRECCION.IDPERSONA = '"+idPersona+"'");
		sql.WHERE("DIRECCION.IDINSTITUCION = '"+idInstitucion+"'");
		
		return sql.toString();
	}

}
