package org.itcgae.siga.exea.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteDTO;
import org.itcgae.siga.DTOs.exea.ExpedienteItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpProcedimientosExea;
import org.itcgae.siga.db.entities.ExpProcedimientosExeaExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpExpedientesExtendsMapper;
import org.itcgae.siga.db.services.exp.mappers.ExpProcedimientosExeaExtendsMapper;
import org.itcgae.siga.exea.services.ExpedientesEXEAService;
import org.itcgae.siga.scs.services.impl.guardia.AsistenciaServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientExpedientesEXEA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ExpedientesEXEAServiceImpl implements ExpedientesEXEAService {

    private final Logger LOGGER = Logger.getLogger(ExpedientesEXEAServiceImpl.class);

    @Autowired
    private ExpProcedimientosExeaExtendsMapper expProcedimientosExeaExtendsMapper;

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private ExpExpedientesExtendsMapper expExpedientesExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

    @Autowired
    private ClientExpedientesEXEA _clientExpedientesEXEA;

    @Override
    public StringDTO isEXEActivoInstitucion(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        StringDTO stringDTO = new StringDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "isEXEActivoInstitucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "isEXEActivoInstitucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    ExpProcedimientosExeaExample expProcedimientosExeaExample = new ExpProcedimientosExeaExample();
                    expProcedimientosExeaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);
                    List<ExpProcedimientosExea> procedimientosExea = expProcedimientosExeaExtendsMapper.selectByExample(expProcedimientosExeaExample);

                    //Si la institucion no tiene procedimientos de EXEA o no tiene ninguno que sea de colegiacion
                    if(procedimientosExea != null
                            && !procedimientosExea.isEmpty()
                            && procedimientosExea.stream().anyMatch(procedimiento -> 1 == procedimiento.getEsColegiacion())){
                        stringDTO.setValor(SigaConstants.DB_TRUE);
                    }else{
                        stringDTO.setValor(SigaConstants.DB_FALSE);
                    }

                }
            }
        }catch(Exception e){
            LOGGER.error("isEXEActivoInstitucion() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al consultar si la institucion tiene tramites activos con EXEA");
            error.description("Error al consultar si la institucion tiene tramites activos con EXEA");
        }
        return stringDTO;
    }

    @Override
    public ExpedienteDTO getExpedientesSIGAColegiado(HttpServletRequest request, String idPersona) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        Error error = new Error();
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "getExpedientesSIGAColegiado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getExpedientesSIGAColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    List<ExpedienteItem> expedienteItems = expExpedientesExtendsMapper.getExpedientesSigaColegiado(idInstitucion, idPersona);
                    expedienteDTO.setExpedienteItem(expedienteItems);

                }
            }
        }catch(Exception e){
            LOGGER.error("getExpedientesSIGAColegiado() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al buscar los expedientes del colegiado");
            error.description("Error al buscar los expedientes del colegiado");
            expedienteDTO.setError(error);
        }
        return expedienteDTO;
    }

    @Override
    public StringDTO getTokenLoginEXEA(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        StringDTO stringDTO = new StringDTO();
        Error error = new Error();
        String idAplicacion, idSede, cdgoExternoInstitucion;
        try {
            if (idInstitucion != null) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "getTokenLoginEXEA() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

                LOGGER.info(
                        "getTokenLoginEXEA() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (usuarios != null && usuarios.size() > 0) {

                    idSede = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.ID_SEDE_PARAM, idInstitucion.toString()).getValor();

                    idAplicacion = genParametrosExtendsMapper.selectParametroPorInstitucion(SigaConstants.ID_APLICACION_PARAM, idInstitucion.toString()).getValor();

                    cdgoExternoInstitucion = cenInstitucionExtendsMapper.selectByPrimaryKey(idInstitucion).getCodigoext();

                    if(!UtilidadesString.esCadenaVacia(idSede) && UtilidadesString.esCadenaVacia(idAplicacion)
                        && !"NULL".equals(idSede) && !"NULL".equals(idAplicacion)){
                        //TODO: Llamada a EXEA obtencion token
                        stringDTO.setValor("Bearer token valido");
                    }
                }
            }
        }catch(Exception e){
            LOGGER.error("getTokenLoginEXEA() / ERROR: " + e.getMessage(), e);
            error.setCode(500);
            error.setMessage("Error al obtener el token de EXEA");
            error.description("Error al obtener el token de EXEA");
        }
        return stringDTO;
    }

}
