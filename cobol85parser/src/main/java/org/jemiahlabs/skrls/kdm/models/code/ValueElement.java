package org.jemiahlabs.skrls.kdm.models.code;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("valueElement")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"value"})
public class ValueElement {
	@XStreamAsAttribute
	private String value;
	@XStreamAsAttribute
	private PrimitiveTypes primitiveType;
	
	public ValueElement(PrimitiveTypes primitiveType) {
		this.primitiveType = primitiveType;
	}
	
	public ValueElement(String value, PrimitiveTypes primitiveType) {
		this.value = value;
		this.primitiveType = primitiveType;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public PrimitiveTypes getPrimitiveType() {
		return primitiveType;
	}
	
	public void setPrimitiveType(PrimitiveTypes primitiveType) {
		this.primitiveType = primitiveType;
	}
}
