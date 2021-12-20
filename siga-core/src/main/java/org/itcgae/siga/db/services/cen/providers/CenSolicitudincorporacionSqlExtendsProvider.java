package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;

public class CenSolicitudincorporacionSqlExtendsProvider {
	
	
	public String selectSolicitudes(SolicitudIncorporacionSearchDTO solIncorporacionSearchDTO, String idLenguage){
		
		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("IDSOLICITUD");
		sql.SELECT("NUMEROIDENTIFICADOR");
		sql.SELECT("SOLICITUD.NOMBRE");
		sql.SELECT("CONCAT(APELLIDO1 || ' ',APELLIDO2) as APELLIDOS");
		sql.SELECT("APELLIDO1 AS APELLIDO1");
		sql.SELECT("APELLIDO2 AS APELLIDO2");
		sql.SELECT("SEXO");
		sql.SELECT("FECHANACIMIENTO AS FECHANACIMIENTO");
		sql.SELECT("NCOLEGIADO AS NUMCOLEGIADO");
		sql.SELECT("FECHASOLICITUD");
		sql.SELECT("FECHAESTADO");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("(select INITCAP(f_siga_getrecurso(DESCRIPCION," + idLenguage + ")) AS LABEL from CEN_ESTADOSOLICITUD e WHERE IDESTADO = SOLICITUD.IDESTADO) AS ESTADOSOLICITUD");
		sql.SELECT("(select f_siga_getrecurso(DESCRIPCION," + idLenguage +") AS LABEL from CEN_TIPOSOLICITUD e WHERE IDTIPOSOLICITUD = SOLICITUD.IDTIPOSOLICITUD) AS TIPOSOLICITUD");
		sql.SELECT("(select f_siga_getrecurso(DESCRIPCION," + idLenguage +") AS LABEL from CEN_DOCUMENTACIONMODALIDAD e WHERE IDMODALIDAD = SOLICITUD.IDMODALIDADDOCUMENTACION AND IDINSTITUCION = SOLICITUD.IDINSTITUCION) AS MODALIDAD");
		sql.SELECT("IDMODALIDADDOCUMENTACION AS IDMODALIDADDOCUMENTACION");
		sql.SELECT("IDESTADO AS IDESTADO");
		sql.SELECT("IDTIPOSOLICITUD  AS IDTIPO");
		sql.SELECT("(select f_siga_getrecurso(DESCRIPCION,1) AS LABEL from CEN_TIPOCOLEGIACION e WHERE IDTIPOCOLEGIACION = SOLICITUD.IDTIPOCOLEGIACION) AS TIPOCOLEGIACION");
		sql.SELECT("IDTIPOCOLEGIACION AS IDTIPOCOLEGIACION");
		sql.SELECT("(select f_siga_getrecurso(DESCRIPCION,1) AS LABEL from CEN_TIPOIDENTIFICACION e WHERE IDTIPOIDENTIFICACION = SOLICITUD.IDTIPOIDENTIFICACION) AS TIPOIDENTIFICACION");
		sql.SELECT("IDTIPOIDENTIFICACION AS IDTIPOIDENTIFICACION");
		sql.SELECT("observaciones");
		sql.SELECT("FECHAESTADOCOLEGIAL AS FECHAINCORPORACION");
		sql.SELECT("RESIDENTE AS RESIDENTE");
		sql.SELECT("NATURALDE AS NATURALDE");
		sql.SELECT("(select f_siga_getrecurso(DESCRIPCION,1) AS LABEL from CEN_TRATAMIENTO e WHERE IDTRATAMIENTO = SOLICITUD.IDTRATAMIENTO) AS TRATAMIENTO");
		sql.SELECT("IDTRATAMIENTO as IDTRATAMIENTO");
		sql.SELECT("(select f_siga_getrecurso(DESCRIPCION,1) AS LABEL from CEN_ESTADOCIVIL e WHERE IDESTADOCIVIL = SOLICITUD.IDESTADOCIVIL) AS ESTADOCIVIL");
		sql.SELECT("IDESTADOCIVIL AS IDESTADOCIVIL");
		sql.SELECT("(select f_siga_getrecurso(NOMBRE,1) AS LABEL from CEN_PAIS e WHERE IDPAIS = SOLICITUD.IDPAIS) AS PAIS");
		sql.SELECT("SOLICITUD.IDPAIS AS IDPAIS");		
		sql.SELECT("DOMICILIO AS DOMICILIO");
		sql.SELECT("TELEFONO1 AS TELEFONO1");
		sql.SELECT("TELEFONO2 AS TELEFONO2");
		sql.SELECT("FAX1 AS FAX1");
		sql.SELECT("FAX2 AS FAX2");
		sql.SELECT("MOVIL AS MOVIL");
		sql.SELECT("CORREOELECTRONICO");
		sql.SELECT("CODIGOPOSTAL");
		sql.SELECT("SOLICITUD.IDPOBLACION");
		sql.SELECT("SOLICITUD.IDPROVINCIA");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");
		sql.SELECT("TITULAR");
		sql.SELECT("IBAN");
		sql.SELECT("BIC");
		sql.SELECT("IDPERSONA");
		sql.SELECT("NOMBREBANCO");
		sql.SELECT("ABONOCARGO");
		sql.SELECT("ABONOSJCS");
		sql.SELECT("CBO_CODIGO");
		sql.SELECT("CODIGOSUCURSAL");
		sql.SELECT("DIGITOCONTROL");
		sql.SELECT("NUMEROCUENTA");
		sql.SELECT("concat(CBO_CODIGO,concat(CODIGOSUCURSAL,concat(DIGITOCONTROL,DIGITOCONTROL))) AS BANCO");
		sql.SELECT("NUM_REGISTRO");
		
		
		sql.FROM("CEN_SOLICITUDINCORPORACION SOLICITUD");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = SOLICITUD.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = SOLICITUD.IDPOBLACION");
		
		sql.WHERE("IDINSTITUCION = '"+solIncorporacionSearchDTO.getIdInstitucion()+"'");
		
		if(solIncorporacionSearchDTO.getTipoSolicitud()!=null && !solIncorporacionSearchDTO.getTipoSolicitud().equals("")){
			sql.WHERE("SOLICITUD.IDTIPOSOLICITUD = '" + solIncorporacionSearchDTO.getTipoSolicitud() + "'");
		}
		if(solIncorporacionSearchDTO.getEstadoSolicitud()!=null && !solIncorporacionSearchDTO.getEstadoSolicitud().equals("")){
			sql.WHERE("SOLICITUD.IDESTADO = '" + solIncorporacionSearchDTO.getEstadoSolicitud() + "'");
		}
		if(solIncorporacionSearchDTO.getNumeroIdentificacion()!= null && !solIncorporacionSearchDTO.getNumeroIdentificacion().equals("")){
			sql.WHERE("NUMEROIDENTIFICADOR LIKE '%" + solIncorporacionSearchDTO.getNumeroIdentificacion() +"%'");
		}
		if(solIncorporacionSearchDTO.getApellidos()!=null && !solIncorporacionSearchDTO.getApellidos().equals("")){
			String columna = "CONCAT(APELLIDO1,APELLIDO2)";
			String cadena = solIncorporacionSearchDTO.getApellidos().replaceAll("\\s+","%");
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//			sql.WHERE(UtilidadesString.filtroTextoBusquedas("CONCAT(APELLIDO1,APELLIDO2)", solIncorporacionSearchDTO.getApellidos()));
		}
		if(solIncorporacionSearchDTO.getNombre()!= null && !solIncorporacionSearchDTO.getNombre().equals("")){
			String columna = "SOLICITUD.NOMBRE";
			String cadena = solIncorporacionSearchDTO.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//			sql.WHERE(UtilidadesString.filtroTextoBusquedas("SOLICITUD.NOMBRE", solIncorporacionSearchDTO.getNombre()));
		}
		
		
		
		if(solIncorporacionSearchDTO.getFechaDesde()!=null){
			String fechaDesde = dateFormat.format(solIncorporacionSearchDTO.getFechaDesde());
			sql.WHERE("FECHASOLICITUD >= TO_DATE('" + fechaDesde + "', 'DD/MM/YYYY')");
		}
		
		if(solIncorporacionSearchDTO.getFechaHasta()!=null){
			String fechaHasta = dateFormat.format(solIncorporacionSearchDTO.getFechaHasta());
			sql.WHERE("FECHASOLICITUD <= TO_DATE('" + fechaHasta + "', 'DD/MM/YYYY')");
		}
		
		return sql.toString();
	}
	
	public String getMaxIdSolicitud(){
		SQL sql = new SQL();
		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITUDINCORPORACION");
		return sql.toString();
	}

	/*public String getMaxNColegiado(String idInstitucion){
		SQL sql = new SQL();
		sql.SELECT("NVL(MAX(NCOLEGIADO + 1),0001) AS NCOLEGIADO");
		sql.FROM("CEN_COLEGIADO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	} 

	public String getMaxNComunitario(String idInstitucion){
		SQL sql = new SQL();
		sql.SELECT("NVL(MAX(NCOMUNITARIO + 1),0001) AS NCOMUNITARIO");
		sql.FROM("CEN_COLEGIADO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	} 
	
	public String getMaxNColegiadoComunitario(String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("NVL(CASE WHEN max(to_number(ncolegiado)) >= max(to_number(ncomunitario)) THEN max(to_number(ncolegiado))+1 ELSE max(to_number(ncomunitario))+1 END, 0001) as NCOLEGIADO");
		sql.FROM("CEN_COLEGIADO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		
		return sql.toString();
	}*/

	public String getMaxNColegiado(String idInstitucion){
		SQL sql = new SQL();
		sql.SELECT("greatest((select nvl(max(to_number(ncolegiado+1)),0001) from cen_colegiado where idinstitucion = "+idInstitucion+"), (select nvl(max(to_number(ncolegiado+1)),0001) from cen_reserva_ncolegiado where idinstitucion = "+idInstitucion+" and tipo_ncolegiado='E')) as ncolegiado");
		sql.FROM("DUAL");
		return sql.toString();
	}

	public String getMaxNComunitario(String idInstitucion){
		SQL sql = new SQL();
		sql.SELECT("greatest((select nvl(max(to_number(ncomunitario+1)),0001) from cen_colegiado where idinstitucion = "+idInstitucion+"), (select nvl(max(to_number(ncolegiado+1)),0001) from cen_reserva_ncolegiado where idinstitucion = "+idInstitucion+" and tipo_ncolegiado='I')) as NCOMUNITARIO");
		sql.FROM("DUAL");
		return sql.toString();
	}

	public String getMaxNColegiadoComunitario(String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("greatest((SELECT NVL(CASE WHEN max(to_number(ncolegiado)) >= max(to_number(ncomunitario)) THEN max(to_number(ncolegiado))+1 ELSE max(to_number(ncomunitario))+1 END, 0001) from cen_colegiado where idinstitucion = "+idInstitucion+"), (select nvl(max(to_number(ncolegiado+1)),0001) from cen_reserva_ncolegiado where idinstitucion = "+idInstitucion+")) as ncolegiado");
		sql.FROM("DUAL");
		return sql.toString();
	}

}