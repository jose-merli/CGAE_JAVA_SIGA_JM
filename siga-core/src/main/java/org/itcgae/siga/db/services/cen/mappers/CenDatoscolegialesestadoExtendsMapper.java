package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.InsertProvider;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.mappers.CenDatoscolegialesestadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenDatoscolegialesestadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface CenDatoscolegialesestadoExtendsMapper extends CenDatoscolegialesestadoMapper {

	
	@InsertProvider(type = CenDatoscolegialesestadoSqlExtendsProvider.class, method = "insertColegiado")
	int insertColegiado(CenDatoscolegialesestado cenDatoscolegialesestado);

}
