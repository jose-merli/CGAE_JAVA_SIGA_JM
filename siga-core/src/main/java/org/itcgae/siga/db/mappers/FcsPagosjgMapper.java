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
import org.itcgae.siga.db.entities.FcsPagosjg;
import org.itcgae.siga.db.entities.FcsPagosjgExample;
import org.itcgae.siga.db.entities.FcsPagosjgKey;

public interface FcsPagosjgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@SelectProvider(type = FcsPagosjgSqlProvider.class, method = "countByExample")
	long countByExample(FcsPagosjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@DeleteProvider(type = FcsPagosjgSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsPagosjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@Delete({ "delete from FCS_PAGOSJG", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPAGOSJG = #{idpagosjg,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsPagosjgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@Insert({ "insert into FCS_PAGOSJG (IDINSTITUCION, IDPAGOSJG, ", "IDFACTURACION, NOMBRE, ",
			"ABREVIATURA, FECHADESDE, ", "FECHAHASTA, CRITERIOPAGOTURNO, ", "IMPORTEREPARTIR, IMPORTEPAGADO, ",
			"IMPORTEOFICIO, IMPORTEGUARDIA, ", "IMPORTESOJ, IMPORTEEJG, ", "IMPORTEMINIMO, FECHAMODIFICACION, ",
			"USUMODIFICACION, CONTABILIZADO, ", "BANCOS_CODIGO, IDSUFIJO, ", "IDPROPSEPA, IDPROPOTROS)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idpagosjg,jdbcType=DECIMAL}, ",
			"#{idfacturacion,jdbcType=DECIMAL}, #{nombre,jdbcType=VARCHAR}, ",
			"#{abreviatura,jdbcType=VARCHAR}, #{fechadesde,jdbcType=TIMESTAMP}, ",
			"#{fechahasta,jdbcType=TIMESTAMP}, #{criteriopagoturno,jdbcType=VARCHAR}, ",
			"#{importerepartir,jdbcType=DECIMAL}, #{importepagado,jdbcType=DECIMAL}, ",
			"#{importeoficio,jdbcType=DECIMAL}, #{importeguardia,jdbcType=DECIMAL}, ",
			"#{importesoj,jdbcType=DECIMAL}, #{importeejg,jdbcType=DECIMAL}, ",
			"#{importeminimo,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{contabilizado,jdbcType=VARCHAR}, ",
			"#{bancosCodigo,jdbcType=VARCHAR}, #{idsufijo,jdbcType=DECIMAL}, ",
			"#{idpropsepa,jdbcType=DECIMAL}, #{idpropotros,jdbcType=DECIMAL})" })
	int insert(FcsPagosjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@InsertProvider(type = FcsPagosjgSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsPagosjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@SelectProvider(type = FcsPagosjgSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CRITERIOPAGOTURNO", property = "criteriopagoturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTEREPARTIR", property = "importerepartir", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEPAGADO", property = "importepagado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEOFICIO", property = "importeoficio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEGUARDIA", property = "importeguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTESOJ", property = "importesoj", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEEJG", property = "importeejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEMINIMO", property = "importeminimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CONTABILIZADO", property = "contabilizado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSUFIJO", property = "idsufijo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROPSEPA", property = "idpropsepa", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROPOTROS", property = "idpropotros", jdbcType = JdbcType.DECIMAL) })
	List<FcsPagosjg> selectByExample(FcsPagosjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDPAGOSJG, IDFACTURACION, NOMBRE, ABREVIATURA, FECHADESDE, FECHAHASTA, ",
			"CRITERIOPAGOTURNO, IMPORTEREPARTIR, IMPORTEPAGADO, IMPORTEOFICIO, IMPORTEGUARDIA, ",
			"IMPORTESOJ, IMPORTEEJG, IMPORTEMINIMO, FECHAMODIFICACION, USUMODIFICACION, CONTABILIZADO, ",
			"BANCOS_CODIGO, IDSUFIJO, IDPROPSEPA, IDPROPOTROS", "from FCS_PAGOSJG",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPAGOSJG = #{idpagosjg,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CRITERIOPAGOTURNO", property = "criteriopagoturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTEREPARTIR", property = "importerepartir", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEPAGADO", property = "importepagado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEOFICIO", property = "importeoficio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEGUARDIA", property = "importeguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTESOJ", property = "importesoj", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEEJG", property = "importeejg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IMPORTEMINIMO", property = "importeminimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CONTABILIZADO", property = "contabilizado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSUFIJO", property = "idsufijo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROPSEPA", property = "idpropsepa", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROPOTROS", property = "idpropotros", jdbcType = JdbcType.DECIMAL) })
	FcsPagosjg selectByPrimaryKey(FcsPagosjgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@UpdateProvider(type = FcsPagosjgSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsPagosjg record, @Param("example") FcsPagosjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@UpdateProvider(type = FcsPagosjgSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsPagosjg record, @Param("example") FcsPagosjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@UpdateProvider(type = FcsPagosjgSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsPagosjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_PAGOSJG
	 * @mbg.generated  Thu Dec 26 23:53:13 CET 2019
	 */
	@Update({ "update FCS_PAGOSJG", "set IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL},",
			"NOMBRE = #{nombre,jdbcType=VARCHAR},", "ABREVIATURA = #{abreviatura,jdbcType=VARCHAR},",
			"FECHADESDE = #{fechadesde,jdbcType=TIMESTAMP},", "FECHAHASTA = #{fechahasta,jdbcType=TIMESTAMP},",
			"CRITERIOPAGOTURNO = #{criteriopagoturno,jdbcType=VARCHAR},",
			"IMPORTEREPARTIR = #{importerepartir,jdbcType=DECIMAL},",
			"IMPORTEPAGADO = #{importepagado,jdbcType=DECIMAL},", "IMPORTEOFICIO = #{importeoficio,jdbcType=DECIMAL},",
			"IMPORTEGUARDIA = #{importeguardia,jdbcType=DECIMAL},", "IMPORTESOJ = #{importesoj,jdbcType=DECIMAL},",
			"IMPORTEEJG = #{importeejg,jdbcType=DECIMAL},", "IMPORTEMINIMO = #{importeminimo,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"CONTABILIZADO = #{contabilizado,jdbcType=VARCHAR},", "BANCOS_CODIGO = #{bancosCodigo,jdbcType=VARCHAR},",
			"IDSUFIJO = #{idsufijo,jdbcType=DECIMAL},", "IDPROPSEPA = #{idpropsepa,jdbcType=DECIMAL},",
			"IDPROPOTROS = #{idpropotros,jdbcType=DECIMAL}", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPAGOSJG = #{idpagosjg,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsPagosjg record);
}