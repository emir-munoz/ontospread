package org.ontospread.gui.view;




public enum TimeToWait {
    SLOW("SLOW"),    
    MEDIUM("MEDIUM"),
    FAST("FAST");
    private final String value;

    TimeToWait(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TimeToWait fromValue(String v) {
        for (TimeToWait c: TimeToWait.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }
    public long timeToWait(){
    	return (value.equals(SLOW)?3000:value.equals(MEDIUM)?2000:1000);
    }

}
