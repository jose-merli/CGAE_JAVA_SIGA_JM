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
import org.itcgae.siga.db.entities.AdmUsuarioEfectivo;
import org.itcgae.siga.db.entities.AdmUsuarioEfectivoExample;
import org.itcgae.siga.db.entities.AdmUsuarioEfectivoKey;

public interface AdmUsuarioEfectivoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "countByExample")
	long countByExample(AdmUsuarioEfectivoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AdmUsuarioEfectivoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from ADM_USUARIO_EFECTIVO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDUSUARIO = #{idusuario,jdbcType=DECIMAL}", "and IDROL = #{idrol,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(AdmUsuarioEfectivoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into ADM_USUARIO_EFECTIVO (IDINSTITUCION, IDUSUARIO, ", "IDROL, FECHAMODIFICACION, ",
			"USUMODIFICACION, NUMSERIE)", "values (#{idinstitucion,jdbcType=DECIMAL}, #{idusuario,jdbcType=DECIMAL}, ",
			"#{idrol,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{numserie,jdbcType=VARCHAR})" })
	int insert(AdmUsuarioEfectivo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "insertSelective")
	int insertSelective(AdmUsuarioEfectivo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDUSUARIO", property = "idusuario", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMSERIE", property = "numserie", jdbcType = JdbcType.VARCHAR) })
	List<AdmUsuarioEfectivo> selectByExample(AdmUsuarioEfectivoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDUSUARIO, IDROL, FECHAMODIFICACION, USUMODIFICACION, NUMSERIE",
			"from ADM_USUARIO_EFECTIVO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDUSUARIO = #{idusuario,jdbcType=DECIMAL}", "and IDROL = #{idrol,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDUSUARIO", property = "idusuario", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NUMSERIE", property = "numserie", jdbcType = JdbcType.VARCHAR) })
	AdmUsuarioEfectivo selectByPrimaryKey(AdmUsuarioEfectivoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AdmUsuarioEfectivo record,
			@Param("example") AdmUsuarioEfectivoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AdmUsuarioEfectivo record,
			@Param("example") AdmUsuarioEfectivoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmUsuarioEfectivoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(AdmUsuarioEfectivo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIO_EFECTIVO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update ADM_USUARIO_EFECTIVO", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "NUMSERIE = #{numserie,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDUSUARIO = #{idusuario,jdbcType=DECIMAL}",
			"and IDROL = #{idrol,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(AdmUsuarioEfectivo record);
}