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
import org.itcgae.siga.db.entities.CenTratamiento;
import org.itcgae.siga.db.entities.CenTratamientoExample;

public interface CenTratamientoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTratamientoSqlProvider.class, method = "countByExample")
	long countByExample(CenTratamientoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenTratamientoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTratamientoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_TRATAMIENTO", "where IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idtratamiento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_TRATAMIENTO (IDTRATAMIENTO, FECHAMODIFICACION, ", "USUMODIFICACION, DESCRIPCION, ",
			"CODIGOEXT, BLOQUEADO, ", "GENERO)",
			"values (#{idtratamiento,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ", "#{genero,jdbcType=VARCHAR})" })
	int insert(CenTratamiento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenTratamientoSqlProvider.class, method = "insertSelective")
	int insertSelective(CenTratamiento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTratamientoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTRATAMIENTO", property = "idtratamiento", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "GENERO", property = "genero", jdbcType = JdbcType.VARCHAR) })
	List<CenTratamiento> selectByExample(CenTratamientoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDTRATAMIENTO, FECHAMODIFICACION, USUMODIFICACION, DESCRIPCION, CODIGOEXT, BLOQUEADO, ",
			"GENERO", "from CEN_TRATAMIENTO", "where IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTRATAMIENTO", property = "idtratamiento", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "GENERO", property = "genero", jdbcType = JdbcType.VARCHAR) })
	CenTratamiento selectByPrimaryKey(Short idtratamiento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTratamientoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTratamiento record,
			@Param("example") CenTratamientoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTratamientoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTratamiento record, @Param("example") CenTratamientoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTratamientoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTratamiento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TRATAMIENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_TRATAMIENTO", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"CODIGOEXT = #{codigoext,jdbcType=VARCHAR},", "BLOQUEADO = #{bloqueado,jdbcType=CHAR},",
			"GENERO = #{genero,jdbcType=VARCHAR}", "where IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTratamiento record);
}