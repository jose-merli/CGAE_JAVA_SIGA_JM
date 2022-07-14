package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.DesasociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.cen.PerJuridicaDatosRegistralesUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaActividadDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.SociedadCreateDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenNocolegiadoSqlProvider;

public class CenNocolegiadoSqlExtendsProvider extends CenNocolegiadoSqlProvider {

	public String searchLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String grupos = "";

		sql2.SELECT(" DISTINCT COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql2.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql2.SELECT("TO_CHAR(PER.FECHANACIMIENTO,'DD/MM/YYYY')  AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql2.SELECT("CA.DESCRIPCION AS TIPO");
		sql2.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql2.SELECT("TIPOSOCIEDAD.LETRACIF AS TIPOSOCIEDAD");

		sql2.SELECT("NVL(COUNT(COM.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql2.SELECT("F_SIGA_GETINTEGRANTES(COL.IDINSTITUCION,COL.IDPERSONA)  AS NOMBRESINTEGRANTES");
		
		sql2.FROM("CEN_NOCOLEGIADO COL");
		
		sql2.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql2.INNER_JOIN("CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION");
		sql2.LEFT_OUTER_JOIN("CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON  COL.TIPO = TIPOSOCIEDAD.LETRACIF");
		
		sql2.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '" + idLenguaje + "')");
		sql2.LEFT_OUTER_JOIN("CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA "
		+ "AND (grupos_cliente.fecha_inicio <= SYSDATE  AND ( grupos_cliente.fecha_baja > SYSDATE OR grupos_cliente.fecha_baja IS NULL )))");
		sql2.LEFT_OUTER_JOIN("CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION AND COM.FECHABAJA IS NULL)");
		sql2.LEFT_OUTER_JOIN("CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA PER2 ON PER2.IDPERSONA = COM.CEN_CLIENTE_IDPERSONA");
		
		if(null != idInstitucion && "2000" != idInstitucion) {

			sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");
		}

		sql2.WHERE("COL.FECHA_BAJA IS NULL");
		if (null != busquedaJuridicaSearchDTO.getNif() && !busquedaJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql2.WHERE("(UPPER(PER.NIFCIF) LIKE UPPER  ('%" + busquedaJuridicaSearchDTO.getNif() + "%'))");

		}

		if (busquedaJuridicaSearchDTO.getGrupos() != null && busquedaJuridicaSearchDTO.getGrupos().length > 0) {
			String etiquetas = "";

			for (int i = 0; busquedaJuridicaSearchDTO.getGrupos().length > i; i++) {

				if (i == busquedaJuridicaSearchDTO.getGrupos().length - 1) {
					etiquetas += "( GRUPOS_CLIENTE.IDGRUPO ='" + busquedaJuridicaSearchDTO.getGrupos()[i].getValue()
							+ "' and GRUPOS_CLIENTE.IDINSTITUCION_GRUPO = '"
							+ busquedaJuridicaSearchDTO.getGrupos()[i].getIdInstitucion() + "')";
				} else {
					etiquetas += "( GRUPOS_CLIENTE.IDGRUPO ='" + busquedaJuridicaSearchDTO.getGrupos()[i].getValue()
							+ "' and GRUPOS_CLIENTE.IDINSTITUCION_GRUPO = '"
							+ busquedaJuridicaSearchDTO.getGrupos()[i].getIdInstitucion() + "') or";

				}
			}

			sql2.WHERE("(" + etiquetas + ")");

		}

		if (null != busquedaJuridicaSearchDTO.getFechaConstitucion()) {
			String fechaC = dateFormat.format(busquedaJuridicaSearchDTO.getFechaConstitucion());
			sql2.WHERE("PER.FECHANACIMIENTO = TO_DATE('" + fechaC + "', 'DD/MM/YYYY')");
		}

		if (busquedaJuridicaSearchDTO.getSociedadesProfesionales()) {
			sql2.WHERE("COL.SOCIEDADPROFESIONAL = '1'");
		}

		if (null != busquedaJuridicaSearchDTO.getTipo() && !busquedaJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql2.WHERE("COL.TIPO = '" + busquedaJuridicaSearchDTO.getTipo() + "'");
		} else {
			//sql2.WHERE("COL.TIPO IN('0','A','B','C','D','E','F','G','H','J','P','Q','R','S','U','V')");
		}
		sql2.WHERE("PER.IDTIPOIDENTIFICACION IN ('20')");
		
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
		sql2.GROUP_BY("TIPOSOCIEDAD.LETRACIF");


		// meter subconsulta de objeto sql2 en objeto sql
		sql.SELECT("CONSULTA.*");
		sql.FROM("(" + sql2 + ") consulta");

		
		if (!UtilidadesString.esCadenaVacia(busquedaJuridicaSearchDTO.getDenominacion())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.DENOMINACION",
					busquedaJuridicaSearchDTO.getDenominacion()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaJuridicaSearchDTO.getIntegrante())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.NOMBRESINTEGRANTES",
					busquedaJuridicaSearchDTO.getIntegrante()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaJuridicaSearchDTO.getAbreviatura())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.ABREVIATURA",
					busquedaJuridicaSearchDTO.getAbreviatura()));
		}

		return sql.toString();
	}

	public String searchHistoricLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String grupos = "";
		
		
//		if(busquedaJuridicaSearchDTO.getGrupos().length > 1) {
//			for (String string : busquedaJuridicaSearchDTO.getGrupos()) {
//				grupos += string;
//				grupos += ",";
//			}
//			grupos = grupos.substring(0,grupos.length()-1);
//		}
//		else if(busquedaJuridicaSearchDTO.getGrupos().length == 1){
//			grupos = busquedaJuridicaSearchDTO.getGrupos()[0];
//		}
		

		sql2.SELECT("DISTINCT COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql2.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql2.SELECT("TO_CHAR(PER.FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql2.SELECT("TIPOSOCIEDAD.LETRACIF AS TIPOSOCIEDAD");
		// sql2.SELECT("(select descripcion from GEN_RECURSOS where idrecurso =
		// 'censo.busquedaClientesAvanzada.literal.Sociedad' and idlenguaje = '"+
		// idLenguaje+ "') AS TIPO");
		sql2.SELECT("CA.DESCRIPCION AS TIPO");
		sql2.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql2.SELECT("NVL(COUNT(PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql2.SELECT(
				"LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(DECODE(PER2.APELLIDOS1,'#NA','',PER2.APELLIDOS1) || ' ',PER2.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");

		sql2.FROM("CEN_NOCOLEGIADO COL");
		sql2.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql2.INNER_JOIN("CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION");
		sql2.LEFT_OUTER_JOIN("CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON  COL.TIPO = TIPOSOCIEDAD.LETRACIF");
		sql2.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '"
						+ idLenguaje + "')");
		sql2.LEFT_OUTER_JOIN(
				"CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA "
						+ "AND ( grupos_cliente.fecha_inicio <= SYSDATE  AND ( grupos_cliente.fecha_baja >= SYSDATE OR grupos_cliente.fecha_baja IS NULL )))");
		sql2.LEFT_OUTER_JOIN(
				"CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION AND COM.FECHABAJA IS NULL)");
		sql2.LEFT_OUTER_JOIN(
				"CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA");

		if (null != idInstitucion && "2000" != idInstitucion) {
			sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");
		}

		if(null != busquedaJuridicaSearchDTO.getNif() && !busquedaJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql2.WHERE("(UPPER(PER.NIFCIF) LIKE UPPER  ('%" + busquedaJuridicaSearchDTO.getNif() + "%'))");

		}
		
		if (busquedaJuridicaSearchDTO.getGrupos() != null && busquedaJuridicaSearchDTO.getGrupos().length > 0) {
			String etiquetas = "";

			for (int i = 0; busquedaJuridicaSearchDTO.getGrupos().length > i; i++) {

				if (i == busquedaJuridicaSearchDTO.getGrupos().length - 1) {
					etiquetas += "( GRUPOS_CLIENTE.IDGRUPO ='" + busquedaJuridicaSearchDTO.getGrupos()[i].getValue()
							+ "' and GRUPOS_CLIENTE.IDINSTITUCION_GRUPO = '"
							+ busquedaJuridicaSearchDTO.getGrupos()[i].getIdInstitucion() + "')";
				} else {
					etiquetas += "( GRUPOS_CLIENTE.IDGRUPO ='" + busquedaJuridicaSearchDTO.getGrupos()[i].getValue()
							+ "' and GRUPOS_CLIENTE.IDINSTITUCION_GRUPO = '"
							+ busquedaJuridicaSearchDTO.getGrupos()[i].getIdInstitucion() + "') or";

				}
			}

			sql2.WHERE("(" + etiquetas + ")");

		}
		
		if(null != busquedaJuridicaSearchDTO.getFechaConstitucion()) {
			String fechaC = dateFormat.format(busquedaJuridicaSearchDTO.getFechaConstitucion());
			sql2.WHERE("PER.FECHANACIMIENTO = TO_DATE('" + fechaC + "', 'DD/MM/YYYY')");
		}
		
		if(busquedaJuridicaSearchDTO.getSociedadesProfesionales()) {
			sql2.WHERE("COL.SOCIEDADPROFESIONAL = '1'");
		}
		
		
		if(null != busquedaJuridicaSearchDTO.getTipo() && !busquedaJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql2.WHERE("COL.TIPO = '" + busquedaJuridicaSearchDTO.getTipo() + "'");
		}
		else {
			//sql2.WHERE("COL.TIPO IN('0','A','B','C','D','E','F','G','H','J','P','Q','R','S','U','V')");
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
		sql2.GROUP_BY("TIPOSOCIEDAD.LETRACIF");

		// meter subconsulta de objeto sql2 en objeto sql
		sql.SELECT("CONSULTA.*");

		sql.FROM( "(" + sql2 + ") consulta");
		

		if (!UtilidadesString.esCadenaVacia(busquedaJuridicaSearchDTO.getDenominacion())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.DENOMINACION", busquedaJuridicaSearchDTO.getDenominacion()));
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaJuridicaSearchDTO.getIntegrante())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.NOMBRESINTEGRANTES", busquedaJuridicaSearchDTO.getIntegrante()));
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaJuridicaSearchDTO.getAbreviatura())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.ABREVIATURA", busquedaJuridicaSearchDTO.getAbreviatura()));
		}
		
	
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
		sql.SELECT("TO_CHAR(PER.FECHANACIMIENTO,'DD/MM/YYYY') AS FECHACONSTITUCION");
		sql.SELECT("TO_CHAR(CLIENTESOCIEDAD.FECHAALTA,'DD/MM/YYYY') AS FECHAALTA");
		sql.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql.SELECT("TIPOSOCIEDAD.LETRACIF  AS TIPO");
		sql.SELECT("TIPOSOCIEDAD.LETRACIF  AS TIPOSOCIEDAD");
		sql.SELECT("TO_CHAR(COL.FECHA_BAJA,'DD/MM/YYYY') AS FECHA_BAJA");
		sql.SELECT("NVL(COUNT(DISTINCT PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql.SELECT(
				"LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(DECODE(PER2.APELLIDOS1,'#NA','',PER2.APELLIDOS1) || ' ',PER2.APELLIDOS2)), ';') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");
		sql.SELECT(
				"LISTAGG(GRUPOS_CLIENTE.IDGRUPO, ';') WITHIN GROUP (ORDER BY GRUPOS_CLIENTE.IDGRUPO)  OVER (PARTITION BY COL.IDPERSONA) AS IDGRUPO");
		sql.SELECT("CLIENTESOCIEDAD.IDLENGUAJE AS LENGUAJESOCIEDAD");
		sql.SELECT("CLIENTESOCIEDAD.ASIENTOCONTABLE");
		sql.FROM(" CEN_NOCOLEGIADO COL");
		sql.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA ");
		sql.INNER_JOIN(" CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION ");
		sql.INNER_JOIN(" CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON TIPOSOCIEDAD.LETRACIF = COL.TIPO "
				+ "LEFT JOIN CEN_CLIENTE CLIENTESOCIEDAD ON (COL.IDPERSONA = CLIENTESOCIEDAD.IDPERSONA AND COL.IDINSTITUCION = CLIENTESOCIEDAD.IDINSTITUCION)");
		sql.INNER_JOIN(" GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '"
				+ personaJuridicaSearchDTO.getIdLenguaje() + "') "
				+ "LEFT JOIN CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA)"
				+ "LEFT JOIN CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION )"
				+ "LEFT JOIN CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )"
				+ "LEFT JOIN CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA ");
		sql.WHERE(" I.IDINSTITUCION = '" + personaJuridicaSearchDTO.getIdInstitucion() + "'");
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
		sql.GROUP_BY("CLIENTESOCIEDAD.FECHAALTA");
		sql.GROUP_BY("CLIENTESOCIEDAD.IDLENGUAJE");
		sql.GROUP_BY("CLIENTESOCIEDAD.ASIENTOCONTABLE");

		return sql.toString();
	}

	public String insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario,
			EtiquetaUpdateDTO etiquetaUpdateDTO) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_NOCOLEGIADO");

		sql.VALUES("IDPERSONA", etiquetaUpdateDTO.getIdPersona());
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		// sql.VALUES("SERIE", "");
		// sql.VALUES("NUMEROREF", "");
		sql.VALUES("SOCIEDADSJ", "'0'");
		sql.VALUES("TIPO", "'" + etiquetaUpdateDTO.getTipo() + "'");
		if (null != etiquetaUpdateDTO.getAnotaciones() && !etiquetaUpdateDTO.getAnotaciones().equals("")) {
			sql.VALUES("ANOTACIONES", "'" + etiquetaUpdateDTO.getAnotaciones().replace("'", "''") + "'");
		}	
		
		// sql.VALUES("PREFIJO_NUMREG", "");
		// sql.VALUES("CONTADOR_NUMREG", "");
		// sql.VALUES("SUFIJO_NUMREG", "");
		// sql.VALUES("FECHAFIN", "");
		// sql.VALUES("IDPERSONANOTARIO", "");
		// sql.VALUES("RESENA", "");
		// sql.VALUES("OBJETOSOCIAL", "");
		sql.VALUES("SOCIEDADPROFESIONAL", "'0'");
		// sql.VALUES("PREFIJO_NUMSSPP", "");
		// sql.VALUES("CONTADOR_NUMSSPP", "");
		// sql.VALUES("SUFIJO_NUMSSPP", "");
		// sql.VALUES("NOPOLIZA", "");
		// sql.VALUES("COMPANIASEG", "");
		// sql.VALUES("IDENTIFICADORDS", "");
		sql.VALUES("FECHA_BAJA", "null");

		return sql.toString();
	}

	public String selectProfesionalActivitiesSociety(PersonaJuridicaActividadDTO personaJuridicaActividadDTO,
			String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("NOCOL_ACT.IDACTIVIDADPROFESIONAL");
		sql.SELECT("REC.DESCRIPCION");
		sql.FROM("CEN_NOCOLEGIADO_ACTIVIDAD NOCOL_ACT");
		sql.INNER_JOIN(
				"CEN_ACTIVIDADPROFESIONAL ACTIVIDAD ON (ACTIVIDAD.IDACTIVIDADPROFESIONAL = NOCOL_ACT.IDACTIVIDADPROFESIONAL )");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = ACTIVIDAD.DESCRIPCION AND REC.IDLENGUAJE = '"
				+ idLenguaje + "')");
		sql.WHERE("NOCOL_ACT.IDPERSONA = '" + personaJuridicaActividadDTO.getIdPersona() + "' ");
		sql.WHERE("NOCOL_ACT.IDINSTITUCION = '" + personaJuridicaActividadDTO.getIdInstitucion() + "'");
		sql.WHERE("NOCOL_ACT.FECHA_BAJA IS NULL");

		return sql.toString();
	}

	public String selectRetenciones(PersonaSearchDTO personaSearchDto, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("PER.IDPERSONA AS IDPERSONA");
		sql.SELECT("TO_CHAR(IRPF.FECHAINICIO, 'DD/MM/YYYY') AS FECHAINICIO");
		sql.SELECT("TO_CHAR(IRPF.FECHAFIN, 'DD/MM/YYYY') AS FECHAFIN");
		sql.SELECT("IRPF.IDRETENCION AS IDRETENCION");
		sql.SELECT("RET.RETENCION AS RETENCION");
		sql.SELECT("RET.DESCRIPCION AS RECURSO");
		sql.SELECT("CAT.DESCRIPCION AS DESCRIPCION");
		sql.FROM("CEN_NOCOLEGIADO NOCOL");
		sql.INNER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = NOCOL.IDPERSONA");
		sql.INNER_JOIN(
				"SCS_RETENCIONESIRPF IRPF ON (IRPF.IDPERSONA = NOCOL.IDPERSONA AND IRPF.IDINSTITUCION = NOCOL.IDINSTITUCION)");
		sql.INNER_JOIN("SCS_MAESTRORETENCIONES RET ON RET.IDRETENCION = IRPF.IDRETENCION");

		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = RET.DESCRIPCION AND CAT.IDLENGUAJE = '" + idLenguaje + "')");

		sql.WHERE("PER.IDPERSONA = '" + personaSearchDto.getIdPersona() + "'");
		if(idInstitucion != "2000") {
			sql.WHERE("IRPF.IDINSTITUCION = '" + idInstitucion + "'");
		}
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
		sql.WHERE("IDINSTITUCION = '" + desasociarPersona.getIdInstitucion() + "'");

		return sql.toString();
	}

	public String updateByExampleDataLegalPerson(
			PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, String idInstitucion,
			AdmUsuarios usuario) {
		SQL sql = new SQL();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		sql.UPDATE("cen_nocolegiado");
			sql.SET("RESENA = '" + perJuridicaDatosRegistralesUpdateDTO.getResena().replace("'", "''") + "'");
			sql.SET("OBJETOSOCIAL = '" + perJuridicaDatosRegistralesUpdateDTO.getObjetoSocial().replace("'", "''") + "'");
			sql.SET("NOPOLIZA = '" + perJuridicaDatosRegistralesUpdateDTO.getNumeroPoliza().replace("'", "''") + "'");
			sql.SET("COMPANIASEG = '" + perJuridicaDatosRegistralesUpdateDTO.getCompaniaAseg().replace("'", "''") + "'");
			
		if(null != perJuridicaDatosRegistralesUpdateDTO.getFechaFin()) {
			String fechaF = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaFin());
			sql.SET("FECHAFIN = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		}else {
			sql.SET("FECHAFIN = " + perJuridicaDatosRegistralesUpdateDTO.getFechaFin());
		}
		/*if(null != perJuridicaDatosRegistralesUpdateDTO.getFechaBaja()) {
			String fechaB = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaBaja());
			sql.SET("FECHA_BAJA = TO_DATE('" + fechaB + "','DD/MM/YYYY')");
		}*/
		
		if(!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional())) {
			sql.SET("SOCIEDADPROFESIONAL = '" + perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional() + "'");
		}
		
		if(null != usuario.getIdusuario()) {
			sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		}
		
		if(null != perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro()) {
			sql.SET("ID_DATOS_REG = '" + perJuridicaDatosRegistralesUpdateDTO.getIdDatosRegistro() + "'");
		}
		if(!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp())) {
			sql.SET("PREFIJO_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp().replace("'", "''") + "'");
		}else {
			sql.SET("PREFIJO_NUMSSPP = ''");
		}
		if(!UtilidadesString.esCadenaVacia(perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp())) {
			sql.SET("CONTADOR_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp().replace("'", "''") + "'");
		}else {
			sql.SET("CONTADOR_NUMSSPP = ''");
		}
		if(null!=perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp() && !perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp().equals("")) {
			sql.SET("SUFIJO_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp().replace("'", "''") + "'");
		}else {
			sql.SET("SUFIJO_NUMSSPP = ''");
		}
		
		sql.SET("FECHAMODIFICACION = SYSDATE");
		
		
		sql.WHERE("IDPERSONA = '" + perJuridicaDatosRegistralesUpdateDTO.getIdPersona() + "'");
		sql.WHERE("IDINSTITUCION = '" +idInstitucion+ "'");
		
		return sql.toString();
	}

	public String selectSociedadesEliminadas(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		SQL sql = new SQL();
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql.FROM("CEN_NOCOLEGIADO COL");

		sql.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("COL.FECHA_BAJA >= TO_DATE('" + fechadesde + "', 'DD/MM/YYYY hh24:mi:ss')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("COL.FECHA_BAJA < TO_DATE('" + fechahasta + "', 'DD/MM/YYYY hh24:mi:ss')");
		
		sql.WHERE("COL.SOCIEDADPROFESIONAL = '1'");

		return sql.toString();

	}

	public String selectSociedadesEditadas(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SQL sql = new SQL();

		sql.SELECT("COL.IDPERSONA AS IDPERSONA");
		sql.FROM("CEN_NOCOLEGIADO COL");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("COL.FECHAMODIFICACION >= TO_DATE('" + fechadesde + "', 'DD/MM/YYYY')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("COL.FECHAMODIFICACION < TO_DATE('" + fechahasta + "', 'DD/MM/YYYY')");
		sql.WHERE("FECHA_BAJA IS NULL");

		return sql.toString();

	}

	public String selectSociedadesEditar(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SQL sql = new SQL();

		sql.SELECT("PER.IDPERSONA AS IDPERSONA");
		sql.SELECT("PER.NIFCIF AS SOCIEDADNIF");
		sql.SELECT("PER.NOMBRE AS SOCIEDADDENOMINACION");
		sql.SELECT("F_SIGA_GETRECURSO(ts.descripcion, 1) AS SOCIEDADFORMASOCIAL");
		sql.SELECT("ClI.FECHAALTA AS SOCIEDADFECHAALTA");
		sql.SELECT("SOCIEDAD.FECHA_BAJA AS FECHA_BAJA");
		sql.SELECT("SOCIEDAD.SOCIEDADPROFESIONAL");
		sql.SELECT("SOCIEDAD.RESENA AS RESENA");
		sql.SELECT("SOCIEDAD.OBJETOSOCIAL AS OBJETOSOCIAL");
		sql.SELECT("SOCIEDAD.FECHAFIN AS FECHAFIN ");
		sql.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION ");
		sql.SELECT("DIR.DOMICILIO");
		sql.SELECT("DIR.CODIGOPOSTAL");
		sql.SELECT("DIR.IDPROVINCIA AS CODIGOPROVINCIA");
		sql.SELECT("PROVINCIAS.NOMBRE AS PROVINCIA");
		sql.SELECT("POBLACIONES.NOMBRE AS POBLACION");
		sql.SELECT("POBLACIONES.CODIGOEXT AS CODIGOPOBLACION");
		sql.SELECT("DIR.CORREOELECTRONICO");
		sql.SELECT("DIR.FAX1");
		sql.SELECT("DIR.FAX2");
		sql.SELECT("DIR.MOVIL");
		sql.SELECT("DIR.PAGINAWEB");
		sql.SELECT("DIR.TELEFONO1");
		sql.SELECT("DIR.TELEFONO2");
		sql.SELECT("DECODE (NOTARIO.IDTIPOIDENTIFICACION, 10, 'NIF', 40, 'NIE', NULL) AS TIPOIDENTIFICACION");
		sql.SELECT("NOTARIO.NIFCIF AS IDENTIFICACIONNOTARIO");
		sql.SELECT("NOTARIO.NOMBRE AS NOMBRENOTARIO");
		sql.SELECT("NOTARIO.APELLIDOS1 AS APELLIDO1NOTARIO");
		sql.SELECT("NOTARIO.APELLIDOS2 AS APELLIDO2NOTARIO");
		sql.SELECT("REG.NUM_REGISTRO AS NUMEROREGISTRO");
		sql.SELECT("REG.IDENTIFICACION_REG AS IDENTIFICACIONREGISTRO");
		sql.SELECT("REG.FECHA_INSCRIPCION AS FECHAINSCRIPCION");
		sql.SELECT("REG.FECHA_CANCELACION AS FECHACANCELACION");
		
		sql.FROM("CEN_NOCOLEGIADO SOCIEDAD");
		sql.INNER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = SOCIEDAD.IDPERSONA");
		sql.INNER_JOIN("CEN_CLIENTE CLI ON CLI.IDPERSONA = SOCIEDAD.IDPERSONA AND SOCIEDAD.IDINSTITUCION =CLI.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA NOTARIO ON NOTARIO.IDPERSONA = SOCIEDAD.IDPERSONANOTARIO");
		sql.INNER_JOIN("CEN_TIPOSOCIEDAD TS ON SOCIEDAD.TIPO = TS.LETRACIF");
		sql.LEFT_OUTER_JOIN("CEN_DIRECCIONES DIR ON (SOCIEDAD.IDPERSONA = DIR.IDPERSONA and dir.IDINSTITUCION = '"+ idInstitucion +"' AND DIR.FECHABAJA IS NULL AND dir.iddireccion IN (SELECT TPDIRECCION.iddireccion from CEN_DIRECCION_TIPODIRECCION TPDIRECCION where  TPDIRECCION.IDPERSONA = SOCIEDAD.IDPERSONA and TPDIRECCION.IDINSTITUCION = '"+idInstitucion+"' and DIR.iddireccion= TPDIRECCION.iddireccion AND TPDIRECCION.IDTIPODIRECCION = 3))");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACIONES ON DIR.IDPOBLACION = POBLACIONES.IDPOBLACION");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.CODIGOEXT = DIR.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_REG_MERCANTIL REG ON REG.ID_DATOS_REG = SOCIEDAD.ID_DATOS_REG");
		
		sql.WHERE("SOCIEDAD.IDINSTITUCION = '" +idInstitucion+ "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("SOCIEDAD.FECHAMODIFICACION >= TO_DATE('" + fechadesde + "', 'DD/MM/YYYY')");
		//TODO revisar este fechahasta
		//String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("SOCIEDAD.FECHAMODIFICACION < SYSDATE +1");
		sql.WHERE("SOCIEDAD.FECHA_BAJA IS NULL");
		sql.WHERE("SOCIEDAD.SOCIEDADPROFESIONAL = 1");
		
		sql.ORDER_BY("PER.IDPERSONA");

		return sql.toString();

	}

	public String insertSelectiveForCreateNewSociety(String idInstitucion, AdmUsuarios usuario,
			SociedadCreateDTO sociedadCreateDTO) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_NOCOLEGIADO");

		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona where idpersona like '" + idInstitucion + "' || '%')");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		// sql.VALUES("SERIE", "");
		// sql.VALUES("NUMEROREF", "");
		sql.VALUES("SOCIEDADSJ", "'0'");
		sql.VALUES("TIPO", "'" + sociedadCreateDTO.getTipo() + "'");
		if (null != sociedadCreateDTO.getAnotaciones() && !sociedadCreateDTO.getAnotaciones().equals("")) {
			sql.VALUES("ANOTACIONES", "'" + sociedadCreateDTO.getAnotaciones().replace("'", "''") + "'");
		}

		// sql.VALUES("PREFIJO_NUMREG", "");
		// sql.VALUES("CONTADOR_NUMREG", "");
		// sql.VALUES("SUFIJO_NUMREG", "");
		// sql.VALUES("FECHAFIN", "");
		// sql.VALUES("IDPERSONANOTARIO", "");
		// sql.VALUES("RESENA", "");
		// sql.VALUES("OBJETOSOCIAL", "");
		sql.VALUES("SOCIEDADPROFESIONAL", "'0'");
		// sql.VALUES("PREFIJO_NUMSSPP", "");
		// sql.VALUES("CONTADOR_NUMSSPP", "");
		// sql.VALUES("SUFIJO_NUMSSPP", "");
		// sql.VALUES("NOPOLIZA", "");
		// sql.VALUES("COMPANIASEG", "");
		// sql.VALUES("IDENTIFICADORDS", "");
		sql.VALUES("FECHA_BAJA", "null");

		return sql.toString();
	}

	public String selectNoColegiados(Short idInstitucion, NoColegiadoItem noColegiadoItem) {

		SQL sql = new SQL();
		//SQL sql1 = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados, tendremos que preparar el filtro de instituciones
//		String instituciones = "";
//		if (noColegiadoItem.getColegio() != null && noColegiadoItem.getColegio().length > 0) {
//			if (noColegiadoItem.getColegio().length > 1) {
//				for (String string : noColegiadoItem.getColegio()) {
//					instituciones += "'" + string + "'";
//					instituciones += ",";
//				}
//				instituciones = instituciones.substring(0, instituciones.length() - 1);
//			} else if (noColegiadoItem.getColegio().length == 1) {
//				instituciones = "'" + noColegiadoItem.getColegio()[0] + "'";
//			}
//		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("nocol.idpersona");
		sql.SELECT_DISTINCT("nocol.idinstitucion");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(concat(per.apellidos1 || ' ', concat(per.apellidos2 , ', ')), per.nombre || ' ') AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("cli.NOAPARECERREDABOGACIA");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("cli.idtratamiento");
		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("cli.publicidad");
		sql.SELECT_DISTINCT("cli.guiajudicial");
		sql.SELECT_DISTINCT("per.fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");
		sql.SELECT_DISTINCT("dir.movil as movil");
		sql.SELECT_DISTINCT("TO_CHAR(nocol.fecha_baja, 'DD/MM/YYYY') AS fechaBaja");
		sql.SELECT("inst.abreviatura as colegioResultado");
		sql.FROM("cen_nocolegiado nocol");

		sql.INNER_JOIN("cen_persona per on nocol.idpersona = per.idpersona");
		sql.INNER_JOIN(
				"cen_cliente cli on (nocol.idpersona = cli.idpersona and nocol.idinstitucion = cli.idinstitucion)");
		sql.INNER_JOIN("cen_institucion inst on nocol.idinstitucion = inst.idinstitucion");
		if (noColegiadoItem.getIdGrupo() != null && noColegiadoItem.getIdGrupo().length > 0) {
		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    (grucli.idinstitucion = inst.idinstitucion and nocol.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja >= SYSDATE OR grucli.fecha_baja IS NULL)))");
		}
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");
		
		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		if ((noColegiadoItem.getTipoCV() != null && noColegiadoItem.getTipoCV() != "") || (noColegiadoItem.getSubTipoCV1() != null && noColegiadoItem.getSubTipoCV1() != "") || (noColegiadoItem.getSubTipoCV2() != null && noColegiadoItem.getSubTipoCV2() != "")) {
			sql.LEFT_OUTER_JOIN("cen_datosCV datosCV ON ( datosCV.idInstitucion = nocol.idInstitucion and datosCV.idPersona = per.idPersona )");
			sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = nocol.idInstitucion )");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = nocol.idInstitucion )");
		}
		
		sql.WHERE("NOCOL.IDINSTITUCION = '" + idInstitucion + "'");
		//sql.WHERE("per.idtipoidentificacion not in '20'");
		sql.WHERE("(dir.fechamodificacion = (select max(fechamodificacion) from cen_direcciones dir2 where dir2.idpersona = dir.idpersona and dir2.idinstitucion = dir.idinstitucion and dir2.idinstitucion = dir.idinstitucion and dir2.fechabaja is null ) or dir.fechamodificacion is null)");
		sql.WHERE("per.idtipoidentificacion not in '20'");
		if(!noColegiadoItem.isHistorico()) {
			sql.WHERE("NOCOL.FECHA_BAJA is NULL");
		}
		
		if (noColegiadoItem.getNif() != null && noColegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + noColegiadoItem.getNif() + "%')");
		}
//		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
//			sql.WHERE("upper(per.nombre) like upper('%" + noColegiadoItem.getNombre() + "%')");
//		}
//		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" + noColegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
//		}
		
		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
			String columna = "per.nombre";
			String cadena = noColegiadoItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}
		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
			String columna = "REPLACE(CONCAT(per.apellidos1,per.apellidos2), ' ', '')";
			String cadena = noColegiadoItem.getApellidos().replaceAll("\\s+","%"); 
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}			
		if (noColegiadoItem.getSexo() != null && noColegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + noColegiadoItem.getSexo() + "'");
		}
		if (noColegiadoItem.getTipoDireccion() != null && noColegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = "+ noColegiadoItem.getTipoDireccion());
		}
		if (noColegiadoItem.getidEstadoCivil() != null && noColegiadoItem.getidEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + noColegiadoItem.getidEstadoCivil() + "'");
		}
		if (noColegiadoItem.getCodigoPostal() != null && noColegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + noColegiadoItem.getCodigoPostal() + "'");
		}
		if (noColegiadoItem.getIdProvincia() != null && noColegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + noColegiadoItem.getIdProvincia() + "'");
		}
		if (noColegiadoItem.getIdPoblacion() != null && noColegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + noColegiadoItem.getIdPoblacion() + "'");
		}
		if (noColegiadoItem.getDomicilio() != null && noColegiadoItem.getDomicilio() != "") {
			sql.WHERE("(dir.domicilio) like upper('" + noColegiadoItem.getDomicilio() + "')");
		}
		if (noColegiadoItem.getCorreo() != null && noColegiadoItem.getCorreo() != "") {
			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + noColegiadoItem.getCorreo() + "%')");
		}
		if (noColegiadoItem.getTelefono() != null && noColegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + noColegiadoItem.getTelefono() + "%'");
		}
		if (noColegiadoItem.getMovil() != null && noColegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + noColegiadoItem.getMovil() + "%'");
		}
		if (noColegiadoItem.getIdGrupo() != null && noColegiadoItem.getIdGrupo().length > 0) {

		String etiquetas = "";
			
			for (int i = 0; noColegiadoItem.getIdGrupo().length > i; i++) {

				if (i == noColegiadoItem.getIdGrupo().length - 1) {
					etiquetas += "( grucli.IDGRUPO ='" + noColegiadoItem.getIdGrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + noColegiadoItem.getIdGrupo()[i].getIdInstitucion() + "')";
				} else {
					etiquetas += "( grucli.IDGRUPO ='" + noColegiadoItem.getIdGrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + noColegiadoItem.getIdGrupo()[i].getIdInstitucion() + "') or";

				}
			}
			

			sql.WHERE("(" + etiquetas + ")");
		}
		
		if (noColegiadoItem.getTipoCV() != null && noColegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idcv = '" + noColegiadoItem.getTipoCV() + "'");
		}

		if (noColegiadoItem.getSubTipoCV1() != null && noColegiadoItem.getSubTipoCV1() != "") {
			sql.WHERE("subt1.idtipocvsubtipo1 = '" + noColegiadoItem.getSubTipoCV1() + "'");
		}
		
		if (noColegiadoItem.getSubTipoCV2() != null && noColegiadoItem.getSubTipoCV2() != "") {
			sql.WHERE("subt2.idtipocvsubtipo2 = '" + noColegiadoItem.getSubTipoCV2() + "'");
		}
				
		if (noColegiadoItem.getFechaNacimientoRango() != null && noColegiadoItem.getFechaNacimientoRango().length != 0) {

			if (noColegiadoItem.getFechaNacimientoRango()[0] != null && noColegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoDesde = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[0]);
				String getFechaNacimientoHasta = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY') " + " and ( TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') <= TO_DATE('"
						+ getFechaNacimientoHasta + "','DD/MM/YYYY')))");

			} else if (noColegiadoItem.getFechaNacimientoRango()[0] != null
					&& noColegiadoItem.getFechaNacimientoRango()[1] == null) {

				String getFechaNacimientoDesde = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[0]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY'))");

			} else if (noColegiadoItem.getFechaNacimientoRango()[0] == null
					&& noColegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoHasta = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("( TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') <= TO_DATE('" + getFechaNacimientoHasta
						+ "','DD/MM/YYYY'))");
			}
		}
		
		sql.ORDER_BY("nocol.IDPERSONA, NOCOL.IDINSTITUCION");
		
		sql2.SELECT("CONSULTA.*, ROW_NUMBER() OVER(PARTITION BY concat(CONSULTA.idpersona,CONSULTA.idinstitucion) ORDER BY CONSULTA.idpersona) AS RN");
		sql2.FROM("(" + sql + ") CONSULTA");
//		sql2.WHERE("rownum < 5000");
		
		sql3.SELECT("*");
		sql3.FROM("(" + sql2 + ")");
		sql3.WHERE("RN = 1");
		return sql3.toString();
	}
	
	public String searchHistoricNoColegiado(NoColegiadoItem noColegiadoItem, String idLenguaje,
			String idInstitucion) {

		SQL sql = new SQL();
		//SQL sql1 = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados, tendremos que preparar el filtro de instituciones
//		String instituciones = "";
//		if (noColegiadoItem.getColegio() != null && noColegiadoItem.getColegio().length > 0) {
//			if (noColegiadoItem.getColegio().length > 1) {
//				for (String string : noColegiadoItem.getColegio()) {
//					instituciones += "'" + string + "'";
//					instituciones += ",";
//				}
//				instituciones = instituciones.substring(0, instituciones.length() - 1);
//			} else if (noColegiadoItem.getColegio().length == 1) {
//				instituciones = "'" + noColegiadoItem.getColegio()[0] + "'";
//			}
//		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("nocol.idpersona");
		sql.SELECT_DISTINCT("nocol.idinstitucion");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(concat(per.apellidos1 || ' ', concat(per.apellidos2 , ', ')), per.nombre || ' ') AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("cli.NOAPARECERREDABOGACIA");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("cli.idtratamiento");
		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("cli.publicidad");
		sql.SELECT_DISTINCT("cli.guiajudicial");
		sql.SELECT_DISTINCT("per.fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");
		sql.SELECT_DISTINCT("dir.movil as movil");
		sql.SELECT_DISTINCT("TO_CHAR(nocol.fecha_baja, 'DD/MM/YYYY') AS fechaBaja");
		sql.SELECT("inst.abreviatura as colegioResultado");
		sql.FROM("cen_nocolegiado nocol");

		sql.INNER_JOIN("cen_persona per on nocol.idpersona = per.idpersona");
		sql.INNER_JOIN(
				"cen_cliente cli on (nocol.idpersona = cli.idpersona and nocol.idinstitucion = cli.idinstitucion)");
		sql.INNER_JOIN("cen_institucion inst on nocol.idinstitucion = inst.idinstitucion");
		if (noColegiadoItem.getIdGrupo() != null && noColegiadoItem.getIdGrupo().length > 0) {
		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    (grucli.idinstitucion = inst.idinstitucion and nocol.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja >= SYSDATE OR grucli.fecha_baja IS NULL)))");
		}
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");
		
		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		if ((noColegiadoItem.getTipoCV() != null && noColegiadoItem.getTipoCV() != "") || (noColegiadoItem.getSubTipoCV1() != null && noColegiadoItem.getSubTipoCV1() != "") || (noColegiadoItem.getSubTipoCV2() != null && noColegiadoItem.getSubTipoCV2() != "")) {
			sql.LEFT_OUTER_JOIN("cen_datosCV datosCV ON ( datosCV.idInstitucion = nocol.idInstitucion and datosCV.idPersona = per.idPersona )");
			sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = nocol.idInstitucion )");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = nocol.idInstitucion )");
		}
		
		sql.WHERE("NOCOL.IDINSTITUCION = '" + idInstitucion + "'");
		//sql.WHERE("per.idtipoidentificacion not in '20'");
		sql.WHERE("dir.fechamodificacion = (select max(fechamodificacion) from cen_direcciones dir2 where dir2.idpersona = dir.idpersona and dir2.idinstitucion = dir.idinstitucion and dir2.idinstitucion = dir.idinstitucion and dir.fechabaja is null )");
		if (noColegiadoItem.getNif() != null && noColegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + noColegiadoItem.getNif() + "%')");
		}
//		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
//			sql.WHERE("upper(per.nombre) like upper('%" + noColegiadoItem.getNombre() + "%')");
//		}
//		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" + noColegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
//		}
		
		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
			String columna = "per.nombre";
			String cadena = noColegiadoItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}
		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
			String columna = "CONCAT(per.apellidos1,per.apellidos2)";
			String cadena = noColegiadoItem.getApellidos().replaceAll("\\s+","%");
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}			
		if (noColegiadoItem.getSexo() != null && noColegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + noColegiadoItem.getSexo() + "'");
		}
		if (noColegiadoItem.getTipoDireccion() != null && noColegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = "+ noColegiadoItem.getTipoDireccion());
		}
		if (noColegiadoItem.getidEstadoCivil() != null && noColegiadoItem.getidEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + noColegiadoItem.getidEstadoCivil() + "'");
		}
		if (noColegiadoItem.getCodigoPostal() != null && noColegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + noColegiadoItem.getCodigoPostal() + "'");
		}
		if (noColegiadoItem.getIdProvincia() != null && noColegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + noColegiadoItem.getIdProvincia() + "'");
		}
		if (noColegiadoItem.getIdPoblacion() != null && noColegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + noColegiadoItem.getIdPoblacion() + "'");
		}
		if (noColegiadoItem.getDomicilio() != null && noColegiadoItem.getDomicilio() != "") {
			sql.WHERE("(dir.domicilio) like upper('" + noColegiadoItem.getDomicilio() + "')");
		}
		if (noColegiadoItem.getCorreo() != null && noColegiadoItem.getCorreo() != "") {
			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + noColegiadoItem.getCorreo() + "%')");
		}
		if (noColegiadoItem.getTelefono() != null && noColegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + noColegiadoItem.getTelefono() + "%'");
		}
		if (noColegiadoItem.getMovil() != null && noColegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + noColegiadoItem.getMovil() + "%'");
		}
		if (noColegiadoItem.getIdGrupo() != null && noColegiadoItem.getIdGrupo().length > 0) {

		String etiquetas = "";
			
			for (int i = 0; noColegiadoItem.getIdGrupo().length > i; i++) {

				if (i == noColegiadoItem.getIdGrupo().length - 1) {
					etiquetas += "( grucli.IDGRUPO ='" + noColegiadoItem.getIdGrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + noColegiadoItem.getIdGrupo()[i].getIdInstitucion() + "')";
				} else {
					etiquetas += "( grucli.IDGRUPO ='" + noColegiadoItem.getIdGrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + noColegiadoItem.getIdGrupo()[i].getIdInstitucion() + "') or";

				}
			}
			

			sql.WHERE("(" + etiquetas + ")");
		}
		
		if (noColegiadoItem.getTipoCV() != null && noColegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idcv = '" + noColegiadoItem.getTipoCV() + "'");
		}

		if (noColegiadoItem.getSubTipoCV1() != null && noColegiadoItem.getSubTipoCV1() != "") {
			sql.WHERE("subt1.idtipocvsubtipo1 = '" + noColegiadoItem.getSubTipoCV1() + "'");
		}
		
		if (noColegiadoItem.getSubTipoCV2() != null && noColegiadoItem.getSubTipoCV2() != "") {
			sql.WHERE("subt2.idtipocvsubtipo2 = '" + noColegiadoItem.getSubTipoCV2() + "'");
		}
				
		if (noColegiadoItem.getFechaNacimientoRango() != null && noColegiadoItem.getFechaNacimientoRango().length != 0) {

			if (noColegiadoItem.getFechaNacimientoRango()[0] != null && noColegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoDesde = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[0]);
				String getFechaNacimientoHasta = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY') " + " and ( TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') <= TO_DATE('"
						+ getFechaNacimientoHasta + "','DD/MM/YYYY')))");

			} else if (noColegiadoItem.getFechaNacimientoRango()[0] != null
					&& noColegiadoItem.getFechaNacimientoRango()[1] == null) {

				String getFechaNacimientoDesde = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[0]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY'))");

			} else if (noColegiadoItem.getFechaNacimientoRango()[0] == null
					&& noColegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoHasta = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("( TO_CHAR(per.fechanacimiento, 'DD/MM/YYYY') <= TO_DATE('" + getFechaNacimientoHasta
						+ "','DD/MM/YYYY'))");
			}
		}
		
		sql.ORDER_BY("nocol.IDPERSONA, NOCOL.IDINSTITUCION");
		
		sql2.SELECT("CONSULTA.*, ROW_NUMBER() OVER(PARTITION BY concat(CONSULTA.idpersona,CONSULTA.idinstitucion) ORDER BY CONSULTA.idpersona) AS RN");
		sql2.FROM("(" + sql + ") CONSULTA");
//		sql2.WHERE("rownum < 5000");
		
		sql3.SELECT("*");
		sql3.FROM("(" + sql2 + ")");
		sql3.WHERE("RN = 1");
		return sql3.toString();
	}
	
	public String selectColegiacionesIdPersona(Long idPersona) {

		SQL sql = new SQL();


		sql.SELECT("col.idinstitucion as IDINSTITUCION");
		
		sql.FROM("cen_nocolegiado col");
		
		sql.WHERE("col.idpersona = '"+ idPersona +"'");
		
	
		return sql.toString();
	}
	
	public String selectNoColegiadosByIdPersona(Short idInstitucion, String idPersona) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("nocol.idpersona");
		sql.SELECT_DISTINCT("nocol.idinstitucion");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(concat(per.apellidos1 || ' ', concat(per.apellidos2 , ', ')), per.nombre || ' ') AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("cli.NOAPARECERREDABOGACIA");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("cli.idtratamiento");
		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("cli.publicidad");
		sql.SELECT_DISTINCT("cli.guiajudicial");
		sql.SELECT_DISTINCT("per.fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");
		sql.SELECT_DISTINCT("dir.movil");
		sql.SELECT_DISTINCT("TO_CHAR(nocol.fecha_baja, 'DD/MM/YYYY') AS fechaBaja");
		sql.FROM("cen_nocolegiado nocol");

		sql.INNER_JOIN("cen_persona per on nocol.idpersona = per.idpersona");
		sql.INNER_JOIN(
				"cen_cliente cli on (nocol.idpersona = cli.idpersona and nocol.idinstitucion = cli.idinstitucion)");
		sql.INNER_JOIN("cen_institucion inst on nocol.idinstitucion = inst.idinstitucion");

		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    (grucli.idinstitucion = inst.idinstitucion and nocol.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja >= SYSDATE OR grucli.fecha_baja IS NULL)))");
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");
		
		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		sql.LEFT_OUTER_JOIN("cen_datosCV datosCV ON ( datosCV.idInstitucion = nocol.idInstitucion and datosCV.idPersona = per.idPersona )");
		sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = nocol.idInstitucion )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = nocol.idInstitucion )");

		if(idInstitucion != null) {
			sql.WHERE("NOCOL.IDINSTITUCION = '" + idInstitucion + "'");
		}
		//sql.WHERE("per.idtipoidentificacion not in '20'");

		sql.WHERE("per.idPersona = '" + idPersona + "'");
			
		return sql.toString();
	}
	
	public String selectCliente(Short idInstitucion, NoColegiadoItem noColegiadoItem) {

		SQL sql = new SQL();

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados, tendremos que preparar el filtro de instituciones
//		String instituciones = "";
//		if (noColegiadoItem.getColegio() != null && noColegiadoItem.getColegio().length > 0) {
//			if (noColegiadoItem.getColegio().length > 1) {
//				for (String string : noColegiadoItem.getColegio()) {
//					instituciones += "'" + string + "'";
//					instituciones += ",";
//				}
//				instituciones = instituciones.substring(0, instituciones.length() - 1);
//			} else if (noColegiadoItem.getColegio().length == 1) {
//				instituciones = "'" + noColegiadoItem.getColegio()[0] + "'";
//			}
//		}
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("nocol.idpersona");
		sql.SELECT_DISTINCT("nocol.idinstitucion");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ',per.apellidos2) ) AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("nocol.NOAPARECERREDABOGACIA");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("nocol.idlenguaje");
		sql.SELECT_DISTINCT("nocol.asientocontable");
		sql.SELECT_DISTINCT("nocol.idtratamiento");
		sql.SELECT_DISTINCT("nocol.comisiones");
		sql.SELECT_DISTINCT("nocol.publicidad");
		sql.SELECT_DISTINCT("nocol.guiajudicial");
		sql.SELECT_DISTINCT("per.fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");
		sql.SELECT_DISTINCT("dir.movil");
		sql.SELECT("inst.abreviatura as colegioResultado");
		sql.FROM("cen_cliente nocol");

		sql.INNER_JOIN("cen_persona per on nocol.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_institucion inst on nocol.idinstitucion = inst.idinstitucion");

		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    (grucli.idinstitucion = inst.idinstitucion and nocol.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja >= SYSDATE OR grucli.fecha_baja IS NULL)))");
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (nocol.idpersona = dir.idpersona and nocol.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion)");
		
		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (nocol.IDPERSONA = TIPODIR.IDPERSONA AND"  
                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND nocol.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		sql.LEFT_OUTER_JOIN("cen_datosCV datosCV ON ( datosCV.idInstitucion = nocol.idInstitucion and datosCV.idPersona = per.idPersona )");
		sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = nocol.idInstitucion )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = nocol.idInstitucion )");

		
		sql.WHERE("NOCOL.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("per.idtipoidentificacion not in '20'");

		
		if (noColegiadoItem.getNif() != null && noColegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + noColegiadoItem.getNif() + "%')");
		}
//		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
//			sql.WHERE("upper(per.nombre) like upper('%" + noColegiadoItem.getNombre() + "%')");
//		}
//		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" + noColegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
//		}
		
		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
			String columna = "per.nombre";
			String cadena = noColegiadoItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}
		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
			String columna = "CONCAT(per.apellidos1,per.apellidos2)";
			String cadena = noColegiadoItem.getApellidos().replaceAll("\\s+","%");
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}			
		if (noColegiadoItem.getSexo() != null && noColegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + noColegiadoItem.getSexo() + "'");
		}
		if (noColegiadoItem.getTipoDireccion() != null && noColegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = "+ noColegiadoItem.getTipoDireccion());
		}
		if (noColegiadoItem.getidEstadoCivil() != null && noColegiadoItem.getidEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + noColegiadoItem.getidEstadoCivil() + "'");
		}
		if (noColegiadoItem.getCodigoPostal() != null && noColegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + noColegiadoItem.getCodigoPostal() + "'");
		}
		if (noColegiadoItem.getIdProvincia() != null && noColegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + noColegiadoItem.getIdProvincia() + "'");
		}
		if (noColegiadoItem.getIdPoblacion() != null && noColegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + noColegiadoItem.getIdPoblacion() + "'");
		}
		if (noColegiadoItem.getDomicilio() != null && noColegiadoItem.getDomicilio() != "") {
			sql.WHERE("(dir.domicilio) like upper('" + noColegiadoItem.getDomicilio() + "')");
		}
		if (noColegiadoItem.getCorreo() != null && noColegiadoItem.getCorreo() != "") {
			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + noColegiadoItem.getCorreo() + "%')");
		}
		if (noColegiadoItem.getTelefono() != null && noColegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + noColegiadoItem.getTelefono() + "%'");
		}
		if (noColegiadoItem.getMovil() != null && noColegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + noColegiadoItem.getMovil() + "%'");
		}
		if (noColegiadoItem.getIdGrupo() != null && noColegiadoItem.getIdGrupo().length > 0) {

			String etiquetas = "";

			for (int i = 0; noColegiadoItem.getIdGrupo().length > i; i++) {

				if (i == noColegiadoItem.getIdGrupo().length - 1) {
					etiquetas += "'" + noColegiadoItem.getIdGrupo()[i] + "'";
				} else {
					etiquetas += "'" + noColegiadoItem.getIdGrupo()[i] + "',";
				}
			}

			sql.WHERE("grucli.IDGRUPO IN (" + etiquetas + ")");
		}
		
		if (noColegiadoItem.getTipoCV() != null && noColegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idcv = '" + noColegiadoItem.getTipoCV() + "'");
		}

		if (noColegiadoItem.getSubTipoCV1() != null && noColegiadoItem.getSubTipoCV1() != "") {
			sql.WHERE("subt1.idtipocvsubtipo1 = '" + noColegiadoItem.getSubTipoCV1() + "'");
		}
		
		if (noColegiadoItem.getSubTipoCV2() != null && noColegiadoItem.getSubTipoCV2() != "") {
			sql.WHERE("subt2.idtipocvsubtipo2 = '" + noColegiadoItem.getSubTipoCV2() + "'");
		}
				
		if (noColegiadoItem.getFechaNacimientoRango() != null && noColegiadoItem.getFechaNacimientoRango().length != 0) {

			if (noColegiadoItem.getFechaNacimientoRango()[0] != null && noColegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoDesde = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[0]);
				String getFechaNacimientoHasta = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("(per.fechanacimiento >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY') " + " and ( per.fechanacimiento <= TO_DATE('"
						+ getFechaNacimientoHasta + "','DD/MM/YYYY')))");

			} else if (noColegiadoItem.getFechaNacimientoRango()[0] != null
					&& noColegiadoItem.getFechaNacimientoRango()[1] == null) {

				String getFechaNacimientoDesde = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[0]);

				sql.WHERE("(per.fechanacimiento >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY'))");

			} else if (noColegiadoItem.getFechaNacimientoRango()[0] == null
					&& noColegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoHasta = dateFormat.format(noColegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("( per.fechanacimiento <= TO_DATE('" + getFechaNacimientoHasta
						+ "','DD/MM/YYYY'))");
			}
		}
		
		sql.ORDER_BY("nocol.IDPERSONA, NOCOL.IDINSTITUCION");
	
		return sql.toString();
	}
}
