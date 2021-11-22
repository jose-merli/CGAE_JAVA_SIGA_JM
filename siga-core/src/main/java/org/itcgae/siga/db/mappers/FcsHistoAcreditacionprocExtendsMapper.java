package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.DeleteProvider;

import java.util.List;

public interface FcsHistoAcreditacionprocExtendsMapper extends FcsHistoAcreditacionprocMapper{

    @DeleteProvider(type = FcsHistoAcreditacionprocSqlExtendsProvider.class, method = "deleteByAcreditacionProc")
    int deleteByAcreditacionProc(String idInstitucion, String idFacturacion);

}
