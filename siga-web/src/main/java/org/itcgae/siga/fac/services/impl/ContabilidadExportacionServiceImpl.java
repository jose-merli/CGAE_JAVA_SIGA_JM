package org.itcgae.siga.fac.services.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.AbonoContabilidadItem;
import org.itcgae.siga.DTO.fac.AltaAnticipoItem;
import org.itcgae.siga.DTO.fac.AnticiposPySItem;
import org.itcgae.siga.DTO.fac.DevolucionesItem;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.DTO.fac.FacRegistroFichContaDTO;
import org.itcgae.siga.DTO.fac.FacturasContabilidadItem;
import org.itcgae.siga.DTO.fac.LiquidacionAnticipoColegioItem;
import org.itcgae.siga.DTO.fac.PagoPorBancoAbonoItem;
import org.itcgae.siga.DTO.fac.PagoPorBancoItem;
import org.itcgae.siga.DTO.fac.PagoPorCajaItem;
import org.itcgae.siga.DTO.fac.PagoPorTarjetaItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.IFicherosService;
import org.itcgae.siga.cen.services.impl.CargasMasivasGFServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.UtilidadesNumeros;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoincluidoendisquete;
import org.itcgae.siga.db.entities.FacBancoinstitucion;
import org.itcgae.siga.db.entities.FacBancoinstitucionKey;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaincluidaendisquete;
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.entities.FacPagosporcaja;
import org.itcgae.siga.db.entities.FacRegistrofichconta;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.PysAnticipoletrado;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.FacBancoinstitucionMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.PysAnticipoletradoMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacFacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineaabonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineafacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacRegistroFichContaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoincluidoendisqueteExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.IContabilidadExportacionService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ContabilidadExportacionServiceImpl implements IContabilidadExportacionService {
	
	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;
	
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
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private FacRegistroFichContaExtendsMapper facRegistroFichContaExtendsMapper;
	
	@Autowired
	private GenDiccionarioMapper genDiccionarioMapper;
	
	@Autowired
	private FacFacturaExtendsMapper facFacturaExtendsMapper;
	
	@Autowired
	private FacAbonoExtendsMapper facAbonoExtendsMapper;

	@Autowired
	private CenClienteMapper cenClienteMapper;
	
	@Autowired
	private FacAbonoincluidoendisqueteExtendsMapper facAbonoincluidoendisqueteExtendsMapper;
	
	@Autowired
	private FacPagosporcajaExtendsMapper facPagosPorCajaMapper;
	
	private Logger LOGGER = Logger.getLogger(ContabilidadExportacionServiceImpl.class);
	
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
		
		private String getParametro(String modulo, String parametro, Short idInstitucion) {
			GenParametrosKey keyParametros = new GenParametrosKey();
			keyParametros.setModulo(modulo);
			keyParametros.setParametro(parametro);
			keyParametros.setIdinstitucion(idInstitucion);
			GenParametros property = genParametrosExtendsMapper.selectByPrimaryKey(keyParametros);
			return property != null ? property.getValor() : "";
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
			private String ASIENTO = "";
			private String FECHA = "";
			private String CUENTA = "";
			private String CONCEPTO = "";
			private String DOCUMENTO = "";
			private String DEBE = "";
			private String HABER = "";
			private String BASEIMP = "";
			private String IVA = "";
			private String CONTRAPARTIDA = "";
			
			private void inicializarVariablesCampos(String idioma) {
				ASIENTO = this.getTraduccion("facturacion.exportacionesyotros.contabilidad.asiento", idioma);
				FECHA = this.getTraduccion("facturacionSJCS.facturacionesYPagos.fecha", idioma);
				CUENTA = this.getTraduccion("facturacion.exportacionesyotros.contabilidad.cuenta", idioma);
				CONCEPTO = this.getTraduccion("facturacionSJCS.facturacionesYPagos.conceptos", idioma);
				DOCUMENTO = this.getTraduccion("justiciaGratuita.ejg.documentacion.Documento", idioma);
				DEBE = this.getTraduccion("facturacion.exportacionesyotros.contabilidad.debe", idioma);
				HABER = this.getTraduccion("facturacion.exportacionesyotros.contabilidad.haber", idioma);
				BASEIMP = this.getTraduccion("facturacion.exportacionesyotros.contabilidad.baseimp", idioma);
				IVA = this.getTraduccion("facturacion.productos.iva", idioma);
				CONTRAPARTIDA = this.getTraduccion("facturacion.exportacionesyotros.contabilidad.contrapartida", idioma);		
			}
			
			private String getTraduccion(String idrecurso, String idioma) {
				GenDiccionarioKey keyParametros = new GenDiccionarioKey();
				keyParametros.setIdrecurso(idrecurso);
				keyParametros.setIdlenguaje(idioma);
				GenDiccionario traduccion = genDiccionarioMapper.selectByPrimaryKey(keyParametros);
				return traduccion != null ? traduccion.getDescripcion() : "";
			}
			
			@Async
			private boolean generarFicheroContabilidad(FacRegistrofichconta registroFacRegistroFichConta, String idioma, String idInstitucion, String usuario) throws Exception	{

				LOGGER.info("generarFicheroContabilidad() -> Entrada al metodo que crea el fichero de contabilidad");
				
				boolean correcto = false;
			
				try {
					//Obtiene traducciones strings necesarios en generaAsientos metodos
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
					
					//Obtiene las traducciones de los campos
					this.inicializarVariablesCampos(idioma);
					
					//2. Crearse la lista con las columnas (multiidioma)
					List<String> camposContabilidad = Arrays.asList(ASIENTO, 
							FECHA, 
							CUENTA,
							CONCEPTO,
							DOCUMENTO,
							DEBE,
							HABER,
							BASEIMP,
							IVA,
							CONTRAPARTIDA);
					
					//3. Obtenemos los datos y los añadimos al excel
					Vector<Hashtable<String, Object>> datosExcel = new Vector<Hashtable<String, Object>>();
					asiento = 0;
			
					// SE PONEN COMMITS INTERMEDIOS PARA QUE EL PROCESO AGUANTE EL TIEMPO QUE TARDA.
									
					// -----------------------------------------------------------------------------------------------------------------
					// Asiento 1 
					// FACTURA. Apunta asiento contable de facturas generadas, no pagada.
					// Ventas (700)	 			--> Bruto por servicio (1) --> 0
					// IVA 				 	    --> Iva (2)		           --> 0
					// Cliente(430.xxx)			--> 0		               --> Negocio(1)+Negocio(2)
					// -----------------------------------------------------------------------------------------------------------------		    
							    
					datosExcel = generaAsiento1(datosExcel, registroFacRegistroFichConta, idioma, idInstitucion, usuario);
					
					// -----------------------------------------------------------------------------------------------------------------
					// Asiento 2 
					// ABONO (POR IMPORTE EXCESIVO EN FACTURA) 
					// 2------>
					// Cliente(430.xxxx)            --> abono(1)+abono(2)	--> 0
					// Devolucion factura (708)		--> 0					--> abono(1)
					// IVA (477)					--> 0					--> abono(2)
					// -----------------------------------------------------------------------------------------------------------------
										
					datosExcel = generaAsiento2(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
					
					// -----------------------------------------------------------------------------------------------------------------
					// Asiento 3
					// PAGO POR CAJA 
					// 3------> HABER --> BEDE
					// Cliente(430.xxx ) 	-->  pagoporcaja --> 0
					// Caja(570) 		 	-->  0			 --> pagoporcaja
					// -----------------------------------------------------------------------------------------------------------------
								
					datosExcel = generaAsiento3(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
					
					// -----------------------------------------------------------------------------------------------------------------
					// PAGO POR BANCO 
					// 4------> Banco
					// Cliente(430.xxx) 	-->  facturaincluidaendisquete 	--> 0
					// Banco(572.1xxx) 		-->  0			 				--> facturaincludidaendisquete
					// -----------------------------------------------------------------------------------------------------------------			
								
					datosExcel = generaAsiento4(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
					
					// -----------------------------------------------------------------------------------------------------------------
					// Asiento 5
					// PAGO POR TARJETA 
					// 5------> Tarjeta
					// Cliente(430.xxx) 	-->  facturaincluidaendisquete 	--> 0
					// Banco(572.2xxx) 		-->  0			 				--> facturaincludidaendisquete
					// -----------------------------------------------------------------------------------------------------------------
			
					datosExcel = generaAsiento5(datosExcel,registroFacRegistroFichConta,idioma, idInstitucion, usuario);
					
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
							
					//Guardarlo en ‘<ruta_base>/ficheros/contabilidad/XXXX/’ (donde XXXX es el idinstitucion).
					Date dateLog = new Date(0);
					FicheroVo ficheroVo = new FicheroVo();
					
					String rutaBase = cargasMasivasGFServiceImpl.getDirectorioFichero(Short.valueOf(idInstitucion));//RUTABASE???		
					//ficheroVo.setDirectorio(rutaBase + "ficheros/contabilidad/"+ idInstitucion +"/");
					ficheroVo.setDirectorio("C:/Users/aavalosmoreno/Desktop/");
					
					LOGGER.info(dateLog + ": Entrada a la subida del fichero de contabilidad con id: " + registroFacRegistroFichConta.getIdcontabilidad() + " subido en la ruta: " + ficheroVo.getDirectorio());

					ficheroVo.setNombre(registroFacRegistroFichConta.getNombrefichero());
					ficheroVo.setDescripcion("Fichero de contabilidad: " + registroFacRegistroFichConta.getNombrefichero());
					ficheroVo.setIdinstitucion(Short.valueOf(idInstitucion));
					
					//Se convierte el fichero en array de bytes para su subida
					byte[] excelEnBytes = FileUtils.readFileToByteArray(excel);
					
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
			
			
			DateFormat javaDateToddMMyyyyFormat = new SimpleDateFormat("dd/MM/yyyy");
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
						concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO1, idioma) + ": " + factura.getDescripcion());
						
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
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(factura.getFechaemision()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, factura.getNumerofactura());
						datosHashtable.put(CUENTA, asientoClientes);
						datosHashtable.put(DEBE, (Double.parseDouble(imp) + Double.parseDouble(importeIva)));
						datosHashtable.put(HABER, "0");
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoIngresos);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
						// Escribimos 2º APUNTE
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(factura.getFechaemision()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, factura.getNumerofactura());
						datosHashtable.put(CUENTA, asientoIngresos);
						datosHashtable.put(DEBE, "0");
						datosHashtable.put(HABER, imp);
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoClientes);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);
						
						// Escribimos 3º APUNTE
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(factura.getFechaemision()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, factura.getNumerofactura());
						datosHashtable.put(CUENTA, asientoIVA);
						datosHashtable.put(DEBE, "0");
						if (!ivacero) {
							datosHashtable.put(HABER, importeIva);
						//MJM se hace un apunte con importe IVA 0 si no tiene IVA 
						//Esto es por la incidencia R1502_0055:
						//en la exportación de datos a contaplus no se tienen en cuenta las fac. con IVA 0 sino existe
						//asiento de IVA.
						} else {
							datosHashtable.put(HABER, "0");
						}
						datosHashtable.put(BASEIMP, imp);
						datosHashtable.put(IVA, valorIva);
						datosHashtable.put(CONTRAPARTIDA, asientoClientes);
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
						concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO2, idioma) + ": " + abono.getNumerofactura());
						
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
							
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(abono.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, abono.getNumeroabono());
							datosHashtable.put(CUENTA, asientoIngresos);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoClientes);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);
							
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(abono.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, abono.getNumeroabono());
							datosHashtable.put(CUENTA, asientoClientes);
							datosHashtable.put(DEBE, "" + (Double.parseDouble(imp) + Double.parseDouble(importeIva)));
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoIngresos);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);
							
							// Escribimos 3º APUNTE
							if (!ivacero) {
								
								datosHashtable = new Hashtable<String, Object>();
								datosHashtable.put(ASIENTO, asiento);
								datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(abono.getFecha()));
								datosHashtable.put(CONCEPTO, concepto);
								datosHashtable.put(DOCUMENTO, abono.getNumeroabono());
								datosHashtable.put(CUENTA, asientoClientes);
								datosHashtable.put(DEBE, importeIva);
								datosHashtable.put(HABER, "0");
								datosHashtable.put(BASEIMP, imp);
								datosHashtable.put(IVA, valorIva);
								datosHashtable.put(CONTRAPARTIDA, asientoIVA);
								datosHashtable = this.checkDatos(asiento, datosHashtable);
								datosExcel.add(datosHashtable);
								
							}	
							
						//Si la factura no está devuelta está pagada inicialmente
						}else{
								
							String asientoContableBancoCja="";
								
								if((abono.getBancos_codigo() != null) && (abono.getBancos_codigo() != ""))
									asientoContableBancoCja	= obtenerAsientoContableBanco(idInstitucion, abono.getBancos_codigo());  
								else if(impPagCaja!="0.0")
									asientoContableBancoCja= CONTABILIDAD_CAJA;
								else
									asientoContableBancoCja=asientoIngresos;
								
								datosHashtable = new Hashtable<String, Object>();
								datosHashtable.put(ASIENTO, asiento);
								datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(abono.getFecha()));
								datosHashtable.put(CONCEPTO, concepto);
								datosHashtable.put(DOCUMENTO, abono.getNumeroabono());
								datosHashtable.put(CUENTA, asientoContableBancoCja);
								datosHashtable.put(DEBE, "" + (Double.parseDouble(imp) + Double.parseDouble(importeIva)));
								datosHashtable.put(HABER, "0");
								datosHashtable.put(BASEIMP, "");
								datosHashtable.put(IVA, "");
								datosHashtable.put(CONTRAPARTIDA, asientoClientes);
								datosHashtable = this.checkDatos(asiento, datosHashtable);
								datosExcel.add(datosHashtable);

								datosHashtable = new Hashtable<String, Object>();
								datosHashtable.put(ASIENTO, asiento);
								datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(abono.getFecha()));
								datosHashtable.put(CONCEPTO, concepto);
								datosHashtable.put(DOCUMENTO, abono.getNumeroabono());
								datosHashtable.put(CUENTA, asientoClientes);
								datosHashtable.put(DEBE, "0");
								datosHashtable.put(HABER, imp);
								datosHashtable.put(BASEIMP, "");
								datosHashtable.put(IVA, "");
								datosHashtable.put(CONTRAPARTIDA, asientoContableBancoCja);
								datosHashtable = this.checkDatos(asiento, datosHashtable);
								datosExcel.add(datosHashtable);

								// Escribimos 3º APUNTE
								if (!ivacero) {
									datosHashtable = new Hashtable<String, Object>();
									datosHashtable.put(ASIENTO, asiento);
									datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(abono.getFecha()));
									datosHashtable.put(CONCEPTO, concepto);
									datosHashtable.put(DOCUMENTO, abono.getNumeroabono());
									datosHashtable.put(CUENTA, asientoClientes);
									datosHashtable.put(DEBE, "0");
									datosHashtable.put(HABER, importeIva);
									datosHashtable.put(BASEIMP, imp);
									datosHashtable.put(IVA, valorIva);
									datosHashtable.put(CONTRAPARTIDA, asientoIVA);
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
					
					conceptoCompensado = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO3_2010, idioma));		
					concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO3, idioma));

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
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorCaja.getFecha()));
							datosHashtable.put(CONCEPTO, conceptoCompensado);
							datosHashtable.put(DOCUMENTO, pagoPorCaja.getNumerofactura());
							datosHashtable.put(CUENTA, asientoContable);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoCompensacionCliente);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);
						
						    // pago por caja sobre factura
							// Escribimos 2� apunte
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorCaja.getFecha()));
							datosHashtable.put(CONCEPTO, conceptoCompensado);
							datosHashtable.put(DOCUMENTO, pagoPorCaja.getNumerofactura());
							datosHashtable.put(CUENTA, asientoCompensacionCliente);
							datosHashtable.put(DEBE, imp);
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoContable);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);
							
						} else {
							// PAGO NORMAL POR CAJA (NO TARJETA)
							// Escribimos 1� apunte
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorCaja.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, pagoPorCaja.getNumerofactura());
							datosHashtable.put(CUENTA, asientoContable);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, CONTABILIDAD_CAJA);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);
							
						    // pago por caja sobre factura
							// Escribimos 2� apunte
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorCaja.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, pagoPorCaja.getNumerofactura());
							datosHashtable.put(CUENTA, CONTABILIDAD_CAJA);
							datosHashtable.put(DEBE, imp);
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoContable);
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
					concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO4, idioma));
				
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
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorBancoItem.getFechacreacion()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, pagoPorBancoItem.getNumerofactura());
						datosHashtable.put(CUENTA, asientoContable);
						datosHashtable.put(DEBE, "0");
						datosHashtable.put(HABER, imp);
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoContableBanco);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);						

						// Escribimos 2� asiento
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorBancoItem.getFechacreacion()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, pagoPorBancoItem.getNumerofactura());
						datosHashtable.put(CUENTA, asientoContableBanco);
						datosHashtable.put(DEBE, imp);
						datosHashtable.put(HABER, "0");
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoContable);
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

//					// Descripcion del concepto
					conceptoT = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO5, idioma));


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
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorTarjetaItem.getFecha()));
						datosHashtable.put(CONCEPTO, conceptoT);
						datosHashtable.put(DOCUMENTO, pagoPorTarjetaItem.getNumerofactura());
						datosHashtable.put(CUENTA, asientoContable);
						datosHashtable.put(DEBE, "0");
						datosHashtable.put(HABER, imp);
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, CONTABILIDAD_TARJETAS);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	

//						// Escribimos 2� asiento
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorTarjetaItem.getFecha()));
						datosHashtable.put(CONCEPTO, conceptoT);
						datosHashtable.put(DOCUMENTO, pagoPorTarjetaItem.getNumerofactura());
						datosHashtable.put(CUENTA, CONTABILIDAD_TARJETAS);
						datosHashtable.put(DEBE, imp);
						datosHashtable.put(HABER, "0");
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoContable);
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
					concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO5, idioma));
					
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
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(devolucion.getFechageneracion()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, devolucion.getNumerofactura());
						datosHashtable.put(CUENTA, asientoContable);
						datosHashtable.put(DEBE, imp);
						datosHashtable.put(HABER, "0");
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoContableBanco);
						datosHashtable = this.checkDatos(asiento, datosHashtable);
						datosExcel.add(datosHashtable);	
						
						// Escribimos 2� asiento
						datosHashtable = new Hashtable<String, Object>();
						datosHashtable.put(ASIENTO, asiento);
						datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(devolucion.getFechageneracion()));
						datosHashtable.put(CONCEPTO, concepto);
						datosHashtable.put(DOCUMENTO, devolucion.getNumerofactura());
						datosHashtable.put(CUENTA, asientoContableBanco);
						datosHashtable.put(DEBE, "0");
						datosHashtable.put(HABER, imp);
						datosHashtable.put(BASEIMP, "");
						datosHashtable.put(IVA, "");
						datosHashtable.put(CONTRAPARTIDA, asientoContable);
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
							concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO7, idioma) + " " + altaAnticipoItem.getDescripcion());
							
							idAnticipo = String.valueOf(altaAnticipoItem.getIdanticipo());
							
							imp = UtilidadesNumeros.redondea(String.valueOf(altaAnticipoItem.getImporteinicial()),2);
							
							String ctaClientes = altaAnticipoItem.getCtacontable();
							
							// Con el IDPERSONA, obtenemos de CEN_CLIENTE, el asiento contable.
						    asientoContable =  ctaClientes + obtenerAsientoContable(idInstitucion, String.valueOf(altaAnticipoItem.getIdpersona()));
							
							// Con el BANCOS_CODIGO, obtenemos de FAC_BANCOINSTITUCION, el numerocuenta.
						    asientoContableCajaAnticipos	= CONTABILIDAD_CAJA_ANTICIPOS;

						    Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
						    
							// Escribimos 1� apunte
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(altaAnticipoItem.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, altaAnticipoItem.getIdanticipo());
							datosHashtable.put(CUENTA, asientoContable);
							datosHashtable.put(DEBE, imp);
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoContableCajaAnticipos);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);	
							
				
							
							// Escribimos 2� asiento
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(altaAnticipoItem.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, altaAnticipoItem.getIdanticipo());
							datosHashtable.put(CUENTA, asientoContableCajaAnticipos);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoContable);
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
					concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO7A, idioma));
								
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
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(liquidacionAnticipoColegio.getFechaefectiva()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, liquidacionAnticipoColegio.getIdanticipo());
							datosHashtable.put(CUENTA, asientoContableIngresosExtra);
							datosHashtable.put(DEBE, imp);
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoAnticiposCliente);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);	
											
							// Escribimos 2� asiento
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(liquidacionAnticipoColegio.getFechaefectiva()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, liquidacionAnticipoColegio.getIdanticipo());
							datosHashtable.put(CUENTA, asientoAnticiposCliente);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoContableIngresosExtra);
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
					
					concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO3, idioma));
					conceptoAnticipo = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO3_2, idioma));
						
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
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(anticipoPyS.getFecha()));
							datosHashtable.put(CONCEPTO, conceptoAnticipo);
							datosHashtable.put(DOCUMENTO, anticipoPyS.getNumerofactura());
							datosHashtable.put(CUENTA, asientoContable);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoAnticiposCliente);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);	
				
							// Escribimos 2 apunte
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(anticipoPyS.getFecha()));
							datosHashtable.put(CONCEPTO, conceptoAnticipo);
							datosHashtable.put(DOCUMENTO, anticipoPyS.getNumerofactura());
							datosHashtable.put(CUENTA, asientoAnticiposCliente);
							datosHashtable.put(DEBE, imp);
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoContable);
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
							concepto = UtilidadesString.sustituirParaExcel(this.getTraduccion(SigaConstants.CONCEPTO_ASIENTO2B, idioma)+ ": "+ pagoPorBancoAbonoItem.getNumerofactura());
							
							asiento++;
			
							Hashtable<String, Object> datosHashtable = new Hashtable<String, Object>();
							
							// Escribimos 1 apunte
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorBancoAbonoItem.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, pagoPorBancoAbonoItem.getNumeroabono());
							datosHashtable.put(CUENTA, asientoIngresos);
							datosHashtable.put(DEBE, imp);
							datosHashtable.put(HABER, "0");
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoClientes);
							datosHashtable = this.checkDatos(asiento, datosHashtable);
							datosExcel.add(datosHashtable);					

							// Escribimos 2� asiento
							datosHashtable = new Hashtable<String, Object>();
							datosHashtable.put(ASIENTO, asiento);
							datosHashtable.put(FECHA, javaDateToddMMyyyyFormat.format(pagoPorBancoAbonoItem.getFecha()));
							datosHashtable.put(CONCEPTO, concepto);
							datosHashtable.put(DOCUMENTO, pagoPorBancoAbonoItem.getNumeroabono());
							datosHashtable.put(CUENTA, asientoClientes);
							datosHashtable.put(DEBE, "0");
							datosHashtable.put(HABER, imp);
							datosHashtable.put(BASEIMP, "");
							datosHashtable.put(IVA, "");
							datosHashtable.put(CONTRAPARTIDA, asientoIngresos);
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
				
				Double importeDebe = Double.valueOf(datos.get(DEBE).toString());
				Double importeHaber = Double.valueOf(datos.get(HABER).toString());
				Double baseImponible = null;
				if(!datos.get(BASEIMP).equals("")) {
					baseImponible = Double.valueOf(datos.get(BASEIMP).toString());
				}
				
				String baseImp = "";
				if(importeDebe<0.0){
					importeHaber = -1 * importeDebe;
					importeDebe = 0.0;
					datos.remove(DEBE);
					datos.put(DEBE, importeDebe);
				}
				if(importeHaber<0.0){
					importeDebe = -1 * importeHaber;
					importeHaber = 0.0;
					datos.remove(HABER);
					datos.put(HABER, importeHaber);
				}
				if(baseImponible!=null&&baseImponible<0.0){
					baseImponible = -1 * baseImponible;
					baseImp = baseImponible.toString().replace('.', ',');
					datos.remove(BASEIMP);
					datos.put(BASEIMP, baseImp);
				}else{
					if(baseImponible != null) {
						baseImp = baseImponible.toString().replace('.', ',');
					}else {
						baseImp = "";
					}
					datos.remove(BASEIMP);
					datos.put(BASEIMP, baseImp);
				}		
					
				// Fecha
				if(datos.get(FECHA) == null) {
					datos.remove(FECHA);
					datos.put(FECHA, "");
				}
			
				// Cuenta
				if(datos.get(CUENTA) == null) {
					datos.remove(CUENTA);
					datos.put(CUENTA, "");
				}
					
				// Concepto
				if(datos.get(CONCEPTO) == null) {
					datos.remove(CONCEPTO);
					datos.put(CONCEPTO, "");
				}
				
				// Documento 
				if(datos.get(DOCUMENTO) == null) {
					datos.remove(DOCUMENTO);
					datos.put(DOCUMENTO, "");
				}
					
				// Debe
				datos.remove(DEBE);
				datos.put(DEBE, importeDebe.toString().replace('.', ','));
					
				// Haber
				datos.remove(HABER);
				datos.put(HABER, importeHaber.toString().replace('.', ','));
					
				// IVA
				if(datos.get(IVA) == null) {
					datos.remove(IVA);
					datos.put(IVA, "");
				}
					
				// Contrapartida
				if(datos.get(CONTRAPARTIDA) == null) {
					datos.remove(CONTRAPARTIDA);
					datos.put(CONTRAPARTIDA, "");
				}
					
				return datos;
			}


}
