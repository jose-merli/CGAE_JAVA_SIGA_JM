package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacPagoabonoefectivoSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacPagoabonoefectivoExtendsMapper extends FacPagoabonoefectivoMapper {

    @SelectProvider(type = FacPagoabonoefectivoSqlExtendsProvider.class, method = "getNuevoID")
    Long getNuevoID(String institucion, String abono);

    @DeleteProvider(type = FacPagoabonoefectivoSqlExtendsProvider.class, method = "deleteDeshacerCierre")
    int deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos);
}
