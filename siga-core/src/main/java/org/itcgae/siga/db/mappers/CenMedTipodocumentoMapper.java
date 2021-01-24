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
import org.itcgae.siga.db.entities.CenMedTipodocumento;
import org.itcgae.siga.db.entities.CenMedTipodocumentoExample;

public interface CenMedTipodocumentoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenMedTipodocumentoSqlProvider.class, method = "countByExample")
	long countByExample(CenMedTipodocumentoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenMedTipodocumentoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenMedTipodocumentoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_MED_TIPODOCUMENTO", "where IDTIPODOCUMENTO = #{idtipodocumento,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String idtipodocumento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_MED_TIPODOCUMENTO (IDTIPODOCUMENTO, DESCRIPCION)",
			"values (#{idtipodocumento,jdbcType=VARCHAR}, #{descripcion,jdbcType=VARCHAR})" })
	int insert(CenMedTipodocumento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenMedTipodocumentoSqlProvider.class, method = "insertSelective")
	int insertSelective(CenMedTipodocumento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenMedTipodocumentoSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDTIPODOCUMENTO", property = "idtipodocumento", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR) })
	List<CenMedTipodocumento> selectByExample(CenMedTipodocumentoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDTIPODOCUMENTO, DESCRIPCION", "from CEN_MED_TIPODOCUMENTO",
			"where IDTIPODOCUMENTO = #{idtipodocumento,jdbcType=VARCHAR}" })
	@Results({
			@Result(column = "IDTIPODOCUMENTO", property = "idtipodocumento", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR) })
	CenMedTipodocumento selectByPrimaryKey(String idtipodocumento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenMedTipodocumentoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenMedTipodocumento record,
			@Param("example") CenMedTipodocumentoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenMedTipodocumentoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenMedTipodocumento record,
			@Param("example") CenMedTipodocumentoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenMedTipodocumentoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenMedTipodocumento record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MED_TIPODOCUMENTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_MED_TIPODOCUMENTO", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR}",
			"where IDTIPODOCUMENTO = #{idtipodocumento,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(CenMedTipodocumento record);
}