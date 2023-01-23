package org.itcgae.siga.scs.services.impl.maestros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.PretensionDTO;
import org.itcgae.siga.DTOs.scs.PretensionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsPretension;
import org.itcgae.siga.db.entities.ScsPretensionExample;
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsProcedimientosExample;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPretensionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcedimientosExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IPretensionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PretensionesServiceImpl implements IPretensionesService {

          private Logger LOGGER = Logger.getLogger(PretensionesServiceImpl.class);

          @Autowired
          private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

          @Autowired
          private ScsPretensionExtendsMapper scsPretensionExtendsMapper;

          @Autowired
          private ScsProcedimientosExtendsMapper scsProcedimientosExtendsMapper;

          @Autowired
          private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
          
          @Autowired
          private GenRecursosCatalogosMapper genRecursosCatalogosMapper;

          @Override
          public PretensionDTO searchPretensiones(PretensionItem pretensionItem, HttpServletRequest request) {
                    LOGGER.info("searchPrisiones() -> Entrada al servicio para obtener prisiones");

                    String token = request.getHeader("Authorization");
                    String dni = UserTokenUtils.getDniFromJWTToken(token);
                    Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
                    PretensionDTO pretensionDTO = new PretensionDTO();
                    List<PretensionItem> pretensionItemList = null;

                    if (idInstitucion != null) {
                              AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                    exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                              LOGGER.info(
                                                 "searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                              List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                              LOGGER.info(
                                                 "searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                              if (usuarios != null && usuarios.size() > 0) {

                                       LOGGER.info(
                                                           "searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Entrada a scsProcuradorExtendsMapper para obtener los procuradores");

                                        pretensionItem.setIdInstitucion(idInstitucion.toString());
                                       pretensionItemList = scsPretensionExtendsMapper.searchPretensiones(usuarios.get(0).getIdlenguaje(),
                                                           pretensionItem);

                                       LOGGER.info(
                                                           "searchProcuradores() / scsProcuradorExtendsMapper.searchProcuradores() -> Salida a scsProcuradorExtendsMapper para obtener los procuradores");

                                       if (pretensionItemList != null) {
                                                 pretensionDTO.setPretensionItems(pretensionItemList);
                                       }
                              }

                    }
                    LOGGER.info("searchComisarias() -> Salida del servicio para obtener prisiones");
                    return pretensionDTO;
          }

          @Override
          @Transactional
          public UpdateResponseDTO deletePretensiones(PretensionDTO pretensionDTO, HttpServletRequest request) {

                    LOGGER.info("deletePretensiones() ->  Entrada al servicio para eliminar prisiones");

                    UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
                    Error error = new Error();
                    int response = 2;

                    String token = request.getHeader("Authorization");
                    String dni = UserTokenUtils.getDniFromJWTToken(token);
                    Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

                    if (null != idInstitucion) {

                              AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                    exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                              LOGGER.info(
                                                 "deletePretensiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                              List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                              LOGGER.info(
                                                 "deletePretensiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                              if (null != usuarios && usuarios.size() > 0) {
                                       AdmUsuarios usuario = usuarios.get(0);

                                       try {

                                                 for (PretensionItem pretensionItem : pretensionDTO.getPretensionItems()) {

                                                           ScsPretensionExample scsPretensionExample = new ScsPretensionExample();
                                                           scsPretensionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                                                               .andIdpretensionEqualTo(Short.valueOf(pretensionItem.getIdPretension()));

                                                           LOGGER.info(
                                                                               "deletePretensiones() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

                                                           List<ScsPretension> pretensionesList = scsPretensionExtendsMapper
                                                                               .selectByExample(scsPretensionExample);

                                                           LOGGER.info(
                                                                               "deletePretensiones() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

                                                           if (null != pretensionesList && pretensionesList.size() > 0) {

                                                                     ScsPretension pretension = pretensionesList.get(0);

                                                                     pretension.setFechabaja(new Date());
                                                                     pretension.setFechamodificacion(new Date());
                                                                     pretension.setUsumodificacion(usuario.getIdusuario());
                                                                     pretension.setIdinstitucion(idInstitucion);
                                                                     LOGGER.info(
                                                                                         "deletePretensiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

                                                                     response = scsPretensionExtendsMapper.updateByPrimaryKey(pretension);

                                                                     LOGGER.info(
                                                                                         "deletePretensiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
                                                           }
                                                 }

                                       } catch (Exception e) {
                                                 LOGGER.error(e);
                                                 response = 0;
                                                  error.setCode(400);
                                                 error.setDescription("general.mensaje.error.bbdd");
                                                 updateResponseDTO.setStatus(SigaConstants.KO);
                                       }
                              }

                    }

                    if (response == 0) {
                              error.setCode(400);
                              updateResponseDTO.setStatus(SigaConstants.KO);
                    } else {
                              error.setCode(200);
                              updateResponseDTO.setStatus(SigaConstants.OK);
                    }

                    updateResponseDTO.setError(error);

                    LOGGER.info("deletePretensiones() -> Salida del servicio para eliminar prisiones");

                    return updateResponseDTO;

          }

          @Override
          @Transactional
          public UpdateResponseDTO activatePretensiones(PretensionDTO pretensionDTO, HttpServletRequest request) {

                    LOGGER.info("activatePretensiones() ->  Entrada al servicio para dar de alta a prisiones");

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
                                                 "activatePretensiones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                              List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                              LOGGER.info(
                                                 "activatePretensiones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
                              try {
                                       if (null != usuarios && usuarios.size() > 0) {
                                                 AdmUsuarios usuario = usuarios.get(0);

                                                 for (PretensionItem pretensionItem : pretensionDTO.getPretensionItems()) {

                                                           ScsPretensionExample scsPretensionExample = new ScsPretensionExample();
                                                           scsPretensionExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                                                               .andIdpretensionEqualTo(Short.valueOf(pretensionItem.getIdPretension()));

                                                           LOGGER.info(
                                                                               "activatePretensiones() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prision");

                                                           List<ScsPretension> pretensionesList = scsPretensionExtendsMapper
                                                                               .selectByExample(scsPretensionExample);

                                                           LOGGER.info(
                                                                               "activatePretensiones() / scsPrisionExtendsMapper.selectByExample() -> Salida de scsPrisionExtendsMapper para buscar la prision");

                                                           if (null != pretensionesList && pretensionesList.size() > 0) {

                                                                     ScsPretension pretension = pretensionesList.get(0);

                                                                     pretension.setFechabaja(null);
                                                                     pretension.setFechamodificacion(new Date());
                                                                     pretension.setUsumodificacion(usuario.getIdusuario());

                                                                     LOGGER.info(
                                                                                         "activatePretensiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Entrada a scsPrisionExtendsMapper para dar de baja a una prision");

                                                                     response = scsPretensionExtendsMapper.updateByPrimaryKey(pretension);

                                                                     LOGGER.info(
                                                                                         "activatePretensiones() / scsPrisionExtendsMapper.updateByPrimaryKey() -> Salida de scsPrisionExtendsMapper para dar de baja a una prision");
                                                           }

                                                 }
                                       }
                              } catch (Exception e) {
                                       LOGGER.error(e);
                                       response = 0;
                                       error.setCode(400);
                                       error.setDescription("general.mensaje.error.bbdd");
                                       updateResponseDTO.setStatus(SigaConstants.KO);
                              }
                    }

                    if (response == 0) {
                              error.setCode(400);
                              updateResponseDTO.setStatus(SigaConstants.KO);
                    } else {
                              error.setCode(200);
                              updateResponseDTO.setStatus(SigaConstants.OK);
                    }

                    updateResponseDTO.setError(error);

                    LOGGER.info("activatePretensiones() -> Salida del servicio para dar de alta a las prisiones");

                    return updateResponseDTO;

          }

          @Override
          public UpdateResponseDTO updatePretension(PretensionDTO pretensionDTO, HttpServletRequest request) {

                    LOGGER.info("updatePretension() ->  Entrada al servicio para editar prision");

                    UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
                    Error error = new Error();
                    int response = 2;

                    String token = request.getHeader("Authorization");
                    String dni = UserTokenUtils.getDniFromJWTToken(token);
                    Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

                    if (null != idInstitucion) {

                              AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                    exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                              LOGGER.info(
                                                 "updatePretension() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                             List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                              LOGGER.info(
                                                 "updatePretension() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                              if (null != usuarios && usuarios.size() > 0) {
                                       AdmUsuarios usuario = usuarios.get(0);

                                       for (PretensionItem pretensionItem : pretensionDTO.getPretensionItems()) {

                                                 try {

                                                           ScsPretensionExample example = new ScsPretensionExample();
                                                 example.createCriteria().andIdpretensionEqualTo(Short.valueOf(pretensionItem.getIdPretension())).andIdinstitucionEqualTo(idInstitucion);
                                                                               

                                                           List<ScsPretension> scsPretensionListAux = scsPretensionExtendsMapper.selectByExample(example);
                                                           ScsPretension newPretension = scsPretensionListAux.get(0); 
                                                           GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
                                                 genRecursosCatalogosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                                                               .andIdrecursoNotEqualTo(newPretension.getDescripcion())
                                                                               .andDescripcionEqualTo(pretensionItem.getDescripcion())
                                                                     .andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("SCS_PRETENSION");
                                                           List<GenRecursosCatalogos> scsPretensionesList = genRecursosCatalogosMapper
                                                                               .selectByExample(genRecursosCatalogosExample);
                                                           
                                                           LOGGER.info("Idrecurso consulta = " + newPretension.getDescripcion());
                                                           LOGGER.info("Descripcion consulta = " + pretensionItem.getDescripcion());
                                                           LOGGER.info(
                                                                               "updateRetenciones() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");
                                                          
                                                           if ((scsPretensionesList != null && scsPretensionesList.size() > 0)) {
                                                        	   LOGGER.info("Entramos a devolver error de pretensiones > 0");
                                                        	   LOGGER.info("tamaño procedimientos: " +  scsPretensionesList.size());
                                                                     error.setCode(400);
                                                           error.setDescription("messages.jgr.maestros.pretension.existeProcedimientoMismoNombre");
                                                           } else {

                                                                     // Obtenemos la pretension que queremos modificar
                                                                     example = new ScsPretensionExample();
                                                                     example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                                                                         .andIdpretensionEqualTo(Short.valueOf(pretensionItem.getIdPretension()))
                                                                                         .andFechabajaIsNull();

                                                                     LOGGER.info(
                                                                                         "updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

                                                                     List<ScsPretension> scsPretensionList = scsPretensionExtendsMapper.selectByExample(example);

                                                                     LOGGER.info(
                                                                                         "updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

                                                                     if (scsPretensionList != null && scsPretensionList.size() > 0) {

                                                                               LOGGER.info(
                                                                                                   "updateFundamentosCalificacion() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Entrada a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

                                                                               LOGGER.info(
                                                                                                   "updateFundamentosCalificacion() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Salida a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

                                                                               ScsPretension pretension = scsPretensionList.get(0);

                                                                               pretension.setCodigoext(pretensionItem.getCodigoExt());
                                                                               pretension.setFechamodificacion(new Date());
                                                                               pretension.setUsumodificacion(usuario.getIdusuario().intValue());
                                                                               if (pretensionItem.getIdJurisdiccion() != null)
                                                                                    pretension.setIdjurisdiccion(Short.valueOf(pretensionItem.getIdJurisdiccion()));

                                                                               GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
                                                                               genRecursosCatalogos.setIdrecurso(pretension.getDescripcion());
                                                                               genRecursosCatalogos.setIdinstitucion(idInstitucion);
                                                                               genRecursosCatalogos.setIdlenguaje(usuarios.get(0).getIdlenguaje());
                                                                               genRecursosCatalogos = genRecursosCatalogosExtendsMapper
                                                                                                   .selectByPrimaryKey(genRecursosCatalogos);
                                                                               genRecursosCatalogos.setFechamodificacion(new Date());
                                                                             genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario().intValue());
                                                                               genRecursosCatalogos.setDescripcion(pretensionItem.getDescripcion());
                                                                               response = scsPretensionExtendsMapper.updateByPrimaryKey(pretension);

                                                                     genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);
                                                                               updateRestoIdiomas(genRecursosCatalogos);

                                                                               updateResponseDTO.setId(pretension.getIdpretension().toString());
                                                                     }
                                                           }
                                                 } catch (Exception e) {
                                                           LOGGER.error(e);
                                                           response = 0;
                                                           error.setCode(400);
                                                           error.setDescription("general.mensaje.error.bbdd");
                                                           updateResponseDTO.setStatus(SigaConstants.KO);
                                                 }
                                       }
                              }

                    }

                    if (response == 0) {
                              error.setCode(400);
                              error.setDescription("general.mensaje.error.bbdd");
                              updateResponseDTO.setStatus(SigaConstants.KO);
                    } else if (response == 1) {
                              error.setCode(200);
                              updateResponseDTO.setStatus(SigaConstants.OK);

                    }

                    updateResponseDTO.setError(error);

                    LOGGER.info("updatePretension() -> Salida del servicio para editar pretension");

                    return updateResponseDTO;

          }

          @Override
          public InsertResponseDTO createPretension(PretensionItem pretensionItem, HttpServletRequest request) {

                    LOGGER.info("createPretension() ->  Entrada al servicio para crear una nueva prisión");

                    InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
                    Error error = new Error();
                    int response = 0;

                    String token = request.getHeader("Authorization");
                    String dni = UserTokenUtils.getDniFromJWTToken(token);
                    Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
                    long idPretension = 0;

                    if (null != idInstitucion) {

                              AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                    exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                              LOGGER.info(
                                                 "createPretension() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                              List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                              LOGGER.info(
                                                 "createPretension() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                              if (null != usuarios && usuarios.size() > 0) {
                                       AdmUsuarios usuario = usuarios.get(0);

                                       try {

                                                 LOGGER.info(
                                                                     "createPretension() / scsFundamentoscalificacionExtendsMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo juzgado");

                                                 
                                                 GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
                                       genRecursosCatalogosExample.createCriteria().andDescripcionEqualTo(pretensionItem.getDescripcion())
                                                 .andCampotablaEqualTo("DESCRIPCION")
                                                 .andNombretablaEqualTo("SCS_PRETENSION")
                                                 .andIdinstitucionEqualTo(idInstitucion);
                                                                     
                                                 List<GenRecursosCatalogos> l = genRecursosCatalogosExtendsMapper
                                                                     .selectByExample(genRecursosCatalogosExample);

                                                 LOGGER.info(
                                                                     "createPretension() / scsPrisionExtendsMapper.selectByExample() -> Entrada a scsPrisionExtendsMapper para buscar la prisión");

                                                 LOGGER.info(
                                                                     "createPretension() / scsPrisionExtendsMapper.selectByExample() -> Salida a scsPrisionExtendsMapper para buscar la prisión");

                                                 if (l != null && l.size() > 0) {
                                                           error.setCode(400);
                                                 error.setDescription("messages.jgr.maestros.pretension.existeProcedimientoMismoNombre");
                                                           insertResponseDTO.setStatus(SigaConstants.KO);
                                                 } else {

                                                           ScsPretension pretension = new ScsPretension();

                                                           pretension.setFechabaja(null);
                                                           pretension.setFechamodificacion(new Date());
                                                           pretension.setUsumodificacion(usuario.getIdusuario().intValue());
                                                           pretension.setIdinstitucion(idInstitucion);
                                                           pretension.setDescripcion(pretensionItem.getDescripcion());
                                                           pretension.setCodigoext(pretensionItem.getCodigoExt());
                                                           pretension.setBloqueado("N");
                                                      pretension.setIdjurisdiccion(Short.valueOf(pretensionItem.getIdJurisdiccion()));

                                                           GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
                                                           genRecursosCatalogos.setDescripcion(pretensionItem.getDescripcion());
                                                           genRecursosCatalogos.setFechamodificacion(new Date());
                                                           genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
                                                           genRecursosCatalogos.setIdinstitucion(idInstitucion);
                                                           genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());

                                                           genRecursosCatalogos.setNombretabla("SCS_PRETENSION");
                                                           genRecursosCatalogos.setCampotabla("DESCRIPCION");
                                                           NewIdDTO idRC = genRecursosCatalogosExtendsMapper
                                                                               .getMaxIdRecursoCatalogo(idInstitucion.toString(), usuario.getIdlenguaje());
                                                           NewIdDTO idP = scsPretensionExtendsMapper.getIdPretension(idInstitucion);

                                                           if (idP == null) {
                                                                     pretension.setIdpretension((short) 1);
                                                           } else {
                                                                     idPretension = (short) (Short.valueOf(idP.getNewId()) + 1);
                                                                     pretension.setIdpretension((short) idPretension);
                                                           }
                                                           if (idRC == null) {
                                                                     genRecursosCatalogos.setIdrecurso("1");

                                                           } else {
                                                                     genRecursosCatalogos.setIdrecurso((Long.parseLong(idRC.getNewId()) + 1) + "");
                                                                     genRecursosCatalogos.setIdrecursoalias("SCS_PRETENSION.descripcion." + idInstitucion + "."
                                                                                         + genRecursosCatalogos.getIdrecurso());
                                                                     pretension.setDescripcion(genRecursosCatalogos.getIdrecurso());
                                                           }

                                                           LOGGER.info(
                                                                               "createPretension() / scsPrisionExtendsMapper.insert() -> Entrada a scsPrisionExtendsMapper para insertar la nueva prision");

                                                           response = scsPretensionExtendsMapper.insert(pretension);
                                                           insertResponseDTO.setId(pretension.getIdpretension().toString());

                                                           genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
                                                           insertarRestoIdiomas(genRecursosCatalogos);
                                                           LOGGER.info(
                                                                               "createPretension() / scsPrisionExtendsMapper.insert() -> Salida de scsPrisionExtendsMapper para insertar la nueva prision");

                                                 }
                                       

                                       } catch (Exception e) {
                                                 LOGGER.error(e);
                                                 response = 0;
                                                 error.setCode(400);
                                                 error.setDescription("general.mensaje.error.bbdd");
                                                 insertResponseDTO.setStatus(SigaConstants.KO);
                                       }
                              }

                    }

                    if (response == 0 && error.getDescription() == null) {
                              error.setCode(400);
                              insertResponseDTO.setStatus(SigaConstants.KO);
                    } else if (error.getCode() == null) {
                              error.setCode(200);
                              insertResponseDTO.setId(String.valueOf(idPretension));
                              insertResponseDTO.setStatus(SigaConstants.OK);
                    }

                    insertResponseDTO.setError(error);

                    LOGGER.info("createPretension() -> Salida del servicio para crear una nueva prisión");

                    return insertResponseDTO;

          }

          @Override
          public ComboDTO getProcedimientos(HttpServletRequest request) {

                    ComboDTO procedimientosReturn = new ComboDTO();
                    List<ScsProcedimientos> procedimientos = new ArrayList<ScsProcedimientos>();
                    String token = request.getHeader("Authorization");
                    Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

                    ScsProcedimientosExample scsProcedimientosExample = new ScsProcedimientosExample();
          scsProcedimientosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                    procedimientos = scsProcedimientosExtendsMapper.selectByExample(scsProcedimientosExample);

                    if (null != procedimientos && procedimientos.size() > 0) {
                              List<ComboItem> combooItems = new ArrayList<ComboItem>();
                              ComboItem comboItem = new ComboItem();
                              for (ScsProcedimientos scsProcedimientos : procedimientos) {
                                       comboItem = new ComboItem();
                                       if(scsProcedimientos.getCodigoext() != null) {
                                    	   comboItem.setLabel("("+scsProcedimientos.getCodigoext()+") " + scsProcedimientos.getNombre());
                                       }else {
                                    	   comboItem.setLabel(scsProcedimientos.getNombre());
                                       }
                                       comboItem.setValue(scsProcedimientos.getIdprocedimiento());
                                       combooItems.add(comboItem);
                              }

                              procedimientosReturn.setCombooItems(combooItems);
                    }

                    return procedimientosReturn;
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

}

