package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.CargaMasivaDatosCVItem;
import org.itcgae.siga.DTOs.cen.SubtiposCVItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.ICargasMasivasCVService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.CEN_TIPOCAMBIO;
import org.itcgae.siga.commons.constants.SigaConstants.CargaMasivaDatosCVVo;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenDatoscvKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenTiposcv;
import org.itcgae.siga.db.entities.CenTiposcvExample;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo1Example;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2;
import org.itcgae.siga.db.entities.CenTiposcvsubtipo2Example;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.mappers.CenDatoscvMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.CenTiposcvMapper;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo1Mapper;
import org.itcgae.siga.db.mappers.CenTiposcvsubtipo2Mapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class CargasMasivasCVServiceImpl implements ICargasMasivasCVService {
	private Logger LOGGER = Logger.getLogger(CargasMasivasCVServiceImpl.class);

	@Autowired
	private ICargasMasivasCVService iCargasMasivasCVService;
	@Autowired
	private CenPersonaMapper cenPersonaMapper;
	@Autowired 
	private CenTiposcvMapper cenTiposcvMapper;
	@Autowired 
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;
	@Autowired
	private CenTiposcvsubtipo1Mapper cenTiposcvsubtipo1Mapper;
	@Autowired
	private CenTiposcvsubtipo2Mapper cenTiposcvsubtipo2Mapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private CenDatoscvMapper cenDatoscvMapper;
	
	@Override
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector)
			throws BusinessException {
		if(orderList ==null && datosVector==null)
			throw new BusinessException("No hay datos para crear el fichero");
		if(orderList ==null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = ExcelHelper.createExcelFile(orderList , datosVector, iCargasMasivasCVService.nombreFicheroEjemplo);
		return XLSFile;
	}

	@Override
	public UpdateResponseDTO uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		LOGGER.info("uploadFile() -> Entrada al servicio para guardar un archivo");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		// Coger archivo del request
		LOGGER.debug("uploadFile() -> Coger archivo del request");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		
		
		// Extraer la información del excel
		LOGGER.debug("uploadFile() -> Extraer los datos del archivo");
		Vector<Hashtable<String, Object>> datos = ExcelHelper.parseExcelFile(file.getBytes());

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				List<CargaMasivaDatosCVItem> cargaMasivaDatosCVItems = parseExcelFile(datos, usuario);
				
				for (CargaMasivaDatosCVItem cargaMasivaDatosCVItem : cargaMasivaDatosCVItems) {
					if(cargaMasivaDatosCVItem.getErrores() != null) {
						
						// Comprobar si el registro no existe
						CenDatoscvKey cenDatoscvKey = new CenDatoscvKey();
						cenDatoscvKey.setIdcv(cargaMasivaDatosCVItem.getIdTipoCV());
						cenDatoscvKey.setIdinstitucion(usuario.getIdinstitucion());
						cenDatoscvKey.setIdpersona(cargaMasivaDatosCVItem.getIdPersona());
						
						CenDatoscv cenDatoscv = cenDatoscvMapper.selectByPrimaryKey(cenDatoscvKey);
						
						// No existe ese registro --> procedemos a insertarlo
						if(cenDatoscv == null) {
							CenDatoscv cenDatosCV = new CenDatoscv();
							cenDatosCV.setCertificado("1");
							cenDatosCV.setCreditos(cargaMasivaDatosCVItem.getCreditos());
							cenDatosCV.setDescripcion(cargaMasivaDatosCVItem.getDescripcion());
							cenDatosCV.setFechabaja(cargaMasivaDatosCVItem.getFechaFin());
							cenDatosCV.setFechainicio(cargaMasivaDatosCVItem.getFechaInicio());
							cenDatosCV.setFechamodificacion(new Date());
							cenDatosCV.setFechamovimiento(cargaMasivaDatosCVItem.getFechaVerificacion());
							cenDatosCV.setIdinstitucion(usuario.getIdinstitucion());
							//cenDatosCV.setIdinstitucioncargo(cargaMasivaDatosCVItem.get);
							cenDatosCV.setIdinstitucionSubt1(cargaMasivaDatosCVItem.getIdinstitucionSubt1());
							cenDatosCV.setIdinstitucionSubt2(cargaMasivaDatosCVItem.getIdTipoCVSubtipo2());
							cenDatosCV.setIdpersona(cargaMasivaDatosCVItem.getIdPersona());
							//cenDatosCV.setIdcv();
							cenDatosCV.setIdtipocv(cargaMasivaDatosCVItem.getIdTipoCV());
							cenDatosCV.setIdtipocvsubtipo1(cargaMasivaDatosCVItem.getIdTipoCVSubtipo1());
							cenDatosCV.setIdtipocvsubtipo2(cargaMasivaDatosCVItem.getIdTipoCVSubtipo2());
							cenDatosCV.setUsumodificacion(0);
							
							cenDatoscvMapper.insertSelective(cenDatosCV);
						}
						
						updateResponseDTO.setStatus(SigaConstants.OK);
					}else {
						Error error = updateResponseDTO.getError();
						error.setDescription(cargaMasivaDatosCVItem.getErrores());
						updateResponseDTO.setError(error);
						
						updateResponseDTO.setStatus(SigaConstants.KO);
						
						break;
					}
				}
			}
		}
		
		return updateResponseDTO;
	}

	
	private List<CargaMasivaDatosCVItem> parseExcelFile(Vector<Hashtable<String, Object>> datos, AdmUsuarios usuario) throws BusinessException {

		List<CargaMasivaDatosCVItem> masivaDatosCVVos = new ArrayList<CargaMasivaDatosCVItem>();
		CargaMasivaDatosCVItem cargaMasivaDatosCVItem  = null; 
		
		Hashtable<String, SubtiposCVItem> tipoCvHashtable = new Hashtable<String, SubtiposCVItem>();
		Hashtable<String, SubtiposCVItem> subtipo1CvHashtable = new Hashtable<String, SubtiposCVItem>();
		Hashtable<String, SubtiposCVItem> subtipo2CvHashtable = new Hashtable<String, SubtiposCVItem>();
		Hashtable<Long, String> personaHashtable = new Hashtable<Long, String>();

		SubtiposCVItem tipoCVVo = null;
		SubtiposCVItem subtipoCV1Vo = null;
		SubtiposCVItem subtipoCV2Vo = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		StringBuffer errorLinea = null;
		for (Hashtable<String, Object> hashtable : datos) {
			cargaMasivaDatosCVItem = new CargaMasivaDatosCVItem();
			
			errorLinea = new StringBuffer();
			if(hashtable.get(CargaMasivaDatosCVVo.C_CREDITOS.getCampo())!=null && !hashtable.get(CargaMasivaDatosCVVo.C_CREDITOS.getCampo()).toString().equals("")){
				try {
					cargaMasivaDatosCVItem.setCreditos(Long.valueOf((String)hashtable.get(CargaMasivaDatosCVVo.C_CREDITOS.getCampo())));	
				} catch (NumberFormatException e) {
					LOGGER.debug("Los creditos debe ser numericos:"+hashtable.get(CargaMasivaDatosCVVo.C_CREDITOS.getCampo()));
					errorLinea.append("Creditos debe ser numérico - Error línea: "+ hashtable.keys());
				}
			}


			// Llamada a método para obtener idpersona
			// si la institucion es el CGAE no miramos en Colegiado porque no tiene sentido ya que no va a encontrar nada
			if(hashtable.get(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo())!=null &&	!hashtable.get(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo()).toString().equals(""))
				cargaMasivaDatosCVItem.setColegiadoNumero((String)hashtable.get(CargaMasivaDatosCVVo.COLEGIADONUMERO.getCampo()));
			if(hashtable.get(CargaMasivaDatosCVVo.PERSONANIF.getCampo())!=null &&	!hashtable.get(CargaMasivaDatosCVVo.PERSONANIF.getCampo()).toString().equals(""))
				cargaMasivaDatosCVItem.setPersonaNIF((String)hashtable.get(CargaMasivaDatosCVVo.PERSONANIF.getCampo()));
			if(cargaMasivaDatosCVItem.getColegiadoNumero()!=null ||cargaMasivaDatosCVItem.getPersonaNIF()!=null){
				
				try {
					
					CenPersonaExample cenPersonaExample = new CenPersonaExample();
					cenPersonaExample.createCriteria().andNifcifEqualTo(cargaMasivaDatosCVItem.getPersonaNIF());
					List<CenPersona> cenPersona = cenPersonaMapper.selectByExample(cenPersonaExample);
						
					cargaMasivaDatosCVItem.setIdPersona(cenPersona.get(0).getIdpersona());
				} catch (Exception e) {
					errorLinea.append(e.getMessage()+". ");
					cargaMasivaDatosCVItem.setPersonaNombre("Error");
				}

				if(cargaMasivaDatosCVItem.getIdPersona()!=null){
					String nombreString = null;
					if(!personaHashtable.containsKey(cargaMasivaDatosCVItem.getIdPersona())){
						
						CenPersona cenPersona = cenPersonaMapper.selectByPrimaryKey(cargaMasivaDatosCVItem.getIdPersona());
						StringBuffer nombre = new StringBuffer();
						nombre.append(cenPersona.getNombre());
						nombre.append(" ");
						nombre.append(cenPersona.getApellidos1());
						if(cenPersona.getApellidos2()!=null){
							nombre.append(" ");
							nombre.append(cenPersona.getApellidos2());
						}

						cargaMasivaDatosCVItem.setPersonaNombre(nombre.toString());
						nombreString = cargaMasivaDatosCVItem.getPersonaNombre();

					}else{
						nombreString = personaHashtable.get(cargaMasivaDatosCVItem.getIdPersona());
						cargaMasivaDatosCVItem.setPersonaNombre(nombreString);
					}

					personaHashtable.put(cargaMasivaDatosCVItem.getIdPersona(), nombreString);

				}
			}else{
				errorLinea.append("Es obligatorio introducir número de colegiado o nif/cif- Error línea: "+ hashtable.keys());
				cargaMasivaDatosCVItem.setPersonaNombre("Error");
			}
			

			if(hashtable.get(CargaMasivaDatosCVVo.C_DESCRIPCION.getCampo())!=null)
				cargaMasivaDatosCVItem.setDescripcion((String)hashtable.get(CargaMasivaDatosCVVo.C_DESCRIPCION.getCampo()));



			if(hashtable.get(CargaMasivaDatosCVVo.C_FECHAFIN.getCampo())!=null)
				try {
					cargaMasivaDatosCVItem.setFechaFin(sdf.parse((String)hashtable.get(CargaMasivaDatosCVVo.C_FECHAFIN.getCampo())));
				} catch (ParseException e1) {
					errorLinea.append("Fecha Fin mal introducida - Error línea: "+ hashtable.keys());

				}
			if(hashtable.get(CargaMasivaDatosCVVo.C_FECHAINICIO.getCampo())!=null)
				try {
					cargaMasivaDatosCVItem.setFechaInicio(sdf.parse((String)hashtable.get(CargaMasivaDatosCVVo.C_FECHAINICIO.getCampo())));
				} catch (ParseException e1) {
					errorLinea.append("Fecha Inicio mal introducida - Error línea: "+ hashtable.keys());
				}
			
			if(cargaMasivaDatosCVItem.getFechaInicio()!=null && cargaMasivaDatosCVItem.getFechaFin()!=null && cargaMasivaDatosCVItem.getFechaInicio().compareTo(cargaMasivaDatosCVItem.getFechaFin())>0 ){
				errorLinea.append("La fecha de inicio no puede ser posterior a la fecha fin - Error línea: "+ hashtable.keys());
			}
			if(hashtable.get(CargaMasivaDatosCVVo.FECHAVERIFICACION.getCampo())!=null)
				try {
					cargaMasivaDatosCVItem.setFechaVerificacion(sdf.parse((String)hashtable.get(CargaMasivaDatosCVVo.FECHAVERIFICACION.getCampo())));
				} catch (ParseException e1) {
					errorLinea.append("Fecha Verificacion mal introducida - Error línea: "+ hashtable.keys());
				}

			if(hashtable.get(CargaMasivaDatosCVVo.TIPOCVCOD.getCampo())!=null){

				Short tipocvCod = new Short(String.valueOf(hashtable.get(CargaMasivaDatosCVVo.TIPOCVCOD.getCampo())));
				if(!tipoCvHashtable.containsKey(Short.toString(tipocvCod))){
					tipoCVVo = new SubtiposCVItem();
					
					//Llamada a método para obtener idtipocv
					CenTiposcvExample cenTiposCVExample = new CenTiposcvExample();
					cenTiposCVExample.createCriteria().andCodigoextEqualTo(Short.toString(tipocvCod));
					List<CenTiposcv> tiposCV  = cenTiposcvMapper.selectByExample(cenTiposCVExample);
					
					if(tiposCV!=null && tiposCV.size()>0){
						tipoCVVo.setIdtipocv(tiposCV.get(0).getIdtipocv());
						
						GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
						genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
						genRecursosCatalogosKey.setIdrecurso(tiposCV.get(0).getDescripcion());
						
						GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosMapper.selectByPrimaryKey(genRecursosCatalogosKey);
						
						tipoCVVo.setTipocvDescripcion(genRecursosCatalogos.getDescripcion());
					}
				}else{
					tipoCVVo = tipoCvHashtable.get(Short.toString(tipocvCod));
				}
				tipoCvHashtable.put(Short.toString(tipocvCod), tipoCVVo);
				if(tipoCVVo.getTipocvDescripcion()!=null){
					cargaMasivaDatosCVItem.setTipoCVNombre(tipoCVVo.getTipocvDescripcion());
					cargaMasivaDatosCVItem.setIdTipoCV(tipoCVVo.getIdTipocv());
				}else{
					cargaMasivaDatosCVItem.setTipoCVNombre("Error");
					errorLinea.append("No se ha encontrado el tipo CV - Error línea: "+ hashtable.keys());
				}
				cargaMasivaDatosCVItem.setTipoCVCOD(tipocvCod);

			}else{
				errorLinea.append("Es obligatorio introducir número de colegiado o nif/cif - Error línea: "+ hashtable.keys());
				cargaMasivaDatosCVItem.setPersonaNombre("Error");
			}
			
			//SI Tiene subtipos1 es obligatorio meter alguno

			if(cargaMasivaDatosCVItem.getIdTipoCV()!=null){

				CenTiposcvsubtipo1Example cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();
				cenTiposcvsubtipo1Example.createCriteria().andIdinstitucionEqualTo(usuario.getIdinstitucion()).andIdtipocvEqualTo(cargaMasivaDatosCVItem.getIdTipoCV());
				List<CenTiposcvsubtipo1> tiposcvsubtipo1s =  cenTiposcvsubtipo1Mapper.selectByExample(cenTiposcvsubtipo1Example);

				if(tiposcvsubtipo1s!=null&&tiposcvsubtipo1s.size()>0 && hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo())==null){
					errorLinea.append("Al existir subtipos 1 para este tipo de cv es obligatorio introducir el subtipo 1 ");
					cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");
				}else if(hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo())!=null && !hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo()).toString().equals("")){
					String subtipocv1Cod = (String)hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV1COD.getCampo());
					if(!subtipo1CvHashtable.containsKey(subtipocv1Cod)){
						subtipoCV1Vo = new SubtiposCVItem();
						//					Llamada a método para obtener idtipocv

						cenTiposcvsubtipo1Example = new CenTiposcvsubtipo1Example();

						cenTiposcvsubtipo1Example.createCriteria().andCodigoextEqualTo(subtipocv1Cod).andIdinstitucionEqualTo(usuario.getIdinstitucion()).andIdtipocvEqualTo(cargaMasivaDatosCVItem.getIdTipoCV());
						//cenTiposcvsubtipo1Example.setOrderByClause("DESC");

						tiposcvsubtipo1s =  cenTiposcvsubtipo1Mapper.selectByExample(cenTiposcvsubtipo1Example);
						
						if(tiposcvsubtipo1s!=null && tiposcvsubtipo1s.size()>0){
							subtipoCV1Vo.setSubTipo1IdTipo(tiposcvsubtipo1s.get(0).getIdtipocvsubtipo1());
							subtipoCV1Vo.setSubTipo1IdInstitucion(tiposcvsubtipo1s.get(0).getIdinstitucion());

							
							GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
							genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
							genRecursosCatalogosKey.setIdrecurso(tiposcvsubtipo1s.get(0).getDescripcion());
							
							GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosMapper.selectByPrimaryKey(genRecursosCatalogosKey);

							subtipoCV1Vo.setSubTipo1Descripcion(genRecursosCatalogos.getDescripcion());
						}

					}else{
						subtipoCV1Vo = subtipo1CvHashtable.get(subtipocv1Cod);
					}
					subtipo1CvHashtable.put(subtipocv1Cod, subtipoCV1Vo);
					if(subtipoCV1Vo.getSubTipo1Descripcion()!=null){
						cargaMasivaDatosCVItem.setSubTipoCV1Nombre(subtipoCV1Vo.getSubTipo1Descripcion());
						cargaMasivaDatosCVItem.setIdTipoCVSubtipo1(subtipoCV1Vo.getSubTipo1IdTipo());
						cargaMasivaDatosCVItem.setIdinstitucionSubt1(subtipoCV1Vo.getSubTipo1IdInstitucion());

					}else{
						cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");
						errorLinea.append("No se ha encontrado el subtipo 1 CV - Error línea: "+ hashtable.keys());
					}
					cargaMasivaDatosCVItem.setSubtipoCV1COD(subtipocv1Cod);

					if(cargaMasivaDatosCVItem.getIdTipoCVSubtipo1()!=null && hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo())!=null && !hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo()).toString().equals("")){
						String subtipocv2Cod = (String)hashtable.get(CargaMasivaDatosCVVo.SUBTIPOCV2COD.getCampo());
						if(!subtipo2CvHashtable.containsKey(subtipocv2Cod)){
							subtipoCV2Vo = new SubtiposCVItem();
							//					Llamada a método para obtener idtipocv

							CenTiposcvsubtipo2Example cenTiposcvsubtipo2Example = new CenTiposcvsubtipo2Example();
							cenTiposcvsubtipo2Example.createCriteria().andCodigoextEqualTo(subtipocv2Cod).andIdinstitucionEqualTo(usuario.getIdinstitucion()).andIdtipocvEqualTo(cargaMasivaDatosCVItem.getIdTipoCV());
							//cenTiposcvsubtipo2Example.setOrderByClause("DESC");

							List<CenTiposcvsubtipo2> tiposcvsubtipo2s =  cenTiposcvsubtipo2Mapper.selectByExample(cenTiposcvsubtipo2Example);
							
							if(tiposcvsubtipo2s!=null && tiposcvsubtipo2s.size()>0){
								subtipoCV2Vo.setSubTipo2IdTipo(tiposcvsubtipo2s.get(0).getIdtipocvsubtipo2());
								subtipoCV2Vo.setSubTipo2IdInstitucion(tiposcvsubtipo2s.get(0).getIdinstitucion());
								
								GenRecursosCatalogosKey genRecursosCatalogosKey = new GenRecursosCatalogosKey();
								genRecursosCatalogosKey.setIdlenguaje(usuario.getIdlenguaje());
								genRecursosCatalogosKey.setIdrecurso(tiposcvsubtipo2s.get(0).getDescripcion());
								
								GenRecursosCatalogos genRecursosCatalogos = genRecursosCatalogosMapper.selectByPrimaryKey(genRecursosCatalogosKey);
								
								subtipoCV2Vo.setSubTipo2Descripcion(genRecursosCatalogos.getDescripcion());
							}
						}else{
							subtipoCV2Vo = subtipo2CvHashtable.get(subtipocv2Cod);
						}
						subtipo2CvHashtable.put(subtipocv2Cod, subtipoCV2Vo);
						if(subtipoCV2Vo.getSubTipo2Descripcion()!=null){
							cargaMasivaDatosCVItem.setSubtipoCV2Nombre(subtipoCV2Vo.getSubTipo2Descripcion());
							cargaMasivaDatosCVItem.setIdTipoCVSubtipo2(subtipoCV2Vo.getSubTipo2IdTipo());
							cargaMasivaDatosCVItem.setIdinstitucionSubt2(subtipoCV2Vo.getSubTipo2IdInstitucion());

						}else{
							cargaMasivaDatosCVItem.setSubtipoCV2Nombre("Error");
							errorLinea.append("No se ha encontrado el subtipo 2 CV - Error línea: "+ hashtable.keys());
						}
						cargaMasivaDatosCVItem.setSubtipoCV2COD(subtipocv2Cod);
					}
				}else{
					errorLinea.append("Al existir subtipos 1 para este tipo de cv es obligatorio introducir el subtipo 1 - Error línea: "+ hashtable.keys());
					cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");

				}
			}else{
				cargaMasivaDatosCVItem.setSubtipoCV2Nombre("Error");
				cargaMasivaDatosCVItem.setSubTipoCV1Nombre("Error");
			}
			
			cargaMasivaDatosCVItem.setErrores(errorLinea.toString() + "\n");
			masivaDatosCVVos.add(cargaMasivaDatosCVItem);
		}
		return masivaDatosCVVos;
	}


}
