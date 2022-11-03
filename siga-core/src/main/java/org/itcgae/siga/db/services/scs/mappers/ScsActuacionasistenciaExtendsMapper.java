package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsActuacionasistenciaSqlExtendsProvider;
import org.springframework.stereotype.Service;

@Service
//@Primary
public interface ScsActuacionasistenciaExtendsMapper extends ScsActuacionasistenciaMapper {

    @SelectProvider(type= ScsActuacionasistenciaSqlExtendsProvider.class, method="comboCosteFijoTipoActuacion")
    @Results({
            @Result(column="ID", property="value", jdbcType= JdbcType.VARCHAR),
            @Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR),
    })
    List<ComboItem> comboCosteFijoTipoActuacion(String idTipoActuacion, Short idInstitucion, Short idTipoAsistencia, Integer idLenguaje);

    @SelectProvider(type= ScsActuacionasistenciaSqlExtendsProvider.class, method="comboTipoActuacion")
    @Results({
            @Result(column="ID", property="value", jdbcType= JdbcType.VARCHAR),
            @Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR),
    })
    List<ComboItem> comboTipoActuacion(Short idInstitucion, Short idTipoAsistencia, Integer idLenguaje);

    @SelectProvider(type = ScsActuacionasistenciaSqlExtendsProvider.class, method = "getNewIdActuacion")
    @Results({ @Result(column = "IDACTUACION", property = "newId", jdbcType = JdbcType.VARCHAR)

    })
    NewIdDTO getNewIdActuacion(Short idInstitucion, String anioNumero);
    
    @SelectProvider(type = ScsActuacionasistenciaSqlExtendsProvider.class, method = "controlCheckDiaDespues")
    int controlCheckDiaDespues(Short idInstitucion, String idTurno, String idGuardia);

}
