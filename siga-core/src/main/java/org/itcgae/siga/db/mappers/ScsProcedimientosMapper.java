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
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsProcedimientosExample;
import org.itcgae.siga.db.entities.ScsProcedimientosKey;

public interface ScsProcedimientosMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@SelectProvider(type = ScsProcedimientosSqlProvider.class, method = "countByExample")
	long countByExample(ScsProcedimientosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@DeleteProvider(type = ScsProcedimientosSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsProcedimientosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@Delete({ "delete from SCS_PROCEDIMIENTOS", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(ScsProcedimientosKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@Insert({ "insert into SCS_PROCEDIMIENTOS (IDINSTITUCION, IDPROCEDIMIENTO, ", "NOMBRE, FECHAMODIFICACION, ",
			"USUMODIFICACION, PRECIO, ", "IDJURISDICCION, CODIGO, ", "COMPLEMENTO, VIGENTE, ", "ORDEN, CODIGOEXT, ",
			"PERMITIRANIADIRLETRADO, FECHADESDEVIGOR, ", "FECHAHASTAVIGOR, FECHABAJA, ", "OBSERVACIONES)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idprocedimiento,jdbcType=VARCHAR}, ",
			"#{nombre,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{precio,jdbcType=DECIMAL}, ",
			"#{idjurisdiccion,jdbcType=DECIMAL}, #{codigo,jdbcType=VARCHAR}, ",
			"#{complemento,jdbcType=VARCHAR}, #{vigente,jdbcType=VARCHAR}, ",
			"#{orden,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
			"#{permitiraniadirletrado,jdbcType=VARCHAR}, #{fechadesdevigor,jdbcType=TIMESTAMP}, ",
			"#{fechahastavigor,jdbcType=TIMESTAMP}, #{fechabaja,jdbcType=TIMESTAMP}, ",
			"#{observaciones,jdbcType=VARCHAR})" })
	int insert(ScsProcedimientos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@InsertProvider(type = ScsProcedimientosSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsProcedimientos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@SelectProvider(type = ScsProcedimientosSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPROCEDIMIENTO", property = "idprocedimiento", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PRECIO", property = "precio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMPLEMENTO", property = "complemento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VIGENTE", property = "vigente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PERMITIRANIADIRLETRADO", property = "permitiraniadirletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESDEVIGOR", property = "fechadesdevigor", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTAVIGOR", property = "fechahastavigor", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR) })
	List<ScsProcedimientos> selectByExample(ScsProcedimientosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDPROCEDIMIENTO, NOMBRE, FECHAMODIFICACION, USUMODIFICACION, ",
			"PRECIO, IDJURISDICCION, CODIGO, COMPLEMENTO, VIGENTE, ORDEN, CODIGOEXT, PERMITIRANIADIRLETRADO, ",
			"FECHADESDEVIGOR, FECHAHASTAVIGOR, FECHABAJA, OBSERVACIONES", "from SCS_PROCEDIMIENTOS",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPROCEDIMIENTO", property = "idprocedimiento", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PRECIO", property = "precio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMPLEMENTO", property = "complemento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VIGENTE", property = "vigente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PERMITIRANIADIRLETRADO", property = "permitiraniadirletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESDEVIGOR", property = "fechadesdevigor", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTAVIGOR", property = "fechahastavigor", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR) })
	ScsProcedimientos selectByPrimaryKey(ScsProcedimientosKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@UpdateProvider(type = ScsProcedimientosSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsProcedimientos record,
			@Param("example") ScsProcedimientosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@UpdateProvider(type = ScsProcedimientosSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsProcedimientos record, @Param("example") ScsProcedimientosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@UpdateProvider(type = ScsProcedimientosSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsProcedimientos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	@Update({ "update SCS_PROCEDIMIENTOS", "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "PRECIO = #{precio,jdbcType=DECIMAL},",
			"IDJURISDICCION = #{idjurisdiccion,jdbcType=DECIMAL},", "CODIGO = #{codigo,jdbcType=VARCHAR},",
			"COMPLEMENTO = #{complemento,jdbcType=VARCHAR},", "VIGENTE = #{vigente,jdbcType=VARCHAR},",
			"ORDEN = #{orden,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"PERMITIRANIADIRLETRADO = #{permitiraniadirletrado,jdbcType=VARCHAR},",
			"FECHADESDEVIGOR = #{fechadesdevigor,jdbcType=TIMESTAMP},",
			"FECHAHASTAVIGOR = #{fechahastavigor,jdbcType=TIMESTAMP},", "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
			"OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(ScsProcedimientos record);
}