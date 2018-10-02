package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.db.mappers.CenCargamasivaSqlProvider;

public class CenCargaMasivaSqlExtendsProvider extends CenCargamasivaSqlProvider {
	
	public String selectEtiquetas(Short idInstitucion, CargaMasivaItem cargaMasivaItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TO_CHAR(ca.fechacarga,'DD/MM/YYYY') AS fechacarga");
		sql.SELECT_DISTINCT("ca.nombrefichero");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ', per.apellidos2) ) as usuario");
		sql.SELECT_DISTINCT("ca.numregistros as registros");
		sql.FROM("cen_cargamasiva ca");
		sql.INNER_JOIN("cen_gruposcliente_cliente cli on (cli.usumodificacion = ca.usumodificacion)");
		sql.INNER_JOIN("cen_persona per on (per.idpersona = cli.idpersona)");
		sql.WHERE("ca.idinstitucion = '"+ idInstitucion  + "'");
		sql.WHERE("ca.tipocarga = '"+ cargaMasivaItem.getTipoCarga() + "'");

		if (cargaMasivaItem.getFechaCarga() != null && cargaMasivaItem.getFechaCarga() != "") {
			sql.WHERE("(TO_DATE(ca.fechacarga,'DD/MM/RRRR') like TO_DATE('" + cargaMasivaItem.getFechaCarga() + "','DD/MM/YYYY'))");
		}
		
		sql.ORDER_BY("ca.nombrefichero");
		
		return sql.toString();
	}
}
