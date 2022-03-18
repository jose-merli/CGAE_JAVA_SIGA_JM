package org.itcgae.siga.commons.utils;

import org.itcgae.sspp.ws.registroSociedades.ColegioDocument.Colegio;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto.Fax;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto.Telefono;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto.TelefonoMovil;
import org.itcgae.sspp.ws.registroSociedades.DatosCargoDocument.DatosCargo;
import org.itcgae.sspp.ws.registroSociedades.DatosEntidad;
import org.itcgae.sspp.ws.registroSociedades.DatosEntidad.FormaSocial;
import org.itcgae.sspp.ws.registroSociedades.DatosPersona;
import org.itcgae.sspp.ws.registroSociedades.DatosProfesionalDocument.DatosProfesional;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion.Poblacion;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion.Provincia;
import org.itcgae.sspp.ws.registroSociedades.IdentificacionDocument.Identificacion;
import org.itcgae.sspp.ws.registroSociedades.IntegranteSociedadDocument.IntegranteSociedad;
import org.itcgae.sspp.ws.registroSociedades.ProfesionalAbogadoDocument.ProfesionalAbogado;
import org.itcgae.sspp.ws.registroSociedades.ProfesionalAbogadoPropioDocument.ProfesionalAbogadoPropio;
import org.itcgae.sspp.ws.registroSociedades.ProfesionalDocument.Profesional;

/**
 * Clase que realiza validaciones de diferentes parámetros.
 *
 */
public class Validaciones {
	
	/**
	 * Valida una cadena de caracteres que debe tener 9 caracteres y coincidir con el patrón de NIF.
	 * @param param
	 * @return
	 */
	public static boolean validaNIF(String param) {
		return validar(param, Patrones.NIF) && param.length() == 9;
	}
	
	/**
	 * Valida una cadena de caracteres que debe tener 9 caracteres y coincidir con el patrón de CIF.
	 * @param param
	 * @return
	 */
	public static boolean validaCIF(String param) {
		return validar(param, Patrones.CIF) && param.length() == 9;
	}
	
	/**
	 * Valida una cadena de caracteres que debe tener 9 caracteres y coincidir con el patrón de NIE.
	 * @param param
	 * @return
	 */
	public static boolean validaNIE(String param) {
		return validar(param, Patrones.NIE) && param.length() == 9;
	}
	
	/**
	 * Valida los campos de un integrante de una sociedad.
	 * @param integrante
	 * @return
	 */
	public static boolean validaIntegranteSociedad(IntegranteSociedad integrante) {
		
		// Comprobamos si los datos personales del integrante físico vienen rellenos
		if (integrante.getIntegranteFisico() != null && integrante.getIntegranteFisico().getDatosPersona() == null) {
			return false;
		} else if (integrante.getIntegranteFisico() != null) {
			
			// Comprobamos los datos personales
			if (!validaDatosPersona(integrante.getIntegranteFisico().getDatosPersona())) {
				return false;
			}
			
			// Comprobamos los datos profesionales
			if (integrante.getIntegranteFisico().getDatosProfesional() != null &&
				!validaDatosProfesional(integrante.getIntegranteFisico().getDatosProfesional())) {
				return false;
			}
			
			// Comprobamos los datos de cargo
			if (integrante.getIntegranteFisico().getDatosCargo() != null &&
				!validaDatosCargo(integrante.getIntegranteFisico().getDatosCargo())) {
				return false;
			}
		}
		
		// Comprobamos si los datos de entidad y cargo del integrante jurídico vienen rellenos
		if (integrante.getIntegranteJuridico() != null &&
			integrante.getIntegranteJuridico().getDatosEntidad() == null &&
			integrante.getIntegranteJuridico().getDatosCargo() == null) {
			return false;
		} else if (integrante.getIntegranteJuridico() != null) {
			
			// Comprobamos los datos de entidad
			if (!validaDatosEntidad(integrante.getIntegranteJuridico().getDatosEntidad())) {
				return false;
			}
			
			// Comprobamos los datos de cargo
			if (!validaDatosCargo(integrante.getIntegranteJuridico().getDatosCargo())) {
				return false;
			}
		}
		
		// Comprobamos si el integrante de la sociedad tiene una fecha de modificación
		if (integrante.getFechaModificacion() == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Valida los datos de entidad.
	 * @param datosEntidad
	 * @return
	 */
	private static boolean validaDatosEntidad(DatosEntidad datosEntidad) {
		
		// Comprueba si se ha proporcionado un CIF o NIF válido
		if ( !Validaciones.validaNIF(datosEntidad.getCIFNIF()) &&
			 !Validaciones.validaCIF(datosEntidad.getCIFNIF()) ) {
			return false;
		}
		
		// Comprueba si se ha proporcionado una denominación válida
		if (datosEntidad.getDenominacion() != null &&
			!validaDenominacion(datosEntidad.getDenominacion())) {
			return false;
		}
		
		// Comprueba, si se ha proporcionado, el campo forma social
		if (datosEntidad.getFormaSocial() != null &&
			!validaFormaSocial(datosEntidad.getFormaSocial())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida el campo forma social de una entidad
	 * @param formaSocial
	 * @return
	 */
	private static boolean validaFormaSocial(FormaSocial formaSocial) {
		
		// Se comprueba que no exceda los 20 caracteres
		if (formaSocial.getStringValue() != null && formaSocial.getStringValue().length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida la denominacion de una entidad.
	 * @param denominacion
	 * @return
	 */
	private static boolean validaDenominacion(String denominacion) {

		// Se comprueba que la denominación tenga un mínimo de 1 y un máximo de 500 caracteres
		if (denominacion.length() >= 1 && denominacion.length() >= 500) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean validaDatosCargo(DatosCargo datosCargo) {

		// Comprueba si se ha proporcionado un cargo válido
		if (datosCargo.getCargo() != null &&
			!validaCargo(datosCargo.getCargo())) {
			return false;
		}
		
		// Comprueba, si se ha proporcionado una descripción del cargo, si el campo es válido
		if (datosCargo.getDescCargo() != null && 
			!validaDescCargo(datosCargo.getDescCargo())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida la descripcion de un cargo.
	 * @param descCargo
	 * @return
	 */
	private static boolean validaDescCargo(String descCargo) {

		// La descripción de un cargo debe tener como máximo 1000 caracteres
		if (descCargo.length() <= 1000) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida el campo cargo.
	 * @param cargo
	 * @return
	 */
	private static boolean validaCargo(String cargo) {

		// Se comprueba que el cargo tenga un mínimo de 1 y un máximo de 100 caracteres
		if (cargo.length() >= 1 && cargo.length() <= 100) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida los datos del profesional.
	 * @param datosProfesional
	 * @return
	 */
	private static boolean validaDatosProfesional(DatosProfesional datosProfesional) {
		
		// Comprobamos los datos profesionales de abogado
		if (datosProfesional.getProfesionalAbogado() != null &&
			!validaProfesionalAbogado(datosProfesional.getProfesionalAbogado())) {
			return false;
		}
		
		// Comprobamos los datos profesionales de abogado propio
		if (datosProfesional.getProfesionalAbogadoPropio() != null &&
			!validaProfesionalAbogadoPropio(datosProfesional.getProfesionalAbogadoPropio())) {
			return false;
		}
		
		// Comprobamos los datos profesionales
		if (datosProfesional.getProfesional() != null &&
			!validaProfesional(datosProfesional.getProfesional())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida los datos profesionales de abogado.
	 * @param profesionalAbogado
	 * @return
	 */
	private static boolean validaProfesionalAbogado(ProfesionalAbogado profesionalAbogado) {
		
		// Se comprueban los datos del colegio
		if (profesionalAbogado.getColegio() != null &&
			!validaColegio(profesionalAbogado.getColegio())) {
			return false;
		}
		
		// Se comprueban el número de colegiado
		if (profesionalAbogado.getNumColegiado() != null &&
			!validaNumColegiado(profesionalAbogado.getNumColegiado())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida un número de colegiado.
	 * @param numColegiado
	 * @return
	 */
	private static boolean validaNumColegiado(String numColegiado) {
		
		// Se comprueba que el número de colegiado tenga un mínimo de 1 y un máximo de 6 caracteres
		if (numColegiado.length() >= 1 && numColegiado.length() <= 6) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida el colegio.
	 * @param colegio
	 * @return
	 */
	private static boolean validaColegio(Colegio colegio) {
		
		// Se comprueba que el código del colegio sean 6 caracteres
		if (colegio.getCodigoColegio().length() != 6) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida los datos profesionales de abogado propio.
	 * @param profesionalAbogadoPropio
	 * @return
	 */
	private static boolean validaProfesionalAbogadoPropio(ProfesionalAbogadoPropio profesionalAbogadoPropio) {
		
		// Si existe una identificación, se validan sus datos
		if (profesionalAbogadoPropio.getIdentificacion() != null) {
			if (!validaIdentificacion(profesionalAbogadoPropio.getIdentificacion())) {
				return false;
			}
		}
		
		// Se comprueban los datos del colegio
		if (profesionalAbogadoPropio.getColegio() != null &&
			!validaColegio(profesionalAbogadoPropio.getColegio())) {
			return false;
		}
		
		// Se comprueban el número de colegiado
		if (profesionalAbogadoPropio.getNumColegiado() != null &&
			!validaNumColegiado(profesionalAbogadoPropio.getNumColegiado())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida datos profesionales.
	 * @param profesional
	 * @return
	 */
	private static boolean validaProfesional(Profesional profesional) {
		
		// Se comprueba la profesión
		if (profesional.getProfesion() == null ||
			!validaProfesion(profesional.getProfesion())) {
			return false;
		}
		
		// Se comprueban los datos del colegio
		if (profesional.getNombreColegio() != null &&
			!validaNombreColegio(profesional.getNombreColegio())) {
			return false;
		}
		
		// Se comprueban el número de colegiado
		if (profesional.getNumColegiado() != null &&
			!validaNumColegiado(profesional.getNumColegiado())) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Valida el nombre del colegio
	 * @param nombreColegio
	 * @return
	 */
	private static boolean validaNombreColegio(String nombreColegio) {
		
		// Se comprueba que el nombre del colegio no supere los 100 caracteres
		if (nombreColegio.length() <= 100) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida los datos de profesion de un profesional.
	 * @param profesion
	 * @return
	 */
	private static boolean validaProfesion(String profesion) {
		
		// Se comprueba que los caracteres de la profesion estén comprendidos entre 3 y 20
		if (profesion.length() >= 3 && profesion.length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida los datos personales de un integrante.
	 * @param datosPersona
	 * @return
	 */
	public static boolean validaDatosPersona(DatosPersona datosPersona) {
		
		// Comprobamos si en los datos personales figura el nombre
		if (datosPersona.getNombre() == null) {
			return false;
		}
		
		// Si existe una identificación, se validan sus datos
		if (datosPersona.getIdentificacion() != null) {
			if (!validaIdentificacion(datosPersona.getIdentificacion())) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Valida los datos de identificacion de una persona.
	 * @param identificacion
	 * @return
	 */
	private static boolean validaIdentificacion(Identificacion identificacion) {
		
		// Comprueba si existe un documento de identificación
		if (identificacion.getNIF() == null && identificacion.getNIE() == null) {
			return false;
		}
		
		// Comprueba si tiene un NIE y si lo tiene, si es válido
		if (identificacion.getNIE() != null && !validaNIE(identificacion.getNIE())) {
			return false;
		}
		
		// Comprueba si tiene NIF y si lo tiene, si es válido
		if (identificacion.getNIF() != null && !validaNIF(identificacion.getNIF())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida un parámetro mediante un patrón especificado.
	 * @param param
	 * @param patron
	 * @return
	 */
	public static boolean validar(String param, String patron) {
		boolean valid = false;
		if(param != null && param.matches(patron)) {
			valid = true;
		}
		return valid;
	}

	/**
	 * Valida los datos de dirección de una sociedad.
	 * @param direccion
	 * @return
	 */
	public static boolean validaDireccion(Direccion direccion) {
		
		// Comprueba que la descripción del tipo de vía no supere los 15 caracteres
		if (direccion.getDescTipoVia() != null &&
			direccion.getDescTipoVia().length() > 15) {
			return false;
		}
		
		// Comprueba que tenga un domicilio válido
		if (direccion.getDomicilio() != null &&
			!validaDomicilio(direccion.getDomicilio())) {
			return false;
		}
		
		// Comprueba que tenga un código postal de 5 caracteres
		if (direccion.getCodigoPostal() != null &&
			direccion.getCodigoPostal().length() != 5) {
			return false;
		}
		
		// Comprueba que tenga una provincia válida
		if (direccion.getProvincia() == null ||
			!validaProvincia(direccion.getProvincia())) {
			return false;
		}
		
		// Comprueba que tenga una población válida
		if (direccion.getPoblacion() != null &&
			!validaPoblacion(direccion.getPoblacion())) {
			return false;
		}
		
		// Comprueba que el campo contacto sea válido
		if (direccion.getContactoArray().length > 0) {
			for (Contacto contacto: direccion.getContactoArray()) {
				
				if (!validaContacto(contacto)) {
					return false;
				}
			}
		} else {
			return false;
		}
		
		// Comprueba que tenga una dirección de correo electrónica válida
		if (direccion.getCorreoElectronico() != null &&
			direccion.getCorreoElectronico().getStringValue() != null &&
			direccion.getCorreoElectronico().getStringValue().length() > 100) {
			return false;
		}
		
		// Comprueba que tenga una página web válida
		if (direccion.getPaginaWeb() != null &&
			!validaPaginaWeb(direccion.getPaginaWeb())) {
			return false;
		}
				
		return true;
	}

	/**
	 * Valida un contacto de una dirección.
	 * @param contacto
	 * @return
	 */
	private static boolean validaContacto(Contacto contacto) {
		
		// Comprueba que el número de teléfono sea válido
		if (contacto.getTelefono() != null &&
			!validaTelefono(contacto.getTelefono())) {
			return false;
		}
		
		// Comprueba que el número de teléfono móvil sea válido
		if (contacto.getTelefonoMovil() != null &&
			!validaTelefonoMovil(contacto.getTelefonoMovil())) {
			return false;
		}
		
		// Comprueba que el fax sea válido
		if (contacto.getFax() != null &&
			!validaFax(contacto.getFax())) {
			return false;
		}
		return true;
	}

	/**
	 * Valida el fax de un contacto.
	 * @param fax
	 * @return
	 */
	private static boolean validaFax(Fax fax) {
		
		// Comprueba que los caracteres del fax estén comprendidos entre 6 y 20
		if (fax.getStringValue() != null &&
			fax.getStringValue().length() >= 6 &&
			fax.getStringValue().length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida el teléfono móvil de un contacto
	 * @param telefonoMovil
	 * @return
	 */
	private static boolean validaTelefonoMovil(TelefonoMovil telefonoMovil) {
		
		// Comprueba que los caracteres del telefono móvil estén comprendidos entre 9 y 20
		if (telefonoMovil.getStringValue() != null &&
			telefonoMovil.getStringValue().length() >= 9 &&
			telefonoMovil.getStringValue().length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida el teléfono de un contacto.
	 * @param telefono
	 * @return
	 */
	private static boolean validaTelefono(Telefono telefono) {
		
		// Comprueba que los caracteres del telefono estén comprendidos entre 6 y 20
		if (telefono.getStringValue() != null &&
			telefono.getStringValue().length() >= 6 &&
			telefono.getStringValue().length() <= 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida la página web de una dirección.
	 * @param paginaWeb
	 * @return
	 */
	private static boolean validaPaginaWeb(String paginaWeb) {
		
		// Se comprueba que los caracteres de la pagina web estén comprendidos entre 5 y 300
		if (paginaWeb.length() >= 5 && paginaWeb.length() <= 300) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida la población de una sociedad.
	 * @param poblacion
	 * @return
	 */
	private static boolean validaPoblacion(Poblacion poblacion) {
		
		// Comprueba que el código de población sea válido
		if (poblacion.getCodigoPoblacion() != null &&
			poblacion.getCodigoPoblacion().length() != 11) {
			return false;
		}
		
		// Comprueba que la descripción de la población sea válida
		if (poblacion.getDescripcionPoblacion() != null &&
			poblacion.getDescripcionPoblacion().length() > 100) {
			return false;
		}
		
		// Comprueba que la descripción sea válida
		if (poblacion.getDescripcion() != null &&
			!validaDescripcion(poblacion.getDescripcion())) {
			return false;
		}
		return true;
	}

	/**
	 * Valida una descripción comprendida entre 1 y 100 caracteres
	 * @param descripcion
	 * @return
	 */
	private static boolean validaDescripcion(String descripcion) {
		
		// Se comprueba la descripción
		if (descripcion.length() >= 1 && descripcion.length() <= 100) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida la provincia de una sociedad.
	 * @param provincia
	 * @return
	 */
	private static boolean validaProvincia(Provincia provincia) {
		
		// Comprueba que el codigo de provincia sean 2 caracteres
		if (provincia.getCodigoProvincia() == null ||
			provincia.getCodigoProvincia().length() != 2) {
			return false;
		}
		
		// Comprueba que la descripción de provincia no tenga más de 100 caracteres
		if (provincia.getDescripcionProvincia() != null &&
			provincia.getDescripcionProvincia().length() > 100) {
			return false;
		}
		
		return true;
	}

	/**
	 * Valida el domicilio de una dirección.
	 * @param domicilio
	 * @return
	 */
	private static boolean validaDomicilio(String domicilio) {
		// Se comprueba que los caracteres del domicilio estén comprendidos entre 3 y 100
		if (domicilio.length() >= 3 && domicilio.length() <= 100) {
			return true;
		} else {
			return false;
		}
	}

}
