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
import org.itcgae.siga.db.entities.CenTiposancion;
import org.itcgae.siga.db.entities.CenTiposancionExample;

public interface CenTiposancionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTiposancionSqlProvider.class, method = "countByExample")
	long countByExample(CenTiposancionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenTiposancionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTiposancionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_TIPOSANCION", "where IDTIPOSANCION = #{idtiposancion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idtiposancion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_TIPOSANCION (IDTIPOSANCION, CODIGOEXT, ", "DESCRIPCION, FECHAMODIFICACION, ",
			"USUMODIFICACION, BLOQUEADO)", "values (#{idtiposancion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
			"#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{bloqueado,jdbcType=CHAR})" })
	int insert(CenTiposancion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenTiposancionSqlProvider.class, method = "insertSelective")
	int insertSelective(CenTiposancion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTiposancionSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTIPOSANCION", property = "idtiposancion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR) })
	List<CenTiposancion> selectByExample(CenTiposancionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDTIPOSANCION, CODIGOEXT, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, BLOQUEADO",
			"from CEN_TIPOSANCION", "where IDTIPOSANCION = #{idtiposancion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTIPOSANCION", property = "idtiposancion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR) })
	CenTiposancion selectByPrimaryKey(Short idtiposancion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTiposancionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTiposancion record,
			@Param("example") CenTiposancionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTiposancionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTiposancion record, @Param("example") CenTiposancionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTiposancionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTiposancion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOSANCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_TIPOSANCION", "set CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "BLOQUEADO = #{bloqueado,jdbcType=CHAR}",
			"where IDTIPOSANCION = #{idtiposancion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTiposancion record);
}