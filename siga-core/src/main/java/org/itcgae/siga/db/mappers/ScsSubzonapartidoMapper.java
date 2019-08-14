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
import org.itcgae.siga.db.entities.ScsSubzonapartido;
import org.itcgae.siga.db.entities.ScsSubzonapartidoExample;
import org.itcgae.siga.db.entities.ScsSubzonapartidoKey;

public interface ScsSubzonapartidoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @SelectProvider(type=ScsSubzonapartidoSqlProvider.class, method="countByExample")
    long countByExample(ScsSubzonapartidoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @DeleteProvider(type=ScsSubzonapartidoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsSubzonapartidoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @Delete({
        "delete from SCS_SUBZONAPARTIDO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDSUBZONA = #{idsubzona,jdbcType=DECIMAL}",
          "and IDZONA = #{idzona,jdbcType=DECIMAL}",
          "and IDPARTIDO = #{idpartido,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsSubzonapartidoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @Insert({
        "insert into SCS_SUBZONAPARTIDO (IDINSTITUCION, IDSUBZONA, ",
        "IDZONA, IDPARTIDO, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "FECHABAJA)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idsubzona,jdbcType=DECIMAL}, ",
        "#{idzona,jdbcType=DECIMAL}, #{idpartido,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{fechabaja,jdbcType=TIMESTAMP})"
    })
    int insert(ScsSubzonapartido record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @InsertProvider(type=ScsSubzonapartidoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsSubzonapartido record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @SelectProvider(type=ScsSubzonapartidoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSUBZONA", property="idsubzona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDZONA", property="idzona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPARTIDO", property="idpartido", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ScsSubzonapartido> selectByExample(ScsSubzonapartidoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @Select({
        "select",
        "IDINSTITUCION, IDSUBZONA, IDZONA, IDPARTIDO, FECHAMODIFICACION, USUMODIFICACION, ",
        "FECHABAJA",
        "from SCS_SUBZONAPARTIDO",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDSUBZONA = #{idsubzona,jdbcType=DECIMAL}",
          "and IDZONA = #{idzona,jdbcType=DECIMAL}",
          "and IDPARTIDO = #{idpartido,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDSUBZONA", property="idsubzona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDZONA", property="idzona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPARTIDO", property="idpartido", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ScsSubzonapartido selectByPrimaryKey(ScsSubzonapartidoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @UpdateProvider(type=ScsSubzonapartidoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsSubzonapartido record, @Param("example") ScsSubzonapartidoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @UpdateProvider(type=ScsSubzonapartidoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsSubzonapartido record, @Param("example") ScsSubzonapartidoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @UpdateProvider(type=ScsSubzonapartidoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsSubzonapartido record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_SUBZONAPARTIDO
     *
     * @mbg.generated Thu Aug 08 11:25:13 CEST 2019
     */
    @Update({
        "update SCS_SUBZONAPARTIDO",
        "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDSUBZONA = #{idsubzona,jdbcType=DECIMAL}",
          "and IDZONA = #{idzona,jdbcType=DECIMAL}",
          "and IDPARTIDO = #{idpartido,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsSubzonapartido record);
}