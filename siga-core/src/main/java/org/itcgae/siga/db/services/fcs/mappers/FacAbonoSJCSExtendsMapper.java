package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacAbonoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacAbonoSJCSExtendsMapper extends FacAbonoMapper{
	
	/*@SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "buscarAbonos")
	@Results({
		//MAPEAR DATOS CON CAMPOS NECESARIOS
		
	})
	List<FacAbonoItem> buscarAbonos(FacAbonoItem facAbonoItem, String idInstitucion);	
	*/

}
