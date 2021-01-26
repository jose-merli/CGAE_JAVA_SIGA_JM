package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDocumentacionejgExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDocumentacionejgExtendsMapper extends ScsDocumentacionejgMapper{
	@SelectProvider(type = ScsDocumentacionejgExtendsProvider.class, method = "getDocumentacion")
	@Results({ 
		@Result(column = "fechalimite", property = "flimite_presentacion", jdbcType = JdbcType.DATE),
		@Result(column = "presentadores", property = "presentador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombrecompleto", property = "presentador_persona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "parentesco", property = "parentesco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "documentacion", property = "documentoDesc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "regentrada", property = "regEntrada", jdbcType = JdbcType.VARCHAR),
		@Result(column = "regsalida", property = "regSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaentrega", property = "f_presentacion", jdbcType = JdbcType.DATE),
		@Result(column = "comisionajg", property = "propietario", jdbcType = JdbcType.VARCHAR),

	})
	List<EjgDocumentacionItem> getDocumentacion(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje);
}