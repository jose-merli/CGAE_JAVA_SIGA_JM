package org.itcgae.siga.DTOs.scs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

public class CalendarioAutomatico {

	//////////////////// ATRIBUTOS ////////////////////
	/**
	 * Este ArrayList tiene la misma estructura que el del CGAE y contiene los dias
	 * posibles para esa guardia calculado por el metodo del CGAE
	 */
	private ArrayList arrayDiasGuardiaCGAE;

	/**
	 * Este es el Calendario con los atributos propios de SJCS necesario para llamar
	 * al metodo del CGAE que calcula los dias posibles de una guardia
	 */
	// private CalendarioSJCS calendarioSJCS;

	// Datos necesatios para la clase del CGAE que genera
	// la lista de guardias posibles
	private String fechaInicio;
	private String fechaFin;
	private int duracion;
	private int unidadesDuracion;
	private int periodo;
	private int unidadesPeriodo;
	private Vector seleccionLaborables = null;
	private Vector seleccionFestivos = null;
	private List<String> listaFestivos = null;

	////////////////////CONSTRUCTORES ////////////////////
	public CalendarioAutomatico(GuardiasTurnoItem beanGuardiasTurno, String fInicio, String fFin,
			List<String> vDiasFestivos) {
		//variables
		String semana;

		//FECHAS:
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			this.fechaInicio = fInicio;
			this.fechaFin = fFin;
		} catch (Exception e) {
			this.fechaInicio = "";
			this.fechaFin = "";
		}

		//DURACION:
		this.duracion = beanGuardiasTurno.getDiasGuardia().intValue();
		this.unidadesDuracion = this.convertirUnidadesDuracion(beanGuardiasTurno.getTipodiasGuardia());

		//PERIODO:
		if (beanGuardiasTurno.getDiasPeriodo() != null)
			this.periodo = beanGuardiasTurno.getDiasPeriodo().intValue();
		else
			this.periodo = 0;
		this.unidadesPeriodo = this.convertirUnidadesDuracion(beanGuardiasTurno.getTipoDiasPeriodo());

		//Seleccion de laborables:
		this.seleccionLaborables = new Vector();
		semana = beanGuardiasTurno.getSeleccionLaborables();

		if (semana != null) {
			for (int i = 0; i < semana.length(); i++)
				this.seleccionLaborables
						.add(new Integer(CalendarioAutomatico.convertirUnidadesDiasSemana(semana.charAt(i))));
		}

		//Seleccion de festivos:
		this.seleccionFestivos = new Vector();
		if (beanGuardiasTurno.getSeleccionFestivos() != null) {
			semana = beanGuardiasTurno.getSeleccionFestivos();

			if (semana != null) {
				for (int i = 0; i < semana.length(); i++)
					this.seleccionFestivos
							.add(new Integer(CalendarioAutomatico.convertirUnidadesDiasSemana(semana.charAt(i))));
			}
		}

		//FESTIVOS:
		this.listaFestivos = vDiasFestivos;
	} // CalendarioAutomatico ()

	//////////////////// METODOS ////////////////////
	/**
	 * Convierte de nuestro formato para el combo de tipo de dias de guardia
	 * (Duracion) al usado por el CGAE
	 */
	private int convertirUnidadesDuracion(String tipoDiasGuardia) {
		int unidades = 0;

		try {
			switch (tipoDiasGuardia.charAt(0)) {
			case 'D':
				unidades = Calendar.DAY_OF_YEAR;
				break;// 6
			case 'S':
				unidades = Calendar.WEEK_OF_YEAR;
				break;// 3
			case 'Q':
				unidades = CalendarioEfectivo.QUINCENA;
				break;// 1524
			case 'M':
				unidades = Calendar.MONTH;
				break;// 2
			}
		} catch (Exception e) {
			unidades = 0;
		}
		return unidades;
	}

	/**
	 * Convierte de nuestro formato para los checkBox de los dias seleccionados del
	 * campo SEMANA en los usados por el CGAE.
	 */
	public static int convertirUnidadesDiasSemana(char dia) {
		int unidades = 0;

		try {
			switch (dia) {
			case 'D':
				unidades = Calendar.SUNDAY;
				break;// 1
			case 'L':
				unidades = Calendar.MONDAY;
				break;// 2
			case 'M':
				unidades = Calendar.TUESDAY;
				break;// 3
			case 'X':
				unidades = Calendar.WEDNESDAY;
				break;// 4
			case 'J':
				unidades = Calendar.THURSDAY;
				break;// 5
			case 'V':
				unidades = Calendar.FRIDAY;
				break;// 6
			case 'S':
				unidades = Calendar.SATURDAY;
				break;// 7
			}
		} catch (Exception e) {
			unidades = 1;
		}
		return unidades;
	}

	/**
	 * Metodo para obtener una matriz con las guardias disponibles segun las
	 * condiciones de la guardia.
	 */
	public ArrayList obtenerMatrizDiasGuardia() throws Exception {
		CalendarioEfectivo calendarioCGAE;
		ArrayList arrayPeriodosDiasGuardia = new ArrayList();

		try {
			//Imprimo para ver los datos de la llamada:
			this.imprimirDatosCalendario();

			//Instancio el Calendario Efectivo del CGAE que calcula 
			//los dias disponibles para hacer guardias:
			calendarioCGAE = new CalendarioEfectivo(fechaInicio, fechaFin, duracion, unidadesDuracion, periodo,
					unidadesPeriodo, seleccionLaborables, seleccionFestivos, listaFestivos);

			//Carga en la lista de salida de los periodos efectivos
			arrayPeriodosDiasGuardia = calendarioCGAE.getListaPeriodos();
		} catch (Exception e) {
			throw new Exception(e + ": Excepcion en obtenerMatrizDiasGuardia()");
		}
		return arrayPeriodosDiasGuardia;
	}

	/** Muestra en el LOG la configuracion del Calendario */
	private void imprimirDatosCalendario() {
	//ClsLogging.writeFileLog("*** DATOS DEL CALENDARIO:",10);
	//ClsLogging.writeFileLog("> FECHA INICIO:"+this.fechaInicio,10);
	//ClsLogging.writeFileLog("> FECHA FIN:"+this.fechaFin,10);		
	//ClsLogging.writeFileLog("> DURACION:"+this.duracion,10);
	//ClsLogging.writeFileLog("> UNIDADES DURACION:"+this.unidadesDuracion,10);
	//ClsLogging.writeFileLog("> PERIODO:"+this.periodo,10);
	//ClsLogging.writeFileLog("> UNIDADES PERIODO:"+this.unidadesPeriodo,10);
	//ClsLogging.writeFileLog("> SELECCION LABORABLES:"+this.seleccionLaborables.toString(),10);
	//ClsLogging.writeFileLog("> SELECCION FESTIVOS:"+this.seleccionFestivos.toString(),10);
	//ClsLogging.writeFileLog("***",10);
	} // imprimirDatosCalendario ()

	public ArrayList getArrayDiasGuardiaCGAE() {
		return arrayDiasGuardiaCGAE;
	}

	public void setArrayDiasGuardiaCGAE(ArrayList arrayDiasGuardiaCGAE) {
		this.arrayDiasGuardiaCGAE = arrayDiasGuardiaCGAE;
	}

	//public CalendarioSJCS getCalendarioSJCS() {
	//	return calendarioSJCS;
	//}
	//
	//
	//public void setCalendarioSJCS(CalendarioSJCS calendarioSJCS) {
	//	this.calendarioSJCS = calendarioSJCS;
	//}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int getUnidadesDuracion() {
		return unidadesDuracion;
	}

	public void setUnidadesDuracion(int unidadesDuracion) {
		this.unidadesDuracion = unidadesDuracion;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getUnidadesPeriodo() {
		return unidadesPeriodo;
	}

	public void setUnidadesPeriodo(int unidadesPeriodo) {
		this.unidadesPeriodo = unidadesPeriodo;
	}

	public Vector getSeleccionLaborables() {
		return seleccionLaborables;
	}

	public void setSeleccionLaborables(Vector seleccionLaborables) {
		this.seleccionLaborables = seleccionLaborables;
	}

	public Vector getSeleccionFestivos() {
		return seleccionFestivos;
	}

	public void setSeleccionFestivos(Vector seleccionFestivos) {
		this.seleccionFestivos = seleccionFestivos;
	}

	public List<String> getListaFestivos() {
		return listaFestivos;
	}

	public void setListaFestivos(List<String> listaFestivos) {
		this.listaFestivos = listaFestivos;
	}

}
