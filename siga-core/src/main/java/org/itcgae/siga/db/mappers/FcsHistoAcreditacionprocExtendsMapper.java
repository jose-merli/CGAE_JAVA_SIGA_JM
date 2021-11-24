package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FcsHistoAcreditacionprocExtendsMapper extends FcsHistoAcreditacionprocMapper{

    @DeleteProvider(type = FcsHistoAcreditacionprocSqlExtendsProvider.class, method = "deleteByAcreditacionProc")
    int deleteByAcreditacionProc(String idInstitucion, String idFacturacion);

}
