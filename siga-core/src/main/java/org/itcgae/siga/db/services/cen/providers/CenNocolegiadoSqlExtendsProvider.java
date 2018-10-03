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
		String grupos = "";

		if (busquedaJuridicaSearchDTO.getGrupos().length > 1) {
			for (String string : busquedaJuridicaSearchDTO.getGrupos()) {
				grupos += string;
				grupos += ",";
			}
			grupos = grupos.substring(0, grupos.length() - 1);
		} else if (busquedaJuridicaSearchDTO.getGrupos().length == 1) {
			grupos = busquedaJuridicaSearchDTO.getGrupos()[0];
		}

		sql2.SELECT(" DISTINCT COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql2.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql2.SELECT("TO_CHAR(PER.FECHANACIMIENTO,'DD/MM/YYYY')  AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
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
						+ "AND (TO_DATE(grupos_cliente.fecha_inicio,'DD/MM/YYYY') <= SYSDATE  AND ( TO_DATE(grupos_cliente.fecha_baja,'DD/MM/YYYY') >= SYSDATE OR grupos_cliente.fecha_baja IS NULL )))");
		sql2.LEFT_OUTER_JOIN(
				"CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION AND COM.FECHABAJA IS NULL)");
		sql2.LEFT_OUTER_JOIN(
				"CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA");

		if (null != idInstitucion) {
			sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");
		}

		sql2.WHERE("COL.FECHA_BAJA IS NULL");
		if (null != busquedaJuridicaSearchDTO.getNif() && !busquedaJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql2.WHERE("(UPPER(PER.NIFCIF) LIKE UPPER  ('%" + busquedaJuridicaSearchDTO.getNif() + "%'))");

		}

		if (!grupos.equalsIgnoreCase("")) {
			sql2.WHERE("GRUPOS_CLIENTE.IDGRUPO IN (" + grupos + ")");
		}

		if (null != busquedaJuridicaSearchDTO.getFechaConstitucion()
				&& !busquedaJuridicaSearchDTO.getFechaConstitucion().equals("")) {
			String fechaC = dateFormat.format(busquedaJuridicaSearchDTO.getFechaConstitucion());
			sql2.WHERE("PER.FECHANACIMIENTO = TO_DATE('" + fechaC + "', 'DD/MM/YYYY')");
		}

		if (busquedaJuridicaSearchDTO.getSociedadesProfesionales()) {
			sql2.WHERE("COL.SOCIEDADPROFESIONAL = '1'");
		}

		if (null != busquedaJuridicaSearchDTO.getTipo() && !busquedaJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql2.WHERE("COL.TIPO = '" + busquedaJuridicaSearchDTO.getTipo() + "'");
		} else {
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

		sql2.SELECT("DISTINCT COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql2.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql2.SELECT("TO_CHAR(PER.FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
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
						+ "AND (TO_DATE(grupos_cliente.fecha_inicio,'DD/MM/RRRR') <= SYSDATE  AND ( TO_DATE(grupos_cliente.fecha_baja,'DD/MM/RRRR') >= SYSDATE OR grupos_cliente.fecha_baja IS NULL )))");
		sql2.LEFT_OUTER_JOIN(
				"CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION AND COM.FECHABAJA IS NULL)");
		sql2.LEFT_OUTER_JOIN(
				"CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA");

		if (null != idInstitucion) {
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
		sql.FROM("(" + sql2 + ") consulta");

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
		sql.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql.SELECT("TIPOSOCIEDAD.LETRACIF  AS TIPO");
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
		sql.GROUP_BY("CLIENTESOCIEDAD.IDLENGUAJE");
		sql.GROUP_BY("CLIENTESOCIEDAD.ASIENTOCONTABLE");

		return sql.toString();
	}

	public String insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario,
			EtiquetaUpdateDTO etiquetaUpdateDTO) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_NOCOLEGIADO");

		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		// sql.VALUES("SERIE", "");
		// sql.VALUES("NUMEROREF", "");
		sql.VALUES("SOCIEDADSJ", "'0'");
		sql.VALUES("TIPO", "'" + etiquetaUpdateDTO.getTipo() + "'");
		if (null != etiquetaUpdateDTO.getAnotaciones() && !etiquetaUpdateDTO.getAnotaciones().equals("")) {
			sql.VALUES("ANOTACIONES", "'" + etiquetaUpdateDTO.getAnotaciones() + "'");
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
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON (CAT.IDRECURSO = RET.DESCRIPCION AND CAT.IDLENGUAJE = '"
				+ idLenguaje + "')");

		sql.WHERE("PER.IDPERSONA = '" + personaSearchDto.getIdPersona() + "'");
		sql.WHERE("IRPF.IDINSTITUCION = '" + idInstitucion + "'");
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

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.UPDATE("cen_nocolegiado");

		if (!perJuridicaDatosRegistralesUpdateDTO.getResena().equals("")) {
			sql.SET("RESENA = '" + perJuridicaDatosRegistralesUpdateDTO.getResena() + "'");
		}

		if (!perJuridicaDatosRegistralesUpdateDTO.getObjetoSocial().equals("")) {
			sql.SET("OBJETOSOCIAL = '" + perJuridicaDatosRegistralesUpdateDTO.getObjetoSocial() + "'");
		}

		if (!perJuridicaDatosRegistralesUpdateDTO.getNumeroPoliza().equals("")) {
			sql.SET("NOPOLIZA = '" + perJuridicaDatosRegistralesUpdateDTO.getNumeroPoliza() + "'");
		}

		if (!perJuridicaDatosRegistralesUpdateDTO.getCompaniaAseg().equals("")) {
			sql.SET("COMPANIASEG = '" + perJuridicaDatosRegistralesUpdateDTO.getCompaniaAseg() + "'");
		}

		if (null != perJuridicaDatosRegistralesUpdateDTO.getFechaFin()) {
			String fechaF = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaFin());
			sql.SET("FECHAFIN = TO_DATE('" + fechaF + "','DD/MM/YYYY')");
		}

		if (null != perJuridicaDatosRegistralesUpdateDTO.getFechaBaja()) {
			String fechaB = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaBaja());
			sql.SET("FECHA_BAJA = TO_DATE('" + fechaB + "','DD/MM/YYYY')");
		}

		if (!perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional().equals("")) {
			sql.SET("SOCIEDADPROFESIONAL = '" + perJuridicaDatosRegistralesUpdateDTO.getSociedadProfesional() + "'");
		}

		if (null != usuario.getIdusuario() && !usuario.getIdusuario().equals("")) {
			sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		}

		if (!perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp().equals("")) {
			sql.SET("PREFIJO_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getPrefijoNumsspp() + "'");
		}
		if (!perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp().equals("")) {
			sql.SET("CONTADOR_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getContadorNumsspp() + "'");
		}
		if (null != perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp()
				&& !perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp().equals("")) {
			sql.SET("SUFIJO_NUMSSPP = '" + perJuridicaDatosRegistralesUpdateDTO.getSufijoNumsspp() + "'");
		}

		sql.SET("FECHAMODIFICACION = SYSDATE");

		sql.WHERE("IDPERSONA = '" + perJuridicaDatosRegistralesUpdateDTO.getIdPersona() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String selectSociedadesEliminadas(Short idInstitucion, Date fechaDesde, Date fechaHasta) {
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SQL sql = new SQL();
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql.FROM("CEN_NOCOLEGIADO COL");

		sql.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("COL.FECHA_BAJA >= TO_DATE('" + fechadesde + "', 'DD/MM/YYYY')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("COL.FECHA_BAJA < TO_DATE('" + fechahasta + "', 'DD/MM/YYYY')");

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
		sql.SELECT(
				"SOCIEDAD.PREFIJO_NUMSSPP || SOCIEDAD.CONTADOR_NUMSSPP || SOCIEDAD.SUFIJO_NUMSSPP AS IDENTIFICACIONREGISTRO");
		sql.SELECT("SOCIEDAD.CONTADOR_NUMSSPP AS NUMEROREGISTRO");
		sql.FROM("CEN_NOCOLEGIADO SOCIEDAD");
		sql.INNER_JOIN("CEN_PERSONA PER ON PER.IDPERSONA = SOCIEDAD.IDPERSONA");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA NOTARIO ON NOTARIO.IDPERSONA = SOCIEDAD.IDPERSONANOTARIO");
		sql.INNER_JOIN("CEN_TIPOSOCIEDAD TS ON SOCIEDAD.TIPO = TS.LETRACIF");
		sql.WHERE("SOCIEDAD.IDINSTITUCION = '" + idInstitucion + "'");
		String fechadesde = dateFormat.format(fechaDesde);
		sql.WHERE("SOCIEDAD.FECHAMODIFICACION >= TO_DATE('" + fechadesde + "', 'DD/MM/YYYY')");
		String fechahasta = dateFormat.format(fechaHasta);
		sql.WHERE("SOCIEDAD.FECHAMODIFICACION < TO_DATE('" + fechahasta + "', 'DD/MM/YYYY')");
		sql.WHERE("SOCIEDAD.FECHA_BAJA IS NULL");
		sql.WHERE("SOCIEDAD.TIPO IN (SELECT LETRACIF FROM CEN_TIPOSOCIEDAD)");
		sql.WHERE("SOCIEDAD.SOCIEDADPROFESIONAL = 1");
		
		sql.ORDER_BY("PER.IDPERSONA");

		return sql.toString();

	}

	public String insertSelectiveForCreateNewSociety(String idInstitucion, AdmUsuarios usuario,
			SociedadCreateDTO sociedadCreateDTO) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_NOCOLEGIADO");

		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		// sql.VALUES("SERIE", "");
		// sql.VALUES("NUMEROREF", "");
		sql.VALUES("SOCIEDADSJ", "'0'");
		sql.VALUES("TIPO", "'" + sociedadCreateDTO.getTipo() + "'");
		if (null != sociedadCreateDTO.getAnotaciones() && !sociedadCreateDTO.getAnotaciones().equals("")) {
			sql.VALUES("ANOTACIONES", "'" + sociedadCreateDTO.getAnotaciones() + "'");
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
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("cli.idtratamiento as tratamiento");
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
				+ "    (grucli.idinstitucion = inst.idinstitucion and nocol.idpersona = grucli.idpersona and (TO_DATE(grucli.fecha_inicio,'DD/MM/YYYY') <= SYSDATE and \r\n"
				+ "        ( TO_DATE(grucli.fecha_baja,'DD/MM/YYYY') >= SYSDATE OR grucli.fecha_baja IS NULL)))");
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");
		sql.LEFT_OUTER_JOIN(
				"CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON (TIPODIRECCION.IDDIRECCION = DIR.IDDIRECCION AND TIPODIRECCION.IDPERSONA = DIR.IDPERSONA AND  TIPODIRECCION.IDINSTITUCION = DIR.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN(
				"cen_datoscv datoscv on (datoscv.idpersona = cli.idpersona and datoscv.idinstitucion = cli.idinstitucion)");

		sql.WHERE("NOCOL.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("per.idtipoidentificacion not in '20'");

		if (noColegiadoItem.getNif() != null && noColegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + noColegiadoItem.getNif() + "%')");
		}
		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
			sql.WHERE("upper(per.nombre) like upper('%" + noColegiadoItem.getNombre() + "%')");
		}
		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" + noColegiadoItem.getApellidos()
					+ "%')");
		}
		if (noColegiadoItem.getSexo() != null && noColegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + noColegiadoItem.getSexo() + "'");
		}
		if (noColegiadoItem.getTipoDireccion() != null && noColegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("TIPODIRECCION.IDTIPODIRECCION = '" + noColegiadoItem.getTipoDireccion() + "'");
		}
		if (noColegiadoItem.getEstadoCivil() != null && noColegiadoItem.getEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + noColegiadoItem.getEstadoCivil() + "'");
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
		if (noColegiadoItem.getEtiquetas() != null && noColegiadoItem.getEtiquetas().length > 0) {

			String etiquetas = "";

			for (int i = 0; noColegiadoItem.getEtiquetas().length > i; i++) {

				if (i == noColegiadoItem.getEtiquetas().length - 1) {
					etiquetas += "'" + noColegiadoItem.getEtiquetas()[i] + "'";
				} else {
					etiquetas += "'" + noColegiadoItem.getEtiquetas()[i] + "',";
				}
			}

			sql.WHERE("grucli.IDGRUPO IN (" + etiquetas + ")");
		}
		if (noColegiadoItem.getIdcv() != null && noColegiadoItem.getIdcv() != "") {
			sql.WHERE("datoscv.idcv = '" + noColegiadoItem.getIdcv() + "'");
		}
		if (noColegiadoItem.getFechaNacimiento() != null && noColegiadoItem.getFechaNacimiento() != "") {
			sql.WHERE("(TO_DATE(PER.FECHANACIMIENTO,'DD/MM/RRRR') >= TO_DATE('" + noColegiadoItem.getFechaNacimiento()
					+ "','DD/MM/YYYY') and ( TO_DATE(PER.FECHANACIMIENTO,'DD/MM/RRRR') <= TO_DATE('" + noColegiadoItem.getFechaNacimiento()
					+ "','DD/MM/YYYY')))");
		}
		
		sql.ORDER_BY("nocol.IDPERSONA, NOCOL.IDINSTITUCION");
	
		return sql.toString();
	}
	
	public String searchHistoricNoColegiado(NoColegiadoItem noColegiadoItem, String idLenguaje,
			String idInstitucion) {
		SQL sql = new SQL();

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
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("cli.idtratamiento as tratamiento");
		sql.SELECT_DISTINCT("per.fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");
		sql.SELECT_DISTINCT("dir.movil");
		sql.SELECT_DISTINCT("nocol.fecha_baja");
		sql.FROM("cen_nocolegiado nocol");

		sql.INNER_JOIN("cen_persona per on nocol.idpersona = per.idpersona");
		sql.INNER_JOIN(
				"cen_cliente cli on (nocol.idpersona = cli.idpersona and nocol.idinstitucion = cli.idinstitucion)");
		sql.INNER_JOIN("cen_institucion inst on nocol.idinstitucion = inst.idinstitucion");

		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");
		sql.LEFT_OUTER_JOIN(
				"CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON (TIPODIRECCION.IDDIRECCION = DIR.IDDIRECCION AND TIPODIRECCION.IDPERSONA = DIR.IDPERSONA AND  TIPODIRECCION.IDINSTITUCION = DIR.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN(
				"cen_datoscv datoscv on (datoscv.idpersona = cli.idpersona and datoscv.idinstitucion = cli.idinstitucion)");

		sql.WHERE("NOCOL.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("per.idtipoidentificacion not in '20'");

		if (noColegiadoItem.getNif() != null && noColegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + noColegiadoItem.getNif() + "%')");
		}
		if (noColegiadoItem.getNombre() != null && noColegiadoItem.getNombre() != "") {
			sql.WHERE("upper(per.nombre) like upper('%" + noColegiadoItem.getNombre() + "%')");
		}
		if (noColegiadoItem.getApellidos() != null && noColegiadoItem.getApellidos() != "") {
			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" + noColegiadoItem.getApellidos()
					+ "%')");
		}
		if (noColegiadoItem.getSexo() != null && noColegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + noColegiadoItem.getSexo() + "'");
		}
		if (noColegiadoItem.getTipoDireccion() != null && noColegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("TIPODIRECCION.IDTIPODIRECCION = '" + noColegiadoItem.getTipoDireccion() + "'");
		}
		if (noColegiadoItem.getEstadoCivil() != null && noColegiadoItem.getEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + noColegiadoItem.getEstadoCivil() + "'");
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
		if (noColegiadoItem.getEtiquetas() != null && noColegiadoItem.getEtiquetas().length > 0) {

			String etiquetas = "";

			for (int i = 0; noColegiadoItem.getEtiquetas().length > i; i++) {

				if (i == noColegiadoItem.getEtiquetas().length - 1) {
					etiquetas += "'" + noColegiadoItem.getEtiquetas()[i] + "'";
				} else {
					etiquetas += "'" + noColegiadoItem.getEtiquetas()[i] + "',";
				}
			}

			sql.WHERE("grucli.IDGRUPO IN (" + etiquetas + ")");
		}
		if (noColegiadoItem.getIdcv() != null && noColegiadoItem.getIdcv() != "") {
			sql.WHERE("datoscv.idcv = '" + noColegiadoItem.getIdcv() + "'");
		}
		if (noColegiadoItem.getFechaNacimiento() != null && noColegiadoItem.getFechaNacimiento() != "") {
			sql.WHERE("(TO_DATE(PER.FECHANACIMIENTO,'DD/MM/RRRR') >= TO_DATE('" + noColegiadoItem.getFechaNacimiento()
					+ "','DD/MM/YYYY') and ( TO_DATE(PER.FECHANACIMIENTO,'DD/MM/RRRR') <= TO_DATE('" + noColegiadoItem.getFechaNacimiento()
					+ "','DD/MM/YYYY')))");
		}
		
		sql.ORDER_BY("nocol.IDPERSONA, NOCOL.IDINSTITUCION");
	
		return sql.toString();
	}
}
