package org.jemiahlabs.skrls.kdm.models.code;

public enum StorableKind {
	GLOBAL("global"),
	LOCAL("local"),
	STATIC("static"),
	EXTERNAL("external"),
	REGISTER("register"),
	UNKNOWN("unknown");
	
	private String description;
	
	private StorableKind(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
