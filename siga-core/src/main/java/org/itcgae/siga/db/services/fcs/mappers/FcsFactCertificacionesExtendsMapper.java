package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.entities.FcsCertificaciones;
import org.itcgae.siga.db.mappers.FcsFactCertificacionesMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsFactCertificacionesSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface FcsFactCertificacionesExtendsMapper extends FcsFactCertificacionesMapper {
    @SelectProvider(type = FcsFactCertificacionesSqlExtendsProvider.class, method = "getFechaMaxMinFact")
    FcsCertificaciones getFechaMaxMinFact(Short idInstitucion, String idFactList);
}
