package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.DocumentacionEjgItem;
import org.itcgae.siga.db.mappers.ScsDocumentoejgSqlProvider;

public class ScsDocumentoejgSqlExtendsProvider extends ScsDocumentoejgSqlProvider {

	
	public String searchDocumento(DocumentacionEjgItem documentoEjgItem, String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT("documento.idinstitucion");
		sql.SELECT("documento.idtipodocumentoejg AS IDTIPODOC");
		sql.SELECT("documento.iddocumentoejg AS IDDOCUMENTO");
		sql.SELECT("documento.abreviatura AS ABREVIATURADOC");
		sql.SELECT("documento.codigoext AS DOCCODIGOEXT");
		sql.SELECT("catdocumento.descripcion AS DESCRIPCION");
		sql.SELECT("DOCUMENTO.fechabaja AS FECHABAJA");
		sql.SELECT("CATDOCUMENTO.IDRECURSO AS CODIGODESCRIPCION");


		sql.FROM("SCS_DOCUMENTOEJG documento");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CATDOCUMENTO ON CATDOCUMENTO.IDRECURSO = DOCUMENTO.DESCRIPCION AND CATDOCUMENTO.IDLENGUAJE= '" + idLenguaje + "'");
		
		sql.WHERE("documento.idinstitucion = '" + documentoEjgItem.getIdInstitucion() + "'");
		//sql.WHERE("DOCUMENTO.IDTIPODOCUMENTOEJG = '" + documentoEjgItem.getIdDocumentoEjg() + "'");
		
		sql.WHERE("documento.IDTIPODOCUMENTOEJG  = '" + documentoEjgItem.getIdTipoDocumento() + "'");
		
		if(!documentoEjgItem.isHistorico()) {
			sql.WHERE("DOCUMENTO.fechabaja is null");
		}
		sql.ORDER_BY("documento.abreviatura");
		return sql.toString();
	}
	
	public String getIdDocumentoEjg(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDDOCUMENTOEJG) AS IDDOCUMENTOEJG");
		sql.FROM("SCS_DOCUMENTOEJG");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		return sql.toString();
	}
	
}
