package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.mappers.PysCompraMapper;
import org.itcgae.siga.db.services.fac.providers.PysCompraSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PysCompraExtendsMapper extends PysCompraMapper {

    @SelectProvider(type = PysCompraSqlExtendsProvider.class, method = "obtenerComprasPeticion")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPETICION", property = "idpeticion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPRODUCTOINSTITUCION", property = "idproductoinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTEUNITARIO", property = "importeunitario", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDFORMAPAGO", property = "idformapago", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEANTICIPADO", property = "importeanticipado", jdbcType = JdbcType.DECIMAL),
            @Result(column = "ACEPTADO", property = "aceptado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NUMEROLINEA", property = "numerolinea", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDFACTURA", property = "idfactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDCUENTA", property = "idcuenta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERSONADEUDOR", property = "idpersonadeudor", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDCUENTADEUDOR", property = "idcuentadeudor", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOFACTURABLE", property = "nofacturable", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOIVA", property = "idtipoiva", jdbcType = JdbcType.DECIMAL)
    })
    List<PysCompra> obtenerComprasPeticion(String idInstitucion, String idPeticion);

    @SelectProvider(type = PysCompraSqlExtendsProvider.class, method = "lockTable")
    void lockTable();

    @SelectProvider(type = PysCompraSqlExtendsProvider.class, method = "obtenerCompraCertificado")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPETICION", property = "idpeticion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPRODUCTOINSTITUCION", property = "idproductoinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTEUNITARIO", property = "importeunitario", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDFORMAPAGO", property = "idformapago", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEANTICIPADO", property = "importeanticipado", jdbcType = JdbcType.DECIMAL),
            @Result(column = "ACEPTADO", property = "aceptado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NUMEROLINEA", property = "numerolinea", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDFACTURA", property = "idfactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDCUENTA", property = "idcuenta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERSONADEUDOR", property = "idpersonadeudor", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDCUENTADEUDOR", property = "idcuentadeudor", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOFACTURABLE", property = "nofacturable", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOIVA", property = "idtipoiva", jdbcType = JdbcType.DECIMAL)
    })
    List<PysCompra> obtenerCompraCertificado(String idInstitucion, String idSolicitudCertificado);
}
