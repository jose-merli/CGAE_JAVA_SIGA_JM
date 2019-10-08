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
import org.itcgae.siga.db.entities.ScsTipoasistenciaguardia;
import org.itcgae.siga.db.entities.ScsTipoasistenciaguardiaExample;
import org.itcgae.siga.db.entities.ScsTipoasistenciaguardiaKey;

public interface ScsTipoasistenciaguardiaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @SelectProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="countByExample")
    long countByExample(ScsTipoasistenciaguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @DeleteProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="deleteByExample")
    int deleteByExample(ScsTipoasistenciaguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @Delete({
        "delete from SCS_TIPOASISTENCIAGUARDIA",
        "where IDTIPOGUARDIA = #{idtipoguardia,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIACOLEGIO = #{idtipoasistenciacolegio,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(ScsTipoasistenciaguardiaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @Insert({
        "insert into SCS_TIPOASISTENCIAGUARDIA (IDTIPOGUARDIA, IDINSTITUCION, ",
        "IDTIPOASISTENCIACOLEGIO, FECHAMODIFICACION, ",
        "USUMODIFICACION)",
        "values (#{idtipoguardia,jdbcType=DECIMAL}, #{idinstitucion,jdbcType=DECIMAL}, ",
        "#{idtipoasistenciacolegio,jdbcType=DECIMAL}, #{fechamodificacion,jdbcType=TIMESTAMP}, ",
        "#{usumodificacion,jdbcType=DECIMAL})"
    })
    int insert(ScsTipoasistenciaguardia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @InsertProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="insertSelective")
    int insertSelective(ScsTipoasistenciaguardia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @SelectProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDTIPOGUARDIA", property="idtipoguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOASISTENCIACOLEGIO", property="idtipoasistenciacolegio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<ScsTipoasistenciaguardia> selectByExample(ScsTipoasistenciaguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @Select({
        "select",
        "IDTIPOGUARDIA, IDINSTITUCION, IDTIPOASISTENCIACOLEGIO, FECHAMODIFICACION, USUMODIFICACION",
        "from SCS_TIPOASISTENCIAGUARDIA",
        "where IDTIPOGUARDIA = #{idtipoguardia,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIACOLEGIO = #{idtipoasistenciacolegio,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="IDTIPOGUARDIA", property="idtipoguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTIPOASISTENCIACOLEGIO", property="idtipoasistenciacolegio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    ScsTipoasistenciaguardia selectByPrimaryKey(ScsTipoasistenciaguardiaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @UpdateProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ScsTipoasistenciaguardia record, @Param("example") ScsTipoasistenciaguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @UpdateProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ScsTipoasistenciaguardia record, @Param("example") ScsTipoasistenciaguardiaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @UpdateProvider(type=ScsTipoasistenciaguardiaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ScsTipoasistenciaguardia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_TIPOASISTENCIAGUARDIA
     *
     * @mbg.generated Wed Oct 02 09:49:23 CEST 2019
     */
    @Update({
        "update SCS_TIPOASISTENCIAGUARDIA",
        "set FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP},",
          "USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}",
        "where IDTIPOGUARDIA = #{idtipoguardia,jdbcType=DECIMAL}",
          "and IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}",
          "and IDTIPOASISTENCIACOLEGIO = #{idtipoasistenciacolegio,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ScsTipoasistenciaguardia record);
}