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
import org.itcgae.siga.db.entities.FcsFactEjg;
import org.itcgae.siga.db.entities.FcsFactEjgExample;
import org.itcgae.siga.db.entities.FcsFactEjgKey;

public interface FcsFactEjgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@SelectProvider(type = FcsFactEjgSqlProvider.class, method = "countByExample")
	long countByExample(FcsFactEjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@DeleteProvider(type = FcsFactEjgSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsFactEjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Delete({ "delete from FCS_FACT_EJG", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}", "and IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}",
			"and ANIO = #{anio,jdbcType=DECIMAL}", "and NUMERO = #{numero,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsFactEjgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Insert({ "insert into FCS_FACT_EJG (IDINSTITUCION, IDFACTURACION, ", "IDTIPOEJG, ANIO, ", "NUMERO, IDPERSONA, ",
			"IDTURNO, IDGUARDIA, ", "FECHAAPERTURA, PRECIOAPLICADO, ", "FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idfacturacion,jdbcType=DECIMAL}, ",
			"#{idtipoejg,jdbcType=DECIMAL}, #{anio,jdbcType=DECIMAL}, ",
			"#{numero,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{idturno,jdbcType=DECIMAL}, #{idguardia,jdbcType=DECIMAL}, ",
			"#{fechaapertura,jdbcType=TIMESTAMP}, #{precioaplicado,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(FcsFactEjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@InsertProvider(type = FcsFactEjgSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsFactEjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@SelectProvider(type = FcsFactEjgSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAAPERTURA", property = "fechaapertura", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "PRECIOAPLICADO", property = "precioaplicado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<FcsFactEjg> selectByExample(FcsFactEjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDFACTURACION, IDTIPOEJG, ANIO, NUMERO, IDPERSONA, IDTURNO, IDGUARDIA, ",
			"FECHAAPERTURA, PRECIOAPLICADO, FECHAMODIFICACION, USUMODIFICACION", "from FCS_FACT_EJG",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}", "and IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}",
			"and ANIO = #{anio,jdbcType=DECIMAL}", "and NUMERO = #{numero,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAAPERTURA", property = "fechaapertura", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "PRECIOAPLICADO", property = "precioaplicado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	FcsFactEjg selectByPrimaryKey(FcsFactEjgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsFactEjgSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsFactEjg record, @Param("example") FcsFactEjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsFactEjgSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsFactEjg record, @Param("example") FcsFactEjgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsFactEjgSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsFactEjg record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_EJG
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Update({ "update FCS_FACT_EJG", "set IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
			"IDTURNO = #{idturno,jdbcType=DECIMAL},", "IDGUARDIA = #{idguardia,jdbcType=DECIMAL},",
			"FECHAAPERTURA = #{fechaapertura,jdbcType=TIMESTAMP},",
			"PRECIOAPLICADO = #{precioaplicado,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}", "and IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}",
			"and ANIO = #{anio,jdbcType=DECIMAL}", "and NUMERO = #{numero,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsFactEjg record);
}