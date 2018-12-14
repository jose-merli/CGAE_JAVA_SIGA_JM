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
import org.itcgae.siga.db.entities.ForEventoCurso;
import org.itcgae.siga.db.entities.ForEventoCursoExample;
import org.itcgae.siga.db.entities.ForEventoCursoKey;

public interface ForEventoCursoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @SelectProvider(type=ForEventoCursoSqlProvider.class, method="countByExample")
    long countByExample(ForEventoCursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @DeleteProvider(type=ForEventoCursoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForEventoCursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @Delete({
        "delete from FOR_EVENTO_CURSO",
        "where IDCURSO = #{idcurso,jdbcType=DECIMAL}",
          "and IDEVENTO = #{idevento,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ForEventoCursoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @Insert({
        "insert into FOR_EVENTO_CURSO (IDCURSO, IDEVENTO, ",
        "USUMODIFICACION, FECHAMODIFICACION, ",
        "IDINSTITUCION, FECHABAJA)",
        "values (#{idcurso,jdbcType=DECIMAL}, #{idevento,jdbcType=DECIMAL}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{idinstitucion,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP})"
    })
    int insert(ForEventoCurso record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @InsertProvider(type=ForEventoCursoSqlProvider.class, method="insertSelective")
    int insertSelective(ForEventoCurso record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @SelectProvider(type=ForEventoCursoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDCURSO", property="idcurso", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDEVENTO", property="idevento", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ForEventoCurso> selectByExample(ForEventoCursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @Select({
        "select",
        "IDCURSO, IDEVENTO, USUMODIFICACION, FECHAMODIFICACION, IDINSTITUCION, FECHABAJA",
        "from FOR_EVENTO_CURSO",
        "where IDCURSO = #{idcurso,jdbcType=DECIMAL}",
          "and IDEVENTO = #{idevento,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDCURSO", property="idcurso", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDEVENTO", property="idevento", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ForEventoCurso selectByPrimaryKey(ForEventoCursoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @UpdateProvider(type=ForEventoCursoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForEventoCurso record, @Param("example") ForEventoCursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @UpdateProvider(type=ForEventoCursoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForEventoCurso record, @Param("example") ForEventoCursoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @UpdateProvider(type=ForEventoCursoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForEventoCurso record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_EVENTO_CURSO
     *
     * @mbg.generated Tue Dec 11 14:08:12 CET 2018
     */
    @Update({
        "update FOR_EVENTO_CURSO",
        "set USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDCURSO = #{idcurso,jdbcType=DECIMAL}",
          "and IDEVENTO = #{idevento,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ForEventoCurso record);
}