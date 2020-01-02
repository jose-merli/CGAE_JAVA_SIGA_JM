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
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsAsistenciaKey;

public interface ScsAsistenciaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @SelectProvider(type=ScsAsistenciaSqlProvider.class, method="countByExample")
    long countByExample(ScsAsistenciaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @DeleteProvider(type=ScsAsistenciaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsAsistenciaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @Delete({
        "delete from SCS_ASISTENCIA",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and ANIO = #{anio,jdbcType=DECIMAL}",
          "and NUMERO = #{numero,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsAsistenciaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @Insert({
        "insert into SCS_ASISTENCIA (IDINSTITUCION, ANIO, ",
        "NUMERO, FECHAHORA, ",
        "IDTIPOASISTENCIA, IDTURNO, ",
        "IDGUARDIA, FECHAMODIFICACION, ",
        "USUMODIFICACION, IDPERSONACOLEGIADO, ",
        "OBSERVACIONES, INCIDENCIAS, ",
        "FECHAANULACION, MOTIVOSANULACION, ",
        "DELITOSIMPUTADOS, CONTRARIOS, ",
        "DATOSDEFENSAJURIDICA, FECHACIERRE, ",
        "IDTIPOASISTENCIACOLEGIO, DESIGNA_ANIO, ",
        "DESIGNA_NUMERO, IDPERSONAJG, ",
        "FACTURADO, PAGADO, ",
        "IDFACTURACION, DESIGNA_TURNO, ",
        "NUMERODILIGENCIA, NUMEROPROCEDIMIENTO, ",
        "COMISARIA, COMISARIAIDINSTITUCION, ",
        "JUZGADO, JUZGADOIDINSTITUCION, ",
        "IDESTADOASISTENCIA, FECHAESTADOASISTENCIA, ",
        "EJGIDTIPOEJG, EJGANIO, ",
        "EJGNUMERO, CODIGO, ",
        "NIG, IDPRETENSION, ",
        "ASISTIDOAUTORIZAEEJG, IDTIPOASISTIDO, ",
        "IDORIGENASISTENCIA, ASISTIDOSOLICITAJG, ",
        "FECHASOLICITUD, IDSOLICITUDCENTRALITA, ",
        "IDMOVIMIENTO)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{anio,jdbcType=DECIMAL}, ",
        "#{numero,jdbcType=DECIMAL}, #{fechahora,jdbcType=TIMESTAMP}, ",
        "#{idtipoasistencia,jdbcType=DECIMAL}, #{idturno,jdbcType=DECIMAL}, ",
        "#{idguardia,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{idpersonacolegiado,jdbcType=DECIMAL}, ",
        "#{observaciones,jdbcType=VARCHAR}, #{incidencias,jdbcType=VARCHAR}, ",
        "#{fechaanulacion,jdbcType=TIMESTAMP}, #{motivosanulacion,jdbcType=VARCHAR}, ",
        "#{delitosimputados,jdbcType=VARCHAR}, #{contrarios,jdbcType=VARCHAR}, ",
        "#{datosdefensajuridica,jdbcType=VARCHAR}, #{fechacierre,jdbcType=TIMESTAMP}, ",
        "#{idtipoasistenciacolegio,jdbcType=DECIMAL}, #{designaAnio,jdbcType=DECIMAL}, ",
        "#{designaNumero,jdbcType=DECIMAL}, #{idpersonajg,jdbcType=DECIMAL}, ",
        "#{facturado,jdbcType=VARCHAR}, #{pagado,jdbcType=VARCHAR}, ",
        "#{idfacturacion,jdbcType=DECIMAL}, #{designaTurno,jdbcType=DECIMAL}, ",
        "#{numerodiligencia,jdbcType=VARCHAR}, #{numeroprocedimiento,jdbcType=VARCHAR}, ",
        "#{comisaria,jdbcType=DECIMAL}, #{comisariaidinstitucion,jdbcType=DECIMAL}, ",
        "#{juzgado,jdbcType=DECIMAL}, #{juzgadoidinstitucion,jdbcType=DECIMAL}, ",
        "#{idestadoasistencia,jdbcType=DECIMAL}, #{fechaestadoasistencia,jdbcType=TIMESTAMP}, ",
        "#{ejgidtipoejg,jdbcType=DECIMAL}, #{ejganio,jdbcType=DECIMAL}, ",
        "#{ejgnumero,jdbcType=DECIMAL}, #{codigo,jdbcType=VARCHAR}, ",
        "#{nig,jdbcType=VARCHAR}, #{idpretension,jdbcType=DECIMAL}, ",
        "#{asistidoautorizaeejg,jdbcType=VARCHAR}, #{idtipoasistido,jdbcType=DECIMAL}, ",
        "#{idorigenasistencia,jdbcType=DECIMAL}, #{asistidosolicitajg,jdbcType=VARCHAR}, ",
        "#{fechasolicitud,jdbcType=TIMESTAMP}, #{idsolicitudcentralita,jdbcType=DECIMAL}, ",
        "#{idmovimiento,jdbcType=DECIMAL})"
    })
    int insert(ScsAsistencia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @InsertProvider(type=ScsAsistenciaSqlProvider.class, method="insertSelective")
    int insertSelective(ScsAsistencia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @SelectProvider(type=ScsAsistenciaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAHORA", property="fechahora", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONACOLEGIADO", property="idpersonacolegiado", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="INCIDENCIAS", property="incidencias", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAANULACION", property="fechaanulacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="MOTIVOSANULACION", property="motivosanulacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="DELITOSIMPUTADOS", property="delitosimputados", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONTRARIOS", property="contrarios", jdbcType=JdbcType.VARCHAR),
        @Result(column="DATOSDEFENSAJURIDICA", property="datosdefensajuridica", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHACIERRE", property="fechacierre", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTIPOASISTENCIACOLEGIO", property="idtipoasistenciacolegio", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESIGNA_ANIO", property="designaAnio", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESIGNA_NUMERO", property="designaNumero", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONAJG", property="idpersonajg", jdbcType=JdbcType.DECIMAL),
        @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESIGNA_TURNO", property="designaTurno", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMERODILIGENCIA", property="numerodiligencia", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROPROCEDIMIENTO", property="numeroprocedimiento", jdbcType=JdbcType.VARCHAR),
        @Result(column="COMISARIA", property="comisaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="COMISARIAIDINSTITUCION", property="comisariaidinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="JUZGADO", property="juzgado", jdbcType=JdbcType.DECIMAL),
        @Result(column="JUZGADOIDINSTITUCION", property="juzgadoidinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDESTADOASISTENCIA", property="idestadoasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAESTADOASISTENCIA", property="fechaestadoasistencia", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="EJGIDTIPOEJG", property="ejgidtipoejg", jdbcType=JdbcType.DECIMAL),
        @Result(column="EJGANIO", property="ejganio", jdbcType=JdbcType.DECIMAL),
        @Result(column="EJGNUMERO", property="ejgnumero", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGO", property="codigo", jdbcType=JdbcType.VARCHAR),
        @Result(column="NIG", property="nig", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPRETENSION", property="idpretension", jdbcType=JdbcType.DECIMAL),
        @Result(column="ASISTIDOAUTORIZAEEJG", property="asistidoautorizaeejg", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOASISTIDO", property="idtipoasistido", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDORIGENASISTENCIA", property="idorigenasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="ASISTIDOSOLICITAJG", property="asistidosolicitajg", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDSOLICITUDCENTRALITA", property="idsolicitudcentralita", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMOVIMIENTO", property="idmovimiento", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsAsistencia> selectByExample(ScsAsistenciaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, ANIO, NUMERO, FECHAHORA, IDTIPOASISTENCIA, IDTURNO, IDGUARDIA, ",
        "FECHAMODIFICACION, USUMODIFICACION, IDPERSONACOLEGIADO, OBSERVACIONES, INCIDENCIAS, ",
        "FECHAANULACION, MOTIVOSANULACION, DELITOSIMPUTADOS, CONTRARIOS, DATOSDEFENSAJURIDICA, ",
        "FECHACIERRE, IDTIPOASISTENCIACOLEGIO, DESIGNA_ANIO, DESIGNA_NUMERO, IDPERSONAJG, ",
        "FACTURADO, PAGADO, IDFACTURACION, DESIGNA_TURNO, NUMERODILIGENCIA, NUMEROPROCEDIMIENTO, ",
        "COMISARIA, COMISARIAIDINSTITUCION, JUZGADO, JUZGADOIDINSTITUCION, IDESTADOASISTENCIA, ",
        "FECHAESTADOASISTENCIA, EJGIDTIPOEJG, EJGANIO, EJGNUMERO, CODIGO, NIG, IDPRETENSION, ",
        "ASISTIDOAUTORIZAEEJG, IDTIPOASISTIDO, IDORIGENASISTENCIA, ASISTIDOSOLICITAJG, ",
        "FECHASOLICITUD, IDSOLICITUDCENTRALITA, IDMOVIMIENTO",
        "from SCS_ASISTENCIA",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and ANIO = #{anio,jdbcType=DECIMAL}",
          "and NUMERO = #{numero,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAHORA", property="fechahora", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONACOLEGIADO", property="idpersonacolegiado", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="INCIDENCIAS", property="incidencias", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAANULACION", property="fechaanulacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="MOTIVOSANULACION", property="motivosanulacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="DELITOSIMPUTADOS", property="delitosimputados", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONTRARIOS", property="contrarios", jdbcType=JdbcType.VARCHAR),
        @Result(column="DATOSDEFENSAJURIDICA", property="datosdefensajuridica", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHACIERRE", property="fechacierre", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTIPOASISTENCIACOLEGIO", property="idtipoasistenciacolegio", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESIGNA_ANIO", property="designaAnio", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESIGNA_NUMERO", property="designaNumero", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONAJG", property="idpersonajg", jdbcType=JdbcType.DECIMAL),
        @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESIGNA_TURNO", property="designaTurno", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMERODILIGENCIA", property="numerodiligencia", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROPROCEDIMIENTO", property="numeroprocedimiento", jdbcType=JdbcType.VARCHAR),
        @Result(column="COMISARIA", property="comisaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="COMISARIAIDINSTITUCION", property="comisariaidinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="JUZGADO", property="juzgado", jdbcType=JdbcType.DECIMAL),
        @Result(column="JUZGADOIDINSTITUCION", property="juzgadoidinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDESTADOASISTENCIA", property="idestadoasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAESTADOASISTENCIA", property="fechaestadoasistencia", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="EJGIDTIPOEJG", property="ejgidtipoejg", jdbcType=JdbcType.DECIMAL),
        @Result(column="EJGANIO", property="ejganio", jdbcType=JdbcType.DECIMAL),
        @Result(column="EJGNUMERO", property="ejgnumero", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGO", property="codigo", jdbcType=JdbcType.VARCHAR),
        @Result(column="NIG", property="nig", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPRETENSION", property="idpretension", jdbcType=JdbcType.DECIMAL),
        @Result(column="ASISTIDOAUTORIZAEEJG", property="asistidoautorizaeejg", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOASISTIDO", property="idtipoasistido", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDORIGENASISTENCIA", property="idorigenasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="ASISTIDOSOLICITAJG", property="asistidosolicitajg", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDSOLICITUDCENTRALITA", property="idsolicitudcentralita", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMOVIMIENTO", property="idmovimiento", jdbcType=JdbcType.DECIMAL)
    })
    ScsAsistencia selectByPrimaryKey(ScsAsistenciaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @UpdateProvider(type=ScsAsistenciaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsAsistencia record, @Param("example") ScsAsistenciaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @UpdateProvider(type=ScsAsistenciaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsAsistencia record, @Param("example") ScsAsistenciaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @UpdateProvider(type=ScsAsistenciaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsAsistencia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ASISTENCIA
     *
     * @mbg.generated Fri Nov 29 11:58:24 CET 2019
     */
    @Update({
        "update SCS_ASISTENCIA",
        "set FECHAHORA = #{fechahora,jdbcType=TIMESTAMP},",
          "IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL},",
          "IDTURNO = #{idturno,jdbcType=DECIMAL},",
          "IDGUARDIA = #{idguardia,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDPERSONACOLEGIADO = #{idpersonacolegiado,jdbcType=DECIMAL},",
          "OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
          "INCIDENCIAS = #{incidencias,jdbcType=VARCHAR},",
          "FECHAANULACION = #{fechaanulacion,jdbcType=TIMESTAMP},",
          "MOTIVOSANULACION = #{motivosanulacion,jdbcType=VARCHAR},",
          "DELITOSIMPUTADOS = #{delitosimputados,jdbcType=VARCHAR},",
          "CONTRARIOS = #{contrarios,jdbcType=VARCHAR},",
          "DATOSDEFENSAJURIDICA = #{datosdefensajuridica,jdbcType=VARCHAR},",
          "FECHACIERRE = #{fechacierre,jdbcType=TIMESTAMP},",
          "IDTIPOASISTENCIACOLEGIO = #{idtipoasistenciacolegio,jdbcType=DECIMAL},",
          "DESIGNA_ANIO = #{designaAnio,jdbcType=DECIMAL},",
          "DESIGNA_NUMERO = #{designaNumero,jdbcType=DECIMAL},",
          "IDPERSONAJG = #{idpersonajg,jdbcType=DECIMAL},",
          "FACTURADO = #{facturado,jdbcType=VARCHAR},",
          "PAGADO = #{pagado,jdbcType=VARCHAR},",
          "IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL},",
          "DESIGNA_TURNO = #{designaTurno,jdbcType=DECIMAL},",
          "NUMERODILIGENCIA = #{numerodiligencia,jdbcType=VARCHAR},",
          "NUMEROPROCEDIMIENTO = #{numeroprocedimiento,jdbcType=VARCHAR},",
          "COMISARIA = #{comisaria,jdbcType=DECIMAL},",
          "COMISARIAIDINSTITUCION = #{comisariaidinstitucion,jdbcType=DECIMAL},",
          "JUZGADO = #{juzgado,jdbcType=DECIMAL},",
          "JUZGADOIDINSTITUCION = #{juzgadoidinstitucion,jdbcType=DECIMAL},",
          "IDESTADOASISTENCIA = #{idestadoasistencia,jdbcType=DECIMAL},",
          "FECHAESTADOASISTENCIA = #{fechaestadoasistencia,jdbcType=TIMESTAMP},",
          "EJGIDTIPOEJG = #{ejgidtipoejg,jdbcType=DECIMAL},",
          "EJGANIO = #{ejganio,jdbcType=DECIMAL},",
          "EJGNUMERO = #{ejgnumero,jdbcType=DECIMAL},",
          "CODIGO = #{codigo,jdbcType=VARCHAR},",
          "NIG = #{nig,jdbcType=VARCHAR},",
          "IDPRETENSION = #{idpretension,jdbcType=DECIMAL},",
          "ASISTIDOAUTORIZAEEJG = #{asistidoautorizaeejg,jdbcType=VARCHAR},",
          "IDTIPOASISTIDO = #{idtipoasistido,jdbcType=DECIMAL},",
          "IDORIGENASISTENCIA = #{idorigenasistencia,jdbcType=DECIMAL},",
          "ASISTIDOSOLICITAJG = #{asistidosolicitajg,jdbcType=VARCHAR},",
          "FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP},",
          "IDSOLICITUDCENTRALITA = #{idsolicitudcentralita,jdbcType=DECIMAL},",
          "IDMOVIMIENTO = #{idmovimiento,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and ANIO = #{anio,jdbcType=DECIMAL}",
          "and NUMERO = #{numero,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsAsistencia record);
}