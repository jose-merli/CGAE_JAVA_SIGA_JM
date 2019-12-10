package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.AsuntosJusticiableItem;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;

public class ScsAsistenciaSqlExtendsProvider extends ScsAsistenciaSqlProvider {

	public String searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem) {
		SQL sql = new SQL();


		sql.SELECT("ASISTENCIA.idinstitucion, ASISTENCIA.anio,ASISTENCIA.numero,'' as clave, '' as rol, 'A' as tipo");
		sql.FROM("SCS_ASISTENCIA ASISTENCIA");
//		El Join con la tabla de scs_personaJG, solo realizará si nos viene informado alguno de los datos del solicitante(Nif, nombre o apellidos).
		if(asuntosJusticiableItem.getNif()!= null || asuntosJusticiableItem.getNombre() != null || asuntosJusticiableItem.getApellidos()!= null) {
			sql.INNER_JOIN("SCS_PERSONAJG PERSONA ON (ASISTENCIA.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = ASISTENCIA.IDINSTITUCION)");
		}
		sql.WHERE("ASISTENCIA.idinstitucion =" + asuntosJusticiableItem.getidInstitucion());
		
		if(asuntosJusticiableItem.getAnio() != null) {
			sql.WHERE("ASISTENCIA.ANIO = "+asuntosJusticiableItem.getAnio());
		}		
		if(asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("ASISTENCIA.NUMERO = "+asuntosJusticiableItem.getNumero());
		}
		if(asuntosJusticiableItem.getIdTurno()!= null) {
			sql.WHERE("ASISTENCIA.IDTURNO = "+asuntosJusticiableItem.getIdTurno());
		}
		if(asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("ASISTENCIA.IDGUARDIA = "+asuntosJusticiableItem.getIdGuardia());
		}
		if(asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			sql.WHERE("TO_CHAR(ASISTENCIA.FECHASOLICITUD,'DD/MM/RRRR') >= TO_DATE("+asuntosJusticiableItem.getFechaAperturaDesde()+")");
		}
		if(asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			sql.WHERE("TO_CHAR(ASISTENCIA.FECHASOLICITUD,'DD/MM/RRRR') <= TO_DATE("+asuntosJusticiableItem.getFechaAperturaHasta()+")");
		}
		
		
		sql.WHERE("(TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%%APELLIDOS%%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
		sql.WHERE("(TRANSLATE(LOWER( NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%%NOMBRE %%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
	
		if(asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = "+asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if(asuntosJusticiableItem.getNif() != null) {
			sql.WHERE("PERSONA.NIF = "+asuntosJusticiableItem.getNif());
		}
		if(asuntosJusticiableItem.getIdTipoAsistencia() != null) {
			sql.WHERE("ASISTENCIA.IDTIPOASISTENCIA  = "+asuntosJusticiableItem.getIdTipoAsistencia());
		}
		if(asuntosJusticiableItem.getNumeroProcedimiento() != null) {
			sql.WHERE("ASISTENCIA.NUMEROPROCEDIMIENTO   = "+asuntosJusticiableItem.getNumeroProcedimiento());
		}
		if(asuntosJusticiableItem.getNumeroDiligencia() != null) {
			sql.WHERE("ASISTENCIA.NUMERODILIGENCIA   = "+asuntosJusticiableItem.getNumeroDiligencia());
		}
		if(asuntosJusticiableItem.getComisaria() != null) {
			sql.WHERE("ASISTENCIA.COMISARIA   = "+asuntosJusticiableItem.getComisaria());
		}
		if(asuntosJusticiableItem.getJuzgado() != null) {
			sql.WHERE("ASISTENCIA.JUZGADO  = "+asuntosJusticiableItem.getJuzgado());
		}
		
		return sql.toString();
	}
	
}
