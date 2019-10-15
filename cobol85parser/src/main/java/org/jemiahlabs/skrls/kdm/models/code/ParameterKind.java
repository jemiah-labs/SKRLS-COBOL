package org.jemiahlabs.skrls.kdm.models.code;

public enum ParameterKind {
	PUBLIC("public"),
	PRIVATE("private"),
	PROTECTED("protected"),
	FINAL("final"),
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
