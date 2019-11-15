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
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgExample;
import org.itcgae.siga.db.entities.ScsPersonajgKey;

public interface ScsPersonajgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @SelectProvider(type=ScsPersonajgSqlProvider.class, method="countByExample")
    long countByExample(ScsPersonajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @DeleteProvider(type=ScsPersonajgSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsPersonajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @Delete({
        "delete from SCS_PERSONAJG",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsPersonajgKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @Insert({
        "insert into SCS_PERSONAJG (IDINSTITUCION, IDPERSONA, ",
        "FECHANACIMIENTO, FECHAMODIFICACION, ",
        "USUMODIFICACION, IDPAIS, ",
        "NIF, NOMBRE, APELLIDO1, ",
        "APELLIDO2, DIRECCION, ",
        "CODIGOPOSTAL, IDPROFESION, ",
        "REGIMEN_CONYUGAL, IDPROVINCIA, ",
        "IDPOBLACION, IDESTADOCIVIL, ",
        "TIPOPERSONAJG, IDTIPOIDENTIFICACION, ",
        "OBSERVACIONES, IDREPRESENTANTEJG, ",
        "IDTIPOENCALIDAD, SEXO, ",
        "IDLENGUAJE, NUMEROHIJOS, ",
        "FAX, CORREOELECTRONICO, ",
        "EDAD, IDMINUSVALIA, ",
        "EXISTEDOMICILIO, IDPROVINCIA2, ",
        "IDPOBLACION2, DIRECCION2, ",
        "CODIGOPOSTAL2, IDTIPODIR, ",
        "IDTIPODIR2, CNAE, ",
        "IDTIPOVIA, NUMERODIR, ",
        "ESCALERADIR, PISODIR, ",
        "PUERTADIR, IDTIPOVIA2, ",
        "NUMERODIR2, ESCALERADIR2, ",
        "PISODIR2, PUERTADIR2, ",
        "IDPAISDIR1, IDPAISDIR2, ",
        "DESCPAISDIR1, DESCPAISDIR2, ",
        "IDTIPOIDENTIFICACIONOTROS, ASISTIDOSOLICITAJG, ",
        "ASISTIDOAUTORIZAEEJG, AUTORIZAAVISOTELEMATICO)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
        "#{fechanacimiento,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{idpais,jdbcType=VARCHAR}, ",
        "#{nif,jdbcType=VARCHAR}, #{nombre,jdbcType=VARCHAR}, #{apellido1,jdbcType=VARCHAR}, ",
        "#{apellido2,jdbcType=VARCHAR}, #{direccion,jdbcType=VARCHAR}, ",
        "#{codigopostal,jdbcType=VARCHAR}, #{idprofesion,jdbcType=DECIMAL}, ",
        "#{regimenConyugal,jdbcType=VARCHAR}, #{idprovincia,jdbcType=VARCHAR}, ",
        "#{idpoblacion,jdbcType=VARCHAR}, #{idestadocivil,jdbcType=DECIMAL}, ",
        "#{tipopersonajg,jdbcType=VARCHAR}, #{idtipoidentificacion,jdbcType=DECIMAL}, ",
        "#{observaciones,jdbcType=VARCHAR}, #{idrepresentantejg,jdbcType=DECIMAL}, ",
        "#{idtipoencalidad,jdbcType=DECIMAL}, #{sexo,jdbcType=VARCHAR}, ",
        "#{idlenguaje,jdbcType=VARCHAR}, #{numerohijos,jdbcType=DECIMAL}, ",
        "#{fax,jdbcType=VARCHAR}, #{correoelectronico,jdbcType=VARCHAR}, ",
        "#{edad,jdbcType=DECIMAL}, #{idminusvalia,jdbcType=DECIMAL}, ",
        "#{existedomicilio,jdbcType=VARCHAR}, #{idprovincia2,jdbcType=VARCHAR}, ",
        "#{idpoblacion2,jdbcType=VARCHAR}, #{direccion2,jdbcType=VARCHAR}, ",
        "#{codigopostal2,jdbcType=VARCHAR}, #{idtipodir,jdbcType=VARCHAR}, ",
        "#{idtipodir2,jdbcType=VARCHAR}, #{cnae,jdbcType=VARCHAR}, ",
        "#{idtipovia,jdbcType=VARCHAR}, #{numerodir,jdbcType=VARCHAR}, ",
        "#{escaleradir,jdbcType=VARCHAR}, #{pisodir,jdbcType=VARCHAR}, ",
        "#{puertadir,jdbcType=VARCHAR}, #{idtipovia2,jdbcType=VARCHAR}, ",
        "#{numerodir2,jdbcType=VARCHAR}, #{escaleradir2,jdbcType=VARCHAR}, ",
        "#{pisodir2,jdbcType=VARCHAR}, #{puertadir2,jdbcType=VARCHAR}, ",
        "#{idpaisdir1,jdbcType=VARCHAR}, #{idpaisdir2,jdbcType=VARCHAR}, ",
        "#{descpaisdir1,jdbcType=VARCHAR}, #{descpaisdir2,jdbcType=VARCHAR}, ",
        "#{idtipoidentificacionotros,jdbcType=VARCHAR}, #{asistidosolicitajg,jdbcType=VARCHAR}, ",
        "#{asistidoautorizaeejg,jdbcType=VARCHAR}, #{autorizaavisotelematico,jdbcType=VARCHAR})"
    })
    int insert(ScsPersonajg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @InsertProvider(type=ScsPersonajgSqlProvider.class, method="insertSelective")
    int insertSelective(ScsPersonajg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @SelectProvider(type=ScsPersonajgSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHANACIMIENTO", property="fechanacimiento", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPAIS", property="idpais", jdbcType=JdbcType.VARCHAR),
        @Result(column="NIF", property="nif", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO1", property="apellido1", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO2", property="apellido2", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIRECCION", property="direccion", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPOSTAL", property="codigopostal", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROFESION", property="idprofesion", jdbcType=JdbcType.DECIMAL),
        @Result(column="REGIMEN_CONYUGAL", property="regimenConyugal", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION", property="idpoblacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDESTADOCIVIL", property="idestadocivil", jdbcType=JdbcType.DECIMAL),
        @Result(column="TIPOPERSONAJG", property="tipopersonajg", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOIDENTIFICACION", property="idtipoidentificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDREPRESENTANTEJG", property="idrepresentantejg", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOENCALIDAD", property="idtipoencalidad", jdbcType=JdbcType.DECIMAL),
        @Result(column="SEXO", property="sexo", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDLENGUAJE", property="idlenguaje", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROHIJOS", property="numerohijos", jdbcType=JdbcType.DECIMAL),
        @Result(column="FAX", property="fax", jdbcType=JdbcType.VARCHAR),
        @Result(column="CORREOELECTRONICO", property="correoelectronico", jdbcType=JdbcType.VARCHAR),
        @Result(column="EDAD", property="edad", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMINUSVALIA", property="idminusvalia", jdbcType=JdbcType.DECIMAL),
        @Result(column="EXISTEDOMICILIO", property="existedomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA2", property="idprovincia2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION2", property="idpoblacion2", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIRECCION2", property="direccion2", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPOSTAL2", property="codigopostal2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPODIR", property="idtipodir", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPODIR2", property="idtipodir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="CNAE", property="cnae", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOVIA", property="idtipovia", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMERODIR", property="numerodir", jdbcType=JdbcType.VARCHAR),
        @Result(column="ESCALERADIR", property="escaleradir", jdbcType=JdbcType.VARCHAR),
        @Result(column="PISODIR", property="pisodir", jdbcType=JdbcType.VARCHAR),
        @Result(column="PUERTADIR", property="puertadir", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOVIA2", property="idtipovia2", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMERODIR2", property="numerodir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="ESCALERADIR2", property="escaleradir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="PISODIR2", property="pisodir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="PUERTADIR2", property="puertadir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPAISDIR1", property="idpaisdir1", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPAISDIR2", property="idpaisdir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCPAISDIR1", property="descpaisdir1", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCPAISDIR2", property="descpaisdir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOIDENTIFICACIONOTROS", property="idtipoidentificacionotros", jdbcType=JdbcType.VARCHAR),
        @Result(column="ASISTIDOSOLICITAJG", property="asistidosolicitajg", jdbcType=JdbcType.VARCHAR),
        @Result(column="ASISTIDOAUTORIZAEEJG", property="asistidoautorizaeejg", jdbcType=JdbcType.VARCHAR),
        @Result(column="AUTORIZAAVISOTELEMATICO", property="autorizaavisotelematico", jdbcType=JdbcType.VARCHAR)
    })
    List<ScsPersonajg> selectByExample(ScsPersonajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDPERSONA, FECHANACIMIENTO, FECHAMODIFICACION, USUMODIFICACION, ",
        "IDPAIS, NIF, NOMBRE, APELLIDO1, APELLIDO2, DIRECCION, CODIGOPOSTAL, IDPROFESION, ",
        "REGIMEN_CONYUGAL, IDPROVINCIA, IDPOBLACION, IDESTADOCIVIL, TIPOPERSONAJG, IDTIPOIDENTIFICACION, ",
        "OBSERVACIONES, IDREPRESENTANTEJG, IDTIPOENCALIDAD, SEXO, IDLENGUAJE, NUMEROHIJOS, ",
        "FAX, CORREOELECTRONICO, EDAD, IDMINUSVALIA, EXISTEDOMICILIO, IDPROVINCIA2, IDPOBLACION2, ",
        "DIRECCION2, CODIGOPOSTAL2, IDTIPODIR, IDTIPODIR2, CNAE, IDTIPOVIA, NUMERODIR, ",
        "ESCALERADIR, PISODIR, PUERTADIR, IDTIPOVIA2, NUMERODIR2, ESCALERADIR2, PISODIR2, ",
        "PUERTADIR2, IDPAISDIR1, IDPAISDIR2, DESCPAISDIR1, DESCPAISDIR2, IDTIPOIDENTIFICACIONOTROS, ",
        "ASISTIDOSOLICITAJG, ASISTIDOAUTORIZAEEJG, AUTORIZAAVISOTELEMATICO",
        "from SCS_PERSONAJG",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHANACIMIENTO", property="fechanacimiento", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPAIS", property="idpais", jdbcType=JdbcType.VARCHAR),
        @Result(column="NIF", property="nif", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO1", property="apellido1", jdbcType=JdbcType.VARCHAR),
        @Result(column="APELLIDO2", property="apellido2", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIRECCION", property="direccion", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPOSTAL", property="codigopostal", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROFESION", property="idprofesion", jdbcType=JdbcType.DECIMAL),
        @Result(column="REGIMEN_CONYUGAL", property="regimenConyugal", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION", property="idpoblacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDESTADOCIVIL", property="idestadocivil", jdbcType=JdbcType.DECIMAL),
        @Result(column="TIPOPERSONAJG", property="tipopersonajg", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOIDENTIFICACION", property="idtipoidentificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDREPRESENTANTEJG", property="idrepresentantejg", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOENCALIDAD", property="idtipoencalidad", jdbcType=JdbcType.DECIMAL),
        @Result(column="SEXO", property="sexo", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDLENGUAJE", property="idlenguaje", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROHIJOS", property="numerohijos", jdbcType=JdbcType.DECIMAL),
        @Result(column="FAX", property="fax", jdbcType=JdbcType.VARCHAR),
        @Result(column="CORREOELECTRONICO", property="correoelectronico", jdbcType=JdbcType.VARCHAR),
        @Result(column="EDAD", property="edad", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDMINUSVALIA", property="idminusvalia", jdbcType=JdbcType.DECIMAL),
        @Result(column="EXISTEDOMICILIO", property="existedomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA2", property="idprovincia2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION2", property="idpoblacion2", jdbcType=JdbcType.VARCHAR),
        @Result(column="DIRECCION2", property="direccion2", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPOSTAL2", property="codigopostal2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPODIR", property="idtipodir", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPODIR2", property="idtipodir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="CNAE", property="cnae", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOVIA", property="idtipovia", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMERODIR", property="numerodir", jdbcType=JdbcType.VARCHAR),
        @Result(column="ESCALERADIR", property="escaleradir", jdbcType=JdbcType.VARCHAR),
        @Result(column="PISODIR", property="pisodir", jdbcType=JdbcType.VARCHAR),
        @Result(column="PUERTADIR", property="puertadir", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOVIA2", property="idtipovia2", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMERODIR2", property="numerodir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="ESCALERADIR2", property="escaleradir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="PISODIR2", property="pisodir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="PUERTADIR2", property="puertadir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPAISDIR1", property="idpaisdir1", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPAISDIR2", property="idpaisdir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCPAISDIR1", property="descpaisdir1", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCPAISDIR2", property="descpaisdir2", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOIDENTIFICACIONOTROS", property="idtipoidentificacionotros", jdbcType=JdbcType.VARCHAR),
        @Result(column="ASISTIDOSOLICITAJG", property="asistidosolicitajg", jdbcType=JdbcType.VARCHAR),
        @Result(column="ASISTIDOAUTORIZAEEJG", property="asistidoautorizaeejg", jdbcType=JdbcType.VARCHAR),
        @Result(column="AUTORIZAAVISOTELEMATICO", property="autorizaavisotelematico", jdbcType=JdbcType.VARCHAR)
    })
    ScsPersonajg selectByPrimaryKey(ScsPersonajgKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @UpdateProvider(type=ScsPersonajgSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsPersonajg record, @Param("example") ScsPersonajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @UpdateProvider(type=ScsPersonajgSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsPersonajg record, @Param("example") ScsPersonajgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @UpdateProvider(type=ScsPersonajgSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsPersonajg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PERSONAJG
     *
     * @mbg.generated Fri Oct 25 14:10:06 CEST 2019
     */
    @Update({
        "update SCS_PERSONAJG",
        "set FECHANACIMIENTO = #{fechanacimiento,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "IDPAIS = #{idpais,jdbcType=VARCHAR},",
          "NIF = #{nif,jdbcType=VARCHAR},",
          "NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "APELLIDO1 = #{apellido1,jdbcType=VARCHAR},",
          "APELLIDO2 = #{apellido2,jdbcType=VARCHAR},",
          "DIRECCION = #{direccion,jdbcType=VARCHAR},",
          "CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR},",
          "IDPROFESION = #{idprofesion,jdbcType=DECIMAL},",
          "REGIMEN_CONYUGAL = #{regimenConyugal,jdbcType=VARCHAR},",
          "IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR},",
          "IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR},",
          "IDESTADOCIVIL = #{idestadocivil,jdbcType=DECIMAL},",
          "TIPOPERSONAJG = #{tipopersonajg,jdbcType=VARCHAR},",
          "IDTIPOIDENTIFICACION = #{idtipoidentificacion,jdbcType=DECIMAL},",
          "OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
          "IDREPRESENTANTEJG = #{idrepresentantejg,jdbcType=DECIMAL},",
          "IDTIPOENCALIDAD = #{idtipoencalidad,jdbcType=DECIMAL},",
          "SEXO = #{sexo,jdbcType=VARCHAR},",
          "IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR},",
          "NUMEROHIJOS = #{numerohijos,jdbcType=DECIMAL},",
          "FAX = #{fax,jdbcType=VARCHAR},",
          "CORREOELECTRONICO = #{correoelectronico,jdbcType=VARCHAR},",
          "EDAD = #{edad,jdbcType=DECIMAL},",
          "IDMINUSVALIA = #{idminusvalia,jdbcType=DECIMAL},",
          "EXISTEDOMICILIO = #{existedomicilio,jdbcType=VARCHAR},",
          "IDPROVINCIA2 = #{idprovincia2,jdbcType=VARCHAR},",
          "IDPOBLACION2 = #{idpoblacion2,jdbcType=VARCHAR},",
          "DIRECCION2 = #{direccion2,jdbcType=VARCHAR},",
          "CODIGOPOSTAL2 = #{codigopostal2,jdbcType=VARCHAR},",
          "IDTIPODIR = #{idtipodir,jdbcType=VARCHAR},",
          "IDTIPODIR2 = #{idtipodir2,jdbcType=VARCHAR},",
          "CNAE = #{cnae,jdbcType=VARCHAR},",
          "IDTIPOVIA = #{idtipovia,jdbcType=VARCHAR},",
          "NUMERODIR = #{numerodir,jdbcType=VARCHAR},",
          "ESCALERADIR = #{escaleradir,jdbcType=VARCHAR},",
          "PISODIR = #{pisodir,jdbcType=VARCHAR},",
          "PUERTADIR = #{puertadir,jdbcType=VARCHAR},",
          "IDTIPOVIA2 = #{idtipovia2,jdbcType=VARCHAR},",
          "NUMERODIR2 = #{numerodir2,jdbcType=VARCHAR},",
          "ESCALERADIR2 = #{escaleradir2,jdbcType=VARCHAR},",
          "PISODIR2 = #{pisodir2,jdbcType=VARCHAR},",
          "PUERTADIR2 = #{puertadir2,jdbcType=VARCHAR},",
          "IDPAISDIR1 = #{idpaisdir1,jdbcType=VARCHAR},",
          "IDPAISDIR2 = #{idpaisdir2,jdbcType=VARCHAR},",
          "DESCPAISDIR1 = #{descpaisdir1,jdbcType=VARCHAR},",
          "DESCPAISDIR2 = #{descpaisdir2,jdbcType=VARCHAR},",
          "IDTIPOIDENTIFICACIONOTROS = #{idtipoidentificacionotros,jdbcType=VARCHAR},",
          "ASISTIDOSOLICITAJG = #{asistidosolicitajg,jdbcType=VARCHAR},",
          "ASISTIDOAUTORIZAEEJG = #{asistidoautorizaeejg,jdbcType=VARCHAR},",
          "AUTORIZAAVISOTELEMATICO = #{autorizaavisotelematico,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsPersonajg record);
}