package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvDestinatariosExtendsSqlProvider {
	
	
	public String selectDestinatarios (Short idInstitucion, String idEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDPERSONA");
		sql.SELECT("NIFCIF");
		sql.SELECT("APELLIDOS1");
		sql.SELECT("APELLIDOS2");
		sql.SELECT("NOMBRE");
		sql.SELECT("DOMICILIO, MOVIL, CORREOELECTRONICO, NOMBRE || ' ' || APELLIDOS1 || ' '|| APELLIDOS2 AS NOMBRECOMPLETO");
		sql.FROM("ENV_DESTINATARIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE("IDENVIO = '" + idEnvio +"'");
		sql.WHERE("ORIGENDESTINATARIO = 0");
		
		return sql.toString();
	}
	
	public String selectDestinatariosComunicaciones (Short idInstitucion, String idEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDPERSONA");
		sql.SELECT("NIFCIF");
		sql.SELECT("APELLIDOS1");
		sql.SELECT("APELLIDOS2");
		sql.SELECT("ED.NOMBRE");
		sql.SELECT("DOMICILIO, MOVIL, CORREOELECTRONICO, ED.NOMBRE || ' ' || APELLIDOS1 || ' '|| APELLIDOS2 AS NOMBRECOMPLETO");
		sql.SELECT("DOMICILIO || ', ' || CODIGOPOSTAL || ' ' || CASE WHEN POBLACIONEXTRANJERA IS NOT NULL THEN POBLACIONEXTRANJERA ELSE CP.NOMBRE END AS DIRECCION");
		sql.FROM("ENV_DESTINATARIOS ED");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES CP ON ED.IDPOBLACION = CP.IDPOBLACION");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE("IDENVIO = '" + idEnvio +"'");
		
		return sql.toString();
	}

}
