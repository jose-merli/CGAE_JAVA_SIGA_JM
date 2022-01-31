package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.exea.DocumentacionIncorporacionItem;
import org.itcgae.siga.db.mappers.CenDocumentsolicitudinstituMapper;
import org.itcgae.siga.db.services.scs.providers.CenDocumentsolicitudinstituSqlExtendsProvider;

import java.util.List;

public interface CenDocumentsolicitudinstituExtendsMapper extends CenDocumentsolicitudinstituMapper {

    @SelectProvider(type = CenDocumentsolicitudinstituSqlExtendsProvider.class, method = "getDocRequerida")
    @Results({
            @Result(column = "IDDOCUMENTACION", property = "idDocumentacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "documento", jdbcType = JdbcType.VARCHAR),
            @Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
            @Result(column = "OBLIGATORIO", property = "obligatorio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREFICHERO", property = "nombreDoc", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODALIDAD", property = "idModalidad", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOSOLICITUD", property = "tipoSolicitud", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOCOLEGIACION", property = "tipoColegiacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CODIGOEXT", property = "codDocEXEA", jdbcType = JdbcType.VARCHAR)
    })
    List<DocumentacionIncorporacionItem> getDocRequerida(Short idInstitucion, String tipoColegiacion, String tipoSolicitud, String modalidad, String idLenguaje, String idSolicitud, String codDocAnexo);
}
