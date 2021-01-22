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
import org.itcgae.siga.db.entities.EcomServicio;
import org.itcgae.siga.db.entities.EcomServicioExample;

public interface EcomServicioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @SelectProvider(type=EcomServicioSqlProvider.class, method="countByExample")
    long countByExample(EcomServicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @DeleteProvider(type=EcomServicioSqlProvider.class, method="deleteByExample")
    int deleteByExample(EcomServicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @Delete({
        "delete from ECOM_SERVICIO",
        "where IDSERVICIO = #{idservicio,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Integer idservicio);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @Insert({
        "insert into ECOM_SERVICIO (IDSERVICIO, NOMBRE, ",
        "ACTIVO, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idservicio,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
        "#{activo,jdbcType=CHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(EcomServicio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @InsertProvider(type=EcomServicioSqlProvider.class, method="insertSelective")
    int insertSelective(EcomServicio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @SelectProvider(type=EcomServicioSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVO", property="activo", jdbcType=JdbcType.CHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<EcomServicio> selectByExample(EcomServicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @Select({
        "select",
        "IDSERVICIO, NOMBRE, ACTIVO, FECHAMODIFICACION, USUMODIFICACION",
        "from ECOM_SERVICIO",
        "where IDSERVICIO = #{idservicio,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDSERVICIO", property="idservicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVO", property="activo", jdbcType=JdbcType.CHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    EcomServicio selectByPrimaryKey(Integer idservicio);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @UpdateProvider(type=EcomServicioSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EcomServicio record, @Param("example") EcomServicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @UpdateProvider(type=EcomServicioSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EcomServicio record, @Param("example") EcomServicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @UpdateProvider(type=EcomServicioSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EcomServicio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.ECOM_SERVICIO
     *
     * @mbg.generated Mon Oct 21 18:09:21 CEST 2019
     */
    @Update({
        "update ECOM_SERVICIO",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "ACTIVO = #{activo,jdbcType=CHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDSERVICIO = #{idservicio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(EcomServicio record);
}