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
import org.itcgae.siga.db.entities.EnvCamposplantilla;
import org.itcgae.siga.db.entities.EnvCamposplantillaExample;
import org.itcgae.siga.db.entities.EnvCamposplantillaKey;

public interface EnvCamposplantillaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@SelectProvider(type = EnvCamposplantillaSqlProvider.class, method = "countByExample")
	long countByExample(EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@DeleteProvider(type = EnvCamposplantillaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Delete({ "delete from ENV_CAMPOSPLANTILLA", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDCAMPO = #{idcampo,jdbcType=DECIMAL}", "and TIPOCAMPO = #{tipocampo,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(EnvCamposplantillaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Insert({ "insert into ENV_CAMPOSPLANTILLA (IDINSTITUCION, IDTIPOENVIOS, ", "IDPLANTILLAENVIOS, IDCAMPO, ",
			"TIPOCAMPO, FECHAMODIFICACION, ", "USUMODIFICACION, IDFORMATO, ", "VALOR)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoenvios,jdbcType=DECIMAL}, ",
			"#{idplantillaenvios,jdbcType=DECIMAL}, #{idcampo,jdbcType=DECIMAL}, ",
			"#{tipocampo,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{idformato,jdbcType=DECIMAL}, ", "#{valor,jdbcType=CLOB})" })
	@SelectKey(statement = "SELECT SEQ_ENV_CAMPOSPLANTILLA.NEXTVAL FROM DUAL", keyProperty = "idcampo", before = true, resultType = Short.class)
	int insert(EnvCamposplantilla record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@InsertProvider(type = EnvCamposplantillaSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_ENV_CAMPOSPLANTILLA.NEXTVAL FROM DUAL", keyProperty = "idcampo", before = true, resultType = Short.class)
	int insertSelective(EnvCamposplantilla record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@SelectProvider(type = EnvCamposplantillaSqlProvider.class, method = "selectByExampleWithBLOBs")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLAENVIOS", property = "idplantillaenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCAMPO", property = "idcampo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "TIPOCAMPO", property = "tipocampo", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFORMATO", property = "idformato", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.CLOB) })
	List<EnvCamposplantilla> selectByExampleWithBLOBs(EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@SelectProvider(type = EnvCamposplantillaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLAENVIOS", property = "idplantillaenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCAMPO", property = "idcampo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "TIPOCAMPO", property = "tipocampo", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFORMATO", property = "idformato", jdbcType = JdbcType.DECIMAL) })
	List<EnvCamposplantilla> selectByExample(EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDTIPOENVIOS, IDPLANTILLAENVIOS, IDCAMPO, TIPOCAMPO, FECHAMODIFICACION, ",
			"USUMODIFICACION, IDFORMATO, VALOR", "from ENV_CAMPOSPLANTILLA",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDCAMPO = #{idcampo,jdbcType=DECIMAL}", "and TIPOCAMPO = #{tipocampo,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLAENVIOS", property = "idplantillaenvios", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCAMPO", property = "idcampo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "TIPOCAMPO", property = "tipocampo", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFORMATO", property = "idformato", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALOR", property = "valor", jdbcType = JdbcType.CLOB) })
	EnvCamposplantilla selectByPrimaryKey(EnvCamposplantillaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = EnvCamposplantillaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") EnvCamposplantilla record,
			@Param("example") EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = EnvCamposplantillaSqlProvider.class, method = "updateByExampleWithBLOBs")
	int updateByExampleWithBLOBs(@Param("record") EnvCamposplantilla record,
			@Param("example") EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = EnvCamposplantillaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") EnvCamposplantilla record,
			@Param("example") EnvCamposplantillaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@UpdateProvider(type = EnvCamposplantillaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(EnvCamposplantilla record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Update({ "update ENV_CAMPOSPLANTILLA", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "IDFORMATO = #{idformato,jdbcType=DECIMAL},",
			"VALOR = #{valor,jdbcType=CLOB}", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDCAMPO = #{idcampo,jdbcType=DECIMAL}", "and TIPOCAMPO = #{tipocampo,jdbcType=VARCHAR}" })
	int updateByPrimaryKeyWithBLOBs(EnvCamposplantilla record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_CAMPOSPLANTILLA
	 * @mbg.generated  Tue Jan 22 10:10:11 CET 2019
	 */
	@Update({ "update ENV_CAMPOSPLANTILLA", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "IDFORMATO = #{idformato,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
			"and IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}",
			"and IDCAMPO = #{idcampo,jdbcType=DECIMAL}", "and TIPOCAMPO = #{tipocampo,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(EnvCamposplantilla record);
}