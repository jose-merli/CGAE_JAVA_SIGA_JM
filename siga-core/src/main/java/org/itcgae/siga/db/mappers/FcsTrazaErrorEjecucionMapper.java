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
import org.itcgae.siga.db.entities.FcsTrazaErrorEjecucion;
import org.itcgae.siga.db.entities.FcsTrazaErrorEjecucionExample;

public interface FcsTrazaErrorEjecucionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @SelectProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="countByExample")
    long countByExample(FcsTrazaErrorEjecucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @DeleteProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="deleteByExample")
    int deleteByExample(FcsTrazaErrorEjecucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @Delete({
        "delete from FCS_TRAZA_ERROR_EJECUCION",
        "where IDERROR = #{iderror,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long iderror);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @Insert({
        "insert into FCS_TRAZA_ERROR_EJECUCION (IDERROR, IDINSTITUCION, ",
        "IDFACTURACION, IDOPERACION, ",
        "DESCRIPCION, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{iderror,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idfacturacion,jdbcType=DECIMAL}, #{idoperacion,jdbcType=VARCHAR}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(FcsTrazaErrorEjecucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @InsertProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="insertSelective")
    int insertSelective(FcsTrazaErrorEjecucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @SelectProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDERROR", property="iderror", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDOPERACION", property="idoperacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<FcsTrazaErrorEjecucion> selectByExample(FcsTrazaErrorEjecucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @Select({
        "select",
        "IDERROR, IDINSTITUCION, IDFACTURACION, IDOPERACION, DESCRIPCION, FECHAMODIFICACION, ",
        "USUMODIFICACION",
        "from FCS_TRAZA_ERROR_EJECUCION",
        "where IDERROR = #{iderror,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDERROR", property="iderror", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDOPERACION", property="idoperacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    FcsTrazaErrorEjecucion selectByPrimaryKey(Long iderror);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @UpdateProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FcsTrazaErrorEjecucion record, @Param("example") FcsTrazaErrorEjecucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @UpdateProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FcsTrazaErrorEjecucion record, @Param("example") FcsTrazaErrorEjecucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @UpdateProvider(type=FcsTrazaErrorEjecucionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FcsTrazaErrorEjecucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_TRAZA_ERROR_EJECUCION
     *
     */
    @Update({
        "update FCS_TRAZA_ERROR_EJECUCION",
        "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL},",
          "IDOPERACION = #{idoperacion,jdbcType=VARCHAR},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDERROR = #{iderror,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FcsTrazaErrorEjecucion record);
}