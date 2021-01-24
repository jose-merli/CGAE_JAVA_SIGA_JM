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
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;

public interface CenPersonaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenPersonaSqlProvider.class, method = "countByExample")
	long countByExample(CenPersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenPersonaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenPersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_PERSONA", "where IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idpersona);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_PERSONA (IDPERSONA, NOMBRE, ", "APELLIDOS1, APELLIDOS2, ", "NIFCIF, FECHAMODIFICACION, ",
			"USUMODIFICACION, IDTIPOIDENTIFICACION, ", "FECHANACIMIENTO, IDESTADOCIVIL, ", "NATURALDE, FALLECIDO, ",
			"SEXO)", "values (#{idpersona,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
			"#{apellidos1,jdbcType=VARCHAR}, #{apellidos2,jdbcType=VARCHAR}, ",
			"#{nifcif,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{idtipoidentificacion,jdbcType=DECIMAL}, ",
			"#{fechanacimiento,jdbcType=TIMESTAMP}, #{idestadocivil,jdbcType=DECIMAL}, ",
			"#{naturalde,jdbcType=VARCHAR}, #{fallecido,jdbcType=VARCHAR}, ", "#{sexo,jdbcType=VARCHAR})" })
	int insert(CenPersona record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenPersonaSqlProvider.class, method = "insertSelective")
	int insertSelective(CenPersona record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenPersonaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOIDENTIFICACION", property = "idtipoidentificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDESTADOCIVIL", property = "idestadocivil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NATURALDE", property = "naturalde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FALLECIDO", property = "fallecido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR) })
	List<CenPersona> selectByExample(CenPersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDPERSONA, NOMBRE, APELLIDOS1, APELLIDOS2, NIFCIF, FECHAMODIFICACION, USUMODIFICACION, ",
			"IDTIPOIDENTIFICACION, FECHANACIMIENTO, IDESTADOCIVIL, NATURALDE, FALLECIDO, ", "SEXO", "from CEN_PERSONA",
			"where IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOIDENTIFICACION", property = "idtipoidentificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDESTADOCIVIL", property = "idestadocivil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NATURALDE", property = "naturalde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FALLECIDO", property = "fallecido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR) })
	CenPersona selectByPrimaryKey(Long idpersona);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenPersonaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenPersona record, @Param("example") CenPersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenPersonaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenPersona record, @Param("example") CenPersonaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenPersonaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenPersona record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_PERSONA", "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"APELLIDOS1 = #{apellidos1,jdbcType=VARCHAR},", "APELLIDOS2 = #{apellidos2,jdbcType=VARCHAR},",
			"NIFCIF = #{nifcif,jdbcType=VARCHAR},", "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"IDTIPOIDENTIFICACION = #{idtipoidentificacion,jdbcType=DECIMAL},",
			"FECHANACIMIENTO = #{fechanacimiento,jdbcType=TIMESTAMP},",
			"IDESTADOCIVIL = #{idestadocivil,jdbcType=DECIMAL},", "NATURALDE = #{naturalde,jdbcType=VARCHAR},",
			"FALLECIDO = #{fallecido,jdbcType=VARCHAR},", "SEXO = #{sexo,jdbcType=VARCHAR}",
			"where IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenPersona record);
}