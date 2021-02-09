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
import org.itcgae.siga.db.entities.CerSolicitudcertificados;
import org.itcgae.siga.db.entities.CerSolicitudcertificadosExample;
import org.itcgae.siga.db.entities.CerSolicitudcertificadosKey;

public interface CerSolicitudcertificadosMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @SelectProvider(type=CerSolicitudcertificadosSqlProvider.class, method="countByExample")
    long countByExample(CerSolicitudcertificadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @DeleteProvider(type=CerSolicitudcertificadosSqlProvider.class, method="deleteByExample")
    int deleteByExample(CerSolicitudcertificadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @Delete({
        "delete from CER_SOLICITUDCERTIFICADOS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(CerSolicitudcertificadosKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @Insert({
        "insert into CER_SOLICITUDCERTIFICADOS (IDINSTITUCION, IDSOLICITUD, ",
        "FECHASOLICITUD, IDESTADOSOLICITUDCERTIFICADO, ",
        "IDINSTITUCION_SOL, IDPERSONA_DES, ",
        "IDESTADOCERTIFICADO, FECHAMODIFICACION, ",
        "USUMODIFICACION, DESCRIPCION, ",
        "IDPERSONA_DIR, IDDIRECCION_DIR, ",
        "PPN_IDTIPOPRODUCTO, PPN_IDPRODUCTO, ",
        "FECHAEMISIONCERTIFICADO, PPN_IDPRODUCTOINSTITUCION, ",
        "IDTIPOENVIOS, IDINSTITUCIONORIGEN, ",
        "IDINSTITUCIONDESTINO, IDPETICIONPRODUCTO, ",
        "CONTADOR_CER, PREFIJO_CER, ",
        "SUFIJO_CER, FECHADESCARGA, ",
        "FECHACOBRO, FECHAENVIO, ",
        "FECHAESTADO, COMENTARIO, ",
        "FECHAENTREGAINFO, IDMETODOSOLICITUD, ",
        "USUCREACION, FECHACREACION, ",
        "IDINSTITUCIONCOLEGIACION, CBO_CODIGO, ",
        "CODIGO_SUCURSAL, ACEPTACESIONMUTUALIDAD, ",
        "FICHERO_DOCUMENTO)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idsolicitud,jdbcType=DECIMAL}, ",
        "#{fechasolicitud,jdbcType=TIMESTAMP}, #{idestadosolicitudcertificado,jdbcType=DECIMAL}, ",
        "#{idinstitucionSol,jdbcType=DECIMAL}, #{idpersonaDes,jdbcType=DECIMAL}, ",
        "#{idestadocertificado,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{idpersonaDir,jdbcType=DECIMAL}, #{iddireccionDir,jdbcType=DECIMAL}, ",
        "#{ppnIdtipoproducto,jdbcType=DECIMAL}, #{ppnIdproducto,jdbcType=DECIMAL}, ",
        "#{fechaemisioncertificado,jdbcType=TIMESTAMP}, #{ppnIdproductoinstitucion,jdbcType=DECIMAL}, ",
        "#{idtipoenvios,jdbcType=DECIMAL}, #{idinstitucionorigen,jdbcType=DECIMAL}, ",
        "#{idinstituciondestino,jdbcType=DECIMAL}, #{idpeticionproducto,jdbcType=DECIMAL}, ",
        "#{contadorCer,jdbcType=DECIMAL}, #{prefijoCer,jdbcType=VARCHAR}, ",
        "#{sufijoCer,jdbcType=VARCHAR}, #{fechadescarga,jdbcType=TIMESTAMP}, ",
        "#{fechacobro,jdbcType=TIMESTAMP}, #{fechaenvio,jdbcType=TIMESTAMP}, ",
        "#{fechaestado,jdbcType=TIMESTAMP}, #{comentario,jdbcType=VARCHAR}, ",
        "#{fechaentregainfo,jdbcType=TIMESTAMP}, #{idmetodosolicitud,jdbcType=DECIMAL}, ",
        "#{usucreacion,jdbcType=DECIMAL}, #{fechacreacion,jdbcType=TIMESTAMP}, ",
        "#{idinstitucioncolegiacion,jdbcType=DECIMAL}, #{cboCodigo,jdbcType=VARCHAR}, ",
        "#{codigoSucursal,jdbcType=VARCHAR}, #{aceptacesionmutualidad,jdbcType=VARCHAR}, ",
        "#{ficheroDocumento,jdbcType=VARCHAR})"
    })
    int insert(CerSolicitudcertificados record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @InsertProvider(type=CerSolicitudcertificadosSqlProvider.class, method="insertSelective")
    int insertSelective(CerSolicitudcertificados record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @SelectProvider(type=CerSolicitudcertificadosSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSOLICITUD", property="idsolicitud", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDESTADOSOLICITUDCERTIFICADO", property="idestadosolicitudcertificado", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION_SOL", property="idinstitucionSol", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA_DES", property="idpersonaDes", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDESTADOCERTIFICADO", property="idestadocertificado", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERSONA_DIR", property="idpersonaDir", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDDIRECCION_DIR", property="iddireccionDir", jdbcType=JdbcType.DECIMAL),
        @Result(column="PPN_IDTIPOPRODUCTO", property="ppnIdtipoproducto", jdbcType=JdbcType.DECIMAL),
        @Result(column="PPN_IDPRODUCTO", property="ppnIdproducto", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAEMISIONCERTIFICADO", property="fechaemisioncertificado", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PPN_IDPRODUCTOINSTITUCION", property="ppnIdproductoinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCIONORIGEN", property="idinstitucionorigen", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCIONDESTINO", property="idinstituciondestino", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPETICIONPRODUCTO", property="idpeticionproducto", jdbcType=JdbcType.DECIMAL),
        @Result(column="CONTADOR_CER", property="contadorCer", jdbcType=JdbcType.DECIMAL),
        @Result(column="PREFIJO_CER", property="prefijoCer", jdbcType=JdbcType.VARCHAR),
        @Result(column="SUFIJO_CER", property="sufijoCer", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHADESCARGA", property="fechadescarga", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHACOBRO", property="fechacobro", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAENVIO", property="fechaenvio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAESTADO", property="fechaestado", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="COMENTARIO", property="comentario", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAENTREGAINFO", property="fechaentregainfo", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDMETODOSOLICITUD", property="idmetodosolicitud", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUCREACION", property="usucreacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCIONCOLEGIACION", property="idinstitucioncolegiacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CBO_CODIGO", property="cboCodigo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGO_SUCURSAL", property="codigoSucursal", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACEPTACESIONMUTUALIDAD", property="aceptacesionmutualidad", jdbcType=JdbcType.VARCHAR),
        @Result(column="FICHERO_DOCUMENTO", property="ficheroDocumento", jdbcType=JdbcType.VARCHAR)
    })
    List<CerSolicitudcertificados> selectByExample(CerSolicitudcertificadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @Select({
        "select",
        "IDINSTITUCION, IDSOLICITUD, FECHASOLICITUD, IDESTADOSOLICITUDCERTIFICADO, IDINSTITUCION_SOL, ",
        "IDPERSONA_DES, IDESTADOCERTIFICADO, FECHAMODIFICACION, USUMODIFICACION, DESCRIPCION, ",
        "IDPERSONA_DIR, IDDIRECCION_DIR, PPN_IDTIPOPRODUCTO, PPN_IDPRODUCTO, FECHAEMISIONCERTIFICADO, ",
        "PPN_IDPRODUCTOINSTITUCION, IDTIPOENVIOS, IDINSTITUCIONORIGEN, IDINSTITUCIONDESTINO, ",
        "IDPETICIONPRODUCTO, CONTADOR_CER, PREFIJO_CER, SUFIJO_CER, FECHADESCARGA, FECHACOBRO, ",
        "FECHAENVIO, FECHAESTADO, COMENTARIO, FECHAENTREGAINFO, IDMETODOSOLICITUD, USUCREACION, ",
        "FECHACREACION, IDINSTITUCIONCOLEGIACION, CBO_CODIGO, CODIGO_SUCURSAL, ACEPTACESIONMUTUALIDAD, ",
        "FICHERO_DOCUMENTO",
        "from CER_SOLICITUDCERTIFICADOS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSOLICITUD", property="idsolicitud", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDESTADOSOLICITUDCERTIFICADO", property="idestadosolicitudcertificado", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION_SOL", property="idinstitucionSol", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPERSONA_DES", property="idpersonaDes", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDESTADOCERTIFICADO", property="idestadocertificado", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERSONA_DIR", property="idpersonaDir", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDDIRECCION_DIR", property="iddireccionDir", jdbcType=JdbcType.DECIMAL),
        @Result(column="PPN_IDTIPOPRODUCTO", property="ppnIdtipoproducto", jdbcType=JdbcType.DECIMAL),
        @Result(column="PPN_IDPRODUCTO", property="ppnIdproducto", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAEMISIONCERTIFICADO", property="fechaemisioncertificado", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PPN_IDPRODUCTOINSTITUCION", property="ppnIdproductoinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOENVIOS", property="idtipoenvios", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCIONORIGEN", property="idinstitucionorigen", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCIONDESTINO", property="idinstituciondestino", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPETICIONPRODUCTO", property="idpeticionproducto", jdbcType=JdbcType.DECIMAL),
        @Result(column="CONTADOR_CER", property="contadorCer", jdbcType=JdbcType.DECIMAL),
        @Result(column="PREFIJO_CER", property="prefijoCer", jdbcType=JdbcType.VARCHAR),
        @Result(column="SUFIJO_CER", property="sufijoCer", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHADESCARGA", property="fechadescarga", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHACOBRO", property="fechacobro", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAENVIO", property="fechaenvio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAESTADO", property="fechaestado", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="COMENTARIO", property="comentario", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAENTREGAINFO", property="fechaentregainfo", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDMETODOSOLICITUD", property="idmetodosolicitud", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUCREACION", property="usucreacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDINSTITUCIONCOLEGIACION", property="idinstitucioncolegiacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CBO_CODIGO", property="cboCodigo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CODIGO_SUCURSAL", property="codigoSucursal", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACEPTACESIONMUTUALIDAD", property="aceptacesionmutualidad", jdbcType=JdbcType.VARCHAR),
        @Result(column="FICHERO_DOCUMENTO", property="ficheroDocumento", jdbcType=JdbcType.VARCHAR)
    })
    CerSolicitudcertificados selectByPrimaryKey(CerSolicitudcertificadosKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @UpdateProvider(type=CerSolicitudcertificadosSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CerSolicitudcertificados record, @Param("example") CerSolicitudcertificadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @UpdateProvider(type=CerSolicitudcertificadosSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CerSolicitudcertificados record, @Param("example") CerSolicitudcertificadosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @UpdateProvider(type=CerSolicitudcertificadosSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CerSolicitudcertificados record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CER_SOLICITUDCERTIFICADOS
     *
     * @mbg.generated Thu Oct 25 13:43:52 CEST 2018
     */
    @Update({
        "update CER_SOLICITUDCERTIFICADOS",
        "set FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP},",
          "IDESTADOSOLICITUDCERTIFICADO = #{idestadosolicitudcertificado,jdbcType=DECIMAL},",
          "IDINSTITUCION_SOL = #{idinstitucionSol,jdbcType=DECIMAL},",
          "IDPERSONA_DES = #{idpersonaDes,jdbcType=DECIMAL},",
          "IDESTADOCERTIFICADO = #{idestadocertificado,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "IDPERSONA_DIR = #{idpersonaDir,jdbcType=DECIMAL},",
          "IDDIRECCION_DIR = #{iddireccionDir,jdbcType=DECIMAL},",
          "PPN_IDTIPOPRODUCTO = #{ppnIdtipoproducto,jdbcType=DECIMAL},",
          "PPN_IDPRODUCTO = #{ppnIdproducto,jdbcType=DECIMAL},",
          "FECHAEMISIONCERTIFICADO = #{fechaemisioncertificado,jdbcType=TIMESTAMP},",
          "PPN_IDPRODUCTOINSTITUCION = #{ppnIdproductoinstitucion,jdbcType=DECIMAL},",
          "IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL},",
          "IDINSTITUCIONORIGEN = #{idinstitucionorigen,jdbcType=DECIMAL},",
          "IDINSTITUCIONDESTINO = #{idinstituciondestino,jdbcType=DECIMAL},",
          "IDPETICIONPRODUCTO = #{idpeticionproducto,jdbcType=DECIMAL},",
          "CONTADOR_CER = #{contadorCer,jdbcType=DECIMAL},",
          "PREFIJO_CER = #{prefijoCer,jdbcType=VARCHAR},",
          "SUFIJO_CER = #{sufijoCer,jdbcType=VARCHAR},",
          "FECHADESCARGA = #{fechadescarga,jdbcType=TIMESTAMP},",
          "FECHACOBRO = #{fechacobro,jdbcType=TIMESTAMP},",
          "FECHAENVIO = #{fechaenvio,jdbcType=TIMESTAMP},",
          "FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP},",
          "COMENTARIO = #{comentario,jdbcType=VARCHAR},",
          "FECHAENTREGAINFO = #{fechaentregainfo,jdbcType=TIMESTAMP},",
          "IDMETODOSOLICITUD = #{idmetodosolicitud,jdbcType=DECIMAL},",
          "USUCREACION = #{usucreacion,jdbcType=DECIMAL},",
          "FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
          "IDINSTITUCIONCOLEGIACION = #{idinstitucioncolegiacion,jdbcType=DECIMAL},",
          "CBO_CODIGO = #{cboCodigo,jdbcType=VARCHAR},",
          "CODIGO_SUCURSAL = #{codigoSucursal,jdbcType=VARCHAR},",
          "ACEPTACESIONMUTUALIDAD = #{aceptacesionmutualidad,jdbcType=VARCHAR},",
          "FICHERO_DOCUMENTO = #{ficheroDocumento,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(CerSolicitudcertificados record);
}