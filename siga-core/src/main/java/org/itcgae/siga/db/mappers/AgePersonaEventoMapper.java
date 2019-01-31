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
import org.itcgae.siga.db.entities.AgePersonaEvento;
import org.itcgae.siga.db.entities.AgePersonaEventoExample;

public interface AgePersonaEventoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @SelectProvider(type=AgePersonaEventoSqlProvider.class, method="countByExample")
    long countByExample(AgePersonaEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @DeleteProvider(type=AgePersonaEventoSqlProvider.class, method="deleteByExample")
    int deleteByExample(AgePersonaEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @Delete({
        "delete from AGE_PERSONA_EVENTO",
        "where IDFORMADOREVENTO = #{idformadorevento,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idformadorevento);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @Insert({
        "insert into AGE_PERSONA_EVENTO (IDFORMADOREVENTO, IDPERSONA, ",
        "IDEVENTO, USUMODIFICACION, ",
        "FECHAMODIFICACION, IDINSTITUCION, ",
        "FECHABAJA)",
        "values (#{idformadorevento,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{idevento,jdbcType=DECIMAL}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{fechabaja,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT SEQ_AGEPERSONAEVENTO .NEXTVAL FROM DUAL", keyProperty="idformadorevento", before=true, resultType=Long.class)
    int insert(AgePersonaEvento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @InsertProvider(type=AgePersonaEventoSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SEQ_AGEPERSONAEVENTO .NEXTVAL FROM DUAL", keyProperty="idformadorevento", before=true, resultType=Long.class)
    int insertSelective(AgePersonaEvento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @SelectProvider(type=AgePersonaEventoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDFORMADOREVENTO", property="idformadorevento", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDEVENTO", property="idevento", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<AgePersonaEvento> selectByExample(AgePersonaEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @Select({
        "select",
        "IDFORMADOREVENTO, IDPERSONA, IDEVENTO, USUMODIFICACION, FECHAMODIFICACION, IDINSTITUCION, ",
        "FECHABAJA",
        "from AGE_PERSONA_EVENTO",
        "where IDFORMADOREVENTO = #{idformadorevento,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDFORMADOREVENTO", property="idformadorevento", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDEVENTO", property="idevento", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    AgePersonaEvento selectByPrimaryKey(Long idformadorevento);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @UpdateProvider(type=AgePersonaEventoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AgePersonaEvento record, @Param("example") AgePersonaEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @UpdateProvider(type=AgePersonaEventoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AgePersonaEvento record, @Param("example") AgePersonaEventoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @UpdateProvider(type=AgePersonaEventoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AgePersonaEvento record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_PERSONA_EVENTO
     *
     * @mbg.generated Tue Jan 22 10:19:37 CET 2019
     */
    @Update({
        "update AGE_PERSONA_EVENTO",
        "set IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
          "IDEVENTO = #{idevento,jdbcType=DECIMAL},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDFORMADOREVENTO = #{idformadorevento,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(AgePersonaEvento record);
}