package org.itcgae.siga.commons.utils;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
//import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

public class FoUtils
{
    /** To reuse the TransformerFactory **/
    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();



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
            FopFactory fopFactory = new FopFactoryBuilder( baseURI ).build();
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
}
