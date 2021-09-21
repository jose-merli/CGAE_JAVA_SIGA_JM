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
import org.itcgae.siga.db.entities.ScsOrigencajg;
import org.itcgae.siga.db.entities.ScsOrigencajgExample;

public interface ScsOrigencajgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @SelectProvider(type=ScsOrigencajgSqlProvider.class, method="countByExample")
    long countByExample(ScsOrigencajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @DeleteProvider(type=ScsOrigencajgSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsOrigencajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @Delete({
        "delete from SCS_ORIGENCAJG",
        "where IDORIGENCAJG = #{idorigencajg,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idorigencajg);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @Insert({
        "insert into SCS_ORIGENCAJG (IDORIGENCAJG, DESCRIPCION, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "CODIGOEXT, BLOQUEADO, ",
        "FECHA_BAJA)",
        "values (#{idorigencajg,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ",
        "#{fechaBaja,jdbcType=TIMESTAMP})"
    })
    int insert(ScsOrigencajg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @InsertProvider(type=ScsOrigencajgSqlProvider.class, method="insertSelective")
    int insertSelective(ScsOrigencajg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @SelectProvider(type=ScsOrigencajgSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDORIGENCAJG", property="idorigencajg", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ScsOrigencajg> selectByExample(ScsOrigencajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @Select({
        "select",
        "IDORIGENCAJG, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, BLOQUEADO, ",
        "FECHA_BAJA",
        "from SCS_ORIGENCAJG",
        "where IDORIGENCAJG = #{idorigencajg,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDORIGENCAJG", property="idorigencajg", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ScsOrigencajg selectByPrimaryKey(Short idorigencajg);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @UpdateProvider(type=ScsOrigencajgSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsOrigencajg record, @Param("example") ScsOrigencajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @UpdateProvider(type=ScsOrigencajgSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsOrigencajg record, @Param("example") ScsOrigencajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @UpdateProvider(type=ScsOrigencajgSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsOrigencajg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORIGENCAJG
     *
     * @mbg.generated Thu Jan 16 17:01:42 CET 2020
     */
    @Update({
        "update SCS_ORIGENCAJG",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "BLOQUEADO = #{bloqueado,jdbcType=CHAR},",
          "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
        "where IDORIGENCAJG = #{idorigencajg,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsOrigencajg record);
}