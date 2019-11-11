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
import org.itcgae.siga.db.entities.ScsTelefonospersona;
import org.itcgae.siga.db.entities.ScsTelefonospersonaExample;
import org.itcgae.siga.db.entities.ScsTelefonospersonaKey;

public interface ScsTelefonospersonaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @SelectProvider(type=ScsTelefonospersonaSqlProvider.class, method="countByExample")
    long countByExample(ScsTelefonospersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @DeleteProvider(type=ScsTelefonospersonaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsTelefonospersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @Delete({
        "delete from SCS_TELEFONOSPERSONA",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
          "and IDTELEFONO = #{idtelefono,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsTelefonospersonaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @Insert({
        "insert into SCS_TELEFONOSPERSONA (IDINSTITUCION, IDPERSONA, ",
        "IDTELEFONO, NOMBRETELEFONO, ",
        "NUMEROTELEFONO, FECHAMODIFICACION, ",
        "USUMODIFICACION, PREFERENTESMS)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{idtelefono,jdbcType=DECIMAL}, #{nombretelefono,jdbcType=VARCHAR}, ",
        "#{numerotelefono,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{preferentesms,jdbcType=DECIMAL})"
    })
    int insert(ScsTelefonospersona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @InsertProvider(type=ScsTelefonospersonaSqlProvider.class, method="insertSelective")
    int insertSelective(ScsTelefonospersona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @SelectProvider(type=ScsTelefonospersonaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTELEFONO", property="idtelefono", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRETELEFONO", property="nombretelefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROTELEFONO", property="numerotelefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="PREFERENTESMS", property="preferentesms", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsTelefonospersona> selectByExample(ScsTelefonospersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDPERSONA, IDTELEFONO, NOMBRETELEFONO, NUMEROTELEFONO, FECHAMODIFICACION, ",
        "USUMODIFICACION, PREFERENTESMS",
        "from SCS_TELEFONOSPERSONA",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
          "and IDTELEFONO = #{idtelefono,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTELEFONO", property="idtelefono", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRETELEFONO", property="nombretelefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROTELEFONO", property="numerotelefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="PREFERENTESMS", property="preferentesms", jdbcType=JdbcType.DECIMAL)
    })
    ScsTelefonospersona selectByPrimaryKey(ScsTelefonospersonaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @UpdateProvider(type=ScsTelefonospersonaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsTelefonospersona record, @Param("example") ScsTelefonospersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @UpdateProvider(type=ScsTelefonospersonaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsTelefonospersona record, @Param("example") ScsTelefonospersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @UpdateProvider(type=ScsTelefonospersonaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsTelefonospersona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TELEFONOSPERSONA
     *
     * @mbg.generated Wed Oct 23 14:48:30 CEST 2019
     */
    @Update({
        "update SCS_TELEFONOSPERSONA",
        "set NOMBRETELEFONO = #{nombretelefono,jdbcType=VARCHAR},",
          "NUMEROTELEFONO = #{numerotelefono,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "PREFERENTESMS = #{preferentesms,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
          "and IDTELEFONO = #{idtelefono,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsTelefonospersona record);
}