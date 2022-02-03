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
import org.itcgae.siga.db.entities.EnvRemitentes;
import org.itcgae.siga.db.entities.EnvRemitentesExample;
import org.itcgae.siga.db.entities.EnvRemitentesKey;

public interface EnvRemitentesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @SelectProvider(type=EnvRemitentesSqlProvider.class, method="countByExample")
    long countByExample(EnvRemitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @DeleteProvider(type=EnvRemitentesSqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvRemitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @Delete({
        "delete from ENV_REMITENTES",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDENVIO = #{idenvio,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(EnvRemitentesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @Insert({
        "insert into ENV_REMITENTES (IDINSTITUCION, IDENVIO, ",
        "IDPERSONA, FECHAMODIFICACION, ",
        "USUMODIFICACION, CODIGOPOSTAL, ",
        "CORREOELECTRONICO, DOMICILIO, ",
        "FAX1, FAX2, IDPAIS, ",
        "IDPOBLACION, IDPROVINCIA, ",
        "DESCRIPCION, POBLACIONEXTRANJERA, ",
        "MOVIL)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idenvio,jdbcType=DECIMAL}, ",
        "#{idpersona,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{codigopostal,jdbcType=VARCHAR}, ",
        "#{correoelectronico,jdbcType=VARCHAR}, #{domicilio,jdbcType=VARCHAR}, ",
        "#{fax1,jdbcType=VARCHAR}, #{fax2,jdbcType=VARCHAR}, #{idpais,jdbcType=VARCHAR}, ",
        "#{idpoblacion,jdbcType=VARCHAR}, #{idprovincia,jdbcType=VARCHAR}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{poblacionextranjera,jdbcType=VARCHAR}, ",
        "#{movil,jdbcType=VARCHAR})"
    })
    int insert(EnvRemitentes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @InsertProvider(type=EnvRemitentesSqlProvider.class, method="insertSelective")
    int insertSelective(EnvRemitentes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @SelectProvider(type=EnvRemitentesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL, id=true),
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
    List<EnvRemitentes> selectByExample(EnvRemitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @Select({
        "select",
        "IDINSTITUCION, IDENVIO, IDPERSONA, FECHAMODIFICACION, USUMODIFICACION, CODIGOPOSTAL, ",
        "CORREOELECTRONICO, DOMICILIO, FAX1, FAX2, IDPAIS, IDPOBLACION, IDPROVINCIA, ",
        "DESCRIPCION, POBLACIONEXTRANJERA, MOVIL",
        "from ENV_REMITENTES",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDENVIO = #{idenvio,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL, id=true),
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
    EnvRemitentes selectByPrimaryKey(EnvRemitentesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @UpdateProvider(type=EnvRemitentesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvRemitentes record, @Param("example") EnvRemitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @UpdateProvider(type=EnvRemitentesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvRemitentes record, @Param("example") EnvRemitentesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @UpdateProvider(type=EnvRemitentesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EnvRemitentes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    @Update({
        "update ENV_REMITENTES",
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
          "and IDENVIO = #{idenvio,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(EnvRemitentes record);
}