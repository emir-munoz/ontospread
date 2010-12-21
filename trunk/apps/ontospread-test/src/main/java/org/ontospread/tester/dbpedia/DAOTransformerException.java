package org.ontospread.tester.dbpedia;

public class DAOTransformerException extends RuntimeException {

	 public DAOTransformerException() {
	        super();
	    }
    public DAOTransformerException(Exception e, String xsl) {
        super("grounding transformer exception trying to create transformer from xsl " + xsl,e);
    }
    
    public DAOTransformerException(Exception e) {
        super(e);
    }
    
}
