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
import org.itcgae.siga.db.entities.ScsSituacion;
import org.itcgae.siga.db.entities.ScsSituacionExample;

public interface ScsSituacionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @SelectProvider(type=ScsSituacionSqlProvider.class, method="countByExample")
    long countByExample(ScsSituacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @DeleteProvider(type=ScsSituacionSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsSituacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @Delete({
        "delete from SCS_SITUACION",
        "where IDSITUACION = #{idsituacion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idsituacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @Insert({
        "insert into SCS_SITUACION (IDSITUACION, DESCRIPCION, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "CODIGOEXT, BLOQUEADO, ",
        "CODIGOEJIS, FECHA_BAJA)",
        "values (#{idsituacion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ",
        "#{codigoejis,jdbcType=VARCHAR}, #{fechaBaja,jdbcType=TIMESTAMP})"
    })
    int insert(ScsSituacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @InsertProvider(type=ScsSituacionSqlProvider.class, method="insertSelective")
    int insertSelective(ScsSituacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @SelectProvider(type=ScsSituacionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDSITUACION", property="idsituacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="CODIGOEJIS", property="codigoejis", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ScsSituacion> selectByExample(ScsSituacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @Select({
        "select",
        "IDSITUACION, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, BLOQUEADO, ",
        "CODIGOEJIS, FECHA_BAJA",
        "from SCS_SITUACION",
        "where IDSITUACION = #{idsituacion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDSITUACION", property="idsituacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="CODIGOEJIS", property="codigoejis", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ScsSituacion selectByPrimaryKey(Short idsituacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @UpdateProvider(type=ScsSituacionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsSituacion record, @Param("example") ScsSituacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @UpdateProvider(type=ScsSituacionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsSituacion record, @Param("example") ScsSituacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @UpdateProvider(type=ScsSituacionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsSituacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SITUACION
     *
     * @mbg.generated Mon Jun 07 10:08:40 CEST 2021
     */
    @Update({
        "update SCS_SITUACION",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "BLOQUEADO = #{bloqueado,jdbcType=CHAR},",
          "CODIGOEJIS = #{codigoejis,jdbcType=VARCHAR},",
          "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
        "where IDSITUACION = #{idsituacion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsSituacion record);
}