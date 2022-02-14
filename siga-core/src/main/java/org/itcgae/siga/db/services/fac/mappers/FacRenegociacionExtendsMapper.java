package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.mappers.FacRenegociacionMapper;
import org.itcgae.siga.db.services.fac.providers.FacRenegociacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacRenegociacionExtendsMapper extends FacRenegociacionMapper {

	@SelectProvider(type = FacRenegociacionExtendsSqlProvider.class, method = "getNuevoID")
	Short getNuevoID(Short idInstitucion, String idFactura);

}
