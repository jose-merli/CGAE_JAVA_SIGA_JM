package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenDocumentacionsolicitudMapper;
import org.itcgae.siga.db.services.cen.providers.CenDocumentacionsolicitudSqlExtendsProvider;

public interface CenDocumentacionsolicitudExtendsMapper extends CenDocumentacionsolicitudMapper {

    @SelectProvider(type = CenDocumentacionsolicitudSqlExtendsProvider.class, method = "getNextId")
    @Results({ @Result(column = "IDDOCUMENTACION", property = "newId", jdbcType = JdbcType.VARCHAR)

    })
    NewIdDTO getNextId();
}
