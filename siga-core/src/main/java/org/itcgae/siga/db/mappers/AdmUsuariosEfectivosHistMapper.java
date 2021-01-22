package org.itcgae.siga.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosHist;
import org.itcgae.siga.db.entities.AdmUsuariosEfectivosHistExample;

public interface AdmUsuariosEfectivosHistMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmUsuariosEfectivosHistSqlProvider.class, method = "countByExample")
	long countByExample(AdmUsuariosEfectivosHistExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = AdmUsuariosEfectivosHistSqlProvider.class, method = "deleteByExample")
	int deleteByExample(AdmUsuariosEfectivosHistExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into ADM_USUARIOS_EFECTIVOS_HIST (IDUSUARIO, IDINSTITUCION, ", "IDROL, IDPERFIL, ",
			"USUCREACION, FECHACREACION, ", "USUMODIFICACION, FECHAMODIFICACION)",
			"values (#{idusuario,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{idrol,jdbcType=VARCHAR}, #{idperfil,jdbcType=VARCHAR}, ",
			"#{usucreacion,jdbcType=DECIMAL}, #{fechacreacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP})" })
	int insert(AdmUsuariosEfectivosHist record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = AdmUsuariosEfectivosHistSqlProvider.class, method = "insertSelective")
	int insertSelective(AdmUsuariosEfectivosHist record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = AdmUsuariosEfectivosHistSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDUSUARIO", property = "idusuario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERFIL", property = "idperfil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUCREACION", property = "usucreacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	List<AdmUsuariosEfectivosHist> selectByExample(AdmUsuariosEfectivosHistExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmUsuariosEfectivosHistSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") AdmUsuariosEfectivosHist record,
			@Param("example") AdmUsuariosEfectivosHistExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_HIST
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = AdmUsuariosEfectivosHistSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") AdmUsuariosEfectivosHist record,
			@Param("example") AdmUsuariosEfectivosHistExample example);
}