package org.itcgae.siga.db.services.cen.mappers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.CenTiposancionSqlProvider;

public class CenTiposancionSqlExtendsProvider extends CenTiposancionSqlProvider {

	public String getComboTipoSancion() {
		SQL sql = new SQL();
		sql.SELECT("IDTIPOSANCION");
		sql.SELECT("DESCRIPCION");
		sql.FROM("CEN_TIPOSANCION");
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}

	public String searchBusquedaSanciones(BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();

		String rdo = "";

		String fechaDesde = "";
		String fechaHasta = "";
		String tipoFecha = busquedaSancionesSearchDTO.getTipoFecha();

		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("DISTINCT institucion.NOMBRE as COLEGIO");
		sql.SELECT("institucion.IDINSTITUCION as IDCOLEGIO");
		sql.SELECT("sancion.IDSANCION as IDSANCION");
		sql.SELECT("sancion.IDINSTITUCION as IDINSTITUCION");
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
		sql.SELECT("decode(sancion.CHKARCHIVADA,0,'No', 'Sí') as ARCHIVADA");
		sql.SELECT("sancion.texto as TEXTO");
		sql.SELECT("sancion.observaciones as OBSERVACIONES");
		sql.SELECT("sancion.NUM_EXPEDIENTE");

		sql.FROM("CEN_SANCION sancion");

		sql.INNER_JOIN("CEN_INSTITUCION institucion ON institucion.idInstitucion = sancion.idInstitucionSancion");
		sql.INNER_JOIN("CEN_PERSONA persona ON persona.idPersona = sancion.idPersona");
		sql.INNER_JOIN("CEN_COLEGIADO colegiado ON colegiado.idPersona = persona.idPersona");
		sql.INNER_JOIN("CEN_TIPOSANCION tipoSancion ON tipoSancion.idTipoSancion = sancion.idTipoSancion");

		sql.WHERE("(sancion.idInstitucion = '" + idInstitucion
				+ "' OR (sancion.idInstitucion = '2000' AND sancion.chkFirmeza = '1'))");

		// ARCHIVADA
		if(busquedaSancionesSearchDTO.getChkArchivadas() != null) {
			if (busquedaSancionesSearchDTO.getChkArchivadas() == false){
				sql.WHERE(
						"(nvl (sancion.chkArchivada,0) = 0 OR nvl (sancion.chkArchivada,0) = 1 AND sancion.fechaArchivada is not null AND sancion.fechaArchivada > sysdate)");
			} else {
				sql.WHERE("sancion.CHKARCHIVADA = 1");

				if (busquedaSancionesSearchDTO.getFechaArchivadaDesde() == null
						&& busquedaSancionesSearchDTO.getFechaArchivadaHasta() == null) {
					sql.WHERE("(sancion.FECHAARCHIVADA <=sysdate OR sancion.FECHAARCHIVADA is null)");
				} else {
					String fechaArcDesde = dateFormat.format(busquedaSancionesSearchDTO.getFechaArchivadaDesde());
					String fechaArcHasta = dateFormat.format(busquedaSancionesSearchDTO.getFechaArchivadaHasta());

					if (null != busquedaSancionesSearchDTO.getFechaArchivadaDesde()
							&& null != busquedaSancionesSearchDTO.getFechaArchivadaHasta()) {
						sql.WHERE("TO_DATE(sancion.FECHAARCHIVADA) >= TO_DATE('" + fechaArcDesde
								+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAARCHIVADA) <= TO_DATE('" + fechaArcHasta
								+ "', 'DD/MM/YYYY')");
					} else if (busquedaSancionesSearchDTO.getFechaArchivadaDesde() == null
							&& null != busquedaSancionesSearchDTO.getFechaArchivadaHasta()) {
						sql.WHERE("TO_DATE(sancion.FECHAARCHIVADA) <= TO_DATE('" + fechaArcHasta + "', 'DD/MM/YYYY')");
					} else if (null != busquedaSancionesSearchDTO.getFechaArchivadaDesde()
							&& busquedaSancionesSearchDTO.getFechaArchivadaHasta() == null) {
						sql.WHERE("TO_DATE(sancion.FECHAARCHIVADA) >= TO_DATE('" + fechaArcDesde + "', 'DD/MM/YYYY')");
					}
				}
			}
		}

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

			sql.WHERE(
					"institucion.IDINSTITUCION IN (" + String.join(",", busquedaSancionesSearchDTO.getColegio()) + ")");
		}

		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getTipoSancion())) {
			sql.WHERE("tipoSancion.IDTIPOSANCION = '" + busquedaSancionesSearchDTO.getTipoSancion() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getRefColegio())) {
			sql.WHERE(filtroTextoBusquedas("sancion.REFCOLEGIO", busquedaSancionesSearchDTO.getRefColegio()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getRefConsejo())) {
			sql.WHERE("sancion.REFCGAE = '" + busquedaSancionesSearchDTO.getRefConsejo() + "'");
		}

		// FECHA ACUERDO

		if (null != busquedaSancionesSearchDTO.getFechaDesde()) {
			fechaDesde = dateFormat.format(busquedaSancionesSearchDTO.getFechaDesde());
		}

		if (null != busquedaSancionesSearchDTO.getFechaHasta()) {
			fechaHasta = dateFormat.format(busquedaSancionesSearchDTO.getFechaHasta());
		}

		if (tipoFecha.equals(SigaConstants.ACUERDO)) {
			
				if (null != busquedaSancionesSearchDTO.getFechaDesde()
						&& busquedaSancionesSearchDTO.getFechaHasta() != null) {
					sql.WHERE("TO_DATE(sancion.FECHAACUERDO) >= TO_DATE('" + fechaDesde
							+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAACUERDO) <= TO_DATE('" + fechaHasta
							+ "', 'DD/MM/YYYY')");
				} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
						&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
					sql.WHERE("TO_DATE(sancion.FECHAACUERDO) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
				} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
						&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
					sql.WHERE("TO_DATE(sancion.FECHAACUERDO) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
				}
		}

		if (tipoFecha.equals(SigaConstants.FIN)) {
			if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() != null) {
				sql.WHERE("TO_DATE(sancion.FECHAFIN) >= TO_DATE('" + fechaDesde
						+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAFIN) <= TO_DATE('" + fechaHasta
						+ "', 'DD/MM/YYYY')");
			} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
					&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
				sql.WHERE("TO_DATE(sancion.FECHAFIN) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
			} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
				sql.WHERE("TO_DATE(sancion.FECHAFIN) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
			}
		}

		if (tipoFecha.equals(SigaConstants.FIRMEZA)) {
			if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() != null) {
				sql.WHERE("TO_DATE(sancion.FECHAFIRMEZA) >= TO_DATE('" + fechaDesde
						+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAFIRMEZA) <= TO_DATE('" + fechaHasta
						+ "', 'DD/MM/YYYY')");
			} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
					&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
				sql.WHERE("TO_DATE(sancion.FECHAFIRMEZA) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
			} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
				sql.WHERE("TO_DATE(sancion.FECHAFIRMEZA) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
			}
		}

		if (tipoFecha.equals(SigaConstants.IMPOSICION)) {
			if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaDesde() != null) {
				sql.WHERE("TO_DATE(sancion.FECHAIMPOSICION) >= TO_DATE('" + fechaDesde
						+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAIMPOSICION) <= TO_DATE('" + fechaHasta
						+ "', 'DD/MM/YYYY')");
			} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
					&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
				sql.WHERE("TO_DATE(sancion.FECHAIMPOSICION) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
			} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
				sql.WHERE("TO_DATE(sancion.FECHAIMPOSICION) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
			}
		}

		if (tipoFecha.equals(SigaConstants.INICIO)) {
			if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() != null) {
				sql.WHERE("TO_DATE(sancion.FECHAINICIO) >= TO_DATE('" + fechaDesde
						+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAINICIO) <= TO_DATE('" + fechaHasta
						+ "', 'DD/MM/YYYY')");
			} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
					&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
				sql.WHERE("TO_DATE(sancion.FECHAINICIO) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
			} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
				sql.WHERE("TO_DATE(sancion.FECHAINICIO) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
			}
		}

		if (tipoFecha.equals(SigaConstants.REHABILITADO)) {
			if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() != null) {
				sql.WHERE("TO_DATE(sancion.FECHAREHABILITADO) >= TO_DATE('" + fechaDesde
						+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHAREHABILITADO) <= TO_DATE('" + fechaHasta
						+ "', 'DD/MM/YYYY')");
			} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
					&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
				sql.WHERE("TO_DATE(sancion.FECHAREHABILITADO) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
			} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
				sql.WHERE("TO_DATE(sancion.FECHAREHABILITADO) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
			}
		}

		if (tipoFecha.equals(SigaConstants.RESOLUCION)) {
			if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() != null) {
				sql.WHERE("TO_DATE(sancion.FECHARESOLUCION) >= TO_DATE('" + fechaDesde
						+ "', 'DD/MM/YYYY') AND TO_DATE(sancion.FECHARESOLUCION) <= TO_DATE('" + fechaHasta
						+ "', 'DD/MM/YYYY')");
			} else if (busquedaSancionesSearchDTO.getFechaDesde() == null
					&& null != busquedaSancionesSearchDTO.getFechaHasta()) {
				sql.WHERE("TO_DATE(sancion.FECHARESOLUCION) <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
			} else if (null != busquedaSancionesSearchDTO.getFechaDesde()
					&& busquedaSancionesSearchDTO.getFechaHasta() == null) {
				sql.WHERE("TO_DATE(sancion.FECHARESOLUCION) >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
			}
		}

		if (busquedaSancionesSearchDTO.getChkRehabilitados()) {
			sql.WHERE("sancion.CHKREHABILITADO = 1");
		}

		if (!UtilidadesString.esCadenaVacia(busquedaSancionesSearchDTO.getTipo())) {
			sql.WHERE("sancion.IDTIPOSANCION = '" + busquedaSancionesSearchDTO.getTipo() + "'");
		}
		
		//TODO revisar si solo es cuando se entra como colegiado
		if (busquedaSancionesSearchDTO.getChkFirmeza()) {
			sql.WHERE("sancion.CHKREHABILITADO = 1");
		}

		rdo = sql.toString() + " ORDER BY NOMBRE";
		return rdo;
	}

	public static String filtroTextoBusquedas(String columna, String cadena) {
		StringBuilder cadenaWhere = new StringBuilder();
		cadenaWhere.append(" (TRANSLATE(LOWER( " + columna
				+ "),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') ");
		cadenaWhere.append(" LIKE");
		cadenaWhere.append(" TRANSLATE(LOWER('%" + cadena
				+ "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz')) ");
		return cadenaWhere.toString();

	}

}
