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
import org.itcgae.siga.db.entities.CenSolicitudincorporacion;
import org.itcgae.siga.db.entities.CenSolicitudincorporacionExample;

public interface CenSolicitudincorporacionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@SelectProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "countByExample")
	long countByExample(CenSolicitudincorporacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@DeleteProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenSolicitudincorporacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@Delete({ "delete from CEN_SOLICITUDINCORPORACION", "where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idsolicitud);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@SelectKey(statement = "SELECT SEQ_SOLICITUDINCORPORACION.NEXTVAL FROM DUAL", keyProperty = "idsolicitud", before = true, resultType = Long.class)
	@Insert({ "insert into CEN_SOLICITUDINCORPORACION (IDSOLICITUD, FECHASOLICITUD, ", "IDTRATAMIENTO, NOMBRE, ",
			"APELLIDO1, APELLIDO2, ", "NUMEROIDENTIFICADOR, DOMICILIO, ", "CODIGOPOSTAL, TELEFONO1, ",
			"CORREOELECTRONICO, IDINSTITUCION, ", "IDESTADO, IDTIPOSOLICITUD, ",
			"IDTIPOCOLEGIACION, FECHAMODIFICACION, ", "USUMODIFICACION, FECHANACIMIENTO, ",
			"IDTIPOIDENTIFICACION, IDPROVINCIA, ", "IDPOBLACION, FECHAESTADO, ", "NCOLEGIADO, TELEFONO2, ",
			"MOVIL, FAX1, FAX2, ", "OBSERVACIONES, IDESTADOCIVIL, ", "IDPAIS, NATURALDE, ",
			"SEXO, POBLACIONEXTRANJERA, ", "IDMODALIDADDOCUMENTACION, FECHAESTADOCOLEGIAL, ",
			"TITULAR, CODIGOSUCURSAL, ", "CBO_CODIGO, DIGITOCONTROL, ", "NUMEROCUENTA, ABONOSJCS, ",
			"ABONOCARGO, RESIDENTE, ", "IDPERSONA, FECHAALTA, ", "IDPERSONATEMP, IDDIRECCIONTEMP, ",
			"IBAN, FECHAESTADOSOLICITUD, ", "NOMBREBANCO, BIC, ", "NUM_REGISTRO, CLAVECONSULTAREGTEL, ",
			"NUM_EXPEDIENTE)", "values (#{idsolicitud,jdbcType=DECIMAL}, #{fechasolicitud,jdbcType=TIMESTAMP}, ",
			"#{idtratamiento,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
			"#{apellido1,jdbcType=VARCHAR}, #{apellido2,jdbcType=VARCHAR}, ",
			"#{numeroidentificador,jdbcType=VARCHAR}, #{domicilio,jdbcType=VARCHAR}, ",
			"#{codigopostal,jdbcType=VARCHAR}, #{telefono1,jdbcType=VARCHAR}, ",
			"#{correoelectronico,jdbcType=VARCHAR}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{idestado,jdbcType=DECIMAL}, #{idtiposolicitud,jdbcType=DECIMAL}, ",
			"#{idtipocolegiacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechanacimiento,jdbcType=TIMESTAMP}, ",
			"#{idtipoidentificacion,jdbcType=DECIMAL}, #{idprovincia,jdbcType=VARCHAR}, ",
			"#{idpoblacion,jdbcType=VARCHAR}, #{fechaestado,jdbcType=TIMESTAMP}, ",
			"#{ncolegiado,jdbcType=VARCHAR}, #{telefono2,jdbcType=VARCHAR}, ",
			"#{movil,jdbcType=VARCHAR}, #{fax1,jdbcType=VARCHAR}, #{fax2,jdbcType=VARCHAR}, ",
			"#{observaciones,jdbcType=VARCHAR}, #{idestadocivil,jdbcType=DECIMAL}, ",
			"#{idpais,jdbcType=VARCHAR}, #{naturalde,jdbcType=VARCHAR}, ",
			"#{sexo,jdbcType=VARCHAR}, #{poblacionextranjera,jdbcType=VARCHAR}, ",
			"#{idmodalidaddocumentacion,jdbcType=DECIMAL}, #{fechaestadocolegial,jdbcType=TIMESTAMP}, ",
			"#{titular,jdbcType=VARCHAR}, #{codigosucursal,jdbcType=VARCHAR}, ",
			"#{cboCodigo,jdbcType=VARCHAR}, #{digitocontrol,jdbcType=VARCHAR}, ",
			"#{numerocuenta,jdbcType=VARCHAR}, #{abonosjcs,jdbcType=VARCHAR}, ",
			"#{abonocargo,jdbcType=VARCHAR}, #{residente,jdbcType=VARCHAR}, ",
			"#{idpersona,jdbcType=DECIMAL}, #{fechaalta,jdbcType=TIMESTAMP}, ",
			"#{idpersonatemp,jdbcType=DECIMAL}, #{iddirecciontemp,jdbcType=DECIMAL}, ",
			"#{iban,jdbcType=VARCHAR}, #{fechaestadosolicitud,jdbcType=TIMESTAMP}, ",
			"#{nombrebanco,jdbcType=VARCHAR}, #{bic,jdbcType=VARCHAR}, ",
			"#{numRegistro,jdbcType=VARCHAR}, #{claveconsultaregtel,jdbcType=VARCHAR}, ",
			"#{numExpediente,jdbcType=VARCHAR})" })
	int insert(CenSolicitudincorporacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@SelectKey(statement = "SELECT SEQ_SOLICITUDINCORPORACION.NEXTVAL FROM DUAL", keyProperty = "idsolicitud", before = true, resultType = Long.class)
	@InsertProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "insertSelective")
	int insertSelective(CenSolicitudincorporacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@SelectProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTRATAMIENTO", property = "idtratamiento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROIDENTIFICADOR", property = "numeroidentificador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correoelectronico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOSOLICITUD", property = "idtiposolicitud", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOCOLEGIACION", property = "idtipocolegiacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOIDENTIFICACION", property = "idtipoidentificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADOCIVIL", property = "idestadocivil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NATURALDE", property = "naturalde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACIONEXTRANJERA", property = "poblacionextranjera", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODALIDADDOCUMENTACION", property = "idmodalidaddocumentacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAESTADOCOLEGIAL", property = "fechaestadocolegial", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "TITULAR", property = "titular", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOSUCURSAL", property = "codigosucursal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CBO_CODIGO", property = "cboCodigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIGITOCONTROL", property = "digitocontrol", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCUENTA", property = "numerocuenta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOSJCS", property = "abonosjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOCARGO", property = "abonocargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPERSONATEMP", property = "idpersonatemp", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDDIRECCIONTEMP", property = "iddirecciontemp", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADOSOLICITUD", property = "fechaestadosolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NOMBREBANCO", property = "nombrebanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_REGISTRO", property = "numRegistro", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVECONSULTAREGTEL", property = "claveconsultaregtel", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_EXPEDIENTE", property = "numExpediente", jdbcType = JdbcType.VARCHAR) })
	List<CenSolicitudincorporacion> selectByExample(CenSolicitudincorporacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@Select({ "select",
			"IDSOLICITUD, FECHASOLICITUD, IDTRATAMIENTO, NOMBRE, APELLIDO1, APELLIDO2, NUMEROIDENTIFICADOR, ",
			"DOMICILIO, CODIGOPOSTAL, TELEFONO1, CORREOELECTRONICO, IDINSTITUCION, IDESTADO, ",
			"IDTIPOSOLICITUD, IDTIPOCOLEGIACION, FECHAMODIFICACION, USUMODIFICACION, FECHANACIMIENTO, ",
			"IDTIPOIDENTIFICACION, IDPROVINCIA, IDPOBLACION, FECHAESTADO, NCOLEGIADO, TELEFONO2, ",
			"MOVIL, FAX1, FAX2, OBSERVACIONES, IDESTADOCIVIL, IDPAIS, NATURALDE, SEXO, POBLACIONEXTRANJERA, ",
			"IDMODALIDADDOCUMENTACION, FECHAESTADOCOLEGIAL, TITULAR, CODIGOSUCURSAL, CBO_CODIGO, ",
			"DIGITOCONTROL, NUMEROCUENTA, ABONOSJCS, ABONOCARGO, RESIDENTE, IDPERSONA, FECHAALTA, ",
			"IDPERSONATEMP, IDDIRECCIONTEMP, IBAN, FECHAESTADOSOLICITUD, NOMBREBANCO, BIC, ",
			"NUM_REGISTRO, CLAVECONSULTAREGTEL, NUM_EXPEDIENTE", "from CEN_SOLICITUDINCORPORACION",
			"where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTRATAMIENTO", property = "idtratamiento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROIDENTIFICADOR", property = "numeroidentificador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correoelectronico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOSOLICITUD", property = "idtiposolicitud", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOCOLEGIACION", property = "idtipocolegiacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOIDENTIFICACION", property = "idtipoidentificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADOCIVIL", property = "idestadocivil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPAIS", property = "idpais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NATURALDE", property = "naturalde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACIONEXTRANJERA", property = "poblacionextranjera", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODALIDADDOCUMENTACION", property = "idmodalidaddocumentacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAESTADOCOLEGIAL", property = "fechaestadocolegial", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "TITULAR", property = "titular", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOSUCURSAL", property = "codigosucursal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CBO_CODIGO", property = "cboCodigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIGITOCONTROL", property = "digitocontrol", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCUENTA", property = "numerocuenta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOSJCS", property = "abonosjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOCARGO", property = "abonocargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPERSONATEMP", property = "idpersonatemp", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDDIRECCIONTEMP", property = "iddirecciontemp", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADOSOLICITUD", property = "fechaestadosolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NOMBREBANCO", property = "nombrebanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_REGISTRO", property = "numRegistro", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVECONSULTAREGTEL", property = "claveconsultaregtel", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_EXPEDIENTE", property = "numExpediente", jdbcType = JdbcType.VARCHAR) })
	CenSolicitudincorporacion selectByPrimaryKey(Long idsolicitud);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@UpdateProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenSolicitudincorporacion record,
			@Param("example") CenSolicitudincorporacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@UpdateProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenSolicitudincorporacion record,
			@Param("example") CenSolicitudincorporacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@UpdateProvider(type = CenSolicitudincorporacionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenSolicitudincorporacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.CEN_SOLICITUDINCORPORACION
	 * @mbg.generated  Wed Jan 26 12:20:14 CET 2022
	 */
	@Update({ "update CEN_SOLICITUDINCORPORACION", "set FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP},",
			"IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL},", "NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"APELLIDO1 = #{apellido1,jdbcType=VARCHAR},", "APELLIDO2 = #{apellido2,jdbcType=VARCHAR},",
			"NUMEROIDENTIFICADOR = #{numeroidentificador,jdbcType=VARCHAR},",
			"DOMICILIO = #{domicilio,jdbcType=VARCHAR},", "CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR},",
			"TELEFONO1 = #{telefono1,jdbcType=VARCHAR},", "CORREOELECTRONICO = #{correoelectronico,jdbcType=VARCHAR},",
			"IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},", "IDESTADO = #{idestado,jdbcType=DECIMAL},",
			"IDTIPOSOLICITUD = #{idtiposolicitud,jdbcType=DECIMAL},",
			"IDTIPOCOLEGIACION = #{idtipocolegiacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHANACIMIENTO = #{fechanacimiento,jdbcType=TIMESTAMP},",
			"IDTIPOIDENTIFICACION = #{idtipoidentificacion,jdbcType=DECIMAL},",
			"IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR},", "IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR},",
			"FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP},", "NCOLEGIADO = #{ncolegiado,jdbcType=VARCHAR},",
			"TELEFONO2 = #{telefono2,jdbcType=VARCHAR},", "MOVIL = #{movil,jdbcType=VARCHAR},",
			"FAX1 = #{fax1,jdbcType=VARCHAR},", "FAX2 = #{fax2,jdbcType=VARCHAR},",
			"OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},", "IDESTADOCIVIL = #{idestadocivil,jdbcType=DECIMAL},",
			"IDPAIS = #{idpais,jdbcType=VARCHAR},", "NATURALDE = #{naturalde,jdbcType=VARCHAR},",
			"SEXO = #{sexo,jdbcType=VARCHAR},", "POBLACIONEXTRANJERA = #{poblacionextranjera,jdbcType=VARCHAR},",
			"IDMODALIDADDOCUMENTACION = #{idmodalidaddocumentacion,jdbcType=DECIMAL},",
			"FECHAESTADOCOLEGIAL = #{fechaestadocolegial,jdbcType=TIMESTAMP},",
			"TITULAR = #{titular,jdbcType=VARCHAR},", "CODIGOSUCURSAL = #{codigosucursal,jdbcType=VARCHAR},",
			"CBO_CODIGO = #{cboCodigo,jdbcType=VARCHAR},", "DIGITOCONTROL = #{digitocontrol,jdbcType=VARCHAR},",
			"NUMEROCUENTA = #{numerocuenta,jdbcType=VARCHAR},", "ABONOSJCS = #{abonosjcs,jdbcType=VARCHAR},",
			"ABONOCARGO = #{abonocargo,jdbcType=VARCHAR},", "RESIDENTE = #{residente,jdbcType=VARCHAR},",
			"IDPERSONA = #{idpersona,jdbcType=DECIMAL},", "FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP},",
			"IDPERSONATEMP = #{idpersonatemp,jdbcType=DECIMAL},",
			"IDDIRECCIONTEMP = #{iddirecciontemp,jdbcType=DECIMAL},", "IBAN = #{iban,jdbcType=VARCHAR},",
			"FECHAESTADOSOLICITUD = #{fechaestadosolicitud,jdbcType=TIMESTAMP},",
			"NOMBREBANCO = #{nombrebanco,jdbcType=VARCHAR},", "BIC = #{bic,jdbcType=VARCHAR},",
			"NUM_REGISTRO = #{numRegistro,jdbcType=VARCHAR},",
			"CLAVECONSULTAREGTEL = #{claveconsultaregtel,jdbcType=VARCHAR},",
			"NUM_EXPEDIENTE = #{numExpediente,jdbcType=VARCHAR}",
			"where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenSolicitudincorporacion record);
}