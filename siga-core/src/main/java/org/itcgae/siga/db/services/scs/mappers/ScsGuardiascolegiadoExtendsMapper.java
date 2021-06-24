package org.itcgae.siga.db.services.scs.mappers;


import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.mappers.ScsGuardiascolegiadoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiascolegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsGuardiascolegiadoExtendsMapper extends ScsGuardiascolegiadoMapper {

    

    
    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getTurnosGuardias")
    @Results({ 
        @Result(column = "COUNT", property = "valor", jdbcType = JdbcType.VARCHAR),
    })
    StringDTO getTurnosGuardias(String idPersona, Short idInstitucion);
    
    
    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getTurnosByColegiadoFecha")
    @Results({ 
        @Result(column = "idturno", property = "value", jdbcType = JdbcType.VARCHAR),
        @Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
    })
    List<ComboItem> getTurnosByColegiadoFecha(String idPersona, Short idInstitucion, String guardiaDia);
    
    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getColegiadosGuardiaDia")
    @Results({ 
        @Result(column = "idpersona", property = "value", jdbcType = JdbcType.VARCHAR),
        @Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
    })
    List<ComboItem> getColegiadosGuardiaDia(String idTurno, String idGuardia, Short idInstitucion, String guardiaDia);
}
