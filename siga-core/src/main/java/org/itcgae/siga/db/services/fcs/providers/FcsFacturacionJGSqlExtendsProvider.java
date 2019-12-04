package org.itcgae.siga.db.services.fcs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.FacturacionItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsFacturacionjgSqlProvider;

public class FcsFacturacionJGSqlExtendsProvider extends FcsFacturacionjgSqlProvider{

	public String buscarFacturaciones(FacturacionItem facturacionItem, String idInstitucion) {
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
		
		sql2.SELECT("FAC.IDINSTITUCION");
		sql2.SELECT("INS.ABREVIATURA");
		sql2.SELECT("FAC.IDFACTURACION");
		sql2.SELECT("FAC.FECHADESDE");
		sql2.SELECT("FAC.FECHAHASTA");
		sql2.SELECT("FAC.NOMBRE");
		sql2.SELECT("DECODE(FAC.REGULARIZACION, '1', 'Si', 'No') AS REGULARIZACION");
		sql2.SELECT("(SELECT F_SIGA_GETRECURSO(ESTADOS.DESCRIPCION, 1) DESCRIPCION "
		          +"FROM FCS_ESTADOSFACTURACION ESTADOS "
		          +"WHERE EST.IDESTADOFACTURACION = ESTADOS.IDESTADOFACTURACION) AS DESESTADO");
		sql2.SELECT("EST.IDESTADOFACTURACION AS IDESTADO");
		sql2.SELECT("EST.FECHAESTADO AS FECHAESTADO");
		sql2.SELECT("NVL(FAC.IMPORTETOTAL, 0) AS IMPORTETOTAL");
		sql2.SELECT("NVL(DECODE(EST.IDESTADOFACTURACION, 20, 0, "
		          +"(SELECT SUM(P.IMPORTEPAGADO) "
		          +"FROM FCS_PAGOSJG P "
		          +"WHERE P.IDFACTURACION = FAC.IDFACTURACION AND P.IDINSTITUCION = FAC.IDINSTITUCION)),0) AS IMPORTEPAGADO");
		sql2.FROM("FCS_FACTURACIONJG FAC");
		sql2.INNER_JOIN("CEN_INSTITUCION INS ON (FAC.IDINSTITUCION = INS.IDINSTITUCION)");
		sql2.INNER_JOIN("FCS_FACT_ESTADOSFACTURACION EST ON (FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION)");
		sql2.WHERE("FAC.PREVISION = '0'");
		sql2.WHERE("FAC.IDINSTITUCION = '"+idInstitucion+"'");
		
		//FILTRO ESTADOS FACTURACIÓN
		if(!UtilidadesString.esCadenaVacia(facturacionItem.getIdEstado())) {
			sql2.WHERE("EST.IDORDENESTADO =(SELECT MAX(EST2.IDORDENESTADO) "
										+"FROM FCS_FACT_ESTADOSFACTURACION EST2 " 
										+"WHERE EST2.IDINSTITUCION = EST.IDINSTITUCION AND EST2.IDFACTURACION = EST.IDFACTURACION)");
			sql2.WHERE("EST.IDESTADOFACTURACION = "+facturacionItem.getIdEstado());
		}

		//FILTRO NOMBRE
		if(!UtilidadesString.esCadenaVacia(facturacionItem.getNombre())) {
			sql2.WHERE("(REGEXP_LIKE(FAC.NOMBRE, '"+facturacionItem.getNombre()+"'))");
		}

		//FILTRO POR CONCEPTOS DE FACTURACIÓN Y POR GRUPOS DE FACTURACIÓN Y PARTIDA PRESUPUESTARIA
		if(!UtilidadesString.esCadenaVacia(facturacionItem.getIdConcepto()) || !UtilidadesString.esCadenaVacia(facturacionItem.getIdFacturacion()) 
				|| !UtilidadesString.esCadenaVacia(facturacionItem.getIdPartidaPresupuestaria())) {
			StringBuilder queryAux= new StringBuilder();
			queryAux.append("EXISTS (SELECT 1 FROM FCS_FACT_GRUPOFACT_HITO HIT WHERE HIT.IDFACTURACION = FAC.IDFACTURACION AND HIT.IDINSTITUCION = FAC.IDINSTITUCION");
			
			//FILTRO POR CONCEPTOS DE FACTURACION
			if(!UtilidadesString.esCadenaVacia(facturacionItem.getIdConcepto())) {
				queryAux.append(" AND HIT.IDHITOGENERAL = "+facturacionItem.getIdConcepto());
			}
			//FILTRO POR GRUPO FACTURACION
			if(!UtilidadesString.esCadenaVacia(facturacionItem.getIdFacturacion())) {
				queryAux.append(" AND HIT.IDGRUPOFACTURACION = "+facturacionItem.getIdFacturacion());
			}
			//FILTRO POR PARTIDA PRESUPUESTARIA
			if(!UtilidadesString.esCadenaVacia(facturacionItem.getIdPartidaPresupuestaria())) {
				queryAux.append(" AND HIT.IDPARTIDAPRESUPUESTARIA = "+facturacionItem.getIdPartidaPresupuestaria());
			}
			
			queryAux.append(")");
			sql2.WHERE(queryAux.toString());
		}	
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		//FILTRO FECHADESDE
		if(null!=facturacionItem.getFechaDesde()) {
			String fechaF = dateFormat.format(facturacionItem.getFechaDesde());
			sql2.WHERE("FAC.FECHADESDE >=TO_DATE('"+fechaF+"', 'DD/MM/YYYY hh24:mi:ss')");
		}
	
		//FILTRO FECHAHASTA
		if(null!=facturacionItem.getFechaHasta()) {
			String fechaF = dateFormat.format(facturacionItem.getFechaHasta());
			sql2.WHERE("FAC.FECHAHASTA <=TO_DATE('"+fechaF+"', 'DD/MM/YYYY hh24:mi:ss')");
		}
		
		sql.FROM("("+sql2.toString()+") busqueda");
		sql.ORDER_BY("busqueda.NOMBRE");
		sql.ORDER_BY("busqueda.FECHADESDE DESC");
		
		return sql.toString();
	}
}

