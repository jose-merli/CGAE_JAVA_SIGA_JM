package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.db.mappers.FacFacturaSqlProvider;

import java.text.SimpleDateFormat;


public class FacAbonoExtendsSqlProvider extends FacFacturaSqlProvider {

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

        //forma de pago abono
        if(item.getFormaCobroAbono()!=null && (item.getFormaCobroAbono().equalsIgnoreCase("E") || item.getFormaCobroAbono().equalsIgnoreCase("A"))) {
            abonos.WHERE("f.IMPTOTALABONADOEFECTIVO > 0");
        }
        if(item.getFormaCobroAbono()!=null && (item.getFormaCobroAbono().equalsIgnoreCase("B") || item.getFormaCobroAbono().equalsIgnoreCase("A"))) {
            abonos.WHERE("f.IMPTOTALABONADOPORBANCO > 0");
        }

        //importe facturado
        if(item.getImportefacturadoDesde()!=null && !item.getImportefacturadoDesde().isEmpty()) {
            abonos.WHERE("f.IMPTOTAL>=to_number("+item.getImportefacturadoDesde()+",'99999999999999999.99')");
        }
        if(item.getImportefacturadoHasta()!=null && !item.getImportefacturadoHasta().isEmpty()) {
            abonos.WHERE("f.IMPTOTAL<=to_number("+item.getImportefacturadoHasta()+",'99999999999999999.99')");
        }

        //fecha emision
        if(item.getFechaEmisionDesde()!=null) {
            fecha = dateFormat.format(item.getFechaEmisionDesde());
            abonos.WHERE("f.fecha >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }
        if(item.getFechaEmisionHasta()!=null) {
            fecha = dateFormat.format(item.getFechaEmisionHasta());
            abonos.WHERE("f.fecha <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
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
            transferencia.FROM("fac_abonoincluidoendisquete");
            transferencia.WHERE("idinstitucion = f.idinstitucion AND IDDISQUETEABONO = "+ item.getIdentificadorTransferencia());

            abonos.WHERE("idabono IN (" + transferencia.toString() + ")");
        }

        abonos.ORDER_BY("f.numeroabono DESC");

        //query completa abonos
        sqlAbonos.SELECT("*");
        sqlAbonos.FROM("("+abonos.toString()+")");

        sqlAbonos.WHERE("ROWNUM < 201");

        return sqlAbonos.toString();
    }

    public String getAbono(String idFactura, String idInstitucion) {

        SQL query = new SQL();

        //select de abono
        query.SELECT("'ABONO' TIPO,A.IDABONO,A.IDINSTITUCION,A.NUMEROABONO,A.FECHA,A.IMPTOTAL,A.IMPTOTALNETO,"
                +"A.IMPTOTALIVA,A.IMPTOTAL IMPANULADO,A.IMPTOTALABONADOEFECTIVO,A.IMPTOTALABONADOPORBANCO,"
                +"A.IMPTOTALABONADO,A.IMPPENDIENTEPORABONAR,A.OBSERVACIONES,A.MOTIVOS,P.IDPERSONA,P.NIFCIF,P.NOMBRE,"
                +"(P.APELLIDOS1 || ' ' || NVL(P.APELLIDOS2, '')) APELLIDOS,NVL(COL.NCOLEGIADO,COL.NCOMUNITARIO) NCOLIDENT,"
                +"A.IDPERSONADEUDOR,M.ACREEDOR_ID,M.ACREEDOR_NOMBRE,FF.IMPTOTALANTICIPADO,FF.IMPTOTALCOMPENSADO,FF.IMPTOTALPAGADOPORCAJA,"
                + "FF.IMPTOTALPAGADOPORBANCO,FF.IMPTOTALPAGADO,FF.IMPTOTALPORPAGAR");

        query.FROM("FAC_ABONO A");
        query.INNER_JOIN("CEN_PERSONA P ON (P.IDPERSONA = A.IDPERSONA)");
        query.INNER_JOIN("FAC_FACTURA FF ON (FF.IDFACTURA = A.IDFACTURA AND FF.IDINSTITUCION = A.IDINSTITUCION)");
        query.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS M ON (M.IDPERSONA = A.IDPERSONADEUDOR AND M.IDINSTITUCION = A.IDINSTITUCION AND M.IDMANDATO = 1)");
        query.LEFT_OUTER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = P.IDPERSONA AND COL.IDINSTITUCION = A.IDINSTITUCION)");

        query.WHERE("A.IDABONO ="+idFactura);
        query.WHERE("A.IDINSTITUCION ="+idInstitucion);

        return query.toString();
    }
}