package org.itcgae.siga.commons.utils;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;

public class SolModifSQLUtils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

	public static String getGeneralRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaGeneral = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

		consultaGeneral.SELECT_DISTINCT("'0' AS especifica");
		consultaGeneral.SELECT("sol.idsolicitud AS IDSOLICITUD");
		consultaGeneral.SELECT("sol.descripcion as MOTIVO");
		consultaGeneral.SELECT("sol.idpersona AS IDPERSONA");
		consultaGeneral.SELECT("sol.idInstitucion as IDINSTITUCION");
		consultaGeneral.SELECT("11 AS codigo");
		consultaGeneral.SELECT("sol.fechaalta AS FECHAALTA");
		consultaGeneral.SELECT("cat1.descripcion AS ESTADO");
		consultaGeneral.SELECT("modif.idtipomodificacion AS IDTIPOMODIFICACION");
		consultaGeneral.SELECT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ',per.apellidos2) ) as NOMBRE");
		consultaGeneral.SELECT("cat2.descripcion AS TIPOMODIFICACION");
		consultaGeneral.SELECT("col.ncolegiado AS NUMCOLEGIADO");
		consultaGeneral.FROM("CEN_SOLICITUDESMODIFICACION sol");
		consultaGeneral.INNER_JOIN("CEN_ESTADOSOLICITUDMODIF est on est.IDESTADOSOLIC = sol.IDESTADOSOLIC");
		consultaGeneral.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat1 on cat1.IDRECURSO = est.DESCRIPCION");
		consultaGeneral
				.INNER_JOIN("CEN_TIPOSMODIFICACIONES modif on modif.IDTIPOMODIFICACION = sol.IDTIPOMODIFICACION");
		consultaGeneral.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat2 on cat2.IDRECURSO = modif.DESCRIPCION");
		consultaGeneral.INNER_JOIN("CEN_PERSONA per on per.IDPERSONA = sol.IDPERSONA");
		consultaGeneral.INNER_JOIN(
				"CEN_COLEGIADO col on col.IDPERSONA = per.IDPERSONA and col.IDINSTITUCION = sol.IDINSTITUCION");
		consultaGeneral.WHERE("cat1.IDLENGUAJE ='" + idLenguaje + "'");
		consultaGeneral.WHERE("cat2.IDLENGUAJE ='" + idLenguaje + "'");
		consultaGeneral.WHERE("sol.idinstitucion ='" + idInstitucion + "'");

		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getTipoModificacion())) {
			consultaGeneral.WHERE(UtilidadesString.filtroTextoBusquedas("sol.IDTIPOMODIFICACION",
					solicitudModificacionSearchDTO.getTipoModificacion()));
		}

		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaGeneral.WHERE(UtilidadesString.filtroTextoBusquedas("sol.IDESTADOSOLIC",
					solicitudModificacionSearchDTO.getEstado()));
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaGeneral
					.WHERE(" TO_DATE(sol.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaGeneral
					.WHERE(" TO_DATE(sol.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY') ");
		}

		return consultaGeneral.toString();
	}

	public static String getBasicDataRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosBasicos = new SQL();
		
		consultaDatosBasicos.SELECT("'1' AS especifica");
		consultaDatosBasicos.SELECT("cen_solicitmodifdatosbasicos.idsolicitud");
		consultaDatosBasicos.SELECT("cen_solicitmodifdatosbasicos.motivo");
		consultaDatosBasicos.SELECT("cen_solicitmodifdatosbasicos.idpersona");
		consultaDatosBasicos.SELECT("cen_solicitmodifdatosbasicos.idinstitucion");
		consultaDatosBasicos.SELECT("0 AS codigo");
		consultaDatosBasicos.SELECT("cen_solicitmodifdatosbasicos.fechaalta");
		consultaDatosBasicos.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosBasicos
				.SELECT("10 AS idTipoModificacion, (" + getPersonSql("cen_solicitmodifdatosbasicos") + ") as nombre");
		consultaDatosBasicos.SELECT("(" + getTypeSql("10") + ") as tipoModificacion");
		consultaDatosBasicos.SELECT("(" + getNColSql("cen_solicitmodifdatosbasicos") + ") as numColegiado");

		consultaDatosBasicos.FROM("cen_solicitmodifdatosbasicos, cen_estadosolicitudmodif");

		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosBasicos.WHERE(
					"cen_solicitmodifdatosbasicos.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosBasicos
					.WHERE("TO_DATE(cen_solicitmodifdatosbasicos.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('" + fechaDesde
							+ "', 'DD/MM/YYYY')");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosBasicos
					.WHERE("TO_DATE(cen_solicitmodifdatosbasicos.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('" + fechaHasta
							+ "', 'DD/MM/YYYY')");
		}

		consultaDatosBasicos.WHERE(
				"cen_solicitmodifdatosbasicos.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND cen_solicitmodifdatosbasicos.idinstitucion = "
						+ idInstitucion);

		return consultaDatosBasicos.toString();
	}

	public static String getAddressesRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosDirecciones = new SQL();

		consultaDatosDirecciones.SELECT("'1' AS especifica");
		consultaDatosDirecciones.SELECT("cen_solimodidirecciones.idsolicitud");
		consultaDatosDirecciones.SELECT("cen_solimodidirecciones.motivo");
		consultaDatosDirecciones.SELECT("cen_solimodidirecciones.idpersona");
		consultaDatosDirecciones.SELECT("cen_solimodidirecciones.idinstitucion");
		consultaDatosDirecciones.SELECT("cen_solimodidirecciones.iddireccion AS codigo");
		consultaDatosDirecciones.SELECT("cen_solimodidirecciones.fechaalta");
		consultaDatosDirecciones.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosDirecciones
				.SELECT("30 AS idTipoModificacion, (" + getPersonSql("cen_solimodidirecciones") + ") as nombre");
		consultaDatosDirecciones.SELECT("(" + getTypeSql("30") + ") as tipoModificacion");
		consultaDatosDirecciones.SELECT("(" + getNColSql("cen_solimodidirecciones") + ") as numColegiado");

		consultaDatosDirecciones.FROM("cen_solimodidirecciones, cen_estadosolicitudmodif");

		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosDirecciones
					.WHERE("cen_solimodidirecciones.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosDirecciones.WHERE(" TO_DATE(cen_solimodidirecciones.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('"
					+ fechaDesde + "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosDirecciones.WHERE(" TO_DATE(cen_solimodidirecciones.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('"
					+ fechaHasta + "', 'DD/MM/YYYY') ");
		}

		consultaDatosDirecciones.WHERE(
				"cen_solimodidirecciones.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND cen_solimodidirecciones.idinstitucion = "
						+ idInstitucion);
		
		return consultaDatosDirecciones.toString();
	}

	public static String getBancDataRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosBancarios = new SQL();

		consultaDatosBancarios.SELECT("'1' AS especifica");
		consultaDatosBancarios.SELECT("cen_solicmodicuentas.idsolicitud");
		consultaDatosBancarios.SELECT("cen_solicmodicuentas.motivo");
		consultaDatosBancarios.SELECT("cen_solicmodicuentas.idpersona");
		consultaDatosBancarios.SELECT("cen_solicmodicuentas.idinstitucion");
		consultaDatosBancarios.SELECT("cen_solicmodicuentas.idcuenta AS codigo");
		consultaDatosBancarios.SELECT("cen_solicmodicuentas.fechaalta");
		consultaDatosBancarios.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosBancarios.SELECT("40 AS idtipomodificacion, ( " + getPersonSql("cen_solicmodicuentas") + ") as nombre");
		consultaDatosBancarios.SELECT("(" + getTypeSql("40") + ") as tipoModificacion");
		consultaDatosBancarios.SELECT("(" + getNColSql("cen_solicmodicuentas") + ") as numColegiado");
		consultaDatosBancarios.FROM("cen_solicmodicuentas, cen_estadosolicitudmodif");

		// CONDICIONES solicmodicuentas
		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosBancarios
					.WHERE("cen_solicmodicuentas.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosBancarios.WHERE(" TO_DATE(cen_solicmodicuentas.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('" + fechaDesde
					+ "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosBancarios.WHERE(" TO_DATE(cen_solicmodicuentas.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('" + fechaHasta
					+ "', 'DD/MM/YYYY') ");
		}

		consultaDatosBancarios.WHERE(
				"cen_solicmodicuentas.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND cen_solicmodicuentas.idinstitucion = "
						+ idInstitucion);
		
		return consultaDatosBancarios.toString();
	}

	public static String getCVRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosCV = new SQL();

		consultaDatosCV.SELECT("'1' AS especifica");
		consultaDatosCV.SELECT("cen_solicitudmodificacioncv.idsolicitud");
		consultaDatosCV.SELECT("cen_solicitudmodificacioncv.motivo");
		consultaDatosCV.SELECT("cen_solicitudmodificacioncv.idpersona");
		consultaDatosCV.SELECT("cen_solicitudmodificacioncv.idinstitucion");
		consultaDatosCV.SELECT("cen_solicitudmodificacioncv.idcv AS codigo");
		consultaDatosCV.SELECT("cen_solicitudmodificacioncv.fechaalta");
		consultaDatosCV.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosCV
				.SELECT("50 AS idtipomodificacion, ( " + getPersonSql("cen_solicitudmodificacioncv") + ") as nombre");
		consultaDatosCV.SELECT("(" + getTypeSql("50") + ") as tipoModificacion");
		consultaDatosCV.SELECT("(" + getNColSql("cen_solicitudmodificacioncv") + ") as numColegiado");
		consultaDatosCV.FROM("cen_solicitudmodificacioncv, cen_estadosolicitudmodif");

		// CONDICIONES solicitudmodificacioncv
		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosCV
					.WHERE("cen_solicitudmodificacioncv.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosCV.WHERE(" TO_DATE(cen_solicitudmodificacioncv.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('"
					+ fechaDesde + "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosCV.WHERE(" TO_DATE(cen_solicitudmodificacioncv.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('"
					+ fechaHasta + "', 'DD/MM/YYYY') ");
		}

		consultaDatosCV.WHERE(
				"cen_solicitudmodificacioncv.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND cen_solicitudmodificacioncv.idinstitucion = "
						+ idInstitucion);
		
		return consultaDatosCV.toString();
	}

	public static String getDataInvoicingRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosFacturacion = new SQL();

		consultaDatosFacturacion.SELECT("'1' AS especifica");
		consultaDatosFacturacion.SELECT("cen_solmodifacturacionservicio.idsolicitud");
		consultaDatosFacturacion.SELECT("cen_solmodifacturacionservicio.motivo");
		consultaDatosFacturacion.SELECT("cen_solmodifacturacionservicio.idpersona");
		consultaDatosFacturacion.SELECT("cen_solmodifacturacionservicio.idinstitucion");
		consultaDatosFacturacion.SELECT("cen_solmodifacturacionservicio.idcuenta AS codigo");
		consultaDatosFacturacion.SELECT("cen_solmodifacturacionservicio.fechaalta");
		consultaDatosFacturacion.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosFacturacion.SELECT(
				"70 AS idtipomodificacion, ( " + SolModifSQLUtils.getPersonSql("cen_solmodifacturacionservicio") + ") as nombre");
		consultaDatosFacturacion.SELECT("(" + SolModifSQLUtils.getTypeSql("70") + ") as tipoModificacion");
		consultaDatosFacturacion.SELECT("(" + SolModifSQLUtils.getNColSql("cen_solmodifacturacionservicio") + ") as numColegiado");
		consultaDatosFacturacion.FROM("cen_solmodifacturacionservicio, cen_estadosolicitudmodif");

		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosFacturacion.WHERE(
					"cen_solmodifacturacionservicio.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosFacturacion
					.WHERE(" TO_DATE(cen_solmodifacturacionservicio.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('" + fechaDesde
							+ "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosFacturacion
					.WHERE(" TO_DATE(cen_solmodifacturacionservicio.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('" + fechaHasta
							+ "', 'DD/MM/YYYY') ");
		}

		consultaDatosFacturacion.WHERE(
				"cen_solmodifacturacionservicio.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND cen_solmodifacturacionservicio.idinstitucion = "
						+ idInstitucion);
		
		return consultaDatosFacturacion.toString();
	}

	public static String getDataFileRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosExpedientes = new SQL();

		consultaDatosExpedientes.SELECT("'1' AS especifica");
		consultaDatosExpedientes.SELECT("exp_solicitudborrado.idsolicitud");
		consultaDatosExpedientes.SELECT("exp_solicitudborrado.motivo");
		consultaDatosExpedientes.SELECT("exp_solicitudborrado.idpersona");
		consultaDatosExpedientes.SELECT("exp_solicitudborrado.idinstitucion");
		consultaDatosExpedientes.SELECT("0 AS codigo");
		consultaDatosExpedientes.SELECT("exp_solicitudborrado.fechaalta");
		consultaDatosExpedientes.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosExpedientes.SELECT("90 AS idtipomodificacion, ( " + SolModifSQLUtils.getPersonSql("exp_solicitudborrado") + ") as nombre");
		consultaDatosExpedientes.SELECT("(" + SolModifSQLUtils.getTypeSql("90") + ") as tipoModificacion");
		consultaDatosExpedientes.SELECT("(" + SolModifSQLUtils.getNColSql("exp_solicitudborrado") + " ) as numColegiado");
		consultaDatosExpedientes.FROM("exp_solicitudborrado, cen_estadosolicitudmodif");

		// CONDICIONES solicitudborrado
		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosExpedientes
					.WHERE("exp_solicitudborrado.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosExpedientes.WHERE(" TO_DATE(exp_solicitudborrado.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('" + fechaDesde
					+ "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosExpedientes.WHERE(" TO_DATE(exp_solicitudborrado.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('" + fechaHasta
					+ "', 'DD/MM/YYYY') ");
		}

		consultaDatosExpedientes.WHERE(
				"exp_solicitudborrado.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND exp_solicitudborrado.idinstitucion = "
						+ idInstitucion);
		
		return consultaDatosExpedientes.toString();
	}
	
	public static String getDataPhotoRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion) {

		SQL consultaDatosFoto = new SQL();

		consultaDatosFoto.SELECT("'1' AS especifica");
		consultaDatosFoto.SELECT("cen_solicmodifexportarfoto.idsolicitud");
		consultaDatosFoto.SELECT("cen_solicmodifexportarfoto.motivo");
		consultaDatosFoto.SELECT("cen_solicmodifexportarfoto.idpersona");
		consultaDatosFoto.SELECT("cen_solicmodifexportarfoto.idinstitucion");
		consultaDatosFoto.SELECT("0 AS codigo");
		consultaDatosFoto.SELECT("cen_solicmodifexportarfoto.fechaalta");
		consultaDatosFoto.SELECT("    f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
		consultaDatosFoto
				.SELECT("35 AS idtipomodificacion, ( " + SolModifSQLUtils.getPersonSql("cen_solicmodifexportarfoto") + ") as nombre");
		consultaDatosFoto.SELECT("(" + SolModifSQLUtils.getTypeSql("35") + ") as tipoModificacion");
		consultaDatosFoto.SELECT("(" + SolModifSQLUtils.getNColSql("cen_solicmodifexportarfoto") + ") as numColegiado");
		consultaDatosFoto.FROM("cen_solicmodifexportarfoto, cen_estadosolicitudmodif");

		// CONDICIONES solicmodifexportarfoto
		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			consultaDatosFoto
					.WHERE("cen_estadosolicitudmodif.idestadosolic = " + solicitudModificacionSearchDTO.getEstado());
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			consultaDatosFoto.WHERE(" TO_DATE(cen_solicmodifexportarfoto.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('"
					+ fechaDesde + "', 'DD/MM/YYYY') ");
		}

		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			consultaDatosFoto.WHERE(" TO_DATE(cen_solicmodifexportarfoto.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('"
					+ fechaHasta + "', 'DD/MM/YYYY') ");
		}

		consultaDatosFoto.WHERE(
				"cen_solicmodifexportarfoto.idestadosolic = cen_estadosolicitudmodif.idestadosolic AND cen_solicmodifexportarfoto.idinstitucion = "
						+ idInstitucion);
		
		return consultaDatosFoto.toString();
	}
	
	private static String getPersonSql(String tableName) {
		SQL getPersonSql = new SQL();
		getPersonSql.SELECT("concat(nombre || ' ',concat(apellidos1|| ' ',apellidos2))");
		getPersonSql.FROM("cen_persona");
		getPersonSql.WHERE("idpersona = " + tableName + ".idpersona");
		return getPersonSql.toString();
	}

	private static String getTypeSql(String idtipomodificacion) {
		SQL getTypeSql = new SQL();
		getTypeSql.SELECT("f_siga_getrecurso(descripcion,1) descripcion");
		getTypeSql.FROM("cen_tiposmodificaciones");
		getTypeSql.WHERE("idtipomodificacion = '" + idtipomodificacion + "'");
		return getTypeSql.toString();
	}

	private static String getNColSql(String tableName) {
		SQL getNColSql = new SQL();
		getNColSql.SELECT("ncolegiado");
		getNColSql.FROM("cen_colegiado");
		getNColSql.WHERE("idpersona = " + tableName + ".idpersona and idInstitucion = " + tableName + ".idinstitucion");
		return getNColSql.toString();
	}
}
