package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface AdmUsuarioEfectivoExtendsMapper extends AdmUsuarioEfectivoMapper {

	@SelectProvider(type = AdmUsuarioEfectivoSqlExtendsProvider.class, method = "isLetrado")
	int[] isLetrado(AdmUsuarios usuario);
}