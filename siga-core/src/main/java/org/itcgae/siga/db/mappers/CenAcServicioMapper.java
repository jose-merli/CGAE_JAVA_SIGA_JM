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
import org.itcgae.siga.db.entities.CenAcServicio;
import org.itcgae.siga.db.entities.CenAcServicioExample;

public interface CenAcServicioMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAcServicioSqlProvider.class, method = "countByExample")
	long countByExample(CenAcServicioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenAcServicioSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenAcServicioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_AC_SERVICIO", "where IDCENACSERVICIO = #{idcenacservicio,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idcenacservicio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_AC_SERVICIO (IDCENACSERVICIO, NOMBRE, ", "ACTIVO, USUMODIFICACION, ",
			"FECHAMODIFICACION)", "values (#{idcenacservicio,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
			"#{activo,jdbcType=DECIMAL}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP})" })
	int insert(CenAcServicio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenAcServicioSqlProvider.class, method = "insertSelective")
	int insertSelective(CenAcServicio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAcServicioSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDCENACSERVICIO", property = "idcenacservicio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVO", property = "activo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	List<CenAcServicio> selectByExample(CenAcServicioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDCENACSERVICIO, NOMBRE, ACTIVO, USUMODIFICACION, FECHAMODIFICACION", "from CEN_AC_SERVICIO",
			"where IDCENACSERVICIO = #{idcenacservicio,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDCENACSERVICIO", property = "idcenacservicio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVO", property = "activo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	CenAcServicio selectByPrimaryKey(Short idcenacservicio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcServicioSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenAcServicio record, @Param("example") CenAcServicioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcServicioSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenAcServicio record, @Param("example") CenAcServicioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcServicioSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenAcServicio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_SERVICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_AC_SERVICIO", "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"ACTIVO = #{activo,jdbcType=DECIMAL},", "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
			"where IDCENACSERVICIO = #{idcenacservicio,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenAcServicio record);
}