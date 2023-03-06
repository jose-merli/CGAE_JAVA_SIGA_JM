package org.itcgae.siga.scs.services.impl.oficio;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ColegiadoItemDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaDTO;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaDTO;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaItem;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.GuardaJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.LetradoDesignaDTO;
import org.itcgae.siga.DTOs.scs.LetradoDesignaItem;
import org.itcgae.siga.DTOs.scs.LetradoInscripcionItem;
import org.itcgae.siga.DTOs.scs.ListDTO;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaLetradosDesignaItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.cen.services.IFichaDatosGeneralesService;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.Converter;
import org.itcgae.siga.commons.utils.Puntero;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.UtilOficio;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.ScsAcreditacion;
import org.itcgae.siga.db.entities.ScsAcreditacionExample;
import org.itcgae.siga.db.entities.ScsActuaciondesigna;
import org.itcgae.siga.db.entities.ScsActuaciondesignaExample;
import org.itcgae.siga.db.entities.ScsActuaciondesignaKey;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsAsistenciaKey;
import org.itcgae.siga.db.entities.ScsContrariosasistencia;
import org.itcgae.siga.db.entities.ScsContrariosasistenciaExample;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaExample;
import org.itcgae.siga.db.entities.ScsContrariosdesignaKey;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsContrariosejgExample;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaExample;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaKey;
import org.itcgae.siga.db.entities.ScsDelitosasistencia;
import org.itcgae.siga.db.entities.ScsDelitosasistenciaExample;
import org.itcgae.siga.db.entities.ScsDelitosdesigna;
import org.itcgae.siga.db.entities.ScsDelitosdesignaExample;
import org.itcgae.siga.db.entities.ScsDelitosejg;
import org.itcgae.siga.db.entities.ScsDelitosejgExample;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsDesignaprocurador;
import org.itcgae.siga.db.entities.ScsDesignaprocuradorExample;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.entities.ScsDesignasletradoExample;
import org.itcgae.siga.db.entities.ScsDocumentacionasi;
import org.itcgae.siga.db.entities.ScsDocumentacionasiKey;
import org.itcgae.siga.db.entities.ScsDocumentaciondesigna;
import org.itcgae.siga.db.entities.ScsDocumentaciondesignaExample;
import org.itcgae.siga.db.entities.ScsDocumentaciondesignaKey;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsEjgdesignaExample;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsProcedimientosKey;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojExample;
import org.itcgae.siga.db.entities.ScsTipodictamenejg;
import org.itcgae.siga.db.entities.ScsTiporesolucion;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoExample;
import org.itcgae.siga.db.entities.ScsTurnoKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsAcreditacionMapper;
import org.itcgae.siga.db.mappers.ScsActuaciondesignaMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosejgMapper;
import org.itcgae.siga.db.mappers.ScsDesignaprocuradorMapper;
import org.itcgae.siga.db.mappers.ScsDesignasletradoMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionasiMapper;
import org.itcgae.siga.db.mappers.ScsDocumentaciondesignaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgdesignaMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.mappers.ScsProcedimientosMapper;
import org.itcgae.siga.db.mappers.ScsSaltoscompensacionesMapper;
import org.itcgae.siga.db.mappers.ScsSojMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignasLetradoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsImpugnacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrisionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipodictamenejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTiporesolucionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.itcgae.siga.scs.services.oficio.ISaltosCompOficioService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DesignacionesServiceImpl implements IDesignacionesService {

	private Logger LOGGER = Logger.getLogger(DesignacionesServiceImpl.class);

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private ScsSojMapper scsSojMapper;

	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;
	
	@Autowired
	private ScsImpugnacionExtendsMapper scsImpugnacionSqlExtendsMapper;

	@Autowired
	private ScsDelitosejgMapper scsDelitosejgMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosDesignaMapper;

	@Autowired
	private ScsDefendidosdesignaMapper scsDefendidosdesignaMapper;

	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosdesignaMapper;

	@Autowired
	private ScsContrariosasistenciaMapper scsContrariosasistenciaMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private ScsTipodictamenejgExtendsMapper scsTipodictamenejgExtendsMapper;

	@Autowired
	private ScsPrisionExtendsMapper scsPrisionExtendsMapper;

	@Autowired
	private ScsPersonajgMapper scsPersonajgMapper;

	@Autowired
	private ScsActuaciondesignaMapper scsActuaciondesignaMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignaMapper;

	@Autowired
	private ScsSaltoscompensacionesMapper scsSaltoscompensacionesMapper;

	@Autowired
	private ScsTurnoMapper scsTurnoMapper;

	@Autowired
	private ScsOrdenacioncolasMapper scsOrdenacioncolasMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;

	@Autowired
	private ScsDesignasletradoMapper scsDesignasletradoMapper;

	@Autowired
	private ScsDesignasLetradoExtendsMapper scsDesignasLetradoExtendMapper;

	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionesTurnoExtendsMapper;

	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private ScsContrariosejgMapper scsContrariosejgMapper;

	@Autowired
	private ScsDocumentacionasiMapper scsDocumentacionasiMapper;

	@Autowired
	private GenFicheroMapper genFicheroMapper;

	@Autowired
	private ScsDocumentaciondesignaMapper scsDocumentaciondesignaMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private ScsDelitosasistenciaMapper scsDelitosasistenciaMapper;

	@Autowired
	private ScsProcedimientosMapper scsProcedimientosMapper;

	@Autowired
	private ScsAcreditacionMapper scsAcreditacionMapper;

	@Autowired
	private ISaltosCompOficioService saltosCompOficioService;

	@Autowired
	private ScsDesignaprocuradorMapper scsDesignaProcuradorMapper;

	@Autowired
	private UtilOficio utilOficio;

	@Autowired
	private ScsEjgdesignaMapper scsEjgdesignaMapper;

	@Autowired
	private IFichaDatosGeneralesService fichaDatosGenerales;

	@Autowired
	private ITarjetaDatosDireccionesService tarjetaDatosDireccionesService;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsDelitosdesignaMapper scsDelitosdesignaMapper;

	@Autowired
	private ScsAsistenciaMapper scsAsistenciaMapper;

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsTiporesolucionExtendsMapper scsTiporesolucionExtendMapper;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;
	
	// Contadores necesarios para las comprobaciones de filtros modo lectura al guardar justificaciones exprés
	private int numActualizados;
	private int numNoActualizadosSinEjg;
	private int numNoActualizadosEjgNoFavorable;
	private int numNoActualizadosEjgSinResolucion;
	private int numNoActualizadosEjgResolucionPteCajg;

	/**
	 * busquedaJustificacionExpres
	 */
	@Override
	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item,
			HttpServletRequest request) {
		List<JustificacionExpressItem> result = null;
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO parametros = new StringDTO();
		String idPersona = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaJustificacionExpres -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo longitud_codeejg...");
					// cargamos los parámetros necesarios
					String longitudCodEJG;

					// LONGITUD_CODEJG
					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG",
							idInstitucion.toString());

					// si el ncolegiado, viene relleno, debemos obtener la idpersona
					if (item.getnColegiado() != null && !item.getnColegiado().trim().isEmpty()) {
						LOGGER.info(
								"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo la idpersona...");

						// obtenemos la idpersona
						ColegiadosSJCSItem colegiadosSJCSItem = new ColegiadosSJCSItem();
						colegiadosSJCSItem.setnColegiado(item.getnColegiado());

						List<ColegiadosSJCSItem> colegiadosSJCSItems = cenColegiadoExtendsMapper
								.busquedaColegiadosSJCS(idInstitucion.toString(), colegiadosSJCSItem);

						if (colegiadosSJCSItems.size() > 0) {
							idPersona = colegiadosSJCSItems.get(0).getIdPersona();
						}
					}

					// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
					// la institucion 0
					if (parametros != null && parametros.getValor() != null) {
						longitudCodEJG = parametros.getValor();
					} else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", "0");
						longitudCodEJG = parametros.getValor();
					}

					// obtenemos los estados para los expedientes

					List<ScsTipodictamenejg> estadosExpedientes = scsTipodictamenejgExtendsMapper
							.estadosDictamen(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

					String idFavorable = null;
					String idDesfavorable = null;

					for (ScsTipodictamenejg tipoDictamen : estadosExpedientes) {
						if ("FAVORABLE".equalsIgnoreCase(tipoDictamen.getDescripcion())) {
							idFavorable = tipoDictamen.getIdtipodictamenejg().toString();
						} else if ("DESFAVORABLE".equalsIgnoreCase(tipoDictamen.getDescripcion())) {
							idDesfavorable = tipoDictamen.getIdtipodictamenejg().toString();
						}
					}
					String fechaDesde = null;
					String fechaHasta = null;
					if (item.getDesignacionDesde() != null) {
						fechaDesde = Converter.dateToString(item.getDesignacionDesde());
					}
					if (item.getDesignacionHasta() != null) {
						fechaHasta = Converter.dateToString(item.getDesignacionHasta());
					}
					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo justificaciones...");
					// busqueda de designaciones segun los filtros (max 200)
					result = scsDesignacionesExtendsMapper.busquedaJustificacionExpresPendientes(item,
							idInstitucion.toString(), longitudCodEJG, idPersona, idFavorable, idDesfavorable,
							fechaDesde, fechaHasta);
//					result = scsDesignacionesExtendsMapper.busquedaJustificacionExpresPendientes(item,
//							idInstitucion.toString(), longitudCodEJG, idPersona, 
//							fechaDesde, fechaHasta);

					LOGGER.info(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> obteniendo las actuaciones...");
					// obtenemos las actuaciones

					
					Iterator<JustificacionExpressItem> it = result.iterator();
					
					while (it.hasNext()) {
						JustificacionExpressItem record = it.next();
						
						record.setActuaciones(scsDesignacionesExtendsMapper.busquedaActuacionesJustificacionExpres(
								record.getIdInstitucion(), record.getIdTurno(), record.getAnioDesignacion(),
								record.getNumDesignacion(), item));
						
						if (item.isMuestraPendiente()) {
							float porcentajeTotal = 0;
							
							for (ActuacionesJustificacionExpressItem actuacion: record.getActuaciones()) {
								porcentajeTotal += Float.valueOf(actuacion.getPorcentaje().replace(",", "."));
							}
							
							if ("A".equals(record.getEstado())
									|| "F".equals(record.getEstado())
									|| porcentajeTotal >= 100) {
								it.remove();;
							}
						}
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> tratando expedientes...");

					// cogemos los expedientes devueltos de la consulta y los tratamos para el front
					for (int i = 0; i < result.size(); i++) {
						Map<String, String> expedientes = new HashMap<String, String>();

						if (result.get(i).getEjgs() != null && !result.get(i).getEjgs().isEmpty()) {
							String[] parts = result.get(i).getEjgs().split(",");

							for (String str : parts) {
								if (str.indexOf("##") != -1) {
//									String id = str.substring(str.length() - 1).toString();
////									expedientes.put(str.substring(0, str.indexOf("##")).trim(),
////											(str.substring(str.length() - 1) == idFavorable ? "FAVORABLE"
////													: (str.substring(str.length() - 1) == idDesfavorable
////															? "DESFAVORABLE"
////															: "''")));
//									if(id.equals(idFavorable)) {
//										expedientes.put(str.substring(0, str.indexOf("##")).trim(),"FAVORABLE");
//									}else if(id.equals(idDesfavorable)) {
//										expedientes.put(str.substring(0, str.indexOf("##")).trim(),"DESFAVORABLE");
//									}else {
//										expedientes.put(str.substring(0, str.indexOf("##")).trim(),"''");
//									}

									// obtenemos los datos del EJG
									// anio/numEjg ## docresolucion ## idinstitucion ## anio ## idtipoejg ## numero ## idtiporatificacionejg ## fecharesolucioncajg
									String[] datosEJG = str.split("##");
									String anioEJG = datosEJG[0].split("/")[0].trim();
									String numEJG = datosEJG[0].split("/")[1];
									String idInstitucionEJG = datosEJG[2];
									String idtipoEJG = datosEJG[4];
									String num = datosEJG[5];
									String dictamen = "";
									String resolucion = "";
									String impugnacion = "";
									String tooltip = "";

									// hacemos una busqueda para obtener el EJG con el dictamen y la resolucion
									ScsEjgExample ejgExample = new ScsEjgExample();
									ejgExample.createCriteria().andAnioEqualTo(Short.parseShort(anioEJG))
											.andNumejgEqualTo(numEJG)
											.andIdinstitucionEqualTo(Short.parseShort(idInstitucionEJG))
											.andIdtipoejgEqualTo(Short.valueOf(idtipoEJG));
									List<ScsEjg> ejg = scsEjgExtendsMapper.selectByExample(ejgExample);
									if (!ejg.isEmpty()) {

										// si la resolucion no esta vacia hace una busqueda para obtener la descripcion
										// de la resolucion
										if (ejg.get(0).getIdtiporatificacionejg() != null) {

											List<ScsTiporesolucion> resolucionExpediente = scsTiporesolucionExtendMapper
													.getResolucionesEJG2(usuarios.get(0).getIdlenguaje());
											// resolucion = resolucionExpediente.get(0).getDescripcion();

											for (ScsTiporesolucion tipoResolucion : resolucionExpediente) {
												if (ejg.get(0).getIdtiporatificacionejg().toString()
														.equals(tipoResolucion.getIdtiporesolucion().toString())) {
													resolucion = tipoResolucion.getDescripcion();
												}

											}
										}

										// si el dictamen no esta vacio hace una busqueda para obtener la descripcion
										// del dictamen
										if (ejg.get(0).getIdtipodictamenejg() != null) {

											for (ScsTipodictamenejg tipoDictamen : estadosExpedientes) {
												if (ejg.get(0).getIdtipodictamenejg().toString()
														.equals(tipoDictamen.getIdtipodictamenejg().toString())) {
													dictamen = tipoDictamen.getDescripcion();
												}
											}

										}
										
										// si la impugnacion no esta vacia hace una busqueda para obtener la descripcion
										// de la impugnacion
										if (ejg.get(0).getIdtiporesolauto() != null) {

											List<String> impugnacionExpediente = scsImpugnacionSqlExtendsMapper
													.getDescImpugnacion(ejg.get(0).getIdtiporesolauto());
											
											impugnacion = impugnacionExpediente.get(0);

										}
										
										// en esta parte se configura el mensaje para el tooltip
										if ((impugnacion == null || impugnacion.isEmpty())
												&& (resolucion == null || resolucion.isEmpty())
												&& (dictamen == null || dictamen.isEmpty())) {
											tooltip = "Designación con EJG sin Resolución";
											
										}
										
										if (impugnacion != null && !impugnacion.isEmpty()) {
											tooltip = "Impugnación: " + impugnacion;
										}
										
										if (resolucion != null && !resolucion.isEmpty()) {

											if (tooltip != null && tooltip.isEmpty()) {
												tooltip = "Resolución: " + resolucion;
											} else {
												tooltip += "\n\rResolución: " + resolucion;
											}

										} 
										
										if (dictamen != null && !dictamen.isEmpty()) {

											if (tooltip != null && tooltip.isEmpty()) {
												tooltip = "Dictamen: " + dictamen;
											} else {
												tooltip += "\n\rDictamen: " + dictamen;
											}

										}
										
										expedientes.put(str.substring(0, str.indexOf("##")).trim(),
												tooltip);
										//Info EJG
										expedientes.put("EJG",num + "/" + idtipoEJG);
									}

								}

							}
						}

						if (expedientes.size() > 0) {
							result.get(i).setExpedientes(expedientes);
						}
					}

					if ((result != null) && (result.size()) >= 200) {
						error.setCode(200);
						error.setDescription(
								"La consulta devuelve más de 200 resultados, pero se muestran sólo los 200 más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						result.get(0).setError(error);
					}

					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida del servicio");
				} catch (Exception e) {
					LOGGER.error(
							"DesignacionesServiceImpl.busquedaJustificacionExpres -> ERROR: al consultar datos de la bd. ",
							e);
				}
			}
		}

		return result;
	}

	/**
	 * insertaJustificacionExpres
	 */
	@Override
	public InsertResponseDTO insertaJustificacionExpres(ActuacionesJustificacionExpressItem item,
			HttpServletRequest request) {
		InsertResponseDTO responseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.insertaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.insertaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.insertaJustificacionExpres() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Haciendo el insert...");

					ScsActuaciondesigna record = new ScsActuaciondesigna();

					if (item.getAnioProcedimiento() != null && !item.getAnioProcedimiento().trim().isEmpty()) {
						record.setAnioprocedimiento(Short.parseShort(item.getAnioProcedimiento()));
					}

					if (item.getFechaJustificacion() != null && !item.getFechaJustificacion().trim().isEmpty()
							&& !item.getFechaJustificacion().equals("true")
							&& !item.getFechaJustificacion().equals("false")) {
						fecha = formatter.parse(item.getFechaJustificacion());
						record.setFechajustificacion(fecha);
					}

					// Idacreditacion, hecho asi por el front
					if (item.getDescripcion() != null && !item.getDescripcion().trim().isEmpty()
							&& item.getDescripcion().indexOf(",") != -1) {
						record.setIdacreditacion(Short
								.parseShort(item.getDescripcion().substring(0, item.getDescripcion().indexOf(","))));
					}

					// idJuzgado, hecho asi por el front
					if (item.getIdJuzgado() != null && !item.getIdJuzgado().trim().isEmpty()) {
						record.setIdjuzgado(Long.parseLong(item.getIdJuzgado()));
						record.setIdinstitucionJuzg(idInstitucion);
					}

					if (item.getNig() != null && !item.getNig().trim().isEmpty()) {
						record.setNig(item.getNig());
					}

					if (item.getNumProcedimiento() != null && !item.getNumProcedimiento().trim().isEmpty()) {
						record.setNumeroprocedimiento(item.getNumProcedimiento());
					}
					if (item.getValidada() != null && !item.getValidada().trim().isEmpty()) {
						record.setValidada(item.getValidada());

						// Si se valida una actuación se establece automáticamente la fecha de
						// justificación
						if (record.getValidada().equals("1") && record.getFechajustificacion() == null) {
							record.setFechajustificacion(new Date());
						}
					}

					fecha = formatter.parse(item.getFecha());
					record.setFecha(fecha);

					record.setIdturno(Integer.parseInt(item.getIdTurno()));
					record.setIdinstitucion(idInstitucion);
					record.setAnio(Short.parseShort(item.getAnio()));
					record.setNumero(Long.parseLong(item.getNumDesignacion()));
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setFechamodificacion(new Date());
					record.setAcuerdoextrajudicial((short) 0);
					record.setAnulacion((short) 0);

					ScsProcedimientosKey procedimientosKey = new ScsProcedimientosKey();
					procedimientosKey.setIdprocedimiento(item.getProcedimiento());
					procedimientosKey.setIdinstitucion(idInstitucion);
					if (scsProcedimientosMapper.selectByPrimaryKey(procedimientosKey) != null) {
						record.setIdprocedimiento(item.getProcedimiento());// idprocedimiento, por el front
					}

					record.setIdinstitucionProc(idInstitucion);

					record.setUsucreacion(usuarios.get(0).getIdusuario());
					record.setFechacreacion(new Date());

					// cogemos el numasunto
					ActuacionDesignaItem actDesItem = new ActuacionDesignaItem();

					actDesItem.setIdTurno(item.getIdTurno());
					actDesItem.setAnio(item.getAnio());
					actDesItem.setNumero(item.getNumDesignacion());

					MaxIdDto maxIdDto = scsDesignacionesExtendsMapper.getNewIdActuDesigna(actDesItem, idInstitucion);

					record.setNumeroasunto(maxIdDto.getIdMax());
					record.setIdpersonacolegiado(Long.valueOf(item.getIdPersonaColegiado()));
					
					response = scsActuaciondesignaMapper.insertSelective(record);

					LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Insert finalizado");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.insertaJustificacionExpres() -> Se ha producido un error ",
							e);
					response = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.insertaJustificacionExpres() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}

		responseDTO.setError(error);

		return responseDTO;
	}

	/**
	 * eliminaJustificacionExpres
	 */
	public DeleteResponseDTO eliminaJustificacionExpres(List<ActuacionesJustificacionExpressItem> listaItem,
			HttpServletRequest request) {
		DeleteResponseDTO responseDTO = new DeleteResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Realizando borrado...");

					ScsActuaciondesignaKey key = new ScsActuaciondesignaKey();

					for (ActuacionesJustificacionExpressItem item : listaItem) {
						key.setAnio(Short.parseShort(item.getAnio()));
						key.setIdinstitucion(Short.parseShort(item.getIdInstitucion()));
						key.setIdturno(Integer.parseInt(item.getIdTurno()));
						key.setNumero(Long.parseLong(item.getNumDesignacion()));
						key.setNumeroasunto(Long.parseLong(item.getNumAsunto()));

						response += scsActuaciondesignaMapper.deleteByPrimaryKey(key);
					}

					LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Borrado finalizado");
				} catch (Exception e) {
					LOGGER.error(
							"DesignacionesServiceImpl.eliminaJustificacionExpres() -> Se ha producido un error al borrar la actuacion. ",
							e);
					response = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.eliminaJustificacionExpres() -> Saliendo del servicio");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.eliminado");
		}

		responseDTO.setError(error);

		return responseDTO;

	}

	@Override
	public List<DesignaItem> busquedaDesignas(DesignaItem designaItem, HttpServletRequest request) {
		// DesignaItem result = new DesignaItem();
		Error error = new Error();
		List<DesignaItem> designas = null;
		List<DesignaItem> designasNuevas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaDesignas -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaDesignaciones(designaItem, idInstitucion,
							tamMaximo);
					// designasNuevas =
					// scsDesignacionesExtendsMapper.busquedaNuevaDesigna(designaItem,
					// idInstitucion,
					// tamMaximo, false);

					// List<DesignaItem> designasNuevasNoColegiado = null;
					// designasNuevasNoColegiado =
					// scsDesignacionesExtendsMapper.busquedaNuevaDesigna(designaItem,
					// idInstitucion, tamMaximo, true);

					// for (DesignaItem designaNoColegiado : designasNuevasNoColegiado) {
					// designasNuevas.add(designaNoColegiado);
					// }

//					Map<String, DesignaItem> desginasBusqueda = new HashMap<String, DesignaItem>();
//					for (DesignaItem d : designas) {
//						int[] indice = new int[4];
//						indice[0] = d.getAno();
//						indice[1] = d.getNumero();
//						indice[2] = d.getIdTurno();
//						indice[3] = idInstitucion;
//						desginasBusqueda.put(d.getAno() + "" + d.getNumero() + "" + d.getIdTurno() + "" + idInstitucion,
//								d);
//					}
//					for (DesignaItem d : designasNuevas) {
//						int[] indice = new int[4];
//						indice[0] = d.getAno();
//						indice[1] = d.getNumero();
//						indice[2] = d.getIdTurno();
//						indice[3] = idInstitucion;
//						if (!(desginasBusqueda.containsKey(
//								d.getAno() + "" + d.getNumero() + "" + d.getIdTurno() + "" + idInstitucion))) {
//							desginasBusqueda
//									.put(d.getAno() + "" + d.getNumero() + "" + d.getIdTurno() + "" + idInstitucion, d);
//						}
//					}

//					designas = new ArrayList<DesignaItem>(desginasBusqueda.values());
//
//					if (designas.size() > 200) {
//						int z = 0;
//						for (int x = 200; x <= designas.size() - 1; z++) {
//							designas.remove(x);
//						}
//					}

					if ((designas != null) && (designas.size()) >= 200) {
						error.setCode(200);
						error.setDescription(
								"La consulta devuelve más de 200 resultados, pero se muestran sólo los 200 más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						designas.get(0).setError(error);
					}

				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<DesignaItem> busquedaNuevaDesigna(DesignaItem designaItem, HttpServletRequest request,
			boolean isNoColegiado) {
		// DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaDesignas -> Entrada a servicio para la busqueda de justifiacion express");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaNuevaDesigna(designaItem, idInstitucion, tamMaximo,
							false);

					if (designas.size() == 0) {
						designas = scsDesignacionesExtendsMapper.busquedaNuevaDesigna(designaItem, idInstitucion,
								tamMaximo, true);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<DesignaItem> busquedaProcedimientoDesignas(DesignaItem designaItem, HttpServletRequest request) {
		// DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaProcedimientoDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Entrada a servicio para la busqueda de ProcedimientoDesignas");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaProcedimientoDesignas(designaItem, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaProcedimientoDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<DesignaItem> busquedaModuloDesignas(DesignaItem designaItem, HttpServletRequest request) {
		// DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaModuloDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaModuloDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaModuloDesignas -> Entrada a servicio para la busqueda de ModuloDesignas");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaModuloDesignas(designaItem, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaModuloDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaModuloDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem item, HttpServletRequest request) {

		// [designaItem.idTurno, designaItem.nombreTurno, designaItem.numero,
		// designaItem.anio]

		LOGGER.info("DesignacionesServiceImpl.busquedaListaInteresados() -> Entrada al servicio servicio");
		List<ListaInteresadoJusticiableItem> interesados = null;
//		List<GenParametros> tamMax = null;
//		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaInteresados() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaInteresados() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

//			GenParametrosExample genParametrosExample = new GenParametrosExample();
//			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
//					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
//					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
//			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
//			LOGGER.info(
//					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
//			LOGGER.info(
//					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			if (tamMax != null) {
//				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
//			} else {
//				tamMaximo = null;
//			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaListaInteresados -> Entrada a servicio para la busqueda de interesados de una ficha de designacion");
				try {
					interesados = scsDesignacionesExtendsMapper.busquedaListaInteresados(item, idInstitucion);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaListaInteresados -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaListaInteresados -> Salida del servicio");
			}
		}

		return interesados;
	}

	@Override
	public DeleteResponseDTO deleteInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteInteresado() ->  Entrada al servicio para eliminar contrarios");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsDefendidosdesignaKey key = new ScsDefendidosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Entrada a ScsDefendidosdesignaMapper para eliminar los interesados seleccionados");

					response = scsDefendidosdesignaMapper.deleteByPrimaryKey(key);

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Salida de ScsDefendidosdesignaMapper para eliminar los interesados seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("deleteInteresado() -> Salida del servicio para eliminar interesados");

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO insertInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info("insertInteresado() ->  Entrada al servicio para insert un interesado");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsDefendidosdesigna designa = new ScsDefendidosdesigna();
					designa.setAnio(item.getAnio());
					designa.setNumero(item.getNumero());
					designa.setIdturno(item.getIdturno());
					designa.setIdinstitucion(item.getIdinstitucion());
					designa.setIdpersona(item.getIdpersona());
					designa.setFechamodificacion(new Date());
					designa.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener el justiciable");

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info(
							"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");

					// Se comprueba si tiene representante y se busca.
					if (personajg.getIdrepresentantejg() != null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						designa.setNombrerepresentante(representante.getApellido1() + " " + representante.getApellido2()
								+ ", " + representante.getNombre());
					}

					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para insertar el interesado");

					response = scsDefendidosdesignaMapper.insert(designa);

					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para insertar el interesado");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertInteresado() -> Salida del servicio para eliminar contrarios");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateRepresentanteInteresado(ScsDefendidosdesigna item, HttpServletRequest request) {
		LOGGER.info(
				"updateRepresentanteInteresado() ->  Entrada al servicio para actualizar el representante de un interesado");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateRepresentanteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRepresentanteInteresado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsDefendidosdesignaKey key = new ScsDefendidosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsDefendidosdesigna interesado = scsDefendidosdesignaMapper.selectByPrimaryKey(key);

					interesado.setNombrerepresentante(item.getNombrerepresentante());

					interesado.setFechamodificacion(new Date());
					interesado.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateRepresentanteInteresado() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					response = scsDefendidosdesignaMapper.updateByPrimaryKey(interesado);

					LOGGER.info(
							"updateRepresentanteInteresado() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateRepresentanteInteresado() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateRepresentanteContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info(
				"updateRepresentanteContrario() ->  Entrada al servicio para actualizar el representante de un contrario");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateRepresentanteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRepresentanteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

					contrario.setNombrerepresentante(item.getNombrerepresentante());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateRepresentanteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para actualizar el representante de un interesado.");

					response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateRepresentanteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para actualizar el representante de un interesado.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateRepresentanteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateAbogadoContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("updateAbogadoContrario() ->  Entrada al servicio para actualizar el abogado de un contrario");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

					contrario.setIdabogadocontrario(item.getIdabogadocontrario());
					contrario.setNombreabogadocontrario(item.getNombreabogadocontrario());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

//						List<ColegiadoItem> colegiadosItems = cenColegiadoExtendsMapper.selectColegiadosByIdPersona(idInstitucion, contrario.getIdabogadocontrario().toString());
//						
//						FichaDatosColegialesItem abogado = colegiadosItems.get(0);

//						contrario.set

					LOGGER.info(
							"updateAbogadoContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para actualizar el abogado de un contrario.");

					response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateAbogadoContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para actualizar el abogado de un contrario.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateAbogadoContrario() -> Salida del servicio para actualizar del abogado de un contrario.");

		return updateResponseDTO;
	}

	@Override
	public ColegiadoItemDTO SearchAbogadoByIdPersona(String idPersona, HttpServletRequest request) {
		LOGGER.info("updateAbogadoContrario() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ColegiadoItemDTO abogado = new ColegiadoItemDTO();
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAbogadoContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					List<ColegiadoItem> colegiadosItems = cenColegiadoExtendsMapper
							.selectColegiadosByIdPersona(idInstitucion, idPersona);

					abogado.setColegiadoItem(colegiadosItems.get(0));

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		abogado.setError(error);

		LOGGER.info("updateAbogadoContrario() -> Salida del servicio para eliminar contrarios");

		return abogado;
	}

	@Override
	public UpdateResponseDTO updateProcuradorContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info(
				"updateProcuradorContrario() ->  Entrada al servicio para actualizar el procurador de un contrario asociado a una designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateProcuradorContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateProcuradorContrarioo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

					contrario.setIdprocurador(item.getIdprocurador());
					contrario.setIdinstitucionProcu(item.getIdinstitucionProcu());

//					List<FichaDatosColegialesItem> colegiadosSJCSItems = cenColegiadoExtendsMapper
//							.selectDatosColegiales(item.getIdprocurador().toString(), idInstitucion.toString());

//					FichaDatosColegialesItem procurador = colegiadosSJCSItems.get(0);
//						contrario.set

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateProcuradorContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para actualizar el procurador de un contrario asociado a una designacion.");

					response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateProcuradorContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para actualizar el procurador de un contrario asociado a una designacion.");

					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"updateProcuradorContrario() -> Salida del servicio para actualizar el procurador de un contrario asociado a una designacion");

		return updateResponseDTO;
	}

	@Override
	public List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, HttpServletRequest request,
			Boolean historico) {
		LOGGER.info(
				"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada al servicio para buscar los contrarios asociados a una designacion.");
		List<ListaContrarioJusticiableItem> contrarios = null;
//		List<GenParametros> tamMax = null;
//		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

//			GenParametrosExample genParametrosExample = new GenParametrosExample();
//			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
//					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
//					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
//			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
//			LOGGER.info(
//					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
//			LOGGER.info(
//					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			if (tamMax != null) {
//				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
//			} else {
//				tamMaximo = null;
//			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaListaContrarios -> Entrada a servicio para la busqueda de contrarios");

				try {
					contrarios = scsDesignacionesExtendsMapper.busquedaListaContrarios(item, idInstitucion, historico);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaListaContrarios -> Salida del servicio");
			}
		}

		return contrarios;
	}

	@Override
	public UpdateResponseDTO deleteContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("deleteContrarios() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosdesignaKey key = new ScsContrariosdesignaKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdturno(item.getIdturno());
					key.setIdinstitucion(item.getIdinstitucion());
					key.setIdpersona(item.getIdpersona());

					ScsContrariosdesigna contrario = scsContrariosDesignaMapper.selectByPrimaryKey(key);

					if (contrario.getFechabaja() == null) {
						contrario.setFechabaja(new Date());
					} else {
						contrario.setFechabaja(null);
					}

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"deleteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosDesignaMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"deleteContrario() / scsContrariosDesignaMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					ScsDesignaKey designaKey = new ScsDesignaKey();
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(item.getAnio());
					designaKey.setIdturno(item.getIdturno());
					designaKey.setNumero(item.getNumero());
					ScsDesigna scsDesigna = scsDesignacionesExtendsMapper.selectByPrimaryKey(designaKey);// new
																											// ScsDesigna();

					if (scsDesigna != null) {
						actualizaDesignaEnAsuntos(scsDesigna, idInstitucion, "contrariosDesigna", usuarios.get(0));
					}
					// actualizaAsistenciaEnAsuntos
					// }

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertContrario(ScsContrariosdesigna item, HttpServletRequest request) {
		LOGGER.info("insertContrario() ->  Entrada al servicio para eliminar contrarios");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsContrariosdesigna designa = new ScsContrariosdesigna();
					designa.setAnio(item.getAnio());
					designa.setNumero(item.getNumero());
					designa.setIdturno(item.getIdturno());
					designa.setIdinstitucion(item.getIdinstitucion());
					designa.setIdpersona(item.getIdpersona());
					designa.setFechamodificacion(new Date());
					designa.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info(
							"insertInteresado() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciables");

					// Se comprueba si tiene representante y se busca.
					if (personajg.getIdrepresentantejg() != null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						designa.setNombrerepresentante(representante.getApellido1() + " " + representante.getApellido2()
								+ ", " + representante.getNombre());
					}

					LOGGER.info(
							"insertInteresado() / ScsDefendidosdesignaMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosdesignaMapper.insert(designa);

					ScsDesignaKey designaKey = new ScsDesignaKey();
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(item.getAnio());
					designaKey.setIdturno(item.getIdturno());
					designaKey.setNumero(item.getNumero());
					ScsDesigna scsDesigna = scsDesignacionesExtendsMapper.selectByPrimaryKey(designaKey);// new
																											// ScsDesigna();

					if (scsDesigna != null) {
						actualizaDesignaEnAsuntos(scsDesigna, idInstitucion, "contrariosDesigna", usuarios.get(0));
					}

					LOGGER.info(
							"insertContrario() / ScsDefendidosdesignaMapper.insert() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription(e.getMessage());
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertContrario() -> Salida del servicio para eliminar contrarios");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDetalleDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDetalleDesigna() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDetalleDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDetalleDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updateDatosAdicionales()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample example = new ScsDesignaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(designaItem.getIdTurno()).andAnioEqualTo((short) designaItem.getAno())
							.andNumeroEqualTo(new Long(designaItem.getNumero()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(example);

					if ((designaExistentes == null || designaExistentes.size() == 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					}

					ScsDesignaKey designaKey = new ScsDesignaKey();
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(Short.valueOf(String.valueOf(designaItem.getAno())));
					designaKey.setIdturno(designaItem.getIdTurno());
					designaKey.setNumero(Long.valueOf(designaItem.getNumero()));
					ScsDesigna scsDesigna = scsDesignacionesExtendsMapper.selectByPrimaryKey(designaKey);// new ScsDesigna();

					if (scsDesigna != null) {

						if (designaItem.getResumenAsunto() != null && !designaItem.getResumenAsunto().isEmpty()) {
							scsDesigna.setResumenasunto(designaItem.getResumenAsunto());
						}

						if (designaItem.getFechaAnulacion() == null) {
							scsDesigna.setEstado(designaItem.getEstado());
							scsDesigna.setFechaestado(designaItem.getFechaEstado());
						} else {
							scsDesigna.setNig(designaItem.getNig());
							scsDesigna.setNumprocedimiento(designaItem.getNumProcedimiento());
							if (designaItem.getEstado() != null && !designaItem.getEstado().isEmpty()) {
								scsDesigna.setEstado(designaItem.getEstado());
							}
							Long juzgado = new Long(designaItem.getIdJuzgado());
							scsDesigna.setIdjuzgado(juzgado);
							if (designaItem.getIdPretension() == 0) {
								scsDesigna.setIdpretension(null);
							} else {
								Short idPretension = new Short((short) designaItem.getIdPretension());
								scsDesigna.setIdpretension(idPretension);
							}
							scsDesigna.setIdprocedimiento(designaItem.getIdProcedimiento());
							scsDesigna.setFechaestado(designaItem.getFechaEstado());
							scsDesigna.setFechafin(designaItem.getFechaFin());

							ScsDelitosdesignaExample scsDelitosdesignaExample = new ScsDelitosdesignaExample();
							scsDelitosdesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andNumeroEqualTo(Long.valueOf(designaItem.getNumero()))
									.andIdturnoEqualTo(Integer.valueOf(designaItem.getIdTurno()))
									.andAnioEqualTo(Integer.valueOf(designaItem.getAno()).shortValue());

							scsDelitosdesignaMapper.deleteByExample(scsDelitosdesignaExample);

							if (!UtilidadesString.esCadenaVacia(designaItem.getDelitos())) {

								String[] listaDelitos = designaItem.getDelitos().split(",");

								ScsDelitosdesigna scsDelitosdesigna = new ScsDelitosdesigna();
								scsDelitosdesigna.setIdinstitucion(idInstitucion);
								scsDelitosdesigna.setNumero(Long.valueOf(designaItem.getNumero()));
								scsDelitosdesigna.setIdturno(Integer.valueOf(designaItem.getIdTurno()));
								scsDelitosdesigna.setAnio(Integer.valueOf(designaItem.getAno()).shortValue());
								scsDelitosdesigna.setFechamodificacion(new Date());
								scsDelitosdesigna.setUsumodificacion(usuarios.get(0).getIdusuario());

								for (String delito : listaDelitos) {
									scsDelitosdesigna.setIddelito(Short.valueOf(delito));
									scsDelitosdesignaMapper.insertSelective(scsDelitosdesigna);
								}
							}

						}
						// VALIDAMOS EL NIG

						LOGGER.info("updateDetalleDesigna() / Validamos el NIG Entrada");

						// UtilOficio utilOficio= new UtilOficio();

						boolean nigValido = utilOficio.validaNIG(designaItem.getNig(), request);

						LOGGER.info("updateDetalleDesigna() / Validamos el NIG Salida");

						LOGGER.info("updateDatosAdicionales() / scsDesignacionesExtendsMapper -> Salida ");

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
						if (nigValido == true) {
							scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);
						} else {
							error.setCode(400);
							error.setDescription("justiciaGratuita.oficio.designa.NIGInvalido");
							updateResponseDTO.setStatus(SigaConstants.KO);
						}

						actualizaDesignaEnAsuntos(scsDesigna, idInstitucion, "defensaJuridicaDesigna", usuarios.get(0));

						LOGGER.info(
								"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
					}
				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateDetalleDesigna() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDatosAdicionales(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDatosAdicionales() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDatosAdicionales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDatosAdicionales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info("updateDatosAdicionales()-> Entrada a scsDesignacionesExtendsMapper ");
					ScsDesignaExample example = new ScsDesignaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(designaItem.getIdTurno()).andAnioEqualTo((short) designaItem.getAno())
							.andNumeroEqualTo(new Long(designaItem.getNumero()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(example);

					if ((designaExistentes == null || designaExistentes.size() == 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					}

					ScsDesigna scsDesigna = new ScsDesigna();
					scsDesigna.setIdturno(designaItem.getIdTurno());
					Integer a = new Integer(idInstitucion);
					scsDesigna.setIdinstitucion(idInstitucion);
					a = designaItem.getAno();
					scsDesigna.setAnio(a.shortValue());
					Long b = new Long(designaItem.getNumero());
					scsDesigna.setNumero(b.longValue());

					scsDesigna.setFechaoficiojuzgado(designaItem.getFechaOficioJuzgado());
					scsDesigna.setDelitos(designaItem.getDelitos());
					// TODO faltan dos campos observaciones
					scsDesigna.setObservaciones(designaItem.getObservaciones());
					scsDesigna.setFecharecepcioncolegio(designaItem.getFechaRecepcionColegio());
					// TODO hora juicio?
					scsDesigna.setFechajuicio(designaItem.getFechaJuicio());
					scsDesigna.setDefensajuridica(designaItem.getDefensaJuridica());

					LOGGER.info("updateDatosAdicionales() / scsDesignacionesExtendsMapper -> Salida ");

					LOGGER.info(
							"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

					scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

					LOGGER.info(
							"updateDatosAdicionales() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info(
						"updateDatosAdicionales() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public ActuacionDesignaDTO busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
		Error error = new Error();
		ActuacionDesignaDTO actuacionDedignaDTO = new ActuacionDesignaDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.busquedaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				LOGGER.info(
						"DesignacionesServiceImpl.busquedaActDesigna() -> Se inicia la búsqueda de actuaciones asociadas a una designación");

				List<ActuacionDesignaItem> listaActuacionDesignaItem = scsDesignacionesExtendsMapper
						.busquedaActDesigna(actuacionDesignaRequestDTO, Short.toString(idInstitucion));
				
				for (ActuacionDesignaItem actuacionModificacion : listaActuacionDesignaItem) {
					if ("S".equals(actuacionModificacion.getValidarJustificacion())
							&& Integer.parseInt(actuacionModificacion.getUsuCreacion()) == usuarios.get(0)
									.getIdusuario()
							|| "N".equals(letrado)) {
						actuacionModificacion.setPermiteModificacion(true);
					} else {
						actuacionModificacion.setPermiteModificacion(false);
					}
				}

				actuacionDedignaDTO.setActuacionesDesignaItems(listaActuacionDesignaItem);

				LOGGER.info(
						"DesignacionesServiceImpl.busquedaActDesigna() -> Se finaliza la búsqueda de actuaciones asociadas a una designación");
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.busquedaActDesigna() -> Se ha producido un error al consultar las actuaciones asociadas a una designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			actuacionDedignaDTO.setError(error);
		}

		return actuacionDedignaDTO;
	}

	@Override
	public UpdateResponseDTO updatePartidaPresupuestaria(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDatosAdicionales() ->  Entrada al servicio para guardar la tarjeta de detalle designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updatePartidaPresupuestaria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updatePartidaPresupuestaria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					int response = scsDesignacionesExtendsMapper.actualizarPartidaPresupuestariaDesigna(designaItem,
							idInstitucion, usuarios.get(0));

					if (response == 1) {
						error.setCode(200);
						updateResponseDTO.setStatus(SigaConstants.OK);
						updateResponseDTO.setError(error);
					}

					if (response == 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.updatePartidaPresupuestaria() -> Se ha producido un error al altualizar la partidapresupuestaria de la designación");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						updateResponseDTO.setError(error);
					}

				} catch (Exception e) {
					LOGGER.error(
							"DesignacionesServiceImpl.updatePartidaPresupuestaria() -> Se ha producido un error al altualizar la partidapresupuestaria de la designación",
							e);
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					error.setMessage(e.getMessage());
					updateResponseDTO.setError(error);
				}

				LOGGER.info(
						"updatePartidaPresupuestaria() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO anularReactivarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			boolean anular, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.anularReactivarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.anularReactivarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for (ActuacionDesignaItem actuacionDesignaItem : listaActuacionDesignaItem) {

					if ((anular && !actuacionDesignaItem.isFacturado()) || !anular) {
						int response = scsDesignacionesExtendsMapper.anularReactivarActDesigna(actuacionDesignaItem,
								idInstitucion, usuarios.get(0), anular);
						responses.add(response);
					}

				}

				updateResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar alguna de las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.anularReactivarActDesigna() -> Se ha producido un error al anular/reactivar las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, boolean validar,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.validarDesvalidarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.validarDesvalidarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				int response = scsDesignacionesExtendsMapper.validarDesvalidarActDesigna(actuacionDesignaItem,
						Short.toString(idInstitucion), usuarios.get(0), validar);

				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.validarDesvalidarActDesigna() -> Se ha producido un error al validar/desvalidar la actuación asociada a la designación");
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.validarDesvalidarActDesigna() -> Se ha producido un error al validar/desvalidar la actuación asociada a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarActDesigna(List<ActuacionDesignaItem> listaActuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<Integer> responses = new ArrayList<>();

				for (ActuacionDesignaItem actuacionDesignaItem : listaActuacionDesignaItem) {

					LOGGER.info(
							"DesignacionesServiceImpl.eliminarActDesigna() --> Se eliminan los documentos asociados a la actuación");

					ScsDocumentaciondesignaExample documentaciondesignaExample = new ScsDocumentaciondesignaExample();
					documentaciondesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.valueOf(actuacionDesignaItem.getIdTurno()))
							.andAnioEqualTo(Short.valueOf(actuacionDesignaItem.getAnio()))
							.andNumeroEqualTo(Long.valueOf(actuacionDesignaItem.getNumero()))
							.andIdactuacionEqualTo(Long.valueOf(actuacionDesignaItem.getNumeroAsunto()));

					scsDocumentaciondesignaMapper.deleteByExample(documentaciondesignaExample);

					int response = scsDesignacionesExtendsMapper.eliminarActDesigna(actuacionDesignaItem, idInstitucion,
							usuarios.get(0));
					responses.add(response);

				}

				deleteResponseDTO.setStatus(SigaConstants.OK);

				if (responses.contains(0)) {
					LOGGER.error(
							"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al eliminar alguna de las actuaciones asociadas a la designación");
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					deleteResponseDTO.setError(error);
				} else {
					error.setCode(200);
					error.setDescription("messages.deleted.selected.success");
					deleteResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al anular las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.guardarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.guardarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				Error errorComprobaciones = null;

				// Comprobamos si el módulo tiene activado el check de "Amite múltiples (columna
				// "complemento" en la tabla) para ver si acepta varias acreditaciones
				ScsProcedimientosKey procedimientoskey = new ScsProcedimientosKey();
				procedimientoskey.setIdinstitucion(idInstitucion);
				procedimientoskey.setIdprocedimiento(actuacionDesignaItem.getIdProcedimiento());
				ScsProcedimientos procedimiento = scsProcedimientosMapper.selectByPrimaryKey(procedimientoskey);

				if (procedimiento.getComplemento() != null && procedimiento.getComplemento().equals("1")) {
					errorComprobaciones = comprobacionesActDesignaMultiples(actuacionDesignaItem, idInstitucion, false);
				} else {
					errorComprobaciones = comprobacionesActDesigna(actuacionDesignaItem, idInstitucion, false);
				}

				if (errorComprobaciones.getCode().toString().equals("200")) {

					boolean nigValido = true;

					if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
						nigValido = utilOficio.validaNIG(actuacionDesignaItem.getNig(), request);
					}

					if (nigValido) {

						MaxIdDto maxIdDto = scsDesignacionesExtendsMapper.getNewIdActuDesigna(actuacionDesignaItem,
								idInstitucion);

						actuacionDesignaItem.setNumeroAsunto(maxIdDto.getIdMax().toString());

						// Marcamos por defecto la partida presupuestaria del turno
						ScsTurnoExample scsTurnoExample = new ScsTurnoExample();
						scsTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.valueOf(actuacionDesignaItem.getIdTurno()));

						List<ScsTurno> listaTurnos = scsTurnoMapper.selectByExample(scsTurnoExample);

						if (actuacionDesignaItem.getIdPartidaPresupuestaria() == null
								|| actuacionDesignaItem.getIdPartidaPresupuestaria().isEmpty()) {
							if (!listaTurnos.isEmpty() && null != listaTurnos.get(0).getIdpartidapresupuestaria()) {
								actuacionDesignaItem.setIdPartidaPresupuestaria(
										listaTurnos.get(0).getIdpartidapresupuestaria().toString());
							}
						}

						int response = scsDesignacionesExtendsMapper.guardarActDesigna(actuacionDesignaItem,
								Short.toString(idInstitucion), usuarios.get(0));

						if (response == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
							insertResponseDTO.setId(maxIdDto.getIdMax().toString());
						}

						if (response == 0) {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.error(
									"DesignacionesServiceImpl.guardarActDesigna() -> Se ha producido un error al guardar la actuación asociada a la designación");
							error.setCode(500);
							error.setDescription("general.mensaje.error.bbdd");
							insertResponseDTO.setError(error);
						}

						ScsDesignaKey scsDesignaKey = new ScsDesignaKey();
						scsDesignaKey.setIdturno(Integer.valueOf(actuacionDesignaItem.getIdTurno()));
						scsDesignaKey.setNumero(Long.valueOf(actuacionDesignaItem.getNumero()));
						scsDesignaKey.setAnio(Short.valueOf(actuacionDesignaItem.getAnio()));
						scsDesignaKey.setIdinstitucion(idInstitucion);

						ScsDesigna scsDesigna = scsDesignaMapper.selectByPrimaryKey(scsDesignaKey);

						if (scsDesigna != null) {

							if (scsDesigna.getIdjuzgado() == null
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdJuzgado())) {
								scsDesigna.setIdjuzgado(Long.valueOf(actuacionDesignaItem.getIdJuzgado()));
							}

							if (UtilidadesString.esCadenaVacia(scsDesigna.getIdprocedimiento())
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdProcedimiento())) {
								scsDesigna.setIdprocedimiento(actuacionDesignaItem.getIdProcedimiento());
							}

							if (scsDesigna.getIdpretension() == null
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPretension())) {
								scsDesigna.setIdpretension(Short.valueOf(actuacionDesignaItem.getIdPretension()));
							}

							if (UtilidadesString.esCadenaVacia(scsDesigna.getNumprocedimiento())
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNumProcedimiento())) {
								scsDesigna.setNumprocedimiento(actuacionDesignaItem.getNumProcedimiento());
							}

							if (UtilidadesString.esCadenaVacia(scsDesigna.getNig())
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
								scsDesigna.setNig(actuacionDesignaItem.getNig());
							}

							scsDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsDesigna.setFechamodificacion(new Date());

							int response2 = scsDesignaMapper.updateByPrimaryKeySelective(scsDesigna);

							if (response2 == 1) {
								insertResponseDTO.setStatus(SigaConstants.OK);
							}

							if (response2 == 0) {
								insertResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.error(
										"DesignacionesServiceImpl.guardarActDesigna() -> Se ha producido un error al actualizar los datos de la designación");
								error.setCode(400);
								error.setDescription("general.mensaje.error.bbdd");
								insertResponseDTO.setError(error);
							}

						}
					} else {
						LOGGER.error(
								"DesignacionesServiceImpl.guardarActDesigna() -> Se ha producido un error al guardar la actuación asociada a la designación, NIG no válido");
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setDescription("justiciaGratuita.oficio.designa.NIGInvalido");
						insertResponseDTO.setError(error);
					}

				} else {
					insertResponseDTO.setStatus(SigaConstants.KO);
					insertResponseDTO.setError(errorComprobaciones);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.guardarActDesigna() -> Se ha producido un error al guardar la actuación asociada a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO actualizarActDesigna(ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.actualizarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.actualizarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				Error errorComprobaciones = null;

				// Comprobamos si el módulo tiene activado el check de "Amite múltiples (columna
				// "complemento" en la tabla) para ver si acepta varias acreditaciones
				ScsProcedimientosKey procedimientoskey = new ScsProcedimientosKey();
				procedimientoskey.setIdinstitucion(idInstitucion);
				procedimientoskey.setIdprocedimiento(actuacionDesignaItem.getIdProcedimiento());
				ScsProcedimientos procedimiento = scsProcedimientosMapper.selectByPrimaryKey(procedimientoskey);

				if (procedimiento.getComplemento() != null && procedimiento.getComplemento().equals("1")) {
					errorComprobaciones = comprobacionesActDesignaMultiples(actuacionDesignaItem, idInstitucion, true);
				} else {
					errorComprobaciones = comprobacionesActDesigna(actuacionDesignaItem, idInstitucion, true);
				}

				if (errorComprobaciones.getCode().toString().equals("200")) {

					boolean nigValido = true;

					if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
						nigValido = utilOficio.validaNIG(actuacionDesignaItem.getNig(), request);
					}

					if (nigValido) {
						int response = scsDesignacionesExtendsMapper.actualizarActDesigna(actuacionDesignaItem,
								Short.toString(idInstitucion), usuarios.get(0));

						if (response == 1) {
							updateResponseDTO.setStatus(SigaConstants.OK);
							updateResponseDTO.setId(actuacionDesignaItem.getNumeroAsunto());
						}

						if (response == 0) {
							updateResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.error(
									"DesignacionesServiceImpl.actualizarActDesigna() -> Se ha producido un error al actualizar la actuación asociada a la designación");
							error.setCode(500);
							error.setDescription("general.mensaje.error.bbdd");
							updateResponseDTO.setError(error);
						}

						ScsDesignaKey scsDesignaKey = new ScsDesignaKey();
						scsDesignaKey.setIdturno(Integer.valueOf(actuacionDesignaItem.getIdTurno()));
						scsDesignaKey.setNumero(Long.valueOf(actuacionDesignaItem.getNumero()));
						scsDesignaKey.setAnio(Short.valueOf(actuacionDesignaItem.getAnio()));
						scsDesignaKey.setIdinstitucion(idInstitucion);

						ScsDesigna scsDesigna = scsDesignaMapper.selectByPrimaryKey(scsDesignaKey);

						if (scsDesigna != null) {

							if (scsDesigna.getIdjuzgado() == null
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdJuzgado())) {
								scsDesigna.setIdjuzgado(Long.valueOf(actuacionDesignaItem.getIdJuzgado()));
							}

							if (UtilidadesString.esCadenaVacia(scsDesigna.getIdprocedimiento())
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdProcedimiento())) {
								scsDesigna.setIdprocedimiento(actuacionDesignaItem.getIdProcedimiento());
							}

							if (scsDesigna.getIdpretension() == null
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPretension())) {
								scsDesigna.setIdpretension(Short.valueOf(actuacionDesignaItem.getIdPretension()));
							}

							if (UtilidadesString.esCadenaVacia(scsDesigna.getNumprocedimiento())
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNumProcedimiento())) {
								scsDesigna.setNumprocedimiento(actuacionDesignaItem.getNumProcedimiento());
							}

							if (UtilidadesString.esCadenaVacia(scsDesigna.getNig())
									&& !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
								scsDesigna.setNig(actuacionDesignaItem.getNig());
							}

							scsDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsDesigna.setFechamodificacion(new Date());

							int response2 = scsDesignaMapper.updateByPrimaryKeySelective(scsDesigna);

							if (response2 == 1) {
								updateResponseDTO.setStatus(SigaConstants.OK);
							}

							if (response2 == 0) {
								updateResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.error(
										"DesignacionesServiceImpl.actualizarActDesigna() -> Se ha producido un error al actualizar los datos de la designación");
								error.setCode(400);
								error.setDescription("general.mensaje.error.bbdd");
								updateResponseDTO.setError(error);
							}

						}
					} else {
						LOGGER.error(
								"DesignacionesServiceImpl.actualizarActDesigna() -> Se ha producido un error al actualizar los datos de la designación, NIG no válido");
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setDescription("justiciaGratuita.oficio.designa.NIGInvalido");
						updateResponseDTO.setError(error);
					}

				} else {
					updateResponseDTO.setStatus(SigaConstants.KO);
					updateResponseDTO.setError(errorComprobaciones);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.actualizarActDesigna() -> Se ha producido un error al actualizar la actuación asociada a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO createDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("createDesigna() ->  Entrada al servicio para insertar designacion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		String codigoDesigna = "";
		ScsDesigna designa = new ScsDesigna();
		Boolean letradoDesigSinTurno = Boolean.FALSE;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);

			LOGGER.info(
					"createDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() >= 0) {
				try {

					designa.setIdinstitucion(idInstitucion);
					designa.setIdturno(designaItem.getIdTurno());

					Calendar cal = Calendar.getInstance();
					short year = (short) cal.get(Calendar.YEAR);
					designa.setAnio(year);

					// CALCULO CAMPO CODIGO (NUMERO EN FRONT)
					codigoDesigna = scsDesignacionesExtendsMapper.obtenerCodigoDesigna(String.valueOf(idInstitucion),
							String.valueOf(designaItem.getAno()));

					// Limitacion campo numero en updateDesigna
					// Obtenemos el parametro de limite para el campo CODIGO en BBDD
					StringDTO parametros = new StringDTO();
					Integer longitudDesigna;

					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
							idInstitucion.toString());

					// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
					// la institucion 0
					if (parametros != null && parametros.getValor() != null) {
						longitudDesigna = Integer.parseInt(parametros.getValor());
					} else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
								"0");
						longitudDesigna = Integer.parseInt(parametros.getValor());
					}

					if (codigoDesigna == null || codigoDesigna.isEmpty()) {
						codigoDesigna = "1";
					}

					// Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
					while (codigoDesigna.length() < longitudDesigna) {
						codigoDesigna = "0" + codigoDesigna;
					}
					designa.setCodigo(codigoDesigna);

					// Fecha entrada es el que se muestra en el form. Que nos llega en el campo
					// Fecha Alta.
					SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
					String fecha = dt.format(designaItem.getFechaAlta());
					Date FechaFormateada = dt.parse(fecha);
					designa.setFechaentrada(FechaFormateada);
					designa.setFechamodificacion(new Date());
					designa.setUsumodificacion(usuario.getIdusuario());
					designa.setEstado("V");
					designa.setFechaestado(new Date());
					designa.setFechaalta(new Date());
					// CALCULO CAMPO NUMERO

					// Limitacion campo numero en updateDesigna

					// Obtenemos el ultimo numero + 1
					String numeroDesigna = scsDesignacionesExtendsMapper
							.obtenerNumeroDesigna(String.valueOf(idInstitucion), String.valueOf(designaItem.getAno()));

					if (numeroDesigna == null || numeroDesigna.isEmpty()) {
						numeroDesigna = "1";
					}

					// Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
					/*
					 * while (numeroDesigna.length() < longitudDesigna) { numeroDesigna = "0" +
					 * numeroDesigna; }
					 */
					designa.setNumero(Long.parseLong(numeroDesigna));

					if (designaItem.getIdTipoDesignaColegio() != 0) {
						designa.setIdtipodesignacolegio((short) designaItem.getIdTipoDesignaColegio());
					}

					designa.setArt27(designaItem.getArt27());

					// SCS_INSCRIPCIONTURNO por idPersona idinstitucion idturno.
					ScsDesignasletrado designaLetrado = new ScsDesignasletrado();
					String numeroColegiado = designaItem.getNumColegiado();
					if (StringUtils.isEmpty(numeroColegiado) && !designaItem.getArt27().equals("1")) {
						// Se realiza busqueda en la cola de oficio

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

						String fechaform = sdf.format(designaItem.getFechaAlta());

						LetradoInscripcionItem letradoAlgoritmoSeleccion = this.getLetradoTurno(
								idInstitucion.toString(), String.valueOf(designaItem.getIdTurno()), fechaform, usuario,
								designaLetrado);

						if (letradoAlgoritmoSeleccion == null) {
							response = 0;
							error.setCode(404);
							error.setDescription("general.mensaje.error.bbdd");
							insertResponseDTO.setStatus(SigaConstants.KO);
							insertResponseDTO.setError(error);
							LOGGER.info("No se ha encontrado letrado en la cola");
							return insertResponseDTO;
						}

//						//Anotamos log
						short mes = (short) cal.get(Calendar.MONTH);
						short dia = (short) cal.get(Calendar.DAY_OF_MONTH);
						LOGGER.info("Buscando letrado para el turno " + designaItem.getIdTurno() + " en la fecha " + dia
								+ "/" + mes + "/" + year);
//						
//						//MANUAL = 0, LETRADODELTURNO = 1 si el sistema elige automáticamente de la cola
//						
						designaLetrado.setIdinstitucion(idInstitucion);
						designaLetrado.setIdturno(designaItem.getIdTurno());
						designaLetrado.setAnio(year);
						designaLetrado.setNumero(Long.parseLong(numeroDesigna));
						designaLetrado.setFechadesigna(FechaFormateada);
						designaLetrado.setFechamodificacion(new Date());
						designaLetrado.setUsumodificacion(usuario.getIdusuario());
						designaLetrado.setIdpersona(letradoAlgoritmoSeleccion.getIdpersona());
						designaLetrado.setManual((short) 0);
						designaLetrado.setLetradodelturno("1");

					} else {
						// 1) Averiguamos el colegiado a partir de su numero para obtener el IdPersona
						CenColegiadoExample cenColegExample = new CenColegiadoExample();
						cenColegExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andNcolegiadoEqualTo(numeroColegiado);
						List<CenColegiado> resCenColegiado = this.cenColegiadoExtendsMapper
								.selectByExample(cenColegExample);
						CenColegiado cenColeg = CollectionUtils.isNotEmpty(resCenColegiado) ? resCenColegiado.get(0)
								: null;

						if (cenColeg != null) {
							// 2) Averiguamos el/los turnos inscritos del colegiado elegido manualmente
							// (buscador)
							ScsInscripcionturnoExample insTurnoExample = new ScsInscripcionturnoExample();
							insTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andFechadenegacionIsNull().andFechasolicitudIsNotNull()
									.andFechavalidacionIsNotNull().andFechabajaIsNull()
									.andIdpersonaEqualTo(cenColeg.getIdpersona());

							List<ScsInscripcionturno> resInscLetrado = this.scsInscripcionesTurnoExtendsMapper
									.selectByExample(insTurnoExample);

							if (CollectionUtils.isNotEmpty(resInscLetrado)) {
								// 3) Recuperamos las entidades "Turno" iteradas para comparar si estuviera
								// inscrito a alguno de tipo tramitacion (idtipoturno = 1)

								for (ScsInscripcionturno scsTurnoInscrito : resInscLetrado) {
									ScsTurnoExample turnoExample = new ScsTurnoExample();
									turnoExample.createCriteria().andIdturnoEqualTo(scsTurnoInscrito.getIdturno());
									List<ScsTurno> turnoInscritoIt = this.scsTurnosExtendsMapper
											.selectByExample(turnoExample);

									if (CollectionUtils.isNotEmpty(turnoInscritoIt)
											&& turnoInscritoIt.get(0).getIdtipoturno() != null
											&& Short.toUnsignedInt(turnoInscritoIt.get(0)
													.getIdtipoturno()) != SigaConstants.TIPO_TURNO_DESIGNACION
															.intValue()) {
										error.setCode(HttpStatus.NOT_ACCEPTABLE.value());
										error.setDescription(
												"justiciaGratuita.oficio.designa.designacionletradoturnotramitacion");
										insertResponseDTO.setStatus(SigaConstants.KO);
										insertResponseDTO.setError(error);
										return insertResponseDTO;
									}
								}
							} else {
								letradoDesigSinTurno = Boolean.TRUE;
							}
						} else {
							LOGGER.info(
									"+++ DesignacionesServiceImple/createDesigna() -> No se ha encontrado 'CenColegiado' para el NCOLEGIADO = "
											+ numeroColegiado);
						}

						designaLetrado.setIdinstitucion(idInstitucion);
						designaLetrado.setIdturno(designaItem.getIdTurno());
						designaLetrado.setAnio(year);
						designaLetrado.setNumero(Long.parseLong(numeroDesigna));
						designaLetrado.setFechadesigna(FechaFormateada);
						designaLetrado.setFechamodificacion(new Date());
						designaLetrado.setUsumodificacion(usuario.getIdusuario());
						designaLetrado.setManual((short) 1);
						designaLetrado.setLetradodelturno("1");

						String idPersona = null;

						if (idInstitucion == designaItem.getIdInstitucion() || designaItem.getIdInstitucion() == 0) {
							idPersona = scsDesignacionesExtendsMapper.obtenerIdPersonaByNumCol(idInstitucion.toString(),
									numeroColegiado);

							if (idPersona == null || idPersona == "") {
								idPersona = scsDesignacionesExtendsMapper.obtenerIdPersonaByNumComunitario(
										designa.getIdinstitucion().toString(), numeroColegiado);
							}
//
//							if(idPersona == null || idPersona == "") {
//								idPersona = scsDesignacionesExtendsMapper
//										.obtenerIdPersonaByNumColNColegiado(designaItem.getNif());
//								idPersona = scsDesignacionesExtendsMapper.obtenerNumNoColegiado(designa.getIdinstitucion().toString(), idPersona);
//
//							}
						} /*
							 * else { idPersona = scsDesignacionesExtendsMapper
							 * .obtenerIdPersonaByNumColNColegiado(designaItem.getNif()); if(idPersona ==
							 * null || idPersona.isEmpty()) { idPersona = scsDesignacionesExtendsMapper
							 * .obtenerNumNoColegiado(String.valueOf(designaItem.getIdInstitucion()),
							 * idPersona); } }
							 */

						InsertResponseDTO responseNColegiado = new InsertResponseDTO();
						if (idPersona == null || idPersona == "") {
							idPersona = scsDesignacionesExtendsMapper
									.obtenerIdPersonaByNumColNColegiado(designaItem.getNif());
							if (idPersona == null || idPersona.isEmpty()) {
								idPersona = scsDesignacionesExtendsMapper.obtenerNumNoColegiado(
										String.valueOf(designaItem.getIdInstitucion()), idPersona);
							}
							NoColegiadoItem noColegiadoItem = new NoColegiadoItem();
							noColegiadoItem.setIdTipoIdentificacion("10");
							noColegiadoItem.setNif(designaItem.getNif());
							noColegiadoItem.setIdInstitucion(designa.getIdinstitucion().toString());
							noColegiadoItem.setIdPersona(idPersona);
							noColegiadoItem.setNombre(designaItem.getNombreColegiado());
							noColegiadoItem.setSoloNombre(designaItem.getNombreColegiado());
							noColegiadoItem.setApellidos1(designaItem.getApellido1Colegiado());
							noColegiadoItem.setApellidos2(designaItem.getApellido2Colegiado());
							noColegiadoItem.setIdLenguaje("1");
							noColegiadoItem.setidTratamiento("2");
							noColegiadoItem.setComisiones("0");
							responseNColegiado = fichaDatosGenerales.createNoColegiado(noColegiadoItem, request);
							DatosDireccionesDTO responseDirecciones = new DatosDireccionesDTO();
							DatosDireccionesItem direccionCensoColegiado = new DatosDireccionesItem();
							if (responseNColegiado != null && responseNColegiado.getStatus().equals("OK")) {
								idPersona = responseNColegiado.getId();
								DatosDireccionesSearchDTO busquedaDirecciones = new DatosDireccionesSearchDTO();
								busquedaDirecciones.setIdPersona(idPersona);
								busquedaDirecciones.setHistorico(false);
								busquedaDirecciones.setIdInstitucion(String.valueOf(designaItem.getIdInstitucion()));
								responseDirecciones = tarjetaDatosDireccionesService.datosDireccionesSearch(1,
										busquedaDirecciones, request);
							}
//							designa.setIdinstitucion(idInstitucion)
							if (responseDirecciones.getDatosDireccionesItem() != null
									&& responseDirecciones.getDatosDireccionesItem().size() > 0) {
								for (DatosDireccionesItem direccion : responseDirecciones.getDatosDireccionesItem()) {
									if (direccion.getTipoDireccion().contains("CensoWeb")) {
										direccionCensoColegiado = direccion;
									}
								}
//								UpdateResponseDTO response = tarjetaDatosDireccionesService.updateDirection(datosDirecciones, request);
								InsertResponseDTO responseDireccion = tarjetaDatosDireccionesService
										.createDirection(direccionCensoColegiado, request);
							}
						}
						List<InscripcionesItem> listaInscripciones = new ArrayList<InscripcionesItem>();
						if (responseNColegiado.getId() != null) {
							listaInscripciones = scsInscripcionesTurnoExtendsMapper.obtenerColegiadoInscritoTurno(
									idInstitucion, String.valueOf(designaItem.getIdTurno()),
									responseNColegiado.getId());
						} else {
							listaInscripciones = scsInscripcionesTurnoExtendsMapper.obtenerColegiadoInscritoTurno(
									idInstitucion, String.valueOf(designaItem.getIdTurno()), idPersona);
						}

						if (listaInscripciones != null && listaInscripciones.size() > 0) {
//							MANUAL = 1, LETRADODELTURNO = 1 cuando el colegiado elegido manualmente por el usuario esté inscrito en el turno (en ese momento)
							designaLetrado.setLetradodelturno("1");
						} else {
//							MANUAL = 1, LETRADODELTURNO = 0 cuando el colegiado elegido manualmente por el usuario NO esté inscrito en el turno
							designaLetrado.setLetradodelturno("0");
						}

						designaLetrado.setIdpersona(Long.parseLong(idPersona));

					}

					// Marcamos por defecto la partida presupuestaria del turno
					ScsTurnoExample scsTurnoExample = new ScsTurnoExample();
					scsTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(designa.getIdturno());

					List<ScsTurno> listaTurnos = scsTurnoMapper.selectByExample(scsTurnoExample);

					if (!listaTurnos.isEmpty() && null != listaTurnos.get(0).getIdpartidapresupuestaria()) {
						designa.setIdpartidapresupuestaria(listaTurnos.get(0).getIdpartidapresupuestaria());
					}

					LOGGER.info(
							"createDesigna() / scsDesignaMapper.insert() -> Entrada a scsDesignaMapper para insertar la designacion");

					response = scsDesignaMapper.insert(designa);

					LOGGER.info(
							"createDesigna() / scsDesignaMapper.insert() -> Salida de scsDesignaMapper para insertar la designacion");

					LOGGER.info(
							"createDesigna() / scsDesignasletradoMapper.insert() -> Entrada a scsDesignasletradoMapper para insertar designaLetrado");

					scsDesignasletradoMapper.insert(designaLetrado);

					LOGGER.info(
							"createDesigna() / scsDesignasletradoMapper.insert() -> Salida de scsDesignasletradoMapper para insertar designaLetrado");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
					insertResponseDTO.setError(error);
					LOGGER.error(e);
					return insertResponseDTO;
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if (error.getDescription() == null) {
				error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(codigoDesigna);
			error.setCode(letradoDesigSinTurno ? 202 : 200);
			error.setDescription(letradoDesigSinTurno ? "justiciaGratuita.oficio.designa.designacionletradosinturno"
					: "general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		if (designa != null && designa.getCodigo() != null) {
			LOGGER.info("createDesigna() -> Salida del servicio para insertar designa Codigo: " + designa.getCodigo());
		} else {
			LOGGER.info("createDesigna() -> Salida del servicio para insertar designa");
		}

		return insertResponseDTO;

	}

	@Override
	public DeleteResponseDTO deleteDesigna(List<DesignaItem> item, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;
		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

//				List<Integer> responses = new ArrayList<>();

				for (DesignaItem designa : item) {

					ScsDesignasletradoExample exampleDesignasLetrado = new ScsDesignasletradoExample();

					exampleDesignasLetrado.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(designa.getIdTurno()).andAnioEqualTo((short) designa.getAno())
							.andNumeroEqualTo(new Long(designa.getNumero()));

					List<ScsDesignasletrado> listadoLetradosAsignadosADesigna = scsDesignasletradoMapper
							.selectByExample(exampleDesignasLetrado);

					if (listadoLetradosAsignadosADesigna.size() > 0) {
						for (ScsDesignasletrado letradoAsignadoADesigna : listadoLetradosAsignadosADesigna) {
							int responseEliminacionLetradoAsignadoADesigna = scsDesignasletradoMapper
									.deleteByPrimaryKey(letradoAsignadoADesigna);

							if (responseEliminacionLetradoAsignadoADesigna == 0) {
								LOGGER.error("No se pudo eliminar el letrado con idpersona: "
										+ letradoAsignadoADesigna.getIdpersona()
										+ " asignado a la designa con idinstitucion: " + idInstitucion + ", idturno: "
										+ letradoAsignadoADesigna.getIdturno() + ", anio: "
										+ letradoAsignadoADesigna.getAnio() + ", numero: "
										+ letradoAsignadoADesigna.getNumero());
							} else {
								LOGGER.error("See pudo eliminar el letrado con idpersona: "
										+ letradoAsignadoADesigna.getIdpersona()
										+ " asignado a la designa con idinstitucion: " + idInstitucion + ", idturno: "
										+ letradoAsignadoADesigna.getIdturno() + ", anio: "
										+ letradoAsignadoADesigna.getAnio() + ", numero: "
										+ letradoAsignadoADesigna.getNumero());
							}
						}
					}

					ScsDesignaKey key = new ScsDesignaKey();
					Short anio = new Short((short) designa.getAno());
					key.setAnio(anio);
					key.setNumero(new Long(designa.getNumero()));
					key.setIdturno(designa.getIdTurno());
					key.setIdinstitucion(idInstitucion);

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Entrada a ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

					response = scsDesignacionesExtendsMapper.deleteByPrimaryKey(key);

					LOGGER.info(
							"deleteInteresado() / ScsDefendidosdesignaMapper.deleteByPrimaryKey() -> Salida de ScsDefendidosdesignaMapper para eliminar los contrarios seleccionados");

				}
				deleteResponseDTO.setStatus(SigaConstants.OK);

				if (response == 0) {
					error.setCode(400);
					error.setDescription("areasmaterias.materias.ficha.eliminarError");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				} else {
					error.setCode(200);
					error.setDescription("general.message.registro.actualizado");
				}

				deleteResponseDTO.setError(error);

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarActDesigna() -> Se ha producido un error al anular las actuaciones asociadas a la designación",
					e);
			error.setCode(500);
			if (!e.getCause().getMessage().contains("ORA-02292")) {
				error.setDescription("general.mensaje.error.bbdd");
			} else {
				error.setDescription("sjcs.designaciones.enuso");
			}
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		return deleteResponseDTO;
	}

	public LetradoInscripcionItem getLetradoTurno(String idInstitucion, String idTurno, String fechaForm,
			AdmUsuarios usuario, ScsDesignasletrado designaLetradoVieja) throws java.lang.Exception {

		// Variables generales
		ArrayList<String> diasGuardia; // Periodo o dia de guardia para rellenar con letrado

		List<BajasTemporalesItem> hmBajasTemporalesList;

		HashMap<Long, TreeMap<String, BajasTemporalesItem>> hmBajasTemporales; // variable siga classique

		List<LetradoInscripcionItem> hmPersonasConSaltosList; // Lista de saltos

		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos; // variable siga classique

		List<LetradoInscripcionItem> alCompensaciones; // Lista de compensaciones

		ArrayList<LetradoInscripcionItem> alLetradosOrdenados; // Cola de letrados en la guardia

		Puntero punteroListaLetrados;

		LetradoInscripcionItem unLetrado;

		LetradoInscripcionItem letradoGuardia;

		try {

			// obteniendo bajas temporales por letrado
			hmBajasTemporalesList = scsDesignacionesExtendsMapper.getLetradosDiasBajaTemporalTurno(idInstitucion,
					idTurno, fechaForm);

			hmBajasTemporales = this.getLetradosDiasBajaTemporalTurnoHash(hmBajasTemporalesList);

			// obteniendo saltos
			hmPersonasConSaltosList = scsDesignacionesExtendsMapper.getSaltos(idInstitucion, idTurno, null);

			hmPersonasConSaltos = this.getSaltosHash(hmPersonasConSaltosList, Integer.parseInt(idInstitucion),
					Integer.parseInt(idTurno), null, usuario);

			diasGuardia = new ArrayList<String>();
			diasGuardia.add(fechaForm);

			// obteniendo cola de letrados
			punteroListaLetrados = new Puntero();
			alLetradosOrdenados = (ArrayList<LetradoInscripcionItem>) this.getColaTurno(Integer.parseInt(idInstitucion),
					Integer.parseInt(idTurno), fechaForm, false, usuario);

			if (alLetradosOrdenados == null || alLetradosOrdenados.size() == 0) {
				letradoGuardia = null;
				return letradoGuardia;

			}

			// obteniendo las compensaciones. Se obtienen dentro de este
			// bucle, ya que si hay incompatibilidades señade una compensacion
			alCompensaciones = this.getLogicaCompensaciones(idInstitucion, idTurno, fechaForm);

			// obteniendo el letrado a asignar.
			// ATENCION: este metodo es el nucleo del proceso
			letradoGuardia = getSiguienteLetradoTurno(alCompensaciones, alLetradosOrdenados, punteroListaLetrados,
					diasGuardia, hmPersonasConSaltos, hmBajasTemporales, idInstitucion, idTurno, null, null, usuario);

			// Si son la misma guardia se crea una compensacion del Letrado y se obtiene el
			// siguiente Letrado. (JGC)
			/*
			 * if (letradoGuardia.getIdpersona().equals(designaLetradoVieja.getIdpersona()))
			 * { // Actualizamos la guardia y la llevamos a la última. if
			 * (letradoGuardia.getSaltoocompensacion() == null) { int punteroUltimo = 0; if
			 * (punteroListaLetrados.getValor() == 0) punteroUltimo =
			 * alLetradosOrdenados.size() - 1; else punteroUltimo =
			 * punteroListaLetrados.getValor() - 1; unLetrado =
			 * alLetradosOrdenados.get(punteroUltimo);
			 * scsDesignacionesExtendsMapper.cambiarUltimoCola(unLetrado.getIdinstitucion().
			 * toString(), unLetrado.getIdTurno().toString(),
			 * unLetrado.getIdpersona().toString(),
			 * unLetrado.getInscripcionTurno().getFechasolicitud(), usuario); } // Obtenemos
			 * de nuevo el siguiente Letrado. // obteniendo el letrado a asignar. //
			 * ATENCION: este metodo es el nucleo del proceso letradoGuardia =
			 * getSiguienteLetradoTurno(alCompensaciones, alLetradosOrdenados,
			 * punteroListaLetrados, diasGuardia, hmPersonasConSaltos, hmBajasTemporales,
			 * idInstitucion, idTurno, null, null, usuario);
			 * 
			 * }
			 */

			if (letradoGuardia == null) {
				throw new Exception("gratuita.modalRegistro_DefinirCalendarioGuardia.literal.errorLetradosSuficientes");
			}

			// actualizando el ultimo letrado en la guardia solo si no es de la lista de
			// compensaciones
			if (letradoGuardia.getSaltoocompensacion() == null) {
				int punteroUltimo = 0;

				if (punteroListaLetrados.getValor() == 0)
					punteroUltimo = alLetradosOrdenados.size() - 1;
				else
					punteroUltimo = punteroListaLetrados.getValor() - 1;

				unLetrado = alLetradosOrdenados.get(punteroUltimo);

				scsDesignacionesExtendsMapper.cambiarUltimoCola(unLetrado.getIdinstitucion().toString(),
						unLetrado.getIdTurno().toString(), unLetrado.getIdpersona().toString(),
						unLetrado.getInscripcionTurno().getFechasolicitud(), usuario);

			}

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}

		return letradoGuardia;
	}

	public CenPersonaItem getPersonaPorId(String idPersona) throws Exception {

		try {

			CenPersona cenPersona = null;
			CenPersonaItem cenPersonaItem = new CenPersonaItem();

			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.parseLong(idPersona));

			List<CenPersona> personas = cenPersonaMapper.selectByExample(cenPersonaExample);

			if ((personas != null) && personas.size() == 1) {
				cenPersona = personas.get(0);
			}

			if (cenPersona != null) {
				cenPersonaItem.setApellidos1(cenPersona.getApellidos1());
				cenPersonaItem.setApellidos2(cenPersona.getApellidos2());
				cenPersonaItem.setFallecido(cenPersona.getFallecido());
				cenPersonaItem.setFechanacimiento(cenPersona.getFechanacimiento());
				cenPersonaItem.setIdestadocivil(cenPersona.getIdestadocivil());
				cenPersonaItem.setIdpersona(cenPersona.getIdpersona());
				cenPersonaItem.setIdtipoidentificacion(cenPersona.getIdtipoidentificacion());
				cenPersonaItem.setNaturalde(cenPersona.getNaturalde());
				cenPersonaItem.setNifcif(cenPersona.getNifcif());
				cenPersonaItem.setNombre(cenPersona.getNombre());
				cenPersonaItem.setSexo(cenPersona.getSexo());

			}

			return cenPersonaItem;
		} catch (Exception e) {
			throw new Exception("Error al recuperar los datos", e);
		}
	}

	public List<LetradoInscripcionItem> getColaTurno(Integer idInstitucion, Integer idTurno, String fecha,
			boolean quitarSaltos, AdmUsuarios usr) throws Exception {
		try {

			ArrayList<LetradoInscripcionItem> colaLetrados = new ArrayList<LetradoInscripcionItem>();
//			ScsInscripcionTurnoAdm insadm = new ScsInscripcionTurnoAdm(usr);
//			ScsSaltosCompensacionesAdm saladm = new ScsSaltosCompensacionesAdm(usr);
//			ScsOrdenacionColasAdm ordenacionColasAdm = new ScsOrdenacionColasAdm(usr);

			// obteniendo la guardia
			ScsTurnoKey scsTurnoKey = new ScsTurnoKey();
			scsTurnoKey.setIdinstitucion(Short.valueOf(idInstitucion.toString()));
			scsTurnoKey.setIdturno(idTurno);

			ScsTurno beanTurno = scsTurnoMapper.selectByPrimaryKey(scsTurnoKey);

			Integer idOrdenacionColas = beanTurno.getIdordenacioncolas();
			if (idOrdenacionColas == null)
				throw new Exception("messages.general.error");

			Long idPersonaUltimo = beanTurno.getIdpersonaUltimo();

			// obteniendo ordenacion del turno
			String orden = this.getOrderBy(idOrdenacionColas.toString());

			InscripcionTurnoItem ultimoAnterior;

			// obteniendo ultimo apuntado de la guardia
			if (idPersonaUltimo == null) {
				ultimoAnterior = null;
			} else {
				ultimoAnterior = new InscripcionTurnoItem(Short.valueOf(idInstitucion.toString()), idTurno,
						idPersonaUltimo, beanTurno.getFechasolicitudUltimo());
			}

			Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String fechaBBDD2 = formatter.format(fechaBBDD);
			// obteniendo lista de letrados (ordenada)
			List<InscripcionTurnoItem> listaLetrados = scsDesignacionesExtendsMapper
					.getColaTurnoBBDD(idInstitucion.toString(), idTurno.toString(), fechaBBDD2, orden);

//			Format formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			
//			Date fechaprueba = listaLetrados.get(0).getFechasolicitud();
//			LOGGER.info(formatter2.format(fechaprueba));

			if (listaLetrados == null || listaLetrados.size() == 0) {
				return colaLetrados;
			}

			InscripcionTurnoItem punteroInscripciones = null;
			boolean foundUltimo;
			LetradoInscripcionItem letradoTurno;
			if (ultimoAnterior == null) {
				// si no existe ultimo colegiado, se empieza la cola desde el
				// primero en la lista
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = (InscripcionTurnoItem) listaLetrados.get(i);
					if (punteroInscripciones.getEstado().equals("1")) {
						colaLetrados.add(new LetradoInscripcionItem(punteroInscripciones));
					}
				}
			} else {
				// ordenando la cola en funcion del idPersonaUltimo guardado
				List<LetradoInscripcionItem> colaAuxiliar = new ArrayList<LetradoInscripcionItem>();
				foundUltimo = false;
				for (int i = 0; i < listaLetrados.size(); i++) {
					punteroInscripciones = listaLetrados.get(i);

					// insertando en la cola si la inscripcion esta activa
					if (punteroInscripciones.getEstado().equals("1")) {
						// El primero que se anyade es el siguiente al ultimo
						if (foundUltimo) {
							colaLetrados.add(new LetradoInscripcionItem(punteroInscripciones));
						} else {
							colaAuxiliar.add(new LetradoInscripcionItem(punteroInscripciones));
						}

						// revisando si se encontro ya al ultimo
						if (!foundUltimo
								&& (punteroInscripciones.getIdpersona().equals(ultimoAnterior.getIdpersona()))) {
							foundUltimo = true;
						}
					}

				}
				colaLetrados.addAll(colaAuxiliar);
			}

			// quitando letrados de la cola si tienen saltos
			if (quitarSaltos) {
				List<LetradoInscripcionItem> personasConSaltosList = scsDesignacionesExtendsMapper
						.getSaltos(idInstitucion.toString(), idTurno.toString(), null);
				HashMap<Long, ArrayList<LetradoInscripcionItem>> personasConSaltos = this
						.getSaltosHash(personasConSaltosList, idInstitucion, idTurno, null, usr);
				for (Iterator<LetradoInscripcionItem> iter = colaLetrados.iterator(); iter.hasNext();) {
					letradoTurno = (LetradoInscripcionItem) iter.next();
					if (personasConSaltos.get(letradoTurno.getIdpersona()) != null) {
						iter.remove();
					}
				}
			}

			return colaLetrados;

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception("Error al ejecutar getColaTurno()", e);
		}
	}

	private HashMap<Long, TreeMap<String, BajasTemporalesItem>> getLetradosDiasBajaTemporalTurnoHash(
			List<BajasTemporalesItem> hmBajasTemporalesList) throws java.lang.Exception {

		HashMap<Long, TreeMap<String, BajasTemporalesItem>> mSalida = new HashMap<Long, TreeMap<String, BajasTemporalesItem>>();

		for (int i = 0; i < hmBajasTemporalesList.size(); i++) {

			BajasTemporalesItem bajasBean = hmBajasTemporalesList.get(i);

			Long idPersona = new Long(bajasBean.getIdpersona());
			Date fechaBT = bajasBean.getFechabt();

			TreeMap<String, BajasTemporalesItem> bajasDePersona = (TreeMap<String, BajasTemporalesItem>) mSalida
					.get(idPersona);

			if (bajasDePersona != null)
				bajasDePersona.put(this.getFormatedDateShort("", fechaBT), bajasBean);
			else {
				bajasDePersona = new TreeMap<String, BajasTemporalesItem>();
				bajasDePersona.put(this.getFormatedDateShort("", fechaBT), bajasBean);
				mSalida.put(idPersona, bajasDePersona);
			}
		}

		return mSalida;
	}

	private String getFormatedDateShort(String lang, Date date) throws java.lang.Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String datFormat = null;

		if (date.toString() != null && !date.toString().trim().equals("")) {
			try {

				// DCG date = GstDate.getFechaLenguaje(lang, date);
				datFormat = sdf.format(date);
			} catch (Exception ex) {
				Exception exc = new Exception("THIS DATE " + date + " IS BAD FORMATED");
				// exc.setErrorCode("DATEFORMAT");
				throw exc;
			}

			if (lang.equalsIgnoreCase("EN")) {
				sdf.applyPattern("dd/MM/yyyy");
			} else {
				sdf.applyPattern("dd/MM/yyyy");
			}
		}
		return datFormat;
	}

	private String getApplicationFormatDate(String lang, String date) throws Exception {
		if (date == null || date.length() == 0)
			return null;
		String dat = "";
		Date oDate = null;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (date.length() == 11 && date.substring(2, 3).equals(".")) {
			String DATE_FORMAT_LOGBOOK = "dd.MMM.yyyy";
			if (lang.equalsIgnoreCase("EN")) {
				oDate = this.parseStringToDate(date, DATE_FORMAT_LOGBOOK, Locale.ENGLISH);
			} else {
				// Espanhol oDate = new
				// GstDate().parseStringToDate(date,DATE_FORMAT_LOGBOOK,Locale.ENGLISH);
			}
		} else if (date.length() == 20 && date.substring(4, 5).equals("-")) {
			String DATE_FORMAT_LOGBOOK = "dd.MMM.yyyy";
			if (lang.equalsIgnoreCase("EN")) {
				oDate = this.parseStringToDate(date, DATE_FORMAT_LOGBOOK, Locale.ENGLISH);
			} else {
				// Espanhol oDate=new
				// GstDate().parseStringToDate(date,DATE_FORMAT_LOGBOOK,Locale.ENGLISH);
			}
		} else {
			if (date.length() > 11) {
				// LONG
				if (lang.equalsIgnoreCase("EN")) {
					sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				} else {
					sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				}
			} else {
				// SHORT
				if (lang.equalsIgnoreCase("EN")) {
					sdf = new SimpleDateFormat("dd/MM/yyyy");
				} else {
					sdf = new SimpleDateFormat("dd/MM/yyyy");
				}
			}

			try {
				oDate = sdf.parse(date);
			} catch (Exception ex) {
				Exception exc = new Exception("THIS DATE " + date + " IS BAD FORMATED", ex);
				// exc.setErrorCode("DATEFORMAT");
				throw exc;
			}
		}
		if (oDate == null) {
			sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
			try {
				oDate = sdf.parse(date);
			} catch (Exception ex) {
				Exception exc = new Exception("THIS DATE " + date + " IS BAD FORMATED", ex);
				// exc.setErrorCode("DATEFORMAT");
				throw exc;
			}
		}

		sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
		dat = sdf.format(oDate);
		return dat;
	}

	private Date parseStringToDate(String date, String format, Locale locale) throws Exception {
		Date dat = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		try {
			dat = sdf.parse(date);
		} catch (Exception e) {
			Exception exc = new Exception("error en GstDate     :" + e.toString(), e);
			// exc.setErrorCode("DATEFORMAT");
			throw exc;
		}
		return dat;
	}

	private List<LetradoInscripcionItem> getLogicaCompensaciones(String idInstitucion, String idTurno, String fechaForm)
			throws Exception {

		List<LetradoInscripcionItem> compensaciones;
		List<LetradoInscripcionItem> alLetradosCompensados = new ArrayList<LetradoInscripcionItem>();

		LetradoInscripcionItem letradoSeleccionado;
		String idPersona;

		compensaciones = scsDesignacionesExtendsMapper.getCompensaciones(idInstitucion, idTurno, fechaForm);

		try {

			for (LetradoInscripcionItem elem : compensaciones) {

				idPersona = elem.getIdpersona().toString();

				Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(fechaForm);
				Format formatter = new SimpleDateFormat("dd/MM/yyyy");
				String fechaBBDD2 = formatter.format(fechaBBDD);

				InscripcionTurnoItem inscripcionTurno = scsDesignacionesExtendsMapper
						.getInscripcionTurnoActiva(idInstitucion.toString(), idTurno.toString(), idPersona, fechaBBDD2);

				if (inscripcionTurno == null) {
					continue;
				}

				letradoSeleccionado = new LetradoInscripcionItem();

				letradoSeleccionado.setIdpersona(elem.getIdpersona());
				letradoSeleccionado.setIdinstitucion(Short.valueOf(elem.getIdinstitucion().toString()));
				letradoSeleccionado.setIdSaltoCompensacion(elem.getIdSaltoCompensacion());
				letradoSeleccionado.setIdTurno(elem.getIdTurno());
				letradoSeleccionado.setInscripcionTurno(inscripcionTurno);
				letradoSeleccionado.setSaltoocompensacion("C");
				alLetradosCompensados.add(letradoSeleccionado);

			}

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception("Error al obtener letrados compensados  en BD.", e);
		}

		return alLetradosCompensados;
	}

	private HashMap<Long, ArrayList<LetradoInscripcionItem>> getSaltosHash(
			List<LetradoInscripcionItem> hmPersonasConSaltosList, Integer idInstitucion, Integer idTurno,
			Integer idGuardia, AdmUsuarios usuario) throws Exception {

		HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos = new HashMap<Long, ArrayList<LetradoInscripcionItem>>();
		ArrayList<LetradoInscripcionItem> alLetradosSaltados;

		for (int i = 0; i < hmPersonasConSaltosList.size(); i++) {

			LetradoInscripcionItem htFila = hmPersonasConSaltosList.get(i);

			Long idPersona = htFila.getIdpersona();

			LetradoInscripcionItem letradoSeleccionado = new LetradoInscripcionItem(
					this.getPersonaPorId(idPersona.toString()), idInstitucion, idTurno, idGuardia, "S");

			if (hmPersonasConSaltos.containsKey(idPersona))
				alLetradosSaltados = hmPersonasConSaltos.get(idPersona);
			else
				alLetradosSaltados = new ArrayList<LetradoInscripcionItem>();

			alLetradosSaltados.add(letradoSeleccionado);
			hmPersonasConSaltos.put(idPersona, alLetradosSaltados);
		}

		return hmPersonasConSaltos;
	}

	private String getOrderBy(String idOrdenacionColas) throws Exception {

		// obteniendo el orden
		ScsOrdenacioncolas ordenBean = scsOrdenacioncolasMapper.selectByPrimaryKey(Integer.parseInt(idOrdenacionColas));

		Integer apellidos = Integer.parseInt(ordenBean.getAlfabeticoapellidos().toString());
		Integer antiguedad = Integer.parseInt(ordenBean.getAntiguedadcola().toString());
		Integer fechanacimiento = Integer.parseInt(ordenBean.getFechanacimiento().toString());
		Integer numerocolegiado = Integer.parseInt(ordenBean.getNumerocolegiado().toString());

		// calculando order by
		String orden = "";
		for (int i = 4; i > 0; i--) {
			if (Math.abs(apellidos) == i) {
				orden += "ALFABETICOAPELLIDOS";
				if (Math.abs(apellidos) != apellidos)
					orden += " desc";
				orden += "," + "NOMBRE";
				if (Math.abs(apellidos) != apellidos)
					orden += " desc";
				orden += ", ";
			}
			if (Math.abs(antiguedad) == i) {
				orden += "ANTIGUEDADCOLA";
				if (Math.abs(antiguedad) != antiguedad)
					orden += " desc";
				orden += ", ";
			}
			if (Math.abs(fechanacimiento) == i) {
				orden += "FECHANACIMIENTO";
				if (Math.abs(fechanacimiento) != fechanacimiento)
					orden += " desc";
				orden += ", ";
			}
			if (Math.abs(numerocolegiado) == i) {
				// orden += "to_number("+ScsOrdenacionColasBean.C_NUMEROCOLEGIADO+")"; esta
				// linea queda comentada para que se vea que el to_number es peligroso
				orden += "lpad(" + "NUMEROCOLEGIADO" + ",20,'0')";
				if (Math.abs(numerocolegiado) != numerocolegiado)
					orden += " desc";
				orden += ", ";
			}
		}

		if (orden.equals("")) {
			// En el caso de que no se haya establecido orden, se ordena por antiguedad en
			// la cola
			orden = " " + "ANTIGUEDADCOLA" + " ";
			orden += ", ";
		}
		orden = orden.substring(0, orden.length() - 2); // quitando ultima coma

		return orden;
	}

	private LetradoInscripcionItem getSiguienteLetradoTurno(List<LetradoInscripcionItem> alCompensaciones,
			ArrayList<LetradoInscripcionItem> alLetradosOrdenados, Puntero punteroLetrado,
			ArrayList<String> diasGuardia, HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String, BajasTemporalesItem>> hmBajasTemporales, String idInstitucion, String idTurno,
			String idGuardia, String idCalendarioGuardias, AdmUsuarios usuario) throws Exception {

		LetradoInscripcionItem letradoGuardia, auxLetradoSeleccionado;

		letradoGuardia = null;

		// recorriendo compensaciones
		if (alCompensaciones != null && alCompensaciones.size() > 0) {

			// Si hay compensaciones se coge el letrado con la compensación mas antigua.
			LOGGER.info("Buscando compensaciones…");

			Iterator<LetradoInscripcionItem> iterador = alCompensaciones.iterator();
			while (iterador.hasNext()) {
				auxLetradoSeleccionado = (LetradoInscripcionItem) iterador.next();
				// vale
				if (comprobarRestriccionesLetradoCompensadoTurno(auxLetradoSeleccionado, diasGuardia, iterador, null,
						hmBajasTemporales, idInstitucion, idTurno, idGuardia, idCalendarioGuardias, usuario)) {
					letradoGuardia = auxLetradoSeleccionado;
					LOGGER.info("Letrado encontrado. idPersona: " + letradoGuardia.getIdpersona());
					break;
				}
			}
		}
		if (letradoGuardia != null)
			return letradoGuardia;

		// recorriendo la cola
		if (alLetradosOrdenados != null && alLetradosOrdenados.size() > 0) {

//			//Si no hay compensaciones se coge el primer letrado de la cola 
			LOGGER.info("Buscando en la cola");

			int fin = punteroLetrado.getValor();
			do {
				auxLetradoSeleccionado = (LetradoInscripcionItem) alLetradosOrdenados.get(punteroLetrado.getValor());
				// vale
				if (comprobarRestriccionesLetradoColaTurno(auxLetradoSeleccionado, diasGuardia, hmPersonasConSaltos,
						hmBajasTemporales, idInstitucion, idTurno, idGuardia, idCalendarioGuardias, usuario)) {
					letradoGuardia = auxLetradoSeleccionado;
				}

				if (letradoGuardia != null && letradoGuardia.getInscripcionTurno() != null) {
					LOGGER.info("Letrado encontrado. " + letradoGuardia.getInscripcionTurno().getNombre() + " "
							+ letradoGuardia.getInscripcionTurno().getApellidos1() + " "
							+ letradoGuardia.getInscripcionTurno().getApellidos2());
				}

				// obteniendo siguiente en la cola
				if (punteroLetrado.getValor() < alLetradosOrdenados.size() - 1)
					punteroLetrado.setValor(punteroLetrado.getValor() + 1);
				else
					punteroLetrado.setValor(0); // como es una cola circular hay que volver al principio

			} while (letradoGuardia == null && fin != punteroLetrado.getValor());
		}

		if (letradoGuardia != null)
			return letradoGuardia;
		else
			return null;
	}

	private boolean comprobarRestriccionesLetradoColaTurno(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, HashMap<Long, ArrayList<LetradoInscripcionItem>> hmPersonasConSaltos,
			HashMap<Long, TreeMap<String, BajasTemporalesItem>> hmBajasTemporales, String idInstitucion, String idTurno,
			String idGuardia, String idCalendarioGuardias, AdmUsuarios usuario) throws Exception {

		LOGGER.info("Probando letrados");

		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
			// ... crear un salto cumplido (como si fuera un log)
			insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Salto por BT", idInstitucion, idTurno, idGuardia,
					idCalendarioGuardias, usuario);

			return false; // y no seleccionar
		}

		// si tiene saltos, ...
		List<LetradoInscripcionItem> alSaltos;
		if ((alSaltos = hmPersonasConSaltos.get(letradoGuardia.getIdpersona())) != null) {
			// ... compensar uno
			cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "S", " - Salto cumplido", idInstitucion, idTurno,
					idGuardia, idCalendarioGuardias, usuario);
			alSaltos.remove(0);
			if (alSaltos.size() == 0)
				hmPersonasConSaltos.remove(letradoGuardia.getIdpersona());
			return false; // y no seleccionar
		}

		// una vez comprobado todo, se selecciona a este letrado
		return true;
	} // comprobarRestriccionesLetradoCola()

	private boolean comprobarRestriccionesLetradoCompensadoTurno(LetradoInscripcionItem letradoGuardia,
			ArrayList<String> diasGuardia, Iterator<LetradoInscripcionItem> iteCompensaciones,
			String idSaltoCompensacionGrupo, HashMap<Long, TreeMap<String, BajasTemporalesItem>> hmBajasTemporales,
			String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias, AdmUsuarios usuario)
			throws Exception {

		LOGGER.info("Probando letrados");

		// si esta de vacaciones, ...
		if (isLetradoBajaTemporal(hmBajasTemporales.get(letradoGuardia.getIdpersona()), diasGuardia, letradoGuardia)) {
			// ... crear un salto cumplido (como si fuera un log)
			insertarNuevoSaltoBT(letradoGuardia, diasGuardia, "Salto por BT", idInstitucion, idTurno, idGuardia,
					idCalendarioGuardias, usuario);
			return false; // y no seleccionar
		}

		// cumpliendo compensacion
		cumplirSaltoCompensacion(letradoGuardia, diasGuardia, "C", " - Compensacion cumplida", idInstitucion, idTurno,
				idGuardia, idCalendarioGuardias, usuario);
		iteCompensaciones.remove();

		// una vez comprobado todo, se selecciona a este letrado
		return true;
	}

	/**
	 * Si el letrado esta de baja seteamos la baja temporarl en el objeto
	 * LetradoGuardia, para luego insertar un salto
	 */
	private boolean isLetradoBajaTemporal(TreeMap<String, BajasTemporalesItem> hmBajasTemporales,
			ArrayList<String> diasGuardia, LetradoInscripcionItem letradoGuardia) {
		boolean isLetradoBaja = false;
		BajasTemporalesItem bajaTemporal;

		LOGGER.info("Se comprueba que no tenga baja temporal");

		if (hmBajasTemporales == null)
			return isLetradoBaja;

		for (int j = 0; j < diasGuardia.size(); j++) {
			String fechaPeriodo = (String) diasGuardia.get(j);
			if (hmBajasTemporales.containsKey(fechaPeriodo)) {
				bajaTemporal = hmBajasTemporales.get(fechaPeriodo);
				letradoGuardia.setBajaTemporal(bajaTemporal);
				isLetradoBaja = true;
				LOGGER.info("Se anota en el log Letrado" + letradoGuardia.getPersona().getNombre() + " "
						+ letradoGuardia.getPersona().getApellidos1() + " "
						+ letradoGuardia.getPersona().getApellidos2() + " saltado por baja temporal");
				break;
			}
		}

		return isLetradoBaja;
	}

	private void insertarNuevoSaltoBT(LetradoInscripcionItem letradoGuardia, ArrayList<String> diasGuardia,
			String motivo, String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias,
			AdmUsuarios usuario) throws Exception {

		String saltoOCompensacion = "S";

		Integer idGuardiaInt = null;
		if (idGuardia != null) {
			idGuardiaInt = Integer.parseInt(idGuardia);
		}
		Integer idCalendarioGuardiasInt = null;
		if (idCalendarioGuardias != null) {
			idCalendarioGuardiasInt = Integer.parseInt(idCalendarioGuardias);
		}

		// obteniendo motivo
		StringBuffer descripcion = new StringBuffer();
		BajasTemporalesItem bajaTemporalBean = letradoGuardia.getBajaTemporal();

		if (bajaTemporalBean.getTipo().equals("V")) {
			descripcion.append(UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(),
					"censo.bajastemporales.tipo.vacaciones"));
		} else if (bajaTemporalBean.getTipo().equals("B")) {
			descripcion.append(
					UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "censo.bajastemporales.tipo.baja"));
		}
		descripcion.append(" ");
		descripcion.append(bajaTemporalBean.getDescripcion());

		// Creamos la entity para insertar
		ScsSaltoscompensaciones scsSaltoscompensaciones = new ScsSaltoscompensaciones();
		scsSaltoscompensaciones.setIdinstitucion(Short.parseShort(idInstitucion));
		scsSaltoscompensaciones.setIdturno(Integer.parseInt(idTurno));
		scsSaltoscompensaciones.setIdguardia(idGuardiaInt);
		scsSaltoscompensaciones.setIdcalendarioguardiascreacion(idCalendarioGuardiasInt);
		Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(diasGuardia.get(0).toString());
		scsSaltoscompensaciones.setFechacumplimiento(fechaBBDD);
		scsSaltoscompensaciones.setIdcalendarioguardias(idCalendarioGuardiasInt);
		scsSaltoscompensaciones.setMotivos(descripcion + ": " + motivo);
		scsSaltoscompensaciones.setSaltoocompensacion(saltoOCompensacion);

		scsSaltoscompensacionesMapper.insert(scsSaltoscompensaciones);
	}

	private void cumplirSaltoCompensacion(LetradoInscripcionItem letradoGuardia, ArrayList<String> diasGuardia,
			String saltoOCompensacion, String motivo, String idInstitucion, String idTurno, String idGuardia,
			String idCalendarioGuardias, AdmUsuarios usuario) throws Exception {

		Integer idGuardiaInt = null;
		if (idGuardia != null) {
			idGuardiaInt = Integer.parseInt(idGuardia);
		}
		Integer idCalendarioGuardiasInt = null;
		if (idCalendarioGuardias != null) {
			idCalendarioGuardiasInt = Integer.parseInt(idCalendarioGuardias);
		}

		ScsSaltoscompensaciones scsSaltoscompensaciones = new ScsSaltoscompensaciones();
		scsSaltoscompensaciones.setIdinstitucion(Short.parseShort(idInstitucion));
		scsSaltoscompensaciones.setIdturno(Integer.parseInt(idTurno));
		scsSaltoscompensaciones.setIdguardia(idGuardiaInt);
		scsSaltoscompensaciones.setIdpersona(letradoGuardia.getIdpersona());
		scsSaltoscompensaciones.setSaltoocompensacion(saltoOCompensacion);
		Date fechaBBDD = new SimpleDateFormat("yyyy-MM-dd").parse(diasGuardia.get(0).toString());
		scsSaltoscompensaciones.setFechacumplimiento(fechaBBDD);

		if (!UtilidadesString.esCadenaVacia(letradoGuardia.getIdSaltoCompensacion())) {
			scsSaltoscompensaciones.setIdsaltosturno(Long.valueOf(letradoGuardia.getIdSaltoCompensacion()));
		}

		if (idGuardia != null) {
			scsSaltoscompensaciones.setIdcalendarioguardias(idCalendarioGuardiasInt);
		}
		scsSaltoscompensaciones.setMotivos(motivo);

		scsDesignacionesExtendsMapper.marcarSaltoCompensacion(scsSaltoscompensaciones, usuario);
	}

	@Override
	public ProcuradorDTO busquedaProcurador(List<String> procurador, HttpServletRequest request) {
		LOGGER.info("busquedaProcurador() -> Entrada al servicio para obtener procuradores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaProcurador() / scsProcuradorExtendsMapper.busquedaProcurador() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				String num = procurador.get(0);
				String idinstitucion = procurador.get(1);
				String idturno = procurador.get(2);
				String anio = procurador.get(3).split("/")[0].substring(1);
				procuradorItemList = scsDesignacionesExtendsMapper.busquedaProcurador(num, idinstitucion, idturno,
						anio);

				LOGGER.info(
						"busquedaProcurador() / scsProcuradorExtendsMapper.busquedaProcurador() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					if (!procuradorItemList.isEmpty()) {
						procuradorItemList.get(0).setNumeroTotalProcuradores(String.valueOf(procuradorItemList.size()));
					}
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("busquedaProcurador() -> Salida del servicio para obtener procuradores");
		return procuradorDTO;
	}

	@Override
	public ComboDTO comboTipoMotivo(HttpServletRequest request) {

		LOGGER.info("comboTipoMotivo() -> Entrada al servicio para combo comboTipoMotivo");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoMotivo() / scsDesignacionesExtendsMapper.comboTipoMotivo() -> Entrada a scsDesignacionesExtendsMapper para obtener combo TipoMotivo");

				List<ComboItem> comboItems = scsDesignacionesExtendsMapper.comboTipoMotivo(idInstitucion,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"comboTipoMotivo() / scsDesignacionesExtendsMapper.comboTipoMotivo() -> Salida e scsDesignacionesExtendsMapper para obtener combo TipoMotivo");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboTipoMotivo() -> Salida del servicio para obtener combo TipoMotivo");
		}
		return comboDTO;
	}

	@Override
	public ProcuradorDTO compruebaProcurador(String procurador, HttpServletRequest request) {
		LOGGER.info("compruebaProcurador() -> Entrada al servicio para obtener procuradores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"compruebaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"compruebaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"compruebaProcurador() / scsProcuradorExtendsMapper.compruebaProcurador() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				String[] parts = procurador.split("/");
				String anio = parts[0].substring(1);
				String num = parts[1];

				procuradorItemList = scsDesignacionesExtendsMapper.compruebaProcurador(num, anio);

				LOGGER.info(
						"compruebaProcurador() / scsProcuradorExtendsMapper.compruebaProcurador() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("compruebaProcurador() -> Salida del servicio para obtener procuradores");
		return procuradorDTO;
	}

	@Override
	public ProcuradorDTO compruebaFechaProcurador(ProcuradorItem procuradorItem, HttpServletRequest request) {
		LOGGER.info("compruebaFechaProcurador() -> Entrada al servicio para obtener procuradores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = new ArrayList<ProcuradorItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"compruebaFechaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"compruebaFechaProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"compruebaFechaProcurador() / scsProcuradorExtendsMapper.compruebaFechaProcurador() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

				ScsDesignaprocuradorExample procsDesignaDeleteExample = new ScsDesignaprocuradorExample();

				procsDesignaDeleteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.valueOf(procuradorItem.getIdTurno()))
						.andNumeroEqualTo(Long.valueOf(procuradorItem.getNumero()))
						.andAnioEqualTo(procuradorItem.getAnio())
						.andFechadesignaEqualTo(procuradorItem.getFechaDesigna());

				List<ScsDesignaprocurador> procuradorItemList2 = scsDesignaProcuradorMapper
						.selectByExample(procsDesignaDeleteExample);

				if (!procuradorItemList2.isEmpty())
					procuradorItemList.add(new ProcuradorItem());

				LOGGER.info(
						"compruebaFechaProcurador() / scsProcuradorExtendsMapper.compruebaFechaProcurador() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("compruebaFechaProcurador() -> Salida del servicio para obtener procuradores");
		return procuradorDTO;
	}

	@Override
	public UpdateResponseDTO guardarProcuradorEJG(ProcuradorItem procurador, HttpServletRequest request) {
		LOGGER.info(
				"guardarProcuradorEJG() ->  Entrada al servicio para guardar el procurador en los EJGs asociados a nuestra designa");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response1 = 1;
		int response2 = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

//		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsEjgdesignaExample example = new ScsEjgdesignaExample();

					example.createCriteria().andAniodesignaEqualTo((short) procurador.getAnio())
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.valueOf(procurador.getIdTurno()))
							.andNumerodesignaEqualTo(Long.valueOf(procurador.getNumero()));

					List<ScsEjgdesigna> ejgDesignas = scsEjgdesignaMapper.selectByExample(example);

					for (ScsEjgdesigna ejgDesigna : ejgDesignas) {
						ScsEjgWithBLOBs ejg = new ScsEjgWithBLOBs();
						ejg.setAnio(ejgDesigna.getAnioejg());
						ejg.setNumero(ejgDesigna.getNumeroejg());
						ejg.setIdtipoejg(ejgDesigna.getIdtipoejg());
						ejg.setIdinstitucion(Short.parseShort(idInstitucion.toString()));

						if (procurador.getIdProcurador() != null)
							ejg.setIdprocurador(Long.parseLong(procurador.getIdProcurador()));
						else
							ejg.setIdprocurador(null);
						ejg.setIdinstitucionProc(Short.valueOf(procurador.getIdInstitucion()));
						ejg.setFechaDesProc(procurador.getFechaDesigna());
						ejg.setNumerodesignaproc(procurador.getNumerodesignacion());
						ejg.setFechamodificacion(new Date());
						ejg.setUsumodificacion(usuarios.get(0).getIdusuario());

						LOGGER.info(
								"guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKeySelective() -> Entrada a scsEjgMapper para guardar procurador EJG.");

						if (response2 == 1 && response1 == 1)
							response1 = scsEjgMapper.updateByPrimaryKeySelective(ejg);

						LOGGER.info(
								"guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKeySelective() -> Salida de scsEjgMapper para guardar procurador EJG.");

						ScsEstadoejg estado = new ScsEstadoejg();

						// creamos el objeto para el insert
						estado.setIdinstitucion(idInstitucion);
						estado.setAnio(ejgDesigna.getAnioejg());
						estado.setNumero(ejgDesigna.getNumeroejg());

						estado.setIdestadoejg((short) 19);
						estado.setFechainicio(new Date());
						if (procurador.getNombreApe() != null)
							estado.setObservaciones(procurador.getNombreApe());
						else
							estado.setObservaciones("Ninguno");
						estado.setAutomatico("0");

						estado.setIdtipoejg(ejgDesigna.getIdtipoejg());

						estado.setFechamodificacion(new Date());
						estado.setUsumodificacion(usuarios.get(0).getIdusuario());

						// obtenemos el maximo de idestadoporejg
						ScsEstadoejgExample estadoExample = new ScsEstadoejgExample();
						estadoExample.setOrderByClause("IDESTADOPOREJG DESC");
						estadoExample.createCriteria().andAnioEqualTo(ejgDesigna.getAnioejg())
								.andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(ejgDesigna.getIdtipoejg())
								.andNumeroEqualTo(ejgDesigna.getNumeroejg());

						List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(estadoExample);

						// damos el varlo al idestadoporejg + 1
						if (listEjg.size() > 0) {
							estado.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
						} else {
							estado.setIdestadoporejg(Long.parseLong("0"));
						}

						if (response1 == 1 && response2 == 1)
							response2 = scsEstadoejgMapper.insert(estado);
					}

				} catch (Exception e) {
					response1 = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response1 == 0 || response2 == 0) {
					error.setCode(400);
					error.setDescription("No se ha guardado el procurador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha guardado el procurador correctamente");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				updateResponseDTO.setError(error);

				LOGGER.info(
						"guardarProcuradorEJG() -> Salida del servicio para guardar el procurador en los EJGs asociados a nuestra designa");

			}

		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO guardarProcurador(ProcuradorItem procuradorItem, HttpServletRequest request) {
		LOGGER.info("nuevoProcurador() ->  Entrada al servicio para actualizar los procuradores de una designacion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response11 = 1;
		int response12 = 1;
		int response13 = 1;
		int response2 = 1;
		int response3 = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

//		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarProcurador() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarProcurador() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsDesignaprocurador newProcDesigna = new ScsDesignaprocurador();

					// Se extraen los procuradores asignados a la designacion
					ScsDesignaprocuradorExample procsDesignaExample = new ScsDesignaprocuradorExample();

					procsDesignaExample.setOrderByClause("FECHADESIGNA DESC");
					procsDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.valueOf(procuradorItem.getIdTurno()))
							.andNumeroEqualTo(Long.valueOf(procuradorItem.getNumero()))
							.andAnioEqualTo(procuradorItem.getAnio());

					List<ScsDesignaprocurador> procsDesigna = scsDesignaProcuradorMapper
							.selectByExample(procsDesignaExample);

					// En el caso que haya algun procurador asignado
					if (!procsDesigna.isEmpty()) {
						// 1. Se modifica las entradas correspondientes en la tabla, si la hubiera, para
						// que su fecha de renuncia
						// efectiva se corresponda con la fecha de designacion del nuevo procurador. Se
						// eligiran en base a la fecha de designacion introducida.

						// En el caso que se alterando la fecha designacion del procurador más reciente,
						// se eliminaria
						// y se realizaria el mismo proceso que con un procurador nuevo.
						if (procsDesigna.size() == Integer.valueOf(procuradorItem.getNumeroTotalProcuradores())) {
							response11 = scsDesignaProcuradorMapper.deleteByPrimaryKey(procsDesigna.get(0));
							if (procsDesigna.size() > 1) {
								procsDesigna.get(1).setFecharenuncia(null);
								response12 = scsDesignaProcuradorMapper.updateByPrimaryKey(procsDesigna.get(1));
							}
						}

						// Se buscan los procuradores con fechas superiores a la seleccionada y se
						// selecciona
						// el procurador inmediatamente superior, si lo hubiera.
						ScsDesignaprocuradorExample procsDesignaSupExample = new ScsDesignaprocuradorExample();

						procsDesignaSupExample.setOrderByClause("FECHADESIGNA");
						procsDesignaSupExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.valueOf(procuradorItem.getIdTurno()))
								.andNumeroEqualTo(Long.valueOf(procuradorItem.getNumero()))
								.andAnioEqualTo(procuradorItem.getAnio())
								.andFechadesignaGreaterThan(procuradorItem.getFechaDesigna());

						List<ScsDesignaprocurador> procsSupDesigna = scsDesignaProcuradorMapper
								.selectByExample(procsDesignaSupExample);

						if (!procsSupDesigna.isEmpty()) {
							newProcDesigna.setFecharenuncia(procsSupDesigna.get(0).getFechadesigna());
						} else
							newProcDesigna.setFecharenuncia(null);

						// Se buscan los procuradores con fechas menores a la seleccionada y se
						// selecciona
						// el procurador inmediatamente menor, si lo hubiera.
						ScsDesignaprocuradorExample procsDesignaMenExample = new ScsDesignaprocuradorExample();

						procsDesignaMenExample.setOrderByClause("FECHADESIGNA DESC");
						procsDesignaMenExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.valueOf(procuradorItem.getIdTurno()))
								.andNumeroEqualTo(Long.valueOf(procuradorItem.getNumero()))
								.andAnioEqualTo(procuradorItem.getAnio())
								.andFechadesignaLessThan(procuradorItem.getFechaDesigna());

						List<ScsDesignaprocurador> procsMenDesigna = scsDesignaProcuradorMapper
								.selectByExample(procsDesignaMenExample);

						if (!procsMenDesigna.isEmpty()) {
							procsMenDesigna.get(0).setFecharenuncia(procuradorItem.getFechaDesigna());
							response13 = scsDesignaProcuradorMapper.updateByPrimaryKey(procsMenDesigna.get(0));
						}

						// 2. Se comprueba si la designacion tiene un procurador con la misma fecha de
						// designacion
						// que el procurador nuevo y se elimina.

						ScsDesignaprocuradorExample procsDesignaDeleteExample = new ScsDesignaprocuradorExample();

						procsDesignaDeleteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.valueOf(procuradorItem.getIdTurno()))
								.andNumeroEqualTo(Long.valueOf(procuradorItem.getNumero()))
								.andAnioEqualTo(procuradorItem.getAnio())
								.andFechadesignaEqualTo(procuradorItem.getFechaDesigna());

						List<ScsDesignaprocurador> procsDesignaDelete = scsDesignaProcuradorMapper
								.selectByExample(procsDesignaDeleteExample);

						if (response11 == 1 && response12 == 1 && response13 == 1 && !procsDesignaDelete.isEmpty())
							response2 = scsDesignaProcuradorMapper.deleteByPrimaryKey(procsDesignaDelete.get(0));
					}
					// 3. Se introduce el nuevo procurador

					// Informacion designacion
					newProcDesigna.setIdinstitucion(idInstitucion);
					newProcDesigna.setIdturno(Integer.valueOf(procuradorItem.getIdTurno()));
					newProcDesigna.setNumero(Long.valueOf(procuradorItem.getNumero()));
					newProcDesigna.setAnio(procuradorItem.getAnio());

					// Informacion procurador
					newProcDesigna.setIdinstitucionProc(Short.valueOf(procuradorItem.getIdInstitucion()));
					newProcDesigna.setIdprocurador(Long.valueOf(procuradorItem.getIdProcurador()));

					// Informacion entrada
					newProcDesigna.setFechadesigna(procuradorItem.getFechaDesigna());
					newProcDesigna.setNumerodesignacion(procuradorItem.getNumerodesignacion());
					newProcDesigna.setMotivosrenuncia(procuradorItem.getMotivosRenuncia());
					newProcDesigna.setObservaciones(procuradorItem.getObservaciones());
					newProcDesigna.setFecharenunciasolicita(procuradorItem.getFecharenunciasolicita());

					newProcDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
					newProcDesigna.setFechamodificacion(new Date());

					if (response11 == 1 && response12 == 1 && response13 == 1 && response2 == 1) {
						// if(procsDesigna.size()<Integer.valueOf(procuradorItem.getNumeroTotalProcuradores()))
						response3 = scsDesignaProcuradorMapper.insert(newProcDesigna);
						// else response3 =
						// scsDesignaProcuradorMapper.updateByPrimaryKey(newProcDesigna);
					}
					ScsDesignaKey designaKey = new ScsDesignaKey();
					designaKey.setIdinstitucion(idInstitucion);
					designaKey.setAnio(procuradorItem.getAnio());
					designaKey.setIdturno(Integer.valueOf(procuradorItem.getIdTurno()));
					designaKey.setNumero(Long.valueOf(procuradorItem.getNumero()));
					ScsDesigna scsDesigna = scsDesignacionesExtendsMapper.selectByPrimaryKey(designaKey);
					if(scsDesigna != null)
						actualizaDesignaEnAsuntos(scsDesigna, idInstitucion, "procuradorDesigna", usuarios.get(0));
					
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
					response11 = 0;
				}
				if (response11 == 0 || response12 == 0 || response13 == 0 || response2 == 0 || response3 == 0) {
					error.setCode(400);
					error.setDescription("No se ha insertado el procurador correctamente");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else {
					error.setCode(200);
					error.setDescription("Se ha insertado el procurador correctamente");
					insertResponseDTO.setStatus(SigaConstants.OK);
				}

				insertResponseDTO.setError(error);

				LOGGER.info(
						"guardarProcurador() -> Salida del servicio para actualizar los procuradores de una designacion");

			}

		}

		return insertResponseDTO;
	}

	@Override
	public DesignaItem existeDesginaJuzgadoProcedimiento(DesignaItem designa, HttpServletRequest request) {
		// DesignaItem result = new DesignaItem();
		DesignaItem designas = null;
		// List<GenParametros> tamMax = null;
		// Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

//			GenParametrosExample genParametrosExample = new GenParametrosExample();
//			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
//					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
//					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
//			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
//			LOGGER.info(
//					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
//			LOGGER.info(
//					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
//			if (tamMax != null) {
//				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
//			} else {
//				tamMaximo = null;
//			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.getDatosAdicionales -> Entrada a servicio para la busqueda de datos adicionales de una designa");

				try {
					designas = scsDesignacionesExtendsMapper.existeDesginaJuzgadoProcedimiento(idInstitucion, designa);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public List<DesignaItem> getDatosAdicionales(DesignaItem designa, HttpServletRequest request) {
		// DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDatosAdicionales -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.getDatosAdicionales -> Entrada a servicio para la busqueda de datos adicionales de una designa");

				try {
					designas = scsDesignacionesExtendsMapper.getDatosAdicionales(idInstitucion, designa);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getDatosAdicionales -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public ComboDTO comboPrisiones(HttpServletRequest request) {

		LOGGER.info("comboPrisiones() -> Entrada al servicio para combo comboPrisiones");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPrisiones() / scsPrisionExtendsMapper.getComboPrisiones() -> Entrada a scsPrisionExtendsMapper para obtener combo de prisiones");

				List<ComboItem> comboItems = scsPrisionExtendsMapper.getComboPrisiones(idInstitucion);

				LOGGER.info(
						"comboPrisiones() / scsPrisionExtendsMapper.getComboPrisiones() -> Salida e scsPrisionExtendsMapper para obtener combo de prisiones");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboPrisiones() -> Salida del servicio para obtener combo de prisiones");
		}
		return comboDTO;
	}

	@Override
	public ComboDTO getPartidaPresupuestariaDesigna(HttpServletRequest request, @RequestBody DesignaItem designaItem) {
		// DesignaItem result = new DesignaItem();
		ComboDTO comboDTO = new ComboDTO();
		// List<GenParametros> tamMax = null;
		// Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getPartidaPresupuestariaDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

//			if (tamMax != null) {
//				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
//			} else {
//				tamMaximo = null;
//			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Entrada a servicio ");

				try {
					List<ComboItem> comboItems = scsDesignacionesExtendsMapper
							.getPartidaPresupuestariaDesigna(idInstitucion, designaItem);

					comboDTO.setCombooItems(comboItems);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.getPartidaPresupuestariaDesigna -> Salida del servicio");
			}
		}

		return comboDTO;
	}

	@Override
	public DesignaItem busquedaDesignaActual(ScsDesigna item, HttpServletRequest request) {
		LOGGER.info("DesignacionesServiceImpl.busquedaDesigna() -> Entrada al servicio servicio");
		List<DesignaItem> designasList = null;
		DesignaItem designa = null;
		// List<GenParametros> tamMax = null;
		// Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaDesigna() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaDesigna -> Entrada a servicio para la busqueda de contrarios");

				try {

					ScsDesignaKey key = new ScsDesignaKey();
					key.setIdinstitucion(idInstitucion);
					key.setAnio(item.getAnio());
					key.setIdturno(item.getIdturno());
					key.setNumero(item.getNumero());

					designasList = scsDesignaMapper.busquedaDesignaActual(key);
					if (designasList != null && !designasList.isEmpty()) {
						designa = designasList.get(0);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesigna -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesigna -> Salida del servicio");
			}
		}

		return designa;
	}

	@Override
	public List<ListaLetradosDesignaItem> busquedaLetradosDesigna(ScsDesigna item, HttpServletRequest request) {
		LOGGER.info(
				"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada al servicio para buscar los letrados asociados a una designacion");
		List<ListaLetradosDesignaItem> listaLetrados = null;
		// List<GenParametros> tamMax = null;
		// Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaLetradosDesigna() -> Entrada a servicio para la busqueda de letrados de la designacion");

				try {

					listaLetrados = scsDesignacionesExtendsMapper.getListaLetradosDesigna(item, idInstitucion);
					// Repasamos las fechas obtenidas
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					for (ListaLetradosDesignaItem letrado : listaLetrados) {
						String Fecha = formatter.format(letrado.getFechaDesignacion());
					}

				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaLetradosDesigna() -> Salida del servicio");
			}
		}

		return listaLetrados;
	}

	@Override
	@Transactional
	public UpdateResponseDTO updateLetradoDesigna(String[] item, HttpServletRequest request) throws Exception {

		LOGGER.info(
				"updateLetradoDesigna() -> Entrada al servicio para actualizar el letrado asociado a la designación");

		LOGGER.info("updateLetradoDesigna() -> Inicio Preparacion de datos proviniente del item de front");

		String anio = item[0].substring(1, 5);

		ScsDesigna designa = new ScsDesigna();
		designa.setAnio(Short.parseShort(anio));
		designa.setIdturno(Integer.parseInt(item[1]));
		designa.setNumero(Long.parseLong(item[2]));
		designa.setArt27(item[12]);

		ScsDesignasletrado letradoSaliente = new ScsDesignasletrado();
		letradoSaliente.setIdpersona(Long.parseLong(item[3]));
		letradoSaliente.setObservaciones(item[4]);
		letradoSaliente.setIdtipomotivo(Short.parseShort(item[5]));
		if (item[6] != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(item[6]));
			letradoSaliente.setFechadesigna(formatter.parse(formatter.format(calendar.getTime())));
		}
		if (item[7] != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = item[7].substring(0, 10);
			letradoSaliente.setFecharenunciasolicita(formatter.parse(date));
		}

		ScsDesignasletrado letradoEntrante = new ScsDesignasletrado();

//		if (designa.getIdturno() != null) {
//			letradoEntrante.setIdturno(designa.getIdturno());
//		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (item[8] != null) {

			if (item[8].length() != 10) {
				String date = item[8].substring(0, 10);
				letradoEntrante.setFechadesigna(formatter.parse(date));
			} else {
				String date = item[8].substring(0, 10);
				letradoEntrante.setFechadesigna(format.parse(date));
			}
		}

		if (item[9] != null) {
			letradoEntrante.setIdpersona(Long.parseLong(item[9]));
		}

		Boolean checkCompensacionSaliente = false;
		if (item[10] != null) {
			checkCompensacionSaliente = Boolean.parseBoolean(item[10]);
		}

		Boolean checkSaltoEntrante = false;
		if (item[11] != null) {
			checkSaltoEntrante = Boolean.parseBoolean(item[11]);
		}
		// Faltaria, si es necesario el elemento 12 (Art.27)
		Boolean checkArt27 = false;
//		if(item[11] != null) {
//			 checkSaltoEntrante = Boolean.parseBoolean(item[12]);
//		}
		
		LOGGER.info("updateLetradoDesigna() ->  Fin Preparacion de datos proviniente del item de front");

		LOGGER.info(
				"updateLetradoDesigna() -> Obtencion de datos de autenticacion y otros para proceder con la actualizacion de letrado");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Boolean letradoDesigSinTurno = Boolean.FALSE;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

//				try {

				ScsDesignasletradoExample example = new ScsDesignasletradoExample();

				example = new ScsDesignasletradoExample();
				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(designa.getAnio())
						.andIdturnoEqualTo(designa.getIdturno()).andNumeroEqualTo(designa.getNumero())
						.andIdpersonaEqualTo(letradoSaliente.getIdpersona())
						.andFechadesignaGreaterThanOrEqualTo(letradoSaliente.getFechadesigna())
						.andFecharenunciasolicitaIsNull();

				example.setOrderByClause("FECHADESIGNA DESC");

				List<ScsDesignasletrado> designas = scsDesignasletradoMapper.selectByExample(example);

				ScsDesignasletrado designaLetradoVieja = new ScsDesignasletrado();
				if (designas.size() != 0) {
					designaLetradoVieja = designas.get(0);
				}

				ScsDesignasletrado designaLetradoNueva = new ScsDesignasletrado();
				designaLetradoNueva.setIdinstitucion(letradoEntrante.getIdinstitucion());
				designaLetradoNueva.setAnio(designa.getAnio());
				designaLetradoNueva.setIdturno(designa.getIdturno());
				designaLetradoNueva.setNumero(designa.getNumero());
				designaLetradoNueva.setFechadesigna(letradoEntrante.getFechadesigna());
				designaLetradoNueva.setFechamodificacion(new Date());
				designaLetradoNueva.setManual((short) 0);
				designaLetradoNueva.setLetradodelturno("S");
				designaLetradoNueva.setUsumodificacion(usuarios.get(0).getIdusuario());

				// Validar que la fecha sea igual o posterior a la fecha de la designación
				// anterior
				if (designaLetradoVieja != null && designaLetradoVieja.getFechadesigna() != null
						&& designaLetradoNueva.getFechadesigna() != null
						&& designaLetradoVieja.getFechadesigna().toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate().compareTo(designaLetradoNueva.getFechadesigna().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDate()) > 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.updateLetradoDesigna() -> Se ha producido un error al actualizar el letrado asociado a la designación");
					error.setCode(400);
					error.setDescription("justiciaGratuita.oficio.designas.letrados.fechaDesignaIncorrecta");

					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}

				// Seleccion de un letrado en el caso de que no se haya introducido
				if (letradoEntrante.getIdpersona() == null) {

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					// Verificar que si es un Letrado igual al asignado entonces pasar al siguiente
					// Letrado.
					LetradoInscripcionItem newLetrado = this.getLetradoTurno(idInstitucion.toString(),
							String.valueOf(designa.getIdturno()), dateFormat.format(letradoSaliente.getFechadesigna()),
							usuarios.get(0), designaLetradoVieja);

					if (newLetrado == null) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.updateLetradoDesigna() -> Se ha producido un error al actualizar el letrado asociado a la designación");
						error.setCode(100);
						error.setDescription("justiciaGratuita.oficio.designas.letrados.nocolaletrado");
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					} else {
						letradoEntrante.setIdpersona(newLetrado.getIdpersona());
						designaLetradoNueva.setIdpersona(newLetrado.getIdpersona());
					}

				} else {
					designaLetradoNueva.setIdpersona(letradoEntrante.getIdpersona());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					// Recuperamos ultima asignacion sin renuncia del letrado para saber su turno y
					// comparar (Deberia ser misma busqueda que en buscador colegiados para asignar)
					ScsDesignasletradoExample designaletrado = new ScsDesignasletradoExample();

					designaletrado.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(letradoEntrante.getIdpersona()).andFecharenunciaIsNull();
					designaletrado.setOrderByClause("FECHADESIGNA DESC");

					// 1) Averiguamos el colegiado a partir de su numero para obtener el IdPersona
					CenColegiadoExample cenColegExample = new CenColegiadoExample();
					cenColegExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpersonaEqualTo(letradoEntrante.getIdpersona());
					List<CenColegiado> resCenColegiado = this.cenColegiadoExtendsMapper
							.selectByExample(cenColegExample);
					CenColegiado cenColeg = CollectionUtils.isNotEmpty(resCenColegiado) ? resCenColegiado.get(0) : null;

					if (cenColeg != null) {
						// 2) Averiguamos el/los turnos inscritos del colegiado elegido manualmente
						// (buscador)
						ScsInscripcionturnoExample insTurnoExample = new ScsInscripcionturnoExample();
						insTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andFechadenegacionIsNull().andFechasolicitudIsNotNull().andFechavalidacionIsNotNull()
								.andFechabajaIsNull().andIdpersonaEqualTo(cenColeg.getIdpersona());

						List<ScsInscripcionturno> resInscLetrado = this.scsInscripcionesTurnoExtendsMapper
								.selectByExample(insTurnoExample);

						if (CollectionUtils.isNotEmpty(resInscLetrado)) {
							// 3) Recuperamos las entidades "Turno" iteradas para comparar si estuviera
							// inscrito a alguno de tipo tramitacion (idtipoturno = 1)

							for (ScsInscripcionturno scsTurnoInscrito : resInscLetrado) {
								ScsTurnoExample turnoExample = new ScsTurnoExample();
								turnoExample.createCriteria().andIdturnoEqualTo(scsTurnoInscrito.getIdturno());
								List<ScsTurno> turnoInscritoIt = this.scsTurnosExtendsMapper
										.selectByExample(turnoExample);

								if (CollectionUtils.isNotEmpty(turnoInscritoIt) && Short.toUnsignedInt(turnoInscritoIt
										.get(0).getIdtipoturno()) != SigaConstants.TIPO_TURNO_DESIGNACION.intValue()) {
									error.setCode(HttpStatus.NOT_ACCEPTABLE.value());
									error.setDescription(
											"justiciaGratuita.oficio.designa.designacionletradoturnotramitacion");
									updateResponseDTO.setStatus(SigaConstants.KO);
									updateResponseDTO.setError(error);
									return updateResponseDTO;
								}
							}
						} else {
							letradoDesigSinTurno = Boolean.TRUE;
						}
					} else {
						LOGGER.info(
								"+++ DesignacionesServiceImple/createDesigna() -> No se ha encontrado 'CenColegiado' para el NCOLEGIADO = "
										+ letradoEntrante.getNumero());
					}

				}

				// Validar que el letrado sea diferente al anterior.
				// Realizar todos los pasos.
				if (designaLetradoVieja != null && designaLetradoVieja.getIdpersona() != null
						&& designaLetradoVieja.getIdpersona().equals(designaLetradoNueva.getIdpersona())) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"DesignacionesServiceImpl.updateLetradoDesigna() -> El letrado seleccionado automáticamente coincide con el asociado a la designación");
					error.setCode(200);
					error.setDescription("justiciaGratuita.oficio.designas.letrados.letradoRepetidoAutmatico");
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}

				if (letradoSaliente.getFechadesigna().equals(letradoEntrante.getFechadesigna())
						|| designa.getArt27().equals("Si")) {
					Long idPersonaDesignaVieja = designaLetradoVieja.getIdpersona();
//						designaLetradoNueva = designaLetradoVieja;
					designaLetradoNueva.setIdpersona(designaLetradoNueva.getIdpersona());
					// No se puede borrar antes del insert porque si falla el insert problema.
					response = scsDesignasletradoMapper.deleteByPrimaryKey(designaLetradoVieja);
					if (response == 1) {
						CenColegiadoExample colegiado = new CenColegiadoExample();
						colegiado.createCriteria().andIdpersonaEqualTo(designaLetradoNueva.getIdpersona());
						List<CenColegiado> colegiados = cenColegiadoExtendsMapper.selectByExample(colegiado);
						ArrayList<String> institucionClientes = new ArrayList<String>();
						for (CenColegiado colegiadoInsti : colegiados) {
							institucionClientes.add(colegiadoInsti.getIdinstitucion().toString());
						}

						// Comprobar que este colegiado no existe en el colegio.
						if (institucionClientes.contains(idInstitucion.toString()) == false) {

							CenPersona persona = cenPersonaExtendsMapper
									.selectByPrimaryKey(designaLetradoNueva.getIdpersona());

							NoColegiadoItem noColegiadoItem = new NoColegiadoItem();
							noColegiadoItem.setIdTipoIdentificacion("10");
							noColegiadoItem.setNif(persona.getNifcif());
							noColegiadoItem.setIdInstitucion(String.valueOf(idInstitucion));
							noColegiadoItem.setIdPersona(persona.getIdpersona().toString());
							noColegiadoItem.setNombre(persona.getNombre());
							noColegiadoItem.setSoloNombre(persona.getNombre());
							noColegiadoItem.setApellidos1(persona.getApellidos1());
							noColegiadoItem.setApellidos2(persona.getApellidos2());
							noColegiadoItem.setIdLenguaje("1");
							noColegiadoItem.setidTratamiento("2");
							noColegiadoItem.setComisiones("0");

							// Crearlo en el colegio
							InsertResponseDTO responseNColegiado = fichaDatosGenerales
									.createNoColegiado(noColegiadoItem, request);
						}

						designaLetradoNueva.setIdinstitucion(idInstitucion);
						response = scsDesignasletradoMapper.insertSelective(designaLetradoNueva);
						if (response == 0)
							throw (new Exception("Error al actualizar el letrado asociado a la designacion"));
					} else
						throw (new Exception("Error al actualizar el letrado asociado a la designacion"));
				} else {
					// Creamos designa nueva para letradoEntrante
					designaLetradoNueva.setIdinstitucion(idInstitucion);
					response = scsDesignasletradoMapper.insertSelective(designaLetradoNueva);

					// Actualizamos LetradoSaliente
					if (response != 0 && letradoEntrante.getIdpersona() != null) {
						List<ComboItem> motivos = scsDesignacionesExtendsMapper.comboTipoMotivo(idInstitucion,
								usuarios.get(0).getIdlenguaje());
						int i = 0;
						while (i < motivos.size() && designaLetradoVieja.getMotivosrenuncia() == null) {
							if (motivos.get(i).getValue().equals(letradoSaliente.getIdtipomotivo().toString())) {
								designaLetradoVieja.setMotivosrenuncia(motivos.get(i).getLabel());
							}
							i++;
						}
						designaLetradoVieja.setObservaciones(letradoSaliente.getObservaciones());
						designaLetradoVieja.setIdtipomotivo(letradoSaliente.getIdtipomotivo());
						designaLetradoVieja.setUsumodificacion(usuarios.get(0).getIdusuario());
						designaLetradoVieja.setFechamodificacion(new Date());
						designaLetradoVieja.setLetradodelturno("N");
						designaLetradoVieja.setFecharenunciasolicita(letradoSaliente.getFecharenunciasolicita());
						designaLetradoVieja.setFecharenuncia(letradoEntrante.getFechadesigna());
						// Si el usuario que realiza el cambio es un colegio se acepta automaticamente
						// la renuncia
						if (UserTokenUtils.getLetradoFromJWTToken(token) == "N") {
							designaLetradoVieja.setFecharenuncia(letradoSaliente.getFecharenunciasolicita());
						}

						response = scsDesignasletradoMapper.updateByPrimaryKeySelective(designaLetradoVieja);
						if (response == 0)
							throw (new Exception("Error al actualizar el letrado asociado a la designacion"));
					}
				}

				// Creamos salto y/o compensacion si han marcado el/los check

				List<SaltoCompGuardiaItem> listaSaltoItem = new ArrayList<SaltoCompGuardiaItem>();

				if (checkCompensacionSaliente) {
					SaltoCompGuardiaItem compensacion = new SaltoCompGuardiaItem();
					compensacion.setIdPersona(designaLetradoVieja.getIdpersona().toString());
					compensacion.setIdTurno(designa.getIdturno().toString());
					compensacion.setSaltoCompensacion("C");
					String fecha = sdf.format(new Date());
					compensacion.setFecha(fecha);
					compensacion.setMotivo(designaLetradoVieja.getMotivosrenuncia());
					listaSaltoItem.add(compensacion);

				}

				if (checkSaltoEntrante) {
					SaltoCompGuardiaItem salto = new SaltoCompGuardiaItem();
					String fecha = sdf.format(new Date());
					salto.setFecha(fecha);
					salto.setIdPersona(designaLetradoNueva.getIdpersona().toString());
					salto.setIdTurno(designa.getIdturno().toString());
					salto.setMotivo("");
					salto.setSaltoCompensacion("S");
					listaSaltoItem.add(salto);
				}

				// Introducimos el salto y/o compensacion
				DeleteResponseDTO op = saltosCompOficioService.guardarSaltosCompensaciones(listaSaltoItem, request);

				if (op.getError() != null && op.getError().getCode() == 500)
					throw (new Exception("Error al asociar un salto o una compensacion a unos de los letrados"));

				if (response == 0) {
					if (error.getCode() != 100) {
						error.setCode(400);
						error.setDescription("general.mensaje.error.bbdd");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
					}
				} else {
					error.setCode(letradoDesigSinTurno ? 202 : 200);
					error.setDescription(
							letradoDesigSinTurno ? "justiciaGratuita.oficio.designa.designacionletradosinturno"
									: "general.message.registro.actualizado");
					updateResponseDTO.setError(error);
				}

//				} catch (Exception e) {
//					error.setDescription("general.mensaje.error.bbdd");
//					error.code(500);/designas/busquedaLetradosDesignacion
//					updateResponseDTO.setStatus(SigaConstants.KO);
//					updateResponseDTO.setError(error);
//					LOGGER.error(e.getMessage());
//					LOGGER.info("updateLetradoDesigna() -> Salida del servicio");
//				}

			}

			LOGGER.info("updateLetradoDesigna() -> Salida del servicio");

		}
		return updateResponseDTO;
	}

	@Override
	public ResponseDataDTO compruebaLetradoInscritoEnTurno(ScsDesignasletrado designaLetrado,
			HttpServletRequest request) throws Exception {
		LOGGER.info(
				"compruebaLetradoInscritoEnTurno() -> Entrando al servicio que comprueba si el turno contiene al letrado indicado");
		ResponseDataDTO response = new ResponseDataDTO();

		// Conseguimos información del usuario logeado
		LOGGER.info("compruebaLetradoInscritoEnTurno() -> Entrando al servicio de autenticación");
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
		LOGGER.info("compruebaLetradoInscritoEnTurno() <- Saliendo del servicio de autenticación");

		ScsInscripcionturnoExample inscripcionturnoExample = new ScsInscripcionturnoExample();
		inscripcionturnoExample.createCriteria().andIdinstitucionEqualTo(designaLetrado.getIdinstitucion())
				.andIdturnoEqualTo(designaLetrado.getIdturno()).andIdpersonaEqualTo(designaLetrado.getIdpersona())
				.andFechabajaIsNull();

		long result = scsInscripcionesTurnoExtendsMapper.countByExample(inscripcionturnoExample);
		response.setData(Boolean.valueOf(result != 0).toString());

		LOGGER.info(
				"compruebaLetradoInscritoEnTurno() -> Saliendo del servicio que comprueba si el turno contiene al letrado indicado");
		return response;
	}

	public ComboDTO comboMotivosCambioActDesigna(HttpServletRequest request) {
		LOGGER.info(
				"comboMotivosCambioActDesigna() -> Entrada al servicio para obtener combo de movitos de cambio de la actuación");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboMotivosCambioActDesigna() / scsDesignacionesExtendsMapper.comboMotivosCambioActDesigna() -> Entrada a scsDesignacionesExtendsMapper para obtener combo de movitos de cambio de la actuación");

				List<ComboItem> comboItems = scsDesignacionesExtendsMapper.comboMotivosCambioActDesigna(idInstitucion,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"comboMotivosCambioActDesigna() / scsDesignacionesExtendsMapper.comboMotivosCambioActDesigna() -> Salida e scsDesignacionesExtendsMapper para obtener combo de movitos de cambio de la actuación");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info(
					"comboMotivosCambioActDesigna() -> Salida del servicio para obtener combo de movitos de cambio de la actuación");
		}
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO updateJustiActDesigna(ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.updateJustiActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.updateJustiActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				int response = scsDesignacionesExtendsMapper.updateJustiActDesigna(actuacionDesignaItem,
						Short.toString(idInstitucion), usuarios.get(0));

				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.updateJustiActDesigna() -> Se ha producido un error al actualizar los datos de justificación de la actuación");
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.updateJustiActDesigna() -> Se ha producido un error al actualizar los datos de justificación de la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	public RelacionesDTO busquedaRelaciones(List<String> relaciones, HttpServletRequest request) {
		LOGGER.info("busquedaRelaciones() -> Entrada al servicio para obtener relaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RelacionesDTO relacionesDTO = new RelacionesDTO();
		List<RelacionesItem> relacionesItem = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaRelaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaRelaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaRelaciones() / scsProcuradorExtendsMapper.busquedaRelaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las relaciones");

				String[] parts = relaciones.get(0).split("/");
				String anio = parts[0].substring(1);
				String num = relaciones.get(3);
				String idTurno = relaciones.get(1);
				String idinstitucion = relaciones.get(2);
				relacionesItem = scsDesignacionesExtendsMapper.busquedaRelaciones(anio, num, idTurno, idinstitucion);

				LOGGER.info(
						"busquedaRelaciones() / scsDesignacionesExtendsMapper.busquedaRelaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las relaciones");

				if (relacionesItem != null) {
					relacionesDTO.setRelacionesItem(relacionesItem);
				}
			}

		}
		LOGGER.info("busquedaRelaciones() -> Salida del servicio para obtener relaciones");
		return relacionesDTO;
	}

	@Override
	public DeleteResponseDTO eliminarRelacion(List<String> datos, HttpServletRequest request) {
		LOGGER.info("eliminarRelacion() ->  Entrada al servicio para eliminar relaciones");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"eliminarRelacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"eliminarRelacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"eliminarRelacion() / ScsDefendidosdesignaMapper.eliminarRelacion() -> Entrada a ScsDefendidosdesignaMapper para eliminar las relaciones");

					String anioEjg = datos.get(2);
					String numEjg = datos.get(1);
					String idTurno = datos.get(6);
					String institucion = datos.get(0);
					String anioDes = datos.get(4);
					String numDes = datos.get(5);
					String idTipoEjg = datos.get(3);

					response = scsDesignacionesExtendsMapper.eliminarRelacion(anioEjg, numEjg, idTurno, institucion,
							anioDes, numDes, idTipoEjg);

					LOGGER.info(
							"eliminarRelacion() / ScsDefendidosdesignaMapper.eliminarRelacion() -> Salida de ScsDefendidosdesignaMapper para eliminar las relaciones");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					deleteResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("eliminarRelacion() -> Salida del servicio para eliminar relaciones");

		return deleteResponseDTO;
	}

	@Override
	public UpdateResponseDTO eliminarRelacionAsistenciaDesigna(RelacionesItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"DesignacionesServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"DesignacionesServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"DesignacionesServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String idinstitucion = datos.getIdinstitucion();
					String numero = datos.getNumero();
					String anio = datos.getAnio();

					response = scsAsistenciaExtendsMapper.eliminarRelacionAsistenciaDes(idinstitucion, anio, numero);

					LOGGER.debug(
							"DesignacionesServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"DesignacionesServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"DesignacionesServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("DesignacionesServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}

	/**
	 * actualizaJustificacionExpres
	 */
	@Transactional
	public UpdateResponseDTO actualizaJustificacionExpres(GuardaJustificacionExpressItem item,
			HttpServletRequest request) {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int responseDesig = 0;
		int responseAct = 0;
		numActualizados = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.actualizaJustificacionExpres() -> Entrada a servicio para actualizar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Realizando update...");
					
					LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Comprobando designaciones con EJGs en modo solo lectura...");

					/*
					 * Comprobamos que no existan designaciones con EJGs marcados como sólo lectura por el filtro
					 * En el caso que se detecten, se eliminaran de la lista para no ser actualizados
					 */
					compruebaEjgsSoloLectura(item.getFiltro(), item.getListaItem());
					
					for (JustificacionExpressItem justificacion : item.getListaItem()) {
						// guardamos los cambios de la designacion

						ScsDesigna recordJust = new ScsDesigna();

						recordJust.setIdinstitucion(Short.parseShort(justificacion.getIdInstitucion()));
						recordJust.setIdturno(Integer.parseInt(justificacion.getIdTurno()));
						recordJust.setAnio(Short.parseShort(justificacion.getAnioDesignacion()));
						recordJust.setNumero(Long.parseLong(justificacion.getNumDesignacion()));
						recordJust.setFechamodificacion(new Date());
						recordJust.setUsumodificacion(usuarios.get(0).getIdusuario());

						if (justificacion.getNombreJuzgado() != null && !justificacion.getNombreJuzgado().isEmpty()) {
							recordJust.setIdjuzgado(Long.parseLong(justificacion.getNombreJuzgado()));
							recordJust.setIdinstitucionJuzg(idInstitucion);
						}

						if (justificacion.getNig() != null && !justificacion.getNig().isEmpty()) {
							recordJust.setNig(justificacion.getNig());
						}

						if (justificacion.getEstado() != null && !justificacion.getEstado().isEmpty()) {
							recordJust.setEstado(justificacion.getEstado());
						}
						if (justificacion.getProcedimiento() != null && !justificacion.getProcedimiento().isEmpty()) {
							ScsProcedimientosKey procedimientosKey = new ScsProcedimientosKey();
							procedimientosKey.setIdprocedimiento(justificacion.getProcedimiento());
							procedimientosKey.setIdinstitucion(idInstitucion);

							if (scsProcedimientosMapper.selectByPrimaryKey(procedimientosKey) != null) {
								recordJust.setIdprocedimiento(justificacion.getProcedimiento());
							}
						}

						if (justificacion.getNumProcedimiento() != null
								&& !justificacion.getNumProcedimiento().isEmpty()) {
							recordJust.setNumprocedimiento(justificacion.getNumProcedimiento());
						}

						responseDesig = scsDesignaMapper.updateByPrimaryKeySelective(recordJust);
						numActualizados++;

						// guardamos las actuaciones
						if (justificacion.getActuaciones() != null && justificacion.getActuaciones().size() > 0) {
							for (ActuacionesJustificacionExpressItem actuacion : justificacion.getActuaciones()) {
								if (actuacion.getValidada() != "1") {

									ScsActuaciondesignaKey actuaciondesignaKey = new ScsActuaciondesignaKey();
									actuaciondesignaKey
											.setIdinstitucion(Short.parseShort(actuacion.getIdInstitucion()));
									actuaciondesignaKey.setNumeroasunto(Long.parseLong(actuacion.getNumAsunto()));
									actuaciondesignaKey.setNumero(Long.parseLong(actuacion.getNumDesignacion()));
									actuaciondesignaKey.setIdturno(Integer.parseInt(actuacion.getIdTurno()));
									actuaciondesignaKey.setAnio(Short.parseShort(actuacion.getAnio()));

									ScsActuaciondesigna record = scsActuaciondesignaMapper
											.selectByPrimaryKey(actuaciondesignaKey);

									if (actuacion.getAnioProcedimiento() != null
											&& !actuacion.getAnioProcedimiento().isEmpty()) {
										record.setAnioprocedimiento(Short.parseShort(actuacion.getAnioProcedimiento()));
									}

									if (actuacion.getFecha() != null && !actuacion.getFecha().isEmpty()) {
										fecha = formatter.parse(actuacion.getFecha());
										record.setFecha(fecha);
									}

									if (actuacion.getFechaJustificacion() != null
											&& actuacion.getFechaJustificacion() != "false"
											&& actuacion.getFechaJustificacion() != "true"
											&& !actuacion.getFechaJustificacion().isEmpty()) {

										fecha = formatter.parse(actuacion.getFechaJustificacion());
										record.setFechajustificacion(fecha);
									} else {
										record.setFechajustificacion(null);
									}

									if (actuacion.getIdAcreditacion() != null
											&& !actuacion.getIdAcreditacion().isEmpty()) {
										record.setIdacreditacion(Short.parseShort(actuacion.getIdAcreditacion()));
									}

									if (actuacion.getIdJuzgado() != null && !actuacion.getIdJuzgado().isEmpty()) {
										record.setIdjuzgado(Long.parseLong(actuacion.getIdJuzgado()));
									}

									if (actuacion.getProcedimiento() != null
											&& !actuacion.getProcedimiento().isEmpty()) {
										ScsProcedimientosKey procedimientosKey = new ScsProcedimientosKey();
										procedimientosKey.setIdprocedimiento(actuacion.getProcedimiento());
										procedimientosKey.setIdinstitucion(idInstitucion);

										if (scsProcedimientosMapper.selectByPrimaryKey(procedimientosKey) != null) {
											record.setIdprocedimiento(actuacion.getProcedimiento());
										}
									}

									if (actuacion.getNumProcedimiento() != null
											&& !actuacion.getNumProcedimiento().isEmpty()) {
										record.setNumeroprocedimiento(actuacion.getNumProcedimiento());
									}

									if (actuacion.getNig() != null && !actuacion.getNig().isEmpty()) {
										record.setNig(actuacion.getNig());
									}

									if (actuacion.getValidada() != null && !actuacion.getValidada().isEmpty()) {
										record.setValidada(actuacion.getValidada());
										if (record.getValidada().equals("1")
												&& record.getFechajustificacion() == null) {
											record.setFechajustificacion(new Date());

										}
									}

									record.setFechamodificacion(new Date());

									responseAct = scsActuaciondesignaMapper.updateByPrimaryKey(record);

								}
							}
						}
					}

					LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Actualizacion realizada");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Se ha producido un error ",
							e);
					responseDesig = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.actualizaJustificacionExpres() -> Saliendo del servicio. ");
			}
		}

		if (responseDesig == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}
		
		/*
		 * Se tokenizan los contadores para mostrar el mensaje personalizado en la vista.
		 * Variables de los tokens:
		 * [0] numActualizados; Registros actualizados
		 * [1] numNoActualizadosSinEjg; Registros sin ejg en modo lectura no actualizados
		 * [2] numNoActualizadosEjgSinResolucion; Registros sin resolucion en modo lectura no actualizados
		 * [3] numNoActualizadosEjgNoFavorable; Registros con EJG no favorables en modo lectura no actualizados
		 * [4] numNoActualizadosEjgResolucionPteCajg; Registros con EJG resolución pendiente de CAJG en modo lectura no actualizados
		 */
		error.setMessage(numActualizados + ";"
						+ numNoActualizadosSinEjg + ";"
						+ numNoActualizadosEjgSinResolucion + ";"
						+ numNoActualizadosEjgNoFavorable + ";"
						+ numNoActualizadosEjgResolucionPteCajg);

		responseDTO.setError(error);

		return responseDTO;
	}

	/**
	 * Método que comprueba si existe algún EJG en modo sólo lectura, en caso afirmativo, éste se eliminará
	 * de la lista ocasionando que no se actualice la justificación.
	 * @param filtro
	 * @param listaItem
	 */
	private void compruebaEjgsSoloLectura(JustificacionExpressItem filtro, List<JustificacionExpressItem> listaItem) {
		
		// Inicialización de contadores
		numNoActualizadosSinEjg = 0;
		numNoActualizadosEjgSinResolucion = 0;
		numNoActualizadosEjgNoFavorable = 0;
		numNoActualizadosEjgResolucionPteCajg = 0;
		
		// 1. Comprobamos que exista algún filtro en modo sólo lectura para agilizar el proceso
		if ("1".equals(filtro.getSinEJG())
			|| "1".equals(filtro.getEjgSinResolucion())
			|| "1".equals(filtro.getConEJGNoFavorables())
			|| "1".equals(filtro.getResolucionPTECAJG())) {
			
			// 2. Comprobamos las justificaciones y las eliminamos si no pasan el control
			Iterator<JustificacionExpressItem> i = listaItem.iterator();
			
			while (i.hasNext()) {
				JustificacionExpressItem justificacion = i.next();
				
				if (!"".equals(justificacion.getEjgs()) && !justificacion.getEjgs().isEmpty()) {
					
					// Si el EJG se encuentra sin resolución, no actualizamos la justificacion
					if ("1".equals(filtro.getEjgSinResolucion())
							&& !"".equals(justificacion.getResolucionDesignacion())
							&& !justificacion.getResolucionDesignacion().isEmpty()
							&& "SIN_RESOLUCION".equals(justificacion.getResolucionDesignacion())) {
						i.remove();
						numNoActualizadosEjgSinResolucion++;
					// Si el EJG no es favorable, no actualizamos la justificacion
					} else if ("1".equals(filtro.getConEJGNoFavorables())
							&& !"".equals(justificacion.getResolucionDesignacion())
							&& !justificacion.getResolucionDesignacion().isEmpty()
							&& "NO_FAVORABLE".equals(justificacion.getResolucionDesignacion())) {
						i.remove();
						numNoActualizadosEjgNoFavorable++;
					// Si el EJG tiene resolución pendiente CAJG, no actualizamos la justificacion
					} else if ("1".equals(filtro.getResolucionPTECAJG())
							&& !"".equals(justificacion.getResolucionDesignacion())
							&& !justificacion.getResolucionDesignacion().isEmpty()
							&& "PTE_CAJG".equals(justificacion.getResolucionDesignacion())) {
						i.remove();
						numNoActualizadosEjgResolucionPteCajg++;
					}
					
				// Si no tiene EJGs y el filtro sin EJGs está marcado como modo lectura, eliminamos la justificacion
				} else if ("1".equals(filtro.getSinEJG())) {
					i.remove();
					numNoActualizadosSinEjg++;
				}
			}
		}
	}

	@Override
	public EnviosMasivosDTO busquedaComunicaciones(List<String> comunicaciones, HttpServletRequest request) {
		LOGGER.info("busquedaComunicaciones() -> Entrada al servicio para obtener comunicaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EnviosMasivosDTO enviosMasivosDTO = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosMasivosItem = new ArrayList<EnviosMasivosItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las comunicaciones");

				String[] parts = comunicaciones.get(0).split("/");
				String anio = parts[0].substring(1);
				String num = parts[1];
				String idTurno = comunicaciones.get(1);
//		 		String isLetrado = comunicaciones.get(2);

				enviosMasivosItem = scsDesignacionesExtendsMapper.busquedaComunicaciones(num, anio, idTurno,
						idInstitucion, usuarios.get(0).getIdlenguaje());
//				if (isLetrado != null && isLetrado.equals("false")) {
//					String idpersona = null;
//					comunicacionesItem = scsDesignacionesExtendsMapper.busquedaComunicaciones(anio, num, idTurno,
//							idpersona);
//				} else if(comunicaciones.size() > 3){
//					String idpersona = comunicaciones.get(3);
//					comunicacionesItem = scsDesignacionesExtendsMapper.busquedaComunicaciones(anio, num, idTurno,
//							idpersona);
//				}

				LOGGER.info(
						"busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las comunicaciones");

//				for (int x = 0; x < comunicacionesItem.size(); x++) {
//					comunicacionesItem.get(x)
//							.setDestinatario(comunicacionesItem.get(x).getNombre() + ", "
//									+ comunicacionesItem.get(x).getApellido1() + " "
//									+ comunicacionesItem.get(x).getApellido2());
//				}

				if (enviosMasivosItem != null) {
					enviosMasivosDTO.setEnviosMasivosItem(enviosMasivosItem);
				}
			}

		}
		LOGGER.info("busquedaComunicaciones() -> Salida del servicio para obtener comunicaciones");
		return enviosMasivosDTO;
	}

	@Override
	public ActuacionDesignaItem getHistorioAccionesActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ActuacionDesignaItem actuacionDesignaItem = new ActuacionDesignaItem();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getHistorioAccionesActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getHistorioAccionesActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<ActuacionDesignaItem> listaActuacionDesignaItem = scsDesignacionesExtendsMapper
						.busquedaActDesigna(actuacionDesignaRequestDTO, Short.toString(idInstitucion));

				if (!listaActuacionDesignaItem.isEmpty()) {

					actuacionDesignaItem = listaActuacionDesignaItem.get(0);

					AdmUsuariosExample exampleUsuario = null;
					List<AdmUsuarios> listaUsuarios = null;

					if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getUsuCreacion())) {
						exampleUsuario = new AdmUsuariosExample();
						exampleUsuario.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdusuarioEqualTo(Integer.valueOf(actuacionDesignaItem.getUsuCreacion()));
						listaUsuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuario);
						if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
							actuacionDesignaItem.setUsuCreacion(listaUsuarios.get(0).getDescripcion());
						} else {
							actuacionDesignaItem.setUsuCreacion(usuarios.get(0).getDescripcion());
						}
					} else {
						actuacionDesignaItem.setUsuCreacion(usuarios.get(0).getDescripcion());
					}

					if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getUsuJustificacion())) {
						exampleUsuario = new AdmUsuariosExample();
						exampleUsuario.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdusuarioEqualTo(Integer.valueOf(actuacionDesignaItem.getUsuJustificacion()));
						listaUsuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuario);
						if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
							actuacionDesignaItem.setUsuJustificacion(listaUsuarios.get(0).getDescripcion());
						} else {
							actuacionDesignaItem.setUsuCreacion(usuarios.get(0).getDescripcion());
						}
					} else {
						actuacionDesignaItem.setUsuCreacion(usuarios.get(0).getDescripcion());
					}

					if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getUsuValidacion())) {
						exampleUsuario = new AdmUsuariosExample();
						exampleUsuario.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdusuarioEqualTo(Integer.valueOf(actuacionDesignaItem.getUsuValidacion()));
						listaUsuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuario);
						if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
							actuacionDesignaItem.setUsuValidacion(listaUsuarios.get(0).getDescripcion());
						} else {
							actuacionDesignaItem.setUsuCreacion(usuarios.get(0).getDescripcion());
						}
					} else {
						actuacionDesignaItem.setUsuCreacion(usuarios.get(0).getDescripcion());
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getHistorioAccionesActDesigna() -> Se ha producido un error altratar de obtener el histórico de la acciones realizadas sobre una actuación",
					e);
		}

		return actuacionDesignaItem;
	}

	@Override
	public UpdateResponseDTO updateDesigna(DesignaItem designaItem, HttpServletRequest request) {
		LOGGER.info("updateDetalleDesigna() ->  Entrada al servicio para guardar la tarjeta general designacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Date fechaAntiguaDesigna = null;
		Boolean letradoAsignadoSinTurno = Boolean.FALSE;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info("updateDesigna()-> Entrada a seteo de datos ");
					ScsDesignaExample example = new ScsDesignaExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(designaItem.getIdTurno()).andAnioEqualTo((short) designaItem.getAno())
							.andNumeroEqualTo(Long.valueOf(designaItem.getNumero()));

					List<ScsDesigna> designaExistentes = scsDesignacionesExtendsMapper.selectByExample(example);

					if (designaExistentes == null || (designaExistentes != null && designaExistentes.size() == 0)) {
						error.setCode(400);
						// TODO crear description
						error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
						updateResponseDTO.setStatus(SigaConstants.KO);
						updateResponseDTO.setError(error);
						return updateResponseDTO;
					}

					fechaAntiguaDesigna = designaExistentes.get(0).getFechaentrada();
					ScsDesigna scsDesigna = designaExistentes.get(0);

					// Gestionamos el campo Numero de Front. OJO CAMPO NUMERO EN FRONT = CODIGO EN
					// BBDD!!!!! Se manda en el DesignaItem.codigo
					if (!StringUtils.isEmpty(designaItem.getCodigo())) {
						// Limitacion campo numero en updateDesigna
						// Obtenemos el parametro de limite para el campo CODIGO en BBDD.
						StringDTO parametros = new StringDTO();
						Integer longitudDesigna;
						String codigoDesigna = String.valueOf(designaItem.getCodigo());

						// Comprobamos si existe el valor de codigo designa en el campo codigo de BBDD.
						String codigoBBDD = scsDesignacionesExtendsMapper.comprobarCodigoDesigna(
								String.valueOf(idInstitucion), String.valueOf(designaItem.getAno()),
								String.valueOf(designaItem.getIdTurno()), designaItem.getCodigo());

						if (codigoBBDD != null && !scsDesigna.getCodigo().equals(designaItem.getCodigo())) {
							error.setCode(400);
							// TODO crear description
							error.setDescription("justiciaGratuita.oficio.designa.yaexiste");
							updateResponseDTO.setStatus(SigaConstants.KO);
							updateResponseDTO.setError(error);
							return updateResponseDTO;
						}

						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
								idInstitucion.toString());

						ActuacionDesignaRequestDTO actDesigReqDTO = new ActuacionDesignaRequestDTO();
						actDesigReqDTO.setAnio(String.valueOf(designaItem.getAno()));
						actDesigReqDTO.setIdPersonaColegiado(designaItem.getIdPersona());
						actDesigReqDTO.setIdTurno(String.valueOf(designaItem.getIdTurno()));
						actDesigReqDTO.setNumero(String.valueOf(designaItem.getNumero()));
						List<ActuacionDesignaItem> listaActuacionDesignaItem = scsDesignacionesExtendsMapper
								.busquedaActDesigna(actDesigReqDTO, Short.toString(idInstitucion));

						String fechaActuacion = CollectionUtils.isNotEmpty(listaActuacionDesignaItem)
								? listaActuacionDesignaItem.get(0).getFechaActuacion()
								: null;
						SimpleDateFormat dateSimpleFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date fechaActDate = dateSimpleFormat.parse(fechaActuacion);

						if (designaItem.getFechaAlta().after(fechaActDate)) {
							error.setCode(HttpStatus.NOT_ACCEPTABLE.value());
							error.setDescription("justiciaGratuita.oficio.designa.fechaposteriorprimeractuacion");
//							error.setDescription("La fecha no puede ser posterior a la fecha de primera actuacion (" + fechaActDate.toString() + ")");
							updateResponseDTO.setStatus(SigaConstants.KO);
							updateResponseDTO.setError(error);
							return updateResponseDTO;
						}

						// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
						// la institucion 0
						if (parametros != null && parametros.getValor() != null) {
							longitudDesigna = Integer.parseInt(parametros.getValor());
						} else {
							parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
									"0");
							longitudDesigna = Integer.parseInt(parametros.getValor());
						}

						if (codigoDesigna != null || !codigoDesigna.isEmpty()) {

							// Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
							while (codigoDesigna.length() < longitudDesigna) {
								codigoDesigna = "0" + codigoDesigna;
							}

							scsDesigna.setCodigo(codigoDesigna);

						}

						// Fecha entrada es el que varia con la fecha del form. El dato nos llega en
						// fechaAlta.
						scsDesigna.setFechaentrada(designaItem.getFechaAlta());

						scsDesigna.setFechamodificacion(new Date());
						scsDesigna.setUsumodificacion(usuario.getIdusuario());

						if (designaItem.getIdTipoDesignaColegio() != 0) {
							scsDesigna.setIdtipodesignacolegio((short) designaItem.getIdTipoDesignaColegio());
						}

						scsDesigna.setArt27(designaItem.getArt27());

						// DesignaLetrado
//						***** MOVIDO A PIE DE METODO DESPUES DE VALIDACIONES *****
//						LOGGER.info("updateDesigna() / scsDesignacionesExtendsMapper -> Salida a seteo de datos");
//
//						LOGGER.info(
//								"updateDesigna() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
//
//						scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);
//
//						LOGGER.info(
//								"updateDesigna() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");
//						

						// Modificar Fecha del Colegiado con mas antiguedad.
						/*
						 * ScsDesignasletrado designaletrado = new ScsDesignasletrado();
						 * designaletrado.setIdinstitucion(idInstitucion);
						 * designaletrado.setAnio((short) designaItem.getAno());
						 * designaletrado.setNumero(Long.valueOf(designaItem.getNumero()));
						 * designaletrado.setIdturno(Integer.valueOf(designaItem.getIdTurno()));
						 * //designaletrado.setIdpersona(Long.valueOf(designaItem.getIdPersona()));
						 * designaletrado.setFechadesigna(scsDesigna.getFechaalta());
						 */

						ScsDesignasletradoExample designaletrado = new ScsDesignasletradoExample();

						designaletrado.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andAnioEqualTo((short) designaItem.getAno())
								.andNumeroEqualTo(Long.valueOf(designaItem.getNumero()))
								.andIdturnoEqualTo(Integer.valueOf(designaItem.getIdTurno()))
								.andFechadesignaEqualTo(fechaAntiguaDesigna);
						example.setOrderByClause("FECHADESIGNA ASC");

						// Obtener la Designa Letrado Original.
						List<ScsDesignasletrado> designaletradoOriginal = scsDesignasLetradoExtendMapper
								.selectByExample(designaletrado);

						if (!designaletradoOriginal.isEmpty()) {
							Date fechaOriginal = designaletradoOriginal.get(0).getFechadesigna();

							// TODO [SIGARNV-2334] - Revisar en caso de duda - El Req exponia:
							// Si ha tenido cambio de letrado, LA FECHA tiene que ser siempre antes del
							// primer cambio de letrado.
							if (designaItem.getFechaAlta().after(fechaOriginal)) {
								error.setCode(HttpStatus.NOT_ACCEPTABLE.value());
								error.setDescription("justiciaGratuita.oficio.designa.fechaposteriordesignaletrado");
//								error.setDescription("La fecha no puede ser posterior a la fecha de designacion del letrado (" + dateSimpleFormat.format(fechaOriginal) + ")");
								updateResponseDTO.setStatus(SigaConstants.KO);
								updateResponseDTO.setError(error);
								return updateResponseDTO;
							}

							// 1) Averiguamos el colegiado a partir de su numero para obtener el IdPersona
							CenColegiadoExample cenColegExample = new CenColegiadoExample();
							cenColegExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andNcolegiadoEqualTo(String.valueOf(designaItem.getNumero()));
							List<CenColegiado> resCenColegiado = this.cenColegiadoExtendsMapper
									.selectByExample(cenColegExample);
							CenColegiado cenColeg = CollectionUtils.isNotEmpty(resCenColegiado) ? resCenColegiado.get(0)
									: null;

							if (cenColeg != null) {
								// 2) Averiguamos el/los turnos inscritos del colegiado elegido manualmente
								// (buscador)
								ScsInscripcionturnoExample insTurnoExample = new ScsInscripcionturnoExample();
								insTurnoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andFechadenegacionIsNull().andFechasolicitudIsNotNull()
										.andFechavalidacionIsNotNull().andFechabajaIsNull()
										.andIdpersonaEqualTo(cenColeg.getIdpersona());

								List<ScsInscripcionturno> resInscLetrado = this.scsInscripcionesTurnoExtendsMapper
										.selectByExample(insTurnoExample);

								if (CollectionUtils.isNotEmpty(resInscLetrado)) {
									// 3) Recuperamos las entidades "Turno" iteradas para comparar si estuviera
									// inscrito a alguno de tipo tramitacion (idtipoturno = 1)

									for (ScsInscripcionturno scsTurnoInscrito : resInscLetrado) {
										ScsTurnoExample turnoExample = new ScsTurnoExample();
										turnoExample.createCriteria().andIdturnoEqualTo(scsTurnoInscrito.getIdturno());
										List<ScsTurno> turnoInscritoIt = this.scsTurnosExtendsMapper
												.selectByExample(turnoExample);

										if (CollectionUtils.isNotEmpty(turnoInscritoIt)
												&& Short.toUnsignedInt(turnoInscritoIt.get(0)
														.getIdtipoturno()) != SigaConstants.TIPO_TURNO_DESIGNACION
																.intValue()) {
											error.setCode(HttpStatus.NOT_ACCEPTABLE.value());
											error.setDescription(
													"justiciaGratuita.oficio.designa.designacionletradoturnotramitacion");
											updateResponseDTO.setStatus(SigaConstants.KO);
											updateResponseDTO.setError(error);
											return updateResponseDTO;
										}
									}
								} else {
									letradoAsignadoSinTurno = Boolean.TRUE;
								}
							} else {
								LOGGER.info(
										"+++ DesignacionesServiceImple/createDesigna() -> No se ha encontrado 'CenColegiado' para el NCOLEGIADO = "
												+ designaItem.getNumero());
							}

							// Actualizar La designa Letrado con su fecha correspondiente.
							ScsDesignasletrado designaUpdate = new ScsDesignasletrado();
							designaUpdate.setIdinstitucion(idInstitucion);
							designaUpdate.setAnio((short) designaletradoOriginal.get(0).getAnio());
							designaUpdate.setNumero(Long.valueOf(designaletradoOriginal.get(0).getNumero()));
							designaUpdate.setIdturno(Integer.valueOf(designaletradoOriginal.get(0).getIdturno()));
							designaUpdate.setFechadesigna(designaItem.getFechaAlta());

//							**** INTEGRADO AQUI *****

							LOGGER.info("updateDesigna() / scsDesignacionesExtendsMapper -> Salida a seteo de datos");

							LOGGER.info(
									"updateDesigna() / scsDesignacionesExtendsMapper.update()-> Entrada a scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

							scsDesignacionesExtendsMapper.updateByPrimaryKeySelective(scsDesigna);

							LOGGER.info(
									"updateDesigna() / scsDesignacionesExtendsMapper.update() -> Salida de scsDesignacionesExtendsMapper para insertar tarjeta detalle designaciones");

							LOGGER.info(
									"updateDesigna() / scsDesignasLetradoExtendMapper.update() -> Salida de scsDesignasLetradoExtendMapper para actualizar la fecha del Colegiado");
							scsDesignasLetradoExtendMapper.updateFechaDesignasLetrado(designaUpdate, fechaOriginal);

							LOGGER.info(
									"updateDesigna() / scsDesignasLetradoExtendMapper.update() -> Salida de scsDesignasLetradoExtendMapper para actualizar la fecha del Colegiado");
						}
					}

				} catch (Exception e) {
					LOGGER.error(e);
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la designacion  correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateDetalleDesigna() -> Salida del servicio para actualizar una partida presupuestaria");

			}
		}
		return updateResponseDTO;
	}

	@Override
	public LetradoDesignaDTO getLetradoDesigna(AsuntosClaveJusticiableItem asuntoClave, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		LetradoDesignaDTO letradoDesignaDTO = new LetradoDesignaDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getLetradoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getLetradoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				asuntoClave.setIdInstitucion(idInstitucion.toString());

				List<LetradoDesignaItem> listaLetradoDesignaItem = scsDesignasLetradoExtendMapper
						.getDesignaLetradoPorFecha(asuntoClave);

				letradoDesignaDTO.setListaLetradosDesignaItem(listaLetradoDesignaItem);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getLetradoDesigna() -> Se ha producido un error al tratar de obtener el letrado designado",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			letradoDesignaDTO.setError(error);
		}

		return letradoDesignaDTO;
	}

	private Long uploadFileActDesigna(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
			String extension) {

		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(idInstitucion);
		ficheroVo.setDirectorio(directorioFichero);
		ficheroVo.setNombre(nombreFichero);
		ficheroVo.setDescripcion("Fichero asociado a la actuación " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(idInstitucion);
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(extension.toLowerCase());

		ficheroVo.setUsumodificacion(idUsuario);
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());

		return ficheroVo.getIdfichero();
	}

	private Long uploadFileDesigna(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
			String extension) {

		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFicheroDes(idInstitucion);
		ficheroVo.setDirectorio(directorioFichero);
		ficheroVo.setNombre(nombreFichero);
		ficheroVo.setDescripcion("Fichero asociado a la designación " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(idInstitucion);
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(extension.toLowerCase());

		ficheroVo.setUsumodificacion(idUsuario);
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());

		return ficheroVo.getIdfichero();
	}

	private String getDirectorioFichero(Short idInstitucion) {

		// Extraemos el path para los ficheros
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String path = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(path);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraemos el path concreto para actuaciones
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.actuaciones");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		return directorioFichero.toString();
	}

	private String getDirectorioFicheroDes(Short idInstitucion) {

		// Extraemos el path para los ficheros
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String path = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(path);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraemos el path concreto para actuaciones
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.designaciones");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		return directorioFichero.toString();
	}

	@Override
	public InsertResponseDTO subirDocumentoActDesigna(MultipartHttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				Iterator<String> itr = request.getFileNames();

				while (itr.hasNext()) {

					MultipartFile file = request.getFile(itr.next());
					String nombreFichero = file.getOriginalFilename().split(";")[0];
					String json = file.getOriginalFilename().split(";")[1].replaceAll("%22", "\"");
					DocumentoActDesignaItem documentoActDesignaItem = objectMapper.readValue(json,
							DocumentoActDesignaItem.class);
					String extension = FilenameUtils.getExtension(nombreFichero);

					Long idFile = uploadFileActDesigna(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion,
							nombreFichero, extension);

					MaxIdDto nuevoId = scsDesignacionesExtendsMapper.getNewIdDocumentacionAsi(idInstitucion);

					ScsDocumentacionasi scsDocumentacionasi = new ScsDocumentacionasi();

					scsDocumentacionasi.setAnio(Short.valueOf(documentoActDesignaItem.getAnio()));
					scsDocumentacionasi.setNumero(Long.valueOf(documentoActDesignaItem.getNumero()));
					scsDocumentacionasi.setIdactuacion(Long.valueOf(documentoActDesignaItem.getIdActuacion()));
					if (!UtilidadesString.esCadenaVacia(documentoActDesignaItem.getObservaciones())) {
						scsDocumentacionasi.setObservaciones(documentoActDesignaItem.getObservaciones());
					}
					scsDocumentacionasi.setNombrefichero(nombreFichero);
					scsDocumentacionasi.setIddocumentacionasi(Integer.valueOf(nuevoId.getIdMax().toString()));
					scsDocumentacionasi.setIdtipodocumento(Short.valueOf("2"));
					scsDocumentacionasi.setIdfichero(idFile);
					scsDocumentacionasi.setIdinstitucion(idInstitucion);
					scsDocumentacionasi.setUsumodificacion(usuarios.get(0).getIdusuario());
					scsDocumentacionasi.setFechamodificacion(new Date());
					scsDocumentacionasi.setFechaentrada(new Date());

					int response = scsDocumentacionasiMapper.insertSelective(scsDocumentacionasi);

					if (response == 1) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.subirDocumentoActDesigna() -> Se ha producido un error al subir un fichero perteneciente a la actuación");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						insertResponseDTO.setError(error);
					}

				}

				String documentos = request.getParameter("documentosActualizar");
				List<DocumentoActDesignaItem> listaDocumentos = objectMapper.readValue(documentos,
						new TypeReference<List<DocumentoActDesignaItem>>() {
						});

				if (listaDocumentos != null && !listaDocumentos.isEmpty()) {

					for (DocumentoActDesignaItem doc : listaDocumentos) {

						ScsDocumentacionasi scsDocumentacionasi = new ScsDocumentacionasi();

						if (!UtilidadesString.esCadenaVacia(doc.getObservaciones())) {
							scsDocumentacionasi.setObservaciones(doc.getObservaciones());
						}
						scsDocumentacionasi.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsDocumentacionasi.setFechamodificacion(new Date());
						scsDocumentacionasi.setIddocumentacionasi(Integer.valueOf(doc.getIdDocumentacionasi()));
						scsDocumentacionasi.setIdinstitucion(idInstitucion);

						int response2 = scsDocumentacionasiMapper.updateByPrimaryKeySelective(scsDocumentacionasi);

						if (response2 == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
						}

						if (response2 == 0) {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.error(
									"DesignacionesServiceImpl.subirDocumentoActDesigna() -> Se ha producido un error al actualizar la información relacionada al documento de la actuación");
							error.setCode(500);
							error.setDescription("general.mensaje.error.bbdd");
							insertResponseDTO.setError(error);
						}

					}

				}
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.subirDocumentoActDesigna2() -> Se ha producido un error al subir un fichero perteneciente a la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public DocumentoActDesignaDTO getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DocumentoActDesignaDTO documentoActDesignaDTO = new DocumentoActDesignaDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<DocumentoActDesignaItem> listaDocumentoActDesignaItem = scsDesignacionesExtendsMapper
						.getDocumentosPorActDesigna(documentoActDesignaItem, idInstitucion);

				documentoActDesignaDTO.setListaDocumentoActDesignaItem(listaDocumentoActDesignaItem);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() -> Se ha producido un error en la búsqueda de documentos asociados a la actuacion",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			documentoActDesignaDTO.setError(error);
		}

		return documentoActDesignaDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumentosActDesigna(
			List<DocumentoActDesignaItem> listaDocumentoActDesignaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;
		InputStream fileStream = null;
		HttpHeaders headers = new HttpHeaders();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !listaDocumentoActDesignaItem.isEmpty()) {

				if (listaDocumentoActDesignaItem.size() == 1) {

					String path = getDirectorioFichero(idInstitucion);
					path += File.separator + idInstitucion + "_" + listaDocumentoActDesignaItem.get(0).getIdFichero()
							+ listaDocumentoActDesignaItem.get(0).getExtension().toLowerCase();

					File file = new File(path);
					fileStream = new FileInputStream(file);

					String tipoMime = getMimeType(listaDocumentoActDesignaItem.get(0).getExtension());

					headers.setContentType(MediaType.parseMediaType(tipoMime));
					headers.set("Content-Disposition",
							"attachment; filename=\"" + listaDocumentoActDesignaItem.get(0).getNombreFichero() + "\"");
					headers.setContentLength(file.length());

				} else {
					fileStream = getZipFileDocumentosActDesigna(listaDocumentoActDesignaItem, idInstitucion);

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"documentos.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.descargarDocumentosActDesigna() -> Se ha producido un error al descargar un archivo asociado a la actuacion",
					e);
		}

		return res;
	}

	private InputStream getZipFileDocumentosActDesigna(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem,
			Short idInstitucion) {

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (DocumentoActDesignaItem doc : listaDocumentoActDesignaItem) {

				zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
				String path = getDirectorioFichero(idInstitucion);
				path += File.separator + idInstitucion + "_" + doc.getIdFichero() + doc.getExtension().toLowerCase();
				File file = new File(path);
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
			}

			zipOutputStream.closeEntry();

			if (zipOutputStream != null) {
				zipOutputStream.finish();
				zipOutputStream.flush();
				IOUtils.closeQuietly(zipOutputStream);
			}

			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	private InputStream getZipFileDocumentosDesigna(List<DocumentoDesignaItem> listaDocumentoDesignaItem,
			Short idInstitucion) {

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (DocumentoDesignaItem doc : listaDocumentoDesignaItem) {

				zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
				String extension = doc.getNombreFichero()
						.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
						.toLowerCase();
				String path = getDirectorioFicheroDes(idInstitucion);
				path += File.separator + idInstitucion + "_" + doc.getIdFichero() + extension;
				File file = new File(path);
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
			}

			zipOutputStream.closeEntry();

			if (zipOutputStream != null) {
				zipOutputStream.finish();
				zipOutputStream.flush();
				IOUtils.closeQuietly(zipOutputStream);
			}

			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	private String getMimeType(String extension) {

		String mime = "";

		switch (extension.toLowerCase()) {

		case ".doc":
			mime = "application/msword";
			break;
		case ".docx":
			mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			break;
		case ".pdf":
			mime = "application/pdf";
			break;
		case ".jpg":
			mime = "image/jpeg";
			break;
		case ".png":
			mime = "image/png";
			break;
		case ".rtf":
			mime = "application/rtf";
			break;
		case ".txt":
			mime = "text/plain";
			break;
		}

		return mime;
	}

	@Override
	public DeleteResponseDTO eliminarDocumentosActDesigna(List<DocumentoActDesignaItem> listaDocumentoActDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentosActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentosActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				for (DocumentoActDesignaItem doc : listaDocumentoActDesignaItem) {

					String path = getDirectorioFichero(idInstitucion);
					path += File.separator + idInstitucion + "_" + doc.getIdFichero()
							+ doc.getExtension().toLowerCase();

					File file = new File(path);

					if (file.exists()) {
						file.delete();
					}

					ScsDocumentacionasiKey scsDocumentacionasiKey = new ScsDocumentacionasiKey();

					scsDocumentacionasiKey.setIddocumentacionasi(Integer.valueOf(doc.getIdDocumentacionasi()));
					scsDocumentacionasiKey.setIdinstitucion(idInstitucion);

					int response = scsDocumentacionasiMapper.deleteByPrimaryKey(scsDocumentacionasiKey);

					if (response == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.eliminarDocumentosActDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la actuacion");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						deleteResponseDTO.setError(error);
					}

					GenFicheroKey genFicheroKey = new GenFicheroKey();

					genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
					genFicheroKey.setIdinstitucion(idInstitucion);

					int response2 = genFicheroMapper.deleteByPrimaryKey(genFicheroKey);

					if (response2 == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response2 == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.eliminarDocumentosActDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la actuacion");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						deleteResponseDTO.setError(error);
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentosActDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la actuacion",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public DocumentoDesignaDTO getDocumentosPorDesigna(DocumentoDesignaItem documentoDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DocumentoDesignaDTO documentoDesignaDTO = new DocumentoDesignaDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDocumentosPorDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				List<DocumentoDesignaItem> listaDocumentoActDesignaItem = scsDesignacionesExtendsMapper
						.getDocumentosPorDesigna(documentoDesignaItem, idInstitucion);

				documentoDesignaDTO.setListaDocumentoDesignaItem(listaDocumentoActDesignaItem);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getDocumentosPorDesigna() -> Se ha producido un error en la búsqueda de documentos asociados a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			documentoDesignaDTO.setError(error);
		}

		return documentoDesignaDTO;
	}

	@Override
	public InsertResponseDTO subirDocumentoDesigna(MultipartHttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				Iterator<String> itr = request.getFileNames();

				while (itr.hasNext()) {

					MultipartFile file = request.getFile(itr.next());
					String nombreFichero = file.getOriginalFilename().split(";")[0];
					String json = file.getOriginalFilename().split(";")[1].replaceAll("%22", "\"");
					DocumentoDesignaItem documentoDesignaItem = objectMapper.readValue(json,
							DocumentoDesignaItem.class);
					String extension = FilenameUtils.getExtension(nombreFichero);

					Long idFile = uploadFileDesigna(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion,
							nombreFichero, extension);

					MaxIdDto nuevoId = scsDesignacionesExtendsMapper.getNewIdDocumentacionDes(idInstitucion);

					ScsDocumentaciondesigna scsDocumentaciondesigna = new ScsDocumentaciondesigna();

					scsDocumentaciondesigna.setAnio(Short.valueOf(documentoDesignaItem.getAnio()));
					scsDocumentaciondesigna.setNumero(Long.valueOf(documentoDesignaItem.getNumero()));
					scsDocumentaciondesigna.setIdturno(Integer.valueOf(documentoDesignaItem.getIdTurno()));

					if (!UtilidadesString.esCadenaVacia(documentoDesignaItem.getObservaciones())) {
						scsDocumentaciondesigna.setObservaciones(documentoDesignaItem.getObservaciones());
					}
					scsDocumentaciondesigna.setIddocumentaciondes(Integer.valueOf(nuevoId.getIdMax().toString()));
					scsDocumentaciondesigna.setNombrefichero(nombreFichero);
					scsDocumentaciondesigna
							.setIdtipodocumento(Short.valueOf(documentoDesignaItem.getIdTipodocumento()));
					scsDocumentaciondesigna.setIdfichero(idFile);
					scsDocumentaciondesigna.setIdinstitucion(idInstitucion);
					scsDocumentaciondesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
					scsDocumentaciondesigna.setFechamodificacion(new Date());
					scsDocumentaciondesigna.setFechaentrada(new Date());

					if (!UtilidadesString.esCadenaVacia(documentoDesignaItem.getIdActuacion())) {
						scsDocumentaciondesigna.setIdactuacion(Long.valueOf(documentoDesignaItem.getIdActuacion()));
						scsDocumentaciondesigna.setIdtipodocumento(Short.valueOf("1"));
					}

					int response = scsDocumentaciondesignaMapper.insertSelective(scsDocumentaciondesigna);

					if (response == 1) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.subirDocumentoDesigna() -> Se ha producido un error al subir un fichero perteneciente a la designación");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						insertResponseDTO.setError(error);
					}

				}

				String documentos = request.getParameter("documentosActualizar");
				List<DocumentoDesignaItem> listaDocumentos = objectMapper.readValue(documentos,
						new TypeReference<List<DocumentoDesignaItem>>() {
						});

				DocumentoDesignaItem docuDesigItem = new DocumentoDesignaItem();

				docuDesigItem.setAnio(listaDocumentos.get(0).getAnio());
				docuDesigItem.setNumero(listaDocumentos.get(0).getNumero());
				docuDesigItem.setIdTurno(listaDocumentos.get(0).getIdTurno());
				docuDesigItem.setIdActuacion(listaDocumentos.get(0).getIdActuacion());

				List<DocumentoDesignaItem> listaDocumentosBBDD = scsDesignacionesExtendsMapper
						.getDocumentosPorDesigna(docuDesigItem, idInstitucion);

				if (listaDocumentos != null && !listaDocumentos.isEmpty()) {

					for (DocumentoDesignaItem doc : listaDocumentos) {
						boolean igual = false;
						for (DocumentoDesignaItem doc2 : listaDocumentosBBDD) {

							if (doc2.getNombreFichero().equals(doc.getNombreFichero())
									&& doc2.getObservaciones().equals(doc.getObservaciones())) {
								igual = true;
							}

						}

						if (!igual) {
							ScsDocumentaciondesigna scsDocumentaciondesigna = new ScsDocumentaciondesigna();

							if (!UtilidadesString.esCadenaVacia(doc.getObservaciones())) {
								scsDocumentaciondesigna.setObservaciones(doc.getObservaciones());
							}

							scsDocumentaciondesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
							scsDocumentaciondesigna.setFechamodificacion(new Date());
							scsDocumentaciondesigna.setIdinstitucion(idInstitucion);
							scsDocumentaciondesigna.setIddocumentaciondes(Integer.valueOf(doc.getIdDocumentaciondes()));

							int response2 = scsDocumentaciondesignaMapper
									.updateByPrimaryKeySelective(scsDocumentaciondesigna);

							if (response2 == 1) {
								insertResponseDTO.setStatus(SigaConstants.OK);
							}

							if (response2 == 0) {
								insertResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.error(
										"DesignacionesServiceImpl.subirDocumentoDesigna() -> Se ha producido un error al subir un fichero perteneciente a la designación");
								error.setCode(500);
								error.setDescription("general.mensaje.error.bbdd");
								insertResponseDTO.setError(error);
							}
						}

					}

				}

			} else {
				LOGGER.error(
						"DesignacionesServiceImpl.subirDocumentoDesigna() -> Se ha producido un error, el usuario no pertenece a la tabla de cen_persona o a la tabla de adm_usuarios");
				error.setCode(500);
				error.setDescription("general.mensaje.error.bbdd");
				error.setMessage("Este usuario no puede subir ficheros.");
				insertResponseDTO.setError(error);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.subirDocumentoDesigna() -> Se ha producido un error al subir un fichero perteneciente a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);

		}

		return insertResponseDTO;
	}

	@Override
	public DeleteResponseDTO eliminarDocumentosDesigna(List<DocumentoDesignaItem> listaDocumentoDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				for (DocumentoDesignaItem doc : listaDocumentoDesignaItem) {

					String path = getDirectorioFicheroDes(idInstitucion);
					path += File.separator + idInstitucion + "_" + doc.getIdFichero()
							+ doc.getNombreFichero()
									.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
									.toLowerCase();

					File file = new File(path);

					if (file.exists()) {
						file.delete();
					}

					ScsDocumentaciondesignaKey scsDocumentaciondesignaKey = new ScsDocumentaciondesignaKey();

					scsDocumentaciondesignaKey.setIddocumentaciondes(Integer.valueOf(doc.getIdDocumentaciondes()));
					scsDocumentaciondesignaKey.setIdinstitucion(idInstitucion);

					int response = scsDocumentaciondesignaMapper.deleteByPrimaryKey(scsDocumentaciondesignaKey);

					if (response == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.eliminarDocumentosDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la designación");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						deleteResponseDTO.setError(error);
					}

					GenFicheroKey genFicheroKey = new GenFicheroKey();

					genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
					genFicheroKey.setIdinstitucion(idInstitucion);

					int response2 = genFicheroMapper.deleteByPrimaryKey(genFicheroKey);

					if (response2 == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}

					if (response2 == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"DesignacionesServiceImpl.eliminarDocumentosDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la designación");
						error.setCode(500);
						error.setDescription("general.mensaje.error.bbdd");
						deleteResponseDTO.setError(error);
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentosDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumentosDesigna(
			List<DocumentoDesignaItem> listaDocumentoDesignaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;
		InputStream fileStream = null;
		HttpHeaders headers = new HttpHeaders();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.descargarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.descargarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !listaDocumentoDesignaItem.isEmpty()) {

				if (listaDocumentoDesignaItem.size() == 1) {

					String extension = listaDocumentoDesignaItem.get(0).getNombreFichero()
							.substring(listaDocumentoDesignaItem.get(0).getNombreFichero().lastIndexOf("."),
									listaDocumentoDesignaItem.get(0).getNombreFichero().length())
							.toLowerCase();
					String path = getDirectorioFicheroDes(idInstitucion);
					path += File.separator + idInstitucion + "_" + listaDocumentoDesignaItem.get(0).getIdFichero()
							+ extension;

					File file = new File(path);
					fileStream = new FileInputStream(file);

					String tipoMime = getMimeType(extension);

					headers.setContentType(MediaType.parseMediaType(tipoMime));
					headers.set("Content-Disposition",
							"attachment; filename=\"" + listaDocumentoDesignaItem.get(0).getNombreFichero() + "\"");
					headers.setContentLength(file.length());

				} else {
					fileStream = getZipFileDocumentosDesigna(listaDocumentoDesignaItem, idInstitucion);

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"documentos.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.descargarDocumentosActDesigna() -> Se ha producido un error al descargar un archivo asociado a la actuacion",
					e);
		}

		return res;
	}

	@Override
	public InsertResponseDTO asociarEjgDesigna(List<String> item, HttpServletRequest request) {
		InsertResponseDTO responseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.asociarEjgDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.asociarEjgDesigna() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.asociarEjgDesigna() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.asociarEjgDesigna() -> Haciendo el insert...");

					// Transformamos la lista de valores en objetos para mejor comprension
					// item = [newDesigna.ano.toString(), this.datosEJG.annio,
					// this.datosEJG.tipoEJG,
					// newDesigna.idTurno.toString(), newDesigna.numero.toString(),
					// this.datosEJG.numero]
					// Designacion a asociar
					DesignaItem designaItem = new DesignaItem();

					designaItem.setAno(Integer.parseInt(item.get(0)));
					designaItem.setNumero(Integer.parseInt(item.get(4)));
					// Comprobamos si se ha enviado el nombre del turno desde
					// la busqueda de asuntos en lugar de el id desde la ficha de designacion.
//					contains("[a-zA-Z]+")   .matches("[0-9]+")
					if (item.get(3).matches("[0-9]+") && item.get(3).length() > 2) {
						designaItem.setIdTurno(Integer.parseInt(item.get(3)));
					} else {
						TurnosItem turnosItem = new TurnosItem();
						turnosItem.setAbreviatura(item.get(3));
						List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion,
								usuarios.get(0).getIdlenguaje());
						if(turnos==null || turnos.isEmpty()) {
							turnosItem.setAbreviatura("");
							turnosItem.setIdturno(item.get(3));
							turnos = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion,
									usuarios.get(0).getIdlenguaje());
						}
						if(turnos!=null && !turnos.isEmpty()) {
							designaItem.setIdTurno(Integer.parseInt(turnos.get(0).getIdturno()));
						}
					}

					// EJG a asociar
					EjgItem ejg = new EjgItem();

					ejg.setAnnio(item.get(1));
					ejg.setTipoEJG(item.get(2));
					ejg.setNumero(item.get(5));

					// Objeto que vamos a insertar en la base de datos
					ScsEjgdesigna record = new ScsEjgdesigna();

					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setIdinstitucion(idInstitucion);
					record.setAnioejg(Short.parseShort(ejg.getAnnio()));
					record.setIdtipoejg(Short.parseShort(ejg.getTipoEJG()));
					record.setNumeroejg(Long.parseLong(ejg.getNumero()));
					record.setIdturno(designaItem.getIdTurno());
					record.setAniodesigna((short) designaItem.getAno());
					// record.setNumerodesigna((long) designaItem.getNumero());

					// Debido a que no podemos obtener el numero de la designacion sino su codigo,
					// tendremos que realizar una busqueda extra para poder extraer el numero de la
					// designacion.

					// Replicamos el codigo que se utiliza para obtener el codigo para determinar
					// la longitud a utilizar a la hora de la busqueda.

					Integer longitudDesigna;
					String codigoDesigna = Integer.toString(designaItem.getNumero());

					StringDTO parametros = genParametrosExtendsMapper
							.selectParametroPorInstitucion("LONGITUD_CODDESIGNA", idInstitucion.toString());

					// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
					// la institucion 0
					if (parametros != null && parametros.getValor() != null) {
						longitudDesigna = Integer.parseInt(parametros.getValor());
					} else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODDESIGNA",
								"0");
						longitudDesigna = Integer.parseInt(parametros.getValor());
					}

					// Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
					while (codigoDesigna.length() < longitudDesigna) {
						codigoDesigna = "0" + codigoDesigna;
					}

					ScsDesignaExample designaExample = new ScsDesignaExample();

					designaExample.createCriteria().andAnioEqualTo((short) designaItem.getAno())
							.andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(designaItem.getIdTurno())
							.andCodigoEqualTo(codigoDesigna);

					List<ScsDesigna> designasCodigo = scsDesignaMapper.selectByExample(designaExample);

					record.setNumerodesigna((long) designasCodigo.get(0).getNumero());

					response = scsEjgdesignaMapper.insert(record);

					LOGGER.info("DesignacionesServiceImpl.asociarEjgDesigna() -> Insert finalizado");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.asociarEjgDesigna() -> Se ha producido un error ", e);
					response = 0;
				}

				LOGGER.info("DesignacionesServiceImpl.asociarEjgDesigna() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}

	private Error comprobacionesActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion,
			boolean isActualizar) {

		Error error = new Error();
		ScsAcreditacionExample acreditacionexample = new ScsAcreditacionExample();
		ScsActuaciondesignaExample actuaciondesignaexample = new ScsActuaciondesignaExample();
		List<ScsAcreditacion> acreditaciones = null;
		String errorAcreditacion = "justiciaGratuita.oficio.designas.actuaciones.acreditacion.error";

		try {
			// Comprobamos si el turno tiene activa las restricciones para las
			// acreditaciones
			ScsTurnoKey scsTurnoKey = new ScsTurnoKey();
			scsTurnoKey.setIdinstitucion(idInstitucion);
			scsTurnoKey.setIdturno(Integer.valueOf(actuacionDesignaItem.getIdTurno()));

			ScsTurno scsTurno = scsTurnoMapper.selectByPrimaryKey(scsTurnoKey);

			if (scsTurno.getActivarretriccionacredit().toUpperCase().equals("1")
					|| scsTurno.getActivarretriccionacredit().toUpperCase().equals("S")) {

				// Obtenemos el tipo de acreditación que se quiere guardar
				acreditacionexample = new ScsAcreditacionExample();
				acreditacionexample.createCriteria()
						.andIdacreditacionEqualTo(Short.valueOf(actuacionDesignaItem.getIdAcreditacion()));
				acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
				String tipoAacreditacionAInsertar = acreditaciones.get(0).getIdtipoacreditacion().toString();

				// Obtenemos las actuaciones relacionadas a la designa por modulo
				actuaciondesignaexample = new ScsActuaciondesignaExample();
				actuaciondesignaexample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.valueOf(actuacionDesignaItem.getIdTurno()))
						.andAnioEqualTo(Short.valueOf(actuacionDesignaItem.getAnio()))
						.andNumeroEqualTo(Long.valueOf(actuacionDesignaItem.getNumero()))
						.andIdprocedimientoEqualTo(actuacionDesignaItem.getIdProcedimiento());
				actuaciondesignaexample.setOrderByClause("NUMEROASUNTO ASC");

				List<ScsActuaciondesigna> listaActuaciones = scsActuaciondesignaMapper
						.selectByExample(actuaciondesignaexample);

				// Si se trata de la primera actuación, esta solo podrá ser de INICIO o
				// COMPLETA, a no ser que tenga activado el check "Admite múltiples"
				if (listaActuaciones.isEmpty()) {

					if (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
							|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)) {
						error.setCode(200);
					} else {
						error.setCode(500);
						error.setDescription(errorAcreditacion);
					}

				} else if (listaActuaciones.size() == 1) {

					acreditacionexample = new ScsAcreditacionExample();
					acreditacionexample.createCriteria()
							.andIdacreditacionEqualTo(Short.valueOf(listaActuaciones.get(0).getIdacreditacion()));

					acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
					String tipoAcreditacion = acreditaciones.get(0).getIdtipoacreditacion().toString();

					if (!isActualizar) {

						if (tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
								|| (tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
										&& !tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_FIN))) {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						} else {
							error.setCode(200);

						}

					}

					if (isActualizar) {

						if (((tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
								&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
										|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)))
								|| (tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
										&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
												|| tipoAacreditacionAInsertar
														.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA))))) {
							error.setCode(200);
						} else {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						}

					}

				} else if (listaActuaciones.size() == 2) {

					ScsActuaciondesigna actuacionAnterior = listaActuaciones.get(listaActuaciones.size() - 1);
					acreditacionexample = new ScsAcreditacionExample();
					acreditacionexample.createCriteria()
							.andIdacreditacionEqualTo(Short.valueOf(actuacionAnterior.getIdacreditacion()));
					acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
					String tipoAcreditacionActAnterior = acreditaciones.get(0).getIdtipoacreditacion().toString();
					String tipoAcreditacionNoUltima = "";

					if (!isActualizar) {
						error.setCode(500);
						error.setDescription(errorAcreditacion);
					}

					if (isActualizar) {

						int index = IntStream
								.range(0, listaActuaciones.size()).filter(actInd -> listaActuaciones.get(actInd)
										.getNumeroasunto().toString().equals(actuacionDesignaItem.getNumeroAsunto()))
								.findFirst().getAsInt();

						boolean isUltima = index == (listaActuaciones.size() - 1);

						if (!isUltima) {
							acreditacionexample = new ScsAcreditacionExample();
							acreditacionexample.createCriteria().andIdacreditacionEqualTo(
									Short.valueOf(listaActuaciones.get(index).getIdacreditacion()));
							acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
							tipoAcreditacionNoUltima = acreditaciones.get(0).getIdtipoacreditacion().toString();
						}

						if ((isUltima && ((tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
								&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO))
								|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA))
								|| (tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
										&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO))
										|| tipoAacreditacionAInsertar
												.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA))))
								|| (!isUltima && (tipoAcreditacionNoUltima.equals(tipoAacreditacionAInsertar)))) {
							error.setCode(200);
						} else {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						}

					}

				}
			} else {
				error.setCode(200);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.comprobacionesActDesigna() -> Se ha producido un error al realizar las comprobaciones realacionadas a la actuación de la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
		}

		return error;
	}

	private Error comprobacionesActDesignaMultiples(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion,
			boolean isActualizar) {

		Error error = new Error();
		ScsActuaciondesignaExample actuaciondesignaexample = new ScsActuaciondesignaExample();
		ScsAcreditacionExample acreditacionexample = new ScsAcreditacionExample();
		List<ScsAcreditacion> acreditaciones = null;
		String errorAcreditacion = "justiciaGratuita.oficio.designas.actuaciones.acreditacion.error";

		try {

			// Comprobamos si el turno tiene activa las restricciones para las
			// acreditaciones
			ScsTurnoKey scsTurnoKey = new ScsTurnoKey();
			scsTurnoKey.setIdinstitucion(idInstitucion);
			scsTurnoKey.setIdturno(Integer.valueOf(actuacionDesignaItem.getIdTurno()));

			ScsTurno scsTurno = scsTurnoMapper.selectByPrimaryKey(scsTurnoKey);

			if (scsTurno.getActivarretriccionacredit().toUpperCase().equals("1")
					|| scsTurno.getActivarretriccionacredit().toUpperCase().equals("S")) {

				// Obtenemos el tipo de acreditación que se quiere guardar
				acreditacionexample = new ScsAcreditacionExample();
				acreditacionexample.createCriteria()
						.andIdacreditacionEqualTo(Short.valueOf(actuacionDesignaItem.getIdAcreditacion()));
				acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
				String tipoAacreditacionAInsertar = acreditaciones.get(0).getIdtipoacreditacion().toString();

				// Obtenemos las actuaciones relacionadas a la designa por modulo
				actuaciondesignaexample = new ScsActuaciondesignaExample();
				actuaciondesignaexample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdturnoEqualTo(Integer.valueOf(actuacionDesignaItem.getIdTurno()))
						.andAnioEqualTo(Short.valueOf(actuacionDesignaItem.getAnio()))
						.andNumeroEqualTo(Long.valueOf(actuacionDesignaItem.getNumero()))
						.andIdprocedimientoEqualTo(actuacionDesignaItem.getIdProcedimiento());
				actuaciondesignaexample.setOrderByClause("NUMEROASUNTO ASC");

				List<ScsActuaciondesigna> listaActuaciones = scsActuaciondesignaMapper
						.selectByExample(actuaciondesignaexample);

				if (listaActuaciones.isEmpty()) {

					if (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
							|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)) {
						error.setCode(200);
					} else {
						error.setCode(500);
						error.setDescription(errorAcreditacion);
					}

				} else if (listaActuaciones.size() == 1) {

					acreditacionexample = new ScsAcreditacionExample();
					acreditacionexample.createCriteria()
							.andIdacreditacionEqualTo(Short.valueOf(listaActuaciones.get(0).getIdacreditacion()));

					acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
					String tipoAcreditacion = acreditaciones.get(0).getIdtipoacreditacion().toString();

					if (!isActualizar) {

						if (tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
								&& !tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_FIN)) {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						} else {
							error.setCode(200);
						}

					}

					if (isActualizar) {

						if (((tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
								&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
										|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)))
								|| (tipoAcreditacion.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
										&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
												|| tipoAacreditacionAInsertar
														.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA))))) {
							error.setCode(200);
						} else {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						}

					}

				} else if (listaActuaciones.size() > 1) {

					ScsActuaciondesigna actuacionAnterior = listaActuaciones.get(listaActuaciones.size() - 1);
					acreditacionexample = new ScsAcreditacionExample();
					acreditacionexample.createCriteria()
							.andIdacreditacionEqualTo(Short.valueOf(actuacionAnterior.getIdacreditacion()));
					acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
					String tipoAcreditacionActAnterior = acreditaciones.get(0).getIdtipoacreditacion().toString();
					String tipoAcreditacionNoUltima = "";

					if (!isActualizar) {

						if ((tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_FIN)
								&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
										|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)))
								|| (tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
										&& tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_FIN))
								|| (tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
										&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
												|| tipoAacreditacionAInsertar
														.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)))) {
							error.setCode(200);
						} else {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						}
					}

					if (isActualizar) {

						int index = IntStream
								.range(0, listaActuaciones.size()).filter(actInd -> listaActuaciones.get(actInd)
										.getNumeroasunto().toString().equals(actuacionDesignaItem.getNumeroAsunto()))
								.findFirst().getAsInt();

						boolean isUltima = index == (listaActuaciones.size() - 1);

						if (!isUltima) {
							acreditacionexample = new ScsAcreditacionExample();
							acreditacionexample.createCriteria().andIdacreditacionEqualTo(
									Short.valueOf(listaActuaciones.get(index).getIdacreditacion()));
							acreditaciones = scsAcreditacionMapper.selectByExample(acreditacionexample);
							tipoAcreditacionNoUltima = acreditaciones.get(0).getIdtipoacreditacion().toString();
						}

						if (isUltima
								&& ((tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_INICIO)
										&& (tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_INICIO))
										|| tipoAacreditacionAInsertar.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA))
										|| (tipoAcreditacionActAnterior.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)
												&& (tipoAacreditacionAInsertar
														.equals(SigaConstants.ACREDITACION_TIPO_INICIO))
												|| tipoAacreditacionAInsertar
														.equals(SigaConstants.ACREDITACION_TIPO_COMPLETA)))
								|| (!isUltima && (tipoAcreditacionNoUltima.equals(tipoAacreditacionAInsertar)))) {
							error.setCode(200);
						} else {
							error.setCode(500);
							error.setDescription(errorAcreditacion);
						}

					}

				}

			} else {
				error.setCode(200);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.comprobacionesActDesignaMultiples() -> Se ha producido un error al realizar las comprobaciones realacionadas a la actuación de la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
		}

		return error;
	}

	@Override
	@Transactional
	public String busquedaJuzgadoDesignas(Integer idJuzgado, HttpServletRequest request) {
		// DesignaItem result = new DesignaItem();
		String designas = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJuzgadoDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaJuzgadoDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("CEN")
					.andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaJuzgadoDesignas -> Entrada a servicio para la busqueda de ModuloDesignas");

				try {
					designas = scsDesignacionesExtendsMapper.busquedaJuzgadoDesignas(idJuzgado, idInstitucion,
							tamMaximo);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaJuzgadoDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaJuzgadoDesignas -> Salida del servicio");
			}
		}

		return designas;
	}

	@Override
	public UpdateResponseDTO actualizarPartidaPresupuestariaActDesigna(ActuacionDesignaItem actuacionDesignaItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.actualizarPartidaPresupuestariaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.actualizarPartidaPresupuestariaActDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				int response = scsDesignacionesExtendsMapper.actualizarPartidaPresupuestariaActDesigna(
						actuacionDesignaItem, idInstitucion, usuarios.get(0));

				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

				if (response == 0) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.error(
							"DesignacionesServiceImpl.actualizarPartidaPresupuestariaActDesigna() -> Se ha producido un error al altualizar la partidapresupuestaria de la actuación");
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setError(error);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.actualizarPartidaPresupuestariaActDesigna() -> Se ha producido un error al altualizar la partidapresupuestaria de la actuación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	public ListDTO getDelitos(DesignaItem designaItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ListDTO listDTO = new ListDTO();
		Error error = new Error();

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.getDelitos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getDelitos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();

				delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andAnioEqualTo((short) designaItem.getAno()).andNumeroEqualTo((long) designaItem.getNumero())
						.andIdturnoEqualTo(designaItem.getIdTurno());

				List<ScsDelitosdesigna> listaDelitos = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);

				// List<String> listaDelitos =
				// scsDesignacionesExtendsMapper.getDelitos(idInstitucion, designaItem);

				List<String> listIdDelitos = new ArrayList<String>();

				for (ScsDelitosdesigna delito : listaDelitos) {
					listIdDelitos.add(delito.getIddelito().toString());
				}

				listDTO.setLista(listIdDelitos);
			}

		} catch (Exception e) {
			LOGGER.error(
					"DesignacionesServiceImpl.getDelitos() -> Se ha producido un error al tratar de obtener los delitos de la designación",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			listDTO.setError(error);
		}

		return listDTO;
	}

	@Transactional
	@Override
	public InsertResponseDTO getPreDesignaEJG(ScsEjgdesigna item, HttpServletRequest request) throws Exception {
		InsertResponseDTO responseDTO = new InsertResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;
//		int response11 = 1;
//		int response12 = 1;
//		int response2 = 1;
//		int response3 = 1;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Entrada a servicio para insertar las justificaciones express");
				// Se comentan el try y el catch para que @Transactional funcone adecuadamente.
//				try {
				LOGGER.info("DesignacionesServiceImpl.extraerPreDesignaEJG() -> Iniciando los inserts...");

				// Tareas:
				// 1. Se debe modificar los atributos asociados con predesignacion en la
				// designa.

				LOGGER.info(
						"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Saliendo a la actualizacion de algunos datos juridicos de designa");
				ScsEjgKey ejgKey = new ScsEjgKey();

				ejgKey.setIdinstitucion(idInstitucion);
				ejgKey.setAnio(item.getAnioejg());
				ejgKey.setIdtipoejg(item.getIdtipoejg());
				ejgKey.setNumero(item.getNumeroejg());

				ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

				ScsDesignaKey designaKey = new ScsDesignaKey();

				designaKey.setIdinstitucion(idInstitucion);
				designaKey.setIdturno(item.getIdturno());
				designaKey.setAnio(item.getAniodesigna());
				designaKey.setNumero(item.getNumerodesigna());

				ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);

//					PRE
//					numAnnioProcedimiento = this.designa.ano;
//				    nig = this.designa.nig;
//				    observaciones = this.designa.observaciones;
//				    calidad = this.designa.idCalidad;
//				    idPretension = this.designa.idPretension;
//				    juzgado = this.designa.idJuzgado;
				designa.setNumprocedimiento(ejg.getNumeroprocedimiento());
				designa.setNig(ejg.getNig());
				designa.setObservaciones(ejg.getObservaciones());
				// designa.set (No existe campo calidad en ScsDesigna)
				designa.setIdpretension(ejg.getIdpretension() != null ? ejg.getIdpretension().shortValue() : null);
				designa.setIdjuzgado(ejg.getJuzgado());

				// Actualizamos los delitos del ejg
				ScsDelitosdesigna delitoDesigna = new ScsDelitosdesigna();

				delitoDesigna.setIdinstitucion(idInstitucion);
				delitoDesigna.setAnio(designa.getAnio());
				delitoDesigna.setNumero(designa.getNumero());
				delitoDesigna.setIdturno(designa.getIdturno());
				delitoDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
				delitoDesigna.setFechamodificacion(new Date());

				ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();

				delitosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio())
						.andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg());

				List<ScsDelitosejg> delitosEjg = scsDelitosejgMapper.selectByExample(delitosEjgExample);

				if (!delitosEjg.isEmpty()) {
					for (ScsDelitosejg delitoEjg : delitosEjg) {
						delitoDesigna.setIddelito(delitoEjg.getIddelito());
						response = scsDelitosdesignaMapper.insert(delitoDesigna);
						if (response == 0)
							throw (new Exception("Error al introducir un delito en la designa proveniente del EJG"));
					}
				}

				designa.setUsumodificacion(usuarios.get(0).getIdusuario());
				designa.setFechamodificacion(new Date());

				response = scsDesignaMapper.updateByPrimaryKeySelective(designa);
				if (response == 0)
					throw (new Exception("Error al introducir los datos juridicos en la designa proveniente del EJG"));

				LOGGER.info(
						"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Saliendo de la actualizacion de algunos datos juridicos de designa");

				// 2. Se debe insertar los contrarios seleccionados en EJG.

				LOGGER.info(
						"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Entrando a los inserts para los contrarios de designa");

				// Obtenemos los contrarios ejg a introducir

				ScsContrariosejgExample contrariosEjgExample = new ScsContrariosejgExample();

				contrariosEjgExample.createCriteria().andAnioEqualTo(item.getAnioejg())
						.andNumeroEqualTo(item.getNumeroejg()).andIdinstitucionEqualTo(idInstitucion)
						.andIdtipoejgEqualTo(item.getIdtipoejg());

				List<ScsContrariosejg> contrariosEjg = scsContrariosejgMapper.selectByExample(contrariosEjgExample);

				ScsContrariosdesigna contrariosDesigna = new ScsContrariosdesigna();
				contrariosDesigna.setAnio(item.getAniodesigna());
				contrariosDesigna.setNumero(item.getNumerodesigna());
				contrariosDesigna.setIdturno(item.getIdturno());
				contrariosDesigna.setIdinstitucion(item.getIdinstitucion());

				contrariosDesigna.setFechamodificacion(new Date());
				contrariosDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());

				LOGGER.info(
						"extraerPreDesignaEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables de los contrarios ejg");

				for (ScsContrariosejg contrarioEjg : contrariosEjg) {

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(contrarioEjg.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info(
							"extraerPreDesignaEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida de scsPersonajgExtendsMapper para obtener justiciables de los contrarios ejg");

					// Se comprueba si tiene representante y se busca.
					if (personajg.getIdrepresentantejg() != null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						contrariosDesigna.setNombrerepresentante(representante.getApellido1() + " "
								+ representante.getApellido2() + ", " + representante.getNombre());
					}

					LOGGER.info(
							"extraerPreDesignaEJG() / ScsContrariosdesignaMapper.insert() -> Entrada a ScsContrariosdesignaMapper para insertar los contrarios ejg");

					// Se le van asignando los distintos valores de IdPersona correspondientes
					contrariosDesigna.setIdpersona(Long.valueOf(contrarioEjg.getIdpersona()));

					// Hacemos esto junto a la iniciacion response2 = 1 para evitar que si un
					// contrario se introduce erroneamente,
					// pase desapercibido al insertar bien el siguiente.
					response = scsContrariosdesignaMapper.insert(contrariosDesigna);
					if (response == 0)
						throw (new Exception("Error al introducir contrarios en la designa provenientes del EJG"));

				}

				LOGGER.info(
						"DesignacionesServiceImpl.extraerPreDesignaEJG() -> Saliendo de los inserts para los contrarios de designa");

				// 3. Se debe introducir el procurador seleccionado en el EJG.

				// Se comprueba que hay un procurador definido en el ejg para prevenir
				// inserciones fallidas
				if (ejg.getIdprocurador() != null) {

					LOGGER.info("DesignacionesServiceImpl.extraerPreDesignaEJG() -> Iniciando los inserts...");

					ScsDesignaprocurador procDesigna = new ScsDesignaprocurador();

					procDesigna.setIdinstitucion(idInstitucion);
					procDesigna.setIdturno(designa.getIdturno());
					procDesigna.setNumero(designa.getNumero());
					procDesigna.setNumerodesignacion(ejg.getNumerodesignaproc());
					procDesigna.setAnio(designa.getAnio());

					procDesigna.setIdinstitucionProc(ejg.getIdinstitucionProc());
					procDesigna.setIdprocurador(ejg.getIdprocurador());
					procDesigna.setFechadesigna(ejg.getFechaDesProc());

					procDesigna.setUsumodificacion(usuarios.get(0).getIdusuario());
					procDesigna.setFechamodificacion(new Date());

					procDesigna.setObservaciones(SigaConstants.OBS_IMPORTADO_EJG);

					response = scsDesignaProcuradorMapper.insert(procDesigna);
					if (response == 0)
						throw (new Exception("Error al introducir un procurador en la designa proveniente del EJG"));
				}

				// 4. Se debe insertar los interesados seleccionados en EJG en Unidad Familiar.

				ScsUnidadfamiliarejgExample familiaresEJGExample = new ScsUnidadfamiliarejgExample();

				familiaresEJGExample.createCriteria().andAnioEqualTo(ejg.getAnio())
						.andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(ejg.getIdtipoejg())
						.andNumeroEqualTo(ejg.getNumero()).andFechabajaIsNull();

				List<ScsUnidadfamiliarejg> familiaresEJG = scsUnidadfamiliarejgMapper
						.selectByExample(familiaresEJGExample);

				for (ScsUnidadfamiliarejg familiar : familiaresEJG) {

					// Se crea el interesado que se introducira en el EJG
					ScsDefendidosdesigna interesado = new ScsDefendidosdesigna();

					interesado.setAnio(designa.getAnio());
					interesado.setNumero(designa.getNumero());
					interesado.setIdinstitucion(idInstitucion);
					interesado.setIdpersona(familiar.getIdpersona());
					interesado.setIdturno(designa.getIdturno());

					// Se comprueba si el interesado introducido tiene un representante asociado
					ScsPersonajgKey personajgKey = new ScsPersonajgKey();

					personajgKey.setIdinstitucion(idInstitucion);
					personajgKey.setIdpersona(familiar.getIdpersona());

					ScsPersonajg personajg = scsPersonajgMapper.selectByPrimaryKey(personajgKey);

					if (personajg.getIdrepresentantejg() != null) {
						personajgKey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representanteFamiliar = scsPersonajgMapper.selectByPrimaryKey(personajgKey);

						interesado.setNombrerepresentante(representanteFamiliar.getNombre());
					}

					interesado.setUsumodificacion(usuarios.get(0).getIdusuario());
					interesado.setFechamodificacion(new Date());

					response = scsDefendidosdesignaMapper.insert(interesado);
					if (response == 0)
						throw (new Exception(
								"Error al introducir interesados en la designa proveniente de la unidad familiar del EJG"));

				}

				LOGGER.info("DesignacionesServiceImpl.extraerPreDesignaEJG() -> Inserts finalizados");
//				} catch (Exception e) {
//					LOGGER.error("DesignacionesServiceImpl.extraerPreDesignaEJG() -> Se ha producido un error ",
//							e);
//					response = 0;
//				}

				LOGGER.info("DesignacionesServiceImpl.extraerPreDesignaEJG() -> Saliendo del servicio... ");
			}
		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("general.mensaje.error.bbdd");
			responseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
			responseDTO.setStatus(SigaConstants.OK);
		}

		responseDTO.setError(error);

		return responseDTO;
	}

	@Override
	public EjgDesignaDTO getEjgDesigna(DesignaItem designa, HttpServletRequest request) {

		LOGGER.info("getEjgDesigna() -> Entrada al servicio para obtener el colegiado");
		EjgDesignaDTO ejgDesignaDTO = new EjgDesignaDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {

				LOGGER.info(
						"getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Entrada a scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");

				try {
					ScsEjgdesignaExample example = new ScsEjgdesignaExample();

					example.createCriteria().andAniodesignaEqualTo((short) designa.getAno())
							.andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(designa.getIdTurno())
							.andNumerodesignaEqualTo((long) designa.getNumero());

					List<ScsEjgdesigna> ejgDesignas = scsEjgdesignaMapper.selectByExample(example);

					ejgDesignaDTO.setScsEjgdesignas(ejgDesignas);

					LOGGER.info(
							"getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Salida de scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");
				} catch (Exception e) {
					LOGGER.debug(
							"getEjgDesigna() -> Se ha producido un error al obtener asociaciones con EJGs de la designacion. ",
							e);
				}
			} else {
				LOGGER.warn(
						"getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEjgDesigna() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener las asociaciones con EJGs de la designacion.");
		return ejgDesignaDTO;
	}

	@Override
	public UpdateResponseDTO asociarAsistenciaDesigna(List<String> designaItem, HttpServletRequest request) {
		UpdateResponseDTO responseDTO = new UpdateResponseDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.asociarEjgDesigna() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.asociarEjgDesigna() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.asociarEjgDesigna() -> Entrada a servicio para insertar las justificaciones express");

				try {
					LOGGER.info("DesignacionesServiceImpl.asociarEjgDesigna() -> Haciendo el insert...");

					ScsAsistencia record = new ScsAsistencia();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());

					record.setIdinstitucion(idInstitucion);
					record.setNumero(Long.parseLong(designaItem.get(5)));
					record.setAnio(Short.parseShort(designaItem.get(4)));
					record.setDesignaAnio(Short.parseShort(designaItem.get(0)));
					record.setDesignaNumero(Long.parseLong(designaItem.get(2)));
					record.setDesignaTurno(Integer.parseInt(designaItem.get(1)));
					response = scsAsistenciaExtendsMapper.updateByPrimaryKeySelective(record);

					LOGGER.info("DesignacionesServiceImpl.asociarEjgDesigna() -> Insert finalizado");
				} catch (Exception e) {
					LOGGER.error("DesignacionesServiceImpl.asociarEjgDesigna() -> Se ha producido un error ", e);
					response = 0;
				} finally {
					if (response == 0) {
						error.setCode(400);
						error.setDescription("general.mensaje.error.bbdd");
						responseDTO.setStatus(SigaConstants.KO);
					} else {
						error.setCode(200);
						error.setDescription("general.message.registro.insertado");
						responseDTO.setStatus(SigaConstants.OK);
					}

					responseDTO.setError(error);
				}

				LOGGER.info("DesignacionesServiceImpl.asociarEjgDesigna() -> Saliendo del servicio... ");
			}
		}

		return responseDTO;
	}

	@Override
	public ScsEjg getEJG(EjgItem item, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		int response = 0;

		ScsEjg ejg = new ScsEjg();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.getEJG() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.getEJG() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.getEJG() -> Entrada a servicio para insertar laobtener la infromacion de un EJG");

				LOGGER.info("DesignacionesServiceImpl.getEJG() -> Haciendo la consulta...");

				ScsEjgExample ejgExample = new ScsEjgExample();

				ejgExample.createCriteria().andAnioEqualTo(Short.valueOf(item.getAnnio()))
						.andIdinstitucionEqualTo(idInstitucion).andNumeroEqualTo(Long.valueOf(item.getNumero())).andIdtipoejgEqualTo(Short.valueOf(item.getTipoEJG()));
				List<ScsEjg> ejgs = scsEjgMapper.selectByExample(ejgExample);

				ejg = ejgs.get(0);
			}
		}

		LOGGER.info("DesignacionesServiceImpl.getEJG() -> Saliendo del servicio...");

		return ejg;
	}

	@Override
	public StringDTO formatoProcedimiento(HttpServletRequest request) {
		StringDTO formato = new StringDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String tipoFormato;

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.formatoProcedimiento() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.formatoProcedimiento -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS")
					.andParametroEqualTo("FORMATO_VALIDACION_NPROCEDIMIENTO_DESIGNA")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

			List<GenParametros> lista = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			LOGGER.info(
					"formatoProcedimiento() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
			if (lista != null) {
				tipoFormato = lista.get(0).getValor();
			} else {
				tipoFormato = "";
			}
			LOGGER.info("formatoProcedimiento()- VALOR: " + tipoFormato);
			formato.setValor(tipoFormato);
		} else {
			formato.setValor("");
		}
		return formato;
	}

	private int actualizaDesignaEnAsuntos(ScsDesigna datos, Short idInstitucion, String origen, AdmUsuarios usuario) {
		// TODO Completar y usar como ejemplo y tratar error con try catch
		int respuesta, respuestaDes, respuestaAsi, respuestacopy;
		respuesta = respuestaDes = respuestaAsi = respuestacopy = 0;
		
		try {
			ScsEjgdesignaExample example = new ScsEjgdesignaExample();
			example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAniodesignaEqualTo(datos.getAnio())
					.andNumerodesignaEqualTo(datos.getNumero());

			List<ScsEjgdesigna> relDesignaList = scsEjgdesignaMapper.selectByExample(example);
			
			if (relDesignaList != null && !relDesignaList.isEmpty()) {
				for (ScsEjgdesigna relacion : relDesignaList) {

					// OPCION 1 crear método similar a los que hay en BusquedaAsuntosServiceImpl
					// copyEjg2....
					// pero parametrizando la tarjeta origen que está haciendo el guardado de datos,
					// por ejemplo, Defensa jurídica

					respuestacopy += copyDesigna2Ejg(idInstitucion, origen, usuario, relacion);
				}
				if(respuestacopy == relDesignaList.size()) {
					respuestaDes = 1;
				}
				respuestacopy = 0;
			}
			
			ScsAsistenciaExample exampleAsistencia = new ScsAsistenciaExample();
			exampleAsistencia.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andDesignaAnioEqualTo(datos.getAnio()).andDesignaNumeroEqualTo(datos.getNumero());

			List<ScsAsistencia> relAsistenciaList = scsAsistenciaExtendsMapper.selectByExample(exampleAsistencia);
			
			if (relAsistenciaList != null && !relAsistenciaList.isEmpty()) {
				for (ScsAsistencia relacion : relAsistenciaList) {
					respuestacopy += copyDesigna2Asis(idInstitucion, origen, usuario, relacion);
				}
				if(respuestacopy == relAsistenciaList.size()) {
					respuestaAsi = 1;
				}
				respuestacopy = 0;
			}

			/*
			 * if(origen.equals("unidadFamiliar")) { // Si hay cambios en la unidad familiar
			 * solicitante principal, se actualiza los SOJ. ScsSojExample sojExample = new
			 * ScsSojExample();
			 * sojExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
			 * .andEjgnumeroEqualTo(Long.valueOf(datos.getNumero()))
			 * .andEjganioEqualTo(Short.valueOf(datos.getAnnio()))
			 * .andEjgidtipoejgEqualTo(Short.valueOf(datos.getTipoEJG()));
			 * 
			 * List<ScsSoj> relSojList = scsSojMapper.selectByExample(sojExample);
			 * 
			 * if(relSojList != null && !relSojList.isEmpty()) { for(ScsSoj relacion :
			 * relSojList) { //respuesta = copyEjg2Soj(idInstitucion, origen, usuario,
			 * relacion); } } }
			 */
			
			if((relDesignaList == null || relDesignaList.isEmpty()) && (relAsistenciaList == null || relAsistenciaList.isEmpty())) {
				respuesta = 1; //si no tiene relaciones es que ha ido bien
			}else if(respuestaDes == 0 && respuestaAsi == 0) {
				respuesta = 0;
			}else {
				respuesta = 1;
			}

		} catch (Exception e) {
			return respuesta = 0;
		}
		return respuesta;
	}

	public int copyDesigna2Ejg(Short idInstitucion, String origen, AdmUsuarios usuario, ScsEjgdesigna relacion)
			throws Exception {

		int response = 0;

		LOGGER.info(
				"DesignacionesServiceImpl.copyDesigna2Ejg() -> Entrada a servicio para insertar las justificaciones express");
		// Se comentan el try y el catch para que @Transactional funcone adecuadamente.
//				try {
		LOGGER.info("DesignacionesServiceImpl.copyDesigna2Ejg() -> Iniciando los inserts...");

		// Tareas:
		// 0. Se debe modificar los atributos asociados con predesignacion en la
		// designa.

		LOGGER.info(
				"DesignacionesServiceImpl.copyDesigna2Ejg() -> Saliendo a la actualizacion de algunos datos juridicos de designa");

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setAnio(relacion.getAnioejg());
		ejgKey.setIdtipoejg(relacion.getIdtipoejg());
		ejgKey.setNumero(relacion.getNumeroejg());

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		ScsDesignaKey designaKey = new ScsDesignaKey();

		designaKey.setIdinstitucion(idInstitucion);
		designaKey.setIdturno(relacion.getIdturno());
		designaKey.setAnio(relacion.getAniodesigna());
		designaKey.setNumero(relacion.getNumerodesigna());

		ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);

		// 1. Actualizamos los delitos del EJG.
		
		if(origen.equals("defensaJuridicaDesigna")) {
			// 3. Se asignan los valores de defensa juridica de EJG

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Ejg() -> Iniciando la introduccion de los valores de defensa juridica del EJG");

			ejg.setNumeroprocedimiento(designa.getNumprocedimiento());
			ejg.setNig(designa.getNig());
//			ejg.setObservaciones(designa.getObservaciones());
			ejg.setObservaciones(designa.getResumenasunto());
			ejg.setIdpretension(designa.getIdpretension().longValue());
			ejg.setJuzgado(designa.getIdjuzgado());

			ejg.setUsumodificacion(usuario.getIdusuario());
			ejg.setFechamodificacion(new Date());

			response = scsEjgMapper.updateByPrimaryKey(ejg);

			// Creamos la base de los nuevos nuevos delitos del EJG
			ScsDelitosejg delitoEjg = new ScsDelitosejg();

			delitoEjg.setIdinstitucion(idInstitucion);
			delitoEjg.setAnio(ejg.getAnio());
			delitoEjg.setNumero(ejg.getNumero());
			delitoEjg.setIdtipoejg(ejg.getIdtipoejg());
			delitoEjg.setUsumodificacion(usuario.getIdusuario());
			delitoEjg.setFechamodificacion(new Date());

			// Seleccionamos y eliminamos los delitos anteriores del EJG
			ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();

			delitosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio())
					.andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg());

			List<ScsDelitosejg> delitosEjg = scsDelitosejgMapper.selectByExample(delitosEjgExample);

			if (!delitosEjg.isEmpty()) {
				response = scsDelitosejgMapper.deleteByExample(delitosEjgExample);
				if (response == 0)
					throw (new Exception("Error al eliminar delitos de un EJG (copyDesigna2Ejg)."));
			}

			ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();

			delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdturnoEqualTo(designa.getIdturno());

			List<ScsDelitosdesigna> delitosDesigna = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);

			String delitosEjgString = "";
			if (!delitosDesigna.isEmpty()) {
				for (ScsDelitosdesigna delitoDesigna : delitosDesigna) {
					if (delitosEjgString != "")
						delitosEjgString += ",";
					delitosEjgString += delitoDesigna.getIddelito();
					delitoEjg.setIddelito(delitoDesigna.getIddelito());
					response = scsDelitosejgMapper.insert(delitoEjg);
					if (response == 0)
						throw (new Exception("Error al introducir un delito en el EJG proveniente de la designacion."));
				}
			}

			if (delitosEjgString.equals(""))
				ejg.setDelitos(null);
			else
				ejg.setDelitos(delitosEjgString);

			
			response = scsEjgMapper.updateByPrimaryKey(ejg);
			if (response == 0)
				throw (new Exception("Error al introducir los valores de defensa juridica del EJG"));

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Ejg() -> Saliendo de la actualizacion de algunos datos juridicos de designa");

		}else if(origen.equals("contrariosDesigna")) {
			// 2. Se debe insertar los contrarios seleccionados en la designacion.

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Ejg() -> Entrando a los inserts para los contrarios de designa");

			// Obtenemos los contrarios de designacion a introducir

			ScsContrariosdesignaExample contrariosDesignaExample = new ScsContrariosdesignaExample();

			contrariosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno()).andFechabajaIsNull();

			List<ScsContrariosdesigna> contrariosDesigna = scsContrariosdesignaMapper
					.selectByExample(contrariosDesignaExample);

			ScsContrariosejg newContrarioEjg = new ScsContrariosejg();
			newContrarioEjg.setAnio(ejg.getAnio());
			newContrarioEjg.setNumero(ejg.getNumero());
			newContrarioEjg.setIdtipoejg(ejg.getIdtipoejg());
			newContrarioEjg.setIdinstitucion(idInstitucion);

			newContrarioEjg.setFechamodificacion(new Date());
			newContrarioEjg.setUsumodificacion(usuario.getIdusuario());

			// Seleccionamos y eliminamos los contrarios anteriores del EJG

			ScsContrariosejgExample contrariosEjgExample = new ScsContrariosejgExample();

			contrariosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio())
					.andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg());

			List<ScsContrariosejg> contrariosEjg = scsContrariosejgMapper.selectByExample(contrariosEjgExample);

			if (!contrariosEjg.isEmpty()) {
				response = scsContrariosejgMapper.deleteByExample(contrariosEjgExample);
				if (response == 0)
					throw (new Exception("Error al eliminar contrarios de un EJG (copyDesigna2Ejg)."));
			}

			// Asignamos los valores a los nuevos contrarios de EJG

			for (ScsContrariosdesigna contrarioDesigna : contrariosDesigna) {

				ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
				scsPersonajgkey.setIdpersona(Long.valueOf(contrarioDesigna.getIdpersona()));
				scsPersonajgkey.setIdinstitucion(idInstitucion);

				LOGGER.info(
						"copyDesigna2Ejg() / scsPersonajgMapper.selectByPrimaryKey() -> Entrada a scsPersonajgMapper para obtener justiciables de los contrarios de la designacion");

				ScsPersonajg personajg = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

				LOGGER.info(
						"copyDesigna2Ejg() / scsPersonajgMapper.selectByPrimaryKey() -> Salida de scsPersonajgMapper para obtener justiciables de los contrarios de la designacion");

				// Se comprueba si tiene representante y se busca.
				if (personajg.getIdrepresentantejg() != null) {

					scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

					ScsPersonajg representante = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

					newContrarioEjg.setNombrerepresentanteejg(representante.getApellido1() + " "
							+ representante.getApellido2() + ", " + representante.getNombre());
				}

				// Se le van asignando los distintos valores de IdPersona correspondientes
				newContrarioEjg.setIdpersona(Long.valueOf(contrarioDesigna.getIdpersona()));

				LOGGER.info(
						"copyDesigna2Ejg() / ScsContrariosdesignaMapper.insert() -> Entrada a ScsContrariosejgMapper para insertar los contrarios en ejg");

				response = scsContrariosejgMapper.insert(newContrarioEjg);
				if (response == 0)
					throw (new Exception("Error al introducir contrarios en el EJG provenientes de la designacion"));

				LOGGER.info(
						"copyDesigna2Ejg() / ScsContrariosdesignaMapper.insert() -> Salida de ScsContrariosejgMapper para insertar los contrarios en ejg");

			}

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Ejg() -> Saliendo de los inserts para los contrarios de designa");

		}else if(origen.equals("procuradorDesigna")) {
			// 5. Se debe introducir el procurador seleccionado en la designacion.

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Ejg() -> Iniciando la introduccion del procurador de la designacion al EJG");

			// Obtenemos la lista de procuradores asociados a la designacion
			ScsDesignaprocuradorExample procuradoresDesignaExample = new ScsDesignaprocuradorExample();

			procuradoresDesignaExample.setOrderByClause("FECHADESIGNA DESC");
			procuradoresDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
					.andNumeroEqualTo(designa.getNumero()).andIdinstitucionEqualTo(idInstitucion)
					.andIdturnoEqualTo(designa.getIdturno());

			List<ScsDesignaprocurador> procuradoresDesigna = scsDesignaProcuradorMapper
					.selectByExample(procuradoresDesignaExample);

			// Se comprueba si hay algún procurador asociado antes de continuar
			if (!procuradoresDesigna.isEmpty()) {
				ejg.setIdprocurador(designa.getIdprocurador());
				ejg.setIdinstitucionProc(designa.getIdinstitucionProcur());
				ejg.setFechaDesProc(procuradoresDesigna.get(0).getFechadesigna());
				ejg.setNumerodesignaproc(procuradoresDesigna.get(0).getNumerodesignacion());
			}
			// Si no hay ningún procurador asociado, se desasocia el existente en EJG.
			else {
				ejg.setIdprocurador(null);
				ejg.setIdinstitucionProc(null);
				ejg.setFechaDesProc(null);
				ejg.setNumerodesignaproc(null);
			}

			response = scsEjgMapper.updateByPrimaryKey(ejg);
			if (response == 0)
				throw (new Exception("Error al introducir un procurador en el EJG proveniente de la designacion"));

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Ejg() -> Finalizada la introduccion del procurador de la designacion al EJG");

		}else if(origen.equals("unidadFamiliar")) {
			// 6. Se debe insertar los interesados seleccionados en la designacion en Unidad
			// Familiar del EJG.

			try {
				LOGGER.info(
						"DesignacionesServiceImpl.copyDesigna2Ejg() -> Iniciando la introduccion de los interesados de la designacion a la unidad Familiar del EJG");

				ScsUnidadfamiliarejgExample familiaresEJGExample = new ScsUnidadfamiliarejgExample();

				familiaresEJGExample.createCriteria().andAnioEqualTo(ejg.getAnio()).andIdinstitucionEqualTo(idInstitucion)
						.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andNumeroEqualTo(ejg.getNumero()).andFechabajaIsNull();

				List<ScsUnidadfamiliarejg> familiaresEJG = scsUnidadfamiliarejgMapper.selectByExample(familiaresEJGExample);

				// Se eliminan los familiares presentes en la tarjeta Unidad Familiar del EJG.
				if (!familiaresEJG.isEmpty()) {
					response = scsUnidadfamiliarejgMapper.deleteByExample(familiaresEJGExample);
					if (response == 0)
						throw (new Exception("Error al eliminar la unidad familiar asociada al EJG"));
				}

				ScsDefendidosdesignaExample defendidosDesignaExample = new ScsDefendidosdesignaExample();

				defendidosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio())
						.andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(designa.getIdturno())
						.andNumeroEqualTo(designa.getNumero());

				List<ScsDefendidosdesigna> defendidosDesigna = scsDefendidosdesignaMapper
						.selectByExample(defendidosDesignaExample);

				for (ScsDefendidosdesigna interesado : defendidosDesigna) {

					// Se crea el familiar que se introducira en la unidad familiar del EJG
					ScsUnidadfamiliarejg familiar = new ScsUnidadfamiliarejg();

					familiar.setAnio(ejg.getAnio());
					familiar.setNumero(ejg.getNumero());
					familiar.setIdtipoejg(ejg.getIdtipoejg());
					familiar.setIdinstitucion(idInstitucion);
					familiar.setIdpersona(interesado.getIdpersona());
					familiar.setEncalidadde(interesado.getCalidad());
					familiar.setObservaciones(interesado.getObservaciones());
					familiar.setSolicitante((short) 0);

					familiar.setUsumodificacion(usuario.getIdusuario());
					familiar.setFechamodificacion(new Date());

					response = scsUnidadfamiliarejgMapper.insert(familiar);
					if (response == 0)
						throw (new Exception(
								"Error al introducir familiares en la unidad familiar del EJG desde los interesados de la designacion."));

				}
			} catch (Exception ex) {
				LOGGER.debug("No se han podido eliminar los familiares del ejg " + ejg.getAnio() + "/" + ejg.getNumejg()
						+ " del colegio " + idInstitucion, ex);
			}
		}

	
	
		
		// 4. Se asocia al EJG el ultimo letrado asignado en la designacion.

	//	LOGGER.info(
		//		"BusquedaAsuntosServiceImpl.copyDesigna2Ejg() -> Iniciando la copia de letrado de la designacion al EJG");

		//ScsDesignasletradoExample letradosDesignaExample = new ScsDesignasletradoExample();

	//	letradosDesignaExample.setOrderByClause("FECHADESIGNA DESC");
		//letradosDesignaExample.createCriteria().andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
		//		.andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(designa.getIdturno());

		// Buscamos el ultmo letrado designado en la designa.
//				List<ScsDesignasletrado> letradosDesigna = scsDesignasletradoMapper
//						.selectByExample(letradosDesignaExample);

		// Cuidado ya que puede dar error al no cumpli una restriccion
		// de clave secundaria si el letrado seleccionado en la designacion se hizo por
		// art 27,
		// que permite seleccionar fuera de la institucion activando la restriccion
		// SEG_COLEGIADO_FK de la tabla SCS_EJG.
//				if (!letradosDesigna.isEmpty())
//					ejg.setIdpersona(letradosDesigna.get(0).getIdpersona());
//				else
//					ejg.setIdpersona(null);

		//response = scsEjgMapper.updateByPrimaryKey(ejg);
	
	
	

		LOGGER.info("DesignacionesServiceImpl.copyDesigna2Ejg() -> Inserts finalizados");

		return response;
	}

	public int copyDesigna2Asis(Short idInstitucion, String origen, AdmUsuarios usuario, ScsAsistencia relacionDesigna)
			throws Exception {

		int response = 0;

		LOGGER.info("DesignacionesServiceImpl.copyDesigna2Asis() -> Iniciando los inserts...");

		// Tareas:
		// 1. Obtenemos los asuntos que vamos a manipular.

		LOGGER.info(
				"DesignacionesServiceImpl.copyDesigna2Asis() -> Seleccionando la designacion y la asistencia correspondientes.");

		ScsDesignaKey designaKey = new ScsDesignaKey();

		designaKey.setIdinstitucion(idInstitucion);
		designaKey.setAnio(relacionDesigna.getDesignaAnio());
		designaKey.setNumero(relacionDesigna.getDesignaNumero());
		designaKey.setIdturno(relacionDesigna.getDesignaTurno());

		ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);

		ScsAsistenciaKey asisKey = new ScsAsistenciaKey();

		asisKey.setIdinstitucion(idInstitucion);
		asisKey.setAnio(relacionDesigna.getAnio());
		asisKey.setNumero(relacionDesigna.getNumero());

		ScsAsistencia asis = scsAsistenciaMapper.selectByPrimaryKey(asisKey);

		LOGGER.info("DesignacionesServiceImpl.copyDesigna2Asis() -> EJG y Asistencia seleccionados.");

		// 2. Actualizamos los delitos de la asistencia asignando los de la designacion.

		if (origen.equals("defensaJuridicaDesigna")) {
			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Asis() -> Copiando delitos de la designacion a la asistencia.");

			asis.setJuzgado(designa.getIdjuzgado());
			asis.setJuzgadoidinstitucion(designa.getIdinstitucionJuzg());

//			asis.setNumeroprocedimiento(designa.getNumprocedimiento() + "/" + designa.getAnioprocedimiento());
			asis.setNumeroprocedimiento(designa.getNumprocedimiento());
			asis.setNig(designa.getNig());
			asis.setIdpretension(designa.getIdpretension());

			ScsDelitosasistencia delitoAsistencia = new ScsDelitosasistencia();

			delitoAsistencia.setIdinstitucion(idInstitucion);
			delitoAsistencia.setAnio(asis.getAnio());
			delitoAsistencia.setNumero(asis.getNumero());

			delitoAsistencia.setUsumodificacion(usuario.getIdusuario());
			delitoAsistencia.setFechamodificacion(new Date());

			ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();

			delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno());

			List<ScsDelitosdesigna> delitosDesigna = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);

			ScsDelitosasistenciaExample delitosAsistenciaExample = new ScsDelitosasistenciaExample();

			delitosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(asis.getAnio()).andNumeroEqualTo(asis.getNumero());

			List<ScsDelitosasistencia> delitosAsistencia = scsDelitosasistenciaMapper
					.selectByExample(delitosAsistenciaExample);

			if (!delitosAsistencia.isEmpty()) {
				response = scsDelitosasistenciaMapper.deleteByExample(delitosAsistenciaExample);
				if (response == 0)
					throw (new Exception("Error al eliminar los delitos anteriores de la asistencia"));

			}

			if (!delitosDesigna.isEmpty()) {
				for (ScsDelitosdesigna delitoDesigna : delitosDesigna) {
					delitoAsistencia.setIddelito(delitoDesigna.getIddelito());
					response = scsDelitosasistenciaMapper.insert(delitoAsistencia);
					if (response == 0)
						throw (new Exception(
								"Error al introducir un delito en la asistencia proveniente de la designacion"));
				}
			}
			asis.setDelitosimputados(designa.getDelitos());

			asis.setUsumodificacion(usuario.getIdusuario());
			asis.setFechamodificacion(new Date());

			response = scsAsistenciaMapper.updateByPrimaryKey(asis);

			if (response == 0)
				throw (new Exception(
						"Error en copyDesigna2Asis() al copiar los datos de la designacion a la asistencia."));

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Asis() -> Delitos copiados de la designacion a la asistencia.");

		} else if (origen.equals("contrariosDesigna")) {
			// 3. Actualizamos los contrarios de la asistencia asignando los de la
			// designacion.

			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Asis() -> Copiando contrarios de la designacion a la asistencia.");

			ScsContrariosasistencia contrarioAsistencia = new ScsContrariosasistencia();

			contrarioAsistencia.setIdinstitucion(idInstitucion);
			contrarioAsistencia.setAnio(asis.getAnio());
			contrarioAsistencia.setNumero(asis.getNumero());

			contrarioAsistencia.setUsumodificacion(usuario.getIdusuario());
			contrarioAsistencia.setFechamodificacion(new Date());

			ScsContrariosdesignaExample contrariosDesignaExample = new ScsContrariosdesignaExample();

			contrariosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero())
					.andIdturnoEqualTo(designa.getIdturno()).andFechabajaIsNull();

			List<ScsContrariosdesigna> contrariosDesigna = scsContrariosdesignaMapper
					.selectByExample(contrariosDesignaExample);

			ScsContrariosasistenciaExample contrariosAsistenciaExample = new ScsContrariosasistenciaExample();

			contrariosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andAnioEqualTo(asis.getAnio()).andNumeroEqualTo(asis.getNumero());

			List<ScsContrariosasistencia> contrariosAsistencia = scsContrariosasistenciaMapper
					.selectByExample(contrariosAsistenciaExample);

			if (!contrariosAsistencia.isEmpty()) {
				response = scsContrariosasistenciaMapper.deleteByExample(contrariosAsistenciaExample);
				if (response == 0)
					throw (new Exception("Error al eliminar los contrarios anteriores de la asistencia"));

			}

			String contrariosAsisString = "";
			if (!contrariosDesigna.isEmpty()) {
				for (ScsContrariosdesigna contrarioDesigna : contrariosDesigna) {
					contrarioAsistencia.setIdpersona(contrarioDesigna.getIdpersona());
					if (contrariosAsisString != "")
						contrariosAsisString += ",";
					contrariosAsisString += contrarioDesigna.getIdpersona();
					response = scsContrariosasistenciaMapper.insert(contrarioAsistencia);
					if (response == 0)
						throw (new Exception(
								"Error al introducir un contrario en la asistencia proveniente de la designacion"));
				}
			}

			asis.setUsumodificacion(usuario.getIdusuario());
			asis.setFechamodificacion(new Date());
			asis.setContrarios(contrariosAsisString);
			LOGGER.info(
					"DesignacionesServiceImpl.copyDesigna2Asis() -> Contrarios copiados de la designacion a la asistencia.");

			asis.setUsumodificacion(usuario.getIdusuario());
			asis.setFechamodificacion(new Date());

			response = scsAsistenciaMapper.updateByPrimaryKey(asis);
			if (response == 0)
				throw (new Exception(
						"Error en copyDesigna2Asis() al copiar los datos de la designacion a la asistencia."));

		}

		// 4. Se asignan los datos de la designacion a la asistencia.

		LOGGER.info(
				"DesignacionesServiceImpl.copyDesigna2Asis() -> Copiando informacion de la designacion a la asistencia.");

		// ScsDesignasletradoExample letradoDesignaExample = new
		// ScsDesignasletradoExample();

		// letradoDesignaExample.setOrderByClause("FECHADESIGNA DESC");

		// letradoDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(designa.getAnio())
		// .andNumeroEqualTo(designa.getNumero()).andIdturnoEqualTo(designa.getIdturno());

		LOGGER.info("DesignacionesServiceImpl.copyDesigna2Asis() -> Saliendo del servicio... ");

		return response;
	}

}