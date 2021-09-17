package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;

public class PysPeticioncomprasuscripcionSqlExtendsProvider {
	
	public String getFichaCompraSuscripcion(Short idInstitucion, FichaCompraSuscripcionItem peticion) {
		SQL sql = new SQL();
		

		sql.SELECT("pet.idinstitucion");
		sql.SELECT("per.idpersona");
		//TARJETA CLIENTE
		sql.SELECT("per.nombre");
		sql.SELECT("per.apellidos1 ");
		sql.SELECT("per.apellidos2");
		sql.SELECT("per.nifcif");
		sql.SELECT("per.IDTIPOIDENTIFICACION");
		//TARJETA SOLICITUD
		sql.SELECT("pet.idpeticion");
		sql.SELECT("pet.idestadopeticion");//Para determinar si es una fecha de solicitud, anulacion o denegacion. Determinar el equivalente de sus valores numericos. 
		sql.SELECT("usuario.descripcion as usuModificacion");
		sql.SELECT("pet.fecha"); // Esta fecha se utiliza tanto fecha de solicitud, anulacion o denegacion según la documentación.
		//FECHA SOLICITUD ALTA
		//PRODUCTO: Falta añadir pys_productossolicitados.fecharecepcionsolicitud  para la "fecha de solicitud real" de productos
		//SERVICIO: Le falta contraparte por el lado de servicio a que la tabla pys_productossolicitados carece de fecha aparte de la de modificacion
		//FECHA APROBACION
		//PRODUCTO: pys_compra.fecha 
		//SERVICIO: pys_suscripcion.fechasuscripcion
		//FECHA DENEGACION
		//AMBOS: pys_peticioncomprasuscripcion.fecha si el estado es Pendiente o Baja sin compra.
		//FECHA ANULACIÓN
		//AMBOS:  pys_peticioncomprasuscripcion.fecha si el estado es Aceptada o Baja con compra.
		//FECHA SOLICITUD ANULACIÓN
		//No viene reflejado en el documento de donde se extraeria esta fecha ni que campos se modificarian para reflejar que la petición 
		//está en "Solicitud de anulación".

        

		sql.FROM("pys_peticioncomprasuscripcion pet");

		sql.INNER_JOIN("cen_persona per on per.idpersona = pet.idpersona");
		sql.INNER_JOIN("adm_usuarios usuario ON (pet.usumodificacion = usuario.idusuario and pet.idinstitucion = usuario.idinstitucion)");

		sql.WHERE("pet.idinstitucion = "+idInstitucion.toString());
		sql.WHERE("pet.idpeticion = "+peticion.getnSolicitud());
		
		sql.ORDER_BY("idpeticion desc");
		
		return sql.toString();
	}

}
