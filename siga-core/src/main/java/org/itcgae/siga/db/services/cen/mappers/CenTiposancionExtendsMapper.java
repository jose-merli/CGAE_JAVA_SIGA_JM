package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesItem;
import org.itcgae.siga.DTOs.cen.BusquedaSancionesSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTiposancionMapper;
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
		@Result(column = "IDCOLEGIO", property = "idColegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucionS", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSANCION", property = "idSancion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTODATE", property = "fechaNacimientoDate", jdbcType = JdbcType.DATE),
		@Result(column = "TIPOSANCION", property = "tipoSancion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REFCOLEGIO", property = "refColegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIODATE", property = "fechaDesdeDate", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIN", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFINDATE", property = "fechaHastaDate", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIRMEZA", property = "fechaFirmeza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIRMEZADATE", property = "fechaFirmezaDate", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAACUERDO", property = "fechaAcuerdo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAACUERDODATE", property = "fechaAcuerdoDate", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAREHABILITADO", property = "fechaRehabilitado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAREHABILITADODATE", property = "fechaRehabilitadoDate", jdbcType = JdbcType.DATE),
		@Result(column = "REHABILITADO", property = "rehabilitado", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "FIRMEZA", property = "firmeza", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "ARCHIVADA", property = "archivada", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "TEXTO", property = "texto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUM_EXPEDIENTE", property = "numExpediente", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaSancionesItem> searchBusquedaSanciones(BusquedaSancionesSearchDTO busquedaSancionesSearchDTO, String idLenguaje, String idInstitucion);
}
