package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.RetencionIRPFItem;
import org.itcgae.siga.db.mappers.ScsMaestroretencionesSqlProvider;

public class ScsRetencionirpfSqlExtendsProvider extends ScsMaestroretencionesSqlProvider{


	public String searchRetenciones(String idLenguaje, RetencionIRPFItem retencionItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT("ret.idretencion");
		sql.SELECT("ret.retencion");
		sql.SELECT("ret.letranifsociedad");
		sql.SELECT("ret.CLAVEM190");
		sql.SELECT("cat.DESCRIPCION");
		sql.SELECT("cat.idrecurso");
		sql.SELECT("ret.fechabaja");
		sql.SELECT("cats.descripcion as DESCRIPCIONSOC");
		
		sql.FROM("SCS_MAESTRORETENCIONES ret");
		
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos cat on ret.descripcion = cat.idrecurso and cat.idlenguaje = "+idLenguaje);
		sql.LEFT_OUTER_JOIN("cen_tiposociedad soc on soc.letracif = ret.letranifsociedad");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos cats on cats.idrecurso = soc.descripcion and cats.idlenguaje ="+idLenguaje);
//		sql.WHERE("cat.idinstitucion = '" + retencionItem.getIdInstituxcion() + "'");
		
		
		if(retencionItem.getDescripcion() != null && retencionItem.getDescripcion()!= "") {
			sql.WHERE("upper(cat.DESCRIPCION) like upper('%"+retencionItem.getDescripcion()+"%')");
		}
		if(retencionItem.getRetencion() != null && retencionItem.getRetencion() != "") {
			sql.WHERE("ret.RETENCION= " + retencionItem.getRetencion());
		}
		if(retencionItem.getTipoSociedad() != null && retencionItem.getTipoSociedad() != "") {
			sql.WHERE("ret.letranifsociedad = '" + retencionItem.getTipoSociedad()+"'");
		}
	
		
		if(!retencionItem.getHistorico())
			sql.WHERE("RET.fechabaja is null");
		
		sql.ORDER_BY("CAT.DESCRIPCION"); 
	
		return sql.toString();
	}
	

	public String getIdRetencion() {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDRETENCION) AS IDRETENCION");
		sql.FROM("SCS_MAESTRORETENCIONES");

		return sql.toString();
	}

}
