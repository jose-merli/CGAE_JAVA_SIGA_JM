package org.itcgae.siga.db.mappers;

import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.EnvEnviosgrupocliente;
import org.itcgae.siga.db.entities.EnvEnviosgrupoclienteExample;

public interface EnvEnviosgrupoclienteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @SelectProvider(type=EnvEnviosgrupoclienteSqlProvider.class, method="countByExample")
    long countByExample(EnvEnviosgrupoclienteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @DeleteProvider(type=EnvEnviosgrupoclienteSqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvEnviosgrupoclienteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @Insert({
        "insert into ENV_ENVIOSGRUPOCLIENTE (IDENVIO, IDGRUPO, ",
        "FECHAMODIFICACION, USUMODIFICACION)",
        "values (#{idenvio,jdbcType=DECIMAL}, #{idgrupo,jdbcType=DECIMAL}, ",
        "#{fechamodificacion,jdbcType=TIMESTAMP}, #{usumodificacion,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT SEQ_ENV_ENVIOPROGRAMADO.NEXTVAL FROM DUAL", keyProperty="idenvio", before=true, resultType=Long.class)
    int insert(EnvEnviosgrupocliente record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @InsertProvider(type=EnvEnviosgrupoclienteSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT SEQ_ENV_ENVIOPROGRAMADO.NEXTVAL FROM DUAL", keyProperty="idenvio", before=true, resultType=Long.class)
    int insertSelective(EnvEnviosgrupocliente record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @SelectProvider(type=EnvEnviosgrupoclienteSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDENVIO", property="idenvio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDGRUPO", property="idgrupo", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL)
    })
    List<EnvEnviosgrupocliente> selectByExample(EnvEnviosgrupoclienteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @UpdateProvider(type=EnvEnviosgrupoclienteSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvEnviosgrupocliente record, @Param("example") EnvEnviosgrupoclienteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOSGRUPOCLIENTE
     *
     * @mbg.generated Fri Nov 30 13:10:14 CET 2018
     */
    @UpdateProvider(type=EnvEnviosgrupoclienteSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvEnviosgrupocliente record, @Param("example") EnvEnviosgrupoclienteExample example);
}