package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacPagosporcajaMapper;
import org.itcgae.siga.db.services.fcs.providers.FacPagosporcajaSqlExtendsProvider;
import org.springframework.stereotype.Service;

@Service
public interface FacPagosporcajaExtendsMapper extends FacPagosporcajaMapper {

    @SelectProvider(type = FacPagosporcajaSqlExtendsProvider.class, method = "getNuevoID")
    Short getNuevoID(String idInstitucion, String idFactura);

}
