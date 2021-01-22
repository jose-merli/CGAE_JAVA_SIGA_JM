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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.AgeCalendario;
import org.itcgae.siga.db.entities.AgeCalendarioExample;

public interface AgeCalendarioMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@SelectProvider(type = AgeCalendarioSqlProvider.class, method = "countByExample")
	long countByExample(AgeCalendarioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@DeleteProvider(type = AgeCalendarioSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AgeCalendarioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@Delete({ "delete from AGE_CALENDARIO", "where IDCALENDARIO = #{idcalendario,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idcalendario);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@Insert({ "insert into AGE_CALENDARIO (IDCALENDARIO, IDINSTITUCION, ", "DESCRIPCION, USUMODIFICACION, ",
			"FECHAMODIFICACION, FECHABAJA, ", "IDTIPOCALENDARIO, COLOR)",
			"values (#{idcalendario,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{descripcion,jdbcType=VARCHAR}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{fechabaja,jdbcType=TIMESTAMP}, ",
			"#{idtipocalendario,jdbcType=DECIMAL}, #{color,jdbcType=VARCHAR})" })
	@SelectKey(statement = "SELECT seq_agecalendario.NEXTVAL FROM DUAL", keyProperty = "idcalendario", before = true, resultType = Long.class)
	int insert(AgeCalendario record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@InsertProvider(type = AgeCalendarioSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT seq_agecalendario.NEXTVAL FROM DUAL", keyProperty = "idcalendario", before = true, resultType = Long.class)
	int insertSelective(AgeCalendario record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@SelectProvider(type = AgeCalendarioSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDCALENDARIO", property = "idcalendario", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOCALENDARIO", property = "idtipocalendario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COLOR", property = "color", jdbcType = JdbcType.VARCHAR) })
	List<AgeCalendario> selectByExample(AgeCalendarioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@Select({ "select", "IDCALENDARIO, IDINSTITUCION, DESCRIPCION, USUMODIFICACION, FECHAMODIFICACION, ",
			"FECHABAJA, IDTIPOCALENDARIO, COLOR", "from AGE_CALENDARIO",
			"where IDCALENDARIO = #{idcalendario,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDCALENDARIO", property = "idcalendario", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOCALENDARIO", property = "idtipocalendario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COLOR", property = "color", jdbcType = JdbcType.VARCHAR) })
	AgeCalendario selectByPrimaryKey(Long idcalendario);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@UpdateProvider(type = AgeCalendarioSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AgeCalendario record, @Param("example") AgeCalendarioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@UpdateProvider(type = AgeCalendarioSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AgeCalendario record, @Param("example") AgeCalendarioExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@UpdateProvider(type = AgeCalendarioSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(AgeCalendario record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_CALENDARIO
	 * @mbg.generated  Mon Oct 15 16:27:11 CEST 2018
	 */
	@Update({ "update AGE_CALENDARIO", "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
			"DESCRIPCION = #{descripcion,jdbcType=VARCHAR},", "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},", "IDTIPOCALENDARIO = #{idtipocalendario,jdbcType=DECIMAL},",
			"COLOR = #{color,jdbcType=VARCHAR}", "where IDCALENDARIO = #{idcalendario,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(AgeCalendario record);
}