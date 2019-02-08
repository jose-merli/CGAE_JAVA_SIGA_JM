package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;

public class CenTiposModificacionesSqlExtendsProvider {

	public String getTipoModificacion(String idLenguage) {
		SQL sql = new SQL();
		sql.SELECT("tip.IDTIPOMODIFICACION AS VALUE");
		sql.SELECT("cat.DESCRIPCION AS LABEL");
		sql.FROM("CEN_TIPOSMODIFICACIONES tip");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat on cat.IDRECURSO = tip.DESCRIPCION");
		sql.WHERE("cat.IDLENGUAJE ='" + idLenguage + "'");
		return sql.toString();
	}

	public String searchModificationRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion,Long idPersona) {

		if (null != idPersona) {
			String rdo = "SELECT * FROM (("
					+ SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " ) UNION ( " + getSpecificRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " )) WHERE IDPERSONA = "+ idPersona +"  ORDER BY 6 DESC";
			return rdo;
		}else{
			String rdo = "SELECT * FROM ("
					+ SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " ) UNION ( " + getSpecificRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " ) ORDER BY 6 DESC";
			return rdo;
		}
	}

	private String getSpecificRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje,
			String idInstitucion) {

		String consultaEspecifica = SolModifSQLUtils.getBasicDataRequest(solicitudModificacionSearchDTO, idLenguaje,
				idInstitucion) + " UNION ( "
				+ SolModifSQLUtils.getAddressesRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ("
				+ SolModifSQLUtils.getBancDataRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) "
				+ "UNION (" + SolModifSQLUtils.getCVRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ("
				+ SolModifSQLUtils.getDataInvoicingRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ("
				+ SolModifSQLUtils.getDataPhotoRequest("35", solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ("
				+ SolModifSQLUtils.getDataPhotoRequest("60", solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ("
				+ SolModifSQLUtils.getDataFileRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " )";

		return consultaEspecifica;
	}

	// private String getSpecificRequest1(SolicitudModificacionSearchDTO
	// solicitudModificacionSearchDTO,
	// String idLenguaje, String idInstitucion) {
	//
	// SQL solicitmodifdatosbasicosSQL = new SQL();
	// SQL solimodidirecciones = new SQL();
	// SQL solicmodicuentas = new SQL();
	// SQL solicitudmodificacioncv = new SQL();
	// SQL solmodifacturacionservicio = new SQL();
	// SQL solicmodifexportarfoto = new SQL();
	// SQL solicitudborrado = new SQL();
	//
	// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	//
	// String consultaEspecifica = new String();
	//
	// // solicitmodifdatosbasicosSQL
	// solicitmodifdatosbasicosSQL.SELECT("'1' AS especifica");
	// solicitmodifdatosbasicosSQL.SELECT("cen_solicitmodifdatosbasicos.idsolicitud");
	// solicitmodifdatosbasicosSQL.SELECT("cen_solicitmodifdatosbasicos.motivo");
	// solicitmodifdatosbasicosSQL.SELECT("cen_solicitmodifdatosbasicos.idpersona");
	// solicitmodifdatosbasicosSQL.SELECT("cen_solicitmodifdatosbasicos.idinstitucion");
	// solicitmodifdatosbasicosSQL.SELECT("0 AS codigo");
	// solicitmodifdatosbasicosSQL.SELECT("cen_solicitmodifdatosbasicos.fechaalta");
	// solicitmodifdatosbasicosSQL.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1)
	// AS estado");
	// solicitmodifdatosbasicosSQL
	// .SELECT("10 AS idTipoModificacion, (" +
	// SolModifSQLUtils.getPersonSql("cen_solicitmodifdatosbasicos") + ") as
	// nombre");
	// solicitmodifdatosbasicosSQL.SELECT("(" + SolModifSQLUtils.getTypeSql("10") +
	// ") as tipoModificacion");
	// solicitmodifdatosbasicosSQL.SELECT("(" +
	// SolModifSQLUtils.getNColSql("cen_solicitmodifdatosbasicos") + ") as
	// numColegiado");
	//
	// solicitmodifdatosbasicosSQL.FROM("cen_solicitmodifdatosbasicos,
	// cen_estadosolicitudmodif");
	// // CONDICIONES solicitmodifdatosbasicos
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solicitmodifdatosbasicosSQL.WHERE(
	// "cen_solicitmodifdatosbasicos.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solicitmodifdatosbasicosSQL
	// .WHERE("TO_DATE(cen_solicitmodifdatosbasicos.FECHAALTA,'DD/MM/YYYY') >=
	// TO_DATE('" + fechaDesde
	// + "', 'DD/MM/YYYY')");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solicitmodifdatosbasicosSQL
	// .WHERE("TO_DATE(cen_solicitmodifdatosbasicos.FECHAALTA,'DD/MM/YYYY') <=
	// TO_DATE('" + fechaHasta
	// + "', 'DD/MM/YYYY')");
	// }
	//
	// solicitmodifdatosbasicosSQL.WHERE(
	// "cen_solicitmodifdatosbasicos.idestadosolic =
	// cen_estadosolicitudmodif.idestadosolic AND
	// cen_solicitmodifdatosbasicos.idinstitucion = "
	// + idInstitucion);
	//
	// // solimodidirecciones
	// solimodidirecciones.SELECT("'1' AS especifica");
	// solimodidirecciones.SELECT("cen_solimodidirecciones.idsolicitud");
	// solimodidirecciones.SELECT("cen_solimodidirecciones.motivo");
	// solimodidirecciones.SELECT("cen_solimodidirecciones.idpersona");
	// solimodidirecciones.SELECT("cen_solimodidirecciones.idinstitucion");
	// solimodidirecciones.SELECT("cen_solimodidirecciones.iddireccion AS codigo");
	// solimodidirecciones.SELECT("cen_solimodidirecciones.fechaalta");
	// solimodidirecciones.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1)
	// AS estado");
	// solimodidirecciones
	// .SELECT("30 AS idTipoModificacion, (" +
	// SolModifSQLUtils.getPersonSql("cen_solimodidirecciones") + ") as nombre");
	// solimodidirecciones.SELECT("(" + SolModifSQLUtils.getTypeSql("30") + ") as
	// tipoModificacion");
	// solimodidirecciones.SELECT("(" +
	// SolModifSQLUtils.getNColSql("cen_solimodidirecciones") + ") as
	// numColegiado");
	//
	// solimodidirecciones.FROM("cen_solimodidirecciones,
	// cen_estadosolicitudmodif");
	//
	// // CONDICIONES solimodidirecciones
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solimodidirecciones
	// .WHERE("cen_solimodidirecciones.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solimodidirecciones.WHERE("
	// TO_DATE(cen_solimodidirecciones.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('"
	// + fechaDesde + "', 'DD/MM/YYYY') ");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solimodidirecciones.WHERE("
	// TO_DATE(cen_solimodidirecciones.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('"
	// + fechaHasta + "', 'DD/MM/YYYY') ");
	// }
	//
	// solimodidirecciones.WHERE(
	// "cen_solimodidirecciones.idestadosolic =
	// cen_estadosolicitudmodif.idestadosolic AND
	// cen_solimodidirecciones.idinstitucion = "
	// + idInstitucion);
	//
	// // solicmodicuentas
	// solicmodicuentas.SELECT("'1' AS especifica");
	// solicmodicuentas.SELECT("cen_solicmodicuentas.idsolicitud");
	// solicmodicuentas.SELECT("cen_solicmodicuentas.motivo");
	// solicmodicuentas.SELECT("cen_solicmodicuentas.idpersona");
	// solicmodicuentas.SELECT("cen_solicmodicuentas.idinstitucion");
	// solicmodicuentas.SELECT("cen_solicmodicuentas.idcuenta AS codigo");
	// solicmodicuentas.SELECT("cen_solicmodicuentas.fechaalta");
	// solicmodicuentas.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1)
	// AS estado");
	// solicmodicuentas.SELECT("40 AS idtipomodificacion, ( " +
	// SolModifSQLUtils.getPersonSql("cen_solicmodicuentas") + ") as nombre");
	// solicmodicuentas.SELECT("(" + SolModifSQLUtils.getTypeSql("40") + ") as
	// tipoModificacion");
	// solicmodicuentas.SELECT("(" +
	// SolModifSQLUtils.getNColSql("cen_solicmodicuentas") + ") as numColegiado");
	// solicmodicuentas.FROM("cen_solicmodicuentas, cen_estadosolicitudmodif");
	//
	// // CONDICIONES solicmodicuentas
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solicmodicuentas
	// .WHERE("cen_solicmodicuentas.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solicmodicuentas.WHERE(" TO_DATE(cen_solicmodicuentas.FECHAALTA,'DD/MM/YYYY')
	// >= TO_DATE('" + fechaDesde
	// + "', 'DD/MM/YYYY') ");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solicmodicuentas.WHERE(" TO_DATE(cen_solicmodicuentas.FECHAALTA,'DD/MM/YYYY')
	// <= TO_DATE('" + fechaHasta
	// + "', 'DD/MM/YYYY') ");
	// }
	//
	// solicmodicuentas.WHERE(
	// "cen_solicmodicuentas.idestadosolic = cen_estadosolicitudmodif.idestadosolic
	// AND cen_solicmodicuentas.idinstitucion = "
	// + idInstitucion);
	//
	// // solicitudmodificacioncv
	// solicitudmodificacioncv.SELECT("'1' AS especifica");
	// solicitudmodificacioncv.SELECT("cen_solicitudmodificacioncv.idsolicitud");
	// solicitudmodificacioncv.SELECT("cen_solicitudmodificacioncv.motivo");
	// solicitudmodificacioncv.SELECT("cen_solicitudmodificacioncv.idpersona");
	// solicitudmodificacioncv.SELECT("cen_solicitudmodificacioncv.idinstitucion");
	// solicitudmodificacioncv.SELECT("cen_solicitudmodificacioncv.idcv AS codigo");
	// solicitudmodificacioncv.SELECT("cen_solicitudmodificacioncv.fechaalta");
	// solicitudmodificacioncv.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1)
	// AS estado");
	// solicitudmodificacioncv
	// .SELECT("50 AS idtipomodificacion, ( " +
	// SolModifSQLUtils.getPersonSql("cen_solicitudmodificacioncv") + ") as
	// nombre");
	// solicitudmodificacioncv.SELECT("(" + SolModifSQLUtils.getTypeSql("50") + ")
	// as tipoModificacion");
	// solicitudmodificacioncv.SELECT("(" +
	// SolModifSQLUtils.getNColSql("cen_solicitudmodificacioncv") + ") as
	// numColegiado");
	// solicitudmodificacioncv.FROM("cen_solicitudmodificacioncv,
	// cen_estadosolicitudmodif");
	//
	// // CONDICIONES solicitudmodificacioncv
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solicitudmodificacioncv
	// .WHERE("cen_solicitudmodificacioncv.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solicitudmodificacioncv.WHERE("
	// TO_DATE(cen_solicitudmodificacioncv.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('"
	// + fechaDesde + "', 'DD/MM/YYYY') ");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solicitudmodificacioncv.WHERE("
	// TO_DATE(cen_solicitudmodificacioncv.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('"
	// + fechaHasta + "', 'DD/MM/YYYY') ");
	// }
	//
	// solicitudmodificacioncv.WHERE(
	// "cen_solicitudmodificacioncv.idestadosolic =
	// cen_estadosolicitudmodif.idestadosolic AND
	// cen_solicitudmodificacioncv.idinstitucion = "
	// + idInstitucion);
	//
	// // solmodifacturacionservicio
	// solmodifacturacionservicio.SELECT("'1' AS especifica");
	// solmodifacturacionservicio.SELECT("cen_solmodifacturacionservicio.idsolicitud");
	// solmodifacturacionservicio.SELECT("cen_solmodifacturacionservicio.motivo");
	// solmodifacturacionservicio.SELECT("cen_solmodifacturacionservicio.idpersona");
	// solmodifacturacionservicio.SELECT("cen_solmodifacturacionservicio.idinstitucion");
	// solmodifacturacionservicio.SELECT("cen_solmodifacturacionservicio.idcuenta AS
	// codigo");
	// solmodifacturacionservicio.SELECT("cen_solmodifacturacionservicio.fechaalta");
	// solmodifacturacionservicio.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1)
	// AS estado");
	// solmodifacturacionservicio.SELECT(
	// "70 AS idtipomodificacion, ( " +
	// SolModifSQLUtils.getPersonSql("cen_solmodifacturacionservicio") + ") as
	// nombre");
	// solmodifacturacionservicio.SELECT("(" + SolModifSQLUtils.getTypeSql("70") +
	// ") as tipoModificacion");
	// solmodifacturacionservicio.SELECT("(" +
	// SolModifSQLUtils.getNColSql("cen_solmodifacturacionservicio") + ") as
	// numColegiado");
	// solmodifacturacionservicio.FROM("cen_solmodifacturacionservicio,
	// cen_estadosolicitudmodif");
	//
	// // CONDICIONES solicitudmodificacioncv
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solmodifacturacionservicio.WHERE(
	// "cen_solmodifacturacionservicio.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solmodifacturacionservicio
	// .WHERE(" TO_DATE(cen_solmodifacturacionservicio.FECHAALTA,'DD/MM/YYYY') >=
	// TO_DATE('" + fechaDesde
	// + "', 'DD/MM/YYYY') ");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solmodifacturacionservicio
	// .WHERE(" TO_DATE(cen_solmodifacturacionservicio.FECHAALTA,'DD/MM/YYYY') <=
	// TO_DATE('" + fechaHasta
	// + "', 'DD/MM/YYYY') ");
	// }
	//
	// solmodifacturacionservicio.WHERE(
	// "cen_solmodifacturacionservicio.idestadosolic =
	// cen_estadosolicitudmodif.idestadosolic AND
	// cen_solmodifacturacionservicio.idinstitucion = "
	// + idInstitucion);
	//
	// // solicmodifexportarfoto
	// solicmodifexportarfoto.SELECT("'1' AS especifica");
	// solicmodifexportarfoto.SELECT("cen_solicmodifexportarfoto.idsolicitud");
	// solicmodifexportarfoto.SELECT("cen_solicmodifexportarfoto.motivo");
	// solicmodifexportarfoto.SELECT("cen_solicmodifexportarfoto.idpersona");
	// solicmodifexportarfoto.SELECT("cen_solicmodifexportarfoto.idinstitucion");
	// solicmodifexportarfoto.SELECT("0 AS codigo");
	// solicmodifexportarfoto.SELECT("cen_solicmodifexportarfoto.fechaalta");
	// solicmodifexportarfoto.SELECT("
	// f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1) AS estado");
	// solicmodifexportarfoto
	// .SELECT("35 AS idtipomodificacion, ( " +
	// SolModifSQLUtils.getPersonSql("cen_solicmodifexportarfoto") + ") as nombre");
	// solicmodifexportarfoto.SELECT("(" + SolModifSQLUtils.getTypeSql("35") + ") as
	// tipoModificacion");
	// solicmodifexportarfoto.SELECT("(" +
	// SolModifSQLUtils.getNColSql("cen_solicmodifexportarfoto") + ") as
	// numColegiado");
	// solicmodifexportarfoto.FROM("cen_solicmodifexportarfoto,
	// cen_estadosolicitudmodif");
	//
	// // CONDICIONES solicmodifexportarfoto
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solicmodifexportarfoto
	// .WHERE("cen_estadosolicitudmodif.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solicmodifexportarfoto.WHERE("
	// TO_DATE(cen_solicmodifexportarfoto.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('"
	// + fechaDesde + "', 'DD/MM/YYYY') ");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solicmodifexportarfoto.WHERE("
	// TO_DATE(cen_solicmodifexportarfoto.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('"
	// + fechaHasta + "', 'DD/MM/YYYY') ");
	// }
	//
	// solicmodifexportarfoto.WHERE(
	// "cen_solicmodifexportarfoto.idestadosolic =
	// cen_estadosolicitudmodif.idestadosolic AND
	// cen_solicmodifexportarfoto.idinstitucion = "
	// + idInstitucion);
	//
	// // solicitudborrado
	// solicitudborrado.SELECT("'1' AS especifica");
	// solicitudborrado.SELECT("exp_solicitudborrado.idsolicitud");
	// solicitudborrado.SELECT("exp_solicitudborrado.motivo");
	// solicitudborrado.SELECT("exp_solicitudborrado.idpersona");
	// solicitudborrado.SELECT("exp_solicitudborrado.idinstitucion");
	// solicitudborrado.SELECT("0 AS codigo");
	// solicitudborrado.SELECT("exp_solicitudborrado.fechaalta");
	// solicitudborrado.SELECT("f_siga_getrecurso(cen_estadosolicitudmodif.descripcion,1)
	// AS estado");
	// solicitudborrado.SELECT("90 AS idtipomodificacion, ( " +
	// SolModifSQLUtils.getPersonSql("exp_solicitudborrado") + ") as nombre");
	// solicitudborrado.SELECT("(" + SolModifSQLUtils.getTypeSql("90") + ") as
	// tipoModificacion");
	// solicitudborrado.SELECT("(" +
	// SolModifSQLUtils.getNColSql("exp_solicitudborrado") + " ) as numColegiado");
	// solicitudborrado.FROM("exp_solicitudborrado, cen_estadosolicitudmodif");
	//
	// // CONDICIONES solicitudborrado
	// if
	// (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado()))
	// {
	// solicitudborrado
	// .WHERE("exp_solicitudborrado.idestadosolic = " +
	// solicitudModificacionSearchDTO.getEstado());
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
	// String fechaDesde =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
	// solicitudborrado.WHERE(" TO_DATE(exp_solicitudborrado.FECHAALTA,'DD/MM/YYYY')
	// >= TO_DATE('" + fechaDesde
	// + "', 'DD/MM/YYYY') ");
	// }
	//
	// if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
	// String fechaHasta =
	// dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
	// solicitudborrado.WHERE(" TO_DATE(exp_solicitudborrado.FECHAALTA,'DD/MM/YYYY')
	// <= TO_DATE('" + fechaHasta
	// + "', 'DD/MM/YYYY') ");
	// }
	//
	// solicitudborrado.WHERE(
	// "exp_solicitudborrado.idestadosolic = cen_estadosolicitudmodif.idestadosolic
	// AND exp_solicitudborrado.idinstitucion = "
	// + idInstitucion);
	//
	// consultaEspecifica = solicitmodifdatosbasicosSQL.toString() + " UNION ( " +
	// solimodidirecciones.toString()
	// + " ) UNION (" + solicmodicuentas.toString() + " ) " + "UNION (" +
	// solicitudmodificacioncv.toString()
	// + " ) UNION (" + solmodifacturacionservicio.toString() + " ) UNION ("
	// + solicmodifexportarfoto.toString() + " ) UNION (" +
	// solicitudborrado.toString() + " )";
	//
	// return consultaEspecifica;
	// }
}
