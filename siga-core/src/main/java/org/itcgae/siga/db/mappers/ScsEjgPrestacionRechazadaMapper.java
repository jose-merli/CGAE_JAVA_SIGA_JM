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
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazadaExample;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazadaKey;

public interface ScsEjgPrestacionRechazadaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @SelectProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="countByExample")
    long countByExample(ScsEjgPrestacionRechazadaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @DeleteProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsEjgPrestacionRechazadaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @Delete({
        "delete from SCS_EJG_PRESTACION_RECHAZADA",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=NUMBER}",
          "and ANIO = #{anio,jdbcType=NUMBER}",
          "and NUMERO = #{numero,jdbcType=NUMBER}",
          "and IDTIPOEJG = #{idtipoejg,jdbcType=NUMBER}",
          "and IDPRESTACION = #{idprestacion,jdbcType=NUMBER}"
    })
    int deleteByPrimaryKey(ScsEjgPrestacionRechazadaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @Insert({
        "insert into SCS_EJG_PRESTACION_RECHAZADA (IDINSTITUCION, ANIO, ",
        "NUMERO, IDTIPOEJG, ",
        "IDPRESTACION, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idinstitucion,jdbcType=NUMBER}, #{anio,jdbcType=NUMBER}, ",
        "#{numero,jdbcType=NUMBER}, #{idtipoejg,jdbcType=NUMBER}, ",
        "#{idprestacion,jdbcType=NUMBER}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=NUMBER})"
    })
    int insert(ScsEjgPrestacionRechazada record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @InsertProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="insertSelective")
    int insertSelective(ScsEjgPrestacionRechazada record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @SelectProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="IDTIPOEJG", property="idtipoejg", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="IDPRESTACION", property="idprestacion", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.NUMBER)
    })
    List<ScsEjgPrestacionRechazada> selectByExample(ScsEjgPrestacionRechazadaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, ANIO, NUMERO, IDTIPOEJG, IDPRESTACION, FECHAMODIFICACION, USUMODIFICACION",
        "from SCS_EJG_PRESTACION_RECHAZADA",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=NUMBER}",
          "and ANIO = #{anio,jdbcType=NUMBER}",
          "and NUMERO = #{numero,jdbcType=NUMBER}",
          "and IDTIPOEJG = #{idtipoejg,jdbcType=NUMBER}",
          "and IDPRESTACION = #{idprestacion,jdbcType=NUMBER}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="IDTIPOEJG", property="idtipoejg", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="IDPRESTACION", property="idprestacion", jdbcType=JdbcType.NUMBER, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.NUMBER)
    })
    ScsEjgPrestacionRechazada selectByPrimaryKey(ScsEjgPrestacionRechazadaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @UpdateProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsEjgPrestacionRechazada record, @Param("example") ScsEjgPrestacionRechazadaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @UpdateProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsEjgPrestacionRechazada record, @Param("example") ScsEjgPrestacionRechazadaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @UpdateProvider(type=ScsEjgPrestacionRechazadaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsEjgPrestacionRechazada record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_EJG_PRESTACION_RECHAZADA
     *
     * @mbg.generated Thu May 06 14:50:48 CEST 2021
     */
    @Update({
        "update SCS_EJG_PRESTACION_RECHAZADA",
        "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=NUMBER}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=NUMBER}",
          "and ANIO = #{anio,jdbcType=NUMBER}",
          "and NUMERO = #{numero,jdbcType=NUMBER}",
          "and IDTIPOEJG = #{idtipoejg,jdbcType=NUMBER}",
          "and IDPRESTACION = #{idprestacion,jdbcType=NUMBER}"
    })
    int updateByPrimaryKey(ScsEjgPrestacionRechazada record);
}