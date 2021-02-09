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
import org.itcgae.siga.db.entities.CenEstadoactividadpersona;
import org.itcgae.siga.db.entities.CenEstadoactividadpersonaExample;
import org.itcgae.siga.db.entities.CenEstadoactividadpersonaKey;

public interface CenEstadoactividadpersonaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "countByExample")
	long countByExample(CenEstadoactividadpersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenEstadoactividadpersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_ESTADOACTIVIDADPERSONA", "where IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and IDCODIGO = #{idcodigo,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenEstadoactividadpersonaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_ESTADOACTIVIDADPERSONA (IDPERSONA, IDCODIGO, ", "FECHAESTADO, IDESTADO, ",
			"MOTIVO, FECHAMODIFICACION, ", "USUMODIFICACION)",
			"values (#{idpersona,jdbcType=DECIMAL}, #{idcodigo,jdbcType=DECIMAL}, ",
			"#{fechaestado,jdbcType=TIMESTAMP}, #{idestado,jdbcType=DECIMAL}, ",
			"#{motivo,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CenEstadoactividadpersona record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "insertSelective")
	int insertSelective(CenEstadoactividadpersona record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCODIGO", property = "idcodigo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenEstadoactividadpersona> selectByExample(CenEstadoactividadpersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDPERSONA, IDCODIGO, FECHAESTADO, IDESTADO, MOTIVO, FECHAMODIFICACION, USUMODIFICACION",
			"from CEN_ESTADOACTIVIDADPERSONA", "where IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and IDCODIGO = #{idcodigo,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCODIGO", property = "idcodigo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenEstadoactividadpersona selectByPrimaryKey(CenEstadoactividadpersonaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenEstadoactividadpersona record,
			@Param("example") CenEstadoactividadpersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenEstadoactividadpersona record,
			@Param("example") CenEstadoactividadpersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenEstadoactividadpersonaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenEstadoactividadpersona record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOACTIVIDADPERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_ESTADOACTIVIDADPERSONA", "set FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP},",
			"IDESTADO = #{idestado,jdbcType=DECIMAL},", "MOTIVO = #{motivo,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}", "where IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and IDCODIGO = #{idcodigo,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenEstadoactividadpersona record);
}