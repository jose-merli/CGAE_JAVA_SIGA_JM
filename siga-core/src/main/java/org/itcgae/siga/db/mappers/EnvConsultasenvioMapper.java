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
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;

public interface EnvConsultasenvioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @SelectProvider(type=EnvConsultasenvioSqlProvider.class, method="countByExample")
    long countByExample(EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @DeleteProvider(type=EnvConsultasenvioSqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @Delete({
        "delete from ENV_CONSULTASENVIO",
        "where IDCONSULTAENVIO = #{idconsultaenvio,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idconsultaenvio);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @Insert({
        "insert into ENV_CONSULTASENVIO (IDCONSULTAENVIO, IDENVIO, ",
        "IDOBJETIVO, IDINSTITUCION, ",
        "IDCONSULTA, FECHABAJA, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "IDPLANTILLADOCUMENTO, IDMODELOCOMUNICACION, ",
        "IDINFORME, SUFIJO, ",
        "CONSULTA)",
        "values (#{idconsultaenvio,jdbcType=DECIMAL}, #{idenvio,jdbcType=DECIMAL}, ",
        "#{idobjetivo,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idconsulta,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{idplantilladocumento,jdbcType=DECIMAL}, #{idmodelocomunicacion,jdbcType=DECIMAL}, ",
        "#{idinforme,jdbcType=DECIMAL}, #{sufijo,jdbcType=VARCHAR}, ",
        "#{consulta,jdbcType=CLOB})"
    })
    @SelectKey(statement="SELECT SEQ_ENV_PLANTILLASENVIO.NEXTVAL FROM DUAL", keyProperty="idconsultaenvio", before=true, resultType=Long.class)
    int insert(EnvConsultasenvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @InsertProvider(type=EnvConsultasenvioSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SEQ_ENV_PLANTILLASENVIO.NEXTVAL FROM DUAL", keyProperty="idconsultaenvio", before=true, resultType=Long.class)
    int insertSelective(EnvConsultasenvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @SelectProvider(type=EnvConsultasenvioSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="IDCONSULTAENVIO", property="idconsultaenvio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDOBJETIVO", property="idobjetivo", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPLANTILLADOCUMENTO", property="idplantilladocumento", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINFORME", property="idinforme", jdbcType=JdbcType.DECIMAL),
        @Result(column="SUFIJO", property="sufijo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONSULTA", property="consulta", jdbcType=JdbcType.CLOB)
    })
    List<EnvConsultasenvio> selectByExampleWithBLOBs(EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @SelectProvider(type=EnvConsultasenvioSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCONSULTAENVIO", property="idconsultaenvio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDOBJETIVO", property="idobjetivo", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPLANTILLADOCUMENTO", property="idplantilladocumento", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINFORME", property="idinforme", jdbcType=JdbcType.DECIMAL),
        @Result(column="SUFIJO", property="sufijo", jdbcType=JdbcType.VARCHAR)
    })
    List<EnvConsultasenvio> selectByExample(EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @Select({
        "select",
        "IDCONSULTAENVIO, IDENVIO, IDOBJETIVO, IDINSTITUCION, IDCONSULTA, FECHABAJA, ",
        "FECHAMODIFICACION, USUMODIFICACION, IDPLANTILLADOCUMENTO, IDMODELOCOMUNICACION, ",
        "IDINFORME, SUFIJO, CONSULTA",
        "from ENV_CONSULTASENVIO",
        "where IDCONSULTAENVIO = #{idconsultaenvio,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCONSULTAENVIO", property="idconsultaenvio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDOBJETIVO", property="idobjetivo", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPLANTILLADOCUMENTO", property="idplantilladocumento", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINFORME", property="idinforme", jdbcType=JdbcType.DECIMAL),
        @Result(column="SUFIJO", property="sufijo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONSULTA", property="consulta", jdbcType=JdbcType.CLOB)
    })
    EnvConsultasenvio selectByPrimaryKey(Long idconsultaenvio);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @UpdateProvider(type=EnvConsultasenvioSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvConsultasenvio record, @Param("example") EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @UpdateProvider(type=EnvConsultasenvioSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") EnvConsultasenvio record, @Param("example") EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @UpdateProvider(type=EnvConsultasenvioSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvConsultasenvio record, @Param("example") EnvConsultasenvioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @UpdateProvider(type=EnvConsultasenvioSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EnvConsultasenvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @Update({
        "update ENV_CONSULTASENVIO",
        "set IDENVIO = #{idenvio,jdbcType=DECIMAL},",
          "IDOBJETIVO = #{idobjetivo,jdbcType=DECIMAL},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "IDCONSULTA = #{idconsulta,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL},",
          "IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL},",
          "IDINFORME = #{idinforme,jdbcType=DECIMAL},",
          "SUFIJO = #{sufijo,jdbcType=VARCHAR},",
          "CONSULTA = #{consulta,jdbcType=CLOB}",
        "where IDCONSULTAENVIO = #{idconsultaenvio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKeyWithBLOBs(EnvConsultasenvio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    @Update({
        "update ENV_CONSULTASENVIO",
        "set IDENVIO = #{idenvio,jdbcType=DECIMAL},",
          "IDOBJETIVO = #{idobjetivo,jdbcType=DECIMAL},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "IDCONSULTA = #{idconsulta,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL},",
          "IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL},",
          "IDINFORME = #{idinforme,jdbcType=DECIMAL},",
          "SUFIJO = #{sufijo,jdbcType=VARCHAR}",
        "where IDCONSULTAENVIO = #{idconsultaenvio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(EnvConsultasenvio record);
}