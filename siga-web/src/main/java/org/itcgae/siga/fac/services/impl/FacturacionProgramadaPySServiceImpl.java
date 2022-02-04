package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants.FASES_PROCESO_FACTURACION_AUTOMATICA_PYS;
import org.itcgae.siga.fac.services.IFacturacionProgramadaPySService;
import org.springframework.stereotype.Service;

@Service
public class FacturacionProgramadaPySServiceImpl implements IFacturacionProgramadaPySService {

    private final Logger LOGGER = Logger.getLogger(FacturacionProgramadaPySServiceImpl.class);

    private static Boolean alguienEjecutando = Boolean.FALSE;

    @Override
    public void ejecutaProcesoFacturacionPyS() {

        if (isAlguienEjecutando()) {
            LOGGER.debug("YA SE ESTA EJECUTANDO EL PROCESO AUTOMATICO DE FACTURACION DE PYS EN BACKGROUND. CUANDO TERMINE SE INICIARA OTRA VEZ EL PROCESO DE DESBLOQUEO.");
            return;
        }

        final String sNombreProceso = "ProcesoAutomaticoFacturacion";

        try {

            ProcesoFacPyS procesoFacPyS = null;

            for (FASES_PROCESO_FACTURACION_AUTOMATICA_PYS fase : FASES_PROCESO_FACTURACION_AUTOMATICA_PYS.values()) {

                if (fase.getCodigo().equals(FASES_PROCESO_FACTURACION_AUTOMATICA_PYS.TRATAR_FACTURACION.getCodigo())) {
                    procesoFacPyS = new ProTratarFacturacion();
                } else if (fase.getCodigo().equals(FASES_PROCESO_FACTURACION_AUTOMATICA_PYS.TRATAR_CONFIRMACION.getCodigo())) {
                    procesoFacPyS = new ProTratarConfirmacion();
                } else if (fase.getCodigo().equals(FASES_PROCESO_FACTURACION_AUTOMATICA_PYS.GENERAR_PDFS_Y_ENVIAR_FACTURAS_PROGRAMACION.getCodigo())) {
                    procesoFacPyS = new ProGenerarPDFsYenviarFacturasProgramacion();
                } else if (fase.getCodigo().equals(FASES_PROCESO_FACTURACION_AUTOMATICA_PYS.GENERAR_ENVIOS_FACTURAS_PENDIENTES.getCodigo())) {
                    procesoFacPyS = new ProGenerarEnviosFacturasPendientes();
                } else if (fase.getCodigo().equals(FASES_PROCESO_FACTURACION_AUTOMATICA_PYS.COMPROBACION_TRASPASO_FACTURAS.getCodigo())) {
                    procesoFacPyS = new ProComprobacionTraspasoFacturas();
                }

                if (procesoFacPyS != null) {
                    setAlguienEjecutando();
                    procesoFacPyS.ejecutar();
                }

            }

        } catch (Exception e) {
            LOGGER.error("ERROR en proceso facturacion automatica");
            LOGGER.error(" - Notificacion \"" + sNombreProceso + "\" ejecutada ERROR. : " + e);
            e.printStackTrace();
        } finally {
            setNadieEjecutando();
        }

    }

    public static boolean isAlguienEjecutando() {
        synchronized (FacturacionProgramadaPySServiceImpl.alguienEjecutando) {
            if (!FacturacionProgramadaPySServiceImpl.alguienEjecutando) {
                FacturacionProgramadaPySServiceImpl.alguienEjecutando = Boolean.TRUE;
                return false;
            } else {
                return true;
            }
        }
    }

    public static void setAlguienEjecutando() {
        synchronized (FacturacionProgramadaPySServiceImpl.alguienEjecutando) {
            FacturacionProgramadaPySServiceImpl.alguienEjecutando = Boolean.TRUE;
        }
    }

    public static void setNadieEjecutando() {
        synchronized (FacturacionProgramadaPySServiceImpl.alguienEjecutando) {
            FacturacionProgramadaPySServiceImpl.alguienEjecutando = Boolean.FALSE;
        }
    }

}
