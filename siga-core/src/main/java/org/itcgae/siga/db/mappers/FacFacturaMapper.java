package org.itcgae.siga.db.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturacionEliminar;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturaKey;

public interface FacFacturaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@SelectProvider(type = FacFacturaSqlProvider.class, method = "countByExample")
	long countByExample(FacFacturaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@DeleteProvider(type = FacFacturaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FacFacturaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@Delete({ "delete from FAC_FACTURA", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURA = #{idfactura,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(FacFacturaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@Insert({ "insert into FAC_FACTURA (IDINSTITUCION, IDFACTURA, ", "FECHAEMISION, CONTABILIZADA, ",
			"IDSERIEFACTURACION, IDPROGRAMACION, ", "IDFORMAPAGO, IDPERSONA, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"NUMEROFACTURA, OBSERVACIONES, ", "IDCUENTA, IDPERSONADEUDOR, ", "IDCUENTADEUDOR, CTACLIENTE, ",
			"OBSERVINFORME, IMPTOTAL, ", "IMPTOTALIVA, IMPTOTALNETO, ", "IMPTOTALANTICIPADO, IMPTOTALPAGADOPORCAJA, ",
			"IMPTOTALPAGADOSOLOCAJA, IMPTOTALPAGADOSOLOTARJETA, ", "IMPTOTALPAGADOPORBANCO, IMPTOTALPAGADO, ",
			"IMPTOTALPORPAGAR, IMPTOTALCOMPENSADO, ", "ESTADO, REFMANDATOSEPA, ", "IDMANDATO, COMISIONIDFACTURA, ",
			"TRASPASADA, ERRORTRASPASO, ", "ENVIOTRASPASO)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idfactura,jdbcType=VARCHAR}, ",
			"#{fechaemision,jdbcType=TIMESTAMP}, #{contabilizada,jdbcType=VARCHAR}, ",
			"#{idseriefacturacion,jdbcType=DECIMAL}, #{idprogramacion,jdbcType=DECIMAL}, ",
			"#{idformapago,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{numerofactura,jdbcType=VARCHAR}, #{observaciones,jdbcType=VARCHAR}, ",
			"#{idcuenta,jdbcType=DECIMAL}, #{idpersonadeudor,jdbcType=DECIMAL}, ",
			"#{idcuentadeudor,jdbcType=DECIMAL}, #{ctacliente,jdbcType=VARCHAR}, ",
			"#{observinforme,jdbcType=VARCHAR}, #{imptotal,jdbcType=DECIMAL}, ",
			"#{imptotaliva,jdbcType=DECIMAL}, #{imptotalneto,jdbcType=DECIMAL}, ",
			"#{imptotalanticipado,jdbcType=DECIMAL}, #{imptotalpagadoporcaja,jdbcType=DECIMAL}, ",
			"#{imptotalpagadosolocaja,jdbcType=DECIMAL}, #{imptotalpagadosolotarjeta,jdbcType=DECIMAL}, ",
			"#{imptotalpagadoporbanco,jdbcType=DECIMAL}, #{imptotalpagado,jdbcType=DECIMAL}, ",
			"#{imptotalporpagar,jdbcType=DECIMAL}, #{imptotalcompensado,jdbcType=DECIMAL}, ",
			"#{estado,jdbcType=DECIMAL}, #{refmandatosepa,jdbcType=VARCHAR}, ",
			"#{idmandato,jdbcType=DECIMAL}, #{comisionidfactura,jdbcType=VARCHAR}, ",
			"#{traspasada,jdbcType=VARCHAR}, #{errortraspaso,jdbcType=VARCHAR}, ",
			"#{enviotraspaso,jdbcType=VARCHAR})" })
	int insert(FacFactura record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@InsertProvider(type = FacFacturaSqlProvider.class, method = "insertSelective")
	int insertSelective(FacFactura record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@SelectProvider(type = FacFacturaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURA", property = "idfactura", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAEMISION", property = "fechaemision", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CONTABILIZADA", property = "contabilizada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFORMAPAGO", property = "idformapago", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMEROFACTURA", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCUENTA", property = "idcuenta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONADEUDOR", property = "idpersonadeudor", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCUENTADEUDOR", property = "idcuentadeudor", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CTACLIENTE", property = "ctacliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVINFORME", property = "observinforme", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTAL", property = "imptotal", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALIVA", property = "imptotaliva", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALNETO", property = "imptotalneto", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALANTICIPADO", property = "imptotalanticipado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOPORCAJA", property = "imptotalpagadoporcaja", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOSOLOCAJA", property = "imptotalpagadosolocaja", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOSOLOTARJETA", property = "imptotalpagadosolotarjeta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOPORBANCO", property = "imptotalpagadoporbanco", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADO", property = "imptotalpagado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPORPAGAR", property = "imptotalporpagar", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALCOMPENSADO", property = "imptotalcompensado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REFMANDATOSEPA", property = "refmandatosepa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMANDATO", property = "idmandato", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COMISIONIDFACTURA", property = "comisionidfactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TRASPASADA", property = "traspasada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ERRORTRASPASO", property = "errortraspaso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENVIOTRASPASO", property = "enviotraspaso", jdbcType = JdbcType.VARCHAR) })
	List<FacFactura> selectByExample(FacFacturaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@Select({ "select", "IDINSTITUCION, IDFACTURA, FECHAEMISION, CONTABILIZADA, IDSERIEFACTURACION, IDPROGRAMACION, ",
			"IDFORMAPAGO, IDPERSONA, FECHAMODIFICACION, USUMODIFICACION, NUMEROFACTURA, OBSERVACIONES, ",
			"IDCUENTA, IDPERSONADEUDOR, IDCUENTADEUDOR, CTACLIENTE, OBSERVINFORME, IMPTOTAL, ",
			"IMPTOTALIVA, IMPTOTALNETO, IMPTOTALANTICIPADO, IMPTOTALPAGADOPORCAJA, IMPTOTALPAGADOSOLOCAJA, ",
			"IMPTOTALPAGADOSOLOTARJETA, IMPTOTALPAGADOPORBANCO, IMPTOTALPAGADO, IMPTOTALPORPAGAR, ",
			"IMPTOTALCOMPENSADO, ESTADO, REFMANDATOSEPA, IDMANDATO, COMISIONIDFACTURA, TRASPASADA, ",
			"ERRORTRASPASO, ENVIOTRASPASO", "from FAC_FACTURA",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURA = #{idfactura,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURA", property = "idfactura", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAEMISION", property = "fechaemision", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CONTABILIZADA", property = "contabilizada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFORMAPAGO", property = "idformapago", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMEROFACTURA", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCUENTA", property = "idcuenta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONADEUDOR", property = "idpersonadeudor", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCUENTADEUDOR", property = "idcuentadeudor", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CTACLIENTE", property = "ctacliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVINFORME", property = "observinforme", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTAL", property = "imptotal", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALIVA", property = "imptotaliva", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALNETO", property = "imptotalneto", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALANTICIPADO", property = "imptotalanticipado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOPORCAJA", property = "imptotalpagadoporcaja", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOSOLOCAJA", property = "imptotalpagadosolocaja", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOSOLOTARJETA", property = "imptotalpagadosolotarjeta", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADOPORBANCO", property = "imptotalpagadoporbanco", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPAGADO", property = "imptotalpagado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALPORPAGAR", property = "imptotalporpagar", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPTOTALCOMPENSADO", property = "imptotalcompensado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REFMANDATOSEPA", property = "refmandatosepa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMANDATO", property = "idmandato", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COMISIONIDFACTURA", property = "comisionidfactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TRASPASADA", property = "traspasada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ERRORTRASPASO", property = "errortraspaso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENVIOTRASPASO", property = "enviotraspaso", jdbcType = JdbcType.VARCHAR) })
	FacFactura selectByPrimaryKey(FacFacturaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@UpdateProvider(type = FacFacturaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FacFactura record, @Param("example") FacFacturaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@UpdateProvider(type = FacFacturaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FacFactura record, @Param("example") FacFacturaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@UpdateProvider(type = FacFacturaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FacFactura record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FAC_FACTURA
	 * @mbg.generated  Thu Oct 14 14:59:55 CEST 2021
	 */
	@Update({ "update FAC_FACTURA", "set FECHAEMISION = #{fechaemision,jdbcType=TIMESTAMP},",
			"CONTABILIZADA = #{contabilizada,jdbcType=VARCHAR},",
			"IDSERIEFACTURACION = #{idseriefacturacion,jdbcType=DECIMAL},",
			"IDPROGRAMACION = #{idprogramacion,jdbcType=DECIMAL},", "IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL},",
			"IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"NUMEROFACTURA = #{numerofactura,jdbcType=VARCHAR},", "OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
			"IDCUENTA = #{idcuenta,jdbcType=DECIMAL},", "IDPERSONADEUDOR = #{idpersonadeudor,jdbcType=DECIMAL},",
			"IDCUENTADEUDOR = #{idcuentadeudor,jdbcType=DECIMAL},", "CTACLIENTE = #{ctacliente,jdbcType=VARCHAR},",
			"OBSERVINFORME = #{observinforme,jdbcType=VARCHAR},", "IMPTOTAL = #{imptotal,jdbcType=DECIMAL},",
			"IMPTOTALIVA = #{imptotaliva,jdbcType=DECIMAL},", "IMPTOTALNETO = #{imptotalneto,jdbcType=DECIMAL},",
			"IMPTOTALANTICIPADO = #{imptotalanticipado,jdbcType=DECIMAL},",
			"IMPTOTALPAGADOPORCAJA = #{imptotalpagadoporcaja,jdbcType=DECIMAL},",
			"IMPTOTALPAGADOSOLOCAJA = #{imptotalpagadosolocaja,jdbcType=DECIMAL},",
			"IMPTOTALPAGADOSOLOTARJETA = #{imptotalpagadosolotarjeta,jdbcType=DECIMAL},",
			"IMPTOTALPAGADOPORBANCO = #{imptotalpagadoporbanco,jdbcType=DECIMAL},",
			"IMPTOTALPAGADO = #{imptotalpagado,jdbcType=DECIMAL},",
			"IMPTOTALPORPAGAR = #{imptotalporpagar,jdbcType=DECIMAL},",
			"IMPTOTALCOMPENSADO = #{imptotalcompensado,jdbcType=DECIMAL},", "ESTADO = #{estado,jdbcType=DECIMAL},",
			"REFMANDATOSEPA = #{refmandatosepa,jdbcType=VARCHAR},", "IDMANDATO = #{idmandato,jdbcType=DECIMAL},",
			"COMISIONIDFACTURA = #{comisionidfactura,jdbcType=VARCHAR},",
			"TRASPASADA = #{traspasada,jdbcType=VARCHAR},", "ERRORTRASPASO = #{errortraspaso,jdbcType=VARCHAR},",
			"ENVIOTRASPASO = #{enviotraspaso,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURA = #{idfactura,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(FacFactura record);
	
//	
//	 PROCEDURE ELIMINARFACTURACION   (
//		        P_IDINSTITUCION IN NUMBER,
//		        P_IDSERIEFACTURACION IN NUMBER,
//		        P_IDPROGRAMACION IN NUMBER,
//		        P_USUMODIFICACION IN NUMBER,
//		        P_CODRETORNO OUT VARCHAR2,
//		        P_DATOSERROR OUT VARCHAR2);
	
	@Update(value = "{CALL PKG_SIGA_FACTURACION.ELIMINARFACTURACION ("
					+ "#{idInstitucion,mode=IN},"
					+ "#{idSerieFacturacion, mode=IN},"
					+ "#{idProgramacion, mode=IN},"
					+ "#{idUsuarioModificacion, mode=IN},"
					+ "#{codRetorno, mode=OUT, jdbcType=VARCHAR},"
					+ "#{datosError, mode=OUT, jdbcType=VARCHAR})}")
	@Options(statementType = StatementType.CALLABLE)
	@ResultType(FacFacturacionEliminar.class)
	void eliminarFacturacion(FacFacturacionEliminar facturaEliminar);
	 //I put this field void because I don't need this method return nothing.
	
}