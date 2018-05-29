package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenPersonaSqlProvider;

public class CenPersonaSqlExtendsProvider extends CenPersonaSqlProvider{

	public String loadPhotography(String idPersona) {
		SQL sql = new SQL();
		
		sql.SELECT("PER.NIFCIF");
		sql.SELECT("CLI.FOTOGRAFIA");
		sql.FROM(" CEN_CLIENTE CLI");
		sql.INNER_JOIN("  CEN_PERSONA PER ON PER.IDPERSONA = CLI.IDPERSONA ");
		sql.WHERE(" PER.IDPERSONA ='" + idPersona +"'");
		
		return sql.toString();
	}
	
	
	public String insertSelectiveForCreateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, AdmUsuarios usuario) {
		
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_PERSONA");
		sql.VALUES("IDPERSONA", "(Select max(idpersona) +1 from cen_persona)");
		sql.VALUES("NOMBRE", etiquetaUpdateDTO.getDenominacion());
		sql.VALUES("APELLIDOS1", "");
		sql.VALUES("APELLIDOS2", "");
		sql.VALUES("NIFCIF", etiquetaUpdateDTO.getNif());
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", String.valueOf(usuario.getIdusuario()));
		sql.VALUES("IDTIPOIDENTIFICACION", "(SELECT IDTIPOIDENTIFICACION FROM CEN_TIPOIDENTIFICACION WHERE CODIGOEJIS = 'C')");
		sql.VALUES("FECHANACIMIENTO", "SYSDATE");
		sql.VALUES("IDESTADOCIVIL", "");
		sql.VALUES("NATURALDE", "");
		sql.VALUES("FALLECIDO", "0");
		sql.VALUES("SEXO", "");
		
		return sql.toString();
	}
}
