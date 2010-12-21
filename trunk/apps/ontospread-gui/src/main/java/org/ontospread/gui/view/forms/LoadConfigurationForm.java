package org.ontospread.gui.view.forms;



public class LoadConfigurationForm {
	private int retries;
	private double defaultValue;
	private String[] resources;
	private String context;
	private long time;
	private int minConcepts;
	private int maxConcepts;
	private double activationValue;
	private boolean prizePaths;
	private String relationsFile;
	
	public boolean isPrizePaths() {
		return prizePaths;
	}
	public void setPrizePaths(boolean prizePaths) {
		this.prizePaths = prizePaths;
	}
	public double getActivationValue() {
		return activationValue;
	}
	public void setActivationValue(double activationValue) {
		this.activationValue = activationValue;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public double getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getMaxConcepts() {
		return maxConcepts;
	}
	public void setMaxConcepts(int maxConcepts) {
		this.maxConcepts = maxConcepts;
	}
	public int getMinConcepts() {
		return minConcepts;
	}
	public void setMinConcepts(int minConcepts) {
		this.minConcepts = minConcepts;
	}
	public String[] getResources() {
		return resources;
	}
	public void setResources(String[] strings) {
		this.resources = strings;
	}
	public int getRetries() {
		return retries;
	}
	public void setRetries(int retries) {
		this.retries = retries;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getRelationsFile() {
		return relationsFile;
	}
	public void setRelationsFile(String relationsFile) {
		this.relationsFile = relationsFile;
	}
	
}
