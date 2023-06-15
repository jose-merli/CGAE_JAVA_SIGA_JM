package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.mappers.ScsDesignasletradoSqlProvider;

public class ScsDesignasLetradoSqlExtendsProvider extends ScsDesignasletradoSqlProvider {

	public String getDesignaLetrado(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("COLEGIADO.NCOLEGIADO");
		sql.SELECT("PERSONA.APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT(
				"(COLEGIADO.NCOLEGIADO || ' ' || PERSONA.APELLIDOS1  || ' ' || PERSONA.APELLIDOS2 || ', ' || PERSONA.NOMBRE) AS COLEGIADO");

		sql.FROM("SCS_DESIGNASLETRADO DESIGNALETRADO");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PERSONA ON  DESIGNALETRADO.IDPERSONA = PERSONA.IDPERSONA");
		sql.LEFT_OUTER_JOIN(
				"CEN_CLIENTE CLIENTE ON  DESIGNALETRADO.IDPERSONA = CLIENTE.IDPERSONA AND DESIGNALETRADO.IDINSTITUCION =  CLIENTE.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO COLEGIADO ON  DESIGNALETRADO.IDPERSONA = COLEGIADO.IDPERSONA AND DESIGNALETRADO.IDINSTITUCION =  COLEGIADO.IDINSTITUCION");

		sql.WHERE("DESIGNALETRADO.idinstitucion = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNALETRADO.idturno  = '" + asuntoClave.getClave() + "'");
		sql.WHERE("DESIGNALETRADO.anio   = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNALETRADO.numero   = '" + asuntoClave.getNumero() + "'");

		SQL sql2 = new SQL();

		sql2.SELECT("MAX(LET2.Fechadesigna)");
		sql2.FROM("SCS_DESIGNASLETRADO LET2");
		sql2.WHERE("DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION");
		sql2.WHERE("DESIGNALETRADO.ANIO = LET2.ANIO");
		sql2.WHERE("DESIGNALETRADO.NUMERO = LET2.NUMERO");
		sql2.WHERE("TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)");

		sql.WHERE("(DESIGNALETRADO.fecharenunciasolicita is null)");
		//or DESIGNALETRADO.Fechadesigna = (" + sql2 + "))");
		sql.WHERE("ROWNUM <= 1");
		sql.ORDER_BY("DESIGNALETRADO.fechadesigna");
		return sql.toString();
	}

	public String getDesignaLetradoPorFecha(AsuntosClaveJusticiableItem asuntoClave) {

		SQL sql = new SQL();
		SQL subquery = new SQL();

		subquery.SELECT("MAX(LET2.FECHADESIGNA)");
		subquery.FROM("SCS_DESIGNASLETRADO LET2");
		subquery.WHERE("DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION");
		subquery.WHERE("DESIGNALETRADO.ANIO = LET2.ANIO");
		subquery.WHERE("DESIGNALETRADO.NUMERO = LET2.NUMERO");
		subquery.WHERE("DESIGNALETRADO.IDTURNO = LET2.IDTURNO");
		subquery.WHERE("TRUNC(LET2.FECHADESIGNA) <= TO_DATE('" + asuntoClave.getFechaActuacion() + "', 'DD/MM/RRRR')");

		sql.SELECT("COLEGIADO.NCOLEGIADO");
		sql.SELECT("PERSONA.APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT(
				"(COLEGIADO.NCOLEGIADO || ' - ' || PERSONA.APELLIDOS1  || ' ' || PERSONA.APELLIDOS2 || ', ' || PERSONA.NOMBRE) AS COLEGIADO");
		sql.SELECT("PERSONA.IDPERSONA");

		sql.FROM("SCS_DESIGNASLETRADO DESIGNALETRADO");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA PERSONA ON  DESIGNALETRADO.IDPERSONA = PERSONA.IDPERSONA");
		sql.LEFT_OUTER_JOIN(
				"CEN_CLIENTE CLIENTE ON  DESIGNALETRADO.IDPERSONA = CLIENTE.IDPERSONA AND DESIGNALETRADO.IDINSTITUCION =  CLIENTE.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO COLEGIADO ON  DESIGNALETRADO.IDPERSONA = COLEGIADO.IDPERSONA AND DESIGNALETRADO.IDINSTITUCION =  COLEGIADO.IDINSTITUCION");

		sql.WHERE("DESIGNALETRADO.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNALETRADO.IDTURNO = '" + asuntoClave.getClave() + "'");
		sql.WHERE("DESIGNALETRADO.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNALETRADO.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("TRUNC(DESIGNALETRADO.FECHADESIGNA) <= TO_DATE('" + asuntoClave.getFechaActuacion()
				+ "', 'DD/MM/RRRR')");
		sql.WHERE("( DESIGNALETRADO.FECHADESIGNA = ( " + subquery.toString()
				+ " ))");

		return sql.toString();
	}
	
	public String updateFechaDesignasLetrado(ScsDesignasletrado designaUpdate,Date fechaOriginal) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		SQL sql = new SQL();
		
		String fechaUpdate = dateFormat.format(designaUpdate.getFechadesigna());
		String fechOriginal = dateFormat.format(fechaOriginal);
		sql.UPDATE("SCS_DESIGNASLETRADO");
		if (designaUpdate.getFechadesigna() != null) {
			
			sql.SET("FECHADESIGNA = " + "TO_DATE('" + fechaUpdate + "','DD/MM/YYYY HH24:MI:SS')");
		}
		
		sql.WHERE("IDINSTITUCION = " + designaUpdate.getIdinstitucion());
		sql.WHERE("IDTURNO = " + designaUpdate.getIdturno());
		sql.WHERE("ANIO = " + designaUpdate.getAnio());
		sql.WHERE("NUMERO = " + designaUpdate.getNumero());
		sql.WHERE("TRUNC(FECHADESIGNA) = " +  "TO_DATE('" + fechOriginal + "','dd/MM/yyyy')");
		

		return sql.toString();
	}
	
	
}
