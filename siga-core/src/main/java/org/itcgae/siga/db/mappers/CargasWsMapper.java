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
import org.itcgae.siga.db.entities.CargasWs;
import org.itcgae.siga.db.entities.CargasWsExample;

public interface CargasWsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @SelectProvider(type=CargasWsSqlProvider.class, method="countByExample")
    long countByExample(CargasWsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @DeleteProvider(type=CargasWsSqlProvider.class, method="deleteByExample")
    int deleteByExample(CargasWsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @Delete({
        "delete from CARGAS_WS",
        "where ID_CARGA = #{idCarga,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idCarga);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @Insert({
        "insert into CARGAS_WS (ID_CARGA, ID_ESTADO_CARGA, ",
        "ID_TIPO_CARGA, CON_ERRORES, ",
        "USU_MODIFICACION, FECHA_CREACION, ",
        "FECHA_MODIFICACION, ID_INSTITUCION, ",
        "NUMERO_PETICION, TOTAL_PAGINAS, ",
        "FECHA_FIN_DE_PROCESO)",
        "values (#{idCarga,jdbcType=DECIMAL}, #{idEstadoCarga,jdbcType=DECIMAL}, ",
        "#{idTipoCarga,jdbcType=DECIMAL}, #{conErrores,jdbcType=DECIMAL}, ",
        "#{usuModificacion,jdbcType=DECIMAL}, #{fechaCreacion,jdbcType=TIMESTAMP}, ",
        "#{fechaModificacion,jdbcType=TIMESTAMP}, #{idInstitucion,jdbcType=DECIMAL}, ",
        "#{numeroPeticion,jdbcType=VARCHAR}, #{totalPaginas,jdbcType=DECIMAL}, ",
        "#{fechaFinDeProceso,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT seq_cargas_ws.NEXTVAL FROM DUAL", keyProperty="idCarga", before=true, resultType=Long.class)
    int insert(CargasWs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @InsertProvider(type=CargasWsSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT seq_cargas_ws.NEXTVAL FROM DUAL", keyProperty="idCarga", before=true, resultType=Long.class)
    int insertSelective(CargasWs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @SelectProvider(type=CargasWsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="ID_CARGA", property="idCarga", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ID_ESTADO_CARGA", property="idEstadoCarga", jdbcType=JdbcType.DECIMAL),
        @Result(column="ID_TIPO_CARGA", property="idTipoCarga", jdbcType=JdbcType.DECIMAL),
        @Result(column="CON_ERRORES", property="conErrores", jdbcType=JdbcType.DECIMAL),
        @Result(column="USU_MODIFICACION", property="usuModificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHA_CREACION", property="fechaCreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHA_MODIFICACION", property="fechaModificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ID_INSTITUCION", property="idInstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMERO_PETICION", property="numeroPeticion", jdbcType=JdbcType.VARCHAR),
        @Result(column="TOTAL_PAGINAS", property="totalPaginas", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHA_FIN_DE_PROCESO", property="fechaFinDeProceso", jdbcType=JdbcType.TIMESTAMP)
    })
    List<CargasWs> selectByExample(CargasWsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @Select({
        "select",
        "ID_CARGA, ID_ESTADO_CARGA, ID_TIPO_CARGA, CON_ERRORES, USU_MODIFICACION, FECHA_CREACION, ",
        "FECHA_MODIFICACION, ID_INSTITUCION, NUMERO_PETICION, TOTAL_PAGINAS, FECHA_FIN_DE_PROCESO",
        "from CARGAS_WS",
        "where ID_CARGA = #{idCarga,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="ID_CARGA", property="idCarga", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ID_ESTADO_CARGA", property="idEstadoCarga", jdbcType=JdbcType.DECIMAL),
        @Result(column="ID_TIPO_CARGA", property="idTipoCarga", jdbcType=JdbcType.DECIMAL),
        @Result(column="CON_ERRORES", property="conErrores", jdbcType=JdbcType.DECIMAL),
        @Result(column="USU_MODIFICACION", property="usuModificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHA_CREACION", property="fechaCreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHA_MODIFICACION", property="fechaModificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ID_INSTITUCION", property="idInstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMERO_PETICION", property="numeroPeticion", jdbcType=JdbcType.VARCHAR),
        @Result(column="TOTAL_PAGINAS", property="totalPaginas", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHA_FIN_DE_PROCESO", property="fechaFinDeProceso", jdbcType=JdbcType.TIMESTAMP)
    })
    CargasWs selectByPrimaryKey(Long idCarga);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @UpdateProvider(type=CargasWsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CargasWs record, @Param("example") CargasWsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @UpdateProvider(type=CargasWsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CargasWs record, @Param("example") CargasWsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @UpdateProvider(type=CargasWsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CargasWs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    @Update({
        "update CARGAS_WS",
        "set ID_ESTADO_CARGA = #{idEstadoCarga,jdbcType=DECIMAL},",
          "ID_TIPO_CARGA = #{idTipoCarga,jdbcType=DECIMAL},",
          "CON_ERRORES = #{conErrores,jdbcType=DECIMAL},",
          "USU_MODIFICACION = #{usuModificacion,jdbcType=DECIMAL},",
          "FECHA_CREACION = #{fechaCreacion,jdbcType=TIMESTAMP},",
          "FECHA_MODIFICACION = #{fechaModificacion,jdbcType=TIMESTAMP},",
          "ID_INSTITUCION = #{idInstitucion,jdbcType=DECIMAL},",
          "NUMERO_PETICION = #{numeroPeticion,jdbcType=VARCHAR},",
          "TOTAL_PAGINAS = #{totalPaginas,jdbcType=DECIMAL},",
          "FECHA_FIN_DE_PROCESO = #{fechaFinDeProceso,jdbcType=TIMESTAMP}",
        "where ID_CARGA = #{idCarga,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(CargasWs record);
}