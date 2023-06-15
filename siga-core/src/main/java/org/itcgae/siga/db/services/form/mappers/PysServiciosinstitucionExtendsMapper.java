package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysServiciosinstitucionMapper;
import org.itcgae.siga.db.services.fac.providers.PySTiposServiciosSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysProductosinstitucionSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysServiciosinstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysServiciosinstitucionExtendsMapper extends PysServiciosinstitucionMapper{

	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "selectMaxIdServicioinstitucion")
	@Results({
		@Result(column = "IDSERVICIOINSTITUCION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdServicioinstitucion(Short idInstitucion, Long idServicio);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "selectIdServicioinstitucionByIdServicio")
	@Results({
		@Result(column = "IDSERVICIOSINSTITUCION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectIdServicioinstitucionByIdServicio(Short idInstitucion, Long idServicio);

	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "existsDescription")
	long existsDescription(Long idServicio, Long idServiciosInstitucion, Short idTipoServicios, Short idInstitucion, String descripcion);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "getIndiceMaxServicio")
	@Results({ 
		@Result(column = "IDSERVICIOSINSTITUCION", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxProducto(ServicioDetalleDTO servicio, Short idInstitucion);

	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "detalleServicio")
	@Results({
		@Result(column = "IDTIPOSERVICIOS", property = "idtiposervicios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDSERVICIO", property = "idservicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDSERVICIOSINSTITUCION", property = "idserviciosinstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITARBAJA", property = "permitirbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITARALTA", property = "permitiralta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTACONTABLE", property = "cuentacontable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "NOFACTURABLE", property = "nofacturable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOIVA", property = "idtipoiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CATEGORIA", property = "categoria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALORIVA", property = "valoriva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "AUTOMATICO", property = "automatico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "idconsulta", jdbcType = JdbcType.NUMERIC),
		@Result(column = "INICIOFINALPONDERADO", property = "iniciofinalponderado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FACTURACIONPONDERADA", property = "facturacionponderada", jdbcType = JdbcType.VARCHAR)
		}) 
	ServicioDetalleDTO detalleServicio(int idTipoServicio, int idServicio, int idServiciosInstitucion, Short idInstitucion);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "obtenerFormasDePagoInternetByServicio")
	List<Integer> obtenerFormasDePagoInternetByServicio(int idTipoServicio, int idServicio, int idServicioInstitucion, Short idInstitucion);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "obtenerFormasDePagoSecretariaByServicio")
	List<Integer> obtenerFormasDePagoSecretariaByServicio(int idTipoServicio, int idServicio, int idServicioInstitucion, Short idInstitucion);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "comboCondicionSuscripcion")
	@Results({ 
		@Result(column = "IDCONSULTA", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboCondicionSuscripcion(String idioma ,Short idInstitucion);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "getCriterioByIdConsulta")
	String getCriterioByIdConsulta(Short idInstitucion, int idConsulta);

	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "getIdServicioInstitucion")
	@Results({ 
		@Result(column = "IDSERVICIOSINSTITUCION", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIdServicioInstitucion(ServicioDetalleDTO servicio, Short idInstitucion);
}

