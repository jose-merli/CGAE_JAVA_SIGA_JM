package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsRenunciaSqlProvider;

public class ScsRenunciaSqlExtendsProvider extends ScsRenunciaSqlProvider{
	
	public String comboRenuncia(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("renuncia.IDRENUNCIA");
		sql.SELECT("f_siga_getrecurso(renuncia.descripcion,"+idLenguaje+") DESCRIPCION");
		
		sql.FROM("scs_renuncia renuncia");
		sql.WHERE("renuncia.fecha_baja is null");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
}
