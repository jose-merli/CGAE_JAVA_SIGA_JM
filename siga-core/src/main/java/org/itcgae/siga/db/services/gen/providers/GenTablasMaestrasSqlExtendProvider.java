package org.itcgae.siga.db.services.gen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.CatalogoDeleteDTO;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.db.entities.GenTablasMaestras;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenTablasMaestrasSqlExtendProvider {

	private static Logger LOGGER = LoggerFactory.getLogger(GenTablasMaestrasSqlExtendProvider.class);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectCatalogosByTabla(GenTablasMaestras tablaMaestra,CatalogoRequestDTO catalogo) {
        SQL sql = new SQL();

//        sql.SELECT_DISTINCT(tablaMaestra.getIdcampocodigoext() + " AS CODIGOEXT");
//        sql.SELECT("'" + tablaMaestra.getIdtablamaestra() + "'" + " AS CATALOGO");
//        sql.SELECT("RECURSOS.DESCRIPCION AS DESCRIPCION");
//        sql.SELECT(tablaMaestra.getIdcampocodigo() + " AS IDREGISTRO");
//        sql.SELECT("MAES.LONGITUDCODIGOEXT");
//        sql.SELECT("MAES.LONGITUDDESCRIPCION");
//        sql.FROM(tablaMaestra.getIdtablamaestra() + " TABLA" );
//        sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS RECURSOS ON (RECURSOS.IDRECURSO = " + " TABLA." + tablaMaestra.getIdcampodescripcion() +") ");
//        sql.INNER_JOIN("GEN_TABLAS_MAESTRAS MAES ON MAES.IDTABLAMAESTRA = '"+tablaMaestra.getIdtablamaestra()+"'");
//        
//        sql.WHERE("(RECURSOS.IDINSTITUCION = '" + catalogo.getIdInstitucion() + "' OR RECURSOS.IDINSTITUCION IS NULL)");
//        sql.WHERE("RECURSOS.IDLENGUAJE = '" + catalogo.getIdLenguaje() + "'");
//        sql.WHERE("TABLA.FECHA_BAJA IS NULL ");
//        if (null != catalogo.getCodigoExt() && !catalogo.getCodigoExt().equals("")) {
//        	sql.WHERE(" TABLA." + tablaMaestra.getIdcampocodigoext() + " = '" + catalogo.getCodigoExt()+ "'");
//		}	
//        if (null != catalogo.getDescripcion() && !catalogo.getDescripcion().equals("")) {
//        	sql.WHERE("RECURSOS.DESCRIPCION = '" + catalogo.getDescripcion() + "'");
//		}
//
//        sql.ORDER_BY(" RECURSOS.DESCRIPCION ASC " );
        
        
       
        
      
        
        
        sql.SELECT("TAB.CODIGOEXT AS CODIGOEXT");
        sql.SELECT("REC.DESCRIPCION AS DESCRIPCION");
        sql.SELECT("MAES.IDTABLAMAESTRA AS CATALOGO");
        sql.SELECT("REC.IDRECURSO AS IDREGISTRO");
        sql.SELECT("MAES.LONGITUDCODIGOEXT");
        sql.SELECT("MAES.LONGITUDDESCRIPCION");
        
        sql.FROM(tablaMaestra.getIdtablamaestra() + " TAB" );
        sql.INNER_JOIN("gen_recursos_catalogos REC on TAB.descripcion = REC.idrecurso");
        sql.INNER_JOIN("GEN_TABLAS_MAESTRAS MAES on REC.nombretabla = MAES.idtablamaestra");
        
        sql.WHERE("TAB.fecha_baja is null");
        sql.WHERE("REC.idlenguaje = '"+catalogo.getIdLenguaje()+"' ");
        
        // si el catalogo es local => diferente para cada colegio
        if(catalogo.getLocal().equals("S")) {
        	 sql.WHERE("TAB.idinstitucion = '"+catalogo.getIdInstitucion()+"'");
        }
        
		if (null != catalogo.getCodigoExt() && !catalogo.getCodigoExt().equals("")) {
			sql.WHERE(" TAB." + tablaMaestra.getIdcampocodigoext() + " = '" + catalogo.getCodigoExt() + "'");
		}
		if (null != catalogo.getDescripcion() && !catalogo.getDescripcion().equals("")) {
			sql.WHERE("REC.DESCRIPCION = '" + catalogo.getDescripcion() + "'");
		}
       
        sql.ORDER_BY("REC.descripcion ASC");
        return sql.toString();
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateCodigoExterno(GenTablasMaestras tablaMaestra, CatalogoUpdateDTO catalogo) {
        SQL sql = new SQL();
        sql.UPDATE(tablaMaestra.getIdtablamaestra());
        
        sql.SET("CODIGOEXT = '" + catalogo.getCodigoExt() + "'");

        
        sql.WHERE("DESCRIPCION = '" + catalogo.getIdRegistro() + "'");
        sql.WHERE("IDINSTITUCION = '"+catalogo.getIdInstitucion()+"'");
        //sql.WHERE(tablaMaestra.get)
        return sql.toString();
    }

    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateRecursos(GenTablasMaestras tablaMaestra, CatalogoUpdateDTO catalogo) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_RECURSOS_CATALOGOS");
        
        sql.SET("DESCRIPCION = '" + catalogo.getDescripcion() + "'");

        
        sql.WHERE( " IDRECURSO = ( SELECT DESCRIPCION FROM " + tablaMaestra.getIdtablamaestra() + 
        		" WHERE DESCRIPCION = '" + catalogo.getIdRegistro() + "' AND IDINSTITUCION = '" + catalogo.getIdInstitucion() + "')" );
        sql.WHERE( " IDLENGUAJE = '" + catalogo.getIdLenguaje() + "'");
        sql.WHERE( " IDINSTITUCION = '" + catalogo.getIdInstitucion() + "'");
        return sql.toString();
    }
    
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String deleteRecursos(GenTablasMaestras tablaMaestra, CatalogoDeleteDTO catalogo, Boolean idInstitucion) {
        SQL sql = new SQL();
        sql.UPDATE(tablaMaestra.getIdtablamaestra());
       // String whereIdRegistro = "( " ;
        String whereIdRegistro =  "";
        sql.SET(" FECHA_BAJA = SYSDATE " );
        for (int i = 0; i < catalogo.getIdRegistro().length - 1; i++) {
        	whereIdRegistro =  whereIdRegistro.concat( catalogo.getIdRegistro()[i] + ",");
		}
        whereIdRegistro =  whereIdRegistro.concat(catalogo.getIdRegistro()[catalogo.getIdRegistro().length - 1]);
        //whereIdRegistro =  whereIdRegistro.concat(")");
        if (idInstitucion) {
        	sql.WHERE( " IDINSTITUCION = '" + catalogo.getIdInstitucion() +"'" );
		}	
        
        sql.WHERE("DESCRIPCION IN (" + whereIdRegistro + " )");
        
        return sql.toString();
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String createCatalogo(GenTablasMaestras tablaMaestra, CatalogoUpdateDTO catalogo, Boolean idInstitucion, Integer usuarioModificacion) {
        SQL sql = new SQL();
        LOGGER.info(String.valueOf(idInstitucion));
        
        sql.INSERT_INTO(tablaMaestra.getIdtablamaestra());

        if (idInstitucion) {
        	sql.VALUES("IDINSTITUCION", "'" + catalogo.getIdInstitucion() +"'");
		}	
        

        sql.VALUES(tablaMaestra.getIdcampocodigo(), "(select MAX("+ tablaMaestra.getIdcampocodigo() +" )  + 1 from "+ tablaMaestra.getIdtablamaestra()   +")" );
        sql.VALUES(tablaMaestra.getIdcampocodigoext(), "'" + catalogo.getCodigoExt() +"'");
        sql.VALUES("FECHAMODIFICACION", "SYSDATE");
        sql.VALUES("BLOQUEADO", "'N'");
        sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuarioModificacion)+"'");
        sql.VALUES("DESCRIPCION", ("(SELECT MAX(IDRECURSO) IDRECURSO FROM (" + 
        		"select TO_NUMBER(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'99999999999') IDRECURSO from gen_recursos_catalogos  where NOMBRETABLA = " + "'" + tablaMaestra.getIdtablamaestra() +"' ))" ));
        
        return sql.toString();
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String createRecursos(GenTablasMaestras tablaMaestra, CatalogoUpdateDTO catalogo, Boolean idInstitucion, Integer usuarioModificacion) {
        SQL sql = new SQL();
        
        sql.INSERT_INTO("GEN_RECURSOS_CATALOGOS");
        sql.VALUES("IDRECURSO", ("(SELECT MAX(IDRECURSO)+1 IDRECURSO FROM (" + 
        		"select TO_NUMBER(REPLACE(REPLACE(REPLACE(IDRECURSO,'_',''),'-',''),'NULL',''),'99999999999') IDRECURSO from gen_recursos_catalogos  where NOMBRETABLA = " + "'" + tablaMaestra.getIdtablamaestra() +"' ))" ));
       	sql.VALUES("IDINSTITUCION",  "'" +catalogo.getIdInstitucion()+"'");
        sql.VALUES("DESCRIPCION",  "'" +catalogo.getDescripcion()+"'");
        sql.VALUES("IDLENGUAJE", "'" + catalogo.getIdLenguaje()+"'");
        sql.VALUES("FECHAMODIFICACION", "SYSDATE");
        sql.VALUES("NOMBRETABLA",  "'" +tablaMaestra.getIdtablamaestra()+"'");
        sql.VALUES("CAMPOTABLA",  "'" +tablaMaestra.getIdcampodescripcion()+"'");
        sql.VALUES("USUMODIFICACION",  "'" +String.valueOf(usuarioModificacion)+"'");
        sql.VALUES("IDRECURSOALIAS",  "' '");
        
        return sql.toString();
    }    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectColumnName(GenTablasMaestras tablaMaestra) {
        SQL sql = new SQL();

        sql.SELECT(" COUNT(*) AS IDINSTITUCION");
        
        sql.FROM(" all_tab_columns " );
        
        sql.WHERE("TABLE_NAME = '" + tablaMaestra.getIdtablamaestra() + "'");
        sql.WHERE("COLUMN_NAME = 'IDINSTITUCION' ");
        
        return sql.toString();
    }
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TABLAS_MAESTRAS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectRecursosHistorico(GenTablasMaestras tablaMaestra,CatalogoRequestDTO catalogo) {
        SQL sql = new SQL();

        sql.SELECT_DISTINCT(tablaMaestra.getIdcampocodigoext() + " AS CODIGOEXT");
        sql.SELECT("'" + tablaMaestra.getIdtablamaestra() + "'" + " AS CATALOGO");
        sql.SELECT("RECURSOS.DESCRIPCION AS DESCRIPCION");
        sql.SELECT(tablaMaestra.getIdcampocodigo() + " AS IDREGISTRO");
        sql.SELECT("TABLA.FECHA_BAJA AS FECHABAJA");
        sql.FROM(tablaMaestra.getIdtablamaestra() + " TABLA" );
        
        sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS RECURSOS ON (RECURSOS.IDRECURSO = " + " TABLA." + tablaMaestra.getIdcampodescripcion() +") ");
        
        sql.WHERE("(RECURSOS.IDINSTITUCION = '" + catalogo.getIdInstitucion() + "' OR RECURSOS.IDINSTITUCION IS NULL)");
        sql.WHERE("RECURSOS.IDLENGUAJE = '" + catalogo.getIdLenguaje() + "'");
       // sql.WHERE("TABLA.FECHA_BAJA IS NULL ");

        sql.ORDER_BY(" RECURSOS.DESCRIPCION ASC " );
        
        return sql.toString();
    }
    
    
    
    public String selectUpdateNoRepetidosCodigoExtyDescripcion(GenTablasMaestras tablaMaestra,CatalogoUpdateDTO catalogoUpdate) {
    	SQL sql = new SQL();    	
    	
		sql.SELECT("REC.descripcion");
		sql.SELECT("TAB.DESCRIPCION AS IDREGISTRO");
		sql.SELECT("TAB.CODIGOEXT");
		sql.SELECT("REC.NOMBRETABLA AS CATALOGO");
		sql.SELECT("REC.idinstitucion");
		sql.SELECT("TAB.FECHA_BAJA");
		
		sql.FROM(tablaMaestra.getIdtablamaestra() + " TAB" );
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON(REC.IDRECURSO = " + " TAB." + tablaMaestra.getIdcampodescripcion() +") ");
		sql.WHERE(" REC.idinstitucion = '" + catalogoUpdate.getIdInstitucion() + "'");
		sql.WHERE(" TAB.FECHA_BAJA is null ");
		sql.WHERE("REC.idlenguaje = '"+ catalogoUpdate.getIdLenguaje() +"'");
		
		// comprueba si existen otros registros con esa descripcion
		if(!catalogoUpdate.getDescripcion().equals("")) {
			sql.WHERE(" upper(REC.descripcion) = upper('"+catalogoUpdate.getDescripcion()+"')");
			sql.WHERE("TAB.DESCRIPCION != '"+ catalogoUpdate.getIdRegistro() +"'");
		}
		
		// comprueba si existen otros registros con ese codigoExt
		if(!catalogoUpdate.getCodigoExt().equals("")) {
			sql.WHERE("upper(TAB.CODIGOEXT) = upper('"+catalogoUpdate.getCodigoExt()+"')");
			sql.WHERE("TAB.DESCRIPCION != '"+ catalogoUpdate.getIdRegistro() +"'");
		}
		
		// comprueba codigoext y descripcion. Esto pasa si codigoExt = ""
		if(!catalogoUpdate.getCodigoExt().equals("") && !catalogoUpdate.getDescripcion().equals("")) {
			sql.WHERE("upper(TAB.CODIGOEXT) = upper('"+ catalogoUpdate.getCodigoExt() + "')");
			sql.WHERE(" upper(REC.descripcion) = upper('"+ catalogoUpdate.getDescripcion() +"')");
		}
		
    	return sql.toString();
    }
    
    
    public String selectCreateNoRepetidosCodigoExtyDescripcion(GenTablasMaestras tablaMaestra,CatalogoUpdateDTO catalogoUpdate) {
    	SQL sql = new SQL();    	
    	
		sql.SELECT("REC.descripcion");
		sql.SELECT("TAB.DESCRIPCION AS IDREGISTRO");
		sql.SELECT("TAB.CODIGOEXT");
		sql.SELECT("REC.NOMBRETABLA AS CATALOGO");
		sql.SELECT("REC.idinstitucion");
		sql.SELECT("TAB.FECHA_BAJA");
		
		sql.FROM(tablaMaestra.getIdtablamaestra() + " TAB" );
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON(REC.IDRECURSO = " + " TAB." + tablaMaestra.getIdcampodescripcion() +") ");
		sql.WHERE(" REC.idinstitucion = '" + catalogoUpdate.getIdInstitucion() + "'");
		sql.WHERE(" TAB.FECHA_BAJA is null ");
		sql.WHERE("REC.idlenguaje = '"+ catalogoUpdate.getIdLenguaje() +"'");
		
		// comprueba si existen otros registros con ese codigoExt
		if(!catalogoUpdate.getCodigoExt().equals("")) {
			sql.WHERE("upper(TAB.CODIGOEXT) = upper('"+catalogoUpdate.getCodigoExt()+"')");
		}
//		
//		// comprueba codigoext y descripcion. Esto pasa si codigoExt = ""
//		if(!catalogoUpdate.getCodigoExt().equals("") && !catalogoUpdate.getDescripcion().equals("")) {
//			sql.WHERE("upper(TAB.CODIGOEXT) = upper('"+ catalogoUpdate.getCodigoExt() + "')");
//			sql.WHERE(" upper(REC.descripcion) = upper('"+ catalogoUpdate.getDescripcion() +"')");
//		}
		
    	return sql.toString();
    }
    
}