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
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsTurnoKey;

public interface ScsTurnoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @SelectProvider(type=ScsTurnoSqlProvider.class, method="countByExample")
    long countByExample(ScsTurnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @DeleteProvider(type=ScsTurnoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsTurnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @Delete({
        "delete from SCS_TURNO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsTurnoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @Insert({
        "insert into SCS_TURNO (IDINSTITUCION, IDTURNO, ",
        "NOMBRE, ABREVIATURA, ",
        "GUARDIAS, VALIDARJUSTIFICACIONES, ",
        "DESIGNADIRECTA, VALIDARINSCRIPCIONES, ",
        "IDAREA, IDMATERIA, ",
        "IDZONA, IDORDENACIONCOLAS, ",
        "IDGRUPOFACTURACION, FECHAMODIFICACION, ",
        "USUMODIFICACION, IDSUBZONA, ",
        "IDPARTIDAPRESUPUESTARIA, REQUISITOS, ",
        "IDPERSONA_ULTIMO, DESCRIPCION, ",
        "ACTIVARRETRICCIONACREDIT, LETRADOASISTENCIAS, ",
        "LETRADOACTUACIONES, CODIGOEXT, ",
        "FECHASOLICITUD_ULTIMO, VISIBILIDAD, ",
        "IDTIPOTURNO, VISIBLEMOVIL, ",
        "IDJURISDICCION)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idturno,jdbcType=DECIMAL}, ",
        "#{nombre,jdbcType=VARCHAR}, #{abreviatura,jdbcType=VARCHAR}, ",
        "#{guardias,jdbcType=DECIMAL}, #{validarjustificaciones,jdbcType=VARCHAR}, ",
        "#{designadirecta,jdbcType=VARCHAR}, #{validarinscripciones,jdbcType=VARCHAR}, ",
        "#{idarea,jdbcType=DECIMAL}, #{idmateria,jdbcType=DECIMAL}, ",
        "#{idzona,jdbcType=DECIMAL}, #{idordenacioncolas,jdbcType=DECIMAL}, ",
        "#{idgrupofacturacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{idsubzona,jdbcType=DECIMAL}, ",
        "#{idpartidapresupuestaria,jdbcType=DECIMAL}, #{requisitos,jdbcType=VARCHAR}, ",
        "#{idpersonaUltimo,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{activarretriccionacredit,jdbcType=VARCHAR}, #{letradoasistencias,jdbcType=VARCHAR}, ",
        "#{letradoactuaciones,jdbcType=VARCHAR}, #{codigoext,jdbcType=VARCHAR}, ",
        "#{fechasolicitudUltimo,jdbcType=TIMESTAMP}, #{visibilidad,jdbcType=VARCHAR}, ",
        "#{idtipoturno,jdbcType=DECIMAL}, #{visiblemovil,jdbcType=DECIMAL}, ",
        "#{idjurisdiccion,jdbcType=DECIMAL})"
    })
    int insert(ScsTurno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @InsertProvider(type=ScsTurnoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsTurno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @SelectProvider(type=ScsTurnoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="ABREVIATURA", property="abreviatura", jdbcType=JdbcType.VARCHAR),
        @Result(column="GUARDIAS", property="guardias", jdbcType=JdbcType.DECIMAL),
        @Result(column="VALIDARJUSTIFICACIONES", property="validarjustificaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESIGNADIRECTA", property="designadirecta", jdbcType=JdbcType.VARCHAR),
        @Result(column="VALIDARINSCRIPCIONES", property="validarinscripciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDAREA", property="idarea", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMATERIA", property="idmateria", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDZONA", property="idzona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDORDENACIONCOLAS", property="idordenacioncolas", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGRUPOFACTURACION", property="idgrupofacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSUBZONA", property="idsubzona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARTIDAPRESUPUESTARIA", property="idpartidapresupuestaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="REQUISITOS", property="requisitos", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERSONA_ULTIMO", property="idpersonaUltimo", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVARRETRICCIONACREDIT", property="activarretriccionacredit", jdbcType=JdbcType.VARCHAR),
        @Result(column="LETRADOASISTENCIAS", property="letradoasistencias", jdbcType=JdbcType.VARCHAR),
        @Result(column="LETRADOACTUACIONES", property="letradoactuaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHASOLICITUD_ULTIMO", property="fechasolicitudUltimo", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="VISIBILIDAD", property="visibilidad", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOTURNO", property="idtipoturno", jdbcType=JdbcType.DECIMAL),
        @Result(column="VISIBLEMOVIL", property="visiblemovil", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDJURISDICCION", property="idjurisdiccion", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsTurno> selectByExample(ScsTurnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTURNO, NOMBRE, ABREVIATURA, GUARDIAS, VALIDARJUSTIFICACIONES, ",
        "DESIGNADIRECTA, VALIDARINSCRIPCIONES, IDAREA, IDMATERIA, IDZONA, IDORDENACIONCOLAS, ",
        "IDGRUPOFACTURACION, FECHAMODIFICACION, USUMODIFICACION, IDSUBZONA, IDPARTIDAPRESUPUESTARIA, ",
        "REQUISITOS, IDPERSONA_ULTIMO, DESCRIPCION, ACTIVARRETRICCIONACREDIT, LETRADOASISTENCIAS, ",
        "LETRADOACTUACIONES, CODIGOEXT, FECHASOLICITUD_ULTIMO, VISIBILIDAD, IDTIPOTURNO, ",
        "VISIBLEMOVIL, IDJURISDICCION",
        "from SCS_TURNO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="ABREVIATURA", property="abreviatura", jdbcType=JdbcType.VARCHAR),
        @Result(column="GUARDIAS", property="guardias", jdbcType=JdbcType.DECIMAL),
        @Result(column="VALIDARJUSTIFICACIONES", property="validarjustificaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESIGNADIRECTA", property="designadirecta", jdbcType=JdbcType.VARCHAR),
        @Result(column="VALIDARINSCRIPCIONES", property="validarinscripciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDAREA", property="idarea", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMATERIA", property="idmateria", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDZONA", property="idzona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDORDENACIONCOLAS", property="idordenacioncolas", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGRUPOFACTURACION", property="idgrupofacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDSUBZONA", property="idsubzona", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARTIDAPRESUPUESTARIA", property="idpartidapresupuestaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="REQUISITOS", property="requisitos", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERSONA_ULTIMO", property="idpersonaUltimo", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACTIVARRETRICCIONACREDIT", property="activarretriccionacredit", jdbcType=JdbcType.VARCHAR),
        @Result(column="LETRADOASISTENCIAS", property="letradoasistencias", jdbcType=JdbcType.VARCHAR),
        @Result(column="LETRADOACTUACIONES", property="letradoactuaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHASOLICITUD_ULTIMO", property="fechasolicitudUltimo", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="VISIBILIDAD", property="visibilidad", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOTURNO", property="idtipoturno", jdbcType=JdbcType.DECIMAL),
        @Result(column="VISIBLEMOVIL", property="visiblemovil", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDJURISDICCION", property="idjurisdiccion", jdbcType=JdbcType.DECIMAL)
    })
    ScsTurno selectByPrimaryKey(ScsTurnoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @UpdateProvider(type=ScsTurnoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsTurno record, @Param("example") ScsTurnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @UpdateProvider(type=ScsTurnoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsTurno record, @Param("example") ScsTurnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @UpdateProvider(type=ScsTurnoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsTurno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TURNO
     *
     * @mbg.generated Mon Sep 16 15:06:54 CEST 2019
     */
    @Update({
        "update SCS_TURNO",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "ABREVIATURA = #{abreviatura,jdbcType=VARCHAR},",
          "GUARDIAS = #{guardias,jdbcType=DECIMAL},",
          "VALIDARJUSTIFICACIONES = #{validarjustificaciones,jdbcType=VARCHAR},",
          "DESIGNADIRECTA = #{designadirecta,jdbcType=VARCHAR},",
          "VALIDARINSCRIPCIONES = #{validarinscripciones,jdbcType=VARCHAR},",
          "IDAREA = #{idarea,jdbcType=DECIMAL},",
          "IDMATERIA = #{idmateria,jdbcType=DECIMAL},",
          "IDZONA = #{idzona,jdbcType=DECIMAL},",
          "IDORDENACIONCOLAS = #{idordenacioncolas,jdbcType=DECIMAL},",
          "IDGRUPOFACTURACION = #{idgrupofacturacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDSUBZONA = #{idsubzona,jdbcType=DECIMAL},",
          "IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL},",
          "REQUISITOS = #{requisitos,jdbcType=VARCHAR},",
          "IDPERSONA_ULTIMO = #{idpersonaUltimo,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "ACTIVARRETRICCIONACREDIT = #{activarretriccionacredit,jdbcType=VARCHAR},",
          "LETRADOASISTENCIAS = #{letradoasistencias,jdbcType=VARCHAR},",
          "LETRADOACTUACIONES = #{letradoactuaciones,jdbcType=VARCHAR},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "FECHASOLICITUD_ULTIMO = #{fechasolicitudUltimo,jdbcType=TIMESTAMP},",
          "VISIBILIDAD = #{visibilidad,jdbcType=VARCHAR},",
          "IDTIPOTURNO = #{idtipoturno,jdbcType=DECIMAL},",
          "VISIBLEMOVIL = #{visiblemovil,jdbcType=DECIMAL},",
          "IDJURISDICCION = #{idjurisdiccion,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsTurno record);
}