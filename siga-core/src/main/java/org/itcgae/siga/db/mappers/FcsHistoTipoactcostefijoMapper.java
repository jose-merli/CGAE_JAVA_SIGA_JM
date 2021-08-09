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
import org.itcgae.siga.db.entities.FcsHistoTipoactcostefijo;
import org.itcgae.siga.db.entities.FcsHistoTipoactcostefijoExample;
import org.itcgae.siga.db.entities.FcsHistoTipoactcostefijoKey;

public interface FcsHistoTipoactcostefijoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@SelectProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "countByExample")
	long countByExample(FcsHistoTipoactcostefijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@DeleteProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsHistoTipoactcostefijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Delete({ "delete from FCS_HISTO_TIPOACTCOSTEFIJO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
			"and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
			"and IDCOSTEFIJO = #{idcostefijo,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(FcsHistoTipoactcostefijoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Insert({ "insert into FCS_HISTO_TIPOACTCOSTEFIJO (IDINSTITUCION, IDTIPOASISTENCIA, ",
			"IDTIPOACTUACION, IDCOSTEFIJO, ", "IDFACTURACION, IMPORTE, ", "FECHACREACION, FECHAMODIFICACION, ",
			"USUMODIFICACION)", "values (#{idinstitucion,jdbcType=DECIMAL}, #{idtipoasistencia,jdbcType=DECIMAL}, ",
			"#{idtipoactuacion,jdbcType=DECIMAL}, #{idcostefijo,jdbcType=DECIMAL}, ",
			"#{idfacturacion,jdbcType=DECIMAL}, #{importe,jdbcType=DECIMAL}, ",
			"#{fechacreacion,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL})" })
	int insert(FcsHistoTipoactcostefijo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@InsertProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "insertSelective")
	int insertSelective(FcsHistoTipoactcostefijo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@SelectProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOASISTENCIA", property = "idtipoasistencia", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOACTUACION", property = "idtipoactuacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCOSTEFIJO", property = "idcostefijo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<FcsHistoTipoactcostefijo> selectByExample(FcsHistoTipoactcostefijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Select({ "select", "IDINSTITUCION, IDTIPOASISTENCIA, IDTIPOACTUACION, IDCOSTEFIJO, IDFACTURACION, ",
			"IMPORTE, FECHACREACION, FECHAMODIFICACION, USUMODIFICACION", "from FCS_HISTO_TIPOACTCOSTEFIJO",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
			"and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
			"and IDCOSTEFIJO = #{idcostefijo,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOASISTENCIA", property = "idtipoasistencia", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOACTUACION", property = "idtipoactuacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCOSTEFIJO", property = "idcostefijo", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	FcsHistoTipoactcostefijo selectByPrimaryKey(FcsHistoTipoactcostefijoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@UpdateProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsHistoTipoactcostefijo record,
			@Param("example") FcsHistoTipoactcostefijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@UpdateProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsHistoTipoactcostefijo record,
			@Param("example") FcsHistoTipoactcostefijoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@UpdateProvider(type = FcsHistoTipoactcostefijoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsHistoTipoactcostefijo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	@Update({ "update FCS_HISTO_TIPOACTCOSTEFIJO", "set IMPORTE = #{importe,jdbcType=DECIMAL},",
			"FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}",
			"and IDTIPOACTUACION = #{idtipoactuacion,jdbcType=DECIMAL}",
			"and IDCOSTEFIJO = #{idcostefijo,jdbcType=DECIMAL}",
			"and IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsHistoTipoactcostefijo record);
}