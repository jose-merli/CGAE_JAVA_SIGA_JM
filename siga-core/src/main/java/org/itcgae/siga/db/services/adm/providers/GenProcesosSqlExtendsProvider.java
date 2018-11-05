package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.mappers.AdmContadorSqlProvider;

public class GenProcesosSqlExtendsProvider extends AdmContadorSqlProvider{

	
	
	public String getProcesosPermisos(PermisoRequestItem request){
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("PROC.IDPROCESO AS ID");
		sql.SELECT("NVL(PROC.IDPARENT,'ARBOL') AS PARENT");
		sql.SELECT("PROC.DESCRIPCION AS TEXT");
		sql.SELECT("NVL(ACCESO.DERECHOACCESO,0) AS DERECHOACCESO");
    
		sql.FROM("GEN_PROCESOS PROC");
		
		sql.LEFT_OUTER_JOIN("ADM_TIPOSACCESO ACCESO ON (PROC.IDPROCESO = ACCESO.IDPROCESO AND IDINSTITUCION = '"+ request.getIdInstitucion() + "'   and acceso.idperfil = '" + request.getIdGrupo() + "')");
		// || !request.getIdInstitucion().equals(SigaConstants.InstitucionGeneral)) {
		  if (!request.getIdInstitucionCertificado().equals(SigaConstants.InstitucionGeneral)) {
	        	sql.WHERE(" UPPER(PROC.DESCRIPCION) not like UPPER('%hidden%')");
			}
		sql.ORDER_BY("PARENT DESC, TEXT ASC");
		return sql.toString();
	}

	
}
