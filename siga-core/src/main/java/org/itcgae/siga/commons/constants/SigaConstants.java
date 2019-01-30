package org.itcgae.siga.commons.constants;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SigaConstants {

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

	public static String InstitucionGeneral = "2000";
	public static String Personal = "Personal";

	public static String DB_FALSE = "0";
	public static String DB_TRUE = "1";
	public static final String IP_ACCESO_SERVICIO_CARGAS = "IP_ACCESO_SERVICIO_CARGAS";
	public static final String ACTIVAR_CLIENTE_SERVICIO_CARGAS = "ACTIVAR_CLIENTE_SERVICIO_CARGAS";
	
	// DOCUMENTOS
	
	public static String SUFIJO_MODULO_COM_DUPLICADO = "_Copia";
	public static int NOMBRE_MAXLENGTH = 100;
	public static String rutaficherosInformesYcomunicaciones = "/FILERMSA1000/SIGA/ficheros/archivo/";
	public static String carpetaDocumentosEnvio = "/documentosEnvio/";
	public static String carpetaPlantillasDocumento = "/plantillaDocumentos/";
	public static String rutaExcelConsultaTemp= "/FILERMSA1000/SIGA/ficheros/archivo/tempExcel/";
	public static String nombreExcelConsulta = "ResultadoConsulta";
	
	public static String REPLACECHAR_PREFIJO_SUFIJO = "%%";
	public static String CLAVES_QUERY = "CLAVES";
	public static String WHERE_VALUE = "whereValue";
	public static String CAMPO_NOMBRE = "nombre";
	public static String CAMPO_APELLIDOS = "apellidos";
	public static String CAMPO_APELLIDO1 = "APELLIDO1";
	public static String CAMPO_APELLIDO2 = "APELLIDO2";
	public static String CAMPO_NUM_COLEGIADO = "NCOLEGIADO";
	public static String CAMPO_IDENTIFICACION = "INDENTIFICACIONENTIDAD";
	
	// Para la busqueda de criterios dinamicos de las consultas
	public static final String TIPONUMERO	= "N";
	public static final String TIPOTEXTO	= "A";
	public static final String TIPOFECHA	= "D";
	public static final String TIPOFECHANULA	= "X";
	public static final String TIPOMULTIVALOR		= "MV";
	public static final String TIPOENVIO		= "E";
	
	public static final String ETIQUETATIPONUMERO	= "%%NUMERO%%";
	public static final String ETIQUETATIPOTEXTO	= "%%TEXTO%%";
	public static final String ETIQUETATIPOFECHA	= "%%FECHA%%";
	public static final String ETIQUETATIPOMULTIVALOR		= "%%MULTIVALOR@";
	public static final String ETIQUETATIPOENVIO		= "%%TIPOENVIO%%";
	public static final String ETIQUETAOPERADOR   	= "%%OPERADOR%%";
	
	public static final String NOMBRETIPOENVIO		= "TIPO ENVIO";
	public static final String IS_NULL		= "is null";
	public static final String LIKE		= "like";
	public static final String NOMBRETABLA_CEN_CLIENTE = "CEN_CLIENTE";
	public static final String NOMBRETABLA_CEN_COLEGIADO = "CEN_COLEGIADO";

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
																				"El colegio no puede ser nulo."), CLI_PAGINA_YA_INSERTADA(
																						"La página ya ha sido insertada para esa carga"), CLI_CAMPO_NO_VALIDO(
																								"La longitud es superior a la máxima permitida"), CLI_ORDEN_FECHAS_INCORRECTO(
																										"La fecha hasta no puede ser menor a la fecha desde"), CLI_HORA_EJECUCION_NO_PERMITIDO(
																												"No está permitida la ejecución del servicio a esta hora"), CLI_COLEGIO_NO_EXISTE(
																														"Alguno de los colegios no existe en el sistema"), CLI_INCONCORDANCIA_EN_PAGINAS(
																																"El número de página no puede ser superior al total de páginas.");

		private String mensajeError = null;

		private ERROR_SERVER(String mensajeError) {
			this.mensajeError = mensajeError;
		}

		public String getMensajeError() {
			return mensajeError;
		}
	}
    // Tipo direcciones que tienen logica asociada
	public static final int TIPO_DIRECCION_CENSOWEB		= 3;
	public static final int TIPO_DIRECCION_DESPACHO		= 2;
	public static final int TIPO_DIRECCION_GUIA	    	= 5;
	public static final int TIPO_DIRECCION_GUARDIA  	= 6;
	public static final int TIPO_DIRECCION_FACTURACION  = 8;
	public static final int TIPO_DIRECCION_TRASPASO_OJ  = 9;
	public static final int TIPO_DIRECCION_PUBLICA		= 4;
	
	
	// Estados colegiales
	public static final int ESTADO_COLEGIAL_SINEJERCER 		= 10;
	public static final int ESTADO_COLEGIAL_EJERCIENTE		= 20;
	public static final int ESTADO_COLEGIAL_BAJACOLEGIAL	= 30;
	public static final int ESTADO_COLEGIAL_INHABILITACION	= 40;
	public static final int ESTADO_COLEGIAL_SUSPENSION	= 	  50;
	public static final int ESTADO_COLEGIAL_ALTA	= 	  1020;// Correspnde a una combinacion del estado 10 y 20;
	
	//Tipos de Preferente
	public static final String TIPO_PREFERENTE_CORREO = "C";  
	public static final String TIPO_PREFERENTE_CORREOELECTRONICO = "E";  
	public static final String TIPO_PREFERENTE_FAX = "F";  
	public static final String TIPO_PREFERENTE_SMS = "S"; 
	
	// Tipo cambio histórico de censo
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_COLEGIAL	= 1;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_BAJA_COLEGIAL	= 2;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_EJERCICIO	= 3;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_BAJA_EJERCICIO	= 4;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_INHABILITACION	= 5;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_SUSPENSION		= 6;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_GENERALES		= 10;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_COLEGIALES		= 20;
		public static final int TIPO_CAMBIO_HISTORICO_DIRECCIONES			= 30;
		public static final int TIPO_CAMBIO_HISTORICO_CUENTAS_BANCARIAS		= 40;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_CV				= 50;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_COMPONENTES		= 60;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_FACTURACION		= 70;
		public static final int TIPO_CAMBIO_HISTORICO_TURNOS				= 80;
		public static final int TIPO_CAMBIO_HISTORICO_EXPEDIENTES			= 90;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONMODIFICACION		= 101;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONJUSTIFICACION		= 102;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONALTAACTUACION		= 103;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONMODIFICAACTUACION		= 107;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONDELETEACTUACION		= 108;
		
		public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAALTA		= 104;
		public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAMODIFICACION		= 105;
		public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAALTAACTUACION		= 106;
		
	
	
	
	public enum ERROR_CLIENT {
		XML_NO_VALIDO(null), SERV_NODISPONIBLE("Servicio no disponible."), SERV_CERTNOAUT(
				"En el caso de comunicaciones con certificado de cliente que el certificado presentado no esté autorizado."), CLI_NOVALIDO(
						"Esquema de petición datos no válido."), CLI_NOACTIVO(
								"El colegio que realiza la petición no tiene activo el servicio."), SERV_FECHANOVALIDO(
										"Para el servidor el rango de fechas solicitado no es válido."), SERV_COLEGIONOVALIDO(
												"Para el servidor el código del colegio recibido no es válido."), SERV_NUMPAGINANOVALIDO(
														"Para el servidor el número de página solicitado no existe o no es la esperada.."), SERV_VERSION_INCORRECTA(
																"La versión recibida no existe o no es la correspondiente al esquema utilizado en la petición."), SERV_OTRO_ERROR(
																		"Cualquier otro error no catalogado."), SERV_NOVALIDO(
																				"La respuesta no tiene un formato válido");

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
												"respuesta cliente ECOS"), PETICION_WS_PUBLICADOR(new Short("7"),
														"petición servicio"), RESPUESTA_WS_PUBLICADOR(new Short("8"),
																"respuesta servicio"),PETICION_SERVICIO_FUSIONADOR(new Short("9"), "petición servicio"),
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
		CARGAS(new Short("1"), "CARGAS"), ADMINISTRACION(new Short("2"), "ADMINISTRACION"), PUBLICACION(new Short("3"),
				"PUBLICACION"),FUSIONADOR(new Short("4"), "FUSIONADOR");

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
		ESTADO_PENDIENTE(new Short("1"), "Carga pendiente"), ESTADO_PROCESANDO(new Short("2"),
				"Carga procesando"), ESTADO_PROCESADO(new Short("3"), "Carga procesada"), ESTADO_ERROR_FORMATO(
						new Short("4"), "Carga con errores de formato"), ESTADO_ERROR_GENERAL(new Short("5"),
								"Carga finalizada con error");

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

		ALTA_COLEGIACION(new Short("1"), "Alta Colegiación"), BAJA_COLEGIACION(new Short("2"),
				"Baja Colegiación"), ALTA_EJERCICIO(new Short("3"), "Alta Ejercicio"), BAJA_EJERCICIO(new Short("4"),
						"Baja Ejercicio"), INHABILITACION(new Short("5"), "Inhabilitación"), SUSPENSION_EJERCICIO(
								new Short("6"), "Suspensión Ejercicio"), MODIFICACION_DATOS_GENERALES(new Short("10"),
										"Modificación Datos Generales"), MODIFICACION_DATOS_COLEGIALES(new Short("20"),
												"Modificación Datos Colegiales"), MODIFICACION_DIRECCIONES(
														new Short("30"),
														"Modificación Direcciones"), MODIFICACION_CUENTAS_BANCARIAS(
																new Short("40"),
																"Modificación Cuentas Bancarias"), MODIFICACION_DATOS_CV(
																		new Short("50"),
																		"Modificación Datos CV"), MODIFICACION_COMPONENTES(
																				new Short("60"),
																				"Modificación Componentes"), MODIFICACION_DATOS_FACTURACIÓN(
																						new Short("70"),
																						"Modificación Datos Facturación"), MODIFICACION_DATOS_TURNO(
																								new Short("80"),
																								"Modificación Datos Turno"), MODIFICACION_DATOS_EXPEDIENTES(
																										new Short("90"),
																										"Modificación Datos Expedientes"), MODIFICACION_OTROS_DATOS(
																												new Short(
																														"99"),
																												"Modificación Otros Datos"), OBSERVACIONES(
																														new Short(
																																"100"),
																														"Observaciones"), DESIGNACION_MODIFICACION(
																																new Short(
																																		"101"),
																																"Designación. Modificación"), DESIGNACION_JUSTIFICACION(
																																		new Short(
																																				"102"),
																																		"Designación. Justificación"), DESIGNACION_ALTA_DE_ACTUACIONES(
																																				new Short(
																																						"103"),
																																				"Designación. Alta de Actuaciones"), ASISTENCIA_ALTA(
																																						new Short(
																																								"104"),
																																						"Asistencia. Alta"), ASISTENCIA_MODIFICACION(
																																								new Short(
																																										"105"),
																																								"Asistencia. Modificación"), ASISTENCIA_ALTA_DE_ACTUACIONES(
																																										new Short(
																																												"106"),
																																										"Asistencia. Alta de Actuaciones"), DESIGNACION_MODIFICACION_DE_ACTUACIONES(
																																												new Short(
																																														"107"),
																																												"Designación. Modificación de Actuaciones"), DESIGNACION_ELIMINACION_DE_ACTUACIONES(
																																														new Short(
																																																"108"),
																																														"Designación. Eliminación de Actuaciones");

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
	
	public static enum SUFIJOS {
		NOMBRE_COLEGIADO(new Short("1"), "NOMBRE_COLEGIADO"),
		NUMERO_COLEGIADO(new Short("2"), "NUMERO_COLEGIADO"),
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
		XLS(new Short("1"), "xls"),
		DOC(new Short("2"), "doc"),
		PDF(new Short("3"), "pdf"),
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

	public static enum CargaMasivaDatosCVVo {

		COLEGIADONUMERO("COLEGIADONUMERO"), PERSONANIF("PERSONANIF"), C_FECHAINICIO("C_FECHAINICIO"), C_FECHAFIN(
				"C_FECHAFIN"), C_CREDITOS("C_CREDITOS"), FECHAVERIFICACION("FECHAVERIFICACION"), TIPOCVCOD(
						"TIPOCVCOD"), SUBTIPOCV1COD("SUBTIPOCV1COD"), SUBTIPOCV2COD("SUBTIPOCV2COD"), PERSONANOMBRE(
								"PERSONANOMBRE"), C_IDPERSONA("C_IDPERSONA"), TIPOCVNOMBRE("TIPOCVNOMBRE"), C_IDTIPOCV(
										"C_IDTIPOCV"), SUBTIPOCV1NOMBRE("SUBTIPOCV1NOMBRE"), C_IDTIPOCVSUBTIPO1(
												"C_IDTIPOCVSUBTIPO1"), SUBTIPOCV2NOMBRE(
														"SUBTIPOCV2NOMBRE"), C_IDTIPOCVSUBTIPO2(
																"C_IDTIPOCVSUBTIPO2"), ERRORES(
																		"ERRORES"), C_DESCRIPCION("C_DESCRIPCION");

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
		DATOSGENERALES ((short)10),
		DATOSCV ((short)50);
		
		private short id = 0;
		HISTORICOCAMBIOCV(short id) {
			this.id = id;
		}
		public short getId() {
			return this.id;
		}
	} 
	
	public static final String PLAZAS_DISPO_SI = "1";
	
	public static final String PLAZAS_DISPO_NO = "0";
		
	public static final String AUX_TRANS_TILDES_1 = "áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";
	
	public static final String AUX_TRANS_TILDES_2 = "aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC";
	
	public static final String ESTADO_INSCRIPCION_PENDIENTE = "1";
	public static final String ESTADO_INSCRIPCION_RECHAZADA = "2";
	public static final String ESTADO_INSCRIPCION_APROBADA = "3";
	public static final String ESTADO_INSCRIPCION_CANCELADA = "4";
	
	//FICHA CURSO
	
public static final String ESTADO_CURSO_ABIERTO = "0";
	
	public static final String ESTADO_CURSO_ANUNCIADO = "1";
	
	public static final String ESTADO_CURSO_EN_CURSO = "2";
	
	public static final String ESTADO_CURSO_IMPARTIDO = "3";
	
	public static final String ESTADO_CURSO_FINALIZADO = "4";
	
	public static final String ESTADO_CURSO_CANCELADO = "5";
	
	public static final Integer CURSO_SIN_ARCHIVAR = 0;
	
	public static final Integer CURSO_ARCHIVADO = 1;

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
	public static final String NOMBRE_PERSONA = "NOMBRE_PERSONA";
	public static final String NOMBRE_CURSO = "NOMBRE_CURSO";
	public static final String ESTADO = "ESTADO";
	public static final String ERRORES = "ERRORES";
	
	public static final List<String> CAMPOSPLANTILLACURSO = Arrays.asList(CODIGO_CURSO, FORMA_PAGO, NIF);
	public static final List<String> CAMPOSPLOGCURSO = Arrays.asList(CODIGO_CURSO, NOMBRE_CURSO, FORMA_PAGO, NIF, NOMBRE_PERSONA, ERRORES);
	
	
	//FICHA EVENTOS
	
	public static final String NOMBRE = "NOMBRE";
	public static final String ASISTENCIA = "ASISTENCIA";
	
	public static final long TIPO_EVENTO_INICIO_INSCRIPCION = 4;
	public static final long TIPO_EVENTO_FIN_INSCRIPCION = 5;
	public static final long TIPO_EVENTO_SESION = 8;
	
	public static final List<String> CAMPOSPLANTILLAEVENTOS = Arrays.asList(NOMBRE);
	
	//CARGA MASIVA GF
	
	public static final String ALTA = "A";
	public static final String BAJA = "B";
	public static final String C_IDGRUPO = "C_IDGRUPO";
	public static final String ACCION = "ACCION";
	public static final String GENERAL = "GENERAL";
	public static final String NOMBREGRUPO = "NOMBREGRUPO";
	public static final String PERSONANOMBRE = "PERSONANOMBRE";
	public static final String C_IDPERSONA = "C_IDPERSONA";
	public static final String C_IDINSTITUCION 		= "IDINSTITUCION";
	public static final String PERSONANIF = "PERSONANIF";
	public static final String COLEGIADONUMERO = "COLEGIADONUMERO";
	public static final Short IDINSTITUCION_2000 = 2000;
	public static final String TIPO_CARGA = "GF";
	public static final String C_FECHAINICIO = "C_FECHAINICIO";
	public static final String C_FECHAFIN = "C_FECHAFIN";
	
	public static final List<String> CAMPOSEJEMPLOGF = Arrays.asList(COLEGIADONUMERO, PERSONANIF, 
			C_IDGRUPO, GENERAL, ACCION, C_FECHAINICIO,
			C_FECHAFIN);

	
	public static final List<String> CAMPOSLOGGF = Arrays.asList(COLEGIADONUMERO, PERSONANIF, 
			PERSONANOMBRE, C_IDPERSONA, C_IDGRUPO, 
			GENERAL, NOMBREGRUPO, ACCION, C_FECHAINICIO,
			C_FECHAFIN, ERRORES);

	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx = "xlsx";
	public static final String nombreFicheroEjemplo = "PlantillaMasivaDatosGF";
	public static final String nombreFicheroError = "LogErrorCargaMasivaGF";
	
	public static final String NOTA_SUSPENSO = "1";
	public static final String NOTA_APROBADO = "2";
	public static final String NOTA_BIEN = "3";
	public static final String NOTA_NOTABLE= "4";
	public static final String NOTA_SOBRESALIENTE = "5";
	
	//CARGA MASIVA CV

	public static final String C_IDTIPOCV = "IDTIPOCV";
    public static final String C_CREDITOS = "C_CREDITOS";
    public static final String C_IDTIPOCVSUBTIPO1 = "C_IDTIPOCVSUBTIPO1";
    public static final String C_IDTIPOCVSUBTIPO2 = "C_IDTIPOCVSUBTIPO2";
	public static final String TIPOCVCOD = "TIPOCVCOD";
	public static final String SUBTIPOCV1COD = "SUBTIPOCV1COD";
	public static final String SUBTIPOCV2COD = "SUBTIPOCV2COD";
	public static final String TIPOCVNOMBRE = "TIPOCVNOMBRE";
	public static final String SUBTIPOCV1NOMBRE = "SUBTIPOCV1NOMBRE";
	public static final String SUBTIPOCV2NOMBRE = "SUBTIPOCV2NOMBRE";
	public static final String FECHAVERIFICACION = "FECHAVERIFICACION";
    public static final String C_DESCRIPCION = "C_DESCRIPCION";

    public static final String nombreFicheroEjemploCV = "PlantillaMasivaDatosCV";
    
	public static final List<String> CAMPOSEJEMPLOCV = Arrays.asList(COLEGIADONUMERO,PERSONANIF,
			C_FECHAINICIO,C_FECHAFIN,C_CREDITOS,FECHAVERIFICACION,C_DESCRIPCION,TIPOCVCOD,SUBTIPOCV1COD,SUBTIPOCV2COD);
	public static final List<String> CAMPOSLOGCV = Arrays.asList(COLEGIADONUMERO,PERSONANIF,PERSONANOMBRE,C_IDPERSONA,
			C_FECHAINICIO ,C_FECHAFIN,C_CREDITOS,FECHAVERIFICACION,C_DESCRIPCION
			,TIPOCVCOD,TIPOCVNOMBRE,C_IDTIPOCV,SUBTIPOCV1COD,SUBTIPOCV1NOMBRE,C_IDTIPOCVSUBTIPO1,SUBTIPOCV2COD,SUBTIPOCV2NOMBRE,C_IDTIPOCVSUBTIPO2,ERRORES);

}
