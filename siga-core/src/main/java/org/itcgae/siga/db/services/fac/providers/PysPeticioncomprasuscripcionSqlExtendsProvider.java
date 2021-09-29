package org.itcgae.siga.db.services.fac.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionSqlProvider;

public class PysPeticioncomprasuscripcionSqlExtendsProvider extends PysPeticioncomprasuscripcionSqlProvider {

	public String getFichaCompraSuscripcion(FichaCompraSuscripcionItem peticion, boolean esColegiado) {
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
		
		sql.SELECT("pet.fecha as fechaPendiente"); // Esta fecha se utiliza tanto fecha de solicitud, anulacion o denegacion según
									// la documentación.
		
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
			sql.SELECT("CASE WHEN suscripcion.fecha is null THEN petBaja.fecha \r\n"
					+ "ELSE null END as fechaDenegada \r\n");
			sql.SELECT("CASE WHEN suscripcion.fecha is not null petBaja.fecha \r\n"
					+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
			sql.SELECT("suscripcion.fechasuscripcion as fechaAceptada");
			sql.SELECT("suscripcion.fechaBaja as fechaAnulada");
			sql.LEFT_OUTER_JOIN("(SELECT * FROM PYS_suscripcion sus) suscripcion on suscripcion.idpeticion = pet.idpeticion and suscripcion.idinstitucion = pet.idinstitucion");

		}
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta=pet.idpeticion");

		
		sql.FROM("pys_peticioncomprasuscripcion pet");

		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.INNER_JOIN(
				"adm_usuarios usuario ON (pet.usumodificacion = usuario.idusuario and pet.idinstitucion = usuario.idinstitucion)");

		sql.WHERE("pet.idinstitucion = " + peticion.getIdInstitucion());
		sql.WHERE("pet.idpeticion = " + peticion.getnSolicitud());

		// Para obtener las formas de pago comunes entre los productos de la compra. Se
		// devuelven los idformapago concatenados con comas para se gestion.
		if (peticion.getProductos() != null) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT(
					"LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			for (ListaProductosItem producto : peticion.getProductos()) {
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
						+ producto.getIdproductoinstitucion() + "\r\n";
				fromPagosComunes += "intersection\r\n";
			}
			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 16) + ")) formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");
			
			sql.SELECT("(SELECT idformapago FROM pys_productossolicitados prodSol where prodSol.idinstitucion ="+peticion.getIdInstitucion()+" and prodSol.idpeticion = "+peticion.getnSolicitud()+" and ROWNUM <=1) as idFormaPagoSeleccionada");
			
			sql.INNER_JOIN("pys_productossolicitados prodSol on prodSol.idinstitucion = pet.idinstitucion and prodSol.idpeticion = pet.idpeticion");
		}

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
		if (peticion.getProductos() != null) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT(
					"LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinProductos = "pys_productosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaProductosItem producto : peticion.getProductos()) {
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
			if(peticion.getIdEstadoPeticion() != null) {
				sql.SELECT("FIRST(prodSol.idformapago) as idFormaPagoSeleccionada");
				
				sql.INNER_JOIN("pys_productossolicitados prodSol on prodSol.idinstitucion = pet.idinstitucion and prodSol.idpeticion = pet.idpeticion");
			}
			
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
				if(peticion.getIdEstadoPeticion() != null && !peticion.getIdEstadoPeticion().equals("10")) {
				sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compras.IDFACTURA");
				}
			}
			else sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS pendPago");
			

			sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prin.idtipoiva");
		}
		
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
	
	public String getListaCompras(FiltrosCompraProductosItem filtro, Short idInstitucion, String idioma) {
		
		SQL sql = new SQL();
		
		sql.SELECT("pet.fecha as fechaSolicitud");
		sql.SELECT("pet.idPeticion as nSolicitud");
		sql.SELECT("per.nifcif as nIdentificacion");
		sql.SELECT("col.NCOLEGIADO \r\n");
		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as apellidosnombre \r\n");
		sql.SELECT("FIRST_VALUE(prodIns.descripcion) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as concepto \r\n");
		sql.SELECT("FIRST_VALUE(prodSol.idformapago) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idformapago \r\n");
		sql.SELECT("f_siga_getrecurso(formPago.descripcion, "+ idioma +") as desFormaPago");
		sql.SELECT("F_siga_formatonumero(ROUND(prodIns.VALOR*TIVA.VALOR/100)+prodIns.VALOR, 2) AS impTotal \r\n");
		
		sql.SELECT("CASE WHEN compra.fecha is null THEN petBaja.fecha \r\n"
				+ "ELSE null END as fechaDenegada \r\n");
		sql.SELECT("CASE WHEN compra.fecha is not null THEN petBaja.fecha \r\n"
				+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
		sql.SELECT("compra.fecha as fechaEfectiva");
		sql.SELECT("compra.fechaBaja as fechaAnulada");
		sql.SELECT("f_siga_getrecurso_etiqueta(estfact.DESCRIPCION,'" + idioma + "') AS estadoFactura");
		
		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION pet");
		
		sql.INNER_JOIN("PYS_productosSolicitados prodSol on prodSol.idinstitucion=pet.idInstitucion and prodSol.idpeticion=pet.idPeticion");
		sql.INNER_JOIN("pys_formapago formPago on formPago.idformapago = prodSol.idformapago");
		sql.LEFT_OUTER_JOIN("pys_compra compra on compra.idinstitucion = pet.idinstitucion and compra.idpeticion = pet.idpeticion");
		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.LEFT_OUTER_JOIN("cen_colegiado col on col.idpersona = pet.idpersona and col.idinstitucion = pet.idinstitucion");
		sql.INNER_JOIN("pys_productosinstitucion prodIns on prodIns.idinstitucion = prodSol.idinstitucion and prodIns.idproducto = prodSol.idProducto \r\n"
				+ "and prodIns.idTipoProducto = prodSol.idTipoProducto and prodIns.idProductoInstitucion = prodSol.idProductoInstitucion");
		sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prodIns.idtipoiva");
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta = pet.idpeticion");
		sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compra.idfactura");
		sql.LEFT_OUTER_JOIN("fac_estadoFactura estFact on estFact.idestado = fact.estado");
		
		sql.WHERE("pet.idinstitucion = "+idInstitucion.toString());
		
		if(filtro.getIdEstadoSolicitud() != null) {
			switch(filtro.getIdEstadoSolicitud()) {
				case "1":
					sql.WHERE("fechaDenega is null and fechaAprobada is null");
					break;
				case "2":
					sql.WHERE("fechaDenegada is not null");
					break;
				case "3":
					sql.WHERE("fechaAprobada is not null and fechaSolicitadaAnulacion is null");
					break;
				case "4":
					sql.WHERE("fechaSolicitadaAnulacion is not null and fechaAnulada is null");
					break;
				case "5":
					sql.WHERE("fechaAnulada is not null");
					break;
			}
		}
		
		if(filtro.getIdpersona() != null)sql.WHERE("pet.idpersona = "+filtro.getIdpersona());
		
		if(filtro.getnSolicitud() != null && filtro.getnSolicitud().trim() != "")sql.WHERE("pet.idpeticion like '%"+filtro.getnSolicitud()+"%'");
		
		if(filtro.getDescProd() != null && filtro.getDescProd().trim() != "")sql.WHERE("prodIns.descripcion like '%"+filtro.getDescProd()+"%'");
		
		if(filtro.getFechaSolicitudDesde() != null)sql.WHERE("pet.fecha >= to_date('"+filtro.getFechaSolicitudDesde()+"','DD-MM-YY')");

		if(filtro.getFechaSolicitudHasta() != null)sql.WHERE("pet.fecha <= to_date('"+filtro.getFechaSolicitudHasta()+"','DD-MM-YY')");
			
		if(filtro.getIdTipoProducto() != null)sql.WHERE("prodSol.idProductoInstitucion = "+filtro.getIdTipoProducto());

		if(filtro.getIdCategoria() != null)sql.WHERE("prodSol.idTipoProducto = "+filtro.getIdCategoria());
//		private String importe; // valor aplicado durante la compra (importe total)
		
		return sql.toString();
		
	}
	
	public String comboestadoFactura(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDestado AS ID");
		sql.SELECT("f_siga_getrecurso_etiqueta(DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("fac_estadoFactura");
		
		return sql.toString();
	}

}
