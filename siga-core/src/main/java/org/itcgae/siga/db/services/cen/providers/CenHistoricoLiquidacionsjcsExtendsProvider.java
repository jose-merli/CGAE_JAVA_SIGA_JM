package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosLiquidacionIntegrantesSearchItem;
import org.itcgae.siga.db.mappers.CenHistoricoLiquidacionsjcsSqlProvider;

public class CenHistoricoLiquidacionsjcsExtendsProvider extends CenHistoricoLiquidacionsjcsSqlProvider{

   public String selectLiquidacion (DatosLiquidacionIntegrantesSearchItem datosLiquidacion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("HISTO.FECHAINICIO AS FECHAINICIO");
		sql.SELECT("HISTO.SOCIEDAD AS SOCIEDAD"); //MODIFICADO --> ESTABA COMO COM.SOCIEDAD SOCIEDAD
		sql.SELECT("HISTO.OBSERVACIONES AS OBSERVACIONES");
		sql.SELECT("HISTO.IDINSTITUCION AS IDINSTITUCION");
		sql.SELECT("HISTO.IDPERSONA AS IDPERSONA");
		sql.SELECT("HISTO.IDCOMPONENTE AS IDCOMPONENTE");
		sql.SELECT("HISTO.IDHISTORICO AS IDHISTORICO");
		sql.SELECT("HISTO.FECHAFIN AS FECHAFIN");
		sql.SELECT("HISTO.FECHAMODIFICACION AS FECHAMODIFICACION");
		sql.SELECT("HISTO.USUMODIFICACION AS USUMODIFICACION");
		sql.FROM("CEN_HISTORICO_LIQUIDACIONSJCS HISTO");
		sql.JOIN("CEN_COMPONENTES COM ON HISTO.IDINSTITUCION = COM.IDINSTITUCION AND HISTO.IDPERSONA = COM.IDPERSONA AND HISTO.IDPERSONA = COM.IDPERSONA AND HISTO.IDCOMPONENTE = COM.IDCOMPONENTE");
		sql.WHERE("HISTO.IDINSTITUCION = "+datosLiquidacion.getIdInstitucion());
		sql.WHERE("HISTO.IDPERSONA = "+datosLiquidacion.getIdPersona());
		sql.WHERE("HISTO.IDCOMPONENTE = "+datosLiquidacion.getIdComponente());
		sql.ORDER_BY("FECHAINICIO DESC");
		return sql.toString();
	}
   
   public String buscarPagosColegiados(DatosLiquidacionIntegrantesSearchItem datosLiquidacion) {
	   
	   SQL sql = new SQL();
	   
	   sql.SELECT("COUNT(colpagos.IDPAGOSJG) AS NUMPAGOS");
	   sql.FROM("FCS_PAGO_COLEGIADO colpagos");
	   sql.INNER_JOIN("FCS_PAGOS_ESTADOSPAGOS pagos ON colpagos.IDPAGOSJG = pagos.IDPAGOSJG AND colpagos.IDINSTITUCION = pagos.IDINSTITUCION");
	   sql.WHERE("(colpagos.IDPERORIGEN = '"+datosLiquidacion.getIdPersona()+"' OR IDPERDESTINO = '"+datosLiquidacion.getIdPersona()+"')");
	   sql.WHERE("pagos.FECHAESTADO >= TO_DATE('"+datosLiquidacion.getFechaInicio()+"')");
	   sql.WHERE("colpagos.IDINSTITUCION = '"+datosLiquidacion.getIdInstitucion()+"'");
	   sql.WHERE("pagos.IDESTADOPAGOSJG = 20");
	   
	   return sql.toString();
   }
}
