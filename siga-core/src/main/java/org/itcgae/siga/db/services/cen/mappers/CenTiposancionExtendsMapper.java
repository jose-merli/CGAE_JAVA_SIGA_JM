package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesDTO;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTiposancionMapper;
import org.itcgae.siga.db.services.cen.providers.CenNocolegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTiposancionExtendsMapper extends CenTiposancionMapper{

	@SelectProvider(type = CenTiposancionSqlExtendsProvider.class, method = "getComboTipoSancion")
	@Results({ @Result(column = "IDTIPOSANCION", property = "value", jdbcType = JdbcType.VARCHAR),
		 		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
			})
	List<ComboItem> getComboTipoSancion(); 
		
	@SelectProvider(type = CenTiposancionSqlExtendsProvider.class, method = "searchBusquedaSanciones")
	@Results({
		@Result(column = "COLEGIO", property = "colegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSANCION", property = "tipoSancion", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REFCONSEJO", property = "refConsejo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REFCOLEGIO", property = "refColegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIN", property = "fechaHasta", jdbcType = JdbcType.DATE),
		@Result(column = "REHABILITADO", property = "rehabilitado", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "FIRMEZA", property = "firmeza", jdbcType = JdbcType.BOOLEAN)
	})
	List<BusquedaSancionesItem> searchBusquedaSanciones(BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, String idLenguaje, String idInstitucion);
}
