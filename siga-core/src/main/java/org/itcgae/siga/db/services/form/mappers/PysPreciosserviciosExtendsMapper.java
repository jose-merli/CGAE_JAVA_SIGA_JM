package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.ComboPreciosSuscripcionItem;
import org.itcgae.siga.DTO.fac.FichaTarjetaPreciosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTOs.form.PreciosCursoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.PysPreciosservicios;
import org.itcgae.siga.db.entities.PysPreciosserviciosKey;
import org.itcgae.siga.db.mappers.PysPreciosserviciosMapper;
import org.itcgae.siga.db.services.fac.providers.PySTipoFormaPagoSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysPreciosserviciosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPreciosserviciosExtendsMapper extends PysPreciosserviciosMapper{

	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "selectMaxIdPrecioServicio")
	@Results({
		@Result(column = "IDPRECIOSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPrecioServicio(Short idInstitucion, short idTipoServicio, Long idServicio, Long idServicioInstitucion, Short idPeriocidad);
	
	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "selectPricesCourse")
	@Results({
		@Result(column = "PERIOCIDAD", property = "periocidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORDEFECTO", property = "porDefecto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<PreciosCursoItem> selectPricesCourse(Short idInstitucion, Long idServicio, String idLenguaje, String codigoCurso);
	
	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "detalleTarjetaPrecios")
	@Results({
		@Result(column = "idpreciosservicios", property = "idpreciosservicios", jdbcType = JdbcType.INTEGER),
		@Result(column = "idserviciosinstitucion", property = "idserviciosinstitucion", jdbcType = JdbcType.INTEGER),
		@Result(column = "idtiposervicios", property = "idtiposervicios", jdbcType = JdbcType.INTEGER),
		@Result(column = "idservicio", property = "idservicio", jdbcType = JdbcType.INTEGER),
		@Result(column = "precio", property = "valor", jdbcType = JdbcType.DOUBLE),
		@Result(column = "periodicidad", property = "idperiodicidad", jdbcType = JdbcType.INTEGER),
		@Result(column = "periodicidadValor", property = "periodicidadValor", jdbcType = JdbcType.INTEGER),
		@Result(column = "descripcionprecio", property = "descripcionprecio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "condicion", property = "idcondicion", jdbcType = JdbcType.INTEGER),
		@Result(column = "descripcionperiodicidad", property = "descripcionperiodicidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcionconsulta", property = "descripcionconsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "pordefecto", property = "pordefecto", jdbcType = JdbcType.VARCHAR)
	})
	List<FichaTarjetaPreciosItem> detalleTarjetaPrecios(int idTipoServicio, int idServicio, int idServiciosInstitucion, Short idInstitucion, String idioma);
	
	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "comboPeriodicidad")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboPeriodicidad(String idioma);
	
	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "comboPreciosServPers")
	@Results({ 
		@Result(column = "idpreciosservicios", property = "idpreciosservicios", jdbcType = JdbcType.INTEGER),
		@Result(column = "idserviciosinstitucion", property = "idserviciosinstitucion", jdbcType = JdbcType.INTEGER),
		@Result(column = "idtiposervicios", property = "idtiposervicios", jdbcType = JdbcType.INTEGER),
		@Result(column = "idservicio", property = "idservicio", jdbcType = JdbcType.INTEGER),
		@Result(column = "precio", property = "precio", jdbcType = JdbcType.DOUBLE),
		@Result(column = "periodicidad", property = "idperiodicidad", jdbcType = JdbcType.INTEGER),
		@Result(column = "periodicidadValor", property = "periodicidadValor", jdbcType = JdbcType.INTEGER),
		@Result(column = "descripcionprecio", property = "descripcionprecio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "condicion", property = "idcondicion", jdbcType = JdbcType.INTEGER),
		@Result(column = "descripcionperiodicidad", property = "descripcionperiodicidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcionconsulta", property = "descripcionconsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "pordefecto", property = "pordefecto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "valido", property = "valido", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboPreciosSuscripcionItem> comboPreciosServPers(String idioma, Short idInstitucion, String idPersona, String idServicio, String idTipoServicios, String idServiciosInstitucion);
	
	//Realiza un borrado fisico de los precios de un servicio
	@UpdateProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "borradoFisicoPreciosByServicio")
	int borradoFisicoPreciosByServicio(ListaServiciosItem servicio,Short idInstitucion);
	
	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "checkCriterioPrecio")
    String checkCriterioPrecio(String criterio);
}
