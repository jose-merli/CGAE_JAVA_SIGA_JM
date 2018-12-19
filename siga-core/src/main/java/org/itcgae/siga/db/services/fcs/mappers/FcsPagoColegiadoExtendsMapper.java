package org.itcgae.siga.db.services.fcs.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.mappers.FcsPagoColegiadoMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsPagoColegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsPagoColegiadoExtendsMapper extends FcsPagoColegiadoMapper {

	

	
	@SelectProvider(type = FcsPagoColegiadoSqlExtendsProvider.class, method = "selectPagosColegiadoDeVariasPersonas")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.VARCHAR)
	})
	List<FcsPagoColegiado> selectPagosColegiadoDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas);
	
	
	
}
