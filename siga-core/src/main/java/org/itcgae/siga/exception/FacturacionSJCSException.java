package org.itcgae.siga.exception;

import org.apache.log4j.Logger;

public class FacturacionSJCSException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String description;

    private Logger LOGGER = Logger.getLogger(FacturacionSJCSException.class);

    public FacturacionSJCSException(String mensaje) {
        super(mensaje);
        LOGGER.error(mensaje);
        this.description = "";
    }

    public FacturacionSJCSException(String mensaje, Exception e) {
        super(mensaje, e);
        LOGGER.error(mensaje);
        this.description = "";
    }

    public FacturacionSJCSException(String mensaje, String description) {
        super(mensaje);
        LOGGER.error(mensaje);
        this.description = description;
    }

    public FacturacionSJCSException(String mensaje, Exception e, String description) {
        super(mensaje, e);
        LOGGER.error(mensaje);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
