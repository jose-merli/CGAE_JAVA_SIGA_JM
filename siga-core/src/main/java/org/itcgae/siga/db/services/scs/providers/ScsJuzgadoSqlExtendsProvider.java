package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.db.mappers.ScsJuzgadoSqlProvider;

public class ScsJuzgadoSqlExtendsProvider extends ScsJuzgadoSqlProvider{

	public String searchCourt(JuzgadoItem juzgadoItem, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("juzgado.idinstitucion");
		sql.SELECT("juzgado.idjuzgado");
		sql.SELECT("juzgado.nombre");
		sql.SELECT("juzgado.domicilio");
		sql.SELECT("juzgado.codigopostal");
		sql.SELECT("juzgado.idpoblacion");
		sql.SELECT("juzgado.idprovincia");
		sql.SELECT("juzgado.telefono1");
		sql.SELECT("juzgado.telefono2");
		sql.SELECT("juzgado.fax1");
		sql.SELECT("juzgado.fechabaja");
		sql.SELECT("juzgado.codigoext");
		sql.SELECT("juzgado.codigoprocurador");
		sql.SELECT("juzgado.visible");
		sql.SELECT("juzgado.movil");
		sql.SELECT("juzgado.esdecano");
		sql.SELECT("juzgado.email");
		sql.SELECT("juzgado.codigoext2");
		sql.SELECT("juzgado.iscodigoejis");
		sql.SELECT("juzgado.fechacodigoejis");
		sql.SELECT("juzgado.codigoejis");
		sql.SELECT("juzgado.visiblemovil");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");

		sql.FROM("SCS_JUZGADO juzgado");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = juzgado.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = juzgado.IDPOBLACION");
		if(idInstitucion != 2000) {
			sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		}
		if(juzgadoItem.getNombre() != null && juzgadoItem.getNombre() != "") {
			sql.WHERE("UPPER(juzgado.nombre) like UPPER('%"+ juzgadoItem.getNombre() + "%')");
		}
		
		if(juzgadoItem.getCodigoExt() != null && juzgadoItem.getCodigoExt() != "") {
			sql.WHERE("UPPER(juzgado.codigoext) like UPPER('%"+ juzgadoItem.getCodigoExt() + "%')");
		}
		
		if(juzgadoItem.getIdPoblacion() != null && juzgadoItem.getIdPoblacion() != "") {
			sql.WHERE("juzgado.idpoblacion = '"+ juzgadoItem.getIdPoblacion() + "'");
		}
		
		if(juzgadoItem.getIdProvincia() != null && juzgadoItem.getIdProvincia() != "") {
			sql.WHERE("juzgado.idprovincia = '"+ juzgadoItem.getIdProvincia() + "'");
		}
		
		if(!juzgadoItem.getHistorico()) {
			sql.WHERE("fechabaja is null");
		}
		
		sql.ORDER_BY("juzgado.nombre");
	
		return sql.toString();
	}
	
	public String getIdJuzgado(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDJUZGADO) AS IDJUZGADO");
		sql.FROM("SCS_JUZGADO");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
	public String comboJuzgados(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("IDJUZGADO");
		sql.SELECT("DECODE(CODIGOEXT2,NULL,NOMBRE, CODIGOEXT2 || '-' || NOMBRE) AS DESCRIPCION");
		sql.FROM("SCS_JUZGADO");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
	
	public String comboJuzgado(Short idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();
	 
		sql.SELECT("juzgado.IDJUZGADO");
		sql.SELECT("juzgado.NOMBRE");
		sql.FROM("SCS_JUZGADO juzgado");
		sql.WHERE("juzgado.fechabaja is null");
		sql.WHERE("juzgado.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("juzgado.NOMBRE");
	
		return sql.toString();
	}
	
}
