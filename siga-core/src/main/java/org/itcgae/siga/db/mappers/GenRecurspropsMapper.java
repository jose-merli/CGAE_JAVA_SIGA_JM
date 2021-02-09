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
import org.itcgae.siga.db.entities.GenRecursprops;
import org.itcgae.siga.db.entities.GenRecurspropsExample;

public interface GenRecurspropsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenRecurspropsSqlProvider.class, method="countByExample")
    long countByExample(GenRecurspropsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @DeleteProvider(type=GenRecurspropsSqlProvider.class, method="deleteByExample")
    int deleteByExample(GenRecurspropsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Delete({
        "delete from GEN_RECURSPROPS",
        "where IDPROPIEDAD = #{idpropiedad,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String idpropiedad);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Insert({
        "insert into GEN_RECURSPROPS (IDPROPIEDAD, MAXLENGTH, ",
        "FIXLENGTH, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idpropiedad,jdbcType=VARCHAR}, #{maxlength,jdbcType=DECIMAL}, ",
        "#{fixlength,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(GenRecursprops record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @InsertProvider(type=GenRecurspropsSqlProvider.class, method="insertSelective")
    int insertSelective(GenRecursprops record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenRecurspropsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDPROPIEDAD", property="idpropiedad", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="MAXLENGTH", property="maxlength", jdbcType=JdbcType.DECIMAL),
        @Result(column="FIXLENGTH", property="fixlength", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<GenRecursprops> selectByExample(GenRecurspropsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Select({
        "select",
        "IDPROPIEDAD, MAXLENGTH, FIXLENGTH, FECHAMODIFICACION, USUMODIFICACION",
        "from GEN_RECURSPROPS",
        "where IDPROPIEDAD = #{idpropiedad,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="IDPROPIEDAD", property="idpropiedad", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="MAXLENGTH", property="maxlength", jdbcType=JdbcType.DECIMAL),
        @Result(column="FIXLENGTH", property="fixlength", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    GenRecursprops selectByPrimaryKey(String idpropiedad);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenRecurspropsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") GenRecursprops record, @Param("example") GenRecurspropsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenRecurspropsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") GenRecursprops record, @Param("example") GenRecurspropsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenRecurspropsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(GenRecursprops record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_RECURSPROPS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Update({
        "update GEN_RECURSPROPS",
        "set MAXLENGTH = #{maxlength,jdbcType=DECIMAL},",
          "FIXLENGTH = #{fixlength,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDPROPIEDAD = #{idpropiedad,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(GenRecursprops record);
}