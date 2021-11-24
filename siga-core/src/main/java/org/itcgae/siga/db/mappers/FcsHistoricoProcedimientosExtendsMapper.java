package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FcsHistoricoProcedimientosExtendsMapper extends FcsHistoricoProcedimientosMapper{

    @DeleteProvider(type = FcsHistoricoProcedimientosSqlExtendsProvider.class, method = "deleteByProcedimiento")
    int deleteByProcedimiento(String idInstitucion, String idFacturacion);
}
