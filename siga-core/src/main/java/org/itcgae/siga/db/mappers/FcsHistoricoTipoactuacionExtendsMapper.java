package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsHistoricoTipoactuacionExtendsMapper extends FcsHistoricoTipoactuacionMapper {

    @DeleteProvider(type = FcsHistoricoTipoactuacionSqlExtendsProvider.class, method = "deleteByAcreditacionProc")
    int delete(String idInstitucion, String idFacturacion);
}
