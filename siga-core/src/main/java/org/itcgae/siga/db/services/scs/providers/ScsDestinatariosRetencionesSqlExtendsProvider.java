package org.itcgae.siga.db.services.scs.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.DestinatariosItem;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.db.mappers.FcsDestinatariosRetencionesSqlProvider;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaSqlProvider;
import org.itcgae.siga.db.mappers.ScsProcedimientosSqlProvider;

public class ScsDestinatariosRetencionesSqlExtendsProvider extends FcsDestinatariosRetencionesSqlProvider {

	public String searchDestinatario(DestinatariosItem destinatarioItem) {

		SQL sql = new SQL();

		sql.SELECT("idinstitucion");
		sql.SELECT("IDDESTINATARIO");
		sql.SELECT("nombre");
		sql.SELECT("cuentacontable");
		sql.SELECT("orden");
		sql.SELECT("fechabaja");

		sql.FROM("fcs_destinatarios_Retenciones");

		sql.WHERE("idinstitucion = '" + destinatarioItem.getIdinstitucion() + "'");
		if (destinatarioItem.getNombre() != null && destinatarioItem.getNombre() != "") {
			sql.WHERE("UPPER(nombre) like UPPER( '%" + destinatarioItem.getNombre() + "%')");
		}
		if (destinatarioItem.getOrden() != null && destinatarioItem.getOrden() != "") {
			sql.WHERE("orden = '" + destinatarioItem.getOrden() + "'");
		}
		if (destinatarioItem.getCuentacontable() != null && destinatarioItem.getCuentacontable() != "") {
			sql.WHERE("UPPER (cuentacontable) like UPPER('%" + destinatarioItem.getCuentacontable() + "%')");
		}
		if (!destinatarioItem.isHistorico()) {
			sql.WHERE("fechabaja is null");
		}

		sql.ORDER_BY("IDINSTITUCION, NOMBRE");

		return sql.toString();
	}
	
	public String getIdDestinatariosRetenc(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDDESTINATARIO) AS IDDESTINATARIORETENCIONES");
		sql.FROM("fcs_destinatarios_Retenciones");
		sql.WHERE("idinstitucion = '"+ idInstitucion +"'");

		return sql.toString();
	}
	
	public String updateRetenciones(DestinatariosItem destinatariosItem, Short idInstitucion) {
		SQL sql = new SQL();
		sql.UPDATE("UPDATE FCS_DESTINATARIOS_RETENCIONES\r\n" + 
				"SET cuentacontable = null where idinstitucion = 2005 and nombre ='a'");
		
		return sql.toString();
	}

}
