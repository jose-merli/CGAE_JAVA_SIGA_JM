package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.db.mappers.ScsDocumentoejgSqlProvider;

public class ScsDocumentoejgSqlExtendsProvider extends ScsDocumentoejgSqlProvider {

	
	public String searchDocumento(DocumentacionEjgItem documentoEjgItem, String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT("documento.idinstitucion");
		sql.SELECT("documento.idtipodocumentoejg AS IDTIPODOC");
		sql.SELECT("documento.iddocumentoejg AS IDDOCUMENTO");
		sql.SELECT("F_SIGA_GETRECURSO(documento.abreviatura," + idLenguaje + ") AS ABREVIATURADOC");
		sql.SELECT("documento.codigoext AS DOCCODIGOEXT");
		sql.SELECT("catdocumento.descripcion AS DESCRIPCION");
		sql.SELECT("DOCUMENTO.fechabaja AS FECHABAJA");
		sql.SELECT("F_SIGA_GETRECURSO(CATDOCUMENTO.IDRECURSO," + idLenguaje +") AS CODIGODESCRIPCION");
		sql.SELECT("CATDOCUMENTO.IDRECURSO as IDCODIGODESCRIPCION");
		

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
	
	public String comboDocumentos(String idLenguaje, Short idInstitucion, String idTipoDocumentacion) {
		SQL sql = new SQL();
		
		sql.SELECT("DOCUMENTOEJG.IDDOCUMENTOEJG");
		sql.SELECT("catalogoDOCUMENTOEJG.DESCRIPCION");

		sql.FROM("scs_DOCUMENTOEJG DOCUMENTOEJG");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoDOCUMENTOEJG on catalogoDOCUMENTOEJG.idrecurso = DOCUMENTOEJG.DESCRIPCION and catalogoDOCUMENTOEJG.idlenguaje ="+idLenguaje);
		sql.WHERE("DOCUMENTOEJG.fechabaja is null");
		sql.WHERE("DOCUMENTOEJG.idinstitucion ='"+String.valueOf(idInstitucion)+"'");
		sql.WHERE("DOCUMENTOEJG.idtipodocumentoejg ='"+String.valueOf(idTipoDocumentacion)+"'");
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}
	
}
