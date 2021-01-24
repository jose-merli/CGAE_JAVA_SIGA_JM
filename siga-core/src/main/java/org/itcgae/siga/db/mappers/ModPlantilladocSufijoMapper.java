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
import org.itcgae.siga.db.entities.ModPlantilladocSufijo;
import org.itcgae.siga.db.entities.ModPlantilladocSufijoExample;

public interface ModPlantilladocSufijoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @SelectProvider(type=ModPlantilladocSufijoSqlProvider.class, method="countByExample")
    long countByExample(ModPlantilladocSufijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @DeleteProvider(type=ModPlantilladocSufijoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ModPlantilladocSufijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Delete({
        "delete from MOD_PLANTILLADOC_SUFIJO",
        "where IDSUFIJO = #{idsufijo,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idsufijo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Insert({
        "insert into MOD_PLANTILLADOC_SUFIJO (IDSUFIJO, NOMBRE, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idsufijo,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ModPlantilladocSufijo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @InsertProvider(type=ModPlantilladocSufijoSqlProvider.class, method="insertSelective")
    int insertSelective(ModPlantilladocSufijo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @SelectProvider(type=ModPlantilladocSufijoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDSUFIJO", property="idsufijo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ModPlantilladocSufijo> selectByExample(ModPlantilladocSufijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Select({
        "select",
        "IDSUFIJO, NOMBRE, FECHAMODIFICACION, USUMODIFICACION",
        "from MOD_PLANTILLADOC_SUFIJO",
        "where IDSUFIJO = #{idsufijo,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDSUFIJO", property="idsufijo", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ModPlantilladocSufijo selectByPrimaryKey(Short idsufijo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @UpdateProvider(type=ModPlantilladocSufijoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ModPlantilladocSufijo record, @Param("example") ModPlantilladocSufijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @UpdateProvider(type=ModPlantilladocSufijoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ModPlantilladocSufijo record, @Param("example") ModPlantilladocSufijoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @UpdateProvider(type=ModPlantilladocSufijoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ModPlantilladocSufijo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_SUFIJO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Update({
        "update MOD_PLANTILLADOC_SUFIJO",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDSUFIJO = #{idsufijo,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ModPlantilladocSufijo record);
}