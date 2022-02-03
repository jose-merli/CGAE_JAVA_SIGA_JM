package org.itcgae.siga.fac.services.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.itcgae.siga.DTO.fac.AbonoContabilidadItem;
import org.itcgae.siga.DTO.fac.AltaAnticipoItem;
import org.itcgae.siga.DTO.fac.AnticiposPySItem;
import org.itcgae.siga.DTO.fac.ComunicacionCobroDTO;
import org.itcgae.siga.DTO.fac.ComunicacionCobroItem;
import org.itcgae.siga.DTO.fac.ContadorSeriesDTO;
import org.itcgae.siga.DTO.fac.ContadorSeriesItem;
import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesDTO;
import org.itcgae.siga.DTO.fac.DestinatariosSeriesItem;
import org.itcgae.siga.DTO.fac.DevolucionesItem;
import org.itcgae.siga.DTO.fac.EstadosAbonosDTO;
import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
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
import org.itcgae.siga.DTO.fac.FacturasContabilidadItem;
import org.itcgae.siga.DTO.fac.FacturasIncluidasDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.DTO.fac.InformeFacturacionDTO;
import org.itcgae.siga.DTO.fac.InformeFacturacionItem;
import org.itcgae.siga.DTO.fac.LiquidacionAnticipoColegioItem;
import org.itcgae.siga.DTO.fac.PagoPorBancoAbonoItem;
import org.itcgae.siga.DTO.fac.PagoPorBancoItem;
import org.itcgae.siga.DTO.fac.PagoPorCajaItem;
import org.itcgae.siga.DTO.fac.PagoPorTarjetaItem;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.SeriesFacturacionDTO;
import org.itcgae.siga.DTO.fac.TarjetaPickListSerieDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosDTO;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.com.ConsultaDestinatarioItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.FinalidadConsultaDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.FicherosService;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.cen.services.impl.CargasMasivasGFServiceImpl;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesNumeros;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
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
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacAbonoincluidoendisquete;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionExample;
import org.itcgae.siga.db.entities.FacBancoinstitucionKey;
import org.itcgae.siga.db.entities.FacClienincluidoenseriefactur;
import org.itcgae.siga.db.entities.FacClienincluidoenseriefacturExample;
import org.itcgae.siga.db.entities.FacClienincluidoenseriefacturKey;
import org.itcgae.siga.db.entities.FacDisqueteabonos;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaDevolucion;
import org.itcgae.siga.db.entities.FacFacturaKey;
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
import org.itcgae.siga.db.entities.FacHistoricofacturaKey;
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
import org.itcgae.siga.db.entities.FcsPagosEstadospagos;
import org.itcgae.siga.db.entities.FcsPagosEstadospagosExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModClasecomunicacionesExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.entities.PysAnticipoletrado;
import org.itcgae.siga.db.entities.PysLineaanticipo;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
import org.itcgae.siga.db.mappers.CenBancosMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.EnvComunicacionmorososMapper;
import org.itcgae.siga.db.mappers.FacBancoinstitucionMapper;
import org.itcgae.siga.db.mappers.FacClienincluidoenseriefacturMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacHistoricofacturaMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.db.mappers.FacRenegociacionMapper;
import org.itcgae.siga.db.mappers.FcsPagosEstadospagosMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.mappers.ModClasecomunicacionesMapper;
import org.itcgae.siga.db.mappers.PysAnticipoletradoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
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
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionBancoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacSeriefacturacionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTipocliincluidoenseriefacExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTiposproduincluenfactuExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacTiposservinclsenfactExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoincluidoendisqueteExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPropositosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.WSCommons;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
	private AdmContadorMapper admContadorMapper;

	@Autowired
	private FacSeriefacturacionExtendsMapper facSeriefacturacionExtendsMapper;

	@Autowired
	private FacTiposservinclsenfactExtendsMapper facTiposservinclsenfactExtendsMapper;

	@Autowired
	private FacTiposproduincluenfactuExtendsMapper facTiposproduincluenfactuExtendsMapper;

	@Autowired
	private FacSeriefacturacionBancoExtendsMapper facSeriefacturacionBancoExtendsMapper;

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
	private FacPagosporcajaExtendsMapper facPagosPorCajaMapper;
	
	@Autowired
	private FacHistoricofacturaMapper facHistoricofacturaMapper;

	@Autowired
	private FacPagoabonoefectivoExtendsMapper facPagoabonoefectivoExtendsMapper;

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

	@Autowired
	private FacPropositosExtendsMapper facPropositosExtendsMapper;

	@Autowired
	private GenDiccionarioMapper genDiccionarioMapper;

	@Autowired
	private FcsPagosEstadospagosMapper fcsPagosEstadospagosMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private FacBancoinstitucionMapper facBancoInstitucionMapper;
	
	@Autowired
	private FacFacturaincluidaendisqueteMapper facFacturaIncluidaEnDisqueteMapper;
	
	@Autowired
	private FacLineadevoludisqbancoMapper facLineaDevoluDisqBancoMapper;
	
	@Autowired
	private PysAnticipoletradoMapper pysAnticipoLetradoMapper;
	
	@Autowired
	private CargasMasivasGFServiceImpl cargasMasivasGFServiceImpl;
	
	@Autowired
	private IFicherosService ficherosService;

	private static final int EXCEL_ROW_FLUSH = 1000;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
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
				List<CuentasBancariasItem> cuentaResults = facBancoinstitucionExtendsMapper.getCuentasBancarias(cuenta.getBancosCodigo(),
						usuario.getIdinstitucion());

				if (cuentaResults != null && !cuentaResults.isEmpty()) {
					// No se puede borrar una cuenta bancaria que esta siendo utilizada
					boolean enUso = Integer.parseInt(cuentaResults.get(0).getNumUsos()) > 0;
					if (enUso) {
						throw new Exception("facturacionPyS.cuentasBancarias.eliminar.errorUsos");
					}

					FacBancoinstitucionKey cuentasbancariasKey = new FacBancoinstitucionKey();
					cuentasbancariasKey.setIdinstitucion(usuario.getIdinstitucion());
					cuentasbancariasKey.setBancosCodigo(cuenta.getBancosCodigo());

					// Borrado físico si numFicheros es 0
					if (Integer.parseInt(cuenta.getNumFicheros()) < 1) {
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

				facSeriefacturacionBancoExtendsMapper.updateByExampleSelective(record, serieBancoExample);
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

				FacFacturacionprogramadaExample facturaExample = new FacFacturacionprogramadaExample();
				facturaExample.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion())
						.andIdseriefacturacionEqualTo(Long.valueOf(serieFacturacion.getIdSerieFacturacion()));

				FacSeriefacturacionKey seriefacturacionKey = new FacSeriefacturacionKey();
				seriefacturacionKey.setIdinstitucion(usuario.getIdinstitucion());
				seriefacturacionKey.setIdseriefacturacion(idSerieFacturacion);

				FacSeriefacturacion sfToUpdate = facSeriefacturacionExtendsMapper
						.selectByPrimaryKey(seriefacturacionKey);
				if (null != sfToUpdate) {
					if (sfToUpdate.getFechabaja() == null) {
						long numFacturas = facFacturacionprogramadaExtendsMapper.countByExample(facturaExample);
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

							facSeriefacturacionBancoExtendsMapper.deleteByPrimaryKey(seriefacturacionBancoKey);
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
			List<FacSeriefacturacionBanco> serieBancoItems = facSeriefacturacionBancoExtendsMapper
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
				facSeriefacturacionBancoExtendsMapper.updateByExample(serieBancoToUpdate, bancoExample);
			} else {
				facSeriefacturacionBancoExtendsMapper.insert(serieBancoToUpdate);
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
					if(admContador.getContador() == 0) {
						item.setContador("1");
					}else {
						item.setContador(String.valueOf(admContador.getContador()));
					}
					//item.setContador(String.valueOf(admContador.getContador()));

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
					//	item.setContador(String.valueOf(admContador.getContador()));
					if(admContador.getContador() == 0) {
						item.setContador("1");
					}else {
						item.setContador(String.valueOf(admContador.getContador()));
					}
					

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

	private int prepararFicheroTransferencias(String fcs, Short idInstitucion,
											  String bancosCodigo,
											  Short idSufijo,
											  List<FacAbono> abonosBanco,
											  String idPropositoSEPA,
											  String idPropositoOtros,
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

		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());

		// Propositos
		record.setIdpropsepa(Short.parseShort(idPropositoSEPA));
		record.setIdpropotros(Short.parseShort(idPropositoOtros));

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

			recordAbono.setFechamodificacion(new Date());
			recordAbono.setUsumodificacion(usuario.getIdusuario());
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
		param_in[2] = record.getIdpropsepa();
		param_in[3] = record.getIdpropotros();
		param_in[4] = rutaServidor;
		param_in[5] = nombreFichero;
		param_in[6] = Integer.parseInt(usuario.getIdlenguaje());
        
		String[] resultado = commons.callPLProcedureFacturacionPyS(
				"{call PKG_SIGA_ABONOS.Generarficherotransferencias(?,?,?,?,?,?,?,?,?)}", 2, param_in);

		if (resultado[0] != null && resultado.length > 1 && string2Integer(resultado[0]) != 0) {
			throw new BusinessException(resultado[1]);
		}

		return 1;
	}

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
				throw new BusinessException(resultado[2]);
			} else {
				if (!resultado[1].equals("0")) {
					throw new BusinessException("general.mensaje.error.bbdd");
				}
			}
			insertResponseDTO.setId(resultado[0]);
		}

		LOGGER.info("nuevoFicheroAdeudos() -> Salida del servicio para crear un fichero de adeudos");

		return insertResponseDTO;
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
	public FacturaDTO getFactura(String idFactura, String idAbono, String tipo, HttpServletRequest request) throws Exception {
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
				List<FacturaItem> items = facAbonoExtendsMapper.getAbono(idAbono,
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
				key.setIdabono(Long.valueOf(item.getIdAbono()));
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
			facHistoricoInsert.setUsumodificacion(usuario.getIdusuario());
			facHistoricoInsert.setFechamodificacion(item.getFechaModificaion());

			// abono (si tiene)
			if (facHistoricoInsert.getIdabono() != null) {
				FacAbonoKey abonoKey = new FacAbonoKey();
				abonoKey.setIdinstitucion(usuario.getIdinstitucion());
				abonoKey.setIdabono(facHistoricoInsert.getIdabono());
				FacAbono abonoUpdate = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

				// renegociar
				if (item.getIdAccion().equalsIgnoreCase("7")) {

					renegociarAbono(item, facHistoricoInsert, abonoUpdate, usuario);
				}

				// nuevo abono
				/**
				 * Id accion abono por caja no existe
				 */
				if (item.getIdAccion().equalsIgnoreCase("4") && abonoUpdate.getEstado() == 6) {

					nuevoAbono(item, facHistoricoInsert, abonoUpdate, usuario);
				}
			}

			else {

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
		renegociacionInsert.setUsumodificacion(usuario.getIdusuario());
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
		facUpdate.setUsumodificacion(usuario.getIdusuario());

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

		if (abonoUpdate.getEstado() == 6) {
			facHistoricoInsert.setIdformapago((short) 30);
			facHistoricoInsert.setEstado((short) 2);
		} else if (abonoUpdate.getEstado() == 5) {
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
		renegociacionInsert.setUsumodificacion(usuario.getIdusuario());
		renegociacionInsert.setFecharenegociacion(new Date());
		renegociacionInsert.setFechamodificacion(new Date());
		renegociacionInsert.setIdcuenta(facHistoricoInsert.getIdcuenta());
		renegociacionInsert.setImporte(facHistoricoInsert.getImptotalporpagar());
		renegociacionInsert.setIdpersona(facHistoricoInsert.getIdpersona());
		renegociacionInsert.setIdrenegociacion(facHistoricoInsert.getIdrenegociacion());

		// abono
		abonoUpdate.setIdpersona(facHistoricoInsert.getIdpersona());
		abonoUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		abonoUpdate.setIdpersonadeudor(facHistoricoInsert.getIdpersonadeudor());
		abonoUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());
		abonoUpdate.setFechamodificacion(new Date());
		abonoUpdate.setUsumodificacion(usuario.getIdusuario());

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

		List<FacPagoabonoefectivo> listPagos = facPagoabonoefectivoExtendsMapper.selectByExample(exampleAbonos);
		if (!listPagos.isEmpty())
			abonoCajaInsert.setIdpagoabono((listPagos.get(listPagos.size() - 1).getIdpagoabono() + 1));
		else
			abonoCajaInsert.setIdpagoabono((long) 1);

		abonoCajaInsert.setIdinstitucion(usuario.getIdinstitucion());
		abonoCajaInsert.setIdabono(facHistoricoInsert.getIdabono());

		abonoCajaInsert.setImporte(BigDecimal.valueOf(Double.parseDouble(item.getImpTotalPagado())));
		abonoCajaInsert.setFechamodificacion(new Date());
		abonoCajaInsert.setFecha(item.getFechaModificaion());
		abonoCajaInsert.setUsumodificacion(usuario.getIdusuario());
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
		abonoUpdate.setUsumodificacion(usuario.getIdusuario());
		abonoUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		abonoUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());

		if (abonoUpdate.getImppendienteporabonar().compareTo(BigDecimal.valueOf(0)) > 0)
			abonoUpdate.setEstado((short) 6);
		else
			abonoUpdate.setEstado((short) 1);

		facAbonoExtendsMapper.updateByPrimaryKey(abonoUpdate);
		facPagoabonoefectivoExtendsMapper.insert(abonoCajaInsert);
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

		List<FacPagosporcaja> listPagos = facPagosPorCajaMapper.selectByExample(examplePagos);
		if (!listPagos.isEmpty())
			facHistoricoInsert.setIdpagoporcaja((short) (listPagos.get(listPagos.size() - 1).getIdpagoporcaja() + 1));
		else
			facHistoricoInsert.setIdpagoporcaja((short) 1);

		// pagos caja
		FacPagosporcaja pagosCajaInsert = new FacPagosporcaja();

		pagosCajaInsert.setIdinstitucion(usuario.getIdinstitucion());
		pagosCajaInsert.setIdfactura(item.getIdFactura());
		pagosCajaInsert.setIdpagoporcaja(facHistoricoInsert.getIdpagoporcaja());
		pagosCajaInsert.setUsumodificacion(usuario.getIdusuario());

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
		facUpdate.setUsumodificacion(usuario.getIdusuario());
		facUpdate.setEstado(facHistoricoInsert.getEstado());
		facUpdate.setIdformapago(facHistoricoInsert.getIdformapago());
		facUpdate.setIdcuenta(facHistoricoInsert.getIdcuenta());
		facUpdate.setIdcuentadeudor(facHistoricoInsert.getIdcuentadeudor());

		// saves
		facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
		facPagosPorCajaMapper.insert(pagosCajaInsert);
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
		devolucion.setUsuModificacion(usuario.getIdusuario());

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
		String fecha = dateFormat.format(item.getFechaModificaion());

		devolucion.setFechaDevolucion(fecha);

		String listaFacturas = facHistoricoInsert.getIddisquetecargos() + "||"
				+ facHistoricoInsert.getIdfacturaincluidaendisquete() + "||" + facHistoricoInsert.getIdfactura() + "||"
				+ facFacturaincluidaendisquete.getIdrecibo() + "||" + item.getComentario();

		devolucion.setListaFacturas(listaFacturas);

		facHistoricoInsert.setIdrecibo("00" + facHistoricoInsert.getIdrecibo());


		//Factura Original
		facUpdate.setEstado((short) 4);
		facUpdate.setImptotalpagado(facHistoricoInsert.getImptotalpagado());
		facUpdate.setImptotalporpagar(facHistoricoInsert.getImptotalporpagar());
		facUpdate.setImptotalpagadoporbanco(facHistoricoInsert.getImptotalpagadoporbanco());
		facUpdate.setFechamodificacion(new Date());
		facUpdate.setUsumodificacion(usuario.getIdusuario());


		//Obtener ID Disquete Devoluciones
		String resultado[] = null;

		Object[] param_in = new Object[5]; // Parametros de entrada del PL

		param_in[0] = devolucion.getIdInstitucion();
		param_in[1] = devolucion.getListaFacturas();
		param_in[2] = devolucion.getFechaDevolucion();
		param_in[3] = Integer.parseInt(devolucion.getIdIdioma());
		param_in[4] = devolucion.getUsuModificacion();

		resultado = commons.callPLProcedureFacturacionPyS(
				"{call PKG_SIGA_CARGOS.DevolucionesManuales(?,?,?,?,?,?,?,?)}", 3, param_in);

		if (resultado[0].equals("0")) {

			//ponemos el disquete como devuelto
			facFacturaincluidaendisquete.setDevuelta("S");
			facFacturaincluidaendisqueteMapper.updateByPrimaryKey(facFacturaincluidaendisquete);
			
			//cogemos los nuevos valores del facHistorico que inserta el paquete de bd
			FacHistoricofacturaKey historicoKey = new FacHistoricofacturaKey();
			
			historicoKey.setIdfactura(facHistoricoInsert.getIdfactura());
			historicoKey.setIdhistorico(facHistoricoInsert.getIdhistorico());
			historicoKey.setIdinstitucion(facHistoricoInsert.getIdinstitucion());
			
			//actualizamos el historico con los datos del paquete
			facHistoricoInsert=facHistoricofacturaMapper.selectByPrimaryKey(historicoKey);

			FacLineadevoludisqbancoKey facLineadevoludisqbancoKey = new FacLineadevoludisqbancoKey();
			facLineadevoludisqbancoKey.setIddisquetedevoluciones(facHistoricoInsert.getIddisquetedevoluciones());
			facLineadevoludisqbancoKey.setIdinstitucion(facHistoricoInsert.getIdinstitucion());
			facLineadevoludisqbancoKey.setIdrecibo(facHistoricoInsert.getIdrecibo());
			FacLineadevoludisqbanco lienaDevolucion = facLineadevoludisqbancoMapper.selectByPrimaryKey(facLineadevoludisqbancoKey);

			//Con Comision
			if(item.getComision() != null && item.getComision() && cliente.getComisiones().equals("1") && banco.getComisionimporte() != BigDecimal.valueOf(0)){

				//Copia Factura
				FacFactura facturaComision = new FacFactura();
				BeanUtils.copyProperties(facUpdate, facturaComision);
				
				facturaComision.setIdfactura(facFacturaExtendsMapper.getNewFacturaID(String.valueOf(facturaComision.getIdinstitucion())).get(0).getValue());

				//Historico Factura Anulada
				FacHistoricofactura fachistoricoAnulada = new FacHistoricofactura();
				
				fachistoricoAnulada.setIdfactura(facUpdate.getIdfactura());
				fachistoricoAnulada.setIdinstitucion(facUpdate.getIdinstitucion());

				fachistoricoAnulada.setIdtipoaccion((short) 9);
				fachistoricoAnulada.setEstado((short) 8);
				fachistoricoAnulada.setIdhistorico((short) (facHistoricoInsert.getIdhistorico()+1));

				fachistoricoAnulada.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagado(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalporpagar(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalanticipado(BigDecimal.valueOf(0));
				fachistoricoAnulada.setImptotalcompensado(facUpdate.getImptotal());
				fachistoricoAnulada.setIdformapago((short) 20); //FORMAPAGO= domiciliacion bancaria
				fachistoricoAnulada.setIdpersona(facHistoricoInsert.getIdpersona());

				//Anular Factura Original
				facUpdate.setImptotalpagadoporbanco(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagado(BigDecimal.valueOf(0));
				facUpdate.setImptotalporpagar(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagadosolocaja(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagadoporcaja(BigDecimal.valueOf(0));
				facUpdate.setImptotalpagadosolotarjeta(BigDecimal.valueOf(0));
				facUpdate.setImptotalcompensado(facUpdate.getImptotal());

				facUpdate.setEstado((short) 8);
				
//				facFacturaExtendsMapper.updateByPrimaryKey(facUpdate); --se pone al final del proceso

				//Factura Comision
				facturaComision.setFechaemision(item.getFechaModificaion());
				facturaComision.setEstado((short) 4);

				facturaComision.setComisionidfactura(facHistoricoInsert.getIdfactura());
				facturaComision.setNumerofactura(facFacturaExtendsMapper.getNuevoNumeroFactura(facturaComision.getIdinstitucion().toString(), facturaComision.getIdseriefacturacion().toString()).get(0).getValue());

				long IVAComision = Long.parseLong(facBancoinstitucionExtendsMapper.getPorcentajeIva(String.valueOf(facUpdate.getIdinstitucion()), banco.getBancosCodigo()).get(0).getValue());
				BigDecimal importeIVAComision = banco.getComisionimporte().multiply(BigDecimal.valueOf(IVAComision/100));

				facturaComision.setImptotalporpagar(facturaComision.getImptotalporpagar().add(importeIVAComision.add(banco.getComisionimporte())));
				facturaComision.setImptotal(facturaComision.getImptotal().add(importeIVAComision.add(banco.getComisionimporte())));
				facturaComision.setImptotaliva(facturaComision.getImptotaliva().add(importeIVAComision));
				facturaComision.setImptotalneto(facturaComision.getImptotalneto().add(banco.getComisionimporte()));

				facFacturaExtendsMapper.insert(facturaComision);

				//se actualiza el historico
				fachistoricoAnulada.setComisionidfactura(facturaComision.getIdfactura());
				fachistoricoAnulada.setFechamodificacion(new Date());
				fachistoricoAnulada.setUsumodificacion(usuario.getIdusuario());
				facHistoricofacturaExtendsMapper.insert(fachistoricoAnulada);

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
				fachistoricoRevision.setFechamodificacion(new Date());
				fachistoricoRevision.setUsumodificacion(usuario.getIdusuario());
				
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
				lineaComision.setUsumodificacion(usuario.getIdusuario());
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

			//actualizamos la factura y la linea de la devolucion
			facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
			facLineadevoludisqbancoMapper.updateByPrimaryKey(lienaDevolucion);

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
		abonoInsert.setUsumodificacion(usuario.getIdusuario());
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

			List<FacPagosporcaja> listPagos = facPagosPorCajaMapper.selectByExample(examplePagos);

			FacPagosporcaja pagosCajaDelete = listPagos.get(listPagos.size() - 1);

			// factura
			facUpdate.setImptotalpagado(facUpdate.getImptotalpagado().add(facHistorico.getImptotalpagado()));
			facUpdate.setImptotalpagadoporcaja(
					facUpdate.getImptotalpagadoporcaja().add(facHistorico.getImptotalpagado()));
			facUpdate.setImptotalpagadosolocaja(
					facUpdate.getImptotalpagadosolocaja().add(facHistorico.getImptotalpagado()));
			facUpdate.setImptotalporpagar(facUpdate.getImptotalporpagar().subtract(facHistorico.getImptotalpagado()));
			facUpdate.setFechamodificacion(new Date());
			facUpdate.setUsumodificacion(usuario.getIdusuario());
			facUpdate.setEstado(facHistorico.getEstado());
			facUpdate.setIdformapago(facHistorico.getIdformapago());
			facUpdate.setIdcuenta(facHistorico.getIdcuenta());
			facUpdate.setIdcuentadeudor(facHistorico.getIdcuentadeudor());

			// saves
			facFacturaExtendsMapper.updateByPrimaryKey(facUpdate);
			facHistoricofacturaExtendsMapper.deleteByPrimaryKey(facHistoricoList.get(facHistoricoList.size() - 1));
			facPagosPorCajaMapper.deleteByPrimaryKey(pagosCajaDelete);

			deleteResponseDTO.setStatus(HttpStatus.OK.toString());
		}

		LOGGER.info("insertarEstadosPagos() -> Salida del servicio para crear una entrada al historico de factura");

		return deleteResponseDTO;
	}

	private void subirFicheroDisquete(InputStream ficheroOriginal, String rutaServidor, String nombreFichero) {
		LOGGER.info("subirFicheroDisquete() -> Entrada al servicio para subir el fichero de devoluciones");

		String rutaFichero = rutaServidor + File.separator + nombreFichero;
		InputStream stream =null;
		BufferedReader rdr = null;
		BufferedWriter out = null;

		try {
			stream = ficheroOriginal;
			new File(rutaServidor).mkdirs();

			rdr = new BufferedReader(new InputStreamReader(stream));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaFichero),"ISO-8859-1"));

			boolean esXML = false;
			boolean controlarDocument = true;

			String nombreFicheroDevoluciones = nombreFichero;
			if (nombreFicheroDevoluciones.toUpperCase().endsWith("XML")) {
				esXML = true;
			}

			String linea = "";
			while (linea!=null && linea.trim().equals("")) {
				linea = rdr.readLine();
			}

			while (linea!=null) {

				String lineaFichero = linea;

				// Control que valida si es un fichero XML
				if (!esXML && linea.indexOf("<")>=0) {
					esXML = true;
				}

				// Control que realiza una serie de cambios cuando es XML
				if (esXML) {

					// Comienzo a buscar por la primera letra
					int buscador = 0;

					// Control de longitud de linea
					while (buscador < linea.length()) {

						// Busco < (principio de etiqueta)
						buscador = linea.indexOf("<", buscador);

						// Si no tiene etiqueta pinto la linea
						if (buscador < 0) {
							break;

						}

						// Si tiene < pasamos de letra
						buscador++;

						// Comprueba que tenga por lo menos alguna letra mas
						if (linea.length() < buscador) {
							break;
						}

						// Obtengo la siguiente letra al <
						char letra = linea.charAt(buscador);

						// Si no encuentra </ es que es una apertura de etiqueta
						if (letra != '/') {

							final String etiquetaDocument = "DOCUMENT";

							// Control de si hay que validar la etiqueta DOCUMENT
							if (controlarDocument && linea.length() > buscador + etiquetaDocument.length()) {

								// Obtengo el nombre de la etiqueta
								String buscaDocument = linea.substring(buscador, buscador + etiquetaDocument.length());

								// Compruebo si la etiqueta es DOCUMENT
								if (buscaDocument.equalsIgnoreCase(etiquetaDocument)) {

									// Hay que buscar el final de la etiqueta DOCUMENT
									int buscadorDocument = linea.indexOf(">", buscador + etiquetaDocument.length());

									// Encuento el final de la etiqueta DOCUMENT
									if (buscadorDocument > 0) {

										// Elimino los atributos de la etiqueta DOCUMENT
										linea = linea.substring(0, buscador + etiquetaDocument.length()) + linea.substring(buscadorDocument);

										// Indico que hay que buscar despues de la etiqueta DOCUMENT
										buscador += etiquetaDocument.length();

										// Indicamos que ya hemos controlado la etiqueta DOCUMENT
										controlarDocument = false;
									}
								}
							}

							// Pasamos a la siguiente letra
							buscador++;
							continue;
						}

						// Encuentro </ y buscamos el final de la etiqueta
						buscador = linea.indexOf(">", buscador);

						// Encuento el final de la etiqueta </...>
						if (buscador<0) {
							break;
						}

						// Pasamos a la siguiente letra >
						buscador++;

						// ponemos un retorno de linea al finalizar cada etiqueta final, porque asi evitamos un xml en una linea inmensa
						lineaFichero = linea.substring(0, buscador);

						// Escribimos la linea
						out.write(lineaFichero);
						out.write("\n");

						// Eliminamos los datos escritos
						linea = linea.substring(buscador);

						// Volvemos a empezar
						buscador = 0;
					}

					// Guardamos la linea tal como esta ahora
					lineaFichero = linea;
				} // FIN WHILE

				// Comprueba si queda algo por escribir de la linea
				if (!lineaFichero.trim().equals("")) {

					// Escribimos la linea
					out.write(lineaFichero);
					out.write("\n");
				}

				// Obtenemos la siguiente linea
				linea = "";
				while (linea!=null && linea.trim().equals("")) {
					linea = rdr.readLine();
				}
			}

			// close the stream
			stream.close();
			out.close();
			rdr.close();
		} catch (FileNotFoundException e) {
			throw new BusinessException("facturacion.nuevoFichero.literal.errorAcceso");
		} catch (IOException e) {
			throw new BusinessException("facturacion.nuevoFichero.literal.errorLectura");
		}

		LOGGER.info("subirFicheroDisquete() -> Saliendo del servicio para subir el fichero de devoluciones");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
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
			if (record.getGenerapdf().equals("1")) {
				record.setIdmodelofactura(string2Long(facItem.getIdModeloFactura()));
				record.setIdmodelorectificativa(string2Long(facItem.getIdModeloRectificativa()));
			} else {
				record.setIdmodelofactura(null);
				record.setIdmodelorectificativa(null);
			}

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

	private String getParametro(String modulo, String parametro, Short idInstitucion) {
		GenParametrosKey keyParametros = new GenParametrosKey();
		keyParametros.setModulo(modulo);
		keyParametros.setParametro(parametro);
		keyParametros.setIdinstitucion(idInstitucion);
		GenParametros property = genParametrosExtendsMapper.selectByPrimaryKey(keyParametros);
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
			HttpServletRequest request) throws Exception {

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmUsuarios usuario = new AdmUsuarios();
		
		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (idInstitucion != null && usuario != null) {

			FacRegistrofichconta beanRegistro = new FacRegistrofichconta();
			beanRegistro.setIdcontabilidad(Long.valueOf(facRegistroFichConta.getIdContabilidad()));
			beanRegistro.setIdinstitucion(idInstitucion);
			
			beanRegistro.setFechacreacion(facRegistroFichConta.getFechaCreacion());
			beanRegistro.setNombrefichero(facRegistroFichConta.getNombreFichero());
			beanRegistro.setFechadesde(facRegistroFichConta.getFechaExportacionDesde());
			beanRegistro.setFechahasta(facRegistroFichConta.getFechaExportacionHasta());
			beanRegistro.setFechamodificacion(new Date());
			beanRegistro.setUsumodificacion(usuario.getIdusuario());
			beanRegistro.setEstado(new Short("1"));//ESTADO PROGRAMADO
			
			beanRegistro.setNumeroasientos(0L);//PROVISIONAL

			int resultado = facRegistroFichContaExtendsMapper.insertSelective(beanRegistro);

			if (resultado == 1) {
				updateResponseDTO.setStatus(SigaConstants.CODE_200.toString());
				updateResponseDTO.setId(beanRegistro.getIdcontabilidad().toString());
				
				FacRegistrofichconta ficheroProgramado = facRegistroFichContaExtendsMapper.selectByPrimaryKey(beanRegistro);
				
				this.generarFicheroContabilidad(ficheroProgramado, usuario.getIdlenguaje(), String.valueOf(idInstitucion) ,String.valueOf(usuario.getIdusuario()));
			} else {
				updateResponseDTO.setStatus(SigaConstants.CODE_400.toString());
				updateResponseDTO.setId(beanRegistro.getIdcontabilidad().toString());
			}

		}

		return updateResponseDTO;
	}
	
	
	@Override
	public DeleteResponseDTO desactivarReactivarRegistroFichConta(List <FacRegistroFichConta> facRegistrosFichConta,
			HttpServletRequest request)
			throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		deleteResponseDTO.setError(error);
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("deleteResponseDTO() -> Entrada al servicio para desactivar/reactivar un fichero de exportacion de contabilidad");

		if (usuario != null && idInstitucion != null) {

			//Recorrer el array y establecer fechabaja a dia de hoy o a null dependiendo de si estaba activado o desactivado el registro
			for(FacRegistroFichConta facRegistroFichConta: facRegistrosFichConta) {
				
				FacRegistrofichconta pk = new FacRegistrofichconta();
				
				pk.setIdcontabilidad((long)facRegistroFichConta.getIdContabilidad());
				pk.setIdinstitucion(idInstitucion);
				
				FacRegistrofichconta beanUpdate = facRegistroFichContaExtendsMapper.selectByPrimaryKey(pk);
				
				if(facRegistroFichConta.getFechaBaja() == null) {
					beanUpdate.setFechabaja(new Date());
				}else {
					beanUpdate.setFechabaja(null);
				}
				
				int resultado = facRegistroFichContaExtendsMapper.updateByPrimaryKey(beanUpdate);
				
				if (resultado == 1) {
					deleteResponseDTO.setStatus(SigaConstants.CODE_200.toString());
					LOGGER.info("desactivarReactivarRegistroFichConta() -> Registro con id " + beanUpdate.getIdcontabilidad() + " activado/desactivado con exito.");
				} else {
					deleteResponseDTO.setStatus(SigaConstants.CODE_400.toString());
					LOGGER.info("desactivarReactivarRegistroFichConta() -> Registro con id " + beanUpdate.getIdcontabilidad() + " activado/desactivado sin exito.");
				}
				
			}

		}

		LOGGER.info("deleteResponseDTO() -> Salida del servicio para desactivar/reactivar un fichero de exportacion de contabilidad");

		return deleteResponseDTO;
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
	
	//INICIO METODOS FICHERO CONTABILIDAD
		private String CONTABILIDAD_IVA 		    = "";
		private String CONTABILIDAD_TARJETAS 	    = "";
		private String CONTABILIDAD_CAJA 		    = "";
		private String CONTAB_CAJA_ABONO		    = "";	
		private String CONTABILIDAD_COMPENSACION    = "";
		private String CONTABILIDAD_CAJA_ANTICIPOS	= "";
		private String CONTABILIDAD_INGRESOS_EXTRA	= "";
		private String ANTICIPOS_CLI 			    = "";
		private String CONTABILIDAD_GASTOSBANCARIOS	= "";
		
		private void crearCuentas(String idInstitucion) throws Exception
		{
			try	{
			
				CONTABILIDAD_IVA = this.getParametro("FAC", "CONTABILIDAD_IVA",Short.valueOf(idInstitucion));
				CONTABILIDAD_TARJETAS = this.getParametro("FAC", "CONTABILIDAD_TARJETAS",Short.valueOf(idInstitucion));
				CONTABILIDAD_CAJA = this.getParametro("FAC", "CONTABILIDAD_CAJA",Short.valueOf(idInstitucion));
				CONTABILIDAD_COMPENSACION = this.getParametro("FAC", "CONTABILIDAD_COMPENSACION",Short.valueOf(idInstitucion));
				CONTABILIDAD_CAJA_ANTICIPOS = this.getParametro("FAC", "CONTABILIDAD_CAJA_ANTICIPOS",Short.valueOf(idInstitucion));
				CONTABILIDAD_INGRESOS_EXTRA = this.getParametro("FAC", "CONTABILIDAD_INGRESOS_EXTRA",Short.valueOf(idInstitucion));
				ANTICIPOS_CLI = this.getParametro("FAC", "ANTICIPOS_CLI",Short.valueOf(idInstitucion));
				CONTABILIDAD_GASTOSBANCARIOS = this.getParametro("FAC", "CONTABILIDAD_GASTOSBANCARIOS",Short.valueOf(idInstitucion));
				CONTAB_CAJA_ABONO = this.getParametro("FAC", "CONTAB_CAJA_ABONO",Short.valueOf(idInstitucion));
			}
			catch(Exception e)
			{
				throw new Exception("Error creando cuentas",e);
			}
		}
		
//		private byte[] convertExcelFileToBytesArray(File fichero) {
//				InputStream fileStream = null;
//				ResponseEntity<InputStreamResource> res = null;
//				try {
//					fileStream = new FileInputStream(fichero);
//					HttpHeaders headers = new HttpHeaders();
//					headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
	//
//					headers.setContentLength(fichero.length());
//					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				return res;
//		}
	//	
		
		private boolean generarFicheroContabilidad(FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception	{

			LOGGER.info("generarFicheroContabilidad() -> Entrada al metodo que crea el fichero de contabilidad");
			
			boolean correcto = false;
		
			try {
				this.crearCuentas(idInstitucion);
			
				//1. Cambiar el estado del registro a ENPROCESO
				LOGGER.info("generarFicheroContabilidad() 1. Cambiar el estado del registro a ENPROCESO -> Entrada a "
						+ "facRegistroFichContaExtendsMapper para actualizar el estado a en proceso");
				registroFacRegistroFichConta.setEstado((short) 2);
				
				int respuestaCambioEstadoEnProceso = facRegistroFichContaExtendsMapper.updateByPrimaryKeySelective(registroFacRegistroFichConta);
				
				if(respuestaCambioEstadoEnProceso == 1) {
					LOGGER.info("generarFicheroContabilidad() 1. Cambiar el estado del registro a ENPROCESO -> Registro "
							+ "FacRegistrofichconta con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " e institucion: " 
							+ registroFacRegistroFichConta.getIdinstitucion() + " actualizado correctamente");
				}else {
					LOGGER.info("generarFicheroContabilidad() 1. Cambiar el estado del registro a ENPROCESO -> Registro "
							+ "FacRegistrofichconta con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " e institucion: " 
							+ registroFacRegistroFichConta.getIdinstitucion() + " no pudo ser actualizado");
					throw new Exception();
				}
				LOGGER.info("generarFicheroContabilidad() 1. Cambiar el estado del registro a ENPROCESO -> Salida de "
						+ "facRegistroFichContaExtendsMapper para actualizar el estado a en proceso");			
				
				//2. Crearse la lista con las columnas (multiidioma)
				List<String> camposContabilidad = Arrays.asList(UtilidadesString.getCampoMultidioma(SigaConstants.ASIENTO, idioma), 
						UtilidadesString.getCampoMultidioma(SigaConstants.FECHA, idioma), 
						UtilidadesString.getCampoMultidioma(SigaConstants.CUENTA, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.CONCEPTO, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.DOCUMENTO, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.DEBE, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.HABER, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.BASEIMP, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.TIPOIVA, idioma),
						UtilidadesString.getCampoMultidioma(SigaConstants.CONTRAPARTIDA, idioma));
				
				//3. Obtenemos los datos y los añadimos al excel
				Vector<Hashtable<String, Object>> datosExcel = new Vector<Hashtable<String, Object>>();
		
				// SE PONEN COMMITS INTERMEDIOS PARA QUE EL PROCESO AGUANTE EL TIEMPO QUE TARDA.
								
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 1 
				// FACTURA. Apunta asiento contable de facturas generadas, no pagada.
				// Ventas (700)	 			--> Bruto por servicio (1) --> 0
				// IVA 				 	    --> Iva (2)		           --> 0
				// Cliente(430.xxx)			--> 0		               --> Negocio(1)+Negocio(2)
				// -----------------------------------------------------------------------------------------------------------------		    
						    
				//datosExcel = generaAsiento1(datosExcel, registroFacRegistroFichConta, idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 2 
				// ABONO (POR IMPORTE EXCESIVO EN FACTURA) 
				// 2------>
				// Cliente(430.xxxx)            --> abono(1)+abono(2)	--> 0
				// Devolucion factura (708)		--> 0					--> abono(1)
				// IVA (477)					--> 0					--> abono(2)
				// -----------------------------------------------------------------------------------------------------------------
									
				//datosExcel = generaAsiento2(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 3
				// PAGO POR CAJA 
				// 3------> HABER --> BEDE
				// Cliente(430.xxx ) 	-->  pagoporcaja --> 0
				// Caja(570) 		 	-->  0			 --> pagoporcaja
				// -----------------------------------------------------------------------------------------------------------------
							
				//datosExcel = generaAsiento3(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// PAGO POR BANCO 
				// 4------> Banco
				// Cliente(430.xxx) 	-->  facturaincluidaendisquete 	--> 0
				// Banco(572.1xxx) 		-->  0			 				--> facturaincludidaendisquete
				// -----------------------------------------------------------------------------------------------------------------			
							
				//datosExcel = generaAsiento4(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 5
				// PAGO POR TARJETA 
				// 5------> Tarjeta
				// Cliente(430.xxx) 	-->  facturaincluidaendisquete 	--> 0
				// Banco(572.2xxx) 		-->  0			 				--> facturaincludidaendisquete
				// -----------------------------------------------------------------------------------------------------------------
		
				//datosExcel = generaAsiento5(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 6
				// DEVOLUCION DE FACTURA POR BANCO 
				// 6------>
				// Banco(572.1xxxx) 			-->  facturaincluidaendisquete --> 0
				// Cliente(430.xxx)				-->  0			 			   --> facturaincludidaendisquete
				// Si hay gastos bancarios
				// Banco(572.1xxxx) 			-->  GASTOSDEVOLUCION 			--> 0
				// Gastos bancarios(626)		-->  0			 				--> GASTOSDEVOLUCION
				// -----------------------------------------------------------------------------------------------------------------
				
				datosExcel = generaAsiento6(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 7
				// ALTA DE ANTICIPOS DE SERVICIOS. 
				// 7------> HABER --> BEDE
				// Caja anticipos (572.3xxxx) 	-->  importe del anticipo 	--> 0
				// Anticipos Cliente(438.xxx)	-->  0			 			--> importe del anticipo
				
				datosExcel = generaAsiento7(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 7A
				// LIQUIDACION DE ANTICIPOS DE SERVICIOS AL COLEGIO. 
				// 7A------> HABER --> BEDE
				// Anticipos Cliente(438.xxx) 			-->  importe de liquidacion	--> 0
				// Ingresos extraordinarios (778xxxx)	-->  0			 			--> importe de liquidacion
				
				datosExcel = generaAsiento7A(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 7B
				// ANTICIPOS DE SERVICIOS Y PRODUCTOS
				// 7B------> HABER --> BEDE
				// Anticipos Cliente(438.xxx) 			-->  importe de liquidacion	--> 0
				// Ingresos extraordinarios (778xxxx)	-->  0			 			--> importe de liquidacion
				
				datosExcel = generaAsiento7B(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
				
				// -----------------------------------------------------------------------------------------------------------------
				// Asiento 2B
				// PAGO POR BANCO (ABONOS) 
				// 2B------>
				// Banco (572.1xxx) 		-->  pagoabonobanco	 	--> 0
				// Cliente(430.xxx)			-->  0			 		--> pagoabonobanco
		
				datosExcel = generaAsiento2B(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
											
				//Crear el excel
				File excel = this.createExcelFile(camposContabilidad, datosExcel);
				//MultipartFile file = (MultipartFile) excel;
						
				//Guardarlo en ‘<ruta_base>/ficheros/contabilidad/XXXX/’ (donde XXXX es el idinstitucion).
				Date dateLog = new Date(0);
				FicheroVo ficheroVo = new FicheroVo();
				String rutaBase = cargasMasivasGFServiceImpl.getDirectorioFichero(Short.valueOf(idInstitucion));//RUTABASE???		
				ficheroVo.setDirectorio(rutaBase + "ficheros/contabilidad/"+ idInstitucion +"/");
				//ficheroVo.setDirectorio("C:\Users\aavalosmoreno\Desktop\");
				LOGGER.info(dateLog + ": Entrada a la subida del fichero de contabilidad con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " subido en la ruta: " + ficheroVo.getDirectorio());

				ficheroVo.setNombre(registroFacRegistroFichConta.getNombrefichero());
				ficheroVo.setDescripcion("Fichero de contabilidad: " + registroFacRegistroFichConta.getNombrefichero());

				ficheroVo.setIdinstitucion(Short.valueOf(idInstitucion));
				//Se convierte el fichero en array de bytes para su subida
				byte[] excelEnBytes = FileUtils.readFileToByteArray(excel); //REVISAR POM
				ficheroVo.setFichero(excelEnBytes);
				ficheroVo.setExtension("xls");

				ficheroVo.setUsumodificacion(Integer.valueOf(usuario));
				ficheroVo.setFechamodificacion(new Date());
				ficherosService.insert(ficheroVo);

				SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
				LOGGER.info(dateLog + ": Salida de la subida del fichero de contabilidad con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " subido en la ruta: " + ficheroVo.getDirectorio());
				
				//4. Cambiar el estado del registro a TERMINADO
				LOGGER.info("generarFicheroContabilidad() 4. Cambiar el estado del registro a TERMINADO -> Entrada a "
						+ "facRegistroFichContaExtendsMapper para actualizar el estado a terminado");
				registroFacRegistroFichConta.setEstado((short) 3);
				
				int respuestaCambioEstadoTerminado = facRegistroFichContaExtendsMapper.updateByPrimaryKeySelective(registroFacRegistroFichConta);
				
				if(respuestaCambioEstadoTerminado == 1) {
					LOGGER.info("generarFicheroContabilidad() 4. Cambiar el estado del registro a TERMINADO -> Registro "
							+ "FacRegistrofichconta con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " e institucion: " 
							+ registroFacRegistroFichConta.getIdinstitucion() + " actualizado correctamente");
				}else {
					LOGGER.info("generarFicheroContabilidad() 4. Cambiar el estado del registro a TERMINADO -> Registro "
							+ "FacRegistrofichconta con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " e institucion: " 
							+ registroFacRegistroFichConta.getIdinstitucion() + " no pudo ser actualizado");
					throw new Exception();
				}
				LOGGER.info("generarFicheroContabilidad() 4. Cambiar el estado del registro a TERMINADO -> Salida de "
						+ "facRegistroFichContaExtendsMapper para actualizar el estado a terminado");
				
				correcto = true;
				
			}catch(Exception e) {
				//Cambiar el estado del registro a ERROR
				LOGGER.info("generarFicheroContabilidad() Cambiar el estado del registro a ERROR -> Entrada a "
						+ "facRegistroFichContaExtendsMapper para actualizar el estado a error");
				registroFacRegistroFichConta.setEstado((short) 4);
				
				int respuestaCambioEstadoError = facRegistroFichContaExtendsMapper.updateByPrimaryKeySelective(registroFacRegistroFichConta);
				
				if(respuestaCambioEstadoError == 1) {
					LOGGER.info("generarFicheroContabilidad() Cambiar el estado del registro a ERROR -> Registro "
							+ "FacRegistrofichconta con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " e institucion: " 
							+ registroFacRegistroFichConta.getIdinstitucion() + " actualizado correctamente");
				}else {
					LOGGER.info("generarFicheroContabilidad() Cambiar el estado del registro a ERROR -> Registro "
							+ "FacRegistrofichconta con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " e institucion: " 
							+ registroFacRegistroFichConta.getIdinstitucion() + " no pudo ser actualizado");
					throw new Exception();
				}
				LOGGER.info("generarFicheroContabilidad() Cambiar el estado del registro a ERROR -> Salida de "
						+ "facRegistroFichContaExtendsMapper para actualizar el estado a error");
				correcto = false;
				
				throw new Exception("La generacion del fichado de contabilidad ha sido detenida debido a un error",e);
			}
			
			LOGGER.info("generarFicheroContabilidad() -> Salida del metodo que crea el fichero de contabilidad");
		
			return correcto;
		}
		
		
		private int asiento = 0;
		
		//FACTURAS
		private Vector<Hashtable<String, Object>> generaAsiento1(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento1() --> Entrada al metodo para obtener y guardar la informacion de las facturas en el excel");
			
			String concepto 		= "";
			String asientoClientes 	= ""; 
			String asientoIngresos 	= ""; 
			String asientoIVA 	    = "";  
			String imp 				= null; 
			String importeIva 		= null;
			List<FacFactura> listaFacturasAContabilizar = new ArrayList<FacFactura>();      	
			
			try{
				
				List<FacturasContabilidadItem> listaFacturas = facRegistroFichContaExtendsMapper.obtenerFacturas(registroFacRegistroFichConta);
				
				String idFacturaAnt = ""; 
				String idFactura = "";  
				
				for(int x=0; x < listaFacturas.size(); x++){
					FacturasContabilidadItem factura = listaFacturas.get(x);
					
				    idFactura = factura.getIdfactura();
					    
					// PARA CADA LINEA FACTURA COMPRUEBO LA CONFIGURACIÓN DE CUENTAS
				    String confClientes = factura.getConfdeudor();
				    String confIngresos = factura.getConfingresos();
				    String ctaClientes = factura.getCtaclientes();
				    String ctaIngresos = factura.getCtaingresos();
				     
				    // importes
				    imp = UtilidadesNumeros.redondea(String.valueOf(factura.getImpneto()), 2);
				    importeIva = UtilidadesNumeros.redondea(String.valueOf(factura.getImpiva()), 2);
				    String valorIva = UtilidadesNumeros.redondea(String.valueOf(factura.getIva()), 2);
				    
				    // Control de iva 0
				    boolean ivacero=false;
				    try {
				        Double d = new Double(valorIva);
				        if (d.doubleValue()==0.0) 
				            ivacero=true;
				    } catch (NumberFormatException nf) {
				    }
				    
				    // concepto
					concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO1) + ": " + factura.getDescripcion());
					
					// asientos configurables
					asientoIVA = factura.getCtaiva();
					if (confClientes.equals("F")) {
					    asientoClientes =  ctaClientes;
					} else {
					    asientoClientes =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(factura.getIdpersona()));
					}
					if (confIngresos.equals("F")) {
					    asientoIngresos =  ctaIngresos;
					} else if (confIngresos.equals("C")) {
					    asientoIngresos =  ctaIngresos + obtenerAsientoContable(idInstitucion, String.valueOf(factura.getIdpersona()));
					} else {
					    asientoIngresos =  ctaIngresos + factura.getCtaproductosservicio();
					}
					
					
					// aumentamos el contador de asientos
					asiento++;
					
					Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
					
					// Escribimos 1º APUNTE
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", factura.getFechaemision());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", factura.getNumerofactura());
					datosHashtable.put("CUENTA", asientoClientes);
					datosHashtable.put("DEBE", (Double.parseDouble(imp) + Double.parseDouble(importeIva)));
					datosHashtable.put("HABER", "0");
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoIngresos);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);
					
					// Escribimos 2º APUNTE
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", factura.getFechaemision().toString());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", factura.getNumerofactura());
					datosHashtable.put("CUENTA", asientoIngresos);
					datosHashtable.put("DEBE", "0");
					datosHashtable.put("HABER", imp);
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoClientes);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);
					
					// Escribimos 3º APUNTE
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", factura.getFechaemision().toString());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", factura.getNumerofactura());
					datosHashtable.put("CUENTA", asientoIVA);
					datosHashtable.put("DEBE", "0");
					if (!ivacero) {
						datosHashtable.put("HABER", importeIva);
					//MJM se hace un apunte con importe IVA 0 si no tiene IVA 
					//Esto es por la incidencia R1502_0055:
					//en la exportación de datos a contaplus no se tienen en cuenta las fac. con IVA 0 sino existe
					//asiento de IVA.
					} else {
						datosHashtable.put("HABER", "0");
					}
					datosHashtable.put("BASEIMPONIBLE", imp);
					datosHashtable.put("IVA", valorIva);
					datosHashtable.put("CONTRAPARTIDA", asientoClientes);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);
		
					///////////////////////////////
					
					
					// ACTUALIZACION DE CONTABILIZADA
					if (!idFactura.equals(idFacturaAnt)) {
					    // Modificamos solamente cuando cambia la factura
						FacFactura facturaAcontabilizar = new FacFactura();
						
						facturaAcontabilizar.setContabilizada(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
						facturaAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
						facturaAcontabilizar.setIdfactura(idFactura);
						facturaAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
						facturaAcontabilizar.setFechamodificacion(new Date());
						
						listaFacturasAContabilizar.add(facturaAcontabilizar);
						idFacturaAnt = idFactura;
					}
						
				}
				
				if(listaFacturasAContabilizar.size()>0){			
					
					for (FacFactura facturaAcontabilizar : listaFacturasAContabilizar) {
						int respuestaActualizarFacturaContabilizada = facFacturaExtendsMapper.updateByPrimaryKeySelective(facturaAcontabilizar);
						
						if(respuestaActualizarFacturaContabilizada == 1) {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento1() --> facFacturaExtendsMapper.updateByPrimaryKeySelective() --> Factura con id: " + facturaAcontabilizar.getIdfactura() + " contabilizada");
						}else {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento1() --> facFacturaExtendsMapper.updateByPrimaryKeySelective() --> La factura con id: " + facturaAcontabilizar.getIdfactura() + " no pudo ser contabilizada");
							throw new Exception("La factura con id: " + facturaAcontabilizar.getIdfactura() + " no pudo ser contabilizada");
						}
					}		
				}
					
			}catch (Exception e){
				throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento1()",e);
			}
			
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento1() --> Salida del metodo para obtener y guardar la informacion de las facturas en el excel");
			return datosExcel;
		}

		//ABONOS 
		private Vector<Hashtable<String, Object>> generaAsiento2(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento2() --> Entrada al metodo para obtener y guardar la informacion de los abonos en el excel");

			String concepto 		= "";
			String imp              = null; 
			String importeIva       = null;
			String asientoClientes = ""; 
			String asientoIngresos = ""; 
			List<FacAbono> listaAbonosAContabilizar = new ArrayList<FacAbono>();  
			
			try{	
						    
				List<AbonoContabilidadItem> listaAbonos = facRegistroFichContaExtendsMapper.obtenerAbonos(registroFacRegistroFichConta);
				
				String idAbonoAnt = ""; 
				String idAbono = ""; 
						
				for(int x=0; x < listaAbonos.size(); x++){
					AbonoContabilidadItem abono = listaAbonos.get(x);
					
				    idAbono = String.valueOf(abono.getIdabono());
					    
					// PARA CADA LINEA FACTURA COMPRUEBO LA CONFIGURACIÓN DE CUENTAS
				    String confClientes = abono.getConfdeudor();
				    String confIngresos = abono.getConfingresos();
				    String ctaClientes = abono.getCtaclientes();
				    String ctaIngresos = abono.getCtaingresos();
						
				    // importes
				    imp = UtilidadesNumeros.redondea(String.valueOf(abono.getImpneto()), 2);
				    importeIva = UtilidadesNumeros.redondea(String.valueOf(abono.getImpiva()), 2);
				    String valorIva = UtilidadesNumeros.redondea(String.valueOf(abono.getIva()), 2);
				    
				    // Control de iva 0
				    boolean ivacero=false;
				    try {
				        Double d = new Double(valorIva);
				        if (d.doubleValue()==0.0) 
				            ivacero=true;
				    } catch (NumberFormatException nf) {
				    }

				    // concepto
					concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO2) + ": " + abono.getNumerofactura());
					
					// asientos configurables
					String asientoIVA = abono.getCtaiva();
					if (confClientes.equals("F")) {
					    asientoClientes =  ctaClientes;
					} else {
					    asientoClientes =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(abono.getIdpersona()));
					}
					if (confIngresos.equals("F")) {
					    asientoIngresos =  ctaIngresos;
					} else if (confIngresos.equals("C")) {
					    asientoIngresos =  ctaIngresos + obtenerAsientoContable(idInstitucion, String.valueOf(abono.getIdpersona()));
					} else {
					    asientoIngresos =  ctaIngresos + abono.getCtaproductoservicio();
					}
					 
					String devuelta="";
						
					if(abono.getDescripcion() != null);
						devuelta = abono.getDevuelta();
						
					// aumentamos el contador de asientos
					asiento++; 
								
					Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
					
					String impPagBanco = UtilidadesNumeros.redondea(String.valueOf(abono.getImptotalpagadoporbanco()), 2);			
					String impPagCaja = UtilidadesNumeros.redondea(String.valueOf(abono.getImptotalpagadoporcaja()) , 2);
					
					// Escribimos 1º APUNTE
					
						
					//Si la factura está devuelta o no está pagada inicialmente
					if((devuelta.equalsIgnoreCase("S"))||(impPagBanco.equalsIgnoreCase("0.0")&&(impPagCaja.equalsIgnoreCase("0.0"))))
					{
						
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", abono.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", abono.getNumeroabono());
						datosHashtable.put("CUENTA", asientoIngresos);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoClientes);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", abono.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", abono.getNumeroabono());
						datosHashtable.put("CUENTA", asientoClientes);
						datosHashtable.put("DEBE", "" + (Double.parseDouble(imp) + Double.parseDouble(importeIva)));
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoIngresos);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
						// Escribimos 3º APUNTE
						if (!ivacero) {
							
							datosHashtable.clear();
							datosHashtable.put("ASIENTO", asiento);
							datosHashtable.put("FECHA", abono.getFecha());
							datosHashtable.put("CONCEPTO", concepto);
							datosHashtable.put("DOCUMENTO", abono.getNumeroabono());
							datosHashtable.put("CUENTA", asientoClientes);
							datosHashtable.put("DEBE", importeIva);
							datosHashtable.put("HABER", "0");
							datosHashtable.put("BASEIMPONIBLE", imp);
							datosHashtable.put("IVA", valorIva);
							datosHashtable.put("CONTRAPARTIDA", asientoIVA);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);
							
						}	
						
					//Si la factura no está devuelta está pagada inicialmente
					}else{
							
						String asientoContableBancoCja="";
							
							if((abono.getBancos_codigo() != null) && (abono.getBancos_codigo() != ""))
								asientoContableBancoCja	= obtenerAsientoContableBanco(idInstitucion, abono.getBancos_codigo());  
							else if(impPagCaja!="0.0")
								//asientoContableBancoCja=CONTABILIDAD_CAJA; REVISAR ESTO LA CONSTANTE SALE VACIA EN SIGA VIEJO
								asientoContableBancoCja= CONTABILIDAD_CAJA;
							else
								asientoContableBancoCja=asientoIngresos;
							
							datosHashtable.clear();
							datosHashtable.put("ASIENTO", asiento);
							datosHashtable.put("FECHA", abono.getFecha());
							datosHashtable.put("CONCEPTO", concepto);
							datosHashtable.put("DOCUMENTO", abono.getNumeroabono());
							datosHashtable.put("CUENTA", asientoContableBancoCja);
							datosHashtable.put("DEBE", "" + (Double.parseDouble(imp) + Double.parseDouble(importeIva)));
							datosHashtable.put("HABER", "0");
							datosHashtable.put("BASEIMPONIBLE", "");
							datosHashtable.put("IVA", "");
							datosHashtable.put("CONTRAPARTIDA", asientoClientes);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);

							datosHashtable.clear();
							datosHashtable.put("ASIENTO", asiento);
							datosHashtable.put("FECHA", abono.getFecha());
							datosHashtable.put("CONCEPTO", concepto);
							datosHashtable.put("DOCUMENTO", abono.getNumeroabono());
							datosHashtable.put("CUENTA", asientoClientes);
							datosHashtable.put("DEBE", "0");
							datosHashtable.put("HABER", imp);
							datosHashtable.put("BASEIMPONIBLE", "");
							datosHashtable.put("IVA", "");
							datosHashtable.put("CONTRAPARTIDA", asientoContableBancoCja);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);

							// Escribimos 3º APUNTE
							if (!ivacero) {
								datosHashtable.clear();
								datosHashtable.put("ASIENTO", asiento);
								datosHashtable.put("FECHA", abono.getFecha());
								datosHashtable.put("CONCEPTO", concepto);
								datosHashtable.put("DOCUMENTO", abono.getNumeroabono());
								datosHashtable.put("CUENTA", asientoClientes);
								datosHashtable.put("DEBE", "0");
								datosHashtable.put("HABER", importeIva);
								datosHashtable.put("BASEIMPONIBLE", imp);
								datosHashtable.put("IVA", valorIva);
								datosHashtable.put("CONTRAPARTIDA", asientoIVA);
								datosHashtable = this.checkDatos(asiento, datosHashtable);
								datosExcel.add(datosHashtable);
								
							}	
						
						}
		
						if (!idAbono.equals(idAbonoAnt)) {
							FacAbono abonoAcontabilizar = new FacAbono();
							
							abonoAcontabilizar.setContabilizada(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
							abonoAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
							abonoAcontabilizar.setIdabono(Long.valueOf(idAbono));
							abonoAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
							abonoAcontabilizar.setFechamodificacion(new Date());
							
							listaAbonosAContabilizar.add(abonoAcontabilizar);
							idAbonoAnt = idAbono;
						}
					}
						
					if(listaAbonosAContabilizar.size() > 0){
							
						for (FacAbono abonoAcontabilizar : listaAbonosAContabilizar) {
							int respuestaActualizarAbonoContabilizado = facAbonoExtendsMapper.updateByPrimaryKeySelective(abonoAcontabilizar);
							
							if(respuestaActualizarAbonoContabilizado == 1) {
								LOGGER.info("generarFicheroContabilidad() --> generaAsiento2() --> facAbonoExtendsMapper.updateByPrimaryKeySelective() --> Abono con id: " + abonoAcontabilizar.getIdabono() + " contabilizado");
							}else {
								LOGGER.info("generarFicheroContabilidad() --> generaAsiento2() --> facAbonoExtendsMapper.updateByPrimaryKeySelective() --> El abono con id: " + abonoAcontabilizar.getIdabono() + " no pudo ser contabilizado");
								throw new Exception("El abono con id: " + abonoAcontabilizar.getIdabono() + " no pudo ser contabilizado");
							}
						}
					}
				
				}catch (Exception e) 
				{
					throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento2()",e);
				}
			

			LOGGER.info("generarFicheroContabilidad() --> generaAsiento2() --> Salida del metodo para obtener y guardar la informacion de los abonos en el excel");
			
			return datosExcel;
		}
		
		//PAGO POR CAJA
		private Vector<Hashtable<String, Object>> generaAsiento3(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception {
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento3() --> Entrada al metodo para obtener y guardar la informacion de los pagos por caja en el excel");
			
	        String concepto = "";
	        String conceptoCompensado = "";
	        String asientoContable = null;
			String imp = null; 
			String importeIva = null;
			String asientoClientes = ""; 
			String asientoIngresos = ""; 
			List<FacPagosporcaja> listaPagosPorCajaAcontabilizar = new ArrayList<FacPagosporcaja>();  
		
			try{

				// pagos por caja sobre la factura, obteniendo el idapunte para comprobar si la factura ha sido compensada o no
				List<PagoPorCajaItem> listaPagosPorCaja = facRegistroFichContaExtendsMapper.obtenerPagosPorCaja(registroFacRegistroFichConta);
			
				String idFactura = "";
				String idFacturaAnt = "";
				
				conceptoCompensado = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma , SigaConstants.CONCEPTO_ASIENTO3_2010));		
				concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO3));

				for(int x=0; x < listaPagosPorCaja.size(); x++){
					asiento++;
					
					PagoPorCajaItem pagoPorCaja = listaPagosPorCaja.get(x);
					idFactura = String.valueOf(pagoPorCaja.getIdfactura());
					
					imp = UtilidadesNumeros.redondea( String.valueOf(pagoPorCaja.getImporte()),2);
					String confClientes = pagoPorCaja.getConfdeudor();
					String ctaClientes = pagoPorCaja.getCtaclientes();
					String tipoApunte = pagoPorCaja.getTipoapunte();
					
					// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
					String asientoCliente = obtenerAsientoContable(idInstitucion, String.valueOf(pagoPorCaja.getIdpersona()));
					
					if (confClientes.equals("F")) {
					    asientoContable =  ctaClientes;
					} else {
					    asientoContable =  ctaClientes + asientoCliente;
					}
					
					String asientoCompensacionCliente = CONTABILIDAD_COMPENSACION + asientoCliente;
					
					Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
				
					if (tipoApunte!=null && tipoApunte.trim().equals("C")) {
						// PAGO COMPENSADO
					    // Escribimos 1� apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", pagoPorCaja.getFecha());
						datosHashtable.put("CONCEPTO", conceptoCompensado);
						datosHashtable.put("DOCUMENTO", pagoPorCaja.getNumerofactura());
						datosHashtable.put("CUENTA", asientoContable);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoCompensacionCliente);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
					
					    // pago por caja sobre factura
						// Escribimos 2� apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", pagoPorCaja.getFecha());
						datosHashtable.put("CONCEPTO", conceptoCompensado);
						datosHashtable.put("DOCUMENTO", pagoPorCaja.getNumerofactura());
						datosHashtable.put("CUENTA", asientoCompensacionCliente);
						datosHashtable.put("DEBE", imp);
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoContable);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
					} else {
						// PAGO NORMAL POR CAJA (NO TARJETA)
						// Escribimos 1� apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", pagoPorCaja.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", pagoPorCaja.getNumerofactura());
						datosHashtable.put("CUENTA", asientoContable);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", CONTABILIDAD_CAJA);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
					    // pago por caja sobre factura
						// Escribimos 2� apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", pagoPorCaja.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", pagoPorCaja.getNumerofactura());
						datosHashtable.put("CUENTA", CONTABILIDAD_CAJA);
						datosHashtable.put("DEBE", imp);
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoContable);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
								    
					}
				

					if (!idFactura.equals(idFacturaAnt)) {
					    // Solamente cuando cambia idfactura
						FacPagosporcaja pagoPorCajaAcontabilizar = new FacPagosporcaja();
						
						pagoPorCajaAcontabilizar.setContabilizado(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
						pagoPorCajaAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
						pagoPorCajaAcontabilizar.setIdfactura(idFactura);
						pagoPorCajaAcontabilizar.setIdpagoporcaja((short) pagoPorCaja.getIdpagoporcaja());
						pagoPorCajaAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
						pagoPorCajaAcontabilizar.setFechamodificacion(new Date());
						
						listaPagosPorCajaAcontabilizar.add(pagoPorCajaAcontabilizar);
						idFacturaAnt = idFactura;
						
					}
				}
				
				if(listaPagosPorCajaAcontabilizar.size() > 0){
					
					for (FacPagosporcaja pagoPorCajaAcontabilizar : listaPagosPorCajaAcontabilizar) {
						int respuestaActualizarPagoPorCajaContabilizado = facPagosPorCajaMapper.updateByPrimaryKeySelective(pagoPorCajaAcontabilizar);
						
						if(respuestaActualizarPagoPorCajaContabilizado == 1) {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento3() --> facPagosPorCajaMapper.updateByPrimaryKeySelective() --> Pago por caja con id: " + pagoPorCajaAcontabilizar.getIdfactura() + " contabilizado");
						}else {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento3() --> facPagosPorCajaMapper.updateByPrimaryKeySelective() --> El pago por caja con id: " + pagoPorCajaAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
							throw new Exception("El pago por caja con id: " + pagoPorCajaAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
						}
					}
				}

			}
			catch (Exception e) {
				throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento3()",e);
			}

			LOGGER.info("generarFicheroContabilidad() --> generaAsiento3() --> Salida del metodo para obtener y guardar la informacion de los pagos por caja en el excel");
			
			return datosExcel;
		}
		
		//PAGO POR BANCO                                                                       
		private Vector<Hashtable<String, Object>> generaAsiento4(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento4() --> Entrada al metodo para obtener y guardar la informacion de los pagos por banco en el excel");
			
		    String concepto 		    = "";
			String asientoContable 	    = null; 
			String asientoContableBanco = null;
			String select 		        = null; 
			String imp                  = null;
			int contador                = 0;     
			List<FacFacturaincluidaendisquete> listaPagosPorBancoAcontabilizar = new ArrayList<FacFacturaincluidaendisquete>();  

			try{
				
				List<PagoPorBancoItem> listaPagosPorBanco = facRegistroFichContaExtendsMapper.obtenerPagosPorBanco(registroFacRegistroFichConta);

				String idFactura ="";
				String idFacturaAnt ="";

				// Descripcion del concepto
				concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma,SigaConstants.CONCEPTO_ASIENTO4));
			
				for(int x=0;x<listaPagosPorBanco.size();x++){
					PagoPorBancoItem pagoPorBancoItem = listaPagosPorBanco.get(x);

					asiento++;
					
					idFactura = String.valueOf(pagoPorBancoItem.getIdfactura());
					
					imp = UtilidadesNumeros.redondea(String.valueOf(pagoPorBancoItem.getImporte()),2);
					
					String confClientes = pagoPorBancoItem.getConfdeudor();
					String ctaClientes = pagoPorBancoItem.getCtaclientes();
					
					// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
					if (confClientes.equals("F")) {
					    asientoContable =  ctaClientes;
					} else {
					    asientoContable =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(pagoPorBancoItem.getIdpersona()));
					}
					
					// Con el BANCOS_CODIGO, obtenemos de FAC_BANCOINSTITUCION, el numerocuenta.
					asientoContableBanco	= obtenerAsientoContableBanco(idInstitucion, pagoPorBancoItem.getBancos_codigo());
					
					Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
					
					// Escribimos 1� apunte
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", pagoPorBancoItem.getFechacreacion());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", pagoPorBancoItem.getNumerofactura());
					datosHashtable.put("CUENTA", asientoContable);
					datosHashtable.put("DEBE", "0");
					datosHashtable.put("HABER", imp);
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoContableBanco);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);						

					// Escribimos 2� asiento
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", pagoPorBancoItem.getFechacreacion());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", pagoPorBancoItem.getNumerofactura());
					datosHashtable.put("CUENTA", asientoContableBanco);
					datosHashtable.put("DEBE", imp);
					datosHashtable.put("HABER", "0");
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoContable);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);	
					
					if (!idFactura.equals(idFacturaAnt)) {
						
						// Solamente cuando cambia idfactura
						FacFacturaincluidaendisquete pagoPorBancoAcontabilizar = new FacFacturaincluidaendisquete();
						
						pagoPorBancoAcontabilizar.setContabilizada(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
						
						pagoPorBancoAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
						pagoPorBancoAcontabilizar.setIddisquetecargos(Long.valueOf(pagoPorBancoItem.getIddisquetecargos()));
						pagoPorBancoAcontabilizar.setIdfacturaincluidaendisquete(pagoPorBancoItem.getIdfacturaincluidaendisquete());
						
						pagoPorBancoAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
						pagoPorBancoAcontabilizar.setFechamodificacion(new Date());
						
						listaPagosPorBancoAcontabilizar.add(pagoPorBancoAcontabilizar);
						idFacturaAnt = idFactura;
			
					}
				}
				
				if(listaPagosPorBancoAcontabilizar.size()>0){
					
					for (FacFacturaincluidaendisquete pagoPorBancoAcontabilizar : listaPagosPorBancoAcontabilizar) {
						int respuestaActualizarPagoPorBancoContabilizado = facFacturaIncluidaEnDisqueteMapper.updateByPrimaryKeySelective(pagoPorBancoAcontabilizar);
						
						if(respuestaActualizarPagoPorBancoContabilizado == 1) {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento4() --> facFacturaIncluidaEnDisqueteMapper.updateByPrimaryKeySelective() --> Pago por banco con id: " + pagoPorBancoAcontabilizar.getIdfactura() + " contabilizado");
						}else {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento4() --> facFacturaIncluidaEnDisqueteMapper.updateByPrimaryKeySelective() --> El pago por banco con id: " + pagoPorBancoAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
							throw new Exception("El pago por banco con id: " + pagoPorBancoAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
						}
					}
					
				}
				
			}catch (Exception e) 
			{
				throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento4()",e);
			}

			LOGGER.info("generarFicheroContabilidad() --> generaAsiento4() --> Salida del metodo para obtener y guardar la informacion de los pagos por banco en el excel");
			
			return datosExcel;
		}
		
		//PAGO POR TARJETA 
		private Vector<Hashtable<String, Object>> generaAsiento5(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento5() --> Entrada al metodo para obtener y guardar la informacion de los pagos por tarjeta en el excel");
			
		    String concepto 		= "";
			String conceptoT 		= "";
			String asientoContable 	= null;
			String imp              = null;
	        List<FacPagosporcaja> listaPagosPorTarjetaAcontabilizar = new ArrayList<FacPagosporcaja>();  

			try{

				List<PagoPorTarjetaItem> listaPagosPorTarjeta = facRegistroFichContaExtendsMapper.obtenerPagosPorTarjeta(registroFacRegistroFichConta);
				
				String idFactura ="";
				String idFacturaAnt ="";

//				// Descripcion del concepto
				conceptoT = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma,SigaConstants.CONCEPTO_ASIENTO5));


				for(int x=0;x<listaPagosPorTarjeta.size();x++){
				    
					asiento++;

				    PagoPorTarjetaItem pagoPorTarjetaItem = listaPagosPorTarjeta.get(x);
					idFactura = String.valueOf(pagoPorTarjetaItem.getIdfactura());
					
					imp = UtilidadesNumeros.redondea(String.valueOf(pagoPorTarjetaItem.getImporte()),2);
					String confClientes = pagoPorTarjetaItem.getConfdeudor();
					String ctaClientes = pagoPorTarjetaItem.getCtaclientes();
					
					// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
					if (confClientes.equals("F")) {
					    asientoContable =  ctaClientes;
					} else {
					    asientoContable =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(pagoPorTarjetaItem.getIdpersona()));
					}
					
					// SE CREA EL ASIENTO
					Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
					
					// Escribimos 1� apunte
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", pagoPorTarjetaItem.getFecha());
					datosHashtable.put("CONCEPTO", conceptoT);
					datosHashtable.put("DOCUMENTO", pagoPorTarjetaItem.getNumerofactura());
					datosHashtable.put("CUENTA", asientoContable);
					datosHashtable.put("DEBE", "0");
					datosHashtable.put("HABER", imp);
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", CONTABILIDAD_TARJETAS);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);	

//					// Escribimos 2� asiento
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", pagoPorTarjetaItem.getFecha());
					datosHashtable.put("CONCEPTO", conceptoT);
					datosHashtable.put("DOCUMENTO", pagoPorTarjetaItem.getNumerofactura());
					datosHashtable.put("CUENTA", CONTABILIDAD_TARJETAS);
					datosHashtable.put("DEBE", imp);
					datosHashtable.put("HABER", "0");
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoContable);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);	
			
					if (!idFactura.equals(idFacturaAnt)) {
						// Solamente cuando cambia idfactura
						FacPagosporcaja pagoPorTarjetaAcontabilizar = new FacPagosporcaja();
						
						pagoPorTarjetaAcontabilizar.setContabilizado(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
						pagoPorTarjetaAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
						pagoPorTarjetaAcontabilizar.setIdfactura(String.valueOf(pagoPorTarjetaItem.getIdfactura()));
						pagoPorTarjetaAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
						pagoPorTarjetaAcontabilizar.setFechamodificacion(new Date());
						
						listaPagosPorTarjetaAcontabilizar.add(pagoPorTarjetaAcontabilizar);
						idFacturaAnt = idFactura;

					}
				}

				if(listaPagosPorTarjetaAcontabilizar.size()>0){
					
					for (FacPagosporcaja pagoPorTarjetaAcontabilizar : listaPagosPorTarjetaAcontabilizar) {
						int respuestaActualizarPagoPorTarjetaContabilizado = facPagosPorCajaMapper.updateByPrimaryKeySelective(pagoPorTarjetaAcontabilizar);
						
						if(respuestaActualizarPagoPorTarjetaContabilizado == 1) {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento5() --> facPagosPorCajaMapper.updateByPrimaryKeySelective() --> Pago por tarjeta con id: " + pagoPorTarjetaAcontabilizar.getIdfactura() + " contabilizado");
						}else {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento5() --> facPagosPorCajaMapper.updateByPrimaryKeySelective() --> El pago por tarjeta con id: " + pagoPorTarjetaAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
							throw new Exception("El pago por tarjeta con id: " + pagoPorTarjetaAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
						}
					}
					
				}

			}catch (Exception e) 
			{
				throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento5()",e);
			}
			
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento5() --> Salida del metodo para obtener y guardar la informacion de los pagos por tarjeta en el excel");
			return datosExcel;
		}
		
		//DEVOLUCIONES                                                                       
		private Vector<Hashtable<String, Object>> generaAsiento6(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento6() --> Entrada al metodo para obtener y guardar la informacion de las devoluciones en el excel");
			
			String concepto 		    = "";
			String conceptoComision     = "";
			String asientoContable 	    = null; 
			String asientoContableBanco = null; 
			String gastosDevolucion     = null; 
			String cargarCliente 		= null;
			String select 				= null; 
			String imp 					= null;
			int contador 				= 0;
			
			List<FacLineadevoludisqbanco> listaDevolucionesAcontabilizar = new ArrayList<FacLineadevoludisqbanco>();
	  
			try {
				
				List<DevolucionesItem> listaDevoluciones = facRegistroFichContaExtendsMapper.obtenerDevoluciones(registroFacRegistroFichConta);
				
				String idDisquete ="";
				String idDisqueteAnt ="";

				// Descripcion del concepto
				concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO5));
				
				for(int x=0;x<listaDevoluciones.size();x++){
					asiento++;

					DevolucionesItem devolucion = listaDevoluciones.get(x);

					idDisquete = String.valueOf(devolucion.getIddisquetedevoluciones());
					
					imp = UtilidadesNumeros.redondea(String.valueOf(devolucion.getImporte()), 2);
					
					String confClientes = devolucion.getConfdeudor();
					String ctaClientes = devolucion.getCtaclientes();
					
					// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
					if (confClientes.equals("F")) {
					    asientoContable =  ctaClientes;
					} else {
					    asientoContable =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(devolucion.getIdpersona()));
					}
					
					gastosDevolucion = String.valueOf(devolucion.getGastosdevolucion());
					cargarCliente = devolucion.getCargarcliente();

					// Con el BANCOS_CODIGO, obtenemos de FAC_BANCOINSTITUCION, el numerocuenta.
					asientoContableBanco	= obtenerAsientoContableBanco(idInstitucion, devolucion.getBancos_codigo());  

					Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
					
					// Escribimos 1� apunte
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", devolucion.getFechageneracion());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", devolucion.getNumerofactura());
					datosHashtable.put("CUENTA", asientoContable);
					datosHashtable.put("DEBE", imp);
					datosHashtable.put("HABER", "0");
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoContableBanco);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);	
					
					// Escribimos 2� asiento
					datosHashtable.clear();
					datosHashtable.put("ASIENTO", asiento);
					datosHashtable.put("FECHA", devolucion.getFechageneracion());
					datosHashtable.put("CONCEPTO", concepto);
					datosHashtable.put("DOCUMENTO", devolucion.getNumerofactura());
					datosHashtable.put("CUENTA", asientoContableBanco);
					datosHashtable.put("DEBE", "0");
					datosHashtable.put("HABER", imp);
					datosHashtable.put("BASEIMPONIBLE", "");
					datosHashtable.put("IVA", "");
					datosHashtable.put("CONTRAPARTIDA", asientoContable);
					datosHashtable = this.checkDatos(asiento, datosHashtable);
					datosExcel.add(datosHashtable);	
			
					if (!idDisqueteAnt.equals(idDisquete)) {
						FacLineadevoludisqbanco devolucionAcontabilizar = new FacLineadevoludisqbanco();
						
						devolucionAcontabilizar.setContabilizada(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
						devolucionAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
						devolucionAcontabilizar.setIddisquetedevoluciones(Long.valueOf(devolucion.getIddisquetedevoluciones()));
						devolucionAcontabilizar.setIdrecibo(devolucion.getIdrecibo());
						devolucionAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
						devolucionAcontabilizar.setFechamodificacion(new Date());
						
						listaDevolucionesAcontabilizar.add(devolucionAcontabilizar);
						idDisqueteAnt = idDisquete;					
						
					}
				}
				
				if(listaDevolucionesAcontabilizar.size()>0){
					
					for (FacLineadevoludisqbanco devolucionAcontabilizar : listaDevolucionesAcontabilizar) {
						int respuestaActualizarDevolucionContabilizado = facLineaDevoluDisqBancoMapper.updateByPrimaryKeySelective(devolucionAcontabilizar);
						
						if(respuestaActualizarDevolucionContabilizado == 1) {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento6() --> facLineaDevoluDisqBancoMapper.updateByPrimaryKeySelective() --> Devolucion con id: " + devolucionAcontabilizar.getIddisquetedevoluciones() + " contabilizada");
						}else {
							LOGGER.info("generarFicheroContabilidad() --> generaAsiento6() --> facLineaDevoluDisqBancoMapper.updateByPrimaryKeySelective() --> Devolucion con id: " + devolucionAcontabilizar.getIddisquetedevoluciones() + " no pudo ser contabilizada");
							throw new Exception("Devolucion con id: " + devolucionAcontabilizar.getIddisquetedevoluciones() + " no pudo ser contabilizada");
						}
					}
					
				}
				
				
			}
			catch (Exception e) {
				throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento6()",e);
			}
			
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento6() --> Salida del metodo para obtener y guardar la informacion de devoluciones en el excel");
			return datosExcel;
		}
		
		//ALTA DE ANTICIPOS
		private Vector<Hashtable<String, Object>> generaAsiento7(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento7() --> Entrada al metodo para obtener y guardar la informacion de las altas de anticipos en el excel");
			
			String concepto 		            = "";
			String asientoContable 	            = null; 
			String asientoContableCajaAnticipos = null;
			String imp 							= null;
			
			List<PysAnticipoletrado> listaAltasAnticiposAcontabilizar = new ArrayList<PysAnticipoletrado>();
				
			try{
						
				List<AltaAnticipoItem> listaAltasAnticipos = facRegistroFichContaExtendsMapper.obtenerAltasAnticipos(registroFacRegistroFichConta);

					String idAnticipo ="";
					String idAnticipoAnt ="";

					for(int x=0;x<listaAltasAnticipos.size();x++)
					{
						AltaAnticipoItem altaAnticipoItem = listaAltasAnticipos.get(x);

						asiento++;
						
						// Descripcion del concepto
						concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO7) + " " + altaAnticipoItem.getDescripcion());
						
						idAnticipo = String.valueOf(altaAnticipoItem.getIdanticipo());
						
						imp = UtilidadesNumeros.redondea(String.valueOf(altaAnticipoItem.getImporteinicial()),2);
						
						String ctaClientes = altaAnticipoItem.getCtacontable();
						
						// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
					    asientoContable =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(altaAnticipoItem.getIdpersona()));
						
						// Con el BANCOS_CODIGO, obtenemos de FAC_BANCOINSTITUCION, el numerocuenta.
					    asientoContableCajaAnticipos	= CONTABILIDAD_CAJA_ANTICIPOS;

					    Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
						
						// Escribimos 1� apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", altaAnticipoItem.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", altaAnticipoItem.getIdanticipo());
						datosHashtable.put("CUENTA", asientoContable);
						datosHashtable.put("DEBE", imp);
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoContableCajaAnticipos);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	
						
			
						
						// Escribimos 2� asiento
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", altaAnticipoItem.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", altaAnticipoItem.getIdanticipo());
						datosHashtable.put("CUENTA", asientoContableCajaAnticipos);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoContable);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	
						
						if (!idAnticipo.equals(idAnticipoAnt)) {
							PysAnticipoletrado altaAnticipoAcontabilizar = new PysAnticipoletrado();
							
							altaAnticipoAcontabilizar.setContabilizado(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
							altaAnticipoAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
							altaAnticipoAcontabilizar.setIdpersona(Long.valueOf(altaAnticipoItem.getIdpersona()));
							altaAnticipoAcontabilizar.setIdanticipo((short) altaAnticipoItem.getIdanticipo());
							altaAnticipoAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
							altaAnticipoAcontabilizar.setFechamodificacion(new Date());
							
							listaAltasAnticiposAcontabilizar.add(altaAnticipoAcontabilizar);
							idAnticipoAnt = idAnticipo;		
							
						}
					}
					
					if(listaAltasAnticiposAcontabilizar.size()>0){
						
						for (PysAnticipoletrado altaAnticipoAcontabilizar : listaAltasAnticiposAcontabilizar) {
							int respuestaActualizarAltaAnticipoContabilizado = pysAnticipoLetradoMapper.updateByPrimaryKeySelective(altaAnticipoAcontabilizar);
							
							if(respuestaActualizarAltaAnticipoContabilizado == 1) {
								LOGGER.info("generarFicheroContabilidad() --> generaAsiento7() --> pysAnticipoLetradoMapper.updateByPrimaryKeySelective() --> Alta de anticipo con id: " + altaAnticipoAcontabilizar.getIdanticipo() + " contabilizada");
							}else {
								LOGGER.info("generarFicheroContabilidad() --> generaAsiento7() --> pysAnticipoLetradoMapper.updateByPrimaryKeySelective() --> Alta de anticipo con id: " + altaAnticipoAcontabilizar.getIdanticipo() + " no pudo ser contabilizada");
								throw new Exception("Alta de anticipo con id: " + altaAnticipoAcontabilizar.getIdanticipo() + " no pudo ser contabilizada");
							}
						}
						
					}
								
				}
				catch (Exception e) 
				{
					throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento7()",e);
				}
				
				LOGGER.info("generarFicheroContabilidad() --> generaAsiento7() --> Salida del metodo para obtener y guardar la informacion de altas de anticipo en el excel");
			
			return datosExcel;
		}
		
		//LIQUIDACION DE ANTICIPOS PARA EL COLEGIO
		private Vector<Hashtable<String, Object>> generaAsiento7A(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento7A() --> Entrada al metodo para obtener y guardar la informacion de las liquidaciones de anticipos para el colegio en el excel");
			
			String concepto 		            = "";
			String asientoContableIngresosExtra = null;
			String imp 							= null;

			try{

				List<LiquidacionAnticipoColegioItem> listaLiquidacionesAnticiposColegio = facRegistroFichContaExtendsMapper.obtenerLiquidacionesAnticiposColegios(registroFacRegistroFichConta);

				String idAnticipo ="";
				String idAnticipoAnt ="";

				//Descripcion del concepto
				concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO7A));
							
				for(int x=0;x<listaLiquidacionesAnticiposColegio.size();x++){
					
						LiquidacionAnticipoColegioItem liquidacionAnticipoColegio = listaLiquidacionesAnticiposColegio.get(x);

						asiento++;
						
						idAnticipo = String.valueOf(liquidacionAnticipoColegio.getIdanticipo());
						
						imp = UtilidadesNumeros.redondea(String.valueOf(liquidacionAnticipoColegio.getImporteanticipado()),2);
						
						String asientoCliente= obtenerAsientoContable(idInstitucion, String.valueOf(liquidacionAnticipoColegio.getIdpersona()));
						String asientoAnticiposCliente = ANTICIPOS_CLI + asientoCliente;
						
						String ctaClientes = CONTABILIDAD_CAJA_ANTICIPOS;
						
						
						// Con el BANCOS_CODIGO, obtenemos de FAC_BANCOINSTITUCION, el numerocuenta.
					    asientoContableIngresosExtra	= CONTABILIDAD_INGRESOS_EXTRA;
										
					    Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
						
						// Escribimos 1� apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", liquidacionAnticipoColegio.getFechaefectiva());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", liquidacionAnticipoColegio.getIdanticipo());
						datosHashtable.put("CUENTA", asientoContableIngresosExtra);
						datosHashtable.put("DEBE", imp);
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoAnticiposCliente);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	
										
						// Escribimos 2� asiento
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", liquidacionAnticipoColegio.getFechaefectiva());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", liquidacionAnticipoColegio.getIdanticipo());
						datosHashtable.put("CUENTA", asientoAnticiposCliente);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoContableIngresosExtra);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
					}
					
			
				}
				catch (Exception e) {
					throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento7A()",e);
				}
				
				LOGGER.info("generarFicheroContabilidad() --> generaAsiento7A() --> Salida del metodo para obtener y guardar la informacion de liquidaciones de anticipos colegio en el excel");
				
				return datosExcel;
		}
		
		//ANTICIPOS DE PRODUCTOS Y SERVICIOS - PAGOS POR CAJA
		private Vector<Hashtable<String, Object>> generaAsiento7B(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento7B() --> Entrada al metodo para obtener y guardar la informacion de los anticipos de pys - pagos por caja en el excel");

			String concepto 		        = "";
			String conceptoAnticipo 		= "";
			String asientoContable 	        = null;
			String select 		            = null; 
			String imp                      = null;
			
			List<FacPagosporcaja> listaAnticiposPySAcontabilizar = new ArrayList<FacPagosporcaja>();

			try {
				
				List<AnticiposPySItem> listaAnticiposPyS = facRegistroFichContaExtendsMapper.obtenerAnticiposPyS(registroFacRegistroFichConta);
					
				String idFactura ="";
				String idFacturaAnt ="";
				
				concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO3));
				conceptoAnticipo = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO3_2));
					
				for(int x=0;x<listaAnticiposPyS.size();x++) {
						
						asiento++;
						
						AnticiposPySItem anticipoPyS = listaAnticiposPyS.get(x);
						idFactura = String.valueOf(anticipoPyS.getIdfactura());
						
						imp = UtilidadesNumeros.redondea(String.valueOf(anticipoPyS.getImporte()),2);
						String confClientes = anticipoPyS.getConfdeudor();
						String ctaClientes = anticipoPyS.getCtaclientes();
						String anticipo = anticipoPyS.getAnticipo();
						
						// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
						String asientoCliente= obtenerAsientoContable(idInstitucion, String.valueOf(anticipoPyS.getIdpersona()));
						if (confClientes.equals("F")) {
						    asientoContable =  ctaClientes;
						} else {
						    asientoContable =  ctaClientes + asientoCliente;
						}
						String asientoAnticiposCliente = ANTICIPOS_CLI + asientoCliente;
						
						Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
						
						// PAGO ANTICIPADO
						// Escribimos 1 apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", anticipoPyS.getFecha());
						datosHashtable.put("CONCEPTO", conceptoAnticipo);
						datosHashtable.put("DOCUMENTO", anticipoPyS.getNumerofactura());
						datosHashtable.put("CUENTA", asientoContable);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoAnticiposCliente);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	
			
						// Escribimos 2 apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", anticipoPyS.getFecha());
						datosHashtable.put("CONCEPTO", conceptoAnticipo);
						datosHashtable.put("DOCUMENTO", anticipoPyS.getNumerofactura());
						datosHashtable.put("CUENTA", asientoAnticiposCliente);
						datosHashtable.put("DEBE", imp);
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoContable);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
						if (!idFactura.equals(idFacturaAnt)) {
						    // Solamente cuando cambia idfactura
							FacPagosporcaja anticipoPySAcontabilizar = new FacPagosporcaja();
							
							anticipoPySAcontabilizar.setContabilizado(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
							anticipoPySAcontabilizar.setIdinstitucion(Short.valueOf(idInstitucion));
							anticipoPySAcontabilizar.setIdfactura(idFactura);
							anticipoPySAcontabilizar.setUsumodificacion(Integer.valueOf(usuario));
							anticipoPySAcontabilizar.setFechamodificacion(new Date());
							
							listaAnticiposPySAcontabilizar.add(anticipoPySAcontabilizar);
							idFacturaAnt = idFactura;		
							
						}
					}
					
					if(listaAnticiposPySAcontabilizar.size()>0){
						
						for (FacPagosporcaja anticipoPySAcontabilizar : listaAnticiposPySAcontabilizar) {
							int respuestaActualizarAnticiposPySContabilizado = facPagosPorCajaMapper.updateByPrimaryKeySelective(anticipoPySAcontabilizar);
							
							if(respuestaActualizarAnticiposPySContabilizado == 1) {
								LOGGER.info("generarFicheroContabilidad() --> generaAsiento7B() --> facPagosPorCajaMapper.updateByPrimaryKeySelective() --> Anticipo PyS con id: " + anticipoPySAcontabilizar.getIdfactura() + " contabilizado");
							}else {
								LOGGER.info("generarFicheroContabilidad() --> generaAsiento7B() --> facPagosPorCajaMapper.updateByPrimaryKeySelective() --> Anticipo PyS con id: " + anticipoPySAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
								throw new Exception("Anticipo PyS con id: " + anticipoPySAcontabilizar.getIdfactura() + " no pudo ser contabilizado");
							}
						}
						
					}
					
				}
				catch (Exception e) 
				{
					throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento7B()",e);
				}
				
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento7B() --> Salida del metodo para obtener y guardar la informacion de anticipos PyS en el excel");
			return datosExcel;
		}
		
		//PAGO POR BANCO: ABONO
		private Vector<Hashtable<String, Object>> generaAsiento2B(Vector<Hashtable<String, Object>> datosExcel, FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception{
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento2B() --> Entrada al metodo para obtener y guardar la informacion de los pagos por banco (abonos) en el excel");
			
			String concepto 			= "";
			String asientoContable 		= null; 
			String asientoContableBanco = null;
			String imp 					= null;
			String idFactAnt="";
			String idAbonoAnt="";
			String ctaproductservAnt="";
			
			try{
			
				List<PagoPorBancoAbonoItem> listaPagosPorBancoAbonos = facRegistroFichContaExtendsMapper.obtenerPagosPorBancoAbono(registroFacRegistroFichConta);
				
			
				for(int x=0;x<listaPagosPorBancoAbonos.size();x++){

					PagoPorBancoAbonoItem pagoPorBancoAbonoItem = listaPagosPorBancoAbonos.get(x);
					
					imp = String.valueOf(pagoPorBancoAbonoItem.getImporte());
				
					//Para que no salgan asientos repetidos (sal�a uno por l�nea de factura del mismo CTAPRODUCTOSERVICIO y no procede porque hay que mostrar l�neas de pagos de abonos no de facturas)
					if((idFactAnt.equals(pagoPorBancoAbonoItem.getIdfactura())==false)||
						   (idAbonoAnt.equals(String.valueOf(pagoPorBancoAbonoItem.getIdabono()))==false)||
						   (ctaproductservAnt.equals(pagoPorBancoAbonoItem.getCtaproductoservicio())==false))
					{
										
						String confIngresos= pagoPorBancoAbonoItem.getConfingresos();
						String ctaIngresos= pagoPorBancoAbonoItem.getCtaingresos();
						String confClientes= pagoPorBancoAbonoItem.getConfdeudor();
						String ctaClientes= pagoPorBancoAbonoItem.getCtaclientes();
											
						String asientoIngresos="";
						String asientoClientes="";
						
						if (confClientes.equals("F")) {
						    asientoClientes =  ctaClientes;
						} else {
						    asientoClientes =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(pagoPorBancoAbonoItem.getIdpersona()));
						}
						
						if (confIngresos.equals("F")) {
						    asientoIngresos =  ctaIngresos;
						} else if (confIngresos.equals("C")) {
						    asientoIngresos =  ctaIngresos + obtenerAsientoContable(idInstitucion, String.valueOf(pagoPorBancoAbonoItem.getIdpersona()));
						} else {
						    asientoIngresos =  ctaIngresos + pagoPorBancoAbonoItem.getCtaproductoservicio();
						}
						// Descripcion del concepto
						concepto = UtilidadesString.sustituirParaExcel(UtilidadesString.getMensajeIdioma(idioma, SigaConstants.CONCEPTO_ASIENTO2B)+ ": "+ pagoPorBancoAbonoItem.getNumerofactura());
						
						asiento++;
		
						Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
						
						// Escribimos 1 apunte
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", pagoPorBancoAbonoItem.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", pagoPorBancoAbonoItem.getNumeroabono());
						datosHashtable.put("CUENTA", asientoIngresos);
						datosHashtable.put("DEBE", imp);
						datosHashtable.put("HABER", "0");
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoClientes);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);					

						// Escribimos 2� asiento
						datosHashtable.clear();
						datosHashtable.put("ASIENTO", asiento);
						datosHashtable.put("FECHA", pagoPorBancoAbonoItem.getFecha());
						datosHashtable.put("CONCEPTO", concepto);
						datosHashtable.put("DOCUMENTO", pagoPorBancoAbonoItem.getNumeroabono());
						datosHashtable.put("CUENTA", asientoClientes);
						datosHashtable.put("DEBE", "0");
						datosHashtable.put("HABER", imp);
						datosHashtable.put("BASEIMPONIBLE", "");
						datosHashtable.put("IVA", "");
						datosHashtable.put("CONTRAPARTIDA", asientoIngresos);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	
						
						idFactAnt = pagoPorBancoAbonoItem.getIdfactura();
						idAbonoAnt = String.valueOf(pagoPorBancoAbonoItem.getIdabono());
						ctaproductservAnt = pagoPorBancoAbonoItem.getCtaproductoservicio();
						
					}
					
					//Actualizamos contabilizado
					FacAbonoincluidoendisquete facAbonoIncluidoEnDisquete = new FacAbonoincluidoendisquete();
					
					facAbonoIncluidoEnDisquete.setIdinstitucion(Short.valueOf(idInstitucion));
					facAbonoIncluidoEnDisquete.setIdabono(Long.valueOf(pagoPorBancoAbonoItem.getIdabono()));
					
					facAbonoIncluidoEnDisquete.setContabilizado(SigaConstants.FACTURA_ABONO_CONTABILIZADA);
					facAbonoIncluidoEnDisquete.setUsumodificacion(Integer.valueOf(usuario));
					facAbonoIncluidoEnDisquete.setFechamodificacion(new Date());
					
					int respuestaActualizarPagosPorBancoAbonosContabilizado = facAbonoincluidoendisqueteExtendsMapper.updateByPrimaryKeySelective(facAbonoIncluidoEnDisquete);
					
					if(respuestaActualizarPagosPorBancoAbonosContabilizado == 1) {
						LOGGER.info("generarFicheroContabilidad() --> generaAsiento2B() --> facAbonoincluidoendisqueteExtendsMapper.updateByPrimaryKeySelective() --> Pago por banco (abono) con id: " + pagoPorBancoAbonoItem.getIdabono() + " contabilizado");
					}else {
						LOGGER.info("generarFicheroContabilidad() --> generaAsiento2B() --> facAbonoincluidoendisqueteExtendsMapper.updateByPrimaryKeySelective() --> Pago por banco (abono) con id: " + pagoPorBancoAbonoItem.getIdabono() + " no pudo ser contabilizado");
						throw new Exception("Pago por banco (abono) con id: " + pagoPorBancoAbonoItem.getIdabono() + " no pudo ser contabilizado");
					}
				}

			}
			catch (Exception e) {
				throw new Exception("Error en generarFicheroContabilidad() --> generaAsiento2B()",e);
			}
			
			LOGGER.info("generarFicheroContabilidad() --> generaAsiento2B() --> Salida del metodo para obtener y guardar la informacion de pagos por banco (abono) en el excel");
			
			return datosExcel;
		}	

		
		private String obtenerAsientoContable(String idInstitucion, String idpersona) throws Exception
		{
			String asientoContable = "";
			            
			try
			{
				CenClienteKey cenClienteKey = new CenClienteKey();
				cenClienteKey.setIdinstitucion(Short.valueOf(idInstitucion));
				cenClienteKey.setIdpersona(Long.valueOf(idpersona));	
				
				// Con el IDPERSONA E IDINSTITUCION, obtenemos de CEN_CLIENTE, el asiento contable.
				CenCliente cenCliente = cenClienteMapper.selectByPrimaryKey(cenClienteKey);
				if(cenCliente == null)
				    throw new Exception("No se ha encontrado la cuenta contable en cen_cliente");
				asientoContable =  cenCliente.getAsientocontable();
			}
			catch(Exception e)
			{
			    throw new Exception("Error al obtener asiento contable",e);
			}
			return asientoContable;
		}
		
		private String obtenerAsientoContableBanco(String idInstitucion, String bancosCodigo) throws Exception
		{
			String asientoContable = "";
		
			try
			{
				FacBancoinstitucionKey facBancoInstitucionKey = new FacBancoinstitucionKey();
				facBancoInstitucionKey.setBancosCodigo(bancosCodigo);
				facBancoInstitucionKey.setIdinstitucion(Short.valueOf(idInstitucion));
				
				FacBancoinstitucion facBancoInstitucion = facBancoInstitucionMapper.selectByPrimaryKey(facBancoInstitucionKey);
			
				if(facBancoInstitucion == null)
				    throw new Exception("No se ha encontrado la cuenta contable en fac_bancoinstitucion");
				if(facBancoInstitucion.getAsientocontable() != null) {
					asientoContable =  facBancoInstitucion.getAsientocontable();
				}
			}
			catch(Exception e)
			{
			    throw new Exception("Error al obtener asiento contable banco",e);
			}
			return asientoContable;
		}

		private Hashtable<String, Object> checkDatos (int asiento, Hashtable<String, Object> datos)
		{						
			
			Double importeDebe = Double.valueOf(datos.get("DEBE").toString());
			Double importeHaber = Double.valueOf(datos.get("HABER").toString());
			Double baseImponible = null;
			if(!datos.get("BASEIMPONIBLE").equals("")) {
				baseImponible = Double.valueOf(datos.get("BASEIMPONIBLE").toString());
			}
			
			String baseImp = "";
			if(importeDebe<0.0){
				importeHaber = -1 * importeDebe;
				importeDebe = 0.0;
				datos.remove("DEBE");
				datos.put("DEBE", importeDebe);
			}
			if(importeHaber<0.0){
				importeDebe = -1 * importeHaber;
				importeHaber = 0.0;
				datos.remove("HABER");
				datos.put("HABER", importeHaber);
			}
			if(baseImponible!=null&&baseImponible<0.0){
				baseImponible = -1 * baseImponible;
				baseImp = baseImponible.toString().replace('.', ',');
				datos.remove("baseImp");
				datos.put("BASEIMPONIBLE", baseImp);
			}else{
				if(baseImponible != null) {
					baseImp = baseImponible.toString().replace('.', ',');
				}else {
					baseImp = "";
				}
				datos.remove("BASEIMPONIBLE");
				datos.put("BASEIMPONIBLE", baseImp);
			}		
				
			// Fecha
			if(datos.get("FECHA") == null) {
				datos.remove("FECHA");
				datos.put("FECHA", "");
			}
		
			// Cuenta
			if(datos.get("CUENTA") == null) {
				datos.remove("CUENTA");
				datos.put("CUENTA", "");
			}
				
			// Concepto
			if(datos.get("CONCEPTO") == null) {
				datos.remove("CONCEPTO");
				datos.put("CONCEPTO", "");
			}
			
			// Documento 
			if(datos.get("DOCUMENTO") == null) {
				datos.remove("DOCUMENTO");
				datos.put("DOCUMENTO", "");
			}
				
			// Debe
			datos.remove("DEBE");
			datos.put("DEBE", importeDebe.toString().replace('.', ','));
				
			// Haber
			datos.remove("HABER");
			datos.put("HABER", importeHaber.toString().replace('.', ','));
				
			// IVA
			if(datos.get("IVA") == null) {
				datos.remove("IVA");
				datos.put("IVA", "");
			}
				
			// Contrapartida
			if(datos.get("CONTRAPARTIDA") == null) {
				datos.remove("CONTRAPARTIDA");
				datos.put("CONTRAPARTIDA", "");
			}
				
			return datos;
		}

		//FIN METODOS FICHERO CONTABILIDAD

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO eliminarAbonoSJCSCaja(EstadosPagosItem item, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		deleteResponseDTO.setError(error);

		// Conseguimos información del usuario logeado
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);

		LOGGER.info("insertarEstadosPagos() -> Entrada al servicio para crear una entrada al historico de factura");

		if (usuario != null && item.getIdAccion().equalsIgnoreCase("4")) {

			//Recuperar abono
			FacAbonoKey abonoKey = new FacAbonoKey();
			abonoKey.setIdabono(Long.valueOf(item.getIdAbono()));
			abonoKey.setIdinstitucion(usuario.getIdinstitucion());

			FacAbono abonoSJCS = facAbonoExtendsMapper.selectByPrimaryKey(abonoKey);

			//Elimina el pago
			List<FcsPagosEstadospagos> estadosAbono = new ArrayList<>();

			FcsPagosEstadospagosExample fcsPagosEstadospagosExample = new FcsPagosEstadospagosExample();
			fcsPagosEstadospagosExample.createCriteria().andIdpagosjgEqualTo(abonoSJCS.getIdpagosjg()).
				andIdinstitucionEqualTo(abonoSJCS.getIdinstitucion());
			fcsPagosEstadospagosExample.setOrderByClause("IDESTADOPAGOSJG");

			estadosAbono = fcsPagosEstadospagosMapper.selectByExample(fcsPagosEstadospagosExample);

			if(estadosAbono.get(estadosAbono.size()-1).getIdestadopagosjg() != 30){
				//No se ha pagado
			}


			deleteResponseDTO.setStatus(HttpStatus.OK.toString());
		}

		LOGGER.info("insertarEstadosPagos() -> Salida del servicio para crear una entrada al historico de factura");

		return deleteResponseDTO;
	}

	@Override
	public EstadosAbonosDTO getEstadosAbonosSJCS(String idAbono, HttpServletRequest request) throws Exception {
		EstadosAbonosDTO estadosPagosDTO = new EstadosAbonosDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info(
				"FacturacionPySServiceImpl.getEstadosAbonosSJCS() -> Entrada al servicio para obtener el historico del abono SJCS");

		// Conseguimos información del usuario logeado
		usuario = authenticationProvider.checkAuthentication(request);

		if (usuario != null) {
			LOGGER.info("facPagoabonoefectivoExtendsMapper.getEstadosAbonosSJCS() -> obteniendo el historico del abono SJCS");

			List<EstadosAbonosItem> result = facPagoabonoefectivoExtendsMapper.getEstadosAbonosSJCS(idAbono,
					usuario.getIdinstitucion(), usuario.getIdlenguaje());

			// Se calcula el importe pendiente para cada una de las líneas
			if (result != null && result.size() > 1) {
				Float total = result.get(0).getImportePendiente();
				for (int i = 0; i < result.size(); i++) {
					Float movimiento = result.get(i).getMovimiento();
					if (total != null && movimiento != null) {
						total -= movimiento;
						result.get(i).setImportePendiente(UtilidadesNumeros.redondea(total, 2));
					}
				}
			}

			estadosPagosDTO.setEstadosAbonosItems(result);
		}

		LOGGER.info(
				"FacturacionPySServiceImpl.getEstadosAbonosSJCS() -> Salida del servicio  para obtener el historico del abono sjcs");

		return estadosPagosDTO;
	}
	
	@Override
	public ResponseFileDTO generateExcel(TarjetaPickListSerieDTO etiquetas, HttpServletRequest request) {

		LOGGER.info("generateExcel() -> Entrada del servicio para generar el excel de los colegiados");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		ResponseFileDTO response = new ResponseFileDTO();
		File excel = null;
		List<Map<String, Object>> result = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"generateExcel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"generateExcel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				String sentencia = selectColegiados(idInstitucion,etiquetas.getIdSerieFacturacion(), usuario.getIdlenguaje());

				LOGGER.info(
						"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Entrada a conConsultasExtendsMapper para obtener lista de colegiados");

				result = conConsultasExtendsMapper.ejecutarConsultaString(sentencia);

				LOGGER.info(
						"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Salida a conConsultasExtendsMapper para obtener lista de colegiados");

				if (result != null && result.size() > 0) {

					try {
						Workbook workBook = crearExcel(result);
						
						// Obtenemos la ruta temporal
						GenPropertiesKey key = new GenPropertiesKey();
						key.setFichero(SigaConstants.FICHERO_SIGA);
						key.setParametro(SigaConstants.parametroRutaSalidaInformes);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Entrada a genPropertiesMapper para obtener la ruta donde generar el excel");

						GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Salida a genPropertiesMapper para obtener la ruta donde generar el excel");

						String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion
								+ SigaConstants.pathSeparator + SigaConstants.carpetaTmp;

						File aux = new File(rutaTmp);
						// creo directorio si no existe
						aux.mkdirs();

						GenDiccionarioKey keyDiccionario = new GenDiccionarioKey();
						keyDiccionario.setIdlenguaje(usuario.getIdlenguaje());
						keyDiccionario.setIdrecurso("censo.nombre.fichero.generarexcel");

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Entrada a genPropertiesMapper para obtener la ruta donde generar el excel");

						GenDiccionario nombreFicherosSalida = genDiccionarioMapper.selectByPrimaryKey(keyDiccionario);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Salida a genPropertiesMapper para obtener la ruta donde generar el excel");

						String nombreFichero = nombreFicherosSalida.getDescripcion() + new Date().getTime() + ".xlsx";
						excel = new File(rutaTmp, nombreFichero);
						FileOutputStream fileOut;

						fileOut = new FileOutputStream(rutaTmp + SigaConstants.pathSeparator + nombreFichero);
						workBook.write(fileOut);
						fileOut.close();
						workBook.close();

						response.setFile(excel);
						response.setResultados(true);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					response.setResultados(false);
				}

			}

		}

		LOGGER.info("generateExcel() -> Salida del servicio para generar el excel de los colegiados");

		return response;

	}

	private Workbook crearExcel(List<Map<String, Object>> result) {

		LOGGER.info("crearExcel() -> Entrada del servicio para crear el excel con los datos de los colegiados");

		// Creamos el libro de excel
		Workbook workbook = new SXSSFWorkbook(EXCEL_ROW_FLUSH);
		Sheet sheet = workbook.createSheet("Query");

		// Le aplicamos estilos a las cabeceras
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		// headerFont.setItalic(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		// Recorremos el map y vamos metiendo celdas
		List<String> columnsKey = new ArrayList<String>();
		int rowNum = 1;
		int index = 0;
		Row row = null;

		Map<Integer, CellStyle> mapaEstilos = new HashMap<Integer, CellStyle>();

		CellStyle cellStyleNum = workbook.createCellStyle();
		cellStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
		
		CellStyle cellStyleString = workbook.createCellStyle();
		cellStyleString.setAlignment(CellStyle.ALIGN_LEFT);
		
		Object campo = null;
		XSSFRichTextString textCell = null;
		
		if (result.size() > 0) {
			for (String value : result.get(0).keySet()) {
				Cell cell = headerRow.createCell(index);
				cell.setCellValue(value);
				cell.setCellStyle(headerCellStyle);
				columnsKey.add(value);
				index++;
			}

			for (Map<String, Object> map : result) {
				
				if (map != null) {
	
					row = sheet.createRow(rowNum++);
					int cell = 0;
	
					
					for (int j = 0; j < columnsKey.size(); j++) {
						campo = map.get(columnsKey.get(j).trim());
						
						if (campo == null || campo.toString().trim() == "") {
							row.createCell(cell).setCellValue("");
						} else {
							Cell celda = row.createCell(cell);
							if (campo instanceof Number) {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleNum);
								}
								celda.setCellType(Cell.CELL_TYPE_NUMERIC);
								celda.setCellValue(Double.parseDouble(campo.toString()));
								
							} else if (campo instanceof Date) {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleString);
								}
								
								CreationHelper creationHelper = workbook.getCreationHelper();
								
								celda.setCellValue((Date) campo);
								
								CellStyle style1 = workbook.createCellStyle();
								style1.setDataFormat(creationHelper.createDataFormat().getFormat(
										"dd/mm/yyyy hh:mm"));
								celda.setCellStyle(style1);
								
							} else {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleString);
								}
								
								celda.setCellType(Cell.CELL_TYPE_STRING);
								textCell = new XSSFRichTextString(campo.toString());
								celda.setCellValue(textCell);
							}
						}
						cell++;
						
					}
				}
			}

			for (int i = 0; i < index; i++) {
				//sheet.autoSizeColumn(j);
				if (mapaEstilos.containsKey(i)) {
					sheet.setDefaultColumnStyle(i, mapaEstilos.get(i));
				}
			}
		}

		LOGGER.info("crearExcel() -> Salida del servicio para crear el excel con los datos de los colegiados");

		return workbook;

	}

	private String selectColegiados(Short idInstitucion,String etiquetas, String idLenguaje) {

		LOGGER.info(
				"selectColegiados() -> Entrada del servicio para obtener la sentencia para obtener la lista de colegiados");

		SQL sql = new SQL();

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados,
		// tendremos que preparar el filtro de instituciones
		String instituciones = "";
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("f_siga_getncol_ncom(col.idinstitucion,col.idpersona) AS NcolNcom");
        sql.SELECT("decode (col.comunitario,'1','SI','0','NO') AS Comunitario");
        sql.SELECT("per.nombre AS Nombre");
        sql.SELECT("per.apellidos1 AS Apellido1");
        sql.SELECT("per.apellidos2 AS Apellido2");
        sql.SELECT("f_siga_getrecurso(tip.descripcion,1) AS TipoIdentificacion");
        sql.SELECT("per.nifcif AS NifCif");
        sql.SELECT("Per.Fechanacimiento AS FechadeNacimiento");
        sql.SELECT("f_siga_getrecurso(est.descripcion,1) AS EstadoCivil");
        sql.SELECT("per.naturalde AS Naturalde");
        sql.SELECT("decode (per.fallecido,'1','SI','NO') AS Fallecido");
        sql.SELECT("decode (per.sexo,'M','MUEJR','HOMBRE') AS Sexo");
        sql.SELECT("Cli.Fechaalta AS Fechaalta");
        sql.SELECT("f_siga_getrecurso(tra.descripcion,1) AS Tratamiento");
        sql.SELECT("cli.caracter AS Caracter");
        sql.SELECT("cli.publicidad AS Publicidad");
        sql.SELECT("cli.comisiones AS Comisiones");
        sql.SELECT("cli.guiajudicial AS GuiaJudicial");
        sql.SELECT("cli.abonosbanco AS AbonosBanco");
        sql.SELECT("cli.cargosbanco AS CargosBanco");
        sql.SELECT("decode (cli.idlenguaje,'1','Castellano','2','Catalá','3','Euskera','4','Galego') AS Lenguaje");
        sql.SELECT("cli.fotografia AS Fotografia");
        sql.SELECT("cli.asientocontable AS AsientoContable");
        sql.SELECT("Cli.Fechacarga AS FechaCarga");
        sql.SELECT("cli.letrado AS Letrado");
        sql.SELECT("Cli.Fechaactualizacion AS FechaActualizacion");
        sql.SELECT("Cli.Fechaexportcenso AS FechaExportCenso");
        sql.SELECT("cli.noenviarrevista AS NoenviaRevista");
        sql.SELECT("cli.noaparecerredabogacia AS NoapareceRedAbogacia");
        sql.SELECT("Col.Fechapresentacion AS FechaPresentacion");
        sql.SELECT("Col.Fechaincorporacion AS FechaIncorporacion");
        sql.SELECT("col.indtitulacion AS IndTitulacion");
        sql.SELECT("col.jubilacioncuota AS JubilacionCuota");
        sql.SELECT("decode (col.situacionejercicio,'1','Alta','0','Baja') AS SituacionEjercicio");
        sql.SELECT("decode (col.situacionresidente,'1','SI','0','NO') AS SituacionResidente");
        sql.SELECT("col.situacionempresa AS SituacionEmpresa");
        sql.SELECT("Col.Fechajura AS FechaJura");
        sql.SELECT("Col.Fechatitulacion AS FechaTitulacion");
        sql.SELECT("decode (col.otroscolegios,'1','SI','0','NO') as OtrosColegios");
        sql.SELECT("Col.Fechadeontologia AS FechaDeontologia");
        sql.SELECT("Col.Fechamovimiento AS FechaMovimiento");
        sql.SELECT("f_siga_getrecurso(ts.nombre,1) AS TipoSeguro");
        sql.SELECT("col.cuentacontablesjcs AS CuentaContableSJCS");
        sql.SELECT("decode (f_siga_gettipocliente(col.idpersona,col.idinstitucion,sysdate),'10','No Ejerciente','20','Ejerciente','30','Baja Colegial','40','Inhabilitacion','50','Suspension Ejercicio','60','Baja por Deceso','Baja por Deceso') AS EstadoColegial");
        sql.SELECT("f_siga_getfechaestadocolegial(col.idpersona,col.idinstitucion,sysdate)   AS FechaEstado");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,1) AS Domicilio");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,2) AS CodigoPostal");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,11) AS Telefono1");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,12) AS Telefono2");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,13) AS Movil");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,14) AS Fax1");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,15) AS Fax2");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,16) AS CorreoElectronico");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,17) AS PaginaWeb");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,3) AS Poblacion");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,4) AS Provincia");
        sql.SELECT("f_siga_getdireccioncliente1(col.idinstitucion,col.idpersona,3,5) AS Pais");
        sql.SELECT("(SELECT LISTAGG( UPPER(f_siga_getrecurso(gc.nombre,"+idLenguaje+")), ', ') as Etiquetas "
        		+ "FROM CEN_GRUPOSCLIENTE gc "
        		+ "inner join FAC_TIPOCLIINCLUIDOENSERIEFAC ga on ( gc.idgrupo = ga.idgrupo and gc.idinstitucion = ga.idinstitucion) "
        		+ "INNER JOIN CEN_GRUPOSCLIENTE_CLIENTE ce on (gc.idgrupo = ce.idgrupo and gc.idinstitucion = ce.idinstitucion) "
        		+ "WHERE gc.idinstitucion=col.idinstitucion and ga.idseriefacturacion = tfa.idseriefacturacion "
        		+ "and ce.idpersona = col.idpersona group by ce.idpersona) as Etiquetas ");
        
		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");
		
		//PARA ETIQUETAS
		sql.INNER_JOIN("cen_gruposcliente_cliente gcli on (col.idpersona = gcli.idpersona and col.idinstitucion = gcli.idinstitucion)");	
		sql.INNER_JOIN("FAC_TIPOCLIINCLUIDOENSERIEFAC tfa on (gcli.idgrupo = tfa.idgrupo and gcli.idinstitucion = tfa.idinstitucion)");
		sql.INNER_JOIN("CEN_GRUPOSCLIENTE cgc on (tfa.idinstitucion = cgc.idinstitucion and cgc.idgrupo = tfa.idgrupo)");
		
		
		
		if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
			if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
				//sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
			}
			else{
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli.idinstitucion)");
				//sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli2.idinstitucion)");
			}
			
		}else {
            sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and cli.idinstitucion =  '"+ idInstitucion + "')");
			sql.INNER_JOIN("cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
		}
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion"
						+ " and datcol.fechaestado < sysdate))");
		
		sql.INNER_JOIN("cen_tipoidentificacion tip on (per.idtipoidentificacion = tip.idtipoidentificacion)");
		sql.LEFT_OUTER_JOIN("cen_estadocivil est on ( est.idestadocivil = per.idestadocivil)");
		sql.LEFT_OUTER_JOIN(" cen_tratamiento tra on (tra.idtratamiento = cli.idtratamiento)");
		sql.LEFT_OUTER_JOIN(" cen_tiposseguro ts  on ( col.idtiposseguro = ts.idtiposseguro)") ;     
		

		if(!instituciones.equals("")) {
			sql.WHERE("COL.IDINSTITUCION IN (" + instituciones + ")");
		} else {
			if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
				if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
					sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
				}
				else{
                    sql.WHERE("inst.cen_inst_IDINSTITUCION = '" + idInstitucion + "'");

				}
				
			}
		}
		
		sql.WHERE("per.idtipoidentificacion not in '20'");
		sql.WHERE("tfa.idseriefacturacion IN (" + etiquetas + ")");		
		GenParametrosExample genParametrosExample = new GenParametrosExample();
		
		List<Short> idInstituciones = new ArrayList<>();
		idInstituciones.add(idInstitucion);
		idInstituciones.add(SigaConstants.IDINSTITUCION_0_SHORT);
		
		genParametrosExample.createCriteria().andIdinstitucionIn(idInstituciones)
		.andParametroEqualTo("EXPORTAR_COLEGIADOS_ACOGIDOS_A_LOPD");
		
		genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
		
		
		List<GenParametros> genParametros = genParametrosExtendsMapper.selectByExample(genParametrosExample);
		
		if(genParametros != null && genParametros.size() > 0) {
			
			GenParametros parametro = genParametros.get(0);
			
			if(parametro.getValor().equals("N")) {
				sql.WHERE("(decode(cli.noaparecerredabogacia,null,0,cli.noaparecerredabogacia) <> 1)");
			}
		}

		LOGGER.info(
				"selectColegiados() -> Salida del servicio para obtener la sentencia para obtener la lista de colegiados");
		
		sql.ORDER_BY("NOMBRE");
		LOGGER.info(sql.toString());
		return sql.toString();

	}

	public ResponseFileDTO generateExcelAbonos(FacAbonoItem facAbonosItem, HttpServletRequest request)
			throws Exception {

		LOGGER.info("generateExcel() -> Entrada del servicio para generar el excel de los colegiados");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		ResponseFileDTO response = new ResponseFileDTO();
		File excel = null;
		List<Map<String, Object>> result = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"generateExcel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"generateExcel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);				

				String sentencia = selectAbonos(facAbonosItem,idInstitucion.toString(),usuario.getIdlenguaje());

				LOGGER.info(
						"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Entrada a conConsultasExtendsMapper para obtener lista de colegiados");

				result = conConsultasExtendsMapper.ejecutarConsultaString(sentencia);

				LOGGER.info(
						"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Salida a conConsultasExtendsMapper para obtener lista de colegiados");

				if (result != null && result.size() > 0) {

					try {
						Workbook workBook = crearExcel(result);
						
						// Obtenemos la ruta temporal
						GenPropertiesKey key = new GenPropertiesKey();
						key.setFichero(SigaConstants.FICHERO_SIGA);
						key.setParametro(SigaConstants.parametroRutaSalidaInformes);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Entrada a genPropertiesMapper para obtener la ruta donde generar el excel");

						GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Salida a genPropertiesMapper para obtener la ruta donde generar el excel");

						String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion
								+ SigaConstants.pathSeparator + SigaConstants.carpetaTmp;

						File aux = new File(rutaTmp);
						// creo directorio si no existe
						aux.mkdirs();

						String nombreFichero = "ListaAbonosSJCS" + new Date().getTime() + ".xlsx";
						excel = new File(rutaTmp, nombreFichero);
						FileOutputStream fileOut;

						fileOut = new FileOutputStream(rutaTmp + SigaConstants.pathSeparator + nombreFichero);
						workBook.write(fileOut);
						fileOut.close();
						workBook.close();

						response.setFile(excel);
						response.setResultados(true);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					response.setResultados(false);
				}

			}

		}

		LOGGER.info("generateExcel() -> Salida del servicio para generar el excel de los colegiados");

		return response;


	}

	private String selectAbonos(FacAbonoItem facAbonoItem, String idInstitucion , String idLenguaje) {
		
			SQL sql = new SQL();
			SQL sqlTotal = new SQL();
			SQL transferencia = new SQL();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			 
			sql.SELECT("A.NUMEROABONO, A.FECHA as FECHAEMISION, PA.nombre AS PAGOSJCS");
			sql.SELECT("nvl(nvl(col.ncolegiado,col.ncomunitario),p.nifcif) NCOLIDENT, nvl(p.apellidos1 || ' ' || nvl(p.apellidos2, '') || ', ' || p.nombre, p.nombre) CLIENTE");			
			sql.SELECT("CASE WHEN A.IDPERSONA = A.IDPERORIGEN THEN 'NO' ELSE 'SI' END AS SOCIEDAD");
			sql.SELECT("A.IMPTOTAL as IMPORTETOTAL, A.IMPPENDIENTEPORABONAR AS IMPPENDIENTE");
			sql.SELECT("GEN.DESCRIPCION AS ESTADO");
			
			sql.FROM("FAC_ABONO A");
			sql.INNER_JOIN("FCS_PAGOSJG PA on A.IDPAGOSJG = PA.IDPAGOSJG AND A.idinstitucion = PA.idinstitucion");
			sql.INNER_JOIN("FCS_FACTURACIONJG  F ON (PA.IDFACTURACION = F.IDFACTURACION AND PA.IDINSTITUCION = F.IDINSTITUCION) ");
			sql.INNER_JOIN("FCS_FACT_GRUPOFACT_HITO G ON (G.IDINSTITUCION = F.IDINSTITUCION AND G.IDFACTURACION = F.IDFACTURACION)");
			sql.INNER_JOIN("CEN_CLIENTE C ON (C.IDPERSONA = A.IDPERSONA AND C.IDINSTITUCION = A.IDINSTITUCION)");
			sql.INNER_JOIN("CEN_PERSONA P ON (P.IDPERSONA = A.IDPERSONA)");
			sql.LEFT_OUTER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = P.IDPERSONA AND COL.IDINSTITUCION = A.IDINSTITUCION)");
			sql.LEFT_OUTER_JOIN("FAC_ESTADOABONO EA ON (EA.IDESTADO = A.ESTADO)");
			sql.LEFT_OUTER_JOIN("GEN_RECURSOS GEN ON (EA.DESCRIPCION = GEN.IDRECURSO  AND GEN.IDLENGUAJE = "+ idLenguaje +")");
			
			if(facAbonoItem.getNumeroAbono() != null) sql.WHERE("A.NUMEROABONO LIKE '%" + facAbonoItem.getNumeroAbono() + "%'");
			
			if(facAbonoItem.getIdPersona() != null ) sql.WHERE("A.IDPERSONA LIKE '%" +facAbonoItem.getIdPersona() + "%'");
			
			if(facAbonoItem.getEstado() != 0 ) sql.WHERE("A.ESTADO = " + facAbonoItem.getEstado());
			
			if(facAbonoItem.getForma()!=null && (facAbonoItem.getForma().equalsIgnoreCase("E") || facAbonoItem.getForma().equalsIgnoreCase("A"))) {
				sql.WHERE("A.IMPTOTALABONADOEFECTIVO > 0");
		    }
		    if(facAbonoItem.getForma()!=null && (facAbonoItem.getForma().equalsIgnoreCase("B") || facAbonoItem.getForma().equalsIgnoreCase("A"))) {
		        	sql.WHERE("A.IMPTOTALABONADOPORBANCO > 0");
		    }
		        
		    if(facAbonoItem.getNumColegiado() != null ) sql.WHERE("COL.NCOLEGIADO = " + facAbonoItem.getNumColegiado());
		    
		    if(facAbonoItem.getGrupoPago() != null) sql.WHERE("PA.IDFACTURACION = " + facAbonoItem.getGrupoPago());

	        if(facAbonoItem.getImporteTotalDesde() != 0 ) {
	        	sql.WHERE("A.IMPTOTAL>=to_number("+facAbonoItem.getImporteTotalDesde()+",'99999999999999999.99')");
	        }
	        if(facAbonoItem.getImporteTOtalHasta() != 0) {
	        	sql.WHERE("A.IMPTOTAL<=to_number("+facAbonoItem.getImporteTOtalHasta()+",'99999999999999999.99')");
	        }
		
	        if(facAbonoItem.getFechaEmisionDesde()!=null) {
	            String fecha = dateFormat.format(facAbonoItem.getFechaEmisionDesde());
	            sql.WHERE("A.fecha >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
	        }
	        if(facAbonoItem.getFechaEmisionHasta()!=null) {
	            String fecha = dateFormat.format(facAbonoItem.getFechaEmisionHasta());
	            sql.WHERE("A.fecha <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
	        }
	        
	        if(facAbonoItem.getContabilizada()!=null && facAbonoItem.getContabilizada().equalsIgnoreCase("S")) {
	        	sql.WHERE("A.contabilizada = 'S'");
	        }
	        if(facAbonoItem.getContabilizada()!=null && facAbonoItem.getContabilizada().equalsIgnoreCase("N")) {
	        	sql.WHERE("A.contabilizada = 'N'");
	        }
	        
	        if(facAbonoItem.getGrupoFacturacionNombre() != null) {
	        	sql.WHERE("G.IDGRUPOFACTURACION =" + facAbonoItem.getGrupoFacturacionNombre());
	        }

	        if(facAbonoItem.getNumIdentificadorSociedad() != null ) {
	        	sql.WHERE("UPPER(P.nifcif) LIKE UPPER('%"+facAbonoItem.getNumIdentificadorSociedad()+"%')");
	        }
	        if(facAbonoItem.getIdentificadorFicheroT()!=null){
	            transferencia.SELECT("IDABONO");
	            transferencia.FROM("fac_abonoincluidoendisquete");
	            transferencia.WHERE("idinstitucion = f.idinstitucion AND IDDISQUETEABONO = "+ facAbonoItem.getIdentificadorFicheroT());

	            sql.WHERE("A.IDABONO IN (" + transferencia.toString() + ")");
	        }
	        
	        if(facAbonoItem.getNombreSociedad() != null) sql.WHERE("UPPER(P.NOMBRE) LIKE UPPER('%"+facAbonoItem.getNombreSociedad() + "%')");
	        
	        if(facAbonoItem.getIdInstitucion() != null ) sql.WHERE("COL.IDINSTITUCION = " + facAbonoItem.getIdInstitucion());
	        
			sql.WHERE("A.IDINSTITUCION = " + idInstitucion);
			sql.WHERE("A.IDPAGOSJG IS NOT NULL");
			sql.ORDER_BY("A.NUMEROABONO DESC");
			sql.WHERE("ROWNUM <= 200");
			
			sqlTotal.SELECT("*");
	        sqlTotal.FROM("("+sql.toString()+")");
	        
	        LOGGER.info(sqlTotal.toString());
			return sqlTotal.toString();
		
	}
	
	
	
	
}
