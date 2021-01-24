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
import org.itcgae.siga.db.entities.CenColacambioletrado;
import org.itcgae.siga.db.entities.CenColacambioletradoExample;
import org.itcgae.siga.db.entities.CenColacambioletradoKey;

public interface CenColacambioletradoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenColacambioletradoSqlProvider.class, method = "countByExample")
	long countByExample(CenColacambioletradoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenColacambioletradoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenColacambioletradoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_COLACAMBIOLETRADO", "where IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDCAMBIO = #{idcambio,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenColacambioletradoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_COLACAMBIOLETRADO (IDPERSONA, IDINSTITUCION, ", "IDCAMBIO, FECHACAMBIO, ",
			"IDTIPOCAMBIO, IDDIRECCION, ", "FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idpersona,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{idcambio,jdbcType=DECIMAL}, #{fechacambio,jdbcType=TIMESTAMP}, ",
			"#{idtipocambio,jdbcType=DECIMAL}, #{iddireccion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CenColacambioletrado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenColacambioletradoSqlProvider.class, method = "insertSelective")
	int insertSelective(CenColacambioletrado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenColacambioletradoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCAMBIO", property = "idcambio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHACAMBIO", property = "fechacambio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOCAMBIO", property = "idtipocambio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDDIRECCION", property = "iddireccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenColacambioletrado> selectByExample(CenColacambioletradoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDPERSONA, IDINSTITUCION, IDCAMBIO, FECHACAMBIO, IDTIPOCAMBIO, IDDIRECCION, ",
			"FECHAMODIFICACION, USUMODIFICACION", "from CEN_COLACAMBIOLETRADO",
			"where IDPERSONA = #{idpersona,jdbcType=DECIMAL}", "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDCAMBIO = #{idcambio,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCAMBIO", property = "idcambio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHACAMBIO", property = "fechacambio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOCAMBIO", property = "idtipocambio", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDDIRECCION", property = "iddireccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenColacambioletrado selectByPrimaryKey(CenColacambioletradoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenColacambioletradoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenColacambioletrado record,
			@Param("example") CenColacambioletradoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenColacambioletradoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenColacambioletrado record,
			@Param("example") CenColacambioletradoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenColacambioletradoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenColacambioletrado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLACAMBIOLETRADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_COLACAMBIOLETRADO", "set FECHACAMBIO = #{fechacambio,jdbcType=TIMESTAMP},",
			"IDTIPOCAMBIO = #{idtipocambio,jdbcType=DECIMAL},", "IDDIRECCION = #{iddireccion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}", "where IDPERSONA = #{idpersona,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDCAMBIO = #{idcambio,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenColacambioletrado record);
}