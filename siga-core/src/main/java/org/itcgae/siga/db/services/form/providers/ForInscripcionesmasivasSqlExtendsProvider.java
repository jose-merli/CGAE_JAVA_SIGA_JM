package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForInscripcionesmasivasSqlProvider;

public class ForInscripcionesmasivasSqlExtendsProvider extends ForInscripcionesmasivasSqlProvider {

	public String getMassiveLoadInscriptions(String idCurso, String idInstitucion, boolean historico) {

		SQL sql = new SQL();

		sql.SELECT("mas.IDCARGAINSCRIPCION");
		sql.SELECT("TO_CHAR(mas.FECHAMODIFICACION,'DD/MM/YYYY') AS FECHACARGA");
		sql.SELECT("mas.NOMBREFICHERO");
		sql.SELECT("mas.NUMEROLINEASTOTALES");
		sql.SELECT("mas.INSCRIPCIONESCORRECTAS");
		sql.SELECT("mas.IDCURSO");
		sql.SELECT("mas.IDFICHERO");
		sql.SELECT("mas.IDFICHEROLOG");
		sql.SELECT("mas.FECHABAJA");

		sql.FROM("FOR_INSCRIPCIONESMASIVAS mas");

		sql.WHERE("mas.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("mas.idCurso = '" + idCurso + "'");
		
		if(!historico) {
			sql.WHERE("mas.FECHABAJA is null");
		}


		return sql.toString();
	}

}
