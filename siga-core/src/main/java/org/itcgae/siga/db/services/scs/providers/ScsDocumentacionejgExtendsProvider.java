package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsDocumentacionejgSqlProvider;

public class ScsDocumentacionejgExtendsProvider extends ScsDocumentacionejgSqlProvider {
	public String getDocumentacion(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("doc.*," + 
				" catalogo.descripcion as presentadores," + 
				" familiar.solicitante," + 
				" catalogo2.descripcion as parentesco," + 
//				" persona.nombre," + 
//				" persona.apellido1," + 
//				" persona.apellido2");
				"(CASE WHEN persona.nombre is  NULL THEN '' ELSE persona.apellido1 || ' ' || persona.apellido2 || ', ' || persona.nombre END)as nombrecompleto");
		sql.SELECT("catalogoDOCUMENTOEJG.DESCRIPCION as labelDocumento");
		sql.SELECT("CASE WHEN doc.comisionajg = 1 THEN 'CAJG' ELSE 'ICA' END as propietarioDes");
		
		sql.FROM("scs_documentacionejg doc");
		
		sql.LEFT_OUTER_JOIN("scs_presentador presentador on (doc.idmaestropresentador=presentador.idpresentador and doc.idinstitucion=presentador.idinstitucion)");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos catalogo on (presentador.descripcion=catalogo.idrecurso and catalogo.idlenguaje ='" + idLenguaje + "')");
		
		sql.LEFT_OUTER_JOIN("scs_unidadfamiliarejg familiar on (doc.idinstitucion=familiar.idinstitucion and doc.idtipoejg=familiar.idtipoejg and doc.anio=familiar.anio and doc.numero=familiar.numero and doc.presentador=familiar.idpersona)");
		sql.LEFT_OUTER_JOIN("scs_parentesco parentesco on (familiar.idparentesco=parentesco.idparentesco and familiar.idinstitucion=parentesco.idinstitucion)");
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos catalogo2 on (parentesco.descripcion=catalogo2.idrecurso and catalogo2.idlenguaje ='" + idLenguaje + "')");
		
		sql.LEFT_OUTER_JOIN("scs_personajg persona on (familiar.idpersona=persona.idpersona and familiar.idinstitucion=persona.idinstitucion)");
		
		sql.LEFT_OUTER_JOIN("scs_documentoejg documentoejg on DOCUMENTOEJG.idinstitucion =doc.idinstitucion and documentoejg.fechabaja is null and DOCUMENTOEJG.idtipodocumentoejg=doc.idtipodocumento and DOCUMENTOEJG.iddocumentoejg=doc.iddocumento");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoDOCUMENTOEJG on catalogoDOCUMENTOEJG.idrecurso = DOCUMENTOEJG.DESCRIPCION and catalogoDOCUMENTOEJG.idlenguaje =" + idLenguaje );
		sql.WHERE("documentoejg.fechabaja is null");
		
		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("doc.anio = '" + ejgItem.getAnnio() + "'");
		if(ejgItem.getNumEjg() != null && ejgItem.getNumero() != "")
			sql.WHERE("doc.numero = '" + ejgItem.getNumero() + "'");
//			sql.WHERE("doc.numero = '16347'");

		if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("doc.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		if(idInstitucion != null && idInstitucion != "")
			sql.WHERE("doc.idinstitucion = '" + idInstitucion + "'");

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);

		}
		
		sql.ORDER_BY("IDDOCUMENTACION");
		
		return sql.toString();
	}

	private static String ID_OPERACION_DOCUMENTACION_EJG = "79";

	public String getDocumentacionParaEnviarPericles(Short idInstitucion, Short idTipoEjg, Short anio, Long numero, boolean reenviar) {
		SQL sql = new SQL();

		sql.SELECT("DE.*");
		sql.FROM("SCS_DOCUMENTACIONEJG  DE");
		sql.FROM("SCS_DOCUMENTOEJG      D");
		sql.FROM("SCS_UNIDADFAMILIAREJG FAMILIA");
		sql.FROM("SCS_PERSONAJG         PERSONA");
		sql.FROM("SCS_PRESENTADOR       MAESTROPRESENTADOR");
		sql.WHERE("DE.IDTIPODOCUMENTO = D.IDTIPODOCUMENTOEJG", "DE.IDINSTITUCION = D.IDINSTITUCION", "DE.IDDOCUMENTO = D.IDDOCUMENTOEJG");
		sql.WHERE("FAMILIA.IDPERSONA = PERSONA.IDPERSONA(+)", "FAMILIA.IDINSTITUCION = PERSONA.IDINSTITUCION(+)", "DE.IDINSTITUCION = FAMILIA.IDINSTITUCION(+)", "DE.IDTIPOEJG = FAMILIA.IDTIPOEJG(+)", "DE.ANIO = FAMILIA.ANIO(+)", "DE.NUMERO = FAMILIA.NUMERO(+)", "DE.PRESENTADOR = FAMILIA.IDPERSONA(+)");
		sql.WHERE("DE.IDMAESTROPRESENTADOR = MAESTROPRESENTADOR.IDPRESENTADOR(+)", "DE.IDINSTITUCION = MAESTROPRESENTADOR.IDINSTITUCION(+)");

		sql.WHERE(String.format("DE.IDINSTITUCION = %s", idInstitucion));
		sql.WHERE(String.format("DE.IDTIPOEJG = %s", idTipoEjg));
		sql.WHERE(String.format("DE.ANIO = %s", anio));
		sql.WHERE(String.format("DE.NUMERO = %s", numero));

		if (reenviar) {
			SQL enListaIntercambios = new SQL();
			enListaIntercambios.SELECT("1");

			sql.FROM("ECOM_COLA C");
			sql.LEFT_OUTER_JOIN("LEFT OUTER JOIN ECOM_INTERCAMBIO IC ON (C.IDECOMCOLA = IC.IDECOMCOLA)");

			enListaIntercambios.WHERE("IC.IDESTADORESPUESTA = 5");
			enListaIntercambios.WHERE("IC.RESPUESTA IS NULL");
			enListaIntercambios.WHERE("C.IDINSTITUCION = DE.IDINSTITUCION");

			enListaIntercambios.WHERE(String.format("C.IDECOMCOLA IN (%S)", getParamEcomCola("IDINSTITUCION", "DE.IDINSTITUCION")));
			enListaIntercambios.WHERE(String.format("C.IDECOMCOLA IN (%S)", getParamEcomCola("IDDOCUMENTACION", "DE.IDDOCUMENTACION")));

			enListaIntercambios.WHERE(String.format("C.IDOPERACION = %s", ID_OPERACION_DOCUMENTACION_EJG));


			sql.WHERE(String.format("NOT EXISTS (%s)", enListaIntercambios.toString()));
		}

		sql.ORDER_BY("PRESENTADOR");

		return sql.toString();
	}

	private String getParamEcomCola(String clave, String valor) {
		SQL sql = new SQL();
		sql.SELECT("CP.IDECOMCOLA");
		sql.FROM("ECOM_COLA_PARAMETROS CP");
		sql.WHERE(String.format("CP.CLAVE = '%s' AND CP.VALOR = '%s'", clave, valor));
		return sql.toString();
	}
}
