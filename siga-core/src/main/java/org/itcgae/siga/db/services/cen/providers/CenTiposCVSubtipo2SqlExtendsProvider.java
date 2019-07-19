package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SubtipoCurricularItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo2SqlProvider;

public class CenTiposCVSubtipo2SqlExtendsProvider extends CenTiposcvsubtipo2SqlProvider {

	public String searchSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT tiposCVSubt2.IDTIPOCV as IDTIPOCV");
		sql.SELECT("tiposCVSubt2.IDTIPOCVSUBTIPO2 as IDTIPOCVSUBTIPO2");
		sql.SELECT("tiposCVSubt2.CODIGOEXT as CODIGOEXTERNO");
		sql.SELECT("catalogos.DESCRIPCION as DESCRIPCION");
		sql.SELECT("tiposCVSubt2.IDINSTITUCION as IDINSTITUCION");

		sql.FROM("CEN_TIPOSCVSUBTIPO2 tiposCVSubt2");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos on catalogos.IDRECURSO = tiposCVSubt2.DESCRIPCION");
		sql.INNER_JOIN("CEN_TIPOSCV cenTiposCv on cenTiposCv.IDTIPOCV = tiposCVSubt2.IDTIPOCV");

		sql.WHERE("tiposCVSubt2.IDINSTITUCION in ('2000','" + idInstitucion + "')");
		sql.WHERE("catalogos.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("tiposCVSubt2.FECHA_BAJA IS NULL");

		if (!UtilidadesString.esCadenaVacia(subtipoCurricularItem.getTipoCategoriaCurricular())) {
			sql.WHERE("tiposCVSubt2.IDTIPOCV = '" + subtipoCurricularItem.getTipoCategoriaCurricular() + "'");
		}

		sql.ORDER_BY("tiposCVSubt2.CODIGOEXT ASC");

		return sql.toString();
	}

	public String searchComboSubtipoCurricular(SubtipoCurricularItem subtipoCurricularItem, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT tiposCVSubt2.IDTIPOCV as IDTIPOCV");
		sql.SELECT("tiposCVSubt2.IDTIPOCVSUBTIPO2 as IDTIPOCVSUBTIPO2");
		sql.SELECT("tiposCVSubt2.CODIGOEXT as CODIGOEXTERNO");
		sql.SELECT("catalogos.DESCRIPCION as DESCRIPCION");
		sql.SELECT("tiposCVSubt2.IDINSTITUCION as IDINSTITUCION");

		sql.FROM("CEN_TIPOSCVSUBTIPO2 tiposCVSubt2");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos on catalogos.IDRECURSO = tiposCVSubt2.DESCRIPCION");
		sql.INNER_JOIN("CEN_TIPOSCV cenTiposCv on cenTiposCv.IDTIPOCV = tiposCVSubt2.IDTIPOCV");

		sql.WHERE("tiposCVSubt2.IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE("catalogos.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("tiposCVSubt2.FECHA_BAJA IS NULL");
		if (subtipoCurricularItem.getIdTipoCV() != null) {
			sql.WHERE("tiposCVSubt2.IDTIPOCV= '" + subtipoCurricularItem.getIdTipoCV() + "'");
		}

		// if
		// (!UtilidadesString.esCadenaVacia(subtipoCurricularItem.getTipoCategoriaCurricular()))
		// {
		sql.WHERE("tiposCVSubt2.IDTIPOCV = '" + subtipoCurricularItem.getIdTipoCV() + "'");
		// }

		return sql.toString();
	}

	public String searchCurricularSubtypeCombo(String idTipoCV, boolean historico, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT tiposCVSubt2.IDTIPOCV as IDTIPOCV");
		sql.SELECT("tiposCVSubt2.IDTIPOCVSUBTIPO2 as IDTIPOCVSUBTIPO2");
		sql.SELECT("tiposCVSubt2.CODIGOEXT as CODIGOEXTERNO");
		sql.SELECT("INITCAP(catalogos.DESCRIPCION) as DESCRIPCION");
		sql.SELECT("tiposCVSubt2.IDINSTITUCION as IDINSTITUCION");

		sql.FROM("CEN_TIPOSCVSUBTIPO2 tiposCVSubt2");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos on catalogos.IDRECURSO = tiposCVSubt2.DESCRIPCION");

		sql.WHERE("tiposCVSubt2.IDINSTITUCION in ('2000', '" + idInstitucion + "')");
		sql.WHERE("catalogos.IDLENGUAJE = '" + idLenguaje + "'");

		if (!historico) {
			sql.WHERE("tiposCVSubt2.FECHA_BAJA IS NULL");

		}

		if (idTipoCV != null) {
			sql.WHERE("tiposCVSubt2.IDTIPOCV= '" + idTipoCV + "'");
		}

		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}

	public String getMaxIdCvSubtipo2(String idInstitucion, String idTipoCv) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTIPOCVSUBTIPO2) AS IDTIPOCVSUBTIPO2");
		sql.FROM("CEN_TIPOSCVSUBTIPO2");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOCV = '" + idTipoCv + "'");

		return sql.toString();
	}

	public String getHistory(SubtipoCurricularItem subtipoCurricularItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT tiposCVSubt2.IDTIPOCV as IDTIPOCV");
		sql.SELECT("tiposCVSubt2.IDTIPOCVSUBTIPO2 as IDTIPOCVSUBTIPO2");
		sql.SELECT("tiposCVSubt2.CODIGOEXT as CODIGOEXTERNO");
		sql.SELECT("tiposCVSubt2.FECHA_BAJA as FECHABAJA");
		sql.SELECT("catalogos.DESCRIPCION as DESCRIPCION");
		sql.SELECT("tiposCVSubt2.IDINSTITUCION as IDINSTITUCION");

		sql.FROM("CEN_TIPOSCVSUBTIPO2 tiposCVSubt2");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogos on catalogos.IDRECURSO = tiposCVSubt2.DESCRIPCION");
		sql.INNER_JOIN("CEN_TIPOSCV cenTiposCv on cenTiposCv.IDTIPOCV = tiposCVSubt2.IDTIPOCV");

		sql.WHERE("tiposCVSubt2.IDINSTITUCION in ('2000','" + idInstitucion + "')");
		sql.WHERE("catalogos.IDLENGUAJE = '" + idLenguaje + "'");

		if (!UtilidadesString.esCadenaVacia(subtipoCurricularItem.getTipoCategoriaCurricular())) {
			sql.WHERE("tiposCVSubt2.IDTIPOCV = '" + subtipoCurricularItem.getTipoCategoriaCurricular() + "'");
		}

		sql.ORDER_BY("tiposCVSubt2.CODIGOEXT ASC");

		return sql.toString();
	}
}
