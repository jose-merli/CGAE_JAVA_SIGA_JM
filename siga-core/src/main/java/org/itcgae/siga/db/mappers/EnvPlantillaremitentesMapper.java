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
import org.itcgae.siga.db.entities.EnvPlantillaremitentes;
import org.itcgae.siga.db.entities.EnvPlantillaremitentesExample;
import org.itcgae.siga.db.entities.EnvPlantillaremitentesKey;

public interface EnvPlantillaremitentesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @SelectProvider(type=EnvPlantillaremitentesSqlProvider.class, method="countByExample")
    long countByExample(EnvPlantillaremitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @DeleteProvider(type=EnvPlantillaremitentesSqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvPlantillaremitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @Delete({
        "delete from ENV_PLANTILLAREMITENTES",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(EnvPlantillaremitentesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @Insert({
        "insert into ENV_PLANTILLAREMITENTES (IDINSTITUCION, IDTIPOENVIOS, ",
        "IDPLANTILLAENVIOS, IDPERSONA, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "CODIGOPOSTAL, CORREOELECTRONICO, ",
        "DOMICILIO, FAX1, ",
        "FAX2, IDPAIS, IDPOBLACION, ",
        "IDPROVINCIA, DESCRIPCION, ",
        "POBLACIONEXTRANJERA, MOVIL)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoenvios,jdbcType=DECIMAL}, ",
        "#{idplantillaenvios,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{codigopostal,jdbcType=VARCHAR}, #{correoelectronico,jdbcType=VARCHAR}, ",
        "#{domicilio,jdbcType=VARCHAR}, #{fax1,jdbcType=VARCHAR}, ",
        "#{fax2,jdbcType=VARCHAR}, #{idpais,jdbcType=VARCHAR}, #{idpoblacion,jdbcType=VARCHAR}, ",
        "#{idprovincia,jdbcType=VARCHAR}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{poblacionextranjera,jdbcType=VARCHAR}, #{movil,jdbcType=VARCHAR})"
    })
    int insert(EnvPlantillaremitentes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @InsertProvider(type=EnvPlantillaremitentesSqlProvider.class, method="insertSelective")
    int insertSelective(EnvPlantillaremitentes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @SelectProvider(type=EnvPlantillaremitentesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPLANTILLAENVIOS", property="idplantillaenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOPOSTAL", property="codigopostal", jdbcType=JdbcType.VARCHAR),
        @Result(column="CORREOELECTRONICO", property="correoelectronico", jdbcType=JdbcType.VARCHAR),
        @Result(column="DOMICILIO", property="domicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="FAX1", property="fax1", jdbcType=JdbcType.VARCHAR),
        @Result(column="FAX2", property="fax2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPAIS", property="idpais", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION", property="idpoblacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="POBLACIONEXTRANJERA", property="poblacionextranjera", jdbcType=JdbcType.VARCHAR),
        @Result(column="MOVIL", property="movil", jdbcType=JdbcType.VARCHAR)
    })
    List<EnvPlantillaremitentes> selectByExample(EnvPlantillaremitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOENVIOS, IDPLANTILLAENVIOS, IDPERSONA, FECHAMODIFICACION, ",
        "USUMODIFICACION, CODIGOPOSTAL, CORREOELECTRONICO, DOMICILIO, FAX1, FAX2, IDPAIS, ",
        "IDPOBLACION, IDPROVINCIA, DESCRIPCION, POBLACIONEXTRANJERA, MOVIL",
        "from ENV_PLANTILLAREMITENTES",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPLANTILLAENVIOS", property="idplantillaenvios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOPOSTAL", property="codigopostal", jdbcType=JdbcType.VARCHAR),
        @Result(column="CORREOELECTRONICO", property="correoelectronico", jdbcType=JdbcType.VARCHAR),
        @Result(column="DOMICILIO", property="domicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="FAX1", property="fax1", jdbcType=JdbcType.VARCHAR),
        @Result(column="FAX2", property="fax2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPAIS", property="idpais", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION", property="idpoblacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="POBLACIONEXTRANJERA", property="poblacionextranjera", jdbcType=JdbcType.VARCHAR),
        @Result(column="MOVIL", property="movil", jdbcType=JdbcType.VARCHAR)
    })
    EnvPlantillaremitentes selectByPrimaryKey(EnvPlantillaremitentesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @UpdateProvider(type=EnvPlantillaremitentesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvPlantillaremitentes record, @Param("example") EnvPlantillaremitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @UpdateProvider(type=EnvPlantillaremitentesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvPlantillaremitentes record, @Param("example") EnvPlantillaremitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @UpdateProvider(type=EnvPlantillaremitentesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EnvPlantillaremitentes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLAREMITENTES
     *
     * @mbg.generated Wed Dec 05 12:47:30 CET 2018
     */
    @Update({
        "update ENV_PLANTILLAREMITENTES",
        "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR},",
          "CORREOELECTRONICO = #{correoelectronico,jdbcType=VARCHAR},",
          "DOMICILIO = #{domicilio,jdbcType=VARCHAR},",
          "FAX1 = #{fax1,jdbcType=VARCHAR},",
          "FAX2 = #{fax2,jdbcType=VARCHAR},",
          "IDPAIS = #{idpais,jdbcType=VARCHAR},",
          "IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR},",
          "IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "POBLACIONEXTRANJERA = #{poblacionextranjera,jdbcType=VARCHAR},",
          "MOVIL = #{movil,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
          "and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(EnvPlantillaremitentes record);
}