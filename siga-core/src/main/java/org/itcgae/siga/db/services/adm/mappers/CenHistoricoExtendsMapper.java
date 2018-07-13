package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioItem;
import org.itcgae.siga.DTOs.adm.HistoricoUsuarioRequestDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenHistoricoMapper;
import org.itcgae.siga.db.services.adm.providers.CenHistoricoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenHistoricoExtendsMapper extends CenHistoricoMapper{

		
	@SelectProvider(type = CenHistoricoSqlExtendsProvider.class, method = "auditUsersSearch")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDHISTORICO", property = "idHistorico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAEFECTIVA", property = "fechaEfectiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAENTRADA", property = "fechaEntrada", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCAMBIO", property = "idTipoCambio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONTIPOCAMBIO", property = "descTipoCambio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONUSUARIO", property = "descripcionUsuario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PERSONA", property = "persona", jdbcType = JdbcType.VARCHAR)
	})
	List<HistoricoUsuarioItem> auditUsersSearch(int numPagina, HistoricoUsuarioRequestDTO historicoUsuarioRequestDTO);
	
	
	
	@SelectProvider(type = CenHistoricoSqlExtendsProvider.class, method = "selectMaxIDHistoricoByPerson")
	@Results({
		@Result(column = "IDHISTORICO", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDHistoricoByPerson(String idPersona, String idInstitucion);
	
}
