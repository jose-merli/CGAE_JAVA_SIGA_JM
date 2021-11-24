package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.db.mappers.FcsFicheroImpreso190SqlProvider;

public class FcsFicheroImpreso190SqlExtendsProviders extends FcsFicheroImpreso190SqlProvider {

	public String getImpreso190(int anio, Short idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("IDFICHERO,"
				+ "IDINSTITUCION,"
				+ "ANIO,"
				+ "NOMBRE_FICHERO as NOMFICHERO,"
				+ "NOMBRE as NOMBRECONTACTO,"
				+ "APELLIDO1 as APELLIDO1CONTACTO,"
				+ "APELLIDO2 as APELLIDO2CONTACTO,"
				+ "TELEFONO as TELEFONOCONTACTO,"
				+ "FECHAGENERARION");
		
		sql.FROM("FCS_FICHERO_IMPRESO190");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("ANIO = " + anio);
		return sql.toString();
	}
	
	public String getConfImpreso190(Short idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("IDINSTITUCION,"
				+ "ANIO,"
				+ "NOMBREFICHERO,"
				+ "NOMBRE,"
				+ "APELLIDO1,"
				+ "APELLIDO2,"
				+ "TELEFONO,"
				+ "FECHAMODIFICACION");
		
		sql.FROM("FCS_CONF_IMPRESO190");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	}
	
}
