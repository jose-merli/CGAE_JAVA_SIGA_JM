package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.ScsTipoactuacion;
import org.itcgae.siga.db.entities.ScsTipoactuacionExample;
import org.itcgae.siga.db.mappers.ScsTipoactuacionMapper;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoactuacionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaColegioSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTurnosExtendsMapper extends ScsTurnoMapper{

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnos")
	@Results({
		@Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTurnos(Short idInstitucion);
	

}
