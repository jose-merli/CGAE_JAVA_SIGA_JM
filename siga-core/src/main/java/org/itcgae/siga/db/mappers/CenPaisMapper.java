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
import org.itcgae.siga.db.entities.CenPais;
import org.itcgae.siga.db.entities.CenPaisExample;

public interface CenPaisMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenPaisSqlProvider.class, method = "countByExample")
	long countByExample(CenPaisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenPaisSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenPaisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_PAIS", "where IDPAIS = #{idpais,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String idpais);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_PAIS (IDPAIS, NOMBRE, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"CODIGOEXT, BLOQUEADO, ", "COD_ISO, LONGITUDCUENTABANCARIA, ", "SEPA)",
			"values (#{idpais,jdbcType=VARCHAR}, #{nombre,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ",
			"#{codIso,jdbcType=VARCHAR}, #{longitudcuentabancaria,jdbcType=DECIMAL}, ", "#{sepa,jdbcType=VARCHAR})" })
	int insert(CenPais record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenPaisSqlProvider.class, method = "insertSelective")
	int insertSelective(CenPais record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenPaisSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "COD_ISO", property = "codIso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LONGITUDCUENTABANCARIA", property = "longitudcuentabancaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SEPA", property = "sepa", jdbcType = JdbcType.VARCHAR) })
	List<CenPais> selectByExample(CenPaisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDPAIS, NOMBRE, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, BLOQUEADO, COD_ISO, ",
			"LONGITUDCUENTABANCARIA, SEPA", "from CEN_PAIS", "where IDPAIS = #{idpais,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "COD_ISO", property = "codIso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LONGITUDCUENTABANCARIA", property = "longitudcuentabancaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SEPA", property = "sepa", jdbcType = JdbcType.VARCHAR) })
	CenPais selectByPrimaryKey(String idpais);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenPaisSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenPais record, @Param("example") CenPaisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenPaisSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenPais record, @Param("example") CenPaisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenPaisSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenPais record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_PAIS", "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"BLOQUEADO = #{bloqueado,jdbcType=CHAR},", "COD_ISO = #{codIso,jdbcType=VARCHAR},",
			"LONGITUDCUENTABANCARIA = #{longitudcuentabancaria,jdbcType=DECIMAL},", "SEPA = #{sepa,jdbcType=VARCHAR}",
			"where IDPAIS = #{idpais,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(CenPais record);
}