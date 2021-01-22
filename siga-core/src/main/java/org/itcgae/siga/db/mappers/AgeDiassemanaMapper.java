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
import org.itcgae.siga.db.entities.AgeDiassemana;
import org.itcgae.siga.db.entities.AgeDiassemanaExample;

public interface AgeDiassemanaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @SelectProvider(type=AgeDiassemanaSqlProvider.class, method="countByExample")
    long countByExample(AgeDiassemanaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @DeleteProvider(type=AgeDiassemanaSqlProvider.class, method="deleteByExample")
    int deleteByExample(AgeDiassemanaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @Delete({
        "delete from AGE_DIASSEMANA",
        "where IDDIASSEMANA = #{iddiassemana,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long iddiassemana);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @Insert({
        "insert into AGE_DIASSEMANA (IDDIASSEMANA, DESCRIPCION, ",
        "USUMODIFICACION, FECHAMODIFICACION, ",
        "FECHABAJA)",
        "values (#{iddiassemana,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{fechabaja,jdbcType=TIMESTAMP})"
    })
    int insert(AgeDiassemana record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @InsertProvider(type=AgeDiassemanaSqlProvider.class, method="insertSelective")
    int insertSelective(AgeDiassemana record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @SelectProvider(type=AgeDiassemanaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDDIASSEMANA", property="iddiassemana", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<AgeDiassemana> selectByExample(AgeDiassemanaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @Select({
        "select",
        "IDDIASSEMANA, DESCRIPCION, USUMODIFICACION, FECHAMODIFICACION, FECHABAJA",
        "from AGE_DIASSEMANA",
        "where IDDIASSEMANA = #{iddiassemana,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDDIASSEMANA", property="iddiassemana", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    AgeDiassemana selectByPrimaryKey(Long iddiassemana);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @UpdateProvider(type=AgeDiassemanaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AgeDiassemana record, @Param("example") AgeDiassemanaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @UpdateProvider(type=AgeDiassemanaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AgeDiassemana record, @Param("example") AgeDiassemanaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @UpdateProvider(type=AgeDiassemanaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AgeDiassemana record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    @Update({
        "update AGE_DIASSEMANA",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDDIASSEMANA = #{iddiassemana,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(AgeDiassemana record);
}