package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.CenCargamasiva;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoExample;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.FcsPagoColegiadoMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagoColegiadoExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IImpreso190Service;
import org.springframework.beans.factory.annotation.Autowired;

public class IImpreso190SJCSServiceImpl implements IImpreso190Service {

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
	private FicherosServiceImpl ficherosServiceImpl;

	/*
	 * @Override public Impreso190DTO impreso190generar(Impreso190Item
	 * impreso190Item, HttpServletRequest request) { String salida = null; String
	 * sNombreFichero = ""; try {
	 * 
	 * UsrBean user = (UsrBean) request.getSession().getAttribute("USRBEAN");
	 * 
	 * // borro el formulario en session de Avanzada GenerarImpreso190Form miform =
	 * (GenerarImpreso190Form)request.getAttribute("generarImpreso190Form");
	 * 
	 * // realizo la generaciónv sNombreFichero = miform.getNombreFicheroOriginal();
	 * String sDirectorio = getPathTemporal(user);
	 * 
	 * // creo el directorio si no existe FileHelper.mkdirs(sDirectorio +
	 * File.separator + user.getLocation());
	 * 
	 * String sNombreCompletoFichero=sDirectorio + File.separator +
	 * user.getLocation() + File.separator + sNombreFichero; File fichero = new
	 * File(sNombreCompletoFichero);
	 * 
	 * FcsFacturacionJGAdm admFac = new
	 * FcsFacturacionJGAdm(this.getUserBean(request)); try {
	 * request.removeAttribute(i"mensaje"); fichero =
	 * admFac.generarImpreso190(miform, user.getLocation());
	 * miform.setNombreFichero(fichero.getName());
	 * 
	 * //Controlo si se ha generado un zip por tener errores: if
	 * (fichero.getName().indexOf(".zip")!=-1) { request.setAttribute("mensaje",
	 * "messages.error.log190"); request.setAttribute("logError", "SI"); } else
	 * request.setAttribute("logError", "NO"); } catch (SIGAException se) {
	 * request.setAttribute("mensaje",se.getLiteral(this.getLenguaje(request))); }
	 * 
	 * salida = "exitoImpreso190"; } catch (Exception e) {
	 * throwExcp("messages.general.error",new String[]
	 * {"modulo.facturacionSJCS"},e,null); } // COMUN return salida; }
	 */

	private File generarImprep190(Impreso190Item impreso190Item, String idinstitucion) {

		File impreso190 = null;
		List<Double> importes = new ArrayList<Double>();
		List<Double> irpfs = new ArrayList<Double>();
		List<String> datos = new ArrayList<String>();
		List<String> clavesM190 = new ArrayList<String>();
		double importeTotal = 0;
		double irpfTotal = 0;
		File fichero = null;
		File ficheroErrores = null;
		String sPagos = "";
		boolean hayError = false;
		Impreso190DTO error = new Impreso190DTO();
		String codigoProvincia;
		ArrayList<String> dataPersonas = null;

		// obtener datos de la institucion
		CenPersona datosInstitucion = cenPersonaExtendsMapper.getDatosPersonaForImpreso190(idinstitucion);

		// obtener datos de las provincias
		codigoProvincia = cenDireccionesExtendsMapper.getIdProvinciaImpreso190(
				datosInstitucion.getIdpersona().toString(), idinstitucion, SigaConstants.TIPO_DIRECCION_FACTURACION);

		List<FacAbono> abonos = facAbonoExtendsMapper.getPagosCerrados(idinstitucion, impreso190Item.getAnio());
		if (abonos != null || abonos.size() > 0) {
			for (int i = 0; i < abonos.size(); i++) {
				sPagos += abonos.get(i).getIdpagosjg().toString();
				if (i < abonos.size() - 1)
					sPagos += ",";
			}

			// obtener el IRPF para cada uno de los pagos realizados
			List<Impreso190Item> pagoColegiado = fcsPagoColegiadoExtendsMapper.getIrpfPagos(idinstitucion, sPagos);

			if (pagoColegiado != null || pagoColegiado.size() > 0) {
				for (Impreso190Item pagoCol : pagoColegiado) {
					String idPersona = pagoCol.getIdpersonaImpreso();
					Double importeIrpfPersona = Double.parseDouble(pagoCol.getTotalImporteIrpf());
					Double importePagadoPersona = Double.parseDouble(pagoCol.getTotalImportePagado());
					String claveM190 = pagoCol.getClaveM190();

					if (!importeIrpfPersona.equals(new Double(0.0))) {

						// obtener datos de persona por idPersona;
						CenPersona datosPersona = cenPersonaExtendsMapper.getDatosPersonaForImpreso190(idPersona);

						if (datosPersona != null) {
							String tipoIdentificacion = datosPersona.getIdtipoidentificacion().toString();
							String nombre = datosPersona.getNombre();
							String apellidos1 = datosPersona.getApellidos1();
							String apellidos2 = datosPersona.getApellidos2();
							String numIdentificacion = datosPersona.getNifcif();

							dataPersonas.add(idPersona);
							dataPersonas.add(tipoIdentificacion);
							dataPersonas.add(nombre);
							dataPersonas.add(apellidos1);
							dataPersonas.add(apellidos2);
							dataPersonas.add(numIdentificacion);
						} else {
							hayError = true;
						}

						if (!hayError) {
							String clave = idPersona + claveM190;
							datos.addAll(Integer.parseInt(clave), dataPersonas);

							irpfs.add(Integer.parseInt(clave), importeIrpfPersona);
							irpfTotal += importeIrpfPersona;

							importes.add(Integer.parseInt(clave), importePagadoPersona);
							importeTotal += importePagadoPersona;

							clavesM190.add(Integer.parseInt(clave), claveM190);
							importeTotal += importePagadoPersona;

						}

					}

				}
			}

		}
		
	/**
	 * @TODO seguir con la generacion del fichero
	 */
		
		// realizo la generaci�n del directorio y del fichero:
					String sNombreFichero = impreso190Item.getNomFichero();
					 if (impreso190Item.getNomFichero()!="" && impreso190Item.getNomFichero().indexOf(".190")<0){
			    	   sNombreFichero = impreso190Item.getNomFichero()+".190";
					 }  
			    	//String sDirectorio = getDirectorioFichero(Short.parseShort(idinstitucion));
			    	String sDirectorio ="";
			    	String sCamino = sDirectorio + File.separator + idinstitucion;
			    	
			    	//LOG_IMPRESO190_[IDINSTITUCION]_[A�O]_[FECHA_EJECUCION]
			        Calendar cal = Calendar.getInstance();
		            Date hora = cal.getTime();
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMMM-dd_hh:mm:ss");
		            String fechaEjecucion = sdf.format(hora);
		    		String sNombreLog = "IMPRESO190_"+impreso190Item.getAnio()+".log.xls";
					
			    	// creo el directorio si no existe:
		    		SIGAServicesHelper.uploadFichero(sDirectorio, sNombreFichero, null);

			    	String sNombreCompletoFichero = sCamino + File.separator + sNombreFichero;
			    	String sNombreFicheroErrorLog = sCamino + File.separator + sNombreLog;	    	
			    	String sNombreFicheroZip = sCamino + File.separator + sNombreFichero+".zip"; 

		return impreso190;
	}
	
//	private File generarModelo190(String nombreFichero, String anio, String telefonoContacto, String nombreContacto, 
//			String apellido1Contacto, String apellido2Contacto, String soporte, String codigoProvincia, 
//			List<String> irpfs, List<String>  importes, CenPersona  datosInstitucion, List<String>  datos
//			, double irpfTotal, double importeTotal,List<String> clavesM190) {
//	
//		BufferedWriter bw = null;
//		File fichero = null;
//		
//		try {
//			fichero = new File(nombreFichero);
//			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero),"ISO-8859-1"));
//			
//			if (irpfs.size()==0) {
//				throw new Exception("messages.factSJCS.noRetenciones190");
//			}
//				
//			// registro unico de institucion
//			//
//			// MODELO 190: REGISTRO DE RETENEDOR
//			//
//			String linea = "";
//			int nreg=importes.size();
//			linea += "1"; //1: tipo registro
//			linea += "190"; //2-4: modelo declaracion
//			linea += UtilidadesImpreso190.formatea(anio,4,true); //5-8 ejercicio (anyo)
//			linea += UtilidadesImpreso190.formatea(datosInstitucion.getNifcif(),9,true); //9-17: NIF del declarante
//			
//			String nombreInstitucion = datosInstitucion.getNombre().toString();
//			nombreInstitucion = nombreInstitucion.toUpperCase();
//			nombreInstitucion = UtilidadesImpreso190.quitarAcentos((nombreInstitucion));
//			nombreInstitucion = UtilidadesImpreso190.validarModelo190(nombreInstitucion);
//			linea += UtilidadesImpreso190.formatea(nombreInstitucion,40,false); //18-57: apellidos y nombre, denominaci�n o raz�n social del declarante (nombre de la institucion)
//			
//			linea += soporte; //58: tipo de soporte
//			linea += UtilidadesImpreso190.formatea(telefonoContacto,9,true); //59-67: telefono de contacto
//			
//			apellido1Contacto = apellido1Contacto.toUpperCase();
//			apellido2Contacto = apellido2Contacto.toUpperCase();
//			nombreContacto = nombreContacto.toUpperCase();
//			apellido1Contacto = UtilidadesImpreso190.quitarAcentos(apellido1Contacto);
//			apellido1Contacto = UtilidadesImpreso190.validarModelo190(apellido1Contacto);
//			apellido2Contacto = UtilidadesImpreso190.quitarAcentos(apellido2Contacto);
//			apellido2Contacto = UtilidadesImpreso190.validarModelo190(apellido2Contacto);
//			nombreContacto    = UtilidadesImpreso190.quitarAcentos(nombreContacto);
//			nombreContacto    = UtilidadesImpreso190.validarModelo190(nombreContacto);
//			linea += UtilidadesImpreso190.formatea(apellido1Contacto + " " + apellido2Contacto + " " + nombreContacto,40,false); //68-107: nombre de contacto
//			
//			linea += "170"+UtilidadesImpreso190.relleno("0",10); //108-120: numero identificativo de la declaracion
//			linea += UtilidadesImpreso190.rellenoPosiciones(" ",121,122); //121-122: complementaria
//			linea += UtilidadesImpreso190.rellenoPosiciones("0",123,135); //123-135: numero identificativo declaracion anterior
//			linea += UtilidadesImpreso190.formatea(String.valueOf(nreg),9,true);; //136-144: numero de percepciones (DE MOMENTO SOLO LOS IRPFS)
//			
//			//145-160: importe total
//			List<String> valor = UtilidadesImpreso190.desdoblarDouble(new Double(importeTotal));
//			linea += UtilidadesImpreso190.formatea(valor.get(0),1,false); //145: signo
//			linea += UtilidadesImpreso190.formatea(valor.get(1),13,true); //146-158: entera
//			linea += UtilidadesImpreso190.formatea(valor.get(2),2,true); //159-160: decimal
//			
//			//161-175: retencion total			
//			valor = UtilidadesImpreso190.desdoblarDouble(new Double (irpfTotal));
//			linea += UtilidadesImpreso190.formatea(valor.get(1),13,true); //161-173: entera
//			linea += UtilidadesImpreso190.formatea(valor.get(2),2,true); //174-175: decimal
//			
//			linea += UtilidadesImpreso190.rellenoPosiciones(" ",176,225); //176-225: correo electronico de contacto (ahora mismo no lo tenemos, pero en el cambio de 2017 dejaron este hueco que se puede usar)
//			linea += UtilidadesImpreso190.rellenoPosiciones(" ",226,487); //226-487: relleno
//			linea += UtilidadesImpreso190.rellenoPosiciones(" ",488,500); //488-500: sello electronico
//			
//			// escribo
//			// cambio a formato DOS
//			linea += "\r\n";
//			bw.write(linea);
//			//bw.newLine();
//
//			//
//			// MODELO 190: REGISTROS DE PERCEPCION
//			//
//			List<String> claves = importes.in();
//			while (claves.hasMoreElements()) {
//				Object o = claves.nextElement();
//				String persona = (String)o;
//				List<String> datosPersonaRegistro =  datos.get(persona);
//				
//				// registro unico de persona
//				linea = "";
//				linea += "2"; //1: tipo registro
//				linea += "190"; //2-4: modelo declaracion
//				linea += UtilidadesImpreso190.formatea(anio,4,true); //5-8: ejercicio (anyo)
//				linea += UtilidadesImpreso190.formatea(datosInstitucion.getn,9,true); //9-17: NIF declarante
//				linea += UtilidadesImpreso190.formatea(datosPersonaRegistro.get("NIDENTIFICACION"),9,true); //18-26: NIF perceptor
//				linea += UtilidadesImpreso190.rellenoPosiciones(" ",27,35); //27-35: NIF representante legal
//				
//				String apellido = (String)datosPersonaRegistro.get("APELLIDOS1");
//				//Si es sociedad sin abreviatura se deja solo el nombre
//				String nombrePerceptor = (String) datosPersonaRegistro.get(apellido.equals("#NA") ? "NOMBRE" : "NOMBREPERSONA");
//				nombrePerceptor = nombrePerceptor.toUpperCase();  
//				nombrePerceptor = UtilidadesImpreso190.quitarAcentos(nombrePerceptor);
//				nombrePerceptor = UtilidadesImpreso190.validarModelo190(nombrePerceptor);
//				linea += UtilidadesImpreso190.formatea(nombrePerceptor,40,false); //36-75: apellidos y nombre o denominaci�n del perceptor
//				
//				linea += UtilidadesImpreso190.formatea(codigoProvincia,2,true); //76-77: provincia
//				linea += (String)clavesM190.get(persona); //78-80: clave+subclave de percepcion ("G01" casi siempre)
//				
//				//Obtengo el IRPF y el importe para esta persona:
//				Double personaImporteTotalDouble = (Double)importes.get(persona);
//				Double personaIrpfTotalDouble = (Double)irpfs.get(persona);
//				// Si no hay retenciones "no debe aparecer" en el archivo
//				if(!personaIrpfTotalDouble.equals(new Double(0))){
//				
//					//81-94: percepciones dinerarias
//					valor = UtilidadesImpreso190.desdoblarDouble(personaImporteTotalDouble); 
//					linea += UtilidadesImpreso190.formatea(valor.get(0),1,false); //81: signo
//					linea += UtilidadesImpreso190.formatea(valor.get(1),11,true); //82-92 entera
//					linea += UtilidadesImpreso190.formateaDecimal(valor.get(2),2); //93-94 decimal
//					
//					//95-107: retenciones practicadas
//					valor = UtilidadesImpreso190.desdoblarDouble(personaIrpfTotalDouble); 
//					linea += UtilidadesImpreso190.formatea(valor.get(1),11,true); //95-105: entera
//					linea += UtilidadesImpreso190.list(valor.get(2),2); //106-107: decimal
//					
//					//108-147: percepciones en especie (no aplican)
//					linea += " "; //108: signo de percepcion en especie
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",109,121); //109-121: valor de percepcion en especie
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",122,134); //122-134: valor de percepcion en especie
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",135,147); //135-147: valor de percepcion en especie
//					
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",148,151); //148-151: ejercicio devengo
//					
//					boolean esCeutaOMelilla = (codigoProvincia.equals(SigaConstants.CODIGO_PROVINCIA_CEUTA)||codigoProvincia.equals(SigaConstants.CODIGO_PROVINCIA_MELILLA));
//					linea += esCeutaOMelilla ? "1":"0"; //152: caso de ceuta y melilla
//					
//					//153-254: Datos adicionales no necesarios para nuestro caso
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",153,156); //153-156: anyo nacimiento
//					linea += "0"; //157: situacion familiar
//					linea += UtilidadesImpreso190.rellenoPosiciones(" ",158,166); //158-166: nif conyuge
//					linea += "0"; //167: discapacidad
//					linea += "0"; //168: contrato o relacion
//					linea += " "; //169: MOVILIDAD GEOGR�FICA - ACEPTACION EN 2014
//					linea += "0"; //170: movilidad geografica
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",171,183); //171-183: reducciones aplicables
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",184,196); //184-196: gastos deducibles
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",197,209); //197-209: pensiones compensatorias
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",210,222); //210-222: anualidades por alimentos
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",223,228); //223-228: hijos y otros descendientes
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",229,240); //229-240: hijos y otros descendientes con discapacidad
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",241,244); //241-244: ascendientes
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",245,250); //245-250: ascendientes con discapacidad
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",251,253); //251-253: n� de hijos
//					linea += "0"; //254: prestamo vivienda
//					
//					//255-281: percepciones dinerarias derivadas de incapacidad laboral (no aplican)
//					linea += " "; //255: Signo de las percepciones dinerarias derivadas de incapacidad laboral
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",256,281); //256-281: importe de las percepciones dinerarias derivadas de incapacidad laboral
//					//282-321: percepciones en especie derivadas de incapacidad laboral (no aplican)
//					linea += " "; //282: Signo de las percepciones en especie derivadas de incapacidad laboral
//					linea += UtilidadesImpreso190.rellenoPosiciones("0",283,321); //283-321: importe de las percepciones en especie derivadas de incapacidad laboral
//					
//					linea += UtilidadesImpreso190.rellenoPosiciones(" ",322,500); //322-500: blancos 
//					
//					// cambio a formato DOS
//					linea += "\r\n";
//					
//					// escribiendo la linea
//					bw.write(linea);
//				}
//			}
//			//bw.flush();
//			//bw.newLine();
//			bw.close();
//		} catch (IOException e) {
//			try {
//			if (bw!=null) 
//				bw.close();
//			} catch (Exception e2) {
//				throw new Exception ("Error en FcsFacturacionJG.generarModelo190()");	
//			}
//		} catch (Exception siga){
//			throw siga;
//		} 
//		return fichero;
//	}
	
	
	
	
	
	

		
		

	
//private Long uploadFile(byte[] bytes, Impreso190Item impreso190item, boolean isLog){
//		
//		Date dateLog = new Date();
//		FicheroVo ficheroVo = new FicheroVo();
//
//		String directorioFichero = getDirectorioFichero(Short.parseShort(impreso190item.getIdInstitucion()));
//		ficheroVo.setDirectorio(directorioFichero);
//		String nombreFicheroString = impreso190item.getNomFichero();
//		ficheroVo.setNombre(nombreFicheroString); 
//		ficheroVo.setDescripcion("Impreso 190 " + ficheroVo.getNombre());
//
//		ficheroVo.setIdinstitucion(Short.parseShort(impreso190item.getIdInstitucion()));
//		ficheroVo.setFichero(bytes);
//		ficheroVo.setExtension("txt");
//
//		ficheroVo.setUsumodificacion(Integer.valueOf(impreso190item.getUsuarioModificacion()));
//		ficheroVo.setFechamodificacion(new Date());
//		ficherosServiceImpl.insert(ficheroVo);
//
//		if (isLog) {
//			ficheroVo.setDescripcion("log_" + ficheroVo.getDescripcion());
//			ficheroVo.setNombre("log_" + ficheroVo.getNombre());
//		}
//
//		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
//		return ficheroVo.getIdfichero();
//	}
//	
//	private String getDirectorioFichero(Short idInstitucion) {
//		Date dateLog = new Date();
//
//		// Extraer propiedad
//		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
//		genPropertiesExampleP.createCriteria().andParametroEqualTo("cen.cargaExcel.ficheros.path");
//		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
//		String pathCV = genPropertiesPath.get(0).getValor(); 
//		
//		StringBuffer directorioFichero = new StringBuffer(pathCV);
//		directorioFichero.append(idInstitucion);
//		directorioFichero.append(File.separator);
//
//		// Extraer propiedad
//		GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
//		genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.cargamasivaCV");
//		List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
//		directorioFichero.append(genPropertiesDirectorio.get(0).getValor());
//
//		return directorioFichero.toString();
//	}
	
	

}
