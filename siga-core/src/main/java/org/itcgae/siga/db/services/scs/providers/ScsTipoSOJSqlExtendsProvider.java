package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiposojSqlProvider;

public class ScsTipoSOJSqlExtendsProvider extends ScsTiposojSqlProvider {

	public String comboTipoSOJ(Short idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("tiposoj.IDTIPOSOJ");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOSOJ tiposoj");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tiposoj.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tiposoj.fecha_baja is null");
//		sql.WHERE("tiposoj.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
	public String comboTipoSOJColegio(Short idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipocolegio.IDTIPOSOJCOLEGIO");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOSOJCOLEGIO tipocolegio");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipocolegio.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipocolegio.fecha_baja is null");
		sql.WHERE("tipocolegio.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
	public String comboTipoConsulta(Short idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipocon.IDTIPOCONSULTA");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOCONSULTA tipocon");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipocon.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipocon.fecha_baja is null");
		sql.WHERE("tipocon.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
	public String comboTipoRespuesta(Short idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipores.IDTIPORESPUESTA");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPORESPUESTA tipores");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipores.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipores.fecha_baja is null");
		sql.WHERE("tipores.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
	
}
