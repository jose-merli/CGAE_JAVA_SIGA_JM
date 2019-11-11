package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.FundamentosCalificacionItem;
import org.itcgae.siga.db.mappers.ScsTipofundamentocalifSqlProvider;

public class ScsFundamentoscalificacionSqlExtendsProvider extends ScsTipofundamentocalifSqlProvider{

	public String searchFundamentos(String idLenguaje, String idInstitucion, FundamentosCalificacionItem fundamentosCalificacionItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT("fundamento.idfundamentocalif");
		sql.SELECT("fundamento.codigo");
		sql.SELECT("catalogoFundamento.descripcion");
		sql.SELECT("fundamento.idinstitucion");
		sql.SELECT("fundamento.codigoejis");
		sql.SELECT("fundamento.textoplantilla");
		sql.SELECT("catalogoDictamen.descripcion as descripciondictamen");
		sql.SELECT("fundamento.fechabaja");
		sql.SELECT("fundamento.IDTIPODICTAMENEJG");
		sql.SELECT("catalogoFundamento.IDRECURSO");
		
		sql.FROM("SCS_TIPOFUNDAMENTOCALIF fundamento");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogoFundamento on catalogoFundamento.idrecurso = fundamento.DESCRIPCION and catalogoFundamento.idlenguaje = "+idLenguaje);
		sql.LEFT_OUTER_JOIN("SCS_TIPODICTAMENEJG tipodictamen on fundamento.IDTIPODICTAMENEJG = tipodictamen.IDTIPODICTAMENEJG and fundamento.IDINSTITUCION = tipodictamen.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoDictamen on catalogoDictamen.idrecurso = tipodictamen.DESCRIPCION and catalogoDictamen.idlenguaje = "+idLenguaje);
		
		sql.WHERE("fundamento.idinstitucion = '"+idInstitucion+"'");
		if(fundamentosCalificacionItem.getDescripcionFundamento()!= null)
			sql.WHERE("upper(catalogoFundamento.descripcion) like upper('%"+fundamentosCalificacionItem.getDescripcionFundamento()+"%')");
		if(fundamentosCalificacionItem.getDescripcionDictamen()!= null)
			sql.WHERE("fundamento.IDTIPODICTAMENEJG = "+fundamentosCalificacionItem.getDescripcionDictamen()+"");
		if(!fundamentosCalificacionItem.getHistorico())
			sql.WHERE("fundamento.fechabaja is null");
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}
	
	public String getIdFundamento(Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDFUNDAMENTOCALIF) as IDFUNDAMENTOCALIF");
		sql.FROM("SCS_TIPOFUNDAMENTOCALIF");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}

	
	
}