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
import org.itcgae.siga.db.entities.ScsJuzgado;
import org.itcgae.siga.db.entities.ScsJuzgadoExample;
import org.itcgae.siga.db.entities.ScsJuzgadoKey;

public interface ScsJuzgadoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @SelectProvider(type=ScsJuzgadoSqlProvider.class, method="countByExample")
    long countByExample(ScsJuzgadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @DeleteProvider(type=ScsJuzgadoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsJuzgadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @Delete({
        "delete from SCS_JUZGADO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsJuzgadoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @Insert({
        "insert into SCS_JUZGADO (IDINSTITUCION, IDJUZGADO, ",
        "NOMBRE, DOMICILIO, ",
        "CODIGOPOSTAL, IDPOBLACION, ",
        "IDPROVINCIA, TELEFONO1, ",
        "TELEFONO2, FAX1, ",
        "FECHABAJA, FECHAMODIFICACION, ",
        "USUMODIFICACION, CODIGOEXT, ",
        "CODIGOPROCURADOR, VISIBLE, ",
        "MOVIL, ESDECANO, ",
        "CODIGOEXT2, EMAIL, ",
        "ISCODIGOEJIS, FECHACODIGOEJIS, ",
        "CODIGOEJIS, VISIBLEMOVIL)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idjuzgado,jdbcType=DECIMAL}, ",
        "#{nombre,jdbcType=VARCHAR}, #{domicilio,jdbcType=VARCHAR}, ",
        "#{codigopostal,jdbcType=VARCHAR}, #{idpoblacion,jdbcType=VARCHAR}, ",
        "#{idprovincia,jdbcType=VARCHAR}, #{telefono1,jdbcType=VARCHAR}, ",
        "#{telefono2,jdbcType=VARCHAR}, #{fax1,jdbcType=VARCHAR}, ",
        "#{fechabaja,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
        "#{codigoprocurador,jdbcType=VARCHAR}, #{visible,jdbcType=VARCHAR}, ",
        "#{movil,jdbcType=VARCHAR}, #{esdecano,jdbcType=DECIMAL}, ",
        "#{codigoext2,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{iscodigoejis,jdbcType=DECIMAL}, #{fechacodigoejis,jdbcType=TIMESTAMP}, ",
        "#{codigoejis,jdbcType=VARCHAR}, #{visiblemovil,jdbcType=DECIMAL})"
    })
    int insert(ScsJuzgado record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @InsertProvider(type=ScsJuzgadoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsJuzgado record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @SelectProvider(type=ScsJuzgadoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDJUZGADO", property="idjuzgado", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="DOMICILIO", property="domicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPOSTAL", property="codigopostal", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION", property="idpoblacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO1", property="telefono1", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO2", property="telefono2", jdbcType=JdbcType.VARCHAR),
        @Result(column="FAX1", property="fax1", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPROCURADOR", property="codigoprocurador", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISIBLE", property="visible", jdbcType=JdbcType.VARCHAR),
        @Result(column="MOVIL", property="movil", jdbcType=JdbcType.VARCHAR),
        @Result(column="ESDECANO", property="esdecano", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT2", property="codigoext2", jdbcType=JdbcType.VARCHAR),
        @Result(column="EMAIL", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="ISCODIGOEJIS", property="iscodigoejis", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACODIGOEJIS", property="fechacodigoejis", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CODIGOEJIS", property="codigoejis", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISIBLEMOVIL", property="visiblemovil", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsJuzgado> selectByExample(ScsJuzgadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDJUZGADO, NOMBRE, DOMICILIO, CODIGOPOSTAL, IDPOBLACION, IDPROVINCIA, ",
        "TELEFONO1, TELEFONO2, FAX1, FECHABAJA, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, ",
        "CODIGOPROCURADOR, VISIBLE, MOVIL, ESDECANO, CODIGOEXT2, EMAIL, ISCODIGOEJIS, ",
        "FECHACODIGOEJIS, CODIGOEJIS, VISIBLEMOVIL",
        "from SCS_JUZGADO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDJUZGADO", property="idjuzgado", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="DOMICILIO", property="domicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPOSTAL", property="codigopostal", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPOBLACION", property="idpoblacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPROVINCIA", property="idprovincia", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO1", property="telefono1", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO2", property="telefono2", jdbcType=JdbcType.VARCHAR),
        @Result(column="FAX1", property="fax1", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGOPROCURADOR", property="codigoprocurador", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISIBLE", property="visible", jdbcType=JdbcType.VARCHAR),
        @Result(column="MOVIL", property="movil", jdbcType=JdbcType.VARCHAR),
        @Result(column="ESDECANO", property="esdecano", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT2", property="codigoext2", jdbcType=JdbcType.VARCHAR),
        @Result(column="EMAIL", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="ISCODIGOEJIS", property="iscodigoejis", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACODIGOEJIS", property="fechacodigoejis", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CODIGOEJIS", property="codigoejis", jdbcType=JdbcType.VARCHAR),
        @Result(column="VISIBLEMOVIL", property="visiblemovil", jdbcType=JdbcType.DECIMAL)
    })
    ScsJuzgado selectByPrimaryKey(ScsJuzgadoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @UpdateProvider(type=ScsJuzgadoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsJuzgado record, @Param("example") ScsJuzgadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @UpdateProvider(type=ScsJuzgadoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsJuzgado record, @Param("example") ScsJuzgadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @UpdateProvider(type=ScsJuzgadoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsJuzgado record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_JUZGADO
     *
     * @mbg.generated Thu Sep 05 12:20:51 CEST 2019
     */
    @Update({
        "update SCS_JUZGADO",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "DOMICILIO = #{domicilio,jdbcType=VARCHAR},",
          "CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR},",
          "IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR},",
          "IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR},",
          "TELEFONO1 = #{telefono1,jdbcType=VARCHAR},",
          "TELEFONO2 = #{telefono2,jdbcType=VARCHAR},",
          "FAX1 = #{fax1,jdbcType=VARCHAR},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "CODIGOPROCURADOR = #{codigoprocurador,jdbcType=VARCHAR},",
          "VISIBLE = #{visible,jdbcType=VARCHAR},",
          "MOVIL = #{movil,jdbcType=VARCHAR},",
          "ESDECANO = #{esdecano,jdbcType=DECIMAL},",
          "CODIGOEXT2 = #{codigoext2,jdbcType=VARCHAR},",
          "EMAIL = #{email,jdbcType=VARCHAR},",
          "ISCODIGOEJIS = #{iscodigoejis,jdbcType=DECIMAL},",
          "FECHACODIGOEJIS = #{fechacodigoejis,jdbcType=TIMESTAMP},",
          "CODIGOEJIS = #{codigoejis,jdbcType=VARCHAR},",
          "VISIBLEMOVIL = #{visiblemovil,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsJuzgado record);
}