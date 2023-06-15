package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ComisariaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsComisariaSqlProvider;

public class ScsComisariaSqlExtendsProvider extends ScsComisariaSqlProvider {

	public String searchComisarias(ComisariaItem comisariaItem, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("comisaria.idinstitucion");
		sql.SELECT("comisaria.idcomisaria");
		sql.SELECT("comisaria.nombre");
		sql.SELECT("comisaria.domicilio");
		sql.SELECT("comisaria.codigopostal");
		sql.SELECT("comisaria.idpoblacion");
		sql.SELECT("comisaria.idprovincia");
		sql.SELECT("comisaria.telefono1");
		sql.SELECT("comisaria.fax1");
		sql.SELECT("comisaria.telefono2");
		sql.SELECT("comisaria.fechabaja");
		sql.SELECT("comisaria.codigoext");
		sql.SELECT("comisaria.email");
		sql.SELECT("comisaria.visiblemovil");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");

		sql.FROM("SCS_COMISARIA comisaria");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = comisaria.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = comisaria.IDPOBLACION");
		if(idInstitucion != 2000) {
			sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		}
		if (comisariaItem.getNombre() != null && comisariaItem.getNombre() != "") {
			String columna = "COMISARIA.NOMBRE";
			String cadena = comisariaItem.getNombre();
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			

		}

		if (comisariaItem.getCodigoExt() != null && comisariaItem.getCodigoExt() != "") {
			
			String columna = "COMISARIA.CODIGOEXT";
			String cadena = comisariaItem.getCodigoExt();
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}

		if (comisariaItem.getIdPoblacion() != null && comisariaItem.getIdPoblacion() != "") {
			sql.WHERE("comisaria.idpoblacion = '" + comisariaItem.getIdPoblacion() + "'");
		}

		if (comisariaItem.getIdProvincia() != null && comisariaItem.getIdProvincia() != "") {
			sql.WHERE("comisaria.idprovincia = '" + comisariaItem.getIdProvincia() + "'");
		}

		if (!comisariaItem.getHistorico()) {
			sql.WHERE("fechabaja is null");
		}

		sql.ORDER_BY("comisaria.nombre");

		return sql.toString();
	}

	public String getIdComisaria(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDCOMISARIA) AS IDCOMISARIA");
		sql.FROM("SCS_COMISARIA");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	
	public String comboComisaria(Short idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("comisaria.IDCOMISARIA");
		sql.SELECT("comisaria.NOMBRE");
		sql.FROM("SCS_COMISARIA comisaria");
		sql.WHERE("comisaria.fechabaja is null");
		sql.WHERE("comisaria.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("comisaria.NOMBRE");

		return sql.toString();
	}

	public String comboCDetencion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("comisaria.IDCOMISARIA");
		sql.SELECT("comisaria.nombre || ' [' || comisaria.CODIGOEXT || ']' as cdetencion");
		sql.FROM("SCS_COMISARIA comisaria");
		sql.WHERE("comisaria.fechabaja is null");
		sql.WHERE("comisaria.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cdetencion");

		return sql.toString();
	}
	
	public String comboComisariaCdgoExt(Short idLenguaje, Short idInstitucion){

		SQL sql = new SQL();

		sql.SELECT("comisaria.CODIGOEXT");
		sql.SELECT("comisaria.NOMBRE");
		sql.FROM("SCS_COMISARIA comisaria");
		sql.WHERE("comisaria.fechabaja is null");
		sql.WHERE("comisaria.idinstitucion = " + idInstitucion);
		sql.WHERE("comisaria.CODIGOEXT is not null");
		sql.ORDER_BY("comisaria.NOMBRE");

		return sql.toString();

	}

	public String getComisariasByIdTurno(Short idInstitucion, String idTurno) {

		SQL sql = new SQL();
		sql.SELECT("c.idcomisaria");
		sql.SELECT("decode(c.fechabaja, NULL, c.nombre\r\n"
				+ "                              || ' ('\r\n"
				+ "                              || po.nombre\r\n"
				+ "                              || ')', c.nombre\r\n"
				+ "                                      || ' ('\r\n"
				+ "                                      || po.nombre\r\n"
				+ "                                      || ') (BAJA)') AS nombre");
		sql.FROM("scs_comisaria c");
		sql.INNER_JOIN("cen_poblaciones po ON c.idpoblacion = po.idpoblacion");
		sql.WHERE("c.idinstitucion = "+idInstitucion+"");
		sql.ORDER_BY("c.fechabaja DESC, nombre");



		return sql.toString();
		}
	
}
