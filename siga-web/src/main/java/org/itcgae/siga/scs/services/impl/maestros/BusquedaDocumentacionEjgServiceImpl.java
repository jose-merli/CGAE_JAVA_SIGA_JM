package org.itcgae.siga.scs.services.impl.maestros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsDocumentoejg;
import org.itcgae.siga.db.entities.ScsDocumentoejgExample;
import org.itcgae.siga.db.entities.ScsTipodocumentoejg;
import org.itcgae.siga.db.entities.ScsTipodocumentoejgExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipodocumentoEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDocumentoejgExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IBusquedaDocumentacionEjgService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusquedaDocumentacionEjgServiceImpl implements IBusquedaDocumentacionEjgService {

	private Logger LOGGER = Logger.getLogger(BusquedaDocumentacionEjgServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsTipodocumentoEjgExtendsMapper scsDocumentacionEjgExtendsMapper;

	@Autowired
	private ScsDocumentoejgExtendsMapper scsDocumentoEjgExtendsMapper;
	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public DocumentacionEjgDTO searchDocumento(DocumentacionEjgItem documentacionEjgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean exists = false;
		Error error = new Error();
		DocumentacionEjgDTO documentacionEjgDTO = new DocumentacionEjgDTO();
		List<DocumentacionEjgItem> documentacionEjgItems = null;
		List<DocumentacionEjgItem> documentacionEjgItemsFinal = new ArrayList<DocumentacionEjgItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchDocumento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchDocumento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				try {

					LOGGER.info(
							"searchDocumento() / scsDocumentacionEjgExtendsMapper.selectTipoSolicitud() -> Entrada a scsDocumentacionEjgExtendsMapper para obtener los documentos");

					documentacionEjgItem.setIdInstitucion(idInstitucion);
					
					//Tratamiento apostrofes
					documentacionEjgItem.setabreviaturaTipoDoc(UtilidadesString.tratamientoApostrofes(documentacionEjgItem.getabreviaturaTipoDoc()));
					documentacionEjgItem.setdescripcionTipoDoc(UtilidadesString.tratamientoApostrofes(documentacionEjgItem.getdescripcionTipoDoc()));
					documentacionEjgItem.setAbreviatura(UtilidadesString.tratamientoApostrofes(documentacionEjgItem.getAbreviatura()));
					documentacionEjgItem.setdescripcionDoc(UtilidadesString.tratamientoApostrofes(documentacionEjgItem.getdescripcionDoc()));
					
					documentacionEjgItems = scsDocumentacionEjgExtendsMapper.searchDocumento(documentacionEjgItem,
							usuarios.get(0).getIdlenguaje());

					if (documentacionEjgItems != null && documentacionEjgItems.size() > 0) {

						if (documentacionEjgItems.get(0).getAbreviatura() != null) {
							documentacionEjgItems.get(0).setIndiceAbreviatura(0);
							documentacionEjgItems.get(0).setIndiceDesc(0);

							documentacionEjgItems.get(0)
									.setAbreviatura("1.-" + documentacionEjgItems.get(0).getAbreviatura());
							documentacionEjgItems.get(0).setIndiceAbreviatura(1);
							if (documentacionEjgItems.get(0).getdescripcionDoc() != null) {
								documentacionEjgItems.get(0)
										.setdescripcionDoc("1.-" + documentacionEjgItems.get(0).getdescripcionDoc());
								documentacionEjgItems.get(0).setIndiceDesc(1);
							}
						}
						documentacionEjgItemsFinal.add(documentacionEjgItems.get(0));

						for (int i = 1; i < documentacionEjgItems.size(); i++) {
							for (int j = 0; j < documentacionEjgItemsFinal.size(); j++) {

								if (documentacionEjgItemsFinal.get(j).getIdTipoDocumento()
										.equals(documentacionEjgItems.get(i).getIdTipoDocumento())) {
									exists = true;

									documentacionEjgItemsFinal.get(j).setIndiceAbreviatura(
											documentacionEjgItemsFinal.get(j).getIndiceAbreviatura() + 1);
									documentacionEjgItemsFinal.get(j)
											.setAbreviatura(documentacionEjgItemsFinal.get(j).getAbreviatura() + "<br>"
													+ documentacionEjgItemsFinal.get(j).getIndiceAbreviatura() + ".- "
													+ documentacionEjgItems.get(i).getAbreviatura());

									if (documentacionEjgItemsFinal.get(j).getdescripcionDoc() != null) {
										documentacionEjgItemsFinal.get(j)
												.setIndiceDesc(documentacionEjgItemsFinal.get(j).getIndiceDesc() + 1);
										documentacionEjgItemsFinal.get(j)
												.setdescripcionDoc(documentacionEjgItemsFinal.get(j).getdescripcionDoc()
														+ "<br>" + documentacionEjgItemsFinal.get(j).getIndiceDesc()
														+ ".- " + documentacionEjgItems.get(i).getdescripcionDoc());
									}
								}
							}
							if (!exists) {
								// add tipo
								if (documentacionEjgItems.get(i).getAbreviatura() != null) {
									documentacionEjgItems.get(i).setIndiceAbreviatura(0);
									documentacionEjgItems.get(i)
											.setAbreviatura("1.-" + documentacionEjgItems.get(i).getAbreviatura());
									documentacionEjgItems.get(i).setIndiceAbreviatura(
											documentacionEjgItems.get(i).getIndiceAbreviatura() + 1);

									if (documentacionEjgItems.get(i).getdescripcionDoc() != null) {
										documentacionEjgItems.get(i).setIndiceDesc(0);
										documentacionEjgItems.get(i).setdescripcionDoc(
												"1.-" + documentacionEjgItems.get(i).getdescripcionDoc());
										documentacionEjgItems.get(i)
												.setIndiceDesc(documentacionEjgItems.get(i).getIndiceDesc() + 1);

									}
								}
								documentacionEjgItemsFinal.add(documentacionEjgItems.get(i));
							}
							exists = false;
						}
					}

					/*
					 * if(docItem != null && nuevo.getIdTipoDocumento() !=
					 * nuevo1.getIdTipoDocumento() && nuevo.getIdTipoDocumento() !=
					 * documentacionEjgItems.get(i-1).getIdTipoDocumento()) {
					 * documentacionEjgItemsFinal.add(documentacionEjgItems.get(i-1)); //pusheo
					 * nuevo = documentacionEjgItems.get(i);
					 * 
					 * }else { nuevo.setAbreviatura(nuevo.getAbreviatura() + "\n" +
					 * documentacionEjgItems.get(i).getAbreviatura());
					 * nuevo.setdescripcionDoc(nuevo.getdescripcionDoc() + "\n" +
					 * documentacionEjgItems.get(i).getdescripcionDoc()); } }
					 */

					LOGGER.info(
							"searchDocumento() / scsDocumentacionEjgExtendsMapper.selectTipoSolicitud() -> Salida a scsDocumentacionEjgExtendsMapper para obtener los documentos");

					if (documentacionEjgItemsFinal != null) {
						documentacionEjgDTO.setDocumentacionEjgItems(documentacionEjgItemsFinal);
					}

				} catch (Exception e) {
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
				}
			}

		}
		LOGGER.info("searchCourt() -> Salida del servicio para obtener los documentos");
		return documentacionEjgDTO;
	}

	@Override
	@Transactional
	public UpdateResponseDTO deleteTipoDoc(DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar areas de un grupo zona");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (DocumentacionEjgItem documentacionejgItem : documentacionejgDTO.getDocumentacionEjgItems()) {

						// Eliminamos Tipodocumentos

						ScsTipodocumentoejg documentoejg = new ScsTipodocumentoejg();
						if (documentacionejgItem.getFechabaja() == null) {
							documentoejg.setFechabaja(new Date());
						} else {
							documentoejg.setFechabaja(null);

						}
						/*
						 * documentacionejgItem.getAbreviatura()
						 * documentacionejgItem.getabreviaturaTipoDoc()
						 * documentacionejgItem.getdescripcionDoc()
						 * documentacionejgItem.getdescripcionTipoDoc()
						 */
						documentoejg.setIdtipodocumentoejg(Short.valueOf(documentacionejgItem.getIdTipoDocumento()));
						documentoejg.setIdinstitucion((short) documentacionejgItem.getIdInstitucion());
						documentoejg.setAbreviatura(documentacionejgItem.getabreviaturaTipoDoc());
						documentoejg.setDescripcion(documentacionejgItem.getCodigodescripcion());
						documentoejg.setUsumodificacion(usuarios.get(0).getIdusuario());
						documentoejg.setFechamodificacion(new Date());

						LOGGER.info(
								"deleteAreas() / scsDocumentacionEjgMapper.deleteByExample() -> Entrada a scsDocumentacionEjgMapper para eliminar los tipo Documentos seleccionados");

						response = scsDocumentacionEjgExtendsMapper.updateByPrimaryKey(documentoejg);

						LOGGER.info(
								"deleteAreas() / scsDocumentacionEjgMapper.deleteByExample() -> Salida de scsDocumentacionEjgMapper para eliminar los tipo Documentos seleccionados");
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("documentacionejg.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"deleteZones() -> Salida del servicio para eliminar para eliminar los tipo Documentos seleccionados");

		return updateResponseDTO;
	}

	@Override
	public DocumentacionEjgDTO searchDocumentos(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		DocumentacionEjgDTO documentacionEjgDTO = new DocumentacionEjgDTO();
		List<DocumentacionEjgItem> documentacionEjgItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchDocumentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchDocumentos() / scsDocumentoEjgExtendsMapper.selectTipoSolicitud() -> Entrada a scsDocumentacionEjgExtendsMapper para obtener los documentos");

				documentacionejgItem.setIdInstitucion(idInstitucion);
				documentacionEjgItems = scsDocumentoEjgExtendsMapper.searchDocumento(documentacionejgItem,
						usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchDocumentos() / scsDocumentoEjgExtendsMapper.selectTipoSolicitud() -> Salida a scsDocumentacionEjgExtendsMapper para obtener los documentos");

				if (documentacionEjgItems != null) {
					documentacionEjgDTO.setDocumentacionEjgItems(documentacionEjgItems);
				}
			}

		}
		LOGGER.info("searchCourt() -> Salida del servicio para obtener los documentos");
		return documentacionEjgDTO;
	}

	@Override
	public UpdateResponseDTO updateTipoDoc(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		LOGGER.info("updateTipoDoc() ->  Entrada al servicio para actualizar un tipo de documento");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenRecursosCatalogos genRecursosCatalogos = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateTipoDoc() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateTipoDoc() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"insertFundamentos() / scsTipodocumentoejgMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo documento");

					genRecursosCatalogos = new GenRecursosCatalogos();
					genRecursosCatalogos.setIdrecurso(documentacionejgItem.getCodigodescripcion());
					genRecursosCatalogos.setDescripcion(documentacionejgItem.getdescripcionTipoDoc());
					genRecursosCatalogos.setFechamodificacion(new Date());
					genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
					genRecursosCatalogos.setIdinstitucion(idInstitucion);
					genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());
					genRecursosCatalogos.setIdrecursoalias("scs_tipodocumentoejg.descripcion." + idInstitucion + "."
							+ genRecursosCatalogos.getIdrecurso());
					genRecursosCatalogos.setNombretabla("SCS_SCS_TIPODOCUMENTOEJG");
					genRecursosCatalogos.setCampotabla("DESCRIPCION");

					ScsTipodocumentoejgExample example = new ScsTipodocumentoejgExample();

					example.createCriteria()
							.andIdtipodocumentoejgEqualTo(Short.valueOf(documentacionejgItem.getIdTipoDocumento()))
							.andIdinstitucionEqualTo(idInstitucion);

					ScsTipodocumentoejg tipoDoc = scsDocumentacionEjgExtendsMapper.selectByExample(example).get(0);
					updateResponseDTO.setId(tipoDoc.getIdtipodocumentoejg().toString());

					LOGGER.info(
							"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para updatear el nuevo fundamento");

					LOGGER.info(
							"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para updatear el nuevo fundamento");
					genRecursosCatalogos.setIdrecurso(tipoDoc.getDescripcion());
					genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
					updateRestoIdiomas(genRecursosCatalogos);

					LOGGER.info(
							"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para updatear el nuevo fundamento");

					tipoDoc.setAbreviatura(documentacionejgItem.getabreviaturaTipoDoc());
					tipoDoc.setCodigoext(documentacionejgItem.getCodigoExt());
					tipoDoc.setUsumodificacion(usuario.getIdusuario());
					tipoDoc.setFechamodificacion(new Date());
					tipoDoc.setIdinstitucion(idInstitucion);

					LOGGER.info(
							"updateTipoDoc() / scsDocumentacionEjgExtendsMapper.insert() -> Entrada a scsDocumentacionEjgExtendsMapper para updatear un nuevo documento");

					response = scsDocumentacionEjgExtendsMapper.updateByPrimaryKey(tipoDoc);

					LOGGER.info(
							"updateTipoDoc() / scsDocumentacionEjgExtendsMapper.insert() -> Salida de scsDocumentacionEjgExtendsMapper para updatear un nuevo documento");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			// error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar areas de un grupo zona");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO createTipoDoc(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		LOGGER.info("createTipoDoc() ->  Entrada al servicio para crear un nuevo tipoDocumento");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Short idTipoDocNuevo = 0;
		ScsTipodocumentoejg tipoDoc = new ScsTipodocumentoejg();

		GenRecursosCatalogos genRecursosCatalogos = null;
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createTipoDoc() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createTipoDoc() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"createTipoDoc() / scsTipodocumentoejgMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo documento");
					GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
					genRecursosCatalogosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
							.andDescripcionEqualTo(documentacionejgItem.getdescripcionTipoDoc());

					NewIdDTO idTipoDoc = scsDocumentacionEjgExtendsMapper
							.getIdTipoDocumentoEjg(usuario.getIdinstitucion());
					if (idTipoDoc == null) {
						tipoDoc.setIdtipodocumentoejg((short) 1);
					} else {
						idTipoDocNuevo = (short) (Integer.parseInt(idTipoDoc.getNewId()) + 1);
						tipoDoc.setIdtipodocumentoejg(idTipoDocNuevo);
					}

					/*
					 * if (l != null && l.size() > 0) { //hacer lo mismo q en else, lo dice jesu
					 * 
					 * } else {
					 */
					genRecursosCatalogos = new GenRecursosCatalogos();
					genRecursosCatalogos.setDescripcion(documentacionejgItem.getdescripcionTipoDoc());
					genRecursosCatalogos.setFechamodificacion(new Date());
					genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
					genRecursosCatalogos.setIdinstitucion(idInstitucion);
					genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());
					genRecursosCatalogos.setIdrecursoalias("scs_tipodocumentoejg.descripcion." + idInstitucion + "."
							+ genRecursosCatalogos.getIdrecurso());
					genRecursosCatalogos.setNombretabla("SCS_SCS_TIPODOCUMENTOEJG");
					genRecursosCatalogos.setCampotabla("DESCRIPCION");
					NewIdDTO idJD = genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion.toString(),
							usuario.getIdlenguaje());
					if (idJD == null) {
						genRecursosCatalogos.setIdrecurso("1");

					} else {
						genRecursosCatalogos.setIdrecurso((Long.parseLong(idJD.getNewId()) + 1) + "");
						tipoDoc.setDescripcion(genRecursosCatalogos.getIdrecurso());
					}
					insertResponseDTO.setId(tipoDoc.getIdtipodocumentoejg().toString());

					genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
					insertarRestoIdiomas(genRecursosCatalogos);

					LOGGER.info(
							"createTipoDoc() / genRecursosCatalogosExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para insertar el nuevo documento");
					// }

					tipoDoc.setAbreviatura(documentacionejgItem.getabreviaturaTipoDoc());
					tipoDoc.setCodigoext(documentacionejgItem.getCodigoExt());
					tipoDoc.setIdinstitucion(idInstitucion);
					tipoDoc.setUsumodificacion(usuario.getIdusuario());
					tipoDoc.setFechamodificacion(new Date());

					LOGGER.info(
							"createTipoDoc() / scsDocumentacionEjgExtendsMapper.insert() -> Entrada a scsDocumentacionEjgExtendsMapper para insertar un nuevo documento");

					response = scsDocumentacionEjgExtendsMapper.insert(tipoDoc);

					LOGGER.info(
							"createTipoDoc() / scsDocumentacionEjgExtendsMapper.insert() -> Salida de scsDocumentacionEjgExtendsMapper para insertar un nuevo documento");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idTipoDocNuevo)));
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuvo tipo documento");

		return insertResponseDTO;
	}

	// INSERTAR GEN RECURSOS CATALOGOS
	private int insertarRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

	// MODIFICAR GEN RECURSOS CATALOGOS
	private int updateRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

	@Override
	public UpdateResponseDTO deleteDoc(DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para eliminar areas de un grupo zona");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (DocumentacionEjgItem documentacionejgItem : documentacionejgDTO.getDocumentacionEjgItems()) {

						// Eliminamos Documentos

						ScsDocumentoejg documentoejg = new ScsDocumentoejg();
						documentoejg.setIddocumentoejg(Short.valueOf(documentacionejgItem.getIdDocumento()));

						ScsDocumentoejgExample example = new ScsDocumentoejgExample();
						example.createCriteria()
								.andIddocumentoejgEqualTo(Short.valueOf(documentacionejgItem.getIdDocumento()))
								.andIdtipodocumentoejgEqualTo(Short.valueOf(documentacionejgItem.getIdTipoDocumento()))
								.andIdinstitucionEqualTo(idInstitucion);
						documentoejg = scsDocumentoEjgExtendsMapper.selectByExample(example).get(0);

						if (documentacionejgItem.getFechabaja() == null) {
							documentoejg.setFechabaja(new Date());
						} else {
							documentoejg.setFechabaja(null);

						}
						
						documentoejg.setUsumodificacion(usuarios.get(0).getIdusuario());
						documentoejg.setFechamodificacion(new Date());

						LOGGER.info(
								"deleteAreas() / scsDocumentoEjgExtendsMapper.deleteByExample() -> Entrada a scsDocumentoEjgExtendsMapper para eliminar los  Documentos seleccionados");

						response = scsDocumentoEjgExtendsMapper.updateByPrimaryKey(documentoejg);

						LOGGER.info(
								"deleteAreas() / scsDocumentoEjgExtendsMapper.deleteByExample() -> Salida de scsDocumentacionEjgMapper para eliminar los  Documentos seleccionados");
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("documentacionejg.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info(
				"deleteZones() -> Salida del servicio para eliminar para eliminar los tipo Documentos seleccionados");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateDoc(DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request) {
		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
		// PENDIENTE DE ACABAR
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (DocumentacionEjgItem documentoItem : documentacionejgDTO.getDocumentacionEjgItems()) {

						ScsDocumentoejg documento = new ScsDocumentoejg();
						documento.setIddocumentoejg(Short.valueOf(documentoItem.getIdDocumento()));
						documento.setIdtipodocumentoejg(Short.valueOf(documentoItem.getIdTipoDocumento()));
						documento.setIdinstitucion(idInstitucion);
						documento.setCodigoext(documentoItem.getCodigoExt());
						documento.setUsumodificacion(usuarios.get(0).getIdusuario());
						documento.setFechamodificacion(new Date());
						documento.setAbreviatura(documentoItem.getAbreviatura());
						//Select-update DESCRIPCION
						GenRecursosCatalogosExample exampleDescripcion = new GenRecursosCatalogosExample();
						exampleDescripcion.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdlenguajeEqualTo(usuarios.get(0).getIdlenguaje())
								.andIdrecursoEqualTo(documentoItem.getIdCodigoDescripcion());
						
						GenRecursosCatalogos genDes = genRecursosCatalogosExtendsMapper.selectByExample(exampleDescripcion).get(0);
						genDes.setDescripcion(documentoItem.getdescripcionDoc());

						//Update
						genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genDes);
						response = scsDocumentoEjgExtendsMapper.updateByPrimaryKeySelective(documento);

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.actualizarerror");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteZones() -> Salida del servicio para eliminar areas de un grupo zona");

		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO createDoc(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request) {
		LOGGER.info("createDoc() ->  Entrada al servicio para crear un nuevo Documento");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Short idTipoDocNuevo = 0;
		ScsDocumentoejg nuevoDoc = new ScsDocumentoejg();

		GenRecursosCatalogos genRecursosCatalogos = null;
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createDoc() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createDoc() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsDocumentoejgExample ejemplo = new ScsDocumentoejgExample();
					ejemplo.createCriteria().andAbreviaturaEqualTo(documentacionejgItem.getAbreviatura())
							.andIdinstitucionEqualTo(idInstitucion);

					List<ScsDocumentoejg> tipoDocDuplicados = scsDocumentoEjgExtendsMapper.selectByExample(ejemplo);

					LOGGER.info(
							"updateGroupZone() / scsTipodocumentoejgMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					if (tipoDocDuplicados != null && tipoDocDuplicados.size() > 0) {

						error.setCode(400);
						error.setDescription("messages.censo.nombreExiste");

					} else {

						LOGGER.info(
								"insertFundamentos() / scsTipodocumentoejgMapper.insert() -> Entrada a scsTipodocumentoejgMapper para insertar el nuevo documento");

						NewIdDTO idTipoDoc = scsDocumentoEjgExtendsMapper.getIdDocumentoEjg(usuario.getIdinstitucion());
						if (idTipoDoc == null) {
							nuevoDoc.setIddocumentoejg((short) 1);
						} else {
							idTipoDocNuevo = (short) (Integer.parseInt(idTipoDoc.getNewId()) + 1);
							nuevoDoc.setIddocumentoejg(idTipoDocNuevo);
						}

						genRecursosCatalogos = new GenRecursosCatalogos();
						genRecursosCatalogos.setDescripcion(documentacionejgItem.getdescripcionDoc());
						genRecursosCatalogos.setFechamodificacion(new Date());
						genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
						genRecursosCatalogos.setIdinstitucion(idInstitucion);
						genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());
						genRecursosCatalogos.setIdrecursoalias("SCS_DOCUMENTOEJG.DESCRIPCION." + idInstitucion + "."
								+ genRecursosCatalogos.getIdrecurso());
						genRecursosCatalogos.setNombretabla("SCS_DOCUMENTOEJG");
						genRecursosCatalogos.setCampotabla("DESCRIPCION");
						NewIdDTO idJD = genRecursosCatalogosExtendsMapper
								.getMaxIdRecursoCatalogo(idInstitucion.toString(), usuario.getIdlenguaje());
						if (idJD == null) {
							genRecursosCatalogos.setIdrecurso("1");

						} else {
							genRecursosCatalogos.setIdrecurso((Long.parseLong(idJD.getNewId()) + 1) + "");
							nuevoDoc.setDescripcion(genRecursosCatalogos.getIdrecurso());
						}
						insertResponseDTO.setId(nuevoDoc.getIddocumentoejg().toString());

						LOGGER.info(
								"insertFundamentos() / scsDocumentoEjgExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para insertar el nuevo documento");

						LOGGER.info(
								"insertFundamentos() / scsDocumentoEjgExtendsMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo documento");

						genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
						insertarRestoIdiomas(genRecursosCatalogos);

						LOGGER.info(
								"insertFundamentos() / scsDocumentoEjgExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para insertar el nuevo documento");
						// }

						nuevoDoc.setAbreviatura(documentacionejgItem.getAbreviatura());
						nuevoDoc.setCodigoext(documentacionejgItem.getCodigoExt());
						nuevoDoc.setIdinstitucion(idInstitucion);
						nuevoDoc.setIdtipodocumentoejg(Short.valueOf(documentacionejgItem.getIdTipoDocumento()));
						nuevoDoc.setUsumodificacion(usuario.getIdusuario());
						nuevoDoc.setFechamodificacion(new Date());

						LOGGER.info(
								"createTipoDoc() / scsDocumentoEjgExtendsMapper.insert() -> Entrada a scsDocumentacionEjgExtendsMapper para insertar un nuevo documento");

						response = scsDocumentoEjgExtendsMapper.insert(nuevoDoc);

						LOGGER.info(
								"createTipoDoc() / scsDocumentacionEjgExtendsMapper.insert() -> Salida de scsDocumentacionEjgExtendsMapper para insertar un nuevo documento");
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idTipoDocNuevo)));
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuvo tipo documento");
		return insertResponseDTO;
	}
}