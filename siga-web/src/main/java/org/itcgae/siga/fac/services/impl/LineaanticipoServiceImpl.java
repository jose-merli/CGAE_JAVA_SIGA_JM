package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroMonederoItem;
import org.itcgae.siga.DTO.fac.ListaMonederoDTO;
import org.itcgae.siga.DTO.fac.MonederoDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.PysLineaanticipoExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.fac.services.ILineaanticipoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class LineaanticipoServiceImpl implements ILineaanticipoService {

    private Logger LOGGER = Logger.getLogger(LineaanticipoServiceImpl.class);

    @Autowired
    private PysLineaanticipoExtendsMapper lineaanticipoMapper;

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private PysLineaanticipoExtendsMapper lineaanticipoExtendsMapper;

    @Autowired
    private CenPersonaMapper cenPersonaMapper;


    @Override
    public ListaMonederoDTO listarMonederos(HttpServletRequest request, FiltroMonederoItem filtroMonederoItem) {

        Error error = new Error();
        // Conseguimos información del usuario logeado
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short institutionId = UserTokenUtils.getInstitucionFromJWTToken(token);
        String counsel = UserTokenUtils.getLetradoFromJWTToken(token); // letrado

        if (institutionId != null) {
            AdmUsuariosExample userQuery = new AdmUsuariosExample();
            userQuery.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(institutionId);
            LOGGER.info("getFichaCompraSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
            List<AdmUsuarios> users = admUsuariosExtendsMapper.selectByExample(userQuery);
            LOGGER.info("getFichaCompraSuscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");


            if (users != null && !users.isEmpty()) {
                //Si es colegiado se debe coger el idpersona del usuario conectado
                if (!counsel.equals("N")) {
                    CenPersonaExample peopleQuery = new CenPersonaExample();
                    peopleQuery.createCriteria().andNifcifEqualTo(dni);
                    List<CenPersona> people = cenPersonaMapper.selectByExample(peopleQuery);
                    Long personId = people.get(0).getIdpersona();
                    List<MonederoDTO> walletDTOs = lineaanticipoExtendsMapper.selectByPersonIdAndCreationDate(institutionId, personId, filtroMonederoItem);
                    ListaMonederoDTO walletListDTO = new ListaMonederoDTO();
//                    walletListDTO.setMonederoItems(walletDTOs);
                    error.setCode(200);
//                    walletListDTO.setError(error);

                    return walletListDTO;

                }
            }
        }
        return null;
    }
}
