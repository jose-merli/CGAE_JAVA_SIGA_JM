package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsRenunciaSqlProvider;

public class ScsRenunciaSqlExtendsProvider extends ScsRenunciaSqlProvider{
	
	public String comboRenuncia(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("renuncia.IDRENUNCIA");
		sql.SELECT("catalogoRenuncia.DESCRIPCION");

		sql.FROM("scs_renuncia renuncia");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoRenuncia on catalogoRenuncia.idrecurso = renuncia.DESCRIPCION and catalogoRenuncia.idlenguaje ="+idLenguaje);
		sql.WHERE("renuncia.fecha_baja is null");
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}
}
