package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsActaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsEjgComisionSqlExtendsProvider;

public interface ScsEjgComisionExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "busquedaEJGComision")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.INTEGER),
			@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
			@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejg.idpersonajg", property = "idPersona", jdbcType = JdbcType.INTEGER),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EDITABLECOMISION", property = "editableComision", jdbcType = JdbcType.INTEGER)

	})
	List<EjgItem> busquedaEJGComision(EjgItem ejgItem, String string, Integer tamMaximo,
			String idLenguaje);
	
	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "busquedaEJGComisionFinal")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.INTEGER),
			@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
			@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejg.idpersonajg", property = "idPersona", jdbcType = JdbcType.INTEGER),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EDITABLECOMISION", property = "editableComision", jdbcType = JdbcType.INTEGER)

	})
	List<EjgItem> busquedaEJGComisionFinal(EjgItem ejgItem, String string, Integer tamMaximo,
			String idLenguaje, String stringListaEjgs);
	
	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "busquedaEJGActaComision")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.INTEGER),
			@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
			@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejg.idpersonajg", property = "idPersona", jdbcType = JdbcType.INTEGER),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR)

	})
	List<EjgItem> busquedaEJGActaComision(EjgItem ejgItem, String string, Integer tamMaximo,
			String idLenguaje);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboColegioEjgComision")
	@Results({ @Result(column = "IDINSTITUCION", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboColegioEjgComision(String idinstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "idUltimoEstado")
	@Results({})
	String idUltimoEstado(EjgItem ejgItem, String idinstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboFundamentoJuridComision")
	@Results({ @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),

	})
	List<ComboItem> comboFundamentoJuridComision(Short idlenguaje, String idInstitucion, String resolucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboPonenteComision")
	@Results({ @Result(column = "CLAVE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboPonenteComision(Short idLenguaje, String idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboFundamentoCalificacionComision")
	@Results({ @Result(column = "IDFUNDAMENTOCALIF", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboFundamentoCalificacion(Short idlenguaje, String idInstitucion, String[] list_dictamen);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboJuzgadosComision")
	@Results({ @Result(column = "IDJUZGADO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboJuzgados(String idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboTurnosTipoComision")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTurnosTipo(String idInstitucion, String tipoturno);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboEstadoEjg")
	@Results({ @Result(column = "IDESTADOEJG", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboEstadoEjg(Short idLenguaje, String idInstitucion, String resolucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboDictamenComision")
	@Results({ @Result(column = "IDTIPODICTAMENEJG", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboDictamen(Short idLenguaje, String idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboGuardiasComision")
	@Results({ @Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboGuardias(String idTurno, String idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboTipoEjgColegioComision")
	@Results({ @Result(column = "IDTIPOEJGCOLEGIO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTipoColegioEjg(Short idLenguaje, String idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboAnioActa")
	@Results({ @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboAnioActa(Short idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboResolucion")
	@Results({ @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboResolucion(Short idLenguaje, String idInstitucionp);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "obligatoriedadResolucion")
	@Results({ @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> obligatoriedadResolucion(Short idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "comboPresidente")
	@Results({ @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboPresidente(String string, Short idInstitucion);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "getEtiquetasPonente")
	@Results({ @Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR) })
	String getEtiquetasPonente(Short idLenguaje);

	@SelectProvider(type = ScsEjgComisionSqlExtendsProvider.class, method = "busquedaActas")
	@Results({

			@Result(column = "ANIOACTA", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROACTA", property = "acta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRESIDENTE", property = "Idpresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSECRETARIO", property = "Idsecretario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAREUNION", property = "fechareunion", jdbcType = JdbcType.DATE),
			@Result(column = "FECHARESOLUCION", property = "fecharesolucion", jdbcType = JdbcType.DATE)
	})
	List<ActasItem> busquedaActas(ActasItem actasItem);

}