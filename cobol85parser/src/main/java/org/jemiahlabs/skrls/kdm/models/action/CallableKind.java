package org.jemiahlabs.skrls.kdm.models.action;

public enum CallableKind {
	REGULAR("regular"),
	EXTERNAL("external"),
	OPERATOR("operator"),
	STORED("stored"),
	UNKNOWN("unknown");
	
	private String description;
	
	private CallableKind(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
