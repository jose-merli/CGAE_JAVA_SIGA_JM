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
import org.itcgae.siga.db.entities.EcomOperacion;
import org.itcgae.siga.db.entities.EcomOperacionExample;

public interface EcomOperacionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@SelectProvider(type = EcomOperacionSqlProvider.class, method = "countByExample")
	long countByExample(EcomOperacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@DeleteProvider(type = EcomOperacionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(EcomOperacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@Delete({ "delete from ECOM_OPERACION", "where IDOPERACION = #{idoperacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Integer idoperacion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@Insert({ "insert into ECOM_OPERACION (IDOPERACION, IDSERVICIO, ", "NOMBRE, MAXREINTENTOS, ",
			"ACTIVO, FECHAMODIFICACION, ", "USUMODIFICACION)",
			"values (#{idoperacion,jdbcType=DECIMAL}, #{idservicio,jdbcType=DECIMAL}, ",
			"#{nombre,jdbcType=VARCHAR}, #{maxreintentos,jdbcType=DECIMAL}, ",
			"#{activo,jdbcType=CHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL})" })
	int insert(EcomOperacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@InsertProvider(type = EcomOperacionSqlProvider.class, method = "insertSelective")
	int insertSelective(EcomOperacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@SelectProvider(type = EcomOperacionSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDOPERACION", property = "idoperacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MAXREINTENTOS", property = "maxreintentos", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ACTIVO", property = "activo", jdbcType = JdbcType.CHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<EcomOperacion> selectByExample(EcomOperacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@Select({ "select", "IDOPERACION, IDSERVICIO, NOMBRE, MAXREINTENTOS, ACTIVO, FECHAMODIFICACION, USUMODIFICACION",
			"from ECOM_OPERACION", "where IDOPERACION = #{idoperacion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDOPERACION", property = "idoperacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MAXREINTENTOS", property = "maxreintentos", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ACTIVO", property = "activo", jdbcType = JdbcType.CHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	EcomOperacion selectByPrimaryKey(Integer idoperacion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@UpdateProvider(type = EcomOperacionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") EcomOperacion record, @Param("example") EcomOperacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@UpdateProvider(type = EcomOperacionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") EcomOperacion record, @Param("example") EcomOperacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@UpdateProvider(type = EcomOperacionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(EcomOperacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ECOM_OPERACION
	 * @mbg.generated  Mon Oct 21 18:09:21 CEST 2019
	 */
	@Update({ "update ECOM_OPERACION", "set IDSERVICIO = #{idservicio,jdbcType=DECIMAL},",
			"NOMBRE = #{nombre,jdbcType=VARCHAR},", "MAXREINTENTOS = #{maxreintentos,jdbcType=DECIMAL},",
			"ACTIVO = #{activo,jdbcType=CHAR},", "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDOPERACION = #{idoperacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(EcomOperacion record);
}