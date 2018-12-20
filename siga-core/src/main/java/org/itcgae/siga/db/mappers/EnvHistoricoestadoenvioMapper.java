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
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvio;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvioExample;
import org.itcgae.siga.db.entities.EnvHistoricoestadoenvioKey;

public interface EnvHistoricoestadoenvioMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@SelectProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "countByExample")
	long countByExample(EnvHistoricoestadoenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@DeleteProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "deleteByExample")
	int deleteByExample(EnvHistoricoestadoenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@Delete({ "delete from ENV_HISTORICOESTADOENVIO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDENVIO = #{idenvio,jdbcType=DECIMAL}", "and IDHISTORICO = #{idhistorico,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(EnvHistoricoestadoenvioKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@Insert({ "insert into ENV_HISTORICOESTADOENVIO (IDINSTITUCION, IDENVIO, ", "IDHISTORICO, IDESTADO, ",
			"FECHAESTADO, FECHAMODIFICACION, ", "USUMODIFICACION)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idenvio,jdbcType=DECIMAL}, ",
			"#{idhistorico,jdbcType=DECIMAL}, #{idestado,jdbcType=DECIMAL}, ",
			"#{fechaestado,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL})" })
	@SelectKey(statement = "SELECT SEQ_ENV_HISTORICOESTADOENVIO.NEXTVAL FROM DUAL", keyProperty = "idhistorico", before = true, resultType = Short.class)
	int insert(EnvHistoricoestadoenvio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@InsertProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_ENV_HISTORICOESTADOENVIO.NEXTVAL FROM DUAL", keyProperty = "idhistorico", before = true, resultType = Short.class)
	int insertSelective(EnvHistoricoestadoenvio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@SelectProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDHISTORICO", property = "idhistorico", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<EnvHistoricoestadoenvio> selectByExample(EnvHistoricoestadoenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDENVIO, IDHISTORICO, IDESTADO, FECHAESTADO, FECHAMODIFICACION, ",
			"USUMODIFICACION", "from ENV_HISTORICOESTADOENVIO",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDENVIO = #{idenvio,jdbcType=DECIMAL}",
			"and IDHISTORICO = #{idhistorico,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDHISTORICO", property = "idhistorico", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	EnvHistoricoestadoenvio selectByPrimaryKey(EnvHistoricoestadoenvioKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@UpdateProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") EnvHistoricoestadoenvio record,
			@Param("example") EnvHistoricoestadoenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@UpdateProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") EnvHistoricoestadoenvio record,
			@Param("example") EnvHistoricoestadoenvioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@UpdateProvider(type = EnvHistoricoestadoenvioSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(EnvHistoricoestadoenvio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ENV_HISTORICOESTADOENVIO
	 * @mbg.generated  Thu Dec 20 11:03:46 CET 2018
	 */
	@Update({ "update ENV_HISTORICOESTADOENVIO", "set IDESTADO = #{idestado,jdbcType=DECIMAL},",
			"FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDENVIO = #{idenvio,jdbcType=DECIMAL}",
			"and IDHISTORICO = #{idhistorico,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(EnvHistoricoestadoenvio record);
}