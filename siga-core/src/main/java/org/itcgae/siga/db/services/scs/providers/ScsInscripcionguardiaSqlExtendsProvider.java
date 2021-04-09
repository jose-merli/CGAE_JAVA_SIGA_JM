package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaSqlProvider;

public class ScsInscripcionguardiaSqlExtendsProvider extends ScsInscripcionguardiaSqlProvider{

	public String searchGrupoGuardia(Short idInstitucion, String idGuardia) {

		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.NUMEROGRUPO");
		sql.SELECT("SCS_GRUPOGUARDIA.IDGUARDIA");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.ORDEN");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.IDPERSONA");

		sql.FROM("SCS_GRUPOGUARDIA");
		sql.INNER_JOIN("SCS_GRUPOGUARDIACOLEGIADO gcolegiado on SCS_GRUPOGUARDIA.IDINSTITUCION =  SCS_GRUPOGUARDIACOLEGIADO.IDINSTITUCION"
				+ "AND SCS_GRUPOGUARDIA.IDGRUPOGUARDIA =  SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA");

		sql.WHERE("SCS_GRUPOGUARDIA.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("SCS_GRUPOGUARDIA.IDGUARDIA IN (" + idGuardia + ")");
		
		sql.ORDER_BY("SCS_GRUPOGUARDIA.NUMEROCOLEGIADO");


		return sql.toString();
	}
	
	public String searchGrupo(String grupo, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.NUMEROGRUPO");
		sql.SELECT("SCS_GRUPOGUARDIA.IDGUARDIA");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.ORDEN");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.IDPERSONA");

		sql.FROM("SCS_GRUPOGUARDIA");
		sql.INNER_JOIN("SCS_GRUPOGUARDIACOLEGIADO gcolegiado on SCS_GRUPOGUARDIA.IDINSTITUCION =  SCS_GRUPOGUARDIACOLEGIADO.IDINSTITUCION"
				+ "AND SCS_GRUPOGUARDIA.IDGRUPOGUARDIA =  SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA");

		sql.WHERE("SCS_GRUPOGUARDIA.idinstitucion = '" + idInstitucion + "'");
		//sql.WHERE("SCS_GRUPOGUARDIA.idPersona = '" + idPersona + "'");
		sql.WHERE("SCS_GRUPOGUARDIA.NUMEROGRUPO = '" + grupo + "'");


		return sql.toString();
	}
}
