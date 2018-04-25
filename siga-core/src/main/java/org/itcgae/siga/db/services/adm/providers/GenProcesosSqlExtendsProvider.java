package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.db.entities.AdmContadorExample.Criterion;
import org.itcgae.siga.db.mappers.AdmContadorSqlProvider;
import org.itcgae.siga.db.mappers.GenParametrosSqlProvider;

public class GenProcesosSqlExtendsProvider extends AdmContadorSqlProvider{

	
	
	public String getProcesosPermisos(PermisoRequestItem request){
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("PROC.IDPROCESO AS ID");
		sql.SELECT("PROC.IDPARENT AS PARENT");
		sql.SELECT("PROC.DESCRIPCION AS TEXT");
		sql.SELECT("ACCESO.DERECHOACCESO AS DERECHOACCESO");

		sql.FROM("GEN_PROCESOS PROC");
		
		sql.INNER_JOIN("ADM_TIPOSACCESO ACCESO ON PROC.IDPROCESO = ACCESO.IDPROCESO ");
		sql.WHERE("IDINSTITUCION = ('" + request.getIdInstitucion() + "')");
		sql.WHERE("IDPERFIL = ('" + request.getIdGrupo() + "')");

		sql.ORDER_BY("PARENT DESC, TEXT ASC");
		return sql.toString();
	}

	
}
