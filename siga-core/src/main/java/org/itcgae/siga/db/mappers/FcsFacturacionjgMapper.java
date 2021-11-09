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
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.entities.FcsFacturacionjgExample;
import org.itcgae.siga.db.entities.FcsFacturacionjgKey;

public interface FcsFacturacionjgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@SelectProvider(type = FcsFacturacionjgSqlProvider.class, method = "countByExample")
	long countByExample(FcsFacturacionjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@DeleteProvider(type = FcsFacturacionjgSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsFacturacionjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@Delete({ "delete from FCS_FACTURACIONJG", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsFacturacionjgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@Insert({ "insert into FCS_FACTURACIONJG (IDINSTITUCION, IDFACTURACION, ", "FECHADESDE, FECHAHASTA, ",
			"NOMBRE, IMPORTETOTAL, ", "IMPORTEOFICIO, IMPORTEGUARDIA, ", "IMPORTESOJ, IMPORTEEJG, ",
			"PREVISION, REGULARIZACION, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"IDFACTURACION_REGULARIZA, NOMBREFISICO, ", "IDECOMCOLA, VISIBLE, ", "IDPARTIDAPRESUPUESTARIA)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idfacturacion,jdbcType=DECIMAL}, ",
			"#{fechadesde,jdbcType=TIMESTAMP}, #{fechahasta,jdbcType=TIMESTAMP}, ",
			"#{nombre,jdbcType=VARCHAR}, #{importetotal,jdbcType=DECIMAL}, ",
			"#{importeoficio,jdbcType=DECIMAL}, #{importeguardia,jdbcType=DECIMAL}, ",
			"#{importesoj,jdbcType=DECIMAL}, #{importeejg,jdbcType=DECIMAL}, ",
			"#{prevision,jdbcType=VARCHAR}, #{regularizacion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{idfacturacionRegulariza,jdbcType=DECIMAL}, #{nombrefisico,jdbcType=VARCHAR}, ",
			"#{idecomcola,jdbcType=DECIMAL}, #{visible,jdbcType=VARCHAR}, ",
			"#{idpartidapresupuestaria,jdbcType=DECIMAL})" })
	int insert(FcsFacturacionjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@InsertProvider(type = FcsFacturacionjgSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsFacturacionjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@SelectProvider(type = FcsFacturacionjgSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTETOTAL", property = "importetotal", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEOFICIO", property = "importeoficio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEGUARDIA", property = "importeguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTESOJ", property = "importesoj", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEEJG", property = "importeejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PREVISION", property = "prevision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFACTURACION_REGULARIZA", property = "idfacturacionRegulariza", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBREFISICO", property = "nombrefisico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL) })
	List<FcsFacturacionjg> selectByExample(FcsFacturacionjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDFACTURACION, FECHADESDE, FECHAHASTA, NOMBRE, IMPORTETOTAL, ",
			"IMPORTEOFICIO, IMPORTEGUARDIA, IMPORTESOJ, IMPORTEEJG, PREVISION, REGULARIZACION, ",
			"FECHAMODIFICACION, USUMODIFICACION, IDFACTURACION_REGULARIZA, NOMBREFISICO, ",
			"IDECOMCOLA, VISIBLE, IDPARTIDAPRESUPUESTARIA", "from FCS_FACTURACIONJG",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTETOTAL", property = "importetotal", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEOFICIO", property = "importeoficio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEGUARDIA", property = "importeguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTESOJ", property = "importesoj", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEEJG", property = "importeejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PREVISION", property = "prevision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFACTURACION_REGULARIZA", property = "idfacturacionRegulariza", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBREFISICO", property = "nombrefisico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL) })
	FcsFacturacionjg selectByPrimaryKey(FcsFacturacionjgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@UpdateProvider(type = FcsFacturacionjgSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsFacturacionjg record,
			@Param("example") FcsFacturacionjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@UpdateProvider(type = FcsFacturacionjgSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsFacturacionjg record, @Param("example") FcsFacturacionjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@UpdateProvider(type = FcsFacturacionjgSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsFacturacionjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	@Update({ "update FCS_FACTURACIONJG", "set FECHADESDE = #{fechadesde,jdbcType=TIMESTAMP},",
			"FECHAHASTA = #{fechahasta,jdbcType=TIMESTAMP},", "NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"IMPORTETOTAL = #{importetotal,jdbcType=DECIMAL},", "IMPORTEOFICIO = #{importeoficio,jdbcType=DECIMAL},",
			"IMPORTEGUARDIA = #{importeguardia,jdbcType=DECIMAL},", "IMPORTESOJ = #{importesoj,jdbcType=DECIMAL},",
			"IMPORTEEJG = #{importeejg,jdbcType=DECIMAL},", "PREVISION = #{prevision,jdbcType=VARCHAR},",
			"REGULARIZACION = #{regularizacion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"IDFACTURACION_REGULARIZA = #{idfacturacionRegulariza,jdbcType=DECIMAL},",
			"NOMBREFISICO = #{nombrefisico,jdbcType=VARCHAR},", "IDECOMCOLA = #{idecomcola,jdbcType=DECIMAL},",
			"VISIBLE = #{visible,jdbcType=VARCHAR},",
			"IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsFacturacionjg record);
}