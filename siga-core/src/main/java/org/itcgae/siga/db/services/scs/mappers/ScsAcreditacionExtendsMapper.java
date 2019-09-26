package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.AcreditacionItem;
import org.itcgae.siga.DTO.scs.ModulosItem;
import org.itcgae.siga.DTO.scs.ProcedimientoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsAcreditacionMapper;
import org.itcgae.siga.db.mappers.ScsProcedimientosMapper;
import org.itcgae.siga.db.services.cen.providers.ScsJurisdiccionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsAcreditacionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsAreasMateriasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsAcreditacionExtendsMapper extends ScsAcreditacionMapper{

	@SelectProvider(type = ScsAcreditacionSqlExtendsProvider.class, method = "searchAcreditaciones")
	@Results({
		@Result(column = "IDACREDITACION", property = "idAcreditacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIG_NUMPROCEDIMIENTO", property = "nig_numprocedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORCENTAJE", property = "porcentaje", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODSUBTARIFA", property = "codSubTarifa", jdbcType = JdbcType.VARCHAR)
	})
	List<AcreditacionItem> searchAcreditaciones(ModulosItem modulosItem);

	@SelectProvider(type = ScsAcreditacionSqlExtendsProvider.class, method = "getAcreditaciones")
	@Results({
		@Result(column = "IDACREDITACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getAcreditaciones(String idInstitucion, String idProcedimiento);
	
	
}
