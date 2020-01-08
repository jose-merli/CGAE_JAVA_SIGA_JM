package org.itcgae.siga.db.mappers;

import java.math.BigDecimal;
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
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample;

public interface ScsEejgPeticionesMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@SelectProvider(type = ScsEejgPeticionesSqlProvider.class, method = "countByExample")
	long countByExample(ScsEejgPeticionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@DeleteProvider(type = ScsEejgPeticionesSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsEejgPeticionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@Delete({ "delete from SCS_EEJG_PETICIONES", "where IDPETICION = #{idpeticion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(BigDecimal idpeticion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@Insert({ "insert into SCS_EEJG_PETICIONES (IDPETICION, IDUSUARIOPETICION, ", "FECHAPETICION, ESTADO, ",
			"IDSOLICITUD, IDINSTITUCION, ", "IDTIPOEJG, ANIO, ", "NUMERO, IDPERSONA, ",
			"NUMEROINTENTOSSOLICITUD, NUMEROINTENTOSCONSULTA, ", "IDXML, FECHAMODIFICACION, ",
			"USUMODIFICACION, FECHASOLICITUD, ", "FECHACONSULTA, IDIOMA, ", "NUMEROINTENTOSPENDIENTEINFO, NIF, ",
			"NOMBRE, APELLIDO1, ", "APELLIDO2, OBSERVACIONES, ", "RUTA_PDF, IDECOMCOLA, ", "MSGERROR, CSV)",
			"values (#{idpeticion,jdbcType=DECIMAL}, #{idusuariopeticion,jdbcType=DECIMAL}, ",
			"#{fechapeticion,jdbcType=TIMESTAMP}, #{estado,jdbcType=DECIMAL}, ",
			"#{idsolicitud,jdbcType=VARCHAR}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{idtipoejg,jdbcType=DECIMAL}, #{anio,jdbcType=DECIMAL}, ",
			"#{numero,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{numerointentossolicitud,jdbcType=DECIMAL}, #{numerointentosconsulta,jdbcType=DECIMAL}, ",
			"#{idxml,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechasolicitud,jdbcType=TIMESTAMP}, ",
			"#{fechaconsulta,jdbcType=TIMESTAMP}, #{idioma,jdbcType=VARCHAR}, ",
			"#{numerointentospendienteinfo,jdbcType=DECIMAL}, #{nif,jdbcType=VARCHAR}, ",
			"#{nombre,jdbcType=VARCHAR}, #{apellido1,jdbcType=VARCHAR}, ",
			"#{apellido2,jdbcType=VARCHAR}, #{observaciones,jdbcType=VARCHAR}, ",
			"#{rutaPdf,jdbcType=VARCHAR}, #{idecomcola,jdbcType=DECIMAL}, ",
			"#{msgerror,jdbcType=VARCHAR}, #{csv,jdbcType=VARCHAR})" })
	int insert(ScsEejgPeticiones record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@InsertProvider(type = ScsEejgPeticionesSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsEejgPeticiones record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@SelectProvider(type = ScsEejgPeticionesSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDPETICION", property = "idpeticion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDUSUARIOPETICION", property = "idusuariopeticion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAPETICION", property = "fechapeticion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMEROINTENTOSSOLICITUD", property = "numerointentossolicitud", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMEROINTENTOSCONSULTA", property = "numerointentosconsulta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDXML", property = "idxml", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHACONSULTA", property = "fechaconsulta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDIOMA", property = "idioma", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROINTENTOSPENDIENTEINFO", property = "numerointentospendienteinfo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RUTA_PDF", property = "rutaPdf", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MSGERROR", property = "msgerror", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CSV", property = "csv", jdbcType = JdbcType.VARCHAR) })
	List<ScsEejgPeticiones> selectByExample(ScsEejgPeticionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@Select({ "select", "IDPETICION, IDUSUARIOPETICION, FECHAPETICION, ESTADO, IDSOLICITUD, IDINSTITUCION, ",
			"IDTIPOEJG, ANIO, NUMERO, IDPERSONA, NUMEROINTENTOSSOLICITUD, NUMEROINTENTOSCONSULTA, ",
			"IDXML, FECHAMODIFICACION, USUMODIFICACION, FECHASOLICITUD, FECHACONSULTA, IDIOMA, ",
			"NUMEROINTENTOSPENDIENTEINFO, NIF, NOMBRE, APELLIDO1, APELLIDO2, OBSERVACIONES, ",
			"RUTA_PDF, IDECOMCOLA, MSGERROR, CSV", "from SCS_EEJG_PETICIONES",
			"where IDPETICION = #{idpeticion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDPETICION", property = "idpeticion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDUSUARIOPETICION", property = "idusuariopeticion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAPETICION", property = "fechapeticion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMEROINTENTOSSOLICITUD", property = "numerointentossolicitud", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMEROINTENTOSCONSULTA", property = "numerointentosconsulta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDXML", property = "idxml", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHACONSULTA", property = "fechaconsulta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDIOMA", property = "idioma", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROINTENTOSPENDIENTEINFO", property = "numerointentospendienteinfo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RUTA_PDF", property = "rutaPdf", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.DECIMAL),
			@Result(column = "MSGERROR", property = "msgerror", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CSV", property = "csv", jdbcType = JdbcType.VARCHAR) })
	ScsEejgPeticiones selectByPrimaryKey(BigDecimal idpeticion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@UpdateProvider(type = ScsEejgPeticionesSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsEejgPeticiones record,
			@Param("example") ScsEejgPeticionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@UpdateProvider(type = ScsEejgPeticionesSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsEejgPeticiones record, @Param("example") ScsEejgPeticionesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@UpdateProvider(type = ScsEejgPeticionesSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsEejgPeticiones record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue Jan 07 08:26:49 CET 2020
	 */
	@Update({ "update SCS_EEJG_PETICIONES", "set IDUSUARIOPETICION = #{idusuariopeticion,jdbcType=DECIMAL},",
			"FECHAPETICION = #{fechapeticion,jdbcType=TIMESTAMP},", "ESTADO = #{estado,jdbcType=DECIMAL},",
			"IDSOLICITUD = #{idsolicitud,jdbcType=VARCHAR},", "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
			"IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL},", "ANIO = #{anio,jdbcType=DECIMAL},",
			"NUMERO = #{numero,jdbcType=DECIMAL},", "IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
			"NUMEROINTENTOSSOLICITUD = #{numerointentossolicitud,jdbcType=DECIMAL},",
			"NUMEROINTENTOSCONSULTA = #{numerointentosconsulta,jdbcType=DECIMAL},",
			"IDXML = #{idxml,jdbcType=DECIMAL},", "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP},",
			"FECHACONSULTA = #{fechaconsulta,jdbcType=TIMESTAMP},", "IDIOMA = #{idioma,jdbcType=VARCHAR},",
			"NUMEROINTENTOSPENDIENTEINFO = #{numerointentospendienteinfo,jdbcType=DECIMAL},",
			"NIF = #{nif,jdbcType=VARCHAR},", "NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"APELLIDO1 = #{apellido1,jdbcType=VARCHAR},", "APELLIDO2 = #{apellido2,jdbcType=VARCHAR},",
			"OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},", "RUTA_PDF = #{rutaPdf,jdbcType=VARCHAR},",
			"IDECOMCOLA = #{idecomcola,jdbcType=DECIMAL},", "MSGERROR = #{msgerror,jdbcType=VARCHAR},",
			"CSV = #{csv,jdbcType=VARCHAR}", "where IDPETICION = #{idpeticion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsEejgPeticiones record);
}