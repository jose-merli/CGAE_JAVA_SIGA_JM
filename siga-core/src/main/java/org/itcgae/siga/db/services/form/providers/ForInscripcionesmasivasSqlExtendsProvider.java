package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForInscripcionesmasivasSqlProvider;

public class ForInscripcionesmasivasSqlExtendsProvider extends ForInscripcionesmasivasSqlProvider {

	public String getMassiveLoadInscriptions(String idCurso, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("mas.IDCARGAINSCRIPCION");
		sql.SELECT("TO_CHAR(mas.FECHAMODIFICACION,'DD/MM/YYYY') AS FECHACARGA");
		sql.SELECT("mas.NOMBREFICHERO");
		sql.SELECT("mas.NUMEROLINEASTOTALES");
		sql.SELECT("mas.INSCRIPCIONESCORRECTAS");
		sql.SELECT("mas.IDCURSO");

		sql.FROM("FOR_INSCRIPCIONESMASIVAS mas");

		sql.WHERE("mas.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("mas.idCurso = '" + idCurso + "'");


		return sql.toString();
	}

}
