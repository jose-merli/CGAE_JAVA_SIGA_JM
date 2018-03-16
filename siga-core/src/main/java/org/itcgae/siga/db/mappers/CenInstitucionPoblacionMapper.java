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
import org.itcgae.siga.db.entities.CenInstitucionPoblacion;
import org.itcgae.siga.db.entities.CenInstitucionPoblacionExample;
import org.itcgae.siga.db.entities.CenInstitucionPoblacionKey;

public interface CenInstitucionPoblacionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "countByExample")
	long countByExample(CenInstitucionPoblacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenInstitucionPoblacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_INSTITUCION_POBLACION", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(CenInstitucionPoblacionKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_INSTITUCION_POBLACION (IDINSTITUCION, IDPOBLACION, ",
			"USUMODIFICACION, FECHAMODIFICACION)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idpoblacion,jdbcType=VARCHAR}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP})" })
	int insert(CenInstitucionPoblacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "insertSelective")
	int insertSelective(CenInstitucionPoblacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	List<CenInstitucionPoblacion> selectByExample(CenInstitucionPoblacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDPOBLACION, USUMODIFICACION, FECHAMODIFICACION",
			"from CEN_INSTITUCION_POBLACION", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	CenInstitucionPoblacion selectByPrimaryKey(CenInstitucionPoblacionKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenInstitucionPoblacion record,
			@Param("example") CenInstitucionPoblacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenInstitucionPoblacion record,
			@Param("example") CenInstitucionPoblacionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenInstitucionPoblacionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenInstitucionPoblacion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION_POBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_INSTITUCION_POBLACION", "set USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(CenInstitucionPoblacion record);
}