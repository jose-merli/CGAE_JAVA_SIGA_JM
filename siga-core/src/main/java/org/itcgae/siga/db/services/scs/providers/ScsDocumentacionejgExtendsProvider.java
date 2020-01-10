package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;

public class ScsDocumentacionejgExtendsProvider {
	public String getDocumentacion(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("doc.fechalimite," + 
				" doc.documentacion," + 
				" doc.regentrada," + 
				" doc.regsalida," + 
				" doc.fechaentrega," + 
				" doc.comisionajg," + 
				" catalogo.descripcion as presentadores," + 
				" familiar.solicitante," + 
				" catalogo2.descripcion as parentesco," + 
				" persona.nombre," + 
				" persona.apellido1," + 
				" persona.apellido2");
		
		sql.FROM("scs_documentacionejg doc");
		
		sql.LEFT_OUTER_JOIN("scs_presentador presentador on (doc.idmaestropresentador=presentador.idpresentador and doc.idinstitucion=presentador.idinstitucion)");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos catalogo on (presentador.descripcion=catalogo.idrecurso and catalogo.idlenguaje ='" + idLenguaje + "')");
		sql.LEFT_OUTER_JOIN("scs_unidadfamiliarejg familiar on (doc.idinstitucion=familiar.idinstitucion and doc.idtipoejg=familiar.idtipoejg and doc.anio=familiar.anio and doc.numero=familiar.numero and doc.presentador=familiar.idpersona)");
		sql.LEFT_OUTER_JOIN("scs_parentesco parentesco on (familiar.idparentesco=parentesco.idparentesco and familiar.idinstitucion=parentesco.idinstitucion)");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos catalogo2 on (parentesco.descripcion=catalogo2.idrecurso and catalogo2.idlenguaje ='" + idLenguaje + "')");
		sql.LEFT_OUTER_JOIN("scs_personajg persona on (familiar.idpersona=persona.idpersona and familiar.idinstitucion=persona.idinstitucion)");

		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("doc.anio = '" + ejgItem.getAnnio() + "'");
		if(ejgItem.getNumEjg() != null && ejgItem.getNumEjg() != "")
			sql.WHERE("doc.numero = '" + ejgItem.getNumEjg() + "'");
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
