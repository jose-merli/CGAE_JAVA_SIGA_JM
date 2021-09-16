package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;

public class ScsRemesasResultadosExtendsProvider {
	
	private Logger LOGGER = Logger.getLogger(ScsRemesasResultadosExtendsProvider.class);
	
	public String  buscarRemesasResultado(RemesasResultadoItem remesasBusquedaItem, Short idInstitucion) {
		SQL sql = new SQL();
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("REMR.IDREMESARESOLUCION");
		sql.SELECT("REMR.PREFIJO AS NUMREMESAPREFIJO");
		sql.SELECT("REMR.SUFIJO AS NUMREMESASUFIJO");
		sql.SELECT("REMR.NUMERO AS NUMREMESANUMERO");
		sql.SELECT("REMR.NOMBREFICHERO");
		sql.SELECT("REMR.OBSERVACIONES AS OBSERVACIONESREMESARESULTADO");
		sql.SELECT("REMR.FECHACARGA AS FECHACARGAREMESARESULTADO");
		sql.SELECT("REMR.FECHARESOLUCION AS FECHARESOLUCIONREMESARESULTADO");
		sql.SELECT("REMR.IDREMESA");
		sql.SELECT("REM.PREFIJO AS PREFIJOREMESA");
		sql.SELECT("REM.SUFIJO AS SUFIJOREMESA");
		sql.SELECT("REM.NUMERO AS NUMEROREMESA");
		sql.SELECT("REM.DESCRIPCION AS DESCRIPCIONREMESA");
		
		sql.FROM("CAJG_REMESARESOLUCION REMR");
		sql.LEFT_OUTER_JOIN("CAJG_REMESA REM ON REM.IDINSTITUCION = REMR.IDINSTITUCION");
		
		sql.WHERE("REMR.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("REMR.IDTIPOREMESA = 3");
		sql.WHERE("ROWNUM <= 200");
		
		sql.ORDER_BY("REMR.IDREMESARESOLUCION DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
}
