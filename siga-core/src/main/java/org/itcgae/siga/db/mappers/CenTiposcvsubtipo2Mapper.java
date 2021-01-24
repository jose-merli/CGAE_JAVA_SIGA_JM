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
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Key;

public interface CenTiposcvsubtipo2Mapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@SelectProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "countByExample")
	long countByExample(CenTiposcvsubtipo2Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@DeleteProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTiposcvsubtipo2Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@Delete({ "delete from CEN_TIPOSCVSUBTIPO2", "where IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}",
			"and IDTIPOCVSUBTIPO2 = #{idtipocvsubtipo2,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenTiposcvsubtipo2Key key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@Insert({ "insert into CEN_TIPOSCVSUBTIPO2 (IDTIPOCV, IDTIPOCVSUBTIPO2, ", "IDINSTITUCION, CODIGOEXT, ",
			"DESCRIPCION, FECHAMODIFICACION, ", "USUMODIFICACION, FECHA_BAJA)",
			"values (#{idtipocv,jdbcType=DECIMAL}, #{idtipocvsubtipo2,jdbcType=DECIMAL}, ",
			"#{idinstitucion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ",
			"#{descripcion,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechaBaja,jdbcType=TIMESTAMP})" })
	int insert(CenTiposcvsubtipo2 record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@InsertProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "insertSelective")
	int insertSelective(CenTiposcvsubtipo2 record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@SelectProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTIPOCV", property = "idtipocv", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOCVSUBTIPO2", property = "idtipocvsubtipo2", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	List<CenTiposcvsubtipo2> selectByExample(CenTiposcvsubtipo2Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@Select({ "select", "IDTIPOCV, IDTIPOCVSUBTIPO2, IDINSTITUCION, CODIGOEXT, DESCRIPCION, FECHAMODIFICACION, ",
			"USUMODIFICACION, FECHA_BAJA", "from CEN_TIPOSCVSUBTIPO2", "where IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}",
			"and IDTIPOCVSUBTIPO2 = #{idtipocvsubtipo2,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTIPOCV", property = "idtipocv", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOCVSUBTIPO2", property = "idtipocvsubtipo2", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	CenTiposcvsubtipo2 selectByPrimaryKey(CenTiposcvsubtipo2Key key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@UpdateProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTiposcvsubtipo2 record,
			@Param("example") CenTiposcvsubtipo2Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@UpdateProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTiposcvsubtipo2 record,
			@Param("example") CenTiposcvsubtipo2Example example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@UpdateProvider(type = CenTiposcvsubtipo2SqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTiposcvsubtipo2 record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCVSUBTIPO2
	 * @mbg.generated  Tue Oct 23 16:57:53 CEST 2018
	 */
	@Update({ "update CEN_TIPOSCVSUBTIPO2", "set CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
			"where IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}",
			"and IDTIPOCVSUBTIPO2 = #{idtipocvsubtipo2,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTiposcvsubtipo2 record);
}