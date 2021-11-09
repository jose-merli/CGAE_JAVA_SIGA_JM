package org.itcgae.siga.db.mappers;

import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.ScsAuditoriaejg;
import org.itcgae.siga.db.entities.ScsAuditoriaejgExample;

public interface ScsAuditoriaejgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @SelectProvider(type=ScsAuditoriaejgSqlProvider.class, method="countByExample")
    long countByExample(ScsAuditoriaejgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @DeleteProvider(type=ScsAuditoriaejgSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsAuditoriaejgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @Insert({
        "insert into SCS_AUDITORIAEJG (USUMODIFICACION, FECHAMODIFICACION, ",
        "VALORPRE, VALORPOST, ",
        "FECHAHORACAMBIO, CAMPO, ",
        "IDINSTITUCION, NUMERO, ",
        "IDTIPOEJG, ANIO)",
        "values (#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=DATE}, ",
        "#{valorpre,jdbcType=VARCHAR}, #{valorpost,jdbcType=VARCHAR}, ",
        "#{fechahoracambio,jdbcType=TIMESTAMP}, #{campo,jdbcType=VARCHAR}, ",
        "#{idinstitucion,jdbcType=DECIMAL}, #{numero,jdbcType=DECIMAL}, ",
        "#{idtipoejg,jdbcType=DECIMAL}, #{anio,jdbcType=DECIMAL})"
    })
    int insert(ScsAuditoriaejg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @InsertProvider(type=ScsAuditoriaejgSqlProvider.class, method="insertSelective")
    int insertSelective(ScsAuditoriaejg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @SelectProvider(type=ScsAuditoriaejgSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.DATE),
        @Result(column="VALORPRE", property="valorpre", jdbcType=JdbcType.VARCHAR),
        @Result(column="VALORPOST", property="valorpost", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAHORACAMBIO", property="fechahoracambio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CAMPO", property="campo", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOEJG", property="idtipoejg", jdbcType=JdbcType.DECIMAL),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsAuditoriaejg> selectByExample(ScsAuditoriaejgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @UpdateProvider(type=ScsAuditoriaejgSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsAuditoriaejg record, @Param("example") ScsAuditoriaejgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_AUDITORIAEJG
     *
     * @mbg.generated Thu Aug 05 10:36:41 CEST 2021
     */
    @UpdateProvider(type=ScsAuditoriaejgSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsAuditoriaejg record, @Param("example") ScsAuditoriaejgExample example);
}