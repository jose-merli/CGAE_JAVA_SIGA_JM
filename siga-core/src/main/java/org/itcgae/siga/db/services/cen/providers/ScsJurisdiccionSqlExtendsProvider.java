package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsJurisdiccionSqlProvider;

public class ScsJurisdiccionSqlExtendsProvider extends ScsJurisdiccionSqlProvider {

	public String getJurisdicciones(String idInstitucion, String nif) {

		SQL sql = new SQL();

		sql.SELECT("jurisdiccion.IDJURISDICCION");
		sql.SELECT("rec.descripcion");

		sql.FROM("SCS_JURISDICCION jurisdiccion");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec on rec.idrecurso= jurisdiccion.DESCRIPCION and rec.idlenguaje = (select idLenguaje from Adm_Usuarios where idInstitucion = "+ idInstitucion +" and nif = '"+ nif +"')");
		
		sql.ORDER_BY("rec.DESCRIPCION");

		return sql.toString();
	}
	
	
	public String getComboJurisdiccion(String idLenguaje ) {
		SQL sql = new SQL();
		
		sql.SELECT("SCS_JURISDICCION.IDJURISDICCION");
		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		
		sql.FROM("SCS_JURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_JURISDICCION.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		
		sql.WHERE("FECHA_BAJA IS NULL");
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'");
		sql.ORDER_BY("GEN_RECURSOS_CATALOGOS.DESCRIPCION");

		return sql.toString();
		
	}

}
