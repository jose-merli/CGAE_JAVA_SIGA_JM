package org.itcgae.siga.commons.utils;

import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorContadores {

    @Autowired
    private AdmContadorExtendsMapper admContadorExtendsMapper;

    public AdmContador getContador(Short idInstitucion, String idContador) {
        return getContador(idInstitucion, idContador, null, null);
    }

    public AdmContador getContador(Short idInstitucion, String idContador, String prefijo, String sufijo) {

        AdmContadorExample admContadorExample = new AdmContadorExample();
        AdmContadorExample.Criteria criteria = admContadorExample.createCriteria();
        criteria.andIdinstitucionEqualTo(idInstitucion).andIdcontadorEqualTo(idContador);

        if (!UtilidadesString.esCadenaVacia(prefijo)) {
            criteria.andPrefijoEqualTo(prefijo);
        }
        if (!UtilidadesString.esCadenaVacia(sufijo)) {
            criteria.andSufijoEqualTo(sufijo);
        }

        List<AdmContador> admContadorList = admContadorExtendsMapper.selectByExample(admContadorExample);

        return admContadorList.isEmpty() ? null : admContadorList.get(0);
    }

    public void setContador(AdmContador datosContadorNuevo, String numRegNuevo) throws Exception {
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
            throw new Exception("Error al modificar los datos del contador", e);
        }
    }
 public void setContador(AdmContador datosContadorNuevo, String numRegNuevo, boolean remesa) throws Exception {
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

                admContadorExtendsMapper.updateByPrimaryKeySelective(datosContadorNuevo);
            }

        } catch (Exception e) {
            throw new Exception("Error al modificar los datos del contador", e);
        }
    }	
																												  
    public int getSiguienteNumContador(AdmContador contador) throws Exception {
        int contadorSiguiente = 0;
        AdmContador gcOriginal = null;

        try {
            gcOriginal = getContador(contador.getIdinstitucion(), (String) contador.getIdcontador());
            if ((gcOriginal.getPrefijo().equals(contador.getPrefijo()))
                    && (gcOriginal.getSufijo().equals(contador.getSufijo()))) {
                contadorSiguiente = Integer.parseInt((contador.getContador()).toString());

                // Comprobamos que el contador que se sugiere no supera la longitud definida
                validarLongitudContador(contadorSiguiente, contador);

                // Comprobamos la unicidad de este contador junto con el prefijo y sufijo
                // guardado en la hash contador
                while (this.comprobarUnicidadContador(contadorSiguiente, contador)) {
                    contadorSiguiente++;
                    // Comprobamos que el contador que se sugiere no supera la longitud definida
                    validarLongitudContador(contadorSiguiente, contador);
                }

            } else {
                Long siguienteContador = null;
                siguienteContador = admContadorExtendsMapper.getSiguienteNumContador(contador);

                if (siguienteContador != null) {
                    contadorSiguiente = siguienteContador.intValue();
                } else {
                    contadorSiguiente = 1;
                }

                // Comprobamos que el contador que se sugiere no supera la longitud definida
                validarLongitudContador(contadorSiguiente, contador);
            }
        } catch (Exception e) {

            throw new Exception("Error al modificar los datos del contador", e);
        }
        return contadorSiguiente;
    }

    /**
     * Metodo que devuelve el siguiente contador siguiente al insertado en la tabla ADM_CONTADOR sin
     * validar su unicidad en el sistema
     */
    public String getNuevoContador(AdmContador contador) throws Exception {

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
            throw new Exception("Error al obtener nuevo contador", e);
        }
        return contadorFinalSugerido;
    }

    public String getNuevoContadorConPrefijoSufijo(AdmContador contenidoAdmContador) throws Exception {
        String contadorFinalSugerido = "";
        try {
            String numeroAbono = getNuevoContador(contenidoAdmContador);
            contadorFinalSugerido = contenidoAdmContador.getPrefijo() + numeroAbono + contenidoAdmContador.getSufijo();
        } catch (Exception e) {
            throw new Exception("Error al obtener nuevo contador con prefijo y sufijo", e);
        }
        return contadorFinalSugerido;
    }

    public boolean comprobarUnicidadContador(int contadorSiguiente, AdmContador contador) throws Exception {

        boolean existe = false;
        StringBuilder select = new StringBuilder();
        try {
            Long siguienteContador = null;
            siguienteContador = admContadorExtendsMapper.comprobarUnicidadContador(contador, contadorSiguiente);

            if (siguienteContador != null) {
                existe = true;
            } else {
                existe = false;
            }

        } catch (Exception e) {

            throw new Exception("Error al modificar los datos del contador", e);
        }
        return existe;
    }

    public boolean comprobarUnicidadContadorProdCertif(int contadorSiguiente, AdmContador contador, String idTipoProducto,
                                                       String idProducto, String idProductoInstitucion) throws Exception {

        boolean existe = false;
        try {

            // Comprobamos la unicidad de este contador junto con el prefijo y sufijo
            // guardado en contador
            List<Object> objectList = admContadorExtendsMapper.comprobarUnicidadContadorProdCertif(contadorSiguiente, contador,
                    idTipoProducto, idProducto, idProductoInstitucion);

            if (!objectList.isEmpty()) {
                existe = true;
            } else {
                existe = false;
            }

        } catch (Exception e) {
            throw new Exception("Error al modificar los datos del contador", e);
        }
        return existe;
    }

    public void validarLongitudContador(int contadorSiguiente, AdmContador contador) throws Exception {

        try {
            String contadorlongitud = Integer.toString(contadorSiguiente);
            Integer longitud = contador.getLongitudcontador();
            if (contadorlongitud.length() > longitud.intValue()) {
                if (contador.getModo().toString().equals("1")) {
                    throw new Exception("Ya existe el número de registro y al sugerir un nuevo contador se ha violado la longitud máxima configurada para él.");
                } else {
                    throw new Exception("Se ha superado la máxima longitud para el número contador");
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar los datos del contador", e);
        }
    }

}