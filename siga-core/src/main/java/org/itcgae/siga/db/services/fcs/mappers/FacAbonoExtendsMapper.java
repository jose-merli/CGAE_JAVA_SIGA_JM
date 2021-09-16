package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacAbonoSqlExtendsProvider;
import org.springframework.stereotype.Service;

@Service
public interface FacAbonoExtendsMapper extends FacAbonoMapper {

    @SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "getNuevoID")
    Long getNuevoID(String idInstitucion);

}
