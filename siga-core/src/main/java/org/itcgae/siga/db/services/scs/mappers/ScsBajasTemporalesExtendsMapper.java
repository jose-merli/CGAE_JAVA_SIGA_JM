package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.CenBajastemporalesMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsBajasTemporalesSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionesTurnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoEJGSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

public interface ScsBajasTemporalesExtendsMapper extends CenBajastemporalesMapper{
	
	 @SelectProvider(type=ScsBajasTemporalesSqlExtendsProvider.class, method="busquedaBajasTemporales")
		 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
				@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
				@Result(column = "FECHABT", property = "fechabt", jdbcType = JdbcType.TIMESTAMP, id = true),
				@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
				@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "VALIDADO", property = "validado", jdbcType = JdbcType.CHAR),
				@Result(column = "ELIMINADO", property = "eliminado", jdbcType = JdbcType.DECIMAL),
				@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP) })
	    List<BajasTemporalesItem> busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem,Short idInstitucion, Integer tamMaximo);
	 

	@SelectProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "comboEstado")
		@Results({
			@Result(column = "IDESTADO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		})
	
		List<ComboItem> comboEstado();
	
	
	@SelectProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "checkNifColegiado")
	@Results({
		@Result(column = "IDPERSONA", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> checkNifColegiado(String nif, Short idInstitucion);
	@UpdateProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "deleteBajasTemporales")
	int eliminarBaja(BajasTemporalesItem bajasTemporalesItem, Integer usuario);
	
	@UpdateProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "updateBajasTemporales")
	int updateBajaTemporal(BajasTemporalesItem bajasTemporalesItem, Integer usuario);
	
	@UpdateProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "saveBajaTemporal")
	int saveBajaTemporal(BajasTemporalesItem bajasTemporalesItem, Integer usuario);
	
	@InsertProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "nuevaBajaTemporal")
	int nuevaBaja(BajasTemporalesItem bajasTemporalesItem, Integer usuario);

	@SelectProvider(type = ScsBajasTemporalesSqlExtendsProvider.class, method = "idPersona")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
	})

	String persona(BajasTemporalesItem bajasTemporalesItem);
		
}
