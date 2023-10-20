package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsProcuradorSqlProvider;

public class ScsProcuradorSqlExtendsProvider extends ScsProcuradorSqlProvider {

	public String searchProcuradores(ProcuradorItem procuradorItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("procurador.idinstitucion");
		sql.SELECT("procurador.idprocurador");
		sql.SELECT("procurador.nombre");
		sql.SELECT("procurador.apellidos1");
		sql.SELECT("procurador.apellidos2");
		sql.SELECT("procurador.domicilio");
		sql.SELECT("procurador.codigopostal");
		sql.SELECT("procurador.idpoblacion");
		sql.SELECT("procurador.idprovincia");
		sql.SELECT("procurador.telefono1");
		sql.SELECT("procurador.fax1");
		sql.SELECT_DISTINCT(
				"concat(concat(procurador.apellidos1 || ' ', concat(procurador.apellidos2 , ', ')), procurador.nombre || ' ') AS nombreape");
		sql.SELECT("procurador.telefono2");
		sql.SELECT("procurador.fechabaja");
		sql.SELECT("procurador.ncolegiado");
		sql.SELECT("procurador.idcolprocurador");
		sql.SELECT("CEN_COLEGIOPROCURADOR.NOMBRE AS nombreColProcurador");
		sql.SELECT("procurador.codigo");
		sql.SELECT("procurador.email");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");

		sql.FROM("SCS_PROCURADOR procurador");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = procurador.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = procurador.IDPOBLACION");
		sql.LEFT_OUTER_JOIN("CEN_COLEGIOPROCURADOR ON CEN_COLEGIOPROCURADOR.IDCOLPROCURADOR = PROCURADOR.IDCOLPROCURADOR");
		//if(idInstitucion != 2000) {
		sql.WHERE("procurador.idinstitucion = '" + idInstitucion + "'");
		//}


		if (procuradorItem.getNombre() != null && procuradorItem.getNombre() != "") {
			String columna = "PROCURADOR.NOMBRE";
			String cadena = procuradorItem.getNombre();
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
		}

		if (procuradorItem.getApellido1() != null && procuradorItem.getApellido1() != "") {
			

			String columna = "REPLACE(CONCAT(PROCURADOR.APELLIDOS1,PROCURADOR.APELLIDOS2), ' ', '')";
			String cadena = procuradorItem.getApellido1().replaceAll("\\s+","");
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
			
			/*sql.AND();
			sql.WHERE(
					"((TRANSLATE(LOWER( PROCURADOR.APELLIDOS1),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%"
							+ procuradorItem.getApellido1().trim() + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			sql.OR();
			sql.WHERE(
					"(TRANSLATE(LOWER( PROCURADOR.APELLIDOS2),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%"
							+ procuradorItem.getApellido1().trim() + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')))");*/
		}
		
		if (procuradorItem.getnColegiado() != null && procuradorItem.getnColegiado() != "") {
			sql.AND();
			sql.WHERE("UPPER(procurador.ncolegiado) like UPPER('%" + procuradorItem.getnColegiado() + "%')");
		}
		
		
		
		if(procuradorItem.getIdColProcurador() != null && procuradorItem.getIdColProcurador() != "") {
			sql.WHERE("UPPER(procurador.idColProcurador) like UPPER('%"+procuradorItem.getIdColProcurador()+"%')");
		}
			

		if (!procuradorItem.getHistorico()) {
			sql.AND();

			sql.WHERE("procurador.fechabaja is null");
		}
		
		if (procuradorItem.getCodigoExt() != null && !procuradorItem.getCodigoExt().isEmpty()) {
			
			sql.AND();

			sql.WHERE("procurador.codigo = '" + procuradorItem.getCodigoExt() + "'");
		}
		

		sql.ORDER_BY("procurador.nombre");

		return sql.toString();
	}

	public String buscadorProcurador(String idInstitucion, ProcuradorItem procuradorItem) {

		SQL sql = new SQL();

		sql.SELECT("TRIM(apellidos1) as apellidos1");
		sql.SELECT("TRIM(apellidos2) as apellidos2");
		sql.SELECT("codigo");
		sql.SELECT("codigopostal");
		sql.SELECT("domicilio");
		sql.SELECT("email");
		sql.SELECT("fax1");
		sql.SELECT("fechabaja");
		sql.SELECT("pro.fechamodificacion");
		sql.SELECT("idcolprocurador");
		sql.SELECT("idpoblacion");
		sql.SELECT("idprovincia");
		sql.SELECT_DISTINCT("TRIM(concat(concat(apellidos1 || '', ' '), apellidos2 || '')) AS nombreape");
		sql.SELECT("ncolegiado");
		sql.SELECT("TRIM(pro.nombre) as nombre");
		sql.SELECT("telefono1");
		sql.SELECT("telefono2");
		sql.SELECT("abreviatura");
		sql.SELECT("idprocurador");

		sql.FROM("scs_procurador PRO");

		sql.LEFT_OUTER_JOIN("CEN_INSTITUCION INST ON PRO.IDINSTITUCION = INST.IDINSTITUCION");

		if (procuradorItem.getIdInstitucion() != null && !procuradorItem.getIdInstitucion().trim().equals(""))
			sql.WHERE("pro.idinstitucion in (" + procuradorItem.getIdInstitucion() + ")");
		else if (idInstitucion.equals("2000") && procuradorItem.getIdInstitucion() == null || procuradorItem.getIdInstitucion().equals("")) {
		} else
			sql.WHERE("pro.idinstitucion = '" + idInstitucion + "'");

		if (procuradorItem.getnColegiado() != null && !procuradorItem.getnColegiado().trim().equals(""))
			sql.WHERE("ncolegiado = '" + procuradorItem.getnColegiado() + "'");

		if (procuradorItem.getApellido1() != null && !procuradorItem.getApellido1().trim().equals("")){
			

			String columna = "apellidos1";
			String cadena = procuradorItem.getApellido1();
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}
			
		if (procuradorItem.getApellido2() != null && !procuradorItem.getApellido2().trim().equals("")){
			String columna = "apellidos2";
			String cadena = procuradorItem.getApellido2();
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}

		if (procuradorItem.getNombre() != null && !procuradorItem.getNombre().trim().equals("")){
			String columna = "pro.NOMBRE";
			String cadena = procuradorItem.getNombre();
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}

		if (!procuradorItem.getHistorico())
			sql.WHERE("pro.fechabaja is null");
		return sql.toString();
	}

	public String getIdProcurador(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPROCURADOR) AS IDPROCURADOR");
		sql.FROM("SCS_PROCURADOR");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

}
