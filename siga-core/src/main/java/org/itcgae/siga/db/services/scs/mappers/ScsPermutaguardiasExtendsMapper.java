package org.itcgae.siga.db.services.scs.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ComboGuardiasFuturasItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.DTOs.scs.PermutaItem2;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPermutaCabeceraSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPermutaguardiasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPermutaguardiasExtendsMapper extends ScsPermutaguardiasMapper {
	
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getPermutaColeg")
	@Results({ 
		@Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOPERMUTA", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIADESTINO", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona_solicitante", property = "idpersonaSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona_confirmador", property = "idpersonaConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno_solicitante", property = "idturnoSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idcalendarioguardias_solicitan", property = "idcalendarioguardiasSolicitan", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idguardia_solicitante", property = "idguardiaSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno_confirmador", property = "idturnoConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idcalendarioguardias_confirmad", property = "idcalendarioguardiasConfirmad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idguardia_confirmador", property = "idguardiaConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechainicio_solicitante", property = "fechainicioSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechainicio_confirmador", property = "fechainicioConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "motivosconfirmador", property = "motivosconfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "motivossolicitante", property = "motivossolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_per_cab_solicitante", property = "idPerCabSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_per_cab_confirmador", property = "idPerCabConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idcalendarioguardias", property = "idcalendarioguardias", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno", property = "idturno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idguardia", property = "idguardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.VARCHAR),
		

	})
	List<PermutaItem> getPermutaColeg(PermutaItem permutaItem, Short idInstitucion);
	
	//SIGARNV-2885@DTT.JAMARTIN@07/02/2023@INICIO
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getPermutasGuardiaColeg")
	@Results({ 
		@Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOPERMUTA", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIADESTINO", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona_solicitante", property = "idpersonaSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona_confirmador", property = "idpersonaConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno_solicitante", property = "idturnoSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idcalendarioguardias_solicitan", property = "idcalendarioguardiasSolicitan", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idguardia_solicitante", property = "idguardiaSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno_confirmador", property = "idturnoConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idcalendarioguardias_confirmad", property = "idcalendarioguardiasConfirmad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idguardia_confirmador", property = "idguardiaConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechainicio_solicitante", property = "fechainicioSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechainicio_confirmador", property = "fechainicioConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "motivosconfirmador", property = "motivosconfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "motivossolicitante", property = "motivossolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_per_cab_solicitante", property = "idPerCabSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_per_cab_confirmador", property = "idPerCabConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idcalendarioguardias", property = "idcalendarioguardias", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno", property = "idturno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idguardia", property = "idguardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE_PERSONA_CONFIRMADOR", property = "nombreColegiadoConfirmador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE_PERSONA_SOLICITANTE", property = "nombreColegiadoSolicitante", jdbcType = JdbcType.VARCHAR)
	})
	List<PermutaItem2> getPermutasGuardiaColeg(PermutaItem2 permutaItem, Short idInstitucion);
	//SIGARNV-2885@DTT.JAMARTIN@07/02/2023@FIN 
	
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getTurnoInscrito")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),})
	List<ComboItem> getTurnoInscrito(Long idpersona, Short idinstitucion);

	
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getGuardiaDestinoInscrito")
	@Results({ @Result(column = "DATOS", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIA", property = "label", jdbcType = JdbcType.VARCHAR),
			})
	List<ComboGuardiasFuturasItem> getGuardiaDestinoInscrito(GuardiasItem guardiaItem, String idinstitucion);
	
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@INICIO
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getFechaSolicitanteInicio")
	@Results({ @Result(column = "FECHAINICIO_SOLICITANTE", property = "value", jdbcType = JdbcType.DATE)
			})
	List<Date> getFechaSolicitanteInicio(String idPersona, Short idCalendarioGuardias, Short idGuardia, Short idInstitucion);
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@FIN 
	
	@SelectProvider(type=ScsPermutaguardiasSqlExtendsProvider.class, method="maxIdPermutaGuardia")
    String maxIdPermutaGuardia(String idinstitucion);

	@DeleteProvider(type=ScsPermutaguardiasSqlExtendsProvider.class, method="deletePermutasCalendarioSolicitante")
	public boolean deletePermutasCalendarioSolicitante(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

	@DeleteProvider(type=ScsPermutaguardiasSqlExtendsProvider.class, method="deletePermutasCalendarioConfirmador")
	public boolean deletePermutasCalendarioConfirmador(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

}
