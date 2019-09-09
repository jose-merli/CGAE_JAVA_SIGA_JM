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

}
