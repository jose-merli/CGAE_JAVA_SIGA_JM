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
import org.itcgae.siga.db.entities.PysFormapagoproducto;
import org.itcgae.siga.db.entities.PysFormapagoproductoExample;
import org.itcgae.siga.db.entities.PysFormapagoproductoKey;

public interface PysFormapagoproductoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @SelectProvider(type=PysFormapagoproductoSqlProvider.class, method="countByExample")
    long countByExample(PysFormapagoproductoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @DeleteProvider(type=PysFormapagoproductoSqlProvider.class, method="deleteByExample")
    int deleteByExample(PysFormapagoproductoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @Delete({
        "delete from PYS_FORMAPAGOPRODUCTO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}",
          "and IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(PysFormapagoproductoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @Insert({
        "insert into PYS_FORMAPAGOPRODUCTO (IDINSTITUCION, IDTIPOPRODUCTO, ",
        "IDPRODUCTO, IDPRODUCTOINSTITUCION, ",
        "IDFORMAPAGO, INTERNET, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoproducto,jdbcType=DECIMAL}, ",
        "#{idproducto,jdbcType=DECIMAL}, #{idproductoinstitucion,jdbcType=DECIMAL}, ",
        "#{idformapago,jdbcType=DECIMAL}, #{internet,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(PysFormapagoproducto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @InsertProvider(type=PysFormapagoproductoSqlProvider.class, method="insertSelective")
    int insertSelective(PysFormapagoproducto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @SelectProvider(type=PysFormapagoproductoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOPRODUCTO", property="idtipoproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTO", property="idproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTOINSTITUCION", property="idproductoinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDFORMAPAGO", property="idformapago", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="INTERNET", property="internet", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<PysFormapagoproducto> selectByExample(PysFormapagoproductoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOPRODUCTO, IDPRODUCTO, IDPRODUCTOINSTITUCION, IDFORMAPAGO, ",
        "INTERNET, FECHAMODIFICACION, USUMODIFICACION",
        "from PYS_FORMAPAGOPRODUCTO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}",
          "and IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOPRODUCTO", property="idtipoproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTO", property="idproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTOINSTITUCION", property="idproductoinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDFORMAPAGO", property="idformapago", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="INTERNET", property="internet", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    PysFormapagoproducto selectByPrimaryKey(PysFormapagoproductoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @UpdateProvider(type=PysFormapagoproductoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PysFormapagoproducto record, @Param("example") PysFormapagoproductoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @UpdateProvider(type=PysFormapagoproductoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PysFormapagoproducto record, @Param("example") PysFormapagoproductoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @UpdateProvider(type=PysFormapagoproductoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PysFormapagoproducto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_FORMAPAGOPRODUCTO
     *
     * @mbg.generated Thu Jul 22 14:47:46 CEST 2021
     */
    @Update({
        "update PYS_FORMAPAGOPRODUCTO",
        "set INTERNET = #{internet,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}",
          "and IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(PysFormapagoproducto record);
}