package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsHistoTipoactcostefijoExtendsMapper extends FcsHistoTipoactcostefijoMapper{

    @DeleteProvider(type = FcsHistoTipoactcostefijoSqlExtendsProvider.class, method = "delete")
    int delete(String idInstitucion, String idFacturacion);
}
