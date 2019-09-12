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
import org.itcgae.siga.db.entities.ScsArea;
import org.itcgae.siga.db.entities.ScsAreaExample;
import org.itcgae.siga.db.entities.ScsAreaKey;

public interface ScsAreaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@SelectProvider(type = ScsAreaSqlProvider.class, method = "countByExample")
	long countByExample(ScsAreaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@DeleteProvider(type = ScsAreaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsAreaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@Delete({ "delete from SCS_AREA", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDAREA = #{idarea,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(ScsAreaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@Insert({ "insert into SCS_AREA (IDINSTITUCION, IDAREA, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"NOMBRE, CONTENIDO, ", "FECHABAJA)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idarea,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{nombre,jdbcType=VARCHAR}, #{contenido,jdbcType=VARCHAR}, ", "#{fechabaja,jdbcType=TIMESTAMP})" })
	int insert(ScsArea record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@InsertProvider(type = ScsAreaSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsArea record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@SelectProvider(type = ScsAreaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTENIDO", property = "contenido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<ScsArea> selectByExample(ScsAreaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDAREA, FECHAMODIFICACION, USUMODIFICACION, NOMBRE, CONTENIDO, ", "FECHABAJA",
			"from SCS_AREA", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDAREA = #{idarea,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTENIDO", property = "contenido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	ScsArea selectByPrimaryKey(ScsAreaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@UpdateProvider(type = ScsAreaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsArea record, @Param("example") ScsAreaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@UpdateProvider(type = ScsAreaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsArea record, @Param("example") ScsAreaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@UpdateProvider(type = ScsAreaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsArea record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	@Update({ "update SCS_AREA", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"CONTENIDO = #{contenido,jdbcType=VARCHAR},", "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDAREA = #{idarea,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsArea record);
}