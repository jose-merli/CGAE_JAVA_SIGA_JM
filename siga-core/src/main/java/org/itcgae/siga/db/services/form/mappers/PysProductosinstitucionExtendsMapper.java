package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.CertificadoCursoItem;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.services.form.providers.PysProductosinstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysProductosinstitucionExtendsMapper extends PysProductosinstitucionMapper{

	@SelectProvider(type = PysProductosinstitucionSqlExtendsProvider.class, method = "selectTypesCertificatesCourse")
	@Results({
		@Result(column = "IDTIPOPRODUCTO", property = "idTipoProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTO", property = "idProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPRODUCTOINSTITUCION", property = "idProductoInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "precio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR)

	})
	List<CertificadoCursoItem> selectTypesCertificatesCourse(Short idInstitucion);


}
