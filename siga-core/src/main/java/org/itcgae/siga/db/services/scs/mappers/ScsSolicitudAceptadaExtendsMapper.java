package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.PreAsistenciaItem;
import org.itcgae.siga.db.mappers.ScsSolicitudAceptadaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsSolicitudAceptadaSqlExtendsProvider;

public interface ScsSolicitudAceptadaExtendsMapper extends ScsSolicitudAceptadaMapper {

	
	 @SelectProvider(type = ScsSolicitudAceptadaSqlExtendsProvider.class, method = "searchPreasistencias")
	    @Results({ 
	        @Result(column = "NUMAVISOCV", property = "nAvisoCentralita", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "fechaLlamada", property = "fechaLlamada", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "DESCRIPCIONESTADO", property = "descripcionEstado", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "descripcionGuardia", property = "descripcionGuardia", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "nombreColegiado", property = "nombreColegiado", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "NUMEROCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "centroDetencion", property = "centroDetencion", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "IDSOLICITUD", property = "idSolicitud", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "fechaRecepcion", property = "fechaRecepcion", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
	        @Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
	    })
	    List<PreAsistenciaItem> searchPreAsistencias(PreAsistenciaItem preAsistencia, Integer tamMax, Short idInstitucion);
}
