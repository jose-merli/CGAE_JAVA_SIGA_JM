package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
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
		sql.SELECT("DECODE(PERSONA.APELLIDOS1,'#NA','',PERSONA.APELLIDOS1) AS APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("CONCAT(PERSONA.APELLIDOS1 || ' ',PERSONA.APELLIDOS2) as APELLIDOS" );
		sql.SELECT("CONCAT(PERSONA.NOMBRE ||' ',CONCAT(DECODE(PERSONA.APELLIDOS1,'#NA','',PERSONA.APELLIDOS1) || ' ',PERSONA.APELLIDOS2)) AS NOMBRECOMPLETO");
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
	
	public String updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, AdmUsuarios usuario, String idInstitucion) {
		SQL sql = new SQL();
		
 		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		sql.UPDATE("CEN_COMPONENTES");
		
 		if(null != tarjetaIntegrantesUpdateDTO.getFechaCargo()) {
 			String fechaC = dateFormat.format(tarjetaIntegrantesUpdateDTO.getFechaCargo());
			sql.SET("FECHACARGO = '" + fechaC + "'");
		}
 		
		if(!tarjetaIntegrantesUpdateDTO.getDescripcionCargo().equals("")) {
			sql.SET("CARGO = '" + tarjetaIntegrantesUpdateDTO.getDescripcionCargo() + "'");
		}
	
		if(!tarjetaIntegrantesUpdateDTO.getCargo().equals("")) {
			sql.SET("IDCARGO = '" + tarjetaIntegrantesUpdateDTO.getCargo() + "'");
		}
		
		if(!tarjetaIntegrantesUpdateDTO.getParticipacionSociedad().equals("")) {
			sql.SET("CAPITALSOCIAL = '" + tarjetaIntegrantesUpdateDTO.getParticipacionSociedad() + "'");
		}

		
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario()+ "'");
		
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDPERSONA = '" + tarjetaIntegrantesUpdateDTO.getIdPersona() + "'");
		sql.WHERE("IDCOMPONENTE = '" + tarjetaIntegrantesUpdateDTO.getIdComponente() + "'");
		
		
		return sql.toString();
	}
	
	
	
	public String insertSelectiveForcreateMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,AdmUsuarios usuario, String idInstitucion) {
		SQL sql = new SQL();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		sql.INSERT_INTO("CEN_COMPONENTES");
		
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion  +"'");
		sql.VALUES("IDPERSONA", "'" + tarjetaIntegrantesCreateDTO.getIdPersonaPadre() + "'");
		sql.VALUES("IDCOMPONENTE", "'" + tarjetaIntegrantesCreateDTO.getIdComponente() + "'");
		
		if(!tarjetaIntegrantesCreateDTO.getCargo().equals("")) {
			sql.VALUES("CARGO", "'" + tarjetaIntegrantesCreateDTO.getDescripcionCargo() + "'");
		}
		
		
		if(null != tarjetaIntegrantesCreateDTO.getFechaCargo()) {
			String fechaC = dateFormat.format(tarjetaIntegrantesCreateDTO.getDescripcionCargo());
			sql.VALUES("FECHACARGO", "'" + fechaC + "'");
		}
		
		
		sql.VALUES("CEN_CLIENTE_IDPERSONA", "'" + tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante() + "'");
		sql.VALUES("CEN_CLIENTE_IDINSTITUCION", "'" + tarjetaIntegrantesCreateDTO.getIdInstitucionIntegrante() + "'");
		sql.VALUES("SOCIEDAD", "'0'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + usuario.getIdusuario()+ "'");
		
		if(!tarjetaIntegrantesCreateDTO.getIdTipoColegio().equals("")) {
			sql.VALUES("IDTIPOCOLEGIO", "'" + tarjetaIntegrantesCreateDTO.getIdTipoColegio() + "'");
		}
		
		if(!tarjetaIntegrantesCreateDTO.getIdProvincia().equals("")) {
			sql.VALUES("IDPROVINCIA", "'" + tarjetaIntegrantesCreateDTO.getIdProvincia() + "'");
		}
		
		if(!tarjetaIntegrantesCreateDTO.getNumColegiado().equals("")) {
			sql.VALUES("NUMCOLEGIADO", "'" + tarjetaIntegrantesCreateDTO.getNumColegiado() + "'");
		}
		
		if(!tarjetaIntegrantesCreateDTO.getParticipacionSociedad().equals("")) {
			sql.VALUES("CAPITALSOCIAL", "'" + tarjetaIntegrantesCreateDTO.getParticipacionSociedad() + "'");
		}
		
		if(!tarjetaIntegrantesCreateDTO.getCargo().equals("")) {
			sql.VALUES("IDCARGO", "'" + tarjetaIntegrantesCreateDTO.getCargo() + "'");
		}
		
		
		
		sql.VALUES("FECHABAJA", "null");
		
		return sql.toString();
	}
	
	
	public String selectMaxIDComponente(String idPersonaPadre, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("MAX(IDCOMPONENTE) AS IDCOMPONENTE");
		sql.FROM("CEN_COMPONENTES");
		sql.WHERE("IDPERSONA = '" + idPersonaPadre + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		return sql.toString();
	}
}
