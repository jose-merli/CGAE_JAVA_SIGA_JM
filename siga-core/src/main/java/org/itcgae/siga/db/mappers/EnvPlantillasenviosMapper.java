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
import org.itcgae.siga.db.entities.EnvPlantillasenvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosExample;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;

public interface EnvPlantillasenviosMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @SelectProvider(type=EnvPlantillasenviosSqlProvider.class, method="countByExample")
    long countByExample(EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @DeleteProvider(type=EnvPlantillasenviosSqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @Delete({
        "delete from ENV_PLANTILLASENVIOS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(EnvPlantillasenviosKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @Insert({
        "insert into ENV_PLANTILLASENVIOS (IDINSTITUCION, IDTIPOENVIOS, ",
        "IDPLANTILLAENVIOS, NOMBRE, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "ACUSERECIBO, FECHABAJA, ",
        "IDDIRECCION, IDPERSONA, ",
        "DESCRIPCION, ANTIGUA, ",
        "DESCRIPCION_REMITENTE, IDCLASECOMUNICACION, ",
        "ASUNTO, CUERPO)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoenvios,jdbcType=DECIMAL}, ",
        "#{idplantillaenvios,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{acuserecibo,jdbcType=VARCHAR}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{iddireccion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{antigua,jdbcType=VARCHAR}, ",
        "#{descripcionRemitente,jdbcType=VARCHAR}, #{idclasecomunicacion,jdbcType=DECIMAL}, ",
        "#{asunto,jdbcType=CLOB}, #{cuerpo,jdbcType=CLOB})"
    })
    int insert(EnvPlantillasenviosWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @InsertProvider(type=EnvPlantillasenviosSqlProvider.class, method="insertSelective")
    int insertSelective(EnvPlantillasenviosWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @SelectProvider(type=EnvPlantillasenviosSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPLANTILLAENVIOS", property="idplantillaenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACUSERECIBO", property="acuserecibo", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDDIRECCION", property="iddireccion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="ANTIGUA", property="antigua", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION_REMITENTE", property="descripcionRemitente", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ASUNTO", property="asunto", jdbcType=JdbcType.CLOB),
        @Result(column="CUERPO", property="cuerpo", jdbcType=JdbcType.CLOB)
    })
    List<EnvPlantillasenviosWithBLOBs> selectByExampleWithBLOBs(EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @SelectProvider(type=EnvPlantillasenviosSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPLANTILLAENVIOS", property="idplantillaenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACUSERECIBO", property="acuserecibo", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDDIRECCION", property="iddireccion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="ANTIGUA", property="antigua", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION_REMITENTE", property="descripcionRemitente", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL)
    })
    List<EnvPlantillasenvios> selectByExample(EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOENVIOS, IDPLANTILLAENVIOS, NOMBRE, FECHAMODIFICACION, USUMODIFICACION, ",
        "ACUSERECIBO, FECHABAJA, IDDIRECCION, IDPERSONA, DESCRIPCION, ANTIGUA, DESCRIPCION_REMITENTE, ",
        "IDCLASECOMUNICACION, ASUNTO, CUERPO",
        "from ENV_PLANTILLASENVIOS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPLANTILLAENVIOS", property="idplantillaenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACUSERECIBO", property="acuserecibo", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDDIRECCION", property="iddireccion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="ANTIGUA", property="antigua", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION_REMITENTE", property="descripcionRemitente", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ASUNTO", property="asunto", jdbcType=JdbcType.CLOB),
        @Result(column="CUERPO", property="cuerpo", jdbcType=JdbcType.CLOB)
    })
    EnvPlantillasenviosWithBLOBs selectByPrimaryKey(EnvPlantillasenviosKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @UpdateProvider(type=EnvPlantillasenviosSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvPlantillasenviosWithBLOBs record, @Param("example") EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @UpdateProvider(type=EnvPlantillasenviosSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") EnvPlantillasenviosWithBLOBs record, @Param("example") EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @UpdateProvider(type=EnvPlantillasenviosSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvPlantillasenvios record, @Param("example") EnvPlantillasenviosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @UpdateProvider(type=EnvPlantillasenviosSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EnvPlantillasenviosWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @Update({
        "update ENV_PLANTILLASENVIOS",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "ACUSERECIBO = #{acuserecibo,jdbcType=VARCHAR},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "IDDIRECCION = #{iddireccion,jdbcType=DECIMAL},",
          "IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "ANTIGUA = #{antigua,jdbcType=VARCHAR},",
          "DESCRIPCION_REMITENTE = #{descripcionRemitente,jdbcType=VARCHAR},",
          "IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL},",
          "ASUNTO = #{asunto,jdbcType=CLOB},",
          "CUERPO = #{cuerpo,jdbcType=CLOB}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKeyWithBLOBs(EnvPlantillasenviosWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    @Update({
        "update ENV_PLANTILLASENVIOS",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "ACUSERECIBO = #{acuserecibo,jdbcType=VARCHAR},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "IDDIRECCION = #{iddireccion,jdbcType=DECIMAL},",
          "IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "ANTIGUA = #{antigua,jdbcType=VARCHAR},",
          "DESCRIPCION_REMITENTE = #{descripcionRemitente,jdbcType=VARCHAR},",
          "IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(EnvPlantillasenvios record);
}