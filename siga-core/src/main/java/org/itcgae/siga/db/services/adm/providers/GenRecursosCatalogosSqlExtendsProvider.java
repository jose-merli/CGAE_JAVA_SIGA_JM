package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosSqlProvider;

public class GenRecursosCatalogosSqlExtendsProvider extends GenRecursosCatalogosSqlProvider{

	public String getCatalogEntity(String idInstitucion){
		SQL sql = new SQL();
		
		sql.SELECT("distinct REC.NOMBRETABLA");
		sql.SELECT(" MAES.LOCAL");
		sql.FROM("GEN_RECURSOS_CATALOGOS REC");
		sql.INNER_JOIN("GEN_TABLAS_MAESTRAS MAES ON REC.NOMBRETABLA = MAES.IDTABLAMAESTRA");
		
		if(!idInstitucion.equals(SigaConstants.InstitucionGeneral))
			sql.WHERE("MAES.LOCAL = 'S'");
		
		sql.ORDER_BY("REC.NOMBRETABLA");
		return sql.toString();
	}
	
	
	public String getCatalogSearch(int numPagina, MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO, String idInstitucion, String campoTabla) {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT REC.IDRECURSO");
		sql.SELECT("NOMBRETABLA");
		sql.SELECT("CONCAT(IDRECURSO, CONCAT(' ',REC.IDRECURSOALIAS)) AS IDRECURSOALIAS ");
		sql.SELECT(" (Select DESCRIPCION From GEN_RECURSOS_CATALOGOS REC2 WHERE REC2.IDLENGUAJE = '" + multiidiomaCatalogoSearchDTO.getIdiomaBusqueda()  + "' AND REC2.IDRECURSO = REC.IDRECURSO) AS DESCRIPCIONBUSCAR");
		sql.SELECT(" (Select DESCRIPCION From GEN_RECURSOS_CATALOGOS REC2 WHERE REC2.IDLENGUAJE = '" + multiidiomaCatalogoSearchDTO.getIdiomaTraduccion() + "' AND REC2.IDRECURSO = REC.IDRECURSO) AS DESCRIPCIONTRADUCIR ");
		sql.SELECT(" '" +  multiidiomaCatalogoSearchDTO.getIdiomaBusqueda()  + "' as IDLENGUAJEBUSCAR ");
		sql.SELECT(" '" + multiidiomaCatalogoSearchDTO.getIdiomaTraduccion() + "' as IDLENGUAJETRADUCIR ");
		sql.FROM(" GEN_RECURSOS_CATALOGOS REC ");
		sql.INNER_JOIN(multiidiomaCatalogoSearchDTO.getNombreTabla() +  " TAB on TAB." + campoTabla + "= REC.IDRECURSO");
		sql.WHERE(" REC.IDLENGUAJE = '"+ multiidiomaCatalogoSearchDTO.getIdiomaBusqueda()  + "'");
		sql.WHERE(" REC.NOMBRETABLA = '" + multiidiomaCatalogoSearchDTO.getNombreTabla() + "'");
		sql.WHERE("TAB.FECHA_BAJA IS NULL"); 
		
		if(multiidiomaCatalogoSearchDTO.getLocal().equals("S"))
			sql.WHERE(" REC.IDINSTITUCION = '" + idInstitucion + "'");
		
		sql.ORDER_BY(" DESCRIPCIONBUSCAR ");
		
		return sql.toString();
	}
	
	public String insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario, String grupo,String nombreTabla, String campoTabla) {
		SQL sql = new SQL();
	
		sql.INSERT_INTO("GEN_RECURSOS_CATALOGOS");		
		sql.VALUES("IDRECURSO", "(SELECT MAX(IDRECURSO) + 1 FROM GEN_RECURSOS_CATALOGOS WHERE NOMBRETABLA = 'CEN_GRUPOSCLIENTE')");
		sql.VALUES("DESCRIPCION", "'"+ grupo + "'");
		sql.VALUES("IDLENGUAJE", "'"+ usuario.getIdlenguaje() + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'"+ String.valueOf(usuario.getIdusuario()) + "'");
		sql.VALUES("IDINSTITUCION", "'"+ idInstitucion + "'");
		sql.VALUES("NOMBRETABLA", "'"+nombreTabla + "'");
		sql.VALUES("CAMPOTABLA", "'"+ campoTabla + "'");
		sql.VALUES("IDRECURSOALIAS", "(SELECT LOWER(nombretabla||'.'||campotabla||'.'||idinstitucion||'.'||(count(idRecurso)+2))  FROM  GEN_RECURSOS_CATALOGOS CATALOGO "
				+ "WHERE CATALOGO.IDINSTITUCION = '" + idInstitucion +"' AND NOMBRETABLA = '"+ nombreTabla +"'   AND IDLENGUAJE = '"+ usuario.getIdlenguaje() +"' "
				+ "group by nombretabla,campotabla,idinstitucion)");

			
		return sql.toString();
	}
	
	public String getMaxIdRecurso() {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDRECURSO)");
		sql.FROM("GEN_RECURSOS_CATALOGOS");
		sql.WHERE("NOMBRETABLA = 'CEN_GRUPOSCLIENTE'");
		
		return sql.toString();
	}
	
	public String getMaxIdRecursoCatalogo(String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDRECURSO) AS IDRECURSO");
		sql.FROM("(select TO_NUMBER(REPLACE(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'null',''),'99999999999999') IDRECURSO from gen_recursos_catalogos)");
		
		return sql.toString();
	}
}
