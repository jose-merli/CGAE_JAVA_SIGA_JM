package org.itcgae.siga.db.services.fac.providers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.FiltrosSuscripcionesItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionItem;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionSqlProvider;

public class PysPeticioncomprasuscripcionSqlExtendsProvider extends PysPeticioncomprasuscripcionSqlProvider {

	public String getFichaCompraSuscripcion(FichaCompraSuscripcionItem peticion, boolean esColegiado, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("pet.idinstitucion");
		sql.SELECT("per.idpersona");
		// TARJETA CLIENTE
		sql.SELECT("per.nombre");
		sql.SELECT("concat(per.apellidos1,concat(' ',per.apellidos2)) as apellidos");
		sql.SELECT("per.nifcif");
		sql.SELECT("per.IDTIPOIDENTIFICACION");
		// TARJETA SOLICITUD
		sql.SELECT("pet.idpeticion");
		sql.SELECT("pet.idestadopeticion");// Para determinar si es una fecha de solicitud, anulacion o denegacion.
											// Determinar el equivalente de sus valores numericos.
		sql.SELECT("usuario.descripcion as usuModificacion");
		
		sql.SELECT("pet.fecha as fechaPendiente"); // Esta fecha se utiliza de fecha de solicitud
		
		//Para obtener el historico de la peticion
		if(peticion.getProductos() != null) {
			sql.SELECT("CASE WHEN compra.fecha is null THEN petBaja.fecha \r\n"
					+ "ELSE null END as fechaDenegada \r\n");
			sql.SELECT("CASE WHEN compra.fecha is not null THEN petBaja.fecha \r\n"
					+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
			sql.SELECT("compra.fecha as fechaAceptada");
			sql.SELECT("compra.fechaBaja as fechaAnulada");
			sql.LEFT_OUTER_JOIN("PYS_COMPRA compra on compra.idpeticion = pet.idpeticion and compra.idinstitucion = pet.idinstitucion");
		}
		else {
			sql.SELECT("CASE WHEN suscripcion.fechasuscripcion is null THEN petBaja.fecha \r\n"
					+ "ELSE null END as fechaDenegada \r\n");
			sql.SELECT("CASE WHEN suscripcion.fechasuscripcion is not null THEN petBaja.fecha \r\n"
					+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
			sql.SELECT("suscripcion.fechasuscripcion as fechaAceptada");
			sql.SELECT("suscripcion.fechaBaja as fechaAnulada");
			sql.LEFT_OUTER_JOIN("PYS_suscripcion suscripcion on suscripcion.idpeticion = pet.idpeticion and suscripcion.idinstitucion = pet.idinstitucion");

		}
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta=pet.idpeticion");

		
		sql.FROM("pys_peticioncomprasuscripcion pet");

		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.INNER_JOIN(
				"adm_usuarios usuario ON (pet.usumodificacion = usuario.idusuario and pet.idinstitucion = usuario.idinstitucion)");

		sql.WHERE("pet.idinstitucion = " + idInstitucion);
		sql.WHERE("pet.idpeticion = " + peticion.getnSolicitud());

		// Para obtener las formas de pago comunes entre los productos de la compra. Se
		// devuelven los idformapago concatenados con comas para se gestion.
		if (peticion.getProductos() != null && peticion.getProductos().size() > 0) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT(
					"LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinProductos = "pys_productosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaProductosCompraItem producto : peticion.getProductos()) {
				fromPagosComunes += "select pago.idformapago\r\n"
						+ "				from pys_productosinstitucion prod\r\n"
						+ "				inner join pys_formapagoproducto pago on pago.idinstitucion = prod.idinstitucion and prod.idproducto = pago.idproducto \r\n"
						+ "				and pago.idtipoproducto = prod.idtipoproducto AND prod.IDPRODUCTOINSTITUCION = pago.IDPRODUCTOINSTITUCION\r\n"
						+ "				where prod.idinstitucion = " + peticion.getIdInstitucion();
						//Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no 
						if(esColegiado)fromPagosComunes += " and pago.internet = 'A'";
						else fromPagosComunes += " and pago.internet = 'S'";
						fromPagosComunes += " and (prod.idproducto = " + producto.getIdproducto() + " and prod.idtipoproducto="
						+ producto.getIdtipoproducto() + " and prod.idproductoinstitucion="
						+ producto.getIdproductoinstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";
				
				innerJoinProductos += "(prin.idproducto = "+producto.getIdproducto()+" and prin.idtipoproducto="+producto.getIdtipoproducto()+
						" and prin.idproductoinstitucion="+producto.getIdproductoinstitucion()+") OR";
				
			}
			//Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinProductos.substring(0, innerJoinProductos.length() - 2) + ")");
			
			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");
			
			//Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(prodSol.idformapago) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idFormaPagoSeleccionada");
			//Obtenemos la cuenta bancaria
			sql.SELECT("FIRST_VALUE(prodSol.idcuenta) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idCuentaBancSeleccionada");
			
				
			sql.INNER_JOIN("pys_productossolicitados prodSol on prodSol.idinstitucion = pet.idinstitucion and prodSol.idpeticion = pet.idpeticion");
			
			
			//Obtención de importes de productos
			sql.SELECT("F_siga_formatonumero(PRIN.VALOR,2) AS totalNeto");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100), 2),2) as totalIVA");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS impTotal");
				//Importe total menos Anticipos (pysanticiposletrado) menos Suma de lo pagado en las facturas (fac_factura) asociadas
				sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR - "
						+ "COALESCE((SELECT SUM(importeInicial)\r\n"
						+ "FROM pys_anticipoletrado "
						+ "where idpersona = "+peticion.getIdPersona()+"),0) - "
								+ "COALESCE((SELECT SUM(fact.IMPTOTALPAGADO)\r\n"
								+ "FROM fac_factura fact\r\n"
								+ "INNER JOIN pys_compra on pys_compra.idfactura = fact.idfactura and pys_compra.idpeticion = "+peticion.getnSolicitud()+" and pys_compra.idinstitucion = fact.idinstitucion\r\n"
								+ "WHERE fact.idinstitucion = "+peticion.getIdInstitucion()+"),0), 2),2) AS pendPago");
				//Solo se puden comprobar las facturas en el caso que haya habido un registro de compra.
				//Pendiente de optimizacion por SQL
				if(peticion.getFechaAceptada() != null) {
				sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compra.IDFACTURA");
				}
			
			else sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS pendPago");
			

			sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prodSol.idtipoiva");
		}
		else if (peticion.getServicios() != null && peticion.getServicios().size() > 0){
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT(
					"LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinServicios = "pys_serviciosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaServiciosSuscripcionItem servicio : peticion.getServicios()) {
				fromPagosComunes += "select pago.idformapago\r\n"
						+ "				from pys_serviciosinstitucion serv\r\n"
						+ "				inner join pys_formapagoservicio pago on pago.idinstitucion = prod.idinstitucion and prod.idservicio = pago.idservicio \r\n"
						+ "				and pago.idtiposervicio = prod.idtiposervicio AND prod.IDservicioINSTITUCION = pago.IDservicioINSTITUCION\r\n"
						+ "				where prod.idinstitucion = " + peticion.getIdInstitucion();
						//Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no 
						if(esColegiado)fromPagosComunes += " and pago.internet = 'A'";
						else fromPagosComunes += " and pago.internet = 'S'";
						fromPagosComunes += " and (serv.idservicio = " + servicio.getIdServicio() + " and prod.idtiposervicio="
						+ servicio.getIdTipoServicio() + " and prod.idservicioinstitucion="
						+ servicio.getIdServicioInstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";
				
				innerJoinServicios += "(servIns.idservicio = "+servicio.getIdServicio()+" and prin.idtiposervicio="+servicio.getIdTipoServicio()+
						" and servIns.idservicioinstitucion="+servicio.getIdServicioInstitucion()+") OR";
				
			}
			//Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinServicios.substring(0, innerJoinServicios.length() - 2) + ")");
			
			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");
			
			//Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(servSol.idformapago) OVER (ORDER BY servSol.FECHARECEPCIONSOLICITUD) as idFormaPagoSeleccionada");
			//Obtenemos la cuenta bancaria
			sql.SELECT("FIRST_VALUE(servSol.idcuenta) OVER (ORDER BY servSol.FECHARECEPCIONSOLICITUD) as idCuentaBancSeleccionada");
			
				
			sql.INNER_JOIN("pys_serviciossolicitados servSol on servSol.idinstitucion = pet.idinstitucion and servSol.idpeticion = pet.idpeticion");
		}
		
		//REVISAR
		sql.WHERE("rownum = 1");

		return sql.toString();
	}

	public String getNuevaFichaCompraSuscripcion(FichaCompraSuscripcionItem peticion, boolean esColegiado) {
		SQL sql = new SQL();

		// TARJETA CLIENTE
		if (peticion.getIdPersona() != null) {
			sql.SELECT("per.idpersona");
			sql.SELECT("per.nombre");
			sql.SELECT("concat(per.apellidos1,concat(' ',per.apellidos2)) as apellidos");
			sql.SELECT("per.nifcif");
			sql.SELECT("per.IDTIPOIDENTIFICACION");

			sql.INNER_JOIN("cen_persona per on per.idpersona = " + peticion.getIdPersona());
		}
		else {
			sql.SELECT("null as idpersona");
			sql.SELECT("null as nombre");
			sql.SELECT("null as apellidos");
			sql.SELECT("null as nifcif");
			sql.SELECT("null as IDTIPOIDENTIFICACION");
		}
		sql.SELECT(peticion.getIdInstitucion()+" as idInstitucion");
				
		//TRAJETA SOLICITUD
		sql.SELECT("(\r\n" + "        SELECT\r\n" + "            MAX(pet.idpeticion)\r\n" + "        FROM\r\n"
				+ "            pys_peticioncomprasuscripcion pet\r\n" + "where pet.idinstitucion = "+peticion.getIdInstitucion()+"    ) + 1 AS idpeticion");
		
		sql.SELECT("usuario.descripcion as usuModificacion");

		sql.FROM("pys_peticioncomprasuscripcion pet");

		sql.WHERE("pet.idinstitucion = " + peticion.getIdInstitucion());
		sql.INNER_JOIN("adm_usuarios usuario ON (usuario.idusuario = " + peticion.getUsuModificacion()
				+ " and usuario.idinstitucion = " + peticion.getIdInstitucion() + ")");
		

		

		// Para obtener las formas de pago comunes entre los productos de la compra. Se
		// devuelven los idformapago concatenados con comas para su gestion.
		if (peticion.getProductos() != null && peticion.getProductos().size() > 0) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT(
					"LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinProductos = "pys_productosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaProductosCompraItem producto : peticion.getProductos()) {
				fromPagosComunes += "select pago.idformapago\r\n"
						+ "				from pys_productosinstitucion prod\r\n"
						+ "				inner join pys_formapagoproducto pago on pago.idinstitucion = prod.idinstitucion and prod.idproducto = pago.idproducto \r\n"
						+ "				and pago.idtipoproducto = prod.idtipoproducto AND prod.IDPRODUCTOINSTITUCION = pago.IDPRODUCTOINSTITUCION\r\n"
						+ "				where prod.idinstitucion = " + peticion.getIdInstitucion();
						//Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no 
						if(esColegiado)fromPagosComunes += " and pago.internet = 'A'";
						else fromPagosComunes += " and pago.internet = 'S'";
						fromPagosComunes += " and (prod.idproducto = " + producto.getIdproducto() + " and prod.idtipoproducto="
						+ producto.getIdtipoproducto() + " and prod.idproductoinstitucion="
						+ producto.getIdproductoinstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";
				
				innerJoinProductos += "(prin.idproducto = "+producto.getIdproducto()+" and prin.idtipoproducto="+producto.getIdtipoproducto()+
						" and prin.idproductoinstitucion="+producto.getIdproductoinstitucion()+") OR";
				
			}
			//Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinProductos.substring(0, innerJoinProductos.length() - 2) + ")");
			
			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");
			
			//Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(prodSol.idformapago) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idFormaPagoSeleccionada");
				
			sql.LEFT_OUTER_JOIN("pys_productossolicitados prodSol on prodSol.idinstitucion = pet.idinstitucion and prodSol.idpeticion = pet.idpeticion");
			
			
			//Obtención de importes de productos
			sql.SELECT("F_siga_formatonumero(PRIN.VALOR,2) AS totalNeto");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100), 2),2) as totalIVA");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS impTotal");
			if(peticion.getIdPersona() != null) {
				//Importe total menos Anticipos (pysanticiposletrado) menos Suma de lo pagado en las facturas (fac_factura) asociadas
				sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR - "
						+ "COALESCE((SELECT SUM(importeInicial)\r\n"
						+ "FROM pys_anticipoletrado "
						+ "where idpersona = "+peticion.getIdPersona()+"),0) - "
								+ "COALESCE((SELECT SUM(fact.IMPTOTALPAGADO)\r\n"
								+ "FROM fac_factura fact\r\n"
								+ "INNER JOIN pys_compra on pys_compra.idfactura = fact.idfactura and pys_compra.idpeticion = "+peticion.getnSolicitud()+" and pys_compra.idinstitucion = fact.idinstitucion\r\n"
								+ "WHERE fact.idinstitucion = "+peticion.getIdInstitucion()+"),0), 2),2) AS pendPago");
				//Solo se puden comprobar las facturas en el caso que haya habido un registro de compra.
				//Pendiente de optimizacion por SQL
				if(peticion.getFechaAceptada() != null) {
				sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compras.IDFACTURA");
				}
			}
			else sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS pendPago");
			

			sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prin.idtipoiva");
		}
		else if (peticion.getServicios() != null && peticion.getServicios().size() > 0){
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT(
					"LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinServicios = "pys_serviciosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaServiciosSuscripcionItem servicio : peticion.getServicios()) {
				fromPagosComunes += "select pago.idformapago\r\n"
						+ "				from pys_serviciosinstitucion serv\r\n"
						+ "				inner join pys_formapagoservicio pago on pago.idinstitucion = prod.idinstitucion and prod.idservicio = pago.idservicio \r\n"
						+ "				and pago.idtiposervicio = prod.idtiposervicio AND prod.IDservicioINSTITUCION = pago.IDservicioINSTITUCION\r\n"
						+ "				where prod.idinstitucion = " + peticion.getIdInstitucion();
						//Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no 
						if(esColegiado)fromPagosComunes += " and pago.internet = 'A'";
						else fromPagosComunes += " and pago.internet = 'S'";
						fromPagosComunes += " and (serv.idservicio = " + servicio.getIdServicio() + " and prod.idtiposervicio="
						+ servicio.getIdTipoServicio() + " and prod.idservicioinstitucion="
						+ servicio.getIdServicioInstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";
				
				innerJoinServicios += "(servIns.idservicio = "+servicio.getIdServicio()+" and prin.idtiposervicio="+servicio.getIdTipoServicio()+
						" and servIns.idservicioinstitucion="+servicio.getIdServicioInstitucion()+") OR";
				
			}
			//Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinServicios.substring(0, innerJoinServicios.length() - 2) + ")");
			
			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");
			
			//Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(servSol.idformapago) OVER (ORDER BY servSol.FECHARECEPCIONSOLICITUD) as idFormaPagoSeleccionada");
			//Obtenemos la cuenta bancaria
			sql.SELECT("FIRST_VALUE(servSol.idcuenta) OVER (ORDER BY servSol.FECHARECEPCIONSOLICITUD) as idCuentaBancSeleccionada");
			
				
			sql.INNER_JOIN("pys_serviciossolicitados servSol on servSol.idinstitucion = pet.idinstitucion and servSol.idpeticion = pet.idpeticion");
		}
		
		//REVISAR
		sql.WHERE("rownum = 1");

		return sql.toString();
	}

	public String selectMaxIdPeticion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDPETICION) +1, 1) AS IDPETICION");
		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION");
		sql.WHERE("idInstitucion =" + idInstitucion);

		return sql.toString();
	}

	public String selectMaxNumOperacion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(NUM_OPERACION) +1, 0) AS NUM_OPERACION");
		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION");
		sql.WHERE("idInstitucion =" + idInstitucion);

		return sql.toString();
	}
	
	public String getListaCompras(FiltrosCompraProductosItem filtro, Short idInstitucion, String idioma) throws ParseException {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("pet.fecha as fechaSolicitud");
		sql.SELECT_DISTINCT("pet.idPeticion as nSolicitud");
		sql.SELECT_DISTINCT("per.nifcif as nIdentificacion");
		sql.SELECT_DISTINCT("col.NCOLEGIADO \r\n");
		sql.SELECT_DISTINCT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as apellidosnombre \r\n");
		//REVISAR COUNT que siempre devuelve 200
		sql.SELECT_DISTINCT("CASE WHEN COUNT(1) OVER (ORDER BY prodIns.descripcion) >1 THEN FIRST_VALUE(prodIns.descripcion) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) || '...'\r\n"
				+ "ELSE FIRST_VALUE(prodIns.descripcion) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) END as concepto \r\n");
		sql.SELECT_DISTINCT("prodSol.idformapago as idformapago \r\n");
		sql.SELECT_DISTINCT("CASE WHEN prodSol.noFacturable = '1' THEN 'No facturable'\r\n"
				+ "ELSE f_siga_getrecurso(formPago.descripcion, "+ idioma +") END as desFormaPago");
//		sql.SELECT_DISTINCT("(prodSol.VALOR*prodSol.cantidad)*(1+TIVA.VALOR/100) AS impTotal \r\n");
		
		sql.SELECT_DISTINCT("CASE WHEN compra.fecha is null THEN petBaja.fecha \r\n"
				+ "ELSE null END as fechaDenegada \r\n");
		sql.SELECT_DISTINCT("CASE WHEN compra.fecha is not null THEN petBaja.fecha \r\n"
				+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
		sql.SELECT_DISTINCT("compra.fecha as fechaEfectiva");
		sql.SELECT_DISTINCT("compra.fechaBaja as fechaAnulada");
		sql.SELECT_DISTINCT("f_siga_getrecurso_etiqueta(estfact.DESCRIPCION,'" + idioma + "') AS estadoFactura");
		
		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION pet");
		
		sql.INNER_JOIN("PYS_productosSolicitados prodSol on prodSol.idinstitucion=pet.idInstitucion and prodSol.idpeticion=pet.idPeticion");
		sql.LEFT_OUTER_JOIN("pys_formapago formPago on formPago.idformapago = prodSol.idformapago");
		sql.LEFT_OUTER_JOIN("pys_compra compra on compra.idinstitucion = pet.idinstitucion and compra.idpeticion = pet.idpeticion");
		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.LEFT_OUTER_JOIN("cen_colegiado col on col.idpersona = pet.idpersona and col.idinstitucion = pet.idinstitucion");
		sql.INNER_JOIN("pys_productosinstitucion prodIns on prodIns.idinstitucion = prodSol.idinstitucion and prodIns.idproducto = prodSol.idProducto \r\n"
				+ "and prodIns.idTipoProducto = prodSol.idTipoProducto and prodIns.idProductoInstitucion = prodSol.idProductoInstitucion");
		sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prodSol.idtipoiva");
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta = pet.idpeticion");
		sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compra.idfactura and fact.idinstitucion = compra.idinstitucion");
		sql.LEFT_OUTER_JOIN("fac_estadoFactura estFact on estFact.idestado = fact.estado");
		
		sql.WHERE("pet.idinstitucion = "+idInstitucion.toString());
		sql.WHERE("rownum <= 200");
		
		if(filtro.getIdEstadoSolicitud() != null) {
			switch(filtro.getIdEstadoSolicitud()) {
				case "1":
					sql.WHERE("CASE WHEN compra.fecha is null THEN petBaja.fecha\r\n"
							+ "				ELSE null END is null and compra.fecha is null");
					break;
				case "2":
					sql.WHERE("CASE WHEN compra.fecha is null THEN petBaja.fecha \r\n"
							+ "				ELSE null END is not null");
					break;
				case "3":
					sql.WHERE("compra.fecha is not null and CASE WHEN compra.fecha is not null THEN petBaja.fecha \r\n"
							+ "				ELSE null END is null");
					break;
				case "4":
					sql.WHERE("CASE WHEN compra.fecha is not null THEN petBaja.fecha \r\n"
							+ "				ELSE null END is not null and compra.fechaBaja is null");
					break;
				case "5":
					sql.WHERE("compra.fechaBaja is not null");
					break;
			}
		}
		
		if(filtro.getIdpersona() != null)sql.WHERE("pet.idpersona = "+filtro.getIdpersona());
		
		if(filtro.getnSolicitud() != null && filtro.getnSolicitud().trim() != "")sql.WHERE("pet.idpeticion like '%"+filtro.getnSolicitud()+"%'");
		
		if(filtro.getDescProd() != null && filtro.getDescProd().trim() != "")sql.WHERE("convert(UPPER(prodIns.descripcion) , 'US7ASCII' ) like convert(UPPER('%"+filtro.getDescProd()+"%') , 'US7ASCII' )");
		
		
		if(filtro.getFechaSolicitudDesde() != null) {
			DateFormat dateFormatFront = new SimpleDateFormat(
		            "EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
			String strDate = dateFormatSql.format(dateFormatFront.parse(filtro.getFechaSolicitudDesde().toString()).getTime());
			sql.WHERE("pet.fecha >= to_date('"+strDate+"','dd/MM/YY')");
		}

		if(filtro.getFechaSolicitudHasta() != null) {
			DateFormat dateFormatFront = new SimpleDateFormat(
		            "EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
			String strDate = dateFormatSql.format(dateFormatFront.parse(filtro.getFechaSolicitudHasta().toString()).getTime());
			sql.WHERE("pet.fecha <= to_date('"+strDate+"','dd/MM/YY')");
		}
			
		if(filtro.getIdTipoProducto() != null)sql.WHERE("prodSol.idProductoInstitucion = "+filtro.getIdTipoProducto());

		if(filtro.getIdCategoria() != null)sql.WHERE("prodSol.idTipoProducto = "+filtro.getIdCategoria());
		
		if(filtro.getIdEstadoFactura() != null)sql.WHERE("fact.estado = "+filtro.getIdEstadoFactura());
//		private String importe; // valor aplicado durante la compra (importe total)
		
		return sql.toString();
		
	}
	
	public String comboEstadoFactura(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDestado AS ID");
		sql.SELECT("f_siga_getrecurso_etiqueta(DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("fac_estadoFactura");
		
		return sql.toString();
	}
	
	public String getListaProductosCompra(Short idInstitucion, String idPeticion) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT(" PRodSol.observaciones");
		sql.SELECT_DISTINCT(" case when PRodSol.orden is null then rownum \r\n"
				+ "else prodSol.orden end as orden");
		sql.SELECT_DISTINCT(" PRin.descripcion");
		sql.SELECT_DISTINCT(" PRodSol.cantidad");
		sql.SELECT_DISTINCT(" PRodSol.valor");
		sql.SELECT_DISTINCT(" PRodSol.idtipoiva as idtipoiva");
		sql.SELECT_DISTINCT("TIVA.descripcion as IVA");
		sql.SELECT_DISTINCT("TIVA.valor as valorIva");
//		sql.SELECT_DISTINCT("F_siga_formatonumero(ROUND(prin.VALOR*TIVA.VALOR/100)+prin.VALOR, 2) AS total \r\n");
		sql.SELECT_DISTINCT("PRodSol.idproducto");
		sql.SELECT_DISTINCT("PRodSol.idtipoproducto");
		sql.SELECT_DISTINCT("PRodSol.idproductoinstitucion");
		sql.SELECT_DISTINCT("PRodSol.idpeticion");
		sql.SELECT_DISTINCT("ProdSol.NOFACTURABLE");
		sql.SELECT_DISTINCT("Prin.solicitarBaja"); //Este atributo hace referencia a la propiedad/check "Solictar baja por internet"
		
		
		
		sql.FROM(" pys_productosinstitucion prin, pys_tipoiva tiva");
		sql.FROM("pys_productossolicitados prodSol");
		
		sql.WHERE(" PRIN.IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE(" tiva.idtipoiva (+) = prodSol.idtipoiva");
		sql.WHERE(" prodSol.idinstitucion(+) = prin.idinstitucion");
		sql.WHERE("prodSol.idpeticion = "+idPeticion);
		sql.WHERE("prodSol.idproducto(+) = prin.idproducto and prodSol.idtipoproducto(+) = prin.idtipoproducto and prodSol.idproductoinstitucion(+) = prin.idproductoinstitucion");

		
		sql.ORDER_BY(" PRIN.DESCRIPCION");
		
		return sql.toString();
	}

	public String getListaSuscripciones(FiltrosSuscripcionesItem filtro, Short idInstitucion, String idioma)
			throws ParseException {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("pet.fecha as fechaSolicitud");
		sql.SELECT_DISTINCT("pet.idPeticion as nSolicitud");
		sql.SELECT_DISTINCT("per.nifcif as nIdentificacion");
		sql.SELECT_DISTINCT("col.NCOLEGIADO \r\n");
		sql.SELECT_DISTINCT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as apellidosnombre \r\n");
		//REVISAR COUNT que siempre devuelve 200
//		sql.SELECT_DISTINCT(
//				"CASE WHEN COUNT(1) OVER (ORDER BY servIns.descripcion) >1 THEN FIRST_VALUE(servIns.descripcion) OVER (ORDER BY suscripcion.fechasuscripcion) || '...'\r\n"
//						+ "ELSE FIRST_VALUE(servIns.descripcion) OVER (ORDER BY suscripcion.fechasuscripcion) || '[ ' || precioServ.descripcion || ' ]'  END as concepto \r\n");
//		sql.SELECT_DISTINCT("servIns.descripcion || '[ ' || precioServ.descripcion || ' ]' as concepto");
		sql.SELECT_DISTINCT("CASE WHEN suscripcion.fechabaja is not null or petBaja.fecha is null THEN servIns.descripcion || '[ ' || precioServ.descripcion || ' ]' "
				+ "ELSE servIns.descripcion END as concepto");
		sql.SELECT_DISTINCT("servSol.idformapago as idformapago \r\n");
		sql.SELECT_DISTINCT("CASE WHEN servIns.noFacturable = '1' THEN 'No facturable'\r\n"
				+ "ELSE f_siga_getrecurso(formPago.descripcion, " + idioma + ") END as desFormaPago");
//		sql.SELECT_DISTINCT("(servSol.VALOR*servSol.cantidad)*(1+TIVA.VALOR/100) AS impTotal \r\n");

		sql.SELECT_DISTINCT(
				"CASE WHEN suscripcion.fechasuscripcion is null THEN petBaja.fecha \r\n" + "ELSE null END as fechaDenegada \r\n");
		sql.SELECT_DISTINCT("CASE WHEN suscripcion.fechasuscripcion is not null THEN petBaja.fecha \r\n"
				+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
		sql.SELECT_DISTINCT("suscripcion.fechasuscripcion as fechaEfectiva");
		sql.SELECT_DISTINCT("suscripcion.fechaBaja as fechaAnulada");
		sql.SELECT_DISTINCT("f_siga_getrecurso_etiqueta(estfact.DESCRIPCION,'" + idioma + "') AS estadoFactura");
		sql.SELECT_DISTINCT("precioServ.valor || '(' || f_siga_getrecurso(periodicidad.descripcion, "+idioma+") || ')' as PrecioPerio");
		sql.SELECT_DISTINCT("suscripcion.fechasuscripcion as fechasuscripcion");
		sql.SELECT_DISTINCT("suscripcion.fechabaja as fechabaja");

		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION pet"); 

		sql.INNER_JOIN(
				"PYS_serviciosSolicitados servSol on servSol.idinstitucion=pet.idInstitucion and servSol.idpeticion=pet.idPeticion");
		sql.LEFT_OUTER_JOIN("pys_formapago formPago on formPago.idformapago = servSol.idformapago");
		sql.LEFT_OUTER_JOIN(
				"pys_suscripcion suscripcion on suscripcion.idinstitucion = pet.idinstitucion and suscripcion.idpeticion = pet.idpeticion");
		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.LEFT_OUTER_JOIN(
				"cen_colegiado col on col.idpersona = pet.idpersona and col.idinstitucion = pet.idinstitucion");
		sql.INNER_JOIN(
				"pys_serviciosinstitucion servIns on servIns.idinstitucion = servSol.idinstitucion and servIns.idservicio = servSol.idservicio \r\n"
						+ "and servIns.idTipoServicios = servSol.idTipoServicios and servIns.idServiciosInstitucion = servSol.idServiciosInstitucion");
		sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = servIns.idtipoiva");
		sql.LEFT_OUTER_JOIN("pys_preciosservicios precioServ on precioServ.idinstitucion = servSol.idinstitucion and precioServ.idservicio = servSol.idservicio \r\n"
				+ "and precioServ.idTipoServicios = servSol.idTipoServicios and precioServ.idServiciosInstitucion = servSol.idServiciosInstitucion "
				+ "and precioServ.idperiodicidad = servSol.idperiodicidad and precioServ.idPreciosServicios = servSol.idPreciosServicios");
		sql.LEFT_OUTER_JOIN("pys_periodicidad periodicidad on precioServ.idperiodicidad = periodicidad.idperiodicidad");
		sql.LEFT_OUTER_JOIN(
				"pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta = pet.idpeticion");
		sql.LEFT_OUTER_JOIN("fac_facturacionSuscripcion factSus on factSus.idservicio = suscripcion.idservicio \r\n"
				+ "and factSus.idTipoServicios = suscripcion.idTipoServicios and factSus.idServiciosInstitucion = suscripcion.idServiciosInstitucion "
				+ "and factSus.idinstitucion = suscripcion.idinstitucion and factSus.idsuscripcion = suscripcion.idpeticion");
		sql.LEFT_OUTER_JOIN("fac_facturacionSuscripcion factSusBis ON (factSusBis.idservicio = suscripcion.idservicio \r\n"
				+ "and factSusBis.idTipoServicios = suscripcion.idTipoServicios and factSusBis.idServiciosInstitucion = suscripcion.idServiciosInstitucion \r\n"
				+ "and factSusBis.idinstitucion = suscripcion.idinstitucion and factSusBis.idsuscripcion = suscripcion.idpeticion AND \r\n"
			   + "(factSus.idfactura < factSusBis.idfactura))");
		sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = factSus.idfactura \r\n"
				+ "and fact.idinstitucion = suscripcion.idinstitucion");
		
		sql.LEFT_OUTER_JOIN("fac_estadoFactura estFact on estFact.idestado = fact.estado");

		sql.WHERE("pet.idinstitucion = " + idInstitucion.toString());
		sql.WHERE("rownum <= 200");
		sql.WHERE("factSusBis.idfactura is null");

		if (filtro.getIdEstadoSolicitud() != null) {
			switch (filtro.getIdEstadoSolicitud()) {
			case "1": //Pendiente
				sql.WHERE("CASE WHEN suscripcion.fechaSuscripcion is null THEN petBaja.fecha\r\n"
						+ "				ELSE null END is null and suscripcion.fechaSuscripcion is null");
				break;
			case "2"://Denegada
				sql.WHERE("CASE WHEN suscripcion.fechaSuscripcion is null THEN petBaja.fecha \r\n"
						+ "				ELSE null END is not null");
				break;
			case "3"://Aceptada
				sql.WHERE(
						"suscripcion.fechaSuscripcion is not null and CASE WHEN suscripcion.fechaSuscripcion is not null THEN petBaja.fecha \r\n"
								+ "				ELSE null END is null");
				break;
			case "4"://Anulacion solicitada
				sql.WHERE("CASE WHEN suscripcion.fechaSuscripcion is not null THEN petBaja.fecha \r\n"
						+ "				ELSE null END is not null and suscripcion.fechaBaja is null");
				break;
			case "5"://Anulada
				sql.WHERE("suscripcion.fechaBaja is not null");
				break;
			}
		}

		if (filtro.getIdpersona() != null)
			sql.WHERE("pet.idpersona = " + filtro.getIdpersona());

		if (filtro.getnSolicitud() != null && filtro.getnSolicitud().trim() != "")
			sql.WHERE("pet.idpeticion like '%" + filtro.getnSolicitud() + "%'");

		if (filtro.getDescServ() != null && filtro.getDescServ().trim() != "")
			sql.WHERE("convert(UPPER(servIns.descripcion) , 'US7ASCII' ) like convert(UPPER('%" + filtro.getDescServ()
					+ "%') , 'US7ASCII' )");

		if(filtro.getaFechaDe() != null) {
			DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
			String strDate = dateFormatSql
					.format(dateFormatFront.parse(filtro.getaFechaDe().toString()).getTime());
			//Deben estar en estado "Aceptada" o "Pendiente de anulacion" en esa fecha
			sql.WHERE(
					"(suscripcion.fechaSuscripcion is not null and "
					+ "suscripcion.fechaSuscripcion <= to_date('" + strDate + "','dd/MM/YY') and"
					+ "(suscripcion.fechaBaja is null or suscripcion.fechaBaja > to_date('" + strDate + "','dd/MM/YY')))");
		}
		else{
			if (filtro.getFechaSolicitudDesde() != null) {
				DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
				DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
				String strDate = dateFormatSql
						.format(dateFormatFront.parse(filtro.getFechaSolicitudDesde().toString()).getTime());
				sql.WHERE("pet.fecha >= to_date('" + strDate + "','dd/MM/YY')");
			}
	
			if (filtro.getFechaSolicitudHasta() != null) {
				DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
				DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
				String strDate = dateFormatSql
						.format(dateFormatFront.parse(filtro.getFechaSolicitudHasta().toString()).getTime());
				sql.WHERE("pet.fecha <= to_date('" + strDate + "','dd/MM/YY')");
			}
		}

		if (filtro.getIdTipoServicio() != null) {
			sql.WHERE("servSol.idServiciosInstitucion = " + filtro.getIdTipoServicio());
		}

		if (filtro.getIdCategoria() != null) {
			sql.WHERE("servSol.idTipoServicios = " + filtro.getIdCategoria());
		}

		if(filtro.getIdEstadoFactura() != null) {
			sql.WHERE("fact.estado = "+filtro.getIdEstadoFactura());
		}
//		private String importe; // valor aplicado durante la compra (importe total)
		sql.WHERE("rownum <= 200");

		return sql.toString();
	}

	public String getListaServiciosSuscripcion(Short idInstitucion, String idPeticion, String idioma, Date aFechaDe) throws ParseException {

		SQL sql = new SQL();


		sql.SELECT_DISTINCT(" case when servSol.orden is null then rownum \r\n" + "else servSol.orden end as orden");
		sql.SELECT_DISTINCT(" servIns.descripcion");
		sql.SELECT_DISTINCT(" servSol.cantidad");
		sql.SELECT_DISTINCT(" precioServ.valor");
		sql.SELECT_DISTINCT(" servIns.idtipoiva as idtipoiva");
		sql.SELECT_DISTINCT("TIVA.descripcion as IVA");
		sql.SELECT_DISTINCT("TIVA.valor as valorIva");
//		sql.SELECT_DISTINCT("F_siga_formatonumero(ROUND(prin.VALOR*TIVA.VALOR/100)+prin.VALOR, 2) AS total \r\n");
		sql.SELECT_DISTINCT("servSol.idservicio");
		sql.SELECT_DISTINCT("servSol.idtiposervicios");
		sql.SELECT_DISTINCT("servSol.idserviciosinstitucion");
		sql.SELECT_DISTINCT("servSol.idpeticion");
		sql.SELECT_DISTINCT("servIns.NOFACTURABLE");
		sql.SELECT_DISTINCT("servIns.solicitarBaja"); // Este atributo hace referencia a la propiedad/check "Solictar
														// baja por internet"
		sql.SELECT_DISTINCT("servIns.Automatico");
		sql.SELECT_DISTINCT("precioServ.valor as precio");
		sql.SELECT_DISTINCT("f_siga_getrecurso(periodicidad.descripcion, "+idioma+") as periodo");

		sql.FROM(" pys_serviciosinstitucion servIns, pys_tipoiva tiva");
		sql.FROM("pys_serviciossolicitados servSol");
		sql.FROM("pys_preciosServicios precioServ");
		
		if(aFechaDe != null) {
			DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
			String strDate = dateFormatSql
					.format(dateFormatFront.parse(aFechaDe.toString()).getTime());
			//Deben estar en estado "Aceptada" o "Pendiente de anulacion" en esa fecha
			sql.WHERE(
					"(suscripcion.fechaSuscripcion is not null and "
					+ "suscripcion.fechaSuscripcion <= to_date('" + strDate + "','dd/MM/YY') and"
					+ "(suscripcion.fechaBaja is null or suscripcion.fechaBaja > to_date('" + strDate + "','dd/MM/YY')))");
		}

		sql.WHERE(" servIns.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" tiva.idtipoiva (+) = servIns.idtipoiva");
		sql.WHERE(" servSol.idinstitucion(+) = servIns.idinstitucion");
		sql.WHERE("servSol.idpeticion = " + idPeticion);
		sql.WHERE("servSol.idservicio(+) = servIns.idservicio and servSol.idtiposervicios(+) = servIns.idtiposervicios "
				+ "and servSol.idserviciosinstitucion(+) = servIns.idserviciosinstitucion");
		sql.WHERE("precioserv.idservicio (+) = servIns.idservicio");
		sql.WHERE("precioserv.idtiposervicios (+) = servIns.idtiposervicios");
		sql.WHERE("precioserv.idserviciosinstitucion (+) = servIns.idserviciosinstitucion");
		sql.WHERE("precioserv.idinstitucion (+) = servIns.idinstitucion");

		sql.ORDER_BY(" servIns.DESCRIPCION");

		return sql.toString();
	}

}
