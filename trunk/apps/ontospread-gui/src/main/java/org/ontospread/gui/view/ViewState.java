package org.ontospread.gui.view;




public enum ViewState {

    
    NEW("new"),
    CONFIGURATION("configuration"),
    INITIAL_CONCEPTS("initial_concepts"),
    RUN("run");
    
    private final String value;

    ViewState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ViewState fromValue(String v) {
        for (ViewState c: ViewState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }
}

