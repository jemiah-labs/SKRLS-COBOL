package org.jemiahlabs.skrls.kdm.models.code;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("itemUnit") 
public class ItemUnit {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String ext;
	@XStreamAsAttribute
	private int size;
	
	private ValueElement valueElement;
	
	public ItemUnit(String name, int size) {
		this.name = name;
		this.size = size;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
