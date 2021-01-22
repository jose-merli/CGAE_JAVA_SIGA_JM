package org.itcgae.siga.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.GenFecha;
import org.itcgae.siga.db.entities.GenFechaExample;

public interface GenFechaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenFechaSqlProvider.class, method="countByExample")
    long countByExample(GenFechaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @DeleteProvider(type=GenFechaSqlProvider.class, method="deleteByExample")
    int deleteByExample(GenFechaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Insert({
        "insert into GEN_FECHA (FECHA)",
        "values (#{fecha,jdbcType=TIMESTAMP})"
    })
    int insert(GenFecha record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @InsertProvider(type=GenFechaSqlProvider.class, method="insertSelective")
    int insertSelective(GenFecha record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenFechaSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="FECHA", property="fecha", jdbcType=JdbcType.TIMESTAMP)
    })
    List<GenFecha> selectByExample(GenFechaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenFechaSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") GenFecha record, @Param("example") GenFechaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_FECHA
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenFechaSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") GenFecha record, @Param("example") GenFechaExample example);
}