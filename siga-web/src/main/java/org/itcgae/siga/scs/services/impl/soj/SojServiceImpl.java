package org.itcgae.siga.scs.services.impl.soj;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.FichaSojDTO;
import org.itcgae.siga.DTOs.scs.FichaSojItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.scs.services.soj.ISojService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SojServiceImpl implements ISojService {

    private Logger LOGGER = Logger.getLogger(SojServiceImpl.class);

    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;

    @Autowired
    private ScsSojExtendsMapper scsSojExtendsMapper;

    @Override
    public FichaSojDTO getDetallesSoj(FichaSojItem fichaSojItem, HttpServletRequest request) throws Exception {
        LOGGER.info("getDetallesSoj() -> Entrando al servicio que obtiene los datos de un SOJ");
        FichaSojDTO fichaSojDTO = new FichaSojDTO();
        Error error = new Error();

        // Conseguimos información del usuario logeado
        LOGGER.info("getDetallesSoj() -> Entrando al servicio de autenticación");
        AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
        LOGGER.info("getDetallesSoj() -> Saliendo del servicio de autenticación");

        fichaSojDTO.setFichaSojItems(scsSojExtendsMapper.busquedaSoj(fichaSojItem));
        LOGGER.info("getDetallesSoj() -> Saliendo del servicio que obtiene los datos de un SOJ");
        return fichaSojDTO;
    }

}
