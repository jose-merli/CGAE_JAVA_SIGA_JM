package org.itcgae.siga.db.services.cen.mappers;

import java.util.Date;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;
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
	
	@UpdateProvider(type = CenDatoscolegialesestadoSqlExtendsProvider.class, method = "updateEstadoColegial")
	int updateEstadoColegial(CenDatoscolegialesestado record, Date fechaEstadoNueva);
	
	@UpdateProvider(type = CenDatoscolegialesestadoSqlExtendsProvider.class, method = "deleteEstadoColegial")
	int deleteEstadoColegial(CenDatoscolegialesestado record);

}
