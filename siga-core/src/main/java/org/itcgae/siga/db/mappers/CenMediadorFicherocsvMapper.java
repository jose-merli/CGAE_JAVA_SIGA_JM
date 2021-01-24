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
import org.itcgae.siga.db.entities.CenMediadorFicherocsv;
import org.itcgae.siga.db.entities.CenMediadorFicherocsvExample;

public interface CenMediadorFicherocsvMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "countByExample")
	long countByExample(CenMediadorFicherocsvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenMediadorFicherocsvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_MEDIADOR_FICHEROCSV",
			"where IDMEDIADORFICHEROCSV = #{idmediadorficherocsv,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long idmediadorficherocsv);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_MEDIADOR_FICHEROCSV (IDMEDIADORFICHEROCSV, IDINSTITUCION, ",
			"FECHAMODIFICACION, USUMODIFICACION)",
			"values (#{idmediadorficherocsv,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})" })
	@SelectKey(statement = "SELECT SEQ_CEN_MEDIAD_FICHEROCSV.NEXTVAL FROM DUAL", keyProperty = "idmediadorficherocsv", before = true, resultType = Long.class)
	int insert(CenMediadorFicherocsv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT SEQ_CEN_MEDIAD_FICHEROCSV.NEXTVAL FROM DUAL", keyProperty = "idmediadorficherocsv", before = true, resultType = Long.class)
	int insertSelective(CenMediadorFicherocsv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "IDMEDIADORFICHEROCSV", property = "idmediadorficherocsv", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	List<CenMediadorFicherocsv> selectByExample(CenMediadorFicherocsvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDMEDIADORFICHEROCSV, IDINSTITUCION, FECHAMODIFICACION, USUMODIFICACION",
			"from CEN_MEDIADOR_FICHEROCSV", "where IDMEDIADORFICHEROCSV = #{idmediadorficherocsv,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "IDMEDIADORFICHEROCSV", property = "idmediadorficherocsv", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL) })
	CenMediadorFicherocsv selectByPrimaryKey(Long idmediadorficherocsv);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenMediadorFicherocsv record,
			@Param("example") CenMediadorFicherocsvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenMediadorFicherocsv record,
			@Param("example") CenMediadorFicherocsvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenMediadorFicherocsvSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenMediadorFicherocsv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_MEDIADOR_FICHEROCSV
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_MEDIADOR_FICHEROCSV", "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
			"where IDMEDIADORFICHEROCSV = #{idmediadorficherocsv,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenMediadorFicherocsv record);
}