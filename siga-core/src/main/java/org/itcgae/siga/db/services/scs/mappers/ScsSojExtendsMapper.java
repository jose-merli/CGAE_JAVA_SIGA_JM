package org.itcgae.siga.db.services.scs.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosSOJItem;
import org.itcgae.siga.DTOs.scs.DocumentacionSojItem;
import org.itcgae.siga.DTOs.scs.FichaSojItem;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.mappers.ScsSojMapper;
import org.itcgae.siga.db.services.scs.providers.ScsSojSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsSojExtendsMapper extends ScsSojMapper{

	@SelectProvider(type = ScsSojSqlExtendsProvider.class, method = "searchClaveSoj")
	@Results({  @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		//@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAAPERTURA", property = "fecha", jdbcType = JdbcType.DATE),
		//@Result(column = "NUMEROPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
		//@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOSOJ", property = "idTipoSoj", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSOJ", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)
		
	})
	List<AsuntosJusticiableItem> searchClaveSoj(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo, String idLenguaje);
	
	@SelectProvider(type = ScsSojSqlExtendsProvider.class, method = "getAsuntoTipoSoj")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DATOSINTERES", property = "datosInteres", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONASOJ", property = "idPersonaSoj", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONACOLEGIADO", property = "idPersonaColegiado", jdbcType = JdbcType.VARCHAR)
	})
	AsuntosSOJItem getAsuntoTipoSoj(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
	
	@UpdateProvider(type=ScsSojSqlExtendsProvider.class, method="eliminarRelacionSoj")
    int eliminarRelacionSoj(String idinstitucion, String anio, String numero, String tipoSoj);

	@SelectProvider(type = ScsSojSqlExtendsProvider.class, method = "busquedaSoj")
	@Results({
			@Result(column = "IDTIPOSOJ", property = "idTipoSoj", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAAPERTURA", property = "fechaApertura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAAPERTURA", property = "fechaApertura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONCONSULTA", property = "descripcionConsulta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESPUESTALETRADO", property = "respuestaLetrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOSOJCOLEGIO", property = "idTipoSojColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAJG", property = "idPersonaJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FACTURADO", property = "facturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PAGADO", property = "pagado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMSOJ", property = "numSoj", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOCONSULTA", property = "idTipoConsulta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPORESPUESTA", property = "idTipoRespuesta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejgIdTipoEjg", property = "ejgIdTipoEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejgAnio", property = "ejgAnio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejgNumero", property = "ejgNumero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sufijo", property = "sufijo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ncolegiado", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreAp", property = "nombreAp", jdbcType = JdbcType.VARCHAR),
	})
	List<FichaSojItem> busquedaSoj(FichaSojItem fichaSojItem);
	
	
	@UpdateProvider(type=ScsSojSqlExtendsProvider.class, method="asociarSOJ")
    int asociarSOJ(ScsSoj fichaSojItem);
	
	@InsertProvider(type=ScsSojSqlExtendsProvider.class, method="subirDocumentoSOJ")
    InsertResponseDTO subirDocumentoSOJ(DocumentacionSojItem documentacion,Short idInstitucion,String fechaActualizada);
	
	@SelectProvider(type = ScsSojSqlExtendsProvider.class, method = "busquedaDocumentosSOJ")
	@Results({
		    @Result(column = "IDDOCUMENTACION", property = "idDocumentacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHALIMITE", property = "fechaLimite", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAENTREGA", property = "fechaPresentacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REGENTRADA", property = "registroEntrada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REGSALIDA", property = "registroSalida", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOCUMENTACION", property = "documentacion", jdbcType = JdbcType.VARCHAR),
	})
	List<DocumentacionSojItem> busquedaDocumentosSOJ(FichaSojItem fichaSojItem);
}
