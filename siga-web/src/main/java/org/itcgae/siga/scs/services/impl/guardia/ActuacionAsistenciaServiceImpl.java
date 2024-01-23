package org.itcgae.siga.scs.services.impl.guardia;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.ScsActuacionasistcostefijoMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionasiMapper;
import org.itcgae.siga.db.mappers.ScsTipoactuacionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.*;
import org.itcgae.siga.scs.services.guardia.ActuacionAsistenciaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActuacionAsistenciaServiceImpl implements ActuacionAsistenciaService {

    private final Logger LOGGER = Logger.getLogger(ActuacionAsistenciaServiceImpl.class);

    @Autowired
    private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;

    @Autowired
    private ScsActuacionasistenciaExtendsMapper scsActuacionasistenciaExtendsMapper;
    
    @Autowired
    private ScsTipoactuacionMapper scsTipoactuacionMapper;

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private ScsActuacionasistcostefijoMapper scsActuacionasistcostefijoMapper;

    @Autowired
    private ScsJuzgadoExtendsMapper scsJuzgadoExtendsMapper;

    @Autowired
    private ScsComisariaExtendsMapper scsComisariaExtendsMapper;

    @Autowired
    private ScsTurnosExtendsMapper scsTurnosExtendsMapper;

    @Autowired
    private ScsDocumentacionasiMapper scsDocumentacionasiMapper;

    /**
     *
     * Metodo que busca los datos de las tarjetas plegadas en la ficha de Actuacion de Asistencia
     *
     * @param request
     * @param anioNumero
     * @param idActuacion
     * @return
     */
    @Override
    public ActuacionAsistenciaDTO searchTarjetaActuacion(HttpServletRequest request, String anioNumero, String idActuacion) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ActuacionAsistenciaDTO actuacionAsistenciaDTO = new ActuacionAsistenciaDTO();
        List<ActuacionAsistenciaItem> actuacionesItems = new ArrayList<>();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "searchTarjetaActuacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "searchTarjetaActuacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {

                    List<ActuacionAsistenciaItem> actuaciones = scsAsistenciaExtendsMapper.searchActuaciones(anioNumero.split("/")[0], anioNumero.split("/")[1], idInstitucion, Integer.valueOf(usuarios.get(0).getIdlenguaje()).intValue(),"S");

                    if(actuaciones != null && !actuaciones.isEmpty()) {

                        ActuacionAsistenciaItem actuacionAsistenciaItem = actuaciones.stream().filter(actuacion -> idActuacion.equals(actuacion.getIdActuacion())).findFirst().orElse(new ActuacionAsistenciaItem());

                        ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
                        scsAsistenciaKey.setIdinstitucion(idInstitucion);
                        scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                        scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));

                        ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                        scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(idActuacion));
                        scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                        scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                        scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                        ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);
                        ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

                        if (scsActuacionasistencia != null) {

                            //Obtenemos la descripcion
                            ScsActuacionasistcostefijoExample scsActuacionasistcostefijoExample = new ScsActuacionasistcostefijoExample();
                            scsActuacionasistcostefijoExample.createCriteria().andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
                                    .andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
                                    .andIdinstitucionEqualTo(idInstitucion)
                                    .andIdactuacionEqualTo(Long.valueOf(idActuacion))
                                    .andIdtipoactuacionEqualTo(scsActuacionasistencia.getIdtipoactuacion())
                                    .andIdtipoasistenciaEqualTo(scsAsistencia.getIdtipoasistencia());
                            //Buscamos la descripcion del coste fijo
                            List<ScsActuacionasistcostefijo> costesActuacion = scsActuacionasistcostefijoMapper.selectByExample(scsActuacionasistcostefijoExample);
                            if(costesActuacion != null && !costesActuacion.isEmpty()){
                                actuacionAsistenciaItem.setIdCoste(costesActuacion.get(0).getIdcostefijo().toString());
                                List<ComboItem> costes = scsActuacionasistenciaExtendsMapper.comboCosteFijoTipoActuacion(idActuacion,idInstitucion,scsAsistencia.getIdtipoasistencia(),Integer.valueOf(usuarios.get(0).getIdlenguaje()));

                                if(costes != null && !costes.isEmpty()){
                                    String costeDesc = costes.stream().filter(coste -> actuacionAsistenciaItem.getIdCoste().equals(coste.getValue())).findFirst().orElse(new ComboItem()).getLabel();
                                    actuacionAsistenciaItem.setCosteDesc(costeDesc);
                                }
                            }
                            //Buscamos el nombre de comisaria/Juzgado
                            if(scsActuacionasistencia.getIdjuzgado() != null){

                                List<ComboItem> juzgados = scsJuzgadoExtendsMapper.comboJuzgados(idInstitucion);
                                if(juzgados != null && !juzgados.isEmpty()){
                                   String juzgadoDesc =  juzgados.stream().filter(juzgado -> juzgado.getValue().equals(scsActuacionasistencia.getIdjuzgado().toString())).findFirst().orElse(new ComboItem()).getLabel();
                                   actuacionAsistenciaItem.setComisariaJuzgado(juzgadoDesc);
                                }

                            }else if(scsActuacionasistencia.getIdcomisaria() != null){
                                List<ComboItem> comisarias = scsComisariaExtendsMapper.comboCDetenciones(idInstitucion);
                                if(comisarias != null && !comisarias.isEmpty()){
                                    String comisariaDesc =  comisarias.stream().filter(comisaria -> comisaria.getValue().equals(scsActuacionasistencia.getIdcomisaria().toString())).findFirst().orElse(new ComboItem()).getLabel();
                                    actuacionAsistenciaItem.setComisariaJuzgado(comisariaDesc);
                                }
                            }

                            if("SÍ".equals(actuacionAsistenciaItem.getValidada())){
                                actuacionAsistenciaItem.setEstado("VALIDADA");
                            }
                            if(null != actuacionAsistenciaItem.getAnulada() && "1".equals(actuacionAsistenciaItem.getAnulada())){
                            	actuacionAsistenciaItem.setEstado("ANULADA");
                            }
                            //Buscamos la última operacion hecha
                            actuacionAsistenciaItem.setUltimaModificacion(this.searchHistorico(request, anioNumero, idActuacion).getResponseItems().get(0));

                            //Numero de documentos
                            ScsDocumentacionasiExample scsDocumentacionasiExample = new ScsDocumentacionasiExample();
                            scsDocumentacionasiExample.createCriteria()
                                    .andNumeroEqualTo(scsActuacionasistencia.getNumero())
                                    .andAnioEqualTo(scsActuacionasistencia.getAnio())
                                    .andIdinstitucionEqualTo(idInstitucion)
                                    .andIdactuacionEqualTo(scsActuacionasistencia.getIdactuacion());
                            List<ScsDocumentacionasi> documentos = scsDocumentacionasiMapper.selectByExample(scsDocumentacionasiExample);
                            if(documentos != null && !documentos.isEmpty()){
                                actuacionAsistenciaItem.setNumDocumentos(documentos.size()+"");
                            }

                        }
                        actuacionesItems.add(actuacionAsistenciaItem);
                        actuacionAsistenciaDTO.setActuacionAsistenciaItems(actuacionesItems);
                    }
                }
            }
        }catch(Exception e) {
            LOGGER.error("searchTarjetaActuacion() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar las actuaciones asociadas a la asistencia: " + e);
            error.description("Error al buscar las actuaciones asociadas a la asistencia: " + e);
            actuacionAsistenciaDTO.setError(error);
        }
        return actuacionAsistenciaDTO;
    }

    /**
     * Metodo que busca los datos de la tarjeta de datos generales para mostrarlos
     *
     * @param request
     * @param anioNumero
     * @param idActuacion
     * @return
     */
    @Override
    public DatosGeneralesActuacionAsistenciaDTO searchTarjetaDatosGenerales(HttpServletRequest request, String anioNumero, String idActuacion) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DatosGeneralesActuacionAsistenciaDTO datosGeneralesActuacionAsistenciaDTO = new DatosGeneralesActuacionAsistenciaDTO();
        List<DatosGeneralesActuacionAsistenciaItem> datosGenerales = new ArrayList<>();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "searchTarjetaDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "searchTarjetaDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {


                        ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
                        scsAsistenciaKey.setIdinstitucion(idInstitucion);
                        scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                        scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));

                        ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                        scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(idActuacion));
                        scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                        scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                        scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                        ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);
                        ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);

                        if (scsActuacionasistencia != null) {

                            ScsActuacionasistcostefijoExample scsActuacionasistcostefijoExample = new ScsActuacionasistcostefijoExample();
                            scsActuacionasistcostefijoExample.createCriteria().andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
                                    .andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
                                    .andIdinstitucionEqualTo(idInstitucion)
                                    .andIdactuacionEqualTo(Long.valueOf(idActuacion))
                                    .andIdtipoactuacionEqualTo(scsActuacionasistencia.getIdtipoactuacion())
                                    .andIdtipoasistenciaEqualTo(scsAsistencia.getIdtipoasistenciacolegio());

                            List<ScsActuacionasistcostefijo> costesActuacion = scsActuacionasistcostefijoMapper.selectByExample(scsActuacionasistcostefijoExample);

                            
                            
                            DatosGeneralesActuacionAsistenciaItem datosGeneralesActuacionAsistenciaItem = new DatosGeneralesActuacionAsistenciaItem();
                            datosGeneralesActuacionAsistenciaItem.setIdActuacion(scsActuacionasistencia.getIdactuacion().toString());
                            datosGeneralesActuacionAsistenciaItem.setTipoActuacion(scsActuacionasistencia.getIdtipoactuacion().toString());
                            datosGeneralesActuacionAsistenciaItem.setDescripcion(scsActuacionasistencia.getDescripcionbreve());
                            datosGeneralesActuacionAsistenciaItem.setObservaciones(scsActuacionasistencia.getObservaciones());
                            if(costesActuacion != null && !costesActuacion.isEmpty()) {
                                datosGeneralesActuacionAsistenciaItem.setIdCoste(costesActuacion.get(0).getIdcostefijo().toString());
                            }
                            if(!UtilidadesString.esCadenaVacia(scsActuacionasistencia.getDiadespues()) && "S".equals(scsActuacionasistencia.getDiadespues())){
                                datosGeneralesActuacionAsistenciaItem.setDiaDespues(true);
                            }else{
                                datosGeneralesActuacionAsistenciaItem.setDiaDespues(false);
                            }
                            if(scsActuacionasistencia.getIdcomisaria() != null){
                                datosGeneralesActuacionAsistenciaItem.setComisaria(scsActuacionasistencia.getIdcomisaria().toString());
                            }else if(scsActuacionasistencia.getIdjuzgado() != null){
                                datosGeneralesActuacionAsistenciaItem.setJuzgado(scsActuacionasistencia.getIdjuzgado().toString());
                            }
                            if(scsActuacionasistencia.getIdprision() != null){
                                datosGeneralesActuacionAsistenciaItem.setPrision(scsActuacionasistencia.getIdprision().toString());
                            }
                            //Check que aparece deshabilitado si la guardia no tiene baremo de  actuaciones fuera de guardia
                            int baremosFG = scsActuacionasistenciaExtendsMapper.controlCheckDiaDespues(idInstitucion, scsAsistencia.getIdturno().toString(), scsAsistencia.getIdguardia().toString());
                            if(baremosFG == 0) {
                            	datosGeneralesActuacionAsistenciaItem.setControlCheckDiaDespues(false); 
                            }else {
                            	datosGeneralesActuacionAsistenciaItem.setControlCheckDiaDespues(true);
                            }
                            
                            datosGeneralesActuacionAsistenciaItem.setFechaActuacion(new SimpleDateFormat("dd/MM/yyyy").format(scsActuacionasistencia.getFecha()));
                            datosGeneralesActuacionAsistenciaItem.setNig(scsActuacionasistencia.getNig());
                            datosGeneralesActuacionAsistenciaItem.setNumAsunto(scsActuacionasistencia.getNumeroasunto());

                            datosGenerales.add(datosGeneralesActuacionAsistenciaItem);
                        }


                        datosGeneralesActuacionAsistenciaDTO.setDatosGeneralesActuacionAsistenciaItems(datosGenerales);

                }
            }
        }catch(Exception e) {
            LOGGER.error("searchTarjetaDatosGenerales() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar los datos generales de la actuacion: " + e);
            error.description("Error al buscar los datos generales de la actuacion: " + e);
            datosGeneralesActuacionAsistenciaDTO.setError(error);
        }
        return datosGeneralesActuacionAsistenciaDTO;
    }
    
    public boolean searchHitoNueve(HttpServletRequest request, String anioNumero, String idInstitucion) {
    	boolean esHitoNueve = false;
    	try {
    		String[] anioYnumero = anioNumero.split("/");
    		int resultados = scsAsistenciaExtendsMapper.searchHitoNueveAsistencia(anioYnumero[0], anioYnumero[1], idInstitucion);
    		
    		if(resultados > 0)
    				esHitoNueve = true;
    		
    	}catch(Exception e) {
            LOGGER.error("searchHitoNueve() / ERROR: "+ e.getMessage(), e);
            esHitoNueve = false;
        }
        return esHitoNueve;
    }

    /**
     * Metodo que actualiza o inserta una nueva actuacion, esto dependera de si el idActuacion de {@link DatosGeneralesActuacionAsistenciaItem} va relleno o no
     * @param request
     * @param datosGenerales
     * @param anioNumero
     * @return
     */
    @Override
    public UpdateResponseDTO saveDatosGenerales(HttpServletRequest request, DatosGeneralesActuacionAsistenciaItem datosGenerales, String anioNumero) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        int affectedRows = 0;
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "saveDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "saveDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {


                    ScsAsistenciaKey scsAsistenciaKey = new ScsAsistenciaKey();
                    scsAsistenciaKey.setIdinstitucion(idInstitucion);
                    scsAsistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                    scsAsistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                    ScsAsistencia scsAsistencia = scsAsistenciaExtendsMapper.selectByPrimaryKey(scsAsistenciaKey);
                    //Si no trae idActuacion se trata de una nueva actuacion
                    if(UtilidadesString.esCadenaVacia(datosGenerales.getIdActuacion())){
                        NewIdDTO newIdActuacion = scsActuacionasistenciaExtendsMapper.getNewIdActuacion(idInstitucion, anioNumero);
                        ScsActuacionasistencia newActuacion = new ScsActuacionasistencia();
                        if(newIdActuacion != null) {
                            datosGenerales.setIdActuacion(newIdActuacion.getNewId());
                            newActuacion.setIdactuacion(Long.valueOf(newIdActuacion.getNewId()));
                        }else{ //si es null es porque es la primera actuacion
                            datosGenerales.setIdActuacion("1");
                            newActuacion.setIdactuacion((long)1);
                        }
                        newActuacion.setAnio(scsAsistencia.getAnio());
                        newActuacion.setNumero(scsAsistencia.getNumero());
                        newActuacion.setIdinstitucion(scsAsistencia.getIdinstitucion());
                        newActuacion.setDescripcionbreve(datosGenerales.getDescripcion());
                        newActuacion.setObservaciones(datosGenerales.getObservaciones());
                        newActuacion.setNig(datosGenerales.getNig());
                        newActuacion.setNumeroasunto(datosGenerales.getNumAsunto());
                        newActuacion.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(datosGenerales.getFechaActuacion()));
                        newActuacion.setAcuerdoextrajudicial((short)0);
                        if(datosGenerales.isDiaDespues()){
                            newActuacion.setDiadespues("S");
                        }else{
                            newActuacion.setDiadespues("N");
                        }
                        newActuacion.setIdtipoactuacion(Short.valueOf(datosGenerales.getTipoActuacion()));
                        newActuacion.setIdtipoasistencia(scsAsistencia.getIdtipoasistenciacolegio());
                        if(!UtilidadesString.esCadenaVacia(datosGenerales.getJuzgado())){
                            newActuacion.setIdjuzgado(Long.valueOf(datosGenerales.getJuzgado()));
                            newActuacion.setIdinstitucionJuzg(idInstitucion);
                        }else if(!UtilidadesString.esCadenaVacia(datosGenerales.getComisaria())){
                            newActuacion.setIdcomisaria(Long.valueOf(datosGenerales.getComisaria()));
                            newActuacion.setIdinstitucionComis(idInstitucion);
                        }
                        if(!UtilidadesString.esCadenaVacia(datosGenerales.getPrision())){
                            newActuacion.setIdprision(Long.valueOf(datosGenerales.getPrision()));
                            newActuacion.setIdinstitucionPris(idInstitucion);
                        }

                        //La validacion automatica solo se realiza en el caso de que sea perfil letrado
                        String letrado = UserTokenUtils.getLetradoFromJWTToken(token);
                        if(letrado.equals("S") && isTurnoAutovalidable(idInstitucion,scsAsistencia.getIdturno())){
                            newActuacion.setValidada("1");
                            newActuacion.setFechajustificacion(new Date());
                            newActuacion.setFechavalidacion(new Date());
                            newActuacion.setUsuvalidacion(usuarios.get(0).getIdusuario());
                        }else {
                            newActuacion.setValidada("0");
                        }
                        newActuacion.setUsucreacion(usuarios.get(0).getIdusuario());
                        newActuacion.setFechacreacion(new Date());
                        newActuacion.setUsumodificacion(usuarios.get(0).getIdusuario());
                        newActuacion.setFechamodificacion(new Date());
                        
                        //Comprobamos primeor que exista el registro en SCS_TIPOACTUACION 
                        //Si no existe registro lo insertamos para prevenir error de FK
                        ScsTipoactuacionExample scsTipoactuacionExample = new ScsTipoactuacionExample();
						scsTipoactuacionExample.createCriteria()
								.andIdinstitucionEqualTo(newActuacion.getIdinstitucion())
								.andIdtipoasistenciaEqualTo(scsAsistencia.getIdtipoasistencia())
								.andIdtipoactuacionEqualTo(newActuacion.getIdtipoactuacion());
						
                        List<ScsTipoactuacion> tipoActList = scsTipoactuacionMapper.selectByExample(scsTipoactuacionExample);
                        
                        if(tipoActList != null && tipoActList.isEmpty()) {
                        	ScsTipoactuacion scsTipoactuacionRecord = new ScsTipoactuacion();
                        	scsTipoactuacionRecord.setIdinstitucion(newActuacion.getIdinstitucion());
                        	scsTipoactuacionRecord.setIdtipoasistencia(scsAsistencia.getIdtipoasistenciacolegio());
                        	scsTipoactuacionRecord.setIdtipoactuacion(newActuacion.getIdtipoactuacion());
                        	
                        	//Recuperamos la descripcion de Tipo Asistencia
                        	ScsTipoactuacionExample scsTipoactuacionExample2 = new ScsTipoactuacionExample();
                        	scsTipoactuacionExample2.createCriteria()
                        			.andIdinstitucionEqualTo(newActuacion.getIdinstitucion())
                        			.andIdtipoactuacionEqualTo(Short.valueOf(datosGenerales.getTipoActuacion()));
                        			
                        	List<ScsTipoactuacion> listActAsist = scsTipoactuacionMapper.selectByExample(scsTipoactuacionExample2);
                        	
                        	String descripcion = "";
                        	if(listActAsist != null && !listActAsist.isEmpty()) {
                        		descripcion = listActAsist.get(0).getDescripcion();
                        	}
                        	
                        	scsTipoactuacionRecord.setDescripcion(descripcion);
                        	scsTipoactuacionRecord.setFechamodificacion(new Date());
                        	scsTipoactuacionRecord.setUsumodificacion(usuarios.get(0).getIdusuario());
                        	scsTipoactuacionRecord.setFechabaja(null);
                        	
                        	affectedRows += scsTipoactuacionMapper.insertSelective(scsTipoactuacionRecord);
                        }
                        
                        affectedRows += scsActuacionasistenciaExtendsMapper.insertSelective(newActuacion);

                        //Insertamos el coste fijo si lo ha seleccionado
                        if(!UtilidadesString.esCadenaVacia(datosGenerales.getIdCoste())) {
                            ScsActuacionasistcostefijo scsActuacionasistcostefijo = new ScsActuacionasistcostefijo();
                            scsActuacionasistcostefijo.setAnio(scsAsistencia.getAnio());
                            scsActuacionasistcostefijo.setNumero(scsAsistencia.getNumero());
                            scsActuacionasistcostefijo.setIdinstitucion(scsAsistencia.getIdinstitucion());
                            scsActuacionasistcostefijo.setIdactuacion(newActuacion.getIdactuacion());
                            scsActuacionasistcostefijo.setIdtipoactuacion(newActuacion.getIdtipoactuacion());
                            scsActuacionasistcostefijo.setIdtipoasistencia(scsAsistencia.getIdtipoasistenciacolegio());
                            scsActuacionasistcostefijo.setIdcostefijo(Short.valueOf(datosGenerales.getIdCoste()));
                            scsActuacionasistcostefijo.setFechamodificacion(new Date());
                            scsActuacionasistcostefijo.setUsumodificacion(usuarios.get(0).getIdusuario());
                            affectedRows += scsActuacionasistcostefijoMapper.insertSelective(scsActuacionasistcostefijo);
                        }
                    //Si lo trae relleno actualizamos
                    }else {

                        ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                        scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(datosGenerales.getIdActuacion()));
                        scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                        scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                        scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                        ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);


                        if (scsActuacionasistencia != null) {

                            scsActuacionasistencia.setNig(datosGenerales.getNig());
                            scsActuacionasistencia.setNumeroasunto(datosGenerales.getNumAsunto());
                            scsActuacionasistencia.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(datosGenerales.getFechaActuacion()));
                            scsActuacionasistencia.setIdtipoactuacion(Short.valueOf(datosGenerales.getTipoActuacion()));
                            scsActuacionasistencia.setUsumodificacion(usuarios.get(0).getIdusuario());
                            scsActuacionasistencia.setFechamodificacion(new Date());
                            //Si tiene juzgado
                            if(!UtilidadesString.esCadenaVacia(datosGenerales.getJuzgado())){
                                scsActuacionasistencia.setIdjuzgado(Long.valueOf(datosGenerales.getJuzgado()));
                                scsActuacionasistencia.setIdinstitucionJuzg(idInstitucion);
                                scsActuacionasistencia.setIdcomisaria(null);
                                scsActuacionasistencia.setIdinstitucionComis(null);
                            //Si no, si tiene comisaria
                            }else if(!UtilidadesString.esCadenaVacia(datosGenerales.getComisaria())){
                                scsActuacionasistencia.setIdcomisaria(Long.valueOf(datosGenerales.getComisaria()));
                                scsActuacionasistencia.setIdinstitucionComis(idInstitucion);
                                scsActuacionasistencia.setIdjuzgado(null);
                                scsActuacionasistencia.setIdinstitucionJuzg(null);
                            }
                            //Si tiene prision, si no la pasamos a nulo
                            if(!UtilidadesString.esCadenaVacia(datosGenerales.getPrision())){
                                scsActuacionasistencia.setIdprision(Long.valueOf(datosGenerales.getPrision()));
                                scsActuacionasistencia.setIdinstitucionPris(idInstitucion);
                            }else{
                                scsActuacionasistencia.setIdprision(null);
                                scsActuacionasistencia.setIdinstitucionPris(null);
                            }
                            
                            if(datosGenerales.isDiaDespues()) scsActuacionasistencia.setDiadespues("S");
                            else scsActuacionasistencia.setDiadespues("N");
                            
                            // Se valida la actuación de  la asistencia
                            scsActuacionasistencia.setFechavalidacion(new Date());
                            //scsActuacionasistencia.setValidada("1");
                            scsActuacionasistencia.setUsuvalidacion(usuarios.get(0).getIdusuario());
                            
                            scsActuacionasistencia.setDescripcionbreve(datosGenerales.getDescripcion());
                            scsActuacionasistencia.setObservaciones(datosGenerales.getObservaciones());
                            affectedRows += scsActuacionasistenciaExtendsMapper.updateByPrimaryKey(scsActuacionasistencia);
                            //Borramos el coste fijo de la actuacion
                            ScsActuacionasistcostefijoExample scsActuacionasistcostefijoExample = new ScsActuacionasistcostefijoExample();
                            scsActuacionasistcostefijoExample.createCriteria().andAnioEqualTo(Short.valueOf(anioNumero.split("/")[0]))
                                    .andNumeroEqualTo(Long.valueOf(anioNumero.split("/")[1]))
                                    .andIdinstitucionEqualTo(idInstitucion)
                                    .andIdactuacionEqualTo(scsActuacionasistencia.getIdactuacion())
                                    .andIdtipoactuacionEqualTo(scsActuacionasistencia.getIdtipoactuacion())
                                    .andIdtipoasistenciaEqualTo(scsAsistencia.getIdtipoasistencia());
                            affectedRows += scsActuacionasistcostefijoMapper.deleteByExample(scsActuacionasistcostefijoExample);

                            //Insertamos el nuevo coste en caso de que hubiera seleccionado uno
                            if(!UtilidadesString.esCadenaVacia(datosGenerales.getIdCoste())) {
                                ScsActuacionasistcostefijo scsActuacionasistcostefijo = new ScsActuacionasistcostefijo();
                                scsActuacionasistcostefijo.setAnio(scsAsistencia.getAnio());
                                scsActuacionasistcostefijo.setNumero(scsAsistencia.getNumero());
                                scsActuacionasistcostefijo.setIdinstitucion(scsAsistencia.getIdinstitucion());
                                scsActuacionasistcostefijo.setIdactuacion(scsActuacionasistencia.getIdactuacion());
                                scsActuacionasistcostefijo.setIdtipoactuacion(scsActuacionasistencia.getIdtipoactuacion());
                                scsActuacionasistcostefijo.setIdtipoasistencia(scsAsistencia.getIdtipoasistenciacolegio());
                                scsActuacionasistcostefijo.setIdcostefijo(Short.valueOf(datosGenerales.getIdCoste()));
                                scsActuacionasistcostefijo.setFechamodificacion(new Date());
                                scsActuacionasistcostefijo.setUsumodificacion(usuarios.get(0).getIdusuario());
                                affectedRows += scsActuacionasistcostefijoMapper.insertSelective(scsActuacionasistcostefijo);
                            }
                        }
                    }

                    if(affectedRows > 0){
                        updateResponseDTO.setStatus(SigaConstants.OK);
                        updateResponseDTO.setId(datosGenerales.getIdActuacion());
                    }else{
                        error.setCode(500);
                        error.setMessage("No se actualizo ningun registro");
                        error.setDescription("No se actualizo ningun registro");
                        updateResponseDTO.setStatus(SigaConstants.KO);
                        updateResponseDTO.setError(error);
                    }
                }
            }
        }catch(Exception e) {
            LOGGER.error("saveDatosGenerales() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al guardar los datos generales de la actuacion: " + e);
            error.description("Error al guardar los datos generales de la actuacion: " + e);
            updateResponseDTO.setError(error);
        }
        return updateResponseDTO;
    }

    @Override
    public TarjetaJustificacionActuacionAsistenciaDTO searchTarjetaJustificacion(HttpServletRequest request, String anioNumero, String idActuacion) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        TarjetaJustificacionActuacionAsistenciaDTO tarjetaJustificacionActuacionAsistenciaDTO = new TarjetaJustificacionActuacionAsistenciaDTO();
        List<TarjetaJustificacionActuacionAsistenciaItem> datosJustificacion = new ArrayList<>();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "searchTarjetaJustificacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "searchTarjetaJustificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {

                    ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                    scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(idActuacion));
                    scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                    scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                    scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                    ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);

                    if (scsActuacionasistencia != null) {

                        TarjetaJustificacionActuacionAsistenciaItem tarjeta = new TarjetaJustificacionActuacionAsistenciaItem();
                        if(scsActuacionasistencia.getFechajustificacion() != null) {
                            tarjeta.setFechaJustificacion(new SimpleDateFormat("dd/MM/yyyy").format(scsActuacionasistencia.getFechajustificacion()));
                        }
                        tarjeta.setObservaciones(scsActuacionasistencia.getObservacionesjustificacion());
                        if("1".equals(scsActuacionasistencia.getValidada())){
                            tarjeta.setEstado("VALIDADA");
                        }
                        if(null != scsActuacionasistencia.getAnulacion() && "1".equals(scsActuacionasistencia.getAnulacion().toString())){
                            tarjeta.setEstado("ANULADA");
                        }
                        tarjeta.setAnulada(null != scsActuacionasistencia.getAnulacion() ? scsActuacionasistencia.getAnulacion().toString() : SigaConstants.DB_FALSE);
                        tarjeta.setValidada(scsActuacionasistencia.getValidada());
                        datosJustificacion.add(tarjeta);
                    }


                    tarjetaJustificacionActuacionAsistenciaDTO.setResponseItems(datosJustificacion);

                }
            }
        }catch(Exception e) {
            LOGGER.error("searchTarjetaJustificacion() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar los datos de la tarjeta justificacion de la actuacion: " + e);
            error.description("Error al buscar los datos de la tarjeta justificacion de la actuacion: " + e);
            tarjetaJustificacionActuacionAsistenciaDTO.setError(error);
        }
        return tarjetaJustificacionActuacionAsistenciaDTO;
    }

    @Override
    public UpdateResponseDTO saveTarjetaJustificacion(HttpServletRequest request, String anioNumero, String idActuacion, TarjetaJustificacionActuacionAsistenciaItem tarjeta) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        int affectedRows = 0;
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "saveTarjetaJustificacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "saveTarjetaJustificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {

                    ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                    scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(idActuacion));
                    scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                    scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                    scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                    ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);
                    if (scsActuacionasistencia != null) {
                        if(!UtilidadesString.esCadenaVacia(tarjeta.getFechaJustificacion())) {
                            scsActuacionasistencia.setFechajustificacion(new SimpleDateFormat("dd/MM/yyyy").parse(tarjeta.getFechaJustificacion()));
                            scsActuacionasistencia.setUsujustificacion(usuarios.get(0).getIdusuario());
                            scsActuacionasistencia.setFechausujustificacion(new Date());
                        }
                        else {
                        	scsActuacionasistencia.setFechajustificacion(null);
                        	scsActuacionasistencia.setUsujustificacion(usuarios.get(0).getIdusuario());
                        	scsActuacionasistencia.setFechausujustificacion(null);
                        }
                        scsActuacionasistencia.setObservacionesjustificacion(tarjeta.getObservaciones());
                        affectedRows += scsActuacionasistenciaExtendsMapper.updateByPrimaryKey(scsActuacionasistencia);
                    }
                }

                if(affectedRows > 0){
                    updateResponseDTO.setStatus(SigaConstants.OK);
                    updateResponseDTO.setId(idActuacion);
                }else{
                    error.setCode(500);
                    error.setMessage("No se actualizo ningun registro");
                    error.setDescription("No se actualizo ningun registro");
                    updateResponseDTO.setStatus(SigaConstants.KO);
                    updateResponseDTO.setError(error);
                }
            }

        }catch(Exception e) {
            LOGGER.error("saveTarjetaJustificacion() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al guardar los datos generales de la actuacion: " + e);
            error.description("Error al guardar los datos generales de la actuacion: " + e);
            updateResponseDTO.setError(error);
        }
        return updateResponseDTO;
    }

    @Override
    public UpdateResponseDTO updateEstadoActuacion(HttpServletRequest request, String anioNumero, String idActuacion, TarjetaJustificacionActuacionAsistenciaItem tarjeta) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        int affectedRows = 0;
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "updateEstadoActuacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "updateEstadoActuacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {

                    ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                    scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(idActuacion));
                    scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                    scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                    scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                    ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);
                    if (scsActuacionasistencia != null) {
                        if(!UtilidadesString.esCadenaVacia(tarjeta.getValidada())){
                            scsActuacionasistencia.setValidada(tarjeta.getValidada());
                            if("1".equals(tarjeta.getValidada())){
                                scsActuacionasistencia.setFechavalidacion(new Date());
                                // Rellenar Fecha Justificación
                                Date fechaJusticiacion = formato.parse(tarjeta.getFechaJustificacion());
                                scsActuacionasistencia.setFechajustificacion(fechaJusticiacion);
                                scsActuacionasistencia.setUsuvalidacion(usuarios.get(0).getIdusuario());
                            }else {
                            	scsActuacionasistencia.setFechajustificacion(null);
                            }
                        }else if(!UtilidadesString.esCadenaVacia(tarjeta.getAnulada())){

                            if("1".equals(scsActuacionasistencia.getFacturado()) && "1".equals(tarjeta.getAnulada())){
                                updateResponseDTO.setStatus(SigaConstants.KO);
                                error.setCode(500);
                                error.setDescription("No se puede anular una actuacion facturada");
                                error.setMessage("No se puede anular una actuacion facturada");
                                updateResponseDTO.setError(error);
                            }else {
                                scsActuacionasistencia.setAnulacion(Short.valueOf(tarjeta.getAnulada()));
                            }
                        }
                        //Si no hay error continuamos
                        if(updateResponseDTO.getError() == null) {
                            affectedRows += scsActuacionasistenciaExtendsMapper.updateByPrimaryKey(scsActuacionasistencia);
                            if (affectedRows > 0) {
                                updateResponseDTO.setStatus(SigaConstants.OK);
                                updateResponseDTO.setId(idActuacion);
                            } else {
                                error.setCode(500);
                                error.setMessage("No se actualizo ningun registro");
                                error.setDescription("No se actualizo ningun registro");
                                updateResponseDTO.setStatus(SigaConstants.KO);
                                updateResponseDTO.setError(error);
                            }
                        }
                    }
                }


            }

        }catch(NullPointerException npe) {
            LOGGER.error("updateEstadoActuacion() / ERROR: "+ npe.getMessage(), npe);
            error.setCode(500);
            error.setMessage("Para validar debe rellenar la fecha de justificación");
            error.description("Para validar debe rellenar la fecha de justificación");
            updateResponseDTO.setError(error);
        }catch(Exception e) {
            LOGGER.error("updateEstadoActuacion() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al actualizar el estado de la actuacion: " + e);
            error.description("Error al actualizar el estado de la actuacionn: " + e);
            updateResponseDTO.setError(error);
        }
        return updateResponseDTO;
    }

    @Override
    public HistoricoActuacionAsistenciaDTO searchHistorico(HttpServletRequest request, String anioNumero, String idActuacion) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        HistoricoActuacionAsistenciaDTO historicoActuacionAsistenciaDTO = new HistoricoActuacionAsistenciaDTO();
        List<HistoricoActuacionAsistenciaItem> historicoItems = new ArrayList<>();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "searchHistorico() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "searchHistorico() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if(usuarios != null
                        && !usuarios.isEmpty()
                        && !UtilidadesString.esCadenaVacia(anioNumero)) {

                    ScsActuacionasistenciaKey scsActuacionasistenciaKey = new ScsActuacionasistenciaKey();
                    scsActuacionasistenciaKey.setIdactuacion(Long.valueOf(idActuacion));
                    scsActuacionasistenciaKey.setIdinstitucion(idInstitucion);
                    scsActuacionasistenciaKey.setAnio(Short.valueOf(anioNumero.split("/")[0]));
                    scsActuacionasistenciaKey.setNumero(Long.valueOf(anioNumero.split("/")[1]));
                    ScsActuacionasistencia scsActuacionasistencia = scsActuacionasistenciaExtendsMapper.selectByPrimaryKey(scsActuacionasistenciaKey);

                    if (scsActuacionasistencia != null) {

                        HistoricoActuacionAsistenciaItem historico = new HistoricoActuacionAsistenciaItem();
                        AdmUsuariosKey admUsuariosKey = new AdmUsuariosKey();
                        admUsuariosKey.setIdinstitucion(idInstitucion);
                        admUsuariosKey.setIdusuario(scsActuacionasistencia.getUsucreacion());
                        AdmUsuarios usuario = admUsuariosExtendsMapper.selectByPrimaryKey(admUsuariosKey);
                        historico.setFecha(scsActuacionasistencia.getFechacreacion());
                        historico.setAccion("Crear");
                        if(usuario != null) {
                            historico.setUsuario(usuario.getDescripcion());
                        }else{
                            historico.setUsuario("Usuario desconocido");
                        }
                        historicoItems.add(historico);

                        if(scsActuacionasistencia.getFechamodificacion() != null){
                            admUsuariosKey.setIdusuario(scsActuacionasistencia.getUsumodificacion());
                            usuario = admUsuariosExtendsMapper.selectByPrimaryKey(admUsuariosKey);
                            historico = new HistoricoActuacionAsistenciaItem();
                            historico.setFecha(scsActuacionasistencia.getFechamodificacion());
                            historico.setAccion("Modificar");
                            if(usuario != null) {
                                historico.setUsuario(usuario.getDescripcion());
                            }else{
                                historico.setUsuario("Usuario desconocido");
                            }
                            historicoItems.add(historico);
                        }

                        if(scsActuacionasistencia.getFechausujustificacion() != null){
                            admUsuariosKey.setIdusuario(scsActuacionasistencia.getUsujustificacion());
                            usuario = admUsuariosExtendsMapper.selectByPrimaryKey(admUsuariosKey);
                            historico = new HistoricoActuacionAsistenciaItem();
                            historico.setFecha(scsActuacionasistencia.getFechausujustificacion());
                            historico.setAccion("Justificar");
                            if(usuario != null) {
                                historico.setUsuario(usuario.getDescripcion());
                            }else{
                                historico.setUsuario("Usuario desconocido");
                            }
                            historicoItems.add(historico);
                        }

                        if(scsActuacionasistencia.getFechavalidacion() != null){
                            admUsuariosKey.setIdusuario(scsActuacionasistencia.getUsuvalidacion());
                            usuario = admUsuariosExtendsMapper.selectByPrimaryKey(admUsuariosKey);
                            historico = new HistoricoActuacionAsistenciaItem();
                            historico.setFecha(scsActuacionasistencia.getFechavalidacion());
                            historico.setAccion("Validar");
                            if(usuario != null) {
                                historico.setUsuario(usuario.getDescripcion());
                            }else{
                                historico.setUsuario("Usuario desconocido");
                            }
                            historicoItems.add(historico);
                        }

                        historicoItems = historicoItems.stream().sorted().collect(Collectors.toList());

                    }

                    historicoActuacionAsistenciaDTO.setResponseItems(historicoItems);

                }
            }
        }catch(Exception e) {
            LOGGER.error("searchTarjetaJustificacion() / ERROR: "+ e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar los datos de la tarjeta justificacion de la actuacion: " + e);
            error.description("Error al buscar los datos de la tarjeta justificacion de la actuacion: " + e);
            historicoActuacionAsistenciaDTO.setError(error);
        }
        return historicoActuacionAsistenciaDTO;
    }

    /**
     * Metodo que comprueba si las actuaciones de una asistencia son necesarias validarlas o no  dependiendo de la configuracion del turno
     * @return
     */
    private boolean isTurnoAutovalidable(Short idInstitucion, Integer idTurno){
        boolean isAutovalidable = false;

        ScsTurnoKey scsTurnoKey = new ScsTurnoKey();
        scsTurnoKey.setIdinstitucion(idInstitucion);
        scsTurnoKey.setIdturno(idTurno);

        ScsTurno scsTurno = scsTurnosExtendsMapper.selectByPrimaryKey(scsTurnoKey);
        //Si VALIDARJUSTIFICACIONES es vacio o N, significa que el letrado no tiene que validar las actuaciones, las autovalidamos en back
        if(scsTurno != null
                && (UtilidadesString.esCadenaVacia(scsTurno.getValidarjustificaciones()) || "N".equals(scsTurno.getValidarjustificaciones()))) {
            isAutovalidable = true;
        }

        return isAutovalidable;
    }
}
