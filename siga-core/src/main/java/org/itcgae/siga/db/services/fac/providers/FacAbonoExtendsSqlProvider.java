package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FacFacturaSqlProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class FacAbonoExtendsSqlProvider extends FacFacturaSqlProvider {

    public String getAbonos(FacturaItem item, String idInstitucion, String idLenguaje, Integer maxRows) {

        SQL transferencia = new SQL();
        SQL abonos = new SQL();
        SQL sqlAbonos = new SQL();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        //select de abonos
        abonos.SELECT("'ABONO' tipo,f.idabono,f.numeroabono,f.idinstitucion,f.fecha fecha,"
                + "nvl(nvl(col.ncolegiado,col.ncomunitario),p.nifcif) ncolident,nvl(p.apellidos1 || ' ' || nvl(p.apellidos2, '') || ', ' || p.nombre, p.nombre) nombreCompleto,"
                + "f.imptotal imptotal,f.imppendienteporabonar imptotalporpagar,f.estado idestado,r.descripcion estado,p.idpersona");

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
        if (item.getEstadosFiltroAb()!=null && !item.getEstadosFiltroAb().isEmpty()) {
            StringBuilder aux = new StringBuilder();
            for (String s : item.getEstadosFiltroAb()) {
                aux.append(s).append(",");
            }
            aux.deleteCharAt(aux.length()-1);
            abonos.WHERE("f.estado in (" + aux + ")");
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

        if(maxRows == null || maxRows == 0 || maxRows > 201){
            sqlAbonos.WHERE("ROWNUM < 201");
        }
        else {
            sqlAbonos.WHERE("ROWNUM < " + (201 - maxRows));
        }

        return sqlAbonos.toString();
    }

    public String getAbono(String idFactura, String idInstitucion) {

        SQL query = new SQL();

        //select de abono
        query.SELECT("'ABONO' TIPO,A.IDABONO,A.IDFACTURA,A.IDINSTITUCION,A.NUMEROABONO,A.FECHA,A.IMPTOTAL,A.IMPTOTALNETO,A.ESTADO IDESTADO,"
                +"A.IMPTOTALIVA,A.IMPTOTAL IMPANULADO,A.IMPTOTALABONADOEFECTIVO,A.IMPTOTALABONADOPORBANCO,"
                +"A.IMPTOTALABONADO,A.IMPPENDIENTEPORABONAR,A.OBSERVACIONES,A.MOTIVOS,P.IDPERSONA,P.NIFCIF,P.NOMBRE,"
                +"(P.APELLIDOS1 || ' ' || NVL(P.APELLIDOS2, '')) APELLIDOS,NVL(COL.NCOLEGIADO,COL.NCOMUNITARIO) NCOLIDENT,"
                +"A.IDPERSONADEUDOR,M.ACREEDOR_ID,M.ACREEDOR_NOMBRE,FF.IMPTOTALANTICIPADO,FF.IMPTOTALCOMPENSADO,FF.IMPTOTALPAGADOPORCAJA,"
                + "FF.IMPTOTALPAGADOPORBANCO,FF.IMPTOTALPAGADO,FF.IMPTOTALPORPAGAR");

        query.FROM("FAC_ABONO A");
        query.INNER_JOIN("CEN_PERSONA P ON (P.IDPERSONA = A.IDPERSONA)");
        query.LEFT_OUTER_JOIN("FAC_FACTURA FF ON (FF.IDFACTURA = A.IDFACTURA AND FF.IDINSTITUCION = A.IDINSTITUCION)");
        query.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS M ON (M.IDPERSONA = A.IDPERSONADEUDOR AND M.IDINSTITUCION = A.IDINSTITUCION AND M.IDMANDATO = 1)");
        query.LEFT_OUTER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = P.IDPERSONA AND COL.IDINSTITUCION = A.IDINSTITUCION)");

        query.WHERE("A.IDABONO ="+idFactura);
        query.WHERE("A.IDINSTITUCION ="+idInstitucion);

        return query.toString();
    }

    public String getNewAbonoID(String idInstitucion) {

        SQL query = new SQL();

        query.SELECT("MAX(IDABONO)+1 AS ID");
        query.FROM("FAC_ABONO fa");
        query.WHERE("IDINSTITUCION ="+idInstitucion);

        return query.toString();
    }
    
    public String getNuevoID(String idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(IDABONO) + 1, 1)) AS IDABONO");
        sql.FROM("FAC_ABONO");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }

    public String getIdAbonosPorPago(Short idInstitucion, Integer idPagosjg) {

        SQL sql = new SQL();
        sql.SELECT("IDABONO");
        sql.FROM("FAC_ABONO");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPAGOSJG = " + idPagosjg);

        return sql.toString();
    }

    public String restableceValoresAbono(Short idInstitucion, Long idDisqueteAbono) {

        SQL sql2 = new SQL();
        sql2.SELECT("ABODIS.IMPORTEABONADO");
        sql2.FROM("FAC_ABONOINCLUIDOENDISQUETE ABODIS");
        sql2.WHERE("ABODIS.IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("ABODIS.IDDISQUETEABONO = " + idDisqueteAbono);
        sql2.WHERE("ABODIS.IDABONO = FAC_ABONO.IDABONO");

        SQL sql3 = new SQL();
        sql3.SELECT("IDABONO");
        sql3.FROM("FAC_ABONOINCLUIDOENDISQUETE");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDDISQUETEABONO = " + idDisqueteAbono);

        SQL sql = new SQL();
        sql.UPDATE("FAC_ABONO");
        sql.SET("IMPTOTALABONADOPORBANCO = IMPTOTALABONADOPORBANCO - (" + sql2.toString() + ")");
        sql.SET("IMPTOTALABONADO = IMPTOTALABONADO - (" + sql2.toString() + ")");
        sql.SET("IMPPENDIENTEPORABONAR = IMPPENDIENTEPORABONAR + (" + sql2.toString() + ")");
        sql.SET("ESTADO = 5");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDABONO IN (" + sql3.toString() + ")");

        return sql.toString();
    }

    public String hayAbonoPosterior(Short idInstitucion, Integer idPago) {

        SQL subQuery3 = new SQL();
        subQuery3.SELECT("MAX(FECHA)");
        subQuery3.FROM("FAC_ABONO");
        subQuery3.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery3.WHERE("IDPAGOSJG = " + idPago);

        SQL subQuery2 = new SQL();
        subQuery2.SELECT("IDABONO");
        subQuery2.FROM("FAC_ABONO");
        subQuery2.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery2.WHERE("IDPAGOSJG = " + idPago);
        subQuery2.WHERE("FECHA = ( " + subQuery3.toString() + " )");

        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(IDABONO)");
        subQuery.FROM("( " + subQuery2.toString() + " )");

        SQL query = new SQL();
        query.SELECT("*");
        query.FROM("FAC_ABONO");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO > ( " + subQuery.toString() + " )");
        query.WHERE("ROWNUM = 1");

        return query.toString();
    }

    public String getAbonoAnterior(Short idInstitucion, String fecha) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("FECHA < TO_DATE('" + fecha + "', 'YYYY-MM-DD HH:MI:SS')");
        subQuery.ORDER_BY("IDABONO DESC");

        SQL query = new SQL();
        query.SELECT("IDABONO");
        query.FROM("( " + subQuery.toString() + " )");
        query.WHERE("ROWNUM = 1");

        return query.toString();
    }
    
    public String getPagosCerrados(Short idInstitucion, String anio) {
    	
    	SQL sql = new SQL();
    	
    	sql.SELECT_DISTINCT("IDPAGOSJG");
    	sql.FROM("FAC_ABONO");
    	sql.WHERE("IDINSTITUCION = " + idInstitucion);
    	sql.WHERE("to_char(FECHA, 'YYYY') = "+ anio);
    	sql.WHERE("IDPAGOSJG is not null");
    	
    	return sql.toString();
    }

    public String getNuevoNumeroAbono(String idInstitucion, String idContador){

        SQL sql = new SQL();

        // Select
        sql.SELECT("ac.PREFIJO || LPAD(ac.CONTADOR + 1, ac.LONGITUDCONTADOR, '0') || ac.SUFIJO AS NUEVOCONTADOR");

        // From
        sql.FROM("ADM_CONTADOR ac");

        // Where
        sql.WHERE("ac.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("ac.IDCONTADOR = '" + idContador + "'");

        return sql.toString();
    }

    public String getAbonosBanco(Short idInstitucion, String bancosCodigo, Short idSufijo, List<String> idAbonos) {
        SQL sql = new SQL();

        sql.SELECT("abono.*");

        sql.FROM("FAC_ABONO abono");
        sql.LEFT_OUTER_JOIN("FAC_FACTURA factura ON (factura.IDINSTITUCION = abono.IDINSTITUCION AND factura.IDFACTURA = abono.IDFACTURA)");
        sql.LEFT_OUTER_JOIN("FAC_SERIEFACTURACION_BANCO banco ON (factura.IDINSTITUCION = banco.IDINSTITUCION AND factura.IDSERIEFACTURACION = banco.IDSERIEFACTURACION)");

        sql.WHERE("abono.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("banco.BANCOS_CODIGO = " + bancosCodigo);
        sql.WHERE("banco.IDSUFIJO = " + idSufijo);
        sql.WHERE("F_SIGA_ESTADOSABONO(abono.IDINSTITUCION, abono.IDABONO) = " + SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
        sql.WHERE("abono.IDPAGOSJG IS NULL");

        if (idAbonos != null && !idAbonos.isEmpty())
            sql.WHERE("abono.IDABONO IN (" + String.join(",",
                    idAbonos.stream().filter(id -> !UtilidadesString.esCadenaVacia(id))
                            .map(id -> "'" + id + "'")
                            .collect(Collectors.toList())) + ")");

        return sql.toString();
    }

    public String getAbonosBancoSjcs(Short idInstitucion, String bancosCodigo, Short idSufijo, List<String> idAbonos) {
        SQL sql = new SQL();

        sql.SELECT("abono.*");

        sql.FROM("FAC_ABONO abono");
        sql.LEFT_OUTER_JOIN("FAC_FACTURA factura ON (factura.IDINSTITUCION = abono.IDINSTITUCION AND factura.IDFACTURA = abono.IDFACTURA)");
        sql.LEFT_OUTER_JOIN("FAC_SERIEFACTURACION_BANCO banco ON (factura.IDINSTITUCION = banco.IDINSTITUCION AND factura.IDSERIEFACTURACION = banco.IDSERIEFACTURACION)");
        sql.LEFT_OUTER_JOIN("FCS_PAGOSJG pago ON (abono.IDINSTITUCION = pago.IDINSTITUCION AND abono.IDPAGOSJG = pago.IDPAGOSJG)");

        sql.WHERE("abono.IDINSTITUCION = " + idInstitucion);

        if (idAbonos != null && !idAbonos.isEmpty())
            sql.WHERE("abono.IDABONO IN (" + String.join(",",
                    idAbonos.stream().filter(id -> !UtilidadesString.esCadenaVacia(id))
                            .map(id -> "'" + id + "'")
                            .collect(Collectors.toList())) + ")");

        sql.WHERE("abono.IDPAGOSJG IS NOT NULL");
        sql.WHERE("pago.BANCOS_CODIGO = " + bancosCodigo);
        sql.WHERE("pago.IDSUFIJO = " + idSufijo);
        sql.WHERE("F_SIGA_ESTADOSABONO(abono.IDINSTITUCION, abono.IDABONO) = " + SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
        sql.WHERE("abono.IMPPENDIENTEPORABONAR <> 0.0");

        return sql.toString();
    }

    public String getBancosSufijosSjcs(Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT_DISTINCT("pago.BANCOS_CODIGO, pago.IDSUFIJO, pago.IDPROPSEPA, pago.IDPROPOTROS");

        sql.FROM("FAC_ABONO abono");
        sql.LEFT_OUTER_JOIN("FCS_PAGOSJG pago ON (abono.IDINSTITUCION = pago.IDINSTITUCION AND abono.IDPAGOSJG = pago.IDPAGOSJG)");
        sql.LEFT_OUTER_JOIN("FAC_BANCOINSTITUCION banco ON (abono.IDINSTITUCION = banco.IDINSTITUCION AND pago.BANCOS_CODIGO = banco.BANCOS_CODIGO)");


        sql.WHERE("abono.IDINSTITUCION = " + idInstitucion);

        sql.WHERE("abono.IDPAGOSJG IS NOT NULL");
        sql.WHERE("F_SIGA_ESTADOSABONO(abono.IDINSTITUCION, abono.IDABONO) = " + SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
        sql.WHERE("abono.IMPPENDIENTEPORABONAR <> 0.0");

        return sql.toString();
    }
}