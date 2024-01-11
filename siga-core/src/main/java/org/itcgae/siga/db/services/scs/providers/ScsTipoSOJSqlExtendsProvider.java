package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiposojSqlProvider;

public class ScsTipoSOJSqlExtendsProvider extends ScsTiposojSqlProvider {

	public String comboTipoSOJ(Short idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tiposoj.IDTIPOSOJ");
		sql.SELECT("f_siga_getrecurso(tiposoj.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOSOJ tiposoj");
		sql.WHERE("tiposoj.fecha_baja is null");
//		sql.WHERE("tiposoj.idinstitucion = " + idInstitucion);
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	public String comboTipoSOJColegio(Short idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipocolegio.IDTIPOSOJCOLEGIO");
		sql.SELECT("f_siga_getrecurso(tipocolegio.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOSOJCOLEGIO tipocolegio");
		sql.WHERE("tipocolegio.fecha_baja is null");
		sql.WHERE("tipocolegio.idinstitucion = " + idInstitucion);
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	public String comboTipoConsulta(Short idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipocon.IDTIPOCONSULTA");
		sql.SELECT("f_siga_getrecurso(tipocon.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOCONSULTA tipocon");
		sql.WHERE("tipocon.fecha_baja is null");
		sql.WHERE("tipocon.idinstitucion = " + idInstitucion);
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	public String comboTipoRespuesta(Short idLenguaje,Short idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipores.IDTIPORESPUESTA");
		sql.SELECT("f_siga_getrecurso(tipores.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPORESPUESTA tipores");
		sql.WHERE("tipores.fecha_baja is null");
		sql.WHERE("tipores.idinstitucion = " + idInstitucion);
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	
}
