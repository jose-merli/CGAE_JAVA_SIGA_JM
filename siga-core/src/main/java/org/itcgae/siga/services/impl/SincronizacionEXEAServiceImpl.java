package org.itcgae.siga.services.impl;

import com.exea.sincronizacion.redabogacia.ErrorType;
import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionRequestDocument;
import com.exea.sincronizacion.redabogacia.ObtenerNumColegiacionResponseDocument;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.exception.ValidationException;
import org.itcgae.siga.services.ISincronizacionEXEAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SincronizacionEXEAServiceImpl implements ISincronizacionEXEAService {

    private Logger log = LoggerFactory.getLogger(SincronizacionEXEAServiceImpl.class);

    @Autowired
    private WSCommons wsCommons;

    @Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;

    /**
     * Metodo que recibe una peticion {@link ObtenerNumColegiacionResponseDocument} y, tras varias validaciones, devuelve o no el proximo numero de colegiado
     *
     * tipoColegiacion (1, 2, 3)
     * tipoSolicitud(E, N, I)
     *
     * @param requestDocument
     * @return
     */
    @Override
    public ObtenerNumColegiacionResponseDocument getNumColegiacion(ObtenerNumColegiacionRequestDocument requestDocument, String ipCliente) {

        ObtenerNumColegiacionResponseDocument responseDocument = ObtenerNumColegiacionResponseDocument.Factory.newInstance();
        ObtenerNumColegiacionResponseDocument.ObtenerNumColegiacionResponse response = responseDocument.addNewObtenerNumColegiacionResponse();
        ObtenerNumColegiacionRequestDocument.ObtenerNumColegiacionRequest request = requestDocument.getObtenerNumColegiacionRequest();
        boolean yaDadoAlta = false;
        try {
            wsCommons.validaPeticion(request, response, true);
            String idInstitucion = request.getColegio().getCodigoColegio();
            String identificacion = "";
            Long idPersona = null;
            FichaPersonaItem fichaPersonaItem = null;

            if(!UtilidadesString.esCadenaVacia(request.getIdentificacion().getNIF())){
                identificacion = request.getIdentificacion().getNIF();
            }else if(!UtilidadesString.esCadenaVacia(request.getIdentificacion().getNIE())){
                identificacion = request.getIdentificacion().getNIE();
            }else {
                identificacion = request.getIdentificacion().getPasaporte();
            }

            idPersona = cenPersonaExtendsMapper.getIdPersonaWithNif(identificacion);

            //Puede estar registrado en censo como persona, pero no ser colegiado. Lo siguiente es comprobar si ya es un colegiado
            if(idPersona != null){

               fichaPersonaItem = cenPersonaExtendsMapper.getColegiadoByIdPersona(idPersona.toString(),Short.valueOf(idInstitucion));

               if(fichaPersonaItem != null){
                   ErrorType errorType = response.addNewError();
                   errorType.setCodigo("COLEGIADO_ENCONTRADO");
                   errorType.setDescripcion("El documento de identificación ya está dado de alta como colegiado");
                   yaDadoAlta = true;
               }
            }

            //Si no está dado de alta como colegiado procedemos a
            if(!yaDadoAlta){

            }

        } catch (ValidationException validationException) {
            log.error("Error: XML NO VALIDO", validationException);
        }


        return responseDocument;
    }

}
