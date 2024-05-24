package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsProcedimientosSqlProvider;

public class ScsProcedimientosSqlExtendsProvider extends ScsProcedimientosSqlProvider {

	public String searchProcess(String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("proc.idinstitucion");
		sql.SELECT("proc.idprocedimiento");
		sql.SELECT("proc.nombre");
		sql.SELECT("proc.codigo");
		sql.SELECT("proc.precio as importe");
		sql.SELECT("juris.descripcion as jurisdiccion");

		sql.FROM("SCS_PROCEDIMIENTOS proc");
		sql.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '"
				+ idLenguaje + "')");
		;

		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");

		sql.ORDER_BY("proc.nombre");

		return sql.toString();
	}

	public String getProcedimientos(String idInstitucion, String idProcedimiento, String idioma) {

		String sql = "";
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();

		sql1.SELECT("pretension.idinstitucion");
		sql1.SELECT("pretension.idpretension");
		sql1.SELECT("f_siga_getrecurso(pretension.descripcion, " + idioma + ") nombre");
		sql1.FROM("SCS_PROCEDIMIENTOS procedimiento");
		sql1.LEFT_OUTER_JOIN(
				"SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)");
		sql1.LEFT_OUTER_JOIN(
				"SCS_PRETENSION pretension on (prepro.idpretension = pretension.idpretension AND pretension.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)");
		sql1.WHERE("procedimiento.idinstitucion = '" + idInstitucion + "'");
		sql1.WHERE("procedimiento.idprocedimiento = '" + idProcedimiento + "'");

		sql2.SELECT("pretension.idinstitucion");
		sql2.SELECT("pretension.idpretension");
		sql2.SELECT("f_siga_getrecurso(pretension.descripcion, " + idioma + ") nombre");
		sql2.FROM("SCS_PRETENSION pretension");
		sql2.WHERE("pretension.idinstitucion = '" + idInstitucion + "'");
		sql2.WHERE("pretension.fechabaja is null");

		sql = "SELECT * FROM (" + sql1.toString() + " UNION " + sql2.toString() + ") ORDER BY NOMBRE";

		return sql;
	}

//
//SELECT procedimiento.idprocedimiento, LISTAGG(prepro.idpretension, ',') WITHIN GROUP (ORDER BY prepro.idprocedimiento) AS procedimientos
//, procedimiento.nombre, procedimiento.codigo, procedimiento.precio as importe, procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones
//FROM SCS_PROCEDIMIENTOS procedimiento
//Left JOIN SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)
//
//WHERE (procedimiento.idinstitucion = '2003'  AND UPPER(procedimiento.nombre) like UPPER('%%') AND procedimiento.fechabaja is null)
//GROUP BY procedimiento.idprocedimiento,  procedimiento.nombre, procedimiento.codigo, procedimiento.precio , procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones
//ORDER BY nombre;

	public String searchModulo(ModulosItem moduloItem, String idioma, String idJuzgado) {

		SQL sql2 = new SQL();
		if (idJuzgado != null) {

			sql2.SELECT("proc.idprocedimiento");

			sql2.FROM("SCS_JUZGADOPROCEDIMIENTO procedimiento");
			sql2.INNER_JOIN(
					"SCS_PROCEDIMIENTOS proc on proc.idprocedimiento = procedimiento.idprocedimiento and procedimiento.idinstitucion = proc.idinstitucion");
			sql2.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
			sql2.INNER_JOIN(
					"GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '"
							+ idioma + "')");
			;

			sql2.WHERE("proc.idinstitucion = '" + moduloItem.getIdInstitucion() + "'");

			if (!moduloItem.isHistorico()) {
				sql2.WHERE(
						"(proc.fechadesdevigor <= sysdate AND (proc.FECHAHASTAVIGOR > sysdate OR proc.fechahastavigor is null))");
			}

			sql2.WHERE("procedimiento.idjuzgado = '" + idJuzgado + "'");

			sql2.toString();
		}

		SQL sql = new SQL();

		sql.SELECT("procedimiento.idprocedimiento");
		sql.SELECT("procedimiento.nombre");
		sql.SELECT("procedimiento.codigo");
		sql.SELECT(
				"LISTAGG(pretension.idpretension, ',') WITHIN GROUP (ORDER BY procedimiento.idprocedimiento) AS procedimientos");
		sql.SELECT("procedimiento.precio as importe");
		sql.SELECT("procedimiento.complemento");
		sql.SELECT("procedimiento.vigente");
		sql.SELECT("procedimiento.idjurisdiccion");
		sql.SELECT("procedimiento.orden");
		sql.SELECT("procedimiento.codigoext");
		sql.SELECT("procedimiento.permitiraniadirletrado");
		sql.SELECT("procedimiento.fechadesdevigor");
		sql.SELECT("procedimiento.fechahastavigor");
		sql.SELECT("procedimiento.fechabaja");
		sql.SELECT("procedimiento.observaciones");
		sql.SELECT("juris.descripcion AS jurisdicciondes");

		sql.FROM("SCS_PROCEDIMIENTOS PROCEDIMIENTO");

		sql.WHERE("procedimiento.idinstitucion = '" + moduloItem.getIdInstitucion() + "'");

		sql.LEFT_OUTER_JOIN(
				"SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN(
				"SCS_PRETENSION pretension on (prepro.idpretension = pretension.idpretension AND pretension.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION AND PRETENSION.FECHABAJA IS NULL)");
		sql.INNER_JOIN("scs_jurisdiccion jurisdiccion ON jurisdiccion.idjurisdiccion = procedimiento.idjurisdiccion");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos juris ON (\r\n" + "            juris.idrecurso = jurisdiccion.descripcion\r\n"
						+ "        AND\r\n" + "            idlenguaje = '" + idioma + "' \r\n" + "    )");

		if (moduloItem.getPrecio() == null) {
			if (moduloItem.getNombre() != null && moduloItem.getNombre() != "") {
				sql.WHERE("UPPER(procedimiento.nombre) like UPPER('%" + moduloItem.getNombre() + "%')");
			}
			if (moduloItem.getIdProcedimiento() != null && moduloItem.getIdProcedimiento() != "") {
				sql.WHERE("procedimiento.idprocedimiento = '" + moduloItem.getIdProcedimiento() + "'");
			}
			if (moduloItem.getCodigo() != null && moduloItem.getCodigo() != "") {
				sql.WHERE("UPPER(procedimiento.codigo) like UPPER('%" + moduloItem.getCodigo() + "%')");
			}
		} else {
			if (moduloItem.getNombre() != null && moduloItem.getNombre() != "") {
				sql.WHERE("UPPER(procedimiento.nombre) = UPPER('" + moduloItem.getNombre() + "')");
			}
			if (moduloItem.getIdProcedimiento() != null && moduloItem.getIdProcedimiento() != "") {
				sql.WHERE("procedimiento.idprocedimiento = '" + moduloItem.getIdProcedimiento() + "'");
			}
			if (moduloItem.getCodigo() != null && moduloItem.getCodigo() != "") {
				sql.WHERE("UPPER(procedimiento.codigo) = UPPER('" + moduloItem.getCodigo() + "')");
			}
		}

		if (!moduloItem.isHistorico()) {
			sql.WHERE("procedimiento.fechabaja is null");
			
			if (moduloItem.getFechadesdevigor() != null) {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				String fechaFormateada = formato.format(moduloItem.getFechadesdevigor());
				
				sql.WHERE("procedimiento.fechadesdevigor <= TO_DATE('" + fechaFormateada + "', 'dd/MM/yyyy')");
				sql.WHERE("(procedimiento.fechahastavigor >= TO_DATE('" + fechaFormateada + "', 'dd/MM/yyyy') OR procedimiento.fechahastavigor IS NULL)");
			}
		}

		if (idJuzgado != null) {
			sql.WHERE("procedimiento.idprocedimiento NOT IN(" + sql2 + ")");
		}

		sql.GROUP_BY(
				"procedimiento.idprocedimiento,  procedimiento.nombre, procedimiento.codigo, procedimiento.precio , procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones, juris.descripcion");
		sql.ORDER_BY("nombre");

		return sql.toString();
	}

	/*
	 * public String searchModulo(ModulosItem moduloItem, String idioma) {
	 * 
	 * SQL sql = new SQL();
	 * 
	 * sql.SELECT("procedimiento.idprocedimiento");
	 * sql.SELECT("procedimiento.nombre"); sql.SELECT("procedimiento.codigo"); sql.
	 * SELECT("LISTAGG(pretension.idpretension, ',') WITHIN GROUP (ORDER BY procedimiento.idprocedimiento) AS procedimientos"
	 * ); sql.SELECT("procedimiento.precio as importe");
	 * sql.SELECT("procedimiento.complemento"); sql.SELECT("procedimiento.vigente");
	 * sql.SELECT("procedimiento.idjurisdiccion");
	 * sql.SELECT("procedimiento.orden"); sql.SELECT("procedimiento.codigoext");
	 * sql.SELECT("procedimiento.permitiraniadirletrado");
	 * sql.SELECT("procedimiento.fechadesdevigor");
	 * sql.SELECT("procedimiento.fechahastavigor");
	 * sql.SELECT("procedimiento.fechabaja");
	 * sql.SELECT("procedimiento.observaciones");
	 * sql.SELECT("juris.descripcion AS jurisdicciondes");
	 * 
	 * sql.FROM("SCS_PROCEDIMIENTOS PROCEDIMIENTO");
	 * 
	 * sql.WHERE("procedimiento.idinstitucion = '" + moduloItem.getidInstitucion() +
	 * "'");
	 * 
	 * sql.
	 * LEFT_OUTER_JOIN("SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)"
	 * ); sql.
	 * LEFT_OUTER_JOIN("SCS_PRETENSION pretension on (prepro.idpretension = pretension.idpretension AND pretension.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION AND PRETENSION.FECHABAJA IS NULL)"
	 * ); sql.
	 * INNER_JOIN("scs_jurisdiccion jurisdiccion ON jurisdiccion.idjurisdiccion = procedimiento.idjurisdiccion"
	 * ); sql.LEFT_OUTER_JOIN("gen_recursos_catalogos juris ON (\r\n" +
	 * "            juris.idrecurso = jurisdiccion.descripcion\r\n" +
	 * "        AND\r\n" + "            idlenguaje = '"+ idioma +"' \r\n" +
	 * "    )");
	 * 
	 * if(moduloItem.getPrecio() == null) { if(moduloItem.getNombre() != null &&
	 * moduloItem.getNombre() != "") {
	 * sql.WHERE("UPPER(procedimiento.nombre) like UPPER('%" +
	 * moduloItem.getNombre() + "%')"); } if(moduloItem.getIdProcedimiento() != null
	 * && moduloItem.getIdProcedimiento() != "") {
	 * sql.WHERE("procedimiento.idprocedimiento = '" +
	 * moduloItem.getIdProcedimiento() + "'"); } if(moduloItem.getCodigo() != null
	 * && moduloItem.getCodigo() != "") {
	 * sql.WHERE("UPPER(procedimiento.codigo) like UPPER('%" +
	 * moduloItem.getCodigo() + "%')"); } }else { if(moduloItem.getNombre() != null
	 * && moduloItem.getNombre() != "") {
	 * sql.WHERE("UPPER(procedimiento.nombre) = UPPER('" + moduloItem.getNombre() +
	 * "')"); } if(moduloItem.getIdProcedimiento() != null &&
	 * moduloItem.getIdProcedimiento() != "") {
	 * sql.WHERE("procedimiento.idprocedimiento = '" +
	 * moduloItem.getIdProcedimiento() + "'"); } if(moduloItem.getCodigo() != null
	 * && moduloItem.getCodigo() != "") {
	 * sql.WHERE("UPPER(procedimiento.codigo) = UPPER('" + moduloItem.getCodigo() +
	 * "')"); } }
	 * 
	 * if(!moduloItem.isHistorico()) { if(moduloItem.isVerSoloAlta()) { sql.
	 * WHERE("(procedimiento.fechadesdevigor <= sysdate AND procedimiento.fechahastavigor is null)"
	 * ); } else { sql.
	 * WHERE("(procedimiento.fechadesdevigor <= sysdate AND (procedimiento.FECHAHASTAVIGOR > sysdate OR procedimiento.fechahastavigor is null))"
	 * ); }
	 * 
	 * sql.WHERE("(procedimiento.fechabaja is null)"); }
	 * 
	 * sql.
	 * GROUP_BY("procedimiento.idprocedimiento,  procedimiento.nombre, procedimiento.codigo, procedimiento.precio , procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones, juris.descripcion"
	 * ); sql.ORDER_BY("nombre");
	 * 
	 * return sql.toString(); }
	 */

	public String getIdProcedimiento(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(to_number(IDPROCEDIMIENTO)) AS IDPROCEDIMIENTO");
		sql.FROM("SCS_PROCEDIMIENTOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getComplementoProcedimiento(String idInstitucion, String idProcedimiento) {
		SQL sql = new SQL();

		sql.SELECT("COMPLEMENTO");
		sql.FROM("SCS_PROCEDIMIENTOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDPROCEDIMIENTO = '" + idProcedimiento + "'");

		return sql.toString();
	}

}
