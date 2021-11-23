package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.FcsHistoricoAcreditacionExample;
import org.itcgae.siga.db.services.scs.providers.ScsAcreditacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FcsHistoricoAcreditacionExtendsMapper extends FcsHistoricoAcreditacionMapper{

    @DeleteProvider(type = FcsHistoricoAcreditacionSqlExtendsProvider.class, method = "deleteByAcreditacion")
    int deleteByAcreditacion(String idInstitucion, String idFacturacion, List<String> acreditaciones);

}
