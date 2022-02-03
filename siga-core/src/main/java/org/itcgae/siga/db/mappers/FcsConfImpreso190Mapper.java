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
import org.itcgae.siga.db.entities.FcsConfImpreso190;
import org.itcgae.siga.db.entities.FcsConfImpreso190Example;

public interface FcsConfImpreso190Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @SelectProvider(type=FcsConfImpreso190SqlProvider.class, method="countByExample")
    long countByExample(FcsConfImpreso190Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @DeleteProvider(type=FcsConfImpreso190SqlProvider.class, method="deleteByExample")
    int deleteByExample(FcsConfImpreso190Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @Delete({
        "delete from FCS_CONF_IMPRESO190",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idinstitucion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @Insert({
        "insert into FCS_CONF_IMPRESO190 (IDINSTITUCION, ANIO, ",
        "NOMBREFICHERO, IDPROVINCIA, ",
        "TELEFONO, NOMBRE, ",
        "APELLIDO1, APELLIDO2, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{anio,jdbcType=DECIMAL}, ",
        "#{nombrefichero,jdbcType=VARCHAR}, #{idprovincia,jdbcType=VARCHAR}, ",
        "#{telefono,jdbcType=VARCHAR}, #{nombre,jdbcType=VARCHAR}, ",
        "#{apellido1,jdbcType=VARCHAR}, #{apellido2,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(FcsConfImpreso190 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @InsertProvider(type=FcsConfImpreso190SqlProvider.class, method="insertSelective")
    int insertSelective(FcsConfImpreso190 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @SelectProvider(type=FcsConfImpreso190SqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBREFICHERO", property="nombrefichero", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO", property="telefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO1", property="apellido1", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO2", property="apellido2", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<FcsConfImpreso190> selectByExample(FcsConfImpreso190Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, ANIO, NOMBREFICHERO, IDPROVINCIA, TELEFONO, NOMBRE, APELLIDO1, ",
        "APELLIDO2, FECHAMODIFICACION, USUMODIFICACION",
        "from FCS_CONF_IMPRESO190",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBREFICHERO", property="nombrefichero", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO", property="telefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO1", property="apellido1", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO2", property="apellido2", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    FcsConfImpreso190 selectByPrimaryKey(Short idinstitucion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @UpdateProvider(type=FcsConfImpreso190SqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FcsConfImpreso190 record, @Param("example") FcsConfImpreso190Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @UpdateProvider(type=FcsConfImpreso190SqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FcsConfImpreso190 record, @Param("example") FcsConfImpreso190Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @UpdateProvider(type=FcsConfImpreso190SqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FcsConfImpreso190 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_CONF_IMPRESO190
     *
     * @mbg.generated Thu Nov 18 09:52:57 CET 2021
     */
    @Update({
        "update FCS_CONF_IMPRESO190",
        "set ANIO = #{anio,jdbcType=DECIMAL},",
          "NOMBREFICHERO = #{nombrefichero,jdbcType=VARCHAR},",
          "IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR},",
          "TELEFONO = #{telefono,jdbcType=VARCHAR},",
          "NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "APELLIDO1 = #{apellido1,jdbcType=VARCHAR},",
          "APELLIDO2 = #{apellido2,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FcsConfImpreso190 record);
}