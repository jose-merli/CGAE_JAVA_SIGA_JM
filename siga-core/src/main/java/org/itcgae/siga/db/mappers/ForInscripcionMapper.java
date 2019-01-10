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
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;

public interface ForInscripcionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @SelectProvider(type=ForInscripcionSqlProvider.class, method="countByExample")
    long countByExample(ForInscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @DeleteProvider(type=ForInscripcionSqlProvider.class, method="deleteByExample")
    int deleteByExample(ForInscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @Delete({
        "delete from FOR_INSCRIPCION",
        "where IDINSCRIPCION = #{idinscripcion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idinscripcion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @Insert({
        "insert into FOR_INSCRIPCION (IDINSCRIPCION, IDINSTITUCION, ",
        "IDESTADOINSCRIPCION, FECHASOLICITUD, ",
        "CALIFICACION, IDPERSONA, ",
        "IDCURSO, IDCALIFICACION, ",
        "USUMODIFICACION, FECHAMODIFICACION, ",
        "FECHABAJA, PAGADA, ",
        "IDCARGAINSCRIPCION, EMITIRCERTIFICADO, ",
        "CERTIFICADOEMITIDO)",
        "values (#{idinscripcion,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idestadoinscripcion,jdbcType=DECIMAL}, #{fechasolicitud,jdbcType=TIMESTAMP}, ",
        "#{calificacion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{idcurso,jdbcType=DECIMAL}, #{idcalificacion,jdbcType=DECIMAL}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{fechabaja,jdbcType=TIMESTAMP}, #{pagada,jdbcType=DECIMAL}, ",
        "#{idcargainscripcion,jdbcType=DECIMAL}, #{emitircertificado,jdbcType=DECIMAL}, ",
        "#{certificadoemitido,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT SEQ_FORINSCRIPCION.NEXTVAL FROM DUAL", keyProperty="idinscripcion", before=true, resultType=Long.class)
    int insert(ForInscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @InsertProvider(type=ForInscripcionSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SEQ_FORINSCRIPCION.NEXTVAL FROM DUAL", keyProperty="idinscripcion", before=true, resultType=Long.class)
    int insertSelective(ForInscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @SelectProvider(type=ForInscripcionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSCRIPCION", property="idinscripcion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDESTADOINSCRIPCION", property="idestadoinscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CALIFICACION", property="calificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCURSO", property="idcurso", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCALIFICACION", property="idcalificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PAGADA", property="pagada", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCARGAINSCRIPCION", property="idcargainscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="EMITIRCERTIFICADO", property="emitircertificado", jdbcType=JdbcType.DECIMAL),
        @Result(column="CERTIFICADOEMITIDO", property="certificadoemitido", jdbcType=JdbcType.DECIMAL)
    })
    List<ForInscripcion> selectByExample(ForInscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @Select({
        "select",
        "IDINSCRIPCION, IDINSTITUCION, IDESTADOINSCRIPCION, FECHASOLICITUD, CALIFICACION, ",
        "IDPERSONA, IDCURSO, IDCALIFICACION, USUMODIFICACION, FECHAMODIFICACION, FECHABAJA, ",
        "PAGADA, IDCARGAINSCRIPCION, EMITIRCERTIFICADO, CERTIFICADOEMITIDO",
        "from FOR_INSCRIPCION",
        "where IDINSCRIPCION = #{idinscripcion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSCRIPCION", property="idinscripcion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDESTADOINSCRIPCION", property="idestadoinscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CALIFICACION", property="calificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCURSO", property="idcurso", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCALIFICACION", property="idcalificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PAGADA", property="pagada", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCARGAINSCRIPCION", property="idcargainscripcion", jdbcType=JdbcType.DECIMAL),
        @Result(column="EMITIRCERTIFICADO", property="emitircertificado", jdbcType=JdbcType.DECIMAL),
        @Result(column="CERTIFICADOEMITIDO", property="certificadoemitido", jdbcType=JdbcType.DECIMAL)
    })
    ForInscripcion selectByPrimaryKey(Long idinscripcion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @UpdateProvider(type=ForInscripcionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ForInscripcion record, @Param("example") ForInscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @UpdateProvider(type=ForInscripcionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ForInscripcion record, @Param("example") ForInscripcionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @UpdateProvider(type=ForInscripcionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForInscripcion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_INSCRIPCION
     *
     * @mbg.generated Wed Jan 09 16:10:33 CET 2019
     */
    @Update({
        "update FOR_INSCRIPCION",
        "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "IDESTADOINSCRIPCION = #{idestadoinscripcion,jdbcType=DECIMAL},",
          "FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP},",
          "CALIFICACION = #{calificacion,jdbcType=DECIMAL},",
          "IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
          "IDCURSO = #{idcurso,jdbcType=DECIMAL},",
          "IDCALIFICACION = #{idcalificacion,jdbcType=DECIMAL},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "PAGADA = #{pagada,jdbcType=DECIMAL},",
          "IDCARGAINSCRIPCION = #{idcargainscripcion,jdbcType=DECIMAL},",
          "EMITIRCERTIFICADO = #{emitircertificado,jdbcType=DECIMAL},",
          "CERTIFICADOEMITIDO = #{certificadoemitido,jdbcType=DECIMAL}",
        "where IDINSCRIPCION = #{idinscripcion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ForInscripcion record);
}