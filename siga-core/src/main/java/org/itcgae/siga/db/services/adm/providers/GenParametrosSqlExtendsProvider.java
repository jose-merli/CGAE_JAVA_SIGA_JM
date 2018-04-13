package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.db.mappers.GenParametrosSqlProvider;

public class GenParametrosSqlExtendsProvider extends GenParametrosSqlProvider{

//	select DISTINCT param.modulo, param.parametro 
//	   ,DECODE( (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '2040'),
//	                null,(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '0'), 
//	                     (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '2040')) AS VALOR
//	   ,DECODE(COUNT(IDINSTITUCION),1,'0','2040') AS IDINSTITUCION
//	    from gen_parametros param
//	    WHERE IDINSTITUCION IN ('2040','0') AND MODULO = 'FAC' 
//	AND FECHA_BAJA IS NULL
//	    GROUP BY param.modulo, param.parametro        

	
	public String getModules() {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT MODULO");
		sql.FROM("gen_parametros");
		
		return sql.toString();
	}
	
	public String getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO){
		SQL sql = new SQL();

		sql.SELECT("DISTINCT PARAM.MODULO");
		sql.SELECT("PARAM.PARAMETRO");
		sql.SELECT("DECODE( (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "'),"
				+ "null,(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '0'),"
				+ "(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "')) AS VALOR");
		
		sql.SELECT("DECODE(COUNT(IDINSTITUCION),1,'0','" + parametroRequestDTO.getModulo() + "') AS IDINSTITUCION");
		sql.FROM("gen_parametros param ");
		sql.WHERE("IDINSTITUCION IN ('" + parametroRequestDTO.getIdInstitucion() + "','0')");
		sql.WHERE("MODULO = '" + parametroRequestDTO.getModulo() + "'");
		sql.WHERE("FECHA_BAJA IS NULL");
		sql.GROUP_BY("param.modulo, param.parametro");
		
		return sql.toString();
	}
	
	public String getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO){
		SQL sql = new SQL();

		sql.SELECT("DISTINCT PARAM.MODULO");
		sql.SELECT("PARAM.PARAMETRO");
		sql.SELECT("DECODE( (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "'),"
				+ "null,(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '0'),"
				+ "(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "')) AS VALOR");
		
		sql.SELECT("DECODE(COUNT(IDINSTITUCION),1,'0','" + parametroRequestDTO.getModulo() + "') AS IDINSTITUCION");
		sql.SELECT("FECHA_BAJA");
		sql.FROM("gen_parametros param");
		sql.WHERE("IDINSTITUCION IN ('" + parametroRequestDTO.getIdInstitucion() + "','0')");
		sql.WHERE("MODULO = '" + parametroRequestDTO.getModulo() + "'");
		sql.GROUP_BY("param.modulo, param.parametro");
		
		return sql.toString();
	}
	
}
