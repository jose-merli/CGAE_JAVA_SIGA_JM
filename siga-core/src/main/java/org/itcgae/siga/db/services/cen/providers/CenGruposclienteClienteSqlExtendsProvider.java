package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteSqlProvider;

public class CenGruposclienteClienteSqlExtendsProvider extends CenGruposclienteClienteSqlProvider{

		
	public String insertSelectiveForCreateLegalPerson(String idInstitucion, String grupo, String idUsuario) {
		
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");
		sql.VALUES("IDINSTITUCION", idInstitucion);
		if(!grupo.equals("")) {
			sql.VALUES("IDGRUPO", "'" +grupo + "'");
		}
		else {
			sql.VALUES("IDGRUPO", "(SELECT MAX(IDGRUPO) FROM CEN_GRUPOSCLIENTE)");
		}
		
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +idUsuario + "'");
		sql.VALUES("IDINSTITUCION_GRUPO", "'" +idInstitucion+ "'");
		sql.VALUES("FECHA_BAJA", "null");
		sql.VALUES("FECHA_INICIO", "SYSDATE");
		return sql.toString();
	}
	
	public String selectGruposPersonaJuridica(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("cli.idgrupo");
		sql.SELECT("GENR.descripcion");
		sql.FROM("CEN_GRUPOSCLIENTE_CLIENTE cli");
		sql.INNER_JOIN("cen_persona per on cli.idpersona = per.idpersona");
		sql.INNER_JOIN("CEN_GRUPOSCLIENTE GRUCLI on cli.idGrupo = GRUCLI.idGrupo");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '1'");
		sql.WHERE("per.idpersona = '"+idPersona+"'");
		sql.WHERE("cli.FECHA_BAJA is null");
		sql.WHERE("GRUCLI.idinstitucion = '"+idInstitucion+"'");
		
		return sql.toString();
	}
	
	public String selectGrupos(String idlenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("GRUCLI.idgrupo");
		sql.SELECT("GENR.descripcion");
		sql.FROM("CEN_GRUPOSCLIENTE GRUCLI");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '"+idlenguaje+"'");
		sql.WHERE("GRUCLI.idinstitucion = '"+idInstitucion+"'");
		sql.ORDER_BY("GENR.descripcion");
		return sql.toString();
	}
	
	public String insertSelectiveForUpdateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, String idInstitucion, String grupo, String idUsuario) {
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "'" + etiquetaUpdateDTO.getIdPersona() + "'");
		sql.VALUES("IDINSTITUCION", idInstitucion);
		
		if(!grupo.equals("")) {
			sql.VALUES("IDGRUPO", "'" +grupo + "'");
		}
		else {
			sql.VALUES("IDGRUPO", "(SELECT MAX(IDGRUPO) FROM CEN_GRUPOSCLIENTE)");
		}
		
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +idUsuario + "'");
		sql.VALUES("IDINSTITUCION_GRUPO", "'" +idInstitucion+ "'");
		sql.VALUES("FECHA_BAJA", "null");
		sql.VALUES("FECHA_INICIO", "SYSDATE");
		return sql.toString();
	}
	
	
}


