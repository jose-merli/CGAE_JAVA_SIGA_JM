package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.db.mappers.CenComponentesSqlProvider;

public class CenComponentesSqlExtendsProvider extends CenComponentesSqlProvider{

	
	public String selectIntegrantes(DatosIntegrantesSearchDTO integrantesSearchDTO) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("COMPONENTE.IDINSTITUCION");
		sql.SELECT("COMPONENTE.IDPERSONA");
		sql.SELECT("COMPONENTE.IDCOMPONENTE");
		sql.SELECT("COMPONENTE.CARGO");
		sql.SELECT("TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') AS FECHACARGO");
		sql.SELECT("COMPONENTE.FECHABAJA AS FECHABAJACARGO");
		sql.SELECT("COMPONENTE.CEN_CLIENTE_IDPERSONA AS IDPERSONACOMPONENTE");
		sql.SELECT("DECODE(COMPONENTE.SOCIEDAD,1,'SI','NO') AS SOCIEDAD");
		sql.SELECT("COMPONENTE.CAPITALSOCIAL");
		sql.SELECT("TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') AS FECHACARGOINFORME");
		sql.SELECT("DECODE(f_siga_gettipocliente(COMPONENTE.CEN_CLIENTE_IDPERSONA,COMPONENTE.CEN_CLIENTE_IDINSTITUCION,SYSDATE),20,'Ejerciente','') AS EJERCIENTE");
		sql.SELECT("DECODE(COMPONENTE.FECHABAJA, NULL,TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy'),TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') || ' -(' || TO_CHAR(COMPONENTE.FECHABAJA, 'dd/mm/yyyy') || ') ' ) AS FECHA_HISTORICO");
		sql.SELECT("PERSONA.NIFCIF");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT("PERSONA.APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("CONCAT(PERSONA.NOMBRE ||' ',CONCAT(PERSONA.APELLIDOS1 || ' ',PERSONA.APELLIDOS2)) AS NOMBRECOMPLETO");
		sql.SELECT("COMPONENTE.IDTIPOCOLEGIO");
		sql.SELECT("COMPONENTE.NUMCOLEGIADO");
		sql.SELECT("COMPONENTE.IDCARGO");
		sql.SELECT("COMPONENTE.IDPROVINCIA");
		sql.FROM("CEN_COMPONENTES COMPONENTE");
		sql.INNER_JOIN(" CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = COMPONENTE.CEN_CLIENTE_IDPERSONA ");
		sql.WHERE("COMPONENTE.IDPERSONA = '"+integrantesSearchDTO.getIdPersona()+"'");
		if (!integrantesSearchDTO.getHistorico()) {
			sql.WHERE("COMPONENTE.FECHABAJA is null");
		}
		sql.ORDER_BY("COMPONENTE.IDCOMPONENTE");
		return sql.toString();
	}
	
}
