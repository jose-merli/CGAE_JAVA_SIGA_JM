package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo1SqlProvider;

public class CenTiposCVSubtipo1SqlExtendsProvider extends CenTiposcvsubtipo1SqlProvider{
	
	public String search(TipoCurricularItem tipoCurricularItem, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT tiposCVSubt1.CODIGOEXT as CODIGOEXTERNO");
		sql.SELECT("catalogos.DESCRIPCION as DESCRIPCION");
		
		sql.FROM("CEN_TIPOSCVSUBTIPO1 tiposCVSubt1");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos on catalogos.IDRECURSO = tiposCVSubt1.DESCRIPCION");
		
		sql.WHERE("tiposCVSubt1.IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE("catalogos.IDLENGUAJE = '" + idLenguaje + "'");
		
		if (!UtilidadesString.esCadenaVacia(tipoCurricularItem.getTipoCategoriaCurricular())) {
			sql.WHERE("tiposCVSubt1.IDTIPOCV = '" + tipoCurricularItem.getTipoCategoriaCurricular() + "'");
		}

		return sql.toString();
	}
	
	public String getMaxIdCvSubtipo1(String idInstitucion, String idTipoCv) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTIPOCVSUBTIPO1) AS IDTIPOCVSUBTIPO1");
		sql.FROM("CEN_TIPOSCVSUBTIPO1");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("IDTIPOCV = '"+ idTipoCv +"'");
		
		return sql.toString();
	}


}
