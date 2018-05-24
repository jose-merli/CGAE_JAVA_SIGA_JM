package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.db.mappers.GenParametrosSqlProvider;

public class GenParametrosSqlExtendsProvider extends GenParametrosSqlProvider{

	
	public String getModules() {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT MODULO");
		sql.FROM("gen_parametros");
		sql.ORDER_BY(" MODULO ASC");
		
		return sql.toString();
	}
	
	
	public String getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO){
        SQL sql = new SQL();

        sql.SELECT("DISTINCT PARAM.MODULO");
        sql.SELECT("PARAM.PARAMETRO");
        sql.SELECT("PARAM.IDRECURSO");
        sql.SELECT("DECODE( (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "' and param.MODULO =param2.MODULO),"
                + "null,(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '0' and param.MODULO = param2.MODULO),"
                + "(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "' and param.MODULO =param2.MODULO)) AS VALOR");
        sql.SELECT("DECODE(COUNT(IDINSTITUCION),1,'0','" + parametroRequestDTO.getIdInstitucion() + "') AS IDINSTITUCION");
        sql.FROM("gen_parametros param ");
        sql.WHERE("IDINSTITUCION IN ('" + parametroRequestDTO.getIdInstitucion() + "','0')");
        sql.WHERE("MODULO = '" + parametroRequestDTO.getModulo() + "'");
        sql.WHERE("FECHA_BAJA IS NULL");
        sql.GROUP_BY("param.modulo, param.parametro, param.idrecurso");
        sql.ORDER_BY(" PARAM.MODULO ");
		
		return sql.toString();
	}
	
	
	public String getParametersSearchGeneral(int numPagina, ParametroRequestDTO parametroRequestDTO){
		SQL sql = new SQL();

		sql.SELECT("DISTINCT PARAM3.MODULO");
		sql.SELECT("PARAM3.PARAMETRO");
		sql.SELECT("PARAM3.IDRECURSO");
		sql.SELECT("DECODE((SELECT  PARAM2.VALOR FROM GEN_PARAMETROS PARAM2 WHERE PARAM.PARAMETRO = PARAM2.PARAMETRO AND   IDINSTITUCION = '2000' AND   PARAM.MODULO = PARAM2.MODULO),"
				+ "NULL, DECODE( (SELECT PARAM2.VALOR FROM GEN_PARAMETROS PARAM2 WHERE PARAM.PARAMETRO = PARAM2.PARAMETRO AND   IDINSTITUCION = '"+ parametroRequestDTO.getIdInstitucion() +"' AND   PARAM.MODULO = PARAM2.MODULO),"
				+ "NULL, ( SELECT PARAM2.VALOR FROM GEN_PARAMETROS PARAM2 WHERE PARAM.PARAMETRO = PARAM2.PARAMETRO AND   IDINSTITUCION = '0' AND   PARAM.MODULO = PARAM2.MODULO), "
				+ "(SELECT PARAM2.VALOR FROM GEN_PARAMETROS PARAM2 WHERE PARAM.PARAMETRO = PARAM2.PARAMETRO AND   IDINSTITUCION = '"+ parametroRequestDTO.getIdInstitucion() +"' AND   PARAM.MODULO = PARAM2.MODULO) ),"
				+ "(SELECT PARAM2.VALOR FROM GEN_PARAMETROS PARAM2 WHERE PARAM.PARAMETRO = PARAM2.PARAMETRO AND   IDINSTITUCION = '2000' AND   PARAM.MODULO = PARAM2.MODULO) )AS VALOR");
		
		sql.SELECT(" PARAM.IDINSTITUCION ");
		sql.FROM(" (SELECT DISTINCT PARAMETRO.MODULO, PARAMETRO.PARAMETRO, DECODE(MAX( DECODE(IDINSTITUCION,'2000','9999',IDINSTITUCION )),'9999','2000',MAX(IDINSTITUCION)) IDINSTITUCION "
				+ " FROM    GEN_PARAMETROS PARAMETRO "
				+ " WHERE IDINSTITUCION IN ( '"+ parametroRequestDTO.getIdInstitucion() +"', '0', '2000' ) AND   MODULO = '"+ parametroRequestDTO.getModulo() +"' AND   FECHA_BAJA IS NULL "
				+ " GROUP BY PARAMETRO.MODULO,  PARAMETRO.PARAMETRO ORDER BY PARAMETRO) PARAM ");
		sql.INNER_JOIN(" GEN_PARAMETROS PARAM3 ON (PARAM3.MODULO = PARAM.MODULO AND PARAM.PARAMETRO = PARAM3.PARAMETRO AND PARAM.IDINSTITUCION = PARAM3.IDINSTITUCION) ");

		
		return sql.toString();
	}
	
	public String getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO){
		SQL sql = new SQL();

		sql.SELECT("DISTINCT PARAM.MODULO");
		sql.SELECT("PARAM.PARAMETRO");
		sql.SELECT("DECODE( (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "' and param.MODULO =param2.MODULO),"
				+ "null,(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '0' and param.MODULO = param2.MODULO),"
				+ "(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "' and param.MODULO =param2.MODULO)) AS VALOR");
		
		sql.SELECT("DECODE(COUNT(IDINSTITUCION),1,'0','" + parametroRequestDTO.getIdInstitucion() + "') AS IDINSTITUCION");
		sql.SELECT("FECHA_BAJA");
		sql.FROM("gen_parametros param");
		sql.WHERE("IDINSTITUCION IN ('" + parametroRequestDTO.getIdInstitucion() + "','0')");
		sql.WHERE("MODULO = '" + parametroRequestDTO.getModulo() + "'");
		sql.GROUP_BY("param.modulo, param.parametro");
		
		return sql.toString();
	}
	
}
