package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class PeriodoEfectivoItem {

	    //ATRIBUTOS
	    public TreeSet ConjuntoDias;
	    
	    
	    //CONSTRUCTORES
	    /** Crea una nueva instancia de PeriodoEfectivo */
	    public PeriodoEfectivoItem ()
	    {
	        ConjuntoDias = new TreeSet();
	    }
	    /** Crea una nueva instancia de PeriodoEfectivo */
	    public PeriodoEfectivoItem (TreeSet ConjuntoDias)
	    {
	        this.ConjuntoDias = ConjuntoDias;
	    }
	    
	    
	    //GETTERS
	    public boolean contains (Date fecha) {
	        return (ConjuntoDias.contains (fecha));
	    }
	    
	    public Iterator iterator () {
	        return ConjuntoDias.iterator ();
	    }
	    
	    
	    //SETTERS
	    public void clear () {
	        ConjuntoDias.clear ();
	    }
	    
	    public void add (Date fecha) {
	        ConjuntoDias.add (fecha);
	    }
	    
	    
	    //METODOS
	    /** Indica si este PeriodoEfectivo coincide con otro */
	    public boolean equals (Object o) {
	        if (! (o instanceof PeriodoEfectivoItem))
	            return false;
	        
	        Vector v1, v2;
	        v1 = new Vector (); v1.addAll (this.ConjuntoDias);
	        v2 = new Vector (); v2.addAll (((PeriodoEfectivoItem) o).ConjuntoDias);
	        
	        return v1.equals (v2);
	    } //equals ()

	
}
