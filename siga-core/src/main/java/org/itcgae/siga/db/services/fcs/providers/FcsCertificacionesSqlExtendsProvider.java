package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.BusquedaRetencionesRequestDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsCertificacionesSqlProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FcsCertificacionesSqlExtendsProvider extends FcsCertificacionesSqlProvider {

	public String buscarCertificaciones(BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, Integer tamMax,
			String idLenguaje) {

		SQL sql1 = new SQL();
		sql1.SELECT("CER.IDCERTIFICACION");
		sql1.SELECT("CER.FECHADESDE");
		sql1.SELECT("CER.FECHAHASTA");
		sql1.SELECT(
				"(CASE (CER.FECHADESDE || ' - ' || CER.FECHAHASTA) WHEN ' - ' THEN NULL ELSE ((CER.FECHADESDE || ' - ' || CER.FECHAHASTA)) END) AS PERIODO");
		sql1.SELECT("CER.NOMBRE");
		sql1.SELECT("SUM(NVL(F.IMPORTEOFICIO, 0)) AS TURNO");
		sql1.SELECT("SUM(NVL(F.IMPORTEGUARDIA , 0)) AS GUARDIA");
		sql1.SELECT("SUM(NVL(F.IMPORTEEJG , 0)) AS EJG");
		sql1.SELECT("SUM(NVL(F.IMPORTESOJ , 0)) AS SOJ");
		sql1.SELECT("SUM(NVL(F.IMPORTETOTAL, 0)) TOTAL");
		sql1.SELECT("CER.IDESTADOCERTIFICACION");
		sql1.SELECT("F_SIGA_GETRECURSO(EST.DESCRIPCION, " + idLenguaje + ") AS ESTADO");
		sql1.SELECT("F.IDPARTIDAPRESUPUESTARIA");
		sql1.SELECT("P.NOMBREPARTIDA AS NOMBREPARTIDAPRESUPUESTARIA");
		sql1.FROM("FCS_CERTIFICACIONES CER");
		sql1.JOIN("FCS_ESTADOSCERTIFICACIONES EST ON CER.IDESTADOCERTIFICACION = EST.IDESTADOCERTIFICACION "
				+ "LEFT JOIN FCS_FACT_CERTIFICACIONES FC ON CER.IDINSTITUCION = FC.IDINSTITUCION AND CER.IDCERTIFICACION = FC.IDCERTIFICACION "
				+ "LEFT JOIN FCS_FACTURACIONJG F ON F.IDINSTITUCION = FC.IDINSTITUCION AND F.IDFACTURACION = FC.IDFACTURACION "
				+ "LEFT JOIN SCS_PARTIDAPRESUPUESTARIA P ON P.IDINSTITUCION = F.IDINSTITUCION AND P.IDPARTIDAPRESUPUESTARIA = F.IDPARTIDAPRESUPUESTARIA");

		if (!UtilidadesString.esCadenaVacia(busquedaRetencionesRequestDTO.getIdCertificacion())) {
			sql1.WHERE("CER.IDCERTIFICACION = " + busquedaRetencionesRequestDTO.getIdCertificacion());
		}

		if (busquedaRetencionesRequestDTO.getIdInstitucionList() != null
				&& !busquedaRetencionesRequestDTO.getIdInstitucionList().isEmpty()) {
			sql1.WHERE("CER.IDINSTITUCION IN " + busquedaRetencionesRequestDTO.getIdInstitucionList().toString()
					.replace("[", "(").replace("]", ")"));
		}
		if (busquedaRetencionesRequestDTO.getIdEstadoCertificacionList() != null
				&& !busquedaRetencionesRequestDTO.getIdEstadoCertificacionList().isEmpty()) {
			sql1.WHERE("CER.IDESTADOCERTIFICACION IN " + busquedaRetencionesRequestDTO.getIdEstadoCertificacionList()
					.toString().replace("[", "(").replace("]", ")"));
		}

		if (busquedaRetencionesRequestDTO.getIdHitoGeneralList() != null
				&& !busquedaRetencionesRequestDTO.getIdHitoGeneralList().isEmpty()) {
			sql1.WHERE("CER.IDHITOGENERAL IN " + busquedaRetencionesRequestDTO.getIdHitoGeneralList().toString()
					.replace("[", "(").replace("]", ")"));
		}

		if (busquedaRetencionesRequestDTO.getIdGrupoFacturacionList() != null
				&& !busquedaRetencionesRequestDTO.getIdGrupoFacturacionList().isEmpty()) {
			sql1.WHERE("CER.IDGRUPOFACTURACION IN " + busquedaRetencionesRequestDTO.getIdGrupoFacturacionList()
					.toString().replace("[", "(").replace("]", ")"));
		}

		if (busquedaRetencionesRequestDTO.getIdPartidaPresupuestariaList() != null
				&& !busquedaRetencionesRequestDTO.getIdPartidaPresupuestariaList().isEmpty()) {
			sql1.WHERE("CER.IDPARTIDAPRESUPUESTARIA IN " + busquedaRetencionesRequestDTO
					.getIdPartidaPresupuestariaList().toString().replace("[", "(").replace("]", ")"));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaRetencionesRequestDTO.getNombre())) {
			sql1.WHERE("UPPER(CER.NOMBRE) LIKE UPPER('%" + busquedaRetencionesRequestDTO.getNombre() + "%')");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		if (busquedaRetencionesRequestDTO.getFechaDesde() != null) {
			String fechaF = dateFormat.format(busquedaRetencionesRequestDTO.getFechaDesde());
			sql1.WHERE("TRUNC(CER.FECHADESDE) >= TO_DATE('" + fechaF + "', 'DD/MM/YYYY')");
		}

		if (busquedaRetencionesRequestDTO.getFechaHasta() != null) {
			String fechaF = dateFormat.format(busquedaRetencionesRequestDTO.getFechaHasta());
			sql1.WHERE("TRUNC(CER.FECHAHASTA) <= TO_DATE('" + fechaF + "', 'DD/MM/YYYY')");
		}

		sql1.GROUP_BY("CER.IDCERTIFICACION");
		sql1.GROUP_BY("CER.FECHADESDE");
		sql1.GROUP_BY("CER.FECHAHASTA");
		sql1.GROUP_BY("CER.NOMBRE");
		sql1.GROUP_BY("(CER.FECHADESDE || ' - ' || CER.FECHAHASTA)");
		sql1.GROUP_BY("CER.IDESTADOCERTIFICACION");
		sql1.GROUP_BY("F.IDPARTIDAPRESUPUESTARIA");
		sql1.GROUP_BY("P.NOMBREPARTIDA");
		sql1.GROUP_BY("F_SIGA_GETRECURSO(EST.DESCRIPCION, " + idLenguaje + ")");

		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("( " + sql1.toString() + " )");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("ROWNUM <= " + tamMaxNumber);
		}

		return sql.toString();
	}

	public String getComboEstadosCertificaciones(String idLenguaje) {

		SQL sql = new SQL();
		sql.SELECT("F_SIGA_GETRECURSO(EST.DESCRIPCION," + idLenguaje + " ) AS LABEL");
		sql.SELECT("EST.IDESTADOCERTIFICACION AS VALUE");
		sql.FROM("FCS_ESTADOSCERTIFICACIONES EST");

		return sql.toString();
	}

	public String getEstadosCertificacion(String idCertificacion, Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();
		sql.SELECT("H.IDHISTORICO");
		sql.SELECT("H.IDCERTIFICACION");
		sql.SELECT("H.IDINSTITUCION");
		sql.SELECT("F_SIGA_GETRECURSO(H.PROCESO, " + idLenguaje + ") AS PROCESO");
		sql.SELECT("H.FECHAESTADO");
		sql.SELECT("H.IDESTADO");
		sql.SELECT("F_SIGA_GETRECURSO(EST.DESCRIPCION, " + idLenguaje + ") AS ESTADO");
		sql.FROM("FCS_CERTIFICACIONES_HISTORICO_ESTADO H");
		sql.JOIN("FCS_ESTADOSCERTIFICACIONES EST ON H.IDESTADO = EST.IDESTADOCERTIFICACION");
		sql.WHERE("H.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("H.IDCERTIFICACION = " + idCertificacion);
		sql.ORDER_BY("H.FECHAESTADO DESC, H.IDESTADO DESC");

		return sql.toString();
	}

	public String getCurrentValueSequence(String sequence) {

		SQL sql = new SQL();
		sql.SELECT(sequence + ".CURRVAL");
		sql.FROM("DUAL");
		return sql.toString();
	}

	public String getMvariosAsociadosCertificacion(String idCertificacion, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("MVC.IDINSTITUCION");
		sql.SELECT("MVC.IDCERTIFICACION");
		sql.SELECT("MVC.IDMOVIMIENTO");
		sql.SELECT("M.IDPERSONA");
		sql.SELECT("DECODE(C.COMUNITARIO , '1', C.NCOMUNITARIO, C.NCOLEGIADO) NUMCOLEGIADO");
		sql.SELECT("P.APELLIDOS1");
		sql.SELECT("P.APELLIDOS2");
		sql.SELECT("(P.APELLIDOS1 || ' ' || P.APELLIDOS2) AS APELLIDOS");
		sql.SELECT("P.NOMBRE");
		sql.SELECT("M.DESCRIPCION");
		sql.SELECT("M.FECHAALTA");
		sql.SELECT("NVL(M.CANTIDAD, 0) AS IMPORTE");
		sql.FROM("FCS_MVARIOS_CERTIFICACIONES MVC");
		sql.JOIN(
				"FCS_MOVIMIENTOSVARIOS M ON M.IDINSTITUCION = MVC.IDINSTITUCION AND M.IDMOVIMIENTO = MVC.IDMOVIMIENTO");
		sql.JOIN("CEN_PERSONA P ON P.IDPERSONA = M.IDPERSONA");
		sql.JOIN("CEN_COLEGIADO C ON C.IDINSTITUCION = M.IDINSTITUCION AND C.IDPERSONA = P.IDPERSONA");
		sql.WHERE("MVC.IDCERTIFICACION = " + idCertificacion);
		sql.WHERE("MVC.IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}

	public String getFactCertificaciones(String idCertificacion, String idInstitucion, Integer tamMax,
			String idLenguaje) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("IDINSTITUCION");
		sql.SELECT("ABREVIATURA");
		sql.SELECT("IDFACTURACION");
		sql.SELECT("FECHADESDE");
		sql.SELECT("FECHAHASTA");
		sql.SELECT("NOMBRE");
		sql.SELECT("REGULARIZACION");
		sql.SELECT("DESESTADO");
		sql.SELECT("IDESTADO");
		sql.SELECT("FECHAESTADO");
		sql.SELECT("IMPORTETOTAL");
		sql.SELECT("IMPORTEPAGADO");
		sql.SELECT("IMPORTETOTAL-IMPORTEPAGADO AS IMPORTEPENDIENTE");
		sql.SELECT("PARTIDAPRESUPUESTARIA");
		sql.SELECT("GUARDIA");
		sql.SELECT("TURNO");
		sql.SELECT("PREVISION");
		sql.SELECT("IMPORTESOJ");
		sql.SELECT("IMPORTEEJG");
		
		sql2.SELECT("FAC.PREVISION");
		sql2.SELECT("FAC.IDINSTITUCION");
		sql2.SELECT("INS.ABREVIATURA");
		sql2.SELECT("FAC.IDFACTURACION");
		sql2.SELECT("FAC.FECHADESDE");
		sql2.SELECT("FAC.FECHAHASTA");
		sql2.SELECT("FAC.NOMBRE");
		sql2.SELECT("DECODE(FAC.REGULARIZACION, '1', 'Si', 'No') AS REGULARIZACION");
		sql2.SELECT("(SELECT F_SIGA_GETRECURSO(ESTADOS.DESCRIPCION, 1) DESCRIPCION "
				+ "FROM FCS_ESTADOSFACTURACION ESTADOS "
				+ "WHERE EST.IDESTADOFACTURACION = ESTADOS.IDESTADOFACTURACION) AS DESESTADO");
		sql2.SELECT("EST.IDESTADOFACTURACION AS IDESTADO");
		sql2.SELECT("EST.FECHAESTADO AS FECHAESTADO");
		sql2.SELECT("NVL(FAC.IMPORTETOTAL, 0) AS IMPORTETOTAL");
		sql2.SELECT("NVL(DECODE(EST.IDESTADOFACTURACION, 20, 0, " + "(SELECT SUM(P.IMPORTEPAGADO) "
				+ "FROM FCS_PAGOSJG P "
				+ "WHERE P.IDFACTURACION = FAC.IDFACTURACION AND P.IDINSTITUCION = FAC.IDINSTITUCION)),0) AS IMPORTEPAGADO");
		sql2.SELECT("fac.idpartidapresupuestaria as PARTIDAPRESUPUESTARIA");
		sql2.SELECT("NVL(fac.IMPORTEGUARDIA, 0) AS GUARDIA");
		sql2.SELECT("NVL(fac.IMPORTEOFICIO, 0) AS TURNO");
		sql2.SELECT("NVL(fac.IMPORTESOJ, 0) AS IMPORTESOJ");
		sql2.SELECT("NVL(fac.IMPORTEEJG, 0) AS IMPORTEEJG");

		sql2.FROM("FCS_FACTURACIONJG FAC");
		sql2.INNER_JOIN("CEN_INSTITUCION INS ON (FAC.IDINSTITUCION = INS.IDINSTITUCION)");
		sql2.INNER_JOIN(
				"FCS_FACT_ESTADOSFACTURACION EST ON (FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION)");
		sql2.INNER_JOIN(
				"FCS_FACT_CERTIFICACIONES cert ON (fac.idinstitucion = cert.idinstitucion AND fac.idfacturacion = cert.idfacturacion)");

		sql2.WHERE("FAC.IDINSTITUCION = '" + idInstitucion + "'");
		sql2.WHERE("cert.IDCERTIFICACION = " + idCertificacion);

		SQL subQueryMaxEstado = new SQL();
		subQueryMaxEstado.SELECT("MAX(EST2.IDORDENESTADO)");
		subQueryMaxEstado.FROM("FCS_FACT_ESTADOSFACTURACION EST2");
		subQueryMaxEstado.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		subQueryMaxEstado.WHERE("EST2.IDFACTURACION = EST.IDFACTURACION");

		sql2.WHERE("EST.IDORDENESTADO = (" + subQueryMaxEstado.toString() + ")");

		sql.FROM("(" + sql2.toString() + ") busqueda");

		sql.ORDER_BY("busqueda.FECHADESDE ASC");
		sql.ORDER_BY("busqueda.FECHAHASTA");
		sql.ORDER_BY("busqueda.FECHAESTADO DESC");

		SQL query = new SQL();
		query.SELECT("*");
		query.FROM("( " + sql.toString() + " )");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			query.WHERE("ROWNUM <= " + tamMaxNumber);
		}
		return query.toString();
	}

	public String getFactIdCertificaciones(String idCertificacion, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("FAC.IDFACTURACION");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.INNER_JOIN(
				"FCS_FACT_CERTIFICACIONES cert ON (fac.idinstitucion = cert.idinstitucion AND fac.idfacturacion = cert.idfacturacion)");
		sql.WHERE("FAC.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("cert.IDCERTIFICACION = " + idCertificacion);

		return sql.toString();
	}

	public String comboFactByPartidaPresu(String idpartidapresupuestaria, String idinstitucion) {

		SQL subQuery = new SQL();
		subQuery.SELECT("MAX(EST2.IDORDENESTADO)");
		subQuery.FROM("FCS_FACT_ESTADOSFACTURACION EST2");
		subQuery.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		subQuery.WHERE("EST2.IDFACTURACION = EST.IDFACTURACION");

		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION EST ON FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION");
		sql.WHERE("FAC.IDINSTITUCION = " + idinstitucion);
		sql.WHERE("EST.IDESTADOFACTURACION = 20");
		sql.WHERE("EST.IDORDENESTADO = (" + subQuery.toString() + ")");

		if (!idpartidapresupuestaria.equals("sinPartida")) {
			sql.WHERE("FAC.IDPARTIDAPRESUPUESTARIA = " + idpartidapresupuestaria);
		}

		return sql.toString();
	}

	public String comboFactNull(String idinstitucion, String IdCertificados) {
		SQL subQuery = new SQL();
		subQuery.SELECT("MAX(EST2.IDORDENESTADO)");
		subQuery.FROM("FCS_FACT_ESTADOSFACTURACION EST2");
		subQuery.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		subQuery.WHERE("EST2.IDFACTURACION = EST.IDFACTURACION");

		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION EST ON FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION");
		sql.WHERE("FAC.IDINSTITUCION = " + idinstitucion);
		if (!IdCertificados.isEmpty()) {
			sql.WHERE("FAC.IDFACTURACION NOT IN (" + IdCertificados + ")");
		}
		sql.WHERE("EST.IDESTADOFACTURACION = 20");
		sql.WHERE("EST.IDORDENESTADO = (" + subQuery.toString() + ")");
		return sql.toString();
	}

	public String getMvariosAplicadosEnPagosEjecutadosPorPeriodo(Short idInstitucion, Date fechaDesde,
			Date fechaHasta) {

		SQL subQuery = new SQL();
		subQuery.SELECT("MAX(EST2.FECHAESTADO)");
		subQuery.FROM("FCS_PAGOS_ESTADOSPAGOS EST2");
		subQuery.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		subQuery.WHERE("EST2.IDPAGOSJG = EST.IDPAGOSJG");

		SQL sql = new SQL();
		sql.SELECT("MOV.IDINSTITUCION");
		sql.SELECT("MOV.IDMOVIMIENTO");
		sql.SELECT("APM.IDPERSONA");
		sql.SELECT("DECODE(COL.COMUNITARIO , '1', COL.NCOMUNITARIO, COL.NCOLEGIADO) AS NUMCOLEGIADO");
		sql.SELECT("PER.APELLIDOS1");
		sql.SELECT("PER.APELLIDOS2");
		sql.SELECT("(PER.APELLIDOS1 || ' ' || PER.APELLIDOS2) AS APELLIDOS");
		sql.SELECT("PER.NOMBRE");
		sql.SELECT("MOV.DESCRIPCION");
		sql.SELECT("MOV.FECHAALTA");
		sql.SELECT("NVL(APM.IMPORTEAPLICADO, 0) AS IMPORTEAPLICADO");
		sql.FROM("FCS_MOVIMIENTOSVARIOS MOV");
		sql.JOIN(
				"FCS_APLICA_MOVIMIENTOSVARIOS APM ON APM.IDINSTITUCION = MOV.IDINSTITUCION AND APM.IDMOVIMIENTO = MOV.IDMOVIMIENTO");
		sql.JOIN("CEN_PERSONA PER ON APM.IDPERSONA = PER.IDPERSONA");
		sql.JOIN("CEN_COLEGIADO COL ON COL.IDPERSONA = PER.IDPERSONA AND COL.IDINSTITUCION = APM.IDINSTITUCION");
		sql.JOIN("FCS_PAGOSJG PAG ON PAG.IDINSTITUCION = APM.IDINSTITUCION AND PAG.IDPAGOSJG = APM.IDPAGOSJG");
		sql.JOIN(
				"FCS_PAGOS_ESTADOSPAGOS EST ON EST.IDINSTITUCION = PAG.IDINSTITUCION AND EST.IDPAGOSJG = PAG.IDPAGOSJG");
		sql.WHERE("MOV.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("EST.FECHAESTADO = (" + subQuery.toString() + ")");
		sql.WHERE("EST.IDESTADOPAGOSJG = " + SigaConstants.ESTADO_PAGO_EJECUTADO);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		if (fechaDesde != null) {
			String fechaF = dateFormat.format(fechaDesde);
			sql.WHERE("TRUNC(EST.FECHAESTADO) >= TO_DATE('" + fechaF + "', 'DD/MM/YYYY')");
		}

		if (fechaHasta != null) {
			String fechaF = dateFormat.format(fechaHasta);
			sql.WHERE("TRUNC(EST.FECHAESTADO) <= TO_DATE('" + fechaF + "', 'DD/MM/YYYY')");
		}

		return sql.toString();
	}

	public String getAsuntoActuacionDesignaPorMovimientos(Short idInstitucion, List<Long> idMovimientos) {

		SQL sql = new SQL();
		sql.SELECT(
				"('Designación ' || AD.ANIO || '/' || AD.NUMERO || '/' || AD.NUMEROASUNTO || '-' || NVL(PRO.NOMBRE, '')) AS ASUNTO");
		sql.SELECT("AD.IDMOVIMIENTO");
		sql.FROM("SCS_ACTUACIONDESIGNA AD "
				+ "LEFT JOIN SCS_PROCEDIMIENTOS PRO ON PRO.IDINSTITUCION = AD.IDINSTITUCION AND PRO.IDPROCEDIMIENTO = AD.IDPROCEDIMIENTO");
		sql.WHERE("AD.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("AD.IDMOVIMIENTO IN " + idMovimientos.toString().replace("[", "(").replace("]", ")"));

		return sql.toString();
	}

	public String getAsuntoActuacionAsistenciaPorMovimientos(Short idInstitucion, List<Long> idMovimientos) {

		SQL sql = new SQL();
		sql.SELECT("('Actuación de asistencia ' || ANIO || '/' || NUMERO || '/' || NUMEROASUNTO) AS ASUNTO");
		sql.SELECT("IDMOVIMIENTO");
		sql.FROM("SCS_ACTUACIONASISTENCIA");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDMOVIMIENTO IN " + idMovimientos.toString().replace("[", "(").replace("]", ")"));

		return sql.toString();
	}

	public String getAsuntoAsistenciaPorMovimientos(Short idInstitucion, List<Long> idMovimientos) {

		SQL sql = new SQL();
		sql.SELECT("('Asistencia ' || ANIO || '/' || NUMERO) AS ASUNTO");
		sql.SELECT("IDMOVIMIENTO");
		sql.FROM("SCS_ASISTENCIA");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDMOVIMIENTO IN " + idMovimientos.toString().replace("[", "(").replace("]", ")"));

		return sql.toString();
	}

	public String getAsuntoGuardiaPorMovimientos(Short idInstitucion, List<Long> idMovimientos) {

		SQL sql = new SQL();
		sql.SELECT("('Guardia ' || C.FECHAINICIO || '.' || T.NOMBRE || '>' || G.NOMBRE) AS ASUNTO");
		sql.SELECT("IDMOVIMIENTO");
		sql.FROM("SCS_CABECERAGUARDIAS C");
		sql.JOIN("SCS_TURNO T ON T.IDINSTITUCION = C.IDINSTITUCION AND T.IDTURNO = C.IDTURNO");
		sql.JOIN(
				"SCS_GUARDIASTURNO G ON G.IDINSTITUCION = C.IDINSTITUCION AND G.IDTURNO = C.IDTURNO AND G.IDGUARDIA = C.IDGUARDIA");
		sql.WHERE("C.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("C.IDMOVIMIENTO IN " + idMovimientos.toString().replace("[", "(").replace("]", ")"));

		return sql.toString();
	}

	public String getGrupoFacturacionPorFacturacion(Short idInstitucion, String idFacturacion, String idLenguaje) {

		SQL sql = new SQL();
		sql.SELECT("F_SIGA_GETRECURSO(G.NOMBRE, " + idLenguaje + ") AS GRUPOFACTURACION");
		sql.FROM("FCS_FACT_GRUPOFACT_HITO H");
		sql.JOIN(
				"SCS_GRUPOFACTURACION G ON G.IDINSTITUCION = H.IDINSTITUCION AND G.IDGRUPOFACTURACION = H.IDGRUPOFACTURACION");
		sql.WHERE("H.IDFACTURACION = " + idFacturacion);
		sql.WHERE("H.IDINSTITUCION = " + idInstitucion);
		sql.GROUP_BY("H.IDINSTITUCION");
		sql.GROUP_BY("H.IDGRUPOFACTURACION");
		sql.GROUP_BY("F_SIGA_GETRECURSO(G.NOMBRE, " + idLenguaje + ")");

		return sql.toString();
	}

}
