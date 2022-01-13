package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacAbonoincluidoendisqueteMapper;
import org.itcgae.siga.db.services.fcs.providers.FacAbonoincluidoendisqueteSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacAbonoincluidoendisqueteExtendsMapper extends FacAbonoincluidoendisqueteMapper {

    @SelectProvider(type = FacAbonoincluidoendisqueteSqlExtendsProvider.class, method = "getDisquetesPorAbonos")
    List<Long> getDisquetesPorAbonos(Short idInstitucion, List<Long> idAbonos);

    @SelectProvider(type = FacAbonoincluidoendisqueteSqlExtendsProvider.class, method = "getRestoPagosDisquete")
    List<Integer> getRestoPagosDisquete(Short idInstitucion, Long idDisqueteAbono, Integer idPago);
}
