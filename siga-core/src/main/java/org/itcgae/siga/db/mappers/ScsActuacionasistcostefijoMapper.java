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
import org.itcgae.siga.db.entities.ScsActuacionasistcostefijo;
import org.itcgae.siga.db.entities.ScsActuacionasistcostefijoExample;
import org.itcgae.siga.db.entities.ScsActuacionasistcostefijoKey;

public interface ScsActuacionasistcostefijoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @SelectProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="countByExample")
    long countByExample(ScsActuacionasistcostefijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @DeleteProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsActuacionasistcostefijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @Delete({
        "delete from SCS_ACTUACIONASISTCOSTEFIJO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and ANIO = #{anio,jdbcType=DECIMAL}",
          "and NUMERO = #{numero,jdbcType=DECIMAL}",
          "and IDACTUACION = #{idactuacion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
          "and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
          "and IDCOSTEFIJO = #{idcostefijo,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsActuacionasistcostefijoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @Insert({
        "insert into SCS_ACTUACIONASISTCOSTEFIJO (IDINSTITUCION, ANIO, ",
        "NUMERO, IDACTUACION, ",
        "IDTIPOASISTENCIA, IDTIPOACTUACION, ",
        "IDCOSTEFIJO, FACTURADO, ",
        "PAGADO, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{anio,jdbcType=DECIMAL}, ",
        "#{numero,jdbcType=DECIMAL}, #{idactuacion,jdbcType=DECIMAL}, ",
        "#{idtipoasistencia,jdbcType=DECIMAL}, #{idtipoactuacion,jdbcType=DECIMAL}, ",
        "#{idcostefijo,jdbcType=DECIMAL}, #{facturado,jdbcType=VARCHAR}, ",
        "#{pagado,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ScsActuacionasistcostefijo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @InsertProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsActuacionasistcostefijo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @SelectProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDACTUACION", property="idactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOACTUACION", property="idtipoactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCOSTEFIJO", property="idcostefijo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsActuacionasistcostefijo> selectByExample(ScsActuacionasistcostefijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, ANIO, NUMERO, IDACTUACION, IDTIPOASISTENCIA, IDTIPOACTUACION, ",
        "IDCOSTEFIJO, FACTURADO, PAGADO, FECHAMODIFICACION, USUMODIFICACION",
        "from SCS_ACTUACIONASISTCOSTEFIJO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and ANIO = #{anio,jdbcType=DECIMAL}",
          "and NUMERO = #{numero,jdbcType=DECIMAL}",
          "and IDACTUACION = #{idactuacion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
          "and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
          "and IDCOSTEFIJO = #{idcostefijo,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDACTUACION", property="idactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOACTUACION", property="idtipoactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCOSTEFIJO", property="idcostefijo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ScsActuacionasistcostefijo selectByPrimaryKey(ScsActuacionasistcostefijoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @UpdateProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsActuacionasistcostefijo record, @Param("example") ScsActuacionasistcostefijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @UpdateProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsActuacionasistcostefijo record, @Param("example") ScsActuacionasistcostefijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @UpdateProvider(type=ScsActuacionasistcostefijoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsActuacionasistcostefijo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ACTUACIONASISTCOSTEFIJO
     *
     * @mbg.generated Fri Aug 27 13:02:52 CEST 2021
     */
    @Update({
        "update SCS_ACTUACIONASISTCOSTEFIJO",
        "set FACTURADO = #{facturado,jdbcType=VARCHAR},",
          "PAGADO = #{pagado,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and ANIO = #{anio,jdbcType=DECIMAL}",
          "and NUMERO = #{numero,jdbcType=DECIMAL}",
          "and IDACTUACION = #{idactuacion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
          "and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
          "and IDCOSTEFIJO = #{idcostefijo,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsActuacionasistcostefijo record);
}