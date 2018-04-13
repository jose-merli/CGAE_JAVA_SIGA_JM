package org.itcgae.siga.db.services.gen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.CatalogoDeleteDTO;
import org.itcgae.siga.DTOs.adm.CatalogoMaestroItem;
import org.itcgae.siga.DTOs.adm.CatalogoRequestDTO;
import org.itcgae.siga.DTOs.adm.CatalogoUpdateDTO;
import org.itcgae.siga.DTOs.adm.InstitucionDTO;
import org.itcgae.siga.db.entities.GenTablasMaestras;
import org.itcgae.siga.db.mappers.GenTablasMaestrasMapper;
import org.itcgae.siga.db.services.gen.providers.GenTablasMaestrasSqlExtendProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface GenTablasMaestrasExtendsMapper extends GenTablasMaestrasMapper{
 
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="selectRecursos")
    @Results({
        @Result(column="CATALOGO", property="catalogo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="CODIGOEXT", property="codigoExt", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idInstitucion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDREGISTRO", property="idRegistro", jdbcType=JdbcType.VARCHAR)

    })
    List<CatalogoMaestroItem> selectCatalogosByTabla(GenTablasMaestras example,CatalogoRequestDTO catalogo);
    
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="updateCodigoExterno")
    int updateCodigoExterno(GenTablasMaestras example,CatalogoUpdateDTO catalogo);

    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="updateRecursos")
    int updateRecursos(GenTablasMaestras example,CatalogoUpdateDTO catalogo);
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="deleteRecursos")
    int deleteRecursos(GenTablasMaestras example,CatalogoDeleteDTO catalogo);

    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="selectColumnName")
    @Results({
        @Result(column="IDINSTITUCION", property="idInstitucion", jdbcType=JdbcType.VARCHAR)

    })
    InstitucionDTO selectColumnName(GenTablasMaestras example);
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @InsertProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="createRecursos")
    int createRecursos(GenTablasMaestras example,CatalogoUpdateDTO catalogo, Boolean idInstitucion,  Integer usuario);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @InsertProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="createCatalogo")
    int createCatalogo(GenTablasMaestras example,CatalogoUpdateDTO catalogo, Boolean idInstitucion,  Integer usuario);
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenTablasMaestrasSqlExtendProvider.class, method="selectRecursosHistorico")
    @Results({
        @Result(column="CATALOGO", property="catalogo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="CODIGOEXT", property="codigoExt", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idInstitucion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDREGISTRO", property="idRegistro", jdbcType=JdbcType.VARCHAR)

    })
    List<CatalogoMaestroItem> selectCatalogosHistoricoByTabla(GenTablasMaestras example,CatalogoRequestDTO catalogo);
   
   
}