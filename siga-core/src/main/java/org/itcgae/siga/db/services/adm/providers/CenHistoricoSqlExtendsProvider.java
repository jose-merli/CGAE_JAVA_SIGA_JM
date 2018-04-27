package org.itcgae.siga.db.services.adm.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;

public class CenHistoricoSqlExtendsProvider {

	public String auditUsersSearch(int numPagina, HistoricoUsuarioRequestDTO historicoUsuarioRequestDTO) {
		SQL sql = new SQL();
		
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		
		sql.SELECT("HIST.IDPERSONA");
		sql.SELECT("HIST.IDHISTORICO");
		sql.SELECT("HIST.FECHAEFECTIVA");
		sql.SELECT("HIST.FECHAENTRADA");
		sql.SELECT("HIST.MOTIVO");
		sql.SELECT("HIST.DESCRIPCION");
		sql.SELECT("TIPO.IDTIPOCAMBIO");
		sql.SELECT("CAT.DESCRIPCION AS DESCRIPCIONTIPOCAMBIO");
		sql.SELECT("PER.NOMBRE");
		sql.SELECT("PER.APELLIDOS1");
		sql.SELECT("PER.APELLIDOS2");
		sql.SELECT("CONCAT (CONCAT ( CONCAT (PER.NOMBRE,' '), CONCAT(PER.APELLIDOS1,' ')), PER.APELLIDOS2) AS PERSONA");
		sql.SELECT("USU.DESCRIPCION AS DESCRIPCIONUSUARIO ");
		sql.FROM(" CEN_HISTORICO  HIST ");
		sql.INNER_JOIN(" CEN_PERSONA PER ON HIST.IDPERSONA = PER.IDPERSONA ");
		sql.INNER_JOIN(" CEN_TIPOCAMBIO TIPO ON TIPO.IDTIPOCAMBIO = HIST.IDTIPOCAMBIO ");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS CAT ON  CAT.IDRECURSO = TIPO.DESCRIPCION ");
		sql.INNER_JOIN(" ADM_USUARIOS USU ON HIST.USUMODIFICACION = USU.IDUSUARIO ");
		
		// campos que pueden ser opcionales
		if(null != historicoUsuarioRequestDTO.getIdTipoAccion() && !historicoUsuarioRequestDTO.getIdTipoAccion().equalsIgnoreCase("")){
			sql.WHERE(" TIPO.IDTIPOCAMBIO = '"+ historicoUsuarioRequestDTO.getIdTipoAccion() +"'");
		}
		if(null != historicoUsuarioRequestDTO.getIdPersona() && !historicoUsuarioRequestDTO.getIdPersona().equalsIgnoreCase("")) {
			sql.WHERE("(UPPER(PER.NOMBRE) LIKE UPPER('%" + historicoUsuarioRequestDTO.getIdPersona() +"%') OR UPPER(PER.APELLIDOS1) LIKE UPPER('%" + historicoUsuarioRequestDTO.getIdPersona() +"%') OR UPPER(PER.APELLIDOS2) LIKE UPPER('%"+historicoUsuarioRequestDTO.getIdPersona() +"%'))");
			
		}
		if(null != historicoUsuarioRequestDTO.getUsuario() && !historicoUsuarioRequestDTO.getUsuario().equalsIgnoreCase("")) {
			sql.WHERE("(UPPER(USU.DESCRIPCION) LIKE '%"+ historicoUsuarioRequestDTO.getUsuario()+"%')");
		}
		if(null != historicoUsuarioRequestDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(historicoUsuarioRequestDTO.getFechaDesde());
			sql.WHERE("HIST.FECHAEFECTIVA >= '" +fechaDesde + "'");
		}
		if(null != historicoUsuarioRequestDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(historicoUsuarioRequestDTO.getFechaHasta());
			sql.WHERE("HIST.FECHAEFECTIVA <= '" + fechaHasta + "'");
		}
		
		// campos que siempre seran obligatorios
		sql.WHERE(" HIST.IDINSTITUCION = '"+ historicoUsuarioRequestDTO.getIdInstitucion() +"'");
		sql.WHERE(" USU.IDINSTITUCION = '"+ historicoUsuarioRequestDTO.getIdInstitucion() +"'");
		sql.WHERE(" CAT.IDLENGUAJE = '"+ historicoUsuarioRequestDTO.getIdLenguaje() +"'");
		//sql.WHERE("HIST.FECHAEFECTIVA between " + historicoUsuarioRequestDTO.getFechaDesde() + " and " + historicoUsuarioRequestDTO.getFechaHasta());
		
		
		
		return sql.toString();
	}
}
