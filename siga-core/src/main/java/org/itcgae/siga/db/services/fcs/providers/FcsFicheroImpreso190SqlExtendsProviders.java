package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsFicheroImpreso190SqlProvider;

public class FcsFicheroImpreso190SqlExtendsProviders extends FcsFicheroImpreso190SqlProvider {

	public String getImpreso190(String[] anio, Short idInstitucion) {
		String anios = "";
		if(anio != null) {
			for (int i = 0; i < anio.length; i++) {
				anios += anio[i].toString();
				if (i < anio.length - 1)
					anios += ",";
			}
		}
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
		
		if(!UtilidadesString.esCadenaVacia(anios)) {
			sql.WHERE("ANIO in (" + anios + ")");
		}
		sql.ORDER_BY("FECHAGENERARION DESC");
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
	
	public String getComboAnio(Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT ANIO");
		sql.SELECT("ANIO ANIO2");
		sql.FROM("FCS_FICHERO_IMPRESO190");
		sql.WHERE("idinstitucion = " + idInstitucion);
		sql.ORDER_BY("ANIO DESC");
		
		return sql.toString();
	}
	
}
