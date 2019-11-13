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
import org.itcgae.siga.db.entities.ScsPreceptivo;
import org.itcgae.siga.db.entities.ScsPreceptivoExample;

public interface ScsPreceptivoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @SelectProvider(type=ScsPreceptivoSqlProvider.class, method="countByExample")
    long countByExample(ScsPreceptivoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @DeleteProvider(type=ScsPreceptivoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsPreceptivoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @Delete({
        "delete from SCS_PRECEPTIVO",
        "where IDPRECEPTIVO = #{idpreceptivo,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idpreceptivo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @Insert({
        "insert into SCS_PRECEPTIVO (IDPRECEPTIVO, DESCRIPCION, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "CODIGOEXT, BLOQUEADO, ",
        "CODIGOEJIS, FECHA_BAJA)",
        "values (#{idpreceptivo,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ",
        "#{codigoejis,jdbcType=VARCHAR}, #{fechaBaja,jdbcType=TIMESTAMP})"
    })
    int insert(ScsPreceptivo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @InsertProvider(type=ScsPreceptivoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsPreceptivo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @SelectProvider(type=ScsPreceptivoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDPRECEPTIVO", property="idpreceptivo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="CODIGOEJIS", property="codigoejis", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ScsPreceptivo> selectByExample(ScsPreceptivoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @Select({
        "select",
        "IDPRECEPTIVO, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, BLOQUEADO, ",
        "CODIGOEJIS, FECHA_BAJA",
        "from SCS_PRECEPTIVO",
        "where IDPRECEPTIVO = #{idpreceptivo,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDPRECEPTIVO", property="idpreceptivo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="CODIGOEJIS", property="codigoejis", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ScsPreceptivo selectByPrimaryKey(Short idpreceptivo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @UpdateProvider(type=ScsPreceptivoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsPreceptivo record, @Param("example") ScsPreceptivoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @UpdateProvider(type=ScsPreceptivoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsPreceptivo record, @Param("example") ScsPreceptivoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @UpdateProvider(type=ScsPreceptivoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsPreceptivo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    @Update({
        "update SCS_PRECEPTIVO",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "BLOQUEADO = #{bloqueado,jdbcType=CHAR},",
          "CODIGOEJIS = #{codigoejis,jdbcType=VARCHAR},",
          "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
        "where IDPRECEPTIVO = #{idpreceptivo,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsPreceptivo record);
}