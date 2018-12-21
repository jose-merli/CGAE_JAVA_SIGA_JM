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
import org.itcgae.siga.db.entities.ModPlantilladocConsulta;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaExample;
import org.itcgae.siga.db.entities.ModPlantilladocConsultaKey;

public interface ModPlantilladocConsultaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @SelectProvider(type=ModPlantilladocConsultaSqlProvider.class, method="countByExample")
    long countByExample(ModPlantilladocConsultaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @DeleteProvider(type=ModPlantilladocConsultaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ModPlantilladocConsultaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @Delete({
        "delete from MOD_PLANTILLADOC_CONSULTA",
        "where IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL}",
          "and IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ModPlantilladocConsultaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @Insert({
        "insert into MOD_PLANTILLADOC_CONSULTA (IDPLANTILLADOCUMENTO, IDCONSULTA, ",
        "IDINSTITUCION, IDMODELOCOMUNICACION, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "FECHABAJA)",
        "values (#{idplantilladocumento,jdbcType=DECIMAL}, #{idconsulta,jdbcType=DECIMAL}, ",
        "#{idinstitucion,jdbcType=DECIMAL}, #{idmodelocomunicacion,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{fechabaja,jdbcType=TIMESTAMP})"
    })
    int insert(ModPlantilladocConsulta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @InsertProvider(type=ModPlantilladocConsultaSqlProvider.class, method="insertSelective")
    int insertSelective(ModPlantilladocConsulta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @SelectProvider(type=ModPlantilladocConsultaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDPLANTILLADOCUMENTO", property="idplantilladocumento", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ModPlantilladocConsulta> selectByExample(ModPlantilladocConsultaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @Select({
        "select",
        "IDPLANTILLADOCUMENTO, IDCONSULTA, IDINSTITUCION, IDMODELOCOMUNICACION, FECHAMODIFICACION, ",
        "USUMODIFICACION, FECHABAJA",
        "from MOD_PLANTILLADOC_CONSULTA",
        "where IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL}",
          "and IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDPLANTILLADOCUMENTO", property="idplantilladocumento", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCONSULTA", property="idconsulta", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDMODELOCOMUNICACION", property="idmodelocomunicacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
    })
    ModPlantilladocConsulta selectByPrimaryKey(ModPlantilladocConsultaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @UpdateProvider(type=ModPlantilladocConsultaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ModPlantilladocConsulta record, @Param("example") ModPlantilladocConsultaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @UpdateProvider(type=ModPlantilladocConsultaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ModPlantilladocConsulta record, @Param("example") ModPlantilladocConsultaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @UpdateProvider(type=ModPlantilladocConsultaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ModPlantilladocConsulta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    @Update({
        "update MOD_PLANTILLADOC_CONSULTA",
        "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
        "where IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL}",
          "and IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ModPlantilladocConsulta record);
}