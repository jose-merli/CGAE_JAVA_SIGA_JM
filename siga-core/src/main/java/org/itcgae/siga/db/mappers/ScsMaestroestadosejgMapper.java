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
import org.itcgae.siga.db.entities.ScsMaestroestadosejg;
import org.itcgae.siga.db.entities.ScsMaestroestadosejgExample;

public interface ScsMaestroestadosejgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@SelectProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "countByExample")
	long countByExample(ScsMaestroestadosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@DeleteProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsMaestroestadosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@Delete({ "delete from SCS_MAESTROESTADOSEJG", "where IDESTADOEJG = #{idestadoejg,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idestadoejg);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@Insert({ "insert into SCS_MAESTROESTADOSEJG (IDESTADOEJG, DESCRIPCION, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"CODIGOEXT, BLOQUEADO, ", "ORDEN, VISIBLECOMISION, ", "CODIGOEJIS, FECHA_BAJA)",
			"values (#{idestadoejg,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ",
			"#{orden,jdbcType=DECIMAL}, #{visiblecomision,jdbcType=VARCHAR}, ",
			"#{codigoejis,jdbcType=VARCHAR}, #{fechaBaja,jdbcType=TIMESTAMP})" })
	int insert(ScsMaestroestadosejg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@InsertProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsMaestroestadosejg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@SelectProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDESTADOEJG", property = "idestadoejg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLECOMISION", property = "visiblecomision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEJIS", property = "codigoejis", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	List<ScsMaestroestadosejg> selectByExample(ScsMaestroestadosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@Select({ "select", "IDESTADOEJG, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, BLOQUEADO, ",
			"ORDEN, VISIBLECOMISION, CODIGOEJIS, FECHA_BAJA", "from SCS_MAESTROESTADOSEJG",
			"where IDESTADOEJG = #{idestadoejg,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDESTADOEJG", property = "idestadoejg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLECOMISION", property = "visiblecomision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEJIS", property = "codigoejis", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	ScsMaestroestadosejg selectByPrimaryKey(Short idestadoejg);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@UpdateProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsMaestroestadosejg record,
			@Param("example") ScsMaestroestadosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@UpdateProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsMaestroestadosejg record,
			@Param("example") ScsMaestroestadosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@UpdateProvider(type = ScsMaestroestadosejgSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsMaestroestadosejg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_MAESTROESTADOSEJG
	 * @mbg.generated  Thu Nov 28 17:25:42 CET 2019
	 */
	@Update({ "update SCS_MAESTROESTADOSEJG", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"BLOQUEADO = #{bloqueado,jdbcType=CHAR},", "ORDEN = #{orden,jdbcType=DECIMAL},",
			"VISIBLECOMISION = #{visiblecomision,jdbcType=VARCHAR},", "CODIGOEJIS = #{codigoejis,jdbcType=VARCHAR},",
			"FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}", "where IDESTADOEJG = #{idestadoejg,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsMaestroestadosejg record);
}