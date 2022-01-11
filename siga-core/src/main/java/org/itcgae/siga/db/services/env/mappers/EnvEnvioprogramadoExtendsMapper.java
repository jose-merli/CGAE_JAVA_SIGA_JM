package org.itcgae.siga.db.services.env.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.services.env.providers.EnvEnvioprogramadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface EnvEnvioprogramadoExtendsMapper extends EnvEnvioprogramadoMapper {

    @DeleteProvider(type = EnvEnvioprogramadoSqlExtendsProvider.class, method = "eliminarEnviosPago")
    int eliminarEnviosPago(Short idInstitucion, List<String> idPagos);
}
