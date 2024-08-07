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
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenClienteKey;

public interface CenClienteMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenClienteSqlProvider.class, method = "countByExample")
	long countByExample(CenClienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@DeleteProvider(type = CenClienteSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CenClienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Delete({ "delete from CEN_CLIENTE", "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(CenClienteKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Insert({ "insert into CEN_CLIENTE (IDINSTITUCION, IDPERSONA, ", "FECHAALTA, CARACTER, ",
			"PUBLICIDAD, GUIAJUDICIAL, ", "ABONOSBANCO, CARGOSBANCO, ", "COMISIONES, IDTRATAMIENTO, ",
			"FECHAMODIFICACION, USUMODIFICACION, ", "IDLENGUAJE, FOTOGRAFIA, ", "ASIENTOCONTABLE, LETRADO, ",
			"FECHACARGA, FECHAACTUALIZACION, ", "FECHAEXPORTCENSO, NOENVIARREVISTA, ",
			"NOAPARECERREDABOGACIA, EXPORTARFOTO)",
			"values (#{idinstitucion,jdbcType=DECIMAL}, #{idpersona,jdbcType=DECIMAL}, ",
			"#{fechaalta,jdbcType=TIMESTAMP}, #{caracter,jdbcType=VARCHAR}, ",
			"#{publicidad,jdbcType=VARCHAR}, #{guiajudicial,jdbcType=VARCHAR}, ",
			"#{abonosbanco,jdbcType=VARCHAR}, #{cargosbanco,jdbcType=VARCHAR}, ",
			"#{comisiones,jdbcType=VARCHAR}, #{idtratamiento,jdbcType=DECIMAL}, ",
			"#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
			"#{idlenguaje,jdbcType=VARCHAR}, #{fotografia,jdbcType=VARCHAR}, ",
			"#{asientocontable,jdbcType=VARCHAR}, #{letrado,jdbcType=VARCHAR}, ",
			"#{fechacarga,jdbcType=TIMESTAMP}, #{fechaactualizacion,jdbcType=TIMESTAMP}, ",
			"#{fechaexportcenso,jdbcType=TIMESTAMP}, #{noenviarrevista,jdbcType=VARCHAR}, ",
			"#{noaparecerredabogacia,jdbcType=VARCHAR}, #{exportarfoto,jdbcType=VARCHAR})" })
	int insert(CenCliente record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@InsertProvider(type = CenClienteSqlProvider.class, method = "insertSelective")
	int insertSelective(CenCliente record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@SelectProvider(type = CenClienteSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CARACTER", property = "caracter", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PUBLICIDAD", property = "publicidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUIAJUDICIAL", property = "guiajudicial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOSBANCO", property = "abonosbanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CARGOSBANCO", property = "cargosbanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTRATAMIENTO", property = "idtratamiento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDLENGUAJE", property = "idlenguaje", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FOTOGRAFIA", property = "fotografia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ASIENTOCONTABLE", property = "asientocontable", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACARGA", property = "fechacarga", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAACTUALIZACION", property = "fechaactualizacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAEXPORTCENSO", property = "fechaexportcenso", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NOENVIARREVISTA", property = "noenviarrevista", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOAPARECERREDABOGACIA", property = "noaparecerredabogacia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EXPORTARFOTO", property = "exportarfoto", jdbcType = JdbcType.VARCHAR) })
	List<CenCliente> selectByExample(CenClienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Select({ "select", "IDINSTITUCION, IDPERSONA, FECHAALTA, CARACTER, PUBLICIDAD, GUIAJUDICIAL, ABONOSBANCO, ",
			"CARGOSBANCO, COMISIONES, IDTRATAMIENTO, FECHAMODIFICACION, USUMODIFICACION, ",
			"IDLENGUAJE, FOTOGRAFIA, ASIENTOCONTABLE, LETRADO, FECHACARGA, FECHAACTUALIZACION, ",
			"FECHAEXPORTCENSO, NOENVIARREVISTA, NOAPARECERREDABOGACIA, EXPORTARFOTO", "from CEN_CLIENTE",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "CARACTER", property = "caracter", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PUBLICIDAD", property = "publicidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUIAJUDICIAL", property = "guiajudicial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOSBANCO", property = "abonosbanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CARGOSBANCO", property = "cargosbanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTRATAMIENTO", property = "idtratamiento", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDLENGUAJE", property = "idlenguaje", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FOTOGRAFIA", property = "fotografia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ASIENTOCONTABLE", property = "asientocontable", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACARGA", property = "fechacarga", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAACTUALIZACION", property = "fechaactualizacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAEXPORTCENSO", property = "fechaexportcenso", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NOENVIARREVISTA", property = "noenviarrevista", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOAPARECERREDABOGACIA", property = "noaparecerredabogacia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EXPORTARFOTO", property = "exportarfoto", jdbcType = JdbcType.VARCHAR) })
	CenCliente selectByPrimaryKey(CenClienteKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenClienteSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CenCliente record, @Param("example") CenClienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenClienteSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenCliente record, @Param("example") CenClienteExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@UpdateProvider(type = CenClienteSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CenCliente record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	@Update({ "update CEN_CLIENTE", "set FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP},",
			"CARACTER = #{caracter,jdbcType=VARCHAR},", "PUBLICIDAD = #{publicidad,jdbcType=VARCHAR},",
			"GUIAJUDICIAL = #{guiajudicial,jdbcType=VARCHAR},", "ABONOSBANCO = #{abonosbanco,jdbcType=VARCHAR},",
			"CARGOSBANCO = #{cargosbanco,jdbcType=VARCHAR},", "COMISIONES = #{comisiones,jdbcType=VARCHAR},",
			"IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL},",
			"FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
			"USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},", "IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR},",
			"FOTOGRAFIA = #{fotografia,jdbcType=VARCHAR},", "ASIENTOCONTABLE = #{asientocontable,jdbcType=VARCHAR},",
			"LETRADO = #{letrado,jdbcType=VARCHAR},", "FECHACARGA = #{fechacarga,jdbcType=TIMESTAMP},",
			"FECHAACTUALIZACION = #{fechaactualizacion,jdbcType=TIMESTAMP},",
			"FECHAEXPORTCENSO = #{fechaexportcenso,jdbcType=TIMESTAMP},",
			"NOENVIARREVISTA = #{noenviarrevista,jdbcType=VARCHAR},",
			"NOAPARECERREDABOGACIA = #{noaparecerredabogacia,jdbcType=VARCHAR},",
			"EXPORTARFOTO = #{exportarfoto,jdbcType=VARCHAR}",
			"where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
			"and IDPERSONA = #{idpersona,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(CenCliente record);
}