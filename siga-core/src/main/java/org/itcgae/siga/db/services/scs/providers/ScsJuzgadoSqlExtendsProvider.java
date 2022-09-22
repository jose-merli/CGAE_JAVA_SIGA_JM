package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.db.mappers.ScsJuzgadoSqlProvider;

public class ScsJuzgadoSqlExtendsProvider extends ScsJuzgadoSqlProvider {

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
		if (idInstitucion != 2000) {
			sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		}
		if (juzgadoItem.getNombre() != null && juzgadoItem.getNombre() != "") {
			sql.WHERE("UPPER(juzgado.nombre) like UPPER('%" + juzgadoItem.getNombre() + "%')");
		}

		if (juzgadoItem.getCodigoExt() != null && juzgadoItem.getCodigoExt() != "") {
			sql.WHERE("UPPER(juzgado.codigoext) like UPPER('%" + juzgadoItem.getCodigoExt() + "%')");
		}

		if (juzgadoItem.getIdPoblacion() != null && juzgadoItem.getIdPoblacion() != "") {
			sql.WHERE("juzgado.idpoblacion = '" + juzgadoItem.getIdPoblacion() + "'");
		}

		if (juzgadoItem.getIdProvincia() != null && juzgadoItem.getIdProvincia() != "") {
			sql.WHERE("juzgado.idprovincia = '" + juzgadoItem.getIdProvincia() + "'");
		}

		if (!juzgadoItem.getHistorico()) {
			sql.WHERE("fechabaja is null");
		}

		sql.ORDER_BY("juzgado.nombre");

		return sql.toString();
	}

	public String getIdJuzgado(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDJUZGADO) AS IDJUZGADO");
		sql.FROM("SCS_JUZGADO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String comboJuzgado(Short idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("juzgado.IDJUZGADO");
//		sql.SELECT("juzgado.IDJUZGADO, DECODE(CODIGOEXT2,NULL,NOMBRE, NOMBRE || ' - ' || CODIGOEXT2) nombre");
//		sql.SELECT("juzgado.NOMBRE");
		sql.SELECT("'(' || juzgado.CODIGOEXT2 || ') ' || juzgado.NOMBRE || ' (' || P.NOMBRE || ')' AS NOMBRE");
		sql.FROM("SCS_JUZGADO juzgado ");
		sql.INNER_JOIN(" CEN_POBLACIONES P ON P.IDPOBLACION = juzgado.IDPOBLACION ");
		sql.WHERE("juzgado.fechabaja is null");
		sql.WHERE("juzgado.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("juzgado.NOMBRE");

		return sql.toString();
	}

	public String comboJuzgados(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("IDJUZGADO");
		sql.SELECT("CASE WHEN juzgado.FECHABAJA IS NOT NULL "
				+ "THEN '(BAJA)(' || juzgado.CODIGOEXT2 || ') ' || juzgado.NOMBRE || ' (' || P.NOMBRE || ')'"
				+ "ELSE '(' || juzgado.CODIGOEXT2 || ') ' || juzgado.NOMBRE || ' (' || P.NOMBRE || ')'"
				+ "END AS DESCRIPCION");
		sql.FROM("SCS_JUZGADO juzgado ");
		sql.INNER_JOIN(" CEN_POBLACIONES P ON P.IDPOBLACION = juzgado.IDPOBLACION ");
		sql.WHERE("IDINSTITUCION = " + idInstitucion + "");
		sql.ORDER_BY("juzgado.NOMBRE");
		return sql.toString();
	}

	public String comboJuzgadoCdgoExt(Short idLenguaje, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("juzgado.CODIGOEXT");
		sql.SELECT("'(' || juzgado.CODIGOEXT2 || ') ' || juzgado.NOMBRE || ' (' || P.NOMBRE || ')' AS NOMBRE");
		sql.FROM("SCS_JUZGADO juzgado ");
		sql.INNER_JOIN(" CEN_POBLACIONES P ON P.IDPOBLACION = juzgado.IDPOBLACION ");
		sql.WHERE("juzgado.fechabaja is null");
		sql.WHERE("juzgado.idinstitucion = " + idInstitucion);
		sql.WHERE("juzgado.CODIGOEXT is not null");
		sql.ORDER_BY("juzgado.NOMBRE");

		return sql.toString();
	}

	public String comboJuzgadoDesignaciones(Short idLenguaje, Short idInstitucion, String idJuzgado) {

		SQL sql = new SQL();
		SQL sqlJuzgado = new SQL();
		StringBuilder sqlUnion = new StringBuilder();

		sql.SELECT("juzgado.CODIGOEXT2");
		sql.SELECT("juzgado.NOMBRE");
		sql.SELECT("P.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("juzgado.IDJUZGADO");
		sql.FROM("SCS_JUZGADO juzgado");
		sql.INNER_JOIN("CEN_POBLACIONES P ON P.IDPOBLACION = juzgado.IDPOBLACION");
		sql.WHERE("juzgado.fechabaja is null");
		sql.WHERE("juzgado.idinstitucion = " + idInstitucion);

		if (idJuzgado.equals('0')) {
			sql.ORDER_BY("juzgado.NOMBRE");
			return sql.toString();
		} else {
			sqlUnion.append(sql);
			sqlUnion.append(" UNION ");
			sqlJuzgado.SELECT("juzgado.CODIGOEXT2");
			sqlJuzgado.SELECT("juzgado.NOMBRE");
			sqlJuzgado.SELECT("P.NOMBRE AS NOMBREPOBLACION");
			sqlJuzgado.SELECT("juzgado.IDJUZGADO");
			sqlJuzgado.FROM("SCS_JUZGADO juzgado");
			sqlJuzgado.INNER_JOIN("CEN_POBLACIONES P ON P.IDPOBLACION = juzgado.IDPOBLACION");
			sqlJuzgado.WHERE("juzgado.idjuzgado = " + idJuzgado);
			sqlJuzgado.WHERE("juzgado.idinstitucion = " + idInstitucion);
			sqlUnion.append(sqlJuzgado);
			sqlUnion.append(" ORDER BY NOMBRE");
			return sqlUnion.toString();
		}

	}

	public String getJuzgadosByIdTurno(Short idInstitucion, String idTurno) {
		SQL sql = new SQL();

		sql.SELECT("scs_juzgado.idjuzgado");
		sql.SELECT("decode(scs_juzgado.fechabaja, NULL, scs_juzgado.nombre\r\n"
				+ "                                        || ' ('\r\n"
				+ "                                        || cen_poblaciones.nombre\r\n"
				+ "                                        || ')', scs_juzgado.nombre\r\n"
				+ "                                                || ' ('\r\n"
				+ "                                                || cen_poblaciones.nombre\r\n"
				+ "                                                || ') (BAJA)') AS nombre");
		sql.FROM("scs_juzgado");
		sql.INNER_JOIN("cen_poblaciones ON  scs_juzgado.idpoblacion = cen_poblaciones.idpoblacion");
		sql.WHERE("EXISTS (\r\n" + "        SELECT\r\n" + "            *\r\n" + "        FROM\r\n"
				+ "            scs_turno\r\n"
				+ "            inner join scs_materiajurisdiccion on scs_turno.idinstitucion = scs_materiajurisdiccion.idinstitucion AND scs_turno.idmateria = scs_materiajurisdiccion.idmateria AND scs_turno.idarea = scs_materiajurisdiccion.idarea\r\n"
				+ "            inner join scs_procedimientos on scs_materiajurisdiccion.idjurisdiccion = scs_procedimientos.idjurisdiccion AND scs_materiajurisdiccion.idinstitucion = scs_procedimientos.idinstitucion\r\n"
				+ "            inner join scs_juzgadoprocedimiento on scs_procedimientos.idinstitucion = scs_juzgadoprocedimiento.idinstitucion AND scs_procedimientos.idprocedimiento = scs_juzgadoprocedimiento.idprocedimiento\r\n"
				+ "        WHERE\r\n"
				+ "            scs_juzgadoprocedimiento.idinstitucion_juzg = scs_juzgado.idinstitucion \r\n"
				+ "            AND scs_juzgadoprocedimiento.idjuzgado = scs_juzgado.idjuzgado\r\n"
				+ "            and scs_turno.idinstitucion = " + idInstitucion + "\r\n"
				+ "            AND scs_turno.idturno = " + idTurno + "\r\n"
				+ "            AND scs_procedimientos.fechadesdevigor <= sysdate\r\n"
				+ "            AND ( scs_procedimientos.fechahastavigor >= sysdate\r\n"
				+ "                  OR scs_procedimientos.fechahastavigor IS NULL )\r\n" + "    )");
		sql.ORDER_BY("scs_juzgado.fechabaja DESC, nombre");

		return sql.toString();
	}
}
