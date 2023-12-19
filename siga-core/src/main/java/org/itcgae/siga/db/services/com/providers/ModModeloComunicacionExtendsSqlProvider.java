package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;
import org.itcgae.siga.commons.constants.SigaConstants;

public class ModModeloComunicacionExtendsSqlProvider {

	public String selectModelosComunicacion(String idInstitucion, DatosModelosComunicacionesSearch filtros,
			String idLenguaje, boolean historico) {

		SQL sql = new SQL();

		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("modelo.IDMODELOCOMUNICACION");
		sql.SELECT("modelo.NOMBRE");
		sql.SELECT("modelo.VISIBLE");
		sql.SELECT("modelo.ORDEN");
		sql.SELECT("modelo.IDINSTITUCION");
		sql.SELECT("modelo.DESCRIPCION");
		sql.SELECT("modelo.FECHABAJA");
		sql.SELECT("modelo.PRESELECCIONAR");
		sql.SELECT("modelo.IDCLASECOMUNICACION");
		sql.SELECT("modelo.FECHABAJA");
		sql.SELECT("inst.ABREVIATURA");
		sql.SELECT("modelo.PORDEFECTO");
		sql.SELECT("modelo.IDPLANTILLAENVIOS");
		sql.SELECT("modelo.IDTIPOENVIOS");
		sql.SELECT("modelo.INFORMEUNICO");
		sql.SELECT(
				"(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = modelo.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
						+ idLenguaje + "') AS TIPOENVIO");
		sql.SELECT("(SELECT CAT2.DESCRIPCION FROM MOD_CLASECOMUNICACIONES CLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT2 ON CAT2.IDRECURSO = CLA.DESCRIPCION WHERE CLA.IDCLASECOMUNICACION = modelo.IDCLASECOMUNICACION AND CLA. CAT2.IDLENGUAJE = '"
						+ idLenguaje +"') AS NOMBRECLASE");

		sql.FROM("MOD_MODELOCOMUNICACION modelo");

		sql.INNER_JOIN("MOD_CLASECOMUNICACIONES clase ON modelo.IDCLASECOMUNICACION = clase.IDCLASECOMUNICACION");
		sql.INNER_JOIN("CEN_INSTITUCION inst ON inst.IDINSTITUCION = modelo.IDINSTITUCION");

		if (filtros.getIdInstitucion() != null && !filtros.getIdInstitucion().trim().equals("")) {
			if (filtros.getIdInstitucion().equalsIgnoreCase(SigaConstants.IDINSTITUCION_0)) {
				sql.WHERE("(modelo.IDINSTITUCION = '2000' AND modelo.PORDEFECTO = 'SI')");
			} else if (filtros.getIdInstitucion().equalsIgnoreCase(String.valueOf(SigaConstants.IDINSTITUCION_2000))) {
				sql.WHERE("(modelo.IDINSTITUCION = '2000' AND modelo.PORDEFECTO = 'NO')");
			} else {
				sql.WHERE("modelo.IDINSTITUCION = '" + filtros.getIdInstitucion() + "'");
			}
		}
		if (filtros.getIdClaseComunicacion() != null && !filtros.getIdClaseComunicacion().trim().equals("")) {
			sql.WHERE("modelo.IDCLASECOMUNICACION = '" + filtros.getIdClaseComunicacion() + "'");
		}

		if (filtros.getNombre() != null && !filtros.getNombre().trim().equals("")) {
			sql.WHERE("lower(modelo.NOMBRE) LIKE lower('%" + filtros.getNombre() + "%')");
		}
		if (filtros.getPreseleccionar() != null && !filtros.getPreseleccionar().trim().equals("")) {
			sql.WHERE("modelo.PRESELECCIONAR = '" + filtros.getPreseleccionar() + "'");
		}
		if (filtros.getVisible() != null && !filtros.getVisible().trim().equals("")) {
			sql.WHERE("modelo.VISIBLE = '" + filtros.getVisible() + "'");
		}

		sql.WHERE("(modelo.IDINSTITUCION = '" + idInstitucion
				+ "' OR (modelo.IDINSTITUCION = '2000' AND modelo.PORDEFECTO = 'SI'))");

		if (historico) {
			if (filtros.getFechaBaja() != null) {
				String fechaBaja = dateFormat.format(filtros.getFechaBaja());
				String fechaBaja2 = dateFormat.format(filtros.getFechaBaja());
				fechaBaja += " 00:00:00";
				fechaBaja2 += " 23:59:59";
				sql.WHERE("(modelo.FECHABAJA >= TO_DATE('" + fechaBaja
						+ "', 'DD/MM/YYYY HH24:MI:SS') AND modelo.FECHABAJA <= TO_DATE('" + fechaBaja2
						+ "', 'DD/MM/YYYY HH24:MI:SS'))");
			}
		} else {
			sql.WHERE("modelo.FECHABAJA is NULL");
		}

		if (historico) {
			sql.ORDER_BY("modelo.FECHABAJA ASC, modelo.ORDEN ASC");
		} else {
			sql.ORDER_BY("modelo.ORDEN ASC");
		}

		return sql.toString();
	}

	public String selectModelosComunicacionDialg(String idInstitucionLogueada, String idInstitucion, String idClaseComunicacion, String idModulo,
			String idLenguaje, String idConsulta, List<String> perfiles) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("MODELO.IDCLASECOMUNICACION, MODELO.IDMODELOCOMUNICACION, MODELO.NOMBRE");
		sql.SELECT("MODELO.IDPLANTILLAENVIOS");
		sql.SELECT("MODELO.IDTIPOENVIOS");
		sql.SELECT("MODELO.VISIBLE");
		sql.SELECT("MODELO.PRESELECCIONAR");
		sql.SELECT("MODELO.INFORMEUNICO");
		sql.SELECT("MODELO.ORDEN");
		sql.SELECT(
				"(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = MODELO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
						+ idLenguaje + "') AS TIPOENVIO");

		sql.FROM("MOD_MODELOCOMUNICACION MODELO");
		sql.JOIN("MOD_CLASECOMUNICACIONES CLASE ON CLASE.IDCLASECOMUNICACION = MODELO.IDCLASECOMUNICACION");

		sql.JOIN( // = MODELO.IDINSTITUCION
					"MOD_MODELO_PERFILES PERFILES ON PERFILES.IDMODELOCOMUNICACION = MODELO.IDMODELOCOMUNICACION AND PERFILES.IDINSTITUCION = " + idInstitucionLogueada);

		if(idInstitucion.equals(SigaConstants.IDINSTITUCION_2000.toString())) {
			
			sql.WHERE("MODELO.IDCLASECOMUNICACION = " + idClaseComunicacion + "  AND MODELO.FECHABAJA IS NULL AND MODELO.IDINSTITUCION = "+idInstitucion);
			sql.WHERE("MODELO.VISIBLE = '1'");

		}else {

			// CLASE.IDMODULO = " + idModulo + " OR 
			sql.WHERE("MODELO.IDCLASECOMUNICACION = " + idClaseComunicacion + " AND MODELO.FECHABAJA IS NULL ");	
			sql.WHERE("((MODELO.IDINSTITUCION = " + idInstitucion + " AND MODELO.VISIBLE = '1') or (MODELO.IDINSTITUCION = " + SigaConstants.IDINSTITUCION_2000 + " AND MODELO.VISIBLE = '1' and modelo.pordefecto = 'SI'))");


		}

		if (idConsulta != null) {
			sql.WHERE(
					"MODELO.IDMODELOCOMUNICACION IN (SELECT IDMODELOCOMUNICACION FROM MOD_PLANTILLADOC_CONSULTA WHERE IDINSTITUCION_CONSULTA='"
							+ idInstitucion + "' AND IDCONSULTA='" + idConsulta + "' AND FECHABAJA IS NULL)");
		}

		if (perfiles != null && perfiles.size() > 0) {
			sql.WHERE("PERFILES.IDPERFIL IN (" + String.join(",", perfiles) + ")");
		}
		
		sql.ORDER_BY("MODELO.ORDEN ASC");

		return sql.toString();
	}
	
	public String selectModelosComunicacionDialgConConsultaDestinatarios(String idInstitucionLogueada, String idInstitucion, String idClaseComunicacion, String idModulo,
			String idLenguaje, String idConsulta, List<String> perfiles) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("MODELO.IDCLASECOMUNICACION, MODELO.IDMODELOCOMUNICACION, MODELO.NOMBRE");
		sql.SELECT("MODELO.IDPLANTILLAENVIOS");
		sql.SELECT("MODELO.IDTIPOENVIOS");
		sql.SELECT("MODELO.VISIBLE");
		sql.SELECT("MODELO.PRESELECCIONAR");
		sql.SELECT("MODELO.INFORMEUNICO");
		sql.SELECT("MODELO.ORDEN");
		sql.SELECT("(select LISTAGG(distinct cc.DESCRIPCION, ', ') "
				+ "    from MOD_PLANTILLADOC_CONSULTA mpc  "
				+ "    JOIN CON_CONSULTA cc ON  cc.IDINSTITUCION = mpc.IDINSTITUCION_CONSULTA and cc.IDCONSULTA = mpc.IDCONSULTA and cc.IDOBJETIVO = 1 "
				+ "    where mpc.IDMODELOCOMUNICACION = modelo.IDMODELOCOMUNICACION) AS NOMBRECONSULTADESTINATARIOS");
		sql.SELECT(
				"(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = MODELO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
						+ idLenguaje + "') AS TIPOENVIO");

		sql.FROM("MOD_MODELOCOMUNICACION MODELO");
		sql.JOIN("MOD_CLASECOMUNICACIONES CLASE ON CLASE.IDCLASECOMUNICACION = MODELO.IDCLASECOMUNICACION");

		sql.JOIN( // = MODELO.IDINSTITUCION
					"MOD_MODELO_PERFILES PERFILES ON PERFILES.IDMODELOCOMUNICACION = MODELO.IDMODELOCOMUNICACION AND PERFILES.IDINSTITUCION = " + idInstitucionLogueada);

		if(idInstitucion.equals(SigaConstants.IDINSTITUCION_2000.toString())) {
			
			sql.WHERE("MODELO.IDCLASECOMUNICACION = " + idClaseComunicacion + "  AND MODELO.FECHABAJA IS NULL AND MODELO.IDINSTITUCION = "+idInstitucion);
			sql.WHERE("MODELO.VISIBLE = '1'");

		}else {

			// CLASE.IDMODULO = " + idModulo + " OR 
			sql.WHERE("MODELO.IDCLASECOMUNICACION = " + idClaseComunicacion + " AND MODELO.FECHABAJA IS NULL ");	
			sql.WHERE("((MODELO.IDINSTITUCION = " + idInstitucion + " AND MODELO.VISIBLE = '1') or (MODELO.IDINSTITUCION = " + SigaConstants.IDINSTITUCION_2000 + " AND MODELO.VISIBLE = '1' and modelo.pordefecto = 'SI'))");


		}

		if (idConsulta != null) {
			sql.WHERE(
					"MODELO.IDMODELOCOMUNICACION IN (SELECT IDMODELOCOMUNICACION FROM MOD_PLANTILLADOC_CONSULTA WHERE IDINSTITUCION_CONSULTA='"
							+ idInstitucion + "' AND IDCONSULTA='" + idConsulta + "' AND FECHABAJA IS NULL)");
		}

		if (perfiles != null && perfiles.size() > 0) {
			sql.WHERE("PERFILES.IDPERFIL IN (" + String.join(",", perfiles) + ")");
		}
		
		sql.ORDER_BY("MODELO.ORDEN ASC");

		return sql.toString();
	}

	public String selectPlantillaModelo(String idModeloComunicacion, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("MPLANTILLA.IDPLANTILLAENVIOS AS VALUE, PLANTILLA.NOMBRE AS LABEL");
		sql.FROM("MOD_MODELO_PLANTILLAENVIO MPLANTILLA");
		sql.JOIN(
				"ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDPLANTILLAENVIOS = MPLANTILLA.IDPLANTILLAENVIOS AND MPLANTILLA.IDINSTITUCION = PLANTILLA.IDINSTITUCION AND MPLANTILLA.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS");
		sql.WHERE("MPLANTILLA.IDMODELOCOMUNICACION = '" + idModeloComunicacion + "'");
		sql.WHERE("PLANTILLA.IDINSTITUCION = " + idInstitucion + " AND MPLANTILLA.FECHABAJA IS NULL");

		return sql.toString();
	}

	public String selectTipoEnvioPlantilla(String idLenguaje, String idPlantilla) {

		SQL sql = new SQL();
		sql.SELECT("TIPO.IDTIPOENVIOS AS VALUE, CAT.DESCRIPCION");
		sql.FROM("ENV_TIPOENVIOS TIPO");
		sql.JOIN("GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = TIPO.NOMBRE AND CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("TIPO.IDTIPOENVIOS = '" + idPlantilla + "'");

		return sql.toString();
	}

	public String selectModelosClasesComunicacion(String idClasesComunicacion) {

		SQL sql = new SQL();

		sql.SELECT("MODELO.IDMODELOCOMUNICACION as value, MODELO.NOMBRE as label");
		sql.FROM("MOD_MODELOCOMUNICACION MODELO");
		sql.WHERE("MODELO.IDCLASECOMUNICACION IN (" + idClasesComunicacion + ")");

		return sql.toString();
	}

	public String comprobarNombreDuplicado(String nombreModelo) {

		SQL sql = new SQL();
		sql.SELECT("max((nvl(SUBSTR(nombre,LENGTH('" + nombreModelo + "')+1,LENGTH(nombre)),0))+1) as NOMBRE");
		sql.FROM("mod_modelocomunicacion");
		sql.WHERE("lower(nombre) like lower('" + nombreModelo + "%')");

		return sql.toString();
	}

	public String selectModelo(String idModelo, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("modelo.IDMODELOCOMUNICACION");
		sql.SELECT("modelo.NOMBRE");
		sql.SELECT("modelo.VISIBLE");
		sql.SELECT("modelo.ORDEN");
		sql.SELECT("modelo.IDINSTITUCION");
		sql.SELECT("modelo.DESCRIPCION");
		sql.SELECT("modelo.FECHABAJA");
		sql.SELECT("modelo.PRESELECCIONAR");
		sql.SELECT("modelo.IDCLASECOMUNICACION");
		sql.SELECT("modelo.FECHABAJA");
		sql.SELECT("clase.NOMBRE AS NOMBRECLASE");
		sql.SELECT("inst.ABREVIATURA");
		sql.SELECT("modelo.PORDEFECTO");
		sql.SELECT("modelo.IDPLANTILLAENVIOS");
		sql.SELECT("modelo.IDTIPOENVIOS");
		sql.SELECT("modelo.INFORMEUNICO");
		sql.SELECT(
				"(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = modelo.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
						+ idLenguaje + "') AS TIPOENVIO");

		sql.FROM("MOD_MODELOCOMUNICACION modelo");

		sql.INNER_JOIN("MOD_CLASECOMUNICACIONES clase ON modelo.IDCLASECOMUNICACION = clase.IDCLASECOMUNICACION");
		sql.INNER_JOIN("CEN_INSTITUCION inst ON inst.IDINSTITUCION = modelo.IDINSTITUCION");

		sql.WHERE("modelo.IDMODELOCOMUNICACION = '" + idModelo + "'");
		sql.WHERE("modelo.FECHABAJA is NULL");

		sql.ORDER_BY("IDMODELOCOMUNICACION ASC");

		return sql.toString();
	}

	public String selectPlantillaPorDefecto(String idModelo, Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("PLANTILLA.NOMBRE, PLANTILLA.IDTIPOENVIOS, MODELO.IDINSTITUCION,"
				+ "(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
				+ idLenguaje + "') AS TIPOENVIO");
		sql.SELECT("PLANTILLA.IDPLANTILLAENVIOS");
		sql.FROM("MOD_MODELO_PLANTILLAENVIO MODELO");
		sql.JOIN(
				"ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDPLANTILLAENVIOS = MODELO.IDPLANTILLAENVIOS AND PLANTILLA.IDINSTITUCION = '"
						+ idInstitucion + "' AND MODELO.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS");
		sql.WHERE("MODELO.IDMODELOCOMUNICACION = '" + idModelo + "'");

		return sql.toString();
	}
	public String selectTodasPlantillasBorradas(String idModeloComunicacion, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("");
		
		sql.FROM("MOD_MODELOCOMUNICACION mm");
		sql.LEFT_OUTER_JOIN("MOD_MODELO_PLANTILLADOCUMENTO mp\r\n" + 
				"	ON mm.IDMODELOCOMUNICACION = mp.IDMODELOCOMUNICACION");
		
		sql.LEFT_OUTER_JOIN("LEFT JOIN MOD_PLANTILLADOCUMENTO pl "
				+ "ON pl.IDPLANTILLADOCUMENTO = mp.IDPLANTILLADOCUMENTO");
		sql.WHERE("IDINSTITUCION = "+ idInstitucion);
		sql.WHERE("IDMODELOCOMUNICACION = "+idModeloComunicacion);
		
		return sql.toString();
	}
}
