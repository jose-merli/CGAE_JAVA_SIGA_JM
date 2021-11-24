package org.itcgae.siga.db.services.fcs.providers;

import java.util.ArrayList;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsPagoColegiadoSqlProvider;

public class FcsPagoColegiadoSqlExtendsProvider extends FcsPagoColegiadoSqlProvider{

	public String selectPagosColegiadoDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas) {
		SQL sql = new SQL();
		// preparar grupos en sentencia IN
		String gruposIN = "";
		for(int i=0;i< listaIdPersonas.size(); i++) {
			
			gruposIN = gruposIN.concat("'");
			if(i != listaIdPersonas.size() -1) {
				gruposIN = gruposIN.concat(listaIdPersonas.get(i));
				gruposIN = gruposIN.concat("'");
				gruposIN = gruposIN.concat(",");
			}else {
				gruposIN = gruposIN.concat(listaIdPersonas.get(i));
				gruposIN = gruposIN.concat("'");
			}	
		}
		sql.SELECT_DISTINCT("IDINSTITUCION");
		sql.SELECT("IDPAGOSJG");
		sql.FROM("FCS_PAGO_COLEGIADO");
		sql.WHERE("IDINSTITUCION = " + colegio +  "");
		sql.WHERE("IDPERORIGEN IN (" + gruposIN +  ")");
		sql.GROUP_BY("IDINSTITUCION, IDPAGOSJG");
		if (null != listaIdPersonas && listaIdPersonas.size()>0) {
			sql.HAVING("COUNT(1) = " + listaIdPersonas.size() +  "");
		}else {
			sql.HAVING("COUNT(1) = 0");
		}
		
		return sql.toString();
	}
	
	
	
	public String getIrpfByPago(String institucion, String pagos) {
		SQL sql = new SQL();
		
		sql.SELECT("IDPERSONAIMPRESO,"
				+ "Sum(importeirpf) As TOTALIMPORTEIRPF, "
				+ "Sum(importepagado) As TOTALIMPORTEPAGADO,"
				+ "CLAVEM190   FROM (SELECT IDPERDESTINO AS IDPERSONAIMPRESO,"
				+ "  	(-1) * IMPIRPF  AS importeirpf, IMPOFICIO + IMPASISTENCIA + IMPEJG + IMPSOJ + IMPMOVVAR AS importepagado, "
				+ " NVL((SELECT CLAVEM190 FROM  SCS_MAESTRORETENCIONES Ret  WHERE Ret.IDRETENCION = Pag.IDRETENCION), 'G01') AS CLAVEM190"
				+ "  FROM FCS_PAGO_COLEGIADO Pag"
				+ "  WHERE IDINSTITUCION = " + institucion
				+ "  AND IDPAGOSJG IN ( "+ pagos +")"
				+ "  AND PORCENTAJEIRPF > 0 "
				+ "  AND ("
				+ "IMPOFICIO > 0 or  "
				+ "IMPASISTENCIA > 0 or "
				+ "IMPEJG > 0 or  "
				+ "IMPSOJ > 0 ) )"
				+ "GROUP BY IDPERSONAIMPRESO, CLAVEM190");
		
		
		return sql.toString();
	}
}

