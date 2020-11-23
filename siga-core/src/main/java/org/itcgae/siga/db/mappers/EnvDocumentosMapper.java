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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvDocumentosExample;
import org.itcgae.siga.db.entities.EnvDocumentosKey;

public interface EnvDocumentosMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@SelectProvider(type = EnvDocumentosSqlProvider.class, method = "countByExample")
	long countByExample(EnvDocumentosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@DeleteProvider(type = EnvDocumentosSqlProvider.class, method = "deleteByExample")
	int deleteByExample(EnvDocumentosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@Delete({ "delete from ENV_DOCUMENTOS", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDENVIO = #{idenvio,jdbcType=DECIMAL}", "and IDDOCUMENTO = #{iddocumento,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(EnvDocumentosKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@Insert({ "insert into ENV_DOCUMENTOS (IDINSTITUCION, IDENVIO, ", "IDDOCUMENTO, DESCRIPCION, ",
			"PATHDOCUMENTO, FECHAMODIFICACION, ", "USUMODIFICACION)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idenvio,jdbcType=DECIMAL}, ",
			"#{iddocumento,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{pathdocumento,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL})" })
	@SelectKey(statement = "SELECT SEQ_ENV_DOCUMENTOS.NEXTVAL FROM DUAL", keyProperty = "iddocumento", before = true, resultType = Short.class)
	int insert(EnvDocumentos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@InsertProvider(type = EnvDocumentosSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_ENV_DOCUMENTOS.NEXTVAL FROM DUAL", keyProperty = "iddocumento", before = true, resultType = Short.class)
	int insertSelective(EnvDocumentos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@SelectProvider(type = EnvDocumentosSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDDOCUMENTO", property = "iddocumento", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PATHDOCUMENTO", property = "pathdocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<EnvDocumentos> selectByExample(EnvDocumentosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDENVIO, IDDOCUMENTO, DESCRIPCION, PATHDOCUMENTO, FECHAMODIFICACION, ",
			"USUMODIFICACION", "from ENV_DOCUMENTOS", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDENVIO = #{idenvio,jdbcType=DECIMAL}", "and IDDOCUMENTO = #{iddocumento,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDENVIO", property = "idenvio", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDDOCUMENTO", property = "iddocumento", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PATHDOCUMENTO", property = "pathdocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	EnvDocumentos selectByPrimaryKey(EnvDocumentosKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@UpdateProvider(type = EnvDocumentosSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") EnvDocumentos record, @Param("example") EnvDocumentosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@UpdateProvider(type = EnvDocumentosSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") EnvDocumentos record, @Param("example") EnvDocumentosExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@UpdateProvider(type = EnvDocumentosSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(EnvDocumentos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.ENV_DOCUMENTOS
	 * @mbg.generated  Mon Nov 26 12:11:01 CET 2018
	 */
	@Update({ "update ENV_DOCUMENTOS", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"PATHDOCUMENTO = #{pathdocumento,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}", "and IDENVIO = #{idenvio,jdbcType=DECIMAL}",
			"and IDDOCUMENTO = #{iddocumento,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(EnvDocumentos record);
}