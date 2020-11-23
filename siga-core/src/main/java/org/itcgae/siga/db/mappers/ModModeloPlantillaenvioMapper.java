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
import org.itcgae.siga.db.entities.ModModeloPlantillaenvio;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioExample;
import org.itcgae.siga.db.entities.ModModeloPlantillaenvioKey;

public interface ModModeloPlantillaenvioMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@SelectProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "countByExample")
	long countByExample(ModModeloPlantillaenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@DeleteProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ModModeloPlantillaenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Delete({ "delete from MOD_MODELO_PLANTILLAENVIO",
			"where IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(ModModeloPlantillaenvioKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Insert({ "insert into MOD_MODELO_PLANTILLAENVIO (IDMODELOCOMUNICACION, IDPLANTILLAENVIOS, ",
			"IDINSTITUCION, IDTIPOENVIOS, ", "FECHAMODIFICACION, USUMODIFICACION, ", "PORDEFECTO, FECHABAJA)",
			"values (#{idmodelocomunicacion,jdbcType=DECIMAL}, #{idplantillaenvios,jdbcType=DECIMAL}, ",
			"#{idinstitucion,jdbcType=DECIMAL}, #{idtipoenvios,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{pordefecto,jdbcType=VARCHAR}, #{fechabaja,jdbcType=TIMESTAMP})" })
	int insert(ModModeloPlantillaenvio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@InsertProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "insertSelective")
	int insertSelective(ModModeloPlantillaenvio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@SelectProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDMODELOCOMUNICACION", property = "idmodelocomunicacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLAENVIOS", property = "idplantillaenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PORDEFECTO", property = "pordefecto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<ModModeloPlantillaenvio> selectByExample(ModModeloPlantillaenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Select({ "select", "IDMODELOCOMUNICACION, IDPLANTILLAENVIOS, IDINSTITUCION, IDTIPOENVIOS, FECHAMODIFICACION, ",
			"USUMODIFICACION, PORDEFECTO, FECHABAJA", "from MOD_MODELO_PLANTILLAENVIO",
			"where IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDMODELOCOMUNICACION", property = "idmodelocomunicacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLAENVIOS", property = "idplantillaenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PORDEFECTO", property = "pordefecto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	ModModeloPlantillaenvio selectByPrimaryKey(ModModeloPlantillaenvioKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ModModeloPlantillaenvio record,
			@Param("example") ModModeloPlantillaenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ModModeloPlantillaenvio record,
			@Param("example") ModModeloPlantillaenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = ModModeloPlantillaenvioSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ModModeloPlantillaenvio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLAENVIO
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Update({ "update MOD_MODELO_PLANTILLAENVIO", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "PORDEFECTO = #{pordefecto,jdbcType=VARCHAR},",
			"FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
			"where IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ModModeloPlantillaenvio record);
}