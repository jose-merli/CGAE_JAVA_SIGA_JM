package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.mappers.ScsDesignasletradoSqlProvider;

public class ScsDesignasLetradoSqlExtendsProvider extends ScsDesignasletradoSqlProvider {

	public String getDesignaLetrado(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("COLEGIADO.NCOLEGIADO");
		sql.SELECT("PERSONA.APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT("(COLEGIADO.NCOLEGIADO || ' ' || PERSONA.APELLIDOS1  || ' ' || PERSONA.APELLIDOS2 || ', ' || PERSONA.NOMBRE) AS COLEGIADO");
		sql.FROM("SCS_DESIGNASLETRADO DESIGNALETRADO");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PERSONA ON  DESIGNALETRADO.IDPERSONA = PERSONA.IDPERSONA");
		sql.LEFT_OUTER_JOIN("CEN_CLIENTE CLIENTE ON  DESIGNALETRADO.IDPERSONA = CLIENTE.IDPERSONA AND DESIGNALETRADO.IDINSTITUCION =  CLIENTE.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_COLEGIADO COLEGIADO ON  DESIGNALETRADO.IDPERSONA = COLEGIADO.IDPERSONA AND DESIGNALETRADO.IDINSTITUCION =  COLEGIADO.IDINSTITUCION");
		sql.WHERE("DESIGNALETRADO.idinstitucion = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNALETRADO.idturno  = '" + asuntoClave.getClave() + "'");
		sql.WHERE("DESIGNALETRADO.anio   = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNALETRADO.numero   = '" + asuntoClave.getNumero() + "'");
		
		SQL sql2 = new SQL();
		
		sql2.SELECT("MAX(LET2.Fechadesigna)");
		sql2.FROM("SCS_DESIGNASLETRADO LET2");
		sql2.WHERE("DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION");
		sql2.WHERE("DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION");
		sql2.WHERE("DESIGNALETRADO.ANIO = LET2.ANIO");
		sql2.WHERE("DESIGNALETRADO.NUMERO = LET2.NUMERO");
		sql2.WHERE("TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)");
	
		
		sql.WHERE("(DESIGNALETRADO.fecharenuncia is null or DESIGNALETRADO.Fechadesigna = (" + sql2 + "))");
		sql.ORDER_BY("DESIGNALETRADO.fechadesigna");
		return sql.toString();
	}

}
