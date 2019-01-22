package org.itcgae.siga.commons.constants;


import org.springframework.stereotype.Component;

@Component
public class SigaConstants {

	public static String COMBO_INSTITUCIONES = "instituciones";
	
	public static String COMBO_PERFILES = "perfiles";
	public static String TIPO_PERSONA_NOTARIO = "Notario";
	
	public static Integer IdUsuarioPuertaAtras = 1;
	public static String OK = "OK";
	public static String KO = "KO";
	public static String InstitucionGeneral = "2000";
	public static String Personal = "Personal";
	public static String DB_FALSE = "0";
	public static String DB_TRUE = "1";
	public static final String IP_ACCESO_SERVICIO_CARGAS = "IP_ACCESO_SERVICIO_CARGAS";
	public static final String ACTIVAR_CLIENTE_SERVICIO_CARGAS = "ACTIVAR_CLIENTE_SERVICIO_CARGAS";
	
	public static String SUFIJO_MODULO_COM_DUPLICADO = "_Copia";
	public static int NOMBRE_MAXLENGTH = 100;
	public static String rutaficherosInformesYcomunicaciones = "/FILERMSA1000/SIGA/ficheros/archivo/";
	public static String carpetaDocumentosEnvio = "/documentosEnvio/";
	public static String carpetaPlantillasDocumento = "/plantillaDocumentos/";
	public static String rutaExcelConsultaTemp= "/FILERMSA1000/SIGA/ficheros/archivo/tempExcel/";
	public static String nombreExcelConsulta = "ResultadoConsulta";
	
	public enum ERROR_SERVER {
		XML_NO_VALIDO(null), CLI_NOAUTORIZADO(
				"La IP recibida en la petición no está autorizada."), CLI_IP_NO_ENCONTRADA(
						"La ip no se ha encontrado."), CLI_NOVALIDO(
								"Esquema de petición datos no válido."), CLI_NOACTIVO(
										"El colegio que realiza la petición no tiene activo el servicio."), CLI_CODIGO_NO_EXISTE(
												"El código del colegio no existe."), CLI_VERSION_INCORRECTA(
														"La versión recibida no existe o no es la correspondiente al esquema utilizado."), CLI_ERROR_DATOS_INTERNO(
																"Los datos que se reciben no son válidos para enviar una respuesta correcta en el servicio."), CLI_OTROS_ERRORES_INTERNOS(
																		"Error de causas externas."), CLI_COLEGIO_NULO(
																				"El colegio no puede ser nulo."),
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

	public enum ERROR_CLIENT {
		XML_NO_VALIDO(null), 
		SERV_NODISPONIBLE("Servicio no disponible."), 
		SERV_CERTNOAUT("En el caso de comunicaciones con certificado de cliente que el certificado presentado no esté autorizado."), 
		CLI_NOVALIDO("Esquema de petición datos no válido."), 
		CLI_NOACTIVO("El colegio que realiza la petición no tiene activo el servicio."), 
		SERV_FECHANOVALIDO("Para el servidor el rango de fechas solicitado no es válido."), 
		SERV_COLEGIONOVALIDO("Para el servidor el código del colegio recibido no es válido."), 
		SERV_NUMPAGINANOVALIDO("Para el servidor el número de página solicitado no existe o no es la esperada.."), 
		SERV_VERSION_INCORRECTA("La versión recibida no existe o no es la correspondiente al esquema utilizado en la petición."), 
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
		PETICION_SERVICIO_CARGAS(new Short("1"), "petición servicio"), RESPUESTA_SERVICIO_CARGAS(new Short("2"),
				"respuesta servicio"), PETICION_CLIENTE_CARGAS(new Short("3"),
						"petición cliente"), RESPUESTA_CLIENTE_CARGAS(new Short("4"),
								"respuesta cliente"), PETICION_SERVICIO_ECOS(new Short("5"),
										"petición servicio ECOS"), RESPUESTA_CLIENTE_ECOS(new Short("6"),
												"respuesta cliente ECOS"),
		PETICION_WS_PUBLICADOR(new Short("7"), "petición servicio"),
		RESPUESTA_WS_PUBLICADOR(new Short("8"), "respuesta servicio");

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
		CARGAS(new Short("1"), "CARGAS"), ADMINISTRACION(new Short("2"), "ADMINISTRACION"), PUBLICACION(new Short("3"),
				"PUBLICACION");

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
		ESTADO_PENDIENTE(new Short("1"), "Carga pendiente"),
		ESTADO_PROCESANDO(new Short("2"), "Carga procesando"),
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
	
	public static enum CEN_TIPOCAMBIO{
		
		ALTA_COLEGIACION(new Short("1"), "Alta Colegiación"),
		BAJA_COLEGIACION(new Short("2"), "Baja Colegiación"),
		ALTA_EJERCICIO(new Short("3"), "Alta Ejercicio"),
		BAJA_EJERCICIO(new Short("4"), "Baja Ejercicio"),
		INHABILITACION(new Short("5"), "Inhabilitación"),
		SUSPENSION_EJERCICIO(new Short("6"), "Suspensión Ejercicio"),
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
		DESIGNACION_ELIMINACION_DE_ACTUACIONES(new Short("108"), "Designación. Eliminación de Actuaciones");
		
		
		private final Short idTipoCambio;
		private final String descripcionTipoCambio;
		
		
		private CEN_TIPOCAMBIO(Short idTipoCambio,String descripcionTipoCambio ) {
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
		DESTINATARIOS(new Short("1"), "DESTINATARIOS"),
		MULTIDOCUMENTO(new Short("2"), "MULTIDOCUMENTO"),
		CONDICIONAL(new Short("3"), "CONDICIONAL"),
		DATOS(new Short("4"), "DATOS");
		
		private final Short codigo;
		private final String descripcion;

		OBJETIVO(Short codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Short getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static OBJETIVO getEnum(Short codigo) {
			for (OBJETIVO sc : values()) {
				if (sc.getCodigo().shortValue() == codigo.shortValue()) {
					return sc;
				}
			}
			return null;
		}
	}
}
