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
import org.itcgae.siga.db.entities.FcsHistoricoHitofact;
import org.itcgae.siga.db.entities.FcsHistoricoHitofactExample;
import org.itcgae.siga.db.entities.FcsHistoricoHitofactKey;

public interface FcsHistoricoHitofactMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@SelectProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "countByExample")
	long countByExample(FcsHistoricoHitofactExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@DeleteProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsHistoricoHitofactExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Delete({ "delete from FCS_HISTORICO_HITOFACT", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}", "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
			"and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}", "and IDHITO = #{idhito,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsHistoricoHitofactKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Insert({ "insert into FCS_HISTORICO_HITOFACT (IDINSTITUCION, IDFACTURACION, ", "IDTURNO, IDGUARDIA, ",
			"IDHITO, PRECIOHITO, ", "FECHACREACION, FECHAMODIFICACION, ", "USUMODIFICACION, DIASAPLICABLES, ",
			"AGRUPAR)", "values (#{idinstitucion,jdbcType=DECIMAL}, #{idfacturacion,jdbcType=DECIMAL}, ",
			"#{idturno,jdbcType=DECIMAL}, #{idguardia,jdbcType=DECIMAL}, ",
			"#{idhito,jdbcType=DECIMAL}, #{preciohito,jdbcType=DECIMAL}, ",
			"#{fechacreacion,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{diasaplicables,jdbcType=VARCHAR}, ",
			"#{agrupar,jdbcType=VARCHAR})" })
	int insert(FcsHistoricoHitofact record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@InsertProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsHistoricoHitofact record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@SelectProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDHITO", property = "idhito", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "PRECIOHITO", property = "preciohito", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DIASAPLICABLES", property = "diasaplicables", jdbcType = JdbcType.VARCHAR),
			@Result(column = "AGRUPAR", property = "agrupar", jdbcType = JdbcType.VARCHAR) })
	List<FcsHistoricoHitofact> selectByExample(FcsHistoricoHitofactExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDFACTURACION, IDTURNO, IDGUARDIA, IDHITO, PRECIOHITO, FECHACREACION, ",
			"FECHAMODIFICACION, USUMODIFICACION, DIASAPLICABLES, AGRUPAR", "from FCS_HISTORICO_HITOFACT",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}", "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
			"and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}", "and IDHITO = #{idhito,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDHITO", property = "idhito", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "PRECIOHITO", property = "preciohito", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DIASAPLICABLES", property = "diasaplicables", jdbcType = JdbcType.VARCHAR),
			@Result(column = "AGRUPAR", property = "agrupar", jdbcType = JdbcType.VARCHAR) })
	FcsHistoricoHitofact selectByPrimaryKey(FcsHistoricoHitofactKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@UpdateProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsHistoricoHitofact record,
			@Param("example") FcsHistoricoHitofactExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@UpdateProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsHistoricoHitofact record,
			@Param("example") FcsHistoricoHitofactExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@UpdateProvider(type = FcsHistoricoHitofactSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsHistoricoHitofact record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_HITOFACT
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Update({ "update FCS_HISTORICO_HITOFACT", "set PRECIOHITO = #{preciohito,jdbcType=DECIMAL},",
			"FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"DIASAPLICABLES = #{diasaplicables,jdbcType=VARCHAR},", "AGRUPAR = #{agrupar,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}", "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
			"and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}", "and IDHITO = #{idhito,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsHistoricoHitofact record);
}