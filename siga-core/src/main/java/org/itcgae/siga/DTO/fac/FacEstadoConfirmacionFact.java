package org.itcgae.siga.DTO.fac;

public enum FacEstadoConfirmacionFact {

    CONFIRM_PROGRAMADA(1),
    GENERADA(2),
    CONFIRM_FINALIZADA(3),
    CONFIRM_FINALIZADAERRORES(4),
    PDF_NOAPLICA(5),
    PDF_PROGRAMADA(6),
    PDF_PENDIENTE(7),
    PDF_PROCESANDO(8),
    PDF_FINALIZADA(9),
    PDF_FINALIZADAERRORES(10),
    ENVIO_NOAPLICA(11),
    ENVIO_PROGRAMADA(12),
    ENVIO_PENDIENTE(13),
    ENVIO_PROCESANDO(14),
    ENVIO_FINALIZADA(15),
    ENVIO_FINALIZADAERRORES(16),
    EJECUTANDO_CONFIRMACION(17),
    GENERACION_PROGRAMADA(18),
    EJECUTANDO_GENERACION(19),
    ERROR_GENERACION(20),
    ERROR_CONFIRMACION(21),
    TRASPASO_NOAPLICA(22),
    TRASPASO_PROGRAMADA(23),
    TRASPASO_PENDIENTE(24),
    TRASPASO_PROCESANDO(25),
    TRASPASO_FINALIZADA(26),
    TRASPASO_FINALIZADAERRORES(27);

    private Short id;

    FacEstadoConfirmacionFact(Integer id) {
        this.id = id.shortValue();
    }

    public short getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
