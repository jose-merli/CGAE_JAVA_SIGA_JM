package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class InformeFacturacionItem {

	String momento;
	String formaPago;
	String numeroFacturas;
	String total;
	String totalPendiente;
}