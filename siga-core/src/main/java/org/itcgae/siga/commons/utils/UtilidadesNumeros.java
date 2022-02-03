package org.itcgae.siga.commons.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilidadesNumeros {
	
	static public String redondea (String numero, int precision) {
	    double d =  Double.parseDouble(numero);
	    return String.valueOf(redondea(d,precision));
	}
	
	static public double redondea (double numero, int precision) {
		
		if (Double.isNaN(numero)) // Contolo NaN 
			return 0.0;
		
		// Calcula el signo
		BigDecimal bdSigno = new BigDecimal("1");
		if (numero<0) {
			bdSigno = new BigDecimal("-1");
		}
				
		// Calcula la precision
		BigDecimal bdPrecision = new BigDecimal("1");
		for (int i=0; i<precision; i++) {
			bdPrecision = bdPrecision.multiply(new BigDecimal("10"));
		}
		
		BigDecimal bCalculo = BigDecimal.valueOf(numero); // Conversion double to BigDecimal
		
		bCalculo = bCalculo.multiply(bdSigno); // Control inicial del signo
		
		bCalculo = bCalculo.multiply(bdPrecision); // Pone la parte decimal dentro de la precision como entero
		
		bCalculo = bCalculo.add(new BigDecimal("0.5")); // Sumo 0.5
		
		RoundingMode RM = RoundingMode.DOWN;
		bCalculo = bCalculo.setScale(0, RM); // Obtengo la parte entera		
		//bCalculo = BigDecimal.valueOf(bCalculo.intValue()); 
		
		bCalculo = bCalculo.divide(bdPrecision); // Vuelvo a poner la parte decimal
		
		bCalculo = bCalculo.multiply(bdSigno); // Control final del signo
		
		return bCalculo.doubleValue(); 
	}	
	 
	static public float redondea (float numero, int precision) {
	    double d = numero;
	    return (float) redondea(d, precision);
	}
	
}