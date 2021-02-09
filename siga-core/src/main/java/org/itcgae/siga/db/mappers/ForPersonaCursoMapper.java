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
import org.itcgae.siga.db.entities.ForPersonaCurso;
import org.itcgae.siga.db.entities.ForPersonaCursoExample;

public interface ForPersonaCursoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@SelectProvider(type = ForPersonaCursoSqlProvider.class, method = "countByExample")
	long countByExample(ForPersonaCursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@DeleteProvider(type = ForPersonaCursoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ForPersonaCursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@Delete({ "delete from FOR_PERSONA_CURSO", "where IDFORMADOR = #{idformador,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idformador);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@Insert({ "insert into FOR_PERSONA_CURSO (IDFORMADOR, IDPERSONA, ", "IDCURSO, IDROL, ",
			"USUMODIFICACION, FECHAMODIFICACION, ", "IDINSTITUCION, IDTIPOCOSTE, ", "TARIFA, TUTOR, FECHABAJA)",
			"values (#{idformador,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{idcurso,jdbcType=DECIMAL}, #{idrol,jdbcType=DECIMAL}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{idinstitucion,jdbcType=DECIMAL}, #{idtipocoste,jdbcType=DECIMAL}, ",
			"#{tarifa,jdbcType=DECIMAL}, #{tutor,jdbcType=DECIMAL}, #{fechabaja,jdbcType=TIMESTAMP})" })
	@SelectKey(statement = "SELECT SEQ_FORPERSONACURSO.NEXTVAL FROM DUAL", keyProperty = "idformador", before = true, resultType = Long.class)
	int insert(ForPersonaCurso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@InsertProvider(type = ForPersonaCursoSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_FORPERSONACURSO.NEXTVAL FROM DUAL", keyProperty = "idformador", before = true, resultType = Long.class)
	int insertSelective(ForPersonaCurso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@SelectProvider(type = ForPersonaCursoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDFORMADOR", property = "idformador", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCURSO", property = "idcurso", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOCOSTE", property = "idtipocoste", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TARIFA", property = "tarifa", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TUTOR", property = "tutor", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<ForPersonaCurso> selectByExample(ForPersonaCursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@Select({ "select", "IDFORMADOR, IDPERSONA, IDCURSO, IDROL, USUMODIFICACION, FECHAMODIFICACION, IDINSTITUCION, ",
			"IDTIPOCOSTE, TARIFA, TUTOR, FECHABAJA", "from FOR_PERSONA_CURSO",
			"where IDFORMADOR = #{idformador,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDFORMADOR", property = "idformador", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDCURSO", property = "idcurso", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDROL", property = "idrol", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOCOSTE", property = "idtipocoste", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TARIFA", property = "tarifa", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TUTOR", property = "tutor", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	ForPersonaCurso selectByPrimaryKey(Long idformador);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@UpdateProvider(type = ForPersonaCursoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ForPersonaCurso record,
			@Param("example") ForPersonaCursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@UpdateProvider(type = ForPersonaCursoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ForPersonaCurso record, @Param("example") ForPersonaCursoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@UpdateProvider(type = ForPersonaCursoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ForPersonaCurso record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Mon Dec 17 13:32:27 CET 2018
	 */
	@Update({ "update FOR_PERSONA_CURSO", "set IDPERSONA = #{idpersona,jdbcType=DECIMAL},",
			"IDCURSO = #{idcurso,jdbcType=DECIMAL},", "IDROL = #{idrol,jdbcType=DECIMAL},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},", "IDTIPOCOSTE = #{idtipocoste,jdbcType=DECIMAL},",
			"TARIFA = #{tarifa,jdbcType=DECIMAL},", "TUTOR = #{tutor,jdbcType=DECIMAL},",
			"FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}", "where IDFORMADOR = #{idformador,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ForPersonaCurso record);
}