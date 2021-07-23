package org.itcgae.siga.scs.services.impl.ejg;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
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
import org.itcgae.siga.DTOs.scs.AsuntosAsistenciaItem;
import org.itcgae.siga.DTOs.scs.AsuntosSOJItem;
import org.itcgae.siga.DTOs.scs.DelitosEjgDTO;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgDesignaDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionDTO;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgDTO;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoDTO;
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
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CajgEjgremesa;
import org.itcgae.siga.db.entities.CajgEjgremesaExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteKey;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsContrariosejg;
import org.itcgae.siga.db.entities.ScsContrariosejgKey;
import org.itcgae.siga.db.entities.ScsDelitosejg;
import org.itcgae.siga.db.entities.ScsDelitosejgExample;
import org.itcgae.siga.db.entities.ScsDictamenejg;
import org.itcgae.siga.db.entities.ScsDictamenejgExample;
import org.itcgae.siga.db.entities.ScsDictamenejgKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
import org.itcgae.siga.db.entities.ScsDocumentacionejgKey;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgActa;
import org.itcgae.siga.db.entities.ScsEjgActaExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazada;
import org.itcgae.siga.db.entities.ScsEjgPrestacionRechazadaExample;
import org.itcgae.siga.db.entities.ScsEjgResolucion;
import org.itcgae.siga.db.entities.ScsEjgResolucionKey;
import org.itcgae.siga.db.entities.ScsEjgResolucionWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsEjgdesigna;
import org.itcgae.siga.db.entities.ScsEjgdesignaExample;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgKey;
import org.itcgae.siga.db.entities.ScsPonente;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojExample;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgKey;
import org.itcgae.siga.db.mappers.CajgEjgremesaMapper;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.GenFicheroMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.mappers.ScsDelitosejgMapper;
import org.itcgae.siga.db.mappers.ScsDictamenejgMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgMapper;
import org.itcgae.siga.db.mappers.ScsDocumentoejgMapper;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.mappers.ScsEjgActaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsEjgPrestacionRechazadaMapper;
import org.itcgae.siga.db.mappers.ScsEjgResolucionMapper;
import org.itcgae.siga.db.mappers.ScsEjgdesignaMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsPonenteMapper;
import org.itcgae.siga.db.mappers.ScsSojMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
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
import org.itcgae.siga.db.services.scs.mappers.ScsExpedienteEconomicoExtendsMapper;
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
import org.itcgae.siga.scs.services.ejg.IEEJGServices;
import org.itcgae.siga.scs.services.ejg.IGestionEJG;
import org.itcgae.siga.scs.services.impl.maestros.BusquedaDocumentacionEjgServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GestionEJGServiceImpl implements IGestionEJG {
	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private GenFicheroMapper genFicheroMapper;

	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;

	@Autowired
	private ScsDocumentacionejgMapper scsDocumentacionejgMapper;

	@Autowired
	private ScsPresentadorExtendsMapper scsPresentadorExtendsMapper;

	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;

	@Autowired
	private ScsPonenteMapper scsPonenteMapper;

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
	private ScsExpedienteEconomicoExtendsMapper scsExpedienteEconomicoExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Autowired
	private ScsDocumentacionEjgExtendsMapper scsDocumentacionejgExtendsMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

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
	private ScsEjgdesignaMapper scsEjgdesignaMapper;

	@Autowired
	private IEEJGServices eejgService;

	@Autowired
	private ScsDocumentoejgMapper scsDocumentoejgMapper;

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
	private ScsDesignaMapper scsDesignaMapper;

	@Autowired
	private CajgEjgremesaMapper cajgEjgremesaMapper;

	@Autowired
	private ScsAsistenciaMapper scsAsistenciaMapper;

	@Autowired
	private ScsEjgActaMapper scsEjgActaMapper;

	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

	@Autowired
	private ScsSojMapper scsSojMapper;

	@Autowired
	private ScsSojExtendsMapper scsSojExtendsMapper;

	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;

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
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"datosEJG() / scsEjgExtendsMapper.datosEJG() -> Entrada a scsEjgExtendsMapper para obtener el EJG");
				ejgDTO.setEjgItems(scsEjgExtendsMapper.datosEJG(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"datosEJG() / scsEjgExtendsMapper.datosEJG() -> Salida de scsEjgExtendsMapper para obtener lista de EJGs");
			} else {
				LOGGER.warn(
						"datosEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosEJG() -> idInstitucion del token nula");
		}

		LOGGER.info("datosEJG() -> Salida del servicio para obtener los datos del ejg seleccionado");
		return ejgDTO;
	}

	@Override
	public ComboDTO comboPrestaciones(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboPrestaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboPrestaciones() / scsPrestacionesExtendsMapper.comboPrestaciones() -> Entrada a scsPrestacionesExtendsMapper para obtener los combo");

				comboItems = scsPrestacionesExtendsMapper.comboPrestaciones(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());

				LOGGER.info(
						"comboPrestaciones() / scsPrestacionesExtendsMapper.comboPrestaciones() -> Salida a scsPrestacionesExtendsMapper para obtener los combo");

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
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTipoencalidad() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboTipoencalidad() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboTipoencalidad() / scsTiporesolucionExtendsMapper.comboTipoEnCalidadDe() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				comboItems = scsTipoencalidadExtendsMapper
						.comboTipoencalidad(usuarios.get(0).getIdlenguaje().toString(), idInstitucion);

				LOGGER.info(
						"comboTipoencalidad() / scsTiporesolucionExtendsMapper.comboTipoEnCalidadDe() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

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
			LOGGER.info(
					"comboSituaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboSituaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboSituaciones() / scsSituacionesExtendsMapper.comboSituaciones() -> Entrada a scsSituacionesExtendsMapper para obtener el combo de situaciones");

				comboItems = scsSituacionesExtendsMapper.comboSituaciones(usuarios.get(0).getIdlenguaje().toString());

				LOGGER.info(
						"comboSituaciones() / scsSituacionesExtendsMapper.comboSituaciones() -> Salida a scsSituacionesExtendsMapper para obtener el combo de situaciones");

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
			LOGGER.info(
					"comboCDetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboCDetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboCDetenciones() / scsComisariaExtendsMapper.comboCDetenciones() -> Entrada a scsComisariaExtendsMapper para obtener el combo de centros de detencion");

				comboItems = scsComisariaExtendsMapper.comboCDetenciones(idInstitucion);

				LOGGER.info(
						"comboCDetenciones() / scsComisariaExtendsMapper.comboCDetenciones() -> Salida a scsComisariaExtendsMapper para obtener el combo de centros de detencion");

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

				comboItems = scsPresentadorExtendsMapper.comboPresentadores(usuarios.get(0).getIdlenguaje(),
						idInstitucion);

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

				comboItems = scsTipodocumentoEjgExtendsMapper.comboTipoDocumentacion(usuarios.get(0).getIdlenguaje(),
						idInstitucion);

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

				comboItems = scsDocumentoejgExtendsMapper.comboDocumentos(usuarios.get(0).getIdlenguaje(),
						idInstitucion, idTipoDocumentacion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("comboDocumento() -> Salida del servicio para obtener combo documento");

		return comboDTO;
	}

	@Override
	public UpdateResponseDTO guardarServiciosTramitacion(EjgItem datos, HttpServletRequest request) {

		LOGGER.info(
				"guardarServiciosTramitacion() -> Entrada al servicio para actualizar los datos asociados a los servicios de tramitacion de EJG");

		UpdateResponseDTO responsedto = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {

					// Seleccionamos el EJG que vamos a actualizar
					ScsEjgKey key = new ScsEjgKey();

					key.setIdinstitucion(idInstitucion);
					key.setAnio(Short.parseShort(datos.getAnnio()));
					key.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					key.setNumero(Long.parseLong(datos.getNumero()));

					ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(key);

					// Una vez tenemos el EJG, introducimos la informacion seleccionada en la
					// tarjeta

					ejg.setGuardiaturnoIdguardia(Integer.parseInt(datos.getIdGuardia()));
					ejg.setGuardiaturnoIdturno(Integer.parseInt(datos.getIdTurno()));
					ejg.setIdpersona(Long.parseLong(datos.getIdPersona()));

					LOGGER.info(
							"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Entrada a scsEjgMapper para actualizar el ejg");

					response = scsEjgMapper.updateByPrimaryKeySelective(ejg);

					LOGGER.info(
							"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Salida a scsEjgMapper para actualizar el ejg");

					if (response != 1) {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"guardarServiciosTramitacion() -> KO. No se ha actualizado turno, guardia y letrado asociados a un EJG.");
						throw new Exception("ERROR: No se ha actualizado turno, guardia y letrado asociados a un EJG.");
					} else {
						responsedto.setStatus(SigaConstants.OK);
					}

				} catch (Exception e) {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.debug(
							"guardarServiciosTramitacion() -> Se ha producido un error al actualizar turno, guardia y letrado asociados a un EJG.",
							e);
				}
			}

		}
		LOGGER.info(
				"guardarServiciosTramitacion() -> Salida del servicio para actualizar turno, guardia y letrado asociados a un EJG.");
		return responsedto;
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
			LOGGER.info(
					"searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchPrestacionesRechazadas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");

				idsPrestRech = scsPrestacionesExtendsMapper.prestacionesRechazadas(ejgItem, idInstitucion);

				LOGGER.info(
						"searchPrestacionesRechazadas() / scsPrestacionesExtendsMapper.prestacionesRechazadas() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");

			}

		}
		LOGGER.info(
				"searchPrestacionesRechazadas() -> Salida del servicio para obtener las prestaciones rechazadas del ejg");
		return idsPrestRech;
	}

	@Override
	public UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request) {
		LOGGER.info("unidadFamiliarEJG() -> Entrada al servicio para obtener la tarjeta unidad familiar de un EJG");
		UnidadFamiliarEJGDTO unidadFamiliarEJGDTO = new UnidadFamiliarEJGDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"unidadFamiliarEJG() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Entrada a scsEjgExtendsMapper para obtener Unidad Familiar");

				unidadFamiliarEJGDTO.setUnidadFamiliarEJGItems(scsPersonajgExtendsMapper.unidadFamiliarEJG(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));

				LOGGER.info(
						"unidadFamiliarEJG() / scsPersonajgExtendsMapper.unidadFamiliarEJG() -> Salida de scsEjgExtendsMapper para obtener Unidad Familiar");
			} else {
				LOGGER.warn(
						"unidadFamiliarEJG() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
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

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.insertFamiliarEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.insertFamiliarEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.nuevoEstado() -> Entrada para insertar en la unidad familiar del ejg");

				try {
					// [ejg.idInstitucion, justiciable.idpersona, ejg.annio, ejg.tipoEJG,
					// ejg.numero]
					ScsUnidadfamiliarejg familiar = new ScsUnidadfamiliarejg();

					familiar.setIdinstitucion(idInstitucion);
					familiar.setIdpersona(Long.parseLong(item.get(1)));
					familiar.setAnio(Short.parseShort(item.get(2)));
					familiar.setIdtipoejg(Short.parseShort(item.get(3)));
					familiar.setNumero(Long.parseLong(item.get(4)));

					familiar.setUsumodificacion(usuarios.get(0).getIdusuario());
					familiar.setFechamodificacion(new Date());

					familiar.setSolicitante((short) 0);

					response = scsUnidadfamiliarejgMapper.insert(familiar);

					if (response != 1) {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarEstado() -> KO. No se ha introducido ningún familiar en el ejg");
						throw new Exception("ERROR: no se ha podido introducir ningún familiar en el ejg");
					} else {
						responsedto.setStatus(SigaConstants.OK);
					}

					LOGGER.debug(
							"GestionEJGServiceImpl.insertFamiliarEJG() -> Salida del servicio para insertar en la unidad familiar en el ejg");
				} catch (Exception e) {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.debug(
							"GestionEJGServiceImpl.insertFamiliarEJG() -> Se ha producido un error al insertar en la unidad familiar en el ejg.",
							e);
				}
			}
		}
		return responsedto;
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
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				Error error = new Error();

				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getExpedientesEconomicos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"getExpedientesEconomicos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Entrada a scsEjgExtendsMapper para obtener Expedienets Económicos");
				expedienteEconomicoDTO.setExpEconItems(scsExpedienteEconomicoExtendsMapper.getExpedientesEconomicos(
						ejgItem, idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getExpedientesEconomicos() / getExpedientesEconomicos.getExpedientesEconomicos() -> Salida de scsEjgExtendsMapper para obtener Expedienets Económicos");
				if (expedienteEconomicoDTO.getExpEconItems() != null && tamMaximo != null
						&& expedienteEconomicoDTO.getExpEconItems().size() > tamMaximo) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo
							+ " resultados, pero se muestran sólo los " + tamMaximo
							+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					expedienteEconomicoDTO.setError(error);
					// justiciablesItems.remove(justiciablesItems.size()-1);
				}

			} else {
				LOGGER.warn(
						"getExpedientesEconomicos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getExpedientesEconomicos() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return expedienteEconomicoDTO;
	}

	@Override
	public EstadoEjgDTO getEstados(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getEstados() -> Entrada al servicio para obtener el colegiado");
		EstadoEjgDTO estadoEjgDTO = new EstadoEjgDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getEstados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getEstados() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEstadoejgExtendsMapper para obtener los estados del EJG");
				estadoEjgDTO.setEstadoEjgItems(scsEstadoejgExtendsMapper.getEstados(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getEstados() / scsEjgExtendsMapper.getEstados() -> Salida de scsEstadoejgExtendsMapper para obtener los estados del EJG");
			} else {
				LOGGER.warn(
						"getEstados() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
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
			LOGGER.info(
					"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
				LOGGER.info(
						"getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				LOGGER.info(
						"getDocumentos() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

				LOGGER.info(
						"getDocumentos() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
				if (tamMax != null) {
					tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
				} else {
					tamMaximo = null;
				}

				LOGGER.info(
						"getDocumentos() / scsDocumentacionejgExtendsMappe.getDocumentos() -> Entrada a scsEjgExtendsMapper para obtener la documentación de EJG");
				ejgDocumentacionDTO.setEjgDocItems(scsDocumentacionejgExtendsMapper.getDocumentacion(ejgItem,
						idInstitucion.toString(), tamMaximo, usuarios.get(0).getIdlenguaje().toString()));
				LOGGER.info(
						"getDocumentos() / scsDocumentacionejgExtendsMappe.getDocumentos() -> Salida de scsEjgExtendsMapper para obtener la documentación de EJG");
			} else {
				LOGGER.warn(
						"getDocumentos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDocumentos() -> idInstitucion del token nula");
		}

		LOGGER.info("getDocumentos() -> Salida del servicio para obtener la documentación de EJG");
		return ejgDocumentacionDTO;
	}

	@Override
	public EjgItem getDictamen(EjgItem ejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("getDictamen() -> Entrada al servicio para obtener el colegiado");
		EjgItem dictamen = new EjgItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getDictamen() / scsEjgExtendsMapper.getEstados() -> Entrada a scsEjgExtendsMapper para obtener información del Informe de Calificación");
				dictamen = scsEjgExtendsMapper.getDictamen(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());
				LOGGER.info(
						"getDictamen() / scsEjgExtendsMapper.getEstados() -> Salida de scsEjgExtendsMapper para obtener información del Informe de Calificación");
			} else {
				LOGGER.warn(
						"getDictamen() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getDictamen() -> idInstitucion del token nula");
		}
		return dictamen;
	}

	@Override
	public ComboDTO comboOrigen(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboOrigen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Entrada a scsOrigencajgExtendsMapper para rellenar el combo");
				comboItems = scsOrigencajgExtendsMapper.comboOrigen(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());
				LOGGER.info(
						"comboOrigen() / scsOrigencajgExtendsMapper.comboOrigen() -> Salida a scsOrigencajgExtendsMapper para rellenar el combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	public ComboDTO comboActaAnnio(String idActa, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboActaAnnio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Entrada a scsActacomisionExtendsMapper para obtener los combo");
				comboItems = scsActacomisionExtendsMapper.getActaAnnio(idInstitucion.toString(), idActa);
				LOGGER.info(
						"comboActaAnnio() / scsActacomisionExtendsMapper.getActaAnnio() -> Salida a scsActacomisionExtendsMapper para obtener los combo");
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
		// TODO Auto-generated method stub
		LOGGER.info("getResolucion() -> Entrada al servicio para obtener el colegiado");
		ResolucionEJGItem resolucion = new ResolucionEJGItem();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getResolucion() / scsEjgExtendsMapper.getResolucion() -> Entrada a scsEjgExtendsMapper para obtener información de la Resolución");
				resolucion = scsEjgExtendsMapper.getResolucion(ejgItem, idInstitucion.toString(),
						usuarios.get(0).getIdlenguaje().toString());
//				ScsEjgResolucionKey ejgResolucionKey = new ScsEjgResolucionKey();
//				ejgResolucionKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
//				ejgResolucionKey.setIdinstitucion(idInstitucion);
//				ejgResolucionKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
//				ejgResolucionKey.setNumero(Long.valueOf(ejgItem.getNumero()));	
//				
//				resolucion = scsEjgResolucionMapper.selectByPrimaryKey(ejgResolucionKey);

				LOGGER.info(
						"getResolucion() / scsEjgExtendsMapper.getResolucion() -> Salida de scsEjgExtendsMapper para obtener información de la Resolución");
			} else {
				LOGGER.warn(
						"getResolucion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getResolucion() -> idInstitucion del token nula");
		}
		return resolucion;
	}

	@Override
	public ComboDTO comboTipoExpediente(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"comboTipoExpediente() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Entrada a scsTipofundamentosExtendsMapper para obtener los combo");
				comboItems = expExpedienteExtendsMapper.comboTipoExpediente(usuarios.get(0).getIdlenguaje().toString(),
						idInstitucion.toString());
				LOGGER.info(
						"comboTipoExpediente() / expExpedienteExtendsMapper.comboTipoExpediente() -> Salida a scsTipofundamentosExtendsMapper para obtener los combo");
				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}
		}
		LOGGER.info("comboTipoEJG() -> Salida del servicio para obtener los tipos ejg");
		return comboDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO cambioEstadoMasivo(List<EjgItem> datos, HttpServletRequest request) {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Entrada para cambiar los estados y la fecha de estado para los ejgs");

				try {
					for (int i = 0; datos.size() > i; i++) {
						ScsEstadoejg record = new ScsEstadoejg();
						response = 0;

						// creamos el objeto para el insert
						record.setIdinstitucion(idInstitucion);
						record.setIdtipoejg(Short.parseShort(datos.get(i).getTipoEJG()));
						record.setAnio(Short.parseShort(datos.get(i).getAnnio()));
						record.setNumero(Long.parseLong(datos.get(i).getNumero()));
						record.setIdestadoejg(Short.parseShort(datos.get(i).getEstadoNew()));
						record.setFechainicio(datos.get(i).getFechaEstadoNew());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setAutomatico("0");

						// obtenemos el maximo de idestadoporejg
						ScsEstadoejgExample example = new ScsEstadoejgExample();
						example.setOrderByClause("IDESTADOPOREJG DESC");
						example.createCriteria().andAnioEqualTo(Short.parseShort(datos.get(i).getAnnio()))
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdtipoejgEqualTo(Short.parseShort(datos.get(i).getTipoEJG()))
								.andNumeroEqualTo(Long.parseLong(datos.get(i).getNumero()));

						List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

						// damos el varlo al idestadoporejg + 1
						if (listEjg.size() > 0) {
							record.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
						} else {
							record.setIdestadoporejg(Long.parseLong("0"));
						}

						// scsEstadoejgMapper.selectByExample(example)

						response = scsEstadoejgMapper.insert(record);
					}

					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.cambiarEstadoEJGs() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.cambiarEstadoEJGs() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public ResponseEntity<InputStreamResource> descargarExpedientesJG(List<EjgItem> itemEJG,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ResponseEntity<InputStreamResource> response = null;
		List<DatosDocumentoItem> ficheros = new ArrayList<DatosDocumentoItem>();
		HttpHeaders headers = new HttpHeaders();
		File fichero = null;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarExpedientesJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarExpedientesJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarExpedientesJG() -> Generando fichero y creando la descarga...");

				try {
					// recorremos la lista para generar el documento de cada uno de los ejgs
					if (itemEJG != null) {
						for (EjgItem ejg : itemEJG) {
							// obtenemos la peticion y el idXML
							LOGGER.debug(
									"GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos de la petición...");

							List<ScsEejgPeticiones> peticiones = scsEejgPeticionesExtendsMapper
									.getPeticionesPorEJG(ejg);

							if (peticiones != null && peticiones.size() > 0) {

								for (ScsEejgPeticiones peticion : peticiones) {
									// obtenemos los datos del fichero
									LOGGER.debug(
											"GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo datos para el informe...");
									Map<Integer, Map<String, String>> mapInformeEejg = eejgService
											.getDatosInformeEejg(ejg, peticion);

									LOGGER.debug(
											"GestionEJGServiceImpl.descargarExpedientesJG() -> Obteniendo el informe...");
									DatosDocumentoItem documento = eejgService.getInformeEejg(mapInformeEejg,
											ejg.getidInstitucion());

									ficheros.add(documento);
								}
							}

						}
					}

					fichero = WSCommons.zipBytes(ficheros, new File("downloads.zip"));

					String tipoMime = "application/zip";
					headers.setContentType(MediaType.parseMediaType(tipoMime));
					headers.set("Content-Disposition", "attachment; filename=\"" + fichero.getName() + "\"");
					headers.setContentLength(fichero.length());

					InputStream fileStream = new FileInputStream(fichero);
					response = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
							HttpStatus.OK);

					LOGGER.debug("GestionEJGServiceImpl.descargarExpedientesJG() -> Acción realizada correctamente");
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
			LOGGER.debug(
					"GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.insertaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.insertaDatosGenerales() -> Entrada para insertar los datos generales del ejg");

				// Para que @Transactional funcione adecuadamente se comenta el try y el catch
//				try {
				record = setDatosGeneralesEJG(datos);

				// AGREGAMOS DATOS QUE FALTAN EN EL RECORD

				// longitud maxima para num ejg
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("LONGITUD_CODEJG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> listParam = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				String longitudEJG = listParam.get(0).getValor();

				// numejg

				String numEJG = scsEjgExtendsMapper.getNumeroEJG(record.getIdtipoejg(), record.getAnio(),
						record.getIdinstitucion());

				int numCeros = Integer.parseInt(longitudEJG) - numEJG.length();

				String ceros = "";
				for (int i = 0; i < numCeros; i++) {
					ceros += "0";
				}

				ceros += numEJG;

				record.setNumejg(ceros);

				// Repetimos el proceso con numero

				String numero = scsEjgExtendsMapper.getNumero(record.getIdtipoejg(), record.getAnio(),
						record.getIdinstitucion());

				numCeros = Integer.parseInt(longitudEJG) - numero.length();

				ceros = "";
				for (int i = 0; i < numCeros; i++) {
					ceros += "0";
				}

				ceros += numero;

				record.setNumero(Long.parseLong(ceros));

				// Determinamos el origen de apertura ya que, aunque no sea una clave primaria,
				// no se permita que tenga valor null.
				record.setOrigenapertura("M");

				// Sucede lo mismo
				record.setTipoletrado("M");
				record.setFechacreacion(new Date());
				record.setFechamodificacion(new Date());
				record.setUsucreacion(usuarios.get(0).getIdusuario());
				record.setUsumodificacion(usuarios.get(0).getIdusuario());

				// Campos opcionales
				record.setFechapresentacion(datos.getFechapresentacion());
				record.setFechalimitepresentacion(datos.getFechalimitepresentacion());
				if (datos.getTipoEJGColegio() != null)
					record.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));

				// Campo de prestaciones
				if (datos.getPrestacionesRechazadas() != null) {
					ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();

					preRe.setIdinstitucion(idInstitucion);
					preRe.setAnio(Short.parseShort(datos.getAnnio()));
					preRe.setNumero(record.getNumero());
					preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
					preRe.setFechamodificacion(new Date());
					preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));

					for (String idprestacion : datos.getPrestacionesRechazadas()) {
						preRe.setIdprestacion(Short.parseShort(idprestacion));

						response = scsEjgPrestacionRechazadaMapper.insert(preRe);
						if (response == 0)
							throw (new Exception("Error al insertar las prestaciones rechazadas del EJG"));
					}
				}

				response = scsEjgMapper.insert(record);
				if (response == 0)
					throw (new Exception("Error al insertar los datos generales del EJG"));

				// Se ejecuta este método que sustituye los triggers de la base de datos
				// al insertar una nueva fila en la tabla SCS_EJG por codigo java.
				this.triggersEjgInsert(record, usuarios.get(0), idInstitucion);

//				} catch (Exception e) {
//					LOGGER.error(
//							"GestionEJGServiceImpl.insertaDatosGenerales(). ERROR: al hacer el insert de datos generales. ",
//							e);
//					response = 0;
//				}
				// respuesta si se actualiza correctamente para que se rellene el campo de
				// numero
				if (response >= 1) {
					List<EjgItem> list = new ArrayList<>();

					EjgItem item = new EjgItem();

					item.seteidInstitucion(idInstitucion.toString());
					item.setAnnio(datos.getAnnio());
					item.setNumEjg(record.getNumejg());
					item.setNumero(record.getNumero().toString());
					item.setTipoEJG(datos.getTipoEJG());

					// Campos opcionales
					item.setFechapresentacion(datos.getFechapresentacion());
					item.setFechalimitepresentacion(datos.getFechalimitepresentacion());
					item.setTipoEJGColegio(datos.getTipoEJGColegio());
					item.setIdTipoExpInsos(datos.getIdTipoExpInsos());

					list.add(item);

					ejgdto.setEjgItems(list);
					// responsedto.setStatus(SigaConstants.OK);
					error.setCode(200);
					LOGGER.debug("GestionEJGServiceImpl.insertaDatosGenerales() -> OK. Datos generales insertados");
				} else {
					// responsedto.setStatus(SigaConstants.KO);
					error.setCode(400);
					LOGGER.error(
							"GestionEJGServiceImpl.insertaDatosGenerales() -> KO. No se ha insertado los datos generales");
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

					example.createCriteria().andAnioejgEqualTo(Short.parseShort(datos.getAnnio()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdtipoejgEqualTo(Short.parseShort(datos.getTipoEJG()))
							.andNumeroejgEqualTo(Long.parseLong(datos.getNumero()));

					List<ScsEjgdesigna> ejgDesignas = scsEjgdesignaMapper.selectByExample(example);

					ejgDesignaDTO.setScsEjgdesignas(ejgDesignas);

					LOGGER.info(
							"getEjgDesigna() / scsEjgdesignaMapper.selectByExample() -> Salida de scsEjgExtendsMapper para obtener asociaciones con designaciones del EJG.");
				} catch (Exception e) {
					LOGGER.debug(
							"getEjgDesigna() -> Se ha producido un error al obtener asociaciones con designaciones del EJG. ",
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

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.info(
					"GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> Entrada para actualizar los datos generales del ejg");

//				try {
				// for (int i = 0; datos.get) > i; i++) {
				response = 0;

				// Clave primaria scsEstadoejg
				// IDINSTITUCION
				// IDTIPOEJG
				// ANIO
				// NUMERO
				// IDESTADOPOREJG

				// seleccionamos el objeto para el update

				ScsEjgKey ejgKey = new ScsEjgKey();

				ejgKey.setIdinstitucion(idInstitucion);
				ejgKey.setAnio(Short.parseShort(datos.getAnnio()));
				ejgKey.setNumero(Long.parseLong(datos.getNumero()));
				ejgKey.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
				// key.setIdestadoporejg(Long.parseLong(datos.getEstadoEJG()));

				ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

				// Modificamos el objeto obtenido

				ejg.setFechaapertura(datos.getFechaApertura());
				ejg.setFechapresentacion(datos.getFechapresentacion());
				ejg.setFechalimitepresentacion(datos.getFechalimitepresentacion());
				if (datos.getTipoEJGColegio() != null)
					ejg.setIdtipoejgcolegio(Short.parseShort(datos.getTipoEJGColegio()));
				ejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				ejg.setFechamodificacion(new Date());

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				this.triggersEjgUpdatesFApertura(datos, usuarios.get(0), idInstitucion);

				// Actualizamos la entrada en la BBDD
				response = scsEjgMapper.updateByPrimaryKeySelective(ejg);
				if (response == 0)
				throw (new Exception("Error al actualizar los datos generales del EJG"));

				// Actualizar el expediente del que se extrae el tipo de expediente

				ExpExpedienteKey expKey = new ExpExpedienteKey();

				expKey.setIdinstitucion(idInstitucion);
				if (datos.getAnioexpInsos() != null)
					expKey.setAnioexpediente(Short.parseShort(datos.getAnioexpInsos()));
				if (datos.getNumeroexpInsos() != null)
					expKey.setNumeroexpediente(Integer.parseInt(datos.getNumeroexpInsos()));
				if (datos.getIdTipoExpInsos() != null)
					expKey.setIdtipoexpediente(Short.parseShort(datos.getIdTipoExpInsos()));
				if (datos.getIdInstTipoExp() != null)
					expKey.setIdinstitucionTipoexpediente(Short.parseShort(datos.getIdInstTipoExp()));

				ExpExpediente newExp = expExpedienteMapper.selectByPrimaryKey(expKey);

				if (newExp != null) {
					if (datos.getTipoEJG() != null)
						newExp.setIdtipoexpediente(Short.parseShort(datos.getTipoEJG()));
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
				if (datos.getPrestacionesRechazadas() != null) {
					ScsEjgPrestacionRechazada preRe = new ScsEjgPrestacionRechazada();

					preRe.setIdinstitucion(idInstitucion);
					preRe.setAnio(Short.parseShort(datos.getAnnio()));
					preRe.setNumero(Long.parseLong(datos.getNumero()));
					preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
					preRe.setUsumodificacion(usuarios.get(0).getIdusuario());
					preRe.setFechamodificacion(new Date());
					preRe.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));

					ScsEjgPrestacionRechazadaExample examplePresRe = new ScsEjgPrestacionRechazadaExample();

					examplePresRe.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andAnioEqualTo(preRe.getAnio()).andNumeroEqualTo(preRe.getNumero())
							.andIdtipoejgEqualTo(preRe.getIdtipoejg()).andIdtipoejgEqualTo(preRe.getIdtipoejg());

					List<ScsEjgPrestacionRechazada> rechazadas = scsEjgPrestacionRechazadaMapper
							.selectByExample(examplePresRe);

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

				}

				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> Salida del servicio para actualizar los datos generales del ejg");

//				} catch (Exception e) {
//					LOGGER.info(
//							"GestionEJGServiceImpl.actualizaDatosGenerales() -> Se ha producido un error al actualizar los datos generales del ejg",
//							e);
//					response = 0;
//				}
			}

			// respuesta si se actualiza correctamente
			if (response >= 1) {
				responsedto.setStatus(SigaConstants.OK);
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> OK. Datos Generales actualizados para el ejg");
			} else {
				responsedto.setStatus(SigaConstants.KO);
				LOGGER.info(
						"GestionEJGServiceImpl.actualizaDatosGenerales() -> KO. No se ha actualizado ningún dato general del ejg");
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
		if (item.getTipoEJG() != null) {

			result.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
		}

		if (item.getTipoEJGColegio() != null) {
			result.setIdtipoejgcolegio(Short.parseShort(item.getTipoEJGColegio()));
		}

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
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarEstado() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarEstado() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarEstado() -> Entrada para cambiar los datos generales del ejg");

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
						response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);

						if (response != 1) {
							responsedto.setStatus(SigaConstants.KO);
							LOGGER.error(
									"GestionEJGServiceImpl.borrarEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
							throw new Exception("ERROR: no se ha podido ");
						} else {
							responsedto.setStatus(SigaConstants.OK);
						}
					}
					LOGGER.debug(
							"GestionEJGServiceImpl.cambiarEstadoEJGs() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {

				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO borrarFamiliar(List<UnidadFamiliarEJGItem> datos, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarFamiliar() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarFamiliar() -> Entrada para cambiar los datos generales del ejg");
				// Comentamos el try y el catch para que el @Transactional funcione
				// correctamente
//				try {
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

							ejg.setIdpersonajg(null);

							response = scsEjgMapper.updateByPrimaryKey(ejg);
							if (response == 0)
								throw (new Exception("Error al actualizar eliminar el solicitante principal del EJG"));
						}
					} else {
						record.setFechabaja(null);
					}

					record.setEncalidadde(null);

					response = scsUnidadfamiliarejgMapper.updateByPrimaryKey(record);
					if (response == 0)
						throw (new Exception("Error al actualizar los datos generales del EJG"));

				}
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio para borrar familiares del ejg");
//				} catch (Exception e) {
//					LOGGER.debug(
//							"GestionEJGServiceImpl.borrarFamiliar() -> Se ha producido un error al borrar familiares del ejg",
//							e);
//					response = 0;
//				} finally {
				// respuesta si se actualiza correctamente
				if (response >= 1) {
					responsedto.setStatus(SigaConstants.OK);
					LOGGER.debug("GestionEJGServiceImpl.borrarFamiliar() -> OK.");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error("GestionEJGServiceImpl.borrarFamiliar() -> KO.");
				}
//				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarFamiliar() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
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
				LOGGER.debug("GestionEJGServiceImpl.nuevoEstado() -> Entrada para cambiar los datos generales del ejg");

				try {

					ScsEstadoejg record = new ScsEstadoejg();
					response = 0;

					// creamos el objeto para el insert
					record.setIdinstitucion(idInstitucion);
					record.setAnio(Short.parseShort(datos.getAnio()));
					record.setNumero(Long.parseLong(datos.getNumero()));

					record.setIdestadoejg(Short.parseShort(datos.getIdEstadoejg()));
					record.setFechainicio(datos.getFechaInicio());
					record.setObservaciones(datos.getObservaciones());

					record.setIdtipoejg(Short.parseShort(datos.getIdtipoejg()));

					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());

					// obtenemos el maximo de idestadoporejg
					ScsEstadoejgExample example = new ScsEstadoejgExample();
					example.setOrderByClause("IDESTADOPOREJG DESC");
					example.createCriteria().andAnioEqualTo(Short.parseShort(datos.getAnio()))
							.andIdinstitucionEqualTo(idInstitucion)
							.andIdtipoejgEqualTo(Short.parseShort(datos.getIdtipoejg()))
							.andNumeroEqualTo(Long.parseLong(datos.getNumero()));

					List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

					// damos el varlo al idestadoporejg + 1
					if (listEjg.size() > 0) {
						record.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
					} else {
						record.setIdestadoporejg(Long.parseLong("0"));
					}

					response = scsEstadoejgMapper.insertSelective(record);

					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.nuevoEstado() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.nuevoEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarEstado() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public UpdateResponseDTO editarEstado(EstadoEjgItem datos, HttpServletRequest request) {
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

				try {

					ScsEstadoejg record = new ScsEstadoejg();
					response = 0;

					record.setIdinstitucion(idInstitucion);
					record.setAnio(Short.parseShort(datos.getAnio()));
					record.setNumero(Long.parseLong(datos.getNumero()));

					record.setIdestadoejg(Short.parseShort(datos.getIdEstadoejg()));
					record.setFechainicio(datos.getFechaInicio());
					record.setObservaciones(datos.getObservaciones());

					record.setIdtipoejg(Short.parseShort(datos.getIdtipoejg()));

					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setIdestadoporejg(Long.parseLong(datos.getIdestadoporejg()));

					response = scsEstadoejgMapper.updateByPrimaryKeySelective(record);

					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.nuevoEstado() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.nuevoEstado() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.nuevoEstado() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
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
		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarImpugnacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarImpugnacion() -> Entrada para cambiar los datos generales del ejg");

				// Comentamos el try y el catch para que el @Transactional funcione
				// correctamente
//				try {

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
				this.triggersEjgUpdatesImpug(datos, usuarios.get(0), idInstitucion);

				response = scsEjgMapper.updateByPrimaryKeyWithBLOBs(scsEjgWithBLOBs);

//				} catch (Exception e) {
//
//					LOGGER.error(e);
//					LOGGER.error(
//							"GestionEJGServiceImpl.guardarImpugnacion() -> Se ha producido un error al actualizar la Impugnacion",
//							e);
//					error.setCode(500);
//					error.setDescription("general.mensaje.error.bbdd");
//					error.setMessage(e.getMessage());
//					responsedto.setStatus(SigaConstants.OK);
//					} 
				// respuesta si se actualiza correctamente
				if (response == 1) {
					responsedto.setStatus(SigaConstants.OK);
					error.setCode(200);
					LOGGER.debug("GestionEJGServiceImpl.guardarImpugnacion() -> OK.  Impugnacion");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					LOGGER.error(
							"GestionEJGServiceImpl.guardarImpugnacion() -> KO. Se ha producido un error al actualizar la Impugnacion");
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
			LOGGER.debug(
					"GestionEJGServiceImpl.guardarResolucion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.guardarResolucion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.guardarResolucion() -> Entrada para cambiar los datos de resolucion del EJG");
				// Para que la etiqueta @Transactional funcione adecuadamente debe recibir una
				// excepcion
//				try {

				// 1. Se selecciona el EJG asociado y se actualiza según los datos introducidos
				// en la tarjeta "resolucion"
				ScsEjgKey ejgKey = new ScsEjgKey();

				ejgKey.setAnio(datos.getAnio());
				ejgKey.setIdinstitucion(idInstitucion);
				ejgKey.setIdtipoejg(datos.getIdTipoEJG());
				ejgKey.setNumero(datos.getNumero());

				ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

				ejg.setIdacta(datos.getIdActa());
				ejg.setAnioacta(datos.getAnnioActa());
				if (datos.getIdActa() == null)
					ejg.setIdinstitucionacta(null);
				else
					ejg.setIdinstitucionacta(idInstitucion);
				ejg.setFecharesolucioncajg(datos.getFechaResolucionCAJG());
				// Como se indica en el documento tecnico.
				ejg.setIdtiporatificacionejg(datos.getIdTiporatificacionEJG());
				ejg.setIdfundamentojuridico(datos.getIdFundamentoJuridico());
				ejg.setAniocajg(datos.getAnioCAJG());
				ejg.setNumeroCajg(datos.getNumeroCAJG());

				ejg.setIdponente(datos.getIdPonente());
				ejg.setFechapresentacionponente(datos.getFechaPresentacionPonente());
				ejg.setFecharesolucioncajg(datos.getFechaResolucionCAJG());
				ejg.setFecharatificacion(datos.getFechaRatificacion());
				ejg.setFechanotificacion(datos.getFechaNotificacion());
				ejg.setIdorigencajg(datos.getIdOrigencajg());
				ejg.setRefauto(datos.getRefAuto());
				if (datos.getTurnadoRatificacion() == "true") {
					ejg.setTurnadoratificacion("1");
				} else
					ejg.setTurnadoratificacion("0");
				if (datos.getRequiereNotificarProc() == "true") {
					ejg.setRequierenotificarproc("1");
				} else
					ejg.setRequierenotificarproc("0");

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				this.triggersEjgUpdatesResol(datos, usuarios.get(0), idInstitucion);

				response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar la parte de la resolucion del EJG"));

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

				ejgResolucion.setIdacta(datos.getIdActa());
				// Se realiza esta asignacion ya que actualmente no se puede asignar EJGs a
				// actas que no sean de la misma institucion.
				ejgResolucion.setIdinstitucionacta(idInstitucion);
				ejgResolucion.setAnioacta(datos.getAnnioActa());
				ejgResolucion.setFecharesolucioncajg(datos.getFechaResolucionCAJG());
				ejgResolucion.setIdtiporatificacionejg(datos.getIdTiporatificacionEJG());
				ejgResolucion.setIdfundamentojuridico(datos.getIdFundamentoJuridico());
				ejgResolucion.setAniocajg(datos.getAnioCAJG());
				ejgResolucion.setNumeroCajg(datos.getNumeroCAJG());

				ejgResolucion.setRatificaciondictamen(datos.getRatificacionDictamen());
				ejgResolucion.setNotascajg(datos.getNotasCAJG());

				ejgResolucion.setIdponente(datos.getIdPonente());
				ejgResolucion.setFechapresentacionponente(datos.getFechaPresentacionPonente());
				// Actualmente el combo de ponentes tienen el mismo idInstitucion que el EJG por
				// lo que se le asigna el mismo valor.
				ejgResolucion.setIdinstitucionponente(idInstitucion);

				ejgResolucion.setFechanotificacion(datos.getFechaNotificacion());
				ejgResolucion.setFecharatificacion(datos.getFechaRatificacion());
				ejgResolucion.setIdorigencajg(datos.getIdOrigencajg());
				ejgResolucion.setRefauto(datos.getRefAuto());
				if (datos.getTurnadoRatificacion() == "true") {
					ejgResolucion.setTurnadoratificacion("1");
				} else
					ejgResolucion.setTurnadoratificacion("0");
				if (datos.getRequiereNotificarProc() == "true") {
					ejgResolucion.setRequierenotificarproc("1");
				} else
					ejgResolucion.setRequierenotificarproc("0");

				ejgResolucion.setFechamodificacion(new Date());
				ejgResolucion.setUsumodificacion(usuarios.get(0).getIdusuario());

				if (nuevo)
					response = scsEjgResolucionMapper.insertSelective(ejgResolucion);
				else
					response = scsEjgResolucionMapper.updateByPrimaryKey(ejgResolucion);
				if (response == 0)
					throw (new Exception("Error al insertar o actualizar la parte de la resolucion asociada al EJG"));

				// 3. ASOCIAR EL EJG CON LA ACTA

				if (ejg.getIdacta() != null) {

					// Se comprueba si el EJG ya esta relacionado con la acta
					ScsEjgActaExample exampleRelacion = new ScsEjgActaExample();

					exampleRelacion.createCriteria().andAnioejgEqualTo(ejg.getAnio())
							.andIdinstitucionejgEqualTo(idInstitucion).andIdtipoejgEqualTo(ejg.getIdtipoejg())
							.andNumeroejgEqualTo(ejg.getNumero());

					List<ScsEjgActa> relacionExistente = scsEjgActaMapper.selectByExample(exampleRelacion);
					LOGGER.info(
							"GestionEJGServiceImpl.guardarResolucion() -> Se inicia la asociacion entre el EJG y el acta.");
					// En el caso que no exista una relacion entre la acta seleccionada y el EJG
					if (relacionExistente.isEmpty()) {

						ScsEjgActa relacion = new ScsEjgActa();

						relacion.setIdinstitucionejg(idInstitucion);
						relacion.setAnioejg(ejg.getAnio());
						relacion.setNumeroejg(ejg.getNumero());
						relacion.setIdtipoejg(ejg.getIdtipoejg());

						relacion.setIdinstitucionacta(idInstitucion);
						relacion.setIdacta(ejg.getIdacta());
						relacion.setAnioacta(ejg.getAnioacta());

						relacion.setIdfundamentojuridico(datos.getIdFundamentoJuridico());
						relacion.setIdtiporatificacionejg(datos.getIdTiporatificacionEJG());

						relacion.setFechamodificacion(new Date());
						relacion.setUsumodificacion(usuarios.get(0).getIdusuario());

						response = scsEjgActaMapper.insert(relacion);
						if (response == 0)
							throw (new Exception("Error al insertar la relacion entre la acta y el EJG"));
					}

					LOGGER.info(
							"GestionEJGServiceImpl.guardarResolucion() -> Se finaliza la asociacion entre el EJG y la acta.");
				}
				// Para que la etiqueta @Transactional funcione adecuadamente debe recibir una
				// excepcion
//				} catch (Exception e) {
//					
//					Error error = new Error();
//					error.setCode(500);
//					error.setDescription("general.mensaje.error.bbdd");
//					error.setMessage(e.getMessage());
//					responsedto.setError(error);
//					response = 2;
//					responsedto.setStatus(SigaConstants.KO);
//				}

				if (response == 1) {
					LOGGER.debug(
							"GestionEJGServiceImpl.guardarResolucion() -> OK. Datos de resolucion cambiados en el EJG");
					responsedto.setStatus(SigaConstants.OK);
					Error error = new Error();
					error.setCode(200);
					responsedto.setError(error);
				}

				if (response == 0) {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error(
							"GestionEJGServiceImpl.guardarResolucion() -> KO. No se ha actualizado ningún dato de resolucion en el EJG");
					Error error = new Error();
					error.setCode(500);
					error.setDescription("general.mensaje.error.bbdd");
					responsedto.setError(error);
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.guardarResolucion() -> Salida del servicio.");

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
			LOGGER.info(
					"getHabilitarActa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getHabilitarActa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getHabilitarActa() / scsSituacionesExtendsMapper.comboSituaciones() -> Entrada a scsSituacionesExtendsMapper para obtener el combo de situaciones");

				GenParametrosKey genKey = new GenParametrosKey();

				genKey.setIdinstitucion(idInstitucion);
				genKey.setParametro("HABILITA_ACTAS_COMISION");
				genKey.setModulo("SCS");

				GenParametros parametro = genParametrosMapper.selectByPrimaryKey(genKey);

				if (parametro != null)
					habilitar = true;
				else
					habilitar = false;

				LOGGER.info(
						"getHabilitarActa() / scsSituacionesExtendsMapper.comboSituaciones() -> Salida a scsSituacionesExtendsMapper para obtener el combo de situaciones");
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

		ScsEjgWithBLOBs record = new ScsEjgWithBLOBs();

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.descargarInformeCalificacion() -> Entrada para cambiar los datos generales del ejg");

				try {

				} catch (Exception e) {

				} finally {
					// respuesta si se actualiza correctamente
					if (response >= 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.descargarInformeCalificacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.descargarInformeCalificacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
					}
				}
			}
		}

		LOGGER.info("GestionEJGServiceImpl.descargarInformeCalificacion() -> Salida del servicio.");

		return responsedto;
	}

	@Override
	@Transactional
	public DeleteResponseDTO borrarRelacion(RelacionesItem datos, HttpServletRequest request) {
		DeleteResponseDTO responsedto = new DeleteResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String anio = datos.getAnio();
					String num = datos.getNumero();
					String idTurno = datos.getIdturno();
					String institucion = datos.getIdinstitucion();
					response = scsDesignacionesExtendsMapper.eliminarRelacion(anio, num, idTurno, institucion);

					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
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
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String idinstitucion = datos.getIdinstitucion();
					String numero = datos.getNumero();
					String anio = datos.getAnio();

					response = scsAsistenciaExtendsMapper.eliminarRelacionAsistencia(idinstitucion, anio, numero);

					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
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
			LOGGER.debug(
					"GestionEJGServiceImpl.borrarRelacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.borrarRelacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.borrarRelacion() -> Entrada para cambiar los datos generales del ejg");

				try {

					String idinstitucion = datos.getIdinstitucion();
					String numero = datos.getNumero();
					String anio = datos.getAnio();
					String tipoSoj = datos.getIdtipo();

					response = scsSojExtendsMapper.eliminarRelacionSoj(idinstitucion, anio, numero, tipoSoj);

					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio para cambiar los estados y la fecha de estados para los ejgs");
				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.borrarRelacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
				} finally {
					// respuesta si se actualiza correctamente
					if (response == 1) {
						responsedto.setStatus(SigaConstants.OK);
						LOGGER.debug(
								"GestionEJGServiceImpl.borrarRelacion() -> OK. Estado y fecha actualizados para los ejgs seleccionados");
					} else {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.borrarRelacion() -> KO. No se ha actualizado ningún estado y fecha para los ejgs seleccionados");
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
		int response2 = 0;
		int response3 = 1;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.updateDatosJuridicos() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.updateDatosJuridicos() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.updateDatosJuridicos() -> Entrada para cambiar los datos generales del ejg");

				// Se comenta el try y el catch para que @Transactional funcione correctamente
//				try {

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

				// Datos pre-designacion
				record.setNumeroprocedimiento(dni);
				record.setAnioprocedimiento(idInstitucion);
				record.setNig(datos.getNig());
				if (datos.getJuzgado() != null)
					record.setJuzgado(Long.parseLong(datos.getJuzgado()));
				else
					record.setJuzgado(null);
				record.setIdpretension(datos.getIdPretension());
				record.setObservaciones(datos.getObservaciones());
				record.setDelitos(datos.getDelitos());

				response = scsEjgMapper.updateByPrimaryKey(record);
				if (response == 0)
					throw (new Exception(
							"Error al actualizar la defensa juridica de la ficha pre-designacion del EJG"));

				// Actualizamos los delitos del ejg
				ScsDelitosejg delitos = new ScsDelitosejg();

				delitos.setIdinstitucion(idInstitucion);
				delitos.setAnio(Short.parseShort(datos.getAnnio()));
				delitos.setNumero(Long.parseLong(datos.getNumero()));
				delitos.setIdtipoejg(Short.parseShort(datos.getTipoEJG()));
				delitos.setUsumodificacion(usuarios.get(0).getIdusuario());
				delitos.setFechamodificacion(new Date());

				ScsDelitosejgExample oldDelitos = new ScsDelitosejgExample();

				oldDelitos.createCriteria().andIdinstitucionEqualTo(idInstitucion).andAnioEqualTo(delitos.getAnio())
						.andNumeroEqualTo(delitos.getNumero()).andIdtipoejgEqualTo(delitos.getIdtipoejg());

				List<ScsDelitosejg> oldDel = scsDelitosejgMapper.selectByExample(oldDelitos);

				if (oldDel.isEmpty())
					response2 = 1;
				else {
					// Eliminamos las entradas existentes relacionadas si las hubiera.

					response = scsDelitosejgMapper.deleteByExample(oldDelitos);

					if (response == 0)
						throw (new Exception(
								"Error al eliminar los delitos asociados a la ficha pre-designacion del EJG"));
				}
				if (datos.getDelitos() != null) {
					String[] idDelitos = datos.getDelitos().split(",");
					for (String idDelito : idDelitos) {
						delitos.setIddelito(Short.parseShort(idDelito));
						response = scsDelitosejgMapper.insert(delitos);
						if (response == 0)
							throw (new Exception(
									"Error al insertar los delitos asociados a la ficha pre-designacion del EJG"));
					}
				}

				LOGGER.debug(
						"GestionEJGServiceImpl.updateDatosJuridicos() -> Salida del servicio para cambiar los datos juridicos para el ejg");
//				} catch (Exception e) {
//					LOGGER.debug(
//							"GestionEJGServiceImpl.updateDatosJuridicos() -> Se ha producido un error al actualizar los datos juridicos para el ejg. ",
//							e);
//					response = 0;
//				} 
				// respuesta si se actualiza correctamente
				if (response == 1) {
					responsedto.setStatus(SigaConstants.OK);
					LOGGER.debug(
							"GestionEJGServiceImpl.updateDatosJuridicos() -> OK. Datos juridicos del ejg actualizados");
				} else {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.error(
							"GestionEJGServiceImpl.updateDatosJuridicos() -> KO. No se ha actualizado ningúno de los datos juridicos para el ejg");
				}

			}
		}

		LOGGER.info("GestionEJGServiceImpl.borrarRelacion() -> Salida del servicio.");

		return responsedto;
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

			LOGGER.info(
					"busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaComunicaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.debug(
						"busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las comunicaciones");

				// obtenemos los datos de la comunicacion
				enviosMasivosItem = scsEjgExtendsMapper.getComunicaciones(item.getNumEjg(), item.getAnnio(),
						item.getTipoEJG(), idInstitucion, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"busquedaComunicaciones() / scsDesignacionesExtendsMapper.busquedaComunicaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las comunicaciones");

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

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getRelacionesEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getRelacionesEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getRelacionesEJG() / scsProcuradorExtendsMapper.busquedaRelaciones() -> Entrada a scsDesignacionesExtendsMapper para obtener las relaciones");

				relacionesItem = scsEjgExtendsMapper.getRelacionesEJG(item);

				LOGGER.info(
						"busquedaRelaciones() / scsDesignacionesExtendsMapper.busquedaRelaciones() -> Salida a scsDesignacionesExtendsMapper para obtener las relaciones");

				if (relacionesItem != null) {

					relacionesDTO.setRelacionesItem(relacionesItem);
				}
			}

		}
		LOGGER.info("getRelacionesEJG()) -> Salida del servicio para obtener relaciones");
		return relacionesDTO;
	}

	@Override
	public List<ListaContrarioEJGJusticiableItem> busquedaListaContrariosEJG(EjgItem item, HttpServletRequest request,
			Boolean historico) {
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

			LOGGER.info(
					"GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.busquedaListaContrariosEJG() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			// GenParametrosExample genParametrosExample = new GenParametrosExample();
			// genParametrosExample.createCriteria().andModuloEqualTo("CEN")
			// .andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO")
			// .andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT,
			// idInstitucion));
			// genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			// LOGGER.info(
			// "GestionEJGServiceImpl.busquedaListaContrarios() -> Entrada a
			// genParametrosExtendsMapper para obtener tamaño máximo consulta");
			// tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			// LOGGER.info(
			// "GestionEJGServiceImpl.busquedaListaContrarios() -> Salida a
			// genParametrosExtendsMapper para obtener tamaño máximo consulta");
			// if (tamMax != null) {
			// tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			// } else {
			// tamMaximo = null;
			// }

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"GestionEJGServiceImpl.busquedaListaContrariosEJG -> Entrada a servicio para la busqueda de contrarios en EJG");

				try {
					contrarios = scsContrariosejgExtendsMapper.busquedaListaContrariosEJG(item, idInstitucion,
							historico);
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

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteContrario() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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

					LOGGER.info(
							"deleteContrario() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"deleteContrario() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosDesignaMapper para eliminar los contrarios seleccionados");

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

			LOGGER.info(
					"insertContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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

					LOGGER.info(
							"insertContrarioEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Entrada a scsPersonajgExtendsMapper para obtener justiciables");

					ScsPersonajgKey scsPersonajgkey = new ScsPersonajgKey();
					scsPersonajgkey.setIdpersona(Long.valueOf(item.getIdpersona()));
					scsPersonajgkey.setIdinstitucion(idInstitucion);

					ScsPersonajg personajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

					LOGGER.info(
							"insertContrarioEJG() / scsPersonajgExtendsMapper.selectByPrimaryKey() -> Salida a scsPersonajgExtendsMapper para obtener justiciable");

					// Se comprueba si tiene representante y se busca.
					if (personajg.getIdrepresentantejg() != null) {

						scsPersonajgkey.setIdpersona(personajg.getIdrepresentantejg());

						ScsPersonajg representante = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajgkey);

						contrario.setNombrerepresentanteejg(representante.getApellido1() + " "
								+ representante.getApellido2() + ", " + representante.getNombre());
					}

					LOGGER.info(
							"insertContrarioEJG() / scsContrariosejgMapper.insert() -> Entrada a ScsDefendidosdesignaMapper para insertar contrario ejg");

					response = scsContrariosejgMapper.insert(contrario);

					LOGGER.info(
							"insertContrarioEJG() / scsContrariosejgMapper.insert() -> Salida de ScsDefendidosdesignaMapper para insertar contrario ejg");

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

			LOGGER.info(
					"updateRepresentanteContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateRepresentanteContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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

					contrario.setFechamodificacion(new Date());
					contrario.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"updateRepresentanteContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosejgMapper para actualizar el representante de un contrario ejg.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateRepresentanteContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosejgMapper para actualizar el representante de un contrario ejg.");

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

		LOGGER.info(
				"updateRepresentanteContrarioEJG() -> Salida del servicio para actualizar el representante de un contrario ejg");

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

			LOGGER.info(
					"updateAbogadoContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAbogadoContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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

					LOGGER.info(
							"updateAbogadoContrarioEJG() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Entrada a scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateAbogadoContrarioEJG() / scsDefendidosdesignaMapper.updateByPrimaryKey() -> Salida de scsDefendidosdesignaMapper para actualizar el representante de un interesado.");

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

			LOGGER.info(
					"updateProcuradorContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateProcuradorContrarioEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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

					LOGGER.info(
							"updateProcuradorContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Entrada a scsContrariosejgMapper para actualizar el representante de un interesado.");

					response = scsContrariosejgMapper.updateByPrimaryKey(contrario);

					LOGGER.info(
							"updateProcuradorContrarioEJG() / scsContrariosejgMapper.updateByPrimaryKey() -> Salida de scsContrariosejgMapper para actualizar el representante de un interesado.");

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

			LOGGER.info(
					"busquedaProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaProcuradorEJG() / scsEjgExtendsMapper.busquedaProcuradorEJG() -> Entrada a scsEjgExtendsMapper para obtener los procuradores");

				procuradorItemList = scsEjgExtendsMapper.busquedaProcuradorEJG(ejg.getIdProcurador(),
						idInstitucion.toString());

				LOGGER.info(
						"busquedaProcuradorEJG() / scsEjgExtendsMapper.busquedaProcuradorEJG -> Salida a scsEjgExtendsMapper para obtener los procuradores");

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
		int response1 = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarProcuradorEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				// Comentamos el try y el catch para que @Transactional funcione correctamente
//				try {

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

				LOGGER.info(
						"guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKey() -> Entrada a scsEjgMapper para guardar procurador EJG.");

				response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar el procurador de la ficha pre-designacion del EJG"));

				LOGGER.info(
						"guardarProcuradorEJG() / scsEjgMapper.updateByPrimaryKey() -> Salida de scsEjgMapper para guardar procurador EJG.");

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
				if (response == 0)
					throw (new Exception("Error al introducir el nuevo estado al cambiar el procurador del EJG"));

//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription(e.getMessage());
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
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

			LOGGER.info(
					"getDelitosEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getDelitosEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.debug(
						"getDelitosEjg() / scsDelitosejgMapper.selectByExample() -> Entrada a scsDelitosejgMapper para obtener los delitos ejg");

				// Obtenemos los delitos ejg que esten asignados a nuestro EJG

				ScsDelitosejgExample example = new ScsDelitosejgExample();

				example.createCriteria().andAnioEqualTo(Short.parseShort(item.getAnnio()))
						.andIdinstitucionEqualTo(idInstitucion).andNumeroEqualTo(Long.parseLong(item.getNumero()))
						.andIdtipoejgEqualTo(Short.parseShort(item.getTipoEJG()));

				delitosEjgItem = scsDelitosejgMapper.selectByExample(example);

				LOGGER.info(
						"getDelitosEjg() / scsDelitosejgMapper.selectByExample() -> Salida a scsDelitosejgMapper para obtener los delitos ejg");

				if (delitosEjgItem != null) {
					delitosEjgDTO.setDelitosEjgItem(delitosEjgItem);
				}
			}

		}
		LOGGER.info("getDelitosEjg() -> Salida del servicio para obtener delitos ejg");

		return delitosEjgDTO;
	}

//	@Override
//	@Transactional
//	public InsertResponseDTO actualizarDelitosEJG(EjgItem item, HttpServletRequest request) {
//		LOGGER.info("actualizarDelitosEJG() -> Entrada al servicio para insertar delitos ejg");
//		InsertResponseDTO responsedto = new InsertResponseDTO();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (idInstitucion != null) {
//			LOGGER.debug(
//					"GestionEJGServiceImpl.actualizarDelitosEJG() -> Entrada para obtener información del usuario logeado");
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.debug(
//					"GestionEJGServiceImpl.actualizarDelitosEJG() -> Salida de obtener información del usuario logeado");
//
//			if (usuarios != null && usuarios.size() > 0) {
//				LOGGER.debug("GestionEJGServiceImpl.actualizarDelitosEJG() -> Entrada para insertar delitos ejg");
//
//				try {
//					ScsDelitosejg delitos = new ScsDelitosejg();
//
//					delitos.setIdinstitucion(idInstitucion);
//					delitos.setAnio(Short.parseShort(item.getAnnio()));
//					delitos.setNumero(Long.parseLong(item.getNumero()));
//					delitos.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
//					delitos.setUsumodificacion(usuarios.get(0).getIdusuario());
//					delitos.setFechamodificacion(new Date());
//
//					ScsDelitosejgExample oldDelitos = new ScsDelitosejgExample();
//
//					oldDelitos.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//					.andAnioEqualTo(delitos.getAnio()).andNumeroEqualTo(delitos.getNumero())
//					.andIdtipoejgEqualTo(delitos.getIdtipoejg());
//
//					// Eliminamos las entradas existentes relacionadas si las hubiera.
//
//					response = scsDelitosejgMapper.deleteByExample(oldDelitos);
//
//					if (item.getDelitos()==null) response = 1;
//					else {
//						String[] idDelitos = item.getDelitos().split(",");
//						for (String idDelito : idDelitos) {
//							delitos.setIddelito(Short.parseShort(idDelito));
//
//							response = scsDelitosejgMapper.insert(delitos);
//						}
//					}
//
//					LOGGER.debug(
//							"GestionEJGServiceImpl.actualizarDelitosEJG() -> Salida del servicio para insertar delitos ejgs");
//				} catch (Exception e) {
//					LOGGER.debug(
//							"GestionEJGServiceImpl.actualizarDelitosEJG() -> Se ha producido un error al insertar delitos ejgs. ",
//							e);
//				}
//				// respuesta si se actualiza correctamente
//				if (response >= 1) {
//					responsedto.setStatus(SigaConstants.OK);
//					LOGGER.debug("GestionEJGServiceImpl.actualizarDelitosEJG() -> OK.");
//				} else {
//					responsedto.setStatus(SigaConstants.KO);
//					LOGGER.error("GestionEJGServiceImpl.actualizarDelitosEJG() -> KO.");
//				}
//			}
//		}
//		return responsedto;
//	}

	@Override
	public ProcuradorDTO busquedaProcuradores(ProcuradorItem procuradorItem, HttpServletRequest request) {
		LOGGER.info(
				"busquedaProcuradores() -> Entrada al servicio para obtener procuradores de la pantalla de buscador general");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradorItemList = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"busquedaProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores de la pantalla de buscador general");

				procuradorItemList = scsProcuradorExtendsMapper.searchProcuradores(procuradorItem, idInstitucion);

				LOGGER.info(
						"busquedaProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores de la pantalla de buscador general");

				if (procuradorItemList != null) {
					procuradorDTO.setProcuradorItems(procuradorItemList);
				}
			}

		}
		LOGGER.info(
				"busquedaProcuradores() -> Salida del servicio para obtener los procuradores de la pantalla de buscador general");
		return procuradorDTO;
	}

	private Long uploadFileEjg(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
			String extension) {

		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFicheroEjg(idInstitucion);
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

		return ficheroVo.getIdfichero();
	}

	private String getDirectorioFicheroEjg(Short idInstitucion) {

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

		return directorioFichero.toString();
	}

	private InputStream getZipFileDocumentosEjg(List<EjgDocumentacionItem> listadocumentoEjgItem, Short idInstitucion) {

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (EjgDocumentacionItem doc : listadocumentoEjgItem) {
				if (doc.getNombreFichero() != null) {
					zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
					String extension = doc.getNombreFichero()
							.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
							.toLowerCase();
					String path = getDirectorioFicheroEjg(idInstitucion);
					path += File.separator + idInstitucion + "_" + doc.getIdFichero() + extension;
					File file = new File(path);
					FileInputStream fileInputStream = new FileInputStream(file);
					IOUtils.copy(fileInputStream, zipOutputStream);
					fileInputStream.close();
				}
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
	@Transactional
	public InsertResponseDTO crearDocumentacionEjg(EjgDocumentacionItem documentacionEjgItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 1;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"GestionEJGServiceImpl.crearDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.crearDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

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

				// Posible fuente de problemas por restricciones de la base de datos
				// Se lee el valor de presentador enviado desde el front.
				// Se ha implementado para que si empieza con "S_" (añadido unicamente en el
				// combo de presentador)
				// se trata de un solicitante de la unidad (idpersona), en caso contrario, es
				// uno de los presentadores de EJGS
				if (documentacionEjgItem.getPresentador().contains("S_")) {
					scsDocumentacionejg
							.setPresentador(Long.valueOf(documentacionEjgItem.getPresentador().split("S_")[1]));
				} else
					scsDocumentacionejg.setIdmaestropresentador(Short.valueOf(documentacionEjgItem.getPresentador()));
				scsDocumentacionejg.setDocumentacion(documentacionEjgItem.getDescripcionDoc());

				scsDocumentacionejg.setIdinstitucion(idInstitucion);
				scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				scsDocumentacionejg.setFechamodificacion(new Date());

				if (documentacionEjgItem.getIdDocumento() == -1) {

					List<ComboItem> comboItems = scsDocumentoejgExtendsMapper.comboDocumentos(
							usuarios.get(0).getIdlenguaje(), idInstitucion,
							String.valueOf(documentacionEjgItem.getIdTipoDocumento()));
					for (ComboItem item : comboItems) {
						scsDocumentacionejg.setIddocumento(Short.valueOf(item.getValue()));

						if (response == 1) {
							nuevoId = scsEjgExtendsMapper.getNewIdDocumentacionEjg(idInstitucion);
							scsDocumentacionejg.setIddocumentacion(Integer.valueOf(nuevoId.getIdMax().toString()));
							response = scsDocumentacionejgMapper.insert(scsDocumentacionejg);
						}
					}
				} else {
					scsDocumentacionejg.setIddocumento(Short.valueOf(documentacionEjgItem.getIdDocumento()));
					response = scsDocumentacionejgMapper.insert(scsDocumentacionejg);
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"GestionEJGServiceImpl.crearDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
			insertResponseDTO.setStatus(SigaConstants.KO);
			response = 0;
		}

		if (response == 1) {
			insertResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			insertResponseDTO.setError(error);
		}

		if (response == 0) {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error(
					"GestionEJGServiceImpl.crearDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO actualizarDocumentacionEjg(EjgDocumentacionItem documentacionEjgItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"GestionEJGServiceImpl.actualizarDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.actualizarDocumentacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
			LOGGER.error(
					"GestionEJGServiceImpl.actualizarDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg",
					e);
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
			LOGGER.error(
					"GestionEJGServiceImpl.actualizarDocumentacionEjg() -> Se ha producido un error al subir un fichero perteneciente al ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setError(error);
		}

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO eliminarDocumentacionEjg(List<EjgDocumentacionItem> listadocumentoEjgItem,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		int response = 1, response2 = 1;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentacionDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentacionDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
						String path = getDirectorioFicheroEjg(idInstitucion);
						path += File.separator + idInstitucion + "_" + doc.getIdFichero() + doc.getNombreFichero()
								.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
								.toLowerCase();

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
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentacionDesigna() -> Se ha producido un error en la eliminación dedocumentacion de ejg",
					e);
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
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentacionDesigna() -> Se ha producido un error en la eliminación de documentacion de ejg");
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
			LOGGER.info(
					"GestionEJGServiceImpl.eliminarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.eliminarDocumentosDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				// Seleccionamos la documentacion del fichero
				ScsDocumentacionejgKey scsDocumentacionejgKey = new ScsDocumentacionejgKey();

				scsDocumentacionejgKey.setIddocumentacion(doc.getIdDocumentacion().intValue());
				scsDocumentacionejgKey.setIdinstitucion(idInstitucion);

				ScsDocumentacionejg scsDocumentacionejg = scsDocumentacionejgMapper
						.selectByPrimaryKey(scsDocumentacionejgKey);

				// Modificamos la entrada en la tabla
				scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				scsDocumentacionejg.setFechamodificacion(new Date());
				scsDocumentacionejg.setIdfichero(null);
				scsDocumentacionejg.setNombrefichero(null);

				response = scsDocumentacionejgMapper.updateByPrimaryKey(scsDocumentacionejg);

				// Eliminacion fisica del fichero asociado
				String path = getDirectorioFicheroEjg(idInstitucion);
				path += File.separator + idInstitucion + "_" + doc.getIdFichero()
						+ doc.getNombreFichero()
								.substring(doc.getNombreFichero().lastIndexOf("."), doc.getNombreFichero().length())
								.toLowerCase();

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
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentosnDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la documentacion de ejg",
					e);
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
			LOGGER.error(
					"DesignacionesServiceImpl.eliminarDocumentosDesigna() -> Se ha producido un error en la eliminación de documentos asociados a la documentacion de ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public InsertResponseDTO subirDocumentoEjg(MultipartHttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 1;

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"GestionEJGServiceImpl.subirDocumentoEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.subirDocumentoEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				Iterator<String> itr = request.getFileNames();

				MultipartFile file = request.getFile(itr.next());
				String nombreFichero = file.getOriginalFilename().split(";")[0];
				String extension = FilenameUtils.getExtension(nombreFichero);

				String idDocumentacion = request.getParameter("idPersona");

				Long idFile = uploadFileEjg(file.getBytes(), usuarios.get(0).getIdusuario(), idInstitucion,
						nombreFichero, extension);

				// Actualizamos la documentacion modificada
				ScsDocumentacionejg scsDocumentacionejg = new ScsDocumentacionejg();

				scsDocumentacionejg.setUsumodificacion(usuarios.get(0).getIdusuario());
				scsDocumentacionejg.setFechamodificacion(new Date());
				scsDocumentacionejg.setIdinstitucion(idInstitucion);
				scsDocumentacionejg.setIdfichero(idFile);
				scsDocumentacionejg.setIddocumentacion(Integer.valueOf(idDocumentacion));
				scsDocumentacionejg.setNombrefichero(nombreFichero);

				response = scsDocumentacionejgMapper.updateByPrimaryKeySelective(scsDocumentacionejg);

			}
		} catch (Exception e) {
			LOGGER.error(
					"GestionEJGServiceImpl.subirDocumentoEjg() -> Se ha producido un error al subir un fichero perteneciente a la designación",
					e);
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

		}

		if (response == 0) {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error(
					"GestionEJGServiceImpl.subirDocumentoDesigna() -> Se ha producido un error al subir un fichero perteneciente al ejg");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarDocumentosEjg(List<EjgDocumentacionItem> listadocumentoEjgItem,
			HttpServletRequest request) {

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
					"GestionEJGServiceImpl.descargarDocumentosEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"GestionEJGServiceImpl.descargarDocumentosEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !listadocumentoEjgItem.isEmpty()) {

				if (listadocumentoEjgItem.size() == 1) {

					String extension = listadocumentoEjgItem.get(0).getNombreFichero()
							.substring(listadocumentoEjgItem.get(0).getNombreFichero().lastIndexOf("."),
									listadocumentoEjgItem.get(0).getNombreFichero().length())
							.toLowerCase();
					String path = getDirectorioFicheroEjg(idInstitucion);
					path += File.separator + idInstitucion + "_" + listadocumentoEjgItem.get(0).getIdFichero()
							+ extension;

					File file = new File(path);
					fileStream = new FileInputStream(file);

					String tipoMime = getMimeType(extension);

					headers.setContentType(MediaType.parseMediaType(tipoMime));
					headers.set("Content-Disposition",
							"attachment; filename=\"" + listadocumentoEjgItem.get(0).getNombreFichero() + "\"");
					headers.setContentLength(file.length());

				} else {
					fileStream = getZipFileDocumentosEjg(listadocumentoEjgItem, idInstitucion);

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"documentosEJG.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error(
					"GestionEJGServiceImpl.descargarDocumentosEjg() -> Se ha producido un error al descargar archivos asociados al ejg",
					e);
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
			LOGGER.debug(
					"GestionEJGServiceImpl.insertFamiliarEJG() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.insertFamiliarEJG() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.nuevoEstado() -> Entrada para insertar en la unidad familiar del ejg");

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
				eejgPeticion.setFechapeticion(new Date());
				eejgPeticion.setFechamodificacion(new Date());
				eejgPeticion.setUsumodificacion(0);
				eejgPeticion.setNumerointentosconsulta(Short.parseShort("0"));
				eejgPeticion.setNumerointentospendienteinfo(Short.parseShort("0"));
				eejgPeticion.setNumerointentossolicitud(Short.parseShort("0"));
				eejgPeticion.setIdpeticion(scsEejgPeticionesExtendsMapper.getMaxIdpeticion());

				scsEejgPeticionesExtendsMapper.insertSelective(eejgPeticion);

				// insercion de nuevo registro a la tabla EcomCola una vez se realiza la
				// peticion de EEJG.
				EcomCola ecomCola = new EcomCola();

				// ecomCola.setIdecomcola(null);
				ecomCola.setIdestadocola(Short.parseShort("1"));
				ecomCola.setIdoperacion(81);
				ecomCola.setReintento(0);
				ecomCola.setFechacreacion(new Date());
				ecomCola.setFechamodificacion(new Date());
				ecomCola.setUsumodificacion(usuarios.get(0).getIdusuario());

				ecomCola.setIdinstitucion(idInstitucion);

				ecomColaMapper.insertSelective(ecomCola);

				// obtener el ultimo idcomcola de la tabla ECOM_COLA.
				EcomColaExample example = new EcomColaExample();
				example.setOrderByClause("IDECOMCOLA DESC");
				example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdestadocolaEqualTo((short) 1)
						.andIdoperacionEqualTo(81);

				List<EcomCola> listEcomCola = ecomColaMapper.selectByExample(example);

				if (listEcomCola.size() > 0) {
					idComCola = listEcomCola.get(0).getIdecomcola() + 56;
				} else {
					idComCola = 0;
				}

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

				LOGGER.debug(
						"GestionEJGServiceImpl.insertFamiliarEJG() -> Salida del servicio para insertar una solicitud de EEJG");
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
		Error error = new Error();

		int response = 1;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.asociarDesignacion() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.asociarDesignacion() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.asociarDesignacion() -> Entrada para asociar un EJG a una designacion");
				try {

					ScsEjgdesigna record = new ScsEjgdesigna();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setIdinstitucion(Short.parseShort(datos.get(0)));

					record.setAniodesigna(Short.parseShort(datos.get(1)));
					record.setNumerodesigna(Long.parseLong(datos.get(2)));

					record.setAnioejg(Short.parseShort(datos.get(5)));
					record.setIdtipoejg(Short.parseShort(datos.get(4)));
					record.setNumeroejg(Long.parseLong(datos.get(6)));

					TurnosItem turnosItem = new TurnosItem();
					String turnoDesc = datos.get(7).substring(0, datos.get(7).length() - 1);
					turnosItem.setAbreviatura(turnoDesc);
					List<TurnosItem> turnos = scsTurnosExtendsMapper.busquedaTurnos(turnosItem, idInstitucion);
					record.setIdturno(Integer.parseInt(turnos.get(0).getIdturno()));

					response = scsEjgdesignaMapper.insertSelective(record);

				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.asociarDesignacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarDesignacion() -> KO. No se ha asociado ningun elemento");

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
	public UpdateResponseDTO asociarAsistencia(List<String> datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		int response = 1;

		if (idInstitucion != null) {
			LOGGER.debug(
					"GestionEJGServiceImpl.asociarAsistencia() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"GestionEJGServiceImpl.asociarAsistencia() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"GestionEJGServiceImpl.asociarAsistencia() -> Entrada para asociar un EJG a una asistencia");

				try {

					ScsAsistencia record = new ScsAsistencia();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());

					record.setIdinstitucion(Short.parseShort(datos.get(0)));
					record.setEjganio(Short.parseShort(datos.get(4)));
					record.setEjgidtipoejg(Short.parseShort(datos.get(3)));
					record.setEjgnumero(Long.parseLong(datos.get(5)));

					record.setNumero(Long.parseLong(datos.get(2)));
					record.setAnio(Short.parseShort(datos.get(1)));

					response = scsAsistenciaMapper.updateByPrimaryKeySelective(record);

				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.asociarDesignacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarDesignacion() -> KO. No se ha asociado ningun elemento");

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
		Error error = new Error();

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

					ScsSoj record = new ScsSoj();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setIdinstitucion(Short.parseShort(datos.get(0)));
					record.setNumero(Long.parseLong(datos.get(2)));
					record.setAnio(Short.parseShort(datos.get(1)));
					record.setIdtiposoj(Short.parseShort(datos.get(3)));

					record.setEjganio(Short.parseShort(datos.get(5)));
					record.setEjgidtipoejg(Short.parseShort(datos.get(4)));
					record.setEjgnumero(Long.parseLong(datos.get(6)));

					response = scsSojMapper.updateByPrimaryKeySelective(record);

				} catch (Exception e) {
					LOGGER.debug(
							"GestionEJGServiceImpl.asociarDesignacion() -> Se ha producido un error al actualizar el estado y la fecha de los ejgs. ",
							e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarDesignacion() -> KO. No se ha asociado ningun elemento");

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
	public UpdateResponseDTO actualizarInformeCalificacionEjg(EjgItem ejgItem, HttpServletRequest request)
			throws Exception {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 1;

		// Comentamos el try y el catch para que @Transactional funcione correctamente
//		try {

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		LOGGER.info(
				"GestionEJGServiceImpl.actualizarInformeCalificacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		LOGGER.info(
				"GestionEJGServiceImpl.actualizarInformeCalificacionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

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
			if (ejgItem.getFechaDictamen() == null && ejgItem.getFechaDictamen() == null
					&& ejgItem.getFechaDictamen() == null && ejgItem.getFechaDictamen() == null
					&& ejgItem.getFechaDictamen() == null && ejgItem.getIddictamen() != null) {

				// Actualizamos el EJG asociado para poder eliminar el dictamen.
				newEjg.setIddictamen(null);

				// Se ejecuta el método de que sustituye los triggers asociados a la tabla
				// SCS_EJG
				// cuando una fila es actualizada.
				this.triggersEjgUpdatesDictamen(ejgItem, usuarios.get(0), idInstitucion);

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
				this.triggersEjgUpdatesDictamen(ejgItem, usuarios.get(0), idInstitucion);

				response = scsEjgMapper.updateByPrimaryKeyWithBLOBs(newEjg);
				if (response == 0)
					throw (new Exception("Error al actualizar el EJG con informacion de dictamen"));

			}

		}

//		} catch (Exception e) {
//			LOGGER.error(
//					"GestionEJGServiceImpl.actualizarInformeCalificacionEjg() -> Se ha producido un error al actualizar el dictamen del ejg",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//			error.setMessage(e.getMessage());
//			updateResponseDTO.setError(error);
//			response = 0;
//		}

		if (response == 1) {
			updateResponseDTO.setStatus(SigaConstants.OK);
			error.setCode(200);
			// error.setDescription("general.mensaje.error.bbdd");
			updateResponseDTO.setError(error);
		}

		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.error(
					"GestionEJGServiceImpl.actualizarInformeCalificacionEjg() -> Se ha producido un error al actualizar el dictamen del ejg");
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
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		String valorNoColegiadoDocu = null;
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ScsEjgKey key = new ScsEjgKey();

		key.setIdinstitucion(idInstitucion);
		key.setAnio(Short.valueOf(ejgItem.getAnnio()));
		key.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		key.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg config = scsEjgMapper.selectByPrimaryKey(key);

//		if (config.getIdentificadords() == null) {
//			
//			// longitud maxima para numero en esa institucion
//			GenParametrosExample genParametrosExample = new GenParametrosExample();
//			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("LONGITUD_CODEJG")
//					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
//			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
//
//			List<GenParametros> listParam = genParametrosExtendsMapper.selectByExample(genParametrosExample);
//
//			String longitudEJG = listParam.get(0).getValor();
//
//			// Alteramos el numero para que todos los numeros de las carpetas de una institucion tengan
//			// la misma longitud.
//			
//			String numero = ejgItem.getNumero();
//
//			int numCeros = Integer.parseInt(longitudEJG) - ejgItem.getNumero().length();
//
//			String ceros = "";
//			for (int i = 0; i < numCeros; i++) {
//				ceros += "0";
//			}
//
//			ceros += numero;
//			
//			//Año EJG/Num EJG
//			String carpeta = ejgItem.getAnnio()+"/"+ceros;
//			
//			identificadorDS = docushareHelper.buscaCollectionEjg(carpeta, idInstitucion);
//		} else {
		identificadorDS = config.getIdentificadords();
//		}
		List<DocuShareObjectVO> docus = docushareHelper.getContenidoCollection(idInstitucion, identificadorDS, "");
		docushareDTO.setDocuShareObjectVO(docus);

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

			LOGGER.info(
					"insertCollectionEjg() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertCollectionEjg() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// longitud maxima para num ejg
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("LONGITUD_CODEJG")
						.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
				genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

				List<GenParametros> listParam = genParametrosExtendsMapper.selectByExample(genParametrosExample);

				String longitudEJG = listParam.get(0).getValor();

				// Alteramos el numero para que todos los numeros de las carpetas de una
				// institucion tengan
				// la misma longitud.

				String numero = ejgItem.getNumero();

				int numCeros = Integer.parseInt(longitudEJG) - ejgItem.getNumero().length();

				String ceros = "";
				for (int i = 0; i < numCeros; i++) {
					ceros += "0";
				}

				ceros += numero;

				// Año EJG/Num EJG
				String carpeta = ejgItem.getAnnio() + "/" + ceros;
				// String carpeta = ejgItem.getAnnio().concat("/").concat(ceros);

				idDS = docushareHelper.createCollectionEjg(idInstitucion, carpeta, "");

				ScsEjgKey key = new ScsEjgKey();

				key.setIdinstitucion(idInstitucion);
				key.setAnio(Short.valueOf(ejgItem.getAnnio()));
				key.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
				key.setNumero(Long.valueOf(ejgItem.getNumero()));

				ScsEjgWithBLOBs ejg = scsEjgMapper.selectByPrimaryKey(key);

				ejg.setIdentificadords(idDS);
				ejg.setFechamodificacion(new Date());
				ejg.setUsumodificacion(usuario.getIdusuario());

				LOGGER.info(
						"insertCollectionEjg() / scsEjgMapper.updateByPrimaryKey() -> Entrada a scsEjgMapper para modificar el identificador para DocuShare del EJG");

				int response = scsEjgMapper.updateByPrimaryKey(ejg);
				if (response == 0)
					throw (new Exception("Error al actualizar el identificador para DocuShare del EJG"));

				LOGGER.info(
						"insertCollectionEjg() / scsEjgMapper.updateByPrimaryKey() -> Salida de scsEjgMapper para modificar el identificador para DocuShare del EJG");
			}
		}

		LOGGER.info("insertCollectionEjg() -> Salida al servicio para la insertar una nueva colección");

		return idDS;

	}

	public int triggersEjgUpdatesFApertura(EjgItem ejgItem, AdmUsuarios usuario, short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesFApertura() -> Entrada al metodo para realizar cambios en el estado inicial del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG
		// de la BBDD
		// al actualizar.

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

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(Short.valueOf(ejgItem.getTipoEJG()))
					.andAnioEqualTo(Short.valueOf(ejgItem.getAnnio()))
					.andNumeroEqualTo(Long.valueOf(ejgItem.getNumero())).andIdestadoejgEqualTo((short) 23); // Solicitud
																											// en
																											// procesdo
																											// de alta
																											// ===
																											// scs_maestroestadosejg.idestado=23

			List<ScsEstadoejg> estadoIni = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			estadoIni.get(0).setFechainicio(ejgItem.getFechaApertura());

			response = scsEstadoejgMapper.updateByExample(estadoIni.get(0), estadoEjgExample);
			if (response == 0)
				throw (new Exception("Error en triggersEjgUpdatesFApertura()"));
		}
		
		LOGGER.info("triggersEjgUpdatesFApertura() -> Salida del metodo para realizar cambios en el estado inicial del EJG");

		return response;
	}

	public int triggersEjgUpdatesDictamen(EjgItem ejgItem, AdmUsuarios usuario, short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesDictamen() -> Entrada al metodo para realizar cambios en estados de \"Dictaminado\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG
		// de la BBDD
		// al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		ejgKey.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		// 2.Si cambia el dictamen o la fecha dictamen y no eran nulos antes o despues
		// ponemos fecha de baja a todos los estados anteriores que hayan sido
		// dictaminados
		if ((ejg.getFechadictamen()!=ejgItem.getFechaDictamen()
				|| ejg.getIdtipodictamenejg()!=ejgItem.getIdTipoDictamen())
				&& (ejg.getFechadictamen() != null && ejg.getIdtipodictamenejg() != null)
				|| (ejgItem.getFechaDictamen() != null && ejgItem.getIdTipoDictamen() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(Short.valueOf(ejgItem.getTipoEJG()))
					.andAnioEqualTo(Short.valueOf(ejgItem.getAnnio())).andNumeroEqualTo(ejg.getNumero())
					.andIdestadoejgEqualTo((short) 6) // Dictaminado === scs_maestroestadosejg.idestado=6
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoDict = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if(!estadoDict.isEmpty()) {
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
				newEstadoDictaminado.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgDictamen(idInstitucion,
						usuario.getIdlenguaje(), ejgItem.getIdTipoDictamen()));

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

	public int triggersEjgUpdatesPonente(ResolucionEJGItem resolEjg, AdmUsuarios usuario, Short idInstitucion)
			throws Exception {

		LOGGER.info("triggersEjgUpdatesPonente() -> Entrada al metodo para realizar cambios en estados de \"Remitida apertura a CAJG-Reparto Ponente\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG
		// de la BBDD
		// al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(resolEjg.getAnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(resolEjg.getIdTipoEJG()));
		ejgKey.setNumero(Long.valueOf(resolEjg.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		// 3. Si cambia el ponente o la fecha presentacion ponente y no eran nulos antes
		// o despues
		// ponemos fecha de baja a todos los estados anteriores iguales
		if ((ejg.getFechapresentacionponente()!=resolEjg.getFechaPresentacionPonente()
				|| (ejg.getIdponente()!=resolEjg.getIdPonente()))
				&& (ejg.getFechapresentacionponente() != null && ejg.getIdponente() != null)
				|| (resolEjg.getFechaPresentacionPonente() != null && resolEjg.getIdPonente() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(Short.valueOf(resolEjg.getIdTipoEJG()))
					.andAnioEqualTo(Short.valueOf(resolEjg.getAnio())).andNumeroEqualTo(ejg.getNumero())
					.andIdestadoejgEqualTo((short) 0) // Remitida apertura a CAJG-Reparto Ponente ===
														// scs_maestroestadosejg.idestado=0
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoPonente = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if(!estadoPonente.isEmpty()) {
				estadoPonente.get(0).setFechabaja(new Date());
			
				response = scsEstadoejgMapper.updateByExample(estadoPonente.get(0), estadoEjgExample);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesPonente 3.1"));
			}

			// 3.2 En el caso que ahora (los valores nuevos) no fueran nulos
			if (resolEjg.getFechaPresentacionPonente() != null && resolEjg.getIdPonente() != null) {
				// Se inserta el estado "Remitida apertura a CAJG-Reparto Ponente" y se pone en
				// las observacions el nombre del ponente.

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

				// Se realiza una consulta SQL para obtener las observaciones asociadas
				newEstadoPonente.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgPonente(idInstitucion,
						usuario.getIdlenguaje(), resolEjg.getIdPonente()));

				// obtenemos el maximo de idestadoporejg
				newEstadoPonente.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

				newEstadoPonente.setAutomatico("1");
				newEstadoPonente.setPropietariocomision("1");

				response = scsEstadoejgMapper.insert(newEstadoPonente);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesPonente 3.2"));
			}
		}
		
		LOGGER.info("triggersEjgUpdatesPonente() -> Salida del metodo para realizar cambios en estados de \"Remitida apertura a CAJG-Reparto Ponente\" anteriores del EJG");

		return response;
	}

	public int triggersEjgUpdatesResol(ResolucionEJGItem resolEjg, AdmUsuarios usuario, Short idInstitucion) throws Exception {

		LOGGER.info("triggersEjgUpdatesResol() -> Entrada al metodo para realizar cambios en estados de \"Resuelto Comisión\" anteriores del EJG");

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG
		// de la BBDD
		// al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(resolEjg.getAnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(resolEjg.getIdTipoEJG()));
		ejgKey.setNumero(Long.valueOf(resolEjg.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);

		

		// 4. Si cambia la resolucion o la fecha de resolucion y no eran nulos
		// antes o despues
		// ponemos fecha de baja a todos los estados anteriores iguales
		if ((ejg.getFecharesolucioncajg()!=resolEjg.getFechaResolucionCAJG()
				|| (ejg.getIdtiporatificacionejg()!=resolEjg.getIdTiporatificacionEJG()))
				&& (ejg.getFecharesolucioncajg() != null && ejg.getIdtiporatificacionejg() != null)
				|| (resolEjg.getFechaResolucionCAJG() != null && resolEjg.getIdTiporatificacionEJG() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(Short.valueOf(resolEjg.getIdTipoEJG()))
					.andAnioEqualTo(Short.valueOf(resolEjg.getAnio())).andNumeroEqualTo(ejg.getNumero())
					.andIdestadoejgEqualTo((short) 10) // Resuelto Comisión === scs_maestroestadosejg.idestado=10
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoResol = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if(!estadoResol.isEmpty()) {
				estadoResol.get(0).setFechabaja(new Date());
			
				response = scsEstadoejgMapper.updateByExample(estadoResol.get(0), estadoEjgExample);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesResol 4.1"));
			}
			
			// 4.2 En el caso que ahora (los valores nuevos) no fueran nulos
			if (resolEjg.getFechaResolucionCAJG() != null && resolEjg.getIdTiporatificacionEJG() != null) {
				// Se inserta el estado Resuelto comisión y se pone en las observacions el tipo
				// de resolcuion.

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
				newEstadoResol.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgResol(
						usuario.getIdlenguaje(), resolEjg.getIdTiporatificacionEJG()));

				// Obtenemos el maximo de idestadoporejg
				newEstadoResol.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

				newEstadoResol.setAutomatico("1");
				newEstadoResol.setPropietariocomision("1");

				response = scsEstadoejgMapper.insert(newEstadoResol);
				if (response == 0)
					throw (new Exception("Error en triggersEjgUpdatesResol 4.2"));
				
				// 4.3 En el caso que ahora (los valores nuevos) la resolucion fuera "Devuelto al Colegio" (valor 6 del combo)
				if (resolEjg.getIdTiporatificacionEJG() == 6) {
					// Se inserta el estado Resuelta impugnación y se pone en las observaciones el
					// tipo de resolucion.

					ScsEstadoejg newEstadoResolDev = new ScsEstadoejg();

					newEstadoResolDev.setIdinstitucion(idInstitucion);
					newEstadoResolDev.setIdtipoejg(ejg.getIdtipoejg());
					newEstadoResolDev.setAnio(ejg.getAnio());
					newEstadoResolDev.setNumero(ejg.getNumero());
					newEstadoResolDev.setIdestadoejg((short) 21); // Devuelto al Colegio === scs_maestroestadosejg.idestado=21
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

		// Este método sustituye los triggers presentes actualmente en la tabla SCS_EJG
		// de la BBDD
		// al actualizar.

		int response = 1;

		ScsEjgKey ejgKey = new ScsEjgKey();

		ejgKey.setAnio(Short.valueOf(ejgItem.getAnnio()));
		ejgKey.setIdinstitucion(idInstitucion);
		ejgKey.setIdtipoejg(Short.valueOf(ejgItem.getTipoEJG()));
		ejgKey.setNumero(Long.valueOf(ejgItem.getNumero()));

		ScsEjg ejg = scsEjgMapper.selectByPrimaryKey(ejgKey);
		
		// 5. Si cambia la impugnacion o la fecha de impugnacion y no eran nulos antes o
		// despues ponemos fecha de baja a todos los estados anteriores iguales
		if ((ejg.getFechaauto()!=ejgItem.getFechaAuto()
				|| (ejg.getIdtiporesolauto().toString()!=ejgItem.getAutoResolutorio()))
				&& (ejg.getFechaauto() != null && ejg.getIdtiporesolauto() != null)
				|| (ejgItem.getFechaAuto() != null && ejgItem.getAutoResolutorio() != null)) {

			ScsEstadoejgExample estadoEjgExample = new ScsEstadoejgExample();

			estadoEjgExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
					.andIdtipoejgEqualTo(Short.valueOf(ejgItem.getTipoEJG()))
					.andAnioEqualTo(Short.valueOf(ejgItem.getAnnio())).andNumeroEqualTo(ejg.getNumero())
					.andIdestadoejgEqualTo((short) 13) // Resuelta Impugnación === scs_maestroestadosejg.idestado=13
					.andAutomaticoEqualTo("1").andFechabajaIsNull();

			List<ScsEstadoejg> estadoImpug = scsEstadoejgMapper.selectByExample(estadoEjgExample);

			if(!estadoImpug.isEmpty()) {
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
				newEstadoImpug.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgImpug(
						usuario.getIdlenguaje(), ejgItem.getAutoResolutorio()));

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

	private int triggersEjgInsert(ScsEjg ejg, AdmUsuarios usuario, Short idInstitucion)
			throws Exception {

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
		newEstadoIni.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoIniInsertEjg(
				usuario.getIdlenguaje()));

		// Obtenemos el maximo de idestadoporejg
		newEstadoIni.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));

		newEstadoIni.setAutomatico("1");

		response = scsEstadoejgMapper.insert(newEstadoIni);
		if (response == 0)
			throw (new Exception("Error en triggersEjgInsert() 1"));

		// 2. Estado a partir de fechadictamen
//		if (ejg.getFechadictamen() != null && ejg.getIdtipodictamenejg() != null) {
//			ScsEstadoejg newEstadoDict = new ScsEstadoejg();
//
//			newEstadoDict.setIdinstitucion(idInstitucion);
//			newEstadoDict.setIdtipoejg(ejg.getIdtipoejg());
//			newEstadoDict.setAnio(ejg.getAnio());
//			newEstadoDict.setNumero(ejg.getNumero());
//			newEstadoDict.setIdestadoejg((short) 6); // Dictaminado === scs_maestroestadosejg.idestado=6
//			newEstadoDict.setFechainicio(ejg.getFechadictamen());
//			newEstadoDict.setFechamodificacion(new Date());
//			newEstadoDict.setUsumodificacion(usuario.getIdusuario());
//
//			// Se realiza una consulta SQL para obtener las observaciones asociadas
//			newEstadoDict.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgDictamen(idInstitucion,
//					usuario.getIdlenguaje(), ejgItem.getIdTipoDictamen()));
//
//			// Obtenemos el maximo de idestadoporejg
//			newEstadoDict.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//			newEstadoDict.setAutomatico("1");
//
//			response = scsEstadoejgMapper.insert(newEstadoDict);
//			if (response == 0)
//				throw (new Exception("Error en triggersEjgInsert() 2"));
//		}
//
//		// 3 En el caso que los datos de ponente no fueran nulos
//		if (resolEjg.getFechaPresentacionPonente() != null && resolEjg.getIdPonente() != null) {
//			// Se inserta el estado "Remitida apertura a CAJG-Reparto Ponente" y se pone en
//			// las observacions el nombre del ponente.
//
//			ScsEstadoejg newEstadoPonente = new ScsEstadoejg();
//
//			newEstadoPonente.setIdinstitucion(idInstitucion);
//			newEstadoPonente.setIdtipoejg(ejg.getIdtipoejg());
//			newEstadoPonente.setAnio(ejg.getAnio());
//			newEstadoPonente.setNumero(ejg.getNumero());
//			newEstadoPonente.setIdestadoejg((short) 0); // Remitida apertura a CAJG-Reparto Ponente ===
//														// scs_maestroestadosejg.idestado=0
//			newEstadoPonente.setFechainicio(resolEjg.getFechaPresentacionPonente());
//			newEstadoPonente.setFechamodificacion(new Date());
//			newEstadoPonente.setUsumodificacion(usuario.getIdusuario());
//
//			// Se realiza una consulta SQL para obtener las observaciones asociadas
//			newEstadoPonente.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgPonente(idInstitucion,
//					usuario.getIdlenguaje(), resolEjg.getIdPonente()));
//
//			// obtenemos el maximo de idestadoporejg
//			newEstadoPonente.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//			newEstadoPonente.setAutomatico("1");
//
//			response = scsEstadoejgMapper.insert(newEstadoPonente);
//			if (response == 0)
//				throw (new Exception("Error en triggersEjgInsert() 3"));
//		}
//
//		// 4 En el caso que los datos de resolucion no fueran nulos
//		if (resolEjg.getFechaResolucionCAJG() != null && resolEjg.getIdTiporatificacionEJG() != null) {
//			// Se inserta el estado Resuelto comisión y se pone en las observacions el tipo
//			// de resolcuion.
//
//			ScsEstadoejg newEstadoResol = new ScsEstadoejg();
//
//			newEstadoResol.setIdinstitucion(idInstitucion);
//			newEstadoResol.setIdtipoejg(ejg.getIdtipoejg());
//			newEstadoResol.setAnio(ejg.getAnio());
//			newEstadoResol.setNumero(ejg.getNumero());
//			newEstadoResol.setIdestadoejg((short) 10); // Resuelto Comisión === scs_maestroestadosejg.idestado=10
//			newEstadoResol.setFechainicio(resolEjg.getFechaResolucionCAJG());
//			newEstadoResol.setFechamodificacion(new Date());
//			newEstadoResol.setUsumodificacion(usuario.getIdusuario());
//
//			// Se realiza una consulta SQL para obtener las observaciones asociadas
//			newEstadoResol.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgResol(idInstitucion,
//					usuario.getIdlenguaje(), resolEjg.getIdTiporatificacionEJG()));
//
//			// Obtenemos el maximo de idestadoporejg
//			newEstadoResol.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//			newEstadoResol.setAutomatico("1");
//
//			response = scsEstadoejgMapper.insert(newEstadoResol);
//			if (response == 0)
//				throw (new Exception("Error en triggersEjgInsert() 4"));
//		}
//
//		// 5 En el caso que los datos del auto no fueran nulos
//		if (ejgItem.getFechaAuto() != null && resolEjg.getTipoResolucionCAJG() != null) {
//			// Se inserta el estado Resuelta impugnación y se pone en las observaciones el
//			// tipo de resolucion.
//
//			ScsEstadoejg newEstadoImpug = new ScsEstadoejg();
//
//			newEstadoImpug.setIdinstitucion(idInstitucion);
//			newEstadoImpug.setIdtipoejg(ejg.getIdtipoejg());
//			newEstadoImpug.setAnio(ejg.getAnio());
//			newEstadoImpug.setNumero(ejg.getNumero());
//			newEstadoImpug.setIdestadoejg((short) 13); // Resuelta Impugnación === scs_maestroestadosejg.idestado=13
//			newEstadoImpug.setFechainicio(ejgItem.getFechaAuto());
//			newEstadoImpug.setFechamodificacion(new Date());
//			newEstadoImpug.setUsumodificacion(usuario.getIdusuario());
//
//			// Se realiza una consulta SQL para obtener las observaciones asociadas
//			newEstadoImpug.setObservaciones(scsEjgExtendsMapper.getObservacionEstadoEjgImpug(idInstitucion,
//					usuario.getIdlenguaje(), resolEjg.getTipoResolucionCAJG()));
//
//			// Obtenemos el maximo de idestadoporejg
//			newEstadoImpug.setIdestadoporejg(getNewIdestadoporejg(ejg, idInstitucion));
//
//			newEstadoImpug.setAutomatico("1");
//
//			response = scsEstadoejgMapper.insert(newEstadoImpug);
//			if (response == 0)
//				throw (new Exception("Error en triggersEjgInsert() 5"));
//		}

		return response;
	}

	private Long getNewIdestadoporejg(ScsEjg ejg, Short idInstitucion) {

		Long newIdestadoporejg;
		// obtenemos el maximo de idestadoporejg
		ScsEstadoejgExample example = new ScsEstadoejgExample();

		example.setOrderByClause("IDESTADOPOREJG DESC");
		example.createCriteria().andAnioEqualTo(ejg.getAnio()).andIdinstitucionEqualTo(idInstitucion)
				.andIdtipoejgEqualTo(ejg.getIdtipoejg()).andNumeroEqualTo(ejg.getNumero());

		List<ScsEstadoejg> listEjg = scsEstadoejgMapper.selectByExample(example);

		// damos el varlo al idestadoporejg + 1
		if (listEjg.size() > 0) {
			newIdestadoporejg = listEjg.get(0).getIdestadoporejg() + 1;
		} else {
			newIdestadoporejg = Long.parseLong("1");
		}

		return newIdestadoporejg;
	}

}