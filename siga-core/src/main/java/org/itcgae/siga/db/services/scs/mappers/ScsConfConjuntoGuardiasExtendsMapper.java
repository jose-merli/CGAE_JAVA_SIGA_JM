package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsConfConjuntoGuardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsConfConjuntoGuardiasSqlExtendsProvider;

import java.util.List;

public interface ScsConfConjuntoGuardiasExtendsMapper extends ScsConfConjuntoGuardiasMapper {
    @SelectProvider(type = ScsConfConjuntoGuardiasSqlExtendsProvider.class, method = "searchGuardiasFromLista")
    @Results({
            @Result(column = "orden", property = "orden", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idconjuntoguardia", property = "idListaGuardia", jdbcType = JdbcType.VARCHAR),
            @Result(column = "nombreguardia", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idguardia", property = "idGuardia", jdbcType = org.apache.ibatis.type.JdbcType.VARCHAR),
            @Result(column = "nombreturno", property = "turno", jdbcType = org.apache.ibatis.type.JdbcType.VARCHAR),
            @Result(column = "diaslaborables", property = "seleccionLaborables", jdbcType = org.apache.ibatis.type.JdbcType.VARCHAR),
            @Result(column = "diasfestivos", property = "seleccionFestivos", jdbcType = org.apache.ibatis.type.JdbcType.VARCHAR)})
    List<GuardiasItem> searchGuardiasFromLista(String idLista, Short idInstitucion, Integer tamMax);

}
