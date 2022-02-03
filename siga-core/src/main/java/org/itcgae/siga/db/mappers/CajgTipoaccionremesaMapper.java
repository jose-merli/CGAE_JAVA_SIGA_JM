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
import org.itcgae.siga.db.entities.CajgTipoaccionremesa;
import org.itcgae.siga.db.entities.CajgTipoaccionremesaExample;

public interface CajgTipoaccionremesaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@SelectProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "countByExample")
	long countByExample(CajgTipoaccionremesaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@DeleteProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CajgTipoaccionremesaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@Delete({ "delete from CAJG_TIPOACCIONREMESA",
			"where IDTIPOACCIONREMESA = #{idtipoaccionremesa,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Integer idtipoaccionremesa);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@Insert({ "insert into CAJG_TIPOACCIONREMESA (IDTIPOACCIONREMESA, DESCRIPCION, ",
			"FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idtipoaccionremesa,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CajgTipoaccionremesa record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@InsertProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "insertSelective")
	int insertSelective(CajgTipoaccionremesa record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@SelectProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDTIPOACCIONREMESA", property = "idtipoaccionremesa", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CajgTipoaccionremesa> selectByExample(CajgTipoaccionremesaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@Select({ "select", "IDTIPOACCIONREMESA, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION",
			"from CAJG_TIPOACCIONREMESA", "where IDTIPOACCIONREMESA = #{idtipoaccionremesa,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDTIPOACCIONREMESA", property = "idtipoaccionremesa", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CajgTipoaccionremesa selectByPrimaryKey(Integer idtipoaccionremesa);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@UpdateProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CajgTipoaccionremesa record,
			@Param("example") CajgTipoaccionremesaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@UpdateProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CajgTipoaccionremesa record,
			@Param("example") CajgTipoaccionremesaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@UpdateProvider(type = CajgTipoaccionremesaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CajgTipoaccionremesa record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCIONREMESA
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	@Update({ "update CAJG_TIPOACCIONREMESA", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDTIPOACCIONREMESA = #{idtipoaccionremesa,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CajgTipoaccionremesa record);
}