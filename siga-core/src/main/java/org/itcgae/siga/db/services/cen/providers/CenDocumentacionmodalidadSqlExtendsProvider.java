package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmUsuarios;

public class CenDocumentacionmodalidadSqlExtendsProvider {

	public String selectDistinctModalidadDocumentacion(AdmUsuarios usuario, String isEXEA){
		
		SQL sql = new SQL();
		
		sql.SELECT("distinct E.IDMODALIDAD AS VALUE");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION," + usuario.getIdlenguaje() + ") AS LABEL");
		sql.FROM("CEN_DOCUMENTACIONMODALIDAD E");
		sql.WHERE("EXEA ='"+isEXEA+"'");
		sql.WHERE("E.IDINSTITUCION = '" + usuario.getIdinstitucion() + "'");
		
		return sql.toString();
	}
}