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
import org.itcgae.siga.db.entities.CajgProcedimientoremesaresol;
import org.itcgae.siga.db.entities.CajgProcedimientoremesaresolExample;

public interface CajgProcedimientoremesaresolMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @SelectProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="countByExample")
    long countByExample(CajgProcedimientoremesaresolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @DeleteProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="deleteByExample")
    int deleteByExample(CajgProcedimientoremesaresolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @Delete({
        "delete from CAJG_PROCEDIMIENTOREMESARESOL",
        "where IDPROCRESOLUCION = #{idprocresolucion,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short idprocresolucion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @Insert({
        "insert into CAJG_PROCEDIMIENTOREMESARESOL (IDPROCRESOLUCION, IDINSTITUCION, ",
        "CONSULTA, CABECERA, ",
        "DELIMITADOR, IDTIPOREMESA)",
        "values (#{idprocresolucion,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{consulta,jdbcType=VARCHAR}, #{cabecera,jdbcType=DECIMAL}, ",
        "#{delimitador,jdbcType=VARCHAR}, #{idtiporemesa,jdbcType=DECIMAL})"
    })
    int insert(CajgProcedimientoremesaresol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @InsertProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="insertSelective")
    int insertSelective(CajgProcedimientoremesaresol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @SelectProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDPROCRESOLUCION", property="idprocresolucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CONSULTA", property="consulta", jdbcType=JdbcType.VARCHAR),
        @Result(column="CABECERA", property="cabecera", jdbcType=JdbcType.DECIMAL),
        @Result(column="DELIMITADOR", property="delimitador", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOREMESA", property="idtiporemesa", jdbcType=JdbcType.DECIMAL)
    })
    List<CajgProcedimientoremesaresol> selectByExample(CajgProcedimientoremesaresolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @Select({
        "select",
        "IDPROCRESOLUCION, IDINSTITUCION, CONSULTA, CABECERA, DELIMITADOR, IDTIPOREMESA",
        "from CAJG_PROCEDIMIENTOREMESARESOL",
        "where IDPROCRESOLUCION = #{idprocresolucion,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDPROCRESOLUCION", property="idprocresolucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CONSULTA", property="consulta", jdbcType=JdbcType.VARCHAR),
        @Result(column="CABECERA", property="cabecera", jdbcType=JdbcType.DECIMAL),
        @Result(column="DELIMITADOR", property="delimitador", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOREMESA", property="idtiporemesa", jdbcType=JdbcType.DECIMAL)
    })
    CajgProcedimientoremesaresol selectByPrimaryKey(Short idprocresolucion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @UpdateProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CajgProcedimientoremesaresol record, @Param("example") CajgProcedimientoremesaresolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @UpdateProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CajgProcedimientoremesaresol record, @Param("example") CajgProcedimientoremesaresolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @UpdateProvider(type=CajgProcedimientoremesaresolSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CajgProcedimientoremesaresol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_PROCEDIMIENTOREMESARESOL
     *
     * @mbg.generated Thu Oct 07 13:18:08 CEST 2021
     */
    @Update({
        "update CAJG_PROCEDIMIENTOREMESARESOL",
        "set IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL},",
          "CONSULTA = #{consulta,jdbcType=VARCHAR},",
          "CABECERA = #{cabecera,jdbcType=DECIMAL},",
          "DELIMITADOR = #{delimitador,jdbcType=VARCHAR},",
          "IDTIPOREMESA = #{idtiporemesa,jdbcType=DECIMAL}",
        "where IDPROCRESOLUCION = #{idprocresolucion,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(CajgProcedimientoremesaresol record);
}