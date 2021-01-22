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
import org.itcgae.siga.db.entities.ForTemacurso;
import org.itcgae.siga.db.entities.ForTemacursoExample;

public interface ForTemacursoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@SelectProvider(type = ForTemacursoSqlProvider.class, method = "countByExample")
	long countByExample(ForTemacursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@DeleteProvider(type = ForTemacursoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ForTemacursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@Delete({ "delete from FOR_TEMACURSO", "where IDTEMACURSO = #{idtemacurso,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idtemacurso);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@Insert({ "insert into FOR_TEMACURSO (IDTEMACURSO, USUMODIFICACION, ", "DESCRIPCION, FECHAMODIFICACION, ",
			"FECHABAJA, IDINSTITUCION)",
			"values (#{idtemacurso,jdbcType=DECIMAL}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{fechabaja,jdbcType=TIMESTAMP}, #{idinstitucion,jdbcType=DECIMAL})" })
	int insert(ForTemacurso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@InsertProvider(type = ForTemacursoSqlProvider.class, method = "insertSelective")
	int insertSelective(ForTemacurso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@SelectProvider(type = ForTemacursoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTEMACURSO", property = "idtemacurso", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL) })
	List<ForTemacurso> selectByExample(ForTemacursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@Select({ "select", "IDTEMACURSO, USUMODIFICACION, DESCRIPCION, FECHAMODIFICACION, FECHABAJA, IDINSTITUCION",
			"from FOR_TEMACURSO", "where IDTEMACURSO = #{idtemacurso,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTEMACURSO", property = "idtemacurso", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL) })
	ForTemacurso selectByPrimaryKey(Long idtemacurso);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@UpdateProvider(type = ForTemacursoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ForTemacurso record, @Param("example") ForTemacursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@UpdateProvider(type = ForTemacursoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ForTemacurso record, @Param("example") ForTemacursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@UpdateProvider(type = ForTemacursoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ForTemacurso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_TEMACURSO
	 * @mbg.generated  Thu Jan 03 17:59:06 CET 2019
	 */
	@Update({ "update FOR_TEMACURSO", "set USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},", "IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"where IDTEMACURSO = #{idtemacurso,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ForTemacurso record);
}