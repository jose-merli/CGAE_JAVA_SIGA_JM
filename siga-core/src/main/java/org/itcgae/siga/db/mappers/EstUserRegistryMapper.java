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
import org.itcgae.siga.db.entities.EstUserRegistry;
import org.itcgae.siga.db.entities.EstUserRegistryExample;

public interface EstUserRegistryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @SelectProvider(type=EstUserRegistrySqlProvider.class, method="countByExample")
    long countByExample(EstUserRegistryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @DeleteProvider(type=EstUserRegistrySqlProvider.class, method="deleteByExample")
    int deleteByExample(EstUserRegistryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @Insert({
        "insert into EST_USER_REGISTRY (IDUSUARIO, IDINSTITUCION, ",
        "IDPERFIL, FECHA_REGISTRO)",
        "values (#{idusuario,jdbcType=NUMERIC}, #{idinstitucion,jdbcType=NUMERIC}, ",
        "#{idperfil,jdbcType=VARCHAR}, #{fechaRegistro,jdbcType=TIMESTAMP})"
    })
    int insert(EstUserRegistry record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @InsertProvider(type=EstUserRegistrySqlProvider.class, method="insertSelective")
    int insertSelective(EstUserRegistry record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @SelectProvider(type=EstUserRegistrySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="IDUSUARIO", property="idusuario", jdbcType=JdbcType.NUMERIC),
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.NUMERIC),
        @Result(column="IDPERFIL", property="idperfil", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHA_REGISTRO", property="fechaRegistro", jdbcType=JdbcType.TIMESTAMP)
    })
    List<EstUserRegistry> selectByExample(EstUserRegistryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @UpdateProvider(type=EstUserRegistrySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EstUserRegistry record, @Param("example") EstUserRegistryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.EST_USER_REGISTRY
     *
     * @mbg.generated Tue Oct 10 09:16:37 CEST 2023
     */
    @UpdateProvider(type=EstUserRegistrySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EstUserRegistry record, @Param("example") EstUserRegistryExample example);
}