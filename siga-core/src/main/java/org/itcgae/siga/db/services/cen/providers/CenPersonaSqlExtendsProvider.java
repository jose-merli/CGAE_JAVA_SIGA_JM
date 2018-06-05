package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenPersonaSqlProvider;

public class CenPersonaSqlExtendsProvider extends CenPersonaSqlProvider {

	public String loadPhotography(String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("PER.NIFCIF");
		sql.SELECT("CLI.FOTOGRAFIA");
		sql.FROM("CEN_CLIENTE CLI");
		sql.INNER_JOIN("  CEN_PERSONA PER ON PER.IDPERSONA = CLI.IDPERSONA ");
		sql.WHERE(" PER.IDPERSONA ='" + idPersona + "'");

		return sql.toString();
	}

	// SELECT PER.IDPERSONA,
	// PER.NOMBRE AS DENOMINACION,
	// CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2) AS APELLIDOS,
	// I.IDINSTITUCION AS COLEGIO,
	// COL.NCOLEGIADO,
	// PER.FECHANACIMIENTO,
	// CA.DESCRIPCION AS ESTADOCOLEGIAL,
	// DECODE(COL.SITUACIONRESIDENTE,'0','NO','1','SI') AS RESIDENTE
	// FROM
	// CEN_PERSONA PER
	// LEFT JOIN CEN_NOCOLEGIADO NOCOL ON (PER.IDPERSONA = NOCOL.IDPERSONA AND
	// NOCOL.FECHA_BAJA IS NULL)
	// LEFT JOIN CEN_COLEGIADO COL ON PER.IDPERSONA = COL.IDPERSONA
	// INNER JOIN CEN_INSTITUCION I ON (COL.IDINSTITUCION = I.IDINSTITUCION OR
	// NOCOL.IDINSTITUCION = I.IDINSTITUCION)
	// LEFT JOIN (SELECT A.IDINSTITUCION, A.IDPERSONA, A.IDESTADO FROM
	// CEN_DATOSCOLEGIALESESTADO A,
	// (SELECT IDINSTITUCION, IDPERSONA, MAX(FECHAESTADO) AS FE FROM
	// CEN_DATOSCOLEGIALESESTADO GROUP BY IDINSTITUCION, IDPERSONA) B
	// WHERE A.IDINSTITUCION=B.IDINSTITUCION AND A.IDPERSONA=B.IDPERSONA AND
	// A.FECHAESTADO=B.FE
	// ) DATOSCOLEGIALES ON (DATOSCOLEGIALES.IDPERSONA = PER.IDPERSONA AND
	// DATOSCOLEGIALES.IDINSTITUCION = I.IDINSTITUCION)
	// LEFT JOIN CEN_ESTADOCOLEGIAL ESTADOCOLEGIAL ON ESTADOCOLEGIAL.IDESTADO =
	// DATOSCOLEGIALES.IDESTADO
	// LEFT JOIN GEN_RECURSOS_CATALOGOS CA ON (ESTADOCOLEGIAL.DESCRIPCION =
	// CA.IDRECURSO AND CA.IDLENGUAJE = '1')
	// WHERE
	// PER.IDTIPOIDENTIFICACION NOT IN ('20','50') ;
	// AND PER.NIFCIF = 'NIF'
	// AND UPPER(PER.NOMBRE) = UPPER('NOMBRE')
	// AND UPPER(PER.APELLIDOS1) = UPPER('PRIMERAPELLIDO')
	// AND UPPER(PER.APELLIDOS2) = UPPER('SEGUNDOAPELLIDO');
	// AND COL.NCOLEGIADO = 'NCOLEGIADO'
	// AND I.IDINSTITUCIO IN ('COLEGIOS')

	public String searchPerFisica(int numpagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		String instituciones = "";
		sql.SELECT("PER.IDPERSONA");
		sql.SELECT("PER.NOMBRE AS DENOMINACION");

		sql.SELECT("CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2) AS APELLIDOS)");
		sql.SELECT("I.IDINSTITUCION AS COLEGIO");
		sql.SELECT("COL.NCOLEGIADO");
		sql.SELECT("PER.FECHANACIMIENTO");
		sql.SELECT("CA.DESCRIPCION AS ESTADOCOLEGIAL");
		sql.SELECT("DECODE(COL.SITUACIONRESIDENTE,'0','NO','1','SI') AS RESIDENTE");
		sql.FROM("CEN_PERSONA PER");
		sql.LEFT_OUTER_JOIN("CEN_NOCOLEGIADO NOCOL  ON (PER.IDPERSONA = NOCOL.IDPERSONA AND NOCOL.FECHA_BAJA IS NULL)");
		sql.LEFT_OUTER_JOIN("CEN_COLEGIADO COL  ON PER.IDPERSONA = COL.IDPERSONA");
		sql.INNER_JOIN(
				"CEN_INSTITUCION I ON (COL.IDINSTITUCION = I.IDINSTITUCION OR NOCOL.IDINSTITUCION = I.IDINSTITUCION)");

		sql2.SELECT("A.IDINSTITUCION");
		sql2.SELECT("A.IDPERSONA");
		sql2.SELECT("A.IDESTADO");
		sql2.FROM("CEN_DATOSCOLEGIALESESTADO A");

		sql3.SELECT("IDINSTITUCION");
		sql3.SELECT("IDPERSONA");
		sql3.SELECT("MAX(FECHAESTADO) AS FE");
		sql3.FROM("CEN_DATOSCOLEGIALESESTADO ");
		sql3.GROUP_BY("GROUP BY IDINSTITUCION, IDPERSONA B");
		sql3.WHERE("A.IDINSTITUCION=B.IDINSTITUCION");
		sql3.WHERE("A.IDPERSONA=B.IDPERSONA");
		sql3.WHERE("A.FECHAESTADO=B.FE");

		sql.LEFT_OUTER_JOIN("(" + sql2.toString() + ",(" + sql3.toString()
				+ ") DATOSCOLEGIALES ON (DATOSCOLEGIALES.IDPERSONA  = PER.IDPERSONA AND DATOSCOLEGIALES.IDINSTITUCION = I.IDINSTITUCION");

		busquedaPerFisicaSearchDTO.getIdInstitucion();
		busquedaPerFisicaSearchDTO.getNif();
		busquedaPerFisicaSearchDTO.getNombre();
		busquedaPerFisicaSearchDTO.getPrimerApellido();
		busquedaPerFisicaSearchDTO.getSegundoApellido();
		busquedaPerFisicaSearchDTO.getNumColegiado();

		sql.LEFT_OUTER_JOIN("CEN_ESTADOCOLEGIAL ESTADOCOLEGIAL ON ESTADOCOLEGIAL.IDESTADO = DATOSCOLEGIALES.IDESTADO");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS CA ON (ESTADOCOLEGIAL.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '1')");
		sql.WHERE("PER.IDTIPOIDENTIFICACION NOT IN ('20','50');");
		if (null != busquedaPerFisicaSearchDTO.getNif() && !busquedaPerFisicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql.WHERE("PER.NIFCIF = '" + busquedaPerFisicaSearchDTO.getNif() + "'");
		}
		if (null != busquedaPerFisicaSearchDTO.getNombre()
				&& !busquedaPerFisicaSearchDTO.getNombre().equalsIgnoreCase("")) {
			sql.WHERE("UPPER(PER.NOMBRE) = UPPER('" + busquedaPerFisicaSearchDTO.getNombre() + "')");
		}
		if (null != busquedaPerFisicaSearchDTO.getPrimerApellido()
				&& !busquedaPerFisicaSearchDTO.getPrimerApellido().equalsIgnoreCase("")) {
			sql.WHERE(" UPPER(PER.APELLIDOS1) = UPPER('" + busquedaPerFisicaSearchDTO.getPrimerApellido() + "')");
		}
		if (null != busquedaPerFisicaSearchDTO.getSegundoApellido()
				&& !busquedaPerFisicaSearchDTO.getSegundoApellido().equalsIgnoreCase("")) {
			sql.WHERE("UPPER(PER.APELLIDOS2) = UPPER('" + busquedaPerFisicaSearchDTO.getSegundoApellido() + "')");
		}
		if (null != busquedaPerFisicaSearchDTO.getNumColegiado()
				&& !busquedaPerFisicaSearchDTO.getNumColegiado().equalsIgnoreCase("")) {
			sql.WHERE(" COL.NCOLEGIADO = '" + busquedaPerFisicaSearchDTO.getNumColegiado() + "')");
		}

		if (busquedaPerFisicaSearchDTO.getIdInstitucion().length > 1) {
			for (String string : busquedaPerFisicaSearchDTO.getIdInstitucion()) {
				instituciones += string;
				instituciones += ",";
			}
			instituciones = instituciones.substring(0, instituciones.length() - 1);
		} else if (busquedaPerFisicaSearchDTO.getIdInstitucion().length == 1) {
			instituciones = busquedaPerFisicaSearchDTO.getIdInstitucion()[0];
		}

		if (null != busquedaPerFisicaSearchDTO.getNumColegiado()
				&& !busquedaPerFisicaSearchDTO.getNumColegiado().equalsIgnoreCase("")) {
			sql.WHERE(" COL.NCOLEGIADO = '" + busquedaPerFisicaSearchDTO.getNumColegiado() + "')");
		}
		 
		return sql.toString();

	}

	public String searchJuridica(int numpagina, BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		String instituciones = "";
		sql2.SELECT("consulta.*");

		sql.SELECT_DISTINCT("col.idpersona AS idpersona");
		sql.SELECT_DISTINCT("per.nifcif AS nif");
		sql.SELECT_DISTINCT("per.IDTIPOIDENTIFICACION ");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ',per.apellidos2) ) AS denominacion");
		sql.SELECT_DISTINCT("i.abreviatura AS abreviatura");
		sql.SELECT_DISTINCT("per.fechanacimiento AS fechaconstitucion");
		sql.SELECT_DISTINCT("col.sociedadprofesional AS sociedadprofesional");

		sql.SELECT_DISTINCT("ca.descripcion AS tipo");
		sql.SELECT_DISTINCT("col.fecha_baja AS fecha_baja");
		sql.SELECT_DISTINCT("nvl(COUNT(DISTINCT per2.idpersona),0) AS numerointegrantes");
		sql.SELECT_DISTINCT(
				"LISTAGG(concat(per2.nombre || ' ',concat(per2.apellidos1 || ' ',per2.apellidos2) ),';' WITHIN GROUP(ORDER BY per2.nombre) AS nombresintegrantes");

		sql.FROM("cen_persona per");
		sql.LEFT_OUTER_JOIN("cen_nocolegiado col  ON per.idpersona = col.idpersona");
		sql.LEFT_OUTER_JOIN("cen_institucion i ON col.idinstitucion = i.idinstitucion");
		sql.LEFT_OUTER_JOIN("cen_tiposociedad tiposociedad ON tiposociedad.letracif = col.tipo");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos ca ON ( tiposociedad.descripcion = ca.idrecurso  AND ca.idlenguaje = '1' )");
		sql.LEFT_OUTER_JOIN(
				"cen_gruposcliente_cliente grupos_cliente ON (grupos_cliente.idinstitucion = i.idinstitucion AND col.idpersona = grupos_cliente.idpersona AND ( TO_DATE(grupos_cliente.fecha_inicio,'DD/MM/RRRR') <= SYSDATE    AND ( TO_DATE(grupos_cliente.fecha_baja,'DD/MM/RRRR') >= SYSDATE  OR grupos_cliente.fecha_baja IS NULL)))");
		sql.LEFT_OUTER_JOIN(
				"cen_componentes com ON ( com.idpersona = col.idpersona        AND col.idinstitucion = com.idinstitucion )");
		sql.LEFT_OUTER_JOIN(
				"cen_cliente cli ON ( com.cen_cliente_idpersona = cli.idpersona AND cli.idinstitucion = com.idinstitucion )");
		sql.LEFT_OUTER_JOIN("cen_persona per2 ON per2.idpersona = cli.idpersona");
		
	 
		
		if (null != busquedaPerJuridicaSearchDTO.getIdInstitucion()
				&& !busquedaPerJuridicaSearchDTO.getIdInstitucion().equalsIgnoreCase("")) {
			sql.WHERE("i.idinstitucion in ('" + busquedaPerJuridicaSearchDTO.getIdInstitucion() + "')");
		}
		sql.WHERE("col.fecha_baja IS NULL");
		sql.WHERE("PER.IDTIPOIDENTIFICACION IN ('20','50')");
		if (null != busquedaPerJuridicaSearchDTO.getNumColegiado()
				&& !busquedaPerJuridicaSearchDTO.getNumColegiado().equalsIgnoreCase("")) {
			sql.WHERE(" COL.NCOLEGIADO = '" + busquedaPerJuridicaSearchDTO.getNumColegiado() + "')");
		}
		if (null != busquedaPerJuridicaSearchDTO.getTipo()
				&& !busquedaPerJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql.WHERE("tiposociedad.letracif = '" + busquedaPerJuridicaSearchDTO.getTipo() + "')");
		}
		if (null != busquedaPerJuridicaSearchDTO.getNif()
				&& !busquedaPerJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql.WHERE("per.nifcif = ''" + busquedaPerJuridicaSearchDTO.getNif() + "')");
		}
		sql.GROUP_BY("col.idinstitucion");
		sql.GROUP_BY("col.idpersona");
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
		sql.GROUP_BY("col.fecha_baja) consulta ");
		if (null != busquedaPerJuridicaSearchDTO.getDenominacion()
				&& !busquedaPerJuridicaSearchDTO.getDenominacion().equalsIgnoreCase("")) {
			sql.WHERE("(upper(consulta.denominacion) LIKE upper('%" + busquedaPerJuridicaSearchDTO.getDenominacion() + "%'))");
		}

		sql2.FROM("( " + sql2.toString() + ")");
		return sql2.toString();
	}
	
	
	public String insertSelectiveForCreateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, AdmUsuarios usuario) {
		
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_PERSONA");
		sql.VALUES("IDPERSONA", "(Select max(idpersona) +1 from cen_persona)");
		sql.VALUES("NOMBRE", etiquetaUpdateDTO.getDenominacion());
		sql.VALUES("APELLIDOS1", "");
		sql.VALUES("APELLIDOS2", "");
		sql.VALUES("NIFCIF", etiquetaUpdateDTO.getNif());
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", String.valueOf(usuario.getIdusuario()));
		sql.VALUES("IDTIPOIDENTIFICACION", "(SELECT IDTIPOIDENTIFICACION FROM CEN_TIPOIDENTIFICACION WHERE CODIGOEJIS = 'C')");
		sql.VALUES("FECHANACIMIENTO", "SYSDATE");
		sql.VALUES("IDESTADOCIVIL", "");
		sql.VALUES("NATURALDE", "");
		sql.VALUES("FALLECIDO", "0");
		sql.VALUES("SEXO", "");
		
		return sql.toString();
	}
}
