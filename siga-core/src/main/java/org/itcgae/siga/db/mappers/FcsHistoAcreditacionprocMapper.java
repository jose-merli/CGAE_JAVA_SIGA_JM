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
import org.itcgae.siga.db.entities.FcsHistoAcreditacionproc;
import org.itcgae.siga.db.entities.FcsHistoAcreditacionprocExample;
import org.itcgae.siga.db.entities.FcsHistoAcreditacionprocKey;

public interface FcsHistoAcreditacionprocMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@SelectProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "countByExample")
	long countByExample(FcsHistoAcreditacionprocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@DeleteProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsHistoAcreditacionprocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Delete({ "delete from FCS_HISTO_ACREDITACIONPROC", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}",
			"and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsHistoAcreditacionprocKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Insert({ "insert into FCS_HISTO_ACREDITACIONPROC (IDINSTITUCION, IDPROCEDIMIENTO, ",
			"IDACREDITACION, IDFACTURACION, ", "PORCENTAJE, USUMODIFICACION, ", "FECHACREACION, FECHAMODIFICACION, ",
			"CODIGOEXT)", "values (#{idinstitucion,jdbcType=DECIMAL}, #{idprocedimiento,jdbcType=VARCHAR}, ",
			"#{idacreditacion,jdbcType=DECIMAL}, #{idfacturacion,jdbcType=DECIMAL}, ",
			"#{porcentaje,jdbcType=DECIMAL}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{fechacreacion,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{codigoext,jdbcType=VARCHAR})" })
	int insert(FcsHistoAcreditacionproc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@InsertProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsHistoAcreditacionproc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@SelectProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPROCEDIMIENTO", property = "idprocedimiento", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDACREDITACION", property = "idacreditacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "PORCENTAJE", property = "porcentaje", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR) })
	List<FcsHistoAcreditacionproc> selectByExample(FcsHistoAcreditacionprocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDPROCEDIMIENTO, IDACREDITACION, IDFACTURACION, PORCENTAJE, USUMODIFICACION, ",
			"FECHACREACION, FECHAMODIFICACION, CODIGOEXT", "from FCS_HISTO_ACREDITACIONPROC",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}",
			"and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPROCEDIMIENTO", property = "idprocedimiento", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDACREDITACION", property = "idacreditacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "PORCENTAJE", property = "porcentaje", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR) })
	FcsHistoAcreditacionproc selectByPrimaryKey(FcsHistoAcreditacionprocKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsHistoAcreditacionproc record,
			@Param("example") FcsHistoAcreditacionprocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsHistoAcreditacionproc record,
			@Param("example") FcsHistoAcreditacionprocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsHistoAcreditacionprocSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsHistoAcreditacionproc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_ACREDITACIONPROC
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Update({ "update FCS_HISTO_ACREDITACIONPROC", "set PORCENTAJE = #{porcentaje,jdbcType=DECIMAL},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}",
			"and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsHistoAcreditacionproc record);
}