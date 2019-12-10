package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.AsuntosJusticiableItem;
import org.itcgae.siga.db.mappers.ScsSojSqlProvider;

public class ScsSojSqlExtendsProvider extends ScsSojSqlProvider{
	
	public String searchClaveSoj(AsuntosJusticiableItem asuntosJusticiableItem) {
		SQL sql = new SQL();

		sql.SELECT("SOJ.idinstitucion, SOJ.anio,SOJ.numero,to_char(SOJ.IDTIPOSOJ) AS clave, '' as rol, 'S' as tipo");
		sql.FROM("SCS_SOJ SOJ");
//		El Join con la tabla de scs_personaJG, solo realizará si nos viene informado alguno de los datos del solicitante(Nif, nombre o apellidos).
		if(asuntosJusticiableItem.getNombre()!= null || asuntosJusticiableItem.getApellidos() != null || asuntosJusticiableItem.getNif()!= null) {
			sql.INNER_JOIN("SCS_PERSONAJG PERSONA ON (SOJ.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = SOJ.IDINSTITUCION)");
		}
		sql.WHERE("SOJ.idinstitucion =" + asuntosJusticiableItem.getidInstitucion());
		
		if(asuntosJusticiableItem.getAnio() != null) {
			sql.WHERE("SOJ.ANIO = "+asuntosJusticiableItem.getAnio());
		}		
		if(asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("SOJ.NUMERO = "+asuntosJusticiableItem.getNumero());
		}
		if(asuntosJusticiableItem.getIdTurno()!= null) {
			sql.WHERE("SOJ.IDTURNO = "+asuntosJusticiableItem.getIdTurno());
		}
		if(asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("SOJ.IDGUARDIA = "+asuntosJusticiableItem.getIdGuardia());
		}
		
		if(asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("SOJ.IDPERSONA = "+asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if(asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			sql.WHERE("TO_CHAR(SOJ.FECHAAPERTURA,'DD/MM/RRRR') >= TO_DATE("+asuntosJusticiableItem.getFechaAperturaDesde()+")");
		}
		if(asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			sql.WHERE("TO_CHAR(SOJ.FECHAAPERTURA,'DD/MM/RRRR') <= TO_DATE("+asuntosJusticiableItem.getFechaAperturaHasta()+")");
		}
		if(asuntosJusticiableItem.getNif() != null) {
			sql.WHERE("SOJ.NIF = "+asuntosJusticiableItem.getNif());
		}
		
		sql.WHERE("(TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+asuntosJusticiableItem.getApellidos()+"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
		sql.WHERE("(TRANSLATE(LOWER( NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('" + asuntosJusticiableItem.getNombre()+ "'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
	
	
		if(asuntosJusticiableItem.getIdTipoSoj() != null) {
			sql.WHERE("SOJ.IDTIPOSOJ = "+asuntosJusticiableItem.getIdTipoSoj());
		}
		
		return sql.toString();
	}
}
