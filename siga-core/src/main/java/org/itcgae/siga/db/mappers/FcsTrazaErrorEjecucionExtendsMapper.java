package org.itcgae.siga.db.mappers;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsTrazaErrorEjecucionExtendsMapper {
	
	 @SelectProvider(type = FcsTrazaErrorEjecucionSqlExtendsProvider.class, method = "selectSQ")
	    int selectSQ();
	
}
