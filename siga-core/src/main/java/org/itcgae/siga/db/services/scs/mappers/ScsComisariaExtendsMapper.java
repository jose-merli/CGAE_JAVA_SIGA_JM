package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ComisariaItem;
import org.itcgae.siga.db.mappers.ScsComisariaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsComisariaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsComisariaExtendsMapper extends ScsComisariaMapper{

	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "searchComisarias")
	@Results({
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOMISARIA", property = "idComisaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLEMOVIL", property = "visibleMovil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),

	})
	List<ComisariaItem> searchComisarias(ComisariaItem comisariaItem, Short idInstitucion);

	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "getIdComisaria")
	@Results({ @Result(column = "IDCOMISARIA", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdComisaria(Short idInstitucion);
	
	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "comboComisaria")
	@Results({
		@Result(column = "IDCOMISARIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboComisaria(Short idLenguaje, Short idInstitucion);

	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "comboCDetencion")
	@Results({
		@Result(column = "IDCOMISARIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cdetencion", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboCDetenciones(Short idInstitucion);
	
	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "getComisariasByIdTurno")
	@Results({
		@Result(column = "idcomisaria", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getComisariasByIdTurno(Short idInstitucion, String idTurno);
	
	@SelectProvider(type = ScsComisariaSqlExtendsProvider.class, method = "comboComisariaCdgoExt")
	@Results({
		@Result(column = "CODIGOEXT", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboComisariaCdgoExt(Short idLenguaje, Short idInstitucion);
	
}
