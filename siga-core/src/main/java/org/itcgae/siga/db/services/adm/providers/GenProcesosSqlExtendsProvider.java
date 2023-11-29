package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.gen.PermisoRequestItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.mappers.AdmContadorSqlProvider;

public class GenProcesosSqlExtendsProvider extends AdmContadorSqlProvider{

	
	
	public String getProcesosPermisos(PermisoRequestItem request,String idInstitucionCert, String idLenguaje){
		SQL sql = new SQL();
		SQL sqlMenuInactivo = new SQL();
		
		sqlMenuInactivo.SELECT("'[INACTIVO]'");
		sqlMenuInactivo.FROM("gen_menu"); 
		sqlMenuInactivo.WHERE("gen_menu.idmenu = MENU.IDMENU");
		sqlMenuInactivo.WHERE("(gen_menu.fecha_baja is not null or not exists "
				+ " (select 1 from gen_menu_institucion "
				+ " where gen_menu_institucion.idmenu = gen_menu.idmenu"
				+ " and gen_menu_institucion.idinstitucion = '" + request.getIdInstitucion() + "'))");

		sql.SELECT_DISTINCT("PROC.IDPROCESO AS ID");
		sql.SELECT("NVL(PROC.IDPARENT,'ARBOL') AS PARENT");
		sql.SELECT("CASE \r\n"
				+ "		WHEN MENU.IDRECURSO IS NULL THEN\r\n"
				+ "			PROC.DESCRIPCION || ' [' || PROC.IDPROCESO || ']'\r\n"
				+ "		ELSE\r\n"
				+ "			PROC.DESCRIPCION || ' [' || PROC.IDPROCESO || '] - Menu: '"
				+ " || f_siga_getrecurso_etiqueta(MENU.IDRECURSO,1) || ' [' || MENU.IDMENU || ']' || (" + sqlMenuInactivo.toString() + ")\r\n"
				+ "	END AS TEXT");
		sql.SELECT("NVL(ACCESO.DERECHOACCESO,0) AS DERECHOACCESO");
		sql.FROM("GEN_PROCESOS PROC");
		sql.LEFT_OUTER_JOIN("GEN_MENU menu on PROC.IDPROCESO = MENU.IDPROCESO");
		sql.LEFT_OUTER_JOIN("ADM_TIPOSACCESO ACCESO ON (PROC.IDPROCESO = ACCESO.IDPROCESO AND IDINSTITUCION = '"+ request.getIdInstitucion() + "'   and acceso.idperfil = '" + request.getIdGrupo() + "')");
		// || !request.getIdInstitucionCertificado().equals(SigaConstants.InstitucionGeneral)
		  if (!idInstitucionCert.equals(SigaConstants.InstitucionGeneral)) {
	        	sql.WHERE(" UPPER(PROC.DESCRIPCION) not like UPPER('%hidden%')");
			}
		sql.ORDER_BY("PARENT DESC, TEXT ASC");
		return sql.toString();
	}

	
}
