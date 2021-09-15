package org.itcgae.siga.scs.services.impl.ejg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.EstadoRemesaItem;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasItem2;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CajgEjgremesa;
import org.itcgae.siga.db.entities.CajgEjgremesaExample;
import org.itcgae.siga.db.entities.CajgRemesa;
import org.itcgae.siga.db.entities.CajgRemesaExample;
import org.itcgae.siga.db.entities.CajgRemesaKey;
import org.itcgae.siga.db.entities.CajgRemesaestadosExample;
import org.itcgae.siga.db.entities.CajgRemesaestadosKey;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.mappers.CajgRemesaestadosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgEjgremesaExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgRemesaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IBusquedaRemesas;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusquedaRemesasServiceImpl implements IBusquedaRemesas {

	private Logger LOGGER = Logger.getLogger(BusquedaRemesasServiceImpl.class);

	@Autowired
	private ScsRemesasExtendsMapper scsRemesasExtendsMapper;
	
	@Autowired 
	private CajgRemesaExtendsMapper cajgRemesaExtendsMapper;
	
	@Autowired 
	private CajgRemesaestadosMapper cajgRemesaEstadosMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CajgEjgremesaExtendsMapper cajgEjgremesaExtendsMapper;

	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoejgExtendsMapper;

	@Override
	public ComboDTO comboEstado(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"comboEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());
			
			LOGGER.info(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"comboEstado() / ScsRemesasExtendsMapper.comboEstado() -> Entrada a ScsRemesasExtendsMapper para obtener los combo");

			comboItems = scsRemesasExtendsMapper.comboEstado(usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"comboEstado() / ScsRemesasExtendsMapper.comboEstado() -> Salida a ScsRemesasExtendsMapper para obtener los combo");

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			}
			
		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return comboDTO;
	}

	@Override
	public RemesaBusquedaDTO buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaBusquedaDTO remesaBusquedaDTO = new RemesaBusquedaDTO();
		List<RemesasItem> remesasItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"buscarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			LOGGER.info(
					"buscarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			LOGGER.info(
					"buscarRemesas() / ScsRemesasExtendsMapper.buscarRemesas() -> Entrada a ScsRemesasExtendsMapper para obtener las remesas");
			
			LOGGER.info(
					"Id remesa -> " + remesasBusquedaItem.getIdRemesa() + " | idLenguaje -> " + usuarios.get(0).getIdlenguaje() + " | fecha generacion -> " + remesasBusquedaItem.getFechaGeneracionDesde());

			remesasItems = scsRemesasExtendsMapper.buscarRemesas(remesasBusquedaItem, idInstitucion, usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"buscarRemesas() / ScsRemesasExtendsMapper.buscarRemesas() -> Salida a ScsRemesasExtendsMapper para obtener las remesas");

			if (remesasItems != null) {
				remesaBusquedaDTO.setRemesasItem(remesasItems);
			}
		
		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener las remesas");
		return remesaBusquedaDTO;
	}

	@Override
	public DeleteResponseDTO borrarRemesas(List<RemesasBusquedaItem> remesasBusquedaItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String remesasNoBorradas = "";
		CajgRemesa remesasItems;
		int response = 0;
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"borrarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"borrarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"borrarRemesas() / ScsRemesasExtendsMapper.borrarRemesas() -> Entrada a ScsRemesasExtendsMapper para eliminar las remesas");
			
			try {
				
				if(remesasBusquedaItem != null && remesasBusquedaItem.size() > 0) {
					
					LOGGER.info("remesaBusquedaItem -> " + remesasBusquedaItem.get(0).getIdRemesa());
					
					for(RemesasBusquedaItem remesas: remesasBusquedaItem) {
						
						LOGGER.info("Entra al foreach de la remesas");
						
						CajgRemesaKey remesaKey =new CajgRemesaKey();
						
						remesaKey.setIdinstitucion(idInstitucion);
						remesaKey.setIdremesa(Long.valueOf(remesas.getIdRemesa()));
						
						remesasItems = cajgRemesaExtendsMapper.selectByPrimaryKey(remesaKey);
						
						List<RemesasItem2> remesaEstado = scsRemesasExtendsMapper.isEstadoRemesaIniciada(remesas, idInstitucion);
						
						//Se comprueba que la remesa esté en estado "Iniciado"
						if(remesaEstado != null && remesaEstado.size()>0) {
							
							if(Integer.parseInt(remesaEstado.get(0).getIdRemesa()) == remesas.getIdRemesa()) {
							
								LOGGER.info("Se comprueba que la remesa con id -> " + remesas.getIdRemesa() + " está inciada");
								
								//Buscamos si la remesa tiene EJG asociados
								CajgEjgremesaExample example = new CajgEjgremesaExample();  
								example.createCriteria().andIdremesaEqualTo(Long.valueOf(remesas.getIdRemesa())).andIdinstitucionremesaEqualTo(idInstitucion);
								List<CajgEjgremesa> ejgRemesas = cajgEjgremesaExtendsMapper.selectByExample(example);
								
								LOGGER.info("Obtenemos los EJG asociados a la remesa");
								
								//Comprobamos si está vacia la lista con los EJG asociados
								if(!ejgRemesas.isEmpty()) {
									
									for(CajgEjgremesa cajgEjgRemesa: ejgRemesas) {
										
										//Buscamos los registros de los EJG asociados a la remesa con ciertas condiciones
										ScsEstadoejgExample exampleEstadoEjg = new ScsEstadoejgExample();
										exampleEstadoEjg.createCriteria().andAnioEqualTo(cajgEjgRemesa.getAnio())
										.andNumeroEqualTo(cajgEjgRemesa.getNumero())
										.andIdtipoejgEqualTo(cajgEjgRemesa.getIdtipoejg())
										.andIdinstitucionEqualTo(idInstitucion)
										.andIdestadoejgEqualTo(Short.valueOf("8"))
										.andFechabajaIsNull();
										
										List<ScsEstadoejg> estadoEjg = scsEstadoejgExtendsMapper.selectByExample(exampleEstadoEjg);
										
										//Comprobamos si está vacia la lista con los registros anteriores
										if(!estadoEjg.isEmpty()) {
											
											ScsEstadoejg cambiarFechaBaja = new ScsEstadoejg();
											
											//Hacemos el update de la columna FechaBaja del EJG
											for(ScsEstadoejg scsEstadoejg: estadoEjg) {
												
												LOGGER.info("Actualizamos el EJG con año/numero: " + scsEstadoejg.getAnio() + "/" + scsEstadoejg.getNumero() + " para ponerle FECHABAJA");
											
												cambiarFechaBaja.setAnio(scsEstadoejg.getAnio());
												cambiarFechaBaja.setNumero(scsEstadoejg.getNumero());
												cambiarFechaBaja.setIdinstitucion(scsEstadoejg.getIdinstitucion());
												cambiarFechaBaja.setIdtipoejg(scsEstadoejg.getIdtipoejg());
												cambiarFechaBaja.setIdestadoporejg(scsEstadoejg.getIdestadoporejg());
												cambiarFechaBaja.setFechabaja(new Date());
												
												response = scsEstadoejgExtendsMapper.updateByPrimaryKeySelective(cambiarFechaBaja);	
												
											}
											
										}
										
										LOGGER.info("Borramos la relación entre el EJG y la remesa");
										
										//Borramos la relación entre el ejg y la remesa
										
										CajgEjgremesaExample ejgRemesaExample = new CajgEjgremesaExample();
										ejgRemesaExample.createCriteria().andIdejgremesaEqualTo(cajgEjgRemesa.getIdejgremesa());
										response = cajgEjgremesaExtendsMapper.deleteByExample(ejgRemesaExample);
										
									}
									
								}
								
								LOGGER.info("Borramos la relacion entre la remesa y el estado de la misma");
								
								//Borramos la relacion de la remesa y su estado
								CajgRemesaestadosExample remesaEstadoKey = new CajgRemesaestadosExample();
								remesaEstadoKey.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdremesaEqualTo(Long.valueOf(remesaEstado.get(0).getIdRemesa()));
								
								response = cajgRemesaEstadosMapper.deleteByExample(remesaEstadoKey);
								
							}
						
							LOGGER.info("Borramos la remesa con id: " + remesas.getIdRemesa());
							
							//Borramos la remesa
							CajgRemesaExample remesaExample= new CajgRemesaExample();
							remesaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdremesaEqualTo(Long.valueOf(remesas.getIdRemesa()));
							
							response = cajgRemesaExtendsMapper.deleteByExample(remesaExample);
							
						}else {
							remesasNoBorradas += remesasItems.getNumero() + ",";
						}
						
					}
					
				}
				
			}catch (Exception e) {
				response = 0;
				error.setCode(400);
				error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				deleteResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.info("Se ha producido un error en BBDD contacte con su administrador");
				throw e;
			}
			
			if (!remesasNoBorradas.equals("") && error.getDescription() == null) {
				error.setCode(200);
				
				String nRegistro = "";
				
				String[] remesas = remesasNoBorradas.split(",");
				
				for(int i = 0; i < remesas.length; i++) {
					if(i == remesas.length - 1) {
						nRegistro += remesas[i].toString();
					}else {
						nRegistro += remesas[i].toString() + ", ";
					}
				}
				
				error.setDescription("No se ha(n) borrado la(s) remesa(s) con Nº de Registro: " + nRegistro + ", porque no estan en estado 'Iniciado'");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.info("No se ha(n) borrado la(s) remesa(s) con Nº de Registro: " + nRegistro + ", porque no estan en estado 'Iniciado'");
			} else if (error.getCode() == null) {
				error.setCode(200);
				error.setDescription("Se han borrado las remesas correctamente");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.info("Se han borrado las remesas correctamente");
			}
							
			deleteResponseDTO.setError(error);

			LOGGER.info(
					"borrarRemesas() / ScsRemesasExtendsMapper.borrarRemesas() -> Salida a ScsRemesasExtendsMapper para eliminar las remesas");
		
		}
		LOGGER.info("getLabel() -> Salida del servicio para eliminar las remesas");
		
		return deleteResponseDTO;
	}

	@Override
	public EstadoRemesaDTO listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				EstadoRemesaDTO estadoRemesaDTO = new EstadoRemesaDTO();
				List<EstadoRemesaItem> estadoRemesaItem = null;

				if (idInstitucion != null) {
					AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
					exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
					LOGGER.info(
							"listadoEstadoRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

					List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

					LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());
					
					LOGGER.info(
							"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

					LOGGER.info(
							"listadoEstadoRemesa() / ScsRemesasExtendsMapper.listadoEstadoRemesa() -> Entrada a ScsRemesasExtendsMapper para obtener los estados de la remesa");
					
					estadoRemesaItem = scsRemesasExtendsMapper.listadoEstadoRemesa(remesasBusquedaItem, Short.valueOf(idInstitucion), usuarios.get(0).getIdlenguaje());

					LOGGER.info(
							"listadoEstadoRemesa() / ScsRemesasExtendsMapper.listadoEstadoRemesa() -> Salida a ScsRemesasExtendsMapper para obtener los estados de la remesa");

					if (estadoRemesaItem != null) {
						estadoRemesaDTO.setEstadoRemesaItem(estadoRemesaItem);
					}
					
				}
				LOGGER.info("getLabel() -> Salida del servicio para obtener los estado de la remesa");
				return estadoRemesaDTO;
	}
}