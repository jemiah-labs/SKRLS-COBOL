package org.jemiahlabs.skrls.kdm.models.code;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class DataModel extends XmiEntity {
	@XStreamAsAttribute
	private String name;
	@XStreamImplicit
	private List<DataElement> dataElements;
	
	public DataModel() {
		dataElements = new ArrayList<DataElement>();
		setType("data:DataModel");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addDataElement(DataElement dataElement) {
		dataElements.add(dataElement);
	}
	
	public List<DataElement> getDataElements() {
		return dataElements;
	}
}
