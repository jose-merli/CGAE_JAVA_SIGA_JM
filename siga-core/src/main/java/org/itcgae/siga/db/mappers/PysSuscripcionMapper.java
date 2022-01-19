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
import org.itcgae.siga.db.entities.PysSuscripcion;
import org.itcgae.siga.db.entities.PysSuscripcionExample;
import org.itcgae.siga.db.entities.PysSuscripcionKey;

public interface PysSuscripcionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @SelectProvider(type=PysSuscripcionSqlProvider.class, method="countByExample")
    long countByExample(PysSuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @DeleteProvider(type=PysSuscripcionSqlProvider.class, method="deleteByExample")
    int deleteByExample(PysSuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @Delete({
        "delete from PYS_SUSCRIPCION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL}",
          "and IDSERVICIO = #{idservicio,jdbcType=DECIMAL}",
          "and IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL}",
          "and IDSUSCRIPCION = #{idsuscripcion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(PysSuscripcionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @Insert({
        "insert into PYS_SUSCRIPCION (IDINSTITUCION, IDTIPOSERVICIOS, ",
        "IDSERVICIO, IDSERVICIOSINSTITUCION, ",
        "IDSUSCRIPCION, IDPERSONA, ",
        "IDPETICION, FECHASUSCRIPCION, ",
        "CANTIDAD, IDFORMAPAGO, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "DESCRIPCION, IMPORTEUNITARIO, ",
        "IMPORTEANTICIPADO, FECHABAJA, ",
        "IDCUENTA, FECHABAJAFACTURACION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtiposervicios,jdbcType=DECIMAL}, ",
        "#{idservicio,jdbcType=DECIMAL}, #{idserviciosinstitucion,jdbcType=DECIMAL}, ",
        "#{idsuscripcion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{idpeticion,jdbcType=DECIMAL}, #{fechasuscripcion,jdbcType=TIMESTAMP}, ",
        "#{cantidad,jdbcType=DECIMAL}, #{idformapago,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{importeunitario,jdbcType=DECIMAL}, ",
        "#{importeanticipado,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{idcuenta,jdbcType=DECIMAL}, #{fechabajafacturacion,jdbcType=TIMESTAMP})"
    })
    int insert(PysSuscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @InsertProvider(type=PysSuscripcionSqlProvider.class, method="insertSelective")
    int insertSelective(PysSuscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @SelectProvider(type=PysSuscripcionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOSERVICIOS", property="idtiposervicios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIOSINSTITUCION", property="idserviciosinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSUSCRIPCION", property="idsuscripcion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPETICION", property="idpeticion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHASUSCRIPCION", property="fechasuscripcion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CANTIDAD", property="cantidad", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFORMAPAGO", property="idformapago", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IMPORTEUNITARIO", property="importeunitario", jdbcType=JdbcType.DECIMAL),
        @Result(column="IMPORTEANTICIPADO", property="importeanticipado", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDCUENTA", property="idcuenta", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJAFACTURACION", property="fechabajafacturacion", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PysSuscripcion> selectByExample(PysSuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOSERVICIOS, IDSERVICIO, IDSERVICIOSINSTITUCION, IDSUSCRIPCION, ",
        "IDPERSONA, IDPETICION, FECHASUSCRIPCION, CANTIDAD, IDFORMAPAGO, FECHAMODIFICACION, ",
        "USUMODIFICACION, DESCRIPCION, IMPORTEUNITARIO, IMPORTEANTICIPADO, FECHABAJA, ",
        "IDCUENTA, FECHABAJAFACTURACION",
        "from PYS_SUSCRIPCION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL}",
          "and IDSERVICIO = #{idservicio,jdbcType=DECIMAL}",
          "and IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL}",
          "and IDSUSCRIPCION = #{idsuscripcion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOSERVICIOS", property="idtiposervicios", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSERVICIOSINSTITUCION", property="idserviciosinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSUSCRIPCION", property="idsuscripcion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPETICION", property="idpeticion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHASUSCRIPCION", property="fechasuscripcion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CANTIDAD", property="cantidad", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFORMAPAGO", property="idformapago", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IMPORTEUNITARIO", property="importeunitario", jdbcType=JdbcType.DECIMAL),
        @Result(column="IMPORTEANTICIPADO", property="importeanticipado", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDCUENTA", property="idcuenta", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJAFACTURACION", property="fechabajafacturacion", jdbcType=JdbcType.TIMESTAMP)
    })
    PysSuscripcion selectByPrimaryKey(PysSuscripcionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @UpdateProvider(type=PysSuscripcionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PysSuscripcion record, @Param("example") PysSuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @UpdateProvider(type=PysSuscripcionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PysSuscripcion record, @Param("example") PysSuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @UpdateProvider(type=PysSuscripcionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PysSuscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_SUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    @Update({
        "update PYS_SUSCRIPCION",
        "set IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
          "IDPETICION = #{idpeticion,jdbcType=DECIMAL},",
          "FECHASUSCRIPCION = #{fechasuscripcion,jdbcType=TIMESTAMP},",
          "CANTIDAD = #{cantidad,jdbcType=DECIMAL},",
          "IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "IMPORTEUNITARIO = #{importeunitario,jdbcType=DECIMAL},",
          "IMPORTEANTICIPADO = #{importeanticipado,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "IDCUENTA = #{idcuenta,jdbcType=DECIMAL},",
          "FECHABAJAFACTURACION = #{fechabajafacturacion,jdbcType=TIMESTAMP}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL}",
          "and IDSERVICIO = #{idservicio,jdbcType=DECIMAL}",
          "and IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL}",
          "and IDSUSCRIPCION = #{idsuscripcion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(PysSuscripcion record);
    
    @Select({
        "select",
        "NVL(max(IDSUSCRIPCION) +1, 1)",
        "from PYS_SUSCRIPCION",
        "where idinstitucion= #{idinstitucion,jdbcType=DECIMAL}"
    })
    int getNewIdSus(PysSuscripcion idInstitucion);
}