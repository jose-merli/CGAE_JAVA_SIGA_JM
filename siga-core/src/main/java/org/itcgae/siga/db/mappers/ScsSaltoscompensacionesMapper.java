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
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.entities.ScsSaltoscompensacionesExample;
import org.itcgae.siga.db.entities.ScsSaltoscompensacionesKey;

public interface ScsSaltoscompensacionesMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@SelectProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "countByExample")
	long countByExample(ScsSaltoscompensacionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@DeleteProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsSaltoscompensacionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@Delete({ "delete from SCS_SALTOSCOMPENSACIONES", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTURNO = #{idturno,jdbcType=DECIMAL}", "and IDSALTOSTURNO = #{idsaltosturno,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(ScsSaltoscompensacionesKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@Insert({ "insert into SCS_SALTOSCOMPENSACIONES (IDINSTITUCION, IDTURNO, ", "IDSALTOSTURNO, IDPERSONA, ",
			"SALTOOCOMPENSACION, FECHA, ", "FECHAMODIFICACION, USUMODIFICACION, ", "IDGUARDIA, MOTIVOS, ",
			"FECHACUMPLIMIENTO, IDCALENDARIOGUARDIAS, ", "IDCALENDARIOGUARDIASCREACION, TIPOMANUAL)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idturno,jdbcType=DECIMAL}, ",
			"#{idsaltosturno,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{saltoocompensacion,jdbcType=VARCHAR}, #{fecha,jdbcType=TIMESTAMP}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{idguardia,jdbcType=DECIMAL}, #{motivos,jdbcType=VARCHAR}, ",
			"#{fechacumplimiento,jdbcType=TIMESTAMP}, #{idcalendarioguardias,jdbcType=DECIMAL}, ",
			"#{idcalendarioguardiascreacion,jdbcType=DECIMAL}, #{tipomanual,jdbcType=DECIMAL})" })
	int insert(ScsSaltoscompensaciones record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@InsertProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsSaltoscompensaciones record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@SelectProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDSALTOSTURNO", property = "idsaltosturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SALTOOCOMPENSACION", property = "saltoocompensacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACUMPLIMIENTO", property = "fechacumplimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCALENDARIOGUARDIASCREACION", property = "idcalendarioguardiascreacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TIPOMANUAL", property = "tipomanual", jdbcType = JdbcType.DECIMAL) })
	List<ScsSaltoscompensaciones> selectByExample(ScsSaltoscompensacionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@Select({ "select", "IDINSTITUCION, IDTURNO, IDSALTOSTURNO, IDPERSONA, SALTOOCOMPENSACION, FECHA, ",
			"FECHAMODIFICACION, USUMODIFICACION, IDGUARDIA, MOTIVOS, FECHACUMPLIMIENTO, IDCALENDARIOGUARDIAS, ",
			"IDCALENDARIOGUARDIASCREACION, TIPOMANUAL", "from SCS_SALTOSCOMPENSACIONES",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
			"and IDSALTOSTURNO = #{idsaltosturno,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDSALTOSTURNO", property = "idsaltosturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SALTOOCOMPENSACION", property = "saltoocompensacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACUMPLIMIENTO", property = "fechacumplimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCALENDARIOGUARDIASCREACION", property = "idcalendarioguardiascreacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TIPOMANUAL", property = "tipomanual", jdbcType = JdbcType.DECIMAL) })
	ScsSaltoscompensaciones selectByPrimaryKey(ScsSaltoscompensacionesKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@UpdateProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsSaltoscompensaciones record,
			@Param("example") ScsSaltoscompensacionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@UpdateProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsSaltoscompensaciones record,
			@Param("example") ScsSaltoscompensacionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@UpdateProvider(type = ScsSaltoscompensacionesSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsSaltoscompensaciones record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table USCGAE.SCS_SALTOSCOMPENSACIONES
	 *
	 * @mbg.generated Mon Jan 27 17:17:57 CET 2020
	 */
	@Update({ "update SCS_SALTOSCOMPENSACIONES", "set IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
			"SALTOOCOMPENSACION = #{saltoocompensacion,jdbcType=VARCHAR},", "FECHA = #{fecha,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "IDGUARDIA = #{idguardia,jdbcType=DECIMAL},",
			"MOTIVOS = #{motivos,jdbcType=VARCHAR},", "FECHACUMPLIMIENTO = #{fechacumplimiento,jdbcType=TIMESTAMP},",
			"IDCALENDARIOGUARDIAS = #{idcalendarioguardias,jdbcType=DECIMAL},",
			"IDCALENDARIOGUARDIASCREACION = #{idcalendarioguardiascreacion,jdbcType=DECIMAL},",
			"TIPOMANUAL = #{tipomanual,jdbcType=DECIMAL}", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTURNO = #{idturno,jdbcType=DECIMAL}", "and IDSALTOSTURNO = #{idsaltosturno,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsSaltoscompensaciones record);
}