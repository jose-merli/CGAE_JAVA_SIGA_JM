package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.VPcajgAlcActErrorCam;
import org.itcgae.siga.db.mappers.PcajgAlcActErrorCamMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsCertificacionesSqlExtendsProvider;
import org.itcgae.siga.db.services.fcs.providers.FcsPcajgAlcActErrorCamExtendsProvider;
import org.springframework.stereotype.Service;

@Service
public interface FcsPcajgAlcActErrorCamExtendsMapper extends PcajgAlcActErrorCamMapper {
	//CODIGO_ERROR,OBSERVACIONES_ERROR,CODIGO_CAMPO_ERROR,IDFACTURACION
    @SelectProvider(type = FcsPcajgAlcActErrorCamExtendsProvider.class, method = "buscarErroresCAM")
    @Results({@Result(column = "CODIGO_ERROR", property = "codigoError", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ERROR_DESCRIPCION", property = "errorDescripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.INTEGER),
            @Result(column = "CUENTA", property = "cuenta", jdbcType = JdbcType.INTEGER)})
    List<VPcajgAlcActErrorCam> buscarErroresCAM(String listaIdFacturacion, Short idInstitucion, int tamMaximo);

	
}
