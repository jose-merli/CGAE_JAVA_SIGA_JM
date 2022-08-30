package org.itcgae.siga.scs.services.impl.ejg;

import org.itcgae.siga.DTO.fac.FicherosAdeudosDTO;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.scs.services.ejg.IEJGIntercambios;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EJGIntercambios implements IEJGIntercambios {

    @Override
    public FicherosAdeudosDTO getFicherosAdeudos(FicherosAdeudosItem item, HttpServletRequest request)
            throws Exception {
        FicherosAdeudosDTO ficherosAdeudosDTO = new FicherosAdeudosDTO();
        AdmUsuarios usuario = new AdmUsuarios();

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosAdeudos() -> Entrada al servicio para obtener los ficheros de adeudos");

        // Conseguimos información del usuario logeado
        usuario = authenticationProvider.checkAuthentication(request);

        if (usuario != null) {
            LOGGER.info("FacturacionPySExportacionesServiceImpl.getFicherosAdeudos() -> obteniendo datos de ficheros de adeudos");

            Integer tamMaximo = getTamanoMaximo(usuario.getIdinstitucion());

            List<FicherosAdeudosItem> items = facDisquetecargosExtendsMapper.getFicherosAdeudos(item,
                    usuario.getIdinstitucion().toString(), tamMaximo);

            ficherosAdeudosDTO.setFicherosAdeudosItems(items);
        }

        LOGGER.info(
                "FacturacionPySExportacionesServiceImpl.getFicherosAdeudos() -> Salida del servicio  para obtener los ficheros de adeudos");

        return ficherosAdeudosDTO;
    }

}
