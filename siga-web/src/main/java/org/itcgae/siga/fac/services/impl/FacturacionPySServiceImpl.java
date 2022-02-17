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
import java.text.DateFormat;
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
import org.itcgae.siga.DTO.fac.FaseFacturacionProgramadaDTO;
import org.itcgae.siga.DTO.fac.FaseFacturacionProgramadaItem;
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
import org.itcgae.siga.db.entities.FacEstadofactura;
import org.itcgae.siga.db.entities.FacEstadofacturaExample;
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
import org.itcgae.siga.db.entities.FacPagoabonoefectivoKey;
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
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.ModClasecomunicaciones;
import org.itcgae.siga.db.entities.ModClasecomunicacionesExample;
import org.itcgae.siga.db.entities.ModModelocomunicacion;
import org.itcgae.siga.db.entities.ModModelocomunicacionExample;
import org.itcgae.siga.db.entities.PysAnticipoletrado;
import org.itcgae.siga.db.entities.PysLineaanticipo;
import org.itcgae.siga.db.entities.PysTipoiva;
import org.itcgae.siga.db.entities.PysTipoivaExample;
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
import org.itcgae.siga.exception.FacturacionSJCSException;
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
	private ExcelHelper excelHelper;

	private static final int EXCEL_ROW_FLUSH = 1000;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO borrarCuentasBancarias(List<CuentasBancariasItem> cuentasBancarias,
			HttpServletRequest request) throws Exception {

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		LOGGER.info("borrarCuentasBancarias() -> Entrada al servicio para dar de baja las cuentas bancarias");
		
		int totalCuentasBancarias = cuentasBancarias.size();
		int cuentasBancariasEliminadas = 0;

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
					if (!enUso) {
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
						
						cuentasBancariasEliminadas++;
					}
				}
			}
		}
		
		if (cuentasBancariasEliminadas == 0) {
			throw new BusinessException(cuentasBancariasEliminadas + " cuentas eliminadas de " + totalCuentasBancarias);
		}

		deleteResponseDTO.setStatus(HttpStatus.OK.toString());
		
		Error error = new Error();
		
		error.setMessage(cuentasBancariasEliminadas + " cuentas eliminadas de " + totalCuentasBancarias);
		deleteResponseDTO.setError(error);
		LOGGER.info("borrarCuentasBancarias() -> Salida del servicio para eliminar las cuentas bancarias");

		return deleteResponseDTO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO borrarCuentasBancariasIndividual(CuentasBancariasItem cuentaBancaria,
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
			List<CuentasBancariasItem> cuentaResults = facBancoinstitucionExtendsMapper.getCuentasBancarias(cuentaBancaria.getBancosCodigo(),
					usuario.getIdinstitucion());

			if (cuentaResults != null && !cuentaResults.isEmpty()) {
				// No se puede borrar una cuenta bancaria que esta siendo utilizada
				boolean enUso = Integer.parseInt(cuentaResults.get(0).getNumUsos()) > 0;
				if (enUso) {
					throw new BusinessException("facturacionPyS.cuentasBancarias.eliminar.errorUsos");
				}

				FacBancoinstitucionKey cuentasbancariasKey = new FacBancoinstitucionKey();
				cuentasbancariasKey.setIdinstitucion(usuario.getIdinstitucion());
				cuentasbancariasKey.setBancosCodigo(cuentaBancaria.getBancosCodigo());

				// Borrado físico si numFicheros es 0
				if (Integer.parseInt(cuentaBancaria.getNumFicheros()) < 1) {
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
				
				if (cuenta.getSjcs() == true) {
					cuenta.setSjcsFiltro("si");
				} else {
					cuenta.setSjcsFiltro("no");
				}
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
				record.setComisioncuentacontable(null);
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

				items.get(0).setCuentasBanco(cenCuentasbancariasExtendsMapper.getComboCuentas(
						items.get(0).getIdDeudor() == null ? items.get(0).getIdCliente() : items.get(0).getIdDeudor(),
						usuario.getIdinstitucion().toString()));

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
					updateItem.setObservaciones(item.getObservacionesFactura().replace("<p>", "").replace("</p>", ""));
				if (item.getObservacionesFicheroFactura() != null)
					updateItem.setObservinforme(item.getObservacionesFicheroFactura().replace("<p>", "").replace("</p>", ""));
				if(updateItem.getObservaciones().length()>255 || updateItem.getObservinforme().length()>255) {
					throw new BusinessException("facturacion.facturas.numeroMaxCaracteres");
				}

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

			Boolean modificarDescripcion = getParametro("FAC", "MODIFICAR_DESCRIPCION", usuario.getIdinstitucion()).equals(SigaConstants.DB_TRUE);
			Boolean modificarImporte = getParametro("FAC", "MODIFICAR_IMPORTE_UNITARIO", usuario.getIdinstitucion()).equals(SigaConstants.DB_TRUE);
			Boolean modificarIva = getParametro("FAC", "MODIFICAR_IVA", usuario.getIdinstitucion()).equals(SigaConstants.DB_TRUE);

			FacLineafacturaKey key = new FacLineafacturaKey();
			key.setIdfactura(item.getIdFactura());
			key.setNumerolinea(Long.valueOf(item.getNumeroLinea()));
			key.setIdinstitucion(usuario.getIdinstitucion());

			FacLineafactura updateItem = facLineafacturaExtendsMapper.selectByPrimaryKey(key);

			if (modificarDescripcion && !UtilidadesString.esCadenaVacia(item.getDescripcion())) {
				updateItem.setDescripcion(item.getDescripcion().trim());
			}

			if (modificarImporte && !UtilidadesString.esCadenaVacia(item.getPrecioUnitario())) {
				updateItem.setPreciounitario(BigDecimal.valueOf(Double.parseDouble(item.getPrecioUnitario())));
			}

			if (modificarIva && !UtilidadesString.esCadenaVacia(item.getIdTipoIVA())) {
				updateItem.setIdtipoiva(Integer.valueOf(item.getIdTipoIVA()));

				PysTipoivaExample tipoivaExample = new PysTipoivaExample();
				tipoivaExample.createCriteria().andIdtipoivaEqualTo(updateItem.getIdtipoiva());

				List<PysTipoiva> pysTipoivas = pySTipoIvaExtendsMapper.selectByExample(tipoivaExample);
				if (pysTipoivas == null || pysTipoivas.isEmpty())
					throw new BusinessException("No se encuentra el tipo de IVA indicado");

				updateItem.setIva(pysTipoivas.get(0).getValor());
			}

			if (!UtilidadesString.esCadenaVacia(item.getCantidad())) {
				updateItem.setCantidad(Integer.valueOf(item.getCantidad()));
			}

			facLineafacturaExtendsMapper.updateByPrimaryKey(updateItem);

			updateResponseDTO.setId(String.valueOf(item.getIdFactura()));
		}

		LOGGER.info("guardarLineasFactura() -> Salida del servicio para guardar las lineas de una factura");

		return updateResponseDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
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

			Boolean modificarDescripcion = getParametro("FAC", "MODIFICAR_DESCRIPCION", usuario.getIdinstitucion()).equals(SigaConstants.DB_TRUE);
			Boolean modificarImporte = getParametro("FAC", "MODIFICAR_IMPORTE_UNITARIO", usuario.getIdinstitucion()).equals(SigaConstants.DB_TRUE);

			FacLineaabonoKey key = new FacLineaabonoKey();
			key.setIdabono(Long.valueOf(item.getIdFactura()));
			key.setNumerolinea(Long.valueOf(item.getNumeroLinea()));
			key.setIdinstitucion(usuario.getIdinstitucion());

			FacLineaabono updateItem = facLineaabonoExtendsMapper.selectByPrimaryKey(key);

			// Se actualiza la descripcion
			if (modificarDescripcion && !UtilidadesString.esCadenaVacia(item.getDescripcion())) {
				updateItem.setDescripcionlinea(item.getDescripcion());
			}

			if (modificarImporte && !UtilidadesString.esCadenaVacia(item.getPrecioUnitario())) {
				BigDecimal precioUnitario = new BigDecimal(item.getPrecioUnitario());
				updateItem.setPreciounitario(precioUnitario.setScale(2, RoundingMode.DOWN));
			}

			facLineaabonoExtendsMapper.updateByPrimaryKey(updateItem);

			/* Descomentar cuando el procedimiento PROC_SIGA_ACTESTADOABONO exista

			if (modificarImporte && !UtilidadesString.esCadenaVacia(item.getPrecioUnitario())) {
				Object[] param_in = new Object[3]; // Parametros de entrada del PL

				param_in[0] = usuario.getIdinstitucion();
				param_in[1] = key.getIdabono();
				param_in[2] = usuario.getIdusuario();
				String[] resultado = commons.callPLProcedureFacturacionPyS("{call PROC_SIGA_ACTESTADOABONO(?,?,?,?,?)}", 2, param_in);
				String codretorno = resultado[0];
				if (!codretorno.equals(RET_OK))
					throw new BusinessException("Error al actualizar el estado del abono");
			}

			 */

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
		File XLSFile = this.excelHelper.createExcelFile(orderList, datosVector, SigaConstants.nombreFicheroEjemplo);

		LOGGER.info("createExcelFile() -> Salida al servicio que crea la plantilla Excel");

		return XLSFile;
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

	@Override
	public FaseFacturacionProgramadaDTO getFasesFacturacionProgramada(HttpServletRequest request, String idInstitucion, String idSerieFacturacion, String idProgramacion) throws Exception {

		LOGGER.info("FacturacionPySServiceImpl.getEstadosFacturacionProgramada() --> ENTRADA al servicio para obtener las fases de una facturacion programada y su posicion dentro de ella");

		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
		FaseFacturacionProgramadaDTO faseFacturacionProgramadaDTO = new FaseFacturacionProgramadaDTO();

		if (usuario != null) {

			FacFacturacionprogramadaKey facFacturacionprogramadaKey = new FacFacturacionprogramadaKey();
			facFacturacionprogramadaKey.setIdinstitucion(Short.valueOf(idInstitucion));
			facFacturacionprogramadaKey.setIdseriefacturacion(Long.valueOf(idSerieFacturacion));
			facFacturacionprogramadaKey.setIdprogramacion(Long.valueOf(idProgramacion));
			FacFacturacionprogramada facFacturacionprogramada = facFacturacionprogramadaExtendsMapper.selectByPrimaryKey(facFacturacionprogramadaKey);

			GenDiccionarioKey genDiccionarioKey = new GenDiccionarioKey();
			genDiccionarioKey.setIdlenguaje(usuario.getIdlenguaje());
			genDiccionarioKey.setIdrecurso("factPyS.mensaje.fase.procesado");

			final String literalProcesado = genDiccionarioMapper.selectByPrimaryKey(genDiccionarioKey).getDescripcion();
			genDiccionarioKey.setIdrecurso("factPyS.mensaje.fase.pendiente");
			final String literalPendienteFase = genDiccionarioMapper.selectByPrimaryKey(genDiccionarioKey).getDescripcion();
			final String literarFase1 = "TRATAR FACTURACIÓN";
			final String literarFase2 = "TRATAR CONFIRMACIÓN";
			final String literarFase3 = "GENERAR PDFs Y ENVIAR FACTURAS PROGRAMACIÓN";
			final String literarFase4 = "GENERAR ENVÍOS FACTURAS PENDIENTES";
			final String literarFase5 = "COMPROBACIÓN TRASPASO FACTURAS";

			SimpleDateFormat sdf = new SimpleDateFormat(SigaConstants.DATEST_FORMAT_MIN);
			String fechaProgramacion = sdf.format(facFacturacionprogramada.getFechaprogramacion());

			FaseFacturacionProgramadaItem fase1 = new FaseFacturacionProgramadaItem();
			fase1.setOrden("10");
			fase1.setNombreFase(literarFase1);
			fase1.setFechaProgramacion(fechaProgramacion);
			fase1.setPuestoEnCola(literalProcesado);

			FaseFacturacionProgramadaItem fase2 = new FaseFacturacionProgramadaItem();
			fase2.setOrden("20");
			fase2.setNombreFase(literarFase2);
			fase2.setFechaProgramacion(fechaProgramacion);
			fase2.setPuestoEnCola(literalProcesado);

			FaseFacturacionProgramadaItem fase3 = new FaseFacturacionProgramadaItem();
			fase3.setOrden("30");
			fase3.setNombreFase(literarFase3);
			fase3.setFechaProgramacion(fechaProgramacion);
			fase3.setPuestoEnCola(literalProcesado);

			FaseFacturacionProgramadaItem fase4 = new FaseFacturacionProgramadaItem();
			fase4.setOrden("40");
			fase4.setNombreFase(literarFase4);
			fase4.setFechaProgramacion(fechaProgramacion);
			fase4.setPuestoEnCola(literalProcesado);

			FaseFacturacionProgramadaItem fase5 = new FaseFacturacionProgramadaItem();
			fase5.setOrden("50");
			fase5.setNombreFase(literarFase5);
			fase5.setFechaProgramacion(fechaProgramacion);
			fase5.setPuestoEnCola(literalProcesado);

			GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
			genPropertiesExample.createCriteria().andParametroEqualTo("facturacion.programacionAutomatica.maxMinutosEnEjecucion");

			Double minutos = Double.valueOf(genPropertiesMapper.selectByExample(genPropertiesExample).get(0).getValor());
			minutos = minutos / (24.0 * 60.0);

			Integer posicionFase1 = facFacturacionprogramadaExtendsMapper.getPosicionFacturacionProTratarFacturacion(Short.valueOf(idInstitucion), minutos, idSerieFacturacion, idProgramacion);

			if (posicionFase1 != null) {
				Integer numTotalFase1 = facFacturacionprogramadaExtendsMapper.getNumTotalFacturacionesProTratarFacturacion(Short.valueOf(idInstitucion), minutos);
				fase1.setPuestoEnCola(posicionFase1 + "/" + numTotalFase1);
				fase2.setPuestoEnCola(literalPendienteFase);
				fase3.setPuestoEnCola(literalPendienteFase);
				fase4.setPuestoEnCola(literalPendienteFase);
				fase5.setPuestoEnCola(literalPendienteFase);
			} else {
				Integer posicionFase2 = facFacturacionprogramadaExtendsMapper.getPosicionFacturacionProTratarConfirmacion(Short.valueOf(idInstitucion), idSerieFacturacion, idProgramacion);

				if (posicionFase2 != null) {
					Integer numTotalFase2 = facFacturacionprogramadaExtendsMapper.getNumTotalFacturacionesProTratarConfirmacion(Short.valueOf(idInstitucion));
					fase1.setPuestoEnCola(literalProcesado);
					fase2.setPuestoEnCola(posicionFase2 + "/" + numTotalFase2);
					fase3.setPuestoEnCola(literalPendienteFase);
					fase4.setPuestoEnCola(literalPendienteFase);
					fase5.setPuestoEnCola(literalPendienteFase);
				} else {
					Integer posicionFase3 = facFacturacionprogramadaExtendsMapper.getPosicionFacturacionProGenerarPDFsYenviarFacturasProgramacion(Short.valueOf(idInstitucion), idSerieFacturacion, idProgramacion, minutos);

					if (posicionFase3 != null) {
						Integer numTotalFase3 = facFacturacionprogramadaExtendsMapper.getNumTotalFacturacionesProGenerarPDFsYenviarFacturasProgramacion(Short.valueOf(idInstitucion), minutos);
						fase1.setPuestoEnCola(literalProcesado);
						fase2.setPuestoEnCola(literalProcesado);
						fase3.setPuestoEnCola(posicionFase3 + "/" + numTotalFase3);
						fase4.setPuestoEnCola(literalPendienteFase);
						fase5.setPuestoEnCola(literalPendienteFase);
					} else {

						Integer posicionFase4 = facFacturacionprogramadaExtendsMapper.getPosicionFacturacionProGenerarEnviosFacturasPendientes(Short.valueOf(idInstitucion), idSerieFacturacion, idProgramacion, minutos);

						if (posicionFase4 != null) {
							Integer numTotalFase4 = facFacturacionprogramadaExtendsMapper.getNumTotalFacturacionesProGenerarEnviosFacturasPendientes(Short.valueOf(idInstitucion), minutos);
							fase1.setPuestoEnCola(literalProcesado);
							fase2.setPuestoEnCola(literalProcesado);
							fase3.setPuestoEnCola(literalProcesado);
							fase4.setPuestoEnCola(posicionFase4 + "/" + numTotalFase4);
							fase5.setPuestoEnCola(literalPendienteFase);
						} else {

							Integer posicionFase5 = facFacturacionprogramadaExtendsMapper.getPosicionFacturacionProComprobacionTraspasoFacturas(Short.valueOf(idInstitucion), idSerieFacturacion, idProgramacion, minutos);

							if (posicionFase5 != null) {
								Integer numTotalFase5 = facFacturacionprogramadaExtendsMapper.getNumTotalFacturacionesProComprobacionTraspasoFacturas(Short.valueOf(idInstitucion), minutos);
								fase1.setPuestoEnCola(literalProcesado);
								fase2.setPuestoEnCola(literalProcesado);
								fase3.setPuestoEnCola(literalProcesado);
								fase4.setPuestoEnCola(literalProcesado);
								fase5.setPuestoEnCola(posicionFase5 + "/" + numTotalFase5);
							}
						}
					}
				}

			}

			faseFacturacionProgramadaDTO.getFaseFacturacionProgramadaItemList().add(fase1);
			faseFacturacionProgramadaDTO.getFaseFacturacionProgramadaItemList().add(fase2);
			faseFacturacionProgramadaDTO.getFaseFacturacionProgramadaItemList().add(fase3);
			faseFacturacionProgramadaDTO.getFaseFacturacionProgramadaItemList().add(fase4);
			faseFacturacionProgramadaDTO.getFaseFacturacionProgramadaItemList().add(fase5);

		}

		LOGGER.info("FacturacionPySServiceImpl.getEstadosFacturacionProgramada() --> SALIDA del servicio para obtener las fases de una facturacion programada y su posicion dentro de ella");

		return faseFacturacionProgramadaDTO;
	}

}
