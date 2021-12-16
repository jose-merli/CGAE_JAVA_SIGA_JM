package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.BusquedaRetencionesRequestDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsCertificacionesSqlProvider;

import java.text.SimpleDateFormat;

public class FcsCertificacionesSqlExtendsProvider extends FcsCertificacionesSqlProvider {

    public String buscarCertificaciones(BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, Integer tamMax, String idLenguaje) {

        SQL sql1 = new SQL();
        sql1.SELECT("CER.FECHADESDE");
        sql1.SELECT("CER.FECHAHASTA");
        sql1.SELECT("(CER.FECHADESDE || ' - ' || CER.FECHAHASTA) AS PERIODO");
        sql1.SELECT("CER.NOMBRE");
        sql1.SELECT("NVL(F.IMPORTEOFICIO, 0) AS TURNO");
        sql1.SELECT("NVL(F.IMPORTEGUARDIA , 0) AS GUARDIA");
        sql1.SELECT("NVL(F.IMPORTEEJG , 0) AS EJG");
        sql1.SELECT("NVL(F.IMPORTESOJ , 0) AS SOJ");
        sql1.SELECT("NVL(F.IMPORTETOTAL, 0) TOTAL");
        sql1.SELECT("CER.IDESTADOCERTIFICACION");
        sql1.SELECT("F_SIGA_GETRECURSO(EST.DESCRIPCION, " + idLenguaje + ") AS ESTADO");
        sql1.FROM("FCS_CERTIFICACIONES CER");
        sql1.JOIN("FCS_ESTADOSCERTIFICACIONES EST ON CER.IDESTADOCERTIFICACION = EST.IDESTADOCERTIFICACION");
        sql1.JOIN("FCS_FACT_CERTIFICACIONES FC ON CER.IDINSTITUCION = FC.IDINSTITUCION AND CER.IDCERTIFICACION = FC.IDCERTIFICACION");
        sql1.JOIN("FCS_FACTURACIONJG F ON F.IDINSTITUCION = FC.IDINSTITUCION AND F.IDFACTURACION = FC.IDFACTURACION");

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if (busquedaRetencionesRequestDTO.getFechaDesde() != null) {
            String fechaF = dateFormat.format(busquedaRetencionesRequestDTO.getFechaDesde());
            sql1.WHERE("CER.FECHADESDE >= TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
        }

        if (busquedaRetencionesRequestDTO.getFechaHasta() != null) {
            String fechaF = dateFormat.format(busquedaRetencionesRequestDTO.getFechaHasta());
            sql1.WHERE("CER.FECHAHASTA <= TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
        }

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

}
