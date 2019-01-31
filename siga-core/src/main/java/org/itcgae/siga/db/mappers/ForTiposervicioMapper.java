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
import org.itcgae.siga.db.entities.ForTiposervicio;
import org.itcgae.siga.db.entities.ForTiposervicioExample;

public interface ForTiposervicioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @SelectProvider(type=ForTiposervicioSqlProvider.class, method="countByExample")
    long countByExample(ForTiposervicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @DeleteProvider(type=ForTiposervicioSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForTiposervicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @Delete({
        "delete from FOR_TIPOSERVICIO",
        "where IDTIPOSERVICIO = #{idtiposervicio,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idtiposervicio);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @Insert({
        "insert into FOR_TIPOSERVICIO (IDTIPOSERVICIO, USUMODIFICACION, ",
        "DESCRIPCION, FECHAMODIFICACION, ",
        "IDINSTITUCION, CODIGOEXT, ",
        "FECHA_BAJA, BLOQUEADO)",
        "values (#{idtiposervicio,jdbcType=DECIMAL}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{idinstitucion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
        "#{fechaBaja,jdbcType=TIMESTAMP}, #{bloqueado,jdbcType=CHAR})"
    })
    int insert(ForTiposervicio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @InsertProvider(type=ForTiposervicioSqlProvider.class, method="insertSelective")
    int insertSelective(ForTiposervicio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @SelectProvider(type=ForTiposervicioSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDTIPOSERVICIO", property="idtiposervicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR)
    })
    List<ForTiposervicio> selectByExample(ForTiposervicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @Select({
        "select",
        "IDTIPOSERVICIO, USUMODIFICACION, DESCRIPCION, FECHAMODIFICACION, IDINSTITUCION, ",
        "CODIGOEXT, FECHA_BAJA, BLOQUEADO",
        "from FOR_TIPOSERVICIO",
        "where IDTIPOSERVICIO = #{idtiposervicio,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDTIPOSERVICIO", property="idtiposervicio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR)
    })
    ForTiposervicio selectByPrimaryKey(Long idtiposervicio);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @UpdateProvider(type=ForTiposervicioSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForTiposervicio record, @Param("example") ForTiposervicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @UpdateProvider(type=ForTiposervicioSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForTiposervicio record, @Param("example") ForTiposervicioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @UpdateProvider(type=ForTiposervicioSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForTiposervicio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_TIPOSERVICIO
     *
     * @mbg.generated Fri Dec 14 09:54:59 CET 2018
     */
    @Update({
        "update FOR_TIPOSERVICIO",
        "set USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP},",
          "BLOQUEADO = #{bloqueado,jdbcType=CHAR}",
        "where IDTIPOSERVICIO = #{idtiposervicio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ForTiposervicio record);
}