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
import org.itcgae.siga.db.entities.FacMotivodevolucion;
import org.itcgae.siga.db.entities.FacMotivodevolucionExample;

public interface FacMotivodevolucionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @SelectProvider(type=FacMotivodevolucionSqlProvider.class, method="countByExample")
    long countByExample(FacMotivodevolucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @DeleteProvider(type=FacMotivodevolucionSqlProvider.class, method="deleteByExample")
    int deleteByExample(FacMotivodevolucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @Delete({
        "delete from FAC_MOTIVODEVOLUCION",
        "where IDMOTIVO = #{idmotivo,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idmotivo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @Insert({
        "insert into FAC_MOTIVODEVOLUCION (IDMOTIVO, CODIGO, ",
        "NOMBRE)",
        "values (#{idmotivo,jdbcType=DECIMAL}, #{codigo,jdbcType=VARCHAR}, ",
        "#{nombre,jdbcType=VARCHAR})"
    })
    int insert(FacMotivodevolucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @InsertProvider(type=FacMotivodevolucionSqlProvider.class, method="insertSelective")
    int insertSelective(FacMotivodevolucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @SelectProvider(type=FacMotivodevolucionSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDMOTIVO", property="idmotivo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="CODIGO", property="codigo", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR)
    })
    List<FacMotivodevolucion> selectByExample(FacMotivodevolucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @Select({
        "select",
        "IDMOTIVO, CODIGO, NOMBRE",
        "from FAC_MOTIVODEVOLUCION",
        "where IDMOTIVO = #{idmotivo,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDMOTIVO", property="idmotivo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="CODIGO", property="codigo", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR)
    })
    FacMotivodevolucion selectByPrimaryKey(Short idmotivo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @UpdateProvider(type=FacMotivodevolucionSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FacMotivodevolucion record, @Param("example") FacMotivodevolucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @UpdateProvider(type=FacMotivodevolucionSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FacMotivodevolucion record, @Param("example") FacMotivodevolucionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @UpdateProvider(type=FacMotivodevolucionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FacMotivodevolucion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FAC_MOTIVODEVOLUCION
     *
     * @mbg.generated Mon Dec 20 14:40:35 CET 2021
     */
    @Update({
        "update FAC_MOTIVODEVOLUCION",
        "set CODIGO = #{codigo,jdbcType=VARCHAR},",
          "NOMBRE = #{nombre,jdbcType=VARCHAR}",
        "where IDMOTIVO = #{idmotivo,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FacMotivodevolucion record);
}