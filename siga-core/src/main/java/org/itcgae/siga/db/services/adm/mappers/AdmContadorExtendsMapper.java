package org.itcgae.siga.db.services.adm.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.AdmContadorDTO;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroItem;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.services.adm.providers.AdmContadorSqlExtendsProvider;
import org.itcgae.siga.db.services.adm.providers.GenParametrosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface AdmContadorExtendsMapper extends AdmContadorMapper {


    @SelectProvider(type = AdmContadorSqlExtendsProvider.class, method = "getModules")
    @Results({
            @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODULO", property = "value", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> getModules();


    @SelectProvider(type = AdmContadorSqlExtendsProvider.class, method = "getMode")
    @Results({
            @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODO", property = "value", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> getMode(String idLenguaje);


    @SelectProvider(type = AdmContadorSqlExtendsProvider.class, method = "getContadoresSearch")
    @Results({@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCONTADOR", property = "idcontador", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MODIFICABLECONTADOR", property = "modificablecontador", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MODO", property = "modo", jdbcType = JdbcType.DECIMAL),
            @Result(column = "CONTADOR", property = "contador", jdbcType = JdbcType.DECIMAL),
            @Result(column = "PREFIJO", property = "prefijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LONGITUDCONTADOR", property = "longitudcontador", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHARECONFIGURACION", property = "fechareconfiguracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "RECONFIGURACIONCONTADOR", property = "reconfiguracioncontador", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RECONFIGURACIONPREFIJO", property = "reconfiguracionprefijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RECONFIGURACIONSUFIJO", property = "reconfiguracionsufijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTABLA", property = "idtabla", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCAMPOCONTADOR", property = "idcampocontador", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCAMPOPREFIJO", property = "idcampoprefijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCAMPOSUFIJO", property = "idcamposufijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODULO", property = "idmodulo", jdbcType = JdbcType.DECIMAL),
            @Result(column = "GENERAL", property = "general", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUCREACION", property = "usucreacion", jdbcType = JdbcType.DECIMAL)})
    List<AdmContadorDTO> getContadoresSearch(int numPagina, ContadorRequestDTO example);


    @SelectProvider(type = GenParametrosSqlExtendsProvider.class, method = "getParametersRecord")
    @Results({
            @Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PARAMETRO", property = "parametro", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VALOR", property = "valor", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
    })
    List<ParametroItem> getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO);

    @SelectProvider(type = AdmContadorSqlExtendsProvider.class, method = "comprobarUnicidadContadorProdCertif")
    List<Object> comprobarUnicidadContadorProdCertif(int contadorSiguiente, AdmContador contador, String idTipoProducto,
                                                     String idProducto, String idProductoInstitucion);

    @SelectProvider(type = AdmContadorSqlExtendsProvider.class, method = "getSiguienteNumContador")
    @Results({
            @Result(column = "CONTADOR", property = "contador", jdbcType = JdbcType.DECIMAL)
    })
    Long getSiguienteNumContador(AdmContador contador);

    @SelectProvider(type = AdmContadorSqlExtendsProvider.class, method = "comprobarUnicidadContador")
    @Results({
            @Result(column = "CONTADOR", property = "contador", jdbcType = JdbcType.DECIMAL)
    })
    Long comprobarUnicidadContador(AdmContador contador, int contadorSiguiente);
}