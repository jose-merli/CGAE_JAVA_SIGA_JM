package org.itcgae.siga.db.services.adm.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.AdmConsultaInformeConsultaDTO;
import org.itcgae.siga.db.mappers.AdmConsultainformeMapper;
import org.itcgae.siga.db.services.adm.providers.AdmConsultainformeSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public interface AdmConsultainformeExtendsMapper extends AdmConsultainformeMapper {

    @SelectProvider(type = AdmConsultainformeSqlExtendsProvider.class, method = "getConsultasInforme")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDINSTITUCION_CONSULTA", property = "idInstitucionConsulta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VARIASLINEAS", property = "variasLineas", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "GENERAL", property = "general", jdbcType = JdbcType.VARCHAR),
            @Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.DECIMAL)})
    List<AdmConsultaInformeConsultaDTO> getConsultasInforme(String idInstitucion, String idPlantilla);

    @SelectProvider(type = AdmConsultainformeSqlExtendsProvider.class, method = "selectCamposOrdenados")
    List<LinkedHashMap<String, String>> selectCamposOrdenados(String select);

    @SelectProvider(type = AdmConsultainformeSqlExtendsProvider.class, method = "getFechaCompletaBD")
    String getFechaCompletaBD();

}
