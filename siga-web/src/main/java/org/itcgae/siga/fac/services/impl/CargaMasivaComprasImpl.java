package org.itcgae.siga.fac.services.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.commons.io.IOUtils;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasBusquedaItem;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasDTO;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasItem;
import org.itcgae.siga.DTO.fac.CargaMasivaDatosCompItem;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltroCargaMasivaCompras;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.entities.PysFormapagoproductoExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysProductosinstitucionExample;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.mappers.PysProductossolicitadosMapper;
import org.itcgae.siga.db.mappers.PysTipoivaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCargaMasivaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.fac.services.ICargaMasivaComprasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import java.text.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Service
public class CargaMasivaComprasImpl implements ICargaMasivaComprasService {
	
	private Logger LOGGER = Logger.getLogger(CargaMasivaComprasImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;
	
	@Autowired
	private CenColegiadoMapper cenColegiadoMapper;
	
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	
	@Autowired
	private PysProductosinstitucionMapper pysProductosinstitucionMapper;
	
	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;
	
	@Autowired
	private PysFormapagoproductoMapper pysFormapagoproductoMapper;
	
	@Autowired
	private CenCuentasbancariasMapper cenCuentasbancariasMapper;
	
	@Autowired
	private PysTipoivaMapper pysTipoivaMapper;
	
	@Autowired
	private PysProductossolicitadosMapper pysProductossolicitadosMapper;
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	
	@Autowired
	private CenCargaMasivaExtendsMapper cenCargaMasivaExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private GestionFichaCompraSuscripcionServiceImpl gestionFichaCompraSuscripcionServiceImpl;
	
	@Autowired
	private ExcelHelper excelHelper;
	
	@Override
	public InputStreamResource descargarModelo(HttpServletRequest request)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		ByteArrayInputStream byteInput = crearExcel();

		return new InputStreamResource(byteInput);

	}

	private ByteArrayInputStream crearExcel() {
		List<String> cabeceraExcel = Arrays.asList(SigaConstants.CP_NCOLEGIADO, SigaConstants.CP_NIF, SigaConstants.CP_APELLIDOS_CLI, SigaConstants.CP_NOMBRE_CLI,
				SigaConstants.CP_CANT_PROD, SigaConstants.CP_NOMBRE_PROD, SigaConstants.CP_COD_PROD, SigaConstants.CP_FECHA_COMP);
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Modelo Carga Masiva Compras");

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < cabeceraExcel.size(); col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cabeceraExcel.get(col));
			}

			Row row = sheet.createRow(1);

			row.createCell(0).setCellValue("nnnnnnnnn");
			row.createCell(1).setCellValue("nnnnnnnna");
			row.createCell(2).setCellValue("aaaaaaaaaaaaa");
			row.createCell(3).setCellValue("aaaaaaaaaaaaa");
			row.createCell(4).setCellValue("nnnnn");
			row.createCell(5).setCellValue("aaaaaaaaaaaaa");
			row.createCell(6).setCellValue("aaaaaaaaaaaaa");
			row.createCell(7).setCellValue("dd/mm/yyyy");

			Row row2 = sheet.createRow(2);

			row2.createCell(0).setCellValue("Requerido");
			row2.createCell(1).setCellValue("Requerido");
			row2.createCell(2).setCellValue("Requerido");
			row2.createCell(3).setCellValue("Requerido");
			row2.createCell(4).setCellValue("Requerido");
			row2.createCell(5).setCellValue("Requerido");
			row2.createCell(6).setCellValue("Requerido");
			row2.createCell(7).setCellValue("Requerido");

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Error al crear el archivo Excel: " + e.getMessage());
		}
	}
	
	@Override
	public CargaMasivaComprasDTO listado(FiltroCargaMasivaCompras cargaMasivaItem,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CargaMasivaComprasDTO cargaMasivaComprasDTO = new CargaMasivaComprasDTO();
		List<CargaMasivaComprasItem> cargaMasivaCompras = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"listado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"listado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"listado() / ScsRemesasExtendsMapper.buscarRemesas() -> Entrada a ScsIntercambiosExtendsMapper para obtener las Cargas Maivas de Compras");

			if (null != usuarios && usuarios.size() > 0) {
				Integer tamMaximo = getTamanoMaximo(idInstitucion);
				cargaMasivaCompras = pysPeticioncomprasuscripcionExtendsMapper.listadoCargaMasivaCompras(cargaMasivaItem, idInstitucion, tamMaximo);
			}

			LOGGER.debug(
					"listado() / ScsRemesasExtendsMapper.buscarRemesas() -> Salida a ScsRemesasExtendsMapper para obtener las Cargas Maivas de Compras");

			if (cargaMasivaCompras != null) {
				cargaMasivaComprasDTO.setCargaMasivaComprasItem(cargaMasivaCompras);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener las Cargas Maivas de Compras");
		return cargaMasivaComprasDTO;
	}
	
	private Integer getTamanoMaximo(Short idinstitucion) {
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("FAC").andParametroEqualTo("TAM_MAX_CONSULTA_FAC")
	    		.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idinstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    List<GenParametros> tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        Integer tamMaximo = null;
		if (tamMax != null) {
            tamMaximo  = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
		return tamMaximo;
	}
	
	@Override
	@Transactional(timeout=24000)
	public DeleteResponseDTO cargarFichero(MultipartHttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("cargarFichero() -> Entrada al servicio para subir un archivo");
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errores = "";
		int registrosErroneos = 0;
		Vector<Hashtable<String, Object>> datosLog = new Vector<Hashtable<String, Object>>();
		Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String, Object>>();

		// Coger archivo del request
		LOGGER.debug("cargarFichero() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		String nombreFichero = file.getOriginalFilename();

		// Extraer la debugrmación del excel
		LOGGER.debug("cargarFichero() -> Extraer los datos del archivo");
		try {
			datos = this.excelHelper.parseExcelFile(file.getBytes());
		} catch (BusinessException | IOException e1) {
			// TODO Auto-generated catch block
			LOGGER.error(e1.getStackTrace());
			throw e1;
		}

		CenCargamasiva cenCargamasivacv = new CenCargamasiva();

		// Conseguimos debugrmación del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"cargarFichero() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.debug(
					"cargarFichero() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.debug("cargarFichero() -> Extraer los datos del archivo y los convertimos en clases");
				List<CargaMasivaDatosCompItem> CargaMasivaDatosCompItems = parseExcelFileComp(datos, usuario);

				LOGGER.debug("cargarFichero() / Se obtienen los ficheros cargados en una lista de clases");
				
				if(CargaMasivaDatosCompItems.size() != 0 && !CargaMasivaDatosCompItems.isEmpty()) {
					
					//Se comprueba si alguna de las filas a dado error
					boolean found = false;
					int i=0;
					while(i<CargaMasivaDatosCompItems.size() && found == false) {
						CargaMasivaDatosCompItem CargaMasivaDatosCompItem = CargaMasivaDatosCompItems.get(i);
						if(CargaMasivaDatosCompItem.getErrores() != null) {
							found = true;
						}
						i++;
					}
					
					for (CargaMasivaDatosCompItem CargaMasivaDatosCompItem : CargaMasivaDatosCompItems) {

						LOGGER.debug("cargarFichero() / Se van leyendo los ficheros cargados en la lista");
						
						LOGGER.debug("cargarFichero() / Si no se ha detectado errores leyendo el excel introducido");
						if (CargaMasivaDatosCompItem.getErrores() == null) {

							LOGGER.debug("cargarFichero() / Insertamos el la solicitud de compra ");
							
							Long idPersona = null;
							if(CargaMasivaDatosCompItem.getNifCifCliente() != null) {
								CenPersonaExample personaExample = new CenPersonaExample();
								
								personaExample.createCriteria().andNifcifEqualTo(CargaMasivaDatosCompItem.getNifCifCliente());								
								
								idPersona = cenPersonaMapper.selectByExample(personaExample).get(0).getIdpersona();
								
							}
							else {
								CenColegiadoExample colegiadoExample = new CenColegiadoExample();
								
								colegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andNcolegiadoEqualTo(CargaMasivaDatosCompItem.getNumColegiadoCliente());								
								
								idPersona = cenColegiadoMapper.selectByExample(colegiadoExample).get(0).getIdpersona();
							}
							//Se obtiene un producto con el codigo introducido
							PysProductosinstitucionExample productoInsExample = new PysProductosinstitucionExample();
								
							productoInsExample.createCriteria().andCodigoextEqualTo(CargaMasivaDatosCompItem.getCodigoProducto());
							
							PysProductosinstitucion productoCodigo = pysProductosinstitucionMapper.selectByExample(productoInsExample).get(0);
							

							FichaCompraSuscripcionItem ficha = new FichaCompraSuscripcionItem();
							
							
							//Se comprueba si el producto es "No facturable"
							if(CargaMasivaDatosCompItem.getNoFacturable().equals("1")) {
								//De forma temporal se utilizara el id 80 que se refiere a pago por domiciliacion bancaria 
								//que no tendra cuenta bancaria seleccionada.
								ficha.setIdFormaPagoSeleccionada("-1");
								
							}
							//Se escoge una forma de pago por secretaria asociada con el producto 
							// Personal del colegio = pago por secretaria ("S"), colegiado = formas de pago por internet ("A").
							else {
								PysFormapagoproductoExample pagoProductoExample = new PysFormapagoproductoExample();
								
								pagoProductoExample.createCriteria().andIdproductoEqualTo(CargaMasivaDatosCompItem.getIdProducto()).andIdproductoinstitucionEqualTo(CargaMasivaDatosCompItem.getIdProductoInstitucion())
								.andIdtipoproductoEqualTo(CargaMasivaDatosCompItem.getIdTipoProducto()).andInternetEqualTo("S").andIdinstitucionEqualTo(idInstitucion);
								
								ficha.setIdFormaPagoSeleccionada(pysFormapagoproductoMapper.selectByExample(pagoProductoExample).get(0).getIdformapago().toString());
								
								
								//Si es domiciliación bancaria se eligen una cuenta bancaria asociada al cliente
								if(ficha.getIdFormaPagoSeleccionada().equals("80")) {
									
									CenCuentasbancariasExample cuentaExample = new CenCuentasbancariasExample();
									
									cuentaExample.createCriteria().andIdpersonaEqualTo(idPersona).andIdinstitucionEqualTo(idInstitucion);
									
									ficha.setCuentaBancSelecc(cenCuentasbancariasMapper.selectByExample(cuentaExample).get(0).getIdcuenta().toString());
								}
							}
							
							
							ficha.setIdPersona(idPersona);
							ficha.setFechaCompra(CargaMasivaDatosCompItem.getFechaCompra());
							
							ListaProductosCompraItem prod = new ListaProductosCompraItem();
							
							prod.setIdtipoproducto(CargaMasivaDatosCompItem.getIdTipoProducto());
							prod.setIdproductoinstitucion(Integer.valueOf(CargaMasivaDatosCompItem.getIdProductoInstitucion().toString()));
							prod.setIdproducto(Integer.valueOf(CargaMasivaDatosCompItem.getIdProducto().toString()));
							
							prod.setPrecioUnitario(CargaMasivaDatosCompItem.getValor().toString());
							prod.setNoFacturable(CargaMasivaDatosCompItem.getNoFacturable());
							prod.setCantidad(CargaMasivaDatosCompItem.getCantidadProducto());
							prod.setIdPersona(idPersona.toString());
							prod.setIdtipoiva(CargaMasivaDatosCompItem.getIdTipoIva().toString());
							prod.setOrden("1");
							prod.setCantidad(CargaMasivaDatosCompItem.getCantidadProducto());
							
							
							ficha.setProductos(Arrays.asList(prod));
							
							PysPeticioncomprasuscripcion pet = new PysPeticioncomprasuscripcion ();
							
							pet.setIdinstitucion(idInstitucion);
							
							ficha.setnSolicitud(String.valueOf(pysPeticioncomprasuscripcionExtendsMapper.getNewIdPet(pet)));
							
							gestionFichaCompraSuscripcionServiceImpl.solicitarCompra(request, ficha);


							LOGGER.info(
									"solicitarCompra() / updateProductosPeticion() -> Salida de pysProductossolicitadosMapper para introducir los productos solicitados");

							
							
							// Se introduce el documento en el historico
							LOGGER.debug("cargarFichero() -> Se introduce el documento en el historico");
							insertaCenHistoricoCompras(CargaMasivaDatosCompItem, usuario);
						}else if(CargaMasivaDatosCompItem.getErrores() != null){
							errores += CargaMasivaDatosCompItem.getErrores();
							error.setDescription(errores);
							deleteResponseDTO.setError(error);

							registrosErroneos++;
						}

						LOGGER.debug("cargarFichero() / Se llama al método convertItemtoHastCompra()");
						Hashtable<String, Object> e = new Hashtable<String, Object>();
						e = convertItemtoHashCompra(CargaMasivaDatosCompItem);
						// Guardar log
						datosLog.add(e);
					}
					
					LOGGER.debug("cargarFichero() / Subimos el fichero");
					
					int result = 0;
					try {
						byte[] bytesLog = this.excelHelper.createExcelBytes(SigaConstants.CAMPOSLOGCP, datosLog);

						cenCargamasivacv.setTipocarga("CP");
						cenCargamasivacv.setIdinstitucion(usuario.getIdinstitucion());
						cenCargamasivacv.setNombrefichero(nombreFichero);
						cenCargamasivacv.setNumregistros(CargaMasivaDatosCompItems.size());
						cenCargamasivacv.setNumregistroserroneos(registrosErroneos);
						cenCargamasivacv.setFechamodificacion(new Date());
						cenCargamasivacv.setFechacarga(new Date());
						cenCargamasivacv.setUsumodificacion(usuario.getIdusuario());

						Long idFile = uploadFileLog(file.getBytes(), cenCargamasivacv, false);
						Long idLogFile = uploadFileLog(bytesLog, cenCargamasivacv, true);

						cenCargamasivacv.setIdfichero(idFile);
						cenCargamasivacv.setIdficherolog(idLogFile);

						result = cenCargaMasivaExtendsMapper.insert(cenCargamasivacv);
					}catch(IOException e) {
						LOGGER.error(e.getStackTrace());
						throw e;
					}
					
					if (result == 0) {
						throw new Exception("Error subiendo el fichero al repositorio");
					}

					deleteResponseDTO.setStatus(SigaConstants.OK);
					error.setDescription(errores);
					int correctos = cenCargamasivacv.getNumregistros() - registrosErroneos;
					error.setMessage("Fichero cargado correctamente. Registros Correctos: " + correctos
							+ "<br/> Registros Erroneos: " + cenCargamasivacv.getNumregistroserroneos());
					error.setCode(SigaConstants.CODE_200);
					
				}else {
					error.setMessage("No existen registros en el fichero.");
					deleteResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}

		deleteResponseDTO.setError(error);

		LOGGER.debug("cargarFichero() -> Salida del servicio para cargarFichero");
		
		return deleteResponseDTO;
	}
	
	@Transactional
	private void insertaCenHistoricoCompras(CargaMasivaDatosCompItem CargaMasivaDatosCompItem, AdmUsuarios usuario) 
			throws BusinessException{
		
		LOGGER.debug("Insertando en CEN_HISTORICO para el colegio " + usuario.getIdinstitucion() + ", idPersona "
				+ CargaMasivaDatosCompItem.getIdPersona());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		CenHistorico cenHistorico = new CenHistorico();
		cenHistorico.setIdinstitucion(usuario.getIdinstitucion());
		cenHistorico.setIdpersona(CargaMasivaDatosCompItem.getIdPersona());
		cenHistorico.setFechaentrada(Calendar.getInstance().getTime());
		cenHistorico.setFechaefectiva(Calendar.getInstance().getTime());
		cenHistorico.setMotivo(null);
		cenHistorico.setIdtipocambio(SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		cenHistorico.setFechamodificacion(Calendar.getInstance().getTime());
		cenHistorico.setUsumodificacion(usuario.getIdusuario());

		NewIdDTO newIdDTO = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(
				String.valueOf(CargaMasivaDatosCompItem.getIdPersona()), String.valueOf(usuario.getIdinstitucion()));
		if (newIdDTO == null) {
			cenHistorico.setIdhistorico((short) 1);
		} else {
			int newIdCv = Integer.parseInt(newIdDTO.getNewId()) + 1;
			cenHistorico.setIdhistorico((short) newIdCv);
		}

		StringBuffer descripcion = new StringBuffer();

		GenRecursosExample genRecursosExample = new GenRecursosExample();
		genRecursosExample.createCriteria().andIdrecursoEqualTo("historico.literal.registroNuevo")
				.andIdlenguajeEqualTo(usuario.getIdlenguaje());
		List<GenRecursos> genRecursos = genRecursosMapper.selectByExample(genRecursosExample);

		descripcion = descripcion.append(genRecursos.get(0).getDescripcion());

		descripcion.append("\n");
		descripcion.append("- ApellidosCliente: ");
		descripcion.append(CargaMasivaDatosCompItem.getApellidosCliente() != null
				? CargaMasivaDatosCompItem.getApellidosCliente()
				: "");

		descripcion.append("\n");
		descripcion.append("- CantidadProducto: ");
		descripcion.append(CargaMasivaDatosCompItem.getCantidadProducto() != null ? CargaMasivaDatosCompItem.getCantidadProducto() : "");
		
		descripcion.append("\n");
		descripcion.append("- CodigoProducto: ");
		descripcion.append(
				CargaMasivaDatosCompItem.getCodigoProducto() != null ? CargaMasivaDatosCompItem.getCodigoProducto()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- NifCifCliente: ");
		descripcion.append(
				CargaMasivaDatosCompItem.getNifCifCliente() != null ? CargaMasivaDatosCompItem.getNifCifCliente()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- NombreCliente: ");
		descripcion.append(
				CargaMasivaDatosCompItem.getNombreCliente() != null ? CargaMasivaDatosCompItem.getNombreCliente()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- FechaCompra: ");
		descripcion.append(
				CargaMasivaDatosCompItem.getFechaCompra() != null ? simpleDateFormat.format(CargaMasivaDatosCompItem.getFechaCompra())
						: "");
		
		descripcion.append("\n");
		descripcion.append("- NumColegiadoCliente: ");
		descripcion.append(
				CargaMasivaDatosCompItem.getNumColegiadoCliente() != null ? CargaMasivaDatosCompItem.getNumColegiadoCliente()
						: "");
		
		descripcion.append("\n");
		descripcion.append("- NombreProducto: ");
		descripcion.append(
				CargaMasivaDatosCompItem.getNombreProducto() != null ? CargaMasivaDatosCompItem.getNombreProducto()
						: "");

		cenHistorico.setDescripcion(descripcion.toString());

		if (cenHistoricoExtendsMapper.insert(cenHistorico) != 1) {
			throw new BusinessException("No se ha insertado correctamente el registro en cenHistorico para el colegio "
					+ usuario.getIdinstitucion() + ", idPersona " + CargaMasivaDatosCompItem.getIdPersona()
					+ " e idTipoCambio " + SigaConstants.HISTORICOCAMBIOCV.DATOSCV.getId());
		}

	}

	private Hashtable<String, Object> convertItemtoHashCompra(CargaMasivaDatosCompItem cargaMasivaDatosCompItem){
		// TODO Auto-generated method stub
		Date dateLog = new Date();
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

		LOGGER.debug(dateLog + " --> Inicio CargaMasivaComprasServiceImpl convertItemtoHashCompra");
		Hashtable<String, Object> e = new Hashtable<String, Object>();
		
		e.put(SigaConstants.CP_NCOLEGIADO, cargaMasivaDatosCompItem.getNumColegiadoCliente() != null ? cargaMasivaDatosCompItem.getNumColegiadoCliente() : "");

		e.put(SigaConstants.CP_NIF, cargaMasivaDatosCompItem.getNifCifCliente() != null ? cargaMasivaDatosCompItem.getNifCifCliente() : "");
		
		e.put(SigaConstants.CP_APELLIDOS_CLI, cargaMasivaDatosCompItem.getApellidosCliente() != null ? cargaMasivaDatosCompItem.getApellidosCliente() : "");

		e.put(SigaConstants.CP_NOMBRE_CLI, cargaMasivaDatosCompItem.getNombreCliente() != null ? cargaMasivaDatosCompItem.getNombreCliente() : "");

		e.put(SigaConstants.CP_CANT_PROD, cargaMasivaDatosCompItem.getCantidadProducto() != null ? cargaMasivaDatosCompItem.getCantidadProducto() : "");

		e.put(SigaConstants.CP_NOMBRE_PROD, cargaMasivaDatosCompItem.getNombreProducto() != null ? cargaMasivaDatosCompItem.getNombreProducto() : "");

		e.put(SigaConstants.CP_COD_PROD, cargaMasivaDatosCompItem.getCodigoProducto() != null ? cargaMasivaDatosCompItem.getCodigoProducto() : "");
		
		String fechaCompra = "";
		
		if (cargaMasivaDatosCompItem.getFechaCompra() != null) {
			fechaCompra = df2.format(cargaMasivaDatosCompItem.getFechaCompra());
		}
		
		e.put(SigaConstants.CP_FECHA_COMP, cargaMasivaDatosCompItem.getFechaCompra() != null ? fechaCompra : "");
		
		e.put(SigaConstants.ERRORES, cargaMasivaDatosCompItem.getErrores() != null ? cargaMasivaDatosCompItem.getErrores() : "");
		
		LOGGER.debug(dateLog + " --> Fin CargaMasivaComprasServiceImpl convertItemtoHashCompra");
		return e;
	}

	@Transactional(timeout=24000)
	private Long uploadFileLog(byte[] bytes, CenCargamasiva cenCargamasiva, boolean isLog) throws IOException {
		// TODO Auto-generated method stub
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaComprasServiceImpl uploadFileLog");
		FicheroVo ficheroVo = new FicheroVo();

		String directorioFichero = getDirectorioFichero(cenCargamasiva.getIdinstitucion());
		ficheroVo.setDirectorio(directorioFichero);
		String nombreFicheroString = cenCargamasiva.getNombrefichero();
		ficheroVo.setNombre(nombreFicheroString); 
		ficheroVo.setDescripcion("Carga Masiva " + ficheroVo.getNombre());

		ficheroVo.setIdinstitucion(cenCargamasiva.getIdinstitucion());
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(SigaConstants.tipoExcelXls);

		ficheroVo.setUsumodificacion(Integer.valueOf(cenCargamasiva.getUsumodificacion()));
		ficheroVo.setFechamodificacion(new Date());
		
		ficherosServiceImpl.insert(ficheroVo);

		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		
		LOGGER.debug(dateLog + " --> Fin CargaMasivaComprasServiceImpl uploadFileLog");
		return ficheroVo.getIdfichero();
	}
	
	private List<CargaMasivaDatosCompItem> parseExcelFileComp(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario) {
		// TODO Auto-generated method stub
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaComprasServiceImpl parseExcelFileComp");

		List<CargaMasivaDatosCompItem> masivaComprasVos = new ArrayList<CargaMasivaDatosCompItem>();
		CargaMasivaDatosCompItem CargaMasivaDatosCompItem = null;
		
		HashMap<String, PysPeticioncomprasuscripcion> compraHashtable = new HashMap<String, PysPeticioncomprasuscripcion>();
		PysPeticioncomprasuscripcion compra = null;

		Short idInstitucion = usuario.getIdinstitucion();
		String idLenguaje = usuario.getIdlenguaje();

		GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
		genRecursosCatalogosKey.setIdlenguaje(idLenguaje);

		StringBuffer errorLinea = null;
		int numLinea = 1;
		for (Hashtable<String, Object> hashtable : datos) {
			CargaMasivaDatosCompItem = new CargaMasivaDatosCompItem();

			CargaMasivaDatosCompItem.setIdInstitucion(idInstitucion);
			errorLinea = new StringBuffer();
			
			if(hashtable.get(SigaConstants.CP_NCOLEGIADO)!=null && !hashtable.get(SigaConstants.CP_NCOLEGIADO).toString().equals("") &&
					!hashtable.get(SigaConstants.CP_NCOLEGIADO).toString().equals("nnnnn") &&	!hashtable.get(SigaConstants.CP_NCOLEGIADO).toString().equals("Requerido")){
				CargaMasivaDatosCompItem.setNumColegiadoCliente(hashtable.get(SigaConstants.CP_NCOLEGIADO).toString());

				CenColegiadoExample colegiadoExample = new CenColegiadoExample();

				colegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andNcolegiadoEqualTo(CargaMasivaDatosCompItem.getNumColegiadoCliente());								

				if(!cenColegiadoMapper.selectByExample(colegiadoExample).isEmpty()) {
					CargaMasivaDatosCompItem.setIdPersona(cenColegiadoMapper.selectByExample(colegiadoExample).get(0).getIdpersona());
				}
				else {
					errorLinea.append("Cliente no encontrado por Num Colegiado. ");
				}
			}else if(hashtable.get(SigaConstants.CP_NIF)!=null && !hashtable.get(SigaConstants.CP_NIF).toString().equals("") &&
					!hashtable.get(SigaConstants.CP_NIF).toString().equals("nnnnn") &&	!hashtable.get(SigaConstants.CP_NIF).toString().equals("Requerido")){

				LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna NIF CLIENTE");

				CargaMasivaDatosCompItem.setNifCifCliente(hashtable.get(SigaConstants.CP_NIF).toString());

				CenPersonaExample personaExample = new CenPersonaExample();

				personaExample.createCriteria().andNifcifEqualTo(CargaMasivaDatosCompItem.getNifCifCliente());	

				if(!cenPersonaMapper.selectByExample(personaExample).isEmpty()) {
					CargaMasivaDatosCompItem.setIdPersona(cenPersonaMapper.selectByExample(personaExample).get(0).getIdpersona());
				}else {
					errorLinea.append("Cliente no encontrado por Nif. ");
				}
			}else{
				errorLinea.append("Es obligatorio introducir el numero de colegiado o el NIF del cliente. ");
			}
				
			LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna APELLIDOS CLIENTE");
			if (hashtable.get(SigaConstants.CP_APELLIDOS_CLI) != null && !hashtable.get(SigaConstants.CP_APELLIDOS_CLI).toString().equals("") &&
				!hashtable.get(SigaConstants.CP_APELLIDOS_CLI).toString().equals("aaaaaaaaaaaaa") && !hashtable.get(SigaConstants.CP_APELLIDOS_CLI).toString().equals("Requerido")) {
				CargaMasivaDatosCompItem.setApellidosCliente(hashtable.get(SigaConstants.CP_APELLIDOS_CLI).toString());
			}else{
				errorLinea.append("Es obligatorio introducir los apellidos del cliente. ");
			}
			
			LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna NOMBRE CLIENTE");
			if (hashtable.get(SigaConstants.CP_NOMBRE_CLI) != null && !hashtable.get(SigaConstants.CP_NOMBRE_CLI).toString().equals("") &&
				!hashtable.get(SigaConstants.CP_NOMBRE_CLI).toString().equals("aaaaaaaaaaaaa") && !hashtable.get(SigaConstants.CP_NOMBRE_CLI).toString().equals("Requerido")) {
				CargaMasivaDatosCompItem.setNombreCliente(hashtable.get(SigaConstants.CP_NOMBRE_CLI).toString());
			}else{
				errorLinea.append("Es obligatorio introducir el nombre del cliente. ");
			}
			
			LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna CANTIDAD PRODUCTO");
			if(hashtable.get(SigaConstants.CP_CANT_PROD)!=null && !hashtable.get(SigaConstants.CP_CANT_PROD).toString().equals("") && isNumber(hashtable.get(SigaConstants.CP_CANT_PROD).toString()) &&
				!hashtable.get(SigaConstants.CP_CANT_PROD).toString().equals("nnnnn") &&	!hashtable.get(SigaConstants.CP_CANT_PROD).toString().equals("Requerido")){
				CargaMasivaDatosCompItem.setCantidadProducto(hashtable.get(SigaConstants.CP_CANT_PROD).toString());
			}else{
				errorLinea.append("Es obligatorio introducir la cantidad del producto en formato de número entero. ");
			}
			
			LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna NOMBRE CLIENTE");
			if (hashtable.get(SigaConstants.CP_NOMBRE_PROD) != null && !hashtable.get(SigaConstants.CP_NOMBRE_PROD).toString().equals("") &&
				!hashtable.get(SigaConstants.CP_NOMBRE_PROD).toString().equals("aaaaaaaaaaaaa") && !hashtable.get(SigaConstants.CP_NOMBRE_PROD).toString().equals("Requerido")) {
				CargaMasivaDatosCompItem.setNombreProducto(hashtable.get(SigaConstants.CP_NOMBRE_PROD).toString());
			}else{
				errorLinea.append("Es obligatorio introducir el nombre del producto. ");
			}
			
			LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna CODIGO PRODUCTO");
			if(hashtable.get(SigaConstants.CP_COD_PROD)!=null && !hashtable.get(SigaConstants.CP_COD_PROD).toString().equals("") &&
			   !hashtable.get(SigaConstants.CP_COD_PROD).toString().equals("aaaaaaaaaaaaa") && !hashtable.get(SigaConstants.CP_COD_PROD).toString().equals("Requerido")){
				CargaMasivaDatosCompItem.setCodigoProducto(hashtable.get(SigaConstants.CP_COD_PROD).toString());
				
				//Se comprueba si existe un producto con el codigo introducido
				PysProductosinstitucionExample productoInsExample = new PysProductosinstitucionExample();
					
				productoInsExample.createCriteria().andCodigoextEqualTo(CargaMasivaDatosCompItem.getCodigoProducto());
				
				List<PysProductosinstitucion> productosCodigo = pysProductosinstitucionMapper.selectByExample(productoInsExample);
				

				if(!productosCodigo.isEmpty()) {
					
					//Se compruba si el producto introducido es de la categoria de certificados.
					//En tal caso, no se permite la compra.
					if(productosCodigo.get(0).getIdtipoproducto() == 18) {
						errorLinea.append("No se puede realizar la compra masiva de certificados (se debe hacer desde el modulo de certificados). ");
					}
					else {
						PysProductosinstitucion prodIns = productosCodigo.get(0);
						CargaMasivaDatosCompItem.setIdProducto(prodIns.getIdproducto());
						CargaMasivaDatosCompItem.setIdProductoInstitucion(prodIns.getIdproductoinstitucion());
						CargaMasivaDatosCompItem.setIdTipoProducto(prodIns.getIdtipoproducto());
						CargaMasivaDatosCompItem.setValor(prodIns.getValor());
						CargaMasivaDatosCompItem.setIdTipoIva(prodIns.getIdtipoiva());
						CargaMasivaDatosCompItem.setNoFacturable(prodIns.getNofacturable());
					}
				}
				else {
					errorLinea.append("Producto no encontrado por codigo de producto. ");
				}
			}else{
				errorLinea.append("Es obligatorio introducir codigo del producto. ");
			}
			
			LOGGER.debug("parseExcelFileComp() / Obtenemos los datos de la columna FECHA COMPRA");
			if(hashtable.get(SigaConstants.CP_FECHA_COMP)!=null && !hashtable.get(SigaConstants.CP_FECHA_COMP).toString().equals("") &&
					!hashtable.get(SigaConstants.CP_FECHA_COMP).toString().equals("dd/mm/yyyy") && !hashtable.get(SigaConstants.CP_FECHA_COMP).toString().equals("Requerido")){
				try {
					CargaMasivaDatosCompItem.setFechaCompra(new SimpleDateFormat("dd-MM-yyyy").parse(hashtable.get(SigaConstants.CP_FECHA_COMP).toString()));
				} catch (ParseException e) {
					errorLinea.append("Fecha de compra. Debe ser dd/mm/yyyy. ");
				}
			}else{
				errorLinea.append("Es obligatorio introducir la fecha de compra. ");
			}

			if (!errorLinea.toString().isEmpty()) {
				CargaMasivaDatosCompItem.setErrores("Errores en la línea " + numLinea + " : " + errorLinea.toString() + "<br/>");
			}

			masivaComprasVos.add(CargaMasivaDatosCompItem);
			numLinea++;
		}

		LOGGER.debug(dateLog + " --> Fin CargaMasivaComprasServiceImpl parseExcelFileComp");
		return masivaComprasVos;
	}
	
	private String getDirectorioFichero(Short idInstitucion) {
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio CargaMasivaComprasServiceImpl getDirectorioFicheroSigaClassique");

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathCV = genPropertiesPath.get(0).getValor(); 
		
		StringBuffer directorioFichero = new StringBuffer(pathCV);
		directorioFichero.append(idInstitucion);
		directorioFichero.append(File.separator);

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaCV");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.debug(dateLog + " --> Fin CargaMasivaComprasServiceImpl getDirectorioFicheroSigaClassique");
		return directorioFichero.toString();
	}
	
	private boolean isNumber(String numeroString) {
		
		try {
			Integer.parseInt(numeroString);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
		
	}

	@Override
	public ResponseEntity<InputStreamResource> descargarFicheros(List<CargaMasivaComprasItem> cargaMasivaComprasItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		LOGGER.info("descargarFicheros() -> Entrada al servicio para generar la plantilla de errores");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;

		try {

			if(idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				LOGGER.debug(
						"descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.debug(
						"descargarFicheros() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");
				
				if (null != usuarios && usuarios.size() > 0) {
					if(cargaMasivaComprasItem.size() == 1) {
						res = soloUnArchivo(cargaMasivaComprasItem, idInstitucion);
					}else {
						res = getZipFile(cargaMasivaComprasItem, idInstitucion);
					}
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error(
					"descargarFicheros() -> Se ha producido un error al descargar archivos asociados a la carga masiva de compras",
					e);
		}
		
		LOGGER.info("descargarFicheros() -> Salida del servicio para generar la plantilla de errores");
		
		return res;
	}

	private ResponseEntity<InputStreamResource> soloUnArchivo(List<CargaMasivaComprasItem> cargaMasivaComprasItem, Short idInstitucion) throws Exception {
		
		byte[] buf = {};
		
		InputStream fileStream = new ByteArrayInputStream(buf);
		
		ResponseEntity<InputStreamResource> res = null;

		LOGGER.debug("descargarFicheros() -> Se busca el fichero original");

		String pathClassique = getDirectorioFichero(idInstitucion);
		pathClassique += File.separator + idInstitucion + "_" + cargaMasivaComprasItem.get(0).getIdFicheroLog()  + "." + SigaConstants.tipoExcelXls;
		
		HttpHeaders headersClassique = new HttpHeaders();
		headersClassique.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		headersClassique.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + cargaMasivaComprasItem.get(0).getNombreFichero());
		headersClassique.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
		
		try {
			File fileClassique = new File(pathClassique);
			
			if(fileClassique.exists()) {
				fileStream = new FileInputStream(fileClassique);
				headersClassique.setContentLength(fileClassique.length());
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headersClassique, HttpStatus.OK);
			}else {
				LOGGER.debug("descargarFicheros() -> No encuentra el fichero original");
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headersClassique, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception eClassique) {
			LOGGER.debug("descargarFicheros() -> " + eClassique.getStackTrace());
			throw eClassique;
		}
		return res;
	}

	private ResponseEntity<InputStreamResource> getZipFile(List<CargaMasivaComprasItem> cargaMasivaComprasItem,
			Short idInstitucion) {
		// TODO Auto-generated method stub
		
		HttpHeaders headersClassique = new HttpHeaders();
		headersClassique.setContentType(MediaType.parseMediaType("application/zip"));

		headersClassique.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CargaMasivaCompras.zip");
		headersClassique.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
		
		ByteArrayOutputStream byteArrayOutputStream = null;
		ResponseEntity<InputStreamResource> res = null;

		byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
		
		List<CargaMasivaComprasItem> cargaMasiva = new ArrayList<CargaMasivaComprasItem>();
		
		int ficherosEncontrados = 0;
				
		try {
			for (CargaMasivaComprasItem doc : cargaMasivaComprasItem) {
					LOGGER.debug("descargarFicheros() -> Se busca el fichero original");
					String pathClassique = getDirectorioFichero(idInstitucion);
					pathClassique += File.separator + idInstitucion + "_" + doc.getIdFicheroLog() + "." + SigaConstants.tipoExcelXls;
					
					try {
						File fileClassique = new File(pathClassique);
						FileInputStream fileInputStream = new FileInputStream(fileClassique);
						zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
						IOUtils.copy(fileInputStream, zipOutputStream);
						fileInputStream.close();
						
						ficherosEncontrados++;
						
						cargaMasiva.add(doc);
						
					}catch(FileNotFoundException eClassique) {
						LOGGER.debug("descargarFicheros() -> No encuentra el fichero original");
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
		
		if(ficherosEncontrados == 1) {
			try {
				res = soloUnArchivo(cargaMasiva, idInstitucion);
			} catch (Exception e) {
				LOGGER.debug("Error al descarga fichero -> " + e.getStackTrace());
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), headersClassique, HttpStatus.NO_CONTENT);
			}
		}else{
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), headersClassique, ficherosEncontrados > 1 ? HttpStatus.OK : HttpStatus.NO_CONTENT);			
		}

		return res;
	}

}
