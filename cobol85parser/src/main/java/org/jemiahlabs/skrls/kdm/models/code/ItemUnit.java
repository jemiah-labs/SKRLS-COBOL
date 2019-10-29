package org.jemiahlabs.skrls.kdm.models.code;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("itemUnit") 
public class ItemUnit extends XmiEntity {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String ext;
	@XStreamAsAttribute
	private String size;
	
	private ValueElement valueElement;
	
	public ItemUnit(String name, String ext) {
		this.name = name;
		this.ext = ext;
	}
	
	public ItemUnit(String name, ValueElement valueElement) {
		this.name = name;
		this.valueElement = valueElement;
	}
	
	public ValueElement getValueElement() {
		return valueElement;
	}

	public void setValueElement(ValueElement valueElement) {
		this.valueElement = valueElement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
