package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForCertificadoscursoSqlProvider;

public class ForCertificadoscursoSqlExtendsProvider extends ForCertificadoscursoSqlProvider {

	public String getCertificatesCourse(String idCurso, String idInstitucion, String idLenguaje) {

		SQL sql = new SQL();
			
		sql.SELECT_DISTINCT("cer.IDCERTIFICADOCURSO");
		sql.SELECT("cer.IDCURSO");
		sql.SELECT("cer.IDPRODUCTO");
		sql.SELECT("cer.IDTIPOPRODUCTO");
		sql.SELECT("cer.IDPRODUCTOINSTITUCION");
		sql.SELECT("cer.IDCALIFICACION");
		sql.SELECT("cer.PRECIO");
		sql.SELECT("cat.DESCRIPCION as CALIFICACION");
		sql.SELECT("pys.DESCRIPCION AS NOMBRECERTIFICADO");

		sql.FROM("FOR_CERTIFICADOSCURSO cer");
		sql.INNER_JOIN("FOR_CALIFICACIONES CAL ON CAL.IDCALIFICACION = cer.IDCALIFICACION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CAL.DESCRIPCION = CAT.IDRECURSO and CAT.IDLENGUAJE = '" + idLenguaje +"'");
		sql.INNER_JOIN("PYS_PRODUCTOSINSTITUCION pys ON pys.IDPRODUCTO = cer.IDPRODUCTO and pys.IDPRODUCTOINSTITUCION = cer.IDPRODUCTOINSTITUCION and pys.IDTIPOPRODUCTO = cer.IDTIPOPRODUCTO and pys.IDINSTITUCION = cer.IDINSTITUCION");

		sql.WHERE("cer.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("cer.idCurso = '" + idCurso + "'");

		return sql.toString();
	}

}
