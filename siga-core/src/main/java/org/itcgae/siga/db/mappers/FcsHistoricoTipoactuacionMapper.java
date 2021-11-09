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
import org.itcgae.siga.db.entities.FcsHistoricoTipoactuacion;
import org.itcgae.siga.db.entities.FcsHistoricoTipoactuacionExample;
import org.itcgae.siga.db.entities.FcsHistoricoTipoactuacionKey;

public interface FcsHistoricoTipoactuacionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @SelectProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="countByExample")
    long countByExample(FcsHistoricoTipoactuacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @DeleteProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="deleteByExample")
    int deleteByExample(FcsHistoricoTipoactuacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @Delete({
        "delete from FCS_HISTORICO_TIPOACTUACION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
          "and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
          "and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(FcsHistoricoTipoactuacionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @Insert({
        "insert into FCS_HISTORICO_TIPOACTUACION (IDINSTITUCION, IDTIPOASISTENCIA, ",
        "IDTIPOACTUACION, IDFACTURACION, ",
        "DESCRIPCION, IMPORTE, ",
        "FECHACREACION, FECHAMODIFICACION, ",
        "IMPORTEMAXIMO, USUMODIFICACION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoasistencia,jdbcType=DECIMAL}, ",
        "#{idtipoactuacion,jdbcType=DECIMAL}, #{idfacturacion,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{importe,jdbcType=DECIMAL}, ",
        "#{fechacreacion,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{importemaximo,jdbcType=DECIMAL}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(FcsHistoricoTipoactuacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @InsertProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="insertSelective")
    int insertSelective(FcsHistoricoTipoactuacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @SelectProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOACTUACION", property="idtipoactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IMPORTE", property="importe", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IMPORTEMAXIMO", property="importemaximo", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<FcsHistoricoTipoactuacion> selectByExample(FcsHistoricoTipoactuacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTIPOASISTENCIA, IDTIPOACTUACION, IDFACTURACION, DESCRIPCION, ",
        "IMPORTE, FECHACREACION, FECHAMODIFICACION, IMPORTEMAXIMO, USUMODIFICACION",
        "from FCS_HISTORICO_TIPOACTUACION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
          "and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
          "and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOACTUACION", property="idtipoactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IMPORTE", property="importe", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IMPORTEMAXIMO", property="importemaximo", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    FcsHistoricoTipoactuacion selectByPrimaryKey(FcsHistoricoTipoactuacionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @UpdateProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FcsHistoricoTipoactuacion record, @Param("example") FcsHistoricoTipoactuacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @UpdateProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FcsHistoricoTipoactuacion record, @Param("example") FcsHistoricoTipoactuacionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @UpdateProvider(type=FcsHistoricoTipoactuacionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FcsHistoricoTipoactuacion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    @Update({
        "update FCS_HISTORICO_TIPOACTUACION",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "IMPORTE = #{importe,jdbcType=DECIMAL},",
          "FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "IMPORTEMAXIMO = #{importemaximo,jdbcType=DECIMAL},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
          "and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
          "and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FcsHistoricoTipoactuacion record);
}