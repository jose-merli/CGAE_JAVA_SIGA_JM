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
import org.itcgae.siga.db.entities.ForEstadocurso;
import org.itcgae.siga.db.entities.ForEstadocursoExample;

public interface ForEstadocursoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @SelectProvider(type=ForEstadocursoSqlProvider.class, method="countByExample")
    long countByExample(ForEstadocursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @DeleteProvider(type=ForEstadocursoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForEstadocursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @Delete({
        "delete from FOR_ESTADOCURSO",
        "where IDESTADOCURSO = #{idestadocurso,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idestadocurso);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @Insert({
        "insert into FOR_ESTADOCURSO (IDESTADOCURSO, DESCRIPCION, ",
        "USUMODIFICACION, FECHABAJA, ",
        "FECHAMODIFICACION)",
        "values (#{idestadocurso,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP})"
    })
    int insert(ForEstadocurso record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @InsertProvider(type=ForEstadocursoSqlProvider.class, method="insertSelective")
    int insertSelective(ForEstadocurso record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @SelectProvider(type=ForEstadocursoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDESTADOCURSO", property="idestadocurso", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ForEstadocurso> selectByExample(ForEstadocursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @Select({
        "select",
        "IDESTADOCURSO, DESCRIPCION, USUMODIFICACION, FECHABAJA, FECHAMODIFICACION",
        "from FOR_ESTADOCURSO",
        "where IDESTADOCURSO = #{idestadocurso,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDESTADOCURSO", property="idestadocurso", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP)
    })
    ForEstadocurso selectByPrimaryKey(Short idestadocurso);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @UpdateProvider(type=ForEstadocursoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForEstadocurso record, @Param("example") ForEstadocursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @UpdateProvider(type=ForEstadocursoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForEstadocurso record, @Param("example") ForEstadocursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @UpdateProvider(type=ForEstadocursoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForEstadocurso record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOCURSO
     *
     * @mbg.generated Mon Oct 15 10:00:47 CEST 2018
     */
    @Update({
        "update FOR_ESTADOCURSO",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
        "where IDESTADOCURSO = #{idestadocurso,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ForEstadocurso record);
}