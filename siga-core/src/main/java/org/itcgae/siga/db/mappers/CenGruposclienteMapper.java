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
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteExample;
import org.itcgae.siga.db.entities.CenGruposclienteKey;

public interface CenGruposclienteMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenGruposclienteSqlProvider.class, method = "countByExample")
	long countByExample(CenGruposclienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenGruposclienteSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenGruposclienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_GRUPOSCLIENTE", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDGRUPO = #{idgrupo,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenGruposclienteKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_GRUPOSCLIENTE (IDINSTITUCION, IDGRUPO, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"NOMBRE)", "values (#{idinstitucion,jdbcType=DECIMAL}, #{idgrupo,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{nombre,jdbcType=VARCHAR})" })
	int insert(CenGruposcliente record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenGruposclienteSqlProvider.class, method = "insertSelective")
	int insertSelective(CenGruposcliente record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenGruposclienteSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDGRUPO", property = "idgrupo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR) })
	List<CenGruposcliente> selectByExample(CenGruposclienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDGRUPO, FECHAMODIFICACION, USUMODIFICACION, NOMBRE", "from CEN_GRUPOSCLIENTE",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDGRUPO = #{idgrupo,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDGRUPO", property = "idgrupo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR) })
	CenGruposcliente selectByPrimaryKey(CenGruposclienteKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenGruposclienteSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenGruposcliente record,
			@Param("example") CenGruposclienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenGruposclienteSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenGruposcliente record, @Param("example") CenGruposclienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenGruposclienteSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenGruposcliente record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_GRUPOSCLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_GRUPOSCLIENTE", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "NOMBRE = #{nombre,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDGRUPO = #{idgrupo,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenGruposcliente record);
}