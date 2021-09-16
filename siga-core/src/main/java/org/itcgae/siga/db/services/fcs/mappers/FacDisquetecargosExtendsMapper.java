package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacDisquetecargosMapper;
import org.itcgae.siga.db.services.fcs.providers.FacDisquetecargosSqlExtendsProvider;
import org.springframework.stereotype.Service;

@Service
public interface FacDisquetecargosExtendsMapper extends FacDisquetecargosMapper {

    @SelectProvider(type = FacDisquetecargosSqlExtendsProvider.class, method = "getRenegociacionFactura")
    String getRenegociacionFactura(String institucion, String factura);
}
