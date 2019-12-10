package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.AsuntosJusticiableItem;
import org.itcgae.siga.db.mappers.ScsEjgSqlProvider;

public class ScsEJGSqlExtendsProvider extends ScsEjgSqlProvider {

	public String searchClaveAsuntosEJG(AsuntosJusticiableItem asuntosJusticiableItem) {
		SQL sql = new SQL();


		sql.SELECT("EJG.idinstitucion, EJG.anio,EJG.numero,to_char(EJG.IDTIPOEJG) AS clave, '' as rol, 'E' as tipo");
		sql.FROM("SCS_EJG EJG");
		sql.INNER_JOIN("SCS_PERSONAJG PERSONA ON (EJG.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = EJG.IDINSTITUCION)");
		sql.INNER_JOIN("SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.ANIO = "
				+ "EJG.ANIO AND ESTADO.NUMERO = EJG.NUMERO AND ESTADO.FECHABAJA IS NULL AND ESTADO.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION" + 
				" AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG AND ESTADO.ANIO = ESTADO2.ANIO AND ESTADO.NUMERO = ESTADO2.NUMERO AND ESTADO2.FECHABAJA IS NULL)))");
		sql.WHERE("EJG.idinstitucion = " + asuntosJusticiableItem.getidInstitucion());
		
		if(asuntosJusticiableItem.getAnio() != null) {
			sql.WHERE("EJG.ANIO = "+asuntosJusticiableItem.getAnio());
		}		
		if(asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("EJG.NUMERO = "+asuntosJusticiableItem.getNumero());
		}
		if(asuntosJusticiableItem.getIdTurno()!= null) {
			sql.WHERE("EJG.GUARDIATURNO_IDTURNO = "+asuntosJusticiableItem.getIdTurno());
		}
		if(asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = "+asuntosJusticiableItem.getIdGuardia());
		}
		if(asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') >= TO_DATE("+asuntosJusticiableItem.getFechaAperturaDesde()+")");
		}
		if(asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') <= TO_DATE("+asuntosJusticiableItem.getFechaAperturaHasta()+")");
		}
		
		sql.WHERE("(TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+asuntosJusticiableItem.getApellidos()+"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
		sql.WHERE("(TRANSLATE(LOWER( NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+asuntosJusticiableItem.getNombre()+"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
	
		if(asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("EJG.IDPERSONA = "+asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if(asuntosJusticiableItem.getNif() != null) {
			sql.WHERE("PERSONA.NIF = "+asuntosJusticiableItem.getNif());
		}
		if(asuntosJusticiableItem.getIdTipoEjg() != null) {
			sql.WHERE("EJG.IDTIPOEJG = "+asuntosJusticiableItem.getIdTipoEjg());
		}
		if(asuntosJusticiableItem.getIdTipoEjColegio() != null) {
			sql.WHERE("EJG.IDTIPOEJGCOLEGIO = "+asuntosJusticiableItem.getIdTipoEjColegio());
		}
		if(asuntosJusticiableItem.getIdEstadoPorEjg() != null) {
			sql.WHERE("ESTADO.IDESTADOPOREJG = "+asuntosJusticiableItem.getIdEstadoPorEjg());
		}
		
		return sql.toString();
	}
	
}
