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
import org.itcgae.siga.db.entities.ForCalificaciones;
import org.itcgae.siga.db.entities.ForCalificacionesExample;

public interface ForCalificacionesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @SelectProvider(type=ForCalificacionesSqlProvider.class, method="countByExample")
    long countByExample(ForCalificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @DeleteProvider(type=ForCalificacionesSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForCalificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @Delete({
        "delete from FOR_CALIFICACIONES",
        "where IDCALIFICACION = #{idcalificacion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idcalificacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @Insert({
        "insert into FOR_CALIFICACIONES (IDCALIFICACION, DESCRIPCION, ",
        "USUMODIFICACION, FECHAMODIFICACION, ",
        "FECHABAJA)",
        "values (#{idcalificacion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{fechabaja,jdbcType=TIMESTAMP})"
    })
    int insert(ForCalificaciones record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @InsertProvider(type=ForCalificacionesSqlProvider.class, method="insertSelective")
    int insertSelective(ForCalificaciones record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @SelectProvider(type=ForCalificacionesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCALIFICACION", property="idcalificacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ForCalificaciones> selectByExample(ForCalificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @Select({
        "select",
        "IDCALIFICACION, DESCRIPCION, USUMODIFICACION, FECHAMODIFICACION, FECHABAJA",
        "from FOR_CALIFICACIONES",
        "where IDCALIFICACION = #{idcalificacion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCALIFICACION", property="idcalificacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ForCalificaciones selectByPrimaryKey(Long idcalificacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @UpdateProvider(type=ForCalificacionesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForCalificaciones record, @Param("example") ForCalificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @UpdateProvider(type=ForCalificacionesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForCalificaciones record, @Param("example") ForCalificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @UpdateProvider(type=ForCalificacionesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForCalificaciones record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CALIFICACIONES
     *
     * @mbg.generated Mon Jan 07 20:38:05 CET 2019
     */
    @Update({
        "update FOR_CALIFICACIONES",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDCALIFICACION = #{idcalificacion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ForCalificaciones record);
}