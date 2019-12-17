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
import org.itcgae.siga.db.entities.ScsTipoejgcolegio;
import org.itcgae.siga.db.entities.ScsTipoejgcolegioExample;
import org.itcgae.siga.db.entities.ScsTipoejgcolegioKey;

public interface ScsTipoejgcolegioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @SelectProvider(type=ScsTipoejgcolegioSqlProvider.class, method="countByExample")
    long countByExample(ScsTipoejgcolegioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @DeleteProvider(type=ScsTipoejgcolegioSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsTipoejgcolegioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @Delete({
        "delete from SCS_TIPOEJGCOLEGIO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOEJGCOLEGIO = #{idtipoejgcolegio,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsTipoejgcolegioKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @Insert({
        "insert into SCS_TIPOEJGCOLEGIO (IDINSTITUCION, IDTIPOEJGCOLEGIO, ",
        "DESCRIPCION, FECHAMODIFICACION, ",
        "USUMODIFICACION, CODIGOEXT, ",
        "BLOQUEADO, FECHA_BAJA)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoejgcolegio,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
        "#{bloqueado,jdbcType=CHAR}, #{fechaBaja,jdbcType=TIMESTAMP})"
    })
    int insert(ScsTipoejgcolegio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @InsertProvider(type=ScsTipoejgcolegioSqlProvider.class, method="insertSelective")
    int insertSelective(ScsTipoejgcolegio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @SelectProvider(type=ScsTipoejgcolegioSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOEJGCOLEGIO", property="idtipoejgcolegio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ScsTipoejgcolegio> selectByExample(ScsTipoejgcolegioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOEJGCOLEGIO, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, ",
        "CODIGOEXT, BLOQUEADO, FECHA_BAJA",
        "from SCS_TIPOEJGCOLEGIO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOEJGCOLEGIO = #{idtipoejgcolegio,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOEJGCOLEGIO", property="idtipoejgcolegio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ScsTipoejgcolegio selectByPrimaryKey(ScsTipoejgcolegioKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @UpdateProvider(type=ScsTipoejgcolegioSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsTipoejgcolegio record, @Param("example") ScsTipoejgcolegioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @UpdateProvider(type=ScsTipoejgcolegioSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsTipoejgcolegio record, @Param("example") ScsTipoejgcolegioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @UpdateProvider(type=ScsTipoejgcolegioSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsTipoejgcolegio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOEJGCOLEGIO
     *
     * @mbg.generated Fri Nov 29 11:55:04 CET 2019
     */
    @Update({
        "update SCS_TIPOEJGCOLEGIO",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "BLOQUEADO = #{bloqueado,jdbcType=CHAR},",
          "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOEJGCOLEGIO = #{idtipoejgcolegio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsTipoejgcolegio record);
}