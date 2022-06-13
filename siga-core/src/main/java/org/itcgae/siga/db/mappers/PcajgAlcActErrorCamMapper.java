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
import org.itcgae.siga.db.entities.PcajgAlcActErrorCam;
import org.itcgae.siga.db.entities.PcajgAlcActErrorCamExample;

public interface PcajgAlcActErrorCamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @SelectProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="countByExample")
    long countByExample(PcajgAlcActErrorCamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @DeleteProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="deleteByExample")
    int deleteByExample(PcajgAlcActErrorCamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @Delete({
        "delete from PCAJG_ALC_ACT_ERROR_CAM",
        "where IDENTIFICADOR = #{identificador,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long identificador);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @Insert({
        "insert into PCAJG_ALC_ACT_ERROR_CAM (IDENTIFICADOR, IDINSTITUCION, ",
        "IDFACTURACION, REGISTRO_ERROR_CAM, ",
        "CODIGO_ERROR, CODIGO_CAMPO_ERROR, ",
        "OBSERVACIONES_ERROR, EJG_ANIO, ",
        "EJG_NUMEJG, BORRADO, ",
        "USUMODIFICACION, FECHAMODIFICACION)",
        "values (#{identificador,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idfacturacion,jdbcType=DECIMAL}, #{registroErrorCam,jdbcType=VARCHAR}, ",
        "#{codigoError,jdbcType=VARCHAR}, #{codigoCampoError,jdbcType=VARCHAR}, ",
        "#{observacionesError,jdbcType=VARCHAR}, #{ejgAnio,jdbcType=VARCHAR}, ",
        "#{ejgNumejg,jdbcType=VARCHAR}, #{borrado,jdbcType=DECIMAL}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP})"
    })
    int insert(PcajgAlcActErrorCam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @InsertProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="insertSelective")
    int insertSelective(PcajgAlcActErrorCam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @SelectProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDENTIFICADOR", property="identificador", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="REGISTRO_ERROR_CAM", property="registroErrorCam", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGO_ERROR", property="codigoError", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGO_CAMPO_ERROR", property="codigoCampoError", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONES_ERROR", property="observacionesError", jdbcType=JdbcType.VARCHAR),
        @Result(column="EJG_ANIO", property="ejgAnio", jdbcType=JdbcType.VARCHAR),
        @Result(column="EJG_NUMEJG", property="ejgNumejg", jdbcType=JdbcType.VARCHAR),
        @Result(column="BORRADO", property="borrado", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PcajgAlcActErrorCam> selectByExample(PcajgAlcActErrorCamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @Select({
        "select",
        "IDENTIFICADOR, IDINSTITUCION, IDFACTURACION, REGISTRO_ERROR_CAM, CODIGO_ERROR, ",
        "CODIGO_CAMPO_ERROR, OBSERVACIONES_ERROR, EJG_ANIO, EJG_NUMEJG, BORRADO, USUMODIFICACION, ",
        "FECHAMODIFICACION",
        "from PCAJG_ALC_ACT_ERROR_CAM",
        "where IDENTIFICADOR = #{identificador,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDENTIFICADOR", property="identificador", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="REGISTRO_ERROR_CAM", property="registroErrorCam", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGO_ERROR", property="codigoError", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGO_CAMPO_ERROR", property="codigoCampoError", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONES_ERROR", property="observacionesError", jdbcType=JdbcType.VARCHAR),
        @Result(column="EJG_ANIO", property="ejgAnio", jdbcType=JdbcType.VARCHAR),
        @Result(column="EJG_NUMEJG", property="ejgNumejg", jdbcType=JdbcType.VARCHAR),
        @Result(column="BORRADO", property="borrado", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP)
    })
    PcajgAlcActErrorCam selectByPrimaryKey(Long identificador);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @UpdateProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PcajgAlcActErrorCam record, @Param("example") PcajgAlcActErrorCamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @UpdateProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") PcajgAlcActErrorCam record, @Param("example") PcajgAlcActErrorCamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @UpdateProvider(type=PcajgAlcActErrorCamSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PcajgAlcActErrorCam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    @Update({
        "update PCAJG_ALC_ACT_ERROR_CAM",
        "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL},",
          "REGISTRO_ERROR_CAM = #{registroErrorCam,jdbcType=VARCHAR},",
          "CODIGO_ERROR = #{codigoError,jdbcType=VARCHAR},",
          "CODIGO_CAMPO_ERROR = #{codigoCampoError,jdbcType=VARCHAR},",
          "OBSERVACIONES_ERROR = #{observacionesError,jdbcType=VARCHAR},",
          "EJG_ANIO = #{ejgAnio,jdbcType=VARCHAR},",
          "EJG_NUMEJG = #{ejgNumejg,jdbcType=VARCHAR},",
          "BORRADO = #{borrado,jdbcType=DECIMAL},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
        "where IDENTIFICADOR = #{identificador,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(PcajgAlcActErrorCam record);
}