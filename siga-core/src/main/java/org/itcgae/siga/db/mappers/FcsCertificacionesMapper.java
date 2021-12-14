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
import org.itcgae.siga.db.entities.FcsCertificaciones;
import org.itcgae.siga.db.entities.FcsCertificacionesExample;
import org.itcgae.siga.db.entities.FcsCertificacionesKey;

public interface FcsCertificacionesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @SelectProvider(type=FcsCertificacionesSqlProvider.class, method="countByExample")
    long countByExample(FcsCertificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @DeleteProvider(type=FcsCertificacionesSqlProvider.class, method="deleteByExample")
    int deleteByExample(FcsCertificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @Delete({
        "delete from FCS_CERTIFICACIONES",
        "where IDCERTIFICACION = #{idcertificacion,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(FcsCertificacionesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @Insert({
        "insert into FCS_CERTIFICACIONES (IDCERTIFICACION, IDINSTITUCION, ",
        "IDESTADOCERTIFICACION, FECHADESDE, ",
        "FECHAHASTA, IDHITOGENERAL, ",
        "NOMBRE, IDGRUPOFACTURACION, ",
        "IDPARTIDAPRESUPUESTARIA, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idcertificacion,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idestadocertificacion,jdbcType=DECIMAL}, #{fechadesde,jdbcType=TIMESTAMP}, ",
        "#{fechahasta,jdbcType=TIMESTAMP}, #{idhitogeneral,jdbcType=DECIMAL}, ",
        "#{nombre,jdbcType=VARCHAR}, #{idgrupofacturacion,jdbcType=DECIMAL}, ",
        "#{idpartidapresupuestaria,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT SEQ_FCS_CERTIFICACIONES.NEXTVAL FROM DUAL", keyProperty="idcertificacion", before=true, resultType=Short.class)
    int insert(FcsCertificaciones record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @InsertProvider(type=FcsCertificacionesSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SEQ_FCS_CERTIFICACIONES.NEXTVAL FROM DUAL", keyProperty="idcertificacion", before=true, resultType=Short.class)
    int insertSelective(FcsCertificaciones record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @SelectProvider(type=FcsCertificacionesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCERTIFICACION", property="idcertificacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDESTADOCERTIFICACION", property="idestadocertificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHADESDE", property="fechadesde", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAHASTA", property="fechahasta", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDHITOGENERAL", property="idhitogeneral", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDGRUPOFACTURACION", property="idgrupofacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARTIDAPRESUPUESTARIA", property="idpartidapresupuestaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<FcsCertificaciones> selectByExample(FcsCertificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @Select({
        "select",
        "IDCERTIFICACION, IDINSTITUCION, IDESTADOCERTIFICACION, FECHADESDE, FECHAHASTA, ",
        "IDHITOGENERAL, NOMBRE, IDGRUPOFACTURACION, IDPARTIDAPRESUPUESTARIA, FECHAMODIFICACION, ",
        "USUMODIFICACION",
        "from FCS_CERTIFICACIONES",
        "where IDCERTIFICACION = #{idcertificacion,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCERTIFICACION", property="idcertificacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDESTADOCERTIFICACION", property="idestadocertificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHADESDE", property="fechadesde", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAHASTA", property="fechahasta", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDHITOGENERAL", property="idhitogeneral", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDGRUPOFACTURACION", property="idgrupofacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARTIDAPRESUPUESTARIA", property="idpartidapresupuestaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    FcsCertificaciones selectByPrimaryKey(FcsCertificacionesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @UpdateProvider(type=FcsCertificacionesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FcsCertificaciones record, @Param("example") FcsCertificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @UpdateProvider(type=FcsCertificacionesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FcsCertificaciones record, @Param("example") FcsCertificacionesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @UpdateProvider(type=FcsCertificacionesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FcsCertificaciones record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    @Update({
        "update FCS_CERTIFICACIONES",
        "set IDESTADOCERTIFICACION = #{idestadocertificacion,jdbcType=DECIMAL},",
          "FECHADESDE = #{fechadesde,jdbcType=TIMESTAMP},",
          "FECHAHASTA = #{fechahasta,jdbcType=TIMESTAMP},",
          "IDHITOGENERAL = #{idhitogeneral,jdbcType=DECIMAL},",
          "NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "IDGRUPOFACTURACION = #{idgrupofacturacion,jdbcType=DECIMAL},",
          "IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDCERTIFICACION = #{idcertificacion,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FcsCertificaciones record);
}