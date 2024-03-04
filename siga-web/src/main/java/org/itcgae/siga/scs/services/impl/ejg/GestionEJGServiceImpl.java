package org.itcgae.siga.scs.services.impl.ejg;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.DocushareDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.DelitosEjgDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.ExpInsosDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioEJGJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesDTO;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteKey;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenFicheroExample;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsAsistenciaKey;
import org.itcgae.siga.db.entities.ScsContrariosasistencia;
import org.itcgae.siga.db.entities.ScsContrariosasistenciaExample;
import org.itcgae.siga.db.entities.ScsContrariosdesigna;
import org.itcgae.siga.db.entities.ScsContrariosdesignaExample;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsContrariosejgExample;
import org.itcgae.siga.db.entities.ScsContrariosejgKey;
import org.itcgae.siga.db.entities.ScsDefendidosdesigna;
import org.itcgae.siga.db.entities.ScsDefendidosdesignaExample;
import org.itcgae.siga.db.entities.ScsDelitosasistencia;
import org.itcgae.siga.db.entities.ScsDelitosasistenciaExample;
import org.itcgae.siga.db.entities.ScsDelitosdesigna;
import org.itcgae.siga.db.entities.ScsDelitosdesignaExample;
import org.itcgae.siga.db.entities.ScsDelitosejg;
import org.itcgae.siga.db.entities.ScsDelitosejgExample;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsDesignaprocurador;
import org.itcgae.siga.db.entities.ScsDesignaprocuradorExample;
import org.itcgae.siga.db.entities.ScsDictamenejg;
import org.itcgae.siga.db.entities.ScsDictamenejgExample;
import org.itcgae.siga.db.entities.ScsDictamenejgKey;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
import org.itcgae.siga.db.entities.ScsDocumentacionejgKey;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazadaExample;
import org.itcgae.siga.db.entities.ScsEjgResolucionKey;
import org.itcgae.siga.db.entities.ScsEjgResolucionWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsEjgdesignaExample;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.entities.ScsMaestroestadosejg;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojExample;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgKey;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosdesignaMapper;
import org.itcgae.siga.db.mappers.ScsDelitosejgMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaprocuradorMapper;
import org.itcgae.siga.db.mappers.ScsDictamenejgMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgPrestacionRechazadaMapper;
import org.itcgae.siga.db.mappers.ScsEjgResolucionMapper;
import org.itcgae.siga.db.mappers.ScsEjgdesignaMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsMaestroestadosejgMapper;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.mappers.ScsSojMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpTipoexpedienteExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsActacomisionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsComisariaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsContrariosejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDelitoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDocumentacionEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDocumentoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEejgPeticionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrigencajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPresentadorExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPrestacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcuradorExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSituacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipodocumentoEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoencalidadExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.itcgae.siga.scs.services.impl.ejg.comision.BusquedaEJGComisionServiceImpl;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Configurable
public class GestionEJGServiceImpl implements IGestionEJG {

	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

	@Autowired
	private GenFicheroMapper genFicheroMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;

	@Autowired
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;

	@Autowired
	private ScsDocumentacionejgMapper scsDocumentacionejgMapper;

	@Autowired
	private ScsPresentadorExtendsMapper scsPresentadorExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsPrestacionExtendsMapper scsPrestacionesExtendsMapper;

	@Autowired
	private ScsSituacionExtendsMapper scsSituacionesExtendsMapper;

	@Autowired
	private ScsDocumentoejgExtendsMapper scsDocumentoejgExtendsMapper;

	@Autowired
	private ScsProcuradorExtendsMapper scsProcuradorExtendsMapper;

	@Autowired
	private ScsTipodocumentoEjgExtendsMapper scsTipodocumentoEjgExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private ScsTipoencalidadExtendsMapper scsTipoencalidadExtendsMapper;

	@Autowired
	private ScsEejgPeticionesExtendsMapper scsExpedienteEconomicoExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Autowired
	private ScsDocumentacionEjgExtendsMapper scsDocumentacionejgExtendsMapper;

	@Autowired
	private ScsOrigencajgExtendsMapper scsOrigencajgExtendsMapper;

	@Autowired
	private ScsDictamenejgMapper scsDictamenejgMapper;

	@Autowired
	private ScsActacomisionExtendsMapper scsActacomisionExtendsMapper;

	@Autowired
	private ExpTipoexpedienteExtendsMapper expExpedienteExtendsMapper;

	@Autowired
	private ScsDelitoExtendsMapper scsDelitoExtendsMapper;

	@Autowired
	private ScsEstadoejgMapper scsEstadoejgMapper;

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private ScsDelitosejgMapper scsDelitosejgMapper;

	@Autowired
	private DocushareHelper docushareHelper;

	@Autowired
	private ScsEjgResolucionMapper scsEjgResolucionMapper;

	@Autowired
	private ScsEejgPeticionesExtendsMapper scsEejgPeticionesExtendsMapper;

	@Autowired
	private ExpExpedienteMapper expExpedienteMapper;

	@Autowired
	private ScsEjgPrestacionRechazadaMapper scsEjgPrestacionRechazadaMapper;

	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;

	@Autowired
	private GenParametrosExtendsMapper genParamterosExtendsMapper;

	@Autowired
	private ScsEjgdesignaMapper scsEjgdesignaMapper;

	@Autowired
	private ScsMaestroestadosejgMapper scsMaestroestadosejgMapper;

	@Autowired
	private EEJGServiceImpl eejgServiceImpl;

	@Autowired
	private ScsPersonajgMapper scsPersonajgMapper;

	@Autowired
	private ScsComisariaExtendsMapper scsComisariaExtendsMapper;

	@Autowired
	private ScsContrariosejgExtendsMapper scsContrariosejgExtendsMapper;

	@Autowired
	private ScsContrariosejgMapper scsContrariosejgMapper;

	@Autowired
	private EcomColaMapper ecomColaMapper;

	@Autowired
	private EcomColaParametrosMapper ecomColaParametrosMapper;

	@Autowired
	private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

	@Autowired
	private ScsAsistenciaMapper scsAsistenciaMapper;

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private ScsSojMapper scsSojMapper;

	@Autowired
	private ScsSojExtendsMapper scsSojExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

	@Autowired
	private BusquedaEJGComisionServiceImpl busquedaEJGComisionServiceImpl;

	@Autowired
	private EJGIntercambiosHelper ejgIntercambiosHelper;

	@Autowired
	private ScsDesignaMapper scsDesignaMapper;

	@Autowired
	private ScsDelitosdesignaMapper scsDelitosdesignaMapper;

	@Autowired
	private ScsContrariosdesignaMapper scsContrariosdesignaMapper;

	@Autowired
	private ScsDesignaprocuradorMapper scsDesignaProcuradorMapper;

	@Autowired
	private ScsDefendidosdesignaMapper scsDefendidosdesignaMapper;

	@Autowired
	private ScsDelitosasistenciaMapper scsDelitosasistenciaMapper;

	@Autowired
	private ScsContrariosasistenciaMapper scsContrariosasistenciaMapper;

	@Override
	public EjgDTO datosEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("datosEJG() -> Entrada al servicio para obtener el colegiado");
		EjgDTO ejgDTO = new EjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("datosEJG() / scsEjgExtendsMapper.datosEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgExtendsMapper.datosEJG(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("datosEJG() / scsEjgExtendsMapper.datosEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			} else {
				LOGGER.warn("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("datosEJG() -> Salida del servicio para obtener los datos del ejg seleccionado");
		return ejgDTO;
	}

	@Override
	public EjgDTO datosEJGJustificacionExpres(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("datosEJG() -> Entrada al servicio para obtener el colegiado");
		EjgDTO ejgDTO = new EjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("datosEJG() / scsEjgExtendsMapper.datosEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgExtendsMapper.datosEJGJustificacionExpres(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("datosEJG() / scsEjgExtendsMapper.datosEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			} else {
				LOGGER.warn("datosEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("datosEJG() -> Salida del servicio para obtener los datos del ejg seleccionado");
		return ejgDTO;
	}

	@Override
	public ComboDTO comboPrestaciones(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("comboPrestaciones() / scsPrestacionesExtendsMapper.comboPrestaciones() -> Entrada a scsPrestacionesExtendsMapper para obtener los combo");

				comboItems = scsPrestacionesExtendsMapper.comboPrestaciones(usuarios.get(0).getIdlenguaje().toString(), idInstitucion.toString());

				LOGGER.info("comboPrestaciones() / scsPrestacionesExtendsMapper.comboPrestaciones() -> Salida a scsPrestacionesExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboPrestaciones() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoencalidad(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboTipoencalidad() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("comboTipoencalidad() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("comboTipoencalidad() / scsTiporesolucionExtendsMapper.comboTipoEnCalidadDe() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				comboItems = scsTipoencalidadExtendsMapper.comboTipoencalidad(usuarios.get(0).getIdlenguaje().toString(), idInstitucion);

				LOGGER.info("comboTipoencalidad() / scsTiporesolucionExtendsMapper.comboTipoEnCalidadDe() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboTipoencalidad() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboSituaciones(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboSituaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("comboSituaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("comboSituaciones() / scsSituacionesExtendsMapper.comboSituaciones() -> Entrada a scsSituacionesExtendsMapper para obtener el combo de situaciones");

				comboItems = scsSituacionesExtendsMapper.comboSituaciones(usuarios.get(0).getIdlenguaje().toString());

				LOGGER.info("comboSituaciones() / scsSituacionesExtendsMapper.comboSituaciones() -> Salida a scsSituacionesExtendsMapper para obtener el combo de situaciones");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboSituaciones() -> Salida del servicio para obtener las situaciones");
		return comboDTO;
	}

	@Override
	public ComboDTO comboCDetenciones(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboCDetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("comboCDetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("comboCDetenciones() / scsComisariaExtendsMapper.comboCDetenciones() -> Entrada a scsComisariaExtendsMapper para obtener el combo de centros de detencion");

				comboItems = scsComisariaExtendsMapper.comboCDetenciones(idInstitucion);

				LOGGER.info("comboCDetenciones() / scsComisariaExtendsMapper.comboCDetenciones() -> Salida a scsComisariaExtendsMapper para obtener el combo de centros de detencion");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboCDetenciones() -> Salida del servicio para obtener los centros de detencion");
		return comboDTO;
	}

	@Override
	public ComboDTO comboDelitos(HttpServletRequest request) {
		LOGGER.info("comboDelitos() -> Entrada al servicio para obtener combo delitos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDelitoExtendsMapper.comboDelitos(usuarios.get(0).getIdlenguaje(), idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("comboDelitos() -> Salida del servicio para obtener combo delitos");

		return comboDTO;
	}

	@Override
	public ComboDTO comboPresentadores(HttpServletRequest request) {
		LOGGER.info("comboPresentador() -> Entrada al servicio para obtener combo presentadores");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsPresentadorExtendsMapper.comboPresentadores(usuarios.get(0).getIdlenguaje(), idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("comboPresentador() -> Salida del servicio para obtener combo presentadores");

		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoDocumentacion(HttpServletRequest request) {
		LOGGER.info("comboTipoDocumentacion() -> Entrada al servicio para obtener combo tipo documentacion");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsTipodocumentoEjgExtendsMapper.comboTipoDocumentacion(usuarios.get(0).getIdlenguaje(), idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("comboTipoDocumentacion() -> Salida del servicio para obtener combo tipo documentacion");

		return comboDTO;
	}

	@Override
	public ComboDTO comboDocumentos(String idTipoDocumentacion, HttpServletRequest request) {
		LOGGER.info("comboDocumento() -> Entrada al servicio para obtener combo documento");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDocumentoejgExtendsMapper.comboDocumentos(usuarios.get(0).getIdlenguaje(), idInstitucion, idTipoDocumentacion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("comboDocumento() -> Salida del servicio para obtener combo documento");

		return comboDTO;
	}

	@Override
	public List<ScsEjgPrestacionRechazada> searchPrestacionesRechazadas(EjgItem ejgItem, HttpServletRequest request) {

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<ScsEjgPrestacionRechazada> idsPrestRech = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				idsPrestRech = scsPrestacionesExtendsMapper.prestacionesRechazadas(ejgItem, idInstitucion);

				LOGGER.info("searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

			}

		}
		LOGGER.info("searchPrestacionesRechazadas() -> Salida del servicio para obtener las prestaciones rechazadas del ejg");
		return idsPrestRech;
	}

	@Override
	public UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("unidadFamiliarEJG() -> Entrada al servicio para obtener la tarjeta unidad familiar de un EJG");
		UnidadFamiliarEJGDTO unidadFamiliarEJGDTO = new UnidadFamiliarEJGDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info("unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info("unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info("unidadFamiliarEJG() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info("unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Entrada a scsEjgExtendsMapper para obtener Unidad Familiar");

				unidadFamiliarEJGDTO.setUnidadFamiliarEJGItems(scsPersonajgExtendsMapper.unidadFamiliarEJG(ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));

				LOGGER.info("unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Salida de scsEjgExtendsMapper para obtener Unidad Familiar");
			} else {
				LOGGER.warn("unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("unidadFamiliarEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("unidadFamiliarEJG() -> Salida del servicio para obtener la tarjeta unidad familiar de un EJG");
		return unidadFamiliarEJGDTO;
	}

	@Override
	public InsertResponseDTO insertFamiliarEJG(List<String> item, HttpServletRequest request) {
		LOGGER.info("insertFamiliarEJG() -> Entrada al servicio para obtener unidad Familiar");
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Boolean primero = false;
		boolean editadoNuevo = false;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para insertar en la unidad familiar del ejg");

				try {
					// [ejg.idInstitucion, justiciable.idpersona, ejg.annio, ejg.tipoEJG,
					// ejg.numero]

					ScsUnidadfamiliarejgExample exampleFamiliar = new ScsUnidadfamiliarejgExample();
					exampleFamiliar.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.parseLong(item.get(1))).andAnioEqualTo(Short.parseShort(item.get(2))).andIdtipoejgEqualTo(Short.parseShort(item.get(3))).andNumeroEqualTo(Long.parseLong(item.get(4)));

					List<ScsUnidadfamiliarejg> uf = scsUnidadfamiliarejgMapper.selectByExample(exampleFamiliar);

					ScsUnidadfamiliarejg familiar = new ScsUnidadfamiliarejg();

					if (!uf.isEmpty()) {
						if (uf.get(0).getFechabaja() != null) {
							familiar.setIdinstitucion(idInstitucion);
							familiar.setIdpersona(Long.parseLong(item.get(1)));
							familiar.setAnio(Short.parseShort(item.get(2)));
							familiar.setIdtipoejg(Short.parseShort(item.get(3)));
							familiar.setNumero(Long.parseLong(item.get(4)));

							familiar.setUsumodificacion(usuarios.get(0).getIdusuario());
							familiar.setFechamodificacion(new Date());
							familiar.setFechabaja(null);
							primero = compruebaSiSolicitanteEjg(idInstitucion, item);
							if (primero) {
								familiar.setSolicitante((short) 1);
							} else {
								familiar.setSolicitante((short) 0);
							}
							response = scsUnidadfamiliarejgMapper.updateByPrimaryKey(familiar);
						} else {
							response = 0;
						}

					} else {
						familiar.setIdinstitucion(idInstitucion);
						familiar.setIdpersona(Long.parseLong(item.get(1)));
						familiar.setAnio(Short.parseShort(item.get(2)));
						familiar.setIdtipoejg(Short.parseShort(item.get(3)));
						familiar.setNumero(Long.parseLong(item.get(4)));

						familiar.setUsumodificacion(usuarios.get(0).getIdusuario());
						familiar.setFechamodificacion(new Date());
						primero = compruebaSiSolicitanteEjg(idInstitucion, item);

						if (primero) {
							// Además Se marca como solicitante
							familiar.setSolicitante((short) 1);
						} else {
							familiar.setSolicitante((short) 0);
						}

						// Insertamos el familiar
						// Comprobamos si venimos desde editar creando uno nuevo para editar la relacion antigua en SCS_UNIDADFAMILIAREJG
						if (item.size() > 5 && Boolean.parseBoolean(item.get(5))) {
							editadoNuevo = true;
							familiar.setFechabaja(null);
							primero = compruebaSiSolicitanteEjg(idInstitucion, item);

							if (primero) {
								familiar.setSolicitante((short) 1);
							} else {
								familiar.setSolicitante((short) 0);
							}

							exampleFamiliar = new ScsUnidadfamiliarejgExample();
							exampleFamiliar.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.parseLong(item.get(6))).andAnioEqualTo(Short.parseShort(item.get(2))).andIdtipoejgEqualTo(Short.parseShort(item.get(3))).andNumeroEqualTo(Long.parseLong(item.get(4)));

							response = scsUnidadfamiliarejgMapper.updateByExampleSelective(familiar, exampleFamiliar);
						}
						// Si no venimos de editar creamos la nueva relacion
						else {
							response = scsUnidadfamiliarejgMapper.insert(familiar);
						}
					}

					// Si venimos de ditar un justiciable creando uno nuevo no copiamos sus asuntos
					if (!editadoNuevo) {
						EjgItem ejgItem = new EjgItem();
						ejgItem.setidInstitucion(idInstitucion.toString());
						ejgItem.setAnnio(item.get(2));
						ejgItem.setTipoEJG(item.get(3));
						ejgItem.setNumero(item.get(4));

						response = actualizaEjgEnAsuntos(ejgItem, idInstitucion, "unidadFamiliar", usuarios.get(0));

						if (response == 0)
							throw (new Exception("Error al copiar los datos a otros asuntos"));

						if (response != 1) {
							responsedto.setStatus(SigaConstants.KO);
							LOGGER.error("GestionEJGServiceImpl.borrarEstado() -> KO. No se ha introducido ningún familiar en el ejg");
							throw new Exception("ERROR: no se ha podido introducir ningún familiar en el ejg");
						} else {
							responsedto.setStatus(SigaConstants.OK);

							// String descripcionUnidadFamiliar = getDescripcionUnidadFamiliar(null,
							// familiar, "INSERT");

							// auditoriaCenHistoricoService.insertaCenHistorico(null,
							// SigaConstants.CEN_TIPOCAMBIO.EJG_ANADIR_FAMILIAR_NUEVO,
							// descripcionUnidadFamiliar, request, null);
							// insertAuditoriaEJG("familiar", null, "NUEVO", usuarios.get(0), ejgItem);
						}
					} else {
						responsedto.setStatus(SigaConstants.OK);
					}

					LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Salida del servicio para insertar en la unidad familiar en el ejg");
				} catch (Exception e) {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Se ha producido un error al insertar en la unidad familiar en el ejg.", e);
				}
			}
		}

		return responsedto;
	}

	private Boolean compruebaSiSolicitanteEjg(Short idInstitucion, List<String> item) {
		// Miramos si es el primero como unidad familiar para ponerlo como solicitante principal
		ScsUnidadfamiliarejgExample exampleFamiliar = new ScsUnidadfamiliarejgExample();
		exampleFamiliar.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(Short.parseShort(item.get(2))).andIdtipoejgEqualTo(Short.parseShort(item.get(3))).andNumeroEqualTo(Long.parseLong(item.get(4))).andFechabajaIsNull();
		List<ScsUnidadfamiliarejg> uf = scsUnidadfamiliarejgMapper.selectByExample(exampleFamiliar);

		ScsEjgKey keyEjg = new ScsEjgKey();
		keyEjg.setAnio(Short.valueOf(item.get(2)));
		keyEjg.setIdinstitucion(idInstitucion);
		keyEjg.setNumero(Long.valueOf(item.get(4)));
		keyEjg.setIdtipoejg(Short.valueOf(item.get(3)));

		ScsEjgWithBLOBs ejg = scsEjgExtendsMapper.selectByPrimaryKey(keyEjg);
		// Si no hay resultados se inserta como solicitante principal
		if ((uf.isEmpty()) || (item.size() > 5 && ejg.getIdpersonajg().toString().equals(item.get(6)))) {

			// Si es el primero se marca como solicitante principal
			ejg.setIdpersonajg(Long.parseLong(item.get(1)));
			scsEjgExtendsMapper.updateByPrimaryKeySelective(ejg);

			return true;
		} else {
			return false;
		}
	}

	@Override
	public ExpedienteEconomicoDTO getExpedientesEconomicos(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getExpedientesEconomicos() -> Entrada al servicio para obtener expedientes económicos");
		ExpedienteEconomicoDTO expedienteEconomicoDTO = new ExpedienteEconomicoDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				Error error = new Error();

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info("getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info("getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info("getExpedientesEconomicos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info("getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener Expedienets Económicos");

				List<ExpedienteEconomicoItem> expedienteEconomicoItems = scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString());

				if (expedienteEconomicoItems != null) {
					expedienteEconomicoItems.forEach(e -> e.setEstado(Stream.of(SigaConstants.EEJG_ESTADO.values()).filter(e2 -> String.valueOf(e2.getId()).equals(e.getIdEstado())).map(e2 -> e2.getMessageToTranslate()).findFirst().orElse(null)));
				}

				expedienteEconomicoDTO.setExpEconItems(expedienteEconomicoItems);

				LOGGER.info("getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener Expedienets Económicos");
				if (expedienteEconomicoDTO.getExpEconItems() != null && tamMaximo != null && expedienteEconomicoDTO.getExpEconItems().size() > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					expedienteEconomicoDTO.setError(error);
					// justiciablesItems.remove(justiciablesItems.size()-1);
				}

			} else {
				LOGGER.warn("getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getExpedientesEconomicos() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return expedienteEconomicoDTO;
	}

	@Override
	public EstadoEjgDTO getEstados(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getEstados() -> Entrada al servicio para obtener el colegiado");
		EstadoEjgDTO estadoEjgDTO = new EstadoEjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getEstados() / scsEstadoejgExtendsMapper.getEstados() -> Entrada a scsEstadoejgExtendsMapper para obtener los estados del EJG");
				estadoEjgDTO.setEstadoEjgItems(scsEstadoejgExtendsMapper.getEstados(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("getEstados() / scsEstadoejgExtendsMapper.getEstados() -> Salida de scsEstadoejgExtendsMapper para obtener los estados del EJG");
			} else {
				LOGGER.warn("getEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEstados() -> idInstitucion del token nula");
		}
		return estadoEjgDTO;
	}

	@Override
	public EstadoEjgDTO getUltEstadoEjg(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getUltEstadoEjg() -> Entrada al servicio para obtener el colegiado");
		EstadoEjgDTO estadoEjgDTO = new EstadoEjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getUltEstadoEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getUltEstadoEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getUltEstadoEjg() / scsEstadoejgExtendsMapper.getUltEstadoEjg() -> Entrada a scsEstadoejgExtendsMapper para obtener el ultimo estado del EJG");
				estadoEjgDTO.setEstadoEjgItems(Arrays.asList(scsEstadoejgExtendsMapper.getUltEstadoEjg(ejgItem, idInstitucion.toString())));
				LOGGER.info("getUltEstadoEjg() / scsEstadoejgExtendsMapper.getUltEstadoEjg() -> Salida de scsEstadoejgExtendsMapper para obtener el ultimo estado del EJG");
			} else {
				LOGGER.warn("getUltEstadoEjg() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEstados() -> idInstitucion del token nula");
		}
		return estadoEjgDTO;
	}

	@Override
	public EjgDocumentacionDTO getDocumentos(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getExpedientesEconomicos() -> Entrada al servicio para obtener expedientes económicos");
		EjgDocumentacionDTO ejgDocumentacionDTO = new EjgDocumentacionDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info("getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info("getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info("getDocumentos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info("getDocumentos() / scsDocumentacionejgExtendsMappe.getDocumentos() -> Entrada a scsEjgExtendsMapper para obtener la documentación de EJG");
				ejgDocumentacionDTO.setEjgDocItems(scsDocumentacionejgExtendsMapper.getDocumentacion(ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info("getDocumentos() / scsDocumentacionejgExtendsMappe.getDocumentos() -> Salida de scsEjgExtendsMapper para obtener la documentación de EJG");
			} else {
				LOGGER.warn("getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDocumentos() -> idInstitucion del token nula");
		}

		LOGGER.info("getDocumentos() -> Salida del servicio para obtener la documentación de EJG");
		return ejgDocumentacionDTO;
	}

	@Override
	public EjgItem getDictamen(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("getDictamen() -> Entrada al servicio para obtener el colegiado");
		EjgItem dictamen = new EjgItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getDictamen() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEjgExtendsMapper para obtener información del Informe de Calificación");
				dictamen = scsEjgExtendsMapper.getDictamen(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString());
				LOGGER.info("getDictamen() / scsEjgExtendsMapper.getEstados() -> Salida de scsEjgExtendsMapper para obtener información del Informe de Calificación");
			} else {
				LOGGER.warn("getDictamen() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDictamen() -> idInstitucion del token nula");
		}
		return dictamen;
	}

	@Override
	public ComboDTO comboOrigen(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Entrada a scsOrigencajgExtendsMapper para rellenar el combo");
				comboItems = scsOrigencajgExtendsMapper.comboOrigen(usuarios.get(0).getIdlenguaje().toString(), idInstitucion.toString());
				LOGGER.info("comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Salida a scsOrigencajgExtendsMapper para rellenar el combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboActaAnnio(String anioacta, String idacta, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Entrada a scsActacomisionExtendsMapper para obtener los combo");
				comboItems = scsActacomisionExtendsMapper.getActaAnnio(idInstitucion.toString(), anioacta, idacta);
				LOGGER.info("comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Salida a scsActacomisionExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboActaAnnio() -> Salida del servicio");
		return comboDTO;
	}

	@Override
	public ResolucionEJGItem getResolucion(EjgItem ejgItem, HttpServletRequest request) {

		LOGGER.info("getResolucion() -> Entrada al servicio para obtener el colegiado");
		ResolucionEJGItem resolucion = new ResolucionEJGItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getResolucion() / scsEjgExtendsMapper.getResolucion() -> Entrada a scsEjgExtendsMapper para obtener información de la Resolución");
				resolucion = scsEjgExtendsMapper.getResolucion(ejgItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString());

				LOGGER.info("getResolucion() / scsEjgExtendsMapper.getResolucion() -> Salida de scsEjgExtendsMapper para obtener información de la Resolución");
			} else {
				LOGGER.warn("getResolucion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getResolucion() -> idInstitucion del token nula");
		}
		return resolucion;
	}

	@Override
	public Boolean getEditResolEjg(EjgItem ejgItem, HttpServletRequest request) {

		LOGGER.info("getEditResolEjg() -> Entrada al servicio para obtener el colegiado");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Boolean response = false;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getEditResolEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getEditResolEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getEditResolEjg() / ScsEstadoejgExtendsMapper.getEditResolEjg() -> Entrada a ScsEstadoejgExtendsMapper para obtener si la resolución es editable");
				String resolEdit = scsEstadoejgExtendsMapper.getEditResolEjg(ejgItem, idInstitucion.toString());

				if (resolEdit.equals("1")) {
					response = true;
				} else {
					response = false;
				}

				LOGGER.info("getEditResolEjg() / ScsEstadoejgExtendsMapper.getEditResolEjg() -> Salida de ScsEstadoejgExtendsMapper para obtener si la resolución es editable");
			} else {
				LOGGER.warn("getEditResolEjg() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEditResolEjg() -> idInstitucion del token nula");
		}
		return response;
	}

	@Override
	public ComboDTO comboTipoExpediente(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");
				comboItems = expExpedienteExtendsMapper.comboTipoExpediente(usuarios.get(0).getIdlenguaje().toString(), idInstitucion.toString());
				LOGGER.info("comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public UpdateResponseDTO cambioEstadoMasivo(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para cambiar los estados y la fecha de estado para los ejgs");

				try {
					for (int i = 0; datos.size() > i; i++) {
						response = cambioEstadoMasivoItem(datos.get(i), usuarios.get(0), idInstitucion);
					}

					LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.cambiarEstadoEJGs() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio.");

		return responsedto;
	}

	@Transactional(rollbackFor = Exception.class)
	private int cambioEstadoMasivoItem(EjgItem dato, AdmUsuarios usuario, Short idInstitucion) throws Exception {
		ScsEstadoejg record = new ScsEstadoejg();

		// creamos el objeto para el insert
		record.setIdinstitucion(idInstitucion);
		record.setIdtipoejg(Short.parseShort(dato.getTipoEJG()));
		record.setAnio(Short.parseShort(dato.getAnnio()));
		record.setNumero(Long.parseLong(dato.getNumero()));
		record.setIdestadoejg(Short.parseShort(dato.getEstadoNew()));
		record.setFechainicio(dato.getFechaEstadoNew());
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setAutomatico("0");
		record.setObservaciones(dato.getObservaciones());
		// obtenemos el maximo de idestadoporejg
		ScsEstadoejgExample example = new ScsEstadoejgExample();
		example.setOrderByClause("IDESTADOPOREJG DESC");
		example.createCriteria().andAnioEqualTo(Short.parseShort(dato.getAnnio())).andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.parseShort(dato.getTipoEJG())).andNumeroEqualTo(Long.parseLong(dato.getNumero()));

		List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

		// damos el varlo al idestadoporejg + 1
		if (listEjg.size() > 0) {
			record.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
		} else {
			record.setIdestadoporejg(Long.parseLong("0"));
		}

		// scsEstadoejgMapper.selectByExample(example)

		int response = scsEstadoejgMapper.insert(record);

		if (response != 0 && record.getIdestadoejg() != null && record.getIdestadoejg().equals(SigaConstants.ESTADOS_EJG.LISTO_REMITIR_COMISION.getCodigo())) {
			ejgIntercambiosHelper.insertaCambioEstadoPericles(record, usuario);
		}

		return response;
	}

	@Override
	@Transactional
	public ResponseEntity<InputStreamResource> descargarExpedientesJG(List<EjgItem> itemEJG, HttpServletRequest request) throws BusinessException {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ResponseEntity<InputStreamResource> response = null;
		List<DatosDocumentoItem> ficheros = new ArrayList<DatosDocumentoItem>();
		HttpHeaders headers = new HttpHeaders();
		File fichero = null;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Generando fichero y creando la descarga...");

				try {
					// recorremos la lista para generar el documento de cada uno de los ejgs
					if (itemEJG != null) {
						for (EjgItem ejg : itemEJG) {
							// obtenemos la peticion y el idXML
							LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos de la petición...");

							ScsEjgExample exampleEjg = new ScsEjgExample();
							exampleEjg.createCriteria().andAnioEqualTo(Short.valueOf(ejg.getAnnio())).andIdinstitucionEqualTo(Short.valueOf(ejg.getidInstitucion())).andNumejgEqualTo(ejg.getNumEjg()).andIdtipoejgEqualTo(Short.valueOf(ejg.getTipoEJG()));

							List<ScsEjg> expedientes = scsEjgExtendsMapper.selectByExample(exampleEjg);
							if (!expedientes.isEmpty()) {
								ejg.setNumero(expedientes.get(0).getNumero().toString());
								List<ScsEejgPeticiones> peticiones = scsEejgPeticionesExtendsMapper.getPeticionesPorEJG(ejg);

								if (peticiones != null && peticiones.size() > 0) {

									for (ScsEejgPeticiones peticion : peticiones) {
										if (!UtilidadesString.esCadenaVacia(peticion.getCsv())) {
											// obtenemos los datos del fichero
											LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos para el informe...");
											Map<Integer, Map<String, String>> mapInformeEejg = eejgServiceImpl.getDatosInformeEejg(ejg, peticion);

											LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo el informe...");
											DatosDocumentoItem documento = eejgServiceImpl.getInformeEejg(mapInformeEejg, ejg.getidInstitucion());
											if (documento.getDatos() != null) {
												ficheros.add(documento);
											}
										}
									}
								}
							}

						}
					}

					if (ficheros.isEmpty()) {
						throw new BusinessException("No se puede descargar el archivo.");
					} else {
						fichero = WSCommons.zipBytes(ficheros, new File("downloads.zip"));

						String tipoMime = "application/zip";
						headers.setContentType(MediaType.parseMediaType(tipoMime));
						headers.set("Content-Disposition", "attachment; filename=\"" + fichero.getName() + "\"");
						headers.setContentLength(fichero.length());

						InputStream fileStream = new FileInputStream(fichero);
						response = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);

						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Acción realizada correctamente");
					}

				} catch (BusinessException e) {
					throw new BusinessException("No se ha encontrado archivos que descargar");
				} catch (Exception e) {
					if ("noExiste".equals(e.getMessage())) {
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() ->", e);
					} else {
						LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Se ha producido un error: ", e);
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarExpedientesJG() -> Salida del servicio.");
		return response;
	}

	@Override
	@Transactional
	public EjgDTO insertaDatosGenerales(EjgItem datos, HttpServletRequest request) throws Exception {
		EjgDTO ejgdto = new EjgDTO();
		int response = 0;
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para insertar los datos generales del ejg");

				// Para que @Transactional funcione adecuadamente se comenta el try y el catch
//                                                           try {
				record = setDatosGeneralesEJG(datos);

				// AGREGAMOS DATOS QUE FALTAN EN EL RECORD

				// longitud maxima para num ejg
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("LONGITUD_CODEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> listParam = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				String longitudEJG = listParam.get(0).getValor();

				// numejg

				String numEJG = scsEjgExtendsMapper.getNumeroEJG(record.getIdtipoejg(), record.getAnio(), record.getIdinstitucion());

				int numCeros = Integer.parseInt(longitudEJG) - numEJG.length();

				String ceros = "";
				for (int i = 0; i < numCeros; i++) {
					ceros += "0";
				}

				ceros += numEJG;

				record.setNumejg(ceros);

				// Repetimos el proceso con numero

				String numero = scsEjgExtendsMapper.getNumero(record.getIdtipoejg(), record.getAnio(), record.getIdinstitucion());

				numCeros = Integer.parseInt(longitudEJG) - numero.length();

				ceros = "";
				for (int i = 0; i < numCeros; i++) {
					ceros += "0";
				}

				ceros += numero;

				record.setNumero(Long.parseLong(ceros));

				// Determinamos el origen de apertura ya que, aunque no sea una clave primaria,
				// no se permita que tenga valor null.
				if (datos.getCreadoDesde() != null) {
					record.setOrigenapertura(datos.getCreadoDesde());
				} else {
					record.setOrigenapertura("M");
				}

				// Sucede lo mismo
				record.setTipoletrado("M");
				record.setFechacreacion(new Date());
				record.setFechamodificacion(new Date());
				record.setUsucreacion(usuarios.get(0).getIdusuario());
				record.setUsumodificacion(usuarios.get(0).getIdusuario());
				if (datos.getPerceptivo() != null) {
					record.setIdpreceptivo(Short.valueOf(datos.getPerceptivo()));
				}
				if (datos.getCalidad() != null) {
					record.setCalidad(datos.getCalidad());
				}

				// Campos opcionales
				record.setFechapresentacion(datos.getFechapresentacion());
				record.setFechalimitepresentacion(datos.getFechalimitepresentacion());
				if (datos.getTipoEJGColegio() != null)
					record.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));

				response = scsEjgMapper.insert(record);

				List<EjgItem> ejgItems = scsEjgExtendsMapper.datosEJG(datos, idInstitucion.toString(), usuarios.get(0).getIdlenguaje().toString());

				if (!ejgItems.isEmpty()) {
					datos.setEstadoEJG(ejgItems.get(0).getEstadoEJG());
				}

				if (response == 0)
					throw (new Exception("Error al insertar los datos generales del EJG"));

				// Campo de prestaciones
				if (datos.getPrestacionesRechazadas() != null) {
					ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();

					preRe.setIdinstitucion(idInstitucion);
					preRe.setAnio(Short.parseShort(datos.getAnnio()));
					preRe.setNumero(record.getNumero());
					preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
					preRe.setFechamodificacion(new Date());

					for (String idprestacion : datos.getPrestacionesRechazadas()) {
						preRe.setIdprestacion(Short.parseShort(idprestacion));

						response = scsEjgPrestacionRechazadaMapper.insert(preRe);
						if (response == 0)
							throw (new Exception("Error al insertar las prestaciones rechazadas del EJG"));
					}
				}

				// Se ejecuta de forma parametrizada este método que sustituye los triggers de la base de datos
				// al insertar una nueva fila en la tabla SCS_EJG por codigo java.

				GenParametrosExample exampleParam = new GenParametrosExample();
				exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS).andParametroEqualTo("ENABLETRIGGERSEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				exampleParam.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);

				if (parametrosTrigger != null && !parametrosTrigger.isEmpty() && parametrosTrigger.get(0).getValor().equals("1")) {
					this.triggersEjgInsert(record, usuarios.get(0), idInstitucion);
				}

				// respuesta si se actualiza correctamente para que se rellene el campo de
				// numero
				if (response >= 1) {
					List<EjgItem> list = new ArrayList<>();

					EjgItem item = new EjgItem();

					item.setidInstitucion(idInstitucion.toString());
					item.setAnnio(datos.getAnnio());
					item.setNumEjg(record.getNumejg());
					item.setNumero(record.getNumero().toString());
					item.setTipoEJG(datos.getTipoEJG());

					// Campos opcionales
					item.setFechapresentacion(datos.getFechapresentacion());
					item.setFechalimitepresentacion(datos.getFechalimitepresentacion());
					item.setTipoEJGColegio(datos.getTipoEJGColegio());
					item.setIdTipoExpInsos(datos.getIdTipoExpInsos());
					item.setEstadoEJG(datos.getEstadoEJG());
					if(datos.getNombreApeSolicitante() != null) {
						item.setNombreApeSolicitante(datos.getNombreApeSolicitante());
					}
					
					list.add(item);

					ejgdto.setEjgItems(list);

					ScsEjg ejg = new ScsEjg();

					ejg.setIdinstitucion(idInstitucion);
					ejg.setAnio(Short.valueOf(datos.getAnnio()));
					ejg.setNumero(Long.valueOf(record.getNumero().toString()));
					ejg.setIdtipoejg(Short.valueOf(datos.getTipoEJG()));

					insertAuditoriaEJG("EJG", null, "NUEVO", usuarios.get(0), ejg);
					// responsedto.setStatus(SigaConstants.OK);
					error.setCode(200);
					LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> OK. Datos generales insertados");
				} else {
					// responsedto.setStatus(SigaConstants.KO);
					error.setCode(400);
					LOGGER.error("GestionEJGServiceImpl.insertaDatosGenerales() -> KO. No se ha insertado los datos generales");
				}
				ejgdto.setError(error);

			}
		}

		LOGGER.info("GestionEJGServiceImpl.insertaDatosGenerales() -> Salida del servicio.");

		return ejgdto;
	}

	@Override
	public EjgDesignaDTO getEjgDesigna(EjgItem datos, HttpServletRequest request) {

		LOGGER.info("getEjgDesigna() -> Entrada al servicio para obtener el colegiado");
		EjgDesignaDTO ejgDesignaDTO = new EjgDesignaDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {

				LOGGER.info("getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Entrada a scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");

				try {
					ScsEjgdesignaExample example = new ScsEjgdesignaExample();

					example.createCriteria().andAnioejgEqualTo(Short.parseShort(datos.getAnnio())).andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.parseShort(datos.getTipoEJG())).andNumeroejgEqualTo(Long.parseLong(datos.getNumero()));

					example.setOrderByClause(" aniodesigna DESC, NUMERODESIGNA DESC");

					List<ScsEjgdesigna> ejgDesignas = scsEjgdesignaMapper.selectByExample(example);

					ejgDesignaDTO.setScsEjgdesignas(ejgDesignas);

					LOGGER.info("getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Salida de scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");
				} catch (Exception e) {
					LOGGER.debug("getEjgDesigna() -> Se ha producido un error al obtener asociaciones con designaciones del EJG. ", e);
				}
			} else {
				LOGGER.warn("getEjgDesigna() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getEjgDesigna() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener las asociaciones con designaciones del EJG.");
		return ejgDesignaDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO actualizaDatosGenerales(EjgItem datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para actualizar los datos generales del ejg");

				response = 0;

				// seleccionamos el objeto para el update

				ScsEjgKey ejgKey = new ScsEjgKey();

				ejgKey.setIdinstitucion(idInstitucion);
				ejgKey.setAnio(Short.parseShort(datos.getAnnio()));
				ejgKey.setNumero(Long.parseLong(datos.getNumero()));
				ejgKey.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));

				ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

				// Modificamos el objeto obtenido
				// SIGARNV-2438@DTT.JAMARTIN@01/10/2021@INICIO
				if (datos.getFechaApertura() != null && !datos.getFechaApertura().equals(ejg.getFechaapertura())) {
					if (ejg.getFechaapertura() != null) {
						insertAuditoriaEJG("fecha de apertura", ejg.getFechaapertura().toString(), datos.getFechaApertura().toString(), usuarios.get(0), (ScsEjg) ejg);
					} else {
						insertAuditoriaEJG("fecha de apertura", null, datos.getFechaApertura().toString(), usuarios.get(0), (ScsEjg) ejg);
					}
					ejg.setFechaapertura(datos.getFechaApertura());
				}

				if (datos.getFechapresentacion() != null && !datos.getFechapresentacion().equals(ejg.getFechapresentacion())) {
					if (ejg.getFechapresentacion() != null) {
						insertAuditoriaEJG("fecha de presentacion", ejg.getFechapresentacion().toString(), datos.getFechapresentacion().toString(), usuarios.get(0), (ScsEjg) ejg);
					} else {
						insertAuditoriaEJG("fecha de presentacion", null, datos.getFechapresentacion().toString(), usuarios.get(0), (ScsEjg) ejg);
					}
					ejg.setFechapresentacion(datos.getFechapresentacion());
				}

				if (datos.getIdGuardia() == null) {
					ejg.setGuardiaturnoIdguardia(null);
				} else {
					ejg.setGuardiaturnoIdguardia(Integer.parseInt(datos.getIdGuardia()));
				}

				if (datos.getIdTurno() == null) {
					ejg.setGuardiaturnoIdturno(null);
				} else {
					ejg.setGuardiaturnoIdturno(Integer.parseInt(datos.getIdTurno()));
				}
				// Persona de la tarjeta de Servicio de Tramitación (Opcional)
				if (!UtilidadesString.esCadenaVacia(datos.getIdPersona()))
					ejg.setIdpersona(Long.parseLong(datos.getIdPersona()));
				else
					ejg.setIdpersona(null);

				if (datos.getFechalimitepresentacion() != null && !datos.getFechalimitepresentacion().equals(ejg.getFechalimitepresentacion())) {
					if (ejg.getFechalimitepresentacion() != null) {
						insertAuditoriaEJG("fecha limite de presentacion", ejg.getFechalimitepresentacion().toString(), datos.getFechalimitepresentacion().toString(), usuarios.get(0), (ScsEjg) ejg);
					} else {
						insertAuditoriaEJG("fecha limite de presentacion", null, datos.getFechalimitepresentacion().toString(), usuarios.get(0), (ScsEjg) ejg);
					}
					ejg.setFechalimitepresentacion(datos.getFechalimitepresentacion());
				}
				// SIGARNV-2438@DTT.JAMARTIN@01/10/2021@FIN

				if (datos.getTipoEJGColegio() != null) {
					if (ejg.getIdtipoejgcolegio() == null || ejg.getIdtipoejgcolegio() != Short.parseShort(datos.getTipoEJGColegio())) {
						if (ejg.getIdtipoejgcolegio() != null)
							insertAuditoriaEJG("Tipo EEJG Colegio", ejg.getIdtipoejgcolegio().toString(), datos.getTipoEJGColegio().toString(), usuarios.get(0), (ScsEjg) ejg);
						else
							insertAuditoriaEJG("Tipo EEJG Colegio", null, datos.getTipoEJGColegio().toString(), usuarios.get(0), (ScsEjg) ejg);
						ejg.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));
					}
				} else if (ejg.getIdtipoejgcolegio() != null) {
					insertAuditoriaEJG("Tipo EEJG Colegio", ejg.getIdtipoejgcolegio().toString(), null, usuarios.get(0), (ScsEjg) ejg);
					ejg.setIdtipoejgcolegio(null);

				}

				// Comprobamos si existe el valor de codigo designa en el campo codigo de BBDD.
				StringDTO parametros = new StringDTO();
				Integer longitudEjg;
				parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", idInstitucion.toString());
				// comprobamos la longitud para la institucion, si no tiene nada, cogemos el de
				// la institucion 0
				if (parametros != null && parametros.getValor() != null) {
					longitudEjg = Integer.parseInt(parametros.getValor());
				} else {
					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", "0");
					longitudEjg = Integer.parseInt(parametros.getValor());
				}

				// Rellenamos por la izquierda ceros hasta llegar a longitudDesigna
				while (datos.getNumEjg().length() < longitudEjg) {
					datos.setNumEjg("0" + datos.getNumEjg());
				}

				if (!ejg.getNumejg().equals(datos.getNumEjg())) {
					ScsEjgExample exampleEJG = new ScsEjgExample();
					exampleEJG.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(Short.parseShort(datos.getAnnio())).andNumejgEqualTo(datos.getNumEjg());
					List<ScsEjg> lista = scsEjgExtendsMapper.selectByExample(exampleEJG);
					if (lista != null && lista.size() > 0) {
						String codigoBBDD = lista.get(0).getNumejg();
						if (codigoBBDD != null) {

							String maxNumEJG = scsEjgExtendsMapper.getMaxNumEjg(idInstitucion, datos.getAnnio(), datos.getTipoEJG());
							Error error = new Error();
							error.setCode(400);
							error.setDescription(maxNumEJG);
							error.setMessage("justiciaGratuita.oficio.ejg.yaexiste");
							responsedto.setStatus(SigaConstants.KO);
							responsedto.setError(error);
							return responsedto;
						}
					}
					insertAuditoriaEJG("Numero EJG", ejg.getNumejg().toString(), datos.getNumEjg(), usuarios.get(0), (ScsEjg) ejg);
					ejg.setNumejg(datos.getNumEjg());
				}

				ejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				ejg.setFechamodificacion(new Date());

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				GenParametrosExample exampleParam = new GenParametrosExample();
				exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS).andParametroEqualTo("ENABLETRIGGERSEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				exampleParam.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);

				if (parametrosTrigger != null && !parametrosTrigger.isEmpty() && parametrosTrigger.get(0).getValor().equals("1")) {
					this.triggersEjgUpdatesFApertura(datos, usuarios.get(0), idInstitucion);
				}

				// Actualizamos la entrada en la BBDD
				response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar los datos generales del EJG"));

				// Actualizar el expediente del que se extrae el tipo de expediente

				ExpExpedienteKey expKey = new ExpExpedienteKey();

				ExpExpediente newExp = null;

				if (datos.getAnioexpInsos() != null && datos.getNumeroexpInsos() != null && datos.getIdTipoExpInsos() != null && datos.getIdInstTipoExp() != null) {
					expKey.setIdinstitucion(idInstitucion);

					expKey.setAnioexpediente(Short.parseShort(datos.getAnioexpInsos()));
					expKey.setNumeroexpediente(Integer.parseInt(datos.getNumeroexpInsos()));
					expKey.setIdtipoexpediente(Short.parseShort(datos.getIdTipoExpInsos()));
					expKey.setIdinstitucionTipoexpediente(Short.parseShort(datos.getIdInstTipoExp()));

					newExp = expExpedienteMapper.selectByPrimaryKey(expKey);
				}

				if (newExp != null) {
					if (datos.getTipoEJG() != null) {
						if (newExp.getIdtipoexpediente() != null)
							insertAuditoriaEJG("Tipo expediente", newExp.getIdtipoexpediente().toString(), datos.getIdTipoExpInsos(), usuarios.get(0), (ScsEjg) ejg);
						else
							insertAuditoriaEJG("Tipo expediente", null, datos.getIdTipoExpInsos(), usuarios.get(0), (ScsEjg) ejg);
						newExp.setIdtipoexpediente(Short.parseShort(datos.getIdTipoExpInsos()));
					} else {
						if (newExp.getIdtipoexpediente() != null) {
							insertAuditoriaEJG("Tipo expediente", newExp.getIdtipoexpediente().toString(), null, usuarios.get(0), (ScsEjg) ejg);
							newExp.setIdtipoexpediente(null);
						}

					}

					newExp.setUsumodificacion(usuarios.get(0).getIdusuario());
					newExp.setFechamodificacion(new Date());

					response = expExpedienteMapper.updateByPrimaryKeySelective(newExp);
					if (response == 0)
						throw (new Exception("Error el expediente de insostenibilidad asociado al EJG"));
				}

				// Manejo de prestaciones
				// Se comprueban las prestaciones rechazadas en la ficha.
				// El proceso que se va a seguir sera comprobar si se recibe una lista
				// prestaciones rechazadas
				// en tal caso, eliminar las existentes y despues recorrer las recibidas para i

				// Clave primaria
				// IDINSTITUCION
				// ANIO
				// NUMERO
				// IDTIPOEJG
				// IDPRESTACION

				// Comprobar que no es true con un conjunto vacio.
				if (datos.getPrestacionesRechazadas() != null) {
					ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();

					preRe.setIdinstitucion(idInstitucion);
					preRe.setAnio(Short.parseShort(datos.getAnnio()));
					preRe.setNumero(Long.parseLong(datos.getNumero()));
					preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
					preRe.setFechamodificacion(new Date());

					ScsEjgPrestacionRechazadaExample examplePresRe = new ScsEjgPrestacionRechazadaExample();

					examplePresRe.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(preRe.getAnio()).andNumeroEqualTo(preRe.getNumero()).andIdtipoejgEqualTo(preRe.getIdtipoejg()).andIdtipoejgEqualTo(preRe.getIdtipoejg());

					List<ScsEjgPrestacionRechazada> rechazadas = scsEjgPrestacionRechazadaMapper.selectByExample(examplePresRe);

					// Eliminamos las entradas existentes relacionadas si las hubiera.

					response = scsEjgPrestacionRechazadaMapper.deleteByExample(examplePresRe);

					if (rechazadas.isEmpty())
						response = 1;

					for (String idprestacion : datos.getPrestacionesRechazadas()) {
						preRe.setIdprestacion(Short.parseShort(idprestacion));

						response = scsEjgPrestacionRechazadaMapper.insert(preRe);

						if (response == 0)
							throw (new Exception("Error al introducir prestaciones rechazadas al actualizar el EJG"));
					}

					String oldRechazadasString = "";
					for (ScsEjgPrestacionRechazada rechazada : rechazadas) {
						if (oldRechazadasString != "")
							oldRechazadasString += ",";
						oldRechazadasString += rechazada.getIdprestacion();
					}

					if (datos.getPrestacionesRechazadas().length > 0)
						insertAuditoriaEJG("Prestaciones rechazadas", oldRechazadasString, datos.getPrestacionesRechazadas().toString(), usuarios.get(0), (ScsEjg) ejg);

				}

				LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio para actualizar los datos generales del ejg");
			}

			// respuesta si se actualiza correctamente
			if (response >= 1) {
				responsedto.setId(datos.getNumEjg());
				responsedto.setStatus(SigaConstants.OK);
				LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> OK. Datos Generales actualizados para el ejg");
			} else {
				responsedto.setStatus(SigaConstants.KO);
				LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> KO. No se ha actualizado ningún dato general del ejg");
			}

		}

		LOGGER.info("GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio.");

		return responsedto;
	}

	/**
	 * metodo para settear los datos del objeto para el insert o update
	 *
	 * @param item
	 * @return
	 */
	private ScsEjgWithBLOBs setDatosGeneralesEJG(EjgItem item) {
		ScsEjgWithBLOBs result = new ScsEjgWithBLOBs();

		if (item.getidInstitucion() != null) {
			result.setIdinstitucion(Short.parseShort(item.getidInstitucion()));
		}
		
		if (item.getIdPersonajg() != null) {
			result.setIdpersonajg(Long.parseLong(item.getIdPersonajg()));
		}

		if (item.getFechaApertura() != null) {
			result.setFechaapertura(item.getFechaApertura());
		}

		if (item.getAnnio() != null) {
			result.setAnio(Short.parseShort(item.getAnnio()));
		}

		if (item.getNumero() != null) {
			result.setNumero(Long.parseLong(item.getNumEjg()));
		}

		if (item.getFechapresentacion() != null) {
			result.setFechapresentacion(item.getFechapresentacion());
		}

		if (item.getFechalimitepresentacion() != null) {
			result.setFechalimitepresentacion(item.getFechalimitepresentacion());
		}

		// ver si id o nombre de estado ¿situacion?
		if (item.getEstadoEJG() != null) {
			result.setIdsituacion(Short.parseShort(item.getEstadoEJG()));
		}

		// tenemos q ver si devuleve id o nombre de tipo ejg
		if (item.getTipoEJG() != null && !item.getTipoEJG().isEmpty()){

			result.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
		}

		if (item.getTipoEJGColegio() != null) {
			result.setIdtipoejgcolegio(Short.parseShort(item.getTipoEJGColegio()));
		}
		if (item.getIdGuardia() == null) {
			result.setGuardiaturnoIdguardia(null);
		} else {
			result.setGuardiaturnoIdguardia(Integer.parseInt(item.getIdGuardia()));
		}

		if (item.getIdTurno() == null) {
			result.setGuardiaturnoIdturno(null);
		} else {
			result.setGuardiaturnoIdturno(Integer.parseInt(item.getIdTurno()));
		}
		// Persona de la tarjeta de Servicio de Tramitación (Opcional)
		if (!UtilidadesString.esCadenaVacia(item.getIdPersona()))
			result.setIdpersona(Long.parseLong(item.getIdPersona()));
		else
			result.setIdpersona(null);

		// PRETENSIONES FALTA POR HACER
		// if(item.getPr!=null) {
		// result.set.setPr();
		// }

		// if(item.getAnioexpediente()!=null) {
		// result.setAn(Short.parseShort(item.getAnioexpediente()));
		// }
		//
		// if(item.getNumeroexpediente()!=null) {
		// result.setNum(Short.parseShort(item.getNumeroexpediente()));
		// }
		//
		//
		// if(item.getTurno()!=null) {
		// result.setTu(Short.parseShort(item.getTurno()));
		// }
		//
		// if(item.getGuardia()!=null) {
		// result.setGuardiaturnoIdguardia(item.getGuardia());
		// }
		//
		// if(item.getNumColegiado()!=null) {
		// result.setNum(Short.parseShort(item.getNumColegiado()));
		// }
		//
		// if(item.getNumeroexpediente()!=null) {
		// result.setNum(Short.parseShort(item.getNumeroexpediente()));
		// }

		return result;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarEstado(List<EstadoEjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.borrarEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.borrarEstado() -> Entrada para cambiar los datos generales del ejg");

				try {
					for (EstadoEjgItem item : datos) {
						response = 0;

						ScsEstadoejg record = new ScsEstadoejg();
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(item.getIdtipoejg()));
						record.setAnio(Short.parseShort(item.getAnio()));
						record.setNumero(Long.parseLong(item.getNumero()));
						record.setIdestadoporejg(Long.parseLong(item.getIdestadoporejg()));
						record.setFechabaja(new Date());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());

						response = scsEstadoejgExtendsMapper.bajaEstadoEjg(record);

						if (response != 1) {
							responsedto.setStatus(SigaConstants.KO);
							LOGGER.error("GestionEJGServiceImpl.borrarEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
							throw new Exception("ERROR: no se ha podido ");
						} else {
							responsedto.setStatus(SigaConstants.OK);

							ScsEjg ejg = new ScsEjg();

							ejg.setIdinstitucion(idInstitucion);
							ejg.setIdtipoejg(Short.parseShort(item.getIdtipoejg()));
							ejg.setAnio(Short.parseShort(item.getAnio()));
							ejg.setNumero(Long.parseLong(item.getNumero()));

							insertAuditoriaEJG("Estado borrado", record.getIdestadoporejg().toString(), null, usuarios.get(0), ejg);

						}
					}
					LOGGER.debug("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {

				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarFamiliar(List<UnidadFamiliarEJGItem> datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Entrada para cambiar los datos generales del ejg");
				// Comentamos el try y el catch para que el @Transactional funcione
				// correctamente
//                                                           try {
				for (int i = 0; datos.size() > i; i++) {

					// seleccionamos el objeto por primary key
					ScsUnidadfamiliarejgKey key = new ScsUnidadfamiliarejgKey();

					key.setIdinstitucion(idInstitucion);
					key.setIdtipoejg(Short.parseShort(datos.get(i).getUf_idTipoejg()));
					key.setAnio(Short.parseShort(datos.get(i).getUf_anio()));
					key.setNumero(Long.parseLong(datos.get(i).getUf_numero()));
					key.setIdpersona(Long.parseLong(datos.get(i).getUf_idPersona()));

					ScsUnidadfamiliarejg record = scsUnidadfamiliarejgMapper.selectByPrimaryKey(key);

					// Modificamos el objeto
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());

					// Según se elimine o se active.
					if (datos.get(i).getFechaBaja() == null) {
						record.setFechabaja(new Date());

						// Se elimina su propiedad como solicitante y su rol
						record.setSolicitante((short) 0);

						// Actualizamos el ejg correspondientemente para que no lo considere su
						// solicitante principal
						if (record.getEncalidadde() != null && record.getEncalidadde().equals("3")) {
							ScsEjgKey ejgKey = new ScsEjgKey();

							ejgKey.setAnio(Short.parseShort(datos.get(i).getUf_anio()));
							ejgKey.setIdinstitucion(idInstitucion);
							ejgKey.setIdtipoejg(Short.parseShort(datos.get(i).getUf_idTipoejg()));
							ejgKey.setNumero(Long.parseLong(datos.get(i).getUf_numero()));

							ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

							// String descripcionUnidadFamiliar = getDescripcionUnidadFamiliar(null, record, "INSERT");
							// auditoriaCenHistoricoService.insertaCenHistorico(null,
							// SigaConstants.CEN_TIPOCAMBIO.EJG_BORRAR_FAMILIAR, getDescripcionEjg(ejg, ejg, "UPDATE"), request, null);

//							insertAuditoriaEJG("Solicitante principal borrado", ejg.getIdpersonajg().toString(), null,	usuarios.get(0), ejg);

							ejg.setIdpersonajg(null);

							response = scsEjgMapper.updateByPrimaryKey(ejg);
							if (response == 0) {
								throw (new Exception("Error al actualizar eliminar el solicitante principal del EJG"));
							}
						}
					} else {
						record.setFechabaja(null);
					}

					record.setEncalidadde(null);

					response = scsUnidadfamiliarejgMapper.updateByPrimaryKey(record);
					if (response == 0)
						throw (new Exception("Error al actualizar los datos generales del EJG"));

					EjgItem ejgItem = new EjgItem();

					ejgItem.setAnnio(datos.get(i).getUf_anio());
					ejgItem.setidInstitucion(idInstitucion.toString());
					ejgItem.setTipoEJG(datos.get(i).getUf_idTipoejg());
					ejgItem.setNumero(datos.get(i).getUf_numero());

					response = actualizaEjgEnAsuntos(ejgItem, idInstitucion, "unidadFamiliar", usuarios.get(0));
					if (response == 0)
						throw (new Exception("Error al copiar los datos a otros asuntos"));

//                    ScsPersonajgKey perKey = new ScsPersonajgKey();
//
//                    perKey.setIdinstitucion(idInstitucion);
//                    perKey.setIdpersona(record.getIdpersona());
//
//                    ScsPersonajg per = scsPersonajgMapper.selectByPrimaryKey(perKey);

//					if (record.getFechabaja() != null) {
					// auditoriaCenHistoricoService.insertaCenHistorico(null,
					// SigaConstants.CEN_TIPOCAMBIO.EJG_BORRAR_FAMILIAR,
					// getDescripcionUnidadFamiliar(record2, record, "UPDATE"), request, null);
					// insertAuditoriaEJG("Familiar borrado", per.getNif(), null, usuarios.get(0),
					// ejg);
//					} else {
					// auditoriaCenHistoricoService.insertaCenHistorico(null,
					// SigaConstants.CEN_TIPOCAMBIO.EJG_ACTIVAR_FAMILIAR_BORRADO,
					// getDescripcionUnidadFamiliar(record2, record, "UPDATE"), request, null);
					// insertAuditoriaEJG("Familiar activado", null, per.getNif(), usuarios.get(0),
					// ejg);
//					}

				}
				LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio para borrar familiares del ejg");
//                                                           } catch (Exception e) {
//                                                                           LOGGER.debug(
//                                                                                                          "GestionEJGServiceImpl.borrarFamiliar() -> Se ha producido un error al borrar familiares del ejg",
//                                                                                                          e);
//                                                                           response = 0;
//                                                           } finally {
				// respuesta si se actualiza correctamente
				if (response >= 1) {
					responsedto.setStatus(SigaConstants.OK);
					LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> OK.");

				} else {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error("GestionEJGServiceImpl.borrarFamiliar() -> KO.");
				}
//                                                           }
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	public InsertResponseDTO nuevoEstado(EstadoEjgItem datos, HttpServletRequest request) {
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para insertar un nuevo estado al ejg");

				try {
					response = insertarNuevoEstado(datos, usuarios.get(0), idInstitucion);

					LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Salida del servicio para introducir un nuevo estado al EJG");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Se ha producido un error al introducir un nuevo estado al EJG", e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> OK. nuevo estado introducido en el EJG");

						ScsEjg ejg = new ScsEjg();

						ejg.setAnio(Short.parseShort(datos.getAnio()));
						ejg.setIdinstitucion(idInstitucion);
						ejg.setIdtipoejg(Short.parseShort(datos.getIdtipoejg()));
						ejg.setNumero(Long.parseLong(datos.getNumero()));

						ScsMaestroestadosejg labelEstado = scsMaestroestadosejgMapper.selectByPrimaryKey(Short.valueOf(datos.getIdEstadoejg()));

						GenRecursosCatalogosKey labelRecursoKey = new GenRecursosCatalogosKey();

						labelRecursoKey.setIdlenguaje(usuarios.get(0).getIdlenguaje());
						labelRecursoKey.setIdrecurso(labelEstado.getDescripcion());

						GenRecursosCatalogos labelRecurso = genRecursosCatalogosMapper.selectByPrimaryKey(labelRecursoKey);
						insertAuditoriaEJG("Nuevo Estado", null, labelRecurso.getDescripcion(), usuarios.get(0), ejg);

					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.nuevoEstado() -> KO. Error al introducir un nuevo estado en el EJG");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Transactional(rollbackFor = Exception.class)
	private int insertarNuevoEstado(EstadoEjgItem datos, AdmUsuarios usuario, Short idInstitucion) throws Exception {
		ScsEstadoejg record = new ScsEstadoejg();
		int response = 0;

		// creamos el objeto para el insert
		record.setIdinstitucion(idInstitucion);
		record.setAnio(Short.parseShort(datos.getAnio()));
		record.setNumero(Long.parseLong(datos.getNumero()));

		record.setIdestadoejg(Short.parseShort(datos.getIdEstadoejg()));
		record.setFechainicio(datos.getFechaInicio());
		record.setObservaciones(datos.getObservaciones());

		record.setIdtipoejg(Short.parseShort(datos.getIdtipoejg()));

		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());

		// obtenemos el maximo de idestadoporejg
		ScsEstadoejgExample example = new ScsEstadoejgExample();
		example.setOrderByClause("IDESTADOPOREJG DESC");
		example.createCriteria().andAnioEqualTo(Short.parseShort(datos.getAnio())).andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.parseShort(datos.getIdtipoejg())).andNumeroEqualTo(Long.parseLong(datos.getNumero()));

		List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

		// damos el varlo al idestadoporejg + 1
		if (listEjg.size() > 0) {
			record.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
		} else {
			record.setIdestadoporejg(Long.parseLong("1"));
		}

		response = scsEstadoejgMapper.insertSelective(record);

		if (response != 0 && record.getIdestadoejg() != null && record.getIdestadoejg().equals(SigaConstants.ESTADOS_EJG.LISTO_REMITIR_COMISION.getCodigo())) {
			ejgIntercambiosHelper.insertaCambioEstadoPericles(record, usuario);
		}

		return response;
	}

	@Override
	@Transactional
	public UpdateResponseDTO editarEstado(EstadoEjgItem datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para cambiar los datos generales del ejg");

				ScsEjg ejg = new ScsEjg();

				ejg.setAnio(Short.parseShort(datos.getAnio()));
				ejg.setIdinstitucion(idInstitucion);
				ejg.setIdtipoejg(Short.parseShort(datos.getIdtipoejg()));
				ejg.setNumero(Long.parseLong(datos.getNumero()));

				ScsMaestroestadosejg labelEstado = scsMaestroestadosejgMapper.selectByPrimaryKey(Short.valueOf(datos.getIdEstadoejg()));

				GenRecursosCatalogosKey labelRecursoKey = new GenRecursosCatalogosKey();

				labelRecursoKey.setIdlenguaje(usuarios.get(0).getIdlenguaje());
				labelRecursoKey.setIdrecurso(labelEstado.getDescripcion());

				ScsEstadoejg record = new ScsEstadoejg();
				response = 0;

				record.setIdinstitucion(idInstitucion);
				record.setAnio(Short.parseShort(datos.getAnio()));
				record.setNumero(Long.parseLong(datos.getNumero()));
				record.setIdtipoejg(Short.parseShort(datos.getIdtipoejg()));

				record.setIdestadoejg(Short.parseShort(datos.getIdEstadoejg()));
				record.setFechainicio(datos.getFechaInicio());
				record.setObservaciones(datos.getObservaciones());

				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuarios.get(0).getIdusuario());
				record.setIdestadoporejg(Long.parseLong(datos.getIdestadoporejg()));

				response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);
				if (response == 0)
					throw (new Exception("Error al actualizar un estado"));

				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");

				// respuesta si se actualiza correctamente
				if (response == 1) {
					responsedto.setStatus(SigaConstants.OK);
					LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error("GestionEJGServiceImpl.nuevoEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarImpugnacion(EjgItem datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.guardarImpugnacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para cambiar los datos generales del ejg");

				ScsEjgKey scsEjgKey = new ScsEjgKey();

				scsEjgKey.setAnio(Short.valueOf(datos.getAnnio()));
				scsEjgKey.setIdinstitucion(idInstitucion);
				scsEjgKey.setIdtipoejg(Short.valueOf(datos.getTipoEJG()));
				scsEjgKey.setNumero(Long.valueOf(datos.getNumero()));

				ScsEjgWithBLOBs scsEjgWithBLOBs = scsEjgMapper.selectByPrimaryKey(scsEjgKey);

				if (scsEjgWithBLOBs != null) {
					scsEjgWithBLOBs.setFechaauto(datos.getFechaAuto());
					if (datos.getAutoResolutorio() != null) {
						scsEjgWithBLOBs.setIdtiporesolauto(Short.valueOf(datos.getAutoResolutorio()));
					} else
						scsEjgWithBLOBs.setIdtiporesolauto(null);
					if (datos.getSentidoAuto() != null) {
						scsEjgWithBLOBs.setIdtiposentidoauto(Short.valueOf(datos.getSentidoAuto()));
					} else
						scsEjgWithBLOBs.setIdtiposentidoauto(null);
					scsEjgWithBLOBs.setObservacionimpugnacion(datos.getObservacionesImpugnacion());
					// front -> numero impugnacion;
					scsEjgWithBLOBs.setNumeroresolucion(datos.getnImpugnacion());
					scsEjgWithBLOBs.setFechapublicacion(datos.getFechaPublicacion());
					if (datos.getBis()) {
						scsEjgWithBLOBs.setBisresolucion("1");
					} else
						scsEjgWithBLOBs.setBisresolucion("0");
					if (datos.isRequiereTurn()) {
						scsEjgWithBLOBs.setTurnadoratificacion("1");
					} else
						scsEjgWithBLOBs.setTurnadoratificacion("0");
				}

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				GenParametrosExample exampleParam = new GenParametrosExample();
				exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS).andParametroEqualTo("ENABLETRIGGERSEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				exampleParam.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);

				if (parametrosTrigger != null && !parametrosTrigger.isEmpty() && parametrosTrigger.get(0).getValor().equals("1")) {
					this.triggersEjgUpdatesImpug(datos, usuarios.get(0), idInstitucion);
				}

				response = scsEjgMapper.updateByPrimaryKeyWithBLOBs(scsEjgWithBLOBs);

				// respuesta si se actualiza correctamente
				if (response == 1) {
					responsedto.setStatus(SigaConstants.OK);
					error.setCode(200);
					LOGGER.debug("GestionEJGServiceImpl.guardarImpugnacion() -> OK.  Impugnacion");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					LOGGER.error("GestionEJGServiceImpl.guardarImpugnacion() -> KO. Se ha producido un error al actualizar la Impugnacion");
				}

				responsedto.setError(error);

			}
		}
		LOGGER.info("GestionEJGServiceImpl.guardarImpugnacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarResolucion(ResolucionEJGItem datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Boolean nuevo = false;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.guardarResolucion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.guardarResolucion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.guardarResolucion() -> Entrada para cambiar los datos de resolucion del EJG");
				// Para que la etiqueta @Transactional funcione adecuadamente debe recibir una
				// excepcion
				// try {

				// CAMBIO IMPORTANTE
				// Modificacion del modulo de comisiones: se desplaza la funcionalidad de este
				// servicio
				// a su modulo y se haran llamadas a sus servicios
				EjgItem fichaEjg = new EjgItem();

				fichaEjg.setAnnio(datos.getAnio().toString());

				if (datos.getAnnioActa() != null) {
					fichaEjg.setAnnioActa(datos.getAnnioActa().toString());
				} else {
					fichaEjg.setAnnioActa(null);
				}

				if (datos.getAnioCAJG() != null) {
					fichaEjg.setAnnioCAJG(datos.getAnioCAJG().toString());
				} else {
					fichaEjg.setAnnioCAJG(null);
				}

				fichaEjg.setFechaPonenteDesd(datos.getFechaPresentacionPonente());

				if (datos.getIdActa() != null) {
					fichaEjg.setNumActa(datos.getIdActa().toString());
				} else {
					fichaEjg.setNumActa(null);
				}

				if (datos.getAnnioActa() != null) {
					fichaEjg.setAnnioActa(datos.getAnnioActa().toString());
				} else {
					fichaEjg.setNumActa(null);
				}

				if (datos.getIdFundamentoJuridico() != null) {
					fichaEjg.setFundamentoJuridico(datos.getIdFundamentoJuridico().toString());
				} else {
					fichaEjg.setFundamentoJuridico(null);
				}

				fichaEjg.setidInstitucion(idInstitucion.toString());

				if (datos.getIdPonente() != null) {
					fichaEjg.setPonente(datos.getIdPonente().toString());
				} else {
					fichaEjg.setPonente(null);
				}
				if (datos.getIdTipoEJG() != null) {
					fichaEjg.setTipoEJG(datos.getIdTipoEJG().toString());
				} else {
					fichaEjg.setTipoEJG(null);
				}
				fichaEjg.setIdTipoDictamen(datos.getIdTiporatificacionEJG());

				fichaEjg.setNumero(datos.getNumero().toString());
				fichaEjg.setNumEjg(datos.getNumero().toString());
				fichaEjg.setNumCAJG(datos.getNumeroCAJG());

				List<EjgItem> list = Arrays.asList(fichaEjg);

				// Se desplaza parte de la funcionalidad a los servicios del modulo de
				// comisiones
				if (datos.getIdActa() != null) {
					busquedaEJGComisionServiceImpl.editarActaAnio(list, request);
				} else {
					busquedaEJGComisionServiceImpl.borrarActaAnio(list, request);
				}
				// Se tiene esta comprobacion ya que algunas instituciones
				// No permiten guardar una resolucion sin
				if ((datos.getIdFundamentoJuridico() != null || busquedaEJGComisionServiceImpl.obligatorioFundamento(request).equals("0")) && datos.getIdTiporatificacionEJG() != null) {
					busquedaEJGComisionServiceImpl.editarResolucionFundamento(list, request);
				} else {
					busquedaEJGComisionServiceImpl.borrarResolucionFundamento(list, request);
				}
				if (datos.getIdPonente() != null && datos.getFechaPresentacionPonente() != null) {
					busquedaEJGComisionServiceImpl.editarPonente(list, request);
				} else {
					busquedaEJGComisionServiceImpl.borrarPonente(list, request);
				}

				// Se terminan de introducir los datos que faltan en el EJG y su resolucion en
				// el codigo antiguo
//                datos.getTipoResolucionCAJG();//FALTA Y NO SE USABA EN EL ORIGINAL

				// 1. Se selecciona el EJG asociado y se actualiza según los datos introducidos
				// en la tarjeta "resolucion"
				ScsEjgKey ejgKey = new ScsEjgKey();

				ejgKey.setAnio(datos.getAnio());
				ejgKey.setIdinstitucion(idInstitucion);
				ejgKey.setIdtipoejg(datos.getIdTipoEJG());
				ejgKey.setNumero(datos.getNumero());

				ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

//                ejg.setIdacta(datos.getIdActa());
//                ejg.setAnioacta(datos.getAnnioActa());
//                if (datos.getIdActa() == null)
//                    ejg.setIdinstitucionacta(null);
//                else
//                    ejg.setIdinstitucionacta(idInstitucion);
				ejg.setFecharesolucioncajg(datos.getFechaResolucionCAJG());
//                // Como se indica en el documento tecnico.
//                ejg.setIdtiporatificacionejg(datos.getIdTiporatificacionEJG());
//                ejg.setIdfundamentojuridico(datos.getIdFundamentoJuridico());
				ejg.setAniocajg(datos.getAnioCAJG());
				ejg.setNumeroCajg(datos.getNumeroCAJG());
//
//                ejg.setIdponente(datos.getIdPonente());
//                ejg.setFechapresentacionponente(datos.getFechaPresentacionPonente());
				ejg.setFecharatificacion(datos.getFechaRatificacion());
				ejg.setFechanotificacion(datos.getFechaNotificacion());
				ejg.setIdorigencajg(datos.getIdOrigencajg());
				ejg.setRefauto(datos.getRefAuto());
				if (datos.getTurnadoRatificacion() == "true") {
					ejg.setTurnadoratificacion("1");
				} else {
					ejg.setTurnadoratificacion("0");
				}
				if (datos.getRequiereNotificarProc() == "true") {
					ejg.setRequierenotificarproc("1");
				} else
					ejg.setRequierenotificarproc("0");

				ejg.setRatificaciondictamen(datos.getRatificacionDictamen());

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				GenParametrosExample exampleParam = new GenParametrosExample();
				exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS).andParametroEqualTo("ENABLETRIGGERSEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				exampleParam.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);

				if (parametrosTrigger != null && !parametrosTrigger.isEmpty() && parametrosTrigger.get(0).getValor().equals("1")) {
					this.triggersEjgUpdatesResol(datos, usuarios.get(0), idInstitucion);
				}

				response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar la parte de la resolucion del EJG"));
				// FUNCIONALIDAD DESPLAZADA AL APARTADO 3

				// 2. Se actualiza la tabla SCS_EJG_RESOLUCION
				ScsEjgResolucionKey ejgResolucionKey = new ScsEjgResolucionKey();

				ejgResolucionKey.setAnio(datos.getAnio());
				ejgResolucionKey.setIdinstitucion(idInstitucion);
				ejgResolucionKey.setIdtipoejg(datos.getIdTipoEJG());
				ejgResolucionKey.setNumero(datos.getNumero());

				ScsEjgResolucionWithBLOBs ejgResolucion = scsEjgResolucionMapper.selectByPrimaryKey(ejgResolucionKey);

				// En el caso que no se tuviera una entrada en la tabla previamente
				if (ejgResolucion == null) {

					ejgResolucion = new ScsEjgResolucionWithBLOBs();
					nuevo = true;

				}

				ejgResolucion.setAnio(datos.getAnio());
				ejgResolucion.setIdinstitucion(idInstitucion);
				ejgResolucion.setIdtipoejg(datos.getIdTipoEJG());
				ejgResolucion.setNumero(datos.getNumero());
//
//                ejgResolucion.setIdacta(datos.getIdActa());
//                // Se realiza esta asignacion ya que actualmente no se puede asignar EJGs a
//                // actas que no sean de la misma institucion.
//                ejgResolucion.setIdinstitucionacta(idInstitucion);
//                ejgResolucion.setAnioacta(datos.getAnnioActa());
				ejgResolucion.setFecharesolucioncajg(datos.getFechaResolucionCAJG());
				ejgResolucion.setIdtiporatificacionejg(datos.getIdTiporatificacionEJG());
//                ejgResolucion.setIdfundamentojuridico(datos.getIdFundamentoJuridico());
				ejgResolucion.setAniocajg(datos.getAnioCAJG());
				ejgResolucion.setNumeroCajg(datos.getNumeroCAJG());
//
				ejgResolucion.setRatificaciondictamen(datos.getRatificacionDictamen());
				ejgResolucion.setNotascajg(datos.getNotasCAJG());
//
//                ejgResolucion.setIdponente(datos.getIdPonente());
//                ejgResolucion.setFechapresentacionponente(datos.getFechaPresentacionPonente());
//                // Actualmente el combo de ponentes tienen el mismo idInstitucion que el EJG por
//                // lo que se le asigna el mismo valor.
//                ejgResolucion.setIdinstitucionponente(idInstitucion);
//
				ejgResolucion.setFechanotificacion(datos.getFechaNotificacion());
				ejgResolucion.setFecharatificacion(datos.getFechaRatificacion());
				ejgResolucion.setIdorigencajg(datos.getIdOrigencajg());
				ejgResolucion.setRefauto(datos.getRefAuto());
				if (datos.getTurnadoRatificacion() == "true") {
					ejgResolucion.setTurnadoratificacion("1");
				} else {
					ejgResolucion.setTurnadoratificacion("0");
				}
				if (datos.getRequiereNotificarProc() == "true") {
					ejgResolucion.setRequierenotificarproc("1");
				} else {
					ejgResolucion.setRequierenotificarproc("0");
				}

				ejgResolucion.setFechamodificacion(new Date());
				ejgResolucion.setUsumodificacion(usuarios.get(0).getIdusuario());

				if (nuevo)
					response = scsEjgResolucionMapper.insertSelective(ejgResolucion);
				else
					response = scsEjgResolucionMapper.updateByPrimaryKeyWithBLOBs(ejgResolucion);
				if (response == 0)
					throw (new Exception("Error al insertar o actualizar la parte de la resolucion asociada al EJG"));

				// 3. ASOCIAR EL EJG CON LA ACTA

//                if (ejg.getIdacta() != null) {
//
//                    // Se comprueba si el EJG ya esta relacionado con la acta
//                    ScsEjgActaExample exampleRelacion = new ScsEjgActaExample();
//
//                    exampleRelacion.createCriteria().andAnioejgEqualTo(ejg.getAnio())
//                            .andIdinstitucionejgEqualTo(idInstitucion).andIdtipoejgEqualTo(ejg.getIdtipoejg())
//                            .andNumeroejgEqualTo(ejg.getNumero());
//
//                    List<ScsEjgActa> relacionExistente = scsEjgActaMapper.selectByExample(exampleRelacion);
//                    LOGGER.info(
//                            "GestionEJGServiceImpl.guardarResolucion() -> Se inicia la asociacion entre el EJG y el acta.");
//                    // En el caso que no exista una relacion entre la acta seleccionada y el EJG
//                    if (relacionExistente.isEmpty()) {
//
//                        ScsEjgActa relacion = new ScsEjgActa();
//
//                        relacion.setIdinstitucionejg(idInstitucion);
//                        relacion.setAnioejg(ejg.getAnio());
//                        relacion.setNumeroejg(ejg.getNumero());
//                        relacion.setIdtipoejg(ejg.getIdtipoejg());
//
//                        relacion.setIdinstitucionacta(idInstitucion);
//                        relacion.setIdacta(ejg.getIdacta());
//                        relacion.setAnioacta(ejg.getAnioacta());
//
//                        relacion.setIdfundamentojuridico(datos.getIdFundamentoJuridico());
//                        relacion.setIdtiporatificacionejg(datos.getIdTiporatificacionEJG());
//
//                        relacion.setFechamodificacion(new Date());
//                        relacion.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//                        response = scsEjgActaMapper.insert(relacion);
//                        if (response == 0)
//                            throw (new Exception("Error al insertar la relacion entre la acta y el EJG"));
//                    }

//                    LOGGER.info(
//                            "GestionEJGServiceImpl.guardarResolucion() -> Se finaliza la asociacion entre el EJG y la acta.");
//                }
				// Para que la etiqueta @Transactional funcione adecuadamente debe recibir una
				// excepcion
//                                                           } catch (Exception e) {
//
//                                                                           Error error = new Error();
//                                                                           error.setCode(500);
//                                                                           error.setDescription("general.mensaje.error.bbdd");
//                                                                           error.setMessage(e.getMessage());
//                                                                           responsedto.setError(error);
//                                                                           response = 2;
//                                                                           responsedto.setStatus(SigaConstants.KO);
//                                                           }

//                if (response == 1) {
//                    LOGGER.debug(
//                            "GestionEJGServiceImpl.guardarResolucion() -> OK. Datos de resolucion cambiados en el EJG");
//                    responsedto.setStatus(SigaConstants.OK);
//                    Error error = new Error();
//                    error.setCode(200);
//                    responsedto.setError(error);
//                }
//
//                if (response == 0) {
//                    responsedto.setStatus(SigaConstants.KO);
//                    LOGGER.error(
//                            "GestionEJGServiceImpl.guardarResolucion() -> KO. No se ha actualizado ningún dato de resolucion en el EJG");
//                    Error error = new Error();
//                    error.setCode(500);
//                    error.setDescription("general.mensaje.error.bbdd");
//                    responsedto.setError(error);
//                }
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarResolucion() -> Salida del servicio.");

		responsedto.setStatus(SigaConstants.OK);
		return responsedto;
	}

	@Override
	public Boolean getHabilitarActa(HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Boolean habilitar = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getHabilitarActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("getHabilitarActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("getHabilitarActa() / scsSituacionesExtendsMapper.comboSituaciones() -> Entrada a scsSituacionesExtendsMapper para obtener el combo de situaciones");

//                                                           Por peticion del cliente (Adrian) No utilizamos una consulta SQL al uso si no utilizamos una funcion.
//                                                           GenParametrosKey genKey = new GenParametrosKey();
//
//                                                           genKey.setIdinstitucion(idInstitucion);
//                                                           genKey.setParametro("HABILITA_ACTAS_COMISION");
//                                                           genKey.setModulo("SCS");
//
//                                                           GenParametros parametro = genParametrosMapper.selectByPrimaryKey(genKey);

				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();

				parametroRequestDTO.setIdInstitucion(idInstitucion.toString());
				parametroRequestDTO.setModulo("SCS");
				parametroRequestDTO.setParametrosGenerales("HABILITA_ACTAS_COMISION");

				StringDTO parametro = genParamterosExtendsMapper.getParameterFunction(0, parametroRequestDTO);

				if (parametro.getValor().equals("N"))
					habilitar = false;
				else
					habilitar = true;

				LOGGER.info("getHabilitarActa() / scsSituacionesExtendsMapper.comboSituaciones() -> Salida a scsSituacionesExtendsMapper para obtener el combo de situaciones");
			}

		}
		LOGGER.info("getHabilitarActa() -> Salida del servicio para obtener la habilitacion de acta");
		return habilitar;
	}

	@Override
	@Transactional
	public UpdateResponseDTO descargarInformeCalificacion(EjgItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug("GestionEJGServiceImpl.descargarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.descargarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public DeleteResponseDTO borrarRelacion(List<String> datos, HttpServletRequest request) {
		DeleteResponseDTO responsedto = new DeleteResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String anioEjg = datos.get(4);
					String numEjg = datos.get(5);
					String idTurno = datos.get(3);
					String institucion = datos.get(0);
					String anioDes = datos.get(2);
					String numDes = datos.get(1);
					String idTipoEjg = datos.get(6);

					response = scsDesignacionesExtendsMapper.eliminarRelacion(anioEjg, numEjg, idTurno, institucion, anioDes, numDes, idTipoEjg);

					LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	public UpdateResponseDTO borrarRelacionAsistenciaEJG(RelacionesItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String idinstitucion = datos.getIdinstitucion();
					String numero = datos.getNumero();
					String anio = datos.getAnio();

					response = scsAsistenciaExtendsMapper.eliminarRelacionAsistencia(idinstitucion, anio, numero);

					LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	public UpdateResponseDTO borrarRelacionSojEJG(RelacionesItem datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String idinstitucion = datos.getIdinstitucion();
					String numero = datos.getNumero();
					String anio = datos.getAnio();
					String tipoSoj = datos.getIdtipo();

					response = scsSojExtendsMapper.eliminarRelacionSoj(idinstitucion, anio, numero, tipoSoj);

					LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO updateDatosJuridicos(EjgItem datos, HttpServletRequest request) throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.updateDatosJuridicos() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.updateDatosJuridicos() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.updateDatosJuridicos() -> Entrada para cambiar los datos generales del ejg");

				// Se comenta el try y el catch para que @Transactional funcione correctamente
//                                                           try {

				response = 0;

				ScsEjgKey ejgKey = new ScsEjgKey();

				// Preparamos las variables para buscar la tabla a actualizar
				ejgKey.setIdinstitucion(idInstitucion);
				ejgKey.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
				ejgKey.setAnio(Short.parseShort(datos.getAnnio()));
				ejgKey.setNumero(Long.parseLong(datos.getNumero()));

				ScsEjgWithBLOBs record = scsEjgMapper.selectByPrimaryKey(ejgKey);

				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuarios.get(0).getIdusuario());

				// Preparamos las variables de la tabla a actualizar
				if (datos.getRenuncia() != null)
					record.setIdrenuncia(Short.parseShort(datos.getRenuncia()));
				else
					record.setIdrenuncia(null);
				if (datos.getPerceptivo() != null)
					record.setIdpreceptivo(Short.parseShort(datos.getPerceptivo()));
				else
					record.setIdpreceptivo(null);
				record.setIdsituacion(datos.getIdsituacion());
				record.setNumerodiligencia(datos.getNumerodiligencia());
				record.setComisaria(datos.getComisaria());
				record.setCalidad(datos.getCalidad());
				if (datos.getCalidad() != null) {
					record.setIdtipoencalidad(Short.parseShort(datos.getCalidad()));
				} else {
					record.setIdtipoencalidad(null);
				}

				// Datos pre-designacion
				// SIGARNV-2429@DTT.JAMARTIN@30/09/2021@INICIO
				// record.setNumeroprocedimiento(datos.getNumAnnioProcedimiento());
				record.setNumeroprocedimiento(datos.getProcedimiento());
				// record.setAnioprocedimiento(datos.getAnnio());
				// SIGARNV-2429@DTT.JAMARTIN@30/09/2021@FIN
				record.setNig(datos.getNig());
				if (datos.getJuzgado() != null) {
					record.setJuzgado(Long.parseLong(datos.getJuzgado()));
					record.setJuzgadoidinstitucion(idInstitucion);
				} else
					record.setJuzgado(null);
				// SIGARNV-2429@DTT.JAMARTIN@30/09/2021@INICIO
				if (datos.getIdPretension() != null && datos.getIdPretension() != 0) {
					record.setIdpretension(datos.getIdPretension().longValue());
				} else {
					record.setIdpretension(null);
				}
				// SIGARNV-2429@DTT.JAMARTIN@30/09/2021@FIN
				record.setObservaciones(datos.getObservaciones()); // Campo Asunto
				record.setDelitos(datos.getDelitos()); // Campo Comentario

				response = scsEjgMapper.updateByPrimaryKey(record);
				if (response == 0)
					throw (new Exception("Error al actualizar la defensa juridica de la ficha pre-designacion del EJG"));

				// Actualizamos los delitos del ejg
				ScsDelitosejg delitos = new ScsDelitosejg();

				delitos.setIdinstitucion(idInstitucion);
				delitos.setAnio(Short.parseShort(datos.getAnnio()));
				delitos.setNumero(Long.parseLong(datos.getNumero()));
				delitos.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
				delitos.setUsumodificacion(usuarios.get(0).getIdusuario());
				delitos.setFechamodificacion(new Date());

				ScsDelitosejgExample oldDelitos = new ScsDelitosejgExample();

				oldDelitos.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(delitos.getAnio()).andNumeroEqualTo(delitos.getNumero()).andIdtipoejgEqualTo(delitos.getIdtipoejg());

				List<ScsDelitosejg> oldDel = scsDelitosejgMapper.selectByExample(oldDelitos);

				if (oldDel.isEmpty())
					response = 1;
				else {
					// Eliminamos las entradas existentes relacionadas si las hubiera.

					response = scsDelitosejgMapper.deleteByExample(oldDelitos);

					if (response == 0)
						throw (new Exception("Error al eliminar los delitos asociados a la ficha pre-designacion del EJG"));
				}
				if (datos.getDelitosSeleccionados() != null && !datos.getDelitosSeleccionados().isEmpty()) {
					String[] idDelitos = datos.getDelitosSeleccionados().split(",");
					for (String idDelito : idDelitos) {
						delitos.setIddelito(Short.parseShort(idDelito));
						response = scsDelitosejgMapper.insert(delitos);
						if (response == 0)
							throw (new Exception("Error al insertar los delitos asociados a la ficha pre-designacion del EJG"));
					}
				}

				response = actualizaEjgEnAsuntos(datos, idInstitucion, "defensaJuridica", usuarios.get(0));
				if (response == 0)
					throw (new Exception("Error al copiar los datos a otros asuntos"));

				LOGGER.debug("GestionEJGServiceImpl.updateDatosJuridicos() -> Salida del servicio para cambiar los datos juridicos para el ejg");
//                                                           } catch (Exception e) {
//                                                                           LOGGER.debug(
//                                                                                                          "GestionEJGServiceImpl.updateDatosJuridicos() -> Se ha producido un error al actualizar los datos juridicos para el ejg. ",
//                                                                                                          e);
//                                                                           response = 0;
//                                                           }
				// respuesta si se actualiza correctamente
				if (response == 1) {
					responsedto.setStatus(SigaConstants.OK);
					LOGGER.debug("GestionEJGServiceImpl.updateDatosJuridicos() -> OK. Datos juridicos del ejg actualizados");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error("GestionEJGServiceImpl.updateDatosJuridicos() -> KO. No se ha actualizado ningúno de los datos juridicos para el ejg");
				}

			}
		}

		LOGGER.info("GestionEJGServiceImpl.updateDatosJuridicos() -> Salida del servicio.");

		return responsedto;
	}

	private int actualizaEjgEnAsuntos(EjgItem datos, Short idInstitucion, String origen, AdmUsuarios usuario) {
		int respuesta, respuestaDes, respuestaAsi, respuestaSoj, respuestacopy;
		respuesta = respuestaDes = respuestaAsi = respuestaSoj = respuestacopy = 0;

		List<ScsSoj> relSojList = null;
		try {
			ScsEjgdesignaExample example = new ScsEjgdesignaExample();
			example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioejgEqualTo(Short.valueOf(datos.getAnnio())).andNumeroejgEqualTo(Long.valueOf(datos.getNumero())).andIdtipoejgEqualTo(Short.valueOf(datos.getTipoEJG()));

			List<ScsEjgdesigna> relDesignaList = scsEjgdesignaMapper.selectByExample(example);

			if (relDesignaList != null && !relDesignaList.isEmpty()) {
				for (ScsEjgdesigna relacion : relDesignaList) {

					// OPCION 1 crear método similar a los que hay en BusquedaAsuntosServiceImpl copyEjg2....
					// pero parametrizando la tarjeta origen que está haciendo el guardado de datos, por ejemplo, Defensa jurídica

					respuestacopy += copyEjg2Designa(idInstitucion, origen, usuario, relacion);

				}
				if (respuestacopy == relDesignaList.size()) {
					respuestaDes = 1;
				}
				respuestacopy = 0;
			}

			ScsAsistenciaExample exampleAsistencia = new ScsAsistenciaExample();
			exampleAsistencia.createCriteria().andIdinstitucionEqualTo(idInstitucion).andEjganioEqualTo(Short.valueOf(datos.getAnnio())).andEjgnumeroEqualTo(Long.valueOf(datos.getNumero())).andEjgidtipoejgEqualTo(Short.valueOf(datos.getTipoEJG()));

			List<ScsAsistencia> relAsistenciaList = scsAsistenciaExtendsMapper.selectByExample(exampleAsistencia);

			if (relAsistenciaList != null && !relAsistenciaList.isEmpty()) {
				for (ScsAsistencia relacion : relAsistenciaList) {
					respuestacopy += copyEjg2Asis(idInstitucion, origen, usuario, relacion);

				}
				if (respuestacopy == relAsistenciaList.size()) {
					respuestaAsi = 1;
				}
				respuestacopy = 0;
			}

			if (origen.equals("unidadFamiliar")) { // Si hay cambios en la unidad familiar solicitante principal, se actualiza los SOJ.
				ScsSojExample sojExample = new ScsSojExample();
				sojExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andEjgnumeroEqualTo(Long.valueOf(datos.getNumero())).andEjganioEqualTo(Short.valueOf(datos.getAnnio())).andEjgidtipoejgEqualTo(Short.valueOf(datos.getTipoEJG()));

				relSojList = scsSojMapper.selectByExample(sojExample);

				if (relSojList != null && !relSojList.isEmpty()) {
					for (ScsSoj relacion : relSojList) {
						respuestacopy += copyEjg2Soj(idInstitucion, origen, usuario, relacion);
					}
					if (respuestacopy == relSojList.size()) {
						respuestaSoj = 1;
					}
					respuestacopy = 0;
				}
			}

			if ((relDesignaList == null || relDesignaList.isEmpty()) && (relAsistenciaList == null || relAsistenciaList.isEmpty()) && (relSojList == null || relSojList.isEmpty())) {
				respuesta = 1; // si no tiene relaciones es que ha ido bien
			} else if (respuestaDes == 0 && respuestaAsi == 0 && respuestaSoj == 0) {
				respuesta = 0;
			} else {
				respuesta = 1;
			}

		} catch (Exception e) {
			return respuesta = 0;
		}
		return respuesta;
	}

	public int copyEjg2Soj(Short idInstitucion, String origen, AdmUsuarios usuario, ScsSoj relacion) throws Exception {
		int response = 0;
		ScsSoj soj = relacion;
		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setAnio(relacion.getEjganio());
		ejgKey.setIdtipoejg(relacion.getEjgidtipoejg());
		ejgKey.setNumero(relacion.getEjgnumero());

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Soj() -> EJG y SOJ seleccionados.");

		// 2. Se asignan el letrado y el solicitante principal del EJG al SOJ.

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Soj() -> Copiando informacion del EJG al SOJ.");

		soj.setIdpersonajg(ejg.getIdpersonajg());
		soj.setIdpersona(ejg.getIdpersona());

		soj.setUsumodificacion(usuario.getIdusuario());
		soj.setFechamodificacion(new Date());

		response = scsSojMapper.updateByPrimaryKeySelective(soj);
		if (response == 0)
			throw (new Exception("Error en copyEjg2Soj() al copiar los datos del EJG al SOJ."));

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Soj() -> Saliendo del servicio... ");

		return response;
	}

	public int copyEjg2Designa(Short idInstitucion, String origen, AdmUsuarios usuario, ScsEjgdesigna relacion) throws Exception {

		int response = 1;

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Entrada metodo copyEjg2Designa ");

		ScsDesignaKey designaKey = new ScsDesignaKey();

		designaKey.setIdinstitucion(idInstitucion);
		designaKey.setAnio(relacion.getAniodesigna());
		designaKey.setNumero(relacion.getNumerodesigna());

		designaKey.setIdturno(relacion.getIdturno());

		ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setAnio(relacion.getAnioejg());
		ejgKey.setIdtipoejg(relacion.getIdtipoejg());
		ejgKey.setNumero(relacion.getNumeroejg());

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		if (ejg != null && designa != null) {
			LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Encontrado Datos para relacionar ");

			if (origen.equals("defensaJuridica")) {
				LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Actualizando Defensa Jurifica en Relaciones");

				// 1. Se debe modificar los atributos asociados en la ficha predesignacion del
				// EJG en la designa.

				designa.setNumprocedimiento(ejg.getNumeroprocedimiento());
				designa.setAnioprocedimiento(ejg.getAnioprocedimiento());
				designa.setNig(ejg.getNig());
				designa.setResumenasunto(ejg.getObservaciones()); // Campo Asunto
				designa.setIdpretension(ejg.getIdpretension() != null ? ejg.getIdpretension().shortValue() : null);
				designa.setIdjuzgado(ejg.getJuzgado());
				// Actualizamos los delitos de la designacion eliminando los anteriores y
				// asignando los designados en EJG.
				ScsDelitosdesigna delitoDesigna = new ScsDelitosdesigna();

				delitoDesigna.setIdinstitucion(idInstitucion);
				delitoDesigna.setAnio(designa.getAnio());
				delitoDesigna.setNumero(designa.getNumero());
				delitoDesigna.setIdturno(designa.getIdturno());
				delitoDesigna.setUsumodificacion(usuario.getIdusuario());
				delitoDesigna.setFechamodificacion(new Date());

				// Seleccionamos y eliminamos los delitos anteriormente seleccionados en la
				// designacion.

				ScsDelitosdesignaExample delitosDesignaExample = new ScsDelitosdesignaExample();

				delitosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero()).andIdturnoEqualTo(designa.getIdturno());

				List<ScsDelitosdesigna> delitosDesigna = scsDelitosdesignaMapper.selectByExample(delitosDesignaExample);

				if (!delitosDesigna.isEmpty()) {
					response = scsDelitosdesignaMapper.deleteByExample(delitosDesignaExample);
					if (response == 0)
						throw (new Exception("Error al eliminar los delitos de la designacion"));
				}

				ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();

				delitosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg());

				List<ScsDelitosejg> delitosEjg = scsDelitosejgMapper.selectByExample(delitosEjgExample);

				if (!delitosEjg.isEmpty()) {
					for (ScsDelitosejg delitoEjg : delitosEjg) {
						delitoDesigna.setIddelito(delitoEjg.getIddelito());
						response = scsDelitosdesignaMapper.insert(delitoDesigna);
						if (response == 0)
							throw (new Exception("Error al introducir un delito en la designacion proveniente del EJG"));
					}
				}

				designa.setUsumodificacion(usuario.getIdusuario());
				designa.setFechamodificacion(new Date());

				response = scsDesignaMapper.updateByPrimaryKeySelective(designa);

				LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> FIN - Actualizando Defensa Jurifica en Relaciones");
			} else if (origen.equals("ContrariosEJG")) {
				// 2. Se debe insertar los contrarios seleccionados en EJG.

				LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Actualizando ContrariosEJG en Relaciones");

				// Obtenemos los contrarios ejg a introducir. Se seleccionan solo los activos
				// (con fecha de baja nula).

				ScsContrariosejgExample contrariosEjgExample = new ScsContrariosejgExample();

				contrariosEjgExample.createCriteria().andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero()).andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(ejg.getIdtipoejg()).andFechabajaIsNull();

				List<ScsContrariosejg> contrariosEjg = scsContrariosejgMapper.selectByExample(contrariosEjgExample);

				// Seleccionamos y borramos los contrarios presentes anteriormente en la
				// designacion

				ScsContrariosdesignaExample contrariosDesignaExample = new ScsContrariosdesignaExample();

				contrariosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero()).andIdturnoEqualTo(designa.getIdturno());

				List<ScsContrariosdesigna> contrariosDesigna = scsContrariosdesignaMapper.selectByExample(contrariosDesignaExample);

				if (!contrariosDesigna.isEmpty()) {
					response = scsContrariosdesignaMapper.deleteByExample(contrariosDesignaExample);
					if (response == 0)
						throw (new Exception("Error al eliminar los contrarios de la designacion"));
				}

				// Se crean los nuevos contrarios y se asignan

				ScsContrariosdesigna newContrarioDesigna = new ScsContrariosdesigna();
				newContrarioDesigna.setAnio(designa.getAnio());
				newContrarioDesigna.setNumero(designa.getNumero());
				newContrarioDesigna.setIdturno(designa.getIdturno());
				newContrarioDesigna.setIdinstitucion(designa.getIdinstitucion());

				newContrarioDesigna.setFechamodificacion(new Date());
				newContrarioDesigna.setUsumodificacion(usuario.getIdusuario());

				for (ScsContrariosejg contrarioEjg : contrariosEjg) {
					if (contrarioEjg.getFechabaja() == null) {
						ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
						scsPersonajgkey.setIdpersona(Long.valueOf(contrarioEjg.getIdpersona()));
						scsPersonajgkey.setIdinstitucion(idInstitucion);

						LOGGER.info("copyEjg2Designa() / scsPersonajgMapper.selectByPrimaryKey() -> Entrada a scsPersonajgMapper para obtener justiciables de los contrarios ejg");

						ScsPersonajg personajg = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

						LOGGER.info("copyEjg2Designa() / scsPersonajgMapper.selectByPrimaryKey() -> Salida de scsPersonajgMapper para obtener justiciables de los contrarios ejg");

						// Se comprueba si tiene representante y se busca.
						if (personajg.getIdrepresentantejg() != null) {

							scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

							ScsPersonajg representante = scsPersonajgMapper.selectByPrimaryKey(scsPersonajgkey);

							newContrarioDesigna.setNombrerepresentante(representante.getApellido1() + " " + representante.getApellido2() + ", " + representante.getNombre());
						}

						// Se le van asignando los distintos valores de IdPersona correspondientes
						newContrarioDesigna.setIdpersona(Long.valueOf(contrarioEjg.getIdpersona()));

						LOGGER.info("copyEjg2Designa() / ScsContrariosdesignaMapper.insert() -> Entrada a ScsContrariosdesignaMapper para insertar los contrarios ejg");

						response = scsContrariosdesignaMapper.insert(newContrarioDesigna);
						if (response == 0)
							throw (new Exception("Error al introducir contrarios en la designa provenientes del EJG"));

					}

				}

				LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> FIN - Actualizando ContrariosEJG en Relaciones");
			} else if (origen.equals("procuradorEJG")) {
				// 3. Se debe introducir el procurador seleccionado en el EJG.

				// Se comprueba que hay un procurador definido en el ejg para prevenir
				// inserciones fallidas
				if (ejg.getIdprocurador() != null) {
					LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Actualizando Procurador en Relaciones");

					ScsDesignaprocuradorExample exampleDesignaProcurador = new ScsDesignaprocuradorExample();
					exampleDesignaProcurador.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(designa.getIdturno()).andNumeroEqualTo(designa.getNumero()).andAnioEqualTo(designa.getAnio());

					List<ScsDesignaprocurador> listaDesignaPro = scsDesignaProcuradorMapper.selectByExample(exampleDesignaProcurador);

					ScsDesignaprocurador procDesigna = new ScsDesignaprocurador();

					procDesigna.setIdinstitucion(idInstitucion);
					procDesigna.setIdturno(designa.getIdturno());
					procDesigna.setNumero(designa.getNumero());
					procDesigna.setNumerodesignacion(ejg.getNumerodesignaproc());
					procDesigna.setAnio(designa.getAnio());

					procDesigna.setIdinstitucionProc(ejg.getIdinstitucionProc());
					procDesigna.setIdprocurador(ejg.getIdprocurador());
					procDesigna.setFechadesigna(ejg.getFechaDesProc());

					procDesigna.setUsumodificacion(usuario.getIdusuario());
					procDesigna.setFechamodificacion(new Date());

					procDesigna.setObservaciones(SigaConstants.OBS_IMPORTADO_EJG);

					if (listaDesignaPro != null && !listaDesignaPro.isEmpty()) {
						response = scsDesignaProcuradorMapper.updateByExample(procDesigna, exampleDesignaProcurador);
					} else {
						response = scsDesignaProcuradorMapper.insert(procDesigna);
					}

					if (response == 0)
						throw (new Exception("Error al introducir un procurador en la designa proveniente del EJG"));

					LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> FIN - Actualizando Procurador en Relaciones");

				} else {
					// Si el ejg no tiene procurador, borramos los procuradores del ejg.
					ScsDesignaprocuradorExample exampleDesignaProcurador = new ScsDesignaprocuradorExample();
					exampleDesignaProcurador.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdturnoEqualTo(designa.getIdturno()).andNumeroEqualTo(designa.getNumero()).andAnioEqualTo(designa.getAnio());
					response = scsDesignaProcuradorMapper.deleteByExample(exampleDesignaProcurador);

				}
			} else if (origen.equals("unidadFamiliar")) {
				// 4. Se debe insertar los interesados seleccionados en EJG en Unidad Familiar.

				// Seleccionamos y borramos los interesados presentes anteriormente en la
				// designacion
				LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Actualizando Unidad Familiar / Interesados en Relaciones");

				ScsDefendidosdesignaExample interesadosDesignaExample = new ScsDefendidosdesignaExample();

				interesadosDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(designa.getAnio()).andNumeroEqualTo(designa.getNumero()).andIdturnoEqualTo(designa.getIdturno());

				List<ScsDefendidosdesigna> interesadosDesigna = scsDefendidosdesignaMapper.selectByExample(interesadosDesignaExample);

				if (!interesadosDesigna.isEmpty()) {
					response = scsDefendidosdesignaMapper.deleteByExample(interesadosDesignaExample);
					if (response == 0)
						throw (new Exception("Error al eliminar los interesados de la designacion"));
				}

				// Se debe insertar los interesados seleccionados en EJG en Unidad Familiar.

				ScsUnidadfamiliarejgExample familiaresEJGExample = new ScsUnidadfamiliarejgExample();

				familiaresEJGExample.createCriteria().andAnioEqualTo(ejg.getAnio()).andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(ejg.getIdtipoejg()).andNumeroEqualTo(ejg.getNumero()).andFechabajaIsNull();

				List<ScsUnidadfamiliarejg> familiaresEJG = scsUnidadfamiliarejgMapper.selectByExample(familiaresEJGExample);

				// Se crea el interesado que se introducira en la designacion
				ScsDefendidosdesigna interesado = new ScsDefendidosdesigna();

				interesado.setAnio(designa.getAnio());
				interesado.setNumero(designa.getNumero());
				interesado.setIdinstitucion(idInstitucion);
				interesado.setIdturno(designa.getIdturno());

				for (ScsUnidadfamiliarejg familiar : familiaresEJG) {

					interesado.setIdpersona(familiar.getIdpersona());

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

					interesado.setUsumodificacion(usuario.getIdusuario());
					interesado.setFechamodificacion(new Date());

					response = scsDefendidosdesignaMapper.insert(interesado);
					if (response == 0)
						throw (new Exception("Error al introducir interesados en la designa proveniente de la unidad familiar del EJG"));

				}
				LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> FIN - Actualizando Unidad Familiar / Interesados en Relaciones");

			}
		} else {
			LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> No encontrado Datos para Actualizar");
			response = 0;
		}

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Designa() -> Saliendo del servicio");

		return response;
	}

	public int copyEjg2Asis(Short idInstitucion, String origen, AdmUsuarios usuario, ScsAsistencia relacion) throws Exception {

		int response = 0;

		// Tareas:
		// 1. Obtenemos los asuntos que vamos a manipular.

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Asis() -> Seleccionando EJG y la asistencia correspondientes.");

		ScsAsistenciaKey asisKey = new ScsAsistenciaKey();

		asisKey.setIdinstitucion(idInstitucion);
		asisKey.setNumero(relacion.getNumero());
		asisKey.setAnio(relacion.getAnio());

		ScsAsistencia asis = scsAsistenciaMapper.selectByPrimaryKey(asisKey);

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setAnio(relacion.getEjganio());
		ejgKey.setIdtipoejg(relacion.getEjgidtipoejg());
		ejgKey.setNumero(relacion.getEjgnumero());

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Asis() -> EJG y Asistencia seleccionados.");

		if (origen.equals("defensaJuridica")) {
			// 2. Actualizamos los delitos de la asistencia asignando los del EJG.

			LOGGER.info("GestionEJGServiceImpl.copyEjg2Asis() -> Copiando delitos del EJG a la asistencia.");

			ScsDelitosasistencia delitoAsistencia = new ScsDelitosasistencia();

			delitoAsistencia.setIdinstitucion(idInstitucion);
			delitoAsistencia.setAnio(asis.getAnio());
			delitoAsistencia.setNumero(asis.getNumero());
			delitoAsistencia.setUsumodificacion(usuario.getIdusuario());
			delitoAsistencia.setFechamodificacion(new Date());

			ScsDelitosasistenciaExample delitosAsistenciaExample = new ScsDelitosasistenciaExample();

//			delitosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio())
//					.andNumeroEqualTo(ejg.getNumero());

			delitosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(asis.getAnio()).andNumeroEqualTo(asis.getNumero());

			List<ScsDelitosasistencia> delitosAsistencia = scsDelitosasistenciaMapper.selectByExample(delitosAsistenciaExample);

			if (!delitosAsistencia.isEmpty()) {
				response = scsDelitosasistenciaMapper.deleteByExample(delitosAsistenciaExample);
				if (response == 0)
					throw (new Exception("Error al eliminar los delitos anteriores de la asistencia"));

			}

			ScsDelitosejgExample delitosEjgExample = new ScsDelitosejgExample();

			delitosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg());

			List<ScsDelitosejg> delitosEjg = scsDelitosejgMapper.selectByExample(delitosEjgExample);

			String delitosAsisString = "";
			if (!delitosEjg.isEmpty()) {
				for (ScsDelitosejg delitoEjg : delitosEjg) {
					if (delitosAsisString != "")
						delitosAsisString += ",";
					delitosAsisString += delitoEjg.getIddelito();
					delitoAsistencia.setIddelito(delitoEjg.getIddelito());
					response = scsDelitosasistenciaMapper.insert(delitoAsistencia);
					if (response == 0)
						throw (new Exception("Error al introducir un delito en la asistencia proveniente del EJG"));
				}
			}

			if (delitosAsisString.equals(""))
				asis.setDelitosimputados(null);
			else
				asis.setDelitosimputados(delitosAsisString);

			asis.setJuzgado(ejg.getJuzgado());
			asis.setJuzgadoidinstitucion(ejg.getJuzgadoidinstitucion());

			asis.setComisaria(ejg.getComisaria());
			asis.setComisariaidinstitucion(ejg.getComisariaidinstitucion());

//			asis.setNumeroprocedimiento(ejg.getNumeroprocedimiento() + "/" + ejg.getAnioprocedimiento());
			asis.setNumeroprocedimiento(ejg.getNumeroprocedimiento());
			asis.setNumerodiligencia(ejg.getNumerodiligencia());
			asis.setNig(ejg.getNig());
//			asis.setDelitosimputados(ejg.getDelitos());
			asis.setDatosdefensajuridica(ejg.getObservaciones()); // Campo Asunto
			asis.setObservaciones(ejg.getDelitos()); // Campo Comentario

			asis.setUsumodificacion(usuario.getIdusuario());
			asis.setFechamodificacion(new Date());

			LOGGER.info("GestionEJGServiceImpl.copyEjg2Asis() -> Delitos copiados del EJG a la asistencia.");

			response = scsAsistenciaMapper.updateByPrimaryKey(asis);
			if (response == 0)
				throw (new Exception("Error en copyEjg2Asis() al copiar los datos del EJG a la asistencia."));

		} else if (origen.equals("ContrariosEJG")) {
			// 3. Actualizamos los contrarios de la asistencia asignando los del EJG.

			LOGGER.info("GestionEJGServiceImpl.copyEjg2Asis() -> Copiando contrarios del EJG a la asistencia.");

			ScsContrariosasistencia contrarioAsistencia = new ScsContrariosasistencia();

			contrarioAsistencia.setIdinstitucion(idInstitucion);
			contrarioAsistencia.setAnio(asis.getAnio());
			contrarioAsistencia.setNumero(asis.getNumero());

			contrarioAsistencia.setUsumodificacion(usuario.getIdusuario());
			contrarioAsistencia.setFechamodificacion(new Date());

			ScsContrariosejgExample contrariosEjgExample = new ScsContrariosejgExample();

			contrariosEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg());

			List<ScsContrariosejg> contrariosEjg = scsContrariosejgMapper.selectByExample(contrariosEjgExample);

			ScsContrariosasistenciaExample contrariosAsistenciaExample = new ScsContrariosasistenciaExample();

			contrariosAsistenciaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(asis.getAnio()).andNumeroEqualTo(asis.getNumero());

			List<ScsContrariosasistencia> contrariosAsistencia = scsContrariosasistenciaMapper.selectByExample(contrariosAsistenciaExample);

			if (!contrariosAsistencia.isEmpty()) {
				response = scsContrariosasistenciaMapper.deleteByExample(contrariosAsistenciaExample);
				if (response == 0)
					throw (new Exception("Error al eliminar los contrarios anteriores de la asistencia"));

			}

			String contrariosAsisString = "";
			if (!contrariosEjg.isEmpty()) {
				for (ScsContrariosejg contrarioEjg : contrariosEjg) {
					if (contrarioEjg.getFechabaja() == null) {
						contrarioAsistencia.setIdpersona(contrarioEjg.getIdpersona());
						contrariosAsisString += contrarioEjg.getIdpersona();
						response = scsContrariosasistenciaMapper.insert(contrarioAsistencia);
						if (response == 0)
							throw (new Exception("Error al introducir un contrario en la asistencia proveniente del EJG"));

					}
				}
			}

			asis.setUsumodificacion(usuario.getIdusuario());
			asis.setFechamodificacion(new Date());
			asis.setContrarios(contrariosAsisString);

			response = scsAsistenciaMapper.updateByPrimaryKey(asis);
			if (response == 0)
				throw (new Exception("Error en copyEjg2Asis() al copiar los datos del EJG a la asistencia."));

		}else if(origen.equals("unidadFamiliar")) {
			response++;
		}

		// 4. Se asignan los datos del EJG a la asistencia.

		// asis.setIdpersonajg(ejg.getIdpersonajg());
		// asis.setIdpretension(ejg.getIdpretension() != null ? ejg.getIdpretension().shortValue() : null);
		// Estas en que tarjetas de ficha EJG se actualiza ?

		LOGGER.info("GestionEJGServiceImpl.copyEjg2Asis() -> Saliendo del servicio... ");

		return response;
	}

	/**
	 * getComunicaciones
	 */
	public EnviosMasivosDTO getComunicaciones(EjgItem item, HttpServletRequest request) {

		LOGGER.info("GestionEJGServiceImpl.getComunicaciones() -> Entrada al servicio para obtener comunicaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EnviosMasivosDTO enviosMasivosDTO = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosMasivosItem = new ArrayList<EnviosMasivosItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.debug("busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las comunicaciones");

				// obtenemos los datos de la comunicacion
				enviosMasivosItem = scsEjgExtendsMapper.getComunicaciones(item.getNumero(), item.getAnnio(), item.getTipoEJG(), idInstitucion, usuarios.get(0).getIdlenguaje());

				LOGGER.info("busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las comunicaciones");

				if (enviosMasivosItem != null) {
					enviosMasivosDTO.setEnviosMasivosItem(enviosMasivosItem);
				}
			}

		}
		LOGGER.info("busquedaComunicaciones() -> Salida del servicio para obtener comunicaciones");

		return enviosMasivosDTO;
	}

	public RelacionesDTO getRelacionesEJG(EjgItem item, HttpServletRequest request) {
		LOGGER.info("getRelacionesEJG() -> Entrada al servicio para obtener relaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RelacionesDTO relacionesDTO = new RelacionesDTO();
		List<RelacionesItem> relacionesItem = null;
		List<EjgItem> ejgDesigna = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("getRelacionesEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("getRelacionesEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("getEjgDesignas() / scsProcuradorExtendsMapper.getEjgDesignas() -> Entrada a scsDesignacionesExtendsMapper para obtener Anio y Numero Designas");

				ejgDesigna = scsEjgExtendsMapper.getEjgDesignas(item);

				LOGGER.info("getEjgDesignas() / scsProcuradorExtendsMapper.getEjgDesignas() -> Salida a scsDesignacionesExtendsMapper para obtener Anio y Numero Designas");

				if (ejgDesigna != null && ejgDesigna.size() > 0) {
					item.setAnioDesigna(ejgDesigna.get(0).getAnioDesigna());
					item.setNumeroDesigna(ejgDesigna.get(0).getNumeroDesigna());
				}

				LOGGER.info("getRelacionesEJG() / scsProcuradorExtendsMapper.busquedaRelaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las relaciones");

				relacionesItem = scsEjgExtendsMapper.getRelacionesEJG(item);

				LOGGER.info("busquedaRelaciones() / scsDesignacionesExtendsMapper.busquedaRelaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las relaciones");

				if (relacionesItem != null) {

					relacionesDTO.setRelacionesItem(relacionesItem);
				}
			}

		}
		LOGGER.info("getRelacionesEJG()) -> Salida del servicio para obtener relaciones");
		return relacionesDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumentoResolucion(String docResolucion, HttpServletRequest request) {
		LOGGER.info("GestionEJGServiceImpl.descargarDocumentoResolucion() -> Entrada al servicio servicio");

		ResponseEntity<InputStreamResource> res = null;
		HttpHeaders headers = new HttpHeaders();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("GestionEJGServiceImpl.descargarDocumentoResolucion() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionEJGServiceImpl.descargarDocumentoResolucion() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("GestionEJGServiceImpl.descargarDocumentoResolucion -> Entrada a servicio para la descarga del zip de la resolucion");
				try {
					// obtenemos los datos de la ruta de gen_properties

					// directorio fisico cajg
					GenPropertiesKey key = new GenPropertiesKey();

					key.setFichero("SIGA");
					key.setParametro("cajg.directorioFisicoCAJG");

					GenProperties directorioFisicoCAJG = genPropertiesMapper.selectByPrimaryKey(key);

					// directorio cajg java
					key = new GenPropertiesKey();
					key.setFichero("SIGA");
					key.setParametro("cajg.directorioCAJGJava");

					GenProperties directorioCAJGJava = genPropertiesMapper.selectByPrimaryKey(key);

					// directorio remesa resolcion
					key = new GenPropertiesKey();
					key.setFichero("SIGA");
					key.setParametro("cajg.directorioRemesaResoluciones");

					GenProperties directorioRemesaResoluciones = genPropertiesMapper.selectByPrimaryKey(key);

					// directorio Resoluciones Archivos
					key = new GenPropertiesKey();
					key.setFichero("SIGA");
					key.setParametro("cajg.directorioResolucionesArchivos");

					GenProperties directorioResolucionesArchivos = genPropertiesMapper.selectByPrimaryKey(key);

					String path = directorioFisicoCAJG.getValor() + directorioCAJGJava.getValor() + File.separator + idInstitucion + File.separator + directorioRemesaResoluciones.getValor() + File.separator + directorioResolucionesArchivos.getValor() + File.separator + docResolucion;

					LOGGER.info("GestionEJGServiceImpl.descargarDocumentoResolucion -> Path Descarga: " + path);

					File file = new File(path);
					if (file.exists()) {
						FileInputStream fileStream = new FileInputStream(file);

						headers.setContentType(MediaType.parseMediaType("application/zip"));

						headers.set("Content-Disposition", "attachment; filename=\"" + docResolucion + "\"");
//	                                                                           headers.setContentLength(file.length());

						res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);

						LOGGER.info("GestionEJGServiceImpl.descargarDocumentoResolucion() -> Operación realizada correctamente. Saliendo del servicio");
					} else {
						LOGGER.warn("GestionEJGServiceImpl.descargarDocumentoResolucion() -> No se encuentra el fichero actual.");
						res = new ResponseEntity<InputStreamResource>(null, null, HttpStatus.BAD_REQUEST);
					}
				} catch (Exception e) {
					res = new ResponseEntity<InputStreamResource>(null, null, HttpStatus.BAD_REQUEST);
					LOGGER.error("GestionEJGServiceImpl.descargarDocumentoResolucion() -> " + e.getMessage());

				}
			}
		}

		return res;
	}

	@Override
	public List<ListaContrarioEJGJusticiableItem> busquedaListaContrariosEJG(EjgItem item, HttpServletRequest request, Boolean historico) {
		LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Entrada al servicio servicio");
		List<ListaContrarioEJGJusticiableItem> contrarios = null;
		// List<GenParametros> tamMax = null;
		// Integer tamMaximo = null;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG -> Entrada a servicio para la busqueda de contrarios en EJG");

				try {
					contrarios = scsContrariosejgExtendsMapper.busquedaListaContrariosEJG(item, idInstitucion, historico);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG -> Salida del servicio");
				}
				LOGGER.info("GestionEJGServiceImpl.busquedaListaContrariosEJG -> Salida del servicio");
			}
		}

		return contrarios;
	}

	@Override
	public UpdateResponseDTO deleteContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
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

			LOGGER.info("deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());
					key.setIdtipoejg(item.getIdtipoejg());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					if (contrario.getFechabaja() == null) {
						contrario.setFechabaja(new Date());
					} else {
						contrario.setFechabaja(null);
					}

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info("deleteContrario() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					EjgItem ejgItem = new EjgItem();
					ejgItem.setidInstitucion(idInstitucion.toString());
					ejgItem.setAnnio(item.getAnio().toString());
					ejgItem.setNumero(String.valueOf(item.getNumero()));
					ejgItem.setTipoEJG(item.getIdtipoejg().toString());

					response = actualizaEjgEnAsuntos(ejgItem, idInstitucion, "ContrariosEJG", usuarios.get(0));
					if (response == 0)
						throw (new Exception("Error al copiar los datos a otros asuntos"));

					LOGGER.info("deleteContrario() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

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

		LOGGER.info("deleteContrario() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("insertContrario() ->  Entrada al servicio para insertar contrarios");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("insertContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("insertContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					ScsContrariosejg contrario = new ScsContrariosejg();

					contrario.setAnio(item.getAnio());
					contrario.setNumero(item.getNumero());
					contrario.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					contrario.setIdpersona(item.getIdpersona());
					contrario.setIdtipoejg(item.getIdtipoejg());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info("insertContrarioEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info("insertContrarioEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciable");

					// Se comprueba si tiene representante y se busca.
					if (personajg.getIdrepresentantejg() != null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						contrario.setNombrerepresentanteejg(representante.getApellido1() + " " + representante.getApellido2() + ", " + representante.getNombre());
					}

					LOGGER.info("insertContrarioEJG() / scsContrariosejgMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para insertar contrario ejg");

					response = scsContrariosejgMapper.insert(contrario);

					EjgItem ejgItem = new EjgItem();
					ejgItem.setidInstitucion(idInstitucion.toString());
					ejgItem.setAnnio(item.getAnio().toString());
					ejgItem.setNumero(String.valueOf(item.getNumero()));
					ejgItem.setTipoEJG(item.getIdtipoejg().toString());

					response = actualizaEjgEnAsuntos(ejgItem, idInstitucion, "ContrariosEJG", usuarios.get(0));
					if (response == 0)
						throw (new Exception("Error al copiar los datos a otros asuntos"));

					LOGGER.info("insertContrarioEJG() / scsContrariosejgMapper.insert() -> Salida de ScsDefendidosdesignaMapper para insertar contrario ejg");

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
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertContrario() -> Salida del servicio para insertar contrarios");

		return insertResponseDTO;
	}

	/**
	 * Cuando editamos un contrario y marcamos guardar como nuevo justiciable, actualizamos la referencia del justiciable antiguo al nuevo que acabamos de crear en la tabla SCS_CONTRARIOSEJG
	 */
	public InsertResponseDTO updateContrarioEJG(String[] item, HttpServletRequest request) {
		LOGGER.info("updateContrarioEJG() ->  Entrada al servicio para actualizar contrarios");

		InsertResponseDTO updateResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("updateContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("updateContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					// Contrario nuevo
					ScsContrariosejg contrario = new ScsContrariosejg();
					contrario.setIdpersona(Long.parseLong(item[0]));
					contrario.setAnio(Short.parseShort(item[1]));
					contrario.setNumero(Long.parseLong(item[3]));
					contrario.setIdtipoejg(Short.parseShort(item[2]));
					contrario.setIdinstitucion(idInstitucion);
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());
					contrario.setFechamodificacion(new Date());

					// Contrario antiguo
					long idPersonaOld = Long.parseLong(item[5]);

					ScsContrariosejgExample scsContrariosejgExample = new ScsContrariosejgExample();
					scsContrariosejgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(idPersonaOld).andAnioEqualTo(contrario.getAnio()).andNumeroEqualTo(contrario.getNumero()).andIdtipoejgEqualTo(contrario.getIdtipoejg());

					LOGGER.info("updateContrarioEJG() / scsContrariosejgMapper.updateByExampleSelective() -> Entrada a scsContrariosejgMapper para actualizar contrario ejg");

					// Actualizamos la referencia la contrario antiguo con el nuevo
					response = scsContrariosejgMapper.updateByExampleSelective(contrario, scsContrariosejgExample);

//					EjgItem ejgItem = new EjgItem();
//					ejgItem.setidInstitucion(idInstitucion.toString());
//					ejgItem.setAnnio(contrario.getAnio().toString());
//					ejgItem.setNumero(String.valueOf(contrario.getNumero()));
//					ejgItem.setTipoEJG(contrario.getIdtipoejg().toString());
//
//					response = actualizaEjgEnAsuntos(ejgItem, idInstitucion, "ContrariosEJG", usuarios.get(0));

					if (response == 0)
						throw (new Exception("Error al copiar los datos a otros asuntos"));

					LOGGER.info("updateContrarioEJG() / scsContrariosejgMapper.updateByExampleSelective() -> Salida de scsContrariosejgMapper para actualizar contrario ejg");

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
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("insertContrario() -> Salida del servicio para insertar contrarios");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateRepresentanteContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("updateRepresentanteContrarioEJG() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("updateRepresentanteContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("updateRepresentanteContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdtipoejg(item.getIdtipoejg());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					contrario.setNombrerepresentanteejg(item.getNombrerepresentanteejg());
					contrario.setIdrepresentanteejg(item.getIdrepresentanteejg());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info("updateRepresentanteContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosejgMapper para actualizar el representante de un contrario ejg.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info("updateRepresentanteContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosejgMapper para actualizar el representante de un contrario ejg.");

					// Obtenemos el justiciable
					ScsPersonajgKey perKey = new ScsPersonajgKey();

					perKey.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					perKey.setIdpersona(item.getIdpersona());

					ScsPersonajg per = scsPersonajgMapper.selectByPrimaryKey(perKey);

					// Le asignamos la id de su representante
					per.setIdrepresentantejg(item.getIdrepresentanteejg());

					LOGGER.info("updateRepresentanteContrarioEJG() / scsPersonajgMapper.updateByPrimaryKey() -> Entrada a scsPersonajgMapper para actualizar el id de representante de una persona ejg.");

					// Actualizamos el justiciable
					scsPersonajgMapper.updateByPrimaryKey(per);

					LOGGER.info("updateRepresentanteContrarioEJG() / scsPersonajgMapper.updateByPrimaryKey() -> Salida a scsPersonajgMapper para actualizar el id de representante de una persona ejg.");

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

		LOGGER.info("updateRepresentanteContrarioEJG() -> Salida del servicio para actualizar el representante de un contrario ejg");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateAbogadoContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("updateAbogadoContrarioEJG() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("updateAbogadoContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("updateAbogadoContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdtipoejg(item.getIdtipoejg());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					contrario.setIdabogadocontrarioejg(item.getIdabogadocontrarioejg());
					contrario.setNombreabogadocontrarioejg(item.getNombreabogadocontrarioejg());

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					// List<ColegiadoItem> colegiadosItems =
					// cenColegiadoExtendsMapper.selectColegiadosByIdPersona(idInstitucion,
					// contrario.getIdabogadocontrario().toString());
					//
					// FichaDatosColegialesItem abogado = colegiadosItems.get(0);

					// contrario.set

					LOGGER.info("updateAbogadoContrarioEJG() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info("updateAbogadoContrarioEJG() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

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

		LOGGER.info("updateAbogadoContrarioEJG() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateProcuradorContrarioEJG(ScsContrariosejg item, HttpServletRequest request) {
		LOGGER.info("updateProcuradorContrarioEJG() ->  Entrada al servicio para eliminar contrarios");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("updateProcuradorContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("updateProcuradorContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					// for(ScsContrariosdesigna item: items) {

					ScsContrariosejgKey key = new ScsContrariosejgKey();
					key.setAnio(item.getAnio());
					key.setNumero(item.getNumero());
					key.setIdtipoejg(item.getIdtipoejg());
					key.setIdinstitucion(Short.parseShort(idInstitucion.toString()));
					key.setIdpersona(item.getIdpersona());

					ScsContrariosejg contrario = scsContrariosejgMapper.selectByPrimaryKey(key);

					contrario.setIdprocurador(item.getIdprocurador());
					contrario.setIdinstitucionProcu(item.getIdinstitucionProcu());

					// List<FichaDatosColegialesItem> colegiadosSJCSItems =
					// cenColegiadoExtendsMapper
					// .selectDatosColegiales(item.getIdprocurador().toString(),
					// idInstitucion.toString());

					// FichaDatosColegialesItem procurador = colegiadosSJCSItems.get(0);
					// contrario.set

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info("updateProcuradorContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosejgMapper para actualizar el representante de un interesado.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info("updateProcuradorContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosejgMapper para actualizar el representante de un interesado.");

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

		LOGGER.info("updateProcuradorContrarioEJG() -> Salida del servicio para eliminar contrarios");

		return updateResponseDTO;
	}

	@Override
	public ProcuradorDTO busquedaProcuradorEJG(EjgItem ejg, HttpServletRequest request) {
		LOGGER.info("busquedaProcuradorEJG() -> Entrada al servicio para obtener procuradores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("busquedaProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("busquedaProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("busquedaProcuradorEJG() / scsEjgExtendsMapper.busquedaProcuradorEJG() -> Entrada a scsEjgExtendsMapper para obtener los procuradores");

				procuradorItemList = scsEjgExtendsMapper.busquedaProcuradorEJG(ejg.getIdProcurador(), ejg.getIdInstitucionProc().toString());

				LOGGER.info("busquedaProcuradorEJG() / scsEjgExtendsMapper.busquedaProcuradorEJG -> Salida a scsEjgExtendsMapper para obtener los procuradores");

				if (procuradorItemList != null) {
					if (!procuradorItemList.isEmpty()) {
						procuradorItemList.get(0).setNumeroTotalProcuradores(String.valueOf(procuradorItemList.size()));
					}
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("busquedaProcuradorEJG() -> Salida del servicio para obtener procuradores");
		return procuradorDTO;
	}

	@Override
	public UpdateResponseDTO guardarProcuradorEJG(EjgItem item, HttpServletRequest request) throws Exception {
		LOGGER.info("guardarProcuradorEJG() ->  Entrada al servicio para guardar procurador EJG");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				// Comentamos el try y el catch para que @Transactional funcione correctamente
//                                                           try {

				// Introducimos los atributos claves para seleccionar el ejg que queremos
				// actualizar

				ScsEjgKey ejgKey = new ScsEjgKey();

				ejgKey.setAnio(Short.parseShort(item.getAnnio()));
				ejgKey.setNumero(Long.parseLong(item.getNumero()));
				ejgKey.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
				ejgKey.setIdinstitucion(Short.parseShort(idInstitucion.toString()));

				ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

				if (item.getIdProcurador() != null)
					ejg.setIdprocurador(Long.parseLong(item.getIdProcurador()));
				else
					ejg.setIdprocurador(null);
				ejg.setIdinstitucionProc(item.getIdInstitucionProc());
				ejg.setFechaDesProc(item.getFechaDesProc());
				ejg.setNumerodesignaproc(item.getNumerodesignaproc());
				ejg.setFechamodificacion(new Date());
				ejg.setUsumodificacion(usuarios.get(0).getIdusuario());

				LOGGER.info("guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKey() -> Entrada a scsEjgMapper para guardar procurador EJG.");

				response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar el procurador de la ficha pre-designacion del EJG"));

				LOGGER.info("guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKey() -> Salida de scsEjgMapper para guardar procurador EJG.");

				if (item.getIdProcurador() != null) {
					ScsEstadoejg estado = new ScsEstadoejg();

					// creamos el objeto para el insert
					estado.setIdinstitucion(idInstitucion);
					estado.setAnio(Short.parseShort(item.getAnnio()));
					estado.setNumero(Long.parseLong(item.getNumero()));

					// Estado "Designado Procurador"
					estado.setIdestadoejg((short) 19);
					estado.setFechainicio(new Date());
					if (item.getNombreApProcurador() != null)
						estado.setObservaciones(item.getNombreApProcurador());
					else
						estado.setObservaciones("Ninguno");
					estado.setAutomatico("1");

					estado.setIdtipoejg(Short.parseShort(item.getTipoEJG()));

					estado.setFechamodificacion(new Date());
					estado.setUsumodificacion(usuarios.get(0).getIdusuario());

					// obtenemos el maximo de idestadoporejg
					estado.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

					response = scsEstadoejgMapper.insert(estado);
				} else {
					ScsEstadoejgExample exampleEstado = new ScsEstadoejgExample();
					exampleEstado.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(ejg.getAnio()).andNumeroEqualTo(ejg.getNumero()).andIdtipoejgEqualTo(ejg.getIdtipoejg()).andIdestadoejgEqualTo(Short.valueOf("19")).andFechabajaIsNull();

					List<ScsEstadoejg> estadosList = scsEstadoejgMapper.selectByExample(exampleEstado);
					for (ScsEstadoejg estado : estadosList) {
						estado.setFechabaja(new Date());
						response = scsEstadoejgMapper.updateByPrimaryKeySelective(estado);
					}
				}
				if (response == 0)
					throw (new Exception("Error al introducir el nuevo estado al cambiar el procurador del EJG"));

				response = actualizaEjgEnAsuntos(item, idInstitucion, "procuradorEJG", usuarios.get(0));
				if (response == 0)
					throw (new Exception("Error al copiar los datos a otros asuntos"));

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

		LOGGER.info("guardarProcuradorEJG() -> Salida del servicio para guardar procurador EJG");

		return updateResponseDTO;
	}

	public DelitosEjgDTO getDelitosEjg(EjgItem item, HttpServletRequest request) {

		LOGGER.info("GestionEJGServiceImpl.getDelitosEjg() -> Entrada al servicio para obtener delitos ejg");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DelitosEjgDTO delitosEjgDTO = new DelitosEjgDTO();
		List<ScsDelitosejg> delitosEjgItem = new ArrayList<ScsDelitosejg>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("getDelitosEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("getDelitosEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.debug("getDelitosEjg() / scsDelitosejgMapper.selectByExample() -> Entrada a scsDelitosejgMapper para obtener los delitos ejg");

				// Obtenemos los delitos ejg que esten asignados a nuestro EJG

				ScsDelitosejgExample example = new ScsDelitosejgExample();

				example.createCriteria().andAnioEqualTo(Short.parseShort(item.getAnnio())).andIdinstitucionEqualTo(idInstitucion).andNumeroEqualTo(Long.parseLong(item.getNumero())).andIdtipoejgEqualTo(Short.parseShort(item.getTipoEJG()));

				delitosEjgItem = scsDelitosejgMapper.selectByExample(example);

				LOGGER.info("getDelitosEjg() / scsDelitosejgMapper.selectByExample() -> Salida a scsDelitosejgMapper para obtener los delitos ejg");

				if (delitosEjgItem != null) {
					delitosEjgDTO.setDelitosEjgItem(delitosEjgItem);
				}
			}

		}
		LOGGER.info("getDelitosEjg() -> Salida del servicio para obtener delitos ejg");

		return delitosEjgDTO;
	}

//            @Override
//            @Transactional
//            public InsertResponseDTO actualizarDelitosEJG(EjgItem item, HttpServletRequest request) {
//                           LOGGER.info("actualizarDelitosEJG() -> Entrada al servicio para insertar delitos ejg");
//                           InsertResponseDTO responsedto = new InsertResponseDTO();
//                           int response = 0;
//
//                           String token = request.getHeader("Authorization");
//                           String dni = UserTokenUtils.getDniFromJWTToken(token);
//                           Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//                           if (idInstitucion != null) {
//                                           LOGGER.debug(
//                                                                           "GestionEJGServiceImpl.actualizarDelitosEJG() -> Entrada para obtener información del usuario logeado");
//
//                                           AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//                                           exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//                                           List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//                                           LOGGER.debug(
//                                                                           "GestionEJGServiceImpl.actualizarDelitosEJG() -> Salida de obtener información del usuario logeado");
//
//                                           if (usuarios != null && usuarios.size() > 0) {
//                                                           LOGGER.debug("GestionEJGServiceImpl.actualizarDelitosEJG() -> Entrada para insertar delitos ejg");
//
//                                                           try {
//                                                                           ScsDelitosejg delitos = new ScsDelitosejg();
//
//                                                                           delitos.setIdinstitucion(idInstitucion);
//                                                                           delitos.setAnio(Short.parseShort(item.getAnnio()));
//                                                                           delitos.setNumero(Long.parseLong(item.getNumero()));
//                                                                           delitos.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
//                                                                           delitos.setUsumodificacion(usuarios.get(0).getIdusuario());
//                                                                           delitos.setFechamodificacion(new Date());
//
//                                                                           ScsDelitosejgExample oldDelitos = new ScsDelitosejgExample();
//
//                                                                           oldDelitos.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//                                                                           .andAnioEqualTo(delitos.getAnio()).andNumeroEqualTo(delitos.getNumero())
//                                                                           .andIdtipoejgEqualTo(delitos.getIdtipoejg());
//
//                                                                           // Eliminamos las entradas existentes relacionadas si las hubiera.
//
//                                                                           response = scsDelitosejgMapper.deleteByExample(oldDelitos);
//
//                                                                           if (item.getDelitos()==null) response = 1;
//                                                                           else {
//                                                                                          String[] idDelitos = item.getDelitos().split(",");
//                                                                                          for (String idDelito : idDelitos) {
//                                                                                                          delitos.setIddelito(Short.parseShort(idDelito));
//
//                                                                                                          response = scsDelitosejgMapper.insert(delitos);
//                                                                                          }
//                                                                           }
//
//                                                                           LOGGER.debug(
//                                                                                                          "GestionEJGServiceImpl.actualizarDelitosEJG() -> Salida del servicio para insertar delitos ejgs");
//                                                           } catch (Exception e) {
//                                                                           LOGGER.debug(
//                                                                                                          "GestionEJGServiceImpl.actualizarDelitosEJG() -> Se ha producido un error al insertar delitos ejgs. ",
//                                                                                                          e);
//                                                           }
//                                                           // respuesta si se actualiza correctamente
//                                                           if (response >= 1) {
//                                                                           responsedto.setStatus(SigaConstants.OK);
//                                                                           LOGGER.debug("GestionEJGServiceImpl.actualizarDelitosEJG() -> OK.");
//                                                           } else {
//                                                                           responsedto.setStatus(SigaConstants.KO);
//                                                                           LOGGER.error("GestionEJGServiceImpl.actualizarDelitosEJG() -> KO.");
//                                                           }
//                                           }
//                           }
//                           return responsedto;
//            }

	@Override
	public ProcuradorDTO busquedaProcuradores(ProcuradorItem procuradorItem, HttpServletRequest request) {
		LOGGER.info("busquedaProcuradores() -> Entrada al servicio para obtener procuradores de la pantalla de buscador general");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("busquedaProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("busquedaProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info("busquedaProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores de la pantalla de buscador general");

				procuradorItemList = scsProcuradorExtendsMapper.searchProcuradores(procuradorItem, idInstitucion);

				LOGGER.info("busquedaProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores de la pantalla de buscador general");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info("busquedaProcuradores() -> Salida del servicio para obtener los procuradores de la pantalla de buscador general");
		return procuradorDTO;
	}

	private Long uploadFileEjg(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero, String extension, String idDoc) {
		LOGGER.debug("GestionEJGServiceImpl.uploadFileEjg() - INICIO");
		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFicheroEjg(idInstitucion, idDoc);
		ficheroVo.setDirectorio(directorioFichero);
		ficheroVo.setNombre(nombreFichero);
		ficheroVo.setDescripcion("Fichero asociado al EJG " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(idInstitucion);
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(extension.toLowerCase());

		ficheroVo.setUsumodificacion(idUsuario);
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		LOGGER.debug("GestionEJGServiceImpl.uploadFileEjg() - FIN");
		return ficheroVo.getIdfichero();
	}

	private String getDirectorioFicheroEjg(Short idInstitucion, String idDoc) {

		// Extraemos el path para los ficheros
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String path = genPropertiesPath.get(0).getValor();

		StringBuffer directorioFichero = new StringBuffer(path);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraemos el path concreto para los ejg
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.expedientesJG");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		// Extraemos el año y el número del ejg
		ScsDocumentacionejgKey docuKey = new ScsDocumentacionejgKey();
		docuKey.setIddocumentacion(Integer.valueOf(idDoc));
		docuKey.setIdinstitucion(idInstitucion);
		ScsDocumentacionejg miDocu = scsDocumentacionejgMapper.selectByPrimaryKey(docuKey);
		directorioFichero.append(File.separator + miDocu.getAnio() + "_");

		// Extraemos el ejg para usar su numejg
		ScsEjgKey ejgKey = new ScsEjgKey();
		ejgKey.setAnio(miDocu.getAnio());
		ejgKey.setIdinstitucion(miDocu.getIdinstitucion());
		ejgKey.setNumero(miDocu.getNumero());
		ejgKey.setIdtipoejg(miDocu.getIdtipoejg());
		ScsEjg miEjg = scsEjgExtendsMapper.selectByPrimaryKey(ejgKey);
		directorioFichero.append(miEjg.getNumejg());

		return directorioFichero.toString();
	}

	private InputStream getZipFileDocumentosEjg(List<EjgDocumentacionItem> listadocumentoEjgItem, Short idInstitucion) throws Exception {

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (EjgDocumentacionItem doc : listadocumentoEjgItem) {
				// path += File.separator + idInstitucion + "_" + doc.getIdFichero() + extension;

				GenFicheroExample genFicheroExampleP = new GenFicheroExample();
				if (doc.getIdFichero() == null) {
					throw new SigaExceptions("Error al descarga zip, el IdFichero es null para el documento" + doc.getNombreFichero());
				}
				genFicheroExampleP.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdficheroEqualTo(Long.valueOf(doc.getIdFichero()));
				List<GenFichero> genFichero = genFicheroMapper.selectByExample(genFicheroExampleP);

				if (doc.getNombreFichero() != null) {
					zipOutputStream.putNextEntry(new ZipEntry(doc.getIdFichero() + "_" + doc.getNombreFichero()));
				} else {
					zipOutputStream.putNextEntry(new ZipEntry(doc.getIdFichero() + "." + genFichero.get(0).getExtension()));
				}

				String path = genFichero.get(0).getDirectorio();
				String extension = genFichero.get(0).getExtension();
				LOGGER.warn("AGUERRA - EXTENSION: " + extension);

				path += File.separator + idInstitucion + "_" + genFichero.get(0).getIdfichero();

				path += "." + extension;

				LOGGER.warn("AGUERRA - TIPOMIME: " + extension);
				// String mimeType = getMimeType("."+extension);
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
			LOGGER.error("getZipFileDocumentosEjg -> Error al generar el zip de los documentos. " + e.getMessage());
			throw (new Exception("Error al generar el ZIP con la documentación en el EJG, contiene " + listadocumentoEjgItem.size() + " documentos.", e));
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	private String getMimeType(String extension) {

		String mime = "";

		switch (extension.toLowerCase()) {

		case ".gif":
			mime = "image/gif";
			break;
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
		case ".zip":
			mime = "application/zip";
			break;
		case ".txt":
			mime = "text/plain";
			break;
		case ".csv":
			mime = "text/csv";
			break;
		case ".xls":
			mime = "application/vnd.ms-excel";
			break;
		case ".xlsx":
			mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			break;
		}

		return mime;
	}

	private boolean extensionValida(String extension) {
		for (String extensionCorrecta : SigaConstants.formatosPermitidosSubidaDocumentacionDesignas) {
			if (extensionCorrecta.equals(extension)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public InsertResponseDTO crearDocumentacionEjg(EjgDocumentacionItem documentacionEjgItem, HttpServletRequest request) throws Exception {
		boolean extensionErronea = false;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		// Se comentan el try y el catch para que @Transactional funcione adecuadamente
//        try {

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("GestionEJGServiceImpl.crearDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		LOGGER.info("GestionEJGServiceImpl.crearDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (usuarios != null && !usuarios.isEmpty()) {
			String[] extension = null;

			if (documentacionEjgItem.getNombreFichero() != null)
				extension = documentacionEjgItem.getNombreFichero().split("\\.");

			if (extension != null && !extensionValida(extension[1])) {
				extensionErronea = true;
			} else {
				MaxIdDto nuevoId = scsEjgExtendsMapper.getNewIdDocumentacionEjg(idInstitucion);

				ScsDocumentacionejg scsDocumentacionejg = new ScsDocumentacionejg();

				scsDocumentacionejg.setAnio(Short.valueOf(documentacionEjgItem.getAnio()));
				scsDocumentacionejg.setNumero(Long.valueOf(documentacionEjgItem.getNumero()));
				scsDocumentacionejg.setIdtipoejg(Short.valueOf(documentacionEjgItem.getIdTipoEjg()));

				scsDocumentacionejg.setIddocumentacion(Integer.valueOf(nuevoId.getIdMax().toString()));
				scsDocumentacionejg.setIdtipodocumento(Short.valueOf(documentacionEjgItem.getIdTipoDocumento()));

				scsDocumentacionejg.setFechalimite(documentacionEjgItem.getFlimite_presentacion());
				scsDocumentacionejg.setFechaentrega(documentacionEjgItem.getF_presentacion());
				scsDocumentacionejg.setRegentrada(documentacionEjgItem.getRegEntrada());
				scsDocumentacionejg.setRegsalida(documentacionEjgItem.getRegSalida());

				// Si trae fichero
				if (documentacionEjgItem.getNombreFichero() != null && !documentacionEjgItem.getNombreFichero().isEmpty()) {
					scsDocumentacionejg.setNombrefichero(documentacionEjgItem.getNombreFichero());
				}

				// Posible fuente de problemas por restricciones de la base de datos
				// Se lee el valor de presentador enviado desde el front.
				// Se ha implementado para que si empieza con "S_" (añadido unicamente en el
				// combo de presentador)
				// se trata de un solicitante de la unidad (idpersona), en caso contrario, es
				// uno de los presentadores de EJGS
				if (documentacionEjgItem.getPresentador().contains("S_")) {
					scsDocumentacionejg.setPresentador(Long.valueOf(documentacionEjgItem.getPresentador().split("S_")[1]));
				} else
					scsDocumentacionejg.setIdmaestropresentador(Short.valueOf(documentacionEjgItem.getPresentador()));
				scsDocumentacionejg.setDocumentacion(documentacionEjgItem.getDescripcionDoc());

				// Actualmente se asigna la nueva documentacion a "CAJG" o a "ICA" según el
				// perfil que lo cree.
				// La logica que se utiliza es que si es un perfil "'CJG'" (Comision de Justicia
				// Gratuita) se considera de comision, el resto no.
				if (UserTokenUtils.getPerfilesFromJWTToken(token).get(0).equals("'CJG'"))
					scsDocumentacionejg.setComisionajg((short) 1);
				else
					scsDocumentacionejg.setComisionajg((short) 0);

				scsDocumentacionejg.setIdinstitucion(idInstitucion);
				scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				scsDocumentacionejg.setFechamodificacion(new Date());

				if (documentacionEjgItem.getIdDocumento() == -1) {

					List<ComboItem> comboItems = scsDocumentoejgExtendsMapper.comboDocumentos(usuarios.get(0).getIdlenguaje(), idInstitucion, String.valueOf(documentacionEjgItem.getIdTipoDocumento()));
					for (ComboItem item : comboItems) {
						scsDocumentacionejg.setIddocumento(Short.valueOf(item.getValue()));
						response = scsDocumentacionejgMapper.insert(scsDocumentacionejg);
						if (response == 1) {
							nuevoId = scsEjgExtendsMapper.getNewIdDocumentacionEjg(idInstitucion);
							scsDocumentacionejg.setIddocumentacion(Integer.valueOf(nuevoId.getIdMax().toString()));
						}
						if (response == 0) {
							throw (new Exception("Error al introducir la nueva documentación en el EJG"));
						}
					}
				} else {
					scsDocumentacionejg.setIddocumento(Short.valueOf(documentacionEjgItem.getIdDocumento()));
					response = scsDocumentacionejgMapper.insert(scsDocumentacionejg);
					if (response == 0)
						throw (new Exception("Error al introducir la nueva documentación en el EJG"));
				}
			}
		}

//        } catch (Exception e) {
//            LOGGER.error(
//                    "GestionEJGServiceImpl.crearDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg",
//                    e);
//            error.setCode(500);
//            error.setDescription("general.mensaje.error.bbdd");
//            error.setMessage(e.getMessage());
//            insertResponseDTO.setError(error);
//            insertResponseDTO.setStatus(SigaConstants.KO);
//            response = 0;
//        }

		if (response == 1) {
			insertResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			insertResponseDTO.setError(error);
		}

		if (response == 0 && !extensionErronea) {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("GestionEJGServiceImpl.crearDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setError(error);
		}
		if (extensionErronea) {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("GestionEJGServiceImpl.crearDocumentacionEjg() -> Formato incorrecto");
			error.setCode(500);
			error.setDescription("justiciaGratuita.oficio.designa.formatoDocumentacionNoValido");
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO actualizarDocumentacionEjg(EjgDocumentacionItem documentacionEjgItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info("GestionEJGServiceImpl.actualizarDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionEJGServiceImpl.actualizarDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				ScsDocumentacionejg scsDocumentacionejg = new ScsDocumentacionejg();

				scsDocumentacionejg.setIddocumentacion(documentacionEjgItem.getIdDocumentacion().intValue());
				scsDocumentacionejg.setIdtipodocumento(Short.valueOf(documentacionEjgItem.getIdTipoDocumento()));
				scsDocumentacionejg.setIdinstitucion(idInstitucion);

				scsDocumentacionejg.setFechalimite(documentacionEjgItem.getFlimite_presentacion());
				scsDocumentacionejg.setFechaentrega(documentacionEjgItem.getF_presentacion());
				scsDocumentacionejg.setRegentrada(documentacionEjgItem.getRegEntrada());
				scsDocumentacionejg.setRegsalida(documentacionEjgItem.getRegSalida());
				scsDocumentacionejg.setDocumentacion(documentacionEjgItem.getDescripcionDoc());
				scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				scsDocumentacionejg.setFechamodificacion(new Date());

				response = scsDocumentacionejgMapper.updateByPrimaryKeySelective(scsDocumentacionejg);

			}

		} catch (Exception e) {
			LOGGER.error("GestionEJGServiceImpl.actualizarDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			updateResponseDTO.setError(error);
			response = 0;
		}

		if (response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			// error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setError(error);
		}

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("GestionEJGServiceImpl.actualizarDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO eliminarDocumentacionEjg(List<EjgDocumentacionItem> listadocumentoEjgItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		int response = 1, response2 = 1;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info("GestionEJGServiceImpl.eliminarDocumentacionDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionEJGServiceImpl.eliminarDocumentacionDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				for (EjgDocumentacionItem doc : listadocumentoEjgItem) {

					// Eliminacion de la entrada en la tabla
					ScsDocumentacionejgKey scsDocumentacionejgKey = new ScsDocumentacionejgKey();

					scsDocumentacionejgKey.setIddocumentacion(doc.getIdDocumentacion().intValue());
					scsDocumentacionejgKey.setIdinstitucion(idInstitucion);

					if (response2 == 1)
						response = scsDocumentacionejgMapper.deleteByPrimaryKey(scsDocumentacionejgKey);
					else
						response = 0;

					// Eliminacion fisica del fichero asociado
					if (doc.getIdFichero() != null) {

						GenFicheroKey fichKey = new GenFicheroKey();
						fichKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
						fichKey.setIdinstitucion(idInstitucion);
						GenFichero miFichero = genFicheroMapper.selectByPrimaryKey(fichKey);
						String path = miFichero.getDirectorio();

						path += File.separator + idInstitucion + "_" + doc.getIdFichero() + "." + miFichero.getExtension();

						File file = new File(path);

						if (file.exists()) {
							file.delete();
						}

						// Eliminacion de su registro en la BBDD
						GenFicheroKey genFicheroKey = new GenFicheroKey();

						genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
						genFicheroKey.setIdinstitucion(idInstitucion);

						// Gestion de respuesta para que no se siga ejecutando cuando hay errores
						if (response == 1)
							response2 = genFicheroMapper.deleteByPrimaryKey(genFicheroKey);
						else
							response2 = 0;
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error("GestionEJGServiceImpl.eliminarDocumentacionDesigna() -> Se ha producido un error en la eliminación dedocumentacion de ejg", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			response = 0;
			response2 = 0;
		}

		if (response2 == 1 && response == 1) {
			deleteResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			deleteResponseDTO.setError(error);
		} else {
			deleteResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("GestionEJGServiceImpl.eliminarDocumentacionDesigna() -> Se ha producido un error en la eliminación de documentacion de ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO eliminarDocumentosEjg(EjgDocumentacionItem doc, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		int response = 0, response2 = 0;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info("GestionEJGServiceImpl.eliminarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("GestionEJGServiceImpl.eliminarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				// Seleccionamos la documentacion del fichero
				ScsDocumentacionejgKey scsDocumentacionejgKey = new ScsDocumentacionejgKey();

				scsDocumentacionejgKey.setIddocumentacion(doc.getIdDocumentacion().intValue());
				scsDocumentacionejgKey.setIdinstitucion(idInstitucion);

				ScsDocumentacionejg scsDocumentacionejg = scsDocumentacionejgMapper.selectByPrimaryKey(scsDocumentacionejgKey);

				// Modificamos la entrada en la tabla
				scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				scsDocumentacionejg.setFechamodificacion(new Date());
				scsDocumentacionejg.setIdfichero(null);
				scsDocumentacionejg.setNombrefichero(null);

				response = scsDocumentacionejgMapper.updateByPrimaryKey(scsDocumentacionejg);

				// Eliminacion fisica del fichero asociado
				GenFicheroKey ficheroKey = new GenFichero();
				ficheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
				ficheroKey.setIdinstitucion(idInstitucion);
				GenFichero miFichero = genFicheroMapper.selectByPrimaryKey(ficheroKey);
				String path = miFichero.getDirectorio();

				path += File.separator + idInstitucion + "_" + doc.getIdFichero() + "." + miFichero.getExtension();

				File file = new File(path);

				if (file.exists()) {
					file.delete();
				}

				// Eliminacion de su registro en la BBDD
				GenFicheroKey genFicheroKey = new GenFicheroKey();

				genFicheroKey.setIdfichero(Long.valueOf(doc.getIdFichero()));
				genFicheroKey.setIdinstitucion(idInstitucion);

				if (response == 1)
					response2 = genFicheroMapper.deleteByPrimaryKey(genFicheroKey);

			}

		} catch (Exception e) {
			LOGGER.error("GestionEJGServiceImpl.eliminarDocumentosnDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la documentacion de ejg", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
			response = 0;
		}

		if (response2 == 1 && response == 1) {
			deleteResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			deleteResponseDTO.setError(error);
		} else {
			deleteResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("GestionEJGServiceImpl.eliminarDocumentosDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la documentacion de ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO subirDocumentoEjg(MultipartHttpServletRequest request) {
		LOGGER.debug("GestionEJGServiceImpl.subirDocumentoEjg() - INICIO");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 1;

		// SIGARNV-3078@DTT.JAMARTIN@12/01/2023@INICIO
		try {
			response = subirAdjuntoEJG(dni, idInstitucion, request);
		} catch (Exception e) {
			LOGGER.error("ERROR: GestionEJGServiceImpl.subirDocumentoEjg() -> Se ha producido un error al subir un fichero perteneciente a la designación", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
			response = 0;
		}

		if (response == 1) {
			insertResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			// error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setError(error);
			LOGGER.debug("GestionEJGServiceImpl.subirDocumentoDesigna() -> Se ha subido correctamente el fichero al ejg");
		}

		if (response == 0) {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("ERROR: GestionEJGServiceImpl.subirDocumentoDesigna() -> Se ha producido un error al subir un fichero perteneciente al ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setError(error);
		}

		LOGGER.debug("GestionEJGServiceImpl.subirDocumentoEjg() - INICIO");
		return insertResponseDTO;
	}

	@Transactional
	private int subirAdjuntoEJG(String dni, Short idInstitucion, MultipartHttpServletRequest request) throws IOException {
		LOGGER.debug("GestionEJGServiceImpl.subirAdjuntoEJG() - INICIO");

		int response = 1;

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

		LOGGER.info("Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info("Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (usuarios != null && !usuarios.isEmpty()) {

			Iterator<String> itr = request.getFileNames();

			MultipartFile file = request.getFile(itr.next());
			String nombreFichero = file.getOriginalFilename().split(";")[0];
			String extension = FilenameUtils.getExtension(nombreFichero);

			String idDocumentacion = request.getParameter("idPersona");

			// Actualizamos la documentacion modificada
			ScsDocumentacionejg scsDocumentacionejg = new ScsDocumentacionejg();

			scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
			scsDocumentacionejg.setFechamodificacion(new Date());
			scsDocumentacionejg.setIdinstitucion(idInstitucion);
			scsDocumentacionejg.setNombrefichero(nombreFichero);

			// En el caso que se haya seleccionado "Todos" en el desplegable "Documento",
			// se pasan varios idDocumentacion y se introduce el fichero en cada uno de
			// ellos
			if (idDocumentacion.contains(",")) {
				String[] documentaciones = idDocumentacion.split(",");
				for (String idDoc : documentaciones) {
					Long idFile = uploadFileEjg(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion, nombreFichero, extension, idDoc);

					scsDocumentacionejg.setIdfichero(idFile);
					scsDocumentacionejg.setIddocumentacion(Integer.valueOf(idDoc));

					response = scsDocumentacionejgMapper.updateByPrimaryKeySelective(scsDocumentacionejg);
				}
			} else {
				LOGGER.info("****************" + file + "*****************************************************************");
				Long idFile = uploadFileEjg(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion, nombreFichero, extension, idDocumentacion);

				scsDocumentacionejg.setIdfichero(idFile);
				scsDocumentacionejg.setIddocumentacion(Integer.valueOf(idDocumentacion));

				response = scsDocumentacionejgMapper.updateByPrimaryKeySelective(scsDocumentacionejg);
			}
		}

		LOGGER.debug("GestionEJGServiceImpl.subirAdjuntoEJG() - FIN");
		return response;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumentosEjg(List<EjgDocumentacionItem> listadocumentoEjgItem, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;
		InputStream fileStream = null;
		HttpHeaders headers = new HttpHeaders();

		LOGGER.warn("AGUERRA - COMIENZA EL SERVICIO");
		LOGGER.warn("AGUERRA - PRIMER DOCUMENTO: " + listadocumentoEjgItem.get(0).getNombreFichero());
		try {

			LOGGER.info("GestionEJGServiceImpl.descargarDocumentosEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("GestionEJGServiceImpl.descargarDocumentosEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !listadocumentoEjgItem.isEmpty()) {
				if (listadocumentoEjgItem.size() == 1) {

					GenFicheroExample genFicheroExampleP = new GenFicheroExample();
					if (listadocumentoEjgItem.get(0).getIdFichero() == null) {
						throw new SigaExceptions("Error descarga documento el IdFichero es null para el documento" + listadocumentoEjgItem.get(0).getNombreFichero());
					}
					genFicheroExampleP.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdficheroEqualTo(Long.valueOf(listadocumentoEjgItem.get(0).getIdFichero()));
					List<GenFichero> genFichero = genFicheroMapper.selectByExample(genFicheroExampleP);
					String path = genFichero.get(0).getDirectorio();

					String extension = genFichero.get(0).getExtension();

					LOGGER.warn("AGUERRA - EXTENSION: " + extension);

					path += File.separator + idInstitucion + "_" + genFichero.get(0).getIdfichero();

					path += "." + extension;
					LOGGER.warn("AGUERRA - RUTA: " + path);
					LOGGER.debug("descargarDocumentosEjg: RUTA FICHERO A DESCARGAR: " + path);

					File file = new File(path);
					fileStream = new FileInputStream(file);

					// String tipoMime = getMimeType(extension);
					LOGGER.warn("AGUERRA - TIPOMIME: " + extension);
					String mimeType = getMimeType("." + extension);
					if (mimeType == "") {
						LOGGER.warn("AGUERRA - ENTRA EN LA EXCEPCION DE LA EXTENSION NO SOPORTADA");
						throw new Exception("Error: el documento contiene una extensión no soportada");
					} else {
						headers.setContentType(MediaType.parseMediaType(mimeType));
						headers.set("Content-Disposition", "attachment; filename=\"" + listadocumentoEjgItem.get(0).getNombreFichero() + "\"");
						headers.setContentLength(file.length());
					}
				} else {
					fileStream = getZipFileDocumentosEjg(listadocumentoEjgItem, idInstitucion);

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"documentosEJG.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
			}

//		} catch (Exception e) {
//			LOGGER.warn("AGUERRA - HA OCURRIDO UN ERROR EN EL PROCESO");
//			LOGGER.error("GestionEJGServiceImpl.descargarDocumentosEjg() -> Se ha producido un error al descargar archivos asociados al ejg", e);
//			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.INTERNAL_SERVER_ERROR);
//		} finally {
//			if(fileStream != null) {
//				try {
//					fileStream.close();
//				} catch (IOException e) {
//					LOGGER.error("GestionEJGServiceImpl.descargarDocumentosEjg() --> se ha producido un error al generar el fichero", e);
//				}
//			}
//        }
		} catch (Exception e) {
			LOGGER.warn("AGUERRA - HA OCURRIDO UN ERROR EN EL PROCESO");
			LOGGER.error(
					"GestionEJGServiceImpl.descargarDocumentosEjg() -> Se ha producido un error al descargar archivos asociados al ejg",
					e);
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
					HttpStatus.INTERNAL_SERVER_ERROR);
			try {
				fileStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		LOGGER.warn("AGUERRA - SALE DEL SERVICIO");

		return res;
	}

	@Override
	@Transactional
	public InsertResponseDTO solicitarEEJG(UnidadFamiliarEJGItem datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		long idComCola;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para insertar en la unidad familiar del ejg");

				CenInstitucion institucion = cenInstitucionExtendsMapper.selectByPrimaryKey(idInstitucion);

				// insert de la peticion de EEJG.
				ScsEejgPeticiones eejgPeticion = new ScsEejgPeticiones();
				eejgPeticion.setIdinstitucion(idInstitucion);
				eejgPeticion.setIdtipoejg(Short.parseShort(datos.getUf_idTipoejg()));
				eejgPeticion.setAnio(Short.parseShort(datos.getUf_anio()));
				eejgPeticion.setNumero(Long.parseLong(datos.getUf_numero()));
				eejgPeticion.setEstado(10L);
				eejgPeticion.setNif(datos.getPjg_nif());
				eejgPeticion.setNombre(datos.getPjg_nombre());
				eejgPeticion.setApellido1(datos.getPjg_ape1());
				eejgPeticion.setApellido2(datos.getPjg_ape2());
				eejgPeticion.setIdusuariopeticion(BigDecimal.valueOf(usuarios.get(0).getIdusuario()));
				eejgPeticion.setIdpersona(Long.parseLong(datos.getUf_idPersona()));
				eejgPeticion.setFechasolicitud(new Date());
				eejgPeticion.setFechapeticion(new Date());
				eejgPeticion.setFechamodificacion(new Date());
				eejgPeticion.setUsumodificacion(0);
				eejgPeticion.setNumerointentosconsulta(Short.parseShort("0"));
				eejgPeticion.setNumerointentospendienteinfo(Short.parseShort("0"));
				eejgPeticion.setNumerointentossolicitud(Short.parseShort("0"));
				String lenguaje = "es_ES";
				if (institucion.getIdlenguaje().equals("2")) {
					lenguaje = "ca_ES";
				} else if (institucion.getIdlenguaje().equals("3")) {
					lenguaje = "eu_ES";
				} else if (institucion.getIdlenguaje().equals("4")) {
					lenguaje = "gl_ES";
				}
				eejgPeticion.setIdioma(lenguaje);
				eejgPeticion.setIdpeticion(scsEejgPeticionesExtendsMapper.getMaxIdpeticion());

				scsEejgPeticionesExtendsMapper.insertSelective(eejgPeticion);

				// insercion de nuevo registro a la tabla EcomCola una vez se realiza la
				// peticion de EEJG.
				EcomCola ecomCola = new EcomCola();

				ecomCola.setIdestadocola(Short.parseShort("1"));
				ecomCola.setIdoperacion(81);
				ecomCola.setReintento(0);
				ecomCola.setFechacreacion(new Date());
				ecomCola.setFechamodificacion(new Date());
				ecomCola.setUsumodificacion(usuarios.get(0).getIdusuario());

				ecomCola.setIdinstitucion(idInstitucion);

				ecomColaMapper.insertSelective(ecomCola);

				// obtener el ultimo idcomcola de la tabla ECOM_COLA.
				idComCola = scsEjgExtendsMapper.getIdEcomCola();

				// primer insert a la tabla ECOM_COLA_PARAMETROS con los datos idComCola, clave
				// y valor para saber la institucion que hacer el insert.
				EcomColaParametros ecmp = new EcomColaParametros();
				ecmp.setIdecomcola(idComCola);
				ecmp.setClave("IDINSTITUCION");
				ecmp.setValor(idInstitucion.toString());

				ecomColaParametrosMapper.insert(ecmp);

				// segundo insert a la tabla ECOM_COLA_PARAMETROS con los datos idComCola, clave
				// y valor para saber a que peticion pertenece.
				EcomColaParametros ecmp2 = new EcomColaParametros();
				ecmp2.setIdecomcola(idComCola);
				ecmp2.setClave("IDPETICION");
				ecmp2.setValor(scsEejgPeticionesExtendsMapper.getUltimoIdPeticion());

				ecomColaParametrosMapper.insert(ecmp2);

				insertResponseDTO.setStatus(SigaConstants.OK);

				LOGGER.debug("GestionEJGServiceImpl.insertFamiliarEJG() -> Salida del servicio para insertar una solicitud de EEJG");
			}
		}
		return insertResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO asociarDesignacion(List<String> datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		int response = 1;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.asociarDesignacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.asociarDesignacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.asociarDesignacion() -> Entrada para asociar un EJG a una designacion");
				try {

					ScsEjgdesigna record = new ScsEjgdesigna();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setIdinstitucion(idInstitucion);

					record.setAniodesigna(Short.parseShort(datos.get(1)));
					record.setNumerodesigna(Long.parseLong(datos.get(2)));

					record.setAnioejg(Short.parseShort(datos.get(5)));
					record.setIdtipoejg(Short.parseShort(datos.get(4)));
					record.setNumeroejg(Long.parseLong(datos.get(6)));

					TurnosItem turnosItem = new TurnosItem();
					String turnoDesc = datos.get(7).substring(0, datos.get(7).length() - 1);
					turnosItem.setAbreviatura(turnoDesc);
					turnosItem.setHistorico(true);
					List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion, usuarios.get(0).getIdlenguaje());
					record.setIdturno(Integer.parseInt(turnos.get(0).getIdturno()));

					response = scsEjgdesignaMapper.insert(record);
					
					asociarAsistenciAlDesignaEJG(record, usuarios.get(0), request);
					

				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.asociarDesignacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.asociarDesignacion() -> KO. No se ha asociado ningun elemento");

					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.info("GestionEJGServiceImpl.asociarDesignacion() -> OK. Se ha asociado el elemento correctamente");
					}
				}
			}
		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO asociarAsistencia(List<String> datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		int response = 1;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.asociarAsistencia() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.asociarAsistencia() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.asociarAsistencia() -> Entrada para asociar un EJG a una asistencia");

				try {

					ScsAsistencia record = new ScsAsistencia();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());

					record.setIdinstitucion(idInstitucion);
					record.setEjganio(Short.parseShort(datos.get(4)));
					record.setEjgidtipoejg(Short.parseShort(datos.get(3)));
					record.setEjgnumero(Long.parseLong(datos.get(5)));

					record.setNumero(Long.parseLong(datos.get(2)));
					record.setAnio(Short.parseShort(datos.get(1)));
					
					ScsAsistenciaExample asisExample = new ScsAsistenciaExample();
					asisExample.createCriteria()
						.andNumeroEqualTo(record.getNumero())
						.andAnioEqualTo(record.getAnio())
						.andIdinstitucionEqualTo(idInstitucion);
					
					
					EjgItem item = new EjgItem();
					item.setidInstitucion(idInstitucion.toString());
					item.setNumero(datos.get(5));
					item.setTipoEJG(datos.get(3));
					item.setAnnio(datos.get(4));
					
					//obtenemos los datos completos para asignar a la asistencia el Designa del EJG
					List<ScsAsistencia> asis = scsAsistenciaExtendsMapper.selectByExample(asisExample);
					List<EjgItem>ejgDesigna = scsEjgExtendsMapper.getEjgDesignas(item);
					// insertamos los datos del Designa solo si la asistencia no tiene datos de Designa
					if (!ejgDesigna.isEmpty() && !asis.isEmpty() && asis.get(0).getDesignaNumero() == null ) {
						record.setDesignaAnio(ejgDesigna.get(0).getAnioDesigna());
						record.setDesignaNumero(Long.valueOf(ejgDesigna.get(0).getNumeroDesigna()));
						record.setDesignaTurno(Integer.valueOf(ejgDesigna.get(0).getTurnoDes()));
					}
					

					response = scsAsistenciaMapper.updateByPrimaryKeySelective(record);

				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.asociarAsistencia() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.asociarAsistencia() -> KO. No se ha asociado ningun elemento");

					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
				}

			}
		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO asociarSOJ(List<String> datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		int response = 1;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Entrada para asociar un EJG a un SOJ");
				try {

					ScsSojExample example = new ScsSojExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtiposojEqualTo(Short.parseShort(datos.get(3))).andAnioEqualTo(Short.parseShort(datos.get(1))).andNumsojEqualTo(datos.get(2));

					ScsSoj record = new ScsSoj();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					// record.setIdinstitucion(idInstitucion);
					// record.setNumsoj(datos.get(2));
					// record.setAnio(Short.parseShort(datos.get(1)));
					// record.setIdtiposoj(Short.parseShort(datos.get(3)));

					record.setEjganio(Short.parseShort(datos.get(5)));
					record.setEjgidtipoejg(Short.parseShort(datos.get(4)));
					record.setEjgnumero(Long.parseLong(datos.get(6)));

					response = scsSojMapper.updateByExampleSelective(record, example);

				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ", e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error("GestionEJGServiceImpl.asociarSOJ() -> KO. No se ha asociado ningun elemento");

					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
				}
			}
		}

		return updateResponseDTO;
	}

	@Transactional
	@Override
	public UpdateResponseDTO actualizarInformeCalificacionEjg(EjgItem ejgItem, HttpServletRequest request) throws Exception {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		// Comentamos el try y el catch para que @Transactional funcione correctamente
//                           try {

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info("GestionEJGServiceImpl.actualizarInformeCalificacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		LOGGER.info("GestionEJGServiceImpl.actualizarInformeCalificacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (usuarios != null && !usuarios.isEmpty()) {

			// 1. Actualizar informacion del EJG asociado
			ScsEjgKey ejgKey = new ScsEjgKey();

			// Introducimos la clave primaria y seleccionamos el ejg correspondiente

			ejgKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
			ejgKey.setIdinstitucion(idInstitucion);
			ejgKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
			ejgKey.setNumero(Long.valueOf(ejgItem.getNumero()));

			ScsEjgWithBLOBs newEjg = scsEjgMapper.selectByPrimaryKey(ejgKey);

			// Modificamos la informacion vinculada a la tarjeta de dictamen de la ficha
			// EJG,
			// excepto la fecha de dictamen que se actualiza más adelante

			newEjg.setIdtipodictamenejg(ejgItem.getIdTipoDictamen());
			newEjg.setIdfundamentocalif(ejgItem.getFundamentoCalif());
			newEjg.setDictamen(ejgItem.getDictamen());

			newEjg.setFechamodificacion(new Date());
			newEjg.setUsumodificacion(usuarios.get(0).getIdusuario());

			// Ahora se actualiza la fecha de dictamen
			newEjg.setFechadictamen(ejgItem.getFechaDictamen());

			// 2. Objeto Dictamen asociado al EJG

			// si todos los campos son nulos, se elimina el dictamen asociado
			if (ejgItem.getFechaDictamen() == null && ejgItem.getFechaDictamen() == null && ejgItem.getFechaDictamen() == null && ejgItem.getFechaDictamen() == null && ejgItem.getFechaDictamen() == null && ejgItem.getIddictamen() != null) {

				// Actualizamos el EJG asociado para poder eliminar el dictamen.
				newEjg.setIddictamen(null);

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				GenParametrosExample exampleParam = new GenParametrosExample();
				exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS).andParametroEqualTo("ENABLETRIGGERSEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				exampleParam.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);

				if (parametrosTrigger != null && !parametrosTrigger.isEmpty() && parametrosTrigger.get(0).getValor().equals("1")) {
					this.triggersEjgUpdatesDictamen(ejgItem, usuarios.get(0), idInstitucion);
				}

				response = scsEjgMapper.updateByPrimaryKeyWithBLOBs(newEjg);
				if (response == 0)
					throw (new Exception("Error al actualizar el EJG"));

				ScsDictamenejgKey dictamenDelete = new ScsDictamenejgKey();

				dictamenDelete.setIddictamen(ejgItem.getIddictamen());
				dictamenDelete.setIdinstitucion(idInstitucion);

				response = scsDictamenejgMapper.deleteByPrimaryKey(dictamenDelete);
				if (response == 0)
					throw (new Exception("Error al eliminar el dictamen asociado al EJG"));
			} else {
				// Creamos un nuevo dictamen con la informacion introducida
				ScsDictamenejg dictamenEjg = new ScsDictamenejg();

				dictamenEjg.setDescripcion(ejgItem.getDictamen());
				dictamenEjg.setIdtipodictamen(ejgItem.getIdTipoDictamen());
				dictamenEjg.setIdfundamento(ejgItem.getFundamentoCalif());
				dictamenEjg.setIdinstitucion(idInstitucion);

				dictamenEjg.setFechamodificacion(new Date());
				dictamenEjg.setUsumodificacion(usuarios.get(0).getIdusuario());

				// En el caso que se inserte un dictamen nuevo
				if (ejgItem.getIddictamen() == null) {
					ScsDictamenejgExample dictamenExample = new ScsDictamenejgExample();
					dictamenExample.setOrderByClause("IDDICTAMEN DESC");
					dictamenExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

					List<ScsDictamenejg> listDictCola = scsDictamenejgMapper.selectByExample(dictamenExample);

					// Buscamos el iddictamen qu corresponde para asignarlo al nuevo dictamen y al
					// ejg.
					short idDictCola;
					if (!listDictCola.isEmpty()) {
						idDictCola = (short) (listDictCola.get(0).getIddictamen() + (short) 1);
					} else {
						idDictCola = 1;
					}
					dictamenEjg.setIddictamen(idDictCola);

					response = scsDictamenejgMapper.insertSelective(dictamenEjg);
					if (response == 0)
						throw (new Exception("Error al insertar el nuevo dictamen asociado al EJG"));

					// Actualizamos el EJG asociado
					newEjg.setIddictamen(idDictCola);
				}
				// Actualizamos dictamen ya existente
				else {

					dictamenEjg.setIddictamen(ejgItem.getIddictamen());
					response = scsDictamenejgMapper.updateByPrimaryKeySelective(dictamenEjg);
					if (response == 0)
						throw (new Exception("Error al actualizar el dictamen asociado al EJG"));

					// Actualizamos el EJG asociado
					newEjg.setIddictamen(ejgItem.getIddictamen());
				}

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG cuando una fila es actualizada.
				GenParametrosExample exampleParam = new GenParametrosExample();
				exampleParam.createCriteria().andModuloEqualTo(SigaConstants.MODULO_SCS).andParametroEqualTo("ENABLETRIGGERSEJG").andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				exampleParam.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> parametrosTrigger = genParametrosMapper.selectByExample(exampleParam);

				if (parametrosTrigger != null && !parametrosTrigger.isEmpty() && parametrosTrigger.get(0).getValor().equals("1")) {
					this.triggersEjgUpdatesDictamen(ejgItem, usuarios.get(0), idInstitucion);
				}

				response = scsEjgMapper.updateByPrimaryKeyWithBLOBs(newEjg);
				if (response == 0)
					throw (new Exception("Error al actualizar el EJG con informacion de dictamen"));

			}

		}

//                           } catch (Exception e) {
//                                           LOGGER.error(
//                                                                           "GestionEJGServiceImpl.actualizarInformeCalificacionEjg() -> Se ha producido un error al actualizar el dictamen del ejg",
//                                                                           e);
//                                           error.setCode(500);
//                                           error.setDescription("general.mensaje.error.bbdd");
//                                           error.setMessage(e.getMessage());
//                                           updateResponseDTO.setError(error);
//                                           response = 0;
//                           }

		if (response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			// error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setError(error);
		}

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error("GestionEJGServiceImpl.actualizarInformeCalificacionEjg() -> Se ha producido un error al actualizar el dictamen del ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	public DocushareDTO searchListDocEjg(EjgItem ejgItem, HttpServletRequest request) throws Exception {

		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgKey key = new ScsEjgKey();

		key.setIdinstitucion(idInstitucion);
		key.setAnio(Short.valueOf(ejgItem.getAnnio()));
		key.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		key.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(key);

		if (ejg.getIdentificadords() == null) {

			// Año EJG/Num EJG. formato de DocuShare.
			String title = ejg.getAnio() + "/" + ejg.getNumejg();

			LOGGER.debug("ValorEjgDocu : " + title);
			identificadorDS = docushareHelper.buscaCollectionEjg(title, idInstitucion);
			if (null != identificadorDS) {
				ejg.setIdentificadords(identificadorDS);
				scsEjgMapper.updateByPrimaryKey(ejg);
			}
		} else {
			LOGGER.debug("IdentificadorDS : " + ejg.getIdentificadords());
			identificadorDS = ejg.getIdentificadords();
		}
		LOGGER.debug("IdentificadorDS : " + identificadorDS);
		// EJG - ALICANTE
		// identificadorDS = "Collection-691";
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, identificadorDS);
			docushareDTO.setDocuShareObjectVO(docus);
			docushareDTO.setIdentificadorDS(identificadorDS);
		}
		return docushareDTO;
	}

	@Override
	public String insertCollectionEjg(EjgItem ejgItem, HttpServletRequest request) throws Exception {

		LOGGER.info("insertCollectionEjg() -> Entrada al servicio para la insertar una nueva colección");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idDS = null;

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("insertCollectionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("insertCollectionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				ScsEjgKey key = new ScsEjgKey();

				key.setIdinstitucion(idInstitucion);
				key.setAnio(Short.valueOf(ejgItem.getAnnio()));
				key.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
				key.setNumero(Long.valueOf(ejgItem.getNumero()));

				ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(key);

				// Año EJG/Num EJG. Se realiza el proceso anterior para no utilizar numEjg ya
				// que no es una clave unica
				// y mantener el formato de DocuShare.
				String title = ejgItem.getAnnio() + "/" + ejg.getNumejg();

				idDS = docushareHelper.createCollectionEjg(idInstitucion, title, "");

				if (idDS == null || idDS.isEmpty()) {
					throw (new Exception("Error al crear la colección en Regtel para el EJG"));
				}

				LOGGER.info("insertCollectionEjg() / docushareHelper.createCollectionEjg() -> Valor de idDS obtenido: " + idDS);

				ejg.setIdentificadords(idDS);
				ejg.setFechamodificacion(new Date());
				ejg.setUsumodificacion(usuarios.get(0).getIdusuario());

				LOGGER.info("insertCollectionEjg() / scsEjgMapper.updateByPrimaryKey() -> Entrada a scsEjgMapper para modificar el identificador para DocuShare del EJG");

				int response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar el identificador para DocuShare del EJG"));

				LOGGER.info("insertCollectionEjg() / scsEjgMapper.updateByPrimaryKey() -> Salida de scsEjgMapper para modificar el identificador para DocuShare del EJG");
			}
		}

		LOGGER.info("insertCollectionEjg() -> Salida al servicio para la insertar una nueva colección");

		return idDS;
	}

	@Override
	public DocushareDTO searchListDirEjg(int numPagina, DocuShareObjectVO docu, HttpServletRequest request) throws Exception {
		DocushareDTO docushareDTO = new DocushareDTO();
		String identificadorDS = null;
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ScsEjgKey key = new ScsEjgKey();

		key.setIdinstitucion(idInstitucion);
		key.setAnio(Short.valueOf(docu.getAnio()));
		key.setIdtipoejg(Short.valueOf(docu.getIdTipoEjg()));
		key.setNumero(Long.valueOf(docu.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(key);

		if (ejg.getIdentificadords() == null) {

			// Año EJG/Num EJG. Se realiza el proceso anterior para no utilizar numEjg ya
			// que no es una clave unica
			// y mantener el formato de DocuShare.
			String title = docu.getAnio() + "/" + ejg.getNumejg();

			LOGGER.debug("ValorEjgDocu : " + title);
			identificadorDS = docushareHelper.buscaCollectionEjg(title, idInstitucion);
		} else {
			identificadorDS = ejg.getIdentificadords();
		}
		if (identificadorDS != null) {
			List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, docu.getParent());
			docushareDTO.setDocuShareObjectVO(docus);
		}
		return docushareDTO;
	}

	public int triggersEjgUpdatesFApertura(EjgItem ejgItem, AdmUsuarios usuario, short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesFApertura() -> Entrada al metodo para realizar cambios en el estado inicial del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG de la BBDD al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		ejgKey.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		// 1. Fecha de apertura. si modifican la fecha de apertura modificamos la fecha
		// del estado inicial
		if (!ejg.getFechaapertura().equals(ejgItem.getFechaApertura())) {
			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(ejgItem.getTipoEJG())).andAnioEqualTo(Short.valueOf(ejgItem.getAnnio())).andNumeroEqualTo(Long.valueOf(ejgItem.getNumero())).andIdestadoejgEqualTo((short) 23); // Solicitud
			// en
			// procesdo
			// de alta
			// ===
			// scs_maestroestadosejg.idestado=23

			List<ScsEstadoejg> estadoIni = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			estadoIni.get(0).setFechainicio(ejgItem.getFechaApertura());

			response = scsEstadoejgMapper.updateByExample(estadoIni.get(0), estadoEjgExample);
			if (response == 0)
				throw (new Exception("Error en triggersEjgUpdatesFApertura() 1."));
		}

		LOGGER.info("triggersEjgUpdatesFApertura() -> Salida del metodo para realizar cambios en el estado inicial del EJG");

		return response;
	}

	public int triggersEjgUpdatesDictamen(EjgItem ejgItem, AdmUsuarios usuario, short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesDictamen() -> Entrada al metodo para realizar cambios en estados de \"Dictaminado\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG de la BBDD al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		ejgKey.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		// 2.1cSi cambia el dictamen o la fecha dictamen y no eran nulos antes o despues
		// ponemos fecha de baja a todos los estados anteriores con valor "Dictaminado"
		if ((ejg.getFechadictamen() != ejgItem.getFechaDictamen() || ejg.getIdtipodictamenejg() != ejgItem.getIdTipoDictamen()) && (ejg.getFechadictamen() != null && ejg.getIdtipodictamenejg() != null) || (ejgItem.getFechaDictamen() != null && ejgItem.getIdTipoDictamen() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(ejgItem.getTipoEJG())).andAnioEqualTo(Short.valueOf(ejgItem.getAnnio())).andNumeroEqualTo(ejg.getNumero()).andIdestadoejgEqualTo((short) 6) // Dictaminado === scs_maestroestadosejg.idestado=6
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoDict = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if (!estadoDict.isEmpty()) {
				estadoDict.get(0).setFechabaja(new Date());

				response = scsEstadoejgMapper.updateByExample(estadoDict.get(0), estadoEjgExample);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesDictamen() 2.1"));
			}

			// 2.2 En el caso que ahora (los valores nuevos) no fueran nulos
			if (ejgItem.getFechaDictamen() != null && ejgItem.getIdTipoDictamen() != null) {
				// Se inserta el estado dictaminado y se pone en las observacions el dictamen.
				// La fecha de inicio es la fecha de dictamen

				ScsEstadoejg newEstadoDictaminado = new ScsEstadoejg();

				newEstadoDictaminado.setIdinstitucion(idInstitucion);
				newEstadoDictaminado.setIdtipoejg(ejg.getIdtipoejg());
				newEstadoDictaminado.setAnio(ejg.getAnio());
				newEstadoDictaminado.setNumero(ejg.getNumero());
				newEstadoDictaminado.setIdestadoejg((short) 6); // Dictaminado === scs_maestroestadosejg.idestado=6
				newEstadoDictaminado.setFechainicio(ejgItem.getFechaDictamen());
				newEstadoDictaminado.setFechamodificacion(new Date());
				newEstadoDictaminado.setUsumodificacion(usuario.getIdusuario());

				// Se realiza una consulta SQL para obtener las observaciones asociadas
				newEstadoDictaminado.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgDictamen(idInstitucion, usuario.getIdlenguaje(), ejgItem.getIdTipoDictamen()));

				// obtenemos el maximo de idestadoporejg
				newEstadoDictaminado.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

				newEstadoDictaminado.setAutomatico("1");
				newEstadoDictaminado.setPropietariocomision("0");

				response = scsEstadoejgMapper.insert(newEstadoDictaminado);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesFApertura() 2.2"));

			}
		}

		LOGGER.info("triggersEjgUpdatesDictamen() -> Salida del metodo para realizar cambios en estados de \"Dictaminado\" anteriores del EJG");

		return response;
	}

	public int triggersEjgUpdatesPonente(ResolucionEJGItem resolEjg, AdmUsuarios usuario, Short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesPonente() -> Entrada al metodo para realizar cambios en estados de \"Remitida apertura a CAJG-Reparto Ponente\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG de la BBDD al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(resolEjg.getAnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(resolEjg.getIdTipoEJG()));
		ejgKey.setNumero(Long.valueOf(resolEjg.getNumero()));

		LOGGER.info("RESOLEJG: anio idinstitucion tipoejg numero " + resolEjg.getAnio() + " " + idInstitucion + " " + resolEjg.getIdTipoEJG() + " " + resolEjg.getNumero());

		LOGGER.info("EJGKEY: anio idinstitucion tipoejg numero " + ejgKey.getAnio().toString() + " " + ejgKey.getIdinstitucion().toString() + " " + ejgKey.getIdtipoejg().toString() + " " + ejgKey.getNumero().toString());

		LOGGER.info("****** scsejgmapper ******** " + scsEjgMapper);

		ScsEjgExample scsEjgEx = new ScsEjgExample();
		scsEjgEx.createCriteria().andIdinstitucionactaEqualTo((short) 2078);
		LOGGER.info("****avc****" + scsEjgMapper.countByExample(scsEjgEx));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		LOGGER.info("Si que conseguimos el ejg ");

		// 3.1 Si cambia el ponente o la fecha presentacion ponente y no eran nulos antes o despues
		// ponemos fecha de baja a todos los estados anteriores con "Remitida apertura a CAJG-Reparto Ponente"
		if ((ejg.getFechapresentacionponente() != resolEjg.getFechaPresentacionPonente() || (ejg.getIdponente() != resolEjg.getIdPonente())) && (ejg.getFechapresentacionponente() != null && ejg.getIdponente() != null) || (resolEjg.getFechaPresentacionPonente() != null && resolEjg.getIdPonente() != null)) {

			LOGGER.info("entramos en el if ");
			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(resolEjg.getIdTipoEJG())).andAnioEqualTo(Short.valueOf(resolEjg.getAnio())).andNumeroEqualTo(ejg.getNumero()).andIdestadoejgEqualTo((short) 0) // Remitida apertura a CAJG-Reparto Ponente ===
					// scs_maestroestadosejg.idestadoejg=0
					.andAutomaticoEqualTo("1").andFechabajaIsNull();
			LOGGER.info("creamos criteria ");
			List<ScsEstadoejg> estadoPonente = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			LOGGER.info("obtenemos lista criteria");
			if (!estadoPonente.isEmpty()) {
				estadoPonente.get(0).setFechabaja(new Date());
				LOGGER.info("Si la lista no es nula intentamos updatear ");
				response = scsEstadoejgMapper.updateByExample(estadoPonente.get(0), estadoEjgExample);
				LOGGER.info("updateamos ");
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesPonente 3.1"));
			}

			// 3.2 En el caso que ahora (los valores nuevos) no fueran nulos
			if (resolEjg.getFechaPresentacionPonente() != null && resolEjg.getIdPonente() != null) {
				// Se inserta el estado "Remitida apertura a CAJG-Reparto Ponente" y se pone en
				// las observacions el nombre del ponente.
				LOGGER.info("fecha y idponente no nulos ");
				ScsEstadoejg newEstadoPonente = new ScsEstadoejg();

				newEstadoPonente.setIdinstitucion(idInstitucion);
				newEstadoPonente.setIdtipoejg(ejg.getIdtipoejg());
				newEstadoPonente.setAnio(ejg.getAnio());
				newEstadoPonente.setNumero(ejg.getNumero());
				newEstadoPonente.setIdestadoejg((short) 0); // Remitida apertura a CAJG-Reparto Ponente ===
				// scs_maestroestadosejg.idestado=0
				newEstadoPonente.setFechainicio(resolEjg.getFechaPresentacionPonente());
				newEstadoPonente.setFechamodificacion(new Date());
				newEstadoPonente.setUsumodificacion(usuario.getIdusuario());
				LOGGER.info("Completamos datos para el key ");

				LOGGER.info("Completamos datos para el key " + idInstitucion);

				LOGGER.info("Completamos datos para el key " + usuario.getIdlenguaje());

				LOGGER.info("Completamos datos para el key " + resolEjg.getIdPonente());

				// Se realiza una consulta SQL para obtener las observaciones asociadas
				newEstadoPonente.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgPonente(idInstitucion, usuario.getIdlenguaje(), resolEjg.getIdPonente()));

				LOGGER.info("seteamos las observaciones ");

				LOGGER.info("ejg: anio idinstitucion tipoejg numero " + ejg.getAnio().toString() + " " + ejg.getIdinstitucion().toString() + " " + ejg.getIdtipoejg().toString() + " " + ejg.getNumero().toString());

				// obtenemos el maximo de idestadoporejg
				newEstadoPonente.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
				LOGGER.info("seteamos el id estado por ejg ");
				newEstadoPonente.setAutomatico("1");
				newEstadoPonente.setPropietariocomision("1");

				response = scsEstadoejgMapper.insert(newEstadoPonente);
				LOGGER.info("Creamos el registro ");
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesPonente 3.2"));
			}
		}

		LOGGER.info("triggersEjgUpdatesPonente() -> Salida del metodo para realizar cambios en estados de \"Remitida apertura a CAJG-Reparto Ponente\" anteriores del EJG");

		return response;
	}

	public int triggersEjgUpdatesResol(ResolucionEJGItem resolEjg, AdmUsuarios usuario, Short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesResol() -> Entrada al metodo para realizar cambios en estados de \"Resuelto Comisión\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG de la BBDD al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(resolEjg.getAnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(resolEjg.getIdTipoEJG()));
		ejgKey.setNumero(Long.valueOf(resolEjg.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		// 4.1 Si cambia la resolucion o la fecha de resolucion y no eran nulos antes o despues
		// ponemos fecha de baja a todos los estados anteriores que hayan sido "Resuelto Comisión"
		if ((ejg.getFecharesolucioncajg() != resolEjg.getFechaResolucionCAJG() || (ejg.getIdtiporatificacionejg() != resolEjg.getIdTiporatificacionEJG())) && (ejg.getFecharesolucioncajg() != null && ejg.getIdtiporatificacionejg() != null) || (resolEjg.getFechaResolucionCAJG() != null && resolEjg.getIdTiporatificacionEJG() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(resolEjg.getIdTipoEJG())).andAnioEqualTo(Short.valueOf(resolEjg.getAnio())).andNumeroEqualTo(ejg.getNumero()).andIdestadoejgEqualTo((short) 10) // Resuelto Comisión === scs_maestroestadosejg.idestado=10
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoResol = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if (!estadoResol.isEmpty()) {
				estadoResol.get(0).setFechabaja(new Date());

				response = scsEstadoejgMapper.updateByExample(estadoResol.get(0), estadoEjgExample);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesResol 4.1"));
			}

			// 4.2 En el caso que ahora (los valores nuevos) no fueran nulos
			if (resolEjg.getFechaResolucionCAJG() != null && resolEjg.getIdTiporatificacionEJG() != null) {
				// Se inserta el estado Resuelto comisión y se pone en las observacions el tipo de resolcuion.

				ScsEstadoejg newEstadoResol = new ScsEstadoejg();

				newEstadoResol.setIdinstitucion(idInstitucion);
				newEstadoResol.setIdtipoejg(ejg.getIdtipoejg());
				newEstadoResol.setAnio(ejg.getAnio());
				newEstadoResol.setNumero(ejg.getNumero());
				newEstadoResol.setIdestadoejg((short) 10); // Resuelto Comisión === scs_maestroestadosejg.idestado=10
				newEstadoResol.setFechainicio(resolEjg.getFechaResolucionCAJG());
				newEstadoResol.setFechamodificacion(new Date());
				newEstadoResol.setUsumodificacion(usuario.getIdusuario());

				// Se realiza una consulta SQL para obtener las observaciones asociadas
				newEstadoResol.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgResol(usuario.getIdlenguaje(), resolEjg.getIdTiporatificacionEJG()));

				// Obtenemos el maximo de idestadoporejg
				newEstadoResol.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

				newEstadoResol.setAutomatico("1");
				newEstadoResol.setPropietariocomision("1");

				response = scsEstadoejgMapper.insert(newEstadoResol);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesResol 4.2"));

				// 4.3 En el caso que ahora (los valores nuevos) la resolucion fuera "Devuelto al Colegio" (valor 6 del combo)
				if (resolEjg.getIdTiporatificacionEJG() == 6) {
					
					// Se inserta el estado Resuelta impugnación y se pone en las observaciones el tipo de resolucion.
					ScsEstadoejg newEstadoResolDev = new ScsEstadoejg();

					newEstadoResolDev.setIdinstitucion(idInstitucion);
					newEstadoResolDev.setIdtipoejg(ejg.getIdtipoejg());
					newEstadoResolDev.setAnio(ejg.getAnio());
					newEstadoResolDev.setNumero(ejg.getNumero());
					newEstadoResolDev.setIdestadoejg((short) 21); // Devuelto al Colegio ===
					// scs_maestroestadosejg.idestado=21
					newEstadoResolDev.setFechainicio(resolEjg.getFechaResolucionCAJG());
					newEstadoResolDev.setFechamodificacion(new Date());
					newEstadoResolDev.setUsumodificacion(usuario.getIdusuario());

					// Se realiza una consulta SQL para obtener las observaciones asociadas
					newEstadoResolDev.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgResolDev(usuario.getIdlenguaje()));

					// Obtenemos el maximo de idestadoporejg
					newEstadoResolDev.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

					newEstadoResolDev.setAutomatico("1");
					newEstadoResolDev.setPropietariocomision("0");

					response = scsEstadoejgMapper.insert(newEstadoResolDev);
					if (response == 0)
						throw (new Exception("Error en triggersEjgUpdatesResol 4.3"));
				}
			}
		}

		LOGGER.info("triggersEjgUpdatesResol() -> Salida del metodo para realizar cambios en estados de \"Resuelto Comisión\" anteriores del EJG");

		return response;
	}

	public int triggersEjgUpdatesImpug(EjgItem ejgItem, AdmUsuarios usuario, Short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesImpug() -> Entrada al metodo para realizar cambios en estados de \"Resuelta Impugnación\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG de la BBDD al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		ejgKey.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		// 5.1 Si cambia la impugnacion o la fecha de impugnacion y no eran nulos antes
		// o
		// despues ponemos fecha de baja a todos los estados anteriores "Resuelta
		// Impugnación"
		if ((ejg.getFechaauto() != ejgItem.getFechaAuto() || (ejg.getIdtiporesolauto().toString() != ejgItem.getAutoResolutorio())) && (ejg.getFechaauto() != null && ejg.getIdtiporesolauto() != null) || (ejgItem.getFechaAuto() != null && ejgItem.getAutoResolutorio() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(Short.valueOf(ejgItem.getTipoEJG())).andAnioEqualTo(Short.valueOf(ejgItem.getAnnio())).andNumeroEqualTo(ejg.getNumero()).andIdestadoejgEqualTo((short) 13) // Resuelta Impugnación === scs_maestroestadosejg.idestado=13
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoImpug = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if (!estadoImpug.isEmpty()) {
				estadoImpug.get(0).setFechabaja(new Date());

				response = scsEstadoejgMapper.updateByExample(estadoImpug.get(0), estadoEjgExample);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesImpug 5.1"));
			}

			// 5.2 En el caso que ahora (los valores nuevos) no fueran nulos
			if (ejgItem.getFechaAuto() != null && ejgItem.getAutoResolutorio() != null) {
				// Se inserta el estado Resuelta impugnación y se pone en las observaciones el
				// tipo de resolucion.

				ScsEstadoejg newEstadoImpug = new ScsEstadoejg();

				newEstadoImpug.setIdinstitucion(idInstitucion);
				newEstadoImpug.setIdtipoejg(ejg.getIdtipoejg());
				newEstadoImpug.setAnio(ejg.getAnio());
				newEstadoImpug.setNumero(ejg.getNumero());
				newEstadoImpug.setIdestadoejg((short) 13); // Resuelta Impugnación === scs_maestroestadosejg.idestado=13
				newEstadoImpug.setFechainicio(ejgItem.getFechaAuto());
				newEstadoImpug.setFechamodificacion(new Date());
				newEstadoImpug.setUsumodificacion(usuario.getIdusuario());

				// Se realiza una consulta SQL para obtener las observaciones asociadas
				newEstadoImpug.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgImpug(usuario.getIdlenguaje(), ejgItem.getAutoResolutorio()));

				// Obtenemos el maximo de idestadoporejg
				newEstadoImpug.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

				newEstadoImpug.setAutomatico("1");
				newEstadoImpug.setPropietariocomision("1");

				response = scsEstadoejgMapper.insert(newEstadoImpug);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesImpug 5.2"));
			}
		}

		LOGGER.info("triggersEjgUpdatesImpug() -> Salida del metodo para realizar cambios en estados de \"Resuelta Impugnación\" anteriores del EJG");

		return response;
	}

	private int triggersEjgInsert(ScsEjg ejg, AdmUsuarios usuario, Short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgInsert() -> Entrada al metodo para realizar cambios en estados anteriores");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG
		// de la BBDD al inserta una nueva fila.

		int response = 1;

		// Insertar
		// 1. Metemos por defecto en todos los expediente el estado inicial -->
		// Solicitud en Proceso de Alta
		ScsEstadoejg newEstadoIni = new ScsEstadoejg();

		newEstadoIni.setIdinstitucion(idInstitucion);
		newEstadoIni.setIdtipoejg(ejg.getIdtipoejg());
		newEstadoIni.setAnio(ejg.getAnio());
		newEstadoIni.setNumero(ejg.getNumero());
		newEstadoIni.setIdestadoejg((short) 23); // Solicitud en Proceso de Alta === scs_maestroestadosejg.idestado=23
		newEstadoIni.setFechainicio(ejg.getFechaapertura());
		newEstadoIni.setFechamodificacion(new Date());
		newEstadoIni.setUsumodificacion(usuario.getIdusuario());

		// Se realiza una consulta SQL para obtener las observaciones asociadas
		newEstadoIni.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoIniInsertEjg(usuario.getIdlenguaje()));

		// Obtenemos el maximo de idestadoporejg
		newEstadoIni.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

		newEstadoIni.setAutomatico("1");

		response = scsEstadoejgMapper.insert(newEstadoIni);
		if (response == 0)
			throw (new Exception("Error en triggersEjgInsert() 1"));

		// 2. Estado a partir de fechadictamen
//                           if (ejg.getFechadictamen() != null && ejg.getIdtipodictamenejg() != null) {
//                                           ScsEstadoejg newEstadoDict = new ScsEstadoejg();
//
//                                           newEstadoDict.setIdinstitucion(idInstitucion);
//                                           newEstadoDict.setIdtipoejg(ejg.getIdtipoejg());
//                                           newEstadoDict.setAnio(ejg.getAnio());
//                                           newEstadoDict.setNumero(ejg.getNumero());
//                                           newEstadoDict.setIdestadoejg((short) 6); // Dictaminado === scs_maestroestadosejg.idestado=6
//                                           newEstadoDict.setFechainicio(ejg.getFechadictamen());
//                                           newEstadoDict.setFechamodificacion(new Date());
//                                           newEstadoDict.setUsumodificacion(usuario.getIdusuario());
//
//                                           // Se realiza una consulta SQL para obtener las observaciones asociadas
//                                           newEstadoDict.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgDictamen(idInstitucion,
//                                                                           usuario.getIdlenguaje(), ejgItem.getIdTipoDictamen()));
//
//                                           // Obtenemos el maximo de idestadoporejg
//                                           newEstadoDict.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//                                           newEstadoDict.setAutomatico("1");
//
//                                           response = scsEstadoejgMapper.insert(newEstadoDict);
//                                           if (response == 0)
//                                                           throw (new Exception("Error en triggersEjgInsert() 2"));
//                           }
//
//                           // 3 En el caso que los datos de ponente no fueran nulos
//                           if (resolEjg.getFechaPresentacionPonente() != null && resolEjg.getIdPonente() != null) {
//                                           // Se inserta el estado "Remitida apertura a CAJG-Reparto Ponente" y se pone en
//                                           // las observacions el nombre del ponente.
//
//                                           ScsEstadoejg newEstadoPonente = new ScsEstadoejg();
//
//                                           newEstadoPonente.setIdinstitucion(idInstitucion);
//                                           newEstadoPonente.setIdtipoejg(ejg.getIdtipoejg());
//                                           newEstadoPonente.setAnio(ejg.getAnio());
//                                           newEstadoPonente.setNumero(ejg.getNumero());
//                                           newEstadoPonente.setIdestadoejg((short) 0); // Remitida apertura a CAJG-Reparto Ponente ===
//                                                                                                                                                                                                                        // scs_maestroestadosejg.idestado=0
//                                           newEstadoPonente.setFechainicio(resolEjg.getFechaPresentacionPonente());
//                                           newEstadoPonente.setFechamodificacion(new Date());
//                                           newEstadoPonente.setUsumodificacion(usuario.getIdusuario());
//
//                                           // Se realiza una consulta SQL para obtener las observaciones asociadas
//                                           newEstadoPonente.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgPonente(idInstitucion,
//                                                                           usuario.getIdlenguaje(), resolEjg.getIdPonente()));
//
//                                           // obtenemos el maximo de idestadoporejg
//                                           newEstadoPonente.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//                                           newEstadoPonente.setAutomatico("1");
//
//                                           response = scsEstadoejgMapper.insert(newEstadoPonente);
//                                           if (response == 0)
//                                                           throw (new Exception("Error en triggersEjgInsert() 3"));
//                           }
//
//                           // 4 En el caso que los datos de resolucion no fueran nulos
//                           if (resolEjg.getFechaResolucionCAJG() != null && resolEjg.getIdTiporatificacionEJG() != null) {
//                                           // Se inserta el estado Resuelto comisión y se pone en las observacions el tipo
//                                           // de resolcuion.
//
//                                           ScsEstadoejg newEstadoResol = new ScsEstadoejg();
//
//                                           newEstadoResol.setIdinstitucion(idInstitucion);
//                                           newEstadoResol.setIdtipoejg(ejg.getIdtipoejg());
//                                           newEstadoResol.setAnio(ejg.getAnio());
//                                           newEstadoResol.setNumero(ejg.getNumero());
//                                           newEstadoResol.setIdestadoejg((short) 10); // Resuelto Comisión === scs_maestroestadosejg.idestado=10
//                                           newEstadoResol.setFechainicio(resolEjg.getFechaResolucionCAJG());
//                                           newEstadoResol.setFechamodificacion(new Date());
//                                           newEstadoResol.setUsumodificacion(usuario.getIdusuario());
//
//                                           // Se realiza una consulta SQL para obtener las observaciones asociadas
//                                           newEstadoResol.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgResol(idInstitucion,
//                                                                           usuario.getIdlenguaje(), resolEjg.getIdTiporatificacionEJG()));
//
//                                           // Obtenemos el maximo de idestadoporejg
//                                           newEstadoResol.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//                                           newEstadoResol.setAutomatico("1");
//
//                                           response = scsEstadoejgMapper.insert(newEstadoResol);
//                                           if (response == 0)
//                                                           throw (new Exception("Error en triggersEjgInsert() 4"));
//                           }
//
//                           // 5 En el caso que los datos del auto no fueran nulos
//                           if (ejgItem.getFechaAuto() != null && resolEjg.getTipoResolucionCAJG() != null) {
//                                           // Se inserta el estado Resuelta impugnación y se pone en las observaciones el
//                                           // tipo de resolucion.
//
//                                           ScsEstadoejg newEstadoImpug = new ScsEstadoejg();
//
//                                           newEstadoImpug.setIdinstitucion(idInstitucion);
//                                           newEstadoImpug.setIdtipoejg(ejg.getIdtipoejg());
//                                           newEstadoImpug.setAnio(ejg.getAnio());
//                                           newEstadoImpug.setNumero(ejg.getNumero());
//                                           newEstadoImpug.setIdestadoejg((short) 13); // Resuelta Impugnación === scs_maestroestadosejg.idestado=13
//                                           newEstadoImpug.setFechainicio(ejgItem.getFechaAuto());
//                                           newEstadoImpug.setFechamodificacion(new Date());
//                                           newEstadoImpug.setUsumodificacion(usuario.getIdusuario());
//
//                                           // Se realiza una consulta SQL para obtener las observaciones asociadas
//                                           newEstadoImpug.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgImpug(idInstitucion,
//                                                                           usuario.getIdlenguaje(), resolEjg.getTipoResolucionCAJG()));
//
//                                           // Obtenemos el maximo de idestadoporejg
//                                           newEstadoImpug.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//                                           newEstadoImpug.setAutomatico("1");
//
//                                           response = scsEstadoejgMapper.insert(newEstadoImpug);
//                                           if (response == 0)
//                                                           throw (new Exception("Error en triggersEjgInsert() 5"));
//                           }

		return response;
	}

	private Long getNewIdestadoporejg(ScsEjg ejg, Short idInstitucion) {

		Long newIdestadoporejg;
		// obtenemos el maximo de idestadoporejg
		ScsEstadoejgExample example = new ScsEstadoejgExample();

		example.setOrderByClause("IDESTADOPOREJG DESC");
		example.createCriteria().andAnioEqualTo(ejg.getAnio()).andIdinstitucionEqualTo(idInstitucion).andIdtipoejgEqualTo(ejg.getIdtipoejg()).andNumeroEqualTo(ejg.getNumero());

		List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

		// damos el varlo al idestadoporejg + 1
		if (listEjg.size() > 0) {
			newIdestadoporejg = listEjg.get(0).getIdestadoporejg() + 1;
		} else {
			newIdestadoporejg = Long.parseLong("1");
		}

		return newIdestadoporejg;
	}

	private void insertAuditoriaEJG(String campo, String valorPre, String valorPost, AdmUsuarios usuario, ScsEjg item) {

//        ScsAuditoriaejg entradaAuditoriaEjg = new ScsAuditoriaejg();
//
//        entradaAuditoriaEjg.setIdinstitucion(item.getIdinstitucion());
//        entradaAuditoriaEjg.setAnio(item.getAnio());
//        entradaAuditoriaEjg.setNumero(item.getNumero());
//        entradaAuditoriaEjg.setIdtipoejg(item.getIdtipoejg());
//
//        entradaAuditoriaEjg.setCampo(campo);
//        entradaAuditoriaEjg.setValorpre(valorPre);
//        entradaAuditoriaEjg.setValorpost(valorPost);
//        // Revisar que se esta asignando la hora correctamente
//        entradaAuditoriaEjg.setFechahoracambio(new Date());
//
//        entradaAuditoriaEjg.setFechamodificacion(new Date());
//        entradaAuditoriaEjg.setUsumodificacion(usuario.getIdusuario());
//
//        int response = scsAuditoriaejgMapper.insert(entradaAuditoriaEjg);
	}

	private String getDescripcionEjg(ScsEjg ejbAnterior, ScsEjg ejgNuevo, String accion) {

		StringBuilder sb = new StringBuilder();
		String descripcion = null;

		switch (accion) {
		case "INSERT":
			estableceDescripcionEjg(sb, ejgNuevo, "REGISTRO NUEVO");
			descripcion = sb.toString();
			break;
		case "UPDATE":
			estableceDescripcionEjg(sb, ejbAnterior, "REGISTRO ANTERIOR");
			estableceDescripcionEjg(sb, ejgNuevo, "REGISTRO ACTUAL");
			descripcion = sb.toString();
			break;
		case "DELETE":
			estableceDescripcionEjg(sb, ejgNuevo, "REGISTRO ELIMINADO");
			descripcion = sb.toString();
			break;
		default:
			break;
		}

		return descripcion;
	}

	private void estableceDescripcionEjg(StringBuilder sb, ScsEjg ejg, String cabecera) {

		sb.append(cabecera);
		sb.append("\n");
		addDato(sb, "Anio", ejg.getAnio());
		addDato(sb, "Numero", ejg.getNumero());
		addDato(sb, "Fechaapertura", ejg.getFechaapertura());
		addDato(sb, "Origenapertura", ejg.getOrigenapertura());
		addDato(sb, "Calidad", ejg.getCalidad());
		addDato(sb, "Tipoletrado", ejg.getTipoletrado());
		addDato(sb, "Fechamodificacion", ejg.getFechamodificacion());
		addDato(sb, "Usumodificacion", ejg.getUsumodificacion());
		addDato(sb, "Idpersona", ejg.getIdpersona());
		addDato(sb, "Idinstitucion", ejg.getIdinstitucion());
		addDato(sb, "Idtipoejg", ejg.getIdtipoejg());
		addDato(sb, "Guardiaturno_idturno", ejg.getGuardiaturnoIdturno());
		addDato(sb, "Guardiaturno_idguardia", ejg.getGuardiaturnoIdguardia());
		addDato(sb, "Fechalimitepresentacion", ejg.getFechalimitepresentacion());
		addDato(sb, "Fechapresentacion", ejg.getFechapresentacion());
		addDato(sb, "Procuradornecesario", ejg.getProcuradornecesario());
		addDato(sb, "Observaciones", ejg.getObservaciones());
		addDato(sb, "Delitos", ejg.getDelitos());
		addDato(sb, "Fechadictamen", ejg.getFechadictamen());
		addDato(sb, "Fecharatificacion", ejg.getFecharatificacion());
		addDato(sb, "Idtipodictamenejg", ejg.getIdtipodictamenejg());
		addDato(sb, "Idtipoejgcolegio", ejg.getIdtipoejgcolegio());
		addDato(sb, "Idpersonajg", ejg.getIdpersonajg());
		addDato(sb, "Facturado", ejg.getFacturado());
		addDato(sb, "Pagado", ejg.getPagado());
		addDato(sb, "Idfacturacion", ejg.getIdfacturacion());
		addDato(sb, "Idinstitucion_proc", ejg.getIdinstitucionProc());
		addDato(sb, "Idprocurador", ejg.getIdprocurador());
		addDato(sb, "Idtiporatificacionejg", ejg.getIdtiporatificacionejg());
		addDato(sb, "Aniocajg", ejg.getAniocajg());
		addDato(sb, "Fecharesolucioncajg", ejg.getFecharesolucioncajg());
		addDato(sb, "Idfundamentojuridico", ejg.getIdfundamentojuridico());
		addDato(sb, "Fechanotificacion", ejg.getFechanotificacion());
		addDato(sb, "Turnadoratificacion", ejg.getTurnadoratificacion());
		addDato(sb, "Turnadoauto", ejg.getTurnadoauto());
		addDato(sb, "Fechaauto", ejg.getFechaauto());
		addDato(sb, "Idtiporesolauto", ejg.getIdtiporesolauto());
		addDato(sb, "Idtiposentidoauto", ejg.getIdtiposentidoauto());
		addDato(sb, "Idfundamentocalif", ejg.getIdfundamentocalif());
		addDato(sb, "Numero_cajg", ejg.getNumeroCajg());
		addDato(sb, "Numejg", ejg.getNumejg());
		addDato(sb, "Numerodiligencia", ejg.getNumerodiligencia());
		addDato(sb, "Numeroprocedimiento", ejg.getNumeroprocedimiento());
		addDato(sb, "Comisaria", ejg.getComisaria());
		addDato(sb, "Comisariaidinstitucion", ejg.getComisariaidinstitucion());
		addDato(sb, "Juzgado", ejg.getJuzgado());
		addDato(sb, "Juzgadoidinstitucion", ejg.getJuzgadoidinstitucion());
		addDato(sb, "Idpretension", ejg.getIdpretension());
		addDato(sb, "Iddictamen", ejg.getIddictamen());
		addDato(sb, "Refauto", ejg.getRefauto());
		addDato(sb, "Fecha_des_proc", ejg.getFechaDesProc());
		addDato(sb, "Sufijo", ejg.getSufijo());
		addDato(sb, "Identificadords", ejg.getIdentificadords());
		addDato(sb, "Idorigencajg", ejg.getIdorigencajg());
		addDato(sb, "Idpreceptivo", ejg.getIdpreceptivo());
		addDato(sb, "Idsituacion", ejg.getIdsituacion());
		addDato(sb, "Idrenuncia", ejg.getIdrenuncia());
		addDato(sb, "Numerodesignaproc", ejg.getNumerodesignaproc());
		addDato(sb, "Idtipoencalidad", ejg.getIdtipoencalidad());
		addDato(sb, "Calidadidinstitucion", ejg.getCalidadidinstitucion());
		addDato(sb, "Docresolucion", ejg.getDocresolucion());
		addDato(sb, "Fechacreacion", ejg.getFechacreacion());
		addDato(sb, "Usucreacion", ejg.getUsucreacion());
		addDato(sb, "Nig", ejg.getNig());
		addDato(sb, "Idponente", ejg.getIdponente());
		addDato(sb, "Fechapublicacion", ejg.getFechapublicacion());
		addDato(sb, "Anioresolucion", ejg.getAnioresolucion());
		addDato(sb, "Numeroresolucion", ejg.getNumeroresolucion());
		addDato(sb, "Bisresolucion", ejg.getBisresolucion());
		addDato(sb, "Idacta", ejg.getIdacta());
		addDato(sb, "Idinstitucionacta", ejg.getIdinstitucionacta());
		addDato(sb, "Anioacta", ejg.getAnioacta());
		addDato(sb, "Idecomcola", ejg.getIdecomcola());
		addDato(sb, "Requierenotificarproc", ejg.getRequierenotificarproc());
		addDato(sb, "Fechapresentacionponente", ejg.getFechapresentacionponente());
		addDato(sb, "Anioprocedimiento", ejg.getAnioprocedimiento());
		addDato(sb, "Idinstitucionponente", ejg.getIdinstitucionponente());

		if (ejg instanceof ScsEjgWithBLOBs) {
			ScsEjgWithBLOBs scsEjgWithBLOBs = (ScsEjgWithBLOBs) ejg;
			addDato(sb, "Observacionimpugnacion", scsEjgWithBLOBs.getObservacionimpugnacion());
			addDato(sb, "Ratificaciondictamen", scsEjgWithBLOBs.getRatificaciondictamen());
			addDato(sb, "Dictamen", scsEjgWithBLOBs.getDictamen());
		}

	}

	private String getDescripcionUnidadFamiliar(ScsUnidadfamiliarejg familiarAnterior, ScsUnidadfamiliarejg familiarNuevo, String accion) {

		StringBuilder sb = new StringBuilder();
		String descripcion = null;

		switch (accion) {
		case "INSERT":
			estableceDescripcionUnidadFamiliar(sb, familiarNuevo, "REGISTRO NUEVO");
			descripcion = sb.toString();
			break;
		case "UPDATE":
			estableceDescripcionUnidadFamiliar(sb, familiarAnterior, "REGISTRO ANTERIOR");
			estableceDescripcionUnidadFamiliar(sb, familiarNuevo, "REGISTRO ACTUAL");
			descripcion = sb.toString();
			break;
		case "DELETE":
			estableceDescripcionUnidadFamiliar(sb, familiarNuevo, "REGISTRO ELIMINADO");
			descripcion = sb.toString();
			break;
		default:
			break;
		}

		return descripcion;
	}

	private void estableceDescripcionUnidadFamiliar(StringBuilder sb, ScsUnidadfamiliarejg familiar, String cabecera) {

		sb.append(cabecera);
		sb.append("\n");
		addDato(sb, "Idinstitucion", familiar.getIdinstitucion());
		addDato(sb, "Idtipoejg", familiar.getIdtipoejg());
		addDato(sb, "Anio", familiar.getAnio());
		addDato(sb, "Numero", familiar.getNumero());
		addDato(sb, "Idpersona", familiar.getIdpersona());
		addDato(sb, "Solicitante", familiar.getSolicitante());
		addDato(sb, "Fechamodificacion", familiar.getFechamodificacion());
		addDato(sb, "Usumodificacion", familiar.getUsumodificacion());
		addDato(sb, "Observaciones", familiar.getObservaciones());
		addDato(sb, "Encalidadde", familiar.getEncalidadde());
		addDato(sb, "Bienesinmuebles", familiar.getBienesinmuebles());
		addDato(sb, "Importebienesinmuebles", familiar.getImportebienesinmuebles());
		addDato(sb, "Bienesmuebles", familiar.getBienesmuebles());
		addDato(sb, "Importebienesmuebles", familiar.getImportebienesmuebles());
		addDato(sb, "Otrosbienes", familiar.getOtrosbienes());
		addDato(sb, "Descripcioningresosanuales", familiar.getDescripcioningresosanuales());
		addDato(sb, "Importeingresosanuales", familiar.getImporteingresosanuales());
		addDato(sb, "Importeotrosbienes", familiar.getImporteotrosbienes());
		addDato(sb, "Idtipogrupolab", familiar.getIdtipogrupolab());
		addDato(sb, "Idparentesco", familiar.getIdparentesco());
		addDato(sb, "Idtipoingreso", familiar.getIdtipoingreso());
		addDato(sb, "Incapacitado", familiar.getIncapacitado());
		addDato(sb, "Circunstancias_excepcionales", familiar.getCircunstanciasExcepcionales());
		addDato(sb, "Fechabaja", familiar.getFechabaja());

	}

	private void addDato(StringBuilder sb, String descripcion, String valor) {
		sb.append(" - ").append(descripcion).append(": ").append(valor != null ? valor : "").append("\n");
	}

	public void addDato(StringBuilder sb, String descripcion, Integer valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	private void addDato(StringBuilder sb, String descripcion, Date date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		addDato(sb, descripcion, (date != null ? df.format(date) : ""));
	}

	private void addDato(StringBuilder sb, String descripcion, Long valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	private void addDato(StringBuilder sb, String descripcion, Short valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	private void addDato(StringBuilder sb, String descripcion, BigDecimal valor) {
		addDato(sb, descripcion, (valor != null ? valor.toString() : ""));
	}

	@Override
	public ExpInsosDTO getDatosExpInsos(EjgItem ejgItem, HttpServletRequest request) {
		ExpInsosDTO result = new ExpInsosDTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.getDatosExpInsos() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.getDatosExpInsos() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.getDatosExpInsos() -> Entrada para obtener los datos del expediente de insostenibilidad");

				try {
					if (ejgItem != null)
						result.setExpInsosItems(scsEjgExtendsMapper.getDatosExpInsos(ejgItem));
					else
						LOGGER.debug("GestionEJGServiceImpl.getDatosExpInsos() -> ejgItem vacío. ");
				} catch (Exception e) {
					LOGGER.debug("GestionEJGServiceImpl.getDatosExpInsos() -> Se ha producido un error. ", e);
					LOGGER.debug("GestionEJGServiceImpl.getDatosExpInsos() -> Se ha producido un error. ", e);
				}
			}
		}

		LOGGER.info("guardarServiciosTramitacion() -> Salida del servicio para actualizar turno, guardia y letrado asociados a un EJG.");

		return result;
	}
	
	private void asociarAsistenciAlDesignaEJG(ScsEjgdesigna record, AdmUsuarios usuario, HttpServletRequest request) {
		//Cuando se asiga Designa, si el EJG está asociado a una asistencia, asociamos el Designa a la asistencia también
		ScsAsistenciaExample asisExample = new ScsAsistenciaExample();
		asisExample.createCriteria()
			.andEjgnumeroEqualTo(record.getNumeroejg())
			.andEjganioEqualTo(record.getAnioejg())
			.andEjgidtipoejgEqualTo(record.getIdtipoejg())
			.andIdinstitucionEqualTo(record.getIdinstitucion());
		
		//obtenemos los datos completos para asignar a la asistencia el Designa del EJG
		List<ScsAsistencia> asis = scsAsistenciaExtendsMapper.selectByExample(asisExample);
		// insertamos los datos del Designa solo si la asistencia no tiene datos de Designa
		if (!asis.isEmpty() && asis.get(0).getDesignaNumero() == null ) {
			ScsAsistencia scsAsistencia = asis.get(0);
			scsAsistencia.setFechamodificacion(new Date());
			scsAsistencia.setUsumodificacion(usuario.getIdusuario());
			scsAsistencia.setDesignaAnio(record.getAniodesigna());
			scsAsistencia.setDesignaNumero(record.getNumerodesigna());
			scsAsistencia.setDesignaTurno(record.getIdturno());
			
			scsAsistenciaMapper.updateByPrimaryKey(scsAsistencia);
			// llevamos los datos de la observaciones de la asistencia al designa
			ScsDesignaKey designaKey = new ScsDesignaKey();
			designaKey.setAnio(record.getAniodesigna());
			designaKey.setNumero(record.getNumerodesigna());
			designaKey.setIdturno(record.getIdturno());
			designaKey.setIdinstitucion(record.getIdinstitucion());
			
			ScsDesigna designa = scsDesignaMapper.selectByPrimaryKey(designaKey);
			
			if (asis.size() == 1 && designa != null && designa.getObservaciones() != null &&  designa.getObservaciones().isEmpty() ) {
				designa.setObservaciones(scsAsistencia.getObservaciones());
				scsDesignaMapper.updateByPrimaryKey(designa);
			}
			
		}
	}
}