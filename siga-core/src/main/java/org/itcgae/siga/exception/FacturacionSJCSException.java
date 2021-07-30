package org.itcgae.siga.exception;

public class FacturacionSJCSException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String description;

    public FacturacionSJCSException(String mensaje, String description) {
        super(mensaje);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
