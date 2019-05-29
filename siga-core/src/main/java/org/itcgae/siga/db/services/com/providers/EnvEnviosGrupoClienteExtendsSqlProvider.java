package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvEnviosGrupoClienteExtendsSqlProvider {
	
	public String selectGruposEnvio(String idEnvio, String idLenguaje, Short IdInstitucion){
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("GRUCLI.IDGRUPO");
		sql.SELECT("GENR.DESCRIPCION");
		sql.SELECT("GRUCLI.IDINSTITUCION_GRUPO as IDINSTITUCION");
		sql.FROM("env_enviosgrupocliente grucli");
		sql.INNER_JOIN("cen_gruposcliente cli on cli.IDGRUPO = grucli.IDGRUPO and grucli.IDINSTITUCION_GRUPO = cli.IDINSTITUCION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on cli.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '" + idLenguaje + "'");
		
		sql.WHERE("grucli.IDENVIO = '" + idEnvio +"' AND GRUCLI.IDINSTITUCION = '" + IdInstitucion + "'");
		
		return sql.toString();
	}

}
