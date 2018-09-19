package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenColegiadoSqlProvider;

public class CenColegiadoSqlExtendsProvider extends CenColegiadoSqlProvider {

	public String selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();
		SQL sql1 = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT_DISTINCT("col.idpersona");
		sql.SELECT_DISTINCT("col.idinstitucion");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ', per.apellidos2) ) AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("cli.fechaalta");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("col.nmutualista");
		sql.SELECT_DISTINCT("col.fechaincorporacion");
		sql.SELECT_DISTINCT("col.fechajura");
		sql.SELECT_DISTINCT("col.fechatitulacion");
		sql.SELECT_DISTINCT("col.idtiposseguro");
		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("decode(col.comunitario,0, col.ncolegiado,col.ncomunitario) as numcolegiado");
		sql.SELECT_DISTINCT("cat.descripcion as estadoColegial");
		sql.SELECT_DISTINCT(
				"concat( decode(col.situacionresidente,0,'No', 'Sí')  || ' / ',decode(col.comunitario,0,'No', 'Sí')) as residenteInscrito");
		sql.SELECT_DISTINCT("per.fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");
		
		sql1.SELECT("partidojudicial.nombre");
		sql1.FROM("cen_partidojudicial partidojudicial");
		sql1.INNER_JOIN("cen_poblaciones pob on pob.idpartido = partidojudicial.idpartido");
		sql1.INNER_JOIN("cen_direcciones direcciones on pob.idpoblacion = direcciones.idpoblacion");
		sql1.INNER_JOIN("CEN_DIRECCION_TIPODIRECCION tipodireccion ON (tipodireccion.IDDIRECCION = direcciones.IDDIRECCION AND"
				+ " tipodireccion.IDPERSONA = direcciones.IDPERSONA AND  tipodireccion.IDINSTITUCION = direcciones.IDINSTITUCION)");
		sql1.WHERE("tipodireccion.idtipodireccion = '2'");
		sql1.WHERE("direcciones.idpersona = dir.idpersona");
		sql1.WHERE("direcciones.idinstitucion = dir.idinstitucion");
		sql1.WHERE("direcciones.iddireccion = dir.iddireccion");
		
		sql.SELECT_DISTINCT("("+ sql1 + ") as partidoJudicial");
		sql.SELECT_DISTINCT("dir.movil as movil");
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
			sql.WHERE("(TO_DATE(per.fechanacimiento,'DD/MM/RRRR') >= TO_DATE('" + fechaNacimiento + "','DD/MM/YYYY')"
					+ " and ( TO_DATE(per.fechanacimiento,'DD/MM/RRRR') <= TO_DATE('" + fechaNacimiento + "','DD/MM/YYYY')))");
		}

		if (colegiadoItem.getFechaIncorporacion() != null && colegiadoItem.getFechaIncorporacion().length != 0) {

			if (colegiadoItem.getFechaIncorporacion()[0] != null && colegiadoItem.getFechaIncorporacion()[1] != null) {
				
				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);
				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY') " + " and ( TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') <= TO_DATE('"
						+ fechaIncorporacionHasta + "','DD/MM/YYYY')))");
				
			} else if (colegiadoItem.getFechaIncorporacion()[0] != null && colegiadoItem.getFechaIncorporacion()[1] == null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);

				sql.WHERE("(TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY'))");
				
			} else if (colegiadoItem.getFechaIncorporacion()[0] == null && colegiadoItem.getFechaIncorporacion()[1] != null) {

					String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

					sql.WHERE("( TO_DATE(col.fechaincorporacion,'DD/MM/RRRR') <= TO_DATE('"
							+ fechaIncorporacionHasta + "','DD/MM/YYYY'))");
				}
			}

		if (colegiadoItem.getEstadoColegial() != null && colegiadoItem.getEstadoColegial() != "") {
			sql.WHERE("cat.descripcion like '" + colegiadoItem.getEstadoColegial() + "'");
		}


		sql.ORDER_BY("NOMBRE");
		//sql.ORDER_BY("per.nombre");

		return sql.toString();
	}
	
	public String getLabel(AdmUsuarios usuario) {
		
		SQL sql = new SQL();
		
		sql.SELECT("distinct GRUCLI.IDGRUPO");
		sql.SELECT("GENR.DESCRIPCION");
		sql.FROM("cen_gruposcliente GRUCLI");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO");
		sql.WHERE("GRUCLI.IDINSTITUCION = '"+ usuario.getIdinstitucion()  + "'");
		sql.WHERE("GENR.IDLENGUAJE = '"+ usuario.getIdlenguaje()+ "'");
		sql.ORDER_BY("GENR.DESCRIPCION");
		
		return sql.toString();
	}
}
