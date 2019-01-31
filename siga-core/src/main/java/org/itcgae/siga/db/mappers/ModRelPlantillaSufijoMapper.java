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
import org.itcgae.siga.db.entities.ModRelPlantillaSufijo;
import org.itcgae.siga.db.entities.ModRelPlantillaSufijoExample;

public interface ModRelPlantillaSufijoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@SelectProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "countByExample")
	long countByExample(ModRelPlantillaSufijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@DeleteProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ModRelPlantillaSufijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@Delete({ "delete from MOD_REL_PLANTILLA_SUFIJO",
			"where IDPLANTILLASUFIJO = #{idplantillasufijo,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idplantillasufijo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@Insert({ "insert into MOD_REL_PLANTILLA_SUFIJO (IDPLANTILLASUFIJO, IDMODELOCOMUNICACION, ",
			"IDPLANTILLADOCUMENTO, IDINFORME, ", "IDSUFIJO, ORDEN, ", "USUMODIFICACION, FECHAMODIFICACION)",
			"values (#{idplantillasufijo,jdbcType=DECIMAL}, #{idmodelocomunicacion,jdbcType=DECIMAL}, ",
			"#{idplantilladocumento,jdbcType=DECIMAL}, #{idinforme,jdbcType=DECIMAL}, ",
			"#{idsufijo,jdbcType=DECIMAL}, #{orden,jdbcType=DECIMAL}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP})" })
	@SelectKey(statement = "SELECT SEQ_MOD_REL_PLANTILLA_SUFIJO.NEXTVAL FROM DUAL", keyProperty = "idplantillasufijo", before = true, resultType = Long.class)
	int insert(ModRelPlantillaSufijo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@InsertProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_MOD_REL_PLANTILLA_SUFIJO.NEXTVAL FROM DUAL", keyProperty = "idplantillasufijo", before = true, resultType = Long.class)
	int insertSelective(ModRelPlantillaSufijo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@SelectProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDPLANTILLASUFIJO", property = "idplantillasufijo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDMODELOCOMUNICACION", property = "idmodelocomunicacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPLANTILLADOCUMENTO", property = "idplantilladocumento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINFORME", property = "idinforme", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUFIJO", property = "idsufijo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	List<ModRelPlantillaSufijo> selectByExample(ModRelPlantillaSufijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@Select({ "select", "IDPLANTILLASUFIJO, IDMODELOCOMUNICACION, IDPLANTILLADOCUMENTO, IDINFORME, IDSUFIJO, ",
			"ORDEN, USUMODIFICACION, FECHAMODIFICACION", "from MOD_REL_PLANTILLA_SUFIJO",
			"where IDPLANTILLASUFIJO = #{idplantillasufijo,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDPLANTILLASUFIJO", property = "idplantillasufijo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDMODELOCOMUNICACION", property = "idmodelocomunicacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPLANTILLADOCUMENTO", property = "idplantilladocumento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINFORME", property = "idinforme", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUFIJO", property = "idsufijo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	ModRelPlantillaSufijo selectByPrimaryKey(Long idplantillasufijo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@UpdateProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ModRelPlantillaSufijo record,
			@Param("example") ModRelPlantillaSufijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@UpdateProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ModRelPlantillaSufijo record,
			@Param("example") ModRelPlantillaSufijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@UpdateProvider(type = ModRelPlantillaSufijoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ModRelPlantillaSufijo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_REL_PLANTILLA_SUFIJO
	 * @mbg.generated  Fri Jan 18 10:33:59 CET 2019
	 */
	@Update({ "update MOD_REL_PLANTILLA_SUFIJO", "set IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL},",
			"IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL},",
			"IDINFORME = #{idinforme,jdbcType=DECIMAL},", "IDSUFIJO = #{idsufijo,jdbcType=DECIMAL},",
			"ORDEN = #{orden,jdbcType=DECIMAL},", "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
			"where IDPLANTILLASUFIJO = #{idplantillasufijo,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ModRelPlantillaSufijo record);
}