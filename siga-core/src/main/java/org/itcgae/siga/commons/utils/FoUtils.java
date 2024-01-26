package org.itcgae.siga.commons.utils;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.log4j.Logger;
//import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.util.MimeConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FoUtils
{
    /** To reuse the TransformerFactory **/
    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
    
    private static final Logger LOGGER = Logger.getLogger(FoUtils.class);
    
    private static final String TAG_FOG_TABLE_BODY = "fo:table-body";
    
    private static final String TAG_FOG_TABLE_ROW = "fo:table-row";
    
    private static final String TAG_FOG_TABLE_CELL = "fo:table-cell";
    
    private static final String TAG_FOG_BLOCK = "fo:block";



    /**
     * Converts an FO file to a PDF file using FOP.
     *
     * @param fo the FO file, not null.
     * @param pdf the target PDF file, not null.
     * @param resourceDir The base directory for relative path resolution, could be null.
     * If null, defaults to the parent directory of fo.
     * @param documentModel the document model to add PDF metadatas like author, title and keywords, could be null.
     * @throws javax.xml.transform.TransformerException In case of a conversion problem.
     * @throws IOException 
     * @throws SAXException 
     * @since 1.1.1
     */
    public static void convertFO2PDF( File fo, File pdf, String resourceDir )
        throws TransformerException
    {
        try ( OutputStream out = new BufferedOutputStream( new FileOutputStream( pdf ) ) )
        {
            URI baseURI = getBaseURI( fo, resourceDir );
            FopFactory fopFactory = new FopFactoryBuilder( baseURI )
            		.setStrictFOValidation(false)
            		.setStrictUserConfigValidation(false)
            		.build();
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop( MimeConstants.MIME_PDF, userAgent, out );
            Result res = new SAXResult( fop.getDefaultHandler() );

            // identity transformer
            Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
            transformer.transform( new StreamSource( fo ), res );
        }
        catch ( FOPException | TransformerConfigurationException | IOException e )
        {
            throw new TransformerException( e );
        }
        /*
        // Step 1: Construct a FopFactory by specifying a reference to the configuration file
    	// (reuse if you plan to render multiple documents!)
    	FopFactory fopFactory = FopFactory.newInstance(ResourceUtils.getFile("classpath:fopConf/fop.xconf"));

    	// Step 2: Set up output stream.
    	// Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
    	OutputStream out = new BufferedOutputStream(new FileOutputStream(pdf));

    	try {
    	    // Step 3: Construct fop with desired output format
    	    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

    	    // Step 4: Setup JAXP using identity transformer
    	    TransformerFactory factory = TransformerFactory.newInstance();
    	    Transformer transformer = factory.newTransformer(); // identity transformer

    	    // Step 5: Setup input and output for XSLT transformation
    	    // Setup input stream
    	    Source src = new StreamSource(fo);

    	    // Resulting SAX events (the generated FO) must be piped through to FOP
    	    Result res = new SAXResult(fop.getDefaultHandler());

    	    // Step 6: Start XSLT transformation and FOP processing
    	    transformer.transform(src, res);

    	} finally {
    	    //Clean-up
    	    out.close();
    	}*/
    }


    /**
     * Returns a base URI.
     *
     * @param fo the FO file.
     * @param resourceDir the resource directory.
     * @return URI.
     */
    private static URI getBaseURI( File fo, String resourceDir )
    {
        if ( resourceDir == null )
        {
            return fo.getParentFile().toURI();
        }
        else
        {
            return new File( resourceDir + "/" ).toURI();
        }
    }

    private FoUtils()
    {
        // Utility class
    }
    
    /**
     * 
     * @param plantilla content
     * @return Plantilla content sin tilder y otros caracteres especiales
     */
    static  public String reemplazaCaracteresExpeciales(String plantillaFO) {
    	// replazamos las tildes y otros caracteres especiales para UTF-8
    	plantillaFO = plantillaFO.replaceAll("À", "&#xC0;");
    	plantillaFO = plantillaFO.replaceAll("Á", "&#xC1;");
    	plantillaFO = plantillaFO.replaceAll("Â", "&#xC2;");
    	plantillaFO = plantillaFO.replaceAll("Ã", "&#xC3;");
    	plantillaFO = plantillaFO.replaceAll("Ä", "&#xC4;");
    	plantillaFO = plantillaFO.replaceAll("Å", "&#xC5;");
    	plantillaFO = plantillaFO.replaceAll("Æ", "&#xC6;");
    	plantillaFO = plantillaFO.replaceAll("Ç", "&#xC7;");
    	plantillaFO = plantillaFO.replaceAll("È", "&#xC8;");
    	plantillaFO = plantillaFO.replaceAll("É", "&#xC9;");
    	plantillaFO = plantillaFO.replaceAll("Ê", "&#xCA;");
    	plantillaFO = plantillaFO.replaceAll("Ë", "&#xCB;");
    	plantillaFO = plantillaFO.replaceAll("Ì", "&#xCC;");
    	plantillaFO = plantillaFO.replaceAll("Í", "&#xCD;");
    	plantillaFO = plantillaFO.replaceAll("Î", "&#xCE;");
    	plantillaFO = plantillaFO.replaceAll("Ï", "&#xCF;");
    	plantillaFO = plantillaFO.replaceAll("Ð", "&#xD0;");
    	plantillaFO = plantillaFO.replaceAll("Ñ", "&#xD1;");
    	plantillaFO = plantillaFO.replaceAll("Ò", "&#xD2;");
    	plantillaFO = plantillaFO.replaceAll("Ó", "&#xD3;");
    	plantillaFO = plantillaFO.replaceAll("Ô", "&#xD4;");
    	plantillaFO = plantillaFO.replaceAll("Õ", "&#xD5;");
    	plantillaFO = plantillaFO.replaceAll("Ö", "&#xD6;");
    	plantillaFO = plantillaFO.replaceAll("Ø", "&#xD8;");
    	plantillaFO = plantillaFO.replaceAll("Ù", "&#xD9;");
    	plantillaFO = plantillaFO.replaceAll("Ú", "&#xDA;");
    	plantillaFO = plantillaFO.replaceAll("Û", "&#xDB;");
    	plantillaFO = plantillaFO.replaceAll("Ü", "&#xDC;");
    	plantillaFO = plantillaFO.replaceAll("Ý", "&#xDD;");
    	plantillaFO = plantillaFO.replaceAll("Þ", "&#xDE;");
    	plantillaFO = plantillaFO.replaceAll("ß", "&#xDF;");
    	plantillaFO = plantillaFO.replaceAll("à", "&#xE0;");
    	plantillaFO = plantillaFO.replaceAll("á", "&#xE1;");
    	plantillaFO = plantillaFO.replaceAll("â", "&#xE2;");
    	plantillaFO = plantillaFO.replaceAll("ã", "&#xE3;");
    	plantillaFO = plantillaFO.replaceAll("ä", "&#xE4;");
    	plantillaFO = plantillaFO.replaceAll("å", "&#xE5;");
    	plantillaFO = plantillaFO.replaceAll("æ", "&#xE6;");
    	plantillaFO = plantillaFO.replaceAll("ç", "&#xE7;");
    	plantillaFO = plantillaFO.replaceAll("è", "&#xE8;");
    	plantillaFO = plantillaFO.replaceAll("é", "&#xE9;");
    	plantillaFO = plantillaFO.replaceAll("ê", "&#xEA;");
    	plantillaFO = plantillaFO.replaceAll("ë", "&#xEB;");
    	plantillaFO = plantillaFO.replaceAll("ì", "&#xEC;");
    	plantillaFO = plantillaFO.replaceAll("í", "&#xED;");
    	plantillaFO = plantillaFO.replaceAll("î", "&#xEE;");
    	plantillaFO = plantillaFO.replaceAll("ï", "&#xEF;");
    	plantillaFO = plantillaFO.replaceAll("ð", "&#xF0;");
    	plantillaFO = plantillaFO.replaceAll("ñ", "&#xF1;");
    	plantillaFO = plantillaFO.replaceAll("ò", "&#xF2;");
    	plantillaFO = plantillaFO.replaceAll("ó", "&#xF3;");
    	plantillaFO = plantillaFO.replaceAll("ô", "&#xF4;");
    	plantillaFO = plantillaFO.replaceAll("õ", "&#xF5;");
    	plantillaFO = plantillaFO.replaceAll("ö", "&#xF6;");
    	plantillaFO = plantillaFO.replaceAll("ø", "&#xF8;");
    	plantillaFO = plantillaFO.replaceAll("ù", "&#xF9;");
    	plantillaFO = plantillaFO.replaceAll("ú", "&#xFA;");
    	plantillaFO = plantillaFO.replaceAll("û", "&#xFB;");
    	plantillaFO = plantillaFO.replaceAll("ü", "&#xFC;");
    	plantillaFO = plantillaFO.replaceAll("ý", "&#xFD;");
    	plantillaFO = plantillaFO.replaceAll("þ", "&#xFE;");
    	plantillaFO = plantillaFO.replaceAll("ÿ", "&#xFF;");
    	

    	plantillaFO = plantillaFO.replaceAll("÷", "&#xF7;");
    	plantillaFO = plantillaFO.replaceAll("¡", "&#xA1;");
    	plantillaFO = plantillaFO.replaceAll("¢", "&#xA2;");
    	plantillaFO = plantillaFO.replaceAll("£", "&#xA3;");
    	plantillaFO = plantillaFO.replaceAll("¤", "&#xA4;");
    	plantillaFO = plantillaFO.replaceAll("¥", "&#xA5;");
    	plantillaFO = plantillaFO.replaceAll("¦", "&#xA6;");
    	plantillaFO = plantillaFO.replaceAll("§", "&#xA7;");
    	plantillaFO = plantillaFO.replaceAll("¨", "&#xA8;");
    	plantillaFO = plantillaFO.replaceAll("©", "&#xA9;");
    	plantillaFO = plantillaFO.replaceAll("ª", "&#xAA;");
    	plantillaFO = plantillaFO.replaceAll("«", "&#xAB;");
    	plantillaFO = plantillaFO.replaceAll("¬", "&#xAC;");
    	plantillaFO = plantillaFO.replaceAll("­", "&#xAD;");
    	plantillaFO = plantillaFO.replaceAll("®", "&#xAE;");
    	plantillaFO = plantillaFO.replaceAll("¯", "&#xAF;");
    	plantillaFO = plantillaFO.replaceAll("°", "&#xB0;");
    	plantillaFO = plantillaFO.replaceAll("±", "&#xB1;");
    	plantillaFO = plantillaFO.replaceAll("²", "&#xB2;");
    	plantillaFO = plantillaFO.replaceAll("³", "&#xB3;");
    	plantillaFO = plantillaFO.replaceAll("´", "&#xB4;");
    	plantillaFO = plantillaFO.replaceAll("µ", "&#xB5;");
    	plantillaFO = plantillaFO.replaceAll("¶", "&#xB6;");
    	plantillaFO = plantillaFO.replaceAll("·", "&#xB7;");
    	plantillaFO = plantillaFO.replaceAll("¸", "&#xB8;");
    	plantillaFO = plantillaFO.replaceAll("¹", "&#xB9;");
    	plantillaFO = plantillaFO.replaceAll("º", "&#xBA;");
    	plantillaFO = plantillaFO.replaceAll("»", "&#xBB;");
    	plantillaFO = plantillaFO.replaceAll("¼", "&#xBC;");
    	plantillaFO = plantillaFO.replaceAll("½", "&#xBD;");
    	plantillaFO = plantillaFO.replaceAll("¾", "&#xBE;");
    	plantillaFO = plantillaFO.replaceAll("¿", "&#xBF;");
    	plantillaFO = plantillaFO.replaceAll("€", "&#x20AC;");
    	
        return plantillaFO;
    }
    
    
    static public void fodUpdateTemplateVersion(File ficheroFOP) {
    	LOGGER.info("Actualizando plantilla fop a version 2.6 "+ ficheroFOP.getAbsolutePath());
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db;
    	
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(ficheroFOP);
			
			// Añadir a la etiqueta "<fo:repeatable-page-master-reference />" el atributo: master-reference="first"
			NodeList masterNodes = doc.getElementsByTagName("fo:repeatable-page-master-reference");
			for (int i = 0; i < masterNodes.getLength(); i++) {
				Node node = masterNodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element masterElement = (Element) node;

					masterElement.setAttribute("master-reference", "first");
				}
			}
			
			// las tablas row y celdas no pueden estar vacías o da un validateException
			fodUpdateVersionTables(doc);
			
			// guardar cambios
			TransformerFactory transformerFactory =  TransformerFactory.newInstance();
			Transformer transformer=  transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result =new StreamResult(ficheroFOP);

	        transformer.transform(source, result);
			
			LOGGER.info("plantilla actualizada fop a version 2.6 ");
		} catch (Exception e) {
			LOGGER.error("No se ha podido adaptar la plantilla a la version de fop 2.6", e);
		}

        
    }
    
    static private void fodUpdateVersionTables(Document doc) {
    	NodeList tablesNodes = doc.getElementsByTagName("fo:table");
    	// las tablas deben de tener como minimo un header o un body
		for (int i = 0; i < tablesNodes.getLength(); i++) {
			Node tableNode = tablesNodes.item(i);
			Element table = (Element) tableNode;
			NodeList headerelements = table.getElementsByTagName(TAG_FOG_TABLE_ROW);
			NodeList bodyelements = table.getElementsByTagName(TAG_FOG_TABLE_BODY);
			
			// añadir body y celda con block
			if (headerelements.getLength() == 0 && bodyelements.getLength() == 0) {
				Element block = doc.createElement(TAG_FOG_BLOCK);
				Element cell = doc.createElement(TAG_FOG_TABLE_CELL);
				Element body = doc.createElement(TAG_FOG_TABLE_BODY);
				
				cell.appendChild(block);
				body.appendChild(cell);
				table.appendChild(body);
			}
			
			// conprobar row no vacias
			for (int h = 0; h < headerelements.getLength(); h++) {
				fodUpdateVersionTableRows(headerelements.item(h).getChildNodes(), doc);
			}
				
			for (int b = 0; b < bodyelements.getLength(); b++) {
				fodUpdateVersionTableRows(bodyelements.item(b).getChildNodes(), doc);
			}
				
		}
    }
    
    static private void fodUpdateVersionTableRows(NodeList rowNodes, Document doc ) {
    	for (int i = 0; i < rowNodes.getLength(); i++) {
    		Node node = rowNodes.item(i);
			Element row = null;
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				row = (Element) rowNodes.item(i);
			}
			
			if (row != null && TAG_FOG_TABLE_ROW.equals(row.getTagName()) ) {
				// añadir celda con block
				if (!row.hasChildNodes()) {
					Element block = doc.createElement(TAG_FOG_BLOCK);
					Element cell = doc.createElement(TAG_FOG_TABLE_CELL);
					
					cell.appendChild(block);
					row.appendChild(cell);
				}
				// comprobar celdas no vacias
				else {
					fodUpdateVersionTablescell(row.getChildNodes(), doc);
				}
			}
			
		}
    	
    }
    
    static private void fodUpdateVersionTablescell(NodeList cellNodes, Document doc ) {
    	for (int i = 0; i < cellNodes.getLength(); i++) {
    		Node node = cellNodes.item(i);
			Element cell = null;
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				cell = (Element) node;
			}
			// añadir block
			if (cell != null && TAG_FOG_TABLE_CELL.equals(cell.getTagName()) && !cell.hasChildNodes() ) {
				Element block = doc.createElement(TAG_FOG_BLOCK);
				cell.appendChild(block);
				
			}
			
		}
    	
    }

}
