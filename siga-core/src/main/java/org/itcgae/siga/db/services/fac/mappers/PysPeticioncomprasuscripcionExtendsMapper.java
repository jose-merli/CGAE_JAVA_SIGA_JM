package org.itcgae.siga.db.services.fac.mappers;

import java.util.Date;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.services.fac.providers.PysPeticioncomprasuscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPeticioncomprasuscripcionExtendsMapper extends PysPeticioncomprasuscripcionMapper{

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getFichaCompraSuscripcion")
	@Results({ 
			// TARJETA CLIENTE
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoidentificacion", property = "idtipoidentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "nif", jdbcType = JdbcType.VARCHAR), 

			// TARJETA SOLICITUD
			@Result(column = "idpeticion", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "usuModificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechafechaPendiente", property = "fechafechaPendiente", jdbcType = JdbcType.DATE),
			@Result(column = "fechafechaAceptada", property = "fechafechaAceptada", jdbcType = JdbcType.DATE),
			@Result(column = "fechaDenegada", property = "fechaDenegada", jdbcType = JdbcType.DATE),
			@Result(column = "fechaSolicitadaAnulacion", property = "fechaSolicitadaAnulacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechaAnulada", property = "fechaAnulada", jdbcType = JdbcType.DATE),
			//TARJETA FORMAS DE PAGO
			@Result(column = "idformaspagocomunes", property = "idFormasPagoComunes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idFormaPagoSeleccionada", property = "idFormaPagoSeleccionada", jdbcType = JdbcType.VARCHAR)})
	FichaCompraSuscripcionItem getFichaCompraSuscripcion(FichaCompraSuscripcionItem ficha, boolean esColegiado);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getNuevaFichaCompraSuscripcion")
	@Results({ 
			// TARJETA CLIENTE
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoidentificacion", property = "idtipoidentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "nif", jdbcType = JdbcType.VARCHAR), 
			//TARJETA SOLICITUD
			@Result(column = "idpeticion", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "usuModificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			//TARJETA FORMAS DE PAGO
			@Result(column = "idformaspagocomunes", property = "idFormasPagoComunes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "totalNeto", property = "totalNeto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "totalIVA", property = "totalIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "impTotal", property = "impTotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pendPago", property = "pendPago", jdbcType = JdbcType.VARCHAR)})
	FichaCompraSuscripcionItem getNuevaFichaCompraSuscripcion(FichaCompraSuscripcionItem ficha, boolean esColegiado);

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectMaxIdPeticion")
	@Results({
		@Result(column = "IDPETICION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPeticion(Short idInstitucion);

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectMaxNumOperacion")
	@Results({
		@Result(column = "NUM_OPERACION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxNumOperacion(Short idInstitucion);
}
