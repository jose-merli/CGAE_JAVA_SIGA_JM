package org.itcgae.siga.scs.services.impl.ejg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesas;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.EstadoRemesaItem;
import org.itcgae.siga.DTOs.scs.RemesaAccionItem;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasItem2;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_ESTADOSCOLA;
import org.itcgae.siga.commons.utils.GestorContadores;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CajgEjgremesa;
import org.itcgae.siga.db.entities.CajgEjgremesaExample;
import org.itcgae.siga.db.entities.CajgRemesa;
import org.itcgae.siga.db.entities.CajgRemesaExample;
import org.itcgae.siga.db.entities.CajgRemesaKey;
import org.itcgae.siga.db.entities.CajgRemesaestados;
import org.itcgae.siga.db.entities.CajgRemesaestadosExample;
import org.itcgae.siga.db.entities.CajgRemesaestadosKey;
import org.itcgae.siga.db.entities.CajgRespuestaEjgremesa;
import org.itcgae.siga.db.entities.CajgRespuestaEjgremesaExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.EcomColaParametrosExample;
import org.itcgae.siga.db.entities.EcomOperacion;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.mappers.CajgRemesaMapper;
import org.itcgae.siga.db.mappers.CajgRemesaestadosMapper;
import org.itcgae.siga.db.mappers.CajgRespuestaEjgremesaMapper;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EcomOperacionMapper;
import org.itcgae.siga.db.mappers.EcomOperacionTipoaccionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgEjgremesaExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgRemesaExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgRespuestaEjgremesaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEejgPeticionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.ejg.IBusquedaRemesas;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

	@Autowired
	private AdmContadorExtendsMapper admContadorExtendsMapper;

	@Autowired
	private GenParametrosMapper genParametrosMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CajgRespuestaEjgremesaMapper cajgRespuestaEjgremesaMapper;
	
	@Autowired
	private CajgRespuestaEjgremesaExtendsMapper cajgRespuestaEjgremesaExtendsMapper;
	
	@Autowired
	private EcomColaMapper ecomColaMapper;
	
	@Autowired
	private EcomOperacionMapper ecomOperacionMapper;
	
	@Autowired
	private EcomOperacionTipoaccionMapper ecomOperacionTipoaccionMapper;
	
	@Autowired
	private EcomColaParametrosMapper ecomColaParametrosMapper;
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	
	@Autowired
    private ScsEejgPeticionesExtendsMapper scsExpedienteEconomicoExtendsMapper;
	
	@Autowired
    private ScsEejgPeticionesExtendsMapper ScsEejgpeticionesExtendsMapper;

	@Autowired
	private EJGIntercambiosHelper ejgIntercambiosHelper;
	
	@Autowired
    private GestorContadores gestor;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private ScsEstadoejgExtendsMapper scsEstadoEjgextendsMapper;
	
	@Autowired
	private CajgRemesaMapper cajgRemesaMapper;

	@Override
	public ComboDTO comboEstado(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos debugrmación del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"comboEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"comboEstado() / ScsRemesasExtendsMapper.comboEstado() -> Entrada a ScsRemesasExtendsMapper para obtener los combo");

			comboItems = scsRemesasExtendsMapper.comboEstado(usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"comboEstado() / ScsRemesasExtendsMapper.comboEstado() -> Salida a ScsRemesasExtendsMapper para obtener los combo");

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return comboDTO;
	}

	@Override
	public RemesaBusquedaDTO buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos debugrmación del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaBusquedaDTO remesaBusquedaDTO = new RemesaBusquedaDTO();
		List<RemesasItem> remesasItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"buscarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			GenParametrosExample genParametrosExample = new GenParametrosExample();
			genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
					.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
			genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
			LOGGER.info(
					"buscarRemesas() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

			LOGGER.info(
					"buscarRemesas() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

			LOGGER.info(
					"buscarRemesas() / scsPersonajgExtendsMapper.searchIdPersonaJusticiables() -> Entrada a scsPersonajgExtendsMapper para obtener las personas justiciables");
			if (tamMax != null) {
				tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
			} else {
				tamMaximo = null;
			}

			LOGGER.debug(
					"buscarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"buscarRemesas() / ScsRemesasExtendsMapper.buscarRemesas() -> Entrada a ScsRemesasExtendsMapper para obtener las remesas");

			LOGGER.debug("Id remesa -> " + remesasBusquedaItem.getIdRemesa() + " | idLenguaje -> "
					+ usuarios.get(0).getIdlenguaje() + " | fecha generacion -> "
					+ remesasBusquedaItem.getFechaGeneracionDesde());

			remesasItems = scsRemesasExtendsMapper.buscarRemesas(remesasBusquedaItem, idInstitucion, tamMaximo, 
					usuarios.get(0).getIdlenguaje());

			String nRegistro;

			for (int i = 0; i < remesasItems.size(); i++) {
				nRegistro = "";
				if(remesasItems.get(i).getPrefijo() != null) {
					nRegistro += remesasItems.get(i).getPrefijo();
				}
				
				if(remesasItems.get(i).getNumero() != null) {
					nRegistro += remesasItems.get(i).getNumero();
				}

				if(remesasItems.get(i).getSufijo() != null) {
					nRegistro += remesasItems.get(i).getSufijo();
				}
				
				if(nRegistro.length() < 5) {
					String ceros = "";
					for(; (nRegistro.length() + ceros.length()) < 5;) {
						ceros += "0";
					}
					nRegistro = ceros + nRegistro;
				}
				
				remesasItems.get(i).setnRegistro(nRegistro);
			}

			LOGGER.debug(
					"buscarRemesas() / ScsRemesasExtendsMapper.buscarRemesas() -> Salida a ScsRemesasExtendsMapper para obtener las remesas");

			if (remesasItems != null) {
				remesaBusquedaDTO.setRemesasItem(remesasItems);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener las remesas");
		return remesaBusquedaDTO;
	}

	@Override
	@Transactional
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
			LOGGER.debug(
					"borrarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"borrarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"borrarRemesas() / ScsRemesasExtendsMapper.borrarRemesas() -> Entrada a ScsRemesasExtendsMapper para eliminar las remesas");

			try {

				if (remesasBusquedaItem != null && remesasBusquedaItem.size() > 0) {

					LOGGER.debug("remesaBusquedaItem -> " + remesasBusquedaItem.get(0).getIdRemesa());

					for (RemesasBusquedaItem remesas : remesasBusquedaItem) {

						LOGGER.debug("Entra al foreach de la remesas");

						CajgRemesaKey remesaKey = new CajgRemesaKey();

						remesaKey.setIdinstitucion(idInstitucion);
						remesaKey.setIdremesa(Long.valueOf(remesas.getIdRemesa()));

						remesasItems = cajgRemesaExtendsMapper.selectByPrimaryKey(remesaKey);

						List<RemesasItem2> remesaEstado = scsRemesasExtendsMapper.isEstadoRemesaIniciada(remesas,
								idInstitucion);

						// Se comprueba que la remesa esté en estado "Iniciado"
						if (remesaEstado != null && remesaEstado.size() > 0) {

							if (Integer.parseInt(remesaEstado.get(0).getIdRemesa()) == remesas.getIdRemesa()) {

								LOGGER.debug("Se comprueba que la remesa con id -> " + remesas.getIdRemesa()
										+ " está inciada");

								// Buscamos si la remesa tiene EJG asociados
								CajgEjgremesaExample example = new CajgEjgremesaExample();
								example.createCriteria().andIdremesaEqualTo(Long.valueOf(remesas.getIdRemesa()))
										.andIdinstitucionremesaEqualTo(idInstitucion);
								List<CajgEjgremesa> ejgRemesas = cajgEjgremesaExtendsMapper.selectByExample(example);

								LOGGER.debug("Obtenemos los EJG asociados a la remesa");

								// Comprobamos si está vacia la lista con los EJG asociados
								if (!ejgRemesas.isEmpty()) {

									for (CajgEjgremesa cajgEjgRemesa : ejgRemesas) {

										// Buscamos los registros de los EJG asociados a la remesa con ciertas
										// condiciones
										ScsEstadoejgExample exampleEstadoEjg = new ScsEstadoejgExample();
										exampleEstadoEjg.createCriteria().andAnioEqualTo(cajgEjgRemesa.getAnio())
												.andNumeroEqualTo(cajgEjgRemesa.getNumero())
												.andIdtipoejgEqualTo(cajgEjgRemesa.getIdtipoejg())
												.andIdinstitucionEqualTo(idInstitucion)
												.andIdestadoejgEqualTo(Short.valueOf("8")).andFechabajaIsNull();

										List<ScsEstadoejg> estadoEjg = scsEstadoejgExtendsMapper
												.selectByExample(exampleEstadoEjg);

										// Comprobamos si está vacia la lista con los registros anteriores
										if (!estadoEjg.isEmpty()) {

											ScsEstadoejg cambiarFechaBaja = new ScsEstadoejg();

											// Hacemos el update de la columna FechaBaja del EJG
											for (ScsEstadoejg scsEstadoejg : estadoEjg) {

												LOGGER.debug("Actualizamos el EJG con año/numero: "
														+ scsEstadoejg.getAnio() + "/" + scsEstadoejg.getNumero()
														+ " para ponerle FECHABAJA");

												cambiarFechaBaja.setAnio(scsEstadoejg.getAnio());
												cambiarFechaBaja.setNumero(scsEstadoejg.getNumero());
												cambiarFechaBaja.setIdinstitucion(scsEstadoejg.getIdinstitucion());
												cambiarFechaBaja.setIdtipoejg(scsEstadoejg.getIdtipoejg());
												cambiarFechaBaja.setIdestadoporejg(scsEstadoejg.getIdestadoporejg());
												cambiarFechaBaja.setFechabaja(new Date());

												response = scsEstadoejgExtendsMapper
														.updateByPrimaryKeySelective(cambiarFechaBaja);

											}

										}

										LOGGER.debug("Borramos la relación entre el EJG y la remesa");

										// Borramos la relación entre el ejg y la remesa

										CajgEjgremesaExample ejgRemesaExample = new CajgEjgremesaExample();
										ejgRemesaExample.createCriteria()
												.andIdejgremesaEqualTo(cajgEjgRemesa.getIdejgremesa());
										response = cajgEjgremesaExtendsMapper.deleteByExample(ejgRemesaExample);

									}

								}

								LOGGER.debug("Borramos la relacion entre la remesa y el estado de la misma");

								// Borramos la relacion de la remesa y su estado
								CajgRemesaestadosExample remesaEstadoKey = new CajgRemesaestadosExample();
								remesaEstadoKey.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdremesaEqualTo(Long.valueOf(remesaEstado.get(0).getIdRemesa()));

								response = cajgRemesaEstadosMapper.deleteByExample(remesaEstadoKey);

							}

							LOGGER.debug("Borramos la remesa con id: " + remesas.getIdRemesa());

							// Borramos la remesa
							CajgRemesaExample remesaExample = new CajgRemesaExample();
							remesaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdremesaEqualTo(Long.valueOf(remesas.getIdRemesa()));

							response = cajgRemesaExtendsMapper.deleteByExample(remesaExample);

						} else {
							remesasNoBorradas += remesasItems.getNumero() + ",";
						}

					}

				}

			} catch (Exception e) {
				response = 0;
				error.setCode(400);
				error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				deleteResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.debug("Se ha producido un error en BBDD contacte con su administrador");
				throw e;
			}

			if (!remesasNoBorradas.equals("") && error.getDescription() == null) {
				error.setCode(200);

				String nRegistro = "";

				String[] remesas = remesasNoBorradas.split(",");

				for (int i = 0; i < remesas.length; i++) {
					if (i == remesas.length - 1) {
						nRegistro += remesas[i].toString();
					} else {
						nRegistro += remesas[i].toString() + ", ";
					}
				}

				error.setDescription("No se ha(n) borrado la(s) remesa(s) con Nº de Registro: " + nRegistro
						+ ", porque no estan en estado 'Iniciado'");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.debug("No se ha(n) borrado la(s) remesa(s) con Nº de Registro: " + nRegistro
						+ ", porque no estan en estado 'Iniciado'");
			} else if (error.getCode() == null) {
				error.setCode(200);
				error.setDescription("Se han borrado las remesas correctamente");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.debug("Se han borrado las remesas correctamente");
			}

			deleteResponseDTO.setError(error);

			LOGGER.debug(
					"borrarRemesas() / ScsRemesasExtendsMapper.borrarRemesas() -> Salida a ScsRemesasExtendsMapper para eliminar las remesas");

		}
		LOGGER.debug("getLabel() -> Salida del servicio para eliminar las remesas");

		return deleteResponseDTO;
	}

	@Override
	public EstadoRemesaDTO listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos debugrmación del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EstadoRemesaDTO estadoRemesaDTO = new EstadoRemesaDTO();
		List<EstadoRemesaItem> estadoRemesaItem = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"listadoEstadoRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"listadoEstadoRemesa() / ScsRemesasExtendsMapper.listadoEstadoRemesa() -> Entrada a ScsRemesasExtendsMapper para obtener los estados de la remesa");

			estadoRemesaItem = scsRemesasExtendsMapper.listadoEstadoRemesa(remesasBusquedaItem, idInstitucion, usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"listadoEstadoRemesa() / ScsRemesasExtendsMapper.listadoEstadoRemesa() -> Salida a ScsRemesasExtendsMapper para obtener los estados de la remesa");

			if (estadoRemesaItem != null) {
				estadoRemesaDTO.setEstadoRemesaItem(estadoRemesaItem);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener los estado de la remesa");
		return estadoRemesaDTO;
	}

	@Override
	public AdmContador getUltimoRegitroRemesa(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AdmContador contador = new AdmContador();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"getUltimoRegitroRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"getUltimoRegitroRemesa() / admContadorExtendsMapper.getUltimoRegitroRemesa() -> Entrada a AdmContadorExtendsMapper para obtener el ultimo registro de las remesas");
			
			contador = gestor.getContador(idInstitucion, "REMESA");
			
			if(contador != null) {
				String numero;
				try {
					numero = gestor.getNuevoContador(contador);
					contador.setContador(Long.valueOf(numero));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			LOGGER.debug(
					"getUltimoRegitroRemesa() / admContadorExtendsMapper.getUltimoRegitroRemesa() -> Salida a AdmContadorExtendsMapper para obtener el ultimo registro de las remesas");

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return contador;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarRemesa(RemesasItem remesasItem, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		Integer idRemesa = 0;
		int response = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.debug(
					"guardarRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"guardarRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			if (remesasItem.getIdRemesa() == 0) {
				try {

					LOGGER.debug(
							"guardarRemesa() / cajgRemesaExtendsMapper.insert() -> Entrada a CajgRemesaExtendsMapper para insertar una remesa");

					CajgRemesa remesa = new CajgRemesa();
					
					AdmContador contador = getUltimoRegitroRemesa(request);

					RemesasItem rem = scsRemesasExtendsMapper.getMaxIdRemesa(idInstitucion);

					idRemesa = rem.getIdRemesa();

					remesa.setIdinstitucion(idInstitucion);
					remesa.setIdremesa(Long.valueOf(rem.getIdRemesa()));
					remesa.setDescripcion(remesasItem.getDescripcion());
					if(contador != null) {
						remesa.setPrefijo(contador.getPrefijo());
						remesa.setSufijo(contador.getSufijo());
					}
					remesa.setNumero(String.valueOf(remesasItem.getNumero()));
					remesa.setIdtiporemesa(remesasItem.isInformacionEconomica() ? Short.valueOf("1") : Short.valueOf("0"));
					remesa.setFechamodificacion(new Date());

					response = cajgRemesaExtendsMapper.insert(remesa);

					LOGGER.debug(
							"guardarRemesa() / cajgRemesaExtendsMapper.insert() -> Salida de CajgRemesaExtendsMapper para insertar una remesa");

					if (response == 1) {

						LOGGER.debug(
								"guardarRemesa() / admContadorExtendsMapper.selectByPrimaryKey() -> Entrada de AdmContadorExtendsMapper para obtener el contador de REMESA");

						LOGGER.debug(
								"guardarRemesa() / admContadorExtendsMapper.selectByPrimaryKey() -> Salida de AdmContadorExtendsMapper para obtener el contador de REMESA");

						if (contador != null) {

							LOGGER.debug(
									"guardarRemesa() / admContadorExtendsMapper.updateByPrimaryKeySelective() -> Entrada de AdmContadorExtendsMapper para incrementar en 1 el contador de REMESA");
							
							gestor.setContador(contador, contador.getContador().toString(), true);

							LOGGER.debug(
									"guardarRemesa() / admContadorExtendsMapper.updateByPrimaryKeySelective() -> Salida de AdmContadorExtendsMapper para incrementar en 1 el contador de REMESA");

							LOGGER.debug(
									"guardarRemesa() / cajgRemesaEstadosMapper.insert() -> Entrada de CajgRemesaEstadosMapper para insertar un estado de una remesas");

							CajgRemesaestados remesaEstado = new CajgRemesaestados();

							remesaEstado.setIdinstitucion(idInstitucion);
							remesaEstado.setIdremesa(Long.valueOf(rem.getIdRemesa()));
							remesaEstado.setIdestado(Short.valueOf("0"));
							remesaEstado.setFecharemesa(new Date());
							remesaEstado.setFechamodificacion(new Date());

							response = cajgRemesaEstadosMapper.insert(remesaEstado);

							LOGGER.debug(
									"guardarRemesa() / cajgRemesaEstadosMapper.insert() -> Salida de CajgRemesaEstadosMapper para insertar un estado de una remesas");
						}
					}

				} catch (Exception e) {
					LOGGER.error("guardarRemesa()" + e.getMessage());

					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
					updateResponseDTO.setError(error);
					throw e;
				}

				if (response == 0) {
					error.setCode(400);
					if (error.getDescription() == null) {
						error.setDescription("No se ha añadido la remesa");
					}

					updateResponseDTO.setStatus(SigaConstants.KO);
				} else {
					updateResponseDTO.setId(String.valueOf(idRemesa));
					error.setCode(200);
					error.setDescription("Remesa añadida correctamente");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}

			} else {
				try {

					LOGGER.debug(
							"guardarRemesa() / cajgRemesaExtendsMapper.selectByPrimaryKey(example) -> Entrada a CajgRemesaExtendsMapper para buscar la remesa a actualizar");

					CajgRemesaKey remesa = new CajgRemesaKey();

					remesa.setIdinstitucion(idInstitucion);
					remesa.setIdremesa(Long.valueOf(remesasItem.getIdRemesa()));

					CajgRemesa remesaBD = cajgRemesaExtendsMapper.selectByPrimaryKey(remesa);

					LOGGER.debug(
							"guardarRemesa() / cajgRemesaExtendsMapper.selectByPrimaryKey(example) -> Salida de CajgRemesaExtendsMapper para buscar la remesa a actualizar");

					if (remesaBD != null) {

						LOGGER.debug(
								"guardarRemesa() / cajgRemesaExtendsMapper.updateByPrimaryKeySelective(example) -> Entrada a CajgRemesaExtendsMapper para actulizar la remesa");

						remesaBD.setDescripcion(remesasItem.getDescripcion());

						response = cajgRemesaExtendsMapper.updateByPrimaryKeySelective(remesaBD);

						LOGGER.debug(
								"guardarRemesa() / cajgRemesaExtendsMapper.updateByPrimaryKeySelective(example) -> Salida de CajgRemesaExtendsMapper para actulizar la remesa");

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
					throw e;
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha modificado la remesa");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (error.getCode() == null) {
					updateResponseDTO.setId(String.valueOf(remesasItem.getIdRemesa()));
					error.setCode(200);
					error.setDescription("Se ha modificado la remesa correctamente");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}

		updateResponseDTO.setError(error);

		LOGGER.debug("guardarRemesa() -> Salida del servicio para insertar/actualizar remesa");

		return updateResponseDTO;
	}

	@Override
	public EJGRemesaDTO getEJGRemesa(RemesasItem remesasItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos debugrmación del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EJGRemesaDTO ejgRemesaDTO = new EJGRemesaDTO();
		List<EJGRemesaItem> ejgRemesaItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"getEJGRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"getEJGRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"getEJGRemesa() / ScsRemesasExtendsMapper.getEJGRemesa() -> Entrada a ScsRemesasExtendsMapper para obtener los ejg asociados a la remesa");

			LOGGER.debug("Id remesa -> " + remesasItem.getIdRemesa());

			ejgRemesaItems = scsRemesasExtendsMapper.getEJGRemesa(remesasItem, idInstitucion, usuarios.get(0).getIdlenguaje());

			for (int i = 0; i < ejgRemesaItems.size(); i++) {
				String incidencias = ejgRemesaItems.get(i).getNumIncidencias() + "/"
						+ ejgRemesaItems.get(i).getIncidenciasAntesEnvio() + "/"
						+ ejgRemesaItems.get(i).getIncidenciasDespuesEnvio() + "/"
						+ ejgRemesaItems.get(i).getNuevaRemesa();
				ejgRemesaItems.get(i).setIncidencias(incidencias);
			}

			LOGGER.debug(
					"getEJGRemesa() / ScsRemesasExtendsMapper.getEJGRemesa() -> Salida a ScsRemesasExtendsMapper para obtener los ejg asociados a la remesa");

			if (ejgRemesaItems != null) {
				ejgRemesaDTO.setEJGRemesaItem(ejgRemesaItems);
			}
		}

		LOGGER.debug("getLabel() -> Salida del servicio para obtener los ejg asociados a la remesa");

		return ejgRemesaDTO;
	}

	@Override
	@Transactional
	public DeleteResponseDTO borrarExpedientesRemesa(List<EJGRemesaItem> ejgRemesaItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.debug(
					"borrarExpedientesRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug(
					"borrarExpedientesRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"borrarExpedientesRemesa() / ScsRemesasExtendsMapper.borrarExpedientesRemesa() -> Entrada a ScsRemesasExtendsMapper para eliminar los expedientes de la remesa");

			try {

				if (ejgRemesaItem != null && ejgRemesaItem.size() > 0) {

					LOGGER.debug("remesaBusquedaItem -> " + ejgRemesaItem.get(0).getIdentificadorEJG());

					for (EJGRemesaItem ejg : ejgRemesaItem) {

						LOGGER.debug("Entra al foreach de los EJG's");

						// Buscamos los registros de los EJG asociados a la remesa con ciertas
						// condiciones
						ScsEstadoejgExample exampleEstadoEjg = new ScsEstadoejgExample();
						exampleEstadoEjg.createCriteria().andAnioEqualTo((short) ejg.getAnioEJG())
								.andNumeroEqualTo(Long.valueOf(ejg.getNumeroEJG()))
								.andIdtipoejgEqualTo((short) ejg.getIdTipoEJG()).andIdinstitucionEqualTo(idInstitucion)
								.andIdestadoejgEqualTo(Short.valueOf("8")).andFechabajaIsNull();

						List<ScsEstadoejg> estadoEjg = scsEstadoejgExtendsMapper.selectByExample(exampleEstadoEjg);

						// Comprobamos si está vacia la lista con los registros anteriores
						if (!estadoEjg.isEmpty()) {

							ScsEstadoejg cambiarFechaBaja = new ScsEstadoejg();

							// Hacemos el update de la columna FechaBaja del EJG
							for (ScsEstadoejg scsEstadoejg : estadoEjg) {

								LOGGER.debug("Actualizamos el EJG con año/numero: " + scsEstadoejg.getAnio() + "/"
										+ scsEstadoejg.getNumero() + " para ponerle FECHABAJA");

								cambiarFechaBaja.setAnio(scsEstadoejg.getAnio());
								cambiarFechaBaja.setNumero(scsEstadoejg.getNumero());
								cambiarFechaBaja.setIdinstitucion(scsEstadoejg.getIdinstitucion());
								cambiarFechaBaja.setIdtipoejg(scsEstadoejg.getIdtipoejg());
								cambiarFechaBaja.setIdestadoporejg(scsEstadoejg.getIdestadoporejg());
								cambiarFechaBaja.setFechabaja(new Date());

								response = scsEstadoejgExtendsMapper.updateByPrimaryKeySelective(cambiarFechaBaja);

							}

						}

						LOGGER.debug("Borramos la relación entre el EJG y la remesa");

						// Borramos la relación entre el ejg y la remesa

						CajgEjgremesaExample ejgRemesaExample = new CajgEjgremesaExample();
						ejgRemesaExample.createCriteria().andIdejgremesaEqualTo(Long.valueOf(ejg.getIdEjgRemesa()));
						response = cajgEjgremesaExtendsMapper.deleteByExample(ejgRemesaExample);

					}

				}

			} catch (Exception e) {
				response = 0;
				error.setCode(400);
				error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
				deleteResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.debug("Se ha producido un error en BBDD contacte con su administrador");
				throw e;
			}

			if (error.getCode() == null) {
				error.setCode(200);
				error.setDescription("Se han borrado los expedientes correctamente");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.debug("Se han borrado los expedientes correctamente");
			}

			deleteResponseDTO.setError(error);

			LOGGER.debug(
					"borrarExpedientesRemesa() / ScsRemesasExtendsMapper.borrarExpedientesRemesa() -> Salida a ScsRemesasExtendsMapper para eliminar los expedientes de la remesa");

		}
		LOGGER.debug("getLabel() -> Salida del servicio para eliminar los expedientes");

		return deleteResponseDTO;
	}

	public GenParametros getTipoPCAJG(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"getTipoPCAJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"getTipoPCAJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"getTipoPCAJG() / genParametrosMapper.selectByPrimaryKey() -> Entrada a GenParametrosMapper para obtener el tipo de PCAJG de la institucion logeada");
			GenParametrosKey key = new GenParametrosKey();

			key.setIdinstitucion(idInstitucion);
			key.setModulo("SCS");
			key.setParametro("PCAJG_TIPO");

			parametro = genParametrosMapper.selectByPrimaryKey(key);
			
			if(parametro == null) {
				key.setIdinstitucion(new Short("0"));
				parametro = genParametrosMapper.selectByPrimaryKey(key);	
			}

			LOGGER.debug(
					"getTipoPCAJG() / genParametrosMapper.selectByPrimaryKey() -> Entrada a GenParametrosMapper para obtener el tipo de PCAJG de la institucion logeada"
							+ parametro.getValor());

			LOGGER.debug(
					"getTipoPCAJG() / genParametrosMapper.selectByPrimaryKey() -> Salida a GenParametrosMapper para obtener el tipo de PCAJG de la institucion logeada");

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener el tipo de PCAJG de la institucion logeada");
		return parametro;
	}

	@Override
	public CheckAccionesRemesasDTO getAcciones(RemesasItem remesasItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CheckAccionesRemesasDTO checkAccionesRemesasDTO = new CheckAccionesRemesasDTO();
		List<CheckAccionesRemesas> checkAccionesRemesasItems = null;
		GenParametros parametro = new GenParametros();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"validaOperacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"validaOperacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"validaOperacion() / ScsRemesasExtendsMapper.checkAcciones() -> Entrada a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");

			parametro = getTipoPCAJG(request);

			checkAccionesRemesasItems = scsRemesasExtendsMapper.getAcciones(remesasItem, idInstitucion,
					usuarios.get(0).getIdlenguaje(), parametro.getValor());

			LOGGER.debug(
					"validaOperacion() / ScsRemesasExtendsMapper.checkAcciones() -> Salida a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");

			if (checkAccionesRemesasItems != null) {
				checkAccionesRemesasDTO.setCheckAccionesRemesas(checkAccionesRemesasItems);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return checkAccionesRemesasDTO;
	}

	@Override
	public InsertResponseDTO ejecutaOperacionRemesa(RemesaAccionItem remesaAccionItem, HttpServletRequest request) throws SigaExceptions {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();
		boolean validar = false;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"ejecutaOperacionRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"ejecutaOperacionRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"ejecutaOperacionRemesa() / ScsRemesasExtendsMapper.ejecutaOperacionRemesa() -> Entrada a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");
			
			parametro = getTipoPCAJG(request);
			
			GenRecursosKey grKey = new GenRecursosKey();
			
			grKey.setIdrecurso("scs.mensaje.validando");
			grKey.setIdlenguaje(usuarios.get(0).getIdlenguaje());
			
			GenRecursos gr = genRecursosMapper.selectByPrimaryKey(grKey);

			validar = validarAccion(remesaAccionItem, request, remesaAccionItem.getDescripcion());
			
			if (remesaAccionItem.getAccion() != 8) {
				if(validar) {
					insertResponseDTO = validaEnviaExpedientes(idInstitucion, remesaAccionItem, parametro.getValor(), gr.getDescripcion(), request, usuarios.get(0).getUsumodificacion());

				} else {
					LOGGER.error("Error al validar la remesa. No cumple los requisitos");
					error.setCode(200);
					error.setDescription("Error al validar la remesa. No cumple los requisitos.");
					insertResponseDTO.setStatus(SigaConstants.OK);
					insertResponseDTO.setError(error);
				}

				if (remesaAccionItem.getAccion() == 2 && ejgIntercambiosHelper.isColegioZonaComun(idInstitucion)
						&& ejgIntercambiosHelper.envioPericlesDisponible(idInstitucion)) {
					if(insertResponseDTO.getStatus().equals(SigaConstants.OK) && insertResponseDTO.getError().getCode() == 200) {
						LOGGER.debug("ejecutaOperacionRemesa() / insertaEstadoEjgEnviandoPericles() -> Comenzando el envío de los EJG al servicio de Pericles");
						CajgRemesa cajgRemesaKey = new CajgRemesa();
						cajgRemesaKey.setIdinstitucion(idInstitucion);
						cajgRemesaKey.setIdremesa(Long.valueOf(remesaAccionItem.getIdRemesa()));
						insertResponseDTO = insertaEstadoEjgEnviandoPericles(cajgRemesaKey, usuarios.get(0));
						LOGGER.debug("ejecutaOperacionRemesa() / insertaEstadoEjgEnviandoPericles() -> Finalizando el envío de los EJG al servicio de Pericles");
					}
				}
			}

			LOGGER.debug(
					"ejecutaOperacionRemesa() / ScsRemesasExtendsMapper.checkAcciones() -> Salida a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return insertResponseDTO;
	}

	public boolean validarAccion(RemesaAccionItem remesaAccionItem, HttpServletRequest request, String accion) {
		RemesasBusquedaItem remesa = new RemesasBusquedaItem();
		remesa.setIdRemesa(remesaAccionItem.getIdRemesa());
		EstadoRemesaDTO estadoRemesa = listadoEstadoRemesa(remesa, request);
		
		if(estadoRemesa != null && estadoRemesa.getEstadoRemesaItem().size() > 0) {
			RemesasItem remesasItem = new RemesasItem();
			remesasItem.setEstado(estadoRemesa.getEstadoRemesaItem().get(estadoRemesa.getEstadoRemesaItem().size() - 1).getEstado());
			
			CheckAccionesRemesasDTO acciones = getAcciones(remesasItem, request);
			
			if(acciones != null && acciones.getCheckAccionesRemesas().size() > 0) {
				for(CheckAccionesRemesas aux: acciones.getCheckAccionesRemesas()) {
					if(aux.getDescripcion().equals(accion)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public ResponseEntity<InputStreamResource> descargar(RemesaAccionItem remesaAccionItem, HttpServletRequest request) throws SigaExceptions {
	
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();
		boolean validar = false;
		byte[] buf = {};
		ByteArrayInputStream byteInput = new ByteArrayInputStream(buf);
		ResponseEntity<InputStreamResource> res = new ResponseEntity<InputStreamResource>(new InputStreamResource(byteInput), null, HttpStatus.NO_CONTENT);

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"descargar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"descargar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			LOGGER.debug(
					"descargar() / ScsRemesasExtendsMapper.descargar() -> Entrada a ScsRemesasExtendsMapper para descargar la remesa");
			
			parametro = getTipoPCAJG(request);

			validar = validarAccion(remesaAccionItem, request, remesaAccionItem.getDescripcion());
			
			if (remesaAccionItem.getAccion() == 8) {
				if(validar) {
					if (parametro.getValor().equals("3") || parametro.getValor().equals("6")) {
						 res = getFicheroXML(idInstitucion.toString(), String.valueOf(remesaAccionItem.getIdRemesa()));
					} else {
						res = getFichero(idInstitucion.toString(), String.valueOf(remesaAccionItem.getIdRemesa()));
					}
					
					if(res != null) {			
						//Comprobamos que no se haya enviado ya
						CajgRemesaestadosKey keyEstado = new CajgRemesaestadosKey();							
						keyEstado.setIdinstitucion(idInstitucion);
						keyEstado.setIdremesa((long) remesaAccionItem.getIdRemesa());			
						keyEstado.setIdestado(Short.valueOf("2"));			
						CajgRemesaestados cajgEnviada = cajgRemesaEstadosMapper.selectByPrimaryKey(keyEstado);
						
						if (cajgEnviada == null) {
							//Actualizamos estado remesa
							CajgRemesaestados cajgRemesa = new CajgRemesaestados();
							
							cajgRemesa.setIdremesa(Long.valueOf(remesaAccionItem.getIdRemesa()));
							cajgRemesa.setIdinstitucion(idInstitucion);
							cajgRemesa.setIdestado(Short.valueOf("2")); //ENVIADA
							cajgRemesa.setFecharemesa(new Date());
							cajgRemesa.setFechamodificacion(new Date());
							cajgRemesa.setUsumodificacion(usuarios.get(0).getUsumodificacion());
							
							LOGGER.debug(
									"insert() / cajgRemesaEstadosMapper.insert() -> Entrada añadir estado ENVIADA a la remesa");
							
							cajgRemesaEstadosMapper.insert(cajgRemesa);
							
							LOGGER.debug(
									"insert() / cajgRemesaEstadosMapper.insert() -> Entrada añadir estado ENVIADA a la remesa");
							
							// Obtenemos el numero y el estado de la remesa 
							CajgRemesaKey key = new CajgRemesaKey();							
							key.setIdinstitucion(idInstitucion);
							key.setIdremesa((long) remesaAccionItem.getIdRemesa());							
							CajgRemesa remesaSelected = cajgRemesaMapper.selectByPrimaryKey(key);
							
							//Buscamos los EJG asociados y les añadimos el estado "remitido a comision"
							RemesasItem remesaActual = new RemesasItem();
							remesaActual.setIdRemesa(remesaAccionItem.getIdRemesa());
							EJGRemesaDTO listaEJGRemesa = getEJGRemesa(remesaActual, request);
							
							if(listaEJGRemesa != null && listaEJGRemesa.getRemesasItem().size() > 0) {
															
								LOGGER.debug("Entrada FOR de EJGs asociados remesa para actulizar estado a REMITIDO COMISION");
								for(EJGRemesaItem ejg : listaEJGRemesa.getRemesasItem()) {
									
									ScsEstadoejg estado = new ScsEstadoejg();
									estado.setIdinstitucion(idInstitucion);
									estado.setIdtipoejg((short) ejg.getIdTipoEJG());
									estado.setAnio((short) ejg.getAnioEJG());
									estado.setNumero((long) ejg.getNumeroEJG());
									estado.setIdestadoejg(Short.parseShort("9"));	//id de estado correspondiente a Remitido comision.
									estado.setObservaciones("Nº Remesa: " + remesaSelected.getNumero());
									estado.setFechainicio(new Date());
									estado.setFechamodificacion(new Date());
									estado.setUsumodificacion(usuarios.get(0).getIdusuario());
									estado.setAutomatico("1");
									
									//asignamiento del id de estado por EJG.
									
									// obtenemos el maximo de idestadoporejg
									ScsEstadoejgExample example = new ScsEstadoejgExample();
	
									example.setOrderByClause("IDESTADOPOREJG DESC");
									example.createCriteria().andAnioEqualTo((short) ejg.getAnioEJG()).andIdinstitucionEqualTo(idInstitucion)
											.andIdtipoejgEqualTo((short) ejg.getIdTipoEJG()).andNumeroEqualTo((long) ejg.getNumeroEJG());
	
									List<ScsEstadoejg> listEjg = scsEstadoEjgextendsMapper.selectByExample(example);
	
									// damos el varlo al idestadoporejg + 1
									if (listEjg.size() > 0) {
										estado.setIdestadoporejg(listEjg.get(0).getIdestadoporejg() + 1);
									} else {
										estado.setIdestadoporejg(Long.parseLong("1"));
									}
									
									scsEstadoEjgextendsMapper.insertSelective(estado);
									
								}
								LOGGER.debug("Salida FOR de EJGs asociados remesa para actulizar estado a REMITIDO COMISION");
							}
							else {
								LOGGER.error("Error!!! No se han encontrado EJGs asociados a la remesa (id / numero): " 
										+ remesaAccionItem.getIdRemesa() + " / " + remesaSelected.getNumero());
							}
						}
					}
					
				}else {
					LOGGER.error("Error al descargar la remesa. No cumple los requisitos");
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(byteInput), null, HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		LOGGER.debug(
				"descargar() / ScsRemesasExtendsMapper.descargar() -> Salida a ScsRemesasExtendsMapper para descargar la remesa");
		
		return res;
	}
	
	public ResponseEntity<InputStreamResource> getFicheroXML(String idInstitucion, String idRemesa) throws SigaExceptions {
		
		byte[] buf = {};
		File file = new File(getRutaFicheroZIP(Integer.parseInt(idInstitucion), Integer.parseInt(idRemesa)));
		InputStream fileStream = new ByteArrayInputStream(buf);
		ResponseEntity<InputStreamResource> res = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
		headers.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
		
		try {
			fileStream = new FileInputStream(file);

			if(fileStream.available() > 0) {	
				headers.setContentLength(file.length());
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
			}else {
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), null, HttpStatus.NO_CONTENT);
			}
		} catch (IOException e) {
			res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), null, HttpStatus.NO_CONTENT);
		}
		
		return res;
	}
	
	public String getNombreRutaZIPconXMLs(int idInstitucion, int idRemesa) {
		return getDirXML(idInstitucion, idRemesa) + File.separator + idInstitucion + "_" + idRemesa;
	}
	
	public String getRutaFicheroZIP(int idInstitucion, int idRemesa) {
		return getNombreRutaZIPconXMLs(idInstitucion, idRemesa) + ".zip";
	}

	public String getDirXML(int idInstitucion, int idRemesa) {
		String pathFichero = getDirectorioFichero();
		return pathFichero + File.separator + idInstitucion  + File.separator + idRemesa + File.separator + "xml";
	}
	
	public ResponseEntity<InputStreamResource> getFichero(String idInstitucion, String idRemesa) throws SigaExceptions {
		File file = null;
		String rutaAlmacen = getDirectorioFichero();
		byte[] buf = {};
		InputStream fileStream = new ByteArrayInputStream(buf);
		ResponseEntity<InputStreamResource> res = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		
		rutaAlmacen += File.separator + idInstitucion;
		rutaAlmacen += File.separator + idRemesa;
		
		File dir = new File(rutaAlmacen);
		if (dir.exists()) {
			if (dir.listFiles() != null && dir.listFiles().length > 0) {
				int numFicheros = 0;
				for (int i = 0; i < dir.listFiles().length ; i++) {
					if (dir.listFiles()[i].isFile() && dir.listFiles()[i].getName().endsWith(".zip")){
						file = dir.listFiles()[i];
						numFicheros++;
					}
				}
									
				if (numFicheros > 1) {
					res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), null, HttpStatus.PARTIAL_CONTENT);
				}else {
					headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
					headers.setAccessControlExposeHeaders(Arrays.asList(HttpHeaders.CONTENT_DISPOSITION));
					
					try {
						fileStream = new FileInputStream(file);
						headers.setContentLength(file.length());
						res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers, HttpStatus.OK);
					} catch (IOException e) {
						res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), null, HttpStatus.NO_CONTENT);
					}
				}
				
			}else {
				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), null, HttpStatus.NO_CONTENT);
			}
		}
			
		return res;
	}
	
	private String getDirectorioFichero() {
		Date dateLog = new Date();
		LOGGER.debug(dateLog + " --> Inicio ScsRemesasExtendsMapper getDirectorioFichero");

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("cajg.directorioFisicoCAJG");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
		String pathCV = genPropertiesPath.get(0).getValor(); 
		
		StringBuffer directorioFichero = new StringBuffer(pathCV);

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
		genPropertiesExampleD.createCriteria().andParametroEqualTo("cajg.directorioCAJGJava");
		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

		LOGGER.debug(dateLog + " --> Fin ScsRemesasExtendsMapper getDirectorioFichero");
		return directorioFichero.toString();
	}

	public InsertResponseDTO validaEnviaExpedientes(Short idinstitucion, RemesaAccionItem remesaAccionItem, String tipoPCAJG, String mensajeValidando, HttpServletRequest request, Integer usuModificacion) {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		LOGGER.debug("Insertando un nuevo registro para la validación o envío de datos de una remesa de envío.");

		try {
			
			EcomOperacionTipoaccionExample ecomOperacionTipoAccionExample = new EcomOperacionTipoaccionExample();
			ecomOperacionTipoAccionExample.createCriteria().andIdtipoaccionremesaEqualTo(remesaAccionItem.getAccion()).andTipoPcajgEqualTo(tipoPCAJG);
			List<EcomOperacionTipoaccion> ecomOperacionTipoaccion = ecomOperacionTipoaccionMapper.selectByExample(ecomOperacionTipoAccionExample);
			
			EcomCola ecomCola = new EcomCola();
			if(ecomOperacionTipoaccion != null && ecomOperacionTipoaccion.size() > 0) {
				ecomCola.setIdinstitucion(idinstitucion);
				if(remesaAccionItem.isInformacionEconomica()) {
					if(remesaAccionItem.getAccion() == 1) {
						ecomCola.setIdoperacion(59); //VALIDAR REMESA PARA ALCALA Y REMESA INFORMACIÓN ECONÓMICA
					}else{
						ecomCola.setIdoperacion(48); //ENVIAR REMESA PARA ALCALA Y REMESA INFORMACIÓN ECONÓMICA
					}
				}else {
					ecomCola.setIdoperacion(ecomOperacionTipoaccion.get(0).getIdoperacion());
				}
				
			}
			
			
			CajgRemesa cajgRemesaKey = new CajgRemesa();
			cajgRemesaKey.setIdinstitucion(idinstitucion);
			cajgRemesaKey.setIdremesa(Long.valueOf(remesaAccionItem.getIdRemesa()));

			insertResponseDTO = deleteCajgRespuestasRemesa(cajgRemesaKey, idinstitucion);

			insertResponseDTO = insertaEstadoValidandoRemesa(cajgRemesaKey, mensajeValidando, request);

			Map<String, String> mapa = new HashMap<String, String>();
			mapa.put("IDREMESA", String.valueOf(remesaAccionItem.getIdRemesa()));
			
			if(remesaAccionItem.isInformacionEconomica()) {
				mapa.put("IDINSTITUCION", idinstitucion.toString());
			}

			insertResponseDTO = insertaColaRemesa(ecomCola, mapa, cajgRemesaKey);
						
			if(insertResponseDTO.getStatus().equals(SigaConstants.OK) && insertResponseDTO.getError().getCode() == 200
					&& remesaAccionItem.isInformacionEconomica()) {
				
				CajgRemesaestados cajgRemesa = new CajgRemesaestados();
				
				cajgRemesa.setIdremesa(Long.valueOf(remesaAccionItem.getIdRemesa()));
				cajgRemesa.setIdinstitucion(idinstitucion);
				cajgRemesa.setIdestado(Short.valueOf("1")); //GENERADA
				cajgRemesa.setFecharemesa(new Date());
				cajgRemesa.setFechamodificacion(new Date());
				cajgRemesa.setUsumodificacion(usuModificacion);
				
				cajgRemesaEstadosMapper.insert(cajgRemesa);
			}

		} catch (Exception e) {
			LOGGER.error("Error al solicitar envío o validación de expedientes. " + e.getStackTrace());
			error.setCode(400);
			error.setDescription("Se ha producido un error en la validación de los expedientes.");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setId(String.valueOf(remesaAccionItem.getIdRemesa()));
			insertResponseDTO.setError(error);
			throw e;
		}

		return insertResponseDTO;
	}

	@Transactional
	public InsertResponseDTO deleteCajgRespuestasRemesa(CajgRemesa cajgRemesa, short idInstitucion){
		// TODO Auto-generated method stub
		int borrados = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		try {
			if (cajgRemesa == null || cajgRemesa.getIdinstitucion() == null || cajgRemesa.getIdremesa() == null) {
				LOGGER.debug("Se ha recibido un cajgremesa no válido");
				throw new IllegalArgumentException("Se debe indicar el idinstitución e idremesa");
			}
			
			borrados = cajgRespuestaEjgremesaExtendsMapper.deleteConSelect(cajgRemesa.getIdremesa(), idInstitucion);
			LOGGER.debug(String.format("Se han eliminado %s registros de la tabla CajgRespuestaEjgremesa asociados a la remesa %s y la institución %s", borrados, 
					cajgRemesa.getIdremesa(), cajgRemesa.getIdinstitucion()));
			
		}catch(Exception e) {
			borrados = 0;
			error.setCode(400);
			error.setDescription("Se ha producido un error en el borrado de registros");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setError(error);
			LOGGER.error("Se ha producido un error en el borrado de registros. " + e.getStackTrace());
			throw e;
		}
		
		if(borrados != 0) {
			insertResponseDTO.setId(String.valueOf(cajgRemesa.getIdremesa()));
			error.setCode(200);
			error.setDescription("Se han borrado los registros correctamente");
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		return insertResponseDTO;
	}

	public InsertResponseDTO insertaEstadoValidandoRemesa(CajgRemesa cajgRemesa, String mensajeTraducido, HttpServletRequest request) {
		// TODO Auto-generated method stub
		int insertados = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		try {
			if (cajgRemesa == null || cajgRemesa.getIdinstitucion() == null || cajgRemesa.getIdremesa() == null) {
				LOGGER.warn("Se ha recibido un cajgremesa no válido");
				throw new IllegalArgumentException("Se debe indicar el idinstitución e idremesa");
			}

			CajgEjgremesaExample cajgEjgremesaExample = new CajgEjgremesaExample();

			cajgEjgremesaExample.createCriteria().andIdinstitucionEqualTo(cajgRemesa.getIdinstitucion()).andIdremesaEqualTo(cajgRemesa.getIdremesa());
			List<CajgEjgremesa> listaEJGsRemesa = cajgEjgremesaExtendsMapper.selectByExample(cajgEjgremesaExample);
			if (listaEJGsRemesa != null && listaEJGsRemesa.size() > 0) {
				for (CajgEjgremesa cajgEjgremesa : listaEJGsRemesa) {
					insertados += insertaRespuestaEJGRemesa(cajgEjgremesa.getIdejgremesa(), "-5", mensajeTraducido, request);
				}
			}
		}catch(Exception e) {
			insertados = 0;
			error.setCode(400);
			error.setDescription("Se ha producido un error en la inserción los de registros");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setError(error);
			LOGGER.error("Se ha producido un error la inserción los de registros. " + e.getStackTrace());
			throw e;
		}
		
		if(insertados != 0) {
			insertResponseDTO.setId(String.valueOf(cajgRemesa.getIdremesa()));
			error.setCode(200);
			error.setDescription("Se han insertado los registros correctamente");
			insertResponseDTO.setStatus(SigaConstants.OK);
		}
		
		return insertResponseDTO;
	}

	private InsertResponseDTO insertaEstadoEjgEnviandoPericles(CajgRemesa cajgRemesa, AdmUsuarios usuario) {
		// TODO Auto-generated method stub
		int insertados = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		try {
			if (cajgRemesa == null || cajgRemesa.getIdinstitucion() == null || cajgRemesa.getIdremesa() == null) {
				LOGGER.warn("Se ha recibido un cajgremesa no válido");
				throw new IllegalArgumentException("Se debe indicar el idinstitución e idremesa");
			}

			CajgEjgremesaExample cajgEjgremesaExample = new CajgEjgremesaExample();

			cajgEjgremesaExample.createCriteria().andIdinstitucionEqualTo(cajgRemesa.getIdinstitucion()).andIdremesaEqualTo(cajgRemesa.getIdremesa());
			List<CajgEjgremesa> listaEJGsRemesa = cajgEjgremesaExtendsMapper.selectByExample(cajgEjgremesaExample);
			if (listaEJGsRemesa != null && listaEJGsRemesa.size() > 0) {
				for (CajgEjgremesa cajgEjgremesa : listaEJGsRemesa) {
					ScsEstadoejgExample estadoejgExample = new ScsEstadoejgExample();
					estadoejgExample.createCriteria().andIdinstitucionEqualTo(cajgEjgremesa.getIdinstitucion())
							.andAnioEqualTo(cajgEjgremesa.getAnio()).andIdtipoejgEqualTo(cajgEjgremesa.getIdtipoejg())
							.andNumeroEqualTo(cajgEjgremesa.getNumero());
					estadoejgExample.setOrderByClause("IDESTADOPOREJG");

					List<ScsEstadoejg> estados = scsEstadoejgExtendsMapper.selectByExample(estadoejgExample);
					if (estados != null && !estados.isEmpty()) {
						ejgIntercambiosHelper.insertaCambioEstadoPericles(estados.get(estados.size() - 1), usuario);
					}

				}
			}
		}catch(Exception e) {
			insertados = 0;
			error.setCode(400);
			error.setDescription("Se ha producido un error en la inserción los de registros");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setError(error);
			LOGGER.error("Se ha producido un error la inserción los de registros. " + e.getStackTrace());
		}

		if(insertados != 0) {
			insertResponseDTO.setId(String.valueOf(cajgRemesa.getIdremesa()));
			error.setCode(200);
			error.setDescription("Se han insertado los registros correctamente");
			insertResponseDTO.setStatus(SigaConstants.OK);
		}

		return insertResponseDTO;
	}

	@Transactional
	public int insertaRespuestaEJGRemesa(Long idejgremesa, String codigoError, String descripcionErrorTraducido, HttpServletRequest request) {
		// TODO Auto-generated method stub;
		CajgRespuestaEjgremesa cajgRespuestaEjgremesa = new CajgRespuestaEjgremesa();
		cajgRespuestaEjgremesa.setIdejgremesa(idejgremesa);
		cajgRespuestaEjgremesa.setCodigo(codigoError);

		if (descripcionErrorTraducido != null && descripcionErrorTraducido.length() >= 1500) {// en la bdd esta definido como varchar de 1500
			descripcionErrorTraducido = descripcionErrorTraducido.substring(0, 1499);
		}

		cajgRespuestaEjgremesa.setDescripcion(descripcionErrorTraducido);
		cajgRespuestaEjgremesa.setAbreviatura(null);
		cajgRespuestaEjgremesa.setFecha(new Date());
		cajgRespuestaEjgremesa.setFechamodificacion(new Date());
		cajgRespuestaEjgremesa.setUsumodificacion(SigaConstants.USUMODIFICACION_0);
		cajgRespuestaEjgremesa.setIdtiporespuesta(Long.valueOf(1));

		return cajgRespuestaEjgremesaMapper.insert(cajgRespuestaEjgremesa);
	}

	public InsertResponseDTO insertaColaRemesa(EcomCola ecomCola, Map<String, String> mapa, CajgRemesaKey cajgRemesaKey) {

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		try {
			if (isEjectuandoRemesa(cajgRemesaKey.getIdinstitucion(), cajgRemesaKey.getIdremesa(), ecomCola.getIdoperacion())) {
				error.setCode(200);
				error.setDescription("La operación ya se está ejecutando para la remesa");
				insertResponseDTO.setStatus(SigaConstants.OK);
				insertResponseDTO.setError(error);
				LOGGER.error("La operación ya se está ejecutando para la remesa.");
			} else {
				insertResponseDTO = insertaColaConParametros(ecomCola, mapa);
			}
			
			insertResponseDTO.setId(String.valueOf(cajgRemesaKey.getIdremesa()));
		}catch(Exception e) {
			error.setCode(400);
			error.setDescription("Se ha producido un error en la inserción en la tabla ECOM_COLA O ECOM_COLA_PARAMETRO");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setError(error);
			LOGGER.error("Se ha producido un error en la inserción en la tabla ECOM_COLA O ECOM_COLA_PARAMETRO. " + e.getStackTrace());
			throw e;
		}
		
		return insertResponseDTO;
	}
	
	private boolean isEjectuandoRemesa(Short idinstitucion, Long idremesa, int operacionCompruebaEnEjecucion) {
		boolean ejecutandose = false;

		EcomColaParametrosExample ecomColaParametrosExample = new EcomColaParametrosExample();
		
		ecomColaParametrosExample.createCriteria().andClaveEqualTo("IDREMESA").andValorEqualTo(idremesa.toString());
		List<EcomColaParametros> listaEcomColaParametros = ecomColaParametrosMapper.selectByExample(ecomColaParametrosExample);
		
		if (listaEcomColaParametros != null && listaEcomColaParametros.size() > 0) {
			LOGGER.debug("Posibles candidatos para ver si la remesa ha sido ejecutada o se está ejecutando");
			List<Long> ids = new ArrayList<Long>();
			for (EcomColaParametros ecomColaParametros : listaEcomColaParametros) {
				ids.add(ecomColaParametros.getIdecomcola());
			}
			
			List<Short> listaEstados = new ArrayList<Short>();
			listaEstados.add(ECOM_ESTADOSCOLA.INICIAL.getId());
			listaEstados.add(ECOM_ESTADOSCOLA.EJECUTANDOSE.getId());
			listaEstados.add(ECOM_ESTADOSCOLA.REINTENTANDO.getId());
			
			EcomColaExample ecomColaExample = new EcomColaExample();			
			ecomColaExample.createCriteria().andIdinstitucionEqualTo(idinstitucion).andIdoperacionEqualTo(operacionCompruebaEnEjecucion).andIdestadocolaIn(listaEstados).andIdecomcolaIn(ids);			
			long count = ecomColaMapper.countByExample(ecomColaExample);
			if (count > 0) {
				ejecutandose = true;
			}else {
				EcomOperacion operacion = ecomOperacionMapper.selectByPrimaryKey(operacionCompruebaEnEjecucion);
				ecomColaExample = new EcomColaExample();
				ecomColaExample.createCriteria().andIdinstitucionEqualTo(idinstitucion).andIdoperacionEqualTo(operacionCompruebaEnEjecucion).andIdestadocolaEqualTo(ECOM_ESTADOSCOLA.ERROR.getId()).andReintentoLessThan(operacion.getMaxreintentos()).andIdecomcolaIn(ids);
				count = ecomColaMapper.countByExample(ecomColaExample);
				if (count > 0) 
					ejecutandose = true;
				
			}
		}
		
		LOGGER.debug("¿La remesa del colegio " + idinstitucion + " con idRemesa " + idremesa + " está en ejecución? = '" + ejecutandose + "'");
		
		return ejecutandose;		
	}

	@Transactional
	public InsertResponseDTO insertaColaConParametros(EcomCola ecomCola, Map<String, String> mapa) {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int insertado = 0;
		
		try {
			insertado = insert(ecomCola);
			if (mapa != null) {
				Iterator<String> it = mapa.keySet().iterator();
				while(it.hasNext()) {
					EcomColaParametros ecomColaParametros = new EcomColaParametros();
					String clave = it.next();
					String valor = mapa.get(clave);
					ecomColaParametros.setIdecomcola(ecomCola.getIdecomcola());
					ecomColaParametros.setClave(clave);
					ecomColaParametros.setValor(valor);
					insertado = ecomColaParametrosMapper.insert(ecomColaParametros);
					if (insertado != 1) {
						throw new BusinessException("Error al insertar los parámetros de la cola.");
					}
				}
			}
		}catch(Exception e) {
			insertado = 0;
			error.setCode(400);
			error.setDescription("Se ha producido un error en la inserción en la tabla ECOM_COLA O ECOM_COLA_PARAMETRO");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setError(error);
			LOGGER.error("Se ha producido un error al insertar en ECOM_COLA_PARAMETRO. " + e.getStackTrace());
			throw e;
		}
		
		if(insertado != 0) {
			error.setCode(200);
			error.setDescription("La validación se ha programado correctamente. Vuelva a consultar la remesa pasado unos instantes.");
			insertResponseDTO.setStatus(SigaConstants.OK);
			insertResponseDTO.setError(error);
		}
		
		return insertResponseDTO;
		
	}

	@Transactional
	public int insert(EcomCola ecomCola) {
		try {		
			if (ecomCola.getIdoperacion() == null) {
				throw new IllegalArgumentException("El identificador de operación no puede ser nulo al insertar en ecom_cola");
			}
			ecomCola.setIdestadocola(ECOM_ESTADOSCOLA.INICIAL.getId());
			ecomCola.setReintento(0);
			ecomCola.setFechacreacion(new Date());
			ecomCola.setFechamodificacion(new Date());
			ecomCola.setUsumodificacion(SigaConstants.USUMODIFICACION_0);
			return ecomColaMapper.insert(ecomCola);
		} catch (Exception e) {
			LOGGER.error("Se ha producido un error al insertar en la cola. " + e.getStackTrace());
			throw e;
		}
		
	}
	
	@Override
	public InputStreamResource descargarLogErrores(RemesaAccionItem remesaAccionItem, HttpServletRequest request){
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		byte[] buf = {};
		ByteArrayInputStream byteInput = new ByteArrayInputStream(buf);
			
		CajgEjgremesaExample cajgEjgremesaExample = new CajgEjgremesaExample();
		cajgEjgremesaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdremesaEqualTo(Long.valueOf(remesaAccionItem.getIdRemesa()));
		
		List<CajgEjgremesa> listaCajgEjgremesas = cajgEjgremesaExtendsMapper.selectByExample(cajgEjgremesaExample);
		
		if (listaCajgEjgremesas != null && listaCajgEjgremesas.size() > 0) {
			List<Long> listIdejgremesa = new ArrayList<Long>();
			
			for (CajgEjgremesa cajgEjgremesa : listaCajgEjgremesas) {
				listIdejgremesa.add(cajgEjgremesa.getIdejgremesa());
			}
			
			CajgRespuestaEjgremesaExample cajgRespuestaEjgremesaExample = new CajgRespuestaEjgremesaExample();
			cajgRespuestaEjgremesaExample.createCriteria().andIdejgremesaIn(listIdejgremesa);
		
			List<CajgRespuestaEjgremesa> respuestaEjgRemesa = cajgRespuestaEjgremesaMapper.selectByExample(cajgRespuestaEjgremesaExample);
			
			if(respuestaEjgRemesa != null && respuestaEjgRemesa.size() > 0) {
				byteInput = crearExcel(respuestaEjgRemesa, listaCajgEjgremesas, remesaAccionItem.getIdRemesa());
			}
		}

		return new InputStreamResource(byteInput);
		
	}
	
	private ByteArrayInputStream crearExcel(List<CajgRespuestaEjgremesa> respuestaEjgRemesa, List<CajgEjgremesa> listaCajgEjgremesas, int idRemesa) {
		List<String> cabeceraExcel = Arrays.asList("IDENTIFICADOR DEL EJG", "DESCRIPCION");
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("ErrorRemesa"+idRemesa);

		    // Header
		    Row headerRow = sheet.createRow(0);
	
		    for (int col = 0; col < cabeceraExcel.size(); col++) {
		    	Cell cell = headerRow.createCell(col);
		        cell.setCellValue(cabeceraExcel.get(col));
		    }
	
		    int rowIdx = 1;
		    String identificadorEJG = null;
		    for (CajgRespuestaEjgremesa respuestaEJGREMESA : respuestaEjgRemesa) {
		    	Row row = sheet.createRow(rowIdx++);
	
		    	for(CajgEjgremesa listaCaj: listaCajgEjgremesas) {
		    		if(listaCaj.getIdejgremesa().equals(respuestaEJGREMESA.getIdejgremesa())) {
		    			identificadorEJG = listaCaj.getIdinstitucion()+"-"+listaCaj.getIdtipoejg()+"-"+
						listaCaj.getAnio()+"-"+listaCaj.getNumero();
		    		}
		    	}
		    	
		        row.createCell(0).setCellValue(identificadorEJG);
		        row.createCell(1).setCellValue(respuestaEJGREMESA.getDescripcion());
		    }
	
		    workbook.write(out);
		    return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("Error al crear el archivo Excel: " + e.getMessage());
	    }
	}

}