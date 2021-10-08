package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.db.mappers.FacBancoinstitucionMapper;
import org.itcgae.siga.db.services.fac.providers.FacBancoinstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacBancoinstitucionExtendsMapper extends FacBancoinstitucionMapper {
	
	@SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "getCuentasBancarias")
	@Results({ 
		@Result(column = "COD_BANCO", property = "codBanco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COD_SUCURSAL", property = "codSucursal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "IBAN", property = "IBAN", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SJCS", property = "sjcs", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMISIONDESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMISIONIMPORTE", property = "comisionImporte", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUM_USOS", property = "numUsos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUM_FICHEROS", property = "numFicheros", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR)}) 
	List<CuentasBancariasItem> getCuentasBancarias(Short idInstitucion);
	
}
