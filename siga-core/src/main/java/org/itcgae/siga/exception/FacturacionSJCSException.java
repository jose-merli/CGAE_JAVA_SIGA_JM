package org.itcgae.siga.exception;

public class FacturacionSJCSException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String description;

    public FacturacionSJCSException(String mensaje) {
        super(mensaje);
        this.description = "";
    }

    public FacturacionSJCSException(String mensaje, Exception e) {
        super(mensaje, e);
        this.description = "";
    }

    public FacturacionSJCSException(String mensaje, String description) {
        super(mensaje);
        this.description = description;
    }

    public FacturacionSJCSException(String mensaje, Exception e, String description) {
        super(mensaje, e);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
