package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.DocumentacionEjgItem;
import org.itcgae.siga.db.entities.ScsMaestroestadosejg;

public class ScsEstadoejgExtendsProvider extends ScsMaestroestadosejg {
	
	public String comboEstadoEJG(String idLenguaje,String idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("estadoejg.IDESTADOEJG");
		sql.SELECT("catalogoEstadojg.DESCRIPCION");

		sql.FROM("SCS_MAESTROESTADOSEJG estadoejg");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoEstadojg on catalogoEstadojg.idrecurso = estadoejg.DESCRIPCION and catalogoEstadojg.idlenguaje ="+idLenguaje);
		sql.WHERE("estadoejg.fecha_baja is  null");
		
		return sql.toString();
	}
}
