package org.itcgae.siga.db.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.GenMenu;
import org.itcgae.siga.db.entities.GenMenuExample;

public interface GenMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenMenuSqlProvider.class, method="countByExample")
    long countByExample(GenMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @DeleteProvider(type=GenMenuSqlProvider.class, method="deleteByExample")
    int deleteByExample(GenMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Delete({
        "delete from GEN_MENU",
        "where IDMENU = #{idmenu,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String idmenu);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Insert({
        "insert into GEN_MENU (IDMENU, ORDEN, ",
        "TAGWIDTH, IDPARENT, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "URI_IMAGEN, IDRECURSO, ",
        "GEN_MENU_IDMENU, IDPROCESO, ",
        "IDLENGUAJE)",
        "values (#{idmenu,jdbcType=VARCHAR}, #{orden,jdbcType=DECIMAL}, ",
        "#{tagwidth,jdbcType=DECIMAL}, #{idparent,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{uriImagen,jdbcType=VARCHAR}, #{idrecurso,jdbcType=VARCHAR}, ",
        "#{genMenuIdmenu,jdbcType=VARCHAR}, #{idproceso,jdbcType=VARCHAR}, ",
        "#{idlenguaje,jdbcType=VARCHAR})"
    })
    int insert(GenMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @InsertProvider(type=GenMenuSqlProvider.class, method="insertSelective")
    int insertSelective(GenMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenMenuSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDMENU", property="idmenu", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGWIDTH", property="tagwidth", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARENT", property="idparent", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="URI_IMAGEN", property="uriImagen", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDRECURSO", property="idrecurso", jdbcType=JdbcType.VARCHAR),
        @Result(column="GEN_MENU_IDMENU", property="genMenuIdmenu", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROCESO", property="idproceso", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDLENGUAJE", property="idlenguaje", jdbcType=JdbcType.VARCHAR)
    })
    List<GenMenu> selectByExample(GenMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Select({
        "select",
        "IDMENU, ORDEN, TAGWIDTH, IDPARENT, FECHAMODIFICACION, USUMODIFICACION, URI_IMAGEN, ",
        "IDRECURSO, GEN_MENU_IDMENU, IDPROCESO, IDLENGUAJE",
        "from GEN_MENU",
        "where IDMENU = #{idmenu,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="IDMENU", property="idmenu", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="TAGWIDTH", property="tagwidth", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARENT", property="idparent", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="URI_IMAGEN", property="uriImagen", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDRECURSO", property="idrecurso", jdbcType=JdbcType.VARCHAR),
        @Result(column="GEN_MENU_IDMENU", property="genMenuIdmenu", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROCESO", property="idproceso", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDLENGUAJE", property="idlenguaje", jdbcType=JdbcType.VARCHAR)
    })
    GenMenu selectByPrimaryKey(String idmenu);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenMenuSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") GenMenu record, @Param("example") GenMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenMenuSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") GenMenu record, @Param("example") GenMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenMenuSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(GenMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MENU
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Update({
        "update GEN_MENU",
        "set ORDEN = #{orden,jdbcType=DECIMAL},",
          "TAGWIDTH = #{tagwidth,jdbcType=DECIMAL},",
          "IDPARENT = #{idparent,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "URI_IMAGEN = #{uriImagen,jdbcType=VARCHAR},",
          "IDRECURSO = #{idrecurso,jdbcType=VARCHAR},",
          "GEN_MENU_IDMENU = #{genMenuIdmenu,jdbcType=VARCHAR},",
          "IDPROCESO = #{idproceso,jdbcType=VARCHAR},",
          "IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR}",
        "where IDMENU = #{idmenu,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(GenMenu record);
}