package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FacFacturaSqlProvider;

import java.text.SimpleDateFormat;


public class FacFacturaExtendsSqlProvider extends FacFacturaSqlProvider {

    public String getFacturas(FacturaItem item, String idInstitucion, String idLenguaje) {

        SQL numComunicaciones = new SQL();
        SQL ultComunicacion = new SQL();
        SQL facturasPendientes = new SQL();
        SQL adeudos = new SQL();
        SQL devoluciones = new SQL();
        SQL facturas = new SQL();
        SQL sqlFacturas = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        //num facturas pendientes
        if(item.getFacturasPendientesDesde() != null || item.getFacturasPendientesHasta() != null){
            facturasPendientes.SELECT("IDPERSONA");
            facturasPendientes.FROM("FAC_FACTURA");
            facturasPendientes.WHERE("IDINSTITUCION="+idInstitucion);
            facturasPendientes.GROUP_BY("IDPERSONA");
            facturasPendientes.HAVING("SUM(IMPTOTALPORPAGAR)>0");
            if(item.getFacturasPendientesHasta() != null)
                facturasPendientes.HAVING("COUNT(IMPTOTALPORPAGAR) <=to_number(" + item.getFacturasPendientesHasta() + ",'99999999999999999')");
            if(item.getFacturasPendientesDesde() != null)
                facturasPendientes.HAVING("COUNT(IMPTOTALPORPAGAR) >=to_number(" + item.getFacturasPendientesDesde() + ",'99999999999999999')");
        }

        //num comunicaciones
        numComunicaciones.SELECT("COUNT(1)");
        numComunicaciones.FROM("env_comunicacionmorosos m");
        numComunicaciones.WHERE("m.idinstitucion = f.idinstitucion AND m.idpersona = f.idpersona AND m.idfactura = f.idfactura");

        //ult comunicacion
        ultComunicacion.SELECT("MAX(m.fecha_envio)");
        ultComunicacion.FROM("env_comunicacionmorosos m");
        ultComunicacion.WHERE("m.idinstitucion = f.idinstitucion AND m.idpersona = f.idpersona AND m.idfactura = f.idfactura");

        //select de facturas
        facturas.SELECT("'FACTURA' tipo,f.idfactura,f.numerofactura,f.idinstitucion,f.fechaemision fecha,f.idprogramacion,fp.descripcion facturacion,"
                + "nvl(nvl(col.ncolegiado,col.ncomunitario),p.nifcif) ncolident,nvl(p.apellidos1 || ' ' || nvl(p.apellidos2, '') || ', ' ||  p.nombre, p.nombre) nombreCompleto,"
                + "f.imptotal,f.imptotalporpagar,f.estado idestado,r.descripcion estado, (" + numComunicaciones.toString() + ") numcomunicaciones,"
                + "(" + ultComunicacion.toString() + ") ultcomunicacion,p.idpersona");

        //joins
        facturas.FROM("fac_factura f");
        facturas.INNER_JOIN("fac_facturacionprogramada fp ON (fp.idinstitucion = f.idinstitucion AND fp.idprogramacion = f.idprogramacion AND f.idseriefacturacion = fp.idseriefacturacion)");
        facturas.INNER_JOIN("cen_cliente c ON (c.idpersona = f.idpersona AND c.idinstitucion = f.idinstitucion)");
        facturas.INNER_JOIN("cen_persona p ON (p.idpersona = f.idpersona)");
        facturas.LEFT_OUTER_JOIN("cen_colegiado col ON (col.idpersona = p.idpersona AND col.idinstitucion = f.idinstitucion)");
        facturas.LEFT_OUTER_JOIN("fac_estadofactura ef ON (ef.idestado = f.estado)");
        facturas.LEFT_OUTER_JOIN("gen_recursos r ON (ef.descripcion = r.idrecurso AND r.idlenguaje =" + idLenguaje + ")");

        //filtro
        if (idInstitucion.equals(SigaConstants.InstitucionGeneral)) {
            if (item.getIdInstitucion() != null) {
                facturas.WHERE("f.idinstitucion = " + item.getIdInstitucion());
            }
        } else {
            if (item.getIdInstitucion() != null) {
                facturas.WHERE("f.idinstitucion = " + item.getIdInstitucion());
            } else {
                facturas.WHERE("f.idinstitucion = " + idInstitucion);
            }
        }

        if(item.getFacturasPendientesDesde() != null || item.getFacturasPendientesHasta() != null) {
            facturas.WHERE("c.idpersona IN (" + facturasPendientes.toString() + ")");
        }

        //numero factura
        if (item.getNumeroFactura() != null) {
            facturas.WHERE("UPPER(f.numerofactura) LIKE UPPER('%"+item.getNumeroFactura()+"%')");
        }

        //estados
        if (null!=item.getEstadosFiltroFac() && !item.getEstadosFiltroFac().isEmpty()) {
            StringBuilder aux = new StringBuilder();
            for (String s : item.getEstadosFiltroFac()) {
                aux.append(s).append(",");
            }
            aux.deleteCharAt(aux.length()-1);
            facturas.WHERE("f.estado in (" + aux + ")");
        }

        //forma de pago o abono
        if (item.getFormaCobroFactura() != null) {
            facturas.WHERE("f.idformapago=" + item.getFormaCobroFactura());
        }

        //fecha emision
        if (item.getFechaEmisionDesde() != null) {
            fecha = dateFormat.format(item.getFechaEmisionDesde());
            facturas.WHERE("f.fechaemision >= TO_DATE('" + fecha + "', 'DD/MM/YYYY')");
        }
        if (item.getFechaEmisionHasta() != null) {
            fecha = dateFormat.format(item.getFechaEmisionHasta());
            facturas.WHERE("f.fechaemision <= TO_DATE('" + fecha + "', 'DD/MM/YYYY')");
        }

        //importe facturado
        if (item.getImportefacturadoDesde() != null && !item.getImportefacturadoDesde().isEmpty()) {
            facturas.WHERE("f.imptotal>=to_number(" + item.getImportefacturadoDesde() + ",'99999999999999999.99')");
        }
        if (item.getImportefacturadoHasta() != null && !item.getImportefacturadoHasta().isEmpty()) {
            facturas.WHERE("f.imptotal<=to_number(" + item.getImportefacturadoHasta() + ",'99999999999999999.99')");
        }

        //contabilizado SI('S') NO('N')
        if (item.getContabilizado() != null && item.getContabilizado().equalsIgnoreCase("S")) {
            facturas.WHERE("f.contabilizada = 'S'");
        }
        if (item.getContabilizado() != null && item.getContabilizado().equalsIgnoreCase("N")) {
            facturas.WHERE("f.contabilizada = 'N'");
        }

        //serie
        if (item.getSerie() != null) {
            facturas.WHERE("f.idseriefacturacion=" + item.getSerie());
        }

        //facturacion
        if (item.getFacturacion() != null) {
            String[] facturacion = item.getFacturacion().split("-");
            if (facturacion.length == 2) {
                facturas.WHERE("f.idprogramacion=" + facturacion[0].trim());
                facturas.WHERE("f.idseriefacturacion=" + facturacion[1].trim());
            }
        }

        //importe adeudado
        if (item.getImporteAdeudadoDesde() != null && !item.getImporteAdeudadoDesde().isEmpty()) {
            facturas.WHERE("f.imptotalporpagar>=to_number(" + item.getImporteAdeudadoDesde() + ",'99999999999999999.99')");
        }
        if (item.getImporteAdeudadoHasta() != null && !item.getImporteAdeudadoHasta().isEmpty()) {
            facturas.WHERE("f.imptotalporpagar<=to_number(" + item.getImporteAdeudadoHasta() + ",'99999999999999999.99')");
        }

        //id persona
        if (item.getNumeroIdentificacion() != null) {
            facturas.WHERE("UPPER(p.nifcif) LIKE UPPER('%"+item.getNumeroIdentificacion()+"%')");
        }

        //numero colegiado
        if(item.getNumeroColegiado() != null) {
            facturas.WHERE("col.NCOLEGIADO="+item.getNumeroColegiado());
        }

        //nombre
        if(item.getNombre()!=null) {
            facturas.WHERE("UPPER(p.nombre) LIKE UPPER('%"+item.getNombre()+"%')");
        }
        if(item.getApellidos()!=null) {
            facturas.WHERE("UPPER(p.apellidos1 || ' ' || nvl(p.apellidos2, '')) LIKE UPPER('%"+item.getApellidos()+"%')");
        }

        //fichero adeudos
        if (item.getIdentificadorAdeudos() != null) {
            adeudos.SELECT("idfactura");
            adeudos.FROM("fac_facturaincluidaendisquete");
            adeudos.WHERE("idinstitucion = f.idinstitucion AND iddisquetecargos = " + item.getIdentificadorAdeudos());

            facturas.WHERE("f.idfactura IN (" + adeudos.toString() + ")");
        }

        //fichero devoluciones
        if (item.getIdentificadorDevolucion() != null) {
            devoluciones.SELECT("d.idfactura");
            devoluciones.FROM("FAC_LINEADEVOLUDISQBANCO b, fac_facturaincluidaendisquete d");
            devoluciones.WHERE("b.idinstitucion = f.idinstitucion AND d.idinstitucion=b.idinstitucion AND "
                    + "d.idfacturaincluidaendisquete=b.idfacturaincluidaendisquete AND d.iddisquetecargos=b.iddisquetecargos"
                    + " AND iddisquetedevoluciones=" + item.getIdentificadorDevolucion());

            facturas.WHERE("f.idfactura IN (" + devoluciones.toString() + ")");
        }

        facturas.ORDER_BY("f.numerofactura DESC");

        //query completa facturas
        sqlFacturas.SELECT("*");
        sqlFacturas.FROM("(" + facturas.toString() + ")");
        sqlFacturas.WHERE("ROWNUM < 201");

        //num facturas pendiente
        if (item.getFacturasPendientesDesde() != null && !item.getFacturasPendientesDesde().isEmpty()) {
            //sqlFacturas.WHERE("TOTALPENDIENTE>=to_number(" + item.getFacturasPendientesDesde() + ",'99999999999999999.99')");
        }
        if (item.getFacturasPendientesHasta() != null && !item.getFacturasPendientesHasta().isEmpty()) {
            //sqlFacturas.WHERE("TOTALPENDIENTE<=to_number(" + item.getFacturasPendientesHasta() + ",'99999999999999999.99')");
        }

        //num comunicaciones
        if (item.getComunicacionesFacturasDesde() != null && !item.getComunicacionesFacturasDesde().isEmpty()) {
            sqlFacturas.WHERE("numcomunicaciones>=to_number(" + item.getComunicacionesFacturasDesde() + ",'99999999999999999.99')");
        }
        if (item.getComunicacionesFacturasHasta() != null && !item.getComunicacionesFacturasHasta().isEmpty()) {
            sqlFacturas.WHERE("numcomunicaciones<=to_number(" + item.getComunicacionesFacturasHasta() + ",'99999999999999999.99')");
        }

        return sqlFacturas.toString();
    }

    public String getFactura(String idFactura, String idInstitucion) {

        SQL query = new SQL();

        //select de factura
        query.SELECT("'FACTURA' TIPO,F.IDFACTURA,F.IDINSTITUCION,F.NUMEROFACTURA,F.FECHAEMISION,F.IMPTOTAL,"
            +"F.IMPTOTALNETO,F.IMPTOTALIVA,F.IMPTOTALANTICIPADO,F.IMPTOTALCOMPENSADO,F.IMPTOTALPAGADOPORCAJA,"
            +"F.IMPTOTALPAGADOPORBANCO,F.IMPTOTALPAGADO,F.IMPTOTALPORPAGAR,F.OBSERVACIONES,F.OBSERVINFORME,"
            +"FS.IDSERIEFACTURACION,F.IDPROGRAMACION,FS.NOMBREABREVIADO,FS.DESCRIPCION FACTURACION,FS.FECHAMODIFICACION,"
            +"P.IDPERSONA,P.NIFCIF,P.NOMBRE,(P.APELLIDOS1 || ' ' || NVL(P.APELLIDOS2, '')) APELLIDOS,"
            +"NVL(COL.NCOLEGIADO,COL.NCOMUNITARIO) NCOLIDENT,F.IDPERSONADEUDOR,M.ACREEDOR_ID,M.ACREEDOR_NOMBRE,"
            +"FA.IMPTOTAL IMPANULADO,FA.IMPTOTALABONADOEFECTIVO,FA.IMPTOTALABONADOPORBANCO,FA.IMPTOTALABONADO,FA.IMPPENDIENTEPORABONAR,"
            +"FB.BANCOS_CODIGO");

        query.FROM("FAC_FACTURA F");
        query.INNER_JOIN("FAC_SERIEFACTURACION FS ON (FS.IDSERIEFACTURACION = F.IDSERIEFACTURACION AND FS.IDINSTITUCION = F.IDINSTITUCION)");
        query.LEFT_OUTER_JOIN("FAC_ABONO FA ON (F.IDFACTURA = FA.IDFACTURA AND F.IDINSTITUCION = FA.IDINSTITUCION)");
        query.INNER_JOIN("CEN_PERSONA P ON (P.IDPERSONA = F.IDPERSONA)");
        query.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS M ON (M.IDPERSONA = F.IDPERSONADEUDOR AND M.IDINSTITUCION = F.IDINSTITUCION AND M.IDMANDATO = 1)");
        query.LEFT_OUTER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = P.IDPERSONA AND COL.IDINSTITUCION = F.IDINSTITUCION)");
        query.LEFT_OUTER_JOIN("FAC_SERIEFACTURACION_BANCO FB ON (FB.IDSERIEFACTURACION = F.IDSERIEFACTURACION AND FB.IDINSTITUCION = F.IDINSTITUCION)");

        query.WHERE("F.IDFACTURA ="+idFactura);
        query.WHERE("F.IDINSTITUCION ="+idInstitucion);

        return query.toString();
    }


    public String getInformeFacturacionActual(String idSerieFacturacion, String idProgramacion, String idInstitucion, String idLenguaje) {
        SQL sql = new SQL();

        // Select
        sql.SELECT("'ACTUAL' as momento, F_SIGA_GETRECURSO(pf.DESCRIPCION, "+ idLenguaje+") AS formaPago,"
                +"COUNT(*) AS numeroFacturas, SUM(ff.IMPTOTAL) AS total, SUM(ff.IMPTOTALPORPAGAR) AS totalPendiente");

        // From
        sql.FROM("FAC_FACTURA ff");
        sql.INNER_JOIN("PYS_FORMAPAGO pf ON (ff.IDFORMAPAGO = pf.IDFORMAPAGO)");

        // Where
        sql.WHERE("ff.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("ff.IDSERIEFACTURACION = " + idSerieFacturacion);
        sql.WHERE("ff.IDPROGRAMACION = " + idProgramacion);

        // Order by
        sql.GROUP_BY("pf.DESCRIPCION");

        return sql.toString();
    }

    public String getInformeFacturacionOriginal(String idSerieFacturacion, String idProgramacion, String idInstitucion, String idLenguaje) {
        SQL sql = new SQL();

        // Select
        sql.SELECT("'ORIGINAL' as momento, F_SIGA_GETRECURSO(pf.DESCRIPCION, "+ idLenguaje+") AS formaPago,"
                +"COUNT(*) AS numeroFacturas, SUM(fh.IMPTOTALPAGADO) AS total, SUM(fh.IMPTOTALPORPAGAR) AS totalPendiente");

        // From
        sql.FROM("FAC_FACTURA ff");
        sql.INNER_JOIN("FAC_HISTORICOFACTURA fh ON (ff.IDINSTITUCION = fh.IDINSTITUCION AND ff.IDFACTURA = fh.IDFACTURA AND fh.IDHISTORICO = 1)");
        sql.INNER_JOIN("PYS_FORMAPAGO pf ON (ff.IDFORMAPAGO = pf.IDFORMAPAGO)");

        // Where
        sql.WHERE("ff.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("ff.IDSERIEFACTURACION = " + idSerieFacturacion);
        sql.WHERE("ff.IDPROGRAMACION = " + idProgramacion);

        // Order by
        sql.GROUP_BY("pf.DESCRIPCION");

        return sql.toString();
    }

    public String getNewFacturaID(String idInstitucion) {

        SQL query = new SQL();

        query.SELECT("MAX(TO_NUMBER(IDFACTURA))+1 AS ID");
        query.FROM("FAC_FACTURA ff");
        query.WHERE("IDINSTITUCION ="+idInstitucion);

        return query.toString();
    }

    public String getNuevoNumeroFactura(String idInstitucion, String idSerieFacturacion){

        SQL sql = new SQL();

        // Select
        sql.SELECT("ac.PREFIJO || LPAD(ac.CONTADOR + 1, ac.LONGITUDCONTADOR, '0') || ac.SUFIJO AS NUEVOCONTADOR");

        // From
        sql.FROM("ADM_CONTADOR ac");
        sql.INNER_JOIN("FAC_SERIEFACTURACION fs ON(ac.IDINSTITUCION = fs.IDINSTITUCION AND ac.IDCONTADOR = fs.IDCONTADOR)");

        // Where
        sql.WHERE("fs.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("fs.IDSERIEFACTURACION = " + idSerieFacturacion);

        return sql.toString();
    }

    public String getFacturasDeFacturacionProgramada(String institucion, String seriefacturacion, String idProgramacion) {

        SQL sql = new SQL();
        sql.SELECT("F.IDFACTURA");
        sql.SELECT("F.NUMEROFACTURA");
        sql.SELECT("F.IDINSTITUCION");
        sql.SELECT("F.IDPERSONA");
        sql.SELECT("FP.IDESTADOPDF");
        sql.FROM("FAC_FACTURA F");
        sql.INNER_JOIN("FAC_FACTURACIONPROGRAMADA FP ON F.IDINSTITUCION = FP.IDINSTITUCION AND F.IDSERIEFACTURACION = FP.IDSERIEFACTURACION AND F.IDPROGRAMACION = FP.IDPROGRAMACION");
        sql.WHERE("F.IDINSTITUCION = " + institucion);
        sql.WHERE("F.IDSERIEFACTURACION = " + seriefacturacion);
        sql.WHERE("F.IDPROGRAMACION = " + idProgramacion);
        sql.ORDER_BY("F.FECHAEMISION");

        return sql.toString();
    }

    public String obtenerFacturasFacturacionRapida(String idInstitucion, String idPeticion, String idSolicitudCertificado) {
        SQL sql = new SQL();
        sql.SELECT_DISTINCT("F.IDINSTITUCION, F.IDPERSONA, F.IDFACTURA, F.NUMEROFACTURA, F.IDSERIEFACTURACION, F.IDPROGRAMACION, F.ESTADO, C.IDPETICION");
        sql.FROM("FAC_FACTURA F, PYS_COMPRA C");
        sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("C.IDINSTITUCION = F.IDINSTITUCION");
        sql.WHERE("C.IDFACTURA = F.IDFACTURA");
        if (!UtilidadesString.esCadenaVacia(idPeticion)) {
            sql.WHERE("C.IDPETICION = " + idPeticion);
        } else {
            SQL subQuery = new SQL();
            subQuery.SELECT("1");
            subQuery.FROM("CER_SOLICITUDCERTIFICADOS CER");
            subQuery.WHERE("C.IDINSTITUCION = CER.IDINSTITUCION");
            subQuery.WHERE("C.IDPETICION = CER.IDPETICIONPRODUCTO");
            subQuery.WHERE("C.IDTIPOPRODUCTO = CER.PPN_IDTIPOPRODUCTO");
            subQuery.WHERE("C.IDPRODUCTO = CER.PPN_IDPRODUCTO");
            subQuery.WHERE("C.IDPRODUCTOINSTITUCION = CER.PPN_IDPRODUCTOINSTITUCION");
            subQuery.WHERE("CER.IDSOLICITUD = " + idSolicitudCertificado);
            sql.WHERE("EXISTS (" + subQuery + ")");
        }
        return sql.toString();
    }
}