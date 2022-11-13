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
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
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
import org.itcgae.siga.db.entities.PysServicioanticipoExample;
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

import java.math.BigDecimal;
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
//                    walletListDTO.setMonederoItems(walletDTOs);
                    error.setCode(200);
//                    walletListDTO.setError(error);

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
	public UpdateResponseDTO updateMovimientosMonedero(HttpServletRequest request, FichaMonederoItem ficha)
			throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		Error error = new Error();
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
				List<PysLineaanticipo> movimientosViejosMonedero = null;

				// 1. Se comprueba si es un monedero nuevo o no
				if (ficha.getIdAnticipo() != null) {
					
					//1.a Se eliminan todos los movimientos del monedero
					
					PysLineaanticipoExample movimientosExample = new PysLineaanticipoExample();
					
					movimientosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(ficha.getIdPersona())
					.andIdanticipoEqualTo(ficha.getIdAnticipo());
					
					movimientosExample.setOrderByClause("idlinea desc");
					
					movimientosViejosMonedero = pysLineaanticipoExtendsMapper.selectByExample(movimientosExample);
					
					response = pysLineaanticipoExtendsMapper.deleteByExample(movimientosExample);
					
					if (response == 0) {
						throw new SigaExceptions("Error al borrar los movimientos del monedero en la BBDD.");
					}

					// 1.b Se elimina el anticipo del monedero (PYS_ANTICIPOLETRADO)

//					PysAnticipoletradoKey monederoKey = new PysAnticipoletradoKey();
//
//					monederoKey.setIdinstitucion(idInstitucion);
//					monederoKey.setIdpersona(ficha.getIdPersona());
//					monederoKey.setIdanticipo(ficha.getIdAnticipo());
//
//					response = pysAnticipoletradoMapper.deleteByPrimaryKey(monederoKey);
					
					if (response == 0) {
						throw new SigaExceptions("Error al borrar el anticipo del monedero en la BBDD.");
					}
					
					
				}

				// 2. Se inserta el anticipo asociado al monedero y sus movimientos

				List<ListaMovimientosMonederoItem> movimientosFicha = ficha.getMovimientos();
				
				// 2.a Se introduce o actualiza una fila en la tabla PYS_ANTICIPOLETRADO

				PysAnticipoletrado anticipo = new PysAnticipoletrado();

//				anticipo.setContabilizado(anticipoInicial.getContabilizado());
				anticipo.setCtacontable(movimientosFicha.get(movimientosFicha.size()-1).getCuentaContable());
				anticipo.setDescripcion(movimientosFicha.get(movimientosFicha.size()-1).getConcepto());
				anticipo.setFecha(movimientosFicha.get(movimientosFicha.size()-1).getFecha());
				anticipo.setIdinstitucion(idInstitucion);
				anticipo.setIdpersona(ficha.getIdPersona());
				anticipo.setImporteinicial(new BigDecimal(0));

				anticipo.setFechamodificacion(new Date());
				anticipo.setUsumodificacion(usuarios.get(0).getIdusuario());
				
				int idAnticipo;
				if (ficha.getIdAnticipo() == null) {
					// Comprobamos el id maximo de los anticipos asociados con esta persona
					idAnticipo = Integer.valueOf(pysAnticipoletradoExtendsMapper
							.selectMaxIdAnticipo(idInstitucion, ficha.getIdPersona()).getNewId()) + 1;
					anticipo.setIdanticipo((short) idAnticipo);
					
					LOGGER.debug(
							"LineaanticipoServiceImpl.updateMovimientosMonedero() / pysAnticipoletradoMapper.insert() -> Se entra en el mapper para introducir el anticipo asociado al monedero");

					response = pysAnticipoletradoMapper.insert(anticipo);
					if (response == 0) {
						throw new SigaExceptions("Error al insertar un anticipo en la BBDD.");
					}

					LOGGER.debug(
							"LineaanticipoServiceImpl.updateMovimientosMonedero() / pysAnticipoletradoMapper.insert() -> Se sale del mapper para introducir el anticipo asociado al monedero");
					
				} else {
					idAnticipo = ficha.getIdAnticipo();
					anticipo.setIdanticipo((short) idAnticipo);
					
					LOGGER.debug(
							"LineaanticipoServiceImpl.updateMovimientosMonedero() / pysAnticipoletradoMapper.updateByPrimaryKey() -> Se entra en el mapper para actualizar el anticipo asociado al monedero");

					response = pysAnticipoletradoMapper.updateByPrimaryKey(anticipo);
					if (response == 0) {
						throw new SigaExceptions("Error al insertar un anticipo en la BBDD.");
					}

					LOGGER.debug(
							"LineaanticipoServiceImpl.updateMovimientosMonedero() / pysAnticipoletradoMapper.updateByPrimaryKey() -> Se sale del mapper para actualizar el anticipo asociado al monedero");
					
				}

				error.setMessage(Integer.toString(idAnticipo));

				// 2.b Se recorren los movimientos enviados desde la ficha.
				// Se comprueba si tienen valor de idLinea.
				// Eso indicara que no son nuevos.
				
				int diff = 0;
				
				if(movimientosViejosMonedero != null) {
					diff = movimientosViejosMonedero.size() - movimientosFicha.size();
				}
				
				BigDecimal impIniTot = new BigDecimal(0);
				
				for (int i = movimientosFicha.size() - 1; i >= 0; i--) {
					
					//Se comprueba si el importe es positivo (si es un ingreso)
					if(movimientosFicha.get(i).getImpOp().compareTo(new BigDecimal(0)) == 1) {
						impIniTot = impIniTot.add(movimientosFicha.get(i).getImpOp());
					}
					
					if(movimientosFicha.get(i).getIdLinea() != null) {
						pysLineaanticipoExtendsMapper.insert(movimientosViejosMonedero.get(i + diff));
					}
					else {
						PysLineaanticipo movimiento = new PysLineaanticipo();

						if(i==0) {
							movimiento.setIdlinea((short) (movimientosFicha.size() - i));	
						}else {
						movimiento.setIdlinea((short) (movimientosFicha.size() - i - 1));
						}
						movimiento.setFechaefectiva(new Date());
						movimiento.setIdanticipo((short) idAnticipo);
						movimiento.setIdinstitucion(idInstitucion);
						movimiento.setIdpersona(ficha.getIdPersona());
						movimiento.setImporteanticipado(movimientosFicha.get(i).getImpOp());
						movimiento.setLiquidacion("0");
						
						movimiento.setUsumodificacion(usuarios.get(0).getIdusuario());
						movimiento.setFechamodificacion(new Date());
						
						pysLineaanticipoExtendsMapper.insert(movimiento);
					}
		        }
				
				anticipo.setImporteinicial(impIniTot);
				response = pysAnticipoletradoMapper.updateByPrimaryKey(anticipo);
				
				if (response == 0) {
					throw new SigaExceptions("Error al actualizar el importe incial de un anticipo en la BBDD.");
				}
				

				LOGGER.debug(
						"LineaanticipoServiceImpl.updateMovimientosMonedero() -> Salida de cambiar los movimientos del monedero");
			}
		}

		LOGGER.info("LineaanticipoServiceImpl.updateMovimientosMonedero() -> Salida del servicio.");

		responsedto.setStatus(SigaConstants.OK);// HttpStatus.OK
		
		
		responsedto.setError(error);

		return responsedto;
	}

	@Override
	public ListaMovimientosMonederoDTO getListaMovimientosMonedero(HttpServletRequest request, String idAnticipo, String idPersona) {
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
	            		List<ListaMovimientosMonederoItem> movimientosMonedero = pysLineaanticipoExtendsMapper.getListaMovimientosMonedero(idInstitucion, idAnticipo, idPersona);
	            		
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
	                    "LineaanticipoServiceImpl.getListaServiciosMonedero() -> Entrada para obtener información del usuario logeado");

	            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

	            LOGGER.debug(
	                    "LineaanticipoServiceImpl.getListaServiciosMonedero() -> Salida de obtener información del usuario logeado");

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

	@Override
	@Transactional
	public UpdateResponseDTO updateServiciosMonedero(HttpServletRequest request, FichaMonederoItem ficha)
			throws Exception {
		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"LineaanticipoServiceImpl.updateServiciosMonedero() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"LineaanticipoServiceImpl.updateServiciosMonedero() -> Salida de obtener información del usuario logeado");

			List<PysServicioanticipo> servicios = new ArrayList<PysServicioanticipo>();

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug(
						"LineaanticipoServiceImpl.updateServiciosMonedero() -> Entrada para cambiar los movimientos del monedero");

				// 1. Se eliminan los servicios relacionados con el monedero
				// (PYS_SERVICIOANTICIPO)

				PysServicioanticipoExample serviciosMonederoExample = new PysServicioanticipoExample();

				serviciosMonederoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdpersonaEqualTo(ficha.getIdPersona());

				servicios = pysServicioanticipoMapper.selectByExample(serviciosMonederoExample);

				if (servicios != null && !servicios.isEmpty()) {
					response = pysServicioanticipoMapper.deleteByExample(serviciosMonederoExample);
					if (response == 0) {
						throw new SigaExceptions("Error al borrar los servicios del monedero en la BBDD.");
					}
				}
			}

			// 2. Se comprueba que servicios se han mantenido para introducirlos de nuevo
			// sin cambiar la fecha de edicion ni el usuario


			for (ListaServiciosMonederoItem servicioTarj : ficha.getServicios()) {
				int i = 0;
				while (i < servicios.size() && (!servicioTarj.getIdServicio().equals(servicios.get(i).getIdservicio())
						&& !servicioTarj.getIdServiciosInstitucion()
								.equals(servicios.get(i).getIdserviciosinstitucion())
						&& !servicioTarj.getIdTipoServicios().equals(servicios.get(i).getIdtiposervicios()))) {
					i++;
				}

				// Al salirse antes del bucle, significa que el servicio ya estaba asociado
				// antes
				if (i < servicios.size()) {
					// 2.a Se introducen los servicios que se han mantenido

					PysServicioanticipo servicioAnticipo = new PysServicioanticipo();

					servicioAnticipo.setIdanticipo(ficha.getIdAnticipo());
					servicioAnticipo.setIdpersona(ficha.getIdPersona());
					servicioAnticipo.setIdservicio(servicioTarj.getIdServicio());
					
					servicioAnticipo.setIdserviciosinstitucion(servicioTarj.getIdServiciosInstitucion());
					servicioAnticipo.setIdtiposervicios(servicioTarj.getIdTipoServicios());
					servicioAnticipo.setIdinstitucion(idInstitucion);

					servicioAnticipo.setFechamodificacion(servicios.get(i).getFechamodificacion());
					servicioAnticipo.setUsumodificacion(servicios.get(i).getUsumodificacion());

					response = pysServicioanticipoMapper.insert(servicioAnticipo);
					if (response == 0) {
						throw new SigaExceptions(
								"Error al insertar un servicio relacionado con un anticipo en la BBDD.");
					}
				} else {
					// 2.b Se introducen los servicios nuevos

					PysServicioanticipo servicioAnticipo = new PysServicioanticipo();

					servicioAnticipo.setIdanticipo(ficha.getIdAnticipo());
					servicioAnticipo.setIdpersona(ficha.getIdPersona());
					servicioAnticipo.setIdservicio(servicioTarj.getIdServicio());
					servicioAnticipo.setIdserviciosinstitucion(servicioTarj.getIdServiciosInstitucion());
					servicioAnticipo.setIdtiposervicios(servicioTarj.getIdTipoServicios());
					servicioAnticipo.setIdinstitucion(idInstitucion);

					servicioAnticipo.setFechamodificacion(new Date());
					servicioAnticipo.setUsumodificacion(usuarios.get(0).getIdusuario());

					response = pysServicioanticipoMapper.insert(servicioAnticipo);
					if (response == 0) {
						throw new SigaExceptions(
								"Error al insertar un servicio relacionado con un anticipo en la BBDD.");
					}
				}
			}
		}


		LOGGER.info("LineaanticipoServiceImpl.updateServiciosMonedero() -> Salida del servicio.");

		responsedto.setStatus(SigaConstants.OK);// HttpStatus.OK

		return responsedto;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO liquidarMonederos(HttpServletRequest request, List<ListaMonederosItem> monederos)
			throws Exception {
		InsertResponseDTO responsedto = new InsertResponseDTO();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
			LOGGER.debug(
					"LineaanticipoServiceImpl.liquidarMonederos() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"LineaanticipoServiceImpl.liquidarMonederos() -> Salida de obtener información del usuario logeado");

			List<PysServicioanticipo> servicios = new ArrayList<PysServicioanticipo>();

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("LineaanticipoServiceImpl.liquidarMonederos() -> Entrada para liquidar monederos");

				for (ListaMonederosItem monedero : monederos) {
					// 1. Se crea la liquidación
					
					PysLineaanticipo liquidacion = new PysLineaanticipo();
					
					liquidacion.setFechaefectiva(new Date());
					liquidacion.setIdanticipo(Short.valueOf(monedero.getIdAnticipo()));
					liquidacion.setIdinstitucion(idInstitucion);
					
					//Obtenemos el id de linea maximo
					PysLineaanticipoExample lineaExample = new PysLineaanticipoExample();
					
					lineaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdanticipoEqualTo(Short.valueOf(monedero.getIdAnticipo()))
					.andIdpersonaEqualTo(Long.valueOf(monedero.getIdPersona()));
					
					//Actualmente (05/01/2022) se el idLinea se inicia a partir de 0
					liquidacion.setIdlinea(Short.valueOf(String.valueOf(pysLineaanticipoExtendsMapper.countByExample(lineaExample))));
					
					liquidacion.setIdpersona(Long.valueOf(monedero.getIdPersona()));
					liquidacion.setImporteanticipado(monedero.getImporteRestante());
					liquidacion.setLiquidacion("1");

					liquidacion.setUsumodificacion(usuarios.get(0).getIdusuario());
					liquidacion.setFechamodificacion(new Date());
						
					response = pysLineaanticipoExtendsMapper.insert(liquidacion);
					if (response == 0) {
						throw new SigaExceptions(
								"Error al insertar la liquidacion de un monedero en la BBDD.");
					}
					
				}
			}
		}
		LOGGER.info("LineaanticipoServiceImpl.liquidarMonederos() -> Salida del servicio.");

		responsedto.setStatus(SigaConstants.OK);// HttpStatus.OK

		return responsedto;
	}
}
