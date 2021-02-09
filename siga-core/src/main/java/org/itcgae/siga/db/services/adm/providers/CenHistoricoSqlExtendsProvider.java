package org.itcgae.siga.db.services.adm.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;
import org.itcgae.siga.commons.constants.SigaConstants;

public class CenHistoricoSqlExtendsProvider {

	public String auditUsersSearch(int numPagina, HistoricoUsuarioRequestDTO historicoUsuarioRequestDTO) {
		SQL sql = new SQL();
		
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		
		sql.SELECT(" DISTINCT HIST.IDPERSONA");
		sql.SELECT("HIST.IDHISTORICO");
		sql.SELECT("HIST.FECHAEFECTIVA");
		sql.SELECT("HIST.FECHAENTRADA");
		sql.SELECT("HIST.MOTIVO");
		sql.SELECT("HIST.DESCRIPCION");
		sql.SELECT("TIPO.IDTIPOCAMBIO");
		sql.SELECT("CAT.DESCRIPCION AS DESCRIPCIONTIPOCAMBIO");
		sql.SELECT("PER.NOMBRE");
		sql.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS APELLIDOS1");
		sql.SELECT("PER.APELLIDOS2");
		sql.SELECT("CONCAT (CONCAT ( CONCAT (PER.NOMBRE,' '), CONCAT(DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1),' ')), PER.APELLIDOS2) AS PERSONA");
		sql.SELECT("USU.DESCRIPCION AS DESCRIPCIONUSUARIO ");
		sql.FROM(" CEN_HISTORICO  HIST ");
		sql.INNER_JOIN(" CEN_PERSONA PER ON HIST.IDPERSONA = PER.IDPERSONA ");
		sql.INNER_JOIN(" CEN_TIPOCAMBIO TIPO ON TIPO.IDTIPOCAMBIO = HIST.IDTIPOCAMBIO ");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS CAT ON  CAT.IDRECURSO = TIPO.DESCRIPCION ");
		sql.INNER_JOIN(" ADM_USUARIOS USU ON HIST.USUMODIFICACION = USU.IDUSUARIO AND HIST.IDINSTITUCION = USU.IDINSTITUCION ");

		// campos que pueden ser opcionales
		if(null != historicoUsuarioRequestDTO.getIdTipoAccion() && !historicoUsuarioRequestDTO.getIdTipoAccion().equalsIgnoreCase("")){
			sql.WHERE(" TIPO.IDTIPOCAMBIO = '"+ historicoUsuarioRequestDTO.getIdTipoAccion() +"'");
		}
		// campos que pueden ser opcionales
		if(null != historicoUsuarioRequestDTO.getIdPersonaReal() && !historicoUsuarioRequestDTO.getIdPersonaReal().equalsIgnoreCase("")){
			sql.WHERE(" HIST.IDPERSONA = '"+ historicoUsuarioRequestDTO.getIdPersonaReal() +"'");
		}
		if(null != historicoUsuarioRequestDTO.getIdPersona() && !historicoUsuarioRequestDTO.getIdPersona().equalsIgnoreCase("")) {
			sql.WHERE("(Concat(upper(per.nombre || ' '),Concat(upper(per.apellidos1 || ' '), upper(per.apellidos2 || ' '))) LIKE UPPER('%"+historicoUsuarioRequestDTO.getIdPersona() +"%'))");
			
		}
		if(null != historicoUsuarioRequestDTO.getUsuario() && !historicoUsuarioRequestDTO.getUsuario().equalsIgnoreCase("")) {
			sql.WHERE("(UPPER(USU.DESCRIPCION) LIKE UPPER('%"+ historicoUsuarioRequestDTO.getUsuario()+"%'))");
		}
		if(null != historicoUsuarioRequestDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(historicoUsuarioRequestDTO.getFechaDesde());
			sql.WHERE(" HIST.FECHAEFECTIVA >= TO_DATE('" +fechaDesde + "', 'DD/MM/YYYY') ");
		}
		if(null != historicoUsuarioRequestDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(historicoUsuarioRequestDTO.getFechaHasta());
			sql.WHERE(" HIST.FECHAEFECTIVA < TO_DATE('" +fechaHasta + "', 'DD/MM/YYYY') + 1");
		}
		if(null != historicoUsuarioRequestDTO.getUsuarioAutomatico() && historicoUsuarioRequestDTO.getUsuarioAutomatico().equals("S")) {
			sql.WHERE(" HIST.USUMODIFICACION = '0'");
		}
		
	
		// campos que siempre seran obligatorios
		sql.WHERE(" HIST.IDINSTITUCION = '"+ historicoUsuarioRequestDTO.getIdInstitucion() +"'");
		if(null != historicoUsuarioRequestDTO.getIdInstitucion() && historicoUsuarioRequestDTO.getIdInstitucion().equals(SigaConstants.COMBO_INSTITUCIONES)) {
			sql.WHERE(" USU.IDINSTITUCION = '"+ historicoUsuarioRequestDTO.getIdInstitucion() +"'");
		}
		sql.WHERE(" CAT.IDLENGUAJE = '"+ historicoUsuarioRequestDTO.getIdLenguaje() +"' ");
		sql.ORDER_BY(" HIST.IDHISTORICO ");
		return sql.toString();
	}
	
	
	public String selectMaxIDHistoricoByPerson(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("nvl(MAX(IDHISTORICO)+1,1) AS IDHISTORICO");
		sql.FROM("CEN_HISTORICO");
		sql.WHERE("IDPERSONA = '" + idPersona + "'" );
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'" );
		return sql.toString();
	}
}
