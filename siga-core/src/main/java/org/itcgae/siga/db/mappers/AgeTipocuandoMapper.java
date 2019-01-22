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
import org.itcgae.siga.db.entities.AgeTipocuando;
import org.itcgae.siga.db.entities.AgeTipocuandoExample;

public interface AgeTipocuandoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@SelectProvider(type = AgeTipocuandoSqlProvider.class, method = "countByExample")
	long countByExample(AgeTipocuandoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@DeleteProvider(type = AgeTipocuandoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AgeTipocuandoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@Delete({ "delete from AGE_TIPOCUANDO", "where IDTIPOCUANDO = #{idtipocuando,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idtipocuando);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@Insert({ "insert into AGE_TIPOCUANDO (IDTIPOCUANDO, DESCRIPCION, ", "USUMODIFICACION, FECHAMODIFICACION, ",
			"FECHABAJA)", "values (#{idtipocuando,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{fechabaja,jdbcType=TIMESTAMP})" })
	int insert(AgeTipocuando record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@InsertProvider(type = AgeTipocuandoSqlProvider.class, method = "insertSelective")
	int insertSelective(AgeTipocuando record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@SelectProvider(type = AgeTipocuandoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTIPOCUANDO", property = "idtipocuando", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<AgeTipocuando> selectByExample(AgeTipocuandoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@Select({ "select", "IDTIPOCUANDO, DESCRIPCION, USUMODIFICACION, FECHAMODIFICACION, FECHABAJA",
			"from AGE_TIPOCUANDO", "where IDTIPOCUANDO = #{idtipocuando,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTIPOCUANDO", property = "idtipocuando", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	AgeTipocuando selectByPrimaryKey(Long idtipocuando);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@UpdateProvider(type = AgeTipocuandoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AgeTipocuando record, @Param("example") AgeTipocuandoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@UpdateProvider(type = AgeTipocuandoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AgeTipocuando record, @Param("example") AgeTipocuandoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@UpdateProvider(type = AgeTipocuandoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(AgeTipocuando record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_TIPOCUANDO
	 * @mbg.generated  Mon Oct 22 08:52:58 CEST 2018
	 */
	@Update({ "update AGE_TIPOCUANDO", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}", "where IDTIPOCUANDO = #{idtipocuando,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(AgeTipocuando record);
}