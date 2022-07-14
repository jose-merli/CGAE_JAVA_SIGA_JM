package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.db.mappers.CenCargamasivaSqlProvider;

public class CenCargaMasivaSqlExtendsProvider extends CenCargamasivaSqlProvider {
	
	public String selectEtiquetas(Short idInstitucion, CargaMasivaItem cargaMasivaItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TO_CHAR(ca.fechacarga,'DD/MM/YYYY') AS fechacarga");
		sql.SELECT("ca.nombrefichero");
		sql.SELECT("adm.descripcion as usuario");
		sql.SELECT("(nvl(ca.numregistros,0) - nvl(ca.numregistroserroneos,0)) as registroscorrectos");
		sql.SELECT("nvl(ca.numregistroserroneos,0) as registroserroneos");
		sql.SELECT("ca.idfichero");
		sql.SELECT("ca.idficherolog");
		sql.SELECT("ca.tipocarga");
		sql.SELECT("ca.fechacarga");
		sql.FROM("cen_cargamasiva ca");
		sql.INNER_JOIN("adm_usuarios adm on (adm.idusuario = ca.usumodificacion and adm.idinstitucion = ca.idinstitucion)");
		sql.WHERE("ca.idinstitucion = '"+ idInstitucion  + "'");
		sql.WHERE("ca.tipocarga = '"+ cargaMasivaItem.getTipoCarga() + "'");
		
		if (cargaMasivaItem.getFechaCargaDesde() != null && !cargaMasivaItem.getFechaCarga().isEmpty()) {
			sql.WHERE("ca.fechacarga>= TO_DATE('" + cargaMasivaItem.getFechaCargaDesde() + "','DD/MM/YYYY')");
		}

		if(cargaMasivaItem.getFechaCargaHasta() != null && !cargaMasivaItem.getFechaCargaHasta().isEmpty()) {
			 sql.WHERE("ca.fechacarga<= TO_DATE('" + cargaMasivaItem.getFechaCargaHasta() + "','DD/MM/YYYY')+1");
		}
		
		if(cargaMasivaItem.getFechaSolicitudDesde() != null && !cargaMasivaItem.getFechaSolicitudDesde().isEmpty()) {
			sql.WHERE("ca.fechacarga>= TO_DATE('" + cargaMasivaItem.getFechaSolicitudDesde() + "','DD/MM/YYYY')");
		}
		/*if (cargaMasivaItem.getFechaCarga() != null && cargaMasivaItem.getFechaCarga() != "") {
			sql.WHERE("ca.fechacarga>= TO_DATE('" + cargaMasivaItem.getFechaCarga() + "','DD/MM/RRRR') and ca.fechacarga< (TO_DATE('" + cargaMasivaItem.getFechaCarga() + "','DD/MM/YYYY')+1)");
		}*/

		sql.WHERE(" rownum <= 200");
		sql.ORDER_BY("ca.fechacarga desc");
		
		return sql.toString(); 
	}
}
