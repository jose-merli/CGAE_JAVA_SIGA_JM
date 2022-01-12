package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.itcgae.siga.db.mappers.FacHistoricofacturaMapper;
import org.itcgae.siga.db.services.fcs.providers.FacHistoricofacturaSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacHistoricofacturaExtendsMapper extends FacHistoricofacturaMapper {

    @InsertProvider(type = FacHistoricofacturaSqlExtendsProvider.class, method = "insertarHistoricoFacParametros")
    int insertarHistoricoFacParametros(String idInstitucion, String idFactura, Integer idTipoAccion,
                                       Integer idPagoPorCaja, Integer idDisqueteCargos, Integer idFacturaIncluidaEnDisquete,
                                       Integer idDisqueteDevoluciones, String idRecibo, Integer idRenegociacion, Integer idAbono, String comisionIdFactura);

    @DeleteProvider(type = FacHistoricofacturaSqlExtendsProvider.class, method = "deleteDeshacerCierre")
    int deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos);
}
