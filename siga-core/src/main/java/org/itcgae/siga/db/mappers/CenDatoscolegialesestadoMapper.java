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
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoExample;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoKey;

public interface CenDatoscolegialesestadoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@SelectProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "countByExample")
	long countByExample(CenDatoscolegialesestadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@DeleteProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenDatoscolegialesestadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@Delete({ "delete from CEN_DATOSCOLEGIALESESTADO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}", "and FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP}" })
	int deleteByPrimaryKey(CenDatoscolegialesestadoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@Insert({ "insert into CEN_DATOSCOLEGIALESESTADO (IDINSTITUCION, IDPERSONA, ", "FECHAESTADO, IDESTADO, ",
			"FECHAMODIFICACION, USUMODIFICACION, ", "OBSERVACIONES, SITUACIONRESIDENTE, ", "NUM_EXPEDIENTE)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{fechaestado,jdbcType=TIMESTAMP}, #{idestado,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{observaciones,jdbcType=VARCHAR}, #{situacionresidente,jdbcType=VARCHAR}, ",
			"#{numExpediente,jdbcType=VARCHAR})" })
	int insert(CenDatoscolegialesestado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@InsertProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "insertSelective")
	int insertSelective(CenDatoscolegialesestado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@SelectProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP, id = true),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONRESIDENTE", property = "situacionresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_EXPEDIENTE", property = "numExpediente", jdbcType = JdbcType.VARCHAR) })
	List<CenDatoscolegialesestado> selectByExample(CenDatoscolegialesestadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@Select({ "select", "IDINSTITUCION, IDPERSONA, FECHAESTADO, IDESTADO, FECHAMODIFICACION, USUMODIFICACION, ",
			"OBSERVACIONES, SITUACIONRESIDENTE, NUM_EXPEDIENTE", "from CEN_DATOSCOLEGIALESESTADO",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP, id = true),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONRESIDENTE", property = "situacionresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_EXPEDIENTE", property = "numExpediente", jdbcType = JdbcType.VARCHAR) })
	CenDatoscolegialesestado selectByPrimaryKey(CenDatoscolegialesestadoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@UpdateProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenDatoscolegialesestado record,
			@Param("example") CenDatoscolegialesestadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@UpdateProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenDatoscolegialesestado record,
			@Param("example") CenDatoscolegialesestadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@UpdateProvider(type = CenDatoscolegialesestadoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenDatoscolegialesestado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_DATOSCOLEGIALESESTADO
	 * @mbg.generated  Fri Nov 19 13:08:35 CET 2021
	 */
	@Update({ "update CEN_DATOSCOLEGIALESESTADO", "set IDESTADO = #{idestado,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
			"SITUACIONRESIDENTE = #{situacionresidente,jdbcType=VARCHAR},",
			"NUM_EXPEDIENTE = #{numExpediente,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP}" })
	int updateByPrimaryKey(CenDatoscolegialesestado record);
}