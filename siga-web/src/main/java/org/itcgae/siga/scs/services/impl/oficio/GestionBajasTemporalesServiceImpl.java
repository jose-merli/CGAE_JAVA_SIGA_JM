package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.CenBajastemporalesExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaExample;
import org.itcgae.siga.db.entities.ScsInscripcionturno;
import org.itcgae.siga.db.entities.ScsInscripcionturnoExample;
import org.itcgae.siga.db.mappers.CenBajastemporalesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBajasTemporalesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionguardiaExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IGestionBajasTemporalesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionBajasTemporalesServiceImpl implements IGestionBajasTemporalesService {

	private Logger LOGGER = Logger.getLogger(GestionBajasTemporalesServiceImpl.class);

	@Autowired
	private ScsBajasTemporalesExtendsMapper scsBajasTemporalesExtendsMapper;
	
    @Autowired
    private CenBajastemporalesMapper cenBajastemporalesMapper;


	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionesTurnoExtendsMapper;
	
	@Autowired
	private ScsInscripcionguardiaExtendsMapper scsInscripcionguardiaExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;


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
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		BajasTemporalesDTO bajasTemporalesDTO = new BajasTemporalesDTO();
		List<BajasTemporalesItem> bajasTemporalesItems = null;
		try {

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"busquedaBajasTemporales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"busquedaBajasTemporales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				
				Integer tamMaximo = getTamanoMaximo(idInstitucion);
				
				LOGGER.info(
						"busquedaBajasTemporales() / scsBajasTemporalesExtendsMapper.busquedaBajasTemporales() -> Entrada a scsBajasTemporalesExtendsMapper para obtener las bajas temporales");
				
						bajasTemporalesItems = scsBajasTemporalesExtendsMapper.busquedaBajasTemporales(bajasTemporalesItem, idInstitucion, tamMaximo);	
				LOGGER.info(
						"busquedaBajasTemporales() / scsBajasTemporalesExtendsMapper.busquedaBajasTemporales() -> Salida a scsBajasTemporalesExtendsMapper para obtener las bajas temporales");

				if((bajasTemporalesItems != null) && (bajasTemporalesItems.size()) >= 200) {
					error.setCode(200);
					error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
					bajasTemporalesDTO.setError(error);
				}
				
				if (bajasTemporalesItems != null) {
					bajasTemporalesDTO.setBajasTemporalesItems(bajasTemporalesItems);
				}
			}
		}
		
		LOGGER.info("busquedaBajasTemporales() -> Salida del servicio para obtener las bajas temporales");
		
		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
			LOGGER.error(e.getMessage());
			return null;
		}
		
		return bajasTemporalesDTO;
	}
	
	@Override
	public UpdateResponseDTO updateEstado(List<Object> bajasTemporalesItem, HttpServletRequest request) {
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
					String nombres[];
					SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					for(Object bti: bajasTemporalesItem) {
						CenBajastemporales record = new CenBajastemporales();
						java.util.LinkedHashMap bj = (java.util.LinkedHashMap)bti;
						Set entrySet = bj.entrySet();
						// Obtain an Iterator for the entries Set
						Iterator it = entrySet.iterator();
						while(it.hasNext()) {
							nombres = it.next().toString().split("=");
							
							if(nombres[0].equals("validado") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setValidado(nombres[1]);
							}
//							if(nombres[0].equals("fechabt") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
//								Date fecha = format2.parse(nombres[1]);
//								record.setFechabt(fecha);
//							}
							if(nombres[0].equals("idpersona") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setIdpersona(Long.valueOf(nombres[1]));
							}
							if(nombres[0].equals("fechadesde") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								Date fecha = format2.parse(nombres[1]);
								record.setFechadesde(fecha);
							}
							if(nombres[0].equals("fechahasta") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setFechahasta(format2.parse(nombres[1]));
							}
						}
						
						record.setIdinstitucion(idInstitucion); 
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setFechamodificacion(new Date());
						
//						response = cenBajastemporalesMapper.updateByPrimaryKeySelective(record);
						
						CenBajastemporalesExample cenBajastemporalesExample = new CenBajastemporalesExample();
						cenBajastemporalesExample.createCriteria().andIdinstitucionEqualTo(record.getIdinstitucion())
							.andIdpersonaEqualTo(record.getIdpersona()).andFechadesdeEqualTo(record.getFechadesde())
							.andFechahastaEqualTo(record.getFechahasta());
						
						response = cenBajastemporalesMapper.updateByExampleSelective(record, cenBajastemporalesExample);
						
						//response = scsBajasTemporalesExtendsMapper.updateBajaTemporal(bti,usuarios.get(0).getIdusuario());
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
	public UpdateResponseDTO deleteBaja(List<Object> bajasTemporalesItem, HttpServletRequest request) {
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
					SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					for(Object bti: bajasTemporalesItem) {
						CenBajastemporales record = new CenBajastemporales();
						java.util.LinkedHashMap bj = (java.util.LinkedHashMap)bti;
						Set entrySet = bj.entrySet();
						// Obtain an Iterator for the entries Set
						Iterator it = entrySet.iterator();
						while(it.hasNext()) {
							nombres = it.next().toString().split("=");
							if(nombres[0].equals("fechadesde") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								Date fecha = format2.parse(nombres[1]);
								record.setFechadesde(fecha);
							}
							if(nombres[0].equals("fechahasta") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								Date fecha = format2.parse(nombres[1]);
								record.setFechahasta(fecha);
							}
							if(nombres[0].equals("idpersona") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setIdpersona(Long.valueOf(nombres[1]));
							}
						}
						record.setEliminado((short) 1);
						record.setIdinstitucion(idInstitucion);
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setFechamodificacion(new Date());
						
						CenBajastemporalesExample bajaExample = new CenBajastemporalesExample();
						bajaExample.createCriteria().andIdpersonaEqualTo(record.getIdpersona()).andIdinstitucionEqualTo(record.getIdinstitucion())
							.andFechadesdeEqualTo(record.getFechadesde()).andFechahastaEqualTo(record.getFechahasta());
						
						List<CenBajastemporales> lis = cenBajastemporalesMapper.selectByExample(bajaExample);
						response = cenBajastemporalesMapper.updateByExampleSelective(record, bajaExample);
						LOGGER.info("Eliminados " + response + " registros ");
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
		LOGGER.info("saveBajaTemporal() ->  Entrada al servicio para guardar bajas temporales");

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

			LOGGER.info("saveBajaTemporal() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"saveBajaTemporal() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					String nombres[];
					SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					for(Object bti: bajasTemporalesItem) {
						//BajasTemporalesItem bjtmp = new BajasTemporalesItem();
						CenBajastemporales record = new CenBajastemporales();
						java.util.LinkedHashMap bj = (java.util.LinkedHashMap)bti;
						Set entrySet = bj.entrySet();
						
						// Obtain an Iterator for the entries Set
						Iterator it = entrySet.iterator();
						while(it.hasNext()) {
							nombres = it.next().toString().split("=");
							//if(nombres[0].equals("ncolegiado")) {
							//	bjtmp.setNcolegiado(nombres[1]);
							//}
							//if(nombres[0].equals("nombre")) {
							//	bjtmp.setNombre(nombres[1]);
							//}
							if(nombres[0].equals("tipo") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setTipo(nombres[1]);
							}
							if(nombres[0].equals("descripcion")) {
								if(nombres[1]== null || nombres[1].equals("null") || nombres[1].isEmpty()) {
									nombres[1] = "";
								}
								record.setDescripcion(nombres[1]);
							}
							if(nombres[0].equals("fechadesde") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								Date fecha = format2.parse(nombres[1]);
								record.setFechadesde(fecha);
							}
							if(nombres[0].equals("fechahasta") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setFechahasta(format2.parse(nombres[1]));
							}
							if(nombres[0].equals("fechaalta") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setFechaalta(format2.parse(nombres[1]));
							}
							if(nombres[0].equals("validado") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setValidado(nombres[1]);
							}
//							if(nombres[0].equals("fechabt") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
//								Date fecha = format2.parse(nombres[1]);
//								record.setFechabt(fecha);
//							}
							if(nombres[0].equals("idpersona") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								record.setIdpersona(Long.valueOf(nombres[1]));
							}
							
						}
						
						record.setIdinstitucion(idInstitucion); 
						record.setUsumodificacion(usuarios.get(0).getIdusuario());
						record.setFechamodificacion(new Date());
						
//						response = cenBajastemporalesMapper.updateByPrimaryKeySelective(record);
						
						CenBajastemporalesExample cenBajastemporalesExample = new CenBajastemporalesExample();
						cenBajastemporalesExample.createCriteria().andIdinstitucionEqualTo(record.getIdinstitucion())
							.andIdpersonaEqualTo(record.getIdpersona()).andFechadesdeEqualTo(record.getFechadesde())
							.andFechahastaEqualTo(record.getFechahasta());
						
						response = cenBajastemporalesMapper.updateByExampleSelective(record, cenBajastemporalesExample);
							
						//response = scsBajasTemporalesExtendsMapper.saveBajaTemporal(bjtmp,usuarios.get(0).getIdusuario());
					}
				}catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
				
				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha guardado la baja temporal");
					updateResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han guardado las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha guardado la baja temporal correctamente");
				}

				updateResponseDTO.setError(error);

				LOGGER.info("saveBajaTemporal() -> Salida del servicio para guardar bajas temporales");

			}

		}

		return updateResponseDTO;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO nuevaBajaTemporal(List<Object> bajasTemporalesItem, HttpServletRequest request) {
		LOGGER.info("nuevaBajaTemporal() ->  Entrada al servicio para crear nuevas bajas temporales");

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

			LOGGER.info("nuevaBajaTemporal() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"nuevaBajaTemporal() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					String nombres[];
					SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					for(int j=0; j < bajasTemporalesItem.size(); j++ ) {
						LOGGER.info("Tamaño bajas temporales: " + bajasTemporalesItem.size());
						Object bti = bajasTemporalesItem.get(j);
						BajasTemporalesItem bjtmp = new BajasTemporalesItem();
						java.util.LinkedHashMap bj = (java.util.LinkedHashMap)bti;
						Set entrySet = bj.entrySet();
						// Obtain an Iterator for the entries Set
						Iterator it = entrySet.iterator();
						while(it.hasNext()) {
							nombres = it.next().toString().split("=");
							if(nombres.length > 1 && nombres[0].equals("ncolegiado") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setNcolegiado(nombres[1]);
							}
							if(nombres.length > 1 && nombres[0].equals("nombre") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setNombre(nombres[1]);
							}
							if(nombres.length > 1 && nombres[0].equals("tipo") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setTipo(nombres[1]);
							}
							if(nombres.length > 1 && nombres[0].equals("descripcion") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setDescripcion(nombres[1]);
							}
							if(nombres.length > 1 && nombres[0].equals("fechadesde") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								Date fecha = format2.parse(nombres[1]);
								bjtmp.setFechadesde(fecha);
							}
							if(nombres.length > 1 && nombres[0].equals("fechahasta") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setFechahasta(format2.parse(nombres[1]));
							}
							if(nombres.length > 1 &&nombres[0].equals("fechaalta") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setFechaalta(format2.parse(format2.format(new Date())));
							}
							if(nombres.length > 1 && nombres[0].equals("validado") && nombres[1]!= null && !nombres[1].equals("null") && !nombres[1].isEmpty()) {
								bjtmp.setValidado(nombres[1]);
							}
							
						}
						bjtmp.setFechabt(new Date());
						bjtmp.setIdinstitucion(String.valueOf(idInstitucion));
						String idPersona = scsBajasTemporalesExtendsMapper.persona(bjtmp);
						bjtmp.setIdpersona(idPersona);
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
						String strDateDesde = dateFormat.format(bjtmp.getFechadesde());  
						String strDateHasta = dateFormat.format(bjtmp.getFechahasta());
						LocalDate dateBefore = LocalDate.parse(strDateDesde);
						LocalDate dateAfter = LocalDate.parse(strDateHasta);
						
						long fechasBT = ChronoUnit.DAYS.between(dateBefore, dateAfter);
						//if(!scsBajasTemporalesExtendsMapper.busquedaBajasTemporalesValidadas(bjtmp).isEmpty()) {
							if(fechasBT == 0) {
								bjtmp.setFechabt(bjtmp.getFechadesde());
								response = scsBajasTemporalesExtendsMapper.nuevaBaja(bjtmp,usuarios.get(0).getIdusuario());
							}else if(fechasBT == 1) {
								bjtmp.setFechabt(bjtmp.getFechadesde());
								response = scsBajasTemporalesExtendsMapper.nuevaBaja(bjtmp,usuarios.get(0).getIdusuario());
								bjtmp.setFechabt(bjtmp.getFechahasta());
								response = scsBajasTemporalesExtendsMapper.nuevaBaja(bjtmp,usuarios.get(0).getIdusuario());
							}else if(fechasBT > 1) {
								bjtmp.setFechabt(bjtmp.getFechadesde());
								Date dateAux = bjtmp.getFechadesde();
								response = scsBajasTemporalesExtendsMapper.nuevaBaja(bjtmp,usuarios.get(0).getIdusuario());
								for (int i = 0; i < fechasBT; i++) {
									dateAux = this.diaSiguienteDate(dateAux);
									//String strAux = dateFormat.format(bjtmp.getFechabt());  
									bjtmp.setFechabt(dateAux);
									response = scsBajasTemporalesExtendsMapper.nuevaBaja(bjtmp,usuarios.get(0).getIdusuario());
								}
							}
							//eliminarTurnosGuardias(idInstitucion, Long.valueOf(idPersona),request);
						//}
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
				
				if (response == 0 && error.getDescription() == null) {
					error.setCode(400);
					error.setDescription("No se ha creado la baja temporal");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else if (response == 1 && existentes != 0) {
					error.setCode(200);
					error.setDescription("Se han creado las bajas temporales excepto algunas");
				} else if (error.getCode() == null) {
					error.setCode(200);
					error.setDescription("Se ha creado la baja temporal correctamente");
				}

				insertResponseDTO.setError(error);

				LOGGER.info("nuevaBajaTemporal() -> Salida del servicio para crear bajas temporales");

			}

		}

		return insertResponseDTO;
	}

	private Date diaSiguienteDate(Date dateDesde) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDesde);
		cal.add(Calendar.DAY_OF_YEAR, 1); 
		return cal.getTime();
	}
	
	private void eliminarTurnosGuardias(Short idInstitucion, Long idPersona, HttpServletRequest request) throws Exception {
		ScsInscripcionturnoExample inscripcionesExample = new ScsInscripcionturnoExample();
		inscripcionesExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(idPersona);		
		List<ScsInscripcionturno> inscripciones = scsInscripcionesTurnoExtendsMapper.selectByExample(inscripcionesExample);
		if(inscripciones.size() > 0) {
			for(ScsInscripcionturno inscripcion : inscripciones) {
				ScsInscripcionguardiaExample guardiasExample = new ScsInscripcionguardiaExample();
					guardiasExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(idPersona)
							.andIdturnoEqualTo(inscripcion.getIdturno());
				List<ScsInscripcionguardia> guardias = scsInscripcionguardiaExtendsMapper.selectByExample(guardiasExample);
				if(guardias.size() > 0) {
					//quitar el if y meter el método en un catch, si peta que lance la excepción
					for(ScsInscripcionguardia guardia : guardias) {
						ScsInscripcionguardia record = new ScsInscripcionguardia();
						record.setIdinstitucion(new Short(idInstitucion));
						record.setIdturno(guardia.getIdturno());
						record.setIdpersona(idPersona);
						record.setFechasuscripcion(guardia.getFechabaja());
						record.setIdguardia(guardia.getIdguardia());
						record.setFechabaja(new Date());
						record.setFechamodificacion(new Date());
						record.setUsumodificacion(0);
						
						//SIGARNV-2143@DTT.JAMARTIN@30/07/2021@INICIO
						try {
							scsInscripcionguardiaExtendsMapper.updateByPrimaryKeySelective(record);
						} catch (Exception sqle) {
							LOGGER.error("Error al eliminar las inscripciones a guardias: ", sqle);
						}
						//SIGARNV-2143@DTT.JAMARTIN@30/07/2021@FIN 
					}
				}
				ScsInscripcionturno record = new ScsInscripcionturno();
				record.setIdinstitucion(inscripcion.getIdinstitucion());
				record.setIdturno(inscripcion.getIdturno());
				record.setIdpersona(inscripcion.getIdpersona());
				record.setFechasolicitud(inscripcion.getFechasolicitud());
				record.setFechabaja(new Date());
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(0);
				
				//SIGARNV-2143@DTT.JAMARTIN@30/07/2021@INICIO
				try {
					scsInscripcionesTurnoExtendsMapper.updateByPrimaryKeySelective(record);
				} catch (Exception sqle) {
					LOGGER.error("Error al eliminar las inscripciones a turnos: ", sqle);
				}
				//SIGARNV-2143@DTT.JAMARTIN@30/07/2021@FIN
			}
		}
		
	}
	
	private Integer getTamanoMaximo(Short idinstitucion) {
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
	    		.andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idinstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    List<GenParametros> tamMax = genParametrosMapper.selectByExample(genParametrosExample);
	    LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        Integer tamMaximo = null;
		if (tamMax != null) {
            tamMaximo  = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
		return tamMaximo;
	}
	
}