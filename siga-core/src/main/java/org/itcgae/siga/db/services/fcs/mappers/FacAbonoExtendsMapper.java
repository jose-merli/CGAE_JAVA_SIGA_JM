package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacAbonoSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface FacAbonoExtendsMapper extends FacAbonoMapper {

    @SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "getNuevoID")
    Long getNuevoID(String idInstitucion);

    @SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "getIdAbonosPorPago")
    List<Long> getIdAbonosPorPago(Short idInstitucion, Integer idPagosjg);

    @UpdateProvider(type = FacAbonoSqlExtendsProvider.class, method = "restableceValoresAbono")
    int restableceValoresAbono(Short idInstitucion, Long idDisqueteAbono);

    @SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "hayAbonoPosterior")
    List<FacAbono> hayAbonoPosterior(Short idInstitucion, Integer idPago);

    @SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "getAbonoAnterior")
    List<Long> getAbonoAnterior(Short idInstitucion, Date fecha);
    
    @SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "getPagosCerrados")
    List<FacAbono> getPagosCerrados(String idInstitucion, String anio);
}
