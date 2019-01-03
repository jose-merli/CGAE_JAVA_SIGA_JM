package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.CargaMasivaInscripcionesItem;
import org.itcgae.siga.db.mappers.ForInscripcionesmasivasMapper;
import org.itcgae.siga.db.services.form.providers.ForInscripcionesmasivasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForInscripcionesmasivasExtendsMapper extends ForInscripcionesmasivasMapper{

	@SelectProvider(type = ForInscripcionesmasivasSqlExtendsProvider.class, method = "getMassiveLoadInscriptions")
	@Results({ @Result(column = "IDCARGAINSCRIPCION", property = "idCargaInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACARGA", property = "fechaCarga", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROLINEASTOTALES", property = "numeroLineasTotales", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INSCRIPCIONESCORRECTAS", property = "inscripcionesCorrectas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.VARCHAR),
	})
	List<CargaMasivaInscripcionesItem> getMassiveLoadInscriptions(String idCurso, String idInstitucion);	

}
