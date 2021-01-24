package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.CertificadoItem;
import org.itcgae.siga.db.mappers.CerSolicitudcertificadosMapper;
import org.itcgae.siga.db.services.cen.providers.CenDatosCertificadosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface CenDatosCertificadosExtendsMapper extends CerSolicitudcertificadosMapper {

	
	@SelectProvider(type = CenDatosCertificadosSqlExtendsProvider.class, method = "datosCertificadosSearch")
	@Results({ @Result(column = "IDSOLICITUD", property = "idSolicitud", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAEMISION", property = "fechaEmision", jdbcType = JdbcType.VARCHAR)
	})
	List<CertificadoItem> datosCertificadosSearch(String idPersona,Short idInstitucion);
	
}
