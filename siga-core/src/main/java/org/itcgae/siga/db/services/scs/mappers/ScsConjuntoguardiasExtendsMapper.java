package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.ListaGuardiasItem;
import org.itcgae.siga.db.mappers.ScsConjuntoguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsConjuntoguardiasSqlExtendsProvider;

import java.util.List;

public interface ScsConjuntoguardiasExtendsMapper extends ScsConjuntoguardiasMapper {

    @SelectProvider(
            type = ScsConjuntoguardiasSqlExtendsProvider.class,
            method = "searchListaGuardias"
    )
    @Results({@Result(
            column = "nombre",
            property = "nombre",
            jdbcType = JdbcType.VARCHAR
    ), @Result(
            column = "idlista",
            property = "idLista",
            jdbcType = JdbcType.VARCHAR
    ), @Result(
            column = "observaciones",
            property = "observaciones",
            jdbcType = JdbcType.VARCHAR
    ), @Result(
            column = "lugar",
            property = "lugar",
            jdbcType = JdbcType.VARCHAR
    ), @Result(
            column = "tipo",
            property = "tipoDesc",
            jdbcType = JdbcType.VARCHAR
    ), @Result(
            column = "idtipo",
            property = "idTipo",
            jdbcType = JdbcType.VARCHAR
    )})
    List<ListaGuardiasItem> searchListaGuardias(ListaGuardiasItem lista, Short idinstitucion, Integer tamMax);

    @SelectProvider(
            type = ScsConjuntoguardiasSqlExtendsProvider.class,
            method = "getNextIdLista"
    )
    @Results({@Result(
            column = "ID",
            property = "ID",
            jdbcType = JdbcType.VARCHAR
    )})
    String getNextIdLista(Short idInstitucion);
}
