package org.itcgae.siga.db.services.cajg.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CajgEjgremesaMapper;
import org.itcgae.siga.db.services.cajg.providers.CajgEjgremesaSqlExtendsProvider;
import org.itcgae.siga.db.services.cajg.providers.CajgRemesaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;

public interface CajgEjgremesaExtendsMapper extends CajgEjgremesaMapper{
	
	@SelectProvider(type = CajgEjgremesaSqlExtendsProvider.class, method = "getIdEjgRemesa")
	String getIdEjgRemesa();
	
	@SelectProvider(type = CajgEjgremesaSqlExtendsProvider.class, method = "getNumeroIntercambio")
	String getNumeroIntercambio(Short idInstitucion);

}
