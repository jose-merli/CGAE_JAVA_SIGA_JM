package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;

import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DestinatariosItem;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoItem;
import org.itcgae.siga.db.mappers.FcsDestinatariosRetencionesMapper;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaMapper;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaSqlProvider;
import org.itcgae.siga.db.mappers.ScsProcedimientosMapper;
import org.itcgae.siga.db.services.scs.providers.ScsAreasMateriasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDestinatariosRetencionesSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPartidasPresupuestariasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDestinatariosRetencionesExtendsMapper extends FcsDestinatariosRetencionesMapper{

	@SelectProvider(type=ScsDestinatariosRetencionesSqlExtendsProvider.class, method="searchDestinatario")
    @Results({
    	@Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDDESTINATARIO", property="iddestinatario", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="CUENTACONTABLE", property="cuentacontable", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
	})
	List<DestinatariosItem> searchDestinatario(DestinatariosItem destinatariosItems);
	
	@SelectProvider(type=ScsDestinatariosRetencionesSqlExtendsProvider.class, method="updateRetenciones")
    @Results({
    	@Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDDESTINATARIO", property="iddestinatario", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NOMBRE", property="nombre", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="CUENTACONTABLE", property="cuentacontable", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDEN", property="orden", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP)
	})
	List<DestinatariosItem> updateRetenciones(Short idInstitucion,DestinatariosItem destinatariosItem);
	
	@SelectProvider(type = ScsDestinatariosRetencionesSqlExtendsProvider.class, method = "getIdDestinatariosRetenc")
	@Results({ @Result(column = "IDDESTINATARIORETENCIONES", property = "newId", jdbcType = JdbcType.DECIMAL, id=true)
	})
	NewIdDTO getIdDestinatariosRetenc(Short idInstitucion);
	
	
}
