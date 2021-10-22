package org.itcgae.siga.commons.utils;

import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorContadores {

    @Autowired
    private AdmContadorExtendsMapper admContadorExtendsMapper;

    private void validarLongitudContador(int contadorSiguiente, AdmContador contador) throws FacturacionSJCSException {

        try {
            String contadorlongitud = Integer.toString(contadorSiguiente);
            Integer longitud = contador.getLongitudcontador();
            if (contadorlongitud.length() > longitud.intValue()) {
                if (contador.getModo().toString().equals("1")) {
                    throw new FacturacionSJCSException("Ya existe el número de registro y al sugerir un nuevo contador se ha violado la longitud máxima configurada para él.");
                } else {
                    throw new FacturacionSJCSException("Se ha superado la máxima longitud para el número contador");
                }
            }
        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al modificar los datos del contador", e);
        }
    }

    /**
     * Metodo que devuelve el siguiente contador siguiente al insertado en la tabla ADM_CONTADOR sin
     * validar su unicidad en el sistema
     */
    private String getNuevoContador(AdmContador contador) throws FacturacionSJCSException {

        String contadorFinalSugerido = "";
        try {
            int contadorNuevo = Integer.parseInt(contador.getContador().toString()) + 1;
            Integer contadorSugerido = new Integer(contadorNuevo);

            // Comprobamos que el contador que se sugiere no supera la longitud definida
            validarLongitudContador(contadorNuevo, contador);

            Integer longitud = contador.getLongitudcontador();
            int longitudContador = longitud.intValue();

            contadorFinalSugerido = UtilidadesString.formatea(contadorSugerido, longitudContador, true);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener nuevo contador", e);
        }
        return contadorFinalSugerido;
    }

    private String getNuevoContadorConPrefijoSufijo(AdmContador contenidoAdmContador) throws FacturacionSJCSException {
        String contadorFinalSugerido = "";
        try {
            String numeroAbono = getNuevoContador(contenidoAdmContador);
            contadorFinalSugerido = contenidoAdmContador.getPrefijo() + numeroAbono + contenidoAdmContador.getSufijo();
        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener nuevo contador con prefijo y sufijo", e);
        }
        return contadorFinalSugerido;
    }

    private AdmContador getContador(Short idInstitucion, String idContador) {

        AdmContadorKey admContadorKey = new AdmContadorKey();
        admContadorKey.setIdinstitucion(idInstitucion);
        admContadorKey.setIdcontador(idContador);

        return admContadorExtendsMapper.selectByPrimaryKey(admContadorKey);
    }

    private void setContador(AdmContador datosContadorNuevo, String numRegNuevo) throws FacturacionSJCSException {
        // registro contador que se obtiene de la tabla ADM_CONTADORES con el
        // idinstitucion y el idcontador
        AdmContador gcOriginal = null;

        try {
            gcOriginal = getContador(datosContadorNuevo.getIdinstitucion(), datosContadorNuevo.getIdcontador());
            // Solo actualizamos la tabla Adm_Contador si el contador introducido por
            // pantalla es mayor que el almacenado en la tabla de contadores
            Long numReg = new Long(numRegNuevo);

            if (numReg.longValue() > gcOriginal.getContador()) {
                datosContadorNuevo.setContador(Long.valueOf(numRegNuevo));

                if ((gcOriginal.getPrefijo().equals(datosContadorNuevo.getPrefijo())) && (gcOriginal.getSufijo().equals(datosContadorNuevo.getSufijo()))) {
                    // Solo en el caso de que el usuario no ha modificado el prefijo y sufijo que se
                    // le propone cuando se inserta una nueva sociedad, entonces
                    // se actualiza el campo contador en la tabla ADM_CONTADORES con el ultimo utilizado
                    admContadorExtendsMapper.updateByPrimaryKeySelective(datosContadorNuevo);
                }
            }

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al modificar los datos del contador", e);
        }
    }

}
