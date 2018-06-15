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
import org.itcgae.siga.db.entities.CenNocolegiadoActividad;
import org.itcgae.siga.db.entities.CenNocolegiadoActividadExample;
import org.itcgae.siga.db.entities.CenNocolegiadoActividadKey;

public interface CenNocolegiadoActividadMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@SelectProvider(type = CenNocolegiadoActividadSqlProvider.class, method = "countByExample")
	long countByExample(CenNocolegiadoActividadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@DeleteProvider(type = CenNocolegiadoActividadSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenNocolegiadoActividadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@Insert({ "insert into CEN_NOCOLEGIADO_ACTIVIDAD (IDINSTITUCION, IDPERSONA, ",
			"IDACTIVIDADPROFESIONAL, FECHAMODIFICACION, ", "USUMODIFICACION, FECHA_BAJA)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{idactividadprofesional,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{fechaBaja,jdbcType=TIMESTAMP})" })
	int insert(CenNocolegiadoActividad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@InsertProvider(type = CenNocolegiadoActividadSqlProvider.class, method = "insertSelective")
	int insertSelective(CenNocolegiadoActividad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@SelectProvider(type = CenNocolegiadoActividadSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDACTIVIDADPROFESIONAL", property = "idactividadprofesional", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP) })
	List<CenNocolegiadoActividad> selectByExample(CenNocolegiadoActividadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@UpdateProvider(type = CenNocolegiadoActividadSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenNocolegiadoActividad record,
			@Param("example") CenNocolegiadoActividadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CEN_NOCOLEGIADO_ACTIVIDAD
	 * @mbg.generated  Thu Jun 14 16:03:32 CEST 2018
	 */
	@UpdateProvider(type = CenNocolegiadoActividadSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenNocolegiadoActividad record,
			@Param("example") CenNocolegiadoActividadExample example);
}