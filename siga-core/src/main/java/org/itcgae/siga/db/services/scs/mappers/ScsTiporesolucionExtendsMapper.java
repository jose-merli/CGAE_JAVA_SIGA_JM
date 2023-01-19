package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.ScsTiporesolucion;
import org.itcgae.siga.db.mappers.ScsJuzgadoMapper;
import org.itcgae.siga.db.mappers.ScsTiporesolucionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsJuzgadoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipofundamentosSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTiporesolucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTiporesolucionExtendsMapper extends ScsTiporesolucionMapper{

	@SelectProvider(type = ScsTiporesolucionSqlExtendsProvider.class, method = "getResoluciones")
	@Results({
		@Result(column = "IDTIPORESOLUCION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getResoluciones(String idLenguaje, String origen);
	
	@SelectProvider(type = ScsTiporesolucionSqlExtendsProvider.class, method = "getResoluciones")
	@Results({
		@Result(column = "IDTIPORESOLUCION", property = "idtiporesolucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsTiporesolucion> getResolucionesEJG(String idLenguaje);
	

}
