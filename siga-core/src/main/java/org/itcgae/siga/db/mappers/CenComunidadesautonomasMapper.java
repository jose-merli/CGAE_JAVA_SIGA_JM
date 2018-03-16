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
import org.itcgae.siga.db.entities.CenComunidadesautonomas;
import org.itcgae.siga.db.entities.CenComunidadesautonomasExample;

public interface CenComunidadesautonomasMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenComunidadesautonomasSqlProvider.class, method = "countByExample")
	long countByExample(CenComunidadesautonomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenComunidadesautonomasSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenComunidadesautonomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_COMUNIDADESAUTONOMAS",
			"where IDCOMUNIDADAUTONOMA = #{idcomunidadautonoma,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String idcomunidadautonoma);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_COMUNIDADESAUTONOMAS (IDCOMUNIDADAUTONOMA, DESCRIPCION, ",
			"FECHAMODIFICACION, USUMODIFICACION, ", "CODIGOEXT, BLOQUEADO)",
			"values (#{idcomunidadautonoma,jdbcType=VARCHAR}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR})" })
	int insert(CenComunidadesautonomas record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenComunidadesautonomasSqlProvider.class, method = "insertSelective")
	int insertSelective(CenComunidadesautonomas record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenComunidadesautonomasSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDCOMUNIDADAUTONOMA", property = "idcomunidadautonoma", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR) })
	List<CenComunidadesautonomas> selectByExample(CenComunidadesautonomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDCOMUNIDADAUTONOMA, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, ",
			"BLOQUEADO", "from CEN_COMUNIDADESAUTONOMAS",
			"where IDCOMUNIDADAUTONOMA = #{idcomunidadautonoma,jdbcType=VARCHAR}" })
	@Results({
			@Result(column = "IDCOMUNIDADAUTONOMA", property = "idcomunidadautonoma", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR) })
	CenComunidadesautonomas selectByPrimaryKey(String idcomunidadautonoma);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenComunidadesautonomasSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenComunidadesautonomas record,
			@Param("example") CenComunidadesautonomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenComunidadesautonomasSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenComunidadesautonomas record,
			@Param("example") CenComunidadesautonomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenComunidadesautonomasSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenComunidadesautonomas record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COMUNIDADESAUTONOMAS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_COMUNIDADESAUTONOMAS", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"BLOQUEADO = #{bloqueado,jdbcType=CHAR}",
			"where IDCOMUNIDADAUTONOMA = #{idcomunidadautonoma,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(CenComunidadesautonomas record);
}