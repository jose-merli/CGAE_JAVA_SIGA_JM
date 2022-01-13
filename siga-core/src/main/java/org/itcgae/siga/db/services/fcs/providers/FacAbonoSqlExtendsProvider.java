package org.itcgae.siga.db.services.fcs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.db.mappers.FacAbonoSqlProvider;
import org.apache.log4j.Logger;


public class FacAbonoSqlExtendsProvider extends FacAbonoSqlProvider{
	
	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	public String buscarAbonosSJCS(FacAbonoItem facAbonoItem, String idInstitucion,String idLenguaje) {
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
		
		sql.FROM("FAC_ABONO A");
		sql.INNER_JOIN("FCS_PAGOSJG PA on A.IDPAGOSJG = PA.IDPAGOSJG AND A.idinstitucion = PA.idinstitucion");
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
        
        
		sql.WHERE("A.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("A.IDPAGOSJG IS NOT NULL");
		sql.ORDER_BY("A.NUMEROABONO DESC");
		sql.WHERE("ROWNUM <= 200");
		
		sqlTotal.SELECT("*");
        sqlTotal.FROM("("+sql.toString()+")");
        sqlTotal.WHERE("ROWNUM < 200");
        
        LOGGER.info(sqlTotal.toString());
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
}
