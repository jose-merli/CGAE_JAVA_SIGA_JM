package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuacionasistenciaExample;
import org.itcgae.siga.db.entities.ScsTipoactuacion;
import org.itcgae.siga.db.entities.ScsTipoactuacionExample;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoactuacionMapper;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoactuacionAsistenciaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoactuacionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaColegioSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoActuacionAsistenciaExtendsMapper extends ScsActuacionasistenciaMapper{

	@SelectProvider(type=ScsTipoactuacionAsistenciaSqlExtendsProvider.class, method="selectTiposActuacionasistencia")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="NUMERO", property="numero", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDACTUACION", property="idactuacion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHA", property="fecha", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DIADESPUES", property="diadespues", jdbcType=JdbcType.VARCHAR),
        @Result(column="ACUERDOEXTRAJUDICIAL", property="acuerdoextrajudicial", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHAJUSTIFICACION", property="fechajustificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DESCRIPCIONBREVE", property="descripcionbreve", jdbcType=JdbcType.VARCHAR),
        @Result(column="LUGAR", property="lugar", jdbcType=JdbcType.VARCHAR),
        @Result(column="NUMEROASUNTO", property="numeroasunto", jdbcType=JdbcType.VARCHAR),
        @Result(column="ANULACION", property="anulacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="OBSERVACIONESJUSTIFICACION", property="observacionesjustificacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
        @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
        @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION_PRIS", property="idinstitucionPris", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPRISION", property="idprision", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION_COMIS", property="idinstitucionComis", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDCOMISARIA", property="idcomisaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION_JUZG", property="idinstitucionJuzg", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDJUZGADO", property="idjuzgado", jdbcType=JdbcType.DECIMAL),
        @Result(column="VALIDADA", property="validada", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDTIPOACTUACION", property="idtipoactuacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="NIG", property="nig", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDORIGENACTUACION", property="idorigenactuacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="USUCREACION", property="usucreacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHACREACION", property="fechacreacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="IDMOVIMIENTO", property="idmovimiento", jdbcType=JdbcType.DECIMAL)
    })
		List<ScsActuacionasistencia> selectTiposActuacionasistencia(TiposActuacionItem tiposActuacionItem, String idtiposasistencia);
}
