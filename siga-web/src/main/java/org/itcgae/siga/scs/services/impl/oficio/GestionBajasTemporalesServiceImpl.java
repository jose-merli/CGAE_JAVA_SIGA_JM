package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	}
	
	@Override
	public InsertResponseDTO nuevaBajaTemporal(ColegiadoItem colegiadoItem, HttpServletRequest request) {
		LOGGER.info("nuevaBajaTemporal() ->  Entrada al servicio para insertar bajas temporales");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		Integer idTurnoNuevo = 0;
		Integer idOrdenacionNuevo = 0;
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"nuevaBajaTemporal() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);
			
			LOGGER.info(
					"nuevaBajaTemporal() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() >= 0) {
				try {
			        ColegiadoDTO colegiadoDTO = busquedaColegiadosServiceImpl.searchColegiado(colegiadoItem, request);

						LOGGER.info(
								"nuevaBajaTemporal() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");
						
						BajasTemporalesItem bajasTemporalesItem=new BajasTemporalesItem();
//						bajasTemporalesItem = scsBajasTemporalesExtendsMapper.nuevaBajaTemporal(colegiadoDTO.getColegiadoItem().get(0));
						CenBajastemporales cenBajasTemporales = new CenBajastemporales();
						cenBajasTemporales.setIdpersona(Long.parseLong(colegiadoDTO.getColegiadoItem().get(0).getIdPersona()));
						cenBajasTemporales.setIdinstitucion(Short.parseShort(colegiadoDTO.getColegiadoItem().get(0).getIdInstitucion()));
						cenBajasTemporales.setFechaalta(new Date());
						cenBajasTemporales.setFechadesde(new Date());
						cenBajasTemporales.setFechahasta(new Date());
						cenBajasTemporales.setFechamodificacion(new Date());
						cenBajasTemporales.setTipo("P");
						cenBajasTemporales.setUsumodificacion(usuarios.get(0).getIdusuario());
						cenBajasTemporales.setFechaestado(new Date());
						cenBajasTemporales.setFechabt(new Date());
						cenBajasTemporales.setEliminado(0);
						
//						CenBajastemporalesMapper cenBajasTemporalesMapper;
						
						response =cenBajastemporalesMapper.insert(cenBajasTemporales);
						LOGGER.info(
								"nuevaBajaTemporal() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para insertar los modulos seleccionados");


				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
					insertResponseDTO.setError(error);
					return insertResponseDTO;
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if (error.getDescription() == null) {
				error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(String.valueOf(idTurnoNuevo));
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("nuevaBajaTemporal() -> Salida del servicio para insertar bajas temporsale");

		return insertResponseDTO;
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
					for(int x=0;x<bajasTemporalesItem.size();x++) {
						CenBajastemporales cenBajasTemporales = new CenBajastemporales();
						cenBajasTemporales.setIdinstitucion(Short.parseShort(bajasTemporalesItem.get(x).getIdinstitucion()));
						cenBajasTemporales.setIdpersona(Long.parseLong(bajasTemporalesItem.get(x).getIdpersona()));
						cenBajasTemporales.setFechabt(bajasTemporalesItem.get(x).getFechabt());
						
						if(bajasTemporalesItem.get(x).getValidado().equals("Anular")) {
							cenBajasTemporales.setValidado("3");
						}else if(bajasTemporalesItem.get(x).getValidado().equals("Denegar")) {
							cenBajasTemporales.setValidado("0");
						}else {
							cenBajasTemporales.setValidado("1");
						}
						
						response =cenBajastemporalesMapper.updateByPrimaryKeySelective(cenBajasTemporales);
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
					error.setDescription(
							"Se han modificado las bajas temporales excepto algunos que tiene las mismas características");

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

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteBaja() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for(int x=0;x<bajasTemporalesItem.size();x++) {
						CenBajastemporales cenBajasTemporales = new CenBajastemporales();
						cenBajasTemporales.setIdinstitucion(Short.parseShort(bajasTemporalesItem.get(x).getIdinstitucion()));
						cenBajasTemporales.setIdpersona(Long.parseLong(bajasTemporalesItem.get(x).getIdpersona()));
						cenBajasTemporales.setFechabt(bajasTemporalesItem.get(x).getFechabt());
						cenBajasTemporales.setEliminado(1);
						
						response = cenBajastemporalesMapper.updateByPrimaryKeySelective(cenBajasTemporales);
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
					error.setDescription(
							"Se han eliminado las bajas temporales excepto algunos que tiene las mismas características");

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
}