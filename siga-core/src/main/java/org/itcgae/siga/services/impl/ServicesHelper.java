package org.itcgae.siga.services.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlErrorCodes;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

public class ServicesHelper {
	
	private static Logger log = LoggerFactory.getLogger(ServicesHelper.class);
	
	// Para que coja el idioma español !!! Debería cogerlo desde el bundle de xmlbeans pero no lo coge
	private static ResourceBundle _bundle = PropertyResourceBundle.getBundle("org.apache.xmlbeans.message");
	
	private static final String SEPARADOR_RUTA_NODO = ">";
	
	public static List<String> validate(XmlObject xmlObject, boolean deleteEmptyNode)  {
		return validate(xmlObject, deleteEmptyNode, true);
	}

	private static List<String> validate(XmlObject xmlObject, boolean deleteEmptyNode, boolean rutaNodo)  {
		if (deleteEmptyNode) {
			deleteEmptyNode(xmlObject.getDomNode());
		}
		List<String> list = new ArrayList<String>();
		XmlOptions xmlOptions = new XmlOptions();
		List<XmlValidationError> errores = new ArrayList<XmlValidationError>();
		xmlOptions.setErrorListener(errores);
		
				
		if (!xmlObject.validate(xmlOptions)){
				
			String st = null;
			for (XmlValidationError error : errores) {
				Node node = error.getCursorLocation().getDomNode();
				String mensaje="";
				
				if (XmlErrorCodes.ELEM_COMPLEX_TYPE_LOCALLY_VALID$EXPECTED_DIFFERENT_ELEMENT.equals(error.getErrorCode()) && node.getParentNode() != null) {
					//el error es pq no se ha rellenado el hermano del nodo
					node = node.getParentNode();
				}
				if (XmlErrorCodes.ELEM_COMPLEX_TYPE_LOCALLY_VALID$MISSING_ELEMENT.equals(error.getErrorCode()) || XmlErrorCodes.ELEM_COMPLEX_TYPE_LOCALLY_VALID$EXPECTED_DIFFERENT_ELEMENT.equals(error.getErrorCode())) {
					String campos = "";
					if (error.getExpectedQNames() != null) {
						for (int i = 0; i < error.getExpectedQNames().size(); i++) {
							QName qName = (QName) error.getExpectedQNames().get(i);
							if (qName != null) {
								if (i == 0){
									campos += qName.getLocalPart();
								} else {
									campos += ", " + qName.getLocalPart();
								}
							}
						}
					}
					
					mensaje += getMessage(error.getErrorCode(), new String[]{null, campos});
					
//					mensaje += XmlError.formattedMessage(error.getErrorCode(), new String[]{null, campos});
				} else if (XmlErrorCodes.DATATYPE_VALID$PATTERN_VALID.equals(error.getErrorCode())) {					
					mensaje += getMessage(error.getErrorCode(), new String[]{null, getContenido(node).toString(), node.getNodeName()});
				} else if (XmlErrorCodes.DATATYPE_MAX_LENGTH_VALID$STRING.equals(error.getErrorCode()) || XmlErrorCodes.DATATYPE_MIN_LENGTH_VALID$STRING.equals(error.getErrorCode())) {
					mensaje += error.getMessage() + "'" + getContenido(node) + "'";
				} else if (XmlErrorCodes.ELEM_LOCALLY_VALID$NOT_NILLABLE.equals(error.getErrorCode())) {
					mensaje += getMessage(error.getErrorCode(), new String[]{node.getNodeName()});
				} else if (XmlErrorCodes.ELEM_COMPLEX_TYPE_LOCALLY_VALID$MISSING_REQUIRED_ATTRIBUTE.equals(error.getErrorCode())) {
					mensaje += getMessage(error.getErrorCode(), new String[]{error.getOffendingQName().getLocalPart(), error.getFieldQName().getLocalPart()});
				} else {
					mensaje += error.getMessage();
				}
				st = "";
				if (rutaNodo) {
					st = getRutaNodo(node) + ";";
				}else{
					st = node.getParentNode().getNodeName();
				}
				st += mensaje;
				
				if (!list.contains(st)) {
					list.add(st);
				}	
				error.getCursorLocation().insertComment("ERROR: " + mensaje);
			}
			
			log.warn("El XML no es válido: " + xmlObject.xmlText());
			
		}
		return list;
	}


	/**
	 * Obtiene el contenido de un nodo junto con sus hijos de manera recursiva
	 * @param node
	 * @return
	 */
	private static StringBuffer getContenido(Node node) {
		StringBuffer contenido = new StringBuffer();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			contenido.append(nodeList.item(i).getNodeValue()!=null?nodeList.item(i).getNodeValue().replaceAll("\\n", "").trim():"");
			contenido.append(getContenido(nodeList.item(i)));
		}
		return contenido;
	}

	private static String getRutaNodo(Node domNode) {
		String ruta = domNode.getLocalName();
		Node padre = domNode.getParentNode();
		while (padre != null && padre.getLocalName() != null) {
			ruta = padre.getLocalName() + " " + SEPARADOR_RUTA_NODO + " " + ruta;
			padre = padre.getParentNode();
		}
		return ruta;
	}

	/**
	 * 
	 * @param nodeParent
	 * @throws Exception
	 */
	public static void deleteEmptyNode(Node nodeParent) {
		List<Node> arrayNodes = new ArrayList<Node>();
		arrayNodes.add(nodeParent);
		Node node = null;
		while(!arrayNodes.isEmpty()){
			node = arrayNodes.remove(0);
			if (node != null) {	
				if (node.getNodeType() != Node.TEXT_NODE) {
					StringBuffer contenido = getContenido(node);
					if (contenido.length() == 0) {	
						if (node.getParentNode() != null) {
							node.getParentNode().removeChild(node);
						}
					} else {						
						NodeList nodeList = node.getChildNodes();
						for (int i = 0; i < nodeList.getLength(); i++) {
							arrayNodes.add(nodeList.item(i));	
						}	
					}
				}
			}	
		}
			
	}
	
	private static String getMessage(String errorCode, Object[] args) {
		String msg = null;
	
		if (errorCode != null && _bundle.containsKey(errorCode)) {			
			msg = MessageFormat.format(_bundle.getString(errorCode), args);
		}
		
		return msg;
	}
	
}
