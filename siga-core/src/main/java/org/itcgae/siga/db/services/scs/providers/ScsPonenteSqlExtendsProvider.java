package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.db.mappers.ScsPonenteSqlProvider;

public class ScsPonenteSqlExtendsProvider extends ScsPonenteSqlProvider{
	private Logger LOGGER = Logger.getLogger(ScsPonenteSqlExtendsProvider.class);

	public String comboPonente(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("SCS_PONENTE.IDPONENTE, GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		sql.FROM("SCS_PONENTE");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_PONENTE.NOMBRE = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		sql.WHERE("SCS_PONENTE.FECHA_BAJA IS NULL AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE ='"+idLenguaje+"'");
		//Se le limita por isinstitucion como se indica en la documentacion tecnica.
		sql.WHERE("SCS_PONENTE.IDINSTITUCION = "+idInstitucion);
		sql.ORDER_BY("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		return sql.toString();
	}
}
