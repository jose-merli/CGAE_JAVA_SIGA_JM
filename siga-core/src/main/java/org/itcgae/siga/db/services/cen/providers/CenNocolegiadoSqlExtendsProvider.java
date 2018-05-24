package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
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
		
		
		
		sql2.SELECT("COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2)) AS DENOMINACION");
		sql2.SELECT("I.ABREVIATURA AS ABREVIATURA");
		sql2.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql2.SELECT("CA.DESCRIPCION AS TIPO");
		sql2.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql2.SELECT("NVL(COUNT(DISTINCT PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql2.SELECT("LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(PER2.APELLIDOS1 || ' ',PER2.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");
		
		sql2.FROM("CEN_NOCOLEGIADO COL");
		
		sql2.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql2.INNER_JOIN(" CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION "
				+ " LEFT JOIN CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON TIPOSOCIEDAD.LETRACIF = COL.TIPO "
				+ " LEFT JOIN  GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '" + idLenguaje + "')"
				+ " LEFT JOIN CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA)"
				+ " LEFT JOIN CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION )"
				+ " LEFT JOIN CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )"
				+ " LEFT JOIN CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA ");
		
		sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");
		sql2.WHERE("COL.FECHA_BAJA IS NULL");
		if(null != busquedaJuridicaSearchDTO.getNif() && !busquedaJuridicaSearchDTO.getNif().equalsIgnoreCase("")) {
			sql2.WHERE("PER.NIFCIF = '" + busquedaJuridicaSearchDTO.getNif() + "'");
		}
		
		if(!grupos.equalsIgnoreCase("")) {
			sql2.WHERE("GRUPOS_CLIENTE.IDGRUPO IN ("+ grupos +")");
		}
		
		if(null != busquedaJuridicaSearchDTO.getTipo() && !busquedaJuridicaSearchDTO.getTipo().equalsIgnoreCase("")) {
			sql2.WHERE("COL.TIPO = '"+ busquedaJuridicaSearchDTO.getTipo() +"'");
		}
		
		if(null != busquedaJuridicaSearchDTO.getFechaConstitucion() && !busquedaJuridicaSearchDTO.getFechaConstitucion().equals("")) {
			String fechaC = dateFormat.format(busquedaJuridicaSearchDTO.getFechaConstitucion());
			sql2.WHERE("TO_DATE(PER.FECHANACIMIENTO,'DD/MM/RRRR') = TO_DATE('" + fechaC + "', 'DD/MM/RRRR')");
		}
		
		if(busquedaJuridicaSearchDTO.getSociedadProfesional().equals("1")) {
			sql2.WHERE("COL.SOCIEDADPROFESIONAL = '1'");
		}
		else {
			if(null == busquedaJuridicaSearchDTO.getSociedadProfesional() || busquedaJuridicaSearchDTO.getSociedadProfesional().equals("0") || busquedaJuridicaSearchDTO.getSociedadProfesional().equalsIgnoreCase(""))
				sql2.WHERE(" (COL.SOCIEDADPROFESIONAL IS NULL OR COL.SOCIEDADPROFESIONAL = '0' )");
		}
		
		
		sql2.WHERE("TO_DATE(GRUPOS_CLIENTE.FECHA_INICIO,'DD/MM/RRRR') <= SYSDATE");
		sql2.WHERE("(TO_DATE(GRUPOS_CLIENTE.FECHA_BAJA,'DD/MM/RRRR') >= SYSDATE OR GRUPOS_CLIENTE.FECHA_BAJA IS NULL)");
		
		sql2.GROUP_BY("COL.IDINSTITUCION");
		sql2.GROUP_BY("COL.IDPERSONA");
		sql2.GROUP_BY("PER.NIFCIF");
		sql2.GROUP_BY("PER.NOMBRE");
		sql2.GROUP_BY("PER.APELLIDOS1");
		sql2.GROUP_BY("PER.APELLIDOS2");
		sql2.GROUP_BY("I.ABREVIATURA");
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
		
		return sql.toString();
	}
	
	
	
	public String searchHistoricLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		sql2.SELECT("COL.IDPERSONA AS IDPERSONA");
		sql2.SELECT("PER.NIFCIF AS NIF");
		sql2.SELECT("CONCAT(PER.NOMBRE ||' ',CONCAT(PER.APELLIDOS1 || ' ',PER.APELLIDOS2)) AS DENOMINACION");
		sql2.SELECT("I.ABREVIATURA AS ABREVIATURA");
		sql2.SELECT("PER.FECHANACIMIENTO AS FECHACONSTITUCION");
		sql2.SELECT("COL.SOCIEDADPROFESIONAL AS SOCIEDADPROFESIONAL");
		sql2.SELECT("CA.DESCRIPCION AS TIPO");
		sql2.SELECT("COL.FECHA_BAJA AS FECHA_BAJA");
		sql2.SELECT("NVL(COUNT(DISTINCT PER2.IDPERSONA),0) AS NUMEROINTEGRANTES");
		sql2.SELECT("LISTAGG(CONCAT(PER2.NOMBRE ||' ',CONCAT(PER2.APELLIDOS1 || ' ',PER2.APELLIDOS2)), '; ') WITHIN GROUP (ORDER BY PER2.NOMBRE) AS NOMBRESINTEGRANTES");
		
		sql2.FROM("CEN_NOCOLEGIADO COL");
		
		sql2.INNER_JOIN(" CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");
		sql2.INNER_JOIN(" CEN_INSTITUCION I ON COL.IDINSTITUCION = I.IDINSTITUCION "
				+ " LEFT JOIN CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON TIPOSOCIEDAD.LETRACIF = COL.TIPO "
				+ " LEFT JOIN  GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '" + idLenguaje + "')"
				+ " LEFT JOIN CEN_GRUPOSCLIENTE_CLIENTE GRUPOS_CLIENTE ON (GRUPOS_CLIENTE.IDINSTITUCION = I.IDINSTITUCION AND COL.IDPERSONA = GRUPOS_CLIENTE.IDPERSONA)"
				+ " LEFT JOIN CEN_COMPONENTES COM ON (COM.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = COM.IDINSTITUCION )"
				+ " LEFT JOIN CEN_CLIENTE CLI ON (COM.CEN_CLIENTE_IDPERSONA = CLI.IDPERSONA AND CLI.IDINSTITUCION = COM.IDINSTITUCION )"
				+ " LEFT JOIN CEN_PERSONA PER2 ON PER2.IDPERSONA = CLI.IDPERSONA ");
		
		sql2.WHERE("I.IDINSTITUCION = '" + idInstitucion + "'");	
		
		sql2.GROUP_BY("COL.IDINSTITUCION");
		sql2.GROUP_BY("COL.IDPERSONA");
		sql2.GROUP_BY("PER.NIFCIF");
		sql2.GROUP_BY("PER.NOMBRE");
		sql2.GROUP_BY("PER.APELLIDOS1");
		sql2.GROUP_BY("PER.APELLIDOS2");
		sql2.GROUP_BY("I.ABREVIATURA");
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
	
}
