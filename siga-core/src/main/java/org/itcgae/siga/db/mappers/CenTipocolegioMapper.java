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
import org.itcgae.siga.db.entities.CenTipocolegio;
import org.itcgae.siga.db.entities.CenTipocolegioExample;

public interface CenTipocolegioMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTipocolegioSqlProvider.class, method = "countByExample")
	long countByExample(CenTipocolegioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenTipocolegioSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTipocolegioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_TIPOCOLEGIO", "where IDTIPOCOLEGIO = #{idtipocolegio,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idtipocolegio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_TIPOCOLEGIO (IDTIPOCOLEGIO, DESCRIPCION, ", "CODIGOEXT, FECHAMODIFICACION, ",
			"USUMODIFICACION, BLOQUEADO)",
			"values (#{idtipocolegio,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{codigoext,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{bloqueado,jdbcType=CHAR})" })
	int insert(CenTipocolegio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenTipocolegioSqlProvider.class, method = "insertSelective")
	int insertSelective(CenTipocolegio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenTipocolegioSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTIPOCOLEGIO", property = "idtipocolegio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR) })
	List<CenTipocolegio> selectByExample(CenTipocolegioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDTIPOCOLEGIO, DESCRIPCION, CODIGOEXT, FECHAMODIFICACION, USUMODIFICACION, BLOQUEADO",
			"from CEN_TIPOCOLEGIO", "where IDTIPOCOLEGIO = #{idtipocolegio,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTIPOCOLEGIO", property = "idtipocolegio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR) })
	CenTipocolegio selectByPrimaryKey(Short idtipocolegio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipocolegioSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTipocolegio record,
			@Param("example") CenTipocolegioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipocolegioSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTipocolegio record, @Param("example") CenTipocolegioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenTipocolegioSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTipocolegio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_TIPOCOLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_TIPOCOLEGIO", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "BLOQUEADO = #{bloqueado,jdbcType=CHAR}",
			"where IDTIPOCOLEGIO = #{idtipocolegio,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTipocolegio record);
}