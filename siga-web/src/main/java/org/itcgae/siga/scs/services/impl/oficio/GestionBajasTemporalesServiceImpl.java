package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.cen.services.impl.BusquedaColegiadosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.mappers.CenBajastemporalesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBajasTemporalesExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IGestionBajasTemporalesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionBajasTemporalesServiceImpl implements IGestionBajasTemporalesService {

	private Logger LOGGER = Logger.getLogger(GestionBajasTemporalesServiceImpl.class);

	@Autowired
	private ScsBajasTemporalesExtendsMapper scsBajasTemporalesExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private BusquedaColegiadosServiceImpl busquedaColegiadosServiceImpl;
	
	@Autowired
	private CenBajastemporalesMapper cenBajastemporalesMapper;
	
	@Override
	public ComboDTO comboEstado(HttpServletRequest request) {

		LOGGER.info("comboEstado() -> Entrada al servicio para combo comboEstado");
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboEstado() / scsBajasTemporalesExtendsMapper.comboEstado() -> Entrada a scsBajasTemporalesExtendsMapper para obtener combo Estado");

				List<ComboItem> comboItems = scsBajasTemporalesExtendsMapper.comboEstado();

				LOGGER.info(
						"comboEstado() / scsBajasTemporalesExtendsMapper.comboEstado() -> Salida e scsBajasTemporalesExtendsMapper para obtener combo comboEstado");

				comboDTO.setCombooItems(comboItems);
			}

			LOGGER.info("comboEstado() -> Salida del servicio para obtener combo comboEstado");
		}
		return comboDTO;
	}
	
	@Override
	public BajasTemporalesDTO busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado

		Error error = new Error();
		
		try {
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		BajasTemporalesDTO bajasTemporalesDTO = new BajasTemporalesDTO();
		List<BajasTemporalesItem> bajasTemporalesItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
				String fechahasta = "";
				String fechadesde = "";
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
				if (bajasTemporalesItem.getFechadesde() != null) {
					Date fechad = bajasTemporalesItem.getFechadesde();

					fechadesde = dateFormat.format(fechad);
					if (bajasTemporalesItem.getFechahasta() != null) {
						Date fechah = bajasTemporalesItem.getFechahasta();
						fechahasta = dateFormat.format(fechah);

					}
				}
						bajasTemporalesItems = scsBajasTemporalesExtendsMapper.busquedaBajasTemporales(bajasTemporalesItem, idInstitucion, fechadesde, fechahasta);

				
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (bajasTemporalesItems != null) {
					bajasTemporalesDTO.setBajasTemporalesItems(bajasTemporalesItems);
				}
			}

		}
		
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		
		return bajasTemporalesDTO;
		
		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			LOGGER.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public UpdateResponseDTO updateEstado(List<BajasTemporalesItem> bajasTemporalesItem, HttpServletRequest request) {
		LOGGER.info("updateEstado() ->  Entrada al servicio para modificar nuevas bajas temporales");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateEstado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateEstado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for(BajasTemporalesItem bti: bajasTemporalesItem) {
						response = scsBajasTemporalesExtendsMapper.updateBajaTemporal(bti);
					}
				
				}catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha creado la baja temporal");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han modificado las bajas temporales excepto algunos que tiene las mismas características");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha modificado la baja temporal correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("updateEstado() -> Salida del servicio para modificar bajas temporales");

			}

		}

		return updateResponseDTO;
	}
	@Override
	public UpdateResponseDTO deleteBaja(List<BajasTemporalesItem> bajasTemporalesItem, HttpServletRequest request) {
		LOGGER.info("deleteBaja() ->  Entrada al servicio para eliminar bajas temporales");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					
					for(BajasTemporalesItem bti: bajasTemporalesItem) {
						bti.setEliminado("1");
						response = scsBajasTemporalesExtendsMapper.eliminarBaja(bti);
					}
				}catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
				
				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha eliminado la baja temporal");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han elimiando las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha eliminado la baja temporal correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("deleteBaja() -> Salida del servicio para eliminar bajas temporales");

			}

		}

		return updateResponseDTO;
	}
	
	@Override
	public UpdateResponseDTO saveBajaTemporal(List<Object> bajasTemporalesItem, HttpServletRequest request) {
		LOGGER.info("deleteBaja() ->  Entrada al servicio para eliminar bajas temporales");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					String nombres[];
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy");
					for(Object bti: bajasTemporalesItem) {
						BajasTemporalesItem bjtmp = new BajasTemporalesItem();
						java.util.LinkedHashMap bj = (java.util.LinkedHashMap)bti;
						Set entrySet = bj.entrySet();
						// Obtain an Iterator for the entries Set
						Iterator it = entrySet.iterator();
						while(it.hasNext()) {
							nombres = it.next().toString().split("=");
							if(nombres[0].equals("ncolegiado")) {
								bjtmp.setNcolegiado(nombres[1]);
							}
							if(nombres[0].equals("nombre")) {
								bjtmp.setNombre(nombres[1]);
							}
							if(nombres[0].equals("tipo")) {
								bjtmp.setTipo(nombres[1]);
							}
							if(nombres[0].equals("descripcion")) {
								bjtmp.setDescripcion(nombres[1]);
							}
							if(nombres[0].equals("fechadesde")) {
								Date fecha = format.parse(nombres[1]);
								bjtmp.setFechadesde(fecha);
							}
							if(nombres[0].equals("fechahasta")) {
								bjtmp.setFechahasta(format.parse(nombres[1]));
							}
							if(nombres[0].equals("fechaalta")) {
								bjtmp.setFechaalta(format.parse(nombres[1]));
							}
							if(nombres[0].equals("validado")) {
								bjtmp.setValidado(nombres[1]);
							}
							if(nombres[0].equals("fechabt")) {
								bjtmp.setFechabt(format2.parse(nombres[1]));
							}
							if(nombres[0].equals("idpersona")) {
								bjtmp.setIdpersona(nombres[1]);
							}
							
							bjtmp.setIdinstitucion(String.valueOf(idInstitucion));
						}
							
						response = scsBajasTemporalesExtendsMapper.saveBajaTemporal(bjtmp);
					}
				}catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
				
				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha eliminado la baja temporal");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han elimiando las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha eliminado la baja temporal correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("deleteBaja() -> Salida del servicio para eliminar bajas temporales");

			}

		}

		return updateResponseDTO;
	}
	
	@Override
	public InsertResponseDTO nuevaBajaTemporal(List<Object> bajasTemporalesItem, HttpServletRequest request) {
		LOGGER.info("deleteBaja() ->  Entrada al servicio para eliminar bajas temporales");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int existentes = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					String nombres[];
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format2=new SimpleDateFormat("dd/mm/yyyy");
					for(Object bti: bajasTemporalesItem) {
						BajasTemporalesItem bjtmp = new BajasTemporalesItem();
						java.util.LinkedHashMap bj = (java.util.LinkedHashMap)bti;
						Set entrySet = bj.entrySet();
						// Obtain an Iterator for the entries Set
						Iterator it = entrySet.iterator();
						while(it.hasNext()) {
							nombres = it.next().toString().split("=");
							if(nombres[0].equals("ncolegiado")) {
								bjtmp.setNcolegiado(nombres[1]);
							}
							if(nombres[0].equals("nombre")) {
								bjtmp.setNombre(nombres[1]);
							}
							if(nombres[0].equals("tipo")) {
								bjtmp.setTipo(nombres[1]);
							}
							if(nombres[0].equals("descripcion")) {
								bjtmp.setDescripcion(nombres[1]);
							}
							if(nombres[0].equals("fechadesde")) {
								Date fecha = format.parse(nombres[1]);
								bjtmp.setFechadesde(fecha);
							}
							if(nombres[0].equals("fechahasta")) {
								bjtmp.setFechahasta(format.parse(nombres[1]));
							}
							if(nombres[0].equals("fechaalta")) {
								bjtmp.setFechaalta(format.parse(nombres[1]));
							}
							if(nombres[0].equals("validado")) {
								bjtmp.setValidado(nombres[1]);
							}
							
							if(nombres[0].equals("fechabt")) {
								bjtmp.setFechabt(format.parse(nombres[1]));
							}
							
							bjtmp.setIdinstitucion(String.valueOf(idInstitucion));
						}
						
						bjtmp.setIdpersona(scsBajasTemporalesExtendsMapper.persona(bjtmp));
						
						response = scsBajasTemporalesExtendsMapper.nuevaBaja(bjtmp,usuarios.get(0).getIdusuario());
					}
				}catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
				
				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha eliminado la baja temporal");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han elimiando las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha eliminado la baja temporal correctamente");
				}

				insertResponseDTO.setError(error);

				LOGGER.info("deleteBaja() -> Salida del servicio para eliminar bajas temporales");

			}

		}

		return insertResponseDTO;
	}
	
}