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
import org.itcgae.siga.db.entities.ScsConfConjuntoGuardias;
import org.itcgae.siga.db.entities.ScsConfConjuntoGuardiasExample;
import org.itcgae.siga.db.entities.ScsConfConjuntoGuardiasKey;

public interface ScsConfConjuntoGuardiasMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @SelectProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="countByExample")
    long countByExample(ScsConfConjuntoGuardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @DeleteProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsConfConjuntoGuardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @Delete({
        "delete from SCS_CONF_CONJUNTO_GUARDIAS",
        "where IDCONJUNTOGUARDIA = #{idconjuntoguardia,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
          "and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsConfConjuntoGuardiasKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @Insert({
        "insert into SCS_CONF_CONJUNTO_GUARDIAS (IDCONJUNTOGUARDIA, IDINSTITUCION, ",
        "IDTURNO, IDGUARDIA, ",
        "ORDEN, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idconjuntoguardia,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idturno,jdbcType=DECIMAL}, #{idguardia,jdbcType=DECIMAL}, ",
        "#{orden,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ScsConfConjuntoGuardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @InsertProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="insertSelective")
    int insertSelective(ScsConfConjuntoGuardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @SelectProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCONJUNTOGUARDIA", property="idconjuntoguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsConfConjuntoGuardias> selectByExample(ScsConfConjuntoGuardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @Select({
        "select",
        "IDCONJUNTOGUARDIA, IDINSTITUCION, IDTURNO, IDGUARDIA, ORDEN, FECHAMODIFICACION, ",
        "USUMODIFICACION",
        "from SCS_CONF_CONJUNTO_GUARDIAS",
        "where IDCONJUNTOGUARDIA = #{idconjuntoguardia,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
          "and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCONJUNTOGUARDIA", property="idconjuntoguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ScsConfConjuntoGuardias selectByPrimaryKey(ScsConfConjuntoGuardiasKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @UpdateProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsConfConjuntoGuardias record, @Param("example") ScsConfConjuntoGuardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @UpdateProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsConfConjuntoGuardias record, @Param("example") ScsConfConjuntoGuardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @UpdateProvider(type=ScsConfConjuntoGuardiasSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsConfConjuntoGuardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONF_CONJUNTO_GUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:55:58 CEST 2021
     */
    @Update({
        "update SCS_CONF_CONJUNTO_GUARDIAS",
        "set ORDEN = #{orden,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDCONJUNTOGUARDIA = #{idconjuntoguardia,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
          "and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsConfConjuntoGuardias record);
}