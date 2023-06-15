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
import org.itcgae.siga.db.entities.ScsDelito;
import org.itcgae.siga.db.entities.ScsDelitoExample;
import org.itcgae.siga.db.entities.ScsDelitoKey;

public interface ScsDelitoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @SelectProvider(type=ScsDelitoSqlProvider.class, method="countByExample")
    long countByExample(ScsDelitoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @DeleteProvider(type=ScsDelitoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsDelitoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @Delete({
        "delete from SCS_DELITO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDDELITO = #{iddelito,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsDelitoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @Insert({
        "insert into SCS_DELITO (IDINSTITUCION, IDDELITO, ",
        "DESCRIPCION, FECHAMODIFICACION, ",
        "USUMODIFICACION, CODIGOEXT, ",
        "BLOQUEADO, FECHABAJA, ",
        "IDTIPODELITO, ARTICULO_LEY, ",
        "FECHA_BAJA)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{iddelito,jdbcType=DECIMAL}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
        "#{bloqueado,jdbcType=CHAR}, #{fechabaja,jdbcType=TIMESTAMP}, ",
        "#{idtipodelito,jdbcType=DECIMAL}, #{articuloLey,jdbcType=VARCHAR}, ",
        "#{fechaBaja,jdbcType=TIMESTAMP})"
    })
    int insert(ScsDelito record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @InsertProvider(type=ScsDelitoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsDelito record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @SelectProvider(type=ScsDelitoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDDELITO", property="iddelito", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTIPODELITO", property="idtipodelito", jdbcType=JdbcType.DECIMAL),
        @Result(column="ARTICULO_LEY", property="articuloLey", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ScsDelito> selectByExample(ScsDelitoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, IDDELITO, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, ",
        "BLOQUEADO, FECHABAJA, IDTIPODELITO, ARTICULO_LEY, FECHA_BAJA",
        "from SCS_DELITO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDDELITO = #{iddelito,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDDELITO", property="iddelito", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTIPODELITO", property="idtipodelito", jdbcType=JdbcType.DECIMAL),
        @Result(column="ARTICULO_LEY", property="articuloLey", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ScsDelito selectByPrimaryKey(ScsDelitoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @UpdateProvider(type=ScsDelitoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsDelito record, @Param("example") ScsDelitoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @UpdateProvider(type=ScsDelitoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsDelito record, @Param("example") ScsDelitoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @UpdateProvider(type=ScsDelitoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsDelito record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITO
     *
     * @mbg.generated Mon Jun 14 13:03:23 CEST 2021
     */
    @Update({
        "update SCS_DELITO",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
          "BLOQUEADO = #{bloqueado,jdbcType=CHAR},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
          "IDTIPODELITO = #{idtipodelito,jdbcType=DECIMAL},",
          "ARTICULO_LEY = #{articuloLey,jdbcType=VARCHAR},",
          "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDDELITO = #{iddelito,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsDelito record);
}