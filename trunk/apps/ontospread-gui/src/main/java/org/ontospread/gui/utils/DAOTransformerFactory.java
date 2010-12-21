package org.ontospread.gui.utils;

import java.util.HashMap;

import javax.xml.transform.Transformer;

public abstract class DAOTransformerFactory {

    public abstract Transformer getDAOTransformer(String keyTemplates);
       
    public abstract Transformer getDAOTransformer(String keyTemplates, HashMap<String,String>parameters);
}
