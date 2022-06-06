package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CompensacionFacDTO;
import org.itcgae.siga.DTOs.scs.CompensacionFacItem;
import org.itcgae.siga.DTOs.scs.ConceptoPagoDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FacPropositos;
import org.itcgae.siga.db.entities.FacPropositosExample;
import org.itcgae.siga.db.entities.FacSufijo;
import org.itcgae.siga.db.entities.FacSufijoExample;
import org.itcgae.siga.db.entities.FcsAplicaMovimientosvarios;
import org.itcgae.siga.db.entities.FcsAplicaMovimientosvariosExample;
import org.itcgae.siga.db.entities.FcsCobrosRetencionjudicial;
import org.itcgae.siga.db.entities.FcsCobrosRetencionjudicialExample;
import org.itcgae.siga.db.entities.FcsFactGrupofactHito;
import org.itcgae.siga.db.entities.FcsFactGrupofactHitoExample;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.FcsPagoColegiadoExample;
import org.itcgae.siga.db.entities.FcsPagoGrupofactHito;
import org.itcgae.siga.db.entities.FcsPagoGrupofactHitoExample;
import org.itcgae.siga.db.entities.FcsPagosEstadospagos;
import org.itcgae.siga.db.entities.FcsPagosEstadospagosExample;
import org.itcgae.siga.db.entities.FcsPagosjg;
import org.itcgae.siga.db.entities.FcsPagosjgKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.FacEstadofacturaMapper;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FcsAplicaMovimientosvariosMapper;
import org.itcgae.siga.db.mappers.FcsCobrosRetencionjudicialMapper;
import org.itcgae.siga.db.mappers.FcsPagoGrupofactHitoMapper;
import org.itcgae.siga.db.mappers.FcsPagosEstadospagosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineaabonoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPropositosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacSufijoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactGrupofactHitoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagoColegiadoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagosjgExtendsMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.itcgae.siga.scs.services.facturacionsjcs.IPagoSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoSJCSServiceImpl implements IPagoSJCSService {

	private final Logger LOGGER = Logger.getLogger(PagoSJCSServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;

	@Autowired
	private FcsPagosjgExtendsMapper fcsPagosjgExtendsMapper;

	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

	@Autowired
	private FcsPagosEstadospagosMapper fcsPagosEstadospagosMapper;

	@Autowired
	private FcsFactGrupofactHitoExtendsMapper fcsFactGrupofactHitoExtendsMapper;

	@Autowired
	private FcsPagoGrupofactHitoMapper fcsPagoGrupofactHitoMapper;

	@Autowired
	private FacPropositosExtendsMapper facPropositosExtendsMapper;

	@Autowired
	private FacSufijoExtendsMapper facSufijoExtendsMapper;

	@Autowired
	private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

	@Autowired
	private FcsPagoColegiadoExtendsMapper fcsPagoColegiadoExtendsMapper;

	@Autowired
	private FcsAplicaMovimientosvariosMapper fcsAplicaMovimientosvariosMapper;

	@Autowired
	private UtilidadesFacturacionSJCS utilidadesFacturacionSJCS;

	@Autowired
	private GenRecursosMapper genRecursosMapper;

	@Autowired
	private CenPersonaMapper cenPersonaMapper;

	@Autowired
	private FacAbonoExtendsMapper facAbonoExtendsMapper;

	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;

	@Autowired
	private FacLineaabonoExtendsMapper facLineaabonoExtendsMapper;

	@Autowired
	private FacFacturaMapper facFacturaMapper;

	@Autowired
	private FacPagoabonoefectivoExtendsMapper facPagoabonoefectivoExtendsMapper;

	@Autowired
	private FacPagosporcajaExtendsMapper facPagosporcajaExtendsMapper;

	@Autowired
	private FacDisquetecargosExtendsMapper facDisquetecargosExtendsMapper;

	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private FacEstadofacturaMapper facEstadofacturaMapper;

	@Autowired
	private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

	@Autowired
	private FcsCobrosRetencionjudicialMapper fcsCobrosRetencionjudicialMapper;

	@Autowired
	private UtilidadesPagoSJCS utilidadesPagoSJCS;

	@Override
	public PagosjgDTO buscarPagos(PagosjgItem pagosItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PagosjgDTO pagos = new PagosjgDTO();
		String idLenguaje = "";
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();

		try {

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
					usuario.setIdinstitucion(idInstitucion);
					idLenguaje = usuario.getIdlenguaje();

					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria().andModuloEqualTo("SCS")
							.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					LOGGER.info(
							"PagoSJCSServiceImpl.buscarPagos() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					tamMax = genParametrosMapper.selectByExample(genParametrosExample);

					LOGGER.info(
							"PagoSJCSServiceImpl.buscarPagos() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					if (tamMax != null) {
						tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
					} else {
						tamMaximo = null;
					}

					LOGGER.info(
							"PagoSJCSServiceImpl.buscarPagos() / fcsFacturacionJGExtendsMapper.buscarPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los pagos");
					List<PagosjgItem> pagosItems = fcsPagosjgExtendsMapper.buscarPagos(pagosItem,
							idInstitucion.toString(), idLenguaje, tamMaximo);

					if (null != pagosItems && pagosItems.size() > tamMaximo) {
						pagosItems.remove(pagosItems.size() - 1);
						error.setCode(200);
						error.setDescription("general.message.consulta.resultados");
					}

					pagos.setPagosjgItem(pagosItems);
					LOGGER.info(
							"PagoSJCSServiceImpl.buscarPagos() / fcsFacturacionJGExtendsMapper.buscarPagos() -> Salida a fcsFacturacionJGExtendsMapper para obtener los pagos");
				} else {
					LOGGER.warn(
							"PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("PagoSJCSServiceImpl.buscarPagos() -> idInstitucion del token nula");
			}

		} catch (Exception e) {
			LOGGER.error("PagoSJCSServiceImpl.buscarPagos() -> Se ha producido un error al buscar los pagos", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		pagos.setError(error);
		LOGGER.info("PagoSJCSServiceImpl.buscarPagos() -> Salida del servicio para obtener los pagos");

		return pagos;
	}

	@Override
	public PagosjgDTO getPago(String idPago, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.getPago() -> Entrada al servicio para obtener los datos del pago: " + idPago);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PagosjgDTO pagosjgDTO = new PagosjgDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"PagoSJCSServiceImpl.getPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.getPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {

					PagosjgItem pagosjgItem = fcsPagosjgExtendsMapper.getPago(idPago, idInstitucion,
							usuarios.get(0).getIdlenguaje());
					pagosjgDTO.setPagosjgItem(Collections.singletonList(pagosjgItem));
				}

			}

		} catch (Exception e) {
			LOGGER.error("PagoSJCSServiceImpl.getPago() -> Se ha producido un error al buscar el pago: " + idPago, e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		pagosjgDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.getPago() -> Salida del servicio para obtener los datos del pago: " + idPago);

		return pagosjgDTO;
	}

	@Override
	public PagosjgDTO datosGeneralesPagos(String idPago, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.datosGeneralesPagos() -> Entrada para obtener los datos generales del pago: "
				+ idPago);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PagosjgDTO pagosjgDTO = new PagosjgDTO();
		Error error = new Error();

		if (null != idInstitucion) {

			try {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"PagoSJCSServiceImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					AdmUsuarios usuario = usuarios.get(0);
					usuario.setIdinstitucion(idInstitucion);

					LOGGER.info(
							"PagoSJCSServiceImpl.datosGeneralesPagos() -> fcsFacturacionJGExtendsMapper.datosGeneralesPagos() -> Entrada para obtener los datos del pago");
					List<PagosjgItem> pago = fcsPagosjgExtendsMapper.datosGeneralesPagos(idPago,
							idInstitucion.toString());
					pagosjgDTO.setPagosjgItem(pago);
					LOGGER.info(
							"PagoSJCSServiceImpl.datosGeneralesPagos() -> fcsFacturacionJGExtendsMapper.datosGeneralesPagos() -> Salida para obtener los datos del pago");
				} else {
					LOGGER.warn(
							"PagoSJCSServiceImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}

			} catch (Exception e) {
				LOGGER.error(
						"PagoSJCSServiceImpl.datosGeneralesPagos() -> Se ha producido un error al buscar los datos generales del pago: "
								+ idPago,
						e);
				error.setCode(500);
				error.setDescription("general.mensaje.error.bbdd");
			}

		} else {
			LOGGER.warn("PagoSJCSServiceImpl.datosGeneralesPagos() -> idInstitucion del token nula");
		}

		pagosjgDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.datosGeneralesPagos() -> Salida con los datos generales del pago: " + idPago);

		return pagosjgDTO;
	}

	@Override
	public PagosjgDTO historicoPagos(String idPago, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.historicoPagos() -> Entrada al servicio para obtener el histórico de un pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PagosjgDTO pagosjgDTO = new PagosjgDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"PagoSJCSServiceImpl.historicoPagos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.historicoPagos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					String idLenguaje = usuarios.get(0).getIdlenguaje();

					LOGGER.info(
							"PagoSJCSServiceImpl.historicoPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los historicos de estados del pago");
					List<PagosjgItem> listaEstados = fcsPagosjgExtendsMapper.historicoPagos(idPago, idLenguaje,
							idInstitucion);
					LOGGER.info(
							"PagoSJCSServiceImpl.historicoPagos() -> Salida a fcsFacturacionJGExtendsMapper para obtener los historicos de estados del pago");
					pagosjgDTO.setPagosjgItem(listaEstados);
				}
			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.historicoPagos() -> Se ha producido un error al buscar el histórico de estados del pago: "
							+ idPago,
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		pagosjgDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.historicoPagos() -> Salida del servicio para obtener el histórico de un pago");

		return pagosjgDTO;
	}

	@Override
	public InsertResponseDTO savePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.savePago() -> Entrada al servicio para la creación de un pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.savePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.savePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.getNewIdPago() -> Entrada al método para obtener un nuevo identificador de pago");
					NewIdDTO newId = fcsPagosjgExtendsMapper.getNewIdPago(idInstitucion);
					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.getNewIdPago() -> Salida del método para obtener un nuevo identificador de pago: "
									+ newId.getNewId());

					FcsPagosjg record = new FcsPagosjg();
					record.setIdinstitucion(idInstitucion);
					record.setIdpagosjg(Integer.valueOf(newId.getNewId()));
					record.setIdfacturacion(Integer.valueOf(pagosjgItem.getIdFacturacion()));
					record.setNombre(pagosjgItem.getNombre());
					record.setAbreviatura(pagosjgItem.getAbreviatura());
					record.setFechadesde(pagosjgItem.getFechaDesde());
					record.setFechahasta(pagosjgItem.getFechaHasta());
					record.setCriteriopagoturno("F");
					record.setImportepagado(BigDecimal.ZERO);
					record.setImporterepartir(BigDecimal.ZERO);
					record.setImporteoficio(BigDecimal.ZERO);
					record.setImporteguardia(BigDecimal.ZERO);
					record.setImportesoj(BigDecimal.ZERO);
					record.setImporteejg(BigDecimal.ZERO);
					record.setImporteminimo(BigDecimal.ZERO);
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setFechamodificacion(new Date());

					// Buscamos el propósito sepa por defecto de la institución y lo seteamos en el
					// pago
					GenParametrosExample genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria()
							.andParametroEqualTo(SigaConstants.PARAMETRO_PROPOSITO_TRANSFERENCIA_SEPA)
							.andModuloEqualTo(SigaConstants.MODULO_FACTURACION)
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> genParametrosMapper.selectByExample() -> Obtenenos el proposito SEPA por defecto");
					List<GenParametros> parametrosPropSepa = genParametrosMapper.selectByExample(genParametrosExample);

					if (null != parametrosPropSepa && !parametrosPropSepa.isEmpty()) {

						FacPropositosExample facPropositosExample = new FacPropositosExample();
						facPropositosExample.createCriteria().andCodigoEqualTo(parametrosPropSepa.get(0).getValor());
						List<FacPropositos> propositos = facPropositosExtendsMapper
								.selectByExample(facPropositosExample);

						if (null != propositos && !propositos.isEmpty()) {
							record.setIdpropsepa(propositos.get(0).getIdproposito());
						}

					}

					// Buscamos el propósito para otras transferencias por defecto de la institución
					// y lo seteamos en el pago
					genParametrosExample = new GenParametrosExample();
					genParametrosExample.createCriteria()
							.andParametroEqualTo(SigaConstants.PARAMETRO_PROPOSITO_OTRA_TRANFERENCIA)
							.andModuloEqualTo(SigaConstants.MODULO_FACTURACION)
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> genParametrosMapper.selectByExample() -> Obtenenos el proposito para otras transferencias por defecto");
					List<GenParametros> parametrosPropOtraTrans = genParametrosMapper
							.selectByExample(genParametrosExample);

					if (null != parametrosPropOtraTrans && !parametrosPropOtraTrans.isEmpty()) {

						FacPropositosExample facPropositosExample = new FacPropositosExample();
						facPropositosExample.createCriteria()
								.andCodigoEqualTo(parametrosPropOtraTrans.get(0).getValor());
						List<FacPropositos> propositos = facPropositosExtendsMapper
								.selectByExample(facPropositosExample);

						if (null != propositos && !propositos.isEmpty()) {
							record.setIdpropotros(propositos.get(0).getIdproposito());
						}
					}

					// Si sólo encontramos un sufijo lo ponemos por defecto en el pago
					FacSufijoExample facSufijoExample = new FacSufijoExample();
					facSufijoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> facSufijoExtendsMapper.selectByExample() -> Obtenenos la lista de sufijos");
					List<FacSufijo> listaSufijos = facSufijoExtendsMapper.selectByExample(facSufijoExample);
					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> facSufijoExtendsMapper.selectByExample() -> Lista de sufijos obtenida");

					if (listaSufijos.size() == 1) {
						record.setIdsufijo(listaSufijos.get(0).getIdsufijo());
					}

					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.insertSelective() -> Entrada al método para insertar el nuevo pago: "
									+ newId.getNewId());
					response = fcsPagosjgExtendsMapper.insertSelective(record);
					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.insertSelective() -> Salida del método para insertar el nuevo pago: "
									+ newId.getNewId());

					FcsPagosEstadospagos record2 = new FcsPagosEstadospagos();
					record2.setIdinstitucion(idInstitucion);
					record2.setIdpagosjg(Integer.valueOf(newId.getNewId()));
					record2.setIdestadopagosjg(Short.valueOf("10"));
					record2.setFechaestado(new Date());
					record2.setFechamodificacion(new Date());
					record2.setUsumodificacion(usuarios.get(0).getIdusuario());

					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> fcsPagosEstadospagosMapper.insertSelective() -> Entrada al método para insertar el nuevo estado al pago: "
									+ newId.getNewId());
					fcsPagosEstadospagosMapper.insertSelective(record2);
					LOGGER.info(
							"PagoSJCSServiceImpl.savePago() -> fcsPagosEstadospagosMapper.insertSelective() -> Salida del método para insertar el nuevo estado al pago: "
									+ newId.getNewId());

					if (response == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("general.message.error.realiza.accion");
					} else {
						insertResponseDTO.setStatus(SigaConstants.OK);
						insertResponseDTO.setId(newId.getNewId());
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error("PagoSJCSServiceImpl.savePago() -> Se ha producido un error en la creación del pago", e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.savePago() -> Salida del servicio para la creación de un pago");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updatePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.updatePago() -> Entrada al servicio para la actualización de un pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.updatePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.updatePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					FcsPagosjg record = new FcsPagosjg();
					record.setIdinstitucion(idInstitucion);
					record.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
					record.setIdfacturacion(Integer.valueOf(pagosjgItem.getIdFacturacion()));
					record.setNombre(pagosjgItem.getNombre());
					record.setAbreviatura(pagosjgItem.getAbreviatura());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setFechamodificacion(new Date());

					LOGGER.info(
							"PagoSJCSServiceImpl.updatePago() -> fcsPagosjgMapper.updateByPrimaryKeySelective() -> Entrada al método para actualizar el pago: "
									+ pagosjgItem.getIdPagosjg());
					response = fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(record);
					LOGGER.info(
							"PagoSJCSServiceImpl.updatePago() -> fcsPagosjgMapper.updateByPrimaryKeySelective() -> Salida del método para actualizar el pago: "
									+ pagosjgItem.getIdPagosjg());

					if (response == 0) {
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("general.message.error.realiza.accion");
					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error("PagoSJCSServiceImpl.updatePago() -> Se ha producido un error en la actualización del pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.updatePago() -> Salida del servicio para la actualización de un pago");

		return updateResponseDTO;
	}

	@Override
	public DeleteResponseDTO deletePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.deletePago() -> Entrada al servicio para el borrado fisico del pago: "
				+ pagosjgItem.getIdPagosjg());

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.deletePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.deletePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
					fcsPagosjgKey.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
					fcsPagosjgKey.setIdinstitucion(idInstitucion);

					FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
					// Obtenemos el idFacturacion
					Integer idFacturacion = pago.getIdfacturacion();

					// Recupero el nombre de los ficheros asociados a la facturacion
					Hashtable nombreFicheros = utilidadesFacturacionSJCS.getNombreFicherosPago(idInstitucion,
							idFacturacion, pago.getIdpagosjg(), null);

					// deshaciendo los cobros de retenciones judiciales
					FcsCobrosRetencionjudicialExample fcsCobrosRetencionjudicialExample = new FcsCobrosRetencionjudicialExample();
					fcsCobrosRetencionjudicialExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpagosjgEqualTo(pago.getIdpagosjg());

					List<FcsCobrosRetencionjudicial> vAuxCobrosRetencionJudicial = fcsCobrosRetencionjudicialMapper
							.selectByExample(fcsCobrosRetencionjudicialExample);
					if ((vAuxCobrosRetencionJudicial != null) && (!vAuxCobrosRetencionJudicial.isEmpty())) {
						fcsCobrosRetencionjudicialMapper.deleteByExample(fcsCobrosRetencionjudicialExample);
					}

					FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
					fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpagosjgEqualTo(pago.getIdpagosjg());

					List<FcsPagoGrupofactHito> vAuxPagoGrupoFactHito = fcsPagoGrupofactHitoMapper
							.selectByExample(fcsPagoGrupofactHitoExample);
					if ((vAuxPagoGrupoFactHito != null) && (!vAuxPagoGrupoFactHito.isEmpty())) {
						fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
					}

					FcsAplicaMovimientosvariosExample fcsAplicaMovimientosvariosExample = new FcsAplicaMovimientosvariosExample();
					fcsAplicaMovimientosvariosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpagosjgEqualTo(pago.getIdpagosjg());

					List<FcsAplicaMovimientosvarios> vAuxMovimientosAplicados = fcsAplicaMovimientosvariosMapper
							.selectByExample(fcsAplicaMovimientosvariosExample);
					if ((vAuxMovimientosAplicados != null) && (!vAuxMovimientosAplicados.isEmpty())) {
						fcsAplicaMovimientosvariosMapper.deleteByExample(fcsAplicaMovimientosvariosExample);
					}

					FcsPagoColegiadoExample fcsPagoColegiadoExample = new FcsPagoColegiadoExample();
					fcsPagoColegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdpagosjgEqualTo(pago.getIdpagosjg());

					List<FcsPagoColegiado> vAuxPagoColegiado = fcsPagoColegiadoExtendsMapper
							.selectByExample(fcsPagoColegiadoExample);
					if ((vAuxPagoColegiado != null) && (!vAuxPagoColegiado.isEmpty())) {
						fcsPagoColegiadoExtendsMapper.deleteByExample(fcsPagoColegiadoExample);
					}

					FcsPagosEstadospagosExample fcsPagosEstadospagosExample = new FcsPagosEstadospagosExample();
					fcsPagosEstadospagosExample.createCriteria().andIdpagosjgEqualTo(pago.getIdpagosjg())
							.andIdinstitucionEqualTo(idInstitucion);

					fcsPagosEstadospagosMapper.deleteByExample(fcsPagosEstadospagosExample);

					// Borramos: se hace borrado en cascada de todos los apuntes, movimientos
					// varios, estados, criterios pago y cobros de retenciones judiciales
					if (fcsPagosjgExtendsMapper.deleteByPrimaryKey(fcsPagosjgKey) == 1) {
						// borrado fisico de ficheros del servidor web
						utilidadesFacturacionSJCS.borrarFicheros(idInstitucion, nombreFicheros);
					}

					deleteResponseDTO.setStatus(SigaConstants.OK);

				}

			}

		} catch (Exception e) {
			LOGGER.error("PagoSJCSServiceImpl.deletePago() -> Se ha producido un error en la eliminación del pago: "
					+ pagosjgItem.getIdPagosjg(), e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.deletePago() -> Salida del servicio para el borrado fisico del pago: "
				+ pagosjgItem.getIdPagosjg());

		return deleteResponseDTO;
	}

	@Override
	public ConceptoPagoDTO comboConceptosPago(String idFacturacion, HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.comboConceptosPago() -> Entrada al servicio para obtener el combo de los conceptos del pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.comboConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.comboConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Entrada para obtener el combo de los conceptos del pago según su facturación");

					List<ConceptoPagoItem> listaConceptosFac = fcsPagosjgExtendsMapper.comboConceptosPago(idInstitucion,
							idFacturacion, usuarios.get(0).getIdlenguaje());
					conceptoPagoDTO.setListaConceptos(listaConceptosFac);
					LOGGER.info(
							"PagoSJCSServiceImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Salida de obtener el combo de los conceptos del pago según su facturación");

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.comboConceptosPago() -> Se ha producido un error en la obtención del combo de conceptos del pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		conceptoPagoDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.comboConceptosPago() -> Salida del servicio para obtener el combo de los conceptos del pago");

		return conceptoPagoDTO;
	}

//	@Override
//	public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request) {
//
//		LOGGER.info(
//				"PagoSJCSServiceImpl.getConceptosPago() -> Entrada al servicio para obtener los conceptos del pago: "
//						+ idPago);
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
//		Error error = new Error();
//		List<Integer> responses = new ArrayList<Integer>();
//		List<FcsFactGrupofactHito> listaConceptosFac = new ArrayList<FcsFactGrupofactHito>();
//		List<ConceptoPagoItem> listaConceptosPago = new ArrayList<ConceptoPagoItem>();
//
//		try {
//
//			if (null != idInstitucion) {
//
//				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//				exampleUsuarios.createCriteria().andNifEqualTo(dni)
//						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//				LOGGER.info(
//						"PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//				LOGGER.info(
//						"PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				if (null != usuarios && !usuarios.isEmpty()) {
//
//					FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
//					fcsFactGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//							.andIdfacturacionEqualTo(Integer.valueOf(idFacturacion));
//
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsFactGrupofactHitoMapper.selectByExample() -> Entrada para la obtención de los conceptos asociados a la facturación: "
//									+ idFacturacion);
//					listaConceptosFac = fcsFactGrupofactHitoMapper.selectByExample(fcsFactGrupofactHitoExample);
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsFactGrupofactHitoMapper.selectByExample() -> Salida de la obtención de los conceptos asociados a la facturación: "
//									+ idFacturacion);
//
//					FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
//					fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//							.andIdpagosjgEqualTo(Integer.valueOf(idPago));
//
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Entrada para la eliminación de los conceptos del pago: "
//									+ idPago);
//					fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Salida de la eliminación de los conceptos del pago: "
//									+ idPago);
//
//					if (!listaConceptosFac.isEmpty()) {
//
//						for (FcsFactGrupofactHito fcsFactGrupofactHito : listaConceptosFac) {
//
//							FcsPagoGrupofactHito fcsPagoGrupofactHito = new FcsPagoGrupofactHito();
//							fcsPagoGrupofactHito.setIdinstitucion(idInstitucion);
//							fcsPagoGrupofactHito.setIdpagosjg(Integer.valueOf(idPago));
//							fcsPagoGrupofactHito.setIdhitogeneral(fcsFactGrupofactHito.getIdhitogeneral());
//							fcsPagoGrupofactHito.setIdgrupofacturacion(fcsFactGrupofactHito.getIdgrupofacturacion());
//							fcsPagoGrupofactHito.setFechamodificacion(new Date());
//							fcsPagoGrupofactHito.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//							LOGGER.info(
//									"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Entrada para la inserción de los conceptos del pago: "
//											+ idPago);
//							int response = fcsPagoGrupofactHitoMapper.insertSelective(fcsPagoGrupofactHito);
//							LOGGER.info(
//									"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Salida de la inserción de los conceptos del pago: "
//											+ idPago);
//
//							responses.add(response);
//
//						}
//
//						LOGGER.info(
//								"PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Entrada para la obtención de los conceptos del pago: "
//										+ idPago);
//						listaConceptosPago = fcsPagosjgExtendsMapper.getConceptosPago(idPago, idInstitucion,
//								usuarios.get(0).getIdlenguaje());
//						LOGGER.info(
//								"PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Salida de la obtención de los conceptos del pago: "
//										+ idPago);
//
//						conceptoPagoDTO.setListaConceptos(listaConceptosPago);
//					}
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			LOGGER.error(
//					"PagoSJCSServiceImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
//							+ idPago,
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//		}
//
//		if (!listaConceptosFac.isEmpty() && !responses.isEmpty() && responses.contains(0)) {
//			LOGGER.error(
//					"PagoSJCSServiceImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
//							+ idPago);
//			error.setCode(400);
//			error.setDescription("general.message.error.realiza.accion");
//		}
//
//		conceptoPagoDTO.setError(error);
//
//		LOGGER.info(
//				"PagoSJCSServiceImpl.getConceptosPago() -> Salida del servicio para obtener los conceptos del pago: "
//						+ idPago);
//
//		return conceptoPagoDTO;
//	}

	@Override
	public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.getConceptosPago() -> Entrada al servicio para obtener los conceptos del pago: "
						+ idPago);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
		Error error = new Error();
		List<ConceptoPagoItem> listaConceptosPago = new ArrayList<ConceptoPagoItem>();
		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Entrada para obtener el combo de los conceptos del pago según su facturación");

					List<ConceptoPagoItem> listaConceptosFac = fcsPagosjgExtendsMapper.comboConceptosPago(idInstitucion,
							idFacturacion, usuarios.get(0).getIdlenguaje());
					conceptoPagoDTO.setListaConceptos(listaConceptosFac);

					LOGGER.info(
							"PagoSJCSServiceImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Salida de obtener el combo de los conceptos del pago según su facturación");

					LOGGER.info(
							"PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Entrada para la obtención de los conceptos del pago: "
									+ idPago);
					listaConceptosPago = fcsPagosjgExtendsMapper.getConceptosPago(idPago, idInstitucion,
							usuarios.get(0).getIdlenguaje());
					LOGGER.info(
							"PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Salida de la obtención de los conceptos del pago: "
									+ idPago);

					listaConceptosPago = listaConceptosPago.stream()
							.collect(collectingAndThen(
									toCollection(
											() -> new TreeSet<>(Comparator.comparing(ConceptoPagoItem::getIdConcepto))),
									ArrayList::new));
					
					// Añadir el Importe Pendiente y el Porcentaje Pendiente de la factura en el Pago.
					for (ConceptoPagoItem conceptoPago : listaConceptosPago) {
						String contConceptoPago = conceptoPago.getIdConcepto();
						// Filtro Conceptos Factura.
						List<ConceptoPagoItem> fact = listaConceptosFac.stream()
								.filter(factura -> factura.getIdConcepto().equals(contConceptoPago))
								.collect(Collectors.toList());
						// Mapear los valores.
						conceptoPago.setImportePendiente(fact.get(0).getImportePendiente());
						conceptoPago.setPorcentajePendiente(fact.get(0).getPorcentajePendiente());
					}
				}
				conceptoPagoDTO.setListaConceptos(listaConceptosPago);
			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
							+ idPago,
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		conceptoPagoDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.getConceptosPago() -> Salida del servicio para obtener los conceptos del pago: "
						+ idPago);

		return conceptoPagoDTO;
	}

	@Override
	public UpdateResponseDTO saveConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
			HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.saveConceptoPago() -> Entrada al servicio para actualizar conceptos asociados al pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		List<Integer> responses = null;

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.saveConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.saveConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					if (null != listaConceptoPagoItem && !listaConceptoPagoItem.isEmpty()) {

						responses = new ArrayList<Integer>();

						// Obtenemos los datos del pago sobre el que actuaremos
						FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
						fcsPagosjgKey.setIdinstitucion(idInstitucion);
						fcsPagosjgKey.setIdpagosjg(Integer.valueOf(listaConceptoPagoItem.get(0).getIdPagosjg()));

						LOGGER.info(
								"PagoSJCSServiceImpl.saveConceptoPago() -> fcsPagosjgExtendsMapper.selectByPrimaryKey() -> Obtenemos el pago");
						FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

						// Dependiendo de el tipo de concepto actualizaremos el importe correspondiente
						// y el importe pagado
						for (ConceptoPagoItem concepto : listaConceptoPagoItem) {

							if (null != concepto.getCantidadApagar()) {

								if (concepto.getCantidadApagar() == 0
										&& !hayMovimientosVariosPositivosAaplicar(idInstitucion,
												concepto.getIdFacturacion())) {
									error.setCode(400);
									error.setDescription("facturacionSJCS.facturacionesYPagos.errorImporteCriterio");
									throw new Exception(
											"No se puede introducir un importe igual a 0 si no hay movimientos varios positivos a aplicar");
								}

								switch (concepto.getIdConcepto()) {
								case "10":
									pago.setImporteoficio(BigDecimal.valueOf(
											pago.getImporteoficio().doubleValue() + concepto.getCantidadApagar()));
									pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
											+ pago.getImporteoficio().doubleValue()));
									break;
								case "20":
									pago.setImporteguardia(BigDecimal.valueOf(
											pago.getImporteguardia().doubleValue() + concepto.getCantidadApagar()));
									pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
											+ pago.getImporteguardia().doubleValue()));
									break;
								case "30":
									pago.setImportesoj(BigDecimal.valueOf(
											pago.getImportesoj().doubleValue() + concepto.getCantidadApagar()));
									pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
											+ pago.getImportesoj().doubleValue()));
									break;
								case "40":
									pago.setImporteejg(BigDecimal.valueOf(
											pago.getImporteejg().doubleValue() + concepto.getCantidadApagar()));
									pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
											+ pago.getImporteejg().doubleValue()));
									break;
								}

								LOGGER.info(
										"PagoSJCSServiceImpl.saveConceptoPago() -> fcsFactGrupofactHitoExtendsMapper.selectByExample() -> Buscamos los conceptos de facturación con IDHITOGENERAL = "
												+ concepto.getIdConcepto());
								FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
								fcsFactGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdfacturacionEqualTo(Integer.valueOf(concepto.getIdFacturacion()))
										.andIdhitogeneralEqualTo(Short.valueOf(concepto.getIdConcepto()));

								List<FcsFactGrupofactHito> listaFcsFactGrupofactHito = fcsFactGrupofactHitoExtendsMapper
										.selectByExample(fcsFactGrupofactHitoExample);

								// Recorremos los conceptos de la facturación con el idHitogeneral buscado y por
								// cada línea insertamos una en los conceptos del pago
								for (FcsFactGrupofactHito fcsFactGrupofactHito : listaFcsFactGrupofactHito) {

									FcsPagoGrupofactHito fcsPagoGrupofactHito = new FcsPagoGrupofactHito();
									fcsPagoGrupofactHito.setIdinstitucion(idInstitucion);
									fcsPagoGrupofactHito.setIdpagosjg(fcsPagosjgKey.getIdpagosjg());
									fcsPagoGrupofactHito.setIdhitogeneral(Short.valueOf(concepto.getIdConcepto()));
									fcsPagoGrupofactHito
											.setIdgrupofacturacion(fcsFactGrupofactHito.getIdgrupofacturacion());
									fcsPagoGrupofactHito.setFechamodificacion(new Date());
									fcsPagoGrupofactHito.setUsumodificacion(usuarios.get(0).getIdusuario());

									LOGGER.info(
											"PagoSJCSServiceImpl.saveConceptoPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Insertamos el concepto del pago");
									int response = fcsPagoGrupofactHitoMapper.insertSelective(fcsPagoGrupofactHito);
									responses.add(response);
								}

							}

						}

						LOGGER.info(
								"PagoSJCSServiceImpl.saveConceptoPago() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos del pago");
						pago.setImporterepartir(pago.getImportepagado());
						pago.setFechamodificacion(new Date());
						pago.setUsumodificacion(usuarios.get(0).getIdusuario());
						fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(pago);

					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.saveConceptoPago() -> Se ha producido un error al introducir los conceptos del pago",
					e);
			if (null == error.getCode() || error.getCode() != 400) {
				error.setCode(500);
				error.setDescription("general.mensaje.error.bbdd");
			}
		}

		if (null != responses && responses.contains(0)) {
			LOGGER.error(
					"PagoSJCSServiceImpl.saveConceptoPago() -> Se ha producido un error al introducir los conceptos del pago");
			error.setCode(400);
			error.setDescription("general.message.error.realiza.accion");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.saveConceptoPago() -> Salida del servicio para actualizar conceptos asociados al pago");

		return updateResponseDTO;
	}

	private boolean hayMovimientosVariosPositivosAaplicar(Short idInstitucion, String idFacturacion) {
		int numero = fcsPagosjgExtendsMapper.hayMovimientosVariosPositivosAaplicar(idInstitucion, idFacturacion);
		return (numero > 0);
	}

	@Override
	public DeleteResponseDTO deleteConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
			HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.deleteConceptoPago() -> Entrada al servicio para desasociar cenceptos del pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		List<Integer> responses = null;

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.deleteConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.deleteConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					if (null != listaConceptoPagoItem && !listaConceptoPagoItem.isEmpty()) {

						responses = new ArrayList<Integer>();

						// Obtenemos el pago
						FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
						fcsPagosjgKey.setIdinstitucion(idInstitucion);
						fcsPagosjgKey.setIdpagosjg(Integer.valueOf(listaConceptoPagoItem.get(0).getIdPagosjg()));

						LOGGER.info(
								"PagoSJCSServiceImpl.deleteConceptoPago() -> fcsPagosjgExtendsMapper.selectByPrimaryKey() -> Obtenemos el pago");
						FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

						// Dependiendo del concepto de pago, restaremos la cantidad al importe pagado y
						// además actualizaremos a 0 el campo correspondiente
						for (ConceptoPagoItem concepto : listaConceptoPagoItem) {

							switch (concepto.getIdConcepto()) {
							case "10":
								pago.setImportepagado(BigDecimal.valueOf(
										pago.getImportepagado().doubleValue() - pago.getImporteoficio().doubleValue()));
								pago.setImporteoficio(BigDecimal.ZERO);
								break;
							case "20":
								pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
										- pago.getImporteguardia().doubleValue()));
								pago.setImporteguardia(BigDecimal.ZERO);
								break;
							case "30":
								pago.setImportepagado(BigDecimal.valueOf(
										pago.getImportepagado().doubleValue() - pago.getImportesoj().doubleValue()));
								pago.setImportesoj(BigDecimal.ZERO);
								break;
							case "40":
								pago.setImportepagado(BigDecimal.valueOf(
										pago.getImportepagado().doubleValue() - pago.getImporteejg().doubleValue()));
								pago.setImporteejg(BigDecimal.ZERO);
								break;
							}

							// Eliminamos el concepto del pago
							FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
							fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdhitogeneralEqualTo(Short.valueOf(concepto.getIdConcepto()))
									.andIdpagosjgEqualTo(Integer.valueOf(concepto.getIdPagosjg()));

							LOGGER.info(
									"PagoSJCSServiceImpl.deleteConceptoPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Eliminamos el concepto del pago");
							int response = fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
							responses.add(response);

						}

						LOGGER.info(
								"PagoSJCSServiceImpl.deleteConceptoPago() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos del pago");
						pago.setImporterepartir(pago.getImportepagado());
						pago.setFechamodificacion(new Date());
						pago.setUsumodificacion(usuarios.get(0).getIdusuario());
						fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(pago);

					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.deleteConceptoPago() -> Se ha producido un error al intentar desasociar cenceptos del pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		if (null != responses && responses.contains(0)) {
			LOGGER.error(
					"PagoSJCSServiceImpl.deleteConceptoPago() -> Se ha producido un error al intentar desasociar cenceptos del pago");
			error.setCode(400);
			error.setDescription("general.message.error.realiza.accion");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		} else {
			deleteResponseDTO.setStatus(SigaConstants.OK);
		}

		deleteResponseDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.deleteConceptoPago() -> Salida del servicio para desasociar cenceptos del pago");

		return deleteResponseDTO;
	}

	@Override
	public ComboDTO comboPropTranferenciaSepa(HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> Entrada al servicio para obtener el combo de propósitos para transferencias SEPA");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> facPropositosExtendsMapper.comboPropTranferencia() -> LLamada al servicio para obtener el combo");
					List<ComboItem> combo = facPropositosExtendsMapper
							.comboPropTranferencia(usuarios.get(0).getIdlenguaje(), false);
					LOGGER.info(
							"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> facPropositosExtendsMapper.comboPropTranferencia() -> Salida del servicio con el combo");
					comboDTO.setCombooItems(combo);

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> Se ha producido un error al intentar obtener el combo de propósitos para transferencias SEPA",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> Salida del servicio para obtener el combo de propósitos para transferencias SEPA");

		return comboDTO;
	}

	@Override
	public ComboDTO comboPropOtrasTranferencias(HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> Entrada al servicio para obtener el combo de propósitos para otras transferencias");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> facPropositosExtendsMapper.comboPropTranferencia() -> LLamada al servicio para obtener el combo");
					List<ComboItem> combo = facPropositosExtendsMapper
							.comboPropTranferencia(usuarios.get(0).getIdlenguaje(), true);
					LOGGER.info(
							"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> facPropositosExtendsMapper.comboPropTranferencia() -> Salida del servicio con el combo");
					comboDTO.setCombooItems(combo);

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> Se ha producido un error al intentar obtener el combo de propósitos para otras transferencias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> Salida del servicio para obtener el combo de propósitos para otras transferencias");

		return comboDTO;
	}

	@Override
	public ComboDTO comboSufijos(HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.comboSufijos() -> Entrada al servicio para obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.comboSufijos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.comboSufijos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.comboSufijos() -> facSufijoExtendsMapper.comboSufijos() -> Entrada al servicio para obtener el combo");
					List<ComboItem> comboSufijos = facSufijoExtendsMapper.comboSufijos(idInstitucion);
					LOGGER.info(
							"PagoSJCSServiceImpl.comboSufijos() -> facSufijoExtendsMapper.comboSufijos() -> Salida del servicio con el combo obtenido");
					comboDTO.setCombooItems(comboSufijos);

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.comboSufijos() -> Se ha producido un error al intentar obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.comboSufijos() -> Salida del servicio para obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria");

		return comboDTO;
	}

	@Override
	public ComboDTO comboCuentasBanc(HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.comboCuentasBanc() -> Entrada al servicio para obtener el combo de cuentas bancarias");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.comboCuentasBanc() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.comboCuentasBanc() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.comboCuentasBanc() -> facBancoinstitucionExtendsMapper.comboCuentasBanc() -> Entrada al servicio para obtener el combo");
					List<ComboItem> comboCuentasBancarias = facBancoinstitucionExtendsMapper
							.comboCuentasBanc(idInstitucion);
					LOGGER.info(
							"PagoSJCSServiceImpl.comboCuentasBanc() -> facBancoinstitucionExtendsMapper.comboCuentasBanc() -> Salida del servicio con el combo obtenido");
					comboDTO.setCombooItems(comboCuentasBancarias);

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.comboCuentasBanc() -> Se ha producido un error al intentar obtener el combo de cuentas bancarias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.comboCuentasBanc() -> Salida del servicio para obtener el combo de cuentas bancarias");

		return comboDTO;
	}

	@Override
	public UpdateResponseDTO saveConfigFichAbonos(PagosjgItem pagosjgItem, HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.saveConfigFichAbonos() -> Entrada al servicio para guardar la configuración de ficheros de abonos");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.saveConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.saveConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					FcsPagosjg record = new FcsPagosjg();
					record.setIdinstitucion(idInstitucion);
					record.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuarios.get(0).getIdusuario());
					record.setBancosCodigo(pagosjgItem.getCodBanco());
					record.setIdsufijo(Short.valueOf(pagosjgItem.getIdSufijo()));
					record.setIdpropsepa(Short.valueOf(pagosjgItem.getIdPropSepa()));
					record.setIdpropotros(Short.valueOf(pagosjgItem.getIdPropOtros()));

					LOGGER.info(
							"PagoSJCSServiceImpl.saveConfigFichAbonos() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos en el pago");
					response = fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(record);
					LOGGER.info(
							"PagoSJCSServiceImpl.saveConfigFichAbonos() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Pago actualizado: "
									+ response);
				}

			}

			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				error.setCode(500);
				error.setDescription("general.message.error.realiza.accion");

			} else {
				updateResponseDTO.setStatus(SigaConstants.OK);
				updateResponseDTO.setId(pagosjgItem.getIdPagosjg());
			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.saveConfigFichAbonos() -> Se ha producido un error al intentar guardar la configuración de ficheros de abonos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.saveConfigFichAbonos() -> Salida del servicio para guardar la configuración de ficheros de abonos");

		return updateResponseDTO;
	}

	@Override
	public PagosjgDTO getConfigFichAbonos(String idPago, HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.getConfigFichAbonos() -> Entrada al servicio para obtener la configuración de ficheros de abonos");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PagosjgDTO pagosjgDTO = new PagosjgDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.getConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.getConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.getConfigFichAbonos() -> fcsPagosjgExtendsMapper.getConfigFichAbonos() -> Entrada a la obtención de los datos");
					List<PagosjgItem> datosConfigFichAbonos = fcsPagosjgExtendsMapper.getConfigFichAbonos(idPago,
							idInstitucion);
					LOGGER.info(
							"PagoSJCSServiceImpl.getConfigFichAbonos() -> fcsPagosjgExtendsMapper.getConfigFichAbonos() -> Salida con los datos obtenidos");
					pagosjgDTO.setPagosjgItem(datosConfigFichAbonos);

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.getConfigFichAbonos() -> Se ha producido un error al intentar obtener la configuración de ficheros de abonos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		pagosjgDTO.setError(error);

		LOGGER.info(
				"PagoSJCSServiceImpl.getConfigFichAbonos() -> Salida del servicio para obtener la configuración de ficheros de abonos");

		return pagosjgDTO;
	}

	@Override
	public StringDTO getNumApuntesPago(String idPago, HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.getNumApuntesPago() -> Entrada al servicio para obtener el número de apuntes del pago: "
						+ idPago);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO stringDTO = new StringDTO();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.getNumApuntesPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.getNumApuntesPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.getNumApuntesPago() -> fcsPagosjgExtendsMapper.getNumApuntesPago() -> Entrada para obtener el número de apuntes");
					stringDTO = fcsPagosjgExtendsMapper.getNumApuntesPago(idPago, idInstitucion,
							usuarios.get(0).getIdlenguaje());
					LOGGER.info(
							"PagoSJCSServiceImpl.getNumApuntesPago() -> fcsPagosjgExtendsMapper.getNumApuntesPago() -> Salida, número de apuntes: "
									+ stringDTO.getValor());

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.getNumApuntesPago() -> Se ha producido un error al intentar obtener el número de apuntes del pago:"
							+ idPago,
					e);
		}

		LOGGER.info(
				"PagoSJCSServiceImpl.getNumApuntesPago() -> Salida del servicio para obtener el número de apuntes del pago: "
						+ idPago);

		return stringDTO;
	}

	@Override
	public CompensacionFacDTO getCompensacionFacturas(String idPago, HttpServletRequest request) {

		LOGGER.info(
				"PagoSJCSServiceImpl.getCompensacionFacturas() -> Entrada al servicio para obtener todas las facturas pendientes de colegiados que estén en el pago para compensar");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CompensacionFacDTO compensacionFacDTO = new CompensacionFacDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.getCompensacionFacturas() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.getCompensacionFacturas() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					LOGGER.info(
							"PagoSJCSServiceImpl.getCompensacionFacturas() -> fcsPagosjgExtendsMapper.getCompensacionFacturas() -> Entrada al servicio para obtener las compensaciones del pago: "
									+ idPago);
					List<CompensacionFacItem> compensaciones = fcsPagosjgExtendsMapper.getCompensacionFacturas(idPago,
							idInstitucion);
					LOGGER.info(
							"PagoSJCSServiceImpl.getCompensacionFacturas() -> fcsPagosjgExtendsMapper.getCompensacionFacturas() -> Salida del servicio para obtener las compensaciones del pago: "
									+ idPago);
					compensacionFacDTO.setCompensaciones(compensaciones);

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.getCompensacionFacturas() -> Se ha producido un error al intentar obtener todas las facturas pendientes de colegiados que estén en el pago para compensar",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		compensacionFacDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.getCompensacionFacturas() -> Salida del servicio");

		return compensacionFacDTO;
	}

	@Override
	public InsertResponseDTO ejecutarPagoSJCS(String idPago, boolean simular, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		List<AdmUsuarios> usuarios = null;
		FcsPagosjg pago = null;
		boolean ponerEnEstadoAbierto = true;

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.ejecutarPagoSJCS() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.ejecutarPagoSJCS() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {
					if (FacturacionSJCSServicesImpl.isAlguienEjecutando()) {
						LOGGER.info(
								"YA SE ESTA EJECUTANDO LA FACTURACIÓN SJCS EN BACKGROUND. CUANDO TERMINE SE INICIARA OTRA VEZ EL PROCESO DE DESBLOQUEO.");
						insertResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(400);
						error.setDescription("facturacionSJCS.facturacionPagos.pagos.errorDeshacerCierreProceso");
					} else {
						FacturacionSJCSServicesImpl.setAlguienEjecutando();
						try {
							AdmUsuarios usuario = usuarios.get(0);

							// Antes de ejecutar el pago comprobamos si tiene banco asociado
							FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
							fcsPagosjgKey.setIdinstitucion(idInstitucion);
							fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

							LOGGER.info(
									"UtilidadesPagoSJCS.ejecutarPagoSJCS() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
											+ idPago);
							pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
							LOGGER.info(
									"UtilidadesPagoSJCS.ejecutarPagoSJCS() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
											+ idPago);

							if (UtilidadesString.esCadenaVacia(pago.getBancosCodigo())) {
								throw new FacturacionSJCSException("Debe de seleccionar una cuenta bancaria",
										"factSJCS.abonos.configuracion.literal.cuentaObligatoria");
							}

							String estadoPago = fcsPagosjgExtendsMapper.getEstadoPago(idPago, idInstitucion);
							String criterioTurno = pago.getCriteriopagoturno();

							// Validacion de los datos antes de ejecutar el pago:
							// 1. El estado del pago debe ser abierto:
							if (!estadoPago.equals(SigaConstants.ESTADO_PAGO_ABIERTO)) {
								ponerEnEstadoAbierto = false;
								LOGGER.error(
										"El pago no se encuentra en un estado correcto para realizar esta operación");
								throw new FacturacionSJCSException(
										"El pago no se encuentra en un estado correcto para realizar esta operación",
										"messages.factSJCS.error.estadoPagoNoCorrecto");
							}
							// 2. Criterios correctos del Turno:
							if (!criterioTurno.equals(SigaConstants.CRITERIOS_PAGO_FACTURACION)) {
								LOGGER.error("No ha sido configurado el criterio de pago.");
								throw new FacturacionSJCSException("No ha sido configurado el criterio de pago.",
										"messages.factSJCS.error.criterioPago");
							}

							// 3. Si no se ha introducido importe a pagar el importe a facturar será cero
							if (pago.getImporterepartir().doubleValue() == 0.00) {
								LOGGER.error(
										"El importe a facturar será cero, introduzca importe a pagar distinto de cero.");
								throw new FacturacionSJCSException(
										"El importe a facturar será cero, introduzca importe a pagar distinto de cero.",
										"messages.facturacionSJCS.abono.sin.importe.pago");
							}

							utilidadesPagoSJCS.asyncEjecutarPagoSJCS(pago, simular, idInstitucion, usuario);
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
							LOGGER.error(e.getStackTrace());
							FacturacionSJCSServicesImpl.setNadieEjecutando();
							throw e;
						}
					}
				}

			}

		} catch (FacturacionSJCSException fe) {
			LOGGER.error(fe.getMessage());
			LOGGER.error(fe.getStackTrace());
			if (ponerEnEstadoAbierto) {
				utilidadesPagoSJCS.ponerPagoEstadoAbierto(pago, idInstitucion, usuarios.get(0));
			}
			error.setDescription(fe.getDescription());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace());
			if (simular && e.getMessage().equals("messages.facturacionSJCS.pago.simulado")) {
				error.setDescription(null);
			} else {
				LOGGER.error(
						"PagoSJCSServiceImpl.ejecutarPagoSJCS() -> Se ha producido en la ejecución del pago: " + idPago,
						e);
				error.setCode(500);
				error.setDescription("general.mensaje.error.bbdd");
			}
		}

		insertResponseDTO.setError(error);

		if (error.getDescription() == null) {
			insertResponseDTO.setStatus(SigaConstants.OK);
		} else {
			insertResponseDTO.setStatus(SigaConstants.KO);
		}

		return insertResponseDTO;
	}

	/**
	 * Funcion que devuelve los colegiados que interviene en un pago en Actuaciones
	 * Designas, Asistencias, EJG's, Guardias y/o SOJ's y Movimientos.
	 * <p>
	 * listaPagoSoloIncluirMorosos = 0 listaPagoSoloIncluirNoMorosos = 1
	 * listaPagoTodos = 2
	 *
	 * @param idInstitucion
	 * @param idPago
	 * @return List<FcsPagoColegiado>
	 */
	private List<FcsPagoColegiado> getColegiadosApagar(Short idInstitucion, String idPago, int caseMorosos,
			AdmUsuarios usuario) throws Exception {

		List<FcsPagoColegiado> colegiados;

		try {

			colegiados = fcsPagosjgExtendsMapper.getColegiadosApagar(idInstitucion, idPago, caseMorosos);

		} catch (Exception e) {
			throw new FacturacionSJCSException("Error al obtener los colegiados a pagar", e,
					"messages.factSJCS.error.colegiadosApagar");
		}

		return colegiados;
	}

	private double redondea(double numero, int precision) {

		if (Double.isNaN(numero)) // Contolo NaN
			return 0.0;

		// Calcula el signo
		BigDecimal bdSigno = new BigDecimal("1");
		if (numero < 0) {
			bdSigno = new BigDecimal("-1");
		}

		// Calcula la precision
		BigDecimal bdPrecision = new BigDecimal("1");
		for (int i = 0; i < precision; i++) {
			bdPrecision = bdPrecision.multiply(new BigDecimal("10"));
		}

		BigDecimal bCalculo = BigDecimal.valueOf(numero); // Conversion double to BigDecimal

		bCalculo = bCalculo.multiply(bdSigno); // Control inicial del signo

		bCalculo = bCalculo.multiply(bdPrecision); // Pone la parte decimal dentro de la precision como entero

		bCalculo = bCalculo.add(new BigDecimal("0.5")); // Sumo 0.5

		RoundingMode RM = RoundingMode.DOWN;
		bCalculo = bCalculo.setScale(0, RM); // Obtengo la parte entera
		// bCalculo = BigDecimal.valueOf(bCalculo.intValue());

		bCalculo = bCalculo.divide(bdPrecision); // Vuelvo a poner la parte decimal

		bCalculo = bCalculo.multiply(bdSigno); // Control final del signo

		return bCalculo.doubleValue();
	}

	/**
	 * Método que implementa la accion cerrarPago. Modifica el estado del pago a
	 * cerrado.
	 */
	@Override
	public UpdateResponseDTO cerrarPago(String idPago, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.cerrarPago() -> Entrada al servicio para el cierre de un pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		FcsPagosjg pago = null;

		try {

			updateResponseDTO.setStatus(SigaConstants.OK);

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.cerrarPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.cerrarPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
					fcsPagosjgKey.setIdinstitucion(idInstitucion);
					fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

					LOGGER.info(
							"PagoSJCSServiceImpl.cerrarPago() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
									+ idPago);
					pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
					LOGGER.info(
							"PagoSJCSServiceImpl.cerrarPago() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
									+ idPago);

					LOGGER.info(
							"PagoSJCSServiceImpl.cerrarPago() -> fcsPagosEstadospagosMapper.insertSelective() -> Insertamo el estado CERRADO para el pago: "
									+ idPago);
					// Insertamos el estado del pago
					utilidadesPagoSJCS.asyncCerrarPagoSJCS(pago, usuarios.get(0));

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.cerrarPago() -> Se ha producido un error en el cierre del pago: " + idPago, e);
			error.setCode(500);
			error.setDescription("formacion.mensaje.general.mensaje.error");
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		updateResponseDTO.setError(error);
		updateResponseDTO.setId(idPago);

		LOGGER.info("PagoSJCSServiceImpl.cerrarPago() -> Salida del servicio para el cierre de un pago");

		return updateResponseDTO;
	}

	/**
	 * Método que implementa la accion cerrarPagoManual. Modifica el estado del pago
	 * a cerrado
	 *
	 * @param idPago
	 * @param idsParaEnviar Son los idPersona
	 * @param request
	 */
	@Override
	public UpdateResponseDTO cerrarPagoManual(String idPago, List<String> idsParaEnviar, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.cerrarPagoManual() -> Entrada al servicio para cerrar manualmente el pago: "
				+ idPago);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		FcsPagosjg pago;

		try {

			updateResponseDTO.setStatus(SigaConstants.OK);

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"PagoSJCSServiceImpl.cerrarPagoManual() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"PagoSJCSServiceImpl.cerrarPagoManual() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {

					FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
					fcsPagosjgKey.setIdinstitucion(idInstitucion);
					fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

					LOGGER.info(
							"PagoSJCSServiceImpl.cerrarPagoManual() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
									+ idPago);
					pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
					LOGGER.info(
							"PagoSJCSServiceImpl.cerrarPagoManual() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
									+ idPago);

					utilidadesPagoSJCS.asyncCerrarPagoManual(pago, idsParaEnviar, usuarios.get(0));
				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"PagoSJCSServiceImpl.cerrarPagoManual() -> Se ha producido un error en el cierre manual del pago: "
							+ idPago,
					e);
			error.setCode(500);
			error.setDescription("formacion.mensaje.general.mensaje.error");
			updateResponseDTO.setStatus(SigaConstants.KO);
		}

		updateResponseDTO.setError(error);
		updateResponseDTO.setId(idPago);

		LOGGER.info("PagoSJCSServiceImpl.cerrarPagoManual() -> Salida del servicio para cerrar manualmente el pago: "
				+ idPago);

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO deshacerCierre(String idPago, HttpServletRequest request) {

		LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> Entrada al servicio para deshacer el cierre del pago: "
				+ idPago);

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		try {

			if (null != idInstitucion) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> admUsuariosExtendsMapper.selectByExample() ->"
						+ " Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> admUsuariosExtendsMapper.selectByExample() ->"
						+ " Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && !usuarios.isEmpty()) {
					if (FacturacionSJCSServicesImpl.isAlguienEjecutando()) {
						LOGGER.info(
								"YA SE ESTA EJECUTANDO LA FACTURACIÓN SJCS EN BACKGROUND. CUANDO TERMINE SE INICIARA OTRA VEZ EL PROCESO DE DESBLOQUEO.");
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setCode(400);
						error.setDescription("facturacionSJCS.facturacionPagos.pagos.errorDeshacerCierreProceso");
					} else {
						FacturacionSJCSServicesImpl.setAlguienEjecutando();
						try {
							FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
							fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));
							fcsPagosjgKey.setIdinstitucion(idInstitucion);

							FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

							utilidadesPagoSJCS.deshacerCierre(pago, idInstitucion, usuarios.get(0));
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
							LOGGER.error(e.getCause());
							LOGGER.error(e);
							throw e;
						} finally {
							FacturacionSJCSServicesImpl.setNadieEjecutando();
						}
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getCause());
			LOGGER.error(e);
			updateResponseDTO.setStatus(SigaConstants.KO);
			error.setCode(500);
			error.setDescription("general.message.error.realiza.accion");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> Salida del servicio para deshacer el cierre del pago: "
				+ idPago);

		return updateResponseDTO;
	}

}
