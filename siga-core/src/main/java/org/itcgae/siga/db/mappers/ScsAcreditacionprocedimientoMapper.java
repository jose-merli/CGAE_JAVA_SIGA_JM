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
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimiento;
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimientoExample;
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimientoKey;

public interface ScsAcreditacionprocedimientoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @SelectProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="countByExample")
    long countByExample(ScsAcreditacionprocedimientoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @DeleteProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsAcreditacionprocedimientoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @Delete({
        "delete from SCS_ACREDITACIONPROCEDIMIENTO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}",
          "and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsAcreditacionprocedimientoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @Insert({
        "insert into SCS_ACREDITACIONPROCEDIMIENTO (IDINSTITUCION, IDPROCEDIMIENTO, ",
        "IDACREDITACION, PORCENTAJE, ",
        "USUMODIFICACION, FECHAMODIFICACION, ",
        "CODIGOEXT, NIG_NUMPROCEDIMIENTO, ",
        "CODSUBTARIFA)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idprocedimiento,jdbcType=VARCHAR}, ",
        "#{idacreditacion,jdbcType=DECIMAL}, #{porcentaje,jdbcType=DECIMAL}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{codigoext,jdbcType=VARCHAR}, #{nigNumprocedimiento,jdbcType=DECIMAL}, ",
        "#{codsubtarifa,jdbcType=VARCHAR})"
    })
    int insert(ScsAcreditacionprocedimiento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @InsertProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsAcreditacionprocedimiento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @SelectProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPROCEDIMIENTO", property="idprocedimiento", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="IDACREDITACION", property="idacreditacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PORCENTAJE", property="porcentaje", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="NIG_NUMPROCEDIMIENTO", property="nigNumprocedimiento", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODSUBTARIFA", property="codsubtarifa", jdbcType=JdbcType.VARCHAR)
    })
    List<ScsAcreditacionprocedimiento> selectByExample(ScsAcreditacionprocedimientoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDPROCEDIMIENTO, IDACREDITACION, PORCENTAJE, USUMODIFICACION, ",
        "FECHAMODIFICACION, CODIGOEXT, NIG_NUMPROCEDIMIENTO, CODSUBTARIFA",
        "from SCS_ACREDITACIONPROCEDIMIENTO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}",
          "and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPROCEDIMIENTO", property="idprocedimiento", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="IDACREDITACION", property="idacreditacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="PORCENTAJE", property="porcentaje", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="NIG_NUMPROCEDIMIENTO", property="nigNumprocedimiento", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODSUBTARIFA", property="codsubtarifa", jdbcType=JdbcType.VARCHAR)
    })
    ScsAcreditacionprocedimiento selectByPrimaryKey(ScsAcreditacionprocedimientoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @UpdateProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsAcreditacionprocedimiento record, @Param("example") ScsAcreditacionprocedimientoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @UpdateProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsAcreditacionprocedimiento record, @Param("example") ScsAcreditacionprocedimientoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @UpdateProvider(type=ScsAcreditacionprocedimientoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsAcreditacionprocedimiento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACREDITACIONPROCEDIMIENTO
     *
     * @mbg.generated Tue Sep 24 13:21:44 CEST 2019
     */
    @Update({
        "update SCS_ACREDITACIONPROCEDIMIENTO",
        "set PORCENTAJE = #{porcentaje,jdbcType=DECIMAL},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "NIG_NUMPROCEDIMIENTO = #{nigNumprocedimiento,jdbcType=DECIMAL},",
          "CODSUBTARIFA = #{codsubtarifa,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}",
          "and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsAcreditacionprocedimiento record);
}