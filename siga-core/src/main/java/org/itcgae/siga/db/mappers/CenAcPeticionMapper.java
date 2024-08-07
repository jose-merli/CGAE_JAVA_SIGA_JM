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
import org.itcgae.siga.db.entities.CenAcPeticion;
import org.itcgae.siga.db.entities.CenAcPeticionExample;

public interface CenAcPeticionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAcPeticionSqlProvider.class, method = "countByExample")
	long countByExample(CenAcPeticionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenAcPeticionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenAcPeticionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_AC_PETICION", "where IDCENACPETICION = #{idcenacpeticion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idcenacpeticion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_AC_PETICION (IDCENACPETICION, IDCENACXMLRECIBIDA, ", "IDCENACXMLNOVALIDO, IDCENACIPS, ",
			"IP_RECIBIDA, TIPODOCUMENTO, ", "NUMDOC, CODCOLEGIO, ", "DESCCOLEGIO, NUMCOLEGIADO, ",
			"FECHAPETICION, USUMODIFICACION, ", "FECHAMODIFICACION)",
			"values (#{idcenacpeticion,jdbcType=DECIMAL}, #{idcenacxmlrecibida,jdbcType=DECIMAL}, ",
			"#{idcenacxmlnovalido,jdbcType=DECIMAL}, #{idcenacips,jdbcType=DECIMAL}, ",
			"#{ipRecibida,jdbcType=VARCHAR}, #{tipodocumento,jdbcType=VARCHAR}, ",
			"#{numdoc,jdbcType=VARCHAR}, #{codcolegio,jdbcType=VARCHAR}, ",
			"#{desccolegio,jdbcType=VARCHAR}, #{numcolegiado,jdbcType=VARCHAR}, ",
			"#{fechapeticion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP})" })
	int insert(CenAcPeticion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenAcPeticionSqlProvider.class, method = "insertSelective")
	int insertSelective(CenAcPeticion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAcPeticionSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDCENACPETICION", property = "idcenacpeticion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCENACXMLRECIBIDA", property = "idcenacxmlrecibida", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCENACXMLNOVALIDO", property = "idcenacxmlnovalido", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCENACIPS", property = "idcenacips", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IP_RECIBIDA", property = "ipRecibida", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPODOCUMENTO", property = "tipodocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMDOC", property = "numdoc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODCOLEGIO", property = "codcolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCCOLEGIO", property = "desccolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMCOLEGIADO", property = "numcolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAPETICION", property = "fechapeticion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	List<CenAcPeticion> selectByExample(CenAcPeticionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDCENACPETICION, IDCENACXMLRECIBIDA, IDCENACXMLNOVALIDO, IDCENACIPS, IP_RECIBIDA, ",
			"TIPODOCUMENTO, NUMDOC, CODCOLEGIO, DESCCOLEGIO, NUMCOLEGIADO, FECHAPETICION, ",
			"USUMODIFICACION, FECHAMODIFICACION", "from CEN_AC_PETICION",
			"where IDCENACPETICION = #{idcenacpeticion,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDCENACPETICION", property = "idcenacpeticion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCENACXMLRECIBIDA", property = "idcenacxmlrecibida", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCENACXMLNOVALIDO", property = "idcenacxmlnovalido", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCENACIPS", property = "idcenacips", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IP_RECIBIDA", property = "ipRecibida", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPODOCUMENTO", property = "tipodocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMDOC", property = "numdoc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODCOLEGIO", property = "codcolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCCOLEGIO", property = "desccolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMCOLEGIADO", property = "numcolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAPETICION", property = "fechapeticion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	CenAcPeticion selectByPrimaryKey(Long idcenacpeticion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcPeticionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenAcPeticion record, @Param("example") CenAcPeticionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcPeticionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenAcPeticion record, @Param("example") CenAcPeticionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcPeticionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenAcPeticion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_PETICION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_AC_PETICION", "set IDCENACXMLRECIBIDA = #{idcenacxmlrecibida,jdbcType=DECIMAL},",
			"IDCENACXMLNOVALIDO = #{idcenacxmlnovalido,jdbcType=DECIMAL},",
			"IDCENACIPS = #{idcenacips,jdbcType=DECIMAL},", "IP_RECIBIDA = #{ipRecibida,jdbcType=VARCHAR},",
			"TIPODOCUMENTO = #{tipodocumento,jdbcType=VARCHAR},", "NUMDOC = #{numdoc,jdbcType=VARCHAR},",
			"CODCOLEGIO = #{codcolegio,jdbcType=VARCHAR},", "DESCCOLEGIO = #{desccolegio,jdbcType=VARCHAR},",
			"NUMCOLEGIADO = #{numcolegiado,jdbcType=VARCHAR},", "FECHAPETICION = #{fechapeticion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
			"where IDCENACPETICION = #{idcenacpeticion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenAcPeticion record);
}