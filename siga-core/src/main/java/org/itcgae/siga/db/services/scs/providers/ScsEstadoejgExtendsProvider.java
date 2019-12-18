package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
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
	
    public String getEstadoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

        SQL sql = new SQL();
        SQL sql2 = new SQL();
        
        sql2.SELECT("E.IDESTADOEJG");
        sql2.SELECT("REC.DESCRIPCION");
        sql2.SELECT("E.FECHAINICIO");

        sql2.FROM("SCS_ESTADOEJG E");
        sql2.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON MAESTROESTADO.IDESTADOEJG = E.IDESTADOEJG");
        sql2.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND IDLENGUAJE = '" + idLenguaje + "'");

        sql2.WHERE("E.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
        sql2.WHERE("E.IDTIPOEJG = '" + asuntoClave.getClave() + "'");
        sql2.WHERE("E.ANIO  = '" + asuntoClave.getAnio() + "'");
        sql2.WHERE("E.NUMERO  = '" + asuntoClave.getNumero() + "'");
        sql2.WHERE("E.FECHABAJA IS NULL");

        sql2.ORDER_BY("TRUNC(E.FECHAINICIO) DESC, E.IDESTADOPOREJG DESC");
        
        sql.SELECT("*");
        
        sql.FROM("(" + sql2 + ")");
        
        sql.WHERE("ROWNUM = 1");
        
        return sql.toString();
}

}
