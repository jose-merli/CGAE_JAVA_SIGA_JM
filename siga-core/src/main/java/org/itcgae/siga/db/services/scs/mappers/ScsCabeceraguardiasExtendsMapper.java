package org.itcgae.siga.db.services.scs.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsCabeceraguardiasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsCabeceraguardiasExtendsMapper extends ScsCabeceraguardiasMapper {

	

	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "getCabeceraGuardiasDeVariasPersonas")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsCabeceraguardias> getCabeceraGuardiasDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas);
	
	
	
}
