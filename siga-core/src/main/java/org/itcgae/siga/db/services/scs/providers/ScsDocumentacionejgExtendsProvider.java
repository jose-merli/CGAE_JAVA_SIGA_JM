package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgSqlProvider;

public class ScsDocumentacionejgExtendsProvider extends ScsDocumentacionejgSqlProvider {
	public String getDocumentacion(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("doc.*," + 
				" catalogo.descripcion as presentadores," + 
				" familiar.solicitante," + 
				" catalogo2.descripcion as parentesco," + 
//				" persona.nombre," + 
//				" persona.apellido1," + 
//				" persona.apellido2");
				"(CASE WHEN persona.nombre is  NULL THEN '' ELSE persona.apellido1 || ' ' || persona.apellido2 || ', ' || persona.nombre END)as nombrecompleto");
		sql.SELECT("catalogoDOCUMENTOEJG.DESCRIPCION as labelDocumento");
		
		sql.FROM("scs_documentacionejg doc");
		
		sql.LEFT_OUTER_JOIN("scs_presentador presentador on (doc.idmaestropresentador=presentador.idpresentador and doc.idinstitucion=presentador.idinstitucion)");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos catalogo on (presentador.descripcion=catalogo.idrecurso and catalogo.idlenguaje ='" + idLenguaje + "')");
		
		sql.LEFT_OUTER_JOIN("scs_unidadfamiliarejg familiar on (doc.idinstitucion=familiar.idinstitucion and doc.idtipoejg=familiar.idtipoejg and doc.anio=familiar.anio and doc.numero=familiar.numero and doc.presentador=familiar.idpersona)");
		sql.LEFT_OUTER_JOIN("scs_parentesco parentesco on (familiar.idparentesco=parentesco.idparentesco and familiar.idinstitucion=parentesco.idinstitucion)");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos catalogo2 on (parentesco.descripcion=catalogo2.idrecurso and catalogo2.idlenguaje ='" + idLenguaje + "')");
		
		sql.LEFT_OUTER_JOIN("scs_personajg persona on (familiar.idpersona=persona.idpersona and familiar.idinstitucion=persona.idinstitucion)");
		
		sql.LEFT_OUTER_JOIN("scs_documentoejg documentoejg on DOCUMENTOEJG.idinstitucion =doc.idinstitucion and documentoejg.fechabaja is null and DOCUMENTOEJG.idtipodocumentoejg=doc.idtipodocumento and DOCUMENTOEJG.iddocumentoejg=doc.iddocumento");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoDOCUMENTOEJG on catalogoDOCUMENTOEJG.idrecurso = DOCUMENTOEJG.DESCRIPCION and catalogoDOCUMENTOEJG.idlenguaje =" + idLenguaje );
		sql.WHERE("documentoejg.fechabaja is null");
		
		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("doc.anio = '" + ejgItem.getAnnio() + "'");
		if(ejgItem.getNumEjg() != null && ejgItem.getNumero() != "")
			sql.WHERE("doc.numero = '" + ejgItem.getNumero() + "'");
//			sql.WHERE("doc.numero = '16347'");

		if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("doc.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		if(idInstitucion != null && idInstitucion != "")
			sql.WHERE("doc.idinstitucion = '" + idInstitucion + "'");

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);

		}
		
		return sql.toString();
	}
}
