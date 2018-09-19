package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.db.mappers.CenColegiadoSqlProvider;

public class CenColegiadoSqlExtendsProvider extends CenColegiadoSqlProvider {

	public String selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("col.idpersona");
		sql.SELECT("col.idinstitucion");
		sql.SELECT("per.nifcif AS identificacion");
		
		
		sql.SELECT("per.nombre as SOLONOMBRE");
		sql.SELECT("per.apellidos1");
		sql.SELECT("per.apellidos2");
		sql.SELECT("per.sexo");
		sql.SELECT("per.idestadocivil");
		sql.SELECT("per.idtipoidentificacion");
		sql.SELECT("per.fechanacimiento");
		sql.SELECT("per.naturalde");
		sql.SELECT("cli.fechaalta");
		sql.SELECT("cli.idlenguaje");
		sql.SELECT("cli.asientocontable");
		sql.SELECT("col.nmutualista");
		sql.SELECT("col.fechaincorporacion");
		sql.SELECT("col.fechapresentacion");
		sql.SELECT("col.fechajura");
		sql.SELECT("col.fechatitulacion");
		sql.SELECT("col.idtiposseguro");
		sql.SELECT("cli.comisiones");
		
		sql.SELECT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ', per.apellidos2) ) AS nombre");
		sql.SELECT("decode(col.comunitario,0, col.ncolegiado,col.ncomunitario) as numcolegiado");
		sql.SELECT("cat.descripcion as estadoColegial");
		sql.SELECT(
				"concat( decode(col.situacionresidente,0,'No', 'Sí')  || ' / ',decode(col.comunitario,0,'No', 'Sí')) as residenteInscrito");
		sql.SELECT("per.fechanacimiento");
		sql.SELECT("dir.correoelectronico AS correo");
		sql.SELECT("dir.telefono1 AS telefono");
		sql.SELECT("dir.movil as movil");
		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    (grucli.idinstitucion = inst.idinstitucion and col.idpersona = grucli.idpersona and (TO_DATE(grucli.fecha_inicio,'DD/MM/YYYY') <= SYSDATE and \r\n"
				+ "        ( TO_DATE(grucli.fecha_baja,'DD/MM/YYYY') >= SYSDATE OR grucli.fecha_baja IS NULL)))");
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion))");
		sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '1')");
		sql.INNER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");

		sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("per.idtipoidentificacion not in '20'");

		if (colegiadoItem.getNif() != null && colegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + colegiadoItem.getNif() + "%')");
		}

		if (colegiadoItem.getNombre() != null && colegiadoItem.getNombre() != "") {
			sql.WHERE("upper(per.nombre) like upper('%" + colegiadoItem.getNombre() + "%')");
		}

		if (colegiadoItem.getApellidos() != null && colegiadoItem.getApellidos() != "") {
			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" + colegiadoItem.getApellidos()
					+ "%')");
		}

		if (colegiadoItem.getNumColegiado() != null && colegiadoItem.getNumColegiado() != "") {
			sql.WHERE("col.ncolegiado like '%" + colegiadoItem.getNumColegiado() + "%'");
		}

		if (colegiadoItem.getSexo() != null && colegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + colegiadoItem.getSexo() + "'");
		}

		if (colegiadoItem.getCodigoPostal() != null && colegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + colegiadoItem.getCodigoPostal() + "'");
		}

		if (colegiadoItem.getTipoDireccion() != null && colegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("dir.iddireccion  = '" + colegiadoItem.getTipoDireccion() + "'");
		}

		if (colegiadoItem.getEstadoCivil() != null && colegiadoItem.getEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + colegiadoItem.getEstadoCivil() + "'");
		}

		if (colegiadoItem.getIdProvincia() != null && colegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + colegiadoItem.getIdProvincia() + "'");
		}

		if (colegiadoItem.getIdPoblacion() != null && colegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + colegiadoItem.getIdPoblacion() + "'");
		}

		if (colegiadoItem.getDomicilio() != null && colegiadoItem.getDomicilio() != "") {
			sql.WHERE("(dir.domicilio) like upper('" + colegiadoItem.getDomicilio() + "')");
		}

		if (colegiadoItem.getCorreo() != null && colegiadoItem.getCorreo() != "") {
			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + colegiadoItem.getCorreo() + "%')");
		}

		if (colegiadoItem.getTelefono() != null && colegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + colegiadoItem.getTelefono() + "%'");
		}

		if (colegiadoItem.getMovil() != null && colegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + colegiadoItem.getMovil() + "%'");
		}

		if (colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idcv = '" + colegiadoItem.getTipoCV() + "'");
		}

		if (colegiadoItem.getSituacion() != null && colegiadoItem.getSituacion() != "") {
			sql.WHERE("colest.idestado ='" + colegiadoItem.getSituacion() + "'");
		}

		if (colegiadoItem.getResidencia() != null && colegiadoItem.getResidencia() != "") {
			sql.WHERE("col.situacionresidente ='" + colegiadoItem.getResidencia() + "'");
		}

		if (colegiadoItem.getInscrito() != null && colegiadoItem.getInscrito() != "") {
			sql.WHERE("col.comunitario ='" + colegiadoItem.getInscrito() + "'");
		}

		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {

			String etiquetas = "";

			for (int i = 0; colegiadoItem.getIdgrupo().length > i; i++) {

				if (i == colegiadoItem.getIdgrupo().length - 1) {
					etiquetas += "'" + colegiadoItem.getIdgrupo()[i] + "'";
				} else {
					etiquetas += "'" + colegiadoItem.getIdgrupo()[i] + "',";
				}
			}

			sql.WHERE("grucli.IDGRUPO IN (" + etiquetas + ")");
		}

		if (colegiadoItem.getFechaNacimiento() != null) {
			String fechaNacimiento = dateFormat.format(colegiadoItem.getFechaNacimiento());
			sql.WHERE("(TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') >= TO_DATE('" + fechaNacimiento
					+ "','DD/MM/YYYY') " + " and ( TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') <= TO_DATE('"
					+ fechaNacimiento + "','DD/MM/YYYY')))");
		}

		if (colegiadoItem.getFechaIncorporacion() != null && colegiadoItem.getFechaIncorporacion().length != 0) {

			if (colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);
				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') >= TO_DATE('" + fechaIncorporacionHasta
						+ "','DD/MM/YYYY') " + " and ( TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') <= TO_DATE('"
						+ fechaIncorporacionDesde + "','DD/MM/YYYY')))");
			} else {

				String fechaIncorporacion = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);

				sql.WHERE("(TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') >= TO_DATE('" + fechaIncorporacion
						+ "','DD/MM/YYYY') " + " and ( TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') <= TO_DATE('"
						+ fechaIncorporacion + "','DD/MM/YYYY')))");
			}
		}

		if (colegiadoItem.getEstadoColegial() != null && colegiadoItem.getEstadoColegial() != "") {
			sql.WHERE("cat.descripcion like '" + colegiadoItem.getEstadoColegial() + "'");
		}

		sql.ORDER_BY("NOMBRE");

		return sql.toString();
	}
}
