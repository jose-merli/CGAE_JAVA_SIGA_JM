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
import org.itcgae.siga.db.entities.FcsJeCertanexoEstado;
import org.itcgae.siga.db.entities.FcsJeCertanexoEstadoExample;
import org.apache.ibatis.annotations.SelectKey;

public interface FcsJeCertanexoEstadoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@SelectProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "countByExample")
	long countByExample(FcsJeCertanexoEstadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@DeleteProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(FcsJeCertanexoEstadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@Delete({ "delete from FCS_JE_CERTANEXO_ESTADO",
			"where IDCERTIFICACIONANEXOESTADO = #{idcertificacionanexoestado,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idcertificacionanexoestado);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@Insert({ "insert into FCS_JE_CERTANEXO_ESTADO (IDCERTIFICACIONANEXOESTADO, ", "IDCERTIFICACIONANEXO, IDESTADO, ",
			"IDJEINTERCAMBIO)", "values (#{idcertificacionanexoestado,jdbcType=DECIMAL}, ",
			"#{idcertificacionanexo,jdbcType=DECIMAL}, #{idestado,jdbcType=DECIMAL}, ",
			"#{idjeintercambio,jdbcType=DECIMAL})" })
	@SelectKey(statement = "SELECT SEQ_FCS_JE_CERTANEXO_ESTADO.NEXTVAL FROM DUAL", keyProperty = "idcertificacionanexoestado", before = true, resultType = Long.class)
	int insert(FcsJeCertanexoEstado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@InsertProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_FCS_JE_CERTANEXO_ESTADO.NEXTVAL FROM DUAL", keyProperty = "idcertificacionanexoestado", before = true, resultType = Long.class)
	int insertSelective(FcsJeCertanexoEstado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@SelectProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDCERTIFICACIONANEXOESTADO", property = "idcertificacionanexoestado", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCERTIFICACIONANEXO", property = "idcertificacionanexo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJEINTERCAMBIO", property = "idjeintercambio", jdbcType = JdbcType.DECIMAL) })
	List<FcsJeCertanexoEstado> selectByExample(FcsJeCertanexoEstadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@Select({ "select", "IDCERTIFICACIONANEXOESTADO, IDCERTIFICACIONANEXO, IDESTADO, IDJEINTERCAMBIO",
			"from FCS_JE_CERTANEXO_ESTADO",
			"where IDCERTIFICACIONANEXOESTADO = #{idcertificacionanexoestado,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDCERTIFICACIONANEXOESTADO", property = "idcertificacionanexoestado", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDCERTIFICACIONANEXO", property = "idcertificacionanexo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADO", property = "idestado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJEINTERCAMBIO", property = "idjeintercambio", jdbcType = JdbcType.DECIMAL) })
	FcsJeCertanexoEstado selectByPrimaryKey(Long idcertificacionanexoestado);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@UpdateProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") FcsJeCertanexoEstado record,
			@Param("example") FcsJeCertanexoEstadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@UpdateProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") FcsJeCertanexoEstado record,
			@Param("example") FcsJeCertanexoEstadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@UpdateProvider(type = FcsJeCertanexoEstadoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(FcsJeCertanexoEstado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_JE_CERTANEXO_ESTADO
	 * @mbg.generated  Mon Dec 20 09:00:12 CET 2021
	 */
	@Update({ "update FCS_JE_CERTANEXO_ESTADO", "set IDCERTIFICACIONANEXO = #{idcertificacionanexo,jdbcType=DECIMAL},",
			"IDESTADO = #{idestado,jdbcType=DECIMAL},", "IDJEINTERCAMBIO = #{idjeintercambio,jdbcType=DECIMAL}",
			"where IDCERTIFICACIONANEXOESTADO = #{idcertificacionanexoestado,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(FcsJeCertanexoEstado record);
}