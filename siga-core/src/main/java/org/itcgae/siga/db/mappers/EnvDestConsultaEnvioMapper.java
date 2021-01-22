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
import org.itcgae.siga.db.entities.EnvDestConsultaEnvio;
import org.itcgae.siga.db.entities.EnvDestConsultaEnvioExample;
import org.itcgae.siga.db.entities.EnvDestConsultaEnvioKey;

public interface EnvDestConsultaEnvioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @SelectProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="countByExample")
    long countByExample(EnvDestConsultaEnvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @DeleteProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvDestConsultaEnvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @Delete({
        "delete from ENV_DEST_CONSULTA_ENVIO",
        "where IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}",
          "and IDENVIO = #{idenvio,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(EnvDestConsultaEnvioKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @Insert({
        "insert into ENV_DEST_CONSULTA_ENVIO (IDCONSULTA, IDENVIO, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "IDINSTITUCION, FECHABAJA, ",
        "IDINSTITUCION_CONSULTA)",
        "values (#{idconsulta,jdbcType=DECIMAL}, #{idenvio,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{idinstitucion,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{idinstitucionConsulta,jdbcType=DECIMAL})"
    })
    int insert(EnvDestConsultaEnvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @InsertProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="insertSelective")
    int insertSelective(EnvDestConsultaEnvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @SelectProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION_CONSULTA", property="idinstitucionConsulta", jdbcType=JdbcType.DECIMAL)
    })
    List<EnvDestConsultaEnvio> selectByExample(EnvDestConsultaEnvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @Select({
        "select",
        "IDCONSULTA, IDENVIO, FECHAMODIFICACION, USUMODIFICACION, IDINSTITUCION, FECHABAJA, ",
        "IDINSTITUCION_CONSULTA",
        "from ENV_DEST_CONSULTA_ENVIO",
        "where IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}",
          "and IDENVIO = #{idenvio,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION_CONSULTA", property="idinstitucionConsulta", jdbcType=JdbcType.DECIMAL)
    })
    EnvDestConsultaEnvio selectByPrimaryKey(EnvDestConsultaEnvioKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @UpdateProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvDestConsultaEnvio record, @Param("example") EnvDestConsultaEnvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @UpdateProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvDestConsultaEnvio record, @Param("example") EnvDestConsultaEnvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @UpdateProvider(type=EnvDestConsultaEnvioSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EnvDestConsultaEnvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_DEST_CONSULTA_ENVIO
     *
     * @mbg.generated Wed Mar 13 12:44:18 CET 2019
     */
    @Update({
        "update ENV_DEST_CONSULTA_ENVIO",
        "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "IDINSTITUCION_CONSULTA = #{idinstitucionConsulta,jdbcType=DECIMAL}",
        "where IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}",
          "and IDENVIO = #{idenvio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(EnvDestConsultaEnvio record);
}