package org.itcgae.siga.fac.services.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.ComunicacionCobroDTO;
import org.itcgae.siga.DTO.fac.ComunicacionCobroItem;
import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.EstadosPagosDTO;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.DTO.fac.FacFacturacionEliminarItem;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaDTO;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.DTO.fac.FacRegistroFichContaDTO;
import org.itcgae.siga.DTO.fac.FacturaDTO;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FacturaLineaDTO;
import org.itcgae.siga.DTO.fac.FacturaLineaItem;
import org.itcgae.siga.DTO.fac.FacturasIncluidasDTO;
import org.itcgae.siga.DTO.fac.FicherosAbonosDTO;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesDTO;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.DTO.fac.InformeFacturacionDTO;
import org.itcgae.siga.DTO.fac.InformeFacturacionItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.com.ConsultaDestinatarioItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.FinalidadConsultaDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAReferences;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenBancos;
import org.itcgae.siga.db.entities.CenBancosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenGruposcriterios;
import org.itcgae.siga.db.entities.CenGruposcriteriosExample;
import org.itcgae.siga.db.entities.CenGruposcriteriosKey;
import org.itcgae.siga.db.entities.CenSucursalesExample;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaKey;
import org.itcgae.siga.db.entities.EnvComunicacionmorosos;
import org.itcgae.siga.db.entities.EnvComunicacionmorososExample;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoExample;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacAbonoincluidoendisquete;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionExample;
import org.itcgae.siga.db.entities.FacBancoinstitucionKey;
import org.itcgae.siga.db.entities.FacClienincluidoenseriefactur;
import org.itcgae.siga.db.entities.FacClienincluidoenseriefacturExample;
import org.itcgae.siga.db.entities.FacClienincluidoenseriefacturKey;
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.itcgae.siga.db.entities.FacDisqueteabonosKey;
import org.itcgae.siga.db.entities.FacDisquetecargos;
import org.itcgae.siga.db.entities.FacDisquetecargosKey;
import org.itcgae.siga.db.entities.FacDisquetedevoluciones;
import org.itcgae.siga.db.entities.FacDisquetedevolucionesKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaDevolucion;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacFacturacionEliminar;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.entities.FacFacturacionprogramadaExample;
import org.itcgae.siga.db.entities.FacFacturacionprogramadaKey;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisquete;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisqueteKey;
import org.itcgae.siga.db.entities.FacFormapagoserie;
import org.itcgae.siga.db.entities.FacFormapagoserieExample;
import org.itcgae.siga.db.entities.FacGrupcritincluidosenserie;
import org.itcgae.siga.db.entities.FacGrupcritincluidosenserieExample;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.entities.FacHistoricofacturaExample;
import org.itcgae.siga.db.entities.FacLineaabono;
import org.itcgae.siga.db.entities.FacLineaabonoKey;
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.entities.FacLineadevoludisqbancoKey;
import org.itcgae.siga.db.entities.FacLineafactura;
import org.itcgae.siga.db.entities.FacLineafacturaExample;
import org.itcgae.siga.db.entities.FacLineafacturaKey;
import org.itcgae.siga.db.entities.FacPagoabonoefectivo;
import org.itcgae.siga.db.entities.FacPagoabonoefectivoExample;
import org.itcgae.siga.db.entities.FacPagosporcaja;
import org.itcgae.siga.db.entities.FacPagosporcajaExample;
import org.itcgae.siga.db.entities.FacRegistrofichconta;
import org.itcgae.siga.db.entities.FacRenegociacion;
import org.itcgae.siga.db.entities.FacRenegociacionExample;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionBanco;
import org.itcgae.siga.db.entities.FacSeriefacturacionBancoExample;
import org.itcgae.siga.db.entities.FacSeriefacturacionBancoKey;
import org.itcgae.siga.db.entities.FacSeriefacturacionExample;
import org.itcgae.siga.db.entities.FacSeriefacturacionKey;
import org.itcgae.siga.db.entities.FacTipocliincluidoenseriefac;
import org.itcgae.siga.db.entities.FacTipocliincluidoenseriefacExample;
import org.itcgae.siga.db.entities.FacTiposproduincluenfactu;
import org.itcgae.siga.db.entities.FacTiposproduincluenfactuExample;
import org.itcgae.siga.db.entities.FacTiposproduincluenfactuKey;
import org.itcgae.siga.db.entities.FacTiposservinclsenfact;
import org.itcgae.siga.db.entities.FacTiposservinclsenfactExample;
import org.itcgae.siga.db.entities.FacTiposservinclsenfactKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModClasecomunicacionesExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.entities.PysTipoiva;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenBancosMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.EnvComunicacionmorososMapper;
import org.itcgae.siga.db.mappers.FacClienincluidoenseriefacturMapper;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoMapper;
import org.itcgae.siga.db.mappers.FacPagosporcajaMapper;
import org.itcgae.siga.db.mappers.FacRenegociacionMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionBancoMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.CenGruposcriteriosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisqueteabonosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetedevolucionesExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFormapagoserieExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacGrupcritincluidosenserieExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineaabonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineafacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacRegistroFichContaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTipocliincluidoenseriefacExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTiposproduincluenfactuExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTiposservinclsenfactExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoincluidoendisqueteExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Service
public class FacturacionPySServiceImpl implements IFacturacionPySService {

	private static final String RET_OK = "0";

	private Logger LOGGER = Logger.getLogger(FacturacionPySServiceImpl.class);

	@Autowired
	WSCommons commons;

	@Autowired
	private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

	@Autowired
	private FacFacturaMapper facFacturaMapper;

	@Autowired
	private AdmContadorMapper admContadorMapper;

	@Autowired
	private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

	@Autowired
	private FacTiposservinclsenfactExtendsMapper facTiposservinclsenfactExtendsMapper;

	@Autowired
	private FacTiposproduincluenfactuExtendsMapper facTiposproduincluenfactuExtendsMapper;

	@Autowired
	private FacSeriefacturacionBancoMapper facSeriefacturacionBancoMapper;

	@Autowired
	private FacTipocliincluidoenseriefacExtendsMapper facTipocliincluidoenseriefacExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private FacFormapagoserieExtendsMapper facFormapagoserieExtendsMapper;

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Autowired
	private FacDisquetecargosExtendsMapper facDisquetecargosExtendsMapper;

	@Autowired
	private CenBancosMapper cenBancosMapper;

	@Autowired
	private FacClienincluidoenseriefacturMapper facClienincluidoenseriefacturMapper;

	@Autowired
	private FacFacturacionprogramadaExtendsMapper facFacturacionprogramadaExtendsMapper;

	@Autowired
	private FacDisqueteabonosExtendsMapper facDisqueteabonosExtendsMapper;

	@Autowired
	private FacAbonoincluidoendisqueteExtendsMapper facAbonoincluidoendisqueteExtendsMapper;

	@Autowired
	private FacDisquetedevolucionesExtendsMapper facDisquetedevolucionesExtendsMapper;

	@Autowired
	private FacFacturaExtendsMapper facFacturaExtendsMapper;

	@Autowired
	private FacAbonoExtendsMapper facAbonoExtendsMapper;

	@Autowired
	private FacLineafacturaExtendsMapper facLineafacturaExtendsMapper;

	@Autowired
	private FacLineaabonoExtendsMapper facLineaabonoExtendsMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	@Autowired
	private EnvComunicacionmorososMapper envComunicacionmorososMapper;

	@Autowired
	private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

	@Autowired
	private FacRenegociacionMapper facRenegociacionMapper;

	@Autowired
	private FacPagosporcajaMapper facPagosporcajaMapper;

	@Autowired
	private FacPagoabonoefectivoMapper facPagoabonoefectivoMapper;

	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

	@Autowired
	private FacRegistroFichContaExtendsMapper facRegistroFichContaExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CenGruposcriteriosExtendsMapper cenGruposcriteriosExtendsMapper;

	@Autowired
	private FacGrupcritincluidosenserieExtendsMapper facGrupcritincluidosenserieExtendsMapper;

	@Autowired
	private ConConsultasExtendsMapper conConsultasExtendsMapper;

	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Autowired
	private FacFacturaincluidaendisqueteMapper facFacturaincluidaendisqueteMapper;

	@Autowired
	private GenRecursosMapper genRecursosMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private FacLineadevoludisqbancoMapper facLineadevoludisqbancoMapper;

	@Autowired
	private PySTipoIvaExtendsMapper pySTipoIvaExtendsMapper;

	@Autowired
	private ModClasecomunicacionesMapper modClasecomunicacionesMapper;

	@Autowired
	private ModModeloComunicacionExtendsMapper modModeloComunicacionExtendsMapper;

	@Override
	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) throws Exception {

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("borrarCuentasBancarias() -> Entrada al servicio para dar de baja las cuentas bancarias");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"borrarCuentasBancarias() / facBancoInstitucionExtendsMapper.getCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para establecer la fecha de baja");

			// Logica
			for (CuentasBancariasItem cuenta : cuentasBancarias) {

				FacBancoinstitucionKey cuentasbancariasKey = new FacBancoinstitucionKey();
				cuentasbancariasKey.setIdinstitucion(usuario.getIdinstitucion());
				cuentasbancariasKey.setBancosCodigo(cuenta.getBancosCodigo());

				LOGGER.info("\n\nTratamiento de la cuenta con IBAN: " + cuenta.getIBAN() + "\n\n");

				if (Integer.parseInt(cuenta.getNumUsos()) < 1) {
					this.facBancoinstitucionExtendsMapper.deleteByPrimaryKey(cuentasbancariasKey);
				} else {
					FacBancoinstitucion cuentaCambio = this.facBancoinstitucionExtendsMapper
							.selectByPrimaryKey(cuentasbancariasKey);
					if (cuentaCambio != null) {
						cuentaCambio.setFechabaja(new Date());
						this.facBancoinstitucionExtendsMapper.updateByPrimaryKey(cuentaCambio);
					}
				}
			}
		}

		deleteResponseDTO.setStatus(HttpStatus.OK.toString());

		LOGGER.info("borrarCuentasBancarias() -> Salida del servicio para eliminar las cuentas bancarias");

		return deleteResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO reactivarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("reactivarCuentasBancarias() -> Entrada al servicio para reactivar las cuentas bancarias");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"reactivarCuentasBancarias() / facBancoInstitucionExtendsMapper.selectByPrimaryKey() -> Entrada a facBancoInstitucionExtendsMapper para eliminar la fecha de baja");

			// Logica
			for (CuentasBancariasItem cuenta : cuentasBancarias) {

				FacBancoinstitucionKey cuentasbancariasKey = new FacBancoinstitucionKey();
				cuentasbancariasKey.setIdinstitucion(usuario.getIdinstitucion());
				cuentasbancariasKey.setBancosCodigo(cuenta.getBancosCodigo());

				FacBancoinstitucion cuentaCambio = this.facBancoinstitucionExtendsMapper
						.selectByPrimaryKey(cuentasbancariasKey);

				if (cuentaCambio != null) {
					cuentaCambio.setFechabaja(null);
					this.facBancoinstitucionExtendsMapper.updateByPrimaryKey(cuentaCambio);
				}
			}
		}

		updateResponseDTO.setStatus(HttpStatus.OK.toString());

		LOGGER.info("reactivarCuentasBancarias() -> Salida del servicio para reactivar las cuentas bancarias");

		return updateResponseDTO;
	}

	@Override
	public CuentasBancariasDTO getCuentasBancarias(String idCuenta, HttpServletRequest request) throws Exception {

		CuentasBancariasDTO cuentasBancariasDTO = new CuentasBancariasDTO();
		List<CuentasBancariasItem> listaCuentasBancarias;
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();
		LOGGER.info("getCuentasBancarias() -> Entrada al servicio para recuperar el listado de cuentas bancarias");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"getCuentasBancarias() / facBancoInstitucionExtendsMapper.getCuentasBancarias() -> Entrada a facBancoInstitucionExtendsMapper para obtener el listado de cuentas bancarias");

			// Logica
			listaCuentasBancarias = facBancoinstitucionExtendsMapper.getCuentasBancarias(idCuenta,
					usuario.getIdinstitucion());
			listaCuentasBancarias.forEach(cuenta -> {
				cuenta.setDescripcionRepetida(listaCuentasBancarias.stream()
						.anyMatch(c2 -> !cuenta.getBancosCodigo().equals(c2.getBancosCodigo())
								&& cuenta.getDescripcion().equals(c2.getDescripcion())));
			});
			LOGGER.info("getCuentasBancarias() ->" + listaCuentasBancarias.toString());

			// comprobar primero si la lista de cuentas bancarias viene vacia
			cuentasBancariasDTO.setCuentasBancariasITem(listaCuentasBancarias);

		}

		cuentasBancariasDTO.setError(error);

		LOGGER.info("getCuentasBancarias() -> Salida del servicio para obtener el listado de cuentas bancarias");

		return cuentasBancariasDTO;
	}

	@Override
	public CuentasBancariasDTO validarIBANCuentaBancaria(CuentasBancariasItem cuentaBancaria,
			HttpServletRequest request) throws Exception {

		CuentasBancariasDTO cuentasBancariasDTO = new CuentasBancariasDTO();
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("validarIBANCuentaBancaria() -> Entrada al servicio para validar una cuenta bancaria");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			// Logica

			// comprobar primero si la lista de cuentas bancarias viene vacia
			if (UtilidadesString.esCadenaVacia(cuentaBancaria.getIBAN()) || cuentaBancaria.getIBAN().length() != 24) {
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.longitud");
			}

			String iban = cuentaBancaria.getIBAN().replaceAll(" ", "").trim().toUpperCase();

			// La cuenta debe ser española
			if (!iban.substring(0, 2).equals("ES"))
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.ES");

			// Comprobar los dígitos de control
			if (!UtilidadesString.validarIBAN(iban)) {
				throw new Exception("censo.datosBancarios.mensaje.control.ibanIncorrecto");
			}

			String codBanco = iban.substring(4, 8);
			String codSucursal = iban.substring(8, 12);

			// Comprobación del banco
			CenBancosExample bancoExample = new CenBancosExample();
			bancoExample.createCriteria().andCodigoEqualTo(codBanco);
			List<CenBancos> bancos = cenBancosMapper.selectByExample(bancoExample);
			if (bancos == null || bancos.isEmpty())
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.banco");

			// Conprobación de la sucursal
			CenSucursalesExample sucursalesExample = new CenSucursalesExample();
			sucursalesExample.createCriteria().andCodSucursalEqualTo(codSucursal);
			if (bancos == null || bancos.isEmpty())
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.sucursal");

			CuentasBancariasItem resultado = new CuentasBancariasItem();
			resultado.setBIC(bancos.get(0).getBic());
			resultado.setNombre(bancos.get(0).getNombre());

			List<CuentasBancariasItem> respuestas = new ArrayList<>();
			respuestas.add(resultado);
			cuentasBancariasDTO.setCuentasBancariasITem(respuestas);
		}

		cuentasBancariasDTO.setError(error);

		LOGGER.info("validarIBANCuentaBancaria() -> Salida del servicio para validar una cuenta bancaria");

		return cuentasBancariasDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO insertaCuentaBancaria(CuentasBancariasItem cuentaBancaria, HttpServletRequest request)
			throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		FacBancoinstitucion record = new FacBancoinstitucion();

		LOGGER.info("insertaCuentaBancaria() -> Entrada al servicio para crear una cuenta bancaria");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			// Logica
			record.setIdinstitucion(usuario.getIdinstitucion());

			String newBancosCodigo = facBancoinstitucionExtendsMapper
					.getNextIdCuentaBancaria(usuario.getIdinstitucion()).getNewId();
			record.setBancosCodigo(newBancosCodigo);

			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario.getIdusuario());

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getDescripcion())
					&& cuentaBancaria.getDescripcion().trim().length() <= 40) {
				record.setDescripcion(cuentaBancaria.getDescripcion().trim());

				FacBancoinstitucionExample uniqueExample = new FacBancoinstitucionExample();
				uniqueExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion()).andFechabajaIsNull()
						.andDescripcionEqualTo(record.getDescripcion().trim());

				long found = facBancoinstitucionExtendsMapper.countByExample(uniqueExample);

				if (found > 0)
					throw new Exception("facturacion.cuentaBancaria.descripcion.unica");
			}

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getAsientoContable())
					&& cuentaBancaria.getAsientoContable().trim().length() <= 20) {
				record.setAsientocontable(cuentaBancaria.getAsientoContable().trim());
			} else {
				record.setAsientocontable(null);
			}

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getCuentaContableTarjeta())
					&& cuentaBancaria.getCuentaContableTarjeta().trim().length() <= 20) {
				record.setCuentacontabletarjeta(cuentaBancaria.getCuentaContableTarjeta().trim());
			} else {
				record.setCuentacontabletarjeta(null);
			}

			// comprobar primero si la lista de cuentas bancarias viene vacia
			if (UtilidadesString.esCadenaVacia(cuentaBancaria.getIBAN()) || cuentaBancaria.getIBAN().length() != 24) {
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.longitud");
			}

			String iban = cuentaBancaria.getIBAN().replaceAll(" ", "").trim().toUpperCase();

			// La cuenta debe ser española
			if (!iban.substring(0, 2).equals("ES"))
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.ES");

			// Comprobar los dígitos de control
			if (!UtilidadesString.validarIBAN(iban)) {
				throw new Exception("censo.datosBancarios.mensaje.control.ibanIncorrecto");
			}

			String codBanco = iban.substring(4, 8);
			String codSucursal = iban.substring(8, 12);
			String codControl = iban.substring(12, 14);
			String numCuenta = iban.substring(14, 24);

			// Comprobación del banco
			CenBancosExample bancoExample = new CenBancosExample();
			bancoExample.createCriteria().andCodigoEqualTo(codBanco);
			List<CenBancos> bancos = cenBancosMapper.selectByExample(bancoExample);
			if (bancos == null || bancos.isEmpty())
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.banco");

			// Conprobación de la sucursal
			CenSucursalesExample sucursalesExample = new CenSucursalesExample();
			sucursalesExample.createCriteria().andCodSucursalEqualTo(codSucursal);
			if (bancos == null || bancos.isEmpty())
				throw new Exception("facturacion.cuentaBancaria.iban.invalid.sucursal");

			record.setIban(iban);
			record.setCodBanco(codBanco);
			record.setCodSucursal(codSucursal);
			record.setDigitocontrol(codControl);
			record.setNumerocuenta(numCuenta);

			LOGGER.info(
					"insertaCuentaBancaria() / facBancoinstitucionExtendsMapper.insertSelective() -> Entrada a facBancoinstitucionExtendsMapper para crear una nueva cuenta bancaria");

			facBancoinstitucionExtendsMapper.insertSelective(record);

			insertResponseDTO.setId(newBancosCodigo);
		}

		LOGGER.info("insertaCuentaBancaria() -> Salida del servicio para crear una cuenta bancaria");

		return insertResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO actualizaCuentaBancaria(CuentasBancariasItem cuentaBancaria, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();
		FacBancoinstitucion record = new FacBancoinstitucion();

		LOGGER.info("actualizaCuentaBancaria() -> Entrada al servicio para actualizar una cuenta bancaria");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			// Logica

			FacBancoinstitucionKey bancoKey = new FacBancoinstitucionKey();
			bancoKey.setIdinstitucion(usuario.getIdinstitucion());
			bancoKey.setBancosCodigo(cuentaBancaria.getBancosCodigo());
			record = facBancoinstitucionExtendsMapper.selectByPrimaryKey(bancoKey);

			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario.getIdusuario());

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getDescripcion())
					&& cuentaBancaria.getDescripcion().trim().length() <= 40) {
				record.setDescripcion(cuentaBancaria.getDescripcion().trim());

				FacBancoinstitucionExample uniqueExample = new FacBancoinstitucionExample();
				uniqueExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andBancosCodigoNotEqualTo(record.getBancosCodigo()).andFechabajaIsNull()
						.andDescripcionEqualTo(record.getDescripcion().trim());

				long found = facBancoinstitucionExtendsMapper.countByExample(uniqueExample);

				if (found > 0)
					throw new Exception("facturacion.cuentaBancaria.descripcion.unica");
			}

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getAsientoContable())
					&& cuentaBancaria.getAsientoContable().trim().length() <= 20) {
				record.setAsientocontable(cuentaBancaria.getAsientoContable().trim());
			} else {
				record.setAsientocontable(null);
			}

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getCuentaContableTarjeta())
					&& cuentaBancaria.getCuentaContableTarjeta().trim().length() <= 20) {
				record.setCuentacontabletarjeta(cuentaBancaria.getCuentaContableTarjeta().trim());
			} else {
				record.setCuentacontabletarjeta(null);
			}

			// Actualización de la tarjeta de comisión
			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getComisionImporte()))
				record.setComisionimporte(new BigDecimal(cuentaBancaria.getComisionImporte()));
			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getComisionDescripcion())
					&& cuentaBancaria.getComisionDescripcion().trim().length() <= 255)
				record.setComisiondescripcion(cuentaBancaria.getComisionDescripcion().trim());

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getIdTipoIVA())) {
				record.setIdtipoiva(Integer.parseInt(cuentaBancaria.getIdTipoIVA()));
			} else {
				record.setIdtipoiva(null);
			}

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getComisionCuentaContable())
					&& cuentaBancaria.getComisionCuentaContable().trim().length() <= 20) {
				record.setComisioncuentacontable(cuentaBancaria.getComisionCuentaContable().trim());
			} else {
				record.setAsientocontable(null);
			}

			// Actualización de la tarjeta de configuración
			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getConfigFicherosEsquema()))
				record.setConfigficherosesquema(Short.parseShort(cuentaBancaria.getConfigFicherosEsquema()));
			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getConfigFicherosSecuencia()))
				record.setConfigficherossecuencia(Short.parseShort(cuentaBancaria.getConfigFicherosSecuencia()));
			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getConfigLugaresQueMasSecuencia()))
				record.setConfiglugaresquemasecuencia(
						Short.parseShort(cuentaBancaria.getConfigLugaresQueMasSecuencia()));
			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getConfigConceptoAmpliado()))
				record.setConfigconceptoampliado(Short.parseShort(cuentaBancaria.getConfigConceptoAmpliado()));

			// Actualizar tarjeta de uso en ficheros
			record.setSjcs((cuentaBancaria.getSjcs() != null && cuentaBancaria.getSjcs()) ? "1" : "0");

			if (!UtilidadesString.esCadenaVacia(cuentaBancaria.getIdSufijoSjcs())) {
				record.setIdsufijosjcs(Short.parseShort(cuentaBancaria.getIdSufijoSjcs()));
			} else {
				record.setIdsufijosjcs(null);
			}

			LOGGER.info(
					"actualizaCuentaBancaria() / facBancoinstitucionExtendsMapper.updateByPrimaryKey() -> Entrada a facBancoinstitucionExtendsMapper para actualizar una cuenta bancaria");

			facBancoinstitucionExtendsMapper.updateByPrimaryKey(record);
		}

		updateResponseDTO.setId(record.getBancosCodigo());
		updateResponseDTO.setError(error);

		LOGGER.info("actualizaCuentaBancaria() -> Salida del servicio para actualizar una cuenta bancaria");

		return updateResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO insertaActualizaSerie(List<UsosSufijosItem> usosSufijosItems, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"insertaActualizaSerie() -> Entrada al servicio para actualizar las series que usan la cuenta bancaria");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"insertaActualizaSerie() / facSeriefacturacionBancoMapper.updateByExampleSelective() -> Entrada a facSeriefacturacionBancoMapper para actualizar las series que usan la cuenta bancaria");

			// Logica
			for (UsosSufijosItem usosSufijos : usosSufijosItems) {
				FacSeriefacturacionBancoExample serieBancoExample = new FacSeriefacturacionBancoExample();
				serieBancoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(Long.parseLong(usosSufijos.getIdSerieFacturacion()));

				FacSeriefacturacionBanco record = new FacSeriefacturacionBanco();
				record.setBancosCodigo(usosSufijos.getBancosCodigo());
				record.setIdsufijo(Short.parseShort(usosSufijos.getIdSufijo()));

				facSeriefacturacionBancoMapper.updateByExampleSelective(record, serieBancoExample);
			}

		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"insertaActualizaSerie() -> Salida del servicio para actualizar las series que usan la cuenta bancaria");

		return updateResponseDTO;
	}

	@Override
	public SeriesFacturacionDTO getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem,
			HttpServletRequest request) throws Exception {
		LOGGER.info("getSeriesFacturacion() -> Entrada al servicio para buscar series de facturación");

		AdmUsuarios usuario = new AdmUsuarios();
		SeriesFacturacionDTO seriesFacturacionDTO = new SeriesFacturacionDTO();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			String idioma = usuario.getIdlenguaje();
			List<SerieFacturacionItem> serieFacturacionItems = facSeriefacturacionExtendsMapper
					.getSeriesFacturacion(serieFacturacionItem, usuario.getIdinstitucion(), idioma);

			if (null != serieFacturacionItems && !serieFacturacionItems.isEmpty()) {

				for (SerieFacturacionItem serieItem : serieFacturacionItems) {
					String idSerieFacturacion = serieItem.getIdSerieFacturacion();

					LOGGER.info("getSeriesFacturacion() -> Obteniendo los tipos de servicios para idInstitucion="
							+ usuario.getIdinstitucion() + ", idSerieFacturacion=" + idSerieFacturacion);
					List<ComboItem> tiposServicios = facTiposservinclsenfactExtendsMapper
							.getTiposServicios(idSerieFacturacion, usuario.getIdinstitucion(), idioma);
					LOGGER.info("getSeriesFacturacion() -> Obteniendo los tipos de productos para idInstitucion="
							+ usuario.getIdinstitucion() + ", idSerieFacturacion=" + idSerieFacturacion);
					List<ComboItem> tiposProductos = facTiposproduincluenfactuExtendsMapper
							.getTiposProductos(idSerieFacturacion, usuario.getIdinstitucion(), idioma);

					List<String> tiposIncluidos = Stream.concat(tiposServicios.stream(), tiposProductos.stream())
							.map(t -> t.getLabel()).collect(Collectors.toList());

					serieItem.setTiposIncluidos(tiposIncluidos);
					serieItem.setTiposServicios(tiposServicios);
					serieItem.setTiposProductos(tiposProductos);
				}
			}

			seriesFacturacionDTO.setSerieFacturacionItems(serieFacturacionItems);
		}

		LOGGER.info("getSeriesFacturacion() -> Salida del servicio para buscar series de facturación");
		return seriesFacturacionDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminaSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request) throws Exception {
		LOGGER.info("eliminaSerieFacturacion() -> Entrada al servicio para eliminar series de facturación");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			for (SerieFacturacionItem serieFacturacion : serieFacturacionItems) {
				Long idSerieFacturacion = Long.valueOf(serieFacturacion.getIdSerieFacturacion());

				FacFacturaExample facturaExample = new FacFacturaExample();
				facturaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

				FacSeriefacturacionKey seriefacturacionKey = new FacSeriefacturacionKey();
				seriefacturacionKey.setIdinstitucion(usuario.getIdinstitucion());
				seriefacturacionKey.setIdseriefacturacion(idSerieFacturacion);

				FacSeriefacturacion sfToUpdate = facSeriefacturacionExtendsMapper
						.selectByPrimaryKey(seriefacturacionKey);
				if (null != sfToUpdate) {
					if (sfToUpdate.getFechabaja() == null) {
						long numFacturas = facFacturaMapper.countByExample(facturaExample);
						if (numFacturas == 0) {
							LOGGER.info(
									"eliminaSerieFacturacion() -> Baja física de la serie de facturación con idseriefacturacion="
											+ serieFacturacion.getIdSerieFacturacion());

							// Elimina la asociación con cuenta bancaria
							FacSeriefacturacionBancoKey seriefacturacionBancoKey = new FacSeriefacturacionBancoKey();
							seriefacturacionBancoKey.setIdinstitucion(usuario.getIdinstitucion());
							seriefacturacionBancoKey.setIdseriefacturacion(idSerieFacturacion);
							seriefacturacionBancoKey.setBancosCodigo(serieFacturacion.getIdCuentaBancaria());

							// Eliminamos las asociaciones con Productos
							FacTiposproduincluenfactuExample prodExample = new FacTiposproduincluenfactuExample();
							prodExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							// Eliminamos las asociaciones con Servicios
							FacTiposservinclsenfactExample servExample = new FacTiposservinclsenfactExample();
							prodExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							// Eliminamos las asociaciones con Etiquetas
							FacTipocliincluidoenseriefacExample etiqExample = new FacTipocliincluidoenseriefacExample();
							prodExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							// Eliminamos las asociaciones con destinatarios individuales
							FacClienincluidoenseriefacturExample destExample = new FacClienincluidoenseriefacturExample();
							prodExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							// Eliminamos las asociaciones con formas de pago
							FacFormapagoserieExample formapagoExample = new FacFormapagoserieExample();
							formapagoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							// Eliminamos las asociaciones con formas de pago
							FacGrupcritincluidosenserieExample grupoCriterioExample = new FacGrupcritincluidosenserieExample();
							grupoCriterioExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							facSeriefacturacionBancoMapper.deleteByPrimaryKey(seriefacturacionBancoKey);
							facTiposproduincluenfactuExtendsMapper.deleteByExample(prodExample);
							facTiposservinclsenfactExtendsMapper.deleteByExample(servExample);
							facTipocliincluidoenseriefacExtendsMapper.deleteByExample(etiqExample);
							facClienincluidoenseriefacturMapper.deleteByExample(destExample);
							facFormapagoserieExtendsMapper.deleteByExample(formapagoExample);
							facGrupcritincluidosenserieExtendsMapper.deleteByExample(grupoCriterioExample);

							// Si la consulta no se utiliza en ninguna otra serie de facturación, se elimina la entrada en CEN_GRUPOSCRITERIOS

							FacGrupcritincluidosenserieExample criteriosExample = new FacGrupcritincluidosenserieExample();
							criteriosExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
									.andIdseriefacturacionEqualTo(idSerieFacturacion);

							List<FacGrupcritincluidosenserie> gruposCriterios = facGrupcritincluidosenserieExtendsMapper.selectByExample(criteriosExample);
							for (FacGrupcritincluidosenserie grupoCriterio: gruposCriterios) {
								facGrupcritincluidosenserieExtendsMapper.deleteByPrimaryKey(grupoCriterio);

								FacGrupcritincluidosenserieExample criterioExample = new FacGrupcritincluidosenserieExample();
								criterioExample.createCriteria()
										.andIdgruposcriteriosEqualTo(grupoCriterio.getIdgruposcriterios())
										.andIdinstitucionGrupEqualTo(grupoCriterio.getIdinstitucion());

								long foundGrupocriterios = facGrupcritincluidosenserieExtendsMapper.countByExample(criterioExample);

								if (foundGrupocriterios == 0) {
									CenGruposcriteriosKey criterioKey = new	CenGruposcriteriosKey();
									criterioKey.setIdinstitucion(grupoCriterio.getIdinstitucion());
									criterioKey.setIdgruposcriterios(grupoCriterio.getIdgruposcriterios());

									cenGruposcriteriosExtendsMapper.deleteByPrimaryKey(criterioKey);
								}
							}



							facSeriefacturacionExtendsMapper.deleteByPrimaryKey(seriefacturacionKey);
						} else {
							LOGGER.info(
									"eliminaSerieFacturacion() -> Baja lógica de la serie de facturación con idseriefacturacion="
											+ idSerieFacturacion);

							FacSeriefacturacion sf = new FacSeriefacturacion();
							sf.setIdinstitucion(usuario.getIdinstitucion());
							sf.setIdseriefacturacion(idSerieFacturacion);
							sf.setFechabaja(new Date());
							facSeriefacturacionExtendsMapper.updateByPrimaryKeySelective(sf);
						}
					} else {
						LOGGER.debug(
								"eliminaSerieFacturacion() -> Ya se encontraba eliminada la serie de facturación con id="
										+ serieFacturacion.getIdSerieFacturacion());
					}
				} else {
					LOGGER.debug("eliminaSerieFacturacion() -> No existe serie facturación con id="
							+ serieFacturacion.getIdSerieFacturacion());

				}

			}

		}

		LOGGER.info("eliminaSerieFacturacion() -> Salida del servicio para eliminar series de facturación");
		return deleteResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO reactivarSerieFacturacion(List<SerieFacturacionItem> serieFacturacionItems,
			HttpServletRequest request) throws Exception {
		LOGGER.info("reactivarSerieFacturacion() -> Entrada al servicio para reactivar series de facturación");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {

			for (SerieFacturacionItem serieFacturacion : serieFacturacionItems) {
				LOGGER.info("reactivarSerieFacturacion() -> Reactivando serie facturación con id="
						+ serieFacturacion.getIdSerieFacturacion());

				FacSeriefacturacionExample seriefacturacionExample = new FacSeriefacturacionExample();
				seriefacturacionExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

				List<FacSeriefacturacion> sfResults = facSeriefacturacionExtendsMapper
						.selectByExample(seriefacturacionExample);
				if (null != sfResults && !sfResults.isEmpty()) {
					FacSeriefacturacion sfToUpdate = sfResults.get(0);
					if (sfToUpdate.getFechabaja() != null) {
						sfToUpdate.setFechabaja(null);
						facSeriefacturacionExtendsMapper.updateByExample(sfToUpdate, seriefacturacionExample);
					} else {
						LOGGER.debug(
								"reactivarSerieFacturacion() -> Ya se encontraba activa la serie de facturación con id="
										+ serieFacturacion.getIdSerieFacturacion());
					}
				} else {
					LOGGER.debug("reactivarSerieFacturacion() -> No existe serie facturación con id="
							+ serieFacturacion.getIdSerieFacturacion());
				}
			}
		}

		LOGGER.info("reactivarSerieFacturacion() -> Salida del servicio para reactivar series de facturación");
		return updateResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO guardarSerieFacturacion(SerieFacturacionItem serieFacturacion, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Long idSerieFacturacion = null;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("guardarSerieFacturacion() -> Entrada al servicio para guardar una serie de facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			Integer idUsuario = usuario.getIdusuario();
			LOGGER.info(
					"comboPlanificacion() / facSeriefacturacionExtendsMapper.selectByExample() -> Entrada a facSeriefacturacionExtendsMapper para obtener la serie de facturación");

			// Logica

			// 1. Actualizar FAC_SERIEFACTURACION
			boolean isNewSerie = serieFacturacion.getIdSerieFacturacion() == null
					|| serieFacturacion.getIdSerieFacturacion().trim().isEmpty();
			FacSeriefacturacion serieToUpdate = null;

			if (isNewSerie) {
				serieToUpdate = new FacSeriefacturacion();
				serieToUpdate.setIdinstitucion(usuario.getIdinstitucion());
				serieToUpdate.setIdNombreDescargaFac(Short.parseShort("1"));
				serieToUpdate.setTraspasofacturas("0");
				serieToUpdate.setIdcontador("FAC_GENERAL");
				serieToUpdate.setIdcontadorAbonos("FAC_ABONOS_GENERAL");
				serieToUpdate.setIdplantilla(1);

				serieToUpdate.setConfdeudor("F");
				serieToUpdate.setCtaclientes("4300");
				serieToUpdate.setConfingresos("F");
				serieToUpdate.setCtaingresos("7010");

				idSerieFacturacion = Long.parseLong(facSeriefacturacionExtendsMapper
						.getNextIdSerieFacturacion(usuario.getIdinstitucion()).getNewId());
				serieToUpdate.setIdseriefacturacion(idSerieFacturacion);

                // 3. Generación de ficheros por defecto
				ModClasecomunicacionesExample claseFactExample = new ModClasecomunicacionesExample();
				claseFactExample.createCriteria().andNombreEqualTo("Facturas");
				List<ModClasecomunicaciones> clasesFactura = modClasecomunicacionesMapper.selectByExample(claseFactExample);

				ModClasecomunicacionesExample claseFactRectExample = new ModClasecomunicacionesExample();
				claseFactRectExample.createCriteria().andNombreEqualTo("Facturas rectificativas");
				List<ModClasecomunicaciones> clasesFacturaRect = modClasecomunicacionesMapper.selectByExample(claseFactRectExample);

				// 3.1. Obtenemos el modelo de comunicación por defecto de las facturas
				if (clasesFactura != null && !clasesFactura.isEmpty()) {
					ModModelocomunicacionExample comunicacionExample = new ModModelocomunicacionExample();
					comunicacionExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
							.andIdclasecomunicacionEqualTo(clasesFactura.get(0).getIdclasecomunicacion())
							.andFechabajaIsNull();
					comunicacionExample.setOrderByClause("(CASE pordefecto WHEN 'SI' THEN 1 ELSE 0 END), fechamodificacion");

					List<ModModelocomunicacion> modelos = modModeloComunicacionExtendsMapper.selectByExample(comunicacionExample);
					if (modelos != null && !modelos.isEmpty())
						serieToUpdate.setIdmodelofactura(modelos.get(0).getIdmodelocomunicacion());
				} else {
					serieToUpdate.setIdmodelofactura(null);
				}

				// 3.2. Obtenemos el modelo de comunicación por defecto de las facturas rectificativas
				if (clasesFacturaRect != null && !clasesFacturaRect.isEmpty()) {
					ModModelocomunicacionExample comunicacionExample = new ModModelocomunicacionExample();
					comunicacionExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
							.andIdclasecomunicacionEqualTo(clasesFacturaRect.get(0).getIdclasecomunicacion())
							.andFechabajaIsNull();
					comunicacionExample.setOrderByClause("(CASE pordefecto WHEN 'SI' THEN 1 ELSE 0 END), fechamodificacion");

					List<ModModelocomunicacion> modelos = modModeloComunicacionExtendsMapper.selectByExample(comunicacionExample);
					if (modelos != null && !modelos.isEmpty())
						serieToUpdate.setIdmodelorectificativa(modelos.get(0).getIdmodelocomunicacion());
				} else {
					serieToUpdate.setIdmodelorectificativa(null);
				}

			} else {
				idSerieFacturacion = Long.parseLong(serieFacturacion.getIdSerieFacturacion());
				FacSeriefacturacionKey serieKey = new FacSeriefacturacionKey();
				serieKey.setIdinstitucion(usuario.getIdinstitucion());
				serieKey.setIdseriefacturacion(idSerieFacturacion);

				serieToUpdate = facSeriefacturacionExtendsMapper.selectByPrimaryKey(serieKey);

				// 2. Actualizar contadores
				if (serieFacturacion.getIdContadorFacturas() != null
						&& !serieFacturacion.getIdContadorFacturas().trim().isEmpty()) {
					serieToUpdate.setIdcontador(serieFacturacion.getIdContadorFacturas());
				}

				if (serieFacturacion.getIdContadorFacturasRectificativas() != null
						&& !serieFacturacion.getIdContadorFacturasRectificativas().trim().isEmpty()) {
					serieToUpdate.setIdcontadorAbonos(serieFacturacion.getIdContadorFacturasRectificativas());
				}

				// 3. Actualizar generación de ficheros
				serieToUpdate.setGenerarpdf(
						(serieFacturacion.getGenerarPDF() != null && serieFacturacion.getGenerarPDF()) ? "1" : "0");
				if (serieFacturacion.getIdModeloFactura() != null
						&& !serieFacturacion.getIdModeloFactura().trim().isEmpty()) {
					serieToUpdate.setIdmodelofactura(Long.parseLong(serieFacturacion.getIdModeloFactura()));
				} else {
					serieToUpdate.setIdmodelofactura(null);
				}

				if (serieFacturacion.getIdModeloRectificativa() != null
						&& !serieFacturacion.getIdModeloRectificativa().trim().isEmpty()) {
					serieToUpdate.setIdmodelorectificativa(Long.parseLong(serieFacturacion.getIdModeloRectificativa()));
				} else {
					serieToUpdate.setIdmodelorectificativa(null);
				}

				// 4. Envío de facturas
				serieToUpdate.setEnviofacturas(
						(serieFacturacion.getEnvioFacturas() != null && serieFacturacion.getEnvioFacturas()) ? "1"
								: "0");

				if (serieFacturacion.getIdPlantillaMail() != null
						&& !serieFacturacion.getIdPlantillaMail().trim().isEmpty()) {
					serieToUpdate.setIdtipoenvios(Short.parseShort("1")); // Por corregir: Tiene que buscar el tipo
					// correo electrónico
					serieToUpdate.setIdtipoplantillamail(Integer.parseInt(serieFacturacion.getIdPlantillaMail()));
				} else {
					serieToUpdate.setIdtipoenvios(null);
					serieToUpdate.setIdtipoplantillamail(null);
				}

				// 5. Actualizar traspaso de facturas
				serieToUpdate.setTraspasofacturas(
						(serieFacturacion.getTraspasoFacturas() != null && serieFacturacion.getTraspasoFacturas()) ? "1"
								: "0");

				if (serieFacturacion.getTraspasoPlantilla() != null
						&& !serieFacturacion.getTraspasoPlantilla().trim().isEmpty()
						&& serieFacturacion.getTraspasoPlantilla().trim().length() <= 10) {
					serieToUpdate.setTraspasoPlantilla(serieFacturacion.getTraspasoPlantilla().trim());
				} else {
					serieToUpdate.setTraspasoPlantilla(null);
				}

				if (serieFacturacion.getTraspasoCodAuditoriaDef() != null
						&& !serieFacturacion.getTraspasoCodAuditoriaDef().trim().isEmpty()
						&& serieFacturacion.getTraspasoCodAuditoriaDef().trim().length() <= 10) {
					serieToUpdate.setTraspasoCodauditoriaDef(serieFacturacion.getTraspasoCodAuditoriaDef().trim());
				} else {
					serieToUpdate.setTraspasoCodauditoriaDef(null);
				}

				// 6. Actualizar exportación contabilidad
				if (serieFacturacion.getConfDeudor() != null && !serieFacturacion.getConfDeudor().trim().isEmpty())
					serieToUpdate.setConfdeudor(serieFacturacion.getConfDeudor().trim());
				else
					serieToUpdate.setConfdeudor(null);

				if (serieFacturacion.getConfIngresos() != null && !serieFacturacion.getConfIngresos().trim().isEmpty())
					serieToUpdate.setConfingresos(serieFacturacion.getConfIngresos().trim());
				else
					serieFacturacion.setConfIngresos(null);

				serieToUpdate.setCtaclientes(
						serieFacturacion.getCtaClientes() != null ? serieFacturacion.getCtaClientes().trim() : null);
				serieToUpdate.setCtaingresos(
						serieFacturacion.getCtaIngresos() != null ? serieFacturacion.getCtaIngresos().trim() : null);
			}

			serieToUpdate.setUsumodificacion(idUsuario);
			serieToUpdate.setFechamodificacion(new Date());

			// 1. Actualizar datos generales
			if (serieFacturacion.getAbreviatura() != null && !serieFacturacion.getAbreviatura().trim().isEmpty()
					&& serieFacturacion.getAbreviatura().trim().length() <= 20) {
				serieToUpdate.setNombreabreviado(serieFacturacion.getAbreviatura().trim());

				// 1.1. Abreviatura única
				FacSeriefacturacionExample uniqueExample = new FacSeriefacturacionExample();
				if (isNewSerie)
					uniqueExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
							.andNombreabreviadoEqualTo(serieFacturacion.getAbreviatura().trim());
				else
					uniqueExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
							.andIdseriefacturacionNotEqualTo(idSerieFacturacion)
							.andNombreabreviadoEqualTo(serieFacturacion.getAbreviatura().trim());

				long found = facSeriefacturacionExtendsMapper.countByExample(uniqueExample);

				if (found > 0)
					throw new Exception("facturacion.seriesFactura.abreviatura.unica");
			}

			if (serieFacturacion.getDescripcion() != null && !serieFacturacion.getDescripcion().trim().isEmpty()
					&& serieFacturacion.getDescripcion().trim().length() <= 100) {
				serieToUpdate.setDescripcion(serieFacturacion.getDescripcion());

				// 1.2. Descripción única
				FacSeriefacturacionExample uniqueExample = new FacSeriefacturacionExample();
				if (isNewSerie)
					uniqueExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
							.andDescripcionEqualTo(serieFacturacion.getDescripcion().trim());
				else
					uniqueExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
							.andIdseriefacturacionNotEqualTo(idSerieFacturacion)
							.andDescripcionEqualTo(serieFacturacion.getDescripcion().trim());

				long found = facSeriefacturacionExtendsMapper.countByExample(uniqueExample);

				if (found > 0)
					throw new Exception("facturacion.seriesFactura.descripcion.unica");
			}

			if (serieFacturacion.getIdSerieFacturacionPrevia() != null
					&& !serieFacturacion.getIdSerieFacturacionPrevia().trim().isEmpty()) {
				serieToUpdate
						.setIdseriefacturacionprevia(Long.parseLong(serieFacturacion.getIdSerieFacturacionPrevia()));
			} else {
				serieToUpdate.setIdseriefacturacionprevia(null);
			}

			if (serieFacturacion.getObservaciones() != null && !serieFacturacion.getObservaciones().trim().isEmpty()
					&& serieFacturacion.getObservaciones().trim().length() <= 4000) {
				serieToUpdate.setObservaciones(serieFacturacion.getObservaciones().trim());
			} else {
				serieToUpdate.setObservaciones(null);
			}

			if (serieFacturacion.getSerieGenerica() != null && serieFacturacion.getSerieGenerica()) {
				serieToUpdate.setTiposerie("G");
			} else {
				serieToUpdate.setTiposerie(null);
			}

			if (isNewSerie) {
				facSeriefacturacionExtendsMapper.insert(serieToUpdate);
			} else {
				facSeriefacturacionExtendsMapper.updateByPrimaryKey(serieToUpdate);
			}

			// 7. Actualizar FAC_SERIEFACTURACION_BANCO
			FacSeriefacturacionBancoExample bancoExample = new FacSeriefacturacionBancoExample();
			bancoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdseriefacturacionEqualTo(idSerieFacturacion);
			List<FacSeriefacturacionBanco> serieBancoItems = facSeriefacturacionBancoMapper
					.selectByExample(bancoExample);
			boolean isNewBanco = serieBancoItems == null || serieBancoItems.isEmpty();

			FacSeriefacturacionBanco serieBancoToUpdate = new FacSeriefacturacionBanco();
			if (isNewBanco) {
				serieBancoToUpdate.setIdinstitucion(usuario.getIdinstitucion());
				serieBancoToUpdate.setIdseriefacturacion(idSerieFacturacion);
			} else {
				serieBancoToUpdate = serieBancoItems.get(0);
			}
			serieBancoToUpdate.setUsumodificacion(idUsuario);
			serieBancoToUpdate.setFechamodificacion(new Date());
			serieBancoToUpdate.setBancosCodigo(serieFacturacion.getIdCuentaBancaria());
			serieBancoToUpdate.setIdsufijo(Short.parseShort(serieFacturacion.getIdSufijo()));

			if (!isNewBanco) {
				facSeriefacturacionBancoMapper.updateByExample(serieBancoToUpdate, bancoExample);
			} else {
				facSeriefacturacionBancoMapper.insert(serieBancoToUpdate);
			}

			// 8. Actualizar tipos de productos

			if (serieFacturacion.getTiposProductos() != null) {
				// 8.1. Primero eliminamos los tipos que ya no existan

				List<ComboItem> productos = facTiposproduincluenfactuExtendsMapper.getTiposProductos(
						idSerieFacturacion.toString(), usuario.getIdinstitucion(), usuario.getIdlenguaje());
				for (ComboItem item : productos) {
					if (serieFacturacion.getTiposProductos().stream()
							.allMatch(p -> !p.getValue().equals(item.getValue()))) {
						FacTiposproduincluenfactuKey key = new FacTiposproduincluenfactuKey();
						key.setIdinstitucion(usuario.getIdinstitucion());
						key.setIdseriefacturacion(idSerieFacturacion);
						key.setIdtipoproducto(Short.parseShort(item.getValue().split("-")[0]));
						key.setIdproducto(Long.parseLong(item.getValue().split("-")[1]));

						facTiposproduincluenfactuExtendsMapper.deleteByPrimaryKey(key);
					}
				}

				// 8.2. Agregamos los nuevos tipos
				for (ComboItem item : serieFacturacion.getTiposProductos()) {
					if (productos.stream().allMatch(p -> !p.getValue().equals(item.getValue()))) {
						FacTiposproduincluenfactu producto = new FacTiposproduincluenfactu();
						producto.setIdinstitucion(usuario.getIdinstitucion());
						producto.setIdseriefacturacion(idSerieFacturacion);
						producto.setIdtipoproducto(Short.parseShort(item.getValue().split("-")[0]));
						producto.setIdproducto(Long.parseLong(item.getValue().split("-")[1]));

						producto.setUsumodificacion(idUsuario);
						producto.setFechamodificacion(new Date());
						facTiposproduincluenfactuExtendsMapper.insert(producto);
					}
				}
			}

			// 9. Actualizar tipos de servicios

			if (serieFacturacion.getTiposServicios() != null) {
				// 9.1. Primero eliminamos los tipos que ya no existan
				List<ComboItem> servicios = facTiposservinclsenfactExtendsMapper.getTiposServicios(
						idSerieFacturacion.toString(), usuario.getIdinstitucion(), usuario.getIdlenguaje());
				for (ComboItem item : servicios) {
					if (serieFacturacion.getTiposServicios().stream()
							.allMatch(p -> !p.getValue().equals(item.getValue()))) {
						FacTiposservinclsenfactKey key = new FacTiposservinclsenfactKey();
						key.setIdinstitucion(usuario.getIdinstitucion());
						key.setIdseriefacturacion(idSerieFacturacion);
						key.setIdtiposervicios(Short.parseShort(item.getValue().split("-")[0]));
						key.setIdservicio(Long.parseLong(item.getValue().split("-")[1]));

						facTiposservinclsenfactExtendsMapper.deleteByPrimaryKey(key);
					}
				}

				// 9.2. Agregamos los nuevos tipos
				for (ComboItem item : serieFacturacion.getTiposServicios()) {
					if (servicios.stream().allMatch(p -> !p.getValue().equals(item.getValue()))) {
						FacTiposservinclsenfact servicio = new FacTiposservinclsenfact();
						servicio.setIdinstitucion(usuario.getIdinstitucion());
						servicio.setIdseriefacturacion(idSerieFacturacion);
						servicio.setIdtiposervicios(Short.parseShort(item.getValue().split("-")[0]));
						servicio.setIdservicio(Long.parseLong(item.getValue().split("-")[1]));

						servicio.setUsumodificacion(idUsuario);
						servicio.setFechamodificacion(new Date());
						facTiposservinclsenfactExtendsMapper.insert(servicio);
					}
				}
			}

		}

		updateResponseDTO.setId(String.valueOf(idSerieFacturacion));

		LOGGER.info("guardarSerieFacturacion() -> Salida del servicio para guardar la serie de facturación");

		return updateResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO guardarEtiquetasSerieFacturacion(TarjetaPickListSerieDTO etiquetas,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("guardarEtiquetasSerieFacturacion() -> Entrada al servicio para guardar las etiquetas de la serie");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			Integer idUsuario = usuario.getIdusuario();

			// Logica
			FacTipocliincluidoenseriefac etiqueta = null;
			Long idSerie = Long.parseLong(etiquetas.getIdSerieFacturacion());

			for (ComboItem item : etiquetas.getNoSeleccionados()) {
				Short idGrupo = Short.parseShort(item.getValue());

				FacTipocliincluidoenseriefacExample etiquetaExample = new FacTipocliincluidoenseriefacExample();
				etiquetaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(idSerie).andIdgrupoEqualTo(idGrupo)
						.andIdinstitucionGrupoEqualTo(usuario.getIdinstitucion());

				facTipocliincluidoenseriefacExtendsMapper.deleteByExample(etiquetaExample);
			}

			for (ComboItem item : etiquetas.getSeleccionados()) {
				Short idGrupo = Short.parseShort(item.getValue());

				FacTipocliincluidoenseriefacExample etiquetaExample = new FacTipocliincluidoenseriefacExample();
				etiquetaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(idSerie).andIdgrupoEqualTo(idGrupo)
						.andIdinstitucionGrupoEqualTo(usuario.getIdinstitucion());

				long size = facTipocliincluidoenseriefacExtendsMapper.countByExample(etiquetaExample);

				if (size == 0) {
					etiqueta = new FacTipocliincluidoenseriefac();
					etiqueta.setUsumodificacion(idUsuario);
					etiqueta.setFechamodificacion(new Date());
					etiqueta.setIdinstitucion(usuario.getIdinstitucion());
					etiqueta.setIdseriefacturacion(idSerie);
					etiqueta.setIdgrupo(idGrupo);
					etiqueta.setIdinstitucionGrupo(usuario.getIdinstitucion());

					facTipocliincluidoenseriefacExtendsMapper.insert(etiqueta);
				}
			}
		}

		LOGGER.info("guardarEtiquetasSerieFacturacion() -> Salida del servicio para guardar las etiquetas de la serie");

		return updateResponseDTO;
	}

	@Override
	public DestinatariosSeriesDTO getDestinatariosSeries(String idSerieFacturacion, HttpServletRequest request)
			throws Exception {
		DestinatariosSeriesDTO destinatariosSeriesDTO = new DestinatariosSeriesDTO();

		List<DestinatariosSeriesItem> destinatariosSeriesItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"getDestinatariosSeries() -> Entrada al servicio para recuperar los destinatarios de la serie de facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getDestinatariosSeries() / cenPersonaExtendsMapper.getDestinatariosSeries() -> Entrada a facTipocliincluidoenseriefacExtendsMapper para obtener los destinatarios de la serie");

			// Logica
			destinatariosSeriesItems = cenPersonaExtendsMapper.getDestinatariosSeries(usuario.getIdinstitucion(),
					idSerieFacturacion);

			LOGGER.debug(
					"getDestinatariosSeries() / cenPersonaExtendsMapper.getDestinatariosSeries() -> Saliendo de facTipocliincluidoenseriefacExtendsMapper para obtener los destinatarios de la serie");

			destinatariosSeriesDTO.setDestinatariosSeriesItems(destinatariosSeriesItems);
		}

		LOGGER.info(
				"getDestinatariosSeries() -> Salida del servicio para obtener los destinatarios de la serie de facturación");

		return destinatariosSeriesDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CreateResponseDTO nuevoDestinatariosSerie(DestinatariosSeriesItem destinatariosSeriesItem,
			HttpServletRequest request) throws Exception {
		LOGGER.info(
				"nuevoDestinatariosSerie() -> Entrada al servicio para agregar un destinatario individual a una serie");

		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			FacClienincluidoenseriefacturKey key = new FacClienincluidoenseriefacturKey();
			key.setIdinstitucion(Short.parseShort(destinatariosSeriesItem.getNumeroInstitucion()));
			key.setIdseriefacturacion(Long.parseLong(destinatariosSeriesItem.getIdSerieFacturacion()));
			key.setIdpersona(Long.parseLong(destinatariosSeriesItem.getIdPersona()));

			FacClienincluidoenseriefactur foundClienincluidoenseriefactur = facClienincluidoenseriefacturMapper
					.selectByPrimaryKey(key);

			if (foundClienincluidoenseriefactur == null) {
				FacClienincluidoenseriefactur record = new FacClienincluidoenseriefactur();
				record.setIdinstitucion(key.getIdinstitucion());
				record.setIdseriefacturacion(key.getIdseriefacturacion());
				record.setIdpersona(key.getIdpersona());
				record.setUsumodificacion(usuario.getIdusuario());
				record.setFechamodificacion(new Date());

				facClienincluidoenseriefacturMapper.insert(record);
			} else {
				throw new Exception("facturacion.cuentaBancaria.destIndividuales.unico");
			}

		}

		LOGGER.info(
				"nuevoDestinatariosSerie() -> Salida del servicio para agregar un destinatario individual a una serie");
		return createResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminaDestinatariosSerie(List<DestinatariosSeriesItem> destinatariosSeriesItems,
			HttpServletRequest request) throws Exception {
		LOGGER.info(
				"eliminaDestinatariosSerie() -> Entrada al servicio para eliminar destinatarios individuales de una serie");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			for (DestinatariosSeriesItem destinatariosSeries : destinatariosSeriesItems) {
				FacClienincluidoenseriefacturKey key = new FacClienincluidoenseriefacturKey();
				key.setIdinstitucion(Short.parseShort(destinatariosSeries.getIdInstitucion()));
				key.setIdseriefacturacion(Long.parseLong(destinatariosSeries.getIdSerieFacturacion()));
				key.setIdpersona(Long.parseLong(destinatariosSeries.getIdPersona()));

				facClienincluidoenseriefacturMapper.deleteByPrimaryKey(key);
			}

		}

		LOGGER.info(
				"eliminaDestinatariosSerie() -> Salida del servicio para eliminar destinatarios individuales de una serie");
		return deleteResponseDTO;
	}

	@Override
	public ConsultasDTO getConsultasSerie(String idSerieFacturacion, HttpServletRequest request) throws Exception {
		ConsultasDTO destinatariosSeriesDTO = new ConsultasDTO();

		List<ConsultaItem> consultasItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"getConsultasSerie() -> Entrada al servicio para recuperar los destinatarios de la serie de facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getDestinatariosSeries() / facGrupcritincluidosenserieExtendsMapper.getConsultasSerie() -> Entrada a facGrupcritincluidosenserieExtendsMapper para obtener las consultas disponibles de la serie");

			// Logica
			consultasItems = facGrupcritincluidosenserieExtendsMapper.getConsultasSerie(idSerieFacturacion,
					usuario.getIdinstitucion());

			String idIdioma = usuario.getIdlenguaje();
			if (consultasItems != null) {
				consultasItems.forEach(c -> {
					String objetivo = conConsultasExtendsMapper.SelectObjetivo(c.getIdObjetivo(), idIdioma);
					c.setObjetivo(objetivo);
				});
			}

			LOGGER.debug(
					"getConsultasSerie() / facGrupcritincluidosenserieExtendsMapper.getConsultasSerie() -> Saliendo de facGrupcritincluidosenserieExtendsMapper para obtener las consultas disponibles de la serie");

			destinatariosSeriesDTO.setConsultaItem(consultasItems);
		}

		LOGGER.info("getConsultasSerie() -> Salida del servicio para obtener las consultas de la serie de facturación");

		return destinatariosSeriesDTO;
	}

	@Override
	public FinalidadConsultaDTO getFinalidadConsultasSerie(ConsultaDestinatarioItem consulta,
			HttpServletRequest request) throws Exception {
		FinalidadConsultaDTO finalidadConsultaDTO = new FinalidadConsultaDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("getFinalidadConsultasSerie() -> Entrada al servicio para recuperar la finalidad de una consulta");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getFinalidadConsultasSerie() / conConsultasExtendsMapper.SelectObjetivo() -> Entrada a conConsultasExtendsMapper para obtener la finalidad de una consulta");

			// Logica
			ConConsultaKey key = new ConConsultaKey();
			key.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
			key.setIdinstitucion(Short.parseShort(consulta.getIdInstitucion()));
			ConConsulta conConsulta = conConsultasExtendsMapper.selectByPrimaryKey(key);

			String objetivo = conConsultasExtendsMapper.SelectObjetivo(String.valueOf(conConsulta.getIdobjetivo()),
					String.valueOf(usuario.getIdlenguaje()));

			LOGGER.debug(
					"getFinalidadConsultasSerie() / conConsultasExtendsMapper.SelectObjetivo() -> Saliendo de conConsultasExtendsMapper para obtener las finalidad de una consulta");

			finalidadConsultaDTO.setObjetivo(objetivo);
		}

		LOGGER.info("getFinalidadConsultasSerie() -> Salida del servicio para obtener la finalidad de una consulta");

		return finalidadConsultaDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CreateResponseDTO nuevaConsultaSerie(ConsultaDestinatarioItem consulta, HttpServletRequest request)
			throws Exception {
		LOGGER.info("nuevaConsultaSerie() -> Entrada al servicio para agregar una nueva consulta a una serie");

		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {

			CenGruposcriteriosExample gruposCriteriosExample = new CenGruposcriteriosExample();
			gruposCriteriosExample.createCriteria()
					.andIdinstitucionEqualTo(Short.parseShort(consulta.getIdInstitucion()))
					.andIdconsultaEqualTo(Long.parseLong(consulta.getIdConsulta()));

			List<CenGruposcriterios> gruposCriterios = cenGruposcriteriosExtendsMapper
					.selectByExample(gruposCriteriosExample);

			String idGruposCriterios = null;
			if (gruposCriterios == null || gruposCriterios.isEmpty()) {
				idGruposCriterios = cenGruposcriteriosExtendsMapper
						.getNewIdGruposCriterios(Short.parseShort(consulta.getIdInstitucion())).getNewId();

				ConConsultaKey consultaKey = new ConConsultaKey();
				consultaKey.setIdinstitucion(Short.parseShort(consulta.getIdInstitucion()));
				consultaKey.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));

				ConConsulta conConsulta = conConsultasExtendsMapper.selectByPrimaryKey(consultaKey);

				// Si no existe la consulta en CEN_GRUPOSCRITERIOS se crea la entrada
				if (consulta != null) {
					CenGruposcriterios recordGruposCriterios = new CenGruposcriterios();
					recordGruposCriterios.setIdinstitucion(Short.parseShort(consulta.getIdInstitucion()));
					recordGruposCriterios.setIdconsulta(Long.parseLong(consulta.getIdConsulta()));
					recordGruposCriterios.setIdgruposcriterios(Integer.parseInt(idGruposCriterios));
					recordGruposCriterios.setFechamodificacion(new Date());
					recordGruposCriterios.setUsumodificacion(usuario.getIdusuario());
					recordGruposCriterios.setNombre(conConsulta.getDescripcion());

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(new ByteArrayInputStream(
							("<root>" + conConsulta.getSentencia() + "</root>").getBytes(StandardCharsets.UTF_8)));

					List<String> partesSentencia = new ArrayList<>();
					NodeList nodes = doc.getDocumentElement().getChildNodes();
					for (int i = 0; i < nodes.getLength(); i++) {
						String nodeContent = nodes.item(i).getTextContent();
						if (nodeContent != null)
							partesSentencia.add(nodeContent.trim());
					}

					if (partesSentencia.isEmpty()) {
						recordGruposCriterios.setSentencia(conConsulta.getSentencia());
					} else {
						recordGruposCriterios.setSentencia(String.join(" \n", partesSentencia));
					}

					cenGruposcriteriosExtendsMapper.insertSelective(recordGruposCriterios);
				}
			} else {
				idGruposCriterios = gruposCriterios.get(0).getIdgruposcriterios().toString();
			}

			FacGrupcritincluidosenserie record = new FacGrupcritincluidosenserie();
			record.setIdinstitucion(usuario.getIdinstitucion());
			record.setIdseriefacturacion(Long.parseLong(consulta.getIdSerieFacturacion()));
			record.setIdinstitucionGrup(Short.parseShort(consulta.getIdInstitucion()));
			record.setIdgruposcriterios(Integer.parseInt(idGruposCriterios));
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario.getIdusuario());

			facGrupcritincluidosenserieExtendsMapper.insertSelective(record);

		}

		LOGGER.info("nuevaConsultaSerie() -> Salida del servicio para agregar una nueva consulta a una serie");
		return createResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminaConsultasSerie(List<ConsultaDestinatarioItem> consultas, HttpServletRequest request)
			throws Exception {
		LOGGER.info(
				"eliminaConsultasSerie() -> Entrada al servicio para eliminar consultas de destinatarios de una serie");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			for (ConsultaDestinatarioItem consulta : consultas) {
				FacGrupcritincluidosenserieExample grupoCriterioIncuidoExample = new FacGrupcritincluidosenserieExample();
				grupoCriterioIncuidoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdgruposcriteriosEqualTo(Integer.parseInt(consulta.getIdConsulta()))
						.andIdinstitucionGrupEqualTo(Short.parseShort(consulta.getIdInstitucion()))
						.andIdseriefacturacionEqualTo(Long.parseLong(consulta.getIdSerieFacturacion()));

				facGrupcritincluidosenserieExtendsMapper.deleteByExample(grupoCriterioIncuidoExample);

				// Si la consulta no se utiliza en ninguna otra serie de facturación, se elimina la entrada en CEN_GRUPOSCRITERIOS

				FacGrupcritincluidosenserieExample criterioExample = new
				FacGrupcritincluidosenserieExample(); criterioExample.createCriteria()
					.andIdgruposcriteriosEqualTo(Integer.parseInt(consulta.getIdConsulta()))
					.andIdinstitucionGrupEqualTo(Short.parseShort(consulta.getIdInstitucion()));

				long foundGrupocriterios = facGrupcritincluidosenserieExtendsMapper.countByExample(criterioExample);

				if (foundGrupocriterios == 0) {
					CenGruposcriteriosKey criterioKey = new	CenGruposcriteriosKey();
					criterioKey.setIdinstitucion(Short.parseShort(consulta.getIdInstitucion()));
					criterioKey.setIdgruposcriterios(Integer.parseInt(consulta.getIdConsulta()));

					cenGruposcriteriosExtendsMapper.deleteByPrimaryKey(criterioKey);
				}
			}

		}

		LOGGER.info(
				"eliminaConsultasSerie() -> Salida del servicio para eliminar consultas de destinatarios de una serie");
		return deleteResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO guardarFormasPagosSerie(TarjetaPickListSerieDTO formasPagos, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("guardarFormasPagosSerie() -> Entrada al servicio para guardar las formas de pago");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			Integer idUsuario = usuario.getIdusuario();
			LOGGER.info(
					"guardarFormasPagosSerie() / facFormapagoserieExtendsMapper.insertSelective() -> Entrada a facFormapagoserieExtendsMapper para guardar las formas de pago");

			// Logica
			FacFormapagoserie formapagoserie = null;
			Long idSerie = Long.parseLong(formasPagos.getIdSerieFacturacion());

			for (ComboItem item : formasPagos.getNoSeleccionados()) {
				Short idFormaPago = Short.parseShort(item.getValue());

				FacFormapagoserieExample formapagoExample = new FacFormapagoserieExample();
				formapagoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(idSerie).andIdformapagoEqualTo(idFormaPago);

				facFormapagoserieExtendsMapper.deleteByExample(formapagoExample);
			}

			for (ComboItem item : formasPagos.getSeleccionados()) {
				Short idFormaPago = Short.parseShort(item.getValue());

				FacFormapagoserieExample formapagoExample = new FacFormapagoserieExample();
				formapagoExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(idSerie).andIdformapagoEqualTo(idFormaPago);

				long size = facFormapagoserieExtendsMapper.countByExample(formapagoExample);

				if (size == 0) {
					formapagoserie = new FacFormapagoserie();
					formapagoserie.setUsumodificacion(idUsuario);
					formapagoserie.setFechamodificacion(new Date());
					formapagoserie.setIdinstitucion(usuario.getIdinstitucion());
					formapagoserie.setIdseriefacturacion(idSerie);
					formapagoserie.setIdformapago(idFormaPago);

					facFormapagoserieExtendsMapper.insert(formapagoserie);
				}
			}

		}

		LOGGER.info("guardarFormasPagosSerie() -> Salida del servicio para guardar las formas de pago");

		return updateResponseDTO;
	}

	@Override
	public ContadorSeriesDTO getContadoresSerie(HttpServletRequest request) throws Exception {
		ContadorSeriesDTO contadorSeriesDTO = new ContadorSeriesDTO();

		List<ContadorSeriesItem> contadorSeriesItems = null;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("getContadoresSerie() -> Entrada al servicio para recuperar los datos de los contadores");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getContadoresSerie() / admContadorMapper.selectByExample() -> Entrada a admContadorMapper para obtener los datos de los contadores");

			// Logica
			AdmContadorExample exampleContador = new AdmContadorExample();
			exampleContador.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdtablaEqualTo("FAC_FACTURA");
			exampleContador.setOrderByClause("NOMBRE");

			List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

			if (contadores != null) {
				contadorSeriesItems = new ArrayList<>();
				for (AdmContador admContador : contadores) {
					ContadorSeriesItem item = new ContadorSeriesItem();
					item.setIdContador(admContador.getIdcontador());
					item.setNombre(admContador.getNombre());
					item.setPrefijo(admContador.getPrefijo());
					item.setSufijo(admContador.getSufijo());
					item.setContador(String.valueOf(admContador.getContador()));

					contadorSeriesItems.add(item);
				}
			}

			LOGGER.debug(
					"getContadoresSerie() / admContadorMapper.selectByExample() -> Saliendo de admContadorMapper para obtener las formas de pago de la serie de facturación");

			contadorSeriesDTO.setContadorSeriesItems(contadorSeriesItems);
		}

		LOGGER.info("getContadoresSerie() -> Salida del servicio para obtener los datos de los contadores");

		return contadorSeriesDTO;
	}

	@Override
	public ContadorSeriesDTO getContadoresRectificativasSerie(HttpServletRequest request) throws Exception {
		ContadorSeriesDTO contadorSeriesDTO = new ContadorSeriesDTO();

		List<ContadorSeriesItem> contadorSeriesItems = null;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"getContadoresRectificativasSerie() -> Entrada al servicio para recuperar los datos de los contadores de fact. rectificativas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.debug(
					"getContadoresRectificativasSerie() / admContadorMapper.selectByExample() -> Entrada a admContadorMapper para obtener los datos de los contadores rectificativas");

			// Logica
			AdmContadorExample exampleContador = new AdmContadorExample();
			exampleContador.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdtablaEqualTo("FAC_ABONO").andIdcontadorNotEqualTo("FAC_ABONOS_FCS");
			exampleContador.setOrderByClause("NOMBRE");

			List<AdmContador> contadores = admContadorMapper.selectByExample(exampleContador);

			if (contadores != null) {
				contadorSeriesItems = new ArrayList<>();
				for (AdmContador admContador : contadores) {
					ContadorSeriesItem item = new ContadorSeriesItem();
					item.setIdContador(admContador.getIdcontador());
					item.setNombre(admContador.getNombre());
					item.setPrefijo(admContador.getPrefijo());
					item.setSufijo(admContador.getSufijo());
					item.setContador(String.valueOf(admContador.getContador()));

					contadorSeriesItems.add(item);
				}
			}

			LOGGER.debug(
					"getContadoresRectificativasSerie() / admContadorMapper.selectByExample() -> Saliendo de admContadorMapper para obtener los datos de los contadores de fact. rectificativas");

			contadorSeriesDTO.setContadorSeriesItems(contadorSeriesItems);
		}

		LOGGER.info(
				"getContadoresRectificativasSerie() -> Salida del servicio para obtener los datos de los contadores rectificativas");

		return contadorSeriesDTO;
	}

	@Override
	public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request)
			throws Exception {
		FicherosAdeudosDTO ficherosAdeudosDTO = new FicherosAdeudosDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosAdeudos() -> Entrada al servicio para obtener los ficheros de adeudos");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getFicherosAdeudos() -> obteniendo datos de ficheros de adeudos");

			List<FicherosAdeudosItem> items = facDisquetecargosExtendsMapper.getFicherosAdeudos(item,
					usuario.getIdinstitucion().toString());

			ficherosAdeudosDTO.setFicherosAdeudosItems(items);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosAdeudos() -> Salida del servicio  para obtener los ficheros de adeudos");

		return ficherosAdeudosDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO guardarContadorSerie(ContadorSeriesItem contador, HttpServletRequest request)
			throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("guardarContadorSerie() -> Entrada al servicio para crear un nuevo contador");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			Short idInstitucion = usuario.getIdinstitucion();
			Integer idUsuario = usuario.getIdusuario();
			String idSerieFacturacion = contador.getIdSerieFacturacion();
			LOGGER.info(
					"guardarContadorSerie() / admContadorMapper.insertSelective() -> Entrada a admContadorMapper para crear un nuevo contador");

			// Logica
			String idContador = getNextIdContador(idInstitucion, idSerieFacturacion,
					contador.getFacturaRectificativa()); // Obtener autoincremental

			AdmContador nuevoContador = new AdmContador();
			nuevoContador.setNombre(contador.getNombre());
			nuevoContador.setDescripcion(contador.getNombre());
			nuevoContador.setPrefijo(contador.getPrefijo());
			nuevoContador.setContador(Long.parseLong(contador.getContador()));
			nuevoContador.setSufijo(contador.getSufijo());
			nuevoContador.setIdcontador(idContador);
			nuevoContador.setIdmodulo(Short.parseShort("6"));

			if (!contador.getFacturaRectificativa()) {
				nuevoContador.setIdtabla("FAC_FACTURA");
				nuevoContador.setIdcamposufijo("NUMEROFACTURA");
				nuevoContador.setIdcampoprefijo("NUMEROFACTURA");
				nuevoContador.setIdcampocontador("NUMEROFACTURA");
			} else {
				nuevoContador.setIdtabla("FAC_ABONO");
				nuevoContador.setIdcamposufijo("NUMEROABONO");
				nuevoContador.setIdcampoprefijo("NUMEROABONO");
				nuevoContador.setIdcampocontador("NUMEROABONO");
			}

			nuevoContador.setIdinstitucion(idInstitucion);
			nuevoContador.setUsucreacion(idUsuario);
			nuevoContador.setFechacreacion(new Date());
			nuevoContador.setUsumodificacion(idUsuario);
			nuevoContador.setFechamodificacion(new Date());
			nuevoContador.setModificablecontador("1");

			admContadorMapper.insertSelective(nuevoContador);

			FacSeriefacturacionKey serieKey = new FacSeriefacturacionKey();
			serieKey.setIdinstitucion(idInstitucion);
			serieKey.setIdseriefacturacion(Long.parseLong(idSerieFacturacion));
			FacSeriefacturacion serieToUpdate = facSeriefacturacionExtendsMapper.selectByPrimaryKey(serieKey);

			if (!contador.getFacturaRectificativa())
				serieToUpdate.setIdcontador(idContador);
			else
				serieToUpdate.setIdcontadorAbonos(idContador);

			facSeriefacturacionExtendsMapper.updateByPrimaryKey(serieToUpdate);

			insertResponseDTO.setId(idContador);
		}

		LOGGER.info("guardarContadorSerie() -> Salida del servicio para crear un nuevo contador");

		return insertResponseDTO;
	}

	private String getNextIdContador(Short idInstitucion, String idSerieFacturacion, Boolean isRectificativa) {
		String res = "FAC_" + (isRectificativa ? "ABONOS_" : "") + idSerieFacturacion.trim() + "_";
		AdmContadorExample contadorExample = new AdmContadorExample();

		if (!isRectificativa)
			contadorExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtablaEqualTo("FAC_FACTURA");
		else
			contadorExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdtablaEqualTo("FAC_ABONO")
					.andIdcontadorNotEqualTo("FAC_ABONOS_FCS");

		List<AdmContador> contadores = admContadorMapper.selectByExample(contadorExample);

		if (contadores == null || contadores.isEmpty())
			return res + "1";
		else
			return res + String.valueOf(contadores.stream().map(c -> c.getIdcontador()).filter(c -> c.contains(res))
					.mapToInt(c -> Integer.parseInt(c.replace(res, ""))).max().orElse(0) + 1);
	}

	@Override
	public UsosSufijosDTO getUsosSufijos(String codBanco, HttpServletRequest request) throws Exception {
		UsosSufijosDTO usosSufijosDTO = new UsosSufijosDTO();

		List<UsosSufijosItem> usosSufijosItems;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("getUsosSufijos() -> Entrada al servicio para recuperar usos y sufijos");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"getUsosSufijos() / facSeriefacturacionExtendsMapper.getUsosSufijos() -> Entrada a facSeriefacturacionExtendsMapper para obtener los usos y sufijos");

			// Logica
			usosSufijosItems = facSeriefacturacionExtendsMapper.getUsosSufijos(usuario.getIdinstitucion(), codBanco);
			usosSufijosDTO.setUsosSufijosItems(usosSufijosItems);
		}

		LOGGER.info("getUsosSufijos() -> Salida del servicio para obtener los usos y sufijos");

		return usosSufijosDTO;
	}

	@Override
	public FacFacturacionprogramadaDTO getFacturacionesProgramadas(
			FacFacturacionprogramadaItem facturacionProgramadaItem, HttpServletRequest request) throws Exception {
		FacFacturacionprogramadaDTO itemsDTO = new FacFacturacionprogramadaDTO();
		List<FacFacturacionprogramadaItem> items;
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"getFacturacionesProgramadas() -> Entrada al servicio para recuperar el listado de facturaciones programadas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"getFacturacionesProgramadas() / facFacturacionprogramadaExtendsMapper.getFacturacionesProgramadas() -> Entrada a facFacturacionprogramadaExtendsMapper para obtener el listado de facturaciones programadas");

			// Logica
			items = facFacturacionprogramadaExtendsMapper.getFacturacionesProgramadas(facturacionProgramadaItem,
					usuario.getIdinstitucion(), usuario.getIdlenguaje(), 200);
			itemsDTO.setFacturacionprogramadaItems(items);

		}

		itemsDTO.setError(error);

		LOGGER.info(
				"getFacturacionesProgramadas() -> Salida del servicio para obtener el listado de facturaciones programadas");

		return itemsDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminarFacturacion(FacFacturacionEliminarItem fac, HttpServletRequest request)
			throws Exception {
		LOGGER.info("eliminarFacturacion() -> Entrada al servicio para eliminar facturación");

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
		Error error = new Error();
		error.setCode(0);
		deleteResponseDTO.setError(error);

		// Obtener la facturación cuyo fichero se va a eliminar
		FacFacturacionprogramadaKey key = new FacFacturacionprogramadaKey();
		key.setIdinstitucion(usuario.getIdinstitucion());
		key.setIdseriefacturacion(fac.getIdSerieFacturacion());
		key.setIdprogramacion(fac.getIdProgramacion());
		FacFacturacionprogramada facturacionToDelete = facFacturacionprogramadaExtendsMapper.selectByPrimaryKey(key);

        Object[] param_in = new Object[4]; // Parametros de entrada del PL

        param_in[0] = usuario.getIdinstitucion();
        param_in[1] = fac.getIdSerieFacturacion();
        param_in[2] = fac.getIdProgramacion();
        param_in[3] = usuario.getIdusuario();

        String resultado[] = commons.callPLProcedureFacturacionPyS(
                "{call PKG_SIGA_FACTURACION.ELIMINARFACTURACION(?,?,?,?,?,?)}", 2, param_in);

        if (resultado[0] != null && !resultado[0].equals(RET_OK)) {
            throw new Exception(resultado[1]);
        } else {
        	// Obtener la ruta del fichero
			String directorioFisico = "facturacion.directorioFisicoPrevisionesJava";
			String directorio = "facturacion.directorioPrevisionesJava";

			String pathFichero = getProperty(directorioFisico) + getProperty(directorio)
					+ File.separator + usuario.getIdinstitucion();

			// Borrado del fichero
			File file = null;
			if (Objects.nonNull(facturacionToDelete)) {
				String nombreFichero = pathFichero + File.separator + facturacionToDelete.getNombrefichero();
				file = new File(nombreFichero);

				if (file.exists()) {
					file.delete();
				}
			}
        }


		LOGGER.info("eliminarFacturacion() -> Salida del servicio para eliminar facturación");

		return deleteResponseDTO;
	}

	@Override
	public FicherosAbonosDTO getFicherosTransferencias(FicherosAbonosItem item, HttpServletRequest request)
			throws Exception {
		FicherosAbonosDTO ficherosAbonosDTO = new FicherosAbonosDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosTransferencias() -> Entrada al servicio para obtener los ficheros de transferencias");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"FacturacionPySServiceImpl.getFicherosTransferencias() -> obteniendo datos de ficheros de transferencias");

			List<FicherosAbonosItem> items = facDisqueteabonosExtendsMapper.getFicherosTransferencias(item,
					usuario.getIdinstitucion().toString(), usuario.getIdlenguaje());

			ficherosAbonosDTO.setFicherosAbonosItems(items);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosTransferencias() -> Salida del servicio  para obtener los ficheros de transferencias");

		return ficherosAbonosDTO;
	}

	@Override
	public InsertResponseDTO nuevoFicheroTransferencias(List<FacAbonoItem> abonoItems, HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.nuevoFicheroTransferencias() -> Entrada al servicio para generar un nuevo fichero de transferencias");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			String fcs = "0";

			FacSeriefacturacionBancoExample bancosExample = new FacSeriefacturacionBancoExample();
			bancosExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion());

			List<FacSeriefacturacionBanco> bancos = facSeriefacturacionBancoMapper.selectByExample(bancosExample);

			for (FacSeriefacturacionBanco banco: bancos) {

				String bancosCodigo = banco.getBancosCodigo();
				Short idSufijo = banco.getIdsufijo();

				if (idSufijo == null) {
					throw new Exception("facturacion.ficheroBancarioTransferencias.errorSufijosSerie.mensajeCondicionesIncumplidas");
				}

				List<FacAbono> abonosBanco = facAbonoExtendsMapper.getAbonosBanco(usuario.getIdinstitucion(), bancosCodigo, idSufijo);
				int resultado = this.prepararFicheroTransferencias(fcs, usuario.getIdinstitucion(), bancosCodigo, idSufijo, abonosBanco, null, null, usuario);

				if (resultado == -1) {
					throw new Exception("general.mensaje.error.bbdd");
				}
			}

		}

		LOGGER.info(
				"FacturacionPySServiceImpl.nuevoFicheroTransferencias() -> Salida del servicio  para generar un nuevo fichero de transferencias");

		return insertResponseDTO;
	}

	private int prepararFicheroTransferencias(String fcs, Short idInstitucion,
											  String bancosCodigo,
											  Short idSufijo,
											  List<FacAbono> abonosBanco,
											  Integer idPropositoSEPA,
											  Integer idPropositoOtros,
											  AdmUsuarios usuario) throws Exception {
		Long idDisqueteAbono = facDisqueteabonosExtendsMapper.getNextIdDisqueteAbono(idInstitucion);
		String nombreFichero = getProperty("facturacion.prefijo.ficherosAbonos") + idDisqueteAbono;

		// Creación del nuevo fichero de transferencias
		FacDisqueteabonos record = new FacDisqueteabonos();
		record.setIdinstitucion(idInstitucion);
		record.setIddisqueteabono(idDisqueteAbono);
		record.setFecha(new Date());
		record.setFechaejecucion(new Date());
		record.setBancosCodigo(bancosCodigo);
		record.setFcs(fcs);
		record.setNombrefichero(nombreFichero);
		record.setNumerolineas(Long.valueOf(abonosBanco.size()));
		record.setIdsufijo(idSufijo);

		if (facDisqueteabonosExtendsMapper.insert(record) == 0) {
			return -1;
		}

		int numeroAbonosIncluidosEnDisquete = 0;
		for (FacAbono abono: abonosBanco) {
			FacAbonoKey abonoKey = new FacAbonoKey();
			abonoKey.setIdinstitucion(idInstitucion);
			abonoKey.setIdabono(abono.getIdabono());

			FacAbono abonoToUpdate = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

			Double importeAbonado = abonoToUpdate.getImppendienteporabonar().doubleValue();
			if (importeAbonado == 0)
				continue;

			numeroAbonosIncluidosEnDisquete++;

			// Introducimos el abono en el fichero de transferencias
			FacAbonoincluidoendisquete recordAbono = new FacAbonoincluidoendisquete();
			recordAbono.setIdinstitucion(idInstitucion);
			recordAbono.setIdabono(abono.getIdabono());
			recordAbono.setIddisqueteabono(idDisqueteAbono);
			recordAbono.setImporteabonado(new BigDecimal(importeAbonado).setScale(2, RoundingMode.DOWN));
			recordAbono.setContabilizado("N");
			facAbonoincluidoendisqueteExtendsMapper.insert(recordAbono);

			// Actualización de los importes
			Double impPendientePorAbonar = abonoToUpdate.getImppendienteporabonar().doubleValue() - importeAbonado;
			Double impTotalAbonado = abonoToUpdate.getImptotalabonado().doubleValue() + importeAbonado;
			Double impTotalAbonadoPorBanco = abonoToUpdate.getImptotalabonadoporbanco().doubleValue() + importeAbonado;

			abonoToUpdate.setImppendienteporabonar(new BigDecimal(impPendientePorAbonar).setScale(2, RoundingMode.DOWN));
			abonoToUpdate.setImptotalabonado(new BigDecimal(impTotalAbonado).setScale(2, RoundingMode.DOWN));
			abonoToUpdate.setImptotalabonadoporbanco(new BigDecimal(impTotalAbonadoPorBanco).setScale(2, RoundingMode.DOWN));

			//  Actualización del estado
			if (impPendientePorAbonar <= 0) {
				abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PAGADO);
			} else if (abonoToUpdate.getIdcuenta() != null) {
				abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_BANCO);
			} else {
				abonoToUpdate.setEstado(SigaConstants.FAC_ABONO_ESTADO_PENDIENTE_CAJA);
			}

			facAbonoExtendsMapper.updateByPrimaryKey(abonoToUpdate);
		}

		if (numeroAbonosIncluidosEnDisquete == 0) {
			return 0;
		}

		// Obtener la ruta del fichero
		String directorioFisico = "facturacion.directorioFisicoAbonosBancosJava";
		String directorio = "facturacion.directorioAbonosBancosJava";

		String rutaServidor = getProperty(directorioFisico) + getProperty(directorio)
				+ File.separator + usuario.getIdinstitucion();

		Object[] param_in = new Object[7]; // Parametros de entrada del PL

		param_in[0] = idInstitucion;
		param_in[1] = idDisqueteAbono;
		param_in[2] = idPropositoSEPA;
		param_in[3] = idPropositoOtros;
		param_in[4] = rutaServidor;
		param_in[5] = nombreFichero;
		param_in[6] = usuario.getIdlenguaje();

		String[] resultado = commons.callPLProcedureFacturacionPyS(
				"{call PKG_SIGA_ABONOS.Generarficherotransferencias("
						+ String.join(",", Collections.nCopies(9, "?"))
						+ ")}", 2, param_in);

		if (resultado[0] != null && resultado.length > 1 && string2Integer(resultado[0]) > 0) {
			throw new Exception(resultado[1]);
		}

		return 1;
	}

	@Override
	public FicherosDevolucionesDTO getFicherosDevoluciones(FicherosDevolucionesItem item, HttpServletRequest request)
			throws Exception {
		FicherosDevolucionesDTO ficherosDevolucionesDTO = new FicherosDevolucionesDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosDevoluciones() -> Entrada al servicio para obtener los ficheros de devoluciones");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"FacturacionPySServiceImpl.getFicherosDevoluciones() -> obteniendo datos de ficheros de devoluciones");

			List<FicherosDevolucionesItem> items = facDisquetedevolucionesExtendsMapper.getFicherosDevoluciones(item,
					usuario.getIdinstitucion().toString());

			ficherosDevolucionesDTO.setFicherosDevolucionesItems(items);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getFicherosDevoluciones() -> Salida del servicio  para obtener los ficheros de devoluciones");

		return ficherosDevolucionesDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO archivarFacturaciones(List<FacFacturacionprogramadaItem> facturacionProgramadaItems,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"archivarFacturaciones() -> Entrada al servicio para archivar/desarchivar facturaciones programadas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"archivarFacturaciones() / facFacturacionprogramadaExtendsMapper.updateByPrimaryKeySelective() -> Entrada a facFacturacionprogramadaExtendsMapper para archivar/desarchivar facturaciones programadas");

			// Logica
			for (FacFacturacionprogramadaItem item : facturacionProgramadaItems) {
				FacFacturacionprogramada record = new FacFacturacionprogramada();
				record.setIdinstitucion(usuario.getIdinstitucion());
				record.setIdprogramacion(Long.parseLong(item.getIdProgramacion()));
				record.setIdseriefacturacion(Long.parseLong(item.getIdSerieFacturacion()));

				record.setArchivarfact(item.getArchivarFact() ? "1" : "0");
				facFacturacionprogramadaExtendsMapper.updateByPrimaryKeySelective(record);
			}
		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"archivarFacturaciones() -> Salida del servicio para archivar/desarchivar facturaciones programadas");

		return updateResponseDTO;
	}

	@Override
	public FacturaDTO getFacturas(FacturaItem item, HttpServletRequest request) throws Exception {
		FacturaDTO facturaDTO = new FacturaDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		List<FacturaItem> items = new ArrayList<>();

		LOGGER.info("FacturacionPySServiceImpl.getFacturas() -> Entrada al servicio para obtener las facturas");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getFacturas() -> obteniendo las facturas");

			boolean filtrosSoloFactura = item.getIdentificadorAdeudos() != null
					|| item.getIdentificadorDevolucion() != null || item.getComunicacionesFacturasDesde() != null
					|| item.getComunicacionesFacturasHasta() != null || item.getImporteAdeudadoDesde() != null
					|| item.getImporteAdeudadoHasta() != null || item.getFacturacion() != null
					|| item.getSerie() != null || item.getFormaCobroFactura() != null
					|| (item.getEstadosFiltroFac() != null && item.getEstadosFiltroFac().size() > 0)
					|| item.getFacturasPendientesHasta() != null || item.getFacturasPendientesDesde() != null;

			boolean filtrosSoloAbono = item.getIdentificadorTransferencia() != null || item.getNumeroAbonoSJCS() != null
					|| item.getFormaCobroAbono() != null
					|| (item.getEstadosFiltroAb() != null && item.getEstadosFiltroAb().size() > 0);

			if (!(filtrosSoloAbono && !filtrosSoloFactura)) {
				items.addAll(facFacturaExtendsMapper.getFacturas(item, usuario.getIdinstitucion().toString(),
						usuario.getIdlenguaje()));
			}

			if (!(!filtrosSoloAbono && filtrosSoloFactura)) {
				items.addAll(facAbonoExtendsMapper.getAbonos(item, usuario.getIdinstitucion().toString(),
						usuario.getIdlenguaje(), items.size()));
			}

			facturaDTO.setFacturasItems(items);
		}

		LOGGER.info("FacturacionPySServiceImpl.getFacturas() -> Salida del servicio  para obtener las facturas");

		return facturaDTO;
	}

	@Override
	public FacturaDTO getFactura(String idFactura, String tipo, HttpServletRequest request) throws Exception {
		FacturaDTO facturaDTO = new FacturaDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getFactura() -> Entrada al servicio para obtener los detalles de la factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getFactura() -> obteniendo los detalles de la factura");

			if (tipo.equalsIgnoreCase("FACTURA")) {
				List<FacturaItem> items = facFacturaExtendsMapper.getFactura(idFactura,
						usuario.getIdinstitucion().toString());

				items.get(0).setCuentasBanco(cenCuentasbancariasExtendsMapper.getComboCuentas(
						items.get(0).getIdDeudor() == null ? items.get(0).getIdCliente() : items.get(0).getIdDeudor(),
						usuario.getIdinstitucion().toString()));

				facturaDTO.setFacturasItems(items);
			}

			if (tipo.equalsIgnoreCase("ABONO")) {
				List<FacturaItem> items = facAbonoExtendsMapper.getAbono(idFactura,
						usuario.getIdinstitucion().toString());

				facturaDTO.setFacturasItems(items);
			}

		}

		if (facturaDTO.getFacturasItems() == null || facturaDTO.getFacturasItems().isEmpty()) {
			throw new SigaExceptions("No se encuentra la factura");
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getFactura() -> Salida del servicio  para obtener los detalles de la factura");

		return facturaDTO;
	}

	@Override
	public UpdateResponseDTO guardaDatosFactura(FacturaItem item, HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.guardarObservacionesFactura() -> Entrada al servicio para guardar las observaciones de una factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacFacturaExtendsMapper.updateByPrimaryKey -> guardando las observaciones de una factura");

			if (item.getTipo().equalsIgnoreCase("FACTURA")) {

				FacFacturaKey key = new FacFacturaKey();
				key.setIdfactura(item.getIdFactura());
				key.setIdinstitucion(usuario.getIdinstitucion());
				FacFactura updateItem = facFacturaExtendsMapper.selectByPrimaryKey(key);

				if (item.getObservacionesFactura() != null)
					updateItem.setObservaciones(item.getObservacionesFactura());
				if (item.getObservacionesFicheroFactura() != null)
					updateItem.setObservinforme(item.getObservacionesFicheroFactura());

				facFacturaExtendsMapper.updateByPrimaryKey(updateItem);

				updateResponseDTO.setId(String.valueOf(item.getIdFactura()));
			}

			if (item.getTipo().equalsIgnoreCase("ABONO")) {

				FacAbonoKey key = new FacAbonoKey();
				key.setIdabono(Long.valueOf(item.getIdFactura()));
				key.setIdinstitucion(usuario.getIdinstitucion());
				FacAbono updateItem = facAbonoExtendsMapper.selectByPrimaryKey(key);

				if (item.getObservacionesAbono() != null)
					updateItem.setObservaciones(item.getObservacionesAbono());
				if (item.getMotivosAbono() != null)
					updateItem.setMotivos(item.getMotivosAbono());

				facAbonoExtendsMapper.updateByPrimaryKey(updateItem);

				updateResponseDTO.setId(String.valueOf(item.getIdFactura()));
			}

		}

		LOGGER.info(
				"guardarObservacionesFactura() -> Salida del servicio para guardar las observaciones de una factura");

		return updateResponseDTO;
	}

	@Override
	public FacturaLineaDTO getLineasFactura(String idFactura, HttpServletRequest request) throws Exception {
		FacturaLineaDTO facturaLineaDTO = new FacturaLineaDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getLineasFactura() -> Entrada al servicio para obtener las lineas de la factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getLineasFactura() -> obteniendo las lineas de la factura");

			List<FacturaLineaItem> items = facLineafacturaExtendsMapper.getLineasFactura(idFactura,
					usuario.getIdinstitucion().toString());

			facturaLineaDTO.setFacturasLineasItems(items);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getLineasFactura() -> Salida del servicio  para obtener las lineas de la factura");

		return facturaLineaDTO;
	}
	
	@Override
	public FacturaLineaDTO getLineasAbono(String idAbono, HttpServletRequest request) throws Exception {
		FacturaLineaDTO facturaLineaDTO = new FacturaLineaDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getLineasAbono() -> Entrada al servicio para obtener las lineas de la factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getLineasAbono() -> obteniendo las lineas de la factura");

			List<FacturaLineaItem> items = facLineaabonoExtendsMapper.getLineasAbono(idAbono,
					usuario.getIdinstitucion().toString());

			facturaLineaDTO.setFacturasLineasItems(items);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getLineasAbono() -> Salida del servicio  para obtener las lineas de la factura");

		return facturaLineaDTO;
	}

	@Override
	public UpdateResponseDTO guardarLineasFactura(FacturaLineaItem item, HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();
		GenParametros parametros = null;

		LOGGER.info(
				"FacturacionPySServiceImpl.guardarLineasFactura() -> Entrada al servicio para guardar las lineas de una factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacFacturaExtendsMapper.updateByPrimaryKey -> guardando las lineas de una factura");

			GenParametrosKey genKey = new GenParametros();
			genKey.setIdinstitucion(usuario.getIdinstitucion());
			genKey.setModulo("FAC");

			genKey.setParametro("MODIFICAR_DESCRIPCION");
			parametros = genParametrosMapper.selectByPrimaryKey(genKey);
			boolean modificarDescripcion = !(parametros == null || parametros.getValor().equals("0"));

			genKey.setParametro("MODIFICAR_IMPORTE_UNITARIO");
			parametros = genParametrosMapper.selectByPrimaryKey(genKey);
			boolean modificarImporteUnitario = !(parametros == null || parametros.getValor().equals("0"));

			genKey.setParametro("MODIFICAR_IVA");
			parametros = genParametrosMapper.selectByPrimaryKey(genKey);
			boolean modificarIVA = !(parametros == null || parametros.getValor().equals("0"));

			FacLineafacturaKey key = new FacLineafacturaKey();
			key.setIdfactura(item.getIdFactura());
			key.setNumerolinea(Long.valueOf(item.getNumeroLinea()));
			key.setIdinstitucion(usuario.getIdinstitucion());
			FacLineafactura updateItem = facLineafacturaExtendsMapper.selectByPrimaryKey(key);

			if (modificarDescripcion && item.getDescripcion() != null) {
				updateItem.setDescripcion(item.getDescripcion());
			}

			if (modificarImporteUnitario && item.getPrecioUnitario() != null) {
				updateItem.setPreciounitario(BigDecimal.valueOf(Double.parseDouble(item.getPrecioUnitario())));
			}

			if (modificarIVA && item.getTipoIVA() != null) {
				updateItem.setIdtipoiva(Integer.valueOf(item.getIdTipoIVA()));
			}

			if (item.getCantidad() != null) {
				updateItem.setCantidad(Integer.valueOf(item.getCantidad()));
			}

			if (item.getImporteAnticipado() != null) {
				updateItem.setImporteanticipado(BigDecimal.valueOf(Double.parseDouble(item.getImporteAnticipado())));
			}

			facLineafacturaExtendsMapper.updateByPrimaryKey(updateItem);

			updateResponseDTO.setId(String.valueOf(item.getIdFactura()));
		}

		LOGGER.info("guardarLineasFactura() -> Salida del servicio para guardar las lineas de una factura");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO guardarLineasAbono(FacturaLineaItem item, HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.guardarLineasAbono() -> Entrada al servicio para guardar las lineas de una factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacFacturaExtendsMapper.updateByPrimaryKey -> guardando las lineas de una factura");

			GenParametrosKey genKey = new GenParametros();
			genKey.setIdinstitucion(usuario.getIdinstitucion());
			genKey.setModulo("FAC");

			genKey.setParametro("MODIFICAR_DESCRIPCION");
			boolean modificarDescripcion = !genParametrosMapper.selectByPrimaryKey(genKey).getValor().equals("0");

			genKey.setParametro("MODIFICAR_IMPORTE_UNITARIO");
			boolean modificarImporteUnitario = !genParametrosMapper.selectByPrimaryKey(genKey).getValor().equals("0");

			FacLineaabonoKey key = new FacLineaabonoKey();
			key.setIdabono(Long.valueOf(item.getIdFactura()));
			key.setNumerolinea(Long.valueOf(item.getNumeroLinea()));
			key.setIdinstitucion(usuario.getIdinstitucion());
			FacLineaabono updateItem = facLineaabonoExtendsMapper.selectByPrimaryKey(key);

			if (modificarDescripcion && item.getDescripcion() != null) {
				updateItem.setDescripcionlinea(item.getDescripcion());
			}

			if (modificarImporteUnitario && item.getPrecioUnitario() != null) {
				updateItem.setPreciounitario(BigDecimal.valueOf(Double.parseDouble(item.getPrecioUnitario())));
			}

			if (item.getCantidad() != null) {
				updateItem.setCantidad(Integer.valueOf(item.getCantidad()));
			}

			facLineaabonoExtendsMapper.updateByPrimaryKey(updateItem);

			updateResponseDTO.setId(String.valueOf(item.getIdFactura()));
		}

		LOGGER.info("guardarLineasAbono() -> Salida del servicio para guardar las lineas de una factura");

		return updateResponseDTO;
	}

	@Override
	public ComunicacionCobroDTO getComunicacionCobro(String idFactura, HttpServletRequest request) throws Exception {
		ComunicacionCobroDTO comunicacionCobroDTO = new ComunicacionCobroDTO();
		List<ComunicacionCobroItem> items = new ArrayList<>();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getLineasFactura() -> Entrada al servicio para obtener las lineas de la factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getLineasFactura() -> obteniendo las lineas de la factura");

			EnvComunicacionmorososExample example = new EnvComunicacionmorososExample();
			example.createCriteria().andIdfacturaEqualTo(idFactura).andIdinstitucionEqualTo(usuario.getIdinstitucion());
			example.setOrderByClause("IDENVIO");

			List<EnvComunicacionmorosos> result = envComunicacionmorososMapper.selectByExample(example);

			for (EnvComunicacionmorosos env : result) {
				ComunicacionCobroItem item = new ComunicacionCobroItem();
				item.setDocumento(env.getDescripcion());
				item.setFechaEnvio(env.getFechaEnvio());
				item.setOrden(String.valueOf(env.getIdenvio()));
				items.add(item);
			}

			comunicacionCobroDTO.setComunicacionCobroItems(items);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getLineasFactura() -> Salida del servicio  para obtener las lineas de la factura");

		return comunicacionCobroDTO;
	}

	@Override
	public EstadosPagosDTO getEstadosPagos(String idFactura, HttpServletRequest request) throws Exception {
		EstadosPagosDTO estadosPagosDTO = new EstadosPagosDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getEstadosPagos() -> Entrada al servicio para obtener el historico de la factura");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("FacturacionPySServiceImpl.getEstadosPagos() -> obteniendo el historico de la factura");

			List<EstadosPagosItem> result = facHistoricofacturaExtendsMapper.getEstadosPagos(idFactura,
					usuario.getIdinstitucion().toString(), usuario.getIdlenguaje());

			estadosPagosDTO.setEstadosPagosItems(result);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getEstadosPagos() -> Salida del servicio  para obtener el historico de la factura");

		return estadosPagosDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO insertarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		insertResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("insertarEstadosPagos() -> Entrada al servicio para crear una entrada al historico de factura");

		if (usuario != null) {

			// factura
			FacFacturaKey facKey = new FacFacturaKey();
			facKey.setIdinstitucion(usuario.getIdinstitucion());
			facKey.setIdfactura(item.getIdFactura());
			FacFactura facUpdate = facFacturaExtendsMapper.selectByPrimaryKey(facKey);

			// ultima entrada
			FacHistoricofacturaExample example = new FacHistoricofacturaExample();
			example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdfacturaEqualTo(item.getIdFactura());
			example.setOrderByClause("IDHISTORICO");
			List<FacHistoricofactura> facHistoricoList = facHistoricofacturaExtendsMapper.selectByExample(example);

			FacHistoricofactura facHistoricoInsert = facHistoricoList.get(facHistoricoList.size() - 1);

			// pasamos parametros
			facHistoricoInsert.setIdhistorico((short) (facHistoricoInsert.getIdhistorico() + 1));
			facHistoricoInsert.setIdinstitucion(usuario.getIdinstitucion());
			facHistoricoInsert.setIdfactura(item.getIdFactura());
			facHistoricoInsert.setFechamodificacion(new Date());
			facHistoricoInsert.setUsumodificacion(usuario.getUsumodificacion());
			facHistoricoInsert.setFechamodificacion(item.getFechaModificaion());

			// abono (si tiene)
			if (facHistoricoInsert.getIdabono() != null) {
				FacAbonoKey abonoKey = new FacAbonoKey();
				abonoKey.setIdinstitucion(usuario.getIdinstitucion());
				abonoKey.setIdabono(facHistoricoInsert.getIdabono());
				FacAbono abonoUpdate = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

				// renegociar
				if (item.getIdAccion().equalsIgnoreCase("7")
						&& (abonoUpdate.getEstado() == 6 || abonoUpdate.getEstado() == 5)) {

					renegociarAbono(item, facHistoricoInsert, abonoUpdate, usuario);
				}

				// nuevo abono
				/**
				 * Id accion abono por caja no existe
				 */
				if (item.getIdAccion().equalsIgnoreCase("10") && abonoUpdate.getEstado() == 6) {

					nuevoAbono(item, facHistoricoInsert, abonoUpdate, usuario);
				}
			}

			// renegociar
			if (item.getIdAccion().equalsIgnoreCase("7") && (facHistoricoInsert.getEstado() == 2
					|| facHistoricoInsert.getEstado() == 4 || facHistoricoInsert.getEstado() == 5)) {

				renegociarFactura(item, facHistoricoInsert, facUpdate, usuario);
			}

			// nuevo cobro
			if (item.getIdAccion().equalsIgnoreCase("4") && facHistoricoInsert.getEstado() == 2) {

				nuevoCobroFactura(item, facHistoricoInsert, facUpdate, usuario);
			}

			// devolver
			if (item.getIdAccion().equalsIgnoreCase("6") && facHistoricoInsert.getEstado() == 1
					&& facHistoricoInsert.getIdtipoaccion() == 5) {

				devolverFactura(item, facHistoricoInsert, facUpdate, usuario);
			}

			// anular
			if (item.getIdAccion().equalsIgnoreCase("8") && facHistoricoInsert.getEstado() != 7
					&& facHistoricoInsert.getEstado() != 8) {

				anularFactura(item, facHistoricoInsert, facUpdate, usuario);
			}

			insertResponseDTO.setId(facHistoricoInsert.getIdfactura());
		}

		LOGGER.info("insertarEstadosPagos() -> Salida del servicio para crear una entrada al historico de factura");

		return insertResponseDTO;
	}

	private void renegociarFactura(EstadosPagosItem item, FacHistoricofactura facHistoricoInsert, FacFactura facUpdate,
			AdmUsuarios usuario) {
		// historico fac
		facHistoricoInsert.setIdtipoaccion(Short.valueOf(item.getIdAccion()));

		facHistoricoInsert.setIddisquetecargos(null);
		facHistoricoInsert.setIdfacturaincluidaendisquete(null);
		facHistoricoInsert.setIddisquetedevoluciones(null);
		facHistoricoInsert.setIdrecibo(null);
		facHistoricoInsert.setIdpagoporcaja(null);
		facHistoricoInsert.setIdabono(null);
		facHistoricoInsert.setComisionidfactura(null);

		FacRenegociacionExample exampleRenegociacion = new FacRenegociacionExample();
		exampleRenegociacion.createCriteria().andIdfacturaEqualTo(item.getIdFactura())
				.andIdinstitucionEqualTo(usuario.getIdinstitucion());
		exampleRenegociacion.setOrderByClause("IDRENEGOCIACION");

		List<FacRenegociacion> listRenegociacion = facRenegociacionMapper.selectByExample(exampleRenegociacion);
		if (!listRenegociacion.isEmpty())
			facHistoricoInsert.setIdrenegociacion(
					(short) (listRenegociacion.get(listRenegociacion.size() - 1).getIdrenegociacion() + 1));
		else
			facHistoricoInsert.setIdrenegociacion((short) 1);

		if (facHistoricoInsert.getIdcuenta() == null)
			facHistoricoInsert.setEstado((short) 2);
		else
			facHistoricoInsert.setEstado((short) 5);

		if (item.getIdEstado().equalsIgnoreCase("2")) {
			facHistoricoInsert.setIdformapago((short) 30);
			facHistoricoInsert.setEstado((short) 2);
		} else if (item.getIdEstado().equalsIgnoreCase("5")) {
			facHistoricoInsert.setIdformapago((short) 20);
			facHistoricoInsert.setEstado((short) 5);

			if (facHistoricoInsert.getIdpersonadeudor() != null) {
				facHistoricoInsert.setIdpersona(facHistoricoInsert.getIdpersonadeudor());
			}
			facHistoricoInsert.setIdcuenta(Short.valueOf(item.getCuentaBanco()));
		}

		// renegociacion
		FacRenegociacion renegociacionInsert = new FacRenegociacion();

		renegociacionInsert.setIdfactura(facHistoricoInsert.getIdfactura());
		renegociacionInsert.setIdinstitucion(facHistoricoInsert.getIdinstitucion());
		renegociacionInsert.setComentario(item.getComentario());
		renegociacionInsert.setUsumodificacion(usuario.getUsumodificacion());
		renegociacionInsert.setFecharenegociacion(new Date());
		renegociacionInsert.setFechamodificacion(new Date());
		renegociacionInsert.setIdcuenta(facHistoricoInsert.getIdcuenta());
		renegociacionInsert.setImporte(facHistoricoInsert.getImptotalporpagar());
		renegociacionInsert.setIdpersona(facHistoricoInsert.getIdpersona());
		renegociacionInsert.setIdrenegociacion(facHistoricoInsert.getIdrenegociacion());

		// factura
		facUpdate.setEstado(facHistoricoInsert.getEstado());
		facUpdate.setIdformapago(facHistoricoInsert.getIdformapago());
		facUpdate.setIdpersona(facHistoricoInsert.getIdpersona());
		facUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		facUpdate.setIdpersonadeudor(facHistoricoInsert.getIdpersonadeudor());
		facUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());
		facUpdate.setFechamodificacion(new Date());
		facUpdate.setUsumodificacion(usuario.getUsumodificacion());

		facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
		facRenegociacionMapper.insert(renegociacionInsert);
		facHistoricofacturaExtendsMapper.insert(facHistoricoInsert);
	}

	private void renegociarAbono(EstadosPagosItem item, FacHistoricofactura facHistoricoInsert, FacAbono abonoUpdate,
			AdmUsuarios usuario) {
		// historico fac
		facHistoricoInsert.setIdtipoaccion(Short.valueOf(item.getIdAccion()));

		facHistoricoInsert.setIddisquetecargos(null);
		facHistoricoInsert.setIdfacturaincluidaendisquete(null);
		facHistoricoInsert.setIddisquetedevoluciones(null);
		facHistoricoInsert.setIdrecibo(null);
		facHistoricoInsert.setIdpagoporcaja(null);
		facHistoricoInsert.setIdabono(null);
		facHistoricoInsert.setComisionidfactura(null);

		FacRenegociacionExample exampleRenegociacion = new FacRenegociacionExample();
		exampleRenegociacion.createCriteria().andIdfacturaEqualTo(item.getIdFactura())
				.andIdinstitucionEqualTo(usuario.getIdinstitucion());
		exampleRenegociacion.setOrderByClause("IDRENEGOCIACION");

		List<FacRenegociacion> listRenegociacion = facRenegociacionMapper.selectByExample(exampleRenegociacion);
		if (!listRenegociacion.isEmpty())
			facHistoricoInsert.setIdrenegociacion(
					(short) (listRenegociacion.get(listRenegociacion.size() - 1).getIdrenegociacion() + 1));
		else
			facHistoricoInsert.setIdrenegociacion((short) 1);

		if (facHistoricoInsert.getIdcuenta() == null)
			facHistoricoInsert.setEstado((short) 2);
		else
			facHistoricoInsert.setEstado((short) 5);

		if (item.getIdEstado().equalsIgnoreCase("2")) {
			facHistoricoInsert.setIdformapago((short) 30);
			facHistoricoInsert.setEstado((short) 2);
		} else if (item.getIdEstado().equalsIgnoreCase("5")) {
			facHistoricoInsert.setIdformapago((short) 20);
			facHistoricoInsert.setEstado((short) 5);

			if (facHistoricoInsert.getIdpersonadeudor() != null) {
				facHistoricoInsert.setIdpersona(facHistoricoInsert.getIdpersonadeudor());
			}
			facHistoricoInsert.setIdcuenta(Short.valueOf(item.getCuentaBanco()));
		}

		// renegociacion
		FacRenegociacion renegociacionInsert = new FacRenegociacion();

		renegociacionInsert.setIdfactura(facHistoricoInsert.getIdfactura());
		renegociacionInsert.setIdinstitucion(facHistoricoInsert.getIdinstitucion());
		renegociacionInsert.setComentario(item.getComentario());
		renegociacionInsert.setUsumodificacion(usuario.getUsumodificacion());
		renegociacionInsert.setFecharenegociacion(new Date());
		renegociacionInsert.setFechamodificacion(new Date());
		renegociacionInsert.setIdcuenta(facHistoricoInsert.getIdcuenta());
		renegociacionInsert.setImporte(facHistoricoInsert.getImptotalporpagar());
		renegociacionInsert.setIdpersona(facHistoricoInsert.getIdpersona());
		renegociacionInsert.setIdrenegociacion(facHistoricoInsert.getIdrenegociacion());

		// abono
		abonoUpdate.setEstado(Short.valueOf(item.getEstado()));
		abonoUpdate.setIdpersona(facHistoricoInsert.getIdpersona());
		abonoUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		abonoUpdate.setIdpersonadeudor(facHistoricoInsert.getIdpersonadeudor());
		abonoUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());
		abonoUpdate.setFechamodificacion(new Date());
		abonoUpdate.setUsumodificacion(usuario.getUsumodificacion());

		facAbonoExtendsMapper.updateByPrimaryKey(abonoUpdate);
		facRenegociacionMapper.insert(renegociacionInsert);
		facHistoricofacturaExtendsMapper.insert(facHistoricoInsert);
	}

	private void nuevoAbono(EstadosPagosItem item, FacHistoricofactura facHistoricoInsert, FacAbono abonoUpdate,
			AdmUsuarios usuario) {

		// abonos caja
		FacPagoabonoefectivo abonoCajaInsert = new FacPagoabonoefectivo();

		FacPagoabonoefectivoExample exampleAbonos = new FacPagoabonoefectivoExample();
		exampleAbonos.createCriteria().andIdabonoEqualTo(abonoUpdate.getIdabono())
				.andIdinstitucionEqualTo(usuario.getIdinstitucion());
		exampleAbonos.setOrderByClause("IDPAGOABONO");

		List<FacPagoabonoefectivo> listPagos = facPagoabonoefectivoMapper.selectByExample(exampleAbonos);
		if (!listPagos.isEmpty())
			abonoCajaInsert.setIdpagoabono((listPagos.get(listPagos.size() - 1).getIdpagoabono() + 1));
		else
			abonoCajaInsert.setIdpagoabono((long) 1);

		abonoCajaInsert.setIdinstitucion(usuario.getIdinstitucion());
		abonoCajaInsert.setIdabono(facHistoricoInsert.getIdabono());

		abonoCajaInsert.setImporte(BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado())));
		abonoCajaInsert.setFechamodificacion(new Date());
		abonoCajaInsert.setFecha(item.getFechaModificaion());
		abonoCajaInsert.setUsumodificacion(usuario.getUsumodificacion());
		abonoCajaInsert.setContabilizado(abonoUpdate.getContabilizada());

		// abono
		abonoUpdate.setObservaciones(item.getComentario());
		abonoUpdate.setIdfactura(facHistoricoInsert.getIdfactura());
		abonoUpdate.setImptotalabonado(
				abonoUpdate.getImptotalabonado().add(BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado()))));
		abonoUpdate.setImptotalabonadoefectivo(abonoUpdate.getImptotalabonadoefectivo()
				.add(BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado()))));
		abonoUpdate.setImppendienteporabonar(abonoUpdate.getImppendienteporabonar()
				.subtract(BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado()))));
		abonoUpdate.setFechamodificacion(new Date());
		abonoUpdate.setUsumodificacion(usuario.getUsumodificacion());
		abonoUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		abonoUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());

		if (abonoUpdate.getImppendienteporabonar().compareTo(BigDecimal.valueOf(0)) > 0)
			abonoUpdate.setEstado((short) 6);
		else
			abonoUpdate.setEstado((short) 1);

		facAbonoExtendsMapper.updateByPrimaryKey(abonoUpdate);
		facPagoabonoefectivoMapper.insert(abonoCajaInsert);
	}

	private void nuevoCobroFactura(EstadosPagosItem item, FacHistoricofactura facHistoricoInsert, FacFactura facUpdate,
			AdmUsuarios usuario) {
		// historico fac
		facHistoricoInsert.setIdtipoaccion(Short.valueOf(item.getIdAccion()));

		facHistoricoInsert.setIdformapago((short) 30);
		facHistoricoInsert.setIdcuenta(null);
		facHistoricoInsert.setIdcuentadeudor(null);

		facHistoricoInsert.setImptotalpagadoporcaja(facHistoricoInsert.getImptotalpagado().add(
				BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado()))));
		facHistoricoInsert.setImptotalpagadosolocaja(facHistoricoInsert.getImptotalpagadosolocaja().add(
				BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado()))));
		facHistoricoInsert.setImptotalpagado(facHistoricoInsert.getImptotalpagado().add(
				BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado()))));
		facHistoricoInsert.setImptotalporpagar(
				facHistoricoInsert.getImptotalporpagar().subtract(facHistoricoInsert.getImptotalpagado()));

		facHistoricoInsert.setIddisquetecargos(null);
		facHistoricoInsert.setIdfacturaincluidaendisquete(null);
		facHistoricoInsert.setIddisquetedevoluciones(null);
		facHistoricoInsert.setIdrecibo(null);
		facHistoricoInsert.setIdrenegociacion(null);
		facHistoricoInsert.setIdabono(null);
		facHistoricoInsert.setComisionidfactura(null);

		if (facHistoricoInsert.getImptotalporpagar().compareTo(BigDecimal.valueOf(0)) > 0)
			facHistoricoInsert.setEstado((short) 2);
		else
			facHistoricoInsert.setEstado((short) 1);

		FacPagosporcajaExample examplePagos = new FacPagosporcajaExample();
		examplePagos.createCriteria().andIdfacturaEqualTo(item.getIdFactura())
				.andIdinstitucionEqualTo(usuario.getIdinstitucion());
		examplePagos.setOrderByClause("IDPAGOPORCAJA");

		List<FacPagosporcaja> listPagos = facPagosporcajaMapper.selectByExample(examplePagos);
		if (!listPagos.isEmpty())
			facHistoricoInsert.setIdpagoporcaja((short) (listPagos.get(listPagos.size() - 1).getIdpagoporcaja() + 1));
		else
			facHistoricoInsert.setIdpagoporcaja((short) 1);

		// pagos caja
		FacPagosporcaja pagosCajaInsert = new FacPagosporcaja();

		pagosCajaInsert.setIdinstitucion(usuario.getIdinstitucion());
		pagosCajaInsert.setIdfactura(item.getIdFactura());
		pagosCajaInsert.setIdpagoporcaja(facHistoricoInsert.getIdpagoporcaja());
		pagosCajaInsert.setUsumodificacion(usuario.getUsumodificacion());

		pagosCajaInsert.setImporte(facHistoricoInsert.getImptotalpagado());
		pagosCajaInsert.setTarjeta("N");
		pagosCajaInsert.setFechamodificacion(new Date());
		pagosCajaInsert.setFecha(item.getFechaModificaion());
		pagosCajaInsert.setContabilizado(facUpdate.getContabilizada());

		pagosCajaInsert.setObservaciones(item.getComentario());

		// factura
		facUpdate.setImptotalpagado(facUpdate.getImptotalpagado().add(facHistoricoInsert.getImptotalpagado()));
		facUpdate.setImptotalpagadoporcaja(
				facUpdate.getImptotalpagadoporcaja().add(facHistoricoInsert.getImptotalpagado()));
		facUpdate.setImptotalpagadosolocaja(
				facUpdate.getImptotalpagadosolocaja().add(facHistoricoInsert.getImptotalpagado()));
		facUpdate.setImptotalporpagar(facUpdate.getImptotalporpagar().subtract(facHistoricoInsert.getImptotalpagado()));
		facUpdate.setFechamodificacion(new Date());
		facUpdate.setUsumodificacion(usuario.getUsumodificacion());
		facUpdate.setEstado(facHistoricoInsert.getEstado());
		facUpdate.setIdformapago(facHistoricoInsert.getIdformapago());
		facUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		facUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());

		// saves
		facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
		facPagosporcajaMapper.insert(pagosCajaInsert);
		facHistoricofacturaExtendsMapper.insert(facHistoricoInsert);
	}

	private void devolverFactura(EstadosPagosItem item, FacHistoricofactura facHistoricoInsert, FacFactura facUpdate,
			AdmUsuarios usuario) throws Exception {

		//Cliente
		CenClienteKey cenClienteKey = new CenClienteKey();
		cenClienteKey.setIdinstitucion(facUpdate.getIdinstitucion());
		cenClienteKey.setIdpersona(facUpdate.getIdpersona());
		CenCliente cliente = cenClienteMapper.selectByPrimaryKey(cenClienteKey);


		//Entidad Bancaria
		String codigoBanco = facBancoinstitucionExtendsMapper.getBancosCodigo(String.valueOf(facUpdate.getIdinstitucion()), facUpdate.getIdfactura()).get(0).getValue();

		FacBancoinstitucionKey bancoinstitucionKey = new FacBancoinstitucionKey();
		bancoinstitucionKey.setIdinstitucion(facHistoricoInsert.getIdinstitucion());
		bancoinstitucionKey.setBancosCodigo(codigoBanco);

		FacBancoinstitucion banco = facBancoinstitucionExtendsMapper.selectByPrimaryKey(bancoinstitucionKey);


		//Pago a devolver
		FacFacturaincluidaendisqueteKey facFacturaincluidaendisqueteKey = new FacFacturaincluidaendisqueteKey();
		facFacturaincluidaendisqueteKey.setIddisquetecargos(facHistoricoInsert.getIddisquetecargos());
		facFacturaincluidaendisqueteKey.setIdfacturaincluidaendisquete(facHistoricoInsert.getIdfacturaincluidaendisquete());
		facFacturaincluidaendisqueteKey.setIdinstitucion(facHistoricoInsert.getIdinstitucion());

		FacFacturaincluidaendisquete facFacturaincluidaendisquete = facFacturaincluidaendisqueteMapper.selectByPrimaryKey(facFacturaincluidaendisqueteKey);

		facFacturaincluidaendisquete.setDevuelta("S");
		facFacturaincluidaendisqueteMapper.updateByPrimaryKey(facFacturaincluidaendisquete);

		BigDecimal importeDevolver = facFacturaincluidaendisquete.getImporte();


		//Historico fac
		facHistoricoInsert.setIdtipoaccion(Short.valueOf(item.getIdAccion()));

		facHistoricoInsert.setImptotalporpagar(facHistoricoInsert.getImptotalporpagar().add(importeDevolver));
		facHistoricoInsert.setImptotalpagado(facHistoricoInsert.getImptotalpagado().subtract(importeDevolver));
		facHistoricoInsert.setImptotalpagadoporbanco(facHistoricoInsert.getImptotalpagadoporbanco().subtract(importeDevolver));

		facHistoricoInsert.setEstado((short) 4);

		//Objeto del PKG para la devolucion
		FacFacturaDevolucion devolucion = new FacFacturaDevolucion();

		devolucion.setIdIdioma(usuario.getIdlenguaje());
		devolucion.setIdInstitucion(usuario.getIdinstitucion());
		devolucion.setUsuModificacion(usuario.getUsumodificacion());

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
		String fecha = dateFormat.format(item.getFechaModificaion());

		devolucion.setFechaDevolucion(fecha);

		String listaFacturas = facHistoricoInsert.getIddisquetecargos() + "||"
				+ facHistoricoInsert.getIdfacturaincluidaendisquete() + "||" + facHistoricoInsert.getIdfactura() + "||"
				+ facHistoricoInsert.getIdrecibo() + "||" + item.getComentario();

		devolucion.setListaFacturas(listaFacturas);

		facHistoricoInsert.setIdrecibo("00" + facHistoricoInsert.getIdrecibo());


		//Factura Original
		facUpdate.setEstado((short) 4);
		facUpdate.setImptotalpagado(facHistoricoInsert.getImptotalpagado());
		facUpdate.setImptotalporpagar(facHistoricoInsert.getImptotalporpagar());
		facUpdate.setImptotalpagadoporbanco(facHistoricoInsert.getImptotalpagadoporbanco());
		facUpdate.setFechamodificacion(new Date());
		facUpdate.setUsumodificacion(usuario.getUsumodificacion());


		//Obtener ID Disquete Devoluciones
		String resultado[] = null;

		Object[] param_in = new Object[2]; // Parametros de entrada del PL

		param_in = new Object[5];

		param_in[0] = devolucion.getIdInstitucion();
		param_in[1] = devolucion.getListaFacturas();
		param_in[2] = devolucion.getFechaDevolucion();
		param_in[3] = devolucion.getIdIdioma();
		param_in[4] = devolucion.getUsuModificacion();

		resultado = commons.callPLProcedureFacturacionPyS(
				"{call PKG_SIGA_CARGOS.DevolucionesManuales(?,?,?,?,?,?,?,?)}", 3, param_in);

		if (resultado[0].equals("0")) {

			facHistoricoInsert.setIddisquetedevoluciones(Long.valueOf(devolucion.getListaIdDisquetesDevolucion()));

			facHistoricofacturaExtendsMapper.insert(facHistoricoInsert);
			facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);

			FacLineadevoludisqbancoKey facLineadevoludisqbancoKey = new FacLineadevoludisqbancoKey();
			facLineadevoludisqbancoKey.setIddisquetedevoluciones(facHistoricoInsert.getIddisquetedevoluciones());
			facLineadevoludisqbancoKey.setIdinstitucion(facHistoricoInsert.getIdinstitucion());
			facLineadevoludisqbancoKey.setIdrecibo(facHistoricoInsert.getIdrecibo());
			FacLineadevoludisqbanco lienaDevolucion = facLineadevoludisqbancoMapper.selectByPrimaryKey(facLineadevoludisqbancoKey);

			//Con Comision
			if(item.getComision() != null && item.getComision() && cliente.getComisiones().equals("1") && banco.getComisionimporte() != BigDecimal.valueOf(0)){

				//Copia Factura
				FacFactura facturaComision = facUpdate;
				facturaComision.setIdfactura(facFacturaExtendsMapper.getNewFacturaID(String.valueOf(facturaComision.getIdinstitucion())).get(0).getValue());


				//Historico Factura Anulada
				FacHistoricofactura fachistoricoAnulada = new FacHistoricofactura();

				fachistoricoAnulada.setIdfactura(facUpdate.getIdfactura());
				fachistoricoAnulada.setIdinstitucion(facUpdate.getIdinstitucion());

				fachistoricoAnulada.setIdtipoaccion((short) 9);
				fachistoricoAnulada.setEstado((short) 8);
				fachistoricoAnulada.setIdhistorico((short) (fachistoricoAnulada.getIdhistorico()+1));

				fachistoricoAnulada.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagado(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalporpagar(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalcompensado(facUpdate.getImptotal());

				facHistoricofacturaExtendsMapper.insert(fachistoricoAnulada);


				//Anular Factura Original
				facUpdate.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagado(BigDecimal.valueOf(0));
				facUpdate.setImptotalporpagar(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
				facUpdate.setImptotalcompensado(facUpdate.getImptotal());

				facUpdate.setEstado((short) 8);

				facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);


				//Factura Comision
				facturaComision.setFechaemision(item.getFechaModificaion());
				facturaComision.setEstado((short) 4);

				facturaComision.setComisionidfactura(facUpdate.getIdfactura());
				facturaComision.setNumerofactura(facFacturaExtendsMapper.getNuevoNumeroFactura(facturaComision.getIdinstitucion().toString(), facturaComision.getIdseriefacturacion().toString()).get(0).getValue());

				long IVAComision = Long.parseLong(facBancoinstitucionExtendsMapper.getPorcentajeIva(String.valueOf(facUpdate.getIdinstitucion()), banco.getBancosCodigo()).get(0).getValue());
				BigDecimal importeIVAComision = banco.getComisionimporte().multiply(BigDecimal.valueOf(IVAComision/100));

				facturaComision.setImptotalporpagar(facturaComision.getImptotalporpagar().add(importeIVAComision.add(banco.getComisionimporte())));
				facturaComision.setImptotal(facturaComision.getImptotal().add(importeIVAComision.add(banco.getComisionimporte())));
				facturaComision.setImptotaliva(facturaComision.getImptotaliva().add(importeIVAComision));
				facturaComision.setImptotalneto(facturaComision.getImptotalneto().add(banco.getComisionimporte()));

				facFacturaExtendsMapper.insert(facturaComision);


				//Actualizar Contador Factura
				FacSeriefacturacionKey facSeriefacturacionKey = new FacSeriefacturacionKey();
				facSeriefacturacionKey.setIdinstitucion(facturaComision.getIdinstitucion());
				facSeriefacturacionKey.setIdseriefacturacion(facturaComision.getIdseriefacturacion());

				AdmContadorKey admContadorKey = new AdmContadorKey();
				admContadorKey.setIdinstitucion(facturaComision.getIdinstitucion());
				admContadorKey.setIdcontador(facSeriefacturacionExtendsMapper.selectByPrimaryKey(facSeriefacturacionKey).getIdcontador());

				AdmContador admContador = admContadorMapper.selectByPrimaryKey(admContadorKey);
				admContador.setContador(admContador.getContador()+1);

				admContadorMapper.updateByPrimaryKey(admContador);


				//Historico Factura Comision
				FacHistoricofactura fachistoricoRevision = fachistoricoAnulada;

				fachistoricoRevision.setComisionidfactura(facUpdate.getIdfactura());

				fachistoricoRevision.setIdfactura(facturaComision.getIdfactura());
				fachistoricoRevision.setIdhistorico((short) 1);
				fachistoricoRevision.setIdtipoaccion((short) 1);
				fachistoricoRevision.setEstado((short) 7);

				fachistoricoRevision.setImptotalanticipado(BigDecimal.valueOf(0));
				fachistoricoRevision.setImptotalcompensado(facturaComision.getImptotal());

				facHistoricofacturaExtendsMapper.insert(fachistoricoRevision);

				FacHistoricofactura fachistoricoPendiente = fachistoricoRevision;

				fachistoricoRevision.setIdhistorico((short) 2);
				fachistoricoRevision.setIdtipoaccion((short) 2);
				fachistoricoRevision.setEstado((short) 9);

				facHistoricofacturaExtendsMapper.insert(fachistoricoPendiente);


				//Copia Lineas Factura
				FacLineafacturaExample exampleLinea = new FacLineafacturaExample();
				exampleLinea.createCriteria().andIdfacturaEqualTo(item.getIdFactura())
						.andIdinstitucionEqualTo(usuario.getIdinstitucion());
				exampleLinea.setOrderByClause("NUMEROLINEA");

				List<FacLineafactura> listLinea = facLineafacturaExtendsMapper.selectByExample(exampleLinea);

				long maximoNumeroOrden = 1;
				long maximoNumeroLinea = 1;

				for (FacLineafactura lf : listLinea) {
					lf.setIdfactura(facturaComision.getIdfactura());
					facLineafacturaExtendsMapper.insert(lf);

					if(lf.getNumeroorden() > maximoNumeroOrden){
						maximoNumeroOrden = lf.getNumeroorden();
					}
					if(lf.getNumerolinea() > maximoNumeroLinea){
						maximoNumeroLinea = lf.getNumerolinea();
					}
				}


				//Linea Factura Comision
				FacLineafactura lineaComision = new FacLineafactura();

				lineaComision.setIdinstitucion(facturaComision.getIdinstitucion());
				lineaComision.setIdfactura(facturaComision.getIdfactura());
				lineaComision.setCantidad(1);
				lineaComision.setFechamodificacion(new Date());
				lineaComision.setUsumodificacion(usuario.getUsumodificacion());
				lineaComision.setFechamodificacion(new Date());

				lineaComision.setIdformapago(facUpdate.getIdformapago());

				lineaComision.setPreciounitario(banco.getComisionimporte());
				lineaComision.setImporteanticipado(BigDecimal.valueOf(0));
				lineaComision.setIdtipoiva(banco.getIdtipoiva());
				lineaComision.setIva(BigDecimal.valueOf(IVAComision));

				lineaComision.setCtaiva(pySTipoIvaExtendsMapper.getC_CTAIVA(lineaComision.getIdinstitucion().toString(), lineaComision.getIdtipoiva().toString()).get(0).getValue());

				lineaComision.setCtaproductoservicio(banco.getComisioncuentacontable());

				lineaComision.setNumeroorden(maximoNumeroOrden + 1);
				lineaComision.setNumerolinea(maximoNumeroLinea + 1);

				GenRecursosKey genRecursosKey = new GenRecursosKey();
				genRecursosKey.setIdlenguaje(usuario.getIdlenguaje());
				genRecursosKey.setIdrecurso("certificados.tipocertificado.literal.comisionBancaria");

				lineaComision.setDescripcion(genRecursosMapper.selectByPrimaryKey(genRecursosKey).getDescripcion());

				facLineafacturaExtendsMapper.insert(lineaComision);


				//Carga Cliente
				lienaDevolucion.setCargarcliente("S");
			}


			//Carga Cliente Sin Comision
			else {
				lienaDevolucion.setCargarcliente("N");
			}


			//Guardar Carga Cliente
			facLineadevoludisqbancoMapper.updateByPrimaryKey(lienaDevolucion);

			// Aplicacion de comisiones
//				if (aplicaComisiones!=null && aplicaComisiones.equalsIgnoreCase(ClsConstants.DB_TRUE)){
//					
//					String [] aListaIdDisquetesDevolucion = retornoDevolucionManual[2].split(";");
//					for (String sIdDisquetesDevolucion : aListaIdDisquetesDevolucion) {
//				    	ClsLogging.writeFileLog("Aplicando Comisiones de devolucion=" + sIdDisquetesDevolucion, 8);
//				    	Facturacion facturacion = new Facturacion(user);
//				    	
//						// Identificamos los disquetes devueltos asociados al fichero de devoluciones
//						FacLineaDevoluDisqBancoAdm admLDDB= new FacLineaDevoluDisqBancoAdm(user);
//						Vector<FacLineaDevoluDisqBancoBean> vDevoluciones = admLDDB.obtenerDevoluciones(idInstitucion, sIdDisquetesDevolucion, false);
//						
//						// Aplicamos la comision a cada devolucion
//						for (int d=0; d<vDevoluciones.size(); d++) {
//							FacLineaDevoluDisqBancoBean lineaDevolucion = (FacLineaDevoluDisqBancoBean) vDevoluciones.get(d);
//							facturacion.aplicarComisionAFactura (idInstitucion, lineaDevolucion, aplicaComisiones, user, fechaDevolucionHora);
//						}
//					}
//			    }

		} else if (resultado[0].equals("5404")) {
			throw new Exception("facturacion.devolucionManual.error.fechaDevolucion");

		} else {
			throw new Exception(
					"Fichero de devoluciones manuales: Error en el proceso de actualicacion de tablas de devolucion.");
		}
	}

	private void anularFactura(EstadosPagosItem item, FacHistoricofactura facHistoricoInsert, FacFactura facUpdate,
			AdmUsuarios usuario) {

		//Historico Factura Anulada
		facHistoricoInsert.setIdfactura(facUpdate.getIdfactura());
		facHistoricoInsert.setIdinstitucion(facUpdate.getIdinstitucion());

		facHistoricoInsert.setIdtipoaccion((short) 8);
		facHistoricoInsert.setEstado((short) 8);

		facHistoricoInsert.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
		facHistoricoInsert.setImptotalpagado(BigDecimal.valueOf(0));
		facHistoricoInsert.setImptotalporpagar(BigDecimal.valueOf(0));
		facHistoricoInsert.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
		facHistoricoInsert.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
		facHistoricoInsert.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
		facHistoricoInsert.setImptotalcompensado(facUpdate.getImptotal());

		facHistoricoInsert.setIddisquetecargos(null);
		facHistoricoInsert.setIdfacturaincluidaendisquete(null);
		facHistoricoInsert.setIddisquetedevoluciones(null);
		facHistoricoInsert.setIdrecibo(null);
		facHistoricoInsert.setIdpagoporcaja(null);
		facHistoricoInsert.setIdrenegociacion(null);
		facHistoricoInsert.setIdabono(null);
		facHistoricoInsert.setComisionidfactura(null);


		//Anular Factura Original
		facUpdate.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
		facUpdate.setImptotalpagado(BigDecimal.valueOf(0));
		facUpdate.setImptotalporpagar(BigDecimal.valueOf(0));
		facUpdate.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
		facUpdate.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
		facUpdate.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
		facUpdate.setImptotalcompensado(facUpdate.getImptotal());

		facUpdate.setEstado((short) 8);

		facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);


		//Abono
		FacAbono abonoInsert = new FacAbono();

		abonoInsert.setIdinstitucion(facUpdate.getIdinstitucion());
		abonoInsert.setIdfactura(item.getIdFactura());
		abonoInsert.setFecha(item.getFechaModificaion());
		abonoInsert.setFechamodificacion(new Date());
		abonoInsert.setContabilizada(facUpdate.getContabilizada());
		abonoInsert.setIdpersona(facUpdate.getIdpersona());
		abonoInsert.setUsumodificacion(usuario.getUsumodificacion());
		abonoInsert.setIdcuenta(facUpdate.getIdcuenta());
		abonoInsert.setMotivos(item.getComentario());
		abonoInsert.setImptotalneto(facUpdate.getImptotalneto());
		abonoInsert.setImptotaliva(facUpdate.getImptotaliva());
		abonoInsert.setImptotal(facUpdate.getImptotal());
		abonoInsert.setImptotalabonadoefectivo(BigDecimal.valueOf(0));
		abonoInsert.setImptotalabonadoporbanco(BigDecimal.valueOf(0));
		abonoInsert.setImptotalabonado(BigDecimal.valueOf(0));
		abonoInsert.setImppendienteporabonar(facUpdate.getImptotalpagado());
		abonoInsert.setIdpersonadeudor(facUpdate.getIdpersonadeudor());
		abonoInsert.setIdcuentadeudor(facUpdate.getIdcuentadeudor());

		abonoInsert.setIdabono(Long.valueOf(
				facAbonoExtendsMapper.getNewAbonoID(String.valueOf(usuario.getIdinstitucion())).get(0).getValue()));

		abonoInsert.setNumeroabono(facAbonoExtendsMapper.getNuevoNumeroAbono(facUpdate.getIdinstitucion().toString(), "FAC_ABONOS_GENERAL").get(0).getValue());

		abonoInsert.setEstado((short) 6);

		facHistoricoInsert.setIdabono(abonoInsert.getIdabono());


		//Actualizar Contador Factura
		AdmContadorKey admContadorKey = new AdmContadorKey();
		admContadorKey.setIdinstitucion(facUpdate.getIdinstitucion());
		admContadorKey.setIdcontador("FAC_ABONOS_GENERAL");

		AdmContador admContador = admContadorMapper.selectByPrimaryKey(admContadorKey);
		admContador.setContador(admContador.getContador()+1);

		admContadorMapper.updateByPrimaryKey(admContador);

		// saves
		facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
		facAbonoExtendsMapper.insert(abonoInsert);
		facHistoricofacturaExtendsMapper.insert(facHistoricoInsert);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminarEstadosPagos(EstadosPagosItem item, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		deleteResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("insertarEstadosPagos() -> Entrada al servicio para crear una entrada al historico de factura");

		if (usuario != null && item.getIdAccion().equalsIgnoreCase("4")) {

			// factura
			FacFacturaKey facKey = new FacFacturaKey();
			facKey.setIdinstitucion(usuario.getIdinstitucion());
			facKey.setIdfactura(item.getIdFactura());
			FacFactura facUpdate = facFacturaExtendsMapper.selectByPrimaryKey(facKey);

			// ultima entrada
			FacHistoricofacturaExample example = new FacHistoricofacturaExample();
			example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andIdfacturaEqualTo(item.getIdFactura());
			example.setOrderByClause("IDHISTORICO");
			List<FacHistoricofactura> facHistoricoList = facHistoricofacturaExtendsMapper.selectByExample(example);

			FacHistoricofactura facHistorico = facHistoricoList.get(facHistoricoList.size() - 2);

			FacPagosporcajaExample examplePagos = new FacPagosporcajaExample();
			examplePagos.createCriteria().andIdfacturaEqualTo(item.getIdFactura())
					.andIdinstitucionEqualTo(usuario.getIdinstitucion());
			example.setOrderByClause("IDPAGOPORCAJA");

			List<FacPagosporcaja> listPagos = facPagosporcajaMapper.selectByExample(examplePagos);

			FacPagosporcaja pagosCajaDelete = listPagos.get(listPagos.size() - 1);

			// factura
			facUpdate.setImptotalpagado(facUpdate.getImptotalpagado().add(facHistorico.getImptotalpagado()));
			facUpdate.setImptotalpagadoporcaja(
					facUpdate.getImptotalpagadoporcaja().add(facHistorico.getImptotalpagado()));
			facUpdate.setImptotalpagadosolocaja(
					facUpdate.getImptotalpagadosolocaja().add(facHistorico.getImptotalpagado()));
			facUpdate.setImptotalporpagar(facUpdate.getImptotalporpagar().subtract(facHistorico.getImptotalpagado()));
			facUpdate.setFechamodificacion(new Date());
			facUpdate.setUsumodificacion(usuario.getUsumodificacion());
			facUpdate.setEstado(facHistorico.getEstado());
			facUpdate.setIdformapago(facHistorico.getIdformapago());
			facUpdate.setIdcuenta(facHistorico.getIdcuenta());
			facUpdate.setIdcuentadeudor(facHistorico.getIdcuentadeudor());

			// saves
			facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
			facHistoricofacturaExtendsMapper.deleteByPrimaryKey(facHistoricoList.get(facHistoricoList.size() - 1));
			facPagosporcajaMapper.deleteByPrimaryKey(pagosCajaDelete);

			deleteResponseDTO.setStatus(HttpStatus.OK.toString());
		}

		LOGGER.info("insertarEstadosPagos() -> Salida del servicio para crear una entrada al historico de factura");

		return deleteResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO nuevoFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
			throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		insertResponseDTO.setError(error);

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("nuevoFicheroAdeudos() -> Entrada al servicio para crear un fichero de adeudos");

		if (usuario != null) {
			// Comprobar los campos obligatorios
			if ( Objects.isNull(ficheroAdeudosItem.getFechaPresentacion())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosPrimeros())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosRecurrentes())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosCOR())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosB2B())) {
				throw new Exception("general.message.camposObligatorios");
			}

			Object[] param_in = new Object[11]; // Parametros de entrada del PL

			// Ruta del fichero
			String pathFichero = getProperty("facturacion.directorioBancosOracle");

			String sBarra = "";
			if (pathFichero.indexOf("/") > -1) sBarra = "/";
			if (pathFichero.indexOf("\\") > -1) sBarra = "\\";
			pathFichero += sBarra + usuario.getIdinstitucion().toString();

			// Parámetros de entrada
			param_in[0] = usuario.getIdinstitucion();
			param_in[1] = Objects.nonNull(ficheroAdeudosItem.getIdseriefacturacion()) ? ficheroAdeudosItem.getIdseriefacturacion() : "";
			param_in[2] = Objects.nonNull(ficheroAdeudosItem.getIdprogramacion()) ? ficheroAdeudosItem.getIdprogramacion() : "";
			param_in[3] = formatDate.format(ficheroAdeudosItem.getFechaPresentacion());
			param_in[4] = formatDate.format(ficheroAdeudosItem.getFechaRecibosPrimeros());
			param_in[5] = formatDate.format(ficheroAdeudosItem.getFechaRecibosRecurrentes());
			param_in[6] = formatDate.format(ficheroAdeudosItem.getFechaRecibosCOR());
			param_in[7] = formatDate.format(ficheroAdeudosItem.getFechaRecibosB2B());
			param_in[8] = pathFichero;
			param_in[9] = usuario.getIdusuario();
			param_in[10] = usuario.getIdlenguaje();

			String[] resultado = commons.callPLProcedureFacturacionPyS(
					"{call Pkg_Siga_Cargos.Presentacion(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", 3, param_in);

			String[] codigosErrorFormato = {"5412", "5413", "5414", "5415", "5416", "5417", "5418", "5421", "5422"};
			if (Arrays.asList(codigosErrorFormato).contains(resultado[1])) {
				throw new Exception(resultado[2]);
			} else {
				if (!resultado[1].equals("0")) {
					throw new Exception("censo.fichaCliente.bancos.mandatos.error.generacionFicheros");
				}
			}
			insertResponseDTO.setId(resultado[0]);
		}

		LOGGER.info("nuevoFicheroAdeudos() -> Salida del servicio para crear un fichero de adeudos");

		return insertResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO actualizarFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		updateResponseDTO.setError(error);

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("actualizarFicheroAdeudos() -> Entrada al servicio para actualizar un fichero de adeudos");

		if (usuario != null) {
			// Comprobar los campos obligatorios
			if (Objects.isNull(ficheroAdeudosItem.getFechaPresentacion())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosPrimeros())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosRecurrentes())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosCOR())
					|| Objects.isNull(ficheroAdeudosItem.getFechaRecibosB2B())) {
				throw new Exception("general.message.camposObligatorios");
			}

			Object[] param_in = new Object[9]; // Parametros de entrada del PL

			// Ruta del fichero
			String pathFichero = getProperty("facturacion.directorioBancosOracle");

			String sBarra = "";
			if (pathFichero.indexOf("/") > -1) sBarra = "/";
			if (pathFichero.indexOf("\\") > -1) sBarra = "\\";
			pathFichero += sBarra + usuario.getIdinstitucion().toString();

			// Se borran todos os ficheros que contenga el identificador del fichero de abonos
			File directorioFicheros = new File(pathFichero);
			if (directorioFicheros.exists() && directorioFicheros.isDirectory()){
				File[] ficheros = directorioFicheros.listFiles();
				for (int x=0; x<ficheros.length; x++){
					String nombreFichero = ficheros[x].getName();
					if (nombreFichero.startsWith(ficheroAdeudosItem.getIdDisqueteCargos() + ".")) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						nombreFichero = sdf.format(new Date()) + "_" + nombreFichero;

						File newFile = new File(directorioFicheros, nombreFichero);
						ficheros[x].renameTo(newFile);
						// Se cambia el nombre del archivo para que no se interponga en la nueva generación
					}
				}
			}

			// Parámetros de entrada
			param_in[0] = usuario.getIdinstitucion();
			param_in[1] = ficheroAdeudosItem.getIdDisqueteCargos();
			param_in[2] = formatDate.format(ficheroAdeudosItem.getFechaPresentacion());
			param_in[3] = formatDate.format(ficheroAdeudosItem.getFechaRecibosPrimeros());
			param_in[4] = formatDate.format(ficheroAdeudosItem.getFechaRecibosRecurrentes());
			param_in[5] = formatDate.format(ficheroAdeudosItem.getFechaRecibosCOR());
			param_in[6] = formatDate.format(ficheroAdeudosItem.getFechaRecibosB2B());
			param_in[7] = pathFichero;
			param_in[8] = usuario.getIdlenguaje();

			String[] resultado = commons.callPLProcedureFacturacionPyS(
					"{call PKG_SIGA_CARGOS.Regenerar_Presentacion(?,?,?,?,?,?,?,?,?,?,?)}", 2, param_in);

			String[] codigosErrorFormato = {"5412", "5413", "5414", "5415", "5416", "5417", "5418", "5421", "5422"};
			if (Arrays.asList(codigosErrorFormato).contains(resultado[0])) {
				throw new Exception(resultado[1]);
			} else {
				if (!resultado[1].equals("0")) {
					throw new Exception("censo.fichaCliente.bancos.mandatos.error.generacionFicheros");
				}
			}
		}

		LOGGER.info("actualizarFicheroAdeudos() -> Salida del servicio para actualizar un fichero de adeudos");

		return updateResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminarFicheroAdeudos(FicherosAdeudosItem ficheroAdeudosItem, HttpServletRequest request)
			throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		deleteResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("deleteResponseDTO() -> Entrada al servicio para eliminar un fichero de adeudos");


		LOGGER.info("deleteResponseDTO() -> Salida del servicio para eliminar un fichero de adeudos");

		return deleteResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO actualizarFicheroTranferencias(FacDisqueteabonos updateItem, HttpServletRequest request)
			throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		updateResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("actualizarFicheroDevoluciones() -> Entrada al servicio para actualizar un fichero devoluciones");

		if (usuario != null) {
			// Clave primaria
			FacDisqueteabonosKey key = new FacDisqueteabonosKey();
			key.setIddisqueteabono(updateItem.getIddisqueteabono());
			key.setIdinstitucion(usuario.getIdinstitucion());

			FacDisqueteabonos record = facDisqueteabonosExtendsMapper.selectByPrimaryKey(key);

			if (updateItem.getBancosCodigo() != null)
				record.setBancosCodigo(updateItem.getBancosCodigo());
			if (updateItem.getFcs() != null)
				record.setFcs(updateItem.getFcs());
			if (updateItem.getFecha() != null)
				record.setFecha(updateItem.getFecha());
			if (updateItem.getFechaejecucion() != null)
				record.setFechaejecucion(updateItem.getFechaejecucion());
			if (updateItem.getFechamodificacion() != null)
				record.setFechamodificacion(updateItem.getFechamodificacion());
			if (updateItem.getIdsufijo() != null)
				record.setIdsufijo(updateItem.getIdsufijo());
			if (updateItem.getNombrefichero() != null)
				record.setNombrefichero(updateItem.getNombrefichero());
			if (updateItem.getNumerolineas() != null)
				record.setNumerolineas(updateItem.getNumerolineas());
			if (updateItem.getUsumodificacion() != null)
				record.setUsumodificacion(updateItem.getUsumodificacion());

			facDisqueteabonosExtendsMapper.updateByPrimaryKey(record);

			updateResponseDTO.setId(record.getIddisqueteabono().toString());
		}

		LOGGER.info("actualizarProgramacionFactura() -> Salida del servicio para actualizar un fichero devoluciones");

		return updateResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO actualizarFicheroDevoluciones(FacDisquetedevoluciones updateItem,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		updateResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("actualizarFicheroDevoluciones() -> Entrada al servicio para actualizar un fichero devoluciones");

		if (usuario != null) {
			// Clave primaria
			FacDisquetedevolucionesKey key = new FacDisquetedevolucionesKey();
			key.setIddisquetedevoluciones(updateItem.getIddisquetedevoluciones());
			key.setIdinstitucion(usuario.getIdinstitucion());

			FacDisquetedevoluciones record = facDisquetedevolucionesExtendsMapper.selectByPrimaryKey(key);

			if (updateItem.getBancosCodigo() != null)
				record.setBancosCodigo(updateItem.getBancosCodigo());
			if (updateItem.getFechageneracion() != null)
				record.setFechageneracion(updateItem.getFechageneracion());
			if (updateItem.getFechamodificacion() != null)
				record.setFechamodificacion(updateItem.getFechamodificacion());
			if (updateItem.getNombrefichero() != null)
				record.setNombrefichero(updateItem.getNombrefichero());
			if (updateItem.getUsumodificacion() != null)
				record.setUsumodificacion(updateItem.getUsumodificacion());

			facDisquetedevolucionesExtendsMapper.updateByPrimaryKey(record);

			updateResponseDTO.setId(record.getIddisquetedevoluciones().toString());
		}

		LOGGER.info("actualizarProgramacionFactura() -> Salida del servicio para actualizar un fichero devoluciones");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO insertarProgramacionFactura(FacFacturacionprogramadaItem facItem,
			HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		insertResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("insertarProgramacionFactura() -> Entrada al servicio para crear una programación de factura");

		if (usuario != null) {
			FacFacturacionprogramada facProg = creaFacturacionProgramadaDesdeItem(facItem, usuario);
			facFacturacionprogramadaExtendsMapper.insertSelective(facProg);

			insertResponseDTO.setId(facProg.getIdprogramacion().toString());
		}

		LOGGER.info("insertarProgramacionFactura() -> Salida del servicio para crear una programación de factura");

		return insertResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateResponseDTO actualizarProgramacionFactura(FacFacturacionprogramadaItem facItem,
			HttpServletRequest request) throws Exception {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		updateResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info(
				"actualizarProgramacionFactura() -> Entrada al servicio para actualizar una programación de factura");

		if (usuario != null) {
			// Clave primaria
			FacFacturacionprogramadaKey key = new FacFacturacionprogramadaKey();
			key.setIdinstitucion(usuario.getIdinstitucion());
			key.setIdprogramacion(string2Long(facItem.getIdProgramacion()));
			key.setIdseriefacturacion(string2Long(facItem.getIdSerieFacturacion()));

			FacFacturacionprogramada record = facFacturacionprogramadaExtendsMapper.selectByPrimaryKey(key);

			actualizarProgramacionDesdeItem(record, facItem);

			facFacturacionprogramadaExtendsMapper.updateByPrimaryKey(record);

			updateResponseDTO.setId(record.getIdprogramacion().toString());
		}

		LOGGER.info(
				"actualizarProgramacionFactura() -> Salida del servicio para actualizar una programación de factura");

		return updateResponseDTO;
	}

	@Override
	public FacturasIncluidasDTO getFacturasIncluidas(String idFichero, String tipoFichero, HttpServletRequest request)
			throws Exception {
		FacturasIncluidasDTO facturasIncluidasDTO = new FacturasIncluidasDTO();
		Error error = new Error();
		facturasIncluidasDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("getFacturasIncluidas() -> Entrada al servicio para  devolver las facturas incluidas");

		if (usuario != null) {
			LOGGER.info(
					"FacturacionPySServiceImpl.FacturacionPySServiceImpl() -> obteniendo las las facturas incluidas");

			if (tipoFichero.equalsIgnoreCase("A"))
				facturasIncluidasDTO.setFacturasIncluidasItem(facDisquetecargosExtendsMapper.getFacturasIncluidas(
						idFichero, usuario.getIdinstitucion().toString(), usuario.getIdlenguaje()));

			if (tipoFichero.equalsIgnoreCase("T"))
				facturasIncluidasDTO.setFacturasIncluidasItem(facDisqueteabonosExtendsMapper.getFacturasIncluidas(
						idFichero, usuario.getIdinstitucion().toString(), usuario.getIdlenguaje()));

			if (tipoFichero.equalsIgnoreCase("D"))
				facturasIncluidasDTO.setFacturasIncluidasItem(facDisquetedevolucionesExtendsMapper.getFacturasIncluidas(
						idFichero, usuario.getIdinstitucion().toString(), usuario.getIdlenguaje()));
		}

		LOGGER.info("FacturacionPySServiceImpl() -> Salida del servicio para devolver las facturas incluidas");

		return facturasIncluidasDTO;
	}

	private void actualizarProgramacionDesdeItem(FacFacturacionprogramada record,
			FacFacturacionprogramadaItem facItem) {
		if (record.getIdestadoconfirmacion() != null && (record.getIdestadoconfirmacion() == string2Short("20") // Generación
																												// con
																												// errores
				|| record.getIdestadoconfirmacion() == string2Short("2") // Generada
				|| record.getIdestadoconfirmacion() == string2Short("21") // Confirmación con errores
		)) {

			// Tarjeta de datos generales
			record.setFechaprevistageneracion(facItem.getFechaPrevistaGeneracion());
			record.setFechaprevistaconfirm(facItem.getFechaPrevistaConfirm());

			// Tarjeta de generación de fichero de adeudos
			record.setFechapresentacion(facItem.getFechaPresentacion());
			record.setFecharecibosprimeros(facItem.getFechaRecibosPrimeros());
			record.setFecharecibosrecurrentes(facItem.getFechaRecibosRecurrentes());
			record.setFechareciboscor1(facItem.getFechaRecibosCOR1());
			record.setFecharecibosb2b(facItem.getFechaRecibosB2B());
		}

		// Procesos automáticos
		if (record.getIdestadoconfirmacion() != null && (record.getIdestadoconfirmacion() == string2Short("18") // Generada
																												// programada
				|| record.getIdestadoconfirmacion() == string2Short("20") // Generación con errores
				|| record.getIdestadoconfirmacion() == string2Short("2") // Generada
				|| record.getIdestadoconfirmacion() == string2Short("19") // Generando
				|| record.getIdestadoconfirmacion() == string2Short("21") // Confirmación con errores
				|| record.getIdestadoconfirmacion() == string2Short("3") // Confirmada
		)) {

			// Datos de la tarjeta generación de ficheros
			record.setGenerapdf(facItem.getGeneraPDF() != null && facItem.getGeneraPDF() ? "1" : "0");
			// seriefacturacion.getIdmodelofactura();
			// seriefacturacion.getIdmodelorectificativa();

			if (record.getIdestadoconfirmacion() == string2Short("3") && record.getIdestadopdf() != null
					|| record.getIdestadopdf() == string2Short("5") // Confirmada y no aplica
							&& record.getIdestadopdf() == string2Short("10")) { // Confirmada y finalizada con errores
				if (facItem.getGeneraPDF()) {
					record.setIdestadopdf(Short.parseShort("7")); // Pendiente
				} else {
					record.setIdestadopdf(Short.parseShort("5")); // No aplica
				}
			}

			// Datos de la tarjeta envío facturas
			record.setEnvio(facItem.getEnvio() != null && facItem.getEnvio() ? "1" : "0");
			if (!UtilidadesString.esCadenaVacia(facItem.getIdTipoPlantillaMail())) {
				record.setIdtipoplantillamail(string2Integer(facItem.getIdTipoPlantillaMail()));
				record.setIdtipoenvios(Short.parseShort("1"));
			} else {
				record.setIdtipoplantillamail(null);
				record.setIdtipoenvios(null);
			}

			if (record.getIdestadoconfirmacion() == string2Short("3") && record.getIdestadoenvio() != null
					|| record.getIdestadoenvio() == string2Short("11") // Confirmada y no aplica
							&& record.getIdestadoenvio() == string2Short("16")) { // Confirmada y finalizada con errores
				if (facItem.getEnvio()) {
					record.setIdestadoenvio(Short.parseShort("13")); // Pendiente
				} else {
					record.setIdestadoenvio(Short.parseShort("11")); // No aplica
				}
			}

			// Datos de la tarjeta generación de traspasos
			record.setTraspasofacturas(facItem.getTraspasoFacturas() != null && facItem.getTraspasoFacturas() ? "1" : "0");
			record.setTraspasoPlantilla(facItem.getTraspasoPlatilla());
			record.setTraspasoCodauditoriaDef(facItem.getTraspasoCodAuditoriaDef());

			if (record.getIdestadoconfirmacion() == string2Short("3") && record.getIdestadotraspaso() != null
					|| record.getIdestadotraspaso() == string2Short("22") // Confirmada y no aplica
							&& record.getIdestadotraspaso() == string2Short("27")) { // Confirmada y finalizada con
																						// errores
				if (facItem.getTraspasoFacturas()) {
					record.setIdestadotraspaso(Short.parseShort("24")); // Pendiente
				} else {
					record.setIdestadotraspaso(Short.parseShort("22")); // No aplica
				}
			}
		}

		if (record.getIdestadoconfirmacion() != null && facItem.getEsDatosGenerales()
				&& (record.getIdestadoconfirmacion() == string2Short("21") // Confirmación con errores
						|| record.getIdestadoconfirmacion() == string2Short("2") // Generada
				)) {

			// Datos generales -> Fecha de confirmación
			if (facItem.getFechaPrevistaConfirm() != null) {
				record.setFechaprevistaconfirm(facItem.getFechaPrevistaConfirm());
				record.setIdestadoconfirmacion(string2Short("1")); // Confirmación programada
			} else {
				record.setIdestadoconfirmacion(string2Short("18")); // Generación programada
			}
		}
	}

	private FacFacturacionprogramada creaFacturacionProgramadaDesdeItem(FacFacturacionprogramadaItem facItem,
			AdmUsuarios usuario) {
		FacFacturacionprogramada fac = new FacFacturacionprogramada();

		String idProgramacion = facFacturacionprogramadaExtendsMapper.getNextIdFacturacionProgramada(
				usuario.getIdinstitucion(), Long.parseLong(facItem.getIdSerieFacturacion())).getNewId();

		fac.setUsumodificacion(usuario.getIdusuario());
		fac.setFechamodificacion(new Date());
		fac.setFechaprogramacion(new Date());

		// Clave primaria
		fac.setIdprogramacion(string2Long(idProgramacion));
		fac.setIdseriefacturacion(string2Long(facItem.getIdSerieFacturacion()));
		fac.setIdinstitucion(usuario.getIdinstitucion());

		// Comprobamos que la descripción sea única
		if (!UtilidadesString.esCadenaVacia(facItem.getDescripcion())) {
			FacFacturacionprogramadaExample descUnica = new FacFacturacionprogramadaExample();
			descUnica.createCriteria().andIdseriefacturacionEqualTo(string2Long(facItem.getIdSerieFacturacion()))
					.andIdinstitucionEqualTo(usuario.getIdinstitucion())
					.andDescripcionEqualTo(facItem.getDescripcion().trim());
			long descCount = facFacturacionprogramadaExtendsMapper.countByExample(descUnica);

			if (descCount > 0) {
				fac.setDescripcion(facItem.getDescripcion().trim() + " [" + idProgramacion + "]");
			} else {
				fac.setDescripcion(facItem.getDescripcion().trim());
			}
		}

		// Tarjeta de datos generales
		fac.setFechainicioproductos(facItem.getFechaInicioProductos());
		fac.setFechafinproductos(facItem.getFechaFinProductos());
		fac.setFechainicioservicios(facItem.getFechaInicioServicios());
		fac.setFechafinservicios(facItem.getFechaFinServicios());
		fac.setFechaprevistageneracion(facItem.getFechaPrevistaGeneracion());
		fac.setFechaprevistaconfirm(facItem.getFechaPrevistaConfirm());

//		Campos sin correspondencia en facItem
//		fac.setConfdeudor(facItem.get);
//		fac.setConfingresos(facItem.get);
//		fac.setCtaclientes(facItem.get);
//		fac.setCtaingresos(facItem.get);
//		fac.setFechacargo(facItem.get);
//		fac.setIdprevision(facItem.get);
//		fac.setIdtipoenvios(facItem.get);

		// Recuperamos la serie de facturación para copiar las columnas
		FacSeriefacturacionKey serieKey = new FacSeriefacturacionKey();
		serieKey.setIdinstitucion(usuario.getIdinstitucion());
		serieKey.setIdseriefacturacion(Long.parseLong(facItem.getIdSerieFacturacion()));

		FacSeriefacturacion seriefacturacion = facSeriefacturacionExtendsMapper.selectByPrimaryKey(serieKey);

		// Copiar columnas de serie de facturación
		fac.setConfdeudor(seriefacturacion.getConfdeudor());
		fac.setConfingresos(seriefacturacion.getConfingresos());
		fac.setCtaclientes(seriefacturacion.getCtaclientes());
		fac.setCtaingresos(seriefacturacion.getCtaingresos());

		// Datos de la tarjeta generación de ficheros
		fac.setGenerapdf(seriefacturacion.getGenerarpdf());
		fac.setIdmodelofactura(seriefacturacion.getIdmodelofactura());
		fac.setIdmodelorectificativa(seriefacturacion.getIdmodelorectificativa());

		if (!UtilidadesString.esCadenaVacia(seriefacturacion.getGenerarpdf())
				&& seriefacturacion.getGenerarpdf().equals("1")) {
			fac.setIdestadopdf(Short.parseShort("7")); // Pendiente
		} else {
			fac.setIdestadopdf(Short.parseShort("5")); // No aplica
		}

		// Datos de la tarjeta envío facturas
		fac.setEnvio(seriefacturacion.getEnviofacturas());
		fac.setIdtipoplantillamail(seriefacturacion.getIdtipoplantillamail());
		fac.setIdtipoenvios(seriefacturacion.getIdtipoenvios());
		if (!UtilidadesString.esCadenaVacia(seriefacturacion.getEnviofacturas())
				&& seriefacturacion.getEnviofacturas().equals("1")) {
			fac.setIdestadoenvio(Short.parseShort("13")); // Pendiente
		} else {
			fac.setIdestadoenvio(Short.parseShort("11")); // No aplica
		}

		// Datos de la tarjeta generación de traspasos
		fac.setTraspasofacturas(seriefacturacion.getTraspasofacturas());
		fac.setTraspasoPlantilla(seriefacturacion.getTraspasoPlantilla());
		fac.setTraspasoCodauditoriaDef(seriefacturacion.getTraspasoCodauditoriaDef());
		if (!UtilidadesString.esCadenaVacia(seriefacturacion.getTraspasofacturas())
				&& seriefacturacion.getTraspasofacturas().equals("1")) {
			fac.setIdestadotraspaso(Short.parseShort("24")); // Pendiente
		} else {
			fac.setIdestadotraspaso(Short.parseShort("22")); // No aplica
		}

		fac.setArchivarfact(boolToString10(false)); // Desarchivada por defecto
		fac.setIdestadoconfirmacion(Short.parseShort("18")); // Generación programada
		fac.setVisible("S");

		return fac;
	}

	private String getProperty(String parametro) {
		GenPropertiesKey keyProperties = new GenPropertiesKey();
		keyProperties.setFichero("SIGA");
		keyProperties.setParametro(parametro);
		GenProperties property = genPropertiesMapper.selectByPrimaryKey(keyProperties);
		return property != null ? property.getValor() : "";
	}

	private Short string2Short(String val) {
		return val != null ? Short.valueOf(val) : null;
	}

	private Long string2Long(String val) {
		return val != null ? Long.valueOf(val) : null;
	}

	private Integer string2Integer(String val) {
		return val != null ? Integer.valueOf(val) : null;
	}

	private String boolToString10(Boolean b) {
		return b ? "1" : "0";
	}

	@Override
	public FacRegistroFichContaDTO search(FacRegistroFichConta facRegistroFichConta, HttpServletRequest request)
			throws Exception {

		LOGGER.info("Entrada Metodo: search()");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FacRegistroFichContaDTO facRegistroFichContaDTO = new FacRegistroFichContaDTO();
		List<FacRegistroFichConta> listaFacRegistroFichConta = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("getInformeFacturacion() -> Entrada al servicio para recuperar el informe de facturacion");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);


		GenParametrosExample genParametrosExample = new GenParametrosExample();
		genParametrosExample.createCriteria().andModuloEqualTo("FAC").andParametroEqualTo("TAM_MAX_CONSULTA_FAC")
				.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
		genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

		if (idInstitucion != null) {
			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
			if (tamMax != null && !tamMax.isEmpty()) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = 200;
			}
			LOGGER.info("Filtro: search()- Item:" + facRegistroFichConta.toString());
			listaFacRegistroFichConta = facRegistroFichContaExtendsMapper.search(facRegistroFichConta, idInstitucion,
					tamMaximo,usuario.getIdlenguaje());
			if (listaFacRegistroFichConta != null) {
				facRegistroFichContaDTO.setFacRegistroFichConta(listaFacRegistroFichConta);
			}
		}
		LOGGER.info("Salida Metodo: search()");
		return facRegistroFichContaDTO;
	}

	@Override
	public FacRegistroFichContaDTO maxIdContabilidad(HttpServletRequest request) throws Exception {

		LOGGER.info("Entrada Metodo: search()");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FacRegistroFichContaDTO facRegistroFichContaDTO = new FacRegistroFichContaDTO();
		List<FacRegistroFichConta> listaFacRegistroFichConta = new ArrayList<FacRegistroFichConta>();
		FacRegistroFichConta facRegistroFichConta = null;

		if (idInstitucion != null) {

			facRegistroFichConta = facRegistroFichContaExtendsMapper.getMaxIdFacRegistroFichConta(idInstitucion);

			if (facRegistroFichConta != null) {
				listaFacRegistroFichConta.add(facRegistroFichConta);
				facRegistroFichContaDTO.setFacRegistroFichConta(listaFacRegistroFichConta);
			}
		}
		LOGGER.info("Salida Metodo: search()");
		return facRegistroFichContaDTO;
	}

	@Override
	public UpdateResponseDTO guardarRegistroFichConta(FacRegistroFichConta facRegistroFichConta,
			HttpServletRequest request) {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			FacRegistrofichconta beanRegistro = new FacRegistrofichconta();
			beanRegistro.setIdcontabilidad(Long.valueOf(facRegistroFichConta.getIdContabilidad()));
			beanRegistro.setFechacreacion(facRegistroFichConta.getFechaCreacion());
			beanRegistro.setNombrefichero(facRegistroFichConta.getNombreFichero());
			beanRegistro.setFechadesde(facRegistroFichConta.getFechaExportacionDesde());
			beanRegistro.setFechahasta(facRegistroFichConta.getFechaExportacionHasta());
			beanRegistro.setFechamodificacion(new Date());
			beanRegistro.setEstado(new Short("1"));

			int resultado = facRegistroFichContaExtendsMapper.insert(beanRegistro);

			if (resultado == 1) {
				updateResponseDTO.setStatus(SigaConstants.CODE_200.toString());
				updateResponseDTO.setId(beanRegistro.getIdcontabilidad().toString());
			} else {
				updateResponseDTO.setStatus(SigaConstants.CODE_400.toString());
				updateResponseDTO.setId(beanRegistro.getIdcontabilidad().toString());
			}

		}

		return updateResponseDTO;
	}

	@Override
	public InformeFacturacionDTO getInformeFacturacion(String idSerieFacturacion, String idProgramacion, HttpServletRequest request) throws Exception {
		InformeFacturacionDTO informeFacturacionDTO = new InformeFacturacionDTO();
		List<InformeFacturacionItem> items;
		Error error = new Error();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("getInformeFacturacion() -> Entrada al servicio para recuperar el informe de facturacion");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info(
					"getInformeFacturacion() / facFacturaExtendsMapper.getInformeFacturacion() -> Entrada a facFacturacionprogramadaExtendsMapper para recuperar el informe de facturacion");

			// Logica
			items = facFacturaExtendsMapper.getInformeFacturacionOriginal(idSerieFacturacion, idProgramacion, usuario.getIdinstitucion().toString(), usuario.getIdlenguaje());
			items.addAll(facFacturaExtendsMapper.getInformeFacturacionActual(idSerieFacturacion, idProgramacion, usuario.getIdinstitucion().toString(), usuario.getIdlenguaje()));
			informeFacturacionDTO.setInformeFacturacion(items);

		}

		informeFacturacionDTO.setError(error);

		LOGGER.info(
				"getFacturacionesProgramadas() -> Salida del servicio para recuperar el informe de facturacion");

		return informeFacturacionDTO;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarFicheroAdeudos(List<FicherosAdeudosItem> ficheroAdeudosItems, HttpServletRequest request) throws Exception {
		ResponseEntity<InputStreamResource> res = null;

		String directorioFisico = "facturacion.directorioFisicoPagosBancosJava";
		String directorio = "facturacion.directorioPagosBancosJava";

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("descargarFicheroAdeudos() -> Entrada al servicio para descargar ficheros de adeudos");

		String pathFichero = getProperty(directorioFisico) + getProperty(directorio)
				+ File.separator + usuario.getIdinstitucion();

		List<File> listaFicheros = ficheroAdeudosItems.stream().flatMap(item -> {
			FacDisquetecargosKey key = new FacDisquetecargosKey();
			key.setIdinstitucion(usuario.getIdinstitucion());
			key.setIddisquetecargos(Long.parseLong(item.getIdDisqueteCargos()));
			FacDisquetecargos disquetecargos = facDisquetecargosExtendsMapper.selectByPrimaryKey(key);

			List<File> files = new ArrayList<>();
			if (Objects.nonNull(disquetecargos)) {
				String nombreFichero = disquetecargos.getNombrefichero();
				File directorioFicheros = new File(pathFichero);

				// Se buscan todos los ficheros que coincidan con el nombre del fichero
				if (directorioFicheros.exists() && Objects.nonNull(disquetecargos.getNombrefichero())) {
					File[] ficheros = directorioFicheros.listFiles();
					String nombreFicheroListadoSinExtension, nombreFicheroGeneradoSinExtension;
					for (File file: ficheros){
						nombreFicheroListadoSinExtension = (file.getName().indexOf(".") > 0)
								? file.getName().substring(0, file.getName().indexOf(".")) : file.getName();
						nombreFicheroGeneradoSinExtension = (nombreFichero.indexOf(".") > 0)
								? nombreFichero.substring(0, nombreFichero.indexOf(".")) : nombreFichero;
						if(nombreFicheroGeneradoSinExtension.equalsIgnoreCase(nombreFicheroListadoSinExtension)){
							files.add(file);
						}
					}
				}
			}

			return files.stream();
		}).filter(Objects::nonNull).collect(Collectors.toList());

		// Construcción de la respuesta para uno o más archivos
		res = SIGAServicesHelper.descargarFicheros(listaFicheros,
				MediaType.parseMediaType("application/octet-stream"),
				MediaType.parseMediaType("application/zip"), "LOG_FICHERO_ADEUDOS");

		LOGGER.info("descargarFicheroAdeudos() -> Salida del servicio para descargar ficheros de adeudos");

		return res;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarFicheroTransferencias(List<FicherosAbonosItem> ficheroAbonosItems, HttpServletRequest request) throws Exception {
		ResponseEntity<InputStreamResource> res = null;

		String directorioFisico = "facturacion.directorioFisicoAbonosBancosJava";
		String directorio = "facturacion.directorioAbonosBancosJava";

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("descargarFicheroTransferencias() -> Entrada al servicio para descargar ficheros de transferencias");

		String pathFichero = getProperty(directorioFisico) + getProperty(directorio)
				+ File.separator + usuario.getIdinstitucion();

		List<File> listaFicheros = ficheroAbonosItems.stream().flatMap(item -> {
			FacDisqueteabonosKey key = new FacDisqueteabonosKey();
			key.setIdinstitucion(usuario.getIdinstitucion());
			key.setIddisqueteabono(string2Long(item.getIdDisqueteAbono()));
			FacDisqueteabonos disqueteabonos = facDisqueteabonosExtendsMapper.selectByPrimaryKey(key);

			List<File> files = new ArrayList<>();
			if (Objects.nonNull(disqueteabonos)) {
				String nombreFichero = disqueteabonos.getNombrefichero();
				File directorioFicheros = new File(pathFichero);

				// Se buscan todos los ficheros que coincidan con el nombre del fichero
				if (directorioFicheros.exists() && Objects.nonNull(disqueteabonos.getNombrefichero())) {
					File[] ficheros = directorioFicheros.listFiles();
					String nombreFicheroListadoSinExtension, nombreFicheroGeneradoSinExtension;
					for (File file: ficheros){
						nombreFicheroListadoSinExtension = (file.getName().indexOf(".") > 0)
								? file.getName().substring(0, file.getName().indexOf(".")) : file.getName();
						nombreFicheroGeneradoSinExtension = (nombreFichero.indexOf(".") > 0)
								? nombreFichero.substring(0, nombreFichero.indexOf(".")) : nombreFichero;
						if(nombreFicheroGeneradoSinExtension.equalsIgnoreCase(nombreFicheroListadoSinExtension)){
							files.add(file);
						}
					}
				}
			}

			return files.stream();
		}).filter(Objects::nonNull).collect(Collectors.toList());

		// Construcción de la respuesta para uno o más archivos
		res = SIGAServicesHelper.descargarFicheros(listaFicheros,
				MediaType.parseMediaType("application/octet-stream"),
				MediaType.parseMediaType("application/zip"), "LOG_FICHERO_TRANSFERENCIAS");

		LOGGER.info("descargarFicheroTransferencias() -> Salida del servicio para descargar ficheros de transferencias");

		return res;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarFicheroDevoluciones(List<FicherosDevolucionesItem> ficheroDevolucionesItems, HttpServletRequest request) throws Exception {
		ResponseEntity<InputStreamResource> res = null;

		String directorioFisico = "facturacion.directorioFisicoDevolucionesJava";
		String directorio = "facturacion.directorioDevolucionesJava";

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("descargarFicheroDevoluciones() -> Entrada al servicio para descargar ficheros de devoluciones");

		String pathFichero = getProperty(directorioFisico) + getProperty(directorio)
				+ File.separator + usuario.getIdinstitucion();

		List<File> listaFicheros = ficheroDevolucionesItems.stream().map(item -> {
			FacDisquetedevolucionesKey key = new FacDisquetedevolucionesKey();
			key.setIdinstitucion(usuario.getIdinstitucion());
			key.setIddisquetedevoluciones(string2Long(item.getIdDisqueteDevoluciones()));
			FacDisquetedevoluciones disquetedevoluciones = facDisquetedevolucionesExtendsMapper.selectByPrimaryKey(key);

			List<File> files = new ArrayList<>();
			File file = null;
			if (Objects.nonNull(disquetedevoluciones) && Objects.nonNull(disquetedevoluciones.getNombrefichero())) {
				String nombreFichero = pathFichero + File.separator + disquetedevoluciones.getNombrefichero();
				file = new File(nombreFichero);
			}

			return file;
		}).filter(Objects::nonNull).collect(Collectors.toList());

		// Construcción de la respuesta para uno o más archivos
		res = SIGAServicesHelper.descargarFicheros(listaFicheros,
				MediaType.parseMediaType("application/octet-stream"),
				MediaType.parseMediaType("application/zip"), "LOG_FICHERO_DEVOLUCIONES");

		LOGGER.info("descargarFicheroDevoluciones() -> Salida del servicio para descargar ficheros de devoluciones");

		return res;
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarFichaFacturacion(List<FacFacturacionprogramadaItem> facturacionItems, HttpServletRequest request)throws Exception {
		ResponseEntity<InputStreamResource> res = null;
		AdmUsuarios usuario = null;

		LOGGER.info("descargarFichaFacturacion() -> Entrada al servicio para recuperar el archivo de LOG de la facturación");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);
		Short idInstitucion = usuario.getIdinstitucion();

		String directorioFisico = "facturacion.directorioFisicoPrevisionesJava";
		String directorio = "facturacion.directorioPrevisionesJava";

		String pathFichero = getProperty(directorioFisico) + getProperty(directorio)
				+ File.separator + usuario.getIdinstitucion();

		// Lista de ficheros de facturaciones programadas
		List<File> listaFicheros = facturacionItems.stream().map(item -> {
			FacFacturacionprogramadaKey key = new FacFacturacionprogramadaKey();
			key.setIdinstitucion(idInstitucion);
			key.setIdseriefacturacion(string2Long(item.getIdSerieFacturacion()));
			key.setIdprogramacion(string2Long(item.getIdProgramacion()));
			FacFacturacionprogramada facturacion = facFacturacionprogramadaExtendsMapper.selectByPrimaryKey(key);

			File file = null;
			if (Objects.nonNull(facturacion) && Objects.nonNull(facturacion.getNombrefichero())) {
				String nombreFichero = pathFichero + File.separator + facturacion.getNombrefichero();
				file = new File(nombreFichero);
			}

			return file;
		}).filter(Objects::nonNull).collect(Collectors.toList());

		// Construcción de la respuesta para uno o más archivos
		res = SIGAServicesHelper.descargarFicheros(listaFicheros,
				MediaType.parseMediaType("application/vnd.ms-excel"),
				MediaType.parseMediaType("application/zip"), "LOG_FACTURACION");


		LOGGER.info("descargarFichaFacturacion() -> Salida del servicio para obtener el archivo de LOG de la facturación");

		return res;
	}

	private File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector)
			throws BusinessException {

		LOGGER.info("createExcelFile() -> Entrada al servicio que crea la plantilla Excel");

		if (orderList == null && datosVector == null)
			throw new BusinessException("No hay datos para crear el fichero");
		if (orderList == null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = ExcelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroEjemplo);

		LOGGER.info("createExcelFile() -> Salida al servicio que crea la plantilla Excel");

		return XLSFile;
	}
}
