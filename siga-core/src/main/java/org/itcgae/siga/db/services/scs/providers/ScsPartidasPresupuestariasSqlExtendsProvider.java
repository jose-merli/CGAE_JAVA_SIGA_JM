package org.itcgae.siga.db.services.scs.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaSqlProvider;
import org.itcgae.siga.db.mappers.ScsProcedimientosSqlProvider;

public class ScsPartidasPresupuestariasSqlExtendsProvider extends ScsPartidapresupuestariaSqlProvider{

	public String searchProcess(String idLenguaje, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("proc.idinstitucion");
		sql.SELECT("proc.idprocedimiento");
		sql.SELECT("proc.nombre");
		sql.SELECT("proc.codigo");
		sql.SELECT("proc.precio as importe");
		sql.SELECT("juris.descripcion as jurisdiccion");

		sql.FROM("SCS_PROCEDIMIENTOS proc");
		sql.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '" + idLenguaje + "')");;
		
		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");
		
		sql.ORDER_BY("proc.nombre");
	
		return sql.toString();
	}

	public String searchPartida(PartidasItem partidasItems) {
	
	SQL sql = new SQL();
	
	sql.SELECT("idinstitucion");
	sql.SELECT("descripcion");
	sql.SELECT("nombrepartida");
	sql.SELECT("fechamodificacion");
	sql.SELECT("usumodificacion");
	sql.SELECT("idpartidapresupuestaria");
	sql.SELECT("fechabaja");
	sql.SELECT("importepartida");
	

	sql.FROM("SCS_PARTIDAPRESUPUESTARIA");
	
	sql.WHERE("idinstitucion = '" + partidasItems.getIdinstitucion() + "'");
	if(partidasItems.getNombrepartida() != null && partidasItems.getNombrepartida() != "") {
		sql.WHERE("UPPER(nombrepartida) like UPPER('%" + partidasItems.getNombrepartida() + "%')");
	}
	if(partidasItems.getDescripcion() != null && partidasItems.getDescripcion() != "") {
		sql.WHERE("UPPER(descripcion) like UPPER('%" + partidasItems.getDescripcion() + "%')");
	}
	if(!partidasItems.isHistorico()) {
		sql.WHERE("fechabaja is null");
	}
	sql.ORDER_BY("nombrepartida"); 

	return sql.toString();
}
	
	public String searchPartidaPres(PartidasItem partidasItems) {
		
		SQL sql = new SQL();
		
		sql.SELECT("idinstitucion");
		sql.SELECT("descripcion");
		sql.SELECT("nombrepartida");
		sql.SELECT("idpartidapresupuestaria");
		sql.SELECT("fechabaja");
		sql.SELECT("importepartida");
		

		sql.FROM("SCS_PARTIDAPRESUPUESTARIA");
		
		sql.WHERE("idinstitucion = '" + partidasItems.getIdinstitucion() + "'");
		if(partidasItems.getNombrepartida() != null && partidasItems.getNombrepartida() != "") {
			sql.WHERE("UPPER(nombrepartida) = UPPER('" + partidasItems.getNombrepartida() + "')");
		}
		if(partidasItems.getDescripcion() != null && partidasItems.getDescripcion() != "") {
			sql.WHERE("UPPER(descripcion) = UPPER('" + partidasItems.getDescripcion() + "')");
		}
		if(!partidasItems.isHistorico()) {
			sql.WHERE("fechabaja is null");
		}
		sql.WHERE("idpartidapresupuestaria != '" + partidasItems.getIdpartidapresupuestaria() + "'");
		sql.ORDER_BY("nombrepartida"); 

		return sql.toString();
	}
	
	public String getIdPartidaPres(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPARTIDAPRESUPUESTARIA) AS IDPARTIDAPRES");
		sql.FROM("SCS_PARTIDAPRESUPUESTARIA");
		sql.WHERE("idinstitucion = '"+ idInstitucion +"'");

		return sql.toString();
	}
}
