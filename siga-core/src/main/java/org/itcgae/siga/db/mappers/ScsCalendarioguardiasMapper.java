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
import org.itcgae.siga.db.entities.ScsCalendarioguardias;
import org.itcgae.siga.db.entities.ScsCalendarioguardiasExample;
import org.itcgae.siga.db.entities.ScsCalendarioguardiasKey;

public interface ScsCalendarioguardiasMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @SelectProvider(type=ScsCalendarioguardiasSqlProvider.class, method="countByExample")
    long countByExample(ScsCalendarioguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @DeleteProvider(type=ScsCalendarioguardiasSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsCalendarioguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @Delete({
        "delete from SCS_CALENDARIOGUARDIAS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
          "and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}",
          "and IDCALENDARIOGUARDIAS = #{idcalendarioguardias,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsCalendarioguardiasKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @Insert({
        "insert into SCS_CALENDARIOGUARDIAS (IDINSTITUCION, IDTURNO, ",
        "IDGUARDIA, IDCALENDARIOGUARDIAS, ",
        "FECHAINICIO, FECHAFIN, ",
        "FECHAMODIFICACION, USUMODIFICACION, ",
        "OBSERVACIONES, IDPERSONA_ULTIMOANTERIOR, ",
        "FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, ",
        "IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL, ",
        "IDGRUPOGUARDIA_ULTIMOANTERIOR)",
        "values (#{idinstitucion,jdbcType=DECIMAL}, #{idturno,jdbcType=DECIMAL}, ",
        "#{idguardia,jdbcType=DECIMAL}, #{idcalendarioguardias,jdbcType=DECIMAL}, ",
        "#{fechainicio,jdbcType=TIMESTAMP}, #{fechafin,jdbcType=TIMESTAMP}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL}, ",
        "#{observaciones,jdbcType=VARCHAR}, #{idpersonaUltimoanterior,jdbcType=DECIMAL}, ",
        "#{fechasuscUltimoanterior,jdbcType=TIMESTAMP}, #{idturnoprincipal,jdbcType=DECIMAL}, ",
        "#{idguardiaprincipal,jdbcType=DECIMAL}, #{idcalendarioguardiasprincipal,jdbcType=DECIMAL}, ",
        "#{idgrupoguardiaUltimoanterior,jdbcType=DECIMAL})"
    })
    int insert(ScsCalendarioguardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @InsertProvider(type=ScsCalendarioguardiasSqlProvider.class, method="insertSelective")
    int insertSelective(ScsCalendarioguardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @SelectProvider(type=ScsCalendarioguardiasSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCALENDARIOGUARDIAS", property="idcalendarioguardias", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAINICIO", property="fechainicio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAFIN", property="fechafin", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERSONA_ULTIMOANTERIOR", property="idpersonaUltimoanterior", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHASUSC_ULTIMOANTERIOR", property="fechasuscUltimoanterior", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTURNOPRINCIPAL", property="idturnoprincipal", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGUARDIAPRINCIPAL", property="idguardiaprincipal", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCALENDARIOGUARDIASPRINCIPAL", property="idcalendarioguardiasprincipal", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGRUPOGUARDIA_ULTIMOANTERIOR", property="idgrupoguardiaUltimoanterior", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsCalendarioguardias> selectByExample(ScsCalendarioguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @Select({
        "select",
        "IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAINICIO, FECHAFIN, ",
        "FECHAMODIFICACION, USUMODIFICACION, OBSERVACIONES, IDPERSONA_ULTIMOANTERIOR, ",
        "FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL, ",
        "IDGRUPOGUARDIA_ULTIMOANTERIOR",
        "from SCS_CALENDARIOGUARDIAS",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
          "and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}",
          "and IDCALENDARIOGUARDIAS = #{idcalendarioguardias,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDCALENDARIOGUARDIAS", property="idcalendarioguardias", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAINICIO", property="fechainicio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAFIN", property="fechafin", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERSONA_ULTIMOANTERIOR", property="idpersonaUltimoanterior", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHASUSC_ULTIMOANTERIOR", property="fechasuscUltimoanterior", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDTURNOPRINCIPAL", property="idturnoprincipal", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGUARDIAPRINCIPAL", property="idguardiaprincipal", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCALENDARIOGUARDIASPRINCIPAL", property="idcalendarioguardiasprincipal", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGRUPOGUARDIA_ULTIMOANTERIOR", property="idgrupoguardiaUltimoanterior", jdbcType=JdbcType.DECIMAL)
    })
    ScsCalendarioguardias selectByPrimaryKey(ScsCalendarioguardiasKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @UpdateProvider(type=ScsCalendarioguardiasSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsCalendarioguardias record, @Param("example") ScsCalendarioguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @UpdateProvider(type=ScsCalendarioguardiasSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsCalendarioguardias record, @Param("example") ScsCalendarioguardiasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @UpdateProvider(type=ScsCalendarioguardiasSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsCalendarioguardias record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CALENDARIOGUARDIAS
     *
     * @mbg.generated Mon Jul 26 08:52:58 CEST 2021
     */
    @Update({
        "update SCS_CALENDARIOGUARDIAS",
        "set FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP},",
          "FECHAFIN = #{fechafin,jdbcType=TIMESTAMP},",
          "FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL},",
          "OBSERVACIONES = #{observaciones,jdbcType=VARCHAR},",
          "IDPERSONA_ULTIMOANTERIOR = #{idpersonaUltimoanterior,jdbcType=DECIMAL},",
          "FECHASUSC_ULTIMOANTERIOR = #{fechasuscUltimoanterior,jdbcType=TIMESTAMP},",
          "IDTURNOPRINCIPAL = #{idturnoprincipal,jdbcType=DECIMAL},",
          "IDGUARDIAPRINCIPAL = #{idguardiaprincipal,jdbcType=DECIMAL},",
          "IDCALENDARIOGUARDIASPRINCIPAL = #{idcalendarioguardiasprincipal,jdbcType=DECIMAL},",
          "IDGRUPOGUARDIA_ULTIMOANTERIOR = #{idgrupoguardiaUltimoanterior,jdbcType=DECIMAL}",
        "where IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTURNO = #{idturno,jdbcType=DECIMAL}",
          "and IDGUARDIA = #{idguardia,jdbcType=DECIMAL}",
          "and IDCALENDARIOGUARDIAS = #{idcalendarioguardias,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsCalendarioguardias record);
    
    @SelectProvider(type = ScsCalendarioguardiasSqlProvider.class, method = "setLogName")
	@Results(
			)
	String setLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia);
	
	
//	@SelectProvider(type = ScsCalendarioguardiasSqlProvider.class, method = "addLogName")
//	@Results(
//			)
//	String addLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia);
//	
	
	@SelectProvider(type = ScsCalendarioguardiasSqlProvider.class, method = "getLogName")
	@Results(
			)
	String getLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String idTurno, String idGuardia);
	
	@SelectProvider(type = ScsCalendarioguardiasSqlProvider.class, method = "getGeneracionEnProceso")
	@Results(
			)
	List<String> getGeneracionEnProceso();
	
	@SelectProvider(type = ScsCalendarioguardiasSqlProvider.class, method = "getGenerado")
	@Results(
			)
	String getGenerado(String idProgCal);
	
	@InsertProvider(type = ScsCalendarioguardiasSqlProvider.class, method = "setGeneracionEnProceso")
	@Results(
			)
	int setGeneracionEnProceso(String idProgCal, String procesando);
	
}