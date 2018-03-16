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
import org.itcgae.siga.db.entities.CenAcListaNegraBlanca;
import org.itcgae.siga.db.entities.CenAcListaNegraBlancaExample;

public interface CenAcListaNegraBlancaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "countByExample")
	long countByExample(CenAcListaNegraBlancaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenAcListaNegraBlancaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_AC_LISTA_NEGRA_BLANCA",
			"where IDCENACLISTANEGBLA = #{idcenaclistanegbla,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idcenaclistanegbla);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_AC_LISTA_NEGRA_BLANCA (IDCENACLISTANEGBLA, IDTIPOIDENTIFICADOR, ", "NUMDOC, ACCESO, ",
			"USUMODIFICACION, FECHAMODIFICACION)",
			"values (#{idcenaclistanegbla,jdbcType=DECIMAL}, #{idtipoidentificador,jdbcType=VARCHAR}, ",
			"#{numdoc,jdbcType=VARCHAR}, #{acceso,jdbcType=DECIMAL}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP})" })
	int insert(CenAcListaNegraBlanca record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "insertSelective")
	int insertSelective(CenAcListaNegraBlanca record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDCENACLISTANEGBLA", property = "idcenaclistanegbla", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOIDENTIFICADOR", property = "idtipoidentificador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMDOC", property = "numdoc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACCESO", property = "acceso", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	List<CenAcListaNegraBlanca> selectByExample(CenAcListaNegraBlancaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDCENACLISTANEGBLA, IDTIPOIDENTIFICADOR, NUMDOC, ACCESO, USUMODIFICACION, FECHAMODIFICACION",
			"from CEN_AC_LISTA_NEGRA_BLANCA", "where IDCENACLISTANEGBLA = #{idcenaclistanegbla,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDCENACLISTANEGBLA", property = "idcenaclistanegbla", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOIDENTIFICADOR", property = "idtipoidentificador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMDOC", property = "numdoc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACCESO", property = "acceso", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP) })
	CenAcListaNegraBlanca selectByPrimaryKey(Long idcenaclistanegbla);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenAcListaNegraBlanca record,
			@Param("example") CenAcListaNegraBlancaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenAcListaNegraBlanca record,
			@Param("example") CenAcListaNegraBlancaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenAcListaNegraBlancaSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenAcListaNegraBlanca record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_AC_LISTA_NEGRA_BLANCA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_AC_LISTA_NEGRA_BLANCA", "set IDTIPOIDENTIFICADOR = #{idtipoidentificador,jdbcType=VARCHAR},",
			"NUMDOC = #{numdoc,jdbcType=VARCHAR},", "ACCESO = #{acceso,jdbcType=DECIMAL},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}",
			"where IDCENACLISTANEGBLA = #{idcenaclistanegbla,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenAcListaNegraBlanca record);
}