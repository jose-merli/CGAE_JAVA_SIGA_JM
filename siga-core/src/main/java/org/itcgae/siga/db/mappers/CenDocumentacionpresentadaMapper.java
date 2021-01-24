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
import org.itcgae.siga.db.entities.CenDocumentacionpresentada;
import org.itcgae.siga.db.entities.CenDocumentacionpresentadaExample;
import org.itcgae.siga.db.entities.CenDocumentacionpresentadaKey;

public interface CenDocumentacionpresentadaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "countByExample")
	long countByExample(CenDocumentacionpresentadaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenDocumentacionpresentadaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_DOCUMENTACIONPRESENTADA", "where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}",
			"and IDDOCUMENTACION = #{iddocumentacion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenDocumentacionpresentadaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_DOCUMENTACIONPRESENTADA (IDSOLICITUD, IDDOCUMENTACION, ",
			"FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idsolicitud,jdbcType=DECIMAL}, #{iddocumentacion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	int insert(CenDocumentacionpresentada record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "insertSelective")
	int insertSelective(CenDocumentacionpresentada record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDDOCUMENTACION", property = "iddocumentacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenDocumentacionpresentada> selectByExample(CenDocumentacionpresentadaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDSOLICITUD, IDDOCUMENTACION, FECHAMODIFICACION, USUMODIFICACION",
			"from CEN_DOCUMENTACIONPRESENTADA", "where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}",
			"and IDDOCUMENTACION = #{iddocumentacion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDDOCUMENTACION", property = "iddocumentacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenDocumentacionpresentada selectByPrimaryKey(CenDocumentacionpresentadaKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenDocumentacionpresentada record,
			@Param("example") CenDocumentacionpresentadaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenDocumentacionpresentada record,
			@Param("example") CenDocumentacionpresentadaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenDocumentacionpresentadaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenDocumentacionpresentada record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_DOCUMENTACIONPRESENTADA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_DOCUMENTACIONPRESENTADA", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}",
			"and IDDOCUMENTACION = #{iddocumentacion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenDocumentacionpresentada record);
}