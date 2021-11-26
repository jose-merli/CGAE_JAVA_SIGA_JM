package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.db.mappers.FacFacturaSqlProvider;

import java.text.SimpleDateFormat;


public class FacFacturaExtendsSqlProvider extends FacFacturaSqlProvider {

    public String getFacturas(FacturaItem item, String idInstitucion, String idLenguaje) {

        SQL numComunicaciones = new SQL();
        SQL ultComunicacion = new SQL();
        SQL adeudos = new SQL();
        SQL devoluciones = new SQL();
        SQL facturas = new SQL();
        SQL sqlFacturas = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        //num comunicaciones
        numComunicaciones.SELECT("COUNT(1)");
        numComunicaciones.FROM("env_comunicacionmorosos m");
        numComunicaciones.WHERE("m.idinstitucion = f.idinstitucion AND m.idpersona = f.idpersona AND m.idfactura = f.idfactura");

        //ult comunicacion
        ultComunicacion.SELECT("MAX(m.fecha_envio)");
        ultComunicacion.FROM("env_comunicacionmorosos m");
        ultComunicacion.WHERE("m.idinstitucion = f.idinstitucion AND m.idpersona = f.idpersona AND m.idfactura = f.idfactura");

        //select de facturas
        facturas.SELECT("'FACTURA' tipo,f.idfactura,f.numerofactura,f.idinstitucion,f.fechaemision,f.idprogramacion,fp.descripcion facturacion,"
                + "nvl(nvl(col.ncolegiado,col.ncomunitario),p.nifcif) ncolident,p.nombre,(p.apellidos1 || ' ' || nvl(p.apellidos2, '')) apellidos,"
                + "f.imptotal,f.imptotalporpagar,f.estado idestado,r.descripcion estado, (" + numComunicaciones.toString() + ") numcomunicaciones,"
                + "(" + ultComunicacion.toString() + ") ultcomunicacion");

        //joins
        facturas.FROM("fac_factura f");
        facturas.INNER_JOIN("fac_facturacionprogramada fp ON (fp.idinstitucion = f.idinstitucion AND fp.idprogramacion = f.idprogramacion AND f.idseriefacturacion = fp.idseriefacturacion)");
        facturas.INNER_JOIN("cen_cliente c ON (c.idpersona = f.idpersona AND c.idinstitucion = f.idinstitucion)");
        facturas.INNER_JOIN("cen_persona p ON (p.idpersona = f.idpersona)");
        facturas.LEFT_OUTER_JOIN("cen_colegiado col ON (col.idpersona = p.idpersona AND col.idinstitucion = f.idinstitucion)");
        facturas.LEFT_OUTER_JOIN("fac_estadofactura ef ON (ef.idestado = f.estado)");
        facturas.LEFT_OUTER_JOIN("gen_recursos r ON (ef.descripcion = r.idrecurso AND r.idlenguaje =" + idLenguaje + ")");

        //filtros
        facturas.WHERE("f.idinstitucion =" + idInstitucion);

        //numero factura
        if (item.getNumeroFactura() != null) {
            facturas.WHERE("UPPER(f.numerofactura) LIKE UPPER('%"+item.getNumeroFactura()+"%')");
        }

        //estados
        if (item.getEstado() != null) {
            facturas.WHERE("f.estado=" + item.getEstado());
        }

        //forma de pago o abono
        if (item.getFormaCobroAbono() != null) {
            facturas.WHERE("f.idformapago=" + item.getFormaCobroAbono());
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
            facturas.WHERE("f.importetotal>=to_number(" + item.getImportefacturadoDesde() + ",'99999999999999999.99')");
        }
        if (item.getImportefacturadoHasta() != null && !item.getImportefacturadoHasta().isEmpty()) {
            facturas.WHERE("f.importetotal<=to_number(" + item.getImportefacturadoHasta() + ",'99999999999999999.99')");
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
            facturas.WHERE("f.idprogramacion=" + item.getFacturacion());
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
        if(item.getNumeroColegiado()!=null) {
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
                    + "d.idfacturaincluidaendisquete=b.idfacturaincluidaendisquete AND iddisquetedevoluciones=" + item.getIdentificadorDevolucion());

            facturas.WHERE("f.idfactura IN (" + devoluciones.toString() + ")");
        }

        facturas.ORDER_BY("f.numerofactura DESC");

        //query completa facturas
        sqlFacturas.SELECT("*");
        sqlFacturas.FROM("(" + facturas.toString() + ")");
        sqlFacturas.WHERE("ROWNUM < 201");

        //num facturas pendiente
        if (item.getFacturasPendientesDesde() != null && !item.getFacturasPendientesDesde().isEmpty()) {
            sqlFacturas.WHERE("TOTALPENDIENTE>=to_number(" + item.getFacturasPendientesDesde() + ",'99999999999999999.99')");
        }
        if (item.getFacturasPendientesHasta() != null && !item.getFacturasPendientesHasta().isEmpty()) {
            sqlFacturas.WHERE("TOTALPENDIENTE<=to_number(" + item.getFacturasPendientesHasta() + ",'99999999999999999.99')");
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

    public String getAbonos(FacturaItem item, String idInstitucion, String idLenguaje) {

        SQL transferencia = new SQL();
        SQL abonos = new SQL();
        SQL sqlAbonos = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        //select de abonos
        abonos.SELECT("'ABONO' tipo,f.idabono,f.numeroabono,f.idinstitucion,f.fecha fecha,"
                + "nvl(nvl(col.ncolegiado,col.ncomunitario),p.nifcif) ncolident,p.nombre nombre,(p.apellidos1 || ' ' || nvl(p.apellidos2, '')) apellidos,"
                + "f.imptotal total,f.imppendienteporabonar pendiente,f.estado idestado,r.descripcion estado");

        //joins
        abonos.FROM("fac_abono f");
        abonos.INNER_JOIN("cen_cliente c ON (c.idpersona = f.idpersona AND c.idinstitucion = f.idinstitucion)");
        abonos.INNER_JOIN("cen_persona p ON (p.idpersona = f.idpersona)");
        abonos.LEFT_OUTER_JOIN("cen_colegiado col ON (col.idpersona = p.idpersona AND col.idinstitucion = f.idinstitucion)");
        abonos.LEFT_OUTER_JOIN("fac_estadoabono ef ON (ef.idestado = f.estado)");
        abonos.LEFT_OUTER_JOIN("gen_recursos r ON (ef.descripcion = r.idrecurso  AND r.idlenguaje = "+ idLenguaje +")");

        //filtros
        abonos.WHERE("f.idinstitucion ="+idInstitucion);

        //numero factura
        if(item.getNumeroFactura()!=null) {
            abonos.WHERE("UPPER(f.numeroabono) LIKE UPPER('%"+item.getNumeroFactura()+"%')");
        }

        //estados
        if(item.getEstado()!=null) {
            abonos.WHERE("f.estado="+item.getEstado());
        }

        //forma de pago o abono
        if(item.getFormaCobroAbono()!=null) {
            //abonos.WHERE("f.idformapago="+item.getFormaCobroAbono());
        }

        //fecha emision
        if(item.getFechaEmisionDesde()!=null) {
            fecha = dateFormat.format(item.getFechaEmisionDesde());
            abonos.WHERE("f.fechaemision >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }
        if(item.getFechaEmisionHasta()!=null) {
            fecha = dateFormat.format(item.getFechaEmisionHasta());
            abonos.WHERE("f.fechaemision <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }

        //importe facturado
        if(item.getImportefacturadoDesde()!=null && !item.getImportefacturadoDesde().isEmpty()) {
            abonos.WHERE("f.importetotal>=to_number("+item.getImportefacturadoDesde()+",'99999999999999999.99')");
        }
        if(item.getImportefacturadoHasta()!=null && !item.getImportefacturadoHasta().isEmpty()) {
            abonos.WHERE("f.importetotal<=to_number("+item.getImportefacturadoHasta()+",'99999999999999999.99')");
        }

        //contabilizado SI('S') NO('N')
        if(item.getContabilizado()!=null && item.getContabilizado().equalsIgnoreCase("S")) {
            abonos.WHERE("f.contabilizada = 'S'");
        }
        if(item.getContabilizado()!=null && item.getContabilizado().equalsIgnoreCase("N")) {
            abonos.WHERE("f.contabilizada = 'N'");
        }

        //abono SJCS
        if(item.getNumeroAbonoSJCS()!=null) {
            abonos.WHERE("f.IDPAGOSJG="+item.getNumeroAbonoSJCS());
        }

        //id persona
        if (item.getNumeroIdentificacion() != null) {
            abonos.WHERE("UPPER(p.nifcif) LIKE UPPER('%"+item.getNumeroIdentificacion()+"%')");
        }

        //numero colegiado
        if(item.getNumeroColegiado()!=null) {
            abonos.WHERE("col.NCOLEGIADO="+item.getNumeroColegiado());
        }

        //nombre
        if(item.getNombre()!=null) {
            abonos.WHERE("UPPER(p.nombre) LIKE UPPER('%"+item.getNombre()+"%')");
        }
        if(item.getApellidos()!=null) {
            abonos.WHERE("UPPER(p.apellidos1 || ' ' || nvl(p.apellidos2, '')) LIKE UPPER('%"+item.getApellidos()+"%')");
        }

        //fichero transferencia
        if(item.getIdentificadorTransferencia()!=null){
            transferencia.SELECT("idabono");
            transferencia.FROM("fac_abonoincluidaendisquete");
            transferencia.WHERE("idinstitucion = f.idinstitucion AND iddisquetecargos = "+ item.getIdentificadorTransferencia());

            abonos.WHERE("idabono IN (" + transferencia.toString() + ")");
        }

        abonos.ORDER_BY("f.numeroabono DESC");

        //query completa abonos
        sqlAbonos.SELECT("*");
        sqlAbonos.FROM("("+abonos.toString()+")");

        sqlAbonos.WHERE("ROWNUM < 201");

        return sqlAbonos.toString();
    }
}