package org.jemiahlabs.skrls.kdm.models.code;

public enum PrimitiveTypes {
	BOOLEAN("boolean"),
	CHAR("char"),
	ORDINAL("ordinal"),
	DATE("date"),
	TIME("time"),
	INTEGER("integer"),
	DECIMAL("decimal"),
	SCALED("scaled"),
	FLOAT("float"),
	VOID("void"),
	STRING("string"),
	BIT("bit"),
	BITSTRING("bitstring"),
	OCTET("octet"),
	OCTETSTRING("octetstring");
	
	private String description;
	
	private PrimitiveTypes(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
