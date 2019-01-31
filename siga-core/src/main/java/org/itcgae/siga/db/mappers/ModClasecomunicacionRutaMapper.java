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
import org.itcgae.siga.db.entities.ModClasecomunicacionRuta;
import org.itcgae.siga.db.entities.ModClasecomunicacionRutaExample;

public interface ModClasecomunicacionRutaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @SelectProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="countByExample")
    long countByExample(ModClasecomunicacionRutaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @DeleteProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ModClasecomunicacionRutaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @Delete({
        "delete from MOD_CLASECOMUNICACION_RUTA",
        "where IDCLASECOMUNICACION_RUTA = #{idclasecomunicacionRuta,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idclasecomunicacionRuta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @Insert({
        "insert into MOD_CLASECOMUNICACION_RUTA (IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, ",
        "RUTA, FECHAMODIFICACION, ",
        "USUARIOMODIFICACION)",
        "values (#{idclasecomunicacionRuta,jdbcType=DECIMAL}, #{idclasecomunicacion,jdbcType=DECIMAL}, ",
        "#{ruta,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usuariomodificacion,jdbcType=DECIMAL})"
    })
    int insert(ModClasecomunicacionRuta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @InsertProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="insertSelective")
    int insertSelective(ModClasecomunicacionRuta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @SelectProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCLASECOMUNICACION_RUTA", property="idclasecomunicacionRuta", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="RUTA", property="ruta", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUARIOMODIFICACION", property="usuariomodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ModClasecomunicacionRuta> selectByExample(ModClasecomunicacionRutaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @Select({
        "select",
        "IDCLASECOMUNICACION_RUTA, IDCLASECOMUNICACION, RUTA, FECHAMODIFICACION, USUARIOMODIFICACION",
        "from MOD_CLASECOMUNICACION_RUTA",
        "where IDCLASECOMUNICACION_RUTA = #{idclasecomunicacionRuta,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCLASECOMUNICACION_RUTA", property="idclasecomunicacionRuta", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="RUTA", property="ruta", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUARIOMODIFICACION", property="usuariomodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ModClasecomunicacionRuta selectByPrimaryKey(Short idclasecomunicacionRuta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @UpdateProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ModClasecomunicacionRuta record, @Param("example") ModClasecomunicacionRutaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @UpdateProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ModClasecomunicacionRuta record, @Param("example") ModClasecomunicacionRutaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @UpdateProvider(type=ModClasecomunicacionRutaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ModClasecomunicacionRuta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_CLASECOMUNICACION_RUTA
     *
     * @mbg.generated Wed Jan 23 12:58:34 CET 2019
     */
    @Update({
        "update MOD_CLASECOMUNICACION_RUTA",
        "set IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL},",
          "RUTA = #{ruta,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUARIOMODIFICACION = #{usuariomodificacion,jdbcType=DECIMAL}",
        "where IDCLASECOMUNICACION_RUTA = #{idclasecomunicacionRuta,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ModClasecomunicacionRuta record);
}