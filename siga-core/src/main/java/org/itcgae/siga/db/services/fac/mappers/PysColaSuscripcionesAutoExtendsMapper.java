package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Select;
import org.itcgae.siga.db.mappers.PysColaSuscripcionesAutoMapper;

public interface PysColaSuscripcionesAutoExtendsMapper extends PysColaSuscripcionesAutoMapper {
    
    @Select({
        "select",
        "NVL(max(IDCOLASUSCRIPCION) +1, 1)",
        "from PYS_COLA_SUSCRIPCIONES_AUTO",
        "where rownum <= 1"
    })
    int getNewIdCola();
}