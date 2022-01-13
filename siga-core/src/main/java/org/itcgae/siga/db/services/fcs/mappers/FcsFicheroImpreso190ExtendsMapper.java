package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.db.mappers.FcsFicheroImpreso190Mapper;
import org.itcgae.siga.db.services.fcs.providers.FcsFicheroImpreso190SqlExtendsProviders;
import org.springframework.stereotype.Service;

@Service
public interface FcsFicheroImpreso190ExtendsMapper extends FcsFicheroImpreso190Mapper{
	@SelectProvider(type = FcsFicheroImpreso190SqlExtendsProviders.class, method = "getImpreso190")
    @Results({@Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMFICHERO", property = "nomFichero", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "TELEFONOCONTACTO", property = "telefonoContacto", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBRECONTACTO", property = "nombreContacto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO1CONTACTO", property = "apellido1Contacto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO2CONTACTO", property = "apellido2Contacto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAGENERARION", property = "fechageneracion", jdbcType = JdbcType.VARCHAR),
       })
    List<Impreso190Item> getImpreso190(String[] anio, Short idInstitucion);
	
	@SelectProvider(type = FcsFicheroImpreso190SqlExtendsProviders.class, method = "getConfImpreso190")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREFICHERO", property = "nomFichero", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "TELEFONO", property = "telefonoContacto", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBRE", property = "nombreContacto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO1", property = "apellido1Contacto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDO2", property = "apellido2Contacto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAMODIFICACION", property = "fechageneracion", jdbcType = JdbcType.VARCHAR),
       })
    List<Impreso190Item> getConfImpreso190(Short idInstitucion);
	
	@SelectProvider(type = FcsFicheroImpreso190SqlExtendsProviders.class, method = "getComboAnio")
	@Results({ 
		@Result(column = "ANIO", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ANIO2", property = "label", jdbcType = JdbcType.NUMERIC)
	})
	List<ComboItem> getComboAnio(Short idInstitucion);

}
