package org.itcgae.siga.db.services.scs.mappers;


import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
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

    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getGuardiasByTurnoColegiadoFecha")
    @Results({
            @Result(column = "idGuardia", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
    })
    List<ComboItem> getGuardiasByTurnoColegiadoFecha(String idPersona, Short idInstitucion, String guardiaDia, String idTurno);
    
    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getColegiadosGuardiaDia")
    @Results({ 
        @Result(column = "idpersona", property = "value", jdbcType = JdbcType.VARCHAR),
        @Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
    })
    List<ComboItem> getColegiadosGuardiaDia(String idTurno, String idGuardia, Short idInstitucion, String guardiaDia);

    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getGuardiasColegiadoNoSustitucion")
    @Results({
            @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="FECHAINICIO", property="fechainicio", jdbcType=JdbcType.TIMESTAMP, id=true),
            @Result(column="FECHAFIN", property="fechafin", jdbcType=JdbcType.TIMESTAMP, id=true),
            @Result(column="DIASGUARDIA", property="diasguardia", jdbcType=JdbcType.DECIMAL),
            @Result(column="DIASACOBRAR", property="diasacobrar", jdbcType=JdbcType.DECIMAL),
            @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
            @Result(column="RESERVA", property="reserva", jdbcType=JdbcType.VARCHAR),
            @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
            @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
            @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
            @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
            @Result(column="GUARDIA_SELECCIONLABORABLES", property="guardiaSeleccionlaborables", jdbcType=JdbcType.VARCHAR),
            @Result(column="GUARDIA_SELECCIONFESTIVOS", property="guardiaSeleccionfestivos", jdbcType=JdbcType.VARCHAR)
    })
    List<ScsGuardiascolegiado> getGuardiasColegiadoNoSustitucion(TarjetaAsistenciaResponseItem asistencia, Short idInstitucion);

    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getGuardiasColegiadoEnFecha")
    @Results({
            @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
    })
    List<ScsGuardiascolegiado> getGuardiasColegiadoEnFecha(TarjetaAsistenciaResponseItem asistencia, Short idInstitucion);

    @SelectProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "getGuardiasColegiado")
    @Results({
            @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="FECHAINICIO", property="fechainicio", jdbcType=JdbcType.TIMESTAMP, id=true),
            @Result(column="FECHAFIN", property="fechafin", jdbcType=JdbcType.TIMESTAMP, id=true),
            @Result(column="DIASGUARDIA", property="diasguardia", jdbcType=JdbcType.DECIMAL),
            @Result(column="DIASACOBRAR", property="diasacobrar", jdbcType=JdbcType.DECIMAL),
            @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
            @Result(column="RESERVA", property="reserva", jdbcType=JdbcType.VARCHAR),
            @Result(column="OBSERVACIONES", property="observaciones", jdbcType=JdbcType.VARCHAR),
            @Result(column="FACTURADO", property="facturado", jdbcType=JdbcType.VARCHAR),
            @Result(column="PAGADO", property="pagado", jdbcType=JdbcType.VARCHAR),
            @Result(column="IDFACTURACION", property="idfacturacion", jdbcType=JdbcType.DECIMAL),
            @Result(column="GUARDIA_SELECCIONLABORABLES", property="guardiaSeleccionlaborables", jdbcType=JdbcType.VARCHAR),
            @Result(column="GUARDIA_SELECCIONFESTIVOS", property="guardiaSeleccionfestivos", jdbcType=JdbcType.VARCHAR)
    })
    List<ScsGuardiascolegiado> getGuardiasColegiado(TarjetaAsistenciaResponseItem asistencia, Short idInstitucion, String idPersona);

    @DeleteProvider(type = ScsGuardiascolegiadoSqlExtendsProvider.class, method = "deleteGuardiasCalendario")
    public boolean deleteGuardiasCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

}
