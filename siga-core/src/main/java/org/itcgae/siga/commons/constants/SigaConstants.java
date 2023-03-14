package org.itcgae.siga.commons.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.itcgae.siga.commons.utils.UtilidadesString;
import org.springframework.stereotype.Component;

@Component
public class SigaConstants {

    public static String parametroRutaAlmacenFicheros = "gen.ficheros.path";
    public static String parametroRutaCertificados = "informes.directorioCertificacionJava";
    public static String parametroRutaDirectorioIncidenciasWS = "certificacion.directorioIncidenciasWS";
    
    public enum ERROR_SERVER {
        XML_NO_VALIDO(null), CLI_NOAUTORIZADO("La IP recibida en la petición no está autorizada."),
        CLI_IP_NO_ENCONTRADA("La ip no se ha encontrado."), CLI_NOVALIDO("Esquema de petición datos no válido."),
        CLI_NOACTIVO("El colegio que realiza la petición no tiene activo el servicio."),
        CLI_CODIGO_NO_EXISTE("El código del colegio no existe."),
        CLI_VERSION_INCORRECTA("La versión recibida no existe o no es la correspondiente al esquema utilizado."),
        CLI_ERROR_DATOS_INTERNO(
                "Los datos que se reciben no son válidos para enviar una respuesta correcta en el servicio."),
        CLI_OTROS_ERRORES_INTERNOS("Error de causas externas."), CLI_COLEGIO_NULO("El colegio no puede ser nulo."),
        CLI_PAGINA_YA_INSERTADA("La página ya ha sido insertada para esa carga"),
        CLI_CAMPO_NO_VALIDO("La longitud es superior a la máxima permitida"),
        CLI_ORDEN_FECHAS_INCORRECTO("La fecha hasta no puede ser menor a la fecha desde"),
        CLI_HORA_EJECUCION_NO_PERMITIDO("No está permitida la ejecución del servicio a esta hora"),
        CLI_COLEGIO_NO_EXISTE("Alguno de los colegios no existe en el sistema"),
        CLI_INCONCORDANCIA_EN_PAGINAS("El número de página no puede ser superior al total de páginas.");

        private String mensajeError = null;

        private ERROR_SERVER(String mensajeError) {
            this.mensajeError = mensajeError;
        }

        public String getMensajeError() {
            return mensajeError;
        }
    }

    public static enum ECOM_CEN_ROLES {
        ABOGADO("ABO", "ABOGADO", "Abogado"), NOEJERCIENTE("CNE", "COLEGIADO_NO_EJERCIENTE", "Colegiado No Ejerciente"),
        PERSONAL("PER", "PERSONAL", "Personal"), DECANO("DEC", "DECANO", "Decano"),
        MIEMBROJUNTA("MJU", "MIEMBRO_JUNTA", "Miembro de Junta"), CONSEJERO("CON", "CONSEJERO", "Consejero"),
        DIRECTIVO("DIR", "DIRECTIVO", "Directivo"), INSCRITO("INS", "ABOGADO_INSCRITO", "Abogado inscrito"),
        ABOGADOEUROPEO("CCB", "ABOGADO_ADVOCAT_AVOGADO_ABOKATU", "Abogado Advocat Avogado Abokatu"),
        ADMINISTRADOR("ADM", "ADMINISTRADOR", "Administrador"),
        ADMINISTRADORUNICO("ADMUNI", "ADMINISTRADOR_UNICO", "Administrador Único"),
        ADMINISTRADORSOLIDARIO("ADMSOL", "ADMINISTRADOR_SOLIDARIO", "Administrador Solidario"),
        AUTORIZADO("AUT", "AUTORIZADO", "Autorizado"),
        REPRESENTANTELEGAL("REP", "RESPRESENTANTE_LEGAL", "Representante Legal"),
        REPRESENTANTEVOLUNTARIO("REPVOL", "RESPRESENTANTE_VOLUNTARIO", "Representante Voluntario"),
        SECRETARIO("SEC", "SECRETARIO", "Secretario"), VICEDECANO("VICDEC", "VICEDECANO", "Vicedecano"),
        SIGAADMIN("SAD", "SIGA-Admin", "SIGA-Admin");

        private String codigo = null;
        private String recurso = null;
        private String descripcion = null;

        private ECOM_CEN_ROLES(String codigo, String recurso, String descripcion) {
            this.codigo = codigo;
            this.recurso = recurso;
            this.descripcion = descripcion;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getRecurso() {
            return recurso;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    public enum ERROR_CLIENT {
        XML_NO_VALIDO(null), SERV_NODISPONIBLE("Servicio no disponible."),
        SERV_CERTNOAUT(
                "En el caso de comunicaciones con certificado de cliente que el certificado presentado no esté autorizado."),
        CLI_NOVALIDO("Esquema de petición datos no válido."),
        CLI_NOACTIVO("El colegio que realiza la petición no tiene activo el servicio."),
        SERV_FECHANOVALIDO("Para el servidor el rango de fechas solicitado no es válido."),
        SERV_COLEGIONOVALIDO("Para el servidor el código del colegio recibido no es válido."),
        SERV_NUMPAGINANOVALIDO("Para el servidor el número de página solicitado no existe o no es la esperada.."),
        SERV_VERSION_INCORRECTA(
                "La versión recibida no existe o no es la correspondiente al esquema utilizado en la petición."),
        SERV_OTRO_ERROR("Cualquier otro error no catalogado."),
        SERV_NOVALIDO("La respuesta no tiene un formato válido");

        private String mensajeError = null;

        private ERROR_CLIENT(String mensajeError) {
            this.mensajeError = mensajeError;
        }

        public String getMensajeError() {
            return mensajeError;
        }
    }

    public enum ID_TIPO_CARGA {
        SERV_SOC(new Short("1")), CLI_SOC(new Short("2"));

        private Short tipo = null;

        private ID_TIPO_CARGA(Short tipo) {
            this.tipo = tipo;
        }

        public Short getTipo() {
            return tipo;
        }
    }

    public static enum TIPO_XML {
        PETICION_SERVICIO_CARGAS(new Short("1"), "petición servicio"),
        RESPUESTA_SERVICIO_CARGAS(new Short("2"), "respuesta servicio"),
        PETICION_CLIENTE_CARGAS(new Short("3"), "petición cliente"),
        RESPUESTA_CLIENTE_CARGAS(new Short("4"), "respuesta cliente"),
        PETICION_SERVICIO_ECOS(new Short("5"), "petición servicio ECOS"),
        RESPUESTA_CLIENTE_ECOS(new Short("6"), "respuesta cliente ECOS"),
        PETICION_WS_PUBLICADOR(new Short("7"), "petición servicio"),
        RESPUESTA_WS_PUBLICADOR(new Short("8"), "respuesta servicio"),
        PETICION_SERVICIO_FUSIONADOR(new Short("9"), "petición servicio"),
        RESPUESTA_SERVICIO_FUSIONADOR(new Short("10"), "respuesta servicio");

        private final Short codigo;
        private final String descripcion;

        TIPO_XML(Short codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        public Short getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static TIPO_XML getEnum(Short codigo) {
            for (TIPO_XML sc : values()) {
                if (sc.getCodigo().shortValue() == codigo.shortValue()) {
                    return sc;
                }
            }
            return null;
        }
    }

    public static enum MODULO {
        CARGAS(new Short("1"), "CARGAS"), ADMINISTRACION(new Short("2"), "ADMINISTRACION"),
        PUBLICACION(new Short("3"), "PUBLICACION"), FUSIONADOR(new Short("4"), "FUSIONADOR");

        private final Short codigo;
        private final String descripcion;

        MODULO(Short codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        public Short getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static MODULO getEnum(Short codigo) {
            for (MODULO sc : values()) {
                if (sc.getCodigo().shortValue() == codigo.shortValue()) {
                    return sc;
                }
            }
            return null;
        }
    }

    public static enum ESTADO_CARGAS {
        ESTADO_PENDIENTE(new Short("1"), "Carga pendiente"), ESTADO_PROCESANDO(new Short("2"), "Carga procesando"),
        ESTADO_PROCESADO(new Short("3"), "Carga procesada"),
        ESTADO_ERROR_FORMATO(new Short("4"), "Carga con errores de formato"),
        ESTADO_ERROR_GENERAL(new Short("5"), "Carga finalizada con error");

        private final Short codigo;
        private final String descripcion;

        ESTADO_CARGAS(Short codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        public Short getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static ESTADO_CARGAS getEnum(Short codigo) {
            for (ESTADO_CARGAS sc : values()) {
                if (sc.getCodigo().shortValue() == codigo.shortValue()) {
                    return sc;
                }
            }
            return null;
        }
    }

    public static enum CEN_TIPOCAMBIO {

        ALTA_COLEGIACION(new Short("1"), "Alta Colegiación"), BAJA_COLEGIACION(new Short("2"), "Baja Colegiación"),
        ALTA_EJERCICIO(new Short("3"), "Alta Ejercicio"), BAJA_EJERCICIO(new Short("4"), "Baja Ejercicio"),
        INHABILITACION(new Short("5"), "Inhabilitación"), SUSPENSION_EJERCICIO(new Short("6"), "Suspensión Ejercicio"),
        MODIFICACION_DATOS_GENERALES(new Short("10"), "Modificación Datos Generales"),
        MODIFICACION_DATOS_COLEGIALES(new Short("20"), "Modificación Datos Colegiales"),
        MODIFICACION_DIRECCIONES(new Short("30"), "Modificación Direcciones"),
        MODIFICACION_CUENTAS_BANCARIAS(new Short("40"), "Modificación Cuentas Bancarias"),
        MODIFICACION_DATOS_CV(new Short("50"), "Modificación Datos CV"),
        MODIFICACION_COMPONENTES(new Short("60"), "Modificación Componentes"),
        MODIFICACION_DATOS_FACTURACIÓN(new Short("70"), "Modificación Datos Facturación"),
        MODIFICACION_DATOS_TURNO(new Short("80"), "Modificación Datos Turno"),
        MODIFICACION_DATOS_EXPEDIENTES(new Short("90"), "Modificación Datos Expedientes"),
        MODIFICACION_OTROS_DATOS(new Short("99"), "Modificación Otros Datos"),
        OBSERVACIONES(new Short("100"), "Observaciones"),
        DESIGNACION_MODIFICACION(new Short("101"), "Designación. Modificación"),
        DESIGNACION_JUSTIFICACION(new Short("102"), "Designación. Justificación"),
        DESIGNACION_ALTA_DE_ACTUACIONES(new Short("103"), "Designación. Alta de Actuaciones"),
        ASISTENCIA_ALTA(new Short("104"), "Asistencia. Alta"),
        ASISTENCIA_MODIFICACION(new Short("105"), "Asistencia. Modificación"),
        ASISTENCIA_ALTA_DE_ACTUACIONES(new Short("106"), "Asistencia. Alta de Actuaciones"),
        DESIGNACION_MODIFICACION_DE_ACTUACIONES(new Short("107"), "Designación. Modificación de Actuaciones"),
        DESIGNACION_ELIMINACION_DE_ACTUACIONES(new Short("108"), "Designación. Eliminación de Actuaciones"),
        EJG_CREACION_NUEVO_EJG(new Short("120"), "EJG. Creación nuevo EJG"),
        EJG_MODIFICACION_DATOS_GENERALES(new Short("121"), "EJG. Modificación datos generales"),
        EJG_MODIFICACION_SERVICIOS_TRAMITACION(new Short("122"), "EJG. Modificación servicios de tramitación"),
        EJG_ANADIR_FAMILIAR_NUEVO(new Short("123"), "EJG. Se añade un familiar nuevo"),
        EJG_BORRAR_FAMILIAR(new Short("124"), "EJG. Se borra un familiar"),
        EJG_ACTIVAR_FAMILIAR_BORRADO(new Short("125"), "EJG. Se activa un familiar borrado"),
        EJG_ANADIR_ESTADO_MANUALMENTE(new Short("126"), "EJG. Añadido estado manualmente"),
        EJG_ESTADO_BORRADO_MANUALMENTE(new Short("127"), "EJG. Estado borrado manualmente"),
        EJG_ESTADO_MODIFICADO(new Short("128"), "EJG. Estado modificado"),
        EJG_DOCUMENTO_ANADIDO(new Short("129"), "EJG. Documento añadido"),
        EJG_DOCUMENTO_ELIMINADO(new Short("130"), "EJG. Documento eliminado"),
        EJG_DOCUMENTO_MODIFICADO(new Short("131"), "EJG. Documento modificado"),
        EJG_FICHERO_ANADIDO_A_UN_DOCUMENTO(new Short("132"), "EJG. Fichero añadido a un documento"),
        EJG_FICHERO_ELIMINADO_DE_UN_DOCUMENTO(new Short("133"), "EJG. Fichero eliminado de un documentoEJG. Fichero eliminado de un documento"),
        EJG_CREACION_DICTAMEN(new Short("134"), "EJG. Creacion de un dictamen"),
        EJG_ELIMINACION_DICTAMEN(new Short("135"), "EJG. Eliminación de un dictamen"),
        EJG_MODIFICACION_DICTAMEN(new Short("136"), "EJG. Modificación de un dictamen"),
        EJG_CREACION_RESOLUCION(new Short("137"), "EJG. Eliminación de un dictamen"),
        EJG_MODIFICACION_IMPUGNACION(new Short("138"), "EJG. Modificación de una impugnación"),
        EJG_DESIGNACION_ASOCIADA(new Short("139"), "EJG. Designación asociada"),
        EJG_ASISTENCIA_ASOCIADA(new Short("140"), "EJG. Asistencia asociada"),
        EJG_SOJ_ASOCIADO(new Short("141"), "EJG. SOJ asociado"),
        EJG_RELACION_ELIMINADA(new Short("142"), "EJG. Relación eliminada");


        private final Short idTipoCambio;
        private final String descripcionTipoCambio;

        private CEN_TIPOCAMBIO(Short idTipoCambio, String descripcionTipoCambio) {
            this.idTipoCambio = idTipoCambio;
            this.descripcionTipoCambio = descripcionTipoCambio;
        }

        public Short getIdTipoCambio() {
            return idTipoCambio;
        }

        public String getDescripcionTipoCambio() {
            return descripcionTipoCambio;
        }

        public static CEN_TIPOCAMBIO getEnum(Short idTipoCambio) {
            for (CEN_TIPOCAMBIO sc : values()) {
                if (sc.getIdTipoCambio().shortValue() == idTipoCambio.shortValue()) {
                    return sc;
                }
            }
            return null;
        }

    }

    public static enum OBJETIVO {
        DESTINATARIOS(new Long("1"), "DESTINATARIOS"), MULTIDOCUMENTO(new Long("2"), "MULTIDOCUMENTO"),
        CONDICIONAL(new Long("3"), "CONDICIONAL"), DATOS(new Long("4"), "DATOS");

        private final Long codigo;
        private final String descripcion;

        OBJETIVO(Long codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        public Long getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static OBJETIVO getEnum(Short codigo) {
            for (OBJETIVO sc : values()) {
                if (sc.getCodigo().longValue() == codigo.longValue()) {
                    return sc;
                }
            }
            return null;
        }
    }

    public static enum SUFIJOS {
        NOMBRE_COLEGIADO(new Short("1"), "NOMBRE_COLEGIADO"), NUMERO_COLEGIADO(new Short("2"), "NUMERO_COLEGIADO"),
        IDENTIFICACION(new Short("3"), "IDENTIFICACION");

        private final Short codigo;
        private final String descripcion;

        SUFIJOS(Short codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        public Short getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static SUFIJOS getEnum(Short codigo) {
            for (SUFIJOS sc : values()) {
                if (sc.getCodigo().shortValue() == codigo.shortValue()) {
                    return sc;
                }
            }
            return null;
        }
    }

    public static enum FORMATO_SALIDA {
        XLS(new Short("1"), "xlsx"), DOC(new Short("2"), "docx"), PDF(new Short("3"), "pdf"),
        PDF_FIRMADO(new Short("4"), "pdf");

        private final Short codigo;
        private final String descripcion;

        FORMATO_SALIDA(Short codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
        }

        public Short getCodigo() {
            return codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static FORMATO_SALIDA getEnum(Short codigo) {
            for (FORMATO_SALIDA sc : values()) {
                if (sc.getCodigo().shortValue() == codigo.shortValue()) {
                    return sc;
                }
            }
            return null;
        }
    }

    public static final String PERICLES_PARAM_ECOMCOLA_IDINSTITUCION = "IDINSTITUCION";
    public static final String PERICLES_PARAM_ECOMCOLA_ANIO = "ANIO";
    public static final String PERICLES_PARAM_ECOMCOLA_IDTIPOEJG = "IDTIPOEJG";
    public static final String PERICLES_PARAM_ECOMCOLA_NUMERO = "NUMERO";
    public static final String PERICLES_PARAM_ECOMCOLA_IDDOCUMENTACION = "IDDOCUMENTACION";
    public static final String PERICLES_PARAM_ECOMCOLA_NIFNIE = "NIFNIE";
    public static final String PERICLES_PARAM_ECOMCOLA_ULTIMODOCUMENTO = "ULTIMODOCUMENTO";
    public static final String PERICLES_PARAM_ECOMCOLA_IDDOCUSHARE = "IDDOCUSHARE";


    public static enum ESTADOS_EJG {
        REMITIDA_APERTURA_A_COMISION((short)0),
        SOLICITADA_DOCUMENTACION((short)1),
        COMPLETADA_SOLICITITUD_Y_DOCUMENTACION((short)2),
        TRASLADO_A_TRAMITADOR((short)3),
        PREVISION_RECIBIR_DICTAMEN((short)4),
        SOLICITADO_AMPLIACIÓN_DOCUMENTACION((short)5),
        DICTAMINADO((short)6),
        LISTO_REMITIR_COMISION((short)7),
        GENERADO_EN_REMESA((short)8),
        REMITIDO_COMISION((short)9),
        RESUELTO_COMISION((short)10),
        IMPUGNADO((short)11),
        ARCHIVADO((short)12),
        RESUELTA_IMPUGNACION((short)13),
        INCIDENCIAS((short)14),
        PETICION_DE_DATOS((short)15),
        ENVIADO_A_EDICTO((short)16),
        LISTO_REMITIR_COMISION_ACT_DESIGNACION((short)17),
        PETICION_PROCURADOR((short)18),
        DESIGNADO_PROCURADOR((short)19),
        REMITIDA_APERTURA_A_CAJG_REPARTO_PONENTE((short)20),
        DEVUELTO_AL_COLEGIO((short)21),
        INCIDENCIAS_PROCURADOR((short)22),
        SOLICITUD_EN_PROCESO_DE_ALTA((short)23),
        IMPUGNABLE((short)24),
        GENERADO_ENV_COMISION((short)25);

        private final short codigo;

        private ESTADOS_EJG (short codigo) {
            this.codigo = codigo;
        }

        public short getCodigo() {
            return this.codigo;
        }
        public static ESTADOS_EJG getEnum(Short codigo){
            for(ESTADOS_EJG sc : values()){
                if (sc.getCodigo()==codigo.shortValue()){
                    return sc;
                }
            }
            return null;
        }
    }

    // Estados facturacion
    public static enum ESTADO_FACTURACION {
        ESTADO_FACTURACION_ABIERTA(10), ESTADO_FACTURACION_EJECUTADA(20), ESTADO_FACTURACION_LISTA_CONSEJO(30),
        ESTADO_FACTURACION_EN_EJECUCION(40), ESTADO_FACTURACION_PROGRAMADA(50),
        ESTADO_FACTURACION_VALIDACION_NO_CORRECTA(60), ESTADO_FACTURACION_ENVIO_NO_ACEPTADO(70),
        ESTADO_FACTURACION_ENVIO_NO_DISPONIBLE(80), ESTADO_FACTURACION_ENVIO_EN_PROCESO(90);

        private Integer codigo;

        private ESTADO_FACTURACION(Integer codigo) {
            this.codigo = codigo;
        }

        public Integer getCodigo() {
            return this.codigo;
        }
    }

    public static enum CargaMasivaDatosCVVo {

        COLEGIADONUMERO("COLEGIADONUMERO"), PERSONANIF("PERSONANIF"), C_FECHAINICIO("C_FECHAINICIO"),
        C_FECHAFIN("C_FECHAFIN"), C_CREDITOS("C_CREDITOS"), FECHAVERIFICACION("FECHAVERIFICACION"),
        TIPOCVCOD("TIPOCVCOD"), SUBTIPOCV1COD("SUBTIPOCV1COD"), SUBTIPOCV2COD("SUBTIPOCV2COD"),
        PERSONANOMBRE("PERSONANOMBRE"), C_IDPERSONA("C_IDPERSONA"), TIPOCVNOMBRE("TIPOCVNOMBRE"),
        C_IDTIPOCV("C_IDTIPOCV"), SUBTIPOCV1NOMBRE("SUBTIPOCV1NOMBRE"), C_IDTIPOCVSUBTIPO1("C_IDTIPOCVSUBTIPO1"),
        SUBTIPOCV2NOMBRE("SUBTIPOCV2NOMBRE"), C_IDTIPOCVSUBTIPO2("C_IDTIPOCVSUBTIPO2"), ERRORES("ERRORES"),
        C_DESCRIPCION("C_DESCRIPCION");

        private final String campo;

        private CargaMasivaDatosCVVo(String campo) {
            this.campo = campo;
        }

        public String getCampo() {
            return campo;
        }
    }

    public static enum HISTORICOCAMBIOGF {
        DATOSGENERALES((short) 10), DATOSCARGAMASIVA((short) 50);

        private short id = 0;

        HISTORICOCAMBIOGF(short id) {
            this.id = id;
        }

        public short getId() {
            return this.id;
        }
    }

    public static enum HISTORICOCAMBIOCV {
        DATOSGENERALES((short) 10), DATOSCV((short) 50);

        private short id = 0;

        HISTORICOCAMBIOCV(short id) {
            this.id = id;
        }

        public short getId() {
            return this.id;
        }
    }

    public static enum PARAMETRO_GENERAL {
        MAX_FILE_SIZE("10485760");

        private String valor = null;

        private PARAMETRO_GENERAL(String valor) {
            this.valor = valor;
        }

        public final String getValor() {
            return this.valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    public static enum GEN_PARAMETROS {
        PATH_DOCUMENTOSADJUNTOS
    }

    public static enum ENVIOS_MASIVOS_LOG_EXTENSION {
        xls, xlsx
    }



    public static enum ECOM_ESTADOSCOLA {
        INICIAL((short) 1), EJECUTANDOSE((short) 2), REINTENTANDO((short) 3), ERROR((short) 4), FINAL((short) 5),
        ERROR_VALIDACION((short) 6);

        private short id = -1;

        private ECOM_ESTADOSCOLA(short id) {
            this.id = id;
        }

        public short getId() {
            return this.id;
        }

    }

    public static enum ECOM_OPERACION {
        ECOM2_INIT_PARAMETROS_GENERALES(206),
        ECOM2_XUNTA_JE(18),
        ECOM2_CAT_VALIDA_JUSTIFICACION(69),
        CAT_ENVIA_RESP_JUSTIFICACION(74),
        PCAJG_ALCALA_JE_FICHERO_ERROR(56),
    	XUNTA_ENVIO_JUSTIFICACION (27),
		XUNTA_ENVIO_REINTEGROS (28),
		TRASPASAR_FACTURAS_CREARCLIENTE_NAVISION(61);

        public static ECOM_OPERACION getEnum(Integer codigo) {
            for (ECOM_OPERACION sc : values()) {
                if (sc.getId() == codigo) {
                    return sc;
                }
            }
            return null;
        }

        private int id = -1;

        private ECOM_OPERACION(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static enum GEN_PROPERTIES_FICHERO {
        eCOM2_LOG4J
    }

    public static final String PARAMETRO_PROPOSITO_TRANSFERENCIA_SEPA = "PROPOSITO_TRANSFERENCIA_SEPA";
    public static final String PARAMETRO_PROPOSITO_OTRA_TRANFERENCIA = "PROPOSITO_OTRA_TRANSFERENCIA";

    public static final String MODULO_FACTURACION = "FAC";

    // Estados Pago SJCS
    public static final String ESTADO_PAGO_ABIERTO = "10";
    public static final String ESTADO_PAGO_EJECUTADO = "20";
    public static final String ESTADO_PAGO_CERRADO = "30";
    public static final String ESTADO_PAGO_EJECUTANDO = "40";
    public static final String ESTADO_PAGO_CERRANDO = "50";
    public static final String ESTADO_PAGO_DESHACIENDO_CIERRE = "60";

    public static final String CRITERIOS_PAGO_FACTURACION = "F";
    public static final String CRITERIOS_PAGO_PAGOS = "P";

    public static final int LISTA_PAGO_SOLO_INCLUIR_MOROSOS = 0;
    public static final int LISTA_PAGO_SOLO_INCLUIR_NO_MOROSOS = 1;
    public static final int LISTA_PAGO_TODOS = 2;

    public static final int CASO_MVNOASOCIADO = 1;
    public static final int CASO_MVASOCIADOAGRUPOFACT = 2;
    public static final int CASO_MVASOCIADOAFACTURACION = 3;

    public static final String PATH_PREVISIONES_BD = "PATH_PREVISIONES_BD";
    public static final String PATH_PREVISIONES = "PATH_PREVISIONES";
    public static final String MODULO_FCS = "FCS";
    public static final String MODULO_CER = "CER";
    public static final String CONTADOR_ABONOS_PAGOSJG = "FAC_ABONOS_FCS";

    // Fcatura/abono Contabilizada
    public static final String FACTURA_ABONO_CONTABILIZADA = "S";
    public static final String FACTURA_ABONO_NO_CONTABILIZADA = "N";

    public static final String MODULO_FACTURACION_SJCS = "FCS";

    public static final String DEDUCIR_COBROS_AUTOMATICO = "DEDUCIR_COBROS_AUTOMATICO";

    public static final String TIPO_APUNTE_COMPENSADO = "C";

    // Tipos de formas de pago de Productos y Servicios
    public static final String TIPO_FORMAPAGO_TARJETA	= "10";
    public static final String TIPO_FORMAPAGO_FACTURA	= "20";
    public static final String TIPO_FORMAPAGO_METALICO	= "30";
    public static final String TIPO_FORMAPAGO_DOMICILIACION_BANCARIA = "80";

    //Estado Facturas
    public static final String ESTADO_FACTURA_PAGADA = "1";
    public static final String ESTADO_FACTURA_CAJA = "2";
    public static final String ESTADO_FACTURA_BANCO = "5";
    public static final String ESTADO_FACTURA_EN_REVISION = "7";
    public static final String ESTADO_FACTURA_ANULADA = "8";
    public static final String ESTADO_FACTURA_DEVUELTA = "4";
    public static final String ESTADO_FACTURA_LISTA_PARA_FICHERO = "6";

    public static final String PARAMETRO_DIRECTORIO_FISICO_FACTURA_PDF = "facturacion.directorioFisicoFacturaPDFJava";
    public static final String PARAMETRO_DIRECTORIO_FACTURA_PDF = "facturacion.directorioFacturaPDFJava";
    public static final String PARAMETRO_PATH_CERTIFICADOS_DIGITALES = "PATH_CERTIFICADOS_DIGITALES";
    public static final String PARAMETRO_NOMBRE_CERTIFICADOS_DIGITALES = "NOMBRE_CERTIFICADOS_DIGITALES";
    public static final String PARAMETRO_CLAVE_CERTIFICADOS_DIGITALES = "CLAVE_CERTIFICADOS_DIGITALES";
    public static final String FILE_SEP = System.getProperty("file.separator");

    public static final String CODIGO_PROVINCIA_CEUTA = "51";
    public static final String CODIGO_PROVINCIA_MELILLA = "52";

    //	Tipo de identificaci�n
    public static final int TIPO_IDENTIFICACION_NIF = 10;
    public static final int TIPO_IDENTIFICACION_CIF = 20;
    public static final int TIPO_IDENTIFICACION_TRESIDENTE = 40;
    public static final int TIPO_IDENTIFICACION_PASAPORTE = 30;
    public static final int TIPO_IDENTIFICACION_OTRO = 50;

    public static final String SEPARADOR = "	";

    public static final String IMPRESO190_ENCODING = "ISO-8859-1";

    public static final String TIPO_SOPORTE_CARTUCHO = "C";
    public static final String TIPO_SOPORTE_TELEMATICO = "T";
    public static final String TIPO_SOPORTE_DISQUETE = "D";
    public static final String PATH_IMPRESO190 = "PATH_IMPRESO190";

    public static final String SOJ = "SOJ";

    public static final String PARAMETRO_PCAJG_TIPO = "PCAJG_TIPO";
    public static final int TIPO_CAJG_XML_SANTIAGO = 6;
    public static final int TIPO_CAJG_CATALANES = 2;
    public static final int TIPO_CAJG_CAM = 5;
    
    public enum Consejos {
        C_CATALUNYA("AC0900"),
        EUSKAL_K_("AC1600"),
        C_ANDALUZ("AC0100"),
        C_VALENCI("AC1500"),
        C_GALEGA("AC1100"),
        C_CASTILLA_Y_LEON("AC0700"),
        C_CASTILLA_LA_MANCHA("AC0800"),
        C_MADRID("AC1200"),
        C_CANARIO("AC0500"),
        C_ARAGON("AC0200");

        private String codigoExt;

        private Consejos(String codigoExt) {
            this.codigoExt = codigoExt;
        }

        public String getCodigoExt() {
            return codigoExt;
        }

        public void setCodigoExt(String codigoExt) {
            this.codigoExt = codigoExt;
        }
    }

	
	
	public static enum OPERACION {
		ASIGNA_OBTENER_PROCURADOR (1)
		, ASIGNA_ENVIO_DOCUMENTO (2)
		, EJIS_OBTENER_DESTINATARIOS (3)
		, EJIS_COMUNICACION_DESIGNA_ABOGADO_PROCURADOR (4)
		, ASIGNA_CONSULTA_NUMERO (5)		
		, INIT_PARAMETROS_GENERALES(6)
		, XUNTA_FICHERO_RESPUESTA (7)
		, EJIS_COMUNICACION_SOL_SUSP_PROCEDIMIENTO(8)
		, EJIS_NOTIFICACIONES_PENDIENTES(9)
		, EJIS_PROCESAR_SOL_ABG_PRO(10)
		, EJIS_PROCESAR_RESPUESTAS(11)
		, XUNTA_RESOLUCIONES(12)
		, GV_VALIDACION_EXPEDIENTES(13)
		, GV_ENVIO_EXPEDIENTES(14)
		, ASIGNA_RESOLUCIONES(15)		
		, EJIS_NOTIFICACIONES_ERRONEAS(16)
		, EJIS_OBTENER_TIPOS_PROCEDIMIENTO(17)
		, XUNTA_JE(18)
		, GV_RESOLUCIONES(19)
		, XUNTA_JE_PROCURADORES(20)
		, EJIS_PROCESAR_RESOLUCION_IMPUGNACION(21)	
		, CENSO_OBTENER_COLEGIADOS(22)
		, EJIS_SOLICITUD_IMPUGNACION_RESOL_AJG(23)
		, EJIS_COMUNICACION_RESOLUCION_SOL_AJG(24)
		, XUNTA_VERIFICAR_CERTIFICACION (25)
		, XUNTA_VERIFICAR_REINTEGROS (26)
		, XUNTA_ENVIO_JUSTIFICACION (27)
		, XUNTA_ENVIO_REINTEGROS (28)
		, ASIGNA_VALIDA_EXPEDIENTES(29)
		, ASIGNA_ENVIA_EXPEDIENTES(30)
		, CENSO_PROCESO_COLEGIADOS_REMESA(31)
		, CENSO_ENVIO_MAIL_INCIDENCIAS(32)
		, MEDIADORES_CONCURSALES_XML(33)
		, CV_DATOS_CONTACTO_COLEGIADO (34)
		, CV_GUARDIAS_COLEGIADO (35)
		, GVA_VALIDACION_EXPEDIENTES(36)
		, GVA_ENVIO_EXPEDIENTES(37)
		, MUTUALIDAD_ENVIO_CERTIFICADOS_FINALIZADOS(38)
		, CV_OBTENER_LLAMADAS_ACEPTADAS (39)
		, EJIS_ANDALUCIA_VALIDACION_EXPEDIENTES(40)
		, EJIS_ANDALUCIA_ENVIO_EXPEDIENTES(41)
		, CAT_RESOLUCIONES_PDF(42)
		, XUNTA_VERIFICAR_JUSTIFICACION (43)
		, CENSO_CARGA_EXCEL(44)
		, MJU_REPORT_INCIDENCIAS(45)
		, MJU_REPORT_MAIL_INCIDENCIAS_EXCEL_CGAE(46)
		, CENSO_ELIMINA_REMESA(47)
		, PCAJG_ALCALA_ENVIO_INFORME_ECONOMICO(48)
		, ASIGNA_MODIFICAR_DATOS_SOLICITANTE(49)
		, ASIGNA_MODIFICAR_PRETENSIONES_A_DEFENDER(50)
		, ASIGNA_MODIFICAR_INTERVIENTES(51)
		, ASIGNA_MODIFICAR_TURNADO_ABOGADO(52)
		, ASIGNA_MODIFICAR_CALIFICACION(53)
		, ASIGNA_MODIFICAR_TURNADO_PROCURADOR(54)
		, ASIGNA_ENVIO_CENSO(55)
		, PCAJG_ALCALA_JE_FICHERO_ERROR(56)
		, XUNTA_VALIDA_CARGA_EXPEDIENTES(57)
		, XUNTA_CARGA_EXPEDIENTES(58)
		, PCAJG_ALCALA_VALIDA_INFORME_ECONOMICO(59)
		, XUNTA_VERIFICA_CARGA_EXPEDIENTES(60)
		, TRASPASAR_FACTURAS_CREARCLIENTE_NAVISION(61)
		, TRASPASAR_FACTURAS_CREARFACTURA_NAVISION(62)
		,EJIS_VALIDA_ENVIO_EXPEDIENTES(63)
		,EJIS_ENVIO_EXPEDIENTES(64)
		,ATLANTE_ACUSES_ERRONEOS(66)
		,ATLANTE_ACUSES_NOERRONEOS(67)
//		, EEJG_SOLICITUD_INFORMACION(68)
		, CAT_VALIDA_JUSTIFICACION(69)
		, CAT_ENVIA_JUSTIFICACION_CICAC(70)
		,CAT_ENVIA_JUSTIFICACION_GEN(71)
		,CAT_PROC_JUSTIFICACION_PROC(73)
		,CAT_ENVIA_RESP_JUSTIFICACION(74), 
		CAT_RECIBE_JUSTIFICACION_FTP_ICA(75),
		PCAJG_ALCALA_VALIDA_DOCUMENTACION(76),
		PCAJG_ALCALA_ENVIO_DOCUMENTACION(77),
		PERICLES_ENVIA_EXPEDIENTE(78),
		PERICLES_ENVIA_DOCUMENTO(79),
		ENVIA_PUNTO_NEUTRO_JUDICIAL(80),
		EEJG_SOLICITUD_PETICION_INFOAAPP(81),
		EEJG_CONSULTA_INFORMACION_COMPLETA_AAPP(82),
        PERICLES_CONSULTA_RESOLUCION(83),
        PERICLES_CONSULTA_ESTADO(84),
        CONSULTAR_RESOLUCIONES(85),
        CONSULTAR_ESTADOS(87),
        PERICLES_ENVIO_COMUNICACION(88);


		public static OPERACION getEnum(Integer codigo){
			for(OPERACION sc : values()){
				if (sc.getId()==codigo){
					return sc;
				}
			}
			return null;
		}
		
		
		private int id = -1;
		
		private OPERACION(int id) {			
			this.id = id;
		}
		public int getId() {
			return this.id;
		}
	}
    

    // Estados certificación
    public enum ESTADO_CERTIFICACION {

        ESTADO_CERTIFICACION_ABIERTA("1"),
        ESTADO_CERTIFICACION_VALIDANDO("2"),
        ESTADO_CERTIFICACION_NO_VALIDADA("3"),
        ESTADO_CERTIFICACION_VALIDADA("4"),
        ESTADO_CERTIFICACION_ENVIANDO("5"),
        ESTADO_CERTIFICACION_ENVIO_CON_ERRORES("6"),
        ESTADO_CERTIFICACION_CERRADA("7");

        private String codigo;

        ESTADO_CERTIFICACION(String codigo) {
            this.codigo = codigo;
        }

        public String getCodigo() {
            return this.codigo;
        }
    }

    // Procesos certificación
    public enum PROCESO_CERTIFICACION {

        PROCESO_CERTIFICACION_JUSTIFICACION("873016"),
        PROCESO_CERTIFICACION_REINTEGRO("873017"),
        PROCESO_CERTIFICACION_CERTIFICACION("873018");

        private String recurso;

        PROCESO_CERTIFICACION(String recurso) {
            this.recurso = recurso;
        }

        public String getRecurso() {
            return this.recurso;
        }
    }

    public static final String SEQUENCE_CERTIFICACIONES = "SEQ_FCS_CERTIFICACIONES";

	public static String ACUERDO = "Acuerdo";
	public static String FIN = "Fin";
	public static String FIRMEZA = "Firmeza";
	public static String IMPOSICION = "Imposicion";
	public static String INICIO = "Inicio";
	public static String REHABILITADO = "Rehabilitado";
	public static String RESOLUCION = "Resolucion";

	public static String COMBO_INSTITUCIONES = "instituciones";

	public static String COMBO_PERFILES = "perfiles";
	public static String TIPO_PERSONA_NOTARIO = "Notario";

	public static Integer IdUsuarioPuertaAtras = 1;
	public static String OK = "OK";
	public static String KO = "KO";

	public static Integer CODE_200 = 200;
	public static Integer CODE_400 = 400;

	public static String MAX_SIZE_FILE = "10485760";
	public static String InstitucionGeneral = "2000";
	public static String Personal = "Personal";

	public static String DB_FALSE = "0";
	public static String DB_TRUE = "1";
	public static final String IP_ACCESO_SERVICIO_CARGAS = "IP_ACCESO_SERVICIO_CARGAS";
	public static final String ACTIVAR_CLIENTE_SERVICIO_CARGAS = "ACTIVAR_CLIENTE_SERVICIO_CARGAS";
	public static final String DESFASE_PROGRAMACION_ENVIO_MINUTOS = "DESFASE_PROGRAMACION_ENVIO_MINUTOS";
	public static final String NUM_MAXIMO_MODELOS_SELECCIONADOS = "dialogo.modelocomunicacion.seleccionMax";
	public static final String TINY_APIKEY = "comunicaciones.tinyapikey";
	public static final String FICHERO_SIGA = "SIGA";
	public static final String LENGUAJE_DEFECTO = "1";

	// DOCUMENTOS

	public static String SUFIJO_MODULO_COM_DUPLICADO = "_Copia";
	public static String SUFIJO_CONSULTA_COM_DUPLICADO = "_Copia";
	public static int NOMBRE_MAXLENGTH = 100;
	/*
	 * public static String rutaficherosInformesYcomunicaciones =
	 * "/Datos/SIGA/ficheros/comunicaciones/"; public static String
	 * rutaExcelConsultaTemp= "/Datos/SIGA/ficheros/temporal/"; public static String
	 * carpetaDocumentosEnvio = "/documentosEnvio/"; public static String
	 * carpetaPlantillasDocumento = "/plantillaDocumentos/";
	 */
	public static String carpetaDocumentosEnvio = "documentosEnvio";
	public static String carpetaTmp = "temp";
	public static String parametroRutaPlantillas = "informes.directorioFisicoPlantillaInformesJava";
	public static String parametroRutaSalidaInformes = "informes.directorioFisicoSalidaInformesJava";
	public static String parametroRutaCalendarios = "sjcs.directorioFisicoGeneracionCalendarios";
	public static String rutaPlantillaSinClase = "plantillasSinClase";
	public static String parametroSizePlantillas = "gen.ficheros.maxsize.bytes";
	
	public static String pathAbsolutoFiler = "path.absoluto.origenFiler"; 
	public static String pathRelativoTemp = "path.relativo.temporalesDescarga"; 

	public static String pathSeparator = "/";

	public static String nombreExcelConsulta = "ResultadoConsulta";
	public static String nombreZip = "DocumentosComunicacion";
	public static String rutaLicencia = "/WEB-INF/Aspose.Words.lic";

	public static String REPLACECHAR_PREFIJO_SUFIJO = "%%";
	public static String CLAVES_QUERY = "CLAVES";
	public static String WHERE_VALUE = "whereValue";
	public static String CAMPO_NOMBRE = "NOMBRE";
	public static String CAMPO_APELLIDOS = "APELLIDOS";
	public static String CAMPO_APELLIDO1 = "APELLIDO1";
	public static String CAMPO_APELLIDO2 = "APELLIDO2";
	public static String CAMPO_NUM_COLEGIADO = "NCOLEGIADO";
	public static String CAMPO_IDENTIFICACION = "INDENTIFICACIONENTIDAD";
	public static String CAMPO_IDINSTITUCION = "IDINSTITUCION";
	public static String CAMPO_CP = "CP";
	public static String IDINSTITUCION_0 = "0";
	public static Short IDINSTITUCION_0_SHORT = Short.valueOf(IDINSTITUCION_0);
	public static String SI = "SI";
	public static String NO = "NO";
	
	public static String minutosRevisionEnviosProcesando = "minutos.revision.envios.procesando"; 
	
	// Para la busqueda de criterios dinamicos de las consultas
	public static final String TIPONUMERO = "N";
	public static final String TIPOTEXTO = "A";
	public static final String TIPOFECHA = "D";
	public static final String TIPOFECHANULA = "X";
	public static final String TIPOMULTIVALOR = "MV";
	public static final String TIPOENVIO = "E";

	public static final String ETIQUETATIPONUMERO = "%%NUMERO%%";
	public static final String ETIQUETATIPOTEXTO = "%%TEXTO%%";
	public static final String ETIQUETATIPOFECHA = "%%FECHA%%";
	public static final String ETIQUETATIPOMULTIVALOR = "%%MULTIVALOR@";
	public static final String ETIQUETATIPOENVIO = "%%TIPOENVIO%%";
	public static final String ETIQUETAOPERADOR = "%%OPERADOR%%";

	public static final String NOMBRETIPOENVIO = "TIPO ENVIO";
	public static final String IS_NULL = "IS NULL";
	public static final String LIKE = "LIKE";
	public static final String NOMBRETABLA_CEN_CLIENTE = "CEN_CLIENTE";
	public static final String NOMBRETABLA_CEN_COLEGIADO = "CEN_COLEGIADO";

	public static final String ALIASIDPERSONA = "IDPERSONA";
	public static final String ALIASCORREO = "CORREOELECTRONICO";
	public static final String ALIASDOMICILIO = "DOMICILIO";
	public static final String ALIASMOVIL = "MOVIL";
	public static final String ALIASCODIGOPOSTAL = "CODIGOPOSTAL";
	public static final String ALIASIDPAIS = "IDPAIS";
	public static final String ALIASIDPOBLACION = "IDPOBLACION";
	public static final String ALIASIDPROVINCIA = "IDPROVINCIA";
	public static final String ALIASPOBLACIONEXTRANJERA = "POBLACIONEXTRANJERA";
	public static final String ALIASNOMBRE = "NOMBRE";
	public static final String ALIASAPELLIDOS1 = "APELLIDOS1";
	public static final String ALIASAPELLIDOS2 = "APELLIDOS2";
	public static final String ALIASNIFCIF = "NIFCIF"; 
	
	public static final String ECOS_PREFIJO_ESPANA = "(+34)";
	public static final long ID_OBJETIVO_DESTINATARIOS = 1;
	public static final int COLA_CAMBIO_LETRADO_APROBACION_COLEGIACION = 10;
	public static final int COLA_CAMBIO_LETRADO_ACTIVACION_RESIDENCIA = 20;
	public static final int COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION = 30;
	public static final int COLA_CAMBIO_LETRADO_BORRADO_DIRECCION = 40;
	public static final int COLA_CAMBIO_LETRADO_LOPD = 50;

	// Tipos de Cambio ColaCambioLetrado

	// estados envio
	public static final Short ENVIO_PENDIENTE_MANUAL = 1;
	public static final Short ENVIO_PROCESADO = 2;
	public static final Short ENVIO_PROCESADO_CON_ERRORES = 3;
	public static final Short ENVIO_PENDIENTE_AUTOMATICO = 4;
	public static final Short ENVIO_PROCESANDO = 5;
	public static final Short ENVIO_ARCHIVADO = 6;

	// tipos envio
	public static final String ID_ENVIO_MAIL = "1";
	public static final String ID_ENVIO_CORREO_ORDINARIO = "2";
	public static final String ID_ENVIO_SMS = "4";
	public static final String ID_ENVIO_BURO_SMS = "5";
	public static final String ID_ENVIO_DOCUMENTACION_LETRADO = "7";

	// Tipo Campos envio
	public static final String ID_CAMPO_ASUNTO = "1";
	public static final String ID_CAMPO_CUERPO = "2";
	public static final String ID_CAMPO_TEXTO_SMS = "1";
	public static final String ID_TIPO_CAMPO_EMAIL = "E";
	public static final String ID_TIPO_CAMPO_SMS = "S";

	// Etiquetas para envios
	public static final String ETIQUETA_DEST_TRATAMIENTO = "TRATAMIENTO";
	public static final String ETIQUETA_DEST_NOMBRE = "NOMBRE";
	public static final String ETIQUETA_DEST_APELLIDO1 = "APELLIDO1";
	public static final String ETIQUETA_DEST_APELLIDO2 = "APELLIDO2";
	public static final String ETIQUETA_DEST_CIFNIF = "CIFNIF";

	public static final String ETIQUETA_IDENVIO = "IDENVIO";
	public static final String ETIQUETA_FECHAACTUAL = "FECHAACTUAL";

	public static final String MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO = "%%";
	public static final String MARCAS_ETIQUETAS_REEMPLAZO_TEXTO = "##";

	// PFD
	public static final String FIRMA_OK = "FIRMA_OK";
	public static final String SOLICITUD_DOCUMENTO_OK = "SOLICITUD_DOCUMENTO_OK";
	public static final String SOLICITUD_DOCUMENTO_KO = "SOLICITUD_DOCUMENTO_KO";
	public static final String URL_HTTP_NO_ENCONTRADA = "URL_HTTP_NO_ENCONTRADA";
	public static final String PFD_URLWS = "PFD_URLWS";
	public static final String PFD_FIRMA_VISIBLE = "PFD_FIRMA_VISIBLE";
	public static final String PFD_FIRMA_RAZON = "PFD_FIRMA_RAZON";
	public static final String PFD_FIRMA_LOCATION = "PFD_FIRMA_LOCATION";
	public static final String PFD_IDCLIENTE = "PFD_IDCLIENTE";
	public static final String SMS_URL_SERVICE = "SMS_URL_SERVICE";
	public static final String SMS_CLIENTE_ECOS = "SMS_CLIENTE_ECOS";
	public static final String NOMBRE_FICHERO_BUROSMS = "Justificante_BUROSMS.pdf";

	public static final String MODULO_SCS = "SCS";
	public static final String MODULO_ADM = "ADM";
	public static final String MODULO_ENV = "ENV";
	public static final String MODULO_GEN = "GEN";
	public static final String MODULO_COM = "COM";

	public static final String TIPO_CEN_PERSONA = "CEN_PERSONA";

	public static final String SENTENCIA_UPDATE = "UPDATE";
	public static final String SENTENCIA_DELETE = "DELETE";
	public static final String SENTENCIA_DROP = "DROP";
	public static final String SENTENCIA_INSERT = "INSERT";
	public static final String SENTENCIA_CREATE = "CREATE";
	public static final String SENTENCIA_ALTER = "ALTER";

	public static final String ID_CLASE_CONSULTA_GENERICA = "5";

	public static final String MODULO_CENSO = "CEN";
	public static final String PARAMETRO_CONTADOR_UNICO = "CONTADOR_UNICO_NCOLEGIADO_NCOMUNIT";


	public static String getTipoUsuario(String rol) {
		if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.PERSONAL.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.PERSONAL.getRecurso())) {
			return ECOM_CEN_ROLES.PERSONAL.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADO.getRecurso())) {
			return ECOM_CEN_ROLES.ABOGADO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.NOEJERCIENTE.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.NOEJERCIENTE.getRecurso())) {
			return ECOM_CEN_ROLES.NOEJERCIENTE.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.CONSEJERO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.CONSEJERO.getRecurso())) {
			return ECOM_CEN_ROLES.CONSEJERO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.DECANO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.DECANO.getRecurso())) {
			return ECOM_CEN_ROLES.DECANO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.DIRECTIVO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.DIRECTIVO.getRecurso())) {
			return ECOM_CEN_ROLES.DIRECTIVO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.MIEMBROJUNTA.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.MIEMBROJUNTA.getRecurso())) {
			return ECOM_CEN_ROLES.MIEMBROJUNTA.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.SECRETARIO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.SECRETARIO.getRecurso())) {
			return ECOM_CEN_ROLES.SECRETARIO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.VICEDECANO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.VICEDECANO.getRecurso())) {
			return ECOM_CEN_ROLES.VICEDECANO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADOEUROPEO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADOEUROPEO.getRecurso())) {
			return ECOM_CEN_ROLES.ABOGADOEUROPEO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.INSCRITO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.INSCRITO.getRecurso())) {
			return ECOM_CEN_ROLES.INSCRITO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADOR.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADOR.getRecurso())) {
			return ECOM_CEN_ROLES.ADMINISTRADOR.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORSOLIDARIO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORSOLIDARIO.getRecurso())) {
			return ECOM_CEN_ROLES.ADMINISTRADORSOLIDARIO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORUNICO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORUNICO.getRecurso())) {
			return ECOM_CEN_ROLES.ADMINISTRADORUNICO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.AUTORIZADO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.AUTORIZADO.getRecurso())) {
			return ECOM_CEN_ROLES.AUTORIZADO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTELEGAL.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTELEGAL.getRecurso())) {
			return ECOM_CEN_ROLES.REPRESENTANTELEGAL.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTEVOLUNTARIO.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTEVOLUNTARIO.getRecurso())) {
			return ECOM_CEN_ROLES.REPRESENTANTEVOLUNTARIO.getCodigo();
		} else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.SIGAADMIN.getDescripcion())
				|| rol.equalsIgnoreCase(ECOM_CEN_ROLES.SIGAADMIN.getRecurso())) {
			return ECOM_CEN_ROLES.SIGAADMIN.getCodigo();
		}
		return "";
	}

	// Tipo direcciones que tienen logica asociada
	public static final int TIPO_DIRECCION_CENSOWEB = 3;
	public static final int TIPO_DIRECCION_DESPACHO = 2;
	public static final int TIPO_DIRECCION_GUIA = 5;
	public static final int TIPO_DIRECCION_GUARDIA = 6;
	public static final int TIPO_DIRECCION_FACTURACION = 8;
	public static final int TIPO_DIRECCION_TRASPASO_OJ = 9;
	public static final int TIPO_DIRECCION_PUBLICA = 4;
	public static final int TIPO_DIRECCION_FORMACION = 14;

	// Estados colegiales
	public static final int ESTADO_COLEGIAL_SINEJERCER = 10;
	public static final int ESTADO_COLEGIAL_EJERCIENTE = 20;
	public static final int ESTADO_COLEGIAL_BAJACOLEGIAL = 30;
	public static final int ESTADO_COLEGIAL_INHABILITACION = 40;
	public static final int ESTADO_COLEGIAL_SUSPENSION = 50;
	public static final int ESTADO_COLEGIAL_ALTA = 1020;// Correspnde a una combinacion del estado 10 y 20;

	// Tipos de Preferente
	public static final String TIPO_PREFERENTE_CORREO = "C";
	public static final String TIPO_PREFERENTE_CORREOELECTRONICO = "E";
	public static final String TIPO_PREFERENTE_FAX = "F";
	public static final String TIPO_PREFERENTE_SMS = "S";

	// Tipo cambio histórico de censo
	public static final int TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_COLEGIAL = 1;
	public static final int TIPO_CAMBIO_HISTORICO_ESTADO_BAJA_COLEGIAL = 2;
	public static final int TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_EJERCICIO = 3;
	public static final int TIPO_CAMBIO_HISTORICO_ESTADO_BAJA_EJERCICIO = 4;
	public static final int TIPO_CAMBIO_HISTORICO_ESTADO_INHABILITACION = 5;
	public static final int TIPO_CAMBIO_HISTORICO_ESTADO_SUSPENSION = 6;
	public static final int TIPO_CAMBIO_HISTORICO_DATOS_GENERALES = 10;
	public static final int TIPO_CAMBIO_HISTORICO_DATOS_COLEGIALES = 20;
	public static final int TIPO_CAMBIO_HISTORICO_DIRECCIONES = 30;
	public static final int TIPO_CAMBIO_HISTORICO_CUENTAS_BANCARIAS = 40;
	public static final int TIPO_CAMBIO_HISTORICO_DATOS_CV = 50;
	public static final int TIPO_CAMBIO_HISTORICO_DATOS_COMPONENTES = 60;
	public static final int TIPO_CAMBIO_HISTORICO_DATOS_FACTURACION = 70;
	public static final int TIPO_CAMBIO_HISTORICO_TURNOS = 80;
	public static final int TIPO_CAMBIO_HISTORICO_EXPEDIENTES = 90;
	public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONMODIFICACION = 101;
	public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONJUSTIFICACION = 102;
	public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONALTAACTUACION = 103;
	public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONMODIFICAACTUACION = 107;
	public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONDELETEACTUACION = 108;

	public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAALTA = 104;
	public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAMODIFICACION = 105;
	public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAALTAACTUACION = 106;

	public static final String PLAZAS_DISPO_SI = "1";

	public static final String PLAZAS_DISPO_NO = "0";

	public static final String AUX_TRANS_TILDES_1 = "áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";

	public static final String AUX_TRANS_TILDES_2 = "aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC";

	public static final String ESTADO_INSCRIPCION_PENDIENTE = "1";
	public static final String ESTADO_INSCRIPCION_RECHAZADA = "2";
	public static final String ESTADO_INSCRIPCION_APROBADA = "3";
	public static final String ESTADO_INSCRIPCION_CANCELADA = "4";

	// FICHA CURSO

	public static final String ESTADO_CURSO_ABIERTO = "0";

	public static final String ESTADO_CURSO_ANUNCIADO = "1";

	public static final String ESTADO_CURSO_EN_CURSO = "2";

	public static final String ESTADO_CURSO_IMPARTIDO = "3";

	public static final String ESTADO_CURSO_FINALIZADO = "4";

	public static final String ESTADO_CURSO_CANCELADO = "5";

	public static final Integer CURSO_SIN_ARCHIVAR = 0;

	public static final Integer CURSO_ARCHIVADO = 1;

	public static final Long EMITIR_CERTIFICADO = new Long("1");
	
	public static final Short DESIGNAR_TUTOR = 0; 
	public static final Short ASIGNAR_TUTOR = 1; 
	public static final long ANUNCIADO_CURSO = 1; 
	public static final long ABIERTO_CURSO = 0;
	public static final long FINALIZADO_CURSO = 4;
	public static final long CANCELADO_CURSO = 5;

	public static final long INSCRIPCION_PENDIENTE = 1;
	public static final long INSCRIPCION_CANCELADA = 4;
	public static final long INSCRIPCION_APROBADA = 3;
	public static final long INSCRIPCION_RECHAZADA = 2;

	public static final String EVENTO_PLANIFICADO = "1";
	public static final String EVENTO_CUMPLIDO = "2";
	public static final String EVENTO_CANCELADO = "3";
	public static final String EVENTO_SESION = "8";

	public static final short ID_TIPO_SERVICIOS_FORMACION = 5;
	public static final short PERIOCIDAD_1MES = 1;
	public static final short MODULO_CONTADOR = 11;

	public static final String CODIGO_CURSO = "CÓDIGO CURSO";
	public static final String FORMA_PAGO = "FORMA PAGO";
	public static final String NIF = "NIF";
	public static final String CIF = "CIF";
	public static final String NIE = "NIE";
	public static final String NOMBRE_PERSONA = "NOMBRE_PERSONA";
	public static final String NOMBRE_CURSO = "NOMBRE_CURSO";
	public static final String ESTADO = "ESTADO";
	public static final String ERRORES = "ERRORES";

	public static final List<String> CAMPOSPLANTILLACURSO = Arrays.asList(CODIGO_CURSO, FORMA_PAGO, NIF);
	public static final List<String> CAMPOSPLOGCURSO = Arrays.asList(CODIGO_CURSO, NOMBRE_CURSO, FORMA_PAGO, NIF,
			NOMBRE_PERSONA, ERRORES);

	// FICHA EVENTOS

	public static final String NOMBRE = "NOMBRE";
	public static final String ASISTENCIA = "ASISTENCIA";

	public static final long TIPO_EVENTO_INICIO_INSCRIPCION = 4;
	public static final long TIPO_EVENTO_FIN_INSCRIPCION = 5;
	public static final long TIPO_EVENTO_SESION = 8;
	public static final long ESTADO_EVENTO_PLANIFICADO = 1;

	public static final List<String> CAMPOSPLANTILLAEVENTOS = Arrays.asList(NIF, NOMBRE, ASISTENCIA);

	// CARGA MASIVA GF

	public static final String ALTA = "A";
	public static final String BAJA = "B";
	public static final String C_IDGRUPO = "IDGRUPO";
	public static final String ACCION = "ACCION";
	public static final String GENERAL = "GENERAL";
	public static final String NOMBREGRUPO = "NOMBREGRUPO";
	public static final String PERSONANOMBRE = "PERSONANOMBRE";
	public static final String C_IDPERSONA = "IDPERSONA";
	public static final String C_IDINSTITUCION = "IDINSTITUCION";
	public static final String PERSONANIF = "PERSONANIFCIF";
	public static final String COLEGIADONUMERO = "COLEGIADONUMERO";
	public static final Short IDINSTITUCION_2000 = 2000;
	public static final Short IDINSTITUCION_3500 = 3500;
	public static final String TIPO_CARGA = "GF";
	public static final String C_FECHAINICIO = "FECHAINICIO";
	public static final String C_FECHAFIN = "FECHAFIN";
	
	//FICHERO CONTABILIDAD
	public static final String CONCEPTO_ASIENTO1		= "facturacionSJCS.facturacionesYPagos.factura"; 	    // Factura
	public static final String CONCEPTO_ASIENTO2		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento2"; 	    // Factura Rectificativa Nº
	public static final String CONCEPTO_ASIENTO2B		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento2B"; 	    // Pago por banco. Factura Rectificativa Nº
	public static final String CONCEPTO_ASIENTO3		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento3"; 	    // Pago por caja. Factura
	public static final String CONCEPTO_ASIENTO3_2		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento3_2"; 	// Pago Anticipado. Factura 
	public static final String CONCEPTO_ASIENTO4		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento4"; 	    // Pago por banco. Factura 
	public static final String CONCEPTO_ASIENTO5		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento5";     // Pago por tarjeta. Factura
	public static final String CONCEPTO_ASIENTO6		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento6"; 	    // Devolucion por banco. Factura Nº
	public static final String CONCEPTO_ASIENTO7		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento7";      // Alta de anticipos
	public static final String CONCEPTO_ASIENTO7A		= "facturacion.exportacionesyotros.contabilidad.conceptoasiento7A";      // Liquidación de anticipos de Letrado por Baja Colegial

	public static final String CONCEPTO_ASIENTO3_2010   = "facturacion.exportacionesyotros.contabilidad.conceptoasiento3_2010";  // Compensación por caja
	
	
	
	public static final List<String> CAMPOSEJEMPLOGF = Arrays.asList(COLEGIADONUMERO, PERSONANIF, C_IDGRUPO, GENERAL,
			ACCION, C_FECHAINICIO);

	public static final List<String> CAMPOSLOGGF = Arrays.asList(COLEGIADONUMERO, PERSONANIF, PERSONANOMBRE,
			C_IDPERSONA, C_IDGRUPO, GENERAL, NOMBREGRUPO, ACCION, C_FECHAINICIO, ERRORES);
	

	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx = "xlsx";
	public static final String nombreFicheroEjemplo = "PlantillaMasivaDatosGF";
	public static final String nombreFicheroError = "LogErrorCargaMasivaGF";

	public static final String NOTA_SUSPENSO = "1";
	public static final String NOTA_APROBADO = "2";
	public static final String NOTA_BIEN = "3";
	public static final String NOTA_NOTABLE = "4";
	public static final String NOTA_SOBRESALIENTE = "5";

	// CARGA MASIVA CV

	public static final String C_IDTIPOCV = "IDTIPOCV";
	public static final String C_CREDITOS = "CREDITOS";
	public static final String C_IDTIPOCVSUBTIPO1 = "IDTIPOCVSUBTIPO1";
	public static final String C_IDTIPOCVSUBTIPO2 = "IDTIPOCVSUBTIPO2";
	public static final String TIPOCVCOD = "TIPOCVCOD";
	public static final String SUBTIPOCV1COD = "SUBTIPOCV1COD";
	public static final String SUBTIPOCV2COD = "SUBTIPOCV2COD";
	public static final String TIPOCVNOMBRE = "TIPOCVNOMBRE";
	public static final String SUBTIPOCV1NOMBRE = "SUBTIPOCV1NOMBRE";
	public static final String SUBTIPOCV2NOMBRE = "SUBTIPOCV2NOMBRE";
	public static final String FECHAVERIFICACION = "FECHAVERIFICACION";
	public static final String C_DESCRIPCION = "DESCRIPCION";

	public static final String nombreFicheroEjemploCV = "PlantillaMasivaDatosCV";

	public static final List<String> CAMPOSEJEMPLOCV = Arrays.asList(COLEGIADONUMERO, PERSONANIF, C_FECHAINICIO,
			C_FECHAFIN, C_CREDITOS, FECHAVERIFICACION, C_DESCRIPCION, TIPOCVCOD, SUBTIPOCV1COD, SUBTIPOCV2COD);
	public static final List<String> CAMPOSLOGCV = Arrays.asList(COLEGIADONUMERO, PERSONANIF, PERSONANOMBRE,
			C_IDPERSONA, C_FECHAINICIO, C_FECHAFIN, C_CREDITOS, FECHAVERIFICACION, C_DESCRIPCION, TIPOCVCOD,
			TIPOCVNOMBRE, C_IDTIPOCV, SUBTIPOCV1COD, SUBTIPOCV1NOMBRE, C_IDTIPOCVSUBTIPO1, SUBTIPOCV2COD,
			SUBTIPOCV2NOMBRE, C_IDTIPOCVSUBTIPO2, ERRORES);
	public static String EVENTO_TIPO_FIESTA_NACIONAL = "Fiesta Nacional";

	// CARGA MASIVA INSCRIPCIONES
	public static final String IT_TURNO = "TURNO";
	public static final String IT_GUARDIA = "GUARDIA";
	public static final String IT_NCOLEGIADO = "Nº COLEGIADO";
	public static final String IT_FECHAEFECTIVA = "FECHA EFECTIVA";
	public static final String IT_TIPO = "TIPO";
	public static final String IT_GRUPO = "GRUPO";
	public static final String IT_ORDEN = "ORDEN";

	public static final String nombreFicheroModeloIT = "PlantillaMasivaDatosIT";

	public static final List<String> CAMPOSMODEL_IT = Arrays.asList(IT_TURNO, IT_GUARDIA, IT_NCOLEGIADO,
			IT_FECHAEFECTIVA, IT_TIPO, IT_GRUPO, IT_ORDEN);
	public static final List<String> CAMPOSLOGIT = Arrays.asList(IT_TURNO, IT_GUARDIA, IT_NCOLEGIADO, IT_FECHAEFECTIVA,
			IT_TIPO, IT_GRUPO, IT_ORDEN, ERRORES);

	// CARGA MASIVA BAJAS TEMPORALES

	public static final String BT_NCOLEGIADO = "Nº COLEGIADO";
	public static final String BT_NIF = "NIF";
	public static final String BT_TIPO = "TIPO SOLICITUD";
	public static final String BT_MOTIVO = "MOTIVO";
	public static final String BT_FECHAI = "FECHA INICIO";
	public static final String BT_FECHAF = "FECHA FIN";

	public static final String nombreFicheroModeloBT = "PlantillaMasivaDatosBT";

	public static final List<String> CAMPOSMODEL_BT = Arrays.asList(BT_NCOLEGIADO, BT_NIF, BT_TIPO, BT_MOTIVO,
			BT_FECHAI, BT_FECHAF);
	public static final List<String> CAMPOSLOGBT = Arrays.asList(BT_NCOLEGIADO, BT_NIF, BT_TIPO, BT_MOTIVO, BT_FECHAI,
			BT_FECHAF, ERRORES);
	
	// CARGA MASIVA COMPRAS

	public static final String CP_NCOLEGIADO = "Nº COLEGIADO";
	public static final String CP_NIF = "NIF";
	public static final String CP_APELLIDOS_CLI = "APELLIDOS CLIENTE";
	public static final String CP_NOMBRE_CLI = "NOMBRE CLIENTE";
	public static final String CP_CANT_PROD = "CANTIDAD PRODUCTO";
	public static final String CP_NOMBRE_PROD = "NOMBRE PRODUCTO";
	public static final String CP_COD_PROD = "CODIGO PRODUCTO";
	public static final String CP_FECHA_COMP = "FECHA COMPRA";

	public static final String nombreFicheroModeloCP = "PlantillaMasivaCompras";

	public static final List<String> CAMPOSMODEL_CP = Arrays.asList(CP_NCOLEGIADO, CP_NIF, CP_APELLIDOS_CLI, CP_NOMBRE_CLI,
			CP_CANT_PROD, CP_NOMBRE_PROD, CP_COD_PROD, CP_FECHA_COMP);
	public static final List<String> CAMPOSLOGCP = Arrays.asList(CP_NCOLEGIADO, CP_NIF, CP_APELLIDOS_CLI, CP_NOMBRE_CLI,
			CP_CANT_PROD, CP_NOMBRE_PROD, CP_COD_PROD, CP_FECHA_COMP, ERRORES);

	// CARGA MASIVA PROCURADORES
	
	public static final String PD_CODIGODESIGNAABOGADO= "CODIGODESIGNAABOGADO";
	public static final String PD_NUMEJG = "NUMEJG";
	public static final String PD_NUMCOLPROCURADOR = "NUMCOLPROCURADOR";
	public static final String PD_FECHADESIGPROCURADOR = "FECHADESIGPROCURADOR";
	public static final String PD_NUMDESIGNAPROCURADOR = "NUMDESIGNAPROCURADOR";
	public static final String PD_OBSERVACIONES = "OBSERVACIONES";
	
	public static final List<String> CAMPOSMODEL_PD = Arrays.asList(PD_CODIGODESIGNAABOGADO, PD_NUMEJG, PD_NUMCOLPROCURADOR,
			PD_FECHADESIGPROCURADOR, PD_NUMDESIGNAPROCURADOR, PD_OBSERVACIONES);
	public static final List<String> CAMPOSLOG_PD = Arrays.asList(PD_CODIGODESIGNAABOGADO, PD_NUMEJG, PD_NUMCOLPROCURADOR,
			PD_FECHADESIGPROCURADOR, PD_NUMDESIGNAPROCURADOR, PD_OBSERVACIONES, ERRORES);

	// CARGA MASIVA INSCRIPCIONES - Guardia
		public static final String TURNO = "TURNO";
		public static final String GUARDIAFIELD = "GUARDIA";
		public static final String NCOLEGIADO = "Nº COLEGIADO";
		public static final String FECHAEFECTIVA = "FECHA EFECTIVA";
		public static final String TIPO = "TIPO";
		public static final String GRUPO = "GRUPO";
		public static final String ORDEN = "ORDEN";

		public static final String nombreFicheroModeloI = "PlantillaMasivaDatosI";

		public static final List<String> CAMPOSMODEL_I = Arrays.asList(TURNO, GUARDIAFIELD, NCOLEGIADO,
				FECHAEFECTIVA, TIPO, GRUPO, ORDEN);
		public static final List<String> CAMPOSLOGI = Arrays.asList(TURNO, GUARDIAFIELD, NCOLEGIADO, FECHAEFECTIVA,
				TIPO, GRUPO, ORDEN, ERRORES);
		// CARGA MASIVA GRUPOS COLA - Guardia
		public static final String nombreFicheroModeloGC = "PlantillaMasivaDatosGC";
		public static final List<String> CAMPOSMODEL_GC = Arrays.asList(TURNO, GUARDIAFIELD, NCOLEGIADO,
				 GRUPO, ORDEN);
		public static final List<String> CAMPOSLOGGC = Arrays.asList(TURNO, GUARDIAFIELD, NCOLEGIADO,
				 GRUPO, ORDEN, ERRORES);
		// CARGA MASIVA CALENDARIOS - Guardia

		public static final String FECHAI = "FECHA INICIO";
		public static final String FECHAF = "FECHA FIN";

		public static final String nombreFicheroModeloC = "PlantillaMasivaDatosC";

		public static final List<String> CAMPOSMODEL_C = Arrays.asList(TURNO, GUARDIAFIELD, NCOLEGIADO,
				FECHAI, FECHAF);
		public static final List<String> CAMPOSLOGC = Arrays.asList(TURNO, GUARDIAFIELD, NCOLEGIADO,
				FECHAI, FECHAF, ERRORES);
	// AGENDA
	public static final long CALENDARIO_GENERAL = 1;
	public static final long CALENDARIO_LABORAL = 2;
	public static final long CALENDARIO_FORMACION = 3;

	// AVISOS PARA NOTIFICACIONES EN EVENTOS
	public static final String AVISO_CANCELAR_CURSO = "1";
	public static final String AVISO_MODIFICAR_CURSO = "2";
	public static final String AVISO_PLAZAS_DISPONIBLES = "3";
	public static final String AVISO_CANCELAR_SESION = "4";
	public static final String AVISO_MODIFICAR_SESION = "5";

	// NOTIFICACIONES
	public static final long NOTIFICACION_TIPOCUANDO_ANTES = 1;
	public static final long NOTIFICACION_TIPOCUANDO_DESPUES = 2;

	public static final long NOTIFICACION_HORAS = 1;
	public static final long NOTIFICACION_MINUTOS = 2;
	public static final long NOTIFICACION_DIAS = 3;

	// SOLICITUD INCORPORACION
	public static final String TIPO_DIR_RESIDENCIA = "1";
	public static final String TIPO_DIR_DESPACHO = "2";
	public static final String TIPO_DIR_CENSOWEB = "3";
	public static final String TIPO_DIR_PUBLICA = "4";
	public static final String TIPO_DIR_GUIAJUDICIAL = "5";
	public static final String TIPO_DIR_GUARDIA = "6";
	public static final String TIPO_DIR_REVISTA = "7";
	public static final String TIPO_DIR_FACTURACION = "8";
	public static final String TIPO_DIR_TRASPASO = "9";
	public static final String TIPO_DIR_PREFERENTE_EMAIL = "10";
	public static final String TIPO_DIR_PREFERENTE_CORREO = "11";
	public static final String TIPO_DIR_PREFERENTE_SMS = "12";
	public static final String TIPO_DIR_PREFERENTE_FAX = "13";
	public static final String TIPO_DIR_FORMACION = "14";

	public static final String DIR_PREFERENTE_EMAIL = "E";
	public static final String DIR_PREFERENTE_CORREO = "C";
	public static final String DIR_PREFERENTE_SMS = "S";
	public static final String DIR_PREFERENTE_FAX = "F";

	public static final short REINCORPORACION_EJERCIENTE = 10;
	public static final short REINCORPORACION_NO_EJERCIENTE = 20;
	public static final short INCORPORACION_EJERCIENTE = 30;
	public static final short INCORPORACION_NO_EJERCIENTE = 40;

	public static final long TIPO_NOTIFICACION_INICIOINSCRIPCION = 3;
	public static final long TIPO_NOTIFICACION_FININSCRIPCION = 4;
	public static final long TIPO_NOTIFICACION_SESION = 7;

	public static final String CENSO_WS_SITUACION_EJERCIENTE = "EJERCIENTE";
	public static final String CENSO_WS_SITUACION_NOEJERCIENTE = "NOEJERCIENTE";
	public static final String CENSO_WS_SITUACION_BAJACOLEGIO = "BAJACOLEGIO";
	public static final String CENSO_WS_SITUACION_INSCRITO = "INSCRITO";



	public static String DATEST_FORMAT_MIN = "dd/MM/yyyy HH:mm";
	public static String DATEST_FORMAT_MIN_SEC = "dd/MM/yyyy HH:mm:ss";
	public static String DATEST_FORMAT_ONLYDATE = "dd/MM/yyyy";

	public static DateFormat DATE_FORMAT_MIN = new SimpleDateFormat(DATEST_FORMAT_MIN);
	public static DateFormat DATE_FORMAT = new SimpleDateFormat(DATEST_FORMAT_ONLYDATE);

	public static String[] columnsExcelLogEnvios = new String[] { "ENVIO", "DESCRIPCION", "FECHA ENVÍO", "REMITENTE",
			"CORREO REMITENTE", "NIF/CIF", "NOMBRE", "APELLIDO 1", "APELLIDO 2", "MOVIL", "CORREO ELECTRONICO",
			"MENSAJE", "DOCUMENTOS" };

	public static String ENVIOS_MASIVOS_LOG_NOMBRE_FICHERO = "informeEnvio.log";


	public static String EXPRESION_REGULAR_MAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,10}$";
	public static String EXPRESION_REGULAR_MAIL2 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static String EVENTO_TIPO_FESTIVO = "9";

	public static final String JUSTICIABLE_ROL_SOLICITANTE = "1";
	public static final String JUSTICIABLE_ROL_CONTRARIO = "2";
	public static final String JUSTICIABLE_ROL_REPRESENTANTE = "3";
	public static final String JUSTICIABLE_ROL_UNIDADFAMILIAR = "4";

	public static final String TIPO_ASUNTO_EJG = "E";
	public static final String TIPO_ASUNTO_DESIGNA = "D";
	public static final String TIPO_ASUNTO_ASISTENCIA = "A";
	public static final String TIPO_ASUNTO_SOJ = "S";

	public static final String TIPO_ESTADO_EJG_DICTAMINADO = "6";
	public static final String TIPO_ESTADO_EJG_RESUELTO_COMISION = "10";
	public static final String TIPO_ESTADO_EJG_RESUELTA_IMPUGNACION = "13";

	public static final String SCS_JUSTICIABLE = "0";
	public static final String SCS_SOLICITANTE_EJG = "1";
	public static final String SCS_UNIDAD_FAMILIAR_EJG = "2";
	public static final String SCS_CONTRARIO_EJG = "3";
	public static final String SCS_CONTRARIO_DESIGNACION = "4";
	public static final String SCS_CONTRARIO_ASISTENCIA = "5";
	public static final String SCS_SOLICITANTE_DESIGNACION = "6";
	public static final String SCS_SOLICITANTE_ASISTENCIA = "7";
	public static final String SCS_SOLICITANTE_SOJ = "8";
	public static String EXPRESION_REGULAR_MOVIL = "(\\+34|0034|34|\\(\\+34\\)|\\(0034\\)|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}";
	public static final String ROL_SOLICITANTE_EJG = "SE";
	public static final String ROL_SOLICITANTE_ASISTENCIA = "SA";
	public static final String ROL_SOLICITANTE_DESIGNACION = "SD";
	public static final String ROL_SOLICITANTE_SOJ = "SS";
	public static final String ROL_UNIDAD_FAMILIAR_EJG = "UE";
	public static final String ROL_CONTRARIO_EJG = "CE";
	public static final String ROL_CONTRADIO_DESIGNACION = "CD";
	public static final String ROL_CONTRARIO_ASISTENCIA = "CA";
	public static final String ROL_REPRESENTANTE = "RE";

	public static final Short ID_INSTITUCION_0 = 0;

	public static Integer USUMODIFICACION_0 = 0;
	public static int ECOM_COLA_HORAS_EN_EJECUCION_MAXIMAS = 2;
	
	public static final String TAM_MAX_CONSULTA_JG = "TAM_MAX_CONSULTA_JG";
	

	public static final String I_INFORMEFACTSJCS = "FACJ2";

	// hitos generales de facturacion
	public static final int HITO_GENERAL_TURNO = 10;
	public static final int HITO_GENERAL_GUARDIA = 20;
	public static final int HITO_GENERAL_SOJ = 30;
	public static final int HITO_GENERAL_EJG = 40;
	public static final String GUARDIA = "GUARDIA";
	public static final String OFICIO = "OFICIO";
	public static final String EJG = "EJG";
	public static final String GESTIONEJG = "GESTIONEJG";
	public static final String EJGEXPRESS = "EJGEXPRESS";

	// si es facturacion o pago
	public static final String FACTURACION_SJCS = "F";
	public static final String PAGOS_SJCS = "P";

	// se ha facturado todo el procedimiento o solo un porcentaje
	public static final String FACTURACION_COMPLETA = "C";
	public static final String FACTURACION_PARCIAL = "P";

	//EEJG
	public static String EEJG_IDSISTEMA ="EEJG_IDSISTEMA";
	public static String EEJG_URLWS ="EEJG_URLWS";
	public static String SIGAFRONT_VERSION = "202109071016";
	public static String SIGAWEB_VERSION = "1.0.82_18";
	
	public static final String ACREDITACION_TIPO_INICIO = "1";
	public static final String ACREDITACION_TIPO_FIN = "2";
	public static final String ACREDITACION_TIPO_COMPLETA = "3";
	
	public static String DEFAULT_EMAIL_FROM = "DEFAULT_EMAIL_FROM";
	
	//Remesa Resultados
	public static final String CONTADOR_REMESAS_RESULTADOS= "REMESARESULTEJG";
	public static final String MAX_NUM_LINEAS_FICHERO = "MAX_NUM_LINEAS_FICH";
	
	//Contadores
	public static final String C_PPN_IDTIPOPRODUCTO = "PPN_IDTIPOPRODUCTO";
    public static final String C_PPN_IDPRODUCTO = "PPN_IDPRODUCTO";
    public static final String C_PPN_IDPRODUCTOINSTITUCION = "PPN_IDPRODUCTOINSTITUCION";
    
	public static final String C_IDFACTURACION = "IDFACTURACION";

	// Estados de FAC_ABONO
    public static final Short FAC_ABONO_ESTADO_PAGADO = 1;
    public static final Short FAC_ABONO_ESTADO_PENDIENTE_BANCO = 5;
    public static final Short FAC_ABONO_ESTADO_PENDIENTE_CAJA = 6;
    public static final Short FAC_ABONO_DESTINATARIOABONO_SOCIEDAD = 0;
    public static final Short FAC_ABONO_DESTINATARIOABONO_SJCS = 1;
    public static final Short FAC_ABONO_DESTINATARIOABONO_NORMAL = 2;

    public static final String PCAJG_ALC_CAM_PATH = "PCAJG_ALC_CAM_PATH";
    public static final String IDFACTURACION = "IDFACTURACION";

    public static final String  PARAMETRO_FACTURACION_DIRECTORIO_FISICO_PLANTILLA_FACTURA_JAVA = "facturacion.directorioFisicoPlantillaFacturaJava";
    public static final String  PARAMETRO_FACTURACION_DIRECTORIO_PLANTILLA_FACTURA_JAVA = "facturacion.directorioPlantillaFacturaJava";

	//Expedientes - INICIO
	public static final String RECURSO_MENU_EXP_EXEA = "menu.expedientesexea";
	public static final String PARAM_MENU_EXEA_ACTIVO = "EXPEDIENTES_EXEA_ACTIVOS";
	public static final String ID_APLICACION_PARAM = "ID_APLICACION";
	public static final String EXEA_AUTENTICACION_URL_PARAM = "URL_EXEA_AUTENTICACION";
	public static final String EXEA_SYNC_IP_PARAM = "EXEA_SYNC_IPS";
	public static final String EXEA_WEBSERVICES_ADDIN_PARAM = "URL_WEBSERVICES_ADDIN";
	public static final String EXEA_NOMBRE_FORM_BUSQ = "NOMBRE_FORM_BUSQUEDA";
	public static final String EXEA_NOMBRE_GRUPO = "NOMBRE_GRUPO";
	public static final String EXEA_URL_WEBSERVICES_REGTEL = "URL_WEBSERVICES_REGTEL";
	public static final String ID_SEDE_PARAM = "ID_SEDE";
	public static final String EXPEDIENTE_ACEPTADO_EXEA = "Aceptado";
	public static final String EXPEDIENTE_DENEGADO_EXEA = "Rechazado";
	public static final short SANCION_EN_SUSPENSO = 8;
	public static final short INCORPORACION_PENDIENTE_APROBACION = 20;
	public static final short INCORPORACION_PENDIENTE_DOCUMENTACION = 10;
	public static final String COD_DOC_ANEXO_PARAM = "COD_DOC_ANEXO";

	public enum ERROR_SINCRONIZACION_EXEA {
		FORMATO_NOVALIDO("Formato XML de petición no correcto."),
		SERV_NODISPONIBLE("Servicio no disponible."),
		IP_NOVALIDA("La IP desde la que se ha recibido la petición no está autorizada."),
		IDENTIFICACION_NOVALIDA("La identificación del colegiado no es válida."),
		COLEGIO_NOVALIDO("El código del colegio recibido no es válido."),
		NUMCOLEGIADO_NOVALIDO("Número de colegiado no válido para el colegio indicado."),
		COLEGIADO_NOENCONTRADO("No se encuentra en el sistema ningún colegiado con la identificación facilitada."),
		COLEGIADO_ENCONTRADO("No es posible el alta del colegiado debido a que existe ya en el sistema."),
		POBLACION_NOENCONTRADA("Población indicada no ha sido identificada."),
		PROVINCIA_NOVALIDA("Provincia desconocida."),
		PAIS_NOVALIDO("País desconocido."),
		TIPOVIA_NOVALIDA("Tipo de vía desconocida."),
		SANCION_NOENCONTRADA("La sanción correspondiente a la referencia recibida no ha sido encontrada en el sistema."),
		EXPEDIENTE_NOENCONTRADO("El expediente recibido en la petición no ha sido encontrado en el sistema."),
		OTRO_ERROR("Se ha producido un error en el procesado de la petición.");

		private String mensajeError = null;

		private ERROR_SINCRONIZACION_EXEA(String mensajeError) {
			this.mensajeError = mensajeError;
		}

		public String getMensajeError() {
			return mensajeError;
		}
	}

	//Expedientes - FIN

    public static final String PARAMETRO_LOG_COLALETRADOS_LEVEL = "log.colaLetrados.level";

    public enum FASES_PROCESO_FACTURACION_AUTOMATICA_PYS {
        TRATAR_FACTURACION("1"),
        TRATAR_CONFIRMACION("2"),
        GENERAR_PDFS_Y_ENVIAR_FACTURAS_PROGRAMACION("3"),
        GENERAR_ENVIOS_FACTURAS_PENDIENTES("4"),
        COMPROBACION_TRASPASO_FACTURAS("5");

        private String codigo;

        FASES_PROCESO_FACTURACION_AUTOMATICA_PYS(String codigo) {
            this.codigo = codigo;
        }

        public String getCodigo() {
            return this.codigo;
        }
    }

    public static final String MODULO_ECOM = "ECOM";

    public enum ENV_ENVIOS_ESTADOS {
        ESTADO_INICIAL((short) 1),
        ESTADO_PROCESADO((short) 2),
        ESTADO_PROCESADO_ERRORES((short) 3),
        ESTADO_PENDIENTE_AUTOMATICO((short) 4),
        ESTADO_PROCESANDO((short) 5);

        private short id;

        ENV_ENVIOS_ESTADOS(short id) {
            this.id = id;
        }

        public short getId() {
            return this.id;
        }

    }

    public static final String TIPODESTINATARIO_CENPERSONA = "CEN_PERSONA";

    public static final String K_TIPOCAMPO_F="F";
    public static final String K_TIPOCAMPO_E="E";
    public static final String K_TIPOCAMPO_A="A";
    public static final String K_TIPOCAMPO_S="S";

    public static final String K_IDCAMPO_ASUNTO="1";
    public static final String K_IDCAMPO_CUERPO="2";
    public static final String K_IDCAMPO_SMS="1";

    public static final String ID_PAIS_ESPANA ="191";

    public static final int K_CORREO_ELECTRONICO = 1;
    public static final int K_CORREO_ORDINARIO = 2;
    public static final int K_FAX = 3;
    public static final int K_SMS = 4;
    public static final int K_BUROSMS = 5;
    public static final int K_ENVIOTELEMATICO = 6;
    public static final int K_DOCUMENTACIONLETRADO  = 7;

    public static final String TIPODESTINATARIO_SCSPERSONAJG = "SCS_PERSONAJG";
    public static final String TIPODESTINATARIO_SCSJUZGADO = "SCS_JUZGADO";
    public static final String TIPODESTINATARIO_SCSPROCURADOR = "SCS_PROCURADOR";
    public static final String TIPODESTINATARIO_SCSCONTRARIOSJG = "SCS_CONTRARIOSJG";

    public static final String DATE_FORMAT_JAVA = "yyyy/MM/dd HH:mm:ss";

    public static final String PATH_DOCUMENTOSADJUNTOS = "PATH_DOCUMENTOSADJUNTOS";

    public static final String ESTADO_TRASPASADA_ERROR = "E";
    public static final String ESTADO_TRASPASADA_NAV_OK = "1";

    public static final String PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_FROM = "TRASPASO_FACTURAS_MAILRESUMEN_FROM";
    public static final String PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_BCC = "TRASPASO_FACTURAS_MAILRESUMEN_BCC";
    public static final String PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_BODY = "TRASPASO_FACTURAS_MAILRESUMEN_BODY";
    public static final String PARAMETRO_TRASPASO_FACTURAS_MAILRESUMEN_ASUNTO = "TRASPASO_FACTURAS_MAILRESUMEN_ASUNTO";

    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_ACTUALIZACIONCENSO_HOST = "mail.smtp.actualizacioncenso.host";
    public static final String MAIL_SMTP_ACTUALIZACIONCENSO_PORT = "mail.smtp.actualizacioncenso.port";
    public static final String MAIL_SMTP_ACTUALIZACIONCENSO_USER = "mail.smtp.actualizacioncenso.user";
    public static final String MAIL_SMTP_ACTUALIZACIONCENSO_PWD = "mail.smtp.actualizacioncenso.pwd";

    // Estado peticion compra
    public static final String ESTADO_PETICION_COMPRA_PENDIENTE = "10";
    public static final String ESTADO_PETICION_COMPRA_PROCESADA = "20";
    public static final String ESTADO_PETICION_COMPRA_BAJA	= "30";

    // Tipo de peticion compra
    public static final String TIPO_PETICION_COMPRA_ALTA = "A";
    public static final String TIPO_PETICION_COMPRA_BAJA = "B";

    // Estado Productos
    public static final String PRODUCTO_PENDIENTE = "P";
    public static final String PRODUCTO_ACEPTADO = "A";
    public static final String PRODUCTO_DENEGADO = "D";
    public static final String PRODUCTO_BAJA = "B";
    public static final String PRODUCTO_PENDIENTE_GENERAR_PDF= "G";

    public static final String TIMESTAMP_BBDD = "yyyy-MM-dd HH:mm:ss";
	public static final String OBS_IMPORTADO_EJG = "IMPORTADO DESDE EJG";

    public static enum EEJG_ESTADO {
        INICIAL (10, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.inicial"),
        INICIAL_ESPERANDO (15, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.inicialEsperando"),
        ESPERA (20, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.espera"),
        ESPERA_ESPERANDO (25, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.esperaEsperando"),
        PENDIENTE_INFO (23, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.pendienteInfo"),
        FINALIZADO (30, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.finalizado"),
        ERROR_SOLICITUD (40, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.errorSolicitud"),
        ERROR_CONSULTA_INFO (50, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.errorConsultaInfo"),
        CADUCADO (60, "justiciaGratuita.ejg.solicitante.solicitudExpEconomico.estado.caducado");

        private long id = -1;
        private String messageToTranslate = "";

        EEJG_ESTADO(long id, String messageToTranslate) {
            this.id = id;
            this.messageToTranslate = messageToTranslate;
        }

        public long getId() {
            return this.id;
        }

        public String getMessageToTranslate() {
            return this.messageToTranslate;
        }

    }

    public static enum GRUPOINSTITUCION {
        COMUN_MINI ("COMUN_MINI"),
        CATALUÑA ("CATALUÑA"),
        ARAGON ("ARAGON"),
        NAVARRA ("NAVARRA"),
        CANTABRIA ("CANTABRIA"),
        MADRID("MADRID"),
        GALICIA ("GALICIA"),
        PAISVASCO ("PAISVASCO"),
        ANDALUCIA ("ANDALUCIA"),
        VALENCIA ("VALENCIA"),
        CANARIAS("CANARIAS"),
        ASTURIAS("ASTURIAS"),

                ;

        private String codigoGrupo = null;

        GRUPOINSTITUCION(String codigoGrupo) {
            this.codigoGrupo = codigoGrupo;
        }
        public String getCodigoGrupo() {
            return this.codigoGrupo;
        }


    }
    
    public static Integer TIPO_TURNO_DESIGNACION = 2;
    public static Integer TIPO_TURNO_TRAMITACION = 1;
}
