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
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenColegiadoKey;

public interface CenColegiadoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenColegiadoSqlProvider.class, method = "countByExample")
	long countByExample(CenColegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenColegiadoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenColegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_COLEGIADO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenColegiadoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_COLEGIADO (IDINSTITUCION, IDPERSONA, ", "FECHAPRESENTACION, FECHAINCORPORACION, ",
			"INDTITULACION, JUBILACIONCUOTA, ", "SITUACIONEJERCICIO, SITUACIONRESIDENTE, ",
			"SITUACIONEMPRESA, FECHAMODIFICACION, ", "USUMODIFICACION, COMUNITARIO, ", "NCOLEGIADO, FECHAJURA, ",
			"NCOMUNITARIO, FECHATITULACION, ", "OTROSCOLEGIOS, FECHADEONTOLOGIA, ", "FECHAMOVIMIENTO, IDTIPOSSEGURO, ",
			"CUENTACONTABLESJCS, IDENTIFICADORDS, ", "NMUTUALISTA, NUMSOLICITUDCOLEGIACION, ", "COLUMNA_PRUEBA)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{fechapresentacion,jdbcType=TIMESTAMP}, #{fechaincorporacion,jdbcType=TIMESTAMP}, ",
			"#{indtitulacion,jdbcType=VARCHAR}, #{jubilacioncuota,jdbcType=VARCHAR}, ",
			"#{situacionejercicio,jdbcType=VARCHAR}, #{situacionresidente,jdbcType=VARCHAR}, ",
			"#{situacionempresa,jdbcType=VARCHAR}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{comunitario,jdbcType=VARCHAR}, ",
			"#{ncolegiado,jdbcType=VARCHAR}, #{fechajura,jdbcType=TIMESTAMP}, ",
			"#{ncomunitario,jdbcType=VARCHAR}, #{fechatitulacion,jdbcType=TIMESTAMP}, ",
			"#{otroscolegios,jdbcType=VARCHAR}, #{fechadeontologia,jdbcType=TIMESTAMP}, ",
			"#{fechamovimiento,jdbcType=TIMESTAMP}, #{idtiposseguro,jdbcType=DECIMAL}, ",
			"#{cuentacontablesjcs,jdbcType=VARCHAR}, #{identificadords,jdbcType=VARCHAR}, ",
			"#{nmutualista,jdbcType=VARCHAR}, #{numsolicitudcolegiacion,jdbcType=VARCHAR}, ",
			"#{columnaPrueba,jdbcType=VARCHAR})" })
	int insert(CenColegiado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenColegiadoSqlProvider.class, method = "insertSelective")
	int insertSelective(CenColegiado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenColegiadoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAINCORPORACION", property = "fechaincorporacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "INDTITULACION", property = "indtitulacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "JUBILACIONCUOTA", property = "jubilacioncuota", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONEJERCICIO", property = "situacionejercicio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONRESIDENTE", property = "situacionresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONEMPRESA", property = "situacionempresa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COMUNITARIO", property = "comunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJURA", property = "fechajura", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NCOMUNITARIO", property = "ncomunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHATITULACION", property = "fechatitulacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "OTROSCOLEGIOS", property = "otroscolegios", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADEONTOLOGIA", property = "fechadeontologia", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMOVIMIENTO", property = "fechamovimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOSSEGURO", property = "idtiposseguro", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CUENTACONTABLESJCS", property = "cuentacontablesjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDENTIFICADORDS", property = "identificadords", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NMUTUALISTA", property = "nmutualista", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMSOLICITUDCOLEGIACION", property = "numsolicitudcolegiacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLUMNA_PRUEBA", property = "columnaPrueba", jdbcType = JdbcType.VARCHAR) })
	List<CenColegiado> selectByExample(CenColegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDPERSONA, FECHAPRESENTACION, FECHAINCORPORACION, INDTITULACION, ",
			"JUBILACIONCUOTA, SITUACIONEJERCICIO, SITUACIONRESIDENTE, SITUACIONEMPRESA, FECHAMODIFICACION, ",
			"USUMODIFICACION, COMUNITARIO, NCOLEGIADO, FECHAJURA, NCOMUNITARIO, FECHATITULACION, ",
			"OTROSCOLEGIOS, FECHADEONTOLOGIA, FECHAMOVIMIENTO, IDTIPOSSEGURO, CUENTACONTABLESJCS, ",
			"IDENTIFICADORDS, NMUTUALISTA, NUMSOLICITUDCOLEGIACION, COLUMNA_PRUEBA", "from CEN_COLEGIADO",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAINCORPORACION", property = "fechaincorporacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "INDTITULACION", property = "indtitulacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "JUBILACIONCUOTA", property = "jubilacioncuota", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONEJERCICIO", property = "situacionejercicio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONRESIDENTE", property = "situacionresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONEMPRESA", property = "situacionempresa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COMUNITARIO", property = "comunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJURA", property = "fechajura", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NCOMUNITARIO", property = "ncomunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHATITULACION", property = "fechatitulacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "OTROSCOLEGIOS", property = "otroscolegios", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADEONTOLOGIA", property = "fechadeontologia", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMOVIMIENTO", property = "fechamovimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDTIPOSSEGURO", property = "idtiposseguro", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CUENTACONTABLESJCS", property = "cuentacontablesjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDENTIFICADORDS", property = "identificadords", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NMUTUALISTA", property = "nmutualista", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMSOLICITUDCOLEGIACION", property = "numsolicitudcolegiacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLUMNA_PRUEBA", property = "columnaPrueba", jdbcType = JdbcType.VARCHAR) })
	CenColegiado selectByPrimaryKey(CenColegiadoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenColegiadoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenColegiado record, @Param("example") CenColegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenColegiadoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenColegiado record, @Param("example") CenColegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenColegiadoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenColegiado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_COLEGIADO", "set FECHAPRESENTACION = #{fechapresentacion,jdbcType=TIMESTAMP},",
			"FECHAINCORPORACION = #{fechaincorporacion,jdbcType=TIMESTAMP},",
			"INDTITULACION = #{indtitulacion,jdbcType=VARCHAR},",
			"JUBILACIONCUOTA = #{jubilacioncuota,jdbcType=VARCHAR},",
			"SITUACIONEJERCICIO = #{situacionejercicio,jdbcType=VARCHAR},",
			"SITUACIONRESIDENTE = #{situacionresidente,jdbcType=VARCHAR},",
			"SITUACIONEMPRESA = #{situacionempresa,jdbcType=VARCHAR},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "COMUNITARIO = #{comunitario,jdbcType=VARCHAR},",
			"NCOLEGIADO = #{ncolegiado,jdbcType=VARCHAR},", "FECHAJURA = #{fechajura,jdbcType=TIMESTAMP},",
			"NCOMUNITARIO = #{ncomunitario,jdbcType=VARCHAR},",
			"FECHATITULACION = #{fechatitulacion,jdbcType=TIMESTAMP},",
			"OTROSCOLEGIOS = #{otroscolegios,jdbcType=VARCHAR},",
			"FECHADEONTOLOGIA = #{fechadeontologia,jdbcType=TIMESTAMP},",
			"FECHAMOVIMIENTO = #{fechamovimiento,jdbcType=TIMESTAMP},",
			"IDTIPOSSEGURO = #{idtiposseguro,jdbcType=DECIMAL},",
			"CUENTACONTABLESJCS = #{cuentacontablesjcs,jdbcType=VARCHAR},",
			"IDENTIFICADORDS = #{identificadords,jdbcType=VARCHAR},", "NMUTUALISTA = #{nmutualista,jdbcType=VARCHAR},",
			"NUMSOLICITUDCOLEGIACION = #{numsolicitudcolegiacion,jdbcType=VARCHAR},",
			"COLUMNA_PRUEBA = #{columnaPrueba,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenColegiado record);
}