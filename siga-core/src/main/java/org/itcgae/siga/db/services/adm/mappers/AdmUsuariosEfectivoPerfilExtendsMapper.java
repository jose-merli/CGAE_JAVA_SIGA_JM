package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.AdmUsuariosEfectivosPerfilMapper;
import org.itcgae.siga.db.services.adm.providers.AdmUsuariosEfectivosPerfilSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AdmUsuariosEfectivoPerfilExtendsMapper extends AdmUsuariosEfectivosPerfilMapper{

	
	@SelectProvider(type = AdmUsuariosEfectivosPerfilSqlExtendsProvider.class, method = "getPerfilesUsuario")
	@Results({
		@Result(column = "PERFIL1", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PERFIL2", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getPerfilesUsuario(UsuarioUpdateDTO usuarioUpdateDTO, String idInstitucion);
}
