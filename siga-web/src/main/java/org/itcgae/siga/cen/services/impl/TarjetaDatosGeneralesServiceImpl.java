package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaItem;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.SociedadCreateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.ITarjetaDatosGeneralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class TarjetaDatosGeneralesServiceImpl implements ITarjetaDatosGeneralesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosGeneralesServiceImpl.class);
	
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private CenClienteMapper cenClienteMapper;
	
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired 
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;
	
	@Autowired
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;
	
	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
		
	@Autowired
	private IAuditoriaCenHistoricoService auditoriaCenHistoricoService;
		
	
	@Override
	public ComboItem loadPhotography(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info(
				"loadPhotography() -> Entrada al servicio para cargar de una ruta la fotografía de una persona jurídica");

		ComboItem comboItem = new ComboItem();
		List<GenProperties> genProperties = new ArrayList<GenProperties>();
		String pathFinal = "";
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// obtener el directorio para la fotografia de la persona
		LOGGER.debug("loadPhotography() -> Obtener el directorio para la fotografia de la persona jurídica");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener el directorio para la fotografia de la persona jurídica");
		genProperties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener el directorio para la fotografia de la persona jurídica");

		if (null != genProperties && genProperties.size() > 0) {
			String path = genProperties.get(0).getValor() + "/" + String.valueOf(idInstitucion) + "/";
			pathFinal = pathFinal.concat(path);

			// obtener el nombre del archivo de la fotografía
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.loadPhotography() -> Entrada a cenPersonaExtendsMapper para obtener el nombre del archivo de la fotografía");
			//comboItem = cenPersonaExtendsMapper.loadPhotography(etiquetaUpdateDTO.getIdPersona(), String.valueOf(idInstitucion));
			comboItem = cenPersonaExtendsMapper.loadPhotography(etiquetaUpdateDTO.getIdPersona(), idInstitucion.toString());
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.loadPhotography() -> Salida de cenPersonaExtendsMapper para obtener el nombre del archivo de la fotografía");

			if (null != comboItem && null != comboItem.getLabel()) {
				pathFinal = pathFinal.concat(comboItem.getLabel());
				LOGGER.info(
						"loadPhotography() -> Se obtiene fotografia de la persona jurídica del path:  " + pathFinal);

				// Se coge la imagen de la persona juridica
				File file = new File(pathFinal);
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					IOUtils.copy(fis, response.getOutputStream());
		
				} catch (FileNotFoundException e) {
					LOGGER.error("No se ha encontrado el fichero", e);
					comboItem = null;
		
				} catch (IOException e1) {
					LOGGER.error("No se han podido escribir los datos binarios de la imagen en la respuesta HttpServletResponse", e1);
					comboItem = null;
				} finally {
					if (null != fis)
						try {
							fis.close();
						} catch (IOException e) {
							LOGGER.error("No se ha cerrado el archivo correctamente", e);
							comboItem = null;
						}
				}
				
			} else {
				LOGGER.warn("loadPhotography() / cenPersonaExtendsMapper.loadPhotography() -> . No se ha podido obtener el nombre del archivo de la fotografía: " + pathFinal);
				comboItem = null;

			}

		} else {
			LOGGER.warn("loadPhotography() / genPropertiesMapper.selectByExample() -> No se ha podido obtener el directorio para la fotografia de la persona jurídica");
			comboItem = null;
		}
		
		LOGGER.info("loadPhotography() -> Salida del servicio para cargar de una ruta la fotografía de una persona jurídica");
		
		return comboItem;
	}

	@Override
	public UpdateResponseDTO uploadPhotography(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {
		
		LOGGER.info(
				"uploadPhotography() -> Entrada al servicio para guardar una fotografía de una persona jurídica");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		String idPersona = request.getParameter("idPersona");
		int response = 0;
		
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		// obtener path para almacenar las fotografias
		LOGGER.debug("uploadPhotography() -> Obtener path para almacenar las fotografias");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener directorio de la fotografía");
		List<GenProperties> properties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener directorio de la fotografía");
		
		if (null != properties && properties.size() > 0) {
			String pathImagenes = properties.get(0).getValor() + "/" + String.valueOf(idInstitucion) + "/";

			// Coger archivo del request
			LOGGER.debug("uploadPhotography() -> Coger fotografía del request");
			Iterator<String> itr = request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			String fileName = file.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

			// comprobar extension de la fotografia
			LOGGER.debug("uploadPhotography() -> Comprobar extension de la fotografia");
			if (extension == null || extension.trim().equals("")
					|| (!extension.trim().toUpperCase().equals(".JPG") && !extension.trim().toUpperCase().equals(".GIF")
							&& !extension.trim().toUpperCase().equals(".PNG")
							&& !extension.trim().toUpperCase().equals(".JPEG"))) {

				try {
					throw new SigaExceptions("messages.error.imagen.tipoNoCorrecto");
				} catch (SigaExceptions e) {
					e.printStackTrace();
				}
			}

			// Crear nombre del archivo a guardar
			LOGGER.debug("uploadPhotography() -> Crear nombre de la fotografía a guardar");
			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona));
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> Entrada a cenPersonaExtendsMapper para nifcif de una persona");
			List<CenPersona> cenPersonas = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> Salida de cenPersonaExtendsMapper para nifcif de una persona");

			if (null != cenPersonas && cenPersonas.size() > 0) {
				String nifCif = cenPersonas.get(0).getNifcif();

				fileName = nifCif + "_"  + String.valueOf(idInstitucion) + "_" + fileName;

				BufferedOutputStream stream = null;
				// Guardar el archivo
				LOGGER.debug("uploadPhotography() -> Guardar la fotografía");
				try {
					File aux = new File(pathImagenes);
					// creo directorio si no existe
					aux.mkdirs();
					File serverFile = new File(pathImagenes, fileName);
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(file.getBytes());

				} catch (FileNotFoundException e) {
					LOGGER.error("uploadPhotography() -> Error al buscar la fotografía de la persona jurídica en el directorio indicado", e);
				} catch (IOException ioe) {
					LOGGER.error("uploadPhotography() -> Error al guardar la fotografía de la persona jurídica en el directorio indicado",
							ioe);
				} finally {
					// close the stream
					LOGGER.debug("uploadPhotography() -> Cierre del stream de la fotografía de la persona jurídica");
					stream.close();
				}
				
				
				// actualizar nombre de la fotografia en base de datos
				LOGGER.debug("uploadPhotography() -> actualizar nombre de la fotografia en base de datos");
				CenClienteExample cenClienteExample = new CenClienteExample();
				cenClienteExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona)).andIdinstitucionEqualTo(idInstitucion);
				CenCliente cenCliente = new CenCliente();
				cenCliente.setFotografia(fileName);
				LOGGER.info(
						"loadPhotography() / cenClienteMapper.updateByExample() -> Entrada a cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
				response = cenClienteMapper.updateByExampleSelective(cenCliente, cenClienteExample);
				LOGGER.info(
						"loadPhotography() / cenClienteMapper.updateByExample() -> Salida de cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
				if(response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.warn(
							"loadPhotography() / cenClienteMapper.updateByExample() -> " + updateResponseDTO.getStatus() + " .Nombre de la fotografía de una persona jurídica actualizado correctamente");
	
				}
				else {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"loadPhotography() / cenClienteMapper.updateByExample() -> " + updateResponseDTO.getStatus() + " .No se ha podido actualizar el nombre de la fotografía de una persona jurídica");
				}
				
			}
			else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> " + updateResponseDTO.getStatus() + ".No existen ninguna persona con en idPersona:" + idPersona + " indicado");
			}

		}
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn(
					"loadPhotography() / genPropertiesMapper.selectByExample() -> " + updateResponseDTO.getStatus() + ".No se pudo obtener el directorio de la fotografía");
		}
		
		LOGGER.info(
				"uploadPhotography() -> Salida del servicio para guardar una fotografía de una persona jurídica");
		
		return updateResponseDTO;
	}

	@Override
	public PersonaJuridicaDTO searchGeneralData(int numPagina, PersonaJuridicaSearchDTO personaJuridicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"searchGeneralData() -> Entrada al servicio para buscar información general de una persona jurídica");
		
		PersonaJuridicaDTO personaJuridicaDTO = new PersonaJuridicaDTO();
		List<PersonaJuridicaItem> personaJuridicaItems = new ArrayList<PersonaJuridicaItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				personaJuridicaSearchDTO.setIdInstitucion(String.valueOf(idInstitucion));
				personaJuridicaSearchDTO.setIdLenguaje(usuario.getIdlenguaje());
				LOGGER.info(
						"searchGeneralData() / cenNocolegiadoExtendsMapper.searchGeneralData() -> Entrada a cenNocolegiadoExtendsMapper para informacion de persona jurídica por filtro");
				personaJuridicaItems = cenNocolegiadoExtendsMapper.searchGeneralData(personaJuridicaSearchDTO);
				LOGGER.info(
						"searchGeneralData() / cenNocolegiadoExtendsMapper.searchGeneralData() -> Salida de cenNocolegiadoExtendsMapper para informacion de persona jurídica por filtro");
				
				
				personaJuridicaDTO.setPersonaJuridicaItems(personaJuridicaItems);
				
			} 
			else {
				LOGGER.warn("searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchGeneralData() -> idInstitucion del token nula");
		}
		
		LOGGER.info(
				"searchGeneralData() -> Salida del servicio para buscar información general de una persona jurídica");
		
		return personaJuridicaDTO;
	}

	@Override
	public InsertResponseDTO createLegalPerson(SociedadCreateDTO sociedadCreateDTO, HttpServletRequest request) {
		
		LOGGER.info(
				"createLegalPerson() -> Entrada al servicio para actualizar información general de una persona jurídica");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();

		CenPersonaExample example = new CenPersonaExample();
		example.createCriteria().andNifcifEqualTo(sociedadCreateDTO.getNif());
		List<CenPersona> personas =  cenPersonaExtendsMapper.selectByExample(example );
		if(null != personas && !(personas.size()>0)) {
			
		
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"createLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"createLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
								
				// 1. crear tablas cen_persona, cen_nocolegiado y cen_cliente
				if(!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {
					
					// 1.1 crear registro en tabla cen_persona 
					LOGGER.info(
							"createLegalPerson() / cenPersonaExtendsMapper.insertSelectiveForNewSociety() -> Entrada a cenPersonaExtendsMapper para crear una nueva persona");
					
					int responseInsertPersona = cenPersonaExtendsMapper.insertSelectiveForNewSociety(sociedadCreateDTO, usuario);
					LOGGER.info(
							"createLegalPerson() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenPersonaExtendsMapper para crear una nueva persona");
					
					if(responseInsertPersona == 1) {
						// 1.3 crear registro en tabla cen_cliente
						CenCliente record = new CenCliente();
						record = rellenarInsertCenClienteNewSociety(sociedadCreateDTO, usuario, idInstitucion);
						int responseCenCliente = 0;
						if(null !=record.getIdpersona()) {
							responseCenCliente = cenClienteMapper.insertSelective(record);
						}
						
						if(responseCenCliente == 1)
						{
							// 1.2 crear registro en tabla cen_nocolegiado
							LOGGER.info(
									"createLegalPerson() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateNewSociety() -> Entrada a cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");
							
							int responseInsertNoColegiado =  cenNocolegiadoExtendsMapper.insertSelectiveForCreateNewSociety(String.valueOf(idInstitucion), usuario, sociedadCreateDTO);
							LOGGER.info(
									"createLegalPerson() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");
							
							
							// comprobar insercion en cen_cliente y obtener el nuevo idpersona creado
							if(responseInsertNoColegiado == 1) {
								insertResponseDTO.setStatus(SigaConstants.OK);
								insertResponseDTO.setId(String.valueOf(record.getIdpersona()));
							}
							else {
								insertResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"createLegalPerson() / cenClienteMapper.insertSelective() -> No se ha podido crear la persona no colegiada en tabla CEN_CLIENTE");
						
							}
							
						}
							
						else {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn(
									"createLegalPerson() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_NOCOLEGIADO");
						}
					}
					else {
						insertResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn(
								"createLegalPerson() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_PERSONA");
					}
					
				}
				
				
				// 2. crear/actualizar relaciones entre tablas para todos los grupos
				if(!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {
					
					if(null != sociedadCreateDTO.getGrupos() )
					{
						for (String grupo : sociedadCreateDTO.getGrupos()) {
							LOGGER.info(
									"createLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para obtener grupos de clientes");
							cenGruposcliente = cenGruposclienteExtendsMapper.selectDistinctGruposClientes(String.valueOf(idInstitucion), usuario.getIdlenguaje(), grupo);
							LOGGER.info(
									"createLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Salida de cenGruposclienteExtendsMapper para obtener grupos de clientes");
							
							// 2.1 existen registros => solo insertar/actualizar fecha_baja en tabla CEN_GRUPOSCLIENTE_CLIENTE 
							if(null != cenGruposcliente && cenGruposcliente.size() > 0) {
								
								List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
								CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
								relacionGrupoPersona.createCriteria().andIdpersonaEqualTo(Long.valueOf(insertResponseDTO.getId())).andIdgrupoEqualTo(Short.valueOf(grupo)).andIdinstitucionEqualTo(idInstitucion);
								listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper.selectByExample(relacionGrupoPersona);
								
								// 2.1.1. Si no existe registro en CEN_GRUPOSCLIENTE_CLIENTE se inserta
								if(listarelacionGrupoPersona.isEmpty()) {
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
									
									int response = cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion), grupo, String.valueOf(usuario.getIdusuario()));
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
									
									if(response == 0) {
										insertResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = " + grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
									}
								}
								// 2.1.2. Si existe registro en CEN_GRUPOSCLIENTE_CLIENTE se actualiza su fecha de baja
								else {
									
									listarelacionGrupoPersona.get(0).setFechaBaja(null);
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Entrada a cenGruposclienteClienteExtendsMapper actualizar la relacion grupo-persona jurídica");
									
									int response = cenGruposclienteClienteExtendsMapper.updateByExample(listarelacionGrupoPersona.get(0), relacionGrupoPersona);
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Salida de cenGruposclienteClienteExtendsMapper actualizar la relacion grupo-persona jurídica");
									
									if(response == 0) {
										insertResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> No se pudo actualizar la fecha de baja del grupo = " + grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
									}
									
								}
								
							}
							// 2.2 no existen registros => solo insertar en: tabla CEN_GRUPOSCLIENTE , GEN_RECURSOS_CATALOGOS  y CEN_GRUPOSCLIENTE_CLIENTE 
							else {
								int response1 = 0;
								int response2 = 0;
								int response3 = 0;
								// 1.2.1 insertar en GEN_RECURSOS_CATALOGOS para generar recurso
								String nombreTabla = "CEN_GRUPOSCLIENTE";
								String campoTabla = "NOMBRE";
								LOGGER.info(
										"createLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
								response1 = genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion),usuario, grupo, nombreTabla, campoTabla);
								LOGGER.info(
										"createLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
								
								if(response1 == 1) {
									
									// 2.2.2 insertar en CEN_GRUPOSCLIENTE para generar el grupo
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");
									
									response2 = cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion), usuario);
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");
									
									if(response2 == 1) {
										// 2.2.3 insertar en CEN_GRUPOSCLIENTE_CLIENTE para relacionar grupos-usuarios
										LOGGER.info(
												"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
										
										response3 = cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion), "", String.valueOf(usuario.getIdusuario()));
										LOGGER.info(
												"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
										
										if(response3 == 0) {
											insertResponseDTO.setStatus(SigaConstants.KO);
											LOGGER.warn(
													"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = " + grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
											
										}
									}
									else {
										insertResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"createLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = " + grupo + " en tabla CEN_GRUPOSCLIENTE");	
									}
								}
								else {
									insertResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"createLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = " + grupo + " en tabla GEN_RECURSOS_CATALOGOS");
								}
							}
						}
					}
				}
				

			} else {
				insertResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"createLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			insertResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("createLegalPerson() -> idInstitucion del token nula");
		}
		
		}else {
			insertResponseDTO.setStatus(SigaConstants.KO);
			org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
			error.setMessage("messages.censo.nifcifExiste2");
			insertResponseDTO.setError(error);
		}
		
		// AUDITORIA  => actualizamos cen_historico si todo es correcto
		if(insertResponseDTO.getStatus().equals(SigaConstants.OK)) {
			
			Long idPersonaCreada = Long.valueOf(insertResponseDTO.getId());
			// obtenemos registro cen_persona creado
			CenPersona cenPersonaPosterior = new CenPersona();
			cenPersonaPosterior = cenPersonaExtendsMapper.selectByPrimaryKey(idPersonaCreada);
			// obtenemos registro cen_nocolegiado creado
			CenNocolegiado cenNocolegiadoPosterior = new CenNocolegiado();
			CenNocolegiadoKey cenNocolegiadoKey = new CenNocolegiadoKey();
			cenNocolegiadoKey.setIdpersona(idPersonaCreada);
			cenNocolegiadoKey.setIdinstitucion(idInstitucion);
			cenNocolegiadoPosterior = cenNocolegiadoExtendsMapper.selectByPrimaryKey(cenNocolegiadoKey);
			// obtenemos registro cen_cliente creado
			CenCliente cenClientePosterior = new CenCliente();
			CenClienteKey cenClienteKey = new CenClienteKey();
			cenClienteKey.setIdpersona(idPersonaCreada);
			cenClienteKey.setIdinstitucion(idInstitucion);
			cenClientePosterior = cenClienteMapper.selectByPrimaryKey(cenClienteKey);
			// obtenemos las etiquetas creadas
			List<String> gruposPerJuridicaNuevos = Arrays.asList(sociedadCreateDTO.getGrupos());
			// llamada a auditoria
			auditoriaCenHistoricoService.manageAuditoriaDatosGenerales(gruposPerJuridicaNuevos, null, null, cenPersonaPosterior, null, cenNocolegiadoPosterior, null, cenClientePosterior, "INSERT", request, sociedadCreateDTO.getMotivo());
		}
		
		LOGGER.info(
				"createLegalPerson() -> Salida del servicio para actualizar información general de una persona jurídica");
		
		
		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, HttpServletRequest request) {
		
		LOGGER.info(
				"updateLegalPerson() -> Entrada al servicio para actualizar información general de una persona jurídica");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<ComboItem> gruposPersonaJuridica = new ArrayList<ComboItem>();
		// datos para auditoria (cen_historico)
		CenPersona cenPersonaAnterior = new CenPersona();
		CenPersona cenPersonaPosterior = new CenPersona();
		CenNocolegiado cenNocolegiadoAnterior = new CenNocolegiado();
		CenNocolegiado cenNocolegiadoPosterior = new CenNocolegiado();
		CenCliente cenClienteAnterior = new CenCliente();
		CenCliente cenClientePosterior = new CenCliente();
		List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();
		List<String> gruposNuevosNoAniadidos = new ArrayList<String>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				// 1. obtener grupos de la persona juridica antes de actualizar
				gruposPersonaJuridica = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(etiquetaUpdateDTO.getIdPersona(), String.valueOf(usuario.getIdinstitucion()));
				
				List<String> gruposPerJuridicaAntiguos = new ArrayList<String>();
				List<String> gruposPerJuridicaAnterior = new ArrayList<String>();
				
				for(int i = 0; i< gruposPersonaJuridica.size();i++) {
					gruposPerJuridicaAntiguos.add(gruposPersonaJuridica.get(i).getValue());
					gruposPerJuridicaAnterior.add(gruposPersonaJuridica.get(i).getValue());
				}
				
				// 2. actualizar relaciones entre tablas para todos los grupos (eliminado y creación)
				for (String grupo : etiquetaUpdateDTO.getGrupos()) {
					
					// 2.1 si el nuevo grupo no está, hay que crearlo
					if(!gruposPerJuridicaAntiguos.contains(grupo)) {
						
						// guardamos para AUDITORIA los grupos nuevos
						gruposNuevosNoAniadidos.add(grupo);
						
						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para obtener grupos de clientes");
						cenGruposcliente = cenGruposclienteExtendsMapper.selectDistinctGruposClientes(String.valueOf(idInstitucion), usuario.getIdlenguaje(), grupo);
						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Salida de cenGruposclienteExtendsMapper para obtener grupos de clientes");
						
						// 2.1.1 si existen ya registros para ese grupo => solo actualizar/insertar en tabla CEN_GRUPOSCLIENTE_CLIENTE (relacion persona-grupo)
						if(null != cenGruposcliente && cenGruposcliente.size() > 0) {
							
							List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
							CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
							relacionGrupoPersona.createCriteria().andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona())).andIdgrupoEqualTo(Short.valueOf(grupo)).andIdinstitucionEqualTo(idInstitucion);
							listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper.selectByExample(relacionGrupoPersona);
							
							// 2.1.1.1 si no existe registro que relaciona un grupo-persona juridica => se crea registro en CEN_GRUPOSCLIENTE_CLIENTE
							if(listarelacionGrupoPersona.isEmpty()) {
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
								
								int response = cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(etiquetaUpdateDTO, String.valueOf(idInstitucion), grupo, String.valueOf(usuario.getIdusuario()));
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
								
								if(response == 0) {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> No se pudo insertar el grupo = " + grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
								}
							}
							// 2.1.1.2 existe registro que relaciona un grupo-persona juridica => se actualiza la fecha de baja del registro en CEN_GRUPOSCLIENTE_CLIENTE
							else {
								
								listarelacionGrupoPersona.get(0).setFechaBaja(null);
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Entrada a cenGruposclienteClienteExtendsMapper para actualizar relacion grupo-persona jurídica");
								
								int response = cenGruposclienteClienteExtendsMapper.updateByExample(listarelacionGrupoPersona.get(0), relacionGrupoPersona);
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Salida de cenGruposclienteClienteExtendsMapper para actualizar relacion grupo-persona jurídica");
								
								if(response == 0) {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteExtendsMapper.updateByExample() -> No se pudo actualizar la fecha de baja del grupo = " + grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
								}
							}
							
						}
						// 2.1.2 no existen registros para ese grupo => insertar en: tabla CEN_GRUPOSCLIENTE , GEN_RECURSOS_CATALOGOS  y CEN_GRUPOSCLIENTE_CLIENTE 
						else {
							int response1 = 0;
							int response2 = 0;
							int response3 = 0;
							// insertar en GEN_RECURSOS_CATALOGOS para generar recurso
							String nombreTabla = "CEN_GRUPOSCLIENTE";
							String campoTabla = "NOMBRE";
							LOGGER.info(
									"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
							response1 = genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(
									String.valueOf(idInstitucion), usuario, grupo, nombreTabla, campoTabla);
							LOGGER.info(
									"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");

							if (response1 == 1) {

								// insertar en CEN_GRUPOSCLIENTE para generar el grupo
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");

								response2 = cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion), usuario);
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");

								if (response2 == 1) {
									// insertar en CEN_GRUPOSCLIENTE_CLIENTE para relacionar grupos-usuarios
									LOGGER.info(
											"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

									response3 = cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(etiquetaUpdateDTO, String.valueOf(idInstitucion), "",String.valueOf(usuario.getIdusuario()));
									LOGGER.info(
											"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

									if (response3 == 0) {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
														+ grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");

									}
								} else {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
													+ grupo + " en tabla CEN_GRUPOSCLIENTE");
								}
							} else {
								updateResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
												+ grupo + " en tabla GEN_RECURSOS_CATALOGOS");
							}
						}
					}
					// 2.2 el grupo ya existe para la persona juridica => eliminar de los grupos antiguos
					else {
						// eliminamos grupo de los antiguos que tenia la persona juridica para que solo queden los que hay que eliminar
						gruposPerJuridicaAntiguos.remove(grupo);
					}
				}
				
				
				// 3. actualiza campos de la persona jurídica	
				if(!updateResponseDTO.getStatus().equals(SigaConstants.KO)) {
					
					// 3.1.actualiza tabla CEN_PERSONA
					
					cenPersonaAnterior = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					
					CenPersona cenPersona = new CenPersona();
					cenPersona.setNombre(etiquetaUpdateDTO.getDenominacion());
					cenPersona.setApellidos1(etiquetaUpdateDTO.getAbreviatura());
					cenPersona.setFechamodificacion(new Date());
					cenPersona.setUsumodificacion(usuario.getIdusuario());
					CenPersonaExample cenPersonaExample = new CenPersonaExample();
					cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					LOGGER.info(
							"updateLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> Entrada a cenPersonaExtendsMapper para actualizar información de persona juridica en CEN_PERSONA");
					
					cenPersonaExtendsMapper.updateByExampleSelective(cenPersona, cenPersonaExample);
					LOGGER.info(
							"updateLegalPerson() / cenPersonaExtendsMapper.updateByExampleSelective() -> Salida de cenPersonaExtendsMapper para actualizar información de persona juridica en CEN_PERSONA");
					
					cenPersonaPosterior = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					
					// 3.2.Actualiza tabla CEN_NOCOLEGIADO
					
					CenNocolegiadoKey keyAnterior = new CenNocolegiadoKey();
					keyAnterior.setIdinstitucion(idInstitucion);
					keyAnterior.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					cenNocolegiadoAnterior = cenNocolegiadoExtendsMapper.selectByPrimaryKey(keyAnterior);
					
					CenNocolegiado cenNocolegiado = new CenNocolegiado();
					cenNocolegiado.setFechamodificacion(new Date());
					cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
					cenNocolegiado.setTipo(etiquetaUpdateDTO.getTipo());
					cenNocolegiado.setAnotaciones(etiquetaUpdateDTO.getAnotaciones());
					CenNocolegiadoExample cenNocolegiadoExample = new CenNocolegiadoExample();
					cenNocolegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					LOGGER.info("updateLegalPerson() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar información de persona juridica en CEN_NOCOLEGIADO");
					cenNocolegiadoExtendsMapper.updateByExampleSelective(cenNocolegiado, cenNocolegiadoExample);
					LOGGER.info("updateLegalPerson() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar información de persona juridica en CEN_NOCOLEGIADO");
					
					CenNocolegiadoKey keyPosterior = keyAnterior;
					cenNocolegiadoPosterior = cenNocolegiadoExtendsMapper.selectByPrimaryKey(keyPosterior);
					
					// 3.3. Actualiza tabla CEN_CLIENTE
					
					
					CenClienteKey key = new CenClienteKey();
					key.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					key.setIdinstitucion(idInstitucion);
					
					cenClienteAnterior = cenClienteMapper.selectByPrimaryKey(key);
					
					CenCliente cenCliente = new CenCliente();
					cenCliente = cenClienteMapper.selectByPrimaryKey(key);
					cenCliente.setAsientocontable(etiquetaUpdateDTO.getCuentaContable());
					cenCliente.setIdlenguaje(etiquetaUpdateDTO.getIdioma());
					
					cenClienteMapper.updateByPrimaryKey(cenCliente);
					
					
					cenClientePosterior = cenClienteMapper.selectByPrimaryKey(key);
					
					// 3.4 Elimina grupos que dejan de estar relacionados con el usuario
					for(int i=0;i < gruposPerJuridicaAntiguos.size(); i++) {
						CenGruposclienteCliente cenGruposclienteCliente = new CenGruposclienteCliente();
						cenGruposclienteCliente.setFechamodificacion(new Date());
						cenGruposclienteCliente.setUsumodificacion(usuario.getIdusuario());
						cenGruposclienteCliente.setFechaBaja(new Date());
						CenGruposclienteClienteExample cenGruposclienteClienteExample =  new CenGruposclienteClienteExample();
						cenGruposclienteClienteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona())).
						andIdinstitucionGrupoEqualTo(idInstitucion).andIdgrupoEqualTo(Short.valueOf(gruposPerJuridicaAntiguos.get(i)));
						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> Entrada a cenGruposclienteClienteExtendsMapper para eliminar un grupo relacionado con persona juridica en tabla CEN_GRUPOSCLIENTE_CLIENTE");
						int eliminadoGrupo = cenGruposclienteClienteExtendsMapper.updateByExampleSelective(cenGruposclienteCliente, cenGruposclienteClienteExample);
						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> Salida de cenGruposclienteClienteExtendsMapper para eliminar un grupo relacionado con persona juridica en tabla CEN_GRUPOSCLIENTE_CLIENTE");
						
						if(eliminadoGrupo == 0) {
							updateResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn(
									"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> No se pudo eliminar el grupo = "
											+ gruposPerJuridicaAntiguos.get(i) + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
						}
					}
					
					// si todo ha funcionado correctamente, la respuesta será OK
					if(!updateResponseDTO.getStatus().equals(SigaConstants.KO)) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						
						List<String> gruposPerJuridicaPosterior = Arrays.asList(etiquetaUpdateDTO.getGrupos());
						
						// AUDITORIA  => actualizamos cen_historico si todo es correcto
						auditoriaCenHistoricoService.manageAuditoriaDatosGenerales(gruposPerJuridicaPosterior, gruposPerJuridicaAnterior, cenPersonaAnterior, cenPersonaPosterior, cenNocolegiadoAnterior, cenNocolegiadoPosterior, cenClienteAnterior, cenClientePosterior, "UPDATE", request, etiquetaUpdateDTO.getMotivo());
						
						
					}
				}
				
			}
			else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"updateLegalPerson() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateLegalPerson() -> idInstitucion del token nula");
		}
		
		LOGGER.info(
				"updateLegalPerson() -> Salida del servicio para actualizar información general de una persona jurídica");
		
		return updateResponseDTO;
	}
	
	
	
	
	protected CenCliente rellenarInsertCenClienteNewSociety(SociedadCreateDTO sociedadCreateDTO ,AdmUsuarios usuario, Short idInstitucion) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		CenCliente record = new CenCliente();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// selecciona max de idpersona
		comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
		
		if(!comboItems.isEmpty()) {
			record.setIdpersona(Long.valueOf(comboItems.get(0).getValue()));
			record.setIdinstitucion(idInstitucion);
			record.setFechaalta(new Date());
			record.setCaracter("P");
			record.setPublicidad(SigaConstants.DB_FALSE);
			record.setGuiajudicial(SigaConstants.DB_FALSE);
			record.setComisiones(SigaConstants.DB_FALSE);
			record.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
			record.setFechamodificacion(new Date());
			record.setUsumodificacion(usuario.getIdusuario());
			
			if(null != sociedadCreateDTO.getIdioma() && !sociedadCreateDTO.getIdioma().equals("")) {
				record.setIdlenguaje(sociedadCreateDTO.getIdioma());
			}
		
			if (null != sociedadCreateDTO.getFechaAlta()) {
				record.setFechaalta(sociedadCreateDTO.getFechaAlta());
//				sql.VALUES("FECHAALTA", "TO_DATE('" + fechaAlta + "','DD/MM/YYYY')");
			}

			
			if(null != sociedadCreateDTO.getCuentaContable() && !sociedadCreateDTO.getCuentaContable().equals("") ) {
				record.setAsientocontable(sociedadCreateDTO.getCuentaContable());
			}
			
			record.setExportarfoto(SigaConstants.DB_FALSE);
		}
		
		return record;
	}

	
}
