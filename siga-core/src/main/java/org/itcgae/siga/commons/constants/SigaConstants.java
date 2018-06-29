package org.itcgae.siga.commons.constants;


import org.springframework.stereotype.Component;

@Component
public class SigaConstants {

	public static String COMBO_INSTITUCIONES = "instituciones";
	
	public static String COMBO_PERFILES = "perfiles";
	public static String TIPO_PERSONA_NOTARIO = "Notario";
	
	public static String OK = "OK";
	public static String KO = "KO";
	public static String InstitucionGeneral = "2000";
	
	public static String DB_FALSE = "0";
	public static String DB_TRUE = "1";
	public static final String IP_ACCESO_SERVICIO_CARGAS = "IP_ACCESO_SERVICIO_CARGAS";
	public static final String ACTIVAR_CLIENTE_SERVICIO_CARGAS = "ACTIVAR_CLIENTE_SERVICIO_CARGAS";
	
	
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
}
