package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvEnviosGrupoClienteExtendsSqlProvider {
	
	public String selectGruposEnvio(String idEnvio, String idLenguaje, Short IdInstitucion){
		
		SQL sql = new SQL();
		
		sql.SELECT("cli.IDGRUPO");
		sql.SELECT("GENR.DESCRIPCION");
		sql.FROM("env_enviosgrupocliente grucli");
		sql.INNER_JOIN("cen_gruposcliente cli on cli.IDGRUPO = grucli.IDGRUPO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on cli.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '" + idLenguaje + "'");
		
		sql.WHERE("grucli.IDENVIO = '" + idEnvio +"' AND GENR.IDINSTITUCION = '" + IdInstitucion + "'");
		
		return sql.toString();
	}

}
