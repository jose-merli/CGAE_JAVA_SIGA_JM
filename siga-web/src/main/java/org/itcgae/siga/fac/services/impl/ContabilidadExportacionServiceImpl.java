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
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.AbonoContabilidadItem;
import org.itcgae.siga.DTO.fac.AltaAnticipoItem;
import org.itcgae.siga.DTO.fac.AnticiposPySItem;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasItem;
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
import org.itcgae.siga.db.entities.AdmUsuariosExample;
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
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.PysAnticipoletrado;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.FacBancoinstitucionMapper;
import org.itcgae.siga.db.mappers.FacFacturaincluidaendisqueteMapper;
import org.itcgae.siga.db.mappers.FacLineadevoludisqbancoMapper;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.PysAnticipoletradoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ContabilidadExportacionServiceImpl implements IContabilidadExportacionService {
	
	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private FacRegistroFichContaExtendsMapper facRegistroFichContaExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired 
	private GeneracionFicheroContabilidadService generacionFicheroContabilidadService;
	
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
				
				beanRegistro.setNumeroasientos(0L);

				int resultado = facRegistroFichContaExtendsMapper.insertSelective(beanRegistro);

				if (resultado == 1) {
					updateResponseDTO.setStatus(SigaConstants.CODE_200.toString());
					updateResponseDTO.setId(beanRegistro.getIdcontabilidad().toString());
					
					FacRegistrofichconta ficheroProgramado = facRegistroFichContaExtendsMapper.selectByPrimaryKey(beanRegistro);
					
					generacionFicheroContabilidadService.generarFicheroContabilidadAsincrono(ficheroProgramado, usuario.getIdlenguaje(), String.valueOf(idInstitucion) ,String.valueOf(usuario.getIdusuario()));
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
		public ResponseEntity<InputStreamResource> descargarFicherosContabilidad(List <FacRegistroFichConta> facRegistrosFichConta, HttpServletRequest request) {
			// TODO Auto-generated method stub
			LOGGER.info("descargarFicherosContabilidad() -> Entrada al servicio para descargar los excel de exportaciones de contabilidad");

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
						if(facRegistrosFichConta.size() == 1) {
							res = soloUnArchivo(facRegistrosFichConta, idInstitucion);
						}else {
							res = getZipFile(facRegistrosFichConta, idInstitucion);
						}
					}
					
				}
				
			} catch (Exception e) {
				LOGGER.error(
						"descargarFicheros() -> Se ha producido un error al descargar archivos asociados a la exportacion de contabilidad",
						e);
			}
			
			LOGGER.info("descargarFicheros() -> Salida del servicio para descargar los excel de exportaciones de contabilidad");
			
			return res;
		}
		
		private ResponseEntity<InputStreamResource> soloUnArchivo(List <FacRegistroFichConta> facRegistrosFichConta, Short idInstitucion) throws Exception {
			
			byte[] buf = {};
			
			InputStream fileStream = new ByteArrayInputStream(buf);
			
			ResponseEntity<InputStreamResource> res = null;

			LOGGER.debug("descargarFicherosContabilidad() -> Se busca el fichero original");

			String pathClassique = getDirectorioFichero(idInstitucion);
			pathClassique += File.separator + idInstitucion + File.separator + facRegistrosFichConta.get(0).getNombreFichero();
			
			//String pathClassique = "C:/Users/aavalosmoreno/Desktop/"+ facRegistrosFichConta.get(0).getNombreFichero(); //PRUEBA LOCAL
			
			HttpHeaders headersClassique = new HttpHeaders();
			headersClassique.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
			headersClassique.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + facRegistrosFichConta.get(0).getNombreFichero());
			headersClassique.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
			
			try {
				File fileClassique = new File(pathClassique);
				
				if(fileClassique.exists()) {
					fileStream = new FileInputStream(fileClassique);
					headersClassique.setContentLength(fileClassique.length());
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headersClassique, HttpStatus.OK);
				}else {
					LOGGER.debug("descargarFicherosContabilidad() -> No encuentra el fichero original");
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headersClassique, HttpStatus.NO_CONTENT);
				}
				
			} catch (Exception eClassique) {
				LOGGER.debug("descargarFicherosContabilidad() -> " + eClassique.getStackTrace());
				throw eClassique;
			}
			return res;
		}
		
		private ResponseEntity<InputStreamResource> getZipFile(List <FacRegistroFichConta> facRegistrosFichConta,
				Short idInstitucion) {
			
			HttpHeaders headersClassique = new HttpHeaders();
			headersClassique.setContentType(MediaType.parseMediaType("application/zip"));

			headersClassique.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ExportacionContabilidad.zip");
			headersClassique.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
			
			ByteArrayOutputStream byteArrayOutputStream = null;
			ResponseEntity<InputStreamResource> res = null;

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
			
			List<FacRegistroFichConta> exportacionContabilidad = new ArrayList<FacRegistroFichConta>();
			
			int ficherosEncontrados = 0;
					
			try {
				for (FacRegistroFichConta doc : facRegistrosFichConta) {
						LOGGER.debug("descargarFicheros() -> Se busca el fichero original");
						String pathClassique = getDirectorioFichero(idInstitucion);
						pathClassique += File.separator + idInstitucion + File.separator + facRegistrosFichConta.get(0).getNombreFichero();
						
						//String pathClassique = "C:/Users/aavalosmoreno/Desktop/"+ doc.getNombreFichero(); //PRUEBA LOCAL
						
						try {
							File fileClassique = new File(pathClassique);
							FileInputStream fileInputStream = new FileInputStream(fileClassique);
							zipOutputStream.putNextEntry(new ZipEntry(doc.getNombreFichero()));
							IOUtils.copy(fileInputStream, zipOutputStream);
							fileInputStream.close();
							
							ficherosEncontrados++;
							
							exportacionContabilidad.add(doc);
							
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
					res = soloUnArchivo(exportacionContabilidad, idInstitucion);
				} catch (Exception e) {
					LOGGER.debug("Error al descarga fichero -> " + e.getStackTrace());
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), headersClassique, HttpStatus.NO_CONTENT);
				}
			}else{
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())), headersClassique, ficherosEncontrados > 1 ? HttpStatus.OK : HttpStatus.NO_CONTENT);			
			}

			return res;
		}
		
		private String getDirectorioFichero(Short idInstitucion) {
			Date dateLog = new Date();
			LOGGER.debug(dateLog + " --> Inicio ContabilidadExportacionServiceImpl getDirectorioFicheroSigaClassique");

			String pathCV = "";
			
			// Extraer propiedad
			GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
			genPropertiesExampleP.createCriteria().andParametroEqualTo("contabilidad.directorioFisicoContabilidad");
			List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
			
			return pathCV = genPropertiesPath.get(0).getValor(); 
			
		}
		
		


}
