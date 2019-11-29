package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.mappers.ScsDesignaSqlProvider;

public class ScsDesignaSqlExtendsProvider extends ScsDesignaSqlProvider {

	public String getAsuntoTipoDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("DESIGNA.IDINSTITUCION");
		sql.SELECT("concat('D' || DESIGNA.anio || '/',lpad(DESIGNA.codigo,5,'0') ) as asunto");
		sql.SELECT("DESIGNA.FECHAENTRADA as fecha");
		sql.SELECT("DESIGNA.ANIO");
		sql.SELECT("DESIGNA.NUMERO");
		sql.SELECT("DESIGNA.codigo");
		sql.SELECT("turno.nombre as turnoguardia");
		sql.SELECT("'Juzgado: ' || juzgado.nombre as datosinteres");

		sql.FROM("SCS_DESIGNA DESIGNA");

		sql.LEFT_OUTER_JOIN("SCS_TURNO TURNO ON designa.idturno = turno.idturno and designa.idinstitucion = turno.idinstitucion");
		sql.LEFT_OUTER_JOIN("SCS_Juzgado juzgado ON juzgado.idjuzgado = DESIGNA.IDJUZGADO and DESIGNA.IDINSTITUCION = juzgado.IDINSTITUCION");
		
		sql.WHERE("DESIGNA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNA.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("DESIGNA.idturno = '" + asuntoClave.getClave() + "'");
		
		return sql.toString();
	}

}
