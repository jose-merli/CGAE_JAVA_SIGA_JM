package org.itcgae.siga.db.services.cen.mappers;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenTiposancionSqlProvider;

import io.jsonwebtoken.lang.Strings;

public class CenTiposancionSqlExtendsProvider extends CenTiposancionSqlProvider {

	public String getComboTipoSancion() {
		SQL sql = new SQL();
		sql.SELECT("IDTIPOSANCION");
		sql.SELECT("DESCRIPCION");
		sql.FROM("CEN_TIPOSANCION");
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}
	
	public String searchBusquedaSanciones(BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


		sql.SELECT("DISTINCT institucion.NOMBRE as COLEGIO");
		sql.SELECT("institucion.IDINSTITUCION as IDCOLEGIO");
		sql.SELECT("sancion.IDSANCION as IDSANCION");
		sql.SELECT("persona.IDPERSONA as IDPERSONA");
		sql.SELECT("concat(persona.NOMBRE || ' ',concat(persona.APELLIDOS1 || ' ', persona.APELLIDOS2))  as NOMBRE");
		sql.SELECT("persona.NIFCIF as NIFCIF");
		sql.SELECT("TO_CHAR(persona.FECHANACIMIENTO,'DD/MM/YYYY') as FECHANACIMIENTO");
		sql.SELECT("persona.FECHANACIMIENTO as FECHANACIMIENTODATE");
		sql.SELECT("tipoSancion.DESCRIPCION as TIPOSANCION");
		sql.SELECT("sancion.REFCOLEGIO as REFCOLEGIO");
		sql.SELECT("TO_CHAR(sancion.FECHAINICIO,'DD/MM/YYYY') as FECHAINICIO");
		sql.SELECT("sancion.FECHAINICIO as FECHAINICIODATE");
		sql.SELECT("TO_CHAR(sancion.FECHAFIN,'DD/MM/YYYY') as FECHAFIN");
		sql.SELECT("sancion.FECHAFIN as FECHAFINDATE");
		sql.SELECT("TO_CHAR(sancion.FECHAFIRMEZA,'DD/MM/YYYY') as FECHAFIRMEZA");
		sql.SELECT("sancion.FECHAFIRMEZA as FECHAFIRMEZADATE");
		sql.SELECT("TO_CHAR(sancion.FECHAACUERDO,'DD/MM/YYYY') as FECHAACUERDO");
		sql.SELECT("sancion.FECHAACUERDO as FECHAACUERDODATE");
		sql.SELECT("TO_CHAR(sancion.FECHAREHABILITADO,'DD/MM/YYYY') as FECHAREHABILITADO");
		sql.SELECT("sancion.FECHAREHABILITADO as FECHAREHABILITADODATE");
		sql.SELECT("decode(sancion.CHKREHABILITADO,0,'No', 'Sí') as REHABILITADO");
		sql.SELECT("decode(sancion.CHKFIRMEZA,0,'No', 'Sí') as FIRMEZA");

		sql.FROM("CEN_SANCION sancion");

		sql.INNER_JOIN("CEN_INSTITUCION institucion ON institucion.idInstitucion = sancion.idInstitucionSancion");
		sql.INNER_JOIN("CEN_PERSONA persona ON persona.idPersona = sancion.idPersona");
		sql.INNER_JOIN("CEN_COLEGIADO colegiado ON colegiado.idPersona = persona.idPersona");
		sql.INNER_JOIN("CEN_TIPOSANCION tipoSancion ON tipoSancion.idTipoSancion = sancion.idTipoSancion");
		
//		if(null != idInstitucion) {
//
//			sql.WHERE("sancion.IDINSTITUCION = '" + idInstitucion + "'");
//		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getNif())) {
			sql.WHERE("persona.NIFCIF = '" + busquedaSancionesSearchDTO.getNif() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getNombre())) {
			sql.WHERE(filtroTextoBusquedas("persona.NOMBRE", busquedaSancionesSearchDTO.getNombre()));
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getPrimerApellido())) {
			sql.WHERE(filtroTextoBusquedas("persona.APELLIDOS1", busquedaSancionesSearchDTO.getPrimerApellido()));
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getSegundoApellido())) {
			sql.WHERE(filtroTextoBusquedas("persona.APELLIDOS2", busquedaSancionesSearchDTO.getSegundoApellido()));
		}
		
		
		if (null != busquedaSancionesSearchDTO.getColegio() && busquedaSancionesSearchDTO.getColegio().length > 0) {
		
			sql.WHERE("institucion.IDINSTITUCION IN (" +  String.join(",", busquedaSancionesSearchDTO.getColegio()) + ")");
		}else {
			sql.WHERE("institucion.IDINSTITUCION IN (" +  idInstitucion + ")");
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getTipoSancion())) {
			sql.WHERE("tipoSancion.IDTIPOSANCION = '" + busquedaSancionesSearchDTO.getTipoSancion()  + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getRefColegio())) {
			sql.WHERE("sancion.REFCOLEGIO = '" + busquedaSancionesSearchDTO.getRefColegio()  + "'");
		}

		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getRefConsejo())) {
			sql.WHERE("sancion.REFCGAE = '" + busquedaSancionesSearchDTO.getRefConsejo()  + "'");
		}

		if (null != busquedaSancionesSearchDTO.getFecha()) {
			String fecha = dateFormat.format(busquedaSancionesSearchDTO.getFecha());
			sql.WHERE("TO_DATE(sancion.FECHARESOLUCION,'DD/MM/YYYY') = TO_DATE('" + fecha + "', 'DD/MM/YYYY') ");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(busquedaSancionesSearchDTO.getFechaDesde());
			sql.WHERE("TO_DATE(sancion.FECHAINICIO) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY') ");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(busquedaSancionesSearchDTO.getFechaHasta());
			sql.WHERE("TO_DATE(sancion.FECHAFIN) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY') ");
		}
		
		if(busquedaSancionesSearchDTO.getChkArchivadas()) {
			sql.WHERE("sancion.CHKARCHIVADA = 1");
		}else {
			sql.WHERE("sancion.CHKARCHIVADA = 0");
		}
		
		if(busquedaSancionesSearchDTO.getChkRehabilitados()) {
			sql.WHERE("sancion.CHKREHABILITADO = 1");
		}else {
			sql.WHERE("sancion.CHKREHABILITADO = 0");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaArchivadaDesde()) {
			String fechaArcDesde = dateFormat.format(busquedaSancionesSearchDTO.getFechaArchivadaDesde());
			sql.WHERE("TO_DATE(sancion.FECHAARCHIVADA) >= TO_DATE('" + fechaArcDesde + "', 'DD/MM/YYYY') ");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaArchivadaHasta()) {
			String fechaArcHasta = dateFormat.format(busquedaSancionesSearchDTO.getFechaArchivadaHasta());
			sql.WHERE("TO_DATE(sancion.FECHAARCHIVADA) <= TO_DATE('" + fechaArcHasta + "', 'DD/MM/YYYY') ");
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getTipo())) {
			sql.WHERE("sancion.IDTIPOSANCION = '" + busquedaSancionesSearchDTO.getTipo() + "'");
		}

		// OJO Falta estado y origen que son campos nuevos que aún no se han creado
		
		return sql.toString();
	}
	
	public static String filtroTextoBusquedas(String columna, String cadena) {
		StringBuilder cadenaWhere = new StringBuilder();
		cadenaWhere.append(" (TRANSLATE(LOWER( " + columna + "),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') ");
		cadenaWhere.append(" LIKE");
		cadenaWhere.append(" TRANSLATE(LOWER('%" + cadena + "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz')) ");
		return cadenaWhere.toString();
		
	} 

}
