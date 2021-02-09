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
import org.itcgae.siga.db.entities.PysServiciosinstitucion;
import org.itcgae.siga.db.entities.PysServiciosinstitucionExample;
import org.itcgae.siga.db.entities.PysServiciosinstitucionKey;

public interface PysServiciosinstitucionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @SelectProvider(type=PysServiciosinstitucionSqlProvider.class, method="countByExample")
    long countByExample(PysServiciosinstitucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @DeleteProvider(type=PysServiciosinstitucionSqlProvider.class, method="deleteByExample")
    int deleteByExample(PysServiciosinstitucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @Delete({
        "delete from PYS_SERVICIOSINSTITUCION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL}",
          "and IDSERVICIO = #{idservicio,jdbcType=DECIMAL}",
          "and IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(PysServiciosinstitucionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @Insert({
        "insert into PYS_SERVICIOSINSTITUCION (IDINSTITUCION, IDTIPOSERVICIOS, ",
        "IDSERVICIO, IDSERVICIOSINSTITUCION, ",
        "DESCRIPCION, INICIOFINALPONDERADO, ",
        "MOMENTOCARGO, SOLICITARBAJA, ",
        "SOLICITARALTA, FECHAMODIFICACION, ",
        "USUMODIFICACION, AUTOMATICO, ",
        "CUENTACONTABLE, FECHABAJA, ",
        "IDCONSULTA, CRITERIOS, ",
        "FACTURACIONPONDERADA, IDTIPOIVA, ",
        "CODIGO_TRASPASONAV)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtiposervicios,jdbcType=DECIMAL}, ",
        "#{idservicio,jdbcType=DECIMAL}, #{idserviciosinstitucion,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{iniciofinalponderado,jdbcType=VARCHAR}, ",
        "#{momentocargo,jdbcType=VARCHAR}, #{solicitarbaja,jdbcType=VARCHAR}, ",
        "#{solicitaralta,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{automatico,jdbcType=VARCHAR}, ",
        "#{cuentacontable,jdbcType=VARCHAR}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{idconsulta,jdbcType=DECIMAL}, #{criterios,jdbcType=VARCHAR}, ",
        "#{facturacionponderada,jdbcType=VARCHAR}, #{idtipoiva,jdbcType=DECIMAL}, ",
        "#{codigoTraspasonav,jdbcType=VARCHAR})"
    })
    int insert(PysServiciosinstitucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @InsertProvider(type=PysServiciosinstitucionSqlProvider.class, method="insertSelective")
    int insertSelective(PysServiciosinstitucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @SelectProvider(type=PysServiciosinstitucionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOSERVICIOS", property="idtiposervicios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIOSINSTITUCION", property="idserviciosinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="INICIOFINALPONDERADO", property="iniciofinalponderado", jdbcType=JdbcType.VARCHAR),
        @Result(column="MOMENTOCARGO", property="momentocargo", jdbcType=JdbcType.VARCHAR),
        @Result(column="SOLICITARBAJA", property="solicitarbaja", jdbcType=JdbcType.VARCHAR),
        @Result(column="SOLICITARALTA", property="solicitaralta", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="AUTOMATICO", property="automatico", jdbcType=JdbcType.VARCHAR),
        @Result(column="CUENTACONTABLE", property="cuentacontable", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL),
        @Result(column="CRITERIOS", property="criterios", jdbcType=JdbcType.VARCHAR),
        @Result(column="FACTURACIONPONDERADA", property="facturacionponderada", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOIVA", property="idtipoiva", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGO_TRASPASONAV", property="codigoTraspasonav", jdbcType=JdbcType.VARCHAR)
    })
    List<PysServiciosinstitucion> selectByExample(PysServiciosinstitucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOSERVICIOS, IDSERVICIO, IDSERVICIOSINSTITUCION, DESCRIPCION, ",
        "INICIOFINALPONDERADO, MOMENTOCARGO, SOLICITARBAJA, SOLICITARALTA, FECHAMODIFICACION, ",
        "USUMODIFICACION, AUTOMATICO, CUENTACONTABLE, FECHABAJA, IDCONSULTA, CRITERIOS, ",
        "FACTURACIONPONDERADA, IDTIPOIVA, CODIGO_TRASPASONAV",
        "from PYS_SERVICIOSINSTITUCION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL}",
          "and IDSERVICIO = #{idservicio,jdbcType=DECIMAL}",
          "and IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOSERVICIOS", property="idtiposervicios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIOSINSTITUCION", property="idserviciosinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="INICIOFINALPONDERADO", property="iniciofinalponderado", jdbcType=JdbcType.VARCHAR),
        @Result(column="MOMENTOCARGO", property="momentocargo", jdbcType=JdbcType.VARCHAR),
        @Result(column="SOLICITARBAJA", property="solicitarbaja", jdbcType=JdbcType.VARCHAR),
        @Result(column="SOLICITARALTA", property="solicitaralta", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="AUTOMATICO", property="automatico", jdbcType=JdbcType.VARCHAR),
        @Result(column="CUENTACONTABLE", property="cuentacontable", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL),
        @Result(column="CRITERIOS", property="criterios", jdbcType=JdbcType.VARCHAR),
        @Result(column="FACTURACIONPONDERADA", property="facturacionponderada", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOIVA", property="idtipoiva", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGO_TRASPASONAV", property="codigoTraspasonav", jdbcType=JdbcType.VARCHAR)
    })
    PysServiciosinstitucion selectByPrimaryKey(PysServiciosinstitucionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @UpdateProvider(type=PysServiciosinstitucionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PysServiciosinstitucion record, @Param("example") PysServiciosinstitucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @UpdateProvider(type=PysServiciosinstitucionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PysServiciosinstitucion record, @Param("example") PysServiciosinstitucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @UpdateProvider(type=PysServiciosinstitucionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PysServiciosinstitucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SERVICIOSINSTITUCION
     *
     * @mbg.generated Wed Jan 02 12:23:54 CET 2019
     */
    @Update({
        "update PYS_SERVICIOSINSTITUCION",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "INICIOFINALPONDERADO = #{iniciofinalponderado,jdbcType=VARCHAR},",
          "MOMENTOCARGO = #{momentocargo,jdbcType=VARCHAR},",
          "SOLICITARBAJA = #{solicitarbaja,jdbcType=VARCHAR},",
          "SOLICITARALTA = #{solicitaralta,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "AUTOMATICO = #{automatico,jdbcType=VARCHAR},",
          "CUENTACONTABLE = #{cuentacontable,jdbcType=VARCHAR},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "IDCONSULTA = #{idconsulta,jdbcType=DECIMAL},",
          "CRITERIOS = #{criterios,jdbcType=VARCHAR},",
          "FACTURACIONPONDERADA = #{facturacionponderada,jdbcType=VARCHAR},",
          "IDTIPOIVA = #{idtipoiva,jdbcType=DECIMAL},",
          "CODIGO_TRASPASONAV = #{codigoTraspasonav,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL}",
          "and IDSERVICIO = #{idservicio,jdbcType=DECIMAL}",
          "and IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(PysServiciosinstitucion record);
}