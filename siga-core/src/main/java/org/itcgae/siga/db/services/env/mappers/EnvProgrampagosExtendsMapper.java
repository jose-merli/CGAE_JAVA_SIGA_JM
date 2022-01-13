package org.itcgae.siga.db.services.env.mappers;

import org.apache.ibatis.annotations.Update;
import org.itcgae.siga.db.mappers.EnvProgrampagosMapper;
import org.springframework.stereotype.Service;

@Service
public interface EnvProgrampagosExtendsMapper extends EnvProgrampagosMapper {

    @Update("ALTER TABLE ENV_PROGRAMPAGOS DISABLE CONSTRAINT FK_PROGRAMPAGOS_ENVIOPROG")
    int disableConstraint();

    @Update("ALTER TABLE ENV_PROGRAMPAGOS ENABLE CONSTRAINT FK_PROGRAMPAGOS_ENVIOPROG")
    int enableConstraint();

}
