package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;
import org.itcgae.siga.db.entities.FcsMvariosCertificaciones;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsMovimientosvariosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FcsMovimientosvariosExtendsMapper extends FcsMovimientosvariosMapper {


    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "ejecutarFuncionMovVario")
    String ejecutarFuncionMovVario(String idInstitucion, String idMovimiento, String funcion);

    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "comprobarExistenciaActuacion")
    List<String> comprobarExistenciaActuacion(String nombreTabla, String idInstitucion, String idMovimiento);

    @UpdateProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "quitaMovimientoVarioAsociado")
    int quitaMovimientoVarioAsociado(String nombreTabla, String idInstitucion, String idMovimiento);

    @DeleteProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "delete")
    int delete(String idInstitucion, String idMovimiento);

    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "comboTiposMovimientos")
    @Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboTiposMovimientos(String idInstitucion);


    @InsertProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "saveClienteMovimientosVarios")
    int saveClienteMovimientosVarios(MovimientosVariosFacturacionItem movimientoItem, String idInstitucion);

    @UpdateProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "updateClienteMovimientosVarios")
    int updateClienteMovimientosVarios(MovimientosVariosFacturacionItem movimientoItem, String idInstitucion);


    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "getListadoPagos")
    @Results({@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREPAGO", property = "nombrePago", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTEAPLICADO", property = "cantidad", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTERESTANTE", property = "cantidadRestante", jdbcType = JdbcType.VARCHAR)})
    List<MovimientosVariosFacturacionItem> getListadoPagos(MovimientosVariosFacturacionItem facturacionItem,
                                                           String idIns);


    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "selectMaxIdMovimientoByIdInstitucion")
    @Results({
            @Result(column = "IDMOVIMIENTO", property = "newId", jdbcType = JdbcType.VARCHAR)
    })
    NewIdDTO selectMaxIdMovimientoByIdInstitucion(String idInstitucion);


    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "selectIdPersonaByNif")
    @Results({
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)
    })
    String selectIdPersonaByNif(String nif);

    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "getMovimientoVarioPorId")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMOVIMIENTO", property = "idMovimiento", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "letrado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MOVIMIENTO", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRELETRADO", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDGRUPOFACTURACION", property = "idGrupoFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCERTIFICACION", property = "certificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDHITOGENERAL", property = "idConcepto", jdbcType = JdbcType.VARCHAR)})
    MovimientosVariosFacturacionItem getMovimientoVarioPorId(String idMovimiento, Short idInstitucion);


    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "comboCertificacionSJCS")
    @Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboCertificacionSJCS(String idInstitucion);


    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "selectMaxIdCertificacionByIdInstitucion")
    @Results({
            @Result(column = "IDCERTIFICACION", property = "newId", jdbcType = JdbcType.VARCHAR)
    })
    NewIdDTO selectMaxIdCertificacionByIdInstitucion(String idInstitucion);

    
    @SelectProvider(type = FcsMovimientosvariosSqlExtendsProvider.class, method = "buscarMVColegiado")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMOVIMIENTO", property = "idMovimiento", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "letrado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MOVIMIENTO", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.DECIMAL),
            @Result(column = "CANTIDADAPLICADA", property = "cantidadAplicada", jdbcType = JdbcType.DECIMAL),
            @Result(column = "CANTIDADRESTANTE", property = "cantidadRestante", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPAGOSJG", property = "idAplicadoEnPago", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDGRUPOFACTURACION", property = "idGrupoFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRELETRADO", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREFACTURACION", property = "nombrefacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PAGOASOCIADO", property = "nombrePago", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCERTIFICACION", property = "certificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCONCEPTO", property = "idConcepto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRETIPO", property = "nombretipo", jdbcType = JdbcType.VARCHAR)})
    List<MovimientosVariosFacturacionItem> buscarMVColegiado(MovimientosVariosFacturacionItem movimientoItem, String idInstitucion, Integer tamMaximo);


    
}
