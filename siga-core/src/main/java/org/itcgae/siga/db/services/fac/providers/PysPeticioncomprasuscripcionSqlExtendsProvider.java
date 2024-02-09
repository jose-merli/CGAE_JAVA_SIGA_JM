package org.itcgae.siga.db.services.fac.providers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltroCargaMasivaCompras;
import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.FiltrosSuscripcionesItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionItem;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionSqlProvider;

public class PysPeticioncomprasuscripcionSqlExtendsProvider extends PysPeticioncomprasuscripcionSqlProvider {

	private Logger LOGGER = Logger.getLogger(PysPeticioncomprasuscripcionSqlExtendsProvider.class);

	public String getFichaCompraSuscripcion(FichaCompraSuscripcionItem peticion, boolean esColegiado, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("pet.idinstitucion");
		sql.SELECT("per.idpersona");
		// TARJETA CLIENTE
		sql.SELECT("per.nombre");
		sql.SELECT("cc.NCOLEGIADO");
		sql.SELECT("concat(per.apellidos1,concat(' ',per.apellidos2)) as apellidos");
		sql.SELECT("per.nifcif");
		sql.SELECT("per.IDTIPOIDENTIFICACION");
		// TARJETA SOLICITUD
		sql.SELECT("pet.idpeticion");
		sql.SELECT("pet.idestadopeticion");// Para determinar si es una fecha de solicitud, anulacion o denegacion.
											// Determinar el equivalente de sus valores numericos.
		sql.SELECT("nvl(usuario.descripcion, 'Proceso Automático') as usuModificacion");

		sql.SELECT("pet.fecha as fechaPendiente"); // Esta fecha se utiliza de fecha de solicitud

		// Para obtener el historico de la peticion
		if (peticion.getProductos() != null) {
			sql.SELECT("CASE WHEN compra.fecha is null THEN petBaja.fecha \r\n" + "ELSE null END as fechaDenegada \r\n");
			sql.SELECT("CASE WHEN compra.fecha is not null THEN petBaja.fecha \r\n" + "ELSE null END as fechaSolicitadaAnulacion \r\n");
			sql.SELECT("compra.fecha as fechaAceptada");
			sql.SELECT("compra.fechaBaja as fechaAnulada");
			sql.LEFT_OUTER_JOIN("PYS_COMPRA compra on compra.idpeticion = pet.idpeticion and compra.idinstitucion = pet.idinstitucion");
		} else {
			sql.SELECT("CASE WHEN suscripcion.fechasuscripcion is null THEN petBaja.fecha \r\n" + "ELSE null END as fechaDenegada \r\n");
			sql.SELECT("CASE WHEN suscripcion.fechasuscripcion is not null THEN petBaja.fecha \r\n" + "ELSE null END as fechaSolicitadaAnulacion \r\n");
			sql.SELECT("suscripcion.fechasuscripcion as fechaAceptada");
			sql.SELECT("suscripcion.fechaBaja as fechaAnulada");
			sql.LEFT_OUTER_JOIN("PYS_suscripcion suscripcion on suscripcion.idpeticion = pet.idpeticion and suscripcion.idinstitucion = pet.idinstitucion");

		}
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta=pet.idpeticion");

		sql.FROM("pys_peticioncomprasuscripcion pet");

		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.INNER_JOIN("CEN_COLEGIADO cc ON per.idpersona = cc.IDPERSONA");

		sql.LEFT_OUTER_JOIN("adm_usuarios usuario ON (pet.usumodificacion = usuario.idusuario and pet.idinstitucion = usuario.idinstitucion)");

		sql.WHERE("pet.idinstitucion = " + idInstitucion);
		sql.WHERE("pet.idpeticion = " + peticion.getnSolicitud());

		// Para obtener las formas de pago comunes entre los productos de la compra. Se
		// devuelven los idformapago concatenados con comas para se gestion.
		if (peticion.getProductos() != null && peticion.getProductos().size() > 0) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT("LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinProductos = "pys_productosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaProductosCompraItem producto : peticion.getProductos()) {
				fromPagosComunes += "select pago.idformapago\r\n" + "				from pys_productosinstitucion prod\r\n" + "				inner join pys_formapagoproducto pago on pago.idinstitucion = prod.idinstitucion and prod.idproducto = pago.idproducto \r\n" + "				and pago.idtipoproducto = prod.idtipoproducto AND prod.IDPRODUCTOINSTITUCION = pago.IDPRODUCTOINSTITUCION\r\n" + "				where prod.idinstitucion = " + peticion.getIdInstitucion();
				// Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no
				if (esColegiado)
					fromPagosComunes += " and pago.internet = 'A'";
				else
					fromPagosComunes += " and pago.internet = 'S'";
				fromPagosComunes += " and (prod.idproducto = " + producto.getIdproducto() + " and prod.idtipoproducto=" + producto.getIdtipoproducto() + " and prod.idproductoinstitucion=" + producto.getIdproductoinstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";

				innerJoinProductos += "(prin.idproducto = " + producto.getIdproducto() + " and prin.idtipoproducto=" + producto.getIdtipoproducto() + " and prin.idproductoinstitucion=" + producto.getIdproductoinstitucion() + ") OR";

			}
			// Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinProductos.substring(0, innerJoinProductos.length() - 2) + ")");

			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");

			// Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(prodSol.idformapago) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idFormaPagoSeleccionada");
			// Obtenemos la cuenta bancaria
			sql.SELECT("FIRST_VALUE(prodSol.idcuenta) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idCuentaBancSeleccionada");

			sql.INNER_JOIN("pys_productossolicitados prodSol on prodSol.idinstitucion = pet.idinstitucion and prodSol.idpeticion = pet.idpeticion");

			// Obtención de importes de productos
			sql.SELECT("F_siga_formatonumero(PRIN.VALOR,2) AS totalNeto");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100), 2),2) as totalIVA");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS impTotal");
			// Importe total menos Anticipos (pysanticiposletrado) menos Suma de lo pagado en las facturas (fac_factura) asociadas
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR - " + "COALESCE((SELECT SUM(importeInicial)\r\n" + "FROM pys_anticipoletrado " + "where idpersona = " + peticion.getIdPersona() + "),0) - " + "COALESCE((SELECT SUM(fact.IMPTOTALPAGADO)\r\n" + "FROM fac_factura fact\r\n" + "INNER JOIN pys_compra on pys_compra.idfactura = fact.idfactura and pys_compra.idpeticion = " + peticion.getnSolicitud() + " and pys_compra.idinstitucion = fact.idinstitucion\r\n" + "WHERE fact.idinstitucion = " + peticion.getIdInstitucion() + "),0), 2),2) AS pendPago");
			// Solo se puden comprobar las facturas en el caso que haya habido un registro de compra.
			// Pendiente de optimizacion por SQL
			if (peticion.getFechaAceptada() != null) {
				sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compra.IDFACTURA");
			}

			else
				sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS pendPago");

			sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prodSol.idtipoiva");
		} else if (peticion.getServicios() != null && peticion.getServicios().size() > 0) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT("LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinServicios = "pys_serviciosinstitucion servIns on servIns.idinstitucion = pet.idinstitucion  and (";
			for (ListaServiciosSuscripcionItem servicio : peticion.getServicios()) {
				fromPagosComunes += "select pago.idformapago\r\n" + "				from pys_serviciosinstitucion serv\r\n" + "				inner join pys_formapagoservicios pago on pago.idinstitucion = serv.idinstitucion and serv.idservicio = pago.idservicio \r\n" + "				and pago.idtiposervicios = serv.idtiposervicios AND serv.IDserviciosINSTITUCION = pago.IDserviciosINSTITUCION\r\n" + "				where serv.idinstitucion = " + peticion.getIdInstitucion();
				// Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no
				if (esColegiado)
					fromPagosComunes += " and pago.internet = 'A'";
				else
					fromPagosComunes += " and pago.internet = 'S'";
				fromPagosComunes += " and (serv.idservicio = " + servicio.getIdServicio() + " and serv.idtiposervicios=" + servicio.getIdTipoServicios() + " and serv.idserviciosinstitucion=" + servicio.getIdServiciosInstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";

				innerJoinServicios += "(servIns.idservicio = " + servicio.getIdServicio() + " and servIns.idtiposervicios =" + servicio.getIdTipoServicios() + " and servIns.idserviciosinstitucion=" + servicio.getIdServiciosInstitucion() + ") OR";

			}
			// Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinServicios.substring(0, innerJoinServicios.length() - 2) + ")");

			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");

			// Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(servSol.idformapago) OVER (ORDER BY servSol.IDPETICION) as idFormaPagoSeleccionada");
			// Obtenemos la cuenta bancaria
			sql.SELECT("FIRST_VALUE(servSol.idcuenta) OVER (ORDER BY servSol.IDPETICION) as idCuentaBancSeleccionada");

			sql.INNER_JOIN("pys_serviciossolicitados servSol on servSol.idinstitucion = pet.idinstitucion and servSol.idpeticion = pet.idpeticion");
		}

		// REVISAR
		sql.WHERE("rownum = 1");

		LOGGER.info("CONSULTA FICHA COMPRA/SUSCRIPCION: \r\n" + sql.toString());

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
			sql.SELECT("cc.NCOLEGIADO");

			sql.INNER_JOIN("cen_persona per on per.idpersona = " + peticion.getIdPersona());
			sql.INNER_JOIN("CEN_COLEGIADO cc ON per.idpersona = cc.IDPERSONA");
		} else {
			sql.SELECT("null as idpersona");
			sql.SELECT("null as nombre");
			sql.SELECT("null as apellidos");
			sql.SELECT("null as nifcif");
			sql.SELECT("null as IDTIPOIDENTIFICACION");
		}
		sql.SELECT(peticion.getIdInstitucion() + " as idInstitucion");

		// TRAJETA SOLICITUD
		// sql.SELECT("(\r\n" + " SELECT\r\n" + " MAX(pet.idpeticion)\r\n" + " FROM\r\n"
		// + " pys_peticioncomprasuscripcion pet\r\n" + "where pet.idinstitucion = "+peticion.getIdInstitucion()+" ) + 1 AS idpeticion");

		sql.SELECT("usuario.descripcion as usuModificacion");

		sql.FROM("pys_peticioncomprasuscripcion pet");

		sql.WHERE("pet.idinstitucion = " + peticion.getIdInstitucion());
		sql.INNER_JOIN("adm_usuarios usuario ON (usuario.idusuario = " + peticion.getUsuModificacion() + " and usuario.idinstitucion = " + peticion.getIdInstitucion() + ")");

		// Para obtener las formas de pago comunes entre los productos de la compra. Se
		// devuelven los idformapago concatenados con comas para su gestion.
		if (peticion.getProductos() != null && peticion.getProductos().size() > 0) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT("LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinProductos = "pys_productosinstitucion prin on prin.idinstitucion = pet.idinstitucion  and (";
			for (ListaProductosCompraItem producto : peticion.getProductos()) {
				fromPagosComunes += "select pago.idformapago\r\n" + "				from pys_productosinstitucion prod\r\n" + "				inner join pys_formapagoproducto pago on pago.idinstitucion = prod.idinstitucion and prod.idproducto = pago.idproducto \r\n" + "				and pago.idtipoproducto = prod.idtipoproducto AND prod.IDPRODUCTOINSTITUCION = pago.IDPRODUCTOINSTITUCION\r\n" + "				where prod.idinstitucion = " + peticion.getIdInstitucion();
				// Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no
				if (esColegiado)
					fromPagosComunes += " and pago.internet = 'A'";
				else
					fromPagosComunes += " and pago.internet = 'S'";
				fromPagosComunes += " and (prod.idproducto = " + producto.getIdproducto() + " and prod.idtipoproducto=" + producto.getIdtipoproducto() + " and prod.idproductoinstitucion=" + producto.getIdproductoinstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";

				innerJoinProductos += "(prin.idproducto = " + producto.getIdproducto() + " and prin.idtipoproducto=" + producto.getIdtipoproducto() + " and prin.idproductoinstitucion=" + producto.getIdproductoinstitucion() + ") OR";
			}
			// Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinProductos.substring(0, innerJoinProductos.length() - 2) + ")");

			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");

			// Obtenemos el id de la forma de pago utilizada.
			sql.SELECT("FIRST_VALUE(prodSol.idformapago) OVER (ORDER BY prodSol.FECHARECEPCIONSOLICITUD) as idFormaPagoSeleccionada");

			sql.LEFT_OUTER_JOIN("pys_productossolicitados prodSol on prodSol.idinstitucion = pet.idinstitucion and prodSol.idpeticion = pet.idpeticion");

			// Obtención de importes de productos
			sql.SELECT("F_siga_formatonumero(PRIN.VALOR,2) AS totalNeto");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100), 2),2) as totalIVA");
			sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS impTotal");
			if (peticion.getIdPersona() != null) {
				// Importe total menos Anticipos (pysanticiposletrado) menos Suma de lo pagado en las facturas (fac_factura) asociadas
				sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR - " + "COALESCE((SELECT SUM(importeInicial)\r\n" + "FROM pys_anticipoletrado " + "where idpersona = " + peticion.getIdPersona() + "),0) - " + "COALESCE((SELECT SUM(fact.IMPTOTALPAGADO)\r\n" + "FROM fac_factura fact\r\n" + "INNER JOIN pys_compra on pys_compra.idfactura = fact.idfactura and pys_compra.idpeticion = " + peticion.getnSolicitud() + " and pys_compra.idinstitucion = fact.idinstitucion\r\n" + "WHERE fact.idinstitucion = " + peticion.getIdInstitucion() + "),0), 2),2) AS pendPago");
				// Solo se puden comprobar las facturas en el caso que haya habido un registro de compra.
				// Pendiente de optimizacion por SQL
				if (peticion.getFechaAceptada() != null) {
					sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = compras.IDFACTURA");
				}
			} else
				sql.SELECT("F_siga_formatonumero(ROUND((PRIN.VALOR*TIVA.VALOR/100)+PRIN.VALOR, 2),2) AS pendPago");

			sql.INNER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = prin.idtipoiva");
		} else if (peticion.getServicios() != null && peticion.getServicios().size() > 0) {
			SQL sqlPagos = new SQL();
			// Con listagg logramos que los distintos ids de formas de pago se muestren en
			// unica fila
			sqlPagos.SELECT("LISTAGG(formasPagoComunes.idformapago, ',') WITHIN GROUP (ORDER BY formasPagoComunes.idformapago) ");

			// OBTENEMOS LAS FORMAS DE PAGO COMUNES
			String fromPagosComunes = "(";
			String innerJoinServicios = "pys_serviciosinstitucion servIns on servIns.idinstitucion = pet.idinstitucion  and (";
			for (ListaServiciosSuscripcionItem servicio : peticion.getServicios()) {
				fromPagosComunes += "select pago.idformapago\r\n" + "				from pys_serviciosinstitucion serv\r\n" + "				inner join pys_formapagoservicios pago on pago.idinstitucion = serv.idinstitucion and serv.idservicio = pago.idservicio \r\n" + "				and pago.idtiposervicios = serv.idtiposervicios AND serv.IDserviciosINSTITUCION = pago.IDserviciosINSTITUCION\r\n" + "				where serv.idinstitucion = " + peticion.getIdInstitucion();
				// Se filtran los metodos de pago sean por internet o no según si el usuario es un colegiado o no
				if (esColegiado)
					fromPagosComunes += " and pago.internet = 'A'";
				else
					fromPagosComunes += " and pago.internet = 'S'";
				fromPagosComunes += " and (serv.idservicio = " + servicio.getIdServicio() + " and serv.idtiposervicios=" + servicio.getIdTipoServicios() + " and serv.idserviciosinstitucion=" + servicio.getIdServiciosInstitucion() + ") \r\n";
				fromPagosComunes += "intersect\r\n";

				innerJoinServicios += "(servIns.idservicio = " + servicio.getIdServicio() + " and servIns.idtiposervicios =" + servicio.getIdTipoServicios() + " and servIns.idserviciosinstitucion=" + servicio.getIdServiciosInstitucion() + ") OR";

			}
			// Se elimina el ultimo OR
			sql.INNER_JOIN(innerJoinServicios.substring(0, innerJoinServicios.length() - 2) + ")");

			// Se elimina la ultima interseccion
			sqlPagos.FROM(fromPagosComunes.substring(0, fromPagosComunes.length() - 13) + ") formasPagoComunes");

			sql.SELECT("(" + sqlPagos.toString() + ") AS idformaspagocomunes");
		}

		// REVISAR
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

	public String getListaCompras(FiltrosCompraProductosItem filtro, Short idInstitucion, String idioma, Integer tamMax) throws ParseException {

		SQL sql = new SQL();
		sql.SELECT("pet.fecha AS FACHASOLICITUD");
		sql.SELECT("pet.idPersona AS IDPERSONA");
		sql.SELECT("compra.fecha AS FECHAEFECTIVA");
		sql.SELECT("CASE WHEN compra.fecha IS NULL THEN petBaja.fecha ELSE NULL END AS FECHADENEGADA");
		sql.SELECT("CASE WHEN compra.fecha IS NOT NULL THEN petBaja.fecha ELSE NULL END AS FECHASOLICITADAANULACION");
		sql.SELECT("compra.fechaBaja AS FECHAANULADA");
		sql.SELECT("pet.idPeticion AS NSOLICITUD");
		sql.SELECT("per.nifcif AS NIDENTIFICACION");
		sql.SELECT("col.NCOLEGIADO AS NCOLEGIADO");
		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre AS APELLIDOSNOMBRE");		
		sql.SELECT("CASE WHEN compra.fechaBaja IS NOT NULL THEN 5 WHEN compra.fecha IS NOT NULL AND petBaja.fecha IS NOT NULL THEN 4 WHEN compra.fecha IS NOT NULL THEN 3 WHEN compra.fecha IS NULL AND petBaja.fecha IS NOT NULL THEN 2 ELSE 1 END AS IDESTADOSOLICITUD");
		sql.SELECT("prodIns.descripcion AS CONCEPTO");
		sql.SELECT("prodSol.idformapago AS IDFORMADEPAGO");
		sql.SELECT("CASE WHEN prodSol.noFacturable = '1' THEN 'No facturable' ELSE f_siga_getrecurso(formPago.descripcion, 1) END AS DESFROMADEPAGO");
		sql.SELECT("(prodSol.VALOR * prodSol.cantidad)*(1 + tiva.VALOR / 100) AS IMPORTE");
		sql.SELECT("f_siga_getrecurso_etiqueta(estfact.DESCRIPCION, '1') AS ESTADOFACTURA");
		sql.SELECT("CASE WHEN fact.idfactura IS NULL THEN '0' ELSE '1' END AS FACTURAS");
		sql.SELECT("(SELECT SUM(prodIns2.SOLICITARBAJA) - COUNT(*) FROM pys_productosinstitucion prodIns2 INNER JOIN PYS_productosSolicitados prodSol2 ON prodIns2.idinstitucion = prodSol2.idinstitucion AND prodIns2.idproducto = prodSol2.idProducto AND prodIns2.idTipoProducto = prodSol2.idTipoProducto AND prodIns2.idProductoInstitucion = prodSol2.idProductoInstitucion WHERE prodSol2.idinstitucion = pet.idinstitucion AND prodSol2.idpeticion = pet.idPeticion) AS SOLICITARBAJA");
		sql.SELECT("prodSol.idTipoProducto AS IDTIPOPRODUCTO");
		sql.SELECT("prodSol.idProducto AS IDPRODUCTO");
		sql.SELECT("prodSol.idProductoInstitucion AS IDPRODUCTOINSTITUCION");

		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION pet");

		sql.INNER_JOIN("cen_persona per ON per.idpersona = pet.idpersona");
		sql.LEFT_OUTER_JOIN("cen_colegiado col ON col.idpersona = pet.idpersona AND col.idinstitucion = pet.idinstitucion");
		sql.INNER_JOIN("PYS_productosSolicitados prodSol ON prodSol.idinstitucion = pet.idInstitucion AND prodSol.idpeticion = pet.idPeticion");
		sql.INNER_JOIN("pys_productosinstitucion prodIns ON prodIns.idinstitucion = prodSol.idinstitucion AND prodIns.idproducto = prodSol.idProducto AND prodIns.idTipoProducto = prodSol.idTipoProducto AND prodIns.idProductoInstitucion = prodSol.idProductoInstitucion");
		sql.INNER_JOIN("pys_tipoiva tiva ON tiva.idtipoiva = prodSol.idtipoiva");
		sql.LEFT_OUTER_JOIN("pys_formapago formPago ON formPago.idformapago = prodSol.idformapago");
		sql.LEFT_OUTER_JOIN("pys_compra compra ON compra.idinstitucion = pet.idinstitucion AND compra.idpeticion = pet.idpeticion AND compra.idproducto = prodSol.idProducto AND compra.idTipoProducto = prodSol.idTipoProducto AND compra.idProductoInstitucion = prodSol.idProductoInstitucion");
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja ON petBaja.idinstitucion = pet.idinstitucion AND petBaja.idpeticionalta = pet.idpeticion");
		sql.LEFT_OUTER_JOIN("fac_factura fact ON fact.idfactura = compra.idfactura AND fact.idinstitucion = compra.idinstitucion");
		sql.LEFT_OUTER_JOIN("fac_estadoFactura estFact ON estFact.idestado = fact.estado");

		sql.WHERE("pet.idinstitucion = " + idInstitucion.toString());

		if (filtro.getIdEstadoSolicitud() != null && !filtro.getIdEstadoSolicitud().isEmpty()) {
			// Recorremos el array de estados seleccionados
			List<String> estados = filtro.getIdEstadoSolicitud();
			String condSolicitud = "((";
			for (String estado : estados) {
				switch (estado) {
				case "1":
					// Pendiente
					condSolicitud += "CASE WHEN compra.fecha is null THEN petBaja.fecha ELSE null END is null and compra.fecha is null";
					break;
				case "2":
					// Denegada
					condSolicitud += "CASE WHEN compra.fecha is null THEN petBaja.fecha ELSE null END is not null";
					break;
				case "3":
					// Aceptada
					condSolicitud += "compra.fecha is not null and CASE WHEN compra.fecha is not null THEN petBaja.fecha ELSE null END is null and compra.fechaBaja is null";
					break;
				case "4":
					// Solicitada anulacion
					condSolicitud += "CASE WHEN compra.fecha is not null THEN petBaja.fecha ELSE null END is not null and compra.fechaBaja is null and compra.fecha is not null";
					break;
				case "5":
					// Anulada
					condSolicitud += "compra.fechaBaja is not null";
					break;
				}
				condSolicitud += ") OR (";
			}
			condSolicitud = condSolicitud.substring(0, condSolicitud.length() - 4) + ")";
			sql.WHERE(condSolicitud);
		}

		if (filtro.getIdpersona() != null && !"".equalsIgnoreCase(filtro.getIdpersona())) {
			sql.WHERE("pet.idpersona = " + filtro.getIdpersona());
		}

		if (filtro.getnSolicitud() != null && filtro.getnSolicitud().trim() != "") {
			sql.WHERE("pet.idpeticion like '%" + filtro.getnSolicitud() + "%'");
		}

		if (filtro.getDescProd() != null && filtro.getDescProd().trim() != "") {
			sql.WHERE("convert(UPPER(prodIns.descripcion) , 'US7ASCII' ) like convert(UPPER('%" + filtro.getDescProd() + "%') , 'US7ASCII' )");
		}

		if (filtro.getFechaSolicitudDesde() != null) {
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = dateFormatSql.format(filtro.getFechaSolicitudDesde());
			sql.WHERE("pet.fecha >= to_date('" + strDate + " 00:00:00' ,'DD/MM/YYYY HH24:MI:SS')");
		}

		if (filtro.getFechaSolicitudHasta() != null) {
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = dateFormatSql.format(filtro.getFechaSolicitudHasta());
			sql.WHERE("pet.fecha <= to_date('" + strDate + " 23:59:00','DD/MM/YYYY HH24:MI:SS')");
		}

		if (filtro.getIdCategoria() != null && !filtro.getIdCategoria().isEmpty()) {

			if (filtro.getIdTipoProducto() != null && !filtro.getIdTipoProducto().isEmpty()) {
				String condTipoProd = "((";

				// Actualmente el id de producto se define así "IDTIPOPRODUCTO || '-' || IDPRODUCTO".
				// Aunque lleve a confusion, IDTIPOPRODUCTO en la BBDD hace referencia a idCategoria
				// mientras que IDPRODUCTO a idTipoProducto

				List<String> categoriasConProducto = new ArrayList<String>();
				for (String producto : filtro.getIdTipoProducto()) {

					categoriasConProducto.add(producto.split("-")[0]);

					// Creamos las condiciones por pares para los servicios especificos seleccionados
					condTipoProd += ("(prodSol.idProducto = " + producto.split("-")[1] + " AND prodSol.idTipoProducto = " + producto.split("-")[0] + " ) OR ");
				}

				condTipoProd = condTipoProd.substring(0, condTipoProd.length() - 3)+")"; 
				
				//Comprobamos que categorias no tienen un servicio seleccionado 
				List<String> categoriasSinProducto = filtro.getIdCategoria();
				categoriasSinProducto.removeAll(categoriasConProducto); 
				if(!categoriasSinProducto.isEmpty()) { 
					condTipoProd += (" OR (prodSol.idTipoProducto IN (" + String.join(",",filtro.getIdCategoria())+"))"); 
				}
				
				condTipoProd += ")";

				sql.WHERE(condTipoProd);
			} else {
				sql.WHERE("prodSol.idTipoproducto IN (" + String.join(",", filtro.getIdCategoria()) + ")");
			}
		}

		if (filtro.getIdEstadoFactura() != null && !filtro.getIdEstadoFactura().isEmpty()) {
			sql.WHERE("fact.estado IN (" + String.join(",", filtro.getIdEstadoFactura()) + ")");
		}

		sql.WHERE("ROWNUM <= " + tamMax);

		sql.ORDER_BY("FACHASOLICITUD DESC, NSOLICITUD ASC");

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
		sql.SELECT_DISTINCT(" case when PRodSol.orden is null then rownum \r\n" + "else prodSol.orden end as orden");
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
		sql.SELECT_DISTINCT("Prin.solicitarBaja"); // Este atributo hace referencia a la propiedad/check "Solictar baja por internet"

		sql.FROM(" pys_productosinstitucion prin, pys_tipoiva tiva");
		sql.FROM("pys_productossolicitados prodSol");

		sql.WHERE(" PRIN.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE(" tiva.idtipoiva (+) = prodSol.idtipoiva");
		sql.WHERE(" prodSol.idinstitucion(+) = prin.idinstitucion");
		sql.WHERE("prodSol.idpeticion = " + idPeticion);
		sql.WHERE("prodSol.idproducto(+) = prin.idproducto and prodSol.idtipoproducto(+) = prin.idtipoproducto and prodSol.idproductoinstitucion(+) = prin.idproductoinstitucion");

		sql.ORDER_BY(" PRIN.DESCRIPCION");

		return sql.toString();
	}

	public String getListaSuscripciones(FiltrosSuscripcionesItem filtro, Short idInstitucion, String idioma, Integer tamMaximo) throws ParseException {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("pet.fecha AS fechasolicitud, " + "    pet.idpeticion AS nsolicitud, " + "    per.nifcif AS nidentificacion, " + "    col.ncolegiado, " + "    per.apellidos1  || ' ' || per.apellidos2 || ',' || per.nombre AS apellidosnombre, " + "    CASE " + "        WHEN suscripcion.fechabaja IS NOT NULL THEN servins.descripcion " + "        WHEN precioserv.descripcion IS NULL THEN servins.descripcion || '[ ' || regexp_substr( f_siga_calculoprecioservicio( servins.idinstitucion, servins.idtiposervicios, servins.idservicio, servins.idserviciosinstitucion, pet.idpersona, 1), '[^#]+', 1, 6 ) || ' ]' " + "        ELSE servins.descripcion || '[ ' || precioserv.descripcion || ' ]' " + "    END AS concepto, " + "    servsol.idformapago AS idformapago, " + "    CASE WHEN servins.nofacturable = '1' THEN 'No facturable' ELSE f_siga_getrecurso( formpago.descripcion, 1 ) END AS desformapago, "
				+ "    CASE WHEN suscripcion.fechasuscripcion IS NULL THEN petbaja.fecha ELSE NULL END AS fechadenegada, " + "    CASE WHEN suscripcion.fechasuscripcion IS NOT NULL THEN petbaja.fecha ELSE NULL END AS fechasolicitadaanulacion, " + "    suscripcion.fechasuscripcion AS fechaefectiva, " + "    suscripcion.fechabaja AS fechaanulada, " + "    CASE WHEN suscripcion.fechabaja IS NOT NULL THEN 5 WHEN suscripcion.fechasuscripcion IS NOT NULL AND petbaja.fecha IS NOT NULL THEN 4 WHEN suscripcion.fechasuscripcion IS NOT NULL THEN 3 WHEN suscripcion.fechasuscripcion IS NULL AND petbaja.fecha IS NOT NULL THEN 2 ELSE 1 END AS idEstadoSolicitud, " + "    CASE " + "        WHEN substr(  precioserv.valor, 1, 1 ) = ','THEN '0' || precioserv.valor  || ' (' || f_siga_getrecurso( periodicidad.descripcion, 1) || ')' "
				+ "        WHEN precioserv.descripcion IS NULL THEN regexp_substr( f_siga_calculoprecioservicio( servins.idinstitucion, servins.idtiposervicios, servins.idservicio, servins.idserviciosinstitucion, pet.idpersona, 1 ), '[^#]+', 1, 1) || ' (' || regexp_substr( f_siga_calculoprecioservicio( servins.idinstitucion, servins.idtiposervicios, servins.idservicio, servins.idserviciosinstitucion, pet.idpersona, 1 ), '[^#]+', 1, 5) || ')' " + "        ELSE precioserv.valor || ' (' || f_siga_getrecurso( periodicidad.descripcion,  1)  || ')' " + "    END AS precioperio, " + "    CASE WHEN factsus.idfactura IS NULL THEN '0' ELSE '1' END AS facturas, "
				+ "    (SELECT SUM(servinss.solicitarbaja) - COUNT(servinss.solicitarbaja) FROM pys_serviciosinstitucion servinss WHERE servinss.idinstitucion = servSol.idinstitucion and servinss.idservicio = servSol.idservicio and servinss.idTipoServicios = servSol.idTipoServicios and servinss.idServiciosInstitucion = servSol.idServiciosInstitucion) AS solicitarBaja, " + "    servins.automatico AS automatico, " + "    servSol.idtiposervicios AS idtiposervicios, " + "    servSol.idservicio AS idservicio, " + "    servSol.idserviciosinstitucion AS idserviciosinstitucion");

		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION pet");
		sql.INNER_JOIN("PYS_serviciosSolicitados servSol on servSol.idinstitucion=pet.idInstitucion and servSol.idpeticion=pet.idPeticion");
		sql.LEFT_OUTER_JOIN("pys_formapago formPago on formPago.idformapago = servSol.idformapago");
		sql.LEFT_OUTER_JOIN("pys_suscripcion suscripcion on suscripcion.idinstitucion = pet.idinstitucion and suscripcion.idpeticion = pet.idpeticion");
		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.LEFT_OUTER_JOIN("cen_colegiado col on col.idpersona = pet.idpersona and col.idinstitucion = pet.idinstitucion");
		sql.INNER_JOIN("pys_serviciosinstitucion servIns on servIns.idinstitucion = servSol.idinstitucion and servIns.idservicio = servSol.idservicio and servIns.idTipoServicios = servSol.idTipoServicios and servIns.idServiciosInstitucion = servSol.idServiciosInstitucion");
		sql.LEFT_OUTER_JOIN("pys_preciosservicios precioServ on precioServ.idinstitucion = servSol.idinstitucion and precioServ.idservicio = servSol.idservicio and precioServ.idTipoServicios = servSol.idTipoServicios and precioServ.idServiciosInstitucion = servSol.idServiciosInstitucion " + "and precioServ.idperiodicidad = servSol.idperiodicidad and precioServ.idPreciosServicios = servSol.idPreciosServicios");
		sql.LEFT_OUTER_JOIN("pys_periodicidad periodicidad on precioServ.idperiodicidad = periodicidad.idperiodicidad");
		sql.LEFT_OUTER_JOIN("pys_peticioncomprasuscripcion petBaja on petBaja.idinstitucion = pet.idinstitucion and petBaja.idpeticionalta = pet.idpeticion");
		sql.LEFT_OUTER_JOIN("fac_facturacionSuscripcion factSus on factSus.idservicio = suscripcion.idservicio and factSus.idTipoServicios = suscripcion.idTipoServicios and factSus.idServiciosInstitucion = suscripcion.idServiciosInstitucion " + "and factSus.idinstitucion = suscripcion.idinstitucion and factSus.idsuscripcion = suscripcion.idsuscripcion");
		sql.LEFT_OUTER_JOIN("fac_facturacionSuscripcion factSusBis ON factSusBis.idservicio = suscripcion.idservicio and factSusBis.idTipoServicios = suscripcion.idTipoServicios and factSusBis.idServiciosInstitucion = suscripcion.idServiciosInstitucion " + "and factSusBis.idinstitucion = suscripcion.idinstitucion and factSusBis.idsuscripcion = suscripcion.idsuscripcion AND factSus.fechainicio < factSusBis.fechainicio");
		sql.LEFT_OUTER_JOIN("fac_factura fact on fact.idfactura = factSus.idfactura and fact.idinstitucion = suscripcion.idinstitucion");

		sql.WHERE("pet.idinstitucion = " + idInstitucion.toString());
		sql.WHERE("factSusBis.idfactura is null");

		if (filtro.getIdEstadoSolicitud() != null && !filtro.getIdEstadoSolicitud().isEmpty()) {
			// Recorremos el array de estados seleccionados
			List<String> estados = filtro.getIdEstadoSolicitud();
			String condSolicitud = "((";
			for (String estado : estados) {
				switch (estado) {
				case "1": // Pendiente
					condSolicitud += "CASE WHEN suscripcion.fechaSuscripcion is null THEN petBaja.fecha	ELSE null END is null and suscripcion.fechaSuscripcion is null";
					break;
				case "2":// Denegada
					condSolicitud += "CASE WHEN suscripcion.fechaSuscripcion is null THEN petBaja.fecha ELSE null END is not null";
					break;
				case "3":// Aceptada
					condSolicitud += "suscripcion.fechaSuscripcion is not null and CASE WHEN suscripcion.fechaSuscripcion is not null THEN petBaja.fecha ELSE null END is null and suscripcion.fechaBaja is null";
					break;
				case "4":// Anulacion solicitada
					condSolicitud += "CASE WHEN suscripcion.fechaSuscripcion is not null THEN petBaja.fecha ELSE null END is not null and suscripcion.fechaBaja is null and suscripcion.fechasuscripcion is not null";
					break;
				case "5":// Anulada
					condSolicitud += "suscripcion.fechaBaja is not null";
					break;
				}
				condSolicitud += ") OR (";
			}
			condSolicitud = condSolicitud.substring(0, condSolicitud.length() - 4) + ")";
			sql.WHERE(condSolicitud);
		}

		if (filtro.getIdpersona() != null && !"".equalsIgnoreCase(filtro.getIdpersona())) {
			sql.WHERE("pet.idpersona = " + filtro.getIdpersona());
		}

		if (filtro.getnSolicitud() != null && filtro.getnSolicitud().trim() != "")
			sql.WHERE("pet.idpeticion like '%" + filtro.getnSolicitud().trim() + "%'");

		if (filtro.getDescServ() != null && filtro.getDescServ().trim() != "")
			sql.WHERE("convert(UPPER(servIns.descripcion) , 'US7ASCII' ) like convert(UPPER('%" + filtro.getDescServ().trim() + "%') , 'US7ASCII' )");

		if (filtro.getaFechaDe() != null) {
			DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YYYY");
			String strDate = dateFormatSql.format(dateFormatFront.parse(filtro.getaFechaDe().toString()).getTime());

			// Deben estar en estado "Aceptada" o "Pendiente de anulacion" en esa fecha
			sql.WHERE("(suscripcion.fechaSuscripcion is not null and " + "to_char(suscripcion.fechaSuscripcion) <= to_date('" + strDate + "','dd/MM/YYYY') and" + "(suscripcion.fechaBaja is null or to_char(suscripcion.fechaBaja) <= to_date('" + strDate + "','dd/MM/YYYY')))");
		}
		

		// REVISAR: No se busca correctamente con alos anteriores al 2000
		if (filtro.getFechaSolicitudDesde() != null) {
			DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YYYY");
			String strDate = dateFormatSql.format(dateFormatFront.parse(filtro.getFechaSolicitudDesde().toString()).getTime());
			sql.WHERE("to_char(pet.fecha) >= to_date('" + strDate + "','dd/MM/YYYY')");
		}

		if (filtro.getFechaSolicitudHasta() != null) {
			DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YYYY");
			String strDate = dateFormatSql.format(dateFormatFront.parse(filtro.getFechaSolicitudHasta().toString()).getTime());
			sql.WHERE("to_char(pet.fecha) <= to_date('" + strDate + "','dd/MM/YYYY')");
		}

		if (filtro.getIdCategoria() != null && !filtro.getIdCategoria().isEmpty()) {
			if (filtro.getIdTipoServicio() != null && !filtro.getIdTipoServicio().isEmpty()) {
				String condTipoServ = "((";
				// Actualmente el id de servicio se define así "IDTIPOSERVICIOS || '-' || IDSERVICIO".
				// Aunque lleve a confusion, IDTIPOSERVICIOS en la BBDD hace referencia a idCategoria
				// mientras que IDSERVICIO a idTipoServicio
				List<String> categoriasConServicio = new ArrayList<String>();
				for (String servicio : filtro.getIdTipoServicio()) {
					categoriasConServicio.add(servicio.split("-")[0]);
					// Creamos las condiciones por pares para los servicios especificos seleccionados
					condTipoServ += ("(servSol.idServiciosInstitucion = " + servicio.split("-")[1] + " AND servSol.idTipoServicios = " + servicio.split("-")[0] + " ) OR ");
				}

				condTipoServ = condTipoServ.substring(0, condTipoServ.length() - 3) + ")";
				// Comprobamos que categorias no tienen un servicio seleccionado
				List<String> categoriasSinServicio = filtro.getIdCategoria();

				categoriasSinServicio.removeAll(categoriasConServicio);
				if (!categoriasSinServicio.isEmpty()) {
					condTipoServ += (" OR (servSol.idTipoServicios IN (" + String.join(",", filtro.getIdCategoria()) + "))");
				}

				condTipoServ += ")";

				sql.WHERE(condTipoServ);
			} else {
				sql.WHERE("servSol.idTipoServicios IN (" + String.join(",", filtro.getIdCategoria()) + ")");
			}
		}

		if (filtro.getIdEstadoFactura() != null && !filtro.getIdEstadoFactura().isEmpty()) {
			sql.WHERE("fact.estado IN (" + String.join(",", filtro.getIdEstadoFactura()) + ")");
		}

		if (tamMaximo != null) {
			sql.WHERE("ROWNUM <= " + tamMaximo);
		}

		return sql.toString();
	}

	public String getListaServiciosSuscripcion(Short idInstitucion, String idPeticion, String idioma, Date aFechaDe) throws ParseException {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT(" case when servSol.orden is null then rownum else servSol.orden end as orden");
		sql.SELECT_DISTINCT(" servIns.descripcion");
		sql.SELECT_DISTINCT(" servSol.cantidad");
		sql.SELECT_DISTINCT(" coalesce(precioServ.valor, 0) as valor");
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
		sql.SELECT_DISTINCT("precioServ.IDPRECIOSSERVICIOS as idPrecio");
		sql.SELECT_DISTINCT("precioServ.valor as precio");
		sql.SELECT_DISTINCT("periodicidad.IDPERIODICIDAD as idPeriodicidad");
		sql.SELECT_DISTINCT("periodicidad.periodosMes as periodicidadValor");
		sql.SELECT_DISTINCT("f_siga_getrecurso(periodicidad.descripcion, " + idioma + ") as periodicidadDesc");
		sql.SELECT_DISTINCT("precioServ.descripcion as precioServicioDesc");
		sql.SELECT_DISTINCT("suscripcion.fechaSuscripcion as fechaAlta");
		sql.SELECT_DISTINCT("suscripcion.fechaBajaFacturacion as fechaBaja");

		sql.FROM("pys_serviciossolicitados servSol");

		sql.INNER_JOIN("pys_serviciosInstitucion servIns on servIns.idinstitucion = servSol.idinstitucion AND servIns.idservicio = servSol.idservicio and servIns.idtiposervicios = servSol.idtiposervicios and servIns.idserviciosinstitucion = servSol.idserviciosinstitucion\r\n");
		sql.LEFT_OUTER_JOIN("pys_tipoiva tiva on tiva.idtipoiva = servIns.idtipoiva \r\n");
		sql.LEFT_OUTER_JOIN("pys_preciosServicios precioServ on precioserv.idservicio = servIns.idservicio AND precioserv.idtiposervicios = servSol.idtiposervicios " + "AND precioserv.idserviciosinstitucion = servSol.idserviciosinstitucion AND precioserv.idinstitucion = servSol.idinstitucion " + "and precioserv.idpreciosServicios  = servSol.IDPRECIOSSERVICIOS and precioServ.idperiodicidad = servSol.idperiodicidad\r\n");
		sql.LEFT_OUTER_JOIN("pys_periodicidad periodicidad on periodicidad.idperiodicidad = precioServ.idperiodicidad \r\n");
		sql.LEFT_OUTER_JOIN("pys_suscripcion suscripcion on suscripcion.idPeticion = servSol.idPeticion and suscripcion.idInstitucion = servSol.idInstitucion and suscripcion.idservicio = servSol.idservicio and suscripcion.idtiposervicios = servSol.idtiposervicios and suscripcion.idserviciosinstitucion = servSol.idserviciosinstitucion \r\n");

		// REVISAR: NO QUEDA CLARO QUE HAY QUE COMPROBAR PARA QUE TENGA EN CONSIDERACION LA FECHA
		if (aFechaDe != null) {
			DateFormat dateFormatFront = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", new Locale("en"));
			DateFormat dateFormatSql = new SimpleDateFormat("dd/MM/YY");
			String strDate = dateFormatSql.format(dateFormatFront.parse(aFechaDe.toString()).getTime());
			// Deben estar en estado "Aceptada" o "Pendiente de anulacion" en esa fecha
			// REVISAR
			sql.WHERE("(suscripcion.fechaSuscripcion is not null and " + "suscripcion.fechaSuscripcion <= to_date('" + strDate + "','dd/MM/YY')) and" + "(suscripcion.fechaBaja is null or suscripcion.fechaBaja > to_date('" + strDate + "','dd/MM/YY')))");
		}

		sql.WHERE(" servSol.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("servSol.idpeticion = " + idPeticion);

		sql.ORDER_BY(" servIns.DESCRIPCION");

		LOGGER.info("CONSULTA SERVICIOS SUSCRIPCION: \r\n" + sql.toString());

		return sql.toString();
	}

	public String selectNuevoId(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("MAX(IDPETICION) + 1 as Id");

		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION");

		sql.WHERE("IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}

	public String listadoCargaMasivaCompras(FiltroCargaMasivaCompras cargaMasivaItem, Short idInstitucion, Integer tamMaximo) {
		SQL sql = new SQL();
		SQL sqlFinal = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("cm.IDCARGAMASIVA");
		sql.SELECT("cm.TIPOCARGA");
		sql.SELECT("cm.IDINSTITUCION");
		sql.SELECT("cm.FECHACARGA");
		sql.SELECT("cm.IDFICHERO");
		sql.SELECT("cm.IDFICHEROLOG");
		sql.SELECT("cm.FECHAMODIFICACION");
		sql.SELECT("cm.NUMREGISTROS");
		sql.SELECT("cm.NOMBREFICHERO");
		sql.SELECT("cm.NUMREGISTROSERRONEOS");
		sql.SELECT("usu.DESCRIPCION");
		sql.FROM("cen_cargamasiva cm");
		sql.FROM("adm_usuarios usu");
		sql.WHERE("cm.USUMODIFICACION = usu.idusuario");
		sql.WHERE("cm.idinstitucion = usu.idinstitucion");
		sql.WHERE("cm.tipocarga = 'CP'");
		sql.WHERE("cm.idinstitucion = " + idInstitucion.toString());

		if (cargaMasivaItem.getFechaCargaDesde() != null) {
			String fechaCargaDesde = "";
			fechaCargaDesde = dateFormat.format(cargaMasivaItem.getFechaCargaDesde());
			sql.WHERE("TRUNC(fechacarga) >= TO_DATE('" + fechaCargaDesde + "', 'DD/MM/RRRR')");
		}

		if (cargaMasivaItem.getFechaCargaHasta() != null) {
			String fechaCargaHasta = "";
			fechaCargaHasta = dateFormat.format(cargaMasivaItem.getFechaCargaHasta());
			sql.WHERE("TRUNC(fechacarga) <= TO_DATE('" + fechaCargaHasta + "', 'DD/MM/RRRR')");
		}

		sqlFinal.SELECT("*");
		sqlFinal.FROM("(" + sql.toString() + ")");
		if (tamMaximo != null) {
			sqlFinal.WHERE("ROWNUM <= " + tamMaximo);
		}

		// LOGGER.info(sql.toString());

		return sql.toString();
	}

}
