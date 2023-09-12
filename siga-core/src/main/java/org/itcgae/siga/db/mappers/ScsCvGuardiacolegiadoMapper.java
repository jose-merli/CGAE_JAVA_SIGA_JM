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
import org.itcgae.siga.db.entities.ScsCvGuardiacolegiado;
import org.itcgae.siga.db.entities.ScsCvGuardiacolegiadoExample;

public interface ScsCvGuardiacolegiadoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @SelectProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="countByExample")
    long countByExample(ScsCvGuardiacolegiadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @DeleteProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsCvGuardiacolegiadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @Delete({
        "delete from SCS_CV_GUARDIACOLEGIADO",
        "where IDSCSGUARDIACOLEGIADO = #{idscsguardiacolegiado,jdbcType=NUMERIC}"
    })
    int deleteByPrimaryKey(Integer idscsguardiacolegiado);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @Insert({
        "insert into SCS_CV_GUARDIACOLEGIADO (IDSCSGUARDIACOLEGIADO, IDINSTITUCION, ",
        "IDGUARDIA, FECHAGUARDIA, ",
        "NOMBREGUARDIA, ORDENGUARDIA, ",
        "FECHARECEPCION, NUMEROCOLEGIADO, ",
        "NOMBRECOLEGIADO, TELEFONO1, ",
        "TELEFONO2, TELEFONOMOVIL, ",
        "ACCION, EMAIL, IDTURNO, ",
        "IDPERSONA)",
        "values (#{idscsguardiacolegiado,jdbcType=NUMERIC}, #{idinstitucion,jdbcType=NUMERIC}, ",
        "#{idguardia,jdbcType=NUMERIC}, #{fechaguardia,jdbcType=TIMESTAMP}, ",
        "#{nombreguardia,jdbcType=VARCHAR}, #{ordenguardia,jdbcType=NUMERIC}, ",
        "#{fecharecepcion,jdbcType=TIMESTAMP}, #{numerocolegiado,jdbcType=VARCHAR}, ",
        "#{nombrecolegiado,jdbcType=VARCHAR}, #{telefono1,jdbcType=VARCHAR}, ",
        "#{telefono2,jdbcType=VARCHAR}, #{telefonomovil,jdbcType=VARCHAR}, ",
        "#{accion,jdbcType=NUMERIC}, #{email,jdbcType=VARCHAR}, #{idturno,jdbcType=NUMERIC}, ",
        "#{idpersona,jdbcType=NUMERIC})"
    })
    int insert(ScsCvGuardiacolegiado row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @InsertProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="insertSelective")
    int insertSelective(ScsCvGuardiacolegiado row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @SelectProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDSCSGUARDIACOLEGIADO", property="idscsguardiacolegiado", jdbcType=JdbcType.NUMERIC, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.NUMERIC),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.NUMERIC),
        @Result(column="FECHAGUARDIA", property="fechaguardia", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="NOMBREGUARDIA", property="nombreguardia", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDENGUARDIA", property="ordenguardia", jdbcType=JdbcType.NUMERIC),
        @Result(column="FECHARECEPCION", property="fecharecepcion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="NUMEROCOLEGIADO", property="numerocolegiado", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRECOLEGIADO", property="nombrecolegiado", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO1", property="telefono1", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO2", property="telefono2", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONOMOVIL", property="telefonomovil", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACCION", property="accion", jdbcType=JdbcType.NUMERIC),
        @Result(column="EMAIL", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.NUMERIC),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.NUMERIC)
    })
    List<ScsCvGuardiacolegiado> selectByExample(ScsCvGuardiacolegiadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @Select({
        "select",
        "IDSCSGUARDIACOLEGIADO, IDINSTITUCION, IDGUARDIA, FECHAGUARDIA, NOMBREGUARDIA, ",
        "ORDENGUARDIA, FECHARECEPCION, NUMEROCOLEGIADO, NOMBRECOLEGIADO, TELEFONO1, TELEFONO2, ",
        "TELEFONOMOVIL, ACCION, EMAIL, IDTURNO, IDPERSONA",
        "from SCS_CV_GUARDIACOLEGIADO",
        "where IDSCSGUARDIACOLEGIADO = #{idscsguardiacolegiado,jdbcType=NUMERIC}"
    })
    @Results({
        @Result(column="IDSCSGUARDIACOLEGIADO", property="idscsguardiacolegiado", jdbcType=JdbcType.NUMERIC, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.NUMERIC),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.NUMERIC),
        @Result(column="FECHAGUARDIA", property="fechaguardia", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="NOMBREGUARDIA", property="nombreguardia", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDENGUARDIA", property="ordenguardia", jdbcType=JdbcType.NUMERIC),
        @Result(column="FECHARECEPCION", property="fecharecepcion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="NUMEROCOLEGIADO", property="numerocolegiado", jdbcType=JdbcType.VARCHAR),
        @Result(column="NOMBRECOLEGIADO", property="nombrecolegiado", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO1", property="telefono1", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONO2", property="telefono2", jdbcType=JdbcType.VARCHAR),
        @Result(column="TELEFONOMOVIL", property="telefonomovil", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACCION", property="accion", jdbcType=JdbcType.NUMERIC),
        @Result(column="EMAIL", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.NUMERIC),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.NUMERIC)
    })
    ScsCvGuardiacolegiado selectByPrimaryKey(Integer idscsguardiacolegiado);
    
    @Select({
        "select",
        "Nvl(Max(IDSCSGUARDIACOLEGIADO), 0) as IDSCSGUARDIACOLEGIADO",
        "from SCS_CV_GUARDIACOLEGIADO"
    })
    @Results({
        @Result(column="IDSCSGUARDIACOLEGIADO", property="idscsguardiacolegiado", jdbcType=JdbcType.NUMERIC, id=true)
    })
    Integer selectLastIdScsCvGuardiacolegiado();
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @UpdateProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("row") ScsCvGuardiacolegiado row, @Param("example") ScsCvGuardiacolegiadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @UpdateProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("row") ScsCvGuardiacolegiado row, @Param("example") ScsCvGuardiacolegiadoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @UpdateProvider(type=ScsCvGuardiacolegiadoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsCvGuardiacolegiado row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_CV_GUARDIACOLEGIADO
     *
     * @mbg.generated Wed Sep 06 15:38:18 CEST 2023
     */
    @Update({
        "update SCS_CV_GUARDIACOLEGIADO",
        "set IDINSTITUCION = #{idinstitucion,jdbcType=NUMERIC},",
          "IDGUARDIA = #{idguardia,jdbcType=NUMERIC},",
          "FECHAGUARDIA = #{fechaguardia,jdbcType=TIMESTAMP},",
          "NOMBREGUARDIA = #{nombreguardia,jdbcType=VARCHAR},",
          "ORDENGUARDIA = #{ordenguardia,jdbcType=NUMERIC},",
          "FECHARECEPCION = #{fecharecepcion,jdbcType=TIMESTAMP},",
          "NUMEROCOLEGIADO = #{numerocolegiado,jdbcType=VARCHAR},",
          "NOMBRECOLEGIADO = #{nombrecolegiado,jdbcType=VARCHAR},",
          "TELEFONO1 = #{telefono1,jdbcType=VARCHAR},",
          "TELEFONO2 = #{telefono2,jdbcType=VARCHAR},",
          "TELEFONOMOVIL = #{telefonomovil,jdbcType=VARCHAR},",
          "ACCION = #{accion,jdbcType=NUMERIC},",
          "EMAIL = #{email,jdbcType=VARCHAR},",
          "IDTURNO = #{idturno,jdbcType=NUMERIC},",
          "IDPERSONA = #{idpersona,jdbcType=NUMERIC}",
        "where IDSCSGUARDIACOLEGIADO = #{idscsguardiacolegiado,jdbcType=NUMERIC}"
    })
    int updateByPrimaryKey(ScsCvGuardiacolegiado row);
}