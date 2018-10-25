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
import org.itcgae.siga.db.entities.AgeNotificacionesevento;
import org.itcgae.siga.db.entities.AgeNotificacioneseventoExample;
import org.apache.ibatis.annotations.SelectKey;

public interface AgeNotificacioneseventoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@SelectProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "countByExample")
	long countByExample(AgeNotificacioneseventoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@DeleteProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AgeNotificacioneseventoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@Delete({ "delete from AGE_NOTIFICACIONESEVENTO",
			"where IDNOTIFICACIONEVENTO = #{idnotificacionevento,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idnotificacionevento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@SelectKey(statement = "SELECT SEQ_AGEPERMISOSCALENDARIO.NEXTVAL FROM DUAL", keyProperty = "idnotificacionevento", before = true, resultType = Long.class)
	@Insert({ "insert into AGE_NOTIFICACIONESEVENTO (IDNOTIFICACIONEVENTO, IDINSTITUCION, ",
			"USUMODIFICACION, FECHAMODIFICACION, ", "IDTIPONOTIFICACIONEVENTO, CUANDO, ",
			"IDTIPOCUANDO, IDUNIDADMEDIDA, ", "IDCALENDARIO, IDEVENTO, ", "FECHABAJA, IDPLANTILLA, ", "IDTIPOENVIOS)",
			"values (#{idnotificacionevento,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{idtiponotificacionevento,jdbcType=DECIMAL}, #{cuando,jdbcType=DECIMAL}, ",
			"#{idtipocuando,jdbcType=DECIMAL}, #{idunidadmedida,jdbcType=DECIMAL}, ",
			"#{idcalendario,jdbcType=DECIMAL}, #{idevento,jdbcType=DECIMAL}, ",
			"#{fechabaja,jdbcType=TIMESTAMP}, #{idplantilla,jdbcType=DECIMAL}, ", "#{idtipoenvios,jdbcType=DECIMAL})" })
	int insert(AgeNotificacionesevento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@SelectKey(statement = "SELECT SEQ_AGEPERMISOSCALENDARIO.NEXTVAL FROM DUAL", keyProperty = "idnotificacionevento", before = true, resultType = Long.class)
	@InsertProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "insertSelective")
	int insertSelective(AgeNotificacionesevento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@SelectProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDNOTIFICACIONEVENTO", property = "idnotificacionevento", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "idtiponotificacionevento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CUANDO", property = "cuando", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOCUANDO", property = "idtipocuando", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDUNIDADMEDIDA", property = "idunidadmedida", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCALENDARIO", property = "idcalendario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDEVENTO", property = "idevento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPLANTILLA", property = "idplantilla", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL) })
	List<AgeNotificacionesevento> selectByExample(AgeNotificacioneseventoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@Select({ "select",
			"IDNOTIFICACIONEVENTO, IDINSTITUCION, USUMODIFICACION, FECHAMODIFICACION, IDTIPONOTIFICACIONEVENTO, ",
			"CUANDO, IDTIPOCUANDO, IDUNIDADMEDIDA, IDCALENDARIO, IDEVENTO, FECHABAJA, IDPLANTILLA, ", "IDTIPOENVIOS",
			"from AGE_NOTIFICACIONESEVENTO", "where IDNOTIFICACIONEVENTO = #{idnotificacionevento,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDNOTIFICACIONEVENTO", property = "idnotificacionevento", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPONOTIFICACIONEVENTO", property = "idtiponotificacionevento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CUANDO", property = "cuando", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOCUANDO", property = "idtipocuando", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDUNIDADMEDIDA", property = "idunidadmedida", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCALENDARIO", property = "idcalendario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDEVENTO", property = "idevento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPLANTILLA", property = "idplantilla", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL) })
	AgeNotificacionesevento selectByPrimaryKey(Long idnotificacionevento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@UpdateProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AgeNotificacionesevento record,
			@Param("example") AgeNotificacioneseventoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@UpdateProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AgeNotificacionesevento record,
			@Param("example") AgeNotificacioneseventoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@UpdateProvider(type = AgeNotificacioneseventoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(AgeNotificacionesevento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	@Update({ "update AGE_NOTIFICACIONESEVENTO", "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"IDTIPONOTIFICACIONEVENTO = #{idtiponotificacionevento,jdbcType=DECIMAL},",
			"CUANDO = #{cuando,jdbcType=DECIMAL},", "IDTIPOCUANDO = #{idtipocuando,jdbcType=DECIMAL},",
			"IDUNIDADMEDIDA = #{idunidadmedida,jdbcType=DECIMAL},", "IDCALENDARIO = #{idcalendario,jdbcType=DECIMAL},",
			"IDEVENTO = #{idevento,jdbcType=DECIMAL},", "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
			"IDPLANTILLA = #{idplantilla,jdbcType=DECIMAL},", "IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}",
			"where IDNOTIFICACIONEVENTO = #{idnotificacionevento,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(AgeNotificacionesevento record);
}