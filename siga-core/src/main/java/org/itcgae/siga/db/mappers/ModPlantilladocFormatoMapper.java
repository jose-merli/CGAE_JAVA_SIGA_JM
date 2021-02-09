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
import org.itcgae.siga.db.entities.ModPlantilladocFormato;
import org.itcgae.siga.db.entities.ModPlantilladocFormatoExample;

public interface ModPlantilladocFormatoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @SelectProvider(type=ModPlantilladocFormatoSqlProvider.class, method="countByExample")
    long countByExample(ModPlantilladocFormatoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @DeleteProvider(type=ModPlantilladocFormatoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ModPlantilladocFormatoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Delete({
        "delete from MOD_PLANTILLADOC_FORMATO",
        "where IDFORMATOSALIDA = #{idformatosalida,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idformatosalida);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Insert({
        "insert into MOD_PLANTILLADOC_FORMATO (IDFORMATOSALIDA, NOMBRE, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idformatosalida,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ModPlantilladocFormato record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @InsertProvider(type=ModPlantilladocFormatoSqlProvider.class, method="insertSelective")
    int insertSelective(ModPlantilladocFormato record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @SelectProvider(type=ModPlantilladocFormatoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDFORMATOSALIDA", property="idformatosalida", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ModPlantilladocFormato> selectByExample(ModPlantilladocFormatoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Select({
        "select",
        "IDFORMATOSALIDA, NOMBRE, FECHAMODIFICACION, USUMODIFICACION",
        "from MOD_PLANTILLADOC_FORMATO",
        "where IDFORMATOSALIDA = #{idformatosalida,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDFORMATOSALIDA", property="idformatosalida", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ModPlantilladocFormato selectByPrimaryKey(Short idformatosalida);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @UpdateProvider(type=ModPlantilladocFormatoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ModPlantilladocFormato record, @Param("example") ModPlantilladocFormatoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @UpdateProvider(type=ModPlantilladocFormatoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ModPlantilladocFormato record, @Param("example") ModPlantilladocFormatoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @UpdateProvider(type=ModPlantilladocFormatoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ModPlantilladocFormato record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_FORMATO
     *
     * @mbg.generated Wed Jan 16 09:26:57 CET 2019
     */
    @Update({
        "update MOD_PLANTILLADOC_FORMATO",
        "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDFORMATOSALIDA = #{idformatosalida,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ModPlantilladocFormato record);
}