package org.itcgae.siga.scs.services.impl.ejg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesas;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesasDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaDTO;
import org.itcgae.siga.DTOs.scs.EJGRemesaItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaDTO;
import org.itcgae.siga.DTOs.scs.EstadoRemesaItem;
import org.itcgae.siga.DTOs.scs.RemesaAccionItem;
import org.itcgae.siga.DTOs.scs.RemesaBusquedaDTO;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasItem2;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_ESTADOSCOLA;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CajgEjgremesa;
import org.itcgae.siga.db.entities.CajgEjgremesaExample;
import org.itcgae.siga.db.entities.CajgRemesa;
import org.itcgae.siga.db.entities.CajgRemesaExample;
import org.itcgae.siga.db.entities.CajgRemesaKey;
import org.itcgae.siga.db.entities.CajgRemesaestados;
import org.itcgae.siga.db.entities.CajgRemesaestadosExample;
import org.itcgae.siga.db.entities.CajgRespuestaEjgremesa;
import org.itcgae.siga.db.entities.CajgRespuestaEjgremesaExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.EcomColaParametrosExample;
import org.itcgae.siga.db.entities.EcomOperacion;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccionExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgExample;
import org.itcgae.siga.db.mappers.CajgRemesaestadosMapper;
import org.itcgae.siga.db.mappers.CajgRespuestaEjgremesaMapper;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EcomOperacionMapper;
import org.itcgae.siga.db.mappers.EcomOperacionTipoaccionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgEjgremesaExtendsMapper;
import org.itcgae.siga.db.services.cajg.mappers.CajgRemesaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEstadoejgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.ejg.IBusquedaRemesas;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.implementation.auxiliary.AuxiliaryType;

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
	private CajgRespuestaEjgremesaMapper cajgRespuestaEjgremesaMapper;
	
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

			LOGGER.info("Id remesa -> " + remesasBusquedaItem.getIdRemesa() + " | idLenguaje -> "
					+ usuarios.get(0).getIdlenguaje() + " | fecha generacion -> "
					+ remesasBusquedaItem.getFechaGeneracionDesde());

			remesasItems = scsRemesasExtendsMapper.buscarRemesas(remesasBusquedaItem, idInstitucion,
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
					for(; (nRegistro.length() - ceros.length()) < 5;) {
						ceros += "0";
					}
					nRegistro = ceros + nRegistro;
				}
				
				remesasItems.get(i).setnRegistro(nRegistro);
			}

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
			LOGGER.info(
					"borrarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"borrarRemesas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"borrarRemesas() / ScsRemesasExtendsMapper.borrarRemesas() -> Entrada a ScsRemesasExtendsMapper para eliminar las remesas");

			try {

				if (remesasBusquedaItem != null && remesasBusquedaItem.size() > 0) {

					LOGGER.info("remesaBusquedaItem -> " + remesasBusquedaItem.get(0).getIdRemesa());

					for (RemesasBusquedaItem remesas : remesasBusquedaItem) {

						LOGGER.info("Entra al foreach de la remesas");

						CajgRemesaKey remesaKey = new CajgRemesaKey();

						remesaKey.setIdinstitucion(idInstitucion);
						remesaKey.setIdremesa(Long.valueOf(remesas.getIdRemesa()));

						remesasItems = cajgRemesaExtendsMapper.selectByPrimaryKey(remesaKey);

						List<RemesasItem2> remesaEstado = scsRemesasExtendsMapper.isEstadoRemesaIniciada(remesas,
								idInstitucion);

						// Se comprueba que la remesa esté en estado "Iniciado"
						if (remesaEstado != null && remesaEstado.size() > 0) {

							if (Integer.parseInt(remesaEstado.get(0).getIdRemesa()) == remesas.getIdRemesa()) {

								LOGGER.info("Se comprueba que la remesa con id -> " + remesas.getIdRemesa()
										+ " está inciada");

								// Buscamos si la remesa tiene EJG asociados
								CajgEjgremesaExample example = new CajgEjgremesaExample();
								example.createCriteria().andIdremesaEqualTo(Long.valueOf(remesas.getIdRemesa()))
										.andIdinstitucionremesaEqualTo(idInstitucion);
								List<CajgEjgremesa> ejgRemesas = cajgEjgremesaExtendsMapper.selectByExample(example);

								LOGGER.info("Obtenemos los EJG asociados a la remesa");

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

												LOGGER.info("Actualizamos el EJG con año/numero: "
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

										LOGGER.info("Borramos la relación entre el EJG y la remesa");

										// Borramos la relación entre el ejg y la remesa

										CajgEjgremesaExample ejgRemesaExample = new CajgEjgremesaExample();
										ejgRemesaExample.createCriteria()
												.andIdejgremesaEqualTo(cajgEjgRemesa.getIdejgremesa());
										response = cajgEjgremesaExtendsMapper.deleteByExample(ejgRemesaExample);

									}

								}

								LOGGER.info("Borramos la relacion entre la remesa y el estado de la misma");

								// Borramos la relacion de la remesa y su estado
								CajgRemesaestadosExample remesaEstadoKey = new CajgRemesaestadosExample();
								remesaEstadoKey.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdremesaEqualTo(Long.valueOf(remesaEstado.get(0).getIdRemesa()));

								response = cajgRemesaEstadosMapper.deleteByExample(remesaEstadoKey);

							}

							LOGGER.info("Borramos la remesa con id: " + remesas.getIdRemesa());

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
				LOGGER.info("Se ha producido un error en BBDD contacte con su administrador");
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
				LOGGER.info("No se ha(n) borrado la(s) remesa(s) con Nº de Registro: " + nRegistro
						+ ", porque no estan en estado 'Iniciado'");
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

			estadoRemesaItem = scsRemesasExtendsMapper.listadoEstadoRemesa(remesasBusquedaItem, idInstitucion, usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"listadoEstadoRemesa() / ScsRemesasExtendsMapper.listadoEstadoRemesa() -> Salida a ScsRemesasExtendsMapper para obtener los estados de la remesa");

			if (estadoRemesaItem != null) {
				estadoRemesaDTO.setEstadoRemesaItem(estadoRemesaItem);
			}

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los estado de la remesa");
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
			LOGGER.info(
					"getUltimoRegitroRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"getUltimoRegitroRemesa() / admContadorExtendsMapper.getUltimoRegitroRemesa() -> Entrada a AdmContadorExtendsMapper para obtener el ultimo registro de las remesas");
			AdmContadorKey key = new AdmContadorKey();

			key.setIdinstitucion(idInstitucion);
			key.setIdcontador("REMESA");

			contador = admContadorExtendsMapper.selectByPrimaryKey(key);

			LOGGER.info(
					"getUltimoRegitroRemesa() / admContadorExtendsMapper.getUltimoRegitroRemesa() -> Salida a AdmContadorExtendsMapper para obtener el ultimo registro de las remesas");

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return contador;
	}

	@Override
	@Transactional
	public UpdateResponseDTO guardarRemesa(RemesasItem remesasItem, HttpServletRequest request) {
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

			LOGGER.info(
					"guardarRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (remesasItem.getIdRemesa() == 0) {
				try {

					LOGGER.info(
							"guardarRemesa() / cajgRemesaExtendsMapper.insert() -> Entrada a CajgRemesaExtendsMapper para insertar una remesa");

					CajgRemesa remesa = new CajgRemesa();

					RemesasItem rem = scsRemesasExtendsMapper.getMaxIdRemesa(idInstitucion);

					idRemesa = rem.getIdRemesa();

					remesa.setIdinstitucion(idInstitucion);
					remesa.setIdremesa(Long.valueOf(rem.getIdRemesa()));
					remesa.setDescripcion(remesasItem.getDescripcion());
					remesa.setNumero(String.valueOf(remesasItem.getNumero()));
					remesa.setFechamodificacion(new Date());

					response = cajgRemesaExtendsMapper.insert(remesa);

					LOGGER.info(
							"guardarRemesa() / cajgRemesaExtendsMapper.insert() -> Salida de CajgRemesaExtendsMapper para insertar una remesa");

					if (response == 1) {

						LOGGER.info(
								"guardarRemesa() / admContadorExtendsMapper.selectByPrimaryKey() -> Entrada de AdmContadorExtendsMapper para obtener el contador de REMESA");

						AdmContador contador = getUltimoRegitroRemesa(request);

						LOGGER.info(
								"guardarRemesa() / admContadorExtendsMapper.selectByPrimaryKey() -> Salida de AdmContadorExtendsMapper para obtener el contador de REMESA");

						if (contador != null) {

							LOGGER.info(
									"guardarRemesa() / admContadorExtendsMapper.updateByPrimaryKeySelective() -> Entrada de AdmContadorExtendsMapper para incrementar en 1 el contador de REMESA");

							response = 0;

							contador.setContador(contador.getContador() + 1);

							response = admContadorExtendsMapper.updateByPrimaryKeySelective(contador);

							LOGGER.info(
									"guardarRemesa() / admContadorExtendsMapper.updateByPrimaryKeySelective() -> Salida de AdmContadorExtendsMapper para incrementar en 1 el contador de REMESA");

							if (response == 1) {
								LOGGER.info(
										"guardarRemesa() / cajgRemesaEstadosMapper.insert() -> Entrada de CajgRemesaEstadosMapper para insertar un estado de una remesas");

								CajgRemesaestados remesaEstado = new CajgRemesaestados();

								remesaEstado.setIdinstitucion(idInstitucion);
								remesaEstado.setIdremesa(Long.valueOf(rem.getIdRemesa()));
								remesaEstado.setIdestado(Short.valueOf("0"));
								remesaEstado.setFecharemesa(new Date());
								remesaEstado.setFechamodificacion(new Date());

								response = cajgRemesaEstadosMapper.insert(remesaEstado);

								LOGGER.info(
										"guardarRemesa() / cajgRemesaEstadosMapper.insert() -> Salida de CajgRemesaEstadosMapper para insertar un estado de una remesas");
							}
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

					LOGGER.info(
							"guardarRemesa() / cajgRemesaExtendsMapper.selectByPrimaryKey(example) -> Entrada a CajgRemesaExtendsMapper para buscar la remesa a actualizar");

					CajgRemesaKey remesa = new CajgRemesaKey();

					remesa.setIdinstitucion(idInstitucion);
					remesa.setIdremesa(Long.valueOf(remesasItem.getIdRemesa()));

					CajgRemesa remesaBD = cajgRemesaExtendsMapper.selectByPrimaryKey(remesa);

					LOGGER.info(
							"guardarRemesa() / cajgRemesaExtendsMapper.selectByPrimaryKey(example) -> Salida de CajgRemesaExtendsMapper para buscar la remesa a actualizar");

					if (remesaBD != null) {

						LOGGER.info(
								"guardarRemesa() / cajgRemesaExtendsMapper.updateByPrimaryKeySelective(example) -> Entrada a CajgRemesaExtendsMapper para actulizar la remesa");

						remesaBD.setDescripcion(remesasItem.getDescripcion());

						response = cajgRemesaExtendsMapper.updateByPrimaryKeySelective(remesaBD);

						LOGGER.info(
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

		LOGGER.info("guardarRemesa() -> Salida del servicio para insertar/actualizar remesa");

		return updateResponseDTO;
	}

	@Override
	public EJGRemesaDTO getEJGRemesa(RemesasItem remesasItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		EJGRemesaDTO ejgRemesaDTO = new EJGRemesaDTO();
		List<EJGRemesaItem> ejgRemesaItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEJGRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getEJGRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"getEJGRemesa() / ScsRemesasExtendsMapper.getEJGRemesa() -> Entrada a ScsRemesasExtendsMapper para obtener los ejg asociados a la remesa");

			LOGGER.info("Id remesa -> " + remesasItem.getIdRemesa());

			ejgRemesaItems = scsRemesasExtendsMapper.getEJGRemesa(remesasItem, idInstitucion);

			for (int i = 0; i < ejgRemesaItems.size(); i++) {
				String incidencias = ejgRemesaItems.get(i).getNumIncidencias() + "/"
						+ ejgRemesaItems.get(i).getIncidenciasAntesEnvio() + "/"
						+ ejgRemesaItems.get(i).getIncidenciasDespuesEnvio() + "/"
						+ ejgRemesaItems.get(i).getNuevaRemesa();
				ejgRemesaItems.get(i).setIncidencias(incidencias);
			}

			LOGGER.info(
					"getEJGRemesa() / ScsRemesasExtendsMapper.getEJGRemesa() -> Salida a ScsRemesasExtendsMapper para obtener los ejg asociados a la remesa");

			if (ejgRemesaItems != null) {
				ejgRemesaDTO.setEJGRemesaItem(ejgRemesaItems);
			}
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los ejg asociados a la remesa");

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
			LOGGER.info(
					"borrarExpedientesRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"borrarExpedientesRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"borrarExpedientesRemesa() / ScsRemesasExtendsMapper.borrarExpedientesRemesa() -> Entrada a ScsRemesasExtendsMapper para eliminar los expedientes de la remesa");

			try {

				if (ejgRemesaItem != null && ejgRemesaItem.size() > 0) {

					LOGGER.info("remesaBusquedaItem -> " + ejgRemesaItem.get(0).getIdentificadorEJG());

					for (EJGRemesaItem ejg : ejgRemesaItem) {

						LOGGER.info("Entra al foreach de los EJG's");

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

								LOGGER.info("Actualizamos el EJG con año/numero: " + scsEstadoejg.getAnio() + "/"
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

						LOGGER.info("Borramos la relación entre el EJG y la remesa");

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
				LOGGER.info("Se ha producido un error en BBDD contacte con su administrador");
				throw e;
			}

			if (error.getCode() == null) {
				error.setCode(200);
				error.setDescription("Se han borrado los expedientes correctamente");
				deleteResponseDTO.setStatus(SigaConstants.OK);
				LOGGER.info("Se han borrado los expedientes correctamente");
			}

			deleteResponseDTO.setError(error);

			LOGGER.info(
					"borrarExpedientesRemesa() / ScsRemesasExtendsMapper.borrarExpedientesRemesa() -> Salida a ScsRemesasExtendsMapper para eliminar los expedientes de la remesa");

		}
		LOGGER.info("getLabel() -> Salida del servicio para eliminar los expedientes");

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
			LOGGER.info(
					"getTipoPCAJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"getTipoPCAJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"getTipoPCAJG() / genParametrosMapper.selectByPrimaryKey() -> Entrada a GenParametrosMapper para obtener el tipo de PCAJG de la institucion logeada");
			GenParametrosKey key = new GenParametrosKey();

			key.setIdinstitucion(idInstitucion);
			key.setModulo("SCS");
			key.setParametro("PCAJG_TIPO");

			parametro = genParametrosMapper.selectByPrimaryKey(key);

			LOGGER.info(
					"getTipoPCAJG() / genParametrosMapper.selectByPrimaryKey() -> Entrada a GenParametrosMapper para obtener el tipo de PCAJG de la institucion logeada"
							+ parametro.getValor());

			LOGGER.info(
					"getTipoPCAJG() / genParametrosMapper.selectByPrimaryKey() -> Salida a GenParametrosMapper para obtener el tipo de PCAJG de la institucion logeada");

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener el tipo de PCAJG de la institucion logeada");
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
			LOGGER.info(
					"validaOperacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"validaOperacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"validaOperacion() / ScsRemesasExtendsMapper.checkAcciones() -> Entrada a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");

			parametro = getTipoPCAJG(request);

			checkAccionesRemesasItems = scsRemesasExtendsMapper.getAcciones(remesasItem, idInstitucion,
					usuarios.get(0).getIdlenguaje(), parametro.getValor());

			LOGGER.info(
					"validaOperacion() / ScsRemesasExtendsMapper.checkAcciones() -> Salida a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");

			if (checkAccionesRemesasItems != null) {
				checkAccionesRemesasDTO.setCheckAccionesRemesas(checkAccionesRemesasItems);
			}

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return checkAccionesRemesasDTO;
	}

	@Override
	public InsertResponseDTO ejecutaOperacionRemesa(RemesaAccionItem remesaAccionItem, HttpServletRequest request) {
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
			LOGGER.info(
					"ejecutaOperacionRemesa() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"ejecutaOperacionRemesa() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"ejecutaOperacionRemesa() / ScsRemesasExtendsMapper.ejecutaOperacionRemesa() -> Entrada a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");
			
			parametro = getTipoPCAJG(request);
			
			GenRecursosKey grKey = new GenRecursosKey();
			
			grKey.setIdrecurso("scs.mensaje.validando");
			grKey.setIdlenguaje(usuarios.get(0).getIdlenguaje());
			
			GenRecursos gr = genRecursosMapper.selectByPrimaryKey(grKey);

			if (remesaAccionItem.getAccion() == 1 || remesaAccionItem.getAccion() == 2) {
				validar = validarAccion(remesaAccionItem, request, remesaAccionItem.getDescripcion());
				
				if(validar) {
					insertResponseDTO = validaEnviaExpedientes(idInstitucion, Long.valueOf(remesaAccionItem.getIdRemesa()), remesaAccionItem.getAccion(), parametro.getValor(), gr.getDescripcion(), request);
				}else {
					LOGGER.error("Error al validar la remesa. No cumple los requisitos");
					error.setCode(200);
					error.setDescription("Error al validar la remesa. No cumple los requisitos.");
					insertResponseDTO.setStatus(SigaConstants.OK);
					insertResponseDTO.setError(error);
				}
			}

			LOGGER.info(
					"ejecutaOperacionRemesa() / ScsRemesasExtendsMapper.checkAcciones() -> Salida a ScsRemesasExtendsMapper para obtener las acciones posibles para la institucion");

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return insertResponseDTO;
	}

	private boolean validarAccion(RemesaAccionItem remesaAccionItem, HttpServletRequest request, String accion) {
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

	private InsertResponseDTO validaEnviaExpedientes(Short idinstitucion, Long idremesa , int accion, String tipoPCAJG, String mensajeValidando, HttpServletRequest request) {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		LOGGER.info("Insertando un nuevo registro para la validación o envío de datos de una remesa de envío.");

		try {
			
			EcomOperacionTipoaccionExample ecomOperacionTipoAccionExample = new EcomOperacionTipoaccionExample();
			ecomOperacionTipoAccionExample.createCriteria().andIdtipoaccionremesaEqualTo(accion).andTipoPcajgEqualTo(tipoPCAJG);
			List<EcomOperacionTipoaccion> ecomOperacionTipoaccion = ecomOperacionTipoaccionMapper.selectByExample(ecomOperacionTipoAccionExample);
			
			EcomCola ecomCola = new EcomCola();
			if(ecomOperacionTipoaccion != null && ecomOperacionTipoaccion.size() > 0) {
				ecomCola.setIdinstitucion(idinstitucion);
				ecomCola.setIdoperacion(ecomOperacionTipoaccion.get(0).getIdoperacion());
			}
			
			
			CajgRemesa cajgRemesaKey = new CajgRemesa();
			cajgRemesaKey.setIdinstitucion(idinstitucion);
			cajgRemesaKey.setIdremesa(idremesa);

			insertResponseDTO = deleteCajgRespuestasRemesa(cajgRemesaKey);

			insertResponseDTO = insertaEstadoValidandoRemesa(cajgRemesaKey, mensajeValidando, request);

			Map<String, String> mapa = new HashMap<String, String>();
			mapa.put("IDREMESA", idremesa.toString());

			insertResponseDTO = insertaColaRemesa(ecomCola, mapa, cajgRemesaKey);

		} catch (Exception e) {
			LOGGER.error("Error al solicitar envío o validación de expedientes. " + e.getStackTrace());
			error.setCode(400);
			error.setDescription("Se ha producido un error en la validación de los expedientes.");
			insertResponseDTO.setStatus(SigaConstants.KO);
			insertResponseDTO.setError(error);
			throw e;
		}

		return insertResponseDTO;
	}

	@Transactional
	public InsertResponseDTO deleteCajgRespuestasRemesa(CajgRemesa cajgRemesa){
		// TODO Auto-generated method stub
		int borrados = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		try {
			if (cajgRemesa == null || cajgRemesa.getIdinstitucion() == null || cajgRemesa.getIdremesa() == null) {
				LOGGER.info("Se ha recibido un cajgremesa no válido");
				throw new IllegalArgumentException("Se debe indicar el idinstitución e idremesa");
			}
			
			CajgEjgremesaExample cajgEjgremesaExample = new CajgEjgremesaExample();
			cajgEjgremesaExample.createCriteria().andIdinstitucionEqualTo(cajgRemesa.getIdinstitucion()).andIdremesaEqualTo(cajgRemesa.getIdremesa());
			
			List<CajgEjgremesa> listaCajgEjgremesas = cajgEjgremesaExtendsMapper.selectByExample(cajgEjgremesaExample);
			
			if (listaCajgEjgremesas != null && listaCajgEjgremesas.size() > 0) {
				List<Long> listIdejgremesa = new ArrayList<Long>();
				
				for (CajgEjgremesa cajgEjgremesa : listaCajgEjgremesas) {
					listIdejgremesa.add(cajgEjgremesa.getIdejgremesa());
				}
				
				CajgRespuestaEjgremesaExample cajgRespuestaEjgremesaExample = new CajgRespuestaEjgremesaExample();
				cajgRespuestaEjgremesaExample.createCriteria().andIdejgremesaIn(listIdejgremesa);
				borrados = cajgRespuestaEjgremesaMapper.deleteByExample(cajgRespuestaEjgremesaExample);
				LOGGER.info(String.format("Se han eliminado %s registros de la tabla CajgRespuestaEjgremesa asociados a la remesa %s y la institución %s", borrados, 
						cajgRemesa.getIdremesa(), cajgRemesa.getIdinstitucion()));
			}
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
	
	public int getMaxIdRespuesta(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesasItem idRespuesta = new RemesasItem();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getMaxIdRespuesta() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"comboPonenteComision() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			LOGGER.info(
					"getMaxIdRespuesta() / scsRemesasExtendsMapper.getMaxIdRespuesta() -> Entrada a scsRemesasExtendsMapper para obtener el ultimo registro de idRespuesta");

			idRespuesta = scsRemesasExtendsMapper.getMaxIdRespuesta();

			LOGGER.info(
					"getMaxIdRespuesta() / scsRemesasExtendsMapper.getMaxIdRespuesta() -> Salida a scsRemesasExtendsMapper para obtener el ultimo registro de idRespuesta");

		}
		LOGGER.info("getLabel() -> Salida del servicio para obtener los tipos de estado de las remesas");
		return idRespuesta.getIdRemesa();
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
		cajgRespuestaEjgremesa.setIdrespuesta(BigDecimal.valueOf(getMaxIdRespuesta(request)));

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
			LOGGER.info("Posibles candidatos para ver si la remesa ha sido ejecutada o se está ejecutando");
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
		
		LOGGER.info("¿La remesa del colegio " + idinstitucion + " con idRemesa " + idremesa + " está en ejecución? = '" + ejecutandose + "'");
		
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
	
	public InputStreamResource descargarLogErrores(RemesaAccionItem remesaAccionItem, HttpServletRequest request){
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		byte[] buf = {};
		ByteArrayInputStream byteInput = new ByteArrayInputStream(buf);
		
		//if(validarAccion(remesaAccionItem, request, "Descargar Log")) {
			
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
		//}

		return new InputStreamResource(byteInput);
		
	}
	
	private ByteArrayInputStream crearExcel(List<CajgRespuestaEjgremesa> respuestaEjgRemesa, List<CajgEjgremesa> listaCajgEjgremesas, int idRemesa) {
		List<String> cabeceraExcel = Arrays.asList("IDENTIFICADOR DEL EJG", "DESCRIPCION");
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("ErrorRemesa"+idRemesa);

		    // Header
		    Row headerRow = sheet.createRow(0);
	
		    for (int col = 0; col < cabeceraExcel.size(); col++) {
		    	Cell cell = headerRow.createCell(col);
		        cell.setCellValue(cabeceraExcel.get(col));
		    }
	
		    int rowIdx = 1;
		    String identificadorEJG;
		    for (CajgRespuestaEjgremesa respuestaEJGREMESA : respuestaEjgRemesa) {
		    	Row row = sheet.createRow(rowIdx++);
	
		    	identificadorEJG = listaCajgEjgremesas.get(rowIdx-2).getIdinstitucion()+"-"+listaCajgEjgremesas.get(rowIdx-2).getIdtipoejg()+"-"+
				        listaCajgEjgremesas.get(rowIdx-2).getAnio()+"-"+listaCajgEjgremesas.get(rowIdx-2).getNumero();
		    	
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