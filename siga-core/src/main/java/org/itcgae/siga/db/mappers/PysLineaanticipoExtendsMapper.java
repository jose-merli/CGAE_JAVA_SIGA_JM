package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.Result;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederosItem;
import org.itcgae.siga.DTO.fac.MonederoDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface PysLineaanticipoExtendsMapper extends PysLineaanticipoMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_LINEAANTICIPO
     *
     * @mbg.generated Thu Oct 28 17:18:49 CEST 2021
     */
    @SelectProvider(type=PysLineaanticipoExtendsSqlProvider.class, method="selectByPersonIdAndCreationDate")
    @Results({
            @Result(column="FECHA", property="fecha", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="NIFCIF", property="nifCif", jdbcType=JdbcType.VARCHAR),
            @Result(column="NOMBRE_COMPLETO", property="nombreCompleto", jdbcType=JdbcType.VARCHAR),
            @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
            @Result(column="IMPORTE_INICIAL", property="importeInicial", jdbcType=JdbcType.DECIMAL),
            @Result(column="IMPORTE_RESTANTE", property="importeRestante", jdbcType=JdbcType.DECIMAL),
            @Result(column="IMPORTE_USADO", property="importeUsado", jdbcType=JdbcType.DECIMAL),
            @Result(column="IDPERSONA", property="idPersona", jdbcType=JdbcType.DECIMAL),
            @Result(column="IDLINEA", property="idLinea", jdbcType=JdbcType.DECIMAL)
    })
    List<ListaMonederosItem> selectByPersonIdAndCreationDate(Short institutionId, FiltroMonederoItem filter);
    
    @SelectProvider(type=PysLineaanticipoExtendsSqlProvider.class, method="selectMaxIdLinea")
    @Results({
            @Result(column="ID", property="newId", jdbcType=JdbcType.VARCHAR)
    })
    NewIdDTO selectMaxIdLinea(Short idInstitution, Long idPersona);
}
