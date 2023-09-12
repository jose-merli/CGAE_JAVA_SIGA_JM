package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.services.com.providers.ConClaseComunicacionesExtendsSqlProvider;
import org.itcgae.siga.db.services.com.providers.EcomColaExtendsSqlProvider;

public interface EcomColaExtendsMapper extends EcomColaMapper {
	
	@SelectProvider(type = EcomColaExtendsSqlProvider.class, method = "getNewId")
	@Results({
        @Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getNewId();
	
	@Select({
        "select",
        "Nvl(Max(IDECOMCOLA), 0) as IDECOMCOLA",
        "from ECOM_COLA"
    })
    @Results({
        @Result(column="IDECOMCOLA", property="idecomcola", jdbcType=JdbcType.DECIMAL, id=true)
    })
    Integer selectLastIdEcomCola();

}
