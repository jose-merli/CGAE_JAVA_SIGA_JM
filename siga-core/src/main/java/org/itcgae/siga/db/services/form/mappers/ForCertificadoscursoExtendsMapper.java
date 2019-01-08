package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.CertificadoCursoItem;
import org.itcgae.siga.db.mappers.ForCertificadoscursoMapper;
import org.itcgae.siga.db.services.form.providers.ForCertificadoscursoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForCertificadoscursoExtendsMapper extends ForCertificadoscursoMapper {

	@SelectProvider(type = ForCertificadoscursoSqlExtendsProvider.class, method = "getCertificatesCourse")
	@Results({ @Result(column = "IDCERTIFICADOCURSO", property = "idCertificadoCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCURSO", property = "idCurso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRODUCTO", property = "idProducto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOPRODUCTO", property = "idTipoProducto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRODUCTOINSTITUCION", property = "idProductoInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCALIFICACION", property = "idCalificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIO", property = "precio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CALIFICACION", property = "calificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECERTIFICADO", property = "nombreCertificado", jdbcType = JdbcType.VARCHAR) })
	List<CertificadoCursoItem> getCertificatesCourse(String idCurso, String idInstitucion, String idLenguaje);

}
