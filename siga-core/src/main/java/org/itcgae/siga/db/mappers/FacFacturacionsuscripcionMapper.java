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
import org.itcgae.siga.db.entities.FacFacturacionsuscripcion;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcionExample;
import org.itcgae.siga.db.entities.FacFacturacionsuscripcionKey;

public interface FacFacturacionsuscripcionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @SelectProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="countByExample")
    long countByExample(FacFacturacionsuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @DeleteProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="deleteByExample")
    int deleteByExample(FacFacturacionsuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @Delete({
        "delete from FAC_FACTURACIONSUSCRIPCION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDFACTURA = #{idfactura,jdbcType=VARCHAR}",
          "and NUMEROLINEA = #{numerolinea,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(FacFacturacionsuscripcionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @Insert({
        "insert into FAC_FACTURACIONSUSCRIPCION (IDINSTITUCION, IDFACTURA, ",
        "NUMEROLINEA, IDTIPOSERVICIOS, ",
        "IDSERVICIO, IDSERVICIOSINSTITUCION, ",
        "IDSUSCRIPCION, IDFACTURACIONSUSCRIPCION, ",
        "FECHAINICIO, FECHAFIN, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "DESCRIPCION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idfactura,jdbcType=VARCHAR}, ",
        "#{numerolinea,jdbcType=DECIMAL}, #{idtiposervicios,jdbcType=DECIMAL}, ",
        "#{idservicio,jdbcType=DECIMAL}, #{idserviciosinstitucion,jdbcType=DECIMAL}, ",
        "#{idsuscripcion,jdbcType=DECIMAL}, #{idfacturacionsuscripcion,jdbcType=DECIMAL}, ",
        "#{fechainicio,jdbcType=TIMESTAMP}, #{fechafin,jdbcType=TIMESTAMP}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR})"
    })
    int insert(FacFacturacionsuscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @InsertProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="insertSelective")
    int insertSelective(FacFacturacionsuscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @SelectProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDFACTURA", property="idfactura", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="NUMEROLINEA", property="numerolinea", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOSERVICIOS", property="idtiposervicios", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSERVICIOSINSTITUCION", property="idserviciosinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSUSCRIPCION", property="idsuscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURACIONSUSCRIPCION", property="idfacturacionsuscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAINICIO", property="fechainicio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAFIN", property="fechafin", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR)
    })
    List<FacFacturacionsuscripcion> selectByExample(FacFacturacionsuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, IDFACTURA, NUMEROLINEA, IDTIPOSERVICIOS, IDSERVICIO, IDSERVICIOSINSTITUCION, ",
        "IDSUSCRIPCION, IDFACTURACIONSUSCRIPCION, FECHAINICIO, FECHAFIN, FECHAMODIFICACION, ",
        "USUMODIFICACION, DESCRIPCION",
        "from FAC_FACTURACIONSUSCRIPCION",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDFACTURA = #{idfactura,jdbcType=VARCHAR}",
          "and NUMEROLINEA = #{numerolinea,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDFACTURA", property="idfactura", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="NUMEROLINEA", property="numerolinea", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOSERVICIOS", property="idtiposervicios", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSERVICIOSINSTITUCION", property="idserviciosinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSUSCRIPCION", property="idsuscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURACIONSUSCRIPCION", property="idfacturacionsuscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAINICIO", property="fechainicio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAFIN", property="fechafin", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR)
    })
    FacFacturacionsuscripcion selectByPrimaryKey(FacFacturacionsuscripcionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @UpdateProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FacFacturacionsuscripcion record, @Param("example") FacFacturacionsuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @UpdateProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FacFacturacionsuscripcion record, @Param("example") FacFacturacionsuscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @UpdateProvider(type=FacFacturacionsuscripcionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FacFacturacionsuscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_FACTURACIONSUSCRIPCION
     *
     * @mbg.generated Mon Oct 18 09:51:36 CEST 2021
     */
    @Update({
        "update FAC_FACTURACIONSUSCRIPCION",
        "set IDTIPOSERVICIOS = #{idtiposervicios,jdbcType=DECIMAL},",
          "IDSERVICIO = #{idservicio,jdbcType=DECIMAL},",
          "IDSERVICIOSINSTITUCION = #{idserviciosinstitucion,jdbcType=DECIMAL},",
          "IDSUSCRIPCION = #{idsuscripcion,jdbcType=DECIMAL},",
          "IDFACTURACIONSUSCRIPCION = #{idfacturacionsuscripcion,jdbcType=DECIMAL},",
          "FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP},",
          "FECHAFIN = #{fechafin,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDFACTURA = #{idfactura,jdbcType=VARCHAR}",
          "and NUMEROLINEA = #{numerolinea,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FacFacturacionsuscripcion record);
}