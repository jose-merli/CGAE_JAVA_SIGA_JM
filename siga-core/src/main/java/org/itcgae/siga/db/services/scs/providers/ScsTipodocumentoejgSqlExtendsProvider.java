package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.DocumentacionEjgItem;
import org.itcgae.siga.db.mappers.ScsDocumentoejgSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipodocumentoejgSqlProvider;

public class ScsTipodocumentoejgSqlExtendsProvider extends ScsTipodocumentoejgSqlProvider {

	
	public String searchDocumento(DocumentacionEjgItem documentoEjgItem, String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT("tipodocumento.idinstitucion");
		sql.SELECT("tipodocumento.idtipodocumentoejg AS IDTIPODOC");
		sql.SELECT("tipodocumento.abreviatura");
		sql.SELECT("tipodocumento.fechabaja AS FECHABAJA");
		sql.SELECT("documento.abreviatura AS ABREVIATURADOC");
		sql.SELECT("tipodocumento.descripcion AS CODIGODESCRIPCION");
		sql.SELECT("CATTIPODOCUMENTO.descripcion AS DESCRIPCIONTIPO");
		sql.SELECT("tipodocumento.codigoext");
		sql.SELECT("CATDOCUMENTO.descripcion");
		sql.FROM("SCS_TIPODOCUMENTOEJG TIPODOCUMENTO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CATTIPODOCUMENTO ON CATTIPODOCUMENTO.IDRECURSO = TIPODOCUMENTO.DESCRIPCION AND CATTIPODOCUMENTO.IDLENGUAJE = '" + idLenguaje + "'");
		sql.LEFT_OUTER_JOIN("SCS_DOCUMENTOEJG DOCUMENTO ON DOCUMENTO.IDTIPODOCUMENTOEJG = TIPODOCUMENTO.IDTIPODOCUMENTOEJG  AND DOCUMENTO.IDINSTITUCION = TIPODOCUMENTO.IDINSTITUCION AND DOCUMENTO.FECHABAJA IS NULL");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CATDOCUMENTO ON CATDOCUMENTO.IDRECURSO = DOCUMENTO.DESCRIPCION AND CATDOCUMENTO.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("tipodocumento.idinstitucion = '" + documentoEjgItem.getIdInstitucion() + "'");


		if(documentoEjgItem.getabreviaturaTipoDoc() != null && documentoEjgItem.getabreviaturaTipoDoc() != "") {
			sql.WHERE("UPPER(TIPODOCUMENTO.ABREVIATURA ) like UPPER('%"+ documentoEjgItem.getabreviaturaTipoDoc() + "%')");
		}
		
		if(documentoEjgItem.getdescripcionDoc() != null && documentoEjgItem.getdescripcionDoc() != "") {
			sql.WHERE("UPPER(CATDOCUMENTO.DESCRIPCION) like UPPER('%"+ documentoEjgItem.getdescripcionDoc() + "%')");
		}
		
		if(documentoEjgItem.getAbreviatura() != null && documentoEjgItem.getAbreviatura() != "") {
			sql.WHERE("UPPER(DOCUMENTO.ABREVIATURA) like UPPER('%"+ documentoEjgItem.getAbreviatura() + "%')");
		}
		if(documentoEjgItem.getdescripcionTipoDoc() != null && documentoEjgItem.getdescripcionTipoDoc() != "") {
			sql.WHERE("UPPER(CATTIPODOCUMENTO.DESCRIPCION) like UPPER('%"+ documentoEjgItem.getdescripcionTipoDoc() + "%')");
		}
		
		if(!documentoEjgItem.isHistorico()) {
			sql.WHERE("TIPODOCUMENTO.fechabaja is null");
		}
		
		sql.ORDER_BY("documento.abreviatura");
		//sql.ORDER_BY("tipodocumento.idtipodocumentoejg");
		return sql.toString();
	}
	
	public String getIdTipoDocumentoEjg(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTIPODOCUMENTOEJG) AS IDTIPODOCUMENTOEJG");
		sql.FROM("SCS_TIPODOCUMENTOEJG");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		return sql.toString();
	}
	
}
