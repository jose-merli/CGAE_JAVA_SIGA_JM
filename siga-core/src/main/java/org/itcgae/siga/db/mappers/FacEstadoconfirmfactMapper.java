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
import org.itcgae.siga.db.entities.FacEstadoconfirmfact;
import org.itcgae.siga.db.entities.FacEstadoconfirmfactExample;

public interface FacEstadoconfirmfactMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @SelectProvider(type=FacEstadoconfirmfactSqlProvider.class, method="countByExample")
    long countByExample(FacEstadoconfirmfactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @DeleteProvider(type=FacEstadoconfirmfactSqlProvider.class, method="deleteByExample")
    int deleteByExample(FacEstadoconfirmfactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @Delete({
        "delete from FAC_ESTADOCONFIRMFACT",
        "where IDESTADO = #{idestado,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idestado);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @Insert({
        "insert into FAC_ESTADOCONFIRMFACT (IDESTADO, ALIAS, ",
        "DESCRIPCION, TIPO, ",
        "LENGUAJE, ORDEN)",
        "values (#{idestado,jdbcType=DECIMAL}, #{alias,jdbcType=VARCHAR}, ",
        "#{descripcion,jdbcType=VARCHAR}, #{tipo,jdbcType=VARCHAR}, ",
        "#{lenguaje,jdbcType=VARCHAR}, #{orden,jdbcType=DECIMAL})"
    })
    int insert(FacEstadoconfirmfact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @InsertProvider(type=FacEstadoconfirmfactSqlProvider.class, method="insertSelective")
    int insertSelective(FacEstadoconfirmfact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @SelectProvider(type=FacEstadoconfirmfactSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDESTADO", property="idestado", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ALIAS", property="alias", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="TIPO", property="tipo", jdbcType=JdbcType.VARCHAR),
        @Result(column="LENGUAJE", property="lenguaje", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL)
    })
    List<FacEstadoconfirmfact> selectByExample(FacEstadoconfirmfactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @Select({
        "select",
        "IDESTADO, ALIAS, DESCRIPCION, TIPO, LENGUAJE, ORDEN",
        "from FAC_ESTADOCONFIRMFACT",
        "where IDESTADO = #{idestado,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDESTADO", property="idestado", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ALIAS", property="alias", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="TIPO", property="tipo", jdbcType=JdbcType.VARCHAR),
        @Result(column="LENGUAJE", property="lenguaje", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL)
    })
    FacEstadoconfirmfact selectByPrimaryKey(Short idestado);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @UpdateProvider(type=FacEstadoconfirmfactSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FacEstadoconfirmfact record, @Param("example") FacEstadoconfirmfactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @UpdateProvider(type=FacEstadoconfirmfactSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FacEstadoconfirmfact record, @Param("example") FacEstadoconfirmfactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @UpdateProvider(type=FacEstadoconfirmfactSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FacEstadoconfirmfact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    @Update({
        "update FAC_ESTADOCONFIRMFACT",
        "set ALIAS = #{alias,jdbcType=VARCHAR},",
          "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "TIPO = #{tipo,jdbcType=VARCHAR},",
          "LENGUAJE = #{lenguaje,jdbcType=VARCHAR},",
          "ORDEN = #{orden,jdbcType=DECIMAL}",
        "where IDESTADO = #{idestado,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FacEstadoconfirmfact record);
}