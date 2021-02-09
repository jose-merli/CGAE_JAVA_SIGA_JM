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
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysProductossolicitadosExample;
import org.itcgae.siga.db.entities.PysProductossolicitadosKey;

public interface PysProductossolicitadosMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @SelectProvider(type=PysProductossolicitadosSqlProvider.class, method="countByExample")
    long countByExample(PysProductossolicitadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @DeleteProvider(type=PysProductossolicitadosSqlProvider.class, method="deleteByExample")
    int deleteByExample(PysProductossolicitadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @Delete({
        "delete from PYS_PRODUCTOSSOLICITADOS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}",
          "and IDPETICION = #{idpeticion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(PysProductossolicitadosKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @Insert({
        "insert into PYS_PRODUCTOSSOLICITADOS (IDINSTITUCION, IDTIPOPRODUCTO, ",
        "IDPRODUCTO, IDPRODUCTOINSTITUCION, ",
        "IDPETICION, IDPERSONA, ",
        "IDFORMAPAGO, CANTIDAD, ",
        "ACEPTADO, VALOR, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "IDCUENTA, IDTIPOENVIOS, ",
        "IDDIRECCION, IDINSTITUCIONORIGEN, ",
        "NOFACTURABLE, FECHARECEPCIONSOLICITUD, ",
        "METODORECEPCIONSOLICITUD, IDINSTITUCIONCOLEGIACION, ",
        "ORDEN, IDTIPOIVA, ",
        "ACEPTACESIONMUTUALIDAD, OBSERVACIONES, ",
        "IDCENSODATOS)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoproducto,jdbcType=DECIMAL}, ",
        "#{idproducto,jdbcType=DECIMAL}, #{idproductoinstitucion,jdbcType=DECIMAL}, ",
        "#{idpeticion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{idformapago,jdbcType=DECIMAL}, #{cantidad,jdbcType=DECIMAL}, ",
        "#{aceptado,jdbcType=VARCHAR}, #{valor,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{idcuenta,jdbcType=DECIMAL}, #{idtipoenvios,jdbcType=DECIMAL}, ",
        "#{iddireccion,jdbcType=DECIMAL}, #{idinstitucionorigen,jdbcType=DECIMAL}, ",
        "#{nofacturable,jdbcType=VARCHAR}, #{fecharecepcionsolicitud,jdbcType=TIMESTAMP}, ",
        "#{metodorecepcionsolicitud,jdbcType=VARCHAR}, #{idinstitucioncolegiacion,jdbcType=DECIMAL}, ",
        "#{orden,jdbcType=DECIMAL}, #{idtipoiva,jdbcType=DECIMAL}, ",
        "#{aceptacesionmutualidad,jdbcType=VARCHAR}, #{observaciones,jdbcType=VARCHAR}, ",
        "#{idcensodatos,jdbcType=DECIMAL})"
    })
    int insert(PysProductossolicitados record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @InsertProvider(type=PysProductossolicitadosSqlProvider.class, method="insertSelective")
    int insertSelective(PysProductossolicitados record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @SelectProvider(type=PysProductossolicitadosSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOPRODUCTO", property="idtipoproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTO", property="idproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTOINSTITUCION", property="idproductoinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPETICION", property="idpeticion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFORMAPAGO", property="idformapago", jdbcType=JdbcType.DECIMAL),
        @Result(column="CANTIDAD", property="cantidad", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACEPTADO", property="aceptado", jdbcType=JdbcType.VARCHAR),
        @Result(column="VALOR", property="valor", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCUENTA", property="idcuenta", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDDIRECCION", property="iddireccion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCIONORIGEN", property="idinstitucionorigen", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOFACTURABLE", property="nofacturable", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHARECEPCIONSOLICITUD", property="fecharecepcionsolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="METODORECEPCIONSOLICITUD", property="metodorecepcionsolicitud", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDINSTITUCIONCOLEGIACION", property="idinstitucioncolegiacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOIVA", property="idtipoiva", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACEPTACESIONMUTUALIDAD", property="aceptacesionmutualidad", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCENSODATOS", property="idcensodatos", jdbcType=JdbcType.DECIMAL)
    })
    List<PysProductossolicitados> selectByExample(PysProductossolicitadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOPRODUCTO, IDPRODUCTO, IDPRODUCTOINSTITUCION, IDPETICION, ",
        "IDPERSONA, IDFORMAPAGO, CANTIDAD, ACEPTADO, VALOR, FECHAMODIFICACION, USUMODIFICACION, ",
        "IDCUENTA, IDTIPOENVIOS, IDDIRECCION, IDINSTITUCIONORIGEN, NOFACTURABLE, FECHARECEPCIONSOLICITUD, ",
        "METODORECEPCIONSOLICITUD, IDINSTITUCIONCOLEGIACION, ORDEN, IDTIPOIVA, ACEPTACESIONMUTUALIDAD, ",
        "OBSERVACIONES, IDCENSODATOS",
        "from PYS_PRODUCTOSSOLICITADOS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}",
          "and IDPETICION = #{idpeticion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOPRODUCTO", property="idtipoproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTO", property="idproducto", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPRODUCTOINSTITUCION", property="idproductoinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPETICION", property="idpeticion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFORMAPAGO", property="idformapago", jdbcType=JdbcType.DECIMAL),
        @Result(column="CANTIDAD", property="cantidad", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACEPTADO", property="aceptado", jdbcType=JdbcType.VARCHAR),
        @Result(column="VALOR", property="valor", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCUENTA", property="idcuenta", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDDIRECCION", property="iddireccion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCIONORIGEN", property="idinstitucionorigen", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOFACTURABLE", property="nofacturable", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHARECEPCIONSOLICITUD", property="fecharecepcionsolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="METODORECEPCIONSOLICITUD", property="metodorecepcionsolicitud", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDINSTITUCIONCOLEGIACION", property="idinstitucioncolegiacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOIVA", property="idtipoiva", jdbcType=JdbcType.DECIMAL),
        @Result(column="ACEPTACESIONMUTUALIDAD", property="aceptacesionmutualidad", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCENSODATOS", property="idcensodatos", jdbcType=JdbcType.DECIMAL)
    })
    PysProductossolicitados selectByPrimaryKey(PysProductossolicitadosKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @UpdateProvider(type=PysProductossolicitadosSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PysProductossolicitados record, @Param("example") PysProductossolicitadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @UpdateProvider(type=PysProductossolicitadosSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PysProductossolicitados record, @Param("example") PysProductossolicitadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @UpdateProvider(type=PysProductossolicitadosSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PysProductossolicitados record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_PRODUCTOSSOLICITADOS
     *
     * @mbg.generated Wed Jan 09 18:55:17 CET 2019
     */
    @Update({
        "update PYS_PRODUCTOSSOLICITADOS",
        "set IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
          "IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL},",
          "CANTIDAD = #{cantidad,jdbcType=DECIMAL},",
          "ACEPTADO = #{aceptado,jdbcType=VARCHAR},",
          "VALOR = #{valor,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDCUENTA = #{idcuenta,jdbcType=DECIMAL},",
          "IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL},",
          "IDDIRECCION = #{iddireccion,jdbcType=DECIMAL},",
          "IDINSTITUCIONORIGEN = #{idinstitucionorigen,jdbcType=DECIMAL},",
          "NOFACTURABLE = #{nofacturable,jdbcType=VARCHAR},",
          "FECHARECEPCIONSOLICITUD = #{fecharecepcionsolicitud,jdbcType=TIMESTAMP},",
          "METODORECEPCIONSOLICITUD = #{metodorecepcionsolicitud,jdbcType=VARCHAR},",
          "IDINSTITUCIONCOLEGIACION = #{idinstitucioncolegiacion,jdbcType=DECIMAL},",
          "ORDEN = #{orden,jdbcType=DECIMAL},",
          "IDTIPOIVA = #{idtipoiva,jdbcType=DECIMAL},",
          "ACEPTACESIONMUTUALIDAD = #{aceptacesionmutualidad,jdbcType=VARCHAR},",
          "OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
          "IDCENSODATOS = #{idcensodatos,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}",
          "and IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}",
          "and IDPETICION = #{idpeticion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(PysProductossolicitados record);
}