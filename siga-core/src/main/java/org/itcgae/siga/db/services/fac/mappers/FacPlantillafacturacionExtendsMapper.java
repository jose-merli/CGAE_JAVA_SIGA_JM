package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.FacPlantillafacturacion;
import org.itcgae.siga.db.mappers.FacPlantillafacturacionMapper;
import org.itcgae.siga.db.services.fac.providers.FacPlantillafacturacionSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacPlantillafacturacionExtendsMapper extends FacPlantillafacturacionMapper {

    @SelectProvider(type = FacPlantillafacturacionSqlExtendsProvider.class, method = "getPlantillaSerieFacturacion")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "IDPLANTILLA", property = "idplantilla", jdbcType = JdbcType.DECIMAL, id = true),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PLANTILLAPDF", property = "plantillapdf", jdbcType = JdbcType.VARCHAR)
    })
    List<FacPlantillafacturacion> getPlantillaSerieFacturacion(String institucion, String serieFacturacion);
}
