package org.itcgae.siga.db.services.ecom.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.EcomCenDatosSqlProvider;

public class EcomCenDatosSqlExtendsProvider extends EcomCenDatosSqlProvider{

	public String getInfoMantenimientoDuplicados(String idPersona, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("ECD.IDCENSODATOS				as ECD_IDCENSODATOS");
		sql.SELECT("ECD.IDESTADOCOLEGIADO		as ECD_IDESTADOCOLEGIADO");
		sql.SELECT("ECD.FECHAMODIFICACION		as ECD_FECHAMODIFICACION");
		sql.SELECT("ECD.IDECOMCENSOSITUACIONEJER	as ECD_IDECOMCENSOSITUACIONEJER");
		sql.SELECT("ECD.FECHAMODIFRECIBIDA		as ECD_FECHAMODIFRECIBIDA");
		sql.FROM("ECOM_CEN_DATOS ECD");
		

			sql.WHERE("ECD.IDCENSODATOS IN " + 
					"       (SELECT IDCENSODATOS " + 
					"           FROM ECOM_CEN_COLEGIADO " + 
					"          WHERE IDPERSONA = '" + idPersona + "' " + 
					"            AND IDINSTITUCION = " + idInstitucion + " )");
			sql.WHERE("ECD.IDESTADOCOLEGIADO in (1, 2, 3, 4)");
			sql.ORDER_BY("ECD.FECHAMODIFICACION DESC, ECD.IDCENSODATOS DESC");
		return sql.toString();
	}

}
