package org.itcgae.siga.com.documentos;

import java.util.HashMap;
import java.util.List;

import com.aspose.words.IMailMergeDataSource;


/**
 * Descripcion: Clase que recoge una lista de Hashmap y el nombre de la región donde cargar los datos
 * 
 */

public class DataMailMergeDataSource implements IMailMergeDataSource{
	
	List lista;
	String region;
    int mRecordIndex;

    /**
     * Descripcion: Constructor de la clase
     * @param nombreRegion 
     * @param lista: Vector de Hashtable con la información
     */
    public DataMailMergeDataSource(String nombreRegion, List lista)
    {
        this.lista = lista;
        // When the data source is initialized, it must be positioned before the first record.
        mRecordIndex= -1;
        region = nombreRegion;
    }

   
    public String getTableName()
    {
        return region;
    }

    public boolean getValue(String fieldName,  Object[] fieldValue)
    {
    	HashMap registro = (HashMap)lista.get(mRecordIndex);
    	if (registro.containsKey(fieldName)) {
            fieldValue[0] = (String)registro.get(fieldName);
            return true;
        }
        else
        {
            // A field with this name was not found,
            // return false to the Aspose.Words mail merge engine.
            fieldValue[0] = null;
            return false;
        }
    }
   
    public boolean moveNext()
    {
        if (isEof())
            return false;

        mRecordIndex++;

        return (!isEof());
    }

    private boolean isEof()
    {
        return (mRecordIndex >= lista.size());
    }


	/* (non-Javadoc)
	 * @see com.aspose.words.IMailMergeDataSource#getChildDataSource(java.lang.String)
	 */
	public IMailMergeDataSource getChildDataSource(String arg0)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}