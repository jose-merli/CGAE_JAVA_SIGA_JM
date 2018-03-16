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
import org.itcgae.siga.db.entities.CenTipodireccion;
import org.itcgae.siga.db.entities.CenTipodireccionExample;

public interface CenTipodireccionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTipodireccionSqlProvider.class, method = "countByExample")
	long countByExample(CenTipodireccionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenTipodireccionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTipodireccionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_TIPODIRECCION", "where IDTIPODIRECCION = #{idtipodireccion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idtipodireccion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_TIPODIRECCION (IDTIPODIRECCION, DESCRIPCION, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"CODIGOEXT)", "values (#{idtipodireccion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{codigoext,jdbcType=VARCHAR})" })
	int insert(CenTipodireccion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenTipodireccionSqlProvider.class, method = "insertSelective")
	int insertSelective(CenTipodireccion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTipodireccionSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDTIPODIRECCION", property = "idtipodireccion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR) })
	List<CenTipodireccion> selectByExample(CenTipodireccionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDTIPODIRECCION, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT",
			"from CEN_TIPODIRECCION", "where IDTIPODIRECCION = #{idtipodireccion,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDTIPODIRECCION", property = "idtipodireccion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR) })
	CenTipodireccion selectByPrimaryKey(Short idtipodireccion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipodireccionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTipodireccion record,
			@Param("example") CenTipodireccionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipodireccionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTipodireccion record, @Param("example") CenTipodireccionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipodireccionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTipodireccion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPODIRECCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_TIPODIRECCION", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR}",
			"where IDTIPODIRECCION = #{idtipodireccion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTipodireccion record);
}