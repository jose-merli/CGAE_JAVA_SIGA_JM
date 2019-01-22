package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.TipoCurricularItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenTiposcvSqlProvider;

public class CenTiposcvSqlExtendsProvider extends CenTiposcvSqlProvider {

	public String selectCategoriaCV(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("CV.IDTIPOCV");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_TIPOSCV CV");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CV.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("CV.FECHA_BAJA IS NULL");
		sql.WHERE("CV.BLOQUEADO = 'N'");
		sql.ORDER_BY("CAT.DESCRIPCION");

		return sql.toString();
	}

	public String getHistory(TipoCurricularItem tipoCurricularItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT tiposCVSubt1.IDTIPOCV as IDTIPOCV");
		sql.SELECT("tiposCVSubt1.IDTIPOCVSUBTIPO1 as IDTIPOCVSUBTIPO1");
		sql.SELECT("tiposCVSubt1.CODIGOEXT as CODIGOEXTERNO");
		sql.SELECT("tiposCVSubt1.FECHA_BAJA as FECHABAJA");
		sql.SELECT("catalogos.DESCRIPCION as DESCRIPCION");

		sql.FROM("CEN_TIPOSCVSUBTIPO1 tiposCVSubt1");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos on catalogos.IDRECURSO = tiposCVSubt1.DESCRIPCION");
		sql.INNER_JOIN("CEN_TIPOSCV cenTiposCv on cenTiposCv.IDTIPOCV = tiposCVSubt1.IDTIPOCV");
		
		sql.WHERE("tiposCVSubt1.IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE("catalogos.IDLENGUAJE = '" + idLenguaje + "'");

		if (!UtilidadesString.esCadenaVacia(tipoCurricularItem.getTipoCategoriaCurricular())) {
			sql.WHERE("tiposCVSubt1.IDTIPOCV = '" + tipoCurricularItem.getTipoCategoriaCurricular() + "'");
		}

        sql.ORDER_BY("catalogos.DESCRIPCION ASC " );

		return sql.toString();
	}
}
