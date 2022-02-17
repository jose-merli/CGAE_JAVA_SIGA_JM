package org.itcgae.siga.db.services.fcs.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.db.mappers.FacAbonoSqlProvider;


public class FacAbonoSqlExtendsProvider extends FacAbonoSqlProvider{
	
	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	public String buscarAbonosSJCS(FacAbonoItem facAbonoItem,String idsGrupo, String idInstitucion,String idLenguaje) {
		SQL sql = new SQL();
		SQL sqlTotal = new SQL();
		SQL transferencia = new SQL();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		  
		sql.SELECT("A.IDABONO, A.NUMEROABONO,A.FECHA,A.IDPERSONA,A.IMPTOTALABONADO, A.IMPPENDIENTEPORABONAR,A.ESTADO");
		sql.SELECT("nvl(nvl(col.ncolegiado,col.ncomunitario),p.nifcif) ncolident,nvl(p.apellidos1 || ' ' || nvl(p.apellidos2, '') || ', ' || p.nombre, p.nombre) nombreCompleto");
		sql.SELECT("P.NOMBRE as NOMBREGENERAL ,P.APELLIDOS1 || ' ' || NVL(P.APELLIDOS2, '') AS APELLIDOSGENERAL");
		sql.SELECT("A.IMPTOTAL, A.IMPTOTALABONADOEFECTIVO, A.IMPTOTALABONADOPORBANCO, A.IMPTOTALNETO");
		sql.SELECT("CASE WHEN A.IDPERSONA = A.IDPERORIGEN THEN 'NO' ELSE 'SI' END AS SOCIEDAD");
		sql.SELECT("PA.nombre AS NOMBREPAGO");
		sql.SELECT("GEN.DESCRIPCION AS ESTADONOMBRE");
		sql.SELECT("PA.IDPAGOSJG, PA.IDFACTURACION, A.IDFACTURA");
		sql.SELECT("F.NOMBRE as NOMBREFACTURACION, FL.PRECIOUNITARIO AS IRPF");
		
		sql.FROM("FAC_ABONO A");
		sql.INNER_JOIN("FAC_LINEAABONO FL ON (A.IDABONO = FL.IDABONO AND FL.IDINSTITUCION = A.IDINSTITUCION)");
		sql.INNER_JOIN("FCS_PAGOSJG PA on A.IDPAGOSJG = PA.IDPAGOSJG AND A.idinstitucion = PA.idinstitucion");
		sql.INNER_JOIN("FCS_FACTURACIONJG  F ON (PA.IDFACTURACION = F.IDFACTURACION AND PA.IDINSTITUCION = F.IDINSTITUCION) ");
		sql.INNER_JOIN("CEN_CLIENTE C ON (C.IDPERSONA = A.IDPERSONA AND C.IDINSTITUCION = A.IDINSTITUCION)");
		sql.INNER_JOIN("CEN_PERSONA P ON (P.IDPERSONA = A.IDPERSONA)");
		sql.LEFT_OUTER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = P.IDPERSONA AND COL.IDINSTITUCION = A.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN("FAC_ESTADOABONO EA ON (EA.IDESTADO = A.ESTADO)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS GEN ON (EA.DESCRIPCION = GEN.IDRECURSO  AND GEN.IDLENGUAJE = "+ idLenguaje +")");
		
		if(facAbonoItem.getNumeroAbono() != null) sql.WHERE("A.NUMEROABONO LIKE '%" + facAbonoItem.getNumeroAbono() + "%'");
		
		if(facAbonoItem.getIdPersona() != null ) sql.WHERE("A.IDPERSONA LIKE '%" +facAbonoItem.getIdPersona() + "%'");
		
		if(facAbonoItem.getEstado() != 0 ) sql.WHERE("A.ESTADO = " + facAbonoItem.getEstado());
		
		if(facAbonoItem.getForma()!=null && (facAbonoItem.getForma().equalsIgnoreCase("E") || facAbonoItem.getForma().equalsIgnoreCase("A"))) {
			sql.WHERE("A.IMPTOTALABONADOEFECTIVO > 0");
	    }
	    if(facAbonoItem.getForma()!=null && (facAbonoItem.getForma().equalsIgnoreCase("B") || facAbonoItem.getForma().equalsIgnoreCase("A"))) {
	        	sql.WHERE("A.IMPTOTALABONADOPORBANCO > 0");
	    }
	        
	    if(facAbonoItem.getNumColegiado() != null ) sql.WHERE("COL.NCOLEGIADO = " + facAbonoItem.getNumColegiado());
	    
	    if(facAbonoItem.getGrupoPago() != null) sql.WHERE("PA.IDFACTURACION = " + facAbonoItem.getGrupoPago());

        if(facAbonoItem.getImporteTotalDesde() != 0 ) {
        	sql.WHERE("A.IMPTOTAL>=to_number("+facAbonoItem.getImporteTotalDesde()+",'99999999999999999.99')");
        }
        if(facAbonoItem.getImporteTOtalHasta() != 0) {
        	sql.WHERE("A.IMPTOTAL<=to_number("+facAbonoItem.getImporteTOtalHasta()+",'99999999999999999.99')");
        }
	
        if(facAbonoItem.getFechaEmisionDesde()!=null) {
            String fecha = dateFormat.format(facAbonoItem.getFechaEmisionDesde());
            sql.WHERE("A.fecha >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }
        if(facAbonoItem.getFechaEmisionHasta()!=null) {
            String fecha = dateFormat.format(facAbonoItem.getFechaEmisionHasta());
            sql.WHERE("A.fecha <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
        }
        
        if(facAbonoItem.getContabilizada()!=null && facAbonoItem.getContabilizada().equalsIgnoreCase("S")) {
        	sql.WHERE("A.contabilizada = 'S'");
        }
        if(facAbonoItem.getContabilizada()!=null && facAbonoItem.getContabilizada().equalsIgnoreCase("N")) {
        	sql.WHERE("A.contabilizada = 'N'");
        }

        if(facAbonoItem.getNumIdentificadorSociedad() != null ) {
        	sql.WHERE("UPPER(P.nifcif) LIKE UPPER('%"+facAbonoItem.getNumIdentificadorSociedad()+"%')");
        }
        if(facAbonoItem.getIdentificadorFicheroT()!=null){
            transferencia.SELECT("IDABONO");
            transferencia.FROM("fac_abonoincluidoendisquete");
            transferencia.WHERE("idinstitucion = f.idinstitucion AND IDDISQUETEABONO = "+ facAbonoItem.getIdentificadorFicheroT());

            sql.WHERE("A.IDABONO IN (" + transferencia.toString() + ")");
        }
        
        if(facAbonoItem.getNombreSociedad() != null) sql.WHERE("UPPER(P.NOMBRE) LIKE UPPER('%"+facAbonoItem.getNombreSociedad() + "%')");
        
        if(facAbonoItem.getIdInstitucion() != null ) sql.WHERE("COL.IDINSTITUCION = " + facAbonoItem.getIdInstitucion());
        
        if(idsGrupo != "") {
        	sql.WHERE("F.IDFACTURACION in ("+idsGrupo+")");
        }
        
		sql.WHERE("A.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("FL.DESCRIPCIONLINEA = 'IRPF'");
		sql.WHERE("A.IDPAGOSJG IS NOT NULL");
		sql.ORDER_BY("A.NUMEROABONO DESC");
		sql.WHERE("ROWNUM <= 200");
		
		sqlTotal.SELECT("*");
        sqlTotal.FROM("("+sql.toString()+")");
        sqlTotal.WHERE("ROWNUM < 200");
        
        LOGGER.info(sqlTotal.toString());
		return sql.toString();
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

    public String getAbonoAnterior(Short idInstitucion, Date fecha) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("FECHA < TO_DATE(" + fecha + ", 'YYYY-MM-DD HH:MI:SS')");
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

	public String comboEstadosAbono(String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("A.IDESTADO, R.DESCRIPCION");
		sql.FROM("FAC_ESTADOABONO A, GEN_RECURSOS R ");
		sql.WHERE("A.DESCRIPCION = R.IDRECURSO");
		sql.WHERE("R.IDLENGUAJE = " + idLenguaje);
		
		sql.ORDER_BY("R.DESCRIPCION");
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String comboGrupoFacturacion(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("IDGRUPOFACTURACION, F_SIGA_GETRECURSO(NOMBRE,"+idLenguaje+") AS NOMBRE");
		sql.FROM("SCS_GRUPOFACTURACION");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);		
		sql.ORDER_BY("NOMBRE");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String comboPago(String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("IDFACTURACION, ABREVIATURA ");
		sql.FROM("fcs_pagosjg");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);		
		sql.ORDER_BY("ABREVIATURA");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String facturacionByGroup(String idGrupo, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT(" LISTAGG(idfacturacion,',') IDS");
		
		sql.FROM("FCS_FACT_GRUPOFACT_HITO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);	
		sql.WHERE("idgrupofacturacion = " + idGrupo);
		
		LOGGER.info(sql.toString());
		return sql.toString();
		
	}
}
