package org.jemiahlabs.skrls.kdm.models.code;

public enum ParameterKind {
	BYVALUE("byValue"),
	BYNAME("byName"),
	BYREFERENCE("byReference"),
	VARIADIC("variadic"),
	RETURN("return"),
	THROWS("throws"),
	EXCEPTION("exception"),
	CATCHALL("catchall"),
	UNKNOWN("unknown");
	
	private String description;
	
	private ParameterKind(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
