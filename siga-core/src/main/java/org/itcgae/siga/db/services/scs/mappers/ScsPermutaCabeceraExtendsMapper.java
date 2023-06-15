package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.entities.ScsPermutaCabeceraExample;
import org.itcgae.siga.db.mappers.ScsPermutaCabeceraMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPermutaCabeceraSqlExtendsProvider;

public interface ScsPermutaCabeceraExtendsMapper extends ScsPermutaCabeceraMapper{
	 @SelectProvider(type=ScsPermutaCabeceraSqlExtendsProvider.class, method="maxIdPermutaCabecera")
	    String maxIdPermutaCabecera(String idinstitucion);
}
