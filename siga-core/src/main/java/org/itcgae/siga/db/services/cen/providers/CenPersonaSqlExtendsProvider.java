package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.PerJuridicaDatosRegistralesUpdateDTO;
import org.itcgae.siga.DTOs.cen.SociedadCreateDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.mappers.CenPersonaSqlProvider;

public class CenPersonaSqlExtendsProvider extends CenPersonaSqlProvider {

	public String loadPhotography(String idPersona, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("PER.NIFCIF");
		sql.SELECT("CLI.FOTOGRAFIA");
		sql.FROM("CEN_CLIENTE CLI");
		sql.INNER_JOIN("  CEN_PERSONA PER ON PER.IDPERSONA = CLI.IDPERSONA ");
		sql.WHERE(" PER.IDPERSONA ='" + idPersona + "'");
		sql.WHERE(" CLI.IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	
	public String countPerFisica(BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, String idLenguaje,
			String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("count(1) count");
		sql.FROM("CEN_PERSONA PER");
		sql.INNER_JOIN(" CEN_CLIENTE CLI  ON (PER.IDPERSONA = CLI.IDPERSONA) ");
		sql.INNER_JOIN(" CEN_INSTITUCION I ON (CLI.IDINSTITUCION = I.IDINSTITUCION) ");
		sql.LEFT_OUTER_JOIN(
				" CEN_COLEGIADO COL  ON (PER.IDPERSONA = COL.IDPERSONA AND CLI.IDINSTITUCION = COL.IDINSTITUCION)");
		sql.WHERE("PER.IDTIPOIDENTIFICACION IN ('10','30','40','50')");
		
		
		if (busquedaPerFisicaSearchDTO.isFromDesignaciones()) {
			sql.WHERE("ESTADOCOLEGIAL.IDESTADO IN ('10','20')");	
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNif())) {
			sql.WHERE("PER.NIFCIF = '" + busquedaPerFisicaSearchDTO.getNif() + "'");
		}

		// si el dni no está en los filtros, buscamos por nombre y apellidos si los puso
		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNombre())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("PER.NOMBRE", busquedaPerFisicaSearchDTO.getNombre()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getPrimerApellido())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("PER.APELLIDOS1",
					busquedaPerFisicaSearchDTO.getPrimerApellido()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getSegundoApellido())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("PER.APELLIDOS2",
					busquedaPerFisicaSearchDTO.getSegundoApellido()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNumeroColegiado())) {
			sql.WHERE(" (COL.NCOLEGIADO = '" + busquedaPerFisicaSearchDTO.getNumeroColegiado()
					+ "' OR COL.NCOMUNITARIO = '" + busquedaPerFisicaSearchDTO.getNumeroColegiado() + "')");
		}

		String idInstituciones = "";

		if (null != busquedaPerFisicaSearchDTO.getIdInstitucion()
				&& busquedaPerFisicaSearchDTO.getIdInstitucion().length > 0) {
			if (busquedaPerFisicaSearchDTO.getIdInstitucion().length > 1) {
				for (String string : busquedaPerFisicaSearchDTO.getIdInstitucion()) {
					idInstituciones += "'" + string + "'";
					idInstituciones += ",";
				}
				idInstituciones = idInstituciones.substring(0, idInstituciones.length() - 1);
			} else if (busquedaPerFisicaSearchDTO.getIdInstitucion().length == 1) {
				idInstituciones = "'" + busquedaPerFisicaSearchDTO.getIdInstitucion()[0] + "'";
			}
			sql.WHERE(" I.IDINSTITUCION  IN  (" + idInstituciones + ",'2000'" + ")");
		}else if(idInstitucion!=null){
			sql.WHERE(" I.IDINSTITUCION  =  '" + idInstitucion + "'");
			
		}

		return sql.toString();
	}

	public String searchPerFisica(BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, String idLenguaje,
			String idInstitucion) {

		SQL sql = new SQL();
		SQL sqlEstado = new SQL();
		
		sqlEstado.SELECT("ESTADOCOLEGIAL.DESCRIPCION"); 
		sqlEstado.FROM("CEN_ESTADOCOLEGIAL ESTADOCOLEGIAL");
		sqlEstado.INNER_JOIN("CEN_DATOSCOLEGIALESESTADO DCE ON ESTADOCOLEGIAL.IDESTADO = DCE.IDESTADO");
		sqlEstado.WHERE("DCE.IDINSTITUCION = I.IDINSTITUCION");
		sqlEstado.WHERE("DCE.IDPERSONA = PER.IDPERSONA");
		sqlEstado.WHERE("DCE.FECHAESTADO   = (SELECT MAX(ULT.FECHAESTADO) " +
					" FROM CEN_DATOSCOLEGIALESESTADO ULT " +
			        " WHERE ULT.IDINSTITUCION = I.IDINSTITUCION" +
			        " AND ULT.IDPERSONA     = PER.IDPERSONA" +
			        " AND ULT.FECHAESTADO   <= TO_DATE('31/12/2999','DD/MM/YYYY'))");

		// Pasamos string [] a string -> Para usarse en sentencia sql in(...)

		sql.SELECT_DISTINCT("PER.IDPERSONA");
		sql.SELECT("PER.NOMBRE AS DENOMINACION");
		sql.SELECT("CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2) AS APELLIDOS");
		sql.SELECT("PER.APELLIDOS1 AS PRIMERAPELLIDO");
		sql.SELECT("PER.APELLIDOS2 AS SEGUNDOAPELLIDO");
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.SELECT("PER.FECHANACIMIENTO");
		sql.SELECT("TO_CHAR(PER.FECHANACIMIENTO, 'dd/MM/yyyy') as FECHANACIMIENTOSTRING");
		sql.SELECT("I.ABREVIATURA AS COLEGIO");
		sql.SELECT("I.IDINSTITUCION");
		sql.SELECT(
				"NVL(DECODE(NVL(COL.COMUNITARIO,0),0, COL.NCOLEGIADO, COL.NCOMUNITARIO), COL.NCOLEGIADO) AS NUMEROCOLEGIADO");
		sql.SELECT(
				"NVL(f_siga_getrecurso((" + sqlEstado.toString() + ")," + idLenguaje + "), DECODE(PER.IDTIPOIDENTIFICACION,20,'SOCIEDAD','NO COLEGIADO')) AS ESTADOCOLEGIAL");
		sql.SELECT("DECODE(COL.SITUACIONRESIDENTE,'0','NO','1','SI') AS RESIDENTE");
		
//		sql.SELECT("ACT.IDACTIVIDADPROFESIONAL");
		sql.SELECT("PER.SEXO");
		sql.SELECT("PER.IDESTADOCIVIL");
		sql.SELECT("CLI.IDTRATAMIENTO");
		sql.SELECT("PER.NATURALDE");

//		  sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,1) AS Domicilio");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,2) AS CodigoPostal");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,11) AS Telefono1");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,12) AS Telefono2");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,13) AS Movil");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,14) AS Fax1");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,15) AS Fax2");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,3) AS nombrePoblacion");
//        sql.SELECT("f_siga_getdireccioncliente(CLI.idinstitucion,CLI.idpersona,3,16) AS CorreoElectronico");
//        sql.SELECT(" f_siga_getdireccioncliente(cli.idinstitucion, cli.idpersona, 3, 8) as provincia");
//        sql.SELECT(" f_siga_getdireccioncliente(cli.idinstitucion, cli.idpersona, 3, 9) as poblacion");
//        sql.SELECT(" f_siga_getdireccioncliente(cli.idinstitucion, cli.idpersona, 3, 7) as pais");        

		sql.FROM("CEN_PERSONA PER");
		// Mybatis cambia el orden de inner join y left_outer_join con sus funciones
		// predefinidas=> siempre pone inner join antes
		sql.INNER_JOIN(" CEN_CLIENTE CLI  ON (PER.IDPERSONA = CLI.IDPERSONA) ");
		sql.INNER_JOIN(" CEN_INSTITUCION I ON (CLI.IDINSTITUCION = I.IDINSTITUCION) ");
		sql.LEFT_OUTER_JOIN(
				" CEN_COLEGIADO COL  ON (PER.IDPERSONA = COL.IDPERSONA AND CLI.IDINSTITUCION = COL.IDINSTITUCION)");

//		sql.LEFT_OUTER_JOIN(
//				"CEN_NOCOLEGIADO_ACTIVIDAD ACT ON (PER.IDPERSONA = ACT.IDPERSONA AND CLI.IDINSTITUCION = ACT.IDINSTITUCION)");
		sql.WHERE("PER.IDTIPOIDENTIFICACION IN ('10','30','40','50')");
		
		
		if (busquedaPerFisicaSearchDTO.isFromDesignaciones()) {
			sql.WHERE("ESTADOCOLEGIAL.IDESTADO IN ('10','20')");	
		}
		
		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNif())) {
			sql.WHERE("PER.NIFCIF = '" + busquedaPerFisicaSearchDTO.getNif() + "'");
		}

		// si el dni no está en los filtros, buscamos por nombre y apellidos si los puso
		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNombre())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("PER.NOMBRE", busquedaPerFisicaSearchDTO.getNombre()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getPrimerApellido())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("PER.APELLIDOS1",
					busquedaPerFisicaSearchDTO.getPrimerApellido()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getSegundoApellido())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("PER.APELLIDOS2",
					busquedaPerFisicaSearchDTO.getSegundoApellido()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNumeroColegiado())) {
			sql.WHERE(" (COL.NCOLEGIADO = '" + busquedaPerFisicaSearchDTO.getNumeroColegiado()
					+ "' OR COL.NCOMUNITARIO = '" + busquedaPerFisicaSearchDTO.getNumeroColegiado() + "')");
		}

		String idInstituciones = "";

		if (null != busquedaPerFisicaSearchDTO.getIdInstitucion()
				&& busquedaPerFisicaSearchDTO.getIdInstitucion().length > 0) {
			if (busquedaPerFisicaSearchDTO.getIdInstitucion().length > 1) {
				for (String string : busquedaPerFisicaSearchDTO.getIdInstitucion()) {
					idInstituciones += "'" + string + "'";
					idInstituciones += ",";
				}
				idInstituciones = idInstituciones.substring(0, idInstituciones.length() - 1);
			} else if (busquedaPerFisicaSearchDTO.getIdInstitucion().length == 1) {
				idInstituciones = "'" + busquedaPerFisicaSearchDTO.getIdInstitucion()[0] + "'";
			}
			sql.WHERE(" I.IDINSTITUCION  IN  (" + idInstituciones + ",'2000'" + ")");
		}else if(idInstitucion!=null){
			sql.WHERE(" I.IDINSTITUCION  =  '" + idInstitucion + "'");
			
		}
		//
		// if(null != idInstituciones) {
		// if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNif())) {
		// sql.WHERE("PER.NIFCIF = '" + busquedaPerFisicaSearchDTO.getNif() + "' OR
		// (PER.NIFCIF = '" + busquedaPerFisicaSearchDTO.getNif() + "' AND
		// I.IDINSTITUCION IN (" + idInstituciones + "))");
		// }
		// }else{
		// if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNif())) {
		// sql.WHERE("(PER.NIFCIF = '" + busquedaPerFisicaSearchDTO.getNif() + "'");
		// }
		// }

		return sql.toString();

	}

	public String searchPerJuridica(int numpagina, BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO,
			String idLenguaje) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("consulta.*");

		sql.SELECT_DISTINCT("per.idpersona AS idpersona");
		sql.SELECT_DISTINCT("per.nifcif AS nif");
		sql.SELECT_DISTINCT("per.IDTIPOIDENTIFICACION ");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ',per.apellidos2) ) AS denominacion");
		sql.SELECT_DISTINCT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS abreviatura");
		sql.SELECT_DISTINCT("per.fechanacimiento AS fechaconstitucion");
		sql.SELECT_DISTINCT("col.sociedadprofesional AS sociedadprofesional");
		sql.SELECT_DISTINCT("ca.descripcion AS tipo");
		sql.SELECT_DISTINCT("col.fecha_baja AS fecha_baja");
		sql.SELECT_DISTINCT("nvl(COUNT(DISTINCT per2.idpersona),0) AS numerointegrantes");
		sql.SELECT_DISTINCT(
				"LISTAGG(concat(per2.nombre || ' ',concat(per2.apellidos1 || ' ',per2.apellidos2) ),';') WITHIN GROUP(ORDER BY per2.nombre) AS nombresintegrantes");
		sql.SELECT("cliColegiado.idinstitucion AS idinstitucion");
		sql.SELECT("'NO COLEGIADO' AS ESTADOCOLEGIAL");
		sql.FROM("cen_persona per");

		sql.LEFT_OUTER_JOIN("cen_nocolegiado col  ON per.idpersona = col.idpersona");
		sql.LEFT_OUTER_JOIN("cen_cliente cliColegiado ON per.idpersona = cliColegiado.idpersona");
		sql.LEFT_OUTER_JOIN("cen_institucion i ON cliColegiado.idinstitucion = i.idinstitucion");
		sql.LEFT_OUTER_JOIN("cen_tiposociedad tiposociedad ON tiposociedad.letracif = col.tipo");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos ca ON ( tiposociedad.descripcion = ca.idrecurso  AND ca.idlenguaje = '"
						+ idLenguaje + "' )");
		sql.LEFT_OUTER_JOIN(
				"cen_gruposcliente_cliente grupos_cliente ON (grupos_cliente.idinstitucion = col.idinstitucion AND col.idpersona = grupos_cliente.idpersona AND ( TO_DATE(grupos_cliente.fecha_inicio,'DD/MM/RRRR') <= SYSDATE    AND ( TO_DATE(grupos_cliente.fecha_baja,'DD/MM/RRRR') >= SYSDATE  OR grupos_cliente.fecha_baja IS NULL)))");
		sql.LEFT_OUTER_JOIN(
				"cen_componentes com ON ( com.idpersona = col.idpersona        AND col.idinstitucion = com.idinstitucion )");
		sql.LEFT_OUTER_JOIN(
				"cen_cliente cli ON ( com.cen_cliente_idpersona = cli.idpersona AND cli.idinstitucion = com.idinstitucion )");

		sql.LEFT_OUTER_JOIN("cen_persona per2 ON per2.idpersona = cli.idpersona");

		if (null != busquedaPerJuridicaSearchDTO.getIdInstitucion()
				&& busquedaPerJuridicaSearchDTO.getIdInstitucion().length > 0) {
			// Pasamos string [] a string -> Para usarse en sentencia sql in(...)
			String idInstituciones = "";
			if (busquedaPerJuridicaSearchDTO.getIdInstitucion().length > 1) {
				for (String string : busquedaPerJuridicaSearchDTO.getIdInstitucion()) {
					idInstituciones += "'" + string + "'";
					idInstituciones += ",";
				}
				idInstituciones = idInstituciones.substring(0, idInstituciones.length() - 1);
			} else if (busquedaPerJuridicaSearchDTO.getIdInstitucion().length == 1) {
				idInstituciones = "'" + busquedaPerJuridicaSearchDTO.getIdInstitucion()[0] + "'";
			}
			sql.WHERE("i.idinstitucion in (" + idInstituciones + ")");
		}
		sql.WHERE("col.fecha_baja IS NULL");
		sql.WHERE("PER.IDTIPOIDENTIFICACION IN ('20')");
		// if (null != busquedaPerJuridicaSearchDTO.getNumColegiado()
		// && !busquedaPerJuridicaSearchDTO.getNumColegiado().equalsIgnoreCase("")) {
		// sql.WHERE(" COL.NCOLEGIADO = '" +
		// busquedaPerJuridicaSearchDTO.getNumColegiado() + "')");
		// }
		if (null != busquedaPerJuridicaSearchDTO.getTipo()
				&& !busquedaPerJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql.WHERE("tiposociedad.letracif = '" + busquedaPerJuridicaSearchDTO.getTipo() + "'");
		}
		if (null != busquedaPerJuridicaSearchDTO.getNif()
				&& !busquedaPerJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql.WHERE("per.nifcif = '" + busquedaPerJuridicaSearchDTO.getNif() + "'");
			// si trae un nif no tendrá en cuenta el tipo 'V' que es otros
			//sql.WHERE("col.tipo IN ('G','B','A','Y','E','J','F','C','H','P','Q','R','S','U')");
		}

		sql.GROUP_BY("cliColegiado.idinstitucion");
		sql.GROUP_BY("per.idpersona");
		sql.GROUP_BY("per.nifcif");
		sql.GROUP_BY("per.IDTIPOIDENTIFICACION");
		sql.GROUP_BY("per.nombre");
		sql.GROUP_BY("per.apellidos1");
		sql.GROUP_BY("per.apellidos2");
		sql.GROUP_BY("i.abreviatura");
		sql.GROUP_BY("grupos_cliente.idgrupo");
		sql.GROUP_BY("per.fechanacimiento");
		sql.GROUP_BY("col.sociedadprofesional");
		sql.GROUP_BY("col.tipo");
		sql.GROUP_BY("ca.descripcion");
		sql.GROUP_BY("col.fecha_baja ");

		sql2.FROM("( " + sql + ") consulta");

		if (!UtilidadesString.esCadenaVacia(busquedaPerJuridicaSearchDTO.getDenominacion())) {
			sql2.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.denominacion",
					busquedaPerJuridicaSearchDTO.getDenominacion()));
		}

		if (!UtilidadesString.esCadenaVacia(busquedaPerJuridicaSearchDTO.getAbreviatura())) {
			sql2.WHERE(UtilidadesString.filtroTextoBusquedas("consulta.abreviatura",
					busquedaPerJuridicaSearchDTO.getAbreviatura()));
		}

		return sql2.toString();
	}

	public String insertSelectiveForCreateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_PERSONA");
		sql.VALUES("IDPERSONA", "(Select max(idpersona) +1 from cen_persona where idpersona like '"
				+ usuario.getIdinstitucion() + "' || '%' )");
		sql.VALUES("NOMBRE", "'" + etiquetaUpdateDTO.getDenominacion().replace("'", "''") + "'");
		// sql.VALUES("APELLIDOS1", "");
		// sql.VALUES("APELLIDOS2", "");
		sql.VALUES("NIFCIF", "'" + etiquetaUpdateDTO.getNif() + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		sql.VALUES("IDTIPOIDENTIFICACION",
				"(SELECT IDTIPOIDENTIFICACION FROM CEN_TIPOIDENTIFICACION WHERE CODIGOEJIS = 'C')");
		sql.VALUES("FECHANACIMIENTO", "SYSDATE");
		// sql.VALUES("IDESTADOCIVIL", "");
		// sql.VALUES("NATURALDE", "");
		sql.VALUES("FALLECIDO", "'0'");
		// sql.VALUES("SEXO", "");

		return sql.toString();
	}

	public String searchPersonFile(Short idInstitucion, Long idPersona) {

		SQL sql = new SQL();

		sql.SELECT("PER.IDPERSONA AS IDPERSONA");
		sql.SELECT("PER.nifcif AS NIF");
		sql.SELECT("PER.FECHANACIMIENTO AS FECHAALTA");
		sql.SELECT("PER.NOMBRE AS NOMBRE");
		sql.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS APELLIDO1");
		sql.SELECT("PER.APELLIDOS2 AS APELLIDO2");
		sql.SELECT("PER.IDTIPOIDENTIFICACION");
		sql.FROM("CEN_PERSONA PER ");

		if (null != idPersona) {
			sql.WHERE("PER.IDPERSONA = '" + idPersona + "'");
		}

		return sql.toString();
	}

	public String insertSelectiveForPerson(CenPersona crearPersonaDTO, AdmUsuarios usuario) {
		SQL sql = new SQL();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.INSERT_INTO("CEN_PERSONA");
		sql.VALUES("IDPERSONA", "(Select max(idpersona+1)  from cen_persona where idpersona like '"
				+ usuario.getIdinstitucion() + "' || '%' )");

		if (!crearPersonaDTO.getNombre().equals("")) {
			sql.VALUES("NOMBRE", "'" + crearPersonaDTO.getNombre().replace("'", "''") + "'");
		}
		if (!crearPersonaDTO.getApellidos1().equals("")) {
			sql.VALUES("APELLIDOS1", "'" + crearPersonaDTO.getApellidos1().replace("'", "''") + "'");
		}
		if (null != crearPersonaDTO.getApellidos2()) {
			sql.VALUES("APELLIDOS2", "'" + crearPersonaDTO.getApellidos2().replace("'", "''") + "'");
		} else {
			sql.VALUES("APELLIDOS2", "null");
		}
		if (crearPersonaDTO.getFechanacimiento() != null) {

			String fechaNacimiento = dateFormat.format(crearPersonaDTO.getFechanacimiento());
			sql.VALUES("FECHANACIMIENTO", "(TO_DATE('" + fechaNacimiento + "','DD/MM/YYYY'))");
			// sql.VALUES("FECHANACIMIENTO", "'" + crearPersonaDTO.getFechanacimiento() +
			// "'");
		}
		if (!crearPersonaDTO.getNifcif().equals("")) {
			sql.VALUES("NIFCIF", "'" + crearPersonaDTO.getNifcif() + "'");
		} else {
			sql.VALUES("NIFCIF", "null");
		}

		if (null != crearPersonaDTO.getIdtipoidentificacion()) {
			sql.VALUES("IDTIPOIDENTIFICACION", "'" + crearPersonaDTO.getIdtipoidentificacion() + "'");
		}
		if (null != crearPersonaDTO.getSexo()) {
			sql.VALUES("SEXO", "'" + crearPersonaDTO.getSexo() + "'");
		}
		if (null != crearPersonaDTO.getIdestadocivil()) {
			sql.VALUES("IDESTADOCIVIL", "'" + crearPersonaDTO.getIdestadocivil() + "'");
		}

		if (null != crearPersonaDTO.getNaturalde()) {
			sql.VALUES("NATURALDE", "'" + crearPersonaDTO.getNaturalde().replace("'", "''") + "'");
		}

		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");

		return sql.toString();
	}

	public String insertSelectiveForPersonFile(CrearPersonaDTO crearPersonaDTO, AdmUsuarios usuario) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_PERSONA");
		sql.VALUES("IDPERSONA", "(Select max(idpersona+1)  from cen_persona where idpersona like '"
				+ usuario.getIdinstitucion() + "' || '%' )");

		if (!crearPersonaDTO.getNombre().equals("")) {
			sql.VALUES("NOMBRE", "'" + crearPersonaDTO.getNombre().replace("'", "''") + "'");
		}
		if (!crearPersonaDTO.getApellido1().equals("")) {
			sql.VALUES("APELLIDOS1", "'" + crearPersonaDTO.getApellido1().replace("'", "''") + "'");
		}
		if (!crearPersonaDTO.getApellido2().equals("")) {
			sql.VALUES("APELLIDOS2", "'" + crearPersonaDTO.getApellido2().replace("'", "''") + "'");
		} else {
			sql.VALUES("APELLIDOS2", "null");
		}
		if (!crearPersonaDTO.getNif().equals("")) {
			sql.VALUES("NIFCIF", "'" + crearPersonaDTO.getNif() + "'");
		} else {
			sql.VALUES("NIFCIF", "null");
		}
		if (!crearPersonaDTO.getTipoIdentificacion().equals("")) {
			sql.VALUES("IDTIPOIDENTIFICACION", "'" + crearPersonaDTO.getTipoIdentificacion() + "'");
		}

		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");

		return sql.toString();
	}

	public String selectMaxIdPersona(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("max(idpersona) as IDPERSONA1");
		sql.SELECT("max(idpersona) as IDPERSONA2");
		sql.FROM("cen_persona");
		sql.WHERE("idpersona like '" + idInstitucion + "' || '%' ");
		return sql.toString();
	}

	public String selectMaxIdPersona2(String idinstitucion) {

		SQL sql = new SQL();

		sql.SELECT("max(IDPERSONA) +1 AS IDPERSONA");
		sql.FROM("cen_persona");
		sql.WHERE("idpersona like '" + idinstitucion + "' || '%' ");

		return sql.toString();
	}

	public String updatebyExampleDataLegalPerson(
			PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, AdmUsuarios usuario) {
		SQL sql = new SQL();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatHMS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		sql.UPDATE("CEN_PERSONA");

		if (null != perJuridicaDatosRegistralesUpdateDTO.getFechaConstitucion()) {
			String fechaNacimiento = dateFormat.format(perJuridicaDatosRegistralesUpdateDTO.getFechaConstitucion());
			sql.SET("FECHANACIMIENTO = TO_DATE('" + fechaNacimiento + "','DD/MM/YYYY')");
		}

		if (null != usuario.getIdusuario()) {
			sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		}

		sql.SET("FECHAMODIFICACION = TO_DATE('" + dateFormatHMS.format(new Date()) + "','DD/MM/YYYY hh24:mi:ss')");

		sql.WHERE("IDPERSONA = '" + perJuridicaDatosRegistralesUpdateDTO.getIdPersona() + "'");
		return sql.toString();
	}

	public String insertSelectiveForNewSociety(SociedadCreateDTO sociedadCreateDTO, AdmUsuarios usuario) {
		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.INSERT_INTO("CEN_PERSONA");
		sql.VALUES("IDPERSONA", "(Select max(idpersona+1)  from cen_persona where idpersona like '"
				+ usuario.getIdinstitucion() + "' || '%' )");

		if (!sociedadCreateDTO.getDenominacion().equals("")) {
			sql.VALUES("NOMBRE", "'" + sociedadCreateDTO.getDenominacion().replace("'", "''") + "'");
		}
		if (!sociedadCreateDTO.getAbreviatura().equals("")) {
			sql.VALUES("APELLIDOS1", "'" + sociedadCreateDTO.getAbreviatura().replace("'", "''") + "'");
		}

		if (!sociedadCreateDTO.getNif().equals("")) {
			sql.VALUES("NIFCIF", "'" + sociedadCreateDTO.getNif() + "'");
		}

		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		sql.VALUES("IDTIPOIDENTIFICACION", "'20'");

		if (null != sociedadCreateDTO.getFechaConstitucion()) {
			String fechaNacimiento = dateFormat.format(sociedadCreateDTO.getFechaConstitucion());
			sql.VALUES("FECHANACIMIENTO", "TO_DATE('" + fechaNacimiento + "','DD/MM/YYYY')");
		}

		sql.VALUES("FALLECIDO", "'0'");

		return sql.toString();
	}

	public String getPersonaisColegiadoWithIdPersona(String idPersona, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("PER.IDPERSONA");
		sql.SELECT("PER.NOMBRE AS NOMBRE");
		sql.SELECT("PER.APELLIDOS1 AS APELLIDO1");
		sql.SELECT("PER.APELLIDOS2 AS APELLIDO2");
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.FROM("cen_persona per");
		sql.WHERE("per.IDPERSONA = '" + idPersona + "'");

		return sql.toString();

	}

	public String getIdPersonaWithNif(String personaNif) {
		SQL sql = new SQL();

		sql.SELECT("p.idpersona");
		sql.FROM("cen_persona p");
		sql.WHERE("p.nifcif = '" + personaNif + "'");

		return sql.toString();
	}
	public String getColegiadoByIdPersona(String idPersona, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("CASE WHEN COLEGIADO.NCOLEGIADO IS NOT NULL THEN COLEGIADO.NCOLEGIADO ELSE COLEGIADO.NCOMUNITARIO END AS NCOLEGIADO");
		sql.SELECT("PERSONA.APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT("(COLEGIADO.NCOLEGIADO || ' ' || PERSONA.APELLIDOS1  || ' ' || PERSONA.APELLIDOS2 || ', ' || PERSONA.NOMBRE) AS COLEGIADO");
		sql.FROM("CEN_PERSONA PERSONA");
		sql.INNER_JOIN("CEN_CLIENTE CLIENTE ON  PERSONA.IDPERSONA = CLIENTE.IDPERSONA");
		sql.INNER_JOIN("CEN_COLEGIADO COLEGIADO ON PERSONA.IDPERSONA = COLEGIADO.IDPERSONA AND CLIENTE.IDINSTITUCION = COLEGIADO.IDINSTITUCION");
		sql.WHERE("CLIENTE.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("PERSONA.IDPERSONA = '" + idPersona + "'");
		return sql.toString();
	}
	public String busquedaColegiadoExpress(String colegiadoJGItem, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("DECODE(COLEGIADO.COMUNITARIO,0,COLEGIADO.NCOLEGIADO,COLEGIADO.NCOMUNITARIO) AS NCOLEGIADO");
		sql.SELECT("COLEGIADO.IDPERSONA");
		sql.SELECT("(PERSONA.APELLIDOS1 || ' ' || PERSONA.APELLIDOS2 || ' ' || PERSONA.NOMBRE) AS NOMBRE");
		sql.FROM("CEN_PERSONA PERSONA");
		sql.INNER_JOIN("CEN_CLIENTE CLIENTE ON PERSONA.IDPERSONA = CLIENTE.IDPERSONA");
		sql.INNER_JOIN("CEN_COLEGIADO COLEGIADO ON PERSONA.IDPERSONA = COLEGIADO.IDPERSONA AND CLIENTE.IDINSTITUCION = COLEGIADO.IDINSTITUCION");
		sql.WHERE("CLIENTE.idinstitucion = '"+idInstitucion+"'");
		sql.WHERE("((colegiado.comunitario = 0 and COLEGIADO.ncolegiado = '"+colegiadoJGItem+"') OR (colegiado.comunitario = 1 and COLEGIADO.NCOMUNITARIO = '"+colegiadoJGItem+"'))");
		return sql.toString();
	}
	
	public String getDestinatariosSeries(Short idInstitucion, String idSerieFacturacion) {
		SQL sql = new SQL();
		
		// Select
		sql.SELECT_DISTINCT("p.idpersona");
		sql.SELECT_DISTINCT("c.idinstitucion");
		sql.SELECT_DISTINCT("p.nombre");
		sql.SELECT_DISTINCT("p.apellidos1");
		sql.SELECT_DISTINCT("p.apellidos2");
		sql.SELECT_DISTINCT("p.nifcif");
		sql.SELECT_DISTINCT("d.movil");
		sql.SELECT_DISTINCT("d.correoelectronico");
		sql.SELECT_DISTINCT("d.domicilio");
		
		// From
		sql.FROM("cen_persona p");
		
		// Joins
		sql.INNER_JOIN("cen_cliente c ON ( c.idpersona = p.idpersona )");
		sql.INNER_JOIN("fac_clienincluidoenseriefactur fc ON ( fc.idpersona = c.idpersona AND fc.idinstitucion = c.idinstitucion )");
		sql.LEFT_OUTER_JOIN("cen_direcciones d ON ( d.idpersona = p.idpersona AND d.idinstitucion = c.idinstitucion )");

		// Where
		sql.WHERE("fc.idinstitucion = " + idInstitucion);
		sql.WHERE("fc.idseriefacturacion = " + idSerieFacturacion);
		
		// Order by
		sql.ORDER_BY("p.nombre");
		
		return sql.toString();
	}
		
	public String getDatosPersonaForImpreso190(String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("PER.NIFCIF");
		sql.SELECT("PER.IDTIPOIDENTIFICACION AS IDTIPOIDENTIFICACION");
		sql.SELECT("PER.NOMBRE AS NOMBRE");
		sql.SELECT("PER.APELLIDOS1 AS APELLIDO1");
		sql.SELECT("PER.APELLIDOS2 AS APELLIDO2");
		sql.SELECT("PER.NIFCIF AS NIDENTIFICACION");
		sql.SELECT("PER.APELLIDOS1 || ' ' || PER.APELLIDOS2 || ', ' || PER.NOMBRE AS NOMBREPERSONA ");
		sql.FROM("cen_persona per");
		sql.WHERE("per.IDPERSONA = '" + idPersona + "'");

		return sql.toString();

	}
	
	public String getDatosInstitucionForImpreso190(String idinstitucion) {
		SQL sql = new SQL();

		sql.SELECT("PER.NIFCIF");
		sql.SELECT("PER.IDPERSONA");
		sql.SELECT("PER.NOMBRE");
		sql.FROM("Cen_Persona PER ");
		sql.JOIN("Cen_Institucion INS ON INS.IDINSTITUCION = " + idinstitucion + " and INS.IDPERSONA = PER.IDPERSONA");

		return sql.toString();
	}
}
