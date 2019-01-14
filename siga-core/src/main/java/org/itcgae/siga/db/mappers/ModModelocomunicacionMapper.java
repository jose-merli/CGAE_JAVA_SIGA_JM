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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;

public interface ModModelocomunicacionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @SelectProvider(type=ModModelocomunicacionSqlProvider.class, method="countByExample")
    long countByExample(ModModelocomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @DeleteProvider(type=ModModelocomunicacionSqlProvider.class, method="deleteByExample")
    int deleteByExample(ModModelocomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @Delete({
        "delete from MOD_MODELOCOMUNICACION",
        "where IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idmodelocomunicacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @Insert({
        "insert into MOD_MODELOCOMUNICACION (IDMODELOCOMUNICACION, NOMBRE, ",
        "ORDEN, IDINSTITUCION, ",
        "DESCRIPCION, PRESELECCIONAR, ",
        "IDCLASECOMUNICACION, FECHABAJA, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idmodelocomunicacion,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
        "#{orden,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{preseleccionar,jdbcType=VARCHAR}, ",
        "#{idclasecomunicacion,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT SEQ_MOD_MODELOCOMUNICAION FROM DUAL", keyProperty="idmodelocomunicacion", before=true, resultType=Long.class)
    int insert(ModModelocomunicacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @InsertProvider(type=ModModelocomunicacionSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SEQ_MOD_MODELOCOMUNICAION FROM DUAL", keyProperty="idmodelocomunicacion", before=true, resultType=Long.class)
    int insertSelective(ModModelocomunicacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @SelectProvider(type=ModModelocomunicacionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRESELECCIONAR", property="preseleccionar", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ModModelocomunicacion> selectByExample(ModModelocomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @Select({
        "select",
        "IDMODELOCOMUNICACION, NOMBRE, ORDEN, IDINSTITUCION, DESCRIPCION, PRESELECCIONAR, ",
        "IDCLASECOMUNICACION, FECHABAJA, FECHAMODIFICACION, USUMODIFICACION",
        "from MOD_MODELOCOMUNICACION",
        "where IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRESELECCIONAR", property="preseleccionar", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ModModelocomunicacion selectByPrimaryKey(Long idmodelocomunicacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @UpdateProvider(type=ModModelocomunicacionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ModModelocomunicacion record, @Param("example") ModModelocomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @UpdateProvider(type=ModModelocomunicacionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ModModelocomunicacion record, @Param("example") ModModelocomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @UpdateProvider(type=ModModelocomunicacionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ModModelocomunicacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_MODELOCOMUNICACION
     *
     * @mbg.generated Fri Jan 11 13:06:03 CET 2019
     */
    @Update({
        "update MOD_MODELOCOMUNICACION",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "ORDEN = #{orden,jdbcType=DECIMAL},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "PRESELECCIONAR = #{preseleccionar,jdbcType=VARCHAR},",
          "IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ModModelocomunicacion record);
}