package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoItem;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.services.scs.providers.ScsExpedienteEconomicoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsExpedienteEconomicoExtendsMapper extends ScsEejgPeticionesMapper {

	@SelectProvider(type = ScsExpedienteEconomicoSqlExtendsProvider.class, method = "getExpedientesEconomicos")
	@Results({ 
		@Result(column = "justiciable", property = "justiciable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "solicitadopor", property = "solicitadoPor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechasolicitud", property = "f_solicitud", jdbcType = JdbcType.DATE),
		@Result(column = "fechapeticion", property = "f_recepcion", jdbcType = JdbcType.DATE),
		@Result(column = "idEstado", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "csv", property = "csv", jdbcType = JdbcType.VARCHAR),
	})
	List<ExpedienteEconomicoItem> getExpedientesEconomicos(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje);
}
