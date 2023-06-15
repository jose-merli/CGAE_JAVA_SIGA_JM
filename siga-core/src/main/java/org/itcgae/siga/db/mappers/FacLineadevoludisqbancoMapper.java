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
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.entities.FacLineadevoludisqbancoExample;
import org.itcgae.siga.db.entities.FacLineadevoludisqbancoKey;

public interface FacLineadevoludisqbancoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @SelectProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="countByExample")
    long countByExample(FacLineadevoludisqbancoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @DeleteProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="deleteByExample")
    int deleteByExample(FacLineadevoludisqbancoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @Delete({
        "delete from FAC_LINEADEVOLUDISQBANCO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDDISQUETEDEVOLUCIONES = #{iddisquetedevoluciones,jdbcType=DECIMAL}",
          "and IDRECIBO = #{idrecibo,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(FacLineadevoludisqbancoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @Insert({
        "insert into FAC_LINEADEVOLUDISQBANCO (IDINSTITUCION, IDDISQUETEDEVOLUCIONES, ",
        "IDRECIBO, IDDISQUETECARGOS, ",
        "IDFACTURAINCLUIDAENDISQUETE, FECHAMODIFICACION, ",
        "USUMODIFICACION, DESCRIPCIONMOTIVOS, ",
        "GASTOSDEVOLUCION, CARGARCLIENTE, ",
        "CONTABILIZADA)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{iddisquetedevoluciones,jdbcType=DECIMAL}, ",
        "#{idrecibo,jdbcType=VARCHAR}, #{iddisquetecargos,jdbcType=DECIMAL}, ",
        "#{idfacturaincluidaendisquete,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL}, #{descripcionmotivos,jdbcType=VARCHAR}, ",
        "#{gastosdevolucion,jdbcType=DECIMAL}, #{cargarcliente,jdbcType=VARCHAR}, ",
        "#{contabilizada,jdbcType=VARCHAR})"
    })
    int insert(FacLineadevoludisqbanco record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @InsertProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="insertSelective")
    int insertSelective(FacLineadevoludisqbanco record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @SelectProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDDISQUETEDEVOLUCIONES", property="iddisquetedevoluciones", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDRECIBO", property="idrecibo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="IDDISQUETECARGOS", property="iddisquetecargos", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURAINCLUIDAENDISQUETE", property="idfacturaincluidaendisquete", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCIONMOTIVOS", property="descripcionmotivos", jdbcType=JdbcType.VARCHAR),
        @Result(column="GASTOSDEVOLUCION", property="gastosdevolucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CARGARCLIENTE", property="cargarcliente", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONTABILIZADA", property="contabilizada", jdbcType=JdbcType.VARCHAR)
    })
    List<FacLineadevoludisqbanco> selectByExample(FacLineadevoludisqbancoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @Select({
        "select",
        "IDINSTITUCION, IDDISQUETEDEVOLUCIONES, IDRECIBO, IDDISQUETECARGOS, IDFACTURAINCLUIDAENDISQUETE, ",
        "FECHAMODIFICACION, USUMODIFICACION, DESCRIPCIONMOTIVOS, GASTOSDEVOLUCION, CARGARCLIENTE, ",
        "CONTABILIZADA",
        "from FAC_LINEADEVOLUDISQBANCO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDDISQUETEDEVOLUCIONES = #{iddisquetedevoluciones,jdbcType=DECIMAL}",
          "and IDRECIBO = #{idrecibo,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDDISQUETEDEVOLUCIONES", property="iddisquetedevoluciones", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDRECIBO", property="idrecibo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="IDDISQUETECARGOS", property="iddisquetecargos", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDFACTURAINCLUIDAENDISQUETE", property="idfacturaincluidaendisquete", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCIONMOTIVOS", property="descripcionmotivos", jdbcType=JdbcType.VARCHAR),
        @Result(column="GASTOSDEVOLUCION", property="gastosdevolucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CARGARCLIENTE", property="cargarcliente", jdbcType=JdbcType.VARCHAR),
        @Result(column="CONTABILIZADA", property="contabilizada", jdbcType=JdbcType.VARCHAR)
    })
    FacLineadevoludisqbanco selectByPrimaryKey(FacLineadevoludisqbancoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @UpdateProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FacLineadevoludisqbanco record, @Param("example") FacLineadevoludisqbancoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @UpdateProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FacLineadevoludisqbanco record, @Param("example") FacLineadevoludisqbancoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @UpdateProvider(type=FacLineadevoludisqbancoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FacLineadevoludisqbanco record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_LINEADEVOLUDISQBANCO
     *
     * @mbg.generated Wed Jan 12 17:13:18 CET 2022
     */
    @Update({
        "update FAC_LINEADEVOLUDISQBANCO",
        "set IDDISQUETECARGOS = #{iddisquetecargos,jdbcType=DECIMAL},",
          "IDFACTURAINCLUIDAENDISQUETE = #{idfacturaincluidaendisquete,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "DESCRIPCIONMOTIVOS = #{descripcionmotivos,jdbcType=VARCHAR},",
          "GASTOSDEVOLUCION = #{gastosdevolucion,jdbcType=DECIMAL},",
          "CARGARCLIENTE = #{cargarcliente,jdbcType=VARCHAR},",
          "CONTABILIZADA = #{contabilizada,jdbcType=VARCHAR}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDDISQUETEDEVOLUCIONES = #{iddisquetedevoluciones,jdbcType=DECIMAL}",
          "and IDRECIBO = #{idrecibo,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(FacLineadevoludisqbanco record);
}