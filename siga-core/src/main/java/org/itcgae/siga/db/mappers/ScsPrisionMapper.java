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
import org.itcgae.siga.db.entities.ScsPrision;
import org.itcgae.siga.db.entities.ScsPrisionExample;
import org.itcgae.siga.db.entities.ScsPrisionKey;

public interface ScsPrisionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@SelectProvider(type = ScsPrisionSqlProvider.class, method = "countByExample")
	long countByExample(ScsPrisionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@DeleteProvider(type = ScsPrisionSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ScsPrisionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@Delete({ "delete from SCS_PRISION", "where IDPRISION = #{idprision,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(ScsPrisionKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@Insert({ "insert into SCS_PRISION (IDPRISION, IDINSTITUCION, ", "NOMBRE, DOMICILIO, ",
			"CODIGOPOSTAL, IDPOBLACION, ", "IDPROVINCIA, TELEFONO1, ", "TELEFONO2, FAX1, ",
			"FECHABAJA, FECHAMODIFICACION, ", "USUMODIFICACION, CODIGOEXT, ", "EMAIL)",
			"values (#{idprision,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
			"#{nombre,jdbcType=VARCHAR}, #{domicilio,jdbcType=VARCHAR}, ",
			"#{codigopostal,jdbcType=VARCHAR}, #{idpoblacion,jdbcType=VARCHAR}, ",
			"#{idprovincia,jdbcType=VARCHAR}, #{telefono1,jdbcType=VARCHAR}, ",
			"#{telefono2,jdbcType=VARCHAR}, #{fax1,jdbcType=VARCHAR}, ",
			"#{fechabaja,jdbcType=TIMESTAMP}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
			"#{usumodificacion,jdbcType=DECIMAL}, #{codigoext,jdbcType=VARCHAR}, ", "#{email,jdbcType=VARCHAR})" })
	int insert(ScsPrision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@InsertProvider(type = ScsPrisionSqlProvider.class, method = "insertSelective")
	int insertSelective(ScsPrision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@SelectProvider(type = ScsPrisionSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDPRISION", property = "idprision", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR) })
	List<ScsPrision> selectByExample(ScsPrisionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@Select({ "select", "IDPRISION, IDINSTITUCION, NOMBRE, DOMICILIO, CODIGOPOSTAL, IDPOBLACION, IDPROVINCIA, ",
			"TELEFONO1, TELEFONO2, FAX1, FECHABAJA, FECHAMODIFICACION, USUMODIFICACION, CODIGOEXT, ", "EMAIL",
			"from SCS_PRISION", "where IDPRISION = #{idprision,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDPRISION", property = "idprision", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigopostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idpoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idprovincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR) })
	ScsPrision selectByPrimaryKey(ScsPrisionKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@UpdateProvider(type = ScsPrisionSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ScsPrision record, @Param("example") ScsPrisionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@UpdateProvider(type = ScsPrisionSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ScsPrision record, @Param("example") ScsPrisionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@UpdateProvider(type = ScsPrisionSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ScsPrision record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PRISION
	 * @mbg.generated  Mon Sep 23 15:26:12 CEST 2019
	 */
	@Update({ "update SCS_PRISION", "set NOMBRE = #{nombre,jdbcType=VARCHAR},",
			"DOMICILIO = #{domicilio,jdbcType=VARCHAR},", "CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR},",
			"IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR},", "IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR},",
			"TELEFONO1 = #{telefono1,jdbcType=VARCHAR},", "TELEFONO2 = #{telefono2,jdbcType=VARCHAR},",
			"FAX1 = #{fax1,jdbcType=VARCHAR},", "FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "CODIGOEXT = #{codigoext,jdbcType=VARCHAR},",
			"EMAIL = #{email,jdbcType=VARCHAR}", "where IDPRISION = #{idprision,jdbcType=DECIMAL}",
			"and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(ScsPrision record);
}