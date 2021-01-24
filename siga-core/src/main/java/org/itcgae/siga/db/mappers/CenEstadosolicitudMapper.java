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
import org.itcgae.siga.db.entities.CenEstadosolicitud;
import org.itcgae.siga.db.entities.CenEstadosolicitudExample;

public interface CenEstadosolicitudMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenEstadosolicitudSqlProvider.class, method = "countByExample")
	long countByExample(CenEstadosolicitudExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenEstadosolicitudSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenEstadosolicitudExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_ESTADOSOLICITUD", "where IDESTADO = #{idestado,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idestado);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_ESTADOSOLICITUD (IDESTADO, DESCRIPCION, ", "FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idestado,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CenEstadosolicitud record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenEstadosolicitudSqlProvider.class, method = "insertSelective")
	int insertSelective(CenEstadosolicitud record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenEstadosolicitudSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenEstadosolicitud> selectByExample(CenEstadosolicitudExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDESTADO, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION", "from CEN_ESTADOSOLICITUD",
			"where IDESTADO = #{idestado,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenEstadosolicitud selectByPrimaryKey(Short idestado);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenEstadosolicitudSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenEstadosolicitud record,
			@Param("example") CenEstadosolicitudExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenEstadosolicitudSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenEstadosolicitud record,
			@Param("example") CenEstadosolicitudExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenEstadosolicitudSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenEstadosolicitud record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ESTADOSOLICITUD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_ESTADOSOLICITUD", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}", "where IDESTADO = #{idestado,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenEstadosolicitud record);
}