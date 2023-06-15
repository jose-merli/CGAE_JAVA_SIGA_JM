package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FcsFactEstadosfacturacionMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsFactEstadosfacturacionSqlExtendsProvider;
import org.springframework.stereotype.Service;

@Service
public interface FcsFactEstadosfacturacionExtendsMapper extends FcsFactEstadosfacturacionMapper {

    @SelectProvider(type = FcsFactEstadosfacturacionSqlExtendsProvider.class, method = "getIdEstadoFacturacion")
    String getIdEstadoFacturacion(Short idInstitucion, String idFacturacion);

    @SelectProvider(type = FcsFactEstadosfacturacionSqlExtendsProvider.class, method = "getIdordenestadoMaximo")
    String getIdordenestadoMaximo(Short idInstitucion, String idFacturacion);
}
