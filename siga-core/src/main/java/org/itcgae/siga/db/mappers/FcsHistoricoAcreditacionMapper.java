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
import org.itcgae.siga.db.entities.FcsHistoricoAcreditacion;
import org.itcgae.siga.db.entities.FcsHistoricoAcreditacionExample;
import org.itcgae.siga.db.entities.FcsHistoricoAcreditacionKey;

public interface FcsHistoricoAcreditacionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@SelectProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "countByExample")
	long countByExample(FcsHistoricoAcreditacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@DeleteProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsHistoricoAcreditacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Delete({ "delete from FCS_HISTORICO_ACREDITACION", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsHistoricoAcreditacionKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Insert({ "insert into FCS_HISTORICO_ACREDITACION (IDINSTITUCION, IDACREDITACION, ", "IDFACTURACION, DESCRIPCION, ",
			"FECHACREACION, FECHAMODIFICACION, ", "USUMODIFICACION, IDTIPOACREDITACION)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idacreditacion,jdbcType=DECIMAL}, ",
			"#{idfacturacion,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechacreacion,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{idtipoacreditacion,jdbcType=DECIMAL})" })
	int insert(FcsHistoricoAcreditacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@InsertProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsHistoricoAcreditacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@SelectProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDACREDITACION", property = "idacreditacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOACREDITACION", property = "idtipoacreditacion", jdbcType = JdbcType.DECIMAL) })
	List<FcsHistoricoAcreditacion> selectByExample(FcsHistoricoAcreditacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDACREDITACION, IDFACTURACION, DESCRIPCION, FECHACREACION, FECHAMODIFICACION, ",
			"USUMODIFICACION, IDTIPOACREDITACION", "from FCS_HISTORICO_ACREDITACION",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDACREDITACION", property = "idacreditacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOACREDITACION", property = "idtipoacreditacion", jdbcType = JdbcType.DECIMAL) })
	FcsHistoricoAcreditacion selectByPrimaryKey(FcsHistoricoAcreditacionKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsHistoricoAcreditacion record,
			@Param("example") FcsHistoricoAcreditacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsHistoricoAcreditacion record,
			@Param("example") FcsHistoricoAcreditacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@UpdateProvider(type = FcsHistoricoAcreditacionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsHistoricoAcreditacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTORICO_ACREDITACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	@Update({ "update FCS_HISTORICO_ACREDITACION", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"IDTIPOACREDITACION = #{idtipoacreditacion,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDACREDITACION = #{idacreditacion,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsHistoricoAcreditacion record);
}