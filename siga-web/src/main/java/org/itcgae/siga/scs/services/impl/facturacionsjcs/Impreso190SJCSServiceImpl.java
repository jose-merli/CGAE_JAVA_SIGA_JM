package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoExample;
import org.itcgae.siga.db.entities.FcsConfImpreso190;
import org.itcgae.siga.db.entities.FcsConfImpreso190Example;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190Example;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190Key;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenFicheroExample;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.FcsConfImpreso190Mapper;
import org.itcgae.siga.db.mappers.FcsFicheroImpreso190Mapper;
import org.itcgae.siga.db.mappers.FcsPagoColegiadoMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GenFicheroExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFicheroImpreso190ExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagoColegiadoExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IImpreso190Service;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Impreso190SJCSServiceImpl implements IImpreso190Service {

	private final Logger LOGGER = Logger.getLogger(Impreso190SJCSServiceImpl.class);

	@Autowired
	private FacAbonoExtendsMapper facAbonoExtendsMapper;

	@Autowired
	private FcsPagoColegiadoExtendsMapper fcsPagoColegiadoExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;

	@Autowired
	private FcsFicheroImpreso190ExtendsMapper fcsFicheroImpreso190ExtendsMapper;

	@Autowired
	private FcsConfImpreso190Mapper fcsConfImpreso190Mapper;

	@Autowired
	private GenFicheroExtendsMapper genFicheroExtendsMapper;

	@Override
	@Transactional
	public Impreso190DTO impreso190generar(Impreso190Item impreso190Item, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Impreso190DTO impreso190DTO = new Impreso190DTO();
		String sNombreFichero = "";
		Error error = new Error();
		String codigoProvincia;
		String sDirectorio;
		int responseUpImpreso;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				if (usuarios != null && usuarios.size() > 0) {

					sDirectorio = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190, idInstitucion.toString());

					// primero genera los insert y updates a la base de datos
					CenPersona datosInstitucion = cenPersonaExtendsMapper
							.getDatosInstitucionForImpreso190(idInstitucion.toString());
					codigoProvincia = cenDireccionesExtendsMapper.getIdProvinciaImpreso190(
							datosInstitucion.getIdpersona().toString(), idInstitucion.toString(),
							SigaConstants.TIPO_DIRECCION_FACTURACION);

					FcsConfImpreso190Example impresoExample = new FcsConfImpreso190Example();
					impresoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);
					List<FcsConfImpreso190> confImpreso = fcsConfImpreso190Mapper.selectByExample(impresoExample);

					// se comprueba que el nombre del fichero sea unico.
					FcsFicheroImpreso190Example ficheroExample = new FcsFicheroImpreso190Example();
					ficheroExample.createCriteria().andNombreFicheroEqualTo(impreso190Item.getNomFicheroOriginal());

					List<FcsFicheroImpreso190> existFichero = fcsFicheroImpreso190ExtendsMapper
							.selectByExample(ficheroExample);
					if (existFichero.isEmpty() || existFichero == null) {
						// a continuacion genera el fichero. Si la generacion del fichero falla se hace
						// un rollback con el transactioanal y no hace la insercion en BBDD

						sNombreFichero = impreso190Item.getNomFicheroOriginal();

						new File(sDirectorio + File.separator + idInstitucion).mkdir();

						String sNombreCompletoFichero = sDirectorio + File.separator + idInstitucion
								+ File.separator + sNombreFichero;
						File fichero = new File(sNombreCompletoFichero);
						
						fichero = generarImpreso190(impreso190Item, idInstitucion.toString());

						if (fichero != null) {
							if (fichero.getName().indexOf(".zip") != -1) {
								error.setCode(400);
								error.setDescription("facturacionSJCS.impreso190.impresoErrorLog");

							} else if (fichero.getName().indexOf(".190") != -1) {

								if (!confImpreso.isEmpty() || confImpreso != null) {
									FcsConfImpreso190 modImpreso = new FcsConfImpreso190();
									modImpreso.setIdinstitucion(confImpreso.get(0).getIdinstitucion());
									modImpreso.setNombrefichero(impreso190Item.getNomFicheroOriginal());
									modImpreso.setAnio(Short.parseShort(impreso190Item.getAnio()));
									modImpreso.setNombre(impreso190Item.getNombreContacto());
									modImpreso.setApellido1(impreso190Item.getApellido1Contacto());
									modImpreso.setApellido2(impreso190Item.getApellido2Contacto());
									modImpreso.setTelefono(impreso190Item.getTelefonoContacto());
									modImpreso.setIdprovincia(codigoProvincia);
									modImpreso.setFechamodificacion(new Date());
									modImpreso.setUsumodificacion(usuarios.get(0).getIdusuario());

									// siempre hace el update, aunque no llegue a guardar el fichero.
									int impresoUpdate = fcsConfImpreso190Mapper.updateByPrimaryKeySelective(modImpreso);
									if (impresoUpdate != 0) {

										// obtener new idFichero

										NewIdDTO idFichero = genFicheroExtendsMapper
												.selectMaxIdFicheroByIdInstitucion(idInstitucion.toString());

										Long newid = Long.parseLong(idFichero.getNewId());

										GenFichero addFichero = new GenFichero();
										addFichero.setIdfichero(newid + 1);
										addFichero.setIdinstitucion(idInstitucion);
										addFichero.setExtension("txt");
										addFichero.setDescripcion("Impreso 190");
										addFichero.setDirectorio(sDirectorio + File.separator + idInstitucion);
										addFichero.setFechamodificacion(new Date());
										addFichero.setUsumodificacion(usuarios.get(0).getIdusuario());

										int impresoUp = genFicheroExtendsMapper.insertSelective(addFichero);
										if (impresoUp != 0) {
											FcsFicheroImpreso190 impreso190 = new FcsFicheroImpreso190();
											impreso190.setAnio(Short.parseShort(impreso190Item.getAnio()));
											impreso190.setIdinstitucion(idInstitucion);
											impreso190.setNombreFichero(impreso190Item.getNomFicheroOriginal());
											impreso190.setTelefono(impreso190Item.getTelefonoContacto());
											impreso190.setNombre(impreso190Item.getNombreContacto());
											impreso190.setApellido1(impreso190Item.getApellido1Contacto());
											impreso190.setApellido2(impreso190Item.getApellido2Contacto());
											impreso190.setFechagenerarion(new Date());
											impreso190.setIdGenFichero(addFichero.getIdfichero());
											impreso190.setUsumodificacion(usuarios.get(0).getIdusuario());
											impreso190.setFechamodificacion(new Date());

											responseUpImpreso = fcsFicheroImpreso190ExtendsMapper.insertSelective(impreso190);

											if (responseUpImpreso != 0) {
												error.setCode(200);
												error.setDescription("facturacionSJCS.impreso190.impresoGenerado");
											}

										}
									}
								}

							}
						} else {
							error.setCode(500);
							error.setDescription("impreso190.mensajeError.impresoSinPagos");

						}
						
					} else {
						error.setCode(400);
						error.setDescription("impreso190.mensajeError.impresoDuplicado");
					}

				}
			}

		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		impreso190DTO.setError(error);

		return impreso190DTO;
	}

	@Transactional
	private File generarImpreso190(Impreso190Item dataEntry, String idinstitucion) throws Exception {

		File impreso190 = null;
		Map<String, Double> importes = new HashMap<String, Double>();
		Map<String, Double> irpfs = new HashMap<String, Double>();
		Map<String, Map<String, String>> datos = new HashMap<String, Map<String, String>>();
		Map<String, String> clavesM190 = new HashMap<String, String>();
		double importeTotal = 0.0;
		double irpfTotal = 0.0;
		File fichero = null;
		File ficheroErrores = null;
		File fichero190 = null;
		String sPagos = "";
		boolean hayError = false;
		Impreso190DTO error = new Impreso190DTO();
		String codigoProvincia;

		Map<String, String> dataErrorLogs = new HashMap<String, String>();
		List<Map<String, String>> vErrores = new ArrayList<Map<String, String>>();

		// obtener datos de la institucion
		CenPersona datosInstitucion = cenPersonaExtendsMapper.getDatosInstitucionForImpreso190(idinstitucion);

		// obtener datos de las provincias
		codigoProvincia = cenDireccionesExtendsMapper.getIdProvinciaImpreso190(
				datosInstitucion.getIdpersona().toString(), idinstitucion, SigaConstants.TIPO_DIRECCION_FACTURACION);
		dataEntry.setCodigoProvincia(codigoProvincia);

		List<FacAbono> abonos = facAbonoExtendsMapper.getPagosCerrados(Short.parseShort(idinstitucion),
				dataEntry.getAnio());
		if (!abonos.isEmpty()) {
			for (int i = 0; i < abonos.size(); i++) {
				sPagos += abonos.get(i).getIdpagosjg().toString();
				if (i < abonos.size() - 1)
					sPagos += ",";
			}

			// obtener el IRPF para cada uno de los pagos realizados
			List<Impreso190Item> pagoColegiado = fcsPagoColegiadoExtendsMapper.getIrpfPagos(idinstitucion, sPagos);

			if (!pagoColegiado.isEmpty()) {
				for (Impreso190Item pagoCol : pagoColegiado) {
					String idPersona = pagoCol.getIdPersonaImpreso();
					Double importeIrpfPersona = Double.parseDouble(pagoCol.getTotalImporteIrpf());
					Double importePagadoPersona = Double.parseDouble(pagoCol.getTotalImportePagado());
					String claveM190 = pagoCol.getClaveM190();

					Map<String, String> dataPersonas = new HashMap<String, String>();

					if (!importeIrpfPersona.equals(new Double(0.0))) {

						// obtener datos de persona por idPersona;
						Impreso190Item datosPersona = cenPersonaExtendsMapper.getDatosPersonaForImpreso190(idPersona);
						String mensajeError = "";
						if (datosPersona != null) {

							String tipoIdentificacion = datosPersona.getIdtipoidentificacion();
							String nombre = datosPersona.getNombre();
							String apellidos1 = datosPersona.getApellidos1();
							String apellidos2 = datosPersona.getApellidos2();
							String numIdentificacion = datosPersona.getNifcif();
							String nombrePersona = datosPersona.getNombrePersona();

							dataPersonas.put("IDPERSONA", idPersona);
							dataPersonas.put("IDTIPOIDENTIFICACION", tipoIdentificacion);
							dataPersonas.put("NOMBRE", nombre);
							dataPersonas.put("APELLIDOS1", apellidos1);
							dataPersonas.put("APELLIDOS2", apellidos2);
							dataPersonas.put("NIDENTIFICACION", numIdentificacion);
							dataPersonas.put("NOMBREPERSONA", nombrePersona);
							dataErrorLogs.putAll(dataPersonas);// sube todos los datos de la persona para el log de
																// error

							if (nombre == null || nombre.equals("")) {
								mensajeError = "ERROR: el nombre es necesario";
								hayError = true;
							}
							if (apellidos1 == null || apellidos1.equals("")) {
								if (mensajeError.equals(""))
									mensajeError += "ERROR: el apellidos1 es necesario";
								else
									mensajeError += ", el apellidos1 es necesario";
								hayError = true;
							}

						} else {
							hayError = true;
							mensajeError = "ERROR: Los datos de la persona no pueden estar vacios.";
						}

						if (!hayError) {
							String clave = idPersona + claveM190;
							datos.put(clave, dataPersonas);

							irpfs.put(clave, importeIrpfPersona);
							irpfTotal += importeIrpfPersona;

							importes.put(clave, importePagadoPersona);
							importeTotal += importePagadoPersona;

							clavesM190.put(clave, claveM190);

						} else {
							dataErrorLogs.put("Error", mensajeError);
							vErrores.add(dataErrorLogs);
						}

					}

				}

				// realizo la generaci�n del directorio y del fichero:
				String sNombreFichero = dataEntry.getNomFicheroOriginal();
				if (dataEntry.getNomFicheroOriginal() != "" && dataEntry.getNomFicheroOriginal().indexOf(".190") < 0) {
					sNombreFichero = dataEntry.getNomFicheroOriginal() + ".190";
				}
				// String sDirectorio = getDirectorioFichero(Short.parseShort(idinstitucion));
				String sDirectorio = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190, idinstitucion);
				String sCamino = sDirectorio + File.separator + idinstitucion;

				// LOG_IMPRESO190_[IDINSTITUCION]_[A�O]_[FECHA_EJECUCION]
				Calendar cal = Calendar.getInstance();
				Date hora = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMMM-dd_hh:mm:ss");
				String fechaEjecucion = sdf.format(hora);
				String sNombreLog = "IMPRESO190_" + dataEntry.getAnio() + ".log.xls";

				new File(sCamino).mkdirs();

				String sNombreCompletoFichero = sCamino + File.separator + sNombreFichero;
				String sNombreFicheroErrorLog = sCamino + File.separator + sNombreLog;
				String sNombreFicheroZip = sCamino + File.separator + sNombreFichero + ".zip";

				// Generamos el fichero 190 si no ha habido ningun error:
				if (!hayError) {
					fichero = generarModelo190(sNombreCompletoFichero, dataEntry, irpfs, importes, datosInstitucion,
							datos, irpfTotal, importeTotal, clavesM190);
				} else {
					fichero190 = generarModelo190(sNombreCompletoFichero, dataEntry, irpfs, importes, datosInstitucion,
							datos, irpfTotal, importeTotal, clavesM190);
					ficheroErrores = generarLogImpreso190(sNombreFicheroErrorLog, vErrores);

//								FileInputStream inputImpreso190 = null;
//								FileInputStream inputFicheroErrores = null;
					ZipOutputStream outZIP = null;
					FileInputStream in = null;

					// Genero un .ZIP con los ficheros de log de errores y el informe 190 de los que
					// he podido generar:
//									inputImpreso190 = new FileInputStream(fichero190);
//									inputFicheroErrores = new FileInputStream(ficheroErrores);

					outZIP = new ZipOutputStream(new FileOutputStream(sNombreFicheroZip));
					byte[] buf = new byte[1024];

					for (int i = 0; i < 2; i++) {
						String fileName = "";
						in = null;
						if (i == 0) { // Para a�adir el impreso 190
							fileName = sNombreFichero;
							in = new FileInputStream(fichero190);
						} else { // Para a�adir el log
							fileName = sNombreLog;
							in = new FileInputStream(ficheroErrores);
						}

						outZIP.putNextEntry(new ZipEntry(fileName));

						// Transfer bytes from the file to the ZIP file
						int len;
						while ((len = in.read(buf)) > 0) {
							outZIP.write(buf, 0, len);
						}

						// Complete the entry
						outZIP.closeEntry();
						in.close();
					}
					outZIP.close();

					fichero = new File(sNombreFicheroZip);

					/*
					 * if (inputImpreso190!=null) inputImpreso190.close(); if
					 * (inputFicheroErrores!=null) inputFicheroErrores.close();
					 */ if (outZIP != null)
						outZIP.close();
					if (in != null)
						in.close();
					throw new Exception("Error en FcsFacturacionJG.generarImpreso190() al crear el .ZIP");

				}

			}

		}

		return fichero;
	}

	private File generarModelo190(String nombreCompletoFichero, Impreso190Item impreso190Item,
			Map<String, Double> irpfs, Map<String, Double> importes, CenPersona datosInstitucion,
			Map<String, Map<String, String>> datos, double irpfTotal, double importeTotal,
			Map<String, String> clavesM190) throws Exception {

		BufferedWriter bw = null;
		File fichero = null;

		try {
			fichero = new File(nombreCompletoFichero);
			bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fichero), SigaConstants.IMPRESO190_ENCODING));

			if (irpfs.size() == 0) {
				throw new Exception("messages.factSJCS.noRetenciones190");
			}

			// registro unico de institucion
			//
			// MODELO 190: REGISTRO DE RETENEDOR
			//
			String linea = "";
			int nreg = importes.size();
			linea += "1"; // 1: tipo registro
			linea += "190"; // 2-4: modelo declaracion
			linea += UtilidadesImpreso190.formatea(impreso190Item.getAnio(), 4, true); // 5-8 ejercicio (anyo)
			linea += UtilidadesImpreso190.formatea(datosInstitucion.getNifcif(), 9, true); // 9-17: NIF del declarante

			String nombreInstitucion = datosInstitucion.getNombre().toString();
			nombreInstitucion = nombreInstitucion.toUpperCase();
			nombreInstitucion = UtilidadesImpreso190.quitarAcentos((nombreInstitucion));
			nombreInstitucion = UtilidadesImpreso190.validarModelo190(nombreInstitucion);
			linea += UtilidadesImpreso190.formatea(nombreInstitucion, 40, false); // 18-57: apellidos y nombre,
																					// denominaci�n o raz�n social del
																					// declarante (nombre de la
																					// institucion)

			linea += SigaConstants.TIPO_SOPORTE_TELEMATICO; // 58: tipo de soporte. En siga classique lo intruducia
															// directamente en el front por una constante
			linea += UtilidadesImpreso190.formatea(impreso190Item.getTelefonoContacto(), 9, true); // 59-67: telefono de
																									// contacto

			String apel1Contacto = impreso190Item.getApellido1Contacto().toUpperCase();
			String apel2Contacto = impreso190Item.getApellido2Contacto().toUpperCase();
			String nomContacto = impreso190Item.getNombreContacto().toUpperCase();
			apel1Contacto = UtilidadesImpreso190.quitarAcentos(apel1Contacto);
			apel1Contacto = UtilidadesImpreso190.validarModelo190(apel1Contacto);
			apel2Contacto = UtilidadesImpreso190.quitarAcentos(apel2Contacto);
			apel2Contacto = UtilidadesImpreso190.validarModelo190(apel2Contacto);
			nomContacto = UtilidadesImpreso190.quitarAcentos(nomContacto);
			nomContacto = UtilidadesImpreso190.validarModelo190(nomContacto);
			linea += UtilidadesImpreso190.formatea(apel1Contacto + " " + apel2Contacto + " " + nomContacto, 40, false); // 68-107:
																														// nombre
																														// de
																														// contacto

			linea += "170" + UtilidadesImpreso190.relleno("0", 10); // 108-120: numero identificativo de la declaracion
			linea += UtilidadesImpreso190.rellenoPosiciones(" ", 121, 122); // 121-122: complementaria
			linea += UtilidadesImpreso190.rellenoPosiciones("0", 123, 135); // 123-135: numero identificativo
																			// declaracion anterior
			linea += UtilidadesImpreso190.formatea(String.valueOf(nreg), 9, true);
			; // 136-144: numero de percepciones (DE MOMENTO SOLO LOS IRPFS)

			// 145-160: importe total
			List<String> valor = UtilidadesImpreso190.desdoblarDouble(new Double(importeTotal));
			linea += UtilidadesImpreso190.formatea(valor.get(0), 1, false); // 145: signo
			linea += UtilidadesImpreso190.formatea(valor.get(1), 13, true); // 146-158: entera
			linea += UtilidadesImpreso190.formatea(valor.get(2), 2, true); // 159-160: decimal

			// 161-175: retencion total
			valor = UtilidadesImpreso190.desdoblarDouble(new Double(irpfTotal));
			linea += UtilidadesImpreso190.formatea(valor.get(1), 13, true); // 161-173: entera
			linea += UtilidadesImpreso190.formatea(valor.get(2), 2, true); // 174-175: decimal

			linea += UtilidadesImpreso190.rellenoPosiciones(" ", 176, 225); // 176-225: correo electronico de contacto
																			// (ahora mismo no lo tenemos, pero en el
																			// cambio de 2017 dejaron este hueco que se
																			// puede usar)
			linea += UtilidadesImpreso190.rellenoPosiciones(" ", 226, 487); // 226-487: relleno
			linea += UtilidadesImpreso190.rellenoPosiciones(" ", 488, 500); // 488-500: sello electronico

			// escribo
			// cambio a formato DOS
			linea += "\r\n";
			bw.write(linea);
			// bw.newLine();

			//
			// MODELO 190: REGISTROS DE PERCEPCION
			//
			Iterator claves = datos.keySet().iterator();

			while (claves.hasNext()) {
				Object persona = claves.next();
				Map<String, String> datosPersonaRegistro = datos.get(persona);

				// registro unico de persona
				linea = "";
				linea += "2"; // 1: tipo registro
				linea += "190"; // 2-4: modelo declaracion
				linea += UtilidadesImpreso190.formatea(impreso190Item.getAnio(), 4, true); // 5-8: ejercicio (anyo)
				linea += UtilidadesImpreso190.formatea(datosInstitucion.getNifcif(), 9, true); // 9-17: NIF declarante
				linea += UtilidadesImpreso190.formatea(datosPersonaRegistro.get("NIDENTIFICACION"), 9, true); // 18-26:
																												// NIF
																												// perceptor
				linea += UtilidadesImpreso190.rellenoPosiciones(" ", 27, 35); // 27-35: NIF representante legal

				String apellido = (String) datosPersonaRegistro.get("APELLIDOS1");
				// Si es sociedad sin abreviatura se deja solo el nombre
				String nombrePerceptor = (String) datosPersonaRegistro
						.get(apellido.equals("#NA") ? "NOMBRE" : "NOMBREPERSONA");
				nombrePerceptor = nombrePerceptor.toUpperCase();
				nombrePerceptor = UtilidadesImpreso190.quitarAcentos(nombrePerceptor);
				nombrePerceptor = UtilidadesImpreso190.validarModelo190(nombrePerceptor);
				linea += UtilidadesImpreso190.formatea(nombrePerceptor, 40, false); // 36-75: apellidos y nombre o
																					// denominaci�n del perceptor

				linea += UtilidadesImpreso190.formatea(impreso190Item.getCodigoProvincia(), 2, true); // 76-77:
																										// provincia
				linea += (String) clavesM190.get(persona); // 78-80: clave+subclave de percepcion ("G01" casi siempre)

				// Obtengo el IRPF y el importe para esta persona:
				Double personaImporteTotalDouble = (Double) importes.get(persona);
				Double personaIrpfTotalDouble = (Double) irpfs.get(persona);
				// Si no hay retenciones "no debe aparecer" en el archivo
				if (!personaIrpfTotalDouble.equals(new Double(0))) {

					// 81-94: percepciones dinerarias
					valor = UtilidadesImpreso190.desdoblarDouble(personaImporteTotalDouble);
					linea += UtilidadesImpreso190.formatea(valor.get(0), 1, false); // 81: signo
					linea += UtilidadesImpreso190.formatea(valor.get(1), 11, true); // 82-92 entera
					linea += UtilidadesImpreso190.formateaDecimal(valor.get(2), 2); // 93-94 decimal

					// 95-107: retenciones practicadas
					valor = UtilidadesImpreso190.desdoblarDouble(personaIrpfTotalDouble);
					linea += UtilidadesImpreso190.formatea(valor.get(1), 11, true); // 95-105: entera
					linea += UtilidadesImpreso190.formateaDecimal(valor.get(2), 2); // 106-107: decimal

					// 108-147: percepciones en especie (no aplican)
					linea += " "; // 108: signo de percepcion en especie
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 109, 121); // 109-121: valor de percepcion en
																					// especie
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 122, 134); // 122-134: valor de percepcion en
																					// especie
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 135, 147); // 135-147: valor de percepcion en
																					// especie

					linea += UtilidadesImpreso190.rellenoPosiciones("0", 148, 151); // 148-151: ejercicio devengo

					boolean esCeutaOMelilla = (impreso190Item.getCodigoProvincia()
							.equals(SigaConstants.CODIGO_PROVINCIA_CEUTA)
							|| impreso190Item.getCodigoProvincia().equals(SigaConstants.CODIGO_PROVINCIA_MELILLA));
					linea += esCeutaOMelilla ? "1" : "0"; // 152: caso de ceuta y melilla

					// 153-254: Datos adicionales no necesarios para nuestro caso
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 153, 156); // 153-156: anyo nacimiento
					linea += "0"; // 157: situacion familiar
					linea += UtilidadesImpreso190.rellenoPosiciones(" ", 158, 166); // 158-166: nif conyuge
					linea += "0"; // 167: discapacidad
					linea += "0"; // 168: contrato o relacion
					linea += " "; // 169: MOVILIDAD GEOGR�FICA - ACEPTACION EN 2014
					linea += "0"; // 170: movilidad geografica
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 171, 183); // 171-183: reducciones aplicables
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 184, 196); // 184-196: gastos deducibles
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 197, 209); // 197-209: pensiones compensatorias
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 210, 222); // 210-222: anualidades por
																					// alimentos
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 223, 228); // 223-228: hijos y otros
																					// descendientes
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 229, 240); // 229-240: hijos y otros
																					// descendientes con discapacidad
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 241, 244); // 241-244: ascendientes
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 245, 250); // 245-250: ascendientes con
																					// discapacidad
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 251, 253); // 251-253: n� de hijos
					linea += "0"; // 254: prestamo vivienda

					// 255-281: percepciones dinerarias derivadas de incapacidad laboral (no
					// aplican)
					linea += " "; // 255: Signo de las percepciones dinerarias derivadas de incapacidad laboral
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 256, 281); // 256-281: importe de las
																					// percepciones dinerarias derivadas
																					// de incapacidad laboral
					// 282-321: percepciones en especie derivadas de incapacidad laboral (no
					// aplican)
					linea += " "; // 282: Signo de las percepciones en especie derivadas de incapacidad laboral
					linea += UtilidadesImpreso190.rellenoPosiciones("0", 283, 321); // 283-321: importe de las
																					// percepciones en especie derivadas
																					// de incapacidad laboral

					linea += UtilidadesImpreso190.rellenoPosiciones(" ", 322, 500); // 322-500: blancos

					// cambio a formato DOS
					linea += "\r\n";

					// escribiendo la linea
					bw.write(linea);
				}
			}
			// bw.flush();
			// bw.newLine();
			bw.close();
		} catch (IOException e) {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception e2) {
				throw new Exception("Error en FcsFacturacionJG.generarModelo190()");
			}
		} catch (Exception siga) {
			throw siga;
		}
		return fichero;
	}

	private File generarLogImpreso190(String nombreFichero, List<Map<String, String>> vErrores) throws Exception {
		BufferedWriter bw = null;
		File fichero = null;

		try {
			fichero = new File(nombreFichero);
			bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fichero), SigaConstants.IMPRESO190_ENCODING));

			Calendar cal = Calendar.getInstance();
			Date hora = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss");
			String horaActual = sdf.format(hora);
			// final String SEPARADOR = ",";

			String linea = "ERROR" + SigaConstants.SEPARADOR + "FECHA EJECUCION" + SigaConstants.SEPARADOR + "IDPERSONA"
					+ SigaConstants.SEPARADOR + "NUMERO IDENTIFICACION" + SigaConstants.SEPARADOR + "NOMBRE"
					+ SigaConstants.SEPARADOR + "APELLIDOS1" + SigaConstants.SEPARADOR + "APELLIDOS2"
					+ SigaConstants.SEPARADOR + "DESCRIPCION ERROR";
			// Escribo:
			// cambio a formato DOS
			linea += "\r\n";
			bw.write(linea);
			// bw.newLine();

			Iterator iter = vErrores.iterator();
			while (iter.hasNext()) {
				Map<String, String> persona = (Map<String, String>) iter.next();
				String stipoIdentificacion = (String) persona.get("IDTIPOIDENTIFICACION");
				String numeroIdentificacion = (String) persona.get("NIDENTIFICACION");
				String tipoIdentificacion = UtilidadesImpreso190
						.getTipoIdentificacion(Integer.parseInt(stipoIdentificacion));
				String nombre = (String) persona.get("NOMBRE");
				String apellidos1 = (String) persona.get("APELLIDOS1");
				String apellidos2 = (String) persona.get("APELLIDOS2");
				String idPersona = (String) persona.get("IDPERSONA");
				String mensajeError = (String) persona.get("DESCRIPCION_ERROR");

				// Construyo la linea de error del tipo:
				// ERROR [Fecha traza] [idpersona][numero identificacion][nombre y apellidos]
				// [descripcion del error]
				linea = "[ERROR]" + SigaConstants.SEPARADOR + horaActual + SigaConstants.SEPARADOR + idPersona
						+ SigaConstants.SEPARADOR + numeroIdentificacion + SigaConstants.SEPARADOR + nombre
						+ SigaConstants.SEPARADOR + apellidos1 + SigaConstants.SEPARADOR + apellidos2
						+ SigaConstants.SEPARADOR + mensajeError;

				// Escribo:
				// cambio a formato DOS
				linea += "\r\n";
				bw.write(linea);
				// bw.newLine();
			}

			bw.close();
		} catch (IOException e) {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception e2) {
				throw new Exception("Error en FcsFacturacionJG.generarLogImpreso190()");
			}
			throw new Exception("Error en FcsFacturacionJG.generarLogImpreso190()");
		} catch (Exception e) {
			throw new Exception("Error en FcsFacturacionJG.generarLogImpreso190()");
		}
		return fichero;
	}

	private String getDirectorioFichero(String modulo, String parametro, String idinstitucion) {
		GenParametrosExample path = new GenParametrosExample();

		String valor = "";
		path.createCriteria().andModuloEqualTo(modulo).andParametroEqualTo(parametro);

		List<GenParametros> parametros = genParametrosMapper.selectByExample(path);

		for (GenParametros param : parametros) {
			if (idinstitucion.equals(param.getIdinstitucion().toString())) {
				valor = param.getValor().toString();
			} else {

				GenParametrosKey pathDefecto = new GenParametrosKey();
				pathDefecto.setIdinstitucion(Short.parseShort("0"));
				pathDefecto.setModulo(modulo);
				pathDefecto.setParametro(parametro);

				valor = genParametrosMapper.selectByPrimaryKey(pathDefecto).getValor();
			}
		}

		return valor;
	}

	@Override
	@Transactional
	public ResponseEntity<InputStreamResource> impreso190descargar(List<Impreso190Item> listaimpreso190,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ResponseEntity<InputStreamResource> res = null;
		InputStream fileStream = null;
		HttpHeaders headers = new HttpHeaders();
		boolean gen = false;
		String ficheroPath = "";

		try {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"Impreso190ServiceImpl.impreso190descargar() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"Impreso190ServiceImpl.impreso190descargar() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty() && !listaimpreso190.isEmpty()) {

				if (listaimpreso190.size() == 1) {

					// TODO Obtener el fichero a descargar por el id, ¿posibilidad de ficheros que
					// se llamen igual en un mismo año?

					FcsFicheroImpreso190Key i190key = new FcsFicheroImpreso190Key();

					i190key.setIdfichero(Long.parseLong(listaimpreso190.get(0).getIdFichero()));
					i190key.setIdinstitucion(idInstitucion);

					FcsFicheroImpreso190 impreso190 = fcsFicheroImpreso190ExtendsMapper.selectByPrimaryKey(i190key);

					if (impreso190 != null) {
						String path = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190,
								idInstitucion.toString());
						path += File.separator + idInstitucion + File.separator + impreso190.getNombreFichero();

						File file = new File(path);
						if (!file.exists()) {

							Impreso190Item genImpreso = new Impreso190Item();
							genImpreso.setAnio(impreso190.getAnio().toString());
							genImpreso.setNombreContacto(impreso190.getNombre());
							genImpreso.setApellido1Contacto(impreso190.getApellido1());
							genImpreso.setApellido2Contacto(impreso190.getApellido2());
							genImpreso.setNomFicheroOriginal(impreso190.getNombreFichero());

							gen = generarImpreso(genImpreso, idInstitucion, usuarios.get(0).getIdusuario());
							if (gen == true) {
								path = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190,
										idInstitucion.toString());
								path += File.separator + idInstitucion + File.separator + impreso190.getNombreFichero();
								file = new File(path);
							}
						}
						fileStream = new FileInputStream(file);

						String tipoMime = getMimeType(".txt");

						headers.setContentType(MediaType.parseMediaType(tipoMime));
						headers.set("Content-Disposition",
								"attachment; filename=\"" + impreso190.getNombreFichero() + ".txt" + "\"");
						headers.setContentLength(file.length());
					} else {
						res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
								HttpStatus.FORBIDDEN);
					}

				} else {
					fileStream = getZipFileImpreso190(listaimpreso190, idInstitucion, usuarios.get(0).getIdusuario());

					headers.setContentType(MediaType.parseMediaType("application/zip"));
					headers.set("Content-Disposition", "attachment; filename=\"Impresos_190.zip\"");
				}

				res = new ResponseEntity<InputStreamResource>(new InputStreamResource(fileStream), headers,
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.error(
					"Impreso190ServiceImpl.impreso190descargar() -> Se ha producido un error al descargar un fichero 190",
					e);
		}

		return res;

	}

	private String getMimeType(String extension) {

		String mime = "";

		switch (extension.toLowerCase()) {

		case ".doc":
			mime = "application/msword";
			break;
		case ".docx":
			mime = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			break;
		case ".pdf":
			mime = "application/pdf";
			break;
		case ".jpg":
			mime = "image/jpeg";
			break;
		case ".png":
			mime = "image/png";
			break;
		case ".rtf":
			mime = "application/rtf";
			break;
		case ".txt":
			mime = "text/plain";
			break;
		}

		return mime;
	}

	private InputStream getZipFileImpreso190(List<Impreso190Item> listaImpreso190Item, Short idInstitucion,
			int idusuario) throws Exception {

		ByteArrayOutputStream byteArrayOutputStream = null;
		boolean gen = false;
		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			for (Impreso190Item doc : listaImpreso190Item) {

				FcsFicheroImpreso190Key i190key = new FcsFicheroImpreso190Key();

				i190key.setIdfichero(Long.parseLong(doc.getIdFichero()));
				i190key.setIdinstitucion(idInstitucion);

				FcsFicheroImpreso190 impreso190 = fcsFicheroImpreso190ExtendsMapper.selectByPrimaryKey(i190key);

				zipOutputStream.putNextEntry(new ZipEntry(impreso190.getNombreFichero() + ".txt"));
				String path = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190, idInstitucion.toString());
				path += File.separator + idInstitucion + File.separator + impreso190.getNombreFichero();
				File file = new File(path);
				if (!file.exists()) {
					Impreso190Item genImpreso = new Impreso190Item();
					genImpreso.setAnio(impreso190.getAnio().toString());
					genImpreso.setNombreContacto(impreso190.getNombre());
					genImpreso.setApellido1Contacto(impreso190.getApellido1());
					genImpreso.setApellido2Contacto(impreso190.getApellido2());
					genImpreso.setNomFicheroOriginal(impreso190.getNombreFichero());
					gen = generarImpreso(genImpreso, idInstitucion, idusuario);
					if (gen == true) {
						path = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190, idInstitucion.toString());
						path += File.separator + idInstitucion + File.separator + genImpreso.getNomFicheroOriginal();
						file = new File(path);
					}
				}
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
			}

			zipOutputStream.closeEntry();

			if (zipOutputStream != null) {
				zipOutputStream.finish();
				zipOutputStream.flush();
				IOUtils.closeQuietly(zipOutputStream);
			}

			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	@Override
	@Transactional
	public Impreso190DTO searchImpreso190(String[] anio, HttpServletRequest request) throws Exception {
		Error error = new Error();
		LOGGER.info(
				"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada al servicio para buscar los contrarios asociados a una designacion.");
		List<Impreso190Item> impresos = null;
		Impreso190DTO impreso190DTO = new Impreso190DTO();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.busquedaListaContrarios() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"DesignacionesServiceImpl.busquedaListaContrarios -> Entrada a servicio para la busqueda de contrarios");

				impresos = fcsFicheroImpreso190ExtendsMapper.getImpreso190(anio, idInstitucion);
				impreso190DTO.setImpreso190Item(impresos);

				error.setCode(200);
				impreso190DTO.setError(error);

			}
		}

		return impreso190DTO;
	}

	private boolean generarImpreso(Impreso190Item impreso190Item, Short idInstitucion, int idusuario) throws Exception {
		String sDirectorio = "";
		String codigoProvincia = "";
		int responseUpImpreso = 0;
		String sNombreFichero = "";
		boolean generado = false;
		sDirectorio = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190, idInstitucion.toString());

		CenPersona datosInstitucion = cenPersonaExtendsMapper
				.getDatosInstitucionForImpreso190(idInstitucion.toString());
		codigoProvincia = cenDireccionesExtendsMapper.getIdProvinciaImpreso190(
				datosInstitucion.getIdpersona().toString(), idInstitucion.toString(),
				SigaConstants.TIPO_DIRECCION_FACTURACION);

		FcsConfImpreso190Example impresoExample = new FcsConfImpreso190Example();
		impresoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);
		List<FcsConfImpreso190> confImpreso = fcsConfImpreso190Mapper.selectByExample(impresoExample);

		sNombreFichero = impreso190Item.getNomFicheroOriginal();

		new File(sDirectorio + File.separator + idInstitucion).mkdir();

		String sNombreCompletoFichero = sDirectorio + File.separator + idInstitucion + File.separator + sNombreFichero;
		File fichero = new File(sNombreCompletoFichero);

		fichero = generarImpreso190(impreso190Item, idInstitucion.toString());

		if (fichero != null) {
			if (fichero.getName().indexOf(".zip") != -1) {
				throw new Exception("Error al generar el impreso 190. Comprobar Log.");
			} else if (fichero.getName().indexOf(".190") != -1) {

				generado = true;

			}
		} else {
			throw new Exception("Error al generar el impreso 190.");
		}

		return generado;

	}

	@Override
	@Transactional
	public Impreso190DTO deleteImpreso190(List<Impreso190Item> impreso190Item, HttpServletRequest request)
			throws Exception {
		Error error = new Error();
		Impreso190DTO impreso190DTO = new Impreso190DTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int response = 0;

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"Impreso190ServiceImpl.deleteImpreso190() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"Impreso190ServiceImpl.deleteImpreso190() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"Impreso190ServiceImpl.deleteImpreso190() -> Entrada a servicio para eliminar el impreso190");

				for (Impreso190Item impreso : impreso190Item) {

					FcsFicheroImpreso190Key i190key = new FcsFicheroImpreso190Key();

					i190key.setIdfichero(Long.parseLong(impreso.getIdFichero()));
					i190key.setIdinstitucion(idInstitucion);

					FcsFicheroImpreso190 impreso190 = fcsFicheroImpreso190ExtendsMapper.selectByPrimaryKey(i190key);

					if (impreso != null) {
						String path = getDirectorioFichero("FCS", SigaConstants.PATH_IMPRESO190,
								idInstitucion.toString());
						path += File.separator + idInstitucion + File.separator + impreso190.getNombreFichero();

						File file = new File(path);
						if (file.exists()) {
							file.delete();

						}

						int delimpreso190 = fcsFicheroImpreso190ExtendsMapper.deleteByPrimaryKey(i190key);

						if (delimpreso190 != 0) {
							GenFicheroKey genFichero = new GenFicheroKey();
							genFichero.setIdfichero(impreso190.getIdGenFichero());
							genFichero.setIdinstitucion(idInstitucion);
							GenFichero fichero = genFicheroExtendsMapper.selectByPrimaryKey(genFichero);
							if (fichero != null) {
								response = genFicheroExtendsMapper.deleteByPrimaryKey(fichero);
							} else {
								error.setCode(500);
								error.setDescription("general.mensaje.error.bbdd");
								impreso190DTO.setError(error);
							}

						}
					}

					if (response != 0) {
						error.setCode(200);
						error.setDescription("facturacionSJCS.impreso190.impreso190Eliminar");
						impreso190DTO.setError(error);
					} else {
						error.setCode(400);
						error.setDescription("general.mensaje.error.bbdd");
						impreso190DTO.setError(error);
						throw new Exception("Error no se ha podido eliminar");
					}

				}

			}
		}

		return impreso190DTO;
	}

	@Override
	@Transactional
	public Impreso190DTO getConfImpreso190(HttpServletRequest request) throws Exception {
		Error error = new Error();
		Impreso190DTO impreso190DTO = new Impreso190DTO();
		List<Impreso190Item> confImpreso190 = new ArrayList<Impreso190Item>();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"Impreso190ServiceImpl.deleteImpreso190() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"Impreso190ServiceImpl.deleteImpreso190() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info(
						"Impreso190ServiceImpl.deleteImpreso190() -> Entrada a servicio para eliminar el impreso190");

				confImpreso190 = fcsFicheroImpreso190ExtendsMapper.getConfImpreso190(idInstitucion);

				if (!confImpreso190.isEmpty()) {
					impreso190DTO.setImpreso190Item(confImpreso190);
					error.setCode(200);
					impreso190DTO.setError(error);
				} else {
					error.setCode(400);
					error.description("impreso190.mensajeError.impresoConfNoEncontrado");
					impreso190DTO.setError(error);
					throw new Exception("Error en FcsFacturacionJG.generarModelo190()");
				}

			}
		}

		return impreso190DTO;
	}

	@Override
	public ComboDTO getComboAnio(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.debug(
					"getComboAnio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener debugrmación del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.debug(
					"getComboAnio() / fcsFicheroImpreso190ExtendsMapper.getComboAnio() -> Entrada a fcsFicheroImpreso190ExtendsMapper para obtener los combo");

			comboItems = fcsFicheroImpreso190ExtendsMapper.getComboAnio(idInstitucion);

			LOGGER.debug(
					"getComboAnio() / fcsFicheroImpreso190ExtendsMapper.getComboAnio() -> Salida a fcsFicheroImpreso190ExtendsMapper para obtener los combo");

			if (comboItems != null) {
				comboDTO.setCombooItems(comboItems);
			}

		}
		LOGGER.debug("getLabel() -> Salida del servicio para obtener los años del combo año del impreso190");
		return comboDTO;
	}

}
