package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.mappers.GenParametrosSqlProvider;

public class GenParametrosSqlExtendsProvider extends GenParametrosSqlProvider{

	
	public String getModules() {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT MODULO");
		sql.FROM("gen_parametros");
		sql.ORDER_BY(" MODULO ASC");
		
		return sql.toString();
	}
	
	
	public String getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO, String idLenguaje){
        SQL sql = new SQL();
        SQL sql1 = new SQL();
        
        sql.SELECT("DISTINCT PARAM.MODULO");
        sql.SELECT("PARAM.PARAMETRO");
        sql.SELECT("PARAM.IDRECURSO");
        sql.SELECT("F_SIGA_GETPARAMETROGENERAL(PARAM.MODULO, PARAM.PARAMETRO,'"+ parametroRequestDTO.getIdInstitucion() +"') AS VALOR");
        sql.SELECT("DECODE(MAX(IDINSTITUCION),'"+ parametroRequestDTO.getIdInstitucion() +"','"+ parametroRequestDTO.getIdInstitucion()+"',MIN(IDINSTITUCION)) AS IDINSTITUCION");
        sql.SELECT("NVL(DICC.DESCRIPCION, 'SIN DEFINIR') AS DESCRIPCION");
        
        sql1.SELECT("'S'");
		sql1.FROM("GEN_PARAMETROS PARAM2");
		sql1.WHERE("PARAM2.PARAMETRO = PARAM.PARAMETRO");
		sql1.WHERE("IDINSTITUCION = '0'");
		sql1.WHERE("PARAM.MODULO = PARAM2.MODULO");
		
		sql.SELECT("NVL((" + sql1+"),'N') AS POSIBLEELIMINAR");
		sql.SELECT("'"+ parametroRequestDTO.getIdInstitucion() +"' AS IDINSTITUCIONACTUAL");
        
        sql.FROM("GEN_PARAMETROS PARAM");
        sql.LEFT_OUTER_JOIN("GEN_DICCIONARIO  DICC ON (PARAM.IDRECURSO = DICC.IDRECURSO  AND DICC.IDLENGUAJE = '" + idLenguaje +"')");
        sql.WHERE("(IDINSTITUCION IN ('" + parametroRequestDTO.getIdInstitucion() + "','2000','0')  AND FECHA_BAJA IS NULL) AND MODULO ='"+parametroRequestDTO.getModulo() +"'");
        sql.GROUP_BY("PARAM.MODULO, PARAM.PARAMETRO, PARAM.IDRECURSO, DICC.DESCRIPCION");
        sql.ORDER_BY("PARAM.PARAMETRO");
		
		return sql.toString();
	}
	
	
	public String getParametersSearchGeneral(int numPagina, ParametroRequestDTO parametroRequestDTO, String idLenguaje, String idInstitucion){
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		
		sql.SELECT("DISTINCT PARAM.MODULO");
		sql.SELECT("PARAM.PARAMETRO");
		sql.SELECT("PARAM.IDRECURSO");
		sql.SELECT("F_SIGA_GETPARAMETROGENERAL(PARAM.MODULO, PARAM.PARAMETRO,'" + parametroRequestDTO.getIdInstitucion()+ "') AS VALOR");
		sql.SELECT("MAX(IDINSTITUCION) AS IDINSTITUCION");
		sql.SELECT("NVL(DICC.DESCRIPCION, 'SIN DEFINIR') AS DESCRIPCION");
		
		sql1.SELECT("'S'");
		sql1.FROM("GEN_PARAMETROS PARAM2");
		sql1.WHERE("PARAM2.PARAMETRO = PARAM.PARAMETRO");
		sql1.WHERE("IDINSTITUCION = '0'");
		sql1.WHERE("PARAM.MODULO = PARAM2.MODULO");
		
		sql.SELECT("NVL((" + sql1+"),'N') AS POSIBLEELIMINAR");
		sql.SELECT("'"+ parametroRequestDTO.getIdInstitucion() +"' AS IDINSTITUCIONACTUAL");
		
		
		sql.FROM("GEN_PARAMETROS PARAM");
		sql.LEFT_OUTER_JOIN("GEN_DICCIONARIO  DICC ON (PARAM.IDRECURSO = DICC.IDRECURSO  AND DICC.IDLENGUAJE = '"+ idLenguaje + "')");
		sql.WHERE("(IDINSTITUCION IN ('"+idInstitucion+"','0')  AND FECHA_BAJA IS NULL) AND MODULO ='" + parametroRequestDTO.getModulo() + "'");
		sql.GROUP_BY("PARAM.MODULO, PARAM.PARAMETRO, PARAM.IDRECURSO, DICC.DESCRIPCION");
		sql.ORDER_BY("PARAM.PARAMETRO");

		return sql.toString();
	}
	
	public String getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO, String idLenguaje){
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		sql2.SELECT_DISTINCT("PARAM.MODULO");
		sql2.SELECT("PARAM.PARAMETRO");
		sql2.SELECT("PARAM.IDRECURSO");
		sql2.SELECT("PARAM.VALOR");
		sql2.SELECT("TO_CHAR(IDINSTITUCION) AS IDINSTITUCION");
		sql2.SELECT("NVL(DICC.DESCRIPCION, 'SIN DEFINIR') AS DESCRIPCION");
		sql2.SELECT("PARAM.FECHA_BAJA");
		sql2.FROM("GEN_PARAMETROS PARAM");
		sql2.LEFT_OUTER_JOIN("GEN_DICCIONARIO  DICC on (PARAM.IDRECURSO = DICC.IDRECURSO AND DICC.IDLENGUAJE = '"+ idLenguaje +"')");
		sql2.WHERE("PARAM.MODULO = '"+ parametroRequestDTO.getModulo() +"'");
		sql2.WHERE("IDINSTITUCION IN ('"+ parametroRequestDTO.getIdInstitucion()+"')");
		sql2.WHERE("FECHA_BAJA IS NOT NULL");
		sql2.ORDER_BY("PARAMETRO");
		
		sql.SELECT_DISTINCT("PARAM.MODULO");
		sql.SELECT("PARAM.PARAMETRO");
		sql.SELECT("PARAM.IDRECURSO");
		sql.SELECT("F_SIGA_GETPARAMETROGENERAL(PARAM.MODULO, PARAM.PARAMETRO,'"+ parametroRequestDTO.getIdInstitucion()+"') AS VALOR");
		
		// diferenciar entre institucion general y no general
//		if(parametroRequestDTO.getIdInstitucion().equals(SigaConstants.InstitucionGeneral))
//		{
			sql.SELECT("TO_CHAR(MAX(IDINSTITUCION)) AS IDINSTITUCION");
//		}else {
//			sql.SELECT("DECODE(MAX(IDINSTITUCION),'"+ parametroRequestDTO.getIdInstitucion()+"','"+ parametroRequestDTO.getIdInstitucion()+"',TO_CHAR(MIN(IDINSTITUCION))) AS IDINSTITUCION");
//		}
	
		sql.SELECT("NVL(DICC.DESCRIPCION, 'SIN DEFINIR') AS DESCRIPCION");
		sql.SELECT("PARAM.FECHA_BAJA");
		sql.FROM("GEN_PARAMETROS PARAM");
		sql.LEFT_OUTER_JOIN("GEN_DICCIONARIO  DICC on (PARAM.IDRECURSO = DICC.IDRECURSO AND DICC.IDLENGUAJE = '"+ idLenguaje +"')");
		sql.WHERE("PARAM.MODULO = '"+ parametroRequestDTO.getModulo() +"'");
		
		// diferenciar entre institucion general y no general
//		if(parametroRequestDTO.getIdInstitucion().equals(SigaConstants.InstitucionGeneral))
//		{
		sql.WHERE("IDINSTITUCION IN ('"+ parametroRequestDTO.getIdInstitucion()+"','0')");
//		}
//		else {
//			sql.WHERE("IDINSTITUCION IN ('"+ parametroRequestDTO.getIdInstitucion()+"','2000','0')");
//		}
		
		sql.WHERE("FECHA_BAJA IS NULL");
		sql.GROUP_BY("PARAM.MODULO");
		sql.GROUP_BY("PARAM.PARAMETRO");
		sql.GROUP_BY("PARAM.IDRECURSO");
		sql.GROUP_BY("DICC.DESCRIPCION");
		sql.GROUP_BY("PARAM.FECHA_BAJA" + " UNION ALL " + sql2);
		
		
		return sql.toString();
	}
	
	public String selectParametroPorInstitucion(String parametro, String idInstitucion) {
		SQL sql = new  SQL();
		SQL sql2 = new SQL();
		
		sql.SELECT("valor as valor");
		sql.FROM("gen_parametros");
		sql.WHERE("parametro = '" + parametro +"'");
		
		sql2.SELECT("MAX(idinstitucion)");
		sql2.FROM("gen_parametros");
		sql2.WHERE("parametro = '"+ parametro +"'");
		sql2.WHERE("idinstitucion IN ('"+ idInstitucion +"', '0')");
		
		sql.WHERE("idinstitucion = (" + sql2 + ")");
		
		return sql.toString();
	}
	
	public String updateByExampleFechaBaja(GenParametros genParametros) {
		SQL sql = new  SQL();
		
		sql.UPDATE("gen_parametros");
		sql.SET("VALOR = '"+ genParametros.getValor() +"'");
		sql.SET("FECHA_BAJA = NULL");
		
		
		sql.WHERE("MODULO = '" + genParametros.getModulo()+ "'");
		sql.WHERE("PARAMETRO = '" + genParametros.getParametro()+ "'");
		sql.WHERE("IDINSTITUCION = '" + genParametros.getIdinstitucion()+ "'");
		
		return sql.toString();
	}
	
	public String getParameterFunction(int numPagina, ParametroRequestDTO parametroRequestDTO){
        SQL sql = new SQL();
        SQL sql1 = new SQL();
        

        sql.SELECT("F_SIGA_GETPARAMETROGENERAL('" + parametroRequestDTO.getModulo()  +"' , '" + parametroRequestDTO.getParametrosGenerales()  +"','"+ parametroRequestDTO.getIdInstitucion() +"') AS VALOR");
        sql.FROM("DUAL");
		
		return sql.toString();
	}

	public String getSiguienteNumContador(AdmContador contador){
		SQL sql = new SQL();

		sql.SELECT("max (" + contador.getIdcampocontador() + ")+1 CONTADOR");

		sql.FROM(contador.getIdtabla());

		sql.WHERE("IDINSTITUCION = " + contador.getIdinstitucion() + " and " +
				contador.getIdcampoprefijo() + "= " + contador.getPrefijo() +" and " +
				contador.getIdcamposufijo() + "= " + contador.getSufijo() );

		return sql.toString();
	}

	public String comprobarUnicidadContador(AdmContador contador, int contadorSiguiente){
		SQL sql = new SQL();

		sql.SELECT(contador.getIdcampocontador() + " CONTADOR");

		sql.FROM(contador.getIdtabla());

		sql.WHERE("IDINSTITUCION = " + contador.getIdinstitucion() + " and " +
				contador.getIdcampocontador() + "= " + contadorSiguiente);

		if(contador.getPrefijo() != null && !contador.getPrefijo().equals("") ){
			sql.WHERE(contador.getIdcampoprefijo() + " = " + contador.getPrefijo());
		}else{
			sql.WHERE(contador.getIdcampoprefijo() + " IS NULL " );
		}

		if(contador.getSufijo() != null && !contador.getSufijo().equals("") ){
			sql.WHERE(contador.getIdcamposufijo() + " = " + contador.getSufijo());
		}else{
			sql.WHERE(contador.getIdcamposufijo() + " IS NULL " );
		}
		return sql.toString();
	}
}
