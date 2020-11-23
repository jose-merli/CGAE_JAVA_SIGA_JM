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
import org.itcgae.siga.db.entities.CenTipocolegiacion;
import org.itcgae.siga.db.entities.CenTipocolegiacionExample;

public interface CenTipocolegiacionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTipocolegiacionSqlProvider.class, method = "countByExample")
	long countByExample(CenTipocolegiacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenTipocolegiacionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTipocolegiacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_TIPOCOLEGIACION", "where IDTIPOCOLEGIACION = #{idtipocolegiacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idtipocolegiacion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_TIPOCOLEGIACION (IDTIPOCOLEGIACION, DESCRIPCION, ",
			"FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idtipocolegiacion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CenTipocolegiacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenTipocolegiacionSqlProvider.class, method = "insertSelective")
	int insertSelective(CenTipocolegiacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTipocolegiacionSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDTIPOCOLEGIACION", property = "idtipocolegiacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenTipocolegiacion> selectByExample(CenTipocolegiacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDTIPOCOLEGIACION, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION",
			"from CEN_TIPOCOLEGIACION", "where IDTIPOCOLEGIACION = #{idtipocolegiacion,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDTIPOCOLEGIACION", property = "idtipocolegiacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenTipocolegiacion selectByPrimaryKey(Short idtipocolegiacion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipocolegiacionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTipocolegiacion record,
			@Param("example") CenTipocolegiacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipocolegiacionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTipocolegiacion record,
			@Param("example") CenTipocolegiacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipocolegiacionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTipocolegiacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_TIPOCOLEGIACION", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDTIPOCOLEGIACION = #{idtipocolegiacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTipocolegiacion record);
}