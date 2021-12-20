package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.DatosLiquidacionIntegrantesSearchItem;
import org.itcgae.siga.db.mappers.CenHistoricoLiquidacionsjcsMapper;
import org.itcgae.siga.db.services.cen.providers.CenHistoricoLiquidacionsjcsExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenHistoricoLiquidacionsjcsExtendsMapper extends CenHistoricoLiquidacionsjcsMapper{


	@SelectProvider(type = CenHistoricoLiquidacionsjcsExtendsProvider.class, method = "selectLiquidacion")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOMPONENTE", property = "idComponente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDAD", property = "sociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDHISTORICO", property = "idHistorico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.DECIMAL)
	})
	List<DatosLiquidacionIntegrantesSearchItem> selectLiquidacion(DatosLiquidacionIntegrantesSearchItem datosLiquidacion);

	@SelectProvider(type = CenHistoricoLiquidacionsjcsExtendsProvider.class, method = "buscarPagosColegiados")
	int buscarPagosColegiados(DatosLiquidacionIntegrantesSearchItem datosLiquidacion);
	
}
