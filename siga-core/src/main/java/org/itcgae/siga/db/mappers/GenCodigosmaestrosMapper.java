package org.itcgae.siga.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.GenCodigosmaestros;
import org.itcgae.siga.db.entities.GenCodigosmaestrosExample;

public interface GenCodigosmaestrosMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenCodigosmaestrosSqlProvider.class, method="countByExample")
    long countByExample(GenCodigosmaestrosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @DeleteProvider(type=GenCodigosmaestrosSqlProvider.class, method="deleteByExample")
    int deleteByExample(GenCodigosmaestrosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Delete({
        "delete from GEN_CODIGOSMAESTROS",
        "where CODIGO = #{codigo,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short codigo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @Insert({
        "insert into GEN_CODIGOSMAESTROS (CODIGO)",
        "values (#{codigo,jdbcType=DECIMAL})"
    })
    int insert(GenCodigosmaestros record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @InsertProvider(type=GenCodigosmaestrosSqlProvider.class, method="insertSelective")
    int insertSelective(GenCodigosmaestros record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @SelectProvider(type=GenCodigosmaestrosSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="CODIGO", property="codigo", jdbcType=JdbcType.DECIMAL, id=true)
    })
    List<GenCodigosmaestros> selectByExample(GenCodigosmaestrosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenCodigosmaestrosSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") GenCodigosmaestros record, @Param("example") GenCodigosmaestrosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CODIGOSMAESTROS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    @UpdateProvider(type=GenCodigosmaestrosSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") GenCodigosmaestros record, @Param("example") GenCodigosmaestrosExample example);
}