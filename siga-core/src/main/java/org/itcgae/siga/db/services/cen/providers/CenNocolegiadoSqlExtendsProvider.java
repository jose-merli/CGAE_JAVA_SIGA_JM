package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.DesasociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.PerJuridicaDatosRegistralesUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenNocolegiadoSqlProvider;

public class CenNocolegiadoSqlExtendsProvider extends CenNocolegiadoSqlProvider{

	
	public String searchLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		String grupos = "";
		
		
		if(busquedaJuridicaSearchDTO.getGrupos().length > 1) {
			for (String string : busquedaJuridicaSearchDTO.getGrupos()) {
				grupos += string;
				grupos += ",";
			}
			grupos = grupos.substring(0,grupos.length()-1);
		}
		else if(busquedaJuridicaSearchDTO.getGrupos().length == 1){
			grupos = busquedaJuridicaSearchDTO.getGrupos()[0];
		}
		
		
		
		sql2.SELECT(" DISTINCT COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql2.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql2.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql2.SELECT("CA.DESCRIPCION AS TIPO");
		sql2.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql2.SELECT("NVL(COUNT(DISTINCT PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql2.SELECT("LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(DECODE(PER2.APELLIDOS1,'#NA','',PER2.APELLIDOS1) || ' ',PER2.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");
		
		sql2.FROM("CEN_NOCOLEGIADO COL");
		
		sql2.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql2.INNER_JOIN("CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION");
		sql2.LEFT_OUTER_JOIN("CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON  COL.TIPO = TIPOSOCIEDAD.LETRACIF");
		
		sql2.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '" + idLenguaje + "')");
		sql2.LEFT_OUTER_JOIN("CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA "
		+ "AND (TO_DATE(grupos_cliente.fecha_inicio,'DD/MM/RRRR') <= SYSDATE  AND ( TO_DATE(grupos_cliente.fecha_baja,'DD/MM/RRRR') >= SYSDATE OR grupos_cliente.fecha_baja IS NULL )))");
		sql2.LEFT_OUTER_JOIN("CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA");
		
		if(null != idInstitucion) {
			sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");
		}
		
		sql2.WHERE("COL.FECHA_BAJA IS NULL");
		if(null != busquedaJuridicaSearchDTO.getNif() && !busquedaJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql2.WHERE("PER.NIFCIF = '" + busquedaJuridicaSearchDTO.getNif() + "'");
		}
		
		if(!grupos.equalsIgnoreCase("")) {
			sql2.WHERE("GRUPOS_CLIENTE.IDGRUPO IN ("+ grupos +")");
		}
		
		if(null != busquedaJuridicaSearchDTO.getFechaConstitucion() && !busquedaJuridicaSearchDTO.getFechaConstitucion().equals("")) {
			String fechaC = dateFormat.format(busquedaJuridicaSearchDTO.getFechaConstitucion());
			sql2.WHERE("TO_DATE(PER.FECHANACIMIENTO,'DD/MM/RRRR') = TO_DATE('" + fechaC + "', 'DD/MM/RRRR')");
		}
		
		if(busquedaJuridicaSearchDTO.getSociedadesProfesionales()) {
			sql2.WHERE("COL.SOCIEDADPROFESIONAL = '1'");
		}
		
		
		if(null != busquedaJuridicaSearchDTO.getTipo() && !busquedaJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql2.WHERE("COL.TIPO = '" + busquedaJuridicaSearchDTO.getTipo() + "'");
		}
		else {
			sql2.WHERE("COL.TIPO IN('A','B','C','D','E','F','G','H','J','P','Q','R','S','U','V')");
		}
		
		
		sql2.GROUP_BY("COL.IDINSTITUCION");
		sql2.GROUP_BY("COL.IDPERSONA");
		sql2.GROUP_BY("PER.NIFCIF");
		sql2.GROUP_BY("PER.NOMBRE");
		sql2.GROUP_BY("PER.APELLIDOS1");
		sql2.GROUP_BY("GRUPOS_CLIENTE.IDGRUPO");
		sql2.GROUP_BY("PER.FECHANACIMIENTO");
		sql2.GROUP_BY("COL.SOCIEDADPROFESIONAL");
		sql2.GROUP_BY("COL.TIPO");
		sql2.GROUP_BY("CA.DESCRIPCION");
		sql2.GROUP_BY("COL.FECHA_BAJA");
		
		
		// meter subconsulta de objeto sql2 en objeto sql
		sql.SELECT("CONSULTA.*");
		sql.FROM( "(" + sql2 + ") consulta");
		if(null != busquedaJuridicaSearchDTO.getDenominacion() && !busquedaJuridicaSearchDTO.getDenominacion().equalsIgnoreCase("")) {
			sql.WHERE("UPPER(consulta.DENOMINACION) like UPPER('%" + busquedaJuridicaSearchDTO.getDenominacion() + "%')");
		}
		
		if(null != busquedaJuridicaSearchDTO.getIntegrante() && !busquedaJuridicaSearchDTO.getIntegrante().equalsIgnoreCase("")) {
			sql.WHERE("upper(consulta.NOMBRESINTEGRANTES) like upper('%"+ busquedaJuridicaSearchDTO.getIntegrante() + "%')");
		}
		
		if(null != busquedaJuridicaSearchDTO.getAbreviatura() && !busquedaJuridicaSearchDTO.getAbreviatura().equalsIgnoreCase("")) {
			sql.WHERE("upper(consulta.ABREVIATURA) like upper('%"+ busquedaJuridicaSearchDTO.getAbreviatura() + "%')");
		}
		
		return sql.toString();
	}
	
	
	
	public String searchHistoricLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		sql2.SELECT("COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql2.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql2.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql2.SELECT("(select descripcion from GEN_RECURSOS where  idrecurso = 'censo.busquedaClientesAvanzada.literal.Sociedad' and idlenguaje = '"+ idLenguaje+ "') AS TIPO");
		sql2.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql2.SELECT("NVL(COUNT(DISTINCT PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql2.SELECT("LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(DECODE(PER2.APELLIDOS1,'#NA','',PER2.APELLIDOS1) || ' ',PER2.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");
		
		sql2.FROM("CEN_NOCOLEGIADO COL");		
		sql2.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql2.INNER_JOIN("CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION");
		sql2.LEFT_OUTER_JOIN("CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON  COL.TIPO = TIPOSOCIEDAD.LETRACIF");
		sql2.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '" + idLenguaje + "')");
		sql2.LEFT_OUTER_JOIN("CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA "
		+ "AND (TO_DATE(grupos_cliente.fecha_inicio,'DD/MM/RRRR') <= SYSDATE  AND ( TO_DATE(grupos_cliente.fecha_baja,'DD/MM/RRRR') >= SYSDATE OR grupos_cliente.fecha_baja IS NULL )))");
		sql2.LEFT_OUTER_JOIN("CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA");
		
		
		if(null != idInstitucion) {
			sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");
		}
		
		sql2.WHERE("COL.TIPO IN('A','B','C','D','E','F','G','H','J','P','Q','R','S','U','V')");
		
		sql2.GROUP_BY("COL.IDINSTITUCION");
		sql2.GROUP_BY("COL.IDPERSONA");
		sql2.GROUP_BY("PER.NIFCIF");
		sql2.GROUP_BY("PER.NOMBRE");
		sql2.GROUP_BY("PER.APELLIDOS1");
		sql2.GROUP_BY("GRUPOS_CLIENTE.IDGRUPO");
		sql2.GROUP_BY("PER.FECHANACIMIENTO");
		sql2.GROUP_BY("COL.SOCIEDADPROFESIONAL");
		sql2.GROUP_BY("COL.TIPO");
		sql2.GROUP_BY("CA.DESCRIPCION");
		sql2.GROUP_BY("COL.FECHA_BAJA");
		
		// meter subconsulta de objeto sql2 en objeto sql
		sql.SELECT("CONSULTA.*");
		sql.FROM( "(" + sql2 + ") consulta");
		
		return sql.toString();
	}
	

	
	public String searchGeneralData(PersonaJuridicaSearchDTO personaJuridicaSearchDTO) {
		
		SQL sql = new SQL();
		
		sql.SELECT(" DISTINCT COL.IDINSTITUCION AS IDINSTITUCION");
		sql.SELECT("COL.IDPERSONA AS IDPERSONA");
		sql.SELECT("COL.ANOTACIONES AS ANOTACIONES");
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION");
		sql.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql.SELECT("TIPOSOCIEDAD.LETRACIF  AS TIPO");
		sql.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql.SELECT("NVL(COUNT(DISTINCT PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql.SELECT("LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(DECODE(PER2.APELLIDOS1,'#NA','',PER2.APELLIDOS1) || ' ',PER2.APELLIDOS2)), ';') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");
		sql.SELECT("LISTAGG(GRUPOS_CLIENTE.IDGRUPO, ';') WITHIN GROUP (ORDER BY GRUPOS_CLIENTE.IDGRUPO)  OVER (PARTITION BY COL.IDPERSONA) AS IDGRUPO");
		sql.SELECT("CLI.IDLENGUAJE AS LENGUAJESOCIEDAD");
		sql.SELECT("CLI.ASIENTOCONTABLE"); 
		sql.FROM(" CEN_NOCOLEGIADO COL");
		sql.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA ");
		sql.INNER_JOIN(" CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION ");
		sql.INNER_JOIN(" CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON TIPOSOCIEDAD.LETRACIF = COL.TIPO ");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '" + personaJuridicaSearchDTO.getIdLenguaje() +"') "
				+ "LEFT JOIN CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA)"
				+ "LEFT JOIN CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION )"
				+ "LEFT JOIN CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )"
				+ "LEFT JOIN CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA ");
		sql.WHERE(" I.IDINSTITUCION = '" + personaJuridicaSearchDTO.getIdInstitucion() +"'");
		sql.WHERE("COL.IDPERSONA = '" + personaJuridicaSearchDTO.getIdPersona() + "'");
		
		sql.GROUP_BY("COL.IDINSTITUCION");
		sql.GROUP_BY("COL.IDPERSONA");
		sql.GROUP_BY("COL.ANOTACIONES");
		sql.GROUP_BY("PER.NIFCIF");
		sql.GROUP_BY("PER.NOMBRE");
		sql.GROUP_BY("PER.APELLIDOS1");
		sql.GROUP_BY("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1)");
		sql.GROUP_BY("PER.FECHANACIMIENTO");
		sql.GROUP_BY("COL.SOCIEDADPROFESIONAL");
		sql.GROUP_BY("COL.TIPO");
		sql.GROUP_BY("TIPOSOCIEDAD.LETRACIF");
		sql.GROUP_BY("GRUPOS_CLIENTE.IDGRUPO");
		sql.GROUP_BY("COL.FECHA_BAJA");
		sql.GROUP_BY("CLI.IDLENGUAJE");
		sql.GROUP_BY("CLI.ASIENTOCONTABLE");
		
		return sql.toString();
	}
	
	
	public String insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario,EtiquetaUpdateDTO etiquetaUpdateDTO) {
		SQL sql = new SQL();
		

		sql.INSERT_INTO("CEN_NOCOLEGIADO");
		
		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");
		sql.VALUES("IDINSTITUCION", "'" +idInstitucion  +"'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +String.valueOf(usuario.getIdusuario()) +"'");
//		sql.VALUES("SERIE", "");
//		sql.VALUES("NUMEROREF", "");
		sql.VALUES("SOCIEDADSJ", "'0'");
		sql.VALUES("TIPO",  "'" +etiquetaUpdateDTO.getTipo() +"'");
		sql.VALUES("ANOTACIONES",  "'" +etiquetaUpdateDTO.getAnotaciones() +"'");
//		sql.VALUES("PREFIJO_NUMREG", "");
//		sql.VALUES("CONTADOR_NUMREG", "");
//		sql.VALUES("SUFIJO_NUMREG", "");
//		sql.VALUES("FECHAFIN", "");
//		sql.VALUES("IDPERSONANOTARIO", "");
//		sql.VALUES("RESENA", "");
//		sql.VALUES("OBJETOSOCIAL", "");
		sql.VALUES("SOCIEDADPROFESIONAL", "'0'");
//		sql.VALUES("PREFIJO_NUMSSPP", "");
//		sql.VALUES("CONTADOR_NUMSSPP", "");
//		sql.VALUES("SUFIJO_NUMSSPP", "");
//		sql.VALUES("NOPOLIZA", "");
//		sql.VALUES("COMPANIASEG", "");
//		sql.VALUES("IDENTIFICADORDS", "");
		sql.VALUES("FECHA_BAJA", "null");
		
		return sql.toString();
	}
	
	
	public String selectProfesionalActivitiesSociety(PersonaJuridicaActividadDTO personaJuridicaActividadDTO, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("NOCOL_ACT.IDACTIVIDADPROFESIONAL");
		sql.SELECT("REC.DESCRIPCION");
		sql.FROM("CEN_NOCOLEGIADO_ACTIVIDAD NOCOL_ACT");
		sql.INNER_JOIN("CEN_ACTIVIDADPROFESIONAL ACTIVIDAD ON (ACTIVIDAD.IDACTIVIDADPROFESIONAL = NOCOL_ACT.IDACTIVIDADPROFESIONAL )");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = ACTIVIDAD.DESCRIPCION AND REC.IDLENGUAJE = '"+idLenguaje+"')");
		sql.WHERE("NOCOL_ACT.IDPERSONA = '"+personaJuridicaActividadDTO.getIdPersona()+"' ");
		sql.WHERE("NOCOL_ACT.IDINSTITUCION = '"+personaJuridicaActividadDTO.getIdInstitucion()+"'");
		sql.WHERE("NOCOL_ACT.FECHA_BAJA IS NULL");
		
		return sql.toString();
	}
	
	public String selectRetenciones(PersonaSearchDTO  personaSearchDto , String idLenguaje, String idInstitucion){
		SQL sql = new SQL();
		

		sql.SELECT("PER.IDPERSONA AS IDPERSONA");
		sql.SELECT("IRPF.FECHAINICIO AS FECHAINICIO");
		sql.SELECT("IRPF.FECHAFIN AS FECHAFIN");
		sql.SELECT("IRPF.IDRETENCION AS IDRETENCION");
		sql.SELECT("RET.RETENCION AS RETENCION");
		sql.SELECT("RET.DESCRIPCION AS RECURSO");
		sql.SELECT("CAT.DESCRIPCION AS DESCRIPCION");
		sql.FROM("CEN_NOCOLEGIADO NOCOL");
		sql.INNER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = NOCOL.IDPERSONA");
		sql.INNER_JOIN("SCS_RETENCIONESIRPF IRPF ON (IRPF.IDPERSONA = NOCOL.IDPERSONA AND IRPF.IDINSTITUCION = NOCOL.IDINSTITUCION)");
		sql.INNER_JOIN("SCS_MAESTRORETENCIONES RET ON RET.IDRETENCION = IRPF.IDRETENCION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = RET.DESCRIPCION AND CAT.IDLENGUAJE = '" + idLenguaje +"')");
		
		sql.WHERE("IDPERSONA = '"+ personaSearchDto.getIdPersona() +"'");
		sql.WHERE("IRPF.IDINSTITUCION = '"+ idInstitucion +"'");
		sql.ORDER_BY("FECHAINICIO DESC");
		
		return sql.toString();
	}
	
	public String disassociatePerson(AdmUsuarios usuario, DesasociarPersonaDTO desasociarPersona) {
		SQL sql = new SQL();
		
		sql.UPDATE("cen_nocolegiado");
		sql.SET("IDPERSONANOTARIO = null");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.WHERE("IDPERSONA = '" + desasociarPersona.getIdPersona() + "'");
		sql.WHERE("IDINSTITUCION = '" +desasociarPersona.getIdInstitucion()+ "'");
		
		return sql.toString();
	}
	
	public String updateByExampleDataLegalPerson(PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, String idInstitucion, AdmUsuarios usuario) {
		SQL sql = new SQL();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		sql.UPDATE("cen_nocolegiado");
		
		if(!perJuridicaDatosRegistralesUpdateDTO.getResena().equals("")) {
			sql.SET("RESENA = '" + perJuridicaDatosRegistralesUpdateDTO.getResena() + "'");
		}
		
		if(!perJuridicaDatosRegistralesUpdateDTO.getObjetoSocial().equals("")) {
			sql.SET("OBJETOSOCIAL = '" + perJuridicaDatosRegistralesUpdateDTO.getObjetoSocial() + "'");
		}
	
		if(!perJuridicaDatosRegistralesUpdateDTO.getNumeroPoliza().equals("")) {
			sql.SET("NOPOLIZA = '" + perJuridicaDatosRegistralesUpdateDTO.getNumeroPoliza() + "'");
		}
		
		if(!perJuridicaDatosRegistralesUpdateDTO.getCompaniaAseg().equals("")) {
			sql.SET("COMPANIASEG = '" + perJuridicaDatosRegistralesUpdateDTO.getCompaniaAseg() + "'");
		}
		
		if(null != perJuridicaDatosRegistralesUpdateDTO.getFechaFin()) {
			String fechaF = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaFin());
			sql.SET("FECHAFIN = '" + fechaF + "'");
		}
		
		if(!perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional().equals("")) {
			sql.SET("SOCIEDADPROFESIONAL = '" + perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional() + "'");
		}
		
		if(null != usuario.getIdusuario() && !usuario.getIdusuario().equals("")) {
			sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		}
		
		if(!perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp().equals("")) {
			sql.SET("PREFIJO_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp() + "'");
		}
		if(!perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp().equals("")) {
			sql.SET("CONTADOR_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp() + "'");
		}
		if(!perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp().equals("")) {
			sql.SET("SUFIJO_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp() + "'");
		}
		
		sql.SET("FECHAMODIFICACION = SYSDATE");
		
		
		sql.WHERE("IDPERSONA = '" + perJuridicaDatosRegistralesUpdateDTO.getIdPersona() + "'");
		sql.WHERE("IDINSTITUCION = '" +idInstitucion+ "'");
		
		return sql.toString();
	}
	
	public String selectSociedadesEliminadas(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		SQL sql = new SQL();
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql.FROM("CEN_NOCOLEGIADO COL");
		
		sql.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql.WHERE("IDINSTITUCION = '" +idInstitucion+ "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("TO_DATE(COL.FECHA_BAJA,'DD/MM/RRRR') >= TO_DATE('" + fechadesde + "', 'DD/MM/RRRR')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("TO_DATE(COL.FECHA_BAJA,'DD/MM/RRRR') < TO_DATE('" + fechahasta + "', 'DD/MM/RRRR')");
		
		
		return sql.toString();
		
	}
	
	public String selectSociedadesEditadas(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		SQL sql = new SQL();

		sql.SELECT("COL.IDPERSONA AS IDPERSONA");
		sql.FROM("CEN_NOCOLEGIADO COL");
		sql.WHERE("IDINSTITUCION = '" +idInstitucion+ "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("TO_DATE(COL.FECHAMODIFICACION,'DD/MM/RRRR') >= TO_DATE('" + fechadesde + "', 'DD/MM/RRRR')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("TO_DATE(COL.FECHAMODIFICACION,'DD/MM/RRRR') < TO_DATE('" + fechahasta + "', 'DD/MM/RRRR')");
		sql.WHERE("FECHA_BAJA IS NULL");
		
		
		return sql.toString();
		
	}
	
	public String selectSociedadesEditar(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		SQL sql = new SQL();

		sql.SELECT("PER.IDPERSONA AS IDPERSONA");
		sql.SELECT("PER.NIFCIF AS SOCIEDADNIF");
		sql.SELECT("PER.NOMBRE AS SOCIEDADDENOMINACION");
		sql.SELECT("SOCIEDAD.TIPO AS SOCIEDADFORMASOCIAL");
		sql.SELECT("PER.FECHANACIMIENTO AS SOCIEDADFECHAALTA ");
		sql.SELECT("SOCIEDAD.FECHA_BAJA AS FECHA_BAJA");
		sql.SELECT("NOTARIO.NIFCIF AS IDENTIFICACIONNOTARIO");
		sql.SELECT("NOTARIO.NOMBRE AS NOMBRENOTARIO");
		sql.SELECT("NOTARIO.APELLIDOS1 AS APELLIDO1NOTARIO");
		sql.SELECT("NOTARIO.APELLIDOS2 AS APELLIDO2NOTARIO");
		sql.SELECT("SOCIEDAD.SOCIEDADPROFESIONAL");
		sql.SELECT("SOCIEDAD.RESENA AS RESENA");
		sql.SELECT("SOCIEDAD.OBJETOSOCIAL AS OBJETOSOCIAL");
		sql.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION ");
		sql.SELECT("SOCIEDAD.FECHAFIN AS FECHACANCELACION ");
		sql.SELECT("SOCIEDAD.PREFIJO_NUMSSPP || SOCIEDAD.CONTADOR_NUMSSPP || SOCIEDAD.SUFIJO_NUMSSPP AS IDENTIFICACIONREGISTRO");
		sql.SELECT("SOCIEDAD.CONTADOR_NUMSSPP AS NUMEROREGISTRO");
		sql.FROM("CEN_NOCOLEGIADO SOCIEDAD");
		sql.INNER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = SOCIEDAD.IDPERSONA");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA NOTARIO ON NOTARIO.IDPERSONA = SOCIEDAD.IDPERSONANOTARIO");
		sql.WHERE("SOCIEDAD.IDINSTITUCION = '" +idInstitucion+ "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("TO_DATE(SOCIEDAD.FECHAMODIFICACION,'DD/MM/RRRR') >= TO_DATE('" + fechadesde + "', 'DD/MM/RRRR')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("TO_DATE(SOCIEDAD.FECHAMODIFICACION,'DD/MM/RRRR') < TO_DATE('" + fechahasta + "', 'DD/MM/RRRR')");
		sql.WHERE("SOCIEDAD.FECHA_BAJA IS NULL");
		
		return sql.toString();
		
	}
}
