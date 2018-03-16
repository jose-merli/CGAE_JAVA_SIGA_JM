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
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiadoKey;

public interface CenNocolegiadoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenNocolegiadoSqlProvider.class, method = "countByExample")
	long countByExample(CenNocolegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenNocolegiadoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenNocolegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_NOCOLEGIADO", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenNocolegiadoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_NOCOLEGIADO (IDINSTITUCION, IDPERSONA, ", "FECHAMODIFICACION, USUMODIFICACION, ",
			"SERIE, NUMEROREF, ", "SOCIEDADSJ, TIPO, ", "ANOTACIONES, PREFIJO_NUMREG, ",
			"CONTADOR_NUMREG, SUFIJO_NUMREG, ", "FECHAFIN, IDPERSONANOTARIO, ", "RESENA, OBJETOSOCIAL, ",
			"SOCIEDADPROFESIONAL, PREFIJO_NUMSSPP, ", "CONTADOR_NUMSSPP, SUFIJO_NUMSSPP, ", "NOPOLIZA, COMPANIASEG, ",
			"IDENTIFICADORDS)", "values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{serie,jdbcType=VARCHAR}, #{numeroref,jdbcType=VARCHAR}, ",
			"#{sociedadsj,jdbcType=VARCHAR}, #{tipo,jdbcType=VARCHAR}, ",
			"#{anotaciones,jdbcType=VARCHAR}, #{prefijoNumreg,jdbcType=VARCHAR}, ",
			"#{contadorNumreg,jdbcType=DECIMAL}, #{sufijoNumreg,jdbcType=VARCHAR}, ",
			"#{fechafin,jdbcType=TIMESTAMP}, #{idpersonanotario,jdbcType=DECIMAL}, ",
			"#{resena,jdbcType=VARCHAR}, #{objetosocial,jdbcType=VARCHAR}, ",
			"#{sociedadprofesional,jdbcType=VARCHAR}, #{prefijoNumsspp,jdbcType=VARCHAR}, ",
			"#{contadorNumsspp,jdbcType=DECIMAL}, #{sufijoNumsspp,jdbcType=VARCHAR}, ",
			"#{nopoliza,jdbcType=VARCHAR}, #{companiaseg,jdbcType=VARCHAR}, ", "#{identificadords,jdbcType=VARCHAR})" })
	int insert(CenNocolegiado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenNocolegiadoSqlProvider.class, method = "insertSelective")
	int insertSelective(CenNocolegiado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenNocolegiadoSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SERIE", property = "serie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROREF", property = "numeroref", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOCIEDADSJ", property = "sociedadsj", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANOTACIONES", property = "anotaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PREFIJO_NUMREG", property = "prefijoNumreg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTADOR_NUMREG", property = "contadorNumreg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SUFIJO_NUMREG", property = "sufijoNumreg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPERSONANOTARIO", property = "idpersonanotario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "RESENA", property = "resena", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBJETOSOCIAL", property = "objetosocial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadprofesional", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PREFIJO_NUMSSPP", property = "prefijoNumsspp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTADOR_NUMSSPP", property = "contadorNumsspp", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SUFIJO_NUMSSPP", property = "sufijoNumsspp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOPOLIZA", property = "nopoliza", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMPANIASEG", property = "companiaseg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDENTIFICADORDS", property = "identificadords", jdbcType = JdbcType.VARCHAR) })
	List<CenNocolegiado> selectByExample(CenNocolegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDPERSONA, FECHAMODIFICACION, USUMODIFICACION, SERIE, NUMEROREF, ",
			"SOCIEDADSJ, TIPO, ANOTACIONES, PREFIJO_NUMREG, CONTADOR_NUMREG, SUFIJO_NUMREG, ",
			"FECHAFIN, IDPERSONANOTARIO, RESENA, OBJETOSOCIAL, SOCIEDADPROFESIONAL, PREFIJO_NUMSSPP, ",
			"CONTADOR_NUMSSPP, SUFIJO_NUMSSPP, NOPOLIZA, COMPANIASEG, IDENTIFICADORDS", "from CEN_NOCOLEGIADO",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SERIE", property = "serie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROREF", property = "numeroref", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOCIEDADSJ", property = "sociedadsj", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANOTACIONES", property = "anotaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PREFIJO_NUMREG", property = "prefijoNumreg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTADOR_NUMREG", property = "contadorNumreg", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SUFIJO_NUMREG", property = "sufijoNumreg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IDPERSONANOTARIO", property = "idpersonanotario", jdbcType = JdbcType.DECIMAL),
			@Result(column = "RESENA", property = "resena", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBJETOSOCIAL", property = "objetosocial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadprofesional", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PREFIJO_NUMSSPP", property = "prefijoNumsspp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTADOR_NUMSSPP", property = "contadorNumsspp", jdbcType = JdbcType.DECIMAL),
			@Result(column = "SUFIJO_NUMSSPP", property = "sufijoNumsspp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOPOLIZA", property = "nopoliza", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMPANIASEG", property = "companiaseg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDENTIFICADORDS", property = "identificadords", jdbcType = JdbcType.VARCHAR) })
	CenNocolegiado selectByPrimaryKey(CenNocolegiadoKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenNocolegiadoSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenNocolegiado record,
			@Param("example") CenNocolegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenNocolegiadoSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenNocolegiado record, @Param("example") CenNocolegiadoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenNocolegiadoSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenNocolegiado record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_NOCOLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_NOCOLEGIADO", "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "SERIE = #{serie,jdbcType=VARCHAR},",
			"NUMEROREF = #{numeroref,jdbcType=VARCHAR},", "SOCIEDADSJ = #{sociedadsj,jdbcType=VARCHAR},",
			"TIPO = #{tipo,jdbcType=VARCHAR},", "ANOTACIONES = #{anotaciones,jdbcType=VARCHAR},",
			"PREFIJO_NUMREG = #{prefijoNumreg,jdbcType=VARCHAR},",
			"CONTADOR_NUMREG = #{contadorNumreg,jdbcType=DECIMAL},",
			"SUFIJO_NUMREG = #{sufijoNumreg,jdbcType=VARCHAR},", "FECHAFIN = #{fechafin,jdbcType=TIMESTAMP},",
			"IDPERSONANOTARIO = #{idpersonanotario,jdbcType=DECIMAL},", "RESENA = #{resena,jdbcType=VARCHAR},",
			"OBJETOSOCIAL = #{objetosocial,jdbcType=VARCHAR},",
			"SOCIEDADPROFESIONAL = #{sociedadprofesional,jdbcType=VARCHAR},",
			"PREFIJO_NUMSSPP = #{prefijoNumsspp,jdbcType=VARCHAR},",
			"CONTADOR_NUMSSPP = #{contadorNumsspp,jdbcType=DECIMAL},",
			"SUFIJO_NUMSSPP = #{sufijoNumsspp,jdbcType=VARCHAR},", "NOPOLIZA = #{nopoliza,jdbcType=VARCHAR},",
			"COMPANIASEG = #{companiaseg,jdbcType=VARCHAR},", "IDENTIFICADORDS = #{identificadords,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenNocolegiado record);
}