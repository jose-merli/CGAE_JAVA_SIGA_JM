package org.itcgae.siga.db.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.GestionEconomicaCatalunyaItem;

public interface GestionEconomicaCatalunyaMapper {

	@SelectProvider(type = GestionEconomicaCatalunyaMapperSqlProvider.class, method = "getNombreXmlJustificacion")
	String getNombreXmlJustificacion(@Param("params") Map<String, Object> parametrosMap);
    
	@SelectProvider(type = GestionEconomicaCatalunyaMapperSqlProvider.class, method = "getNombreXmlDevolucion")
	String getNombreXmlDevolucion(@Param("params") Map<String, Object> parametrosMap);

	@SelectProvider(type = GestionEconomicaCatalunyaMapperSqlProvider.class, method = "getNombreXmlCertificacionIca")
	String getNombreXmlCertificacionIca(@Param("params") Map<String, Object> parametrosMap);
	
	@SelectProvider(type = GestionEconomicaCatalunyaMapperSqlProvider.class, method = "getNombreXmlCertificacionAnexo")
	String getNombreXmlCertificacionAnexo(@Param("params") Map<String, Object> parametrosMap);
	
	@SelectProvider(type = GestionEconomicaCatalunyaMapperSqlProvider.class, method = "getCabeceraIntercambio")
    @Results({
        @Result(column="IDINTERCAMBIO", property="idIntercambio", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDINSTITUCION", property="idInstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDPERIODO", property="idPeriodo", jdbcType=JdbcType.DECIMAL),
        @Result(column="ANIO", property="anio", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBREPERIODO", property="nombrePeriodo", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCIONESTADO", property="descripcionEstado", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCIONESTADOICA", property="descripcionEstadoIca", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCIONESTADOCONSELL", property="descripcionEstadoConsell", jdbcType=JdbcType.VARCHAR),
        @Result(column="IDESTADO", property="idEstado", jdbcType=JdbcType.DECIMAL),
        @Result(column="ABREVIATURAINSTITUCION", property="abreviaturaInstitucion", jdbcType=JdbcType.VARCHAR)
    })

	GestionEconomicaCatalunyaItem getCabeceraIntercambio(Map<String, Object> map);

	
}