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
import org.itcgae.siga.db.entities.AdmTiposacceso;
import org.itcgae.siga.db.entities.AdmTiposaccesoExample;
import org.itcgae.siga.db.entities.AdmTiposaccesoKey;

public interface AdmTiposaccesoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmTiposaccesoSqlProvider.class, method = "countByExample")
	long countByExample(AdmTiposaccesoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = AdmTiposaccesoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AdmTiposaccesoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from ADM_TIPOSACCESO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCESO = #{idproceso,jdbcType=VARCHAR}", "and IDPERFIL = #{idperfil,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(AdmTiposaccesoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into ADM_TIPOSACCESO (IDINSTITUCION, IDPROCESO, ", "IDPERFIL, FECHAMODIFICACION, ",
			"USUMODIFICACION, DERECHOACCESO)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idproceso,jdbcType=VARCHAR}, ",
			"#{idperfil,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{derechoacceso,jdbcType=DECIMAL})" })
	int insert(AdmTiposacceso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = AdmTiposaccesoSqlProvider.class, method = "insertSelective")
	int insertSelective(AdmTiposacceso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmTiposaccesoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPROCESO", property = "idproceso", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DERECHOACCESO", property = "derechoacceso", jdbcType = JdbcType.DECIMAL) })
	List<AdmTiposacceso> selectByExample(AdmTiposaccesoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDPROCESO, IDPERFIL, FECHAMODIFICACION, USUMODIFICACION, DERECHOACCESO",
			"from ADM_TIPOSACCESO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCESO = #{idproceso,jdbcType=VARCHAR}", "and IDPERFIL = #{idperfil,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPROCESO", property = "idproceso", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DERECHOACCESO", property = "derechoacceso", jdbcType = JdbcType.DECIMAL) })
	AdmTiposacceso selectByPrimaryKey(AdmTiposaccesoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmTiposaccesoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AdmTiposacceso record,
			@Param("example") AdmTiposaccesoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmTiposaccesoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AdmTiposacceso record, @Param("example") AdmTiposaccesoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmTiposaccesoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(AdmTiposacceso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_TIPOSACCESO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update ADM_TIPOSACCESO", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"DERECHOACCESO = #{derechoacceso,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDPROCESO = #{idproceso,jdbcType=VARCHAR}",
			"and IDPERFIL = #{idperfil,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(AdmTiposacceso record);
}