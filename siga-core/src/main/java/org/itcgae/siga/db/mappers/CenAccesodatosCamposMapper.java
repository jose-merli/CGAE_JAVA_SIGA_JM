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
import org.itcgae.siga.db.entities.CenAccesodatosCampos;
import org.itcgae.siga.db.entities.CenAccesodatosCamposExample;
import org.itcgae.siga.db.entities.CenAccesodatosCamposKey;

public interface CenAccesodatosCamposMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAccesodatosCamposSqlProvider.class, method = "countByExample")
	long countByExample(CenAccesodatosCamposExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenAccesodatosCamposSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenAccesodatosCamposExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_ACCESODATOS_CAMPOS", "where CARACTER = #{caracter,jdbcType=VARCHAR}",
			"and IDCAMPOS = #{idcampos,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenAccesodatosCamposKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_ACCESODATOS_CAMPOS (CARACTER, IDCAMPOS, ", "FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{caracter,jdbcType=VARCHAR}, #{idcampos,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CenAccesodatosCampos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenAccesodatosCamposSqlProvider.class, method = "insertSelective")
	int insertSelective(CenAccesodatosCampos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAccesodatosCamposSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "CARACTER", property = "caracter", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDCAMPOS", property = "idcampos", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenAccesodatosCampos> selectByExample(CenAccesodatosCamposExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "CARACTER, IDCAMPOS, FECHAMODIFICACION, USUMODIFICACION", "from CEN_ACCESODATOS_CAMPOS",
			"where CARACTER = #{caracter,jdbcType=VARCHAR}", "and IDCAMPOS = #{idcampos,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "CARACTER", property = "caracter", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDCAMPOS", property = "idcampos", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenAccesodatosCampos selectByPrimaryKey(CenAccesodatosCamposKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAccesodatosCamposSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenAccesodatosCampos record,
			@Param("example") CenAccesodatosCamposExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAccesodatosCamposSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenAccesodatosCampos record,
			@Param("example") CenAccesodatosCamposExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAccesodatosCamposSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenAccesodatosCampos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_ACCESODATOS_CAMPOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_ACCESODATOS_CAMPOS", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}", "where CARACTER = #{caracter,jdbcType=VARCHAR}",
			"and IDCAMPOS = #{idcampos,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenAccesodatosCampos record);
}