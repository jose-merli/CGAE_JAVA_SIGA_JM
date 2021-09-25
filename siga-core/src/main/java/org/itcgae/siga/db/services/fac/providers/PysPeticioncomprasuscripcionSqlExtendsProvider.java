package org.itcgae.siga.db.services.fac.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
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
		sql.SELECT("CASE WHEN not exists(SELECT 1 FROM PYS_COMPRA compra where compra.idpeticion = "+peticion.getnSolicitud()+"  and compra.idinstitucion = "+peticion.getIdInstitucion()+" and rownum <= 1) THEN \r\n"
				+ "		CASE WHEN not exists(select 1 from pys_peticioncomprasuscripcion petBaja where petBaja.idinstitucion = "+peticion.getIdInstitucion()+" and petBaja.idpeticionalta = "+peticion.getnSolicitud()+") THEN null \r\n"
				+ "		ELSE petBaja.fecha END \r\n"
				+ "ELSE null END as fechaDenegada \r\n");
		sql.SELECT("CASE WHEN not exists(SELECT 1 FROM PYS_COMPRA compra where compra.idpeticion = "+peticion.getnSolicitud()+"  and compra.idinstitucion = "+peticion.getIdInstitucion()+" and rownum <= 1) THEN \r\n"
				+ "		CASE WHEN not exists(select 1 from pys_peticioncomprasuscripcion petBaja where petBaja.idinstitucion = "+peticion.getIdInstitucion()+" and petBaja.idpeticionalta = "+peticion.getnSolicitud()+") THEN null \r\n"
				+ "		ELSE petBaja.fecha END "
				+ "ELSE null END as fechaSolicitadaAnulacion \r\n");
		sql.SELECT("compra.fecha as fechaAceptada");
		sql.SELECT("compra.fechaBaja as fechaAnulada");
		sql.LEFT_OUTER_JOIN("PYS_COMPRA compra on compra.idpeticion = pet.idpeticion and compra.idinstitucion = pet.idinstitucion");

		}
		else {
			sql.SELECT("CASE WHEN suscripcion not exists THEN \r\n"
					+ "		CASE WHEN petBaja not exists THEN null \r\n"
					+ "		ELSE petBaja.fecha END \r\n"
					+ "ELSE null END as fechaDenegada \r\n");
			sql.SELECT("CASE WHEN suscripcion exists THEN \r\n"
					+ "		CASE WHEN petBaja not exists THEN null \r\n"
					+ "		ELSE petBaja.fecha END "
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

}
