package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FichaMonederoItem;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaMonederosItem;
import org.itcgae.siga.DTO.fac.ListaMovimientosMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaMovimientosMonederoItem;
import org.itcgae.siga.DTO.fac.ListaServiciosMonederoDTO;
import org.itcgae.siga.DTO.fac.ListaServiciosMonederoItem;
import org.itcgae.siga.DTO.fac.MonederoDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.PysAnticipoletrado;
import org.itcgae.siga.db.entities.PysAnticipoletradoExample;
import org.itcgae.siga.db.entities.PysAnticipoletradoKey;
import org.itcgae.siga.db.entities.PysLineaanticipo;
import org.itcgae.siga.db.entities.PysLineaanticipoExample;
import org.itcgae.siga.db.entities.PysLineaanticipoKey;
import org.itcgae.siga.db.entities.PysServicioanticipo;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgActa;
import org.itcgae.siga.db.entities.ScsEjgActaExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgResolucionKey;
import org.itcgae.siga.db.entities.ScsEjgResolucionWithBLOBs;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.PysAnticipoletradoMapper;
import org.itcgae.siga.db.mappers.PysLineaanticipoExtendsMapper;
import org.itcgae.siga.db.mappers.PysServicioanticipoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysAnticipoletradoExtendsMapper;
import org.itcgae.siga.fac.services.ILineaanticipoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class LineaanticipoServiceImpl implements ILineaanticipoService {

    private Logger LOGGER = Logger.getLogger(LineaanticipoServiceImpl.class);

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private PysLineaanticipoExtendsMapper pysLineaanticipoExtendsMapper;

    @Autowired
    private CenPersonaMapper cenPersonaMapper;
    
    @Autowired
    private PysAnticipoletradoMapper pysAnticipoletradoMapper;
    
    @Autowired
    private PysAnticipoletradoExtendsMapper pysAnticipoletradoExtendsMapper;
    
    @Autowired
    private PysServicioanticipoMapper pysServicioanticipoMapper;


    @Override
    public ListaMonederoDTO listarMonederos(HttpServletRequest request, FiltroMonederoItem filtroMonederoItem) {

        Error error = new Error();
        // Conseguimos información del usuario logeado
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short institutionId = UserTokenUtils.getInstitucionFromJWTToken(token);
        String counsel = UserTokenUtils.getLetradoFromJWTToken(token); // letrado

        if (institutionId != null) {
            AdmUsuariosExample userQuery = new AdmUsuariosExample();
            userQuery.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(institutionId);
            LOGGER.info("listarMonederos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> users = admUsuariosExtendsMapper.selectByExample(userQuery);
            LOGGER.info("listarMonederos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");


            if (users != null && !users.isEmpty()) {
                //Si es colegiado se debe coger el idpersona del usuario conectado
                if (!counsel.equals("N")) {
                    CenPersonaExample peopleQuery = new CenPersonaExample();
                    peopleQuery.createCriteria().andNifcifEqualTo(dni);
                    List<CenPersona> people = cenPersonaMapper.selectByExample(peopleQuery);
                    filtroMonederoItem.setIdPersonaColegiado(people.get(0).getIdpersona().toString());
                    List<ListaMonederosItem> walletDTOs = pysLineaanticipoExtendsMapper.selectByPersonIdAndCreationDate(institutionId, filtroMonederoItem);
                    ListaMonederoDTO walletListDTO = new ListaMonederoDTO();
                    walletListDTO.setMonederoItems(walletDTOs);
                    error.setCode(200);
                    walletListDTO.setError(error);

                    return walletListDTO;
                }
                else {
                	List<ListaMonederosItem> walletDTOs = pysLineaanticipoExtendsMapper.selectByPersonIdAndCreationDate(institutionId, filtroMonederoItem);
                    ListaMonederoDTO walletListDTO = new ListaMonederoDTO();
                    walletListDTO.setMonederoItems(walletDTOs);
                    error.setCode(200);
                    walletListDTO.setError(error);

                    return walletListDTO;
                }
            }
        }
        return null;
    }
    
    @Override
    @Transactional
    public UpdateResponseDTO updateMovimientosMonedero(HttpServletRequest request, FichaMonederoItem ficha) throws Exception {
        UpdateResponseDTO responsedto = new UpdateResponseDTO();
        int response = 0;

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        if (idInstitucion != null) {
            LOGGER.debug(
                    "LineaanticipoServiceImpl.updateMovimientosMonedero() -> Entrada para obtener información del usuario logeado");

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.debug(
                    "LineaanticipoServiceImpl.updateMovimientosMonedero() -> Salida de obtener información del usuario logeado");

            if (usuarios != null && usuarios.size() > 0) {
                LOGGER.debug(
                        "LineaanticipoServiceImpl.updateMovimientosMonedero() -> Entrada para cambiar los movimientos del monedero");
                // Para que la etiqueta @Transactional funcione adecuadamente debe recibir una
                // excepcion
//				try {
                
                

                
                // 1. Se comprueba si es un monedero nuevo o no
                if(ficha.getIdLinea() != null) {
                    
                	//1.a Se eliminan las lineas de anticipo relacionadas con el monedero (PYS_LINEAANTICIPO)
                    
                    PysLineaanticipoExample lineasAnticipoExample = new PysLineaanticipoExample();
                    
                    lineasAnticipoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdlineaEqualTo(ficha.getIdLinea()).
                    andIdpersonaEqualTo(ficha.getIdPersona());
                    
                    List<PysLineaanticipo>lineasAnticipo = pysLineaanticipoExtendsMapper.selectByExample(lineasAnticipoExample);
                    
                    if(lineasAnticipo != null && !lineasAnticipo.isEmpty()) {
                    	response = pysLineaanticipoExtendsMapper.deleteByExample(lineasAnticipoExample);
                    	if(response == 0) {
                        	throw new SigaExceptions("Error al borrar las lineas de anticipo del monedero en la BBDD.");
                        }
                    }
                    
                    //1.b Se eliminan los anticipos relacionados con el monedero (PYS_ANTICIPOLETRADO)
                    //Se complica un poco la cosa ya que los anticipos no tienen clave foranea (secundaria) de las lineas de anticipo.
                    //Esto provoca que una persona pueda tener anticipos sin monedero o "asociados" a un monedero distinto.
                    //Aclarar que los anticipos no tienen referencia de las lineas de anticipo per las lineas si tienen referencia de los anticipos.
                    
                    
                    //Obtenemos los ids de los anticipos relacionados con el monedero
                    List<Short> idAnticiposMonedero = new ArrayList<Short>();
                    
                    for(PysLineaanticipo lineaAnticipo : lineasAnticipo) {
                    	idAnticiposMonedero.add(lineaAnticipo.getIdanticipo());
                    }
                    
                    PysAnticipoletradoExample anticiposMonederoExample = new PysAnticipoletradoExample();
                    
                    anticiposMonederoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(ficha.getIdPersona()).andIdanticipoIn(idAnticiposMonedero);
                    
                    List<PysAnticipoletrado> anticiposMonedero = pysAnticipoletradoMapper.selectByExample(anticiposMonederoExample);
                    
                    if(anticiposMonedero != null && !anticiposMonedero.isEmpty()) {
                    	response = pysAnticipoletradoMapper.deleteByExample(anticiposMonederoExample);
                    	if(response == 0) {
                        	throw new SigaExceptions("Error al borrar las lineas de anticipo del monedero en la BBDD.");
                        }
                    }
                    
                    //1.c Se eliminan los servicios relacionados con el monedero (PYS_SERVICIOANTICIPO)
                    
                    PysAnticipoletradoExample serviciosMonederoExample = new PysAnticipoletradoExample();
                    
                    serviciosMonederoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(ficha.getIdPersona()).andIdanticipoIn(idAnticiposMonedero);
                    
                    List<PysAnticipoletrado> servicios = pysAnticipoletradoMapper.selectByExample(serviciosMonederoExample);
                    
                    if(servicios != null && !servicios.isEmpty()) {
                    	response = pysAnticipoletradoMapper.deleteByExample(serviciosMonederoExample);
                    	if(response == 0) {
                        	throw new SigaExceptions("Error al borrar los servicios del monedero en la BBDD.");
                        }
                    }
                }
                
                // 2. Se actualizan las tablas relacionadas con los movimientos del monedero (PYS_LINEAANTICIPO, PYS_ANTICIPOLETRADO y PYS_SERVICIOANTICIPO)
                
                //Comprobamos el id maximo de los anticipos asociados con esta persona
                int idAnticipo = Integer.valueOf(pysAnticipoletradoExtendsMapper.selectMaxIdAnticipo(idInstitucion, ficha.getIdPersona()).getNewId()) + 1;
                short idLinea;
                
                if(ficha.getIdLinea() == null) {
                	idLinea = (short) (Short.valueOf(pysLineaanticipoExtendsMapper.selectMaxIdLinea(idInstitucion, ficha.getIdPersona()).getNewId())+1);
                }
                else {
                	idLinea = ficha.getIdLinea();
                }
                
                //Se recorren los movimientos presentes
                for(ListaMovimientosMonederoItem movimiento : ficha.getMovimientos()) {
                	
                	//2.a Se introduce fila en la tabla PYS_ANTICIPOLETRADO
                    
                    PysAnticipoletrado anticipo = new PysAnticipoletrado(); 
                    
                    anticipo.setContabilizado(movimiento.getContabilizado());
                    anticipo.setCtacontable(movimiento.getCuentaContable());
                    anticipo.setDescripcion(movimiento.getConcepto());
                    anticipo.setFecha(movimiento.getFecha());
                    anticipo.setIdanticipo((short) idAnticipo);
                    anticipo.setIdinstitucion(idInstitucion);
                    anticipo.setIdpersona(ficha.getIdPersona());
                    anticipo.setImporteinicial(movimiento.getImpOp());
                    
                    anticipo.setFechamodificacion(new Date());
                    anticipo.setUsumodificacion(usuarios.get(0).getIdusuario());
                    
                    response = pysAnticipoletradoMapper.insert(anticipo);
                    if(response == 0) {
                    	throw new SigaExceptions("Error al insertar un anticipo en la BBDD.");
                    }
                	
                	//2.b Se introduce fila en la tabla PYS_LINEAANTICIPO
                	
                	PysLineaanticipo monedero = new PysLineaanticipo();
                	
                	monedero.setIdanticipo((short) idAnticipo);
                    monedero.setIdinstitucion(idInstitucion);
                    monedero.setIdlinea(idLinea);
                    monedero.setIdpersona(ficha.getIdPersona());
                    monedero.setFechaefectiva(movimiento.getFecha());
                    monedero.setIdpersona(ficha.getIdPersona());
                    monedero.setImporteanticipado(movimiento.getImpOp());
                    monedero.setLiquidacion(movimiento.getLiquidacion());
                    monedero.setIdfactura(movimiento.getIdFactura());
                    monedero.setNumerolinea(movimiento.getnLineaFactura()); //RELACION CON LINEA DE FACTURA
                    
                    monedero.setFechamodificacion(new Date());
                    monedero.setUsumodificacion(usuarios.get(0).getIdusuario());
                    
                    response = pysLineaanticipoExtendsMapper.insert(monedero);
                    if(response == 0) {
                    	throw new SigaExceptions("Error al insertar una linea de anticipo en la BBDD.");
                    }
                    
                    
                    
                    //2.c Se introduce fila en la tabla PYS_SERVICIOANTICIPO si es necesario
                    
                    if(movimiento.getIdServicio() != null) {
	                    PysServicioanticipo servicioAnticipo = new PysServicioanticipo(); 
	                    
	                    servicioAnticipo.setIdanticipo((short) idAnticipo);
	                    servicioAnticipo.setIdpersona(ficha.getIdPersona());
	                    servicioAnticipo.setIdservicio(movimiento.getIdServicio());
	                    servicioAnticipo.setIdserviciosinstitucion(movimiento.getIdServiciosInstitucion());
	                    servicioAnticipo.setIdtiposervicios(movimiento.getIdTipoServicios());
	                    servicioAnticipo.setIdinstitucion(idInstitucion);
	                    
	                    servicioAnticipo.setFechamodificacion(new Date());
	                    servicioAnticipo.setUsumodificacion(usuarios.get(0).getIdusuario());
	                    
	                    response = pysServicioanticipoMapper.insert(servicioAnticipo);
	                    if(response == 0) {
	                    	throw new SigaExceptions("Error al insertar un servicio relacionado con un anticipo en la BBDD.");
	                    }
                    }
                    
                    idAnticipo++;
                }
            }
        }

        LOGGER.info("LineaanticipoServiceImpl.updateMovimientosMonedero() -> Salida del servicio.");
        
        responsedto.setStatus(SigaConstants.OK);//HttpStatus.OK

        return responsedto;
    }

	@Override
	public ListaMovimientosMonederoDTO getListaMovimientosMonedero(HttpServletRequest request, String idLinea, String idPersona) {
		ListaMovimientosMonederoDTO responsedto = new ListaMovimientosMonederoDTO();
	        
	        Error error = new Error();

	        String token = request.getHeader("Authorization");
	        String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

	        if (idInstitucion != null) {
	            LOGGER.debug(
	                    "LineaanticipoServiceImpl.getListaMovimientosMonedero() -> Entrada para obtener información del usuario logeado");

	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

	            LOGGER.debug(
	                    "LineaanticipoServiceImpl.getListaMovimientosMonedero() -> Salida de obtener información del usuario logeado");

	            if (usuarios != null && usuarios.size() > 0) {
	            	try {
	            		List<ListaMovimientosMonederoItem> movimientosMonedero = pysLineaanticipoExtendsMapper.getListaMovimientosMonedero(idInstitucion, idLinea, idPersona);
	            		responsedto.setListaMovimientosMonederoItem(movimientosMonedero);
	            	}catch(Exception e) {
	            		 LOGGER.error(
	     	                    "LineaanticipoServiceImpl.getListaMovimientosMonedero() -> error al obtener la lista de movimientos de un monedero");
	            		 error.setCode(500);
	            		 responsedto.setError(error);
	            	}
	            }
	            
	        }
		return responsedto;
	}
	
	@Override
	public ListaServiciosMonederoDTO getListaServiciosMonedero(HttpServletRequest request, String idLinea, String idPersona) {
		ListaServiciosMonederoDTO responsedto = new ListaServiciosMonederoDTO();
	        
	        Error error = new Error();

	        String token = request.getHeader("Authorization");
	        String dni = UserTokenUtils.getDniFromJWTToken(token);
	        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

	        if (idInstitucion != null) {
	            LOGGER.debug(
	                    "LineaanticipoServiceImpl.getListaMovimientosMonedero() -> Entrada para obtener información del usuario logeado");

	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

	            LOGGER.debug(
	                    "LineaanticipoServiceImpl.getListaMovimientosMonedero() -> Salida de obtener información del usuario logeado");

	            if (usuarios != null && usuarios.size() > 0) {
	            	try {
	            		List<ListaServiciosMonederoItem> serviciosMonedero = pysLineaanticipoExtendsMapper.getListaServiciosMonedero(idInstitucion, idLinea, idPersona);
	            		responsedto.setListaServiciosMonederoItems(serviciosMonedero);
	            	}catch(Exception e) {
	            		 LOGGER.error(
	     	                    "LineaanticipoServiceImpl.getListaServiciosMonedero() -> error al obterner los servicios asociados al monedero");
	            		 error.setCode(500);
	            		 responsedto.setError(error);
	            	}
	            }
	            
	        }
		return responsedto;
	}
}
