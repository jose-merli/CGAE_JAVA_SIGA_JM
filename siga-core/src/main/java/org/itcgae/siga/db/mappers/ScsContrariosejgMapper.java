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
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsContrariosejgExample;
import org.itcgae.siga.db.entities.ScsContrariosejgKey;

public interface ScsContrariosejgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@SelectProvider(type = ScsContrariosejgSqlProvider.class, method = "countByExample")
	long countByExample(ScsContrariosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@DeleteProvider(type = ScsContrariosejgSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsContrariosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@Delete({ "delete from SCS_CONTRARIOSEJG", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}", "and ANIO = #{anio,jdbcType=DECIMAL}",
			"and NUMERO = #{numero,jdbcType=DECIMAL}", "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(ScsContrariosejgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@Insert({ "insert into SCS_CONTRARIOSEJG (IDINSTITUCION, IDTIPOEJG, ", "ANIO, NUMERO, IDPERSONA, ",
			"FECHAMODIFICACION, USUMODIFICACION, ", "OBSERVACIONES, IDINSTITUCION_PROCU, ",
			"IDPROCURADOR, IDABOGADOCONTRARIOEJG, ", "NOMBREABOGADOCONTRARIOEJG, IDREPRESENTANTEEJG, ",
			"NOMBREREPRESENTANTEEJG, TMPEJISPROCCODCOLEGIO, ", "TMPEJISPROCDESCCOLEGIO, TMPEJISPROCNUMCOLEGIADO, ",
			"TMPEJISPROCNOMBRE, TMPEJISPROCAPELLIDO1, ", "TMPEJISPROCAPELLIDO2, TMPEJISPROCNIF, ",
			"TMPEJISLETCODCOLEGIO, TMPEJISLETDESCCOLEGIO, ", "TMPEJISLETNUMCOLEGIADO, TMPEJISLETNOMBRE, ",
			"TMPEJISLETAPELLIDO1, TMPEJISLETAPELLIDO2, ", "TMPEJISLETNIF, FECHABAJA)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoejg,jdbcType=DECIMAL}, ",
			"#{anio,jdbcType=DECIMAL}, #{numero,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{observaciones,jdbcType=VARCHAR}, #{idinstitucionProcu,jdbcType=DECIMAL}, ",
			"#{idprocurador,jdbcType=DECIMAL}, #{idabogadocontrarioejg,jdbcType=DECIMAL}, ",
			"#{nombreabogadocontrarioejg,jdbcType=VARCHAR}, #{idrepresentanteejg,jdbcType=DECIMAL}, ",
			"#{nombrerepresentanteejg,jdbcType=VARCHAR}, #{tmpejisproccodcolegio,jdbcType=VARCHAR}, ",
			"#{tmpejisprocdesccolegio,jdbcType=VARCHAR}, #{tmpejisprocnumcolegiado,jdbcType=VARCHAR}, ",
			"#{tmpejisprocnombre,jdbcType=VARCHAR}, #{tmpejisprocapellido1,jdbcType=VARCHAR}, ",
			"#{tmpejisprocapellido2,jdbcType=VARCHAR}, #{tmpejisprocnif,jdbcType=VARCHAR}, ",
			"#{tmpejisletcodcolegio,jdbcType=VARCHAR}, #{tmpejisletdesccolegio,jdbcType=VARCHAR}, ",
			"#{tmpejisletnumcolegiado,jdbcType=VARCHAR}, #{tmpejisletnombre,jdbcType=VARCHAR}, ",
			"#{tmpejisletapellido1,jdbcType=VARCHAR}, #{tmpejisletapellido2,jdbcType=VARCHAR}, ",
			"#{tmpejisletnif,jdbcType=VARCHAR}, #{fechabaja,jdbcType=TIMESTAMP})" })
	int insert(ScsContrariosejg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@InsertProvider(type = ScsContrariosejgSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsContrariosejg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@SelectProvider(type = ScsContrariosejgSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION_PROCU", property = "idinstitucionProcu", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROCURADOR", property = "idprocurador", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDABOGADOCONTRARIOEJG", property = "idabogadocontrarioejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBREABOGADOCONTRARIOEJG", property = "nombreabogadocontrarioejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDREPRESENTANTEEJG", property = "idrepresentanteejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBREREPRESENTANTEEJG", property = "nombrerepresentanteejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCCODCOLEGIO", property = "tmpejisproccodcolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCDESCCOLEGIO", property = "tmpejisprocdesccolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCNUMCOLEGIADO", property = "tmpejisprocnumcolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCNOMBRE", property = "tmpejisprocnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCAPELLIDO1", property = "tmpejisprocapellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCAPELLIDO2", property = "tmpejisprocapellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCNIF", property = "tmpejisprocnif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETCODCOLEGIO", property = "tmpejisletcodcolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETDESCCOLEGIO", property = "tmpejisletdesccolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETNUMCOLEGIADO", property = "tmpejisletnumcolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETNOMBRE", property = "tmpejisletnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETAPELLIDO1", property = "tmpejisletapellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETAPELLIDO2", property = "tmpejisletapellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETNIF", property = "tmpejisletnif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<ScsContrariosejg> selectByExample(ScsContrariosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@Select({ "select", "IDINSTITUCION, IDTIPOEJG, ANIO, NUMERO, IDPERSONA, FECHAMODIFICACION, USUMODIFICACION, ",
			"OBSERVACIONES, IDINSTITUCION_PROCU, IDPROCURADOR, IDABOGADOCONTRARIOEJG, NOMBREABOGADOCONTRARIOEJG, ",
			"IDREPRESENTANTEEJG, NOMBREREPRESENTANTEEJG, TMPEJISPROCCODCOLEGIO, TMPEJISPROCDESCCOLEGIO, ",
			"TMPEJISPROCNUMCOLEGIADO, TMPEJISPROCNOMBRE, TMPEJISPROCAPELLIDO1, TMPEJISPROCAPELLIDO2, ",
			"TMPEJISPROCNIF, TMPEJISLETCODCOLEGIO, TMPEJISLETDESCCOLEGIO, TMPEJISLETNUMCOLEGIADO, ",
			"TMPEJISLETNOMBRE, TMPEJISLETAPELLIDO1, TMPEJISLETAPELLIDO2, TMPEJISLETNIF, FECHABAJA",
			"from SCS_CONTRARIOSEJG", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}", "and ANIO = #{anio,jdbcType=DECIMAL}",
			"and NUMERO = #{numero,jdbcType=DECIMAL}", "and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION_PROCU", property = "idinstitucionProcu", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROCURADOR", property = "idprocurador", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDABOGADOCONTRARIOEJG", property = "idabogadocontrarioejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBREABOGADOCONTRARIOEJG", property = "nombreabogadocontrarioejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDREPRESENTANTEEJG", property = "idrepresentanteejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBREREPRESENTANTEEJG", property = "nombrerepresentanteejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCCODCOLEGIO", property = "tmpejisproccodcolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCDESCCOLEGIO", property = "tmpejisprocdesccolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCNUMCOLEGIADO", property = "tmpejisprocnumcolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCNOMBRE", property = "tmpejisprocnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCAPELLIDO1", property = "tmpejisprocapellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCAPELLIDO2", property = "tmpejisprocapellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISPROCNIF", property = "tmpejisprocnif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETCODCOLEGIO", property = "tmpejisletcodcolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETDESCCOLEGIO", property = "tmpejisletdesccolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETNUMCOLEGIADO", property = "tmpejisletnumcolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETNOMBRE", property = "tmpejisletnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETAPELLIDO1", property = "tmpejisletapellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETAPELLIDO2", property = "tmpejisletapellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TMPEJISLETNIF", property = "tmpejisletnif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	ScsContrariosejg selectByPrimaryKey(ScsContrariosejgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@UpdateProvider(type = ScsContrariosejgSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsContrariosejg record,
			@Param("example") ScsContrariosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@UpdateProvider(type = ScsContrariosejgSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsContrariosejg record, @Param("example") ScsContrariosejgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@UpdateProvider(type = ScsContrariosejgSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsContrariosejg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_CONTRARIOSEJG
	 * @mbg.generated  Wed Jun 09 13:34:59 CEST 2021
	 */
	@Update({ "update SCS_CONTRARIOSEJG", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
			"IDINSTITUCION_PROCU = #{idinstitucionProcu,jdbcType=DECIMAL},",
			"IDPROCURADOR = #{idprocurador,jdbcType=DECIMAL},",
			"IDABOGADOCONTRARIOEJG = #{idabogadocontrarioejg,jdbcType=DECIMAL},",
			"NOMBREABOGADOCONTRARIOEJG = #{nombreabogadocontrarioejg,jdbcType=VARCHAR},",
			"IDREPRESENTANTEEJG = #{idrepresentanteejg,jdbcType=DECIMAL},",
			"NOMBREREPRESENTANTEEJG = #{nombrerepresentanteejg,jdbcType=VARCHAR},",
			"TMPEJISPROCCODCOLEGIO = #{tmpejisproccodcolegio,jdbcType=VARCHAR},",
			"TMPEJISPROCDESCCOLEGIO = #{tmpejisprocdesccolegio,jdbcType=VARCHAR},",
			"TMPEJISPROCNUMCOLEGIADO = #{tmpejisprocnumcolegiado,jdbcType=VARCHAR},",
			"TMPEJISPROCNOMBRE = #{tmpejisprocnombre,jdbcType=VARCHAR},",
			"TMPEJISPROCAPELLIDO1 = #{tmpejisprocapellido1,jdbcType=VARCHAR},",
			"TMPEJISPROCAPELLIDO2 = #{tmpejisprocapellido2,jdbcType=VARCHAR},",
			"TMPEJISPROCNIF = #{tmpejisprocnif,jdbcType=VARCHAR},",
			"TMPEJISLETCODCOLEGIO = #{tmpejisletcodcolegio,jdbcType=VARCHAR},",
			"TMPEJISLETDESCCOLEGIO = #{tmpejisletdesccolegio,jdbcType=VARCHAR},",
			"TMPEJISLETNUMCOLEGIADO = #{tmpejisletnumcolegiado,jdbcType=VARCHAR},",
			"TMPEJISLETNOMBRE = #{tmpejisletnombre,jdbcType=VARCHAR},",
			"TMPEJISLETAPELLIDO1 = #{tmpejisletapellido1,jdbcType=VARCHAR},",
			"TMPEJISLETAPELLIDO2 = #{tmpejisletapellido2,jdbcType=VARCHAR},",
			"TMPEJISLETNIF = #{tmpejisletnif,jdbcType=VARCHAR},", "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}",
			"and ANIO = #{anio,jdbcType=DECIMAL}", "and NUMERO = #{numero,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsContrariosejg record);
}