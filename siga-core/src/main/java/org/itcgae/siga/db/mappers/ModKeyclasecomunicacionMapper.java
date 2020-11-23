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
import org.itcgae.siga.db.entities.ModKeyclasecomunicacion;
import org.itcgae.siga.db.entities.ModKeyclasecomunicacionExample;
import org.itcgae.siga.db.entities.ModKeyclasecomunicacionKey;

public interface ModKeyclasecomunicacionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @SelectProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="countByExample")
    long countByExample(ModKeyclasecomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @DeleteProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="deleteByExample")
    int deleteByExample(ModKeyclasecomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @Delete({
        "delete from MOD_KEYCLASECOMUNICACION",
        "where IDKEY = #{idkey,jdbcType=DECIMAL}",
          "and IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL}",
          "and TABLA = #{tabla,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(ModKeyclasecomunicacionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @Insert({
        "insert into MOD_KEYCLASECOMUNICACION (IDKEY, IDCLASECOMUNICACION, ",
        "TABLA, DESCRIPCION, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idkey,jdbcType=DECIMAL}, #{idclasecomunicacion,jdbcType=DECIMAL}, ",
        "#{tabla,jdbcType=VARCHAR}, #{descripcion,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ModKeyclasecomunicacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @InsertProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="insertSelective")
    int insertSelective(ModKeyclasecomunicacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @SelectProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDKEY", property="idkey", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TABLA", property="tabla", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ModKeyclasecomunicacion> selectByExample(ModKeyclasecomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @Select({
        "select",
        "IDKEY, IDCLASECOMUNICACION, TABLA, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION",
        "from MOD_KEYCLASECOMUNICACION",
        "where IDKEY = #{idkey,jdbcType=DECIMAL}",
          "and IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL}",
          "and TABLA = #{tabla,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="IDKEY", property="idkey", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCLASECOMUNICACION", property="idclasecomunicacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="TABLA", property="tabla", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ModKeyclasecomunicacion selectByPrimaryKey(ModKeyclasecomunicacionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @UpdateProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ModKeyclasecomunicacion record, @Param("example") ModKeyclasecomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @UpdateProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ModKeyclasecomunicacion record, @Param("example") ModKeyclasecomunicacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @UpdateProvider(type=ModKeyclasecomunicacionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ModKeyclasecomunicacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_KEYCLASECOMUNICACION
     *
     * @mbg.generated Fri Jan 25 12:08:29 CET 2019
     */
    @Update({
        "update MOD_KEYCLASECOMUNICACION",
        "set DESCRIPCION = #{descripcion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDKEY = #{idkey,jdbcType=DECIMAL}",
          "and IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL}",
          "and TABLA = #{tabla,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ModKeyclasecomunicacion record);
}