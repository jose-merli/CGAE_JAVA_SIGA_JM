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
import org.itcgae.siga.db.entities.AdmPerfilRol;
import org.itcgae.siga.db.entities.AdmPerfilRolExample;
import org.itcgae.siga.db.entities.AdmPerfilRolKey;

public interface AdmPerfilRolMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmPerfilRolSqlProvider.class, method = "countByExample")
	long countByExample(AdmPerfilRolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = AdmPerfilRolSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AdmPerfilRolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from ADM_PERFIL_ROL", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDROL = #{idrol,jdbcType=VARCHAR}", "and IDPERFIL = #{idperfil,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(AdmPerfilRolKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into ADM_PERFIL_ROL (IDINSTITUCION, IDROL, ", "IDPERFIL, USUMODIFICACION, ",
			"FECHAMODIFICACION, GRUPOPORDEFECTO)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idrol,jdbcType=VARCHAR}, ",
			"#{idperfil,jdbcType=VARCHAR}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{grupopordefecto,jdbcType=VARCHAR})" })
	int insert(AdmPerfilRol record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = AdmPerfilRolSqlProvider.class, method = "insertSelective")
	int insertSelective(AdmPerfilRol record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmPerfilRolSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "GRUPOPORDEFECTO", property = "grupopordefecto", jdbcType = JdbcType.VARCHAR) })
	List<AdmPerfilRol> selectByExample(AdmPerfilRolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDROL, IDPERFIL, USUMODIFICACION, FECHAMODIFICACION, GRUPOPORDEFECTO",
			"from ADM_PERFIL_ROL", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDROL = #{idrol,jdbcType=VARCHAR}", "and IDPERFIL = #{idperfil,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "GRUPOPORDEFECTO", property = "grupopordefecto", jdbcType = JdbcType.VARCHAR) })
	AdmPerfilRol selectByPrimaryKey(AdmPerfilRolKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmPerfilRolSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AdmPerfilRol record, @Param("example") AdmPerfilRolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmPerfilRolSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AdmPerfilRol record, @Param("example") AdmPerfilRolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmPerfilRolSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(AdmPerfilRol record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_PERFIL_ROL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update ADM_PERFIL_ROL", "set USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"GRUPOPORDEFECTO = #{grupopordefecto,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDROL = #{idrol,jdbcType=VARCHAR}",
			"and IDPERFIL = #{idperfil,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(AdmPerfilRol record);
}