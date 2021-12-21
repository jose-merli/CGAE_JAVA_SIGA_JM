package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.db.entities.FacFacturaDevolucion;
import org.itcgae.siga.db.mappers.FacHistoricofacturaMapper;
import org.itcgae.siga.db.services.fac.providers.FacHistoricofacturaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacHistoricofacturaExtendsMapper extends FacHistoricofacturaMapper {

	@SelectProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "getEstadosPagos")
	@Results({
			@Result(column = "FECHAMODIFICACION", property = "fechaModificaion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOACCION", property = "idAccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACCION", property = "accion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADO", property = "impTotalPagado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPORPAGAR", property = "impTotalPorPagar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSJCS", property = "IDSJCS", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENLACEFACTURA", property = "enlaceFactura", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCARGOS", property = "idCargos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDDEVOLUCIONES", property = "idDevoluciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENLACEABONO", property = "enlaceAbono", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "NUMEROABONO", property = "numeroAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDABONO", property = "idAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOTAACCION", property = "notaAccion", jdbcType = JdbcType.VARCHAR)
	})
	List<EstadosPagosItem> getEstadosPagos(String idFactura, String idInstitucion, String idLenguaje);

	@Update(value = "{CALL PKG_SIGA_CARGOS.DevolucionesManuales ("
			+ "#{idInstitucion, mode=IN},"
			+ "#{listaFacturas, mode=IN, jdbcType=VARCHAR},"
			+ "#{fechaDevolucion, mode=IN, jdbcType=VARCHAR},"
			+ "#{idIdioma, mode=IN},"
			+ "#{usuModificacion, mode=IN},"
			+ "#{codretorno, mode=OUT, jdbcType=VARCHAR},"
			+ "#{datoserror, mode=OUT, jdbcType=VARCHAR},"
			+ "#{listaIdDisquetesDevolucion, mode=OUT, jdbcType=VARCHAR})}")
	@Options(statementType = StatementType.CALLABLE)
	@ResultType(FacFacturaDevolucion.class)
	void devolucionesManuales(FacFacturaDevolucion item);
}
