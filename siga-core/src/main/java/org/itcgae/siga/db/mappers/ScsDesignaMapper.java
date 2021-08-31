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
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;

public interface ScsDesignaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@SelectProvider(type = ScsDesignaSqlProvider.class, method = "countByExample")
	long countByExample(ScsDesignaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@DeleteProvider(type = ScsDesignaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsDesignaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@Delete({ "delete from SCS_DESIGNA", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTURNO = #{idturno,jdbcType=DECIMAL}", "and ANIO = #{anio,jdbcType=DECIMAL}",
			"and NUMERO = #{numero,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(ScsDesignaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@Insert({ "insert into SCS_DESIGNA (IDINSTITUCION, IDTURNO, ", "ANIO, NUMERO, FECHAENTRADA, ",
			"FECHAMODIFICACION, USUMODIFICACION, ", "ESTADO, FECHAFIN, ", "RESUMENASUNTO, DELITOS, ",
			"PROCURADOR, IDTIPODESIGNACOLEGIO, ", "OBSERVACIONES, DEFENSAJURIDICA, ",
			"IDPROCURADOR, IDINSTITUCION_PROCUR, ", "IDJUZGADO, IDINSTITUCION_JUZG, ", "FECHAANULACION, CODIGO, ",
			"NUMPROCEDIMIENTO, FECHAJUICIO, ", "IDPROCEDIMIENTO, FECHAESTADO, ", "IDPRETENSION, SUFIJO, ",
			"FECHAOFICIOJUZGADO, FECHARECEPCIONCOLEGIO, ", "FECHAALTA, ART27, ", "NIG, ANIOPROCEDIMIENTO, ",
			"FACTCONVENIO, IDPARTIDAPRESUPUESTARIA)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idturno,jdbcType=DECIMAL}, ",
			"#{anio,jdbcType=DECIMAL}, #{numero,jdbcType=DECIMAL}, #{fechaentrada,jdbcType=TIMESTAMP}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{estado,jdbcType=VARCHAR}, #{fechafin,jdbcType=TIMESTAMP}, ",
			"#{resumenasunto,jdbcType=VARCHAR}, #{delitos,jdbcType=VARCHAR}, ",
			"#{procurador,jdbcType=VARCHAR}, #{idtipodesignacolegio,jdbcType=DECIMAL}, ",
			"#{observaciones,jdbcType=VARCHAR}, #{defensajuridica,jdbcType=VARCHAR}, ",
			"#{idprocurador,jdbcType=DECIMAL}, #{idinstitucionProcur,jdbcType=DECIMAL}, ",
			"#{idjuzgado,jdbcType=DECIMAL}, #{idinstitucionJuzg,jdbcType=DECIMAL}, ",
			"#{fechaanulacion,jdbcType=TIMESTAMP}, #{codigo,jdbcType=VARCHAR}, ",
			"#{numprocedimiento,jdbcType=VARCHAR}, #{fechajuicio,jdbcType=TIMESTAMP}, ",
			"#{idprocedimiento,jdbcType=VARCHAR}, #{fechaestado,jdbcType=TIMESTAMP}, ",
			"#{idpretension,jdbcType=DECIMAL}, #{sufijo,jdbcType=VARCHAR}, ",
			"#{fechaoficiojuzgado,jdbcType=TIMESTAMP}, #{fecharecepcioncolegio,jdbcType=TIMESTAMP}, ",
			"#{fechaalta,jdbcType=TIMESTAMP}, #{art27,jdbcType=VARCHAR}, ",
			"#{nig,jdbcType=VARCHAR}, #{anioprocedimiento,jdbcType=DECIMAL}, ",
			"#{factconvenio,jdbcType=DECIMAL}, #{idpartidapresupuestaria,jdbcType=DECIMAL})" })
	int insert(ScsDesigna record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@InsertProvider(type = ScsDesignaSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsDesigna record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@SelectProvider(type = ScsDesignaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAENTRADA", property = "fechaentrada", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "RESUMENASUNTO", property = "resumenasunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DELITOS", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCURADOR", property = "procurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "idtipodesignacolegio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DEFENSAJURIDICA", property = "defensajuridica", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCURADOR", property = "idprocurador", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINSTITUCION_PROCUR", property = "idinstitucionProcur", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJUZGADO", property = "idjuzgado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINSTITUCION_JUZG", property = "idinstitucionJuzg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAANULACION", property = "fechaanulacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTO", property = "numprocedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUICIO", property = "fechajuicio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPROCEDIMIENTO", property = "idprocedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPRETENSION", property = "idpretension", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAOFICIOJUZGADO", property = "fechaoficiojuzgado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHARECEPCIONCOLEGIO", property = "fecharecepcioncolegio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ART27", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIOPROCEDIMIENTO", property = "anioprocedimiento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FACTCONVENIO", property = "factconvenio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL) })
	List<ScsDesigna> selectByExample(ScsDesignaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@Select({ "select", "IDINSTITUCION, IDTURNO, ANIO, NUMERO, FECHAENTRADA, FECHAMODIFICACION, USUMODIFICACION, ",
			"ESTADO, FECHAFIN, RESUMENASUNTO, DELITOS, PROCURADOR, IDTIPODESIGNACOLEGIO, ",
			"OBSERVACIONES, DEFENSAJURIDICA, IDPROCURADOR, IDINSTITUCION_PROCUR, IDJUZGADO, ",
			"IDINSTITUCION_JUZG, FECHAANULACION, CODIGO, NUMPROCEDIMIENTO, FECHAJUICIO, IDPROCEDIMIENTO, ",
			"FECHAESTADO, IDPRETENSION, SUFIJO, FECHAOFICIOJUZGADO, FECHARECEPCIONCOLEGIO, ",
			"FECHAALTA, ART27, NIG, ANIOPROCEDIMIENTO, FACTCONVENIO, IDPARTIDAPRESUPUESTARIA", "from SCS_DESIGNA",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
			"and ANIO = #{anio,jdbcType=DECIMAL}", "and NUMERO = #{numero,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAENTRADA", property = "fechaentrada", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "RESUMENASUNTO", property = "resumenasunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DELITOS", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCURADOR", property = "procurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "idtipodesignacolegio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DEFENSAJURIDICA", property = "defensajuridica", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCURADOR", property = "idprocurador", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINSTITUCION_PROCUR", property = "idinstitucionProcur", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJUZGADO", property = "idjuzgado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINSTITUCION_JUZG", property = "idinstitucionJuzg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAANULACION", property = "fechaanulacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTO", property = "numprocedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUICIO", property = "fechajuicio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPROCEDIMIENTO", property = "idprocedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPRETENSION", property = "idpretension", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAOFICIOJUZGADO", property = "fechaoficiojuzgado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHARECEPCIONCOLEGIO", property = "fecharecepcioncolegio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ART27", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIOPROCEDIMIENTO", property = "anioprocedimiento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FACTCONVENIO", property = "factconvenio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL) })
	ScsDesigna selectByPrimaryKey(ScsDesignaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@UpdateProvider(type = ScsDesignaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsDesigna record, @Param("example") ScsDesignaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@UpdateProvider(type = ScsDesignaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsDesigna record, @Param("example") ScsDesignaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@UpdateProvider(type = ScsDesignaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsDesigna record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_DESIGNA
	 * @mbg.generated  Mon Jun 07 07:51:06 CEST 2021
	 */
	@Update({ "update SCS_DESIGNA", "set FECHAENTRADA = #{fechaentrada,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "ESTADO = #{estado,jdbcType=VARCHAR},",
			"FECHAFIN = #{fechafin,jdbcType=TIMESTAMP},", "RESUMENASUNTO = #{resumenasunto,jdbcType=VARCHAR},",
			"DELITOS = #{delitos,jdbcType=VARCHAR},", "PROCURADOR = #{procurador,jdbcType=VARCHAR},",
			"IDTIPODESIGNACOLEGIO = #{idtipodesignacolegio,jdbcType=DECIMAL},",
			"OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
			"DEFENSAJURIDICA = #{defensajuridica,jdbcType=VARCHAR},",
			"IDPROCURADOR = #{idprocurador,jdbcType=DECIMAL},",
			"IDINSTITUCION_PROCUR = #{idinstitucionProcur,jdbcType=DECIMAL},",
			"IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL},", "IDINSTITUCION_JUZG = #{idinstitucionJuzg,jdbcType=DECIMAL},",
			"FECHAANULACION = #{fechaanulacion,jdbcType=TIMESTAMP},", "CODIGO = #{codigo,jdbcType=VARCHAR},",
			"NUMPROCEDIMIENTO = #{numprocedimiento,jdbcType=VARCHAR},",
			"FECHAJUICIO = #{fechajuicio,jdbcType=TIMESTAMP},",
			"IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR},",
			"FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP},", "IDPRETENSION = #{idpretension,jdbcType=DECIMAL},",
			"SUFIJO = #{sufijo,jdbcType=VARCHAR},", "FECHAOFICIOJUZGADO = #{fechaoficiojuzgado,jdbcType=TIMESTAMP},",
			"FECHARECEPCIONCOLEGIO = #{fecharecepcioncolegio,jdbcType=TIMESTAMP},",
			"FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP},", "ART27 = #{art27,jdbcType=VARCHAR},",
			"NIG = #{nig,jdbcType=VARCHAR},", "ANIOPROCEDIMIENTO = #{anioprocedimiento,jdbcType=DECIMAL},",
			"FACTCONVENIO = #{factconvenio,jdbcType=DECIMAL},",
			"IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
			"and ANIO = #{anio,jdbcType=DECIMAL}", "and NUMERO = #{numero,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsDesigna record);
}