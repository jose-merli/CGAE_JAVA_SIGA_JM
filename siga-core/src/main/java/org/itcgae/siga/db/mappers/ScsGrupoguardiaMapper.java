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
import org.itcgae.siga.db.entities.ScsGrupoguardia;
import org.itcgae.siga.db.entities.ScsGrupoguardiaExample;

public interface ScsGrupoguardiaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @SelectProvider(type=ScsGrupoguardiaSqlProvider.class, method="countByExample")
    long countByExample(ScsGrupoguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @DeleteProvider(type=ScsGrupoguardiaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsGrupoguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @Delete({
        "delete from SCS_GRUPOGUARDIA",
        "where IDGRUPOGUARDIA = #{idgrupoguardia,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long idgrupoguardia);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @Insert({
        "insert into SCS_GRUPOGUARDIA (IDGRUPOGUARDIA, IDINSTITUCION, ",
        "IDTURNO, IDGUARDIA, ",
        "NUMEROGRUPO, FECHACREACION, ",
        "USUCREACION, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idgrupoguardia,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idturno,jdbcType=DECIMAL}, #{idguardia,jdbcType=DECIMAL}, ",
        "#{numerogrupo,jdbcType=DECIMAL}, #{fechacreacion,jdbcType=TIMESTAMP}, ",
        "#{usucreacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT SQ_SCS_GRUPOGUARDIA.NEXTVAL FROM DUAl;", keyProperty="idgrupoguardia", before=true, resultType=Long.class)
    int insert(ScsGrupoguardia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @InsertProvider(type=ScsGrupoguardiaSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SQ_SCS_GRUPOGUARDIA.NEXTVAL FROM DUAl;", keyProperty="idgrupoguardia", before=true, resultType=Long.class)
    int insertSelective(ScsGrupoguardia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @SelectProvider(type=ScsGrupoguardiaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDGRUPOGUARDIA", property="idgrupoguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMEROGRUPO", property="numerogrupo", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUCREACION", property="usucreacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsGrupoguardia> selectByExample(ScsGrupoguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @Select({
        "select",
        "IDGRUPOGUARDIA, IDINSTITUCION, IDTURNO, IDGUARDIA, NUMEROGRUPO, FECHACREACION, ",
        "USUCREACION, FECHAMODIFICACION, USUMODIFICACION",
        "from SCS_GRUPOGUARDIA",
        "where IDGRUPOGUARDIA = #{idgrupoguardia,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDGRUPOGUARDIA", property="idgrupoguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL),
        @Result(column="NUMEROGRUPO", property="numerogrupo", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUCREACION", property="usucreacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ScsGrupoguardia selectByPrimaryKey(Long idgrupoguardia);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @UpdateProvider(type=ScsGrupoguardiaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsGrupoguardia record, @Param("example") ScsGrupoguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @UpdateProvider(type=ScsGrupoguardiaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsGrupoguardia record, @Param("example") ScsGrupoguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @UpdateProvider(type=ScsGrupoguardiaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsGrupoguardia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GRUPOGUARDIA
     *
     * @mbg.generated Mon Dec 16 14:01:04 CET 2019
     */
    @Update({
        "update SCS_GRUPOGUARDIA",
        "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "IDTURNO = #{idturno,jdbcType=DECIMAL},",
          "IDGUARDIA = #{idguardia,jdbcType=DECIMAL},",
          "NUMEROGRUPO = #{numerogrupo,jdbcType=DECIMAL},",
          "FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
          "USUCREACION = #{usucreacion,jdbcType=DECIMAL},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDGRUPOGUARDIA = #{idgrupoguardia,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsGrupoguardia record);
}