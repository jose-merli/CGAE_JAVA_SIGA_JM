package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacLineaabonoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacLineaabonoSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacLineaabonoExtendsMapper extends FacLineaabonoMapper {

    @SelectProvider(type = FacLineaabonoSqlExtendsProvider.class, method = "getNuevoID")
    Long getNuevoID(String idInstitucion, String idAbono);

    @DeleteProvider(type = FacLineaabonoSqlExtendsProvider.class, method = "deleteDeshacerCierre")
    int deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos);
}
