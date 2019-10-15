package org.jemiahlabs.skrls.kdm.models.code;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("dataModel") 
public class DataModel {
	@XStreamAsAttribute
	private String name;
	@XStreamImplicit
	private List<DataElement> dataElements;
	
	public DataModel() {
		dataElements = new ArrayList<DataElement>();
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
