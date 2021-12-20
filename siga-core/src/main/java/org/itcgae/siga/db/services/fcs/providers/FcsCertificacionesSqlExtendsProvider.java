package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.BusquedaRetencionesRequestDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsCertificacionesSqlProvider;

import java.text.SimpleDateFormat;

public class FcsCertificacionesSqlExtendsProvider extends FcsCertificacionesSqlProvider {

    public String buscarCertificaciones(BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, Integer tamMax, String idLenguaje) {

        SQL sql1 = new SQL();
        sql1.SELECT("CER.IDCERTIFICACION");
        sql1.SELECT("CER.FECHADESDE");
        sql1.SELECT("CER.FECHAHASTA");
        sql1.SELECT("(CER.FECHADESDE || ' - ' || CER.FECHAHASTA) AS PERIODO");
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
        sql1.JOIN("FCS_ESTADOSCERTIFICACIONES EST ON CER.IDESTADOCERTIFICACION = EST.IDESTADOCERTIFICACION");
        sql1.JOIN("FCS_FACT_CERTIFICACIONES FC ON CER.IDINSTITUCION = FC.IDINSTITUCION AND CER.IDCERTIFICACION = FC.IDCERTIFICACION");
        sql1.JOIN("FCS_FACTURACIONJG F ON F.IDINSTITUCION = FC.IDINSTITUCION AND F.IDFACTURACION = FC.IDFACTURACION " +
                "LEFT JOIN SCS_PARTIDAPRESUPUESTARIA P ON P.IDINSTITUCION = F.IDINSTITUCION AND P.IDPARTIDAPRESUPUESTARIA = F.IDPARTIDAPRESUPUESTARIA");

        if (!UtilidadesString.esCadenaVacia(busquedaRetencionesRequestDTO.getIdCertificacion())) {
            sql1.WHERE("CER.IDCERTIFICACION = " + busquedaRetencionesRequestDTO.getIdCertificacion());
        }

        if (busquedaRetencionesRequestDTO.getIdInstitucionList() != null && !busquedaRetencionesRequestDTO.getIdInstitucionList().isEmpty()) {
            sql1.WHERE("CER.IDINSTITUCION IN " + busquedaRetencionesRequestDTO.getIdInstitucionList().toString().replace("[", "(").replace("]", ")"));
        }

        if (busquedaRetencionesRequestDTO.getIdEstadoCertificacionList() != null && !busquedaRetencionesRequestDTO.getIdEstadoCertificacionList().isEmpty()) {
            sql1.WHERE("CER.IDESTADOCERTIFICACION IN " + busquedaRetencionesRequestDTO.getIdEstadoCertificacionList().toString().replace("[", "(").replace("]", ")"));
        }

        if (busquedaRetencionesRequestDTO.getIdHitoGeneralList() != null && !busquedaRetencionesRequestDTO.getIdHitoGeneralList().isEmpty()) {
            sql1.WHERE("CER.IDHITOGENERAL IN " + busquedaRetencionesRequestDTO.getIdHitoGeneralList().toString().replace("[", "(").replace("]", ")"));
        }

        if (busquedaRetencionesRequestDTO.getIdGrupoFacturacionList() != null && !busquedaRetencionesRequestDTO.getIdGrupoFacturacionList().isEmpty()) {
            sql1.WHERE("CER.IDGRUPOFACTURACION IN " + busquedaRetencionesRequestDTO.getIdGrupoFacturacionList().toString().replace("[", "(").replace("]", ")"));
        }

        if (busquedaRetencionesRequestDTO.getIdPartidaPresupuestariaList() != null && !busquedaRetencionesRequestDTO.getIdPartidaPresupuestariaList().isEmpty()) {
            sql1.WHERE("CER.IDPARTIDAPRESUPUESTARIA IN " + busquedaRetencionesRequestDTO.getIdPartidaPresupuestariaList().toString().replace("[", "(").replace("]", ")"));
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
        sql1.GROUP_BY("F_SIGA_GETRECURSO(EST.DESCRIPCION, 1)");

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
        sql.ORDER_BY("H.FECHAESTADO DESC");

        return sql.toString();
    }

}
