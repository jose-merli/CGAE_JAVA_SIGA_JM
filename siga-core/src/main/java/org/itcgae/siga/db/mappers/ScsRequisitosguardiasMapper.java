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
import org.itcgae.siga.db.entities.ScsRequisitosguardias;
import org.itcgae.siga.db.entities.ScsRequisitosguardiasExample;

public interface ScsRequisitosguardiasMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @SelectProvider(type=ScsRequisitosguardiasSqlProvider.class, method="countByExample")
    long countByExample(ScsRequisitosguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @DeleteProvider(type=ScsRequisitosguardiasSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsRequisitosguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @Delete({
        "delete from SCS_REQUISITOSGUARDIAS",
        "where IDREQUISITOSGUARDIAS = #{idrequisitosguardias,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idrequisitosguardias);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @Insert({
        "insert into SCS_REQUISITOSGUARDIAS (IDREQUISITOSGUARDIAS, DESCRIPCION, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idrequisitosguardias,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ScsRequisitosguardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @InsertProvider(type=ScsRequisitosguardiasSqlProvider.class, method="insertSelective")
    int insertSelective(ScsRequisitosguardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @SelectProvider(type=ScsRequisitosguardiasSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDREQUISITOSGUARDIAS", property="idrequisitosguardias", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsRequisitosguardias> selectByExample(ScsRequisitosguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @Select({
        "select",
        "IDREQUISITOSGUARDIAS, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION",
        "from SCS_REQUISITOSGUARDIAS",
        "where IDREQUISITOSGUARDIAS = #{idrequisitosguardias,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDREQUISITOSGUARDIAS", property="idrequisitosguardias", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ScsRequisitosguardias selectByPrimaryKey(Short idrequisitosguardias);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @UpdateProvider(type=ScsRequisitosguardiasSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsRequisitosguardias record, @Param("example") ScsRequisitosguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @UpdateProvider(type=ScsRequisitosguardiasSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsRequisitosguardias record, @Param("example") ScsRequisitosguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @UpdateProvider(type=ScsRequisitosguardiasSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsRequisitosguardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_REQUISITOSGUARDIAS
     *
     * @mbg.generated Wed Nov 13 14:52:59 CET 2019
     */
    @Update({
        "update SCS_REQUISITOSGUARDIAS",
        "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDREQUISITOSGUARDIAS = #{idrequisitosguardias,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsRequisitosguardias record);
}