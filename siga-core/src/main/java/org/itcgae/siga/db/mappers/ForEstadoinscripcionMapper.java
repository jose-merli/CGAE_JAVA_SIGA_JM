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
import org.itcgae.siga.db.entities.ForEstadoinscripcion;
import org.itcgae.siga.db.entities.ForEstadoinscripcionExample;

public interface ForEstadoinscripcionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @SelectProvider(type=ForEstadoinscripcionSqlProvider.class, method="countByExample")
    long countByExample(ForEstadoinscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @DeleteProvider(type=ForEstadoinscripcionSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForEstadoinscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @Delete({
        "delete from FOR_ESTADOINSCRIPCION",
        "where IDESTADOINSCRIPCION = #{idestadoinscripcion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idestadoinscripcion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @Insert({
        "insert into FOR_ESTADOINSCRIPCION (IDESTADOINSCRIPCION, DESCRIPCION, ",
        "USUMODIFICACION, FECHAMODIFICACION, ",
        "FECHABAJA)",
        "values (#{idestadoinscripcion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{fechabaja,jdbcType=TIMESTAMP})"
    })
    int insert(ForEstadoinscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @InsertProvider(type=ForEstadoinscripcionSqlProvider.class, method="insertSelective")
    int insertSelective(ForEstadoinscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @SelectProvider(type=ForEstadoinscripcionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDESTADOINSCRIPCION", property="idestadoinscripcion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ForEstadoinscripcion> selectByExample(ForEstadoinscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @Select({
        "select",
        "IDESTADOINSCRIPCION, DESCRIPCION, USUMODIFICACION, FECHAMODIFICACION, FECHABAJA",
        "from FOR_ESTADOINSCRIPCION",
        "where IDESTADOINSCRIPCION = #{idestadoinscripcion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDESTADOINSCRIPCION", property="idestadoinscripcion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ForEstadoinscripcion selectByPrimaryKey(Long idestadoinscripcion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @UpdateProvider(type=ForEstadoinscripcionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForEstadoinscripcion record, @Param("example") ForEstadoinscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @UpdateProvider(type=ForEstadoinscripcionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForEstadoinscripcion record, @Param("example") ForEstadoinscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @UpdateProvider(type=ForEstadoinscripcionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForEstadoinscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_ESTADOINSCRIPCION
     *
     * @mbg.generated Wed Dec 05 11:53:15 CET 2018
     */
    @Update({
        "update FOR_ESTADOINSCRIPCION",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDESTADOINSCRIPCION = #{idestadoinscripcion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ForEstadoinscripcion record);
}