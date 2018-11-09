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
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvExample;

public interface CenTiposcvMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@SelectProvider(type = CenTiposcvSqlProvider.class, method = "countByExample")
	long countByExample(CenTiposcvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@DeleteProvider(type = CenTiposcvSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenTiposcvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@Delete({ "delete from CEN_TIPOSCV", "where IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Short idtipocv);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@Insert({ "insert into CEN_TIPOSCV (IDTIPOCV, DESCRIPCION, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"CODIGOEXT, BLOQUEADO, ", "FECHA_BAJA)",
			"values (#{idtipocv,jdbcType=DECIMAL}, #{descripcion,jdbcType=VARCHAR}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{codigoext,jdbcType=VARCHAR}, #{bloqueado,jdbcType=CHAR}, ", "#{fechaBaja,jdbcType=TIMESTAMP})" })
	int insert(CenTiposcv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@InsertProvider(type = CenTiposcvSqlProvider.class, method = "insertSelective")
	int insertSelective(CenTiposcv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@SelectProvider(type = CenTiposcvSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDTIPOCV", property = "idtipocv", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	List<CenTiposcv> selectByExample(CenTiposcvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@Select({ "select", "IDTIPOCV, DESCRIPCION, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, BLOQUEADO, ",
			"FECHA_BAJA", "from CEN_TIPOSCV", "where IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDTIPOCV", property = "idtipocv", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.CHAR),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	CenTiposcv selectByPrimaryKey(Short idtipocv);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@UpdateProvider(type = CenTiposcvSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenTiposcv record, @Param("example") CenTiposcvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@UpdateProvider(type = CenTiposcvSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenTiposcv record, @Param("example") CenTiposcvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@UpdateProvider(type = CenTiposcvSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenTiposcv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_TIPOSCV
	 * @mbg.generated  Tue Oct 23 08:43:30 CEST 2018
	 */
	@Update({ "update CEN_TIPOSCV", "set DESCRIPCION = #{descripcion,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"BLOQUEADO = #{bloqueado,jdbcType=CHAR},", "FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}",
			"where IDTIPOCV = #{idtipocv,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenTiposcv record);
}