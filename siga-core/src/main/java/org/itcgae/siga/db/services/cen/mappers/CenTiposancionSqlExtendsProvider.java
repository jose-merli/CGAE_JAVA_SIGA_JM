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
		sql.SELECT("concat(persona.NOMBRE || ' ',concat(persona.APELLIDOS1 || ' ', persona.APELLIDOS2))  as NOMBRE");
		sql.SELECT("persona.NIFCIF as NIFCIF");
		sql.SELECT("persona.FECHANACIMIENTO as FECHANACIMIENTO");
		sql.SELECT("tipoSancion.DESCRIPCION as TIPOSANCION");
		sql.SELECT("sancion.REFCGAE as REFCONSEJO");
		sql.SELECT("sancion.REFCOLEGIO as REFCOLEGIO");
		sql.SELECT("sancion.FECHAINICIO as FECHAINICIO");
		sql.SELECT("sancion.FECHAFIN as FECHAFIN");
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
			sql.WHERE("persona.NOMBRE = '" + busquedaSancionesSearchDTO.getNombre() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getPrimerApellido())) {
			sql.WHERE("persona.APELLIDOS1 = '" + busquedaSancionesSearchDTO.getPrimerApellido() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getSegundoApellido())) {
			sql.WHERE("persona.APELLIDOS2 = '" + busquedaSancionesSearchDTO.getSegundoApellido() + "'");
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
			sql.WHERE("sancion.FECHAINICIO >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY') ");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(busquedaSancionesSearchDTO.getFechaHasta());
			sql.WHERE("sancion.FECHAFIN <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY') ");
		}
		
		if(busquedaSancionesSearchDTO.getChkArchivadas()) {
			sql.WHERE("sancion.CHKARCHIVADA = '" + busquedaSancionesSearchDTO.getChkArchivadas()  + "'");
		}
		
		if(busquedaSancionesSearchDTO.getChkRehabilitados()) {
			sql.WHERE("sancion.CHKREHABILITADO = '" + busquedaSancionesSearchDTO.getChkRehabilitados()  + "'");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaArchivadaDesde()) {
			String fechaArcDesde = dateFormat.format(busquedaSancionesSearchDTO.getFechaArchivadaDesde());
			sql.WHERE("sancion.FECHAARCHIVADA >= TO_DATE('" + fechaArcDesde + "', 'DD/MM/YYYY') ");
		}
		
		if (null != busquedaSancionesSearchDTO.getFechaArchivadaHasta()) {
			String fechaArcHasta = dateFormat.format(busquedaSancionesSearchDTO.getFechaArchivadaHasta());
			sql.WHERE("sancion.FECHAARCHIVADA <= TO_DATE('" + fechaArcHasta + "', 'DD/MM/YYYY') ");
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getTipo())) {
			sql.WHERE("sancion.IDTIPOSANCION = '" + busquedaSancionesSearchDTO.getTipo() + "'");
		}

		// OJO Falta estado y origen que son campos nuevos que aún no se han creado
		
		return sql.toString();
	}

}
